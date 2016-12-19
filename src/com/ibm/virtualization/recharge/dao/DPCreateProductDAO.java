package com.ibm.virtualization.recharge.dao;
import java.util.ArrayList;

import com.ibm.virtualization.recharge.beans.DPCreateProductFormBean;
import com.ibm.virtualization.recharge.exception.DAOException;


/*
 * Created on Aug 14, 2008
 *
 * To change the template for this generated file go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */




/**
 * @author vivek kumar
 *
 * To change the template for this generated type comment go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
public interface DPCreateProductDAO {

	public void insert(DPCreateProductFormBean prodDPBean) throws DAOException;
	
	public ArrayList getRequisitionData(int roleid,String condition) throws DAOException; 
	
	}


