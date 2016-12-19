/*****************************************************************************\
 **
 ** Virtualization - Recharge.
 **
 ** Copyright (c) 2007-2008 IBM.
 ** All Rights Reserved
 **
 **
 \****************************************************************************/

package com.ibm.virtualization.recharge.cache;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.ibm.virtualization.framework.bean.ChannelType;
import com.ibm.virtualization.recharge.common.RecieverType;
import com.ibm.virtualization.recharge.common.TransactionType;
import com.ibm.virtualization.recharge.dto.CircleTopupConfig;
import com.ibm.virtualization.recharge.dto.INRouterServiceDTO;
import com.ibm.virtualization.recharge.dto.MobileNumberSeries;
import com.ibm.virtualization.recharge.dto.SysConfig;
import com.ibm.virtualization.recharge.exception.VirtualizationServiceException;

/**
 * The Cache interface, base interface for Cache services
 * 
 * @author Rohit Dhall
 * @date 07-Sep-2007
 * 
 */
public interface Cache {
	
	
	
	/**
	 * This method returns cache information
	 * @return map
	 */
	public Map getConsolidateCacheInfo();
	
	/**
	 * This method returns an Map containing names of all the roles as Keys
	 * which have been assigned to a user,which is being maintained in cache
	 * Default values of each would be Y
	 * 
	 * @param groupID
	 *            String
	 * @param channelType
	 * 				ChannelType
	 * @return Map
	 */
	public ArrayList getUserCredentials(long groupID,ChannelType channelType);

	/**
	 * This method returns a boolean, indicating whether the group has been
	 * assigned passed in Role
	 * 
	 * @param groupID
	 *            String
	 * @param channelType
	 * 			ChannelType
	 * @param roles
	 *            List
	 * @return boolean
	 */
	public boolean isUserAuthorized(long groupID,ChannelType channelType, List roles);

	/**
	 * This method returns a boolean, indicating whether the group has been
	 * assigned passed in Role
	 * 
	 * @param groupID
	 *            String
	 * @param channelType
	 * 				ChannelType
	 * @param roleName
	 *            String
	 * @return boolean
	 */
	public boolean isUserAuthorized(long groupID,ChannelType channelType, String roleName);

	/**
	 * 
	 * This method returns a CircleTopupConfig DTO object corresponding to
	 * circleId,TransactionType and ammount passed as argument.
	 * 
	 * @param circleId
	 * @param transactionType
	 * @param amount
	 * @param comissionGroupId
	 * @return
	 */
	public CircleTopupConfig getTopupConfig(int circleId,TransactionType transactionType,double amount,long commissionGroupId);
	/**
	 * This method returns a SysConfig DTO object corresponding to circleId,TransactionType and
	 * ChannelType passed as argument.
	 * 
	 * @param circleId
	 * @param transactionType
	 * @param channelType
	 * @return SysConfig DTO.
	 */
	public SysConfig getSystemConfig(int circleId,TransactionType transactionType,ChannelType channelType);
	/**
	 *	This method returns a INRouterServiceDTO  object corresponding to susbscriberNumber passed as argument.
	 * 
	 * @param subscriberNumber
	 * @return
	 * @throws VirtualizationServiceException
	 */
	public INRouterServiceDTO getDestinationIn(String subscriberNumber)throws VirtualizationServiceException;
	/**
	 * This method return a HashMap containing response message details corresponding to message code
	 * passed as argument
	 * 
	 * @param msgCode
	 * @return
	 */
	public HashMap<RecieverType,String> getMessageDetails(String msgCode);
	/**
	 * This method return a MobileNumberSeries object corresponding to mobileNumber passed as argument
	 * 
	 * @param mobileNumber
	 * @return
	 * 			MobileNumberSeries DTO if it found mobile Number in any series
	 * 			else return
	 * 			null 
	 */
	public MobileNumberSeries getNumberSeries(long mobileNumber,String seriesName);
	/**
	 * This method return a mobile series id ccorresponding to mobileNumber passed as argument 
	 * @param mobileNumber
	 * @return mobileSeriesId on success
	 * 			-1 on failure
	 */
	public int getMobileSeriesId(long mobileNumber);
	
}
