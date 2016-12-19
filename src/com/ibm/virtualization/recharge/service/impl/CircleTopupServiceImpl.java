/*****************************************************************************\
 **
 ** Virtualization - Recharge.
 **
 ** Copyright (c) 2007-2008 IBM.
 ** All Rights Reserved
 **
 **
 \****************************************************************************/
package com.ibm.virtualization.recharge.service.impl;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.sql.Connection;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

import com.ibm.virtualization.recharge.common.CSVReader;
import com.ibm.virtualization.recharge.common.Constants;
import com.ibm.virtualization.recharge.common.ExceptionCode;
import com.ibm.virtualization.recharge.common.ResourceReader;
import com.ibm.virtualization.recharge.common.TransactionType;
import com.ibm.virtualization.recharge.common.ValidatorFactory;
import com.ibm.virtualization.recharge.dao.CircleTopupConfigDao;
import com.ibm.virtualization.recharge.dao.DAOFactory;
import com.ibm.virtualization.recharge.db.DBConnectionManager;
import com.ibm.virtualization.recharge.dto.CircleTopupConfig;
import com.ibm.virtualization.recharge.dto.ReportInputs;
import com.ibm.virtualization.recharge.dto.UploadTopupSlab;
import com.ibm.virtualization.recharge.exception.DAOException;
import com.ibm.virtualization.recharge.exception.VirtualizationServiceException;
import com.ibm.virtualization.recharge.service.CircleTopupService;
import com.ibm.virtualization.recharge.validation.ValidatorUtility;

/**
 * This class provides implementation methods of interface CircleTopupService
 * 
 * @author Kumar Saurabh
 * 
 */
public class CircleTopupServiceImpl implements CircleTopupService {

	/* Logger for this class. */
	private Logger logger = Logger.getLogger(CircleTopupServiceImpl.class
			.getName());

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.ibm.virtualization.recharge.service.CircleTopupService#createCircleTopup(com.ibm.virtualization.recharge.dto.CircleTopupConfig)
	 */
	public void createCircleTopup(CircleTopupConfig circleTopupConfig)
			throws VirtualizationServiceException {
		logger.info("Started..." + circleTopupConfig);
		Connection connection = null;

		try {
			/* get the database connection */
			connection = DBConnectionManager.getDBConnection();
			CircleTopupConfigDao circleTopupDao = DAOFactory.getDAOFactory(
					Integer.parseInt(ResourceReader.getCoreResourceBundleValue(Constants.DATABASE_TYPE)))
					.getCircleTopupConfigDao(connection);

			/* DTO validation from Database goes here */

			ArrayList topupConfigList = null;
			try {
				topupConfigList = circleTopupDao.getCircleTopupList(
						circleTopupConfig.getCircleId(), circleTopupConfig
								.getStartAmount(), circleTopupConfig
								.getTillAmount(),
						TransactionType.valueOf(circleTopupConfig
								.getTransactionType()));
				if (topupConfigList == null) {
					logger
							.info("The TopUp-config is not defined so creating the configuration.");
				}
			} catch (DAOException de) {

				logger.info(" DAOException Occured .");
				throw new VirtualizationServiceException(de.getMessage());

			}
			// TODO find shourtcut
			if (topupConfigList != null) {
				for (Object o : topupConfigList) {
					CircleTopupConfig tempTopupConfig = (CircleTopupConfig) o;
					if (tempTopupConfig != null
							&& tempTopupConfig.getCircleId() > 0) {
						logger.info(" The TopUp-config is already defined .");
						throw new VirtualizationServiceException(
								ExceptionCode.SystemConfig.ERROR_TOPUP_OVERLAPPING);
					}
				}
			}
			// Call the method to insert Circle Topup Configuration
			// details
			circleTopupDao.insertCircleTopup(circleTopupConfig);
			/* Commit the transaction */
			DBConnectionManager.commitTransaction(connection);

		} catch (DAOException de) {
			logger.error("DAOException occured : " + "Exception Message: "
					+ de.getMessage());
			throw new VirtualizationServiceException(de.getMessage());
		} catch (VirtualizationServiceException virtualizationExp) {
			logger
					.error("CircleTopupServiceImpl:caught VirtualizationServiceException"
							+ virtualizationExp.getMessage());
			throw new VirtualizationServiceException(virtualizationExp
					.getMessage());
		} finally {
			DBConnectionManager.releaseResources(connection);
		}
		logger.info("Executed ::::");
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.ibm.virtualization.recharge.service.CircleTopupService#getCircleTopupList(com.ibm.virtualization.recharge.dto.ReportInputDTO,
	 *      int, int)
	 */
	public List getCircleTopupList(ReportInputs inputDto, int lb, int ub)
			throws VirtualizationServiceException {
		logger.info("Started... where circle_Id = " + inputDto.getCircleId());
		List topupList = null;
		Connection connection = null;

		try {
			/* get the database connection */
			connection = DBConnectionManager.getDBConnection();
			CircleTopupConfigDao circleTopupDao = DAOFactory.getDAOFactory(
					Integer.parseInt(ResourceReader.getCoreResourceBundleValue(Constants.DATABASE_TYPE)))
					.getCircleTopupConfigDao(connection);
			topupList = circleTopupDao.getCircleTopupList(inputDto, lb, ub);
			if (0 >= topupList.size()) {
				throw new VirtualizationServiceException(
						ExceptionCode.ERROR_NO_RECORD_EXIST);
			}
		} catch (DAOException de) {
			logger.error("DAOException Caught : " + de.getMessage());
			throw new VirtualizationServiceException(de.getMessage());
		} catch (VirtualizationServiceException virtualizationExp) {
			logger
					.error("CircleTopupServiceImpl:caught VirtualizationServiceException"
							+ virtualizationExp.getMessage());
			throw new VirtualizationServiceException(virtualizationExp
					.getMessage());
		} finally {
			DBConnectionManager.releaseResources(connection);
		}
		logger.info("Executed ::::");
		return topupList;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.ibm.virtualization.recharge.service.CircleTopupService#getCircleTopupList(com.ibm.virtualization.recharge.dto.ReportInputDTO)
	 */
	public List getCircleTopupList(ReportInputs inputDto)
			throws VirtualizationServiceException {
		logger.info("Started... where circle_Id = " + inputDto.getCircleId());
		List topupList = null;
		Connection connection = null;

		try {
			/* get the database connection */
			connection = DBConnectionManager.getDBConnection();
			CircleTopupConfigDao circleTopupDao = DAOFactory.getDAOFactory(
					Integer.parseInt(ResourceReader.getCoreResourceBundleValue(Constants.DATABASE_TYPE)))
					.getCircleTopupConfigDao(connection);
			topupList = circleTopupDao.getCircleTopupList(inputDto);
			if (0 >= topupList.size()) {
				throw new VirtualizationServiceException(
						ExceptionCode.ERROR_NO_RECORD_EXIST);
			}
		} catch (DAOException de) {
			logger.error("DAOException Caught : " + de.getMessage());
			throw new VirtualizationServiceException(de.getMessage());
		} catch (VirtualizationServiceException virtualizationExp) {
			logger
					.error("CircleTopupServiceImpl:caught VirtualizationServiceException"
							+ virtualizationExp.getMessage());
			throw new VirtualizationServiceException(virtualizationExp
					.getMessage());
		} finally {
			DBConnectionManager.releaseResources(connection);
		}
		logger.info("Executed ::::");
		return topupList;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.ibm.virtualization.recharge.service.CircleTopupService#getCircleTopupListCount(com.ibm.virtualization.recharge.dto.ReportInputDTO)
	 */
	public int getCircleTopupListCount(ReportInputs inputDto)
			throws VirtualizationServiceException {
		logger.info("Started...getUsersCountList()");
		Connection connection = null;
		int noOfRows = 0;
		try {
			/* get the database connection */
			connection = DBConnectionManager.getDBConnection();
			CircleTopupConfigDao circleTopupDao = DAOFactory.getDAOFactory(
					Integer.parseInt(ResourceReader.getCoreResourceBundleValue(Constants.DATABASE_TYPE)))
					.getCircleTopupConfigDao(connection);

			/* fetch total no of circles */
			noOfRows = circleTopupDao.getCircleTopupListCount(inputDto);
		} catch (DAOException de) {
			logger.fatal("Exception " + de.getMessage()
					+ " Occured While counting number of rows" + noOfRows);
			throw new VirtualizationServiceException(de.getMessage());
		} catch (VirtualizationServiceException virtualizationExp) {
			logger
					.error("CircleTopupServiceImpl:caught VirtualizationServiceException"
							+ virtualizationExp.getMessage());
			throw new VirtualizationServiceException(virtualizationExp
					.getMessage());
		} finally {
			DBConnectionManager.releaseResources(connection);
		}
		logger.info("Executed :::: successfully count no of rows " + noOfRows);
		return noOfRows;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.ibm.virtualization.recharge.service.CircleTopupService#getCircleTopupOfId(int)
	 */
	public CircleTopupConfig getCircleTopupOfId(int topupConfigId)
			throws VirtualizationServiceException {
		logger.info("Started... where topupConfigId = " + topupConfigId);
		CircleTopupConfig circleTopupConfig = null;
		Connection connection = null;
		try {
			/* get the database connection */
			connection = DBConnectionManager.getDBConnection();
			CircleTopupConfigDao circleTopupDao = DAOFactory.getDAOFactory(
					Integer.parseInt(ResourceReader.getCoreResourceBundleValue(Constants.DATABASE_TYPE)))
					.getCircleTopupConfigDao(connection);
			circleTopupConfig = circleTopupDao
					.getCircleTopupOfId(topupConfigId);
		} catch (DAOException de) {
			logger.error(" DAOException Caught : " + de.getMessage());
			throw new VirtualizationServiceException(de.getMessage());
		} catch (VirtualizationServiceException virtualizationExp) {
			logger
					.error("CircleTopupServiceImpl:caught VirtualizationServiceException"
							+ virtualizationExp.getMessage());
			throw new VirtualizationServiceException(virtualizationExp
					.getMessage());
		} finally {
			DBConnectionManager.releaseResources(connection);
		}
		logger.info("Executed ::::");
		return circleTopupConfig;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.ibm.virtualization.recharge.service.CircleTopupService#updateCircleTopup(com.ibm.virtualization.recharge.dto.CircleTopupConfig)
	 */
	public void updateCircleTopup(CircleTopupConfig circleTopupConfig)
			throws VirtualizationServiceException {
		logger.info("Started..." + circleTopupConfig);
		Connection connection = null;
		if (!(circleTopupConfig == null)) {
			try {
				/* get the database connection */
				connection = DBConnectionManager.getDBConnection();
				CircleTopupConfigDao circleTopupDao = DAOFactory.getDAOFactory(
						Integer.parseInt(ResourceReader.getCoreResourceBundleValue(Constants.DATABASE_TYPE)))
						.getCircleTopupConfigDao(connection);
				/* DTO validation from Database goes here */
				ArrayList topupConfigList = null;
				try {
					logger.info("getCircleId="
							+ circleTopupConfig.getCircleId());
					logger.info("getStartAmount="
							+ circleTopupConfig.getStartAmount());
					logger.info("getTransactionType="
							+ TransactionType.valueOf(circleTopupConfig
									.getTransactionType()));
					if (circleTopupConfig.getStatus().equals(
							Constants.ACTIVE_STATUS)) {
						topupConfigList = circleTopupDao.getCircleTopupList(
								circleTopupConfig.getCircleId(),
								circleTopupConfig.getStartAmount(),
								circleTopupConfig.getTillAmount(),
								TransactionType.valueOf(circleTopupConfig
										.getTransactionType()));
					}
				} catch (DAOException de) {

					logger.info(" DAOException Occured .");
					throw new VirtualizationServiceException(de.getMessage());
				}

				if ((topupConfigList == null) || (topupConfigList.size() == 0)) {
					logger
							.info("The TopUp-config is not defined so creating the configuration.");
					// Call the method to insert Circle Topup
					circleTopupDao.updateCircleTopup(circleTopupConfig);
					/* Commit the transaction */
					DBConnectionManager.commitTransaction(connection);
				} else {
					for (Object o : topupConfigList) {
						CircleTopupConfig tempTopupConfig = (CircleTopupConfig) o;
						if (tempTopupConfig.getTopupConfigId() != circleTopupConfig
								.getTopupConfigId()) {
							logger
									.info(" The TopUp-config is already defined .");
							throw new VirtualizationServiceException(
									ExceptionCode.SystemConfig.ERROR_TOPUP_OVERLAPPING);
						}
					}
					// Call the method to insert Circle Topup
					circleTopupDao.updateCircleTopup(circleTopupConfig);
					/* Commit the transaction */
					DBConnectionManager.commitTransaction(connection);
				}

			} catch (DAOException de) {
				logger.fatal("DAOException occured while update : "
						+ "Exception Message: " + de.getMessage());
				throw new VirtualizationServiceException(de.getMessage());
			} catch (VirtualizationServiceException virtualizationExp) {
				logger
						.error("CircleTopupServiceImpl:caught VirtualizationServiceException"
								+ virtualizationExp.getMessage());
				throw new VirtualizationServiceException(virtualizationExp
						.getMessage());
			} finally {
				DBConnectionManager.releaseResources(connection);
			}
			logger.info("Executed ::::");
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.ibm.virtualization.recharge.service.CircleTopupService#uploadSlabData(com.ibm.virtualization.recharge.dto.UploadTopupSlab)
	 */

	public int[] uploadSlabData(UploadTopupSlab topupDto)
			throws VirtualizationServiceException {
		logger.info("Starting uploadSlabData ..");
		Connection connection = null;
		String[] nextLine = null;
		int rowsUpdated;
		int rowsNotUpdated;
		UploadTopupSlab circleTopupDto = null;
		int count = 0;
		int[] numRows;
		ArrayList<UploadTopupSlab> circleTopupDtoList = new ArrayList<UploadTopupSlab>();
		ArrayList<UploadTopupSlab> topupIncorrectDtoList = new ArrayList<UploadTopupSlab>();

		String seperator = ResourceReader
				.getValueFromBundle("topup.bulkupload.fileseperator",
						Constants.WEB_RESOURCE_BUNDLE);

		char fileSeperator = seperator.charAt(0);
		int rowscount = Integer.parseInt(ResourceReader.getValueFromBundle(
				"topup.bulkupload.ignorerows.count",
				Constants.WEB_RESOURCE_BUNDLE));

		String processingCode = ResourceReader.getValueFromBundle(
				"topup.bulkupload.processingcode",
				Constants.WEB_RESOURCE_BUNDLE);

		try {
			CSVReader reader = new CSVReader(new FileReader(topupDto
					.getFilePath()), fileSeperator);

			logger.info("file path..." + topupDto.getFilePath());

			/* get the database connection */
			connection = DBConnectionManager.getDBConnection();
			CircleTopupConfigDao circleTopupDao = DAOFactory.getDAOFactory(
					Integer.parseInt(ResourceReader.getCoreResourceBundleValue(Constants.DATABASE_TYPE)))
					.getCircleTopupConfigDao(connection);
			int circleId = topupDto.getCircleId();
			String transactionType = topupDto.getTransactionType();
			long createdBy = topupDto.getCreatedBy();
			int previousSlab = 0;
			int currentSlab = previousSlab;
			boolean isFirstLine = true;
			int row = 0;

			while ((nextLine = reader.readNext()) != null) {
				row = row + 1;

				if (row > rowscount) {
					circleTopupDto = new UploadTopupSlab();
					try {
						count = count + 1;
						if (ValidatorUtility.isValidAmount(nextLine[0])) {
							currentSlab = Integer.parseInt(nextLine[0]);
							if (isFirstLine) {
								isFirstLine = false;
								circleTopupDto.setStartAmount(nextLine[0]);
								circleTopupDto.setTillAmount(nextLine[0]);
							} else {

								circleTopupDto
										.setStartAmount((previousSlab + 1) + "");
								circleTopupDto.setTillAmount(currentSlab + "");
							}
						} else {
							circleTopupDto.setStartAmount(nextLine[0]);
						}

						circleTopupDto.setEspCommission(nextLine[1].replace(
								"%", ""));
						circleTopupDto.setPspCommission(nextLine[2].replace(
								"%", ""));
						circleTopupDto.setServiceTax(nextLine[3].replace("%",
								""));
						circleTopupDto.setProcessingFee(nextLine[4]);
						circleTopupDto.setInCardGroup(nextLine[6]);
						circleTopupDto.setProcessingCode(processingCode);
						circleTopupDto.setValidity(nextLine[5]);
						previousSlab = currentSlab;

						logger.info("validate called.....");
						ValidatorFactory.getCircleTopupConfigValidator()
								.validateInsert(circleTopupDto);
						circleTopupDtoList.add(circleTopupDto);
						logger.info("Added in arraylist.....");

					} catch (VirtualizationServiceException vsExp) {
						logger.error("DAOException occured while update : "
								+ "Exception Message: " + vsExp.getMessage());
						logger.info("Record upload failed..... ");
						logger.info("DTO" + circleTopupDto);
						topupIncorrectDtoList.add(circleTopupDto);

					}
				}// end of if loop
			}// end of while loop

		//	circleTopupDto.setTopupIncorrectList(topupIncorrectDtoList);
			logger.debug("count value" + count);
			rowsUpdated = circleTopupDao.uploadSlabData(circleTopupDtoList,
					circleId, transactionType, createdBy);

			rowsNotUpdated = circleTopupDao
					.uploadSlabIncorrectData(topupIncorrectDtoList, circleId,
							transactionType, createdBy);

			// rowsNotUpdated = count - rowsUpdated;
			numRows = new int[2];
			numRows[0] = rowsUpdated;
			numRows[1] = rowsNotUpdated;

			/* Commit the transaction */
			DBConnectionManager.commitTransaction(connection);
		} catch (ArrayIndexOutOfBoundsException aibExp) {
			logger.error("Exception occured while parsing.Exception Message: ",
					aibExp);
			throw new VirtualizationServiceException(
					ExceptionCode.SlabConfiguration.ERROR_INCORRECT_FILE_FORMAT);
		} catch (NumberFormatException numExp) {
			logger.error("Exception occured while parsing.Exception Message: ",
					numExp);
			throw new VirtualizationServiceException(
					ExceptionCode.SlabConfiguration.ERROR_INCORRECT_FILE_FORMAT);
		} catch (FileNotFoundException fnfExp) {
			logger.error("File not Found!" + fnfExp.getMessage());
			throw new VirtualizationServiceException(fnfExp.getMessage());
		} catch (IOException ioExp) {
			logger
					.error("Exception Occurs During File writing.Please Try Again Later!"
							+ ioExp.getMessage());
			throw new VirtualizationServiceException(ioExp.getMessage());
		} catch (DAOException daoExp) {
			logger.error("DAOException occured while update : "
					+ "Exception Message: " + daoExp.getMessage());
			throw new VirtualizationServiceException(daoExp.getMessage());
		} finally {
			DBConnectionManager.releaseResources(connection);
		}
		logger.info("Executed uploadSlabData... ");
		return numRows;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.ibm.virtualization.recharge.service.CircleTopupService#getTopupConfigFailData(int,int)
	 */
	public ArrayList getTopupConfigFailData(int circleId)
			throws VirtualizationServiceException {
		logger.info("Started getTopupConfigFailData... where circleId = "
				+ circleId);
		Connection connection = null;
		ArrayList topupIncorrectDataList = null;

		try {
			// get the database connection
			connection = DBConnectionManager.getDBConnection();
			CircleTopupConfigDao circleTopupDao = DAOFactory.getDAOFactory(
					Integer.parseInt(ResourceReader.getCoreResourceBundleValue(Constants.DATABASE_TYPE)))
					.getCircleTopupConfigDao(connection);
			topupIncorrectDataList = circleTopupDao
					.getTopupConfigFailData(circleId);
		} catch (DAOException de) {
			logger.error(" DAOException Caught : " + de.getMessage());
			throw new VirtualizationServiceException(de.getMessage());
		} catch (VirtualizationServiceException virtualizationExp) {
			logger
					.error("CircleTopupServiceImpl:caught VirtualizationServiceException"
							+ virtualizationExp.getMessage());
			throw new VirtualizationServiceException(virtualizationExp
					.getMessage());
		} finally {
			DBConnectionManager.releaseResources(connection);
		}
		logger.info("Executed ::::");
		return topupIncorrectDataList;
	}

}
