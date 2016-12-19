package com.ibm.dp.service;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.apache.struts.upload.FormFile;

import com.ibm.dp.dto.DPReverseChurnCollectionDTO;
import com.ibm.dp.exception.DPServiceException;
import com.ibm.hbo.exception.HBOException;
import com.ibm.virtualization.recharge.exception.DAOException;
import com.ibm.virtualization.recharge.exception.VirtualizationServiceException;

public interface DPReverseChurnCollectionService 
{
	public ArrayList viewPendingChurnSTBList(long accountID)throws DPServiceException, DAOException, SQLException;
	
	public List uploadExcel(FormFile myXls) throws DPServiceException;
	
	public ArrayList insertFreshChurnSTB(DPReverseChurnCollectionDTO churnDTO, Long lnUserID, int circleId)throws DPServiceException;

	ArrayList<DPReverseChurnCollectionDTO> reportChurn(String[] arrCheckedChurn, long loginUserId,String strRemarks) throws DPServiceException;
	
	ArrayList<DPReverseChurnCollectionDTO> reportChurnAddToStock(String[] arrCheckedChurn,long loginUserId,String strRemarks) throws DPServiceException;
	
	public String checkCPEInventory(String stb_nos) throws DPServiceException;
	public int checkTransactionTypeReverse(Long distId)throws DPServiceException;//Neetika
}
