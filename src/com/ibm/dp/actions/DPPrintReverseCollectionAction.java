
package com.ibm.dp.actions;

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

import com.ibm.dp.beans.DPPrintBulkAcceptanceBean;
import com.ibm.dp.beans.DPPrintReverseCollectionBean;
import com.ibm.dp.beans.DPReverseCollectionBean;
import com.ibm.dp.common.Constants;
import com.ibm.dp.dao.impl.DPReverseChurnCollectionDAOImpl;
import com.ibm.dp.dao.impl.DPReverseCollectionDaoImpl;
import com.ibm.dp.dto.DPPoAcceptanceBulkDTO;
import com.ibm.dp.dto.DPPrintBulkAcceptanceDTO;
import com.ibm.dp.dto.DPPrintDCDTO;
import com.ibm.dp.dto.DPReverseCollectionDto;
import com.ibm.dp.dto.WrongShipmentDTO;
import com.ibm.dp.service.DPPrintBulkAcceptanceService;
import com.ibm.dp.service.DPPrintReverseCollectionService;
import com.ibm.dp.service.DPReverseCollectionService;
import com.ibm.dp.service.impl.DPReverseCollectionServiceImpl;
import com.ibm.reports.service.impl.DPPrintBulkAcceptanceServiceImpl;
import com.ibm.reports.service.impl.DPPrintReverseCollectionServiceImpl;
import com.ibm.virtualization.recharge.dto.UserSessionContext;

/**
 * DPPrintDCAction class is gateway for other class and interface for DC related data
 *
 * @author Naveen Jain
 */
public class DPPrintReverseCollectionAction extends DispatchAction {
	private Logger logger = Logger.getLogger(DPPrintReverseCollectionAction.class.getName());
	private static final String INIT_SUCCESS = "initsuccess";
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
	public ActionForward printReverseCollection(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)	throws Exception 
	{		ActionErrors errors = new ActionErrors();
				
				
				
			try 
			{
				DPPrintReverseCollectionBean formBean = (DPPrintReverseCollectionBean) form;
				//dcNo = request.getParameter("Dc_No");
				//List<WrongShipmentDTO> dcNoList=(List<WrongShipmentDTO>) request.getAttribute("dcNoList");
				//String[] arrShort_sr_no=(String[]) request.getAttribute("ShortSerialNum");
				HttpSession session = request.getSession();
				UserSessionContext sessionContext = (UserSessionContext) session.getAttribute(Constants.AUTHENTICATED_USER);
				List<DPReverseCollectionDto> upgradeCollectionDtoList=(List<DPReverseCollectionDto>) session.getAttribute("upgradeCollectionDtoList");
				DPPrintReverseCollectionService  printReverseCollectionService  =new DPPrintReverseCollectionServiceImpl();
				String productName="";
				String status="";
				int dcCount=0;
				int count=0;
				logger.info("Print Thread is -"+Thread.currentThread().getId());
				
				//List<DPReverseCollectionDto> upgradeCollectionDtoList=DPReverseCollectionDaoImpl.threadLocal.get();
				for (DPReverseCollectionDto upgradeCollectionDto : upgradeCollectionDtoList) {
					productName=printReverseCollectionService.getProductName(upgradeCollectionDto.getProductId());
					upgradeCollectionDto.setProductName(productName);
					logger.info("upgradeCollectionDto.isFlagCheck()-"+upgradeCollectionDto.getFlagCheck());
					if(upgradeCollectionDto.getFlagCheck().equals("true")){
						upgradeCollectionDto.setDcStatus("Available for Stock");
						dcCount++;
					}
					else if(upgradeCollectionDto.getFlagCheck().equals("false")){
						upgradeCollectionDto.setDcStatus("Available for DC");
						count++;
					}
				}
				formBean.setUpgradeCollectionDtoList(upgradeCollectionDtoList);
				session.removeAttribute("upgradeCollectionDtoList");
				
				
				saveToken(request);
				//DPReverseCollectionDaoImpl.threadLocal.remove();
			} catch (RuntimeException e) {
				e.printStackTrace();
				logger.info("EXCEPTION OCCURED ::  "+e.getMessage());
				errors.add("errors",new ActionError(e.getMessage()));
				saveErrors(request, errors);
			//	DPReverseCollectionDaoImpl.threadLocal.remove();
				return mapping.findForward(INIT_SUCCESS);
				
			}
			
			return mapping.findForward(INIT_SUCCESS); 
	}// end printDC
}





