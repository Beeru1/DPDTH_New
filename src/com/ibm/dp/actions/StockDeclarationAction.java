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

import com.ibm.dp.beans.StockDeclarationFormBean;
import com.ibm.dp.common.Constants;
import com.ibm.dp.dto.DistributorDTO;
import com.ibm.dp.dto.DistributorDetailsDTO;
import com.ibm.dp.service.StockDeclarationService;
import com.ibm.dp.service.impl.StockDeclarationServiceImpl;
import com.ibm.virtualization.recharge.dto.UserSessionContext;
/**
 * @author Mohammad Aslam
 */
public class StockDeclarationAction extends DispatchAction {
	private Logger logger = Logger.getLogger(DPProductStatusReportAction.class.getName());
	private static final String INIT_SUCCESS = "initSuccess";
	List<DistributorDetailsDTO> distributorDetailsList = null;

	public ActionForward initStockDec(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		ActionErrors errors = new ActionErrors();
		HttpSession session = request.getSession();
		try {
			UserSessionContext userSessionContext = (UserSessionContext) session.getAttribute(Constants.AUTHENTICATED_USER);
			long accountId = userSessionContext.getId(); // Login_Id,Account_id,ZBM_ID,ZSM_ID,TSM_Account_id
			long circleId = userSessionContext.getCircleId();
			String accountLavel = userSessionContext.getAccountLevel();
			StockDeclarationService sds = new StockDeclarationServiceImpl();
			List<DistributorDTO> distributorList = sds.getDistributors(accountLavel, accountId, circleId);
			request.setAttribute("distributorList", distributorList);

			if (request.getAttribute("isNew") == null) {
				distributorDetailsList = new ArrayList<DistributorDetailsDTO>();
				distributorDetailsList.add(new DistributorDetailsDTO());
				request.setAttribute("distributorDetailsList", distributorDetailsList);
			} else {
				request.setAttribute("distributorDetailsList", distributorDetailsList);
			}
		} catch (RuntimeException e) {
			e.printStackTrace();
			logger.info("EXCEPTION OCCURED ::  "+e.getMessage());
			errors.add("errors",new ActionError(e.getMessage()));
			saveErrors(request, errors);
			return mapping.findForward(INIT_SUCCESS);
		}

		return mapping.findForward(INIT_SUCCESS);
	}

	public ActionForward getDistributorDetails(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
			throws Exception {

		HttpSession session = request.getSession();
		ActionErrors errors = new ActionErrors();
		try {
			UserSessionContext userSessionContext = (UserSessionContext) session.getAttribute(Constants.AUTHENTICATED_USER);
			long circleId = userSessionContext.getCircleId();
			String accountLavel = userSessionContext.getAccountLevel();
			String distributorId = ((StockDeclarationFormBean) form).getDistributor();
			long loginUserID = userSessionContext.getId();
			
			StockDeclarationService sds = new StockDeclarationServiceImpl();
			distributorDetailsList = sds.getDistributorDetails(accountLavel, distributorId, circleId, loginUserID);
			if("exportToExcel".equalsIgnoreCase(((StockDeclarationFormBean) form).getExportToExcel())){
				request.setAttribute("distributorDetailsList", distributorDetailsList);
				return mapping.findForward("successExcel");
			}else{
				request.setAttribute("isNew", "False");
				return mapping.findForward(Constants.SUCCESS_MESSAGE);
			}
		} catch (RuntimeException e) {
			e.printStackTrace();
			logger.info("EXCEPTION OCCURED ::  "+e.getMessage());
			errors.add("errors",new ActionError(e.getMessage()));
			saveErrors(request, errors);
			return mapping.findForward(INIT_SUCCESS);
		}	
		
	}

}