/*****************************************************************************\
 **
 ** Virtualization - Recharge.
 **
 ** Copyright (c) 2007-2008 IBM.
 ** All Rights Reserved
 **
 **
 \****************************************************************************/
package com.ibm.virtualization.recharge.service;

import java.util.ArrayList;
import java.util.List;

import com.ibm.virtualization.recharge.dto.CircleTopupConfig;
import com.ibm.virtualization.recharge.dto.ReportInputs;
import com.ibm.virtualization.recharge.dto.UploadTopupSlab;
import com.ibm.virtualization.recharge.exception.VirtualizationServiceException;

/**
 * Interface definition for circle topup management service.
 * 
 * @author Kumar Saurabh
 * 
 */
public interface CircleTopupService {
	/**
	 * This method accepts CircleTopupConfig DTO from Action and performs
	 * business validations to create a circle TopUp Config by calling the
	 * insertCircleTopup() for CircleTopupConfigDaoRdbms.
	 * 
	 * @param circleTopupConfig
	 * @throws VirtualizationServiceException
	 */
	public void createCircleTopup(CircleTopupConfig circleTopupConfig)
			throws VirtualizationServiceException;

	/**
	 * This method invokes getCircleTopupOfId(int topupConfigId) method of
	 * CircleTopupConfigDaoRdbms
	 * 
	 * @param topupConfigId
	 * @return circleTopupConfig
	 * @throws VirtualizationServiceException
	 */
	public CircleTopupConfig getCircleTopupOfId(int topupConfigId)
			throws VirtualizationServiceException;

	/**
	 * This method invokes updateCircleTopup(int topupConfigId) method of
	 * CircleTopupConfigDaoRdbms
	 * 
	 * @param circleTopupConfig
	 * @throws VirtualizationServiceException
	 */
	public void updateCircleTopup(CircleTopupConfig circleTopupConfig)
			throws VirtualizationServiceException;
	
	/**
	 * This method fetches the TopUpConfig Details on the basis of the Arguments passed.
	 * @param inputDto
	 * @return
	 * @throws VirtualizationServiceException
	 */
	public List getCircleTopupList(ReportInputs inputDto,int lb,int ub)	throws VirtualizationServiceException;
	

	/**
	 * This method fetches the TopUpConfig Details on the basis of the Arguments passed for download purpose.
	 * @param inputDto
	 * @return
	 * @throws VirtualizationServiceException
	 */
	public List getCircleTopupList(ReportInputs inputDto)	throws VirtualizationServiceException;
	
	/**
	 * This method Counts the Total no of Circles for the passed arguments
	 * @param inputDto
	 * @return
	 * @throws VirtualizationServiceException
	 */
	public int getCircleTopupListCount(ReportInputs inputDto) throws VirtualizationServiceException;
	
	
	/**
	 * This method is used to upload the slab configuration data
	 * in bulk
	 * @param topupDto
	 * 
	 * @return int[]
	 * @throws VirtualizationServiceException
	 */
	public int [] uploadSlabData(UploadTopupSlab topupDto)
			throws VirtualizationServiceException;
		
	
	/**
	 * This method fetches the failed data of Topup Configuration Bulk Upload
	 * @param circleId
	 * 
	 * @return ArrayList
	 * @throws VirtualizationServiceException
	 */
	public ArrayList getTopupConfigFailData(int circleId)	throws VirtualizationServiceException;

	
	
}