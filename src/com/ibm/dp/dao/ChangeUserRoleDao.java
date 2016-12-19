package com.ibm.dp.dao;

import java.util.List;
import java.sql.Connection;

import com.ibm.dp.dto.NewRoleDto;
import com.ibm.dp.dto.RoleDto;
import com.ibm.dp.dto.UserDto;
import com.ibm.virtualization.recharge.exception.DAOException;

public interface ChangeUserRoleDao {

	
	public List<RoleDto> getRoleList(int groupId) throws DAOException;
	public List<UserDto> getUserList(String type,String circleID, String roleId,String currentUser) throws DAOException;
	public String checkUserChilds(String currentUserID,String currentRole) throws DAOException;
	public void updateRoles(NewRoleDto newRoleDto,Connection con) throws DAOException;
}
