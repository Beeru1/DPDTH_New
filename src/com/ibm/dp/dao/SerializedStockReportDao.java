package com.ibm.dp.dao;

import java.util.List;

import com.ibm.dp.dto.SerializedStockReportDTO;

public interface SerializedStockReportDao {
	public List<SerializedStockReportDTO> getSerializedStockReport(String circleId,String tsmId, String distId, String fseId, String retId, String productId, String date) throws Exception;

}
