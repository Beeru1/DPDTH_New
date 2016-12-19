package com.ibm.dp.dao;

import java.util.List;

import com.ibm.dp.dto.DPPrintBulkAcceptanceDTO;

public interface DPPrintBulkAcceptanceDao {

	public int getInvoiceQty(String invoice_no);

	public List<DPPrintBulkAcceptanceDTO> getCorrectSTB(String dist_id, String invoice_no);
	public List<DPPrintBulkAcceptanceDTO> getWrongShippedSTB(String dist_id, String invoiceNo);
	public List<DPPrintBulkAcceptanceDTO> getMissingSTB(String dist_id, String invoiceNo);

	public String getPONo(String invoice_no);

	public String getPOQty(String invoice_no);
	
}
