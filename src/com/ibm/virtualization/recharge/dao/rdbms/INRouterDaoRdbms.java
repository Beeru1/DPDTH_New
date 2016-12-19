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

import org.apache.log4j.Logger;

import com.ibm.virtualization.recharge.common.ExceptionCode;
import com.ibm.virtualization.recharge.dao.INRouterDao;
import com.ibm.virtualization.recharge.db.DBConnectionManager;
import com.ibm.virtualization.recharge.dto.INRouterServiceDTO;
import com.ibm.virtualization.recharge.exception.DAOException;

/**
 * This class provides the implementation for all the method declarations in
 * INRouterDao interface
 * 
 * @author Mohit Aggarwal
 */

public class INRouterDaoRdbms extends BaseDaoRdbms implements INRouterDao{

	private static Logger logger = Logger.getLogger(INRouterDaoRdbms.class);
	String GET_IN_ID_QUERY_KEY="GET_IN_ID_QUERY";
	protected static final String GET_IN_ID_QUERY = "SELECT config.EXTERNAL_ID,series.CIRCLE_ID,config.EXTERNAL_URL FROM VR_MOBILE_SERIES_CONFIG series,VR_MOBILE_SERIES_IN_MAPPING mapping,VR_EXTERNAL_CONFIG_MSTR config WHERE "
			+ " series.MOBILE_SERIES_ID=mapping.MOBILE_SERIES_ID AND mapping.EXTERNAL_ID=config.EXTERNAL_ID AND config.EXTERNAL_INT_TYPE=2 AND "
			+ " cast(series.PRIMARY_ID AS NUMBER) <= ? AND cast(series.SECONDARY_ID AS NUMBER) >= ? ";
	
//	protected Connection connection = null;

	/**
	 * Constructor to initialize connection
	 * 
	 * @param connection
	 */
	public INRouterDaoRdbms(Connection connection) {
		super(connection);
		queryMap.put(GET_IN_ID_QUERY_KEY,GET_IN_ID_QUERY);
	}
	
	/*
	 * (non-Javadoc)
	 * @see com.ibm.virtualization.recharge.dao.INRouterDao#getDestinationIn(java.lang.String)
	 */
	public INRouterServiceDTO getDestinationIn(String subscriberNumber) throws DAOException {
		INRouterServiceDTO dto = new INRouterServiceDTO();
		PreparedStatement preparedStatement = null;
		ResultSet rs = null;
		try {
			preparedStatement = connection.prepareStatement(queryMap.get(GET_IN_ID_QUERY_KEY));
			preparedStatement.setString(1, subscriberNumber);
			preparedStatement.setString(2, subscriberNumber);
			rs = preparedStatement.executeQuery();
			/*
			 * This is the identification of the in queue to put the message
			 * into.
			 */
			int externalId = -1;
			String airUrl = null;
			int circleId=-1;
			if (rs.next()) {
				circleId=rs.getInt("CIRCLE_ID");
				externalId = rs.getInt("EXTERNAL_ID");
				airUrl = rs.getString("EXTERNAL_URL");
			}
			if (externalId == -1) {
				/* External Id Not Found. */
				logger
						.error("External Config Id Could not be fetched/not found for subscriber no:"
								+ subscriberNumber);
				throw new DAOException(
						"External ID Not Present.");
			}
			if(circleId==-1)
			{
				logger.error("Circle does not exist for Present subscriber no :"
						+ subscriberNumber);
				throw new DAOException(ExceptionCode.Transaction.ERROR_TRANSACTION_NO_SUBS_CIRCLE);
			}
			if ("".equalsIgnoreCase(airUrl) || airUrl == null) {
				logger.error("Air Url Not Present for subscriber no :"
						+ subscriberNumber);
				throw new DAOException(
						"Air URL Could Not be Fetched or is null or empty.");
			}

			dto.setAirUrl(airUrl);
			dto.setExternalConfigId(externalId);
			dto.setSubsCircleId(circleId);
			logger.info("Ending INRouter Service..");
			return dto;
		} catch (Exception e) {
			logger.error("Exception occured in INRouterService."
					+ e.getMessage());
			throw new DAOException("Exception occured in INRouterService.");
		} finally {
			/** Close the statement,resultset */
			DBConnectionManager.releaseResources(preparedStatement, rs);
		}
	}
}
