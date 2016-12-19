package com.ibm.hbo.services.impl;

import java.sql.Connection;

import org.apache.log4j.Logger;

import com.ibm.dp.dao.TransactionAuditDao;
import com.ibm.dp.dao.impl.TransactionAuditDaoImpl;
import com.ibm.hbo.dao.HBOStockDAO;
import com.ibm.hbo.dao.impl.HBOStockDAOImpl;
import com.ibm.hbo.dto.TransactionAuditDTO;
import com.ibm.hbo.exception.DAOException;
import com.ibm.hbo.services.TransactionAuditService;

public class TransactionAuditServiceImpl implements TransactionAuditService{

	public static Logger logger = Logger.getRootLogger();

	public void insertintoHistory(TransactionAuditDTO secondaryDto) throws DAOException {
		// TODO Auto-generated method stub
		Connection con = null;
		TransactionAuditDao secondarySaleDAO = new TransactionAuditDaoImpl(con);
		secondarySaleDAO.insertintoHistory(secondaryDto);
		
		
	}
	
}
