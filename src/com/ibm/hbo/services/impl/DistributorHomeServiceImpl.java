package com.ibm.hbo.services.impl;

import java.util.ArrayList;

import org.apache.log4j.Logger;

import com.ibm.hbo.dao.DistributorHomeDAO;
import com.ibm.hbo.dao.HBOStockDAO;
import com.ibm.hbo.dao.impl.DistributorHomeDAOImpl;
import com.ibm.hbo.dao.impl.HBOStockDAOImpl;
import com.ibm.hbo.exception.DAOException;
import com.ibm.hbo.exception.HBOException;
import com.ibm.hbo.services.DistributorHomeService;

public class DistributorHomeServiceImpl implements DistributorHomeService {
	public static Logger logger = Logger.getRootLogger();
	 public ArrayList getDistributorStockSummary(long distId) throws HBOException{
		 
			ArrayList arrStockSumm = new ArrayList();
			DistributorHomeDAO dao = new DistributorHomeDAOImpl();
			try {
				arrStockSumm = dao.findByPrimaryKey(distId);
			} catch (Exception e) {
				if (e instanceof DAOException) {
					logger.error("DAOException occured in getDistributorStockSummary:"+ e.getMessage());
					throw new HBOException(e.getMessage());
				} else {
					logger.error("Exception occured in getDistributorStockSummary:"+ e.getMessage());
				}
			}
			return arrStockSumm;
		}
	public ArrayList getDistributorProdSummary(long id) throws HBOException {
		 
		ArrayList arrStockSumm = new ArrayList();
		DistributorHomeDAO dao = new DistributorHomeDAOImpl();
		try {
			arrStockSumm = dao.findByPrimaryKey(id,"cond");
		} catch (Exception e) {
			if (e instanceof DAOException) {
				logger.error("DAOException occured in getDistributorStockSummary:"+ e.getMessage());
				throw new HBOException(e.getMessage());
			} else {
				logger.error("Exception occured in getDistributorStockSummary:"+ e.getMessage());
			}
		}
		return arrStockSumm;
	}
}
