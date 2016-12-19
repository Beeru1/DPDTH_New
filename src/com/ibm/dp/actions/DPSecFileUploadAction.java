package com.ibm.dp.actions;

import java.util.ArrayList;

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

import com.ibm.dp.beans.DPPurchaseOrderFormBean;
import com.ibm.dp.beans.DPSecFileUploadBean;
import com.ibm.dp.common.Constants;
import com.ibm.dp.service.DPPurchaseOrderService;
import com.ibm.dp.service.DPSecFileUploadService;
import com.ibm.dp.service.impl.DPPurchaseOrderServiceImpl;
import com.ibm.dp.service.impl.DPSecFileUploadServiceImpl;
import com.ibm.hbo.common.HBOConstants;
import com.ibm.hbo.common.HBOUser;
import com.ibm.virtualization.recharge.dto.UserSessionContext;
import com.ibm.virtualization.recharge.utils.PropertyReader;
/**
 * DPPurchaseOrderAction class is gateway for other class and interface for Purchase Order Requisition related data
 *
 * @author Rohit Kunder
 */
public class DPSecFileUploadAction extends DispatchAction {
	/* Logger for this class. */
	private static Logger logger = Logger.getLogger(DPSecFileUploadAction.class.getName());
	
	private static final String INIT_SUCCESS = "initSuccess";
	/**
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return  success or failure
	 * @throws Exception
	 * @desc Method forward the Secondary File Upload jsp.
	 */
	public ActionForward initSecFileUpload(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		
			logger.info("***: inside initSecFileUpload :***");
			ActionErrors errors = new ActionErrors();
			HttpSession session = request.getSession();
			try {
				UserSessionContext userSessionContext = (UserSessionContext) session.getAttribute(Constants.AUTHENTICATED_USER);
				session.getAttribute("USER_INFO");
				HBOUser hboUser = new HBOUser(userSessionContext);
				DPSecFileUploadBean dpsfUB = (DPSecFileUploadBean)form;
				saveToken(request);
			} catch (RuntimeException e) {
				e.printStackTrace();
				logger.info("EXCEPTION OCCURED ::  "+e.getMessage());
				errors.add("errors",new ActionError(e.getMessage()));
				saveErrors(request, errors);
				return mapping.findForward(INIT_SUCCESS);
			}
			return mapping.findForward(INIT_SUCCESS);
			
	}// end initFileUPload
	
		
	/**
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return success or failure
	 * @throws Exception
	 * @desc This method upload secondary data file into the specific location 
	 */
	public ActionForward SecFileUPload(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		
		logger.info("***: inside SecFileUPload :***");
		
				HttpSession session = request.getSession();
				ActionErrors errors = new ActionErrors();
				ActionMessages msg = new ActionMessages();
				PropertyReader
				.setBundleName("com.ibm.dp.resources.DPResources");	
				
				UserSessionContext userSessionContext = (UserSessionContext) session
				.getAttribute(Constants.AUTHENTICATED_USER);
				HBOUser hboUser = new HBOUser(userSessionContext);
				
				//	local variable 
				String message= null;
				String filePath = "";
				int loginID = (hboUser.getId()).intValue();
				int circleID=Integer.parseInt(hboUser.getCircleId());
				
				DPSecFileUploadBean dpsfUB = (DPSecFileUploadBean)form;
				DPSecFileUploadService dpsfuls = new DPSecFileUploadServiceImpl();
			
				  // Process the FormFile
			        FormFile SecDataFile = dpsfUB.getThefile();
			        String fileName    = SecDataFile.getFileName();
			        byte[] fileData    = SecDataFile.getFileData();

					   // String changedFileName = secFileName.toString();
				   			    
			    if (SecDataFile != null || fileData.length > 0) {
					
			    	filePath=PropertyReader.getString(Constants.FILE_PATH_TO_SAVE);
			    	
					try {
						
						message=dpsfuls.SecFileUploadSave(fileData, filePath, fileName, loginID, circleID);
						msg.add("secUPload",new ActionMessage("DP.SecFileUPload"));
						saveMessages(request, msg);
						//Output ="succes";
					} catch (Exception e) {

						e.printStackTrace();
						logger.info("EXCEPTION OCCURED ::  "+e.getMessage());
						errors.add("errors",new ActionError(e.getMessage()));
						saveErrors(request, errors);
						
					
						errors.add(HBOConstants.ERROR_OCCURED_MESSAGE,new ActionError("error.occured"));
						saveErrors(request, (ActionErrors) errors);
						return mapping.findForward(message);
					}
							
			}
			//}//end SecFileUPload
				 return mapping.findForward(message);
	}//Method End
}//END
