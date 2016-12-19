/*
 * Created on Nov 15, 2007
 *
 * To change the template for this generated file go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
package com.ibm.hbo.dao;
import java.util.ArrayList;
import com.ibm.hbo.exception.DAOException;
/**
 * @author Admin
 *
 * To change the template for this generated type comment go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
public interface HBOProductDAO {

public void insert(String userId,Object formBean,String master,String condition) throws DAOException;
//public ArrayList getRequisitionData(int warehouseid,int roleid,String condition) throws DAOException;
public String insert(ArrayList params) throws DAOException;
public ArrayList getRequisitionData(ArrayList params) throws DAOException;
}
