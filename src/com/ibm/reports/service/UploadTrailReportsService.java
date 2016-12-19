package com.ibm.reports.service;

import java.util.List;

import com.ibm.dp.exception.DPServiceException;
import com.ibm.reports.dto.UploadTrailReportDTO;
import com.ibm.reports.dto.UploadTrailReportsOutputDTO;
import com.ibm.reports.dto.UploadTrailReportsParameterDTO;
import com.ibm.virtualization.recharge.exception.DAOException;

public interface UploadTrailReportsService {

	List<UploadTrailReportDTO> getUploadTypeList() throws DAOException,
			DPServiceException;
	public UploadTrailReportsOutputDTO exportToExcel(UploadTrailReportsParameterDTO trailReportParameterDTO) throws DPServiceException,DAOException;
	

}
