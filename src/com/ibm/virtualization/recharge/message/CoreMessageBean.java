package com.ibm.virtualization.recharge.message;

import java.util.Date;

import com.ibm.virtualization.framework.bean.ChannelType;
import com.ibm.virtualization.framework.bean.MessageBean;
import com.ibm.virtualization.recharge.common.TransactionState;

public class CoreMessageBean extends MessageBean {
	public enum LoginType {
		ACCOUNTCODE, MOBILENO
	};

	private static final long serialVersionUID = 2768907818813126169L;

	private String physicalId;

	private String loginId;

	private LoginType loginType;

	private String password;

	private String responseCode;

	private String reasonId;

	private String responseMessage;

	private ChannelType channelType;

	private String requestTime;

	private TransactionState transactionState;

	private long sourceAccountId;

	private long billableAccountId;
	
	private String cellId;
	
	public long getSourceAccountId() {
		return sourceAccountId;
	}

	public void setSourceAccountId(long sourceAccountId) {
		this.sourceAccountId = sourceAccountId;
	}

	public String getLoginId() {
		return loginId;
	}

	public void setLoginId(String loginId) {
		this.loginId = loginId;
	}

	public LoginType getLoginType() {
		return loginType;
	}

	public void setLoginType(LoginType loginType) {
		this.loginType = loginType;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getResponseCode() {
		return responseCode;
	}

	public void setResponseCode(String responseCode) {
		this.responseCode = responseCode;
	}

	public ChannelType getChannelType() {
		return channelType;
	}

	public void setChannelType(ChannelType channelType) {
		this.channelType = channelType;
	}

	public String getResponseMessage() {
		return responseMessage;
	}

	public void setResponseMessage(String responseMessage) {
		this.responseMessage = responseMessage;
	}

	public TransactionState getTransactionState() {
		return transactionState;
	}

	public void setTransactionState(TransactionState transationState) {
		this.transactionState = transationState;
	}

	public String getReasonId() {
		return reasonId;
	}

	public void setReasonId(String reasonId) {
		this.reasonId = reasonId;
	}

	/**
	 * @return the physicalId
	 */
	public String getPhysicalId() {
		return physicalId;
	}

	/**
	 * @param physicalId
	 *            the physicalId to set
	 */
	public void setPhysicalId(String physicalId) {
		this.physicalId = physicalId;
	}

	/**
	 * @return the requestTime
	 */
	public String getRequestTime() {
		return requestTime;
	}

	/**
	 * @param requestTime
	 *            the requestTime to set
	 */
	public void setRequestTime(String requestTime) {
		this.requestTime = requestTime;
	}

	public long getBillableAccountId() {
		return billableAccountId;
	}

	public void setBillableAccountId(long billableAccountId) {
		this.billableAccountId = billableAccountId;
	}

	/**
	 * the generic message that will be added to the transaction detail table
	 * 
	 * Each message bean will override this to add it own details
	 */
	public String toString() {
		StringBuilder retValue = new StringBuilder();
		if (this.loginId != null) {
			retValue.append("<loginId>").append(this.loginId).append(
					"</loginId>");
		}
		if (this.loginType != null) {
			retValue = retValue.append("<loginType>").append(
					this.loginType.name()).append("</loginType>");
		}
		retValue.append("<CellId>").append(this.getCellId())
			.append("</CellId>");
		if (this.channelType != null) {
			retValue = retValue.append("<channelType>").append(
					this.channelType.name()).append("</channelType>");
		}
		retValue.append("<sourceAccountId>").append(this.sourceAccountId)
				.append("</sourceAccountId>");
		if (this.billableAccountId != 0) {
			retValue.append("<billableAccountId>").append(
					this.billableAccountId).append("</billableAccountId>");
		}
		if (this.transactionState != null) {
			retValue.append("<transactionState>").append(this.transactionState)
					.append("</transactionState>");
		}
		retValue.append("<responseCode>").append(this.getResponseCode())
				.append("</responseCode>");
		return retValue.toString();
	}

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

}
