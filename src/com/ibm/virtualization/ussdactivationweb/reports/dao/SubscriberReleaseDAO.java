/**
 * 
 */
package com.ibm.virtualization.ussdactivationweb.reports.dao;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.Types;

import org.apache.log4j.Logger;

import com.ibm.virtualization.ussdactivationweb.reports.daoInterface.SubscriberReleaseDAOInterface;
import com.ibm.virtualization.ussdactivationweb.services.ProdcatService;
import com.ibm.virtualization.ussdactivationweb.services.dto.LocationDTO;
import com.ibm.virtualization.ussdactivationweb.services.dto.LocationMstDTO;
import com.ibm.virtualization.ussdactivationweb.utils.DAOException;
import com.ibm.virtualization.ussdactivationweb.utils.DBConnection;
import com.ibm.virtualization.ussdactivationweb.utils.DBConnectionUtil;
import com.ibm.virtualization.ussdactivationweb.utils.SQLConstants;

/**
 * @author a_gupta
 *
 */
public class SubscriberReleaseDAO {

	private static final Logger logger = Logger
	.getLogger(SubscriberReleaseDAO.class);
	
	
	public void releaseSubscriber() throws DAOException {
		logger.debug("Entering in SubscriberReleaseDAO : release() method ");
			CallableStatement cs = null;
			String procedureStatus = null;
			String procedureMsg = null;
			Connection connection=null;
			ProdcatService prodcatService = null;
			LocationMstDTO locMstrDTO = new LocationMstDTO();
			
		try {
			
			connection = DBConnection
			.getDBConnection(SQLConstants.FTA_JNDI_NAME);
			cs = connection
			.prepareCall(SubscriberReleaseDAOInterface.RELESE_SUBSCRIBER_PROCCALL);
			
			
			prodcatService = new ProdcatService();
			locMstrDTO=prodcatService.getAllActiveCircles();
			LocationDTO[] locationDTOs = locMstrDTO.getLocationArray();
			LocationDTO locationDTO = null;
			
			int arrayLength = 0;
			if(locationDTOs != null){
	            arrayLength = locationDTOs.length;
			}
			
			for(int i=0; i<arrayLength; i++){
				locationDTO = locationDTOs[i];
				if(locationDTO == null)
					continue;
				else{
					cs.setInt(1,Integer.parseInt(locationDTO.getReleaseTime()));
					cs.setString(2,locationDTO.getLocDataId());
					cs.registerOutParameter(3, Types.VARCHAR);
					cs.registerOutParameter(4, Types.VARCHAR);
					cs.execute();
					procedureStatus = cs.getString(3);
					procedureMsg = cs.getString(4);
					if (!"11111".equalsIgnoreCase(procedureStatus)) {
						logger.debug("Procedure failed reason code - "
								+ procedureStatus + ",Message =" + procedureMsg);
						throw new DAOException("Status =" + procedureStatus
								+ ", Message=" + procedureMsg);
					}
				}
			}
			
		}catch (DAOException e) {
			logger
			.error("SQLException occured  SubscriberReleaseDAO : release() method : "
					+ e);
			throw new DAOException("SQLException occured ",e);
		} catch(Exception e){
			logger
			.error("Exception Occured in SubscriberReleaseDAO : release() method : ",e);
		}
		finally{
		
				try {
					DBConnectionUtil.closeDBResources(connection, cs);
				} catch (Exception e) {
					logger.error(
							"SQLException occur while closing database resources."
									+ e.getMessage(), e);
				}
				logger.debug("Exiting method releaseSubscriber DAOException");

			
		}
	}
}
