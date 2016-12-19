package com.ibm.dp.service.impl;

import java.sql.Connection;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

import com.ibm.dp.beans.ConsumptionPostingDetailReportBeans;
import com.ibm.dp.beans.DPReverseLogisticReportFormBean;
import com.ibm.dp.beans.StockSummaryReportBean;
import com.ibm.dp.dao.ConsumptionPostingDetailReportDao;
import com.ibm.dp.dao.DPReverseLogisticReportDao;
import com.ibm.dp.dao.StockSummaryReportDao;
import com.ibm.dp.dao.impl.ConsumptionPostingDetailReportDaoImpl;
import com.ibm.dp.dao.impl.DPReverseLogisticReportDaoImpl;
import com.ibm.dp.dao.impl.StockSummaryReportDaoImpl;
import com.ibm.dp.dto.ConsumptionPostingDetailReportDto;
import com.ibm.dp.dto.DistributorDTO;
import com.ibm.dp.exception.DPServiceException;
import com.ibm.dp.service.ConsumptionPostingDetailReportService;
import com.ibm.virtualization.recharge.db.DBConnectionManager;
import com.ibm.virtualization.recharge.exception.DAOException;
import com.ibm.virtualization.recharge.exception.VirtualizationServiceException;

public class ConsumptionPostingDetailReportServiceImpl implements ConsumptionPostingDetailReportService{
Logger logger = Logger.getLogger(ConsumptionPostingDetailReportServiceImpl.class.getName());
	
	public List<DistributorDTO> getAllDistList(String strLoginId,String strAcLeveid) throws DPServiceException, VirtualizationServiceException
	{
		logger.info("*********************in ConsumptionPostingDetailReportServiceImpl -> getAllDistList() starts **************************************");
		Connection con=null;
		ConsumptionPostingDetailReportDao revLogReportDao = new ConsumptionPostingDetailReportDaoImpl(con);
		List<DistributorDTO> dcHierarchyDTO = null;
		try
		{
			
			dcHierarchyDTO =  revLogReportDao.getAllDistList(strLoginId,strAcLeveid);
		}
		catch (DAOException de) {
			logger.error(" Exception occured : Message : " + de.getMessage());
			throw new VirtualizationServiceException(de.getMessage());
		}	
		catch (Exception e) 
		{
			e.printStackTrace();
			throw new DPServiceException(e.getMessage());
		}
		
		return dcHierarchyDTO;
		
		
	}
	
	public List<ConsumptionPostingDetailReportDto> getReportExcel(int distId , String fromDate,String toDate, String circleId,int selectedCircleId,String strTSMId) throws VirtualizationServiceException, DPServiceException
	{
		System.out.println("inside getRevLogDistributorStockReportExcel of )DPReportServiceImpl");
		Connection connection = null;
		
		List<ConsumptionPostingDetailReportDto> distributorStockReportList = new ArrayList<ConsumptionPostingDetailReportDto>();
		try
		{
			ConsumptionPostingDetailReportDao revLogReportDao = new ConsumptionPostingDetailReportDaoImpl(connection);
			distributorStockReportList = revLogReportDao.getReportExcel(distId , fromDate, toDate,circleId,selectedCircleId, strTSMId);
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
	
	public List getRevLogTsmAccountDetails(int levelId)throws VirtualizationServiceException
	{
		Connection con=null;
		ConsumptionPostingDetailReportDao revLogReportDao = new ConsumptionPostingDetailReportDaoImpl(con);
		List<ConsumptionPostingDetailReportBeans> revLogTsmAccDetailList = null;
		try
		{
			revLogTsmAccDetailList = revLogReportDao.getRevLogTsmAccountDetails( levelId);
		}
		catch (DAOException de) {
			logger.error(" Exception occured : Message : " + de.getMessage());
			throw new VirtualizationServiceException(de.getMessage());
		}	
		return revLogTsmAccDetailList;
	}
	
	public List getRevLogDistAccountDetails(int levelId , int circleId)throws VirtualizationServiceException
	{
		Connection con=null;
		ConsumptionPostingDetailReportDao revLogReportDao = new ConsumptionPostingDetailReportDaoImpl(con);
		List<ConsumptionPostingDetailReportBeans> revLogDistAccDetailList = null;
		try
		{
			revLogDistAccDetailList = revLogReportDao.getRevLogDistAccountDetails( levelId , circleId);
		}
		catch (DAOException de) {
			logger.error(" Exception occured : Message : " + de.getMessage());
			throw new VirtualizationServiceException(de.getMessage());
		}	
		return revLogDistAccDetailList;
	}
}
