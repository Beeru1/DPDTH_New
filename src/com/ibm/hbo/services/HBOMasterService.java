/*
 * Created on Nov 12,2007
 *
 * To change the template for this generated file go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
package com.ibm.hbo.services;

import java.util.ArrayList;

import com.ibm.hbo.common.HBOUser;
import com.ibm.hbo.dto.HBOUserBean;
import com.ibm.hbo.exception.HBOException;

/**
 * @author Parul
 * @version 2.0
 * 
 */
public interface HBOMasterService {
	
	public ArrayList getMasterList(String userId,String master,String condition) throws HBOException;
	public ArrayList getMasterList(String userId,int master,String condition) throws HBOException;
	//public ArrayList getMasterList(HBOUserBean	hboUserBean,String master,String condition) throws HBOException;
	public ArrayList getMasterList(HBOUser obj) throws HBOException;
	
	public String insert(String userId,Object FormBean,String master,String condition)throws HBOException;
	// Created by Aditya
	public String insertMRL(String userId,Object formBean,String master,String condition) throws HBOException;
	public ArrayList getChange(String Id, String condition, String condition2,String string, Object FormBean) throws HBOException;
	public ArrayList getChange(ArrayList arr)throws HBOException;
	public String getCircle(HBOUserBean hboUserBean) throws HBOException;
	public int getProjectionQty(ArrayList params)throws HBOException;
	public ArrayList getAccountList(String userIdFrom,String conditionA,String conditionB)throws HBOException;

}
 