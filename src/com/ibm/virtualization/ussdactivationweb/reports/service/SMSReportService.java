
/**************************************************************************
 * File     : SMSReportService.java
 * Author   : Ashad
 * Created  : Oct 10, 2008
 * Modified : Oct 10, 2008
 * Version  : 1.0
 **************************************************************************
 *
 * Copyright @ 2002 This document has been prepared and written by 
 * IBM Global Services on behalf of Bharti, and is copyright of Bharti
 *
 **************************************************************************/

package com.ibm.virtualization.ussdactivationweb.reports.service;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.ResourceBundle;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.log4j.Logger;

import com.ibm.virtualization.ussdactivationweb.dao.DistportalServicesDaoImpl;
import com.ibm.virtualization.ussdactivationweb.dao.ServiceClassDAOImpl;
//import com.ibm.virtualization.ussdactivationweb.dto.SMSReportDTO;
//import com.ibm.virtualization.ussdactivationweb.dto.SMSReportRequestDTO;
import com.ibm.virtualization.ussdactivationweb.utils.DAOException;
import com.ibm.virtualization.ussdactivationweb.reports.dao.SMSReportDAO;
import com.ibm.virtualization.ussdactivationweb.services.dto.BusinessUserDTO;
import com.ibm.virtualization.ussdactivationweb.utils.Constants;
import com.ibm.virtualization.ussdactivationweb.utils.SendSMS;
import com.ibm.virtualization.ussdactivationweb.utils.Utility;


/**
 * All the methods in this class call their respective methods in DAO Class It
 * contain all SMSReport related methods.
 *
 * @author Ashad
 *
 */
public class SMSReportService {
	
	static ResourceBundle resourceBundle = null;
	
	static {
		/**  Getting SMS Report Code from SMS Report properies file **/
		resourceBundle = ResourceBundle.getBundle("com.ibm.virtualization.ussdactivationweb.resources.SMSReport");
			
	}

	/**
	 *  logger referenece to log the SMS Service class message. 
	 */
	final Logger log = Logger.getLogger(SMSReportService.class.getName());

	SMSReportDAO smsReportDAO =null;
	//DistportalServicesDaoImpl  distPortalDaoImpl = null;
	
	/*
	public SMSReportService() {
		smsReportDAO =new SMSReportDAO();
		distPortalDaoImpl= new DistportalServicesDaoImpl();
	}*/

  
	
  /*   
	public boolean validateSMSReportRequest(SMSReportRequestDTO requestDTO) {

		
		//boolean isValidRptCodeAndRegNo = validateRptCodeAndRequesterNo(requestDTO);
		String message = requestDTO.getMessage();
		message = message.trim();
		String[] result = message.split("\\s");
		String reportCode = null;
		int msgLength = result.length;
		if(msgLength > 0) {

			reportCode = result[0].trim().toUpperCase();
			String allreports = SMSReportConfigInitializer.ALL_SMS_REPORTS;
			String reports[] = allreports.split(",");
			boolean reportExists = false;
			for (int i=0; i < reports.length; i++) {
				if(reports[i].equals(reportCode)) {
					reportExists = true;					
					requestDTO.setReportCode(reportCode);
					break;
				}
			}
			if(!reportExists) {
				requestDTO.setErrMsg("Invalid FTA SMS Report code.");
				return false;
			}
		}    


		/** Check for report format and get the other parameters for report if required. * 

		if (reportCode.equals(SMSReportConfigInitializer.HUB_WISE_ACT_COUNT_FOR_FTD)){
			if(msgLength != 1) {
				requestDTO.setErrMsg(SMSReportConfigInitializer.HUB_WISE_ACT_COUNT_FOR_FTD_FORMAT);
				return false;
			} else {
				return true;
			} 
		}else if (reportCode.equals(SMSReportConfigInitializer.HUB_WISE_ACT_COUNT_MTD)){
			if(msgLength != 1) {
				requestDTO.setErrMsg(SMSReportConfigInitializer.HUB_WISE_ACT_COUNT_MTD_FORMAT);
				return false;
			} else {
				return true;
			}

		} else if(reportCode.equals(SMSReportConfigInitializer.ACT_PENDING_AT_DIST_FOR_FTD)){
			if(msgLength != 2) {
				requestDTO.setErrMsg(SMSReportConfigInitializer.ACT_PENDING_AT_DIST_FOR_FTD_FORMAT);
				return false;
			} else {
					requestDTO.setDistributorRegNo(result[1]);
				}

		} else if(reportCode.equals(SMSReportConfigInitializer.ACT_DONE_AT_DIST_FOR_FTD)){
			if(msgLength != 2) {
				requestDTO.setErrMsg(SMSReportConfigInitializer.ACT_DONE_AT_DIST_FOR_FTD_FORMAT);
				return false;
			} else {
					requestDTO.setDistributorRegNo(result[1]);
			
			}

		}else if( reportCode.equals(SMSReportConfigInitializer.DIST_WISE_ACT_COUNT_FOR_FTD)){
			if(msgLength != 2) {
				requestDTO.setErrMsg(SMSReportConfigInitializer.DIST_WISE_ACT_COUNT_FOR_FTD_FORMAT);
				return false;
			} else {
				
					requestDTO.setDistributorRegNo(result[1]);
			}

		}else if(reportCode.equals(SMSReportConfigInitializer.DIST_WISE_ACT_COUNT_MTD)){
			if(msgLength != 2) {
				requestDTO.setErrMsg(SMSReportConfigInitializer.DIST_WISE_ACT_COUNT_MTD_FORMAT);
				return false;
			} else {
					requestDTO.setDistributorRegNo(result[1]);
			
			}

		}else if(reportCode.equals(SMSReportConfigInitializer.DIST_WISE_ACT_COUNT_FOR_FTD)){
			if(msgLength != 2) {
				requestDTO.setErrMsg(SMSReportConfigInitializer.DIST_WISE_ACT_COUNT_FOR_FTD_FORMAT);
				return false;
			} else {
					requestDTO.setDistributorRegNo(result[1]);
			
			}

		} else if(reportCode.equals(SMSReportConfigInitializer.DEALER_WISE_ACT_COUNT_FOR_FTD)){
			if(msgLength != 2) {
				requestDTO.setErrMsg(SMSReportConfigInitializer.DEALER_WISE_ACT_COUNT_FOR_FTD_FORMAT);
				return false;
			} else {
				
					requestDTO.setDealerRegNo(result[1]);
			}

		}else if(reportCode.equals(SMSReportConfigInitializer.DEALER_WISE_ACT_COUNT_MTD)){

			if(msgLength != 2) {
				requestDTO.setErrMsg(SMSReportConfigInitializer.DEALER_WISE_ACT_COUNT_MTD_FORMAT);
				return false;
			} else {
				
					requestDTO.setDealerRegNo(result[1]);
				}

		} else  if(reportCode.equals(SMSReportConfigInitializer.CIRCLE_WISE_ACT_COUNT_FOR_FTD)){
			if(msgLength != 2) {
				requestDTO.setErrMsg(SMSReportConfigInitializer.CIRCLE_WISE_ACT_COUNT_FOR_FTD_FORMAT);
				return false;
			} else {
				
					requestDTO.setCircleCode(result[1]);
			
			}

		} else if(reportCode.equals(SMSReportConfigInitializer.CIRCLE_WISE_ACT_COUNT_MTD)){
			if(msgLength != 2) {
				requestDTO.setErrMsg(SMSReportConfigInitializer.CIRCLE_WISE_ACT_COUNT_MTD_FORMAT);
				return false;
			} else {
				
					requestDTO.setCircleCode(result[1]);
				
			}

		} else  if(reportCode.equals(SMSReportConfigInitializer.SERVICE_CLASS_WISE_ACT_COUNT_FOR_FTD)){
			if(msgLength != 3) {
				requestDTO.setErrMsg(SMSReportConfigInitializer.SERVICE_CLASS_WISE_ACT_COUNT_FOR_FTD_FORMAT);
				return false;
			} else {
					requestDTO.setServiceClassId(result[1]);
					requestDTO.setCircleCode(result[2]);
				}
		} else  if(reportCode.equals(SMSReportConfigInitializer.ACT_STATUS_FOR_SINGLE_SUB)){
			if(msgLength != 2) {
				requestDTO.setErrMsg(SMSReportConfigInitializer.ACT_STATUS_FOR_SINGLE_SUB_FORMAT);
				return false;
			} else {
				requestDTO.setSubsMSISDN(result[1]);
			}	
		}

		/** Format Validations for Other Parameters. * 


		String circleCode = requestDTO.getCircleCode();
		String distRegNo = requestDTO.getDistributorRegNo();
		String dealerRegNo = requestDTO.getDealerRegNo();
		String subMSISDN = requestDTO.getSubsMSISDN();
		String serviceClassId = requestDTO.getServiceClassId();

		/** Check Service class id * 
		if(serviceClassId != null) {
			Pattern pattern = Pattern.compile("^\\d+$");
			if(!pattern.matcher(serviceClassId).matches()){
				requestDTO.setErrMsg(SMSReportConfigInitializer.SERVICE_CLASS_ID_ERROR_MSG);
				return false;
			} else if(serviceClassId.length() > Integer.parseInt(SMSReportConfigInitializer.SERVICE_CLASS_ID_MAX_LENGTH)){
				requestDTO.setErrMsg(SMSReportConfigInitializer.SERVICE_CLASS_ID_MAX_LENGTH_ERROR_MSG);
				return false;
			} 
			if(!isValidCircleCode(requestDTO, circleCode)){
				return false;
			} 
			return true;
			
		}
		
		/** Validate circle code * 
		if(!isValidCircleCode(requestDTO, circleCode)){
			return false;
		} 
		/**  Validate distributor/dealer/subscriber  mobile no * 

		int intMinMSISDNLength = Integer.parseInt(SMSReportConfigInitializer.MIN_MOBILE_NO_LENGTH);
		int intMaxMSISDNLength = Integer.parseInt(SMSReportConfigInitializer.MAX_MOBILE_NO_LENGTH);
	
		
		if(distRegNo != null){
			int distRegNumber = distRegNo.length();
			Pattern pattern = Pattern.compile("^\\d+$");
			if (!pattern.matcher(distRegNo).matches()) {
				requestDTO.setErrMsg(SMSReportConfigInitializer.DIST_REG_NO_ONLY_NUMERIC_MSG);
				return false;
			} 
			if (distRegNumber < intMinMSISDNLength || distRegNumber > intMaxMSISDNLength) {
				requestDTO.setErrMsg(SMSReportConfigInitializer.DIST_REG_NO_LENGTH_ERROR_MSG);
				return false;
			} 
			return true;
		}
		
		if(dealerRegNo != null){
			int intDealerLength =dealerRegNo.length();
			Pattern pattern = Pattern.compile("^\\d+$");
			if (!pattern.matcher(dealerRegNo).matches()) {
				requestDTO.setErrMsg(SMSReportConfigInitializer.DEALER_REG_NO_ONLY_NUMERIC_MSG);
				return false;
			} 
			if (intDealerLength < intMinMSISDNLength || intDealerLength > intMaxMSISDNLength) {
				requestDTO.setErrMsg(SMSReportConfigInitializer.DEALER_REG_NO_LENGTH_ERROR_MSG);
				return false;
			} 
			
			return true;
		}
		
		
		if(subMSISDN != null){
			int msisdnLength = subMSISDN.length();
			Pattern pattern = Pattern.compile("^\\d+$");
			if (!pattern.matcher(subMSISDN).matches()) {
				requestDTO.setErrMsg(SMSReportConfigInitializer.SUBS_MSISDN_ONLY_NUMERIC_MSG);
				return false;
			} 
			if (msisdnLength < intMinMSISDNLength || msisdnLength > intMaxMSISDNLength) {
				requestDTO.setErrMsg(SMSReportConfigInitializer.SUBS_MSISDN_LENGTH_ERROR_MSG);
				return false;
			} 
			
			return true;
		}
		/** Check Service class id * 
		if(serviceClassId != null) {
			Pattern pattern = Pattern.compile("^\\d+$");
			if(!pattern.matcher(serviceClassId).matches()){
				requestDTO.setErrMsg(SMSReportConfigInitializer.SERVICE_CLASS_ID_ERROR_MSG);
				return false;
			} else if(serviceClassId.length() > Integer.parseInt(SMSReportConfigInitializer.SERVICE_CLASS_ID_MAX_LENGTH)){
				requestDTO.setErrMsg(SMSReportConfigInitializer.SERVICE_CLASS_ID_MAX_LENGTH_ERROR_MSG);
				return false;
			}
			return true;
		}
		return true;
	}
*/
	/**
	 * @param requestDTO
	 * @param circleCode
	 */
	/*
	private boolean isValidCircleCode(SMSReportRequestDTO requestDTO, String circleCode) {
		if(circleCode != null) {
			Pattern pattern = Pattern.compile("[a-zA-Z]*");
			if(!pattern.matcher(circleCode).matches()){
				requestDTO.setErrMsg(SMSReportConfigInitializer.CIRCLE_CODE_ERROR_MESSAGE);
				return false;
			}
			if(circleCode.length() > 3){
				requestDTO.setErrMsg(SMSReportConfigInitializer.CIRCLE_CODE_LENGTH_ERROR_MESSAGE);
				return false;
			}
			
		}
		return true;
	}
*/
	/**
	 * @param requestDTO
	 * @return
	 */
	/*
	public boolean validateRptCodeAndRequesterNo(SMSReportRequestDTO requestDTO) {
		if(requestDTO == null) {
			return false;
		}
		String requestorMobileNo =requestDTO.getMobileNo();
		if(requestorMobileNo == null || "".equals(requestorMobileNo)) {
			requestDTO.setErrMsg("Please provide requester-registered number.");
			return false;
		}
		
		/**  Validate requestor  mobile no * 
       
		if(requestorMobileNo != null){
			int msisdnLength = requestorMobileNo.length();
			int intMinMSISDNLength = Integer.parseInt(SMSReportConfigInitializer.MIN_MOBILE_NO_LENGTH);
			int intMaxMSISDNLength = Integer.parseInt(SMSReportConfigInitializer.MAX_MOBILE_NO_LENGTH);
			Pattern pattern = Pattern.compile("^\\d+$");
			if (!pattern.matcher(requestorMobileNo).matches()) {
				requestDTO.setErrMsg(SMSReportConfigInitializer.REQUESTOR_MSISDN_ONLY_NUMERIC_MSG);
				return false;
			} 
			if (msisdnLength < intMinMSISDNLength || msisdnLength > intMaxMSISDNLength) {
				requestDTO.setErrMsg(SMSReportConfigInitializer.REQUESTOR_REG_NO_LENGTH_ERROR_MSG);
				return false;
			} 
			
		}
		/**  Check if ReportId / report code is valid * 
		String message = requestDTO.getMessage();
		if(message == null || "".equals(message)) {
			requestDTO.setErrMsg("Kindly provide FTA SMS report code.");
			return false;
		} else {
			message = message.trim();
			String[] result = message.split("\\s");
			String reportCode = null;
			int msgLength = result.length;
			if(msgLength > 0) {
				reportCode = result[0].trim().toUpperCase();
				String allreports = SMSReportConfigInitializer.ALL_SMS_REPORTS;
				String reports[] = allreports.split(",");
				boolean reportExists = false;
				for (int i=0; i < reports.length; i++) {
					if(reports[i].equals(reportCode)) {
						reportExists = true;					
						requestDTO.setReportCode(reportCode);
						break;
					}
				}
				if(!reportExists) {
					requestDTO.setErrMsg("Invalid FTA SMS Report code.");
					return false;
				}
			   
			}
			
		}
		return true;
	}
*/
	/**
	 * This method returns the business user wise activation count 
	 * @param registeredNo is the requester registered number
	 * @param reportId is the requested Report 
	 * @return total activation done by specific business users.
	 */
/*
	public String getPullReport(SMSReportRequestDTO requestDTO,BusinessUserDTO businessUserDTO,int rptTypeFlag){

		String reportMsg = null;
		try{
			log.info(" Entering in getPullReport() method of SMSReportService class");

			if(rptTypeFlag==Constants.DAILY_REPORT || rptTypeFlag==Constants.MONTHLY_REPORT ) {

				/**  Get the total activation count  Distributor/Dealer, Hub/Circle,daily/monthly wise * 

				reportMsg = getReportCount(requestDTO,businessUserDTO,rptTypeFlag); 

			}
		}catch (Exception e) {
			log.error("Error occurred " + e.getMessage(),e);
			reportMsg = Constants.SMS_COMMON_MESAGE;
		} 
		log.info(" Exiting from getPullReport() method of SMSReportService class");
		return reportMsg;
	}
*/	
	/*
	public String getLatestPullReport(SMSReportRequestDTO requestDTO,BusinessUserDTO businessUserDTO,String smsKeyword){

		String reportMsg = null;
		try{
			log.info(" Entering in getNewPullReport() method of SMSReportService class");

				/**  Get the total activation count  Distributor/Dealer, Hub/Circle,daily/monthly wise * 

				reportMsg = getLatestReportCount(requestDTO,businessUserDTO,smsKeyword); 

		}catch (Exception e) {
			log.error("Error occurred " + e.getMessage(),e);
			reportMsg = Constants.SMS_COMMON_MESAGE;
		} 
		log.info(" Exiting from getNewPullReport() method of SMSReportService class");
		return reportMsg;
	}

*/

	/**
	 * 
	 * @param userRegisteredNo
	 * @return
	 */
	/* 
     private HashMap getRequestorInfo(String userRegisteredNo){
    	 HashMap userinfoMap =null;
    	 try{

    		 userinfoMap = smsReportDAO.getRequesterInfo(userRegisteredNo);

		} catch(Exception e){

		}
		return userinfoMap;

	}*/

	public int verifyRequestorPermission(BusinessUserDTO businessUserDTO,String reportId){

		int rptTypeValue = Constants.NO_PERMISSION;
		try {
			rptTypeValue = smsReportDAO.verifyRequestorPermission(businessUserDTO, reportId);
		} catch (DAOException e) {
			String msg = Constants.SMS_COMMON_MESAGE;
			log.error(msg,e);
		} 
		return rptTypeValue;
	}

	private DistportalServicesDaoImpl distPortalService = new DistportalServicesDaoImpl();


	public BusinessUserDTO getNewBusinessUserDetails(String registeredNo) throws Exception {

		try {
			
			return distPortalService.getNewRequesterDetailsOfAllUserType(registeredNo);
			
		} catch (DAOException e) {
			String msg = Constants.SMS_COMMON_MESAGE;
			log.error(msg,e);
			throw new Exception(msg);
		}
	}
	
	public ArrayList getBusinessUserDetails(String registeredNo) throws Exception {
        ArrayList userList = new ArrayList();
        
		try {
			
			userList = distPortalService.getRequesterDetailsOfAllUserType(registeredNo);
			int maxMaterId = 0;
			for(int index =0,size = userList.size() ; index < size ; index++){
				
				BusinessUserDTO businessUserDTO= (BusinessUserDTO)userList.get(index);
				maxMaterId = businessUserDTO .getTypeOfUserId();
				if (maxMaterId < businessUserDTO .getTypeOfUserId() 
						&& Constants.ActiveState.equalsIgnoreCase(businessUserDTO.getStatus())){
					maxMaterId= businessUserDTO .getTypeOfUserId();
					
				}
			}
			userList.clear();
			for(int index =0,size = userList.size() ; index < size ; index++){
				BusinessUserDTO businessUserDTO= (BusinessUserDTO)userList.get(index);
				if (maxMaterId == businessUserDTO .getTypeOfUserId()){
					userList.add(userList);
					break;
				}
			}
			
			
		} catch (DAOException e) {
			String msg = Constants.SMS_COMMON_MESAGE;
			log.error(msg,e);
			throw new Exception(msg);
		}
		return userList;
	} 


	/**
	 * This method return the single Subscriber status requested by business user
	 * @param registeredNo is the requester registered number
	 * @param reportId is the requested Report 
	 * @param subsMSISDN subscriber MSISDN 
	 * @param rptDate is report date 
	 * @return STATUS of requested MSISDN
	 */
	/*
	public String  getActStatusForSubs(SMSReportRequestDTO requestDTO){
		String registeredNo =requestDTO.getMobileNo();
		String subsMSISDN = requestDTO.getSubsMSISDN();
		String rptDate = requestDTO.getRptDate();
		String smsMessage = null;
		try{
			boolean isMSISDNExists =smsReportDAO.getActiveMSISDNList(subsMSISDN);
			if(isMSISDNExists){
				smsMessage =  smsReportDAO.getActStatusForSubs(registeredNo,subsMSISDN,rptDate);   
			} else {
				smsMessage =  new StringBuffer(500)
				.append("Transaction has not been initiated for ")
				.append(subsMSISDN).append(" subscriber ").toString();
				log.debug(smsMessage);
			}	
			
		}catch (Exception e) {
			log.error("Error occurred " + e.getMessage(),e);
			subsMSISDN = Constants.SMS_COMMON_MESAGE;
		} 
		log.info(" Exiting from getActStatusForSubs() method of SMSReportService class");
		return smsMessage;
	}
*/
	/**
	 * 
	 * This method return the no of activation done for input Category
	 * @param registeredNo is the requester registered number
	 * @param reportId is the requested Report 
	 * @param categoryDESC is the Service category 
	 * @param rptDate is report date 
	 * @return HBO wise activation count
	 */
	/*
	public String getActServiceClassWise(SMSReportRequestDTO requestDTO,int rptTypeFlag){
		String reportMsg = "";
		String serviceClassId = requestDTO.getServiceClassId();
		
		String circleCode = requestDTO.getCircleCode();
		String strDate = requestDTO.getRptDate();
		try{
			log.info(" Entering in getActServiceClassWise() method of SMSReportService class");
			
			 String serviceClassName = new ServiceClassDAOImpl().getServiceClassNameById(Integer.parseInt(serviceClassId));
			
			 if(null == serviceClassName ){
				 reportMsg = "Service class id does not exist.";
			 } else if(!smsReportDAO.isCircleExist(circleCode)){
				reportMsg = "Circle code does not exist or may be inactive.";
			} else {
				reportMsg = smsReportDAO.getActServiceClassWise(serviceClassId,circleCode,strDate,rptTypeFlag);
			} 
			
			 else {
				reportMsg = new StringBuffer(200).append("Service class id ")
				.append(serviceClassId).append(" does not exist in ").append(circleCode)
				.append(" circle  or may be inactive.").toString();
				
			} 
		}catch (DAOException e) {
			reportMsg = Constants.SMS_COMMON_MESAGE;
			log.error("Error occurred " + e.getMessage(),e);
		}
		catch (Exception e) {
			log.error("Error occurred " + e.getMessage(),e);
			reportMsg = Constants.SMS_COMMON_MESAGE;
		} 

		log.info(" Exiting from  getActServiceClassWise() method of SMSReportService class");
		return reportMsg;
	}
*/


	/**
	 * This method is responsible for getting the business users 
	 * and send the report on SMS as per  their roles 
	 * @param intRptId report name
	 * @param circleCode is circle code
	 * @param rptDate is the report requested date
	 * @param reporTypeFlag report type may be 1 for daily or 2 for monthly
	 */ 
	/*public  void  pushSMSReport(int intRptId,String circleCode,String rptDate,int reporTypeFlag){

		ArrayList userRoleList = new ArrayList();
		ArrayList userInfoList = new ArrayList();
		String activationMsg = null;
		boolean commonMessage = false;
		String reportId = String.valueOf(intRptId);
		StringBuffer stringBuffer =null;
		try{
			log.info(" Entering in pushSMSReport() method of SMSReportService class");
			userRoleList = smsReportDAO.getUsersByRtIdCode(reportId,circleCode);

			*//** Get the Users List for sending Push reports. **//*

			userInfoList = distPortalDaoImpl.getUserInfoByRole(userRoleList,circleCode);

			log.debug("userInfoList == "+userInfoList);

			*//** If no users are availalble, exit the push report. **//*

			if(userInfoList == null || userInfoList.isEmpty()){
				stringBuffer = new StringBuffer().append("No user available for report id ")
				.append(reportId)
				.append(" , and Circle code ").append(circleCode);

				log.debug(stringBuffer.toString());
				activationMsg = stringBuffer.toString();

				*//** No users to send report, hence return. **//*
				return;				
			} 
			else {

				//Set the messages for the users.  
				// Get count for Hub-wise  / Circle-wise report and then set the message for each user.
				// For all non user-wise reports, get report

				*//** Get Hub-wise daily or monthly report **//*

				if(reportId.equals(SMSReportConfigInitializer.HUB_WISE_ACT_COUNT_FOR_FTD) 
						|| reportId.equals(SMSReportConfigInitializer.HUB_WISE_ACT_COUNT_MTD)){

					String hubCode = smsReportDAO.getHubCodeByCircleCode(circleCode);

					//activationMsg = smsReportDAO.getHubWiseActCount(hubCode,rptDate,reporTypeFlag);
					if(activationMsg != null || "".equals(activationMsg)){
						commonMessage = true;	
					}   
				} 
				*//** Get Circle-wise daily or monthly report **//*
				else if(reportId.equals(SMSReportConfigInitializer.CIRCLE_WISE_ACT_COUNT_FOR_FTD) 
						|| reportId.equals(SMSReportConfigInitializer.CIRCLE_WISE_ACT_COUNT_MTD)){

					//activationMsg = smsReportDAO.getCircleWiseActCount(circleCode,rptDate,reporTypeFlag);
					if(activationMsg != null || "".equals(activationMsg)){
						commonMessage = true;	
					}

				}  *//** Get Service category wise activation count **//*  
				else if((SMSReportConfigInitializer.SERVICE_CLASS_WISE_ACT_COUNT_FOR_FTD).equals(reportId)) {
					ArrayList scIdCodeList = smsReportDAO.getSCIdCircleCode(rptDate);
					
					if(scIdCodeList != null && !scIdCodeList.isEmpty()){
						SMSReportDTO smsReportDTO = null;
						for(int index = 0,size =scIdCodeList.size(); index < size; index++ ){
							smsReportDTO = (SMSReportDTO)scIdCodeList.get(index);
							activationMsg = smsReportDAO.getActServiceClassWise(smsReportDTO.getServiceClassId(),smsReportDTO.getCircleCode1(),rptDate,reporTypeFlag);
							if(activationMsg != null || "".equals(activationMsg)){
								commonMessage = true;	
							}
						}
						
						
					}
					

				} *//** For all user-wise reports, get report for each user **//*		    
				else {

					for(int index = 0, size = userInfoList.size() ; index < size ; index++){

						BusinessUserDTO   businessUserDTO = (BusinessUserDTO) userInfoList.get(index);

						*//** Get distributors wise activation pending **//*

						if(reportId.equals(SMSReportConfigInitializer.ACT_PENDING_AT_DIST_FOR_FTD)){

							activationMsg = smsReportDAO.getDistPendingActCount(businessUserDTO.getRegNumber(), rptDate, reporTypeFlag);

						} 
						*//** Get distributors wise daily / monthly activation count **//*
						else if((reportId.equals(SMSReportConfigInitializer.DIST_WISE_ACT_COUNT_FOR_FTD)) 
								|| reportId.equals(SMSReportConfigInitializer.DIST_WISE_ACT_COUNT_MTD) 
								|| reportId.equals(SMSReportConfigInitializer.ACT_DONE_AT_DIST_FOR_FTD)){

							//activationMsg = smsReportDAO.getDistActCount(businessUserDTO,businessUserDTO.getRegNumber(), rptDate, reporTypeFlag);

						}  *//** Get dealer wise daily / monthly activation count **//* 
						else if(reportId.equals(SMSReportConfigInitializer.DEALER_WISE_ACT_COUNT_FOR_FTD) 
								|| reportId.equals(SMSReportConfigInitializer.DEALER_WISE_ACT_COUNT_MTD)){

							//activationMsg = smsReportDAO.getDealerActCount(businessUserDTO.getRegNumber(), rptDate, reporTypeFlag);

						} 

						*//** Set individual messages in BusinessUserDTO **//*	
						businessUserDTO.setSmsMessage(activationMsg);
					}

				}

			}


			*//** if commonMessage = true, then send commone message to all users **//*

			if(commonMessage == true){
				*//** Loop through all users and Send sms messages. **//* 
				for(int index = 0, size = userInfoList.size() ; index < size ; index++){
					BusinessUserDTO   businessUserDTO = (BusinessUserDTO) userInfoList.get(index);

					String registeredNo = businessUserDTO.getRegNumber();

					log.debug(" HUB/Circle/HBO Wise Activation Count : "+activationMsg);

					*//** send the activationMsg  to all users at their respective registeredNo. **//*
					SendSMS.sendSMS(registeredNo, activationMsg);

				}
			} *//** else get businessUserDTO.getSmsMessage() to send user's message to that user. **//* 
			else {
				for(int index = 0, size = userInfoList.size() ; index < size ; index++){
					BusinessUserDTO   businessUserDTO = (BusinessUserDTO) userInfoList.get(index);
					String registeredNo = businessUserDTO.getRegNumber();
					activationMsg  = businessUserDTO.getSmsMessage();
					if(activationMsg != null){
						log.debug(" Activation Count for report Wise User : "+activationMsg);

						*//** send the activationMsg  to all users at their respective registeredNo. **//*
						SendSMS.sendSMS(registeredNo, activationMsg);
					}
				}

			}

		}catch(DAOException de){
			*//** DAOException is already being handled in catch block of SMSReportDAO **//*
		} catch(Exception e){
			log.error(Constants.SMS_COMMON_MESAGE,e);
		}
		log.info(" Exiting from pushSMSReport() method of SMSReportService class");

	}*/
	
	
	/*
	public  void  pushNewSMSReport(String reportId,String circleCode,String reportCode,String smskeyWord){

		ArrayList userRoleList = new ArrayList();
		ArrayList userInfoList = new ArrayList();
		String activationMsg = null;
		StringBuffer stringBuffer =null;
		try{
			log.info(" Entering in pushNewSMSReport() method of SMSReportService class");
			userRoleList = smsReportDAO.getNewUsersByRtIdCode(reportId,circleCode);

			/** Get the Users List for sending Push reports. **  

			userInfoList = distPortalDaoImpl.getUserInfoByRole(userRoleList,circleCode);

			log.debug("userInfoList == "+userInfoList);

			/** If no users are availalble, exit the push report. * 

			if(userInfoList == null || userInfoList.isEmpty()){
				stringBuffer = new StringBuffer().append("No user available for report id ")
				.append(reportId)
				.append(" , and Circle code ").append(circleCode);

				log.debug(stringBuffer.toString());
				activationMsg = stringBuffer.toString();

				/** No users to send report, hence return. * 
				return;				
			}else {
				if(smskeyWord.equals(SMSReportConfigInitializer.FIRST_SC) ||
						reportCode.equals(SMSReportConfigInitializer.SECOND_SC) ||
						reportCode.equals(SMSReportConfigInitializer.THIRD_SC)){
					/** for service class reports * 
					ArrayList scIdCodeList = smsReportDAO.getNewSCIdCircleCode();
				System.out.println(" Before SC report ");
					if(scIdCodeList != null && !scIdCodeList.isEmpty()){
						SMSReportDTO smsReportDTO = new SMSReportDTO();
						SMSReportRequestDTO smsReqDTO =new SMSReportRequestDTO();
						System.out.println(" Inside  SC report ");
						for(int index = 0,size =scIdCodeList.size(); index < size; index++ ){
							smsReportDTO = (SMSReportDTO)scIdCodeList.get(index);
							smsReqDTO.setServiceClassId(smsReportDTO.getServiceClassId());
							
							for(int index1 = 0, size1 = userInfoList.size() ; index1 < size1 ; index++){
								BusinessUserDTO   businessUserDTO = (BusinessUserDTO) userInfoList.get(index);
								activationMsg = getLatestActServiceClassWise(smsReqDTO,businessUserDTO,reportCode);
								/** Set individual messages in BusinessUserDTO * 
								businessUserDTO.setSmsMessage("SC Report => "+activationMsg);
							}
						}
					}
	
				}else if(smskeyWord.equals(SMSReportConfigInitializer.PENDING)){
					/** for pending reports * 
					for(int index = 0, size = userInfoList.size() ; index < size ; index++){
						BusinessUserDTO   businessUserDTO = (BusinessUserDTO) userInfoList.get(index);
						activationMsg = getPendingReports(null,businessUserDTO,reportCode);
						/** Set individual messages in BusinessUserDTO * 
						businessUserDTO.setSmsMessage("Pending Report => "+activationMsg);
					}
				}else {
					/** for activation reports * 
					for(int index = 0, size = userInfoList.size() ; index < size ; index++){
						BusinessUserDTO   businessUserDTO = (BusinessUserDTO) userInfoList.get(index);
						activationMsg = getLatestPullReport(null,businessUserDTO,reportCode);
						/** Set individual messages in BusinessUserDTO * 
						businessUserDTO.setSmsMessage("Activation Report => "+activationMsg);
					}
					log.debug(" SMS Message : "+activationMsg);

					} 
				}	
				

			   /** get businessUserDTO.getSmsMessage() to send user's message to that user. * 
			
				for(int index = 0, size = userInfoList.size() ; index < size ; index++){
					BusinessUserDTO   businessUserDTO = (BusinessUserDTO) userInfoList.get(index);
					String registeredNo = businessUserDTO.getRegNumber();
					activationMsg  = businessUserDTO.getSmsMessage();
					if(activationMsg != null){
						log.debug(" Activation Count for report Wise User : "+activationMsg);
						System.out.println("circleCode "+circleCode+" ,Role Id "+businessUserDTO.getTypeOfUserId()+","+reportCode+","+registeredNo+" Message =========== : "+activationMsg);
						/** send the activationMsg  to all users at their respective registeredNo. * 
						//SendSMS.sendSMS(registeredNo, activationMsg);
					}
				}

			

		}catch(DAOException de){
			/** DAOException is already being handled in catch block of SMSReportDAO * 
		} catch(Exception e){
			log.error(Constants.SMS_COMMON_MESAGE,e);
		}
		log.info(" Exiting from pushSMSReport() method of SMSReportService class");

	}
*/	
	
/*
	/**
	 * 
	 *	This method returns the business user wise activation count 
	 * @param businessUserDTO contains user info
	 * @param registeredNo is the requester registered number
	 * @param reportId is the requested Report 
	 * @return total activation done by specific business users.
	 *


	public String getReportCount(SMSReportRequestDTO requestDTO,BusinessUserDTO businessUserDTO,int rptTypeFlag) throws DAOException{

		String activationMsg = null;
		//String circleCode = businessUserDTO.getCircleCode();
		//String requesterRegNo = requestDTO.getMobileNo();
		String hubCode = businessUserDTO.getHubCode();
		String reportCode= requestDTO.getReportCode();
		String rptDate = requestDTO.getRptDate();
		String dealerRegNo = requestDTO.getDealerRegNo();
		String distributorRegNo = requestDTO.getDistributorRegNo();
		String circleCode = requestDTO.getCircleCode();

		try{  
			log.info(" Entering in getReportCount() method of SMSReportService class");
            
			/** Get distributors wise daily / monthly activation count **
			if((reportCode.equals(SMSReportConfigInitializer.DIST_WISE_ACT_COUNT_FOR_FTD)) 
					|| reportCode.equals(SMSReportConfigInitializer.DIST_WISE_ACT_COUNT_MTD) 
					|| reportCode.equals(SMSReportConfigInitializer.ACT_DONE_AT_DIST_FOR_FTD)) {
				
				if(smsReportDAO.isValidRegNo(distributorRegNo, "distributor")){
					//activationMsg = smsReportDAO.getDistActCount(businessUserDTO,distributorRegNo,rptDate,rptTypeFlag);
				}else {
					activationMsg ="Distributor/FOS registered number does not exist or may be inactive.";		
				}
				
			}/** Get dealer wise daily / monthly activation count ** 
			else if(reportCode.equals(SMSReportConfigInitializer.DEALER_WISE_ACT_COUNT_FOR_FTD) 
					|| reportCode.equals(SMSReportConfigInitializer.DEALER_WISE_ACT_COUNT_MTD)){

				if(smsReportDAO.isValidRegNo(dealerRegNo, "dealer")){
					//activationMsg = smsReportDAO.getDealerActCount(dealerRegNo,rptDate,rptTypeFlag);
				}else {
					activationMsg ="Dealer registered number does not exist or may be inactive.";		
				}
                	

			}/** Get Hub-wise daily or monthly report ** 
			else if(reportCode.equals(SMSReportConfigInitializer.HUB_WISE_ACT_COUNT_FOR_FTD) 
					|| reportCode.equals(SMSReportConfigInitializer.HUB_WISE_ACT_COUNT_MTD)){

				//activationMsg = smsReportDAO.getHubWiseActCount(hubCode,rptDate,rptTypeFlag);

			}/** Get Circle-wise daily or monthly report **
			else if(reportCode.equals(SMSReportConfigInitializer.CIRCLE_WISE_ACT_COUNT_FOR_FTD) 
					|| reportCode.equals(SMSReportConfigInitializer.CIRCLE_WISE_ACT_COUNT_MTD)){
				
				if(!smsReportDAO.isCircleExist(circleCode)){
					activationMsg = "Circle code does not exist or may be inactive.";
				} else {
					//activationMsg = smsReportDAO.getCircleWiseActCount(circleCode,rptDate,rptTypeFlag);
				}
	
			}  /** Get distributors wise activation pending ** 
			else if(reportCode.equals(SMSReportConfigInitializer.ACT_PENDING_AT_DIST_FOR_FTD)){

				if(smsReportDAO.isValidRegNo(distributorRegNo, "distributor")){
					
				activationMsg = smsReportDAO.getDistPendingActCount(distributorRegNo, rptDate, rptTypeFlag);
				}else {
					activationMsg ="Distributor registered number does not exist or may be inactive.";		
				}
				
			} else {
				activationMsg ="NO Report exist for "+reportCode+" Report code ";
			}

		} catch(DAOException de){
			log.error(de.getMessage(), de);
			throw new DAOException(Constants.SMS_COMMON_MESAGE, de);
		}
		log.info(" Exiting from getReportCount() method of SMSReportService class");
		return activationMsg;
	}
	
	public boolean validateLatestRptCodeAndRequesterNo(SMSReportRequestDTO requestDTO) {
		if(requestDTO == null) {
			return false;
		}
		String requestorMobileNo =requestDTO.getMobileNo();
		if(requestorMobileNo == null || "".equals(requestorMobileNo)) {
			requestDTO.setErrMsg("Please provide requester-registered number.");
			return false;
		}
		
		/**  Validate requestor  mobile no **
       
		if(requestorMobileNo != null){
			int msisdnLength = requestorMobileNo.length();
			int intMinMSISDNLength = Integer.parseInt(SMSReportConfigInitializer.MIN_MOBILE_NO_LENGTH);
			int intMaxMSISDNLength = Integer.parseInt(SMSReportConfigInitializer.MAX_MOBILE_NO_LENGTH);
			Pattern pattern = Pattern.compile("^\\d+$");
			if (!pattern.matcher(requestorMobileNo).matches()) {
				requestDTO.setErrMsg(SMSReportConfigInitializer.REQUESTOR_MSISDN_ONLY_NUMERIC_MSG);
				return false;
			} 
			if (msisdnLength < intMinMSISDNLength || msisdnLength > intMaxMSISDNLength) {
				requestDTO.setErrMsg(SMSReportConfigInitializer.REQUESTOR_REG_NO_LENGTH_ERROR_MSG);
				return false;
			} 
			
		}
		/**  Check if ReportId / report code is valid **
		String message = requestDTO.getMessage();
		if(message == null || "".equals(message)) {
			requestDTO.setErrMsg("Kindly provide FTA SMS report code.");
			return false;
		} else {
			message = message.trim();
			String[] result = message.split("\\s");
			String reportCode = null;
			int msgLength = result.length;
			if(msgLength > 0) {
				reportCode = result[0].trim().toUpperCase();
				String allreports = SMSReportConfigInitializer.NEW_SMS_REPORTS_TYPE;
				String reports[] = allreports.split(",");
				boolean reportExists = false;
				for (int i=0; i < reports.length; i++) {
					if(reports[i].trim().equals(reportCode)) {
						reportExists = true;					
						requestDTO.setReportCode(reportCode);
						break;
					}
				}
				if(!reportExists) {
					requestDTO.setErrMsg("Invalid FTA SMS Report code.");
					return false;
				}
			   
			}
			
		}
		return true;
	}	
	
	public String validateLatestSMSReportRequest(SMSReportRequestDTO requestDTO,BusinessUserDTO businessUserDTO) {
		
		
		String message = requestDTO.getMessage();
		message = message.trim();
		Pattern pattern1 = Pattern.compile("\\s+");
		Matcher matcher = pattern1.matcher(message);
		message = matcher.replaceAll(" ");
		
		String[] result = message.split("\\s");
		String reportCode = null;
		int msgLength = result.length;
		String reportExists = Constants.N;
		if(msgLength > 0) {
			
			reportCode = result[0].trim().toUpperCase();
			String allreports = SMSReportConfigInitializer.NEW_SMS_REPORTS_TYPE;
			String reports[] = allreports.split(",");
			
			for (int i=0; i < reports.length; i++) {
				if(reports[i].trim().equals(reportCode)) {
					reportExists = Constants.Y;					
					requestDTO.setReportCode(reportCode);
					break;
				}
			}
			if(reportExists.equalsIgnoreCase("N")) {
				requestDTO.setErrMsg("Invalid FTA SMS Report code.");
				reportExists = Constants.N;
			}
		}    
		
		/** To check whether FOS and dealer and ed has access to activation reports ******* 
		if(businessUserDTO.getTypeOfUserId()==Constants.FOS){
			if(requestDTO.getReportCode().equalsIgnoreCase(SMSReportConfigInitializer.FIRST_TT)
					|| requestDTO.getReportCode().equalsIgnoreCase(SMSReportConfigInitializer.SECOND_TT)){
				reportExists = Constants.Y;	
			}if(requestDTO.getReportCode().equalsIgnoreCase(SMSReportConfigInitializer.FIRST_SC)
					|| requestDTO.getReportCode().equalsIgnoreCase(SMSReportConfigInitializer.SECOND_SC)){
				if(msgLength != 2) {
					requestDTO.setErrMsg("Invalid FTA Message Format.");
					reportExists = Constants.N;
				}else{
					requestDTO.setServiceClassId(result[1]);
					reportExists = Constants.Y;	
				}
			}
		}else if(businessUserDTO.getTypeOfUserId()==Constants.DEALER){
			if(requestDTO.getReportCode().equalsIgnoreCase(SMSReportConfigInitializer.FIRST_TT)){
				reportExists = Constants.Y;	
			}if(requestDTO.getReportCode().equalsIgnoreCase(SMSReportConfigInitializer.FIRST_SC)){
				if(msgLength != 2) {
					requestDTO.setErrMsg("Invalid FTA Message Format.");
					reportExists = Constants.N;
				}else{
					requestDTO.setServiceClassId(result[1]);
					reportExists = Constants.Y;	
				}
			}
		}else if(businessUserDTO.getTypeOfUserId()==Constants.ED){
			if(requestDTO.getReportCode().equalsIgnoreCase(SMSReportConfigInitializer.FIRST_TT)
					|| requestDTO.getReportCode().equalsIgnoreCase(SMSReportConfigInitializer.SECOND_TT)){
				reportExists = Constants.Y;	
			}else if(requestDTO.getReportCode().equalsIgnoreCase(SMSReportConfigInitializer.FIRST_SC)
					|| requestDTO.getReportCode().equalsIgnoreCase(SMSReportConfigInitializer.SECOND_SC)){
				if(msgLength != 2) {
					requestDTO.setErrMsg("Invalid FTA Message Format.");
					reportExists = Constants.N;
				}else{
					requestDTO.setServiceClassId(result[1]);
					reportExists = Constants.Y;	
				}
			}
		
		}else if(businessUserDTO.getTypeOfUserId()==Constants.CEO || businessUserDTO.getTypeOfUserId()==Constants.SALES_HEAD ||
				businessUserDTO.getTypeOfUserId()==Constants.ZBM || businessUserDTO.getTypeOfUserId()==Constants.ZSM 
				|| businessUserDTO.getTypeOfUserId()==Constants.TM || businessUserDTO.getTypeOfUserId()==Constants.DISTIBUTOR){
			
			if(requestDTO.getReportCode().equalsIgnoreCase(SMSReportConfigInitializer.FIRST_TT)
					|| requestDTO.getReportCode().equalsIgnoreCase(SMSReportConfigInitializer.SECOND_TT)
					|| requestDTO.getReportCode().equalsIgnoreCase(SMSReportConfigInitializer.THIRD_TT)){
				reportExists = Constants.Y;	
			}else if(requestDTO.getReportCode().equalsIgnoreCase(SMSReportConfigInitializer.FIRST_SC)
					|| requestDTO.getReportCode().equalsIgnoreCase(SMSReportConfigInitializer.SECOND_SC)
					|| requestDTO.getReportCode().equalsIgnoreCase(SMSReportConfigInitializer.THIRD_SC)){
				if(msgLength != 2) {
					requestDTO.setErrMsg("Invalid FTA Message Format.");
					reportExists = Constants.N;
				}else{
					requestDTO.setServiceClassId(result[1]);
					reportExists = Constants.Y;	
				}
				
			}
		}
		
		/** To check whether FOS and dealer and ED has access to service class reports ******* 
		
		
		/** To check ED,CEO,SALES HEAD,ZBM,ZSM have not requested for pending reports ******* 
			if(requestDTO.getReportCode().equalsIgnoreCase(SMSReportConfigInitializer.PENDING)){
				if(businessUserDTO.getTypeOfUserId()==Constants.ED ||
						businessUserDTO.getTypeOfUserId()==Constants.CEO ||
						businessUserDTO.getTypeOfUserId()==Constants.SALES_HEAD ||
						businessUserDTO.getTypeOfUserId()==Constants.ZBM ||
						businessUserDTO.getTypeOfUserId()==Constants.ZSM ||
						businessUserDTO.getTypeOfUserId()==Constants.DEALER){
					reportExists = Constants.N;
				}else{
					reportExists = Constants.Y;	
				}
			}
		
			
		/** To check ED,CEO,SALES HEAD,ZBM,ZSM have not requested for subscriber status reports ******* 
			if(requestDTO.getReportCode().equalsIgnoreCase(SMSReportConfigInitializer.SUB_STATUS)){
				if(businessUserDTO.getTypeOfUserId()==Constants.ED ||
						businessUserDTO.getTypeOfUserId()==Constants.CEO ||
						businessUserDTO.getTypeOfUserId()==Constants.SALES_HEAD ||
						businessUserDTO.getTypeOfUserId()==Constants.ZBM ||
						businessUserDTO.getTypeOfUserId()==Constants.ZSM){
					reportExists = Constants.N;
				}else{
					if(msgLength != 2) {
						requestDTO.setErrMsg("Invalid FTA Message Format.");
						reportExists = Constants.N;
					}else{
						requestDTO.setSubsMSISDN(result[1]);
						reportExists = Constants.Y;	
					}
				}
			}
		
		String subMSISDN = requestDTO.getSubsMSISDN();
		String serviceClassId = requestDTO.getServiceClassId();

		/** Check Service class id **
		if(serviceClassId != null) {
			Pattern pattern = Pattern.compile("^\\d+$");
			if(!pattern.matcher(serviceClassId).matches()){
				requestDTO.setErrMsg(SMSReportConfigInitializer.SERVICE_CLASS_ID_ERROR_MSG);
				reportExists = Constants.N;
			} else if(serviceClassId.length() > Integer.parseInt(SMSReportConfigInitializer.SERVICE_CLASS_ID_MAX_LENGTH)){
				requestDTO.setErrMsg(SMSReportConfigInitializer.SERVICE_CLASS_ID_MAX_LENGTH_ERROR_MSG);
				reportExists = Constants.N;
			} 
			reportExists = Constants.Y;	
		}
		
		
		int intMinMSISDNLength = Integer.parseInt(SMSReportConfigInitializer.MIN_MOBILE_NO_LENGTH);
		int intMaxMSISDNLength = Integer.parseInt(SMSReportConfigInitializer.MAX_MOBILE_NO_LENGTH);
	
		if(subMSISDN != null){
			int msisdnLength = subMSISDN.length();
			Pattern pattern = Pattern.compile("^\\d+$");
			if (!pattern.matcher(subMSISDN).matches()) {
				requestDTO.setErrMsg(SMSReportConfigInitializer.SUBS_MSISDN_ONLY_NUMERIC_MSG);
				reportExists = Constants.N;
			} 
			if (msisdnLength < intMinMSISDNLength || msisdnLength > intMaxMSISDNLength) {
				requestDTO.setErrMsg(SMSReportConfigInitializer.SUBS_MSISDN_LENGTH_ERROR_MSG);
				reportExists = Constants.N;
			} 
			reportExists = Constants.Y;	
		}
		return reportExists;
	}
	
	public String verifyNewRequestorPermission(BusinessUserDTO businessUserDTO,String reportCode){

		String smsKeyword = Constants.NO_PERM;
		try {
			smsKeyword = smsReportDAO.verifyNewRequestorPermission(businessUserDTO, reportCode);
		} catch (DAOException e) {
			String msg = Constants.SMS_COMMON_MESAGE;
			log.error(msg,e);
		} 
		return smsKeyword;
	}


	public String getLatestReportCount(SMSReportRequestDTO requestDTO,BusinessUserDTO businessUserDTO,String smsKeyword) throws DAOException{
		log.info(" Entering in getNewReportCount() method of SMSReportService class");
		String activationMsg = null;
		String hubCode = businessUserDTO.getHubCode();
		String circleCode = businessUserDTO.getCircleCode();
		int locId = businessUserDTO.getLocId();
		int userId = businessUserDTO.getUserId();
		try{  
			if(smsKeyword.equalsIgnoreCase(resourceBundle.getString(SMSReportConfigInitializer.HUB_TOTAL_SALES))){
				//hub wise activation count
				activationMsg = smsReportDAO.getTotalSaleForHub(hubCode);
				
			}else if(smsKeyword.equalsIgnoreCase(resourceBundle.getString(SMSReportConfigInitializer.HUB_CIRCLE_TOTAL_SALES))){
				
				activationMsg = smsReportDAO.getCircleWiseTotalSale(hubCode);
				
			}else if(smsKeyword.equalsIgnoreCase(resourceBundle.getString(SMSReportConfigInitializer.CEO_TOTAL_SALES))){
				
				activationMsg = smsReportDAO.getTotalSaleForCircle(circleCode);
				
			} else if(smsKeyword.equalsIgnoreCase(resourceBundle.getString(SMSReportConfigInitializer.CEO_ZONE_TOTAL_SALES))){
				
				activationMsg = smsReportDAO.getZoneWiseTotalSale(circleCode);
				
				
			}else if(smsKeyword.equalsIgnoreCase(resourceBundle.getString(SMSReportConfigInitializer.CEO_ZBM_TOTAL_SALES))){
			
				activationMsg = smsReportDAO.getZBMWiseTotalSale(circleCode);
				
			}else if(smsKeyword.equalsIgnoreCase(resourceBundle.getString(SMSReportConfigInitializer.SH_TOTAL_SALES))){
				
				activationMsg = smsReportDAO.getTotalSaleForCircle(circleCode);
				
			}else if(smsKeyword.equalsIgnoreCase(resourceBundle.getString(SMSReportConfigInitializer.SH_ZONE_TOTAL_SALES))){
				
				activationMsg = smsReportDAO.getZoneWiseTotalSale(circleCode);
				
			}else if(smsKeyword.equalsIgnoreCase(resourceBundle.getString(SMSReportConfigInitializer.SH_ZBM_TOTAL_SALES))){
				
				activationMsg = smsReportDAO.getZBMWiseTotalSale(circleCode);
				
			}else if(smsKeyword.equalsIgnoreCase(resourceBundle.getString(SMSReportConfigInitializer.ZBM_TOTAL_SALES))){
			
				activationMsg = smsReportDAO.zbmGetTotalSaleForZone(userId , locId);
				
				
			}else if(smsKeyword.equalsIgnoreCase(resourceBundle.getString(SMSReportConfigInitializer.ZBM_ZSM_TOTAL_SALES))){
				
				activationMsg = smsReportDAO.getZSMWiseTotalSale(userId, locId);
				
			}else if(smsKeyword.equalsIgnoreCase(resourceBundle.getString(SMSReportConfigInitializer.ZBM_TM_TOTAL_SALES))){
				
				activationMsg = smsReportDAO.zbmGetTMWiseTotalSale(userId, locId);
				
			}else if(smsKeyword.equalsIgnoreCase(resourceBundle.getString(SMSReportConfigInitializer.ZSM_TOTAL_SALES))){
				
				activationMsg = smsReportDAO.zsmGetTotalSaleForZone(userId , locId);
				
			}else if(smsKeyword.equalsIgnoreCase(resourceBundle.getString(SMSReportConfigInitializer.ZSM_TM_TOTAL_SALES))){
				
				activationMsg = smsReportDAO.getTMWiseTotalSale(userId, locId);
				
			}else if(smsKeyword.equalsIgnoreCase(resourceBundle.getString(SMSReportConfigInitializer.ZSM_DIST_TOTAL_SALES))){
				
				activationMsg = smsReportDAO.zsmGetDistWiseTotalSale(userId, locId);
				
			}else if(smsKeyword.equalsIgnoreCase(resourceBundle.getString(SMSReportConfigInitializer.TM_TOTAL_SALES))){
				
				
				activationMsg = smsReportDAO.tmGetTotalSaleForCity(userId, locId);
				
			}else if(smsKeyword.equalsIgnoreCase(resourceBundle.getString(SMSReportConfigInitializer.TM_DIST_TOTAL_SALES))){
				
				activationMsg = smsReportDAO.getDistWiseTotalSale(userId, locId);
				
				
			}else if(smsKeyword.equalsIgnoreCase(resourceBundle.getString(SMSReportConfigInitializer.TM_FOS_TOTAL_SALES))){
				
				activationMsg = smsReportDAO.tmGetFOSWiseTotalSale(userId, locId);
				
			}else if(smsKeyword.equalsIgnoreCase(resourceBundle.getString(SMSReportConfigInitializer.DIST_TOTAL_SALES))){
				
				activationMsg = smsReportDAO.distGetTotalSaleForCity(userId, locId);
				
			}else if(smsKeyword.equalsIgnoreCase(resourceBundle.getString(SMSReportConfigInitializer.DIST_FOS_TOTAL_SALES))){
				
				activationMsg = smsReportDAO.getFOSWiseTotalSale(userId, locId);
				
			}else if(smsKeyword.equalsIgnoreCase(resourceBundle.getString(SMSReportConfigInitializer.DIST_DLR_TOTAL_SALES))){
				
				activationMsg = smsReportDAO.distGetDealerWiseTotalSale(userId, locId);
			
			}else if(smsKeyword.equalsIgnoreCase(resourceBundle.getString(SMSReportConfigInitializer.FOS_TOTAL_SALES))){
				
				activationMsg = smsReportDAO.fosGetTotalSaleForCity(userId, locId);
				
	
			}else if(smsKeyword.equalsIgnoreCase(resourceBundle.getString(SMSReportConfigInitializer.FOS_DLR_TOTAL_SALES))){
				
				activationMsg = smsReportDAO.getDealerWiseTotalSale(userId, locId);
				
			}else if(smsKeyword.equalsIgnoreCase(resourceBundle.getString(SMSReportConfigInitializer.DLR_TOTAL_SALES))){
				
				activationMsg = smsReportDAO.retailerGetTotalSaleForCity(userId, locId);
			} else {
				
				log.debug("Report keyword is incorrect.");
			}



		} catch(DAOException de){
			log.error(de.getMessage(), de);
			throw new DAOException(Constants.SMS_COMMON_MESAGE, de);
		}
		log.info(" Exiting from getReportCount() method of SMSReportService class");
		return activationMsg;
	}
	
	public String getLatestActServiceClassWise(SMSReportRequestDTO requestDTO,BusinessUserDTO
		 bizDTO,String smsKeyword){
		String reportMsg = "";
		String serviceClassId = requestDTO.getServiceClassId();
		//String strDate = requestDTO.getRptDate();
		try{
			log.info(" Entering in getActServiceClassWise() method of SMSReportService class");
			
			 String serviceClassName = new ServiceClassDAOImpl().getServiceClassNameById(Integer.parseInt(serviceClassId));
			
			 if(null == serviceClassName ){
				 reportMsg = "Service class id does not exist.";
			 } else {
				reportMsg = getLatestActServiceClassWiseReport(requestDTO,bizDTO,smsKeyword);
			} 
			
		}catch (DAOException e) {
			reportMsg = Constants.SMS_COMMON_MESAGE;
			log.error("Error occurred " + e.getMessage(),e);
		}
		catch (Exception e) {
			log.error("Error occurred " + e.getMessage(),e);
			reportMsg = Constants.SMS_COMMON_MESAGE;
		} 

		log.info(" Exiting from  getActServiceClassWise() method of SMSReportService class");
		return reportMsg;
	}
	
	
	public String getPendingReports(SMSReportRequestDTO requestDTO,BusinessUserDTO
			 bizDTO,String smsKeyword){
	String smsMessage = "";
		try{
			if(smsKeyword.equalsIgnoreCase(resourceBundle.getString(SMSReportConfigInitializer.TM_PENDING_REPORT))){
				/** to get pending report when TM requested **
				smsMessage =  smsReportDAO.getTMPendingReports( bizDTO.getUserId(),bizDTO.getLocId());   
				
			}else if(smsKeyword.equalsIgnoreCase(resourceBundle.getString(SMSReportConfigInitializer.DIST_PENDING_REPORT))){
				/** to get pending report when distributor requested **
				smsMessage =  smsReportDAO.getDistPendingReports( bizDTO.getUserId(),bizDTO.getLocId());   
				
			}else if(smsKeyword.equalsIgnoreCase(resourceBundle.getString(SMSReportConfigInitializer.FOS_PENDING_REPORT))){
				/** to get pending report when FOS requested **
				smsMessage =  smsReportDAO.getFOSPendingReports( bizDTO.getUserId(),bizDTO.getLocId());   
				
			}
		}catch (DAOException e) {
			smsMessage = Constants.SMS_COMMON_MESAGE;
			log.error("Error occurred " + e.getMessage(),e);
		}catch (Exception e) {
			log.error("Error occurred " + e.getMessage(),e);
			smsMessage = Constants.SMS_COMMON_MESAGE;
		} 
		log.info(" Exiting from  getPendingReports() method of SMSReportService class");
		return smsMessage;
	}

	public String  getLatestActStatusForSubs(SMSReportRequestDTO requestDTO,BusinessUserDTO
			 bizDTO,String smsKeyword){
		String subsMSISDN = requestDTO.getSubsMSISDN();
		String smsMessage = null;
		try{
			boolean isMSISDNExists =smsReportDAO.getActiveMSISDNList(subsMSISDN);
			
			if(isMSISDNExists){
				
				smsMessage =  smsReportDAO.getSuscriberStatus(subsMSISDN,bizDTO.getCircleCode());  
				
			} else {
				smsMessage =  new StringBuffer(500)
				.append("Transaction has not been initiated for ")
				.append(subsMSISDN).append(" subscriber ").toString();
				log.debug(smsMessage);
			}	
			
		}catch (Exception e) {
			log.error("Error occurred " + e.getMessage(),e);
			subsMSISDN = Constants.SMS_COMMON_MESAGE;
		} 
		log.info(" Exiting from getActStatusForSubs() method of SMSReportService class");
		return smsMessage;
	}
	
	
	public String getLatestActServiceClassWiseReport(SMSReportRequestDTO requestDTO,BusinessUserDTO businessUserDTO,String smsKeyword) throws DAOException{
		log.info(" Entering in getLatestActServiceClassWiseReport() method of SMSReportService class");
		String activationMsg = "";
		String hubCode = businessUserDTO.getHubCode();
		String circleCode = businessUserDTO.getCircleCode();
		int userId = businessUserDTO.getUserId();
		int locId = businessUserDTO.getLocId();
		String scId = requestDTO.getServiceClassId();
		
		try{  
			if(smsKeyword.equalsIgnoreCase(resourceBundle.getString(SMSReportConfigInitializer.SC_HUB_TOTAL_SALES))){
				
				activationMsg = smsReportDAO.edGetTotalSaleHubForSC(hubCode,scId);
				
			}else if(smsKeyword.equalsIgnoreCase(resourceBundle.getString(SMSReportConfigInitializer.SC_HUB_CIRCLE_TOTAL_SALES))){
				
				activationMsg = smsReportDAO.edGetCircleWiseTotalSaleForSC(hubCode,scId);
				
			}else if(smsKeyword.equalsIgnoreCase(resourceBundle.getString(SMSReportConfigInitializer.SC_CEO_TOTAL_SALES))){
				
				activationMsg = smsReportDAO.ceoAndShoGetTotalSaleForSC(circleCode,scId);
				
			}else if(smsKeyword.equalsIgnoreCase(resourceBundle.getString(SMSReportConfigInitializer.SC_CEO_ZONE_TOTAL_SALES))){
				
				activationMsg = smsReportDAO.ceoAndShoGetTotalSaleForZoneForSC(circleCode,scId);
				
			}else if(smsKeyword.equalsIgnoreCase(resourceBundle.getString(SMSReportConfigInitializer.SC_CEO_ZBM_TOTAL_SALES))){
				
				activationMsg = smsReportDAO.ceoAndShoGetZBMWiseTotalSaleForSC(circleCode,scId);
				
			}else if(smsKeyword.equalsIgnoreCase(resourceBundle.getString(SMSReportConfigInitializer.SC_SH_TOTAL_SALES))){
				
				activationMsg = smsReportDAO.ceoAndShoGetTotalSaleForSC(circleCode,scId);
				
				
			}else if(smsKeyword.equalsIgnoreCase(resourceBundle.getString(SMSReportConfigInitializer.SC_SH_ZONE_TOTAL_SALES))){
				
				activationMsg = smsReportDAO.ceoAndShoGetTotalSaleForZoneForSC(circleCode,scId);
				
				
			}else if(smsKeyword.equalsIgnoreCase(resourceBundle.getString(SMSReportConfigInitializer.SC_SH_ZBM_TOTAL_SALES))){
				
				activationMsg = smsReportDAO.ceoAndShoGetZBMWiseTotalSaleForSC(circleCode,scId);
				
				
			}else if(smsKeyword.equalsIgnoreCase(resourceBundle.getString(SMSReportConfigInitializer.SC_ZBM_TOTAL_SALES))){
				
				activationMsg = smsReportDAO.zbmGetTotalSaleForZoneForSC(userId, locId, scId );	
				
			}else if(smsKeyword.equalsIgnoreCase(resourceBundle.getString(SMSReportConfigInitializer.SC_ZBM_ZSM_TOTAL_SALES))){
				
				activationMsg = smsReportDAO.zbmGetZSMWiseTotalSaleForSC(userId, locId, scId );				
				
			}else if(smsKeyword.equalsIgnoreCase(resourceBundle.getString(SMSReportConfigInitializer.SC_ZBM_TM_TOTAL_SALES))){
				
				activationMsg = smsReportDAO.zbmGetTMWiseTotalSaleForSC(userId, locId, scId);				
				
			}else if(smsKeyword.equalsIgnoreCase(resourceBundle.getString(SMSReportConfigInitializer.SC_ZSM_TOTAL_SALES))){
				
				activationMsg = smsReportDAO.zsmGetTotalSaleForZoneForSC(userId, locId, scId);
				
			}else if(smsKeyword.equalsIgnoreCase(resourceBundle.getString(SMSReportConfigInitializer.SC_ZSM_TM_TOTAL_SALES))){
				
				activationMsg = smsReportDAO.zsmGetTMWiseTotalSaleForSC(userId, locId, scId);
								
			}else if(smsKeyword.equalsIgnoreCase(resourceBundle.getString(SMSReportConfigInitializer.SC_ZSM_DIST_TOTAL_SALES))){
				
				activationMsg = smsReportDAO.zsmGetDistWiseTotalSaleForSC(userId, locId, scId);
								
			}else if(smsKeyword.equalsIgnoreCase(resourceBundle.getString(SMSReportConfigInitializer.SC_TM_TOTAL_SALES))){
				
				activationMsg = smsReportDAO.tmGetTotalSaleForCityForSC(userId, locId, scId);
				
			}else if(smsKeyword.equalsIgnoreCase(resourceBundle.getString(SMSReportConfigInitializer.SC_TM_DIST_TOTAL_SALES))){
				
				activationMsg = smsReportDAO.tmGetDistWiseTotalSaleForSC(userId, locId, scId);
				
			}else if(smsKeyword.equalsIgnoreCase(resourceBundle.getString(SMSReportConfigInitializer.SC_TM_FOS_TOTAL_SALES))){
				
				activationMsg = smsReportDAO.tmGetFOSWiseTotalSaleForSC(userId, locId, scId);
				
			}else if(smsKeyword.equalsIgnoreCase(resourceBundle.getString(SMSReportConfigInitializer.SC_DIST_TOTAL_SALES))){
				
				activationMsg = smsReportDAO.distGetTotalSaleForCityForSC(userId, locId, scId);
				
				
			}else if(smsKeyword.equalsIgnoreCase(resourceBundle.getString(SMSReportConfigInitializer.SC_DIST_FOS_TOTAL_SALES))){
				
				activationMsg = smsReportDAO.distGetFOSWiseTotalSaleForSC(userId, locId, scId);
				
			}else if(smsKeyword.equalsIgnoreCase(resourceBundle.getString(SMSReportConfigInitializer.SC_DIST_DLR_TOTAL_SALES))){
				
				activationMsg = smsReportDAO.distGetDealerWiseTotalSaleForSC(userId, locId, scId);
				
				
			}else if(smsKeyword.equalsIgnoreCase(resourceBundle.getString(SMSReportConfigInitializer.SC_FOS_TOTAL_SALES))){
				
				activationMsg = smsReportDAO.fosGetTotalSaleForCityForSC(userId, locId, scId);
				
				
			}else if(smsKeyword.equalsIgnoreCase(resourceBundle.getString(SMSReportConfigInitializer.SC_FOS_DLR_TOTAL_SALES))){
				
				activationMsg = smsReportDAO.fosGetDealerWiseTotalSaleForSC(userId, locId, scId);

			}else if(smsKeyword.equalsIgnoreCase(resourceBundle.getString(SMSReportConfigInitializer.SC_DLR_TOTAL_SALES))){
				
				activationMsg = smsReportDAO.dealerGetTotalSaleForCityForSC(userId, locId, scId);

			}

		} catch(Exception de){
			log.error(de.getMessage(), de);
			throw new DAOException(Constants.SMS_COMMON_MESAGE, de);
		}
		log.info(" Exiting from getLatestActServiceClassWiseReport() method of SMSReportService class");
		return activationMsg;
	}
    */
	
	
}
