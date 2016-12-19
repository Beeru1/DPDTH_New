/*
 * Created on Nov 12,2007
 *
 * To change the template for this generated file go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
package com.ibm.hbo.services;

import java.util.ArrayList;

import com.ibm.hbo.exception.HBOException;

/**
 * @author Parul
 * @version 2.0
 * 
 */
public interface HBOWarrantyService {
	
	public ArrayList getMasterList(String userId,String master,String imeino,String condition) throws HBOException;
	public String insert(String userId,Object FormBean,String master,String condition)throws HBOException;
	
	
}
 