package com.ibm.dp.dao;

import java.util.List;

import org.apache.struts.upload.FormFile;

import com.ibm.dp.dto.DPPoAcceptanceBulkDTO;
import com.ibm.virtualization.recharge.exception.DAOException;


public interface DPPoAcceptanceBulkDAO {
	
	public List uploadExcel(FormFile myXls,String deliveryChallanNo) throws DAOException;
	
	public DPPoAcceptanceBulkDTO getWrongShortSrNo(String invoiceNo, String dcNo, int intCircleID ,List list) throws DAOException;
	
	public DPPoAcceptanceBulkDTO getShortSrNo(String dcNo) throws DAOException;
	
	

}
