/*
 * Created on Nov 21, 2007
 *
 * To change the template for this generated file go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
package com.ibm.hbo.actions;
import java.io.PrintWriter;
import java.util.ArrayList;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.apache.log4j.Logger;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessage;
import org.apache.struts.action.ActionMessages;
import org.apache.struts.actions.DispatchAction;
import org.apache.commons.beanutils.BeanUtils;
import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.dom4j.io.OutputFormat;
import org.dom4j.io.XMLWriter;

import com.ibm.virtualization.recharge.authorization.AuthorizationFactory;
import com.ibm.virtualization.recharge.common.Constants;
import com.ibm.virtualization.recharge.dto.UserSessionContext;
import com.ibm.virtualization.recharge.service.AuthorizationInterface;
import com.ibm.dp.service.DPPurchaseOrderService;
import com.ibm.dp.service.impl.DPPurchaseOrderServiceImpl;
import com.ibm.hbo.common.HBOLibrary;
import com.ibm.hbo.common.HBOUser;
import com.ibm.hbo.dto.HBOProductDTO;
import com.ibm.hbo.dto.HBORequisitionDTO;
import com.ibm.hbo.forms.HBOCreateRequisitionFormBean;
import com.ibm.hbo.services.HBOMasterService;
import com.ibm.hbo.services.HBORequisitionService;
import com.ibm.hbo.services.impl.HBOMasterServiceImpl;
import com.ibm.hbo.services.impl.HBORequisitionServiceImpl;
import com.ibm.hbo.common.HBOConstants;
import com.ibm.virtualization.framework.bean.ChannelType;
/**
 * @author Admin
 *
 * To change the template for this generated type comment go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
public class HBORequisitionAction extends DispatchAction {

	private static final Logger logger;

	static {

		logger = Logger.getLogger(HBORequisitionAction.class);
	}

	/* Local Variables */
//	private static String INITCREATEUSER_SUCCESS = "initCreateUser";
//
//	private static String CREATEUSER_SUCCESS = "CreateUserSuccess";
//
//	private static String CREATEUSER_FAILURE = "CreateUserFailure";
//
//	private static String USERCREATED_SUCCESS = "UserCreatedSuccess";
//
//	private static String EDIT_USER_SUCCESS = "EditUserSuccess";
	
	public ActionForward populateValues(
			ActionMapping mapping,
			ActionForm form,
			HttpServletRequest request,
			HttpServletResponse response)
			throws Exception{
			HttpSession session = request.getSession();
			ArrayList arrBussList = null;
			ArrayList arrProdList = null;
			ArrayList arrWarehouseList = null;
			HBOCreateRequisitionFormBean requisitionFormBean = (HBOCreateRequisitionFormBean) form;
			UserSessionContext userSessionContext = (UserSessionContext) session.getAttribute(Constants.AUTHENTICATED_USER);
			HBOUser hboUser = new HBOUser(userSessionContext);
			HBOMasterService masterService = new HBOMasterServiceImpl();
			String userId = hboUser.getUserId();
			requisitionFormBean.reset();				
			arrBussList = masterService.getMasterList(userId,HBOConstants.BUSINESS_CATEGORY_EXT,"");
			arrWarehouseList=masterService.getMasterList(userId,HBOConstants.WAREHOUSE,"");
			requisitionFormBean.setArrProduct(arrProdList);
			requisitionFormBean.setArrWarehouse(arrWarehouseList);
			requisitionFormBean.setArrBusinessCategory(arrBussList);
			saveToken(request);
			return mapping.findForward(HBOConstants.SUCCESS_MESSAGE);
	}
	
	public ActionForward createRequisition(
			ActionMapping mapping,
			ActionForm form,
			HttpServletRequest request,
			HttpServletResponse response)
			throws Exception{
				
				HttpSession session = request.getSession();
				ActionMessages messages = new ActionMessages();
				HBOCreateRequisitionFormBean requisitionFormBean = (HBOCreateRequisitionFormBean) form;
				HBORequisitionDTO reqDTO = new HBORequisitionDTO();
				BeanUtils.copyProperties(reqDTO,requisitionFormBean);
				UserSessionContext userSessionContext = (UserSessionContext) session.getAttribute(Constants.AUTHENTICATED_USER);
				HBOUser hboUser = new HBOUser(userSessionContext);
				HBORequisitionService requisitionService = new HBORequisitionServiceImpl();
				int userId = Integer.parseInt(hboUser.getWarehouseID());
				String message=HBOConstants.SUCCESS_MESSAGE;
				ArrayList params = null;
				if (isTokenValid(request,true)){
					params = new ArrayList();
					params.add(userId);
					params.add(reqDTO);
					message = requisitionService.insert(params);
				}
				if(message.equalsIgnoreCase(HBOConstants.ERROR_MESSAGE)){
					messages.add(HBOConstants.ERROR_OCCURED_MESSAGE,new ActionMessage("error.occured"));
					saveMessages(request, messages);
					return mapping.findForward(message);
				}
				if(message.equalsIgnoreCase(HBOConstants.SUCCESS_MESSAGE)){
					messages.add(HBOConstants.INSERT_SUCCESS_MESSAGE,new ActionMessage("insert.success"));
					saveMessages(request, messages);
				}
				return mapping.findForward(message);
		}
		
		public ActionForward getChange(ActionMapping mapping,ActionForm form, HttpServletRequest request,HttpServletResponse response) throws Exception
			{
				HttpSession session = request.getSession();
				Document document = DocumentHelper.createDocument();
				Element root = document.addElement("options");
				Element optionElement;
				//
				//String userid="";
				//String condition="";
				UserSessionContext userSessionContext = (UserSessionContext) session.getAttribute(Constants.AUTHENTICATED_USER);
				int circleid=userSessionContext.getCircleId();
				String cond="";
				String id=request.getParameter("Id");
				cond=request.getParameter("cond");
				//int x = Integer.parseInt(id);
				ArrayList param = new ArrayList();
				param.add(id);
				param.add(cond);
				//param.add("");
				//param.add("");
				//param.add(null);
				HBOMasterService masterService = new HBOMasterServiceImpl();
				ArrayList arrGetValue=new ArrayList();
				arrGetValue=masterService.getChange(param);

				HBOProductDTO getChangeDTO = new HBOProductDTO(); 
				
				for (int counter = 0; counter < arrGetValue.size(); counter++) {
					
					optionElement = root.addElement("option");
					getChangeDTO = (HBOProductDTO)arrGetValue.get(counter);
					if (getChangeDTO != null) {
						optionElement.addAttribute("value",getChangeDTO.getProductId());
						optionElement.addAttribute("text",getChangeDTO.getProductcode());
					} else {
						optionElement.addAttribute("value", "None");
						optionElement.addAttribute("text", "None");
					}				
				}
				response.setContentType("text/xml");
				response.setHeader("Cache-Control", "No-Cache");
				PrintWriter out = response.getWriter();
				OutputFormat outputFormat = OutputFormat.createCompactFormat();
				XMLWriter writer = new XMLWriter(out);

				writer.write(document);
				writer.flush();
				out.flush();
				
				return null;
			}
		
	public ActionForward viewRequisitions
	(ActionMapping mapping,ActionForm form, 
	HttpServletRequest request,HttpServletResponse response) throws Exception {
		HBOLibrary hboLibrary = new HBOLibrary();
		HttpSession session = request.getSession();
		//ActionErrors errors = new ActionErrors();
		ActionMessages message = new ActionMessages();
		HBOCreateRequisitionFormBean requisitionFormBean = (HBOCreateRequisitionFormBean) form;
		UserSessionContext userSessionContext = (UserSessionContext) session.getAttribute(Constants.AUTHENTICATED_USER);
		HBOUser hboUser = new HBOUser(userSessionContext);
		int warehouseid = Integer.parseInt(hboLibrary.checkNullInteger(hboUser.getWarehouseID()));		
		//int roleid = Integer.parseInt(hboLibrary.checkNullInteger(hboUser.getActorId()));
		ArrayList params=new ArrayList();
		AuthorizationInterface authorizationInterface = AuthorizationFactory.getAuthorizerImpl();
		ArrayList roleList = authorizationInterface.getUserCredentials(userSessionContext.getGroupId(), ChannelType.WEB);
		HBORequisitionService reqService = new HBORequisitionServiceImpl();
		params.add(warehouseid);
		params.add(roleList);
		//params.add(reqDto);
		//ArrayList requisitionList = reqService.getRequisitions(warehouseid,roleid,"condition");
		ArrayList requisitionList = reqService.getRequisitions(params);
		logger.info("in requisition");
		if(requisitionList.size()==0){
			logger.info("no requisition data found");
			message.add("RecordMissingMessage",new ActionMessage("messages.recordmissing"));
			logger.info("-------------------"+message.get("RecordMissingMessage").toString());
			saveMessages(request, message);
		}
		if(roleList.contains("ROLE_ND")){
			requisitionFormBean.setUserType("ROLE_ND");
		}
		else{
			requisitionFormBean.setUserType("ROLE_MA");
		}
		requisitionFormBean.setArrRequisitionList(requisitionList);
		request.setAttribute("requisitionList",requisitionList);
		return mapping.findForward("success");
	}
	public ActionForward populateValuesValidate(
			ActionMapping mapping,
			ActionForm form,
			HttpServletRequest request,
			HttpServletResponse response)
			throws Exception{
			HttpSession session = request.getSession();
			ArrayList arrBussList = null;
			ArrayList arrProdList = null;
			ArrayList arrWarehouseList = null;
			HBOCreateRequisitionFormBean requisitionFormBean = (HBOCreateRequisitionFormBean) form;
			UserSessionContext userSessionContext = (UserSessionContext) session.getAttribute(Constants.AUTHENTICATED_USER);
			HBOUser hboUser = new HBOUser(userSessionContext);
			HBOMasterService masterService = new HBOMasterServiceImpl();
			String userId = hboUser.getUserId();
			//requisitionFormBean.reset();				
			arrBussList = masterService.getMasterList(userId,HBOConstants.BUSINESS_CATEGORY_EXT,"");
			arrWarehouseList=masterService.getMasterList(userId,HBOConstants.WAREHOUSE,"");
			requisitionFormBean.setArrProduct(arrProdList);
			requisitionFormBean.setArrWarehouse(arrWarehouseList);
			requisitionFormBean.setArrBusinessCategory(arrBussList);
			saveToken(request);
			return mapping.findForward(HBOConstants.SUCCESS_MESSAGE);
	}
}