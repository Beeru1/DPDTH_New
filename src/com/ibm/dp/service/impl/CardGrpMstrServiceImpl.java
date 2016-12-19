package com.ibm.dp.service.impl;

import java.sql.Connection;
import java.util.ArrayList;
import java.util.List;

import com.ibm.dp.beans.DPCardGrpMstrFormBean;

import org.apache.log4j.Logger;
import com.ibm.dp.dao.CardGrpMstrDao;
import com.ibm.dp.dao.impl.CardGrpMstrDaoImpl;

import com.ibm.dp.dto.CardMstrDto;
import com.ibm.dp.exception.DPServiceException;
import com.ibm.dp.service.CardGrpMstrService;
import com.ibm.virtualization.recharge.common.Constants;
import com.ibm.virtualization.recharge.common.ResourceReader;
import com.ibm.virtualization.recharge.dao.DAOFactory;
import com.ibm.virtualization.recharge.dao.DPMasterDAO;
import com.ibm.virtualization.recharge.db.DBConnectionManager;
import com.ibm.virtualization.recharge.dto.DPViewProductDTO;
import com.ibm.virtualization.recharge.exception.DAOException;
import com.ibm.virtualization.recharge.exception.VirtualizationServiceException;

public class CardGrpMstrServiceImpl implements CardGrpMstrService
{
	private static  Logger logger = Logger.getLogger(CardGrpMstrServiceImpl.class.getName());

	private static CardGrpMstrService cardGrpMstrService = new CardGrpMstrServiceImpl();
	public CardGrpMstrServiceImpl() {}
	public static CardGrpMstrService getInstance() {
		return cardGrpMstrService;
	}
	private CardGrpMstrDao cardGrpMstrDao = CardGrpMstrDaoImpl.getInstance();

		
	
/*	public List<DebitAmountMstrFormBean> getProductList() throws DPServiceException 
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
		
	}*/
	
	public boolean insertCardGrp(DPCardGrpMstrFormBean dpCardGrpMstrFormBean) throws DPServiceException {
		
		logger.info("********************** insertDebitAmount() **************************************");
		boolean booMessage;
		try
		{
			booMessage =  cardGrpMstrDao.insertCardGrp(dpCardGrpMstrFormBean);
		}
		catch (Exception e) 
		{
			e.printStackTrace();
			throw new DPServiceException(e.getMessage());
		}
		return booMessage;
	}
	
	//ADDED BY VISHWAS
	public ArrayList<CardMstrDto> select(CardMstrDto dpvpDTO, int lowerBound, int upperBound)
	throws DPServiceException {
Connection connection = null;
ArrayList<CardMstrDto> productList = new ArrayList();
try {
	
	productList = cardGrpMstrDao.select(dpvpDTO, lowerBound, upperBound);

} catch (Exception e) 
{
	e.printStackTrace();
	throw new DPServiceException(e.getMessage());
}
	return productList;

}

	//END BY VISHWAS
	public boolean deleteCardGrp(DPCardGrpMstrFormBean dpCardGrpMstrFormBean) throws DPServiceException {
		
		logger.info("********************** insertDebitAmount() **************************************");
		boolean booMessage;
		try
		{
			booMessage =  cardGrpMstrDao.deleteCardGrp(dpCardGrpMstrFormBean);
		}
		catch (Exception e) 
		{
			e.printStackTrace();
			throw new DPServiceException(e.getMessage());
		}
		return booMessage;
	}
	
	
	@SuppressWarnings("unchecked")
	public List getCardGroupList() throws DPServiceException {
		
		logger.info("********************** getCardGroupList() **************************************");
		List<CardMstrDto> cardMstrList= new ArrayList<CardMstrDto>();
		try
		{
			cardMstrList =  cardGrpMstrDao.getCardGroupList();
		}
		catch (Exception e) 
		{
			e.printStackTrace();
			throw new DPServiceException(e.getMessage());
		}
		return cardMstrList;
	}
	
}
