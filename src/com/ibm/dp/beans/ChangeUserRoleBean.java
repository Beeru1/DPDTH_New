package com.ibm.dp.beans;

import java.util.ArrayList;
import java.util.List;

import org.apache.struts.action.ActionForm;

import com.ibm.dp.dto.ActivationDetailReportDTO;
import com.ibm.dp.dto.CircleDto;
import com.ibm.dp.dto.DpProductCategoryDto;
import com.ibm.dp.dto.SerializedStockReportDTO;
import com.ibm.dp.dto.UserDto;
import com.ibm.dpmisreports.common.SelectionCollection;

public class ChangeUserRoleBean extends ActionForm

{
	private static final long serialVersionUID = 1098305345870553453L;
	
	private List<SelectionCollection> roleList =null;
	private List<CircleDto>  circleList  = new ArrayList<CircleDto>();
	private String circleName;
	private String fromCircleId;
	private String toCircleId;
	
	private String  userList ;
	private String userName;
	private String userId;
	private String parentList;
	private String roleName;	
	private String roleId;
	private String userRole;
	private String methodName;
	private String userRoleNew;
	private String parent;
	private String user;
	private String haveChilds;
	String remarks=""; 
	private String approver1;
	private String approver2;
	private String sr_Number;
	private String hiddenCircleSelectIds;
	private String transType;
	private String strMsg="";
	
	public String getStrMsg() {
		return strMsg;
	}
	public void setStrMsg(String strMsg) {
		this.strMsg = strMsg;
	}
	public String getUserRoleNew() {
		return userRoleNew;
	}
	public void setUserRoleNew(String userRoleNew) {
		this.userRoleNew = userRoleNew;
	}
	public List<SelectionCollection> getRoleList() {
		return roleList;
	}
	public void setRoleList(List<SelectionCollection> roleList) {
		this.roleList = roleList;
	}
	public String getRoleId() {
		return roleId;
	}
	public void setRoleId(String roleId) {
		this.roleId = roleId;
	}
	public String getRoleName() {
		return roleName;
	}
	public void setRoleName(String roleName) {
		this.roleName = roleName;
	}
	public String getUserRole() {
		return userRole;
	}
	public void setUserRole(String userRole) {
		this.userRole = userRole;
	}
	public String getMethodName() {
		return methodName;
	}
	public void setMethodName(String methodName) {
		this.methodName = methodName;
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
	public String getFromCircleId() {
		return fromCircleId;
	}
	public void setFromCircleId(String fromCircleId) {
		this.fromCircleId = fromCircleId;
	}
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public String getToCircleId() {
		return toCircleId;
	}
	public void setToCircleId(String toCircleId) {
		this.toCircleId = toCircleId;
	}
	public String getParent() {
		return parent;
	}
	public void setParent(String parent) {
		this.parent = parent;
	}
	public String getParentList() {
		return parentList;
	}
	public void setParentList(String parentList) {
		this.parentList = parentList;
	}
	public String getUserList() {
		return userList;
	}
	public void setUserList(String userList) {
		this.userList = userList;
	}
	public String getUser() {
		return user;
	}
	public void setUser(String user) {
		this.user = user;
	}
	public String getHaveChilds() {
		return haveChilds;
	}
	public void setHaveChilds(String haveChilds) {
		this.haveChilds = haveChilds;
	}
	public String getRemarks() {
		return remarks;
	}
	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}
	public String getApprover1() {
		return approver1;
	}
	public void setApprover1(String approver1) {
		this.approver1 = approver1;
	}
	public String getApprover2() {
		return approver2;
	}
	public void setApprover2(String approver2) {
		this.approver2 = approver2;
	}
	public String getSr_Number() {
		return sr_Number;
	}
	public void setSr_Number(String sr_Number) {
		this.sr_Number = sr_Number;
	}
	public String getHiddenCircleSelectIds() {
		return hiddenCircleSelectIds;
	}
	public void setHiddenCircleSelectIds(String hiddenCircleSelectIds) {
		this.hiddenCircleSelectIds = hiddenCircleSelectIds;
	}
	public String getTransType() {
		return transType;
	}
	public void setTransType(String transType) {
		this.transType = transType;
	}
	
	

}
