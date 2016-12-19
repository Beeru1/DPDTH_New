package com.ibm.dp.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;

import org.apache.log4j.Logger;

import com.ibm.dp.common.Constants;
import com.ibm.dp.common.DBQueries;
import com.ibm.dp.dao.ChurnDCGenerationDAO;
import com.ibm.dp.dto.ChurnDCGenerationDTO;
import com.ibm.dp.dto.SMSDto;
import com.ibm.dp.sms.SMSUtility;
import com.ibm.utilities.DcCreation;
import com.ibm.utilities.Utility;
import com.ibm.virtualization.recharge.dao.rdbms.BaseDaoRdbms;
import com.ibm.virtualization.recharge.db.DBConnectionManager;
import com.ibm.virtualization.recharge.exception.DAOException;

public class ChurnDCGenerationDAOImpl extends BaseDaoRdbms implements ChurnDCGenerationDAO 
{
	private static Logger logger = Logger.getLogger(ChurnDCGenerationDAOImpl.class.getName());
	
	public ChurnDCGenerationDAOImpl(Connection connection) 
	{		
		super(connection);	
	}
//Updated by Shilpa on 16-7-12
	public ArrayList<ArrayList<ChurnDCGenerationDTO>> viewChurnPendingForDCList(long accountID)throws DAOException  
	{
		ResultSet rst = null;			
		PreparedStatement pst=null;
		ChurnDCGenerationDTO dto;
		ArrayList<ChurnDCGenerationDTO> refrbSTBList = new ArrayList<ChurnDCGenerationDTO>();
		//ArrayList<ChurnDCGenerationDTO> swapSTBList = new ArrayList<ChurnDCGenerationDTO>();
		
		ArrayList<ArrayList<ChurnDCGenerationDTO>> returnList = new ArrayList<ArrayList<ChurnDCGenerationDTO>>();
		returnList.add(refrbSTBList);
		//returnList.add(swapSTBList);
		
		try 
		{
			//pst = connection.prepareStatement(DBQueries.GET_PENDING_FOR_DC_CHURN+" and a.AGEING <= ? with ur");
			pst = connection.prepareStatement(DBQueries.GET_PENDING_FOR_DC_CHURN+" with ur");
			
			//int intRefurbAgeing = Resour;
			
			pst.setLong(1, accountID);
			//pst.setInt(2, Constants.CHURN_REFURBHISHMENT_AGEING);
			
			rst=pst.executeQuery();
			
			while (rst.next()) 
			{
				dto = new ChurnDCGenerationDTO();
				dto.setSerial_Number(rst.getString("SERIAL_NUMBER"));
				dto.setProduct_Name(rst.getString("PRODUCT_NAME"));
				dto.setVc_Id(rst.getString("VC_ID"));
				dto.setCustomer_Id(rst.getString("CUSTOMER_ID"));
				dto.setSi_Id(rst.getString("SI_ID"));
				SimpleDateFormat sdf = new SimpleDateFormat("dd-MMM-yyyy");	
				dto.setCollectionDate(sdf.format(rst.getDate("CREATED_ON")));
				if(rst.getDate("CREATED_ON") != null) {
					dto.setCollectionDate(sdf.format(rst.getDate("CREATED_ON")));
					
				} else {
					dto.setCollectionDate("");
				}
				dto.setReqId(rst.getString("REQ_ID"));
				//logger.info("dto.getReq_Id()  ::  "+dto.getReqId());
				refrbSTBList.add(dto);
			}
			
			returnList.set(0, refrbSTBList);
			
			DBConnectionManager.releaseResources(pst, rst);
			
			/*pst = connection.prepareStatement(DBQueries.GET_PENDING_FOR_DC_CHURN+" and a.AGEING > ? with ur");
			
			pst.setLong(1, accountID);
			pst.setInt(2, Constants.CHURN_REFURBHISHMENT_AGEING);
			
			rst = pst.executeQuery();
			
			while (rst.next()) 
			{
				dto = new ChurnDCGenerationDTO();
				dto.setSerial_NumberN(rst.getString("SERIAL_NUMBER"));
				dto.setProduct_NameN(rst.getString("PRODUCT_NAME"));
				dto.setVc_IdN(rst.getString("VC_ID"));
				dto.setCustomer_IdN(rst.getString("CUSTOMER_ID"));
				dto.setSi_IdN(rst.getString("SI_ID"));
				SimpleDateFormat sdf = new SimpleDateFormat("dd-MMM-yyyy");	
				if(rst.getDate("CREATED_ON") != null) {
					dto.setCollectionDateN(sdf.format(rst.getDate("CREATED_ON")));
					
				} else {
					dto.setCollectionDateN("");
				}
				dto.setReqIdN(rst.getString("REQ_ID"));
				logger.info("dto.getReq_Id()  ::  "+dto.getReqId());
				swapSTBList.add(dto);
			}
			
			returnList.set(1, swapSTBList);*/
			
			return returnList;
		} 
		catch (SQLException e) 
		{   
			e.printStackTrace();
			logger.error("SQL Exception occured while geting CHurn STB for DC creating " + "Exception Message: ", e);
			throw new DAOException(e.getMessage());
		} 
		catch (Exception ex) 
		{
			ex.printStackTrace();
			logger.error(" **: ERROR INSIDE geting CHurn STB for DC creating  "+ex.getMessage());
			throw new DAOException(ex.getMessage());
		} 
		finally 
		{	
			/* Close the statement,resultset */
			DBConnectionManager.releaseResources(pst,rst);
		}
	}

	public ArrayList<ArrayList<ChurnDCGenerationDTO>> reportDCGenerationDao(ChurnDCGenerationDTO churnDTO) throws DAOException 
	{		//Statement st = null; 
        PreparedStatement pstDCHeader = null; 
        PreparedStatement pstDCDetail = null;
        PreparedStatement pstChurnStock = null;
        ResultSet rstChurnStock = null;
        PreparedStatement psUpdate = null;
        int count = 0;       
        ArrayList<ArrayList<ChurnDCGenerationDTO>> arrReturn = new ArrayList<ArrayList<ChurnDCGenerationDTO>>();
		try	
		{
			String retMessage = "";
			//Inserting Into DC_HEADER for CHURN
			String strDCNo = DcCreation.getDcCreationObject().generateDcNo(connection, Constants.DC_TYPE_CHURN);
			//logger.info("New Churn DC No ::  "+ strDCNo);
			
			String strCourAgency = churnDTO.getCourierAgency();
			//logger.info("Courier Agency ::  "+ strCourAgency);
			
			String strDocketNo = churnDTO.getDocketNumber();
			//logger.info("Docket No  ::  "+ strDocketNo);
			
			String strDCStatus = "";
			
			Timestamp tsCurrentTS = Utility.getCurrentTimeStamp();
			Timestamp tsDCFinalDate = null;
			Timestamp tsDCDraftDate = null;
			Timestamp tsDCDate = null;
			
			if(strCourAgency==null || strCourAgency.trim().equals("") || strDocketNo==null || strDocketNo.trim().equals(""))
			{
				tsDCDraftDate = tsCurrentTS;
				tsDCDate = tsCurrentTS;
				tsDCFinalDate = null;
				strDCStatus = Constants.PUSH_DC_TO_BOTREE_DRAFT_STATUS;
				retMessage = strDCNo + Constants.DRAFT_DC_MESSAGE;
			}
			else
			{
				tsDCDraftDate = tsCurrentTS;
				tsDCFinalDate = tsCurrentTS;
				tsDCDate = tsCurrentTS;
				strDCStatus = Constants.PUSH_DC_TO_BOTREE_CREATED_STATUS;
				retMessage = strDCNo + Constants.DC_MESSAGE;
			}
			
			//logger.info("DC STATUS  ::  "+ strDCStatus);
			//logger.info("DC Draft Date  ::  "+ tsDCDraftDate);
			//logger.info("DC Final Date  ::  "+ tsDCFinalDate);
			//logger.info("DC Date  ::  "+ tsDCDate);
			
			pstDCHeader =  connection.prepareStatement(DBQueries.INSERT_CHURN_DC_HEADER);
			
			//DC_NO, DC_STATUS, DC_REMARK, COURIER_AGENCY, DOCKET_NO, DC_DRAFT_DATE, DC_FINAL_DATE, CIRCLE_ID, 
			//DC_DATE, DIST_ID, WH_CODE
			
			pstDCHeader.setString(1, strDCNo);
			pstDCHeader.setString(2, strDCStatus);
			pstDCHeader.setString(3, churnDTO.getRemarks());
			pstDCHeader.setString(4, strCourAgency.trim());
			pstDCHeader.setString(5, strDocketNo.trim());
			pstDCHeader.setTimestamp(6, tsDCDraftDate);
			pstDCHeader.setTimestamp(7, tsDCFinalDate);
			pstDCHeader.setInt(8, churnDTO.getIntCircleID());
			pstDCHeader.setTimestamp(9, tsDCDraftDate);
			pstDCHeader.setLong(10, churnDTO.getLnUserID());
			pstDCHeader.setLong(11, churnDTO.getLnUserID());
			
			int intIns = pstDCHeader.executeUpdate();
			
			DBConnectionManager.releaseResources(pstDCHeader, null);
			
			if(intIns>0)
			{
				ArrayList<String> arrAllCheckedSTB = churnDTO.getAllCheckedSTB();
				
				String allCheckedRec = "";
				
				for(String str : arrAllCheckedSTB)
				{
					if(allCheckedRec.length()==0)
						allCheckedRec = str;
					else
						allCheckedRec = allCheckedRec+","+str;
				}
				
				//logger.info("Checked Records for DC  ::  "+ allCheckedRec);
				
				String strQuery = DBQueries.GET_SELECTED_CHURN_RECORDS+" ( "+allCheckedRec+" ) with ur";
				
				//logger.info("Query to get Checked STB details  ::  "+ strQuery);
				
				pstChurnStock = connection.prepareStatement(strQuery);
				
				rstChurnStock = pstChurnStock.executeQuery();
				
				pstDCDetail =  connection.prepareStatement(DBQueries.INSERT_CHURN_DC_DETAIL);
				
				//Insert seq
				//DC_NO, REQ_ID, SERIAL_NUMBER, PRODUCT_ID, VC_ID, CUSTOMER_ID, SI_ID, AGEING, 
				//REMARK, STATUS, CIRCLE_ID, DIST_ID, PURCHASE_DT, SERVICE_START_DT, CHURN_DECLARE_DT
				
				//select seq
				//REQ_ID, SERIAL_NUMBER, PRODUCT_ID, VC_ID, CUSTOMER_ID, SI_ID, AGEING, REMARK, STATUS, CIRCLE_ID, PURCHASE_DT, SERVICE_START_DT, CREATED_ON
				count = 0;
				while(rstChurnStock.next())
				{
					pstDCDetail.setString(1, strDCNo);
					pstDCDetail.setLong(2, rstChurnStock.getLong("REQ_ID"));
					pstDCDetail.setString(3, rstChurnStock.getString("SERIAL_NUMBER")); 
					pstDCDetail.setInt(4, rstChurnStock.getInt("PRODUCT_ID"));
					pstDCDetail.setString(5, rstChurnStock.getString("VC_ID")); 
					pstDCDetail.setString(6, rstChurnStock.getString("CUSTOMER_ID"));
					pstDCDetail.setString(7, rstChurnStock.getString("SI_ID")); 
					pstDCDetail.setInt(8, rstChurnStock.getInt("AGEING"));
					pstDCDetail.setString(9, rstChurnStock.getString("REMARK"));
					pstDCDetail.setString(10, Constants.DC_SR_NO_STATUS_IDC); 
					pstDCDetail.setInt(11, rstChurnStock.getInt("CIRCLE_ID")); 
					pstDCDetail.setLong(12, churnDTO.getLnUserID());
					pstDCDetail.setTimestamp(13,rstChurnStock.getTimestamp("PURCHASE_DT"));
					pstDCDetail.setTimestamp(14,rstChurnStock.getTimestamp("SERVICE_START_DT"));
					pstDCDetail.setTimestamp(15,rstChurnStock.getTimestamp("CREATED_ON"));
						
					pstDCDetail.executeUpdate();
					
					count++;
				}
				//logger.info("Number of Records inserted in DP_REV_CHURN_DC_DETAIL  ::  "+ count);
				
				DBConnectionManager.releaseResources(pstChurnStock, rstChurnStock);
				DBConnectionManager.releaseResources(pstDCDetail, null);
				
				
//				Updating Status in DP_REV_CHURN_INVENTORY
				strQuery = DBQueries.UPDATE_CHURN_STOCK+" ( "+allCheckedRec+" ) ";
				//logger.info("Query to get Update Checked STB details in DP_REV_CHURN_INVENTORY  ::  "+ strQuery);
				
				psUpdate = connection.prepareStatement(strQuery);
				
				psUpdate.setString(1, Constants.DC_SR_NO_STATUS_IDC);
				
				int intUpd = psUpdate.executeUpdate();
				//logger.info("Number of Records updated in DP_REV_CHURN_INVENTORY  ::  "+ intUpd);
				
				DBConnectionManager.releaseResources(psUpdate, null);
			}
			
			arrReturn = viewChurnPendingForDCList(churnDTO.getLnUserID());
			
			ArrayList<ChurnDCGenerationDTO> arrMesg = new ArrayList<ChurnDCGenerationDTO>();
			
			ChurnDCGenerationDTO dtoMesg = new ChurnDCGenerationDTO();
			dtoMesg.setMessage(retMessage);
			dtoMesg.setDcNo(strDCNo);
			//logger.info("In Dao Perform Churn Messsage == "+retMessage);
			arrMesg.add(dtoMesg);
			
			arrReturn.add(arrMesg);
			
////////////////alert management//////////////////
			/*if(strDCStatus ==Constants.PUSH_DC_TO_BOTREE_CREATED_STATUS)
			{
				SMSDto sMSDto = null;
				sMSDto = SMSUtility.getUserDetails(churnDTO.getLnUserID()+"", connection);
				
					sMSDto.setDcNo(strDCNo);
					sMSDto.setCountSrNo(count);
					String SMSmessage=SMSUtility.createMessageContent(com.ibm.virtualization.recharge.common.Constants.CONFIRM_ID_DC_CREATION, sMSDto, connection);
					SMSUtility.saveSMSInDB(connection, new String[] {sMSDto.getParentMobileNumber()}, SMSmessage);
			}*/
			////////////////////////////////////////////////////
			
		}		
		catch(Exception e)		
		{
			e.printStackTrace();
			logger.error("Exception occured while Saving Delivery Challan  ::  "+e.getMessage());
			throw new DAOException(e.getMessage());			
		}		
		finally		
		{		
			DBConnectionManager.releaseResources(pstDCHeader, null);
			DBConnectionManager.releaseResources(pstChurnStock, rstChurnStock);
			DBConnectionManager.releaseResources(pstDCDetail, null);
			DBConnectionManager.releaseResources(psUpdate, null);
		}
		return arrReturn;
	}
	
	public String checkERR(String rowSrNo) throws  DAOException 
	{
		PreparedStatement ps = null;			
		ResultSet rset			= null;
		String strSCMStatus ="";
		String strStatus="";
		String isERR="";
		try 
			{
				connection.setAutoCommit(false);					
				ps = connection.prepareStatement(DBQueries.CHECK_ERROR_STATUS_CHURN);
				 ps.setString(1, rowSrNo);
				
				 rset =  ps.executeQuery();
				 
				while(rset.next()) 
				{							
						 strStatus= rset.getString("STATUS");
						 strSCMStatus = rset.getString("ERROR_STATUS");												
				}
				if(strStatus.trim().equalsIgnoreCase("ERR") &&  strSCMStatus.trim().equalsIgnoreCase("ERR")){
					isERR="Y";
				}
				else{
					isERR="N";
				}
			
			}
			catch(SQLException sqle){
				sqle.printStackTrace();
				throw new DAOException("SQLException: " + sqle.getMessage());
			}catch(Exception e){
				e.printStackTrace();
				throw new DAOException("Exception: " + e.getMessage());
			}finally
			{
				DBConnectionManager.releaseResources(ps, rset);
			
			}
		return isERR;
			
		 }
}
