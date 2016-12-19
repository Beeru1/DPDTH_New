package com.ibm.virtualization.recharge.actions;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;

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
import org.apache.struts.action.ActionMessage;
import org.apache.struts.action.ActionMessages;
import org.apache.struts.actions.DispatchAction;

import com.ibm.dp.beans.DpDcCreationBean;
import com.ibm.dp.dto.DpDcCreationDto;
import com.ibm.dp.dto.DpProductCategoryDto;
import com.ibm.dp.dto.DpProductTypeMasterDto;
import com.ibm.dp.service.DPDcCreationService;
import com.ibm.dp.service.impl.DPDcCreationServiceImpl;
import com.ibm.dpmisreports.common.AppContext;
import com.ibm.dpmisreports.common.SpringCacheUtility;
import com.ibm.virtualization.framework.bean.ChannelType;
import com.ibm.virtualization.recharge.authorization.AuthorizationConstants;
import com.ibm.virtualization.recharge.authorization.AuthorizationServiceImpl;
import com.ibm.virtualization.recharge.beans.DPViewProductFormBean;
import com.ibm.virtualization.recharge.common.Constants;
import com.ibm.virtualization.recharge.dto.DPEditProductDTO;
import com.ibm.virtualization.recharge.dto.UserSessionContext;
import com.ibm.virtualization.recharge.exception.DAOException;
import com.ibm.virtualization.recharge.exception.VirtualizationServiceException;
import com.ibm.virtualization.recharge.service.AuthorizationInterface;
import com.ibm.virtualization.recharge.service.DPMasterService;
import com.ibm.virtualization.recharge.service.impl.DPMasterServiceImpl;

	public class DPEditProductAction  extends DispatchAction {

		private static final Logger logger;
		static {
			logger = Logger.getLogger(DPCreateProductAction.class);
		}
		
		
		public ActionForward edit(ActionMapping mapping, ActionForm form,
				HttpServletRequest request, HttpServletResponse response) throws Exception 
		{
			HttpSession session = request.getSession();
			ActionErrors errors = new ActionErrors();
			UserSessionContext sessionContext = (UserSessionContext) session.getAttribute(Constants.AUTHENTICATED_USER);
			int circleId = sessionContext.getCircleId();
			
	        AuthorizationInterface authorizationService = new AuthorizationServiceImpl();
	        if (!authorizationService.isUserAuthorized(sessionContext.getGroupId(),ChannelType.WEB, AuthorizationConstants.ROLE_EDIT_ACCOUNT)) 
	        {
			    logger.info(" user not auth to perform this ROLE_EDIT_PRODUCT activity  ");
			    errors.add("errors", new ActionError("errors.usernotautherized"));
			    saveErrors(request, errors);
			    return mapping.findForward(Constants.USER_NOT_AUTHERIZED);
	        }	
		//  String productid = request.getParameter("productid");
	        DPViewProductFormBean productFormBean = (DPViewProductFormBean) form;
	        int productid = productFormBean.getProdId();
	      
//	        DPEditProductFormBean editProduct = new DPEditProductFormBean();
//	        editProduct.setProductid(Integer.parseInt(productid));
	        DPMasterService dpms = new DPMasterServiceImpl();
	        ArrayList unitList = new ArrayList();
	     	       
	       	unitList = dpms.getUnitList();
	       	productFormBean.setUnitList(unitList);
	       	
			getProductDetails(mapping, form, request, errors, productid);
			 //Added By Shilpa
	        setDefaultValue(productFormBean, request);
			logger.info(" Executed edit product action ");
			
			//ArrayList bcaList = new ArrayList();
			//String userId = (String) session.getAttribute("userId");
			//bcaList = dpms.getBusinessCategory(userId);
			//log.info("***ABOVE BUSINESS CATEGORY***");
			//productFormBean.setArrBCList(bcaList);
			
//			Add by harbans on Reservation Obserbation 30th June
			List<DpProductTypeMasterDto> reverseProductList = dpms.getAllCommercialProducts(productFormBean.getCircleId());
			productFormBean.setReverseProductList(reverseProductList);
			request.setAttribute("reverseProductList", reverseProductList);
			
			
			return mapping.findForward(Constants.EDIT_PRODUCT);	
		}
		
		private ActionForward getProductDetails(ActionMapping mapping,
				ActionForm form, HttpServletRequest request, ActionErrors errors,
				int productid) throws DAOException {
			DPMasterService productService = new DPMasterServiceImpl();	
			DPViewProductFormBean productFormBean = (DPViewProductFormBean) form;
			int circleId=0;
			try {
							
				DPEditProductDTO product = productService.edit(productid);
				
				String productId = product.getProductTypeId();
				logger.info("product type Id in get product details -- "+productId);
				//String productName = productFormBean.getProductname();
				circleId=product.getCircleId();
				BeanUtils.copyProperties(productFormBean, product);
				
				ArrayList cgList = new ArrayList(); // for card group list
				cgList=productService.getCardGroups(""+circleId);
				productFormBean.setArrCGList(cgList);
				productFormBean.setProductTypeId(productId.trim());
				logger.info("in get  p[roduct Detauils fun item code AV == "+productFormBean.getItmeCodeAv());
				
			}
              catch (VirtualizationServiceException vse) {
				logger.error("  Service Exception occured   ", vse);
				errors.add("errors.account", new ActionError(vse.getMessage()));
			} catch (IllegalAccessException iaExp) {
				logger.error(						" caught Exception while using BeanUtils.copyProperties()",						iaExp);
				saveErrors(request, errors);
				return mapping.findForward(Constants.PRODUCT_ERROR);
			} 
			catch (InvocationTargetException itExp) {
				ArrayList unitList = new ArrayList(); 
				unitList = productService.getUnitList();
				productFormBean.setUnitList(unitList);
				logger.error(						" caught Exception while using BeanUtils.copyProperties()",						itExp);
				ArrayList cgList = new ArrayList(); // for card group list
				cgList=productService.getCardGroups(""+circleId);
				productFormBean.setArrCGList(cgList);
				System.out.println(circleId);
				System.out.println(productFormBean.getCirclename());
				saveErrors(request, errors);
				return mapping.findForward(Constants.PRODUCT_ERROR);
			} 
			if (!errors.isEmpty()) {
				saveErrors(request, errors);
				return mapping.findForward(Constants.PRODUCT_ERROR);
			}
			return mapping.findForward(Constants.EDIT_PRODUCT);

		}
			
		public ActionForward Updatedata(ActionMapping mapping, ActionForm form,
				HttpServletRequest request, HttpServletResponse response)
		throws VirtualizationServiceException, NumberFormatException, DAOException {
			
			ActionForward forward = new ActionForward();
			HttpSession session = request.getSession();			
			UserSessionContext sessionContext = (UserSessionContext) session
			.getAttribute(Constants.AUTHENTICATED_USER);
			ActionErrors errors = new ActionErrors();
		//	DPEditProductFormBean prodDPBean = (DPEditProductFormBean) form;
			DPViewProductFormBean prodDPBean = (DPViewProductFormBean) form;
			//DPEditProductDTO dpepDTO = new DPEditProductDTO();
			DPMasterService dpservice = new DPMasterServiceImpl();
//			 errors.add("message.product", new ActionError(
//			"message.product.insert_success"));
//			 saveErrors(request, errors);
			 String message="";
			try {
				String userId = ""+sessionContext.getId();
				prodDPBean.setUpdatedby(userId);
				prodDPBean.setCreatedby(userId);
				logger.info("Product type id in edit function action class ==== "+prodDPBean.getProductTypeId());	
				message = dpservice.edit(prodDPBean);
//				forward = mapping.findForward(message);
				ActionMessages messages = new ActionMessages();
				logger.info("in Action Edit Message === "+message) ;
				if(message.equalsIgnoreCase("failure")){
					messages.add("ERROR_OCCURED", new ActionMessage("error.occured"));
				}
				else{
					messages.add("MESSAGE_SENT_SUCCESS",new ActionMessage("update.success"));
				}	
				saveMessages(request, messages);
			} catch (VirtualizationServiceException e) {
				ArrayList unitList = new ArrayList(); 
				unitList = dpservice.getUnitList();
				prodDPBean.setUnitList(unitList);
				logger.error("Exception occured while reteriving Update Product Action"
						+ "Exception Message in action part: ", e);
				if(e.getMessage().equalsIgnoreCase("Error.Card.Group")){
					errors.add("errors.product",new ActionError("Error.Card.Group"));
				}
				if(e.getMessage().equals("exception.update")){
				errors.add("errors.product",new ActionError("exception.update"));
				}if(e.getMessage().equals("errors.product.combinationExist")){
				errors.add("errors.product",new ActionError("errors.product.combinationExist"));
				}if(e.getMessage().equals("errors.product.parentExist")){
				errors.add("errors.product",new ActionError("errors.product.parentExist"));
				}
				//added by Karan on 7th June 2012 for validating unique Oracle Item Code
				if(e.getMessage().equalsIgnoreCase("error.oracle.item")){
					errors.add("errors.product",new ActionError("error.oracle.item"));
				}
				
				saveErrors(request, errors);
				return mapping.findForward("failure");
			}
			logger.info("Executed... ");
			return mapping.findForward(message);
		}

		private String valueOf(String freeservice) {
			// TODO Auto-generated method stub
			return null;
		}
		//Added by Shilpa 
		
		
		private void setDefaultValue(DPViewProductFormBean productFormBean,
				HttpServletRequest request) throws Exception 
		{
			logger.info("in setDefaultValue... ");
			DPMasterService dcProductService = new DPMasterServiceImpl();
			List<DpProductTypeMasterDto> dcProductTypeList = dcProductService.getProductTypeMaster();

			productFormBean.setDcProductTypeList(dcProductTypeList);
			request.setAttribute("dcProductTypeList", dcProductTypeList);
			String productCategory = productFormBean.getCategorycode()+"";			
			logger.info("in setDefaultValue executed... productCategory == "+productCategory);
			//Added by Shilpa Khanna for Population product category list
			if(productCategory != null  && !productCategory.equals("")) 
			{
				List<DpProductCategoryDto> dcProductCategList = dcProductService.getProductCategoryLst(productCategory);
				productFormBean.setDcProductCategoryList(dcProductCategList);
				request.setAttribute("dcProductCategoryList", dcProductCategList);
			}
		}
	}