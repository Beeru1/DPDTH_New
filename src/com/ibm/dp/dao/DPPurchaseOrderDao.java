package com.ibm.dp.dao;
import java.sql.SQLException;
import java.util.*;

import com.ibm.dp.beans.DPPurchaseOrderFormBean;
import com.ibm.dp.exception.DPServiceException;
import com.ibm.virtualization.recharge.dto.ReportInputs;
import com.ibm.virtualization.recharge.exception.DAOException;
 
/**
 * DPPurchaseOrderDao class is interface for which is implemented by DPPurchaseOrderDaoDB2 class for Purchase Order Requisition
 *
 * @author Rohit Kunder
 */
public interface DPPurchaseOrderDao {
	
	public int getSecurityLoan(String distId) throws DAOException, DPServiceException;
	
	public String getProductCost(String productId) throws DAOException, DPServiceException;
	
	public ArrayList getBusinessCategoryDao() throws DAOException, DPServiceException;
	
	public ArrayList getProductNameDao(String circleId,int selected,int productTypeValue) throws DAOException;
	
	public String insertPurchaseOrder(ArrayList list,int loginID, String comments)throws DAOException;

	public ArrayList viewPODetails(long accountID,int circleID,int lowerbound, int upperbound,String status) throws DAOException;
	
	public int viewPORCount(int circleID)throws DAOException, SQLException; 

	public void caneclPORNODAO(int pronumber,int productno) throws DAOException;
	
	public void acceptStock(String[] selectedInvNos) throws DAOException;
	
	public ArrayList getPrCount(long accountID) throws DAOException,SQLException;
	
	public String getProductUnit(String prodUnit) throws DAOException,SQLException;
	
	public String getDPQuantity(String prodID, Long loginId) throws DAOException, SQLException; //Added by Mohammad Aslam
	
	public String getDPTotalOpenStock(Long loginId, String productId) throws DAOException, SQLException; // Added by harbans
	
	public ArrayList getPOStatus() throws DAOException, DPServiceException;//Added By Harpreet
	
	public String getTotalStock(String distId,String productId) throws DAOException, DPServiceException;
	public String checkTransactionType(Long distId) throws DAOException, DPServiceException;
	public int checkFNFDone(Long distId) throws DAOException, DPServiceException;
	public int checkTransactionTypeReverse(Long distId) throws DAOException ;
	//Added by Neetika for BFR 2 of Upload eligibility on 25-07-2014
	public String getEligibilityFlag() throws DAOException;
	public String getProdCategory(int prod) throws DAOException;

	public ArrayList getProductTypeListDao() throws DAOException;
}
