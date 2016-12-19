
package com.ibm.hbo.actions;

import java.io.File;
import java.io.PrintWriter;
import java.io.RandomAccessFile;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.beanutils.BeanUtils;
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
import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.dom4j.io.OutputFormat;
import org.dom4j.io.XMLWriter;

import com.ibm.hbo.common.HBOConstants;
import com.ibm.hbo.common.HBOUser;
import com.ibm.hbo.dto.HBOMonthDTO;
import com.ibm.hbo.dto.HBOProductDTO;
import com.ibm.hbo.dto.HBOProjectionBulkUploadDTO;
import com.ibm.hbo.dto.HBOStockDTO;
import com.ibm.hbo.dto.HBOUserBean;
import com.ibm.hbo.dto.HBOWarehouseMasterDTO;
import com.ibm.hbo.exception.HBOException;
import com.ibm.hbo.forms.HBOProjectionBulkUploadFormBean;
import com.ibm.hbo.forms.HBOProjectionFormBean;
import com.ibm.hbo.services.HBOMasterService;
import com.ibm.hbo.services.HBOProjectionBulkUploadService;
import com.ibm.hbo.services.HBOStockService;
import com.ibm.hbo.services.impl.HBOMasterServiceImpl;
import com.ibm.hbo.services.impl.HBOProjectionBulkUploadServiceImpl;
import com.ibm.hbo.services.impl.HBOStockServiceImpl;
import com.ibm.utilities.PropertyReader;
import com.ibm.virtualization.framework.bean.ChannelType;
import com.ibm.virtualization.recharge.authorization.AuthorizationFactory;
import com.ibm.virtualization.recharge.common.Constants;
import com.ibm.virtualization.recharge.dto.UserSessionContext;
import com.ibm.virtualization.recharge.service.AuthorizationInterface;
/**
 * @author Aditya
 *
 * To change the template for this generated type comment go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
public class HBOProjectionAction extends DispatchAction {
	
	/*
		 * Logger for the class.
		 */
		private static final Logger logger;

		static {
			logger = Logger.getLogger(HBOProjectionAction.class);
		}
	public ActionForward getValues(ActionMapping mapping,ActionForm form, HttpServletRequest request,HttpServletResponse response) throws Exception
	{	
		HttpSession session = request.getSession();
		ActionForward forward = new ActionForward(); // return value
		ArrayList arrMasterList = new ArrayList(); 
		UserSessionContext userSessionContext = (UserSessionContext) session.getAttribute(Constants.AUTHENTICATED_USER);
		HBOUser hboUser = new HBOUser(userSessionContext);
		String userId = hboUser.getUserId();
		String roleId=hboUser.getActorId();
		HBOProjectionFormBean projectionFormBean = (HBOProjectionFormBean) form;
		HBOMasterService masterService = new HBOMasterServiceImpl();
		//String condition = "";
		Date dt = new Date();
		int mth=dt.getMonth();
		Calendar calendar = new GregorianCalendar() ;
		String [] month={"Jan","Feb","Mar","Apr","May","June","July","Aug","Sept","Oct","Nov","Dec"};
		Map list = new HashMap();
		ArrayList arrMonth = new ArrayList();
		HBOMonthDTO monthDTO = null;
		for(int i=0;i<=2;i++)
		{
			if(mth>11)
			{
				mth=0;
			}
			monthDTO = new HBOMonthDTO();
			monthDTO.setMonthName(month[mth]);
			monthDTO.setMonthId(mth+1);
			arrMonth.add(monthDTO);
			mth++;
			logger.info("----------------"+monthDTO.getMonthId());
					
		}
		
		//GET YEAR 
		ArrayList arrYear = new ArrayList();
		
		if(dt.getMonth()==11||dt.getMonth()==10)
		{
			for(int i=0;i<2;i++){
			monthDTO =new HBOMonthDTO();
			//monthDTO.setCurrYear(calendar.get(Calendar.YEAR));
			monthDTO.setNextYear((calendar.get(Calendar.YEAR))+i);
			arrYear.add(monthDTO);
			}
		}
		else{			
			monthDTO =new HBOMonthDTO();
			monthDTO.setNextYear((calendar.get(Calendar.YEAR)));
			arrYear.add(monthDTO);
			
		}
		logger.info("months are: "+list);
		// Reset the Screen if navigated from Projection Screen
			projectionFormBean.reset();
			ArrayList arrProductList = null;
			ArrayList arrCircleList = null;
			ArrayList arrBusinessList = null;
			AuthorizationInterface authorizationInterface = AuthorizationFactory.getAuthorizerImpl();
			ArrayList roleList = authorizationInterface.getUserCredentials(userSessionContext.getGroupId(), ChannelType.WEB);
			if(projectionFormBean.getArrCircle() == null ){
				if(roleList.contains(HBOConstants.ROLE_MOBILITYADMIN)){
				arrCircleList = masterService.getMasterList(userId,HBOConstants.CIRCLE,"");
				projectionFormBean.setArrCircle(arrCircleList);
				projectionFormBean.setRole(HBOConstants.ROLE_MOBILITY);
			}
				else if(roleList.contains(HBOConstants.ROLE_CIRCLEADMIN)){
					arrCircleList = masterService.getMasterList(userId,HBOConstants.CIRCLE,HBOConstants.CIRCLE_OWN);
					projectionFormBean.setArrCircle(arrCircleList);
					projectionFormBean.setRole(HBOConstants.ROLE_CIRCLE);
				}
			}
			if(mapping.getPath().equalsIgnoreCase(HBOConstants.INIT_PROJECTION_ACTION)){
				if(projectionFormBean.getArrProducts() == null ) {
					arrProductList = masterService.getMasterList(userId,HBOConstants.PRODUCT,"");
					projectionFormBean.setArrProducts(arrProductList);
				}
				if(projectionFormBean.getArrBusinessCat() == null ) {
					arrBusinessList = masterService.getMasterList(userId,HBOConstants.BUSINESS_CATEGORY,"");
					projectionFormBean.setArrBusinessCat(arrBusinessList);
				}
			}
			if(projectionFormBean.getArrMonth() == null ) {
				projectionFormBean.setArrMonth(arrMonth);
			}
			if(projectionFormBean.getArrYear() == null ) {
				projectionFormBean.setArrYear(arrYear);
			}
		//}		
		saveToken(request);	
		forward = mapping.findForward(HBOConstants.SUCCESS_MESSAGE);
		return (forward);
	}

	public ActionForward getProjectedQuantity(ActionMapping mapping,ActionForm form, HttpServletRequest request,HttpServletResponse response) throws Exception
	{
		Document document = DocumentHelper.createDocument();
		Element root = document.addElement("options");
		Element optionElement;
 		String pid=request.getParameter("cond1");
		String cid=request.getParameter("cond2");
		String mth_yr=request.getParameter("cond3");
		ActionForward forward = new ActionForward();
		HBOMasterService masterService = new HBOMasterServiceImpl();
		int projectedQty = 0;
		//ArrayList arrGetValue=new ArrayList();
		ArrayList params = new ArrayList();
		params.add(pid);
		params.add(cid);
		params.add(mth_yr);
		projectedQty=masterService.getProjectionQty(params);
		response.setContentType("text/html");
		response.setHeader("Cache-Control", "No-Cache");
		PrintWriter out = response.getWriter();
		out.write(projectedQty+"");
		out.flush();	
		return null;
	}
	
	public ActionForward getChangeProducts(ActionMapping mapping,ActionForm form, HttpServletRequest request,HttpServletResponse response) throws Exception
	{
			HttpSession session = request.getSession();
			Document document = DocumentHelper.createDocument();
			Element root = document.addElement("options");
			Element optionElement;
			String userid="";
			String condition="";
			String id="";
			String cond="";
			id=request.getParameter("Id");
			cond=request.getParameter("cond");
			
			int x = Integer.parseInt(id);
			ArrayList param = new ArrayList();
			param.add(id);
			param.add(cond);
			param.add(request.getParameter("cond2"));
			HBOMasterService masterService = new HBOMasterServiceImpl();
			ArrayList arrGetValue=new ArrayList();
			arrGetValue=masterService.getChange(param);
			HBOProductDTO getChangeDTO = new HBOProductDTO(); 
			
			for (int counter = 0; counter < arrGetValue.size(); counter++) {
				
				optionElement = root.addElement("option");
				getChangeDTO = (HBOProductDTO)arrGetValue.get(counter);
				if (getChangeDTO != null) {
					optionElement.addAttribute("value",getChangeDTO.getProductId());
					optionElement.addAttribute("text",getChangeDTO.getProductcode());
				} else {
					optionElement.addAttribute("value", "None");
					optionElement.addAttribute("text", "None");
				}				
			}
			
			response.setContentType("text/xml");
			response.setHeader("Cache-Control", "No-Cache");
			PrintWriter out = response.getWriter();
			OutputFormat outputFormat = OutputFormat.createCompactFormat();
			XMLWriter writer = new XMLWriter(out);
	
			writer.write(document);
			writer.flush();
			out.flush();
			
			return null;
	}
			/**
			 * @param mapping
			 * @param form
			 * @param request
			 * @param response
			 * @return
			 * @throws Exception
			 */
			public ActionForward insertProjection(ActionMapping mapping,ActionForm form, HttpServletRequest request,HttpServletResponse response) throws Exception
			{	
				ActionMessages messages = new ActionMessages();
				HttpSession session = request.getSession();
				ActionErrors errors = new ActionErrors();
				ActionForward forward = new ActionForward();
				HBOProjectionFormBean projectionFormBean = (HBOProjectionFormBean) form; 
				HBOStockDTO stockDTO = new HBOStockDTO();
				HBOStockService stockService = new HBOStockServiceImpl();
				UserSessionContext userSessionContext = (UserSessionContext) session.getAttribute(Constants.AUTHENTICATED_USER);
				HBOUser hboUser = new HBOUser(userSessionContext);
				BeanUtils.copyProperties(stockDTO,projectionFormBean);
				stockDTO.setId(hboUser.getId());
				String message="";
				//CALLING METHOD
				try{
					if (isTokenValid(request,true)){
						message = stockService.projectQty(stockDTO);
					}
					if (message.equalsIgnoreCase(HBOConstants.SUCCESS_MESSAGE)){
						projectionFormBean.reset();	
						messages.add(HBOConstants.INSERT_SUCCESS_MESSAGE,new ActionMessage(HBOConstants.insertSuccessKey));
						saveMessages(request, messages);
					}
					else if (message.equalsIgnoreCase(HBOConstants.FAILURE))
					{
						errors.add(HBOConstants.ERROR_OCCURED_MESSAGE,new ActionError(HBOConstants.errorOccuredKey));
						saveErrors(request, errors);					
					}
				}catch(Exception e){
					errors.add(HBOConstants.ERROR_OCCURED_MESSAGE,new ActionError(HBOConstants.errorOccuredKey));
					saveErrors(request, (ActionErrors) errors);	
					e.printStackTrace();
					logger.error("Exception occured");
				}
				return mapping.findForward(message);
			}
			public ActionForward insertBulkProjection(ActionMapping mapping,ActionForm form, HttpServletRequest request,HttpServletResponse response) throws Exception
			{
				ActionMessages messages = new ActionMessages();
				HttpSession session = request.getSession();
				ActionErrors errors = new ActionErrors();
				ActionForward forward = new ActionForward();
				HBOProjectionFormBean projectionFormBean = (HBOProjectionFormBean) form;
				HBOProjectionBulkUploadDTO upload = new HBOProjectionBulkUploadDTO(); 
				HBOStockService uploadService = new HBOStockServiceImpl();
				String message = "";
				UserSessionContext userSessionContext = (UserSessionContext) session.getAttribute(Constants.AUTHENTICATED_USER);
				HBOUser hboUser = new HBOUser(userSessionContext);
				Long userid = hboUser.getId();
				try{
					upLoadFile(projectionFormBean,hboUser,request);
					BeanUtils.copyProperties(upload,projectionFormBean);
					upload.setUploadedBy(userid);
					message = uploadService.projectBulkQty(upload);
					logger.info("Message after Project Qty:"+message);
					if (message.equalsIgnoreCase(HBOConstants.SUCCESS_MESSAGE)){
						projectionFormBean.reset();
						messages.add(HBOConstants.UPLOAD_SUCCESS,new ActionMessage(HBOConstants.uploadSuccessKey));
						saveMessages(request, messages);
					}
					else if (message.equalsIgnoreCase(HBOConstants.INSERT_FAILED)){
						projectionFormBean.reset();
						messages.add(HBOConstants.INSERT_FAILED_MESSAGE,new ActionMessage(HBOConstants.insertFailedKey));
						saveMessages(request, messages);
						message = HBOConstants.SUCCESS_MESSAGE;
					}
					else if (message.equalsIgnoreCase(HBOConstants.FAILURE))
					{
						errors.add(HBOConstants.ERROR_OCCURED_MESSAGE,new ActionError(HBOConstants.errorOccuredKey));
						saveErrors(request, (ActionErrors) errors);			
					}
				}catch(Exception e){
					e.printStackTrace();
					errors.add(HBOConstants.ERROR_OCCURED_MESSAGE,new ActionError(HBOConstants.errorOccuredKey));
					saveErrors(request, (ActionErrors) errors);
				}	
				return mapping.findForward(message);
			}
			
		public void upLoadFile(
				HBOProjectionFormBean projectionFormBean,
				HBOUser hboUser,
				HttpServletRequest request) {
			try {
				SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
				SimpleDateFormat stf = new SimpleDateFormat("HHmmss");
				java.util.Date nowDate = new java.util.Date();
				FormFile myFile = projectionFormBean.getThefile();
				String filePath = null;
				String today = sdf.format(nowDate);
				String time = stf.format(nowDate);
				String filepath =
					projectionFormBean.getCircle()
						+ "-"
						+ today
						+ "-"
						+ time
						+ "-"
						+ myFile.getFileName();
				String filename =
					projectionFormBean.getCircle() + today + time + myFile.getFileName();
				filePath =
					(String) getResources(request).getMessage(HBOConstants.UPLOAD_PROJECTION_PATH);
				File file = new File(filePath + filename);
				byte[] fileData = myFile.getFileData();
				RandomAccessFile raf = new RandomAccessFile(file, "rw");
				raf.write(fileData);
				raf.close();
				projectionFormBean.setFileName(myFile.getFileName());
				projectionFormBean.setFilePath(filePath+filename);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}		
}