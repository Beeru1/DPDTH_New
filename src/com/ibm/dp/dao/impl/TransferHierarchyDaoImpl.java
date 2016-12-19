package com.ibm.dp.dao.impl;
import java.sql.BatchUpdateException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;

import com.ibm.dp.common.Constants;
import com.ibm.dp.common.DBQueries;
import com.ibm.dp.dao.TransferHierarchyDao;
import com.ibm.dp.dto.TransferHierarchyDto;
import com.ibm.virtualization.recharge.dao.rdbms.BaseDaoRdbms;
import com.ibm.virtualization.recharge.db.DBConnectionManager;
import com.ibm.virtualization.recharge.exception.DAOException;

public class TransferHierarchyDaoImpl extends BaseDaoRdbms  implements TransferHierarchyDao 
{
	private static Logger logger = Logger.getLogger(TransferHierarchyDaoImpl.class.getName());
	
	public TransferHierarchyDaoImpl(Connection connection) 
	{
		super(connection);
	}
	

	public Map<String, List<TransferHierarchyDto>> getHierarchyDetailsDao(String userName) throws DAOException 
	{
		Map<String, List<TransferHierarchyDto>> mapReturn = new HashMap<String, List<TransferHierarchyDto>>();
		PreparedStatement ps = null;
		ResultSet rs=null;
		try
		{
			String strMessage = Constants.TRANSFER_HIERARCHY_MSG;
			ps = connection.prepareStatement(DBQueries.VALIDATE_USER_OLM_ID);
			
			ps.setString(1, userName);
			rs = ps.executeQuery();
			
			if(rs.next())
			{
				int intGroupID = rs.getInt(2);
				if(intGroupID==3 || intGroupID==4 || intGroupID==5 || intGroupID==6)
				{
					logger.info("asa0");
					List<TransferHierarchyDto> listMessage = new ArrayList<TransferHierarchyDto>();
					TransferHierarchyDto tempDTO = new TransferHierarchyDto();
					
					tempDTO.setStrStatus("SUCCESS");
					listMessage.add(tempDTO);
					tempDTO = null;
					
					mapReturn.put(strMessage, listMessage);
					
					mapReturn.put(Constants.TRANS_HIER_CHILD_DETAILS, getChildUser(userName, intGroupID));
					mapReturn.put(Constants.TRANS_HIER_PARENT_DETAILS, getParentUser(userName, intGroupID));
				}
				else
				{
					logger.info("asa1");
					List<TransferHierarchyDto> listMessage = new ArrayList<TransferHierarchyDto>();
					TransferHierarchyDto tempDTO = new TransferHierarchyDto();
					
					tempDTO.setStrStatus("User OLM ID entered should be one of among Circle Admin, ZBM, ZSM or TSM");
					listMessage.add(tempDTO);
					tempDTO = null;
					
					mapReturn.put(strMessage, listMessage);
					
					//return mapReturn;
				}
			}
			else
			{
				List<TransferHierarchyDto> listMessage = new ArrayList<TransferHierarchyDto>();
				TransferHierarchyDto tempDTO = new TransferHierarchyDto();
				
				tempDTO.setStrStatus("Invalid OLM ID");
				listMessage.add(tempDTO);
				tempDTO = null;
				
				mapReturn.put(strMessage, listMessage);
				
				//return mapReturn;
			}
		}
		catch(Exception ex)
		{
			ex.printStackTrace();
			logger.info("Exception occured Method getChild() in TransferHierarchyImpl  ::  "+ex.getMessage());
			logger.info("Exception occured Method getChild() in TransferHierarchyImpl  ::  "+ex);
			
			throw new DAOException("Exception occured while getChild() in TransferHierarchyImpl  ::  "+ex.getMessage());
		}
		finally
		{
			DBConnectionManager.releaseResources(ps, rs);
		}
		
		return mapReturn;
	}
	
	public List<TransferHierarchyDto> getChildUser(String userName, int intGroupID)throws DAOException
	{
		List<TransferHierarchyDto> listReturn = new ArrayList<TransferHierarchyDto>();
		PreparedStatement ps = null;
		PreparedStatement psCircle = null;
		ResultSet rs = null;
		ResultSet rsCircle = null;
		TransferHierarchyDto thDto=null;
		try 
		{

			//logger.info("asa2");
			psCircle = connection.prepareStatement(DBQueries.USER_CIRCLE_DETAIL);
			if(intGroupID==6)
			{

				//logger.info("asa3");
				ps = connection.prepareStatement(DBQueries.GET_CHILD_USER_DETAILS_TSM);
			}
			else
			{

				//logger.info("asa4");
				ps = connection.prepareStatement(DBQueries.GET_CHILD_USER_DETAILS);
			}
			ps.setString(1, userName);
			rs = ps.executeQuery();
			
			while(rs.next())
			{
				thDto = new TransferHierarchyDto();
				Integer intAccountID = rs.getInt("ACCOUNT_ID");
				thDto.setIntAccountID(intAccountID);
				
				thDto.setStrOLMID(rs.getString("OLM_ID"));
				thDto.setStrAccountName(rs.getString("ACCOUNT_NAME"));
				thDto.setStrRole(rs.getString("GROUP_NAME"));
				thDto.setStrStatus(rs.getString("STATUS"));
				
				String strCircleNames = "";
				String strCircleIDs = "";
				
				if(intGroupID==6)
				{

					logger.info("asa5");
					strCircleNames = rs.getString("CIRCLE_NAME");
					strCircleIDs = rs.getString("CIRCLE_ID");
				}
				else
				{
					//Getting circles of user

					logger.info("asa6");	
					psCircle.setInt(1, intAccountID);
					rsCircle = psCircle.executeQuery();
					while(rsCircle.next())
					{
						strCircleNames=strCircleNames+","+rsCircle.getString("CIRCLE_NAME");
						strCircleIDs=strCircleIDs+","+rsCircle.getInt("CIRCLE_ID");
					}
					
					if(strCircleNames!="")
						strCircleNames=new StringBuffer(strCircleNames).deleteCharAt(0).toString();
					if(strCircleIDs!="")
						strCircleIDs=new StringBuffer(strCircleIDs).deleteCharAt(0).toString();
					logger.info("child strCircleNames :"+strCircleNames);
					logger.info("child's strCircleIDs :"+strCircleIDs);
				}
				
				thDto.setStrCircleName(strCircleNames);
				thDto.setStrCircleIDs(strCircleIDs.trim());
				listReturn.add(thDto);
				
				thDto = null;
			}
		}
		catch(Exception ex)
		{
			ex.printStackTrace();
			logger.info("Exception occured Method getChild() in TransferHierarchyImpl  ::  "+ex.getMessage());
			logger.info("Exception occured Method getChild() in TransferHierarchyImpl  ::  "+ex);
			
			throw new DAOException("Exception occured while getChild() in TransferHierarchyImpl  ::  "+ex.getMessage());
		}
		finally
		{
			//Releasing the database connection
			DBConnectionManager.releaseResources(ps, rs);
			DBConnectionManager.releaseResources(psCircle, rsCircle);
		}
	
		return listReturn;
	}
	public List<TransferHierarchyDto> getParentUser(String userName, int intGroupID)throws DAOException
	{
		List<TransferHierarchyDto> listReturn = new ArrayList<TransferHierarchyDto>();
		PreparedStatement psParent = null;
		ResultSet rsParent=null;
		TransferHierarchyDto thDto=null;
		PreparedStatement psCircleId = null;
		ResultSet rsCircleId=null;
		String circleId="";
		//select CM.CIRCLE_NAME,ac.CIRCLE_ID
		//from DP_ACCOUNT_CIRCLE_MAP AC inner join VR_CIRCLE_MASTER CM on AC.CIRCLE_ID=CM.CIRCLE_ID where AC.ACCOUNT_ID =(select LOGIN_ID from VR_LOGIN_MASTER where LOGIN_NAME='B1100001')

		try
		{

			//logger.info("asa7");
			psCircleId = connection.prepareStatement(DBQueries.GET_CIRCLE_ID);
			int intTransType=0;
			if(intGroupID==6)
			{

				//logger.info("asa8");
				psParent = connection.prepareStatement(DBQueries.GET_TSM_TRANSACTION_TYPE);
				psParent.setString(1, userName);
				rsParent = psParent.executeQuery();
				if(rsParent.next())
				intTransType = rsParent.getInt(1);
				
				DBConnectionManager.releaseResources(psParent, rsParent);
				
				psParent = connection.prepareStatement(DBQueries.GET_PARENT_DETAILS_TSM);
				
				psParent.setInt(1, intGroupID);
				psParent.setString(2, userName);
				psParent.setInt(3, intTransType);
			}
			else
			{

				//logger.info("asa9");
				psParent = connection.prepareStatement(DBQueries.GET_PARENT_DETAILS);
				psParent.setInt(1, intGroupID);
				psParent.setString(2, userName);
			}
			
			rsParent=psParent.executeQuery();
			
			while(rsParent.next())
			{
				circleId="";
				thDto = new TransferHierarchyDto();
				
				String parentLoginName=rsParent.getString(2);
				//logger.info("parentLoginName parents login name :"+parentLoginName);
				psCircleId.setString(1, parentLoginName);
				rsCircleId=psCircleId.executeQuery();
				while(rsCircleId.next())
				{
					circleId = circleId+"," + rsCircleId.getString(2);
				}
				
				if(circleId!="")
					circleId = rsParent.getString(3)+"#"+new StringBuffer(circleId).deleteCharAt(0).toString();
				

				//logger.info("asa10");
				logger.info("Parent AccountName :"+rsParent.getString(1)+"====circleId :"+circleId);
				thDto.setStrParentName(rsParent.getString(1));
				thDto.setStrParentCircleId(circleId.trim());
				listReturn.add(thDto);
				thDto=null;
			}
		}
		catch(Exception ex)
		{
			ex.printStackTrace();
			logger.info("Exception occured Method getParentUser() in TransferHierarchyImpl  ::  "+ex.getMessage());
			logger.info("Exception occured Method getParentUser() in TransferHierarchyImpl  ::  "+ex);
			throw new DAOException("Exception occured while getParentUser() in TransferHierarchyImpl  ::  "+ex.getMessage());
		}
		finally
		{
			//Releasing the database connection
			DBConnectionManager.releaseResources(psParent, rsParent);
		}
		return listReturn;
	}
	
	public String saveTransfferedHierarchy(String childParentMapping, String strOLMID)throws DAOException
	{
		String commaSeparatedArray[] ; 
		String hashSeparatedArray[];
		int childAccountId;
		PreparedStatement psInsertInAccDetails=null;
		PreparedStatement psUpdateAccDetails=null;
		PreparedStatement psInsertInLogindetail=null;
		PreparedStatement psInsertInAccountCircle=null;
		PreparedStatement psGroupID=null;
		ResultSet rsGroupID = null;
		PreparedStatement psWrongShipUpd = null;
		
		int parentId=0;
		String result=null;
		try
		{
			logger.info(" childParentMapping in TransferHierarchyImpl:"+childParentMapping);
			
			logger.info("strOLMID::::"+strOLMID);
			logger.info("asa11");
			psInsertInAccDetails=connection.prepareStatement(DBQueries.SQL_INSERT_ACCOUNT_DETAILS);
			
			psInsertInLogindetail=connection.prepareStatement(DBQueries.SQL_INSERT_INLOGINDETAILS);
			psInsertInAccountCircle=connection.prepareStatement(DBQueries.SQLINSERT_INACCOUNTCIRCLE);
			psWrongShipUpd = connection.prepareStatement(DBQueries.WRONG_SHIP_TSM_UPDATE);
			
			commaSeparatedArray = childParentMapping.split(",");
			int intGroupId = 0;
			int intAccountID = 0;
			int intTransType = 2;
			
			psGroupID = connection.prepareStatement(DBQueries.GET_GROUP_ID);
			psGroupID.setString(1, strOLMID);
			rsGroupID = psGroupID.executeQuery();
			
			
			if(rsGroupID.next())
			{
				logger.info("asa::in if");
				intGroupId = rsGroupID.getInt(1);
				
				intAccountID = rsGroupID.getInt(2);
				intTransType = rsGroupID.getInt(3);
			}
			logger.info("intGroupId::::::"+intGroupId);
			if(intGroupId==6)
			{

				logger.info("asa12");
				psUpdateAccDetails = connection.prepareStatement(DBQueries.SQL_UPDATE_DP_DIST_MAP);
			}
			else
			{

				logger.info("asa13");
				psUpdateAccDetails=connection.prepareStatement(DBQueries.SQL_UPDATE_ACCOUNT_DETAILS);
			}
			
			for(int i=0; i < commaSeparatedArray.length; i++)
			{
				//logger.info("comma separated :"+commaSeparatedArray[i]);
				hashSeparatedArray = commaSeparatedArray[i].split("#");
				childAccountId = Integer.parseInt(hashSeparatedArray[0]);
				parentId=Integer.parseInt(hashSeparatedArray[1]);
				logger.info("Child Account id:"+childAccountId);
				logger.info("parentId :"+parentId);
				logger.info("Parent id :"+parentId);
				
				psInsertInAccDetails.setInt(1, childAccountId);
				psInsertInAccDetails.execute();
				
				psInsertInLogindetail.setInt(1, childAccountId);
				psInsertInLogindetail.execute();
				
				if(intGroupId==6)
				{

					logger.info("asa14");
					psUpdateAccDetails.setInt(1, parentId);
					psUpdateAccDetails.setInt(2, childAccountId);
					psUpdateAccDetails.setInt(3, intTransType);
					psUpdateAccDetails.execute();
					
					psWrongShipUpd.setInt(1, parentId);
					psWrongShipUpd.setInt(2, intAccountID);
					psWrongShipUpd.executeUpdate();
				}
				else
				{

					logger.info("asa15");
					psUpdateAccDetails.setInt(1, parentId);
					psUpdateAccDetails.setInt(2, childAccountId);
					psUpdateAccDetails.execute();
					
					psInsertInAccountCircle.setInt(1, childAccountId);
					psInsertInAccountCircle.execute();
				}
				
				logger.info("===hierarchy transfferd successfully , data saved ======"+i);
				
				//=====  DB script using Batch processing  END=============
			}
			result="success";
			connection.commit();
			logger.info("=== result in try block :"+result);
			
		}
		catch(Exception ex)
		{
			result="notsuccess";
			logger.info("=== result in catch batch updat block :"+result);
			try
			{
				connection.rollback();
			}
			catch(Exception exx)
			{
				logger.info("=Exception while connection commit in Method saveTransfferedHierarchy() in TransferHierarchyImpl");
				ex.printStackTrace();
				logger.info("Exception occured Method saveTransfferedHierarchy() in TransferHierarchyImpl  ::  "+exx.getMessage());
				logger.info("Exception occured Method saveTransfferedHierarchy() in TransferHierarchyImpl  ::  "+exx);
				throw new DAOException("Exception occured while saveTransfferedHierarchy() in TransferHierarchyImpl  :: batch updation failed transaction rollbacked "+exx.getMessage());
			}
			ex.printStackTrace();
			logger.info("Exception occured Method saveTransfferedHierarchy() in TransferHierarchyImpl  ::  "+ex.getMessage());
			logger.info("Exception occured Method saveTransfferedHierarchy() in TransferHierarchyImpl  ::  "+ex);
			throw new DAOException("Exception occured while saveTransfferedHierarchy() in TransferHierarchyImpl  :: batch updation failed transaction rollbacked "+ex.getMessage());
		}
		finally
		{
			DBConnectionManager.releaseResources(psInsertInAccDetails, null);
			DBConnectionManager.releaseResources(psUpdateAccDetails, null);
			DBConnectionManager.releaseResources(psInsertInLogindetail, null);
			DBConnectionManager.releaseResources(psInsertInAccountCircle, null);
			DBConnectionManager.releaseResources(psInsertInAccountCircle, null);
			DBConnectionManager.releaseResources(psWrongShipUpd, null);
			DBConnectionManager.releaseResources(psGroupID, rsGroupID);
		}
		
		logger.info("===returing result as  :"+result);
		return result;
	}
}
