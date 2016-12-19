/**************************************************************************
 * File     : BulkSubscriberServiceImpl.java
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

import com.ibm.virtualization.ussdactivationweb.bulkupload.services.interfaces.BulkSubscriberService;
import com.ibm.virtualization.ussdactivationweb.dao.BulkSubscriberDAO;
//import com.ibm.virtualization.ussdactivationweb.dto.BulkSubscriberDTO;
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
public class BulkSubscriberServiceImpl implements BulkSubscriberService {

	private static final Logger logger = Logger
			.getLogger(BulkSubscriberDAO.class);

	/**
	 * This method returns the list of Files which are scheduled for uploading.
	 * 
	 * @return ArrayList
	 */

	public ArrayList getScheduledFilesforSubscriber() {

		logger
				.debug("Entering BulkSubscriberServiceImpl : getScheduledFilesforSubscriber() ");
		ArrayList filesList = new ArrayList();
		BulkSubscriberDAO bulkSubscriberDAO = new BulkSubscriberDAO();
		try {
			filesList = null;//bulkSubscriberDAO.getScheduledFilesforSubscriber();
			logger.info("List of scheduled files fetched.");
		} catch (Exception e) {
			logger.error("Error" + e.getMessage(), e);
		}
		logger
				.debug("Exiting BulkSubscriberServiceImpl : getScheduledFilesforSubscriber() ");
		return filesList;
	}

	/**
	 * This method reads the records in the file. Apply different checks on the
	 * records of the file.
	 * 
	 * If all the things are valid. A suscriber is added in the table.
	 * 
	 * @param BulkSubscriberDTO :
	 *            bulkSubscriberDTO
	 */
/*
	public void uploadFile(BulkSubscriberDTO bulkSubscriberDTO) {
		BulkSubscriberDAO bulkSubscriberDAO = new BulkSubscriberDAO();
		logger.debug("Entering BulkSubscriberServiceImpl : uploadFile() ");
		Connection connection = null;
		Connection connection1 = null;
		
		ArrayList validSub = new ArrayList();
		ArrayList invalidSub = new ArrayList();
		ArrayList strServiceClassArrayList = new ArrayList();
		ArrayList strIMSIArrayList = new ArrayList();
		
		String statusUpdated = "";
		boolean connCommitStatus = false;
		PrintWriter printWriter = null;
		
		int success = 0;
		int noOfInvalidRecord = 0;
		//int totalFailure = 0;
		long relatedFileid = 0;
		int fileCounter = 0;
		
		try {
			File failedFile = null;
			String filePath = Utility.getValueFromBundle(
					Constants.FILE_STORAGE_PATH,
					Constants.BULK_UPLOAD_APPLICATION_RESOURCE_BUNDLE)
					+ bulkSubscriberDTO.getFileName();
			CSVReader reader = new CSVReader(new FileReader(filePath));
			String[] nextLine;
			connection = DBConnection
					.getDBConnection(SQLConstants.FTA_JNDI_NAME);

			/** Starting database transactions. *  
			connCommitStatus = connection.getAutoCommit();
			connection.setAutoCommit(false);

			int fileUploadBatchSize = Integer.parseInt(Utility
					.getValueFromBundle(Constants.FILE_UPLOAD_BATCH_SIZE,
							Constants.BULK_UPLOAD_APPLICATION_RESOURCE_BUNDLE));

			connection1 = DBConnection
					.getDBConnection(SQLConstants.PRODCAT_JNDI_NAME);
			String fileStoragePath = Utility.getValueFromBundle(
					Constants.FILE_STORAGE_PATH,
					Constants.BULK_UPLOAD_APPLICATION_RESOURCE_BUNDLE);
			String failedfilePath = fileStoragePath + "Failed"
					+ bulkSubscriberDTO.getFileName();
			failedFile = new File(failedfilePath);
			printWriter = new PrintWriter(new FileWriter(failedFile));
			statusUpdated = bulkSubscriberDAO.updateFilesforSubscriber(
					bulkSubscriberDTO.getFileId(), connection);
			if ((Constants.STATUS_UPDATED).equalsIgnoreCase(statusUpdated)) {

				/**
				 * getting list of active service class id's in a specific
				 * circle. *
				 
				strServiceClassArrayList = bulkSubscriberDAO
						.activeServiceIdInCircle(bulkSubscriberDTO
								.getCircleCode(), connection1);

				/** extracting 500 records every time. *  
				do {
					while (((nextLine = reader.readNext()) != null)
							&& ++fileCounter <= fileUploadBatchSize) {
						
						int intSubInfoLength = 0;
						String subInfoArrayLength = Utility
								.getValueFromBundle(
										Constants.SUBSCRIBER_INFO_ARRAY_LENGH,
										Constants.BULK_UPLOAD_APPLICATION_RESOURCE_BUNDLE);
						if (subInfoArrayLength != null) {
							intSubInfoLength = Integer
									.parseInt(subInfoArrayLength);
						}
						
						/*** File format validation  ** 
						if (nextLine.length != intSubInfoLength) {
							
							/** if file have empty line * 
							if(nextLine.length == 1 && "".equals(nextLine[0]))
							{
								continue ;
							} 
							String formatMsg = Utility
									.getValueFromBundle(
											Constants.MSISDN_SIM_SERVICECLASS_ID_FORMAT_MSG,
											Constants.BULK_UPLOAD_APPLICATION_RESOURCE_BUNDLE);

							invalidSub.add(new String[] { nextLine[0],
									formatMsg });
							noOfInvalidRecord++;
						
							
						} else if ("".equalsIgnoreCase(nextLine[0])
								|| "".equalsIgnoreCase(nextLine[1])
								|| "".equalsIgnoreCase(nextLine[2])
								|| "".equalsIgnoreCase(nextLine[3])) {

							String formatMsg = Utility
									.getValueFromBundle(
											Constants.MSISDN_SIM_SERVICECLASS_ID_BLANK_MSG,
											Constants.BULK_UPLOAD_APPLICATION_RESOURCE_BUNDLE);
							invalidSub.add(new String[] { nextLine[0],
									formatMsg });
							noOfInvalidRecord++;
							
						} else {
							
							boolean numberflag = false;
							boolean isSCMessage = false;
							
							for (int i = 0; i <= 3; i++) {

								numberflag = ValidatorUtility
										.isNumeric(nextLine[i].trim());

								if (numberflag == false && i == 2) {
									isSCMessage = true;

								} else if (numberflag == false) {
									break;
								}
							}  
							
							if(numberflag){
								
								if (nextLine[0].trim().startsWith(Utility.getValueFromBundle(
										Constants.MSISDN_START_WITH_ZERO,
										Constants.BULK_UPLOAD_APPLICATION_RESOURCE_BUNDLE))
										|| nextLine[0].trim().startsWith(
									Utility.getValueFromBundle(
										Constants.MSISDN_START_WITH_NINE_ONE,
										Constants.BULK_UPLOAD_APPLICATION_RESOURCE_BUNDLE))) {
											String formatMsg = Utility
													.getValueFromBundle(
															Constants.MSISDN_START_DIGIT_CHECK_MSG,
															Constants.BULK_UPLOAD_APPLICATION_RESOURCE_BUNDLE);
											invalidSub.add(new String[] { nextLine[0],
													formatMsg });

											noOfInvalidRecord++;
											
											continue;
										}
								
							} else {
								if (isSCMessage) {
									String formatMsg = Utility
											.getValueFromBundle(
													Constants.SERVICECLASS_ID_FORMAT_INCORRECT,
													Constants.BULK_UPLOAD_APPLICATION_RESOURCE_BUNDLE);
									invalidSub.add(new String[] {
											nextLine[0], formatMsg });

									noOfInvalidRecord++;
								} else {
									String formatMsg = Utility
											.getValueFromBundle(
													Constants.MSISDN_SIM_FORMAT_INCORRECT,
													Constants.BULK_UPLOAD_APPLICATION_RESOURCE_BUNDLE);
									invalidSub.add(new String[] {
											nextLine[0], formatMsg });

									noOfInvalidRecord++;
								}

							}
							
							/*** MSISDN/SIM/SERVICE CLASS ID/IMSI length validation** 
							
							int intMsisdnMinLength =0;
							int intMsisdnMaxLength = 0;
							int intSIM = 0;
							int intServiceClassId = 0;
							int intIMSI=0;
							
							int msisdnlength = nextLine[0].trim().length();
							int simlength = nextLine[1].trim().length();
							int serviceClassIdLength = nextLine[2].trim()
									.length();
							int imsiLength = nextLine[3].trim().length();
							
							logger.debug("nextLine[3].trim()== "
									+ nextLine[3].trim());
							
							String msisdnMinLength = Utility
							.getValueFromBundle(
									Constants.MSISDN_MIN_LENGH,
									Constants.BULK_UPLOAD_APPLICATION_RESOURCE_BUNDLE);
							String msisdnMaxLength = Utility
									.getValueFromBundle(
											Constants.MSISDN_MAX_LENGH,
											Constants.BULK_UPLOAD_APPLICATION_RESOURCE_BUNDLE);
							String simMaxLength = Utility
									.getValueFromBundle(
											Constants.SIM_MAX_LENGH,
											Constants.BULK_UPLOAD_APPLICATION_RESOURCE_BUNDLE);
							String serviceClassMaxLength = Utility
									.getValueFromBundle(
											Constants.SERVICECLASS_ID_MAX_LENGH,
											Constants.BULK_UPLOAD_APPLICATION_RESOURCE_BUNDLE);
							String imsiMaxLength = Utility
							       .getValueFromBundle(
											Constants.IMSI_MAX_LENGH,
											Constants.BULK_UPLOAD_APPLICATION_RESOURCE_BUNDLE);

							if (msisdnMinLength != null) {
								intMsisdnMinLength = Integer.parseInt(msisdnMinLength);
							}
							if (msisdnMaxLength != null) {
								intMsisdnMaxLength = Integer.parseInt(msisdnMaxLength);
							}
							
							if (simMaxLength != null) {
								intSIM = Integer.parseInt(simMaxLength);
							}
							if (imsiMaxLength != null) {
								intIMSI = Integer.parseInt(imsiMaxLength);
							}
							
							if (serviceClassMaxLength != null) {
								intServiceClassId = Integer
										.parseInt(serviceClassMaxLength);
							}
							if (msisdnlength < intMsisdnMinLength  || msisdnlength > intMsisdnMaxLength) {

								String formatMsg = Utility
										.getValueFromBundle(
												Constants.SUB_MSISDN_LENGTH_CHECK_MSG,
												Constants.BULK_UPLOAD_APPLICATION_RESOURCE_BUNDLE);
								invalidSub.add(new String[] { nextLine[0],
										formatMsg });

								noOfInvalidRecord++;
							} else if (simlength != intSIM) {

								String formatMsg = Utility
										.getValueFromBundle(
												Constants.SUB_SIM_LENGTH_CHECK_MSG,
												Constants.BULK_UPLOAD_APPLICATION_RESOURCE_BUNDLE);
								invalidSub.add(new String[] { nextLine[0],
										formatMsg });

								noOfInvalidRecord++;
							}else if (imsiLength != intIMSI) {

								String formatMsg = Utility
										.getValueFromBundle(
												Constants.SUB_IMSI_LENGTH_CHECK_MSG,
												Constants.BULK_UPLOAD_APPLICATION_RESOURCE_BUNDLE);
								invalidSub.add(new String[] { nextLine[0],
										formatMsg });

								noOfInvalidRecord++;
							}  
							else if (serviceClassIdLength > intServiceClassId) {

								String formatMsg = Utility
										.getValueFromBundle(
												Constants.SERVICECLASS_ID_LENGTH_CHECK_MSG,
												Constants.BULK_UPLOAD_APPLICATION_RESOURCE_BUNDLE);
								invalidSub.add(new String[] { nextLine[0],
										formatMsg });

								noOfInvalidRecord++;
							} else {
                                
								/** SIM changed from 7 digits to 5 digits after UAT * 
								
								/** SIM changed from 7 digits to 5 digits after UAT * 
								String subStringIndex = Utility.getValueFromBundle(Constants.SIM_SUBSTRING_INDEX, Constants.WEB_APPLICATION_RESOURCE_BUNDLE);
								String lastFiveDidits = nextLine[1].substring(Integer.parseInt(subStringIndex));
								
								
								/** is Subscriber already exits or not. *  
								/*boolean isSubAlreadyExist = bulkSubscriberDAO
										.checkSubscriber(nextLine[0],
												lastFiveDigits,
												bulkSubscriberDTO
														.getCircleCode(),
												connection); 
								
								    /*** Service class id  exist/Active or not ** 
                                    String serviceClassId =nextLine[2].trim();
                                    
									if (!strServiceClassArrayList
											.contains(serviceClassId)) {

										String formatMsg = Utility
												.getValueFromBundle(
														Constants.SERVICECLASS_ID_NOT_EXIST_OR_INACTIVE_MSG,
														Constants.BULK_UPLOAD_APPLICATION_RESOURCE_BUNDLE);
										invalidSub.add(new String[] {
												nextLine[0], formatMsg });

										noOfInvalidRecord++;
									}/* else if (isSubAlreadyExist) {
										String formatMsg = Utility
												.getValueFromBundle(
														Constants.MSISDN_OR_SIM_ALREADY_EXIST,
														Constants.BULK_UPLOAD_APPLICATION_RESOURCE_BUNDLE);
										invalidSub.add(new String[] {
												nextLine[0], formatMsg });

										noOfInvalidRecord++;

									}  
									
									

								
									
									else {
										/**
										 * inserting valid subscriber in
										 * database *
										  
										
										BulkSubscriberDTO bulkSubsDTO = new BulkSubscriberDTO();
										bulkSubsDTO.setMsisdn(nextLine[0]);
										bulkSubsDTO.setSim(lastFiveDidits);
										bulkSubsDTO.setCompleteSim(nextLine[1]);
										bulkSubsDTO.setServiceClassId(serviceClassId);
										bulkSubsDTO.setImsi(nextLine[3]);
										bulkSubsDTO.setCircleCode(bulkSubscriberDTO.getCircleCode());
										bulkSubsDTO.setUploadedBy(bulkSubscriberDTO.getUploadedBy());
										validSub.add(bulkSubsDTO);
										success++;
									}
								} 
							}
							
						}
					
					    /*** insert valid record in subscriber data ** 
						
					    if (null != validSub && !validSub.isEmpty()){
	
							boolean isInserted = bulkSubscriberDAO
							.insertBulkSubscriber(validSub,connection);
	
							if (isInserted) {
								logger.info("Subscriber created for file id :"+bulkSubscriberDTO.getFileId());
								
							}
	
						}
					
						/**
						 * Calling a method which will write failed records to
						 * another file *
						  
						if (!invalidSub.isEmpty()) {
							if (printWriter == null) {

								fileStoragePath = Utility
										.getValueFromBundle(
												Constants.FILE_STORAGE_PATH,
												Constants.BULK_UPLOAD_APPLICATION_RESOURCE_BUNDLE);
								failedfilePath = fileStoragePath + "Failed"
										+ bulkSubscriberDTO.getFileName();
								failedFile = new File(failedfilePath);
								printWriter = new PrintWriter(new FileWriter(
										failedFile));
							}

							Iterator itr = invalidSub.iterator();
							while (itr.hasNext()) {
								String[] codes = (String[]) itr.next();
								String contents = codes[0] + "," + codes[1];
								printWriter.println(contents);
							}
						}
						
					fileCounter = 0;
					invalidSub.clear();
					validSub.clear();
				} while (nextLine != null);

				/** upload failed file *  
				if (noOfInvalidRecord > 0) {
					String failedFilename = "Failed"
							+ bulkSubscriberDTO.getFileName();
					relatedFileid = bulkSubscriberDAO.fileInfoForSubscriber(
							failedFilename, bulkSubscriberDTO,
							Constants.FILE_STATUS_FAILED_FILE,
							Constants.FILE_FAILED_MSG_FAILED,
							Constants.FILE_TYPE_FAILED_FILE);
				}
				/**
				 * update the related file id , total success record and total
				 * failed records. *
				  
				log.info("updating file info after uploading ");
				bulkSubscriberDAO.updatefileInfoAfterUploading(
						(int) relatedFileid, bulkSubscriberDTO.getFileId(),
						noOfInvalidRecord, success, connection);
				connection.commit();
				log.info("updating file info after uploading ");
			} else {

				/** file is allready upload or in the process of uploading *  
				log
						.debug("Life is already uploaded or in the process of uploading. ");
			}

		} catch (Exception e) {
			try {
				/** file failed to upload *  
				connection.rollback();
				connection.setAutoCommit(connCommitStatus);
				bulkSubscriberDAO.fileFailedToUpload(bulkSubscriberDTO
						.getFileId(), connection);

			} catch (Exception ex) {
				log.error("Error" + e.getMessage(), ex);
			}

			log.error("Error" + e.getMessage(), e);
		} finally {
			try {
				if (noOfInvalidRecord > 0) {
					printWriter.flush();
					printWriter.close();
				}
				if (connection != null) {
					connection.setAutoCommit(connCommitStatus);
					connection.close();
				}
				if (connection1 != null) {
					connection1.close();
				}
				logger
						.debug("Exiting BulkSubscriberServiceImpl : uploadFile() ");
			} catch (Exception e) {
				log.error("Error" + e.getMessage(), e);
			}
		}
	}
*/
}
