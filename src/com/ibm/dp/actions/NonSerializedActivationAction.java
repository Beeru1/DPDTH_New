package com.ibm.dp.actions;

import java.io.PrintWriter;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.actions.DispatchAction;
import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.dom4j.io.XMLWriter;

import com.ibm.dp.beans.NonSerializedActivationBean;
import com.ibm.dp.common.Constants;
import com.ibm.dp.dto.CircleDto;
import com.ibm.dp.service.NonSerializedActivationService;
import com.ibm.dp.service.impl.NonSerializedActivationServiceImpl;
import com.ibm.virtualization.recharge.dto.UserSessionContext;

public class NonSerializedActivationAction  extends DispatchAction {

	private static Logger logger = Logger.getLogger(NonSerializedActivationAction.class
			.getName());
		
	public ActionForward init(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {

		NonSerializedActivationBean formBean =(NonSerializedActivationBean)form ;
		//Setting Circle List in Form
		NonSerializedActivationService nonSerializednService = NonSerializedActivationServiceImpl.getInstance();
		List<CircleDto> circleList =nonSerializednService.getAllCircleList();
		formBean.setCircleList(circleList);
		
		return mapping.findForward("init");

	}
	
	public ActionForward getActivationStatus(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {

		NonSerializedActivationBean formBean =(NonSerializedActivationBean)form ;		
		NonSerializedActivationService nonSerializednService = NonSerializedActivationServiceImpl.getInstance();
		String circleId = request.getParameter("circleId");
		String status =nonSerializednService.getActivationStatus(circleId);
		String[] statusArr={};
		if(!status.equals("")){
			statusArr = status.split(",");
		}
		String statusNser =statusArr[0];
		String statusSer =statusArr[1];
		String statusInvNser =statusArr[2];
		String statusInvSer =statusArr[3];
		
		formBean.setStatusNser(statusNser);
		formBean.setStatusSer(statusSer);
		formBean.setStatusInvNser(statusInvNser);
		formBean.setStatusInvSer(statusInvSer);
		
		Document document = DocumentHelper.createDocument();
		Element root = document.addElement("options");
		Element optionElement;
		optionElement = root.addElement("option");
		optionElement.addAttribute("StatusNser",statusNser);
		optionElement.addAttribute("StatusSer",statusSer);
		optionElement.addAttribute("StatusInvNser", statusInvNser);
		optionElement.addAttribute("StatusInvSer", statusInvSer);
		
		response.setHeader("Cache-Control", "No-Cache");
		response.setContentType("text/xml");
		PrintWriter out = response.getWriter();
		//OutputFormat outputFormat = OutputFormat.createCompactFormat();
		XMLWriter writer = new XMLWriter(out);
		writer.write(document);
		writer.flush();
		out.flush();
		return null;

	}
	
	public ActionForward updateStatus(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {

		NonSerializedActivationBean formBean =(NonSerializedActivationBean)form ;		
		NonSerializedActivationService nonSerializednService = NonSerializedActivationServiceImpl.getInstance();
		String circleId = request.getParameter("circleId");
		String statusNser = request.getParameter("selStatusNser");
		String statusSer = request.getParameter("selStatusSer");
		String statusInvNser = request.getParameter("selStatusInvNser");
		String statusInvSer = request.getParameter("selStatusInvSer");
		HttpSession session = request.getSession();
		UserSessionContext sessionContext = (UserSessionContext) session.getAttribute(Constants.AUTHENTICATED_USER);
		String accountID = String.valueOf(sessionContext.getId());
		String status =nonSerializednService.getActivationStatus(circleId);
		String[] statusArr={};
		if(!status.equals("")){
			statusArr = status.split(",");
		}
		String statusNserOld =statusArr[0];
		String statusSerOld =statusArr[1];
		String statusInvNserOld =statusArr[2];
		String statusInvSerOld =statusArr[3];
		
		int result =0;
		int result1 =0;
		int result2 =0;
		int result3 =0;
			
		if(!statusNserOld.equals(statusNser))
		result =nonSerializednService.updateStatus(accountID,circleId,statusNser,"NSER_ACTIVATION_STATUS");
		if(result!=0){
			nonSerializednService.insertActivation(circleId,"NSER_ACTIVATION_STATUS",statusNser);			
		}
		
		if(!statusSerOld.equals(statusSer))
		result1 =nonSerializednService.updateStatus(accountID,circleId,statusSer,"SER_ACTIVATION_STATUS");
		if(result1!=0){
			nonSerializednService.insertActivation(circleId,"SER_ACTIVATION_STATUS",statusSer);			
		}
		
		if(!statusInvNserOld.equals(statusInvNser))
		result2 =nonSerializednService.updateStatus(accountID,circleId,statusInvNser,"INV_CHANGE_NSER_STATUS");
		if(result2!=0){
			nonSerializednService.insertActivation(circleId,"INV_CHANGE_NSER_STATUS",statusInvNser);			
		}
		
		if(!statusInvSerOld.equals(statusInvSer))
		result3 =nonSerializednService.updateStatus(accountID,circleId,statusInvSer,"INV_CHANGE_SER_STATUS");
		if(result3!=0){
			nonSerializednService.insertActivation(circleId,"INV_CHANGE_SER_STATUS",statusInvSer);			
		}
		List<CircleDto> circleList =nonSerializednService.getAllCircleList();
		formBean.setCircleList(circleList);
		formBean.setFromCircleId(circleId);
						
		formBean.setStatusNser(statusNser);
		formBean.setStatusSer(statusSer);
		formBean.setStatusInvNser(statusInvNser);
		formBean.setStatusInvSer(statusInvSer);
		return mapping.findForward("init");

	}
}
