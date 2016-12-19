/**************************************************************************
 * File     : SMSPullReportServlet.java
 * Author   : Aalok
 * Created  : july 22, 2008
 * Modified :  july 22, 20088
 * Version  : 1.0
 **************************************************************************
 *
 * Copyright @ 2002 This document has been prepared and written by 
 * IBM Global Services on behalf of Bharti, and is copyright of Bharti
 *
 **************************************************************************/
package com.ibm.virtualization.ussdactivationweb.actions;

import java.io.IOException;
import java.util.ArrayList;
import java.util.StringTokenizer;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.apache.struts.action.ActionError;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.actions.DispatchAction;
import org.apache.struts.util.LabelValueBean;

import com.ibm.core.exception.DAOException;
import com.ibm.virtualization.ussdactivationweb.beans.SmsPermissionBean;
import com.ibm.virtualization.ussdactivationweb.beans.UserDetailsBean;
import com.ibm.virtualization.ussdactivationweb.dao.SmsPermissionDaoImpl;
import com.ibm.virtualization.ussdactivationweb.dao.ViewEditCircleMasterDAOImpl;
import com.ibm.virtualization.ussdactivationweb.dto.SmsPermissionDto;
import com.ibm.virtualization.ussdactivationweb.utils.Constants;
import com.ibm.virtualization.ussdactivationweb.utils.USSDCommonUtility;

/**
 * @author Aalok Sharma
 *
 */
public class SmsPermissionAction extends DispatchAction {

	/** getting logger refrence **/
	private static final Logger logger = Logger.getLogger(SmsPermissionAction.class.toString()); 
	
	/**
	 * This method initialize the form with check boxes required.
	 * @param  mapping object of ActionMapping class that holds the action mapping information used to invoke a Struts action.
	 * @param  form object of ActionForm which is a JavaBean optionally associated with one or more ActionMappings
	 * @param  request object of HttpServletRequest which Wraps the standard request object to override some methods.
	 * @param  response object of HttpServletResponse which Wraps the standard response object to override some methods.
	 * 
	 * @exception IOException,Signals that an I/O exception of some sort has occurred.
	 * @exception ServletException,This exception is thrown to indicate a servlet problem.
	 */
	 
//	public ActionForward init(ActionMapping mapping, ActionForm form,
//			HttpServletRequest request, HttpServletResponse response)
//	throws Exception {
//		logger.info("Entering in init() method");
//		ActionErrors errors = new ActionErrors();
//		HttpSession session = request.getSession();
//		ActionForward forward = new ActionForward(); // return value
//		SmsPermissionBean smsPermissionBean = (SmsPermissionBean)form;
//		UserDetailsBean userBean = (UserDetailsBean)session.getAttribute(Constants.USER_INFO);
//		try {
//			smsPermissionBean.setBusinessUserList(null);
//			ArrayList circleList = new ArrayList();
//			if(userBean!=null){
//				if (userBean.getCircleId()== null){
//					circleList = USSDCommonUtility.getCircleList();
//				} else {
//					LabelValueBean lvBean = new LabelValueBean(userBean.getCircleId(),userBean.getCircleName());
//					smsPermissionBean.setCircleCode(userBean.getCircleId());
//					circleList.add(lvBean);
//				}
//			}
//			request.setAttribute(Constants.CIRCLE_LIST,circleList);
//			//setDefaultValues(smsPermissionBean);
//		} catch (DAOException e) {
//			logger.error("Exception occured in init() method : "+e);
//			errors.clear();
//			errors.add(Constants.GENERAL_ERROR,new ActionError(Constants.GENERAL_ERROR));
//			if (!errors.isEmpty()) {
//				saveErrors(request, errors);
//				forward=mapping.findForward(Constants.ERROR);
//			}
//		} 
//		forward=mapping.findForward(Constants.INIT);
//		logger.info("Exiting from init() method");
//		return  forward;
//	}


	
	/**
	 * This method gets the sms config data form the database.
	 * On the basis of ciecle id.
	 * 
	 * @param  mapping object of ActionMapping class that holds the action mapping information used to invoke a Struts action.
	 * @param  form object of ActionForm which is a JavaBean optionally associated with one or more ActionMappings
	 * @param  request object of HttpServletRequest which Wraps the standard request object to override some methods.
	 * @param  response object of HttpServletResponse which Wraps the standard response object to override some methods.
	 * 
	 * @exception IOException,Signals that an I/O exception of some sort has occurred.
	 * @exception ServletException,This exception is thrown to indicate a servlet problem.
	 */
	
	public ActionForward getSmsPermissionConfigData(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
	throws Exception {
		logger.debug("Entering in getSmsPermissionConfigData() method");
		ActionErrors errors = new ActionErrors();
		HttpSession session = request.getSession();
		ActionForward forward = new ActionForward(); // return value
		SmsPermissionDaoImpl smsPermissionDaoImpl = new SmsPermissionDaoImpl();
		SmsPermissionBean smsPermissionBean = (SmsPermissionBean)form; 
		UserDetailsBean userBean = (UserDetailsBean)session.getAttribute(Constants.USER_INFO);
		if(request.getSession().getAttribute(Constants.SUCCESS) != null){
			request.setAttribute(Constants.SUCCESS, request.getSession().getAttribute(Constants.SUCCESS));
			request.getSession().removeAttribute(Constants.SUCCESS);
		}
		
		try {
			smsPermissionBean.setBusinessUserList(null);
			ArrayList circleList = new ArrayList();
			if(userBean!=null){
				if (userBean.getCircleId()== null){
					circleList = ViewEditCircleMasterDAOImpl.getCircleList();
				} else {
					LabelValueBean lvBean = new LabelValueBean(userBean.getCircleId(),userBean.getCircleName());
					smsPermissionBean.setCircleCode(userBean.getCircleId());
					circleList.add(lvBean);
				}
			}
			request.setAttribute(Constants.CIRCLE_LIST,circleList);
			setDefaultValues(smsPermissionBean);
			smsPermissionBean.setSmsConfigData(smsPermissionDaoImpl.getSmsPermissionConfiguraton(smsPermissionBean.getCircleCode()));
			logger.info("Entered into getSmsPermissionConfigData"+userBean.getLoginId());
		} catch (DAOException e) {
			logger.error("Exception occured in getSmsPermissionConfigData() method : "+e);
			errors.clear();
			errors.add(Constants.GENERAL_ERROR,new ActionError(Constants.GENERAL_ERROR));
			if (!errors.isEmpty()) {
				saveErrors(request, errors);
				forward=mapping.findForward(Constants.ERROR);
			}
		} 
		forward=mapping.findForward(Constants.INIT);
		logger.debug("Exiting from getSmsPermissionConfigData() method");
		return  forward;
	}
	
	/**
	 * This method saves the configuration mapping into the database.
	 * 
	 * @param  mapping object of ActionMapping class that holds the action mapping information used to invoke a Struts action.
	 * @param  form object of ActionForm which is a JavaBean optionally associated with one or more ActionMappings
	 * @param  request object of HttpServletRequest which Wraps the standard request object to override some methods.
	 * @param  response object of HttpServletResponse which Wraps the standard response object to override some methods.
	 * 
	 * @exception IOException,Signals that an I/O exception of some sort has occurred.
	 * @exception ServletException,This exception is thrown to indicate a servlet problem.
	 */
	
	public ActionForward saveSmsPermissionConfigData(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
	throws Exception {
		logger.debug("Entering in saveSmsPermissionConfigData() method");
		ActionErrors errors = new ActionErrors();
		HttpSession session = request.getSession();
		ActionForward forward = new ActionForward(); // return value
		SmsPermissionDaoImpl smsPermissionDaoImpl = new SmsPermissionDaoImpl();
		SmsPermissionBean smsPermissionBean = (SmsPermissionBean)form; 
		UserDetailsBean userBean = (UserDetailsBean)session.getAttribute(Constants.USER_INFO);
		String[] smsConfig;		
		ArrayList smsPermissionDtoList =new ArrayList();
		try {
			smsPermissionBean.setBusinessUserList(null);
			ArrayList circleList = new ArrayList();
			if(userBean!=null){
				if (userBean.getCircleId()== null){
					circleList = ViewEditCircleMasterDAOImpl.getCircleList();
				} else {
					LabelValueBean lvBean = new LabelValueBean(userBean.getCircleId(),userBean.getCircleName());
					smsPermissionBean.setCircleCode(userBean.getCircleId());
					circleList.add(lvBean);
				}
			}
			request.setAttribute(Constants.CIRCLE_LIST,circleList);
			smsConfig = request.getParameterValues("smsConfigDataChk");
			String token1 = new String();
			String token2 = new String();
			StringTokenizer stringTokenizer;
			   if (smsConfig != null)  {
			      for (int i = 0; i < smsConfig.length; i++){
			    	  stringTokenizer= new StringTokenizer(smsConfig[i]);
			    	  if(stringTokenizer.hasMoreTokens()) {
			    		  token1 = stringTokenizer.nextToken("-");
			    	  }
			    	  if(stringTokenizer.hasMoreTokens()) {
			    		  token2 = stringTokenizer.nextToken();
			    	  }
			    	  SmsPermissionDto smsPermissionDto =new SmsPermissionDto();
			    	  smsPermissionDto.setSmsRequesterId(token1);
			    	  smsPermissionDto.setSmsReportId(token2);
			    	  smsPermissionDtoList.add(smsPermissionDto);
			      }
			      smsPermissionDaoImpl.saveSmsPermissionConfiguraton(smsPermissionBean.getCircleCode(),smsPermissionDtoList);
			      logger.info("Saved SmsPermissionConfigData"+userBean.getLoginId());
			   }
			   else {
				  smsPermissionDaoImpl.saveSmsPermissionConfiguraton(smsPermissionBean.getCircleCode(),smsPermissionDtoList);
				  logger.info("Saved SmsPermissionConfigData"+userBean.getLoginId());
			   }
			   request.getSession().setAttribute(Constants.SUCCESS, "Data Inserted Successfully!!!");
	   
		} catch (DAOException e) {
			logger.error("Exception occured in saveSmsPermissionConfigData() method : "+e);
			errors.clear();
			errors.add(Constants.GENERAL_ERROR,new ActionError(Constants.GENERAL_ERROR));
			if (!errors.isEmpty()) {
				forward=mapping.findForward(Constants.ERROR);
			}
		} 
		finally {
			saveErrors(request, errors);
		}
		forward=mapping.findForward(Constants.SUCCESS);
		logger.debug("Exiting from saveSmsPermissionConfigData() method");
		return  forward;
	}
	
	/**
	 * This method initialize the default values of the form
	 * 
	 * @param smsPermissionBean formbean object
	 * @throws com.ibm.virtualization.ussdactivationweb.dao.DAOException 
	 */

	private void setDefaultValues(SmsPermissionBean smsPermissionBean) throws com.ibm.virtualization.ussdactivationweb.utils.DAOException {
		SmsPermissionDaoImpl smsPermissionDaoImpl = new SmsPermissionDaoImpl();
		smsPermissionBean.setRequesterIds(smsPermissionDaoImpl.getAllRequesterIds());
		smsPermissionBean.setReportIds(smsPermissionDaoImpl.getAllReportIds());
	}
	
	
	public ActionForward init(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
	throws Exception {
		logger.info("Entering in init() method");
		ActionErrors errors = new ActionErrors();
		HttpSession session = request.getSession();
		ActionForward forward = new ActionForward(); // return value
		SmsPermissionBean smsPermissionBean = (SmsPermissionBean)form;
		UserDetailsBean userBean = (UserDetailsBean)session.getAttribute(Constants.USER_INFO);
		ViewEditCircleMasterDAOImpl circledao = new ViewEditCircleMasterDAOImpl();
		try {
			smsPermissionBean.setBusinessUserList(null);
			ArrayList circleList = new ArrayList();
			if(userBean!=null){
				if (userBean.getCircleId()== null){
					smsPermissionBean.setHubList(circledao.getHubList());
					smsPermissionBean.setUserRole(Constants.SUPER_ADMIN);
					if(!smsPermissionBean.getHubCode().equalsIgnoreCase("") || 
							!smsPermissionBean.getHubCode().equalsIgnoreCase("null")){
						smsPermissionBean.setCircleList(circledao
								.getCircleListByHub(smsPermissionBean.getHubCode()));
					}
				} else {
					LabelValueBean lvBean = new LabelValueBean(userBean.getCircleId(),userBean.getCircleName());
					smsPermissionBean.setCircleCode(userBean.getCircleId());
					circleList.add(lvBean);
				}
			}
			request.setAttribute(Constants.CIRCLE_LIST,circleList);
			//setDefaultValues(smsPermissionBean);
		} catch (DAOException e) {
			logger.error("Exception occured in init() method : "+e);
			errors.clear();
			errors.add(Constants.GENERAL_ERROR,new ActionError(Constants.GENERAL_ERROR));
			if (!errors.isEmpty()) {
				saveErrors(request, errors);
				forward=mapping.findForward(Constants.ERROR);
			}
		} 
		forward=mapping.findForward(Constants.INIT);
		logger.info("Exiting from init() method");
		return  forward;
	}
	
	/**
	 * This method gets the sms config data form the database.
	 * On the basis of ciecle id.
	 * 
	 * @param  mapping object of ActionMapping class that holds the action mapping information used to invoke a Struts action.
	 * @param  form object of ActionForm which is a JavaBean optionally associated with one or more ActionMappings
	 * @param  request object of HttpServletRequest which Wraps the standard request object to override some methods.
	 * @param  response object of HttpServletResponse which Wraps the standard response object to override some methods.
	 * 
	 * @exception IOException,Signals that an I/O exception of some sort has occurred.
	 * @exception ServletException,This exception is thrown to indicate a servlet problem.
	 */
	
	public ActionForward smsConfigurePermissions(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
	throws Exception {
		logger.debug("Entering in smsConfigurePermissions() method");
		ActionErrors errors = new ActionErrors();
		HttpSession session = request.getSession();
		ActionForward forward = new ActionForward(); // return value
		SmsPermissionDaoImpl smsPermissionDaoImpl = new SmsPermissionDaoImpl();
		SmsPermissionBean bean = (SmsPermissionBean)form; 
		UserDetailsBean userBean = (UserDetailsBean)session.getAttribute(Constants.USER_INFO);
		SmsPermissionDto smsdto = new SmsPermissionDto();	
		ViewEditCircleMasterDAOImpl circledao = new ViewEditCircleMasterDAOImpl();
		try {
			//population the list for different users
			smsPermissionDaoImpl.getListSmsReportMstr(smsdto);
			
			//setting in bean
			bean.setEdListSmsReport(smsdto.getEdListSmsReport());
			bean.setCeoListSmsReport(smsdto.getCeoListSmsReport());
			bean.setShListSmsReport(smsdto.getShListSmsReport());
			bean.setZbmListSmsReport(smsdto.getZbmListSmsReport());
			bean.setZsmListSmsReport(smsdto.getZsmListSmsReport());
			bean.setTmListSmsReport(smsdto.getTmListSmsReport());
			bean.setDistListSmsReport(smsdto.getDistListSmsReport());
			bean.setFosListSmsReport(smsdto.getFosListSmsReport());
			bean.setDeaListSmsReport(smsdto.getFosListSmsReport());
			
			//setting circle list 
			
			bean.setCircleList(circledao.getCircleList());
			
			if(bean.getCircleCode()==null || ("-1").equalsIgnoreCase(bean.getCircleCode()) ){
				bean.setCircleCode("-1");
			}
			
			//ED - checking the previously inserted data from v_sms_config
			smsPermissionDaoImpl.getListSmsConfigOfED(smsdto);
			
			//ED - setting in bean
			bean.setEdListSmsConfig(smsdto.getEdListSmsConfig());
			
			//Other Users - checking the previously inserted data from v_sms_config
			smsPermissionDaoImpl.getListSmsConfig(smsdto,bean.getCircleCode());
			
			//Other Users - setting in bean
			bean.setCeoListSmsConfig(smsdto.getCeoListSmsConfig());
			bean.setShListSmsConfig(smsdto.getShListSmsConfig());
			bean.setZbmListSmsConfig(smsdto.getZbmListSmsConfig());
			bean.setZsmListSmsConfig(smsdto.getZsmListSmsConfig());
			bean.setTmListSmsConfig(smsdto.getTmListSmsConfig());
			bean.setDistListSmsConfig(smsdto.getDistListSmsConfig());
			bean.setFosListSmsConfig(smsdto.getFosListSmsConfig());
			bean.setDeaListSmsConfig(smsdto.getFosListSmsConfig());
					
			
		} catch (DAOException e) {
			logger.error("Exception occured in getSmsPermissionConfigData() method : "+e);
			errors.clear();
			errors.add(Constants.GENERAL_ERROR,new ActionError(Constants.GENERAL_ERROR));
			if (!errors.isEmpty()) {
				saveErrors(request, errors);
				forward=mapping.findForward(Constants.ERROR);
			}
		} 
		forward=mapping.findForward("smsConfigMstr");
		logger.debug("Exiting from smsConfigurePermissions() method");
		return  forward;
	}
	
	
	public ActionForward insertsmsConfigurePermissions(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
	throws Exception {
		logger.debug("Entering in smsConfigurePermissions() method");
		ActionErrors errors = new ActionErrors();
		HttpSession session = request.getSession();
		ActionForward forward = new ActionForward(); // return value
		SmsPermissionDaoImpl smsPermissionDaoImpl = new SmsPermissionDaoImpl();
		SmsPermissionBean bean = (SmsPermissionBean)form; 
		UserDetailsBean userBean = (UserDetailsBean)session.getAttribute(Constants.USER_INFO);
		SmsPermissionDto smsdto = new SmsPermissionDto();	
		String[] configStringArray;
		ArrayList configArray=new ArrayList();
		String circode="";
		try {
			//population dto
			circode = bean.getCircleCode();
			configStringArray = request.getParameterValues("ED");
			if (configStringArray != null)  {
			      for (int i = 0; i < configStringArray.length; i++){
			    	  SmsPermissionDto smsdto1 = new SmsPermissionDto();
			    	  smsdto1.setReportId(Integer.parseInt(configStringArray[i]));
			    	  smsdto1.setUserType(Constants.ED);
			    	  smsdto1.setCircleCode("");
			    	  configArray.add(smsdto1);
			      }
			}
			configStringArray = request.getParameterValues("CEO");
			if (configStringArray != null)  {
			      for (int i = 0; i < configStringArray.length; i++){
			    	  SmsPermissionDto smsdto1 = new SmsPermissionDto();
			    	  smsdto1.setReportId(Integer.parseInt(configStringArray[i]));
			    	  smsdto1.setUserType(Constants.CEO);
			    	  smsdto1.setCircleCode(circode);
			    	  configArray.add(smsdto1);
			      }
			}
			configStringArray = request.getParameterValues("SH");
			if (configStringArray != null)  {
			      for (int i = 0; i < configStringArray.length; i++){
			    	  SmsPermissionDto smsdto1 = new SmsPermissionDto();
			    	  smsdto1.setReportId(Integer.parseInt(configStringArray[i]));
			    	  smsdto1.setUserType(Constants.SALES_HEAD);
			    	  smsdto1.setCircleCode(circode);
			    	  configArray.add(smsdto1);
			      }
			}
			configStringArray = request.getParameterValues("ZBM");
			if (configStringArray != null)  {
			      for (int i = 0; i < configStringArray.length; i++){
			    	  SmsPermissionDto smsdto1 = new SmsPermissionDto();
			    	  smsdto1.setReportId(Integer.parseInt(configStringArray[i]));
			    	  smsdto1.setUserType(Constants.ZBM);
			    	  smsdto1.setCircleCode(circode);
			    	  configArray.add(smsdto1);
			      }
			}
			configStringArray = request.getParameterValues("ZSM");
			if (configStringArray != null)  {
			      for (int i = 0; i < configStringArray.length; i++){
			    	  SmsPermissionDto smsdto1 = new SmsPermissionDto();
			    	  smsdto1.setReportId(Integer.parseInt(configStringArray[i]));
			    	  smsdto1.setUserType(5);
			    	  smsdto1.setCircleCode(circode);
			    	  configArray.add(smsdto1);
			      }
			}
			configStringArray = request.getParameterValues("TM");
			if (configStringArray != null)  {
			      for (int i = 0; i < configStringArray.length; i++){
			    	  SmsPermissionDto smsdto1 = new SmsPermissionDto();
			    	  smsdto1.setReportId(Integer.parseInt(configStringArray[i]));
			    	  smsdto1.setUserType(Constants.TM);
			    	  smsdto1.setCircleCode(circode);
			    	  configArray.add(smsdto1);
			      }
			}
			configStringArray = request.getParameterValues("DIST");
			if (configStringArray != null)  {
			      for (int i = 0; i < configStringArray.length; i++){
			    	  SmsPermissionDto smsdto1 = new SmsPermissionDto();
			    	  smsdto1.setReportId(Integer.parseInt(configStringArray[i]));
			    	  smsdto1.setUserType(Constants.DISTIBUTOR);
			    	  smsdto1.setCircleCode(circode);
			    	  configArray.add(smsdto1);
			      }
			}
			configStringArray = request.getParameterValues("FOS");
			if (configStringArray != null)  {
			      for (int i = 0; i < configStringArray.length; i++){
			    	  SmsPermissionDto smsdto1 = new SmsPermissionDto();
			    	  smsdto1.setReportId(Integer.parseInt(configStringArray[i]));
			    	  smsdto1.setUserType(Constants.FOS);
			    	  smsdto1.setCircleCode(circode);
			    	  configArray.add(smsdto1);
			      }
			}
			configStringArray = request.getParameterValues("DEA");
			if (configStringArray != null)  {
			      for (int i = 0; i < configStringArray.length; i++){
			    	  SmsPermissionDto smsdto1 = new SmsPermissionDto();
			    	  smsdto1.setReportId(Integer.parseInt(configStringArray[i]));
			    	  smsdto1.setUserType(Constants.DEALER);
			    	  smsdto1.setCircleCode(circode);
			    	  configArray.add(smsdto1);
			      }
			}
			
			boolean flag = smsPermissionDaoImpl.insertSmsConfig(configArray,userBean.getLoginId(),circode);
			if(flag){
				errors.add("Inserted",new ActionError("ErrorKey","Sms Configuration Insertred Successfully"));
				saveMessages(request, errors);
				logger.info("Sms Configuration Insertred Successfully by : " + userBean.getLoginId());
				// forward to success.jsp
				forward = mapping.findForward(Constants.SUCCESSCAPS);
			}else{
				
				errors.add("There was some error",new ActionError("There was some error"));
				if (!errors.isEmpty()) {
					saveErrors(request, errors);
				}
			}
			
		} catch (DAOException e) {
			logger.error("Exception occured in getSmsPermissionConfigData() method : "+e);
			errors.clear();
			errors.add(Constants.GENERAL_ERROR,new ActionError(Constants.GENERAL_ERROR));
			if (!errors.isEmpty()) {
				saveErrors(request, errors);
				forward=mapping.findForward(Constants.ERROR);
			}
		} 
		forward=mapping.findForward("Valid");
		logger.debug("Exiting from smsConfigurePermissions() method");
		return  forward;
	}
	
}
