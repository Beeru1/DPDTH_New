package com.ibm.dp.service.impl;

import java.sql.Connection;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.ListIterator;

import org.apache.log4j.Logger;

import com.ibm.dp.beans.DistStockAcceptTransferBean;
import com.ibm.dp.beans.DistStockDeclarationDetailsBean;
import com.ibm.dp.dao.DistStockDeclarationDao;
import com.ibm.dp.dao.impl.DistStockDeclarationDaoImpl;
import com.ibm.dp.dto.DistStockAccpDisplayDTO;
import com.ibm.dp.dto.DistStockDecOptionsDTO;
import com.ibm.dp.dto.DiststockAccpTransferDTO;
import com.ibm.dp.service.DistStockDeclService;
import com.ibm.dp.exception.DAOException;
import com.ibm.virtualization.recharge.exception.VirtualizationServiceException;

public class DistStockDeclServiceImpl implements DistStockDeclService{

private Logger logger = Logger.getLogger(DistStockDeclServiceImpl.class.getName());
	
	public void insertStockDecl(ListIterator<DistStockDeclarationDetailsBean> stockDeclarationDetailsBeanListItr) throws VirtualizationServiceException
	{
		Connection con=null;
		DistStockDeclarationDao stockDao = new DistStockDeclarationDaoImpl(con);
		try{
			 stockDao.insertStockDecl(stockDeclarationDetailsBeanListItr);
		}
		catch (DAOException de) {
			logger.error(" Exception occured : Message : " + de.getMessage());
			throw new VirtualizationServiceException(de.getMessage());
		}	
		
	}
	
	public ArrayList<DistStockDecOptionsDTO> fetchProdIdNameList(DistStockDeclarationDetailsBean stockDeclarationDetailsBean)throws VirtualizationServiceException , DAOException
	{
		Connection con=null;
		ArrayList<DistStockDecOptionsDTO> stockDecOptionsDTOList = new ArrayList<DistStockDecOptionsDTO>();
		DistStockDeclarationDao stockDao = new DistStockDeclarationDaoImpl(con);
		try{
			stockDecOptionsDTOList=stockDao.fetchProdIdNameList(stockDeclarationDetailsBean);
		}
		catch (DAOException de) {
			logger.error(" Exception occured : Message : " + de.getMessage());
			throw new VirtualizationServiceException(de.getMessage());
		}	
		
	return stockDecOptionsDTOList;
	}
	
	public ArrayList<DistStockDecOptionsDTO> fetchTransFormList(DistStockAcceptTransferBean stockAcceptTransBean)throws VirtualizationServiceException , DAOException
	{
		Connection con=null;
		ArrayList<DistStockDecOptionsDTO> stockDecOptionsDTOList = new ArrayList<DistStockDecOptionsDTO>();
		DistStockDeclarationDao stockDao = new DistStockDeclarationDaoImpl(con);
		try{
			stockDecOptionsDTOList=stockDao.fetchTransFormList(stockAcceptTransBean);
		}
		catch (DAOException de) {
			logger.error(" Exception occured : Message : " + de.getMessage());
			throw new VirtualizationServiceException(de.getMessage());
		}	
		
	return stockDecOptionsDTOList;
	}

	public ArrayList<DistStockDecOptionsDTO> getDCNoList(int accountId , int selectedValue)throws VirtualizationServiceException , DAOException
	{
		Connection con=null;
		ArrayList<DistStockDecOptionsDTO> stockDecOptionsDTOList = new ArrayList<DistStockDecOptionsDTO>();
		DistStockDeclarationDao stockDao = new DistStockDeclarationDaoImpl(con);
		try{
			stockDecOptionsDTOList=stockDao.getDCNoList(accountId,selectedValue);
		}
		catch (DAOException de) {
			logger.error(" Exception occured : Message : " + de.getMessage());
			throw new VirtualizationServiceException(de.getMessage());
		}	
		
	return stockDecOptionsDTOList;
	}	
	
	public HashMap<String ,ArrayList<DistStockAccpDisplayDTO>> stockAcceptInfo(DistStockAcceptTransferBean stockAcceptTransBean)throws VirtualizationServiceException , DAOException
	{
		Connection con=null;
		HashMap<String,ArrayList<DistStockAccpDisplayDTO>> stockAccpAccpDispDTOMap = new HashMap<String ,ArrayList<DistStockAccpDisplayDTO>>();
		DistStockDeclarationDao stockDao = new DistStockDeclarationDaoImpl(con);
		try{
			stockAccpAccpDispDTOMap=stockDao.stockAcceptInfo(stockAcceptTransBean);
		}
		catch (DAOException de) {
			logger.error(" Exception occured : Message : " + de.getMessage());
			throw new VirtualizationServiceException(de.getMessage());
		}	
		
	return stockAccpAccpDispDTOMap;
	}	
	
	public ArrayList<DiststockAccpTransferDTO> stockAccpDisplayList(DistStockAcceptTransferBean stockAcceptTransBean)throws VirtualizationServiceException , DAOException
	{
		Connection con=null;
		ArrayList<DiststockAccpTransferDTO> stockDecOptionsDTOList = new ArrayList<DiststockAccpTransferDTO>();
		DistStockDeclarationDao stockDao = new DistStockDeclarationDaoImpl(con);
		try{
			stockDecOptionsDTOList=stockDao.stockAccpDisplayList(stockAcceptTransBean);
		}
		catch (DAOException de) {
			logger.error(" Exception occured : Message : " + de.getMessage());
			throw new VirtualizationServiceException(de.getMessage());
		}	
		
	return stockDecOptionsDTOList;
	}
	
	public ArrayList<DiststockAccpTransferDTO> viewAllSerialNoOfStock(String dcNo)throws VirtualizationServiceException , DAOException
	{
		Connection con=null;
		ArrayList<DiststockAccpTransferDTO> stockDecOptionsDTOList = new ArrayList<DiststockAccpTransferDTO>();
		DistStockDeclarationDao stockDao = new DistStockDeclarationDaoImpl(con);
		try{
			stockDecOptionsDTOList=stockDao.viewAllSerialNoOfStock(dcNo);
		}
		catch (DAOException de) {
			logger.error(" Exception occured : Message : " + de.getMessage());
			throw new VirtualizationServiceException(de.getMessage());
		}	
		
	return stockDecOptionsDTOList;
	}
	
	public void updateAcceptStockTransfer(String[] arrCheckedSTA , String account_id)throws VirtualizationServiceException , DAOException
	{
		Connection con=null;
		ArrayList<DiststockAccpTransferDTO> stockDecOptionsDTOList = new ArrayList<DiststockAccpTransferDTO>();
		DistStockDeclarationDao stockDao = new DistStockDeclarationDaoImpl(con);
		try{
			stockDao.updateAcceptStockTransfer(arrCheckedSTA ,account_id);
		}
		catch (DAOException de) {
			logger.error(" Exception occured : Message : " + de.getMessage());
			throw new VirtualizationServiceException(de.getMessage());
		}	
		
	}
	
	
	
}
