package com.ibm.dp.dao.impl;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

import com.ibm.dp.common.Constants;
import com.ibm.dp.common.DBQueries;
import com.ibm.dp.dao.DpDcChurnStatusDao;
import com.ibm.dp.dto.DpDcChurnStatusDto;
import com.ibm.dp.dto.SMSDto;
import com.ibm.dp.sms.SMSUtility;
import com.ibm.virtualization.recharge.dao.rdbms.BaseDaoRdbms;
import com.ibm.virtualization.recharge.db.DBConnectionManager;
import com.ibm.virtualization.recharge.exception.DAOException;

public class DpDcChurnStatusDaoImpl extends BaseDaoRdbms implements DpDcChurnStatusDao {

public static Logger logger = Logger.getLogger(DpDcChurnStatusDaoImpl.class.getName());
	
	public DpDcChurnStatusDaoImpl(Connection connection) 
	{
		super(connection);
	}
	
	public List<DpDcChurnStatusDto> getAllDCListChurn(Long lngCrBy) throws DAOException
	{
		logger.info("in DpDcChurnStatusDaoImpl -> getAllDCList()  lngCrBy=="+lngCrBy);
		List<DpDcChurnStatusDto> dcNosListChurn = new ArrayList<DpDcChurnStatusDto>();
		PreparedStatement pstmt = null;		
		ResultSet rset			= null;		
		DpDcChurnStatusDto dcNoDto=null;
		String status ="";
		String modStatus ="";
		DateFormat formatter= new SimpleDateFormat("dd/MM/yyyy");
		try
		{
			String query = DBQueries.GET_ALL_DCS_LIST_CHURN;
			pstmt = connection.prepareStatement(query);
			pstmt.setLong(1, lngCrBy);
			rset = pstmt.executeQuery();
			
			dcNosListChurn = new ArrayList<DpDcChurnStatusDto>();
			Integer count=0;
			while(rset.next())
			{
				count++;
				status=rset.getString("DC_STATUS");
				modStatus = rset.getString("DC_STATUS");
				
				dcNoDto = new DpDcChurnStatusDto();
				//logger.info("in getAllDCList ^^^^^^^^^^ modStatus ==  "+modStatus);
				
				dcNoDto.setStrBOTreeRemarks(rset.getString("BOTREE_REMARK"));
				dcNoDto.setStrDCDate(rset.getString("DC_DATE"));
				dcNoDto.setStrDCNo(rset.getString("DC_NO"));
				dcNoDto.setStrSerialNo(count.toString());
				dcNoDto.setStrStatus(rset.getString("DC_STATUS"));
				//dcNoDto.setStrDcStatus(rset.getString("DC_STATUS"));
				dcNoDto.setStrDcStatus(modStatus);
				
				dcNoDto.setCourierAgency(rset.getString("COURIER_AGENCY"));
				dcNoDto.setDocketNumber(rset.getString("DOCKET_NO"));
				if(rset.getString("COURIER_AGENCY")!=null 
						&& !rset.getString("COURIER_AGENCY").equals("") 
						&& rset.getString("DOCKET_NO")!=null 
						&& !rset.getString("DOCKET_NO").equals(""))
				{
					dcNoDto.setIsFilled("Y");
				}
				else
				{
					dcNoDto.setIsFilled("N");
				}
				
				//Ends here
								
				dcNosListChurn.add(dcNoDto);
			}
			logger.info("in DpDcChurnStatusDaoImpl -> getAllDCList()  Query ends here ==");
			
		}
		catch (Exception e) 
		{
			e.printStackTrace();
			logger.info("Exception in DC CHurn Status tab  ==  "+e.getMessage());
			throw new DAOException(e.getMessage());
		}
		finally
		{
			DBConnectionManager.releaseResources(pstmt, rset);
		}
		return dcNosListChurn;
		
	}
	public String setDCStatusChurn(String[] arrDcNos) throws DAOException{
		logger.info("in DpDcChurnStatusDaoImpl -> setDCStatus()  ==");
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
			logger.info("in DpDcChurnStatusDaoImpl -> getAllDCListList():::::::  Query ends here ==");
			
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
	
	public List<DpDcChurnStatusDto> viewSerialsStatusChurn(String dc_no) throws DAOException
	{
		logger.info("+++++++++++++++++++++++++++++++++++++++++++++++++*********************in DpDcChurnStatusDaoImpl -> viewSerialsStatus()  dc_no=="+dc_no);
		List<DpDcChurnStatusDto> dcNosListDtoChurn = new ArrayList<DpDcChurnStatusDto>();
		PreparedStatement pstmt = null;
		ResultSet rset			= null;
		DpDcChurnStatusDto dcNoDto=null;
		try
		{
			String query = DBQueries.DC_SR_NO_STATUS_CHURN;
			pstmt = connection.prepareStatement(query);//prepareStatement(SQL_ALL_DC_NOS);
			pstmt.setString(1, dc_no);
			
			rset = pstmt.executeQuery();
			
			dcNosListDtoChurn = new ArrayList<DpDcChurnStatusDto>();
			String status= "";
			while(rset.next())
			{
				status = rset.getString("DC_STATUS");
				dcNoDto = new DpDcChurnStatusDto();
				dcNoDto.setStrSerialNo(rset.getString("SERIAL_NUMBER"));
				dcNoDto.setStrProductName(rset.getString("PRODUCT_NAME"));
				dcNoDto.setStrStatus(status);
				
				dcNosListDtoChurn.add(dcNoDto);
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
		return dcNosListDtoChurn;
		
	}
	
	public void submitDamageDetail(String dc_no,String courierAgency,String docketNum) throws DAOException{
		logger.info("------- in DpDcDamageStatusDaoImpl -> submitDamageDetail   --- Starts here");
		PreparedStatement pstmt = null;
		/*PreparedStatement pstmtAlert = null;
		ResultSet rsAlert = null;*/
		try
		{
			String query ="UPDATE DP_REV_CHURN_DC_HEADER SET DC_FINAL_DATE = current timestamp, DC_STATUS='CREATED', COURIER_AGENCY=?,  DOCKET_NO=?  WHERE DC_NO=? ";
			/*String queryAlert="select count(1) as count,a.DIST_ID,a.dc_no  "
				+" from DP_REV_CHURN_DC_HEADER a,DP_REV_CHURN_DC_DETAIL b "
				+" where a.dc_no=b.dc_no "
				+" and b.dc_no= ? "
				+" group by a.DIST_ID,a.dc_no with ur";
			pstmtAlert = connection.prepareStatement(queryAlert);
			pstmtAlert.setString(1, dc_no);
			rsAlert = pstmtAlert.executeQuery();*/
			
			//if(courierAgency!=null && !courierAgency.equals("")){	query += ",COURIER_AGENCY='"+courierAgency+"'";			}
			//if(docketNum!=null && !docketNum.equals("")){	query += ",DOCKET_NUMBER='"+docketNum+"'";			}
			//query += " where DC_NO=?";
			logger.info("Query generated == "+query);
			pstmt = connection.prepareStatement(query);
			pstmt.setString(1, courierAgency);pstmt.setString(2, docketNum);pstmt.setString(3, dc_no);
			pstmt.executeUpdate();
			
////////////////alert management//////////////////
			/*if(rsAlert.next())
			{
				SMSDto sMSDto = null;
				sMSDto = SMSUtility.getUserDetails(rsAlert.getString("DIST_ID")+"", connection);
				sMSDto.setDcNo(dc_no);
				sMSDto.setCountSrNo(rsAlert.getInt("COUNT"));
				String SMSmessage=SMSUtility.createMessageContent(com.ibm.virtualization.recharge.common.Constants.CONFIRM_ID_DC_CREATION, sMSDto, connection);
				SMSUtility.saveSMSInDB(connection, new String[] {sMSDto.getParentMobileNumber()}, SMSmessage);
			
			}*/
			
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
