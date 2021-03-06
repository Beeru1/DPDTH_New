package com.ibm.dp.service;

import java.util.List;

import com.ibm.dp.beans.RecoPeriodConfFormBean;
import com.ibm.dp.dto.PrintRecoDto;
import com.ibm.dp.dto.RecoDetailReportDTO;
import com.ibm.dp.dto.RecoPeriodDTO;
import com.ibm.dp.exception.DAOException;
import com.ibm.dp.exception.DPServiceException;
import com.ibm.dpmisreports.common.SelectionCollection;


public interface RecoDetailReportService {

	public List<RecoDetailReportDTO> getRecoReportDetails(int recoID, String circleId, String productIds, Integer groupId, long accountID) throws Exception;
	public List<RecoPeriodDTO> getRecoHistory() throws DPServiceException;
	//public List getRecoPrintList(String recoID,String circleid,String selectedProductId) throws DPServiceException;
	public List getRecoPrintList(String recoID,String circleid,String selectedProductId, int intSelectedTsmId, String hiddenDistId) throws DPServiceException;
	public List<SelectionCollection> getCircleList(long accountID) throws DPServiceException;
	public List<RecoPeriodConfFormBean> getTsmList(int circleId) throws DPServiceException; //added by aman
	public List<RecoPeriodConfFormBean> getDistList(int tsmId) throws DPServiceException ; //added by aman
	
}

