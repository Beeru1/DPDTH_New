package com.ibm.dp.beans;

import java.util.ArrayList;
import java.util.List;

import org.apache.struts.action.ActionForm;
import org.apache.struts.upload.FormFile;

import com.ibm.dp.dto.DpProductCategoryDto;
import com.ibm.dp.dto.PrintRecoDto;
import com.ibm.dp.dto.RecoDetailReportDTO;
import com.ibm.dp.dto.RecoPeriodDTO;
import com.ibm.dpmisreports.common.SelectionCollection;

public class RecoPeriodConfFormBean extends ActionForm

{
	private static final long serialVersionUID = 1098305345870553453L;
	
	private String strMessage="";
	private String strmsg="";
	private String  groupId;
	private String  fromDate;
	private String  toDate;
	//Added by Shiva for frontend validation for reco period configuration
	private String recoOpening;
	private String recoClosing;
	String RecoPeriodDTO_Hist;
	//End shiva
	private String  gracePeriod;
	private String  gracePeriodReco;
	private String  recoPeriod;
	private String methodName;
	private String closeRecoIds;
	
	private String deleteRecoId;
	private String lastToDate;
	private String recoID = "";
	private List recoList = new ArrayList();
	private List recoListTotal = new ArrayList();
	private String productId;
	private String productCategoryId;
	String productName;
	private List<DpProductCategoryDto> productList = new ArrayList<DpProductCategoryDto>();
	private String selectedProductId = "";
	private String selectedProductName = "";
	private String product = "";
	List<RecoDetailReportDTO> reportRecoDataList=new ArrayList<RecoDetailReportDTO>();
	private String[] productIdArray = null;
	private String hiddenCircleSelecIds;
	private String distName;
	private List<PrintRecoDto>  printRecoList  = new ArrayList<PrintRecoDto>();
	private String refNo;
	private String certDate;
	private String accountLevel="";
	private List<RecoPeriodDTO>  recoPeriodList  = new ArrayList<RecoPeriodDTO>();
	private int circleid = -1;
	private List<SelectionCollection> arrCIList=null;
	private String recoPeriodId="";
	private FormFile file;
	private String error_flag="";
	private List error_file = new ArrayList();
	private String gracePeriodMod="";
	private String recoValidate ;
	//added by aman
	private List tsmList = new ArrayList();
	List distList=new ArrayList();
	String accountId;
	String distAccId;
	 private String tsmName="";
	 private String tsmAccountId="";
	 private String tsmCategory="";
	 String accountName;
	 private String hiddenDistId;
	 
	 private String validateReco;
	 
	 
	 
	
	
	
	public String getValidateReco() {
		return validateReco;
	}
	public void setValidateReco(String validateReco) {
		this.validateReco = validateReco;
	}
	public String getHiddenDistId() {
		return hiddenDistId;
	}
	public void setHiddenDistId(String hiddenDistId) {
		this.hiddenDistId = hiddenDistId;
	}
	public String getAccountName() {
		return accountName;
	}
	public void setAccountName(String accountName) {
		this.accountName = accountName;
	}
	public String getAccountId() {
		return accountId;
	}
	public void setAccountId(String accountId) {
		this.accountId = accountId;
	}
	public String getGracePeriodMod() {
		return gracePeriodMod;
	}
	public void setGracePeriodMod(String gracePeriodMod) {
		this.gracePeriodMod = gracePeriodMod;
	}
	public List getError_file() {
		return error_file;
	}
	public void setError_file(List error_file) {
		this.error_file = error_file;
	}
	public String getError_flag() {
		return error_flag;
	}
	public void setError_flag(String error_flag) {
		this.error_flag = error_flag;
	}
	public String getRecoPeriodId() {
		return recoPeriodId;
	}
	public void setRecoPeriodId(String recoPeriodId) {
		this.recoPeriodId = recoPeriodId;
	}
	public String getFromDate() {
		return fromDate;
	}
	public void setFromDate(String fromDate) {
		this.fromDate = fromDate;
	}
	public String getToDate() {
		return toDate;
	}
	public void setToDate(String toDate) {
		this.toDate = toDate;
	}
	//Added by shiva
		
	
	public String getRecoOpening() {
		return recoOpening;
	}
	public String getRecoPeriodDTO_Hist() {
		return RecoPeriodDTO_Hist;
	}
	public void setRecoPeriodDTO_Hist(String recoPeriodDTO_Hist) {
		RecoPeriodDTO_Hist = recoPeriodDTO_Hist;
	}
	public void setRecoOpening(String recoOpening) {
		this.recoOpening = recoOpening;
	}
	public String getRecoClosing() {
		return recoClosing;
	}
	public void setRecoClosing(String recoClosing) {
		this.recoClosing = recoClosing;
	}
	
	//End
	

	public List getRecoList() {
		return recoList;
	}
	public void setRecoList(List recoList) {
		this.recoList = recoList;
	}
	public List getRecoListTotal() {
		return recoListTotal;
	}
	public void setRecoListTotal(List recoListTotal) {
		this.recoListTotal = recoListTotal;
	}
	public String getGracePeriod() {
		return gracePeriod;
	}
	public void setGracePeriod(String gracePeriod) {
		this.gracePeriod = gracePeriod;
	}
	public String getMethodName() {
		return methodName;
	}
	public void setMethodName(String methodName) {
		this.methodName = methodName;
	}
	public String getLastToDate() {
		return lastToDate;
	}
	public void setLastToDate(String lastToDate) {
		this.lastToDate = lastToDate;
	}
	
	
	public List<SelectionCollection> getArrCIList() {
		return arrCIList;
	}
	public void setArrCIList(List<SelectionCollection> arrCIList) {
		this.arrCIList = arrCIList;
	}
	public int getCircleid() {
		return circleid;
	}
	public void setCircleid(int circleid) {
		this.circleid = circleid;
	}
	public String getProductId() {
		return productId;
	}
	public void setProductId(String productId) {
		this.productId = productId;
	}
	public String getProductName() {
		return productName;
	}
	public void setProductName(String productName) {
		this.productName = productName;
	}
	public List<DpProductCategoryDto> getProductList() {
		return productList;
	}
	public void setProductList(List<DpProductCategoryDto> productList) {
		this.productList = productList;
	}
	public String getSelectedProductId() {
		return selectedProductId;
	}
	public void setSelectedProductId(String selectedProductId) {
		this.selectedProductId = selectedProductId;
	}
	public String getSelectedProductName() {
		return selectedProductName;
	}
	public void setSelectedProductName(String selectedProductName) {
		this.selectedProductName = selectedProductName;
	}
	public String getProduct() {
		return product;
	}
	public void setProduct(String product) {
		this.product = product;
	}
	
	public String[] getProductIdArray() {
		return productIdArray;
	}
	public void setProductIdArray(String[] productIdArray) {
		this.productIdArray = productIdArray;
	}
	public List<RecoDetailReportDTO> getReportRecoDataList() {
		return reportRecoDataList;
	}
	public void setReportRecoDataList(List<RecoDetailReportDTO> reportRecoDataList) {
		this.reportRecoDataList = reportRecoDataList;
	}
	public String getRecoPeriod() {
		return recoPeriod;
	}
	public void setRecoPeriod(String recoPeriod) {
		this.recoPeriod = recoPeriod;
	}
	public String getRecoID() {
		return recoID;
	}
	public void setRecoID(String recoID) {
		this.recoID = recoID;
	}
	public String getGroupId() {
		return groupId;
	}
	public void setGroupId(String groupId) {
		this.groupId = groupId;
	}
	public String getProductCategoryId() {
		return productCategoryId;
	}
	public void setProductCategoryId(String productCategoryId) {
		this.productCategoryId = productCategoryId;
	}
	public String getStrMessage() {
		return strMessage;
	}
	public void setStrMessage(String strMessage) {
		this.strMessage = strMessage;
	}
	public String getHiddenCircleSelecIds() {
		return hiddenCircleSelecIds;
	}
	public void setHiddenCircleSelecIds(String hiddenCircleSelecIds) {
		this.hiddenCircleSelecIds = hiddenCircleSelecIds;
	}
	public String getCloseRecoIds() {
		return closeRecoIds;
	}
	public void setCloseRecoIds(String closeRecoIds) {
		this.closeRecoIds = closeRecoIds;
	}
	public String getDeleteRecoId() {
		return deleteRecoId;
	}
	public void setDeleteRecoId(String deleteRecoId) {
		this.deleteRecoId = deleteRecoId;
	}
	public String getGracePeriodReco() {
		return gracePeriodReco;
	}
	public void setGracePeriodReco(String gracePeriodReco) {
		this.gracePeriodReco = gracePeriodReco;
	}
	public String getCertDate() {
		return certDate;
	}
	public void setCertDate(String certDate) {
		this.certDate = certDate;
	}
	public String getDistName() {
		return distName;
	}
	public void setDistName(String distName) {
		this.distName = distName;
	}
	public List<PrintRecoDto> getPrintRecoList() {
		return printRecoList;
	}
	public void setPrintRecoList(List<PrintRecoDto> printRecoList) {
		this.printRecoList = printRecoList;
	}
	public String getRefNo() {
		return refNo;
	}
	public void setRefNo(String refNo) {
		this.refNo = refNo;
	}
	public String getAccountLevel() {
		return accountLevel;
	}
	public void setAccountLevel(String accountLevel) {
		this.accountLevel = accountLevel;
	}
	public List<RecoPeriodDTO> getRecoPeriodList() {
		return recoPeriodList;
	}
	public void setRecoPeriodList(List<RecoPeriodDTO> recoPeriodList) {
		this.recoPeriodList = recoPeriodList;
	}
	public FormFile getFile() {
		return file;
	}
	public void setFile(FormFile file) {
		this.file = file;
	}
	public String getStrmsg() {
		return strmsg;
	}
	public void setStrmsg(String strmsg) {
		this.strmsg = strmsg;
	}
	public List getTsmList() {
		return tsmList;
	}
	public void setTsmList(List tsmList) {
		this.tsmList = tsmList;
	}
	public List getDistList() {
		return distList;
	}
	public void setDistList(List distList) {
		this.distList = distList;
	}
	public String getDistAccId() {
		return distAccId;
	}
	public void setDistAccId(String distAccId) {
		this.distAccId = distAccId;
	}
	public String getTsmName() {
		return tsmName;
	}
	public void setTsmName(String tsmName) {
		this.tsmName = tsmName;
	}
	public String getTsmAccountId() {
		return tsmAccountId;
	}
	public void setTsmAccountId(String tsmAccountId) {
		this.tsmAccountId = tsmAccountId;
	}
	public String getTsmCategory() {
		return tsmCategory;
	}
	public void setTsmCategory(String tsmCategory) {
		this.tsmCategory = tsmCategory;
	}
	public String getRecoValidate() {
		return recoValidate;
	}
	public void setRecoValidate(String recoValidate) {
		this.recoValidate = recoValidate;
	}

	

}
