package com.ibm.dp.actions;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.apache.struts.action.ActionError;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.actions.DispatchAction;

import com.ibm.dp.beans.DPReverseCollectionBean;
import com.ibm.dp.common.Constants;
import com.ibm.dp.dto.DPPrintDCDTO;
import com.ibm.dp.service.DPReverseCollectionService;
import com.ibm.dp.service.impl.DPReverseCollectionServiceImpl;

/**
 * DPPrintDCAction class is gateway for other class and interface for DC related data
 *
 * @author Naveen Jain
 */
public class DPPrintDCAction extends DispatchAction {
	private Logger logger = Logger.getLogger(DPProductStatusReportAction.class.getName());
	private static final String INIT_SUCCESS = "initsuccess";
	private static final String FRESH_INIT_SUCCESS = "freshinitsuccess";
	private static final String CHURN_SUCCESS_INIT = "churnsuccessinit";
	/**
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return  success or failure
	 * @throws Exception
	 * @desc get dc details to print
	 */
	public ActionForward printDC(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)	throws Exception 
	{		ActionErrors errors = new ActionErrors();
				
			String dcno = null;
			String strTransType = null;
			DPReverseCollectionBean formBean = (DPReverseCollectionBean) form;
			try 
			{
				dcno = request.getParameter("Dc_No");
				String strDCDate =  request.getParameter("dc_date");
				strTransType = request.getParameter("TransType");
				logger.info("TransType == "+strTransType);
				logger.info("dc_date == "+strDCDate);
//				if(TransType  != null && TransType.equalsIgnoreCase("Fresh"))
//				{
//					dcno= dcno+"#"+TransType;
//				}
				
				DPReverseCollectionService dpdc = new DPReverseCollectionServiceImpl();
				List<List> dcDetailList = dpdc.getDCDetails(dcno);
				List<DPPrintDCDTO> listHeaderInfo = dcDetailList.get(0);
				List<DPPrintDCDTO> listDetailInfo = dcDetailList.get(1);
				List<DPPrintDCDTO> listDetailInfoChurn = null;
				if (strTransType!=null && strTransType.equalsIgnoreCase(Constants.DC_TYPE_CHURN)){
					listDetailInfoChurn = dcDetailList.get(2);
				}
				DPPrintDCDTO dPrintDCDTO = listHeaderInfo.get(0);
				
				formBean.setDc_no(dPrintDCDTO.getDc_no());
				formBean.setDc_date(strDCDate);
				formBean.setFrom_name(dPrintDCDTO.getFrom_name());
				formBean.setCity_name(dPrintDCDTO.getCity_name());
				formBean.setState_name(dPrintDCDTO.getState_name());
				formBean.setContact_name(dPrintDCDTO.getContact_name());
				formBean.setMobile_no(dPrintDCDTO.getMobile_no());
				formBean.setRemarks(dPrintDCDTO.getRemarks());
				formBean.setWh_name(dPrintDCDTO.getWh_name());
				String whAdd1="",whAdd2="";
				if(dPrintDCDTO.getWh_address1()==null)
				{
					whAdd1="";
				}
				else
				{
					whAdd1=dPrintDCDTO.getWh_address1();
				}
				if(dPrintDCDTO.getWh_address2()==null)
				{
					whAdd2="";
				}
				else
				{
					whAdd2=dPrintDCDTO.getWh_address2();
				}
				
				formBean.setWh_address(whAdd1 +" "+ whAdd2);
				formBean.setWh_phone(dPrintDCDTO.getWh_phone());
				formBean.setCourier_agency(dPrintDCDTO.getCourier_agency());
				formBean.setDocket_no(dPrintDCDTO.getDocket_no());
				formBean.setDcDetailsCollectionList(listDetailInfo);
				
				logger.info("SIZE OF LIST  ::::  "+listDetailInfo.size());
				
				if (strTransType!=null && strTransType.equalsIgnoreCase(Constants.DC_TYPE_CHURN))
				{
					formBean.setChurnDcGenerationLess(com.ibm.virtualization.recharge.common.ResourceReader.getCoreResourceBundleValue("churn.dcgeneration.STBsless"));
					formBean.setChurnDcGenerationMore(com.ibm.virtualization.recharge.common.ResourceReader.getCoreResourceBundleValue("churn.dcgeneration.STBsmore"));
					formBean.setDcDetailsCollectionListChurn(listDetailInfoChurn);
					//saveToken(request);
					return mapping.findForward(CHURN_SUCCESS_INIT);
				}
				else if(strTransType!=null && strTransType.equalsIgnoreCase(Constants.DC_TYPE_FRESH))
				{
					saveToken(request);
					return mapping.findForward(FRESH_INIT_SUCCESS);
				}
				else
				{
					saveToken(request);
					return mapping.findForward(INIT_SUCCESS);
				}
				
			} catch (RuntimeException e) {
				e.printStackTrace();
				logger.info("EXCEPTION OCCURED ::  "+e.getMessage());
				errors.add("errors",new ActionError(e.getMessage()));
				saveErrors(request, errors);
				return mapping.findForward(INIT_SUCCESS);
			}
			
	}// end printDC
}





