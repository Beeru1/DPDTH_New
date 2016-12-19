package com.ibm.dp.dao;

import java.util.List;

import com.ibm.dp.dto.PODetailReportDto;
import com.ibm.dpmisreports.common.SelectionCollection;
import com.ibm.virtualization.recharge.exception.DAOException;

public interface PODetailReportDao {
	List<PODetailReportDto> getPoDetailExcel(PODetailReportDto reportDto) throws DAOException;
	List<SelectionCollection> getPoStatusList() throws DAOException;
}
