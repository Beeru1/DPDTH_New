/*****************************************************************************\
 **
 ** Virtualization - Recharge.
 **
 ** Copyright (c) 2007-2008 IBM.
 ** All Rights Reserved
 **
 **
 \****************************************************************************/

package com.ibm.virtualization.recharge.dao;

import java.util.ArrayList;

import com.ibm.virtualization.recharge.dto.CircleTopupConfig;
import com.ibm.virtualization.recharge.dto.InMapping;
import com.ibm.virtualization.recharge.dto.MessageDetail;
import com.ibm.virtualization.recharge.dto.MobileNumberSeries;
import com.ibm.virtualization.recharge.dto.Role;
import com.ibm.virtualization.recharge.dto.SysConfig;
/**
 * Interface for CircleDao Implementation.
 * 
 * @author BAhuja
 * 
 */
public interface CacheDao {

	/**
	 * This method will retrieve all the roles of users, order by group id .
	 * 
	 * @return ArrayList
	 */

	public ArrayList<Role> getGroupAndRoleInfo() ;

	/**
	 * This method will retrieve all the Circle_Topup_Config info, order by
	 * circleId transactionType, and will return an collection of
	 * CircleTopupConfig DTO
	 *
	 * @return ArrayList
	 */
	
	public ArrayList<CircleTopupConfig> getTopupConfigInfo() ;

	
	/**
	 * This method will retrieve all the System Config info, order by circleId
	 * transactionType channelType, and will return an collection of
	 * SystemConfig DTO
	 *
	 * @return ArrayList
	 */
	public ArrayList<SysConfig> getSysConfigInfo() ;
	
	
	
	/**
	 * This Method will retrieve all the INMApping information.
	 */
	public ArrayList<InMapping> getInMappingInfo();
	
	
	
	/**
	 * This method will retrieve all Message Details info,and store it in
	 * HashMap<String,HashMap<RecieverType,String(message to send)>
	 */
	public ArrayList<MessageDetail> getResponseMsgDetails() ;
	
	
	
	/**
	 * This method will retrieve all NumberSeriesDetails info,and store it in
	 * ArrayList<MobileNumberSeries>
	 */
	public ArrayList<MobileNumberSeries> getNumberSeriesDetails()  ;
	
	
	
	public ArrayList<InMapping> getMobileSeriesConfig(); 
	public ArrayList getGroupAndUrlInfo();
	public ArrayList<String> getTotalUrls();
}