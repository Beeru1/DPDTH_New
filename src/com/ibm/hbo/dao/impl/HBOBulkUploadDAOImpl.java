/*
 * Created on Jun 15, 2006
 *
 * To change the template for this generated file go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
package com.ibm.hbo.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import org.apache.log4j.Logger;

import com.ibm.core.exception.DAOException;
import com.ibm.virtualization.recharge.db.DBConnectionManager;
import com.ibm.hbo.common.DBConnection;
import com.ibm.hbo.common.DBQueries;
import com.ibm.hbo.common.HBOLibrary;
import com.ibm.hbo.dao.HBOBulkUploadDAO;
import com.ibm.hbo.dto.HBOProjectionBulkUploadDTO;
import com.ibm.hbo.dto.HBOStockDTO;
import com.ibm.hbo.dto.HBOUserBean;
import com.ibm.hbo.exception.HBOException;
import com.ibm.hbo.forms.HBOFileUploadFormBean;
import com.ibm.utilities.ErrorCodes;

/**
 * @author Parul
 * To change the template for this generated type comment go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
public class HBOBulkUploadDAOImpl implements HBOBulkUploadDAO {

	public static Logger logger = Logger.getRootLogger();
	private static final String CLASSNAME = "HBOBulkUploadDAOImpl";
	public int insertBulkUploadData(
			int circleId,
			String fileName,
			int userId,
			String UploadfilePath,
			String fileType)
			throws HBOException {
			String methodName = "insertBulkUploadData";
			int result = 0;
			Connection con = null;
			PreparedStatement pstmt = null;
			ResultSet rst = null;
			StringBuffer strSQL = new StringBuffer();
			StringBuffer sql = new StringBuffer();
			int fileId =0; 	
			try {
				con = DBConnectionManager.getDBConnection();
				sql.append(DBQueries.BulkFileSeq);
				pstmt = con.prepareStatement(sql.toString());
				rst=pstmt.executeQuery();
				if (rst.next()){
					fileId = rst.getInt("lfileId");
				}
				strSQL.append(DBQueries.InsertBulkUploadFile);
				pstmt = con.prepareStatement(strSQL.toString());
				pstmt.setInt(1, fileId);
				pstmt.setString(2, fileName);
				pstmt.setInt(3, userId);
				pstmt.setInt(4, circleId);
				pstmt.setString(5, "N");
				pstmt.setString(6, fileType);
				pstmt.setString(7, UploadfilePath);			
				result = pstmt.executeUpdate();
				con.commit();
			} catch (SQLException sqe) {
				logger.info("SQLException from insertData >> " + sqe);
				sqe.printStackTrace();
				HBOException HBOException =
					new HBOException(
						CLASSNAME,
						methodName,
						sqe.getMessage(),
						ErrorCodes.SQLEXCEPTION);
				sqe.printStackTrace();
				logger.error("SQL Exception :: " + HBOException);
				throw HBOException;
			} catch (Exception e) {
				logger.info("SQLException from insertData >> " + e);
				e.printStackTrace();
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
					strSQL = null;
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
					logger.info(
						"Exception in Finally from insertSDFData>> " + e);
					e.printStackTrace();
				}
			}
			return fileId;
		}

	
	public ArrayList getBulkUploadData(String circleId,	HBOFileUploadFormBean fileUploadFormBean,int userId,	String fileType)throws HBOException {
		String methodName = "getBulkUploadData";
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rst = null;
		StringBuffer strSQL = new StringBuffer();
		ArrayList uploadFileList = new ArrayList();
		try {
			con = DBConnectionManager.getDBConnection();
			if (fileType.equalsIgnoreCase("SIM")){
				strSQL.append(DBQueries.getUploadedSimFileList);				
				strSQL.append("date(dp.char_to_timestamp('"+fileUploadFormBean.getToDate1()+"','DD-MMM-YYYY')) ORDER BY bulkupload.UPLOADED_DT desc");
				pstmt = con.prepareStatement(strSQL.toString());
				pstmt.setInt(1, Integer.parseInt(circleId));						
				pstmt.setInt(2, userId);
				pstmt.setString(3, fileUploadFormBean.getFileType());
			//	pstmt.setString(4, fileUploadFormBean.getToDate1());	
			}
			if (fileType.equalsIgnoreCase("PROD")){
				strSQL.append(DBQueries.getUploadedHandsetFileList);
				strSQL.append( "date(dp.char_to_timestamp('"+fileUploadFormBean.getToDate1()+"','DD-MMM-YYYY')) ORDER BY bulkupload.UPLOADED_DT DESC");
				pstmt = con.prepareStatement(strSQL.toString());														
				pstmt.setInt(1, userId);
				pstmt.setString(2, fileUploadFormBean.getFileType());
				//pstmt.setString(3, fileUploadFormBean.getToDate1());
			}		
   	        rst = pstmt.executeQuery();
			int i=0;
			while (rst.next()) {
				HBOFileUploadFormBean fileUploadFormData =	new HBOFileUploadFormBean();
				fileUploadFormData.setFileId(rst.getInt("FILE_ID"));
				fileUploadFormData.setRealFileName(rst.getString("FILE_NAME"));
				fileUploadFormData.setFileName(rst.getString("FILE_NAME"));
				fileUploadFormData.setUploadedby(rst.getString("LOGINID"));
				fileUploadFormData.setSuccessFile("Suc" + fileUploadFormData.getFileName());
				fileUploadFormData.setErrorFile("Err" + fileUploadFormData.getFileName());
				fileUploadFormData.setRealSuccessFile("Success/"+ "Suc"+ fileUploadFormData.getRealFileName());
				fileUploadFormData.setRealErrorFile("Error/"+ "Err"+ fileUploadFormData.getRealFileName());

				fileUploadFormData.setUploadedDate(	rst.getString("UPLOADEDDATE"));
				fileUploadFormData.setProcessedStatus(rst.getString("PROCESSED_STATUS"));
				logger.info("PROCESSED_STATUS--------"+rst.getString("PROCESSED_STATUS"));
				fileUploadFormData.setProcessedDate(rst.getString("PROCESSEDDATE"));
				if ("Y".equals(rst.getString("PROCESSED_STATUS"))) {
					fileUploadFormData.setFilePath(rst.getString("FILE_PATH") + "done/");
					fileUploadFormData.setSuccErrPath(rst.getString("FILE_PATH"));
				} else {
					fileUploadFormData.setFilePath(rst.getString("FILE_PATH"));
				}
				i++;
				logger.info("i========="+i);
				uploadFileList.add(fileUploadFormData);
			}
		} catch (SQLException sqe) {
			logger.info("SQLException from getBulkUploadData >> " + sqe);
			sqe.printStackTrace();
			HBOException HBOException =
				new HBOException(
					CLASSNAME,
					methodName,
					sqe.getMessage(),
					ErrorCodes.SQLEXCEPTION);
			sqe.printStackTrace();
			logger.error("SQL Exception :: " + HBOException);
			throw HBOException;
		} catch (Exception e) {
			logger.info("SQLException from getBulkUploadData >> " + e);
			e.printStackTrace();
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
				strSQL = null;
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
				logger.info(
					"Exception in Finally from getBulkUploadData>> " + e);
				e.printStackTrace();
			}
		}
		return uploadFileList;
	}
	
	
	public int insertBulkUploadData(
			int circleId,
			String fileName,
			String changedFileName,
			int userId,
			String UploadfilePath,
			String fileType)
			throws HBOException {
			String methodName = "insertBulkUploadData";
			int result = 0;
			Connection con = null;
			PreparedStatement pstmt = null;
			ResultSet rst = null;
			StringBuffer strSQL = new StringBuffer();
			StringBuffer sql = new StringBuffer();
			int fileId =0; 	
			try {
				con = DBConnectionManager.getDBConnection();
				sql.append(DBQueries.BulkFileSeq);
				pstmt = con.prepareStatement(sql.toString());
				rst=pstmt.executeQuery();
				if (rst.next()){
					fileId = rst.getInt("lfileId");
				}
				strSQL.append(DBQueries.HLRBulkUploadFile);
				pstmt = con.prepareStatement(strSQL.toString());
				pstmt.setInt(1, fileId);
				pstmt.setString(2, fileName);
				pstmt.setString(3, changedFileName);
				pstmt.setInt(4, userId);
				pstmt.setInt(5, circleId);
				pstmt.setString(6, "N");
				pstmt.setString(7, fileType);
				pstmt.setString(8, UploadfilePath);			
				result = pstmt.executeUpdate();
				con.commit();
			} catch (SQLException sqe) {
				logger.info("SQLException from insertData >> " + sqe);
				sqe.printStackTrace();
				HBOException HBOException =
					new HBOException(
						CLASSNAME,
						methodName,
						sqe.getMessage(),
						ErrorCodes.SQLEXCEPTION);
				sqe.printStackTrace();
				logger.error("SQL Exception :: " + HBOException);
				throw HBOException;
			} catch (Exception e) {
				logger.info("SQLException from insertData >> " + e);
				e.printStackTrace();
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
					strSQL = null;
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
					logger.info(
						"Exception in Finally from insertSDFData>> " + e);
					e.printStackTrace();
				}
			}
			return fileId;
		}
	

}
