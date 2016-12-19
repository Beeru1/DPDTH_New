package com.ibm.virtualization.recharge.service.impl;

import java.sql.Connection;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import com.ibm.dp.dto.RejectedInvoiceDTO;
import com.ibm.dp.dto.RejectedPartnerInvDTO;
import com.ibm.virtualization.recharge.common.Constants;
import com.ibm.virtualization.recharge.common.ResourceReader;
import com.ibm.virtualization.recharge.dao.DAOFactory;
import com.ibm.virtualization.recharge.dao.RejectedInvDAO;
import com.ibm.virtualization.recharge.db.DBConnectionManager;
import com.ibm.virtualization.recharge.exception.VirtualizationServiceException;
import com.ibm.virtualization.recharge.service.RejectedInvoiceService;

public class RejectedInvoiceServiceImpl implements RejectedInvoiceService{
	Connection connection=null;
	@Override
	public List<RejectedInvoiceDTO> getRejectedInvExcel(String monthID) throws VirtualizationServiceException {
		// TODO Auto-generated method stubsss
		
		List<RejectedInvoiceDTO> invList=new ArrayList<RejectedInvoiceDTO>();
		try
		{ 
			connection = DBConnectionManager.getDBConnection();
			RejectedInvDAO  rejectedInvDao = DAOFactory.getDAOFactory(Integer.parseInt(ResourceReader.getCoreResourceBundleValue(Constants.DATABASE_TYPE))).getRejectedInvDAO(connection);
			invList = rejectedInvDao.getRejectedInvData(monthID);
		}
		catch (Exception e) {
			//	logger.error(" Exception occured : Message : " + de.getMessage());
				e.printStackTrace();
			}
		
		return invList;
	}

	@Override
	public List<RejectedPartnerInvDTO> getRejectedPartnerInvExcel(String monthID) throws VirtualizationServiceException {
		// TODO Auto-generated method stub
		//Connection connection=null;
		List<RejectedPartnerInvDTO> partnerList=new ArrayList<RejectedPartnerInvDTO>();
		try
		{ 
			connection = DBConnectionManager.getDBConnection();
			RejectedInvDAO  rejectedInvDao = DAOFactory.getDAOFactory(Integer.parseInt(ResourceReader.getCoreResourceBundleValue(Constants.DATABASE_TYPE))).getRejectedInvDAO(connection);
			//RejectedInvDAO  rejectedInvDao = new RejectedInvDAOImpl();
			partnerList = rejectedInvDao.getRejectedPartnerInv(monthID);
		}
		catch (Exception ex) {
			//	logger.error(" Exception occured : Message : " + de.getMessage());
				ex.printStackTrace();
			}
		
		return partnerList;
	}
	
	
	

	@Override
	public List<String> getDateList() throws VirtualizationServiceException {
		// TODO Auto-generated method stub
		ArrayList<String> dateList=new ArrayList<String>();
		Date dt=new Date();
		Calendar cal = Calendar.getInstance();
		//Calendar cal1 = Calendar.getInstance();
		cal.add(Calendar.MONTH, -1);
		Date dt1=cal.getTime();
		cal.add(Calendar.MONTH, -1);
		Date dt2=cal.getTime();
		cal.add(Calendar.MONTH, -1);
		Date dt3=cal.getTime();
		cal.add(Calendar.MONTH, -1);
		Date dt4=cal.getTime();
		cal.add(Calendar.MONTH, -1);
		Date dt5=cal.getTime();
		DateFormat formatter = new SimpleDateFormat("MM-yyyy");
		dateList.add(0, formatter.format(dt));
		dateList.add(1, formatter.format(dt1));
		dateList.add(2, formatter.format(dt2));
		dateList.add(3, formatter.format(dt3));
		dateList.add(4, formatter.format(dt4));
		dateList.add(5, formatter.format(dt5));
		return dateList;
	}

	
	
	
}
