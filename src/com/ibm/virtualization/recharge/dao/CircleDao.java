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

import com.ibm.virtualization.recharge.dto.Circle;
import com.ibm.virtualization.recharge.dto.ReportInputs;
import com.ibm.virtualization.recharge.exception.DAOException;

/**
 * Interface for CircleDao Implementation.
 * 
 * @author BAhuja
 * 
 */
public interface CircleDao {

	/**
	 * This method updates balance of a circle for aaccount .
	 * 
	 * @param circle
	 * @throws DAOException
	 */

	public int updateCircleOperatingBal(double balance, long updateBy,
			long circleId) throws DAOException;

	/**
	 * This method inserts details of the circle.
	 * 
	 * @param circle
	 * @return
	 * @throws DAOException
	 */
	public void insertCircle(Circle circle) throws DAOException;

	/**
	 * This method updates details of a circle.
	 * 
	 * @param circle
	 * @throws DAOException
	 */
	public void updateCircle(Circle circle) throws DAOException;

	/**
	 * This method returns a details of a circle based on the circle name.
	 * 
	 * @param circleName
	 * @return circle
	 */
	public Circle getCircle(String circleName) throws DAOException;

	/**
	 * This method returns a details of a circle based on the circle Id.
	 * 
	 * @param circleId
	 * @return circle
	 */
	public Circle getCircle(int circleId) throws DAOException;

	/**
	 * This Method returns list of circles for a given Circle Code or Circle
	 * Name.
	 * 
	 * @param circleCode
	 * @param circleName
	 * @return ArrayList
	 * @throws DAOException
	 */
	public ArrayList<Circle> getCircles(String circleCode, String circleName)
			throws DAOException;

	/**
	 * This method provides ArrayList of all the Circles regardless of the
	 * STATUS field
	 * 
	 * @return ArrayList of Circle
	 * @throws DAOException
	 */
	public ArrayList getAllCircles() throws DAOException;

	/**
	 * This method provides ArrayList of all the Circles based on the arguments
	 * in MTPInputs
	 * 
	 * 
	 * @return ArrayList of Circle
	 * @throws DAOException
	 */
	public ArrayList getAllCircles(ReportInputs inputDto, int lb, int ub) throws DAOException;
	

	/**
	 * This method provides ArrayList of all the Circles based on the arguments
	 * for download purpose.
	 * 
	 * 
	 * @return ArrayList of Circle
	 * @throws DAOException
	 */
	public ArrayList getAllCircles(ReportInputs inputDto) throws DAOException ;
	
	/**
	 * This method returns the no of Total Circles.
	 * 
	 * @param inputDto
	 * @return
	 * @throws DAOException
	 */
	public int getCirclesCount(ReportInputs inputDto) throws DAOException;

	/**
	 * This method provides ArrayList of active circles
	 * 
	 * @return ArrayList of Circle
	 * @throws DAOException
	 */
	public ArrayList getActiveCircles() throws DAOException;

	/**
	 * This method returns name of the circle based on circleId provided and the
	 * status of the circle is active
	 * 
	 * @param circleId
	 * @return circleName
	 * @throws DAOException
	 */
	public String getActiveCircleName(int circleId) throws DAOException;

	/**
	 * This method updates the available balance of the circle when pending
	 * trasaction has been approved.
	 * 
	 * @param distributorTransaction
	 * @return
	 * @throws DAOException
	 */

	public void reduceCircleAvailableBalance(double transAmt, long updateBy,
			int circleId) throws DAOException;

	/**
	 * This method updates the operating balance of the circle when pending
	 * trasaction has been rejected.
	 * 
	 * @param distributorTransaction
	 * @return
	 * @throws DAOException
	 */

	public void increaseCircleOperatingBalance(double transAmt, long updateBy,
			int circleId) throws DAOException;

	/**
	 * this method reduce the circle operating and available balance at the time
	 * of new account creation
	 * 
	 * @param balance
	 * @param updateBy
	 * @param circleId
	 * @return
	 * @throws DAOException
	 */
	public int doUpdateCircleBalance(double balance, long updateBy,
			long circleId) throws DAOException;

}