package com.ibm.dp.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

import com.ibm.dp.common.DBQueries;
import com.ibm.dp.dao.NonSerializedActivationDao;
import com.ibm.dp.dto.CircleDto;
import com.ibm.virtualization.recharge.dao.rdbms.BaseDaoRdbms;
import com.ibm.virtualization.recharge.db.DBConnectionManager;
import com.ibm.virtualization.recharge.exception.DAOException;

public class NonSerializedActivationDaoImpl  extends BaseDaoRdbms implements NonSerializedActivationDao {
	public static Logger logger = Logger.getLogger(TransferPendingSTBDaoImpl.class.getName());
	
	private static NonSerializedActivationDao  nonSerializedActivationDao  = new NonSerializedActivationDaoImpl();
	private NonSerializedActivationDaoImpl(){}
	public static NonSerializedActivationDao getInstance() {
		return nonSerializedActivationDao;
	}

	public static final String SQL_CIRCLE_MST 	= DBQueries.SQL_SELECT_CIRCLES;
	public static final String GET_ACTIVATION_STATUS	= "Select NSER_ACTIVATION_STATUS, SER_ACTIVATION_STATUS, INV_CHANGE_NSER_STATUS, INV_CHANGE_SER_STATUS from VR_CIRCLE_MASTER where CIRCLE_ID=?";
	//public static final String UPDATE_ACTIVATION_STATUS_NSER = "Update VR_CIRCLE_MASTER set NSER_ACTIVATION_STATUS=? where CIRCLE_ID=? with ur ";
	//public static final String UPDATE_ACTIVATION_STATUS_SER = "Update VR_CIRCLE_MASTER set SER_ACTIVATION_STATUS=? where CIRCLE_ID=? with ur ";
	//public static final String UPDATE_ACTIVATION_STATUS_INV_NSER = "Update VR_CIRCLE_MASTER set INV_CHANGE_NSER_STATUS=? where CIRCLE_ID=? with ur ";
//	public static final String UPDATE_ACTIVATION_STATUS_INV_SER = "Update VR_CIRCLE_MASTER set INV_CHANGE_SER_STATUS=? where CIRCLE_ID=? with ur  " ;
	
	public static final String INSERT_ACTIVATION_AUDIT_TRAIL = "INSERT INTO DPDTH.DP_ACTIVATON_STATUS_AUDIT_TRAIL(CIRCLE_ID, CHANGE_TYPE, FLAG, UPDATED_ON) " +
			"VALUES(?, ?, ?, current timestamp)";
   
		
	public List<CircleDto> getAllCircleList() throws DAOException 
	{
		Connection connection = DBConnectionManager.getDBConnection();
		List<CircleDto> circleListDTO	= new ArrayList<CircleDto>();
		PreparedStatement pstmt = null;
		ResultSet rset			= null;
		CircleDto  circleDto = null;
				
		try
		{
			pstmt = connection.prepareStatement(SQL_CIRCLE_MST);
			rset = pstmt.executeQuery();
			circleListDTO = new ArrayList<CircleDto>();
			
			while(rset.next())
			{
				circleDto = new CircleDto();
				circleDto.setCircleId(rset.getString("CIRCLE_ID"));
				circleDto.setCircleName(rset.getString("CIRCLE_NAME"));
				
									
				circleListDTO.add(circleDto);
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
			DBConnectionManager.releaseResources(connection);
		}
		return circleListDTO;
	}
	
	public String getActivationStatus(String circleId) throws DAOException 
	{
		Connection connection = DBConnectionManager.getDBConnection();
		PreparedStatement pstmt = null;
		ResultSet rset			= null;
		String status = "";		
		int cId = Integer.parseInt(circleId);
		try
		{
			pstmt = connection.prepareStatement(GET_ACTIVATION_STATUS);
			pstmt.setInt(1, cId);
			rset = pstmt.executeQuery();
				
			while(rset.next())
			{
				status = rset.getString("NSER_ACTIVATION_STATUS")+","+
				rset.getString("SER_ACTIVATION_STATUS")+","+rset.getString("INV_CHANGE_NSER_STATUS")+"," +rset.getString("INV_CHANGE_SER_STATUS");
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
			DBConnectionManager.releaseResources(connection);
		}
		return status;
	}
	
	/*public int updateStatus(String circleId, String statusNser, String statusSer, String statusInvNser, String statusInvSer,
			String statusNserOld,String statusSerOld, String statusInvNserOld,String statusInvSerOld) throws DAOException 
	{
		Connection connection = DBConnectionManager.getMISReportDBConnection();
		Statement pstmt = null;
		PreparedStatement pstmt1 = null;
		PreparedStatement pstmt2 = null;
		PreparedStatement pstmt3 = null;
		PreparedStatement pstmt4 = null;
		PreparedStatement pstmt5 = null;
		PreparedStatement pstmt6 = null;
		PreparedStatement pstmt7 = null;
	
		//ResultSet rset			= null;
		int result = 0;		
		int result1 = 0;		
		int result2 = 0;		
		int result3 = 0;		
		int cId = Integer.parseInt(circleId);
		int statusIntNser = Integer.parseInt(statusNser);
		int statusIntSer = Integer.parseInt(statusSer);
		int statusIntInvNser = Integer.parseInt(statusInvNser);
		int statusIntInvSer = Integer.parseInt(statusInvSer);
		
		try
		{
			String statusStr = getActivationStatus(circleId);
			String[] statusArr = statusStr.split(",");
			statusNserOld = statusArr[0];
			statusSerOld = statusArr[1];
			statusInvNserOld = statusArr[2];
			statusInvSerOld = statusArr[3];
			if(!statusNser.equalsIgnoreCase(statusNserOld)){
				System.out.println("11111::"+connection.getMetaData().getURL());
				//connection.setAutoCommit(true);
			pstmt = connection.createStatement();
			System.out.println("22222");
			//pstmt.setInt(1, statusIntNser);
			//pstmt.setInt(2, cId);
			result = pstmt.executeUpdate(("Update VR_CIRCLE_MASTER set NSER_ACTIVATION_STATUS="+statusIntNser+" where CIRCLE_ID="+cId+" with ur "));
			System.out.println("33333");
			}
			
			if(result!=0){
				pstmt4 = connection.prepareStatement(INSERT_ACTIVATION_AUDIT_TRAIL);
				pstmt4.setInt(1, cId);
				pstmt4.setString(2, "Non Serialized Activaton");
				pstmt4.setInt(3, statusIntNser);
				pstmt4.executeUpdate();
			}
			
			if(!statusSer.equalsIgnoreCase(statusSerOld)){
			pstmt1 = connection.prepareStatement(UPDATE_ACTIVATION_STATUS_SER);
			pstmt1.setInt(1, statusIntSer);
			pstmt1.setInt(2, cId);
			result1 = pstmt1.executeUpdate();
			}
			
			if(result1!=0){
				pstmt5 = connection.prepareStatement(INSERT_ACTIVATION_AUDIT_TRAIL);
				pstmt5.setInt(1, cId);
				pstmt5.setString(2, "Serialized Activation");
				pstmt5.setInt(3, statusIntSer);
				pstmt5.executeUpdate();
			}
			
			if(!statusInvNser.equalsIgnoreCase(statusInvNserOld)){
			pstmt2 = connection.prepareStatement(UPDATE_ACTIVATION_STATUS_INV_NSER);
			pstmt2.setInt(1, statusIntInvNser);
			pstmt2.setInt(2, cId);
			result2 = pstmt2.executeUpdate();
			}
			
			if(result2!=0){
				pstmt6 = connection.prepareStatement(INSERT_ACTIVATION_AUDIT_TRAIL);
				pstmt6.setInt(1, cId);
				pstmt6.setString(2, "Non Serialized Inventory Change");
				pstmt6.setInt(3, statusIntInvNser);
				pstmt6.executeUpdate();
			}
			
			if(!statusInvSer.equalsIgnoreCase(statusInvSerOld)){
			pstmt3= connection.prepareStatement(UPDATE_ACTIVATION_STATUS_INV_SER);
			pstmt3.setInt(1, statusIntInvSer);
			pstmt3.setInt(2, cId);
			result3 = pstmt3.executeUpdate();
			}
			
			if(result3!=0){
				pstmt7 = connection.prepareStatement(INSERT_ACTIVATION_AUDIT_TRAIL);
				pstmt7.setInt(1, cId);
				pstmt7.setString(2, "Serialized Inventory Change");
				pstmt7.setInt(3, statusIntInvSer);
				pstmt7.executeUpdate();
			}		
			
			connection.commit();
			
		}
		catch (Exception e) 
		{
			e.printStackTrace();
			throw new DAOException(e.getMessage());
		}
		finally
		{
			DBConnectionManager.releaseResources(pstmt, null);
			DBConnectionManager.releaseResources(pstmt1, null);
			DBConnectionManager.releaseResources(pstmt2, null);
			DBConnectionManager.releaseResources(pstmt3, null);
			DBConnectionManager.releaseResources(pstmt4, null);
			DBConnectionManager.releaseResources(pstmt5, null);
			DBConnectionManager.releaseResources(pstmt6, null);
			DBConnectionManager.releaseResources(pstmt7, null);
			DBConnectionManager.releaseResources(connection);
		}
		return result;
	}*/
	
	
	public int insertActivation(String circleId, String changeType, String status) throws DAOException 
	{
		Connection connection = DBConnectionManager.getDBConnection();
		PreparedStatement pstmt = null;
		
	
		int result = 0;		
		
		int cId = Integer.parseInt(circleId);
		int statusInt = Integer.parseInt(status);
			
		try
		{	
		pstmt = connection.prepareStatement(INSERT_ACTIVATION_AUDIT_TRAIL);
		pstmt.setInt(1, cId);
		pstmt.setString(2, changeType);
		pstmt.setInt(3, statusInt);
		pstmt.executeUpdate();
			
			
			connection.commit();
			
		}
		catch (Exception e) 
		{
			e.printStackTrace();
			throw new DAOException(e.getMessage());
		}
		finally
		{
			DBConnectionManager.releaseResources(pstmt, null);
			
			DBConnectionManager.releaseResources(connection);
		}
		return result;
	}
	
	public int updateStatus(String accountid,String circleId, String status, String changeType) throws DAOException 
	{
		Connection connection = DBConnectionManager.getDBConnection();
		Statement pstmt = null;
		
	
		int result = 0;		
		
		int cId = Integer.parseInt(circleId);
		int statusInt = Integer.parseInt(status);
			
		try
		{	System.out.println("11111::"+connection.getMetaData().getURL());
				//connection.setAutoCommit(true);
			pstmt = connection.createStatement();
			System.out.println("22222");
			//pstmt.setInt(1, statusIntNser);
			//pstmt.setInt(2, cId);
			result = pstmt.executeUpdate(("Update VR_CIRCLE_MASTER set "+changeType+ " = " +statusInt+", UPDATED_BY="+accountid+" , UPDATED_DT=current timestamp where CIRCLE_ID="+cId+" with ur "));
			System.out.println("33333");
			
			
			connection.commit();
			
		}
		catch (Exception e) 
		{
			e.printStackTrace();
			throw new DAOException(e.getMessage());
		}
		finally
		{
			DBConnectionManager.releaseResources(pstmt, null);
			
			DBConnectionManager.releaseResources(connection);
		}
		return result;
	}

}
