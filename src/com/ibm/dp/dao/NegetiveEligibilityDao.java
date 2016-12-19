package com.ibm.dp.dao;

import java.util.List;

import com.ibm.dp.dto.NegetiveEligibilityDTO;
import com.ibm.virtualization.recharge.exception.DAOException;

public interface NegetiveEligibilityDao {
	public List<NegetiveEligibilityDTO> getNegetiveEligibilityReport(String circleIds) throws DAOException;

}
