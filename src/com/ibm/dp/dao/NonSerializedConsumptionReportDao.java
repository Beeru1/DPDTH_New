package com.ibm.dp.dao;

import java.util.List;

import com.ibm.dp.dto.NonSerializedConsumptionReportDTO;
import com.ibm.dp.dto.CollectionReportDTO;
import com.ibm.dp.dto.PODetailReportDto;
import com.ibm.virtualization.recharge.exception.DAOException;
import com.ibm.virtualization.recharge.exception.VirtualizationServiceException;

public interface NonSerializedConsumptionReportDao {

	List<NonSerializedConsumptionReportDTO> getNonSerializedConsumptionExcel(NonSerializedConsumptionReportDTO reportDto) throws DAOException;
	
}