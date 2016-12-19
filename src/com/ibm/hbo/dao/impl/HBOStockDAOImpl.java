package com.ibm.hbo.dao.impl;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.ArrayList;
import java.util.StringTokenizer;

import org.apache.log4j.Logger;

import com.ibm.dp.common.Constants;
import com.ibm.dp.cpedp.DPCPEDBConnection;
import com.ibm.dp.dao.STBFlushOutDao;
import com.ibm.dp.dao.impl.STBFlushOutDaoImpl;
import com.ibm.dp.dto.DpDcReverseStockInventory;
import com.ibm.hbo.common.DBQueries;
import com.ibm.hbo.common.HBOConstants;
import com.ibm.hbo.common.HBOUser;
import com.ibm.hbo.dao.HBOStockDAO;
import com.ibm.hbo.dto.DistStockDTO;
import com.ibm.hbo.dto.HBOProjectionBulkUploadDTO;
import com.ibm.hbo.dto.HBOStockDTO;
import com.ibm.hbo.exception.DAOException;
import com.ibm.hbo.exception.HBOException;
import com.ibm.hbo.forms.HBOBundleStockFormBean;
import com.ibm.hbo.forms.HBOMarkDamagedFormBean;
import com.ibm.utilities.DcCreation;
import com.ibm.utilities.ErrorCodes;
import com.ibm.utilities.Utility;
import com.ibm.virtualization.recharge.common.ResourceReader;
import com.ibm.virtualization.recharge.dao.rdbms.BaseDaoRdbms;
import com.ibm.virtualization.recharge.db.DBConnectionManager;
import com.ibm.utilities.PropertyReader;
import com.ibm.virtualization.recharge.common.ResourceReader;

/**
 * @author
 * 
 * To change the template for this generated type comment go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
public class HBOStockDAOImpl extends BaseDaoRdbms implements HBOStockDAO {

	private static Logger logger = Logger.getLogger(HBOStockDAOImpl.class);

	private static final String CLASSNAME = "HBOStockDAOImpl";

	/**
	 * This method is used to get available stock in hand for the circle admin
	 * 
	 * @param userId.
	 * @param warehouseId.
	 * @param avlStock.
	 * @return TBD
	 * @throws DaoException
	 *             In case of an error
	 */

	public HBOStockDAOImpl(Connection con) {
		super(con);
	}

	public int findValues(String userId, String warehouseId)
			throws DAOException {
		int warehouseID = Integer.parseInt(warehouseId);
		Connection con = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		try {
			con = DBConnectionManager.getDBConnection(); // db2
			ps = con.prepareStatement(DBQueries.simStockInhand);
			ps.setInt(1, warehouseID);
			rs = ps.executeQuery();
			if (rs.next()) {
				return rs.getInt(1);
			}
		} catch (SQLException e) {
			e.printStackTrace();
			logger.error("SQL Exception occured in findValues."
					+ "Exception Message: " + e.getMessage());
			throw new DAOException("SQLException: " + e.getMessage(), e);
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("Exception occured in findValues."
					+ "Exception Message: " + e.getMessage());
			throw new DAOException("Exception: " + e.getMessage(), e);
		} finally {
			try {
				if (rs != null)
					rs.close();
				if (ps != null)
					ps.close();
				if (con != null) {
					con.close();
				}
			} catch (Exception e) {
			}
		}
		return 0;
	}

	/**
	 * TBD
	 * 
	 * @param req_batch_no
	 * @param warehouseId.
	 * @param cond.
	 * @return TBD
	 * @throws DaoException
	 *             In case of an error
	 */
	public ArrayList findByPrimaryKey(String req_batch_no, String cond)
			throws DAOException {
		ArrayList arrBatchdetails = new ArrayList();
		Connection con = null;
		PreparedStatement ps = null;
		ResultSet rs = null;

		final StringBuffer SQL_SELECT = new StringBuffer("");
		if (cond.equalsIgnoreCase("BatchDetails")) {
			SQL_SELECT.append(DBQueries.assignedSimBatchDetails);
		} else if (cond.equalsIgnoreCase(HBOConstants.BUNDLED_DETAILS)) {
			SQL_SELECT.append(DBQueries.viewBundleBatchDetails);
		}
		try {
			con = DBConnectionManager.getDBConnection();
			ps = con.prepareStatement(SQL_SELECT.toString());
			ps.setString(1, req_batch_no);
			rs = ps.executeQuery();
			if (cond.equalsIgnoreCase("BatchDetails")) {
				arrBatchdetails = fetchResult(rs);
			}
			if (cond.equalsIgnoreCase(HBOConstants.BUNDLED_DETAILS)) {
				arrBatchdetails = fetchSingleResult(rs, cond);
			}
			return arrBatchdetails;
		} catch (SQLException e) {
			e.printStackTrace();
			logger.error("SQL Exception occured while inserting."
					+ "Exception Message: " + e.getMessage());
			throw new DAOException("SQLException: " + e.getMessage(), e);
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("Exception occured while inserting."
					+ "Exception Message: " + e.getMessage());
			throw new DAOException("Exception: " + e.getMessage(), e);
		} finally {
			try {
				if (rs != null)
					rs.close();
				if (ps != null)
					ps.close();
				if (con != null) {
					con.close();
				}
			} catch (Exception e) {
			}
		}

	}

	/**
	 * TBD
	 * 
	 * @param req_batch_no
	 * @param warehouseId.
	 * @param cond.
	 * @return TBD
	 * @throws DaoException
	 *             In case of an error
	 */
	public ArrayList findByPrimaryKey(String UserID, String warehouseId,
			String Cond) throws DAOException {
		ArrayList arrBatchList = new ArrayList();
		Connection con = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		StringBuffer sql = new StringBuffer("");
		if (Cond.equalsIgnoreCase("U")) {
			sql.append(DBQueries.unverifiedSimStockList);
		} else if (Cond.equalsIgnoreCase("V")) {
			sql.append(DBQueries.verifiedSimStockList);
		} else if (Cond.equalsIgnoreCase("A")) {
			sql.append(DBQueries.assignedSimBatch);
		}
		try {
			con = DBConnectionManager.getDBConnection();
			logger.info("SQL:" + sql.toString());
			ps = con.prepareStatement(sql.toString());
			ps.setString(1, warehouseId);
			rs = ps.executeQuery();
			arrBatchList = fetchSingleResult(rs, Cond);
			return arrBatchList;
		} catch (SQLException e) {
			e.printStackTrace();
			logger.error("SQL Exception occured while inserting."
					+ "Exception Message: " + e.getMessage());
			throw new DAOException("SQLException: " + e.getMessage(), e);
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("Exception occured while inserting."
					+ "Exception Message: " + e.getMessage());
			throw new DAOException("Exception: " + e.getMessage(), e);
		} finally {
			try {
				if (rs != null)
					rs.close();
				if (ps != null)
					ps.close();
				if (con != null) {
					con.close();
				}
			} catch (Exception e) {
			}
		}
	}

	/**
	 * TBD
	 * 
	 * @param rs
	 * @param cond.
	 * @return TBD
	 * @throws SQLException
	 *             In case of an error
	 */
	private ArrayList fetchSingleResult(ResultSet rs, String Cond)
			throws SQLException {
		HBOStockDTO tempDTO = null;
		ArrayList arrData = new ArrayList();
		int i = 0;
		String lstatus = null;

		while (rs.next()) {
			// Verified & UnVerified Batch List
			if ((Cond.equalsIgnoreCase("U") || Cond.equalsIgnoreCase("V"))) {
				tempDTO = new HBOStockDTO();
				tempDTO.setBatch_no(rs.getString("BATCH_NO"));
				tempDTO.setAssignedSimQty(rs.getInt("assigned_qty"));
				tempDTO.setCreated_dt(rs.getString("CREATED_DT"));
				tempDTO.setUpdated_dt(rs.getString("UPDATED_DT"));
				tempDTO.setStatus(rs.getString("status"));
				lstatus = rs.getString("status").trim();
				if (lstatus.equalsIgnoreCase("A")) {
					tempDTO.setStatus("Accepted");
				}
				if (lstatus.equalsIgnoreCase("R")) {
					tempDTO.setStatus("Rejected");
				}
				arrData.add(tempDTO);
			} else if (Cond.equalsIgnoreCase("A")) { // Assigned Sim Batch
														// List
				tempDTO = new HBOStockDTO();
				tempDTO.setBatch_no(rs.getString("BATCH_NO"));
				tempDTO.setWarehouseTo_Name(rs.getString("Account_Name"));
				tempDTO.setAssignedSimQty(rs.getInt("assigned_qty"));
				tempDTO.setCreated_dt(rs.getString("CREATED_DT").substring(0,
						10));
				// tempDTO.setStatus(rs.getString("status"));
				lstatus = rs.getString("status").trim();
				if (lstatus.equalsIgnoreCase("A")) {
					tempDTO.setStatus("Accepted");
				}
				if (lstatus.equalsIgnoreCase("R")) {
					tempDTO.setStatus("Rejected");
				}
				if (lstatus.equalsIgnoreCase("I")) {
					tempDTO.setStatus("In-Transit");
				}
				arrData.add(tempDTO);
			} else if (Cond.equalsIgnoreCase("Bundled")) { // Bundled List
				tempDTO = new HBOStockDTO();
				tempDTO.setRequestId(rs.getInt("REQUEST_ID"));
				tempDTO.setBundledQty(rs.getInt("BUNDLED_QTY"));
				tempDTO.setModelCode(rs.getString("MODEL_CODE"));
				tempDTO.setModelName(rs.getString("MODEL_NAME"));
				// tempDTO.setCompanyName(rs.getString("COMPANY_NAME"));
				tempDTO.setCircleName(rs.getString("CIRCLE_NAME"));
				tempDTO.setBundledDt(rs.getString("BUNDLED_DT")
						.substring(0, 10));
				arrData.add(tempDTO);
			} else if (Cond.equalsIgnoreCase(HBOConstants.BUNDLED_DETAILS)) { // Bundled
				// List
				tempDTO = new HBOStockDTO();
				tempDTO.setImeiNo(rs.getString("IMEI_NO"));
				tempDTO.setMsidnNo(rs.getString("MSIDN_NO"));
				tempDTO.setSimNo(rs.getString("SIM_NO"));
				tempDTO.setDamageFlag(rs.getString("DAMAGE_FLAG"));
				tempDTO.setStockStatus(rs.getString("STATUS"));
				tempDTO.setWarehouseId(rs.getInt("LAST_WAREHOUSE_ID"));
				arrData.add(tempDTO);
			}
		} // end-of-while
		return arrData;
	}

	/**
	 * TBD
	 * 
	 * @param rs
	 * @return TBD
	 * @throws SQLException
	 *             In case of an error
	 */
	private ArrayList fetchResult(ResultSet rs) throws SQLException {
		HBOStockDTO ViewSimStockDTO = null;
		ArrayList arrData = new ArrayList();
		int i = 0;
		while (rs.next()) {
			i++;
			ViewSimStockDTO = new HBOStockDTO();
			ViewSimStockDTO.setSimNo(rs.getString("imei_sim_no"));
			ViewSimStockDTO.setMsidnNo(rs.getString("msidn_no"));
			arrData.add(ViewSimStockDTO);
		}
		return arrData;
	}

	/**
	 * TBD
	 * 
	 * @param sessionUserBean
	 * @param AssSimStkDto
	 * @return null
	 * @throws DAOException
	 *             In case of an error
	 */
	public void InsertAssignedSimStock(HBOUser obj, HBOStockDTO AssSimStkDto)
			throws DAOException {

		Connection con = null;
		CallableStatement cs = null;
		ResultSet rs = null;

		logger.info("Insert Assigned Sim Stock Data ");
		try {
			con = DBConnectionManager.getDBConnection();
			cs = con.prepareCall("{call PROC_DP_Assign_SimStock(?,?,?,?)}");
			cs.setInt(1, obj.getId().intValue());
			cs.setInt(2, Integer.parseInt(obj.getWarehouseID()));
			cs.setInt(3, AssSimStkDto.getWarehouseId());
			cs.setInt(4, AssSimStkDto.getAssignedSimQty());
			boolean k = cs.execute();

			logger.info("Record updated in Assign SIm Stock " + k);
			con.commit();
		} catch (SQLException e) {
			e.printStackTrace();
			logger.error("SQL Exception occured while inserting."
					+ "Exception Message: " + e.getMessage());
			throw new DAOException("SQLException: " + e.getMessage(), e);
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("Exception occured while inserting."
					+ "Exception Message: " + e.getMessage());
			throw new DAOException("Exception: " + e.getMessage(), e);
		} finally {
			try {
				if (cs != null)
					cs.close();
				if (con != null) {
					con.close();
				}
			} catch (Exception e) {
			}
		}

	}

	/**
	 * TBD - This method is used in Accept/Reject Sim Stock
	 * 
	 * @param sessionUserBean
	 * @param simStkDto
	 * @return null
	 * @throws DAOException
	 *             In case of an error
	 */
	public void UpdateSimStock(HBOUser obj, HBOStockDTO simStkDto)
			throws DAOException {
		Connection con = null;
		CallableStatement cs = null;

		String[] arrStatus;
		String[] arrBatch;
		try {
			con = DBConnectionManager.getDBConnection();
			arrBatch = simStkDto.getArrBatch();
			arrStatus = simStkDto.getArrStatus();

			for (int i = 0; i < arrBatch.length; i++) {

				logger.info("obj.getId().intValue()-----"
						+ obj.getId().intValue());
				logger.info("arrStatus[i])--------" + arrStatus[i]);
				logger.info("Integer.parseInt(obj.getWarehouseID()------"
						+ Integer.parseInt(obj.getWarehouseID()));
				logger.info("arrBatch[i]------" + arrBatch[i]);
				// cs = con.prepareCall("{call
				// PROC_DP_Verify_SimStock(?,?,?,?)}");
				cs = con
						.prepareCall("{call PROC_DP_Verify_SimStock(?,?,?,?)}");

				cs.setInt(1, obj.getId().intValue());
				cs.setString(2, arrStatus[i]);
				cs.setInt(3, Integer.parseInt(obj.getWarehouseID()));
				cs.setString(4, arrBatch[i]);
				boolean k = cs.execute();
				logger.info("Record updated in Verify SIm Stock " + k);
			}
			con.commit();
		} catch (SQLException e) {
			e.printStackTrace();
			logger.error("SQL Exception occured while inserting."
					+ "Exception Message: " + e.getMessage());
			throw new DAOException("SQLException: " + e.getMessage(), e);
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("Exception occured while inserting."
					+ "Exception Message: " + e.getMessage());
			throw new DAOException("Exception: " + e.getMessage(), e);
		} finally {
			try {
				if (cs != null)
					cs.close();
				if (con != null) {
					con.close();
				}
			} catch (Exception e) {
			}
		}

	}

	/**
	 * TBD
	 * 
	 * @param userId
	 * @param formBean
	 * @param master
	 * @param condition
	 * @return null
	 * @throws DAOException
	 *             In case of an error
	 */

	public void bundleStock(HBOUser userbean, Object formBean, String master,
			String condition) throws DAOException {
		HBOBundleStockFormBean bundleStockFormBean = (HBOBundleStockFormBean) formBean;
		Connection con = null;
		ResultSet rs = null;
		CallableStatement cs = null;
		String role = "";
		try {
			ArrayList roleList = userbean.getRoleList();
			con = DBConnectionManager.getDBConnection(); // DB2 database
															// connection
			cs = con
					.prepareCall("{call  PROC_DP_BUNDLE_PRODSTOCK_NEW(?,?,?, ?,?,?,?)}");
			cs.setInt(1, userbean.getId().intValue());
			cs.setInt(2, Integer.parseInt(userbean.getWarehouseID()));
			cs.setInt(3, Integer.parseInt(bundleStockFormBean.getProduct()));
			cs.setInt(4, Integer.parseInt(bundleStockFormBean.getCircle()));
			cs.setInt(5, bundleStockFormBean.getBundleQty());
			cs.setString(6, null);
			if (roleList.contains(HBOConstants.ROLE_NATIONALDIST)) {
				role = HBOConstants.ROLE_NATIONALDIST;
			} else if (roleList.contains(HBOConstants.ROLE_LOCALDIST)) {
				role = HBOConstants.ROLE_LOCALDIST;
			}
			cs.setString(7, role);
			boolean k = cs.execute();

			logger.info("Record updated in Bundle  Stock " + k);
			con.commit();
		} catch (SQLException e) {
			e.printStackTrace();
			logger.error("SQL Exception occured while inserting."
					+ "Exception Message: " + e.getMessage());
			throw new DAOException("SQLException: " + e.getMessage(), e);
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("Exception occured while inserting."
					+ "Exception Message: " + e.getMessage());
			throw new DAOException("Exception: " + e.getMessage(), e);
		} finally {
			try {
				if (cs != null)
					cs.close();
				if (con != null) {
					con.close();
				}
			} catch (Exception e) {
			}
		}

	}

	// public void bundleStock(String userId,Object formBean,String
	// master,String condition) throws DAOException{
	// HBOBundleStockFormBean bundleStockFormBean = null;
	// Connection con = null;
	// PreparedStatement ps = null;
	// ResultSet rs = null;
	// StringBuffer sql = new StringBuffer("");
	//
	// if (master.equalsIgnoreCase("BundleStock")) {
	// String pcode = bundleStockFormBean.getProduct();
	// String circleId=bundleStockFormBean.getCircle();
	// int avlSimStock=bundleStockFormBean.getAvlSimStock();
	// int avlUnBundleStock=bundleStockFormBean.getAvlPairedStock();
	// int qtyToBeBundled=bundleStockFormBean.getBundleQty();
	// int reqNo=bundleStockFormBean.getRequestNo();
	// sql.append("INSERT INTO PRODUCT_SIM(IMEI_NO, SIM_NO, MSIDN_NO,
	// REQUEST_ID) VALUES()");
	// /* PLEASE CHECK SOME SQL MISSING HERE - SANJAY */
	// }
	// try {
	// con = DBConnection.getDBConnection();
	// ps = con.prepareStatement(sql.toString());
	// if (master.equalsIgnoreCase("BundleStock")) {
	//							
	// int param = 1;
	// /*
	// ps.setString(param++,userId); // Login ID Modified
	// */
	//					
	// }
	// ps.executeUpdate();
	// con.commit();
	// } catch (SQLException e) {
	// e.printStackTrace();
	// logger.error(
	// "SQL Exception occured while inserting." + "Exception Message: " +
	// e.getMessage());
	// throw new DAOException("SQLException: " + e.getMessage(), e);
	// } catch (Exception e) {
	// e.printStackTrace();
	// logger.error(
	// "Exception occured while inserting." + "Exception Message: " +
	// e.getMessage());
	// throw new DAOException("Exception: " + e.getMessage(), e);
	// } finally {
	// try {
	// if (ps != null)
	// ps.close();
	// if (con != null) {
	// con.close();
	// }
	// } catch (Exception e) {
	// }
	// }
	// }

	/**
	 * TBD
	 * 
	 * @param hboUserBean
	 * @param condition
	 * @return ArrayList
	 * @throws DAOException
	 *             In case of an error
	 */

	public ArrayList findByPrimaryKey(HBOUser hboUserBean, String cond)
			throws DAOException {
		ArrayList arrBatchList = new ArrayList();
		Connection con = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		StringBuffer sql = new StringBuffer("");
		ArrayList arrBundledStock = new ArrayList();
		sql.append(DBQueries.viewBundleStock);
		try {
			con = DBConnectionManager.getDBConnection(); // DB2 database
															// connection
			logger.info("SQL:" + sql.toString());
			ps = con.prepareStatement(sql.toString());
			ps.setInt(1, hboUserBean.getId().intValue());
			rs = ps.executeQuery();
			arrBundledStock = fetchSingleResult(rs, cond);
			return arrBundledStock;
		} catch (SQLException e) {
			e.printStackTrace();
			logger.error("SQL Exception occured while inserting."
					+ "Exception Message: " + e.getMessage());
			throw new DAOException("SQLException: " + e.getMessage(), e);
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("Exception occured while inserting."
					+ "Exception Message: " + e.getMessage());
			throw new DAOException("Exception: " + e.getMessage(), e);
		} finally {
			try {
				if (rs != null)
					rs.close();
				if (ps != null)
					ps.close();
				if (con != null) {
					con.close();
				}
			} catch (Exception e) {
			}
		}
	}

	/**
	 * TBD
	 * 
	 * @param AssSimStkDto
	 * @return null
	 * @throws DAOException
	 *             In case of an error
	 */

	/*
	 * public void InsertAssignedSimStock(HBOStockDTO AssSimStkDto) throws
	 * DAOException {
	 * 
	 * Connection con = null; PreparedStatement ps = null; ResultSet rs = null;
	 * StringBuffer sql = new StringBuffer(""); try { con =
	 * DBConnection.getDBConnection(); con.setAutoCommit(false); // Get the
	 * batch no int paramCountBN = 1; sql .append("select
	 * WAREHOUSE_ID||'_'||?||'_'||bh_seq.nextval AS BATCH_NO"); sql.append("
	 * from user_master where user_login_id = ?"); ps =
	 * con.prepareStatement(sql.toString()); ps.setInt(paramCountBN++,
	 * AssSimStkDto.getWarehouseId()); ps.setString(paramCountBN++,
	 * AssSimStkDto.getCreated_by()); rs = ps.executeQuery(); String batchNo =
	 * ""; if (rs.next()) { batchNo = rs.getString("BATCH_NO"); } // Get the
	 * Warehouse Id for the LoggedIn User sql = new StringBuffer( "select
	 * warehouse_id from USER_MASTER where USER_LOGIN_ID = ?"); ps =
	 * con.prepareStatement(sql.toString()); int paramCountW = 1; int
	 * warehouseID = 0; ps.setString(paramCountW++,
	 * AssSimStkDto.getCreated_by()); rs = ps.executeQuery(); if (rs.next()) {
	 * warehouseID = rs.getInt("Warehouse_ID"); }
	 * AssSimStkDto.setLoginuser_warehouse_id(warehouseID); // INSERT ASSIGNED
	 * SIMSTOCK BATCH RECORD IN BATCH_HEADER----------- sql = new StringBuffer(
	 * "INSERT INTO BATCH_HEADER (BATCH_NO, WAREHOUSE_TO,
	 * WAREHOUSE_FROM,ASSIGNED_QTY, CREATED_BY, CREATED_DT,STATUS,
	 * PRODUCT_TYPE)"); sql .append(" select
	 * ?,?,WAREHOUSE_ID,?,?,sysdate,'T','S' from user_master"); sql.append("
	 * where user_login_id = ?"); ps = con.prepareStatement(sql.toString()); int
	 * paramCount = 1; AssSimStkDto.setBatch_no(batchNo);
	 * ps.setString(paramCount++, AssSimStkDto.getBatch_no());
	 * ps.setInt(paramCount++, AssSimStkDto.getWarehouseId());
	 * ps.setInt(paramCount++, AssSimStkDto.getAssignedSimQty());
	 * ps.setString(paramCount++, AssSimStkDto.getCreated_by());
	 * ps.setString(paramCount++, AssSimStkDto.getCreated_by());
	 * ps.executeUpdate(); // Update SIM Stock sql = new StringBuffer("update
	 * sim_stock "); sql .append("set Status = 'I',Last_Batch_No =
	 * ?,Last_Warehouse = ? ,updated_dt =sysdate,updated_by = ? where sim_no in(
	 * "); sql.append("Select SIM_NO From "); sql.append("(select
	 * SIM_NO,Created_DT from sim_stock "); sql .append("where DAMAGE_FLAG ='N'
	 * and BUNDLED ='N' and Last_Warehouse = ? and STATUS='S' ");
	 * sql.append("order by created_dt) S Where rownum < ?) ");
	 * 
	 * con.prepareStatement(sql.toString()); int paramCountUSS = 1;
	 * 
	 * ps.setString(paramCountUSS++, AssSimStkDto.getBatch_no());
	 * ps.setInt(paramCountUSS++, AssSimStkDto.getWarehouseId());
	 * ps.setString(paramCountUSS++, AssSimStkDto.getUpdated_by()); ps
	 * .setInt(paramCountUSS++, AssSimStkDto .getLoginuser_warehouse_id());
	 * ps.setInt(paramCountUSS++, AssSimStkDto.getAssignedSimQty());
	 * ps.executeUpdate(); // Insert BAtch Details in BAtch_Details Table sql =
	 * new StringBuffer( "INSERT INTO BATCH_DETAILS
	 * (BATCH_NO,IMEI_SIM_NO,CREATED_BY,CREATED_DT)(select
	 * Last_Batch_No,SIM_NO,Created_By,sysdate from sim_stock where ");
	 * sql.append("Last_Batch_No = ?)"); ps =
	 * con.prepareStatement(sql.toString()); int paramCountBD = 1;
	 * ps.setString(paramCountBD++, AssSimStkDto.getBatch_no());
	 * ps.executeUpdate();
	 *  // Update Sim_Stock_Summary Table sql = new StringBuffer( "Update
	 * SIM_STOCK_SUMMARY Set SIM_STOCK_SUMMARY.UNBUNDLED_SIM_SIT =
	 * (UNBUNDLED_SIM_SIT + ?) Where "); sql.append("WAREHOUSE_ID = ?"); int
	 * paramCountSSS = 1; // ps.close(); ps =
	 * con.prepareStatement(sql.toString()); ps.setInt(paramCountSSS++,
	 * AssSimStkDto.getAssignedSimQty()); ps .setInt(paramCountSSS++,
	 * AssSimStkDto .getLoginuser_warehouse_id()); ps.executeUpdate();
	 * con.commit(); } catch (SQLException e) { e.printStackTrace();
	 * logger.error("SQL Exception occured while inserting." + "Exception
	 * Message: " + e.getMessage()); throw new DAOException("SQLException: " +
	 * e.getMessage(), e); } catch (Exception e) { e.printStackTrace();
	 * logger.error("Exception occured while inserting." + "Exception Message: " +
	 * e.getMessage()); throw new DAOException("Exception: " + e.getMessage(),
	 * e); } finally { try { if (rs != null) rs.close(); if (ps != null)
	 * ps.close(); if (con != null) { con.close(); } } catch (Exception e) { } } }
	 */

	/**
	 * TBD - Do we Require this method ???/
	 * 
	 * @param simStkDto
	 * @return null
	 * @throws DAOException
	 *             In case of an error
	 */
	public void UpdateSimStock(HBOStockDTO simStkDto) {
		logger.info("Insert UpdateSimStock ");
		logger.info("UpdateSimStock..........");
		Connection con = null;
		PreparedStatement ps = null;
		ResultSet rs = null;

		// Update Batch_Header Status - Updated By- Updated Date
		StringBuffer SQL_UPDATE_BATCH_HEADER = new StringBuffer("");
		SQL_UPDATE_BATCH_HEADER
				.append(" Update BATCH_HEADER set  Status = ? , Updated_By =? Updated_DT = ? where  BATCH_NO = ?");

		// Update Sim_STock_Summary for the Circle Warehouse
		StringBuffer SQL_UPDATE_SIM_STOCK_SUMMARY = new StringBuffer("");

		// Update Sim_STock_Summary for the Central /Local warehouse

		// Update Sim_Stock Table Status - Last_Warehouse, Updated_By -
		// Updated_DT
		StringBuffer SQL_SIM_STOCK = new StringBuffer("");

	}

	/**
	 * TBD - Do we Require this method ???/
	 * 
	 * @param simStkDto
	 * @return null
	 * @throws DAOException
	 *             In case of an error
	 */

	public ArrayList getAssignStockDetails(int warehouseId, String condition)
			throws DAOException {
		Connection con = null;

		PreparedStatement ps = null;
		ResultSet rs = null;
		ArrayList stockList = new ArrayList();
		StringBuffer sql = new StringBuffer(DBQueries.assignedStock);
		try {
			con = DBConnectionManager.getDBConnection();
			if (condition.equalsIgnoreCase(HBOConstants.STOCK_IN_TRANSIT)) {
				sql.append(DBQueries.assignedStockTransit);
			} else if (condition
					.equalsIgnoreCase(HBOConstants.ACCEPTED_REJECTED_STOCK)) {
				sql.append(DBQueries.assignedStockAR);
			} else if (condition
					.equalsIgnoreCase(HBOConstants.ALL_ASSIGNED_STOCK)) {
				sql.append(DBQueries.assignedStockAll);
			}
			ps = con.prepareStatement(sql.toString());
			ps.setInt(1, warehouseId);
			rs = ps.executeQuery();
			while (rs.next()) {
				HBOStockDTO stockDTO = new HBOStockDTO();
				stockDTO.setBatch_no(rs.getString("BATCH_NO"));
				stockDTO.setWarehouseId(rs.getInt("WAREHOUSE_TO"));
				stockDTO.setWarehouse_from(rs.getInt("WAREHOUSE_FROM"));
				stockDTO.setWarehouseTo_Name(rs.getString("ACCOUNT_NAME"));
				stockDTO.setAssignedProdQty(rs.getInt("ASSIGNED_QTY"));
				stockDTO.setCreated_by(rs.getString("CREATED_BY"));
				stockDTO.setAssign_date(rs.getString("CREATED_DT"));
				stockDTO.setStatus(rs.getString("STATUS"));
				stockDTO.setUpdated_dt(rs.getString("UPDATED_DT"));
				stockDTO.setUpdated_by(rs.getString("UPDATED_BY"));
				stockDTO.setProduct_type(rs.getString("PRODUCT_TYPE"));
				stockDTO.setProduct_code(rs.getString("PRODUCT_CODE"));
				stockDTO.setBundled_flag(rs.getString("BUNDLED_FLAG"));
				stockList.add(stockDTO);
			}

		} catch (SQLException e) {
			e.printStackTrace();
			logger.error("SQL Exception occured while fetching."
					+ "Exception Message: " + e.getMessage());
			throw new DAOException("SQLException: " + e.getMessage(), e);
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("Exception occured while fetching."
					+ "Exception Message: " + e.getMessage());
			throw new DAOException("Exception: " + e.getMessage(), e);
		} finally {
			try {
				if (rs != null)
					rs.close();
				if (ps != null)
					ps.close();
				if (con != null) {
					con.close();
				}
			} catch (Exception e) {
			}
		}
		return stockList;
	}

	/**
	 * TBD -
	 * 
	 * @param batch_no
	 * @return ArrayList
	 * @throws DAOException
	 *             In case of an error
	 */
	public ArrayList getBatchDetailList(String batch_no) throws DAOException {

		Connection con = null;
		PreparedStatement ps = null;
		ResultSet rs = null;

		ArrayList batchDetailList = new ArrayList();
		// ArrayList damageFlagList = new ArrayList();
		StringBuffer sql = new StringBuffer(DBQueries.batchDetailsList);
		try {
			con = DBConnectionManager.getDBConnection();
			// connection
			ps = con.prepareStatement(sql.toString());
			ps.setString(1, batch_no);
			rs = ps.executeQuery();
			while (rs.next()) {

				HBOStockDTO stockDTO = new HBOStockDTO();
				stockDTO.setInvoice_no(rs.getString("INVOICE_NO"));
				stockDTO.setInvoice_date(rs.getString("INVOICE_DT"));
				stockDTO.setImeiNo(rs.getString("IMEI_SIM_NO"));
				stockDTO.setIssuance_date(rs.getString("ISSUANCE_DT"));
				stockDTO.setReceive_date(rs.getString("RECEIVING_DT"));
				stockDTO.setCompany_name(rs.getString("COMPANY_DESC"));
				stockDTO.setDamageFlag(rs.getString("Damage_Flag"));
				stockDTO.setModel_name(rs.getString("PRODUCT_NAME"));
				stockDTO.setSimNo(rs.getString("SIM_NO"));
				stockDTO.setBundle(rs.getString("Bundled"));
				stockDTO.setMsidnNo(rs.getString("MSIDN_NO"));
				stockDTO.setStockStatus(rs.getString("STATUS"));
				stockDTO.setWarehouseId(rs.getInt("LAST_WAREHOUSE_ID"));
				batchDetailList.add(stockDTO);

			}
		} catch (SQLException e) {
			e.printStackTrace();
			logger.error("SQL Exception occured while fetching batch details."
					+ "Exception Message: " + e.getMessage());
			throw new DAOException("SQLException: " + e.getMessage(), e);
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("Exception occured while fetching batch details."
					+ "Exception Message: " + e.getMessage());
			throw new DAOException("Exception: " + e.getMessage(), e);
		} finally {
			try {
				if (rs != null)
					rs.close();
				if (ps != null)
					ps.close();
				if (con != null) {
					con.close();
				}
			} catch (Exception e) {
			}
		}
		return batchDetailList;
	}

	/**
	 * TBD -
	 * 
	 * @param userBean
	 * @param prodStockDTO
	 * @return null
	 * @throws DAOException
	 *             In case of an error
	 */

	public void acceptRejectProdStock(HBOUser userBean, HBOStockDTO prodStockDTO)
			throws DAOException {
		// Connection con = null;
		PreparedStatement ps = null;
		PreparedStatement ps1 = null;
		PreparedStatement ps2 = null;
		PreparedStatement ps3 = null;
		PreparedStatement ps4 = null;
		PreparedStatement ps5 = null;
		PreparedStatement ps6 = null;
		PreparedStatement ps7 = null;
		PreparedStatement ps8 = null;
		PreparedStatement ps9 = null;
		PreparedStatement ps10 = null;
		PreparedStatement ps11 = null;
		ResultSet rs = null;
		ArrayList roleList = userBean.getRoleList();
		String[] arrBatch = prodStockDTO.getArrBatch();
		String[] arrStatus = prodStockDTO.getArrStatus();
		StringBuffer sql = new StringBuffer("");
		int length = arrBatch.length;
		Long warehouseId = userBean.getId();
		int userId = userBean.getId().intValue();
		try {
			// con = DBConnectionManager.getDBConnection();
			String circleSQL = "";
			String circleId = "";
			// String roleId = userBean.getActorId();
			sql = new StringBuffer(DBQueries.acceptReject_getCircle);
			ps = connection.prepareStatement(sql.toString());
			ps.setLong(1, userBean.getId());
			rs = ps.executeQuery();
			while (rs.next()) {
				circleId = rs.getString(HBOConstants.COL_CIRCLE_ID);
			}
			String flag = "";
			String productCode = "";
			Long warehouse_from = 0L;
			int assignedQty = 0;
			int recordExists = 0;
			for (int i = 0; i < length; i++) {
				sql = new StringBuffer("");
				sql.append(DBQueries.acceptReject_update);
				logger.info("arrStatus[i]:" + arrStatus[i]);
				logger
						.info("userBean.getUserLoginId():"
								+ userBean.getUserId());
				logger.info("arrBatch[i]:" + arrBatch[i]);
//				Added by Shilpa on 05-01-2012
				DBConnectionManager.releaseResources(ps1, null);
				//Ends here

				ps1 = connection.prepareStatement(sql.toString());
				ps1.setString(1, arrStatus[i]);
				ps1.setInt(2, userBean.getId().intValue());
				ps1.setString(3, arrBatch[i]);
				int status = ps1.executeUpdate();
				connection.commit();
				// Get the Bundle Flag. Based upon Sum needed to calculate BSIH
				// or UBSIH
				sql = new StringBuffer(DBQueries.acceptReject_getbundleFlag);
//				Added by Shilpa on 05-01-2012
				DBConnectionManager.releaseResources(ps2, rs);
				//Ends here

				ps2 = connection.prepareStatement(sql.toString());
				ps2.setString(1, arrBatch[i]);
				rs = ps2.executeQuery();
				while (rs.next()) {
					flag = rs.getString(HBOConstants.COL_BUNDLED_FLAG);
					warehouse_from = rs
							.getLong(HBOConstants.COL_WAREHOUSE_FROM);
					productCode = rs.getString(HBOConstants.COL_PRODUCT_CODE);
				}
				// Get Assigned Qty
				sql = new StringBuffer(DBQueries.acceptReject_assignedQty);
//				Added by Shilpa on 05-01-2012
				DBConnectionManager.releaseResources(ps3, rs);
				//Ends here

				ps3 = connection.prepareStatement(sql.toString());
				ps3.setString(1, arrBatch[i]);
				rs = ps3.executeQuery();
				while (rs.next()) {
					assignedQty = rs.getInt(HBOConstants.COL_ASSIGNED_QTY);
				}
				// In case of Acceptance
				if (arrStatus[i].equals(HBOConstants.STOCK_FLAG_ACCEPT)) {
					// Check if record exists for warehouseId in WAREHOUSE_STOCK
					sql = new StringBuffer(DBQueries.acceptReject_recordCheck);
//					Added by Shilpa on 05-01-2012
					DBConnectionManager.releaseResources(ps4, rs);
					//Ends here

					ps4 = connection.prepareStatement(sql.toString());
					ps4.setLong(1, warehouseId);
					ps4.setInt(2, Integer.parseInt(productCode));
					rs = ps4.executeQuery();
					while (rs.next()) {
						recordExists = rs.getInt(HBOConstants.COL_COUNT);
					}
					sql = new StringBuffer(DBQueries.acceptReject_updateReqProd);
//					Added by Shilpa on 05-01-2012
					DBConnectionManager.releaseResources(ps5, null);
					//Ends here

					ps5 = connection.prepareStatement(sql.toString());
					ps5.setLong(1, warehouseId);
					ps5.setInt(2, userId);
					ps5.setString(3, arrBatch[i]);
					ps5.executeUpdate();
					connection.commit();
					logger.info("recordExists:" + recordExists);
					if (flag.equals(HBOConstants.BUNDLE_FLAG)) { // Bundled
																	// ---CIRCLE
						circleSQL = DBQueries.acceptReject_circleCondition;
						if (recordExists > 0) {
							sql = new StringBuffer(
									DBQueries.acceptReject_updateWarehouseToStockBundled);
//							Added by Shilpa on 05-01-2012
							DBConnectionManager.releaseResources(ps6, null);
							//Ends here

							ps6 = connection.prepareStatement(sql.toString());
							ps6.setInt(1, assignedQty);
							ps6.setLong(2, warehouseId);
							ps6.setInt(3, Integer.parseInt(productCode));
						} else { // Insert
							sql = new StringBuffer(
									DBQueries.acceptReject_insertBundled);
//							Added by Shilpa on 05-01-2012
							DBConnectionManager.releaseResources(ps6, null);
							//Ends here

							ps6 = connection.prepareStatement(sql.toString());
							ps6.setLong(1, warehouseId);
							ps6.setInt(2, Integer.parseInt(productCode));
							ps6.setInt(3, assignedQty);
						}
						logger.info(sql.toString());
						ps6.executeUpdate();
						sql = new StringBuffer(
								DBQueries.acceptReject_updateWarehouseFromStockBundled);
						if (roleList.contains(HBOConstants.ROLE_LOCALDIST)) { // Local
																				// Warehouse
							sql.append(DBQueries.acceptReject_circleCondition);
//							Added by Shilpa on 05-01-2012
							DBConnectionManager.releaseResources(ps7, null);
							//Ends here

							ps7 = connection.prepareStatement(sql.toString());
							ps7.setInt(1, assignedQty);
							ps7.setLong(2, warehouse_from);
							ps7.setInt(3, Integer.parseInt(productCode));
							ps7.setInt(4, Integer.parseInt(circleId));
						} else {
//							Added by Shilpa on 05-01-2012
							DBConnectionManager.releaseResources(ps7, null);
							//Ends here

							ps7 = connection.prepareStatement(sql.toString());
							ps7.setInt(1, assignedQty);
							ps7.setLong(2, warehouse_from);
							ps7.setInt(3, Integer.parseInt(productCode));
						}
						ps7.executeUpdate();
					} else { // UnBundled
						if (recordExists > 0) {
							sql = new StringBuffer(
									DBQueries.acceptReject_updateWarehouseToStockUnbundled);
//							Added by Shilpa on 05-01-2012
							DBConnectionManager.releaseResources(ps8, null);
							//Ends here

							ps8 = connection.prepareStatement(sql.toString());
							ps8.setInt(1, assignedQty);
							ps8.setLong(2, warehouseId);
							ps8.setInt(3, Integer.parseInt(productCode));
						} else { // Insert
							sql = new StringBuffer(
									DBQueries.acceptReject_insertUnbundled);
//							Added by Shilpa on 05-01-2012
							DBConnectionManager.releaseResources(ps8, null);
							//Ends here

							ps8 = connection.prepareStatement(sql.toString());
							ps8.setLong(1, warehouseId);
							ps8.setInt(2, Integer.parseInt(productCode));
							ps8.setInt(3, assignedQty);
						}

						logger.info("SQL:" + sql.toString());
						ps8.executeUpdate();
						sql = new StringBuffer(
								DBQueries.acceptReject_updateWarehouseStock);
//						Added by Shilpa on 05-01-2012
						DBConnectionManager.releaseResources(ps9, null);
						//Ends here

						ps9 = connection.prepareStatement(sql.toString());
						ps9.setInt(1, assignedQty);
						ps9.setLong(2, warehouse_from);
						ps9.setInt(3, Integer.parseInt(productCode));
						sql.append(circleSQL);
						ps9.executeUpdate();
					}
					connection.commit();
				} else if (arrStatus[i].equals(HBOConstants.STOCK_FLAG_REJECT)) { // In
																					// case
																					// of
																					// Rejection
					sql = new StringBuffer(
							DBQueries.acceptReject_updateReqProdReject);
//					Added by Shilpa on 05-01-2012
					DBConnectionManager.releaseResources(ps10, null);
					//Ends here

					ps10 = connection.prepareStatement(sql.toString());
					ps10.setInt(1, userId);
					ps10.setString(2, arrBatch[i]);
					ps10.executeUpdate();
					if (flag.equals(HBOConstants.BUNDLE_FLAG)) { // Bundled
						sql = new StringBuffer(
								DBQueries.acceptReject_updateWarehouseStockReject);
						sql.append(circleSQL);
//						Added by Shilpa on 05-01-2012
						DBConnectionManager.releaseResources(ps11, null);
						//Ends here

						ps11 = connection.prepareStatement(sql.toString());
						ps11.setInt(1, assignedQty);
						ps11.setInt(2, assignedQty);
						ps11.setLong(3, warehouse_from);
						ps11.setString(4, productCode);
						ps11.executeUpdate();
					} else { // UnBundled
						circleSQL = DBQueries.acceptReject_circleCondNull; // changed
																			// by
						sql = new StringBuffer(
								DBQueries.acceptReject_updateWareStockRejectUnbundled);
						sql.append(circleSQL);
//						Added by Shilpa on 05-01-2012
						DBConnectionManager.releaseResources(ps11, null);
						//Ends here

						ps11 = connection.prepareStatement(sql.toString());
						ps11.setInt(1, assignedQty);
						ps11.setInt(2, assignedQty);
						ps11.setLong(3, warehouse_from);
						ps11.setString(4, productCode);
						ps11.executeUpdate();
					}
					connection.commit();
				}
			} // end-of-for
		} catch (SQLException e) {
			e.printStackTrace();
			try {
				connection.rollback();
			} catch (SQLException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			logger.error("SQL Exception occured while fetching batch details."
					+ "Exception Message: " + e.getMessage());
			throw new DAOException("SQLException: " + e.getMessage(), e);
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("Exception occured while fetching batch details."
					+ "Exception Message: " + e.getMessage());
			throw new DAOException("Exception: " + e.getMessage(), e);
		} finally {
			try {
				if (rs != null)
					rs.close();
				if (ps != null)
					ps.close();
				if (connection != null) {
					connection.close();
				}
			} catch (Exception e) {
			}
			DBConnectionManager.releaseResources(ps1, null);
			DBConnectionManager.releaseResources(ps2, null);
			DBConnectionManager.releaseResources(ps3, null);
			DBConnectionManager.releaseResources(ps4, null);
			DBConnectionManager.releaseResources(ps5, null);
			DBConnectionManager.releaseResources(ps6, null);
			DBConnectionManager.releaseResources(ps7, null);
			DBConnectionManager.releaseResources(ps8, null);
			DBConnectionManager.releaseResources(ps9, null);
			DBConnectionManager.releaseResources(ps10, null);
			DBConnectionManager.releaseResources(ps11, null);
			
		}
	}


	// Aditya

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.ibm.hbo.dao.HBOStockDAO#projectQty(java.lang.String,
	 *      java.lang.Object, java.lang.String, java.lang.String)
	 */
	public void projectQty(Object object) throws DAOException {
		Connection con = null;
		PreparedStatement ps = null;
		PreparedStatement ps1 = null;
		ResultSet rs = null;
		StringBuffer sql = null;
		HBOStockDTO record = (HBOStockDTO) object;
		int userId = record.getId().intValue();
		String circleCode = record.getCircle().trim();
		logger.info("circle>>>>>>>>" + record.getCircle());
		String prodCode = record.getProduct().trim();
		logger.info("record.getProduct()" + record.getProduct());
		String month = record.getMonth().trim();
		String year = record.getYear();
		int projectedQty = record.getQuantity();
		try {
			con = DBConnectionManager.getDBConnection();
			ps = con.prepareStatement(DBQueries.projectedQty);
			ps.setString(1, circleCode);
			ps.setString(2, prodCode);
			ps.setString(3, month);
			ps.setString(4, year);
			rs = ps.executeQuery();
			if (!rs.next()) {
				logger.info("Creating projection");
				sql = new StringBuffer(DBQueries.createProjection);
//				Added by Shilpa on 05-01-2012
				//DBConnectionManager.releaseResources(ps, null);
				//Ends here

				ps1 = con.prepareStatement(sql.toString());
				ps1.setString(1, circleCode);
				ps1.setString(2, prodCode);
				ps1.setString(3, month);
				ps1.setString(4, year);
				ps1.setInt(5, projectedQty);
				ps1.setInt(6, userId);
				ps1.setInt(7, userId);
				ps1.executeUpdate();
				con.commit();
			} else {
				logger.info("Updating projection");
				sql = new StringBuffer(DBQueries.updateProjection);
//				Added by Shilpa on 05-01-2012
				//DBConnectionManager.releaseResources(ps, null);
				//Ends here

				ps1 = con.prepareStatement(sql.toString());
				logger.info(sql.toString());
				ps1.setInt(1, projectedQty);
				ps1.setInt(2, userId);
				ps1.setString(3, circleCode);
				ps1.setString(4, prodCode);
				ps1.setString(5, month);
				ps1.setString(6, year);
				ps1.executeUpdate();
				con.commit();
			}
		} catch (SQLException e) {
			e.printStackTrace();
			logger.error("SQL Exception occured while inserting."
					+ "Exception Message: " + e.getMessage());
			throw new DAOException("SQLException: " + e.getMessage(), e);
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("Exception occured while inserting."
					+ "Exception Message: " + e.getMessage());
			throw new DAOException("Exception: " + e.getMessage(), e);
		} finally {
			DBConnectionManager.releaseResources(ps1, null);
			try {
				if (rs != null) {
					rs.close();
				}
				if (ps != null) {
					ps.close();
				}
				if (con != null) {
					con.close();
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}


	public int projectBulkQty(Object object) throws DAOException {
		String methodName = "projectBulkQty";
		int result = 0;
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rst = null;
		StringBuffer sql = new StringBuffer();
		HBOProjectionBulkUploadDTO upload = (HBOProjectionBulkUploadDTO) object;
		// int fileId =0;
		try {
			con = DBConnectionManager.getDBConnection();
			sql.append(DBQueries.projectionBulkUpload);
			pstmt = con.prepareStatement(sql.toString());
			pstmt.setString(1, upload.getFileName());
			pstmt.setInt(2, upload.getUploadedBy().intValue());
			pstmt.setString(3, upload.getCircle());
			pstmt.setString(4, upload.getMonth());
			pstmt.setString(5, upload.getYear());
			pstmt.setString(6, upload.getFilePath());
			result = pstmt.executeUpdate();
			con.commit();
		} catch (SQLException sqe) {
			logger.info("SQLException from insertData >> " + sqe);
			sqe.printStackTrace();
			HBOException HBOException = new HBOException(CLASSNAME, methodName,
					sqe.getMessage(), ErrorCodes.SQLEXCEPTION);
			sqe.printStackTrace();
			logger.error("SQL Exception :: " + HBOException);
			throw HBOException;
		} catch (Exception e) {
			logger.info("SQLException from insertData >> " + e);
			e.printStackTrace();
			HBOException HBOException = new HBOException(CLASSNAME, methodName,
					e.getMessage(), ErrorCodes.SYSTEMEXCEPTION);
			logger.error("System Exception Exception :: " + HBOException);
			throw HBOException;
		} finally {
			try {
				sql = null;
				try {
					if (rst != null)
						rst.close();
				} catch (SQLException sqle) {
				}
				try {
					if (pstmt != null)
						pstmt.close();
				} catch (SQLException sqle) {
				}
				con.close();
			} catch (Exception e) {
				logger.info("Exception in Finally from insertSDFData>> " + e);
				e.printStackTrace();
			}
		}
		return result;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.ibm.hbo.dao.HBOStockDAO#arrImeiList(java.lang.String,
	 *      java.lang.String, java.lang.String)
	 */
	public ArrayList arrImeiList(String productId,String wareHouseId, String extraCond,
			String cond) throws DAOException {
		StringBuffer sql = null;
		Connection con = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		HBOStockDTO stockDTO = new HBOStockDTO();
		ArrayList arrImeiList = new ArrayList();
		int warehouseId = Integer.parseInt(wareHouseId);
		String roleName="Airtel Distributor";
		boolean adFlag = false;
		try {
			
			con = DBConnectionManager.getDBConnection();
			sql = new StringBuffer(DBQueries.selectAccountDetails);
			ps = con.prepareStatement(sql.toString());
			ps.setInt(1, warehouseId);
			rs = ps.executeQuery();
			while(rs.next()){
				if(rs.getString("LEVEL_NAME").equalsIgnoreCase(roleName)){
					adFlag=true;
				}
			}
			if (cond.equalsIgnoreCase(HBOConstants.DIRECT_DAMAGE)) {
				if(adFlag)
					sql = new StringBuffer(DBQueries.markDamageDirectAD);
				else
					sql = new StringBuffer(DBQueries.markDamageDirect);
				
				DBConnectionManager.releaseResources(ps, rs);
				//Ends here
				ps = con.prepareStatement(sql.toString());
				ps.setString(1, extraCond);
				ps.setInt(2, warehouseId);
				ps.setInt(3, Integer.parseInt(productId));
				rs = ps.executeQuery();
			} 
//			else if (cond
//					.equalsIgnoreCase(HBOConstants.MARK_DAMAGE_IMEI_LIST)) {
//				sql = new StringBuffer();
//				ps = con.prepareStatement(sql.toString());
//				ps.setString(1, extraCond);
//			}
			
			while (rs.next()) {
				stockDTO = new HBOStockDTO();
				stockDTO.setImeiNo(rs.getString("IMEI_NO"));
				stockDTO.setStatus(rs.getString("DAMAGE_FLAG"));
				stockDTO.setSimnoList(rs.getString("SIM_NO"));
				stockDTO.setBundle(rs.getString("BUNDLED"));
				stockDTO.setStockStatus(rs.getString("STATUS"));
				stockDTO.setWarehouseId(rs.getInt("LAST_WAREHOUSE_ID"));
				arrImeiList.add(stockDTO);
			}

		} catch (SQLException e) {
			e.printStackTrace();
			logger.error("SQL Exception occured while inserting."
					+ "Exception Message: " + e.getMessage());
			throw new DAOException("SQLException: " + e.getMessage(), e);
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("Exception occured while inserting."
					+ "Exception Message: " + e.getMessage());
			throw new DAOException("Exception: " + e.getMessage(), e);
		} finally {
			try {
				if (rs != null)
					rs.close();
				if (ps != null)
					ps.close();
				if (con != null) {
					con.close();
				}
			} catch (Exception e) {
			}
		}
		return arrImeiList;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.ibm.hbo.dao.HBOStockDAO#arrNewImeiNo(java.lang.String,
	 *      java.lang.String, java.lang.String)
	 */
	public String newImeiNo(String imeiNo, String simNo, HBOUser hboUser)
			throws DAOException {
		StringBuffer sql = null;
		Connection con = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		CallableStatement cs = null;
		String newImeiNo = "";
		String productCode = "";
		int warehouseId = Integer.parseInt(hboUser.getWarehouseID());
		StringBuffer sql_IMEI = null;
		String bundleStatus = "";
		String role = "";
		int circleId = 0;
		ArrayList roleList = hboUser.getRoleList();
		sql = new StringBuffer(DBQueries.damageSelectProdCode);

		StringBuffer sqlbundle = new StringBuffer(
				DBQueries.damageUpdateStockBundled);
		StringBuffer sqlCircle = new StringBuffer(DBQueries.damageGetCircle);
		try {
			con = DBConnectionManager.getDBConnection();
			ps = con.prepareStatement(sql.toString());
			ps.setString(1, imeiNo);
			rs = ps.executeQuery();
			while (rs.next()) {
				productCode = rs.getString("REQUISITION_PRODUCT_CODE");
				bundleStatus = rs.getString("bundled");
				logger.info("productCode: " + productCode + " bundleStatus:"
						+ bundleStatus);
			}

//			Added by Shilpa on 05-01-2012
			DBConnectionManager.releaseResources(ps, rs);
			//Ends here
			// Finding the Circle using SIM
			ps = con.prepareStatement(sqlCircle.toString());
			ps.setString(1, simNo);
			rs = ps.executeQuery();
			while (rs.next()) {
				circleId = rs.getInt("circle_id");
			}
			// Calling the procedure to run for bundling in case of central and
			// local users
			cs = con.prepareCall(DBQueries.callBundleProc);
			cs.setInt(1, hboUser.getId().intValue());
			cs.setInt(2, Integer.parseInt(hboUser.getWarehouseID()));
			cs.setInt(3, Integer.parseInt(productCode));
			cs.setInt(4, circleId);
			cs.setInt(5, 1);
			cs.setString(6, simNo);
			if (roleList.contains(HBOConstants.ROLE_NATIONALDIST)) {
				role = HBOConstants.ROLE_NATIONALDIST;
			} else if (roleList.contains(HBOConstants.ROLE_LOCALDIST)) {
				role = HBOConstants.ROLE_LOCALDIST;
			} else if (roleList.contains(HBOConstants.ROLE_SUPER)) {
				role = HBOConstants.ROLE_SUPER;
			} else if (roleList.contains(HBOConstants.ROLE_DIST)) {
				role = HBOConstants.ROLE_DIST;
			}
			cs.setString(7, role);
			boolean k = cs.execute();
			sql_IMEI = new StringBuffer(DBQueries.getImeiNo);
//			Added by Shilpa on 05-01-2012
			DBConnectionManager.releaseResources(ps, rs);
			//Ends here
			ps = con.prepareStatement(sql_IMEI.toString());
			ps.setString(1, simNo);
			rs = ps.executeQuery();
			while (rs.next()) {
				newImeiNo = (rs.getString("IMEI_NO"));
			}
			if (newImeiNo.equalsIgnoreCase("")) {
				// For Bundled Stock users=local
				if (roleList.contains(HBOConstants.ROLE_LOCALDIST)) {
					sqlbundle.append(DBQueries.damageCircleNullCond);
//					Added by Shilpa on 05-01-2012
					DBConnectionManager.releaseResources(ps, null);
					//Ends here
					ps = con.prepareStatement(sqlbundle.toString());
					ps.setInt(1, warehouseId);
					ps.setInt(2, Integer.parseInt(productCode));
					ps.executeUpdate();
					con.commit();
				}
				// For Bundled Stock users = central
				else if (roleList.contains(HBOConstants.ROLE_NATIONALDIST)) {
					sqlbundle.append(DBQueries.damageCircleCond);
//					Added by Shilpa on 05-01-2012
					DBConnectionManager.releaseResources(ps, null);
					//Ends here
					ps = con.prepareStatement(sqlbundle.toString());
					ps.setInt(1, warehouseId);
					ps.setInt(2, Integer.parseInt(productCode));
					ps.setInt(3, circleId);
					ps.executeUpdate();
					con.commit();
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
			logger.error("SQL Exception occured while inserting."
					+ "Exception Message: " + e.getMessage());
			throw new DAOException("SQLException: " + e.getMessage(), e);
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("Exception occured while inserting."
					+ "Exception Message: " + e.getMessage());
			throw new DAOException("Exception: " + e.getMessage(), e);
		} finally {
			try {
				if (rs != null)
					rs.close();
				if (ps != null)
					ps.close();
				if (con != null) {
					con.close();
				}
			} catch (Exception e) {
			}
		}
		updtDmgFlag(imeiNo, simNo, hboUser);
		return newImeiNo;
	}

	public String markDamagedProduct(HBOStockDTO stockDto) throws DAOException {
		StringBuffer sql = null;
		Connection con = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		String damageFlag = "";
		String updateDamagedFlag = HBOConstants.SUCCESS;
		try {
			con = DBConnectionManager.getDBConnection();
			sql = new StringBuffer();
			sql.append(DBQueries.checkSerialNo);
			sql.append(" with ur");
//			Added by Shilpa on 05-01-2012
			DBConnectionManager.releaseResources(ps, rs);
			//Ends here
			ps = con.prepareStatement(sql.toString());
			ps.setString(1, stockDto.getSerialNo());
			ps.setInt(2, stockDto.getId().intValue());
			rs = ps.executeQuery();
			if (!rs.next()) {
				updateDamagedFlag = HBOConstants.PRODUCT_NOT_AVAILABLE;
				return updateDamagedFlag;
			}
			sql = new StringBuffer(DBQueries.checkDamagedFlag);
			sql.append(" with ur");
//			Added by Shilpa on 05-01-2012
			DBConnectionManager.releaseResources(ps, rs);
			//Ends here
			ps = con.prepareStatement(sql.toString());
			ps.setString(1, stockDto.getSerialNo());
			rs = ps.executeQuery();
			if (rs.next()) {
				damageFlag = rs.getString("MARK_DAMAGED");
				if (damageFlag.equalsIgnoreCase(HBOConstants.DAMAGED)) {
					return HBOConstants.ALREADY_DAMAGED;
				}
			}
			sql = new StringBuffer(DBQueries.markDamagedProduct);
			sql.append(" with ur");
//			Added by Shilpa on 05-01-2012
			DBConnectionManager.releaseResources(ps, rs);
			//Ends here
			ps = con.prepareStatement(sql.toString());
			ps.setString(1, stockDto.getRemarks());
			ps.setInt(2, stockDto.getId().intValue());
			ps.setDouble(3, stockDto.getCost());
			ps.setString(4, stockDto.getSerialNo());
			ps.setInt(5, stockDto.getId().intValue());
			ps.setInt(6, Integer.parseInt(stockDto.getProduct()));
			int update = ps.executeUpdate();
			long closingBal=0;
			ps.clearParameters();
			sql = new StringBuffer(DBQueries.checkCurrentDateRecord);
			sql.append(" with ur");
//			Added by Shilpa on 05-01-2012
			DBConnectionManager.releaseResources(ps, rs);
			//Ends here
			ps = con.prepareStatement(sql.toString());
			ps.setInt(1, stockDto.getId().intValue());
			ps.setInt(2, Integer.parseInt(stockDto.getProduct()));
			rs = ps.executeQuery();
			if(!rs.next()){
				sql = new StringBuffer(DBQueries.selectMaxDateClosingBalance);
				sql.append(" with ur");
//				Added by Shilpa on 05-01-2012
				DBConnectionManager.releaseResources(ps, rs);
				//Ends here
				ps = con.prepareStatement(sql.toString());
				ps.setInt(1, stockDto.getId().intValue());
				ps.setInt(2, Integer.parseInt(stockDto.getProduct()));
				ps.setInt(3, stockDto.getId().intValue());
				ps.setInt(4, Integer.parseInt(stockDto.getProduct()));
				rs = ps.executeQuery();
				if(rs.next()){
					closingBal = rs.getLong("CLOSINGBALANCE");
				}
				sql = new StringBuffer(DBQueries.insertClosingBal);
				sql.append(" with ur");
//				Added by Shilpa on 05-01-2012
				DBConnectionManager.releaseResources(ps, rs);
				//Ends here
				ps = con.prepareStatement(sql.toString());
				ps.setInt(1, stockDto.getId().intValue());
				ps.setInt(2, Integer.parseInt(stockDto.getProduct()));
				ps.setLong(3,closingBal);
				ps.setLong(4,closingBal);
				ps.executeUpdate();
			}
			
			sql = new StringBuffer(DBQueries.updateStockDamaged);
			sql.append(" with ur ");
//			Added by Shilpa on 05-01-2012
			DBConnectionManager.releaseResources(ps, null);
			//Ends here
			ps = con.prepareStatement(sql.toString());
			ps.setString(1, stockDto.getSerialNo());
			ps.setInt(2, stockDto.getId().intValue());
			ps.setInt(3, Integer.parseInt(stockDto.getProduct()));
			ps.setInt(4, stockDto.getId().intValue());
			ps.setInt(5, stockDto.getId().intValue());
			ps.setInt(6, Integer.parseInt(stockDto.getProduct()));
			update = ps.executeUpdate();
			con.commit();
		} catch (SQLException e) {
			e.printStackTrace();
			try {
				con.rollback();
			} catch (SQLException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			logger.error("Exception :" + e);
			e.printStackTrace();
			logger.error("SQL Exception occured:Mark Damaged");
			updateDamagedFlag = HBOConstants.FAILURE;
		} catch (Exception e) {
			try {
				con.rollback();
			} catch (SQLException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			e.printStackTrace();
			logger.error("Exception occured:Mark Damaged");
			updateDamagedFlag = HBOConstants.FAILURE;
		} finally {
			try {
				if (rs != null)
					rs.close();
			} catch (SQLException sqle) {
				sqle.printStackTrace();
			}
			try {
				if (ps != null)
					ps.close();
			} catch (SQLException sqle) {
				sqle.printStackTrace();
			}
			try {
				if (con != null)
					con.close();
			} catch (SQLException sqle) {
				sqle.printStackTrace();
			}
		}
		return updateDamagedFlag;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.ibm.hbo.dao.HBOStockDAO#updtDmgFlag(java.lang.String,
	 *      java.lang.String, java.lang.String)
	 */
	public void updtDmgFlag(String imeiNo, String simNo, HBOUser hboUser)
			throws DAOException {
		StringBuffer sql = null;
		Connection con = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		String productCode = "";
		String bundleStatus = "";
		long closingBal=0;
		int warehouseId = Integer.parseInt(hboUser.getWarehouseID());
		String roleName = "";
		ArrayList roleList = hboUser.getRoleList();
		if (roleList.contains(HBOConstants.ROLE_NATIONALDIST))
			roleName = HBOConstants.ROLE_NATIONALDIST;
		else if (roleList.contains(HBOConstants.ROLE_LOCALDIST))
			roleName = HBOConstants.ROLE_LOCALDIST;
		else if (roleList.contains(HBOConstants.ROLE_SUPER))
			roleName = HBOConstants.ROLE_SUPER;
		else
			roleName = HBOConstants.ROLE_DIST;
		HBOStockDTO stockDTO = new HBOStockDTO();
		HBOMarkDamagedFormBean markDamagedFormBean = new HBOMarkDamagedFormBean();
		sql = new StringBuffer(DBQueries.damageFlagReqProducts);
		try {
			con = DBConnectionManager.getDBConnection();
			ps = con.prepareStatement(sql.toString());
			ps.setString(1, imeiNo);
			ps.setInt(2, warehouseId);
			ps.executeUpdate();
			con.commit();
			sql = new StringBuffer(DBQueries.damageSelectProdCode);
//			Added by Shilpa on 05-01-2012
			DBConnectionManager.releaseResources(ps, null);
			//Ends here
			ps = con.prepareStatement(sql.toString());
			ps.setString(1, imeiNo);
			rs = ps.executeQuery();
			while (rs.next()) {
				productCode = rs.getString("REQUISITION_PRODUCT_CODE");
				bundleStatus = rs.getString("bundled");
			}
			// For Unbundled Stock all the users will use same query
			if (bundleStatus.equalsIgnoreCase(HBOConstants.UNBUNDLE_FLAG)) {
				StringBuffer sqlUnbundle = new StringBuffer(
						DBQueries.damageUpdateStockUnbundled);
//				Added by Shilpa on 05-01-2012
				DBConnectionManager.releaseResources(ps, null);
				//Ends here
				ps = con.prepareStatement(sqlUnbundle.toString());
				ps.setInt(1, warehouseId);
				ps.setString(2, productCode);
				ps.executeUpdate();
				con.commit();
			}
			// For Bundled Stock
			if (bundleStatus.equalsIgnoreCase(HBOConstants.BUNDLE_FLAG)
					&& (roleName.equalsIgnoreCase(HBOConstants.ROLE_SUPER) || roleName
							.equalsIgnoreCase(HBOConstants.ROLE_DIST))) {
				StringBuffer sqlbundle = new StringBuffer(
						DBQueries.damageUpdateStockBundled);
				sqlbundle.append(DBQueries.damageCircleNullCond);
//				Added by Shilpa on 05-01-2012
				DBConnectionManager.releaseResources(ps, null);
				//Ends here
				ps = con.prepareStatement(sqlbundle.toString());
				ps.setInt(1, warehouseId);
				ps.setString(2, productCode);
				ps.executeUpdate();
				con.commit();
			}
			if(roleName.equalsIgnoreCase(HBOConstants.ROLE_DIST)){
			sql = new StringBuffer(DBQueries.markDamagedHandsetInv);
//			Added by Shilpa on 05-01-2012
			DBConnectionManager.releaseResources(ps, null);
			//Ends here
			ps = con.prepareStatement(sql.toString());
			ps.setInt(1, warehouseId);
			ps.setString(2, imeiNo);
			ps.setInt(3, warehouseId);
			ps.setInt(4, Integer.parseInt(productCode));
			int updated = ps.executeUpdate();	
			sql = new StringBuffer(DBQueries.checkCurrentDateRecord);
//			Added by Shilpa on 05-01-2012
			DBConnectionManager.releaseResources(ps, rs);
			//Ends here
			ps = con.prepareStatement(sql.toString());
			ps.setInt(1, warehouseId);
			ps.setInt(2, Integer.parseInt(productCode));
			rs = ps.executeQuery();
			if(!rs.next()){
				sql = new StringBuffer(DBQueries.selectMaxDateClosingBalance);
//				Added by Shilpa on 05-01-2012
				DBConnectionManager.releaseResources(ps, rs);
				//Ends here
				ps = con.prepareStatement(sql.toString());
				ps.setInt(1, warehouseId);
				ps.setInt(2, Integer.parseInt(productCode));
				ps.setInt(3, warehouseId);
				ps.setInt(4, Integer.parseInt(productCode));
				rs = ps.executeQuery();
				if(rs.next()){
					closingBal = rs.getLong("CLOSINGBALANCE");
				}
				sql = new StringBuffer(DBQueries.insertClosingBal);
//				Added by Shilpa on 05-01-2012
				DBConnectionManager.releaseResources(ps, null);
				//Ends here
				ps = con.prepareStatement(sql.toString());
				ps.setInt(1, warehouseId);
				ps.setInt(2, Integer.parseInt(productCode));
				ps.setLong(3,closingBal);
				ps.setLong(4,closingBal);
				ps.executeUpdate();
			}
			
			sql = new StringBuffer(DBQueries.updateStockDamaged);
//			Added by Shilpa on 05-01-2012
			DBConnectionManager.releaseResources(ps, null);
			//Ends here
			ps = con.prepareStatement(sql.toString());
			ps.setString(1, imeiNo);
			ps.setInt(2, warehouseId);
			ps.setInt(3, Integer.parseInt(productCode));
			ps.setInt(4, warehouseId);
			ps.setInt(5, warehouseId);
			ps.setInt(6, Integer.parseInt(productCode));
			int update = ps.executeUpdate();
			}
			con.commit();
			
		} catch (SQLException e) {
			e.printStackTrace();
			logger.error("SQL Exception occured while inserting."
					+ "Exception Message: " + e.getMessage());
			throw new DAOException("SQLException: " + e.getMessage(), e);
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("Exception occured while inserting."
					+ "Exception Message: " + e.getMessage());
			throw new DAOException("Exception: " + e.getMessage(), e);
		} finally {
			try {
				if (rs != null)
					rs.close();
				if (ps != null)
					ps.close();
				if (con != null) {
					con.close();
				}
			} catch (Exception e) {
			}
		}
	}

	public void AssignProdStock(HBOStockDTO AssProdStkDto, String warehouseId,
			HBOUser hboUserDetails) throws DAOException {
		logger.info("Insert Assigned Prod Stock Data ");
		logger.info("Insert Assigned Prod Stock Data ");

		int userId = hboUserDetails.getId().intValue();

		int warehouseFromId = Integer.parseInt(warehouseId);

		int warehouseIdTo = AssProdStkDto.getWarehouseId();

		String stockType = AssProdStkDto.getBundle();
		if (stockType == null || stockType.equals(null)) {
			stockType = "UnBundled";
		}
		stockType = stockType.equalsIgnoreCase("Bundled") ? "Y" : "N";
		// RoleList to get RoleId like ROLE_ND,ROLE_LD etc
		ArrayList roleList = AssProdStkDto.getRoleList();
		String roleID = "";
		if (roleList.contains(HBOConstants.ROLE_DIST))
			roleID = HBOConstants.ROLE_DIST;
		else if (roleList.contains(HBOConstants.ROLE_LOCALDIST))
			roleID = HBOConstants.ROLE_LOCALDIST;
		else if (roleList.contains(HBOConstants.ROLE_NATIONALDIST))
			roleID = HBOConstants.ROLE_NATIONALDIST;
		else if (roleList.contains(HBOConstants.ROLE_SUPER))
			roleID = HBOConstants.ROLE_SUPER;

		int circleID = Integer.parseInt(AssProdStkDto.getCircle());

		String pseudoProdCode = AssProdStkDto.getProduct();

		String prodID = pseudoProdCode.substring(6, pseudoProdCode.length());
		// int prodQty=Integer.parseInt(AssProdStkDto.getAssignedProdQty());
		int prodQty = AssProdStkDto.getAssignedProdQty();
		logger.info("prodQtyprodQtyprodQty" + prodQty);
		boolean flag = assignHandsetProc(userId, warehouseFromId,
				warehouseIdTo, prodQty, stockType, Integer.parseInt(prodID),
				circleID, roleID);
	}

	public boolean assignHandsetProc(int userId, int warehouseFromId,
			int warehouseIdTo, int prodQty, String stockType, int prodCode,
			int circleID, String roleID) throws DAOException {
		Connection con = null;
		CallableStatement cs = null;
		boolean flag;
		try {
			con = DBConnectionManager.getDBConnection();
			cs = con
					.prepareCall("{call PROC_DP_ASSIGN_PRODSTOCK_TEST(?,?,?,?,?,?,?,?,?,?)}");
			cs.setInt(1, userId); // Account id
			cs.setInt(2, warehouseFromId);
			cs.setInt(3, warehouseIdTo);
			cs.setInt(4, prodQty);
			cs.setString(5, stockType);
			cs.setInt(6, prodCode);
			cs.setInt(7, circleID);
			cs.setString(8, roleID);
			cs.registerOutParameter(9,Types.CHAR);
			cs.registerOutParameter(10,Types.VARCHAR);
			flag = cs.execute();
			logger.info("Record updated in Assign Prod Stock " + flag);
			con.commit();
		} catch (SQLException e) {
			e.printStackTrace();
			logger.error("SQL Exception occured while inserting."
					+ "Exception Message: " + e.getMessage());
			throw new DAOException("SQLException: " + e.getMessage(), e);
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("Exception occured while inserting."
					+ "Exception Message: " + e.getMessage());
			throw new DAOException("Exception: " + e.getMessage(), e);
		} finally {
			try {
				if (cs != null)
					cs.close();
				if (con != null) {
					con.close();
				}
			} catch (Exception e) {
			}
		}
		return flag;
	}
	
	// Get total available quantity of non serial product.
	public ArrayList getAvailableStock(int accountfrom, int productId,
			String flag, long longAccountID) throws DAOException {
		Connection con = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		StringBuffer sql = new StringBuffer();
		ArrayList arrgetChangedValues = new ArrayList();
		int availableQty = 0;
		try {
			con = DBConnectionManager.getDBConnection();
			// For products other than Handset
			
			if (flag.equalsIgnoreCase("returnStock")) 
			{
				sql = sql.append(DBQueries.GetReturnStock);
				ps = con.prepareStatement(sql.toString());
				ps.setLong(1, longAccountID);
				ps.setInt(2, accountfrom);
				ps.setInt(3, productId);				
			}
			else
			{
				sql = sql.append(DBQueries.GetStock);
				ps = con.prepareStatement(sql.toString());
				ps.setInt(1, accountfrom);
				ps.setInt(2, productId);				
			}
//			// / For handset the record to be shown
//			else if (flag.equalsIgnoreCase("paired")) {
//				sql = sql.append(DBQueries.bundledSIH);
//			} else if (flag.equalsIgnoreCase("non-paired")) {
//				sql = sql.append(DBQueries.UnbundledSIH);
//			}
			 System.out.println("In GetAvailableStock   ::::::" + accountfrom +" productId :::"+ productId +"Flag ::::" +flag);

			rs = ps.executeQuery();
			int i = 0;
			while (rs.next()) {
				DistStockDTO distStock = new DistStockDTO();
				distStock.setOptionValue(rs.getString(1));
				arrgetChangedValues.add(distStock);
				i++;
			}
			if (i == 0) {
				availableQty = insertRowStockSumm(accountfrom, productId);
				DistStockDTO distStock = new DistStockDTO();
				distStock.setOptionValue(availableQty + "");
				arrgetChangedValues.add(distStock);
			}

		} catch (SQLException e) {
			e.printStackTrace();
			throw new DAOException("Exception: " + e.getMessage(), e);
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("Exception occured while inserting."
					+ "Exception Message: " + e.getMessage());
			throw new DAOException("Exception: " + e.getMessage(), e);
		} finally {
			try {
				if (ps != null)
					ps.close();
				if (con != null) {
					con.close();
				}
			} catch (Exception e) {
			}
		}
		return arrgetChangedValues;
	}
	
		
	// Get total available stock of distributor.
	public ArrayList getStockQtyAllocation(int accountfrom, int productId,String flag) throws DAOException 
	{
		Connection con = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		StringBuffer sql = new StringBuffer();
		ArrayList arrgetChangedValues = new ArrayList();
		try 
		{
			con = DBConnectionManager.getDBConnection();
			ps = con.prepareStatement(DBQueries.GET_DISTRIBUTOR_AVAILABLE_STOCK);
			ps.setInt(1, accountfrom);
			ps.setInt(2, productId);
			rs = ps.executeQuery();
			if(rs.next())
			{
				DistStockDTO distStock = new DistStockDTO();
				distStock.setOptionValue(rs.getString("TOTAL_AVAL_STOCK"));
				arrgetChangedValues.add(distStock);
			}
		} catch (SQLException e) 
		{
			e.printStackTrace();
			throw new DAOException("Exception: " + e.getMessage(), e);
		} catch (Exception e) 
		{
			e.printStackTrace();
			logger.error("Exception occured while inserting."+ "Exception Message: " + e.getMessage());
			throw new DAOException("Exception: " + e.getMessage(), e);
		} finally {
			try {
				if (ps != null)
					ps.close();
				if (con != null) 
					con.close();
			} catch (Exception e) {
			}
		}
		return arrgetChangedValues;
	}
	
	
	public ArrayList getAvailableStockSecSale(int accountfrom, int productId,String flag) throws DAOException 
	{
		Connection con = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		StringBuffer sql = new StringBuffer();
		ArrayList arrgetChangedValues = new ArrayList();
		try 
		{
			con = DBConnectionManager.getDBConnection();
			ps = con.prepareStatement(DBQueries.GET_FSE_AVAILABLE_STOCK);
			ps.setInt(1, accountfrom);
			ps.setInt(2, productId);
			rs = ps.executeQuery();
			if(rs.next())
			{
				DistStockDTO distStock = new DistStockDTO();
				distStock.setOptionValue(rs.getString("TOTAL_AVAL_STOCK"));
				arrgetChangedValues.add(distStock);
			}
		} catch (SQLException e) 
		{
			e.printStackTrace();
			throw new DAOException("Exception: " + e.getMessage(), e);
		} catch (Exception e) 
		{
			e.printStackTrace();
			logger.error("Exception occured while inserting."+ "Exception Message: " + e.getMessage());
			throw new DAOException("Exception: " + e.getMessage(), e);
		} finally {
			try {
				if (ps != null)
					ps.close();
				if (con != null) 
					con.close();
			} catch (Exception e) {
			}
		}
		return arrgetChangedValues;
	}
	
	// availabe quantity for distrubutor start
		
	public ArrayList getAvailableStockDist(long distID,int productId,String flag) throws DAOException 
	{
	Connection con = null;
	PreparedStatement ps = null;
	ResultSet rs = null;
	StringBuffer sql = new StringBuffer();
	ArrayList arrgetChangedValues = new ArrayList();
	try 
	{
		con = DBConnectionManager.getDBConnection();
		ps = con.prepareStatement(DBQueries.GET_DIST_AVAILABLE_STOCK);	
		ps.setInt(1, productId);
		ps.setLong(2, distID);
		rs = ps.executeQuery();
		if(rs.next())
		{
			DistStockDTO distStock = new DistStockDTO();
			distStock.setOptionValue(rs.getString("TOTAL_AVAL_STOCK"));
			arrgetChangedValues.add(distStock);
		}
	} catch (SQLException e) 
	{
		e.printStackTrace();
		throw new DAOException("Exception: " + e.getMessage(), e);
	} catch (Exception e) 
	{
		e.printStackTrace();
		logger.error("Exception occured while inserting."+ "Exception Message: " + e.getMessage());
		throw new DAOException("Exception: " + e.getMessage(), e);
	} finally {
		try {
			if (ps != null)
				ps.close();
			if (con != null) 
				con.close();
		} catch (Exception e) {
		}
	}
	return arrgetChangedValues;
}
	
	// available quantity for distributor end
	
	
	/*
	public ArrayList getAvailableStock(int accountfrom, int productId,String flag) throws DAOException 
	{
		Connection con = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		StringBuffer sql = new StringBuffer();
		ArrayList arrgetChangedValues = new ArrayList();
		int availableQty = 0;
		try {
			con = DBConnectionManager.getDBConnection();
			// For products other than Handset
//			if (flag.equalsIgnoreCase("none")) 
			{
				sql = sql.append(DBQueries.GetStock);

			}
//			// / For handset the record to be shown
//			else if (flag.equalsIgnoreCase("paired")) {
//				sql = sql.append(DBQueries.bundledSIH);
//			} else if (flag.equalsIgnoreCase("non-paired")) {
//				sql = sql.append(DBQueries.UnbundledSIH);
//			}
			System.out.println("In GetAvailableStock   ::::::" + accountfrom +" productId :::"+ productId +"Flag ::::" +flag);
			ps = con.prepareStatement(sql.toString() + " with ur ");
			ps.setInt(1, accountfrom);
			ps.setInt(2, productId);
			rs = ps.executeQuery();
			int i = 0;
			while (rs.next()) {
				DistStockDTO distStock = new DistStockDTO();
				distStock.setOptionValue(rs.getString(1));
				arrgetChangedValues.add(distStock);
				i++;
			}
			if (i == 0) {
				availableQty = insertRowStockSumm(accountfrom, productId);
				DistStockDTO distStock = new DistStockDTO();
				distStock.setOptionValue(availableQty + "");
				arrgetChangedValues.add(distStock);
			}

		} catch (SQLException e) {
			e.printStackTrace();
			throw new DAOException("Exception: " + e.getMessage(), e);
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("Exception occured while inserting."
					+ "Exception Message: " + e.getMessage());
			throw new DAOException("Exception: " + e.getMessage(), e);
		} finally {
			try {
				if (ps != null)
					ps.close();
				if (con != null) {
					con.close();
				}
			} catch (Exception e) {
			}
		}
		return arrgetChangedValues;
	}
	*/

	public ArrayList recordsUpdated(DistStockDTO distDto) throws DAOException {
	Connection con = null;
	PreparedStatement ps = null;
	ResultSet rs = null;
	StringBuilder sql = new StringBuilder();
	ArrayList recordsUpdated = new ArrayList();
	int levelDiff = 0; 
	String groupNameFrom;
	String groupNameTo;
	int rowsUpdated=0;
	String startingSerial=distDto.getStartingSerial();
	String endingSerial=distDto.getEndingSno();
	int transactionId=0;
	try {
		con = DBConnectionManager.getDBConnection();
		
		int categoryid = distDto.getCategoryId();
		// Get the Category of selected product to check if Purchase 
		// Internally or externally and Serially 
		sql=new StringBuilder();
		sql = sql.append(DBQueries.selectCategoryList);
		ps = con.prepareStatement(sql.toString() + " with ur");
		ps.setInt(1, distDto.getCategoryId());
		rs = ps.executeQuery();
		String productType = "";
		String serially="";
		while (rs.next()) {
			productType = rs.getString("PURCHASE_INTERNALLY");
			serially = rs.getString("SERIALLY");
		}
		
		// Finding the level of the acount then need to run a  diff query
		logger.info("Finding the level of the account from  if max then need to run a diff query");
		sql = new StringBuilder();
		sql.append(DBQueries.GetLevel);
//		Added by Shilpa on 05-01-2012
		DBConnectionManager.releaseResources(ps, rs);
		//Ends here
		ps = con.prepareStatement(sql.toString() + " with ur ");
		ps.setInt(1, distDto.getAccountFrom());
		ps.setInt(2, distDto.getAccountTo());
		rs = ps.executeQuery();
		while (rs.next()) {
			levelDiff = rs.getInt("1");
		}

		// finding the group names for the selected accounts so as to update
		// columns accordingly
		sql = new StringBuilder();
		sql = sql.append(DBQueries.getRoles);
//		Added by Shilpa on 05-01-2012
		DBConnectionManager.releaseResources(ps, rs);
		//Ends here
		ps = con.prepareStatement(sql.toString());
		ps.setInt(1, distDto.getAccountFrom());
		ps.setInt(2, distDto.getAccountTo());
		rs = ps.executeQuery();
		String groupName[] = new String[2];
		String nextLevel[] = new String[2];
		int i = 0;
		while (rs.next()) {
			groupName[i] = rs.getString("GROUP_NAME");
			nextLevel[i] =  rs.getString("NEXT_LEVEL");
			i++;
		}

		if (levelDiff < 0) {
			groupNameFrom = groupName[0].toUpperCase();
			groupNameTo = groupName[1].toUpperCase();
		} else {
			groupNameTo = groupName[0].toUpperCase();
			groupNameFrom = groupName[1].toUpperCase();
		}

		if (levelDiff < 0) { // fwd assign
			
			
			if (productType.equalsIgnoreCase("Y")&&serially.equalsIgnoreCase("Y")) {
				//	Getting the transaction from Transaction Sequence
				if(!distDto.getBillNo().equals("xxxz") && distDto.getRate()!=-1.0 && distDto.getVat()!=-1.0){
					transactionId=returnTransactionId();
				}
				
				if((startingSerial==""||startingSerial==null)&&(endingSerial==""||endingSerial==null)){
					sql = new StringBuilder();
					/*sql = sql.append(DBQueries.selectSerialnos);
					sql.append(" and "+groupNameTo+"_id is null and "+groupNameFrom+"_id=? and PRODUCT_ID=? order by date("+groupNameFrom+"_PURCHASE_DATE) ");
					sql.append(" fetch first "+distDto.getAssignedProdQty()+" rows only)A");
					logger.info("SQL--"+sql.toString());
					ps = con.prepareStatement(sql.toString() + " with ur");
					ps.setInt(1, distDto.getAccountFrom());
					ps.setInt(2, distDto.getProductId());
					rs = ps.executeQuery();
					while(rs.next()){
						startingSerial=rs.getString("ST_SERIAL_NO");
						endingSerial=rs.getString("END_SERIAL_NO");
					}*/
					
					// Changed By Aditya
					
					sql = sql.append(DBQueries.simRcStockDist);
					sql	.append(" set "
										+ groupNameTo
										+ "_id=?, "
										+ groupNameTo
										+ "_purchase_date=current timestamp,REMARKS=?,TRANSACTION_ID =? where "
										+" serial_no in "
										+ " (select SERIAL_NO from DP_STOCK_INVENTORY where PRODUCT_ID=? and "
										+ groupNameFrom
										+ "_ID=? and MARK_DAMAGED='N' and "
										+ groupNameTo+"_id is null "
										+ " order by date(DISTRIBUTOR_PURCHASE_DATE) fetch first "
										+ distDto.getAssignedProdQty()+" rows only ) ");
					
//					Added by Shilpa on 05-01-2012
					DBConnectionManager.releaseResources(ps, null);
					//Ends here
					
					ps = con.prepareStatement(sql.toString()+" with ur");
					ps.setInt(1, distDto.getAccountTo());
					ps.setString(2, distDto.getRemarks());
					ps.setString(3, transactionId+"");
					ps.setInt(4, distDto.getProductId());
					ps.setInt(5, distDto.getAccountFrom());
					rowsUpdated = ps.executeUpdate();
				
				}
				else{
				//	Updating dp_stock_inventory 
				sql = new StringBuilder();
				sql = sql.append(DBQueries.simRcStockDist);
				sql	.append(" set "
									+ groupNameTo
									+ "_id=?, "
									+ groupNameTo
									+ "_purchase_date=current timestamp,REMARKS=?,TRANSACTION_ID =? where product_id=? and "
									+ groupNameFrom
									+ "_id=? "
									+ " and serial_no>= ? and serial_no<= ? and "
									+ groupNameTo + "_id is null and MARK_DAMAGED='N'");
//				Added by Shilpa on 05-01-2012
				DBConnectionManager.releaseResources(ps, null);
				//Ends here
				
				ps = con.prepareStatement(sql.toString()+" with ur");
				ps.setInt(1, distDto.getAccountTo());
				ps.setString(2, distDto.getRemarks());
				ps.setString(3, transactionId+"");
				ps.setInt(4, distDto.getProductId());
				ps.setInt(5, distDto.getAccountFrom());
				ps.setString(6, startingSerial);
				ps.setString(7, endingSerial);
				rowsUpdated = ps.executeUpdate();
			
				}
				
			} else if (productType.equalsIgnoreCase("N")&&serially.equalsIgnoreCase("N")) {
				int fetchRowss = distDto.getAssignedProdQty();
				sql = new StringBuilder();
				sql = sql.append("update DP_STOCK_INVENTORY set "
									+ groupNameTo
									+ "_ID=?, "
									+ groupNameTo
									+ "_PURCHASE_DATE=current timestamp,REMARKS=? where SERIAL_NO in "
									+ " (select SERIAL_NO from DP_STOCK_INVENTORY where PRODUCT_ID=? and "
									+ groupNameFrom
									+ "_ID=? and MARK_DAMAGED='N' "
									+ " order by date(DISTRIBUTOR_PURCHASE_DATE) fetch first "
									+ fetchRowss + " rows only )");
				
				logger.info("SQL ------"+sql.toString());
//				Added by Shilpa on 05-01-2012
				DBConnectionManager.releaseResources(ps, null);
				//Ends here
				ps = con.prepareStatement(sql.toString()+" with ur");
				ps.setInt(1, distDto.getAccountTo());
				ps.setString(2, distDto.getRemarks());
				ps.setInt(3, distDto.getProductId());
				ps.setInt(4, distDto.getAccountFrom());
				rowsUpdated = ps.executeUpdate();
				//	Getting the transaction from Transaction Sequence
				if(!distDto.getBillNo().equals("xxxz") && distDto.getRate()!=-1 && distDto.getVat()!=-1)
				transactionId=returnTransactionId();

			}
			else if (productType.equalsIgnoreCase("Y")&&serially.equalsIgnoreCase("N")){
				rowsUpdated=-1;
				//	Getting the transaction from Transaction Sequence
				if(!distDto.getBillNo().equals("xxxz") && distDto.getRate()!=-1 && distDto.getVat()!=-1)
				transactionId=returnTransactionId();
			}
						
			// the message that would be shown in comment
			distDto
					.setOptionValue("Actual Assigned Quantity= " + rowsUpdated
							+ "");
			recordsUpdated.add(distDto);

			if (rowsUpdated != 0) 
			{
				// Updating the Dist_stock_summary for Assign From
				logger.info("Updating the Dist_stock_summary for Assign From");
				int assignedQty=0;
				if(rowsUpdated==-1){
					assignedQty=rowsUpdated=distDto.getAssignedProdQty();
					
				}
				else{
					assignedQty = rowsUpdated;
				}
				// Updating Transaction Table in case of Secondary Sales
				if(!distDto.getBillNo().equals("xxxz") && distDto.getRate()!=-1 && distDto.getVat()!=-1 && transactionId!=0){
				int transactionRecords = transactionsInserted(distDto, assignedQty, transactionId);
				if(transactionRecords==0){
					throw new Exception("Exception: Error updating transaction table");
				}
				}
				int accountFrom = distDto.getAccountFrom();
				int productId = distDto.getProductId();
					insertRowStockSumm(distDto.getAccountFrom(), productId);
				sql = new StringBuilder();
				sql = sql.append(DBQueries.distStockSummarySales);
//				Added by Shilpa on 05-01-2012
				DBConnectionManager.releaseResources(ps, null);
				//Ends here
				ps = con.prepareStatement(sql.toString()+" with ur");
				ps.setInt(1, assignedQty);
				ps.setInt(2, assignedQty);
				ps.setInt(3, assignedQty);
				ps.setInt(4, accountFrom);
				ps.setInt(5, productId);
				ps.executeUpdate();

				// Updating the Dist_stock_summary for Assign To
				logger.info("Updating the Dist_stock_summary for Assign To");
				//insertRowStockSumm(distDto.getAccountTo(), productId);
				sql = new StringBuilder();
				sql = sql.append(DBQueries.distStockTo);
//				Added by Shilpa on 05-01-2012
				DBConnectionManager.releaseResources(ps, null);
				//Ends here
				ps = con.prepareStatement(sql.toString()+" with ur");
				ps.setInt(1, assignedQty);
				ps.setInt(2, assignedQty);
				ps.setInt(3, distDto.getAccountTo());
				ps.setInt(4, productId);
				int rows = ps.executeUpdate();

				if (rows == 0) {
					sql = new StringBuilder();
					sql = sql.append(DBQueries.insertReceiptBal);
//					Added by Shilpa on 05-01-2012
					DBConnectionManager.releaseResources(ps, null);
					//Ends here
					ps = con.prepareStatement(sql.toString()+" with ur");
					ps.setInt(1, distDto.getAccountTo());
					ps.setInt(2, productId);
					ps.setInt(3, assignedQty);
					ps.setInt(4, assignedQty);
					ps.executeUpdate();

				}

			}
		} else {   // STOCK RETURN
			if (productType.equalsIgnoreCase("Y")&&serially.equalsIgnoreCase("Y")) { 
				// When serial no does not come frm screen: Allocation Screen - getting Serial Nos
				if((startingSerial==""||startingSerial==null)&&(endingSerial==""||endingSerial==null)){
					sql = new StringBuilder();
					sql = sql.append(DBQueries.selectSerialnos);
					sql.append(" and "+groupNameFrom+"_id=? and PRODUCT_ID=? and "+nextLevel[1].toUpperCase()+"_id is null order by date("+groupNameFrom+"_PURCHASE_DATE) ");
					sql.append(" fetch first "+distDto.getAssignedProdQty()+" rows only)A");
					
					
					logger.info("SQL--"+sql.toString());
//					Added by Shilpa on 05-01-2012
					DBConnectionManager.releaseResources(ps, rs);
					//Ends here
					ps = con.prepareStatement(sql.toString() + " with ur");
					ps.setInt(1, distDto.getAccountFrom());
					ps.setInt(2, distDto.getProductId());
					rs = ps.executeQuery();
					while(rs.next()){
						startingSerial=rs.getString("ST_SERIAL_NO");
						endingSerial=rs.getString("END_SERIAL_NO");
					}
				}
				//Update DP STOCK INVENTORY
				
				sql = new StringBuilder();
				sql = sql.append(DBQueries.simRcRetStock);
				sql.append("" + groupNameFrom + "_id=null,REMARKS=? "
						+ " where product_id=? and "+nextLevel[1].toUpperCase()+"_id is null and " + groupNameTo
						+ "_id=? and serial_no>= ? and serial_no<= ? and "
						+ groupNameFrom + "_id=? ");
				
//				Added by Shilpa on 05-01-2012
				DBConnectionManager.releaseResources(ps, null);
				//Ends here
				
				ps = con.prepareStatement(sql.toString()+" with ur");
				ps.setString(1, distDto.getRemarks());
				ps.setInt(2, distDto.getProductId());
				ps.setInt(3, distDto.getAccountTo());
				ps.setString(4, startingSerial);
				ps.setString(5, endingSerial);
				ps.setInt(6, distDto.getAccountFrom());
				rowsUpdated = ps.executeUpdate();
				
				
			} else if (productType.equalsIgnoreCase("N")&&serially.equalsIgnoreCase("N")) { // HANDSETS CASE
				int fetchRows = distDto.getAssignedProdQty();
				sql = new StringBuilder();
				sql = sql
						.append("update DP_STOCK_INVENTORY set "
								+ groupNameFrom
								+ "_ID=null,REMARKS=? where SERIAL_NO in "
								+ " (select SERIAL_NO from DP_STOCK_INVENTORY where PRODUCT_ID=? and "
								+ groupNameFrom
								+ "_ID=?  "
								+ " order by date(DISTRIBUTOR_PURCHASE_DATE) fetch first "
								+ fetchRows + " rows only )");
				
//				Added by Shilpa on 05-01-2012
				DBConnectionManager.releaseResources(ps, null);
				//Ends here
				
				ps = con.prepareStatement(sql.toString()+" with ur");
				ps.setString(1, distDto.getRemarks());
				ps.setInt(2, distDto.getProductId());
				ps.setInt(3, distDto.getAccountFrom());
				rowsUpdated = ps.executeUpdate();
			}else if (productType.equalsIgnoreCase("Y")&&serially.equalsIgnoreCase("N")){
				rowsUpdated = -1;
			}

			recordsUpdated.add(distDto);

			if (rowsUpdated != 0) {
				// Updating the Dist_stock_summary for Assign From
				logger.info("Updating the Dist_stock_summary for Assign From");
				int assignedQty =0;
				int accountFrom = distDto.getAccountFrom();
				int productId = distDto.getProductId();
				int accountTo = distDto.getAccountTo();
				if(rowsUpdated==-1){
					rowsUpdated=assignedQty=distDto.getAssignedProdQty();
					int closingBal=0;
					sql = new StringBuilder();
					sql = sql.append(DBQueries.balance);
//					Added by Shilpa on 05-01-2012
					DBConnectionManager.releaseResources(ps, rs);
					//Ends here
					ps = con.prepareStatement(sql.toString()+" with ur");
					ps.setInt(1, accountFrom);
					ps.setInt(2, productId);
					rs=ps.executeQuery();
					while(rs.next()){
						closingBal=rs.getInt("CLOSINGBALANCE");
					}
					if(closingBal<assignedQty){
						assignedQty=closingBal;
					}
				}else{
					assignedQty = rowsUpdated;
				}
				insertRowStockSumm(accountFrom, productId); // If there is no record for current date Dist Stock Summary
				sql = new StringBuilder();
				sql = sql.append(DBQueries.distStockSummary); // To update Dist Stock Summary For Assign Frm eg FSE(frm) to Dist(To)
//				Added by Shilpa on 05-01-2012
				DBConnectionManager.releaseResources(ps, null);
				//Ends here
				ps = con.prepareStatement(sql.toString()+" with ur");
				ps.setInt(1, assignedQty);
				ps.setInt(2, assignedQty);
				ps.setInt(3, assignedQty);
				ps.setInt(4, accountFrom);
				ps.setInt(5, productId);
				ps.executeUpdate();

				// Updating the Dist_stock_summary for Assign To
				logger.info("Updating the Dist_stock_summary for Assign To");
				//insertRowStockSumm(accountTo, productId);
				sql = new StringBuilder();
				sql = sql.append(DBQueries.retdistStockTo);
//				Added by Shilpa on 05-01-2012
				DBConnectionManager.releaseResources(ps, null);
				//Ends here
				ps = con.prepareStatement(sql.toString()+" with ur");
				ps.setInt(1, assignedQty);
				ps.setInt(2, assignedQty);
				ps.setInt(3, accountTo);
				ps.setInt(4, productId);
				int rows = ps.executeUpdate();

				if (rows == 0) {
					sql = new StringBuilder();
					sql = sql.append(DBQueries.insertReceiptBal);
//					Added by Shilpa on 05-01-2012
					DBConnectionManager.releaseResources(ps, null);
					//Ends here
					ps = con.prepareStatement(sql.toString()+" with ur");
					ps.setInt(1, distDto.getAccountTo());
					ps.setInt(2, productId);
					ps.setInt(3, assignedQty);
					ps.setInt(4, assignedQty);
					ps.executeUpdate();
				}
			}
		}
		if(rowsUpdated==0){
			distDto.setOptionValue("No record Updated, Error in Input");
		}
		distDto.setOptionValue("Actual Assigned Quantity= " + rowsUpdated+ "");
		con.commit();
	} catch (SQLException e) {
		try {
			con.rollback();
		} catch (Exception ex) {
		}
		e.printStackTrace();
		throw new DAOException("Exception: " + e.getMessage(), e);
	} catch (Exception e) {
		try {
			con.rollback();
		} catch (Exception ex) {
		}
		e.printStackTrace();
		logger.error("Exception occured while inserting."
				+ "Exception Message: " + e.getMessage());
		throw new DAOException("Exception: " + e.getMessage(), e);
	} finally {
		try {
			if (ps != null)
				ps.close();
			if (con != null) {
				con.close();
			}
		} catch (Exception e) {
		}
	}
	return recordsUpdated;
	}

	//rajiv jha added method for allocation and secondary start
	
	public ArrayList recordsNewUpdated(DistStockDTO distDto) throws DAOException {
		Connection con = null;
		Connection connCpe=null;
		Connection connCpeNew=null;
		PreparedStatement ps = null;
		PreparedStatement ps1 = null;
		PreparedStatement ps2 = null;
		PreparedStatement ps3 = null;
		PreparedStatement ps4 = null;
		CallableStatement cs = null;
		ResultSet rs = null;
		ResultSet rs1 = null;
		StringBuilder sql = new StringBuilder();
		ArrayList recordsUpdated = new ArrayList();
		int levelDiff = 0; 
		String groupNameFrom;
		String groupNameTo;
		int rowsUpdated=0;
		String startingSerial=distDto.getStartingSerial();//startingSerial contains total serial in array
		String endingSerial=distDto.getEndingSno();//endingSno contains total serial number
		String allocation=distDto.getAllocation();
		int transactionId=0;
		DistStockDTO dtoDist=null;
		StringTokenizer Tok_returnToFse=null;
		String sr_nos="";
		try {
			con = DBConnectionManager.getDBConnection();
			String cpeUser= ResourceReader.getCoreResourceBundleValue(com.ibm.virtualization.recharge.common.Constants.DP_CPE_USER);
					
			
			//System.out.println("cpeUser:::::::::::::::::::::::::::::::"+cpeUser);
		
			int categoryid = distDto.getCategoryId();
			// Get the Category of selected product to check if Purchase 
			// Internally or externally and Serially 
			sql=new StringBuilder();
			sql = sql.append(DBQueries.selectCategoryList);
//			Added by Shilpa on 05-01-2012
			//	DBConnectionManager.releaseResources(ps, rs);
				//Ends here
			ps = con.prepareStatement(sql.toString() + " with ur");
			ps.setInt(1, distDto.getCategoryId());
			rs = ps.executeQuery();
			String productType = "";
			String serially="";
			while (rs.next())
			{
				productType = rs.getString("PURCHASE_INTERNALLY");
				serially = rs.getString("SERIALLY");
			}			
			// Finding the level of the acount then need to run a  diff query
			logger.info("Finding the level of the account from  if max then need to run a diff query");
			sql = new StringBuilder();
			sql.append(DBQueries.GetLevel);
//			Added by Shilpa on 05-01-2012
			DBConnectionManager.releaseResources(ps, rs);
			//Ends here
			ps = con.prepareStatement(sql.toString() + " with ur ");
			ps.setInt(1, distDto.getAccountFrom());
			ps.setInt(2, distDto.getAccountTo());
			rs = ps.executeQuery();
			while (rs.next()) {
				levelDiff = rs.getInt("1");
			}
			// finding the group names for the selected accounts so as to update
			// columns accordingly
			sql = new StringBuilder();
			sql = sql.append(DBQueries.getRoles);
//			Added by Shilpa on 05-01-2012
			DBConnectionManager.releaseResources(ps, rs);
			//Ends here
			ps = con.prepareStatement(sql.toString());
			ps.setInt(1, distDto.getAccountFrom());
			ps.setInt(2, distDto.getAccountTo());
			rs = ps.executeQuery();
			String groupName[] = new String[2];
			String nextLevel[] = new String[2];
			int i = 0;
			while (rs.next()) {
				groupName[i] = rs.getString("GROUP_NAME");
				nextLevel[i] =  rs.getString("NEXT_LEVEL");
				i++;
			}
			if (levelDiff < 0) {
				groupNameFrom = groupName[0].toUpperCase();
				groupNameTo = groupName[1].toUpperCase();
			} else {
				groupNameTo = groupName[0].toUpperCase();
				groupNameFrom = groupName[1].toUpperCase();
			}
			
//			Naveen Jain: Code change against SR1992936
			//Added to Return to FSE update real time in CPE
			//Implemention in CR49032
			// if(distDto.isReturn_to_fse())  //Commented and updated by Shilpa to implement updation in CPE only in case of Business category CPE
			if(distDto.isReturn_to_fse() && categoryid ==1)
			{
				try
				{
					boolean validateCPE=true;
					// check CPE inventory
					connCpe=DPCPEDBConnection.getDBConnectionCPE();
					connCpe.setAutoCommit(false);
					Tok_returnToFse = new StringTokenizer(distDto.getStartingSerial(),",");
					if(Tok_returnToFse.countTokens() > 1)
					{
					 while (Tok_returnToFse.hasMoreElements())
					 {
						 sr_nos = sr_nos+ "'"+Tok_returnToFse.nextToken()+"',";
					 }
					 sr_nos = sr_nos.substring(0,sr_nos.length()-1);
					// System.out.println("sr_nos::"+sr_nos);	
					 String sql_cpe="select ASSET_SERIAL_NO from "+cpeUser+".CPE_INVENTORY_DETAILS where ASSET_SERIAL_NO in ("+sr_nos+") and CPE_INVENTORY_STATUS_KEY = 3 ";
					 System.out.println(sql_cpe);
					 ps1=connCpe.prepareStatement(sql_cpe);
					// System.out.println("sr_nos::"+sr_nos.substring(0,sr_nos.length()-1));
					}
					else
					{
						sr_nos = distDto.getStartingSerial();
						String sql_cpe="select ASSET_SERIAL_NO from "+cpeUser+".CPE_INVENTORY_DETAILS where ASSET_SERIAL_NO in ('"+sr_nos+"') and CPE_INVENTORY_STATUS_KEY = 3 ";
						ps1=connCpe.prepareStatement(sql_cpe);
						System.out.println(sql_cpe);
					}
					rs1=ps1.executeQuery();
					while(rs1.next())
					{
						dtoDist = new DistStockDTO();
						dtoDist.setSerialNo((String)rs1.getString("ASSET_SERIAL_NO"));
						dtoDist.setValidate(false);
						recordsUpdated.add(dtoDist);
						validateCPE = false;
					}
					if(!validateCPE)
					{
						return recordsUpdated;
					}
					//move from temp to hist
					String sql_cpe="";
					String sql_delete="";
					
					Tok_returnToFse = new StringTokenizer(distDto.getStartingSerial(),",");
					if(Tok_returnToFse.countTokens() > 1)
					{
						sql_cpe="INSERT INTO DPCPE.CPE_INSERT_SERIAL_ASSIGN_HIST(SEQ_ID, SERIAL_NUMBER, STATUS, TRANSACTION_ID, DIST_ID, PRODUCT_ID, UNASSIGN_DATE, ASSIGN_DATE, TRANSFER_DATE, FSE_ID, RETAILER_ID, LAST_HIST_DATE, CUSTOMER_ID, IS_SCM, PRODUCT_TYPE)  " 
									+" SELECT SEQ_ID, SERIAL_NUMBER, -1,     TRANSACTION_ID, DIST_ID, PRODUCT_ID, UNASSIGN_DATE, ASSIGN_DATE, sysdate,FSE_ID, RETAILER_ID,sysdate,CUSTOMER_ID,'Y', PRODUCT_TYPE  "
		                            +" FROM DPCPE.CPE_TEMP_INSERT_SERIAL_NUMBER where SERIAL_NUMBER in("+sr_nos+") and PRODUCT_ID = "+distDto.getProductId();
						
						sql_delete="delete from DPCPE.CPE_TEMP_INSERT_SERIAL_NUMBER where SERIAL_NUMBER in("+sr_nos+")  and PRODUCT_ID = "+distDto.getProductId();
					}
					else
					{
						sql_cpe="INSERT INTO DPCPE.CPE_INSERT_SERIAL_ASSIGN_HIST(SEQ_ID, SERIAL_NUMBER, STATUS, TRANSACTION_ID, DIST_ID, PRODUCT_ID, UNASSIGN_DATE, ASSIGN_DATE, TRANSFER_DATE, FSE_ID, RETAILER_ID, LAST_HIST_DATE, CUSTOMER_ID, IS_SCM, PRODUCT_TYPE)  " 
							+" SELECT SEQ_ID, SERIAL_NUMBER, -1,     TRANSACTION_ID, DIST_ID, PRODUCT_ID, UNASSIGN_DATE, ASSIGN_DATE, sysdate,FSE_ID, RETAILER_ID,sysdate,CUSTOMER_ID,'Y', PRODUCT_TYPE  "
	                        +" FROM DPCPE.CPE_TEMP_INSERT_SERIAL_NUMBER where SERIAL_NUMBER in('"+sr_nos+"') and PRODUCT_ID ="+distDto.getProductId();
						
						sql_delete="delete from DPCPE.CPE_TEMP_INSERT_SERIAL_NUMBER where SERIAL_NUMBER in('"+sr_nos+"')  and PRODUCT_ID ="+distDto.getProductId();
						//System.out.println("sql_cpe::"+sql_cpe);
						//System.out.println("sql_delete::"+sql_delete);
						
					} 
					ps2 = connCpe.prepareStatement(sql_cpe);
					ps2.executeUpdate();
					
					
					ps3 = connCpe.prepareStatement(sql_delete);
					ps3.executeUpdate();
					
					connCpe.commit();
			}
			catch(Exception ex)
			{
				try
				{
					String err = ResourceReader.getCoreResourceBundleValue(Constants.Return_To_Fse_Critical);
					logger.info(err + "::" +Utility.getCurrentDate());
				}
				catch(Exception exp)
				{
					exp.printStackTrace();
				}
				logger.info("Error in return to fse::"+ex);
				try
				{
					connCpe.rollback();
				}
				catch(Exception ex1)
				{
					logger.info("Error in return to fse::"+ex1);
					
				}
				
				
			}
			finally
			{
				DBConnectionManager.releaseResources(ps1, rs1);
				DBConnectionManager.releaseResources(ps2, null);
				DBConnectionManager.releaseResources(ps3, null);
				DBConnectionManager.releaseResources(connCpe);
			}
			
		}
		
			
			if (levelDiff < 0) { // fwd assign				
				
				if (productType.equalsIgnoreCase("Y")&&serially.equalsIgnoreCase("Y")) {
					//	Getting the transaction from Transaction Sequence
					if(!distDto.getBillNo().equals("xxxz") && distDto.getRate()!=-1.0 && distDto.getVat()!=-1.0){
						transactionId=returnTransactionId();
					}
					
					if(allocation!=null && allocation.equalsIgnoreCase("yes")&& !(distDto.getSecondary().equalsIgnoreCase("yes"))){
						sql = new StringBuilder();
						/*sql = sql.append(DBQueries.selectSerialnos);
						sql.append(" and "+groupNameTo+"_id is null and "+groupNameFrom+"_id=? and PRODUCT_ID=? order by date("+groupNameFrom+"_PURCHASE_DATE) ");
						sql.append(" fetch first "+distDto.getAssignedProdQty()+" rows only)A");
						logger.info("SQL--"+sql.toString());
						ps = con.prepareStatement(sql.toString() + " with ur");
						ps.setInt(1, distDto.getAccountFrom());
						ps.setInt(2, distDto.getProductId());
						rs = ps.executeQuery();
						while(rs.next()){
							startingSerial=rs.getString("ST_SERIAL_NO");
							endingSerial=rs.getString("END_SERIAL_NO");
						}*/
						
						// Add by harbans
						distDto.setStatusOSCM(Constants.TRANS_TYPE_ALDP);
						
						// Changed By Aditya
						logger.info("rajivjha check Strarting snos >:"+distDto.getStartingSerial());
						sql = sql.append(DBQueries.simRcStockDist);
						sql	.append(" set "
											+ groupNameTo
											+ "_id=?, "
											+ groupNameTo
											+ "_purchase_date=current timestamp,REMARKS=?,TRANSACTION_ID =?, IS_SEC_SALE='Y' where PRODUCT_ID = ? and DISTRIBUTOR_ID = ? and "
											+" serial_no in "
											+ " (select SERIAL_NO from DP_STOCK_INVENTORY where PRODUCT_ID=? and "
											+ groupNameFrom
											+ "_ID=? and MARK_DAMAGED='N' and "
											+ groupNameTo+"_id is null "
											//rajiv jha add start
											//+ " and serial_no in(?) and "
											+ " and serial_no in(?))  ");
											//rajiv jha add end
											//+ " order by date(DISTRIBUTOR_PURCHASE_DATE) fetch first "
											//+ distDto.getAssignedProdQty()+" rows only ) ");
						
//						Added by Shilpa on 05-01-2012
						DBConnectionManager.releaseResources(ps, null);
						//Ends here
						ps = con.prepareStatement(sql.toString()+" with ur");
						
						ps.setInt(1, distDto.getAccountTo());
						ps.setString(2, distDto.getRemarks());
						ps.setString(3, transactionId+"");
						ps.setInt(4, distDto.getProductId());
						ps.setInt(5, distDto.getAccountFrom());
						ps.setInt(6, distDto.getProductId());
						ps.setInt(7, distDto.getAccountFrom());
						//rajiv jha mod start						
						StringTokenizer Tok = new StringTokenizer(distDto.getStartingSerial(),",");
						if(Tok.countTokens() > 1)
						{
						 while (Tok.hasMoreElements())
						 {
							 ps.setString(8, Tok.nextToken());//starting serial no contains array of serial nos
							 ps.addBatch();
						 }
						}
						else
						{
							ps.setString(8, distDto.getStartingSerial());//starting serial no contains array of serial nos
							 ps.addBatch();							
						}
						//rajiv jha mod end
						 int[] numRows;
						 numRows = ps.executeBatch();
						 rowsUpdated = numRows.length;
						System.err.println("jha check in allocation numRows updated :"+numRows.length);
					}
					else{
					//	Updating dp_stock_inventory
					// Add by harbans: INTRODUCE NEW STATUS UNASSIGN PENDING (10) AND UNASSIGN COMPLETE(1). 
					distDto.setStatusOSCM(Constants.TRANS_TYPE_SSDP);
						
					sql = new StringBuilder();
					sql = sql.append(DBQueries.simRcStockDist);
					sql	.append(" set "
										+ groupNameTo
										+ "_id=?, "
										+ groupNameTo
										+ "_purchase_date=current timestamp,STATUS=?, IS_SEC_SALE='Y', REMARKS=?,TRANSACTION_ID =? where product_id=? and "
										+ groupNameFrom
										+ "_id=? "
										//+ " and serial_no>= ? and serial_no<= ? and " rajiv jha comented
										+ " and serial_no in(?) and "//rajiv jha added
										+ groupNameTo + "_id is null and MARK_DAMAGED='N'");
//					Added by Shilpa on 05-01-2012
					DBConnectionManager.releaseResources(ps, null);
					//Ends here
					int status = 10;
					if(categoryid!=1)
						status = 1;
					
					ps = con.prepareStatement(sql.toString()+" with ur");
					ps.setInt(1, distDto.getAccountTo());
					ps.setInt(2, status);
					ps.setString(3, distDto.getRemarks());
					ps.setString(4, transactionId+"");
					ps.setInt(5, distDto.getProductId());
					ps.setInt(6, distDto.getAccountFrom());
					
					//rajiv jha add serial nos in batch start
					StringTokenizer Tok = new StringTokenizer(distDto.getStartingSerial(),",");
					 if(Tok.countTokens() > 1)
					 {
						 while (Tok.hasMoreElements())
						 {
							 ps.setString(7, Tok.nextToken());//starting serial no contains array of serial nos
							 ps.addBatch();
						 }
					 }
					 else
					 {
						 ps.setString(7, distDto.getStartingSerial());//starting serial no contains array of serial nos
						 ps.addBatch();
						 
					 }
					int[] numRows;
					numRows = ps.executeBatch();
					rowsUpdated =  numRows.length;				
				}
				} else if (productType.equalsIgnoreCase("N")&&serially.equalsIgnoreCase("N")) {
					int fetchRowss = distDto.getAssignedProdQty();
					sql = new StringBuilder();
					sql = sql.append("update DP_STOCK_INVENTORY set "
										+ groupNameTo
										+ "_ID=?, "
										+ groupNameTo
										+ "_PURCHASE_DATE=current timestamp,REMARKS=? where SERIAL_NO in "
										+ " (select SERIAL_NO from DP_STOCK_INVENTORY where PRODUCT_ID=? and "
										+ groupNameFrom
										+ "_ID=? and MARK_DAMAGED='N' "
										+ " order by date(DISTRIBUTOR_PURCHASE_DATE) fetch first "
										+ fetchRowss + " rows only )");
					
					logger.info("SQL ------"+sql.toString());
//					Added by Shilpa on 05-01-2012
					DBConnectionManager.releaseResources(ps, null);
					//Ends here
					ps = con.prepareStatement(sql.toString()+" with ur");
					ps.setInt(1, distDto.getAccountTo());
					ps.setString(2, distDto.getRemarks());
					ps.setInt(3, distDto.getProductId());
					ps.setInt(4, distDto.getAccountFrom());
					rowsUpdated = ps.executeUpdate();
					//	Getting the transaction from Transaction Sequence
					if(!distDto.getBillNo().equals("xxxz") && distDto.getRate()!=-1 && distDto.getVat()!=-1)
					transactionId=returnTransactionId();
				}
				else if (productType.equalsIgnoreCase("Y")&&serially.equalsIgnoreCase("N")){
					rowsUpdated=-1;
					//	Getting the transaction from Transaction Sequence
					if(!distDto.getBillNo().equals("xxxz") && distDto.getRate()!=-1 && distDto.getVat()!=-1)
					transactionId=returnTransactionId();
				}
							
				// the message that would be shown in comment
				distDto.setOptionValue("Actual Assigned Quantity= " + rowsUpdated+ "");
				recordsUpdated.add(distDto);

				if (rowsUpdated != 0) 
				{
				// Updating the Dist_stock_summary for Assign From
				logger.info("Updating the Dist_stock_summary for Assign From rowsUpdated  :"+rowsUpdated);
				int assignedQty=0;
			    	if(rowsUpdated==-1)
			    	{
						assignedQty=rowsUpdated=distDto.getAssignedProdQty();
					}
					else
					{
						assignedQty = rowsUpdated;
					}
					// Updating Transaction Table in case of Secondary Sales
					if(!distDto.getBillNo().equals("xxxz") && distDto.getRate()!=-1 && distDto.getVat()!=-1 && transactionId!=0){
					int transactionRecords = transactionsInserted(distDto, assignedQty, transactionId);
					if(transactionRecords==0){
						throw new Exception("Exception: Error updating transaction table");
					}
					}
					int accountFrom = distDto.getAccountFrom();
					int productId = distDto.getProductId();
						insertRowStockSumm(distDto.getAccountFrom(), productId);
					sql = new StringBuilder();
					sql = sql.append(DBQueries.distStockSummarySales);
//					Added by Shilpa on 05-01-2012
					DBConnectionManager.releaseResources(ps, null);
					//Ends here
					ps = con.prepareStatement(sql.toString()+" with ur");
					System.err.println("jha4 sql.toString()  :"+sql.toString());
					ps.setInt(1, assignedQty);
					ps.setInt(2, assignedQty);
					ps.setInt(3, assignedQty);
					ps.setInt(4, accountFrom);
					ps.setInt(5, productId);
					ps.executeUpdate();

					// Updating the Dist_stock_summary for Assign To
					logger.info("Updating the Dist_stock_summary for Assign To");
					//insertRowStockSumm(distDto.getAccountTo(), productId);
					sql = new StringBuilder();
					sql = sql.append(DBQueries.distStockTo);
//					Added by Shilpa on 05-01-2012
					DBConnectionManager.releaseResources(ps, null);
					//Ends here
					ps = con.prepareStatement(sql.toString()+" with ur");
					ps.setInt(1, assignedQty);
					ps.setInt(2, assignedQty);
					ps.setInt(3, distDto.getAccountTo());
					ps.setInt(4, productId);
					int rows = ps.executeUpdate();

					if (rows == 0) {
						sql = new StringBuilder();
						sql = sql.append(DBQueries.insertReceiptBal);
//						Added by Shilpa on 05-01-2012
						DBConnectionManager.releaseResources(ps, null);
						//Ends here
						ps = con.prepareStatement(sql.toString()+" with ur");
						ps.setInt(1, distDto.getAccountTo());
						ps.setInt(2, productId);
						ps.setInt(3, assignedQty);
						ps.setInt(4, assignedQty);
						ps.executeUpdate();

					}
				}
			} else {   // STOCK RETURN
				if (productType.equalsIgnoreCase("Y")&&serially.equalsIgnoreCase("Y")) { 
					// When serial no does not come frm screen: Allocation Screen - getting Serial Nos
					//if((startingSerial==""||startingSerial==null)&&(endingSerial==""||endingSerial==null)){ //rajiv jha comented
					if(allocation!=null && allocation.equalsIgnoreCase("yes"))
					{
						/*
						
						sql = new StringBuilder();
						sql = sql.append(DBQueries.selectSerialnos);
						sql.append(" and "+groupNameFrom+"_id=? and PRODUCT_ID=?");
						if(!groupNameFrom.equalsIgnoreCase("RETAILER"))
						{	sql.append(" and "+nextLevel[1].toUpperCase()+"_id is null ");	}
						sql.append("_id is null order by date("+groupNameFrom+"_PURCHASE_DATE) ");
						sql.append(" fetch first "+distDto.getAssignedProdQty()+" rows only)A");
						
						
						logger.info("SQL--"+sql.toString());
						ps = con.prepareStatement(sql.toString() + " with ur");
						ps.setInt(1, distDto.getAccountFrom());
						ps.setInt(2, distDto.getProductId());
						rs = ps.executeQuery();
						while(rs.next()){
							startingSerial=rs.getString("ST_SERIAL_NO");
							endingSerial=rs.getString("END_SERIAL_NO");
						}*/
					//Update DP STOCK INVENTORY
					sql = new StringBuilder();
					sql = sql.append(DBQueries.simRcRetStock);
					if(!groupNameFrom.equalsIgnoreCase("RETAILER"))
					{	
						sql.append("" + groupNameFrom + "_id=null, REMARKS=?  where product_id=?");
						sql.append(" and "+nextLevel[1].toUpperCase()+"_id is null and ");
						sql.append(groupNameTo+ "_id=? and serial_no in(?) and "+ groupNameFrom + "_id=? ");
					}
					else
					{
						//sql.append("" + groupNameFrom + "_id=null, REMARKS=?, STATUS=5  where product_id=? and ");
						sql.append("" + groupNameFrom + "_id=null," + groupNameFrom + "_PURCHASE_DATE=null, REMARKS=?, STATUS=5  where product_id=? and ");
						sql.append(groupNameTo+ "_id=? and serial_no in(?) and "+ groupNameFrom + "_id=? ");
					}
					// For retailer start					
					    ps = con.prepareStatement(sql.toString()+" with ur");
						ps.setString(1, distDto.getRemarks());
						ps.setInt(2, distDto.getProductId());
						ps.setInt(3, distDto.getAccountTo());
						ps.setInt(5, distDto.getAccountFrom());
						
					StringTokenizer Tok = new StringTokenizer(distDto.getStartingSerial(),",");
					if(Tok.countTokens() > 1 )
					{
						 while (Tok.hasMoreElements())
						 {						
						  ps.setString(4, Tok.nextToken());//starting serial no contains array of serial nos
						  ps.addBatch();
					     }	
						 
					}else
					{
						  ps.setString(4, distDto.getStartingSerial());//starting serial no contains array of serial nos
						  ps.addBatch();
						
					}
						 int[] numRows;
						 numRows = ps.executeBatch();
						 rowsUpdated = numRows.length;
					  }					
					
				} else if (productType.equalsIgnoreCase("N")&&serially.equalsIgnoreCase("N")) { // HANDSETS CASE
					int fetchRows = distDto.getAssignedProdQty();
					sql = new StringBuilder();
					sql = sql
							.append("update DP_STOCK_INVENTORY set "
									+ groupNameFrom
									+ "_ID=null,REMARKS=? where SERIAL_NO in "
									+ " (select SERIAL_NO from DP_STOCK_INVENTORY where PRODUCT_ID=? and "
									+ groupNameFrom
									+ "_ID=?  "
									+ " order by date(DISTRIBUTOR_PURCHASE_DATE) fetch first "
									+ fetchRows + " rows only )");
					
//					Added by Shilpa on 05-01-2012
					DBConnectionManager.releaseResources(ps, null);
					//Ends here
					ps = con.prepareStatement(sql.toString()+" with ur");
					ps.setString(1, distDto.getRemarks());
					ps.setInt(2, distDto.getProductId());
					ps.setInt(3, distDto.getAccountFrom());
					rowsUpdated = ps.executeUpdate();
				}else if (productType.equalsIgnoreCase("Y")&&serially.equalsIgnoreCase("N")){
					rowsUpdated = -1;
				}
				recordsUpdated.add(distDto);
				if (rowsUpdated != 0) {
					// Updating the Dist_stock_summary for Assign From
					logger.info("Updating the Dist_stock_summary for Assign From");
					int assignedQty =0;
					int accountFrom = distDto.getAccountFrom();
					int productId = distDto.getProductId();
					int accountTo = distDto.getAccountTo();
					if(rowsUpdated==-1){
						rowsUpdated=assignedQty=distDto.getAssignedProdQty();
						int closingBal=0;
						sql = new StringBuilder();
						sql = sql.append(DBQueries.balance);
//						Added by Shilpa on 05-01-2012
						DBConnectionManager.releaseResources(ps, rs);
						//Ends here
						ps = con.prepareStatement(sql.toString()+" with ur");
						ps.setInt(1, accountFrom);
						ps.setInt(2, productId);
						rs=ps.executeQuery();
						while(rs.next()){
							closingBal=rs.getInt("CLOSINGBALANCE");
						}
						if(closingBal<assignedQty){
							assignedQty=closingBal;
						}
					}else{
						assignedQty = rowsUpdated;
					}
					insertRowStockSumm(accountFrom, productId); // If there is no record for current date Dist Stock Summary
					sql = new StringBuilder();
					sql = sql.append(DBQueries.distStockSummary); // To update Dist Stock Summary For Assign Frm eg FSE(frm) to Dist(To)
//					Added by Shilpa on 05-01-2012
					DBConnectionManager.releaseResources(ps, null);
					//Ends here
					ps = con.prepareStatement(sql.toString()+" with ur");
					ps.setInt(1, assignedQty);
					ps.setInt(2, assignedQty);
					ps.setInt(3, assignedQty);
					ps.setInt(4, accountFrom);
					ps.setInt(5, productId);
					ps.executeUpdate();

					// Updating the Dist_stock_summary for Assign To
					logger.info("Updating the Dist_stock_summary for Assign To");
					//insertRowStockSumm(accountTo, productId);
					sql = new StringBuilder();
					sql = sql.append(DBQueries.retdistStockTo);
//					Added by Shilpa on 05-01-2012
					DBConnectionManager.releaseResources(ps, null);
					//Ends here
					ps = con.prepareStatement(sql.toString()+" with ur");
					ps.setInt(1, assignedQty);
					ps.setInt(2, assignedQty);
					ps.setInt(3, accountTo);
					ps.setInt(4, productId);
					int rows = ps.executeUpdate();

					if (rows == 0) {
						sql = new StringBuilder();
						sql = sql.append(DBQueries.insertReceiptBal);
//						Added by Shilpa on 05-01-2012
						DBConnectionManager.releaseResources(ps, null);
						//Ends here
						ps = con.prepareStatement(sql.toString()+" with ur");
						ps.setInt(1, distDto.getAccountTo());
						ps.setInt(2, productId);
						ps.setInt(3, assignedQty);
						ps.setInt(4, assignedQty);
						ps.executeUpdate();
					}
				}
			}
			if(rowsUpdated==0){
				distDto.setOptionValue("No record Updated, Error in Input");
			}
			distDto.setOptionValue("Actual Assigned Quantity= " + rowsUpdated+ "");
			con.commit();
			
//			Commented and updated by Shilpa to implement updation in CPE only in case of Business category CPE
			//if(distDto.isReturn_to_fse())
			if(distDto.isReturn_to_fse() && categoryid ==1)
			{
				
				try
				{
					connCpeNew=DPCPEDBConnection.getDBConnectionCPE();
					connCpeNew.setAutoCommit(false);
					String sql_cpe_inv="";
					Tok_returnToFse = new StringTokenizer(distDto.getStartingSerial(),",");
					if(Tok_returnToFse.countTokens() > 1)
					{
						sql_cpe_inv="update "+cpeUser+".CPE_INVENTORY_DETAILS set CPE_INVENTORY_STATUS_KEY = 5 where ASSET_SERIAL_NO in ("+sr_nos+") ";
					}
					else
					{
						sql_cpe_inv="update "+cpeUser+".CPE_INVENTORY_DETAILS set CPE_INVENTORY_STATUS_KEY = 5 where ASSET_SERIAL_NO in ('"+sr_nos+"') ";
					}
					ps2=connCpeNew.prepareStatement(sql_cpe_inv);
					ps2.executeUpdate();
					connCpeNew.commit();
				}
				catch(Exception ex2)
				{
					try
					{
						String err = ResourceReader.getCoreResourceBundleValue(Constants.Return_To_Fse_Critical);
						logger.info(err + "::" +Utility.getCurrentDate());
					}
					catch(Exception exp)
					{
						exp.printStackTrace();
					}
					System.out.println("*************Catch Block***************************");
					try
					{
						connCpeNew.rollback();
						
						String sql_cpe_inv="insert into dp_return_fse_fail (serial_number,product_id,status,CREATED_ON,UPDATED_ON) values(?,?,1,current_timestamp,current_timestamp)";
						System.out.println("*************Catch Block***************************"+sql_cpe_inv);
						ps4 = connCpeNew.prepareStatement(sql_cpe_inv);
						
						Tok_returnToFse = new StringTokenizer(distDto.getStartingSerial(),",");
						if(Tok_returnToFse.countTokens() > 1)
						{
							System.out.println("Tok_returnToFse.countTokens() > 1");
						 while(Tok_returnToFse.hasMoreElements())
						 {
							 System.out.println("while(Tok_returnToFse.hasMoreElements())");
							 sr_nos = Tok_returnToFse.nextToken();
							 ps4.setString(1, sr_nos);
							 ps4.setString(2, distDto.getProductId()+"");
							 ps4.addBatch();
						 }
						
						}
						else
						{
							System.out.println("else");
							sr_nos = distDto.getStartingSerial();
							ps4.setString(1, sr_nos);
							ps4.setString(2, distDto.getProductId()+"");
							ps4.addBatch();
						}
						System.out.println("execute batch");
						ps4.executeBatch();
						connCpeNew.commit();
						System.out.println("commit complete");
					}
					catch(Exception ex4)
					{
						try
						{
							connCpeNew.rollback();
						}
						catch(Exception ex5)
						{
							
						}
					}
				}
			}

		} catch (SQLException e) {
			try
			{
				String err = ResourceReader.getCoreResourceBundleValue(Constants.Return_To_Fse_Critical);
				logger.info(err + "::" +Utility.getCurrentDate());
			}
			catch(Exception exp)
			{
				exp.printStackTrace();
			}
			System.out.println(e);
			e.printStackTrace();
			try {
				con.rollback();
			} catch (Exception ex) {
			}
			e.printStackTrace();
			throw new DAOException("Exception: " + e.getMessage(), e);
		} catch (Exception e) {
			try
			{
				String err = ResourceReader.getCoreResourceBundleValue(Constants.Return_To_Fse_Critical);
				logger.info(err + "::" +Utility.getCurrentDate());
			}
			catch(Exception exp)
			{
				exp.printStackTrace();
			}
			System.out.println(e);
			e.printStackTrace();
			try {
				con.rollback();
			} catch (Exception ex) {
			}
			e.printStackTrace();
			logger.error("Exception occured while inserting."
					+ "Exception Message: " + e.getMessage());
			throw new DAOException("Exception: " + e.getMessage(), e);
		} finally {
//			Added by Shilpa on 05-01-2012
			DBConnectionManager.releaseResources(ps, rs);
			DBConnectionManager.releaseResources(ps2, null);
			DBConnectionManager.releaseResources(ps4, null);
			DBConnectionManager.releaseResources(connCpeNew);
			
			//Ends here
			try {
				if (ps != null)
					ps.close();
				if (con != null) {
					con.close();
				}
			} catch (Exception e) {
				try
				{
					String err = ResourceReader.getCoreResourceBundleValue(Constants.Return_To_Fse_Critical);
					logger.info(err + "::" +Utility.getCurrentDate());
				}
				catch(Exception exp)
				{
					exp.printStackTrace();
				}
			}
		}
		return recordsUpdated;
		}
	
	//rajiv jha added method for allocation and secondary end
	
	//rajiv jha added method for cpe updation start
	/*
	public boolean updateRestrictedStatus(DistStockDTO distDto) throws Exception{ 
		boolean updated=false;
		Connection con=null;
		CallableStatement cs = null;    	
    	try{
	    	//con = DBConnectionManager.getDBConnectionCPEDB(); // for oracle
	    	con = DPCPEDBConnection.getDBConnectionCPE(); // for oracle
    	} catch (Exception e) {
			e.printStackTrace();
		System.err.println("Exception while calling getDBConnectionCPEDB() "+e);
    	}
    	try{
    	//String msg="test";
    	cs = con.prepareCall("{call CPE_DP_UPDATE_ASSEST_STATUS(?)}");
		cs.setString(1, distDto.getStartingSerial());//starting serial nos contains arrary of snos				
		//cs.setString(2, msg);
		System.err.println("cs : "+cs.toString());
		System.err.println("distDto.getStartingSerial() : "+distDto.getStartingSerial());
		updated= cs.execute();
		System.err.println("updated : "+updated);
    	} catch (Exception e) {
    		System.err.println("inside procedure catch catch: "+e);
			e.printStackTrace();
			logger.error("Exception occured while procedure updating in seconary ."
					+ "Exception Message: " + e.getMessage());
			throw new DAOException("Exception: " + e.getMessage(), e);
		} finally {
			try {
				if (cs != null)
					cs.close();
				if (con != null) {
					con.close();
				}
			} catch (Exception e) {
			}}
	   return updated;
	    }*/
	
	//rajiv jha added method for cpe updation end
	
//comment by harbans
//public String flagStatus()throws DAOException{
//	  String paramFlag="N";	 
//	  Connection con=null;	 
//	  PreparedStatement ps = null;
//	  ResultSet rs=null;
//	  ps=null;
//	  rs=null;
//	  try{
//		  con = DPCPEDBConnection.getDBConnectionDP();
//	  String query="select PARAM_FLAG from DP_CONTROL_PARAMETER where PARAM_ID=? with ur";
//		ps = con.prepareStatement(query);
//		ps.setInt(1,1);
//		rs=ps.executeQuery();
//		if(rs.next())
//			{	
//				paramFlag=rs.getString("PARAM_FLAG");
//				System.err.println("paramFlag :"+paramFlag);
//			}
//	  }catch(Exception e){
//		  e.printStackTrace();
//	  }	
//	  finally {
//			try {
//				if (rs != null)
//					rs.close();
//			} catch (SQLException sqle) {
//				sqle.printStackTrace();
//			}
//			try {
//				if (ps != null)
//					ps.close();
//			} catch (SQLException sqle) {
//				sqle.printStackTrace();
//			}
//			try {
//				if (con != null)
//					con.close();
//			} catch (SQLException sqle) {
//				sqle.printStackTrace();
//			}
//		}
//	
//	return paramFlag;
//}
	
//	rajiv jha added method for cpe insertion start
	
	public int insertDataCPEDB(DistStockDTO distDto) throws DAOException{ 
		int inserted=0;
		Connection conDB=null;
		//CallableStatement cs = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		
    	try{
	    	//con = DBConnectionManager.getDBConnectionCPEDB(); // for oracle
    		conDB = DPCPEDBConnection.getDBConnectionCPENew(); // for oracle
    	} catch (Exception e) {
			e.printStackTrace();
		System.err.println("Exception while calling getDBConnectionCPEDB() "+e);
    	}
    	try
    	{
    		
    	String insertQuery = "insert INTO CPE_TEMP_INSERT_SERIAL_NUMBER(SEQ_ID, SERIAL_NUMBER, STATUS) Values(CPE_TEMP_SEQ.NEXTVAL,?,?) "; 
    	
    	System.err.println("******** insertQuery cpe :"+insertQuery);
    	ps = conDB.prepareStatement(insertQuery);     	
    	String status="1";
		StringTokenizer Tok = new StringTokenizer(distDto.getStartingSerial(),",");
		if(Tok.countTokens() > 1 )
		{
			while (Tok.hasMoreElements())
			{
				ps.setString(1, Tok.nextToken());//starting serial no contains array of serial nos
				ps.setString(2, status);
				ps.addBatch();
			}
		}
		else
		{
			ps.setString(1, distDto.getStartingSerial());//starting serial no contains array of serial nos
			ps.setString(2, status);
			ps.addBatch();
		}
		
		int[] numRows;
		numRows = ps.executeBatch();
		inserted =  numRows.length;		
    	} catch (Exception e) {
    		System.err.println("inside insertion query catch: "+e);
			e.printStackTrace();
			logger.error("Exception occured while insertin in cpe temp table after seconary ."
					+ "Exception Message: " + e.getMessage());
			throw new DAOException("Exception: " + e.getMessage(), e);
		} 
		finally 
		{
			try 
			{
				
				DPCPEDBConnection.releaseResources(conDB, ps, rs);
				
				
			} catch (Exception e) 
			{
				e.printStackTrace();
			}
		}
	   return inserted;
	}
	
	//rajiv jha added method for cpe insertion end
	
	
	public int insertRowStockSumm(int accountfrom, int productId)
			throws DAOException {
		Connection con = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		StringBuffer sql = new StringBuffer();
		ArrayList arrgetChangedValues = new ArrayList();
		int closingBalance = 0;
		try {
			con = DBConnectionManager.getDBConnection();
			// Get the Closing Balance for Current Date for Login ID
			sql = sql.append(DBQueries.GetStock);
			ps = con.prepareStatement(sql.toString() + " with ur ");
			ps.setInt(1, accountfrom);
			ps.setInt(2, productId);
			rs = ps.executeQuery();
			int flag = 0;
			while (rs.next()) {
				// If found get DB Value
				closingBalance = rs.getInt("CLOSINGBALANCE");
				flag++;
			}
			if (flag == 0) { // No Records Found, Get Last Date Closing Balance
				sql = new StringBuffer();
				sql = sql.append(DBQueries.closingBalance);
				logger.info("sql.toString()----" + sql.toString());
//				Added by Shilpa on 05-01-2012
				DBConnectionManager.releaseResources(ps, rs);
				//Ends here
				ps = con.prepareStatement(sql.toString() + " with ur ");
				ps.setInt(1, accountfrom);
				ps.setInt(2, productId);
				ps.setInt(3, accountfrom);
				ps.setInt(4, productId);
				rs = ps.executeQuery();
				while (rs.next()) {
					closingBalance = rs.getInt("CLOSINGBALANCE");
				}
				// if Closing Balance Found, Then Insert Last Closing as todays
				// opening
				if (closingBalance != 0) {
					sql = new StringBuffer();
					sql.append(DBQueries.insertClosingBal);
//					Added by Shilpa on 05-01-2012
					DBConnectionManager.releaseResources(ps, null);
					//Ends here
					ps = con.prepareStatement(sql.toString());
					ps.setInt(1, accountfrom);
					ps.setInt(2, productId);
					ps.setInt(3, closingBalance);
					ps.setInt(4, closingBalance);
					ps.executeUpdate();
				}
				con.commit();
			}

		} catch (SQLException e) {
			try {
				con.rollback();
			} catch (Exception ex) {
			}
			e.printStackTrace();
		} catch (Exception e) {
			try {
				con.rollback();
			} catch (Exception ex) {
			}
			e.printStackTrace();
			logger.error("Exception occured while inserting."
					+ "Exception Message: " + e.getMessage());
			throw new DAOException("Exception: " + e.getMessage(), e);
		} finally {
			try {
				if (ps != null)
					ps.close();
				if (con != null) {
					con.close();
				}
			} catch (Exception e) {
			}
		}
		return closingBalance;
	}
	
	
	

	public ArrayList getSerialNoList(DistStockDTO distDto)throws DAOException{
		
		Connection con = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		StringBuilder sql = new StringBuilder();
		ArrayList serialNosList = new ArrayList();
		int levelDiff = 0; 
		String groupNameFrom;
		String groupNameTo;
		int rowsUpdated=0;
		String startingSerial=distDto.getStartingSerial();
		String endingSerial=distDto.getEndingSno();
		try {
			con = DBConnectionManager.getDBConnection();
			
			int categoryid = distDto.getCategoryId();
			// Get the Category of selected product to check if Purchase 
			// Internally or externally and Serially 
			sql=new StringBuilder();
			sql = sql.append(DBQueries.selectCategoryList);
			ps = con.prepareStatement(sql.toString() + " with ur");
			ps.setInt(1, distDto.getCategoryId());
			rs = ps.executeQuery();
			String productType = "";
			String serially="";
			while (rs.next()) {
				productType = rs.getString("PURCHASE_INTERNALLY");
				serially = rs.getString("SERIALLY");
			}
			
			// Finding the level of the acount then need to run a  diff query
			logger.info("Finding the level of the account from  if max then need to run a diff query");
			sql = new StringBuilder();
			sql.append(DBQueries.GetLevel);
//			Added by Shilpa on 05-01-2012
			DBConnectionManager.releaseResources(ps, rs);
			//Ends here
			ps = con.prepareStatement(sql.toString() + " with ur ");
			ps.setInt(1, distDto.getAccountFrom());
			ps.setInt(2, distDto.getAccountTo());
			rs = ps.executeQuery();
			while (rs.next()) {
				levelDiff = rs.getInt("1");
			}

			// finding the group names for the selected accounts so as to update
			// columns accordingly
			sql = new StringBuilder();
			sql = sql.append(DBQueries.getRoles);
//			Added by Shilpa on 05-01-2012
			DBConnectionManager.releaseResources(ps, rs);
			//Ends here
			ps = con.prepareStatement(sql.toString());
			ps.setInt(1, distDto.getAccountFrom());
			ps.setInt(2, distDto.getAccountTo());
			rs = ps.executeQuery();
			logger.info(sql.toString());
			String groupName[] = new String[2];
			String nextLevel[] = new String[2];
			int i = 0;
			while (rs.next()) {
				groupName[i] = rs.getString("GROUP_NAME");
				nextLevel[i] =  rs.getString("NEXT_LEVEL");
				i++;
			}

			if (levelDiff < 0) {
				groupNameFrom = groupName[0].toUpperCase();
				groupNameTo = groupName[1].toUpperCase();
			} else {
				groupNameTo = groupName[0].toUpperCase();
				groupNameFrom = groupName[1].toUpperCase();
			}

			if (levelDiff < 0) { // fwd assign
				// Updating dp_stock_inventory 
				if (productType.equalsIgnoreCase("Y")&&serially.equalsIgnoreCase("Y")) {
					if((startingSerial==""||startingSerial==null)&&(endingSerial==""||endingSerial==null)){
						sql = new StringBuilder();
						sql = sql.append(DBQueries.selectAvailableSerialNos);
						sql.append(" and "+groupNameTo+"_id is null and "+groupNameFrom+"_id=? and PRODUCT_ID=? order by SERIAL_NO");
						//Updated by Shilpa Khanna
						//sql.append(" and "+groupNameTo+"_id is null and "+groupNameFrom+"_id=? and PRODUCT_ID=? order by "+groupNameFrom+"_PURCHASE_DATE ");
						//sql.append(" fetch first 100 rows only ");
						logger.info("SQL--"+sql.toString());
//						Added by Shilpa on 05-01-2012
						DBConnectionManager.releaseResources(ps, rs);
						//Ends here
						ps = con.prepareStatement(sql.toString() + " with ur");
						ps.setInt(1, distDto.getAccountFrom());
						ps.setInt(2, distDto.getProductId());
						rs = ps.executeQuery();
						
						while(rs.next()){
							distDto = new DistStockDTO();
							distDto.setSerialNo(rs.getString("SERIAL_NO"));
							serialNosList.add(distDto);
						}
					}
				} 
			} else {   // STOCK RETURN
				if (productType.equalsIgnoreCase("Y")&&serially.equalsIgnoreCase("Y")) {
					System.out.println("rajivjhagroupNameFrom:"+groupNameFrom);
					System.err.println("rajivjhagroupNameFrom:"+groupNameFrom);
					if((startingSerial==""||startingSerial==null)&&(endingSerial==""||endingSerial==null)){
						sql = new StringBuilder();
						sql = sql.append(DBQueries.selectAvailableSerialNos);
						sql.append(" and "+groupNameFrom+"_id=? and PRODUCT_ID=? ");						
						if(!groupNameFrom.equalsIgnoreCase("RETAILER"))  //jha added for retailer
						{	sql.append(" and "+nextLevel[1].toUpperCase()+"_id is null ");	} 
						//Updated by Shilpa
						//sql.append(" order by "+groupNameFrom+"_PURCHASE_DATE ");
						sql.append(" order by SERIAL_NO");
						//Commented by Shilpa Khanna ....
						//sql.append(" fetch first 100 rows only ");						
						
						System.out.println("RETURN SQL query : "+sql.toString());
//						Added by Shilpa on 05-01-2012
						DBConnectionManager.releaseResources(ps, rs);
						//Ends here
						ps = con.prepareStatement(sql.toString() + " with ur");
						ps.setInt(1, distDto.getAccountFrom());
						ps.setInt(2, distDto.getProductId());
						rs = ps.executeQuery();
						while(rs.next()){
							distDto = new DistStockDTO();
							distDto.setSerialNo(rs.getString("SERIAL_NO").trim());
							serialNosList.add(distDto);
						}
					}
				}

		}} catch (SQLException e) {
			try {
				con.rollback();
			} catch (Exception ex) {
			}
			e.printStackTrace();
			throw new DAOException("Exception: " + e.getMessage(), e);
		} catch (Exception e) {
			try {
				con.rollback();
			} catch (Exception ex) {
			}
			e.printStackTrace();
			logger.error("Exception occured while inserting."
					+ "Exception Message: " + e.getMessage());
			throw new DAOException("Exception: " + e.getMessage(), e);
		} finally {
			try {
				if (ps != null)
					ps.close();
				if (con != null) {
					con.close();
				}
			} catch (Exception e) {
			}
		}
		return serialNosList;
	
	}
	
	public ArrayList getInvoiceList(DistStockDTO distDto)throws DAOException{
		
		Connection con = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		StringBuilder sql = new StringBuilder();
		ArrayList invoiceList = new ArrayList();
		try {
			con = DBConnectionManager.getDBConnection();
			ps = con.prepareStatement(DBQueries.viewAssignedInvoiceList.toString());
		}catch (SQLException e) {
			try {
				con.rollback();
			} catch (Exception ex) {
			}
			e.printStackTrace();
			throw new DAOException("Exception: " + e.getMessage(), e);
		} catch (Exception e) {
			try {
				con.rollback();
			} catch (Exception ex) {
			}
			e.printStackTrace();
			logger.error("Exception occured while inserting."
					+ "Exception Message: " + e.getMessage());
			throw new DAOException("Exception: " + e.getMessage(), e);
		} finally {
			try {
				if (rs != null)
					rs.close();
			} catch (SQLException sqle) {
				sqle.printStackTrace();
			}
			try {
				if (ps != null)
					ps.close();
			} catch (SQLException sqle) {
				sqle.printStackTrace();
			}
			try {
				if (con != null)
					con.close();
			} catch (SQLException sqle) {
				sqle.printStackTrace();
			}
		}
		return invoiceList;
	}

	public int transactionsInserted(DistStockDTO distDto,int assignedQuantty,int retailerTransID) throws DAOException {
		Connection con = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		StringBuilder sql = new StringBuilder();
		int rows=0;
		try {
			con = DBConnectionManager.getDBConnection();
			sql=new StringBuilder();
			sql = sql.append(DBQueries.insertRetailerTrans);
			
			ps = con.prepareStatement(sql.toString());
			ps.setInt(1,retailerTransID);
			ps.setInt(2,distDto.getUserId());
			ps.setInt(3,distDto.getCategoryId());
			ps.setInt(4,distDto.getProductId());
			ps.setInt(5,distDto.getAccountFrom());
			ps.setInt(6,distDto.getAccountTo());
			ps.setInt(7,assignedQuantty);
			ps.setString(8,distDto.getBillNo());
			ps.setDouble(9,distDto.getVat());
			ps.setDouble(10,distDto.getRate());
			ps.setString(11,distDto.getStatusOSCM());
						
			rows=ps.executeUpdate();
			con.commit();
			}catch (SQLException e) {
				e.printStackTrace();	
			try {
				con.rollback();
			} catch (Exception ex) {
			}
			
			throw new DAOException("Exception: " + e.getMessage(), e);
		} catch (Exception e) {
			e.printStackTrace();
			try {
				con.rollback();
			} catch (Exception ex) {
			}
			logger.error("Exception occured while inserting."
					+ "Exception Message: " + e.getMessage());
			throw new DAOException("Exception: " + e.getMessage(), e);
		} finally {
			try {
				if (rs != null)
					rs.close();
			} catch (SQLException sqle) {
				sqle.printStackTrace();
			}
			try {
				if (ps != null)
					ps.close();
			} catch (SQLException sqle) {
				sqle.printStackTrace();
			}
			try {
				if (con != null)
					con.close();
			} catch (SQLException sqle) {
				sqle.printStackTrace();
			}
		}
		return rows;
	}
	
	public int returnTransactionId()throws DAOException {
		int transactionId=0;
		Connection con = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		StringBuilder sql = new StringBuilder();
		
		try {
			con = DBConnectionManager.getDBConnection();
			sql=new StringBuilder();
			sql.append(DBQueries.transactionID);
			ps = con.prepareStatement(sql.toString());
			rs=ps.executeQuery();
			while(rs.next()){
				transactionId=rs.getInt(1);
			}
		}catch (SQLException e) {
			try {
			con.rollback();
			} catch (Exception ex) {
			}
		
			throw new DAOException("Exception: " + e.getMessage(), e);
		} catch (Exception e) {
			try {
				con.rollback();
			} catch (Exception ex) {
			}
			e.printStackTrace();
			logger.error("Exception occured while inserting."
					+ "Exception Message: " + e.getMessage());
			throw new DAOException("Exception: " + e.getMessage(), e);
		} finally {
			try {
				if (rs != null)
					rs.close();
			} catch (SQLException sqle) {
				sqle.printStackTrace();
			}
			try {
				if (ps != null)
					ps.close();
			} catch (SQLException sqle) {
				sqle.printStackTrace();
			}
			try {
				if (con != null)
					con.close();
			} catch (SQLException sqle) {
				sqle.printStackTrace();
			}
		}
		return transactionId;
		
	}

	public ArrayList getAvailableFreshStockSerialNos(DistStockDTO distDto) throws DAOException {

		
		Connection con = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		StringBuilder sql = new StringBuilder();
		ArrayList serialNosList = new ArrayList();
		
		try {
			con = DBConnectionManager.getDBConnection();			
						sql = sql.append(DBQueries.GET_DIST_FRESH_AVAILABLE_STOCK);	
						ps = con.prepareStatement(sql.toString());
						ps.setInt(1,distDto.getProductId());
						ps.setLong(2,distDto.getDistID());
						rs = ps.executeQuery();						
						while(rs.next()){
							distDto = new DistStockDTO();
							distDto.setSerialNo(rs.getString("SERIAL_NO"));
							distDto.setProductName(rs.getString("PRODUCT_NAME"));
							serialNosList.add(distDto);
							} 
			
		} catch (SQLException e) {
			try {
				con.rollback();
			} catch (Exception ex) {
			}
			e.printStackTrace();
			throw new DAOException("Exception: " + e.getMessage(), e);
		} catch (Exception e) {
			try {
				con.rollback();
			} catch (Exception ex) {
			}
			e.printStackTrace();
			logger.error("Exception occured while inserting."
					+ "Exception Message: " + e.getMessage());
			throw new DAOException("Exception: " + e.getMessage(), e);
		} finally {
			try {
				if (ps != null)
					ps.close();
				if (con != null) {
					con.close();
				}
			} catch (Exception e) {
			}
		}
		return serialNosList;
	
	
	}

	public ArrayList freshRecordsNewUpdated(DistStockDTO distDto) throws DAOException {

		PreparedStatement ps = null;
		PreparedStatement ps1 = null;
		PreparedStatement ps2 = null;
		PreparedStatement ps3 = null;
		PreparedStatement ps4 = null;
		PreparedStatement ps5 = null;
		
		ArrayList SDCNO = new ArrayList();
		StringBuilder sql = new StringBuilder();
		Connection con = null;
		ResultSet rset			= null;
		String strDcNo ="", strIsSecSale = "";
		String status="";
		try 
			{
//			Updated by Shilpa for Critical Changes BFR 14
				if(distDto.getCourierAgency()==null || distDto.getCourierAgency().equals("") || distDto.getDocketNum()==null || distDto.getDocketNum().equals("")){
					status = com.ibm.dp.common.Constants.PUSH_DC_TO_BOTREE_DRAFT_STATUS;
				}else{
					status =com.ibm.dp.common.Constants.PUSH_DC_TO_BOTREE_CREATED_STATUS;
				}
		//Ends here 
				con = DBConnectionManager.getDBConnection();	
				con.setAutoCommit(false);
				DpDcReverseStockInventory dcCreationDto = null;
				DcCreation dcCreation = DcCreation.getDcCreationObject();
				
				strDcNo = dcCreation.generateDcNo(con, Constants.DC_TYPE_FRESH);
				SDCNO.add(strDcNo);
				
					sql=new StringBuilder();
					sql.append(DBQueries.INSERT_DP_STOCK_INVENTORY_ASSIGNED);
					ps = con.prepareStatement(sql.toString());
					
					sql=new StringBuilder();
					sql.append(DBQueries.INSERT_DP_REV_DELIVERY_CHALAN_DETAIL);
					ps1 = con.prepareStatement(sql.toString());
					
					sql=new StringBuilder();
					sql.append(DBQueries.INSERT_DP_REV_FRESH_STOCK);
					ps3 = con.prepareStatement(sql.toString());
					
					sql=new StringBuilder();
					sql.append(DBQueries.INSERT_DP_REV_DELIVERY_CHALAN_HDR);
					ps2  = con.prepareStatement(sql.toString());
					
					sql=new StringBuilder();
					sql.append(DBQueries.DELETE_FROM_STOCK_INVENTORY);
					ps4  = con.prepareStatement(sql.toString());
					
					sql=new StringBuilder();
					sql.append(DBQueries.SR_NO_SEC_SALE_STATUS);
					ps5  = con.prepareStatement(sql.toString());
				//To insert values in DP_DELIVERY_CHALAN_HDR
//					Updated by Shilpa for Critical Changes BFR 14

					 ps2.setString(1, strDcNo);
					 ps2.setInt(2, distDto.getCircleId());
					 ps2.setLong(3, distDto.getDistID());
					 ps2.setString(4, status);
					 ps2.setString(5, distDto.getRemarks());
					 ps2.setString(6, distDto.getCourierAgency());
					 ps2.setString(7, distDto.getDocketNum());
					 ps2.setLong(8, distDto.getDistID());
					 ps2.executeUpdate(); 
					 
					String sr_no="";
					
				StringTokenizer st =null;
				String sr_no_list = "";
				
				String[] strProdId = distDto.getJsArrprodId()[0].split(",");
				String[] strremark= distDto.getJsArrremarks()[0].split(",");
				
				sr_no_list = distDto.getJsSrNo()[0].substring(0,distDto.getJsSrNo()[0].length()-1);
				
				System.out.println("String Print all ---- == "+sr_no_list);
				String[] strsrNo= sr_no_list.split("#,");
				StringTokenizer stSrNo = null;
				String strSrNo="";
//				Added by SHilpa Khanna on 2-07-12 for Releasing POs
				ArrayList<String> arrReleaseSerialNo = new ArrayList<String>();
				for(int i=0; i< strProdId.length ; i++)
				{
					
					stSrNo = new StringTokenizer(strsrNo[i],",");
					while(stSrNo.hasMoreTokens())
					{
						strSrNo = (String) stSrNo.nextToken();
//						Added by SHilpa Khanna on 2-07-12 for Releasing POs
						arrReleaseSerialNo.add(strSrNo);
						 ps.setLong(1, distDto.getDistID());
						 ps.setString(2, strProdId[i]);
						 ps.setString(3, strSrNo);				 
						 
						 ps5.setString(1, strSrNo);
						 ps5.setString(2, strProdId[i]);
						 ps5.setLong(3, distDto.getDistID());
//							Added by Shilpa on 05-01-2012
							DBConnectionManager.releaseResources(null, rset);
							//Ends here
						 rset = ps5.executeQuery();
						 if(rset.next())
							 strIsSecSale = rset.getString(1);
						 
						 ps3.setLong(1, distDto.getDistID());
						 ps3.setString(2, strProdId[i]);
						 ps3.setString(3,strSrNo);
						 ps3.setString(4, strremark[i]);
						 ps3.setString(5, strIsSecSale);
						 
						 ps1.setString(1, strDcNo);
						 ps1.setLong(2, distDto.getDistID());
						 ps1.setString(3, strSrNo);
						 ps1.setString(4, strProdId[i]);
						 //Added by Shilpa Khanna 23-09-2011
						 ps1.setInt(5, distDto.getCircleId());
						 
						 ps4.setString(1, strSrNo);
						 ps4.setString(2, strProdId[i]);
						 ps4.setLong(3, distDto.getDistID());
						 
						 ps.executeUpdate();				
						 ps3.executeUpdate();
						 ps1.executeUpdate();
						 ps4.executeUpdate();
					}
					
				}
				//Added by SHilpa Khanna on 29-06-12 for Releasing POs
				//String allSerialNo = sr_no_list.replaceAll("#", "");
				
				if(arrReleaseSerialNo.size()>0){
					//allSerialNo = "'" + allSerialNo+"'";
					//allSerialNo = allSerialNo.replaceAll(",", "','");
					//logger.info("Final Serial No List with ' , '  ==== "+allSerialNo);
					logger.info("Calling releaseSTB function in STBFlushOutDaoImpl, Arraylist Size == "+arrReleaseSerialNo.size());
					
					STBFlushOutDaoImpl relesePoObj = new STBFlushOutDaoImpl(con);
					relesePoObj.releaseSTB(arrReleaseSerialNo);
			
					logger.info("Exit from releaseSTB function in STBFlushOutDaoImpl");
				}
				
				
				
				 
				  
				 con.commit();
				 
				 /*
				 
				while(dcCreationDtoListItr.hasNext()) 
				{	
					 dcCreationDto = dcCreationDtoListItr.next();
					// strDcNo = dcCreationDto.getStrDCNo();
					 strSrNo = dcCreationDto.getStrSerialNo();
					 strProductId = dcCreationDto.getProdId();
					 //strRemarks = dcCreationDto.getStrNewRemarks();
					
					  //To Select Stock Inverntory Detail
					 ps3.setString(1, strProductId);
					 ps3.setString(2, strSrNo);
					 ps3.setString(3, strDistId);
					 rset = ps3.executeQuery();
					 while(rset.next()){
						 strCollectionId = rset.getString("COLLECTION_ID");
						 strDefectId = rset.getString("DEFECT_ID");
						 strCircleId = rset.getString("CIRCLE_ID");
						 dtCollectedOn = rset.getString("CREATED_ON");
					 }
					 
					 
					 //To insert values in DP_DELIVERY_CHALAN_DETAIL
					 
					 ps.setString(1, strDcNo);
					 ps.setString(2, strDistId);
					 ps.setString(3, strSrNo);
					 ps.setString(4, strProductId);
					 ps.setString(5, strCollectionId);
					 ps.setString(6, strDefectId);
					 ps.setString(7, dtCollectedOn);
					 ps.setString(8, strCircleId);
					 ps.executeUpdate();
					 
					 
					 //To Update status= 'IDC' of DP_STOCK_INVENTORY_REVERSE 
					 ps2.setString(1, strDistId);
					 ps2.setString(2, strProductId);
					 ps2.setString(3, strSrNo);
					 ps2.setString(4, strDistId);
					 ps2.executeUpdate();
				}
				
				
				con.commit();*/
			}
			catch(SQLException sqle){
				try
				{
					String err = ResourceReader.getCoreResourceBundleValue(Constants.Excess_Stock_Full_Final_Critical);
					logger.info(err + "::" +Utility.getCurrentDate());
				}
				catch(Exception ex)
				{
					ex.printStackTrace();
				}
				try {
					con.rollback();
				} catch (SQLException e) {
					
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				sqle.printStackTrace();
				
				throw new DAOException("SQLException: " + sqle.getMessage());
			}catch(Exception e){
				e.printStackTrace();
				try
				{
					String err = ResourceReader.getCoreResourceBundleValue(Constants.Excess_Stock_Full_Final_Critical);
					logger.info(err + "::" +Utility.getCurrentDate());
				}
				catch(Exception ex)
				{
					ex.printStackTrace();
				}
				throw new DAOException("Exception: " + e.getMessage());
			}finally
			{
				ps = null;
				ps1=null;
				ps2=null;
				ps3=null;
				ps4=null;
				
			
			}
		return SDCNO;
			
		 
	}
	
	public ArrayList freshRecordsNewUpdatedSwap(DistStockDTO distDto) throws DAOException {

		PreparedStatement ps = null;
		PreparedStatement ps1 = null;
		PreparedStatement ps2 = null;
		PreparedStatement ps3 = null;
		PreparedStatement ps4 = null;
		PreparedStatement ps5 = null;
		
		ArrayList SDCNO = new ArrayList();
		StringBuilder sql = new StringBuilder();
		Connection con = null;
		ResultSet rset			= null;
		String strDcNo ="", strIsSecSale = "";
		String status="";
		try 
			{
				if(distDto.getCourierAgency()==null || distDto.getCourierAgency().equals("") || distDto.getDocketNum()==null || distDto.getDocketNum().equals("")){
					status = com.ibm.dp.common.Constants.PUSH_DC_TO_BOTREE_DRAFT_STATUS;
				}else{
					status =com.ibm.dp.common.Constants.PUSH_DC_TO_BOTREE_CREATED_STATUS;
				}
				con = DBConnectionManager.getDBConnection();	
				con.setAutoCommit(false);
				DcCreation dcCreation = DcCreation.getDcCreationObject();
				
				strDcNo = dcCreation.generateDcNo(con, Constants.DC_TYPE_SWAP_DOA);
				SDCNO.add(strDcNo);
				
					sql=new StringBuilder();
					sql.append(DBQueries.INSERT_DP_STOCK_INVENTORY_ASSIGNED_SWAP);
					ps = con.prepareStatement(sql.toString());
					
					sql=new StringBuilder();
					sql.append(DBQueries.INSERT_DP_REV_DELIVERY_CHALAN_DETAIL);
					ps1 = con.prepareStatement(sql.toString());
					
					sql=new StringBuilder();
					sql.append(DBQueries.INSERT_DP_REV_FRESH_STOCK);
					ps3 = con.prepareStatement(sql.toString());
					
					sql=new StringBuilder();
					sql.append(DBQueries.INSERT_DP_REV_DELIVERY_CHALAN_HDR);
					ps2  = con.prepareStatement(sql.toString());
					
					sql=new StringBuilder();
					sql.append(DBQueries.DELETE_FROM_STOCK_INVENTORY);
					ps4  = con.prepareStatement(sql.toString());
					
					sql=new StringBuilder();
					sql.append(DBQueries.SR_NO_SEC_SALE_STATUS);
					ps5  = con.prepareStatement(sql.toString());

					 ps2.setString(1, strDcNo);
					 ps2.setInt(2, distDto.getCircleId());
					 ps2.setLong(3, distDto.getDistID());
					 ps2.setString(4, status);
					 ps2.setString(5, distDto.getRemarks());
					 ps2.setString(6, distDto.getCourierAgency());
					 ps2.setString(7, distDto.getDocketNum());
					 ps2.setLong(8, distDto.getDistID());
					 ps2.executeUpdate(); 

				String sr_no_list = "";
				
				String[] strProdId = distDto.getJsArrprodId()[0].split(",");
				String[] strremark= distDto.getJsArrremarks()[0].split(",");
				
				sr_no_list = distDto.getJsSrNo()[0].substring(0,distDto.getJsSrNo()[0].length()-1);
				
				System.out.println("String Print all ---- == "+sr_no_list);
				String[] strsrNo= sr_no_list.split("#,");
				StringTokenizer stSrNo = null;
				String strSrNo="";
				ArrayList<String> arrReleaseSerialNo = new ArrayList<String>();
				for(int i=0; i< strProdId.length ; i++)
				{
					
					stSrNo = new StringTokenizer(strsrNo[i],",");
					while(stSrNo.hasMoreTokens())
					{
						strSrNo = (String) stSrNo.nextToken();
						arrReleaseSerialNo.add(strSrNo);
						 ps.setLong(1, distDto.getDistID());
						 ps.setString(2, strProdId[i]);
						 ps.setString(3, strSrNo);				 
						 
						 ps5.setString(1, strSrNo);
						 ps5.setString(2, strProdId[i]);
						 ps5.setLong(3, distDto.getDistID());
							DBConnectionManager.releaseResources(null, rset);
						 rset = ps5.executeQuery();
						 if(rset.next())
							 strIsSecSale = rset.getString(1);
						 
						 ps3.setLong(1, distDto.getDistID());
						 ps3.setString(2, strProdId[i]);
						 ps3.setString(3,strSrNo);
						 ps3.setString(4, strremark[i]);
						 ps3.setString(5, strIsSecSale);
						 
						 ps1.setString(1, strDcNo);
						 ps1.setLong(2, distDto.getDistID());
						 ps1.setString(3, strSrNo);
						 ps1.setString(4, strProdId[i]);
						 ps1.setInt(5, distDto.getCircleId());
						 
						 ps4.setString(1, strSrNo);
						 ps4.setString(2, strProdId[i]);
						 ps4.setLong(3, distDto.getDistID());
						 
						 ps.executeUpdate();				
						 ps3.executeUpdate();
						 ps1.executeUpdate();
						 ps4.executeUpdate();
					}
					
				}
				if(arrReleaseSerialNo.size()>0){
					logger.info("Calling releaseSTB function in STBFlushOutDaoImpl, Arraylist Size == "+arrReleaseSerialNo.size());
					
					STBFlushOutDaoImpl relesePoObj = new STBFlushOutDaoImpl(con);
					relesePoObj.releaseSTB(arrReleaseSerialNo);
			
					logger.info("Exit from releaseSTB function in STBFlushOutDaoImpl");
				}
					 con.commit();
			}
			catch(SQLException sqle){
				try
				{
					String err = ResourceReader.getCoreResourceBundleValue(Constants.Excess_Stock_Full_Final_Critical);
					logger.info(err + "::" +Utility.getCurrentDate());
				}
				catch(Exception ex)
				{
					ex.printStackTrace();
				}
				try {
					con.rollback();
				} catch (SQLException e) {
					
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				sqle.printStackTrace();
				
				throw new DAOException("SQLException: " + sqle.getMessage());
			}catch(Exception e){
				e.printStackTrace();
				try
				{
					String err = ResourceReader.getCoreResourceBundleValue(Constants.Excess_Stock_Full_Final_Critical);
					logger.info(err + "::" +Utility.getCurrentDate());
				}
				catch(Exception ex)
				{
					ex.printStackTrace();
				}
				throw new DAOException("Exception: " + e.getMessage());
			}finally
			{
				ps = null;
				ps1=null;
				ps2=null;
				ps3=null;
				ps4=null;
			}
		return SDCNO;
			
		 
	}
}