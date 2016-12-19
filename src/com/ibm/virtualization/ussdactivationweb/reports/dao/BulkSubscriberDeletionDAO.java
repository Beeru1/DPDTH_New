/**
 * 
 */
package com.ibm.virtualization.ussdactivationweb.reports.dao;

import java.io.File;
import java.io.FileWriter;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

import com.ibm.virtualization.ussdactivationweb.daoInterfaces.BulkBizUserAssoInterface;
import com.ibm.virtualization.ussdactivationweb.utils.DAOException;
import com.ibm.virtualization.ussdactivationweb.reports.daoInterface.BulkSubscriberDeletionDAOInterface;
import com.ibm.virtualization.ussdactivationweb.utils.CSVWriter;
import com.ibm.virtualization.ussdactivationweb.utils.Constants;
import com.ibm.virtualization.ussdactivationweb.utils.DBConnection;
import com.ibm.virtualization.ussdactivationweb.utils.DBConnectionUtil;
import com.ibm.virtualization.ussdactivationweb.utils.SQLConstants;
import com.ibm.virtualization.ussdactivationweb.utils.Utility;


/**
 * @author a_gupta
 *
 */
public class BulkSubscriberDeletionDAO {

	private static final Logger logger = Logger
	.getLogger(SubscriberReleaseDAO.class);
	
	public void deletionReport()throws DAOException{
		Connection connection = null;
		PreparedStatement prepareStatement = null;
		ResultSet resultSet = null;
		List report = new ArrayList();
		String date = "";
		String fileName = "";
		String filePath = Utility.getValueFromBundle(
				Constants.DELETED_SUB_REPORT,
				Constants.WEB_APPLICATION_RESOURCE_BUNDLE);
		try{
			connection = DBConnection
			.getDBConnection(SQLConstants.FTA_JNDI_NAME);
			
			prepareStatement = connection.prepareStatement(BulkSubscriberDeletionDAOInterface.FETCH_SUBSCRIBER);
			prepareStatement.setString(1, Constants.DELETED);

			resultSet = prepareStatement.executeQuery();
			if(resultSet.next()){
				report.add(new String[] {
							"MSISDN", "SIM",
							"ACTION" , "ACTION_DATE" });
				
				while(resultSet.next()){
					report.add(new String[] {
							resultSet.getString("MSISDN"),
							resultSet.getString("COMPLETE_SIM"),
							resultSet.getString("ACTION_TAKEN"),
							resultSet.getString("ACTION_DATE")});
					date=resultSet.getString("DEL_DATE");
				}
				
				fileName="SubscriberDeletedOn - "+date;
				String fullFilePathWithName=filePath+fileName+".csv";
				File file = new File(fullFilePathWithName);
				CSVWriter csvWriter = new CSVWriter(new FileWriter(file));
				csvWriter.writeAll(report);
				csvWriter.close();
				
				prepareStatement = connection.prepareStatement(BulkSubscriberDeletionDAOInterface.FILE_ENTRY_Business_User);
				prepareStatement.setString(1, fileName);
				prepareStatement.setString(2, filePath);
				prepareStatement.setString(3, null);
				prepareStatement.setInt(4, 100);
				prepareStatement.executeUpdate();
			}//if
			
		
		}catch (SQLException sqe) {
			logger.error("SQL EXCEPTION", sqe);
			throw new DAOException(sqe.getMessage(), sqe);
		} catch (Exception ex) {
			logger.error("Exception", ex);
			throw new DAOException(ex.getMessage(), ex);
		} finally {
			try {
				DBConnectionUtil.closeDBResources(connection,
						prepareStatement,resultSet);
			} catch (SQLException e) {
				throw new DAOException(
						"Exection in database resources closing.", e);
			}
		}
	}
	
	
}
