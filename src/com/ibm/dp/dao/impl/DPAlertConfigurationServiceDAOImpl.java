package com.ibm.dp.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import org.apache.log4j.Logger;
import com.ibm.dp.dao.DPAlertConfigurationServiceDAO;
import com.ibm.dp.dto.AlertConfigurationDTO;

import com.ibm.virtualization.recharge.dao.rdbms.BaseDaoRdbms;
import com.ibm.virtualization.recharge.db.DBConnectionManager;
import com.ibm.virtualization.recharge.exception.DAOException;

public class DPAlertConfigurationServiceDAOImpl extends BaseDaoRdbms implements DPAlertConfigurationServiceDAO{
	
	private Logger logger = Logger.getLogger(AccountDetailReportDaoImpl.class.getName());
	public DPAlertConfigurationServiceDAOImpl(Connection connection) {
		super(connection);
	}
	
	public List<AlertConfigurationDTO> getAlertList() throws DAOException{
		List<AlertConfigurationDTO> listReturn = new ArrayList<AlertConfigurationDTO>();
		PreparedStatement pstmt = null;
		ResultSet rsetReport = null;
		boolean b=false;
		logger.info("get Alert List Dao Impl  for dthadmin Start......!");
		try
		{
			logger.info("********************get alert list when dth admin logs in  ********* ");
			pstmt =  connection.prepareStatement("select a.ALERT_ID , a.ALERT_DESC, b.ENABLE_FLAG from DP_ALERT_MASTER a,DP_ALERT_GROUP_MAPPING b where a.ALERT_ID=b.ALERT_ID and b.GROUP_ID=2 with ur");
			AlertConfigurationDTO AlertConfDTO =null;
			rsetReport= pstmt.executeQuery();
			while(rsetReport.next()){
				AlertConfDTO =new AlertConfigurationDTO();
				AlertConfDTO.setAlertId(rsetReport.getInt("ALERT_ID"));
				AlertConfDTO.setAlertDesc(rsetReport.getString("ALERT_DESC"));
				if(rsetReport.getInt("ENABLE_FLAG") == 1)
					b=true;
				AlertConfDTO.setEnableFlag(b);
				listReturn.add(AlertConfDTO);
			logger.info(AlertConfDTO.getAlertId());
			logger.info(AlertConfDTO.isEnableFlag());
			
			}
			logger.info("get Alert List Dao Impl eNDS......!"+listReturn.size());
			
		}catch (Exception e) 
		{
			e.printStackTrace();
			throw new DAOException(e.getMessage());
		}
		finally
		{
			DBConnectionManager.releaseResources(pstmt ,rsetReport );
		}
		
		return listReturn;
	}
	
	public int updateAlertStatus(int[] idStatuschanged,boolean[] changedStatus)throws DAOException{
		PreparedStatement pstmt = null;
		ResultSet rsetReport = null;
		
		int statusUpdated = 0;
		logger.info("inside updateAlertStatus method......!");
		try
		{
			//get current date and set in the below query for Updated_on field
			pstmt =  connection.prepareStatement("update DP_ALERT_USER_MAPPING set ENABLE_FLAG = ?, UPDATED_ON = current timestamp where ALERT_ID = ? with ur");
			
			for(int i=0; i<idStatuschanged.length; i++)
			{
				// identifying updated alert status 
				if(idStatuschanged[i] >0)
				{
					if(changedStatus[i])
					{
						pstmt.setInt(1,1);
					}
					else
						pstmt.setInt(1,0);
					
					pstmt.setInt(2,idStatuschanged[i]);
					
					pstmt.addBatch();
					
				}
			}

			int[] updateStatus =   pstmt.executeBatch();
			connection.commit();
			statusUpdated = updateStatus[0];
			logger.info(statusUpdated+" exiting updateAlertStatus method......!");
			
		}catch (Exception e) 
		{
			e.printStackTrace();
			throw new DAOException(e.getMessage());
		}
		finally
		{
			DBConnectionManager.releaseResources(pstmt ,rsetReport );
		}
		
		return statusUpdated;
	}
	
	public List<AlertConfigurationDTO> getuserGroupId(int groupId , String loginId)throws DAOException
	{
		List<AlertConfigurationDTO> listReturn = new ArrayList<AlertConfigurationDTO>();
		PreparedStatement pstmt = null;
		ResultSet rsetReport = null;
		boolean b=false;
		logger.info("**************get alert List for OtherUser from dao starts*********************");
		try
		{
			pstmt =  connection.prepareStatement("select a.ALERT_ID,a.ALERT_DESC, (case when c.ENABLE_FLAG  is null then a.ENABLE_FLAG else c.ENABLE_FLAG end)   as ALERT_USER_STATUS,a.ENABLE_FLAG as ALERT_MSTR_STATUS  from DP_ALERT_MASTER a inner join DP_ALERT_GROUP_MAPPING b on a.ALERT_ID=b.ALERT_ID  and b.GROUP_ID=?  left outer join DP_ALERT_USER_MAPPING c on c.ALERT_ID=b.ALERT_ID and c.ACCOUNT_ID=? with ur");
			AlertConfigurationDTO alertDTO =null;
			pstmt.setInt(1, groupId);
			pstmt.setString(2,loginId);
			rsetReport= pstmt.executeQuery();
			while(rsetReport.next()){
				b=false;
				alertDTO =new AlertConfigurationDTO();
				alertDTO.setAlertId(rsetReport.getInt("ALERT_ID"));
				alertDTO.setAlertDesc(rsetReport.getString("ALERT_DESC"));
				if(rsetReport.getInt("ALERT_USER_STATUS") == 1)
					b=true;
				alertDTO.setEnableFlag(b);
				logger.info("--------0----------"+alertDTO.isEnableFlag());
				
				if(alertDTO.isEnableFlag())
				{
					alertDTO.setEnabledChecked("checked");
				}
				else
				{
					alertDTO.setDisabledChecked("checked");
				}
				
				//AlertDTO.setDisabledChecked();
				
				
				
				alertDTO.setAlertMstrStatus(rsetReport.getInt("ALERT_MSTR_STATUS"));
				listReturn.add(alertDTO);
				logger.info(alertDTO.getAlertId());
				logger.info(alertDTO.isEnableFlag());
			}
			logger.info("****************get alert List for OtherUser from dao eNDS......!"+listReturn.size());
			
		}
		catch (Exception e) 
		{
			e.printStackTrace();
			throw new DAOException(e.getMessage());
		}
		finally
		{
			DBConnectionManager.releaseResources(pstmt ,rsetReport );
		}
		
		return listReturn;
	}
	
	public int otherupdateStatus(int[] alertId, int[] status, List<AlertConfigurationDTO> userGroupId,String loginId)throws DAOException{
		PreparedStatement pstmt = null;
		PreparedStatement pstmt1 = null;
		PreparedStatement pstmt2 = null;
		PreparedStatement pstmt3 = null;
		ResultSet rsetReport = null;
		int statusUpdated = 0;
		logger.info("inside updateAlertStatus method......!");
		try
		{
			pstmt=connection.prepareStatement("SELECT * FROM DPDTH.DP_ALERT_USER_MAPPING where ALERT_ID=? and ACCOUNT_ID=? with ur");
			logger.info("array length::"+alertId.length);
			for(int i=0; i<alertId.length; i++)
			{
				logger.info("----->>"+alertId[i]);
				pstmt.setInt(1, alertId[i]);
				pstmt.setString(2, loginId);
				logger.info("----->> Executing Query to get Alert Mapping");
				rsetReport= pstmt.executeQuery();
				if(rsetReport.next())
				{
					 logger.info("----->> copying into history table ");
					 	pstmt3 =  connection.prepareStatement("INSERT INTO DPDTH.DP_ALERT_USER_MAPPING_HIST(ALERT_ID, ACCOUNT_ID, ENABLE_FLAG, UPDATED_ON, HIST_DATE) SELECT ALERT_ID, ACCOUNT_ID, ENABLE_FLAG, UPDATED_ON,current timestamp FROM DPDTH.DP_ALERT_USER_MAPPING where ALERT_ID= ? and ACCOUNT_ID =?  ");
						pstmt3.setInt(1, alertId[i]);
						pstmt3.setString(2, loginId);
						pstmt3.executeUpdate();
					logger.info("----->> Alert  existing");
					pstmt1 =  connection.prepareStatement("update DP_ALERT_USER_MAPPING set ENABLE_FLAG = ?, UPDATED_ON = current timestamp where ALERT_ID = ? and ACCOUNT_ID=? with ur");
					pstmt1.setInt(1,status[i]);
					pstmt1.setInt(2,alertId[i]);
					pstmt1.setString(3, loginId);
					pstmt1.executeUpdate();
					logger.info("----->> record updated");
				}
				else
				{
					pstmt2 =  connection.prepareStatement("insert into DP_ALERT_USER_MAPPING values(?,?,?,current timestamp) with ur");
					pstmt2.setInt(1,alertId[i]);
					pstmt2.setString(2,loginId);
					pstmt2.setInt(3, status[i]);
					pstmt2.executeUpdate();
					logger.info("----->> record inserted");
				}	
			}
			connection.commit();
			logger.info(statusUpdated+" exiting updateAlertStatus method......!");
			
		}catch (Exception e) 
		{
			e.printStackTrace();
			throw new DAOException(e.getMessage());
		}
		finally
		{
			DBConnectionManager.releaseResources(pstmt ,rsetReport );
			DBConnectionManager.releaseResources(pstmt1 ,rsetReport );
			DBConnectionManager.releaseResources(pstmt2 ,rsetReport );
			DBConnectionManager.releaseResources(pstmt3 ,rsetReport );
		}
		
		return statusUpdated =1  ;	
		}
	}
