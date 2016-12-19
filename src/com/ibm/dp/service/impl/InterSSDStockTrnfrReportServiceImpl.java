package com.ibm.dp.service.impl;

import java.sql.Connection;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

import com.ibm.dp.dao.InterSSDStockTrnfrReportDao;
import com.ibm.dp.dao.impl.InterSSDStockTrnfrReportDaoImpl;
import com.ibm.dp.dto.InterSSDStockTrnfrReportDto;
import com.ibm.dp.exception.DPServiceException;
import com.ibm.dp.service.InterSSDStockTrnfrReportService;
import com.ibm.virtualization.recharge.exception.DAOException;
import com.ibm.virtualization.recharge.exception.VirtualizationServiceException;

public class InterSSDStockTrnfrReportServiceImpl implements InterSSDStockTrnfrReportService{

	Logger logger = Logger.getLogger(InterSSDStockTrnfrReportServiceImpl.class.getName());
	
	public List<InterSSDStockTrnfrReportDto> getReportExcel(String circleId , String fromDate,String toDate,int loginRole,String loginCircleId) throws VirtualizationServiceException, DPServiceException
	{
		System.out.println("inside getRevLogDistributorStockReportExcel of )DPReportServiceImpl");
		Connection connection = null;
		
		List<InterSSDStockTrnfrReportDto> distributorStockReportList = new ArrayList<InterSSDStockTrnfrReportDto>();
		try
		{
			InterSSDStockTrnfrReportDao revLogReportDao = new InterSSDStockTrnfrReportDaoImpl(connection);
			distributorStockReportList = revLogReportDao.getReportExcel(circleId , fromDate, toDate,loginRole,loginCircleId);
		}catch (DAOException de) 
		{
			logger.error(" Exception occured : Message : " + de.getMessage());
			throw new VirtualizationServiceException(de.getMessage());
		}catch (Exception e) 
		{
			e.printStackTrace();
			throw new DPServiceException(e.getMessage());
		}
		
		return distributorStockReportList;
	}
}
