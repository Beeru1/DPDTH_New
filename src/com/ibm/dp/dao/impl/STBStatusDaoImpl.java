package com.ibm.dp.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;

import com.ibm.dp.common.Constants;
import com.ibm.dp.common.DBQueries;
import com.ibm.dp.dao.STBStatusDao;
import com.ibm.virtualization.recharge.dao.rdbms.BaseDaoRdbms;
import com.ibm.virtualization.recharge.exception.DAOException;

public class STBStatusDaoImpl extends BaseDaoRdbms implements STBStatusDao{
	Logger logger = Logger.getLogger(STBStatusDaoImpl.class.getName());
	public STBStatusDaoImpl(Connection connection) 
	{
		super(connection);
	}
	
	
	public int checkSrNoAvailibility(String strNo)throws DAOException {
		int isAvail = 0;
		
		PreparedStatement pstmt = null;
		ResultSet rset 			= null;
		
		try
		{
			connection.setAutoCommit(false);
			
			pstmt = connection.prepareStatement(DBQueries.SR_NO_AVAILABLE_CHECK);
			
			pstmt.setString(1, strNo);
			
			rset = pstmt.executeQuery();
			int countSrNo =0;
			while(rset.next()){
				countSrNo = rset.getInt(1);
				logger.info("Sr num Count == "+countSrNo);
			}
			if(countSrNo==0){
				isAvail = 1;
			}
			connection.commit();
		}
		catch (Exception e) 
		{
			e.printStackTrace();
			
			logger.error("::::::::::::::::::: Error in depleteOpenStock WebService -------->",e);
			
			isAvail = 1;
			
			try
			{				
				connection.rollback();
			}
			catch (SQLException sqle) 
			{
				sqle.printStackTrace();
				logger.error("::::::::::::::::::: Error in depleteOpenStock WebService -------->",sqle);
			}
		}
		finally
		{
			pstmt = null;
			rset = null;
		}
		return isAvail;
	}
	
	
	public String updateStatus(String strNos,String status)throws DAOException {
		String serviceMsg = null;
		
		PreparedStatement pstmt = null;
		PreparedStatement pstmt1 = null;
		PreparedStatement pstmt2 = null;
		ResultSet rset 			= null;
		try
		{
			connection.setAutoCommit(false);
			Map<Integer, String> mapSTBStatusData = new LinkedHashMap<Integer, String>();
			pstmt = connection.prepareStatement(DBQueries.CONFIG_STATUS_CHECK);
			rset = pstmt.executeQuery();
			while(rset.next()){
				mapSTBStatusData.put(rset.getInt("ID"), rset.getString("VALUE"));
			}
			logger.info("mapSTBStatusData.get(2)+== "+mapSTBStatusData.get(2)+" mapSTBStatusData.get(5)==="+mapSTBStatusData.get(5));
			String[] srNosArray = strNos.split(",");
			String[] srStatusArray =status.split(",");
			for(int count=0;count<srNosArray.length;count++){
				String strSrNo = srNosArray[count];
				String strStatus =srStatusArray[count];
				if(mapSTBStatusData.containsKey(Integer.parseInt(strStatus))){
					strStatus = mapSTBStatusData.get(Integer.parseInt(strStatus));
				}
				logger.info("Sr No== "+strSrNo+"  status == "+strStatus);
				
				pstmt1 = connection.prepareStatement(DBQueries.UPDATE_REV_STOCK_STATUS);
				pstmt1.setString(1, strStatus);
				pstmt1.setString(2, strSrNo);
				pstmt1.executeUpdate();
				
				pstmt2 = connection.prepareStatement(DBQueries.UPDATE_REV_DEL_DETL_STATUS);
				pstmt2.setString(1, strStatus);
				pstmt2.setString(2, strSrNo);
				pstmt2.executeUpdate();
				
			}
			serviceMsg=Constants.STB_STATUS_WS_MSG_SUCCESS;
			connection.commit();
		}
		catch (Exception e) 
		{
			e.printStackTrace();
			
			logger.error("::::::::::::::::::: Error in depleteOpenStock WebService -------->",e);
			
			serviceMsg = Constants.OPEN_STOCK_WS_MSG_OTHERS;
			
			try
			{				
				connection.rollback();
			}
			catch (SQLException sqle) 
			{
				sqle.printStackTrace();
				logger.error("::::::::::::::::::: Error in depleteOpenStock WebService -------->",sqle);
			}
		}
		finally
		{
			pstmt = null;
			pstmt1 = null;
			rset = null;
		}
		return serviceMsg;
	}
	
}
