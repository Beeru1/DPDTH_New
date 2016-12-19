package com.ibm.reports.service.impl;

import java.util.List;

import org.apache.log4j.Logger;

import com.ibm.dp.dao.DPPrintBulkAcceptanceDao;
import com.ibm.dp.dao.impl.DPPrintBulkAcceptanceDaoImpl;
import com.ibm.dp.dto.DPPrintBulkAcceptanceDTO;
import com.ibm.dp.service.DPPrintBulkAcceptanceService;

public class DPPrintBulkAcceptanceServiceImpl implements DPPrintBulkAcceptanceService{
	private Logger logger = Logger.getLogger(DPPrintBulkAcceptanceServiceImpl.class.getName());

	public int getInvoiceQty(String invoice_no){
		logger.info("********************** getInvoiceQty() **************************************");
		int invoiceQty=0;
		DPPrintBulkAcceptanceDao printBulkAcceptanceDao = DPPrintBulkAcceptanceDaoImpl.getInstance();
		try {
			invoiceQty = printBulkAcceptanceDao.getInvoiceQty(invoice_no);
		} catch (Exception e) {
			logger.error("Exception occured::" + e.getMessage());
			logger.error(e.getMessage(), e);
		
		}
		return invoiceQty;
	}

	public List<DPPrintBulkAcceptanceDTO> getCorrectSTB(String dist_id, String invoice_no) {
		// TODO Auto-generated method stub
		logger.info("********************** getCorrectSTB() **************************************");
		List<DPPrintBulkAcceptanceDTO> correctSTB=null;
		DPPrintBulkAcceptanceDao printBulkAcceptanceDao = DPPrintBulkAcceptanceDaoImpl.getInstance();
		try {
			correctSTB = printBulkAcceptanceDao.getCorrectSTB(dist_id,invoice_no);
		} catch (Exception e) {
			logger.error("Exception occured::" + e.getMessage());
			logger.error(e.getMessage(), e);
		
		}
		return correctSTB;
	}

	public List<DPPrintBulkAcceptanceDTO> getMissingSTB(String dist_id, String invoiceNo) {
		// TODO Auto-generated method stub
		logger.info("********************** getCorrectSTB() **************************************");
		List<DPPrintBulkAcceptanceDTO> correctSTB=null;
		DPPrintBulkAcceptanceDao printBulkAcceptanceDao = DPPrintBulkAcceptanceDaoImpl.getInstance();
		try {
			correctSTB = printBulkAcceptanceDao.getMissingSTB(dist_id,invoiceNo);
		} catch (Exception e) {
			logger.error("Exception occured::" + e.getMessage());
			logger.error(e.getMessage(), e);
		
		}
		return correctSTB;
	}

	public List<DPPrintBulkAcceptanceDTO> getWrongShippedSTB(String dist_id, String invoiceNo) {
		// TODO Auto-generated method stub
		logger.info("********************** getCorrectSTB() **************************************");
		List<DPPrintBulkAcceptanceDTO> correctSTB=null;
		DPPrintBulkAcceptanceDao printBulkAcceptanceDao = DPPrintBulkAcceptanceDaoImpl.getInstance();
		try {
			correctSTB = printBulkAcceptanceDao.getWrongShippedSTB(dist_id,invoiceNo);
		} catch (Exception e) {
			logger.error("Exception occured::" + e.getMessage());
			logger.error(e.getMessage(), e);
		
		}
		return correctSTB;
	}

	public String getPONo(String invoice_no) {
		// TODO Auto-generated method stub
		logger.info("********************** getCorrectSTB() **************************************");
		String poNo=null;
		DPPrintBulkAcceptanceDao printBulkAcceptanceDao = DPPrintBulkAcceptanceDaoImpl.getInstance();
		try {
			poNo = printBulkAcceptanceDao.getPONo(invoice_no);
		} catch (Exception e) {
			logger.error("Exception occured::" + e.getMessage());
			logger.error(e.getMessage(), e);
		
		}
		return poNo;
	}

	public String getPOQty(String invoice_no) {
		// TODO Auto-generated method stub
		logger.info("********************** getCorrectSTB() **************************************");
		String poQty=null;
		DPPrintBulkAcceptanceDao printBulkAcceptanceDao = DPPrintBulkAcceptanceDaoImpl.getInstance();
		try {
			poQty = printBulkAcceptanceDao.getPOQty(invoice_no);
		} catch (Exception e) {
			logger.error("Exception occured::" + e.getMessage());
			logger.error(e.getMessage(), e);
		
		}
		return poQty;
	}
}
