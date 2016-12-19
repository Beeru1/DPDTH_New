package com.ibm.dp.service;

import java.sql.SQLException;
import java.util.ArrayList;



import com.ibm.dp.dto.ChurnDCGenerationDTO;
import com.ibm.dp.exception.DPServiceException;
import com.ibm.virtualization.recharge.exception.DAOException;

public interface ChurnDCGenerationService 
{
	public ArrayList<ArrayList<ChurnDCGenerationDTO>> viewChurnPendingForDCList(long accountID)
	throws DPServiceException, DAOException, SQLException;
	
	public ArrayList<ArrayList<ChurnDCGenerationDTO>> reportDCCreation(ChurnDCGenerationDTO churnDTO)throws DPServiceException;
	
	public String checkERR(String rowSrNo) throws DPServiceException;
}
