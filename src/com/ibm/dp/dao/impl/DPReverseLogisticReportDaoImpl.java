package com.ibm.dp.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

import com.ibm.dp.beans.DPReverseLogisticReportFormBean;
import com.ibm.dp.common.Constants;
import com.ibm.dp.common.DBQueries;
import com.ibm.dp.dao.DPReverseLogisticReportDao;
import com.ibm.dp.dto.DPReverseLogisticReportDTO;
import com.ibm.dp.dto.DPSCMConsumptionReportDto;
import com.ibm.utilities.Utility;
import com.ibm.virtualization.recharge.dao.rdbms.BaseDaoRdbms;
import com.ibm.virtualization.recharge.db.DBConnectionManager;
import com.ibm.virtualization.recharge.exception.DAOException;

public class DPReverseLogisticReportDaoImpl extends BaseDaoRdbms implements DPReverseLogisticReportDao{

	private Logger logger = Logger.getLogger(DPReportDaoImpl.class.getName());
	public DPReverseLogisticReportDaoImpl(Connection con) {
		super(con);
	}
	
	public List getRevLogTsmDistLogin(int distId) throws DAOException {

		Connection con = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		List<DPReverseLogisticReportFormBean> revLogList = new ArrayList<DPReverseLogisticReportFormBean>();
		try 
		{
			con = DBConnectionManager.getDBConnection(); // db2
			
			
			System.out.println("distId--"+distId);
			// Fetch level base
				ps = con.prepareStatement(DBQueries.GET_REV_LOG_DISABLE_TSM_DIST);
				ps.setInt(1, distId);
				rs = ps.executeQuery();
			
			DPReverseLogisticReportFormBean statusReportBean = null;
			while(rs.next())
			{
				statusReportBean = new DPReverseLogisticReportFormBean();
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
	
	public List getRevLogTsmAccountDetails(int circleID) throws DAOException {

		Connection con = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		List<DPReverseLogisticReportFormBean> revLogList = new ArrayList<DPReverseLogisticReportFormBean>();
		try 
		{
			con = DBConnectionManager.getDBConnection(); // db2
			
			
			System.out.println("circleID--"+circleID);
			// Fetch level base
			if(circleID == 0)
			{
				ps = con.prepareStatement(DBQueries.GET_ACCOUNT_DETAILS_REV_LOG_TSM_ALL);
				ps.setInt(1, 5);
			}
			else
			{
				ps = con.prepareStatement(DBQueries.GET_ACCOUNT_DETAILS_REV_LOG_TSM);
				ps.setString(1, Constants.ACC_LEVEL_TSM);
				ps.setInt(2, circleID);
			}
			
			rs = ps.executeQuery();
			
			DPReverseLogisticReportFormBean statusReportBean = null;
			while(rs.next())
			{
				statusReportBean = new DPReverseLogisticReportFormBean();
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
		List<DPReverseLogisticReportFormBean> revLogList = new ArrayList<DPReverseLogisticReportFormBean>();
		try 
		{
			con = DBConnectionManager.getDBConnection(); // db2
			
			
			
			// Fetch level base
			
			if(levelId != 0 && circleId != 0)
			{
				ps = con.prepareStatement(DBQueries.GET_ACCOUNT_DETAILS_REV_LOG_DIST);
				ps.setInt(1, levelId);
			}
			if(levelId != 0 && circleId == 0)
			{
				ps = con.prepareStatement(DBQueries.GET_ACCOUNT_DETAILS_REV_LOG_DIST);
				ps.setInt(1, levelId);
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
			
			DPReverseLogisticReportFormBean statusReportBean = null;
			while(rs.next())
			{
				statusReportBean = new DPReverseLogisticReportFormBean();
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

		

	public List<DPReverseLogisticReportDTO> getRevLogReportExcel(int distId, String fromDate, String toDate , String circleId , String tsmId) throws DAOException
	{
		//Connection con = null;
		PreparedStatement ps1 = null;
		ResultSet rs1 = null;
		
		PreparedStatement ps2 = null;
		ResultSet rs2 = null;
		
		PreparedStatement psDist = null;
		ResultSet rsDist = null;
		
		PreparedStatement psProd = null;
		ResultSet rsProd = null;
		
		int churnInventory= 0;
		int upgradeInventory= 0;
		int doaInventory= 0;
		int defectiveInventory= 0;
		int c2sInventory= 0;
		int s2wInventory = 0;
		int repInventory = 0;
		
		int churnInventoryHist= 0;
		int upgradeInventoryHist= 0;
		int doaInventoryHist= 0;
		int defectiveInventoryHist= 0;
		int c2sInventoryHist= 0;
		int s2wInventoryHist = 0;
		int repInventoryHist = 0;
		int productID = 0;
		String accountName= "";
		String productName = "";
				
		String distQuery = "";
		boolean checkRecord = false;
		
		List<DPReverseLogisticReportDTO> revLogReportList = new ArrayList<DPReverseLogisticReportDTO>();
		DPReverseLogisticReportDTO dpReverseLogisticReportDTO = null ;
				
		try 
		{
			ps1 = 	connection.prepareStatement(DBQueries.SQL_REV_LOG_Excel);
			logger.info("Reverse Logistics Report Main Query  ::  "+DBQueries.SQL_REV_LOG_Excel);
			ps2 = 	connection.prepareStatement(DBQueries.SQL_REV_LOG_HIST_Excel);
			logger.info("Reverse Logistics Report History Query  ::  "+DBQueries.SQL_REV_LOG_HIST_Excel);
			psProd= connection.prepareStatement(DBQueries.SQL_REV_LOG_PRODUCT);
			
			logger.info("fromDate  ::  "+fromDate);
			logger.info("toDate  ::  "+toDate);
			
			fromDate = Utility.convertDateFormat(fromDate, "MM/dd/yyyy", "yyyy-MM-dd");
			toDate = Utility.convertDateFormat(toDate, "MM/dd/yyyy", "yyyy-MM-dd");
			
			logger.info("Converted fromDate  ::  "+fromDate);
			logger.info("Converted toDate  ::  "+toDate);
			
			if(circleId.equals("0"))
			{
				if(tsmId.equals("0"))
				{
					if(distId == 0)
					{
						distQuery = "select ACCOUNT_ID from VR_ACCOUNT_DETAILS where ACCOUNT_LEVEL = 6 with ur";
						psDist = connection.prepareStatement(distQuery);
					}
					else
					{
						distQuery = "select ACCOUNT_ID from VR_ACCOUNT_DETAILS where ACCOUNT_LEVEL = 6 and ACCOUNT_ID=? with ur";
						psDist = connection.prepareStatement(distQuery);
						psDist.setInt(1, distId);
					}
				}
				else
				{
					if(distId == 0)
					{
						distQuery = "select ACCOUNT_ID from VR_ACCOUNT_DETAILS where ACCOUNT_LEVEL = 6 and PARENT_ACCOUNT = ? with ur";
						psDist = connection.prepareStatement(distQuery);
						psDist.setInt(1, Integer.parseInt(tsmId));
					}
					else
					{
						distQuery = "select ACCOUNT_ID from VR_ACCOUNT_DETAILS where ACCOUNT_LEVEL = 6 and PARENT_ACCOUNT = ? and ACCOUNT_ID=? with ur";
						psDist = connection.prepareStatement(distQuery);
						psDist.setInt(1, Integer.parseInt(tsmId));
						psDist.setInt(2, distId);
					}
				}
			}
			else
			{
				if(tsmId.equals("0"))
				{
					if(distId == 0)
					{
						distQuery = "select ACCOUNT_ID from VR_ACCOUNT_DETAILS where ACCOUNT_LEVEL = 6 and CIRCLE_ID = ? with ur";
						psDist = connection.prepareStatement(distQuery);
						psDist.setInt(1, Integer.parseInt(circleId));
					}
					else
					{
						distQuery = "select ACCOUNT_ID from VR_ACCOUNT_DETAILS where ACCOUNT_LEVEL = 6 and ACCOUNT_ID=? and CIRCLE_ID = ? with ur";
						psDist = connection.prepareStatement(distQuery);
						psDist.setInt(1, distId);
						psDist.setInt(2, Integer.parseInt(circleId));
					}
				}
				else
				{
					if(distId == 0)
					{
						distQuery = "select ACCOUNT_ID from VR_ACCOUNT_DETAILS where ACCOUNT_LEVEL = 6 and PARENT_ACCOUNT = ? and CIRCLE_ID = ? with ur";
						psDist = connection.prepareStatement(distQuery);
						psDist.setInt(1, Integer.parseInt(tsmId));
						psDist.setInt(2, Integer.parseInt(circleId));
					}
					else
					{
						distQuery = "select ACCOUNT_ID from VR_ACCOUNT_DETAILS where ACCOUNT_LEVEL = 6 and PARENT_ACCOUNT = ? and ACCOUNT_ID=? and CIRCLE_ID = ? with ur";
						psDist = connection.prepareStatement(distQuery);
						psDist.setInt(1, Integer.parseInt(tsmId));
						psDist.setInt(2, distId);
						psDist.setInt(3, Integer.parseInt(circleId));
					}
				}
			}
			rsDist = psDist.executeQuery();
			
		while(rsDist.next())
		{
			int distIdFetched = 0;
			checkRecord = false;
			distIdFetched =  rsDist.getInt("ACCOUNT_ID");
			
			
			psProd.setInt(1, distIdFetched);
			psProd.setInt(2, distIdFetched);
			
			rsProd = psProd.executeQuery();
	
		while(rsProd.next())
		{
			churnInventory= 0;
			upgradeInventory= 0;
			doaInventory= 0;
			defectiveInventory= 0;
			c2sInventory= 0;
			s2wInventory = 0;
			repInventory = 0;
			
			churnInventoryHist= 0;
			upgradeInventoryHist= 0;
			doaInventoryHist= 0;
			defectiveInventoryHist= 0;
			c2sInventoryHist= 0;
			s2wInventoryHist = 0;
			repInventoryHist = 0;
			productID = 0;
			accountName= "";
			productName = "";
			checkRecord = false;
			productID = rsProd.getInt("PRODUCT_ID");
			
			ps1.setString(1,fromDate);
			ps1.setString(2,toDate);
			ps1.setString(3,fromDate);
			ps1.setString(4,toDate);
			ps1.setString(5,fromDate);
			ps1.setString(6,toDate);
			ps1.setString(7,fromDate);
			ps1.setString(8,toDate);
			ps1.setString(9,fromDate);
			ps1.setString(10,toDate);
			ps1.setString(11,fromDate);
			ps1.setString(12,toDate);
			ps1.setString(13,fromDate);
			ps1.setString(14,toDate);
		
		
			ps1.setInt(15, productID );
			ps1.setString(16,fromDate);
			ps1.setString(17,toDate);
			ps1.setInt(18, distIdFetched);
					
			rs1 = ps1.executeQuery();
				
			if(rs1.next())
			{
				accountName = rs1.getString("ACCOUNT_NAME");
				productName = rs1.getString("PRODUCT_NAME");
				productID = rs1.getInt("PRODUCT_ID");
				churnInventory = rs1.getInt("CHURN");
				upgradeInventory = rs1.getInt("UPGRADE");
				doaInventory = rs1.getInt("DOA");
				defectiveInventory = rs1.getInt("DEFECTIVE");
				c2sInventory = rs1.getInt("C2S");
				s2wInventory = rs1.getInt("S2W");
				repInventory = rs1.getInt("REP");
				checkRecord = true;
			}
			rs1 = null;
		
		
				
			ps2.setString(1,fromDate);
			ps2.setString(2,toDate);
			ps2.setString(3,fromDate);
			ps2.setString(4,toDate);
			ps2.setString(5,fromDate);
			ps2.setString(6,toDate);
			ps2.setString(7,fromDate);
			ps2.setString(8,toDate);
			ps2.setString(9,fromDate);
			ps2.setString(10,toDate);
			ps2.setString(11,fromDate);
			ps2.setString(12,toDate);
			ps2.setString(13,fromDate);
			ps2.setString(14,toDate);
				
				
			ps2.setInt(15, productID );
			ps2.setString(16,fromDate);
			ps2.setString(17,toDate);
			ps2.setInt(18, distIdFetched);
					
			
			rs2 = ps2.executeQuery(); 				
			
			if(rs2.next())
			{
				accountName = rs2.getString("ACCOUNT_NAME");
				productName = rs2.getString("PRODUCT_NAME");
				productID = rs2.getInt("PRODUCT_ID");
				churnInventoryHist = rs2.getInt("CHURN");
				upgradeInventoryHist = rs2.getInt("UPGRADE");
				doaInventoryHist = rs2.getInt("DOA");
				defectiveInventoryHist = rs2.getInt("DEFECTIVE");
				c2sInventoryHist = rs2.getInt("C2S");
				s2wInventoryHist = rs2.getInt("S2W");
				repInventoryHist = rs2.getInt("REP");
				checkRecord = true;
				
			}	
				rs2 = null;
				
				if(checkRecord) {
				dpReverseLogisticReportDTO= new DPReverseLogisticReportDTO();
				
				dpReverseLogisticReportDTO.setAccountName(accountName);
				dpReverseLogisticReportDTO.setProductName(productName);
				dpReverseLogisticReportDTO.setChurnInventory(churnInventory + churnInventoryHist);
				dpReverseLogisticReportDTO.setUpgradeInventory(upgradeInventory+upgradeInventoryHist);
				dpReverseLogisticReportDTO.setDoaInventory(doaInventory + doaInventoryHist);
				dpReverseLogisticReportDTO.setDefectiveInventory(defectiveInventory + defectiveInventoryHist);
				dpReverseLogisticReportDTO.setC2sInventory(c2sInventory + c2sInventoryHist);
				dpReverseLogisticReportDTO.setS2wInventory(s2wInventory + s2wInventoryHist);
				dpReverseLogisticReportDTO.setRepInventory(repInventory + repInventoryHist);
				
				
				revLogReportList.add(dpReverseLogisticReportDTO);
				dpReverseLogisticReportDTO = null; 
				}
			}// END PRODUCT WHILE
			rsProd = null;
			
	}	
		}catch(SQLException sqle){
			sqle.printStackTrace();
			throw new DAOException("SQLException: " + sqle.getMessage());
		}catch(Exception e){
			e.printStackTrace();
			throw new DAOException("Exception: " + e.getMessage());
		}finally{
			try {
				DBConnectionManager.releaseResources(ps1, rs1);
				DBConnectionManager.releaseResources(ps2, rs2);
				DBConnectionManager.releaseResources(psDist, rsDist);
				DBConnectionManager.releaseResources(psProd, rsProd);
			} catch (Exception e) {
				e.printStackTrace();
				logger.error("Error Occured in ReportDAOImpl "+e.getMessage());
			}
		}
		return revLogReportList;
	}
	
	public List<DPReverseLogisticReportDTO> getRevLogReportDetailExcel(int distId, String fromDate, String toDate , String circleId , String tsmId) throws DAOException
	{
		System.out.println("*getRevLogReportDetailExcel*************");
		PreparedStatement ps1 = null;
		ResultSet rs1 = null;
		
		PreparedStatement psDist = null;
		ResultSet rsDist = null;
		
		String ser_no_collect= "";
		String collection_name= "";
		String defect_name= "";
		String collection_date= "";
		String remarks= "";
		String status = "";
		
		String accountName= "";
		String productName = "";
				
		String distQuery = "";
		
		List<DPReverseLogisticReportDTO> revLogReportList = new ArrayList<DPReverseLogisticReportDTO>();
		DPReverseLogisticReportDTO dpReverseLogisticReportDTO = null ;
				
		try 
		{
			ps1 = 	connection.prepareStatement(DBQueries.SQL_REV_LOG_DETAIL_Excel);
			
			if(circleId.equals("0"))
			{
				if(tsmId.equals("0"))
				{
					if(distId == 0)
					{
						distQuery = "select ACCOUNT_ID from VR_ACCOUNT_DETAILS where ACCOUNT_LEVEL = 6 with ur";
						psDist = connection.prepareStatement(distQuery);
					}
					else
					{
						distQuery = "select ACCOUNT_ID from VR_ACCOUNT_DETAILS where ACCOUNT_LEVEL = 6 and ACCOUNT_ID=? with ur";
						psDist = connection.prepareStatement(distQuery);
						psDist.setInt(1, distId);
					}
				}
				else
				{
					if(distId == 0)
					{
						distQuery = "select ACCOUNT_ID from VR_ACCOUNT_DETAILS where ACCOUNT_LEVEL = 6 and PARENT_ACCOUNT = ? with ur";
						psDist = connection.prepareStatement(distQuery);
						psDist.setInt(1, Integer.parseInt(tsmId));
					}
					else
					{
						distQuery = "select ACCOUNT_ID from VR_ACCOUNT_DETAILS where ACCOUNT_LEVEL = 6 and PARENT_ACCOUNT = ? and ACCOUNT_ID=? with ur";
						psDist = connection.prepareStatement(distQuery);
						psDist.setInt(1, Integer.parseInt(tsmId));
						psDist.setInt(2, distId);
					}
				}
			}
			else
			{
				if(tsmId.equals("0"))
				{
					if(distId == 0)
					{
						distQuery = "select ACCOUNT_ID from VR_ACCOUNT_DETAILS where ACCOUNT_LEVEL = 6 and CIRCLE_ID = ? with ur";
						psDist = connection.prepareStatement(distQuery);
						psDist.setInt(1, Integer.parseInt(circleId));
					}
					else
					{
						distQuery = "select ACCOUNT_ID from VR_ACCOUNT_DETAILS where ACCOUNT_LEVEL = 6 and ACCOUNT_ID=? and CIRCLE_ID = ? with ur";
						psDist = connection.prepareStatement(distQuery);
						psDist.setInt(1, distId);
						psDist.setInt(2, Integer.parseInt(circleId));
					}
				}
				else
				{
					if(distId == 0)
					{
						distQuery = "select ACCOUNT_ID from VR_ACCOUNT_DETAILS where ACCOUNT_LEVEL = 6 and PARENT_ACCOUNT = ? and CIRCLE_ID = ? with ur";
						psDist = connection.prepareStatement(distQuery);
						psDist.setInt(1, Integer.parseInt(tsmId));
						psDist.setInt(2, Integer.parseInt(circleId));
					}
					else
					{
						distQuery = "select ACCOUNT_ID from VR_ACCOUNT_DETAILS where ACCOUNT_LEVEL = 6 and PARENT_ACCOUNT = ? and ACCOUNT_ID=? and CIRCLE_ID = ? with ur";
						psDist = connection.prepareStatement(distQuery);
						psDist.setInt(1, Integer.parseInt(tsmId));
						psDist.setInt(2, distId);
						psDist.setInt(3, Integer.parseInt(circleId));
					}
				}
			}
			rsDist = psDist.executeQuery();
			
while(rsDist.next())
{
	int distIdFetched = 0;
	
	accountName = "";
	ser_no_collect= "";
	collection_name= "";
	defect_name= "";
	collection_date= "";
	remarks= "";
	status = "";
	
	distIdFetched =  rsDist.getInt("ACCOUNT_ID");
	
	ps1.setInt(1, distIdFetched  );
	ps1.setDate(2, Utility.getSqlDateFromString(fromDate.trim(), "MM/dd/yyyy"));
	ps1.setDate(3, Utility.getSqlDateFromString(toDate.trim(), "MM/dd/yyyy"));
	ps1.setInt(4, distIdFetched  );
	ps1.setDate(5, Utility.getSqlDateFromString(fromDate.trim(), "MM/dd/yyyy"));
	ps1.setDate(6, Utility.getSqlDateFromString(toDate.trim(), "MM/dd/yyyy"));
			
				
	rs1 = ps1.executeQuery();
			
		while(rs1.next())
		{
			accountName = rs1.getString("ACCOUNT_NAME");
			productName = rs1.getString("PRODUCT_NAME");
			ser_no_collect =rs1.getString("SERIAL_NO_COLLECT");
			collection_name =rs1.getString("COLLECTION_NAME");
			defect_name =rs1.getString("DEFECT_NAME");
			collection_date =rs1.getString("coldate");
			remarks =rs1.getString("REMARKS");
			status = rs1.getString("STATUS");
		
				dpReverseLogisticReportDTO= new DPReverseLogisticReportDTO();
				
				dpReverseLogisticReportDTO.setAccountName(accountName);
				dpReverseLogisticReportDTO.setProductName(productName);
				dpReverseLogisticReportDTO.setSer_no_collect(ser_no_collect);
				dpReverseLogisticReportDTO.setCollection_name(collection_name);
				dpReverseLogisticReportDTO.setDefect_name(defect_name);
				dpReverseLogisticReportDTO.setCollection_date(collection_date);
				dpReverseLogisticReportDTO.setRemarks(remarks);
				dpReverseLogisticReportDTO.setStatus(status);
				
				revLogReportList.add(dpReverseLogisticReportDTO);
				dpReverseLogisticReportDTO = null; 
			}
			rs1 = null;
		// END PRODUCT WHILE
			 ///////////////////////////////// END of MAIN WHILE
		}
		}catch(SQLException sqle){
			sqle.printStackTrace();
			throw new DAOException("SQLException: " + sqle.getMessage());
		}catch(Exception e){
			e.printStackTrace();
			throw new DAOException("Exception: " + e.getMessage());
		}finally{
			try {
				DBConnectionManager.releaseResources(ps1, rs1);
				DBConnectionManager.releaseResources(psDist, rsDist);
			} catch (Exception e) {
				e.printStackTrace();
				logger.error("Error Occured in Detail ReportDAOImpl "+e.getMessage());
			}
		}
		return revLogReportList;

	}

	public List<DPReverseLogisticReportDTO> getlastPOReportExcel(int distId,String circleId ,String tsmId) throws DAOException
	{
		PreparedStatement ps1 = null;
		ResultSet rs1 = null;
		PreparedStatement ps2 = null;
		ResultSet rs2 = null;
		String preQuery="";
		
		List<DPReverseLogisticReportDTO> revLogReportList = new ArrayList<DPReverseLogisticReportDTO>();
		DPReverseLogisticReportDTO dpReverseLogisticReportDTO = null ;
		try 
		{
			ps2 = 	connection.prepareStatement(DBQueries.GET_LAST_PO_DETAILS);
			
			if(circleId.equals("0"))
			{
				if(tsmId.equals("0"))
				{
					if(distId == 0)
					{
						preQuery = " AND VAD.ACCOUNT_LEVEL = 6";
					}
					else
					{
						preQuery = " AND VAD.ACCOUNT_LEVEL = 6 and VAD.ACCOUNT_ID="+distId;
					}
				}
				else
				{
					if(distId == 0)
					{
						preQuery = " AND VAD.ACCOUNT_LEVEL = 6 and VAD.PARENT_ACCOUNT = "+Integer.parseInt(tsmId);
					}
					else
					{
						preQuery = " AND VAD.ACCOUNT_LEVEL = 6 and VAD.PARENT_ACCOUNT = "+Integer.parseInt(tsmId) +" and VAD.ACCOUNT_ID="+distId+" ";
					}
				}
			}
			else
			{
				if(tsmId.equals("0"))
				{
					if(distId == 0)
					{
						preQuery = " AND VAD.ACCOUNT_LEVEL = 6 and VAD.CIRCLE_ID = "+ Integer.parseInt(circleId) +"";
					}
					else
					{
						preQuery = " AND VAD.ACCOUNT_LEVEL = 6 and VAD.ACCOUNT_ID="+distId+" and VAD.CIRCLE_ID = "+Integer.parseInt(circleId)+" " ;
					}
				}
				else
				{
					if(distId == 0)
					{
						preQuery = " AND VAD.ACCOUNT_LEVEL = 6 and VAD.PARENT_ACCOUNT = "+Integer.parseInt(tsmId)+" and VAD.CIRCLE_ID = "+Integer.parseInt(circleId)+" " ;
					}
					else
					{
						preQuery = " AND VAD.ACCOUNT_LEVEL = 6 and VAD.PARENT_ACCOUNT = "+Integer.parseInt(tsmId)+" and VAD.ACCOUNT_ID="+distId+" and VAD.CIRCLE_ID = "+Integer.parseInt(circleId)+" " ;
					}
				}
			}
	int pr_no=0;
	String sql=DBQueries.GET_LAST_PO;
	sql = sql + preQuery +" group by b.PR_DIST_ID,CIRCLE_NAME,LOGIN_NAME,VAD.DISTRIBUTOR_LOCATOR ,VAD.ACCOUNT_NAME,VAD1.ACCOUNT_NAME ORDER BY PR_DIST_ID WITH UR ";
	ps1 = 	connection.prepareStatement(sql);
	rs1 = ps1.executeQuery();
	while(rs1.next())
	{
		pr_no = rs1.getInt("PR_NO");
		ps2.setInt(1, pr_no);
		rs2 = ps2.executeQuery();

		while(rs2.next())
		{
			 dpReverseLogisticReportDTO = new DPReverseLogisticReportDTO() ;
			 
			 dpReverseLogisticReportDTO.setLoginName(rs1.getString("LOGIN_NAME"));
			 dpReverseLogisticReportDTO.setCircle(rs1.getString("CIRCLE_NAME"));
			 dpReverseLogisticReportDTO.setDistributor_locator_code(rs1.getString("DISTRIBUTOR_LOCATOR"));
			 dpReverseLogisticReportDTO.setAccount_name(rs1.getString("ACCOUNT_NAME"));
			 dpReverseLogisticReportDTO.setTsmName(rs1.getString("TSM_ACCOUNT_NAME"));
			 
			 
			 dpReverseLogisticReportDTO.setProduct_name(rs2.getString("PRODUCT_NAME"));
			 dpReverseLogisticReportDTO.setNon_ser_qty(rs2.getString("DP_OPEN_STOCK"));
			 dpReverseLogisticReportDTO.setSer_qty(rs2.getString("DP_QTY"));
			 dpReverseLogisticReportDTO.setPhysical_stock(rs2.getString("RAISED_QTY"));
			 dpReverseLogisticReportDTO.setLast_pr_number(pr_no+"");
			 dpReverseLogisticReportDTO.setLast_pr_date(rs2.getString("PO_DATE"));
			 dpReverseLogisticReportDTO.setIn_hand_qty(rs2.getString("INHAND_QTY"));
			 dpReverseLogisticReportDTO.setTotal_dp_qty( rs2.getInt("DP_OPEN_STOCK") + rs2.getInt("DP_QTY"));
			 
			 revLogReportList.add(dpReverseLogisticReportDTO);
		}
		DBConnectionManager.releaseResources(null, rs2);
	}
		}catch(SQLException sqle){
			sqle.printStackTrace();
			throw new DAOException("SQLException: " + sqle.getMessage());
		}catch(Exception e){
			e.printStackTrace();
			throw new DAOException("Exception: " + e.getMessage());
		}finally{
			try {
				DBConnectionManager.releaseResources(ps1, rs1);
				DBConnectionManager.releaseResources(ps2, rs2);
			} catch (Exception e) {
				e.printStackTrace();
				logger.error("Error Occured in ReportDAOImpl "+e.getMessage());
			}
		}
		return revLogReportList;
	}
	
	
	 //Add by harbans on MIS Reports consumption
		public List<DPSCMConsumptionReportDto> getConsumptionReportExcel(int distId , int tsmId, int circleId, String fromDate, String toDate) throws DAOException
		{
			logger.info("Init getConsumptionReportExcel().");
			Connection conDTH = null;
			Connection conSCM = null;
			PreparedStatement psSCM = null;
			ResultSet rsSCM = null;
			PreparedStatement psDTH = null;
			ResultSet rsDTH = null;
			PreparedStatement ps = null;
			ResultSet rs = null;
			int accountId = 0;
			
			List<DPSCMConsumptionReportDto> consumptionReportList = new ArrayList<DPSCMConsumptionReportDto>(); 
			List<DPSCMConsumptionReportDto> consumptionTempReportList = null;
			
			DPSCMConsumptionReportDto consumptionReportDto = new DPSCMConsumptionReportDto();
			try
			{
				conDTH = DBConnectionManager.getDBConnection(); // db2
				conSCM = DBConnectionManager.getDBConnectionOracleSCM();//Oracle SCM
				
				// In case of All Distributor.
				if(distId == 0 && tsmId == 0)
				{
					psDTH = conDTH.prepareStatement(DBQueries.GET_ALL_ACCOUNT_TSM_DIST_ON_CIRCLE);
					psDTH.setInt(1,circleId);
					rsDTH = psDTH.executeQuery();
					
					while(rsDTH.next())
					{
						accountId = rsDTH.getInt("ACCOUNT_ID");
						consumptionTempReportList = getConsumptionRecord(conDTH, conSCM, accountId, fromDate, toDate);
						consumptionReportList.addAll(consumptionTempReportList);
					}
				}
				else if(distId == 0 && tsmId != 0)
				{
					psDTH = conDTH.prepareStatement(DBQueries.GET_ALL_ACCOUNT_ON_PARENT);
					psDTH.setInt(1,tsmId);
					rsDTH = psDTH.executeQuery();
					
					while(rsDTH.next())
					{
						accountId = rsDTH.getInt("ACCOUNT_ID");
						consumptionTempReportList = getConsumptionRecord(conDTH, conSCM, accountId, fromDate, toDate);
						consumptionReportList.addAll(consumptionTempReportList);
					}
				}
				else
				{
					consumptionTempReportList = getConsumptionRecord(conDTH, conSCM, distId, fromDate, toDate);
					consumptionReportList.addAll(consumptionTempReportList);
				}
				
			}
			catch(SQLException sqle)
			{
				sqle.printStackTrace();
				throw new DAOException("SQLException: " + sqle.getMessage());
			}catch(Exception e)
			{
				e.printStackTrace();
				throw new DAOException("Exception: " + e.getMessage());
			}finally
			{
				try {
					DBConnectionManager.releaseResources(psDTH, rsDTH);
					DBConnectionManager.releaseResources(psSCM, rsSCM);
					DBConnectionManager.releaseResources(ps, rs);
					//Added by Shilpa khanna to release DB2 connection
					if (conDTH != null) {
						conDTH.close();
					}
//					Added by Shilpa khanna to release Oracle SCM connection
					if (conSCM != null) {
						conSCM.close();
					}
				} catch (Exception e) {
					e.printStackTrace();
					logger.error("Error Occured in ReportDAOImpl "+e.getMessage());
				}
			}
			
			logger.info("Consumption posting records size = " + consumptionReportList.size());
			return consumptionReportList;
		}

	 
	 
		
    // Add by harbans 27 may on MIS Reports consumption Pan India Level
	public List<DPSCMConsumptionReportDto> getConsumptionReportExcelPanIndia(int distId , int tsmId, String fromDate, String toDate) throws DAOException
	{
		logger.info("Init getConsumptionReportExcel().");
		Connection conDTH = null;
		Connection conSCM = null;
		PreparedStatement psSCM = null;
		ResultSet rsSCM = null;
		PreparedStatement psDTH = null;
		ResultSet rsDTH = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		int accountId = 0;
		
		List<DPSCMConsumptionReportDto> consumptionReportList = new ArrayList<DPSCMConsumptionReportDto>(); 
		List<DPSCMConsumptionReportDto> consumptionTempReportList = null;
		
		DPSCMConsumptionReportDto consumptionReportDto = new DPSCMConsumptionReportDto();
		try
		{
			conDTH = DBConnectionManager.getDBConnection(); // db2
			conSCM = DBConnectionManager.getDBConnectionOracleSCM();//Oracle SCM
			
			// In case of All Distributor.
			if(distId == 0 && tsmId == 0)
			{
				psDTH = conDTH.prepareStatement(DBQueries.GET_ALL_ACCOUNT_TSM_DIST_ON_ALL_CIRCLE);
				rsDTH = psDTH.executeQuery();
				
				while(rsDTH.next())
				{
					accountId = rsDTH.getInt("ACCOUNT_ID");
					consumptionTempReportList = getConsumptionRecord(conDTH, conSCM, accountId, fromDate, toDate);
					consumptionReportList.addAll(consumptionTempReportList);
				}
			}
			else if(distId == 0 && tsmId != 0)
			{
				psDTH = conDTH.prepareStatement(DBQueries.GET_ALL_ACCOUNT_ON_PARENT);
				psDTH.setInt(1,tsmId);
				rsDTH = psDTH.executeQuery();
				
				while(rsDTH.next())
				{
					accountId = rsDTH.getInt("ACCOUNT_ID");
					consumptionTempReportList = getConsumptionRecord(conDTH, conSCM, accountId, fromDate, toDate);
					consumptionReportList.addAll(consumptionTempReportList);
				}
			}
			else
			{
				consumptionTempReportList = getConsumptionRecord(conDTH, conSCM, distId, fromDate, toDate);
				consumptionReportList.addAll(consumptionTempReportList);
			}
			
		}
		catch(SQLException sqle)
		{
			sqle.printStackTrace();
			throw new DAOException("SQLException: " + sqle.getMessage());
		}catch(Exception e)
		{
			e.printStackTrace();
			throw new DAOException("Exception: " + e.getMessage());
		}finally
		{
			try {
				DBConnectionManager.releaseResources(psDTH, rsDTH);
				DBConnectionManager.releaseResources(psSCM, rsSCM);
				DBConnectionManager.releaseResources(ps, rs);
				//Added by Shilpa khanna to release DB2 connection
				if (conDTH != null) {
					conDTH.close();
				}
//				Added by Shilpa khanna to release Oracle SCM connection
				if (conSCM != null) {
					conSCM.close();
				}
			} catch (Exception e) {
				e.printStackTrace();
				logger.error("Error Occured in ReportDAOImpl "+e.getMessage());
			}
		}
		
		logger.info("Consumption posting records size = " + consumptionReportList.size());
		return consumptionReportList;
	}

	
	//	Add by harbans on MIS consumption records
	private List<DPSCMConsumptionReportDto> getConsumptionRecord(Connection conDTH, Connection conSCM, int distId ,String fromDate, String toDate)throws DAOException
	{
		logger.info("Init getConsumptionReportExcel().");
		PreparedStatement psSCM = null;
		ResultSet rsSCM = null;
		PreparedStatement psDTH = null;
		ResultSet rsDTH = null;
		
		List<DPSCMConsumptionReportDto> consumptionReportList = new ArrayList<DPSCMConsumptionReportDto>(); 
		DPSCMConsumptionReportDto consumptionReportDto = new DPSCMConsumptionReportDto();
		int distLoginId = 0;
		String distributorCode = "";
			
		try
		{
			// Get all records from Oracle DPSCM history table
			psSCM = conSCM.prepareStatement(DBQueries.GET_SCM_HIST_CONSUMTION_REPORT);
			psSCM.setInt(1, distId);
			psSCM.setString(2, Constants.CONSUMPTION_TYPE); //Airtel DP Consumption Booking
			psSCM.setString(3, fromDate);
			psSCM.setString(4, toDate);
			psSCM.setInt(5, distId);
			psSCM.setString(6, Constants.CONSUMPTION_TYPE); //Airtel DP Consumption Booking
			psSCM.setString(7, fromDate);
			psSCM.setString(8, toDate);			
			rsSCM = psSCM.executeQuery();
			
			while(rsSCM.next())
			{
				consumptionReportDto = new DPSCMConsumptionReportDto();
				consumptionReportDto.setBatchId(rsSCM.getString("DP_SCM_ID"));
				consumptionReportDto.setSource(rsSCM.getString("SOURCE"));
				consumptionReportDto.setProcess(rsSCM.getString("TRANSACTION_TYPE"));
				consumptionReportDto.setCompanyCode(rsSCM.getString("FROM_COMPANY_CODE"));
				consumptionReportDto.setArea(rsSCM.getString("FROM_AREA"));
				consumptionReportDto.setSubArea(rsSCM.getString("FROM_SUB_AREA"));
				consumptionReportDto.setSourceType(rsSCM.getString("FROM_SOURCE_TYPE"));
				consumptionReportDto.setProductCode(rsSCM.getString("ITEM_CODE"));
				consumptionReportDto.setQuantity(rsSCM.getString("QUANTITY"));
				consumptionReportDto.setStatus(rsSCM.getString("STATUS"));
				consumptionReportDto.setErrorDesc(rsSCM.getString("ERROR"));
				consumptionReportDto.setRequestId(rsSCM.getString("REQUEST_ID"));
				consumptionReportDto.setCreatedBy(rsSCM.getString("CREATED_BY"));
				consumptionReportDto.setCreatedDate(rsSCM.getString("CREATION_DATE"));
				consumptionReportDto.setLastUpdatedDate(rsSCM.getString("LAST_UPDATE_DATE"));
				consumptionReportDto.setLastUpdatedBy(rsSCM.getString("LAST_UPDATED_BY"));
				consumptionReportDto.setLoginId(rsSCM.getString("DIST_ID"));
				
				// Connect DPDTH get DistributorCode On distId
				if(consumptionReportDto.getLoginId() != null)
				{
					distLoginId = Integer.parseInt(consumptionReportDto.getLoginId());
					psDTH = conDTH.prepareStatement(DBQueries.GET_DISTRIBUTOR_CODE);
					psDTH.setInt(1, distLoginId);
					rsDTH = psDTH.executeQuery();
					
					if(rsDTH.next())
					{
						distributorCode = rsDTH.getString("LOGIN_NAME");					
					}
					consumptionReportDto.setDistributorCode(distributorCode);
				}
				psDTH.clearParameters();
				psDTH=null;
				rsDTH=null;
				//End
				
				consumptionReportList.add(consumptionReportDto);
			}// End while
		}catch(SQLException sqle)
		{
			sqle.printStackTrace();
			throw new DAOException("SQLException: " + sqle.getMessage());
		}catch(Exception e)
		{
			e.printStackTrace();
			throw new DAOException("Exception: " + e.getMessage());
		}finally
		{
			try {
				DBConnectionManager.releaseResources(psDTH, rsDTH);
				DBConnectionManager.releaseResources(psSCM, rsSCM);
			} catch (Exception e) {
				e.printStackTrace();
				logger.error("Error Occured in ReportDAOImpl "+e.getMessage());
			}
		}
				
		return consumptionReportList;
	}
	
	
	public List<DPReverseLogisticReportDTO> getPoReportExcel(int distId, String fromDate, String toDate, String circleId, String tsmId) throws DAOException 
	{
		PreparedStatement ps1 = null;
		ResultSet rs1 = null;
		
		String preQuery="";
		
		List<DPReverseLogisticReportDTO> revLogReportList = new ArrayList<DPReverseLogisticReportDTO>();
		DPReverseLogisticReportDTO dpReverseLogisticReportDTO = null ;
		try 
		{
			if(circleId.equals("0"))
			{
				if(tsmId.equals("0"))
				{
					if(distId == 0)
					{
						preQuery = " AND ACCOUNT_LEVEL = 6";
					}
					else
					{
						preQuery = " AND ACCOUNT_LEVEL = 6 and ACCOUNT_ID="+distId;
					}
				}
				else
				{
					if(distId == 0)
					{
						preQuery = " AND ACCOUNT_LEVEL = 6 and PARENT_ACCOUNT = "+Integer.parseInt(tsmId);
					}
					else
					{
						preQuery = " AND ACCOUNT_LEVEL = 6 and PARENT_ACCOUNT = "+Integer.parseInt(tsmId) +" and ACCOUNT_ID="+distId+" ";
					}
				}
			}
			else
			{
				if(tsmId.equals("0"))
				{
					if(distId == 0)
					{
						preQuery = " AND ACCOUNT_LEVEL = 6 and VAD.CIRCLE_ID = "+ Integer.parseInt(circleId) +"";
					}
					else
					{
						preQuery = " AND ACCOUNT_LEVEL = 6 and ACCOUNT_ID="+distId+" and VAD.CIRCLE_ID = "+Integer.parseInt(circleId)+" " ;
					}
				}
				else
				{
					if(distId == 0)
					{
						preQuery = " AND ACCOUNT_LEVEL = 6 and PARENT_ACCOUNT = "+Integer.parseInt(tsmId)+" and VAD.CIRCLE_ID = "+Integer.parseInt(circleId)+" " ;
					}
					else
					{
						preQuery = " AND ACCOUNT_LEVEL = 6 and PARENT_ACCOUNT = "+Integer.parseInt(tsmId)+" and ACCOUNT_ID="+distId+" and VAD.CIRCLE_ID = "+Integer.parseInt(circleId)+" " ;
					}
				}
			}
			String sql=DBQueries.GET_PO_REPORT;
			sql = sql + preQuery+ " WITH UR ";
			ps1 = connection.prepareStatement(sql);
			ps1.setDate(1, Utility.getSqlDateFromString(fromDate.trim(), "MM/dd/yyyy"));
			ps1.setDate(2, Utility.getSqlDateFromString(toDate.trim(), "MM/dd/yyyy"));
			rs1 = (ResultSet)ps1.executeQuery();
			String strUnit = null;
				
			while(rs1.next())
			{
		
				dpReverseLogisticReportDTO = new DPReverseLogisticReportDTO() ;
			 
				dpReverseLogisticReportDTO.setName(rs1.getString("DIST_NAME"));
				dpReverseLogisticReportDTO.setLoginName(rs1.getString("LOGIN_NAME"));
				dpReverseLogisticReportDTO.setCircleName(rs1.getString("CIRCLE_NAME"));
				dpReverseLogisticReportDTO.setProductName(rs1.getString("PRODUCT_NAME"));
				dpReverseLogisticReportDTO.setPrDate(rs1.getString("PR_DATE"));
				dpReverseLogisticReportDTO.setPrNo(rs1.getString("PR_NO"));
				dpReverseLogisticReportDTO.setPoNo(rs1.getString("PO_NO"));
				dpReverseLogisticReportDTO.setPoDate(rs1.getString("PO_DATE"));
				dpReverseLogisticReportDTO.setPoStatus(rs1.getString("PO_STATUS"));
				dpReverseLogisticReportDTO.setPoAccDate(rs1.getString("PO_ACC_DATE"));
				dpReverseLogisticReportDTO.setInvoiceNo(rs1.getString("INV_NO"));
				dpReverseLogisticReportDTO.setInvoiceDate(rs1.getString("INVOICE_DATE"));
				dpReverseLogisticReportDTO.setDcNo(rs1.getString("DC_NO"));
				dpReverseLogisticReportDTO.setDcDate(rs1.getString("DC_DATE"));
				dpReverseLogisticReportDTO.setDdNo(rs1.getString("DD_CHEQUE_NO"));
				dpReverseLogisticReportDTO.setDdDate(rs1.getString("DD_CHEQUE_DATE"));
				dpReverseLogisticReportDTO.setRaisedQty(rs1.getString("RAISED_QTY"));
				dpReverseLogisticReportDTO.setPoQty(rs1.getString("PO_QTY"));
				dpReverseLogisticReportDTO.setInvoiceQty(rs1.getString("INV_QTY"));
				strUnit = rs1.getString("UNIT");
				
				if(strUnit.equals(Constants.PRODUCT_UNIT_R))
				{
					dpReverseLogisticReportDTO.setUnit(Constants.PRODUCT_UNIT_RUPEES);
				}
				
				else if(strUnit.equals(Constants.PRODUCT_UNIT_Z))
				{
					dpReverseLogisticReportDTO.setUnit(Constants.PRODUCT_UNIT_NONE);
				}
				
				else if(strUnit.equals(Constants.PRODUCT_UNIT_R))
				{
					dpReverseLogisticReportDTO.setUnit(Constants.PRODUCT_UNIT_RUPEES);
				}
				
				else
				{
					dpReverseLogisticReportDTO.setUnit("");
				}
				//unit dpReverseLogisticReportDTO.setUnit();
				dpReverseLogisticReportDTO.setPrStatus(rs1.getString("PR_STATUS"));	
				dpReverseLogisticReportDTO.setRemarks(rs1.getString("REMARKS"));
			 
			 
			 revLogReportList.add(dpReverseLogisticReportDTO);
		
			}
		}
		catch(SQLException sqle){
			sqle.printStackTrace();
			throw new DAOException("SQLException: " + sqle.getMessage());
		}catch(Exception e){
			e.printStackTrace();
			throw new DAOException("Exception: " + e.getMessage());
		}finally{
			try {
				DBConnectionManager.releaseResources(ps1, rs1);
				
			} catch (Exception e) {
				e.printStackTrace();
				logger.error("Error Occured in ReportDAOImpl "+e.getMessage());
			}
		}
		return revLogReportList;

	
	}

	public List<DPReverseLogisticReportDTO> getInHandQtyReportExcel(int distId, String circleId, String tsmId, String productId) throws DAOException {
		PreparedStatement ps1 = null;
		ResultSet rs1 = null;
		PreparedStatement ps2 = null;
		ResultSet rs2 = null;
		String preQuery="";
		
		List<DPReverseLogisticReportDTO> revLogReportList = new ArrayList<DPReverseLogisticReportDTO>();
		DPReverseLogisticReportDTO dpReverseLogisticReportDTO = null ;
		try 
		{
			ps2 = 	connection.prepareStatement(DBQueries.GET_LAST_PO_DETAILS);
			
			if(circleId.equals("0"))
			{
				if(tsmId.equals("0"))
				{
					if(distId == 0)
					{
						preQuery = " AND VAD.ACCOUNT_LEVEL = 6";
					}
					else
					{
						preQuery = " AND VAD.ACCOUNT_LEVEL = 6 and VAD.ACCOUNT_ID="+distId;
					}
				}
				else
				{
					if(distId == 0)
					{
						preQuery = " AND VAD.ACCOUNT_LEVEL = 6 and VAD.PARENT_ACCOUNT = "+Integer.parseInt(tsmId);
					}
					else
					{
						preQuery = " AND VAD.ACCOUNT_LEVEL = 6 and VAD.PARENT_ACCOUNT = "+Integer.parseInt(tsmId) +" and VAD.ACCOUNT_ID="+distId+" ";
					}
				}
			}
			else
			{
				if(tsmId.equals("0"))
				{
					if(distId == 0)
					{
						preQuery = " AND VAD.ACCOUNT_LEVEL = 6 and VAD.CIRCLE_ID = "+ Integer.parseInt(circleId) +"";
					}
					else
					{
						preQuery = " AND VAD.ACCOUNT_LEVEL = 6 and VAD.ACCOUNT_ID="+distId+" and VAD.CIRCLE_ID = "+Integer.parseInt(circleId)+" " ;
					}
				}
				else
				{
					if(distId == 0)
					{
						preQuery = " AND VAD.ACCOUNT_LEVEL = 6 and VAD.PARENT_ACCOUNT = "+Integer.parseInt(tsmId)+" and VAD.CIRCLE_ID = "+Integer.parseInt(circleId)+" " ;
					}
					else
					{
						preQuery = " AND VAD.ACCOUNT_LEVEL = 6 and VAD.PARENT_ACCOUNT = "+Integer.parseInt(tsmId)+" and VAD.ACCOUNT_ID="+distId+" and VAD.CIRCLE_ID = "+Integer.parseInt(circleId)+" " ;
					}
				}
			}
			
			/*if(!productId.equals("0")){
				preQuery += " AND a.PRODUCT_ID="+ Integer.parseInt(productId);
			}*/
			preQuery += " AND INT(a.INHAND_QTY) != (INT(a.DP_QTY) + INT(a.DP_OPEN_STOCK))" ;
	int pr_no=0;
	String sql=DBQueries.GET_EXCEPTION_PO;  //Updated by Shilpa khanna DBQueries.GET_LAST_PO;
	sql = sql + preQuery +" group by b.PR_DIST_ID,CIRCLE_NAME,LOGIN_NAME,VAD.DISTRIBUTOR_LOCATOR ,VAD.ACCOUNT_NAME,VAD1.ACCOUNT_NAME,a.PR_NO ORDER BY PR_DIST_ID WITH UR ";
	
	logger.info("Generated Query ====== "+sql);
	ps1 = 	connection.prepareStatement(sql);
	rs1 = ps1.executeQuery();
	while(rs1.next())
	{
		pr_no = rs1.getInt("PR_NO");
		ps2.setInt(1, pr_no);
		rs2 = ps2.executeQuery();

		while(rs2.next())
		{
			 dpReverseLogisticReportDTO = new DPReverseLogisticReportDTO() ;
			 
			 dpReverseLogisticReportDTO.setLoginName(rs1.getString("LOGIN_NAME"));
			 dpReverseLogisticReportDTO.setCircle(rs1.getString("CIRCLE_NAME"));
			 dpReverseLogisticReportDTO.setDistributor_locator_code(rs1.getString("DISTRIBUTOR_LOCATOR"));
			 dpReverseLogisticReportDTO.setAccount_name(rs1.getString("ACCOUNT_NAME"));
			 dpReverseLogisticReportDTO.setTsmName(rs1.getString("TSM_ACCOUNT_NAME"));
			 dpReverseLogisticReportDTO.setProduct_name(rs2.getString("PRODUCT_NAME"));
			 dpReverseLogisticReportDTO.setNon_ser_qty(rs2.getString("DP_OPEN_STOCK"));
			 dpReverseLogisticReportDTO.setSer_qty(rs2.getString("DP_QTY"));
			 dpReverseLogisticReportDTO.setPhysical_stock(rs2.getString("RAISED_QTY"));
			 dpReverseLogisticReportDTO.setLast_pr_number(pr_no+"");
			 dpReverseLogisticReportDTO.setLast_pr_date(rs2.getString("PO_DATE"));
			 dpReverseLogisticReportDTO.setIn_hand_qty(rs2.getString("INHAND_QTY"));
			 dpReverseLogisticReportDTO.setTotal_dp_qty( rs2.getInt("DP_OPEN_STOCK") + rs2.getInt("DP_QTY"));
			 dpReverseLogisticReportDTO.setDifference(( rs2.getInt("DP_OPEN_STOCK") + rs2.getInt("DP_QTY"))- rs2.getInt("INHAND_QTY"));
			 revLogReportList.add(dpReverseLogisticReportDTO);
		}
		DBConnectionManager.releaseResources(null, rs2);
	}
		}catch(SQLException sqle){
			sqle.printStackTrace();
			throw new DAOException("SQLException: " + sqle.getMessage());
		}catch(Exception e){
			e.printStackTrace();
			throw new DAOException("Exception: " + e.getMessage());
		}finally{
			try {
				DBConnectionManager.releaseResources(ps1, rs1);
				DBConnectionManager.releaseResources(ps2, rs2);
			} catch (Exception e) {
				e.printStackTrace();
				logger.error("Error Occured in ReportDAOImpl "+e.getMessage());
			}
		}
		return revLogReportList;
	}

	public List<DPReverseLogisticReportFormBean> getRevLogDistAccountDetails(int roleId, int circleId, long accountID) throws DAOException 
	{
		Connection con = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		List<DPReverseLogisticReportFormBean> revLogList = new ArrayList<DPReverseLogisticReportFormBean>();
		try 
		{
			con = DBConnectionManager.getDBConnection(); // db2
			
			// Fetch level base
			int intTSMLevel = Integer.parseInt(Constants.ACC_LEVEL_TSM);
			
//			For Distributor login
			if(roleId==Constants.ACC_LEVEL_DIST)
			{
				ps = con.prepareStatement(DBQueries.GET_ACCOUNT_DETAILS_DIST);
				ps.setLong(1, roleId);
			}
			
//			For TSM login
			else if(roleId == intTSMLevel)
			{
				ps = con.prepareStatement(DBQueries.GET_ACCOUNT_DETAILS_REV_LOG_DIST);
				ps.setLong(1, accountID);
			}
			
			rs = ps.executeQuery();
			
			DPReverseLogisticReportFormBean statusReportBean = null;
			while(rs.next())
			{
				statusReportBean = new DPReverseLogisticReportFormBean();
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
	
}

