package com.ibm.dp.actions;

import java.util.ArrayList;
import java.util.List;

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

import com.ibm.dp.beans.RecoPeriodConfFormBean;
import com.ibm.dp.dto.RecoPeriodDTO;
import com.ibm.dp.service.RecoPeriodConfService;
import com.ibm.dp.service.impl.RecoPeriodConfServiceImpl;

public class RecoPeriodConfPaginationAction extends DispatchAction
{
	/* Logger for this class. */
	private static Logger logger = Logger.getLogger(RecoPeriodConfPaginationAction.class.getName());
	
	private static final String SUCCESS_RECO_HISTORY = "successRecoHistory";
	private static final String ERROR = "error";
	
	
	public ActionForward init(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		logger.info("-----------------getRecoHistory----------------");		
		try{
		RecoPeriodConfFormBean recoBean = (RecoPeriodConfFormBean)form;
	
		recoBean.setStrMessage("");
		recoBean.setFromDate("");
		recoBean.setToDate("");
		getRecoPeriodHistory(request, recoBean );
		getGracePeriod(recoBean );
		recoBean.setGracePeriodReco(recoBean.getGracePeriod());
		}catch (Exception e) {
			logger.error("Error:" + e.getMessage());
			e.printStackTrace();
			ActionErrors errors = new ActionErrors();
			errors.add(ActionErrors.GLOBAL_ERROR, new ActionError("system.error"));
			saveErrors(request, errors);
			return mapping.findForward(ERROR);

		}
		return mapping.findForward(SUCCESS_RECO_HISTORY);
		
	
	}
	public void getRecoPeriodHistory(HttpServletRequest request, RecoPeriodConfFormBean recoBean) throws Exception{
		List<RecoPeriodDTO> recoHistoryList = new ArrayList<RecoPeriodDTO>();		
		RecoPeriodConfService recoPeriodConfService = RecoPeriodConfServiceImpl.getInstance();
		recoHistoryList = recoPeriodConfService.getRecoHistory();	
		HttpSession session = request.getSession();
		String lastToDate ="";
		if(!recoHistoryList.isEmpty()){
		lastToDate = recoHistoryList.get(0).getToDate();
		String arr[]=lastToDate.split("-");
		lastToDate = arr[1]+"-"+arr[0]+"-"+arr[2];
		}
		System.out.println("lastToDate---"+lastToDate);
		recoBean.setLastToDate(lastToDate);		
		session.removeAttribute("recoHistoryList");
		session.setAttribute("recoHistoryList",recoHistoryList);
		recoBean.setRecoList(recoHistoryList);
	}
	
	public ActionForward updateRecoPeriodConf(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) throws Exception{
		return init( mapping,  form, request,  response);
	}
	
	
	public ActionForward addRecoPeriod(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) throws Exception{
		
		return init( mapping,  form, request,  response);
		
	}
	public void getGracePeriod(ActionForm form) throws Exception{
		RecoPeriodConfFormBean recoBean = (RecoPeriodConfFormBean)form;
		RecoPeriodConfService recoPeriodConfService = RecoPeriodConfServiceImpl.getInstance();
		
		String gracePeriod = recoPeriodConfService.getGracePeriod();	
		recoBean.setGracePeriod(gracePeriod);
	}
	
	public ActionForward updateGracePeriod(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		return init( mapping,  form, request,  response);
	}
	
}
