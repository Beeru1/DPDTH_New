package com.ibm.dp.dao;

import java.util.List;

import com.ibm.dp.dto.CollectionReportDTO;
import com.ibm.dp.dto.InterSSDReportDTO;
import com.ibm.virtualization.recharge.exception.DAOException;

public interface DPInterSSDStockTransferReportDao {

	List<InterSSDReportDTO> getInterSSDStockTransferExcel(InterSSDReportDTO reportDto) throws DAOException;
}
