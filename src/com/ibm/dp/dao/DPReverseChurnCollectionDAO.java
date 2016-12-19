package com.ibm.dp.dao;

import java.util.ArrayList;
import java.util.List;

import org.apache.struts.upload.FormFile;
import com.ibm.dp.dto.DPReverseChurnCollectionDTO;
import com.ibm.virtualization.recharge.exception.DAOException;


public interface DPReverseChurnCollectionDAO {

//	Added by Shah
	public ArrayList viewChurnDetails(long accountID) throws DAOException;
	
	public List uploadExcel(FormFile myXls) throws DAOException;
	
	public ArrayList insertFreshChurnSTB(DPReverseChurnCollectionDTO churnDTO, Long lnUserID,int circleId)throws DAOException;

	
	ArrayList<DPReverseChurnCollectionDTO> reportChurnDao(String[] arrCheckedChurn, long loginUserId,String strRemarks)
	throws DAOException;
	
	ArrayList<DPReverseChurnCollectionDTO> reportChurnAddToStockDao(String[] arrCheckedChurn, long loginUserId, String strRemarks)
	throws DAOException;
	
	public String checkCPEInventory(String stb_nos) throws DAOException;
}
