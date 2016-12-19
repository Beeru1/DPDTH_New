package com.ibm.dp.dao;

import java.util.ArrayList;

import com.ibm.dp.dto.ChurnDCGenerationDTO;
import com.ibm.virtualization.recharge.exception.DAOException;

public interface ChurnDCGenerationDAO 
{
	public ArrayList<ArrayList<ChurnDCGenerationDTO>> viewChurnPendingForDCList(long accountID) throws DAOException;
	
	public ArrayList<ArrayList<ChurnDCGenerationDTO>> reportDCGenerationDao(ChurnDCGenerationDTO churnDTO)throws DAOException;
	public String checkERR(String rowSrNo) throws  DAOException ;

}
