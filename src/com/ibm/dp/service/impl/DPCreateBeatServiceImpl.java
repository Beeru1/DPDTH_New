package com.ibm.dp.service.impl;

import java.sql.Connection;
import java.util.ArrayList;

import org.apache.log4j.Logger;

import com.ibm.dp.dao.DPCreateAccountDao;
import com.ibm.dp.dao.DPCreateBeatDao;
import com.ibm.dp.dto.DPCreateBeatDto;
import com.ibm.dp.service.DPCreateBeatService;
import com.ibm.virtualization.recharge.common.Constants;
import com.ibm.virtualization.recharge.common.ExceptionCode;
import com.ibm.virtualization.recharge.common.ResourceReader;
import com.ibm.virtualization.recharge.dao.CircleDao;
import com.ibm.virtualization.recharge.dao.DAOFactory;
import com.ibm.virtualization.recharge.db.DBConnectionManager;
import com.ibm.virtualization.recharge.exception.DAOException;
import com.ibm.virtualization.recharge.exception.VirtualizationServiceException;

public class DPCreateBeatServiceImpl implements DPCreateBeatService{
	
	private Logger logger = Logger.getLogger(DPCreateBeatServiceImpl.class.getName());
	public void insertBeat(DPCreateBeatDto createBeatDto) throws VirtualizationServiceException, DAOException{
		Connection connection = null;
		try {
			/* get the database connection */
			connection = DBConnectionManager.getDBConnection();
			DPCreateBeatDao beatDao = DAOFactory
					.getDAOFactory(
							Integer
									.parseInt(ResourceReader
											.getCoreResourceBundleValue(Constants.DATABASE_TYPE)))
					.createBeat(connection);
			int beatCode = beatDao.checkBeatNameAlreadyExist(createBeatDto.getBeatName());
			if(beatCode != -1){
				logger.info("Beat Code is :: "+beatCode);
				throw new DAOException(ExceptionCode.Account.ERROR_BEAT_EXISTS);
			}
			beatDao.insertBeat(createBeatDto);
		}catch(VirtualizationServiceException ve){
			ve.printStackTrace();
			logger.info("dpcreateDTO");
		}
	}
	
	public ArrayList getCities()throws VirtualizationServiceException, DAOException{
		Connection connection = null;
		ArrayList cityList=null;
		try {
			/* get the database connection */
			connection = DBConnectionManager.getDBConnection();
			DPCreateBeatDao beatDao = DAOFactory
					.getDAOFactory(
							Integer
									.parseInt(ResourceReader
											.getCoreResourceBundleValue(Constants.DATABASE_TYPE)))
					.createBeat(connection);
			cityList = beatDao.getCities();
			}catch(VirtualizationServiceException ve){
			ve.printStackTrace();
			logger.info("dpcreateDTO");
		}
		return cityList;
	}

	public ArrayList getAllBeats(DPCreateBeatDto beat, int lb, int ub) throws VirtualizationServiceException {
		ArrayList beatList;
		Connection connection = null;
		logger.info("Started...");
		try {
			/* get the database connection */
			connection = DBConnectionManager.getDBConnection();
			DPCreateBeatDao beatDao = DAOFactory.getDAOFactory(
					Integer.parseInt(ResourceReader.getCoreResourceBundleValue(Constants.DATABASE_TYPE)))
					.createBeat(connection);
			beatList = beatDao.getAllBeats(beat, lb, ub);
		} catch (DAOException de) {
			logger.error("DAOException Caught : " + de.getMessage());
			throw new VirtualizationServiceException(de.getMessage());
		} catch(VirtualizationServiceException virtualizationExp){
			logger.error("CircleServiceImpl:caught VirtualizationServiceException"+virtualizationExp.getMessage());
			throw new VirtualizationServiceException(virtualizationExp.getMessage());
		} finally {
			DBConnectionManager.releaseResources(connection);
		}
		logger.info("Executed ...Circle List returned successfully.");
		return beatList;
	}
	
	public ArrayList getAllcircles() throws NumberFormatException, VirtualizationServiceException{

		logger.info("Starting... ");
		Connection connection = null;
		ArrayList stateList = null;
		try {
			/* get the database connection */
			connection = DBConnectionManager.getDBConnection();
			DPCreateBeatDao beatDao = DAOFactory
					.getDAOFactory(
							Integer
									.parseInt(ResourceReader
											.getCoreResourceBundleValue(Constants.DATABASE_TYPE)))
					.createBeat(connection);

			stateList = beatDao.getAllCircles();

			if (stateList == null || stateList.size() == 0) {
				logger.info("Executed ::::No record Found  ");
				throw new VirtualizationServiceException(
						ExceptionCode.Account.ERROR_ACCOUNT_NO_RECORD_FOUND);
			}
		} catch (DAOException de) {
			logger.error(" Exception occured : Message : " + de.getMessage());
			throw new VirtualizationServiceException(de.getMessage());
		} finally {
			DBConnectionManager.releaseResources(connection);
		}
		logger.info("Executed ::::Successfully retrieved States List ");
		return stateList;
	}
	
	public ArrayList getZones(int circleId)
	throws VirtualizationServiceException {
		logger.info("Starting... ");
		Connection connection = null;
		ArrayList zoneList = null;
		try {
			/* get the database connection */
		connection = DBConnectionManager.getDBConnection();
		DPCreateBeatDao beatDao = DAOFactory
			.getDAOFactory(
					Integer
							.parseInt(ResourceReader
									.getCoreResourceBundleValue(Constants.DATABASE_TYPE)))
			.createBeat(connection);

	zoneList = beatDao.getZones(circleId);
	} catch (DAOException de) {
		logger.error(" Exception occured : Message : " + de.getMessage());
		throw new VirtualizationServiceException(de.getMessage());
	} finally {
		DBConnectionManager.releaseResources(connection);
	}
		logger.info("Executed ::::Successfully retrieved Cites List ");
		return zoneList;
	}
	
	public ArrayList getCites(int zoneId) throws VirtualizationServiceException {
		logger.info("Starting... ");
		Connection connection = null;
		ArrayList cityList = null;
		try {
			/* get the database connection */
			connection = DBConnectionManager.getDBConnection();
			DPCreateBeatDao beatDao = DAOFactory
					.getDAOFactory(
							Integer
									.parseInt(ResourceReader
											.getCoreResourceBundleValue(Constants.DATABASE_TYPE)))
					.createBeat(connection);

			cityList = beatDao.getCites(zoneId);
		} catch (DAOException de) {
			logger.error(" Exception occured : Message : " + de.getMessage());
			throw new VirtualizationServiceException(de.getMessage());
		} finally {
			DBConnectionManager.releaseResources(connection);
		}
		logger.info("Executed ::::Successfully retrieved Cites List ");
		return cityList;
	}
	
	public DPCreateBeatDto getBeatRecord (int beatId)throws VirtualizationServiceException{
		DPCreateBeatDto beat = new DPCreateBeatDto();
		Connection connection = null;
		try{
			connection = DBConnectionManager.getDBConnection();
			DPCreateBeatDao beatDao = DAOFactory
					.getDAOFactory(
							Integer
									.parseInt(ResourceReader
											.getCoreResourceBundleValue(Constants.DATABASE_TYPE)))
					.createBeat(connection);
			beat = beatDao.getBeatRecord(beatId);
		}catch (DAOException de) {
		logger.error(" Exception occured : Message : " + de.getMessage());
		throw new VirtualizationServiceException(de.getMessage());
	} finally {
		DBConnectionManager.releaseResources(connection);
	}
	logger.info("Executed ::::Successfully retrieved Cites List ");
	return beat;
	}
	
	public String updateBeat(DPCreateBeatDto beat) throws VirtualizationServiceException{
		String message = null;
		Connection connection = null;
		try{
			connection = DBConnectionManager.getDBConnection();
			DPCreateBeatDao beatDao = DAOFactory
					.getDAOFactory(
							Integer
									.parseInt(ResourceReader
											.getCoreResourceBundleValue(Constants.DATABASE_TYPE)))
					.createBeat(connection);
			message = beatDao.updateBeat(beat);
		}catch (DAOException de) {
		logger.error(" Exception occured : Message : " + de.getMessage());
		throw new VirtualizationServiceException(de.getMessage());
	} finally {
		DBConnectionManager.releaseResources(connection);
	}
		
		return message;
	}
	
	public int getAllBeatsCount(DPCreateBeatDto beat) throws VirtualizationServiceException
	{
		int beatCount;
		Connection connection = null;
		try {
			/* get the database connection */
			connection = DBConnectionManager.getDBConnection();
			DPCreateBeatDao beatDao = DAOFactory.getDAOFactory(
					Integer.parseInt(ResourceReader.getCoreResourceBundleValue(Constants.DATABASE_TYPE)))
					.createBeat(connection);
			beatCount = beatDao.getAllBeatsCount(beat);
		} catch (DAOException de) {
			logger.error("DAOException Caught : " + de.getMessage());
			throw new VirtualizationServiceException(de.getMessage());
		} catch(VirtualizationServiceException virtualizationExp){
			logger.error("CircleServiceImpl:caught VirtualizationServiceException"+virtualizationExp.getMessage());
			throw new VirtualizationServiceException(virtualizationExp.getMessage());
		} finally {
			DBConnectionManager.releaseResources(connection);
		}
		logger.info("Executed ...Circle List returned successfully.");
		return beatCount;
	}
}