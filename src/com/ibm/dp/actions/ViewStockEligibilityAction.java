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

import com.ibm.dp.beans.ViewStockEligibilityBean;
import com.ibm.dp.dto.UploadedStockEligibilityDto;
import com.ibm.dp.dto.ViewStockEligibilityDTO;
import com.ibm.dp.service.ViewStockEligibilityService;
import com.ibm.dp.service.impl.ViewStockEligibilityServiceImpl;
import com.ibm.virtualization.framework.bean.ChannelType;
import com.ibm.virtualization.recharge.authorization.AuthorizationConstants;
import com.ibm.virtualization.recharge.authorization.AuthorizationServiceImpl;
import com.ibm.virtualization.recharge.common.Constants;
import com.ibm.virtualization.recharge.dto.UserSessionContext;
import com.ibm.virtualization.recharge.service.AuthorizationInterface;

public class ViewStockEligibilityAction extends DispatchAction
{

	private static Logger logger = Logger.getLogger(ViewStockEligibilityAction.class.getName());

	public ActionForward init(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception
	{
		logger.info("init method. of ViewStockEligibility Called ");
		ActionErrors errors = new ActionErrors();
		ViewStockEligibilityBean formBean = (ViewStockEligibilityBean) form;
		try
		{
			HttpSession session = request.getSession();
			UserSessionContext sessionContext = (UserSessionContext) session
					.getAttribute(Constants.AUTHENTICATED_USER);

			AuthorizationInterface authorizationService = new AuthorizationServiceImpl();
			if (!(authorizationService.isUserAuthorized(sessionContext.getGroupId(),
					ChannelType.WEB, AuthorizationConstants.ROLE_VIEW_STOCK_ELIGIBILITY) || (authorizationService.isUserAuthorized(sessionContext.getGroupId(),
							ChannelType.WEB, AuthorizationConstants.ROLE_VIEW_DIST_ELIGIBILITY)))) //Neetika 
			{
				logger.info(" user not auth to perform VIEW STOCK ELIGIBILITY activity  ");
				errors.add("errors", new ActionError("errors.usernotautherized"));
				saveErrors(request, errors);
				return mapping.findForward(Constants.USER_NOT_AUTHERIZED);
			}

			if (!sessionContext.getAccountLevel().equals("6"))
			{
				formBean.setIntUserAcctLevel(sessionContext.getAccountLevel() + "");
				return initAdmin(mapping, form, request, response);
			}

			long loginUserId = sessionContext.getId();

			int distID = (int) loginUserId;

			ViewStockEligibilityService vseService = new ViewStockEligibilityServiceImpl();

			List<ViewStockEligibilityDTO> viewStockList = new ArrayList<ViewStockEligibilityDTO>();
			ViewStockEligibilityDTO viewStockMainDtl = vseService
					.getStockEligibilityMainDtl(distID);
			formBean.setBalance(viewStockMainDtl.getBalance());
			formBean.setSecurity(viewStockMainDtl.getSecurity());
			formBean.setLoan(viewStockMainDtl.getLoan());

			viewStockList = vseService.getStockEligibility(distID);

			formBean.setViewStockEligibilityList(viewStockList);

			formBean.setIntUserAcctLevel(sessionContext.getAccountLevel() + "");

			return mapping.findForward("success");

		}

		catch (Exception e)
		{
			formBean.setStrSuccessMsg("Internal error occured");
			e.printStackTrace();
			logger.info("EXCEPTION OCCURED ::  " + e.getMessage());
			errors.add("errors", new ActionError(e.getMessage()));
			saveErrors(request, errors);
			return mapping.findForward("error");
		}

	}// init

	/**
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 */
//Added by Nehil
	public ActionForward getUploadedStockEligibility(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
	{
		logger.info("getUploadedStockEligibility method of ViewStockEligibility Called ");
		ActionErrors errors = new ActionErrors();
		ViewStockEligibilityBean formBean = (ViewStockEligibilityBean) form;

		try
		{
			HttpSession session = request.getSession();
			UserSessionContext sessionContext = (UserSessionContext) session
					.getAttribute(Constants.AUTHENTICATED_USER);
			String strOLMId = sessionContext.getLoginName();

			AuthorizationInterface authorizationService = new AuthorizationServiceImpl();
			if (!authorizationService.isUserAuthorized(sessionContext.getGroupId(),
					ChannelType.WEB, AuthorizationConstants.ROLE_VIEW_STOCK_ELIGIBILITY))
			{
				logger.info(" user not auth to perform VIEW STOCK ELIGIBILITY activity  ");
				errors.add("errors", new ActionError("errors.usernotautherized"));
				saveErrors(request, errors);
				return mapping.findForward(Constants.USER_NOT_AUTHERIZED);
			}

			ViewStockEligibilityService vseService = new ViewStockEligibilityServiceImpl();
			UploadedStockEligibilityDto useDto = vseService.getUploadedStockEligibility(strOLMId);

			formBean.setCamEligibilityCommerical(useDto.getCamEligibilityCommerical());
			formBean.setCamEligibilitySwap(useDto.getCamEligibilitySwap());
			formBean.setHdBoxEligibility(useDto.getHdBoxEligibility());
			formBean.setHdDvrBoxEligibility(useDto.getHdDvrBoxEligibility());
			formBean.setSdBoxSdPlusEligibility(useDto.getSdBoxSdPlusEligibility());
			formBean.setSecurityDepositAvailableQty(useDto.getSecurityDepositAvailableQty());
			formBean.setSecurityEligibilityBalanceCommercial(useDto
					.getSecurityEligibilityBalanceCommercial());
			formBean.setSecurityEligibilityBalanceSwap(useDto.getSecurityEligibilityBalanceSwap());
			formBean.setSecurityDepositEligibility(useDto.getSecurityDepositEligibility());
			formBean.setSecurityEligibilityBalanceSwap(useDto.getSecurityEligibilityBalanceSwap());
			formBean.setSwapHdDvrEligibility(useDto.getSwapHdDvrEligibility());
			formBean.setSwapHdEligibility(useDto.getSwapHdEligibility());
			formBean.setSwapHdPlusEligibility(useDto.getSwapHdPlusEligibility());
			formBean.setSwapPVREligibilityProductQty(useDto.getSwapPVREligibilityProductQty());
			formBean.setSwapSdEligibility(useDto.getSwapSdEligibility());
		}
		catch (Exception e)
		{
			e.printStackTrace();
			formBean.setStrSuccessMsg("Internal error occured");
			logger.info("EXCEPTION OCCURED ::  " + e);
			logger.info("EXCEPTION OCCURED ::  " + e.getMessage());
			errors.add("errors", new ActionError(e.getMessage()));
			saveErrors(request, errors);
			return mapping.findForward("error");
		}
		return mapping.findForward("uploadSuccess");
	}

	public ActionForward getStockEligibility(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		logger.info("initAdmin method. of ViewStockEligibility Called ");
		ActionErrors errors = new ActionErrors();
		ViewStockEligibilityBean formBean = (ViewStockEligibilityBean) form;
		try
		{
			HttpSession session = request.getSession();
			UserSessionContext sessionContext = (UserSessionContext) session
					.getAttribute(Constants.AUTHENTICATED_USER);
			AuthorizationInterface authorizationService = new AuthorizationServiceImpl();
			if (!authorizationService.isUserAuthorized(sessionContext.getGroupId(),
					ChannelType.WEB, AuthorizationConstants.ROLE_VIEW_STOCK_ELIGIBILITY))
			{
				logger.info(" user not auth to perform VIEW STOCK ELIGIBILITY activity  ");
				errors.add("errors", new ActionError("errors.usernotautherized"));
				saveErrors(request, errors);
				return mapping.findForward(Constants.USER_NOT_AUTHERIZED);
			}

			String strOLMId = formBean.getOlmId();

			ViewStockEligibilityService vseService = new ViewStockEligibilityServiceImpl();
			List<ViewStockEligibilityDTO> viewStockList = new ArrayList<ViewStockEligibilityDTO>();

			viewStockList = vseService.getStockEligibilityAdmin(strOLMId);
			ViewStockEligibilityDTO msgDTO = viewStockList.get(0);
			formBean.setIntUserAcctLevel(sessionContext.getAccountLevel() + "");
			if (msgDTO.getStrStatus().equals("FAIL"))
			{
				formBean.setBalance("");
				formBean.setSecurity("");
				formBean.setLoan("");
				viewStockList = new ArrayList<ViewStockEligibilityDTO>();
				formBean.setViewStockEligibilityList(viewStockList);
				formBean.setStrSuccessMsg(msgDTO.getStrMessage());
				return mapping.findForward("success");
			}
			else
			{
				ViewStockEligibilityDTO viewStockMainDtl = viewStockList.get(1);

				formBean.setBalance(viewStockMainDtl.getBalance());
				formBean.setSecurity(viewStockMainDtl.getSecurity());
				formBean.setLoan(viewStockMainDtl.getLoan());
				formBean.setStrDistName(viewStockMainDtl.getStrDistName());
				formBean.setStrOLMID(strOLMId);

				List<ViewStockEligibilityDTO> gridStockList = viewStockList.subList(2,
						viewStockList.size());

				formBean.setStkEligExcelDetailList(gridStockList);

				formBean.setViewStockEligibilityList(gridStockList);

				formBean.setIntUserAcctLevel(sessionContext.getAccountLevel() + "");

				if (session.getAttribute("eligibForm") == null)
					session.setAttribute("eligibForm", formBean);
				else
				{
					session.removeAttribute("eligibForm");
					session.setAttribute("eligibForm", formBean);
				}

				return mapping.findForward("success");
			}
		}
		catch (Exception e)
		{
			e.printStackTrace();
			formBean.setStrSuccessMsg("Internal error occured");
			logger.info("EXCEPTION OCCURED ::  " + e);
			logger.info("EXCEPTION OCCURED ::  " + e.getMessage());
			errors.add("errors", new ActionError(e.getMessage()));
			saveErrors(request, errors);
			return mapping.findForward("error");
		}

	}// initAdmin

	public ActionForward initAdmin(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		logger.info("initAdmin method. of ViewStockEligibility Called ");
		ActionErrors errors = new ActionErrors();
		ViewStockEligibilityBean formBean = (ViewStockEligibilityBean) form;
		try
		{
			formBean.setBalance("");
			formBean.setSecurity("");
			formBean.setLoan("");
			List<ViewStockEligibilityDTO> viewStockList = new ArrayList<ViewStockEligibilityDTO>();
			formBean.setViewStockEligibilityList(viewStockList);

			return mapping.findForward("success");
		}
		catch (Exception e)
		{
			formBean.setStrSuccessMsg("Internal error occured");
			e.printStackTrace();
			logger.info("EXCEPTION OCCURED ::  " + e.getMessage());
			errors.add("errors", new ActionError(e.getMessage()));
			saveErrors(request, errors);
			return mapping.findForward("error");
		}

	}// initAdmin

	// public ActionForward exportToExcel(ActionMapping mapping, ActionForm
	// form,
	// HttpServletRequest request, HttpServletResponse response) throws
	// Exception
	// {
	// ActionErrors errors = new ActionErrors();
	// ViewStockEligibilityBean formBean = (ViewStockEligibilityBean)form ;
	//		
	// HttpSession session = request.getSession();
	// UserSessionContext sessionContext = (UserSessionContext)
	// session.getAttribute(Constants.AUTHENTICATED_USER);
	// AuthorizationInterface authorizationService = new
	// AuthorizationServiceImpl();
	// if (!authorizationService.isUserAuthorized(sessionContext.getGroupId(),
	// ChannelType.WEB, AuthorizationConstants.ROLE_VIEW_STOCK_ELIGIBILITY))
	// {
	//logger.info(" user not auth to perform VIEW STOCK ELIGIBILITY activity  ")
	// ;
	// errors.add("errors",new ActionError("errors.usernotautherized"));
	// saveErrors(request, errors);
	// return mapping.findForward(Constants.USER_NOT_AUTHERIZED);
	// }
	//		
	// ViewStockEligibilityBean sessionBean = null;
	//		
	// if(session.getAttribute("eligibForm")!=null)
	// sessionBean =
	// (ViewStockEligibilityBean)session.getAttribute("eligibForm");
	// else
	// {
	// formBean.setStrSuccessMsg("Internal error occured");
	// logger.info("No Stock Eligibility data found in session to display");
	// errors.add("errors",new
	// ActionError("No Stock Eligibility data found to display"));
	// saveErrors(request, errors);
	// return mapping.findForward("error");
	// }
	//		
	// BeanUtils.copyProperties(formBean, sessionBean);
	//		
	//		
	// return mapping.findForward("uploadExcel");
	// }
	// Added by sugandha on 20th-August to reslove bug for BFR-39 DP-Phase-3.

	public ActionForward exportToExcel(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) throws Exception

	{
		ActionErrors errors = new ActionErrors();
		ViewStockEligibilityBean formBean = (ViewStockEligibilityBean) form;
		ViewStockEligibilityService vseService = new ViewStockEligibilityServiceImpl();
		HttpSession session = request.getSession();
		UserSessionContext sessionContext = (UserSessionContext) session
				.getAttribute(Constants.AUTHENTICATED_USER);
		AuthorizationInterface authorizationService = new AuthorizationServiceImpl();
		if (!authorizationService.isUserAuthorized(sessionContext.getGroupId(), ChannelType.WEB,
				AuthorizationConstants.ROLE_VIEW_STOCK_ELIGIBILITY))
		{
			logger.info(" user not auth to perform VIEW STOCK ELIGIBILITY activity  ");
			errors.add("errors", new ActionError("errors.usernotautherized"));
			saveErrors(request, errors);
			return mapping.findForward(Constants.USER_NOT_AUTHERIZED);
		}
		List<ViewStockEligibilityDTO> viewStockList = new ArrayList<ViewStockEligibilityDTO>();
		String accountLevel = sessionContext.getAccountLevel();

		// logger.info("accountlevel::::"+accountLevel);
		if (sessionContext.getAccountLevel().equals("6"))
		{
			logger.info("======for distributer ==");
			// String strOLMId = formBean.getOlmId();
			String strOLMId = formBean.getDistOlmId();
			long loginUserId = sessionContext.getId();
			// logger.info("loginUserId:::"+loginUserId);
			int distID = (int) loginUserId;
			// logger.info("distID:::"+distID);
			viewStockList = vseService.getStockEligibility(distID);
			// viewStockList = vseService.getStockEligibilityAdmin(strOLMId);
			ViewStockEligibilityDTO viewStockMainDtl = vseService
					.getStockEligibilityMainDtl(distID);
			formBean.setBalance(viewStockMainDtl.getBalance());
			formBean.setSecurity(viewStockMainDtl.getSecurity());
			formBean.setLoan(viewStockMainDtl.getLoan());
			formBean.setStrDistName(viewStockMainDtl.getStrDistName());
			formBean.setStrOLMID(strOLMId);
			// viewStockList= vseService.getStockEligibility(distID);
			formBean.setViewStockEligibilityList(viewStockList);
			// logger.info("formBean.getBalance:::::"+formBean.getBalance());
			// logger.info("formBean.setSecurity:::::"+formBean.getSecurity());
			// logger.info("formBean.setLoan:::::"+formBean.getLoan());
			//logger.info("formBean.setStrDistName:::::"+formBean.getStrDistName
			// ());
			//logger.info("formBean.setStrDistName:::::"+formBean.getStrOLMID())
			// ;

		}
		else
		{
			viewStockList = new ArrayList<ViewStockEligibilityDTO>();
			logger.info("========for others users ======");
			String strOLMId = formBean.getOlmId();
			// logger.info("strOLMId:::"+strOLMId);
			viewStockList.clear();
			viewStockList = vseService.getStockEligibilityAdmin(strOLMId);
			// logger.info("viewStockList:::"+viewStockList.size());
			ViewStockEligibilityDTO viewStockMainDtl = viewStockList.get(1);
			formBean.setBalance(viewStockMainDtl.getBalance());
			formBean.setSecurity(viewStockMainDtl.getSecurity());
			formBean.setLoan(viewStockMainDtl.getLoan());
			formBean.setStrDistName(viewStockMainDtl.getStrDistName());
			formBean.setStrOLMID(strOLMId);
			formBean.setViewStockEligibilityList(viewStockList);
			// logger.info("viewStockList!###$$$$$:::"+viewStockList.size());
			// logger.info("formBean.getBalance:::::"+formBean.getBalance());
			// logger.info("formBean.setSecurity:::::"+formBean.getSecurity());
			// logger.info("formBean.setLoan:::::"+formBean.getLoan());
			//logger.info("formBean.setStrDistName:::::"+formBean.getStrDistName
			// ());
			//logger.info("formBean.setStrDistName:::::"+formBean.getStrOLMID())
			// ;
		}

		return mapping.findForward("downloadExcel");

	}
	// Added by sugandha ends here on 20th-August to reslove bug for BFR-39
	// DP-Phase-3.
}