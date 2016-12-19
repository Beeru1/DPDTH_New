/**************************************************************************
 * File     : SessionManagement.java
 * Author   : Pragati
 * Version  : 0.1
 **************************************************************************
 *
 * Copyright @ 2002 This document has been prepared and written by 
 * IBM Global Services on behalf of Bharti, and is copyright of Bharti
 *
 **************************************************************************/
package com.ibm.virtualization.ussdactivationweb.session;

import javax.servlet.http.HttpSession;
import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionListener;

import org.apache.log4j.Logger;

import com.ibm.virtualization.ussdactivationweb.beans.UserDetailsBean;
import com.ibm.virtualization.ussdactivationweb.dao.UserMasterDAO;
import com.ibm.virtualization.ussdactivationweb.utils.Constants;


/**
 * @author Pragati
 *
 * This class handles the session of loged user. If user will not perform any action
 * since a defined time then system will destroy the user session.
 */
public class SessionManagement implements HttpSessionListener {

	/* 
	 * @see javax.servlet.http.HttpSessionListener#sessionCreated(javax.servlet.http.HttpSessionEvent)
	 */
	private static final Logger logger = Logger.getLogger(SessionManagement.class);
	public void sessionCreated(HttpSessionEvent arg0) {
		
	}

	/**
	 * This method destroy the user session.  
	 * @see javax.servlet.http.HttpSessionListener#sessionDestroyed(javax.servlet.http.HttpSessionEvent)
	 */
	public void sessionDestroyed(HttpSessionEvent arg0)  {
		HttpSession session = arg0.getSession();
		
		try{
			UserDetailsBean userBean=(UserDetailsBean)session.getAttribute("USER_INFO");
			if(userBean != null) {
				
				userBean.setLoginStatus(Constants.InActiveState);
				new UserMasterDAO().updateLoginStatus(userBean);
			}
			session.invalidate();
			session=null;
			logger.info("sessionDestroyed for LoginId "
					+ userBean.getLoginId());
			logger.debug("sessionDestroyed for LoginId "
					+ userBean.getLoginId());
			
		}catch(Exception e){
			logger.debug("Some exception occured at session management "+ e.getMessage());
		}
	}

}
