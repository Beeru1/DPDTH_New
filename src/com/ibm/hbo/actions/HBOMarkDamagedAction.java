/*
 * Created on Nov 30, 2007
 *
 * To change the template for this generated file go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
package com.ibm.hbo.actions;

import java.io.PrintWriter;
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
import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.dom4j.io.OutputFormat;
import org.dom4j.io.XMLWriter;
import org.apache.commons.beanutils.BeanUtils;

import com.ibm.hbo.common.HBOConstants;
import com.ibm.hbo.common.HBOLibrary;
import com.ibm.hbo.common.HBOUser;
import com.ibm.hbo.dao.impl.HBOBulkUploadDAOImpl;
import com.ibm.hbo.dto.HBOProductDTO;
import com.ibm.hbo.dto.HBOStockDTO;
import com.ibm.hbo.dto.HBOUserBean;
import com.ibm.hbo.forms.HBOFileUploadFormBean;
import com.ibm.hbo.forms.HBOMarkDamagedFormBean;
import com.ibm.hbo.services.HBOMasterService;
import com.ibm.hbo.services.HBOStockService;
import com.ibm.hbo.services.impl.HBOMasterServiceImpl;
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
public class HBOMarkDamagedAction extends DispatchAction{
	/*
	 * Logger for the class.
	 */
	private static final Logger logger; 

	static {
		logger = Logger.getLogger(HBOMarkDamagedAction.class);
		
	}
	public ActionForward getValues(ActionMapping mapping,ActionForm form, HttpServletRequest request,HttpServletResponse response) throws Exception
	{		
		HttpSession session = request.getSession();
		ActionForward forward = new ActionForward(); // return value
		
		HBOMarkDamagedFormBean markDamagedFormBean = (HBOMarkDamagedFormBean) form;
		String invoiceNo = "";
		if(mapping.getPath().equalsIgnoreCase("/initMarkDamaged")){
			invoiceNo=request.getParameter("invoice");
			markDamagedFormBean.setInvoice(invoiceNo);
		}
		else{
			invoiceNo = markDamagedFormBean.getInvoice();
		}
		HBOStockService stockService = new HBOStockServiceImpl();
		HBOUserBean hboUserBean = (HBOUserBean) session.getAttribute("USER_INFO");
		String roleId = hboUserBean.getActorId();
		session.setAttribute("Role",roleId);
		//ArrayList arrImeiList = stockService.getImeiLists(roleId,invoiceNo,"imeiList");
		//markDamagedFormBean.setArrImeino(arrImeiList);
		//request.setAttribute("imeiList",arrImeiList);//added by Siddhartha
		//logger.info("arrImeiList.size().........."+arrImeiList.size()+markDamagedFormBean.getArrImeino().toString());
		//logger.info("arrlist--------"+markDamagedFormBean.getArrImeino().size());
		
		forward = mapping.findForward("success");
		return (forward);
	}
	public ActionForward getChange(ActionMapping mapping,ActionForm form, HttpServletRequest request,HttpServletResponse response) throws Exception
	{
		Document document = DocumentHelper.createDocument();
		Element root = document.addElement("options");
		Element optionElement;
		HttpSession session = request.getSession();
		UserSessionContext userSessionContext = (UserSessionContext) session.getAttribute(Constants.AUTHENTICATED_USER);
		String id="";
		String cond="";
		id=request.getParameter("Id");
		id=id.substring(0,id.indexOf("@"));
		
		cond=request.getParameter("cond");
		int x = Integer.parseInt(id);
		ArrayList param = new ArrayList();
		param.add(id);
		param.add(cond);
		param.add(userSessionContext.getCircleId());
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
	
	public ActionForward setNewIMEI(ActionMapping mapping,ActionForm form, HttpServletRequest request,HttpServletResponse response) throws Exception
	{		
		HttpSession session = request.getSession();
		ActionForward forward = new ActionForward();
		String imeiNo = request.getParameter(HBOConstants.IMEI_NO);
		String simNo=request.getParameter(HBOConstants.SIM_NO);
		HBOStockService stockService = new HBOStockServiceImpl();
		UserSessionContext userSessionContext = (UserSessionContext) session.getAttribute(Constants.AUTHENTICATED_USER);
		HBOUser hboUser = new HBOUser(userSessionContext);
		AuthorizationInterface authorizationInterface = AuthorizationFactory.getAuthorizerImpl();
		hboUser.setRoleList(authorizationInterface.getUserCredentials(userSessionContext.getGroupId(), ChannelType.WEB));
		String newImeiNo = stockService.getNewImeiNo(imeiNo,simNo,hboUser);
		HBOMarkDamagedFormBean markDamagedFormBean = (HBOMarkDamagedFormBean) form;
		markDamagedFormBean.setNewImeiNo(newImeiNo);
		markDamagedFormBean.setImeiNo(imeiNo);
		markDamagedFormBean.setSimno(simNo);
		request.setAttribute("prodInfo",markDamagedFormBean);
		forward = mapping.findForward(HBOConstants.SUCCESS);
		return (forward);
	}
	
	public ActionForward updtDmgFlag(ActionMapping mapping,ActionForm form, HttpServletRequest request,HttpServletResponse response) throws Exception
	{		
		HttpSession session = request.getSession();
		ActionForward forward = new ActionForward();
		String imeiNo = request.getParameter(HBOConstants.REQ_PARAM_IMEI);
		String simNo=request.getParameter(HBOConstants.REQ_PARAM_SIM);
		HBOStockService stockService = new HBOStockServiceImpl();
		UserSessionContext userSessionContext = (UserSessionContext) session.getAttribute(Constants.AUTHENTICATED_USER);
		HBOUser hboUser = new HBOUser(userSessionContext);
		String batch_no = request.getParameter(HBOConstants.REQ_PARAM_BATCH);
		AuthorizationInterface authorizationInterface = AuthorizationFactory.getAuthorizerImpl();
		hboUser.setRoleList(authorizationInterface.getUserCredentials(userSessionContext.getGroupId(), ChannelType.WEB));
		stockService.updtDmgFlag(imeiNo,simNo,hboUser);
		if(mapping.getPath().equalsIgnoreCase(HBOConstants.INIT_MARKDAMAGED_ACTION)){
			request.setAttribute(HBOConstants.REQUEST_ATT_BATCH,batch_no);
			request.setAttribute(HBOConstants.REQUEST_ATT_CONDITION,"A");
			forward = mapping.findForward(HBOConstants.SUCCESS);
		}
		else{
			forward = mapping.findForward(HBOConstants.DAMAGE_SUCCESS);
		}
		//return getValues(mapping,form,request,response);
		
		return forward;
	}
	
	public ActionForward populate(ActionMapping mapping,ActionForm form, HttpServletRequest request,HttpServletResponse response) throws Exception
	{	
		HttpSession session = request.getSession();
		ArrayList arrBussList = null;
		ArrayList arrDamagedProdList = null;
		HBOMarkDamagedFormBean markDamageFormBean = (HBOMarkDamagedFormBean) form;
		UserSessionContext userSessionContext = (UserSessionContext) session.getAttribute(Constants.AUTHENTICATED_USER);
		HBOUser hboUser = new HBOUser(userSessionContext);
		AuthorizationInterface authorizationInterface = AuthorizationFactory.getAuthorizerImpl();
		ArrayList roleList = authorizationInterface.getUserCredentials(userSessionContext.getGroupId(), ChannelType.WEB);
		HBOMasterService masterService = new HBOMasterServiceImpl();
		String userId = hboUser.getUserId();
		long id = hboUser.getId();
		markDamageFormBean.reset();
		if(roleList.contains(HBOConstants.ROLE_DIST)){
			arrBussList = masterService.getMasterList(userId,HBOConstants.BUSINESS_CATEGORY_SERIALLY,"");
			arrDamagedProdList = masterService.getMasterList(userId+"@"+id,HBOConstants.DAMAGED_PROD_LIST,"");
			markDamageFormBean.setDamagedProdList(arrDamagedProdList);
		}	
		else
			arrBussList = masterService.getMasterList(userId,HBOConstants.BUSINESS_CATEGORY_EXT_NEW,"");
		markDamageFormBean.setCategoryList(arrBussList);
		saveToken(request);
		return mapping.findForward(HBOConstants.SUCCESS_MESSAGE);
	}
	
	public ActionForward markDirectDmg(ActionMapping mapping,ActionForm form, HttpServletRequest request,HttpServletResponse response) throws Exception
	{
		HttpSession session = request.getSession();
		ActionForward forward = new ActionForward();
		ActionMessages messages = null;
		HBOMarkDamagedFormBean markDamagedFormBean = (HBOMarkDamagedFormBean) form;
		UserSessionContext userSessionContext = (UserSessionContext) session.getAttribute(Constants.AUTHENTICATED_USER);
		HBOUser hboUser = new HBOUser(userSessionContext);
		String imeino=markDamagedFormBean.getImeiNo();
		String roleName = "";
		String productId=markDamagedFormBean.getProduct();
		if(!(productId.equalsIgnoreCase(null) || productId.equalsIgnoreCase(""))){
			session.setAttribute("PROD_ID", productId);
		}
		if((productId.equalsIgnoreCase(null) || productId.equalsIgnoreCase(""))){
			productId=(String)session.getAttribute("PROD_ID");
		}
		AuthorizationInterface authorizationInterface = AuthorizationFactory.getAuthorizerImpl();
		ArrayList roleList = authorizationInterface.getUserCredentials(userSessionContext.getGroupId(), ChannelType.WEB);
		if(roleList.contains(HBOConstants.ROLE_NATIONALDIST))
			roleName = HBOConstants.ROLE_NATIONALDIST;
		else if(roleList.contains(HBOConstants.ROLE_LOCALDIST))
			roleName = HBOConstants.ROLE_LOCALDIST;
		else if(roleList.contains(HBOConstants.ROLE_DIST))
			roleName = HBOConstants.ROLE_DIST;
		else
			roleName = HBOConstants.ROLE_SUPER;
			request.setAttribute(HBOConstants.ROLE, roleName);
		if(markDamagedFormBean.getCatType().equalsIgnoreCase(HBOConstants.CATEGORY_HANDSET)){
			markDamagedFormBean.setProductType(HBOConstants.CATEGORY_HANDSET);
			HBOStockService stockService = new HBOStockServiceImpl();
			String warehouseId = hboUser.getWarehouseID();
			ArrayList arrImeiList = stockService.getImeiLists(productId,warehouseId,imeino,HBOConstants.DIRECT_DAMAGE);
			if(arrImeiList.size() == 0){
				messages = new ActionMessages();
				messages.add(HBOConstants.NO_RECORD,new ActionMessage("no.record.found"));
				saveMessages(request, (ActionMessages) messages);
			}
			if(roleName.equalsIgnoreCase(HBOConstants.ROLE_DIST)){
				HBOMasterService masterService = new HBOMasterServiceImpl();
				markDamagedFormBean.setDamagedProdList(masterService.getMasterList(hboUser.getUserId()+"@"+hboUser.getId(),HBOConstants.DAMAGED_PROD_LIST,""));
			}	
			markDamagedFormBean.setArrImeino(arrImeiList);
		}			
		else{
			markDamagedFormBean.setProductType(HBOConstants.CATEGORY_INT);
			HBOStockService stockService = new HBOStockServiceImpl();
			HBOStockDTO stockDto = new HBOStockDTO();
			BeanUtils.copyProperties(stockDto, markDamagedFormBean);
			stockDto.setId(hboUser.getId());
			String update = stockService.markDamagedProduct(stockDto);
			if(update.equalsIgnoreCase(HBOConstants.PRODUCT_NOT_AVAILABLE)){
				messages = new ActionMessages();
				messages.add(HBOConstants.PRODUCT_NOT_AVAILABLE,new ActionMessage("product.not.available"));
				saveMessages(request, (ActionMessages) messages);
			}
			else if(update.equalsIgnoreCase(HBOConstants.ALREADY_DAMAGED)){
				messages = new ActionMessages();
				messages.add(HBOConstants.ALREADY_DAMAGED,new ActionMessage("already.damaged"));
				saveMessages(request, (ActionMessages) messages);
			}
			else if(update.equalsIgnoreCase(HBOConstants.FAILURE)){
				messages = new ActionMessages();
				messages.add(HBOConstants.FAILURE,new ActionMessage("error.occured"));
				saveMessages(request, (ActionMessages) messages);
			}
			else if(update.equalsIgnoreCase(HBOConstants.SUCCESS)){
				messages = new ActionMessages();
				messages.add(HBOConstants.UPDATE_SUCCESS,new ActionMessage("update.success"));
				saveMessages(request, (ActionMessages) messages);
			}
		}	
		//populate(mapping,form,request,response);
			forward = mapping.findForward(HBOConstants.SUCCESS_PATH);
			return (forward);
	}

	public ActionForward updtDmgDirect(ActionMapping mapping,ActionForm form, HttpServletRequest request,HttpServletResponse response) throws Exception
	{		
		HttpSession session = request.getSession();
		ActionMessages messages = new ActionMessages();
		String imeiNo = request.getParameter("imeiNo");
		//String simNo=request.getParameter("simnoList");
		String simNo="";
		HBOStockService stockService = new HBOStockServiceImpl();
		try{
		UserSessionContext userSessionContext = (UserSessionContext) session.getAttribute(Constants.AUTHENTICATED_USER);
		HBOUser hboUser = new HBOUser(userSessionContext);
		AuthorizationInterface authorizationInterface = AuthorizationFactory.getAuthorizerImpl();
		hboUser.setRoleList(authorizationInterface.getUserCredentials(userSessionContext.getGroupId(), ChannelType.WEB));
		stockService.updtDmgFlag(imeiNo,simNo,hboUser);
		messages.add(HBOConstants.UPDATE_SUCCESS,new ActionMessage("update.success"));
		saveMessages(request, (ActionMessages) messages);
		}catch(Exception e){
			e.printStackTrace();
			messages.add(HBOConstants.FAILURE,new ActionMessage("error.occured"));
			saveMessages(request, (ActionMessages) messages);
		}
		return markDirectDmg(mapping,form,request,response);
	}
	public ActionForward setNewImeiD(ActionMapping mapping,ActionForm form, HttpServletRequest request,HttpServletResponse response) throws Exception
	{	
		HttpSession session = request.getSession();
		UserSessionContext userSessionContext = (UserSessionContext) session.getAttribute(Constants.AUTHENTICATED_USER);
		HBOUser hboUser = new HBOUser(userSessionContext);
		AuthorizationInterface authorizationInterface = AuthorizationFactory.getAuthorizerImpl();
		ArrayList roleList = authorizationInterface.getUserCredentials(userSessionContext.getGroupId(), ChannelType.WEB);
		hboUser.setRoleList(roleList);
		ActionForward forward = new ActionForward(); // return value
		HBOMarkDamagedFormBean markDamagedFormBean = (HBOMarkDamagedFormBean) form;
		String imeiNo = request.getParameter(HBOConstants.IMEI_NO);
		String simNo=request.getParameter(HBOConstants.SIM_NO);
		HBOStockService stockService = new HBOStockServiceImpl();
		String newImeiNo = stockService.getNewImeiNo(imeiNo,simNo,hboUser);
		markDamagedFormBean.setNewImeiNo(newImeiNo);
		markDamagedFormBean.setImeiNo(imeiNo);
		markDamagedFormBean.setSimno(simNo);
		request.setAttribute("prodInfo",markDamagedFormBean);
	
		forward = mapping.findForward("success");
		return (forward);
	}

// HLR Deletion : by Anju
	
	public ActionForward loadPage(ActionMapping mapping,ActionForm form, HttpServletRequest request, HttpServletResponse response){
		ActionForward forward = new ActionForward();
		forward = mapping.findForward("loadpage");
		return forward;
	}
	public ActionForward damageUploadFile(ActionMapping mapping,ActionForm form, HttpServletRequest request,HttpServletResponse response) throws Exception
	{
		HttpSession session = request.getSession();
		HBOFileUploadFormBean fileUploadFormBean = (HBOFileUploadFormBean)form;
		UserSessionContext sessionContext = (UserSessionContext) session.getAttribute(Constants.AUTHENTICATED_USER);
		HBOUser obj=new HBOUser(sessionContext);	
		ActionErrors errors = new ActionErrors();
		FormFile myFile = (FormFile) fileUploadFormBean.getThefile();
		HBOLibrary hboLibrary = new HBOLibrary();
		String fileType = "Damaged"; // Mark as damage
		String FILEMAXSIZE=null;		
		FILEMAXSIZE = PropertyReader.getValue("PROD_FILE_MAX_SIZE");  // Max File Size define property File
		int FILESIZE = Integer.parseInt(FILEMAXSIZE);  
		String filePath = "";
		String fileFormat = "";
		String fileName = myFile.getFileName();
	//	int fileSize = myFile.getFileSize();
		byte[] fileData = myFile.getFileData();
		int fileId = 0;
		HBOBulkUploadDAOImpl bulkDao = new HBOBulkUploadDAOImpl();
		if (myFile != null && myFile.getFileSize() < FILESIZE) {
		filePath =(String) getResources(request).getMessage("UPLOAD_PATH_DAMAGE"); 
		String changedFileName = hboLibrary.changedFileName(fileName,obj.getWarehouseID()); //,fileId);
		fileId = bulkDao.insertBulkUploadData(Integer.parseInt(obj.getCircleId()),fileName,changedFileName,obj.getId().intValue(),filePath,fileType);	
		request.setAttribute("fileType1", fileType);
		request.setAttribute("ContentOfFile1",fileUploadFormBean.getContentOfFile());
		fileFormat =(String) getResources(request).getMessage("SIM_UPLOAD_FORMAT1"); //SIMNO,MSIDNNO
		
		hboLibrary.writeToFile(fileFormat,fileData,filePath,changedFileName);
		errors.add("uploadFile",new ActionError("uploadFile"));
		saveErrors(request, (ActionErrors) errors);
		}else{
			fileUploadFormBean.setMessage(null);
			if ("SIZE".equals(fileUploadFormBean.getContentOfFile())) {
				errors.add("BUS006", new ActionError("BUS006"));
			} else {
				errors.add("BUS005", new ActionError("BUS005"));
			}
		}
		return mapping.findForward("success");
	}
}