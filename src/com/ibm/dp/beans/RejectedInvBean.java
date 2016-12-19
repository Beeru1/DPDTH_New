package com.ibm.dp.beans;

import java.util.ArrayList;
import java.util.List;

import org.apache.struts.action.ActionForm;

import com.ibm.dpmisreports.common.SelectionCollection;

public class RejectedInvBean extends ActionForm{

	String monthId;
	private ArrayList<SelectionCollection> monthList=null;
	public ArrayList<SelectionCollection> getMonthList() {
		return monthList;
	}
	public void setMonthList(ArrayList<SelectionCollection> monthList) {
		this.monthList = monthList;
	}
	List invList =new ArrayList();
	public String getMonthId() {
		return monthId;
	}
	public void setMonthId(String monthId) {
		this.monthId = monthId;
	}
	
	
	

	public List getInvList() {
		return invList;
	}
	public void setInvList(List invList) {
		this.invList = invList;
	}
	
	
}
