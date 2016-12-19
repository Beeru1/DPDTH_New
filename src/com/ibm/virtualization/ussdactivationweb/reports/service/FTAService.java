/**************************************************************************
 * File     : FTAService.java
 * Author   : Banita
 * Created  : Nov 04, 2008
 * Modified : Nov 04, 2008
 * Version  : 0.1
 **************************************************************************
 *                               HISTORY
 **************************************************************************
 * V0.1		Nov 04, 2008 	Banita	First Cut.
 * V0.2		Nov 04, 2008 	Banita	First modification
 **************************************************************************
 *
 * Copyright @ 2002 This document has been prepared and written by 
 * IBM Global Services on behalf of Bharti, and is copyright of Bharti
 *
 **************************************************************************/
package com.ibm.virtualization.ussdactivationweb.reports.service;

import java.sql.Connection;

import org.apache.log4j.Logger;

import com.ibm.virtualization.ussdactivationweb.utils.DAOException;
import com.ibm.virtualization.ussdactivationweb.reports.dao.FTADataFetchServiceDAO;

/*******************************************************************************
 * All the methods in this class call their respective methods in DAO Class It
 * contain all FTA related methods.
 * 
 * @author Banita
 * @version 1.0
 ******************************************************************************/
public class FTAService {

	private static Logger logger = Logger.getLogger(FTAService.class.getName());

	/**
	 * This method getCurrentDate() calls the respective method of
	 * FTADataFetchServiceDAO
	 * 
	 * @return currentDate:current date in string
	 */

	public String getCurrentDate() {
		String currentDate = "";

		try {
			FTADataFetchServiceDAO ftadataFetchServiceDAO = new FTADataFetchServiceDAO();
			currentDate = ftadataFetchServiceDAO.getCurrentDate();

		} catch (DAOException e) {
			logger.error(e.getMessage(), e);
		}
		return currentDate;
	}

	/**
	 * This method getLastModifiedDate() calls the respective method of
	 * FTADataFetchServiceDAO
	 * 
	 * @param type :
	 *            filter to fetch the date
	 * 
	 * @return modifiedDate
	 */

	public String getLastModifiedDate(String type) {
		String modifiedDate = "";

		try {
			FTADataFetchServiceDAO ftadataFetchServiceDAO = new FTADataFetchServiceDAO();
			modifiedDate = ftadataFetchServiceDAO.getLastModifiedDate(type);

		} catch (DAOException e) {
			logger.error(e.getMessage(), e);
		}
		return modifiedDate;
	}

	/**
	 * This method updateLastModifiedDate() calls the respective method of
	 * FTADataFetchServiceDAO
	 * 
	 * @param connection
	 * @param type :
	 *            for which the data has to be updated
	 * @param currentDate :
	 *            update the data with this date
	 * 
	 */

	public void updateLastModifiedDate(Connection connection, String type,
			String currentDate) {

		try {
			FTADataFetchServiceDAO ftadataFetchServiceDAO = new FTADataFetchServiceDAO();
			ftadataFetchServiceDAO.updateLastModifiedDate(connection, type,
					currentDate);
		} catch (DAOException e) {
			logger.error(e.getMessage(), e);

		}
	}

}
