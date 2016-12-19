package com.ibm.dp.dao;
/**
 * @author Mohammad Aslam
 */
import java.sql.SQLException;
import java.util.List;

import com.ibm.dp.dto.DistributorDTO;
import com.ibm.dp.dto.DistributorDetailsDTO;
import com.ibm.virtualization.recharge.exception.DAOException;

public interface StockDeclarationDao {
	public List<DistributorDTO> getDistributors(String accountLavel, long accountId, long circleId) throws DAOException, SQLException;

	public List<DistributorDetailsDTO> getDistributorDetails(String accountLavel, String distributorId, long circleId, long loginUserID) throws DAOException,
			SQLException;
}
