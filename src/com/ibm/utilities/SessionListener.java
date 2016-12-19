/*
 * Created on Feb 15, 2008
 *
 * To change the template for this generated file go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
package com.ibm.utilities;

/**
 * @author Sachin Kumar
 *
 * To change the template for this generated type comment go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionListener;

import org.apache.log4j.Logger;

import com.ibm.hbo.dto.HBOUserBean;
import com.ibm.hbo.forms.HBOLoginFormBean;
import com.ibm.hbo.services.LoginService;
import com.ibm.hbo.services.impl.LoginServiceImpl;

public class SessionListener
implements HttpSessionListener {
	private static final Logger logger;

static {

	logger = Logger.getLogger(SessionListener.class);
}
	public void sessionCreated(HttpSessionEvent arg0) {
		//logger.info("Session Created");
	}

	/* (non-Javadoc)
	 * @see javax.servlet.http.HttpSessionListener#sessionDestroyed(javax.servlet.http.HttpSessionEvent)
	 */
	public void sessionDestroyed(HttpSessionEvent arg0) {
		// TODO Auto-generated method stub
		HBOUserBean userBean=(HBOUserBean)arg0.getSession().getAttribute("USER_INFO");
		//ICELoginFormBean loginformBean = new ICELoginFormBean();
		
		if (userBean!=null) {
								
					try {
						LoginService loginService = new LoginServiceImpl();
						String returnMessage = "";
						try {
							returnMessage = loginService.logoutUser(userBean.getUserLoginId());
							//loginformBean.setMessage("Session Expired");
							
						}catch(Exception e) {
						}
			
					} catch (Exception e) {
						logger.error("Exception From LogoutAction" + e);
						

					}
			

				}
						
		}
	}




