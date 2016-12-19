package com.ibm.dp.service;

import java.sql.SQLException;
import java.util.ArrayList;

import com.ibm.dp.beans.DPPurchaseOrderFormBean;
import com.ibm.dp.exception.DPServiceException;
import com.ibm.virtualization.recharge.exception.DAOException;
import com.ibm.virtualization.recharge.exception.VirtualizationServiceException;


/**
 * DPPurchaseOrderService interface is implemented by DPPurchaseOrderServiceImpl for Purchase Order Requisition
 *
 * @author Rohit Kunder
 */
public interface DPPurchaseOrderService { 

	public ArrayList getBusinessCategory()throws DPServiceException;
	public ArrayList getProductName(String circleID,int selectedValue,int productTypeValue)throws DPServiceException;
	public String insertPurchaseOrder(DPPurchaseOrderFormBean dppfb,int circleID,int loginID)throws DPServiceException, DAOException;
	public ArrayList viewPOList(long accountID,int circleID,int lowerbound, int upperbound,String status)throws DPServiceException, DAOException, SQLException;
	public void cancelPORNO(int pronumber,int productno)throws DPServiceException;
	public int viewPORCount(int circleID)throws DPServiceException, DAOException, SQLException;
	public String acceptStock(String[] selectedInvNos)throws DPServiceException, DAOException, SQLException;
	public ArrayList getPrCount(long accountID) throws DPServiceException, DAOException, SQLException;
	public String getProductUnit(String prodUnit) throws DPServiceException, DAOException, SQLException;
	public String getDPQuantity(String prodID, Long loginId) throws DPServiceException, DAOException, SQLException; //Added By Mohammad Aslam
	public String getDPTotalOpenStock(Long loginId, String productId) throws DPServiceException, DAOException, SQLException;

	//Added By Harpreet
	public ArrayList getPOStatus()throws DPServiceException;
	
	public int getSecurityLoan(String distId)throws DPServiceException;
	public String getProductCost(String productId)throws DPServiceException;
	public String getTotalStock(String distId,String productId) throws DPServiceException;
	public String checkTransactionType(Long distId) throws DPServiceException;
	//Added by Neetika on 25-07-2014 for BFR2
	public String getEligibilityFlag() throws DPServiceException;
	//Added by Neetika 
	public String getProductCat(int prodid) throws DPServiceException;
	public ArrayList getProductTypeList() throws DPServiceException;
}
