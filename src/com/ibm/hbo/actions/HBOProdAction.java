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

import com.ibm.hbo.common.HBOConstants;
import com.ibm.hbo.common.HBOUser;
import com.ibm.hbo.dto.HBOStockDTO;
import com.ibm.hbo.dto.HBOWarehouseMasterDTO;
import com.ibm.hbo.forms.HBOAssignProdStockFormBean;
import com.ibm.hbo.services.HBOMasterService;
import com.ibm.hbo.services.HBOStockService;
import com.ibm.hbo.services.impl.HBOMasterServiceImpl;
import com.ibm.hbo.services.impl.HBOStockServiceImpl;
import com.ibm.virtualization.framework.bean.ChannelType;
import com.ibm.virtualization.recharge.authorization.AuthorizationFactory;
import com.ibm.virtualization.recharge.common.Constants;
import com.ibm.virtualization.recharge.dto.UserSessionContext;
import com.ibm.virtualization.recharge.service.AuthorizationInterface;



/**
 * @author Parul
 *
 * To change the template for this generated type comment go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
public class HBOProdAction extends DispatchAction {
		/*
		 * Logger for the class.
		 */
	private static Logger logger = Logger.getLogger(HBOProdAction.class.getName());
		
		public ActionForward getValues(ActionMapping mapping,
					ActionForm form, HttpServletRequest request,
					HttpServletResponse response) throws Exception
		{		
				HttpSession session = request.getSession();
				UserSessionContext sessionContext = (UserSessionContext) session.getAttribute(Constants.AUTHENTICATED_USER);	
				HBOUser obj=new HBOUser(sessionContext);
				ActionForward forward = new ActionForward(); // return value
				ArrayList arrBndlSatus=new ArrayList();
				HBOAssignProdStockFormBean assignProdStockFormBean = (HBOAssignProdStockFormBean) form;
				AuthorizationInterface authorizationInterface = AuthorizationFactory.getAuthorizerImpl();
				ArrayList roleList = authorizationInterface.getUserCredentials(sessionContext.getGroupId(), ChannelType.WEB);
			
				String userId = sessionContext.getId()+"";;
				logger.info("Inside getValues");
				
				String condition ="";
				//(String) session.getAttribute("condition");
				
				//Get product list from  masterService
				HBOMasterService masterService = new HBOMasterServiceImpl();
				ArrayList arrProductList = masterService.getMasterList(userId,HBOConstants.PRODUCT,condition);
				logger.info("arrProductList size:"+roleList.contains("ROLE_ND"));
				assignProdStockFormBean.setArrProducts(arrProductList);
				
				//	Get Unbundle/Bundle Status
				if(roleList.contains("ROLE_ND")||roleList.contains("ROLE_LD"))
				{	
					arrBndlSatus.add(0,"Bundled");
					arrBndlSatus.add(1,"Unbundled");
				}
				else 
				{	
					arrBndlSatus.add(0,"Bundled");
				}
				
				assignProdStockFormBean.setArrBndlSatus(arrBndlSatus);
				
				ArrayList arrCircleList=new ArrayList();
				arrCircleList.add("---Select Circle---");
				assignProdStockFormBean.setArrCircle(arrCircleList);
				assignProdStockFormBean.setAvailableProdQty("");
				assignProdStockFormBean.setWarehouseId("---Select WareHouse---");
				assignProdStockFormBean.setAssignedProdQty("");
				saveToken(request);
				forward = mapping.findForward("success");
				return (forward);
		}
		
	public ActionForward getChange(ActionMapping mapping,ActionForm form, HttpServletRequest request,HttpServletResponse response) throws Exception
				{
					HttpSession session = request.getSession();
					UserSessionContext sessionContext = (UserSessionContext) session.getAttribute(Constants.AUTHENTICATED_USER);
					HBOUser obj=new HBOUser(sessionContext);
					int warehouseId=0;
					int circleId=0;
					if(obj.getWarehouseID()!=null){
						warehouseId=Integer.parseInt(obj.getWarehouseID());
					}
					
					if(obj.getCircleId()!=null){
						circleId=Integer.parseInt(obj.getCircleId());
						
					}
					
					String condName=request.getParameter("cond2");;
					AuthorizationInterface authorizationInterface = AuthorizationFactory.getAuthorizerImpl();
					ArrayList roleList = authorizationInterface.getUserCredentials(sessionContext.getGroupId(), ChannelType.WEB);
					
					ArrayList param = new ArrayList();
					param.add(request.getParameter("cond1")); // Id -0 Bundling , #NonP ,#P
					param.add(request.getParameter("cond2")); //Cond- 1
					param.add(warehouseId);	//---2
					
					if(condName.equalsIgnoreCase("Bundling")){
						param.add(roleList);	//	---3	
						param.add(circleId);// ---4
					}		
					else if(condName.startsWith("#NonP")||condName.startsWith("#Pair"))
					{
						logger.info("in GET AVAILABLE Quantity");
						param.add(request.getParameter("cond3")); //3
						param.add(roleList); //4
												
					}else if(condName.equalsIgnoreCase("warehouse")){
						param.add(request.getParameter("cond3")); //3
						param.add(roleList); //4
						param.add(request.getParameter("cond4"));//5
					}
					
					//Method call to get value from DAOImpl
					ArrayList arrGetValue=new ArrayList();
					HBOMasterService masterService = new HBOMasterServiceImpl();
					arrGetValue =masterService.getChange(param);
					
					//Code for AJAX 
					Document document = DocumentHelper.createDocument();
					Element root = document.addElement("options");
					Element optionElement;
					if(condName.startsWith("#NonP")||condName.startsWith("#Pair"))
					{
						String avlProdQty = "0"; 
						if(arrGetValue == null ||arrGetValue.size()== 0){ 
							avlProdQty = "0";
							}
						else{					
							HBOStockDTO stockDTO = (HBOStockDTO)arrGetValue.get(0);	
							avlProdQty =  stockDTO.getAvlProducts();
							
							}	
							
					response.setContentType("text/html");
					response.setHeader("Cache-Control", "No-Cache");
					PrintWriter out = response.getWriter();
					out.write(avlProdQty);
					out.flush();				
					}
					else
					{
						HBOWarehouseMasterDTO warehouseMasterDTO  = new HBOWarehouseMasterDTO();
						
						for (int counter = 0; counter < arrGetValue.size(); counter++) {

							optionElement = root.addElement("option");
							warehouseMasterDTO = (HBOWarehouseMasterDTO)arrGetValue.get(counter);
							if (warehouseMasterDTO != null) {
								optionElement.addAttribute("value",warehouseMasterDTO.getOptionValue());
								optionElement.addAttribute("text",warehouseMasterDTO.getOptionText());
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
		

	public ActionForward assignProdStock(ActionMapping mapping,
						ActionForm form, HttpServletRequest request,
						HttpServletResponse response) throws Exception
			{	
				ActionMessages messages = new ActionMessages();
				ActionErrors errors = new ActionErrors();
				HttpSession session = request.getSession();
				HBOAssignProdStockFormBean assignProdStockFormBean = (HBOAssignProdStockFormBean) form;
				
				UserSessionContext sessionContext = (UserSessionContext) session.getAttribute(Constants.AUTHENTICATED_USER);
				AuthorizationInterface authorizationInterface = AuthorizationFactory.getAuthorizerImpl();
				ArrayList roleList = authorizationInterface.getUserCredentials(sessionContext.getGroupId(), ChannelType.WEB);
				
				HBOUser hboUserDetails=new HBOUser(sessionContext);
				String warehouseId=hboUserDetails.getWarehouseID();
				String message = "";
				
				HBOStockDTO assignHandsetDto = new HBOStockDTO();
				assignHandsetDto.setRoleList(roleList);
				
				/* BeanUtil to populate DTO with Form Bean data */
				BeanUtils.copyProperties(assignHandsetDto, assignProdStockFormBean);
				HBOStockService stockService = new HBOStockServiceImpl();
				if (isTokenValid(request,true)){  
					message = stockService.AssignProdStock(assignHandsetDto,warehouseId,hboUserDetails);
				}
				if(message.equalsIgnoreCase("success")){
					messages.add("INSERT_SUCCESS",new ActionMessage("insert.success"));
					saveMessages(request, (ActionMessages) messages);
				}
				if(message.equalsIgnoreCase("failure")){
					errors.add("ERROR_OCCURED",new ActionError("error.occured"));
					saveErrors(request, (ActionErrors) errors);
				}
				return getValues(mapping,form,request,response);	
			}				
			

}
