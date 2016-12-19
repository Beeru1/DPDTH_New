package com.ibm.dp.service.impl;

import java.sql.Connection;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import org.apache.struts.upload.FormFile;

import com.ibm.dp.dao.DPStbInBulkDAO;
import com.ibm.dp.dao.impl.DPStbInBulkDAOImpl;
import com.ibm.dp.dto.DPSTBHistDTO;
import com.ibm.dp.dto.DPStbInBulkDTO;
import com.ibm.dp.service.DPStbInBulkService;
import com.ibm.hbo.exception.HBOException;
import com.ibm.virtualization.recharge.common.Constants;
import com.ibm.virtualization.recharge.common.ResourceReader;
import com.ibm.virtualization.recharge.dao.DAOFactory;
import com.ibm.virtualization.recharge.db.DBConnectionManager;
import com.ibm.virtualization.recharge.exception.DAOException;
import com.ibm.virtualization.recharge.exception.VirtualizationServiceException;

public class DPStbInBulkServiceImpl implements DPStbInBulkService {
	private Logger logger = Logger.getLogger(DPStbInBulkServiceImpl.class
			.getName());

	public List uploadExcel(FormFile myXls)
			throws VirtualizationServiceException {
		Connection connection = null;
		List list = new ArrayList();
		try {
			/* get the database connection */
			connection = DBConnectionManager.getDBConnection();
			DPStbInBulkDAO stbDAO = DAOFactory
					.getDAOFactory(
							Integer
									.parseInt(ResourceReader
											.getCoreResourceBundleValue(Constants.DATABASE_TYPE)))
					.getStbUploadExcel(connection);
			logger
					.info("**********************get the database connection******************.");
			list = stbDAO.uploadExcel(myXls);
		} catch (DAOException ex) {
			ex.printStackTrace();
			logger.info("Method uploadExcel in service.");
		} finally {
			// Releasing the database connection
			DBConnectionManager.releaseResources(connection);
		}
		return list;
	}
	
	//added by aman for STB history
	
	
	
	public List uploadExcelHistory(FormFile myXls)
	throws VirtualizationServiceException {
Connection connection = null;
List list = new ArrayList();
//System.out.println("*********************************in service impl (Upload excel History)*******************");
try {
	/* get the database connection */
	connection = DBConnectionManager.getDBConnection();
	DPStbInBulkDAO stbDAO = DAOFactory
			.getDAOFactory(
					Integer
							.parseInt(ResourceReader
									.getCoreResourceBundleValue(Constants.DATABASE_TYPE)))
			.getStbUploadExcelHistory(connection);
	logger
			.info("**********************get the database connection******************.");
	list = stbDAO.uploadExcelHistory(myXls);
} catch (DAOException ex) {
	ex.printStackTrace();
	logger.info("Method uploadExcelHistory in service.");
} finally {
	// Releasing the database connection
	DBConnectionManager.releaseResources(connection);
}
return list;
}
	
	
	
	
	//end of changes by aman

	public ArrayList getAvailableSerialNos(DPStbInBulkDTO dpStbInBulkDTO)
			throws HBOException {
		ArrayList serialNosList = new ArrayList();
		Connection con = null;
		DPStbInBulkDAO stockDAO = new DPStbInBulkDAOImpl(con);
		try 
		{
			logger.info("**********************stockDAO.getSerialNoList(dpStbInBulkDTO) --get serialNosList******************.");
			serialNosList = stockDAO.getSerialNoList(dpStbInBulkDTO);
			
		} catch (Exception e) {
			if (e instanceof DAOException) {
				logger.error("DAOException occured in getAvailableSerialNos"
						+ ":" + e.getMessage());
				throw new HBOException(e.getMessage());
			} else {
				logger.error("Exception occured in getAvailableSerialNos:"
						+ e.getMessage());
			}
		}

		return serialNosList;
	}
	
	public ArrayList<DPSTBHistDTO> getSerialNosHist(DPSTBHistDTO dpStbInBulkDTO)
	throws HBOException 
	{
		ArrayList serialNosList = new ArrayList();
		Connection con = null;
		DPStbInBulkDAO stockDAO = new DPStbInBulkDAOImpl(con);
		try 
		{
			logger.info("**********************stockDAO.getSerialNoList(dpStbInBulkDTO) --get serialNosList******************.");
			serialNosList = stockDAO.getSerialNoListHistory(dpStbInBulkDTO);
			System.out.println("serialNosList size in service imple::"+serialNosList.size());
		} catch (Exception e) {
			if (e instanceof DAOException) {
				logger.error("DAOException occured in getSerialNosHist"
						+ ":" + e.getMessage());
				throw new HBOException(e.getMessage());
			} else {
				logger.error("Exception occured in getSerialNosHist:"
						+ e.getMessage());
			}
		}
		
		return serialNosList;
	}

}