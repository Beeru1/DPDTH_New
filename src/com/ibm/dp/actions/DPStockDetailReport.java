package com.ibm.dp.actions;


import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.actions.DispatchAction;

import com.ibm.dp.beans.DPStockDetailsReportBean;
import com.ibm.dp.common.Constants;
import com.ibm.dp.dto.StockDetailReport;
import com.ibm.dp.service.DPReportService;
import com.ibm.dp.service.impl.DPReportServiceImpl;
import com.ibm.virtualization.recharge.dto.UserSessionContext;

/**
 * 
 *	Action class for Reports with External System.
 *
 */
public class DPStockDetailReport extends DispatchAction 
{
private static final String INIT_SUCCESS = "initsuccess";
private static final String SUCCESS = "success";
private static final String SUCCESS_EXCEL = "successExcel";

/*
 * Method for getting stored password for logged-in user group
 */

private Logger logger = Logger.getLogger(DPProductStatusReportAction.class.getName());


//Changed on fixing CQ defect ID :  MASDB00150574
public ActionForward initStockDetailReport(ActionMapping mapping, ActionForm form,
		HttpServletRequest request, HttpServletResponse response)	throws Exception 
{

	logger.info("***************************** initStockDetailReport() method.*****************************");
	
	// Get account Id form session.
	HttpSession session = request.getSession();
	UserSessionContext sessionContext = (UserSessionContext) session.getAttribute(Constants.AUTHENTICATED_USER);
	Long distId = sessionContext.getId();
	String distName = sessionContext.getAccountName();
	String accountLevel=sessionContext.getAccountLevel();
	Long accountID=sessionContext.getId();
	List tempDistList=new ArrayList();
	
	DPStockDetailsReportBean reportBean = (DPStockDetailsReportBean) form;
	DPReportService reportService = new DPReportServiceImpl();
	
	// For distributor
	List<DPStockDetailsReportBean> accountDetailList = new ArrayList<DPStockDetailsReportBean>();
	DPStockDetailsReportBean stockDetail = new DPStockDetailsReportBean();
	StockDetailReport dpreportdto= new StockDetailReport();
	
	if(sessionContext.getAccountLevel().equalsIgnoreCase("6"))
	{
		dpreportdto.setDistID(String.valueOf(distId));
		dpreportdto.setDistName(distName);
		tempDistList.add(dpreportdto);
		reportBean.setDistList(tempDistList);
	}
	else
	{
		// For TSM
		tempDistList = reportService.getAllDistributor(accountID,accountLevel);
		reportBean.setDistList(tempDistList);
		logger.info("********TSM tempDistList********Size :"+tempDistList.size());
		
	}
	
	request.setAttribute("distList", tempDistList);
	return mapping.findForward(INIT_SUCCESS);

}

//Changed on fixing CQ defect ID : MASDB00149148
public ActionForward getStockDetailReportDataExcel(ActionMapping mapping, ActionForm form,
		HttpServletRequest request, HttpServletResponse response)throws Exception 
{
	logger.info("In getStockDetailExcel() ");
		
	DPStockDetailsReportBean reportBean = (DPStockDetailsReportBean) form;
	int distId = Integer.parseInt(request.getParameter("distID"));
		
	DPReportService reportService = new DPReportServiceImpl();
	List reportStockDataList = reportService.getStockReportDetails(distId);
	logger.info("Stock Report Data List size : " + reportStockDataList.size());
	reportBean.setReportStockDataList(reportStockDataList);
	request.setAttribute("reportStockDataList",reportStockDataList);
		
	return mapping.findForward(SUCCESS_EXCEL);
}


/*
public ActionForward initStatusAccount(ActionMapping mapping,
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
		int distId = Integer.parseInt(String.valueOf(accId));
		
		DPCreateAccountFormBean reportBean = (DPCreateAccountFormBean) form;
		String levelId = request.getParameter("levelId");
		logger.info("Fetch account name of role :" + levelId);
		int roleId = Integer.parseInt(levelId);
		DPReportService reportService = new DPReportServiceImpl();
		
		List accountList = reportService.getAccountDetails(roleId, distId);
		reportBean.setProductStatusList(accountList);
		request.setAttribute("productStatusList",accountList);
		logger.info("Account list size : " + accountList.size());
					
		// Ajax start
		Document document = DocumentHelper.createDocument();
		Element root = document.addElement("options");
		Element optionElement;
		
		Iterator iter = accountList.iterator();
		while (iter.hasNext()) 
		{
			InventoryStatusBean statusReportBean = (InventoryStatusBean) iter.next();
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
	}

	return null;
}
*/
}//END


