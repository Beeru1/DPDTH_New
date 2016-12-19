package com.ibm.dp.actions;

import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.InvocationTargetException;
import java.util.Enumeration;
import java.util.Properties;
import java.util.ResourceBundle;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.log4j.Logger;
import org.apache.struts.action.ActionError;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.actions.DispatchAction;

import com.ibm.dp.beans.ConfigurationMasterBean;
import com.ibm.dp.dto.ConfigurationMaster;
import com.ibm.dp.service.ConfigurationService;
import com.ibm.dp.service.impl.ConfigurationServiceIml;
import com.ibm.virtualization.framework.bean.ChannelType;
import com.ibm.virtualization.recharge.authorization.AuthorizationConstants;
import com.ibm.virtualization.recharge.authorization.AuthorizationServiceImpl;
import com.ibm.virtualization.recharge.common.Constants;
import com.ibm.virtualization.recharge.dto.UserSessionContext;
import com.ibm.virtualization.recharge.service.AuthorizationInterface;
import com.ibm.xtq.xslt.xylem.instructions.GetStringValueInstruction;

public class ConfigurationMasterAction extends DispatchAction{

	private static final String EDIT_CONFIGRATION_SUCCESS = "initSuccess";

	/* Logger for this class. */
	private static Logger logger = Logger.getLogger(ConfigurationMasterAction.class
			.getName());

	/* Local Variables */
	
	private String STATUS_ERROR="error";
	private String INIT_SUCCESS="initSuccess";
	private String CREATE_CONFIGRATION_SUCCESS="initSuccess";
	private String CREATE_CONFIGRATION_FAILURE="initSuccess";

	public ActionForward init(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		logger.info(" init Started... ");
		HttpSession session = request.getSession();
		ConfigurationMasterBean masterBean =(ConfigurationMasterBean)form;
		UserSessionContext sessionContext = (UserSessionContext) session
				.getAttribute(Constants.AUTHENTICATED_USER);
		AuthorizationInterface authorizationService = new AuthorizationServiceImpl();
		if (!authorizationService.isUserAuthorized(sessionContext
				.getGroupId(),ChannelType.WEB, AuthorizationConstants.ROLE_ADD_CONFIGURATION)) {
			logger.info(" user not auth to perform ROLE_ADD_CONFIGURATION this activity  ");
			ActionErrors errors = new ActionErrors();
			errors.add("errors", new ActionError(
					"errors.usernotautherized"));
			saveErrors(request, errors);
			return mapping.findForward(Constants.USER_NOT_AUTHERIZED);
		}
		
		ConfigurationService configrationService =new ConfigurationServiceIml();
		ConfigurationMaster configMaster = new ConfigurationMaster();	
		String configId="34", configName = "",configValue = "" , configDesc = "";

		 Properties prop = new Properties();
			InputStream input = null;

			try {
				ResourceBundle rb = ResourceBundle.getBundle("com.ibm.dp.resources.DPResources");
				Enumeration <String> keys = rb.getKeys();
				
				if(rb.getKeys().equals("configuration.configId"));
				{
					configId = rb.getString("configuration.configId");
				}
				if(rb.getKeys().equals("configuration.configName"));
				{
					configName = rb.getString("configuration.configName");
				}
				if(rb.getKeys().equals("configuration.configValue"));
				{
					configValue = rb.getString("configuration.configValue");
				}
				if(rb.getKeys().equals("configuration.configDesc"));
				{
					configDesc = rb.getString("configuration.configDesc");
				}
								
			} finally {
				if (input != null) {
					try {
						input.close();
					} catch (IOException e) {
						e.printStackTrace();
					}
				}
			}

		
		
		configMaster = configrationService.getConfigDetails(configId);
		String configId1 = configMaster.getConfigId(); 
		
		if(configId1 ==  null)
		{
			configId1="";
		}
		
		 if(configId1.equalsIgnoreCase(""))
		 {
			 masterBean.setConfigId(configId);
			 masterBean.setConfigName(configName);
			 masterBean.setConfigValue(configValue);
			 masterBean.setConfigDesc(configDesc);
			 
		 }
		 else
		 {
			 masterBean.setConfigId(configMaster.getConfigId());
			 masterBean.setConfigName(configMaster.getConfigName());
			 masterBean.setConfigValue(configMaster.getConfigValue());
			 masterBean.setConfigDesc(configMaster.getConfigDesc());
			 masterBean.setIsEdit("Y");
		 }
		
	
		try {
			BeanUtils.copyProperties(configMaster, masterBean);
		} catch (IllegalAccessException iaExp) {
			logger
					.error(
							"  caught Exception while using BeanUtils.copyProperties()",
							iaExp);
		} catch (InvocationTargetException itExp) {
			logger
					.error(
							"  caught Exception while using BeanUtils.copyProperties()",
							itExp);
		}
		
		
		//logger.info(" Executed... ");
		//System.out.println("init executed");
		saveToken(request);
		return mapping.findForward(INIT_SUCCESS);
	}
	
	
	public ActionForward insertConfigDetail(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		logger.info(" Started... ");
		ConfigurationService configService = new ConfigurationServiceIml(); 
		ConfigurationMasterBean masterBean =(ConfigurationMasterBean)form;
		
		
		if(!isTokenValid(request))
		{
			return mapping.findForward(Constants.USER_NOT_AUTHERIZED);
		}
		
		HttpSession session = request.getSession();
		UserSessionContext userSessionContext = (UserSessionContext) session
				.getAttribute(Constants.AUTHENTICATED_USER);
		AuthorizationInterface authorizationService = new AuthorizationServiceImpl();
		if (!authorizationService.isUserAuthorized(userSessionContext
				.getGroupId(),ChannelType.WEB, AuthorizationConstants.ROLE_ADD_CONFIGURATION)) {
			logger.info(" user not auth to perform ROLE_ADD_CONFIGURATION this activity  ");
			ActionErrors errors = new ActionErrors();
			errors.add("errors", new ActionError("errors.usernotautherized"));
			saveErrors(request, errors);
			return mapping.findForward(Constants.USER_NOT_AUTHERIZED);
		}	
		ConfigurationMaster configMaster= new ConfigurationMaster();
		
		/* Populate DTO object with Bean values */
		/* BeanUtil to populate Geography DTO with Form Bean data */
		try {
			BeanUtils.copyProperties(configMaster, masterBean);
		} catch (IllegalAccessException iaExp) {
			logger
					.error(
							"  caught Exception while using BeanUtils.copyProperties()",
							iaExp);
		} catch (InvocationTargetException itExp) {
			logger
					.error(
							"  caught Exception while using BeanUtils.copyProperties()",
							itExp);
		}
		
		try{
		configService.insertConfigurationDetail(configMaster);
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		configMaster.setConfigName(masterBean.getConfigName());
		
		ActionErrors errors = new ActionErrors();
		errors.add(ActionErrors.GLOBAL_MESSAGE, new ActionError(
				"messages.ConfigurationMaster.create_success"));
		saveErrors(request, errors);
		logger.info(" Executed... ");
		masterBean.reset(mapping, request);
		return mapping.findForward(CREATE_CONFIGRATION_SUCCESS);
	}

	
	
	public ActionForward updateConfigDetail(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		logger.info(" Started... ");
		ConfigurationService configService = new ConfigurationServiceIml(); 
		ConfigurationMasterBean masterBean =(ConfigurationMasterBean)form;
		
		
		if(!isTokenValid(request))
		{
			return mapping.findForward(Constants.USER_NOT_AUTHERIZED);
		}
		
		HttpSession session = request.getSession();
		UserSessionContext userSessionContext = (UserSessionContext) session
				.getAttribute(Constants.AUTHENTICATED_USER);
		AuthorizationInterface authorizationService = new AuthorizationServiceImpl();
		if (!authorizationService.isUserAuthorized(userSessionContext
				.getGroupId(),ChannelType.WEB, AuthorizationConstants.ROLE_ADD_CONFIGURATION)) {
			logger.info(" user not auth to perform ROLE_ADD_CONFIGURATION this activity  ");
			ActionErrors errors = new ActionErrors();
			errors.add("errors", new ActionError("errors.usernotautherized"));
			saveErrors(request, errors);
			return mapping.findForward(Constants.USER_NOT_AUTHERIZED);
		}	
		ConfigurationMaster configMaster= new ConfigurationMaster();
		
		/* Populate DTO object with Bean values */
		/* BeanUtil to populate Geography DTO with Form Bean data */
		try {
			BeanUtils.copyProperties(configMaster, masterBean);
		} catch (IllegalAccessException iaExp) {
			logger
					.error(
							"  caught Exception while using BeanUtils.copyProperties()",
							iaExp);
		} catch (InvocationTargetException itExp) {
			logger
					.error(
							"  caught Exception while using BeanUtils.copyProperties()",
							itExp);
		}
		
		configService.updateConfigDetails(configMaster);

		configMaster.setConfigName(masterBean.getConfigName());

		ActionErrors errors = new ActionErrors();
		errors.add(ActionErrors.GLOBAL_MESSAGE, new ActionError(
				"messages.ConfigurationMaster.update_success"));
		saveErrors(request, errors);
		logger.info(" Executed... ");
		masterBean.reset(mapping, request);
		//	return mapping.findForward(INIT_SUCCESS);
		return mapping.findForward(EDIT_CONFIGRATION_SUCCESS);
	}
	
	
	
}
