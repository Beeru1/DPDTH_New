package com.ibm.dp.dao;

import java.util.List;

import com.ibm.dp.dto.ActivationDetailReportDTO;

public interface ActivationDetailReportDao {
	public List<ActivationDetailReportDTO> getActivationDetailReport(ActivationDetailReportDTO activationDetailReportDTO) throws Exception;

}
