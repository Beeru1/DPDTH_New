package com.ibm.dp.actions;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
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

import com.ibm.dp.beans.InterSSDFormBean;
import com.ibm.dp.common.Constants;
import com.ibm.dp.dto.InterSSDTransferDTO;
import com.ibm.dp.service.StockTransferService;
import com.ibm.dp.service.impl.StockTransferServiceImpl;
import com.ibm.virtualization.recharge.authorization.AuthorizationServiceImpl;
import com.ibm.virtualization.recharge.dto.UserSessionContext;
import com.ibm.virtualization.recharge.service.AuthorizationInterface;

public class DPInterSSDTransferAction extends DispatchAction {

	private static Logger logger = Logger.getLogger(DPInterSSDTransferAction.class
			.getName());
	private static final String REGEX_COMMA = ",";
	
	
	public ActionForward init(ActionMapping mapping, ActionForm form, // init for assign to retailer 
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {

		logger.info("-----------------init ACTION CALLED-----------------");
		ActionErrors errors = new ActionErrors();
		InterSSDFormBean dpinterssdFormBean = (InterSSDFormBean) form;
			
			/* Logged in user information from session */
			// Getting login ID from session
			HttpSession session = request.getSession();
			
			UserSessionContext sessionContext = (UserSessionContext) session.getAttribute(Constants.AUTHENTICATED_USER);
			AuthorizationInterface authorizationService = new AuthorizationServiceImpl();
			long loginUserId = sessionContext.getId();
			
			StockTransferService dpMSAService = new StockTransferServiceImpl();
			List<InterSSDTransferDTO> initDataList = dpMSAService.interSSDTrans(new Long(sessionContext.getCircleId()));
			
			dpinterssdFormBean.setArrList(initDataList);
			
			
		return mapping.findForward("success");

	}
	public ActionForward getInterssdDetails(ActionMapping mapping, ActionForm form, // init for assign to retailer 
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {

		logger.info("-----------------init ACTION CALLED-----------------");
		ActionErrors errors = new ActionErrors();
		InterSSDFormBean dpinterssdFormBean = (InterSSDFormBean) form;
			
			/* Logged in user information from session */
			// Getting login ID from session
			HttpSession session = request.getSession();
			
			UserSessionContext sessionContext = (UserSessionContext) session.getAttribute(Constants.AUTHENTICATED_USER);
			AuthorizationInterface authorizationService = new AuthorizationServiceImpl();
			long loginUserId = sessionContext.getId();
			String trans_no=request.getParameter("TRNO");
			String transType=request.getParameter("transType");
			String transsubtype=request.getParameter("transsubtype");
			Map map = new HashMap();
	try
	{
			
			StockTransferService dpMSAService = new StockTransferServiceImpl();
			//StockTransferService dpMSAServicedist = new StockTransferServiceImpl();
			//StockTransferService dpMSACurrentdist = new StockTransferServiceImpl();
			
			Map initDataList = dpMSAService.interSSDTransDetails(trans_no,transType,transsubtype,sessionContext.getCircleId());
			//Iterator itt1=null;
			dpinterssdFormBean.setArrList((List)initDataList.get("details"));
			dpinterssdFormBean.setDistList((List)initDataList.get("distList"));
			List list_1 = (List)initDataList.get("currdistList");
			Iterator itt = list_1.iterator();
			if(itt.hasNext())
			{
				
				InterSSDTransferDTO interSSDTransferDTOObj  =   (InterSSDTransferDTO)itt.next();
				dpinterssdFormBean.setCurrent_dist_name(interSSDTransferDTOObj.getAccount_name());
				dpinterssdFormBean.setCurrent_dist_id(interSSDTransferDTOObj.getAccount_id());
			}
			if(transType.equalsIgnoreCase(Constants.INTER_SSD_RETAILER_TRANSFER_DISPLAY))
			{
				List list1 =(List)initDataList.get("currfseList");
				Iterator itt1 = list1.iterator();
				
				if(itt1.hasNext())
				{
					InterSSDTransferDTO interSSDTransferDTOObj1 = (InterSSDTransferDTO)itt1.next();
					
					dpinterssdFormBean.setCurrent_fse_name(interSSDTransferDTOObj1.getAccount_name());
					dpinterssdFormBean.setCurrent_fse_id(interSSDTransferDTOObj1.getAccount_id());
				}
				
			}
			
			dpinterssdFormBean.setCircle_id(sessionContext.getCircleId()+"");
			
			
			
	}
	catch(Exception ex)
	{
		ex.printStackTrace();
		logger.info("EXCEPTION OCCURED ::  "+ex.getMessage());
		errors.add("errors",new ActionError(ex.getMessage()));
		saveErrors(request, errors);
		return mapping.findForward("successdetails");
	}
			
			//List<InterSSDTransferDTO> distList= dpMSAServicedist.distList(sessionContext.getCircleId());
			//InterSSDTransferDTO interSSDTransferDTO = dpMSACurrentdist.getCurrentDist(trans_no);
			
			
			
			
			
			dpinterssdFormBean.setTrans_type(transType);
		return mapping.findForward("successdetails");

	}
	public ActionForward submitHirerchyTransfer(ActionMapping mapping, ActionForm form, // init for assign to retailer 
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {

		logger.info("-----------------init ACTION CALLED-----------------");
		ActionErrors errors = new ActionErrors();
		InterSSDFormBean dpinterssdFormBean = (InterSSDFormBean) form;
			
			/* Logged in user information from session */
			// Getting login ID from session
			HttpSession session = request.getSession();
try {		
			UserSessionContext sessionContext = (UserSessionContext) session.getAttribute(Constants.AUTHENTICATED_USER);
			AuthorizationInterface authorizationService = new AuthorizationServiceImpl();
			long loginUserId = sessionContext.getId();
			
			Pattern commaPattern = Pattern.compile(REGEX_COMMA);
			
			String[] arrTransId = dpinterssdFormBean.getTransaction_Id().split(REGEX_COMMA);
			
			
			InterSSDTransferDTO hirerchyTransferDTO = null;
			ArrayList<InterSSDTransferDTO> hirerchyTransferList = new ArrayList<InterSSDTransferDTO>();
			
			for(int count=0;count<arrTransId.length;count++){
				hirerchyTransferDTO = new InterSSDTransferDTO();
				
				
				//logger.info(" productIdListItr.next() =="+arrProdId[count] + "  sr no == "+arrSrNo[count]);
				if(arrTransId[count].indexOf("#") > 0)
				{
					hirerchyTransferDTO.setTr_no(arrTransId[count].split("#")[0]);
					hirerchyTransferDTO.setAccount_id(arrTransId[count].split("#")[1]);
					hirerchyTransferDTO.setTo_dist(arrTransId[count].split("#")[2]);
					if(dpinterssdFormBean.getTransaction_type().equalsIgnoreCase(com.ibm.dp.common.Constants.INTER_SSD_RETAILER_TRANSFER_DISPLAY))
					{
						hirerchyTransferDTO.setTo_fse(arrTransId[count].split("#")[3]);
						
					}
					hirerchyTransferDTO.setTransaction_type(dpinterssdFormBean.getTransaction_type());
					hirerchyTransferDTO.setTransfer_by(sessionContext.getId()+"");
					hirerchyTransferList.add(hirerchyTransferDTO);
				}
			
			}
			StockTransferService dpMSAService = new StockTransferServiceImpl();
			
			String str =dpMSAService.hirarchyTransfer(hirerchyTransferList);
			dpinterssdFormBean.setStr_msg("SUCCESS");
	} catch (Exception e) {
		e.printStackTrace();
		logger.info("EXCEPTION OCCURED ::  "+e.getMessage());
		errors.add("errors",new ActionError(e.getMessage()));
		saveErrors(request, errors);
		return mapping.findForward("successdetails");
	}		
			
		return mapping.findForward("successdetails");

	}
		
}
