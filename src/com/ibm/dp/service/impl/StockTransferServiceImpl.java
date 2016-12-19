package com.ibm.dp.service.impl;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;

import com.ibm.dp.beans.StockTransferFormBean;
import com.ibm.dp.common.Constants;
import com.ibm.dp.dao.StockTransferDao;
import com.ibm.dp.dto.DistributorDTO;
import com.ibm.dp.dto.DistributorDetailsDTO;
import com.ibm.dp.dto.InterSSDTransferDTO;
import com.ibm.dp.exception.DPServiceException;
import com.ibm.dp.service.StockTransferService;
import com.ibm.virtualization.recharge.common.ResourceReader;
import com.ibm.virtualization.recharge.dao.DAOFactory;
import com.ibm.virtualization.recharge.db.DBConnectionManager;
import com.ibm.virtualization.recharge.exception.DAOException;
/**
 * @author Mohammad Aslam
 */
public class StockTransferServiceImpl implements StockTransferService {

	Logger log = Logger.getLogger(StockTransferServiceImpl.class.getName());
	Connection connection = null;
	StockTransferDao std = null;
	
	public StockTransferServiceImpl() throws DPServiceException {
		try {
			connection = DBConnectionManager.getDBConnection();
			std = DAOFactory.getDAOFactory(Integer.parseInt(ResourceReader.getCoreResourceBundleValue(Constants.DATABASE_TYPE)))
							.getStockTransferDao(connection);
		} catch (Exception e) {
			throw new DPServiceException(e.getMessage());
		}
	}
		
	public List<DistributorDTO> getFromDistributors(long accountId) throws DPServiceException{
		List<DistributorDTO> distributorFromList = new ArrayList<DistributorDTO>();
		try {
			distributorFromList = std.getFromDistributors(accountId);
		} catch (Exception e) {
			log.error("**-> getFromDistributors function of  StockTransferServiceImpl " + e);
			throw new DPServiceException(e.getMessage());
		} finally {
			/* Close the connection */
			DBConnectionManager.releaseResources(connection);
		}
		return distributorFromList;
	}

	public List<DistributorDTO> getToDistributors(long accountId) throws DPServiceException{
		List<DistributorDTO> distributorToList = new ArrayList<DistributorDTO>();
		try {
			distributorToList = std.getToDistributors(accountId);
		} catch (Exception e) {
			log.error("**-> getToDistributors function of  StockTransferServiceImpl " + e);
			throw new DPServiceException(e.getMessage());
		} finally {
			/* Close the connection */
			DBConnectionManager.releaseResources(connection);
		}
		return distributorToList;
	}

	public List<DistributorDetailsDTO> getTransferDetails(long accountId, long fromDistId, long toDistId) throws DPServiceException{
		List<DistributorDetailsDTO> distributorDetailsList = new ArrayList<DistributorDetailsDTO>();
		try {
			distributorDetailsList = std.getTransferDetails(accountId, fromDistId, toDistId);
		} catch (Exception e) {
			log.error("**-> getTransferDetails function of  StockTransferServiceImpl " + e);
			throw new DPServiceException(e.getMessage());
		} finally {
			/* Close the connection */
			DBConnectionManager.releaseResources(connection);
		}
		return distributorDetailsList;
	}

	public String getDCNumber() throws DPServiceException {
		String dcNumber;
		try {
			dcNumber = std.getDCNumber();
		} catch (Exception e) {
			log.error("**-> getDCNumber function of  StockTransferServiceImpl " + e);
			throw new DPServiceException(e.getMessage());
		} finally {
			/* Close the connection */
			DBConnectionManager.releaseResources(connection);
		}
		return dcNumber;
	}

	public String submitTransferDetails(StockTransferFormBean stfb, List<DistributorDetailsDTO> distributorDetailsList, long accountId) throws DPServiceException, DAOException, SQLException {
		String submitResult;
		try {
			submitResult = std.submitTransferDetails(stfb, distributorDetailsList, accountId);
		} catch (Exception e) {
			log.error("**-> submitTransferDetails function of  StockTransferServiceImpl " + e);
			throw new DPServiceException(e.getMessage());
		} finally {
			/* Close the connection */
			DBConnectionManager.releaseResources(connection);
		}
		return submitResult;
	}
	
	public String getContactNameForAccontId(long accountId) throws DPServiceException{
		String contactName;
		try {
			contactName = std.getContactNameForAccontId(accountId);
		} catch (Exception e) {
			log.error("**-> getContactNameForAccontId function of  StockTransferServiceImpl " + e);
			throw new DPServiceException(e.getMessage());
		} finally {
			/* Close the connection */
			DBConnectionManager.releaseResources(connection);
		}
		return contactName;
	}

	public List getSerailNumberList(long accountId, long productId) throws DPServiceException {
		List serialNumberList = new ArrayList();
		try {
			serialNumberList = std.getSerailNumberList(accountId, productId);
		} catch (Exception e) {
			log.error("**-> getSerailNumberList function of  StockTransferServiceImpl " + e);
			throw new DPServiceException(e.getMessage());
		} finally {
			/* Close the connection */
			DBConnectionManager.releaseResources(connection);
		}
		return serialNumberList;
	}
	
	public List<InterSSDTransferDTO> interSSDTrans(Long accountId) throws DPServiceException{
		List<InterSSDTransferDTO> interssdDto = new ArrayList<InterSSDTransferDTO>();
		try {
			interssdDto = std.interSSDTrans(accountId);
		} catch (Exception e) {
			log.error("**-> getFromDistributors function of  StockTransferServiceImpl " + e);
			throw new DPServiceException(e.getMessage());
		} finally {
			/* Close the connection */
			DBConnectionManager.releaseResources(connection);
		}
		return interssdDto;
	}
	public Map interSSDTransDetails(String trans_no,String transType,String transsubtype,int circle_id) throws DPServiceException{
		List<InterSSDTransferDTO> interssdDto = new ArrayList<InterSSDTransferDTO>();
		Map hsmap = null;
		try {
			hsmap = std.interSSDTransDetails(trans_no,transType,transsubtype,circle_id);
		} catch (Exception e) {
			log.error("**-> getFromDistributors function of  StockTransferServiceImpl " + e);
			throw new DPServiceException(e.getMessage());
		} finally {
			/* Close the connection */
			DBConnectionManager.releaseResources(connection);
		}
		return hsmap;
	}
	/*public List<InterSSDTransferDTO> distList(int circle_id) throws DPServiceException{
		List<InterSSDTransferDTO> interssdDto = new ArrayList<InterSSDTransferDTO>();
		try {
			interssdDto = std.distList(circle_id);
		} catch (Exception e) {
			log.error("**-> getFromDistributors function of  StockTransferServiceImpl " + e);
			throw new DPServiceException(e.getMessage());
		} finally {
			 Close the connection 
			DBConnectionManager.releaseResources(connection);
		}
		return interssdDto;
	}*/
	
	public String hirarchyTransfer(List<InterSSDTransferDTO> list) throws DPServiceException{
		List<InterSSDTransferDTO> interssdDto = new ArrayList<InterSSDTransferDTO>();
		String str="";
		try {
			str = std.hirarchyTransfer(list);
		} catch (Exception e) {
			log.error("**-> getFromDistributors function of  StockTransferServiceImpl " + e);
			throw new DPServiceException(e.getMessage());
		} finally {
			/* Close the connection */
			DBConnectionManager.releaseResources(connection);
		}
		return str;
	}
	
	/*public InterSSDTransferDTO getCurrentDist(String str) throws DPServiceException{
		InterSSDTransferDTO interssdDto = null;
		
		try {
			interssdDto = std.getCurrentDist(str);
		} catch (Exception e) {
			log.error("**-> getFromDistributors function of  StockTransferServiceImpl " + e);
			throw new DPServiceException(e.getMessage());
		} finally {
			 Close the connection 
			DBConnectionManager.releaseResources(connection);
		}
		return interssdDto;
	}*/
	
	
}
