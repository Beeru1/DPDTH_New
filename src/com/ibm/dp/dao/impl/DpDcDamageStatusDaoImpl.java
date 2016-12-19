package com.ibm.dp.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

import com.ibm.dp.common.DBQueries;
import com.ibm.dp.dao.DpDcDamageStatusDao;
import com.ibm.dp.dto.DpDcDamageStatusDto;
import com.ibm.dp.dto.SMSDto;
import com.ibm.dp.sms.SMSUtility;
import com.ibm.virtualization.recharge.dao.rdbms.BaseDaoRdbms;
import com.ibm.virtualization.recharge.db.DBConnectionManager;
import com.ibm.virtualization.recharge.exception.DAOException;

public class DpDcDamageStatusDaoImpl extends BaseDaoRdbms implements DpDcDamageStatusDao {

public static Logger logger = Logger.getLogger(DpDcDamageStatusDaoImpl.class.getName());
	
public static final String SQL_ALL_DC_NOS 	= DBQueries.GET_ALL_DCS_LIST;

	public DpDcDamageStatusDaoImpl(Connection connection) 
	{
		super(connection);
	}
	
	
	
	public List<DpDcDamageStatusDto> getAllDCList(Long lngCrBy) throws DAOException{
		logger.info("in DpDcDamageStatusDaoImpl -> getAllDCList()  lngCrBy=="+lngCrBy);
		List<DpDcDamageStatusDto> dcNosListDto = new ArrayList<DpDcDamageStatusDto>();
		PreparedStatement pstmt = null;
		ResultSet rset			= null;
		DpDcDamageStatusDto dcNoDto=null;
		String modStatus ="";
		try
		{
			String query = DBQueries.GET_ALL_DCS_LIST;
			pstmt = connection.prepareStatement(query);//prepareStatement(SQL_ALL_DC_NOS);
			pstmt.setLong(1, lngCrBy);
			
			rset = pstmt.executeQuery();
			dcNosListDto = new ArrayList<DpDcDamageStatusDto>();
			Integer count=0;
			System.out.println("Next Value==================================" + rset.getFetchSize());
			while(rset.next())
			{
				count++;
				modStatus = rset.getString("DC_STATUS");
				dcNoDto = new DpDcDamageStatusDto();
				
				if(modStatus.toLowerCase().equals("draft")){
					modStatus = "Incomplete";
				}
				
				dcNoDto.setStrBOTreeRemarks(rset.getString("BT_REMARKS"));
				dcNoDto.setStrDCDate(rset.getString("CREATE_DT"));
				dcNoDto.setStrDCNo(rset.getString("DC_NO"));
				dcNoDto.setStrSerialNo(count.toString());
				dcNoDto.setStrStatus(rset.getString("STATUS"));
				//dcNoDto.setStrDcStatus(rset.getString("DC_STATUS"));
				dcNoDto.setStrDcStatus(modStatus);
				//Added by Shilpa For Critical Changes BFR 14
				dcNoDto.setCourierAgency(rset.getString("COURIER_AGENCY"));
				dcNoDto.setDocketNumber(rset.getString("DOCKET_NUMBER"));
				if(rset.getString("COURIER_AGENCY")!=null && !rset.getString("COURIER_AGENCY").equals("") && rset.getString("DOCKET_NUMBER")!=null && !rset.getString("DOCKET_NUMBER").equals("")){
					dcNoDto.setIsFilled("Y");
				}else{
					dcNoDto.setIsFilled("N");
				}
				
				//Ends here
				dcNosListDto.add(dcNoDto);
			}
			logger.info("in DpDcDamageStatusDaoImpl -> getAllDCList()  Query ends here ==");
			
		}
		catch (Exception e) 
		{
			e.printStackTrace();
			throw new DAOException(e.getMessage());
		}
		finally
		{
			DBConnectionManager.releaseResources(pstmt, rset);
		}
		return dcNosListDto;
		
	}
	/*   ##### Commented by Shilpa On 30-8-2012 to get status directly from DC_header table
	 * 
	 */
	/*public List<DpDcDamageStatusDto> getAllDCList(Long lngCrBy) throws DAOException{
		logger.info("in DpDcDamageStatusDaoImpl -> getAllDCList()  lngCrBy=="+lngCrBy);
		List<DpDcDamageStatusDto> dcNosListDto = new ArrayList<DpDcDamageStatusDto>();
		PreparedStatement pstmt = null;
		PreparedStatement pstmt1 = null;
		PreparedStatement pstmt2 = null;
		PreparedStatement pstmt3 = null;
		ResultSet rset			= null;
		ResultSet rset1			= null;
		ResultSet rset2			= null;
		ResultSet rset3			= null;
		DpDcDamageStatusDto dcNoDto=null;
		String status ="";
		String modStatus ="";
		try
		{
			String query = DBQueries.GET_ALL_DCS_LIST;
			pstmt = connection.prepareStatement(query);//prepareStatement(SQL_ALL_DC_NOS);
			pstmt.setLong(1, lngCrBy);
			
			rset = pstmt.executeQuery();
			pstmt3 = connection.prepareStatement(DBQueries.GET_ALL_STATUS_COUNT);
			dcNosListDto = new ArrayList<DpDcDamageStatusDto>();
			Integer count=0;
			System.out.println("Next Value==================================" + rset.getFetchSize());
			while(rset.next())
			{
				count++;
				status=rset.getString("STATUS");
				modStatus = rset.getString("DC_STATUS");
				if(status.equals("SUCCESS")){
					pstmt1 = connection.prepareStatement(DBQueries.GET_SR_COUNT);
					pstmt1.setString(1, rset.getString("DC_NO"));
					rset1 = pstmt1.executeQuery();
					Integer countSerialNo = 0;
					Integer countStatusABW =0;
					Integer countStatusMSN =0;
					Integer countStatusAIW =0;
					String strStatusName ="";
					while(rset1.next()){
						 countSerialNo = rset1.getInt(1);
					}
					logger.info("in getAllDCList Dao IMPL --- countSerialNo ---------------------------------------------------------------== "+countSerialNo);
					
				
					pstmt3.setString(1, rset.getString("DC_NO"));
					pstmt3.setString(2, rset.getString("DC_NO"));
					pstmt3.setString(3, rset.getString("DC_NO"));
					rset3 = pstmt3.executeQuery();
					
					while(rset3.next()){
						strStatusName = rset3.getString(2);
						if(strStatusName.equals("MSN_COUNT")){
							countStatusMSN = rset3.getInt(1);
						}else if(strStatusName.equals("AIW_COUNT")){
							countStatusAIW = rset3.getInt(1);
						}else if(strStatusName.equals("ABW_COUNT")){
							countStatusABW = rset3.getInt(1);
						}
						
					}
					
					logger.info("in getAllDCList Dao IMPL --- countStatusMSN  == "+countStatusMSN + "  -- countStatusAIW == "+countStatusAIW+"  -- countStatusABW ==="+countStatusABW );
					if(countSerialNo == countStatusAIW){
						modStatus = Constants.DC_STATUS_COMP_RECEIVE;
					}else if(countSerialNo == countStatusMSN){
						modStatus = Constants.DC_STATUS_COMP_REJECT;
					}else if(countStatusMSN !=0 || countStatusAIW !=0 || countStatusABW != 0){
						modStatus = Constants.DC_STATUS_PARTIAL_RECEIVE;
					}
					
					
					
					
					
					//Commented by Shilpa Khanna
					logger.info("in getAllDCList Dao IMPL --- countStatus == "+countStatus);
					if(countSerialNo == countStatus){
						modStatus = Constants.DC_STATUS_COMP_RECEIVE;
					}else if(countStatus >0){
						modStatus = Constants.DC_STATUS_PARTIAL_RECEIVE;
					}
				}
				
				dcNoDto = new DpDcDamageStatusDto();
				logger.info("in getAllDCList ^^^^^^^^^^ modStatus ==  "+modStatus);
				
				if(modStatus.toLowerCase().equals("draft")){
					modStatus = "Incomplete";
				}
				
				dcNoDto.setStrBOTreeRemarks(rset.getString("BT_REMARKS"));
				dcNoDto.setStrDCDate(rset.getString("CREATE_DT"));
				dcNoDto.setStrDCNo(rset.getString("DC_NO"));
				dcNoDto.setStrSerialNo(count.toString());
				dcNoDto.setStrStatus(rset.getString("STATUS"));
				//dcNoDto.setStrDcStatus(rset.getString("DC_STATUS"));
				dcNoDto.setStrDcStatus(modStatus);
				//Added by Shilpa For Critical Changes BFR 14
				dcNoDto.setCourierAgency(rset.getString("COURIER_AGENCY"));
				dcNoDto.setDocketNumber(rset.getString("DOCKET_NUMBER"));
				if(rset.getString("COURIER_AGENCY")!=null && !rset.getString("COURIER_AGENCY").equals("") && rset.getString("DOCKET_NUMBER")!=null && !rset.getString("DOCKET_NUMBER").equals("")){
					dcNoDto.setIsFilled("Y");
				}else{
					dcNoDto.setIsFilled("N");
				}
				
				//Ends here
				dcNosListDto.add(dcNoDto);
			}
			logger.info("in DpDcDamageStatusDaoImpl -> getAllDCList()  Query ends here ==");
			
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
			pstmt1 = null;
			rset1 = null;
			pstmt2 = null;
			rset2 = null;
			pstmt3 = null;
			rset3 = null;
		}
		return dcNosListDto;
		
	}*/
	public String setDCStatus(String[] arrDcNos) throws DAOException{
		logger.info("in DpDcDamageStatusDaoImpl -> setDCStatus()  ==");
		PreparedStatement pstmt = null;
		Statement pstmt2 = null;
		PreparedStatement pstmt3 = null;
		ResultSet res =null;
		String strSuccessMsg="";
		String strSerialNo ="";
		String strProductId ="";
		String strCreatedBy ="";
		try
		{
			StringBuffer strDcNosString = new StringBuffer("");;
//			for(int count=0;count<arrDcNos.length;count++){
//				if(!strDcNosString.equals("")){
//					strDcNosString +=",";
//				}
//				strDcNosString += "'"+arrDcNos[count]+"'";
//			}
//			logger.info("strDcNosString ***************"+strDcNosString);
//			strDcNosString += ")";
//			String query = DBQueries.SET_DCS_STATUS;
//			pstmt = connection.createStatement();
//			query= query.replace("?1", strDcNosString);
//			logger.info("Query === "+query);
//			pstmt.executeUpdate(query);
//			
//			//to Select Details of DC
//			String query2 = DBQueries.SLECT_REV_DC_DETAIL;
//			pstmt2 = connection.createStatement();
//			query2= query2.replace("?1", strDcNosString);
//			logger.info("Query 2 === "+query2);
//			res=pstmt2.executeQuery(query2);
//			
//			//To Update Status in Stock Inventory
//			pstmt3 = connection.prepareStatement(DBQueries.UPDATE_REV_STOCK_INV);
			int intUpd = 0;
			pstmt = connection.prepareStatement(DBQueries.SET_DCS_STATUS);
			
			for(int count=0;count<arrDcNos.length;count++)
			{
				pstmt.setString(1, arrDcNos[count]);
				intUpd = pstmt.executeUpdate();
				
				if(intUpd>0)
				{
					if(strDcNosString.length() == 0)
					{
						strDcNosString.append("'");
						strDcNosString.append(arrDcNos[count].trim());
						strDcNosString.append("'");
					}
					else
					{
						strDcNosString.append(",'");
						strDcNosString.append(arrDcNos[count].trim());
						strDcNosString.append("'");
					}
				}
			}
			
			strDcNosString.append(") WITH UR");
			
			if(strDcNosString.length()>20)
			{
				//to Select Details of DC
				String query2 = DBQueries.SLECT_REV_DC_DETAIL;
				pstmt2 = connection.createStatement();
				query2= query2.replace("?1", strDcNosString);
				logger.info("Query 2 === "+query2);
				res=pstmt2.executeQuery(query2);
				
				//To Update Status in Stock Inventory
				pstmt3 = connection.prepareStatement(DBQueries.UPDATE_REV_STOCK_INV);
				
				
				while(res.next())
				{
					strProductId = res.getString("PRODUCT_ID");
					strSerialNo = res.getString("SERIAL_NO");
					strCreatedBy = res.getString("DIST_ID");
					//Commented by nazim hussain as prepared statement is created inside loop
	//				pstmt3 = connection.prepareStatement(DBQueries.UPDATE_REV_STOCK_INV);
					pstmt3.setString(1, strProductId);
					pstmt3.setString(2, strSerialNo);
					pstmt3.setString(3, strCreatedBy);
	//				pstmt3.executeUpdate();
					pstmt3.addBatch();
				}
				
				pstmt3.executeBatch();		
				strSuccessMsg ="SUCCESS";
			}
			else
			{
				strSuccessMsg ="All DCs have already been pushed to BoTree";
			}
			connection.commit();
			logger.info("in DpDcDamageStatusDaoImpl -> getAllDCList()  Query ends here ==");
			
		}
		catch (Exception e) 
		{
			e.printStackTrace();
			throw new DAOException(e.getMessage());
		}
		finally
		{
			DBConnectionManager.releaseResources(pstmt, null);
			DBConnectionManager.releaseResources(pstmt2, res);
			DBConnectionManager.releaseResources(pstmt3, null);
		}
		
		return strSuccessMsg;
	}
	
	public List<DpDcDamageStatusDto> viewSerialsStatus(String dc_no) throws DAOException
	{
		logger.info("in DpDcDamageStatusDaoImpl -> viewSerialsStatus()  dc_no=="+dc_no);
		List<DpDcDamageStatusDto> dcNosListDto = new ArrayList<DpDcDamageStatusDto>();
		PreparedStatement pstmt = null;
		ResultSet rset			= null;
		DpDcDamageStatusDto dcNoDto=null;
		try
		{
			String query = DBQueries.DC_SR_NO_STATUS;
			pstmt = connection.prepareStatement(query);//prepareStatement(SQL_ALL_DC_NOS);
			pstmt.setString(1, dc_no);
			
			rset = pstmt.executeQuery();
			
			dcNosListDto = new ArrayList<DpDcDamageStatusDto>();
			String status= "";
			while(rset.next())
			{
				status = rset.getString("STB_STATUS");
				
//				if(status.trim().equalsIgnoreCase(Constants.DC_SR_NO_STATUS_S2W))
//					status=Constants.DC_SR_NO_STATUS_S2W_Full;
//				else if(status.trim().equalsIgnoreCase(Constants.DC_SR_NO_STATUS_AIW))
//					status=Constants.DC_SR_NO_STATUS_AIW_FULL;
//				else if(status.trim().equalsIgnoreCase(Constants.DC_SR_NO_STATUS_IDC))
//					status=Constants.DC_SR_NO_STATUS_IDC_STATUS;
//				else if(status.trim().equalsIgnoreCase(Constants.DC_SR_NO_STATUS_ERR))
//					status=Constants.DC_SR_NO_STATUS_ERR_FULL;
//				else if(status.trim().equalsIgnoreCase(Constants.DC_SR_NO_STATUS_MSN))
//					status=Constants.DC_SR_NO_STATUS_MSN_FULL;
//				else if(status.trim().equalsIgnoreCase(Constants.DC_SR_NO_STATUS_ABW))
//					status=Constants.DC_SR_NO_STATUS_ABW_FULL;
				
				dcNoDto = new DpDcDamageStatusDto();
				dcNoDto.setStrSerialNo(rset.getString("SERIAL_NO"));
				dcNoDto.setStrProductName(rset.getString("PRODUCT_NAME"));
				dcNoDto.setStrStatus(status);
				
				dcNosListDto.add(dcNoDto);
				dcNoDto = null;
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
		return dcNosListDto;
		
	}
	public void submitDamageDetail(String dc_no,String courierAgency,String docketNum) throws DAOException{
		logger.info("------- in DpDcDamageStatusDaoImpl -> submitDamageDetail   --- Starts here");
		PreparedStatement pstmt = null;
	/*	PreparedStatement pstmtAlert = null;
		ResultSet rsAlert = null;*/
		try
		{
			String query ="Update DP_REV_DELIVERY_CHALAN_HDR set UPDATED_ON= current timestamp, STATUS='"+com.ibm.dp.common.Constants.PUSH_DC_TO_BOTREE_CREATED_STATUS+"'";
			/*String queryAlert="select count(1) as count,a.CREATED_BY,a.dc_no  "
				+" from DP_REV_DELIVERY_CHALAN_HDR a,DP_REV_DELIVERY_CHALAN_DETAIL b "
				+" where a.dc_no=b.dc_no "
				+" and b.dc_no= ? "
				+" group by a.CREATED_BY,a.dc_no with ur";
			pstmtAlert = connection.prepareStatement(queryAlert);
			pstmtAlert.setString(1, dc_no);
			rsAlert = pstmtAlert.executeQuery();*/
	
			if(courierAgency!=null && !courierAgency.equals("")){
				query += ",COURIER_AGENCY='"+courierAgency+"'";
			}
			if(docketNum!=null && !docketNum.equals("")){
				query += ",DOCKET_NUMBER='"+docketNum+"'";
			}
			query += " where DC_NO=?";
			logger.info("Query generated == "+query);
			pstmt = connection.prepareStatement(query);
			pstmt.setString(1, dc_no);
			pstmt.executeUpdate();
			
////////////////alert management//////////////////
			/*if(rsAlert.next())
			{
				SMSDto sMSDto = null;
				sMSDto = SMSUtility.getUserDetails(rsAlert.getString("CREATED_BY")+"", connection);
				sMSDto.setDcNo(dc_no);
				sMSDto.setCountSrNo(rsAlert.getInt("COUNT"));
				String SMSmessage=SMSUtility.createMessageContent(com.ibm.virtualization.recharge.common.Constants.CONFIRM_ID_DC_CREATION, sMSDto, connection);
				SMSUtility.saveSMSInDB(connection, new String[] {sMSDto.getParentMobileNumber()}, SMSmessage);
			
			}*/
/////////////////////////////////////////////////////////////////		
			
			connection.commit();
		}catch (Exception e) 
		{
			e.printStackTrace();
			throw new DAOException(e.getMessage());
		}
		finally
		{
			DBConnectionManager.releaseResources(pstmt,null);
			/*DBConnectionManager.releaseResources(pstmtAlert,rsAlert);*/
		}
		
		
	}
}
