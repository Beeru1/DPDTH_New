package com.ibm.dp.actions;

import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.actions.DispatchAction;
import org.dom4j.io.XMLWriter;

import com.ibm.dp.beans.RecoSummaryFormBean;
import com.ibm.dp.common.Constants;
import com.ibm.dp.dto.RecoSummaryDTO;
import com.ibm.dp.service.RecoSummaryService;
import com.ibm.dp.service.impl.RecoSummaryServiceImpl;
import com.ibm.dpmisreports.common.SelectionCollection;
import com.ibm.dpmisreports.service.DropDownUtilityAjaxService;
import com.ibm.dpmisreports.service.impl.DropDownUtilityAjaxServiceImpl;
import com.ibm.virtualization.recharge.dto.UserSessionContext;


public class RecoSummaryPaginationAction extends DispatchAction {
private static Logger logger = Logger.getLogger(RecoSummaryPaginationAction.class.getName());

	
	private final static String SUCCESS = "successExcel";
	private static final String REGEX_COMMA = ",";	

	public ActionForward execute(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)throws Exception {
	
		HttpSession session = request.getSession();
		RecoSummaryFormBean recoSummaryFormBean = (RecoSummaryFormBean) form;
		System.out.println("status value in recoSummaryReport inprogress");
		session.setAttribute("recoSummaryReport", "inprogress"); //Added by Neetika BFR 16 Release 3 on 16 Aug
		recoSummaryFormBean.setCircleList(getCircleList(request));
		
		recoSummaryFormBean.setRecoStatusList((List) session.getAttribute("recoStatusList"));

		recoSummaryFormBean = loadReport(request, recoSummaryFormBean);
	
		logger.info("Completed Execute method of execute Pagination....!!");
		return mapping.findForward(SUCCESS);
	}
	
	/**
	 * load report
	 * @param request
	 * @param RecoSummaryFormBean
	 * @return RecoSummaryFormBean
	 * @throws Exception
	 */
	public RecoSummaryFormBean loadReport(HttpServletRequest request,
			RecoSummaryFormBean recoSummaryFormBean)
			throws Exception {
		HttpSession session = request.getSession();
		UserSessionContext sessionContext = (UserSessionContext) session.getAttribute(Constants.AUTHENTICATED_USER);
		
		Integer circleId = 0;
		
			circleId = recoSummaryFormBean.getCircleId();
		
			Pattern commaPattern = Pattern.compile(REGEX_COMMA);
			
			String[] circleIdArr = commaPattern.split(recoSummaryFormBean.getCircleIdArr()[0]);
			ArrayList<Integer> circleIdArrList = new ArrayList<Integer>();
			for (String s : circleIdArr) {
				circleIdArrList.add(Integer.valueOf(s));
			}
	
		String status= recoSummaryFormBean.getRecoStatus();
		
		ArrayList<RecoSummaryDTO> recoSummaryDTOList = new ArrayList<RecoSummaryDTO>();
		RecoSummaryService recoSummaryService = new RecoSummaryServiceImpl();
		System.out.println("request.getParameter recoid" +request.getParameter("recoID"));
		int recoID = Integer.parseInt(request.getParameter("recoID"));
		recoSummaryDTOList = recoSummaryService.getReport(circleIdArrList, status, recoID);
		
		session.removeAttribute("recoSummaryDTOList");
		session.setAttribute("recoSummaryDTOList",recoSummaryDTOList);
		recoSummaryFormBean.setRecoSummaryList(recoSummaryDTOList);
		
		recoSummaryFormBean.setCircleList(getCircleList(request));
		return recoSummaryFormBean;
	}
	
	public List<SelectionCollection> getCircleList(HttpServletRequest request) throws Exception {

		DropDownUtilityAjaxService utilityAjaxService = new DropDownUtilityAjaxServiceImpl();
		HttpSession session = request.getSession();

		List<SelectionCollection> circleList = utilityAjaxService.getAllCircles();
		
		session.removeAttribute("circleList");
		session.setAttribute("circleList", circleList);
		return circleList;
	}
	public ActionForward getReportDownloadStatus(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)throws Exception 
	{
		//System.out.println("inside getReportDownloadStatus");
		try
		{
			HttpSession session = request.getSession();
			
			String status = (String) session.getAttribute("recoSummaryReport");
			System.out.println("status value in reco summary is "+status);
			String result = "";
			if(status != null)
				result = status;
			
			response.setHeader("Cache-Control", "No-Cache");
			response.setContentType("text/xml");
			PrintWriter out = response.getWriter();
			XMLWriter writer = new XMLWriter(out);
			writer.write(result);
			writer.flush();
			out.flush();
		

		}
		catch (Exception e) 
		{
			e.printStackTrace();
			logger.error("Exception occured while getReportDownloadStatus  ::  "+e.getMessage());
		}
		return null;
	}
}



