/*
 * Created on Aug 7, 2008
 *
 * To change the template for this generated file go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
package com.ibm.hbo.services;

import java.util.ArrayList;

import com.ibm.virtualization.recharge.exception.DAOException;
import com.ibm.hbo.exception.HBOException;
import com.ibm.hbo.forms.HBOSendEmailFormBean;

/**
 * @author Anju
 *
 * To change the template for this generated type comment go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
public interface HBOSendEmailService {
	
	public String sendMail(HBOSendEmailFormBean sendMail, String subject)throws HBOException, DAOException ;
	
	public ArrayList getSubjectList()throws HBOException, DAOException;
	
	public String getTSMEmailId(long loginId)throws HBOException, DAOException;

}
