package com.ibm.dp.dao.impl;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.StringTokenizer;

import org.apache.log4j.Logger;
import org.apache.struts.upload.FormFile;

import com.ibm.core.exception.DAOException;
import com.ibm.dp.common.Constants;
import com.ibm.dp.dao.RecoPeriodConfDao;
import com.ibm.dp.dto.RecoPeriodDTO;
import com.ibm.dp.dto.SMSDto;
import com.ibm.dp.exception.DPServiceException;
import com.ibm.dp.sms.SMSUtility;
import com.ibm.utilities.Utility;
import com.ibm.virtualization.recharge.db.DBConnectionManager;

 
public class RecoPeriodConfDaoImpl implements  RecoPeriodConfDao{
	
	private static Logger logger = Logger.getLogger(RecoPeriodConfDaoImpl.class);	
	private static RecoPeriodConfDao recoPeriodConfDao = new RecoPeriodConfDaoImpl();
	private RecoPeriodConfDaoImpl(){}
	public static RecoPeriodConfDao getInstance() {
		return recoPeriodConfDao;
	}
	
	
	public void updateRecoPeriodConf(String action, String recoConfIds, String loginUser )  throws DPServiceException{
		PreparedStatement ps = null;
		PreparedStatement ps1 = null;
		Connection db2conn = null; 
		System.out.println( action +":" + recoConfIds +"::"+ loginUser);
		try {
			logger.info("updateRecoPeriodConf::::");
			String gracePeriodConf = getGracePeriod();
			 db2conn = DBConnectionManager.getDBConnection();
			 
			 if("update".equals(action))
			 {
					String updateRecoIsClosed = "update DP_RECO_PERIOD set IS_RECO_CLOSED = 1, UPDATED_BY =  ? , UPDATED_ON = current timestamp where ID in ("+recoConfIds+") with ur";
					ps = db2conn.prepareStatement(updateRecoIsClosed);
					ps.setInt(1, Integer.parseInt(loginUser));	
						
					logger.info(updateRecoIsClosed);
					ps.executeUpdate();					
					db2conn.commit();
			}
			 if("delete".equals(action))
			 {
				 logger.info("recoConfIds::::"+recoConfIds);		 
					String insertIntoRecoPeriodHist = "INSERT INTO DP_RECO_PERIOD_HIST( ID, FROM_DATE, TO_DATE, CREATED_BY, CREATED_ON, "+
					" UPDATED_BY, UPDATED_ON, GRACE_PERIOD, IS_RECO_START, IS_RECO_CLOSED, DELETED_BY, DELETED_ON) SELECT ID, "+
					" FROM_DATE, TO_DATE, CREATED_BY, CREATED_ON, UPDATED_BY, UPDATED_ON, GRACE_PERIOD, IS_RECO_START, IS_RECO_CLOSED,"+loginUser +
					" , current timestamp  FROM DP_RECO_PERIOD  where ID >= ?  with ur ";
					
					String deleteRecoPeriod = "delete from  DP_RECO_PERIOD  where ID >= ? with ur ";
    
					logger.info("recoConfIds:::"+recoConfIds);
					ps = db2conn.prepareStatement(insertIntoRecoPeriodHist);
					ps.setInt(1, Integer.parseInt(recoConfIds));
					
					ps1= db2conn.prepareStatement(deleteRecoPeriod);
					ps1.setInt(1, Integer.parseInt(recoConfIds));
					
					logger.info("recoConfIds::::"+recoConfIds);
					logger.info(insertIntoRecoPeriodHist);
					logger.info(deleteRecoPeriod);
					db2conn.setAutoCommit(false);
					ps.executeUpdate();
					ps1.executeUpdate();
					db2conn.commit();
			}
			 
		} catch (Exception e) {
			try {
				db2conn.rollback();
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
			e.printStackTrace();
			logger.error("Exception occured while getting Grace Period. Exception message: "
					+ e.getMessage());
			throw new DAOException("Exception: " + e.getMessage());
		} finally {
			
		try {
			db2conn.setAutoCommit(true);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		DBConnectionManager.releaseResources(ps ,null);
		DBConnectionManager.releaseResources(ps1 ,null);
		DBConnectionManager.releaseResources(db2conn);
		}
		
	}
	public List<RecoPeriodDTO> getRecoHistory() throws DAOException{
		PreparedStatement ps = null;
		Connection db2conn = null; 
		ResultSet rs = null; 
		List<RecoPeriodDTO> recoList= new ArrayList<RecoPeriodDTO>();
		
		try {
			logger.info("getRecoHistory::::");
			db2conn = DBConnectionManager.getDBConnection();
						
			//String sql = "SELECT  * FROM DP_RECO_PERIOD ORDER BY ID DESC";
			/*String sql = " SELECT  ID, FROM_DATE , TO_DATE , CREATED_BY, CREATED_ON, UPDATED_BY, UPDATED_ON, GRACE_PERIOD, IS_RECO_START, IS_RECO_CLOSED , "+
			" CASE  WHEN IS_RECO_CLOSED =1 THEN '<font color=#CC0000><b>Completed</b></font>' WHEN(TO_DATE + GRACE_PERIOD days <  current date ) "+
			" THEN '<input type=checkbox name=closeRc value='|| CAST(ID as CHAR(3)) ||' /> Complete' "+
			" WHEN  FROM_DATE < current date and TO_DATE + GRACE_PERIOD days >=  current date "+
			" THEN '<font color=green><b>In Progress</b></font>' ELSE '<input type=radio name=deleteRc value='|| CAST(ID AS CHAR(3)) ||' /> Delete' END Actions "+    
			" FROM DP_RECO_PERIOD ORDER BY TO_DATE DESC with ur"; */
			//Added By sugandha to make change 
			
			/*String sql = "SELECT  ID, FROM_DATE , TO_DATE , CREATED_BY, CREATED_ON, UPDATED_BY, UPDATED_ON, GRACE_PERIOD, IS_RECO_START, IS_RECO_CLOSED , "+
						" CASE  WHEN IS_RECO_CLOSED =1 THEN '&lt;font color=#CC0000&gt;&lt;b&gt;Completed&lt;/b&gt;&lt;/font&gt;' "+
						" WHEN(TO_DATE + GRACE_PERIOD days <  current date ) "+
						" THEN '<input type=checkbox name=closeRc value='|| CAST(ID as CHAR(3)) ||' /> Complete' "+
						" WHEN  FROM_DATE < current date and TO_DATE + GRACE_PERIOD days >=  current date "+
						" THEN '&lt;font color=#CC0000&gt;&lt;b&gt;In Progress&lt;/b&gt;&lt;/font&gt;' ELSE '<input type=radio name=deleteRc value='|| CAST(ID AS CHAR(3)) ||' /> Delete' END Actions ,"+
						"(CASE when current_date > TO_DATE and IS_RECO_CLOSED != 1 then 'LINK' else '' end  ) as LINK  FROM DP_RECO_PERIOD ORDER BY TO_DATE DESC with ur";
			*/
						
			String sql= "SELECT  ID, FROM_DATE , TO_DATE , CREATED_BY, CREATED_ON, UPDATED_BY, UPDATED_ON, GRACE_PERIOD, IS_RECO_START, IS_RECO_CLOSED , " 
						+" CASE  WHEN IS_RECO_CLOSED =1 THEN 'COMPLETED'  "
						+" WHEN(TO_DATE + GRACE_PERIOD days <  current date ) " 
						+" THEN 'CHECKBOX'  "
						+" WHEN  FROM_DATE < current date and TO_DATE + GRACE_PERIOD days >=  current date " 
						+" THEN 'PROGRESS' ELSE 'RADIO' END Actions , "
						+" (CASE when current_date > TO_DATE and IS_RECO_CLOSED != 1 then 'LINK' else '' end  ) as LINK  FROM DP_RECO_PERIOD " 
						+" ORDER BY TO_DATE DESC with ur";
		
			ps = db2conn.prepareStatement(sql);
			logger.info(sql);
			
			rs = ps.executeQuery();
			String link="";
			
			while (rs.next()) {
				RecoPeriodDTO recDTO = new RecoPeriodDTO();
				String fromDate = Utility.converDateToString(rs.getDate("FROM_DATE"), Constants.DT_FMT_RECO);
				String toDate = Utility.converDateToString(rs.getDate("TO_DATE"), Constants.DT_FMT_RECO);
				link =  rs.getString("LINK");
				if(link != null && link.equalsIgnoreCase("LINK"))
				{
					recDTO.setLinkFlag("true");
				}
				else
				{
					recDTO.setLinkFlag("false");
				}
				
				Integer recoID = rs.getInt("ID");
				String recoPeriodAction = rs.getString("Actions");
				recDTO.setFromDate(fromDate);
				recDTO.setToDate(toDate);
				recDTO.setRecoPeriodId(recoID.toString());
				
				recDTO.setRecoPeriodAction(recoPeriodAction); 
				recoList.add(recDTO);
			}
			
		} catch (Exception e) {
				e.printStackTrace();
				throw new DAOException("Exception: " + e.getMessage());
				}
		finally {
			DBConnectionManager.releaseResources(ps ,rs);			
			DBConnectionManager.releaseResources(db2conn);
			}
		return recoList;
		
	}
	
	public String getGracePeriod() throws DAOException {
		PreparedStatement ps = null;
		Connection db2conn = null; 
		ResultSet rs = null; 		
		String gracePeriod ="";
		
		try {
			logger.info("getRecoHistory:::::");
			db2conn = DBConnectionManager.getDBConnection();
						
			String sql = "SELECT CONFIG_VALUE FROM DP_CONFIGURATION_MASTER " +
					"WHERE CONFIG_NAME='"+Constants.RECO_GRACE_PERIOD+ "' with ur";
	
			ps = db2conn.prepareStatement(sql);
			logger.info(sql);
			
			rs = ps.executeQuery();
			
			
			while (rs.next()) {
				
				 gracePeriod = rs.getString("CONFIG_VALUE");
				
			}
			
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("Exception occured while getting Grace Period. Exception message: "
					+ e.getMessage());
			throw new DAOException("Exception: " + e.getMessage());
		} finally {
			DBConnectionManager.releaseResources(ps ,rs);			
			DBConnectionManager.releaseResources(db2conn);
		}
		return gracePeriod;
		
	}
	
	public int getOldestRecoId(String recoPeriodIDs) throws DAOException {
		PreparedStatement ps = null;
		Connection db2conn = null; 
		ResultSet rs = null; 		
		int oldestRecoId = -1;
		
		try {
			
			logger.info("getOldestRecoId:::::");
			db2conn = DBConnectionManager.getDBConnection();
						
			String sql = "SELECT ID FROM DP_RECO_PERIOD where  IS_RECO_CLOSED = 0 and ID < "+
			" (SELECT min(ID)  FROM DP_RECO_PERIOD where ID in ("+recoPeriodIDs+")) with ur";
	
			ps = db2conn.prepareStatement(sql);
			logger.info(sql);
			
			rs = ps.executeQuery();
			
			if (rs.next()) {
				oldestRecoId = rs.getInt("ID");
			}
			
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("Exception occured while getting Grace Period. Exception message: "
					+ e.getMessage());
			throw new DAOException("Exception: " + e.getMessage());
		} finally {
			DBConnectionManager.releaseResources(ps ,rs);			
			DBConnectionManager.releaseResources(db2conn);
		}
		return oldestRecoId;
		
	}
	
	public String updatePartnerDataDB(List myList,String recoId,String gracePeriod) throws DAOException {
		
		
		logger.info("updatePartnerDataDB:::::");
		Connection  connection = null;
		RecoPeriodDTO recoPeriodDTO = null;
		PreparedStatement ps=null;
		PreparedStatement ps1=null;
		PreparedStatement ps2=null;
		PreparedStatement ps3=null;
		PreparedStatement ps4=null;
		PreparedStatement ps5=null;
		PreparedStatement ps6=null;
		PreparedStatement ps7=null;
		PreparedStatement ps8=null;
		PreparedStatement ps9=null;
		PreparedStatement ps10=null;
		PreparedStatement ps11=null;
		
		String olmId="";
		String retFlag="true";
		
		String sql="update DP_RECO_DIST_DETAILS set GRACE_PERIOD = (GRACE_PERIOD +?) "
					+" , RECO_STATUS = 'INITIATE' where RECO_ID=? and CREATED_BY !=0 and dist_id " 
					+" in(select LOGIN_ID from VR_LOGIN_MASTER where LOGIN_NAME=?)";
		
		String sqlInsert="INSERT INTO DPDTH.DP_RECO_DIST_DETAILS_HIST(RECO_ID, DIST_ID, PRODUCT_ID, PRODUCT_TYPE, CREATED_BY, CREATED_ON, SER_OPEN_STOCK, NSER_OPEN_STOCK, DEFECTIVE_OPEN_STOCK, UPGRADE_OPEN_STOCK, CHURN_OPEN_STOCK, PENDING_PO_OPENING, PENDING_DC_OPENING, RECEIVED_WH, RECEIVED_INTER_SSD_OK, RECEIVED_INTER_SSD_DEF, RECEIVED_HIERARCHY_TRANS, INVENTORY_CHANGE, RECEIVED_UPGRADE, RECEIVED_CHURN, RETURNED_FRESH, RETURNED_INTER_SSD_OK, RETURNED_INTER_SSD_DEF, RETURNED_HIERARCHY_TRANS, RETURNED_DOA_BI, RETURNED_DOA_AI, RETURNED_SWAP, RETURNED_C2S, RETURNED_CHURN, FLUSH_OUT_OK, FLUSH_OUT_DEFECTIVE, SER_ACTIVATION, NSER_ACTIVATION, RECO_QTY, SER_CLOSING_STOCK, NSER_CLOSING_STOCK, DEF_CLOSING_STOCK, UPGRADE_CLOSING_STOCK, CHURN_CLOSING_STOCK, PENDING_PO_CLOSING, PENDING_DC_CLOSING, UPDATED_ON, CIRCLE_ID, RECO_STATUS,  GRACE_PERIOD, HIST_DATE)  " 
					      	+" SELECT RECO_ID, DIST_ID, PRODUCT_ID, PRODUCT_TYPE, CREATED_BY, CREATED_ON, SER_OPEN_STOCK, NSER_OPEN_STOCK, DEFECTIVE_OPEN_STOCK, UPGRADE_OPEN_STOCK, CHURN_OPEN_STOCK, PENDING_PO_OPENING, PENDING_DC_OPENING, RECEIVED_WH, RECEIVED_INTER_SSD_OK, RECEIVED_INTER_SSD_DEF, RECEIVED_HIERARCHY_TRANS, INVENTORY_CHANGE, RECEIVED_UPGRADE, RECEIVED_CHURN, RETURNED_FRESH, RETURNED_INTER_SSD_OK, RETURNED_INTER_SSD_DEF, RETURNED_HIERARCHY_TRANS, RETURNED_DOA_BI, RETURNED_DOA_AI, RETURNED_SWAP, RETURNED_C2S, RETURNED_CHURN, FLUSH_OUT_OK, FLUSH_OUT_DEFECTIVE, SER_ACTIVATION, NSER_ACTIVATION, RECO_QTY, SER_CLOSING_STOCK, NSER_CLOSING_STOCK, DEF_CLOSING_STOCK, UPGRADE_CLOSING_STOCK, CHURN_CLOSING_STOCK, PENDING_PO_CLOSING, PENDING_DC_CLOSING, UPDATED_ON, CIRCLE_ID, RECO_STATUS,GRACE_PERIOD,current_timestamp  "
					      	+" FROM DPDTH.DP_RECO_DIST_DETAILS where RECO_ID=? and CREATED_BY !=0 and dist_id  "
					    	+" in(select LOGIN_ID from VR_LOGIN_MASTER where LOGIN_NAME=?)";
		
		String sqlCert="INSERT INTO DPDTH.DP_RECO_CERTIFICATE_DETAIL_HIST(CERTIFICATE_ID, PRODUCT_ID, QUANTITY, RECO_HEADER_ID, HIST_DATE) " 
						+" SELECT CERTIFICATE_ID, PRODUCT_ID, QUANTITY, RECO_HEADER_ID ,Current_timestamp "
						+" FROM DPDTH.DP_RECO_CERTIFICATE_DETAIL where  "
						+" CERTIFICATE_ID in(select CERTIFICATE_ID from DP_RECO_CERTIFICATE_HDR where RECO_ID=? and DIST_ID in(select LOGIN_ID from VR_LOGIN_MASTER where LOGIN_NAME=?)  )";
		
		String sqlCERTDELETE="delete FROM DPDTH.DP_RECO_CERTIFICATE_DETAIL where " 
							+" CERTIFICATE_ID in(select CERTIFICATE_ID from DP_RECO_CERTIFICATE_HDR where RECO_ID=? and DIST_ID in(select LOGIN_ID from VR_LOGIN_MASTER where LOGIN_NAME=?) )";
		
		//aman
		String moveToHist_RECO_SERIALIZED_STOCK="insert into RECO_SERIALIZED_STOCK_HIST (DISTRIBUTOR_ID, CIRCLE_ID, PRODUCT_ID, SERIAL_NO, PR_NO, PO_NO, DISTRIBUTOR_PURCHASE_DATE, FSE_ID, FSE_PURCHASE_DATE, RETAILER_ID, RETAILER_PURCHASE_DATE, STATUS_DESCRIPTION, DIST_FLAG, DIST_REMARKS, FLUSHOUT_FLAG, DEBIT_FLAG, DTH_REMARKS, CREATED_ON, UPDATED_ON, RUNNING_SCHEDULER_DATE,RECO_ID) select DISTRIBUTOR_ID, CIRCLE_ID, PRODUCT_ID, SERIAL_NO, PR_NO, PO_NO, DISTRIBUTOR_PURCHASE_DATE, FSE_ID, FSE_PURCHASE_DATE, RETAILER_ID, RETAILER_PURCHASE_DATE, STATUS_DESCRIPTION, DIST_FLAG, DIST_REMARKS, FLUSHOUT_FLAG, DEBIT_FLAG, DTH_REMARKS, CREATED_ON,current timestamp, RUNNING_SCHEDULER_DATE,RECO_ID from RECO_SERIALIZED_STOCK where RECO_ID=? and  DISTRIBUTOR_ID in(select LOGIN_ID from VR_LOGIN_MASTER where LOGIN_NAME=?) with ur";		
		
		String moveToHist_RECO_UPGRADE_STOCK="insert into RECO_UPGRADE_STOCK_HIST (FROM_DIST_ID,TO_DIST_ID,CIRCLE_ID,COLLECTION_ID,DEFECTIVE_SR_NO,DEFECTIVE_PRODUCT_ID,NEW_SR_NO,NEW_PRODUCT_ID,INV_CHANGE_DATE,COLLECTED_ON, DEFECT_ID, AGEING,DIST_FLAG,DIST_REMARKS,FLUSHOUT_FLAG,DEBIT_FLAG,DTH_REMARKS,CREATED_ON,UPDATED_ON,RUNNING_SCHEDULER_DATE,RECO_ID) select FROM_DIST_ID,TO_DIST_ID,CIRCLE_ID,COLLECTION_ID,DEFECTIVE_SR_NO,DEFECTIVE_PRODUCT_ID,NEW_SR_NO,NEW_PRODUCT_ID,INV_CHANGE_DATE,COLLECTED_ON, DEFECT_ID, AGEING,DIST_FLAG,DIST_REMARKS,FLUSHOUT_FLAG,DEBIT_FLAG,DTH_REMARKS,CREATED_ON,current timestamp,RUNNING_SCHEDULER_DATE,RECO_ID from RECO_UPGRADE_STOCK where RECO_ID=? and  TO_DIST_ID in(select LOGIN_ID from VR_LOGIN_MASTER where LOGIN_NAME=?) with ur";		
		
		String moveToHist_RECO_DEFECTIVE_STOCK="insert into RECO_DEFECTIVE_STOCK_HIST (FROM_DIST_ID, CIRCLE_ID, COLLECTION_ID, DEFECTIVE_SR_NO, DEFECTIVE_PRODUCT_ID, NEW_SR_NO,NEW_PRODUCT_ID, INV_CHANGE_DATE, COLLECTED_ON, DEFECT_ID,AGEING, DIST_FLAG,DIST_REMARKS, FLUSHOUT_FLAG, DEBIT_FLAG, DTH_REMARKS, CREATED_ON, UPDATED_ON, RUNNING_SCHEDULER_DATE, RECO_ID) select FROM_DIST_ID, CIRCLE_ID, COLLECTION_ID, DEFECTIVE_SR_NO, DEFECTIVE_PRODUCT_ID, NEW_SR_NO,NEW_PRODUCT_ID, INV_CHANGE_DATE, COLLECTED_ON, DEFECT_ID,AGEING, DIST_FLAG,DIST_REMARKS, FLUSHOUT_FLAG, DEBIT_FLAG, DTH_REMARKS, CREATED_ON, current timestamp, RUNNING_SCHEDULER_DATE, RECO_ID from RECO_DEFECTIVE_STOCK where RECO_ID=? and FROM_DIST_ID in(select LOGIN_ID from VR_LOGIN_MASTER where LOGIN_NAME=?) with ur ";
		
		String moveToHist_RECO_CHURN_STOCK="insert into RECO_CHURN_STOCK_HIST (FROM_DIST_ID,CIRCLE_ID,COLLECTION_ID,DEFECTIVE_SR_NO,DEFECTIVE_PRODUCT_ID,COLLECTED_ON,AGEING,DIST_FLAG,DIST_REMARKS,FLUSHOUT_FLAG,DEBIT_FLAG, DTH_REMARKS,CREATED_ON,UPDATED_ON,RUNNING_SCHEDULER_DATE,RECO_ID) select FROM_DIST_ID,CIRCLE_ID,COLLECTION_ID,DEFECTIVE_SR_NO,DEFECTIVE_PRODUCT_ID,COLLECTED_ON,AGEING,DIST_FLAG,DIST_REMARKS,FLUSHOUT_FLAG,DEBIT_FLAG, DTH_REMARKS,CREATED_ON,current timestamp,RUNNING_SCHEDULER_DATE,RECO_ID from RECO_CHURN_STOCK where RECO_ID=? and  FROM_DIST_ID in(select LOGIN_ID from VR_LOGIN_MASTER where LOGIN_NAME=?) with ur";
		
		String update_RECO_SERIALIZED_STOCK="UPDATE RECO_SERIALIZED_STOCK SET DIST_FLAG=null, DIST_REMARKS=null, UPDATED_ON=current timestamp where RECO_ID=? and DISTRIBUTOR_ID in(select LOGIN_ID from VR_LOGIN_MASTER where LOGIN_NAME=?) with ur";
		
		String update_RECO_UPGRADE_STOCK="UPDATE RECO_UPGRADE_STOCK SET DIST_FLAG=null, DIST_REMARKS=null, UPDATED_ON=current timestamp where RECO_ID=? and TO_DIST_ID in(select LOGIN_ID from VR_LOGIN_MASTER where LOGIN_NAME=?) with ur";
		
		String update_RECO_DEFECTIVE_STOCK="UPDATE RECO_DEFECTIVE_STOCK SET DIST_FLAG=null, DIST_REMARKS=null, UPDATED_ON=current timestamp where RECO_ID=? and FROM_DIST_ID in(select LOGIN_ID from VR_LOGIN_MASTER where LOGIN_NAME=?) with ur";
		
		String update_RECO_CHURN_STOCK="UPDATE RECO_CHURN_STOCK SET DIST_FLAG=null, DIST_REMARKS=null, UPDATED_ON=current timestamp where RECO_ID=? and FROM_DIST_ID in(select LOGIN_ID from VR_LOGIN_MASTER where LOGIN_NAME=?) with ur";		
		
		
		//end aman
		
		try
		{
			connection = DBConnectionManager.getDBConnection();
			connection.setAutoCommit(false);
			ps = connection.prepareStatement(sql);
			ps1 = connection.prepareStatement(sqlInsert);
			ps2 = connection.prepareStatement(sqlCert);
			ps3 = connection.prepareStatement(sqlCERTDELETE);
			
			ps4 = connection.prepareStatement(moveToHist_RECO_SERIALIZED_STOCK);
			ps5 = connection.prepareStatement(moveToHist_RECO_UPGRADE_STOCK);
			ps6 = connection.prepareStatement(moveToHist_RECO_DEFECTIVE_STOCK);
			ps7 = connection.prepareStatement(moveToHist_RECO_CHURN_STOCK);
			ps8 = connection.prepareStatement(update_RECO_SERIALIZED_STOCK);
			ps9 = connection.prepareStatement(update_RECO_UPGRADE_STOCK);
			ps10 = connection.prepareStatement(update_RECO_DEFECTIVE_STOCK);
			ps11 = connection.prepareStatement(update_RECO_CHURN_STOCK);
			
			
			
			Iterator itt=myList.iterator();
			while(itt.hasNext())
			{
				recoPeriodDTO = (RecoPeriodDTO) itt.next();
				olmId=recoPeriodDTO.getOlmId();
				
				
				ps1.setString(1, recoId);
				ps1.setString(2, olmId);
				ps1.executeUpdate();
				
				ps.setInt(1, Integer.parseInt(gracePeriod));
				ps.setString(2, recoId);
				ps.setString(3, olmId);
				ps.executeUpdate();
				
				ps2.setString(1, recoId);
				ps2.setString(2, olmId);
				ps2.executeUpdate();
				
				ps3.setString(1, recoId);
				ps3.setString(2, olmId);
				ps3.executeUpdate();
				
				//
				ps4.setString(1, recoId);
				ps4.setString(2, olmId);
				ps4.executeUpdate();
				
				ps5.setString(1, recoId);
				ps5.setString(2, olmId);
				ps5.executeUpdate();


				ps6.setString(1, recoId);
				ps6.setString(2, olmId);
				ps6.executeUpdate();
				
				ps7.setString(1, recoId);
				ps7.setString(2, olmId);
				ps7.executeUpdate();
				
				ps8.setString(1, recoId);
				ps8.setString(2, olmId);
				ps8.executeUpdate();
				
				ps9.setString(1, recoId);
				ps9.setString(2, olmId);
				ps9.executeUpdate();
				
				ps10.setString(1, recoId);
				ps10.setString(2, olmId);
				ps10.executeUpdate();
				
				ps11.setString(1, recoId);
				ps11.setString(2, olmId);
				ps11.executeUpdate();
				//
				
				
			}
			
			
			
			connection.commit();
		}
		catch(Exception ex)
		{
			retFlag="false";
			logger.info(ex);
			try
			{
				connection.rollback();
			}
			catch(Exception e)
			{
				logger.info(e);
			}
		}
		finally
		{
			DBConnectionManager.releaseResources(ps ,null);
			DBConnectionManager.releaseResources(ps1 ,null);
			DBConnectionManager.releaseResources(ps2 ,null);
			DBConnectionManager.releaseResources(ps3 ,null);
			DBConnectionManager.releaseResources(ps4 ,null);
			DBConnectionManager.releaseResources(ps5 ,null);
			DBConnectionManager.releaseResources(ps6 ,null);
			DBConnectionManager.releaseResources(ps7 ,null);
			DBConnectionManager.releaseResources(ps8 ,null);
			DBConnectionManager.releaseResources(ps9 ,null);
			DBConnectionManager.releaseResources(ps10 ,null);
			DBConnectionManager.releaseResources(ps11 ,null);
			
			DBConnectionManager.releaseResources(connection);
		}
		
		return retFlag;
	}
	
	public String createSMSAlerts(List distList,String recoId,String gracePeriod) throws DAOException {
		
		Connection  connection = null;
		RecoPeriodDTO recoPeriodDTO = null;
		PreparedStatement ps=null;
		PreparedStatement ps1=null;
		PreparedStatement ps2=null;
		PreparedStatement ps3=null;
		String olmId="";
		String retFlag="true";
		String prodName = "";
		String dist_id="";
		SMSDto sMSDto = null;
		String message=null;
		String message1=null;
		for(int ii=0; ii< distList.size(); ii++ )
		{
			
			try {
				
				connection = DBConnectionManager.getDBConnection();
				logger.info("recoId = "+recoId+"gracePeriod "+gracePeriod);
				String distolm=((RecoPeriodDTO)(distList.get(ii))).getOlmId();
				logger.info("distolm"+distolm);
				
				dist_id=Integer.toString(SMSUtility.getDistId(distolm.trim()));
				sMSDto = SMSUtility.getUserDetailsasPerTransaction(dist_id, connection,com.ibm.virtualization.recharge.common.Constants.DISTRIBUTOR_TYPE_DEPOSIT);
				sMSDto.setRecoPeriod(recoId);
				sMSDto.setGracePeriod(gracePeriod);	
				
				// MSG FOR Distributor
				sMSDto.setAlertType(com.ibm.virtualization.recharge.common.Constants.CONFIRM_ID_RECO_GRACE_DIST);
				
				message1=SMSUtility.flagsCheck(com.ibm.virtualization.recharge.common.Constants.CONFIRM_ID_RECO_GRACE_DIST, sMSDto, connection,dist_id);
				
				sMSDto.setMessage(message1);
				if(message1!=null && !message1.equalsIgnoreCase(""))
				{
					message=SMSUtility.createMessageContentOnly(com.ibm.virtualization.recharge.common.Constants.CONFIRM_ID_RECO_GRACE_DIST, sMSDto);
				}
				
				if(message != null && !message.equalsIgnoreCase(""))
				{
					SMSUtility.saveSMSInDB(connection, new String[] {sMSDto.getLapuMobile1()}, message,com.ibm.virtualization.recharge.common.Constants.CONFIRM_ID_RECO_GRACE_DIST);
				}
					// MSG FOR TSM
				message1="";
				message= "";
				sMSDto.setAlertType(com.ibm.virtualization.recharge.common.Constants.CONFIRM_ID_RECO_GRACE_TSM);				
				message1=SMSUtility.flagsCheck(com.ibm.virtualization.recharge.common.Constants.CONFIRM_ID_RECO_GRACE_TSM, sMSDto, connection,sMSDto.getTsmID());
				sMSDto.setMessage(message1);

				if(message1!=null && !message1.equalsIgnoreCase(""))
				{
				message=SMSUtility.createMessageContentOnly(com.ibm.virtualization.recharge.common.Constants.CONFIRM_ID_RECO_GRACE_TSM, sMSDto);
				}
				if(message != null && !message.equalsIgnoreCase(""))
				{
					SMSUtility.saveSMSInDB(connection, new String[] {sMSDto.getParentMobileNumber()}, message,com.ibm.virtualization.recharge.common.Constants.CONFIRM_ID_RECO_GRACE_TSM);
				}
				  // MSG FOR ZSM
				message1="";
				message = "";
				sMSDto.setAlertType(com.ibm.virtualization.recharge.common.Constants.CONFIRM_ID_RECO_GRACE_ZSM);				
				message1=SMSUtility.flagsCheck(com.ibm.virtualization.recharge.common.Constants.CONFIRM_ID_RECO_GRACE_ZSM, sMSDto, connection,sMSDto.getZsmID());
				sMSDto.setMessage(message1);

				if(message1!=null && !message1.equalsIgnoreCase(""))
				{
				message=SMSUtility.createMessageContentOnly(com.ibm.virtualization.recharge.common.Constants.CONFIRM_ID_RECO_GRACE_ZSM, sMSDto);
				}
				if(message != null && !message.equalsIgnoreCase(""))
				{
					SMSUtility.saveSMSInDB(connection, new String[] {sMSDto.getParentMobileNumber2()}, message,com.ibm.virtualization.recharge.common.Constants.CONFIRM_ID_RECO_GRACE_ZSM);
				}
				
			}catch (Exception de) {
				de.printStackTrace();
			}	
		
		
		// ********************************ALERT MANAGEMENT********************************
/*for dist
     Rico <reco_period> has become editable for next <grace_period> days
		For TSM:
			Rico <reco_period> for <dist_id> has become editable for next <grace_period> days
			For ZSM:
				Rico <reco_period> for <dist_id> under TSM <tsm_id> has become editable for next <grace_period> days
				*/
			finally
			{
				DBConnectionManager.releaseResources(ps ,null);
				DBConnectionManager.releaseResources(ps1 ,null);
				DBConnectionManager.releaseResources(ps2 ,null);
				DBConnectionManager.releaseResources(ps3 ,null);
				DBConnectionManager.releaseResources(connection);
			}
		}
		return retFlag;
	}
	
	public List updatePartnerData(FormFile myfile,String recoId) throws DAOException {
		//logger.info("333333333 in updatePartnerData ==========");
		String str  = "";
		List list = new ArrayList();
		List Error_list = new ArrayList();
		RecoPeriodDTO recoPeriodDTO = new RecoPeriodDTO();
		boolean validateFlag=true;
		String data="";
		ArrayList checkDuplicate = new ArrayList();
		String olmId="";
		Connection  connection = null;
		try
		{
			logger.info("updatePartnerData:::::");
			 connection = DBConnectionManager.getDBConnection();
			 boolean isEmpty=true;
			  InputStream stream = myfile.getInputStream();
			  ByteArrayOutputStream baos = new ByteArrayOutputStream();
			  int size = (int) myfile.getFileSize();
			  byte buffer[] = new byte[size];
			  int bytesRead = 0;
			//	        read the input
			  while( (bytesRead = stream.read(buffer, 0, size)) != -1 )
			  {
	        	  baos.write( buffer, 0, bytesRead );
	          }
	          data = new String( baos.toByteArray() );
	          int k = 0;
	          
	          //replace ,, with , ,
	          String newdata = data.replace(",", ",");
	          StringTokenizer st = new StringTokenizer(newdata, "");
	      //    String[] values = new String[dynamicArraySize];
	          while (st.hasMoreTokens())
	          {
	        	  isEmpty = false;
	        	  recoPeriodDTO = new RecoPeriodDTO();
	        	  olmId = st.nextToken(",\n\r").toString();
	        	  if(olmId != null)
	        	  {
	        		  olmId = olmId.trim();
	        	  }
	        	  System.out.println("olmId::::::"+olmId);
	        	 if(olmId != null && "".equals(olmId.trim()))
		          {
		        	  	recoPeriodDTO.setErr_msg("Blank space not allowed in OLM Id");
		        	  	recoPeriodDTO.setOlmId(olmId+" ");
		            	Error_list.add(recoPeriodDTO);
						validateFlag = false;
		          }
		          else if(checkDuplicate.contains(olmId))
	          		{
	          				recoPeriodDTO.setErr_msg("Duplicate OLM Id");
	            			recoPeriodDTO.setOlmId(olmId+" ");
	            			//list.add(recoPeriodDTO);
	            			Error_list.add(recoPeriodDTO);
	            			validateFlag = false;
	            			//return list;
	          		}
		          else
	      			{
		        	 // logger.info("44444444 in to be validateolm id  ==========");
	      				String presentInDp = validateOLM(connection, olmId,recoId);
	      				boolean flag1=validateDebit(connection, olmId,recoId);
	      				logger.info("asa:::::::::::::::flag1::::::::::::"+flag1);
	      				
	      				if( presentInDp != null && flag1==true)
	      				{
	      					presentInDp="Reco not submitted or Debit on distributor Already Performed.";
	      				}
	      				else if(flag1==true && presentInDp ==null)
	      				{
	      					presentInDp="Debit on Distributor Already Performed";
	      				}
	      				
	      				if( presentInDp != null && !presentInDp.equalsIgnoreCase("true") && flag1==true )
	      				{
	      					recoPeriodDTO.setOlmId(olmId+" ");
	          				recoPeriodDTO.setErr_msg(presentInDp);
		              		Error_list.add(recoPeriodDTO);
		              		validateFlag = false;
		              	}
	      				else
	      				{
	      					recoPeriodDTO.setErr_msg("SUCCESS");
		          			checkDuplicate.add(olmId);
		          			
		          			recoPeriodDTO.setOlmId(olmId+" ");
		          			Error_list.add(recoPeriodDTO);
	      				}
	      			}
		          k++;
	          };
	          if(isEmpty)
	          {
	        	  	recoPeriodDTO.setErr_msg("Empty File Uploaded");
      				recoPeriodDTO.setOlmId(" ");
      				Error_list.add(recoPeriodDTO);
      				list = Error_list;
	          }
	          else {
		          if(validateFlag)
		          {
		        	 // recoPeriodDTO.setList_srno(checkDuplicate);
		        	  list.add(recoPeriodDTO);
		          }
		          else
		          {
		        	  list = Error_list;
		          }
	          }
	          
	       
        }
		catch (Exception e) {
        	
        	logger.info("Error while reading the file---");
        	recoPeriodDTO.setErr_msg("Error while reading the file");
        	recoPeriodDTO.setOlmId(" ");
			Error_list.add(recoPeriodDTO);
			list = Error_list;
			return list;
        }
		finally
		{
			DBConnectionManager.releaseResources(connection);
		}
		return list;
	}
	
	
	public String validateOLM(Connection con, String olmId,String recoId)
	{
		
		logger.info("validateOLM:::::");
		PreparedStatement ps = null;
		ResultSet rs=null;
		String retFlag="true";
		boolean flag=false;
		
		String sql = "select RECO_STATUS from DP_RECO_DIST_DETAILS a,VR_LOGIN_MASTER b  "
					+" where a.DIST_ID=b.LOGIN_ID and a.reco_id=? and b.LOGIN_NAME=? and CREATED_BY !=0 with ur";
							
		
		try {
			String status="";
			ps =con.prepareStatement(sql);
			ps.setString(1, recoId);
			logger.info("recoId :"+recoId);
			logger.info("olmId :"+olmId);
			ps.setString(2, olmId);
			rs=ps.executeQuery();
			
			while(rs.next())
			{
				
				flag=true;
				status = rs.getString("RECO_STATUS");
		
				if(status != null && !status.equalsIgnoreCase("SUCCESS"))
				{
					
					retFlag = "RECO not Submitted yet or Debit pending";
				}
			}
			
			if(!flag)
			{
				
				retFlag ="Invalid OLM Id";
			}
			
			
		}
		catch(Exception ex)
		{
			logger.info(ex);
		}
		finally
		{
			DBConnectionManager.releaseResources(ps ,rs);
		}
		return retFlag;
	}
	
	
	public void updateGracePeriod(String gracePeriod, String loginUser) throws DAOException {
		PreparedStatement ps = null;
		PreparedStatement ps1 = null;
		Connection db2conn = null; 
		
		try {
			
			logger.info("updateGracePeriod:::::");
			String gracePeriodConf = getGracePeriod();
			 int gracePeriodConfInt = Integer.parseInt(gracePeriodConf)	;	
			 int gracePerioddays = Integer.parseInt(gracePeriod)	;	
			 if(gracePeriodConfInt!=gracePerioddays){
			db2conn = DBConnectionManager.getDBConnection();
					 
			String sql = "UPDATE DP_CONFIGURATION_MASTER SET CONFIG_VALUE=? WHERE CONFIG_ID=" + Constants.DP_RECO_GRACE_PERIOD;
			String sql1 = "UPDATE DP_RECO_PERIOD SET GRACE_PERIOD = ?,UPDATED_BY=?, UPDATED_ON =current timestamp WHERE IS_RECO_CLOSED = 0 ";
			ps = db2conn.prepareStatement(sql);
			ps.setInt(1, Integer.parseInt(gracePeriod));
			ps1= db2conn.prepareStatement(sql1);
			ps1.setInt(1, Integer.parseInt(gracePeriod));
			ps1.setInt(2, Integer.parseInt(loginUser));
			logger.info(sql);
			logger.info(sql1);
			ps.executeUpdate();
			ps1.executeUpdate();
			db2conn.commit();
			 }
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("Exception occured while getting Grace Period. Exception message: "
					+ e.getMessage());
			throw new DAOException("Exception: " + e.getMessage());
		} finally {
		DBConnectionManager.releaseResources(ps ,null);
		DBConnectionManager.releaseResources(ps1 ,null);
		DBConnectionManager.releaseResources(db2conn);
		}
		
	}
	
	
	public void addRecoPeriod(String fromDate, String toDate, String loginUser, String gracePeriod) throws DAOException{	
		Connection db2conn = null; 	
		PreparedStatement ps1 = null;				
			
		try {
			
			logger.info("addRecoPeriod:::::");
			if(fromDate != null && !fromDate.equals("")) {	
				Date dtFromDate =  Utility.getSqlDateFromString(fromDate,"MM/dd/yyyy");
				Date dtToDate =  Utility.getSqlDateFromString(toDate,"MM/dd/yyyy");
				java.sql.Date fromDateSql = new java.sql.Date(dtFromDate.getTime()) ;
				java.sql.Date toDateSql = new java.sql.Date(dtToDate.getTime());		
			db2conn = DBConnectionManager.getDBConnection();
			/*String sql = "SELECT ID FROM DP_RECO_PERIOD  ORDER BY ID DESC FETCH FIRST 1 ROWS ONLY";
			
			
			ps = db2conn.prepareStatement(sql);
			logger.info(sql);
			int id =0;
			
			rs = ps.executeQuery();			
			while (rs.next()) {
				 id = rs.getInt("ID");					
			}
			id+=1;		*/
			
			String sql1 = "INSERT INTO DP_RECO_PERIOD(ID, FROM_DATE, TO_DATE, CREATED_BY, CREATED_ON, GRACE_PERIOD) VALUES(nextval for DP_RECO_PERIOD_SEQ,?,?,?,current timestamp,?)";
			ps1 = db2conn.prepareStatement(sql1);
		
			ps1.setDate(1, fromDateSql);
			ps1.setDate(2, toDateSql);
			ps1.setInt(3, Integer.parseInt(loginUser));
			ps1.setInt(4, Integer.parseInt(gracePeriod));
		
			int result = ps1.executeUpdate();	
			logger.info(result);
			db2conn.commit();
			}
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("Exception occured while getting Reco History. Exception message: "
					+ e.getMessage());
			throw new DAOException("Exception: " + e.getMessage());
		} finally {
			
			//DBConnectionManager.releaseResources(ps ,rs);
			DBConnectionManager.releaseResources(ps1 ,null);
			DBConnectionManager.releaseResources(db2conn);	
		}	
		
	}
	//Added by suagndha to view reco details of individual recoperiod and to update graceperiod of corresponding 
	public List<RecoPeriodDTO> viewRecoGracePeriod(String recoPeriodId) throws DAOException 
	{
		Connection db2conn = null; 	
		List<RecoPeriodDTO> listReturn = new ArrayList<RecoPeriodDTO>();
		PreparedStatement ps= null;
		ResultSet rs = null;
		logger.info("ZSM     get dist List Dao Impl   Start......!");
		try
		{
			
			logger.info("viewRecoGracePeriod:::::");
			db2conn = DBConnectionManager.getDBConnection();
			logger.info("********************viewRecoGracePeriod   ********* "+recoPeriodId);
			String sql="SELECT ID,FROM_DATE,TO_DATE,GRACE_PERIOD FROM DP_RECO_PERIOD WHERE ID=? WITH UR";
			ps = db2conn.prepareStatement(sql);
			ps.setString(1, recoPeriodId);
			RecoPeriodDTO recoDTO =null;
			rs= ps.executeQuery();
			while(rs.next())
			{
			//	ID,FROM_DATE,TO_DATE,GRACE_PERIOD
				recoDTO =new RecoPeriodDTO();
				recoDTO.setRecoPeriodId(rs.getString("ID"));
				recoDTO.setFromDate(rs.getString("FROM_DATE"));
				recoDTO.setToDate(rs.getString("TO_DATE"));
				recoDTO.setGracePeriod(rs.getString("GRACE_PERIOD"));
				logger.info("****RecoPeriodId****"+recoDTO.getRecoPeriodId());
				logger.info("****FromDate****"+recoDTO.getFromDate());
				logger.info("****ToDate****"+recoDTO.getToDate());
				logger.info("****GracePeriod****"+recoDTO.getGracePeriod());
			listReturn.add(recoDTO);
				
			}
			logger.info("get dist List Dao Impl eNDS......!"+listReturn.size());
			
		}catch (Exception e) 
		{
			e.printStackTrace();
			throw new DAOException(e.getMessage());
		}
		finally
		{
			DBConnectionManager.releaseResources(ps ,rs );
		}
		
		return listReturn;
	}
	//Added by sugandha to update grace period of individual reco ID 
	public void updateOtherGracePeriod(String newGracePeriod, String loginUser, String recoPeriodId) throws DPServiceException 
	{
		PreparedStatement ps = null;
		PreparedStatement ps1 = null;
		Connection db2conn = null; 
		try {
			
			logger.info("updateOtherGracePeriod:::");
			db2conn = DBConnectionManager.getDBConnection();
			String sql = "UPDATE DPDTH.DP_RECO_PERIOD  SET GRACE_PERIOD=? WHERE ID=? ";
			String sql1 = "UPDATE DPDTH.DP_RECO_DIST_DETAILS SET GRACE_PERIOD=? WHERE RECO_ID=? ";
			ps = db2conn.prepareStatement(sql);
			ps.setInt(1, Integer.parseInt(newGracePeriod));
			ps.setInt(2, Integer.parseInt(recoPeriodId));
			ps1= db2conn.prepareStatement(sql1);
			ps1.setInt(1, Integer.parseInt(newGracePeriod));
			ps1.setInt(2, Integer.parseInt(recoPeriodId));
			logger.info(sql);
			logger.info(sql1);
			ps.executeUpdate();
			ps1.executeUpdate();
			db2conn.commit();
			
		}
		catch (Exception e)
		{
			e.printStackTrace();
			logger.error("Exception occured while getting Grace Period. Exception message: "
					+ e.getMessage());
			throw new DAOException("Exception: " + e.getMessage());
		} 
		finally 
		{
		DBConnectionManager.releaseResources(ps ,null);
		DBConnectionManager.releaseResources(ps1 ,null);
		DBConnectionManager.releaseResources(db2conn);
		}
	}
	//Added by sugandha to delete reco period details from DP_RECO_CERTIFICATE_DETAIL,DP_RECO_CERTIFICATE_HDR,DP_RECO_DIST_DETAILS,DP_RECO_PERIOD
	public void deleteReco(String loginUser, String recoPeriodId) throws DPServiceException
	{
		PreparedStatement ps = null;
		PreparedStatement ps1 = null;
		PreparedStatement ps2 = null;
		PreparedStatement ps3 = null;
		Connection db2conn = null; 
		try {
			logger.info("deleteReco:::");
			db2conn = DBConnectionManager.getDBConnection();
			
			//DELETE FROM DPDTH.DP_RECO_CERTIFICATE_DETAIL WHERE CERTIFICATE_ID IN(SELECT CERTIFICATE_ID FROM DP_RECO_CERTIFICATE_HDR WHERE RECO_ID=?)
			String deleteRecoCertiDetail  = "DELETE FROM DPDTH.DP_RECO_CERTIFICATE_DETAIL WHERE CERTIFICATE_ID IN(SELECT CERTIFICATE_ID FROM DP_RECO_CERTIFICATE_HDR WHERE RECO_ID=?)";
			ps = db2conn.prepareStatement(deleteRecoCertiDetail);
			ps.setInt(1, Integer.parseInt(recoPeriodId));
			ps.executeUpdate();
			//DELETE FROM DPDTH.DP_RECO_CERTIFICATE_HDR WHERE RECO_ID =?
			String deleteRecoCertiDetailHdr  = "DELETE FROM DPDTH.DP_RECO_CERTIFICATE_HDR WHERE RECO_ID =?";
			ps1 = db2conn.prepareStatement(deleteRecoCertiDetailHdr);
			ps1.setInt(1, Integer.parseInt(recoPeriodId));
			ps1.executeUpdate();

			//AS per discussion on 6th-Aug-2013 data has to be moved into DP_RECO_DIST_DETAILS_HIST
			String insertRecoDistHist="INSERT INTO DPDTH.DP_RECO_DIST_DETAILS_HIST(RECO_ID, DIST_ID, PRODUCT_ID, PRODUCT_TYPE, CREATED_BY, CREATED_ON, SER_OPEN_STOCK, NSER_OPEN_STOCK, DEFECTIVE_OPEN_STOCK, UPGRADE_OPEN_STOCK, CHURN_OPEN_STOCK, PENDING_PO_OPENING, PENDING_DC_OPENING, RECEIVED_WH, RECEIVED_INTER_SSD_OK, RECEIVED_INTER_SSD_DEF, RECEIVED_HIERARCHY_TRANS, INVENTORY_CHANGE, RECEIVED_UPGRADE, RECEIVED_CHURN, RETURNED_FRESH, RETURNED_INTER_SSD_OK, RETURNED_INTER_SSD_DEF, RETURNED_HIERARCHY_TRANS, RETURNED_DOA_BI, RETURNED_DOA_AI, RETURNED_SWAP, RETURNED_C2S, RETURNED_CHURN, FLUSH_OUT_OK, FLUSH_OUT_DEFECTIVE, SER_ACTIVATION, NSER_ACTIVATION, RECO_QTY, SER_CLOSING_STOCK, NSER_CLOSING_STOCK, DEF_CLOSING_STOCK, UPGRADE_CLOSING_STOCK, CHURN_CLOSING_STOCK, PENDING_PO_CLOSING, PENDING_DC_CLOSING, UPDATED_ON, CIRCLE_ID," +
						" RECO_STATUS, GRACE_PERIOD, HIST_DATE)(SELECT RECO_ID, DIST_ID, PRODUCT_ID, PRODUCT_TYPE, CREATED_BY, CREATED_ON, SER_OPEN_STOCK, NSER_OPEN_STOCK, DEFECTIVE_OPEN_STOCK, UPGRADE_OPEN_STOCK, CHURN_OPEN_STOCK, PENDING_PO_OPENING, PENDING_DC_OPENING, RECEIVED_WH, RECEIVED_INTER_SSD_OK, RECEIVED_INTER_SSD_DEF, RECEIVED_HIERARCHY_TRANS, INVENTORY_CHANGE, RECEIVED_UPGRADE, RECEIVED_CHURN, RETURNED_FRESH, RETURNED_INTER_SSD_OK, RETURNED_INTER_SSD_DEF, RETURNED_HIERARCHY_TRANS, RETURNED_DOA_BI, RETURNED_DOA_AI, RETURNED_SWAP, RETURNED_C2S, RETURNED_CHURN, FLUSH_OUT_OK, FLUSH_OUT_DEFECTIVE, SER_ACTIVATION, NSER_ACTIVATION, RECO_QTY, SER_CLOSING_STOCK, NSER_CLOSING_STOCK, DEF_CLOSING_STOCK, UPGRADE_CLOSING_STOCK, CHURN_CLOSING_STOCK, PENDING_PO_CLOSING, PENDING_DC_CLOSING, UPDATED_ON, CIRCLE_ID, RECO_STATUS, GRACE_PERIOD,current timestamp " + 
						"FROM DPDTH.DP_RECO_DIST_DETAILS WHERE RECO_ID=?)";
			ps1 = db2conn.prepareStatement(insertRecoDistHist);
			ps1.setInt(1, Integer.parseInt(recoPeriodId));
			ps1.executeUpdate();

			//DELETE FROM DPDTH.DP_RECO_DIST_DETAILS WHERE RECO_ID =?
			String deleteRecoDist = "DELETE FROM DPDTH.DP_RECO_DIST_DETAILS WHERE RECO_ID =?";
			ps1 = db2conn.prepareStatement(deleteRecoDist);
			ps1.setInt(1, Integer.parseInt(recoPeriodId));
			ps1.executeUpdate();
			
			//DELETE FROM DPDTH.DP_RECO_PERIOD WHERE RECO_ID =?
			String deleteRecoPeriod = "DELETE FROM DPDTH.DP_RECO_PERIOD WHERE ID =?";
			ps1 = db2conn.prepareStatement(deleteRecoPeriod);
			ps1.setInt(1, Integer.parseInt(recoPeriodId));
			ps1.executeUpdate();
			db2conn.commit();
		}
		catch (Exception e)
		{
			e.printStackTrace();
			logger.error("Exception occured while deleting Grace Period. Exception message: "
					+ e.getMessage());
			throw new DAOException("Exception: " + e.getMessage());
		} 
		finally 
		{
		DBConnectionManager.releaseResources(ps ,null);
		DBConnectionManager.releaseResources(ps1 ,null);
		DBConnectionManager.releaseResources(ps2 ,null);
		DBConnectionManager.releaseResources(ps3 ,null);
		DBConnectionManager.releaseResources(db2conn);
		}
	
		
	}
	
	//Added by shiva for frontend validation for reco period configuration
	public String recoPeriodOpening() throws DPServiceException {
		PreparedStatement ps = null;
		Connection db2conn = null; 
		ResultSet rs = null; 		
		String DD_VALUE ="";
		
		try {
			logger.info("recoPeriodOpening:::::");
			db2conn = DBConnectionManager.getDBConnection();
						
			String sql = "select DD_VALUE from DP_CONFIGURATION_DETAILS where VALUE = 'RECO_OPENING'  with UR";
					
			logger.info(sql);
			ps = db2conn.prepareStatement(sql);
			logger.info(sql);
			
			rs = ps.executeQuery();
			
			
			while (rs.next()) {
				
				DD_VALUE = rs.getString("DD_VALUE");
				System.out.println(DD_VALUE+ "DD_VALUE");				
				
			}
			
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("Exception occured in recoPeriodHist. Exception message: "
					+ e.getMessage());
			throw new DAOException("Exception: " + e.getMessage());
		} finally {
			DBConnectionManager.releaseResources(ps ,rs);			
			DBConnectionManager.releaseResources(db2conn);
		}
		return DD_VALUE;
	
		
	}
	
	public String recoPeriodClosing() throws DPServiceException {
		PreparedStatement ps = null;
		Connection db2conn = null; 
		ResultSet rs = null; 		
		String DD_VALUE ="";
		
		try {
			logger.info("recoPeriodClosing:::::");
			db2conn = DBConnectionManager.getDBConnection();
						
			String sql = "select DD_VALUE from DP_CONFIGURATION_DETAILS where VALUE = 'RECO_CLOSING'  with UR";
					
			logger.info(sql);
			ps = db2conn.prepareStatement(sql);
			logger.info(sql);
			
			rs = ps.executeQuery();
			
			
			while (rs.next()) {
				
				DD_VALUE = rs.getString("DD_VALUE");
				System.out.println(DD_VALUE+ "DD_VALUE");				
				
			}
			
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("Exception occured in recoPeriodHist. Exception message: "
					+ e.getMessage());
			throw new DAOException("Exception: " + e.getMessage());
		} finally {
			DBConnectionManager.releaseResources(ps ,rs);			
			DBConnectionManager.releaseResources(db2conn);
		}
		return DD_VALUE;	
		
	}
	
	
	//------------END shiva
	
	public int validateReco(String toDate) throws DAOException {

		PreparedStatement ps = null;
		Connection db2conn = null; 
		ResultSet rs = null; 
		int intValidate=0;
		String DD_VALUE ="";
		
		try {
			logger.info("daoimpl::::toDate:::::");
			db2conn = DBConnectionManager.getDBConnection();
			
			Date dtToDate =  Utility.getSqlDateFromString(toDate,"MM/dd/yyyy");
			java.sql.Date toDateSql = new java.sql.Date(dtToDate.getTime());
			logger.info("asa:::::::::::::::::dtToDate"+dtToDate);
			logger.info("asa:::::::::::::::::toDateSql"+toDateSql);
			
			String sql = "SELECT * FROM DP_RECO_PERIOD WHERE TO_DATE=? with ur";
					
			logger.info(sql);
			ps = db2conn.prepareStatement(sql);
			logger.info("sql:::::"+sql);
			ps.setDate(1, toDateSql);
			//intValidate=ps.executeQuery();
			rs = ps.executeQuery();
			
			
			while (rs.next()) {
				
				intValidate=1;				
				
			}
			
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("Exception occured in recoPeriodHist. Exception message: "
					+ e.getMessage());
			throw new DAOException("Exception: " + e.getMessage());
		} finally {
			DBConnectionManager.releaseResources(ps ,rs);			
			DBConnectionManager.releaseResources(db2conn);
		}
		logger.info("intValidate::::::::::::"+intValidate);
		return intValidate;
	}
	
	public int validateAction(String toDate) throws DAOException {


		PreparedStatement ps = null;
		Connection db2conn = null; 
		ResultSet rs = null; 
		int intValidate=0;
		String DD_VALUE ="";
		
		try {
			logger.info("daoimpl::::toDate:::::");
			db2conn = DBConnectionManager.getDBConnection();
			
			Date dtToDate =  Utility.getSqlDateFromString(toDate,"MM/dd/yyyy");
			java.sql.Date toDateSql = new java.sql.Date(dtToDate.getTime());
			logger.info("asa:::::::::::::::::dtToDate"+dtToDate);
			logger.info("asa:::::::::::::::::toDateSql"+toDateSql);
			
			String sql = "SELECT RP.ID FROM RECO_SERIALIZED_STOCK RSS,DP_RECO_PERIOD RP where RSS.RUNNING_SCHEDULER_DATE=RP.TO_DATE and RSS.DEBIT_FLAG not in ('Y','N') and RP.TO_DATE=? union SELECT RP.ID FROM RECO_UPGRADE_STOCK RUS,DP_RECO_PERIOD RP where RUS.RUNNING_SCHEDULER_DATE=RP.TO_DATE and RUS.DEBIT_FLAG not in ('Y','N') and RP.TO_DATE=? union  SELECT RP.ID FROM RECO_DEFECTIVE_STOCK RDS,DP_RECO_PERIOD RP where RDS.RUNNING_SCHEDULER_DATE=RP.TO_DATE and RDS.DEBIT_FLAG not in ('Y','N') and RP.TO_DATE=? union SELECT RP.ID FROM RECO_CHURN_STOCK RCS,DP_RECO_PERIOD RP where RCS.RUNNING_SCHEDULER_DATE=RP.TO_DATE and RCS.DEBIT_FLAG not in ('Y','N') and RP.TO_DATE=?  with ur";
					
			logger.info(sql);
			ps = db2conn.prepareStatement(sql);
			logger.info("sql:::::"+sql);
			ps.setDate(1, toDateSql);
			//intValidate=ps.executeQuery();
			rs = ps.executeQuery();
			
			
			while (rs.next()) {
				
				intValidate=1;				
				
			}
			
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("Exception occured in recoPeriodHist. Exception message: "
					+ e.getMessage());
			throw new DAOException("Exception: " + e.getMessage());
		} finally {
			DBConnectionManager.releaseResources(ps ,rs);			
			DBConnectionManager.releaseResources(db2conn);
		}
		logger.info("intValidate::::::::::::"+intValidate);
		return intValidate;
	}
	
	
	public int validateAction1(String recoId) throws DAOException {


		PreparedStatement ps = null;
		Connection db2conn = null; 
		ResultSet rs = null; 
		int intValidate=0;
		
		
		try {
			db2conn = DBConnectionManager.getDBConnection();
			
			String recoIds=recoId;
			
			String sql = "SELECT RP.ID FROM RECO_SERIALIZED_STOCK RSS,DP_RECO_PERIOD RP where date(RSS.RUNNING_SCHEDULER_DATE)=date(RP.TO_DATE) and  RSS.DIST_FLAG in ('N') and RSS.DEBIT_FLAG not in ('Y','N') and date(RP.TO_DATE) in  (select date(TO_DATE) from DP_RECO_PERIOD where id in ("+recoIds+")) union SELECT RP.ID FROM RECO_UPGRADE_STOCK RUS,DP_RECO_PERIOD RP where date(RUS.RUNNING_SCHEDULER_DATE)=date(RP.TO_DATE) and RUS.DIST_FLAG in ('N') and RUS.DEBIT_FLAG not in ('Y','N') and date(RP.TO_DATE) in (select date(TO_DATE) from DP_RECO_PERIOD where id in ("+recoIds+")) union SELECT RP.ID FROM RECO_DEFECTIVE_STOCK RDS,DP_RECO_PERIOD RP where date(RDS.RUNNING_SCHEDULER_DATE)=date(RP.TO_DATE) and  RDS.DIST_FLAG in ('N') and RDS.DEBIT_FLAG not in ('Y','N') and date(RP.TO_DATE) in (select date(TO_DATE) from DP_RECO_PERIOD where id in ("+recoIds+")) union SELECT RP.ID FROM RECO_CHURN_STOCK RCS,DP_RECO_PERIOD RP where date(RCS.RUNNING_SCHEDULER_DATE)=date(RP.TO_DATE) and RCS.DIST_FLAG in ('N') and RCS.DEBIT_FLAG not in ('Y','N') and date(RP.TO_DATE) in (select date(TO_DATE) from DP_RECO_PERIOD where id in ("+recoIds+"))  with ur";					
			ps = db2conn.prepareStatement(sql);
			//ps.setString(1, recoId);
			rs = ps.executeQuery();
			
			
			while (rs.next()) {
				
				intValidate=1;				
				
			}
			
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("Exception occured in recoPeriodHist. Exception message: "
					+ e.getMessage());
			throw new DAOException("Exception: " + e.getMessage());
		} finally {
			DBConnectionManager.releaseResources(ps ,rs);			
			DBConnectionManager.releaseResources(db2conn);
		}
		logger.info("intValidate::::::::::::"+intValidate);
		return intValidate;
	}
	
	
	//added for modify partner data
	
	
	
	
	public boolean validateDebit(Connection con, String olmId,String recoId)
	{
		
		logger.info("validateOLM:::::");
		PreparedStatement ps = null;
		ResultSet rs=null;
		boolean flag=true;
		
		//String sql = "SELECT DEBIT_FLAG FROM RECO_SERIALIZED_STOCK RSS, dp_reco_period DRP, DP_RECO_DIST_DETAILS DRDD, VR_LOGIN_MASTER VLM WHERE RSS.DISTRIBUTOR_ID=DRDD.DIST_ID  and RSS.DISTRIBUTOR_ID=VLM.LOGIN_ID and RSS.DEBIT_FLAG in ('Y','N') and DRDD.RECO_ID="+recoId+" and VR_LOGIN_MASTER.LOGIN_NAME= "+olmId+" union SELECT DEBIT_FLAG FROM RECO_UPGRADE_STOCK RUS, dp_reco_period DRP, DP_RECO_DIST_DETAILS DRDD, VR_LOGIN_MASTER VLM WHERE RUS.TO_DIST_ID=DRDD.DIST_ID  and RUS.TO_DIST_ID=VLM.LOGIN_ID and RUS.DEBIT_FLAG in ('Y','N') and DRDD.RECO_ID='"+recoId+"' and VR_LOGIN_MASTER.LOGIN_NAME='"+olmId+"'  union  SELECT DEBIT_FLAG FROM RECO_DEFECTIVE_STOCK RDS, dp_reco_period DRP, DP_RECO_DIST_DETAILS DRDD, VR_LOGIN_MASTER VLM WHERE RDS.FROM_DIST_ID=DRDD.DIST_ID and RDS.FROM_DIST_ID=VLM.LOGIN_ID and RDS.DEBIT_FLAG in ('Y','N') and DRDD.RECO_ID='"+recoId+"' and VR_LOGIN_MASTER.LOGIN_NAME='"+olmId+"' union SELECT DEBIT_FLAG FROM RECO_CHURN_STOCK RCS, dp_reco_period DRP, DP_RECO_DIST_DETAILS DRDD, VR_LOGIN_MASTER VLM WHERE RCS.FROM_DIST_ID=DRDD.DIST_ID and RCS.FROM_DIST_ID=VLM.LOGIN_ID and RCS.DEBIT_FLAG in ('Y','N') and DRDD.RECO_ID="+recoId+" and VR_LOGIN_MASTER.LOGIN_NAME="+olmId+" ";
		
		String sql="SELECT DEBIT_FLAG FROM RECO_SERIALIZED_STOCK RSS, dp_reco_period DRP, DP_RECO_DIST_DETAILS DRDD, VR_LOGIN_MASTER VLM WHERE RSS.DISTRIBUTOR_ID=DRDD.DIST_ID  and RSS.DISTRIBUTOR_ID=VLM.LOGIN_ID and RSS.DEBIT_FLAG in ('Y','N')  and DRDD.RECO_ID="+recoId+" and VLM.LOGIN_NAME= '"+olmId+"' union  SELECT DEBIT_FLAG FROM RECO_UPGRADE_STOCK RUS, dp_reco_period DRP, DP_RECO_DIST_DETAILS DRDD, VR_LOGIN_MASTER VLM WHERE RUS.TO_DIST_ID= DRDD.DIST_ID  and RUS.TO_DIST_ID=VLM.LOGIN_ID and RUS.DEBIT_FLAG in ('Y','N') and DRDD.RECO_ID="+recoId+" and VLM.LOGIN_NAME='"+olmId+"'  union SELECT DEBIT_FLAG FROM RECO_DEFECTIVE_STOCK RDS, dp_reco_period DRP, DP_RECO_DIST_DETAILS DRDD, VR_LOGIN_MASTER VLM  WHERE RDS.FROM_DIST_ID=DRDD.DIST_ID and RDS.FROM_DIST_ID=VLM.LOGIN_ID and RDS.DEBIT_FLAG in ('Y','N') and DRDD.RECO_ID="+recoId+" and VLM.LOGIN_NAME='"+olmId+"'  union  SELECT DEBIT_FLAG FROM RECO_CHURN_STOCK RCS, dp_reco_period DRP, DP_RECO_DIST_DETAILS DRDD, VR_LOGIN_MASTER VLM WHERE RCS.FROM_DIST_ID=DRDD.DIST_ID and RCS.FROM_DIST_ID=VLM.LOGIN_ID and RCS.DEBIT_FLAG in ('Y','N') and DRDD.RECO_ID="+recoId+" and VLM.LOGIN_NAME='"+olmId+"' ";		
		try {
			String status="";
			ps =con.prepareStatement(sql);
			//ps.setString(1, recoId);
			logger.info("recoId :"+recoId);
			logger.info("olmId :"+olmId);
			//ps.setString(2, olmId);
			rs=ps.executeQuery();
			
			if(!rs.next())
			{
				flag=false;
			}
			
			
			
			
		}
		catch(Exception ex)
		{
			ex.printStackTrace();
			logger.info(ex);
		}
		finally
		{
			DBConnectionManager.releaseResources(ps ,rs);
		}
		return flag;
	}
	
	
	
	
	
	
	
	
	//end 
	
	
}

