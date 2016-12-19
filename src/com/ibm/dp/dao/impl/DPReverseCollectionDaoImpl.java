package com.ibm.dp.dao.impl;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.apache.log4j.Logger;

import com.ibm.dp.common.DBQueries;
import com.ibm.dp.dao.DPReverseCollectionDao;
import com.ibm.dp.dto.DPPrintDCDTO;
import com.ibm.dp.dto.DPReverseCollectionDto;
import com.ibm.dp.dto.DpReverseInvetryChangeDTO;
//import com.ibm.icu.impl.data.ResourceReader;
import com.ibm.utilities.PropertyReader;
import com.ibm.utilities.Utility;
import com.ibm.virtualization.recharge.common.Constants;
import com.ibm.virtualization.recharge.common.ExceptionCode;
import com.ibm.virtualization.recharge.dao.rdbms.BaseDaoRdbms;
import com.ibm.virtualization.recharge.db.DBConnectionManager;
import com.ibm.virtualization.recharge.exception.DAOException;
import com.ibm.websphere.security.Result;
import com.ibm.ws.sip.security.auth.ThreadLocalStorage;

public class DPReverseCollectionDaoImpl extends BaseDaoRdbms implements DPReverseCollectionDao
{
	public static Logger logger = Logger.getLogger(DPReverseCollectionDaoImpl.class.getName());
	/*public static ThreadLocal<List<DPReverseCollectionDto>> threadLocal=new ThreadLocal<List<DPReverseCollectionDto>>(){ 
		protected List<DPReverseCollectionDto> initialValue(){
			return new ArrayList<DPReverseCollectionDto>();
		}
	};*/
		
	
	
	public DPReverseCollectionDaoImpl(Connection connection) 
	{
		super(connection);
	}
	
	public static final String SQL_COLLECTION_MST 	= DBQueries.SQL_COLLECTION_MST_WO_CHURN;
	public static final String GET_DEFECT_LIST 	= DBQueries.GET_DEFECT_LIST;
	public static final String GET_COLLECT_PRODUCT_LIST 	= DBQueries.GET_COLLECT_PRODUCT_LIST;
	public static final String GET_DC_BASIC_DETAILS 	= DBQueries.GET_DC_BASIC_DETAILS;
//	public static final String GET_DC_BASIC_DETAILS_FRESH 	= DBQueries.GET_DC_BASIC_DETAILS_FRESH;
	public static final String  GET_DC_CHURN_DETAILS=DBQueries.GET_DC_CHURN_DETAILS;
	public static final String  GET_DC_DETAILS_CHURN=DBQueries.GET_DC_DETAILS_CHURN;
//	public static final String GET_DC_DETAILS 	= DBQueries.GET_DC_DETAILS;
	public static final String GET_DC_DETAILS_FRESH 	= DBQueries.GET_DC_DETAILS_FRESH;
//	public static final String GET_DC_DETAILS_HIST 	= DBQueries.GET_DC_DETAILS_HIST;
	public static final String GET_DC_DETAILS_REVERSE = DBQueries.GET_DC_DETAILS_REVERSE;
//	public static final String GET_DC_DETAILS_HIST_FRESH = DBQueries.GET_DC_DETAILS_HIST_FRESH;
	public static final String SUBMIT_REVERSE_COLLECTION_DATA 	= DBQueries.SUBMIT_REVERSE_COLLECTION_DATA;
	public static final String VALIDATE_COLLECT_SERIAL_NO 	= DBQueries.VALIDATE_COLLECT_SERIAL_NO;
	public static final String VALIDATE_UNIQUE_COLLECT_SERIAL_NO 	= DBQueries.VALIDATE_UNIQUE_COLLECT_SERIAL_NO;
	public static final String VALIDATE_C2S_COLLECT_SERIAL_NO 	= DBQueries.VALIDATE_C2S_COLLECT_SERIAL_NO;
	public static final String VALIDATE_COLLECT_SERIAL_NO_ALL 	= DBQueries.VALIDATE_COLLECT_SERIAL_NO_ALL;
	public static final String SQL_STOCK_REVERSE_INVENTORY_INSERT 	= DBQueries.SQL_STOCK_REVERSE_INVENTORY_INSERT_UPGRADE;
	public static final String MOVE_RECORDS_STOCK_INVENTORY_DAO_SERIAL_NO 	= DBQueries.MOVE_RECORDS_STOCK_INVENTORY_DAO_SERIAL_NO;
	public static final String DELETE_DAO_SERIAL_NO_RECORDS_STOCK_INVENTORY  = DBQueries.DELETE_DAO_SERIAL_NO_RECORDS_STOCK_INVENTORY;
	//Added by Shilpa Khanna
	public static final String MOVE_RECORDS_STOCK_INVENTORY_SWAP_SERIAL_NO 	= DBQueries.MOVE_RECORDS_STOCK_INVENTORY_SWAP_SERIAL_NO;
	
	public static final String UPDATE_RECORDS_INVENTORY_CHANGE= DBQueries.UPDATE_RECORDS_INVENTORY_CHANGE;
	//
	public static final String GET_INVENTORY_CHANGE_LIST 	= DBQueries.GET_INVENTORY_CHANGE_LIST;
	
	
	
	
		
	// GET COLLECTION TYPE
	public List<DPReverseCollectionDto> getCollectionTypeMaster() throws DAOException 
	{
		List<DPReverseCollectionDto> reverseCollectionListDTO	= new ArrayList<DPReverseCollectionDto>();
		PreparedStatement pstmt = null;
		ResultSet rset			= null;
		DPReverseCollectionDto  reverseCollectionDto = null;
				
		try
		{
			pstmt = connection.prepareStatement(SQL_COLLECTION_MST);
			rset = pstmt.executeQuery();
			reverseCollectionListDTO = new ArrayList<DPReverseCollectionDto>();
			
			while(rset.next())
			{
				reverseCollectionDto = new DPReverseCollectionDto();
				reverseCollectionDto.setCollectionId(rset.getString("COLLECTION_ID"));
				reverseCollectionDto.setCollectionName(rset.getString("COLLECTION_NAME"));	
								
				reverseCollectionListDTO.add(reverseCollectionDto);
			}
			
		}
		catch (Exception e) 
		{
			e.printStackTrace();
			throw new DAOException(e.getMessage());
		}
		finally
		{
			pstmt = null;
			rset = null;
		}
		return reverseCollectionListDTO;
	}
	
	//GET DEFECT TYPE ON COLLECTION ID
	public List<DPReverseCollectionDto> getCollectionDefectType(int collectionId) throws DAOException 
	{
		List<DPReverseCollectionDto> reverseCollectionListDTO	= new ArrayList<DPReverseCollectionDto>();
		PreparedStatement pstmt = null;
		ResultSet rset			= null;
		DPReverseCollectionDto  reverseCollectionDto = null;
				
		try
		{
			pstmt = connection.prepareStatement(GET_DEFECT_LIST);
			pstmt.setInt(1, collectionId);
			rset = pstmt.executeQuery();
			reverseCollectionListDTO = new ArrayList<DPReverseCollectionDto>();
			
			while(rset.next())
			{
				reverseCollectionDto = new DPReverseCollectionDto();
				reverseCollectionDto.setDefectId(rset.getString("DEFECT_ID"));
				reverseCollectionDto.setDefectName(rset.getString("DEFECT_NAME"));	
								
				reverseCollectionListDTO.add(reverseCollectionDto);
			}
		}
		catch (Exception e) 
		{
			e.printStackTrace();
			throw new DAOException(e.getMessage());
		}
		finally
		{
			pstmt = null;
			rset = null;
		}
		return reverseCollectionListDTO;
	}
	
	
	public List<DPReverseCollectionDto> getProductCollection(String collectionId,int circleId) throws DAOException 
	{
		List<DPReverseCollectionDto> reverseCollectionListDTO	= new ArrayList<DPReverseCollectionDto>();
		PreparedStatement pstmt = null;
		ResultSet rset			= null;
		DPReverseCollectionDto  reverseCollectionDto = null;
				
		try
		{
			pstmt = connection.prepareStatement(GET_COLLECT_PRODUCT_LIST);
			
//			Commented by Nazim Hussain as this brings Product on Reverse Pending List screen based on Collection type, 
//			now products are populated for DOA BI only so Collection Type check is not required. 
//			Also DOA BI should include SWAP products as well (BFR-15, DP-Phase-3_08158)
			//pstmt.setString(1, collectionId);
			
		//	logger.info("TRANS_ID "+transId);
			pstmt.setInt(1, circleId);
			/*	if(null!= transId && transId.equals("3"))
				pstmt.setInt(2, 5);
			else*/
		//	pstmt.setInt(2, 1);
			rset = pstmt.executeQuery();
			
			reverseCollectionListDTO = new ArrayList<DPReverseCollectionDto>();
			
			while(rset.next())
			{
				reverseCollectionDto = new DPReverseCollectionDto();
				reverseCollectionDto.setProductId(rset.getString("PRODUCT_ID"));
				reverseCollectionDto.setProductName(rset.getString("PRODUCT_NAME"));	
				
				reverseCollectionListDTO.add(reverseCollectionDto);
			}
		}
		catch (Exception e) 
		{
			e.printStackTrace();
			throw new DAOException(e.getMessage());
		}
		finally
		{
			pstmt = null;
			rset = null;
		}
		return reverseCollectionListDTO;
	}
	
	public List<List> getDCDetails(String dc_no) throws DAOException 
	{
		List<List> listReturn = new ArrayList<List>();
		
		List<DPPrintDCDTO> dcInfoListDTO	= new ArrayList<DPPrintDCDTO>();
		List<DPPrintDCDTO> dcDetailsCollectionListDTO	= new ArrayList<DPPrintDCDTO>();
		List<DPPrintDCDTO> dcDetailsChurnPostFixDTO	= new ArrayList<DPPrintDCDTO>();
		
		PreparedStatement pstmt = null;		ResultSet rset			= null;
		PreparedStatement pstmt1 = null;		ResultSet rset1			= null;
		PreparedStatement pstmt2 = null;		ResultSet rset2			= null;
		String status="";
		DPPrintDCDTO  dcDetailsCollectionDto = null;
		String strDCType = "";
		try
		{
			//logger.info("DC NO  ::  "+dc_no);
			strDCType = dc_no.substring(0, 3);
			//logger.info("strDCType  ::  "+strDCType);
	
			dcDetailsCollectionListDTO = new ArrayList<DPPrintDCDTO>();
			dcDetailsChurnPostFixDTO = new ArrayList<DPPrintDCDTO>();
			SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
			SimpleDateFormat sdf1 = new SimpleDateFormat("yyyy-MM-dd");
	
			//Added by Shah
			if(strDCType.equals(com.ibm.dp.common.Constants.DC_TYPE_CHURN_PREFIX))	
			{
				pstmt = connection.prepareStatement(GET_DC_CHURN_DETAILS);
				pstmt.setString(1, dc_no);			
				rset = pstmt.executeQuery();
				if(rset.next())	
				{
					dcDetailsCollectionDto = new DPPrintDCDTO();
					dcDetailsCollectionDto.setDc_no(dc_no);
					dcDetailsCollectionDto.setFrom_name( (rset.getString("ACCOUNT_NAME") ) );
					dcDetailsCollectionDto.setCity_name(rset.getString("CITY_NAME") );
					dcDetailsCollectionDto.setState_name( (rset.getString("CIRCLE_NAME") ) );
					dcDetailsCollectionDto.setContact_name(rset.getString("CONTACT_NAME") );
					dcDetailsCollectionDto.setMobile_no(rset.getString("MOBILE_NUMBER") );
					dcDetailsCollectionDto.setRemarks(rset.getString("DC_REMARK"));
					dcDetailsCollectionDto.setWh_name(rset.getString("WH_NAME"));
					dcDetailsCollectionDto.setWh_address1(rset.getString("WH_ADDRESS1"));
					dcDetailsCollectionDto.setWh_address2(rset.getString("WH_ADDRESS2"));
					dcDetailsCollectionDto.setWh_phone(rset.getString("WH_PHONE"));
					dcDetailsCollectionDto.setCourier_agency(rset.getString("COURIER_AGENCY"));
					dcDetailsCollectionDto.setDocket_no(rset.getString("DOCKET_NO"));
					status = rset.getString("DC_STATUS");
					dcInfoListDTO.add(dcDetailsCollectionDto);
				}
			} 
			else 
			{	
				pstmt = connection.prepareStatement(GET_DC_BASIC_DETAILS);
				pstmt.setString(1, dc_no);			
				pstmt.setString(2, dc_no);
				
				rset = pstmt.executeQuery();
				
				if(rset.next())
				{
					dcDetailsCollectionDto = new DPPrintDCDTO();
					dcDetailsCollectionDto.setDc_no(dc_no);
					dcDetailsCollectionDto.setFrom_name( (rset.getString("ACCOUNT_NAME") ) );
					dcDetailsCollectionDto.setCity_name(rset.getString("CITY_NAME") );
					dcDetailsCollectionDto.setState_name( (rset.getString("CIRCLE_NAME") ) );
					dcDetailsCollectionDto.setContact_name(rset.getString("CONTACT_NAME") );
					dcDetailsCollectionDto.setMobile_no(rset.getString("MOBILE_NUMBER") );
					dcDetailsCollectionDto.setRemarks(rset.getString("REMARKS"));
					dcDetailsCollectionDto.setWh_name(rset.getString("WH_NAME"));
					dcDetailsCollectionDto.setWh_address1(rset.getString("WH_ADDRESS1"));
					dcDetailsCollectionDto.setWh_address2(rset.getString("WH_ADDRESS2"));
					dcDetailsCollectionDto.setWh_phone(rset.getString("WH_PHONE"));
					dcDetailsCollectionDto.setCourier_agency(rset.getString("COURIER_AGENCY"));
					dcDetailsCollectionDto.setDocket_no(rset.getString("DOCKET_NUMBER"));
					status = rset.getString("STATUS");
					dcInfoListDTO.add(dcDetailsCollectionDto);
				}
			}	
			listReturn.add(dcInfoListDTO);
			
			DBConnectionManager.releaseResources(pstmt, rset);
		
			if(strDCType.equals(com.ibm.dp.common.Constants.DC_TYPE_REVERSE_PREFIX))
			{				
				pstmt1 = connection.prepareStatement(GET_DC_DETAILS_REVERSE);
				pstmt1.setString(1, dc_no);
				pstmt1.setString(2, dc_no);
				rset1 = pstmt1.executeQuery();
				while(rset1.next())
				{
					dcDetailsCollectionDto = new DPPrintDCDTO();
					dcDetailsCollectionDto.setSerial_no( (rset1.getString("SERIAL_NO") ) );
					dcDetailsCollectionDto.setProduct_name(rset1.getString("PRODUCT_NAME") );	
					dcDetailsCollectionDto.setCollection_type( (rset1.getString("COLLECTION_NAME") ) );
					dcDetailsCollectionDto.setDefect_type(rset1.getString("DEFECT_NAME") ); 
					dcDetailsCollectionDto.setCollection_date(sdf.format(sdf1.parse(rset1.getString("COLLECTION_DATE"))));
					dcDetailsCollectionDto.setRemarks(rset1.getString("REMARKS") );
					dcDetailsCollectionListDTO.add(dcDetailsCollectionDto);
				}
			//Added by Shah
			} else if(strDCType.equals(com.ibm.dp.common.Constants.DC_TYPE_CHURN_PREFIX))
			{
				//****************** Updated by Shilpa on 9-8-2012 *****************
				
				//pstmt1 = connection.prepareStatement(GET_DC_DETAILS_CHURN);
				pstmt1 = connection.prepareStatement(DBQueries.GET_DC_DETAILS_CHURN+" and b.AGEING <= ? with ur");
				pstmt1.setString(1, dc_no);
				
				System.out.println("------"+com.ibm.virtualization.recharge.common.ResourceReader.getCoreResourceBundleValue(com.ibm.dp.common.Constants.CHURN_REFURBHISHMENT_AGEING));
				//pstmt1.setInt(2, com.ibm.dp.common.Constants.CHURN_REFURBHISHMENT_AGEING);
				pstmt1.setString(2, com.ibm.virtualization.recharge.common.ResourceReader.getCoreResourceBundleValue(com.ibm.dp.common.Constants.CHURN_REFURBHISHMENT_AGEING));
				
				
				
				
				rset1 = pstmt1.executeQuery();
				
				while(rset1.next())
				{
					dcDetailsCollectionDto = new DPPrintDCDTO();
					dcDetailsCollectionDto.setSerial_no( (rset1.getString("SERIAL_NUMBER") ) );
					dcDetailsCollectionDto.setProduct_name(rset1.getString("PRODUCT_NAME") );	
					dcDetailsCollectionDto.setVc_id(rset1.getString("VC_ID")); 
					dcDetailsCollectionDto.setCustomer_id(rset1.getString("CUSTOMER_ID")); 
					//dcDetailsCollectionDto.setDefect_type(rset1.getString("PRODUCT_NAME")); 
					dcDetailsCollectionDto.setAgeing(rset1.getString("AGEING")); 
					//dcDetailsCollectionDto.setCollection_date(sdf.format(sdf1.parse(rset1.getString("DC_DATE"))));
					dcDetailsCollectionDto.setCollection_date(rset1.getString("DC_DATE"));
					//dcDetailsCollectionDto.setCollection_date("23/02/2012");
					dcDetailsCollectionDto.setRemarks(rset1.getString("REMARK") );
					dcDetailsCollectionDto.setSi_id(rset1.getString("SI_ID") );
					
					dcDetailsCollectionListDTO.add(dcDetailsCollectionDto);
				}
				//****************** Updated by SHilpa Ends here **********************
				
//****************  Added by Shilpa on 9-8-2012 **************
				pstmt2 = connection.prepareStatement(DBQueries.GET_DC_DETAILS_CHURN+" and b.AGEING > ? with ur");
				pstmt2.setString(1, dc_no);
				//pstmt2.setInt(2, com.ibm.dp.common.Constants.CHURN_REFURBHISHMENT_AGEING);
				
				
				System.out.println("com.ibm.virtualization.recharge.common.ResourceReader.getCoreResourceBundleValue(com.ibm.dp.common.Constants.CHURN_REFURBHISHMENT_AGEING)::::::"+com.ibm.virtualization.recharge.common.ResourceReader.getCoreResourceBundleValue(com.ibm.dp.common.Constants.CHURN_REFURBHISHMENT_AGEING));
				pstmt2.setString(2, com.ibm.virtualization.recharge.common.ResourceReader.getCoreResourceBundleValue(com.ibm.dp.common.Constants.CHURN_REFURBHISHMENT_AGEING));
					
				rset2 = pstmt2.executeQuery();
				
				while(rset2.next())
				{
					dcDetailsCollectionDto = new DPPrintDCDTO();
					dcDetailsCollectionDto.setSerial_no( (rset2.getString("SERIAL_NUMBER") ) );
					dcDetailsCollectionDto.setProduct_name(rset2.getString("PRODUCT_NAME") );	
					dcDetailsCollectionDto.setVc_id(rset2.getString("VC_ID")); 
					dcDetailsCollectionDto.setCustomer_id(rset2.getString("CUSTOMER_ID")); 
					dcDetailsCollectionDto.setAgeing(rset2.getString("AGEING")); 
					dcDetailsCollectionDto.setCollection_date(rset2.getString("DC_DATE"));
					dcDetailsCollectionDto.setRemarks(rset2.getString("REMARK") );
					dcDetailsCollectionDto.setSi_id(rset2.getString("SI_ID") );
					
					dcDetailsChurnPostFixDTO.add(dcDetailsCollectionDto);
				}
				
				
//*****************  Added by SHilpa Ends here ****************************
			
			}	else	{
				pstmt1 = connection.prepareStatement(GET_DC_DETAILS_FRESH);
				pstmt1.setString(1, dc_no);
				pstmt1.setString(2, dc_no);
				
				rset1 = pstmt1.executeQuery();
				
				while(rset1.next())
				{
					dcDetailsCollectionDto = new DPPrintDCDTO();
					dcDetailsCollectionDto.setSerial_no( (rset1.getString("SERIAL_NO") ) );
					dcDetailsCollectionDto.setProduct_name(rset1.getString("PRODUCT_NAME") );	
					dcDetailsCollectionDto.setCollection_date(sdf.format(sdf1.parse(rset1.getString("RETURN_DATE"))));
					dcDetailsCollectionDto.setRemarks(rset1.getString("REMARKS") );
					dcDetailsCollectionListDTO.add(dcDetailsCollectionDto);
				}
			}

			listReturn.add(dcDetailsCollectionListDTO);
			 if(strDCType.equals(com.ibm.dp.common.Constants.DC_TYPE_CHURN_PREFIX))
				 listReturn.add(dcDetailsChurnPostFixDTO);

		}
		catch (Exception e) 
		{
			e.printStackTrace();
			logger.info("Exception while getting PRINT DC Page Data  ::  "+e.getMessage());
			throw new DAOException(e.getMessage());
		}
		finally
		{ 
			DBConnectionManager.releaseResources(pstmt ,rset );
			DBConnectionManager.releaseResources(pstmt1 ,rset1 );
		}
		return listReturn;
	}
		
	public List<DPReverseCollectionDto> submitReverseCollection(List<DPReverseCollectionDto> reverseCollectionListDTO, int circleId, long  accountId) throws DAOException
	{

		PreparedStatement pstmt = null;
		PreparedStatement pstmtNext = null;
		PreparedStatement pstmtDAO = null;
		PreparedStatement pstmtDAODelete = null;
		PreparedStatement pstmtInvHist = null;
		DPReverseCollectionDto  reverseCollectionDto = null;
		PreparedStatement pstmtSWAP = null;
		PreparedStatement pstmtUpdateInvChange= null;
		PreparedStatement psmt3= null;
//		PreparedStatement psmt4= null;
		PreparedStatement pstmtSelect= null;
		PreparedStatement pstmtInvDel = null;
		ResultSet rset3			= null; 
		String collectionStatus = Constants.COLLECTION_STATUS_TYPE_COL;
		String collectionInventStatus = Constants.COLLECTION_STATUS_TYPE_COL;
		int accID = Integer.parseInt(String.valueOf(accountId));
		int collectionId = 0;
		List<DPReverseCollectionDto> revData=new ArrayList<DPReverseCollectionDto>();
		
		try
		{
			
			pstmtSelect=connection.prepareStatement("SELECT count(*) FROM DPDTH.DP_REV_STOCK_INVENTORY where SERIAL_NO_COLLECT=? and PRODUCT_ID=? and CREATED_BY=?");
			pstmt = connection.prepareStatement(SUBMIT_REVERSE_COLLECTION_DATA);
			pstmtNext = connection.prepareStatement(SQL_STOCK_REVERSE_INVENTORY_INSERT);
			pstmtDAO = connection.prepareStatement(MOVE_RECORDS_STOCK_INVENTORY_DAO_SERIAL_NO);			
			pstmtDAODelete = connection.prepareStatement(DELETE_DAO_SERIAL_NO_RECORDS_STOCK_INVENTORY);
			pstmtSWAP= connection.prepareStatement(MOVE_RECORDS_STOCK_INVENTORY_SWAP_SERIAL_NO);		
			pstmtUpdateInvChange = connection.prepareStatement(UPDATE_RECORDS_INVENTORY_CHANGE);
//			psmt3 = connection.prepareStatement(DBQueries.SQL_STOCK_INVENTORY_ASSIGN_CHECK);
//			psmt4 = connection.prepareStatement(DBQueries.SQL_STOCK_INVENTORY_ASSIGN_UPDATE);
			pstmtInvHist = connection.prepareStatement(DBQueries.INSERT_INV_CHANGE_HIST);
			pstmtInvDel = connection.prepareStatement(DBQueries.DELETE_INV_CHANGE);
			Iterator<DPReverseCollectionDto> iter = reverseCollectionListDTO.iterator();
			///threadLocal=new ThreadLocal<List<DPReverseCollectionDto>>();
			boolean upgrade=false;
			logger.info("Thread is -"+Thread.currentThread().getId());
			
			while(iter.hasNext())
			{
				
				
				// Insert records into DP_STOCK_INVENTORY IN CASE OF COLLECTION TYPE 'churn, upgrade'
				reverseCollectionDto = (DPReverseCollectionDto) iter.next();
				//logger.info(reverseCollectionDto.getCollectionId()+" Collection ");
				collectionId =  Integer.parseInt(reverseCollectionDto.getCollectionId());
				reverseCollectionDto.setFlagCheck("false");
				
				
				if(collectionId == Constants.COLLECTION_TYPE_UPGRADE)
				{
					//collectionStatus = Constants.COLLECTION_STATUS_TYPE_REU;
					collectionStatus ="UPG";
					//added by varun
					//if(reverseCollectionDto.getManuIdKey()=="Y"){
					pstmtNext.setInt(1, Integer.parseInt(reverseCollectionDto.getProductId()));
					pstmtNext.setString(2, reverseCollectionDto.getSerialNo());
					pstmtNext.setInt(3, accID);
					psmt3= connection.prepareStatement("select DEFECTIVE_SR_NO from DP_REV_INVENTORY_CHANGE drsc ,  DP_MANUFACTURER_DETAILS dmd  where  drsc.MODEL_MAN_KEY_OLD=dmd.MANUFACTURER_ID   and drsc.DEFECTIVE_SR_NO = "+reverseCollectionDto.getSerialNo()+" and dmd.SELECTION_FLAG = 'N'");	
					
					rset3= psmt3.executeQuery();
					if(rset3.next()){	
						//flagCheckStatus.add(true);
						reverseCollectionDto.setFlagCheck("true");
						
											
					//pstmtNext.addBatch();
					pstmtNext.executeUpdate();
					
					
					System.out.println("FLAG--->"+reverseCollectionDto.getFlagCheck());
					}
					//revData.add(reverseCollectionDto);
				}
				//}
//				 Update by harbans on Reverse Enhancement.
				else if(collectionId == Constants.COLLECTION_TYPE_DOA_BEFORE_INST)
				{
					// Move DAO reverse collected exist in Stock inventory with Unassign Pending 'OR' Unassign Complete.
					collectionStatus = Constants.COLLECTION_STATUS_TYPE_COL;
					
					pstmtDAO.setInt(1, Integer.parseInt(reverseCollectionDto.getProductId()));
					pstmtDAO.setString(2, reverseCollectionDto.getSerialNo());
					pstmtDAO.setInt(3, accID);
					pstmtDAO.executeUpdate();
					
					// Delete DAO reverse collected exist in Stock inventory with Unassign Pending 'OR' Unassign Complete.
					pstmtDAODelete.setInt(1, Integer.parseInt(reverseCollectionDto.getProductId()));
					pstmtDAODelete.setString(2, reverseCollectionDto.getSerialNo());
					pstmtDAODelete.executeUpdate();
					
				}
				//Added by Shilpa Khanna 23-09-2011 to make activated stock as DOA in case partner declares DOA
				//Commented by Nazim Hussain as DOA is done against new STB activated
				/*else if(collectionId == Constants.COLLECTION_TYPE_DEAD_ON_ARRIVAL_AFTER_INST)
				{
					//Check the availability of serial no. in Stock inventory assign
					psmt3.setString(1, reverseCollectionDto.getSerialNo() );
					rset3 = psmt3.executeQuery();
					if(rset3.next()){
						String serialNo = rset3.getString("SERIAL_NO");
						String productId = rset3.getString("PRODUCT_ID");
						String distId = rset3.getString("DISTRIBUTOR_ID");
						//update Status in Stock Inventory Assigned
						psmt4.setString(1, productId);
						psmt4.setString(2, serialNo);
						psmt4.setString(3, distId);
						psmt4.executeUpdate();
						//logger.info("Update Stock inventory assign successful ");
						
					}
					
				}*/
				//Added by SHilpa Khanna
				else if(collectionId == Constants.COLLECTION_TYPE_DEFFECTIVE)
				{
					//logger.info("in Swap Type . Moving Serial no from Stock Inventory to Stock Inventory Assign");
					collectionStatus = Constants.COLLECTION_STATUS_TYPE_COL;
//					 Move SWAP reverse collected exist in Stock inventory with Unassign Pending 'OR' Unassign Complete.
					pstmtSWAP.setInt(1, Integer.parseInt(reverseCollectionDto.getProductId()));
					pstmtSWAP.setString(2, reverseCollectionDto.getSerialNo());
					pstmtSWAP.setInt(3, accID);
					pstmtSWAP.executeUpdate();
					
					// Delete SWAP reverse collected exist in Stock inventory with Unassign Pending 'OR' Unassign Complete.
					pstmtDAODelete.setInt(1, Integer.parseInt(reverseCollectionDto.getProductId()));
					pstmtDAODelete.setString(2, reverseCollectionDto.getSerialNo());
					pstmtDAODelete.executeUpdate();
				}
				else
				{
					collectionStatus = Constants.COLLECTION_STATUS_TYPE_COL;
					
				}
				
				Integer intNewProductId =0;
				Date invDate = null;
				
				if(!reverseCollectionDto.getNewProductId().equals(""))
				{
					intNewProductId =Integer.parseInt(reverseCollectionDto.getNewProductId());
				}
//				Insert records into DP_STOCK_INVENTORY_REVERSE table
				
				ResultSet rset=null;
				pstmtSelect.setString(1, reverseCollectionDto.getSerialNo());
				pstmtSelect.setInt(2, Integer.parseInt(reverseCollectionDto.getProductId()));
				pstmtSelect.setInt(3, accID);
				int count =0;
				rset=pstmtSelect.executeQuery();
				if(rset.next())
				{
					count=rset.getInt(1);
				}
				if(count!=0)
				{
					logger.info("throwing exception");
					throw new DAOException(ExceptionCode.ERROR_STOCK_RCVD);
					
				}
				//logger.info("reverseCollectionDto.getCollectionId()  ::  "+reverseCollectionDto.getCollectionId());
				pstmt.setInt(1, Integer.parseInt(reverseCollectionDto.getCollectionId()));
				//logger.info("reverseCollectionDto.getDefectId()  ::  "+reverseCollectionDto.getDefectId());
				pstmt.setString(2, reverseCollectionDto.getDefectId());
				//logger.info("reverseCollectionDto.getProductId()  ::  "+reverseCollectionDto.getProductId());
				pstmt.setInt(3, Integer.parseInt(reverseCollectionDto.getProductId()));
				//logger.info("reverseCollectionDto.getSerialNo()  ::  "+reverseCollectionDto.getSerialNo());
				pstmt.setString(4, reverseCollectionDto.getSerialNo());
				//logger.info("reverseCollectionDto.getNewSrno()  ::  "+reverseCollectionDto.getNewSrno());
				pstmt.setString(5, reverseCollectionDto.getNewSrno());
				//logger.info("reverseCollectionDto.getRemarks()  ::  "+reverseCollectionDto.getRemarks());
				pstmt.setString(6, reverseCollectionDto.getRemarks());
				//logger.info("accID  ::  "+accID);
				pstmt.setInt(7, accID);
				//logger.info("circleId  ::  "+circleId);
				pstmt.setInt(8, circleId);
				 logger.info("reverseCollectionDto.isFlagCheck()  ::  "+reverseCollectionDto.getFlagCheck());
				if(reverseCollectionDto.getFlagCheck().equals("true")){
				pstmt.setString(9, "REU");
				}
				else if(reverseCollectionDto.getFlagCheck().equals("false")){
				pstmt.setString(9, collectionStatus);
				}
				//logger.info("intNewProductId  ::  "+intNewProductId);
				pstmt.setInt(10,intNewProductId);
				//logger.info("reverseCollectionDto.getInventryChngDate()  ::  "+reverseCollectionDto.getInventryChngDate());
				if(reverseCollectionDto.getInventryChngDate() != null && !reverseCollectionDto.getInventryChngDate().equals("")) {
					invDate = Utility.getSqlDateFromString(reverseCollectionDto.getInventryChngDate(), "MM/dd/yyyy");
					pstmt.setDate(11, new java.sql.Date(invDate.getTime()));
				}
				//logger.info("reverseCollectionDto.getCollectionId()  ::  "+reverseCollectionDto.getCollectionId());
				if(reverseCollectionDto.getCollectionId().equalsIgnoreCase("2")){
					pstmt.setDate(11, new java.sql.Date(new java.util.Date().getTime()));
				}
				//logger.info("reverseCollectionDto.getCustomerId()  ::  "+reverseCollectionDto.getCustomerId());
				pstmt.setString(12, reverseCollectionDto.getCustomerId());
				//logger.info("reverseCollectionDto.getReqId()  ::  "+reverseCollectionDto.getReqId());
				pstmt.setLong(13, Long.parseLong(reverseCollectionDto.getReqId()));
				
				pstmt.executeUpdate();
				//pstmt.addBatch();
				
				
				
				/*****Update status in Inv Change table added by Naveen********/
				if(reverseCollectionDto.getCollectionId().equals("1") || reverseCollectionDto.getCollectionId().equals("3") || reverseCollectionDto.getCollectionId().equals("5") || reverseCollectionDto.getCollectionId().equals("6"))
				{
					pstmtUpdateInvChange.setString(1, collectionInventStatus);
					pstmtUpdateInvChange.setString(2, reverseCollectionDto.getSerialNo() );
					pstmtUpdateInvChange.setString(3, reverseCollectionDto.getProductId());
					
					pstmtUpdateInvChange.executeUpdate();
					
					if(reverseCollectionDto.getCollectionId().equals("5"))
					{
						pstmtInvHist.setString(1, reverseCollectionDto.getSerialNo());
						pstmtInvHist.setString(2, reverseCollectionDto.getProductId());
						
						pstmtInvHist.execute();
						
						pstmtInvDel.setString(1, reverseCollectionDto.getSerialNo());
						pstmtInvDel.setString(2, reverseCollectionDto.getProductId());
						
						pstmtInvDel.execute();
					}
				}
				if(reverseCollectionDto.getCollectionId().equals("5"))
					revData.add(reverseCollectionDto);
				
			}
		/*	if(upgrade)
			{
				threadLocal.set(revData);
			}
			else
				threadLocal.remove();*/
			
			//int[] numNext = pstmtNext.executeBatch();
			//logger.info("No of records submit on reverse collection into stock inventory  " + numNext.length);
			
			//int[] num = pstmt.executeBatch();
			//logger.info("No of records submit on reverse collection " + num.length);
			connection.commit();
		}
		
		catch (Exception e) 
		{
			e.printStackTrace();
		//	threadLocal.remove();
			throw new DAOException(e.getMessage());
		}
		finally
		{
			DBConnectionManager.releaseResources(pstmt, null);
			DBConnectionManager.releaseResources(pstmtDAO, null);
			DBConnectionManager.releaseResources(pstmtNext, null);
			DBConnectionManager.releaseResources(pstmtSWAP, null);
			DBConnectionManager.releaseResources(pstmtDAODelete, null);
			DBConnectionManager.releaseResources(pstmtUpdateInvChange, null);
//			DBConnectionManager.releaseResources(psmt3, rset3);
//			DBConnectionManager.releaseResources(psmt4, null);
			DBConnectionManager.releaseResources(pstmtInvDel, null);
			DBConnectionManager.releaseResources(pstmtInvHist, null);
		}
		return revData;
	}
	
	
	public String validateCollectSerialNo(int productId, String serialNo) throws DAOException 
	{
		PreparedStatement pstmt = null;
		ResultSet rset			= null;
		DPReverseCollectionDto  reverseCollectionDto = null;
		String errorMsg = ""; 
		int status = 0;
		int dateDiff = 999;
		
		try
		{
//			Update by harbans on Reverse Enhancement.

			pstmt = connection.prepareStatement(VALIDATE_COLLECT_SERIAL_NO);
			pstmt.setString(1, serialNo);
			pstmt.setString(2, serialNo);
			rset = pstmt.executeQuery();
			if(rset.next())
			{
				status = rset.getInt("STATUS");
				dateDiff = rset.getInt("DATEDIFF");
			}
			  
		
//			Step1: Check serial on stock Inventory with Restricted state.
			if(status == Constants.STOCK_STATUS_RESTRICTED)
			{
				errorMsg = "STB_RESTRICTED";
			}
//			Step2: Check serial on stock Inventory with unassigned (InComplete - complete) state.
			else if(status == Constants.STOCK_STATUS_UNASSIGNED_COMPLETE || status == Constants.STOCK_STATUS_UNASSIGNED_PENDING)
			{
				errorMsg = "STB_UNASSIGNED";
			}
//			Step3: Check serial on stock Inventory with Assigned state.
			else if(status == Constants.STOCK_STATUS_ASSIGNED)
			{
				if(dateDiff > 30)
				{
					errorMsg = "STB_ASSIGNED_NOT_VALID";
				}else
				{
					errorMsg = "STB_ASSIGNED_VALID";
				}
			}
//			Step4: Check serial not available in stock inventory both.
			else
			{
				errorMsg = "STB_NOT_AVAIABLE";
			}
		}
		catch (Exception e) 
		{
			e.printStackTrace();
			throw new DAOException(e.getMessage());
		}
		finally
		{
			pstmt = null;
			rset = null;
		}
		return errorMsg;
	}
	
	
	public boolean validateUniqueCollectSerialNo(int productId, String serialNo, String accId) throws DAOException 
	{
		PreparedStatement pstmt = null;
		ResultSet rset			= null;
		DPReverseCollectionDto  reverseCollectionDto = null;
		boolean flag  = false;
		
		try
		{
			logger.info("in try::::validateUniqueCollectSerialNo");
			pstmt = connection.prepareStatement(VALIDATE_UNIQUE_COLLECT_SERIAL_NO);
			
			//pstmt.setInt(1, productId);
			pstmt.setString(1, serialNo);
			pstmt.setInt(2, Integer.parseInt(accId));
			logger.info("b4 query executed");	
			rset = pstmt.executeQuery();
			logger.info("after executre query");
			
			if(rset.next())
				flag = true;
		
		}
		catch (Exception e) 
		{
			e.printStackTrace();
			throw new DAOException(e.getMessage());
		}
		finally
		{
			logger.info("finally::::validateUniqueCollectSerialNo");
			pstmt = null;
			rset = null;
		}
		return flag;
	}
	
//	 Update by harbans on Reverse Enhancement.
	public boolean validateC2SType(String serialNo)  throws DAOException
	{
		PreparedStatement pstmt = null;
		ResultSet rset			= null;
		boolean flag  = false;
		
		try
		{
			pstmt = connection.prepareStatement(VALIDATE_C2S_COLLECT_SERIAL_NO);
			pstmt.setString(1, serialNo);
			
			rset = pstmt.executeQuery();
			
			if(rset.next())
				flag = true;
		
		}
		catch (Exception e) 
		{
			e.printStackTrace();
			throw new DAOException(e.getMessage());
		}
		finally
		{
			pstmt = null;
			rset = null;
		}
		return flag;
	}

	public String validateSerialNumberDAO(String strSerialNo, Integer intCollectionID, Integer intProductID) 
	throws DAOException 
	{
		String strReturn = com.ibm.dp.common.Constants.SUCCESSCAPS;
		PreparedStatement pstmt = null;
		ResultSet rset = null;
		PreparedStatement pstmt1 = null;
		ResultSet rset1 = null;
		PreparedStatement pstmt2 = null;
		ResultSet rset2 = null;
		String strFSEID = null;
		String strProductType = null;
		
		boolean flag=false;
		try
		{
			switch(intCollectionID)
			{
				case com.ibm.dp.common.Constants.COLLECTION_SWAP:
				{
					//logger.info("In Case == SWAP");
					Integer productId = 0;
					Integer parentProductId =-1;//Added by Shilpa Khanna
					Integer productType =-1;
					pstmt = connection.prepareStatement(DBQueries.STOK_INV_CHK);
					pstmt.setString(1, strSerialNo);
					
					rset = pstmt.executeQuery();
					if(rset.next())
					{
						productId = rset.getInt("PRODUCT_ID");
						productType = rset.getInt("PRODUCT_TYPE");
						flag=true;
					}
					
					//Check for Serial no. in DP_STOCK_INVENTORY
					if(flag==true){
						//logger.info("Serial no. found in Stock_Inventory Product ID == "+ productId + " and Product Type == "+productType);
						//Check whether product type == SWAP. 0 for SWAP and 1 for Commercial
						if(productType==0)
						{
							if(productId.equals(intProductID))
							{
								//logger.info("In if cond of Query productId "+ productId + "= =  "  +  intProductID +   " Selected Product ID ");
								strReturn = com.ibm.dp.common.Constants.SUCCESSCAPS;	
							}
							else
							{
								//logger.info("In else cond of Query productId "+ productId + "! =  "  +  intProductID +   " Selected Product ID ");
								return com.ibm.dp.common.Constants.REV_COLL_MSG_INVALID_PRODUCT;
							}
						}
						else
						{
							return com.ibm.dp.common.Constants.REV_COLL_MSG_SRNO_NOT_ASSIG_COMM;
						}
						
					}
					else
					{
						pstmt1 = connection.prepareStatement(DBQueries.STOK_INV_ASSIGN_CHK);
						pstmt1.setString(1, strSerialNo);
						
						rset1 = pstmt1.executeQuery();
						//Check for Serail No. in DP_STOCK_INVENTORY_ASSIGNED
						if(rset1.next())
						{
							productId = rset1.getInt("PRODUCT_ID");
							parentProductId = rset1.getString("PM_PARENT_ID")!= null?rset1.getInt("PM_PARENT_ID"):-1;
							flag=true;
						}
						if(flag==true)
						{
							// Check for Product type 
							//logger.info(" Query productId === "+productId+"  **AND** Selected intProductID=="+intProductID + "   ***** parentProductId == "+parentProductId);
							//Added by Shilpa Khanna for Product mapping check
							if(parentProductId!=-1)
							{
								if((productId.equals(intProductID)) || (parentProductId.equals(intProductID)))
								{
									//Check in Rev_stock Inventory and Rev_Stock Inventory History
									//logger.info("In if cond of parentProductId != -1 === "+ parentProductId);
									strReturn = alreadyReceivedCheck(strSerialNo,"SWAP");
								}
								else
								{
									//logger.info("In else cond of parentProductId != -1 === "+ parentProductId);
									return com.ibm.dp.common.Constants.REV_COLL_MSG_INVALID_PRODUCT;
								}
							}
							else
							{
								if(productId.equals(intProductID))
								{
									//Check in Rev_stock Inventory and Rev_Stock Inventory History
									//logger.info("In if cond of parentProductId === -1 === intProductID == "+ intProductID);
									strReturn = alreadyReceivedCheck(strSerialNo,"SWAP");
								}
								else
								{
									//logger.info("In else cond of parentProductId ==== -1 === ");
									return com.ibm.dp.common.Constants.REV_COLL_MSG_INVALID_PRODUCT;
								}
							}
						}
						else
						{
							//logger.info("In 2nd else");
							strReturn =alreadyReceivedCheck(strSerialNo,"SWAP");
						}
					}
					break;
				}
				case com.ibm.dp.common.Constants.COLLECTION_DOA:
				{
					//logger.info("In Case == DAO");
					Integer productId = 0;
					Integer intDaysValid = 0;	//Added by nazim hussain to add 30 day validation check
					Integer parentProductId =-1;//Added by Shilpa Khanna
					pstmt = connection.prepareStatement(DBQueries.STOK_INV_CHK);
					pstmt.setString(1, strSerialNo);
					
					rset = pstmt.executeQuery();
					//Check for Serial no. in DP_STOCK_INVENTORY
					if(rset.next())
					{
						productId = rset.getInt("PRODUCT_ID");
						//Added by Shilpa Khanna
						parentProductId = rset.getString("PM_PARENT_ID")!= null?rset.getInt("PM_PARENT_ID"):-1;
						
						//logger.info(" Query productId === "+productId+"  **AND** Selected intProductID=="+intProductID + "   ***** parentProductId == "+parentProductId);
						
						//Added by Nazim Hussain to not to allow Restricted stock in DOA this need to be commented for Business
//						strFSEID = rset.getString("FSE_ID");
//						
//						if((strFSEID==null || strFSEID.trim().equals("")))
//						{
//							return "Serial Number is in Restricted Status in Stock Inventory. First Perform Secondary sale for the STB";
//						}
						//Code addition ends here
						//Added by Nazim hussain to restrict SWAP Box in DOA
						strProductType = rset.getString("PRODUCT_TYPE").trim();
						//if(Constants.COLLECTION_PRODUCT_TYPE_SWP.equals(strProductType)) removed to allow SWAP type for BI
							//return com.ibm.dp.common.Constants.REV_COLL_MSG_SWAP_STB_DOA;
						//code ends here 
						flag=true;
					}
					
					if(flag==true)
					{
						//Added by Shilpa Khanna;
						//logger.info("In Falg  == true");
						
						if(parentProductId!=-1)
						{
							if((productId.equals(intProductID)) || (parentProductId.equals(intProductID)))
							{
								//logger.info("In If condition of parentProductId!=-1");
								strReturn = com.ibm.dp.common.Constants.SUCCESSCAPS;
							}
							else
							{
								//logger.info("In else condition of parentProductId!=-1");
								return com.ibm.dp.common.Constants.REV_COLL_MSG_INVALID_PRODUCT;
						    }
						}
						else
						{
							if(productId.equals(intProductID))
							{
								//logger.info("In If condition of parentProductId == -1");
								strReturn = com.ibm.dp.common.Constants.SUCCESSCAPS;
							}							
							else
							{
								//logger.info("In else condition of parentProductId == -1");
								return com.ibm.dp.common.Constants.REV_COLL_MSG_INVALID_PRODUCT;
							}
						}
					}
					else
					{
						
						//Added by Shilpa Khanna
						return com.ibm.dp.common.Constants.REV_COLL_MSG_INVALID_SRNO;
						
						//Commented by Shilpa Khanna
						/*
						logger.info("In else condition of STOK_INV_ASSIGN_DOA_CHK");
						
						
						pstmt1 = connection.prepareStatement(DBQueries.STOK_INV_ASSIGN_DOA_CHK);
						pstmt1.setString(1, strSerialNo);
						
						rset1 = pstmt1.executeQuery();
						//Check for Serail No. in DP_STOCK_INVENTORY_ASSIGNED
						if(rset1.next())
						{
							productId = rset1.getInt("PRODUCT_ID");
//							Added by Shilpa Khanna for product mapping check
							parentProductId = rset1.getString("PM_PARENT_ID")!= null?rset1.getInt("PM_PARENT_ID"):-1;
							
							//Added by nazim hussain to add 30 days validation check
							intDaysValid = rset1.getInt("CASE");
							
							if(intDaysValid!=null && intDaysValid==2)
							{
								return com.ibm.dp.common.Constants.REV_COLL_MSG_DOA_30_DAYS;
							}
							//code ends here
							
							//Added by Nazim hussain to restrict SWAP Box in DOA
							strProductType = rset1.getString("PRODUCT_TYPE").trim();
							if(Constants.COLLECTION_PRODUCT_TYPE_SWP.equals(strProductType))
								return com.ibm.dp.common.Constants.REV_COLL_MSG_SWAP_STB_DOA;
							//code ends here 
							
							flag=true;
						}
						if(flag==true)
						{
							// Check for Product type 
//							Added by Shilpa Khanna for product mapping check
							
							if(parentProductId!=-1)
							{
								if((productId.equals(intProductID)) || (parentProductId.equals(intProductID)))
								{
									logger.info("In If condition of parentProductId!=-1");
									strReturn =com.ibm.dp.common.Constants.SUCCESSCAPS;
								}
								else
								{
									logger.info("In else condition of parentProductId!=-1");
									return strReturn =com.ibm.dp.common.Constants.REV_COLL_MSG_INVALID_PRODUCT;
							    }
							}
							else
							{
								if(productId.equals(intProductID))
								{
									logger.info("In If condition of parentProductId == -1");
									strReturn =com.ibm.dp.common.Constants.SUCCESSCAPS;
								}
								else
								{
									logger.info("In else condition of parentProductId == -1");
									return strReturn =com.ibm.dp.common.Constants.REV_COLL_MSG_INVALID_PRODUCT;
								}
							}
							
							strReturn = alreadyReceivedCheck(strSerialNo,"DOA");
							
							if(!strReturn.equals(com.ibm.dp.common.Constants.SUCCESSCAPS))
								return strReturn;
							
						}
						else
						{
							return com.ibm.dp.common.Constants.REV_COLL_MSG_INVALID_SRNO;
						}*/
						
						//Comment by shilpa Ends Here 
					}
					break;
				}
				case com.ibm.dp.common.Constants.COLLECTION_C2S:
				{
					//logger.info("In Case == C2S");
					pstmt = connection.prepareStatement(DBQueries.C2S_BULK_UPLOAD_CHK);
					pstmt.setString(1, strSerialNo);
//					Added by Shilpa Khanna for Stock_Inventory_Assign Check
					pstmt1 = connection.prepareStatement(DBQueries.STOK_INV_ASSIGN_CHK_C2S);
					pstmt1.setString(1, strSerialNo);
					Integer productId = 0;
					Integer parentProductId =-1;
					
					//Added by Nazim Hussain to restrict Serial Numbers in Stock Inventory
					pstmt2 = connection.prepareStatement(DBQueries.STOK_INV_CHK);
					pstmt2.setString(1, strSerialNo);
					rset2 = pstmt2.executeQuery();
					if(rset2.next())
						return com.ibm.dp.common.Constants.REV_COLL_MSG_INVALID_SRNO_C2S;
					
					rset = pstmt.executeQuery();
					//Updated by Shilpa Khanna for Stock_Inventory_Assign Check
					if(rset.next()){
						//Check for Stock_Inventory_Assign
						rset1 = pstmt1.executeQuery();
						
						if(rset1.next())
						{
							productId = rset1.getInt("PRODUCT_ID");
							//Added by Shilpa Khanna
							parentProductId = rset1.getString("PM_PARENT_ID")!= null?rset1.getInt("PM_PARENT_ID"):-1;
							
							//logger.info(" Query productId === "+productId+"  **AND** Selected intProductID=="+intProductID + "   ***** parentProductId == "+parentProductId);
							flag=true;
						}
						
						
						if(flag==true){
							
							//logger.info("In If condition of C2S Found in Stock_Inventory_Assign");
							if(parentProductId!=-1){
								if((productId.equals(intProductID)) || (parentProductId.equals(intProductID)))
								{
									//logger.info("In If condition of C2S parentProductId!=-1");
									strReturn =com.ibm.dp.common.Constants.SUCCESSCAPS;
								}else{
									//logger.info("In else condition of parentProductId!=-1");
									return strReturn =com.ibm.dp.common.Constants.REV_COLL_MSG_INVALID_PRODUCT;
							    	}
							}else{
								if(productId.equals(intProductID)){
									//logger.info("In If condition of parentProductId == -1");
									strReturn =com.ibm.dp.common.Constants.SUCCESSCAPS;
								}
								else{
									//logger.info("In else condition of parentProductId == -1");
									return strReturn =com.ibm.dp.common.Constants.REV_COLL_MSG_INVALID_PRODUCT;
								}
							}
						}
						if(strReturn.equals(com.ibm.dp.common.Constants.SUCCESSCAPS)){
//							Check in Rev_stock Inventory and Rev_Stock Inventory History
							//logger.info("In If condition of C2S before alreadyReceivedCheck , strReturn === "+strReturn );
							strReturn =alreadyReceivedCheck(strSerialNo,"C2S");
						}
						
					}else
						return com.ibm.dp.common.Constants.REV_COLL_MSG_INVALID_SRNO_C2S;
					break;
				}
				case com.ibm.dp.common.Constants.COLLECTION_CHURN:
				{
					//logger.info("In Case == CHURN");
					Integer productId = 0;
					Integer parentProductId =-1;//Added by Shilpa Khanna
					pstmt = connection.prepareStatement(DBQueries.STOK_INV_CHK);
					pstmt.setString(1, strSerialNo);
					
					rset = pstmt.executeQuery();
					//Check for Serial no. in DP_STOCK_INVENTORY
					if(rset.next())
						return strReturn =com.ibm.dp.common.Constants.REV_COLL_MSG_SRNO_UNASSIGNED;
					else
					{
						pstmt1 = connection.prepareStatement(DBQueries.STOK_INV_ASSIGN_CHK);
						pstmt1.setString(1, strSerialNo);
						
						rset1 = pstmt1.executeQuery();
						//Check for Serail No. in DP_STOCK_INVENTORY_ASSIGNED
						if(rset1.next())
						{
							productId = rset1.getInt("PRODUCT_ID");
							//Added by Shilpa Khanna for product mapping check
							parentProductId = rset1.getString("PM_PARENT_ID")!= null?rset1.getInt("PM_PARENT_ID"):-1;
							//logger.info("productId in Query  == "+productId  + "  parentProductId == "+parentProductId + "  Selected ProductID == "+intProductID );
							
							flag=true;
						}
						if(flag==true)
						{
//							Added by Shilpa Khanna for product mapping check Accross Circle for Churn
							if(parentProductId!=-1){
								if((productId.equals(intProductID)) || (parentProductId.equals(intProductID)))
								{
									//logger.info("In If condition of parentProductId!=-1");
									strReturn =com.ibm.dp.common.Constants.SUCCESSCAPS;
								}else{
									//logger.info("In else condition of parentProductId!=-1");
									return strReturn =com.ibm.dp.common.Constants.REV_COLL_MSG_INVALID_PRODUCT;
							    	}
							}else{
								if(productId.equals(intProductID)){
									//logger.info("In If condition of parentProductId == -1");
									strReturn =com.ibm.dp.common.Constants.SUCCESSCAPS;
								}
								else{
									//logger.info("In else condition of parentProductId == -1");
									return strReturn =com.ibm.dp.common.Constants.REV_COLL_MSG_INVALID_PRODUCT;
								}
							}
						}
						else
							strReturn =com.ibm.dp.common.Constants.SUCCESSCAPS;
					}
					break;
				}case com.ibm.dp.common.Constants.COLLECTION_UPGRADE:
				{
					//logger.info("In Case == UPGRADE");
					Integer productId = 0;
					Integer parentProductId =-1;//Added by Shilpa Khanna
					pstmt = connection.prepareStatement(DBQueries.STOK_INV_CHK);
					pstmt.setString(1, strSerialNo);
					
					rset = pstmt.executeQuery();
					//Check for Serial no. in DP_STOCK_INVENTORY
					if(rset.next())
						return strReturn =com.ibm.dp.common.Constants.REV_COLL_MSG_SRNO_UNASSIGNED;
					else
					{
						pstmt1 = connection.prepareStatement(DBQueries.STOK_INV_ASSIGN_CHK);
						pstmt1.setString(1, strSerialNo);
						
						rset1 = pstmt1.executeQuery();
						//Check for Serail No. in DP_STOCK_INVENTORY_ASSIGNED
						if(rset1.next())
						{
							productId = rset1.getInt("PRODUCT_ID");
							//Added by Shilpa Khanna for product mapping check
							parentProductId = rset1.getString("PM_PARENT_ID")!= null?rset1.getInt("PM_PARENT_ID"):-1;
							//logger.info("productId in Query  == "+productId  + "  parentProductId == "+parentProductId + "  Selected ProductID == "+intProductID );
								
							flag=true;
						}
						if(flag==true)
						{
//							Added by Shilpa Khanna for product mapping check
							if(parentProductId!=-1){
								if((productId.equals(intProductID)) || (parentProductId.equals(intProductID)))
								{
									//logger.info("In If condition of parentProductId!=-1");
									strReturn =com.ibm.dp.common.Constants.SUCCESSCAPS;
								}else{
									//logger.info("In else condition of parentProductId!=-1");
									return strReturn =com.ibm.dp.common.Constants.REV_COLL_MSG_INVALID_PRODUCT;
							    	}
							}else{
								if(productId.equals(intProductID)){
									//logger.info("In If condition of parentProductId == -1");
									strReturn =com.ibm.dp.common.Constants.SUCCESSCAPS;
								}
								else{
									//logger.info("In else condition of parentProductId == -1");
									return strReturn =com.ibm.dp.common.Constants.REV_COLL_MSG_INVALID_PRODUCT;
								}
							}
						}
						else
							strReturn =com.ibm.dp.common.Constants.SUCCESSCAPS;
					}
					break;
				}
			}
		}
		catch (Exception e) 
		{
			e.printStackTrace();
			throw new DAOException(e.getMessage());
		}
		finally
		{
			DBConnectionManager.releaseResources(pstmt, rset);
			DBConnectionManager.releaseResources(pstmt1, rset1);
			DBConnectionManager.releaseResources(pstmt2, rset2);
			
			pstmt = null;
			pstmt1 = null;
			pstmt2 = null;
			rset = null;
			rset1 = null;
			rset2 = null;
		}
		return strReturn;
	}
	
	

	/*public String validateSerialNoAll(String serialNo , int circle_id) throws DAOException 
	{
		PreparedStatement pstmt = null;
		ResultSet rset			= null;
		String flag  = "";
		String product_Id="";
		String product_Name="";
		String returnFlag="";
		try
		{
			pstmt = connection.prepareStatement(VALIDATE_COLLECT_SERIAL_NO_ALL);
			
			//pstmt.setInt(1, productId);
			pstmt.setString(1, serialNo);
			pstmt.setInt(2, circle_id);
			
			rset = pstmt.executeQuery();
			
			if(rset.next())
			{
				product_Id = rset.getString("PRODUCT_ID");
				product_Name = rset.getString("PRODUCT_NAME");
				returnFlag = product_Id+"#"+product_Name;
			}
			else
			{
				returnFlag="false";
			}
		
		}
		catch (Exception e) 
		{
			e.printStackTrace();
			throw new DAOException(e.getMessage());
		}
		finally
		{
			pstmt = null;
			rset = null;
		}
		return returnFlag;
	}*/
	
	private String alreadyReceivedCheck(String strSerialNo,String caseString) throws DAOException{
		String strReturnMsg = com.ibm.dp.common.Constants.SUCCESSCAPS;
		PreparedStatement pstmt = null;
		ResultSet rset = null;
		PreparedStatement pstmt1 = null;
		ResultSet rset1 = null;
		String status="";
		boolean flag=false;
		//logger.info("In alreadyReceivedCheck");
		
		try
		{
			pstmt = connection.prepareStatement(DBQueries.REV_STOK_INV_CHK);
			pstmt.setString(1, strSerialNo);
			
			rset = pstmt.executeQuery();
			
			if(rset.next())
			{
				//logger.error("FOUND IN REV STOCK INV");
				return com.ibm.dp.common.Constants.REV_COLL_MSG_SRNO_ALREADY_REC;
			}
			else 
			{
				pstmt1 = connection.prepareStatement(DBQueries.REV_STOK_INV_HIST_CHK);
				pstmt1.setString(1, strSerialNo);
				
				rset1 = pstmt1.executeQuery();
				if(rset1.next())
				{
					flag=true;
					status = rset1.getString("STATUS");
				}
				//logger.info("In alreadyReceivedCheck  status == "+status);
				if(flag == true)
				{
					if(status.trim().equals("S2W"))
						return com.ibm.dp.common.Constants.REV_COLL_MSG_SRNO_ALREADY_S2W;
					else
						strReturnMsg =com.ibm.dp.common.Constants.SUCCESSCAPS;
				}
				else
				{
//					if(caseString.equals("DOA"))
//						strReturnMsg = com.ibm.dp.common.Constants.REV_COLL_MSG_INVALID_SRNO;
//					else
						strReturnMsg =com.ibm.dp.common.Constants.SUCCESSCAPS;
				}
			}
				
			
			//logger.info("In alreadyReceivedCheck strReturnMsg == "+strReturnMsg);
			
			
		}catch (Exception e) 
		{
			e.printStackTrace();
			strReturnMsg = com.ibm.dp.common.Constants.INERNAL_ERROR;
			throw new DAOException(e.getMessage());
		}
		finally
		{
			if(pstmt != null)
			{
				DBConnectionManager.releaseResources(pstmt, null);
				pstmt = null;
			}
			if(rset == null)
			{
				DBConnectionManager.releaseResources(null, rset);
				rset = null;
			}
		}
		return strReturnMsg;
		
	}
	
	//Added by Shilpa Khanna
	
	public List<DpReverseInvetryChangeDTO> getInventoryChangeList(String collectionId,String accountId) throws DAOException 
	{
		//logger.info("in getInventoryChangeList( ) class --- DPREVERSECollectionDAO  starts here");
		List<DpReverseInvetryChangeDTO> inventryChngListDTO	= new ArrayList<DpReverseInvetryChangeDTO>();
		PreparedStatement pstmt = null;
		ResultSet rset			= null;
		DpReverseInvetryChangeDTO  inventryChangeDto = null;
			int countRow =0;	
		try
		{
			pstmt = connection.prepareStatement(GET_INVENTORY_CHANGE_LIST);
			pstmt.setString(1, collectionId);
			pstmt.setString(2, accountId);
			rset = pstmt.executeQuery();
			
			inventryChngListDTO = new ArrayList<DpReverseInvetryChangeDTO>();
			
			while(rset.next())
			{
				countRow++;
				inventryChangeDto = new DpReverseInvetryChangeDTO();
				inventryChangeDto.setCollectionId(rset.getString("COLLECTION_ID"));
				inventryChangeDto.setAgeing(rset.getString("AEGING"));
				
				if(rset.getString("NEW_PRODUCT_ID")==null || rset.getString("NEW_PRODUCT_ID").equalsIgnoreCase("null"))
					inventryChangeDto.setChangedProductId("");
				else
					inventryChangeDto.setChangedProductId(rset.getString("NEW_PRODUCT_ID"));
				
				if(rset.getString("NEW_PRODUCT_NAME")==null || rset.getString("NEW_PRODUCT_NAME").equalsIgnoreCase("null"))
					inventryChangeDto.setChangedProductName("");
				else
					inventryChangeDto.setChangedProductName(rset.getString("NEW_PRODUCT_NAME"));
				
				if(rset.getString("NEW_SR_NO")==null || rset.getString("NEW_SR_NO").equalsIgnoreCase("null"))
					inventryChangeDto.setChangedSerialNo("");
				else
					inventryChangeDto.setChangedSerialNo(rset.getString("NEW_SR_NO"));
				
				inventryChangeDto.setCollectionType(rset.getString("COLLECTION_TYPE"));
				//inventryChangeDto.setDefectId(rset.getString("PRODUCT_ID"));
				inventryChangeDto.setDefectiveProductId(rset.getString("DEFECTIVE_PRODUCT_ID"));
				inventryChangeDto.setDefectiveProductName(rset.getString("DEFECT_PRODUCT_NAME"));
				inventryChangeDto.setDefectiveSerialNo(rset.getString("DEFECTIVE_SR_NO"));
				//inventryChangeDto.setDefectType(rset.getString("PRODUCT_ID"));
				inventryChangeDto.setInventoryChangedDate(Utility.convertDateFormat(rset.getString("INV_CHANGE_DATE"), "yyyy-MM-dd", "MM/dd/yyyy"));
				inventryChangeDto.setCustomerId(rset.getString("customer_id"));
				inventryChangeDto.setDefectId(String.valueOf(rset.getInt("defect_id")));
				inventryChangeDto.setDefectName(rset.getString("defect_name"));
				inventryChangeDto.setReqId(rset.getString("REQ_ID"));
				if(rset.getString("REJECTED_DC")!=null){
				inventryChangeDto.setRejectedDC(rset.getString("REJECTED_DC"));//Neetika BFR 9
				}
				else
				{
				inventryChangeDto.setRejectedDC("");//Neetika BFR 9	
				}
				inventryChngListDTO.add(inventryChangeDto);
			}
			//logger.info("countRow ==  "+countRow);
			//logger.info("in getInventoryChangeList( ) class --- DPREVERSECollectionDAO  ensds here  ");
				
		}
		catch (Exception e) 
		{
			e.printStackTrace();
			throw new DAOException(e.getMessage());
		}
		finally
		{
			pstmt = null;
			rset = null;
		}
		return inventryChngListDTO;
	}
	
	public List<DpReverseInvetryChangeDTO> getGridDefectList(String collectionId) throws DAOException 
	{
		//logger.info("in getGridDefectList( ) class --- DPREVERSECollectionDAO  starts here");
		List<DpReverseInvetryChangeDTO> defectListDTO	= new ArrayList<DpReverseInvetryChangeDTO>();
		PreparedStatement pstmt = null;
		ResultSet rset			= null;
		DpReverseInvetryChangeDTO  defectDto = null;
			int countRow =0;	
		try
		{
			pstmt = connection.prepareStatement(GET_DEFECT_LIST);
			pstmt.setString(1, collectionId);
			rset = pstmt.executeQuery();
			
			defectListDTO = new ArrayList<DpReverseInvetryChangeDTO>();
			
			while(rset.next())
			{
				countRow++;
				defectDto = new DpReverseInvetryChangeDTO();
				
				defectDto.setDefectId(rset.getString("DEFECT_ID"));
				defectDto.setDefectType(rset.getString("DEFECT_NAME"));
				defectListDTO.add(defectDto);
			}
			//logger.info("countRow ==  "+countRow);
			//logger.info("in getGridDefectList( ) class --- DPREVERSECollectionDAO  ensds here  ");
				
		}
		catch (Exception e) 
		{
			e.printStackTrace();
			throw new DAOException(e.getMessage());
		}
		finally
		{
			pstmt = null;
			rset = null;
		}
		return defectListDTO;
	}
	
}
