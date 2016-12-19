package com.ibm.virtualization.recharge.message;

import com.ibm.virtualization.recharge.common.RequestType;

public class ChangePasswordMessageBean extends CoreMessageBean {

	/**
	 * 
	 */
	private static final long serialVersionUID = 4488962049330747741L;

	private String newMPassword;

	private RequestType requestType;

	private int mobileNo;

	private int email;

	public String getNewMPassword() {
		return newMPassword;
	}

	public void setNewMPassword(String newMPassword) {
		this.newMPassword = newMPassword;
	}

	public RequestType getRequestType() {
		return requestType;
	}

	public void setRequestType(RequestType requestType) {
		this.requestType = requestType;
	}

	public int getMobileNo() {
		return mobileNo;
	}

	public void setMobileNo(int mobileNo) {
		this.mobileNo = mobileNo;
	}

	public int getEmail() {
		return email;
	}

	public void setEmail(int email) {
		this.email = email;
	}

	/**
	 * detailed message for the Out Q listener will be inserted in the
	 * transaction
	 * 
	 * @return String - message in xml format
	 */
	public String getXML() {
		StringBuilder retValue = new StringBuilder();

		retValue.append("<ChangePasswordMessageBean>").append(super.toString());
		if (this.getResponseMessage() != null) {
			retValue.append("<responseMsg>").append(this.getResponseMessage())
					.append("</responseMsg>");
		}
		retValue.append("<mobileNo>").append(this.getRequesterMobileNo())
				.append("</mobileNo>");
		retValue.append("</ChangePasswordMessageBean>");
		return retValue.toString();
	}

	public String toString() {
		StringBuilder retValue = new StringBuilder();

		retValue.append("<ChangePasswordMessageBean>");
		if (this.requestType != null) {
			retValue.append("<requestType>").append(this.requestType.name())
					.append("</requestType>");
		}
		retValue.append("<mobileNo>").append(this.getRequesterMobileNo())
				.append("</mobileNo>");

		retValue = retValue.append("</ChangePasswordMessageBean>");
		return retValue.toString();
	}
}
