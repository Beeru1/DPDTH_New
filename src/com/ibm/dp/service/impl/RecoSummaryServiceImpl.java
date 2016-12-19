package com.ibm.dp.service.impl;

import java.sql.Connection;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

import com.ibm.dp.dao.RecoSummaryDao;
import com.ibm.dp.dao.impl.RecoSummaryDaoImpl;
import com.ibm.dp.dto.RecoSummaryDTO;
import com.ibm.dp.exception.DPServiceException;
import com.ibm.dp.service.RecoSummaryService;
import com.ibm.virtualization.recharge.common.Constants;
import com.ibm.virtualization.recharge.common.ResourceReader;
import com.ibm.virtualization.recharge.dao.DAOFactory;
import com.ibm.virtualization.recharge.db.DBConnectionManager;
import com.ibm.virtualization.recharge.exception.DAOException;
import com.ibm.virtualization.ussdactivationweb.services.dto.CircleDTO;


public class RecoSummaryServiceImpl implements RecoSummaryService {
	

	Logger logger = Logger.getLogger(RecoSummaryServiceImpl.class.getName());
	Connection conn = null;
	
	public List<CircleDTO> getCircleList() throws DPServiceException, DAOException {
		

		List<CircleDTO> circleList = null;
		try {
					conn = DBConnectionManager.getDBConnection();
					RecoSummaryDao recoSummaryDao = new RecoSummaryDaoImpl(conn);
			
			
			circleList = recoSummaryDao.getCircleList();
		} catch (Exception e) {
			if (e instanceof DAOException) {
				logger.error("DAOException occured in getCircleList():" + e.getMessage());
				throw new DPServiceException(e.getMessage());
			} else {
				logger.error("Exception occured in getCircleList():" + e.getMessage());
			}
		}
		return circleList;
	}
	
	public ArrayList<RecoSummaryDTO> getReport(ArrayList<Integer> circleIdArrList, String status, int recoID) throws DPServiceException, DAOException {

		ArrayList<RecoSummaryDTO> reportList = null;
		
		try {
			conn = DBConnectionManager.getDBConnection();
			RecoSummaryDao recoSummaryDao = new RecoSummaryDaoImpl(conn);
			
					reportList = recoSummaryDao.getReport(circleIdArrList, status, recoID);
		} catch (Exception e) {
			if (e instanceof DAOException) {
				logger.error("DAOException occured in getReport():" + e.getMessage());
				throw new DPServiceException(e.getMessage());
			} else {
				logger.error("Exception occured in getReport():" + e.getMessage());
			}
		}
		return reportList;
	}
}