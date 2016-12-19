/*****************************************************************************\
 **
 ** Virtualization - Recharge.
 **
 ** Copyright (c) 2007-2008 IBM.
 ** All Rights Reserved
 **
 **
 \****************************************************************************/
package com.ibm.virtualization.recharge.dao.rdbms;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import org.apache.log4j.Logger;

import com.ibm.virtualization.recharge.common.Constants;
import com.ibm.virtualization.recharge.common.ExceptionCode;
import com.ibm.virtualization.recharge.dao.RegionDao;
import com.ibm.virtualization.recharge.db.DBConnectionManager;
import com.ibm.virtualization.recharge.dto.Region;
import com.ibm.virtualization.recharge.exception.DAOException;

/**
 * This class provides the implementation for all the method declarations in
 * RegionDao interface
 * 
 * @author Paras
 * 
 * 
 */
public class RegionDaoRdbms implements RegionDao {
	/* Logger for this class. */
	private static Logger logger = Logger.getLogger(RegionDaoRdbms.class
			.getName());

	/* SQL Used within DaoImpl */
	protected final static String SQL_REGION_SELECT_ACTIVE_REGION = "SELECT * FROM VR_REGION_MASTER WHERE STATUS = '"+Constants.ACTIVE_STATUS+"' ORDER BY REGION_NAME ";

	protected final static String SQL_REGION_SELECT_REGION_NAME = "SELECT REGION_NAME FROM VR_REGION_MASTER WHERE REGION_ID = ? ";

	private Connection connection = null;

	/**
	 * Constructor to initialize connection
	 * 
	 * @param connection
	 */
	public RegionDaoRdbms(Connection connection) {
		this.connection = connection;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.ibm.virtualization.recharge.dao.RegionDao#getActiveRegions()
	 */
	public ArrayList getActiveRegions() throws DAOException {
		logger.info("Started...");
		PreparedStatement ps = null;
		ResultSet rs = null;

		try {

			ps = connection.prepareStatement(SQL_REGION_SELECT_ACTIVE_REGION);
			rs = ps.executeQuery();
			ArrayList<Region> regionList = new ArrayList<Region>();
			while (rs.next()) {
				regionList.add(populateDto(rs));
			}
			logger.info("Executed ::::");
			return regionList;
		} catch (SQLException sqle) {

			logger.fatal(" SQL Exception occured while find."
					+ "Exception Message: " + sqle.getMessage(), sqle);
			throw new DAOException(ExceptionCode.ERROR_DB_INTERNAL);
		} finally {
			/* Close the resultset, statement. */
			DBConnectionManager.releaseResources(ps, rs);
		}

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.ibm.virtualization.recharge.dao.RegionDao#getRegionName(int)
	 */
	public String getRegionName(int regionId) throws DAOException {

		logger.info("Started...region id :" + regionId);

		PreparedStatement ps = null;
		ResultSet rs = null;

		try {

			ps = connection.prepareStatement(SQL_REGION_SELECT_REGION_NAME);
			ps.setInt(1, regionId);
			String regionName = null;
			rs = ps.executeQuery();

			if (rs.next()) {
				regionName = rs.getString("REGION_NAME");
			} else {
				logger.error("No Region with id = " + regionId);
				throw new DAOException(ExceptionCode.ERROR_NO_REGION);
			}
			return regionName;
		} catch (SQLException sqle) {

			logger.error(" SQL Exception occured while find."
					+ "Exception Message: " + sqle.getMessage(), sqle);
			throw new DAOException(ExceptionCode.ERROR_DB_INTERNAL);
		} finally {
			/* Close the resultset, statement. */
			DBConnectionManager.releaseResources(ps, rs);

		}

	}

	/**
	 * This is a generic method to set the data from Resultset object to
	 * Respective DTO object
	 * 
	 * @param rs
	 * @return region
	 * @throws SQLException
	 */
	private Region populateDto(ResultSet rs) throws SQLException {
		logger.info("Started...");
		Region region = new Region();
		region.setId(rs.getInt("REGION_ID"));
		region.setName(rs.getString("REGION_NAME"));
		region.setStatus(rs.getString("STATUS"));
		region.setCreatedBy(rs.getLong("CREATED_BY"));
		region.setUpdatedBy(rs.getInt("UPDATED_BY"));
		region.setUpdatedDateTime(rs.getString("UPDATED_DT"));
		region.setCreatedDt(rs.getString("CREATED_DT"));
		region.setDescription(rs.getString("DESCRIPTION"));
		logger.info("Executed ....");
		return region;
	}
}