package com.ibm.dp.actions;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.servlet.ServletException;
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
import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.dom4j.io.OutputFormat;
import org.dom4j.io.XMLWriter;

import com.ibm.dp.beans.DPViewHierarchyFormBean;
import com.ibm.dp.common.Constants;
import com.ibm.dp.dto.DPViewHierarchyDTO;
import com.ibm.dp.service.DPViewHierarchyReportService;
import com.ibm.dp.service.impl.DPViewHierarchyReportServiceImpl;
import com.ibm.virtualization.recharge.dto.UserSessionContext;
import com.ibm.virtualization.recharge.service.DPMasterService;
import com.ibm.virtualization.recharge.service.impl.DPMasterServiceImpl;

public class DPViewHierarchyAction extends DispatchAction{
	private Logger logger = Logger.getLogger(DPViewHierarchyAction.class.getName());
	private static final String INIT_SUCCESS = "initsuccess";
	private static final String INIT_SUCCESS_EXCEL = "successExcel";
	private static final String INIT_SUCCESS_EXCEL_HIERARCHY = "successExcelHierarchy";
	
	
	public ActionForward init(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)	throws Exception 
			{
//	 Get account Id form session.
	logger.info("In initExceptionReport() method of view Hierarchy Report......");
	ActionErrors errors = new ActionErrors();
	DPViewHierarchyFormBean viewhierarchyReportBean = (DPViewHierarchyFormBean) form;
	HttpSession session = request.getSession();
	UserSessionContext sessionContext = (UserSessionContext) session.getAttribute(Constants.AUTHENTICATED_USER);
	
	String userId = (String) session.getAttribute("userId");
	viewhierarchyReportBean.setCircleid(sessionContext.getCircleId());
	int loginRole = Integer.parseInt(sessionContext.getAccountLevel());
	DPMasterService dpms = new DPMasterServiceImpl();
	ArrayList ciList = new ArrayList();
	ArrayList cgList = new ArrayList(); // for card group list
	try {
		
		//log.info("***ABOVE BUSINESS CATEGORY***");
		ciList = dpms.getCircleID(userId);
		viewhierarchyReportBean.setArrCIList(ciList);
		if(viewhierarchyReportBean.getCircleid() != 0){
			viewhierarchyReportBean.setShowCircle(Constants.ACCOUNT_NOT_SHOW_CIRCLE);
			cgList=dpms.getCardGroups(""+viewhierarchyReportBean.getCircleid());
			
			
			int roleId = sessionContext.getCircleId();
			DPViewHierarchyReportService viewhierarchyReportService = new DPViewHierarchyReportServiceImpl();
			
			List tsmList = viewhierarchyReportService.getviewhierarchyTsmAccountDetails(roleId);
			viewhierarchyReportBean.setTsmList(tsmList);
			request.setAttribute("tsmList",tsmList);
			logger.info("tsmList list size : " + tsmList.size());
			System.out.println(request.getParameter("circleId"));
			int circleId = sessionContext.getCircleId();
			
			List distList = viewhierarchyReportService.getViewHierarchyDistAccountDetails(roleId , circleId);
			viewhierarchyReportBean.setDistList(distList);
			//request.setAttribute("distList",distList);
			logger.info("distList list size : " + distList.size());
			logger.info("loginRole : " + loginRole);
			
				viewhierarchyReportBean.setShowTSM(Constants.ACCOUNT_SHOW_CIRCLE);
		}else{
			logger.info("444444444444444444");
			viewhierarchyReportBean.setShowCircle(Constants.ACCOUNT_SHOW_CIRCLE);
			cgList=dpms.getCardGroups("0");
			
			
		}
		viewhierarchyReportBean.setArrCGList(cgList);
	}
	catch(Exception e)
	{ 
		e.printStackTrace();
		logger.info("EXCEPTION OCCURED ::  "+e.getMessage());
		errors.add("errors",new ActionError(e.getMessage()));
		saveErrors(request, errors);
		
	
		logger.error("Exception in init method of DPViewHierarchyAction::"+e);
		return mapping.findForward(INIT_SUCCESS);
	}
		
	return mapping.findForward(INIT_SUCCESS);
	
			}
	
	
	
	public ActionForward initTsmReport(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws IOException, ServletException 
	{
		logger.info("In method initStatusAccount().");
		
		try 
		{
			//	Get account Id form session.
			HttpSession session = request.getSession();
			UserSessionContext sessionContext = (UserSessionContext) session.getAttribute(Constants.AUTHENTICATED_USER);
			Long accId = sessionContext.getId();
			int tsmId = Integer.parseInt(String.valueOf(accId));
			
			DPViewHierarchyFormBean revLogReportBean = (DPViewHierarchyFormBean) form;
			String levelId = request.getParameter("levelId");
			logger.info("Fetch account name of role :" + levelId);
			int roleId = Integer.parseInt(levelId);
			DPViewHierarchyReportService revLogReportService = new DPViewHierarchyReportServiceImpl();
			
			List accountList = revLogReportService.getviewhierarchyTsmAccountDetails(roleId);
			revLogReportBean.setTsmList(accountList);
			request.setAttribute("productStatusList",accountList);
			logger.info("Account list size : " + accountList.size());
			
						
			// Ajax start
			Document document = DocumentHelper.createDocument();
			Element root = document.addElement("options");
			Element optionElement;
			
			Iterator iter = accountList.iterator();
			while (iter.hasNext()) 
			{
				DPViewHierarchyFormBean statusReportBean = (DPViewHierarchyFormBean) iter.next();
				optionElement = root.addElement("option");
				optionElement.addAttribute("text", statusReportBean.getAccountName());
				optionElement.addAttribute("value", statusReportBean.getAccountId());
			}

			// For ajax
			response.setHeader("Cache-Control", "No-Cache");
			response.setContentType("text/xml");
			PrintWriter out = response.getWriter();
			OutputFormat outputFormat = OutputFormat.createCompactFormat();
			XMLWriter writer = new XMLWriter(out);
			writer.write(document);
			writer.flush();
			out.flush();
			// End for ajax
		

		} catch (Exception ex) 
		{
			logger.error("Exception in initTsmReport method of DPViewHierarchyAction::"+ex);
		}

		return null;
	}
	
	
	public ActionForward initDistReport(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws IOException, ServletException 
			{
				logger.info("In method initStatusAccount().");
				ActionErrors errors = new ActionErrors();
				try 
				{
					//	Get account Id form session.
					HttpSession session = request.getSession();
					UserSessionContext sessionContext = (UserSessionContext) session.getAttribute(Constants.AUTHENTICATED_USER);
					Long accId = sessionContext.getId();
					int distId = Integer.parseInt(String.valueOf(accId));
					
					DPViewHierarchyFormBean viewhierarchyReportBean = (DPViewHierarchyFormBean) form;
					String levelId = request.getParameter("parentId");
					int circleId = Integer.parseInt(request.getParameter("circleId"));
					
					logger.info("Fetch account name of role :" + levelId);
					int roleId = Integer.parseInt(levelId);
					DPViewHierarchyReportService revLogReportService = new DPViewHierarchyReportServiceImpl();
					
					List accountList = revLogReportService.getViewHierarchyDistAccountDetails(roleId , circleId);
					viewhierarchyReportBean.setGetHierarchyList(accountList);
					session.removeAttribute("arrList");
					session.setAttribute("arrList", accountList);
					request.setAttribute("getHierarchyList",accountList);
					logger.info("Account list size : " + accountList.size());
					
					String userId = (String) session.getAttribute("userId");
					viewhierarchyReportBean.setCircleid(sessionContext.getCircleId());
					int loginRole = Integer.parseInt(sessionContext.getAccountLevel());
					DPMasterService dpms = new DPMasterServiceImpl();
					ArrayList ciList = new ArrayList();
					ArrayList cgList = new ArrayList(); // for card group list
					try { 
						//log.info("***ABOVE BUSINESS CATEGORY***");
						ciList = dpms.getCircleID(userId);
						viewhierarchyReportBean.setArrCIList(ciList);
						//if(viewhierarchyReportBean.getCircleid() != 0){
					System.out.println("sessionContext.getAccountLevel()==="+sessionContext.getAccountLevel());
					
					if(sessionContext.getAccountLevel().equalsIgnoreCase("0") || sessionContext.getAccountLevel().equalsIgnoreCase("1"))
					{
							viewhierarchyReportBean.setShowCircle(Constants.ACCOUNT_SHOW_CIRCLE);
					}
					else
					{
							viewhierarchyReportBean.setShowCircle(Constants.ACCOUNT_NOT_SHOW_CIRCLE);
					}
						cgList=dpms.getCardGroups(""+viewhierarchyReportBean.getCircleid());
							
						int roleId1 = sessionContext.getCircleId();
						
						roleId= roleId1;
						DPViewHierarchyReportService viewhierarchyReportService = new DPViewHierarchyReportServiceImpl();
						
						List tsmList = viewhierarchyReportService.getviewhierarchyTsmAccountDetails(circleId);
						viewhierarchyReportBean.setTsmList(tsmList);
						request.setAttribute("tsmList",tsmList);
						logger.info("tsmList list size : " + tsmList.size());
						System.out.println(request.getParameter("circleId"));
						int circleId1 = sessionContext.getCircleId();
						circleId = circleId1;
						List distList = viewhierarchyReportService.getViewHierarchyDistAccountDetails(roleId , circleId);
						viewhierarchyReportBean.setDistList(distList);
						//request.setAttribute("distList",distList);
						logger.info("distList list size : " + distList.size());
						logger.info("loginRole : " + loginRole);
							
						viewhierarchyReportBean.setShowTSM(Constants.ACCOUNT_SHOW_CIRCLE);
						viewhierarchyReportBean.setArrCGList(cgList);
						viewhierarchyReportBean.setCircleid(Integer.parseInt(request.getParameter("circleId")));
						viewhierarchyReportBean.setStrGetReport("true");
					}
					catch(Exception ex)
					{
						logger.error("Exception in initDistReport method of DPViewHierarchyAction::"+ex);
					}
			} catch (Exception e) 
				{
				e.printStackTrace();
				logger.info("EXCEPTION OCCURED ::  "+e.getMessage());
				errors.add("errors",new ActionError(e.getMessage()));
				saveErrors(request, errors);
				return mapping.findForward(INIT_SUCCESS);
			}
				
				return mapping.findForward(INIT_SUCCESS);
			}
	
	public ActionForward initDistReportExcel(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws IOException, ServletException 
			
			{
		ActionErrors errors = new ActionErrors();
				try {
					DPViewHierarchyFormBean viewhierarchyReportBean = (DPViewHierarchyFormBean) form;
					viewhierarchyReportBean.setGetHierarchyList((List)request.getSession().getAttribute("arrList"));
				} catch (RuntimeException e) {
					e.printStackTrace();
					logger.info("EXCEPTION OCCURED ::  "+e.getMessage());
					errors.add("errors",new ActionError(e.getMessage()));
					saveErrors(request, errors);
					return mapping.findForward(INIT_SUCCESS_EXCEL);
				}
				return mapping.findForward(INIT_SUCCESS_EXCEL);
				
			}
	public ActionForward viewAllHierarchy(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws IOException, ServletException 
			{
				DPViewHierarchyFormBean viewhierarchyReportBean = (DPViewHierarchyFormBean) form;
				String[] arrDist = viewhierarchyReportBean.getStrCheckedVHD();
				DPViewHierarchyReportService viewhierarchyReportService = new DPViewHierarchyReportServiceImpl();
				ActionErrors errors = new ActionErrors();
			try
			{
				List hierarchyList = viewhierarchyReportService.getHierarchyAll(arrDist);
				viewhierarchyReportBean.setHierarchyList(hierarchyList);
				request.setAttribute("hierarchyList", hierarchyList);
				
			}
			catch(Exception ex)
			{

				ex.printStackTrace();
				logger.info("EXCEPTION OCCURED ::  "+ex.getMessage());
				errors.add("errors",new ActionError(ex.getMessage()));
				saveErrors(request, errors);
				return mapping.findForward(INIT_SUCCESS_EXCEL);
			
			}
				//viewhierarchyReportBean.setGetHierarchyList((List)request.getSession().getAttribute("arrList"));
				return mapping.findForward(INIT_SUCCESS_EXCEL_HIERARCHY);
			}
}
