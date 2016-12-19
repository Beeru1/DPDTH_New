package com.ibm.dp.actions;

import java.util.List;
import java.util.Map;

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

import com.ibm.dp.beans.TransferHierarchyBean;
import com.ibm.dp.common.Constants;
import com.ibm.dp.dto.TransferHierarchyDto;
import com.ibm.dp.service.TransferHierarchyService;
import com.ibm.dp.service.impl.TransferHierarchyServiceImpl;
import com.ibm.virtualization.framework.bean.ChannelType;
import com.ibm.virtualization.recharge.authorization.AuthorizationConstants;
import com.ibm.virtualization.recharge.authorization.AuthorizationServiceImpl;
import com.ibm.virtualization.recharge.dto.UserSessionContext;
import com.ibm.virtualization.recharge.service.AuthorizationInterface;

public class TransferHierarchyAction extends DispatchAction 
{
	/* Logger for this class. */
	private static Logger logger = Logger.getLogger(TransferHierarchyAction.class.getName());
	
	private static final String INIT_SUCCESS = "success";
	
	public ActionForward init(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		HttpSession session = request.getSession();
		AuthorizationInterface authorizationService = new AuthorizationServiceImpl();
		UserSessionContext sessionContext = (UserSessionContext) session.getAttribute(Constants.AUTHENTICATED_USER);
		ActionErrors errors = new ActionErrors();
		if (!authorizationService.isUserAuthorized(sessionContext.getGroupId(), ChannelType.WEB, AuthorizationConstants.ROLE_TO_HIERARCHY_TRANSFER)) 
		{
			logger.info(" user not auth to perform hierarchy transfer activity  ");
			errors.add("errors",new ActionError("errors.usernotautherized"));
			saveErrors(request, errors);
			return mapping.findForward(Constants.USER_NOT_AUTHERIZED);
		}
		return mapping.findForward(INIT_SUCCESS);
	}
	
	public ActionForward getChildUser(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		HttpSession session = request.getSession();
		AuthorizationInterface authorizationService = new AuthorizationServiceImpl();
		UserSessionContext sessionContext = (UserSessionContext) session.getAttribute(Constants.AUTHENTICATED_USER);
		TransferHierarchyBean hierForm = (TransferHierarchyBean) form;
		ActionErrors errors = new ActionErrors();
		
		try
		{
			if (!authorizationService.isUserAuthorized(sessionContext.getGroupId(), ChannelType.WEB, AuthorizationConstants.ROLE_TO_HIERARCHY_TRANSFER)) 
			{
				logger.info(" user not auth to perform hierarchy transfer activity  ");
				errors.add("errors",new ActionError("errors.usernotautherized"));
				saveErrors(request, errors);
				return mapping.findForward(Constants.USER_NOT_AUTHERIZED);
			}
			
			logger.info("************getChild in TransferHierarchyAction******************");
			
			String userName = hierForm.getUserName();
			session.setAttribute("userName", userName);
			logger.info("userName*****"+userName);
			
			boolean flag=false;
			
			TransferHierarchyService transHierService = new TransferHierarchyServiceImpl();
			
			//chackInv = wrongShipment.checkWrongInventory(extraSerialNo , productID ,  distId , deliveryChallanNo );
			//List<TransferHierarchyDto> listChildUser = transHierService.getChildUser(userName);	//getting child users in grid values
			//List<TransferHierarchyDto> listParentUser = transHierService.getParentUser(userName);// getting parent users in drop down
			
			Map<String, List<TransferHierarchyDto>> mapHierarchyDetails = transHierService.getHierarchyDetails(userName);
			
			String strMessage = "";
			List<TransferHierarchyDto> listMsg = mapHierarchyDetails.get(Constants.TRANSFER_HIERARCHY_MSG);
			TransferHierarchyDto tempDTO = listMsg.get(0);
			if(!tempDTO.getStrStatus().equals("SUCCESS"))
			{
				hierForm.setStrSuccessMsg(tempDTO.getStrStatus());
			}
			else
			{
				List<TransferHierarchyDto> listChildUser = mapHierarchyDetails.get(Constants.TRANS_HIER_CHILD_DETAILS);
				List<TransferHierarchyDto> listParentUser = mapHierarchyDetails.get(Constants.TRANS_HIER_PARENT_DETAILS);
				hierForm.setTransferHierachyList(listChildUser);
				hierForm.setTransferHierachyParentList(listParentUser);
				request.setAttribute("transferHierachyList", listChildUser);
				request.setAttribute("transferHierachyParentList", listParentUser);
			}
		}
		catch (Exception e) 
		{
			hierForm.setStrSuccessMsg("Error while getting child users");
			e.printStackTrace();
			logger.info("EXCEPTION OCCURED ::  "+e.getMessage());
			errors.add("errors",new ActionError(e.getMessage()));
			saveErrors(request, errors);
			return mapping.findForward(INIT_SUCCESS);
		}
		
		return mapping.findForward(INIT_SUCCESS);
	}
	//saveTransferHierarchy
	public ActionForward saveTransferHierarchy(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		HttpSession session = request.getSession();
		AuthorizationInterface authorizationService = new AuthorizationServiceImpl();
		UserSessionContext sessionContext = (UserSessionContext) session.getAttribute(Constants.AUTHENTICATED_USER);
		TransferHierarchyBean hierForm = (TransferHierarchyBean) form;
		ActionErrors errors = new ActionErrors();
		try
		{
			
			logger.info("======= saveTransferHierarchy method in TransferHierarchyAction==");
			if (!authorizationService.isUserAuthorized(sessionContext.getGroupId(), ChannelType.WEB, AuthorizationConstants.ROLE_TO_HIERARCHY_TRANSFER)) 
			{
				logger.info(" user not auth to perform hierarchy transfer activity  ");
				errors.add("errors",new ActionError("errors.usernotautherized"));
				saveErrors(request, errors);
				return mapping.findForward(Constants.USER_NOT_AUTHERIZED);
			}
			
			logger.info("************saveTransferHierarchy in TransferHierarchyAction******************");
			logger.info("accountNameParentNameMapping :"+hierForm.getAccountNameParentNameMapping());
			String childParentMapping=hierForm.getAccountNameParentNameMapping();
			String strOLMID=(String) session.getAttribute("userName");
			//String strOLMID = hierForm.getUserName();
			logger.info("asa:::action::::strOLMID"+strOLMID);
			
			TransferHierarchyService transHierService = new TransferHierarchyServiceImpl();
			String result = transHierService.saveTransfferedHierarchy(childParentMapping, strOLMID);
			if(result.equals("success"))
			{
				hierForm.setStrSuccessMsg("Hierarchy  Transffered Successfully");
			}
			else
			{
				hierForm.setStrSuccessMsg("Error while Hierarchy Transfer (Hierarchy not transffered)");
			}
			
			
		}
		catch (Exception e) 
		{
			hierForm.setStrSuccessMsg("Error while Hierarchy Transfer (Hierarchy not transffered)");
			e.printStackTrace();
			logger.info("EXCEPTION OCCURED ::  "+e.getMessage());
			errors.add("errors",new ActionError(e.getMessage()));
			saveErrors(request, errors);
			return mapping.findForward(INIT_SUCCESS);
		}
		return mapping.findForward(INIT_SUCCESS);
	}
	
}
