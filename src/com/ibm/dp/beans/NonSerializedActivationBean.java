package com.ibm.dp.beans;

import java.util.ArrayList;
import java.util.List;

import org.apache.struts.validator.ValidatorForm;

import com.ibm.dp.dto.CircleDto;
import com.ibm.dp.dto.DistributorDTO;
import com.ibm.dp.dto.ProductMasterDto;
import com.ibm.dp.dto.TSMDto;
import com.ibm.dp.dto.TransferPendingSTBDto;

public class NonSerializedActivationBean  extends ValidatorForm {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private String successMessage="";
	private String circleName;
	private String fromCircleId;
	private String statusNser;
	private String statusSer;
	private String statusInvNser;
	private String statusInvSer;
		
	private String methodName;	
	private List<CircleDto>  circleList  = new ArrayList<CircleDto>();

	
	public String getFromCircleId() {
		return fromCircleId;
	}
	public void setFromCircleId(String fromCircleId) {
		this.fromCircleId = fromCircleId;
	}
	public List<CircleDto> getCircleList() {
		return circleList;
	}
	public void setCircleList(List<CircleDto> circleList) {
		this.circleList = circleList;
	}
	public String getCircleName() {
		return circleName;
	}
	public void setCircleName(String circleName) {
		this.circleName = circleName;
	}
	public String getMethodName() {
		return methodName;
	}
	public void setMethodName(String methodName) {
		this.methodName = methodName;
	}
	public String getSuccessMessage() {
		return successMessage;
	}
	public void setSuccessMessage(String successMessage) {
		this.successMessage = successMessage;
	}
	
	public String getStatusInvNser() {
		return statusInvNser;
	}
	public void setStatusInvNser(String statusInvNser) {
		this.statusInvNser = statusInvNser;
	}
	public String getStatusInvSer() {
		return statusInvSer;
	}
	public void setStatusInvSer(String statusInvSer) {
		this.statusInvSer = statusInvSer;
	}
	public String getStatusNser() {
		return statusNser;
	}
	public void setStatusNser(String statusNser) {
		this.statusNser = statusNser;
	}
	public String getStatusSer() {
		return statusSer;
	}
	public void setStatusSer(String statusSer) {
		this.statusSer = statusSer;
	}
	
	
}
