/*
 * Created on Nov 13, 2007
 *
 * To change the template for this generated file go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
package com.ibm.hbo.services.impl;

import java.util.ArrayList;

import org.apache.log4j.Logger;

import com.ibm.hbo.dao.HBOMasterDAO;
import com.ibm.hbo.dao.HBOWarrantyDAO;
import com.ibm.hbo.dao.impl.HBOMasterDAOImpl;
import com.ibm.hbo.dao.impl.HBOWarrantyDAOImpl;
import com.ibm.hbo.exception.DAOException;
import com.ibm.hbo.exception.HBOException;
import com.ibm.hbo.services.HBOWarrantyService;

/**
 * @author Parul
 * @version 2.0
 * 
 */
public class HBOWarrantyServiceImpl implements HBOWarrantyService {

	public static Logger logger = Logger.getRootLogger();

	
	/**
	 * This method returns the Warehouse List depending upon the role of the logged_in_userID
	 * @param userID
	 * @return
	 * @throws HBOException
	 */
	public ArrayList getMasterList(String userId,String master,String imeino,String condition) throws HBOException
	{
		HBOWarrantyDAO warrantyDAO = new HBOWarrantyDAOImpl();
		ArrayList masterList = null;
		try{
			masterList = warrantyDAO.findByPrimaryKey(userId,master,imeino,condition);		
			} catch (Exception e) {
			if (e instanceof DAOException) {
				logger.error("DAOException occured in getMasterList():" + e.getMessage());
				throw new HBOException(e.getMessage());
			} else {
				logger.error("Exception occured in getMasterList():" + e.getMessage());
			}
		}
		return masterList;
	}	
	
	public String insert(String userId,Object formBean,String master,String condition) throws HBOException {
		String message="success";
	
		HBOWarrantyDAO warrantyDAO = new HBOWarrantyDAOImpl();
		try{
			
			warrantyDAO.insert(userId,formBean,master,condition);
		} catch (Exception e) {
			if (e instanceof DAOException) {
				logger.error("DAOException occured in getMasterist():" + e.getMessage());
				message="failure"; // Changes on 8/5/2008 by sachin
				//throw new HBOException(e.getMessage());
			} else {
				logger.error("Exception occured in getMasterList():" + e.getMessage());
			}
			message="failure";
		}
		return message;
	}


	

}
