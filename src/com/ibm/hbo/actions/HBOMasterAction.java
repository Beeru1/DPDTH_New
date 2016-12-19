package com.ibm.hbo.actions;

import java.util.ArrayList;
import java.util.HashMap;

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
import org.apache.struts.action.ActionError;
import org.apache.struts.action.ActionErrors;
import com.ibm.hbo.dto.HBOUserBean;
import com.ibm.hbo.forms.HBOBusinessCategoryFormBean;
import com.ibm.hbo.forms.HBOProductFormBean;
import com.ibm.hbo.forms.HBOWarehouseFormBean;
import com.ibm.hbo.services.HBOMasterService;
import com.ibm.hbo.services.UserService;
import com.ibm.hbo.services.impl.HBOMasterServiceImpl;
import com.ibm.hbo.services.impl.UserServiceImpl;
import com.ibm.hbo.common.HBOLibrary;

/**
 * @author Sachin
 *
 * To change the template for this generated type comment go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
public class HBOMasterAction extends DispatchAction {
		/*
		 * Logger for the class.
		 */
		private static final Logger logger;

		static {
			logger = Logger.getLogger(HBOMasterAction.class);
		}
		
		HBOLibrary hboLibrary = new HBOLibrary();

		public ActionForward getValues(ActionMapping mapping,
					ActionForm form, HttpServletRequest request,
					HttpServletResponse response) throws Exception
		{		
			HttpSession session = request.getSession();
			ActionForward forward = new ActionForward(); // return value
			ArrayList arrMasterList = new ArrayList(); 
			HBOUserBean hboUserBean = (HBOUserBean) session.getAttribute("USER_INFO");
			String userId = hboUserBean.getUserLoginId(); //(String) session.getAttribute("userId");
			String condition = ""; 
			logger.info("userId:"+userId);
			logger.info("Parent WarehouseId:"+hboUserBean.getWarehouseID());
			// Get Available Sim Stock for the logged In Circle Admin
			HBOMasterService masterService = new HBOMasterServiceImpl();
			UserService createUserService = new UserServiceImpl();
			

			if(mapping.getPath() != null && mapping.getPath().equals("/initBusinessCategory")) {	
				arrMasterList = masterService.getMasterList(userId,"businesscategory",condition);
				HBOBusinessCategoryFormBean businessCategoryFormBean =  (HBOBusinessCategoryFormBean) form;
				businessCategoryFormBean.setArrBCList(arrMasterList);
			}
			if(mapping.getPath() != null && mapping.getPath().equals("/initProduct")) {
				// Get DropDown Values of Business Category
				arrMasterList = masterService.getMasterList(userId,"businesscategory",condition);
				HBOProductFormBean productFormBean = (HBOProductFormBean) form;
				productFormBean.setArrBCList(arrMasterList);

				// Get Data of Products
				arrMasterList = masterService.getMasterList(userId,"product",condition);
				
				productFormBean.setArrProductList(arrMasterList);
			}
			
			if(mapping.getPath() != null && mapping.getPath().equals("/initWarehouse")) {
				// Get DropDown Values of State
				arrMasterList = masterService.getMasterList(userId,"state",condition);
				if(arrMasterList!=null || arrMasterList.size() == 0) {
					logger.info("No records fetched in State Master");
				}
				HBOWarehouseFormBean warehouseFormBean = (HBOWarehouseFormBean) form;
				//	changes on 23rd jan by sachin
							  warehouseFormBean.setState("");
							  warehouseFormBean.setCircle("");
							  warehouseFormBean.setRole(0);
							  // end of changes

				warehouseFormBean.setArrStateList(arrMasterList);
				//Get Drop down values of Circle
				//==Done on 12th march====
				if(hboUserBean.getActorId().equalsIgnoreCase("2")){
					arrMasterList = masterService.getMasterList(userId,"viewcircle",hboUserBean.getWarehouseID());
				}
				else{
					arrMasterList = masterService.getMasterList(userId,"circle",condition);
				}
					warehouseFormBean.setArrCircleList(arrMasterList);
				//==end of Changes	
				
				
				if(arrMasterList==null || arrMasterList.size() == 0) {
					logger.info("No records fetched in Circle Master");
				}

				//Get Drop down values of Role
				//arrMasterList = masterService.getMasterList(userId,"role",condition);
				
				warehouseFormBean.setArrRoleList((ArrayList) createUserService.getUserTypeService(hboUserBean.getActorId()));
							
				//warehouseFormBean.setArrRoleList(arrMasterList);
				
				if(arrMasterList==null || arrMasterList.size() == 0) {
					logger.info("No records fetched in Role Master");
				}

				// Get Data of Warehouse and send role id as paramete name=userId
				//	condition=userId;// pass userId in condition
				  arrMasterList = masterService.getMasterList(hboUserBean.getActorId(),"viewWarehouse",userId);// changes

				if( arrMasterList==null || arrMasterList.size() == 0) {
					logger.info("No records fetched in Warehouse Master");
				}
				warehouseFormBean.setArrWarehouseList(arrMasterList);
				
			}
			
			//if(arrMasterList.size() == 0) {
				//errors.add("No Records Found");
			//}
			logger.info("Success");
			forward = mapping.findForward("success");
			return (forward);
		}
		public ActionForward insert(ActionMapping mapping,
						ActionForm form, HttpServletRequest request,
						HttpServletResponse response) throws Exception
			{	
				ActionMessages messages = new ActionMessages();
				ActionErrors errors = new ActionErrors();	
				HttpSession session = request.getSession();
				ActionForward forward = new ActionForward(); // return value
				HBOUserBean hboUserBean = (HBOUserBean) session.getAttribute("USER_INFO");
				String userId = hboUserBean.getUserLoginId(); //(String) session.getAttribute("userId");
				logger.info("userId:"+userId);
				String warehouseId=hboLibrary.checkNullInteger(hboUserBean.getWarehouseID());
				if(warehouseId.equals("")) {
					warehouseId = "0"; // In case of Mobility set Default Warehouse to 0	
				}
				String message = "";
				logger.info(mapping.getPath());
				String condition = "";// (String) session.getAttribute("condition");
				
				
				String master = (String) request.getParameter("master");
				HBOMasterService masterService = new HBOMasterServiceImpl();	
				logger.info("In Insert:"+master);			
				if(master != null && master.equals("businesscategory")) {
					if (isTokenValid(request,true)){ 
					HBOBusinessCategoryFormBean businessCategoryFormBean = (HBOBusinessCategoryFormBean) form;
					message = masterService.insert(userId,businessCategoryFormBean,master,condition);
				}else{
					message="success";
				}
				}
				if(master != null && master.equals("product")) {
					if (isTokenValid(request,true)){ 
					HBOProductFormBean productFormBean = (HBOProductFormBean) form;
					message = masterService.insert(userId,productFormBean,master,condition);
					logger.info("message"+message);
				}else{
					message="success";
				}
				}
				if(master != null && master.equals("warehouse")) {
					if (isTokenValid(request,true)){ 
					logger.info("In Insert:"+master);
				HBOWarehouseFormBean warehouseFormBean = (HBOWarehouseFormBean) form;
				logger.info("In Insert111:");
				warehouseFormBean.setParentWarehouseId(Integer.parseInt(warehouseId));
				logger.info("In Insert2222");
				message = masterService.insert(userId,warehouseFormBean,master,condition);
				logger.info("In Insert3333");
				}else
				{
					message="success";	
				}
				}
				if(message.equalsIgnoreCase("success")){
					messages.add("INSERT_SUCCESS",new ActionMessage("insert.success"));
					saveMessages(request, (ActionMessages) messages);
				}
				if(message.equalsIgnoreCase("failure")){
					errors.add("ERROR_OCCURED",new ActionError("error.occured"));
					saveErrors(request, (ActionErrors) errors);
				}
				forward = mapping.findForward(message);
				saveToken(request);
				return (forward);
			}
			

			
}
