package com.ibm.dp.service;

import java.io.InputStream;
import java.util.List;

import org.apache.struts.upload.FormFile;

import com.ibm.dp.dto.RecoPeriodDTO;
import com.ibm.dp.exception.DPServiceException;


public interface RecoPeriodConfService {

	public List<RecoPeriodDTO> getRecoHistory() throws DPServiceException;
	public void addRecoPeriod(String fromDate, String toDate, String loginUser, String gracePeriod) throws DPServiceException;
	public void updateRecoPeriodConf(String action, String recoConfId, String loginUser) throws DPServiceException;
	public String getGracePeriod() throws DPServiceException;
	public int getOldestRecoId(String recoPeriodIDs) throws DPServiceException;
	
	public List updatePartnerData(FormFile myxls,String recoId) throws DPServiceException;
	public String updatePartnerDataDB(List file,String recoId,String gracePeriod) throws DPServiceException;
	
	public void updateGracePeriod(String gracePeriod, String loginUser) throws DPServiceException;
	public String createSMSAlerts(List distlist,String recoId,String gracePeriod) throws DPServiceException;
	
	public List<RecoPeriodDTO> viewRecoGracePeriod(String recoPeriodId)throws DPServiceException;
	public void updateOtherGracePeriod(String newGracePeriod, String loginUser, String recoPeriodId)throws DPServiceException;
	public void deleteReco(String loginUser, String recoPeriodId)throws DPServiceException;
	//Added by shiva for frontend validation for reco period configuration
	public String recoPeriodOpening() throws DPServiceException;
	public String recoPeriodClosing() throws DPServiceException;
	//----------END Shiva
	public int validateReco(String toDate)throws DPServiceException;//validating reco
	public int validateAction(String toDate)throws DPServiceException;//validating reco
	public int validateAction1(String recoID)throws DPServiceException;
	
}
