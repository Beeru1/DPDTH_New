package com.ibm.hbo.actions;

import java.util.ArrayList;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.log4j.Logger;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.actions.DispatchAction;

import com.ibm.hbo.dto.HBOUserBean;
import com.ibm.hbo.dto.HBOWarrantyDTO;
import com.ibm.hbo.forms.HBOBusinessCategoryFormBean;
import com.ibm.hbo.forms.HBOProductFormBean;
import com.ibm.hbo.forms.HBOWarehouseFormBean;
import com.ibm.hbo.forms.HBOWarrantyFormBean;
import com.ibm.hbo.services.HBOMasterService;
import com.ibm.hbo.services.HBOWarrantyService;
import com.ibm.hbo.services.impl.HBOMasterServiceImpl;
import com.ibm.hbo.services.impl.HBOWarrantyServiceImpl;

/**
 * @author Sachin
 *
 * To change the template for this generated type comment go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
public class HBOWarrantyAction extends DispatchAction {
		/*
		 * Logger for the class.
		 */
		private static final Logger logger;

		static {
			logger = Logger.getLogger(HBOWarrantyAction.class);
		}
		
		public ActionForward getWarrantyPage(ActionMapping mapping,
					ActionForm form, HttpServletRequest request,
					HttpServletResponse response) throws Exception
		{		
			HttpSession session = request.getSession();
			ActionForward forward = new ActionForward(); // return value
			HBOUserBean hboUserBean = (HBOUserBean) session.getAttribute("USER_INFO");
			String userId = hboUserBean.getUserLoginId(); //(String) session.getAttribute("userId");
			String condition = "";	
			HBOWarrantyFormBean warrantyFormBean = (HBOWarrantyFormBean) form;
			warrantyFormBean.setImeino("");
			warrantyFormBean.setWarehouseName("");
			warrantyFormBean.setDamageStatus("");
			warrantyFormBean.setStWarranty("");
			warrantyFormBean.setExtWarranty(0);
			warrantyFormBean.setPrCode("");
			forward = mapping.findForward("success");
			return (forward);
		}
		
	public ActionForward getWarrantyList(ActionMapping mapping,
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
				HBOWarrantyService warrantyService = new HBOWarrantyServiceImpl();
				HBOWarrantyFormBean warrantyFormBean = (HBOWarrantyFormBean) form;
				HBOWarrantyDTO warrantyDTO=new HBOWarrantyDTO();
				if(mapping.getPath() != null && mapping.getPath().equals("/getWarranty")) {	
					String imeino=warrantyFormBean.getImeino();
					
					arrMasterList = warrantyService.getMasterList(userId,"warranty",imeino,condition);
					
				}
				
				if (arrMasterList==null ||arrMasterList.size()==0){
					forward = mapping.findForward("failure");
					
				}else{
					warrantyDTO= (HBOWarrantyDTO)arrMasterList.get(0);
					BeanUtils.copyProperties(warrantyFormBean,warrantyDTO);
					forward = mapping.findForward("success");
				
				}
				
				
				return (forward);
			}
		public ActionForward insert(ActionMapping mapping,
						ActionForm form, HttpServletRequest request,
						HttpServletResponse response) throws Exception
			{		
				HttpSession session = request.getSession();
				ActionForward forward = new ActionForward(); // return value
				HBOUserBean hboUserBean = (HBOUserBean) session.getAttribute("USER_INFO");
				String userId = hboUserBean.getUserLoginId(); //(String) session.getAttribute("userId");
				logger.info("userId:"+userId);
				String message = "";
				logger.info(mapping.getPath());
				String condition = "";// (String) session.getAttribute("condition");
				
				
				String master = (String) request.getParameter("master");
				HBOWarrantyService warrantyService = new HBOWarrantyServiceImpl();	
				logger.info("In Insert:"+master);			
				if(master != null && master.equals("warranty")) {
					HBOWarrantyFormBean warrantyFormBean = (HBOWarrantyFormBean) form;
					
					message = warrantyService.insert(userId,warrantyFormBean,master,condition);
				
				}
				forward = mapping.findForward(message);
				return (forward);
			}
}
