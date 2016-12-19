package com.ibm.dp.service.impl;

import java.util.ArrayList;
import java.util.List;

import com.ibm.dp.beans.DebitAmountMstrFormBean;
import com.ibm.dp.beans.RecoPeriodConfFormBean;
import com.ibm.dp.dto.PrintRecoDto;
import org.apache.log4j.Logger;

import com.ibm.dp.dao.DebitAmountMasterDao;
import com.ibm.dp.dao.RecoDetailReportDao;
import com.ibm.dp.dao.impl.DebitAmountMasterDaoImpl;
import com.ibm.dp.dao.impl.RecoDetailReportDaoImpl;
import com.ibm.dp.dto.RecoDetailReportDTO;
import com.ibm.dp.dto.RecoPeriodDTO;
import com.ibm.dp.exception.DAOException;
import com.ibm.dp.exception.DPServiceException;
import com.ibm.dp.service.DebitAmountMasterService;
import com.ibm.dp.service.RecoDetailReportService;
import com.ibm.dpmisreports.common.SelectionCollection;



public class DebitAmountMasterServiceImpl implements DebitAmountMasterService
{
	private static  Logger logger = Logger.getLogger(DebitAmountMasterServiceImpl.class.getName());

	private static DebitAmountMasterService debitAmountMasterService = new DebitAmountMasterServiceImpl();
	public DebitAmountMasterServiceImpl() {}
	public static DebitAmountMasterService getInstance() {
		return debitAmountMasterService;
	}
	private DebitAmountMasterDao debitAmountMasterDao = DebitAmountMasterDaoImpl.getInstance();

		
	
	public List<DebitAmountMstrFormBean> getProductList() throws DPServiceException 
	{
		logger.info("********************** getProdList() **************************************");
		List<DebitAmountMstrFormBean> listReturn = null;
		try
		{
			listReturn =  debitAmountMasterDao.getProductList();
		}
		catch (Exception e) 
		{
			e.printStackTrace();
			throw new DPServiceException(e.getMessage());
		}
		return listReturn;
		
	}
	
	public boolean insertDebitAmount(DebitAmountMstrFormBean debitAmountMstrFormBean) throws DPServiceException {
		
		logger.info("********************** insertDebitAmount() **************************************");
		boolean booMessage;
		try
		{
			booMessage =  debitAmountMasterDao.insertDebitAmount(debitAmountMstrFormBean);
		}
		catch (Exception e) 
		{
			e.printStackTrace();
			throw new DPServiceException(e.getMessage());
		}
		return booMessage;
	}
	
	
	public String getDebitAmount(DebitAmountMstrFormBean debitAmountMstrFormBean) throws DPServiceException {
		
		logger.info("********************** insertDebitAmount() **************************************");
		String amount;
		try
		{
			amount =  debitAmountMasterDao.getDebitAmount(debitAmountMstrFormBean);
		}
		catch (Exception e) 
		{
			e.printStackTrace();
			throw new DPServiceException(e.getMessage());
		}
		return amount;
	}
	
}
