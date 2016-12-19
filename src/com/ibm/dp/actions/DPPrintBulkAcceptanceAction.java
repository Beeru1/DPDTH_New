
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
import com.ibm.dp.beans.DPReverseCollectionBean;
import com.ibm.dp.common.Constants;
import com.ibm.dp.dto.DPPoAcceptanceBulkDTO;
import com.ibm.dp.dto.DPPrintBulkAcceptanceDTO;
import com.ibm.dp.dto.DPPrintDCDTO;
import com.ibm.dp.dto.WrongShipmentDTO;
import com.ibm.dp.service.DPPrintBulkAcceptanceService;
import com.ibm.dp.service.DPReverseCollectionService;
import com.ibm.dp.service.impl.DPReverseCollectionServiceImpl;
import com.ibm.reports.service.impl.DPPrintBulkAcceptanceServiceImpl;
import com.ibm.virtualization.recharge.dto.UserSessionContext;

/**
 * DPPrintDCAction class is gateway for other class and interface for DC related data
 *
 * @author Naveen Jain
 */
public class DPPrintBulkAcceptanceAction extends DispatchAction {
	private Logger logger = Logger.getLogger(DPPrintBulkAcceptanceAction.class.getName());
	private static final String INIT_SUCCESS = "initsuccess";
	private static final String FRESH_INIT_SUCCESS = "freshinitsuccess";
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
	public ActionForward printBulkAcceptance(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)	throws Exception 
	{		ActionErrors errors = new ActionErrors();
				
				
				
			try 
			{
				DPPrintBulkAcceptanceBean formBean = (DPPrintBulkAcceptanceBean) form;
				//dcNo = request.getParameter("Dc_No");
				//List<WrongShipmentDTO> dcNoList=(List<WrongShipmentDTO>) request.getAttribute("dcNoList");
				//String[] arrShort_sr_no=(String[]) request.getAttribute("ShortSerialNum");
				HttpSession session = request.getSession();
				UserSessionContext sessionContext = (UserSessionContext) session.getAttribute(Constants.AUTHENTICATED_USER);
				
				String invoice_no=(String) request.getParameter("invoice_no");
				Integer  totalSerialNumbers=(Integer) session.getAttribute("totalSerialNumbers");
				if(totalSerialNumbers==null)
					totalSerialNumbers=0;
				String distId= String.valueOf(sessionContext.getId());
				//String productId= String.valueOf(request.getParameter("productId"));
				request.removeAttribute("invoice_no");
				
				DPPrintBulkAcceptanceService  printBulkAcceptanceService  =new DPPrintBulkAcceptanceServiceImpl();
				int invoiceQty=printBulkAcceptanceService.getInvoiceQty(invoice_no);
				List<DPPrintBulkAcceptanceDTO> correctSTB=printBulkAcceptanceService.getCorrectSTB(distId, invoice_no);
				List<DPPrintBulkAcceptanceDTO> wrongSTB=printBulkAcceptanceService.getWrongShippedSTB(distId, invoice_no);
				
				List<DPPrintBulkAcceptanceDTO> missingSTB=printBulkAcceptanceService.getMissingSTB(distId, invoice_no);
				String poNo=printBulkAcceptanceService.getPONo(invoice_no);
				String poQty=printBulkAcceptanceService.getPOQty(invoice_no);
				
				session.removeAttribute("totalSerialNumbers");
				formBean.setDcNo(invoice_no);
				formBean.setTotalReceived(String.valueOf(totalSerialNumbers));
				if(wrongSTB!=null)
				formBean.setTotalWrongShipped(String.valueOf(wrongSTB.size()));
				if(missingSTB!=null)
				formBean.setTotalMissing(String.valueOf(missingSTB.size()));
				formBean.setInvoiceQty(String.valueOf(invoiceQty));
				formBean.setStbcorrectList(correctSTB);
				formBean.setStbwrongList(wrongSTB);
				formBean.setPoNo(poNo);
				formBean.setPoQty(poQty);
				formBean.setStbmissingList(missingSTB);
				//formBean.setTotalWrongShipped(totalWrongShipped);
				//if(arrShort_sr_no!=null)
				//formBean.setTotalMissing(String.valueOf(arrShort_sr_no.length));
				//request.setAttribute("dcNoList", dcNoList);
				
				saveToken(request);
			} catch (RuntimeException e) {
				e.printStackTrace();
				logger.info("EXCEPTION OCCURED ::  "+e.getMessage());
				errors.add("errors",new ActionError(e.getMessage()));
				saveErrors(request, errors);
				return mapping.findForward(INIT_SUCCESS);
			}
			return mapping.findForward(INIT_SUCCESS); 
	}// end printDC
}





