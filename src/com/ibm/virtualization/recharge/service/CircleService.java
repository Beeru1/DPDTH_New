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

import com.ibm.virtualization.recharge.dto.Circle;
import com.ibm.virtualization.recharge.dto.ReportInputs;
import com.ibm.virtualization.recharge.exception.VirtualizationServiceException;

/**
 * Interface definition for circle management service.
 * 
 * @author Paras
 * 
 */

public interface CircleService {
	
	/**
	 * This method returns the no of Total Circles .
	 * 
	 * @param inputDto
	 * @return
	 * @throws VirtualizationServiceException
	 */
	public int getCirclesCount(ReportInputs inputDto) throws VirtualizationServiceException;
	
	/**
	 * This method returns collection of circles based on the passed arguments.
	 
	 * @param inputDto
	 * @return ArrayList
	 * @throws VirtualizationServiceException
	 */
	public ArrayList getAllCircles(ReportInputs inputDto,int lb, int ub) throws VirtualizationServiceException;
	
	/**
	 * This method returns collection of circles based on the passed arguments for download purpose.
	 
	 * @param inputDto
	 * @return ArrayList
	 * @throws VirtualizationServiceException
	 */
	public ArrayList getAllCircles(ReportInputs inputDto) throws VirtualizationServiceException; 
	
	/**
	 * This method returns collection of all active and in-active circles.
	 * 
	 * @return ArrayList
	 * @throws VirtualizationServiceException
	 */
	public ArrayList getAllCircles() throws VirtualizationServiceException;

	/**
	 * This method returns collection of all active circles.
	 * 
	 * @return ArrayList
	 * @throws VirtualizationServiceException
	 */
	public ArrayList getCircles() throws VirtualizationServiceException;

	/**
	 * This Method returns details of a circle for a given circleName.
	 * 
	 * @param circleName
	 * @return Circle
	 * @throws VirtualizationServiceException
	 */
	public Circle getCircle(String circleName)
			throws VirtualizationServiceException;

	/**
	 * This Method returns details of a circle for a given circleId.
	 * 
	 * @param circleId
	 * @return Circle
	 * @throws VirtualizationServiceException
	 */
	public Circle getCircle(int circleId)
			throws VirtualizationServiceException;
	
	/**
	 * This Method returns list of circles for a given Circle Code or Circle Name.
	 * 
	 * @param circleCode
	 * @param circleName
	 * @return ArrayList
	 * @throws VirtualizationServiceException
	 */
	public ArrayList getCircles(String circleCode, String circleName)
			throws VirtualizationServiceException;
	
	/**
	 * This method returns the list of active regions.
	 * 
	 * @return ArrayList
	 * @throws VirtualizationServiceException
	 */
	public ArrayList getRegions() throws VirtualizationServiceException;

	/**
	 * This method is used to create a new circle.
	 * 
	 * @param circle
	 * @throws VirtualizationServiceException
	 */
	public void createCircle(Circle circle)
			throws VirtualizationServiceException;

	/**
	 * This method is used to update the details of an existing circle.
	 * 
	 * @param circle
	 * @throws VirtualizationServiceException
	 */
	public void updateCircle(Circle circle)
			throws VirtualizationServiceException;
	
	
	/**
	 * This method returns the circlename on the basis of circle id passed.
	 * 
	 * @return circleId
	 * @throws VirtualizationServiceException
	 */
	
	public String getActiveCircleName(int circleId)
	throws VirtualizationServiceException;
}