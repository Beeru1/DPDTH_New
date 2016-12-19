package com.ibm.dp.actions;

import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.util.Iterator;
import java.util.List;

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
import org.apache.struts.upload.FormFile;
import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.dom4j.io.OutputFormat;
import org.dom4j.io.XMLWriter;

import com.ibm.dp.beans.C2SBulkUploadBean;
import com.ibm.dp.beans.ChangeUserRoleBean;
import com.ibm.dp.beans.DPViewHierarchyFormBean;
import com.ibm.dp.beans.TransferPendingSTBBean;
import com.ibm.dp.dto.CircleDto;
import com.ibm.dp.dto.NewRoleDto;
import com.ibm.dp.dto.RoleDto;
import com.ibm.dp.dto.UserDto;
import com.ibm.dp.service.C2SBulkUploadService;
import com.ibm.dp.service.ChangeUserRoleService;
import com.ibm.dp.service.DPViewHierarchyReportService;
import com.ibm.dp.service.TransferPendingSTBService;
import com.ibm.dp.service.impl.C2SBulkUploadServiceImpl;
import com.ibm.dp.service.impl.ChangeUserRoleServiceImpl;
import com.ibm.dp.service.impl.DPViewHierarchyReportServiceImpl;
import com.ibm.dp.service.impl.TransferPendingSTBServiceImpl;
import com.ibm.virtualization.framework.bean.ChannelType;
import com.ibm.virtualization.recharge.authorization.AuthorizationConstants;
import com.ibm.virtualization.recharge.authorization.AuthorizationServiceImpl;
import com.ibm.virtualization.recharge.common.Constants;
import com.ibm.virtualization.recharge.dto.UserSessionContext;
import com.ibm.virtualization.recharge.service.AuthorizationInterface;

public class ChangeUserRoleAction extends DispatchAction
{
	/* Logger for this class. */
	private static Logger logger = Logger.getLogger(ChangeUserRoleAction.class.getName());
	
	private static final String INIT_SUCCESS = "success";

	
	public ActionForward init(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		// Changes done for Authentication 27/09/2013 by Divya
		HttpSession session = request.getSession();
		AuthorizationInterface authorizationService = new AuthorizationServiceImpl();
		UserSessionContext sessionContext = (UserSessionContext) session.getAttribute(Constants.AUTHENTICATED_USER);
		logger.info("-----------------init ACTION CALLED-----------------");
		ActionErrors errors = new ActionErrors();
		if (!authorizationService.isUserAuthorized(sessionContext.getGroupId(), ChannelType.WEB,AuthorizationConstants.ROLE_CREATE_ACCOUNT_IT_HELPDESK))
		{
			logger.info(" user not auth to perform this ROLE_ADD_ACCOUNT activity  ");
			errors.add("errors",new ActionError("errors.usernotautherized"));
			saveErrors(request, errors);
			return mapping.findForward(Constants.USER_NOT_AUTHERIZED);
		}
		ChangeUserRoleBean formBean =(ChangeUserRoleBean)form ;
		TransferPendingSTBService transferService = TransferPendingSTBServiceImpl.getInstance();
		List<CircleDto> circleList =transferService.getAllCircleList();
		CircleDto circleDto = new CircleDto();
		circleDto.setCircleId("0");
		circleDto.setCircleName("Pan India");			
		circleList.add(circleDto);
		
		formBean.setCircleList(circleList);				
		return mapping.findForward(INIT_SUCCESS);
	}
		
	public ActionForward getRole(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		logger.info("In method getRole().");
		
		try 
		{
			ChangeUserRoleBean formBean =(ChangeUserRoleBean)form ;
			ChangeUserRoleService changeRoleService = new ChangeUserRoleServiceImpl();
			
			List roleList = changeRoleService.getRoleList(Constants.DISTRIBUTOR_ID);
			formBean.setRoleList(roleList);
							
			// Ajax start
			Document document = DocumentHelper.createDocument();
			Element root = document.addElement("options");
			Element optionElement;
			
			Iterator iter = roleList.iterator();
			while (iter.hasNext()) 
			{
				RoleDto roleDto = (RoleDto) iter.next();
				optionElement = root.addElement("option");
				optionElement.addAttribute("text", roleDto.getRoleName());
				optionElement.addAttribute("value", roleDto.getRoleId());
			}

			// For ajax
			response.setHeader("Cache-Control", "No-Cache");
			response.setContentType("text/xml");
			PrintWriter out = response.getWriter();
			OutputFormat outputFormat = OutputFormat.createCompactFormat();
			XMLWriter writer = new XMLWriter(out);
			writer.write(document);
			writer.flush();
			out.flush();
			// End for ajax
		

		} catch (Exception ex) 
		{
			ex.printStackTrace();
			logger.error("Exception in ::"+ex);
		}

		return null;
	}
	
	public ActionForward getUserList(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws IOException, ServletException 
	{
		logger.info("In method getUserList().");
		
		try 
		{			
			ChangeUserRoleBean formBean =(ChangeUserRoleBean)form ;
			String circleId = request.getParameter("circleId");
			String roleID = request.getParameter("userRole");			
			ChangeUserRoleService roleService = new ChangeUserRoleServiceImpl();
			List<UserDto> userList =roleService.getUserList("user",circleId,roleID,"");
		//	formBean.setUserList(userList);
			
//			 Ajax start
			Document document = DocumentHelper.createDocument();
			Element root = document.addElement("options");
			Element optionElement;
			
			Iterator iter = userList.iterator();
			while (iter.hasNext()) 
			{
				UserDto roleDto = (UserDto) iter.next();
				optionElement = root.addElement("option");
				optionElement.addAttribute("text", roleDto.getUserName());
				optionElement.addAttribute("value", roleDto.getUserId());
			}

			// For ajax
			response.setHeader("Cache-Control", "No-Cache");
			response.setContentType("text/xml");
			PrintWriter out = response.getWriter();
			OutputFormat outputFormat = OutputFormat.createCompactFormat();
			XMLWriter writer = new XMLWriter(out);
			writer.write(document);
			writer.flush();
			out.flush();
			// End for ajax
			
		} catch (Exception ex) 
		{
			ex.printStackTrace();
			logger.error("Exception in ::"+ex);
		}

		return null;
	}
	public ActionForward getParentList(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws IOException, ServletException 
	{
		logger.info("In method getParentList().");
		
		try 
		{			
			ChangeUserRoleBean formBean =(ChangeUserRoleBean)form ;
			String circleId =request.getParameter("circleId"); 
			String roleID = request.getParameter("userRole");	
			String currentUser=request.getParameter("currentUser");
			ChangeUserRoleService roleService = new ChangeUserRoleServiceImpl();
			List<UserDto> userList =roleService.getUserList("parent",circleId,roleID,currentUser);
		//	formBean.setUserList(userList);
			System.out.println("Size in action::"+userList.size());
//			 Ajax start
			Document document = DocumentHelper.createDocument();
			Element root = document.addElement("options");
			Element optionElement;
			
			Iterator iter = userList.iterator();
			while (iter.hasNext()) 
			{
				//System.out.println("iteration in action");
				UserDto roleDto = (UserDto) iter.next();
				optionElement = root.addElement("option");
				optionElement.addAttribute("text", roleDto.getUserName());
				optionElement.addAttribute("value", roleDto.getUserId());
			}
			
			// For ajax
			response.setHeader("Cache-Control", "No-Cache");
			response.setContentType("text/xml");
			PrintWriter out = response.getWriter();
			OutputFormat outputFormat = OutputFormat.createCompactFormat();
			XMLWriter writer = new XMLWriter(out);
			writer.write(document);
			writer.flush();
			out.flush();
			// End for ajax
			
		} catch (Exception ex) 
		{
			ex.printStackTrace();
			logger.error("Exception in ::"+ex);
		}

		return null;
	}
	public ActionForward checkChilds(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws IOException, ServletException 
	{
		logger.info("In method checkChilds().");
		
		try 
		{			
			ChangeUserRoleBean formBean =(ChangeUserRoleBean)form ;			
			String currentUserID = request.getParameter("currentUser");
			String currentRole=request.getParameter("currentRole");
			ChangeUserRoleService roleService = new ChangeUserRoleServiceImpl();
			String haveChilds =roleService.checkUserChilds(currentUserID,currentRole);
			formBean.setHaveChilds(haveChilds);
			
//			 Ajax start
			Document document = DocumentHelper.createDocument();
			Element root = document.addElement("options");
			Element optionElement;
				optionElement = root.addElement("option");
				optionElement.addAttribute("haveChilds1", formBean.getHaveChilds());

			// For ajax
			response.setHeader("Cache-Control", "No-Cache");
			response.setContentType("text/xml");
			PrintWriter out = response.getWriter();
			OutputFormat outputFormat = OutputFormat.createCompactFormat();
			XMLWriter writer = new XMLWriter(out);
			writer.write(document);
			writer.flush();
			out.flush();
			// End for ajax
			
		} catch (Exception ex) 
		{
			ex.printStackTrace();
			logger.error("Exception in ::"+ex);
		}

		return null;
	}
	
	public ActionForward updateUserRoles(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws IOException, ServletException 
		{
		ChangeUserRoleBean formBean = null;
		try{
			HttpSession session = request.getSession();
			UserSessionContext userSessionContext = (UserSessionContext) session.getAttribute(Constants.AUTHENTICATED_USER);
		
			formBean =(ChangeUserRoleBean)form ;		
			
			String currentRole = request.getParameter("currentRole");	
			String currentCircle = request.getParameter("currentCircle");	
			String currentUser = request.getParameter("currentUser");	
			String newRole = request.getParameter("newRole");	
			String newCircle = request.getParameter("newCircle");	
			String parent = request.getParameter("parent");		
			
			String approver1 = 	formBean.getApprover1();	
			String approver2 = 	formBean.getApprover2();	
			String srNo = 	formBean.getSr_Number();	
			
			NewRoleDto  newRoleDto =new NewRoleDto();
			newRoleDto.setCurrentRole(currentRole);
			newRoleDto.setCurrentCircle(currentCircle);
			newRoleDto.setCurrentUser(currentUser);			
			newRoleDto.setNewRole(newRole);
			newRoleDto.setNewCircle(newCircle);
			newRoleDto.setParent(parent);
			newRoleDto.setApprover1(approver1);
			newRoleDto.setApprover2(approver2);
			newRoleDto.setSR_Number(srNo);
			newRoleDto.setCreatedBy(userSessionContext.getId()+"");
			newRoleDto.setTransType(formBean.getTransType());
			
			ChangeUserRoleService roleService = new ChangeUserRoleServiceImpl();
			roleService.updateRoles(newRoleDto);
			TransferPendingSTBService transferService = TransferPendingSTBServiceImpl.getInstance();
			List<CircleDto> circleList =transferService.getAllCircleList();
			formBean.setCircleList(circleList);
			logger.info("User Role has been changed ..");
			formBean.setStrMsg("User Role has been changed Successfully");
			}catch (Exception ex) 
			{
				ex.printStackTrace();
				logger.error("Exception in ::"+ex);
				formBean.setStrMsg("Internal Error Occured.Please try later");
			}
			
			
		return mapping.findForward(INIT_SUCCESS);
	}
	
}