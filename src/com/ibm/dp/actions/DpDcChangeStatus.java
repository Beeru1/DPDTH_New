package com.ibm.dp.actions;

import java.util.ArrayList;
import java.util.List;
import java.util.ListIterator;
import java.util.regex.Pattern;

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

import com.ibm.dp.beans.DpDcChangeStatusBean;
import com.ibm.dp.beans.DpDcCreationBean;
import com.ibm.dp.common.Constants;
import com.ibm.dp.dto.DpDcChangeStatusDto;
import com.ibm.dp.dto.DpDcCreationDto;
import com.ibm.dp.dto.DpDcReverseStockInventory;
import com.ibm.dp.service.DPDcCreationService;
import com.ibm.dp.service.DpDcChangeStatusService;
import com.ibm.dp.service.impl.DPDcCreationServiceImpl;
import com.ibm.dp.service.impl.DpDcChangeStatusServiceImpl;
import com.ibm.virtualization.recharge.dto.UserSessionContext;


public class DpDcChangeStatus extends DispatchAction {
private static Logger logger = Logger.getLogger(DpDcChangeStatus.class.getName());
	
	private static final String INIT_SUCCESS = "initSuccess";
	private static final String STATUS_ERROR = "error";
	/**
	 *  
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return  success or failure
	 * @throws Exception
	 * @desc initial information which fetch the dcs from the table and display in table list [infront end screen].
	 */
	public ActionForward initDcStatus(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
   logger.info("************** DpDcChangeStatus -> initDcStatus() method. Called****************");
		
		// Get account Id form session.
		HttpSession session = request.getSession();
		ActionErrors errors = new ActionErrors();
		DpDcChangeStatusBean reverseDCStatusBean =(DpDcChangeStatusBean)form;
		
		try
		{
			UserSessionContext sessionContext = (UserSessionContext) session.getAttribute(Constants.AUTHENTICATED_USER);
			Long distId = sessionContext.getId();
			logger.info("************** distId =="+distId);
			setDefaultValue(reverseDCStatusBean, request,distId);
			request.setAttribute("hidArrDcNos", "");
		}
		catch(Exception ex)
		{
			ex.printStackTrace();
			logger.info("EXCEPTION OCCURED ::  "+ex.getMessage());
			errors.add("errors",new ActionError(ex.getMessage()));
			saveErrors(request, errors);
			return mapping.findForward(STATUS_ERROR);
		}
		return mapping.findForward(INIT_SUCCESS);

			
	}
	
	private void setDefaultValue(DpDcChangeStatusBean reverseDCStatusBean,
			HttpServletRequest request,Long lngCrBy) throws Exception {
			DpDcChangeStatusService dcNosService = new DpDcChangeStatusServiceImpl();
			List<DpDcChangeStatusDto> dcNosList = dcNosService.getAllDCList(lngCrBy);

			reverseDCStatusBean.setDcNosList(dcNosList);
		request.setAttribute("dcNosList", dcNosList);
		
	}
	
	/**
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return  success or failure
	 * @throws Exception
	 * @desc Submit the Change Status
	 */
	public ActionForward saveCancelStatus(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		logger.info("***************************** saveCancelStatus() method. Called*****************************");
		
		HttpSession session = request.getSession();
		DpDcChangeStatusBean reverseDCStatusBean =(DpDcChangeStatusBean)form;
		String strMessage ="";
		try{
			//setDefaultValue(reverseDCStatusBean, request);
			UserSessionContext sessionContext = (UserSessionContext) session.getAttribute(Constants.AUTHENTICATED_USER);
			Long distId = sessionContext.getId();
			String circleId = String.valueOf(sessionContext.getCircleId());
			logger.info(" distId =="+distId+" circleId == "+circleId );
		
			//Pattern commaPattern = Pattern.compile(REGEX_COMMA);
		
			String[] arrDcNos =null;
			if(reverseDCStatusBean.getHidArrDcNos()!=null){
				arrDcNos =reverseDCStatusBean.getHidArrDcNos();
				logger.info(" arrDcNos length == "+arrDcNos.length + " arrDcNos[0] == "+arrDcNos[0]);
			}
			String[] arrDcNosNew =null;
			String strSuccessMsg ="";
			if(arrDcNos!=null){
				arrDcNosNew =arrDcNos[0].split(",");
			}
			if(arrDcNosNew!=null){
				DpDcChangeStatusService dcNosService = new DpDcChangeStatusServiceImpl();
				strSuccessMsg = dcNosService.setDCStatus(arrDcNosNew);
			}
			if(strSuccessMsg.equals("SUCCESS")){
				strMessage ="DC has been Canceled Successfully !!";
			}
			else
			{
				strMessage = strSuccessMsg;
			}
			setDefaultValue(reverseDCStatusBean, request,distId);
			request.setAttribute("hidArrDcNos", "");
			
		}catch(Exception ex){
			ex.printStackTrace();
			strMessage =ex.getMessage();
			return mapping.findForward(STATUS_ERROR);
		}finally{
			reverseDCStatusBean.setStrSuccessMsg(strMessage);
			
			
		}
		return mapping.findForward(INIT_SUCCESS);
	}
	
	    
	public ActionForward viewSerialsStatus(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) throws Exception {
			DpDcChangeStatusService dcNosService = new DpDcChangeStatusServiceImpl();
			String dc_no=request.getParameter("invoiceNo");
			try{
				
				List<DpDcChangeStatusDto> dcNosList = dcNosService.viewSerialsStatus(dc_no);
				DpDcChangeStatusBean dcbean = (DpDcChangeStatusBean) form;
				dcbean.setDcNosList(dcNosList);
				request.setAttribute("dcNosList", dcNosList);
				System.out.println("Size::: "+dcNosList.size());
			
			}catch(Exception ex){
				ex.printStackTrace();
			}
			return mapping.findForward("initSuccessDcStatus");
	}
}
