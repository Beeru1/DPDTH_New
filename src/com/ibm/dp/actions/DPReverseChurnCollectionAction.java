
package com.ibm.dp.actions;

import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Iterator;
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
import org.apache.struts.action.ActionMessage;
import org.apache.struts.action.ActionMessages;
import org.apache.struts.actions.DispatchAction;
import org.apache.struts.upload.FormFile;

import com.ibm.dp.beans.DPReverseChurnCollectionBean;
import com.ibm.dp.dto.DPReverseChurnCollectionDTO;
import com.ibm.dp.service.DPReverseChurnCollectionService;
import com.ibm.dp.service.impl.DPReverseChurnCollectionServiceImpl;
import com.ibm.dp.service.impl.DPWrongShipmentServiceImpl;
import com.ibm.hbo.common.HBOConstants;
import com.ibm.hbo.common.HBOUser;
import com.ibm.virtualization.framework.bean.ChannelType;
import com.ibm.virtualization.recharge.authorization.AuthorizationFactory;
import com.ibm.virtualization.recharge.authorization.AuthorizationServiceImpl;
import com.ibm.virtualization.recharge.common.Constants;
import com.ibm.virtualization.recharge.common.Utility;
import com.ibm.virtualization.recharge.dto.UserSessionContext;
import com.ibm.virtualization.recharge.service.AuthorizationInterface;
import com.ibm.virtualization.recharge.utils.Pagination;
import com.ibm.virtualization.recharge.utils.VirtualizationUtils;

/**
 * Controller class for reverse collection  management 
 * @author Shah Rabbani
 */
public class DPReverseChurnCollectionAction extends DispatchAction {
	
	//private static final String SUCCESS = "success";
	private static final String INIT_UPLOAD_SUCCESS = "uploadSuccess";
	
	/* Logger for this Action class. */
	private static Logger logger = Logger.getLogger(DPReverseChurnCollectionAction.class.getName());

	public static final String INIT_SUCCESS = "initsuccess";
	public static final String CHURN_SUCCESS = "churnsuccess";

	
	public ActionForward init(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)	throws Exception 
	{
		logger.info("***************************** initChurnReverseCollection() method.*****************************");
		DPReverseChurnCollectionBean reverseCollectionBean = (DPReverseChurnCollectionBean)form;
		DPReverseChurnCollectionBean dppfb = (DPReverseChurnCollectionBean)form;
		ActionErrors errors = new ActionErrors();
		try 
		{	
			reverseCollectionBean.setError_flag("false");
			reverseCollectionBean.setMessage("");
			
			
			HttpSession session = request.getSession();
			UserSessionContext userSessionContext = (UserSessionContext) session.getAttribute(Constants.AUTHENTICATED_USER);
			
			DPReverseChurnCollectionService churnCollectionService = new DPReverseChurnCollectionServiceImpl();
			
			
			HBOUser hboUser = new HBOUser(userSessionContext);
			int countdistTransactionType = churnCollectionService.checkTransactionTypeReverse(hboUser.getId());//Neetika
			//logger.info("In method countdistTransactionType()."+countdistTransactionType);
			session.setAttribute("CPEFlag", countdistTransactionType);
			reverseCollectionBean.setTypedist(countdistTransactionType);
			
			ArrayList pendingChurnList = new ArrayList();

			pendingChurnList = ((ArrayList)churnCollectionService.viewPendingChurnSTBList(hboUser.getId()));
			
			dppfb.setPoList(pendingChurnList);
			
			request.setAttribute("porList", pendingChurnList);

			dppfb.setRemarks("");
		}
		catch(Exception e)		
		{
			dppfb.setMessage("Internal Error Occured");
			logger.info("EXCEPTION OCCURED ::  "+e.getMessage());
			errors.add("errors",new ActionError(e.getMessage()));
			saveErrors(request, errors);
			return mapping.findForward(INIT_SUCCESS);
		}
		return mapping.findForward(INIT_SUCCESS);
	}
	
	public ActionForward uploadExcel(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
	throws Exception 
	{
		String msg="";
		ActionErrors errors = new ActionErrors();
		HttpSession session = request.getSession();
		DPReverseChurnCollectionService churnService = new DPReverseChurnCollectionServiceImpl();
		logger.info("****************uploadExcel**********************");
		DPReverseChurnCollectionBean churnBean = (DPReverseChurnCollectionBean) form;
		try
		{
			UserSessionContext sessionContext = (UserSessionContext) session.getAttribute(Constants.AUTHENTICATED_USER);
			Long lnUserID = sessionContext.getId();
			int circleId = sessionContext.getCircleId();
			
			churnBean.setMessage("");
			
			ArrayList churnList = new ArrayList(); 
			HBOUser hboUser = new HBOUser(sessionContext);
			
			FormFile file = churnBean.getUploadedFile();
			String fileName = file.getFileName().toString();
			String arr = (file.getFileName().toString()).substring(fileName.lastIndexOf(".") + 1, fileName.toString().length());
			/*int countdistTransactionType = churnService.checkTransactionTypeReverse(lnUserID);//Neetika
			logger.info("In method countdistTransactionType()."+countdistTransactionType);
			session.setAttribute("CPEFlag", countdistTransactionType);
			churnBean.setTypedist(countdistTransactionType);*/
			if (!arr.equalsIgnoreCase("csv")) 
			{
				msg = "This file is not correct file. Upload csv file. ";
				churnBean.setMessage(msg);
				ActionMessages messages = new ActionMessages();
				messages.add("STB_FILE_TYPE_WRONG", new ActionMessage("stb.file.type"));
				saveMessages(request, messages);
			} 
			else 
			{
				List list = churnService.uploadExcel(file);
				session.removeAttribute("error_file");
				session.setAttribute("error_file", list);
		
				DPReverseChurnCollectionDTO churnDTO = null;
				String stbList = "";
				boolean checkValidate = true;
				if (list.size() > 0) 
				{
					Iterator itt = list.iterator();
					while (itt.hasNext()) 
					{
						churnDTO = (DPReverseChurnCollectionDTO) itt.next();
						if (churnDTO.getErr_msg() != null && !churnDTO.getErr_msg().equalsIgnoreCase(Constants.CHURN_STOCK_OK_MSG)) 
						{
							checkValidate = false;
							break;
						}
					}
					if (checkValidate) 
					{
						churnBean.setError_flag("false");
						DPReverseChurnCollectionDTO churnBulk = new DPReverseChurnCollectionDTO();
						try 
						{
							if (list.size() > 0) 
							{
								Iterator iter = list.iterator();
								while (iter.hasNext()) 
								{
									churnDTO = (DPReverseChurnCollectionDTO) iter.next();
									List  stbSrno = churnDTO.getList_srno();
									if(stbSrno !=null && stbSrno.size() > 0)
									{
										for(int i=0;i<stbSrno.size();i++)
										{
											stbList += "'"+ stbSrno.get(i)+ "',";
											if(i==100)
												break;
										}
									}				
								}
							}
							
							stbList = stbList.substring(0, stbList.lastIndexOf(','));
							churnBulk.setSerial_Number(stbList);
							ArrayList serialNoList = churnService.insertFreshChurnSTB(churnBulk,lnUserID,circleId);
							logger.info("serialNoList..." + serialNoList.size());
							churnBean.setAvailableSerialNosList(serialNoList);
							msg = serialNoList.size()+" STB uploaded successfully";
							churnBean.setMessage(msg);
							
							//Refreshing Page
							churnList = (ArrayList)churnService.viewPendingChurnSTBList(hboUser.getId());
							
							churnBean.setPoList(churnList);
							
				            churnBean.setStatusId("0");
						} 
						catch (Exception e) 
						{						
							e.printStackTrace();
							churnBean.setMessage("Internal Error Occured");
							logger.info("EXception while upload excel  ::  "+e.getMessage());
							errors.add("errors",new ActionError(e.getMessage()));
							saveErrors(request, errors);
						}
					} 
					else 
					{
						churnBean.setError_flag("true");
						return mapping.findForward(INIT_SUCCESS);
					}
				}
			}
		}
		catch (Exception e) 
		{
			churnBean.setMessage("Internal Error Occured");
			logger.info("EXCEPTION OCCURED ::  "+e.getMessage());
			errors.add("errors",new ActionError(e.getMessage()));
			saveErrors(request, errors);
			return mapping.findForward(INIT_SUCCESS);
		}
		return mapping.findForward(INIT_SUCCESS);
	}
	
	public ActionForward acceptChurnGenerateDC(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) 
	throws Exception
	{
		logger.info("-----------------accept Generate DC Option ACTION CALLED-----------------");
		ActionErrors errors = new ActionErrors();
		DPReverseChurnCollectionBean churnBean = (DPReverseChurnCollectionBean) form;
		try 
		{	
			churnBean.setError_flag("false");
			HttpSession session = request.getSession();
			
			UserSessionContext sessionContext = (UserSessionContext) session.getAttribute(Constants.AUTHENTICATED_USER);
			long loginUserId = sessionContext.getId();
			String strRemarks = churnBean.getRemarks();
			
			String[] arrCheckedChurn = request.getParameterValues("strCheckedChurn");
			
			DPReverseChurnCollectionService churnService = new DPReverseChurnCollectionServiceImpl();
			
			ArrayList<DPReverseChurnCollectionDTO> churnList = churnService.reportChurn(arrCheckedChurn, loginUserId, strRemarks);
			
			churnBean.setPoList(churnList);

            churnBean.setStatusId("0"); 
            churnBean.setRemarks("");
		}		
		catch(Exception e)		
		{
			churnBean.setMessage("Internal Error Occured");
			logger.info("EXCEPTION OCCURED ::  "+e.getMessage());
			errors.add("errors",new ActionError(e.getMessage()));
			saveErrors(request, errors);
			return mapping.findForward(INIT_SUCCESS);
		} 
		return mapping.findForward(CHURN_SUCCESS); 
	}
	
	public ActionForward acceptChurnAddToStock(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		logger.info("-----------------accept AddTOStock ACTION CALLED-----------------");
		ActionErrors errors = new ActionErrors();
		String strMessage ="";
		DPReverseChurnCollectionBean churnBean = (DPReverseChurnCollectionBean) form;
		try 
		{	
			churnBean.setError_flag("false");
			HttpSession session = request.getSession();
			
			UserSessionContext sessionContext = (UserSessionContext) session.getAttribute(Constants.AUTHENTICATED_USER);
			long loginUserId = sessionContext.getId();
			String strRemarks = churnBean.getRemarks();
			logger.info(" Remarks " + strRemarks);
			String[] arrCheckedChurn = request.getParameterValues("strCheckedChurn");
			String	strSerialNo="";

			DPReverseChurnCollectionService churnService = new DPReverseChurnCollectionServiceImpl();
			
			ArrayList<DPReverseChurnCollectionDTO> churnList = churnService.reportChurnAddToStock(arrCheckedChurn, loginUserId, strRemarks);
			
//			for (int i=0; i<arrCheckedChurn.length; i++)
//			{
//				if(i==0)
//					strSerialNo  = arrCheckedChurn[i];
//				else
//					strSerialNo  = strSerialNo + ", " + arrCheckedChurn[i];
//			}
			
			churnBean.setPoList(churnList);	
			
            churnBean.setStatusId("0");

			// Display Message
//    		if(strSerialNo!=null)
//    			strMessage = strSerialNo + " serial number added in your serialized stock.";
            
            strMessage = "STB successfully added into your serialized stock";
            
    		churnBean.setMessage(strMessage);
    		churnBean.setRemarks("");
		}		
		catch(Exception e)		
		{
			churnBean.setMessage("Internal Error Occured");
			logger.info("EXCEPTION OCCURED ::  "+e.getMessage());
			errors.add("errors",new ActionError(e.getMessage()));
			saveErrors(request, errors);
			return mapping.findForward(INIT_SUCCESS);
		}
		return mapping.findForward(INIT_SUCCESS);
	}
	
	public ActionForward viewPODetails(ActionMapping mapping,ActionForm form,HttpServletRequest request,HttpServletResponse response)
			throws Exception 
	{
		logger.info("viewPODetails method called");
		HttpSession session = request.getSession();
		DPReverseChurnCollectionBean churnBean = (DPReverseChurnCollectionBean)form;
		ActionErrors errors = new ActionErrors();
		try
		{
			DPReverseChurnCollectionService dppos = new DPReverseChurnCollectionServiceImpl();
			
			UserSessionContext userSessionContext = (UserSessionContext) session.getAttribute(Constants.AUTHENTICATED_USER);
			HBOUser hboUser = new HBOUser(userSessionContext);
			ArrayList porList = new ArrayList();
					
			porList = ((ArrayList)dppos.viewPendingChurnSTBList(hboUser.getId()));
			churnBean.setPoList(porList);
			request.setAttribute("porList", porList);
		} 
		catch (Exception e) 
		{
			churnBean.setMessage("Internal Error Occured");
			logger.info("EXCEPTION OCCURED ::  "+e.getMessage());
			errors.add("errors",new ActionError(e.getMessage()));
			saveErrors(request, errors);
			return mapping.findForward(INIT_SUCCESS);
		}
			
			return mapping.findForward("INIT_SUCCESS");
	}//end viewPODetails
	

	public ActionForward showErrorFile(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
			throws Exception 
	{
		DPReverseChurnCollectionBean DPReverseChurnCollectionBean = (DPReverseChurnCollectionBean) form;
		return mapping.findForward("errorFileChurn");
	}
	
	public ActionForward checkCPEInventory(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)	throws Exception 
	{ 	 
		logger.info("************checkCPEInventory******************");
		String chackInv ="";
		String stbNos = request.getParameter("stbNos");
		logger.info("stbNos::"+stbNos);
		
		try
		{ 
			DPReverseChurnCollectionService dppos = new DPReverseChurnCollectionServiceImpl();
			chackInv = dppos.checkCPEInventory(stbNos);
			response.setContentType("text/html");
			response.setHeader("Cache-Control", "No-Cache");
			PrintWriter out = response.getWriter();
			out.write(""+chackInv);
			out.flush();
		}
		catch(Exception ex)
		{
			logger.info(ex);
		}
		return null;
	}
}
