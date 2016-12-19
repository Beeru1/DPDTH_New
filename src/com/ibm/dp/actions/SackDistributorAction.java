package com.ibm.dp.actions;

import java.util.ArrayList;
import java.util.List;
import java.util.ListIterator;
import java.util.regex.Pattern;

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

import com.ibm.dp.beans.SackDistributorBean;
import com.ibm.dp.common.Constants;
import com.ibm.dp.dto.SackDistributorDetailDto;
import com.ibm.dp.dto.TSMDto;
import com.ibm.dp.service.SackDistributorService;
import com.ibm.dp.service.impl.SackDistributorServiceImpl;
import com.ibm.virtualization.recharge.dto.UserSessionContext;

public class SackDistributorAction extends DispatchAction{
	private static Logger logger = Logger.getLogger(SackDistributorAction.class.getName());
	
	private static final String INIT_SUCCESS = "initSuccess";
	private static final String STATUS_ERROR = "error";
	private static final String REGEX_COMMA = ",";
	/**
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return  success or failure
	 * @throws Exception
	 * @desc initial information which fetch the category from the table and display in category dropdown [infront end screen].
	 */
	public ActionForward initSackDist(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
logger.info("***************************** initSackDist() method. Called*****************************");
		
		// Get account Id form session.
		HttpSession session = request.getSession();
		SackDistributorBean reverseCollectionBean = (SackDistributorBean)form;
		ActionErrors errors = new ActionErrors();
		try
		{
			UserSessionContext sessionContext = (UserSessionContext) session.getAttribute(Constants.AUTHENTICATED_USER);
			String circleId = String.valueOf(sessionContext.getCircleId());
			setDefaultValue(reverseCollectionBean, request,circleId);
			
			//Security Observation CSRF Implementation by Karan on 20 August 2012
			saveToken(request);
			
		}
		catch(Exception e)
		{
			e.printStackTrace();
			logger.info("EXCEPTION OCCURED ::  "+e.getMessage());
			errors.add("errors",new ActionError(e.getMessage()));
			saveErrors(request, errors);
			return mapping.findForward(STATUS_ERROR);
		}
		return mapping.findForward(INIT_SUCCESS);

			
	}

	private void setDefaultValue(SackDistributorBean sackDistBean,
			HttpServletRequest request,String circleId) throws Exception {
			SackDistributorService tsmService = new SackDistributorServiceImpl();
			List<TSMDto> tsmList = tsmService.getTSMMaster(circleId);

			sackDistBean.setTsmList(tsmList);
		request.setAttribute("tsmList", tsmList);
		
	}
	
	
	public ActionForward viewDistDetails(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		logger.info("***************************** viewDistDetails() method. Called*****************************");
		HttpSession session = request.getSession();
		ActionErrors errors = new ActionErrors();
		SackDistributorBean sackDistBean = (SackDistributorBean)form;
		String strMessage = "";
		try
		{
            //Security Observation CSRF Implementation by Karan on 20 August 2012
			if(!isTokenValid(request))
			 {
				return mapping.findForward("autherization");
			  }
			
			
			UserSessionContext sessionContext = (UserSessionContext) session.getAttribute(Constants.AUTHENTICATED_USER);
			String circleId = String.valueOf(sessionContext.getCircleId());
			setDefaultValue(sackDistBean, request,circleId);
			String strTSMId = sackDistBean.getTsmSelectedId(); 
			SackDistributorService dcCollectionService =  new SackDistributorServiceImpl(); 
			List<SackDistributorDetailDto>  distributorList = dcCollectionService.getDistDetailList(strTSMId,circleId);
						
			sackDistBean.setDistributorList(distributorList);
			request.setAttribute("distributorList", distributorList);
		}
		catch(Exception e)
		{
			e.printStackTrace();
			logger.info("EXCEPTION OCCURED ::  "+e.getMessage());
			errors.add("errors",new ActionError(e.getMessage()));
			saveErrors(request, errors);
			return mapping.findForward(STATUS_ERROR);
		}finally{
			sackDistBean.setMessage(strMessage);
			
		}
		
		return mapping.findForward(INIT_SUCCESS);
	}
	
	public ActionForward performAction(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		logger.info("***************************** performAction() method. Called*****************************");
		
		HttpSession session = request.getSession();
		ActionErrors errors = new ActionErrors();
		SackDistributorBean sackDistBean = (SackDistributorBean)form;
		String strMessage = "";
		try{
			//Security Observation CSRF Implementation by Karan on 20 August 2012
			if(!isTokenValid(request))
			 {
				return mapping.findForward("autherization");
			  }
			
			
			UserSessionContext sessionContext = (UserSessionContext) session.getAttribute(Constants.AUTHENTICATED_USER);
			Long distId = sessionContext.getId();
			String circleId = String.valueOf(sessionContext.getCircleId());
			setDefaultValue(sackDistBean, request,circleId);
			logger.info(" distId =="+distId+" circleId == "+circleId );
		
			Pattern commaPattern = Pattern.compile(REGEX_COMMA);
		
			String[] arrAccountId = commaPattern.split(sackDistBean.getHidSeletedDistIds()[0]);
			
			logger.info(" arrProdId  =="+arrAccountId[0]+" arrSrNo length == "+arrAccountId.length);
			String strRemarks = sackDistBean.getStrRemarks();
	
			ArrayList<SackDistributorDetailDto> dcCreationDtoList = new ArrayList<SackDistributorDetailDto>();
			for(int count=0;count<arrAccountId.length;count++){
				SackDistributorDetailDto dcCreationDto = new SackDistributorDetailDto();
				logger.info(" productIdListItr.next() =="+arrAccountId[count]);
				dcCreationDto.setAccountId(arrAccountId[count]);
				dcCreationDto.setStrRemarks(strRemarks);
				dcCreationDtoList.add(dcCreationDto);
			
			}
	
			ListIterator<SackDistributorDetailDto> distDetailDtoListItr = dcCreationDtoList.listIterator();
		
			SackDistributorService dcCollectionService =  new SackDistributorServiceImpl(); 
			String message=dcCollectionService.sackDistributor(distDetailDtoListItr);
			if(message.equals("SUCCESS")){
				strMessage = "Distributor has been Disabled successfully";
			}
			
			
	
			
		}catch(Exception e){
			e.printStackTrace();
			logger.info("EXCEPTION OCCURED ::  "+e.getMessage());
			errors.add("errors",new ActionError(e.getMessage()));
			saveErrors(request, errors);
			return mapping.findForward(STATUS_ERROR);
		}finally{
			sackDistBean.setMessage(strMessage);
			sackDistBean.setTsmSelectedId("0");
			sackDistBean.setStrRemarks("");
		}
		return mapping.findForward(INIT_SUCCESS);
	}

}
