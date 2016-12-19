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

import com.ibm.dp.beans.DistRecoBean;
import com.ibm.dp.beans.RecoPeriodConfFormBean;
import com.ibm.dp.beans.StockSummaryReportBean;
import com.ibm.dp.common.Constants;
import com.ibm.dp.dto.DpProductCategoryDto;
import com.ibm.dp.dto.PrintRecoDto;
import com.ibm.dp.dto.ProductMasterDto;
import com.ibm.dp.dto.RecoDetailReportDTO;
import com.ibm.dp.dto.RecoPeriodDTO;
import com.ibm.dp.service.DistRecoService;
import com.ibm.dp.service.RecoDetailReportService;
import com.ibm.dp.service.StockSummaryReportService;
import com.ibm.dp.service.impl.DistRecoServiceImpl;
import com.ibm.dp.service.impl.RecoDetailReportServiceImpl;
import com.ibm.dp.service.impl.StockSummaryReportServiceImpl;
import com.ibm.dpmisreports.common.SelectionCollection;
import com.ibm.dpmisreports.service.DropDownUtilityAjaxService;
import com.ibm.dpmisreports.service.impl.DropDownUtilityAjaxServiceImpl;
import com.ibm.virtualization.recharge.dto.UserSessionContext;

/**
 * 
 *	Action class for Reports with External System.
 *
 */
public class RecoDetailReportAction extends DispatchAction 
{

	private static final String INIT_SUCCESS = "success";
	private static final String SUCCESS_EXCEL = "successExcel";


	private Logger logger = Logger.getLogger(RecoDetailReportAction.class.getName());

	public ActionForward init(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)	throws Exception {
		
		logger.info("In init method.");

		RecoPeriodConfFormBean recoPeriodBean = (RecoPeriodConfFormBean) form;
		HttpSession session = request.getSession();
		UserSessionContext sessionContext = (UserSessionContext) session.getAttribute(Constants.AUTHENTICATED_USER);
		ActionErrors errors = new ActionErrors();
		
		try 
		{
			List<DpProductCategoryDto> productList = new ArrayList<DpProductCategoryDto>();	
			DropDownUtilityAjaxService utilityAjaxService = new DropDownUtilityAjaxServiceImpl();	
			productList = utilityAjaxService.getProductCategoryLst();		
			recoPeriodBean.setProductList(productList);
			session.setAttribute("productList", productList);
			
			List<RecoPeriodDTO> recoList = new ArrayList<RecoPeriodDTO>();	
			List recoListTotal = new ArrayList();	
			RecoDetailReportService recoDetailReportService = RecoDetailReportServiceImpl.getInstance();
			recoList = recoDetailReportService.getRecoHistory();	
			List<SelectionCollection> ciList = new ArrayList<SelectionCollection>();	
			
			for(RecoPeriodDTO recoPeriodDTO :recoList)
			{
				String todate  = recoPeriodDTO.getToDate();				
				SelectionCollection recoPeriod=new SelectionCollection();
				recoPeriod.setStrText(recoPeriodDTO.getFromDate() + " to " +recoPeriodDTO.getToDate());
				recoPeriod.setStrValue( recoPeriodDTO.getRecoPeriodId());				
				recoListTotal.add(recoPeriod);
			}
			
			recoPeriodBean.setRecoListTotal(recoListTotal);
			
			Integer groupId = sessionContext.getGroupId();
			recoPeriodBean.setGroupId(groupId.toString());			
			recoPeriodBean.setProductId("");
			
			long accountID = sessionContext.getId();	
			
			/*
			if(Integer.parseInt(circleIdSr)!=0)
			{				
				*
				 * Commented by nazim hussain to get Circle IDs based on Group ID
				 * 
				if(groupId ==7)
				{					
					SelectionCollection sc = new SelectionCollection();
					sc.setStrText(sessionContext.getCircleName());
					sc.setStrValue((Integer.toString(sessionContext.getCircleId())));
					ciList.add(sc);
					recoPeriodBean.setArrCIList(ciList);
				}
				else if(groupId ==6)
				{
					SelectionCollection sc = new SelectionCollection();
					sc.setStrText(sessionContext.getCircleName());
					sc.setStrValue((Integer.toString(sessionContext.getCircleId())));
					ciList.add(sc);
					recoPeriodBean.setArrCIList(ciList);
				}
				else if(groupId ==3 || groupId ==4 ||groupId ==5)
				{					
					SelectionCollection sc = new SelectionCollection();
					sc.setStrText(sessionContext.getCircleName());
					sc.setStrValue((Integer.toString(sessionContext.getCircleId())));
					
					ciList.add(sc);
					recoPeriodBean.setArrCIList(ciList);					
					
				}
				else if(groupId==2)
				{
					SelectionCollection sc = new SelectionCollection();
					sc.setStrText(sessionContext.getCircleName());
					sc.setStrValue((Integer.toString(sessionContext.getCircleId())));
					
					ciList.add(sc);
					recoPeriodBean.setArrCIList(ciList);
				}
			}
			else
			{				
				recoPeriodBean.setCircleid(-2);
				ciList = utilityAjaxService.getAllCircles();
				recoPeriodBean.setArrCIList(ciList);
				request.setAttribute("circleList", ciList);
			}
			*/
			

			//Added by nazim hussain to get Circle IDs based on Group ID
		
			if(groupId==7 )
			{
				recoPeriodBean.setCircleid(sessionContext.getCircleId());
				
				SelectionCollection sc = new SelectionCollection();
				sc.setStrText(sessionContext.getCircleName());
				sc.setStrValue((Integer.toString(sessionContext.getCircleId())));
				
				ciList.add(sc);
				recoPeriodBean.setArrCIList(ciList);
			}
			else if(groupId==1 || groupId==2)
			{
				recoPeriodBean.setCircleid(-2);
				ciList = utilityAjaxService.getAllCircles();
				recoPeriodBean.setArrCIList(ciList);
				request.setAttribute("circleList", ciList);
			}
			else if(groupId==3)
			{
				if((sessionContext.getCircleId())!=0)
				{
				recoPeriodBean.setCircleid(-2);
				ciList = utilityAjaxService.getTsmCircles(sessionContext.getId());
				recoPeriodBean.setArrCIList(ciList);
				request.setAttribute("circleList", ciList);
				}
				else
				{
					recoPeriodBean.setCircleid(-2);
					utilityAjaxService = new DropDownUtilityAjaxServiceImpl();	
					
					ciList = utilityAjaxService.getAllCircles();
					recoPeriodBean.setArrCIList(ciList);
					request.setAttribute("circleList", ciList);
				}
				
			}
			else
			{	
				recoPeriodBean.setCircleid(-2);
				recoDetailReportService = RecoDetailReportServiceImpl.getInstance();
				ciList = recoDetailReportService.getCircleList(accountID);
				recoPeriodBean.setArrCIList(ciList);
				request.setAttribute("circleList", ciList);
			}
		}
		catch(Exception e)
		{
			e.printStackTrace();
			logger.info("EXCEPTION OCCURED ::  "+e.getMessage());
			errors.add("errors",new ActionError(e.getMessage()));
			saveErrors(request, errors);
		}


		return mapping.findForward(INIT_SUCCESS);
			}
	public ActionForward initProducts(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)	throws Exception 
			{
		HttpSession session = request.getSession();
		RecoPeriodConfFormBean recoPeriodBean = (RecoPeriodConfFormBean) form;
		String circleId = request.getParameter("circleID");
		List<DpProductCategoryDto> productList = new ArrayList<DpProductCategoryDto>();	
		DropDownUtilityAjaxService utilityAjaxService = new DropDownUtilityAjaxServiceImpl();	
		productList = utilityAjaxService.getProductCategoryLst();		
		recoPeriodBean.setProductList(productList);
		session.setAttribute("productList", productList);
		ajaxCall(request,response,productList);
		return null;
			}
	private void ajaxCall(HttpServletRequest request, HttpServletResponse response, List productList)throws Exception{

		Document document = DocumentHelper.createDocument();
		Element root = document.addElement("options");
		Element optionElement;
		ProductMasterDto productMasterDto = null;

		for (int counter = 0; counter < productList.size(); counter++) {

			optionElement = root.addElement("option");
			productMasterDto = (ProductMasterDto)productList.get(counter);
			if (productMasterDto != null) {
				optionElement.addAttribute("value", String.valueOf(productMasterDto.getProductId()));		
				optionElement.addAttribute("text", String.valueOf(productMasterDto.getProductName()));	
			} else {
				optionElement.addAttribute("value", "None");		
				optionElement.addAttribute("text", "None");	
			}				
		}

		response.setContentType("text/xml");
		response.setHeader("Cache-Control", "No-Cache");
		PrintWriter out = response.getWriter();		
		XMLWriter writer = new XMLWriter(out);
		writer.write(document);
		writer.flush(); 
		out.flush(); 
	}

	public ActionForward getRecoDetailReportDataExcel(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)throws Exception 
	{
		logger.info("In getRecoDetailReportDataExcel() ");
		HttpSession session=request.getSession();
		//System.out.println("status value in recoDetailReport inprogress");
		session.setAttribute("recoDetailReport", "inprogress"); //Added by Neetika BFR 16 Release 3 on 16 Aug
		UserSessionContext sessionContext = (UserSessionContext) session.getAttribute(Constants.AUTHENTICATED_USER);		

		RecoPeriodConfFormBean recoPeriodConfFormBean = (RecoPeriodConfFormBean) form;
		//String circleId = request.getParameter("circleid");
		String circleId = recoPeriodConfFormBean.getHiddenCircleSelecIds();
		int recoID = Integer.parseInt(request.getParameter("recoID"));
		Integer groupId = sessionContext.getGroupId();
		long accountID = sessionContext.getId();
		
		logger.info("circleId  ::  "+circleId+"   ::  group id  ::  "+groupId+"  ::  accountID   ::  "+accountID);
		
		if(circleId.equalsIgnoreCase(""))
		{
			circleId = String.valueOf(sessionContext.getCircleId());
		}

		DropDownUtilityAjaxService utilityAjaxService = new DropDownUtilityAjaxServiceImpl();
		List<SelectionCollection> listAllCircles = new ArrayList<SelectionCollection>();
		if(circleId.equalsIgnoreCase("0"))
			
		{
			utilityAjaxService = new DropDownUtilityAjaxServiceImpl();	
			
			listAllCircles = utilityAjaxService.getAllCircles();
			circleId="";
			for(int i=0;i<listAllCircles.size();i++)
			{
				if(i!=(listAllCircles.size()-1))
				circleId=circleId+""+listAllCircles.get(i).getStrValue()+",";
				else
					circleId=circleId+""+listAllCircles.get(i).getStrValue()+"";	
			}
			
		}
		else
		{
			if(circleId.equalsIgnoreCase("")) {
			
			listAllCircles = utilityAjaxService.getTsmCircles(sessionContext.getId());
			circleId="";
			for(int i=0;i<listAllCircles.size();i++)
			{
				if(i!=(listAllCircles.size()-1))
				circleId=circleId+""+listAllCircles.get(i).getStrValue()+",";
				else
					circleId=circleId+""+listAllCircles.get(i).getStrValue()+"";	
			}
			}
			
		}
		
		logger.info("getRecoDetailReportDataExcel"+circleId);
		String[] productIdArr = recoPeriodConfFormBean.getProductIdArray();
		String productIds = "";
		for(int i=0;i<productIdArr.length;i++)
		{
			if(i==productIdArr.length-1)
			{
				productIds += productIdArr[i];
			}
			else
			{
				productIds += productIdArr[i] +",";
			}
		}
		RecoDetailReportService recoDetailReportService = RecoDetailReportServiceImpl.getInstance();
		List<RecoDetailReportDTO> reportRecoDataList = recoDetailReportService.getRecoReportDetails(recoID,circleId, productIds, groupId, accountID);	

		recoPeriodConfFormBean.setReportRecoDataList(reportRecoDataList);
		request.setAttribute("reportRecoDataList",reportRecoDataList);

		return mapping.findForward(SUCCESS_EXCEL);
	}
	
	public ActionForward printRecoDetail(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)	throws Exception 
	{		ActionErrors errors = new ActionErrors();
				
				String certifcateId = null;
				String hiddenDistId = null;
				
			try 
			{
				HttpSession session = request.getSession();
				UserSessionContext sessionContext = (UserSessionContext) session.getAttribute(Constants.AUTHENTICATED_USER);
				Long distId = sessionContext.getId();
				String distName = sessionContext.getAccountName()+"  ("+sessionContext.getLoginName()+")";
				int intSelectedTsmId=0;
				int intSelectedDistId=0;
		
				logger.info("Dist id == "+distId +" Account Name -- "+sessionContext.getAccountName() +" and  Contact Name == "+sessionContext.getContactName() );
				RecoPeriodConfFormBean formBean =(RecoPeriodConfFormBean)form ;
				
				String recoID = request.getParameter("recoID");
				String circleid=request.getParameter("circleid");
				String selectedTsmId=request.getParameter("selectedTsmId");
				
				if(selectedTsmId.equals(""))
				{
					formBean.setDistName(distName);
				}
				else
				{
					intSelectedTsmId=Integer.parseInt(selectedTsmId);
					String hiddenDistId1=request.getParameter("hiddenDistId");
					logger.info("dist length:::::::::::"+hiddenDistId1.length());
					 hiddenDistId=hiddenDistId1.substring(1);
				}	
				
				RecoDetailReportService recoDetailReportService = RecoDetailReportServiceImpl.getInstance();
				List printRecoList = recoDetailReportService.getRecoPrintList(recoID,circleid,"0", intSelectedTsmId, hiddenDistId);
				formBean.setPrintRecoList(printRecoList);
				
				logger.info("SIZE OF LIST  ::::  "+printRecoList.size());
				request.setAttribute("printRecoList", printRecoList);
				
				
			} catch (RuntimeException e) {
				e.printStackTrace();
				logger.info("EXCEPTION OCCURED ::  "+e.getMessage());
				errors.add("errors",new ActionError(e.getMessage()));
				saveErrors(request, errors);
				return mapping.findForward("initsuccess");
			}
			
			
				return mapping.findForward("initsuccess");
			
	}
	public ActionForward getReportDownloadStatus(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)throws Exception 
	{
		//System.out.println("inside getReportDownloadStatus");
		try
		{
			HttpSession session = request.getSession();
			
			String status = (String) session.getAttribute("recoDetailReport");
			//System.out.println("status value in reco details is "+status);
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
	
	//added by aman
	
	public ActionForward InitTsmList(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws IOException, ServletException 
	{
		logger.info("In method initStatusAccount().");
		
		try 
		{

			RecoPeriodConfFormBean recoPeriodBean = (RecoPeriodConfFormBean) form;
			
			String circle = request.getParameter("circleId");
			//String circle = (String) request.getAttribute("circleId");
			int circleId=Integer.parseInt(circle);

			RecoDetailReportService recoDetailReportService = RecoDetailReportServiceImpl.getInstance();

			List<RecoPeriodConfFormBean> tsmList = recoDetailReportService.getTsmList(circleId);
						
			logger.info("tsmList"+tsmList);
			// Ajax start

			Document document = DocumentHelper.createDocument();
			Element root = document.addElement("options");
			Element optionElement;
			
			Iterator iter = tsmList.iterator();
			while (iter.hasNext()) 
			{
				RecoPeriodConfFormBean recoPeriodConfFormBean = (RecoPeriodConfFormBean) iter.next();
				optionElement = root.addElement("option");
				optionElement.addAttribute("text", recoPeriodConfFormBean.getTsmName());
				optionElement.addAttribute("value", recoPeriodConfFormBean.getTsmAccountId());
			}

			// For ajax
			response.setHeader("Cache-Control", "No-Cache");
			response.setContentType("text/xml");
			PrintWriter out = response.getWriter();
			OutputFormat outputFormat = OutputFormat.createCompactFormat();
			XMLWriter writer = new XMLWriter(out);
			logger.info("document" + document);
			writer.write(document);
			writer.flush();
			out.flush();

			
		} catch (Exception ex) 
		{
			ex.printStackTrace();
		}

		return null;
	}
	
	
	public ActionForward InitDistList(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws IOException, ServletException 
	{
		logger.info("In method InitDistList().");
		
		try 
		{
			//	Get account Id form session.
			HttpSession session = request.getSession();
			UserSessionContext sessionContext = (UserSessionContext) session.getAttribute(Constants.AUTHENTICATED_USER);
			Long accId = sessionContext.getId();
			//int tsmId = Integer.parseInt(String.valueOf(accId));
			
			RecoPeriodConfFormBean recoPeriodBean = (RecoPeriodConfFormBean) form;
			
			String accountId = request.getParameter("levelId");   //tsm Id to be selected here
			int intAccountId=Integer.parseInt(accountId);
			logger.info("asa:::account id selected for user::::::asa::account id"+intAccountId);
			//logger.info("Fetch account name of role :" + levelId);
			//int roleId = Integer.parseInt(levelId);
			//StockSummaryReportService revLogReportService = new StockSummaryReportServiceImpl();
			RecoDetailReportService recoDetailReportService = RecoDetailReportServiceImpl.getInstance();
			
			List distList = recoDetailReportService.getDistList(intAccountId);
			recoPeriodBean.setDistList(distList);
			//request.setAttribute("productStatusList",accountList);
			//logger.info("Account list size : " + accountList.size());
			
						
			// Ajax start
			Document document = DocumentHelper.createDocument();
			Element root = document.addElement("options");
			Element optionElement;
			
			Iterator iter = distList.iterator();
			while (iter.hasNext()) 
			{
				RecoPeriodConfFormBean recoPeriodConfFormBean = (RecoPeriodConfFormBean) iter.next();
				optionElement = root.addElement("option");
				optionElement.addAttribute("text", recoPeriodConfFormBean.getDistName());
				optionElement.addAttribute("value", recoPeriodConfFormBean.getDistAccId());
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
	//end of changes by aman
	
	
	
}


