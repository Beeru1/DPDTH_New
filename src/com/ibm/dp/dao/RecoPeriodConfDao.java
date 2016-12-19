package com.ibm.dp.dao;

import java.io.InputStream;
import java.util.List;

import org.apache.struts.upload.FormFile;

import com.ibm.core.exception.DAOException;
import com.ibm.dp.dto.RecoPeriodDTO;
import com.ibm.dp.exception.DPServiceException;

public interface RecoPeriodConfDao {
	public List<RecoPeriodDTO> getRecoHistory() throws DPServiceException;
	public void addRecoPeriod(String fromDate, String toDate, String loginUser, String gracePeriod)  throws DPServiceException;
	public void updateRecoPeriodConf(String action, String recoConfIds, String loginUser)  throws DPServiceException;
	public String getGracePeriod()  throws DPServiceException;
	public int getOldestRecoId(String recoPeriodIDs) throws DAOException ;
	public void updateGracePeriod(String gracePeriod, String loginUser)  throws DPServiceException;

	public String createSMSAlerts(List distList,String recoId,String gracePeriod) throws DAOException ;
	public List<RecoPeriodDTO> viewRecoGracePeriod(String recoPeriodId) throws DPServiceException;
	public void updateOtherGracePeriod(String newGracePeriod, String loginUser, String recoPeriodId)throws DPServiceException;
	public void deleteReco(String loginUser, String recoPeriodId)throws DPServiceException;
	public List updatePartnerData(FormFile myxls,String recoId) throws DPServiceException;
	public String updatePartnerDataDB(List file,String recoId,String gracePeriod) throws DPServiceException;
	//Added by shiva for frontend validation for reco period configuration
	public String recoPeriodOpening()throws DPServiceException;
	public String recoPeriodClosing()throws DPServiceException;
	//--------------END  shiva
	public int validateReco(String toDate)throws DAOException;
	public int validateAction(String toDate)throws DAOException;
	public int validateAction1(String recoId)throws DAOException;
}
