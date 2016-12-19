package com.ibm.dp.actions;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.actions.DispatchAction;

import com.ibm.dp.beans.DPAlertConfigurationBean;
import com.ibm.dp.dto.AlertConfigurationDTO;
import com.ibm.dp.service.DPAlertConfigurationService;
import com.ibm.dp.service.impl.DPAlertConfigurationServiceImpl;
import com.ibm.virtualization.recharge.common.Constants;
import com.ibm.virtualization.recharge.common.ResourceReader;
import com.ibm.virtualization.recharge.dto.UserSessionContext;
;
public class DPAlertConfigurationAction  extends DispatchAction {

	private static Logger logger = Logger.getLogger(DPAlertConfigurationAction.class.getName());
	
	

	public ActionForward initadmin(ActionMapping mapping, ActionForm form,HttpServletRequest request, HttpServletResponse response)throws Exception 
	{	
		DPAlertConfigurationBean formBean =(DPAlertConfigurationBean)form ;
		HttpSession session = request.getSession();
		DPAlertConfigurationService alertService = new DPAlertConfigurationServiceImpl();
		List<AlertConfigurationDTO> alertList = null;
		
		
//		Added to get initial value of TSM Grace period 
		
		AlertConfigurationDTO obj = alertService.getTsmGracePeriod();
		String tsmPeriod=obj.getTsmGracePeriod();
		logger.info("*****tsmPeriod*****"+tsmPeriod);
		formBean.setTsmGracePeriod(tsmPeriod);
		
//		Added to get initial value of ZSM Grace period 
		
		AlertConfigurationDTO obj1 = alertService.getZsmGracePeriod();
		String zsmPeriod=obj1.getZsmGracePeriod();
		logger.info("*****zsmPeriod*****"+zsmPeriod);
		formBean.setZsmGracePeriod(zsmPeriod);
// 		Added to get initial alert list for dth admin		
		alertList = alertService.getAlertList();
		formBean.setAlertList(alertList);
		session.setAttribute("AlertListSize", alertList.size());
		logger.info("Alert list size in init action::for dthadmin::"+alertList.size());
		session.setAttribute("alertList", alertList);
		return mapping.findForward("initSuccess");
	}
	public ActionForward initOtherUsers(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		DPAlertConfigurationBean formBean =(DPAlertConfigurationBean)form ;
		HttpSession session = request.getSession();
		UserSessionContext sessionContext = (UserSessionContext) session.getAttribute(Constants.AUTHENTICATED_USER);
		DPAlertConfigurationService Service = new DPAlertConfigurationServiceImpl();
		List<AlertConfigurationDTO> userGroupId = null;
		try
		{
			userGroupId = Service.getuserGroupId (sessionContext.getGroupId(),(sessionContext.getId()+""));
			formBean.setUserGroupId(userGroupId);
			logger.info("Alert list size in init action::for other user::"+userGroupId.size());
			session.setAttribute("usergroupid", userGroupId);
			logger.info("Note message  ::  "+ResourceReader.getCoreResourceBundleValue(Constants.ALERT_NOTE_MSG));
			formBean.setStrNoteMsg(ResourceReader.getCoreResourceBundleValue(Constants.ALERT_NOTE_MSG));
		}
		catch(Exception e)
		{
			e.printStackTrace();
			request.setAttribute("msg","Internal Error occured");
			return mapping.findForward("initSuccessOthers");
		}
		return mapping.findForward("initSuccessOthers");
	}
	
	public ActionForward updateStatus(ActionMapping mapping, ActionForm form,HttpServletRequest request, HttpServletResponse response)
		throws Exception 
		{
		List<AlertConfigurationDTO> alertList = null;
		//DPAlertConfigurationBean formBean =(DPAlertConfigurationBean)form ;
		logger.info("**********inside dthadmin updateStatus method*********");
		HttpSession session = request.getSession();
		alertList = (List<AlertConfigurationDTO>)session.getAttribute("alertList");
		logger.info("****************alertlist.size::::::::::"+alertList.size());
		DPAlertConfigurationService alertService = new DPAlertConfigurationServiceImpl();
		int statusUpdated = alertService.updateStatus(request,alertList);
		if (statusUpdated >0)
		{
			request.setAttribute("msg","Alert Configuration Updated Successfully.");
		}
		//session.removeAttribute("AlertListSize");
		session.removeAttribute("alertList");
		return initadmin( mapping,  form,  request,  response);
	}

	public ActionForward otherupdateStatus(ActionMapping mapping, ActionForm form,HttpServletRequest request, HttpServletResponse response)throws Exception {
		DPAlertConfigurationBean formBean =(DPAlertConfigurationBean)form ;
		List<AlertConfigurationDTO> userGroupId = null;
		HttpSession session = request.getSession();
		userGroupId = (List<AlertConfigurationDTO>)session.getAttribute("usergroupid");
		DPAlertConfigurationService alertService = new DPAlertConfigurationServiceImpl();
		
		UserSessionContext sessionContext = (UserSessionContext) session.getAttribute(Constants.AUTHENTICATED_USER);
		
		List userGroup=(List)session.getAttribute("usergroupid");
		
		String loginId = sessionContext.getId()+"";
		
		int[] alertId= new int[userGroup.size()];
		int[] status= new int[userGroup.size()];
		String alertVal="";
		for(int i=0;i<userGroup.size();i++)
		{
			String name="alertId"+i;
			alertId[i]=Integer.valueOf(request.getParameter(name));
			name="status"+i;
			alertVal = request.getParameter(name) ;
			if(alertVal != null && alertVal.equalsIgnoreCase("true"))
			{
				alertVal = "1";
			}
			else
			{
				alertVal = "0";
			}
			status[i]=Integer.valueOf(alertVal);
		}
		
		int statusUpdated = alertService.otherupdateStatus(alertId, status,userGroupId, loginId);
		if (statusUpdated >0)
		{
			request.setAttribute("msg","Alert Configuration Updated Successfully.");
		}
		formBean.setStrNoteMsg(ResourceReader.getCoreResourceBundleValue(Constants.ALERT_NOTE_MSG));
		session.removeAttribute("usergroupid");
		return initOtherUsers( mapping,  form,  request,  response);
	}
	
	
	public ActionForward updateGraceperiod(ActionMapping mapping, ActionForm form,HttpServletRequest request, HttpServletResponse response)
	throws Exception {
		HttpSession session = request.getSession();
		UserSessionContext sessionContext = (UserSessionContext) session.getAttribute(Constants.AUTHENTICATED_USER);
		DPAlertConfigurationBean formBean =(DPAlertConfigurationBean)form ;
		//updating tsm graceperiod
		String tsmGracePeriod = formBean.getTsmGracePeriod();
		logger.info("tsmGracePeriod::"+tsmGracePeriod);
		DPAlertConfigurationService gracePeriodService = new DPAlertConfigurationServiceImpl();
		AlertConfigurationDTO obj = gracePeriodService.updateTsmGracePeriod(tsmGracePeriod);
		logger.info(gracePeriodService.updateTsmGracePeriod(tsmGracePeriod));
		logger.info(obj.getTsmGracePeriod());
		String tsmPeriod=obj.getTsmGracePeriod();
		logger.info("*****tsmGracePeriod new*****"+tsmPeriod);
		formBean.setTsmGracePeriod(tsmPeriod);
	
	//updating zsm graceperiod
		String zsmGracePeriod = formBean.getZsmGracePeriod();
		logger.info("zsmGracePeriod::"+zsmGracePeriod);
		AlertConfigurationDTO obj1 = gracePeriodService.updateZsmGracePeriod(zsmGracePeriod);
		String zsmPeriod=obj1.getZsmGracePeriod();
		logger.info("*****zsmGracePeriod new*****"+zsmPeriod);
		formBean.setTsmGracePeriod(tsmPeriod);
		formBean.setStrMsg("Successfully updated graceperiod");
		return initadmin( mapping,  form,request,  response);
}

	
}