package com.ibm.dp.service;

import java.io.InputStream;
import java.util.List;

import com.ibm.dp.dto.SerializedStockReportDTO;
import com.ibm.dp.dto.WHdistmappbulkDto;
import com.ibm.dp.exception.DPServiceException;


public interface WHdistmappbulkService {

	List uploadExcel(InputStream inputstream) throws DPServiceException;
	String addDistWarehouseMap(List list) throws DPServiceException;
	public List<WHdistmappbulkDto> getALLWhDistMapData() throws DPServiceException;
	
}
