package com.ibm.dp.actions;
/**
 * Author  : Ram Pratap Singh
 * */
 
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.servlet.ServletOutputStream;
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

import com.ibm.dp.beans.ViewStockEligibilityBean;
import com.ibm.dp.beans.WHdistmappbulkBean;
import com.ibm.dp.dto.DPCreateAccountDto;
import com.ibm.dp.dto.ViewStockEligibilityDTO;
import com.ibm.dp.dto.WHdistmappbulkDto;
import com.ibm.dp.service.UploadDistStockEligibilityService;
import com.ibm.dp.service.ViewStockEligibilityService;
import com.ibm.dp.service.WHdistmappbulkService;
import com.ibm.dp.service.impl.UploadDistStockEligibilityServiceImpl;
import com.ibm.dp.service.impl.ViewStockEligibilityServiceImpl;
import com.ibm.dp.service.impl.WHdistmappbulkServiceImpl;
import com.ibm.virtualization.framework.bean.ChannelType;
import com.ibm.virtualization.recharge.authorization.AuthorizationConstants;
import com.ibm.virtualization.recharge.authorization.AuthorizationServiceImpl;
import com.ibm.virtualization.recharge.common.Constants;
import com.ibm.virtualization.recharge.common.ResourceReader;
import com.ibm.virtualization.recharge.dto.UserSessionContext;
import com.ibm.virtualization.recharge.exception.VirtualizationServiceException;
import com.ibm.virtualization.recharge.service.AuthorizationInterface;


public class UploadDistStockEligibilityAction extends DispatchAction{ 
	private static Logger logger = Logger.getLogger(UploadDistStockEligibilityAction.class
			.getName());

	private static final String INIT_SUCCESS = "init";
	private static final String INIT_UPLOAD_SUCCESS = "uploadSuccess";
	private static final String SUCCESS_EXCEL = "successExcel";

	public ActionForward init(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		System.out.println("----------UploadDistStockEligibilityAction-------init ACTION CALLED-----------------");
		ActionErrors errors = new ActionErrors();
		
		try {
			/* Logged in user information from session */
			// Getting login ID from session
			HttpSession session = request.getSession();
			UserSessionContext sessionContext = (UserSessionContext) session
					.getAttribute(Constants.AUTHENTICATED_USER);
			errors.add("errors", new ActionError("hello "));
			saveErrors(request, errors);
			ViewStockEligibilityBean eligibilityBean = (ViewStockEligibilityBean)form;
		UploadDistStockEligibilityService udses= new UploadDistStockEligibilityServiceImpl();
		eligibilityBean.setRegionList(udses.getRegionList());
		} catch (Exception e) {
			logger.info("EXCEPTION OCCURED ::  " + e.getMessage());
			errors.add("errors", new ActionError(e.getMessage()));
			e.printStackTrace();
		}
		return mapping.findForward(INIT_SUCCESS);
	}
	
	public ActionForward getCirclesOfZone(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		System.out.println("----------UploadDistStockEligibilityAction-------getCirclesOfZone ACTION CALLED-----------------");
		ActionErrors errors = new ActionErrors();
		Document document = DocumentHelper.createDocument();
		HttpSession session = request.getSession();
		Element root = document.addElement("options");
		Element optionElement;
		List<ViewStockEligibilityDTO> circleList = null;
		ViewStockEligibilityDTO sDto = null;
		try {
			/* Logged in user information from session */
			// Getting login ID from session
			UserSessionContext sessionContext = (UserSessionContext) session
					.getAttribute(Constants.AUTHENTICATED_USER);
			errors.add("errors", new ActionError("hello "));
			saveErrors(request, errors);
			UploadDistStockEligibilityService udses= new UploadDistStockEligibilityServiceImpl();
			int regionId = Integer.parseInt((String)request.getParameter("regionId"));
			circleList = udses.getCircleList(regionId);
			for (int counter = 0; counter < circleList.size(); counter++) {
				sDto = (ViewStockEligibilityDTO) circleList.get(counter);
				optionElement = root.addElement("option");
				optionElement.addAttribute("value", String.valueOf(sDto.getCircleId()));
				optionElement.addAttribute("text", sDto.getCircleName());
			}
			response.setContentType("text/xml");
			response.setHeader("Cache-Control", "No-Cache");
			PrintWriter out = response.getWriter();
			OutputFormat outputFormat = OutputFormat.createCompactFormat();
			XMLWriter writer = new XMLWriter(out);
			writer.write(document);
			writer.flush();
			out.flush();
		}
		catch(Exception ex)
		{
			logger.info("EXCEPTION OCCURED ::  " + ex.getMessage());
			errors.add("errors", new ActionError(ex.getMessage()));
			ex.printStackTrace();
		}
		return null;
	}
	
	
	public ActionForward downloadTemplate(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)	throws Exception 
	{
		logger.info("Inside downloadTemplate function of UploadDistStockEligibilityAction  :::::");
		response.setHeader( "Pragma", "public" );
		response.setContentType("application/vnd.ms-excel");
		response.setHeader("Cache-Control", "must-revalidate");
		String fileName = null;
		int templateId =  Integer.parseInt((String) request.getParameter("templateId"));
		if(templateId == 1) fileName= "FormateSwapSF";
		else if(templateId == 2) fileName= "FormateSwapSSD";
		else if(templateId == 3) fileName= "FormateCommercialSF";
		else if(templateId == 4) fileName= "FormateCommercialSSD";
		response.setHeader("Content-Disposition", "attachment;filename="+fileName+".xls");			
		InputStream in = null;
		ServletOutputStream out = null;
		try {
			String path=ResourceReader.getCoreResourceBundleValue(com.ibm.virtualization.recharge.common.Constants.DOWNLOAD_FILE);
			String fpath=path  + fileName+".xls";
			in = new FileInputStream(fpath);
			out = response.getOutputStream();
			byte[] outputByte = new byte[4096];
			while (in.read(outputByte, 0, 4096) != -1) {
			out.write(outputByte, 0, 4096);
			}			
		} catch (IOException e) {
			throw e;
		} finally {
			in.close();
			out.flush();
			out.close();
		}	
		return null;
	}
	
	
	public ActionForward uploadExcel(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		logger.info("-----------UploadDistStockEligibilityACtion------uploadExcel CALLED-----------------");
		ActionErrors errors = new ActionErrors();
		ViewStockEligibilityBean eligibilityBean = null;
		ActionMessages messages = new ActionMessages();
		int productType;
		int distType;
		long dthadminid=0;
		int regionId;
		try 
		{	
			HttpSession session = request.getSession();
//Neetika for passing dthadmin id for createdby field
			UserSessionContext sessionContext = (UserSessionContext) session
			.getAttribute(Constants.AUTHENTICATED_USER);
			dthadminid=sessionContext.getId();
			eligibilityBean = (ViewStockEligibilityBean) form;
			FormFile file = eligibilityBean.getUploadedFile();
			productType = eligibilityBean.getProductTypeId();
			distType =  eligibilityBean.getDistTypeId();
			regionId=Integer.parseInt((request.getParameter("regionId")));
			logger.info("productType :"+productType +"  distType :"+distType+"regionId"+regionId);
			InputStream myxls = file.getInputStream();
			UploadDistStockEligibilityService bulkupload = new UploadDistStockEligibilityServiceImpl();
			logger.info("**"+file.getFileName());
			String arr= (file.getFileName().toString()).substring(file.getFileName().toString().lastIndexOf(".")+1,file.getFileName().toString().length());
			String special=ResourceReader.getCoreResourceBundleValue("SPECIAL_CHARS");
				boolean found=false;
				boolean found2=false;
				if(!arr.equalsIgnoreCase("xls"))
				{
					eligibilityBean.setStrmsg("Only XLS File is allowed here");
				found=true;
				}
				 if(found==false)
				{
				for (int i=0; i<special.length(); i++) 
				{
				   if ((file.getFileName().toString()).indexOf(special.charAt(i)) > 0) {
					   eligibilityBean.setStrmsg("Special Characters are not allowed in File Name");
				      found2=true;
				      break;
				   }
				}
				if ((file.getFileName().toString()).indexOf("..")>-1)
				{
					 found2=true;	
					 eligibilityBean.setStrmsg("2 consecutive dots are not allowed in File Name");
				}
				}
				 UploadDistStockEligibilityService udses= new UploadDistStockEligibilityServiceImpl();
				eligibilityBean.setRegionList(udses.getRegionList()); 
				if(found2==false && found==false)
				{
				String msg = bulkupload.uploadExcel(myxls,productType,distType,dthadminid,regionId);
				//logger.info("msg in action class "+msg);
				boolean checkValidate = true;
				if(msg!=null || msg!="")
				{
					eligibilityBean.setStrmsg(msg);
					checkValidate = false;
				}
				
				if(checkValidate)  // Write here code for uploading the excel file
				{
					eligibilityBean.setError_flag("false");
					
					return mapping.findForward(INIT_UPLOAD_SUCCESS);
				}
				
				}
		}
		catch(Exception e)
		{
			
			logger.info("EXCEPTION OCCURED ::  "+e.getMessage());
			messages.add("WRONG_FILE_UPLOAD", new ActionMessage("uploadelig.error.file",e.getMessage()));
			saveMessages(request, messages);
			return mapping.findForward(INIT_UPLOAD_SUCCESS);
		}
		return mapping.findForward(INIT_UPLOAD_SUCCESS);
	}
	
}
