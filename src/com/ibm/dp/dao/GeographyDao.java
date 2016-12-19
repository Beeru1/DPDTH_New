/*****************************************************************************\
 **
 ** Virtualization - Recharge.
 **
 ** Copyright (c) 2007-2008 IBM.
 ** All Rights Reserved
 **
 **
 \****************************************************************************/

package com.ibm.dp.dao;

import java.util.ArrayList;

import com.ibm.dp.dto.Geography;
import com.ibm.virtualization.recharge.dto.Circle;
import com.ibm.virtualization.recharge.dto.ReportInputs;
import com.ibm.virtualization.recharge.exception.DAOException;

/**
 * Interface for RegionDao Implementation
 * 
 * @author Paras
 * 
 */
public interface GeographyDao {

	/**
	 * This method provides ArrayList of all active Geographys.
	 * 
	 * @return ArrayList of Geography
	 * @throws DAOException
	 */
	public ArrayList getActiveGeographys(Integer level) throws DAOException;

	public Geography getGeography(int geographyId,Integer level) throws DAOException;
	public Geography getGeography(String geographyName,Integer level) throws DAOException;
	//public String getGeographyName(int regionId) throws DAOException;
	public ArrayList<Geography> getGeographys(String geographyCode, String geographyName,Integer level)
	throws DAOException;
	public void insertGeography(Geography geography)throws DAOException;
	public void updateGeography(Geography geography)throws DAOException;
	public ArrayList getAllGeographys(Integer level) throws DAOException;
	ArrayList getAllGeographys(ReportInputs inputDto, int lb, int ub,Integer level)
	throws DAOException; 
	//public String getActiveGeographyName(int geographyId) throws DAOException;
}