/*
 * Created on Feb 7, 2005
 *
 * To change the template for this generated file go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
package com.ibm.virtualization.ussdactivationweb.beans;

import java.util.ArrayList;

/**
 * @author manish
 *
 * To change the template for this generated type comment go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
public class UserGroupBean {

	private String groupName;
	private String groupID;
	private ArrayList moduleList;
	private String circleID;
	private String circleCode;
	
	/**
	 * @return
	 */
	public String getGroupID() {
		return groupID;
	}

	/**
	 * @return
	 */
	public String getGroupName() {
		return groupName;
	}

	/**
	 * @return
	 */
	public ArrayList getModuleList() {
		return moduleList;
	}

	/**
	 * @param string
	 */
	public void setGroupID(String string) {
		groupID = string;
	}

	/**
	 * @param string
	 */
	public void setGroupName(String string) {
		groupName = string;
	}

	/**
	 * @param list
	 */
	public void setModuleList(ArrayList list) {
		moduleList = list;
	}

	/**
	 * @return
	 */
	public String getCircleID() {
		return circleID;
	}

	/**
	 * @param string
	 */
	public void setCircleID(String string) {
		circleID = string;
	}

	public String getCircleCode() {
		return circleCode;
	}

	public void setCircleCode(String circleCode) {
		this.circleCode = circleCode;
	}

}
