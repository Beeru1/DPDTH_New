/*
 * Created on Oct 17, 2007
 *
 * To change the template for this generated file go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
package com.ibm.hbo.dto;

import java.io.Serializable;

/**
 * @author avanagar
 * @version 2.0
 * 
 */
public class HBOLogin implements Serializable {

	private String password = null;
	private String userId="";
	private String userName = null;
	private String userIP = "";
	/**
	 * @return
	 */
	public String getPassword() {
		return password;
	}

	/**
	 * @return
	 */
	public String getUserId() {
		return userId;
	}

	/**
	 * @return
	 */
	public String getUserName() {
		return userName;
	}

	/**
	 * @param string
	 */
	public void setPassword(String string) {
		password = string;
	}

	/**
	 * @param string
	 */
	public void setUserId(String string) {
		userId = string;
	}

	/**
	 * @param string
	 */
	public void setUserName(String string) {
		userName = string;
	}

	/**
	 * @return
	 */
	public String getUserIP() {
		return userIP;
	}

	/**
	 * @param string
	 */
	public void setUserIP(String string) {
		userIP = string;
	}

}
