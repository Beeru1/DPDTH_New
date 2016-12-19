/*
 * Created on 15 july 2007
 *
 * To change the template for this generated file go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */

package com.ibm.virtualization.ussdactivationweb.config; 

import java.io.IOException;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.apache.struts.action.RequestProcessor;

import com.ibm.virtualization.ussdactivationweb.actions.LoginAction;
import com.ibm.virtualization.ussdactivationweb.utils.Constants;
  
/**
 * This class filters the request and allows access to valid urls only.
 * 
 * @author Ashad 
 * 
 * To change the template for this generated type comment go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */

public class CustomRequestProcessor extends RequestProcessor {
 
	protected static Logger logger = Logger.getLogger(LoginAction.class
			.getName());
	
	/**
	 * Automatically generated constructor: HsbRequestProcessor
	 */
	public CustomRequestProcessor() {
	}

	/**
	 * processPreprocess method for HsbRequestProcessor
	 * 
	 * @param request
	 *            the execute method parameter
	 * @param response
	 *            the execute method parameter
	 * @return boolean
	 */

	protected boolean processPreprocess(HttpServletRequest request,
			HttpServletResponse response) {
		
		/*	boolean flag = false;
		HttpSession session = request.getSession();
		String requestedURI = request.getRequestURI();
		String requestType = request.getMethod();
		String requestMapping = requestedURI.substring(requestedURI
				.lastIndexOf("/") + 1, requestedURI.lastIndexOf(".do"));*/
		
		boolean flag = true;
		ArrayList completeMenuList = new ArrayList();
		HttpSession session = request.getSession();
		String requestedURI = request.getRequestURI();
		String requestType = request.getMethod();
		String requestMapping = requestedURI.substring(requestedURI
				.lastIndexOf("/") + 1, requestedURI.lastIndexOf("."));
		String methodName = request.getParameter("methodName");
		if(methodName==null) methodName="";
		String reqUrl = requestMapping.concat(methodName);
		
		
//		 Block all HTTP Methods except GET and POST
		boolean validMethod = (requestType.equalsIgnoreCase("GET") || requestType.equalsIgnoreCase("POST"));
		// Block Login to Home Page from Login Page by HTTP GET Method.
		validMethod = !(requestType.equalsIgnoreCase("GET") && reqUrl.equalsIgnoreCase("LoginActionlogin"));
		try {
			if(!validMethod){ // Incorrect HTTP Method page, hence unauthorized.
				doForward("/LoginAction.do?methodName=unauthorizedLogin",
					request, response);
				flag = false;
			}
			
		} catch (IOException IOexp) {
			logger.error("IOException" + IOexp.getMessage(), IOexp);
			flag = false;
		} catch (ServletException ServExp) {
			logger.error("ServletException" + ServExp.getMessage(),
					ServExp);
			flag = false;
		}
		catch(Exception e){
			logger.error("Exception at requestProcessor()"+ e.getMessage(),e);
			flag = false;
		}
		
		if (!(("LoginAction".equalsIgnoreCase(requestMapping)) || ("UserMasterAction"
				.equalsIgnoreCase(requestMapping) && "forgotPassword"
				.equalsIgnoreCase(methodName)))) {
			if (session == null
					|| (session != null && session
							.getAttribute(Constants.USER_INFO) == null)) {
				try {
					doForward("/LoginAction.do?methodName=init", request,
							response);
					flag = false;
				} catch (IOException IOexp) {
					logger.debug("IOException" + IOexp.getMessage(), IOexp);
					logger.error("IOException" + IOexp.getMessage(), IOexp);
					flag = false;
				} catch (ServletException ServExp) {
					logger.debug("ServletException" + ServExp.getMessage(),
							ServExp);
					logger.error("ServletException" + ServExp.getMessage(),
							ServExp);
					flag = false;
				}catch(Exception e){
					logger.debug("Exception at requestProcessor()"+ e.getMessage(),e);
					logger.error("Exception at requestProcessor()"+ e.getMessage(),e);
				}
		
	}
		} 
		
		
		/*else {
			
			ArrayList menuList = (ArrayList) session.getAttribute("MENULIST");
			if(menuList!=null)
			{
				Iterator itr = menuList.iterator();
				while (itr.hasNext()) {
					ModuleBean menuUrl = (ModuleBean) itr.next();

					if (menuUrl != null
							&& menuUrl.getMenu_level().equalsIgnoreCase("0"))
						continue;
                    
					String menuURL = menuUrl.getModuleUrl();
					String methodIndex = menuURL
							.substring(menuURL.indexOf("=") + 1);

					String actionIndex = menuURL.substring(0, menuURL
							.lastIndexOf(".do?"));
					String menuURL1 = actionIndex.concat(methodIndex);
					
					completeMenuList.add(menuURL1);
					
				}
				
			}
			*//**MENUEXTLIST contains action name concatenated with method name 
			 * adding MENULIST and MENUEXTLIST to an arraylist **//*				
			ArrayList menuExtList = (ArrayList) session
					.getAttribute("MENUEXTLIST");
			if(menuExtList!=null)
			{
				arr.addAll(menuExtList);
			}

			*//** adding change password link to arraylist because it is 
			 * not the part of MENULIST and MENUEXTLIST **//*
			completeMenuList.add("ChangePasswordActioninitChangePassword");
			completeMenuList.add("ChangePasswordActionprocessChangePassword");
			
			
			*//**checking if requested URL is authorized or not **//*
			if (!completeMenuList.contains(reqUrl)) {
				try {
					doForward("/LoginAction.do?methodName=unauthorized",
							request, response);
					flag = false;
					
				} catch (IOException IOexp) {
					logger.debug("IOException" + IOexp.getMessage(), IOexp);
					logger.error("IOException" + IOexp.getMessage(), IOexp);
					flag = false;
				} catch (ServletException ServExp) {
					logger.debug("ServletException" + ServExp.getMessage(),
							ServExp);
					logger.error("ServletException" + ServExp.getMessage(),
							ServExp);
					flag = false;
				}
				catch(Exception e){
					logger.debug("Exception at requestProcessor()"+ e.getMessage(),e);
					logger.error("Exception at requestProcessor()"+ e.getMessage(),e);
					flag = false;
				}
			}
		}*/
		response.setHeader("Cache-Control", "no-store");
		response.setIntHeader("max-stale", 0);
		return flag;
	}
}
