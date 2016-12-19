package com.ibm.dp.service.impl;

import java.io.InputStream;
import java.sql.Connection;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.ibm.dp.beans.RecoPeriodConfFormBean;
import com.ibm.dp.dto.PrintRecoDto;
import org.apache.log4j.Logger;

import com.ibm.dp.dao.CloseDistributorRecoDao;
import com.ibm.dp.dao.DPDistPinCodeMapDao;
import com.ibm.dp.dao.DistRecoDao;
import com.ibm.dp.dao.RecoDetailReportDao;
import com.ibm.dp.dao.impl.CloseDistributorRecoDaoImpl;
import com.ibm.dp.dao.impl.RecoDetailReportDaoImpl;
import com.ibm.dp.dto.DistRecoDto;
import com.ibm.dp.dto.DuplicateSTBDTO;
import com.ibm.dp.dto.RecoDetailReportDTO;
import com.ibm.dp.dto.RecoPeriodDTO;
import com.ibm.dp.exception.DAOException;
import com.ibm.dp.exception.DPServiceException;
import com.ibm.dp.service.CloseDistributorRecoService;
import com.ibm.dp.service.RecoDetailReportService;
import com.ibm.dpmisreports.common.SelectionCollection;
import com.ibm.virtualization.recharge.common.Constants;
import com.ibm.virtualization.recharge.common.ResourceReader;
import com.ibm.virtualization.recharge.dao.DAOFactory;
import com.ibm.virtualization.recharge.db.DBConnectionManager;



public class CloseDistributorRecoServiceImpl implements CloseDistributorRecoService
{
	private static  Logger logger = Logger.getLogger(CloseDistributorRecoServiceImpl.class.getName());

	private static CloseDistributorRecoService recoDetailReportService = new CloseDistributorRecoServiceImpl();
	public CloseDistributorRecoServiceImpl() {}
	public static CloseDistributorRecoService getInstance() {
		return recoDetailReportService;
	}
	private CloseDistributorRecoDao recoDetailReportDao = CloseDistributorRecoDaoImpl.getInstance();

		
	public List<RecoDetailReportDTO> getRecoReportDetails(int recoID,String circleId, String productIdArr, Integer groupId, long accountID) throws DPServiceException
	{
			List<RecoDetailReportDTO> reportRecoDataList = null;
			try {
				reportRecoDataList = recoDetailReportDao.getRecoReportDetails(recoID,circleId, productIdArr, groupId, accountID);
			} 
			catch (Exception e) {		
				logger.error("Exception occured in Generating Reco Detail in Service Impl:" + e.getMessage());
			}
		return reportRecoDataList;
	}
	
	public List<RecoPeriodDTO> getRecoHistory() throws DPServiceException
	{
			List<RecoPeriodDTO> recoHistoryList = null;
			try {
				recoHistoryList = recoDetailReportDao.getRecoHistory();
			} 
			catch (Exception e) {		
				logger.error("Exception occured in Generating Reco History in Close Distributor Reco Service Impl:" + e.getMessage());
				e.printStackTrace();
				throw new DPServiceException(e.getMessage());
			}
		return recoHistoryList;
	}
	
	public Map uploadExcel(InputStream inputstream, String recoId) throws DPServiceException {

		logger.error("***********Entered in ServiceImpl");
		Map returnMap = null;
		Connection connection = null;
		CloseDistributorRecoDao bulkupload = null; 
		CloseDistributorRecoDao dao = null; 
		try
		{
			connection = DBConnectionManager.getDBConnection();
			//dao = CloseDistributorRecoDaoImpl.getInstance();
			logger.error("***********before getting instance of DAOImpl");
			returnMap = recoDetailReportDao.uploadExcel(inputstream, recoId);
			connection.commit();
		}
		catch (Exception e) 
		{
			try
			{
				connection.rollback();
				connection.setAutoCommit(true);
			}
			catch(Exception ex)
			{
				logger.error("***********Exception occured while uploadExcel service impl************"+ex.getMessage());
			}
			e.printStackTrace();
			logger.error("***********Exception occured while fetching uploadExcel************"+e.getMessage());
			throw new DPServiceException(e.getMessage());
		}
		finally
		{			
			//Releasing the database connection
			try
			{
				connection.setAutoCommit(true);
			}
			catch(Exception ex)
			{
				ex.printStackTrace();
				logger.error("***********Exception occured while uploadExcel************"+ex.getMessage());
			}
			DBConnectionManager.releaseResources(connection);
		}
		return returnMap;
	
	}
	
public String uploadToSystem(List<DistRecoDto> list, List<DuplicateSTBDTO> uploadList,List<DuplicateSTBDTO> queryList ,String recoPeriodId, String loginUserId) throws DPServiceException {

		String strMessage ="";
		//Connection connection = null;
		CloseDistributorRecoDao bulkupload = null; 
		try
		{
			//connection = DBConnectionManager.getDBConnection();
			bulkupload = CloseDistributorRecoDaoImpl.getInstance();
			strMessage = bulkupload.uploadToSystem(list, uploadList,queryList ,recoPeriodId, loginUserId);
			//connection.commit();
		}
		catch (Exception e) 
		{
			try
			{
				//connection.rollback();
				//connection.setAutoCommit(true);
			}
			catch(Exception ex)
			{
				logger.error("***********Exception occured while uploadExcel service impl************"+ex.getMessage());
			}
			e.printStackTrace();
			logger.error("***********Exception occured while fetching uploadExcel************"+e.getMessage());
			throw new DPServiceException(e.getMessage());
		}
		finally
		{			
			//Releasing the database connection
			try
			{
				//connection.setAutoCommit(true);
			}
			catch(Exception ex)
			{
				ex.printStackTrace();
				logger.error("***********Exception occured while uploadExcel************"+ex.getMessage());
			}
			//DBConnectionManager.releaseResources(connection);
		}
		return strMessage;
	
	}
	
	public List getRecoPrintList(String recoID,String circleid,String selectedProductId, int intSelectedTsmId ,String hiddenDistId ) throws DPServiceException
	{
		logger.info("********************** getRecoPrintList() **************************************");
		List printListDTO = null;
		try
		{
			printListDTO =  recoDetailReportDao.getRecoPrintList(recoID,circleid,selectedProductId,intSelectedTsmId, hiddenDistId);
		}
		catch (Exception e) 
		{
			e.printStackTrace();
			throw new DPServiceException(e.getMessage());
		}
		finally
		{			
			//Releasing the database connection
			
		}
		return printListDTO;
		
		
	}
	public List<SelectionCollection> getCircleList(long accountID) throws DPServiceException 
	{
		logger.info("********************** getRecoPrintList() **************************************");
		List<SelectionCollection> listReturn = null;
		try
		{
			listReturn =  recoDetailReportDao.getCircleListDAO(accountID);
		}
		catch (Exception e) 
		{
			e.printStackTrace();
			throw new DPServiceException(e.getMessage());
		}
		return listReturn;
		
	}

//added by aman
	
	
	public List<RecoPeriodConfFormBean> getTsmList(int circleId) throws DPServiceException
	{
		logger.info("********************** getRecoPrintList() **************************************");
		List<RecoPeriodConfFormBean> listReturn = null;
		try
		{
			listReturn =  recoDetailReportDao.getTsmList(circleId);
		}
		catch (Exception e) 
		{
			e.printStackTrace();
			throw new DPServiceException(e.getMessage());
		}
		return listReturn;
		
	}
	//end of changes by aman
	
	public List<RecoPeriodConfFormBean> getDistList(int tsmId) throws DPServiceException 
	{
		logger.info("********************** getRecoPrintList() **************************************");
		List<RecoPeriodConfFormBean> listReturn = null;
		try
		{
			listReturn =  recoDetailReportDao.getDistList(tsmId);
		}
		catch (Exception e) 
		{
			e.printStackTrace();
			throw new DPServiceException(e.getMessage());
		}
		return listReturn;
		
	}
	//added by Rohit
	public List<DistRecoDto> getExportToExcel(String circleList,String tsmList, String distList, String prodList,String recoPeriod, String distType) throws DPServiceException
	{
		logger.info("********************** getDetailsList() **************************************");
		
		List<DistRecoDto> distRecoDtoList = null;
		try
		{
			distRecoDtoList =  recoDetailReportDao.getExportToExcel(circleList, tsmList, distList, prodList,recoPeriod,distType);
		}
		catch (Exception e) 
		{
			e.printStackTrace();
			throw new DPServiceException(e.getMessage());
		}
		return distRecoDtoList;
		
		
	}
	
	public List<RecoPeriodDTO> getRecoHistoryNotClosed() throws DPServiceException
	{
			List<RecoPeriodDTO> recoHistoryList = null;
			try {
				recoHistoryList = recoDetailReportDao.getRecoHistoryNotClosed();
			} 
			catch (Exception e) {		
				logger.error("Exception occured in Generating Reco History in Close Distributor Reco Service Impl getRecoHistoryNotClosed() :" + e.getMessage());
				e.printStackTrace();
				throw new DPServiceException(e.getMessage());
			}
		return recoHistoryList;
	}
	
}
