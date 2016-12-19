package com.ibm.virtualization.recharge.service;

import java.util.ArrayList;

import com.ibm.virtualization.recharge.beans.MinReorderFormBean;
import com.ibm.virtualization.recharge.exception.DAOException;
import com.ibm.virtualization.recharge.exception.VirtualizationServiceException;

public interface MinmreorderService {
	
	public ArrayList getProductList(String userId);
	
	public ArrayList getDistributor(String userId);
	public String insert(MinReorderFormBean minmreodr)throws  NumberFormatException, VirtualizationServiceException,DAOException;
//	public String Check(MinReorderFormBean minmreodr)throws  NumberFormatException, VirtualizationServiceException,DAOException;
//	public String update(MinReorderFormBean minmreodr)throws  NumberFormatException, VirtualizationServiceException,DAOException;

}
