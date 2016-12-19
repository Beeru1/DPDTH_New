package com.ibm.dp.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

import com.ibm.dp.common.Constants;
import com.ibm.dp.common.DBQueries;
import com.ibm.dp.dao.DpDcChangeStatusDao;
import com.ibm.dp.dto.DpDcChangeStatusDto;
import com.ibm.virtualization.recharge.dao.rdbms.BaseDaoRdbms;
import com.ibm.virtualization.recharge.db.DBConnectionManager;
import com.ibm.virtualization.recharge.exception.DAOException;

public class DpDcChangeStatusDaoImpl extends BaseDaoRdbms implements DpDcChangeStatusDao {

public static Logger logger = Logger.getLogger(DpDcChangeStatusDaoImpl.class.getName());
	
public static final String SQL_ALL_DC_NOS 	= DBQueries.GET_ALL_DCS_LIST;

	public DpDcChangeStatusDaoImpl(Connection connection) 
	{
		super(connection);
	}
	
	
	
	public List<DpDcChangeStatusDto> getAllDCList(Long lngCrBy) throws DAOException{
		logger.info("in DpDcChangeStatusDaoImpl -> getAllDCList()  lngCrBy=="+lngCrBy);
		List<DpDcChangeStatusDto> dcNosListDto = new ArrayList<DpDcChangeStatusDto>();
		PreparedStatement pstmt = null;
		PreparedStatement pstmt1 = null;
		PreparedStatement pstmt2 = null;
		ResultSet rset			= null;
		ResultSet rset1			= null;
		ResultSet rset2			= null;
		DpDcChangeStatusDto dcNoDto=null;
		String status ="";
		String modStatus ="";
		try
		{
			String query = DBQueries.GET_ALL_DCS_LIST;
			pstmt = connection.prepareStatement(query);//prepareStatement(SQL_ALL_DC_NOS);
			pstmt.setLong(1, lngCrBy);
			pstmt.setString(2, Constants.DC_TYPE_REVERSE);
			
			rset = pstmt.executeQuery();
			
			pstmt1 = connection.prepareStatement(DBQueries.GET_SR_COUNT);
			pstmt2 = connection.prepareStatement(DBQueries.GET_STATUS_COUNT);
					
			
			dcNosListDto = new ArrayList<DpDcChangeStatusDto>();
			Integer count=0;
			while(rset.next())
			{
				count++;
				status=rset.getString("STATUS");
				modStatus = rset.getString("DC_STATUS");
				if(status.equals("SUCCESS")){
					pstmt1.setString(1, rset.getString("DC_NO"));
					rset1 = pstmt1.executeQuery();
					Integer countSerialNo = 0;
					Integer countStatus =0;
					while(rset1.next()){
						 countSerialNo = rset1.getInt(1);
					}
					logger.info("in getAllDCList Dao IMPL --- countSerialNo == "+countSerialNo);
					
					pstmt2.setString(1, rset.getString("DC_NO"));
					rset2 = pstmt2.executeQuery();
					while(rset2.next()){
						countStatus = rset2.getInt(1);
					}
					
					logger.info("in getAllDCList Dao IMPL --- countStatus == "+countStatus);
					if(countSerialNo == countStatus){
						modStatus = Constants.DC_STATUS_COMP_RECEIVE;
					}else if(countStatus >0){
						modStatus = Constants.DC_STATUS_PARTIAL_RECEIVE;
					}
				}
				
				dcNoDto = new DpDcChangeStatusDto();
				logger.info("in getAllDCList ^^^^^^^^^^ modStatus ==  "+modStatus);
				
				dcNoDto.setStrBOTreeRemarks(rset.getString("BT_REMARKS"));
				dcNoDto.setStrDCDate(rset.getString("CREATE_DT"));
				dcNoDto.setStrDCNo(rset.getString("DC_NO"));
				dcNoDto.setStrSerialNo(count.toString());
				dcNoDto.setStrStatus(rset.getString("STATUS"));
				//dcNoDto.setStrDcStatus(rset.getString("DC_STATUS"));
				dcNoDto.setStrDcStatus(modStatus);
								
				dcNosListDto.add(dcNoDto);
			}
			logger.info("in DpDcChangeStatusDaoImpl -> getAllDCList()  Query ends here ==");
			
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
		}
		return dcNosListDto;
		
	}
	public String setDCStatus(String[] arrDcNos) throws DAOException{
		logger.info("in DpDcChangeStatusDaoImpl -> setDCStatus()  ==");
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
			logger.info("in DpDcChangeStatusDaoImpl -> getAllDCList()  Query ends here ==");
			
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
	
	public List<DpDcChangeStatusDto> viewSerialsStatus(String dc_no) throws DAOException
	{
		logger.info("in DpDcChangeStatusDaoImpl -> viewSerialsStatus()  dc_no=="+dc_no);
		List<DpDcChangeStatusDto> dcNosListDto = new ArrayList<DpDcChangeStatusDto>();
		PreparedStatement pstmt = null;
		ResultSet rset			= null;
		DpDcChangeStatusDto dcNoDto=null;
		try
		{
			String query = DBQueries.DC_SR_NO_STATUS;
			pstmt = connection.prepareStatement(query);//prepareStatement(SQL_ALL_DC_NOS);
			pstmt.setString(1, dc_no);
			
			rset = pstmt.executeQuery();
			
			dcNosListDto = new ArrayList<DpDcChangeStatusDto>();
			String status= "";
			
			while(rset.next())
			{
				status = rset.getString("STB_STATUS");
				logger.info("status  ::  "+status);
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
				
				
				dcNoDto = new DpDcChangeStatusDto();
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
	
}
