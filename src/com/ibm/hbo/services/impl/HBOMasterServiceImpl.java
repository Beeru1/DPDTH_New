/*
 * Created on Nov 13, 2007
 *
 * To change the template for this generated file go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
package com.ibm.hbo.services.impl;

import java.util.ArrayList;

import org.apache.log4j.Logger;

import com.ibm.hbo.common.HBOUser;
import com.ibm.hbo.dao.HBOMasterDAO;
import com.ibm.hbo.dao.impl.HBOMasterDAOImpl;
import com.ibm.hbo.dto.HBOUserBean;
import com.ibm.hbo.exception.DAOException;
import com.ibm.hbo.exception.HBOException;
import com.ibm.hbo.services.HBOMasterService;

/**
 * @author Parul
 * @version 2.0
 * 
 */
public class HBOMasterServiceImpl implements HBOMasterService {

	private static Logger logger = Logger.getLogger(HBOMasterServiceImpl.class
			.getName());
	
	
	
	public ArrayList getMasterList(String userId,int master,String condition) throws HBOException
	{
		HBOMasterDAO masterDAO = new HBOMasterDAOImpl();
		ArrayList masterList = null;		
		try{
			masterList = masterDAO.findByPrimaryKey(userId,master,condition);				
			
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
	
	public ArrayList getChange(ArrayList arr)throws HBOException
	{
		HBOMasterDAO masterDAO = new HBOMasterDAOImpl();
		ArrayList getChangeValues = null;
		try{
			getChangeValues = masterDAO.getChange(arr);
		} catch (Exception e) {
			if (e instanceof DAOException) {
				logger.error("DAOException occured in getChange():" + e.getMessage());
				//throw new HBOException(e.getMessage());
			} else {
				logger.error("Exception occured in getChange):" + e.getMessage());
			}
		}

		return getChangeValues;
	}
	
	/* (non-Javadoc)
	 * @see com.ibm.hbo.services.HBOMasterService#getMasterList(com.ibm.hbo.dto.HBOUserBean, java.lang.String, java.lang.String)
	 */
	public ArrayList getMasterList(HBOUser obj) throws HBOException {
		HBOMasterDAO masterDAO = new HBOMasterDAOImpl();
		ArrayList masterList = null;
		try{
			masterList = masterDAO.findByPrimaryKey(obj);	
			logger.info("masterList-----------"+masterList.size());		
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
	/**********End Code  by parul********************/
	public ArrayList getMasterList(String userId,String master,String condition) throws HBOException
	{
		HBOMasterDAO masterDAO = new HBOMasterDAOImpl();
		ArrayList masterList = null;
		try{
			masterList = masterDAO.findByPrimaryKey(userId,master,condition);				
			
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
	/*public String getCircle(HBOUser obj) throws HBOException
	{
		HBOMasterDAO masterDAO = new HBOMasterDAOImpl();
		String crcleId =null;
		try{
			crcleId = masterDAO.findByPrimaryKey(obj);
			
			} catch (Exception e) {
			if (e instanceof DAOException) {
				logger.error("DAOException occured in getMasterList():" + e.getMessage());
				throw new HBOException(e.getMessage());
			} else {
				logger.error("Exception occured in getMasterList():" + e.getMessage());
			}
		}
		return crcleId;
	}	*/
	public String insert(String userId,Object formBean,String master,String condition) throws HBOException {
		String message="success";
	HBOMasterDAO masterDAO = new HBOMasterDAOImpl();
		try{
				masterDAO.insert(userId,formBean,master,condition);
		} catch (Exception e) {
			if (e instanceof DAOException) {
				logger.error("DAOException occured in getMasterist():" + e.getMessage());
				message="failure";// chnages done on 07/05/2008
				logger.error(""+e.fillInStackTrace());
				//throw new HBOException(e.getMessage()); // chnages done on 07/05/2008
			} else {
				logger.error("Exception occured in getMasterList():" + e.getMessage());
			}
			message="failure";
		}
		
		return message;
	}


	/**
	 * @author - Aditya
	 * @param userId
	 * @param formBean
	 * @param master
	 * @param condition
	 * @return
	 * @throws HBOException
	 */
	public String insertMRL(String userId,Object formBean,String master,String condition) throws HBOException {
		String message="success";
		
		HBOMasterDAO masterDAO = new HBOMasterDAOImpl();
		try{
			masterDAO.insertMRL(userId,formBean,master,condition);
		} catch (Exception e) {
			if (e instanceof DAOException) {
				logger.error("DAOException occured in getWarehouseList():" + e.getMessage());
				message="failure"; // Chnagen on 8/05/2008 by sachin
				//throw new HBOException(e.getMessage());
			} else {
				logger.error("Exception occured in getWarehouseList():" + e.getMessage());
			}
			message="failure";
		}
		return message;
	}
	public int getProjectionQty(ArrayList arr) throws HBOException{
		HBOMasterDAO masterDAO = new HBOMasterDAOImpl();
		int projectedQty=0;
		try{
			projectedQty = masterDAO.getProjectionQty(arr);
		} catch (Exception e) {
			if (e instanceof DAOException) {
				logger.error("DAOException occured in getProjectionQty():" + e.getMessage());
				//throw new HBOException(e.getMessage());
			} else {
				logger.error("Exception occured in getProjectionQty():" + e.getMessage());
			}
		}
	return projectedQty ;
	}

	public ArrayList getChange(String Id, String condition, String condition2, String string, Object FormBean) throws HBOException {
		// TODO Auto-generated method stub
		return null;
	}

	public String getCircle(HBOUserBean hboUserBean) throws HBOException {
		// TODO Auto-generated method stub
		return null;
	}

	public ArrayList getAccountList(String userIdFrom,String conditionA,String conditionB) throws HBOException {

		HBOMasterDAO masterDAO = new HBOMasterDAOImpl();
		ArrayList masterList = null;
		try{
			masterList = masterDAO.getAccountList(userIdFrom,conditionA,conditionB);				
			
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
}