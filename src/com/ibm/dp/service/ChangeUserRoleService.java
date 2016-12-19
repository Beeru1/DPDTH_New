package com.ibm.dp.service;

import java.util.List;

import com.ibm.dp.dto.NewRoleDto;
import com.ibm.dp.dto.RoleDto;
import com.ibm.dp.dto.UserDto;
import com.ibm.dp.exception.DPServiceException;


public interface ChangeUserRoleService {

	public List<RoleDto> getRoleList(int groupId) throws DPServiceException;
	public List<UserDto> getUserList(String type,String circleId, String role,String currentUser) throws DPServiceException;
	public String checkUserChilds(String currentUserID,String currentRole) throws DPServiceException;
	public void updateRoles(NewRoleDto newRoleDto) throws DPServiceException;
	
}
