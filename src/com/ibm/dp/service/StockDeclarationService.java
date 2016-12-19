package com.ibm.dp.service;

import java.sql.SQLException;
import java.util.List;

import com.ibm.dp.dto.DistributorDTO;
import com.ibm.dp.dto.DistributorDetailsDTO;
import com.ibm.dp.exception.DPServiceException;
import com.ibm.virtualization.recharge.exception.DAOException;
/**
 * @author Mohammad Aslam
 */
public interface StockDeclarationService {
	public List<DistributorDTO> getDistributors(String accountLavel, long accountId, long circleId) throws DPServiceException, DAOException,
			SQLException;

	public List<DistributorDetailsDTO> getDistributorDetails(String accountLavel, String distributorId, long circleId, long loginUserID) throws DPServiceException,
			DAOException, SQLException;
}
