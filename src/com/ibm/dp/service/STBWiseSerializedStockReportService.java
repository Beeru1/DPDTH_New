package com.ibm.dp.service;

import java.util.List;

import com.ibm.dp.dto.STBWiseSerializedStockReportDTO;
import com.ibm.dpmisreports.common.SelectionCollection;


public interface STBWiseSerializedStockReportService {

	public List<STBWiseSerializedStockReportDTO> getSTBWiseSerializedStockReport(STBWiseSerializedStockReportDTO stDTO) throws Exception;
	public List<SelectionCollection> getSTBStatusList() throws Exception;
	
}
