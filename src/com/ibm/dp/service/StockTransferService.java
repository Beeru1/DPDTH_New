package com.ibm.dp.service;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import com.ibm.dp.beans.StockTransferFormBean;
import com.ibm.dp.dto.DistributorDTO;
import com.ibm.dp.dto.DistributorDetailsDTO;
import com.ibm.dp.dto.InterSSDTransferDTO;
import com.ibm.dp.exception.DPServiceException;
import com.ibm.virtualization.recharge.exception.DAOException;

/**
 * @author Mohammad Aslam
 */
public interface StockTransferService {	
	public List<DistributorDTO> getFromDistributors(long accountId) throws DPServiceException, DAOException, SQLException;
	public List<DistributorDTO> getToDistributors(long accountId) throws DPServiceException, DAOException, SQLException;
	public List<DistributorDetailsDTO> getTransferDetails(long accountId, long fromDistId, long toDistId) throws DPServiceException, DAOException, SQLException;
	public String getDCNumber() throws DPServiceException, DAOException, SQLException;
	public String submitTransferDetails(StockTransferFormBean stfb, List<DistributorDetailsDTO> distributorDetailsList, long accountId) throws DPServiceException, DAOException, SQLException;
	public String getContactNameForAccontId(long accountId) throws DPServiceException, DAOException, SQLException;
	public List getSerailNumberList(long accountId, long productId) throws DPServiceException, DAOException, SQLException;
	
	public List<InterSSDTransferDTO> interSSDTrans(Long accountId) throws DPServiceException, DAOException, SQLException;
	public Map interSSDTransDetails(String trans_no,String transType,String transsubtype,int circle_id) throws DPServiceException, DAOException, SQLException;
	//public List<InterSSDTransferDTO> distList(int circle_id) throws DPServiceException, DAOException, SQLException;
	public String hirarchyTransfer(List<InterSSDTransferDTO> list) throws DPServiceException, DAOException, SQLException;
	
	//public InterSSDTransferDTO getCurrentDist(String str) throws DPServiceException, DAOException, SQLException;
	
	
	
	
	
}
