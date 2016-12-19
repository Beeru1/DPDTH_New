package com.ibm.dp.dao;

import java.util.List;

import com.ibm.dp.dto.STBWiseSerializedStockReportDTO;
import com.ibm.dpmisreports.common.SelectionCollection;

public interface STBWiseSerializedStockReportDao {
	public List<STBWiseSerializedStockReportDTO> getSTBWiseSerializedStockReport(STBWiseSerializedStockReportDTO stDTO) throws Exception;
	public List<SelectionCollection> getSTBStatusList() throws Exception;

}
