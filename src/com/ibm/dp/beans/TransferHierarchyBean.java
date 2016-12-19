package com.ibm.dp.beans;

import java.util.ArrayList;
import java.util.List;

import org.apache.struts.action.ActionForm;

import com.ibm.dp.dto.TransferHierarchyDto;

public class TransferHierarchyBean extends ActionForm {
	/**
	 * 
	 */
	private static final long serialVersionUID = -8345588481492585665L;
	private String userName;
	private String methodName;
	private String strSuccessMsg;
	private String strCheckedUsers;
	private String assignValues;
	private String accountNameParentNameMapping;
	
	public String getAccountNameParentNameMapping() {
		return accountNameParentNameMapping;
	}

	public void setAccountNameParentNameMapping(String accountNameParentNameMapping) {
		this.accountNameParentNameMapping = accountNameParentNameMapping;
	}

	public String getAssignValues() {
		return assignValues;
	}

	public void setAssignValues(String assignValues) {
		this.assignValues = assignValues;
	}

	private List<TransferHierarchyDto> transferHierachyList = new ArrayList<TransferHierarchyDto>();
	private List<TransferHierarchyDto> transferHierachyParentList = new ArrayList<TransferHierarchyDto>();
	

	public List<TransferHierarchyDto> getTransferHierachyParentList() {
		return transferHierachyParentList;
	}

	public void setTransferHierachyParentList(
			List<TransferHierarchyDto> transferHierachyParentList) {
		this.transferHierachyParentList = transferHierachyParentList;
	}

	public String getStrCheckedUsers() {
		return strCheckedUsers;
	}

	public void setStrCheckedUsers(String strCheckedUsers) {
		this.strCheckedUsers = strCheckedUsers;
	}

	public String getStrSuccessMsg() {
		return strSuccessMsg;
	}

	public void setStrSuccessMsg(String strSuccessMsg) {
		this.strSuccessMsg = strSuccessMsg;
	}

	public List<TransferHierarchyDto> getTransferHierachyList() {
		return transferHierachyList;
	}

	public void setTransferHierachyList(
			List<TransferHierarchyDto> transferHierachyList) {
		this.transferHierachyList = transferHierachyList;
	}

	
	public String getMethodName() {
		return methodName;
	}

	public void setMethodName(String methodName) {
		this.methodName = methodName;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}
	
	

}
