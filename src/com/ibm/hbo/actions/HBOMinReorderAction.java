package com.ibm.hbo.actions;



import java.io.PrintWriter;
import java.util.ArrayList;

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
import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.dom4j.io.OutputFormat;
import org.dom4j.io.XMLWriter;

import com.ibm.hbo.common.HBOUser;
import com.ibm.hbo.dto.HBOMinReorderDTO;
import com.ibm.hbo.dto.HBOProductDTO;
import com.ibm.hbo.dto.HBOStockDTO;
import com.ibm.hbo.dto.HBOUserBean;
import com.ibm.hbo.forms.HBOMinReorderFormBean;
import com.ibm.hbo.services.HBOMasterService;
import com.ibm.hbo.services.impl.HBOMasterServiceImpl;
import com.ibm.virtualization.recharge.dto.UserSessionContext;




/**
 * @author Aditya
 *
 * To change the template for this generated type comment go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
public class HBOMinReorderAction extends DispatchAction
{
	/*
	 * Logger for the class.
	 */
	private static final Logger logger;

	static {
		logger = Logger.getLogger(HBOMasterAction.class);
	}
		
	public ActionForward getValues(ActionMapping mapping,ActionForm form, HttpServletRequest request,HttpServletResponse response) throws Exception
	{		
		HttpSession session = request.getSession();
		ActionForward forward = new ActionForward(); // return value
		ArrayList arrMasterList = new ArrayList(); 

		String userId = "";//(String) session.getAttribute("userId");
		// Get Airtel Distributors
		HBOMasterService masterService = new HBOMasterServiceImpl();
		UserSessionContext context=(UserSessionContext)session.getAttribute("USER_INFO");
		HBOUser hboUserBean = new HBOUser(context);		
		String warehouse_id=hboUserBean.getWarehouseID();
		//String warehouse_circle_id=hboUserBean.getCircleId();
		logger.info("warehouse_circle_id-------"+warehouse_id);
		String condition = "";
		//"WAM.WAREHOUSE_CIRCLE_ID="+"'"+warehouse_circle_id+"'";// (String) session.getAttribute("condition");
		ArrayList arrDistributorList = masterService.getMasterList(userId,"reorder-warehouse","wm1.warehouse_id ="+warehouse_id);
		logger.info("size>>>>>>>>>>>>>"+arrDistributorList.size());
	
		logger.info("arrDistributorList.size:"+arrDistributorList.size());
		HBOMinReorderFormBean minReorderFormBean = (HBOMinReorderFormBean) form;
		minReorderFormBean.setArrdistributorList(arrDistributorList);
		
//		added on 11 Aug 08 by Anju

		ArrayList arrBusinessList = masterService.getMasterList(userId,"businesscategory",condition);
		minReorderFormBean.setArrBusinessCat(arrBusinessList);
		minReorderFormBean.setArrproductsList(minReorderFormBean.getArrproductsList());
		
		saveToken(request);
		forward = mapping.findForward("success");
		return (forward);
	}
	public ActionForward insertMinReorder(ActionMapping mapping,ActionForm form, HttpServletRequest request,HttpServletResponse response) throws Exception
	{
		ActionMessages msgs = new ActionMessages();
		HttpSession session = request.getSession();
		HBOUserBean hboUserBean = (HBOUserBean) session.getAttribute("USER_INFO");;
		String condition="";
		String userid=hboUserBean.getActorId();
		ActionMessages messages = new ActionMessages();
		ActionErrors errors = new ActionErrors(); 
		ActionForward forward = new ActionForward();
		HBOMinReorderFormBean minReorderFormBean = (HBOMinReorderFormBean) form;
		HBOMinReorderDTO minReordDTO=new HBOMinReorderDTO();
		HBOMasterService masterService = new HBOMasterServiceImpl();
		BeanUtils.copyProperties(minReordDTO,minReorderFormBean);
		logger.info("Action:Insert Min Reorder");
		minReordDTO.setCreated_by(hboUserBean.getUserLoginId());
	//	minReordDTO.setBcode(minReorderFormBean.getBusiness_catg());
		String message ="";
		if (isTokenValid(request,true)){ 
			message = masterService.insertMRL(userid,minReordDTO,"minreorder",condition);
		}

		if (message.equalsIgnoreCase("success")){
			messages.add("INSERT_SUCCESS",new ActionMessage("insert.success"));
			saveMessages(request, (ActionMessages) messages);
		}
		else if (message.equalsIgnoreCase("failure"))
		{
			errors.add("ERROR_OCCURED",new ActionError("error.occured"));
			saveErrors(request, (ActionErrors) errors);			
		}
		minReorderFormBean.reset();
		return getValues(mapping,form,request,response);
	}

	public ActionForward getChange(ActionMapping mapping,ActionForm form, HttpServletRequest request,HttpServletResponse response) throws Exception
				{
					Document document = DocumentHelper.createDocument();
					Element root = document.addElement("options");
					Element optionElement;
					
					String pid="";
					String cid="";
					String mth_yr="";
					
					
					pid=request.getParameter("cond1");
					cid=request.getParameter("cond");
					mth_yr=request.getParameter("cond3");

					String Id=request.getParameter("Id");
					HBOMasterService masterService = new HBOMasterServiceImpl();
					ArrayList arrGetValue=new ArrayList();
					
					//logger.info("in the projection action: "+Id+"cond"+cid+":>>>>"+ware);
					
					if(cid.startsWith("projectionQty")&&(!cid.equals(""))&&(cid!=null)){
						arrGetValue=masterService.getChange(pid,cid,null,mth_yr,null);
						logger.info("arrGetValue"+arrGetValue+"----"+arrGetValue.size());
						String avlProdQty = "0"; 
						if(arrGetValue == null ||arrGetValue.size()== 0){ 
							avlProdQty = "0";
							}
						else{				
							HBOStockDTO stockDTO = (HBOStockDTO)arrGetValue.get(0);	
							avlProdQty =  stockDTO.getProjQty();
										
						}
						logger.info("avlProdQty"+avlProdQty);
						response.setContentType("text/html");
						response.setHeader("Cache-Control", "No-Cache");
						PrintWriter out = response.getWriter();
						out.write(avlProdQty);
						out.flush();	
					}
					else if(cid.equalsIgnoreCase("category")){
						arrGetValue=masterService.getChange(Id,"category","ware",null,null);
						HBOProductDTO getChangeDTO = new HBOProductDTO(); 
						
						for (int counter = 0; counter < arrGetValue.size(); counter++) {
	
							optionElement = root.addElement("option");
							getChangeDTO = (HBOProductDTO)arrGetValue.get(counter);
							if (getChangeDTO != null) {
								optionElement.addAttribute("value",getChangeDTO.getProductcode());
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

						
					}
	
					return null;

				}
}
