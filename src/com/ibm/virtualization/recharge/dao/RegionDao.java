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

import com.ibm.virtualization.recharge.exception.DAOException;

/**
 * Interface for RegionDao Implementation
 * 
 * @author Paras
 * 
 */
public interface RegionDao {

	/**
	 * This method provides ArrayList of all active Regions.
	 * 
	 * @return ArrayList of Region
	 * @throws DAOException
	 */
	public ArrayList getActiveRegions() throws DAOException;

	/**
	 * This method returns region name for the regionId provided.
	 * 
	 * @param regionId
	 * @return regionName
	 * @throws DAOException
	 */
	public String getRegionName(int regionId) throws DAOException;

}