package com.ibm.dp.service;

import java.util.List;

import com.ibm.dp.dto.ActivationDetailReportDTO;
import com.ibm.dp.dto.NegetiveEligibilityDTO;
import com.ibm.dp.dto.SerializedStockReportDTO;
import com.ibm.dp.exception.DPServiceException;


public interface NegetiveEligibiltyService {

	public List<NegetiveEligibilityDTO> getNegetiveEligibilityReport(String circleIds) throws DPServiceException;
}
