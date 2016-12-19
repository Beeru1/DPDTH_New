/**
 * 
 */
package com.ibm.virtualization.recharge.dto;

import com.ibm.virtualization.framework.bean.ChannelType;

/**
 * @author ibm
 *
 */
public class AuthenticationKeyInCache {
	
private int groupId;

private String url;

private ChannelType channelType;


public ChannelType getChannelType() {
	return channelType;
}
public void setChannelType(ChannelType channelType) {
	this.channelType = channelType;
}
public int getGroupId() {
	return groupId;
}
public void setGroupId(int groupId) {
	this.groupId = groupId;
}
public String getUrl() {
	return url;
}
public void setUrl(String url) {
	this.url = url;
}

public boolean equals(Object o) {
	/*System.out.println("========= equals method is invoked ============");
	System.out.println("======: group id :"+((AuthenticationKeyInCache) o).getGroupId()+"  url :"+((AuthenticationKeyInCache) o).getUrl());
	System.out.println("=====:this group id :"+this.groupId+" this url :"+this.url);
	*/if (o != null
			&& o instanceof AuthenticationKeyInCache
			&& (((AuthenticationKeyInCache) o).getGroupId() == this.groupId)
			&& (((AuthenticationKeyInCache) o).getUrl().equalsIgnoreCase(this.url))) {
		
		//System.out.println("======= returning true ============");
		return true;
	}
	//System.out.println("========== rturning false  ==========");
	return false;
}

public int hashCode() {
	int channelTypeLength = ChannelType.values().length;
	return (int) ((this.groupId - 1) * channelTypeLength
			+ this.channelType.getValue() + 1);
}
}
