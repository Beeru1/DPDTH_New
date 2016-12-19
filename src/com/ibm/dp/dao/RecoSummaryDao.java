package com.ibm.dp.dao;

import java.util.ArrayList;
import java.util.List;

import com.ibm.dp.dto.DpDcDamageStatusDto;
import com.ibm.dp.dto.RecoSummaryDTO;
import com.ibm.dp.exception.DPServiceException;
import com.ibm.virtualization.recharge.exception.DAOException;
import com.ibm.virtualization.ussdactivationweb.services.dto.CircleDTO;

public interface RecoSummaryDao {
	
	public List<CircleDTO> getCircleList() throws DAOException;
	
	public ArrayList<RecoSummaryDTO> getReport( ArrayList<Integer> circleIdArrList, String status, int recoID) throws DAOException;
	
}
