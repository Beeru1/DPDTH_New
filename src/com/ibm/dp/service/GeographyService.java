/*****************************************************************************\
 **
 ** Virtualization - Recharge.
 **
 ** Copyright (c) 2007-2008 IBM.
 ** All Rights Reserved
 **
 **
 \****************************************************************************/
package com.ibm.dp.service;

import java.util.ArrayList;

import com.ibm.dp.dto.Geography;
import com.ibm.virtualization.recharge.dto.Circle;
import com.ibm.virtualization.recharge.dto.ReportInputs;
import com.ibm.virtualization.recharge.exception.DAOException;
import com.ibm.virtualization.recharge.exception.VirtualizationServiceException;


public interface GeographyService {
	
	
	public Geography getGeography(int geographyId , Integer level) throws VirtualizationServiceException;
	
	/**
	 * This Method returns list of circles for a given Circle Code or Circle Name.
	 * 
	 * @param circleCode
	 * @param circleName
	 * @return ArrayList
	 * @throws VirtualizationServiceException
	 */
	
	public ArrayList getRegions(Integer level) throws VirtualizationServiceException;

	/**
	 * This method is used to create a new circle.
	 * 
	 * @param circle
	 * @throws VirtualizationServiceException
	 */
	public void createGeography(Geography geography) throws VirtualizationServiceException;
	public void updateGeography(Geography geography) throws VirtualizationServiceException ;
	public ArrayList getAllGeography(Integer level) throws VirtualizationServiceException;
	public ArrayList getAllGeographys(ReportInputs inputDto, int lb, int ub,Integer level) throws VirtualizationServiceException;
}