package com.ibm.dp.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.ListIterator;
import org.apache.log4j.Logger;

import com.ibm.dp.beans.StockSummaryReportBean;
import com.ibm.dp.common.Constants;
import com.ibm.dp.common.DBQueries;
import com.ibm.dp.dao.InterSsdTransferAdminDao;
import com.ibm.dp.dto.CircleDto;
import com.ibm.dp.dto.InterSsdTransferAdminDto;
import com.ibm.utilities.Utility;
import com.ibm.virtualization.recharge.common.ResourceReader;
import com.ibm.virtualization.recharge.dao.rdbms.BaseDaoRdbms;
import com.ibm.virtualization.recharge.db.DBConnectionManager;
import com.ibm.virtualization.recharge.exception.DAOException;

public class InterSsdTransferAdminDaoImpl  extends BaseDaoRdbms implements InterSsdTransferAdminDao {
	public static Logger logger = Logger.getLogger(InterSsdTransferAdminDaoImpl.class.getName());
	
	
	
	public InterSsdTransferAdminDaoImpl(Connection connection) 
	{
		super(connection);
	}

	public static final String SQL_CIRCLE_MST 	= DBQueries.SQL_SELECT_CIRCLES;
	public static final String SQL_GET_SERIAL_NO 	= DBQueries.SQL_GET_SERIAL_NO;
	public static final String SQL_GET_SERIAL_SEC_DONE 	= DBQueries.SQL_GET_SERIAL_SEC_DONE;
	public static final String SQL_GET_CHURN_INACTIVE= DBQueries.SQL_GET_INAC_CHURN;
	public static final String SQL_GET_SERIAL_INACTIVE=DBQueries.GET_INACTIVE_PARTNER_STOCK_LIST;
	public static final String SQL_UPDATE_STOCK_INVENTORY 	= DBQueries.SQL_UPDATE_STOCK_INVENTORY;
	public static final String SQL_INSERT_STOCK_TRANSFER 	= "INSERT into DP_DIST_STOCK_TRANSFER(REQ_ID, FROM_DIST_ID, TO_DIST_ID," +
			" PRODUCT_ID, REQ_TRANSFER_QTY, STATUS,DC_NO, CREATED_BY, CREATED_DATE,TRANSFERRED_QTY,DC_DATE,UPDATED_BY,UPDATED_DATE)" +
			" values(?,?,?,?,?,?,?,?,current timestamp,?,current date,?,current timestamp)";
	public static final String SQL_INSERT_DDST_SERIAL_NO	= "INSERT INTO DP_DDST_SERIAL_NO(REQ_ID, DC_NO, SERIAL_NO, ACTION, PRODUCT_ID)" +
															  "   VALUES(?, ?, ?, ?,?)";
	public static final String SQL_SELECT_DCNO              = "SELECT 'TRNSDC'||CHAR(SEQ_STOCK_TRANSFER_DC.NEXTVAL) FROM SYSIBM.SYSDUMMY1";
	public static final String SQL_SELECT_REQID             = "SELECT CHAR(SEQ_DP_DIST_STOCK_TRANSFER.NEXTVAL) FROM SYSIBM.SYSDUMMY1" ;
		
	
	public List<CircleDto> getAllCircleList() throws DAOException 
	{
		List<CircleDto> circleListDTO	= new ArrayList<CircleDto>();
		PreparedStatement pstmt = null;
		ResultSet rset			= null;
		CircleDto  circleDto = null;
				
		try
		{
			pstmt = connection.prepareStatement(SQL_CIRCLE_MST);
			rset = pstmt.executeQuery();
			circleListDTO = new ArrayList<CircleDto>();
			
			while(rset.next())
			{
				circleDto = new CircleDto();
				circleDto.setCircleId(rset.getString("CIRCLE_ID"));
				circleDto.setCircleName(rset.getString("CIRCLE_NAME"));
				
								
				circleListDTO.add(circleDto);
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
		return circleListDTO;
	}
	
	public ArrayList getAvailableSerialNos(InterSsdTransferAdminDto ssdDto) throws DAOException 
	{
		ArrayList serialNosList = new ArrayList();
		PreparedStatement pstmt = null;
		ResultSet rset			= null;
		InterSsdTransferAdminDto ssdDto2 =null;
				
		try
		{
			String productId = ssdDto.getProductId();
			String fromDistId = ssdDto.getFromDistId();
			if(null==ssdDto.getStockType())
				pstmt = connection.prepareStatement(SQL_GET_SERIAL_NO);
			
			if(null!=ssdDto.getStockType() && ssdDto.getStockType().equals("1"))
				pstmt = connection.prepareStatement(SQL_GET_SERIAL_NO);
			else if(null!=ssdDto.getStockType() && ssdDto.getStockType().equals("2"))
				pstmt=connection.prepareStatement(SQL_GET_SERIAL_INACTIVE);
			else if(null!=ssdDto.getStockType() && ssdDto.getStockType().equals("3"))
				pstmt=connection.prepareStatement(SQL_GET_SERIAL_SEC_DONE);	
			else if(null!=ssdDto.getStockType() && ssdDto.getStockType().equals("4"))
				pstmt=connection.prepareStatement(SQL_GET_CHURN_INACTIVE);
			pstmt.setString(1, productId);
			pstmt.setString(2, fromDistId);
			rset = pstmt.executeQuery();
			while(rset.next())
			{
				ssdDto2 = new InterSsdTransferAdminDto();
				logger.info("Serial No. == "+rset.getString("SERIAL_NO"));
				ssdDto2.setSerialNo(rset.getString("SERIAL_NO"));
				serialNosList.add(ssdDto2);
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
		}
		return serialNosList;
	}
	
	public String insertStockTransfs(ListIterator<InterSsdTransferAdminDto> interssdDtoListItr) throws DAOException
	{
		PreparedStatement ps = null;
		PreparedStatement ps1 = null;
		PreparedStatement ps2 = null;
		String strMessage ="SUCCESS";
		try
		{
			connection.setAutoCommit(false);
			InterSsdTransferAdminDto ssdDto = null;
			String strDcNo = "";
			String reqId="";
			String strSrNoList = "";
			String strProductId = "";
			String strprodQty ="";
			String strFromDistId ="";
			String strTodist ="";
			String strCircleId ="";
			String loginId ="";
			String[] arrSrNos =null;
			ps = connection.prepareStatement(SQL_INSERT_STOCK_TRANSFER);
			ps1 = connection.prepareStatement(SQL_INSERT_DDST_SERIAL_NO);
			ps2 = connection.prepareStatement(SQL_UPDATE_STOCK_INVENTORY);
			int count=0;
			int trnsfrCount =0;
			while(interssdDtoListItr.hasNext()) 
			{	
				
				 if(trnsfrCount == 0){
					 strDcNo = getDCNumber();
					 strMessage = strDcNo;
				 }
				reqId = getReqId();
				logger.info("strDcNo ==== "+strDcNo +"  and ReqId == "+reqId);
				ssdDto = interssdDtoListItr.next();
				strSrNoList = ssdDto.getSerialNo();
				arrSrNos = strSrNoList.split("A");
				strProductId = ssdDto.getProductId();
				strprodQty=ssdDto.getProdQty();
				strCircleId=ssdDto.getCircleId();
				strFromDistId =ssdDto.getFromDistId();
				strTodist= ssdDto.getToDistId();
				loginId =ssdDto.getLoginUser();
				
				//To insert into DP_DIST_STOCK_TRANSFER 
				ps.setString(1, reqId);
				ps.setString(2, strFromDistId);
				ps.setString(3, strTodist);
				ps.setString(4, strProductId);
				ps.setString(5, strprodQty);
				ps.setString(6, Constants.STOCK_TRANSFER_STATUS_RECEIVED);
				ps.setString(7, strDcNo);
				ps.setString(8, loginId);   
				ps.setString(9, strprodQty);
				ps.setString(10, loginId);   
				ps.executeUpdate();
				
				//To insert into DP_DDST_SERIAL_NO
				 for(count=0; count<arrSrNos.length;count++){
					 ps1.setString(1, reqId);
					 ps1.setString(2, strDcNo);
					 ps1.setString(3, arrSrNos[count]);
					 ps1.setString(4, Constants.STOCK_TRANSFER_STATUS_ACCEPTED);
					 ps1.setString(5, strProductId);
					 ps1.executeUpdate();
					 
					 //To uodate into SQL_UPDATE_STOCK_INVENTORY
					 ps2.setString(1, strTodist);
					 ps2.setString(2, arrSrNos[count]);
					 ps2.executeUpdate();
				 }
				 
				 
				 trnsfrCount ++;
				 logger.info("Inserted into both tables succesfully");
				 
			}
			
			logger.info("Str message == "+strMessage);
			connection.commit();	
		}
		catch (Exception e) 
		{
			e.printStackTrace();
			strMessage ="FAILURE";
			try
			{
				String err = ResourceReader.getCoreResourceBundleValue(Constants.InterSSD_Stock_Transfer_Critical);
				logger.info(err + "::" +Utility.getCurrentDate());
			}
			catch(Exception ex)
			{
				ex.printStackTrace();
			}
			throw new DAOException(e.getMessage());
		}
		finally
		{
			DBConnectionManager.releaseResources(ps, null);
			DBConnectionManager.releaseResources(ps1, null);
			DBConnectionManager.releaseResources(ps2, null);
		}
		return strMessage;
	}
	//Commneted by Shilpa On 01-6-2012 for UAT observation to send DC Nos in return
	/*
	public String insertStockTransfs(ListIterator<InterSsdTransferAdminDto> interssdDtoListItr) throws DAOException
	{
		PreparedStatement ps = null;
		PreparedStatement ps1 = null;
		PreparedStatement ps2 = null;
		String strMessage ="SUCCESS";
		try
		{
			connection.setAutoCommit(false);
			InterSsdTransferAdminDto ssdDto = null;
			String strDcNo = "";
			String reqId="";
			String strSrNoList = "";
			String strProductId = "";
			String strprodQty ="";
			String strFromDistId ="";
			String strTodist ="";
			String strCircleId ="";
			String loginId ="";
			String[] arrSrNos =null;
			ps = connection.prepareStatement(SQL_INSERT_STOCK_TRANSFER);
			ps1 = connection.prepareStatement(SQL_INSERT_DDST_SERIAL_NO);
			ps2 = connection.prepareStatement(SQL_UPDATE_STOCK_INVENTORY);
			int count=0;
			 while(interssdDtoListItr.hasNext()) 
			{	
				strDcNo = getDCNumber();
				reqId = getReqId();
				logger.info("strDcNo ==== "+strDcNo +"  and ReqId == "+reqId);
				ssdDto = interssdDtoListItr.next();
				strSrNoList = ssdDto.getSerialNo();
				arrSrNos = strSrNoList.split("A");
				strProductId = ssdDto.getProductId();
				strprodQty=ssdDto.getProdQty();
				strCircleId=ssdDto.getCircleId();
				strFromDistId =ssdDto.getFromDistId();
				strTodist= ssdDto.getToDistId();
				loginId =ssdDto.getLoginUser();
				
				//To insert into DP_DIST_STOCK_TRANSFER 
				ps.setString(1, reqId);
				ps.setString(2, strFromDistId);
				ps.setString(3, strTodist);
				ps.setString(4, strProductId);
				ps.setString(5, strprodQty);
				ps.setString(6, Constants.STOCK_TRANSFER_STATUS_RECEIVED);
				ps.setString(7, strDcNo);
				ps.setString(8, loginId);   
				ps.setString(9, strprodQty);
				ps.setString(10, loginId);   
				ps.executeUpdate();
				
				//To insert into DP_DDST_SERIAL_NO
				 for(count=0; count<arrSrNos.length;count++){
					 ps1.setString(1, reqId);
					 ps1.setString(2, strDcNo);
					 ps1.setString(3, arrSrNos[count]);
					 ps1.setString(4, Constants.STOCK_TRANSFER_STATUS_ACCEPTED);
					 ps1.setString(5, strProductId);
					 ps1.executeUpdate();
					 
					 //To uodate into SQL_UPDATE_STOCK_INVENTORY
					 ps2.setString(1, strTodist);
					 ps2.setString(2, arrSrNos[count]);
					 ps2.executeUpdate();
				 }
				 
				
				 
				 
				 logger.info("Inserted into both tables succesfully");
				 
			}
			
			
			connection.commit();	
		}
		catch (Exception e) 
		{
			e.printStackTrace();
			strMessage ="FAILURE";
			throw new DAOException(e.getMessage());
		}
		finally
		{
			DBConnectionManager.releaseResources(ps, null);
			DBConnectionManager.releaseResources(ps1, null);
			DBConnectionManager.releaseResources(ps2, null);
		}
		return strMessage;
	}*/
	
	public String getDCNumber(){
		ResultSet rst = null;
		PreparedStatement pst = null;
		String dcNumber = "";
		try {
			pst = connection.prepareStatement(SQL_SELECT_DCNO);
			rst = pst.executeQuery();
			if(rst.next()){
				dcNumber = rst.getString(1);
			}
		} catch (SQLException e) {
			logger.error("**-> getDCNumber function of  InterSsdTransferAdminDaoImpl " + e);
		} catch (Exception ex) {
			logger.error("**-> getDCNumber function of  InterSsdTransferAdminDaoImpl " + ex);
		} finally {
			DBConnectionManager.releaseResources(pst, rst);
		}
		return dcNumber;
	}
	public String getReqId(){
		ResultSet rst = null;
		PreparedStatement pst = null;
		String reqId = "";
		try {
			pst = connection.prepareStatement(SQL_SELECT_REQID);
			rst = pst.executeQuery();
			if(rst.next()){
				reqId = rst.getString(1);
			}
		} catch (SQLException e) {
			logger.error("**-> getDCNumber function of  InterSsdTransferAdminDaoImpl " + e);
		} catch (Exception ex) {
			logger.error("**-> getDCNumber function of  InterSsdTransferAdminDaoImpl " + ex);
		} finally {
			DBConnectionManager.releaseResources(pst, rst);
		}
		return reqId;
	}

	public List<List<CircleDto>> getInitData() throws DAOException 
	{
		List<List<CircleDto>> retListDTO	= new ArrayList<List<CircleDto>>();
		List<CircleDto> circleListDTO = new ArrayList<CircleDto>();
		List<CircleDto> busCatListDTO = new ArrayList<CircleDto>();
		
		PreparedStatement pstmt = null;
		ResultSet rset			= null;
		CircleDto  circleDto = null;
				
		try
		{
			pstmt = connection.prepareStatement(SQL_CIRCLE_MST);
			rset = pstmt.executeQuery();
			circleListDTO = new ArrayList<CircleDto>();
			
			while(rset.next())
			{
				circleDto = new CircleDto();
				circleDto.setCircleId(rset.getString("CIRCLE_ID"));
				circleDto.setCircleName(rset.getString("CIRCLE_NAME"));
								
				circleListDTO.add(circleDto);
			}
			
			retListDTO.add(circleListDTO);
			
			DBConnectionManager.releaseResources(pstmt, rset);
			
			pstmt = connection.prepareStatement(DBQueries.SQL_BUSINESS_CATEGORY);
			rset = pstmt.executeQuery();
			while(rset.next())
			{
				circleDto = new CircleDto();
				circleDto.setCircleId(rset.getString("CATEGORY_CODE"));
				circleDto.setCircleName(rset.getString("CATEGORY_NAME"));
								
				busCatListDTO.add(circleDto);
			}
			
			retListDTO.add(busCatListDTO);
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
		return retListDTO;
	}

	public List<StockSummaryReportBean> getFromDistAccountDetails(int intTSMID, int circleId, int intBusCat) 
	throws DAOException 
	{
		PreparedStatement ps = null;
		ResultSet rs = null;
		List<StockSummaryReportBean> revLogList = new ArrayList<StockSummaryReportBean>();
		try 
		{
			/*
			int intDistTransType = 1;
			
			if(intBusCat==1)
				intDistTransType = 2;
				
			*/
			
			ps = connection.prepareStatement(DBQueries.GET_TSM_DIST_TRANSACTION);
			ps.setInt(1, intTSMID);
			ps.setInt(2, circleId);
			// ps.setInt(3, intDistTransType);
			
			rs = ps.executeQuery();
			
			StockSummaryReportBean statusReportBean = null;
			while(rs.next())
			{
				statusReportBean = new StockSummaryReportBean();
				statusReportBean.setDistAccId(String.valueOf(rs.getInt("account_id")));
				statusReportBean.setDistAccName(rs.getString("account_name"));
				revLogList.add(statusReportBean);
			}
		}catch(SQLException sqle){
			sqle.printStackTrace();
			throw new DAOException("SQLException: " + sqle.getMessage());
		}catch(Exception e){
			e.printStackTrace();
			throw new DAOException("Exception: " + e.getMessage());
		}finally{
			try {
				if (rs != null)
					rs.close();
				if (ps != null)
					ps.close();
			} catch (Exception e) {
				e.printStackTrace();
				logger.error("Error Occured in ReportDAOImpl "+e.getMessage());
			}
		}
		
		return revLogList;
	}

	public List<StockSummaryReportBean> getToDistAccountDetailsDAO(int intTSMID, int circleId, int intBusCat) throws DAOException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		List<StockSummaryReportBean> revLogList = new ArrayList<StockSummaryReportBean>();
		try 
		{
			
			/*
			int intDistTransType = 1;
			
			if(intBusCat==1)
				intDistTransType = 2;
				
				*/
			
			ps = connection.prepareStatement(DBQueries.GET_TSM_TODIST_TRANSACTION);
			ps.setInt(1, intTSMID);
			ps.setInt(2, circleId);
			// ps.setInt(3, intDistTransType);
			
			rs = ps.executeQuery();
			
			StockSummaryReportBean statusReportBean = null;
			while(rs.next())
			{
				statusReportBean = new StockSummaryReportBean();
				statusReportBean.setDistAccId(String.valueOf(rs.getInt("account_id")));
				statusReportBean.setDistAccName(rs.getString("account_name"));
				revLogList.add(statusReportBean);
			}
		}catch(SQLException sqle){
			sqle.printStackTrace();
			throw new DAOException("SQLException: " + sqle.getMessage());
		}catch(Exception e){
			e.printStackTrace();
			throw new DAOException("Exception: " + e.getMessage());
		}finally{
			try {
				if (rs != null)
					rs.close();
				if (ps != null)
					ps.close();
			} catch (Exception e) {
				e.printStackTrace();
				logger.error("Error Occured in ReportDAOImpl "+e.getMessage());
			}
		}
		
		return revLogList;
	}
	
	@Override
	public List<StockSummaryReportBean> getFromInactiveDistAccountDetails(
			int intTSMID, int circleId, int intBusCat) throws DAOException {
		// TODO Auto-generated method stub
		PreparedStatement ps = null;
		ResultSet rs = null;
		List<StockSummaryReportBean> revLogList = new ArrayList<StockSummaryReportBean>();
		try 
		{
			/*
			int intDistTransType = 1;
			
			if(intBusCat==1)
				intDistTransType = 2;
				
			*/
			
			ps = connection.prepareStatement(DBQueries.GET_TSM_DIST_TRANSACTION_INAC);
			ps.setInt(1, intTSMID);
			ps.setInt(2, circleId);
			// ps.setInt(3, intDistTransType);
			
			rs = ps.executeQuery();
			
			StockSummaryReportBean statusReportBean = null;
			while(rs.next())
			{
				statusReportBean = new StockSummaryReportBean();
				statusReportBean.setDistAccId(String.valueOf(rs.getInt("account_id")));
				statusReportBean.setDistAccName(rs.getString("account_name"));
				revLogList.add(statusReportBean);
			}
		}catch(SQLException sqle){
			sqle.printStackTrace();
			throw new DAOException("SQLException: " + sqle.getMessage());
		}catch(Exception e){
			e.printStackTrace();
			throw new DAOException("Exception: " + e.getMessage());
		}finally{
			try {
				if (rs != null)
					rs.close();
				if (ps != null)
					ps.close();
			} catch (Exception e) {
				e.printStackTrace();
				logger.error("Error Occured in ReportDAOImpl "+e.getMessage());
			}
		}
		
		return revLogList;
	}
}
