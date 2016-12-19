package com.ibm.hbo.forms;

import java.text.SimpleDateFormat;
import java.util.ArrayList;

import javax.servlet.http.HttpServletRequest;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.upload.FormFile;

/**
 * Form bean for a Struts application.
 * Users may access 1 field on this form:
 * <ul>
 * <li>file - [your comment here]
 * </ul>
 * @version 	1.0
 * @author   
 */
public class HBOFileUploadFormBean extends ActionForm {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private FormFile thefile;
	private ArrayList invalidCounter;
	private ArrayList uploadFileList;
	private String FileName;
	private String FileType;
	private int FileId;
	private String UploadedDate;
	private String Uploadedby;
	private String ProcessedStatus;
	private String ProcessedDate;
	private String repStartDate;
	private String repEndDate;
	private String toDate1 = null;
	private String toDate2 = null;	
	private String filePath = null;
	private String contentOfFile = null;
	private String message = null;
	private String uploadStatus=null;
	private String realFileName=null;
	private String successFile=null;
	private String errorFile=null;	
	private String realSuccessFile=null;
	private String realErrorFile=null;		
	private String SuccErrPath = null;
	private ArrayList defaulterList = null;
	private String[] cols = null;
	private ArrayList arrCategory = null;
	private String category = "";
	private String product = "";
	
	//For Requisition
	String requisition_id = "";
	private ArrayList arrReqList = null;
	private String selectFile = "";
	private String userType="";
	String status=null;
	String statusDetails=null;
	String methodName = "";
	public String getMethodName() {
		return methodName;
	}

	public void setMethodName(String methodName) {
		this.methodName = methodName;
	}

	/**
	 * @return
	 */
	public String[] getCols() {
		return cols;
	}

	/**
	 * @return
	 */
	public String getContentOfFile() {
		return contentOfFile;
	}

	/**
	 * @return
	 */
	public ArrayList getDefaulterList() {
		return defaulterList;
	}

	/**
	 * @return
	 */
	public String getErrorFile() {
		return errorFile;
	}

	/**
	 * @return
	 */
	public int getFileId() {
		return FileId;
	}

	/**
	 * @return
	 */
	public String getFileName() {
		return FileName;
	}

	/**
	 * @return
	 */
	public String getFilePath() {
		return filePath;
	}

	/**
	 * @return
	 */
	public String getFileType() {
		return FileType;
	}

	/**
	 * @return
	 */
	public ArrayList getInvalidCounter() {
		return invalidCounter;
	}

	/**
	 * @return
	 */
	public String getMessage() {
		return message;
	}

	/**
	 * @return
	 */
	public String getProcessedDate() {
		return ProcessedDate;
	}

	/**
	 * @return
	 */
	public String getProcessedStatus() {
		return ProcessedStatus;
	}

	/**
	 * @return
	 */
	public String getRealErrorFile() {
		return realErrorFile;
	}

	/**
	 * @return
	 */
	public String getRealFileName() {
		return realFileName;
	}

	/**
	 * @return
	 */
	public String getRealSuccessFile() {
		return realSuccessFile;
	}

	/**
	 * @return
	 */
	public String getRepEndDate() {
		return repEndDate;
	}

	/**
	 * @return
	 */
	public String getRepStartDate() {
		return repStartDate;
	}

	/**
	 * @return
	 */
	public String getRequisition_id() {
		return requisition_id;
	}

	/**
	 * @return
	 */
	public String getSuccErrPath() {
		return SuccErrPath;
	}

	/**
	 * @return
	 */
	public String getSuccessFile() {
		return successFile;
	}

	/**
	 * @return
	 */
	public FormFile getThefile() {
		return thefile;
	}

	/**
	 * @return
	 */
	public String getToDate1() {
		return toDate1;
	}

	/**
	 * @return
	 */
	public String getToDate2() {
		return toDate2;
	}

	/**
	 * @return
	 */
	public String getUploadedby() {
		return Uploadedby;
	}

	/**
	 * @return
	 */
	public String getUploadedDate() {
		return UploadedDate;
	}

	/**
	 * @return
	 */
	public ArrayList getUploadFileList() {
		return uploadFileList;
	}

	/**
	 * @return
	 */
	public String getUploadStatus() {
		return uploadStatus;
	}

	/**
	 * @param list
	 */
	
	/**
	 * @param strings
	 */
	public void setCols(String[] strings) {
		cols = strings;
	}

	/**
	 * @param string
	 */
	public void setContentOfFile(String string) {
		contentOfFile = string;
	}

	/**
	 * @param list
	 */
	public void setDefaulterList(ArrayList list) {
		defaulterList = list;
	}

	/**
	 * @param string
	 */
	public void setErrorFile(String string) {
		errorFile = string;
	}

	/**
	 * @param i
	 */
	public void setFileId(int i) {
		FileId = i;
	}

	/**
	 * @param string
	 */
	public void setFileName(String string) {
		FileName = string;
	}

	/**
	 * @param string
	 */
	public void setFilePath(String string) {
		filePath = string;
	}

	/**
	 * @param string
	 */
	public void setFileType(String string) {
		FileType = string;
	}

	/**
	 * @param list
	 */
	public void setInvalidCounter(ArrayList list) {
		invalidCounter = list;
	}

	/**
	 * @param string
	 */
	public void setMessage(String string) {
		message = string;
	}

	/**
	 * @param string
	 */
	public void setProcessedDate(String string) {
		ProcessedDate = string;
	}

	/**
	 * @param string
	 */
	public void setProcessedStatus(String string) {
		ProcessedStatus = string;
	}

	/**
	 * @param string
	 */
	public void setRealErrorFile(String string) {
		realErrorFile = string;
	}

	/**
	 * @param string
	 */
	public void setRealFileName(String string) {
		realFileName = string;
	}

	/**
	 * @param string
	 */
	public void setRealSuccessFile(String string) {
		realSuccessFile = string;
	}

	/**
	 * @param string
	 */
	public void setRepEndDate(String string) {
		repEndDate = string;
	}

	/**
	 * @param string
	 */
	public void setRepStartDate(String string) {
		repStartDate = string;
	}

	/**
	 * @param string
	 */
	public void setRequisition_id(String string) {
		requisition_id = string;
	}

	/**
	 * @param string
	 */
	public void setSuccErrPath(String string) {
		SuccErrPath = string;
	}

	/**
	 * @param string
	 */
	public void setSuccessFile(String string) {
		successFile = string;
	}

	/**
	 * @param file
	 */
	public void setThefile(FormFile file) {
		thefile = file;
	}

	/**
	 * @param string
	 */
	public void setToDate1(String string) {
		toDate1 = string;
	}

	/**
	 * @param string
	 */
	public void setToDate2(String string) {
		toDate2 = string;
	}

	/**
	 * @param string
	 */
	public void setUploadedby(String string) {
		Uploadedby = string;
	}

	/**
	 * @param string
	 */
	public void setUploadedDate(String string) {
		UploadedDate = string;
	}

	/**
	 * @param list
	 */
	public void setUploadFileList(ArrayList list) {
		uploadFileList = list;
	}

	/**
	 * @param string
	 */
	public void setUploadStatus(String string) {
		uploadStatus = string;
	}
	/**
	 * @return
	 */
	public String getSelectFile() {
		return selectFile;
	}

	/**
	 * @param string
	 */
	public void setSelectFile(String string) {
		selectFile = string;
	}

	/**
	 * @return
	 */
	public ArrayList getArrReqList() {
		return arrReqList;
	}

	/**
	 * @param list
	 */
	public void setArrReqList(ArrayList list) {
		arrReqList = list;
	}

	/**
	 * @return
	 */
	public String getStatus() {
		return status;
	}

	/**
	 * @return
	 */
	public String getStatusDetails() {
		return statusDetails;
	}

	/**
	 * @param string
	 */
	public void setStatus(String string) {
		status = string;
	}

	/**
	 * @param string
	 */
	public void setStatusDetails(String string) {
		statusDetails = string;
	}

	public String getUserType() {
		return userType;
	}

	public void setUserType(String userType) {
		this.userType = userType;
	}

	public ArrayList getArrCategory() {
		return arrCategory;
	}

	public void setArrCategory(ArrayList arrCategory) {
		this.arrCategory = arrCategory;
	}

	public String getCategory() {
		return category;
	}

	public void setCategory(String category) {
		this.category = category;
	}

	public String getProduct() {
		return product;
	}

	public void setProduct(String product) {
		this.product = product;
	}

}
	