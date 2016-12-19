package com.ibm.dp.service;

import java.util.List;

import com.ibm.dp.dto.ActivationDetailReportDTO;
import com.ibm.dp.dto.SerializedStockReportDTO;


public interface ActivationDetailReportService {

	public List<ActivationDetailReportDTO> getActivationDetailReport(ActivationDetailReportDTO activationDetailReportDTO) throws Exception;
}
