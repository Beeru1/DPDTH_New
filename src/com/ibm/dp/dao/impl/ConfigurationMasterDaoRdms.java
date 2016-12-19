package com.ibm.dp.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.apache.log4j.Logger;

import com.ibm.dp.dao.ConfigurationMasterDao;
import com.ibm.dp.dto.ConfigurationMaster;
import com.ibm.virtualization.recharge.common.ExceptionCode;
import com.ibm.virtualization.recharge.db.DBConnectionManager;
import com.ibm.virtualization.recharge.exception.DAOException;

public class ConfigurationMasterDaoRdms  implements ConfigurationMasterDao{

	/* Logger for this class. */
	private static Logger logger = Logger.getLogger(ConfigurationMasterDaoRdms.class
			.getName());

	/* SQL Used within DaoImpl */
	
	
	private Connection connection = null;
	

	public ConfigurationMasterDaoRdms(Connection connection) {
		this.connection = connection;
	}
	
	@Override
	public void insertConfigDetail(ConfigurationMaster configMaster)
			throws DAOException {
		// TODO Auto-generated method stub
		
		PreparedStatement ps = null;
		ResultSet rs = null;
		String SQL="";

 		int rowsUpdated = 0;
		try {
			
			SQL="INSERT INTO DP_CONFIGURATION_MASTER(CONFIG_ID,CONFIG_NAME,CONFIG_VALUE,CONFIG_DESC) VALUES (?, ?, ?, ?)";
			//System.out.println("SQL----------------"+SQL);
			ps = connection.prepareStatement(SQL);
			int paramCount = 1;
			ps.setString(paramCount++, configMaster.getConfigId());
			ps.setString(paramCount++, configMaster.getConfigName());
			ps.setString(paramCount++,configMaster.getConfigValue()) ;
			ps.setString(paramCount++,configMaster.getConfigDesc()) ;
			
			try{
			rowsUpdated = ps.executeUpdate();
			}
			catch(Exception e)
			{
				
			}
			//logger.info(" Row insertion successful on table: Inserted:"
				//	+ rowsUpdated + " rows");
		} catch (SQLException e) {
			logger.fatal(
					"insert configuration : SQL Exception occured while inserting."
							+ "Exception Message: ", e);
			throw new DAOException(
					ExceptionCode.ConfigurationMaster.ERROR_SQL_EXEPTION);
		} finally {
			/* Close the resultset, statement. */
			DBConnectionManager.releaseResources(ps, rs);

		}
		//logger.info("Executed .... ");
		
		
		
	}

	@Override
	public ConfigurationMaster getConfigDetails(String configId) throws DAOException {
		// TODO Auto-generated method stub
		
		
		PreparedStatement ps = null;
		ResultSet rs = null;
		String SQL="";
		ConfigurationMaster configMaster = new ConfigurationMaster();

		try {
			
			SQL="SELECT CONFIG_ID,CONFIG_NAME,CONFIG_VALUE,CONFIG_DESC from DP_CONFIGURATION_MASTER where CONFIG_ID=?";
			//System.out.println("SQL----------------"+SQL);
			ps = connection.prepareStatement(SQL);
			ps.setString(1, configId);
			rs = ps.executeQuery();
			if(rs.next())
			{
				configMaster.setConfigId(rs.getString("CONFIG_ID"));
				configMaster.setConfigName(rs.getString("CONFIG_NAME"));
				configMaster.setConfigValue(rs.getString("CONFIG_VALUE"));
				configMaster.setConfigDesc(rs.getString("CONFIG_DESC"));
				return configMaster;
			}
			
		} catch (SQLException e) {
			logger.fatal(
					"getconfig details : SQL Exception occured while inserting."
							+ "Exception Message: ", e);
			throw new DAOException(
					ExceptionCode.ConfigurationMaster.ERROR_SQL_EXEPTION);
			
		} finally {
			/* Close the resultset, statement. */
			DBConnectionManager.releaseResources(ps, rs);

		}
		return configMaster;
	}

	@Override
	public void updateConfigDetails(ConfigurationMaster configMaster) throws DAOException {
		// TODO Auto-generated method stub
		
		PreparedStatement ps = null;
		ResultSet rs = null;
		String SQL="";

 		int rowsUpdated = 0;
		try {
			
			SQL="UPDATE DP_CONFIGURATION_MASTER SET CONFIG_NAME=? ,CONFIG_VALUE=? ,CONFIG_DESC=? WHERE CONFIG_ID=?";
			//System.out.println("SQL----------------"+SQL);
			ps = connection.prepareStatement(SQL);
			int paramCount = 1;
			
			ps.setString(paramCount++, configMaster.getConfigName());
			ps.setString(paramCount++,configMaster.getConfigValue()) ;
			ps.setString(paramCount++,configMaster.getConfigDesc()) ;
			ps.setString(paramCount++, configMaster.getConfigId());
			
			rowsUpdated = ps.executeUpdate();
					} catch (SQLException e) {
			logger.fatal(
					"UPDATE CONFIG DETAILS : SQL Exception occured while Update."
							+ "Exception Message: ", e);
			throw new DAOException(
					ExceptionCode.ConfigurationMaster.ERROR_SQL_EXEPTION);
		} finally {
			/* Close the resultset, statement. */
			DBConnectionManager.releaseResources(ps, rs);

		}
	}

	
	
}
