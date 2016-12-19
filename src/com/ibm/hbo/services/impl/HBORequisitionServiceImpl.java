/*
 * Created on Nov 13, 2007
 *
 * To change the template for this generated file go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
package com.ibm.hbo.services.impl;

import java.sql.Connection;
import java.util.ArrayList;

import org.apache.log4j.Logger;

import com.ibm.hbo.common.HBOConstants;
import com.ibm.hbo.dao.HBOProductDAO;
import com.ibm.hbo.exception.DAOException;
import com.ibm.hbo.exception.HBOException;
import com.ibm.hbo.services.HBORequisitionService;
import com.ibm.virtualization.recharge.common.Constants;
import com.ibm.virtualization.recharge.common.ResourceReader;
import com.ibm.virtualization.recharge.dao.DAOFactory;
import com.ibm.virtualization.recharge.db.DBConnectionManager;

/**
 * @author Parul
 *
 * To change the template for this generated type comment go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
/**
 * @author Parul
 * @version 2.0
 * 
 */
public class HBORequisitionServiceImpl implements HBORequisitionService {

	public static Logger logger = Logger.getRootLogger();

	public String insert(ArrayList params) throws HBOException { 
		String message = HBOConstants.SUCCESS;
		Connection con = null;
		try{
			con = DBConnectionManager.getDBConnection();
			HBOProductDAO productDAO = DAOFactory.getDAOFactory(
	  		Integer.parseInt(ResourceReader.getCoreResourceBundleValue(Constants.DATABASE_TYPE))).getProductDao(con);
			message = productDAO.insert(params);
		}
		catch(Exception e){
			if (e instanceof DAOException) {
				logger.error(
					"DAOException occured while creating requisition:"
						+ e.getMessage());
				throw new HBOException(e.getMessage());
			} else {
				logger.error("DAOException occured while creating requisition:" + e.getMessage());
			}
			message = HBOConstants.ERROR_MESSAGE;
		}
		return message; 
	}
	
	public ArrayList getRequisitions(ArrayList params) throws HBOException {
		Connection con = null;
//		HBOProductDAO productDAO = new HBOProductDAOImpl(con);
		ArrayList requisitionList = null;
		try{
			con = DBConnectionManager.getDBConnection();
			HBOProductDAO productDAO = DAOFactory.getDAOFactory(
	  		Integer.parseInt(ResourceReader.getCoreResourceBundleValue(Constants.DATABASE_TYPE))).getProductDao(con);
			requisitionList = productDAO.getRequisitionData(params);
		}
		catch(Exception e){
			logger.info("Exception "+e);
		}
		return requisitionList;
	}
	
}