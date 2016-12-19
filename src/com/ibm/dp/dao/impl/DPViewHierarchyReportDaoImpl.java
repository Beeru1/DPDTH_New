package com.ibm.dp.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

import com.ibm.dp.beans.DPViewHierarchyFormBean;
import com.ibm.dp.common.DBQueries;
import com.ibm.dp.dao.DPViewHierarchyReportDao;
import com.ibm.dp.dto.DPViewHierarchyDTO;
import com.ibm.virtualization.recharge.common.Utility;
import com.ibm.virtualization.recharge.dao.rdbms.BaseDaoRdbms;
import com.ibm.virtualization.recharge.db.DBConnectionManager;
import com.ibm.virtualization.recharge.exception.DAOException;

public class DPViewHierarchyReportDaoImpl extends BaseDaoRdbms implements DPViewHierarchyReportDao {
	private Logger logger = Logger.getLogger(DPViewHierarchyReportDaoImpl.class.getName());
	public DPViewHierarchyReportDaoImpl(Connection con) {
		super(con);
	}
	public List getViewHierarchyDistAccountDetails(int levelId , int circleId) throws DAOException
	{
		Connection con = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		List<DPViewHierarchyDTO> viewHierarchyList = new ArrayList<DPViewHierarchyDTO>();
		DPViewHierarchyDTO objdoViewHierarchyDTO = null;
		try 
		{
			con = DBConnectionManager.getDBConnection(); // db2
			
			if(levelId != 0 && circleId != 0)
			{
				ps = con.prepareStatement(DBQueries.GET_ACCOUNT_DETAILS_VIEW_HIERARCHY_DIST);
				ps.setInt(1, levelId);
			}
			if(levelId != 0 && circleId == 0)
			{
				ps = con.prepareStatement(DBQueries.GET_ACCOUNT_DETAILS_VIEW_HIERARCHY_DIST);
				ps.setInt(1, levelId);
			}
			
			if(levelId == 0 && circleId == 0)
			{
				ps = con.prepareStatement(DBQueries.GET_ACCOUNT_DETAILS_VIEW_HIERARCHY_DIST_ALL);
				ps.setInt(1, 6);
			}
			
			if(levelId == 0 && circleId != 0)
			{
				ps = con.prepareStatement(DBQueries.GET_ACCOUNT_DETAILS_VIEW_HIERARCHY_TSM);
				ps.setInt(1, 6);
				ps.setInt(2, circleId);
			}
			rs = ps.executeQuery();
			 
			DPViewHierarchyFormBean statusReportBean = null;
			while(rs.next())
			{
				objdoViewHierarchyDTO = new DPViewHierarchyDTO();
				objdoViewHierarchyDTO.setLogin_name(rs.getString("LOGIN_NAME"));
				objdoViewHierarchyDTO.setAccount_name(rs.getString("ACCOUNT_NAME"));
				objdoViewHierarchyDTO.setContact_name(rs.getString("CONTACT_NAME"));
				objdoViewHierarchyDTO.setOracle_code(rs.getString("DISTRIBUTOR_LOCATOR"));
				objdoViewHierarchyDTO.setLapu_number(rs.getString("LAPU_MOBILE_NO"));
				objdoViewHierarchyDTO.setCircle_name(rs.getString("CIRCLE_NAME"));
				objdoViewHierarchyDTO.setAddress(rs.getString("ACCOUNT_ADDRESS"));
				objdoViewHierarchyDTO.setCity_name(rs.getString("CITY_NAME"));
				objdoViewHierarchyDTO.setPin_code(rs.getString("PIN_NUMBER"));
				objdoViewHierarchyDTO.setBotree_code(rs.getString("EXT_DIST_ID"));
				objdoViewHierarchyDTO.setEmail_id(rs.getString("EMAIL"));
				objdoViewHierarchyDTO.setMobile_number(rs.getString("MOBILE_NUMBER"));
				objdoViewHierarchyDTO.setParent_name(rs.getString("PARENT_NAME"));
				objdoViewHierarchyDTO.setParent_login_name(rs.getString("PARENT_LOGIN"));
				objdoViewHierarchyDTO.setAccount_id(rs.getString("ACCOUNT_ID"));
				
				viewHierarchyList.add(objdoViewHierarchyDTO);
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
		
		return viewHierarchyList;
	}
	
	public List getviewhierarchyTsmAccountDetails(int levelId) throws DAOException {

		Connection con = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		List<DPViewHierarchyFormBean> revLogList = new ArrayList<DPViewHierarchyFormBean>();
		try 
		{
			con = DBConnectionManager.getDBConnection(); // db2
			
			
			System.out.println("levelId--"+levelId);
			// Fetch level base
			
			if(levelId == 0)
			{
				ps = con.prepareStatement(DBQueries.GET_ACCOUNT_DETAILS_VIEW_HIERARCHY_TSM_ALL);
				ps.setInt(1, 5);			
			}
			else
			{
				ps = con.prepareStatement(DBQueries.GET_ACCOUNT_DETAILS_VIEW_HIERARCHY_TSM_NOT_ALL);
				ps.setInt(1, 5);			
				ps.setInt(2, levelId);				
			}
			
			rs = ps.executeQuery();
			
			DPViewHierarchyFormBean statusReportBean = null;
			while(rs.next())
			{
				statusReportBean = new DPViewHierarchyFormBean();
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
	public List getviewhierarchyFromTsmAccountDetails(int levelId) throws DAOException {

		Connection con = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		List<DPViewHierarchyFormBean> revLogList = new ArrayList<DPViewHierarchyFormBean>();
		try 
		{
			con = DBConnectionManager.getDBConnection(); // db2
			
			
			System.out.println("levelId--"+levelId);
			// Fetch level base
			
			if(levelId == 0)
			{
				ps = con.prepareStatement(DBQueries.GET_ACCOUNT_DETAILS_VIEW_HIERARCHY_FROM_TSM_ALL);
				ps.setInt(1, 5);			
					
			}
			else
			{
				ps = con.prepareStatement(DBQueries.GET_ACCOUNT_DETAILS_VIEW_HIERARCHY_FROM_TSM_NOT_ALL);
				ps.setInt(1, 5);			
				ps.setInt(2, levelId);					
			}
			rs = ps.executeQuery();
			
			DPViewHierarchyFormBean statusReportBean = null;
			while(rs.next())
			{
				statusReportBean = new DPViewHierarchyFormBean();
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
	
	public List getviewhierarchyTsmDistLogin(int distId) throws DAOException {

		Connection con = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		List<DPViewHierarchyFormBean> revLogList = new ArrayList<DPViewHierarchyFormBean>();
		try 
		{
			con = DBConnectionManager.getDBConnection(); // db2
			
			
			System.out.println("distId--"+distId);
			// Fetch level base
				ps = con.prepareStatement(DBQueries.GET_VIEW_HIERARCHY_DISABLE_TSM_DIST);
				ps.setInt(1, distId);
				rs = ps.executeQuery();
			
				DPViewHierarchyFormBean statusReportBean = null;
			while(rs.next())
			{
				statusReportBean = new DPViewHierarchyFormBean();
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
	public List getHierarchyAll(String[] distList) throws DAOException {

		PreparedStatement ps = null;
		ResultSet rs = null;
		List<DPViewHierarchyDTO> viewHierarchyList = new ArrayList<DPViewHierarchyDTO>();
		
		try 
		{ 
				ps = connection.prepareStatement(DBQueries.GET_HIERARCHY);
				for(int i=0; i< distList.length;i++)
				{
					ps.setString(1, distList[i]);
					rs = ps.executeQuery();
					DPViewHierarchyDTO objdpViewHierarchyDTO = null;
					
					while(rs.next())
					{
								objdpViewHierarchyDTO = new DPViewHierarchyDTO();
								
								objdpViewHierarchyDTO.setLogin_name(rs.getString("DIST_LOGIN_NAME"));
								objdpViewHierarchyDTO.setAccount_name(rs.getString("DIST_ACCOUNT_NAME"));
								objdpViewHierarchyDTO.setLapu_number(rs.getString("DIST_LAPU_MOBILE_NO"));
								objdpViewHierarchyDTO.setFse(rs.getString("FSE_ACCOUNT_NAME"));
								objdpViewHierarchyDTO.setFse_lapu_number(rs.getString("FSE_LAPU_MOBILE_NO"));
								objdpViewHierarchyDTO.setRetailer(rs.getString("RET_ACCOUNT_NAME"));
								objdpViewHierarchyDTO.setRetailer_lapu_number(rs.getString("RET_LAPU_MOBILE_NO"));
								
								viewHierarchyList.add(objdpViewHierarchyDTO);
					}
					DBConnectionManager.releaseResources(null, rs);
				}
				DBConnectionManager.releaseResources(ps, rs);
		}
		catch(SQLException sqle){
			sqle.printStackTrace();
			throw new DAOException("SQLException: " + sqle.getMessage());
		}
		catch(Exception e){
			e.printStackTrace();
			throw new DAOException("Exception: " + e.getMessage());
		}finally{
			try {
				DBConnectionManager.releaseResources(ps, rs);
			} catch (Exception e) {
				e.printStackTrace();
				logger.error("Error Occured in ReportDAOImpl "+e.getMessage());
			}
		}
		
		return viewHierarchyList;

	}
	
	// Added by parnika for TSM list of same transaction type 
	
	public List getSameTransactionTsmAccountDetails(int levelId, long loginId) throws DAOException {

		Connection con = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		List<DPViewHierarchyFormBean> revLogList = new ArrayList<DPViewHierarchyFormBean>();
		try 
		{
			con = DBConnectionManager.getDBConnection(); // db2
			
			
			System.out.println("levelId--"+levelId);
			// Fetch level base
			if(levelId == 0)
			{
				ps = con.prepareStatement(DBQueries.GET_TSM_SAME_TRANSACTION_TYPE_ALL);
				ps.setInt(1, 5);
				ps.setLong(2, loginId);
			}
			else
			{
				ps = con.prepareStatement(DBQueries.GET_TSM_SAME_TRANSACTION_TYPE_NOT_ALL);
				ps.setInt(1, 5);
				ps.setInt(2, levelId);
				ps.setLong(3,loginId);
			}
			
			rs = ps.executeQuery();
			
			DPViewHierarchyFormBean statusReportBean = null;
			while(rs.next())
			{
				statusReportBean = new DPViewHierarchyFormBean();
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
				logger.error("Error Occured in getSameTransactionTsmAccountDetails() "+e.getMessage());
			}
		}
		
		return revLogList;

	}
	// Added by parnika for from TSM list of Deposit Type only 
	public List getDepositTypeFromTsmAccountDetails(int levelId) throws DAOException {

		Connection con = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		List<DPViewHierarchyFormBean> revLogList = new ArrayList<DPViewHierarchyFormBean>();
		try 
		{
			con = DBConnectionManager.getDBConnection(); // db2
			
			
			System.out.println("levelId--"+levelId);
			// Fetch level base
			
			/* Changes by Parnika for making it Deposit Type */
			if(levelId == 0)
			{
				ps = con.prepareStatement(DBQueries.GET_ACCOUNT_DETAILS_DEPOSIT_FROM_TSM_ALL);
				ps.setInt(1, 5);			// Role 
				ps.setInt(2, 2);			// Transaction Type as Deposit
			}
			else
			{
				ps = con.prepareStatement(DBQueries.GET_ACCOUNT_DETAILS_DEPOSIT_FROM_TSM_NOT_ALL);
				ps.setInt(1, 5);			// Role
				ps.setInt(2, levelId);		// Circle
				ps.setInt(3, 2);			// Transaction Type as Deposit
			}
			/* End of Changes by Parnika for making it Deposit Type */
			rs = ps.executeQuery();
			
			DPViewHierarchyFormBean statusReportBean = null;
			while(rs.next())
			{
				statusReportBean = new DPViewHierarchyFormBean();
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
	// Added by parnika for TSM list of Deposit Type only
	
	public List getDepositTypeTsmAccountDetails(int levelId) throws DAOException {

		Connection con = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		List<DPViewHierarchyFormBean> revLogList = new ArrayList<DPViewHierarchyFormBean>();
		try 
		{
			con = DBConnectionManager.getDBConnection(); // db2
			
			
			System.out.println("levelId--"+levelId);
			// Fetch level base
			/* Changes by Parnika for making it Deposit Type */
			if(levelId == 0)
			{
				ps = con.prepareStatement(DBQueries.GET_ACCOUNT_DETAILS_DEPOSIT_TSM_ALL);
				ps.setInt(1, 5);			// Role
				ps.setInt(2, 2);			// Transaction Type as Deposit
			}
			else
			{
				ps = con.prepareStatement(DBQueries.GET_ACCOUNT_DETAILS_DEPOSIT_TSM_NOT_ALL);
				ps.setInt(1, 5);			// Role
				ps.setInt(2, levelId);		// Circle
				ps.setInt(3, 2);			// Transaction Type as Deposit
			}
			/* End of Changes by Parnika for making it Deposit Type */
			
			rs = ps.executeQuery();
			
			DPViewHierarchyFormBean statusReportBean = null;
			while(rs.next())
			{
				statusReportBean = new DPViewHierarchyFormBean();
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
	
	
	// Added by parnika for TSM list according to business category
	public List getviewhierarchyFromTsmAccountDetails(int levelId, int busCat) throws DAOException {

		Connection con = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		List<DPViewHierarchyFormBean> revLogList = new ArrayList<DPViewHierarchyFormBean>();
		try 
		{
			con = DBConnectionManager.getDBConnection(); // db2
			
			
			System.out.println("levelId--"+levelId);
			// Fetch level base
			
			/* Changes by Parnika for making it Deposit Type */
			if(levelId == 0)
			{
				ps = con.prepareStatement(DBQueries.GET_ACCOUNT_DETAILS_VIEW_HIERARCHY_FROM_TSM_ALL);
				ps.setInt(1, 5);			// Role 
				ps.setInt(2, 2);			// Transaction Type as Deposit
			}
			else
			{
				ps = con.prepareStatement(DBQueries.GET_ACCOUNT_DETAILS_VIEW_HIERARCHY_FROM_TSM_NOT_ALL);
				ps.setInt(1, 5);			// Role
				ps.setInt(2, levelId);		// Circle
				ps.setInt(3, 2);			// Transaction Type as Deposit
			}
			/* End of Changes by Parnika for making it Deposit Type */
			rs = ps.executeQuery();
			
			DPViewHierarchyFormBean statusReportBean = null;
			while(rs.next())
			{
				statusReportBean = new DPViewHierarchyFormBean();
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
	
	// Added by Parnika for getting from TSM based on business category
	
	public List getCategoryWiseFromTsmAccountDetails(int levelId, int busCat) throws DAOException {

		Connection con = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		List<DPViewHierarchyFormBean> revLogList = new ArrayList<DPViewHierarchyFormBean>();
		try 
		{
			con = DBConnectionManager.getDBConnection(); // db2
			
			
			System.out.println("levelId--"+levelId);
			// Fetch level base
			
			if(levelId == 0)
			{
				ps = con.prepareStatement(DBQueries.GET_ACCOUNT_DETAILS_CATEGORY_WISE_FROM_TSM_ALL);
				ps.setInt(1, 5);
				ps.setInt(2, busCat);
					
			}
			else
			{
				ps = con.prepareStatement(DBQueries.GET_ACCOUNT_DETAILS_CATEGORY_WISE_FROM_TSM_NOT_ALL);
				ps.setInt(1, 5);			
				ps.setInt(2, levelId);	
				ps.setInt(3, busCat);
			}
			rs = ps.executeQuery();
			
			DPViewHierarchyFormBean statusReportBean = null;
			while(rs.next())
			{
				statusReportBean = new DPViewHierarchyFormBean();
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
	// Added by Parnika for getting TSM list based on Business Category
	public List getCategoryWiseTsmAccountDetails(int levelId, int busCat) throws DAOException {

		Connection con = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		List<DPViewHierarchyFormBean> revLogList = new ArrayList<DPViewHierarchyFormBean>();
		try 
		{
			con = DBConnectionManager.getDBConnection(); // db2
			
			
			System.out.println("levelId--"+levelId);
			// Fetch level base
			
			if(levelId == 0)
			{
				ps = con.prepareStatement(DBQueries.GET_ACCOUNT_DETAILS_CATEGORY_WISE_TSM_ALL);
				ps.setInt(1, 5);				// Role
				ps.setInt(2, busCat);			// Business category
			}
			else
			{
				ps = con.prepareStatement(DBQueries.GET_ACCOUNT_DETAILS_CATEGORY_WISE_TSM_NOT_ALL);
				ps.setInt(1, 5);				// Role
				ps.setInt(2, levelId);			// Circle
				ps.setInt(3, busCat);			// Business category
			}
			
			rs = ps.executeQuery();
			
			DPViewHierarchyFormBean statusReportBean = null;
			while(rs.next())
			{
				statusReportBean = new DPViewHierarchyFormBean();
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
	
	// End of changes by Parnika
	
}
