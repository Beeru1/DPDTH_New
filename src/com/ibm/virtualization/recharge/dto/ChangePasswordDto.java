package com.ibm.virtualization.recharge.dto;

import com.ibm.virtualization.framework.bean.ChannelType;
import com.ibm.virtualization.framework.bean.TransactionStatus;
import com.ibm.virtualization.recharge.common.RequestType;
import com.ibm.virtualization.recharge.common.TransactionState;

public class ChangePasswordDto {

	long sourceAccountId;

	ChannelType channelType;

	TransactionStatus transactionStatus;
	
	TransactionState transactionState;
	
	RequestType requestType;

	long transactionId;

	String oldDigest;

	String newDigest;
	
	String newPassword;
	
	String oldPassword;
	
	String confirmPassword; 
	
	String cellId;

	/**
	 * @return the cellId
	 */
	public String getCellId() {
		return cellId;
	}

	/**
	 * @param cellId the cellId to set
	 */
	public void setCellId(String cellId) {
		this.cellId = cellId;
	}

	/**
	 * @return the confirmPassword
	 */
	public String getConfirmPassword() {
		return confirmPassword;
	}

	/**
	 * @param confirmPassword the confirmPassword to set
	 */
	public void setConfirmPassword(String confirmPassword) {
		this.confirmPassword = confirmPassword;
	}

	/**
	 * @return the oldPassword
	 */
	public String getOldPassword() {
		return oldPassword;
	}

	/**
	 * @param oldPassword the oldPassword to set
	 */
	public void setOldPassword(String oldPassword) {
		this.oldPassword = oldPassword;
	}

	public String getNewDigest() {
		return newDigest;
	}

	public void setNewDigest(String newDigest) {
		this.newDigest = newDigest;
	}

	public String getOldDigest() {
		return oldDigest;
	}

	public void setOldDigest(String oldDigest) {
		this.oldDigest = oldDigest;
	}

	public ChannelType getChannelType() {
		return channelType;
	}

	public void setChannelType(ChannelType channelType) {
		this.channelType = channelType;
	}

	public RequestType getRequestType() {
		return requestType;
	}

	public void setRequestType(RequestType requestType) {
		this.requestType = requestType;
	}

	public long getSourceAccountId() {
		return sourceAccountId;
	}

	public void setSourceAccountId(long sourceAccountId) {
		this.sourceAccountId = sourceAccountId;
	}

	public long getTransactionId() {
		return transactionId;
	}

	public void setTransactionId(long transactionId) {
		this.transactionId = transactionId;
	}

	public TransactionStatus getTransactionStatus() {
		return transactionStatus;
	}

	public void setTransactionStatus(TransactionStatus transactionStatus) {
		this.transactionStatus = transactionStatus;
	}
	
	
	

	public String getNewPassword() {
		return newPassword;
	}

	public void setNewPassword(String newPassword) {
		this.newPassword = newPassword;
	}

	@Override
	public String toString() {
		StringBuilder retValue = new StringBuilder();

		retValue = retValue.append("<ChangePasswordDto>").append(
				"<sourceAccountId>").append(this.sourceAccountId).append(
				"</sourceAccountId>");
				retValue.append("<channelType>").append(this.channelType.name()).append("</channelType>");
				retValue.append("<CellId>").append(this.cellId).append("</CellId>");
				retValue.append("<transactionState>").append(this.transactionState.getName()).append("</transactionState>");
				retValue.append("<transactionId>").append(this.transactionId).append(
						"</transactionId>").append("<requestType>").append(
						this.requestType.name()).append("</requestType>");
				if(this.oldDigest != null){
					retValue.append("<oldDigest>").append(this.oldDigest).append(
						"</oldDigest>");
				}
				retValue.append("<newDigest>").append(
						this.newDigest).append("</newDigest>")

				.append("</ChangePasswordDto>");

		return retValue.toString();
	}

	public TransactionState getTransactionState() {
		return transactionState;
	}

	public void setTransactionState(TransactionState transactionState) {
		this.transactionState = transactionState;
	}

}
