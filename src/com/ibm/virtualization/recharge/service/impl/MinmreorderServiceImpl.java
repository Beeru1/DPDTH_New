package com.ibm.virtualization.recharge.service.impl;

import java.sql.Connection;
import java.util.ArrayList;
import org.apache.log4j.Logger;
import com.ibm.virtualization.recharge.beans.MinReorderFormBean;
import com.ibm.virtualization.recharge.common.Constants;
import com.ibm.virtualization.recharge.common.ResourceReader;
import com.ibm.virtualization.recharge.dao.DAOFactory;
import com.ibm.virtualization.recharge.dao.MinReorderDao;
import com.ibm.virtualization.recharge.db.DBConnectionManager;
import com.ibm.virtualization.recharge.exception.DAOException;
import com.ibm.virtualization.recharge.exception.VirtualizationServiceException;
import com.ibm.virtualization.recharge.service.MinmreorderService;

public class MinmreorderServiceImpl  implements MinmreorderService{
	private Logger logger = Logger.getLogger(MinmreorderServiceImpl.class.getName());

	public ArrayList getDistributor(String userId) {
		java.sql.Connection connection = null;
		
		
		
		ArrayList dsAList = null;

		try {

			connection = DBConnectionManager.getDBConnection();
			MinReorderDao dpmdao = DAOFactory.getDAOFactory(Integer.parseInt(ResourceReader
									.getCoreResourceBundleValue(Constants.DATABASE_TYPE))).getDistributorDao(connection);

			dsAList = dpmdao.getDistributorDao(userId);
		
			if (dsAList.size() == 0) {
			
				logger.error(" No Distributor Category present ! ");
			}
			
		} catch (Exception ex) {
			logger	.error("ERROR IN FETCHING BUSINESS CATEGORY LIST [getDistributor(String userId) function  ");
		}

		logger	.info(" **: EXIT from getDistributor -> MinmreorderServiceImpl class :** ");
		return dsAList;
		
		
		
	}


	public ArrayList getProductList(String userId) {
java.sql.Connection connection = null;
		
		
		
		ArrayList plAList = null;

		try {

			connection = DBConnectionManager.getDBConnection();
			MinReorderDao dpmdao = DAOFactory.getDAOFactory(	Integer	.parseInt(ResourceReader
											.getCoreResourceBundleValue(Constants.DATABASE_TYPE))).getDistributorDao(connection);

			plAList = dpmdao.getProductListDao(userId);
		
			if (plAList.size() == 0) {
			
				logger.error(" No Product Category present ! ");
			}
			
		} catch (Exception ex) {
			logger	.error("ERROR IN FETCHING BUSINESS CATEGORY LIST [getBusinessCategory(String userId)] function  ");
		}

		logger	.info(" **: EXIT from getProductList -> MinmreorderServiceImpl class :** ");
		return plAList;
		
		
		
	}


	public String insert(MinReorderFormBean minmreodr) throws NumberFormatException, VirtualizationServiceException, DAOException {

		Connection connection = null;
	

	String message="successfully in DPMasterServiceImpl";
		try{
				connection = DBConnectionManager.getDBConnection();
				MinReorderDao masterDAO = DAOFactory.getDAOFactory(
  			    Integer.parseInt(ResourceReader.getCoreResourceBundleValue(Constants.DATABASE_TYPE))).getAssignedorderDao(connection);				
			 
			    
				masterDAO.insert(minmreodr);
		
				
		} catch (Exception e) {
			
			if (e instanceof DAOException) {
				logger.error("DAOException occured in getMasterist():" + e.getMessage());
				message="failure";
				logger.error(""+e.fillInStackTrace());
				throw new DAOException(e.getMessage());
				
			} else {
				logger.error("Exception occured in getMasterList():" + e.getMessage());
			}
			
			message="failure";
		}
		
		return message;
	}


//	public String Check(MinReorderFormBean minmreodr) throws NumberFormatException, VirtualizationServiceException, DAOException {
//		Connection connection = null;
//		
//
//		String message="successfull";
//			try{
//					connection = DBConnectionManager.getDBConnection();
//					MinReorderDao masterDAO = DAOFactory.getDAOFactory(
//	  			    Integer.parseInt(ResourceReader.getCoreResourceBundleValue(Constants.DATABASE_TYPE))).check(connection);				
//				 
//				    
//					masterDAO.check(minmreodr);
//			
//					
//			} catch (Exception e) {
//				
//				if (e instanceof DAOException) {
//					logger.error("DAOException occured in Checking:" + e.getMessage());
//					message="failure";
//					logger.error(""+e.fillInStackTrace());
//					throw new DAOException(e.getMessage());
//					
//				} else {
//					logger.error("Exception occured in Checking Assigned order:" + e.getMessage());
//				}
//				
//				message="failure";
//			}
//			
//			return message;
//		}
//
//
//
//	public String update(MinReorderFormBean minmreodr) throws NumberFormatException, VirtualizationServiceException, DAOException {
//		
//	Connection connection = null;
//		
//
//		String message="successfull_Update";
//			try{
//					connection = DBConnectionManager.getDBConnection();
//					MinReorderDao masterDAO = DAOFactory.getDAOFactory(
//	  			    Integer.parseInt(ResourceReader.getCoreResourceBundleValue(Constants.DATABASE_TYPE))).updateAssign(connection);				
//				 
//				    
//					masterDAO.updateAssign(minmreodr);
//			
//					
//			} catch (Exception e) {
//				
//				if (e instanceof DAOException) {
//					logger.error("DAOException occured in Updating assigned order:" + e.getMessage());
//					message="failure";
//					logger.error(""+e.fillInStackTrace());
//					throw new DAOException(e.getMessage());
//					
//				} else {
//					logger.error("Exception occured in Updating Assigned order:" + e.getMessage());
//				}
//				
//				message="failure";
//			}
//			
//			return message;
//		}

}
