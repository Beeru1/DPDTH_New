package com.ibm.dp.service.impl;

import java.io.InputStream;
import java.util.List;

import org.apache.log4j.Logger;
import org.apache.struts.upload.FormFile;

import com.ibm.dp.dao.RecoPeriodConfDao;
import com.ibm.dp.dao.impl.RecoPeriodConfDaoImpl;
import com.ibm.dp.dto.RecoPeriodDTO;
import com.ibm.dp.exception.DPServiceException;
import com.ibm.dp.service.RecoPeriodConfService;



public class RecoPeriodConfServiceImpl implements RecoPeriodConfService
{
	private static  Logger logger = Logger.getLogger(RecoPeriodConfServiceImpl.class.getName());

	private static RecoPeriodConfService recoPeriodConfService = new RecoPeriodConfServiceImpl();
	private RecoPeriodConfServiceImpl() {}
	public static RecoPeriodConfService getInstance() {
		return recoPeriodConfService;
	}
	private RecoPeriodConfDao recoPeriodConfDao = RecoPeriodConfDaoImpl.getInstance();

		
	public List<RecoPeriodDTO> getRecoHistory() throws DPServiceException
	{
			List<RecoPeriodDTO> recoHistoryList = null;
			try {
				recoHistoryList = recoPeriodConfDao.getRecoHistory();
			} 
			catch (Exception e) {		
				logger.error("Exception occured in Generating Reco History in Service Impl:" + e.getMessage());
				e.printStackTrace();
				throw new DPServiceException(e.getMessage());
			}
		return recoHistoryList;
	}
	
	
	
	public void updateRecoPeriodConf(String action, String recoConfId, String loginUser) throws DPServiceException
	{
			
			try {
				recoPeriodConfDao.updateRecoPeriodConf(action, recoConfId,  loginUser);
			} 
			catch (Exception e) {		
				logger.error("Exception occured in updating Reco configuration in Service Impl:" + e.getMessage());
				e.printStackTrace();
				throw new DPServiceException(e.getMessage());
			}
		
	}
	public List updatePartnerData(FormFile myxls,String recoId) throws DPServiceException
	{
			List list=null;
			try {
				logger.info("2222222222 in updatePartnerData ==========");
				list = recoPeriodConfDao.updatePartnerData(myxls,recoId);
			} 
			catch (Exception e) {		
				logger.error("Exception occured in updating Reco configuration in Service Impl:" + e.getMessage());
				e.printStackTrace();
				throw new DPServiceException(e.getMessage());
			}
			return list;
		
	}
	public String updatePartnerDataDB(List file,String recoId,String gracePeriod) throws DPServiceException
	{
			String str=null;
			try {
				str = recoPeriodConfDao.updatePartnerDataDB(file,recoId,gracePeriod);
			} 
			catch (Exception e) {		
				logger.error("Exception occured in updating Reco configuration in Service Impl:" + e.getMessage());
				e.printStackTrace();
				throw new DPServiceException(e.getMessage());
			}
			return str;
		
	}
	
	public String createSMSAlerts(List distlist,String recoId,String gracePeriod) throws DPServiceException
	{
			String str=null;
			try {
				str = recoPeriodConfDao.createSMSAlerts(distlist,recoId,gracePeriod);
			} 
			catch (Exception e) {		
				logger.error("Exception occured while creating sms for Reco period update :" + e.getMessage());
				e.printStackTrace();
				throw new DPServiceException(e.getMessage());
			}
			return str;
		
	}
	
	public void addRecoPeriod(String fromDate, String toDate, String loginUser, String gracePeriod) throws DPServiceException
	{
			
			try {
				recoPeriodConfDao.addRecoPeriod(fromDate,toDate,loginUser, gracePeriod);
			} 
			catch (Exception e) {		
				logger.error("Exception occured in Generating Reco History in Service Impl:" + e.getMessage());
				e.printStackTrace();
				throw new DPServiceException(e.getMessage());
			}
		
	}
	public String getGracePeriod() throws DPServiceException
	{
			String gracePeriod = "";
			try {
				gracePeriod = recoPeriodConfDao.getGracePeriod();
			} 
			catch (Exception e) {		
				logger.error("Exception occured in Generating Reco History in Service Impl:" + e.getMessage());
				e.printStackTrace();
				throw new DPServiceException(e.getMessage());
			}
		return gracePeriod;
	}
	
	public int getOldestRecoId(String recoPeriodIDs) throws DPServiceException
	{
		int oldestRecoId =-1;
			try {
				oldestRecoId = recoPeriodConfDao.getOldestRecoId(recoPeriodIDs);
			} 
			catch (Exception e) {		
				logger.error("Exception occured in getting oldest Reco period Id:" + e.getMessage());
				e.printStackTrace();
				throw new DPServiceException(e.getMessage());
			}
		return oldestRecoId;
	}
	
	public void updateGracePeriod(String gracePeriod, String loginUser) throws DPServiceException
	{
		
		try {
			recoPeriodConfDao.updateGracePeriod(gracePeriod, loginUser);
		} 
		catch (Exception e) {		
			logger.error("Exception occured in Generating Reco History in Service Impl:" + e.getMessage());
			e.printStackTrace();
			throw new DPServiceException(e.getMessage());
		}
	
	}
//Added by sugandha to view Reco period detail of individual recoId
	public List<RecoPeriodDTO> viewRecoGracePeriod(String recoPeriodId) throws DPServiceException
	{
			List<RecoPeriodDTO> recoList = null;
			try {
				recoList = recoPeriodConfDao.viewRecoGracePeriod(recoPeriodId);
			} 
			catch (Exception e) {		
				logger.error("Exception occured in viewRecoGracePeriod in Service Impl:" + e.getMessage());
				e.printStackTrace();
				throw new DPServiceException(e.getMessage());
			}
		return recoList;
	}
	//Added by Sugandha to update  graceperiod of individual reco Id
	public void updateOtherGracePeriod(String newGracePeriod, String loginUser, String recoPeriodId) throws DPServiceException {

		try {
			recoPeriodConfDao.updateOtherGracePeriod(newGracePeriod, loginUser,  recoPeriodId);
		} 
		catch (Exception e) {		
			logger.error("Exception occured in updateOtherGracePeriod in Service Impl:" + e.getMessage());
			e.printStackTrace();
			throw new DPServiceException(e.getMessage());
		}
		
	}
	public void deleteReco(String loginUser, String recoPeriodId) throws DPServiceException 
	{
		try {
			recoPeriodConfDao.deleteReco( loginUser,  recoPeriodId);
		} 
		catch (Exception e) {		
			logger.error("Exception occured in updateOtherGracePeriod in Service Impl:" + e.getMessage());
			e.printStackTrace();
			throw new DPServiceException(e.getMessage());
		}
		
	}
	//Added by shiva for frontend validation for reco period configuration
	public String recoPeriodOpening() throws DPServiceException {
		try {
			recoPeriodConfDao.recoPeriodOpening();
		} 
		catch (Exception e) {		
			logger.error("Exception occured in recoPeriodOpening in Service Impl:" + e.getMessage());
			e.printStackTrace();
			throw new DPServiceException(e.getMessage());
		}
		return recoPeriodConfDao.recoPeriodOpening();
	}
	
	public String recoPeriodClosing() throws DPServiceException {
		try {
			recoPeriodConfDao.recoPeriodClosing();
		} 
		catch (Exception e) {		
			logger.error("Exception occured in recoPeriodClosing in Service Impl:" + e.getMessage());
			e.printStackTrace();
			throw new DPServiceException(e.getMessage());
		}
		return recoPeriodConfDao.recoPeriodClosing();
	}
	
	public int validateReco(String toDate) throws DPServiceException {
		int intValidate=0;
		try {
			 intValidate= recoPeriodConfDao.validateReco(toDate);
		} 
		catch (Exception e) {		
			logger.error("Exception occured in recoPeriodClosing in Service Impl:" + e.getMessage());
			e.printStackTrace();
			throw new DPServiceException(e.getMessage());
		}
		return intValidate;
	
	}
	
	public int validateAction(String toDate) throws DPServiceException {
		int intValidate=0;
		try {
			 intValidate= recoPeriodConfDao.validateReco(toDate);
		} 
		catch (Exception e) {		
			logger.error("Exception occured in recoPeriodClosing in Service Impl:" + e.getMessage());
			e.printStackTrace();
			throw new DPServiceException(e.getMessage());
		}
		return intValidate;
	}
	
	public int validateAction1(String recoId) throws DPServiceException {
		int intValidate=0;
		try {
			 intValidate= recoPeriodConfDao.validateAction1(recoId);
		} 
		catch (Exception e) {		
			logger.error("Exception occured in recoPeriodClosing in Service Impl:" + e.getMessage());
			e.printStackTrace();
			throw new DPServiceException(e.getMessage());
		}
		return intValidate;
	}
	
	//-----------END shiva
}
