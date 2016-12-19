package com.ibm.dp.dao.impl;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.StringTokenizer;
import java.util.Map.Entry;

import org.apache.log4j.Logger;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.struts.upload.FormFile;

import com.ibm.dp.common.Constants;
import com.ibm.dp.common.DBQueries;
import com.ibm.dp.cpedp.DPCPEDBConnection;
import com.ibm.dp.dao.DistAvStockUploadDao;
import com.ibm.dp.dto.CircleDto;
import com.ibm.dp.dto.DPPoAcceptanceBulkDTO;
import com.ibm.dp.dto.DuplicateSTBDTO;
import com.ibm.dp.dto.NonSerializedToSerializedDTO;
import com.ibm.utilities.Utility;
import com.ibm.utilities.ValidatorUtility;
import com.ibm.virtualization.recharge.common.ResourceReader;
import com.ibm.virtualization.recharge.dao.rdbms.BaseDaoRdbms;
import com.ibm.virtualization.recharge.db.DBConnectionManager;
import com.ibm.virtualization.recharge.exception.DAOException;

public class DistAvStockUploadDaoImpl  extends BaseDaoRdbms implements DistAvStockUploadDao
{
	private static Logger logger = Logger.getLogger(DistAvStockUploadDaoImpl.class.getName());
	public ArrayList<DuplicateSTBDTO> uploadData(String stbSLNos) throws DAOException {
		return null;
	}

	public DistAvStockUploadDaoImpl(Connection conn) {
		super(conn);
	}	
	
	
	
	public DistAvStockUploadDaoImpl() {
		// TODO Auto-generated constructor stub
	}

	public List uploadExcel(FormFile myXls, int intCatCode) throws DAOException 
	{
		logger.info("**************AvStockUploadDaoImpl****1************");
		String str  = "";
		List list = new ArrayList();
		List Error_list = new ArrayList();
		DPPoAcceptanceBulkDTO dpPoAcceptanceBulkDTO = new DPPoAcceptanceBulkDTO();
		boolean validateFlag=true;
		String data="";
		ArrayList checkDuplicate = new ArrayList();
		String stb_no="";
		try
		{
			logger.info("**************AvStockUploadDaoImpl****2************");
	          boolean isEmpty=true;
	          InputStream stream = myXls.getInputStream();
	          ByteArrayOutputStream baos = new ByteArrayOutputStream();
	          int size = (int) myXls.getFileSize();
	          byte buffer[] = new byte[size];
	          int bytesRead = 0;
	          logger.info("read the input");  
	          while( (bytesRead = stream.read(buffer, 0, size)) != -1 )
	          {
	        	  baos.write( buffer, 0, bytesRead );
	          }
	          data = new String( baos.toByteArray() );
	          int k = 0;
	          
	          logger.info("************replace ,, with , ,*****************");
	          String newdata = data.replace(",", ",");
	          StringTokenizer st = new StringTokenizer(newdata, "");
	          
	          int intSrNoCount = 0;
	          int intMaxSrNoCount = Integer.parseInt(ResourceReader.getCoreResourceBundleValue(Constants.AV_UPLOAD_COUNT_MAX).trim());
	          
	          while (st.hasMoreTokens())
	          {
	        	  intSrNoCount++;
	        	  
	        	  if(intSrNoCount>intMaxSrNoCount)
	        	  {
	        		  Error_list = new ArrayList();
	        		  	dpPoAcceptanceBulkDTO.setErr_msg("Maximum "+intMaxSrNoCount+" serial numbers can be uploaded in a single file.");
		        	  	dpPoAcceptanceBulkDTO.setSerial_no(stb_no+" ");
		            	Error_list.add(dpPoAcceptanceBulkDTO);
		            	list = Error_list;
						validateFlag = false;
						return list;
	        	  }
	        	  
	        	  //logger.info("incase blank file is uploaded");
	        	  isEmpty = false;
	        	  dpPoAcceptanceBulkDTO = new DPPoAcceptanceBulkDTO();
	        	  stb_no = st.nextToken(",\n\r").toString();
	        	  if(stb_no != null)
	        	  {
	        		  stb_no = stb_no.trim();
	        	  }
	        	  boolean validate = validateCell(stb_no);
	        	  logger.info("validate  ::"+validate);
	        	  
		          if(stb_no != null && "".equals(stb_no.trim()))
		          {
		        	  	dpPoAcceptanceBulkDTO.setErr_msg("Blank space not allowed in Serial Number");
		        	  	dpPoAcceptanceBulkDTO.setSerial_no(stb_no+" ");
		            	Error_list.add(dpPoAcceptanceBulkDTO);
						validateFlag = false;
		          }
		          else if(checkDuplicate.contains(stb_no))
		          {
		        	  logger.info("************incase Duplicate values has been uploaded***************");
		        	  dpPoAcceptanceBulkDTO.setErr_msg("Duplicate Serial Number");
		        	  dpPoAcceptanceBulkDTO.setSerial_no(stb_no+" ");
		        	  //list.add(dpPoAcceptanceBulkDTO);
		        	  Error_list.add(dpPoAcceptanceBulkDTO);
		        	  validateFlag = false;
		          }
		          else if(!validate)
	          		{
		        	  logger.info("************incase invalid values has been uploaded******************");
				        dpPoAcceptanceBulkDTO.setErr_msg("Invalid Serial Number.It should be 16 digit number.");
	          			dpPoAcceptanceBulkDTO.setSerial_no(stb_no+" ");
	          			//list.add(dpPoAcceptanceBulkDTO);
	          			Error_list.add(dpPoAcceptanceBulkDTO);
	          			validateFlag = false;
	          		}
	          		else
	      			{
	          			logger.info("****************connection*************");
	      				String presentInDp = avStockPresentinDP(connection, stb_no, intCatCode);
	      				if( presentInDp != null && !presentInDp.equals(""))
	      				{
	      					dpPoAcceptanceBulkDTO.setSerial_no(stb_no+" ");
	          				dpPoAcceptanceBulkDTO.setErr_msg(presentInDp);
		              		Error_list.add(dpPoAcceptanceBulkDTO);
		              		validateFlag = false;
		              	}
	      				else
	      				{
	      					dpPoAcceptanceBulkDTO.setErr_msg("SUCCESS");
		          			checkDuplicate.add(stb_no);
		          			
		          			//dpPoAcceptanceBulkDTO.setSerial_no(stb_no+" ");
		          			//Error_list.add(dpPoAcceptanceBulkDTO);
	      				}
	      			}
		          k++;
	          };
	          if(isEmpty)
	          {
	        	  logger.info("**************empty file has been uploaded**************");
	        	  	dpPoAcceptanceBulkDTO.setErr_msg("Empty File Uploaded");
      				dpPoAcceptanceBulkDTO.setSerial_no(" ");
      				Error_list.add(dpPoAcceptanceBulkDTO);
      				list = Error_list;
	          }
	          else 
	          {
		          if(validateFlag)
		          {
		        	  dpPoAcceptanceBulkDTO.setList_srno(checkDuplicate);
		        	  list.add(dpPoAcceptanceBulkDTO);
		          }
		          else
		          {
		        	  list = Error_list;
		          }
	          }
        }
		catch (Exception e) {
        	
        	logger.info("****************Error while reading the file******************************");
    	  	dpPoAcceptanceBulkDTO.setErr_msg("Error while reading the file");
			dpPoAcceptanceBulkDTO.setSerial_no(" ");
			Error_list.add(dpPoAcceptanceBulkDTO);
			list = Error_list;
			return list;
        }
		return list;
	}
	
	public boolean validateCell(String str)
	{
		
		if(str.trim().length() != Constants.C2S_BULK_UPLOAD_NO_LENGTH_AV)
		{
			logger.info("Serial No length is not correct....  ::  "+str.length());
			return false;
		}

		if(!ValidatorUtility.isValidNumber(str.trim()))
		{
			logger.info("Serial No not numeric  ::  "+str);
			return false;
		}
		
		return true;
	}
	
	public boolean validateCellSTB(String str)
	{
		//logger.info("str"+str);
		str=str.trim();
		//System.out.println("========================================================"+str.codePointAt(0)+"space"+" ".codePointAt(0));
		logger.info("str"+str);
		if(!ValidatorUtility.isValidNumber(str))
		{
			logger.info("Serial No not numeric  ::  "+str);
			return false;
		}
		
		if(str.trim().length() != 11)
		{
			logger.info("Serial No length is not correct  ::  "+str.length());
			return false;
		}
		
		return true;
	}
	
	public static String avStockPresentinDP(Connection conDP,String strSTBSerialNumber, int intCatCode )throws DAOException
	{
		String strReturn = "";
		
		PreparedStatement pstm = null;
		PreparedStatement pstm1 = null;
		ResultSet rs =null;
		
		try
		{
			logger.info("strSTBSerialNumber  ::  "+strSTBSerialNumber);
			logger.info("intCatCode  ::  "+intCatCode);
			
			String strAvStock_MST = "SELECT STATUS from DP_AV_STOCK_MASTER WHERE SERIAL_NO =? and CATEGORY_CODE=?";
			
			pstm = conDP.prepareStatement(strAvStock_MST);
			pstm.setString(1, strSTBSerialNumber);
			pstm.setInt(2, intCatCode);
			rs = pstm.executeQuery();
			if(rs.next())
			{
				int intStatus = rs.getInt("STATUS");
				
				logger.info("SR NO Status in AV Stock Master  ::  "+intStatus);
				
				if(intStatus==1)
					strReturn = "The Serial number already uploaded in DP";
			}
			else
			{
				logger.info("SR NO Status not found in AV Stock Master  ");
				strReturn = "Invalid serial number : Not uploaded by admin";
			}
			
//			String strAvStock_MST = "select SERIAL_NO from DP_STOCK_INVENTORY WHERE SERIAL_NO =? WITH UR";
//			pstm = conDP.prepareStatement(strAvStock_MST);
//			pstm.setString(1, strSTBSerialNumber);
//			rs = pstm.executeQuery();
//			logger.info("******************checking whether Serial number exists before in database or not************");					
//			if (rs.next())
//				strReturn = "The Serial number already exists ";
//			else
//			{
//				DBConnectionManager.releaseResources(pstm, rs);
//				
//				strAvStock_MST = "SELECT STATUS from DP_AV_STOCK_MASTER WHERE SERIAL_NO =? and CATEGORY_CODE=?";
//				
//				pstm = conDP.prepareStatement(strAvStock_MST);
//				pstm.setString(1, strSTBSerialNumber);
//				pstm.setInt(2, intCatCode);
//				rs = pstm.executeQuery();
//				if(rs.next())
//				{
//					int intStatus = rs.getInt("STATUS");
//					
//					logger.info("SR NO Status in AV Stock Master  ::  "+intStatus);
//					
//					if(intStatus==1)
//						strReturn = "The Serial number already uploaded";
//				}
//				else
//				{
//					logger.info("SR NO Status not found in AV Stock Master  ");
//					strReturn = "Invalid serial number : Not uploaded by admin";
//				}
//			}
			return strReturn;
		}
		catch (Exception e) 
		{
			e.printStackTrace();
			logger.info("Error in inserting AV Master stcok  ::  "+e.getMessage());
			logger.info("Error in inserting AV Master stcok  ::  "+e);
			throw new DAOException(e.getMessage());
		}
		finally
		{
			DBConnectionManager.releaseResources(pstm, rs);
		}
	}
	
	public  String insertAVStockinDP(Connection conDP, List list, int intCatCode, long lnDistID, int intCircleID, int intProductID)throws DAOException
	{
		PreparedStatement psPRHdr = null;
		PreparedStatement psPRDtl = null;
		PreparedStatement psPOHdr = null;
		PreparedStatement psPODtl = null;
		PreparedStatement psInvHdr = null;
		PreparedStatement psInvDtl = null;
		PreparedStatement psPOAudTrl = null;
		PreparedStatement psStkInv = null;
		PreparedStatement psUpdAVMst = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		
		logger.info(intCatCode+" :: "+ lnDistID+" :: "+  intCircleID+" :: "+  intProductID);
		
		String strReturn = "Serial Numbers Updated Successfully";
		try
		{
			int intPR_No = 0;
			ps = conDP.prepareStatement(DBQueries.SQL_SEQ_VALUE);
			rs = ps.executeQuery();
			
			if(rs == null)
				return "Error in uploading AV stock";
			
			if(rs.next())
				intPR_No = rs.getInt(1);
			else
				return "Error in uploading AV stock";
			
			DBConnectionManager.releaseResources(ps, rs);
			
			String strExtDistID = "";
			
			ps = conDP.prepareStatement("select EXT_DIST_ID from DP_DISTRIBUTOR_MAPPING where DP_DIST_ID =? and TRANSACTION_TYPE_ID=? with ur ");
			ps.setLong(1, lnDistID);
			ps.setInt(2, Constants.DISTRIBUTOR_TYPE_SALES);
			rs = ps.executeQuery();
			if(rs == null)
				return "Error in uploading AV stock : Distributor is not of sales type";
			else
			{
				if(rs.next())
					strExtDistID = rs.getString(1);
				else
					return "Error in uploading AV stock : Distributor is not of sales type";
			}
			
			DBConnectionManager.releaseResources(ps, rs);

			String strPO_NO = "PODP/"+intPR_No;
			String strINV_NO = "INVDP/"+intPR_No;
			String strDC_NO = "DCDP/"+intPR_No;
			
			Timestamp tsCurrentTS = Utility.getCurrentTimeStamp();
			
			//INSERT INTO DP_PR_HEADER(PR_NO, PR_DATE, PR_DIST_ID, EXT_DIST_ID, CIRCLE_ID, WEBSER_OP_FLAG, WEBSER_MSG, IS_ACTIVE, INACTIVE_DATE, CATEGORY_CODE) 
			//VALUES(?, ?, ?, ?, ?, 'BT', 'PO Created by DP', 2, ?, ?)
			psPRHdr = conDP.prepareStatement(DBQueries.INSERT_PR_HEADER);
			
			psPRHdr.setInt(1, intPR_No);
			psPRHdr.setTimestamp(2, tsCurrentTS);
			psPRHdr.setLong(3, lnDistID);
			psPRHdr.setString(4, strExtDistID);
			psPRHdr.setInt(5, intCircleID);
			psPRHdr.setTimestamp(6, tsCurrentTS);
			psPRHdr.setInt(7, intCatCode);
			
			int intUpd = psPRHdr.executeUpdate();
			
			if(intUpd>0)
				logger.info("Record inserted in DP_PR_HEADER for PR ::  "+intPR_No);
			else
				logger.info("Error in record insertion in DP_PR_HEADER for PR ::  "+intPR_No);
			
			DBConnectionManager.releaseResources(psPRHdr, null);
			
			Iterator itr =  list.iterator();
			List<String> list_sr_no = null;
			
			while (itr.hasNext()) 
			{
				logger.info("********start getting SerialNo****************");
				DPPoAcceptanceBulkDTO dPPoAcceptanceBulkDTO = (DPPoAcceptanceBulkDTO) itr.next();
				
				list_sr_no = dPPoAcceptanceBulkDTO.getList_srno();
			}
			
			int intQty = 0;
			
			if(list_sr_no!=null)
				intQty = list_sr_no.size();
			else
				return "Error in uploading AV stock : No stock found";
			
			//INSERT INTO DP_PR_DETAILS(
//			PR_NO, PRODUCT_ID, RAISED_QTY, CIRCLE_ID, CREATE_DATE, EXT_PRODUCT_ID, INHAND_QTY, DP_QTY, DP_OPEN_STOCK, 
//			ELIGIBLE_QUANTITY, MAX_PO_QUANTITY, FLAG, ELIGIBLE_AMOUNT, APPROVED_QTY) 
//			    VALUES(?, ?, ?, ?, ?, (select BT_PRODUCT_CODE from DP_PRODUCT_MASTER where PRODUCT_ID=?), 
//			(select count(1) from DP_STOCK_INVENTORY where DISTRIBUTOR_ID=? and PRODUCT_ID=?),
//			(select count(1) from DP_STOCK_INVENTORY where DISTRIBUTOR_ID=? and PRODUCT_ID=?), 0,
//			0, 0, 0, 0, ?)
			
			psPRDtl = conDP.prepareStatement(DBQueries.INSERT_PR_DETAIILS);
			psPRDtl.setInt(1, intPR_No);
			psPRDtl.setInt(2, intProductID);
			psPRDtl.setInt(3, intQty);
			psPRDtl.setInt(4, intCircleID);
			psPRDtl.setTimestamp(5, tsCurrentTS);
			psPRDtl.setInt(6, intProductID);
			psPRDtl.setLong(7, lnDistID);
			psPRDtl.setInt(8, intProductID);
			psPRDtl.setLong(9, lnDistID);
			psPRDtl.setInt(10, intProductID);
			psPRDtl.setInt(11, intQty);
			
			intUpd = psPRDtl.executeUpdate();
			
			if(intUpd>0)
				logger.info("Record inserted in DP_PR_DETAILS for PR ::  "+intPR_No);
			else
				logger.info("Error in record insertion in DP_PR_DETAILS for PR ::  "+intPR_No);
			
			DBConnectionManager.releaseResources(psPRDtl, null);
			
//			INSERT INTO DPDTH.PO_HEADER(PR_NO, PO_NO, PO_DATE, PO_STATUS, STATUS_DATE, CREATE_DATE, REMARKS, IS_ACTIVE, INACTIVE_DATE, BT_ACK_DATE, CATEGORY_CODE) 
//		    VALUES(?, ?, ?, 31, ?, ?, 'DP PO', 2, ?, ?, ?)
			psPOHdr = conDP.prepareStatement(DBQueries.INSERT_PO_HEADER);
			psPOHdr.setInt(1, intPR_No);
			psPOHdr.setString(2, strPO_NO);
			psPOHdr.setTimestamp(3, tsCurrentTS);
			psPOHdr.setTimestamp(4, tsCurrentTS);
			psPOHdr.setTimestamp(5, tsCurrentTS);
			psPOHdr.setTimestamp(6, tsCurrentTS);
			psPOHdr.setTimestamp(7, tsCurrentTS);
			psPOHdr.setInt(8, intCatCode);
			
			intUpd = psPOHdr.executeUpdate();
			
			if(intUpd>0)
				logger.info("Record inserted in PO_HEADER for PO ::  "+strPO_NO);
			else
				logger.info("Error in record insertion in PO_HEADER for PO ::  "+strPO_NO);
			
			DBConnectionManager.releaseResources(psPOHdr, null);
			
//			INSERT INTO DPDTH.PO_DETAILS(PO_NO, PO_QTY, CREATE_DATE, EXT_PRODUCT_ID, PRODUCT_ID) 
//		    VALUES(?, ?, ?, (select BT_PRODUCT_CODE from DP_PRODUCT_MASTER where PRODUCT_ID=?), ?)
			psPODtl = conDP.prepareStatement(DBQueries.INSERT_PO_DETAILS);
			
			psPODtl.setString(1, strPO_NO);
			psPODtl.setInt(2, intQty);
			psPODtl.setTimestamp(3, tsCurrentTS);
			psPODtl.setInt(4, intProductID);
			psPODtl.setInt(5, intProductID);
			
			intUpd = psPODtl.executeUpdate();
			
			if(intUpd>0)
				logger.info("Record inserted in PO_DETAILS for PO ::  "+strPO_NO);
			else
				logger.info("Error in record insertion in PO_DETAILS for PO ::  "+strPO_NO);
			
			DBConnectionManager.releaseResources(psPODtl, null);
			
//			INSERT INTO DPDTH.INVOICE_HEADER(PO_NO, INV_NO, INV_DATE, DC_NO, DC_DT, CREATE_DATE, STATUS, DIST_ACTION_DATE, ACCEPT_DATE, CATEGORY_CODE) 
//		    VALUES(?, ?, ?, ?, ?, ?, 'ACCEPT', ?, ?, ?)
			psInvHdr = conDP.prepareStatement(DBQueries.INSERT_INVOICE_HEADER);
			
			psInvHdr.setString(1, strPO_NO);
			psInvHdr.setString(2, strINV_NO);
			psInvHdr.setTimestamp(3, tsCurrentTS);
			psInvHdr.setString(4, strDC_NO);
			psInvHdr.setTimestamp(5, tsCurrentTS);
			psInvHdr.setTimestamp(6, tsCurrentTS);
			psInvHdr.setTimestamp(7, tsCurrentTS);
			psInvHdr.setTimestamp(8, tsCurrentTS);
			psInvHdr.setInt(9, intCatCode);
			
			intUpd = psInvHdr.executeUpdate();
			
			if(intUpd>0)
				logger.info("Record inserted in INVOICE_HEADER for PO ::  "+strPO_NO);
			else
				logger.info("Error in record insertion in INVOICE_HEADER for PO ::  "+strPO_NO);
			
			DBConnectionManager.releaseResources(psInvHdr, null);
			
			//INSERT INTO INVOICE_DETAILS(INV_NO, INV_QTY, CREATE_DATE, EXT_PRODUCT_ID, PRODUCT_ID, ACCEPTED_QTY)  
			//VALUES(?, ?, ?, (select BT_PRODUCT_CODE from DP_PRODUCT_MASTER where PRODUCT_ID=?), ?, ?)
			psInvDtl = conDP.prepareStatement(DBQueries.INSERT_INVOICE_DETAIL);
			
			psInvDtl.setString(1, strINV_NO);
			psInvDtl.setInt(2, intQty);
			psInvDtl.setTimestamp(3, tsCurrentTS);
			psInvDtl.setInt(4, intProductID);
			psInvDtl.setInt(5, intProductID);
			psInvDtl.setInt(6, intQty);
			
			intUpd = psInvDtl.executeUpdate();
			
			if(intUpd>0)
				logger.info("Record inserted in INVOICE_DETAILS for INV ::  "+strINV_NO);
			else
				logger.info("Error in record insertion in INVOICE_DETAILS for INV ::  "+strINV_NO);
			
			DBConnectionManager.releaseResources(psInvDtl, null);
			
//			INSERT INTO PO_STATUS_AUDIT_TRAIL(PO_NO, PO_STATUS, PO_STATUS_DATE, CREATED_ON, TAT) 
//		    VALUES(?, 31, ?, ?, 0)
			psPOAudTrl = conDP.prepareStatement(DBQueries.INSERT_PO_AUDIT_TRAIL);
			
			psPOAudTrl.setString(1, strPO_NO);
			psPOAudTrl.setTimestamp(2, tsCurrentTS);
			psPOAudTrl.setTimestamp(3, tsCurrentTS);
			
			intUpd = psPOAudTrl.executeUpdate();
			
			if(intUpd>0)
				logger.info("Record inserted in PO_STATUS_AUDIT_TRAIL for PO ::  "+strPO_NO);
			else
				logger.info("Error in record insertion in PO_STATUS_AUDIT_TRAIL for PO ::  "+strPO_NO);
			
			DBConnectionManager.releaseResources(psPOAudTrl, null);
			
			//INSERT INTO DP_STOCK_INVENTORY(PRODUCT_ID, SERIAL_NO, MARK_DAMAGED, DISTRIBUTOR_ID, DISTRIBUTOR_PURCHASE_DATE, INV_NO, REMARKS, STATUS) 
//		    VALUES(?, ?, 'N', ?, ?, ?, 'DP PO', 5)
			psStkInv = conDP.prepareStatement(DBQueries.INSERT_STOCK_INVENTORY);
			psUpdAVMst = conDP.prepareStatement(DBQueries.UPDATE_AV_MASTER);
			
			for(String strSRNo : list_sr_no)
			{
				logger.info("Serial_No  ::  "+strSRNo);
				logger.info("updating new serial number into dp av stock master table ");
				psStkInv.setInt(1, intProductID);
				psStkInv.setString(2, strSRNo);
				psStkInv.setLong(3, lnDistID);
				psStkInv.setTimestamp(4, tsCurrentTS);
				psStkInv.setString(5, strINV_NO);
				
				psStkInv.addBatch();
				
				psUpdAVMst.setString(1, strSRNo);
				psUpdAVMst.setInt(2, intCatCode);
				
				psUpdAVMst.addBatch();
			}
			
			int[] intUpdArr = psStkInv.executeBatch();
			
			logger.info("No of AV serial no. inserted  ::  "+intUpdArr.length);
			
			intUpdArr = psUpdAVMst.executeBatch();
			
			logger.info("No of AV serial no. updated in AV Master Stock  ::  "+intUpdArr.length);
			
			strReturn = strReturn + " against PR NO. : "+intPR_No+" and PO NO : "+strPO_NO;
		}
		catch (SQLException e) 
		{
			e.printStackTrace();
			
			while (e != null) 
			{
				logger.info("SQL State:" + e.getSQLState());
				logger.info("Error Code:" + e.getErrorCode());
				logger.info("Message:" + e.getMessage());
				
				logger.error("SQL State:" + e.getSQLState());
				logger.error("Error Code:" + e.getErrorCode());
				logger.error("Message:" + e.getMessage());
				Throwable t = e.getCause();
				while (t != null) 
				{
					logger.error("Cause:" + t);
					t = t.getCause();
				}
				e = e.getNextException();
			}
			
			try
			{
				conDP.rollback();
			}
			catch(Exception e2)
			{
				e2.printStackTrace();
			}
			e.printStackTrace();
			throw new DAOException("Exception occured while inserting AV Stock  ::  "+e.getMessage());
		}
		catch(Exception e)
		{
			e.printStackTrace();
			logger.info(e.getMessage());
			logger.info(e);
			throw new DAOException("Exception occured while inserting AV Stock  ::  "+e.getMessage());
		}
		
		return strReturn;
	}

	public List<List<CircleDto>> getInitDataDAO(Connection connection, int intCircleID) throws DAOException 
	{
		List<List<CircleDto>> listReturn = new ArrayList<List<CircleDto>>();
		PreparedStatement ps = null;
		ResultSet rs = null;
		CircleDto  circleDto = null;
		List<CircleDto> listBusCat = new ArrayList<CircleDto>();
		List<CircleDto> listProduct = new ArrayList<CircleDto>();
		try
		{
			ps =  connection.prepareStatement("select CATEGORY_CODE, CATEGORY_NAME from DP_BUSINESS_CATEGORY_MASTER with ur");
			rs= ps.executeQuery();
			while(rs.next())
			{
				circleDto = new CircleDto();
				circleDto.setCircleId(rs.getString("CATEGORY_CODE"));
				circleDto.setCircleName(rs.getString("CATEGORY_NAME"));
				listBusCat.add(circleDto);
			}
			
			listReturn.add(listBusCat);
			DBConnectionManager.releaseResources(ps, rs);
			
			int intBusCat = com.ibm.virtualization.recharge.common.Constants.PRODUCT_CATEGORY_CODE_AV;
			
			ps = connection.prepareStatement("select PRODUCT_ID, PRODUCT_NAME from DP_PRODUCT_MASTER where CATEGORY_CODE=? and CIRCLE_ID=? and CM_STATUS='A' with ur");
			ps.setInt(1, intBusCat);
			ps.setInt(2, intCircleID);
			rs = ps.executeQuery();
			while(rs.next())
			{
				circleDto = new CircleDto();
				circleDto.setCircleId(rs.getString("PRODUCT_ID"));
				circleDto.setCircleName(rs.getString("PRODUCT_NAME"));
				listProduct.add(circleDto);
			}
			listReturn.add(listProduct);
		}
		catch (Exception e) 
		{
			e.printStackTrace();
			logger.info("Error in getting initial Data  ::  "+e.getMessage());
			logger.info("Error in getting initial Data  ::  "+e);
			throw new DAOException(e.getMessage());
		}
		finally
		{
			DBConnectionManager.releaseResources(ps, rs);
		}
		
		return listReturn;
	}
   //added by aman
	public List<NonSerializedToSerializedDTO> serializedConversion(
			Connection con,String strOLMId) throws DAOException {
		List<NonSerializedToSerializedDTO> listReturn = new ArrayList<NonSerializedToSerializedDTO>();
		PreparedStatement pstmt = null;
		ResultSet rset			= null;
		
		try
		{
			
			String query=" 	select a.LOGIN_NAME as DISTRIBUTOR_OLM_ID,b.ACCOUNT_NAME as DISTRIBUTOR_NAME,e.LOGIN_NAME as TSM_OLM_ID,d.ACCOUNT_NAME as TSM_NAME,f.CIRCLE_NAME, f.CIRCLE_ID "+
						 "	from VR_LOGIN_MASTER a,VR_ACCOUNT_DETAILS b,DP_DISTRIBUTOR_MAPPING c,VR_ACCOUNT_DETAILS d,VR_LOGIN_MASTER e,VR_CIRCLE_MASTER f "+
						 "	where a.LOGIN_NAME=? "+
						 "	and a.LOGIN_ID=b.ACCOUNT_ID "+
						 "	and b.ACCOUNT_ID=c.DP_DIST_ID "+
						 "	and c.PARENT_ACCOUNT=d.ACCOUNT_ID "+
						 "	and d.ACCOUNT_ID=e.LOGIN_ID "+
						 "  and c.TRANSACTION_TYPE_ID=2 "+
						 "	and b.CIRCLE_ID=f.CIRCLE_ID with ur";
			
			pstmt = con.prepareStatement(query);
			pstmt.setString(1,strOLMId);
			rset = pstmt.executeQuery();
			NonSerializedToSerializedDTO tempDTO = null;
			if(rset.next())
			{
				
				tempDTO = new NonSerializedToSerializedDTO();				
				String strDistID =rset.getString(1);
				tempDTO.setOlmId(rset.getString("DISTRIBUTOR_OLM_ID"));
				tempDTO.setDistName(rset.getString("DISTRIBUTOR_NAME"));
				tempDTO.setTsmOlmId(rset.getString("TSM_OLM_ID"));
				tempDTO.setTsmName(rset.getString("TSM_NAME"));
				tempDTO.setCircle(rset.getString("CIRCLE_NAME"));
				tempDTO.setCircleId(rset.getString("CIRCLE_ID"));
				listReturn.add(tempDTO);
				tempDTO=null;
			}
			else
			{
				tempDTO = new NonSerializedToSerializedDTO();
				tempDTO.setStrStatus("FAIL");
				tempDTO.setStrMessage("Invalid Distributor OLM ID");
				listReturn.add(tempDTO);
				tempDTO=null;
				logger.info("************serializedConversion***************");
				return listReturn;
			}
		}
		catch(SQLException sqle)
		{
			logger.info("Error while getting OLMID by Admin  ::  "+sqle);
			logger.info("Error while getting OLMID by Admin  ::  "+sqle.getMessage());
			sqle.printStackTrace();
			throw new DAOException("SQLException: " + sqle.getMessage());
		}
		catch(Exception e)
		{
			logger.info("Error while getting OLMID by Admin  ::  "+e);
			logger.info("Error while getting OLMID by Admin  ::  "+e.getMessage());
			e.printStackTrace();
			throw new DAOException("Exception: " + e.getMessage());
		}
		finally
		{
			DBConnectionManager.releaseResources(con);
			DBConnectionManager.releaseResources(pstmt,rset);
		}
		return listReturn;
	}
	public List<NonSerializedToSerializedDTO> getALLProdSerialData(Connection connection, String circleId) throws DAOException {
		logger.info("**************AvStockUploadDaoImpl-uploadExcelSerializedConversionFormat****1************");
		List<NonSerializedToSerializedDTO> listReturn = new ArrayList<NonSerializedToSerializedDTO>();
		PreparedStatement pstmt = null;
		ResultSet rset			= null;
		
		try
		{
			String query=" select PRODUCT_NAME from DP_PRODUCT_MASTER where CIRCLE_ID=? and CM_STATUS='A' and CATEGORY_CODE=1  with ur";
			
			pstmt = connection.prepareStatement(query);
			pstmt.setInt(1, Integer.parseInt(circleId));
			rset = pstmt.executeQuery();
			NonSerializedToSerializedDTO tempDTO = null;
			while(rset.next())
			{
				tempDTO = new NonSerializedToSerializedDTO();				
				tempDTO.setProductName(rset.getString("PRODUCT_NAME"));
				//tempDTO.setProductId(rset.getString("PRODUCT_ID"));
				listReturn.add(tempDTO);
				tempDTO=null;
			}
			
		}
		catch(SQLException sqle)
		{
			logger.info("Error while getting OLMID by Admin  ::  "+sqle);
			logger.info("Error while getting OLMID by Admin  ::  "+sqle.getMessage());
			sqle.printStackTrace();
			throw new DAOException("SQLException: " + sqle.getMessage());
		}
		catch(Exception e)
		{
			logger.info("Error while getting OLMID by Admin  ::  "+e);
			logger.info("Error while getting OLMID by Admin  ::  "+e.getMessage());
			e.printStackTrace();
			throw new DAOException("Exception: " + e.getMessage());
		}
		finally
		{
			DBConnectionManager.releaseResources(connection);
			DBConnectionManager.releaseResources(pstmt,rset);
		}
		return listReturn;
		
		
	}

	public List uploadSerializedExcel(InputStream inputstream,String distOlmID, String circleId) throws DAOException 
	{
		logger.info("**************AvStockUploadDaoImpl****1************");
		String str  = "";
		NonSerializedToSerializedDTO nonSerializedToSerializedDTO = new NonSerializedToSerializedDTO();
		
		String data="";
		List list = new ArrayList();
		ArrayList checkDuplicate = new ArrayList();
		Connection conCPE = null;
		String productId="";
		String productName="";
		String distProdComb ="";
		try
		{
			conCPE=DPCPEDBConnection.getDBConnectionCPE();
			logger.info("**************AvStockUploadDaoImpl****2************");
			boolean isEmpty=true;
			HSSFWorkbook workbook = new HSSFWorkbook(inputstream);
			HSSFSheet sheet = workbook.getSheetAt(0);
			Iterator rows = sheet.rowIterator();
			int totalrows = sheet.getLastRowNum()+1;
			logger.info("Total Rows == "+sheet.getLastRowNum());
			nonSerializedToSerializedDTO = new NonSerializedToSerializedDTO();
			
			if(totalrows > Integer.parseInt(ResourceReader.getValueFromBundle(com.ibm.virtualization.recharge.common.Constants.BULK_UPLAOD_MAX_LIMIT , com.ibm.virtualization.recharge.common.Constants.WEBSERVICE_RESOURCE_BUNDLE)))
			{
				
				nonSerializedToSerializedDTO.setErr_msg("Limit exceeds: Maximum "+Integer.parseInt(ResourceReader.getValueFromBundle(com.ibm.virtualization.recharge.common.Constants.BULK_UPLAOD_MAX_LIMIT , com.ibm.virtualization.recharge.common.Constants.WEBSERVICE_RESOURCE_BUNDLE))+" Serial Numbers are allowed in a file.");
	      		list.add(nonSerializedToSerializedDTO);
	      		return list;
			}
			
			int rowNumber = 1;
	          
	        int k=1;
	          
	        while(rows.hasNext()) 
	        {
	        	boolean validateFlag=true;
	        	nonSerializedToSerializedDTO = new NonSerializedToSerializedDTO();
	        	HSSFRow row = (HSSFRow) rows.next();
	        	Iterator cells = row.cellIterator();
	        	 logger.info(rowNumber+" : rowNumber");
	        	if(rowNumber>1)
	        	{
		        	int columnIndex = 0;
		            int cellNo = 0;
		            if(cells != null)
		            {
		            	//distProdComb ="";
		            	productName="";
		    			productId="";
		            	while (cells.hasNext()) 
		            	{
		            		if(cellNo < 2)
		            		{
		            			cellNo++;
		            			HSSFCell cell = (HSSFCell) cells.next();
	        			  
		            			columnIndex = cell.getColumnIndex();
		            			//logger.info("columnIndex ::: "+columnIndex);
		            			if(columnIndex > 3)
		            			{
		            				//nonSerializedToSerializedDTO = new NonSerializedToSerializedDTO();
			              			validateFlag =false;
			              		
			              			nonSerializedToSerializedDTO.setErr_msg("File should contain only two data columns");
			              			list.add(nonSerializedToSerializedDTO);
			              			return list;
		            			}
		            			
		            			String cellValue = null;
		        		  
		            			switch(cell.getCellType()) 
		            			{
			            			case HSSFCell.CELL_TYPE_NUMERIC:
			            				cellValue = String.valueOf((double)cell.getNumericCellValue());
			            				break;
			            			case HSSFCell.CELL_TYPE_STRING:
			            				cellValue = cell.getStringCellValue();
			            				break;
		            			}
		            			
		            			logger.info("Row Number == "+rowNumber +" and Cell No. === "+cellNo +" and value == "+cellValue );
		            			if(cellNo==1)
		            			{
		            				
			            			//String cellProduct = cellValue.trim();
			            			String strProdName = cellValue.trim();
			            			logger.info("PRODUCT NAME  ::  "+strProdName);
			            			nonSerializedToSerializedDTO.setProductName(strProdName);
			            			Integer intProductID = Utility.getProductID(connection, strProdName, Integer.parseInt(circleId));
			            			nonSerializedToSerializedDTO.setProductId(intProductID+"");
			            			if(intProductID==0)
				            		{
			            				validateFlag =false;
			            				nonSerializedToSerializedDTO.setProductName(strProdName);
			            				nonSerializedToSerializedDTO.setErr_msg("Invalid Product Name at row No: "+(rowNumber));
				              			list.add(nonSerializedToSerializedDTO);
				              			//logger.info("list size *********** ::  "+list.size());
				              			distProdComb = cellValue.trim();
			            			}
			            			else 
			            			{
			            				//validateFlag =true;
			            				//nonSerializedToSerializedDTO.setProductId(intProductID+"");
			            				nonSerializedToSerializedDTO.setProductName(strProdName+"");
			            				distProdComb = cellValue.trim();
			            			}
		            			}
		            			else if(cellNo==2)
		            			{
		            				//String cellSerialNo = cellValue.trim();
			            			String stb_no = cellValue.trim();
			            			logger.info("stb_no"+stb_no);
		            				boolean validate = validateCellSTB(stb_no);
		            				nonSerializedToSerializedDTO.setSerial_no(stb_no+" ");
		            		        if(stb_no != null && "".equals(stb_no.trim()))
		            		        {
		            		        	nonSerializedToSerializedDTO.setErr_msg("Blank space not allowed in Serial Number");
		            		        	nonSerializedToSerializedDTO.setSerial_no(stb_no+" ");
		            		        	list.add(nonSerializedToSerializedDTO);
		            		        	//logger.info("list size ::  "+list.size());
		            					validateFlag = false;
		            		        }
		            		        else if(checkDuplicate.contains(stb_no))
		            		        {
		            		        	  logger.info("************incase Duplicate values has been uploaded***************");
		            		        	  nonSerializedToSerializedDTO.setErr_msg("Duplicate Serial Number");
		            		        	  nonSerializedToSerializedDTO.setSerial_no(stb_no+" ");
		            		        	  //list.add(dpPoAcceptanceBulkDTO);
		            		        	  list.add(nonSerializedToSerializedDTO);
		            		        	  logger.info("list size ^^^^^^^^^^^::  "+list.size());
		            		        	  validateFlag = false;
		            		        }
		            		        else if(!validate)
		            	          	{
		            		        	logger.info("************incase invalid values has been uploaded******************");
		            		        	nonSerializedToSerializedDTO.setErr_msg("Invalid Serial Number.It should be 11 digit number.");
		            		        	nonSerializedToSerializedDTO.setSerial_no(stb_no+" ");
		            	          		list.add(nonSerializedToSerializedDTO);
		            	          		//logger.info("list size :::::::  "+list.size());
		            	          		validateFlag = false;
		            	          	}
		            	          	else
		            	      		{
		                      			logger.info("****************connection*************");
		                  				String presentInDp = Utility.stbPresentinDP(connection, stb_no);
		                  				
		                  				if( presentInDp != null && !presentInDp.equals(""))
		                  				{
		                  					nonSerializedToSerializedDTO.setErr_msg(presentInDp);
		                  					nonSerializedToSerializedDTO.setSerial_no(stb_no+" ");
		            	              		list.add(nonSerializedToSerializedDTO);
		            	              		//logger.info("list size :: ::::::::::::::: "+list.size());
		            	              		validateFlag = false;
		            	              	}
		                  				else
		                  				{
		                  					String presentInCPE = Utility.stbPresentinCPE(conCPE, stb_no);
		                  					//System.out.println(" ....... %%%%%%%%%%%%%%%%%%%  .."+presentInCPE);
		                  					if( presentInCPE != null && !presentInCPE.equals(""))
		            	      				{
		                  						//System.out.println("**************if****"+stb_no+"presentInDp"+presentInCPE);
		            	      				nonSerializedToSerializedDTO.setErr_msg(presentInCPE);
		            	      				nonSerializedToSerializedDTO.setSerial_no(stb_no+" ");
		            		              	list.add(nonSerializedToSerializedDTO);
		            		              	validateFlag = false;
		            		            	}
		                  					else
		            	      				{
		                  						System.out.println("**************else****"+stb_no);
		                  						nonSerializedToSerializedDTO.setSerial_no(stb_no);
		            	      					nonSerializedToSerializedDTO.setErr_msg("SUCCESS");
		            		          			checkDuplicate.add(stb_no);
		            		          			list.add(nonSerializedToSerializedDTO);
		            	      				}
		                  				}
		            	      		}
		            			}
		            			
		            		}
			        		else
			        		{
			        			//nonSerializedToSerializedDTO = new NonSerializedToSerializedDTO();
			        			validateFlag =false;
			        			nonSerializedToSerializedDTO.setErr_msg("File Should Contain Only two data column");
			        			//list.add(nonSerializedToSerializedDTO);
			        			//return list;
			        			List list2 = new ArrayList();
			            		list2.add(nonSerializedToSerializedDTO);
			            		return list2;
			        		}
		            		
		            	}
		            	if(cellNo!=2 && cellNo<1 )
		            	{
		            		//nonSerializedToSerializedDTO = new NonSerializedToSerializedDTO();
		            		validateFlag =false;
		            		nonSerializedToSerializedDTO.setErr_msg("File should contain two data column");
		            		List list2 = new ArrayList();
		            		list2.add(nonSerializedToSerializedDTO);
		            		return list2;
		            	}
		            	
	            	}
	        	   rowNumber++;
	        	  /* if(validateFlag!=false)
       			{
	        		   
       				logger.info(rowNumber+ ": serial number is "+nonSerializedToSerializedDTO.getSerial_no());
           			nonSerializedToSerializedDTO.setErr_msg("SUCCESS");
       				list.add(nonSerializedToSerializedDTO);
	        			
       			}*/
	            }
	        	else
	        	{
	        		//For Header Checking  
	        		int columnIndex = 0;
	        		int cellNo = 0;
	        		if(cells != null)
	        		{
	        			while (cells.hasNext()) 
	        			{
	        				if(cellNo < 3)
	        				{
	        					cellNo++;
	        					HSSFCell cell = (HSSFCell) cells.next();
	        					columnIndex = cell.getColumnIndex();
			              		//logger.info("columnIndex :::in header "+columnIndex);
	        					if(columnIndex > 3)
			              		{
			              			validateFlag =false;
			              			nonSerializedToSerializedDTO = new NonSerializedToSerializedDTO();
			              			
			              			nonSerializedToSerializedDTO.setErr_msg("File should contain only two data column");
			              			List list2 = new ArrayList();
				            		list2.add(nonSerializedToSerializedDTO);
			              			return list2;
			              		}
			              		String cellValue = null;
			        		  
			            		switch(cell.getCellType()) 
			            		{
				            		case HSSFCell.CELL_TYPE_NUMERIC:
				            			cellValue = String.valueOf((long)cell.getNumericCellValue());
				            		break;
				            		case HSSFCell.CELL_TYPE_STRING:
				            			cellValue = cell.getStringCellValue();
					            	break;
			            		}
			            		if(cellValue ==  null || "".equalsIgnoreCase(cellValue.trim()))
			            		{
			            			validateFlag =false;
			            			nonSerializedToSerializedDTO.setErr_msg("File should contain All Required Values");
			              			List list2 = new ArrayList();
				            		list2.add(nonSerializedToSerializedDTO);
			              			return list2;
			               		}
			            		
			            		logger.info("Row Number == "+rowNumber +" and Cell No. === "+cellNo +" and value == "+cellValue.trim().toLowerCase() );
			            		if(cellNo==1)
			            		{
			            			if(!"product name".equals(cellValue.trim().toLowerCase()))
				            		{
			            				validateFlag =false;
			            				nonSerializedToSerializedDTO.setErr_msg("Invalid Header For Product Name");
				              			list.add(nonSerializedToSerializedDTO);
			            				
			            			}
			            		}
			            		else if(cellNo==2)
			            		{
			            			if(!"serial number".equals(cellValue.trim().toLowerCase()))
				            		{
			            				validateFlag =false;
			            				nonSerializedToSerializedDTO.setErr_msg("Invalid Header For Serial Number");
				              			list.add(nonSerializedToSerializedDTO);
			            			}
			            		}
	        				}
	        				else
	        				{
	        					nonSerializedToSerializedDTO = new NonSerializedToSerializedDTO();
	        					validateFlag =false;
	        					nonSerializedToSerializedDTO.setErr_msg("File should contain only two data Headers");
	        					List list2 = new ArrayList();
	        					list2.add(nonSerializedToSerializedDTO);
	        					return list2;
	        				}
	        			}
	        			if(cellNo !=2)
	        			{
		            		validateFlag =false;
		            		nonSerializedToSerializedDTO.setErr_msg("File should contain two data Headers");
		            		List list2 = new ArrayList();
		            		list2.add(nonSerializedToSerializedDTO);
	              			return list2;
		            	}
	        		} 
	        		/*else
		            {
	        			nonSerializedToSerializedDTO.setErr_msg("File should contain two data column");
		            	List list2 = new ArrayList();
			           	list2.add(nonSerializedToSerializedDTO);
		              	logger.info("*******************excel serilaozedupload***********"+list2);
			           	return list2;
		            }*/
	        		
	        	rowNumber ++;
	        }
	        k++;
	     }
	     logger.info("K == "+k);
	     
	     if(k==1)
	     {
	    	 nonSerializedToSerializedDTO = new NonSerializedToSerializedDTO();
	    	 nonSerializedToSerializedDTO.setErr_msg("Blank File can not be uploaded!!");
	    	 logger.info("************uploadSerializedExcel***************");
	    	 list.add(nonSerializedToSerializedDTO);  	
	     }
	}
	catch (Exception e) 
	{
        	logger.info("****************Error while reading the file******************************");
        	logger.info(e.getMessage()+" == error while reading file..");
        	nonSerializedToSerializedDTO.setErr_msg("Error while reading the file");
        	nonSerializedToSerializedDTO.setSerial_no(" ");
			list.add(nonSerializedToSerializedDTO);
			return list;
     }
	return list;
}
	
	private String chcknonserilized(Connection connection, String distOlmID)throws Exception 
	{
		String strReturn = "";
		
		PreparedStatement pstm = null;
		PreparedStatement pstm1 = null;
		ResultSet rs =null;
		
		try
		{
			logger.info("distOlmID  ::  "+distOlmID);
			String strAvStock_MST = "SELECT STATUS from DP_AV_STOCK_MASTER WHERE SERIAL_NO =? and CATEGORY_CODE=?";
			
			pstm = connection.prepareStatement(strAvStock_MST);
			pstm.setString(1, distOlmID);
			rs = pstm.executeQuery();
			if(rs.next())
			{
				int intStatus = rs.getInt("STATUS");
				
				logger.info("SR NO Status in AV Stock Master  ::  "+intStatus);
				
				if(intStatus==1)
					strReturn = "The Serial number already uploaded in DP";
			}
			else
			{
				logger.info("SR NO Status not found in AV Stock Master  ");
				strReturn = "Invalid serial number : Not uploaded by admin";
			}
			
			return strReturn;
		}
		catch (Exception e) 
		{
			e.printStackTrace();
			logger.info("Error in inserting AV Master stcok  ::  "+e.getMessage());
			logger.info("Error in inserting AV Master stcok  ::  "+e);
			throw new DAOException(e.getMessage());
		}
		finally
		{
			DBConnectionManager.releaseResources(pstm, rs);
		}
	}

	public String convertNserToSerStock(Connection conDP, List list, String distOlmID, String circleId) throws DAOException 
	{
		PreparedStatement psPRHdr = null;
		PreparedStatement psPRDtl = null;
		PreparedStatement psPOHdr = null;
		PreparedStatement psPODtl = null;
		PreparedStatement psInvHdr = null;
		PreparedStatement psInvDtl = null;
		PreparedStatement psPOAudTrl = null;
		PreparedStatement psStkInv = null;
		PreparedStatement psUpdAVMst = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		Long lnDistID = 0L;
		
		logger.info(distOlmID+" :: " +circleId);
		
		System.out.println("*********************in convertNserToSerStock ****************");
		String strReturn = "Non serialized to serialized stock converted successfully";
		try
		{
			int intPR_No = 0;
			ps = conDP.prepareStatement(DBQueries.SQL_SEQ_VALUE);
			rs = ps.executeQuery();
			
			if(rs == null)
				return "Error in coverting Non serialized to serialized stock";
			
			if(rs.next())
				intPR_No = rs.getInt(1);
			else
				return "Error in coverting Non serialized to serialized stock";
			
			DBConnectionManager.releaseResources(ps, rs);
			
			ps = conDP.prepareStatement("select LOGIN_ID from VR_LOGIN_MASTER where LOGIN_NAME=? with ur");
			ps.setString(1, distOlmID);
			rs = ps.executeQuery();
			if(rs.next())
				lnDistID = rs.getLong(1);
			else
				return "Error in coverting Non serialized to serialized stock";
			
			logger.info("lnDistID  ::  "+lnDistID);
			
			String strExtDistID = "";
			
			ps = conDP.prepareStatement("select EXT_DIST_ID from DP_DISTRIBUTOR_MAPPING where DP_DIST_ID =? and TRANSACTION_TYPE_ID=? with ur ");
			ps.setLong(1, lnDistID);
			ps.setInt(2, Constants.DISTRIBUTOR_TYPE_DEPOSIT);
			rs = ps.executeQuery();
			if(rs == null)
				return "Error in coverting Non serialized to serialized stock : Distributor is not of CPE type";
			else
			{
				if(rs.next())
					strExtDistID = rs.getString(1);
				else
					return "Error in coverting Non serialized to serialized stock : Distributor is not of CPE type";
			}
			
			DBConnectionManager.releaseResources(ps, rs);

			String strPO_NO = "PODP/"+intPR_No;
			String strINV_NO = "INVDP/"+intPR_No;
			String strDC_NO = "DCDP/"+intPR_No;
			
			Timestamp tsCurrentTS = Utility.getCurrentTimeStamp();
			
			//INSERT INTO DP_PR_HEADER(PR_NO, PR_DATE, PR_DIST_ID, EXT_DIST_ID, CIRCLE_ID, WEBSER_OP_FLAG, WEBSER_MSG, IS_ACTIVE, INACTIVE_DATE, CATEGORY_CODE) 
			//VALUES(?, ?, ?, ?, ?, 'BT', 'PO Created by DP', 2, ?, ?)
			psPRHdr = conDP.prepareStatement(DBQueries.INSERT_PR_HEADER);
			
			int intCircleID = Integer.parseInt(circleId);
			int intCatCode = 1;
			
			psPRHdr.setInt(1, intPR_No);
			psPRHdr.setTimestamp(2, tsCurrentTS);
			psPRHdr.setLong(3, lnDistID);
			psPRHdr.setString(4, strExtDistID);
			psPRHdr.setInt(5, intCircleID);
			psPRHdr.setTimestamp(6, tsCurrentTS);
			psPRHdr.setInt(7, intCatCode);
			
			int intUpd = psPRHdr.executeUpdate();
			
			if(intUpd>0)
				logger.info("Record inserted in DP_PR_HEADER for PR ::  "+intPR_No);
			else
				logger.info("Error in record insertion in DP_PR_HEADER for PR ::  "+intPR_No);
			
			DBConnectionManager.releaseResources(psPRHdr, null);
			logger.info("No. of Serial Numbers  ::  "+list.size());
			int prodId=0;
			
			Iterator itr = list.iterator();
			
			NonSerializedToSerializedDTO nonSerializedToSerializedDTO = null;
			Map<Integer, Integer> mapProd = new HashMap<Integer, Integer>();
			
			while (itr.hasNext()) 
			{
				logger.info("********start getting SerialNo****************");
				nonSerializedToSerializedDTO = (NonSerializedToSerializedDTO) itr.next();
				Integer intProdID = Integer.parseInt(nonSerializedToSerializedDTO.getProductId());
				
				if(mapProd.containsKey(intProdID))
				{
					int intProdQty = mapProd.get(intProdID);
					intProdQty++;
					mapProd.remove(intProdID);
					mapProd.put(intProdID, intProdQty);
				}
				else
				{
					mapProd.put(intProdID, 1);
				}
				
				nonSerializedToSerializedDTO = null;
				
				intProdID = null;
			}
			
			psPRDtl = conDP.prepareStatement(DBQueries.INSERT_PR_DETAIILS);
			
			Set <Entry<Integer, Integer>> se = mapProd.entrySet();
			
			Iterator<Entry<Integer, Integer>> itrProd = se.iterator();
			
			while(itrProd.hasNext())
			{
				Map.Entry<Integer, Integer> me = itrProd.next();
				
				Integer intProductID = me.getKey();
				Integer intQty = me.getValue();
				
				psPRDtl.setInt(1, intPR_No);
				psPRDtl.setInt(2, intProductID);
				psPRDtl.setInt(3, intQty);
				psPRDtl.setInt(4, intCircleID);
				psPRDtl.setTimestamp(5, tsCurrentTS);
				psPRDtl.setInt(6, intProductID);
				psPRDtl.setLong(7, lnDistID);
				psPRDtl.setInt(8, intProductID);
				psPRDtl.setLong(9, lnDistID);
				psPRDtl.setInt(10, intProductID);
				psPRDtl.setInt(11, intQty);
			
				intUpd = psPRDtl.executeUpdate();
			
				if(intUpd>0)
					logger.info("Record inserted in DP_PR_DETAILS for PR ::  "+intPR_No +"  and Product ID  ::  "+intProductID);
				else
					logger.info("Error in record insertion in DP_PR_DETAILS for PR ::  "+intPR_No +"  and Product ID  ::  "+intProductID);
			}
			
			DBConnectionManager.releaseResources(psPRDtl, null);
			
//			INSERT INTO DPDTH.PO_HEADER(PR_NO, PO_NO, PO_DATE, PO_STATUS, STATUS_DATE, CREATE_DATE, REMARKS, IS_ACTIVE, INACTIVE_DATE, BT_ACK_DATE, CATEGORY_CODE) 
//		    VALUES(?, ?, ?, 31, ?, ?, 'DP PO', 2, ?, ?, ?)
			psPOHdr = conDP.prepareStatement(DBQueries.INSERT_PO_HEADER);
			psPOHdr.setInt(1, intPR_No);
			psPOHdr.setString(2, strPO_NO);
			psPOHdr.setTimestamp(3, tsCurrentTS);
			psPOHdr.setTimestamp(4, tsCurrentTS);
			psPOHdr.setTimestamp(5, tsCurrentTS);
			psPOHdr.setTimestamp(6, tsCurrentTS);
			psPOHdr.setTimestamp(7, tsCurrentTS);
			psPOHdr.setInt(8, intCatCode);
			
			intUpd = psPOHdr.executeUpdate();
			
			if(intUpd>0)
				logger.info("Record inserted in PO_HEADER for PO ::  "+strPO_NO);
			else
				logger.info("Error in record insertion in PO_HEADER for PO ::  "+strPO_NO);
			
			DBConnectionManager.releaseResources(psPOHdr, null);
			
//			INSERT INTO DPDTH.PO_DETAILS(PO_NO, PO_QTY, CREATE_DATE, EXT_PRODUCT_ID, PRODUCT_ID) 
//		    VALUES(?, ?, ?, (select BT_PRODUCT_CODE from DP_PRODUCT_MASTER where PRODUCT_ID=?), ?)
			psPODtl = conDP.prepareStatement(DBQueries.INSERT_PO_DETAILS);
			
			itrProd = se.iterator();
			
			while(itrProd.hasNext())
			{
				Map.Entry<Integer, Integer> me = itrProd.next();
				
				Integer intProductID = me.getKey();
				Integer intQty = me.getValue();
				
				psPODtl.setString(1, strPO_NO);
				psPODtl.setInt(2, intQty);
				psPODtl.setTimestamp(3, tsCurrentTS);
				psPODtl.setInt(4, intProductID);
				psPODtl.setInt(5, intProductID);
			
				intUpd = psPODtl.executeUpdate();
				
				if(intUpd>0)
					logger.info("Record inserted in PO_DETAILS for PO ::  "+strPO_NO);
				else
					logger.info("Error in record insertion in PO_DETAILS for PO ::  "+strPO_NO);
			}
			
			DBConnectionManager.releaseResources(psPODtl, null);
			
//			INSERT INTO DPDTH.INVOICE_HEADER(PO_NO, INV_NO, INV_DATE, DC_NO, DC_DT, CREATE_DATE, STATUS, DIST_ACTION_DATE, ACCEPT_DATE, CATEGORY_CODE) 
//		    VALUES(?, ?, ?, ?, ?, ?, 'ACCEPT', ?, ?, ?)
			psInvHdr = conDP.prepareStatement(DBQueries.INSERT_INVOICE_HEADER);
			
			psInvHdr.setString(1, strPO_NO);
			psInvHdr.setString(2, strINV_NO);
			psInvHdr.setTimestamp(3, tsCurrentTS);
			psInvHdr.setString(4, strDC_NO);
			psInvHdr.setTimestamp(5, tsCurrentTS);
			psInvHdr.setTimestamp(6, tsCurrentTS);
			psInvHdr.setTimestamp(7, tsCurrentTS);
			psInvHdr.setTimestamp(8, tsCurrentTS);
			psInvHdr.setInt(9, intCatCode);
			
			intUpd = psInvHdr.executeUpdate();
			
			if(intUpd>0)
				logger.info("Record inserted in INVOICE_HEADER for PO ::  "+strPO_NO);
			else
				logger.info("Error in record insertion in INVOICE_HEADER for PO ::  "+strPO_NO);
			
			DBConnectionManager.releaseResources(psInvHdr, null);
			
			//INSERT INTO INVOICE_DETAILS(INV_NO, INV_QTY, CREATE_DATE, EXT_PRODUCT_ID, PRODUCT_ID, ACCEPTED_QTY)  
			//VALUES(?, ?, ?, (select BT_PRODUCT_CODE from DP_PRODUCT_MASTER where PRODUCT_ID=?), ?, ?)
			psInvDtl = conDP.prepareStatement(DBQueries.INSERT_INVOICE_DETAIL);
			
			itrProd = se.iterator();
			
			while(itrProd.hasNext())
			{
				Map.Entry<Integer, Integer> me = itrProd.next();
				
				Integer intProductID = me.getKey();
				Integer intQty = me.getValue();
				
				psInvDtl.setString(1, strINV_NO);
				psInvDtl.setInt(2, intQty);
				psInvDtl.setTimestamp(3, tsCurrentTS);
				psInvDtl.setInt(4, intProductID);
				psInvDtl.setInt(5, intProductID);
				psInvDtl.setInt(6, intQty);
				
				intUpd = psInvDtl.executeUpdate();
				
				if(intUpd>0)
					logger.info("Record inserted in INVOICE_DETAILS for INV ::  "+strINV_NO);
				else
					logger.info("Error in record insertion in INVOICE_DETAILS for INV ::  "+strINV_NO);
			}
			
			DBConnectionManager.releaseResources(psInvDtl, null);
			
//			INSERT INTO PO_STATUS_AUDIT_TRAIL(PO_NO, PO_STATUS, PO_STATUS_DATE, CREATED_ON, TAT) 
//		    VALUES(?, 31, ?, ?, 0)
			psPOAudTrl = conDP.prepareStatement(DBQueries.INSERT_PO_AUDIT_TRAIL);
			
			psPOAudTrl.setString(1, strPO_NO);
			psPOAudTrl.setTimestamp(2, tsCurrentTS);
			psPOAudTrl.setTimestamp(3, tsCurrentTS);
			
			intUpd = psPOAudTrl.executeUpdate();
			
			if(intUpd>0)
				logger.info("Record inserted in PO_STATUS_AUDIT_TRAIL for PO ::  "+strPO_NO);
			else
				logger.info("Error in record insertion in PO_STATUS_AUDIT_TRAIL for PO ::  "+strPO_NO);
			
			DBConnectionManager.releaseResources(psPOAudTrl, null);
			
			//INSERT INTO DP_STOCK_INVENTORY(PRODUCT_ID, SERIAL_NO, MARK_DAMAGED, DISTRIBUTOR_ID, DISTRIBUTOR_PURCHASE_DATE, INV_NO, REMARKS, STATUS) 
//		    VALUES(?, ?, 'N', ?, ?, ?, 'DP PO', 5)
			psStkInv = conDP.prepareStatement(DBQueries.INSERT_STOCK_INVENTORY);
			psUpdAVMst = conDP.prepareStatement(DBQueries.UPDATE_AV_MASTER);
			
			itr = list.iterator();
			
			nonSerializedToSerializedDTO = null;
			
			while (itr.hasNext()) 
			{
				nonSerializedToSerializedDTO = (NonSerializedToSerializedDTO) itr.next();
				String strSRNo = nonSerializedToSerializedDTO.getSerial_no();
				int intProductID = Integer.parseInt(nonSerializedToSerializedDTO.getProductId());
				
				logger.info("Serial_No  ::  "+strSRNo);
				logger.info("updating new serial number into dp av stock master table ");
				psStkInv.setInt(1, intProductID);
				psStkInv.setString(2, strSRNo);
				psStkInv.setLong(3, lnDistID);
				psStkInv.setTimestamp(4, tsCurrentTS);
				psStkInv.setString(5, strINV_NO);
				
				psStkInv.addBatch();
				
				psUpdAVMst.setString(1, strSRNo);
				psUpdAVMst.setInt(2, intCatCode);
				
				psUpdAVMst.addBatch();
			}
			
			int[] intUpdArr = psStkInv.executeBatch();
			
			logger.info("No of AV serial no. inserted  ::  "+intUpdArr.length);
			
			intUpdArr = psUpdAVMst.executeBatch();
			
			logger.info("No of AV serial no. updated in AV Master Stock  ::  "+intUpdArr.length);
			
			strReturn = strReturn + " against PR NO. : "+intPR_No+" and PO NO : "+strPO_NO;
		}
		catch (SQLException e) 
		{
			e.printStackTrace();
			
			while (e != null) 
			{
				logger.info("SQL State:" + e.getSQLState());
				logger.info("Error Code:" + e.getErrorCode());
				logger.info("Message:" + e.getMessage());
				
				logger.error("SQL State:" + e.getSQLState());
				logger.error("Error Code:" + e.getErrorCode());
				logger.error("Message:" + e.getMessage());
				Throwable t = e.getCause();
				while (t != null) 
				{
					logger.error("Cause:" + t);
					t = t.getCause();
				}
				e = e.getNextException();
			}
			
			try
			{
				conDP.rollback();
			}
			catch(Exception e2)
			{
				e2.printStackTrace();
			}
			throw new DAOException("Exception occured while inserting AV Stock  ::  "+e.getMessage());
		}
		catch(Exception e)
		{
			e.printStackTrace();
			logger.info(e.getMessage());
			logger.info(e);
			throw new DAOException("Exception occured while inserting AV Stock  ::  "+e.getMessage());
		}
		
		return strReturn;
	}


}
	

	


	
			           
			          
	  
