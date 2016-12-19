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

import org.apache.commons.beanutils.BeanUtils;
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
import com.ibm.dp.beans.HierarchyTransferBean;
import com.ibm.dp.beans.InterSsdTransferAdminBean;
import com.ibm.dp.beans.StockSummaryReportBean;
import com.ibm.dp.common.Constants;
import com.ibm.dp.dto.CircleDto;
import com.ibm.dp.dto.DistributorDTO;
import com.ibm.dp.dto.FseDTO;
import com.ibm.dp.dto.HierarchyTransferDto;
import com.ibm.dp.dto.RetailorDTO;
import com.ibm.dp.service.DPViewHierarchyReportService;
import com.ibm.dp.service.HierarchyTransferService;
import com.ibm.dp.service.InterSsdTransferAdminService;
import com.ibm.dp.service.StockSummaryReportService;
import com.ibm.dp.service.impl.DPViewHierarchyReportServiceImpl;
import com.ibm.dp.service.impl.HierarchyTransferServiceImpl;
import com.ibm.dp.service.impl.InterSsdTransferAdminServiceImpl;
import com.ibm.dp.service.impl.StockSummaryReportServiceImpl;
import com.ibm.ejs.ras.SystemOutStream;
import com.ibm.virtualization.recharge.dto.UserSessionContext;

public class HierarchyTransferAction extends DispatchAction {

	private static Logger logger = Logger
			.getLogger(HierarchyTransferAction.class.getName());

	private static final String INIT_SUCCESS = "initSuccess";

	private static final String STATUS_ERROR = "error";

	/**
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return success or failure
	 * @throws Exception
	 * @desc initial information which fetch the Data from the table and display
	 *       in Frontend [infront end screen].
	 */
	public ActionForward initHierarchyTransfer(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		logger
				.info("************** HierarchyTransferAction -> initHierarchyTransfer() method. Called****************");
		ActionErrors errors = new ActionErrors();
		// Get account Id form session.
		HttpSession session = request.getSession();
		HierarchyTransferBean reverseDCStatusBean = (HierarchyTransferBean) form;
		try {
			UserSessionContext sessionContext = (UserSessionContext) session
					.getAttribute(Constants.AUTHENTICATED_USER);
			Long distId = sessionContext.getId();
			logger.info("************** distId ==" + distId
					+ " **** account Level === "
					+ sessionContext.getAccountLevel());
			setDefaultValue(reverseDCStatusBean, request, distId.toString(),
					sessionContext.getAccountLevel());
			
			//if login through circle admin
			int groupId = sessionContext.getGroupId();
			String circleName ="";
			CircleDto  circleDto = null;
			int circleId =0;
			List<CircleDto> circleList = new ArrayList();
			HierarchyTransferService dcNosService = new HierarchyTransferServiceImpl();
			if(groupId == 3){
				circleName = sessionContext.getCircleName();
				logger.info("************** circle name ==" + circleName);
				circleId = sessionContext.getCircleId();
				logger.info("************** circleId name ==" + circleId);
				//Neetika 
				circleList = dcNosService.getAllCircleListCircleAdmin(distId);
				
				reverseDCStatusBean.setCircleList(circleList);
				
			}else{
			// Setting Circle List in Form
			
			circleList = dcNosService.getAllCircleList();
			reverseDCStatusBean.setCircleList(circleList);
			reverseDCStatusBean.setFromTSMList(null);
			reverseDCStatusBean.setToTSMList(null);
			}

		} catch (Exception e) {
			e.printStackTrace();
			logger.info("EXCEPTION OCCURED ::  " + e.getMessage());
			errors.add("errors", new ActionError(e.getMessage()));
			saveErrors(request, errors);
			return mapping.findForward(INIT_SUCCESS);
		}
		return mapping.findForward(INIT_SUCCESS);

	}

	private void setDefaultValue(HierarchyTransferBean reverseDCStatusBean,
			HttpServletRequest request, String strLoginId, String accountLevel)
			throws Exception {
		HierarchyTransferService dcNosService = new HierarchyTransferServiceImpl();

		List<DistributorDTO> dcDistributorList = dcNosService.getAllDistList(
				strLoginId, accountLevel);

		reverseDCStatusBean.setFromDistributorList(dcDistributorList);
		request.setAttribute("fromDistributorList", dcDistributorList);
		reverseDCStatusBean.setStrLoginId(accountLevel);
		request.setAttribute("strLoginId", accountLevel);
		if (accountLevel.equals("6")) {
			System.out.println("hi acclweevbfbebfjrgg ^^^^^^^^^^^^^");
			reverseDCStatusBean.setFromDistributorId(strLoginId.trim());
			// reverseDCStatusBean.setFromDistributorId("467");
			// request.setAttribute("fromDistributorId", "467");

		}
	}

	/**
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 * @desc This method fetch the FSE list from the table
	 */
	public ActionForward getFSEList(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		logger.info(" in getFSEList   *** ********");

		Document document = DocumentHelper.createDocument();
		Element root = document.addElement("options");
		Element optionElement;

		String selected = "";
		selected = request.getParameter("selected");
		logger.info(" in getFSEList   *** selected Dist === " + selected);
		HierarchyTransferService ddpo = new HierarchyTransferServiceImpl();

		FseDTO dppoDTO = new FseDTO();
		try {
			List<FseDTO> dcDistributorListDTO = ddpo.getAllFSeList(selected);

			for (int counter = 0; counter < dcDistributorListDTO.size(); counter++) {
				optionElement = root.addElement("option");
				dppoDTO = dcDistributorListDTO.get(counter);
				if (dppoDTO != null) {
					optionElement.addAttribute("value", dppoDTO.getAccountId());// change
																				// to
																				// product_id
					optionElement
							.addAttribute("text", dppoDTO.getAccountName());
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

		} catch (Exception e) {
			logger
					.error("**********->ERROR IN FETCHING FSE NAME LIST [getFSEList] function"
							+ e);

		}
		return null;
	}// end getFseList

	/**
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 * @desc This method fetch the Retailor list from the table
	 */
	public ActionForward getRetlrList(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		logger.info(" in getFSEList   *** ********");

		Document document = DocumentHelper.createDocument();
		Element root = document.addElement("options");
		Element optionElement;

		String selected = "";
		selected = request.getParameter("selected");
		logger.info(" in getFSEList   *** selected Dist === " + selected);
		HierarchyTransferService ddpo = new HierarchyTransferServiceImpl();

		RetailorDTO dppoDTO = new RetailorDTO();
		try {
			String strFSEId = selected.split(",")[0];
			logger.info(" in getFSEList   *** selected strFSEId === "
					+ strFSEId);
			List<RetailorDTO> dcDistributorListDTO = ddpo
					.getAllRetlerList(strFSEId);

			for (int counter = 0; counter < dcDistributorListDTO.size(); counter++) {
				optionElement = root.addElement("option");
				dppoDTO = dcDistributorListDTO.get(counter);
				if (dppoDTO != null) {
					optionElement.addAttribute("value", dppoDTO.getAccountId());// change
																				// to
																				// product_id
					optionElement
							.addAttribute("text", dppoDTO.getAccountName());
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

		} catch (Exception e) {
			logger
					.error("**********->ERROR IN FETCHING FSE NAME LIST [getFSEList] function"
							+ e);

		}
		return null;
	}// end getRetlrList

	public ActionForward performTransfer(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		logger
				.info("***************************** performDcCreation() method. Called*****************************");

		HttpSession session = request.getSession();
		HierarchyTransferBean reverseCollectionBean = (HierarchyTransferBean) form;
		String strMessage = "";
		// AccountFormBean bean = (AccountFormBean) form;
		HierarchyTransferDto hierchyDto = new HierarchyTransferDto();
		
		try {
			// setDefaultValue(reverseCollectionBean, request);
			UserSessionContext sessionContext = (UserSessionContext) session
					.getAttribute(Constants.AUTHENTICATED_USER);
			Long distId = sessionContext.getId();
			String circleId = String.valueOf(sessionContext.getCircleId());
			logger.info(" distId ==" + distId + " circleId == " + circleId);

			BeanUtils.copyProperties(hierchyDto, reverseCollectionBean);
			logger.info(" dto values === rdbTransfer == "
					+ hierchyDto.getRdbTransfer()
					+ "  || hiddenFSESelecIds == "
					+ hierchyDto.getHiddenFSESelecIds()
					+ " ||  hierchyDto.getHiddenRetlrSelecIds  ====="
					+ hierchyDto.getHiddenRetlrSelecIds());
			String strTrNo = "";
			HierarchyTransferService dcCollectionStockService = new HierarchyTransferServiceImpl();
			hierchyDto.setStrLoginId(distId.toString());   // created on
			
			/* Added By Parnika to check Transaction types of distributors */	
			boolean isExclusive = false;
			
			isExclusive = dcCollectionStockService.isMutuallyExclusive(hierchyDto.getFromDistributorId(), hierchyDto.getToDistId());
			if(isExclusive){
				strMessage = "Cannot Transfer.From Distributor and To Distributor have different transaction Types.";
			}
			else{
				
				strTrNo = dcCollectionStockService.insertHierarchyTransfer(
						hierchyDto, circleId);

				strMessage = "Hierarchy Transfer have been done successfully : "
						+ strTrNo;
			}
			
			/* End of changes By parnika */
			
			setDefaultValue(reverseCollectionBean, request, distId.toString(),
					sessionContext.getAccountLevel());
			
//			if login through circle admin
			int groupId = sessionContext.getGroupId();
			String circleName ="";
			CircleDto  circleDto = null;
			int circle =0;
			List<CircleDto> circleList = new ArrayList();
			if(groupId == 3){
				circleName = sessionContext.getCircleName();
				logger.info("************** circle name ==" + circleName);
				circle = sessionContext.getCircleId();
				logger.info("************** circleId name ==" + circleId);
				circleDto = new CircleDto();
				circleDto.setCircleId(String.valueOf(circleId));
				circleDto.setCircleName(circleName);
				circleList.add(circleDto);
				reverseCollectionBean.setCircleList(circleList);
				
			}else{
			// Setting Circle List in Form
			HierarchyTransferService dcNosService = new HierarchyTransferServiceImpl();
			circleList = dcNosService.getAllCircleList();
			reverseCollectionBean.setCircleList(circleList);
			reverseCollectionBean.setFromTSMList(null);
			reverseCollectionBean.setToTSMList(null);
			}

		} catch (Exception ex) {
			ex.printStackTrace();
			strMessage = ex.getMessage();
			return mapping.findForward(STATUS_ERROR);
		} finally {
			reverseCollectionBean.setStrSuccessMsg(strMessage);

		}
		return mapping.findForward(INIT_SUCCESS);
	}
	public ActionForward getTsmList(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws IOException, ServletException 
	{
		logger.info("In method initStatusAccount().");
		
		try 
		{
			HierarchyTransferBean reverseDCStatusBean = (HierarchyTransferBean) form;
			String levelId = request.getParameter("levelId");
			logger.info("Fetch Circle name of role :" + levelId);
			int roleId = Integer.parseInt(levelId);
			DPViewHierarchyReportService revLogReportService = new DPViewHierarchyReportServiceImpl();
			
			List accountList = revLogReportService.getviewhierarchyTsmAccountDetails(roleId);
			reverseDCStatusBean.setFromTSMList(accountList);
			reverseDCStatusBean.setToTSMList(accountList);
			request.setAttribute("TsmList",accountList);
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
			ex.printStackTrace();
			logger.error("Exception in getTsmList method of InterSsdTransferAdminAction::"+ex);
		}

		return null;
	}
	
	public ActionForward getFromTsmList(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws IOException, ServletException 
	{
		logger.info("In method initStatusAccount().");
		
		try 
		{
			
			
			HierarchyTransferBean reverseDCStatusBean = (HierarchyTransferBean) form;
			String levelId = request.getParameter("levelId");
			logger.info("Fetch Circle name of role :" + levelId);
			int roleId = Integer.parseInt(levelId);
			DPViewHierarchyReportService revLogReportService = new DPViewHierarchyReportServiceImpl();
			
			List accountList = revLogReportService.getviewhierarchyFromTsmAccountDetails(roleId);
			reverseDCStatusBean.setFromTSMList(accountList);
			reverseDCStatusBean.setToTSMList(accountList);
			request.setAttribute("TsmList",accountList);
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
			ex.printStackTrace();
			logger.error("Exception in ::"+ex);
		}

		return null;
	}
	
	public ActionForward getFromDistList(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws IOException, ServletException 
			{
				logger.info("In method initStatusAccount().");
				
				try 
				{
					//	Get account Id form session.
					HierarchyTransferBean formBean =(HierarchyTransferBean)form ;
					String levelId = request.getParameter("parentId");
					int circleId = Integer.parseInt(request.getParameter("circleId"));
					
					logger.info("Fetch account name of role :" + levelId);
					int roleId = Integer.parseInt(levelId);
					StockSummaryReportService revLogReportService = new StockSummaryReportServiceImpl();
					
					List accountList = revLogReportService.getRevLogFromDistAccountDetails(roleId , circleId);
					formBean.setFromDistList(accountList);
					logger.info("Account list size : " + accountList.size());
					
								
					// Ajax start
					Document document = DocumentHelper.createDocument();
					Element root = document.addElement("options");
					Element optionElement;
					
					Iterator iter = accountList.iterator();
					while (iter.hasNext()) 
					{
						StockSummaryReportBean statusReportBean = (StockSummaryReportBean) iter.next();
						optionElement = root.addElement("option");
						optionElement.addAttribute("text", statusReportBean.getDistAccName());
						optionElement.addAttribute("value", statusReportBean.getDistAccId());
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
					ex.printStackTrace();
				}
				
				return null;
			}
	
	public ActionForward getToDistList(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws IOException, ServletException 
			{
				logger.info("In method initStatusAccount().");
				
				try 
				{
					//	Get account Id form session.
					HierarchyTransferBean formBean =(HierarchyTransferBean)form ;
					String levelId = request.getParameter("parentId");
					int circleId = Integer.parseInt(request.getParameter("circleId"));
					
					logger.info("Fetch account name of role :" + levelId);
					int roleId = Integer.parseInt(levelId);
					StockSummaryReportService revLogReportService = new StockSummaryReportServiceImpl();
					
					List accountList = revLogReportService.getRevLogDistAccountDetails(roleId , circleId);
					formBean.setFromDistList(accountList);
					logger.info("Account list size : " + accountList.size());
					
								
					// Ajax start
					Document document = DocumentHelper.createDocument();
					Element root = document.addElement("options");
					Element optionElement;
					
					Iterator iter = accountList.iterator();
					while (iter.hasNext()) 
					{
						StockSummaryReportBean statusReportBean = (StockSummaryReportBean) iter.next();
						optionElement = root.addElement("option");
						optionElement.addAttribute("text", statusReportBean.getDistAccName());
						optionElement.addAttribute("value", statusReportBean.getDistAccId());
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
					ex.printStackTrace();
				}
				
				return null;
			}
		
}
