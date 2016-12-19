/*
 * Created on Sep 6, 2006
 *
 * The DAO is used to get the records to populate the excel for records present
 * in Success and Error tables after bulk upload process.
 */
package com.ibm.hbo.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import com.ibm.virtualization.recharge.db.DBConnectionManager;
import org.apache.log4j.Logger;
import com.ibm.hbo.common.DBConnection;
import com.ibm.hbo.common.DBQueries;
import com.ibm.hbo.common.HBOLibrary;
import com.ibm.hbo.dao.HBOUploadStatusDao;
/**
 * @author Parul
 *
 * The DAO is used to get the records to populate the excel for records present
 * in Success table after bulk upload process.
 */
public class HBOUploadStatusDaoImpl implements HBOUploadStatusDao {
	private static final Logger logger;

	static {
		logger = Logger.getLogger(HBOUploadStatusDaoImpl.class);
		
	}
	public String getStatus(int fileId, String fileType) {
		StringBuffer sb = new StringBuffer();
		Connection con = null;
		PreparedStatement pStmt = null;	
		ResultSet rst = null;
		String status = null;
		try {
			con = DBConnectionManager.getDBConnection();
			if (("SIM".equals(fileType)) || ("PROD".equals(fileType))) {
				sb.append(DBQueries.uploadStatus);
			}
			pStmt = con.prepareStatement(sb.toString());
			pStmt.setInt(1,fileId);
			rst = pStmt.executeQuery();
			while (rst.next()) {
				status = rst.getString("Status");	
			}
			

		} catch (SQLException SQLe) {
			logger.info("SQLException from getStatus >> " + SQLe);
			SQLe.printStackTrace();
		} catch (Exception e) {
			logger.info("Exception from getStatus  >> " + e);
			e.printStackTrace();
		} finally {
			try {
				try {
					if (rst != null)
						rst.close();
				} catch (SQLException sqle) {
				}
				try {
					if (pStmt != null)
						pStmt.close();
				} catch (SQLException sqle) {
				}
				con.close();
			} catch (Exception e) {
				logger.info("Exception in Finally from getStatus >> " + e);
				e.printStackTrace();
			}
		}
		return status;
	}

	public String getStatusDetails(int fileId, String fileType) {
			StringBuffer sb = new StringBuffer();
			Connection con = null;
			PreparedStatement pStmt = null;	
			ResultSet rst = null;
			String statusDetails = null;
			try {
				con = DBConnectionManager.getDBConnection();
				if (("SIM".equals(fileType)) || ("PROD".equals(fileType))) {
					sb.append(DBQueries.uploadStatusDetails);
				}
				pStmt = con.prepareStatement(sb.toString());
				pStmt.setInt(1, fileId);
				rst = pStmt.executeQuery();
				while (rst.next()) {
					statusDetails = rst.getString("Status_Details");	
				}
			} catch (SQLException SQLe) {
				logger.info("SQLException from getStatusDetails >> " + SQLe);
				SQLe.printStackTrace();
			} catch (Exception e) {
				logger.info("Exception from getStatusDetails  >> " + e);
				e.printStackTrace();
			} finally {
				try {
					try {
						if (rst != null)
							rst.close();
					} catch (SQLException sqle) {
					}
					try {
						if (pStmt != null)
							pStmt.close();
					} catch (SQLException sqle) {
					}
					con.close();
				} catch (Exception e) {
					logger.info("Exception in Finally from getStatusDetails >> " + e);
					e.printStackTrace();
				}
			}
			return statusDetails;
		}

}