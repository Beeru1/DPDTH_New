/**
 * 
 */
package com.ibm.virtualization.ussdactivationweb.bulkupload.services.impl;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.sql.Connection;
import java.util.ArrayList;
import java.util.Iterator;

import org.apache.log4j.Logger;

import com.ibm.virtualization.ussdactivationweb.bulkupload.services.interfaces.BulkBizUserCreationService;
import com.ibm.virtualization.ussdactivationweb.dao.BulkBizUserCreationDAO;
import com.ibm.virtualization.ussdactivationweb.dao.RegistrationOfAllUsersDAO;
import com.ibm.virtualization.ussdactivationweb.dao.ServiceClassDAOImpl;
import com.ibm.virtualization.ussdactivationweb.dao.ViewEditCircleMasterDAOImpl;
//import com.ibm.virtualization.ussdactivationweb.dto.BulkBizUserCreationDTO;
import com.ibm.virtualization.ussdactivationweb.dto.ServiceClassDTO;
import com.ibm.virtualization.ussdactivationweb.utils.CSVReader;
import com.ibm.virtualization.ussdactivationweb.utils.Constants;
import com.ibm.virtualization.ussdactivationweb.utils.DBConnection;
import com.ibm.virtualization.ussdactivationweb.utils.DBConnectionUtil;
import com.ibm.virtualization.ussdactivationweb.utils.SQLConstants;
import com.ibm.virtualization.ussdactivationweb.utils.Utility;
//import com.ibm.virtualization.ussdactivationweb.utils.ValidatorUtility;

/**
 * @author Nitesh
 * 
 */
public class BulkBizUserCreationServiceImpl implements
		BulkBizUserCreationService {

	private static final Logger logger = Logger
			.getLogger(BulkBizUserCreationServiceImpl.class);

	/**
	 * This method returns the list of Files which are scheduled for uploading.
	 * 
	 * @return ArrayList
	 */

	public ArrayList getScheduledFilesforBulkUserCreation() {

		logger
				.debug("Entering BulkBizUserCreationServiceImpl : getScheduledFilesforBulkUserCreation() ");
		ArrayList filesList = new ArrayList();
		BulkBizUserCreationDAO bulkBizUserCreationDAO = new BulkBizUserCreationDAO();
		try {
			filesList = null;/*bulkBizUserCreationDAO
					.getScheduledFilesforBulkUserCreation(Utility
							.getValueFromBundle(
									Constants.BIZ_USER_CREATION_FILE_TYPE,
									Constants.BULK_UPLOAD_APPLICATION_RESOURCE_BUNDLE));*/
			logger.info("List of scheduled files fetched.");
		} catch (Exception e) {
			logger.error("Error" + e.getMessage(), e);
		}
		logger
				.debug("Exiting BulkBizUserCreationServiceImpl : getScheduledFilesforBulkUserCreation() ");
		return filesList;
	}

	/**
	 * This method reads the records in the file. Apply different checks on the
	 * records of the file.
	 * 
	 * If all the things are valid. A suscriber is added in the
	 * table(V_BUSINESS_USER_DATA).
	 * 
	 * @param BulkBizUserCreationDTO :
	 *            bulkBizUserCreationDTOParam
	 */
/*
	public void uploadFile(BulkBizUserCreationDTO bulkBizUserCreationDTOParam,
			int creatorUserId) {

		logger.debug("Entering BulkBizUserCreationServiceImpl : uploadFile() ");
		Connection connectionDistportal = null;

		ArrayList invalidRecords = new ArrayList();
		String statusUpdated = "";
		PrintWriter printWriter = null;
		BulkBizUserCreationDAO bulkBizUserDAO = new BulkBizUserCreationDAO();
		ServiceClassDAOImpl serviceClassDAOImpl = new ServiceClassDAOImpl();
		RegistrationOfAllUsersDAO registrationOfAllUsersDAO = new RegistrationOfAllUsersDAO();
		ViewEditCircleMasterDAOImpl viewEditCircleMasterDAOImpl = new ViewEditCircleMasterDAOImpl();
		
		int success = 0;
		int noOfInvalidRecord = 0;
		// int totalFailure = 0;
		long relatedFileid = 0;
		int fileCounter = 0;

		try {
			File failedFile = null;
			String filePath = Utility.getValueFromBundle(
					Constants.BIZ_USER_CREATION_FILE_STORAGE_PATH,
					Constants.BULK_UPLOAD_APPLICATION_RESOURCE_BUNDLE)
					+ bulkBizUserCreationDTOParam.getFileName();

			CSVReader reader = new CSVReader(new FileReader(filePath));
			String[] nextLine;
			connectionDistportal = DBConnection
					.getDBConnection(SQLConstants.DISTPORTAL_JNDI_NAME);

			/** Starting database transactions. *  
			connectionDistportal.setAutoCommit(false);

			int fileUploadBatchSize = Integer.parseInt(Utility
					.getValueFromBundle(
							Constants.BIZ_USER_CREATION_FILE_UPLOAD_BATCH_SIZE,
							Constants.BULK_UPLOAD_APPLICATION_RESOURCE_BUNDLE));

			String fileStoragePath = Utility.getValueFromBundle(
					Constants.BIZ_USER_CREATION_FILE_STORAGE_PATH,
					Constants.BULK_UPLOAD_APPLICATION_RESOURCE_BUNDLE);

			String failedfilePath = fileStoragePath + "Failed"
					+ bulkBizUserCreationDTOParam.getFileName();

			failedFile = new File(failedfilePath);
			printWriter = null;//new PrintWriter(new FileWriter(failedFile));
			statusUpdated = bulkBizUserDAO.updateFilesforUsers(
					bulkBizUserCreationDTOParam.getFileId(), connectionDistportal);
			if ((Constants.STATUS_UPDATED).equalsIgnoreCase(statusUpdated)) {

				/**
				 * getting list of active service class id's in a specific
				 * circle.
				  
				// strServiceClassArrayList =
				// bulkBizUserDAO.activeServiceIdInCircle(bulkSubscriberDTO
				// .getCircleCode(), connectionDistportal);
				/** extracting 500 records every time. *  
				do {
					while (((nextLine = reader.readNext()) != null)
							&& ++fileCounter <= fileUploadBatchSize) {
						String serviceClassIds = "";
						int intSubInfoLength = 0;
						String subInfoArrayLength;
						if (bulkBizUserCreationDTOParam.getUserMasterId() == Constants.DISTIBUTOR) {
							subInfoArrayLength = Utility
									.getValueFromBundle(
											Constants.BIZ_USER_DISTRIBUTOR_INFO_ARRAY_LENGTH,
											Constants.BULK_UPLOAD_APPLICATION_RESOURCE_BUNDLE);
						} else {
							subInfoArrayLength = Utility
									.getValueFromBundle(
											Constants.BIZ_USER_INFO_ARRAY_LENGTH,
											Constants.BULK_UPLOAD_APPLICATION_RESOURCE_BUNDLE);
						}

						if (subInfoArrayLength != null) {
							intSubInfoLength = Integer
									.parseInt(subInfoArrayLength);
						}

						/** * File format validation **  
						if (nextLine.length < intSubInfoLength) {
							/** if file have less fields *  
							
							String line = "";
							for(int i = 0; i < nextLine.length; i++)
							{
								line+= nextLine[i];
							}
							
							if(line.trim().equals(""))
							{
								continue;
							}
							String formatMsg = Utility
									.getValueFromBundle(
											Constants.BIZ_USER_CREATION_FORMAT_MSG,
											Constants.BULK_UPLOAD_APPLICATION_RESOURCE_BUNDLE);

							invalidRecords.add(new String[] { "",
									formatMsg });
							noOfInvalidRecord++;
							continue;

						} else if (bulkBizUserCreationDTOParam.getUserMasterId() == Constants.DISTIBUTOR) {
							if (!"".equalsIgnoreCase(nextLine[4].trim())
									&& nextLine[4]
											.trim()
											.equals(Utility
													.getValueFromBundle(
													Constants.BIZ_USER_ALL_SERVICE_CLASS_TYPE,Constants.BULK_UPLOAD_APPLICATION_RESOURCE_BUNDLE))) {
								if ("".equalsIgnoreCase(nextLine[0].trim())
										|| "".equalsIgnoreCase(nextLine[1].trim())
										|| "".equalsIgnoreCase(nextLine[2].trim())
										|| "".equalsIgnoreCase(nextLine[3].trim())
										|| "".equalsIgnoreCase(nextLine[4].trim())) {
									String formatMsg = Utility
											.getValueFromBundle(
													Constants.BIZ_USER_DISTRIBUTOR_CREATION_BLANK_MSG,
													Constants.BULK_UPLOAD_APPLICATION_RESOURCE_BUNDLE);
									invalidRecords.add(new String[] {
											"", formatMsg });
									noOfInvalidRecord++;
									continue;
								}
							} else {
								if ("".equalsIgnoreCase(nextLine[0].trim())
										|| "".equalsIgnoreCase(nextLine[1].trim())
										|| "".equalsIgnoreCase(nextLine[2].trim())
										|| "".equalsIgnoreCase(nextLine[3].trim())
										|| "".equalsIgnoreCase(nextLine[4].trim())
										|| "".equalsIgnoreCase(nextLine[5].trim())) {
									String formatMsg = Utility
											.getValueFromBundle(
													Constants.BIZ_USER_DISTRIBUTOR_CREATION_BLANK_SERVICE_CLASS_ID_MSG,
													Constants.BULK_UPLOAD_APPLICATION_RESOURCE_BUNDLE);
									invalidRecords.add(new String[] {
											"", formatMsg });
									noOfInvalidRecord++;
									continue;
								}
							}

						} else if (!(bulkBizUserCreationDTOParam.getUserMasterId() == Constants.DISTIBUTOR)) {
							if ("".equalsIgnoreCase(nextLine[0].trim())
									|| "".equalsIgnoreCase(nextLine[1].trim())
									|| "".equalsIgnoreCase(nextLine[2].trim())) {
								String formatMsg = Utility
										.getValueFromBundle(
												Constants.BIZ_USER_CREATION_BLANK_MSG,
												Constants.BULK_UPLOAD_APPLICATION_RESOURCE_BUNDLE);
								invalidRecords.add(new String[] { "",
										formatMsg });
								noOfInvalidRecord++;
								continue;
							}
						} 

							String formatMsg = "";
							/* User Name Validation  
							if (nextLine[0].trim().length() < Integer
									.parseInt(Utility
											.getValueFromBundle(
													Constants.BIZ_USER_NAME_MIN_LENGTH,
													Constants.BULK_UPLOAD_APPLICATION_RESOURCE_BUNDLE) )											
											) {
								formatMsg = Utility
										.getValueFromBundle(
												Constants.BIZ_USER_NAME_LENGTH_LESS_MSG,
												Constants.BULK_UPLOAD_APPLICATION_RESOURCE_BUNDLE);
								invalidRecords.add(new String[] { nextLine[0],
										formatMsg });

								noOfInvalidRecord++;

								continue;
							} else if (nextLine[0].trim().length() > Integer
									.parseInt(Utility
											.getValueFromBundle(Constants.BIZ_USER_NAME_MAX_LENGTH,Constants.BULK_UPLOAD_APPLICATION_RESOURCE_BUNDLE))) {
								formatMsg = Utility
										.getValueFromBundle(
												Constants.BIZ_USER_NAME_LENGTH_MORE_MSG,
												Constants.BULK_UPLOAD_APPLICATION_RESOURCE_BUNDLE);
								invalidRecords.add(new String[] { nextLine[0],
										formatMsg });

								noOfInvalidRecord++;

								continue;
							}

							/* User Contact No Validation  
							if (nextLine[1]
									.trim()
									.startsWith(
											Utility
													.getValueFromBundle(
															Constants.BIZ_USER_CONTACT_NO_START_WITH_ZERO,
															Constants.BULK_UPLOAD_APPLICATION_RESOURCE_BUNDLE))
									|| nextLine[1]
											.trim()
											.startsWith(
													Utility
															.getValueFromBundle(
																	Constants.BIZ_USER_CONTACT_NO_START_WITH_NINE_ONE,
																	Constants.BULK_UPLOAD_APPLICATION_RESOURCE_BUNDLE))) {

								formatMsg = Utility
										.getValueFromBundle(
												Constants.BIZ_USER_CONTACT_NO_START_DIGIT_CHECK_MSG,
												Constants.BULK_UPLOAD_APPLICATION_RESOURCE_BUNDLE);
								invalidRecords.add(new String[] { nextLine[1],
										formatMsg });

								noOfInvalidRecord++;

								continue;

							}

							/* User Regestration No Validation  
							if (nextLine[2]
									.trim()
									.startsWith(
											Utility
													.getValueFromBundle(
															Constants.BIZ_USER_REG_NO_START_WITH_ZERO,
															Constants.BULK_UPLOAD_APPLICATION_RESOURCE_BUNDLE))
									|| nextLine[2]
											.trim()
											.startsWith(
													Utility
															.getValueFromBundle(
																	Constants.BIZ_USER_REG_NO_START_WITH_NINE_ONE,
																	Constants.BULK_UPLOAD_APPLICATION_RESOURCE_BUNDLE))) {

								formatMsg = Utility
										.getValueFromBundle(
												Constants.BIZ_USER_REG_NO_START_DIGIT_CHECK_MSG,
												Constants.BULK_UPLOAD_APPLICATION_RESOURCE_BUNDLE);
								invalidRecords.add(new String[] { nextLine[2],
										formatMsg });

								noOfInvalidRecord++;

								continue;
							} else if (!registrationOfAllUsersDAO
									.checkRegNoAvail(
											nextLine[2].trim(),
											bulkBizUserCreationDTOParam
													.getUserMasterId())
									.equals(Constants.VALID)) {

								formatMsg = Utility
										.getValueFromBundle(
												Constants.BIZ_USER_REG_NO_EXISTS,
												Constants.BULK_UPLOAD_APPLICATION_RESOURCE_BUNDLE);
								invalidRecords.add(new String[] { nextLine[2],
										formatMsg });

								noOfInvalidRecord++;

								continue;

							}

							/*
							 * Validation for User Role
							 * Distributor
							  
							if (bulkBizUserCreationDTOParam.getUserMasterId() == Constants.DISTIBUTOR) {
								/* User Code Validation  
								String invlidCharacters = "!@#$%^`*()+=[]\\\';,./{}|\":<>?~&";
								String necessaryCharacter = "ABCDEFGHIJKLMNOPQRSTUVWXYZ abcdefghijklmnopqrstuvwxyz";
								boolean invalidFlag = false;
								boolean validFlag = false;
								if (nextLine[3].trim().length() != Integer
										.parseInt(Utility
												.getValueFromBundle(Constants.BIZ_USER_CODE_LENGTH,Constants.BULK_UPLOAD_APPLICATION_RESOURCE_BUNDLE))) {
									formatMsg = Utility
											.getValueFromBundle(
													Constants.BIZ_USER_CODE_LENGTH_MSG,
													Constants.BULK_UPLOAD_APPLICATION_RESOURCE_BUNDLE);
									invalidRecords.add(new String[] {
											nextLine[3], formatMsg });

									noOfInvalidRecord++;

									continue;
								}
								for (int i = 0; i < invlidCharacters.length(); i++) {
									if (nextLine[3].trim().indexOf(
											invlidCharacters.charAt(i)) != -1) {
										invalidFlag = true;
										break;
									}
								}
								if (invalidFlag) {
									formatMsg = Utility
											.getValueFromBundle(
													Constants.BIZ_USER_CODE_TYPE_MSG,
													Constants.BULK_UPLOAD_APPLICATION_RESOURCE_BUNDLE);
									invalidRecords.add(new String[] {
											nextLine[3], formatMsg });

									noOfInvalidRecord++;

									continue;
								}
								
								for (int i = 0; i < necessaryCharacter.length(); i++) {
									if (nextLine[3].trim().indexOf(
											necessaryCharacter.charAt(i)) != -1) {
										validFlag = true;
										break;
									}
								}
								if (!validFlag) {
									formatMsg = Utility
											.getValueFromBundle(
													Constants.BIZ_USER_CODE_TYPE_MSG,
													Constants.BULK_UPLOAD_APPLICATION_RESOURCE_BUNDLE);
									invalidRecords.add(new String[] {
											nextLine[3], formatMsg });

									noOfInvalidRecord++;

									continue;
								}

								/* Service Class Type Validation  
								if (nextLine[4].trim().length() != Integer
										.parseInt(Utility
												.getValueFromBundle(
														Constants.BIZ_USER_SERVICE_CLASS_TYPE_LENGTH,Constants.BULK_UPLOAD_APPLICATION_RESOURCE_BUNDLE))) {
									formatMsg = Utility
											.getValueFromBundle(
													Constants.BIZ_USER_SERVICE_CLASS_TYPE_LENGTH_MSG,
													Constants.BULK_UPLOAD_APPLICATION_RESOURCE_BUNDLE);
									invalidRecords.add(new String[] {
											nextLine[4], formatMsg });

									noOfInvalidRecord++;

									continue;
								} else if (!(nextLine[4]
										.trim()
										.equalsIgnoreCase(Utility
												.getValueFromBundle(
												Constants.BIZ_USER_ALL_SERVICE_CLASS_TYPE,
												Constants.BULK_UPLOAD_APPLICATION_RESOURCE_BUNDLE))
										|| nextLine[4]
												.trim().equalsIgnoreCase(
												Utility
														.getValueFromBundle(
														Constants.BIZ_USER_EXCLUDE_SERVICE_CLASS_TYPE,
														Constants.BULK_UPLOAD_APPLICATION_RESOURCE_BUNDLE))
										|| nextLine[4]
												.trim()
												.equalsIgnoreCase(Utility
														.getValueFromBundle(
														Constants.BIZ_USER_INCLUDE_SERVICE_CLASS_TYPE,
														Constants.BULK_UPLOAD_APPLICATION_RESOURCE_BUNDLE)))) {
									formatMsg = Utility
											.getValueFromBundle(
													Constants.BIZ_USER_SERVICE_CLASS_TYPE_LENGTH_MSG,
													Constants.BULK_UPLOAD_APPLICATION_RESOURCE_BUNDLE);
									invalidRecords.add(new String[] {
											nextLine[4], formatMsg });

									noOfInvalidRecord++;

									continue;
								}

								/* Service Class Value Type Validation  
								ArrayList serviceClassList = new ArrayList();
								if (!nextLine[4]
										.trim()
										.equals(Utility
												.getValueFromBundle(Constants.BIZ_USER_ALL_SERVICE_CLASS_TYPE,Constants.BULK_UPLOAD_APPLICATION_RESOURCE_BUNDLE))) {
									serviceClassIds = "";
									invalidFlag = false;
									String invalidService = "";
									boolean invalidServicClassLengthFlag = false;
									for (int i = 5; i < nextLine.length; i++) {
										if (!nextLine[i].trim().equals("")) {
											if (nextLine[i].trim().length() > Integer
													.parseInt(Utility
															.getValueFromBundle(Constants.BIZ_USER_SERVICE_CLASS_LENGTH,Constants.BULK_UPLOAD_APPLICATION_RESOURCE_BUNDLE))) {
												invalidService = nextLine[i].trim();
												invalidServicClassLengthFlag = true;
												break;
											}
											serviceClassIds += nextLine[i].trim()+",";
											serviceClassList.add(nextLine[i].trim());											
										}
									}
									if (invalidServicClassLengthFlag) {
										formatMsg = Utility
												.getValueFromBundle(
														Constants.BIZ_USER_SERVICE_CLASS_LENGTH_MSG,
														Constants.BULK_UPLOAD_APPLICATION_RESOURCE_BUNDLE);
										invalidRecords.add(new String[] {
												invalidService, formatMsg });

										noOfInvalidRecord++;

										continue;
									}
									if(serviceClassIds != null)
									{
										int index = -1;
										index =  serviceClassIds.lastIndexOf(",");
										if(index != -1)
										{
											serviceClassIds = serviceClassIds.substring(0, index);
										}
									}
									
									if (serviceClassIds.length() > Integer
											.parseInt(Utility
													.getValueFromBundle(Constants.BIZ_USER_SERVICE_CLASS_TOTAL_LENGTH,Constants.BULK_UPLOAD_APPLICATION_RESOURCE_BUNDLE))) {
										formatMsg = Utility
												.getValueFromBundle(
														Constants.BIZ_USER_SERVICE_CLASS_TOTAL_LENGTH_MSG,
														Constants.BULK_UPLOAD_APPLICATION_RESOURCE_BUNDLE);
										invalidRecords.add(new String[] {
												serviceClassIds, formatMsg });

										noOfInvalidRecord++;

										continue;
									}

									invlidCharacters = "!@#$%^`*()+=[]\\\';./{}|\":<>?~&amp;ABCEDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz";
									invalidFlag = false;

									for (int i = 0; i < invlidCharacters
											.length(); i++) {
									if (serviceClassIds
												.indexOf(invlidCharacters
														.charAt(i)) != -1) {
											invalidFlag = true;
											break;
										}
									}

									if (invalidFlag) {
										formatMsg = Utility
												.getValueFromBundle(
														Constants.BIZ_USER_SERVICE_CLASS_VALUE_TYPE_MSG,
														Constants.BULK_UPLOAD_APPLICATION_RESOURCE_BUNDLE);
										invalidRecords.add(new String[] {
												serviceClassIds, formatMsg });

										noOfInvalidRecord++;

										continue;
									}

									invalidFlag = false;
									Iterator iterator = serviceClassList
											.iterator();
									Iterator serviceIterator;
									ArrayList circleServiceClassList = new ArrayList();
									circleServiceClassList = serviceClassDAOImpl
											.getServiceClassList(bulkBizUserCreationDTOParam
													.getCircleCode());
									ServiceClassDTO serviceClassDTO = new ServiceClassDTO();
									String classId = "";
									String localId="";
									boolean existsFlag = false;
									if (circleServiceClassList.size() > 0) {
										while (iterator.hasNext()) {
											localId = (String) iterator.next();
											localId = localId.trim();
											serviceIterator = circleServiceClassList
													.iterator();
											existsFlag = false;
											while (serviceIterator.hasNext()) {
												serviceClassDTO = (ServiceClassDTO) serviceIterator
														.next();
												classId = String
														.valueOf(
																serviceClassDTO
																		.getServiceClassId())
														.trim();
												if (classId.equals(localId)) {
													existsFlag = true;
													break;
												}
												
											}
											if(!existsFlag)
											{
												invalidFlag = true;
											}
											if (invalidFlag) {
												break;
											}
										}
									}
									if (invalidFlag) {
										formatMsg = Utility
												.getValueFromBundle(
														Constants.BIZ_USER_SERVICE_CLASS_NOT_EXISTS,
														Constants.BULK_UPLOAD_APPLICATION_RESOURCE_BUNDLE);
										invalidRecords.add(new String[] {
												classId, formatMsg });

										noOfInvalidRecord++;

										continue;
									}

								}
							}/*
								 * Completed All Validations Regarding File
								 * Format For Distributor
								 
						/* Completed All Validations Regarding File Format  

						/**
						 * inserting valid business user in database *
						  

						BulkBizUserCreationDTO bulkBizUserCreationDTO = new BulkBizUserCreationDTO();

						bulkBizUserCreationDTO.setUserMasterId(bulkBizUserCreationDTOParam.getUserMasterId());
						bulkBizUserCreationDTO.setParentId(bulkBizUserCreationDTOParam.getParentId());
						bulkBizUserCreationDTO
								.setCircleCode(bulkBizUserCreationDTOParam
										.getCircleCode());
						bulkBizUserCreationDTO
								.setLocationId(bulkBizUserCreationDTOParam
										.getLocationId());
						bulkBizUserCreationDTO.setCreatedBy(creatorUserId);
						bulkBizUserCreationDTO.setUploadedBy(bulkBizUserCreationDTOParam.getUploadedBy());

						bulkBizUserCreationDTO.setUserName(nextLine[0].trim());
						bulkBizUserCreationDTO.setContactNo(nextLine[1].trim());
						bulkBizUserCreationDTO.setRegistrationNo(nextLine[2]
								.trim());
						bulkBizUserCreationDTO.setHubCode(viewEditCircleMasterDAOImpl.findHubCodeForCircle(bulkBizUserCreationDTO.getCircleCode()));

						if (bulkBizUserCreationDTOParam.getUserMasterId() == Constants.DISTIBUTOR
								) {
							
							String services = "";
							if(!nextLine[4].trim().equals(Utility
									.getValueFromBundle(Constants.BIZ_USER_ALL_SERVICE_CLASS_TYPE,
											Constants.BULK_UPLOAD_APPLICATION_RESOURCE_BUNDLE)))
							{
								
								for (int i = 5; i < nextLine.length; i++) {
									if (!nextLine[i].trim().equals("")) {
										services += nextLine[i].trim()+",";
									}
								}
								if(services != null && !services.equals(""))
								{
									int index = -1;
									index =  services.lastIndexOf(",");
									if(index != -1)
									{
										services = services.substring(0, index);
									}
								}
							}
							
							bulkBizUserCreationDTO.setUserCode(nextLine[3]
									.trim());
							if (nextLine[4].trim().equals(Utility
									.getValueFromBundle(Constants.BIZ_USER_ALL_SERVICE_CLASS_TYPE,
											Constants.BULK_UPLOAD_APPLICATION_RESOURCE_BUNDLE))) {
								bulkBizUserCreationDTO
										.setAllServiceClass(Utility
												.getValueFromBundle(Constants.BIZ_USER_ALL_SERVICE_CLASS_FLAG_YES,
														Constants.BULK_UPLOAD_APPLICATION_RESOURCE_BUNDLE));
								bulkBizUserCreationDTO.setIncludeService(null);
								bulkBizUserCreationDTO.setExcludeService(null);
							} else {
								bulkBizUserCreationDTO.setAllServiceClass(null);
								if (nextLine[4]
										.trim()
										.equals(Utility
												.getValueFromBundle(Constants.BIZ_USER_INCLUDE_SERVICE_CLASS_TYPE,
														Constants.BULK_UPLOAD_APPLICATION_RESOURCE_BUNDLE))) {
									bulkBizUserCreationDTO
											.setIncludeService(services);
									bulkBizUserCreationDTO
											.setExcludeService(null);
								} else if (nextLine[4]
										.trim()
										.equals(Utility
												.getValueFromBundle(
												Constants.BIZ_USER_EXCLUDE_SERVICE_CLASS_TYPE,Constants.BULK_UPLOAD_APPLICATION_RESOURCE_BUNDLE))) {
									bulkBizUserCreationDTO
											.setIncludeService(null);
									bulkBizUserCreationDTO
											.setExcludeService(services);
								}
							}
						} else {
							bulkBizUserCreationDTO.setUserCode(null);
							bulkBizUserCreationDTO.setAllServiceClass(null);
							bulkBizUserCreationDTO.setIncludeService(null);
							bulkBizUserCreationDTO.setExcludeService(null);

						}
						bulkBizUserCreationDTO.setUserMasterId(bulkBizUserCreationDTOParam.getUserMasterId());
						

						success++;
						/** * insert valid record in business user data **  
						bulkBizUserDAO.insert(bulkBizUserCreationDTO, creatorUserId, connectionDistportal);					
					} 
					/**
					 * Calling a method which will write failed records to
					 * another file *
					  
					if (!invalidRecords.isEmpty()) {
						if (printWriter == null) {

							fileStoragePath = Utility
									.getValueFromBundle(
											Constants.FILE_STORAGE_PATH,
											Constants.BULK_UPLOAD_APPLICATION_RESOURCE_BUNDLE);
							failedfilePath = fileStoragePath + "Failed"
									+ bulkBizUserCreationDTOParam.getFileName();
							failedFile = new File(failedfilePath);
							printWriter = new PrintWriter(new FileWriter(
									failedFile));
						}

						Iterator itr = invalidRecords.iterator();
						while (itr.hasNext()) {
							String[] codes = (String[]) itr.next();
							String contents = codes[0] + "," + codes[1];
							printWriter.println(contents);
						}
					}

					fileCounter = 0;
					invalidRecords.clear();						
				
				}while (nextLine != null);

				/** upload failed file *  
				if (noOfInvalidRecord > 0) {
					String failedFilename = "Failed"+ bulkBizUserCreationDTOParam.getFileName();
					BulkBizUserCreationDTO bulkBizUserCreationDTOlOCAL = bulkBizUserCreationDTOParam;
					bulkBizUserCreationDTOlOCAL.setRelatedFileId(bulkBizUserCreationDTOParam.getFileId());
					relatedFileid = bulkBizUserDAO.insertFileInfoForBizUser(
							failedFilename, bulkBizUserCreationDTOParam,
							Constants.FILE_STATUS_FAILED_FILE,
							Constants.FILE_FAILED_MSG_FAILED,
							Constants.FILE_TYPE_BULK_BIZ_USER_FILE);
				}
				/**
				 * update the related file id , total success record and total
				 * failed records. *
				  
				log.info("updating file info after uploading ");
				bulkBizUserDAO.updatefileInfoAfterUploading(
						(int) relatedFileid, bulkBizUserCreationDTOParam.getFileId(),
						noOfInvalidRecord, success, connectionDistportal);				
				

				log.info("updating file info after uploading ");
			} else {

				/** file is allready upload or in the process of uploading *  
				log
						.debug("Life is already uploaded or in the process of uploading. ");
			}
			connectionDistportal.commit();
			//connectionDistportal1.commit();
			logger.debug("Exiting BulkBizUserCreationServiceImpl : uploadFile() ");
		} catch (Exception e) {
			try {
				
				
				/** file failed to upload *  
				connectionDistportal.rollback();				
				//connectionDistportal1.rollback();			
				
				bulkBizUserDAO.fileFailedToUpload(
						bulkBizUserCreationDTOParam.getFileId(), connectionDistportal);

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
				DBConnectionUtil.closeDBResources(connectionDistportal, null);
				
				
			} catch (Exception e) {
				log.error("Error" + e.getMessage(), e);
			}
		}
	}
*/
}
