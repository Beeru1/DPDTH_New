package com.ibm.dp.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;

import com.ibm.dp.beans.StockSummaryReportBean;
import com.ibm.dp.common.Constants;
import com.ibm.dp.common.DBQueries;
import com.ibm.dp.dao.StockSummaryReportDao;
import com.ibm.dp.dto.ProductMasterDto;
import com.ibm.dp.dto.StockSummaryReportDto;
import com.ibm.utilities.Utility;
import com.ibm.virtualization.recharge.common.ResourceReader;
import com.ibm.virtualization.recharge.dao.rdbms.BaseDaoRdbms;
import com.ibm.virtualization.recharge.db.DBConnectionManager;
import com.ibm.virtualization.recharge.exception.DAOException;

public class StockSummaryReportDaoImpl extends BaseDaoRdbms implements StockSummaryReportDao{
	
	private Logger logger = Logger.getLogger(StockSummaryReportDaoImpl.class.getName());
	public StockSummaryReportDaoImpl(Connection connection) {
		super(connection);
	}
	
	public List<ProductMasterDto> getProductTypeMaster(String circleId, String dcNo) throws DAOException 
	{
		Connection con = null;
		List<ProductMasterDto> productTypeListDTO	= new ArrayList<ProductMasterDto>();
		PreparedStatement pstmt = null;
		ResultSet rset			= null;
		ProductMasterDto  productTypeDto = null;
				
		try
		{
			con = DBConnectionManager.getDBConnection(); // db2
			String categoryCode = Utility.getDcDetail(con, dcNo);
			
			if((circleId).equals("0")){
				pstmt = con.prepareStatement(DBQueries.SQL_ALL_PRODUCT_lIST_REPORT);
			}else{
				pstmt = con.prepareStatement(DBQueries.SQL_PRODUCT_lIST_REPORT);
				pstmt.setString(1, circleId);
				pstmt.setString(2, categoryCode);
			}
			rset = pstmt.executeQuery();
			
			productTypeListDTO = new ArrayList<ProductMasterDto>();
			
			while(rset.next())
			{
				productTypeDto = new ProductMasterDto();
				productTypeDto.setProductId(rset.getString("PRODUCT_ID"));
				productTypeDto.setProductName(rset.getString("PRODUCT_NAME"));
				
								
				productTypeListDTO.add(productTypeDto);
			}
		}
		catch (Exception e) 
		{
			e.printStackTrace();
			try
			{
				String err = ResourceReader.getCoreResourceBundleValue(Constants.PO_Stock_Acceptance_Upload_Critical);
				logger.info(err + "::" +Utility.getCurrentDate());
			}
			catch(Exception ex1)
			{
				ex1.printStackTrace();
			}
			throw new DAOException(e.getMessage());
		}
		finally
		{
			try {if (rset != null)
				rset.close();
			if (pstmt != null)
				pstmt.close();
			if (con != null) {
				con.close();
			}
			
		}
	catch (Exception e) {
					e.printStackTrace();
					logger.error("Error Occured in ReportDAOImpl "+e.getMessage());
				}
		}
		return productTypeListDTO;
	}
	public List<ProductMasterDto> getProductTypeMaster(String circleId) throws DAOException 
	{
		Connection con = null;
		List<ProductMasterDto> productTypeListDTO	= new ArrayList<ProductMasterDto>();
		PreparedStatement pstmt = null;
		ResultSet rset			= null;
		ProductMasterDto  productTypeDto = null;
				
		try
		{
			con = DBConnectionManager.getDBConnection(); // db2
			if((circleId).equals("0")){
				pstmt = con.prepareStatement(DBQueries.SQL_ALL_PRODUCT_lIST_REPORT);
			}else{
				pstmt = con.prepareStatement(DBQueries.SQL_PRODUCT_lIST_REPORT);
				pstmt.setString(1, circleId);
			}
			rset = pstmt.executeQuery();
			
			productTypeListDTO = new ArrayList<ProductMasterDto>();
			
			while(rset.next())
			{
				productTypeDto = new ProductMasterDto();
				productTypeDto.setProductId(rset.getString("PRODUCT_ID"));
				productTypeDto.setProductName(rset.getString("PRODUCT_NAME"));
				
								
				productTypeListDTO.add(productTypeDto);
			}
		}
		catch (Exception e) 
		{
			e.printStackTrace();
			try
			{
				String err = ResourceReader.getCoreResourceBundleValue(Constants.PO_Stock_Acceptance_Upload_Critical);
				logger.info(err + "::" +Utility.getCurrentDate());
			}
			catch(Exception ex1)
			{
				ex1.printStackTrace();
			}
			throw new DAOException(e.getMessage());
		}
		finally
		{
			try {if (rset != null)
				rset.close();
			if (pstmt != null)
				pstmt.close();
			if (con != null) {
				con.close();
			}
			
		}
	catch (Exception e) {
					e.printStackTrace();
					logger.error("Error Occured in ReportDAOImpl "+e.getMessage());
				}
		}
		return productTypeListDTO;
	}
	
	
	public List getRevLogTsmDistLogin(int distId) throws DAOException {

		Connection con = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		List<StockSummaryReportBean> revLogList = new ArrayList<StockSummaryReportBean>();
		try 
		{
			con = DBConnectionManager.getDBConnection(); // db2
			
			
			System.out.println("distId--"+distId);
			// Fetch level base
				ps = con.prepareStatement(DBQueries.GET_REV_LOG_DISABLE_TSM_DIST);
				ps.setInt(1, distId);
				rs = ps.executeQuery();
			
				StockSummaryReportBean statusReportBean = null;
			while(rs.next())
			{
				statusReportBean = new StockSummaryReportBean();
				statusReportBean.setAccountId(String.valueOf(rs.getInt("account_id")));
				statusReportBean.setAccountName(rs.getString("account_name"));
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
				if (con != null) {
					con.close();
				}
			} catch (Exception e) {
				e.printStackTrace();
				logger.error("Error Occured in ReportDAOImpl "+e.getMessage());
			}
		}
		
		return revLogList;

	}
	
	public List getRevLogTsmAccountDetails(int circleId) throws DAOException {

		Connection con = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		List<StockSummaryReportBean> revLogList = new ArrayList<StockSummaryReportBean>();
		try 
		{
			con = DBConnectionManager.getDBConnection(); // db2
			
			
			System.out.println("levelId--"+circleId);
			// Fetch level base
			if(circleId == 0)
			{
				ps = con.prepareStatement(DBQueries.GET_ACCOUNT_DETAILS_REV_LOG_TSM_ALL);
				ps.setInt(1, 5);
			}
			else
			{
				ps = con.prepareStatement(DBQueries.GET_ACCOUNT_DETAILS_REV_LOG_TSM);
				ps.setInt(1, 5);
				ps.setInt(2, circleId);
			}
			
			rs = ps.executeQuery();
			
			StockSummaryReportBean statusReportBean = null;
			while(rs.next())
			{
				statusReportBean = new StockSummaryReportBean();
				statusReportBean.setAccountId(String.valueOf(rs.getInt("account_id")));
				statusReportBean.setAccountName(rs.getString("account_name"));
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
				if (con != null) {
					con.close();
				}
			} catch (Exception e) {
				e.printStackTrace();
				logger.error("Error Occured in ReportDAOImpl "+e.getMessage());
			}
		}
		
		return revLogList;

	}
	
	public List getRevLogDistAccountDetails(int levelId , int circleId) throws DAOException
	{
		Connection con = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		List<StockSummaryReportBean> revLogList = new ArrayList<StockSummaryReportBean>();
		try 
		{
			con = DBConnectionManager.getDBConnection(); // db2
			
			
			
			// Fetch level base
			
			if(levelId != 0 && circleId != 0)
			{	
				ps = con.prepareStatement(DBQueries.GET_ACCOUNT_DETAILS_REV_LOG_DIST);
				ps.setInt(1, levelId);
				ps.setInt(2, circleId);
			}
			if(levelId != 0 && circleId == 0)
			{
				ps = con.prepareStatement(DBQueries.GET_ACCOUNT_DETAILS_REV_LOG_DIST);
				ps.setInt(1, levelId);
				ps.setInt(2, circleId);
			}
			
			if(levelId == 0 && circleId == 0)
			{
				ps = con.prepareStatement(DBQueries.GET_ACCOUNT_DETAILS_REV_LOG_DIST_ALL);
				ps.setInt(1, 6);
			}
			
			if(levelId == 0 && circleId != 0)
			{
				ps = con.prepareStatement(DBQueries.GET_ACCOUNT_DETAILS_REV_LOG_DIST_ALL_TSM_ALL);
				ps.setInt(1, 6);
				ps.setInt(2, circleId);
			}
			
			
			
			/*if(levelId == 0 && )
			{
				ps = con.prepareStatement(DBQueries.GET_ACCOUNT_DETAILS_REV_LOG_DIST_ALL);
				ps.setInt(1, 6);
			}
			else if(circleId != 0)
			{
				ps = con.prepareStatement(DBQueries.GET_ACCOUNT_DETAILS_REV_LOG_DIST);
				ps.setInt(1, levelId);
			}
			else if(circleId == 0 && levelId == 0)
			{
				ps = con.prepareStatement(DBQueries.GET_ACCOUNT_DETAILS_REV_LOG_DIST_ALL);
				ps.setInt(1, levelId);
			}*/
			
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
				if (con != null) {
					con.close();
				}
			} catch (Exception e) {
				e.printStackTrace();
				logger.error("Error Occured in ReportDAOImpl "+e.getMessage());
			}
		}
		
		return revLogList;
	}

	public List getRevLogFromDistAccountDetails(int levelId , int circleId) throws DAOException
	{
		Connection con = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		List<StockSummaryReportBean> revLogList = new ArrayList<StockSummaryReportBean>();
		try 
		{
			con = DBConnectionManager.getDBConnection(); // db2
	
			// Fetch level base		
	
			
			if(levelId != 0 && circleId != 0)
			{

				ps = con.prepareStatement(DBQueries.GET_ACCOUNT_DETAILS_REV_LOG_FROM_DIST);
				ps.setInt(1, levelId);
				ps.setInt(2, circleId);
			}
			if(levelId != 0 && circleId == 0)
			{

				ps = con.prepareStatement(DBQueries.GET_ACCOUNT_DETAILS_REV_LOG_FROM_DIST);
				ps.setInt(1, levelId);
				ps.setInt(2, circleId);
			}
			
			if(levelId == 0 && circleId == 0)
			{

				ps = con.prepareStatement(DBQueries.GET_ACCOUNT_DETAILS_REV_LOG_FROM_DIST_ALL);
				ps.setInt(1, 6);
			}
			
			if(levelId == 0 && circleId != 0)
			{

				ps = con.prepareStatement(DBQueries.GET_ACCOUNT_DETAILS_REV_LOG_FROM_DIST_ALL_TSM_ALL);
				ps.setInt(1, 6);
				ps.setInt(2, circleId);
			}
			
			
			
			/*if(levelId == 0 && )
			{
				ps = con.prepareStatement(DBQueries.GET_ACCOUNT_DETAILS_REV_LOG_DIST_ALL);
				ps.setInt(1, 6);
			}
			else if(circleId != 0)
			{
				ps = con.prepareStatement(DBQueries.GET_ACCOUNT_DETAILS_REV_LOG_DIST);
				ps.setInt(1, levelId);
			}
			else if(circleId == 0 && levelId == 0)
			{
				ps = con.prepareStatement(DBQueries.GET_ACCOUNT_DETAILS_REV_LOG_DIST_ALL);
				ps.setInt(1, levelId);
			}*/
			
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
				if (con != null) {
					con.close();
				}
			} catch (Exception e) {
				e.printStackTrace();
				logger.error("Error Occured in ReportDAOImpl "+e.getMessage());
			}
		}
		
		return revLogList;
	}
		public List<StockSummaryReportDto> getRevLogAllTsm(long accountId,String accountLevel) throws DAOException
		{

			Connection con = null;
			PreparedStatement ps = null;
			ResultSet rsDTH = null;
			List<StockSummaryReportDto> tempDistList=new ArrayList<StockSummaryReportDto>();
			
			try 
			{
				con = DBConnectionManager.getDBConnection(); // db2
				
				// Fetch level base
				if(accountLevel.equalsIgnoreCase("3"))// ZBM
				    ps = con.prepareStatement(DBQueries.GET_ACCOUNT_DETAILS_ZBM);
				else if(accountLevel.equalsIgnoreCase("5"))// TSM
					ps = con.prepareStatement(DBQueries.GET_ACCOUNT_DETAILS_TSM);		
				ps.setLong(1, accountId);
				rsDTH = ps.executeQuery();	
				logger.info("*******Query executed successfully *********");
				
				while(rsDTH.next())
				{	
					StockSummaryReportDto revLogReportdto= new StockSummaryReportDto();
					revLogReportdto.setDistID(String.valueOf(rsDTH.getInt("account_id")));
					revLogReportdto.setDistName(rsDTH.getString("account_name"));
					tempDistList.add(revLogReportdto);
				}
			}catch(SQLException sqle){
				sqle.printStackTrace();
				throw new DAOException("SQLException: " + sqle.getMessage());
			}catch(Exception e){
				e.printStackTrace();
				throw new DAOException("Exception: " + e.getMessage());
			}finally{
				try {
					if (rsDTH != null)
						rsDTH.close();
					if (ps != null)
						ps.close();
					if (con != null) {
						con.close();
					}
				} catch (Exception e) {
					e.printStackTrace();
					logger.error("Error Occured in ReportDAOImpl "+e.getMessage());
				}
			}
			
			return tempDistList;
		}

//	public List<StockSummaryReportDto> getRevLogReportExcel(int distId, String fromDate, String toDate , String circleId , String tsmId) throws DAOException
//	{
//		//Connection con = null;
//		PreparedStatement ps1 = null;
//		ResultSet rs1 = null;
//		
//		Connection con = null;
//		
//		List<StockSummaryReportDto>reportList = new ArrayList<StockSummaryReportDto>();
//		StockSummaryReportDto stockSummaryReportDto = null ;
//		String strProdId = "";
//		Integer[] lst=new Integer[5];
//		lst[0] =0; lst[1] =0; lst[2] =0; lst[3] =0; lst[4] =0; 
//		
//		Map<String, Integer[]>	mapNew = new HashMap<String, Integer[]>();	
//		try 
//		{
//			con = DBConnectionManager.getDBConnection(); // db2
//		
//		//Code for one dist All Product 
//			ps1 = 	con.prepareStatement(DBQueries.GET_REPORT_LIST_ONE_DIST_ALL_PROD);
//		rs1=ps1.executeQuery();
//		
//		while(rs1.next()){
//			if(rs1.getString(1).equals("STK_INV")){
//				strProdId = rs1.getString(2);
//				if(mapNew.containsValue(strProdId)){
//					lst = mapNew.get(strProdId);
//					lst[0] =lst[0]+rs1.getInt(4);
//					mapNew.remove(strProdId);
//					mapNew.put(strProdId, lst);
//				}else{
//					lst[0] =rs1.getInt(4);
//					mapNew.put(strProdId, lst);
//				}
//				
//			}
//			if(rs1.getString(1).equals("STK_INV_ASS")){
//				strProdId = rs1.getString(2);
//				if(mapNew.containsValue(strProdId)){
//					lst = mapNew.get(strProdId);
//					lst[1] = lst[1]+rs1.getInt(4);
//					mapNew.remove(strProdId);
//					mapNew.put(strProdId, lst);
//				}else{
//					lst[1] =rs1.getInt(4);
//					mapNew.put(strProdId, lst);
//				}
//				
//			}
//			if(rs1.getString(1).equals("STK_INV_OPN")){
//				strProdId = rs1.getString(2);
//				if(mapNew.containsValue(strProdId)){
//					lst = mapNew.get(strProdId);
//					lst[2] = lst[2]+rs1.getInt(4);
//					mapNew.remove(strProdId);
//					mapNew.put(strProdId, lst);
//				}else{
//					lst[2] =rs1.getInt(4);
//					mapNew.put(strProdId, lst);
//				}
//				
//			}
//			if(rs1.getString(1).equals("STK_INV_REV")){
//				strProdId = rs1.getString(2);
//				if(mapNew.containsValue(strProdId)){
//					lst = mapNew.get(strProdId);
//					lst[3] = lst[3]+rs1.getInt(4);
//					mapNew.remove(strProdId);
//					mapNew.put(strProdId, lst);
//				}else{
//					lst[3] =rs1.getInt(4);
//					mapNew.put(strProdId, lst);
//				}
//				
//			}
//			if(rs1.getString(1).equals("STK_INV_REV_S2W")){
//				strProdId = rs1.getString(2);
//				if(mapNew.containsValue(strProdId)){
//					lst = mapNew.get(strProdId);
//					lst[4] = lst[4]+rs1.getInt(4);
//					mapNew.remove(strProdId);
//					mapNew.put(strProdId, lst);
//				}else{
//					lst[4] =rs1.getInt(4);
//					mapNew.put(strProdId, lst);
//					
//				}
//				
//			}
//		}
//		for(int count=0;count<mapNew.size();count++){
//			stockSummaryReportDto =new StockSummaryReportDto();
//			//stockSummaryReportDto.set
//		}
//		
//		
//}catch(SQLException sqle){
//		sqle.printStackTrace();
//		throw new DAOException("SQLException: " + sqle.getMessage());
//	}catch(Exception e){
//		e.printStackTrace();
//		throw new DAOException("Exception: " + e.getMessage());
//	}finally{
//		try {
//			if (rs1 != null)
//				rs1.close();
//			if (ps1 != null)
//				ps1.close();
//			if (con != null) {
//				con.close();
//			}
//		} catch (Exception e) {
//			e.printStackTrace();
//			logger.error("Error Occured in ReportDAOImpl "+e.getMessage());
//		}
//	}
//		return reportList;
//	}
//}

	public List<StockSummaryReportDto> getRevLogReportExcel(int distId, String fromDate, String toDate , String circleId , String tsmId,int loginRole,String productId) throws DAOException
	{
		List<StockSummaryReportDto> listReturn = new ArrayList<StockSummaryReportDto>();
		PreparedStatement pstmtStock = null;
		PreparedStatement pstmtDist = null;
		ResultSet rsetStock = null;
		ResultSet rsetDist = null;
		logger.info(" in  &&&&&&&&&&&&&&&&&&&&& getRevLogReportExcel");
		try
		{
			int intDistID;
			String strDistName, strParentName = null;
			//Prepare queries here-----------
			
			
			if(loginRole ==6){
				logger.info(" login role == "+loginRole);
				pstmtDist = connection.prepareStatement(DBQueries.GET_STOCK_SUMM_DIST_LIST);
				pstmtDist.setString(1, String.valueOf(distId));  //Dist Id 
				rsetDist = pstmtDist.executeQuery();
			}else if(loginRole ==5){
				logger.info(" login role == "+loginRole + "  circle -=== "+circleId);
				if(distId==0){
					pstmtDist = connection.prepareStatement(DBQueries.GET_STOCK_CIRCLE_DIST_LIST);
					pstmtDist.setString(1, String.valueOf(circleId));  //Circle Id 
					rsetDist = pstmtDist.executeQuery();
				}else{
					pstmtDist = connection.prepareStatement(DBQueries.GET_STOCK_SUMM_DIST_LIST);
					pstmtDist.setString(1, String.valueOf(distId));  //Dist Id 
					rsetDist = pstmtDist.executeQuery();
				}
			}else if(loginRole ==3 || loginRole ==4 ){
				
				logger.info(" login role == "+loginRole + "  circle -=== "+circleId + "   TSM ID  == "+tsmId);
				if(distId!=0){   // For All TSM one Dist AND One TSM One Dist
					pstmtDist = connection.prepareStatement(DBQueries.GET_STOCK_SUMM_DIST_LIST);
					pstmtDist.setString(1, String.valueOf(distId));  //Dist Id 
					rsetDist = pstmtDist.executeQuery();
				}else if(distId ==0 && tsmId.equals("0")){    //For All TSM All Dist
					pstmtDist = connection.prepareStatement(DBQueries.GET_STOCK_CIRCLE_DIST_LIST);
					pstmtDist.setString(1, String.valueOf(circleId));  //Circle Id 
					rsetDist = pstmtDist.executeQuery();
				}else if(distId ==0 && !tsmId.equals("0")){    //For One TSM All Dist
					pstmtDist = connection.prepareStatement(DBQueries.GET_STOCK_ALL_DIST_ONE_TSM_LIST);
					pstmtDist.setString(1, String.valueOf(circleId));  //Circle Id 
					pstmtDist.setString(2, String.valueOf(tsmId));  //TSM Id 
					rsetDist = pstmtDist.executeQuery();
				}
			}
			else if(loginRole ==0 || loginRole ==1 || loginRole ==2 ){
				logger.info(" distId=="+distId+"  login role == "+loginRole + "  circle -=== "+circleId + "   TSM ID  == "+tsmId);
				if(distId!=0){   // For All TSM one Dist AND One TSM One Dist
					pstmtDist = connection.prepareStatement(DBQueries.GET_STOCK_SUMM_DIST_LIST);
					pstmtDist.setString(1, String.valueOf(distId));  //Dist Id 
					rsetDist = pstmtDist.executeQuery();
				}else if(distId==0){    //For all Dist
					
					if(circleId.equals("0")&& tsmId.equals("0")){    //For All TSM All Circle 
						pstmtDist = connection.prepareStatement(DBQueries.GET_STOCK_ALL_DIST_LIST);
						rsetDist = pstmtDist.executeQuery();
					}else if(!circleId.equals("0")&& tsmId.equals("0")){    //For All TSM All Circle 
						pstmtDist = connection.prepareStatement(DBQueries.GET_STOCK_CIRCLE_DIST_LIST);
						pstmtDist.setString(1, String.valueOf(circleId));  //Circle Id 
						rsetDist = pstmtDist.executeQuery();
					}else if(circleId.equals("0")&& !tsmId.equals("0")){    //For One TSM  All Circle 
						pstmtDist = connection.prepareStatement(DBQueries.GET_STOCK_ALL_DIST_ONE_TSM_LIST_All_CR);
						pstmtDist.setString(1, String.valueOf(tsmId));  //Circle Id 
						rsetDist = pstmtDist.executeQuery();
					}else if(!circleId.equals("0")&& !tsmId.equals("0")){    //For One TSM  One Circle 
						pstmtDist = connection.prepareStatement(DBQueries.GET_STOCK_ALL_DIST_ONE_TSM_LIST);
						pstmtDist.setString(1, String.valueOf(circleId));  //Circle Id 
						pstmtDist.setString(2, String.valueOf(tsmId));  //TSM Id 
						rsetDist = pstmtDist.executeQuery();
					}
				}
			}
			
			
			if(productId.equals("0") && circleId.equals("0"))// All product // All circle
			{
				pstmtStock = connection.prepareStatement(DBQueries.GET_REPORT_LIST_ONE_DIST_ALL_PROD_PAN_INDIA);
				System.out.println("QUERY TO GET STOCK SUMMARY REPORT  ::  "+DBQueries.GET_REPORT_LIST_ONE_DIST_ALL_PROD_PAN_INDIA);
			}
			else if(productId.equals("0") && !circleId.equals("0"))// All product // circle
			{
				pstmtStock = connection.prepareStatement(DBQueries.GET_REPORT_LIST_ONE_DIST_ALL_PROD);
				System.out.println("QUERY TO GET STOCK SUMMARY REPORT  ::  "+DBQueries.GET_REPORT_LIST_ONE_DIST_ALL_PROD);
			}
			else
			{
				pstmtStock = connection.prepareStatement(DBQueries.GET_REPORT_LIST_ONE_DIST_ONE_PROD);
				System.out.println("QUERY TO GET STOCK SUMMARY REPORT  ::  "+DBQueries.GET_REPORT_LIST_ONE_DIST_ONE_PROD);
			}
			
			// Add by harbans 30th may
			while(rsetDist.next())
			{
				intDistID = rsetDist.getInt("ACCOUNT_ID");
				strDistName =  rsetDist.getString("ACCOUNT_NAME");
				strParentName =  rsetDist.getString("PARENT_NAME");
				
				if(productId.equals("0") && circleId.equals("0"))// All product // All circle
				{																
					pstmtStock.setString(1, String.valueOf(intDistID));  //Dist Id 
					pstmtStock.setString(2, fromDate);  //From Date
					pstmtStock.setString(3, toDate);  //To Date
					System.out.println("DIST  ::  "+intDistID);
					System.out.println("FROM DATE  ::  "+fromDate);
					System.out.println("TO DATE  ::  "+toDate);
					 
					pstmtStock.setString(4, String.valueOf(intDistID));  //Dist Id 
					pstmtStock.setString(5, fromDate);  //From Date
					pstmtStock.setString(6, toDate);  //To Date
					 
					pstmtStock.setString(7, String.valueOf(intDistID));  //Dist Id 
					 
					pstmtStock.setString(8, String.valueOf(intDistID));  //Dist Id 
					pstmtStock.setString(9, fromDate);  //From Date
					pstmtStock.setString(10, toDate);  //To Date
					 
					pstmtStock.setString(11, String.valueOf(intDistID));  //Dist Id 
					pstmtStock.setString(12, fromDate);  //From Date
					pstmtStock.setString(13, toDate);  //To Date
					
//					Added by Nazim Hussain for INVENTORY CHANGE
					pstmtStock.setString(14, String.valueOf(intDistID));  //Dist Id 
					pstmtStock.setString(15, fromDate);  //From Date
					pstmtStock.setString(16, toDate);  //To Date
					 
				}
				else if(productId.equals("0") && !circleId.equals("0"))// All product // circle
				{
					pstmtStock.setString(1, String.valueOf(intDistID));  //Dist Id 
					pstmtStock.setString(2, fromDate);  //From Date
					pstmtStock.setString(3, toDate);  //To Date
					pstmtStock.setString(4, circleId);  //Circle Id 
					
					System.out.println("DIST  ::  "+intDistID);
					System.out.println("FROM DATE  ::  "+fromDate);
					System.out.println("TO DATE  ::  "+toDate);
					System.out.println("CIRCLE  ::  "+circleId);
					
					
					pstmtStock.setString(5, String.valueOf(intDistID));  //Dist Id 
					pstmtStock.setString(6, fromDate);  //From Date
					pstmtStock.setString(7, toDate);  //To Date
					pstmtStock.setString(8, circleId);  //Circle Id 
					
					pstmtStock.setString(9, String.valueOf(intDistID));  //Dist Id
					pstmtStock.setString(10, circleId);  //Circle Id 
					
					pstmtStock.setString(11, String.valueOf(intDistID));  //Dist Id 
					pstmtStock.setString(12, fromDate);  //From Date
					pstmtStock.setString(13, toDate);  //To Date
					pstmtStock.setString(14, circleId);  //Circle Id 
					
					pstmtStock.setString(15, String.valueOf(intDistID));  //Dist Id 
					pstmtStock.setString(16, fromDate);  //From Date
					pstmtStock.setString(17, toDate);  //To Date
					pstmtStock.setString(18, circleId);  //Circle Id 
					
					//Added by Nazim Hussain for INVENTORY CHANGE
					pstmtStock.setString(19, String.valueOf(intDistID));  //Dist Id 
					pstmtStock.setString(20, fromDate);  //From Date
					pstmtStock.setString(21, toDate);  //To Date
					pstmtStock.setString(22, circleId);  //Circle Id 
				}
				else
				{																	// One circle product // One dist
					pstmtStock.setString(1, String.valueOf(intDistID));  //Dist Id 
					pstmtStock.setString(2, fromDate);  //From Date
					pstmtStock.setString(3, toDate);  //To Date
					pstmtStock.setString(4, productId);  //Product Id 
					pstmtStock.setString(5, String.valueOf(intDistID));  //Dist Id 
					pstmtStock.setString(6, fromDate);  //From Date
					pstmtStock.setString(7, toDate);  //To Date
					pstmtStock.setString(8, productId);  //Product Id 
					pstmtStock.setString(9, String.valueOf(intDistID));  //Dist Id 
					pstmtStock.setString(10, productId);  //Product Id 
					pstmtStock.setString(11, String.valueOf(intDistID));  //Dist Id 
					pstmtStock.setString(12, fromDate);  //From Date
					pstmtStock.setString(13, toDate);  //To Date
					pstmtStock.setString(14, productId);  //Product Id 
					pstmtStock.setString(15, String.valueOf(intDistID));  //Dist Id 
					pstmtStock.setString(16, fromDate);  //From Date
					pstmtStock.setString(17, toDate);  //To Date
					pstmtStock.setString(18, productId);  //Product Id
					//Added by Nazim Hussain for INVENTORY CHANGE
					pstmtStock.setString(19, String.valueOf(intDistID));  //Dist Id 
					pstmtStock.setString(20, fromDate);  //From Date
					pstmtStock.setString(21, toDate);  //To Date
					pstmtStock.setString(22, productId);  //Product Id
					
					System.out.println("DIST  ::  "+intDistID);
					System.out.println("FROM DATE  ::  "+fromDate);
					System.out.println("TO DATE  ::  "+toDate);
					System.out.println("PROD  ::  "+productId);
				}
				
				
				
				
				
				rsetStock = pstmtStock.executeQuery();
				
				Map<String, List<Integer>> mapDistData = new LinkedHashMap<String, List<Integer>>();
				
				List<Integer> arrProdData = null;
				
				String strDataType, strProductID = null;
				int intDamageCount = 0;
								
				while(rsetStock.next())
				{
					arrProdData = new ArrayList<Integer>(5);
					
					for(int i=0; i<5; i++)
						arrProdData.add(i, 0);
					
					strDataType = rsetStock.getString("DATA_TYPE");
					strProductID = rsetStock.getString("PRODUCT_ID")+","+rsetStock.getString("PRODUCT");
					
					if(strDataType.equals("STK_INV"))
					{
						arrProdData.set(0, rsetStock.getInt("STOCK_COUNT"));
						mapDistData.put(strProductID, arrProdData);	
					}
					else if(strDataType.equals("STK_INV_ASS"))
					{
						if(mapDistData.containsKey(strProductID))
						{
							arrProdData = mapDistData.get(strProductID);
							arrProdData.set(1, rsetStock.getInt("STOCK_COUNT"));
							mapDistData.remove(strProductID);
							mapDistData.put(strProductID, arrProdData);
						}
						else
						{
							arrProdData.set(1, rsetStock.getInt("STOCK_COUNT"));
							mapDistData.put(strProductID, arrProdData);
						}
					}
					else if(strDataType.equals("STK_INV_OPN"))
					{
						if(mapDistData.containsKey(strProductID))
						{
							arrProdData = mapDistData.get(strProductID);
							arrProdData.set(2, rsetStock.getInt("STOCK_COUNT"));;
							mapDistData.remove(strProductID);
							mapDistData.put(strProductID, arrProdData);
						}
						else
						{
							arrProdData.set(2, rsetStock.getInt("STOCK_COUNT"));
							mapDistData.put(strProductID, arrProdData);
						}
					}
					else if(strDataType.equals("STK_INV_REV"))
					{
						if(mapDistData.containsKey(strProductID))
						{
							arrProdData = mapDistData.get(strProductID);
							arrProdData.set(3, rsetStock.getInt("STOCK_COUNT"));;
							mapDistData.remove(strProductID);
							mapDistData.put(strProductID, arrProdData);
						}
						else
						{
							arrProdData.set(3, rsetStock.getInt("STOCK_COUNT"));
							mapDistData.put(strProductID, arrProdData);
						}
					}
					// Commented as INV_Change is not considered as damage stock available with Dist
					/*else if(strDataType.equals("INV_CHANGE"))
					{
						if(mapDistData.containsKey(strProductID))
						{
							arrProdData = mapDistData.get(strProductID);
							intDamageCount = arrProdData.get(3);
							intDamageCount = intDamageCount + rsetStock.getInt("STOCK_COUNT");
							arrProdData.set(3, intDamageCount);
							mapDistData.remove(strProductID);
							mapDistData.put(strProductID, arrProdData);
						}
						else
						{
							arrProdData.set(3, rsetStock.getInt("STOCK_COUNT"));
							mapDistData.put(strProductID, arrProdData);
						}
					}*/
					else if(strDataType.equals("STK_INV_REV_S2W"))
					{
						if(mapDistData.containsKey(strProductID))
						{
							arrProdData = mapDistData.get(strProductID);
							arrProdData.set(4, rsetStock.getInt("STOCK_COUNT"));;
							mapDistData.remove(strProductID);
							mapDistData.put(strProductID, arrProdData);
						}
						else
						{
							arrProdData.set(4, rsetStock.getInt("STOCK_COUNT"));
							mapDistData.put(strProductID, arrProdData);
						}
					}
				}
				
				listReturn = prepareStockSummReport(listReturn, strDistName, strParentName, mapDistData);
			}
				
		}
		catch (Exception e) 
		{
			e.printStackTrace();
			throw new DAOException(e.getMessage());
		}
		finally
		{
			DBConnectionManager.releaseResources(pstmtDist ,rsetDist );
			DBConnectionManager.releaseResources(pstmtStock ,rsetStock );
		}
		
		return listReturn;
	}

	private List<StockSummaryReportDto> prepareStockSummReport(List<StockSummaryReportDto> listReturn, String strDistName, String strParentName, Map<String, List<Integer>> mapDistData) 
	{
		StockSummaryReportDto dtoStockSumm = null;
		String strProduct, strProductName = null;
		List<Integer> listStocktemp;
		int intSerStock, intOpenStock, intActiveStock, intDamagedStock;
		
		for (Map.Entry<String, List<Integer>> mapEntry : mapDistData.entrySet()) 
		{
			dtoStockSumm = new StockSummaryReportDto();
			strProduct = mapEntry.getKey();
			strProductName = strProduct.split(",")[1];
			
			listStocktemp = mapEntry.getValue();
			
			dtoStockSumm.setDistName(strDistName);
			
			dtoStockSumm.setParentAccount(strParentName);
			
			dtoStockSumm.setProductName(strProductName);
			
			intSerStock = listStocktemp.get(0);
			intActiveStock = listStocktemp.get(1);
			intOpenStock = listStocktemp.get(2);
			intDamagedStock = listStocktemp.get(3);
			
			dtoStockSumm.setNonSerailizedAsonDate(intOpenStock);
						
			dtoStockSumm.setSerialized(intSerStock+intActiveStock);

			dtoStockSumm.setTotalReceived(intOpenStock+intSerStock+intActiveStock);
			
			dtoStockSumm.setTotalSerializedActivation(intActiveStock);
			
			dtoStockSumm.setDamagedAvailableDist(intDamagedStock);
			
			dtoStockSumm.setDamagedS2W(listStocktemp.get(4));
			
			dtoStockSumm.setTotalInhandStock(intOpenStock+intSerStock+intDamagedStock);
			
			listReturn.add(dtoStockSumm);
//			dtoStockSumm.set
		} 

		return listReturn;
	}
}
