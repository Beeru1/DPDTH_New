package com.ibm.dp.dao.impl;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.StringTokenizer;
import org.apache.log4j.Logger;
import org.apache.struts.upload.FormFile;
import com.ibm.dp.beans.AvStockUploadFormBean;
import com.ibm.dp.common.Constants;
import com.ibm.dp.dao.AvStockUploadDao;
import com.ibm.dp.dto.DPPoAcceptanceBulkDTO;
import com.ibm.dp.dto.DuplicateSTBDTO;
import com.ibm.utilities.ValidatorUtility;
import com.ibm.virtualization.recharge.common.ResourceReader;
import com.ibm.virtualization.recharge.dao.rdbms.BaseDaoRdbms;
import com.ibm.virtualization.recharge.db.DBConnectionManager;
import com.ibm.virtualization.recharge.exception.DAOException;


public class AvStockUploadDaoImpl  extends BaseDaoRdbms implements AvStockUploadDao
{
	private static Logger logger = Logger.getLogger(AvStockUploadDaoImpl.class.getName());
	public ArrayList<DuplicateSTBDTO> uploadData(String stbSLNos) throws DAOException {
		// TODO Auto-generated method stub
		return null;
	}
	
	public AvStockUploadDaoImpl(Connection conn) {
		super(conn);
	}	
	public List uploadExcel(FormFile myXls) throws DAOException 
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
	        	  
	        	  isEmpty = false;
	        	  dpPoAcceptanceBulkDTO = new DPPoAcceptanceBulkDTO();
	        	  stb_no = st.nextToken(",\n\r").toString();
	        	  if(stb_no != null)
	        	  {
	        		  stb_no = stb_no.trim();
	        	  }
	        	  boolean validate = validateCell(stb_no);
	        	  
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
	      				String presentInDp = avStockPresentinDP(connection, stb_no );
	      				if( presentInDp != null && !presentInDp.equals(""))
	      				{
	      					dpPoAcceptanceBulkDTO.setSerial_no(stb_no+" ");
	          				dpPoAcceptanceBulkDTO.setErr_msg(presentInDp);
		              		Error_list.add(dpPoAcceptanceBulkDTO);
		              		validateFlag = false;
		              		logger.info("validate Error  ::"+validate);
		              	}
	      				else
	      				{
//	      					String newInDp = avNewStockPresentinDP(connection, stb_no );
//	      					if( newInDp != null && !newInDp.equals(""))
//	      					{
//	      						validateFlag = true;
//	      					}
	      					dpPoAcceptanceBulkDTO.setErr_msg("SUCCESS");
	      					checkDuplicate.add(stb_no);
	      					logger.info("validate Success  ::"+validate);
	      				}
	      			}
		          k++;
	          }
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
		logger.info("Serial No  ::  "+str);
		if(!ValidatorUtility.isValidNumber(str))
		{
			logger.info("Serial No not numeric  ::  "+str);
			return false;
		}
		
		if(str.length() != Constants.C2S_BULK_UPLOAD_NO_LENGTH_AV)
		{
			logger.info("Serial No length is not correct  ::  "+str.length());
			return false;
		}
		return true;
	}	
	public static String avStockPresentinDP(Connection conDP,String strSTBSerialNumber  )throws DAOException
	{
		String strReturn = "";
		
		PreparedStatement pstm = null;
		ResultSet rs =null;
		
		try
		{
			String strAvStock_MST = "SELECT SERIAL_NO from DP_AV_STOCK_MASTER WHERE SERIAL_NO =? union select SERIAL_NO from DP_STOCK_INVENTORY WHERE SERIAL_NO =? WITH UR";
			pstm = conDP.prepareStatement(strAvStock_MST);
			pstm.setString(1, strSTBSerialNumber);
			pstm.setString(2, strSTBSerialNumber);
			rs = pstm.executeQuery();
			logger.info("******************check serial number whether it exist previously or not*************");					
			if (rs.next()==true)
			{
				logger.info("**************This serial number exisis ****************");
				strReturn = "The Serial Number already exists ";
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
	
	public  String insertAVStockinDP(Connection conDP, List list)throws DAOException
	{
		String strReturn = "Serial Number Updated Successfully.";
		PreparedStatement pstm1 = null;
		ResultSet rs =null;
		String serialNo;
		DPPoAcceptanceBulkDTO dPPoAcceptanceBulkDTO = null;
		try
		{
			String strInsertNewAvStock = "INSERT INTO DP_AV_STOCK_MASTER(SERIAL_NO, CATEGORY_CODE, STATUS) VALUES(?, ?, 0) with ur";
			logger.info("********The Serial Number does not exists in DP_AV_STOCK_MASTER");
			pstm1 = conDP.prepareStatement(strInsertNewAvStock);
			Iterator itr =  list.iterator();
			
			int intCatCode = com.ibm.virtualization.recharge.common.Constants.PRODUCT_CATEGORY_CODE_AV;
			
			while (itr.hasNext()) 
			{
				logger.info("********start getting SerialNo****************");
				dPPoAcceptanceBulkDTO = (DPPoAcceptanceBulkDTO) itr.next();
				
				List<String> list_sr_no = dPPoAcceptanceBulkDTO.getList_srno();
				
				for(String strSRNo : list_sr_no)
				{
					logger.info("Serial_No  ::  "+strSRNo);
					logger.info("updating new serial number into dp av stock master table ");
					pstm1.setString(1, strSRNo);
					pstm1.setInt(2, intCatCode);
					
					pstm1.addBatch();
				}
			}
			
			int[] intUpd = pstm1.executeBatch();
			
		}
		catch (Exception e) 
		{
			e.printStackTrace();
			logger.info("Error in inserting AV Master stcok  ::  "+e.getMessage());
			logger.info("Error in inserting AV Master stcok  ::  "+e);
			strReturn = "error in serial number upload";
			throw new DAOException(e.getMessage());
		}
		finally
		{
			DBConnectionManager.releaseResources(pstm1, rs);
		}
		return strReturn;
	}
}
	


	
			           
			          
	  
