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
import java.util.List;

import com.ibm.virtualization.recharge.common.TransactionType;
import com.ibm.virtualization.recharge.dto.CircleTopupConfig;
import com.ibm.virtualization.recharge.dto.ReportInputs;
import com.ibm.virtualization.recharge.exception.DAOException;

/**
 * Interface for CircleDao Implementation.
 * 
 * @author Kumar Saurabh
 * 
 */
public interface CircleTopupConfigDao {

	/**
	 * This method inserts top-up configuration details for the circle .
	 * 
	 * @param circleTopupConfig
	 * @throws DAOException
	 */
	public void insertCircleTopup(CircleTopupConfig circleTopupConfig)
			throws DAOException;

	/**
	 * This method returns details of a Circle Topup configuration based on the
	 * circleId
	 * 
	 * @param circleId
	 * @return List
	 * @throws DAOException
	 */
	public List getCircleTopup(int circleId) throws DAOException;

	/**
	 * This method returns details of a Circle Topup configuration based on the
	 * circleId and Transaction Type
	 * 
	 * @param circleId
	 * @param transactionType
	 * @return List
	 * @throws DAOException
	 */
	public List getCircleTopup(int circleId, String transactionType) throws DAOException;
	
	/**
	 * This method returns details of Circle Topup configuration based on the
	 * circleId
	 * 
	 * @param cirleId
	 * @return CircleTopupConfig
	 * @throws DAOException
	 */
	public ArrayList getCircleTopupList(int circleId,double startAmount,double tillAmount, TransactionType transactionType)
			throws DAOException;
	
	/**
	 * This method returns details of Circle Topup configuration based on the
	 * circleId
	 * 
	 * @param cirleId
	 * @return CircleTopupConfig
	 * @throws DAOException
	 */
	public CircleTopupConfig getCircleTopup(int circleId,double transactionAmount, TransactionType transactionType)
			throws DAOException;
	/**
	 * This method returns details of Circle Topup configuration based on the
	 * topupConfigId
	 * 
	 * @param topupConfigId
	 * @return CircleTopupConfig
	 * @throws DAOException
	 */
	public CircleTopupConfig getCircleTopupOfId(int topupConfigId)
			throws DAOException;

	
	/**
	 * This method is used to update the details of Circle Topup config
	 * 
	 * @param circleTopupConfig
	 * @throws DAOException
	 */
	public void updateCircleTopup(CircleTopupConfig circleTopupConfig)
			throws DAOException;
	
	/**
	 * This method fetches the TopUp config details for the passed arguments
	 * @param inputDto
	 * @throws DAOException
	 */
	public List getCircleTopupList(ReportInputs inputDto,int lb,int ub)
	throws DAOException;
	
	/**
	 * This method fetches the TopUp config details for the passed arguments for download purpose
	 * @param inputDto
	 * @throws DAOException
	 */
	public List getCircleTopupList(ReportInputs inputDto)
	throws DAOException;
	
	/**
	 * This method counts the no of rows .
	 * @param inputDto
	 * @return
	 * @throws DAOException
	 */
	public int getCircleTopupListCount(ReportInputs inputDto)
	throws DAOException;
	
	/**
	 * This method is used to save the data of csv file in database.
	 * @param circleTopupDtoList
	 * @param circleId
	 * @param transactionType
	 * @param createdBy
	 * @param updatedBy
	 * 
	 * @return int
	 * @throws DAOException
	 */
	public int uploadSlabData(ArrayList circleTopupDtoList ,int circleId ,String transactionType,long createdBy)
	throws DAOException;
	
	
	/*
	 * This method is used to save the incorrect data of csv file in database.
	 * @param topupIncorrectDtoList
	 * @param circleId
	 * @param transactionType
	 * @param createdBy
	 * 
	 * @return int
	 * @throws DAOException
	 */

	
	public int uploadSlabIncorrectData(ArrayList topupIncorrectDtoList,int circleId,String transactionType,long createdBy)
	 throws DAOException;
	
	
	/**
	 * This method fetches the failed data of Topup Configuration Bulk Upload
	 * @param circleId
	 * 
	 * @return ArrayList
	 * @throws DAOException
	 */
	
	public ArrayList getTopupConfigFailData(int circleId)	throws DAOException;

}