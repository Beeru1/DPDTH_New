package com.ibm.dp.dao.impl;


import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.StringTokenizer;

import org.apache.log4j.Logger;

import com.ibm.dp.common.Constants;
import com.ibm.dp.dao.TransactionAuditDao;
import com.ibm.hbo.dto.TransactionAuditDTO;
import com.ibm.hbo.exception.DAOException;
import com.ibm.utilities.Utility;
import com.ibm.virtualization.recharge.common.ResourceReader;
import com.ibm.virtualization.recharge.dao.rdbms.BaseDaoRdbms;
import com.ibm.virtualization.recharge.db.DBConnectionManager;


public class TransactionAuditDaoImpl  extends BaseDaoRdbms implements  TransactionAuditDao {
	private static Logger logger = Logger.getLogger(TransactionAuditDaoImpl.class);	
	private static TransactionAuditDao  transactionAuditDao  = new TransactionAuditDaoImpl();
	
	public static final String insertHISTORY="insert INTO SECONDARY_SALES_HISTORY(SERIALNO, PRODUCTID, FROMACCOUNT, TOACCOUNT, CREATEDON, TRANSACTIONTYPE)  Values(?,?,?,?,current timestamp,?) WITH UR";
	
	
	private TransactionAuditDaoImpl(){}
	public TransactionAuditDaoImpl(Connection con) {
		super(con);
	}
	

	public void insertintoHistory(TransactionAuditDTO secondaryDto) throws DAOException {
		// TODO Auto-generated method stub

		Connection con = null;
		PreparedStatement ps = null;
		
		int rowsUpdated=0;
		
		
		try {
			con = DBConnectionManager.getDBConnection();
			
			
					ps = con.prepareStatement(insertHISTORY);
					ps.setInt(2, secondaryDto.getProductId());
					ps.setInt(3, secondaryDto.getAccountFrom());
					ps.setInt(4, secondaryDto.getAccountTo());
					//ps.setDate(5, null);
					ps.setString(5, secondaryDto.getTransactionType());
					
					//rajiv jha add serial nos in batch start
					StringTokenizer Tok = new StringTokenizer(secondaryDto.getSerialNo(),",");
					 if(Tok.countTokens() > 1)
					 {
						 while (Tok.hasMoreElements())
						 {
							 ps.setString(1, Tok.nextToken());//starting serial no contains array of serial nos
							 ps.addBatch();
						 }
					 }
					 else
					 {
						 ps.setString(1, secondaryDto.getSerialNo());//starting serial no contains array of serial nos
						 ps.addBatch();
						 
					 }
					int[] numRows;
					numRows = ps.executeBatch();
					rowsUpdated =  numRows.length;				
				

			con.commit();
		} catch (SQLException e) {
			System.out.println(e);
			try
			{
				String err = ResourceReader.getCoreResourceBundleValue(Constants.Return_To_Fse_Critical);
				logger.info(err + "::" +Utility.getCurrentDate());
			}
			catch(Exception expn)
			{
				expn.printStackTrace();
			}
			e.printStackTrace();
			try {
				con.rollback();
			} catch (Exception e1) {}
			e.printStackTrace();
			
			throw new DAOException("Exception: " + e.getMessage(), e);
		} catch (Exception e) {
			try
			{
				String err = ResourceReader.getCoreResourceBundleValue(Constants.Return_To_Fse_Critical);
				logger.info(err + "::" +Utility.getCurrentDate());
			}
			catch(Exception expn)
			{
				expn.printStackTrace();
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
				catch(Exception expn)
				{
					expn.printStackTrace();
				}
			}
		}
	
		
		
	}
	
}
