package com.ibm.dp.actions;

import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.actions.DispatchAction;
import com.ibm.dp.beans.DPCardGrpMstrFormBean;
import com.ibm.dp.dto.CardMstrDto;
import com.ibm.dp.service.CardGrpMstrService;
import com.ibm.dp.service.impl.CardGrpMstrServiceImpl;
import com.ibm.virtualization.recharge.dto.DPViewProductDTO;
import com.ibm.virtualization.recharge.dto.UserSessionContext;
import com.ibm.virtualization.recharge.service.DPMasterService;
import com.ibm.virtualization.recharge.service.impl.DPMasterServiceImpl;
import com.ibm.virtualization.recharge.utils.Pagination;
import com.ibm.virtualization.recharge.utils.VirtualizationUtils;
import com.ibm.utilities.Utility;

public class CardGrpMstrAction extends DispatchAction {
private static Logger logger = Logger.getLogger(CardGrpMstrAction.class.getName());

	private final static String SUCCESS = "initSuccess";
	private final static String LOGINSUCCESS = "loginSuccess";
	
	public ActionForward init(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		
		logger.info("inside card grp action:::");
		CardMstrDto dto=new CardMstrDto();
		ArrayList productList = new ArrayList();
		DPMasterService dpservice = new DPMasterServiceImpl();
		HttpSession session = request.getSession();
		DPCardGrpMstrFormBean dpCardGrpMstrFormBean = (DPCardGrpMstrFormBean)form;
		CardGrpMstrService cardGrpMstrService= new CardGrpMstrServiceImpl();
		/* if(dpCardGrpMstrFormBean != null)
        {
        	dpCardGrpMstrFormBean.reset(mapping, request);
        }
    	*/
		try{
			int noofpages = dpservice.getProductListCount(dto);
			Pagination pagination = VirtualizationUtils
			.setPaginationinRequest(request);
			productList = cardGrpMstrService.select(dto,
					pagination.getLowerBound(), pagination.getUpperBound());
			Iterator iter = productList.iterator();
			while (iter.hasNext()) {
				CardMstrDto cardDto = (CardMstrDto) iter
						.next();
				
		}
			request.setAttribute("PAGES", noofpages);
			dpCardGrpMstrFormBean.setCardGroupList(productList);
		}
		catch(Exception ex)
		{
			ex.printStackTrace();
		}
		
		
		
		
		//dpCardGrpMstrFormBean.setCardGroupList(cardGrpMstrService.getCardGroupList());
		
		logger.info("asa::::::::::::::::::::::cardGrpMstrService.getCardGroupList()::::::::::::::"+dpCardGrpMstrFormBean.getCardGroupList());
		
       
		return mapping.findForward(SUCCESS);
	}
		
	public ActionForward insertCard(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) throws Exception {

		
		boolean booMessage;
		String message;
		try{
		HttpSession session = request.getSession();
		
		DPCardGrpMstrFormBean dpCardGrpMstrFormBean = (DPCardGrpMstrFormBean)form;
		
		UserSessionContext sessionContext = (UserSessionContext)session.getAttribute("USER_INFO");
		
		dpCardGrpMstrFormBean.setCreatedBy(""+sessionContext.getId());
		
		CardGrpMstrService cardGrpMstrService= new CardGrpMstrServiceImpl();
		
		logger.info("::::::card id:::::::::"+dpCardGrpMstrFormBean.getCardgrpId());
		logger.info("::::::card name::::::::"+dpCardGrpMstrFormBean.getCardgrpName());
		
		booMessage=cardGrpMstrService.insertCardGrp(dpCardGrpMstrFormBean);
		
		if(booMessage)
		{
			message="Card Group Added/Updated Successfully";
		}
		else if(dpCardGrpMstrFormBean.isCheckDuplicateFlag())
		{
			message="Card Group Already Exists";
		}
		
		else 
		{
			message="Card Group Not Added/Updated";
		}
		
		
		
		
		dpCardGrpMstrFormBean.setMessage(message);
		//CardGrpMstrService cardGrpMstrService= new CardGrpMstrServiceImpl();
		
		dpCardGrpMstrFormBean.setCardGroupList(cardGrpMstrService.getCardGroupList());
		logger.info("::::::::::::::::::::::::::::::::::message::::"+message);
		dpCardGrpMstrFormBean.setCardgrpId("");
		dpCardGrpMstrFormBean.setCardgrpName("");
	}
	catch(Exception ex)
	{
		ex.printStackTrace();
	}
		return mapping.findForward(SUCCESS);
	}
	
	
	
	public ActionForward deleteCard(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) throws Exception {

		
		logger.info("asa:::::::::::::::::::deleteCard:::::::::;action");
		boolean booMessage;
		String message;
		try{
		HttpSession session = request.getSession();
		CardGrpMstrService cardGrpMstrService= new CardGrpMstrServiceImpl();
		DPCardGrpMstrFormBean dpCardGrpMstrFormBean = (DPCardGrpMstrFormBean)form;
		
		UserSessionContext sessionContext = (UserSessionContext)session.getAttribute("USER_INFO");
		
		dpCardGrpMstrFormBean.setCreatedBy(""+sessionContext.getId());
		dpCardGrpMstrFormBean.setStatus(request.getParameter("status"));
		dpCardGrpMstrFormBean.setCardgrpId(request.getParameter("cardid"));
	
		
		
		logger.info("::::::card id:::::::::"+dpCardGrpMstrFormBean.getCardgrpId());
		logger.info("::::::card name::::::::"+dpCardGrpMstrFormBean.getCardgrpName());
		
		booMessage=cardGrpMstrService.deleteCardGrp(dpCardGrpMstrFormBean);
		
		if(booMessage)
		{
			message="Card Group Status Changed Successfully";
		}
		else
		{
			message="Card Group Status Change Failed";
		}
		
		
		dpCardGrpMstrFormBean.setMessage(message);
		logger.info("::::::::::::::::::::::::::::::::::message::::"+message);
		//CardGrpMstrService cardGrpMstrService= new CardGrpMstrServiceImpl();
		
		dpCardGrpMstrFormBean.setCardGroupList(cardGrpMstrService.getCardGroupList());
		
		dpCardGrpMstrFormBean.setCardgrpId("");
		dpCardGrpMstrFormBean.setCardgrpName("");
		
	}
	catch(Exception ex)
	{
		ex.printStackTrace();
	}
		return mapping.findForward(SUCCESS);
	}
	/*
	
	public ActionForward getCardGrpList(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) throws Exception {

		try
		{
		
			
		DPCardGrpMstrFormBean dpCardGrpMstrFormBean = (DPCardGrpMstrFormBean)form;
		CardGrpMstrService cardGrpMstrService= new CardGrpMstrServiceImpl();
		
		dpCardGrpMstrFormBean.setCardGroupList(cardGrpMstrService.getCardGroupList());
		
		logger.info("asa::::::::::::::::::::::cardGrpMstrService.getCardGroupList()::::::::::::::"+dpCardGrpMstrFormBean.getCardGroupList());
		
		}
		
		catch(Exception ex)
		{
			ex.printStackTrace();
			logger.error(Utility.getStackTrace(ex));
			logger.info("Exception in getting card grp list "+ex.getMessage());
			
		}	
			
		
		
		
		
		/*PrintWriter out=response.getWriter();
		out.println(amount);
		dpCardGrpMstrFormBean.setAmount(amount);

*/		//return null;

	//	return mapping.findForward(SUCCESS);
	//}*/
	
	
}
