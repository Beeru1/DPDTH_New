package com.ibm.dp.service.impl;

import java.util.List;

import org.apache.log4j.Logger;

import com.ibm.dp.dao.SerializedStockReportDao;
import com.ibm.dp.dao.impl.SerializedStockReportDaoImpl;
import com.ibm.dp.dto.SerializedStockReportDTO;
import com.ibm.dp.exception.DPServiceException;
import com.ibm.dp.service.SerializedStockReportService;



public class SerializedStockServiceImpl implements SerializedStockReportService
{
	private static  Logger logger = Logger.getLogger(SerializedStockServiceImpl.class.getName());

	private static SerializedStockReportService serializedStockReportService = new SerializedStockServiceImpl();
	private SerializedStockServiceImpl() {}
	public static SerializedStockReportService getInstance() {
		return serializedStockReportService;
	}
	private SerializedStockReportDao serializedStockReportDao = SerializedStockReportDaoImpl.getInstance();

		
	public List<SerializedStockReportDTO> getSerializedStockReport(String circleId,String tsmId, String distId, String fseId, String retId, String productId, String date) throws DPServiceException
	{
			List<SerializedStockReportDTO> reportStockDataList = null;
			try {
				reportStockDataList = serializedStockReportDao.getSerializedStockReport(circleId, tsmId, distId, fseId, retId, productId, date);
			} 
			catch (Exception e) {		
				logger.error("Exception occured in Generating Reco Detail in Service Impl:" + e.getMessage());
			}
		return reportStockDataList;
	}
	
	
}
