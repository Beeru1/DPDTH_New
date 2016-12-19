package com.ibm.dp.service.impl;

import java.sql.Connection;

import org.apache.log4j.Logger;

import com.ibm.dp.dao.DistributorSTBMappingDao;
import com.ibm.dp.dao.SignInvoiceDao;
import com.ibm.dp.dao.impl.SignInvoiceDaoImpl;
import com.ibm.dp.service.SignInvoiceService;
import com.ibm.dp.webServices.local.SignInvoiceWebService;
import com.ibm.virtualization.recharge.common.Constants;
import com.ibm.virtualization.recharge.common.ResourceReader;
import com.ibm.virtualization.recharge.dao.DAOFactory;
import com.ibm.virtualization.recharge.db.DBConnectionManager;

public class SignInvoiceServiceImpl implements SignInvoiceService{
	
	private static  Logger logger = Logger.getLogger(SignInvoiceServiceImpl.class.getName());
	
	@Override
	public String[] updateStatus(String invoiceNo, String dist_olmId) {
		// TODO Auto-generated method stub
		Connection connection;
		String[] strServiceMsg = new String[2];
		strServiceMsg[0]="SUCCESS";
		strServiceMsg[1]="";
		
		try{
			connection = DBConnectionManager.getDBConnection();
			
			SignInvoiceDao signDao = DAOFactory.getDAOFactory(Integer.parseInt(
					ResourceReader.getCoreResourceBundleValue(Constants.DATABASE_TYPE))).getSignInvoiceDao(connection);
			strServiceMsg =  signDao.updateStatus(invoiceNo, dist_olmId);
			
		}
		catch (Exception e) {
			logger.error("Exception occured in SignInvoiceServiceImpl:" + e.getMessage());
			}
		
		return strServiceMsg;
	}

}
