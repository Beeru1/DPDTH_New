/**
 * 
 */
package com.ibm.virtualization.ussdactivationweb.reports.daoInterface;

import com.ibm.virtualization.ussdactivationweb.utils.Constants;
import com.ibm.virtualization.ussdactivationweb.utils.SQLConstants;
import com.ibm.virtualization.ussdactivationweb.utils.Utility;

/**
 * @author a_gupta
 *
 */
public interface SubscriberReleaseDAOInterface {
	
	public static final  String RELESE_SUBSCRIBER_PROCCALL=new StringBuffer(500)
	.append("{call  ")
	.append(SQLConstants.FTA_SCHEMA)
	.append(".")
	.append(Utility
			.getValueFromBundle(
					Constants.RELESE_SUBSCRIBER_PROC,
					Constants.WEB_APPLICATION_RESOURCE_BUNDLE))
	.append("(?,?,?,?)}").toString();


}
