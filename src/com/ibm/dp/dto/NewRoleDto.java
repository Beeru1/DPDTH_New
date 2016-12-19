package com.ibm.dp.dto;

public class NewRoleDto {
	String currentRole ;	
	String currentCircle ;	
	String currentUser;	
	String newRole;	
	String newCircle;	
	String parent;
	String Approver1;
	String Approver2;
	 String SR_Number;
	 String createdBy="";
	 String transType="";
	
	public String getCurrentCircle() {
		return currentCircle;
	}
	public void setCurrentCircle(String currentCircle) {
		this.currentCircle = currentCircle;
	}
	public String getCurrentRole() {
		return currentRole;
	}
	public void setCurrentRole(String currentRole) {
		this.currentRole = currentRole;
	}
	public String getCurrentUser() {
		return currentUser;
	}
	public void setCurrentUser(String currentUser) {
		this.currentUser = currentUser;
	}
	public String getNewCircle() {
		return newCircle;
	}
	public void setNewCircle(String newCircle) {
		this.newCircle = newCircle;
	}
	public String getNewRole() {
		return newRole;
	}
	public void setNewRole(String newRole) {
		this.newRole = newRole;
	}
	public String getParent() {
		return parent;
	}
	public void setParent(String parent) {
		this.parent = parent;
	}
	public String getApprover1() {
		return Approver1;
	}
	public void setApprover1(String approver1) {
		Approver1 = approver1;
	}
	public String getApprover2() {
		return Approver2;
	}
	public void setApprover2(String approver2) {
		Approver2 = approver2;
	}
	public String getSR_Number() {
		return SR_Number;
	}
	public void setSR_Number(String number) {
		SR_Number = number;
	}
	public String getCreatedBy() {
		return createdBy;
	}
	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
	}
	public String getTransType() {
		return transType;
	}
	public void setTransType(String transType) {
		this.transType = transType;
	}		
	
	

}
