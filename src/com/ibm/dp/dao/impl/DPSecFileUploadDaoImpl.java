package com.ibm.dp.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import org.apache.log4j.Logger;
import com.ibm.dp.common.DBQueries;
import com.ibm.dp.dao.DPSecFileUploadDao;
import com.ibm.hbo.exception.HBOException;
import com.ibm.utilities.ErrorCodes;
import com.ibm.virtualization.recharge.dao.rdbms.BaseDaoRdbms;
import com.ibm.virtualization.recharge.db.DBConnectionManager;

public class DPSecFileUploadDaoImpl extends BaseDaoRdbms  implements DPSecFileUploadDao {

	protected static final String SQL_INSERT_SEC_UPLOADFILE_KEY = "SQL_INSERT_SEC_UPLOADFILE";
	protected static final String SQL_INSERT_SEC_UPLOADFILE =DBQueries.INSERT_SEC_UPLOADFILE; 
		
	public DPSecFileUploadDaoImpl(Connection con) {
		super(con);

		queryMap.put(SQL_INSERT_SEC_UPLOADFILE_KEY, SQL_INSERT_SEC_UPLOADFILE);

	} 
	public static Logger logger = Logger.getRootLogger();
	private static final String CLASSNAME = "DPSecFileUploadDaoImpl";
	
	
	public int insertUploadFileInfo(int circleId, String fileName, int userId, String UploadfilePath) throws HBOException {
		
		String methodName = "insertBulkUploadData";
		int result = 0;
		
		Connection con = null;
		PreparedStatement pstmt = null;
						
		try {
			
			con = DBConnectionManager.getDBConnection();
			if(!con.isClosed()){
			
				pstmt = con.prepareStatement(queryMap.get(SQL_INSERT_SEC_UPLOADFILE_KEY));
				pstmt.setString(1, fileName);
				pstmt.setInt(2, userId);
				pstmt.setInt(3, circleId);
				pstmt.setString(4, UploadfilePath);
				pstmt.setInt(5, 1);//status is 1 
				pstmt.setString(6, "File Upload");
				
			result = pstmt.executeUpdate();
			con.commit();
		  }
		} catch (SQLException sqe) {
			logger.info("SQLException from insertData >> " + sqe);
				HBOException HBOException =
				new HBOException(
					CLASSNAME,
					methodName,
					sqe.getMessage(),
					ErrorCodes.SQLEXCEPTION);
					logger.error("SQL Exception :: " + HBOException);
			throw HBOException;
		} catch (Exception e) {
			logger.info("SQLException from insertData >> " + e);
				HBOException HBOException =
				new HBOException(
					CLASSNAME,
					methodName,
					e.getMessage(),
					ErrorCodes.SYSTEMEXCEPTION);
			logger.error("System Exception Exception :: " + HBOException);
			throw HBOException;
		} finally {
			try {
				if (pstmt != null||con != null)
						pstmt.close();
						pstmt= null;		
						con.close();
						con= null;
				} catch (SQLException sqle) {
							
			} catch (Exception e) {
				logger.info(
					"Exception in Finally from insertSDFData>> " + e);
				}
			}
		
		return result;
		}
	}	
