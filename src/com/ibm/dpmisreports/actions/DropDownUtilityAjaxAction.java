package com.ibm.dpmisreports.actions;

import java.io.PrintWriter;
import java.sql.Connection;
import java.util.Iterator;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.actions.DispatchAction;
import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.dom4j.io.XMLWriter;

import com.ibm.dp.common.Constants;
import com.ibm.dpmisreports.common.SelectionCollection;
import com.ibm.dpmisreports.service.DropDownUtilityAjaxService;
import com.ibm.dpmisreports.service.impl.DropDownUtilityAjaxServiceImpl;
import com.ibm.virtualization.recharge.dto.UserSessionContext;

public class DropDownUtilityAjaxAction extends DispatchAction 
{
	/* Logger for this class. */
	private static Logger logger = Logger.getLogger(DropDownUtilityAjaxAction.class.getName());
	
	/**
	 * This method can be used to fetch all the Active Circles created in the Application
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return ActionForward
	 * @throws Exception
	 */
	public ActionForward getAllCircles(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)throws Exception 
	{
		try
		{
			DropDownUtilityAjaxService utilityAjaxService = new DropDownUtilityAjaxServiceImpl();
			
			List<SelectionCollection> listAllCircles = utilityAjaxService.getAllCircles();
			
			setDropDownResponse(response, listAllCircles);
		}
		catch (Exception e) 
		{
			e.printStackTrace();
			logger.error("Exception occured while getting All Circles List  ::  "+e.getMessage());
		}
		return null;
	}

	/**
	 * This method can be used to fetch all the Active Accounts under a single account
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return ActionForward
	 * @throws Exception
	 */
	public ActionForward getAllAccountsUnderSingleAccount(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)throws Exception 
	{
		try
		{
			String strAccountID = request.getParameter("strAccountID");
			
			DropDownUtilityAjaxService utilityAjaxService = new DropDownUtilityAjaxServiceImpl();
			
			List<SelectionCollection> listAccount = utilityAjaxService.getAllAccountsUnderSingleAccount(strAccountID);
			
			logger.info("No of Accounts found  ::  "+listAccount.size());
			
			setDropDownResponse(response, listAccount);
		}
		catch (Exception e) 
		{
			e.printStackTrace();
			logger.error("Exception occured while getting All Accounts Under Single Account List  ::  "+e.getMessage());
		}
		return null;
	}
	
	/**
	 * This method can be used to fetch all the Active Products based on business category in a circle
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return ActionForward
	 * @throws Exception
	 */
	public ActionForward getAllProductsSingleCircle(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)throws Exception 
	{
		try
		{
			String strBusinessCat = request.getParameter("strBusinessCat");
			
			String strCircleID = request.getParameter("strCircleID");
			
			DropDownUtilityAjaxService utilityAjaxService = new DropDownUtilityAjaxServiceImpl();
			
			List<SelectionCollection> listAccount = utilityAjaxService.getAllProductsSingleCircle(strBusinessCat, strCircleID);
			
			logger.info("No of Products found  ::  "+listAccount.size());
			
			setDropDownResponse(response, listAccount);
		}
		catch (Exception e) 
		{
			e.printStackTrace();
			logger.error("Exception occured while getting All Products in a Single Circle List  ::  "+e.getMessage());
		}
		return null;
	}
	
	/**
	 * This method can be used to fetch all the Active Accounts of a level
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return ActionForward
	 * @throws Exception
	 */
	public ActionForward getAllAccounts(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)throws Exception 
	{
		try
		{
			String strAccountLevel = request.getParameter("strAccountLevel");
			
			DropDownUtilityAjaxService utilityAjaxService = new DropDownUtilityAjaxServiceImpl();
			
			List<SelectionCollection> listAccount = utilityAjaxService.getAllAccounts(strAccountLevel);
			
			logger.info("No of Accounts found  ::  "+listAccount.size());
			
			setDropDownResponse(response, listAccount);
		}
		catch (Exception e) 
		{
			e.printStackTrace();
			logger.error("Exception occured while getting All getAllAccounts for a account level  ::  "+e.getMessage());
		}
		return null;
	}
	
	
	/**
	 * This method can be used to fetch all the Active Accounts in a circle
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return ActionForward
	 * @throws Exception
	 */
	public ActionForward getAllAccountsSingleCircle(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)throws Exception 
	{
		try
		{
			String strAccountLevel = request.getParameter("strAccountLevel");
			
			String strCircleID = request.getParameter("strCircleID");
			
			DropDownUtilityAjaxService utilityAjaxService = new DropDownUtilityAjaxServiceImpl();
			
			List<SelectionCollection> listAccount = utilityAjaxService.getAllProductsSingleCircle(strAccountLevel, strCircleID);
			
			logger.info("No of Account found  ::  "+listAccount.size());
			
			setDropDownResponse(response, listAccount);
		}
		catch (Exception e) 
		{
			e.printStackTrace();
			logger.error("Exception occured while getting All Accounts in a Single Circle  ::  "+e.getMessage());
		}
		return null;
	}
	
	/**
	 * This method can be used to fetch all the Active Accounts in multiple circle
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return ActionForward
	 * @throws Exception
	 */
	public ActionForward getAllAccountsMultipleCircles(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)throws Exception 
	{
		try
		{
			String strAccountLevel = request.getParameter("strAccountLevel");
			
			logger.info("strAccountLevel  ::  "+strAccountLevel);
			
			String strCircleID = request.getParameter("strCircleID");
			
			logger.info("strCircleID  ::  "+strCircleID);
			
			DropDownUtilityAjaxService utilityAjaxService = new DropDownUtilityAjaxServiceImpl();
			
			List<SelectionCollection> listAccount = utilityAjaxService.getAllAccountsMultipleCircles(strAccountLevel, strCircleID);
			
			logger.info("No of Account found  ::  "+listAccount.size());
			
			setDropDownResponse(response, listAccount);
		}
		catch (Exception e) 
		{
			e.printStackTrace();
			logger.error("Exception occured while getting All Accounts in Multiple Circle  ::  "+e.getMessage());
		}
		return null;
	}
	
	
	
	/**
	 * This method can be used to fetch all the Active Accounts under a multiple account
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return ActionForward
	 * @throws Exception
	 */
	public ActionForward getAllAccountsUnderMultipleAccounts(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)throws Exception 
	{
		try
		{
			String strAccountID = request.getParameter("strAccountID");
			
			logger.info("strAccountID  ::  "+strAccountID);
			
			DropDownUtilityAjaxService utilityAjaxService = new DropDownUtilityAjaxServiceImpl();
			
			List<SelectionCollection> listAccount = utilityAjaxService.getAllAccountsUnderMultipleAccounts(strAccountID);
			
			logger.info("No of Accounts found  ::  "+listAccount.size());
			
			setDropDownResponse(response, listAccount);
		}
		catch (Exception e) 
		{
			e.printStackTrace();
			logger.error("Exception occured while getting All Accounts Under Multiple Account List  ::  "+e.getMessage());
		}
		return null;
	}
	
	/**
	 * This method can be used to fetch all the Collection type list
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return ActionForward
	 * @throws Exception
	 */
	public ActionForward getAllCollectionTypes(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)throws Exception 
	{
		try
		{
			DropDownUtilityAjaxService utilityAjaxService = new DropDownUtilityAjaxServiceImpl();
			
			List<SelectionCollection> listAccount = utilityAjaxService.getAllCollectionTypes();
			
			logger.info("No of Collection Type found  ::  "+listAccount.size());
			
			setDropDownResponse(response, listAccount);
		}
		catch (Exception e) 
		{
			e.printStackTrace();
			logger.error("Exception occured while getting All Collection Types List  ::  "+e.getMessage());
		}
		return null;
	}
	
	private void setDropDownResponse(HttpServletResponse response, List<SelectionCollection> listDBValues) throws Exception
	{
		Document document = DocumentHelper.createDocument();
		Element root = document.addElement("options");
		Element optionElement;
		
		Iterator<SelectionCollection> iter = listDBValues.iterator();
		while (iter.hasNext()) 
		{
			SelectionCollection selCol = iter.next();
			
			optionElement = root.addElement("option");
			optionElement.addAttribute("text", selCol.getStrText());
			optionElement.addAttribute("value", selCol.getStrValue());
		}
	
		// For ajax
		response.setHeader("Cache-Control", "No-Cache");
		response.setContentType("text/xml");
		PrintWriter out = response.getWriter();
		XMLWriter writer = new XMLWriter(out);
		writer.write(document);
		writer.flush();
		out.flush();
	}
	
	public ActionForward getProductList(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)throws Exception 
	{
		try
		{
			DropDownUtilityAjaxService utilityAjaxService = new DropDownUtilityAjaxServiceImpl();
			String businessCat = request.getParameter("businessCat");
			String reportId = request.getParameter("reportId");
			List<SelectionCollection> listAllProduct = utilityAjaxService.getProductCategoryLstAjax(businessCat,reportId);
			
			setDropDownResponse(response, listAllProduct);
		}
		catch (Exception e) 
		{
			e.printStackTrace();
			logger.error("Exception occured while getting All Circles List  ::  "+e.getMessage());
		}
		return null;
	}
	
	public ActionForward getAllTSM(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)throws Exception 
	{
		try
		{
			HttpSession session = request.getSession();
			UserSessionContext sessionContext = (UserSessionContext) session
					.getAttribute(Constants.AUTHENTICATED_USER);
			String accLevel=sessionContext.getAccountLevel();
			String loginId=String.valueOf(sessionContext.getId());
			DropDownUtilityAjaxService utilityAjaxService = new DropDownUtilityAjaxServiceImpl();
			String selectedCircle = request.getParameter("cval");
			String businessCat = request.getParameter("businessCat");
			if(businessCat==null || (businessCat!=null && businessCat.equals("")) ) //only in case of eligibility report
			{
				businessCat="1";
			}
			System.out.println("businessCat:::::::"+businessCat);
			List<SelectionCollection> listAllAccounts = utilityAjaxService.getAllTSM(selectedCircle,businessCat,accLevel,loginId);
			
			setDropDownResponse(response, listAllAccounts);
		}
		catch (Exception e) 
		{
			e.printStackTrace();
			logger.error("Exception occured while getting All Circles List  ::  "+e.getMessage());
		}
		return null;
	}
	
	public ActionForward getAllDist(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)throws Exception 
	{
		try
		{
			HttpSession session = request.getSession();
			UserSessionContext sessionContext = (UserSessionContext) session
					.getAttribute(Constants.AUTHENTICATED_USER);
			String accLevel=sessionContext.getAccountLevel();
			String loginId=sessionContext.getId()+"";
			DropDownUtilityAjaxService utilityAjaxService = new DropDownUtilityAjaxServiceImpl();
			String selectedCircle = request.getParameter("cval");
			String selectedTSM = request.getParameter("tval");
			String businessCat = request.getParameter("transType");
			
			
			List<SelectionCollection> listAllAccounts = utilityAjaxService.getAllDist(selectedCircle,selectedTSM,accLevel,loginId,businessCat);
			
			setDropDownResponse(response, listAllAccounts);
		}
		catch (Exception e) 
		{
			e.printStackTrace();
			logger.error("Exception occured while getting All Circles List  ::  "+e.getMessage());
		}
		return null;
	}
	public ActionForward getAllRet(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)throws Exception 
	{
		try
		{
			DropDownUtilityAjaxService utilityAjaxService = new DropDownUtilityAjaxServiceImpl();
			String selectedFse = request.getParameter("fval");
			String distID = request.getParameter("distId");
			
			
			List<SelectionCollection> listAllAccounts = utilityAjaxService.getAllRet(selectedFse,distID);
			
			setDropDownResponse(response, listAllAccounts);
		}
		catch (Exception e) 
		{
			e.printStackTrace();
			logger.error("Exception occured while getting All Circles List  ::  "+e.getMessage());
		}
		return null;
	}

	public ActionForward getAccountNames(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)throws Exception 
	{
		try
		{
			DropDownUtilityAjaxService utilityAjaxService = new DropDownUtilityAjaxServiceImpl();
			String type = request.getParameter("type");
			String distID = request.getParameter("distId");
			
			
			List<SelectionCollection> listAllAccounts = utilityAjaxService.getAccountNames(distID, type);
			
			setDropDownResponse(response, listAllAccounts);
		}
		catch (Exception e) 
		{
			e.printStackTrace();
			logger.error("Exception occured while getting All Circles List  ::  "+e.getMessage());
		}
		return null;
	}
	
	//Added by Neetika
	public ActionForward getAllCirclesAsperZone(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)throws Exception 
	{
		try
		{
			String cval = request.getParameter("cval");
			
			DropDownUtilityAjaxService utilityAjaxService = new DropDownUtilityAjaxServiceImpl();
			
			List<SelectionCollection> listCircles = utilityAjaxService.getAllCirclesAsperZone(cval);
			
			//logger.info("No of circles found  ::  "+listCircles.size());
			
			setDropDownResponse(response, listCircles);
		}
		catch (Exception e) 
		{
			e.printStackTrace();
			logger.error("Exception occured while getting All Accounts Under Single Account List  ::  "+e.getMessage());
		}
		return null;
	}

}
