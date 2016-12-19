/**************************************************************************
 * File     : BulkBizUserAssoServiceImpl.java
 * Author   : Abhipsa
 * Created  : Dec 8, 2008 
 * Modified : Dec 8, 2008 
 * Version  : 0.1
 **************************************************************************
 *                               HISTORY
 **************************************************************************
 * V0.1		Dec 8, 2008 	Abhipsa	First Cut.
 * V0.2		Dec 8, 2008 	Abhipsa	First modification
 **************************************************************************
 *
 * Copyright @ 2002 This document has been prepared and written by 
 * IBM Global Services on behalf of Bharti, and is copyright of Bharti
 *
 **************************************************************************/
package com.ibm.virtualization.ussdactivationweb.bulkupload.services.impl;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.sql.Connection;
import java.util.ArrayList;
import java.util.Iterator;

import org.apache.log4j.Logger;
import org.apache.struts.util.LabelValueBean;

import com.ibm.virtualization.ussdactivationweb.bulkupload.services.interfaces.BulkBizUserAssoService;
import com.ibm.virtualization.ussdactivationweb.dao.BulkBizUserAssoDAO;
import com.ibm.virtualization.ussdactivationweb.dao.LocationDataDAO;
import com.ibm.virtualization.ussdactivationweb.dao.ViewEditCircleMasterDAOImpl;
import com.ibm.virtualization.ussdactivationweb.dto.BulkBizUserAssoDTO;
import com.ibm.virtualization.ussdactivationweb.dto.LocationDataDTO;
import com.ibm.virtualization.ussdactivationweb.utils.CSVReader;
import com.ibm.virtualization.ussdactivationweb.utils.Constants;
import com.ibm.virtualization.ussdactivationweb.utils.DBConnection;
import com.ibm.virtualization.ussdactivationweb.utils.SQLConstants;
import com.ibm.virtualization.ussdactivationweb.utils.Utility;
import com.ibm.virtualization.ussdactivationweb.utils.ValidatorUtility;

/**
 * @author a_gupta
 * 
 */
public class BulkBizUserAssoServiceImpl implements BulkBizUserAssoService {

	private static final Logger logger = Logger
			.getLogger(BulkBizUserAssoServiceImpl.class);

	/**
	 * This method returns the list of Files which are scheduled for uploading.
	 * 
	 * @return ArrayList
	 */
	public ArrayList getScheduledFiles() {

		logger
				.debug("Entering BulkBizUserAssoServiceImpl : getScheduledFiles() ");
		ArrayList files = new ArrayList();
		BulkBizUserAssoDAO bulkBizUserAssoDAO = new BulkBizUserAssoDAO();
		try {
			files = bulkBizUserAssoDAO.getScheduledFiles();
			logger.info("List of scheduled files fetched.");
		} catch (Exception e) {
			logger.error("Error" + e.getMessage(), e);
		}
		logger
				.debug("Exiting BulkBizUserAssoServiceImpl : getScheduledFiles() ");
		return files;
	}

	/**
	 * This method reads the records in the file. Apply different checks on the
	 * records of the file. Checks are : Record in a file should be of specific
	 * format Record in a file should be a valid type of user Record in a should
	 * be active Record in a file should not be mapped to anyone else Record in
	 * a file should have active location
	 * 
	 * If all the things are valid. A record is mapped to a given user.
	 * 
	 * 
	 * @param bulkBizUserAssoDTO :
	 *            bulkBizUserAssoDTO
	 */
	public void uploadFile(BulkBizUserAssoDTO bulkBizUserAssoDTO) {

		logger.debug("Entering BulkBizUserAssoServiceImpl : uploadFile() ");
		BulkBizUserAssoDAO bulkBizUserAssoDAO = new BulkBizUserAssoDAO();
		ArrayList validUsers = new ArrayList();
		ArrayList inValidUsers = new ArrayList();
		int success = 0;
		int failure = 0;
		int fileCounter = 0;
		long relatedFileid = 0;
		Connection connection = null;
		String[] nextLine = null;
		String statusUpdated = "";
		boolean connCommitStatus = false;
		BulkBizUserAssoDTO bulkDto = new BulkBizUserAssoDTO();
		int mappingType = bulkBizUserAssoDTO.getMappingType();
		PrintWriter printWriter = null;
		try {
			String failedFileStoragePath = null;
			File failedFile = null;

			String filePath = Utility.getValueFromBundle(
					Constants.FILE_STORAGE_PATH,
					Constants.BULK_UPLOAD_APPLICATION_RESOURCE_BUNDLE)
					+ bulkBizUserAssoDTO.getFileName();
			CSVReader reader = new CSVReader(new FileReader(filePath));
			connection = DBConnection
					.getDBConnection(SQLConstants.DISTPORTAL_JNDI_NAME);
			statusUpdated = bulkBizUserAssoDAO.updateStatusToUploading(
					bulkBizUserAssoDTO.getFileId(), connection);

			connCommitStatus = connection.getAutoCommit();
			// Starting database transactions.
			connection.setAutoCommit(false);

			int fileUploadBatchSize = Integer.parseInt(Utility
					.getValueFromBundle(Constants.FILE_UPLOAD_BATCH_SIZE,
							Constants.BULK_UPLOAD_APPLICATION_RESOURCE_BUNDLE));

			LocationDataDAO locDao = new LocationDataDAO();

			if ((Constants.STATUS_UPDATED).equalsIgnoreCase(statusUpdated)) {
				bulkDto = bulkBizUserAssoDAO.getLocDetails(bulkBizUserAssoDTO,
						connection);
				do {
					while (((++fileCounter <= fileUploadBatchSize)
							&& (nextLine = reader.readNext()) != null)) {

						boolean invalidRecord = false;

						String str = nextLine[0].trim();

						if (nextLine.length != 1) {
						
							if(nextLine.length<1)
							{
								//No values or Blank line, then do nothing.
							}else{
								// when there are more then one value in a line(eg. comma seperated two values.)
								inValidUsers
										.add(new String[] {
												str,
												Utility
														.getValueFromBundle(
																Constants.code_not_in_proper_format,
																Constants.BULK_UPLOAD_APPLICATION_RESOURCE_BUNDLE) });
								failure++;
								invalidRecord = true;
							}
						}

						if (!invalidRecord
								&& !ValidatorUtility.isAlphaNumeric(str)) {
							
							if(("").equalsIgnoreCase(str)){
								//No values or Blank line, then do nothing.
								invalidRecord = true;
							}else{
								// invalid format exception
								inValidUsers
										.add(new String[] {
												str,
												Utility
														.getValueFromBundle(
																Constants.code_not_in_proper_format,
																Constants.BULK_UPLOAD_APPLICATION_RESOURCE_BUNDLE) });
								failure++;
								invalidRecord = true;
							}
						}

						// User is not of correct Mapping type
						if (!invalidRecord) {
							// checking type of uploaded user
							String typeCheck = bulkBizUserAssoDAO
									.userTypeCheck(str, mappingType, connection);
							if (typeCheck.equalsIgnoreCase(Constants.FALSE)) {

								inValidUsers
										.add(new String[] {
												str,
												Utility
														.getValueFromBundle(
																Constants.not_desired_type,
																Constants.BULK_UPLOAD_APPLICATION_RESOURCE_BUNDLE) });
								failure++;
								invalidRecord = true;
							}
						}

						// Invalid Status of the User
						if (!invalidRecord) {
							// status of the user
							String status = bulkBizUserAssoDAO.userStatusCheck(
									str, connection);
							if (status.equalsIgnoreCase(Constants.FALSE)) {
								inValidUsers
										.add(new String[] {
												str,
												Utility
														.getValueFromBundle(
																Constants.status_inactive,
																Constants.BULK_UPLOAD_APPLICATION_RESOURCE_BUNDLE) });
								failure++;
								invalidRecord = true;
							}
						}

						// Already mapped user
						if (!invalidRecord) {
							String alreadyMappedCheck = bulkBizUserAssoDAO
									.userMappedCheck(str, connection);
							if (alreadyMappedCheck
									.equalsIgnoreCase(Constants.FALSE)) {
								inValidUsers
										.add(new String[] {
												str,
												Utility
														.getValueFromBundle(
																Constants.allready_mapped,
																Constants.BULK_UPLOAD_APPLICATION_RESOURCE_BUNDLE) });
								failure++;
								invalidRecord = true;
							}
						}

						// checking the locations
						if (!invalidRecord) {

							// When loaction of both the users have to be same
							if ((mappingType == Constants.TM)
									|| (mappingType == Constants.DISTIBUTOR)
									|| (mappingType == Constants.FOS)
									|| (mappingType == Constants.ZBM)) {
								
//								checking the status of the location.
								LocationDataDAO locationDao = new LocationDataDAO();
								LocationDataDTO locationDto =  new LocationDataDTO();
								locationDto = locationDao.getLocationDetails(bulkDto.getLocId());
								if(locationDto.getStatus().equalsIgnoreCase(Constants.strActive))
								{
									// check the loaction of the user
									String sameLoaction = bulkBizUserAssoDAO
											.checkLocation(str, String
													.valueOf(bulkDto.getLocId()),
													connection, mappingType);
									if (sameLoaction
											.equalsIgnoreCase(Constants.FALSE)) {
										inValidUsers
												.add(new String[] {
														str,
														Utility
																.getValueFromBundle(
																		Constants.different_loaction,
																		Constants.BULK_UPLOAD_APPLICATION_RESOURCE_BUNDLE) });
										failure++;
										invalidRecord = true;
									}
								}else{
									// territory is inactive
									inValidUsers
									.add(new String[] {
											str,
											Utility
													.getValueFromBundle(
															Constants.different_loaction,
															Constants.BULK_UPLOAD_APPLICATION_RESOURCE_BUNDLE) });
									failure++;
									invalidRecord = true;
								}
							}

							// when base location is different and is
							// Zone-City
							else if (mappingType == Constants.ZSM) {
								// first getting the list of active
								// cities in the zone.

								ArrayList locList = new ArrayList();
								ArrayList cityList = new ArrayList();

								locList = locDao.getLocationList(String
										.valueOf(bulkDto.getLocId()),
										Constants.City,
										Constants.PAGE_FALSE, 0, 0);
								// obtaining list of loaction id's
								Iterator itr = locList.iterator();
								while (itr.hasNext()) {
									LocationDataDTO locDto = (LocationDataDTO) itr
											.next();
									cityList.add(String.valueOf((locDto
											.getLocDataId())));
								}

								String roleArray[] = (String[]) cityList
										.toArray(new String[0]);
								String strterr = Utility.arrayToString(
										roleArray, null);

								// checking if the user belongs to any of the
								// above locations.
								String sameLoaction = bulkBizUserAssoDAO
										.checkLocation(str, strterr,
												connection, mappingType);
								if (strterr == null
										|| "".equals(strterr)
										|| sameLoaction
												.equalsIgnoreCase(Constants.FALSE)) {
									inValidUsers
											.add(new String[] {
													str,
													Utility
															.getValueFromBundle(
																	Constants.different_city,
																	Constants.BULK_UPLOAD_APPLICATION_RESOURCE_BUNDLE) });
									failure++;
									invalidRecord = true;
								}
							}

							// when base location is different and is
							// Circle-Zone
							else if (mappingType == Constants.SALES_HEAD) {

								ArrayList totalZones = new ArrayList();
								ArrayList totalZonesList = new ArrayList();
								// list of active cities in circles
								totalZones = locDao.getLocationList(bulkDto
										.getCircleCode(), Constants.Zone,
										Constants.PAGE_FALSE, 0, 0);
								Iterator itr = totalZones.iterator();
								while(itr.hasNext())
								{
									LocationDataDTO locDto = (LocationDataDTO)itr.next();
									totalZonesList.add(String.valueOf((locDto.getLocDataId())));
			
								}// End of while
								String strZones = null;
								if (!totalZonesList.isEmpty()) {
									String roleArrayZones[] = (String[]) totalZonesList
											.toArray(new String[0]);
									strZones = Utility.arrayToString(
											roleArrayZones, null);
								}

								// checking if the user belongs to
								// any of the above locations.
								String sameLoaction = bulkBizUserAssoDAO
										.checkLocation(str, strZones,
												connection, mappingType);
								if (strZones == null
										|| "".equals(strZones)
										|| sameLoaction
												.equalsIgnoreCase(Constants.FALSE)) {

									inValidUsers
											.add(new String[] {
													str,
													Utility
															.getValueFromBundle(
																	Constants.different_zone,
																	Constants.BULK_UPLOAD_APPLICATION_RESOURCE_BUNDLE) });
									failure++;
									invalidRecord = true;
								}
							}
							// when base location is different and
							// is HUb-Circle
							else if (mappingType == Constants.ED) {
								// getting active circle list under
								// one hub
								ViewEditCircleMasterDAOImpl circledao = new ViewEditCircleMasterDAOImpl();
								ArrayList locList = new ArrayList();
								locList = circledao.getCircleListByHub(bulkDto
										.getHubCode());
								Iterator itr = locList.iterator();
								ArrayList circleList = new ArrayList();
								while (itr.hasNext()) {
									LabelValueBean lbean = (LabelValueBean) itr
											.next();
									circleList
											.add("'" + lbean.getValue() + "'");
								}
								String roleArray[] = (String[]) circleList
										.toArray(new String[0]);
								String strCircle = Utility.arrayToString(
										roleArray, null);

								// checking if the user belong to any of the
								// circles
								String sameLoaction = bulkBizUserAssoDAO
										.checkLocation(str, strCircle,
												connection, mappingType);
								if (strCircle == null
										|| "".equals(strCircle)
										|| sameLoaction
												.equalsIgnoreCase(Constants.FALSE)) {
									inValidUsers
											.add(new String[] {
													str,
													Utility
															.getValueFromBundle(
																	Constants.different_circle,
																	Constants.BULK_UPLOAD_APPLICATION_RESOURCE_BUNDLE) });
									failure++;
									invalidRecord = true;
								}
							}

							// when base location is same and is circle-circle
							else if (mappingType == Constants.CEO) {
								String sameLoaction = bulkBizUserAssoDAO
										.checkLocation(str, bulkDto
												.getCircleCode(), connection,
												mappingType);
								if (sameLoaction
										.equalsIgnoreCase(Constants.FALSE)) {

									inValidUsers
											.add(new String[] {
													str,
													Utility
															.getValueFromBundle(
																	Constants.different_circle,
																	Constants.BULK_UPLOAD_APPLICATION_RESOURCE_BUNDLE) });
									failure++;
									invalidRecord = true;
								}
							}
						}

						// All Validations are complete.
						if (!invalidRecord) {
							// It is a valid record.
							validUsers.add(str);
							success++;
						}

					}// end of while

					// Calling a method which will upload the successfull record
					// list

					if (!validUsers.isEmpty()) {
						bulkBizUserAssoDAO.mappUsers(bulkBizUserAssoDTO
								.getUserDataId(), validUsers, connection);
					}

					// Caliing a method which will write failed records to
					// another
					// file

					if (!inValidUsers.isEmpty()) {

						if (printWriter == null) {

							failedFileStoragePath = Utility
									.getValueFromBundle(
											Constants.FILE_STORAGE_PATH,
											Constants.BULK_UPLOAD_APPLICATION_RESOURCE_BUNDLE);
							String failedfilePath = failedFileStoragePath
									+ "Failed"
									+ bulkBizUserAssoDTO.getFileName();
							failedFile = new File(failedfilePath);
							printWriter = new PrintWriter(new FileWriter(
									failedFile));
						}

						Iterator itr = inValidUsers.iterator();
						while (itr.hasNext()) {
							String[] codes = (String[]) itr.next();
							String contents = codes[0] + "," + codes[1];
							printWriter.println(contents);
						}
					}

					// To process next 500 records, set the coulter to 0.
					fileCounter = 0;
					// emptying the arraylists
					inValidUsers.clear();
					validUsers.clear();
				} while (nextLine != null);

				// Completed working with the orginal file.

				// Upload failed file
				if (failure > 0) {
					String failedFilename = "Failed"
							+ bulkBizUserAssoDTO.getFileName();
					bulkBizUserAssoDTO.setOriginalFileName(bulkBizUserAssoDTO
							.getFileName());
					relatedFileid = bulkBizUserAssoDAO.fileInfoForBusinessUser(
							failedFilename, bulkBizUserAssoDTO,
							Constants.FILE_STATUS_FAILED_FILE,
							Constants.FILE_FAILED_MSG_FAILED,
							Constants.FILE_TYPE_FAILED_FILE);
					logger
							.debug("Failed File information is updated after uploading.");
				}

				// Update the failed file id as related file id for the original
				// file
				// Also update total success record and total failed records.
				bulkBizUserAssoDAO.updatefileInfoAfterUploading(
						(int) relatedFileid, bulkBizUserAssoDTO.getFileId(),
						failure, success, connection);
				logger.debug("File information is updated after uploading.");

				connection.commit();
			} else {
				// file is allready upload or in the process of uploading
				logger
						.debug("File is already uploaded or in the process of uploading. ");
			}

		} catch (Exception e) {
			try {
				connection.rollback();
				connection.setAutoCommit(connCommitStatus);
				// file failed to upload
				bulkBizUserAssoDAO.fileFailedToUpload(bulkBizUserAssoDTO
						.getFileId(), connection);

			} catch (Exception ex) {
				logger.error("Error" + e.getMessage(), ex);
			}

			logger.error("Error" + e.getMessage(), e);
		} finally {
			try {
				if (failure > 0) {
					printWriter.flush();
					printWriter.close();
				}
				if (connection != null) {
					connection.setAutoCommit(connCommitStatus);
					connection.close();
				}
				logger
						.debug("Exiting BulkBizUserAssoServiceImpl : uploadFile() ");
			} catch (Exception e) {
				logger.error("Error" + e.getMessage(), e);
			}
		}

	}
}
