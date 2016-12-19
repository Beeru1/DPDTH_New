package com.ibm.hbo.common;

import java.util.ArrayList;

import com.ibm.virtualization.recharge.dto.UserSessionContext;

public class HBOUser {
	
	private Long id;
	private String userId;
	private String actorId;
	private String warehouseID;
	private String circleId;
	private String parentWarehouseId;
	private ArrayList roleList;
	public HBOUser()
	{
		userId="0";
		actorId="0";
		warehouseID="0";
		circleId="0";
		parentWarehouseId="0";
	}
	public HBOUser(UserSessionContext obj)
	{	
		id=obj.getId();
		userId=obj.getLoginName();
		actorId=Integer.toString(obj.getGroupId());
		warehouseID= Long.toString(obj.getId());
		circleId=obj.getCircleId()+"";
		//circleId=obj.getCircleCode();
		circleId=Integer.toString(obj.getCircleId());
		parentWarehouseId="";
	}
	public String getActorId() {
		return actorId;
	}
	public String getCircleId() {
		return circleId;
	}
	public String getParentWarehouseId() {
		return parentWarehouseId;
	}
	public String getUserId() {
		return userId;
	}
	public String getWarehouseID() {
		return warehouseID;
	}
	public Long getId() {
		return id;
	}
	public ArrayList getRoleList() {
		return roleList;
	}
	public void setRoleList(ArrayList roleList) {
		this.roleList = roleList;
	}
	
}
