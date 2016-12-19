package com.ibm.dp.service;

import java.util.List;

import com.ibm.dp.dto.SerializedStockReportDTO;


public interface SerializedStockReportService {

	public List<SerializedStockReportDTO> getSerializedStockReport(String circleId,String tsmId, String distId, String fseId, String retId, String productId, String date) throws Exception;
}
