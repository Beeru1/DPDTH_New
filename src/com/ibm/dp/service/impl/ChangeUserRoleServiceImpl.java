package com.ibm.dp.service.impl;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

import org.apache.log4j.Logger;

import com.ibm.dp.dao.ChangeUserRoleDao;
import com.ibm.dp.dao.impl.ChangeUserRoleDaoImpl;
import com.ibm.dp.dto.NewRoleDto;
import com.ibm.dp.dto.UserDto;
import com.ibm.dp.exception.DPServiceException;
import com.ibm.dp.service.ChangeUserRoleService;
import com.ibm.virtualization.recharge.db.DBConnectionManager;
import com.ibm.virtualization.recharge.exception.DAOException;


public class ChangeUserRoleServiceImpl implements ChangeUserRoleService
{
	Logger logger = Logger.getLogger(ChangeUserRoleServiceImpl.class.getName());
	
	
	public List getRoleList(int groupId) throws DPServiceException
	{
		
		Connection connection = null;
		List roleList = null;
		ChangeUserRoleDao changeUserRoleDao =new ChangeUserRoleDaoImpl(connection);	
			try {
				connection = DBConnectionManager.getDBConnection();			
				roleList = changeUserRoleDao.getRoleList(groupId);
			} 
			catch (Exception e) {		
				logger.error("Exception occured :" + e.getMessage());
				e.printStackTrace();
				throw new DPServiceException(e.getMessage());
			}
		
		finally
		{							
			DBConnectionManager.releaseResources(connection);
		}
		return roleList;
	}
	
	public List<UserDto> getUserList(String type,String circleId, String roleID,String currentUser) throws DPServiceException
	{
		
		Connection connection = null;
		List<UserDto> userList = null;
		ChangeUserRoleDao changeUserRoleDao =new ChangeUserRoleDaoImpl(connection);	
			try {
				connection = DBConnectionManager.getDBConnection();			
				userList = changeUserRoleDao.getUserList(type,circleId, roleID,currentUser);
			} 
			catch (Exception e) {		
				logger.error("Exception occured :" + e.getMessage());
				e.printStackTrace();
				throw new DPServiceException(e.getMessage());
			}
		
		finally
		{							
			DBConnectionManager.releaseResources(connection);
		}
		return userList;
	}
	
	public String checkUserChilds(String currentUserID,String currentRole) throws DPServiceException
	{
		
		Connection connection = null;
		String haveChilds = "";
		ChangeUserRoleDao changeUserRoleDao =new ChangeUserRoleDaoImpl(connection);	
			try {
				connection = DBConnectionManager.getDBConnection();			
				haveChilds = changeUserRoleDao.checkUserChilds(currentUserID,currentRole);
			} 
			catch (Exception e) {		
				logger.error("Exception occured :" + e.getMessage());
				e.printStackTrace();
				throw new DPServiceException(e.getMessage());
			}
		
		finally
		{							
			DBConnectionManager.releaseResources(connection);
		}
		return haveChilds;
	}
	
	public void updateRoles(NewRoleDto newRoleDto) throws DPServiceException
	{
		
		Connection connection = null;
		
		ChangeUserRoleDao changeUserRoleDao =new ChangeUserRoleDaoImpl(connection);	
			try {
				connection = DBConnectionManager.getDBConnection();			
				changeUserRoleDao.updateRoles(newRoleDto,connection);
				connection.commit();
			} 
			catch (DAOException de) {
				try {
					connection.rollback();
				} catch (SQLException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				de.printStackTrace();
				logger.error("Exception occured : Message : " + de.getMessage());
				throw new DPServiceException(de.getMessage());
			}
			catch (Exception e) 
			{
				e.printStackTrace();
				throw new DPServiceException(e.getMessage());
			}
		
		finally
		{		
			
			DBConnectionManager.releaseResources(connection);
		}		
	}
}
