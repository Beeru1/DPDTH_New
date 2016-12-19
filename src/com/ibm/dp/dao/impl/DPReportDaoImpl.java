package com.ibm.dp.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

import com.ibm.dp.beans.InventoryStatusBean;
import com.ibm.dp.common.DBQueries;
import com.ibm.dp.dao.DPReportDao;
import com.ibm.dp.dto.DPReportDTO;
import com.ibm.dp.dto.ExceptionReportDTO;
import com.ibm.dp.dto.StockDetailReport;
import com.ibm.utilities.Utility;
import com.ibm.virtualization.recharge.dao.rdbms.BaseDaoRdbms;
import com.ibm.virtualization.recharge.db.DBConnectionManager;
import com.ibm.virtualization.recharge.exception.DAOException;

public class DPReportDaoImpl extends BaseDaoRdbms implements DPReportDao{

	private Logger logger = Logger.getLogger(DPReportDaoImpl.class.getName());
	
	final int dumpReport =1;
	final int tertiaryReport =2;
	final int stockReport =3;
	final int purchaseReport =4;
	final int ageingReport =5;
	final int noOfRetailersReport =6;
	final int salesSummaryReport = 7;
	
	public DPReportDaoImpl(Connection con) {
		super(con);
	}
	
	public ArrayList getReportNames() throws DAOException{
		Connection con = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		ArrayList reportNamesList = new ArrayList();
		try {
			//con = DBConnectionManager.getDBConnection(); // db2
			con = DBConnectionManager.getDBConnection(); // db2
			ps = con.prepareStatement(DBQueries.GET_REPORT_NAMES);
			rs = ps.executeQuery();
			DPReportDTO reportDto = null;
			while(rs.next()){
				reportDto = new DPReportDTO();
				reportDto.setReportName(rs.getString("REPORT_NAME"));
				reportDto.setReportId(rs.getString("REPORT_ID"));
				reportDto.setNoColumns(rs.getInt("NOS_COLUMN"));
				reportNamesList.add(reportDto);
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
		
			return reportNamesList;
	}

	public ArrayList getReportData(String reportId,String lapuMobileNo) throws DAOException{
		Connection con = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		ArrayList reportDataList = new ArrayList();
		DPReportDTO reportDto = null;
		int param=0;
		try {	
				
				con = DBConnectionManager.getDBConnectionSnD(); // db2
				if(reportId.equalsIgnoreCase(dumpReport+"")){
					ps = con.prepareStatement(DBQueries.GET_REPORT_DATA_DUMP);
				}
				else if(reportId.equalsIgnoreCase(tertiaryReport+"")){
					ps = con.prepareStatement(DBQueries.GET_REPORT_DATA_TERTIARY);
					ps.setString(++param, lapuMobileNo);
				}
				else if(reportId.equalsIgnoreCase(purchaseReport+"")){
					ps = con.prepareStatement(DBQueries.GET_REPORT_DATA_PURCHASE);
					ps.setString(++param, lapuMobileNo);
				}
				else if(reportId.equalsIgnoreCase(ageingReport+"")){
					ps = con.prepareStatement(DBQueries.GET_REPORT_DATA_AGEING);
					ps.setString(++param, lapuMobileNo);
				}
				else if(reportId.equalsIgnoreCase(noOfRetailersReport+"")){
					ps = con.prepareStatement(DBQueries.GET_REPORT_DATA_NO_OF_RETAILERS);
					ps.setString(++param, lapuMobileNo);
				}
				else if(reportId.equalsIgnoreCase(stockReport+"")){
					ps = con.prepareStatement(DBQueries.GET_REPORT_DATA_STOCK);
					ps.setString(++param, lapuMobileNo);
				}
				else if(reportId.equalsIgnoreCase(salesSummaryReport+"")){
					ps = con.prepareStatement(DBQueries.GET_REPORT_DATA_SALES_SUMMARY);
				}
				ps.setString(++param, lapuMobileNo);
				rs = ps.executeQuery();
				ps.clearParameters();
/*				while(rs.next()){
					reportDto = new DPReportDTO();
					reportDto.setHub(rs.getString("HUB"));
					reportDto.setCircle(rs.getString("CIRCLE"));
					reportDto.setZone(rs.getString("ZONE"));
					reportDto.setDistributorName(rs.getString("DISTRIBUTOR_NAME"));
					reportDto.setDistributormsisdn(rs.getString("DISTRIBUTOR_MSISDN"));
					reportDto.setRetName(rs.getString("RETAILER_NAME"));
					reportDto.setRetmsisdn(rs.getString("RETAILER_MSISDN"));
					reportDto.setProdType(rs.getString("PRODUCT_TYPE"));
					reportDto.setProdCat(rs.getString("PRODUCT_CATEGORY"));
					//reportDto.setField10(rs.getString("DATE"));
					reportDto.setOpeningBal(rs.getString("OPENING"));
					reportDto.setReceived(rs.getString("RECEVIED"));
					reportDto.setReturns(rs.getString("RETURN"));
					reportDto.setSales(rs.getString("SALES"));
					reportDto.setClosingBal(rs.getString("CLOSING"));
					//reportDto.setField16(rs.getString("FIELD16"));
					reportDto.setField17(rs.getString("FIELD17"));
					reportDto.setField18(rs.getString("FIELD18"));	
					reportDataList.add(reportDto);
				}
*/
				switch(Integer.parseInt(reportId)){
				    case dumpReport:
						while(rs.next()){
							reportDto = new DPReportDTO();
							reportDto.setHub(rs.getString("HUB"));
							reportDto.setCircle(rs.getString("CIRCLE"));
							reportDto.setZone(rs.getString("ZONE"));
							reportDto.setDistributorName(rs.getString("DISTRIBUTOR_NAME"));
							reportDto.setDistributormsisdn(rs.getString("DISTRIBUTOR_MSISDN"));
							reportDto.setRetName(rs.getString("RETAILER_NAME"));
							reportDto.setRetmsisdn(rs.getString("RETAILER_MSISDN"));
							reportDto.setProdType(rs.getString("PRODUCT_TYPE"));
							reportDto.setProdCat(rs.getString("PRODUCT_CATEGORY"));
							reportDto.setOpeningBal(rs.getString("OPENING"));
							reportDto.setReceived(rs.getString("RECEVIED"));
							reportDto.setReturns(rs.getString("RETURN"));
							reportDto.setSales(rs.getString("SALES"));
							reportDto.setClosingBal(rs.getString("CLOSING"));
							reportDataList.add(reportDto);
						}
						break;
				    case tertiaryReport:
				    	while(rs.next()){
				    		reportDto = new DPReportDTO();
							reportDto.setHub(rs.getString("HUB"));
							reportDto.setCircle(rs.getString("CIRCLE"));
							reportDto.setZone(rs.getString("ZONE"));
							reportDto.setDistributorName(rs.getString("DISTRIBUTOR_NAME"));
							//reportDto.setDistributormsisdn(rs.getString("DISTRIBUTOR_MSISDN"));
							reportDto.setRetName(rs.getString("RETAILER_NAME"));
							reportDto.setRetmsisdn(rs.getString("RETAILER_MSISDN"));
							reportDto.setProdType(rs.getString("PRODUCT_TYPE"));
							reportDto.setProdCat(rs.getString("PRODUCT_CATEGORY"));
							reportDto.setFseName(rs.getString("FSE_NAME"));
							//reportDto.setDate(rs.getString("DATE"));
							reportDto.setMtdSales(rs.getLong("MTD_SALES"));
							reportDto.setLmtdSales(rs.getLong("LMTD_SALES"));
							reportDataList.add(reportDto);
						}
				    	break;
				    case purchaseReport:
						while(rs.next()){
							reportDto = new DPReportDTO();
							reportDto.setHub(rs.getString("HUB"));
							reportDto.setCircle(rs.getString("CIRCLE"));
							reportDto.setZone(rs.getString("ZONE"));
							reportDto.setDistributorName(rs.getString("DISTRIBUTOR_NAME"));
							reportDto.setDistributormsisdn(rs.getString("DISTRIBUTOR_MSISDN"));
							reportDto.setRetName(rs.getString("RETAILER_NAME"));
							reportDto.setRetmsisdn(rs.getString("RETAILER_MSISDN"));
							reportDto.setProdType(rs.getString("PRODUCT_TYPE"));
							reportDto.setProdCat(rs.getString("PRODUCT_CATEGORY"));
							reportDto.setFseName(rs.getString("FSE_NAME"));
//							reportDto.setDate(rs.getString("DATE"));
							reportDto.setMtdPrimaryPurchase(rs.getLong("MTD_PRIMARY_PURCHASE"));
							reportDto.setLmtdPrimaryPurchase(rs.getLong("LMTD_PRIMARY_PURCHASE"));	
							reportDataList.add(reportDto);
						}
						break;
				    case ageingReport:
						while(rs.next()){
							reportDto = new DPReportDTO();
							reportDto.setHub(rs.getString("HUB"));
							reportDto.setCircle(rs.getString("CIRCLE"));
							reportDto.setZone(rs.getString("ZONE"));
							reportDto.setDistributorName(rs.getString("DISTRIBUTOR_NAME"));
							//reportDto.setDistributormsisdn(rs.getString("DISTRIBUTOR_MSISDN"));
							reportDto.setRetName(rs.getString("RETAILER_NAME"));
							reportDto.setRetmsisdn(rs.getString("RETAILER_MSISDN"));
							reportDto.setProdType(rs.getString("PRODUCT_TYPE"));
							reportDto.setProdCat(rs.getString("PRODUCT_CATEGORY"));
							reportDto.setFseName(rs.getString("FSE_NAME"));
							//reportDto.setDate(rs.getString("DATE"));
							reportDto.setNoLtEq90(rs.getInt("NO_LT_EQ_90"));
							reportDto.setNoGt90(rs.getInt("NO_GT_90"));	
							reportDataList.add(reportDto);
						}
						break;
				    case noOfRetailersReport:
						while(rs.next()){
							reportDto = new DPReportDTO();
							reportDto.setHub(rs.getString("HUB"));
							reportDto.setCircle(rs.getString("CIRCLE"));
							reportDto.setZone(rs.getString("ZONE"));
							reportDto.setDistributorName(rs.getString("DISTRIBUTOR_NAME"));
							reportDto.setBucketName(rs.getString("BUCKET_NAME"));
							//reportDto.setDistributormsisdn(rs.getString("DISTRIBUTOR_MSISDN"));
							reportDto.setProdType(rs.getString("PRODUCT_TYPE"));
							reportDto.setProdCat(rs.getString("PRODUCT_CATEGORY"));
							//reportDto.setDate(rs.getString("DATE"));
							reportDto.setNoOfRetailers(rs.getInt("NO_OF_RETAILER"));	
							reportDataList.add(reportDto);
						}
						break;
				    case stockReport:
						while(rs.next()){
							reportDto = new DPReportDTO();
							reportDto.setHub(rs.getString("HUB"));
							reportDto.setCircle(rs.getString("CIRCLE"));
							reportDto.setZone(rs.getString("ZONE"));
							reportDto.setDistributorName(rs.getString("DISTRIBUTOR_NAME"));
							reportDto.setDistributormsisdn(rs.getString("DISTRIBUTOR_MSISDN"));
							reportDto.setRetName(rs.getString("RETAILER_NAME"));
							reportDto.setRetmsisdn(rs.getString("RETAILER_MSISDN"));
							reportDto.setFseName(rs.getString("FSE_NAME"));
							reportDto.setProdType(rs.getString("PRODUCT_TYPE"));
							reportDto.setProdCat(rs.getString("PRODUCT_CATEGORY"));
//							reportDto.setDate(rs.getString("DATE"));
							reportDto.setClosingStock(rs.getLong("CLOSING_STOCK"));
							reportDto.setStockNoOfDays(rs.getInt("STOCK_NO_OF_DAYS"));	
							reportDataList.add(reportDto);
						}
						break;
				    case salesSummaryReport:
						while(rs.next()){
							reportDto = new DPReportDTO();
							reportDto.setRetName(rs.getString("RETAILER_NAME"));
							reportDto.setCategory(rs.getString("CATEGORY"));
							reportDto.setFseName(rs.getString("FSE_NAME"));
							reportDto.setBeatName(rs.getString("BEAT_NAME"));
							reportDto.setZsmName(rs.getString("ZSM"));
							reportDto.setTmName(rs.getString("TM"));
							reportDto.setGrossActivations(rs.getInt("GROSS_ACTIVATION_MTD"));
							reportDto.setEasyRecharge(rs.getLong("LAPU_MTD"));	
							reportDataList.add(reportDto);
						}
						break;		
				}    	
/*				if(reportId.equalsIgnoreCase("3")){
					ps = con.prepareStatement(DBQueries.SELECT_RETAILER_STOCK);
					ps.setString(1, "SAFdistributor");
					rs = ps.executeQuery();
					
					while(rs.next()){
						reportDto = new DPReportDTO();
						reportDto.setHub(rs.getString("HUB"));
						reportDto.setCircle(rs.getString("CIRCLE"));
						reportDto.setZone(rs.getString("ZONE"));
						reportDto.setDistributorName(rs.getString("DISTRIBUTOR_NAME"));
						reportDto.setDistributormsisdn(rs.getString("DISTRIBUTOR_MSISDN"));
						reportDto.setRetName(rs.getString("RETAILER_NAME"));
						reportDto.setRetmsisdn(rs.getString("RETAILER_MSISDN"));
						reportDto.setProdType(rs.getString("PRODUCT_TYPE"));
						reportDto.setProdCat(rs.getString("PRODUCT_CATEGORY"));
						reportDto.setClosingBal(rs.getString("CLOSING_STOCK"));
						reportDto.setFseName(rs.getString("FSE_NAME"));
						reportDto.setStockNoOfDays(Integer.parseInt(rs.getString("STOCK_NO_OF_DAYS")));
						reportDto.setDate(rs.getString("DATE"));
						reportDataList.add(reportDto);
					}
				}*/
//				con = DBConnectionManager.getDBConnection(); // db2
//				ps = con.prepareStatement(DBQueries.GET_REPORT_NAMES+" where REPORT_ID=? ");
//				ps.setString(1, reportId);
//				rs = ps.executeQuery();
//				if(rs.next()){

					//reportDto = new DPReportDTO();
					//reportDto.setNoColumns(rs.getInt("NOS_COLUMN"));
				//	reportDataList.add(rs.getInt("NOS_COLUMN"));
				//}
//				}		
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
			return reportDataList;
	}
/*		public ArrayList getEmployeeData(){
//			String strDriver = "com.ibm.db2.jcc.DB2Driver";
		//	String url = "jdbc:db2:SAMPLE";
			//String userid = "Administrator";
		//	String passwd = "subha123";
//			Connection connection = null;
//			Statement statement = null;
//			ResultSet rs = null;
//			String sql = "SELECT EMPNO,FIRSTNME,LASTNAME,JOB FROM ADMINISTRATOR.EMPLOYEE ";
			ArrayList arrayList = new ArrayList();
			try{
				//Class.forName(strDriver);
				//connection = DriverManager.getConnection(url, userid, passwd);
				//statement = connection.createStatement();
			    //  rs = statement.executeQuery(sql);

			     // while (rs.next()) {
				DPReportDTO reportDto = new DPReportDTO();
				reportDto.setField1("1");
				reportDto.setField2("SANJAY");
				reportDto.setField3("VERMA");
				reportDto.setField4("MANAGER");
			        reportDto.setEmpNo(rs.getString("EMPNO"));
			        reportDto.setFirstName(rs.getString("FIRSTNME"));
			        reportDto.setLastName(rs.getString("LASTNAME"));
			        reportDto.setJob(rs.getString("JOB"));
			        
			        arrayList.add(reportDto);
			        reportDto = new DPReportDTO();
			        reportDto.setField1("2");
					reportDto.setField2("Anju");
					reportDto.setField3("Jhamb");
					reportDto.setField4("Developer");
			        arrayList.add(reportDto);

			        reportDto = new DPReportDTO();
			        reportDto.setField1("3");
					reportDto.setField2("Siddhartha");
					reportDto.setField3("Sharma");
					reportDto.setField4("Developer");
			        arrayList.add(reportDto);

			        reportDto = new DPReportDTO();
			        reportDto.setField1("4");
					reportDto.setField2("Aditya");
					reportDto.setField3("Sarin");
					reportDto.setField4("Developer");
			        arrayList.add(reportDto);
			      //}
			      //rs.close ();
			      //statement.close ();
		
				
			}catch(Exception e){
				e.printStackTrace();
			}
			
			return arrayList;
		}	*/
	
	
	
//	 Add by harbans
	public List getLevelDetails() throws DAOException
	{
		Connection con = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		List<InventoryStatusBean> productStatusList = new ArrayList<InventoryStatusBean>();
		try 
		{
			con = DBConnectionManager.getDBConnection(); // db2
			ps = con.prepareStatement(DBQueries.GET_LEVEL_DETAILS);
			rs = ps.executeQuery();
		
			InventoryStatusBean statusReportBean = null;
			while(rs.next())
			{
				statusReportBean = new InventoryStatusBean();
				statusReportBean.setLevelId(String.valueOf(rs.getInt("LEVEL_ID")));
				statusReportBean.setLevelName(rs.getString("LEVEL_NAME"));
				productStatusList.add(statusReportBean);
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
		
		return productStatusList;
	}
	
//	 Add by harbans
	public List getAccountDetails(int levelId , int distId) throws DAOException
	{
		Connection con = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		List<InventoryStatusBean> productStatusList = new ArrayList<InventoryStatusBean>();
		try 
		{
			con = DBConnectionManager.getDBConnection(); // db2
			
			// Fetch level base
			if(levelId == 6)// dist
			    ps = con.prepareStatement(DBQueries.GET_ACCOUNT_DETAILS_DIST);
			else if(levelId == 7)// FSE
				ps = con.prepareStatement(DBQueries.GET_ACCOUNT_DETAILS_FSE);
			else
				ps = con.prepareStatement(DBQueries.GET_ACCOUNT_DETAILS_RETAILRES);
			
			ps.setInt(1, distId);
			rs = ps.executeQuery();
			
			InventoryStatusBean statusReportBean = null;
			while(rs.next())
			{
				statusReportBean = new InventoryStatusBean();
				statusReportBean.setAccountId(String.valueOf(rs.getInt("account_id")));
				statusReportBean.setAccountName(rs.getString("account_name"));
				productStatusList.add(statusReportBean);
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
		
		return productStatusList;
	}
	
	
	// Add by harbans
	public List getProductsDetails(int levelId, int accountId , int distId) throws DAOException
	{
		Connection con = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		List<InventoryStatusBean> productStatusList = new ArrayList<InventoryStatusBean>();
		try 
		{
			con = DBConnectionManager.getDBConnection();
			
			// Fetch level base
			if(levelId == 6 && accountId == 9999)
			{
				ps = con.prepareStatement(DBQueries.GET_PRODUCT_DETAILS_ALL_DIST);// Distributor
				ps.setInt(1, distId);
				ps.setInt(2, distId);
				ps.setInt(3, distId);
				ps.setInt(4, distId);
				ps.setInt(5, distId);
				logger.info("Query GET_PRODUCT_DETAILS_ALL_DIST :" + DBQueries.GET_PRODUCT_DETAILS_ALL_DIST);
			}
			else if(levelId == 6 && accountId != 9999)
			{
				ps = con.prepareStatement(DBQueries.GET_PRODUCT_DETAILS_DIST);// Distributor
				ps.setInt(1, distId);
				logger.info("Query for GET_PRODUCT_DETAILS_DIST: " + DBQueries.GET_PRODUCT_DETAILS_DIST);
			}
			else if(levelId == 7 && accountId == 9999)
			{
				ps = con.prepareStatement(DBQueries.GET_PRODUCT_DETAILS_ALL_FSE);// FSE`
				ps.setInt(1, distId);
				ps.setInt(2, distId);
				ps.setInt(3, distId);
				logger.info("Query for GET_PRODUCT_DETAILS_ALL_FSE:" + DBQueries.GET_PRODUCT_DETAILS_ALL_FSE);
			}
			else if(levelId == 7 && accountId != 9999)
			{
				ps = con.prepareStatement(DBQueries.GET_PRODUCT_DETAILS_FSE);// FSE`
				ps.setInt(1, accountId);
				logger.info("query for GET_PRODUCT_DETAILS_FSE :" + DBQueries.GET_PRODUCT_DETAILS_FSE);
			}
			else if(levelId == 8 && accountId == 9999)
			{
				ps = con.prepareStatement(DBQueries.GET_PRODUCT_DETAILS_ALL_RETAILRES);// Retailers
				ps.setInt(1, distId);
				ps.setInt(2, distId);
			
				logger.info("Query for GET_PRODUCT_DETAILS_ALL_RETAILRES:" + DBQueries.GET_PRODUCT_DETAILS_ALL_RETAILRES);
			}
			else 
			{
				ps = con.prepareStatement(DBQueries.GET_PRODUCT_DETAILS_RETAILRES);// Retailers
				ps.setInt(1, accountId);
				ps.setInt(2, accountId);
				
				logger.info("Query for GET_PRODUCT_DETAILS_RETAILRES : " + DBQueries.GET_PRODUCT_DETAILS_RETAILRES);
			}
						
			rs = ps.executeQuery();
			
			InventoryStatusBean statusReportBean = null;
			while(rs.next())
			{
				statusReportBean = new InventoryStatusBean();
				statusReportBean.setAccountName(rs.getString("ACCOUNT_NAME"));
				statusReportBean.setProductName(rs.getString("PRODUCT_NAME"));
				statusReportBean.setSerialNo(rs.getString("SERIAL_NO"));
				statusReportBean.setStatus(rs.getString("STATUS_DESCRIPTION"));
				//Date distPurchaseDate = (Date) rs.getDate("STOCK_DATE");
				//statusReportBean.setDistributorPurchaseDate(Utility.getDateAsString(distPurchaseDate, "dd-MM-yyyy"));
				statusReportBean.setDistributorPurchaseDate( (String) rs.getString("STOCK_DATE"));
				productStatusList.add(statusReportBean);
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
		
		return productStatusList;
	}
	
	// Add by harbans for  Exception Consumed report.
	public List<ExceptionReportDTO> getExceptionConsumedReportData(String fromDate, String toDate) throws DAOException
	{
		Connection con = null;
		PreparedStatement ps = null;
		ResultSet rsDTH = null;
		List<ExceptionReportDTO> exceptionReportList = new ArrayList<ExceptionReportDTO>();
		try 
		{
			con = DBConnectionManager.getDBConnection(); // db2
			ps = con.prepareStatement(DBQueries.GET_EXCEPTION_CONSUMED_REPORT);
			ps.setDate(1, Utility.getSqlDateFromString(fromDate.trim(), "MM/dd/yyyy"));
			ps.setDate(2, Utility.getSqlDateFromString(toDate.trim(), "MM/dd/yyyy"));
			rsDTH = ps.executeQuery();
		
			while(rsDTH.next())
    		{
    			ExceptionReportDTO exceptionReportDTO = new ExceptionReportDTO();
    			exceptionReportDTO.setRecId(rsDTH.getString("REC_ID"));
    			exceptionReportDTO.setSerialNumber(rsDTH.getString("SERIAL_NUMBER"));
    			exceptionReportDTO.setStatus(rsDTH.getString("STATUS"));
    			exceptionReportDTO.setAssignDate(rsDTH.getString("ASSIGN_DATE"));
    			exceptionReportDTO.setDealerCode(rsDTH.getString("DEALER_CODE"));
    			exceptionReportDTO.setDistributionCode(rsDTH.getString("DISTRIBUTOR_CODE"));
    			exceptionReportDTO.setAceCode(rsDTH.getString("ACE_CODE"));
    			exceptionReportList.add(exceptionReportDTO);
    		}

    		logger.info("Recoords size for exception consumption report :"+ exceptionReportList.size());
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
		
		return exceptionReportList;
	}
	
	
	
	
//	 Get distributor detial of given account id. 
	public List<StockDetailReport> getAllDistributor(long accountId,String accountLevel) throws DAOException
	{

		Connection con = null;
		PreparedStatement ps = null;
		ResultSet rsDTH = null;
		List tempDistList=new ArrayList();
		
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
				StockDetailReport dpreportdto= new StockDetailReport();
				dpreportdto.setDistID(String.valueOf(rsDTH.getInt("account_id")));
				dpreportdto.setDistName(rsDTH.getString("account_name"));
				tempDistList.add(dpreportdto);
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

	
//	 Get all distributor stock report details.
	public List<StockDetailReport> getStockDetailsReport(int distId) throws DAOException
	{
		Connection con = null;
		PreparedStatement ps1 = null;
		ResultSet rs1 = null;
		
		PreparedStatement ps2 = null;
		ResultSet rs2 = null;
		
		PreparedStatement ps3 = null;
		ResultSet rs3 = null;
		
		PreparedStatement ps4 = null;
		ResultSet rs4 = null;
		
		PreparedStatement ps5 = null;
		ResultSet rs5 = null;
		
		PreparedStatement ps6 = null;
		ResultSet rs6 = null;
		
		PreparedStatement ps7 = null;
		ResultSet rs7 = null;
		
		PreparedStatement ps8 = null;
		ResultSet rs8 = null;
		
		PreparedStatement ps9 = null;
		ResultSet rs9 = null;
		
		PreparedStatement ps10 = null;
		ResultSet rs10 = null;
		
		PreparedStatement ps11 = null;
		ResultSet rs11 = null;
		
		PreparedStatement ps12 = null;
		ResultSet rs12 = null;
		
		PreparedStatement ps13 = null;
		ResultSet rs13 = null;
		
		PreparedStatement ps14 = null;
		ResultSet rs14 = null;
		
		PreparedStatement ps15 = null;
		ResultSet rs15 = null;
		
		PreparedStatement ps16 = null;
		ResultSet rs16 = null;
				
		String openingStock = "";
		String receiveStock = "";
		String allocationStock = "";
		String saleByRetailersQty ="";
		String closingStock = "";
		
		String distName = "";
		String distRole = "";
		String openingStockDist = "";
		String receiveStockDist = "";
		String allocationStockDist = "";
		String saleByRetailersQtyDist ="";
		int soldByRetailer = 0;
				
		
		List<StockDetailReport> stockDetailReportList = new ArrayList<StockDetailReport>();
				
		try 
		{
			con = DBConnectionManager.getDBConnection(); // db2
						
			// Get products of given dist 
			ps1 = con.prepareStatement(DBQueries.GET_ALL_DIST_PRODUCT_NAME);
			ps1.setInt(1, distId);
			ps1.setInt(2, distId);
			rs1 = ps1.executeQuery(); 				
			
			while(rs1.next())
			{
				soldByRetailer = 0;
				int prodId = rs1.getInt("PRODUCT_ID");
				String productName = rs1.getString("PRODUCT_NAME");
																	
				// Get accname & role of dist
				ps2 = con.prepareStatement(DBQueries.GET_ACC_NAME_AND_ROLE);
				ps2.setInt(1, distId);
				ps2.setInt(2, distId);
				ps2.setInt(3, distId);
				rs2 = ps2.executeQuery();
				
				while(rs2.next())
				{
					StockDetailReport stockDetailReport = new StockDetailReport();
					stockDetailReport.setProductName(productName);
					
				    int groupID = rs2.getInt("GROUP_ID");
				    int accountID = rs2.getInt("ACCOUNT_ID");
				    String accountName = rs2.getString("ACCOUNT_NAME");
				    String roleName = rs2.getString("GROUP_NAME");
				    
				    
				    stockDetailReport.setAccountName(accountName);
				    stockDetailReport.setRole(roleName);
				    							
					// For distributor
					if(groupID == 7)
					{
						// Get opening stock of dist
						ps3 = con.prepareStatement(DBQueries.GET_DIST_OPENING_STOCK);
						ps3.setInt(1, accountID);
						ps3.setInt(2, prodId);
						ps3.setInt(3, accountID);
						ps3.setInt(4, prodId);
						rs3 = ps3.executeQuery();
						if(rs3.next())
						{
							openingStock =  rs3.getString("OPENING_STOCK");
							openingStockDist = openingStock;
						}
						
						if(ps3!=null) {
							ps3.clearParameters();
							ps3=null;
						}
						if(rs3!=null) {
							rs3=null;
						}
													
						// Get received stock of dist
						ps4 = con.prepareStatement(DBQueries.GET_DIST_STOCK_RECEIVED);
						ps4.setInt(1, accountID);
						ps4.setInt(2, prodId);
						ps4.setInt(3, accountID);
						ps4.setInt(4, prodId);
						rs4 = ps4.executeQuery();
						if(rs4.next())
						{
						   receiveStock = rs4.getString("TOTAL_STOCK_RECEIVED");
						   receiveStockDist = receiveStock;
						}
						
						if(ps4!=null) {
							ps4.clearParameters();
							ps4=null;
						}
						if(rs4!=null) {
							rs4=null;
						}
						
						// Get allocation stock of dist
						ps5 = con.prepareStatement(DBQueries.GET_DIST_ALLOCATION_STOCK);
						ps5.setInt(1, accountID);
						ps5.setInt(2, prodId);
						ps5.setInt(3, accountID);
						ps5.setInt(4, prodId);
						rs5 = ps5.executeQuery();
						if(rs5.next())
						{
							allocationStock = rs5.getString("ALLOCATION");
						}
						
						if(ps5!=null) {
							ps5.clearParameters();
							ps5=null;
						}
						if(rs5!=null) {
							rs5=null;
						}
													
						stockDetailReport.setOpeningStockQty(openingStock);
						stockDetailReport.setStockReceivedQty(receiveStock);
						stockDetailReport.setAllocationQty(allocationStock);
						stockDetailReport.setSaleByRetailersQty("NA");
						
						closingStock = String.valueOf(Integer.parseInt(openingStock) + Integer.parseInt(receiveStock) - Integer.parseInt(allocationStock));
						stockDetailReport.setClosingStock(closingStock);
						
//						 For total line
					    distName = accountName;
					    distRole = roleName;
					    
						
					}else if(groupID == 8)// FOR FSE
					{
						// Get opening stock of dist
						ps6 = con.prepareStatement(DBQueries.GET_FSE_OPENING_STOCK);
						ps6.setInt(1, accountID);
						ps6.setInt(2, prodId);
						ps6.setInt(3, accountID);
						ps6.setInt(4, prodId);
						rs6 = ps6.executeQuery();
						if(rs6.next())
						{
							openingStock =  rs6.getString("OPENING_STOCK");
						}
						
						if(ps6!=null) {
							ps6.clearParameters();
							ps6=null;
						}
						if(rs6!=null) {
							rs6=null;
						}
						
						// Get received stock of dist
						ps7 = con.prepareStatement(DBQueries.GET_FSE_STOCK_RECEIVED);
						ps7.setInt(1, accountID);
						ps7.setInt(2, prodId);
						ps7.setInt(3, accountID);
						ps7.setInt(4, prodId);
						rs7 = ps7.executeQuery();
						if(rs7.next())
						{
						   receiveStock = rs7.getString("TOTAL_STOCK_RECEIVED");	
						}
						
						if(ps7!=null) {
							ps7.clearParameters();
							ps7=null;
						}
						if(rs7!=null) {
							rs7=null;
						}
						
						// Get allocation stock of dist
						ps8 = con.prepareStatement(DBQueries.GET_FSE_ALLOCATION_STOCK);
						ps8.setInt(1, accountID);
						ps8.setInt(2, prodId);
						ps8.setInt(3, accountID);
						ps8.setInt(4, prodId);
						rs8 = ps8.executeQuery();
						if(rs8.next())
						{
							allocationStock = rs8.getString("ALLOCATION");
						}
						
						if(ps8!=null) {
							ps8.clearParameters();
							ps8=null;
						}
						if(rs8!=null) {
							rs8=null;
						}
						
						stockDetailReport.setOpeningStockQty(openingStock);
						stockDetailReport.setStockReceivedQty(receiveStock);
						stockDetailReport.setAllocationQty(allocationStock);
						stockDetailReport.setSaleByRetailersQty("NA");
						
						closingStock = String.valueOf(Integer.parseInt(openingStock) + Integer.parseInt(receiveStock) - Integer.parseInt(allocationStock));
						stockDetailReport.setClosingStock(closingStock);
						
					}
					else if(groupID == 9)// FOR RETAILERS
					{
						// Get opening stock of dist
						ps9 = con.prepareStatement(DBQueries.GET_RET_OPENING_STOCK);
						ps9.setInt(1, accountID);
						ps9.setInt(2, prodId);
						ps9.setInt(3, accountID);
						ps9.setInt(4, prodId);
						rs9 = ps9.executeQuery();
						if(rs9.next())
						{
							openingStock =  rs9.getString("OPENING_STOCK");
						}
						
						if(ps9!=null) {
							ps9.clearParameters();
							ps9=null;
						}
						if(rs9!=null) {
							rs9=null;
						}
						
						// Get received stock of dist
						ps10 = con.prepareStatement(DBQueries.GET_RET_STOCK_RECEIVED);
						ps10.setInt(1, accountID);
						ps10.setInt(2, prodId);
						ps10.setInt(3, accountID);
						ps10.setInt(4, prodId);
						rs10 = ps10.executeQuery();
						if(rs10.next())
						{
						   receiveStock = rs10.getString("TOTAL_STOCK_RECEIVED");	
				    	}
						
						if(ps10!=null) {
							ps10.clearParameters();
							ps10=null;
						}
						if(rs10!=null) {
							rs10=null;
						}
													
						// Get allocation stock of dist
						ps11 = con.prepareStatement(DBQueries.GET_RET_ALLOCATION_STOCK);
						ps11.setInt(1, accountID);
						ps11.setInt(2, prodId);
/*	SR1697661	--	Modified by Nazim Hussain to bring Correct Closing stock of Reailer
						ps11.setInt(3, accountID);
						ps11.setInt(4, prodId);
						*/
						rs11 = ps11.executeQuery();
						if(rs11.next())
						{
							allocationStock = rs11.getString("ALLOCATION");
							soldByRetailer = soldByRetailer +  Integer.parseInt(allocationStock);
							//logger.info("The productsId : "+prodId+" sold by retailerId : "+accountID +" countr : "+ soldByRetailer);
						}
						
						if(ps11!=null) {
							ps11.clearParameters();
							ps11=null;
						}
						if(rs11!=null) {
							rs11=null;
						}
						
						stockDetailReport.setOpeningStockQty(openingStock);
						stockDetailReport.setStockReceivedQty(receiveStock);
						stockDetailReport.setAllocationQty("NA");
						stockDetailReport.setSaleByRetailersQty(allocationStock);
						
						closingStock = String.valueOf(Integer.parseInt(openingStock) + Integer.parseInt(receiveStock) - Integer.parseInt(allocationStock));
						stockDetailReport.setClosingStock(closingStock);
				    }
					
					stockDetailReportList.add(stockDetailReport);
				}// End account wise
				
				if(ps2!=null) {
					ps2.clearParameters();
					ps2=null;
				}
				
				if(rs2!=null) {
					rs2=null;
				}
				
//				 Insert values of total line records.
				StockDetailReport totalRecordsStock = new StockDetailReport();
				totalRecordsStock.setAccountName("TOTAL");
				totalRecordsStock.setRole("ALL");
				totalRecordsStock.setProductName(productName);
				totalRecordsStock.setOpeningStockQty(openingStockDist);
				totalRecordsStock.setStockReceivedQty(receiveStockDist);
				totalRecordsStock.setAllocationQty("NA");
				
				saleByRetailersQtyDist = String.valueOf(soldByRetailer);
				totalRecordsStock.setSaleByRetailersQty(saleByRetailersQtyDist);
				
				closingStock = String.valueOf(Integer.parseInt(openingStockDist) + Integer.parseInt(receiveStockDist) - soldByRetailer);				
				totalRecordsStock.setClosingStock(closingStock);
				
				stockDetailReportList.add(totalRecordsStock);
				
				// Insert blank line
				StockDetailReport tempRecordsStock = new StockDetailReport();
				stockDetailReportList.add(tempRecordsStock);			
				
//				 Insert blank line
				StockDetailReport tempRecordsStock1 = new StockDetailReport();
				stockDetailReportList.add(tempRecordsStock1);
				
			}// END PRODUCT WHILE
			
			if(ps1!=null) {
				ps1.clearParameters();
				ps1=null;
			}
			
			if(rs1!=null) {
				rs1=null;
			}
			
			
//############################### Show product detail reports for Open Stock Products(NON DPSYSTEM) ############################
			
//			 Get products of given dist 
			ps12 = con.prepareStatement(DBQueries.GET_ALL_OPEN_PRODUCT_NAME);
			ps12.setInt(1, distId);
			rs12 = ps12.executeQuery(); 				
			
			while(rs12.next())
			{
				soldByRetailer = 0;
				int prodId = rs12.getInt("PRODUCT_ID");
				String productName = rs12.getString("PRODUCT_NAME");
														
				// Get accname & role of dist
				ps13 = con.prepareStatement(DBQueries.GET_ACC_NAME_AND_ROLE_DIST);
				ps13.setInt(1, distId);
				rs13 = ps13.executeQuery();
				
				if(rs13.next())
				{
					StockDetailReport stockDetailReport = new StockDetailReport();
					stockDetailReport.setProductName(productName);
					
				    int groupID = rs13.getInt("GROUP_ID");
				    int accountID = rs13.getInt("ACCOUNT_ID");
				    String accountName = rs13.getString("ACCOUNT_NAME");
				    String roleName = rs13.getString("GROUP_NAME");
				    				    
				    stockDetailReport.setAccountName(accountName);
				    stockDetailReport.setRole(roleName);
				    	
					// For distributor
					if(groupID == 7)
					{
						// Get opening stock of dist OPEN_STOCK_TABLE [DP non serialized]. 
						ps14 = con.prepareStatement(DBQueries.GET_DIST_ALL_OPEN_STOCK);
						ps14.setInt(1, accountID);
						ps14.setInt(2, prodId);
						rs14 = ps14.executeQuery();
						if(rs14.next())
						{
							openingStock =  rs14.getString("OPENING_STOCK");
							openingStockDist = openingStock;
							saleByRetailersQtyDist = rs14.getString("SALE");// Sold BY Retailers
							closingStock = rs14.getString("CLOSING_STOCK");
						}
																				
						stockDetailReport.setOpeningStockQty(openingStock);
						stockDetailReport.setAllocationQty("0");
						stockDetailReport.setStockReceivedQty("0");
						stockDetailReport.setSaleByRetailersQty(saleByRetailersQtyDist);
						stockDetailReport.setClosingStock(closingStock);
						
						stockDetailReportList.add(stockDetailReport);
					}
					if(ps14!=null) {
						ps14.clearParameters();
						ps14=null;
					}
					if(rs14!=null) {
						rs14=null;
					}
				}
				
				if(ps13!=null) {
					ps13.clearParameters();
					ps13=null;
				}
				if(rs13!=null) {
					rs13=null;
				}
			}
			
			if(ps12!=null) {
				ps12.clearParameters();
				ps12=null;
			}
			if(rs12!=null) {
				rs12=null;
			}
			
			// Insert blank line
			StockDetailReport tempRecordsStock = new StockDetailReport();
			stockDetailReportList.add(tempRecordsStock);			
			
			// Insert blank line
			StockDetailReport tempRecordsStock1 = new StockDetailReport();
			stockDetailReportList.add(tempRecordsStock1);					    
			
			
		}catch(SQLException sqle){
			sqle.printStackTrace();
			throw new DAOException("SQLException: " + sqle.getMessage());
		}catch(Exception e){
			e.printStackTrace();
			throw new DAOException("Exception: " + e.getMessage());
		}finally{
			try {
				if (rs1 != null)
					rs1.close();
				if (ps1 != null)
					ps1.close();
				
				if (rs2 != null)
					rs2.close();
				if (ps2 != null)
					ps2.close();
				
				if (rs3 != null)
					rs3.close();
				if (ps3 != null)
					ps3.close();
				
				if (rs4 != null)
					rs4.close();
				if (ps4 != null)
					ps4.close();
				
				if (rs5 != null)
					rs5.close();
				if (ps5 != null)
					ps5.close();
				
				if (rs6 != null)
					rs6.close();
				if (ps6 != null)
					ps6.close();
				
				if (rs7 != null)
					rs7.close();
				if (ps7 != null)
					ps7.close();
				
				if (rs8 != null)
					rs8.close();
				if (ps8 != null)
					ps8.close();
				
				if (rs9 != null)
					rs9.close();
				if (ps9 != null)
					ps9.close();
				
				if (rs10 != null)
					rs10.close();
				if (ps10 != null)
					ps10.close();
				
				if (rs11 != null)
					rs11.close();
				if (ps11 != null)
					ps11.close();
				
				if (rs12 != null)
					rs12.close();
				if (ps12 != null)
					ps12.close();
				
				if (rs13 != null)
					rs13.close();
				if (ps13 != null)
					ps13.close();
				
				if (rs14 != null)
					rs14.close();
				if (ps14 != null)
					ps14.close();
				if (con != null) 
				{
					con.close();
				}
			} catch (Exception e) {
				e.printStackTrace();
				logger.error("Error Occured in ReportDAOImpl "+e.getMessage());
			}
		}
		return stockDetailReportList;
	}


}