package com.ibm.reports.beans;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.apache.struts.action.ActionForm;
import org.apache.struts.upload.FormFile;

import com.ibm.dp.dto.CollectionReportDTO;
import com.ibm.dp.dto.DistRecoDto;
import com.ibm.dp.dto.DpProductCategoryDto;
import com.ibm.dpmisreports.common.SelectionCollection;
import com.ibm.reports.dto.CriteriaDTO;
import com.ibm.reports.dto.GenericOptionDTO;
import com.ibm.reports.dto.GenericReportsOutputDto;


public class CloseDistributorRecoBean extends ActionForm {
	

	private static final long serialVersionUID = 1L;
	private int reportId;
	private String reportName;
	private List<CriteriaDTO> criteriaList;
	private boolean circleRequired;
	private int circleUid;
	private int tsmUid;
	private int distributorUid;
	private int stbUid;
	private int collectionTypeUid;
	private boolean tsmRequired;
	private boolean fseRequired;
	private boolean stockTypeRequired;
	private boolean retailerRequired;
	private boolean productTypeRequired;
	private boolean distributorRequired;
	
	
	private boolean productRequired;
	private boolean statusOptionRequired;
	private boolean initvalstatusOption;
	private boolean initvalFse;
	private boolean initvalRetailer;
	private String collectionName;
	private String circleId;
	private String showCircle="";
	public String lastSchedulerDate;
	private int msgValidation;
	private boolean enableCircle;
	private boolean initvalCircle;
	private boolean enableTSM;
	private boolean enableFSE;
	private boolean enableRetailer;
	private boolean enablebusinessCategory;
	private boolean initvalTSM;
	private boolean enableDistributor;
	private boolean initvalDistributor;
	private boolean initvalBusinessCategory;
	private boolean enableSTBType;
	private boolean initvalSTBType;
	private boolean enableProductType;
	private boolean initvalProductType;
	private boolean initvalBusinessType;
	private boolean enableFromDate;
	private boolean initvalFromDate;
	private boolean initvalSearchCriteria;
	private String hiddenTrnsfrTypeSelec;
	private String groupId;
	private String hiddenStbStatus;
	private boolean enableToDate;
	private boolean initvalToDate;
	private boolean enableStatus;
	private boolean initvalStatus;
	private boolean initvalPOStatus;
	private boolean enableCollectionType;
	private boolean enableAccountType;
	private boolean enableSearchCriteria;
	private boolean enableAccountName;
	private boolean enableSearchOption;
	private boolean initvalSearchOption;
	private boolean initvalPendingAt;
	private boolean enableDateOption;
	private boolean enableActivity;
	
	private boolean enablePOStatus;
	private boolean enablePendingAt;
	private boolean enableDCStatus;
	private boolean enableTransferType;
	private boolean enableStbStatus;
	private boolean enableStockType;
	private boolean initvalStockType;
	private boolean initvalCollectionType;
	private boolean enableDCNo;
	private boolean initvalDCNo;
	private boolean enableRecoPeriod;
	private boolean initvalRecoPeriod;
	private boolean initvalStbStatus;
	private boolean initvalDCStatus;
	private boolean initvalaccountType;
	private boolean initvalaccountName;
	private boolean enableDate;
	private boolean initValDate;
	private boolean dateRequired;
	private boolean recoRequired;
	private boolean enableReco;
	
	
	List<String> idList = new ArrayList<String>();
	private String showTSM="";
	private String showDist="";	
	private boolean stbTypeRequired;
	private boolean collectionTypeRequired;
	private boolean accountTypeRequired;
	private boolean accountNameRequired;
	private boolean pendingAtRequired;
	private boolean transferTypeRequired;
	private boolean recoStatusRequired;
	private boolean recoPeriodRequired;
	private boolean searchOptionRequired;
	private boolean dateOptionRequired;
	private boolean activityRequired;
	private boolean poStatusRequired;
	private boolean dcStatusRequired;
	private boolean searchCreteriaRequired;
	private boolean dcNoRequired;
	private boolean stbStatusRequired;
	private boolean fromDateRequired;
	private boolean toDateRequired;
	private boolean statusRequired;
	private boolean businessCategoryRequired;
	
	private String tsmId;	
	private String distId;	
	private String fseId;
	private String retId;
	private String  accountType;
	private String  accountName;
	private String searchOption ;
	private String poSearchOptions ;
	private String recoStatus ;
	private String  dateOption;
	private String  activity;
	private String  transferType;
	private boolean  initvaltransferType;
	private boolean  initvalrecoStatus;
	private boolean  enablerecoStatus;
	private boolean  initvalDateOption;
	private boolean  initvalActivity;
	private boolean  initvalReco;
	private String  pendingAt;
	private String  poStatus;
	private String  dcStatus;
	private String searchCriteria;
	private String fromDate;
	private String toDate;
	private String date;	
	private String status;
	private String methodName;
	private String hiddenCircleSelectIds;
	private String hiddenTsmSelectIds;
	private String hiddenDistSelectIds;
	private String hiddenSTBTypeSelectIds;
	private String hiddenStockTypeSelectIds;
	private String distLoginId;
	private String hiddenCollectionTypeSelectIds;
	private String hiddenAccountTypeSelectIds;
	private String hiddenAccountNameSelectIds;
	private String hiddenStatusSelectIds;
	private String hiddenPOStatusSelectIds;
	private String hiddenProductTypeSelectIds;
	private String hiddenRecoStatusSelectId;
	private String hiddenPendingAtSelectIds;
	private String hiddenDCStatusSelectIds;
	private String hiddenFseSelectIds;
	private String hiddenBusinesscatSelectIds;
	private String stbType;
	private String stockType;
	private String inventoryChangeDate;
	private String recoveredSTB;
	private String installedSTB;
	private String dcNo;
	private String hiddenProductSelectIds;
	private String productId;
	private String stbStatus;
	private String hiddendistIds;
	private String  distData;
	private String  otherUserData;
	private String accountLevel;
	private int dateValidation;
	private List<String> headers;
	private List<String[]> output;
	private List<String> reportData;
	private String reportLabel;
	private String recoPeriod;
	private String hiddenRecoRequiredIds;
	private String transactionType;
	
	private List<DpProductCategoryDto> productList =null;
	private List<CollectionReportDTO> collectionType;
	private List<SelectionCollection> circleList =null;
	private List<SelectionCollection> tsmList =null;
	private List<SelectionCollection> distList =null;
	private List<SelectionCollection> accountList =null;
	private List<SelectionCollection> accountNameList =null;
	private List<GenericOptionDTO> searchOptionList =null;
	private List<GenericOptionDTO> statusOptionList =null;
	private List<GenericOptionDTO> recoStatusList =null;
	private List<GenericOptionDTO> stbStatusList =null;
	private List<GenericOptionDTO> dateOptionList =null;
	private List<GenericOptionDTO> activityList =null;
	private List<GenericOptionDTO> poStatusList =null;
	private List<GenericOptionDTO> dcStatusList =null;
	private List<GenericOptionDTO> pendingAtList =null;
	private List<GenericOptionDTO> transferTypeList =null;
	private List<CollectionReportDTO> reportList =null;
	private List<SelectionCollection> fseList =null;
	private List<SelectionCollection> retailerList =null;
	private List<GenericOptionDTO> accountTypeList =null;
	private List<GenericOptionDTO> stockTypeList =null;
	private List<GenericOptionDTO> recoPeriodList =null;
	private List<GenericReportsOutputDto> reportRecoDataList=null;
	private List<DpProductCategoryDto> businessCategoryList =null;
	
	private String[] productIdArray = null;
	private String hiddenCircleSelecIds;
	private String strMessage="";
	
	

	private String hiddenTsmSelecIds;
	private String hiddenDistSelecIds;
	private String hiddenProductSeletIds;
	private List recoListTotal = new ArrayList();
	
	private String oknotok;
	private List<DistRecoDto> detailsList;

	private String distOlmId;
	private String circleName;
	private String productName;
	private String typeOfStock;
	private String distFlag;
	private String distRemarks;
	private String flushOutFlag;
	private String dthRemarks;
	private FormFile  uploadedFile;
	private String strmsg;
	private String  error_flag;
	private List error_file = new ArrayList();
	
	
	public String getOknotok() {
		return oknotok;
	}
	public void setOknotok(String oknotok) {
		this.oknotok = oknotok;
	}
	public String getRecoID() {
		return recoID;
	}
	public void setRecoID(String recoID) {
		this.recoID = recoID;
	}
	private String recoID = "";
	
	
	public List getRecoListTotal() {
		return recoListTotal;
	}
	public void setRecoListTotal(List recoListTotal) {
		this.recoListTotal = recoListTotal;
	}
	public boolean isActivityRequired() {
		return activityRequired;
	}
	public void setActivityRequired(boolean activityRequired) {
		this.activityRequired = activityRequired;
	}
	HashMap idMap=new HashMap();
	
	public HashMap getIdMap() {
		return idMap;
	}
	public void setIdMap(HashMap idMap) {
		this.idMap = idMap;
	}
	public String getCircleId() {
		return circleId;
	}
	public void setCircleId(String circleId) {
		this.circleId = circleId;
	}
	public List<SelectionCollection> getCircleList() {
		return circleList;
	}
	public void setCircleList(List<SelectionCollection> circleList) {
		this.circleList = circleList;
	}
	public boolean isCircleRequired() {
		return circleRequired;
	}
	public void setCircleRequired(boolean circleRequired) {
		this.circleRequired = circleRequired;
	}
	public List<CollectionReportDTO> getCollectionType() {
		return collectionType;
	}
	public void setCollectionType(List<CollectionReportDTO> collectionType) {
		this.collectionType = collectionType;
	}
	public boolean isCollectionTypeRequired() {
		return collectionTypeRequired;
	}
	public void setCollectionTypeRequired(boolean collectionTypeRequired) {
		this.collectionTypeRequired = collectionTypeRequired;
	}
	public List<CriteriaDTO> getCriteriaList() {
		return criteriaList;
	}
	public void setCriteriaList(List<CriteriaDTO> criteriaList) {
		this.criteriaList = criteriaList;
	}
	public String getDcNo() {
		return dcNo;
	}
	public void setDcNo(String dcNo) {
		this.dcNo = dcNo;
	}
	public String getDistId() {
		return distId;
	}
	public void setDistId(String distId) {
		this.distId = distId;
	}
	public List<SelectionCollection> getDistList() {
		return distList;
	}
	public void setDistList(List<SelectionCollection> distList) {
		this.distList = distList;
	}
	public String getDistLoginId() {
		return distLoginId;
	}
	public void setDistLoginId(String distLoginId) {
		this.distLoginId = distLoginId;
	}
	public boolean isDistributorRequired() {
		return distributorRequired;
	}
	public void setDistributorRequired(boolean distributorRequired) {
		this.distributorRequired = distributorRequired;
	}
	public String getFromDate() {
		return fromDate;
	}
	public void setFromDate(String fromDate) {
		this.fromDate = fromDate;
	}
	public boolean isFromDateRequired() {
		return fromDateRequired;
	}
	public void setFromDateRequired(boolean fromDateRequired) {
		this.fromDateRequired = fromDateRequired;
	}

	

	public String getHiddenCircleSelectIds() {
		return hiddenCircleSelectIds;
	}
	public void setHiddenCircleSelectIds(String hiddenCircleSelectIds) {
		this.hiddenCircleSelectIds = hiddenCircleSelectIds;
	}

	public String getHiddenProductSelectIds() {
		return hiddenProductSelectIds;
	}
	public void setHiddenProductSelectIds(String hiddenProductSelectIds) {
		this.hiddenProductSelectIds = hiddenProductSelectIds;
	}
	public String getHiddenProductTypeSelectIds() {
		return hiddenProductTypeSelectIds;
	}
	public void setHiddenProductTypeSelectIds(String hiddenProductTypeSelectIds) {
		this.hiddenProductTypeSelectIds = hiddenProductTypeSelectIds;
	}
	
	public String getHiddenStatusSelectIds() {
		return hiddenStatusSelectIds;
	}
	public void setHiddenStatusSelectIds(String hiddenStatusSelectIds) {
		this.hiddenStatusSelectIds = hiddenStatusSelectIds;
	}
	public String getHiddenSTBTypeSelectIds() {
		return hiddenSTBTypeSelectIds;
	}
	public void setHiddenSTBTypeSelectIds(String hiddenSTBTypeSelectIds) {
		this.hiddenSTBTypeSelectIds = hiddenSTBTypeSelectIds;
	}
	public String getHiddenDistSelectIds() {
		return hiddenDistSelectIds;
	}
	public void setHiddenDistSelectIds(String hiddenDistSelectIds) {
		this.hiddenDistSelectIds = hiddenDistSelectIds;
	}
	
	public String getHiddenTsmSelectIds() {
		return hiddenTsmSelectIds;
	}
	public void setHiddenTsmSelectIds(String hiddenTsmSelectIds) {
		this.hiddenTsmSelectIds = hiddenTsmSelectIds;
	}
	public String getInstalledSTB() {
		return installedSTB;
	}
	public void setInstalledSTB(String installedSTB) {
		this.installedSTB = installedSTB;
	}
	public String getInventoryChangeDate() {
		return inventoryChangeDate;
	}
	public void setInventoryChangeDate(String inventoryChangeDate) {
		this.inventoryChangeDate = inventoryChangeDate;
	}
	public String getMethodName() {
		return methodName;
	}
	public void setMethodName(String methodName) {
		this.methodName = methodName;
	}
	
	public List<GenericOptionDTO> getPoStatusList() {
		return poStatusList;
	}
	public void setPoStatusList(List<GenericOptionDTO> poStatusList) {
		this.poStatusList = poStatusList;
	}
	public List<DpProductCategoryDto> getProductList() {
		return productList;
	}
	public void setProductList(List<DpProductCategoryDto> productList) {
		this.productList = productList;
	}
	public String getRecoveredSTB() {
		return recoveredSTB;
	}
	public void setRecoveredSTB(String recoveredSTB) {
		this.recoveredSTB = recoveredSTB;
	}
	public int getReportId() {
		return reportId;
	}
	public void setReportId(int reportId) {
		this.reportId = reportId;
	}
	public List<CollectionReportDTO> getReportList() {
		return reportList;
	}
	public void setReportList(List<CollectionReportDTO> reportList) {
		this.reportList = reportList;
	}
	public String getReportName() {
		return reportName;
	}
	public void setReportName(String reportName) {
		this.reportName = reportName;
	}
	public String getShowCircle() {
		return showCircle;
	}
	public void setShowCircle(String showCircle) {
		this.showCircle = showCircle;
	}
	public String getShowDist() {
		return showDist;
	}
	public void setShowDist(String showDist) {
		this.showDist = showDist;
	}
	public String getShowTSM() {
		return showTSM;
	}
	public void setShowTSM(String showTSM) {
		this.showTSM = showTSM;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public boolean isStatusRequired() {
		return statusRequired;
	}
	public void setStatusRequired(boolean statusRequired) {
		this.statusRequired = statusRequired;
	}
	public String getStbType() {
		return stbType;
	}
	public void setStbType(String stbType) {
		this.stbType = stbType;
	}
	public boolean isStbTypeRequired() {
		return stbTypeRequired;
	}
	public void setStbTypeRequired(boolean stbTypeRequired) {
		this.stbTypeRequired = stbTypeRequired;
	}
	public String getToDate() {
		return toDate;
	}
	public void setToDate(String toDate) {
		this.toDate = toDate;
	}
	public boolean isToDateRequired() {
		return toDateRequired;
	}
	public void setToDateRequired(boolean toDateRequired) {
		this.toDateRequired = toDateRequired;
	}
	public String getTsmId() {
		return tsmId;
	}
	public void setTsmId(String tsmId) {
		this.tsmId = tsmId;
	}
	public List<SelectionCollection> getTsmList() {
		return tsmList;
	}
	public void setTsmList(List<SelectionCollection> tsmList) {
		this.tsmList = tsmList;
	}
	public boolean isTsmRequired() {
		return tsmRequired;
	}
	public void setTsmRequired(boolean tsmRequired) {
		this.tsmRequired = tsmRequired;
	}
	public String getProductId() {
		return productId;
	}
	public void setProductId(String productId) {
		this.productId = productId;
	}
	public String getCollectionName() {
		return collectionName;
	}
	public void setCollectionName(String collectionName) {
		this.collectionName = collectionName;
	}
	
	public List<String> getHeaders() {
		return headers;
	}
	public void setHeaders(List<String> headers) {
		this.headers = headers;
	}
	public List<String[]> getOutput() {
		return output;
	}
	public void setOutput(List<String[]> output) {
		this.output = output;
	}
	
	
	public boolean isEnableCircle() {
		return enableCircle;
	}
	public void setEnableCircle(boolean enableCircle) {
		this.enableCircle = enableCircle;
	}
	public boolean isEnableCollectionType() {
		return enableCollectionType;
	}
	public void setEnableCollectionType(boolean enableCollectionType) {
		this.enableCollectionType = enableCollectionType;
	}
	public boolean isEnableDistributor() {
		return enableDistributor;
	}
	public void setEnableDistributor(boolean enableDistributor) {
		this.enableDistributor = enableDistributor;
	}
	public boolean isEnableFromDate() {
		return enableFromDate;
	}
	public void setEnableFromDate(boolean enableFromDate) {
		this.enableFromDate = enableFromDate;
	}
	public boolean isEnableStatus() {
		return enableStatus;
	}
	public void setEnableStatus(boolean enableStatus) {
		this.enableStatus = enableStatus;
	}
	public boolean isEnableSTBType() {
		return enableSTBType;
	}
	public void setEnableSTBType(boolean enableSTBType) {
		this.enableSTBType = enableSTBType;
	}
	public boolean isEnableToDate() {
		return enableToDate;
	}
	public void setEnableToDate(boolean enableToDate) {
		this.enableToDate = enableToDate;
	}
	public boolean isEnableTSM() {
		return enableTSM;
	}
	public void setEnableTSM(boolean enableTSM) {
		this.enableTSM = enableTSM;
	}
	public boolean isInitvalCircle() {
		return initvalCircle;
	}
	public void setInitvalCircle(boolean initvalCircle) {
		this.initvalCircle = initvalCircle;
	}
	public boolean isInitvalCollectionType() {
		return initvalCollectionType;
	}
	public void setInitvalCollectionType(boolean initvalCollectionType) {
		this.initvalCollectionType = initvalCollectionType;
	}
	public boolean isInitvalDistributor() {
		return initvalDistributor;
	}
	public void setInitvalDistributor(boolean initvalDistributor) {
		this.initvalDistributor = initvalDistributor;
	}
	public boolean isInitvalFromDate() {
		return initvalFromDate;
	}
	public void setInitvalFromDate(boolean initvalFromDate) {
		this.initvalFromDate = initvalFromDate;
	}
	public boolean isInitvalStatus() {
		return initvalStatus;
	}
	public void setInitvalStatus(boolean initvalStatus) {
		this.initvalStatus = initvalStatus;
	}
	public boolean isInitvalSTBType() {
		return initvalSTBType;
	}
	public void setInitvalSTBType(boolean initvalSTBType) {
		this.initvalSTBType = initvalSTBType;
	}
	public boolean isInitvalToDate() {
		return initvalToDate;
	}
	public void setInitvalToDate(boolean initvalToDate) {
		this.initvalToDate = initvalToDate;
	}
	public boolean isInitvalTSM() {
		return initvalTSM;
	}
	public void setInitvalTSM(boolean initvalTSM) {
		this.initvalTSM = initvalTSM;
	}
	public List<String> getReportData() {
		return reportData;
	}
	public void setReportData(List<String> reportData) {
		this.reportData = reportData;
	}
	public String getAccountType() {
		return accountType;
	}
	public void setAccountType(String accountType) {
		this.accountType = accountType;
	}
	public boolean isAccountTypeRequired() {
		return accountTypeRequired;
	}
	public void setAccountTypeRequired(boolean accountTypeRequired) {
		this.accountTypeRequired = accountTypeRequired;
	}
	public boolean isEnableAccountType() {
		return enableAccountType;
	}
	public void setEnableAccountType(boolean enableAccountType) {
		this.enableAccountType = enableAccountType;
	}
	
	public List<SelectionCollection> getAccountList() {
		return accountList;
	}
	public void setAccountList(List<SelectionCollection> accountList) {
		this.accountList = accountList;
	}
	public boolean isEnableSearchOption() {
		return enableSearchOption;
	}
	public void setEnableSearchOption(boolean enableSearchOption) {
		this.enableSearchOption = enableSearchOption;
	}
	
	
	public boolean isSearchOptionRequired() {
		return searchOptionRequired;
	}
	public void setSearchOptionRequired(boolean searchOptionRequired) {
		this.searchOptionRequired = searchOptionRequired;
	}
	public String getSearchCriteria() {
		return searchCriteria;
	}
	public void setSearchCriteria(String searchCriteria) {
		this.searchCriteria = searchCriteria;
	}
	public boolean isEnablePOStatus() {
		return enablePOStatus;
	}
	public void setEnablePOStatus(boolean enablePOStatus) {
		this.enablePOStatus = enablePOStatus;
	}
	
	public String getPoStatus() {
		return poStatus;
	}
	public void setPoStatus(String poStatus) {
		this.poStatus = poStatus;
	}
	public boolean isPoStatusRequired() {
		return poStatusRequired;
	}
	public void setPoStatusRequired(boolean poStatusRequired) {
		this.poStatusRequired = poStatusRequired;
	}
	public String getDateOption() {
		return dateOption;
	}
	public void setDateOption(String dateOption) {
		this.dateOption = dateOption;
	}
	
	public List<GenericOptionDTO> getDateOptionList() {
		return dateOptionList;
	}
	public boolean isEnableActivity() {
		return enableActivity;
	}
	public void setEnableActivity(boolean enableActivity) {
		this.enableActivity = enableActivity;
	}
	public String getActivity() {
		return activity;
	}
	public void setActivity(String activity) {
		this.activity = activity;
	}
	public boolean isInitvalActivity() {
		return initvalActivity;
	}
	public void setInitvalActivity(boolean initvalActivity) {
		this.initvalActivity = initvalActivity;
	}
	public List<GenericOptionDTO> getActivityList() {
		return activityList;
	}
	public void setActivityList(List<GenericOptionDTO> activityList) {
		this.activityList = activityList;
	}
	public void setDateOptionList(List<GenericOptionDTO> dateOptionList) {
		this.dateOptionList = dateOptionList;
	}
	public boolean isDateOptionRequired() {
		return dateOptionRequired;
	}
	public void setDateOptionRequired(boolean dateOptionRequired) {
		this.dateOptionRequired = dateOptionRequired;
	}
	public boolean isEnableDateOption() {
		return enableDateOption;
	}
	public void setEnableDateOption(boolean enableDateOption) {
		this.enableDateOption = enableDateOption;
	}

	public String getPendingAt() {
		return pendingAt;
	}
	public void setPendingAt(String pendingAt) {
		this.pendingAt = pendingAt;
	}
	
	public List<GenericOptionDTO> getPendingAtList() {
		return pendingAtList;
	}
	public void setPendingAtList(List<GenericOptionDTO> pendingAtList) {
		this.pendingAtList = pendingAtList;
	}
	public boolean isPendingAtRequired() {
		return pendingAtRequired;
	}
	public void setPendingAtRequired(boolean pendingAtRequired) {
		this.pendingAtRequired = pendingAtRequired;
	}
	public boolean isEnablePendingAt() {
		return enablePendingAt;
	}
	public void setEnablePendingAt(boolean enablePendingAt) {
		this.enablePendingAt = enablePendingAt;
	}
	public boolean isEnableTransferType() {
		return enableTransferType;
	}
	public void setEnableTransferType(boolean enableTransferType) {
		this.enableTransferType = enableTransferType;
	}
	public String getTransferType() {
		return transferType;
	}
	public void setTransferType(String transferType) {
		this.transferType = transferType;
	}
	
	public List<GenericOptionDTO> getTransferTypeList() {
		return transferTypeList;
	}
	public void setTransferTypeList(List<GenericOptionDTO> transferTypeList) {
		this.transferTypeList = transferTypeList;
	}
	public boolean isTransferTypeRequired() {
		return transferTypeRequired;
	}
	public void setTransferTypeRequired(boolean transferTypeRequired) {
		this.transferTypeRequired = transferTypeRequired;
	}
	public String getAccountName() {
		return accountName;
	}
	public void setAccountName(String accountName) {
		this.accountName = accountName;
	}
	public List<SelectionCollection> getAccountNameList() {
		return accountNameList;
	}
	public void setAccountNameList(List<SelectionCollection> accountNameList) {
		this.accountNameList = accountNameList;
	}
	public boolean isAccountNameRequired() {
		return accountNameRequired;
	}
	public void setAccountNameRequired(boolean accountNameRequired) {
		this.accountNameRequired = accountNameRequired;
	}
	public boolean isEnableAccountName() {
		return enableAccountName;
	}
	public void setEnableAccountName(boolean enableAccountName) {
		this.enableAccountName = enableAccountName;
	}

	public String getDcStatus() {
		return dcStatus;
	}
	public void setDcStatus(String dcStatus) {
		this.dcStatus = dcStatus;
	}
	public List<GenericOptionDTO> getDcStatusList() {
		return dcStatusList;
	}
	public void setDcStatusList(List<GenericOptionDTO> dcStatusList) {
		this.dcStatusList = dcStatusList;
	}
	public boolean isDcStatusRequired() {
		return dcStatusRequired;
	}
	public void setDcStatusRequired(boolean dcStatusRequired) {
		this.dcStatusRequired = dcStatusRequired;
	}
	public boolean isEnableDCStatus() {
		return enableDCStatus;
	}
	public void setEnableDCStatus(boolean enableDCStatus) {
		this.enableDCStatus = enableDCStatus;
	}
	
	public boolean isDcNoRequired() {
		return dcNoRequired;
	}
	public void setDcNoRequired(boolean dcNoRequired) {
		this.dcNoRequired = dcNoRequired;
	}
	public boolean isSearchCreteriaRequired() {
		return searchCreteriaRequired;
	}
	public void setSearchCreteriaRequired(boolean searchCreteriaRequired) {
		this.searchCreteriaRequired = searchCreteriaRequired;
	}
	public String getHiddenAccountNameSelectIds() {
		return hiddenAccountNameSelectIds;
	}
	public void setHiddenAccountNameSelectIds(String hiddenAccountNameSelectIds) {
		this.hiddenAccountNameSelectIds = hiddenAccountNameSelectIds;
	}
	public String getHiddenAccountTypeSelectIds() {
		return hiddenAccountTypeSelectIds;
	}
	public void setHiddenAccountTypeSelectIds(String hiddenAccountTypeSelectIds) {
		this.hiddenAccountTypeSelectIds = hiddenAccountTypeSelectIds;
	}
	public String getHiddenCollectionTypeSelectIds() {
		return hiddenCollectionTypeSelectIds;
	}
	public void setHiddenCollectionTypeSelectIds(
			String hiddenCollectionTypeSelectIds) {
		this.hiddenCollectionTypeSelectIds = hiddenCollectionTypeSelectIds;
	}
	public String getHiddenDCStatusSelectIds() {
		return hiddenDCStatusSelectIds;
	}
	public void setHiddenDCStatusSelectIds(String hiddenDCStatusSelectIds) {
		this.hiddenDCStatusSelectIds = hiddenDCStatusSelectIds;
	}
	public String getHiddenPendingAtSelectIds() {
		return hiddenPendingAtSelectIds;
	}
	public void setHiddenPendingAtSelectIds(String hiddenPendingAtSelectIds) {
		this.hiddenPendingAtSelectIds = hiddenPendingAtSelectIds;
	}
	public String getHiddenPOStatusSelectIds() {
		return hiddenPOStatusSelectIds;
	}
	public void setHiddenPOStatusSelectIds(String hiddenPOStatusSelectIds) {
		this.hiddenPOStatusSelectIds = hiddenPOStatusSelectIds;
	}
	public int getCircleUid() {
		return circleUid;
	}
	public void setCircleUid(int circleUid) {
		this.circleUid = circleUid;
	}
	public int getCollectionTypeUid() {
		return collectionTypeUid;
	}
	public void setCollectionTypeUid(int collectionTypeUid) {
		this.collectionTypeUid = collectionTypeUid;
	}
	public int getDistributorUid() {
		return distributorUid;
	}
	public void setDistributorUid(int distributorUid) {
		this.distributorUid = distributorUid;
	}
	public int getStbUid() {
		return stbUid;
	}
	public void setStbUid(int stbUid) {
		this.stbUid = stbUid;
	}
	public int getTsmUid() {
		return tsmUid;
	}
	public void setTsmUid(int tsmUid) {
		this.tsmUid = tsmUid;
	}
	public List<String> getIdList() {
		return idList;
	}
	public void setIdList(List<String> idList) {
		this.idList = idList;
	}
	public boolean isInitvalPOStatus() {
		return initvalPOStatus;
	}
	public void setInitvalPOStatus(boolean initvalPOStatus) {
		this.initvalPOStatus = initvalPOStatus;
	}
	public boolean isInitvalSearchOption() {
		return initvalSearchOption;
	}
	public void setInitvalSearchOption(boolean initvalSearchOption) {
		this.initvalSearchOption = initvalSearchOption;
	}
	
	public boolean isInitvalPendingAt() {
		return initvalPendingAt;
	}
	public void setInitvalPendingAt(boolean initvalPendingAt) {
		this.initvalPendingAt = initvalPendingAt;
	}
	public String getPoSearchOptions() {
		return poSearchOptions;
	}
	public void setPoSearchOptions(String poSearchOptions) {
		this.poSearchOptions = poSearchOptions;
	}
	public String getSearchOption() {
		return searchOption;
	}
	public void setSearchOption(String searchOption) {
		this.searchOption = searchOption;
	}
	public List<GenericOptionDTO> getSearchOptionList() {
		return searchOptionList;
	}
	public void setSearchOptionList(List<GenericOptionDTO> searchOptionList) {
		this.searchOptionList = searchOptionList;
	}
	public boolean isInitvaltransferType() {
		return initvaltransferType;
	}
	public void setInitvaltransferType(boolean initvaltransferType) {
		this.initvaltransferType = initvaltransferType;
	}
	public boolean isInitvalstatusOption() {
		return initvalstatusOption;
	}
	public void setInitvalstatusOption(boolean initvalstatusOption) {
		this.initvalstatusOption = initvalstatusOption;
	}
	public boolean isStatusOptionRequired() {
		return statusOptionRequired;
	}
	public void setStatusOptionRequired(boolean statusOptionRequired) {
		this.statusOptionRequired = statusOptionRequired;
	}
	public List<GenericOptionDTO> getStatusOptionList() {
		return statusOptionList;
	}
	public void setStatusOptionList(List<GenericOptionDTO> statusOptionList) {
		this.statusOptionList = statusOptionList;
	}
	public boolean isInitvalDateOption() {
		return initvalDateOption;
	}
	public void setInitvalDateOption(boolean initvalDateOption) {
		this.initvalDateOption = initvalDateOption;
	}
	public boolean isEnableSearchCriteria() {
		return enableSearchCriteria;
	}
	public void setEnableSearchCriteria(boolean enableSearchCriteria) {
		this.enableSearchCriteria = enableSearchCriteria;
	}
	public boolean isInitvalSearchCriteria() {
		return initvalSearchCriteria;
	}
	public void setInitvalSearchCriteria(boolean initvalSearchCriteria) {
		this.initvalSearchCriteria = initvalSearchCriteria;
	}
	public boolean isEnablerecoStatus() {
		return enablerecoStatus;
	}
	public void setEnablerecoStatus(boolean enablerecoStatus) {
		this.enablerecoStatus = enablerecoStatus;
	}
	public boolean isInitvalrecoStatus() {
		return initvalrecoStatus;
	}
	public void setInitvalrecoStatus(boolean initvalrecoStatus) {
		this.initvalrecoStatus = initvalrecoStatus;
	}
	public String getRecoStatus() {
		return recoStatus;
	}
	public void setRecoStatus(String recoStatus) {
		this.recoStatus = recoStatus;
	}
	public boolean isRecoStatusRequired() {
		return recoStatusRequired;
	}
	public void setRecoStatusRequired(boolean recoStatusRequired) {
		this.recoStatusRequired = recoStatusRequired;
	}
	public List<GenericOptionDTO> getRecoStatusList() {
		return recoStatusList;
	}
	public void setRecoStatusList(List<GenericOptionDTO> recoStatusList) {
		this.recoStatusList = recoStatusList;
	}
	public String getHiddenRecoStatusSelectId() {
		return hiddenRecoStatusSelectId;
	}
	public void setHiddenRecoStatusSelectId(String hiddenRecoStatusSelectId) {
		this.hiddenRecoStatusSelectId = hiddenRecoStatusSelectId;
	}
	public boolean isEnableProductType() {
		return enableProductType;
	}
	public void setEnableProductType(boolean enableProductType) {
		this.enableProductType = enableProductType;
	}
	public boolean isInitvalProductType() {
		return initvalProductType;
	}
	public void setInitvalProductType(boolean initvalProductType) {
		this.initvalProductType = initvalProductType;
	}
	public boolean isProductRequired() {
		return productRequired;
	}
	public void setProductRequired(boolean productRequired) {
		this.productRequired = productRequired;
	}
	public boolean isEnableStbStatus() {
		return enableStbStatus;
	}
	public void setEnableStbStatus(boolean enableStbStatus) {
		this.enableStbStatus = enableStbStatus;
	}
	public boolean isInitvalStbStatus() {
		return initvalStbStatus;
	}
	public void setInitvalStbStatus(boolean initvalStbStatus) {
		this.initvalStbStatus = initvalStbStatus;
	}
	public String getStbStatus() {
		return stbStatus;
	}
	public void setStbStatus(String stbStatus) {
		this.stbStatus = stbStatus;
	}
	public List<GenericOptionDTO> getStbStatusList() {
		return stbStatusList;
	}
	public void setStbStatusList(List<GenericOptionDTO> stbStatusList) {
		this.stbStatusList = stbStatusList;
	}
	public boolean isStbStatusRequired() {
		return stbStatusRequired;
	}
	public void setStbStatusRequired(boolean stbStatusRequired) {
		this.stbStatusRequired = stbStatusRequired;
	}
	public boolean isProductTypeRequired() {
		return productTypeRequired;
	}
	public void setProductTypeRequired(boolean productTypeRequired) {
		this.productTypeRequired = productTypeRequired;
	}
	public boolean isEnableFSE() {
		return enableFSE;
	}
	public void setEnableFSE(boolean enableFSE) {
		this.enableFSE = enableFSE;
	}
	public boolean isEnableRetailer() {
		return enableRetailer;
	}
	public void setEnableRetailer(boolean enableRetailer) {
		this.enableRetailer = enableRetailer;
	}
	public boolean isFseRequired() {
		return fseRequired;
	}
	public void setFseRequired(boolean fseRequired) {
		this.fseRequired = fseRequired;
	}
	public boolean isInitvalFse() {
		return initvalFse;
	}
	public void setInitvalFse(boolean initvalFse) {
		this.initvalFse = initvalFse;
	}
	public boolean isInitvalRetailer() {
		return initvalRetailer;
	}
	public void setInitvalRetailer(boolean initvalRetailer) {
		this.initvalRetailer = initvalRetailer;
	}
	public boolean isRetailerRequired() {
		return retailerRequired;
	}
	public void setRetailerRequired(boolean retailerRequired) {
		this.retailerRequired = retailerRequired;
	}
	public List<SelectionCollection> getFseList() {
		return fseList;
	}
	public void setFseList(List<SelectionCollection> fseList) {
		this.fseList = fseList;
	}
	public List<SelectionCollection> getRetailerList() {
		return retailerList;
	}
	public void setRetailerList(List<SelectionCollection> retailerList) {
		this.retailerList = retailerList;
	}
	public String getFseId() {
		return fseId;
	}
	public void setFseId(String fseId) {
		this.fseId = fseId;
	}
	public String getRetId() {
		return retId;
	}
	public void setRetId(String retId) {
		this.retId = retId;
	}
	public String getHiddenFseSelectIds() {
		return hiddenFseSelectIds;
	}
	public void setHiddenFseSelectIds(String hiddenFseSelectIds) {
		this.hiddenFseSelectIds = hiddenFseSelectIds;
	}
	public String getHiddendistIds() {
		return hiddendistIds;
	}
	public void setHiddendistIds(String hiddendistIds) {
		this.hiddendistIds = hiddendistIds;
	}
	public boolean isInitvalDCStatus() {
		return initvalDCStatus;
	}
	public void setInitvalDCStatus(boolean initvalDCStatus) {
		this.initvalDCStatus = initvalDCStatus;
	}
	public boolean isInitvalaccountName() {
		return initvalaccountName;
	}
	public void setInitvalaccountName(boolean initvalaccountName) {
		this.initvalaccountName = initvalaccountName;
	}
	public boolean isInitvalaccountType() {
		return initvalaccountType;
	}
	public void setInitvalaccountType(boolean initvalaccountType) {
		this.initvalaccountType = initvalaccountType;
	}
	public boolean isEnableDCNo() {
		return enableDCNo;
	}
	public void setEnableDCNo(boolean enableDCNo) {
		this.enableDCNo = enableDCNo;
	}
	public boolean isInitvalDCNo() {
		return initvalDCNo;
	}
	public void setInitvalDCNo(boolean initvalDCNo) {
		this.initvalDCNo = initvalDCNo;
	}
	public String getHiddenTrnsfrTypeSelec() {
		return hiddenTrnsfrTypeSelec;
	}
	public void setHiddenTrnsfrTypeSelec(String hiddenTrnsfrTypeSelec) {
		this.hiddenTrnsfrTypeSelec = hiddenTrnsfrTypeSelec;
	}
	public boolean isEnableRecoPeriod() {
		return enableRecoPeriod;
	}
	public void setEnableRecoPeriod(boolean enableRecoPeriod) {
		this.enableRecoPeriod = enableRecoPeriod;
	}
	public boolean isInitvalRecoPeriod() {
		return initvalRecoPeriod;
	}
	public void setInitvalRecoPeriod(boolean initvalRecoPeriod) {
		this.initvalRecoPeriod = initvalRecoPeriod;
	}
	public boolean isRecoPeriodRequired() {
		return recoPeriodRequired;
	}
	public void setRecoPeriodRequired(boolean recoPeriodRequired) {
		this.recoPeriodRequired = recoPeriodRequired;
	}
	public String getGroupId() {
		return groupId;
	}
	public void setGroupId(String groupId) {
		this.groupId = groupId;
	}
	public String getHiddenStbStatus() {
		return hiddenStbStatus;
	}
	public void setHiddenStbStatus(String hiddenStbStatus) {
		this.hiddenStbStatus = hiddenStbStatus;
	}
	public String getDistData() {
		return distData;
	}
	public void setDistData(String distData) {
		this.distData = distData;
	}
	public String getAccountLevel() {
		return accountLevel;
	}
	public void setAccountLevel(String accountLevel) {
		this.accountLevel = accountLevel;
	}
	public List<GenericOptionDTO> getAccountTypeList() {
		return accountTypeList;
	}
	public void setAccountTypeList(List<GenericOptionDTO> accountTypeList) {
		this.accountTypeList = accountTypeList;
	}
	public String getOtherUserData() {
		return otherUserData;
	}
	public void setOtherUserData(String otherUserData) {
		this.otherUserData = otherUserData;
	}
	public boolean isEnableDate() {
		return enableDate;
	}
	public void setEnableDate(boolean enableDate) {
		this.enableDate = enableDate;
	}
	public boolean isInitValDate() {
		return initValDate;
	}
	public void setInitValDate(boolean initValDate) {
		this.initValDate = initValDate;
	}
	public boolean isDateRequired() {
		return dateRequired;
	}
	public void setDateRequired(boolean dateRequired) {
		this.dateRequired = dateRequired;
	}
	public String getDate() {
		return date;
	}
	public void setDate(String date) {
		this.date = date;
	}
	public int getDateValidation() {
		return dateValidation;
	}
	public void setDateValidation(int dateValidation) {
		this.dateValidation = dateValidation;
	}
	/**
	 * @return the reportLabel
	 */
	public String getReportLabel() {
		return reportLabel;
	}
	/**
	 * @param reportLabel the reportLabel to set
	 */
	public void setReportLabel(String reportLabel) {
		this.reportLabel = reportLabel;
	}
	public String getLastSchedulerDate() {
		return lastSchedulerDate;
	}
	public void setLastSchedulerDate(String lastSchedulerDate) {
		this.lastSchedulerDate = lastSchedulerDate;
	}
	
	public int getMsgValidation() {
		return msgValidation;
	}
	public void setMsgValidation(int msgValidation) {
		this.msgValidation = msgValidation;
	}
	public boolean isEnableStockType() {
		return enableStockType;
	}
	public void setEnableStockType(boolean enableStockType) {
		this.enableStockType = enableStockType;
	}
	public boolean isInitvalStockType() {
		return initvalStockType;
	}
	public void setInitvalStockType(boolean initvalStockType) {
		this.initvalStockType = initvalStockType;
	}
	public boolean isStockTypeRequired() {
		return stockTypeRequired;
	}
	public void setStockTypeRequired(boolean stockTypeRequired) {
		this.stockTypeRequired = stockTypeRequired;
	}
	public String getHiddenStockTypeSelectIds() {
		return hiddenStockTypeSelectIds;
	}
	public void setHiddenStockTypeSelectIds(String hiddenStockTypeSelectIds) {
		this.hiddenStockTypeSelectIds = hiddenStockTypeSelectIds;
	}
	public String getStockType() {
		return stockType;
	}
	public void setStockType(String stockType) {
		this.stockType = stockType;
	}
	public List<GenericOptionDTO> getStockTypeList() {
		return stockTypeList;
	}
	public void setStockTypeList(List<GenericOptionDTO> stockTypeList) {
		this.stockTypeList = stockTypeList;
	}
	public boolean isEnableReco() {
		return enableReco;
	}
	public void setEnableReco(boolean enableReco) {
		this.enableReco = enableReco;
	}
	public boolean isInitvalReco() {
		return initvalReco;
	}
	public void setInitvalReco(boolean initvalReco) {
		this.initvalReco = initvalReco;
	}
	public boolean isRecoRequired() {
		return recoRequired;
	}
	public void setRecoRequired(boolean recoRequired) {
		this.recoRequired = recoRequired;
	}
	public List<GenericOptionDTO> getRecoPeriodList() {
		return recoPeriodList;
	}
	public void setRecoPeriodList(List<GenericOptionDTO> recoPeriodList) {
		this.recoPeriodList = recoPeriodList;
	}
	public String getRecoPeriod() {
		return recoPeriod;
	}
	public void setRecoPeriod(String recoPeriod) {
		this.recoPeriod = recoPeriod;
	}
	public String getHiddenRecoRequiredIds() {
		return hiddenRecoRequiredIds;
	}
	public void setHiddenRecoRequiredIds(String hiddenRecoRequiredIds) {
		this.hiddenRecoRequiredIds = hiddenRecoRequiredIds;
	}
	public List<GenericReportsOutputDto> getReportRecoDataList() {
		return reportRecoDataList;
	}
	public void setReportRecoDataList(
			List<GenericReportsOutputDto> reportRecoDataList) {
		this.reportRecoDataList = reportRecoDataList;
	}
	public String getHiddenCircleSelecIds() {
		return hiddenCircleSelecIds;
	}
	public void setHiddenCircleSelecIds(String hiddenCircleSelecIds) {
		this.hiddenCircleSelecIds = hiddenCircleSelecIds;
	}
	public String[] getProductIdArray() {
		return productIdArray;
	}
	public void setProductIdArray(String[] productIdArray) {
		this.productIdArray = productIdArray;
	}
	public String getStrMessage() {
		return strMessage;
	}
	public void setStrMessage(String strMessage) {
		this.strMessage = strMessage;
	}
	public boolean isBusinessCategoryRequired() {
		return businessCategoryRequired;
	}
	public void setBusinessCategoryRequired(boolean businessCategoryRequired) {
		this.businessCategoryRequired = businessCategoryRequired;
	}
	public boolean isInitvalBusinessType() {
		return initvalBusinessType;
	}
	public void setInitvalBusinessType(boolean initvalBusinessType) {
		this.initvalBusinessType = initvalBusinessType;
	}
	public List<DpProductCategoryDto> getBusinessCategoryList() {
		return businessCategoryList;
	}
	public void setBusinessCategoryList(
			List<DpProductCategoryDto> businessCategoryList) {
		this.businessCategoryList = businessCategoryList;
	}
	public boolean isEnablebusinessCategory() {
		return enablebusinessCategory;
	}
	public void setEnablebusinessCategory(boolean enablebusinessCategory) {
		this.enablebusinessCategory = enablebusinessCategory;
	}
	public boolean isInitvalBusinessCategory() {
		return initvalBusinessCategory;
	}
	public void setInitvalBusinessCategory(boolean initvalBusinessCategory) {
		this.initvalBusinessCategory = initvalBusinessCategory;
	}
	public String getTransactionType() {
		return transactionType;
	}
	public void setTransactionType(String transactionType) {
		this.transactionType = transactionType;
	}
	public String getHiddenBusinesscatSelectIds() {
		return hiddenBusinesscatSelectIds;
	}
	public void setHiddenBusinesscatSelectIds(String hiddenBusinesscatSelectIds) {
		this.hiddenBusinesscatSelectIds = hiddenBusinesscatSelectIds;
	}
	public String getHiddenTsmSelecIds() {
		return hiddenTsmSelecIds;
	}
	public void setHiddenTsmSelecIds(String hiddenTsmSelecIds) {
		this.hiddenTsmSelecIds = hiddenTsmSelecIds;
	}
	public String getHiddenDistSelecIds() {
		return hiddenDistSelecIds;
	}
	public void setHiddenDistSelecIds(String hiddenDistSelecIds) {
		this.hiddenDistSelecIds = hiddenDistSelecIds;
	}
	public String getHiddenProductSeletIds() {
		return hiddenProductSeletIds;
	}
	public void setHiddenProductSeletIds(String hiddenProductSeletIds) {
		this.hiddenProductSeletIds = hiddenProductSeletIds;
	}
	public List<DistRecoDto> getDetailsList() {
		return detailsList;
	}
	public void setDetailsList(List<DistRecoDto> detailsList) {
		this.detailsList = detailsList;
	}
	public String getDistOlmId() {
		return distOlmId;
	}
	public void setDistOlmId(String distOlmId) {
		this.distOlmId = distOlmId;
	}
	public String getCircleName() {
		return circleName;
	}
	public void setCircleName(String circleName) {
		this.circleName = circleName;
	}
	public String getProductName() {
		return productName;
	}
	public void setProductName(String productName) {
		this.productName = productName;
	}
	public String getTypeOfStock() {
		return typeOfStock;
	}
	public void setTypeOfStock(String typeOfStock) {
		this.typeOfStock = typeOfStock;
	}
	public String getDistFlag() {
		return distFlag;
	}
	public void setDistFlag(String distFlag) {
		this.distFlag = distFlag;
	}
	public String getDistRemarks() {
		return distRemarks;
	}
	public void setDistRemarks(String distRemarks) {
		this.distRemarks = distRemarks;
	}
	public String getFlushOutFlag() {
		return flushOutFlag;
	}
	public void setFlushOutFlag(String flushOutFlag) {
		this.flushOutFlag = flushOutFlag;
	}
	public String getDthRemarks() {
		return dthRemarks;
	}
	public void setDthRemarks(String dthRemarks) {
		this.dthRemarks = dthRemarks;
	}
	public FormFile getUploadedFile() {
		return uploadedFile;
	}
	public void setUploadedFile(FormFile uploadedFile) {
		this.uploadedFile = uploadedFile;
	}
	public String getStrmsg() {
		return strmsg;
	}
	public void setStrmsg(String strmsg) {
		this.strmsg = strmsg;
	}
	public String getError_flag() {
		return error_flag;
	}
	public void setError_flag(String error_flag) {
		this.error_flag = error_flag;
	}
	public List getError_file() {
		return error_file;
	}
	public void setError_file(List error_file) {
		this.error_file = error_file;
	}
	
}