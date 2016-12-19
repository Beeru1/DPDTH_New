package com.ibm.dp.service;

import java.util.List;

import com.ibm.dp.dto.DPPrintBulkAcceptanceDTO;

public interface DPPrintBulkAcceptanceService {

public int getInvoiceQty(String invoice_no);
public List<DPPrintBulkAcceptanceDTO> getCorrectSTB(String distId,String invoice_no);
public List<DPPrintBulkAcceptanceDTO> getWrongShippedSTB(String distId, String invoiceNo);
public List<DPPrintBulkAcceptanceDTO> getMissingSTB(String dist_id, String invoiceNo);
public String getPONo(String invoice_no);
public String getPOQty(String invoice_no);
}
