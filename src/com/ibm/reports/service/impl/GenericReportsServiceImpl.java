package com.ibm.reports.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

import com.ibm.dp.common.DBQueries;
import com.ibm.dp.dto.CollectionReportDTO;
import com.ibm.dp.dto.DpProductCategoryDto;
import com.ibm.dp.dto.GenericReportPararmeterDTO;
import com.ibm.dp.exception.DPServiceException;
import com.ibm.reports.dao.GenericReportsDao;
import com.ibm.reports.dao.impl.GenericReportsDaoFactory;
import com.ibm.reports.dao.impl.GenericReportsDaoImpl;
import com.ibm.reports.dto.CriteriaDTO;
import com.ibm.reports.dto.GenericOptionDTO;
import com.ibm.reports.dto.GenericReportsOutputDto;
import com.ibm.reports.dto.ReportDetailDTO;
import com.ibm.reports.service.GenericReportsService;
import com.ibm.virtualization.recharge.exception.DAOException;

public class GenericReportsServiceImpl implements GenericReportsService
{

	private Logger logger = Logger.getLogger(GenericReportsServiceImpl.class.getName());

	public static final String SQL_COLLECTION_MST = DBQueries.SQL_COLLECTION_MST;

	private static GenericReportsService genericReportsService = new GenericReportsServiceImpl();

	private GenericReportsServiceImpl()
	{
	}

	public static GenericReportsService getInstance()
	{
		return genericReportsService;
	}

	public List<CriteriaDTO> getReportCriterias(int reportId, int groupId) throws DAOException,
			DPServiceException
	{
		logger
				.info("********************** getReportCriterias() **************************************");
		List<CriteriaDTO> criteriaListDTO = new ArrayList<CriteriaDTO>();
		GenericReportsDao reportsDao = GenericReportsDaoImpl.getInstance();
		try
		{
			logger
					.info("**********GenericReportsServiceImpl:getReportCriterias********************");
			criteriaListDTO = reportsDao.getReportCriterias(reportId, groupId);
		}
		catch (DAOException daoException)
		{
			throw daoException;
		}
		catch (Exception e)
		{
			logger.error("Exception occured::" + e.getMessage());
			logger.error(e.getMessage(), e);
			throw new DPServiceException(e.getMessage());
		}
		return criteriaListDTO;
	}

	public ReportDetailDTO getReportDetails(int reportId) throws DPServiceException, DAOException
	{
		ReportDetailDTO reportDetailDTO = null;
		try
		{
			GenericReportsDao reportsDao = GenericReportsDaoImpl.getInstance();

			reportDetailDTO = reportsDao.getReportDetails(reportId);
		}
		catch (DAOException daoException)
		{
			throw daoException;
		}
		catch (Exception e)
		{
			logger.error("Exception occured::" + e.getMessage());
			logger.error(e.getMessage(), e);
			throw new DPServiceException(e.getMessage());
		}
		return reportDetailDTO;
	}

	public List<DpProductCategoryDto> getTransactionType(int reportId) throws DPServiceException,
			DAOException
	{
		List<DpProductCategoryDto> productList = null;
		try
		{
			GenericReportsDao reportsDao = GenericReportsDaoImpl.getInstance();
			productList = reportsDao.getTransactionType(reportId);
		}
		catch (DAOException daoException)
		{
			throw daoException;
		}
		catch (Exception e)
		{
			logger.error("Exception occured::" + e.getMessage());
			logger.error(e.getMessage(), e);
			throw new DPServiceException(e.getMessage());
		}
		return productList;
	}

	public List<DpProductCategoryDto> getProductList(int reportId) throws DPServiceException,
			DAOException
	{
		List<DpProductCategoryDto> productList = null;
		try
		{
			GenericReportsDao reportsDao = GenericReportsDaoImpl.getInstance();
			productList = reportsDao.getProductList(reportId);
		}
		catch (DAOException daoException)
		{
			throw daoException;
		}
		catch (Exception e)
		{
			logger.error("Exception occured::" + e.getMessage());
			logger.error(e.getMessage(), e);
			throw new DPServiceException(e.getMessage());
		}
		return productList;
	}

	public List<CollectionReportDTO> getCollectionTypeMaster(int reportId) throws DAOException,
			DPServiceException
	{
		List<CollectionReportDTO> collectionListDTO = new ArrayList<CollectionReportDTO>();
		try
		{
			GenericReportsDao reportsDao = GenericReportsDaoImpl.getInstance();
			collectionListDTO = reportsDao.getCollectionTypeMaster(reportId);
		}
		catch (DAOException daoException)
		{
			throw daoException;
		}
		catch (Exception e)
		{
			logger.error("Exception occured::" + e.getMessage());
			logger.error(e.getMessage(), e);
			throw new DPServiceException(e.getMessage());
		}
		return collectionListDTO;
	}

	public List<GenericOptionDTO> getDateOptions(int reportId) throws DAOException,
			DPServiceException
	{
		List<GenericOptionDTO> poDateOptions = null;
		try
		{
			GenericReportsDao reportsDao = GenericReportsDaoImpl.getInstance();
			poDateOptions = reportsDao.getDateOptions(reportId);
		}
		catch (DAOException daoException)
		{
			throw daoException;
		}
		catch (Exception e)
		{
			logger.error("Exception occured::" + e.getMessage());
			logger.error(e.getMessage(), e);
			throw new DPServiceException(e.getMessage());
		}
		return poDateOptions;
	}

	// added by aman
	public List<GenericOptionDTO> getActivity(int reportId) throws DAOException, DPServiceException
	{
		List<GenericOptionDTO> activityList = null;
		try
		{
			GenericReportsDao reportsDao = GenericReportsDaoImpl.getInstance();
			activityList = reportsDao.getActivityOptions(reportId);
		}
		catch (DAOException daoException)
		{
			throw daoException;
		}
		catch (Exception e)
		{
			logger.error("Exception occured::" + e.getMessage());
			logger.error(e.getMessage(), e);
			throw new DPServiceException(e.getMessage());
		}

		return activityList;
	}

	// end of changes by aman

	public List<GenericOptionDTO> getPOStatusList() throws DAOException, DPServiceException
	{

		List<GenericOptionDTO> poStatusList = null;
		try
		{
			GenericReportsDao reportsDao = GenericReportsDaoImpl.getInstance();
			poStatusList = reportsDao.getPOStatusList();
		}
		catch (DAOException daoException)
		{
			throw daoException;
		}
		catch (Exception e)
		{
			logger.error("Exception occured::" + e.getMessage());
			logger.error(e.getMessage(), e);
			throw new DPServiceException(e.getMessage());
		}
		return poStatusList;
	}

	public List<GenericOptionDTO> getSearchOptions(int reportId) throws DAOException,
			DPServiceException
	{
		List<GenericOptionDTO> poSearchOptions = null;
		try
		{
			GenericReportsDao reportsDao = GenericReportsDaoImpl.getInstance();
			poSearchOptions = reportsDao.getSearchOptions(reportId);
		}
		catch (DAOException daoException)
		{
			throw daoException;
		}
		catch (Exception e)
		{
			logger.error("Exception occured::" + e.getMessage());
			logger.error(e.getMessage(), e);
			throw new DPServiceException(e.getMessage());
		}
		return poSearchOptions;
	}

	public List<GenericOptionDTO> getPendingOptions(int reportId) throws DAOException,
			DPServiceException
	{
		List<GenericOptionDTO> pendingOptions = null;
		try
		{
			GenericReportsDao reportsDao = GenericReportsDaoImpl.getInstance();
			pendingOptions = reportsDao.getPendingOptions(reportId);
		}
		catch (DAOException daoException)
		{
			throw daoException;
		}
		catch (Exception e)
		{
			logger.error("Exception occured::" + e.getMessage());
			logger.error(e.getMessage(), e);
			throw new DPServiceException(e.getMessage());
		}
		return pendingOptions;
	}

	public List<GenericOptionDTO> getTransferTypeOptions(int reportId) throws DAOException,
			DPServiceException
	{
		List<GenericOptionDTO> transferTypeOptions = null;
		try
		{
			GenericReportsDao reportsDao = GenericReportsDaoImpl.getInstance();
			transferTypeOptions = reportsDao.getTransferTypeOptions(reportId);
		}
		catch (DAOException daoException)
		{
			throw daoException;
		}
		catch (Exception e)
		{
			logger.error("Exception occured::" + e.getMessage());
			logger.error(e.getMessage(), e);
			throw new DPServiceException(e.getMessage());
		}
		return transferTypeOptions;
	}

	public List<GenericOptionDTO> getStatusOptions(int reportId) throws DAOException,
			DPServiceException
	{
		List<GenericOptionDTO> statusOptions = null;
		try
		{
			GenericReportsDao reportsDao = GenericReportsDaoImpl.getInstance();
			statusOptions = reportsDao.getStatusOptions(reportId);
		}
		catch (DAOException daoException)
		{
			throw daoException;
		}
		catch (Exception e)
		{
			logger.error("Exception occured::" + e.getMessage());
			logger.error(e.getMessage(), e);
			throw new DPServiceException(e.getMessage());
		}
		return statusOptions;
	}

	public List<GenericOptionDTO> getRecoStatus(int reportId) throws DAOException,
			DPServiceException
	{
		List<GenericOptionDTO> statusOptions = null;
		try
		{
			GenericReportsDao reportsDao = GenericReportsDaoImpl.getInstance();
			statusOptions = reportsDao.getRecoStatus(reportId);
		}
		catch (DAOException daoException)
		{
			throw daoException;
		}
		catch (Exception e)
		{
			logger.error("Exception occured::" + e.getMessage());
			logger.error(e.getMessage(), e);
			throw new DPServiceException(e.getMessage());
		}
		return statusOptions;
	}

	public List<GenericOptionDTO> getSTBStatusList(int reportId) throws DAOException,
			DPServiceException
	{

		List<GenericOptionDTO> statusOptions = null;
		try
		{
			GenericReportsDao reportsDao = GenericReportsDaoImpl.getInstance();
			statusOptions = reportsDao.getSTBStatusList(reportId);
		}
		catch (DAOException daoException)
		{
			throw daoException;
		}
		catch (Exception e)
		{
			logger.error("Exception occured::" + e.getMessage());
			logger.error(e.getMessage(), e);
			throw new DPServiceException(e.getMessage());
		}
		return statusOptions;
	}

	public List<GenericOptionDTO> getStockType() throws DAOException, DPServiceException
	{

		List<GenericOptionDTO> statusOptions = null;
		try
		{
			GenericReportsDao reportsDao = GenericReportsDaoImpl.getInstance();
			statusOptions = reportsDao.getStockType();
		}
		catch (DAOException daoException)
		{
			throw daoException;
		}
		catch (Exception e)
		{
			logger.error("Exception occured::" + e.getMessage());
			logger.error(e.getMessage(), e);
			throw new DPServiceException(e.getMessage());
		}
		return statusOptions;
	}

	public List<GenericOptionDTO> getAccountTypeList(int reportId, int groupId)
			throws DAOException, DPServiceException
	{

		List<GenericOptionDTO> accountTypeOptions = null;
		try
		{
			GenericReportsDao reportsDao = GenericReportsDaoImpl.getInstance();
			accountTypeOptions = reportsDao.getAccountTypeList(reportId, groupId);
		}
		catch (DAOException daoException)
		{
			throw daoException;
		}
		catch (Exception e)
		{
			logger.error("Exception occured::" + e.getMessage());
			logger.error(e.getMessage(), e);
			throw new DPServiceException(e.getMessage());
		}
		return accountTypeOptions;
	}

	public List<GenericOptionDTO> getDcStatusList(int reportId) throws DAOException,
			DPServiceException
	{

		List<GenericOptionDTO> accountTypeOptions = null;
		try
		{
			GenericReportsDao reportsDao = GenericReportsDaoImpl.getInstance();
			accountTypeOptions = reportsDao.getDcStatusList(reportId);
		}
		catch (DAOException daoException)
		{
			throw daoException;
		}
		catch (Exception e)
		{
			logger.error("Exception occured::" + e.getMessage());
			logger.error(e.getMessage(), e);
			throw new DPServiceException(e.getMessage());
		}
		return accountTypeOptions;
	}

	public GenericReportsOutputDto exportToExcel(GenericReportPararmeterDTO genericDTO)
			throws DPServiceException, DAOException
	{
		logger
				.info("********************** exportToExcel() **************************************");
		GenericReportsOutputDto genericListDTO = new GenericReportsOutputDto();
		try
		{
			GenericReportsDao reportsDao = GenericReportsDaoFactory.getGenericReportDao(genericDTO
					.getGroupId());

			genericListDTO = reportsDao.exportToExcel(genericDTO);

			logger
					.info("asa::::servceimpl:::reportid::::::service impl"
							+ genericDTO.getReportId());

		}
		catch (DAOException daoException)
		{
			throw daoException;
		}
		catch (Exception e)
		{
			logger.error("Exception occured::" + e.getMessage());
			logger.error(e.getMessage(), e);
			throw new DPServiceException(e.getMessage());
		}

		return genericListDTO;
	}

	public String getLastSchedulerDate(int groupId, String distData, String otherData)
			throws DPServiceException, DAOException
	{
		// TODO Auto-generated method stub
		String lastSchDate = null;
		try
		{
			GenericReportsDao reportsDao = GenericReportsDaoImpl.getInstance();
			lastSchDate = reportsDao.getLastSchedulerDate(groupId, distData, otherData);

		}
		catch (DAOException daoException)
		{
			throw daoException;
		}
		catch (Exception e)
		{
			logger.error("Exception occured::" + e.getMessage());
			logger.error(e.getMessage(), e);

		}

		return lastSchDate;
	}

	public List<GenericOptionDTO> getRecoPeriodList(int reportId, int groupId) throws DAOException,
			DPServiceException
	{

		List<GenericOptionDTO> recoPeriodOptions = null;
		try
		{
			GenericReportsDao reportsDao = GenericReportsDaoImpl.getInstance();
			recoPeriodOptions = reportsDao.getRecoPeriodList(reportId, groupId);
		}
		catch (DAOException daoException)
		{
			throw daoException;
		}
		catch (Exception e)
		{
			logger.error("Exception occured::" + e.getMessage());
			logger.error(e.getMessage(), e);
			throw new DPServiceException(e.getMessage());
		}
		return recoPeriodOptions;
	}

	public List<GenericReportPararmeterDTO> exportToExcelDebitNote(
			GenericReportPararmeterDTO genericDTO) throws DAOException, DPServiceException
	{

		List<GenericReportPararmeterDTO> listReturn = new ArrayList<GenericReportPararmeterDTO>();
		// List<GenericOptionDTO> recoPeriodOptions = null;
		try
		{
			GenericReportsDao reportsDao = GenericReportsDaoImpl.getInstance();
			listReturn = reportsDao.exportToExcelDebitNote(genericDTO);
		}
		catch (DAOException daoException)
		{
			throw daoException;
		}
		catch (Exception e)
		{
			logger.error("Exception occured::" + e.getMessage());
			logger.error(e.getMessage(), e);
			throw new DPServiceException(e.getMessage());
		}
		return listReturn;
	}

	// Added by Neetika Commerical or Swap
	public List<GenericOptionDTO> getPTList() throws DAOException, DPServiceException
	{
		List<GenericOptionDTO> statusOptions = null;
		try
		{
			GenericReportsDao reportsDao = GenericReportsDaoImpl.getInstance();
			statusOptions = reportsDao.getPTList();
		}
		catch (DAOException daoException)
		{
			throw daoException;
		}
		catch (Exception e)
		{
			logger.error("Exception occured::" + e.getMessage());
			logger.error(e.getMessage(), e);
			throw new DPServiceException(e.getMessage());
		}
		return statusOptions;
	}

	// Added by Neetika distributor type SSD and SF
	public List<GenericOptionDTO> getDTList() throws DAOException, DPServiceException
	{
		List<GenericOptionDTO> statusOptions = null;
		try
		{
			GenericReportsDao reportsDao = GenericReportsDaoImpl.getInstance();
			statusOptions = reportsDao.getDTList();
		}
		catch (DAOException daoException)
		{
			throw daoException;
		}
		catch (Exception e)
		{
			logger.error("Exception occured::" + e.getMessage());
			logger.error(e.getMessage(), e);
			throw new DPServiceException(e.getMessage());
		}
		return statusOptions;
	}

	@Override
	public String getAgeingDays(String configid) throws DAOException,
			DPServiceException {
		// TODO Auto-generated method stub
		
		logger
				.info("********************** getAgeingDays() **************************************");
		String ageingDays = "";
		try {
			GenericReportsDao reportsDao = GenericReportsDaoImpl.getInstance();
			ageingDays = reportsDao.getAgeingDays(configid);

		} catch (DAOException daoException) {
			throw daoException;
		} catch (Exception e) {
			logger.error("Exception occured::" + e.getMessage());
			logger.error(e.getMessage(), e);
			throw new DPServiceException(e.getMessage());
		}

		return ageingDays;
	}
}
