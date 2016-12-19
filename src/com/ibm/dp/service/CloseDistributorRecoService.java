package com.ibm.dp.service;

import java.io.InputStream;
import java.util.List;
import java.util.Map;

import com.ibm.dp.beans.RecoPeriodConfFormBean;
import com.ibm.dp.dto.DistRecoDto;
import com.ibm.dp.dto.DuplicateSTBDTO;
import com.ibm.dp.dto.PrintRecoDto;
import com.ibm.dp.dto.RecoDetailReportDTO;
import com.ibm.dp.dto.RecoPeriodDTO;
import com.ibm.dp.exception.DAOException;
import com.ibm.dp.exception.DPServiceException;
import com.ibm.dpmisreports.common.SelectionCollection;


public interface CloseDistributorRecoService {

	public List<RecoDetailReportDTO> getRecoReportDetails(int recoID, String circleId, String productIds, Integer groupId, long accountID) throws Exception;
	public List<RecoPeriodDTO> getRecoHistory() throws DPServiceException;
	//public List getRecoPrintList(String recoID,String circleid,String selectedProductId) throws DPServiceException;
	public List getRecoPrintList(String recoID,String circleid,String selectedProductId, int intSelectedTsmId, String hiddenDistId) throws DPServiceException;
	public List<SelectionCollection> getCircleList(long accountID) throws DPServiceException;
	public List<RecoPeriodConfFormBean> getTsmList(int circleId) throws DPServiceException; //added by aman
	public List<RecoPeriodConfFormBean> getDistList(int tsmId) throws DPServiceException ; //added by aman
	public List<DistRecoDto> getExportToExcel(String circleList,
			String tsmList, String distList, String prodList,
			String recoPeriod, String distType) throws DPServiceException ; //added by Rohit
	public Map uploadExcel(InputStream inputstream, String recoId)throws DPServiceException;
	String uploadToSystem(List<DistRecoDto> list ,List<DuplicateSTBDTO> uploadList, List<DuplicateSTBDTO> queryList,String recoPeriodToDate, String loginUserId)throws DPServiceException;
	
	public List<RecoPeriodDTO> getRecoHistoryNotClosed() throws DPServiceException;
	
}

