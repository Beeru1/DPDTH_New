package com.ibm.dp.service.impl;

import java.sql.Connection;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

import com.ibm.dp.common.Constants;
import com.ibm.dp.dao.StockDeclarationDao;
import com.ibm.dp.dto.DistributorDTO;
import com.ibm.dp.dto.DistributorDetailsDTO;
import com.ibm.dp.exception.DPServiceException;
import com.ibm.dp.service.StockDeclarationService;
import com.ibm.virtualization.recharge.common.ResourceReader;
import com.ibm.virtualization.recharge.dao.DAOFactory;
import com.ibm.virtualization.recharge.db.DBConnectionManager;
/**
 * @author Mohammad Aslam
 */
public class StockDeclarationServiceImpl implements StockDeclarationService {

	Logger log = Logger.getLogger(StockDeclarationServiceImpl.class.getName());

	public List<DistributorDTO> getDistributors(String accountLavel, long accountId, long circleId) throws DPServiceException {
		Connection connection = null;
		List<DistributorDTO> distributors = new ArrayList<DistributorDTO>();
		try {
			connection = DBConnectionManager.getDBConnection();
			StockDeclarationDao sdd = DAOFactory.getDAOFactory(Integer.parseInt(ResourceReader.getCoreResourceBundleValue(Constants.DATABASE_TYPE)))
					.getStockDeclarationDao(connection);
			distributors = sdd.getDistributors(accountLavel, accountId, circleId);
		} catch (Exception e) {
			log.error("**-> getDistributors function of  StockDeclarationServiceImpl " + e);
			throw new DPServiceException(e.getMessage());
		} finally {
			/* Close the connection */
			DBConnectionManager.releaseResources(connection);
		}
		return distributors;
	}

	public List<DistributorDetailsDTO> getDistributorDetails(String accountLavel, String distributorId, long circleId, long loginUserID) throws DPServiceException {
		Connection connection = null;
		List<DistributorDetailsDTO> distributorDetailsList = new ArrayList<DistributorDetailsDTO>();
		try {
			connection = DBConnectionManager.getDBConnection();
			StockDeclarationDao sdd = DAOFactory.getDAOFactory(Integer.parseInt(ResourceReader.getCoreResourceBundleValue(Constants.DATABASE_TYPE)))
					.getStockDeclarationDao(connection);
			distributorDetailsList = sdd.getDistributorDetails(accountLavel, distributorId, circleId, loginUserID);
		} catch (Exception e) {
			log.error("**-> getDistributorDetails function of  StockDeclarationServiceImpl " + e);
			throw new DPServiceException(e.getMessage());
		} finally {
			/* Close the connection */
			DBConnectionManager.releaseResources(connection);
		}
		return distributorDetailsList;
	}
}
