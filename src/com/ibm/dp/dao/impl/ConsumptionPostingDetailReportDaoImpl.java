package com.ibm.dp.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

import com.ibm.dp.beans.ConsumptionPostingDetailReportBeans;
import com.ibm.dp.beans.DPReverseLogisticReportFormBean;
import com.ibm.dp.beans.StockSummaryReportBean;
import com.ibm.dp.common.DBQueries;
import com.ibm.dp.dao.ConsumptionPostingDetailReportDao;
import com.ibm.dp.dto.ConsumptionPostingDetailReportDto;
import com.ibm.dp.dto.DistributorDTO;
import com.ibm.dp.dto.StockSummaryReportDto;
import com.ibm.utilities.Utility;
import com.ibm.virtualization.recharge.dao.rdbms.BaseDaoRdbms;
import com.ibm.virtualization.recharge.db.DBConnectionManager;
import com.ibm.virtualization.recharge.exception.DAOException;

public class ConsumptionPostingDetailReportDaoImpl  extends BaseDaoRdbms  implements ConsumptionPostingDetailReportDao {

	
	public static final String SQL_ALL_DISTS 			= DBQueries.GET_ALL_DISTRIBUTOR_LIST_REPORT;
	public  String SQL_REPORT_LIST_ALL_DISTS 			= DBQueries.GET_REPORT_LIST_ALL_DIST;
	public  String SQL_REPORT_LIST		= DBQueries.GET_REPORT_LIST;
	private Logger logger = Logger.getLogger(ConsumptionPostingDetailReportDaoImpl.class.getName());
	public ConsumptionPostingDetailReportDaoImpl(Connection con) {
		super(con);
	}
	
	
	public List<DistributorDTO> getAllDistList(String strLoginId,String strCircleId) throws DAOException {
		logger.info("in HierarchyTransferDaoImpl -> getAllDistList()  lngCrBy=="+strLoginId);
		List<DistributorDTO> distListDto = new ArrayList<DistributorDTO>();
		PreparedStatement pstmt = null;
		ResultSet rset			= null;
		DistributorDTO distDto=null;
		Connection con = null;
		try
		{
			con = DBConnectionManager.getDBConnection(); // db2
			if(strCircleId.equals("0")){
				pstmt = con.prepareStatement(SQL_ALL_DISTS);
				pstmt.setString(1, "6");
			}else{
				pstmt = con.prepareStatement(DBQueries.GET_CIRCLE_DISTRIBUTOR_LIST_REPORT);
				pstmt.setString(1, "6");
				pstmt.setString(2, strCircleId);
			}
			
			
			rset = pstmt.executeQuery();
			
			distListDto = new ArrayList<DistributorDTO>();
			Integer count=0;
			while(rset.next())
			{
				count++;
				distDto = new DistributorDTO();
				distDto.setAccountId(rset.getString("account_id"));
				distDto.setAccountName(rset.getString("account_name"));
				distListDto.add(distDto);
			}
			logger.info("in HierarchyTransferDaoImpl -> getAllDistList()  Query ends here ==");
			
		}catch(SQLException sqle){
			sqle.printStackTrace();
			throw new DAOException("SQLException: " + sqle.getMessage());
		}catch(Exception e){
			e.printStackTrace();
			throw new DAOException("Exception: " + e.getMessage());
		}finally{
			try {
				if (rset != null)
					rset.close();
				if (pstmt != null)
					pstmt.close();
				if (con != null) {
					con.close();
				}
			} catch (Exception e) {
				e.printStackTrace();
				logger.error("Error Occured in ReportDAOImpl "+e.getMessage());
			}
		}
		return distListDto;
		
	}
	
	public List<ConsumptionPostingDetailReportDto> getReportExcel(int distId , String fromDate,String toDate, String circleId,int selectedCircleId,String strTSMId) throws DAOException
	{
		//Connection con = null;
		PreparedStatement ps1 = null;
		ResultSet rs1 = null;
		Connection con = null;
		
		
		List<ConsumptionPostingDetailReportDto> reportList = new ArrayList<ConsumptionPostingDetailReportDto>();
		ConsumptionPostingDetailReportDto reportDto = null ;
				
		try 
		{
			con = DBConnectionManager.getDBConnection(); // db2
			if(distId ==0){
				logger.info("In if condition in getReportExcel consumption dao impl circle == "+circleId);
				
					if(selectedCircleId!=0){
						if(strTSMId.equals("0")){
							SQL_REPORT_LIST_ALL_DISTS += " AND ACC.CIRCLE_ID=? WITH UR ";
						}else{
							SQL_REPORT_LIST_ALL_DISTS += " AND ACC.CIRCLE_ID=? AND ACC.PARENT_ACCOUNT=? WITH UR ";
						}
						
					}else{
						if(strTSMId.equals("0")){
							SQL_REPORT_LIST_ALL_DISTS += " WITH UR ";  
						}else{
							SQL_REPORT_LIST_ALL_DISTS += " AND ACC.PARENT_ACCOUNT=? WITH UR ";
						}
						
					}
				
				
				logger.info("In if condition in getReportExcel consumption dao impl SQL_REPORT_LIST_ALL_DISTS == "+SQL_REPORT_LIST_ALL_DISTS);
				
				ps1 = 	con.prepareStatement(SQL_REPORT_LIST_ALL_DISTS);
				ps1.setString(1, fromDate);
				ps1.setString(2, toDate);
				if(selectedCircleId!=0 && strTSMId.equals("0")){
					ps1.setString(3, String.valueOf(selectedCircleId));
				}
				if(selectedCircleId!=0 && !strTSMId.equals("0")){
					ps1.setString(3, String.valueOf(selectedCircleId));
					ps1.setString(4, strTSMId);
				}
				if(selectedCircleId==0 && !strTSMId.equals("0")){
					ps1.setString(3, strTSMId);
				}
				
			}else{
				logger.info("In else condition in getReportExcel consumption dao impl circle == "+circleId);
				
				if(!circleId.equals("0")){
					SQL_REPORT_LIST += " AND ACC.CIRCLE_ID=? WITH UR ";
				}else{
					SQL_REPORT_LIST += " WITH UR ";
				}
				logger.info("In if condition in getReportExcel consumption dao impl SQL_REPORT_LIST == "+SQL_REPORT_LIST);
				ps1 = 	con.prepareStatement(SQL_REPORT_LIST);
				ps1.setString(1, String.valueOf(distId));
				ps1.setString(2, fromDate);
				ps1.setString(3, toDate);
				if(!circleId.equals("0")){
					ps1.setString(4, circleId);
				}
				
			}
			rs1=ps1.executeQuery();
			reportList = new ArrayList<ConsumptionPostingDetailReportDto>();
			while(rs1.next())
			{
				reportDto = new ConsumptionPostingDetailReportDto();
				reportDto.setSerialNo(rs1.getString("SERIAL_NO"));
				logger.info("rs1.getString(ASSIGN_DATE) == "+rs1.getString("ASSIGN_DATE"));
				String[] strArrayDat = rs1.getString("ASSIGN_DATE").split("-");
				String strDate = strArrayDat[2].split(" ")[0];
				String strMonth = strArrayDat[1];
				String strYear = strArrayDat[0];
				String strFullDate  =strDate+"/"+strMonth+"/"+strYear;
				logger.info("strDate == "+strFullDate);
				reportDto.setAssignedDate(strFullDate);
				reportDto.setItemCode(rs1.getString("ITEM_CODE"));
				reportDto.setDistributorLoginName(rs1.getString("DIST_LOGIN_NAME"));
				reportDto.setDistributorName(rs1.getString("DIST_NAME"));
				reportDto.setFseDetail(rs1.getString("FSE_NAME"));
				reportDto.setRetailerCode(rs1.getString("RETAILER_NAME"));
				reportDto.setCustomerId(rs1.getString("CUSTOMER_ID"));
				reportDto.setProductName(rs1.getString("PRODUCT_NAME"));
				reportDto.setCircleName(rs1.getString("CIRCLE_NAME"));
				reportList.add(reportDto);
			}
			
			
			
			
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
				if (con != null) {
					con.close();
				}
			} catch (Exception e) {
				e.printStackTrace();
				logger.error("Error Occured in ReportDAOImpl "+e.getMessage());
			}
		}
		return reportList;
	}
	public List getRevLogTsmAccountDetails(int levelId) throws DAOException {

		Connection con = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		List<ConsumptionPostingDetailReportBeans> revLogList = new ArrayList<ConsumptionPostingDetailReportBeans>();
		try 
		{
			con = DBConnectionManager.getDBConnection(); // db2
			
			
			System.out.println("levelId--"+levelId);
			// Fetch level base
			if(levelId == 0)
			{
				ps = con.prepareStatement(DBQueries.GET_ACCOUNT_DETAILS_REV_LOG_TSM_ALL);
				ps.setInt(1, 5);
			}
			else
			{
				ps = con.prepareStatement(DBQueries.GET_ACCOUNT_DETAILS_REV_LOG_TSM);
				ps.setInt(1, 5);
				ps.setInt(2, levelId);
			}
			
			rs = ps.executeQuery();
			
			ConsumptionPostingDetailReportBeans statusReportBean = null;
			while(rs.next())
			{
				statusReportBean = new ConsumptionPostingDetailReportBeans();
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
		List<ConsumptionPostingDetailReportBeans> revLogList = new ArrayList<ConsumptionPostingDetailReportBeans>();
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
			
			ConsumptionPostingDetailReportBeans statusReportBean = null;
			while(rs.next())
			{
				statusReportBean = new ConsumptionPostingDetailReportBeans();
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
