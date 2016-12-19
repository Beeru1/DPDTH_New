package com.ibm.dp.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

import com.ibm.dp.common.DBQueries;
import com.ibm.dp.dao.ChangeUserRoleDao;
import com.ibm.dp.dto.NewRoleDto;
import com.ibm.dp.dto.RoleDto;
import com.ibm.dp.dto.UserDto;
import com.ibm.virtualization.recharge.dao.rdbms.BaseDaoRdbms;
import com.ibm.virtualization.recharge.db.DBConnectionManager;
import com.ibm.virtualization.recharge.exception.DAOException;
import com.ibm.virtualization.ussdactivationweb.utils.Constants;

 
public class ChangeUserRoleDaoImpl extends BaseDaoRdbms implements ChangeUserRoleDao{
	
public static Logger logger = Logger.getLogger(ChangeUserRoleDaoImpl.class.getName());

public static final String SQL_ROLE_LIST 	= DBQueries.SQL_ROLE_LIST;
public static final String GET_PARENT_ACCOUNT_ID = DBQueries.GET_PARENT_ACCOUNT_ID;
protected static final String SQL_INSERT_ACC_DETAIL_HIST_IT_HELP = DBQueries.SQL_INSERT_ACC_DETAIL_HIST_IT_HELP;
protected static final String SQL_INSERT_LOGIN_DETAIL_HIST = DBQueries.SQL_INSERT_LOGIN_DETAIL_HIST;
protected final static String SQL_INSERT_ACC_CIRCLE_MAP_HIST = DBQueries.SQL_INSERT_ACC_CIRCLE_MAP_HIST;
public static final String SQL_GET_USER_LIST = DBQueries.SQL_GET_USER_LIST;

	
	public ChangeUserRoleDaoImpl(Connection connection) 
	{
		super(connection);
	}
	public List<RoleDto> getRoleList(int groupId) throws DAOException
	{
		Connection connection = DBConnectionManager.getDBConnection();
		List<RoleDto> roleList	= new ArrayList<RoleDto>();
		PreparedStatement pstmt = null;
		ResultSet rset	= null;
		RoleDto  roleDto = null;
				
		try
		{
			//SQL_GET_USER_LIST instead of SQL_ROLE_LIST to hide finance user from user role dropdown
			pstmt = connection.prepareStatement(SQL_GET_USER_LIST);
			logger.info("groupId::::"+groupId);
			pstmt.setInt(1, groupId);
			rset = pstmt.executeQuery();
			roleList = new ArrayList<RoleDto>();
			
			while(rset.next())
			{
				roleDto = new RoleDto();
				roleDto.setRoleId(rset.getString("GROUP_ID"));
				roleDto.setRoleName(rset.getString("GROUP_NAME"));
				
									
				roleList.add(roleDto);
			}
		}
		catch (Exception e) 
		{
			e.printStackTrace();
			throw new DAOException(e.getMessage());
		}
		finally
		{
			DBConnectionManager.releaseResources(pstmt, rset);
			//DBConnectionManager.releaseResources(connection);
		}
		return roleList;
	}
	
	public List<UserDto> getUserList(String type,String circleId, String roleID,String currentUser) throws DAOException
	{
		Connection connection = DBConnectionManager.getDBConnection();
		List<UserDto> userList	= new ArrayList<UserDto>();
		PreparedStatement pstmt = null;
		ResultSet rset	= null;
		UserDto  userDto = null;
		
		int rID=0;
		String temp="";
		try
		{
			logger.info("roleID:::::::::"+roleID);
			logger.info("circleId:::::::::"+circleId+"type"+type);
			String sql ="";
			if(roleID!=null && !roleID.equalsIgnoreCase("")){
				rID = Integer.parseInt(roleID)-1;
			}
			logger.info("rID:::::::::"+rID);
			
			if(type != null && type.equalsIgnoreCase("parent") && rID == 1 )
			{
				sql = " SELECT ACCOUNT_ID,ACCOUNT_NAME FROM VR_ACCOUNT_DETAILS where ACCOUNT_LEVEL=? and ACCOUNT_ID != "+currentUser;
			}
			else { 
				if(rID==2 && type.equalsIgnoreCase("user") && Integer.parseInt(circleId)==0)
				{
					sql = " SELECT VAD.ACCOUNT_ID,VAD.ACCOUNT_NAME FROM VR_ACCOUNT_DETAILS VAD,DP_ACCOUNT_CIRCLE_MAP CM " +
					"where VAD.ACCOUNT_ID=CM.ACCOUNT_ID AND ACCOUNT_LEVEL=?";
					temp="CM.";
					//sql += " and "+temp+" CIRCLE_ID in ("+circleId+",0) ";  
				}
				
				else if(rID==2 && type.equalsIgnoreCase("parent"))
				{
					sql = " SELECT VAD.ACCOUNT_ID,VAD.ACCOUNT_NAME FROM VR_ACCOUNT_DETAILS VAD,DP_ACCOUNT_CIRCLE_MAP CM " +
							"where VAD.ACCOUNT_ID=CM.ACCOUNT_ID AND ACCOUNT_LEVEL=?";
					temp="CM.";
					sql += " and "+temp+" CIRCLE_ID in ("+circleId+",0) ";  
				}
				else{
				 if(rID>1){
					sql = " SELECT VAD.ACCOUNT_ID,VAD.ACCOUNT_NAME FROM VR_ACCOUNT_DETAILS VAD,DP_ACCOUNT_CIRCLE_MAP CM where VAD.ACCOUNT_ID=CM.ACCOUNT_ID AND ACCOUNT_LEVEL=?";
					temp="CM.";
				}else{
					//sql = " SELECT VAD.ACCOUNT_ID,VAD.ACCOUNT_NAME FROM VR_ACCOUNT_DETAILS VAD,DP_ACCOUNT_CIRCLE_MAP CM where VAD.ACCOUNT_ID=CM.ACCOUNT_ID AND ACCOUNT_LEVEL=?";
					//temp="CM.";
					sql = " SELECT ACCOUNT_ID,ACCOUNT_NAME FROM VR_ACCOUNT_DETAILS where ACCOUNT_LEVEL=?";
				}
							
				//if(circleId!= null && ! "".equals(circleId)){
					sql += " and "+temp+" CIRCLE_ID in ("+circleId+") ";  
				//}
				}
			}
			logger.info("sql query::::::::::::::::::::::::::::::::::::::"+sql);
			
			pstmt = connection.prepareStatement(sql);		
			pstmt.setInt(1, rID);
			
			
			rset = pstmt.executeQuery();
			userList = new ArrayList<UserDto>();
			
			while(rset.next())
			{
				userDto = new UserDto();
				userDto.setUserId(rset.getString("ACCOUNT_ID"));
				userDto.setUserName(rset.getString("ACCOUNT_NAME"));
				//logger.info(userDto.getUserName());
				userList.add(userDto);
			}
		}
		catch (Exception e) 
		{
			e.printStackTrace();
			throw new DAOException(e.getMessage());
		}
		finally
		{
			DBConnectionManager.releaseResources(pstmt, rset);
			//DBConnectionManager.releaseResources(connection);
		}
		logger.info("Size in dao::"+userList.size());
		return userList;
	}
	
	public String checkUserChilds(String userId,String currentRole) throws DAOException
	{
		Connection connection = DBConnectionManager.getDBConnection();
		String haveChilds	= "No";
		PreparedStatement pstmt = null;
		ResultSet rset	= null;
		PreparedStatement pstmt1 = null;
		ResultSet rset1	= null;
		PreparedStatement pstmt2 = null;
		ResultSet rset2	= null;
		int uId=0;
		int count = 0;
		String retFlag="true";
		try
		{
			logger.info("currentRole:::::::::::::::::"+currentRole);
			String sql = "select sum(a.ACC + b.DISTMAP) as COUNT from "
						+" (SELECT COUNT(*) as ACC "
						+" FROM VR_ACCOUNT_DETAILS where PARENT_ACCOUNT=? and account_id!=parent_account) a, "  // clause added by neetika to handle circle admins since they are parent for themselves
						+" (select  COUNT(*) as DISTMAP from DP_DISTRIBUTOR_MAPPING where PARENT_ACCOUNT=?) b with ur "; 
			
			String sqlTsm="select DC_NO from DP_WRONG_SHIP_DETAIL where ASSIGNED_TO =? and TSM_DECISION is null with ur";
			String sqlZsm="select REQ_ID from DP_FNF_HEADER where STATUS='INITIATED' " 
							+" and INITIATED_BY in (select ACCOUNT_ID from VR_ACCOUNT_DETAILS where PARENT_ACCOUNT=? )"; 
			
			if(userId!=null && !userId.equalsIgnoreCase("")){
				uId = Integer.parseInt(userId);
			}
			
			pstmt = connection.prepareStatement(sql);	
				
			
			
			pstmt.setInt(1, uId);
			pstmt.setInt(2, uId);
			
			rset = pstmt.executeQuery();
			
		if(rset.next())
		{
			count= rset.getInt("COUNT");
			if(count > 0 )
			{
				retFlag="You cannot change the role, as current user is having child users";
			}
		}
		else if(currentRole != null &&  currentRole.equalsIgnoreCase("6"))	// for TSM
		{
			pstmt1 = connection.prepareStatement(sqlTsm);
			pstmt1.setInt(1, uId);
			rset1 = pstmt1.executeQuery();
			if(rset1.next())
			{
				retFlag="You cannot change the role, as TSM has wrong shipment action pending";
			}
		}
		else if(currentRole != null &&  currentRole.equalsIgnoreCase("5"))	// for ZSM
		{
			pstmt2=connection.prepareStatement(sqlZsm);
			pstmt2.setInt(1, uId);
			rset2 = pstmt2.executeQuery();
			if(rset2.next())
			{
				retFlag="You cannot change the role, as ZSM has FNF action pending";
			}
		}	
			
		}
		catch (Exception e) 
		{
			e.printStackTrace();
			throw new DAOException(e.getMessage());
		}
		finally
		{
			DBConnectionManager.releaseResources(pstmt, rset);
			DBConnectionManager.releaseResources(pstmt1, rset1);
			DBConnectionManager.releaseResources(pstmt2, rset2);
			//DBConnectionManager.releaseResources(connection);
		}
		return retFlag;
	}
	
	public void updateRoles(NewRoleDto newRoleDto,Connection con) throws DAOException
	{
				
		PreparedStatement pstmt = null;
		PreparedStatement pstmt1 = null;
		PreparedStatement pstmt2 = null;
		PreparedStatement pstmt3 = null;
		PreparedStatement pstmt4 = null;
		PreparedStatement pstmt5 = null;
		PreparedStatement pstmt7 = null;
		PreparedStatement pstmt8 = null;
		PreparedStatement pstmt9 = null;
		ResultSet rset = null;
		PreparedStatement pstmt6 = null;
		ResultSet rset6 = null;
		try
		{
			int currentRoleint = 0;
			int oldParentIdInt=0;
			int parentIdInt=0;
			int currentUserIdInt=0;
			int newRoleInt=0;
			
			
			logger.info("newRoleDto.getCurrentRole()::"+newRoleDto.getCurrentRole());
			logger.info("newRoleDto.getParent()::"+newRoleDto.getParent());
			logger.info("newRoleDto.getCurrentUser()::"+newRoleDto.getCurrentUser());
			logger.info("newRoleDto.getCurrentCircle()::"+newRoleDto.getCurrentCircle());
			logger.info("newRoleDto.getNewCircle()::"+newRoleDto.getNewCircle());
			logger.info("newRoleDto.getNewRole()::"+newRoleDto.getNewRole());
			
			if(!newRoleDto.getCurrentRole().equalsIgnoreCase("")){
				currentRoleint= Integer.parseInt(newRoleDto.getCurrentRole());
			}
			if(!newRoleDto.getParent().equalsIgnoreCase("")){
				parentIdInt= Integer.parseInt(newRoleDto.getParent());
			}
			if(!newRoleDto.getCurrentUser().equalsIgnoreCase("")){
				currentUserIdInt= Integer.parseInt(newRoleDto.getCurrentUser());
			}
			if(!newRoleDto.getNewRole().equalsIgnoreCase("")){
				newRoleInt= Integer.parseInt(newRoleDto.getNewRole());
			}
			
			//added by aman for setting parent account of circle admin as -1
			Timestamp currentTime = new Timestamp(new java.util.Date().getTime());//Neetika
			pstmt3 = con.prepareStatement(GET_PARENT_ACCOUNT_ID);
			pstmt3.setInt(1, currentUserIdInt);
			rset = pstmt3.executeQuery();
			while(rset.next()){
				oldParentIdInt = rset.getInt(1);
			}
			String sql=SQL_INSERT_ACC_DETAIL_HIST_IT_HELP.replace("?2", "timestamp('"+currentTime+"')");
			logger.info(sql);
			pstmt7 = con.prepareStatement(sql);
			//ps.setTimestamp(1, currentTime);
			pstmt7.setLong(1, currentUserIdInt);
			pstmt7.executeUpdate();
			 sql=SQL_INSERT_LOGIN_DETAIL_HIST.replace("?2", "timestamp('"+currentTime+"')");
			 logger.info(sql);
			pstmt8 = con.prepareStatement(sql);
			
			//Neetika timestamp issue
			
			
			
			//ps.setTimestamp(1, currentTime);
			pstmt8.setLong(1, currentUserIdInt);
			pstmt8.executeUpdate();
			
			if(currentRoleint > 2 ) { 
				 sql=SQL_INSERT_ACC_CIRCLE_MAP_HIST.replace("?2", "timestamp('"+currentTime+"')");
				 logger.info(sql);
				pstmt9 = con.prepareStatement(sql);
				pstmt9.setInt(1,currentUserIdInt);
				
				//Neetika timestamp issue
				
				
				pstmt9.executeUpdate();
			}
			
			 sql = "";
			if( newRoleInt == 6 )
			{
				if(currentRoleint == 2)
				{
					sql = "UPDATE VR_ACCOUNT_DETAILS SET ACCOUNT_LEVEL=?,CIRCLE_ID = -1 , PARENT_ACCOUNT=? , TRANSACTION_TYPE = ? ,SR_NUMBER=?,APPROVAL_1=?,APPROVAL_2=? WHERE ACCOUNT_ID=?";
					pstmt = con.prepareStatement(sql);		
					pstmt.setInt(1, (newRoleInt-1));
					pstmt.setInt(2, parentIdInt);
					pstmt.setString(3, newRoleDto.getTransType());
					pstmt.setString(4, newRoleDto.getSR_Number());
					pstmt.setString(5, newRoleDto.getApprover1());
					pstmt.setString(6, newRoleDto.getApprover2());
					pstmt.setInt(7, currentUserIdInt);
				}
				
				else {
					sql = "UPDATE VR_ACCOUNT_DETAILS SET ACCOUNT_LEVEL=?,PARENT_ACCOUNT=? , TRANSACTION_TYPE = ? ,SR_NUMBER=?,APPROVAL_1=?,APPROVAL_2=? WHERE ACCOUNT_ID=?";
					pstmt = con.prepareStatement(sql);		
					pstmt.setInt(1, (newRoleInt-1));
					pstmt.setInt(2, parentIdInt);
					pstmt.setString(3, newRoleDto.getTransType());
					pstmt.setString(4, newRoleDto.getSR_Number());
					pstmt.setString(5, newRoleDto.getApprover1());
					pstmt.setString(6, newRoleDto.getApprover2());
					pstmt.setInt(7, currentUserIdInt);
				}
			}
			else if(newRoleInt == 2)
			{
				
				sql = "UPDATE VR_ACCOUNT_DETAILS SET ACCOUNT_LEVEL=?,CIRCLE_ID = 0 , PARENT_ACCOUNT=? ,SR_NUMBER=?,APPROVAL_1=?,APPROVAL_2=?  WHERE ACCOUNT_ID=?";
				pstmt = con.prepareStatement(sql);		
				pstmt.setInt(1, (newRoleInt-1));
				pstmt.setInt(2, currentUserIdInt);
				pstmt.setString(3, newRoleDto.getSR_Number());
				pstmt.setString(4, newRoleDto.getApprover1());
				pstmt.setString(5, newRoleDto.getApprover2());
				pstmt.setInt(6, currentUserIdInt);
				
			}
			//added by aman to delete circle admin s parent account from screen
			else if(newRoleInt == 3)
			{
				if(currentRoleint == 2)
				{
					sql = "UPDATE VR_ACCOUNT_DETAILS SET ACCOUNT_LEVEL=?,CIRCLE_ID = -1 , PARENT_ACCOUNT= ? ,SR_NUMBER=?,APPROVAL_1=?,APPROVAL_2=? WHERE ACCOUNT_ID=?";
					pstmt = con.prepareStatement(sql);		
					pstmt.setInt(1, (newRoleInt-1));
					pstmt.setInt(2, currentUserIdInt); //again uncommented by neetika
					pstmt.setString(3, newRoleDto.getSR_Number());
					pstmt.setString(4, newRoleDto.getApprover1());
					pstmt.setString(5, newRoleDto.getApprover2());
					pstmt.setInt(6, currentUserIdInt);
				} else
				{
					sql = "UPDATE VR_ACCOUNT_DETAILS SET ACCOUNT_LEVEL=? ,CIRCLE_ID=-1,PARENT_ACCOUNT=? ,SR_NUMBER=?,APPROVAL_1=?,APPROVAL_2=? WHERE ACCOUNT_ID=?";
					pstmt = con.prepareStatement(sql);		
					pstmt.setInt(1, (newRoleInt-1));
					//pstmt.setString(2, newRoleDto.getNewCircle());
					pstmt.setInt(2, currentUserIdInt); //again uncommented by neetika
					pstmt.setString(3, newRoleDto.getSR_Number());
					pstmt.setString(4, newRoleDto.getApprover1());
					pstmt.setString(5, newRoleDto.getApprover2());
					pstmt.setInt(6, currentUserIdInt);
				
				}
			}
			//end of changes by aman
			else
			{
				if(currentRoleint == 2)
				{
					sql = "UPDATE VR_ACCOUNT_DETAILS SET ACCOUNT_LEVEL=?,CIRCLE_ID = -1 , PARENT_ACCOUNT=? ,SR_NUMBER=?,APPROVAL_1=?,APPROVAL_2=? WHERE ACCOUNT_ID=?";
					pstmt = con.prepareStatement(sql);		
					pstmt.setInt(1, (newRoleInt-1));
					pstmt.setInt(2, parentIdInt);
					pstmt.setString(3, newRoleDto.getSR_Number());
					pstmt.setString(4, newRoleDto.getApprover1());
					pstmt.setString(5, newRoleDto.getApprover2());
					pstmt.setInt(6, currentUserIdInt);
				} else
				{
					//added by aman on 13 sept 13
					
					/*String sqlCircleGet="select CIRCLE_ID from DP_ACCOUNT_CIRCLE_MAP where account_id=? with ur";
					String circleList="";
					int i=0;
					pstmt6=con.prepareStatement(sqlCircleGet);
					pstmt6.setInt(1, currentUserIdInt);
					rset6=pstmt6.executeQuery();
					while(rset6.next())
					{
						if(i > 0 ) {
							circleList = circleList + ","+rset6.getString("CIRCLE_ID");
						} else {
							circleList = circleList + rset6.getString("CIRCLE_ID");
						}
						i++;
					}*/
					
					//end of changes by aman
					
					//Added by Sugandha on 5th-August-2013 to fix bug
					sql = "UPDATE VR_ACCOUNT_DETAILS SET ACCOUNT_LEVEL=? ,CIRCLE_ID=-1,PARENT_ACCOUNT=? ,SR_NUMBER=?,APPROVAL_1=?,APPROVAL_2=? WHERE ACCOUNT_ID=?";
					pstmt = con.prepareStatement(sql);		
					pstmt.setInt(1, (newRoleInt-1));
					//pstmt.setString(2, newRoleDto.getNewCircle());
					pstmt.setInt(2, parentIdInt);
					pstmt.setString(3, newRoleDto.getSR_Number());
					pstmt.setString(4, newRoleDto.getApprover1());
					pstmt.setString(5, newRoleDto.getApprover2());
					pstmt.setInt(6, currentUserIdInt);
				}
			}
			pstmt.executeUpdate();
			
			
			
			
			
			
			
			 String sql1 = "UPDATE VR_LOGIN_MASTER SET GROUP_ID=? WHERE LOGIN_ID=?";  
			 pstmt1 = con.prepareStatement(sql1);		
			 pstmt1.setInt(1, newRoleInt);			
			 pstmt1.setInt(2, currentUserIdInt);

			pstmt1.executeUpdate();
			
			
			String sql2 ="";
			if(currentRoleint == 2)
			{
				sql2 = "Insert into DP_ROLE_CHANGE(ACCOUNT_ID,OLD_ROLE,OLD_PARENT," +
		 				"OLD_CIRCLE,NEW_ROLE,NEW_PARENT,NEW_CIRCLE," +
		 				"APPROVER1,APPROVER2,SR_NUMBER,CREATED_BY, CREATED_ON) VALUES(?,?,?,'0' ,?,?,'"+newRoleDto.getNewCircle()+"',?,?,?,?,current timestamp)";  
			}
			else
			{
				String sqlCircleGet="select CIRCLE_ID from DP_ACCOUNT_CIRCLE_MAP where account_id=? with ur";
				String circleList="";
				int i=0;
				pstmt6=con.prepareStatement(sqlCircleGet);
				pstmt6.setInt(1, currentUserIdInt);
				rset6=pstmt6.executeQuery();
				while(rset6.next())
				{
					if(i > 0 ) {
						circleList = circleList + ","+rset6.getString("CIRCLE_ID");
					} else {
						circleList = circleList + rset6.getString("CIRCLE_ID");
					}
					i++;
				}
				if(newRoleInt == 2)
				{
					sql2 = "Insert into DP_ROLE_CHANGE(ACCOUNT_ID,OLD_ROLE,OLD_PARENT," +
	 				"OLD_CIRCLE,NEW_ROLE,NEW_PARENT,NEW_CIRCLE," +
	 				"APPROVER1,APPROVER2,SR_NUMBER,CREATED_BY, CREATED_ON) VALUES(?,?,?,'"+circleList+"' ,?,?,'0',?,?,?,?,current timestamp)";

				}
				else {
					sql2 = "Insert into DP_ROLE_CHANGE(ACCOUNT_ID,OLD_ROLE,OLD_PARENT," +
	 				"OLD_CIRCLE,NEW_ROLE,NEW_PARENT,NEW_CIRCLE," +
	 				"APPROVER1,APPROVER2,SR_NUMBER,CREATED_BY, CREATED_ON) VALUES(?,?,?,'"+circleList+"' ,?,?,'"+newRoleDto.getNewCircle()+"',?,?,?,?,current timestamp)";
				}

			}
			
			 	
			 pstmt2 = con.prepareStatement(sql2);
			 pstmt2.setInt(1, currentUserIdInt);
			 pstmt2.setInt(2, currentRoleint);
			 
			 if(currentRoleint == 2)
				 pstmt2.setInt(3, currentUserIdInt);
			 else
				 pstmt2.setInt(3, oldParentIdInt);
			 
			 pstmt2.setInt(4, newRoleInt);
			 if(newRoleInt == 2)
				 pstmt2.setInt(5, currentUserIdInt);
			 else
				 pstmt2.setInt(5, parentIdInt);
			 pstmt2.setString(6, newRoleDto.getApprover1());
			 pstmt2.setString(7, newRoleDto.getApprover2());
			 pstmt2.setString(8, newRoleDto.getSR_Number());
			 pstmt2.setString(9, newRoleDto.getCreatedBy());
			 
			 pstmt2.executeUpdate();
			 
			 if(currentRoleint > 2)
				{
					String sqlCircle = "delete from DP_ACCOUNT_CIRCLE_MAP where account_id=? ";
					pstmt4=con.prepareStatement(sqlCircle);	
					pstmt4.setInt(1,currentUserIdInt);
					pstmt4.executeUpdate();
				}
				if(newRoleInt > 2)
				{
					String sqlCircleInsert = "INSERT INTO DPDTH.DP_ACCOUNT_CIRCLE_MAP(ACCOUNT_ID, CIRCLE_ID, CREATED_BY, CREATED_ON) " 
										    +" VALUES(?, ?, ?, current_timestamp)";
					
					pstmt5=con.prepareStatement(sqlCircleInsert);
					String arr[] = newRoleDto.getNewCircle().split(",");
					for(int i=0;i<arr.length;i++)
					{
						pstmt5.setInt(1,currentUserIdInt);
						pstmt5.setString(2, arr[i]);
						pstmt5.setString(3, newRoleDto.getCreatedBy());
						pstmt5.executeUpdate();
					}
				}	
				con.commit();
			 
		}
		catch (Exception e) 
		{
			try{
			con.rollback();
			}
			catch(Exception ex)
			{
			}
			e.printStackTrace();
			throw new DAOException(e.getMessage());
		}
		finally
		{
			DBConnectionManager.releaseResources(pstmt, null);
			DBConnectionManager.releaseResources(pstmt1, null);
			DBConnectionManager.releaseResources(pstmt2, null);
			DBConnectionManager.releaseResources(pstmt3, rset);
			DBConnectionManager.releaseResources(pstmt4, null);
			DBConnectionManager.releaseResources(pstmt5, null);
			DBConnectionManager.releaseResources(pstmt6, rset6);
			DBConnectionManager.releaseResources(pstmt7, null);
			DBConnectionManager.releaseResources(pstmt8, null);
			DBConnectionManager.releaseResources(pstmt9, null);
			
		}
		
	}
}
