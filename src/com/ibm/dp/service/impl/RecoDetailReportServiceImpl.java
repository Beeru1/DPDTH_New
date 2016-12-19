package com.ibm.dp.service.impl;

import java.util.ArrayList;
import java.util.List;

import com.ibm.dp.beans.RecoPeriodConfFormBean;
import com.ibm.dp.dto.PrintRecoDto;
import org.apache.log4j.Logger;

import com.ibm.dp.dao.RecoDetailReportDao;
import com.ibm.dp.dao.impl.RecoDetailReportDaoImpl;
import com.ibm.dp.dto.RecoDetailReportDTO;
import com.ibm.dp.dto.RecoPeriodDTO;
import com.ibm.dp.exception.DAOException;
import com.ibm.dp.exception.DPServiceException;
import com.ibm.dp.service.RecoDetailReportService;
import com.ibm.dpmisreports.common.SelectionCollection;



public class RecoDetailReportServiceImpl implements RecoDetailReportService
{
	private static  Logger logger = Logger.getLogger(RecoDetailReportServiceImpl.class.getName());

	private static RecoDetailReportService recoDetailReportService = new RecoDetailReportServiceImpl();
	private RecoDetailReportServiceImpl() {}
	public static RecoDetailReportService getInstance() {
		return recoDetailReportService;
	}
	private RecoDetailReportDao recoDetailReportDao = RecoDetailReportDaoImpl.getInstance();

		
	public List<RecoDetailReportDTO> getRecoReportDetails(int recoID,String circleId, String productIdArr, Integer groupId, long accountID) throws DPServiceException
	{
			List<RecoDetailReportDTO> reportRecoDataList = null;
			try {
				reportRecoDataList = recoDetailReportDao.getRecoReportDetails(recoID,circleId, productIdArr, groupId, accountID);
			} 
			catch (Exception e) {		
				logger.error("Exception occured in Generating Reco Detail in Service Impl:" + e.getMessage());
			}
		return reportRecoDataList;
	}
	
	public List<RecoPeriodDTO> getRecoHistory() throws DPServiceException
	{
			List<RecoPeriodDTO> recoHistoryList = null;
			try {
				recoHistoryList = recoDetailReportDao.getRecoHistory();
			} 
			catch (Exception e) {		
				logger.error("Exception occured in Generating Reco History in Service Impl:" + e.getMessage());
				e.printStackTrace();
				throw new DPServiceException(e.getMessage());
			}
		return recoHistoryList;
	}
	public List getRecoPrintList(String recoID,String circleid,String selectedProductId, int intSelectedTsmId ,String hiddenDistId ) throws DPServiceException
	{
		logger.info("********************** getRecoPrintList() **************************************");
		List printListDTO = null;
		try
		{
			printListDTO =  recoDetailReportDao.getRecoPrintList(recoID,circleid,selectedProductId,intSelectedTsmId, hiddenDistId);
		}
		catch (Exception e) 
		{
			e.printStackTrace();
			throw new DPServiceException(e.getMessage());
		}
		finally
		{			
			//Releasing the database connection
			
		}
		return printListDTO;
		
		
	}
	public List<SelectionCollection> getCircleList(long accountID) throws DPServiceException 
	{
		logger.info("********************** getRecoPrintList() **************************************");
		List<SelectionCollection> listReturn = null;
		try
		{
			listReturn =  recoDetailReportDao.getCircleListDAO(accountID);
		}
		catch (Exception e) 
		{
			e.printStackTrace();
			throw new DPServiceException(e.getMessage());
		}
		return listReturn;
		
	}

//added by aman
	
	
	public List<RecoPeriodConfFormBean> getTsmList(int circleId) throws DPServiceException
	{
		logger.info("********************** getRecoPrintList() **************************************");
		List<RecoPeriodConfFormBean> listReturn = null;
		try
		{
			listReturn =  recoDetailReportDao.getTsmList(circleId);
		}
		catch (Exception e) 
		{
			e.printStackTrace();
			throw new DPServiceException(e.getMessage());
		}
		return listReturn;
		
	}
	//end of changes by aman
	
	public List<RecoPeriodConfFormBean> getDistList(int tsmId) throws DPServiceException 
	{
		logger.info("********************** getRecoPrintList() **************************************");
		List<RecoPeriodConfFormBean> listReturn = null;
		try
		{
			listReturn =  recoDetailReportDao.getDistList(tsmId);
		}
		catch (Exception e) 
		{
			e.printStackTrace();
			throw new DPServiceException(e.getMessage());
		}
		return listReturn;
		
	}
	
}
