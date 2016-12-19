package com.ibm.reports.dao;

import java.sql.ResultSet;
import java.util.List;

import com.ibm.reports.dto.UploadTrailReportDTO;
import com.ibm.reports.dto.UploadTrailReportsOutputDTO;
import com.ibm.reports.dto.UploadTrailReportsParameterDTO;
import com.ibm.virtualization.recharge.exception.DAOException;

public interface UploadTrailReportsDao {

	public List<UploadTrailReportDTO> getUploadTypeList();
	public UploadTrailReportsOutputDTO exportToExcel(UploadTrailReportsParameterDTO trailReportDTO) throws DAOException;
	public UploadTrailReportsOutputDTO getRegetResultSet(UploadTrailReportsOutputDTO trailReportParameterDTO, ResultSet rs)
			throws DAOException;

}
