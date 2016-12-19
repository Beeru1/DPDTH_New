/**
 * 
 */
package com.ibm.dp.cpedp;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.logging.Logger;

import com.ibm.dp.common.DBQueries;
import com.ibm.virtualization.recharge.common.ResourceReader;
import com.ibm.virtualization.recharge.db.DBConnectionManager;
import com.ibm.virtualization.recharge.exception.DAOException;
import com.ibm.virtualization.recharge.common.Constants;

/**
 * @author Harbans Singh Bisht
 *
 */
public class DPCPEDBInteractionImpl implements DPCPEDBInteraction 
{
	private static Logger logger = Logger.getLogger(DPCPEDBInteractionImpl.class.toString());
    
	private static String schemaName = ResourceReader.getValueFromBundle(Constants.DB_DPDTH_CPE_SCHEMA, Constants.WEBSERVICE_RESOURCE_BUNDLE);
	
	protected static final String SQL_SELECT_NOT_ASSIGNED_ASSETS_DTHDP = "SELECT SERIAL_NO  FROM DP_STOCK_INVENTORY WHERE ( STATUS IS NULL OR STATUS=1 OR STATUS <>3 )  AND RETAILER_ID IS NOT NULL AND RETAILER_PURCHASE_DATE IS NOT NULL ORDER BY RETAILER_PURCHASE_DATE with ur";
	protected static final String SQL_SELECT_ASSIGNED_ASSETS_CPE ="SELECT ASSET_SERIAL_NO FROM "+ schemaName +"CPE_INVENTORY_DETAILS WHERE CPE_INVENTORY_STATUS_KEY=3 AND ASSET_SERIAL_NO IN ";
	protected static final String SQL_UPDATE_NOT_ASSIGNED_ASSETS_DTHDP ="UPDATE DP_STOCK_INVENTORY SET STATUS = 3 WHERE SERIAL_NO IN  ";
	
	protected static final String SQL_SELECT_ASSIGNED_ASSETS_DTHDP = "SELECT SERIAL_NO  FROM DP_STOCK_INVENTORY WHERE STATUS IS NOT NULL AND STATUS = 3 AND RETAILER_ID IS NOT NULL AND RETAILER_PURCHASE_DATE IS NOT NULL ORDER BY RETAILER_PURCHASE_DATE  with ur";
	protected static final String SQL_SELECT_REPAIRED_ASSETS_CPE ="SELECT ASSET_SERIAL_NO FROM "+ schemaName+"CPE_INVENTORY_DETAILS WHERE CPE_INVENTORY_STATUS_KEY = 4 AND ASSET_SERIAL_NO IN ";
	protected static final String SQL_UPDATE_REPAIRED_ASSETS_DTHDP ="UPDATE DP_STOCK_INVENTORY SET STATUS = 4, MARK_DAMAGED='Y' WHERE SERIAL_NO IN ";
	
	protected static final String SQL_SELECT_REPAIRED_ASSETS_DTHDP = "SELECT SERIAL_NO  FROM DP_STOCK_INVENTORY WHERE STATUS IS NOT NULL AND STATUS = 4 AND RETAILER_ID IS NOT NULL AND RETAILER_PURCHASE_DATE IS NOT NULL ORDER BY RETAILER_PURCHASE_DATE  with ur";
	protected static final String SQL_SELECT_UNASSIGNED_ASSETS_CPE = "SELECT ASSET_SERIAL_NO FROM "+ schemaName+"CPE_INVENTORY_DETAILS WHERE CPE_INVENTORY_STATUS_KEY = 1 AND ASSET_SERIAL_NO IN ";
	protected static final String SQL_UPDATE_UNASSIGNED_ASSETS_DTHDP = "UPDATE DP_STOCK_INVENTORY SET STATUS = 1, MARK_DAMAGED='N' WHERE SERIAL_NO IN ";
		
	
	
	/*
	 * Check all assets which have sold in secondary sale in to CPE DB table.
	 * If there status updated to ‘assigned’ status then update these status to DTHDP DB table.
	 */
	public boolean updateAssignedStatus() throws Exception 
	{
		logger.info("Entered updateAssignedStatus() for table:FRESH_USER");

		Connection conDP = null;
		Connection conCPE = null;
		PreparedStatement psDP = null;
		ResultSet rsDP = null;
		PreparedStatement psCPE = null;
		ResultSet rsCPE = null;
				
		boolean resultStatus = false;
		List assestListSecSale = new ArrayList();
		List assestListAssigned = new ArrayList();
		try 
		{
			// Check all assets which sold in sec sale and do not have 'Assigned' status.
			String query1 = SQL_SELECT_NOT_ASSIGNED_ASSETS_DTHDP;
			conDP = DPCPEDBConnection.getDBConnectionDP();
			System.err.println("Open connection DP.");
			psDP = conDP.prepareStatement(query1);
			rsDP = psDP.executeQuery();
			while(rsDP.next()) 
			{
				assestListSecSale.add(rsDP.getString("SERIAL_NO"));
			}
			
			//Fetch all Assest No which have 'Assigned' status and with in given no from DTHDP
			if(assestListSecSale.size() > 0)
			{
				conCPE = DPCPEDBConnection.getDBConnectionCPE();
				System.err.println("Open connection CPE.");
				String query2 = SQL_SELECT_ASSIGNED_ASSETS_CPE;
				String partQuery = convertCommasString(assestListSecSale);
				query2 = query2 + "(" + partQuery + " )";
				System.err.println("Query for CPE DB assigne status :" + query2);
				
				psCPE = conCPE.prepareStatement(query2);
				rsCPE = psCPE.executeQuery();
				while(rsCPE.next()) 
				{
					assestListAssigned.add(rsCPE.getString("ASSET_SERIAL_NO"));
				}
			}
			
			
            // Update all Assest NO STATUS  in TO 'Assigned' DTHDB which have 'Assigned' status in CPE.  
			if(assestListAssigned.size() > 0)
			{
				//conDP = DBConnectionManager.getDBConnectionDP();
				String query3 = SQL_UPDATE_NOT_ASSIGNED_ASSETS_DTHDP;
				String partQuery = convertCommasString(assestListAssigned);
				query3 = query3 + "(" + partQuery + " )";
				System.err.println("Query update serial no status 'Assigned' in DTHDP:" + query3);
				
				psDP = conDP.prepareStatement(query3+ " with ur ");
				int updateRows = psDP.executeUpdate();
				System.err.println("No of update rows serial no status 'Assigned' in DTHDP:" + updateRows);
				conDP.commit();
			}
								
		} 
		catch (SQLException e) 
		{
			e.printStackTrace();
			logger.severe("SQL Exception occured while find."
					+ "Exception Message: " + e.getMessage());
			throw new Exception("errors.sqlexception", e);
		} catch (Exception e) {
			e.printStackTrace();
			logger.severe("Exception occured while find."
					+ "Exception Message: " + e.getMessage());
			throw new Exception("errors.genericexception", e);
		} finally {
			try {
							
				DPCPEDBConnection.releaseResources(conDP, psDP, rsDP);
				DPCPEDBConnection.releaseResources(conCPE, psCPE, rsCPE);
				
				
			} catch (Exception e) 
			{
				e.printStackTrace();
			}
		}
		return resultStatus;
	}
	
	
	
	/*
	 * Check all assets which have sold in secondary sale and having status 'Assigned' in DTHDP DB.
	 * and updated to ‘Repaired’ status, if they change there staus 'repaired' in CPE DB table.
	 */
	public boolean updateRepairedStatus() throws Exception 
	{
		logger.info("Entered updateRepairedStatus() for table:FRESH_USER");

		Connection conDP = null;
		Connection conCPE = null;
		PreparedStatement psDP = null;
		ResultSet rsDP = null;
		PreparedStatement psCPE = null;
		ResultSet rsCPE = null;
		
		
		boolean resultStatus = false;
		List assestListSecSale = new ArrayList();
		List assestListAssigned = new ArrayList();
		try 
		{
			// Check all assets which sold in sec sale and  have 'Assigned' status.
 			String query1 = SQL_SELECT_ASSIGNED_ASSETS_DTHDP;
			conDP = DPCPEDBConnection.getDBConnectionDP();
			System.err.println("Open connection DP.");
			psDP = conDP.prepareStatement(query1);
			rsDP = psDP.executeQuery();
			while(rsDP.next()) 
			{
				assestListSecSale.add(rsDP.getString("SERIAL_NO"));
			}
			
			//Fetch all Assest No which have 'REPAIRED' status and with in given no from DTHDP
			if(assestListSecSale.size() > 0)
			{
				conCPE = DPCPEDBConnection.getDBConnectionCPE();
				System.err.println("Open connection CPE.");
				String query2 = SQL_SELECT_REPAIRED_ASSETS_CPE;
				String partQuery = convertCommasString(assestListSecSale);
				query2 = query2 + "(" + partQuery + " )";
				System.err.println("Query for CPE DB REPAIRED status :" + query2);
				
				psCPE = conCPE.prepareStatement(query2);
				rsCPE = psCPE.executeQuery();
				while(rsCPE.next()) 
				{
					assestListAssigned.add(rsCPE.getString("ASSET_SERIAL_NO"));
				}
			}
			
			
            // Update all Assest NO STATUS  in to 'repaired' DTHDB which have 'repaired' status in CPE.  
			if(assestListAssigned.size() > 0)
			{
				//conDP = DBConnectionManager.getDBConnectionDP();
				String query3 = SQL_UPDATE_REPAIRED_ASSETS_DTHDP;
				String partQuery = convertCommasString(assestListAssigned);
				query3 = query3 + "(" + partQuery + " )";
				System.err.println("Query update serial no status 'repaired' in DTHDP:" + query3);
				
				psDP = conDP.prepareStatement(query3 + " with ur ");
				int updateRows = psDP.executeUpdate();
				conDP.commit();
				System.err.println("No of update rows serial no status 'repaired' in DTHDP:" + updateRows);
			}
			
			
		} 
		catch (SQLException e) 
		{
			e.printStackTrace();
			logger.severe("SQL Exception occured while find."
					+ "Exception Message: " + e.getMessage());
			throw new Exception("errors.sqlexception", e);
		} catch (Exception e) {
			e.printStackTrace();
			logger.severe("Exception occured while find."
					+ "Exception Message: " + e.getMessage());
			throw new Exception("errors.genericexception", e);
		} finally {
			try {
				DPCPEDBConnection.releaseResources(conDP, psDP, rsDP);
				DPCPEDBConnection.releaseResources(conCPE, psCPE, rsCPE);
		
				
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return resultStatus;
	}
	
	
	
	
	/*
	 * Check all assets which have sold in secondary sale and having status 'Repaired' in DTHDP DB.
	 * and updated to ‘Unassigned’ status, if they change there staus 'Unassigned' in CPE DB table.
	 */
	public boolean updateUnAssignedStatus() throws Exception 
	{
		logger.info("Entered updateRepairedStatus() for table:FRESH_USER");

		Connection conDP = null;
		Connection conCPE = null;
		PreparedStatement psDP = null;
		ResultSet rsDP = null;
		PreparedStatement psCPE = null;
		ResultSet rsCPE = null;
		
		
		boolean resultStatus = false;
		List assestListSecSale = new ArrayList();
		List assestListAssigned = new ArrayList();
		try 
		{
			// Check all assets which sold in sec sale and  have 'repaired' status.
 			String query1 = SQL_SELECT_REPAIRED_ASSETS_DTHDP;
			conDP = DPCPEDBConnection.getDBConnectionDP();
			System.err.println("Open connection DP.");
			psDP = conDP.prepareStatement(query1);
			rsDP = psDP.executeQuery();
			while(rsDP.next()) 
			{
				assestListSecSale.add(rsDP.getString("SERIAL_NO"));
			}
			
			//Fetch all Assest No which have 'UnAssigned' status and with in given no from DTHDP
			if(assestListSecSale.size() > 0)
			{
				conCPE = DPCPEDBConnection.getDBConnectionCPE();
				System.err.println("Open connection CPE.");
				String query2 = SQL_SELECT_UNASSIGNED_ASSETS_CPE;
				String partQuery = convertCommasString(assestListSecSale);
				query2 = query2 + "(" + partQuery + " )";
				System.err.println("Query for CPE DB REPAIRED status :" + query2);
				
				psCPE = conCPE.prepareStatement(query2);
				rsCPE = psCPE.executeQuery();
				while(rsCPE.next()) 
				{
					assestListAssigned.add(rsCPE.getString("ASSET_SERIAL_NO"));
				}
			}
			
			
            // Update all Assest NO STATUS  in to 'repaired' DTHDB which have 'repaired' status in CPE.  
			if(assestListAssigned.size() > 0)
			{
				//conDP = DBConnectionManager.getDBConnectionDP();
				String query3 = SQL_UPDATE_UNASSIGNED_ASSETS_DTHDP;
				String partQuery = convertCommasString(assestListAssigned);
				query3 = query3 + "(" + partQuery + " )";
				System.err.println("Query update serial no status 'Unassigned' in DTHDP:" + query3);
				
				psDP = conDP.prepareStatement(query3+ " with ur ");
				int updateRows = psDP.executeUpdate();
				conDP.commit();
				System.err.println("No of update rows serial no status 'Unassigned' in DTHDP:" + updateRows);
			}
		} 
		catch (SQLException e) 
		{
			e.printStackTrace();
			logger.severe("SQL Exception occured while find."
					+ "Exception Message: " + e.getMessage());
			throw new Exception("errors.sqlexception", e);
		} catch (Exception e) {
			e.printStackTrace();
			logger.severe("Exception occured while find."
					+ "Exception Message: " + e.getMessage());
			throw new Exception("errors.genericexception", e);
		} finally {
			try {
				DPCPEDBConnection.releaseResources(conDP, psDP, rsDP);
				DPCPEDBConnection.releaseResources(conCPE, psCPE, rsCPE);
						
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return resultStatus;
	}
	
//	 Add by harbans
	public String getControlParameterFlag(String paramName) throws DAOException
	{
		Connection con = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		String flag = "N";
		try 
		{
			con = DBConnectionManager.getDBConnection(); // db2
			ps = con.prepareStatement(DBQueries.GET_CONTROL_PARAM_VALUE);
			ps.setString(1, paramName);
			rs = ps.executeQuery();
			
			if(rs.next())
			{
				flag = rs.getString("PARAM_FLAG");
			}
			
			// Add by harbans 11th May
			con.commit();
			
		}catch(SQLException sqle){
			sqle.printStackTrace();
			throw new DAOException("SQLException: " + sqle.getMessage());
		}catch(Exception e){
			e.printStackTrace();
			throw new DAOException("Exception: " + e.getMessage());
		}finally{
			try {
				DBConnectionManager.releaseResources(ps, rs);
				DBConnectionManager.releaseResources(con);
				
			} catch (Exception e) {
				e.printStackTrace();
				logger.info("Error Occured in ReportDAOImpl "+e.getMessage());
			}
		}
		
		return flag;
	}
	
	
	
	
	
//	 Return string values with comma separated. 
	private String convertCommasString(List assestList) 
	{
		String str = ",";
		Iterator iter = assestList.iterator();
		boolean flagFirst = true;
		
		while(iter.hasNext())
		{
			if(flagFirst)
				str = "'" + (String)iter.next() + "'";  // '1'
			else
				str = str + "," + "'" +(String)iter.next() + "'"; 
			
			flagFirst = false;
		}
		
		return str; 
	}
	
}
