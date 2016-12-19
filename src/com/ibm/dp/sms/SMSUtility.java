package com.ibm.dp.sms;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

import org.apache.log4j.Logger;

import com.ibm.dp.common.DBQueries;
import com.ibm.dp.dao.DPPurchaseOrderDao;
import com.ibm.dp.dto.SMSDto;
import com.ibm.dp.dto.ViewStockEligibilityDTO;
import com.ibm.dp.service.ViewStockEligibilityService;
import com.ibm.dp.service.impl.ViewStockEligibilityServiceImpl;


import com.ibm.virtualization.recharge.common.Constants;
import com.ibm.virtualization.recharge.common.ExceptionCode;
import com.ibm.virtualization.recharge.common.ResourceReader;
import com.ibm.virtualization.recharge.dao.DAOFactory;
import com.ibm.virtualization.recharge.db.DBConnectionManager;
import com.ibm.virtualization.recharge.exception.DAOException;
//import com.ibm.xtq.bcel.classfile.Constant;

public class SMSUtility {
	
	private static Logger logger = Logger.getLogger(SMSUtility.class.getName());
	protected static HashMap<String, String> smsMap = new HashMap<String, String>();
	
	static
	{
		smsMap.put("sms.replace.dc_no","<dc_no>");
		smsMap.put("sms.replace.dist_id","<dist_id>");
		smsMap.put("sms.replace.account_name","<account_name>");
		smsMap.put("sms.replace.password","<password>");
		smsMap.put("sms.replace.po_no","<po_no>");
		smsMap.put("sms.replace.pr_no","<pr_no>");
		smsMap.put("sms.replace.inv_no","<inv_no>");
		smsMap.put("sms.replace.product_name","<product_name>");
		smsMap.put("sms.replace.remarks","<remarks>");
		smsMap.put("sms.replace.reject_login","<reject_login>");
		smsMap.put("sms.replace.tsm_name","<tsm_name>");
		smsMap.put("sms.replace.count","<count>");
		smsMap.put("sms.replace.dynamic","<dynamic>");
		smsMap.put("sms.replace.days","<days>");
		smsMap.put("sms.replace.date","<date>");
		smsMap.put("sms.replace.hours","<hours>");		
		smsMap.put("sms.replace.tsm_id","<tsm_id>");
		smsMap.put("sms.replace.reco_period","<reco_period>");		
		smsMap.put("sms.replace.grace_period","<grace_period>");			
		smsMap.put("sms.replace.status","<status>");
}
		
	
	
	
	public static String GET_MESSAGE="select a.SUCCESS_MESSAGE,a.ENABLE_FLAG as MAIN_ENABLE_FLAG,b.ENABLE_FLAG as USER_ENABLE_FLAG "
									+" from DP_ALERT_MASTER a left outer join DP_ALERT_USER_MAPPING b "
									+" on a.ALERT_ID=b.ALERT_ID and b.ACCOUNT_ID=? "
									+" where a.ALERT_ID=? "
									+" with ur ";
	public static String GET_MESSAGE_FLAGS="select a.SUCCESS_MESSAGE,a.ENABLE_FLAG as MAIN_ENABLE_FLAG,b.ENABLE_FLAG as USER_ENABLE_FLAG "
		+" from DP_ALERT_MASTER a left outer join DP_ALERT_USER_MAPPING b "
		+" on a.ALERT_ID=b.ALERT_ID and b.ACCOUNT_ID=? " 
		+" where a.ALERT_ID=? "
		+" with ur ";
										
	/*public static String GET_USER_DETAIL="select LOGIN_NAME ," 
										+ " (case when b.ACCOUNT_LEVEL = 6 then b.LAPU_MOBILE_NO else b.MOBILE_NUMBER end ) as MOBILE_NUMBER , " 
										+ " c.MOBILE_NUMBER as PARENT_MOBILE ,b.ACCOUNT_LEVEL , c.ACCOUNT_NAME as TSM_NAME , b.ACCOUNT_NAME  " 
										+" , d.MOBILE_NUMBER as ZSM_MOBILE,d.ACCOUNT_NAME as ZSM_NAME "
										+" from VR_LOGIN_MASTER a,VR_ACCOUNT_DETAILS b ,VR_ACCOUNT_DETAILS c  ,VR_ACCOUNT_DETAILS d  "
										+" where  a.LOGIN_ID=b.ACCOUNT_ID   "
										+" and b.PARENT_ACCOUNT=c.ACCOUNT_ID and c.PARENT_ACCOUNT=d.ACCOUNT_ID "
										+" and LOGIN_ID=? with ur ";*/
	
	
	public static String GET_USER_DETAIL = "select LOGIN_NAME ,  " 
										+" b.LAPU_MOBILE_NO,b.LAPU_MOBILE_NO_2,b.MOBILE_NUMBER , "  
										+" c.MOBILE_NUMBER as PARENT_MOBILE ,b.ACCOUNT_LEVEL , c.ACCOUNT_NAME as TSM_NAME , b.ACCOUNT_NAME "   
										+" , d.MOBILE_NUMBER as ZSM_MOBILE,d.ACCOUNT_NAME as ZSM_NAME  "
										+" from VR_LOGIN_MASTER a,VR_ACCOUNT_DETAILS b ,VR_ACCOUNT_DETAILS c  ,VR_ACCOUNT_DETAILS d  , "
										+" DP_DISTRIBUTOR_MAPPING MAP "
										+" where  a.LOGIN_ID=b.ACCOUNT_ID "   
										+" and MAP.DP_DIST_ID=b.ACCOUNT_ID "
										+" and b.ACCOUNT_LEVEL=6 "
										+" and MAP.PARENT_ACCOUNT=c.ACCOUNT_ID and c.PARENT_ACCOUNT=d.ACCOUNT_ID " 
										+" and LOGIN_ID=?  "
										+" union "
										+" select LOGIN_NAME , " 
										+" b.LAPU_MOBILE_NO,b.LAPU_MOBILE_NO_2, "
										+" b.MOBILE_NUMBER as MOBILE_NUMBER ,   "
										+" c.MOBILE_NUMBER as PARENT_MOBILE ,b.ACCOUNT_LEVEL , c.ACCOUNT_NAME as TSM_NAME , b.ACCOUNT_NAME "   
										+" , d.MOBILE_NUMBER as ZSM_MOBILE,d.ACCOUNT_NAME as ZSM_NAME  "
										+" from VR_LOGIN_MASTER a,VR_ACCOUNT_DETAILS b ,VR_ACCOUNT_DETAILS c  ,VR_ACCOUNT_DETAILS d "  
										+" where  a.LOGIN_ID=b.ACCOUNT_ID   "
										+" and b.ACCOUNT_LEVEL != 6  "
										+" and b.PARENT_ACCOUNT=c.ACCOUNT_ID and c.PARENT_ACCOUNT=d.ACCOUNT_ID " 
										+" and LOGIN_ID=? with ur";
	public static String GET_USER_DETAIL_TRANSACTION= "select LOGIN_NAME ,  " 
		+" b.LAPU_MOBILE_NO,b.LAPU_MOBILE_NO_2,b.MOBILE_NUMBER , "  
		+" c.MOBILE_NUMBER as PARENT_MOBILE ,b.ACCOUNT_LEVEL ,c.ACCOUNT_ID as TSM_ID, c.ACCOUNT_NAME as TSM_NAME , b.ACCOUNT_NAME "   
		+" , d.MOBILE_NUMBER as ZSM_MOBILE,d.ACCOUNT_ID as ZSM_ID,d.ACCOUNT_NAME as ZSM_NAME  "
		+" from VR_LOGIN_MASTER a,VR_ACCOUNT_DETAILS b ,VR_ACCOUNT_DETAILS c  ,VR_ACCOUNT_DETAILS d  , "
		+" DP_DISTRIBUTOR_MAPPING MAP "
		+" where  a.LOGIN_ID=b.ACCOUNT_ID "   
		+" and MAP.DP_DIST_ID=b.ACCOUNT_ID "
		+" and b.ACCOUNT_LEVEL=6 "
		+" and MAP.PARENT_ACCOUNT=c.ACCOUNT_ID and c.PARENT_ACCOUNT=d.ACCOUNT_ID " 
		+" and LOGIN_ID=?  and TRANSACTION_TYPE_ID=? with ur ";
		
	
	public static String GET_USER_DETAIL_DIST ="select a.LOGIN_NAME , LAPU_MOBILE_NO, a.LOGIN_ID,b.MOBILE_NUMBER as DISTRIBUTOR_MOBILE,  c.ACCOUNT_ID as TSM_ID,  c.MOBILE_NUMBER as TSM_MOBILE , c.ACCOUNT_NAME as TSM_NAME , b.ACCOUNT_NAME as DIST_ACCOUNT_NAME , d.MOBILE_NUMBER as ZSM_MOBILE,d.ACCOUNT_NAME as ZSM_NAME  ,tsm.LOGIN_NAME as TSM_OLM,zsm.LOGIN_ID as ZSM_ID,zsm.LOGIN_NAME as ZSM_OLM"
	 +" from VR_LOGIN_MASTER a,VR_ACCOUNT_DETAILS b ,VR_ACCOUNT_DETAILS c  ,VR_ACCOUNT_DETAILS d  ,VR_LOGIN_MASTER  tsm,VR_LOGIN_MASTER  zsm,"
		+" DP_DISTRIBUTOR_MAPPING MAP  where  a.LOGIN_ID=b.ACCOUNT_ID    and MAP.DP_DIST_ID=b.ACCOUNT_ID  and MAP.PARENT_ACCOUNT=c.ACCOUNT_ID and c.PARENT_ACCOUNT=d.ACCOUNT_ID and map.TRANSACTION_TYPE_ID=2  "
	+" and a.LOGIN_ID=(select LOGIN_ID from VR_LOGIN_MASTER where LOGIN_NAME=?) and tsm.LOGIN_ID=MAP.PARENT_ACCOUNT and zsm.LOGIN_ID=c.PARENT_ACCOUNT with ur";
	public static String GET_PO_NO = "select PO_NO from INVOICE_HEADER where INV_NO = ?  with ur ";
	public static int saveSMSInDB(Connection conDP,String[] mobileNo, String message, int alertType ) throws Exception{
		logger.info("smsutility  > savesmsindb >>>>>>>>>>>");
		PreparedStatement ps = null;	
		logger.info("Entering saveSMSInDB.");
		int status=0;
		int result=0;
		try {
			System.out.println("Message::"+message);
			System.out.println("status::"+status);
			System.out.println("mobileNo::"+mobileNo[0]);
			System.out.println(conDP);
			message = message.trim();
			System.out.println(message);
			String sql = "INSERT INTO DPDTH.DP_SEND_SMS(SMS_ID, MESSAGE, STATUS, SENT_ON, CREATED_ON, MOBILE_NUMBER , SENT_COUNT, ALERT_TYPE ) " +
					" VALUES(nextval for DP_SEND_SMS_SEQ, ? , ? , null, current timestamp, ? , 0,? )" ;
			ps = conDP.prepareStatement(sql);
			System.out.println(sql);
			System.out.println(mobileNo.length);
			
			for(int i=0; i < mobileNo.length;i++)
			{
				System.out.println("mobileNo[i]::::::::::::::"+mobileNo[i]);
				if(mobileNo[i] != null && !"".equals(mobileNo[i]))
				{
					ps.setString(1, message);
					ps.setInt(2, status);
					if(mobileNo[i]!=null)
					ps.setString(3, mobileNo[i].trim());
					else
					ps.setString(3,"");	
					ps.setInt(4, alertType); //alertType
					result += ps.executeUpdate();
				}	
				
			}
			logger.info(result);
			conDP.commit();

		} catch (Exception exception) {
			logger.info("Exception at saving sms(_) in DP_SEND_SMS table" +exception.getMessage());
			logger.info("error saving sms " + message + " at " + mobileNo+" in DP_SEND_SMS table");
			exception.printStackTrace();
			throw new Exception(exception);
		}
		finally
		{
			DBConnectionManager.releaseResources(ps,null);
			
		}
		logger.info("Exiting saveSMSInDB.");
		return result;
	}
	
	
	public static String getMobileNo(Connection conDP,int accountId) throws Exception{ //not called anywhere
		
		PreparedStatement ps = null;	
		logger.info("Entering getMobileNo.");
		ResultSet rs =null;
		String mobNo="";
		try {											
			String sql = "SELECT MOBILE_NUMBER FROM DPDTH.VR_ACCOUNT_DETAILS where ACCOUNT_ID =? with ur ";
			ps=conDP.prepareStatement(sql);// added by pratap
			ps.setInt(1, accountId);
			rs = ps.executeQuery(sql);	
			while(rs.next()){
				mobNo=rs.getString("MOBILE_NUMBER");
			}

		} catch (Exception exception) {
			logger.info("error in getMobileNo");	
			exception.printStackTrace();
			throw new Exception(exception);
		}
		finally
		{
			DBConnectionManager.releaseResources(ps,null);
			
		}
		logger.info("Exiting getMobileNo.");
		return mobNo;
	}
	public static String getMobileNoForSms(long accountId,int alertType, Connection connection) throws DAOException {
		logger.info("DPCreateAccountDaoImpl > getMobileNoForSms 22222222");
				//logger.info("Started ... for accountID " + accountId);
				String mobileNumber = null;
				int accountLevel=-1;
				int groupFlag=0;
				PreparedStatement ps = null;
				ResultSet rs = null;
				PreparedStatement ps1 = null;
				ResultSet rs1 = null;

				try {

					ps = connection.prepareStatement("SELECT AD.MOBILE_NUMBER,AD.ACCOUNT_LEVEL FROM VR_ACCOUNT_DETAILS AD,VR_LOGIN_MASTER LM WHERE AD.ACCOUNT_ID=? and LM.LOGIN_ID=? and LM.STATUS='A'with ur");
					// getting account Id on the basis of Account Code From AccountDAO
					ps.setLong(1, accountId);
					ps.setLong(2, accountId);
					rs = ps.executeQuery();
					// getting mobileNumber according to account Id
					// If mobileNumber not found then throw Invalid account ID exception
					if (rs.next()) {
						mobileNumber = rs.getString("MOBILE_NUMBER");
						accountLevel=rs.getInt("ACCOUNT_LEVEL");
					} else {
						// record not found if Account Code invalid hence throw
						// exception
						logger
								.error(" Exception occured :While getting mobileNumber According To Account ID "
										+ accountId);
						//throw new DAOException(
								//ExceptionCode.Account.ERROR_INVALID_ACCOUNT_CODE);
					}
					// checking group have anabled to receive SMS or not 
					if(mobileNumber!=null)
					{
						ps1=connection.prepareStatement("SELECT ENABLE_FLAG FROM DP_ALERT_GROUP_MAPPING WHERE ALERT_ID=? and GROUP_ID=? with UR");
						ps1.setInt(1, alertType);
						ps1.setInt(2, accountLevel+1);
						rs1=ps1.executeQuery();
						if(rs1.next())
						groupFlag=rs1.getInt("ENABLE_FLAG");
						else
							groupFlag=-1;
						if(groupFlag ==0)
						{
							mobileNumber=null;
							logger
							.error(" Exception occured :While"
									+ accountId +" Group has not enabled to recive SMS alerts for alert type "+alertType);
						//	throw new DAOException("Group has not enabled to recive SMS alerts for alert type "+alertType);
						}
					}
					
					// end for checking groyou have enabled to receive sms or not
				} catch (SQLException e) {
					logger.error("SQL Exception occured while get mobile no.."
							+ "Exception Message: ", e);
				} finally {
					/* Close the resultset, statement. */
					DBConnectionManager.releaseResources(ps, rs);
					DBConnectionManager.releaseResources(ps1, rs1);
				}
				logger.info("Executed ...Successfully retrieved mobile Number");
				return mobileNumber;
			}
	
	
	
	public static String getMobileNoForSmsOfTSM(long accountId,int alertType, Connection connection) throws DAOException {
		logger.info("DPCreateAccountDaoImpl > getMobileNoForSms 22222222");
				//logger.info("Started ... for accountID " + accountId);
				String mobileNumber = null;
				int accountLevel;
				int groupFlag=0;
				PreparedStatement ps = null;
				ResultSet rs = null;
				PreparedStatement ps1 = null;
				ResultSet rs1 = null;

				try {

					ps = connection.prepareStatement("select MOBILE_NUMBER  from VR_ACCOUNT_DETAILS where ACCOUNT_ID = (select PARENT_ACCOUNT from VR_ACCOUNT_DETAILS where ACCOUNT_ID=?) with ur");
					// getting account Id on the basis of Account Code From AccountDAO
					ps.setLong(1, accountId);
					rs = ps.executeQuery();
					// getting mobileNumber according to account Id
					// If mobileNumber not found then throw Invalid account ID exception
					if (rs.next()) {
						mobileNumber = rs.getString("MOBILE_NUMBER");
						System.out.println("account id  :"+accountId+"mobile no  :"+mobileNumber);
					} else {
						// record not found if Account Code invalid hence throw
						// exception
						logger
								.error(" Exception occured :While getting mobileNumber According To Account ID "
										+ accountId);
						//throw new DAOException(
							//	ExceptionCode.Account.ERROR_INVALID_ACCOUNT_CODE);
					}
					// checking group have anabled to receive SMS or not 
					if(mobileNumber!=null)
					{
						
						ps1=connection.prepareStatement("SELECT ENABLE_FLAG FROM DP_ALERT_GROUP_MAPPING WHERE ALERT_ID=? and GROUP_ID=? with UR");
						ps1.setInt(1, alertType);
						ps1.setInt(2, Constants.TSM_ID); //6
						rs1=ps1.executeQuery();
						if(rs1.next())
						groupFlag=rs1.getInt("ENABLE_FLAG");
						else
							groupFlag=-1; //Neetika.. if for particular group is not maintained in alert group mapping table then by default sms has to eb sent as discussed with Kundan
						
						if(groupFlag ==0)
						{
							mobileNumber=null;
							logger
							.error(" Exception occured :While"
									+ accountId +" Group has not enabled to recive SMS alerts for alert type "+alertType);
						//	throw new DAOException("Group has not enabled to recive SMS alerts for alert type "+alertType);
						}
					}
					
					// end for checking groyou have enabled to receive sms or not
				} catch (SQLException e) {
					logger.error("SQL Exception occured while get mobile no.."
							+ "Exception Message: ", e);
				} finally {
					/* Close the resultset, statement. */
					DBConnectionManager.releaseResources(ps, rs);
					DBConnectionManager.releaseResources(ps1, rs1);
				}
				logger.info("Executed ...Successfully retrieved mobile Number");
				return mobileNumber;
			}
	
public static String[] getSwapManagerMobileNos(Connection conDP,int accountId) throws Exception{ //not called anywhere
		
		PreparedStatement ps = null;	
		logger.info("Entering getSwapManagerMobileNos.");
		ResultSet rs =null;
		String mobNos[] =new String[2];
		try {											
			String sql = "SELECT MOBILE_NUMBER,SWAP_MANAGER_MOBILE_NUMBER FROM DPDTH.VR_ACCOUNT_DETAILS where ACCOUNT_ID =? with ur ";
			ps=conDP.prepareStatement(sql);// added by pratap
			ps.setInt(1, accountId);
			rs = ps.executeQuery(sql);	
			while(rs.next()){
				mobNos[0]=rs.getString("SWAP_MANAGER_MOBILE_NUMBER");
				mobNos[1]=rs.getString("MOBILE_NUMBER");
			}

		} catch (Exception exception) {
			logger.info("error in getMobileNo");	
			exception.printStackTrace();
			throw new Exception(exception);
		}
		finally
		{
			DBConnectionManager.releaseResources(ps,null);
			
		}
		logger.info("Exiting getMobileNo.");
		return mobNos;
	}
	
	
	
	
	public static String createMessageContent(int alertId , SMSDto dtoObj, Connection conn)
	{
		logger.info("alert id  :"+alertId+"dtoObj.getAccountName() "+dtoObj.getAccountName());
		//Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs=null;
		PreparedStatement ps1 = null;
		ResultSet rs1=null;
		String message="";
		String mainEnableFlag="";
		String userEnableFlag="";
		int accountid=0;
		try
		{
			// it is useless code======
			//from here
			conn = DBConnectionManager.getDBConnection();
			
			ps1=conn.prepareStatement("SELECT LOGIN_ID FROM VR_LOGIN_MASTER WHERE LOGIN_NAME=? with ur");
			ps1.setString(1,dtoObj.getAccountName()); // wrong - in case of BFR 30 it should be TSM Login/OLM Name
			rs1=ps1.executeQuery();
			if(rs1.next())
				accountid=rs1.getInt("LOGIN_ID");
			
			ps = conn.prepareStatement(GET_MESSAGE);
			logger.info("Account id of ::"+dtoObj.getUserAccountId()+" Account name  :"+dtoObj.getAccountName());
			//ps.setString(1, dtoObj.getUserAccountId());
			ps.setInt(1,accountid);
			
			ps.setInt(2, alertId);
			rs=ps.executeQuery();
			
			if(rs.next())
			{
				message = rs.getString("SUCCESS_MESSAGE");
				mainEnableFlag =  rs.getString("MAIN_ENABLE_FLAG");
				userEnableFlag =  rs.getString("USER_ENABLE_FLAG");
			}
		
			if(mainEnableFlag != null && mainEnableFlag.equalsIgnoreCase("0"))
			{
				return "";
			}
			if(mainEnableFlag != null && mainEnableFlag.equalsIgnoreCase("1"))
			{
				if(userEnableFlag != null && userEnableFlag.equalsIgnoreCase("0"))
				{
					return "";
				}
			}
			/*
			if(userEnableFlag != null && userEnableFlag.equalsIgnoreCase("0"))
			{
				if(mainEnableFlag != null && mainEnableFlag.equalsIgnoreCase("0"))
			{
				return "";
				
			}
			}
			else if (userEnableFlag == null)
			{
				if(mainEnableFlag != null && mainEnableFlag.equalsIgnoreCase("0"))
			{
				return "";
				
			}
			}*/
		
			//to here 
			switch (alertId) {
			
			case 1:
				// for Dc creation case. 
				message = message.replaceAll(smsMap.get
								("sms.replace.dc_no"),dtoObj.getDcNo() );
				message = message.replaceAll(smsMap.get
						("sms.replace.dist_id"),dtoObj.getLoginName() );
				System.out.println("case 1::"+dtoObj.getCountSrNo());
				message = message.replaceAll(smsMap.get
						("sms.replace.count"), (dtoObj.getCountSrNo()+""));
				message = message.replaceAll(smsMap.get
						("sms.replace.dynamic"), (dtoObj.getStbType()+""));
				message = message.replaceAll(smsMap.get
						("sms.replace.status"), (dtoObj.getStbStatus()+""));
				break;
				
			case 2:
//				 for password change case.
				message = message.replaceAll(smsMap.get
						("sms.replace.account_name"),dtoObj.getAccountName());
				message = message.replaceAll(smsMap.get
						("sms.replace.password"),dtoObj.getNewPassword());
					
				break;
			case 3:
//				 for PO marked as wrong shipment case.
				message = message.replaceAll(smsMap.get
						("sms.replace.dist_id"),dtoObj.getAccountName() );
				message = message.replaceAll(smsMap.get
						("sms.replace.po_no"),dtoObj.getPoNo() );
				message = message.replaceAll(smsMap.get
						("sms.replace.dc_no"),dtoObj.getDeliveryChallanNo() );
				message = message.replaceAll(smsMap.get
						("sms.replace.dynamic"),dtoObj.getDynamicSMSContent() );
				break;
			case 4:
//				 for PO rejected from BoTree.
				message = message.replaceAll(smsMap.get
						("sms.replace.account_name"),dtoObj.getAccountName());
				message = message.replaceAll(smsMap.get
						("sms.replace.po_no"),dtoObj.getPoNo() );
				message = message.replaceAll(smsMap.get
						("sms.replace.reject_login"),dtoObj.getPoRejectDesc() );
				message = message.replaceAll(smsMap.get
						("sms.replace.remarks"),dtoObj.getRemarks() );
			case 5:
//				 for DC dispatched.
				message = message.replaceAll(smsMap.get
						("sms.replace.account_name"),dtoObj.getAccountName() );
				message = message.replaceAll(smsMap.get
						("sms.replace.dc_no"),dtoObj.getDcNo() );
				message = message.replaceAll(smsMap.get
						("sms.replace.dynamic"),dtoObj.getDynamicSMSContent() );
				
				
				break;
			case 6:
//				 for PO Creation.
				message = message.replaceAll(smsMap.get
						("sms.replace.dist_id"),dtoObj.getLoginName() );
				message = message.replaceAll(smsMap.get
						("sms.replace.po_no"),dtoObj.getPoNo() );
				
				Iterator itt = dtoObj.getProdQuantityList().iterator();
				String repeatedStr="";
				
				while(itt.hasNext())
				{
					repeatedStr = repeatedStr + "," +(String)itt.next(); 
				}
				if(repeatedStr != null && repeatedStr.length() >=1 )
					repeatedStr=repeatedStr.substring(1,repeatedStr.length());
				
				message = message.replaceAll(smsMap.get
						("sms.replace.product_qty"),repeatedStr );
				break;
			case 7:
//				 for PO Approval from TSM
				message = message.replaceAll(smsMap.get
						("sms.replace.dist_id"),dtoObj.getAccountName() );
				message = message.replaceAll(smsMap.get
						("sms.replace.po_no"),dtoObj.getPoNo() );
				break;
			case 9:
//				 for PO marked as missing DC.
				message = message.replaceAll(smsMap.get
						("sms.replace.dist_id"),dtoObj.getAccountName() );
				message = message.replaceAll(smsMap.get
						("sms.replace.po_no"),dtoObj.getPoNo() );
				message = message.replaceAll(smsMap.get
						("sms.replace.dc_no"),dtoObj.getDeliveryChallanNo() );
				break;
			case 10:
//				 For Action Taken on DC from WH.
				message = message.replaceAll(smsMap.get
						("sms.replace.dist_id"),dtoObj.getAccountName() );
				message = message.replaceAll(smsMap.get
						("sms.replace.dc_no"),dtoObj.getDeliveryChallanNo() );
				message = message.replaceAll(smsMap.get
						("sms.replace.remarks"),dtoObj.getRemarks() );
				break;
			case 11:
//				 For PASSWORD EXPIRY ALERTS
				message = message.replaceAll(smsMap.get
						("sms.replace.dist_id"),dtoObj.getAccountName() );
				message = message.replaceAll(smsMap.get
						("sms.replace.days"),dtoObj.getDays() );
				
				break;
			case 12:
//				 For PO Acceptance Ageing
				message = message.replaceAll(smsMap.get
						("sms.replace.dist_id"),dtoObj.getAccountName() );
				message = message.replaceAll(smsMap.get
						("sms.replace.po_no"),dtoObj.getPoNo() );
				message = message.replaceAll(smsMap.get
						("sms.replace.dynamic"),dtoObj.getDynamicSMSContent() );
				message = message.replaceAll(smsMap.get
						("sms.replace.date"),dtoObj.getStatusDate());
				message = message.replaceAll(smsMap.get
						("sms.replace.hours"),dtoObj.getHours() );
				break;	       
			case 19:
				// reco update alert to DIST 
				message = message.replaceAll(smsMap.get
						("sms.replace.reco_period"),dtoObj.getRecoPeriod());
				message = message.replaceAll(smsMap.get
						("sms.replace.grace_period"),dtoObj.getGracePeriod());
				break;	
				
			case 20:
				// reco update alert to TSM
				message = message.replaceAll(smsMap.get
						("sms.replace.reco_period"),dtoObj.getRecoPeriod());
				message = message.replaceAll(smsMap.get
						("sms.replace.grace_period"),dtoObj.getGracePeriod());
				message = message.replaceAll(smsMap.get
						("sms.replace.dist_id"),dtoObj.getAccountName());
				break;	
				
			case 21:
			    // reco update alert to ZSM
				message = message.replaceAll(smsMap.get
						("sms.replace.reco_period"),dtoObj.getRecoPeriod());
				message = message.replaceAll(smsMap.get
						("sms.replace.grace_period"),dtoObj.getGracePeriod());
				message = message.replaceAll(smsMap.get
						("sms.replace.dist_id"),dtoObj.getAccountName());
				message = message.replaceAll(smsMap.get
						("sms.replace.tsm_id"),dtoObj.getTsmName());
				break;	
				
			default:
				break;
			}
			
			
		}
		catch(Exception ex)
		{
			logger.info(ex);
		}
		
		return message;
	}
	
public static SMSDto getUserDetails(String accountId ,Connection conn) //not used anywhere
	{
		//Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs=null;
		String loginName="";
		SMSDto smsdto=null;
		String mobileNumber="";
		String parentMobile="";
		String lapuMobile1="";
		String lapuMobile2="";
	try
	{
		//conn = DBConnectionManager.getDBConnection();
		ps = conn.prepareStatement(GET_USER_DETAIL);
		ps.setString(1, accountId);
		ps.setString(2, accountId);
		rs=ps.executeQuery();
		smsdto = new SMSDto();
		int i=0;
		boolean bothType = false;
		while(rs.next())
		{
			
			
			if(i == 0 ) {
				loginName = rs.getString("LOGIN_NAME");
				mobileNumber = rs.getString("MOBILE_NUMBER");
				smsdto.setAccountLevel(rs.getInt("ACCOUNT_LEVEL"));
				smsdto.setUserAccountId(accountId);
				smsdto.setAccountName(rs.getString("ACCOUNT_NAME"));
				smsdto.setZsmMobile(rs.getString("ZSM_MOBILE"));
				smsdto.setZsmName(rs.getString("ZSM_NAME"));
				smsdto.setLoginName(loginName);
				smsdto.setMobileNumber(mobileNumber);
				smsdto.setLapuMobile1(rs.getString("LAPU_MOBILE_NO"));
				smsdto.setLapuMobile2(rs.getString("LAPU_MOBILE_NO_2"));
				parentMobile=rs.getString("PARENT_MOBILE");
				smsdto.setParentMobileNumber(parentMobile);
			}
			if(i > 0 ) {	
				smsdto.setParentMobileNumber2(rs.getString("PARENT_MOBILE"));
				smsdto.setTsmName(rs.getString("TSM_NAME"));
				smsdto.setBothTypeAccount(true);
			}
			i++;
		}
	}
	catch(Exception ex)
	{
		logger.info(ex);
	}
	finally
	{
		DBConnectionManager.releaseResources(ps,rs);
		
	}
	return smsdto;
}

public static String getPONo(String inv_no , Connection conn )
{
	PreparedStatement ps = null;
	ResultSet rs=null;
	String po_no="";
	try
	{
		ps = conn.prepareStatement(GET_PO_NO);
		ps.setString(1, inv_no);
		rs=ps.executeQuery();
		if(rs.next())
		{
			po_no = rs.getString("PO_NO");
		}
	}
	catch(Exception ex)
	{
		logger.info(ex);
	}
	finally
	{
		DBConnectionManager.releaseResources(ps,rs);
		
	}
	return po_no;
}
public static void getStbType(SMSDto smsDto,Connection connection)
{
	String stbType=null;
	logger.info("====== generating stb type =======");
	try
	{
	PreparedStatement pstmt=connection.prepareStatement("SELECT PRODUCT_NAME FROM DP_PRODUCT_MASTER WHERE PRODUCT_ID=?");
	pstmt.setInt(1,Integer.parseInt(smsDto.getPrdId()));
	 ResultSet rset=pstmt.executeQuery();
	 while(rset.next())
	 {
		 stbType=rset.getString("PRODUCT_NAME");
		 smsDto.setProductName(stbType);
	 }
	}
	catch(Exception ex)
	{
		ex.printStackTrace();
	}

}

public static Boolean checkSMSFormat(String msg)
{
	return false;
}
public static String generateSMS(String msg,String mobileno)
{
	String responseMsg=null;
	String msgarr[]=null;
	String msgarrval=null;
	String keyword=null;
	String errorMsg=null;
	String distr_Olm_Id=null;
	Boolean tsmmobileno=true;
	int dist_Id=0;
	Connection connection=null;
	PreparedStatement pstmt=null;
	ResultSet rset=null;
	Boolean flag=false;
	String flag1="N";
	String []tsmMobileno={mobileno};
	
	logger.info("========= in generateSMS ==============");
	try
	{
		connection = DBConnectionManager.getDBConnection();
		tsmmobileno=isTsmMobileNo(mobileno);
		logger.info("========= tsmmobileno =="+tsmmobileno);
		if(tsmmobileno)
		{
		keyword=ResourceReader.getCoreResourceBundleValue("SMS.KEYWORD.STOCK.ELIGIBILITY.REPORT");
		logger.info("========= keyword =="+keyword);
		logger.info("========= msg =="+msg);
		msgarr=msg.trim().split(" ");
		logger.info(" msgarr :"+msgarr);
		msgarrval=msgarr[0];
		logger.info(" msgarrval :"+msgarrval);
		if(msgarrval.equalsIgnoreCase(keyword))
		{
			
			for(int i=1;i<msgarr.length;i++)  // getting distributor OLM ID from send sms by TSM
			{
			
				msgarrval=msgarr[i];
			
					if(!msgarrval.equalsIgnoreCase(""))
					{
					
						distr_Olm_Id=msgarr[i];
						
						break;
					}
			}
			
		}
		else
		{
			
			responseMsg=ResourceReader.getCoreResourceBundleValue("SMS.ERROR.MSG.STOCK.ELIGIBILITY.REPORT");
			return responseMsg;
		}
		
		
		//******* generating sms for distributor ***********
	logger.info("distr_Olm_Id :"+distr_Olm_Id);
		if(distr_Olm_Id!=null)
		{
			//logger.info("==33333333======");
		dist_Id=getDistId(distr_Olm_Id);
		String tsmid=null;
		//logger.info("==44444444444======dist_Id :"+dist_Id);
		if(dist_Id==0) // validating for Distributor id is valid or not 
		{
			logger.info("======dist_Id :"+dist_Id);
			responseMsg=ResourceReader.getCoreResourceBundleValue("SMS.WRONG.DIST.ERROR.MSG.STOCK.ELIGIBILITY.REPORT");
			
		}
		else
		{
			tsmid=isValidAccess(mobileno, dist_Id, connection);
			logger.info("======tsmid :"+tsmid);
			if(!tsmid.equalsIgnoreCase(""))
			{
				
				responseMsg=flagsCheck(Constants.CONFIRM_ID_SMS_PULL, null, connection, tsmid);
				/*pstmt=connection.prepareStatement(GET_MESSAGE);
				
				pstmt.setInt(1, dist_Id);
				pstmt.setInt(2, 22);
				rset=pstmt.executeQuery();
				
				while(rset.next())
				{
					
				responseMsg = rset.getString("SUCCESS_MESSAGE");
				logger.info("responseMsg:"+responseMsg);
				/*mainEnableFlag =  rset.getString("MAIN_ENABLE_FLAG");
				userEnableFlag =  rset.getString("USER_ENABLE_FLAG");
				*/
			//	}
				
			logger.info("===dist_Id :"+dist_Id);
			ViewStockEligibilityService vseService = new ViewStockEligibilityServiceImpl();
			
			List<ViewStockEligibilityDTO> viewStockList = new ArrayList<ViewStockEligibilityDTO>();
			
			DPPurchaseOrderDao dppod = DAOFactory.getDAOFactory( Integer.parseInt(ResourceReader
					.getCoreResourceBundleValue(Constants.DATABASE_TYPE))).getDPPurchaseOrderDao(connection);
			flag1 = dppod.getEligibilityFlag();
			if(flag1.equals("Y"))
			{
			viewStockList= vseService.getUploadedEligibility(dist_Id);//added by neetika
			}
			else 
			{
			viewStockList= vseService.getStockEligibility(dist_Id);
			}
				
			Iterator <ViewStockEligibilityDTO>itr=viewStockList.iterator();
			if(!responseMsg.equalsIgnoreCase(""))
			{
			responseMsg=responseMsg.replaceAll("<dist_id>", distr_Olm_Id);
			while(itr.hasNext())
			{
				ViewStockEligibilityDTO detail=itr.next();
				responseMsg=responseMsg+","+detail.getProductName()+" "+detail.getEligibilty();
			}
			//============== Sending SMS in DP_SEND_SMS TAble ================= 
			saveSMSInDB(connection, tsmMobileno, responseMsg, com.ibm.virtualization.recharge.common.Constants.CONFIRM_ID_SMS_PULL);
			// ============== Sending SMS end here ===========
			}
			else
			{
				logger.info("======== invalid accessss =========1111111111");
				responseMsg=ResourceReader.getCoreResourceBundleValue("SMS.INVALID.ACCESS.MSG.STOCK.ELIGIBILITY.REPORT");
			}
		}
		else
		{		logger.info("response in else");
				responseMsg=ResourceReader.getCoreResourceBundleValue("SMS.INVALID.ACCESS.MSG.STOCK.ELIGIBILITY.REPORT");
		}
		}
		}
		// **************************************************
		}
		else
		{		logger.info("response in else");
				responseMsg=ResourceReader.getCoreResourceBundleValue("SMS.INVALID.ACCESS.MSG.STOCK.ELIGIBILITY.REPORT");
		}
		logger.info("respnse msg:"+responseMsg);
	}
	catch(Exception ex)
	{
		ex.getMessage();	
	}
	finally
	{
		DBConnectionManager.releaseResources(pstmt, rset);
		DBConnectionManager.releaseResources(connection);
	}
	return responseMsg;
}

public static Boolean isTsmMobileNo(String mobileno)
{
	Connection connection=null;
	PreparedStatement pstmt=null;
	ResultSet rset=null;
	int accountlvl=0;
	boolean status=false;
	try
	{
	connection = DBConnectionManager.getDBConnection();
	pstmt=connection.prepareStatement("SELECT ACCOUNT_LEVEL FROM VR_ACCOUNT_DETAILS WHERE MOBILE_NUMBER=?");
	pstmt.setString(1, mobileno);
	rset=pstmt.executeQuery();
	while(rset.next())
	{
		accountlvl=rset.getInt("ACCOUNT_LEVEL");
	}
	if(accountlvl==5)
	{
		status= true;
	}
	else
	{
		status= false;
	}
	}
	catch(Exception ex)
	{
		ex.printStackTrace();
	}
	finally
	{
		DBConnectionManager.releaseResources(pstmt, rset);
		DBConnectionManager.releaseResources(connection);
	}
	return status;
}
public static int getDistId(String distOLMId)
{
	Connection connection=null;
	PreparedStatement pstmt=null;
	ResultSet rset=null;
	int distId=0;
	boolean status=false;
	try
	{
	connection = DBConnectionManager.getDBConnection();
	pstmt=connection.prepareStatement("SELECT LOGIN_ID FROM VR_LOGIN_MASTER WHERE LOGIN_NAME=? with ur");
	pstmt.setString(1, distOLMId);
	rset=pstmt.executeQuery();
	while(rset.next())
	{
		distId=rset.getInt("LOGIN_ID");
	}
	}
	catch(Exception ex)
	{
		ex.printStackTrace();
	}
	finally
	{
		DBConnectionManager.releaseResources(pstmt, rset);
		DBConnectionManager.releaseResources(connection);
	}
	return distId;
}
public static String isValidAccess(String tsmmobileno, int distid,Connection connection)
{
	PreparedStatement  pstmt=null;
	ResultSet rset=null;
	boolean flag=false;
	String tsmid="";
	logger.info("isValidAccess : tsm mobile no  :"+tsmmobileno+ " distid :"+distid);
	try
	{
		pstmt=connection.prepareStatement(DBQueries.SQL_TSM__DIST_VALIDAT);
		pstmt.setString(1, tsmmobileno);
		pstmt.setInt(2, distid);
		rset=pstmt.executeQuery();
		if(rset.next())
		{
			flag=true;
			tsmid=rset.getString("PARENT_ACCOUNT");
		}
	}
	catch(Exception ex)
	{
		ex.printStackTrace();
	}
	finally
	{
		DBConnectionManager.releaseResources(pstmt, rset);
	}
	return tsmid;
}
public static SMSDto getUserDetailsDistributor(String accountId ,Connection conn)//not used anywhere
{
	//Connection conn = null;
	PreparedStatement ps = null;
	ResultSet rs=null;
	String loginName="";
	SMSDto smsdto=null;
	String mobileNumber="";
	String parentMobile="";
	String lapuMobile1="";
	String lapuMobile2="";
try
{
	//conn = DBConnectionManager.getDBConnection();
	ps = conn.prepareStatement(GET_USER_DETAIL_DIST);
	logger.info("In getUserDetail method account id is .."+accountId+"   Query is :  "+GET_USER_DETAIL_DIST);
	ps.setString(1, accountId);

	rs=ps.executeQuery();
	smsdto = new SMSDto();

	boolean bothType = false;
	while(rs.next())
	{
		
			loginName = rs.getString("LOGIN_NAME");
			mobileNumber = rs.getString("DISTRIBUTOR_MOBILE");
			//smsdto.setAccountLevel(rs.getInt("ACCOUNT_LEVEL"));
			smsdto.setTsmID(rs.getString("TSM_ID"));
			smsdto.setZsmID(rs.getString("ZSM_ID"));
			smsdto.setUserAccountId(rs.getString("LOGIN_ID"));
			smsdto.setAccountName(rs.getString("DIST_ACCOUNT_NAME"));
			smsdto.setZsmMobile(rs.getString("ZSM_MOBILE"));
			smsdto.setZsmName(rs.getString("ZSM_OLM"));
			smsdto.setLoginName(loginName);
			smsdto.setMobileNumber(mobileNumber);
			smsdto.setLapuMobile1(rs.getString("LAPU_MOBILE_NO"));
			//smsdto.setLapuMobile2(rs.getString("LAPU_MOBILE_NO_2"));
			smsdto.setTsmName(rs.getString("TSM_OLM"));
			parentMobile=rs.getString("TSM_MOBILE");
			smsdto.setParentMobileNumber(parentMobile);
		
		
		
	}
}
catch(Exception ex)
{
	logger.info(ex.getMessage());
	ex.printStackTrace();
}
finally
{
	DBConnectionManager.releaseResources(ps,rs);
	
}
return smsdto;
}
public static String getProductName(String stbNo, Connection connection )
{
	PreparedStatement pstmt=null;
	ResultSet rset=null;
	String productName=null;
	try
	{
		pstmt=connection.prepareStatement(DBQueries.SQL_PRODUCT_NAME);
		pstmt.setInt(1, Integer.parseInt(stbNo));
		rset=pstmt.executeQuery();
		while(rset.next())
		{
			productName=rset.getString("PRODUCT_NAME");
		}
	}
	catch(Exception ex)
	{
		logger.info("Exception occured while getting product name:"+ex.getMessage());
		ex.printStackTrace();
	}
	return productName;
}
//Added by Neetika 15-9-2013 for getting transaction type basis the PO_NO
public static int getTransactionType(String PO_NO, Connection connection )
{
	PreparedStatement pstmt=null;
	ResultSet rset=null;
	int transactionType=0;
	String categoryCode=null;
	try
	{
		pstmt=connection.prepareStatement(DBQueries.SQL_GET_TRANSACTION_TYPE);
		pstmt.setString(1,PO_NO);
		rset=pstmt.executeQuery();
		while(rset.next())
		{
			categoryCode=rset.getString("CATEGORY_CODE");
		}
		
		if(categoryCode.equalsIgnoreCase("4"))
			transactionType=Constants.DISTRIBUTOR_TYPE_SALES;
		else
			transactionType=Constants.DISTRIBUTOR_TYPE_DEPOSIT;
	}
	catch(Exception ex)
	{
		logger.info("Exception occured while getting product name:"+ex.getMessage());
		ex.printStackTrace();
	}
	return transactionType;
}
//Added by Neetika to properly handle retrieval of mobile nos
public static SMSDto getUserDetailsasPerTransaction(String accountId ,Connection conn,int transaction)
{
	
	PreparedStatement ps = null;
	ResultSet rs=null;
	String loginName="";
	SMSDto smsdto=null;
	String mobileNumber="";
	String parentMobile="";
try
{
	
	ps = conn.prepareStatement(GET_USER_DETAIL_TRANSACTION);//Only for distributor
	ps.setString(1, accountId);
	ps.setInt(2, transaction);
	
	rs=ps.executeQuery();
	smsdto = new SMSDto();

	if(rs.next())
	{
		
			loginName = rs.getString("LOGIN_NAME");//Distributor OLM ID
			mobileNumber = rs.getString("MOBILE_NUMBER");
			smsdto.setMobileNumber(mobileNumber);//not required as such
			smsdto.setAccountLevel(rs.getInt("ACCOUNT_LEVEL"));
			smsdto.setUserAccountId(accountId);//Distributor LOGIN id
			smsdto.setAccountName(rs.getString("ACCOUNT_NAME"));
			smsdto.setZsmName(rs.getString("ZSM_NAME"));//account name
			smsdto.setLoginName(loginName);
			smsdto.setZsmID(rs.getString("ZSM_ID"));
			smsdto.setTsmID(rs.getString("TSM_ID"));
			if(transaction==1)
			smsdto.setLapuMobile1(rs.getString("LAPU_MOBILE_NO_2")); // setting only lapu 1 as this is the ony lapu to which msg is to be sent so pls dont make it lapu 2 unknowingly
			else
			smsdto.setLapuMobile1(rs.getString("LAPU_MOBILE_NO")); 
			parentMobile=rs.getString("PARENT_MOBILE");
			smsdto.setParentMobileNumber(parentMobile);
			smsdto.setParentMobileNumber2(rs.getString("ZSM_MOBILE"));
			smsdto.setZsmMobile(rs.getString("ZSM_MOBILE"));
			smsdto.setTsmName(rs.getString("TSM_NAME"));
			logger.info("Distributor LAPU: "+smsdto.getLapuMobile1()+" TSM MObile:  "+parentMobile +" ZSM mobile : "+ smsdto.getParentMobileNumber2());
	
		
	}
}
catch(Exception ex)
{
	logger.info(ex);
	ex.printStackTrace();
}
finally
{
	ps=null;
	rs=null;
	
}
return smsdto;
}

public static String flagsCheck(int alertId , SMSDto dtoObj, Connection conn,String id)
{
	logger.info("alert id  :"+alertId);
	//Connection conn = null;
	PreparedStatement ps = null;
	ResultSet rs=null;
	int group=0;
	String message="";
	String mainEnableFlag="";
	String userEnableFlag="";
	int groupflag=1;

	try
	{

		ps=conn.prepareStatement("SELECT GROUP_ID FROM VR_LOGIN_MASTER WHERE LOGIN_ID=? with ur");
		ps.setString(1,id); 
		rs=ps.executeQuery();
		if(rs.next())
			group=rs.getInt("GROUP_ID");
		
		ps.clearParameters();
		
		ps=conn.prepareStatement("SELECT ENABLE_FLAG FROM DP_ALERT_GROUP_MAPPING WHERE ALERT_ID=? and GROUP_ID=? with UR");
		ps.setInt(1, alertId);
		ps.setInt(2, group); 
		rs=ps.executeQuery();
		if(rs.next())
			groupflag=rs.getInt("ENABLE_FLAG");
		logger.info(id +" this id has group id "+group+" groupflag "+groupflag);		
		ps.clearParameters();
		if(groupflag==0)
		{
			return "";
			
		}
		
		ps = conn.prepareStatement(GET_MESSAGE_FLAGS);
		logger.info("Account id  ::"+id);
		ps.setInt(1,Integer.parseInt(id));
		ps.setInt(2,alertId);
		
		rs=ps.executeQuery();
		
		if(rs.next())
		{
			message = rs.getString("SUCCESS_MESSAGE");
			mainEnableFlag =  rs.getString("MAIN_ENABLE_FLAG");
			userEnableFlag =  rs.getString("USER_ENABLE_FLAG");
		}
		logger.info(id +" this id has group id "+group+" mainEnableFlag "+mainEnableFlag+" userEnableFlag "+ userEnableFlag);	
		
		
		if(mainEnableFlag != null && mainEnableFlag.equalsIgnoreCase("0"))
		{
			return "";
		}
		if(mainEnableFlag != null && mainEnableFlag.equalsIgnoreCase("1"))
		{
			if(userEnableFlag != null && userEnableFlag.equalsIgnoreCase("0"))
			{
				return "";
			}
		}
	
	
		
		
	}
	catch(Exception ex)
	{
		logger.info(ex);
		ps=null;
		rs=null;
	}
	finally
	{
		ps=null;
		rs=null;
	}
	
	return message;
}
public static String createMessageContentOnly(int alertId , SMSDto dtoObj)
{
	logger.info("alert id  :"+alertId);
	//Connection conn = null;
	PreparedStatement ps = null;
	ResultSet rs=null;
	int group=0;
	String message="";
	String mainEnableFlag="";
	String userEnableFlag="";
	int groupflag=1;

	try
	{

		message=dtoObj.getMessage();
		if(message!=null && !message.equalsIgnoreCase(""))
		{
		switch (alertId) {
		
		case 1:
			// for Dc creation case. 
			message = message.replaceAll(smsMap.get
							("sms.replace.dc_no"),dtoObj.getDcNo() );
			message = message.replaceAll(smsMap.get
					("sms.replace.dist_id"),dtoObj.getLoginName() );
			System.out.println("case 1::"+dtoObj.getCountSrNo());
			message = message.replaceAll(smsMap.get
					("sms.replace.count"), (dtoObj.getCountSrNo()+""));
			message = message.replaceAll(smsMap.get
					("sms.replace.dynamic"), (dtoObj.getStbType()+""));
			message = message.replaceAll(smsMap.get
					("sms.replace.status"), (dtoObj.getStbStatus()+""));
			break;
			
		case 2:
//			 for password change case.
			message = message.replaceAll(smsMap.get
					("sms.replace.account_name"),dtoObj.getAccountName());
			message = message.replaceAll(smsMap.get
					("sms.replace.password"),dtoObj.getNewPassword());
				
			break;
		case 3:
//			 for PO marked as wrong shipment case.
			message = message.replaceAll(smsMap.get
					("sms.replace.dist_id"),dtoObj.getAccountName() );
			message = message.replaceAll(smsMap.get
					("sms.replace.po_no"),dtoObj.getPoNo() );
			message = message.replaceAll(smsMap.get
					("sms.replace.dc_no"),dtoObj.getDeliveryChallanNo() );
			message = message.replaceAll(smsMap.get
					("sms.replace.dynamic"),dtoObj.getDynamicSMSContent() );
			break;
		case 4:
//			 for PO rejected from BoTree.
			message = message.replaceAll(smsMap.get
					("sms.replace.account_name"),dtoObj.getAccountName());
			message = message.replaceAll(smsMap.get
					("sms.replace.po_no"),dtoObj.getPoNo() );
			message = message.replaceAll(smsMap.get
					("sms.replace.reject_login"),dtoObj.getPoRejectDesc() );
			message = message.replaceAll(smsMap.get
					("sms.replace.remarks"),dtoObj.getRemarks() );
		case 5:
//			 for DC dispatched.
			message = message.replaceAll(smsMap.get
					("sms.replace.account_name"),dtoObj.getAccountName() );
			message = message.replaceAll(smsMap.get
					("sms.replace.dc_no"),dtoObj.getDcNo() );
			message = message.replaceAll(smsMap.get
					("sms.replace.dynamic"),dtoObj.getDynamicSMSContent() );
			
			
			break;
		case 6:
//			 for PO Creation.
			message = message.replaceAll(smsMap.get
					("sms.replace.dist_id"),dtoObj.getLoginName() );
			message = message.replaceAll(smsMap.get
					("sms.replace.po_no"),dtoObj.getPoNo() );
			
			Iterator itt = dtoObj.getProdQuantityList().iterator();
			String repeatedStr="";
			
			while(itt.hasNext())
			{
				repeatedStr = repeatedStr + "," +(String)itt.next(); 
			}
			if(repeatedStr != null && repeatedStr.length() >=1 )
				repeatedStr=repeatedStr.substring(1,repeatedStr.length());
			
			message = message.replaceAll(smsMap.get
					("sms.replace.product_qty"),repeatedStr );
			break;
		case 7:
//			 for PO Approval from TSM
			message = message.replaceAll(smsMap.get
					("sms.replace.dist_id"),dtoObj.getAccountName() );
			message = message.replaceAll(smsMap.get
					("sms.replace.po_no"),dtoObj.getPoNo() );
			break;
		case 9:
//			 for PO marked as missing DC.
			message = message.replaceAll(smsMap.get
					("sms.replace.dist_id"),dtoObj.getAccountName() );
			message = message.replaceAll(smsMap.get
					("sms.replace.po_no"),dtoObj.getPoNo() );
			message = message.replaceAll(smsMap.get
					("sms.replace.dc_no"),dtoObj.getDeliveryChallanNo() );
			break;
		case 10:
//			 For Action Taken on DC from WH.
			message = message.replaceAll(smsMap.get
					("sms.replace.dist_id"),dtoObj.getAccountName() );
			message = message.replaceAll(smsMap.get
					("sms.replace.dc_no"),dtoObj.getDeliveryChallanNo() );
			message = message.replaceAll(smsMap.get
					("sms.replace.remarks"),dtoObj.getRemarks() );
			break;
		case 11:
//			 For PASSWORD EXPIRY ALERTS
			message = message.replaceAll(smsMap.get
					("sms.replace.dist_id"),dtoObj.getAccountName() );
			message = message.replaceAll(smsMap.get
					("sms.replace.days"),dtoObj.getDays() );
			
			break;
		case 12:
//			 For PO Acceptance Ageing
			message = message.replaceAll(smsMap.get
					("sms.replace.dist_id"),dtoObj.getAccountName() );
			message = message.replaceAll(smsMap.get
					("sms.replace.po_no"),dtoObj.getPoNo() );
			message = message.replaceAll(smsMap.get
					("sms.replace.dynamic"),dtoObj.getDynamicSMSContent() );
			message = message.replaceAll(smsMap.get
					("sms.replace.date"),dtoObj.getStatusDate());
			message = message.replaceAll(smsMap.get
					("sms.replace.hours"),dtoObj.getHours() );
			break;	       
		case 19:
			// reco update alert to DIST 
			message = message.replaceAll(smsMap.get
					("sms.replace.reco_period"),dtoObj.getRecoPeriod());
			message = message.replaceAll(smsMap.get
					("sms.replace.grace_period"),dtoObj.getGracePeriod());
			break;	
			
		case 20:
			// reco update alert to TSM
			message = message.replaceAll(smsMap.get
					("sms.replace.reco_period"),dtoObj.getRecoPeriod());
			message = message.replaceAll(smsMap.get
					("sms.replace.grace_period"),dtoObj.getGracePeriod());
			message = message.replaceAll(smsMap.get
					("sms.replace.dist_id"),dtoObj.getAccountName());
			break;	
			
		case 21:
		    // reco update alert to ZSM
			message = message.replaceAll(smsMap.get
					("sms.replace.reco_period"),dtoObj.getRecoPeriod());
			message = message.replaceAll(smsMap.get
					("sms.replace.grace_period"),dtoObj.getGracePeriod());
			message = message.replaceAll(smsMap.get
					("sms.replace.dist_id"),dtoObj.getAccountName());
			message = message.replaceAll(smsMap.get
					("sms.replace.tsm_id"),dtoObj.getTsmName());
			break;	
			
		default:
			break;
		}
		}
		
	}
	catch(Exception ex)
	{
		logger.info(ex);
		ex.printStackTrace();
	}
	logger.info("message "+message);
	return message;
}
public static int saveSMSInDBMessages(Connection conDP,ArrayList mobileNo, String message, int alertType ) throws Exception{
	logger.info("smsutility  > savesmsindb >>>>>>>>>>>");
	PreparedStatement ps = null;	
	logger.info("Entering saveSMSInDB.");
	int status=0;
	int result=0;
	try {
		System.out.println("Message::"+message);
		System.out.println("status::"+status);
		System.out.println(conDP);
		message = message.trim();
		System.out.println(message);
		String sql = "INSERT INTO DPDTH.DP_SEND_SMS(SMS_ID, MESSAGE, STATUS, SENT_ON, CREATED_ON, MOBILE_NUMBER , SENT_COUNT, ALERT_TYPE ) " +
				" VALUES(nextval for DP_SEND_SMS_SEQ, ? , ? , null, current timestamp, ? , 0,? )" ;
		ps = conDP.prepareStatement(sql);
		System.out.println(sql);
		System.out.println(mobileNo.size());
		
		for(int i=0; i < mobileNo.size();i++)
		{
			
			if(mobileNo.get(i) != null && !"".equals(mobileNo.get(i).toString()))
			{
				System.out.println("mobileNo[i]::::::::::::::"+mobileNo.get(i).toString());
				ps.setString(1, message);
				ps.setInt(2, status);
				ps.setString(3, mobileNo.get(i).toString().trim());
				ps.setInt(4, alertType); //alertType
				result += ps.executeUpdate();
			}	
			
		}
		logger.info(result);
		conDP.commit();

	} catch (Exception exception) {
		logger.info("Exception at saving sms(_) in DP_SEND_SMS table" +exception.getMessage());
		logger.info("error saving sms " + message + " at " + mobileNo+" in DP_SEND_SMS table");
		exception.printStackTrace();
		throw new Exception(exception);
	}
	finally
	{
		DBConnectionManager.releaseResources(ps,null);
		
	}
	logger.info("Exiting saveSMSInDB.");
	return result;
}


}
