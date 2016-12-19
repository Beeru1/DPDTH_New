package com.ibm.dp.actions;

import java.io.InputStream;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.StringTokenizer;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.apache.struts.action.ActionError;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessage;
import org.apache.struts.action.ActionMessages;
import org.apache.struts.actions.DispatchAction;
import org.apache.struts.upload.FormFile;

import com.ibm.dp.beans.RecoPeriodConfFormBean;
import com.ibm.dp.dto.RecoPeriodDTO;
import com.ibm.dp.service.DistRecoService;
import com.ibm.dp.service.RecoPeriodConfService;
import com.ibm.dp.service.impl.DistRecoServiceImpl;
import com.ibm.dp.service.impl.RecoPeriodConfServiceImpl;
import com.ibm.virtualization.framework.bean.ChannelType;
import com.ibm.virtualization.recharge.authorization.AuthorizationConstants;
import com.ibm.virtualization.recharge.authorization.AuthorizationServiceImpl;
import com.ibm.virtualization.recharge.common.Constants;
import com.ibm.virtualization.recharge.dto.UserSessionContext;
import com.ibm.virtualization.recharge.service.AuthorizationInterface;

public class RecoPeriodConfAction extends DispatchAction
{	
	private static final String SUCCESS = "success";
	/* Logger for this class. */
	private static Logger logger = Logger.getLogger(RecoPeriodConfAction.class.getName());
	
	private static final String SUCCESS_RECO_HISTORY = "successRecoHistory";
	private static final String ERROR = "error";
	
	
	public ActionForward init(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		
		HttpSession session = request.getSession();
		AuthorizationInterface authorizationService = new AuthorizationServiceImpl();
		UserSessionContext sessionContext = (UserSessionContext) session.getAttribute(Constants.AUTHENTICATED_USER);
		logger.info("-----------------init ACTION CALLED-----------------");
		ActionErrors errors = new ActionErrors();
		if (!authorizationService.isUserAuthorized(sessionContext.getGroupId(), ChannelType.WEB,AuthorizationConstants.ROLE_ADD_CIRCLE))
		{
			logger.info(" user not auth to perform this ROLE_ADD_ACCOUNT activity  ");
			errors.add("errors",new ActionError("errors.usernotautherized"));
			saveErrors(request, errors);
			return mapping.findForward(Constants.USER_NOT_AUTHERIZED);
		}
		logger.info("-----------------getRecoHistory----------------");		
		try{
			
		DistRecoService distRecoService =new  DistRecoServiceImpl();
		RecoPeriodConfFormBean recoBean = (RecoPeriodConfFormBean)form;
	
		recoBean.setStrMessage("");
		recoBean.setFromDate("");
		recoBean.setToDate("");
		getRecoPeriodHistory(request, recoBean );
		getGracePeriod(recoBean );
		recoPeriodOpening(recoBean, request);
		recoPeriodClosing(recoBean);
		
		
		List<RecoPeriodDTO> recoPeriodList= distRecoService.getRecoPeriodListAdmin();
		recoBean.setRecoPeriodList(recoPeriodList);
		
		request.setAttribute("recoPeriodList", recoPeriodList);
		}catch (Exception e) {
			logger.error("Error:" + e.getMessage());
			e.printStackTrace();
			//ActionErrors errors = new ActionErrors();
			errors.add(ActionErrors.GLOBAL_ERROR, new ActionError("system.error"));
			saveErrors(request, errors);
			return mapping.findForward(ERROR);

		}
		return mapping.findForward(SUCCESS_RECO_HISTORY);
		
	
	}
	public void getRecoPeriodHistory(HttpServletRequest request, RecoPeriodConfFormBean recoBean) throws Exception{
		List<RecoPeriodDTO> recoHistoryList = new ArrayList<RecoPeriodDTO>();		
		RecoPeriodConfService recoPeriodConfService = RecoPeriodConfServiceImpl.getInstance();
		recoHistoryList = recoPeriodConfService.getRecoHistory();	
		HttpSession session = request.getSession();
		String lastToDate ="";
		if(!recoHistoryList.isEmpty()){
		lastToDate = recoHistoryList.get(0).getToDate();
		String arr[]=lastToDate.split("-");
		lastToDate = arr[0]+"-"+arr[1]+"-"+arr[2];
		}
		System.out.println("lastToDate---"+lastToDate);
		recoBean.setLastToDate(lastToDate);		
		session.removeAttribute("recoHistoryList");
		session.setAttribute("recoHistoryList",recoHistoryList);
		recoBean.setRecoList(recoHistoryList);
	}
	
	public ActionForward updateRecoPeriodConf(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) throws Exception{
		logger.info("----------updateRecoPeriodConf-------------");
		try{
		HttpSession session = request.getSession();
		UserSessionContext sessionContext = (UserSessionContext) session.getAttribute(Constants.AUTHENTICATED_USER);
		Long loginId = sessionContext.getId();
		String loginUser= loginId.toString();
		RecoPeriodConfFormBean recoBean = (RecoPeriodConfFormBean)form;
		RecoPeriodConfService recoPeriodConfService = RecoPeriodConfServiceImpl.getInstance();
		
		String closeRecoIds = recoBean.getCloseRecoIds();
		String deleteRecoId = recoBean.getDeleteRecoId();
		
		if(closeRecoIds!=null && !closeRecoIds.trim().equals(""))
		{
			logger.info("asa::reco action:close");
			logger.info(closeRecoIds.lastIndexOf(",") +":::"+ closeRecoIds.length()+" closeRecoIds ;"+closeRecoIds);
			
			if(closeRecoIds.lastIndexOf(",") == closeRecoIds.length()-1)
			{
			closeRecoIds = closeRecoIds.substring(0, closeRecoIds.lastIndexOf(","));
			}
			logger.info("closeRecoIds : "+closeRecoIds);
			
			recoPeriodConfService.updateRecoPeriodConf("update",closeRecoIds,loginUser);
			recoBean.setStrMessage("Reco Period Updated Successfully.");		
		}
		
		if(deleteRecoId!=null && !deleteRecoId.trim().equals(""))
		{
			logger.info("asa::Reco action ::: delete");
			recoPeriodConfService.updateRecoPeriodConf("delete",deleteRecoId,loginUser);
			recoBean.setStrMessage("Reco Period Deleted Successfully.");		
		}
			
		 recoBean.setCloseRecoIds("");
		 recoBean.setDeleteRecoId("");
		
		getGracePeriod(form);
		getRecoPeriodHistory(request, recoBean );
		
		}
		catch (Exception e) {
			logger.error("Error:" + e.getMessage());
			e.printStackTrace();
			ActionErrors errors = new ActionErrors();
			errors.add(ActionErrors.GLOBAL_ERROR, new ActionError("system.error"));
			saveErrors(request, errors);
			return mapping.findForward(ERROR);

		}
		return mapping.findForward(SUCCESS_RECO_HISTORY);
	}
	
	public ActionForward addRecoPeriod(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) throws Exception{
		System.out.println("----------addRecoPeriod-------------");
		try{
		HttpSession session = request.getSession();
		UserSessionContext sessionContext = (UserSessionContext) session.getAttribute(Constants.AUTHENTICATED_USER);
		Long loginId = sessionContext.getId();
		String loginUser= loginId.toString();
		RecoPeriodConfFormBean recoBean = (RecoPeriodConfFormBean)form;
		RecoPeriodConfService recoPeriodConfService = RecoPeriodConfServiceImpl.getInstance();
		String fromDate = recoBean.getFromDate();
		String toDate = recoBean.getToDate();
		String gracePeriod = recoBean.getGracePeriodReco();
		
		if(recoPeriodConfService.validateReco(toDate)==1) {
			recoBean.setRecoValidate("Reco can not be created as closing date is overlapping.");
			return mapping.findForward(SUCCESS_RECO_HISTORY);
		}
		if(fromDate!=null && !fromDate.trim().equals(""))
		{
			recoPeriodConfService.addRecoPeriod(fromDate, toDate, loginUser, gracePeriod);
			recoBean.setStrMessage("Reco Period Added Successfully.");		
			recoBean.setFromDate("");
			recoBean.setToDate("");
			
		}
		
	//	recoPeriodConfService.updateGracePeriod(gracePeriod, loginUser);
		if(recoBean.getStrMessage()==null || recoBean.getStrMessage().equals(""))
			recoBean.setStrMessage("Grace Period Updated Successfully.");
			
		
		getGracePeriod(form);
		getRecoPeriodHistory(request, recoBean );
		recoBean.setGracePeriodReco(recoBean.getGracePeriod());
		}
		catch (Exception e) {
			logger.error("Error:" + e.getMessage());
			e.printStackTrace();
			ActionErrors errors = new ActionErrors();
			errors.add(ActionErrors.GLOBAL_ERROR, new ActionError("system.error"));
			saveErrors(request, errors);
			return mapping.findForward(ERROR);

		}
		return mapping.findForward(SUCCESS_RECO_HISTORY);
	}
	
	
	public void getGracePeriod(ActionForm form) throws Exception{
		RecoPeriodConfFormBean recoBean = (RecoPeriodConfFormBean)form;
		RecoPeriodConfService recoPeriodConfService = RecoPeriodConfServiceImpl.getInstance();
		
		String gracePeriod = recoPeriodConfService.getGracePeriod();	
		recoBean.setGracePeriod(gracePeriod);
	}
	//Added by Shiva for frontend validation for reco period configuration
	public void recoPeriodOpening(ActionForm form, HttpServletRequest request) throws Exception{
		RecoPeriodConfFormBean recoBean = (RecoPeriodConfFormBean)form;
		RecoPeriodConfService recoPeriodOpening = RecoPeriodConfServiceImpl.getInstance();
		
		System.out.println("recoPeriodHist.recoPeriodHist()"+ recoPeriodOpening.recoPeriodOpening());
		recoBean.setRecoOpening(recoPeriodOpening.recoPeriodOpening());
		System.out.println("recoBean.getRecoOpening() "+"   "+recoBean.getRecoOpening());

	}
	
	public void recoPeriodClosing(ActionForm form) throws Exception{
		RecoPeriodConfFormBean recoBean = (RecoPeriodConfFormBean)form;
		RecoPeriodConfService  recoPeriodClosing = RecoPeriodConfServiceImpl.getInstance();
		
		System.out.println("recoPeriodClosing.recoPeriodClosing()"+ recoPeriodClosing.recoPeriodClosing());
		recoBean.setRecoClosing(recoPeriodClosing.recoPeriodClosing());
		System.out.println("recoBean.getRecoClosing() "+"   "+recoBean.getRecoClosing());
	}
	//--End   shiva
	
	public void getOldestRecoId(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) throws Exception{
		RecoPeriodConfService recoPeriodConfService = RecoPeriodConfServiceImpl.getInstance();
		
		String recoPeriodIDs = request.getParameter("closeRecoIds") ;
		if(recoPeriodIDs.lastIndexOf(",") == recoPeriodIDs.length()-1)
		{
			recoPeriodIDs = recoPeriodIDs.substring(0, recoPeriodIDs.lastIndexOf(","));
		}
		
		String oldestID = String.valueOf(recoPeriodConfService.getOldestRecoId(recoPeriodIDs));
		System.out.println(" oldestID : "+oldestID);
		response.setContentType("text/html");
		response.setHeader("Cache-Control", "No-Cache");
		PrintWriter out = response.getWriter();
		out.write(oldestID);
		out.flush();		
	}
	
	public ActionForward updateGracePeriod(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		try{
		HttpSession session = request.getSession();
		UserSessionContext sessionContext = (UserSessionContext) session.getAttribute(Constants.AUTHENTICATED_USER);
		Long loginId = sessionContext.getId();
		String loginUser= loginId.toString();
		System.out.println("----------updateGracePeriod-------------");
		String gracePeriod = request.getParameter("gracePeriod");
		RecoPeriodConfService recoPeriodConfService = RecoPeriodConfServiceImpl.getInstance();
		recoPeriodConfService.updateGracePeriod(gracePeriod, loginUser);
		RecoPeriodConfFormBean recoBean = (RecoPeriodConfFormBean)form;
		recoBean.setGracePeriodReco(recoBean.getGracePeriod());
		recoBean.setStrMessage("Grace Period Updated Successfully.");
		getRecoPeriodHistory(request, recoBean );
		}catch (Exception e) {
			logger.error("Error:" + e.getMessage());
			e.printStackTrace();
			ActionErrors errors = new ActionErrors();
			errors.add(ActionErrors.GLOBAL_ERROR, new ActionError("system.error"));
			saveErrors(request, errors);
			return mapping.findForward(ERROR);

		}
		return mapping.findForward(SUCCESS_RECO_HISTORY);
	}
	
	//Added by sugandha to view grace period for a particular recoId
	public ActionForward viewGracePeriod(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) throws Exception 
			{
		HttpSession session = request.getSession();
		String recoPeriodId=request.getParameter("recoPeriodId");
		logger.info("recoPeriodId::::::::"+recoPeriodId);
		logger.info("*************************viewGracePeriod for a paricular recoId***************");
			try
			{
				RecoPeriodConfService recoService = RecoPeriodConfServiceImpl.getInstance();
				RecoPeriodConfFormBean recoBean = (RecoPeriodConfFormBean)form;
				List<RecoPeriodDTO> recoPeriodList = recoService.viewRecoGracePeriod(recoPeriodId);
				recoBean.setRecoPeriodList(recoPeriodList);
				request.setAttribute("recoPeriodList", recoPeriodList);
			}
			catch(Exception ex){
				ex.printStackTrace();
			}
			return mapping.findForward("successGracePeriod");
	}
	//Added by sugandha to update graceperiod of corresponding recoId
	public ActionForward updateRecoGracePeriod(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) throws Exception 
			{
		HttpSession session = request.getSession();
		RecoPeriodConfFormBean recoBean = (RecoPeriodConfFormBean)form;
		System.out.println(">><<<>>>>> :: "+recoBean.getGracePeriod());
		String newGracePeriod=recoBean.getGracePeriod();
		UserSessionContext sessionContext = (UserSessionContext) session.getAttribute(Constants.AUTHENTICATED_USER);
		String recoPeriodId=request.getParameter("recoPeriodId");
		logger.info("recoPeriodId:::"+recoPeriodId);
		Long loginId = sessionContext.getId();
		String loginUser= loginId.toString();
		logger.info("*************************updateRecoGracePeriod for a paricular recoId***************");
		ActionErrors errors = new ActionErrors();
		try
		{
		RecoPeriodConfService recoPeriodConfService = RecoPeriodConfServiceImpl.getInstance();
		recoPeriodConfService.updateOtherGracePeriod(newGracePeriod, loginUser,recoPeriodId);
		recoBean.setGracePeriod(newGracePeriod);
		errors.add("success.grace",new ActionError("success.grace.updateGrace"));
		saveErrors(request, errors);
		}
		catch (Exception e)
			{
			logger.error("Error:" + e.getMessage());
			e.printStackTrace();
			
			errors.add(ActionErrors.GLOBAL_ERROR, new ActionError("system.error"));
			saveErrors(request, errors);
		}
			return viewGracePeriod( mapping,  form,request,  response);
	}

	public ActionForward deleteReco(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) throws Exception 
			{
		HttpSession session = request.getSession();
		UserSessionContext sessionContext = (UserSessionContext) session.getAttribute(Constants.AUTHENTICATED_USER);
		RecoPeriodConfFormBean recoBean = (RecoPeriodConfFormBean)form;
		
		String recoPeriodId=request.getParameter("recoPeriodId");
		logger.info("recoPeriodId::::"+recoPeriodId);
		Long loginId = sessionContext.getId();
		String loginUser= loginId.toString();
		ActionErrors errors = new ActionErrors();
		logger.info("*************************delete Reco details for a paricular recoId***************");
		try
		{
		RecoPeriodConfService recoPeriodConfService = RecoPeriodConfServiceImpl.getInstance();
		recoPeriodConfService.deleteReco(loginUser,recoPeriodId);
		recoBean.setStrMessage("Reco details has been deleted");
		errors.add("success.grace",new ActionError("success.grace.deleteReco"));
		saveErrors(request, errors);
		}
		catch (Exception e)
			{
			logger.error("Error:" + e.getMessage());
			e.printStackTrace();
		
			errors.add(ActionErrors.GLOBAL_ERROR, new ActionError("system.error"));
			saveErrors(request, errors);
		}
		return viewGracePeriod( mapping,  form,request,  response);
	}
	
	public ActionForward updatePartnerData(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		RecoPeriodConfService recoPeriodConfService=null;
		RecoPeriodConfFormBean recoBean =null;
		try
		{
		HttpSession session = request.getSession();
		recoBean = (RecoPeriodConfFormBean)form;
		FormFile file = recoBean.getFile();
		String recoId=recoBean.getRecoPeriodId();
		InputStream myxls = file.getInputStream();
		UserSessionContext sessionContext = (UserSessionContext) session.getAttribute(Constants.AUTHENTICATED_USER);
		
		String arr= (file.getFileName().toString()).substring(file.getFileName().toString().lastIndexOf(".")+1,file.getFileName().toString().length());
		recoPeriodConfService = RecoPeriodConfServiceImpl.getInstance();
			if(!arr.equalsIgnoreCase("csv") )
			{
				ActionMessages messages = new ActionMessages();
				messages.add("PO_FILE_TYPE_WRONG",new ActionMessage("stb.file.type"));
				saveMessages(request, messages);
			}
			else
			{
				
				List list = recoPeriodConfService.updatePartnerData(file,recoId);
				session.removeAttribute("error_file");
				session.setAttribute("error_file",list);
				System.out.println("list size:::::::"+list);
				RecoPeriodDTO recoPeriodDTO = null;
				boolean checkValidate = true;
				if(list.size() > 0)
				{
					
					Iterator itt = list.iterator();
					while(itt.hasNext())
					{
						recoPeriodDTO = (RecoPeriodDTO) itt.next();
						System.out.println("recoPeriodDTO.getErr_msg()::::::"+recoPeriodDTO.getErr_msg());
						if(recoPeriodDTO.getErr_msg()!= null && !recoPeriodDTO.getErr_msg().equalsIgnoreCase("SUCCESS"))
						{
							checkValidate = false;
							break;
						} 
					}
					if(checkValidate)
					{
						
						String gracePeriod=recoBean.getGracePeriodMod();
						String retFlag = recoPeriodConfService.updatePartnerDataDB(list,recoId,gracePeriod);
						recoBean.setError_flag("false");
						if(retFlag != null && retFlag.equalsIgnoreCase("true"))
						{
							recoBean.setStrmsg("RECO Data Updated Successfully");
							
							// SMS ALERT for dist list,recoId,gracePeriod
							try
							{
							recoPeriodConfService.createSMSAlerts(list,recoId,gracePeriod);
							}catch (Exception e) {
								e.printStackTrace();
							}
						}
						else
						{
							recoBean.setStrmsg("");
						}
						
					}
					else
					{
						
						
						recoBean.setError_flag("true");
						//recoBean.setError_file(list);
						
					}
				}
			}
		}
		catch(Exception ex)
		{
			logger.info(ex);
		}
		DistRecoService distRecoService =new  DistRecoServiceImpl();
		List<RecoPeriodDTO> recoPeriodList= distRecoService.getRecoPeriodListAdmin();
		recoBean.setRecoPeriodList(recoPeriodList);
		request.setAttribute("recoPeriodList", recoPeriodList);
		return mapping.findForward(SUCCESS);	
	}

	public ActionForward showErrorFile(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		return mapping.findForward("errorFile");
	}
	
	
	//validation for creating reco
	

	public ActionForward validateOverlappingReco(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) throws Exception{
		logger.info("asa::::::validate");
		System.out.println("----------validateRecoPeriod-------------");
		int intValidate=0;
		try{
		HttpSession session = request.getSession();
		//UserSessionContext sessionContext = (UserSessionContext) session.getAttribute(Constants.AUTHENTICATED_USER);
		RecoPeriodConfFormBean recoBean = (RecoPeriodConfFormBean)form;
		RecoPeriodConfService recoPeriodConfService = RecoPeriodConfServiceImpl.getInstance();
		String toDate = recoBean.getToDate();
		logger.info("asa::::toDate:::::::::::::"+toDate);
		intValidate=recoPeriodConfService.validateReco(toDate);
		logger.info("action:::::::::::::::::toDate::::::::::::::::"+toDate);
		logger.info("action::::::::::::::intValidate"+intValidate);
		recoBean.setValidateReco(String.valueOf(intValidate));
		request.setAttribute("recoBean",recoBean);
		//request.setAttribute(recoBean.getValidateReco(),"intValidate");
		logger.info("action:::::::::::;recoBean.getvalidatereco"+recoBean.getValidateReco());
		
		}
		catch (Exception e) {
			logger.error("Error:" + e.getMessage());
			e.printStackTrace();
			ActionErrors errors = new ActionErrors();
			errors.add(ActionErrors.GLOBAL_ERROR, new ActionError("system.error"));
			saveErrors(request, errors);
			return mapping.findForward(ERROR);

		}
		return mapping.findForward(SUCCESS_RECO_HISTORY);
	}
	//end
	

	public ActionForward validateAction(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) throws Exception{
		logger.info("asa::::::validate");
		System.out.println("----------validateRecoPeriod-------------");
		int intValidate=0;
		String recoPeriod="";
		boolean validateRecoPeriod;
		int validateAction=0;
		
		try{
		HttpSession session = request.getSession();
		//UserSessionContext sessionContext = (UserSessionContext) session.getAttribute(Constants.AUTHENTICATED_USER);
		RecoPeriodConfFormBean recoBean = (RecoPeriodConfFormBean)form;
		RecoPeriodConfService recoPeriodConfService = RecoPeriodConfServiceImpl.getInstance();
		DistRecoService distRecoService = new DistRecoServiceImpl();
		PrintWriter out=null;
		recoPeriod=recoBean.getRecoID();
		String toDate = recoBean.getToDate();
		String closeRecoIds="";
		String deleteRecoId1="";
		String selectedRecoIds="";
		String closeRecoIds1="";
		String recoIds1="";
		String deleteRecoId="";
		
		closeRecoIds= request.getParameter("closeRecoIds");
		logger.info("closeRecoIds"+closeRecoIds);
		/*if(!closeRecoIds.equalsIgnoreCase("") || !deleteRecoId.equalsIgnoreCase(""))
		{
				if (!closeRecoIds.equalsIgnoreCase(""))
				{	
					closeRecoIds1= closeRecoIds.substring(0,closeRecoIds.length()-1);
				}	
				
				deleteRecoId=request.getParameter("deleteRecoId");
				logger.info("closeRecoIds after substring"+closeRecoIds+"deleteRecoId"+deleteRecoId);
				if (!deleteRecoId.equalsIgnoreCase(""))
				{
					deleteRecoId1= deleteRecoId.substring(0,deleteRecoId.length()-1);
					logger.info("closeRecoIds after substring"+closeRecoIds+"recoIds1"+recoIds1);
				}
				
				
				if (!closeRecoIds.equalsIgnoreCase("") && !deleteRecoId.equalsIgnoreCase(""))
					
				{
					StringBuilder recoIds=new StringBuilder().append(closeRecoIds).append(deleteRecoId1);
					
					recoIds1=recoIds.toString();	
					logger.info("closeRecoIds after substring....."+closeRecoIds+"recoIds1"+recoIds1);
				}
				
				else if (!closeRecoIds.equalsIgnoreCase("") && deleteRecoId.equalsIgnoreCase(""))
				{
					recoIds1=closeRecoIds1;
					logger.info("closeRecoIds after substring.."+closeRecoIds+"recoIds1"+recoIds1);
				}
				
				else if (closeRecoIds.equalsIgnoreCase("") && !deleteRecoId.equalsIgnoreCase(""))
				{
					recoIds1=deleteRecoId1;
					logger.info("closeRecoIds after substring..."+closeRecoIds+"recoIds1"+recoIds1);
				}
				//validateRecoPeriod=distRecoService.compareRecoGoLiveDate(closeRecoIds1);
				logger.info("closeRecoIds after substring"+closeRecoIds+"recoIds1"+recoIds1);
				
				validateRecoPeriod=distRecoService.compareRecoGoLiveDate(recoIds1);
				
				if(!validateRecoPeriod)//if reco is old
				{
					//validateAction=recoPeriodConfService.validateAction1(closeRecoIds1);
					validateAction=recoPeriodConfService.validateAction1(recoIds1);
				}
				
				response.setHeader("Cache-Control", "No-Cache");
				out=response.getWriter();
				out.println(validateAction);
				out.flush();
				
				
		}*/
		int recotemp=0;
		if (!closeRecoIds.equalsIgnoreCase(""))
		{	
			if(closeRecoIds.indexOf(",")>0)
			{
			
			closeRecoIds1= closeRecoIds.substring(0,closeRecoIds.length()-1);
			}
			else
			{
				closeRecoIds1= closeRecoIds;
				logger.info(closeRecoIds1);
			}
			StringTokenizer st = new StringTokenizer(closeRecoIds1,",");
			while(st.hasMoreTokens())
			{
				 recotemp=Integer.parseInt(st.nextToken());
			
			logger.info("closeRecoIds "+closeRecoIds+"recotemp"+recotemp);
			validateRecoPeriod=distRecoService.compareRecoGoLiveDate(""+recotemp);
			logger.info("validateRecoPeriod "+validateRecoPeriod+"recotemp"+recotemp);
			if(validateRecoPeriod)//if reco is old
			{
				//validateAction=recoPeriodConfService.validateAction1(closeRecoIds1);
				validateAction=recoPeriodConfService.validateAction1(""+recotemp);
				logger.info("validateAction "+validateAction);
			}
			if(validateAction>0)
			{
				logger.info("validateAction break ");
				break;
				
			}
			}
			response.setHeader("Cache-Control", "No-Cache");
			out=response.getWriter();
			out.println(validateAction);
			out.flush();
		}	
		
		}
		catch (Exception e) {
			logger.error("Error:" + e.getMessage());
			e.printStackTrace();
			ActionErrors errors = new ActionErrors();
			errors.add(ActionErrors.GLOBAL_ERROR, new ActionError("system.error"));
			saveErrors(request, errors);
			return mapping.findForward(ERROR);

		}
		return null;
	}
	
	
}


