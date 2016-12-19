package com.ibm.dp.service;

import java.util.ArrayList;
import java.util.List;

import com.ibm.dp.dto.RecoSummaryDTO;
import com.ibm.dp.exception.DPServiceException;
import com.ibm.virtualization.recharge.exception.DAOException;
import com.ibm.virtualization.ussdactivationweb.services.dto.CircleDTO;

public interface RecoSummaryService {
	
	public List<CircleDTO> getCircleList() throws DPServiceException, DAOException;
	
	public ArrayList<RecoSummaryDTO> getReport(ArrayList<Integer> circleIdArrList, String status, int recoID) throws DPServiceException, DAOException;
		
}
