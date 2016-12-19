package com.ibm.virtualization.recharge.dao;

import java.util.ArrayList;

import com.ibm.virtualization.recharge.beans.MinReorderFormBean;
import com.ibm.virtualization.recharge.exception.DAOException;

public interface MinReorderDao {

public ArrayList getDistributorDao(String userId); 
public ArrayList getProductListDao(String userId);
public void insert (MinReorderFormBean minmreodr)throws DAOException;
//public void check(MinReorderFormBean minmreodr)throws DAOException ;
//public void updateAssign (MinReorderFormBean minmreodr)throws DAOException ;

}