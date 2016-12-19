package com.ibm.dp.actions;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.actions.DispatchAction;

import com.ibm.dp.beans.RecoSummaryFormBean;
import com.ibm.dp.dto.RecoPeriodDTO;
import com.ibm.dp.service.RecoDetailReportService;
import com.ibm.dp.service.RecoPeriodConfService;
import com.ibm.dp.service.impl.RecoDetailReportServiceImpl;
import com.ibm.dp.service.impl.RecoPeriodConfServiceImpl;
import com.ibm.dpmisreports.common.SelectionCollection;
import com.ibm.dpmisreports.service.DropDownUtilityAjaxService;
import com.ibm.dpmisreports.service.impl.DropDownUtilityAjaxServiceImpl;
import com.ibm.reports.service.GenericReportsService;
import com.ibm.reports.service.impl.GenericReportsServiceImpl;
import com.ibm.virtualization.recharge.dto.UserSessionContext;


public class RecoSummaryAction extends DispatchAction {
private static Logger logger = Logger.getLogger(RecoSummaryAction.class.getName());

	private final static String SUCCESS = "initSuccess";
	private final static String LOGINSUCCESS = "loginSuccess";
	
	public ActionForward init(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {

		HttpSession session = request.getSession();
        RecoSummaryFormBean recoSummaryFormBean = (RecoSummaryFormBean)form;
        session.removeAttribute("circleList");
        if(recoSummaryFormBean != null)
        {
            recoSummaryFormBean.reset(mapping, request);
        }
        recoSummaryFormBean.setRecoStatus("");
        recoSummaryFormBean.setCircleId(Integer.valueOf(0));
        UserSessionContext sessionContext = (UserSessionContext)session.getAttribute("USER_INFO");
        Integer groupId = sessionContext.getGroupId();
        recoSummaryFormBean.setGroupId(groupId.toString());			
       ActionErrors errors = new ActionErrors();
		String circleIdSr = String.valueOf(sessionContext.getCircleId());
		List<SelectionCollection> ciList = new ArrayList<SelectionCollection>();
		
		DropDownUtilityAjaxService utilityAjaxService = new DropDownUtilityAjaxServiceImpl();
		List<SelectionCollection> listAllCircles = new ArrayList<SelectionCollection>();
		
		
		recoSummaryFormBean.setCircleId(-1);
		if(Integer.parseInt(circleIdSr)!=0){				
			recoSummaryFormBean.setCircleId(Integer.parseInt(circleIdSr));
			if(groupId ==7){					
				SelectionCollection sc = new SelectionCollection();
				sc.setStrText(sessionContext.getCircleName());
				sc.setStrValue((Integer.toString(sessionContext.getCircleId())));
				ciList.add(sc);
				recoSummaryFormBean.setCircleList(ciList);
				
							
			}
			else if(groupId ==6){
				SelectionCollection sc = new SelectionCollection();
				sc.setStrText(sessionContext.getCircleName());
				sc.setStrValue((Integer.toString(sessionContext.getCircleId())));
				ciList.add(sc);
				recoSummaryFormBean.setCircleList(ciList);
				
																
			}else if(groupId ==3 || groupId ==4 ||groupId ==5){					
				/*SelectionCollection sc = new SelectionCollection();
				sc.setStrText(sessionContext.getCircleName());
				sc.setStrValue((Integer.toString(sessionContext.getCircleId())));
				
				ciList.add(sc);*/
				listAllCircles = utilityAjaxService.getTsmCircles(sessionContext.getId());
				
				recoSummaryFormBean.setCircleList(listAllCircles);					
				
			}else if(groupId==2){
				SelectionCollection sc = new SelectionCollection();
				sc.setStrText(sessionContext.getCircleName());
				sc.setStrValue((Integer.toString(sessionContext.getCircleId())));
				
				ciList.add(sc);
				recoSummaryFormBean.setCircleList(ciList);
			}
			
			
		}else{				
			recoSummaryFormBean.setCircleId(-2);
			utilityAjaxService = new DropDownUtilityAjaxServiceImpl();	
			
			ciList = utilityAjaxService.getAllCircles();
			recoSummaryFormBean.setCircleList(ciList);
			request.setAttribute("circleList", ciList);
		}
        
        List<RecoPeriodDTO> recoList = new ArrayList<RecoPeriodDTO>();	
		List recoListTotal = new ArrayList();	
		
		RecoDetailReportService recoDetailReportService = RecoDetailReportServiceImpl.getInstance();
		recoList = recoDetailReportService.getRecoHistory();
	
		for(RecoPeriodDTO recoPeriodDTO :recoList){
			SelectionCollection recoPeriod=new SelectionCollection();
			 recoPeriod.setStrText(recoPeriodDTO.getFromDate() + " to " +recoPeriodDTO.getToDate());
			 recoPeriod.setStrValue( recoPeriodDTO.getRecoPeriodId());				
			recoListTotal.add(recoPeriod);
		}
		
		recoSummaryFormBean.setRecoListTotal(recoListTotal);
		
		return mapping.findForward(SUCCESS);
	}
		
	public List<SelectionCollection> getCircleList(HttpServletRequest request) throws Exception {

		DropDownUtilityAjaxService utilityAjaxService = new DropDownUtilityAjaxServiceImpl();
		HttpSession session = request.getSession();

		List<SelectionCollection> circleList = utilityAjaxService.getAllCircles();
		
		session.removeAttribute("circleList");
		session.setAttribute("circleList", circleList);
		return circleList;
	}
}
