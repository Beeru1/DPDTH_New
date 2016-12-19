/**
 * 
 */
package com.ibm.virtualization.recharge.service.impl;

import java.sql.Connection;

import org.apache.log4j.Logger;

import com.ibm.virtualization.recharge.dao.INRouterDao;
import com.ibm.virtualization.recharge.dao.rdbms.INRouterDaoRdbmsDB2;
import com.ibm.virtualization.recharge.db.DBConnectionManager;
import com.ibm.virtualization.recharge.dto.INRouterServiceDTO;
import com.ibm.virtualization.recharge.exception.DAOException;
import com.ibm.virtualization.recharge.exception.VirtualizationServiceException;
import com.ibm.virtualization.recharge.service.INRouterService;

/**
 * @author puneetla
 * 
 */
public class INRouterServiceImpl implements INRouterService {
	private static Logger logger = Logger.getLogger(INRouterServiceImpl.class);

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.ibm.virtualization.recharge.service.INRouterService#getDestinationIn(java.lang.String)
	 */
	public INRouterServiceDTO getDestinationIn(String subscriberNumber)
			throws VirtualizationServiceException {
		
		if("".equals(subscriberNumber) || subscriberNumber == null){
			throw new VirtualizationServiceException("Subscriber No cannot be blank");
		}
		Connection connection = null;
		INRouterServiceDTO routerServiceDTO;
		try {
			/* get the database connection */
			connection = DBConnectionManager.getDBConnection();
			INRouterDao inRouterDao = new INRouterDaoRdbmsDB2(connection);
			routerServiceDTO = inRouterDao.getDestinationIn(subscriberNumber);
		} catch (DAOException de) {
			logger.error(" Exception occured : Message : " + de.getMessage());
			throw new VirtualizationServiceException(de.getMessage());
		} finally {
			DBConnectionManager.releaseResources(connection);
		}
		return routerServiceDTO;
	}
}
