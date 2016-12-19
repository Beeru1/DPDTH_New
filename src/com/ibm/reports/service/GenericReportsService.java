package com.ibm.reports.service;

import java.util.List;

import com.ibm.dp.dto.CollectionReportDTO;
import com.ibm.dp.dto.DpProductCategoryDto;
import com.ibm.dp.dto.GenericReportPararmeterDTO;
import com.ibm.dp.exception.DPServiceException;
import com.ibm.reports.dto.CriteriaDTO;
import com.ibm.reports.dto.GenericOptionDTO;
import com.ibm.reports.dto.GenericReportsOutputDto;
import com.ibm.reports.dto.ReportDetailDTO;
import com.ibm.virtualization.recharge.exception.DAOException;

public interface GenericReportsService {
	
	public ReportDetailDTO getReportDetails(int reportId) throws DPServiceException, DAOException;
	public List<CriteriaDTO> getReportCriterias(int reportId,int groupId) throws DAOException, DPServiceException;
	public GenericReportsOutputDto exportToExcel(GenericReportPararmeterDTO genericDTO) throws DPServiceException, DAOException;
	public List<DpProductCategoryDto> getProductList(int reportId) throws   DPServiceException, DAOException;
	public List<DpProductCategoryDto> getTransactionType(int reportId) throws   DPServiceException, DAOException;
	
	public List<CollectionReportDTO> getCollectionTypeMaster(int reportId) throws DAOException, DPServiceException;
	public List<GenericOptionDTO> getDateOptions(int reportId) throws DAOException, DPServiceException;
	public List<GenericOptionDTO> getActivity(int reportId) throws DAOException, DPServiceException;
	public List<GenericOptionDTO> getPOStatusList() throws  DAOException, DPServiceException;
	public List<GenericOptionDTO> getSearchOptions(int reportId) throws DAOException, DPServiceException;
	public List<GenericOptionDTO> getPendingOptions(int reportId) throws DAOException, DPServiceException;
	public List<GenericOptionDTO> getTransferTypeOptions(int reportId) throws DAOException, DPServiceException;
	public List<GenericOptionDTO> getStatusOptions(int reportId) throws DAOException, DPServiceException;
	public List<GenericOptionDTO> getRecoStatus(int reportId)  throws DAOException, DPServiceException;
	public List<GenericOptionDTO> getSTBStatusList(int reportId)  throws DAOException, DPServiceException;
	public List<GenericOptionDTO> getAccountTypeList(int reportId, int groupId)  throws DAOException, DPServiceException;
	public List<GenericOptionDTO> getDcStatusList(int reportId)  throws DAOException, DPServiceException;
	public String getLastSchedulerDate(int groupId, String distData, String otherData) throws DPServiceException, DAOException;
	public List<GenericOptionDTO> getStockType()  throws DAOException, DPServiceException;
	public List<GenericOptionDTO> getRecoPeriodList(int reportId, int groupId)  throws DAOException, DPServiceException;
	public List<GenericReportPararmeterDTO> exportToExcelDebitNote(GenericReportPararmeterDTO genericDTO) throws DAOException, DPServiceException;
	
	//Added by neetika
	public List<GenericOptionDTO> getDTList()  throws DAOException, DPServiceException;
	public List<GenericOptionDTO> getPTList()  throws DAOException, DPServiceException;
	public String getAgeingDays(String configid) throws DAOException, DPServiceException;
}
