package com.ibm.dp.service.impl;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import com.ibm.dp.common.Constants;
//import com.ibm.etools.logging.tracing.control.Connection;
import com.ibm.dp.common.Constants;
import com.ibm.virtualization.recharge.common.ExceptionCode;
import com.ibm.virtualization.recharge.common.ResourceReader;
import com.ibm.virtualization.recharge.dao.DAOFactory;
import com.ibm.virtualization.recharge.dao.RegionDao;
import com.ibm.virtualization.recharge.exception.DAOException;
import com.ibm.virtualization.recharge.exception.VirtualizationServiceException;
import com.ibm.virtualization.recharge.service.*;
import com.ibm.dp.dao.DPCreateAccountDao;
import com.ibm.dp.dao.DPPurchaseOrderDao;
import com.ibm.dp.dao.impl.DPPurchaseOrderDaoDB2;

import com.ibm.dp.dto.DPPurchaseOrderDTO;
import com.ibm.dp.exception.DPServiceException;

import com.ibm.dp.beans.DPPurchaseOrderFormBean;
import com.ibm.dp.service.DPPurchaseOrderService;
import com.ibm.virtualization.recharge.db.DBConnectionManager;

import org.apache.log4j.*;
//import org.eclipse.jst.jsp.core.internal.Logger;

/**
 * DPPurchaseOrderServiceImpl class give the defination to DPPurchaseOrderService methods for Purchase Order Requisition
 *
 */

public class DPPurchaseOrderServiceImpl implements DPPurchaseOrderService {

	Logger log = Logger.getLogger(DPPurchaseOrderServiceImpl.class.getName());
	
	public DPPurchaseOrderServiceImpl(){
		
	}

/**
 * Method return business category list
 */ 	
	public ArrayList getBusinessCategory()throws DPServiceException {
		
		java.sql.Connection connection = null;
		ArrayList bcAList = null;
 
		try {

			connection = DBConnectionManager.getDBConnection();
			DPPurchaseOrderDao DDPODao = DAOFactory	.getDAOFactory(	Integer	.parseInt(ResourceReader
											.getCoreResourceBundleValue(Constants.DATABASE_TYPE))).getDPPurchaseOrderDao(connection);

			bcAList = DDPODao.getBusinessCategoryDao();
			if (bcAList.size() == 0) {
				log.error(" No Business Category present ! ");
			}
		
		} catch (Exception ex) {
			log	.error("**********->ERROR IN FETCHING BUSINESS CATEGORY LIST [getBusinessCategory(String userId)] function of  DPPurchaseOrderServiceImpl ");
			throw new DPServiceException(ex.getMessage());
		} finally {
				/* Close the connection */
					DBConnectionManager.releaseResources(connection);
		}
		return bcAList;
	}

	/**
	 * Method return Product list
	 */ 	
	public ArrayList getProductName(String circleID,int selected,int productTypeValue)throws DPServiceException {

		java.sql.Connection connection = null;
		ArrayList productNameAList = null;

		try {
			connection = DBConnectionManager.getDBConnection();
			DPPurchaseOrderDao DDPODao = DAOFactory.getDAOFactory( Integer.parseInt(ResourceReader
											.getCoreResourceBundleValue(Constants.DATABASE_TYPE)))
					.getDPPurchaseOrderDao(connection);

			productNameAList = DDPODao.getProductNameDao(circleID,selected,productTypeValue);
			if (productNameAList.size() == 0) {
				log.error(" No Business Category present ! ");
			}
		} catch (Exception ex) {
			log	.error("**********->ERROR IN FETCHING BUSINESS CATEGORY LIST [getProductName(int selected)] function of  DPPurchaseOrderServiceImpl "+ex);
			throw new DPServiceException(ex.getMessage());
		} finally {

			/* Close the connection */
				DBConnectionManager.releaseResources(connection);
		}
		return productNameAList;
	} 

	/**
	 * Method insert the data for Purchase Order Requisition
	 */ 	
	
	public String insertPurchaseOrder(DPPurchaseOrderFormBean dppfb,int circleID,int loginID )throws DPServiceException{
		java.sql.Connection connection = null;
		String message=Constants.SUCCESS_MESSAGE;
		
		try {
			connection = DBConnectionManager.getDBConnection();
			DPPurchaseOrderDao dppod = DAOFactory.getDAOFactory( Integer.parseInt(ResourceReader
											.getCoreResourceBundleValue(Constants.DATABASE_TYPE)))
					.getDPPurchaseOrderDao(connection);
	
			DPPurchaseOrderDTO dppoDTO = new DPPurchaseOrderDTO();
			String pIDValue[] = dppfb.getPrdID();
			String[] qty = dppfb.getQty();
			String[] inHandQty = dppfb.getInHandQty();  //Added by Mohammad Aslam
			String[] qtyDP = dppfb.getQtyDP();	//Added by Mohammad Aslam
			String[] openStkQty = dppfb.getOpStkDP(); // Added by harbans singh
			
			String[] eligibleAmt = dppfb.getBalAmount1();
			String[] eligibilityQty = dppfb.getBalQty();
			String[] maxPOQTY = dppfb.getMaxPOQty();
			String[] flagNegative = dppfb.getFlag1();
			
			String comments=dppfb.getComments();
			if(pIDValue.length != 0){
				HashMap map = null;
				ArrayList list = new ArrayList();
				for(int i=0;i<pIDValue.length;i++){
					map = new HashMap();
					//map.put("BUSINESS_CAT", bgValue[i]);
					map.put("CIRCLE_ID", circleID);
					map.put("PRODUCT_CODE", pIDValue[i]);
					map.put("QUANTITY", qty[i]);
					map.put("INHANDQUANTITY", inHandQty[i]); //Added by Mohammad Aslam
					map.put("DPQUANTITY", qtyDP[i]); //Added by Mohammad Aslam
					map.put("openStkQty", openStkQty[i]); // added by harbans singh
					
					map.put("eligibleAmt", eligibleAmt[i]);
					map.put("eligibilityQty", eligibilityQty[i]);
					map.put("maxPOQTY", maxPOQTY[i]);
					map.put("flagNegative", flagNegative[i]);
					//System.out.println("eligibleAmt[i]"+eligibleAmt[i]);
					System.out.println("eligibilityQty[i]"+eligibilityQty[i]);
					//System.out.println("pIDValue[i]"+pIDValue[i]);
					//System.out.println("maxPOQTY[i]"+maxPOQTY[i]);
					//map.put("eligibleAmt", eligibleAmt);
					list.add(map);
				}
				message = dppod.insertPurchaseOrder(list,loginID,comments);
					
			}else{
				log.error("**pIDValue FROM createPor "+pIDValue);
						
			}
		} catch (Exception ex) {
			ex.printStackTrace();
			log.error("**********->ERROR IN INSERTING TO PURCHASE ORDER [ insertPurchaseOrder ] function of  DPPurchaseOrderServiceImpl ");
			message = "error";
			//throw new DPServiceException(ex);
			throw new DPServiceException(ex.getMessage());
		}
	 finally {
				/* Close the connection */
			DBConnectionManager.releaseResources(connection);
		}
		return message;
	}


/**
 * Method return Arraylist having details information about purchase order requisition
 */ 	
	public ArrayList viewPOList(long accountID,int circleID,int lowerbound, int upperbound,String status)throws DPServiceException, DAOException, SQLException {
	
		java.sql.Connection connection = null;
		ArrayList<DPPurchaseOrderDTO> POList = null;
		
		try {
			
			connection = DBConnectionManager.getDBConnection();
			DPPurchaseOrderDao dppod = DAOFactory.getDAOFactory( Integer.parseInt(ResourceReader
					.getCoreResourceBundleValue(Constants.DATABASE_TYPE))).getDPPurchaseOrderDao(connection);
			POList = dppod.viewPODetails(accountID,circleID,lowerbound,upperbound,status);
			
		} catch (Exception e) {
			e.printStackTrace();
			log.info("***ERROR IN METHOD ViewPOList() DPPurchaseOrderServiceImpl class **********"+e);
			throw new DPServiceException(e.getMessage());
		}
		 finally {
				/* Close the connection */
			DBConnectionManager.releaseResources(connection);
		}
		return POList;

	}

/**
 * Method cancel the purchase order requisition
 */
	public void cancelPORNO(int pronumber,int productno)throws DPServiceException {
		
		java.sql.Connection connection = null;
			 
		try {
			 connection = DBConnectionManager.getDBConnection();
			 DPPurchaseOrderDao dppod = DAOFactory.getDAOFactory( Integer.parseInt(ResourceReader
						.getCoreResourceBundleValue(Constants.DATABASE_TYPE))).getDPPurchaseOrderDao(connection);
				
				 dppod.caneclPORNODAO(pronumber,productno);
				 DPPurchaseOrderFormBean dppfb = new	 DPPurchaseOrderFormBean();
				 
		} catch (Exception ex) {
			log	.error("**->CANCELINGO PURCHASE ORDER NO. [ cancelPORNO ] function of  DPPurchaseOrderServiceImpl ");
			throw new DPServiceException(ex.getMessage());
		}
		finally {
			/* Close the connection */
			DBConnectionManager.releaseResources(connection);
		}
		}

	/**
	 * Method fetch the count of record present in purchase order requisition table
	 */

	
	public int viewPORCount(int circleID)throws DPServiceException, DAOException, SQLException
	 {
		java.sql.Connection connection  = null;
		int Count=0;
		try {
			
			 connection = DBConnectionManager.getDBConnection();
			 DPPurchaseOrderDao dppod = DAOFactory.getDAOFactory( Integer.parseInt(ResourceReader
				.getCoreResourceBundleValue(Constants.DATABASE_TYPE))).getDPPurchaseOrderDao(connection);
				 Count = dppod.viewPORCount(circleID);
		}catch (Exception e) {
			log	.error("**->viewPORCount function of  DPPurchaseOrderServiceImpl "+e);
			throw new DPServiceException(e.getMessage());
		}
		 finally {
				/* Close the connection */
			DBConnectionManager.releaseResources(connection);
		}
		return Count;	
	}
	public String acceptStock(String[] selectedInvNos)throws DPServiceException, DAOException, SQLException{
		java.sql.Connection connection  = null;
		String message = "success";
		try {
			
			 connection = DBConnectionManager.getDBConnection();
			 DPPurchaseOrderDao purchaseDao = DAOFactory.getDAOFactory( Integer.parseInt(ResourceReader
				.getCoreResourceBundleValue(Constants.DATABASE_TYPE))).getDPPurchaseOrderDao(connection);
			 purchaseDao.acceptStock(selectedInvNos);
		}catch (Exception e) {
			message = "error";
			log	.error("**->viewPORCount function of  DPPurchaseOrderServiceImpl "+e);
			throw new DPServiceException(e.getMessage());
		}
		 finally {
				/* Close the connection */
			DBConnectionManager.releaseResources(connection);
		}
		return message;
	}
	public ArrayList getPrCount(long accountID) throws DPServiceException, DAOException, SQLException{

		java.sql.Connection connection  = null;
		ArrayList countList = new ArrayList();
		try {
			
			connection = DBConnectionManager.getDBConnection();
			DPPurchaseOrderDao dppod = DAOFactory.getDAOFactory( Integer.parseInt(ResourceReader
					.getCoreResourceBundleValue(Constants.DATABASE_TYPE))).getDPPurchaseOrderDao(connection);
			countList = dppod.getPrCount(accountID);
				
		}catch (Exception e) {
			log	.error("**->viewPORCount function of  DPPurchaseOrderServiceImpl "+e);
			throw new DPServiceException(e.getMessage());
		}
		 finally {
				/* Close the connection */
			DBConnectionManager.releaseResources(connection);
		}
		return countList;
	}
	
	public String getProductUnit(String prodUnit) throws DPServiceException, DAOException, SQLException{

		java.sql.Connection connection  = null;
		String productUnit = "";
		try {
			connection = DBConnectionManager.getDBConnection();
			DPPurchaseOrderDao dppod = DAOFactory.getDAOFactory( Integer.parseInt(ResourceReader
					.getCoreResourceBundleValue(Constants.DATABASE_TYPE))).getDPPurchaseOrderDao(connection);
			productUnit = dppod.getProductUnit(prodUnit);
				
		}catch (Exception e) {
			log	.error("**->viewPORCount function of  DPPurchaseOrderServiceImpl "+e);
			throw new DPServiceException(e.getMessage());
		}
		 finally {
				/* Close the connection */
			DBConnectionManager.releaseResources(connection);
		}
		return productUnit;
	}
	
	//Added by Mohammad Aslam
	public String getDPQuantity(String prodID, Long loginId) throws DPServiceException, DAOException, SQLException {
		java.sql.Connection connection  = null;
		String quantityDP = "";
		try {
			connection = DBConnectionManager.getDBConnection();
			DPPurchaseOrderDao dppod = DAOFactory.getDAOFactory( Integer.parseInt(ResourceReader
					.getCoreResourceBundleValue(Constants.DATABASE_TYPE))).getDPPurchaseOrderDao(connection);
			quantityDP = dppod.getDPQuantity(prodID, loginId);
				
		}catch (Exception e) {
			log	.error("**->viewPORCount function of  DPPurchaseOrderServiceImpl "+e);
			throw new DPServiceException(e.getMessage());
		}
		 finally {
				/* Close the connection */
			DBConnectionManager.releaseResources(connection);
		}
		return quantityDP;
	}
	
	//Added by Harabns Singh
	public String getDPTotalOpenStock(Long loginId, String productId) throws DPServiceException, DAOException, SQLException 
	{
		java.sql.Connection connection  = null;
		String openStockDp = "0";
		try 
		{
			connection = DBConnectionManager.getDBConnection();
			DPPurchaseOrderDao dppod = DAOFactory.getDAOFactory( Integer.parseInt(ResourceReader
					.getCoreResourceBundleValue(Constants.DATABASE_TYPE))).getDPPurchaseOrderDao(connection);
			openStockDp = dppod.getDPTotalOpenStock(loginId, productId);
				
		}catch (Exception e) {
			log	.error("**->viewPORCount function of  getDPTotalOpenStock "+e);
			throw new DPServiceException(e.getMessage());
		}
		 finally {
				/* Close the connection */
			DBConnectionManager.releaseResources(connection);
		}
		return openStockDp;
	}

	
	//Added By Harpreet
	
	public ArrayList getPOStatus()throws DPServiceException{
		
		java.sql.Connection connection = null;
		ArrayList posList = null;
 
		try {

			connection = DBConnectionManager.getDBConnection();
			DPPurchaseOrderDao DDPODao = DAOFactory	.getDAOFactory(	Integer	.parseInt(ResourceReader
											.getCoreResourceBundleValue(Constants.DATABASE_TYPE))).getDPPurchaseOrderDao(connection);

			posList = DDPODao.getPOStatus();
			
		
		} catch (Exception ex) {
					
			log	.error("**********->ERROR IN FETCHING PO STATUS LIST DPPurchaseOrderServiceImpl ");
			throw new DPServiceException(ex.getMessage());
		} finally {
				/* Close the connection */
					DBConnectionManager.releaseResources(connection);
		}
		return posList;
		
		
	}

	
	public int getSecurityLoan(String distId) throws DPServiceException
	{
		java.sql.Connection connection  = null;
		int securityAndLoan = 0;
		try 
		{
			connection = DBConnectionManager.getDBConnection();
			DPPurchaseOrderDao dppod = DAOFactory.getDAOFactory( Integer.parseInt(ResourceReader
					.getCoreResourceBundleValue(Constants.DATABASE_TYPE))).getDPPurchaseOrderDao(connection);
			securityAndLoan = dppod.getSecurityLoan(distId);
				
		}catch (Exception e) {
			log	.error("**->viewPORCount function of  getDPTotalOpenStock "+e);
			throw new DPServiceException(e.getMessage());
		}
		 finally {
				/* Close the connection */
			DBConnectionManager.releaseResources(connection);
		}
		return securityAndLoan;
	}
	
	public String getProductCost(String productId) throws DPServiceException
	{
		java.sql.Connection connection  = null;
		String productCost = "0";
		try 
		{
			connection = DBConnectionManager.getDBConnection();
			DPPurchaseOrderDao dppod = DAOFactory.getDAOFactory( Integer.parseInt(ResourceReader
					.getCoreResourceBundleValue(Constants.DATABASE_TYPE))).getDPPurchaseOrderDao(connection);
			productCost = dppod.getProductCost(productId);
				
		}catch (Exception e) {
			log	.error("**->viewPORCount function of  getDPTotalOpenStock "+e);
			throw new DPServiceException(e.getMessage());
		}
		 finally {
				/* Close the connection */
			DBConnectionManager.releaseResources(connection);
		}
		return productCost;
	}

	public String getTotalStock(String distId,String productId)throws DPServiceException
	{
	java.sql.Connection connection  = null;
	String totalCost = "0";
	try 
	{
		connection = DBConnectionManager.getDBConnection();
		DPPurchaseOrderDao dppod = DAOFactory.getDAOFactory( Integer.parseInt(ResourceReader
				.getCoreResourceBundleValue(Constants.DATABASE_TYPE))).getDPPurchaseOrderDao(connection);
		totalCost = dppod.getTotalStock(distId,productId);
			
	}catch (Exception e) {
		log	.error("**->viewPORCount function of  getDPTotalOpenStock "+e);
		throw new DPServiceException(e.getMessage());
	}
	 finally {
			/* Close the connection */
		DBConnectionManager.releaseResources(connection);
	}
	return totalCost;
	}
	
	
	public String checkTransactionType(Long distId)throws DPServiceException
	{
	java.sql.Connection connection  = null;
	String totalCost = "0";
	try 
	{
		connection = DBConnectionManager.getDBConnection();
		DPPurchaseOrderDao dppod = DAOFactory.getDAOFactory( Integer.parseInt(ResourceReader
				.getCoreResourceBundleValue(Constants.DATABASE_TYPE))).getDPPurchaseOrderDao(connection);
		totalCost = dppod.checkTransactionType(distId);
			
	}catch (Exception e) {
		log	.error("**->viewPORCount function of  checkTransactionType "+e);
		throw new DPServiceException(e.getMessage());
	}
	 finally {
			/* Close the connection */
		DBConnectionManager.releaseResources(connection);
	}
	return totalCost;
	}
	
	//Added by Neetika for Eligibility 25-07-2014

	public String getEligibilityFlag()throws DPServiceException
	{
	java.sql.Connection connection  = null;
	String flag = "N";
	try 
	{
		connection = DBConnectionManager.getDBConnection();
		DPPurchaseOrderDao dppod = DAOFactory.getDAOFactory( Integer.parseInt(ResourceReader
				.getCoreResourceBundleValue(Constants.DATABASE_TYPE))).getDPPurchaseOrderDao(connection);
		flag = dppod.getEligibilityFlag();
			
	}catch (Exception e) {
		log	.error("**->viewPORCount function of  checkTransactionType "+e);
		throw new DPServiceException(e.getMessage());
	}
	 finally {
			/* Close the connection */
		DBConnectionManager.releaseResources(connection);
	}
	return flag;
	}
	//Neetika
	public String getProductCat(int productid)throws DPServiceException
	{
	java.sql.Connection connection  = null;
	String cat = "";
	try 
	{
		connection = DBConnectionManager.getDBConnection();
		DPPurchaseOrderDao dppod = DAOFactory.getDAOFactory( Integer.parseInt(ResourceReader
				.getCoreResourceBundleValue(Constants.DATABASE_TYPE))).getDPPurchaseOrderDao(connection);
		cat = dppod.getProdCategory(productid);
			
	}catch (Exception e) {
		log	.error("**->viewPORCount function of  checkTransactionType "+e);
		throw new DPServiceException(e.getMessage());
	}
	 finally {
			/* Close the connection */
		DBConnectionManager.releaseResources(connection);
	}
	return cat;
	}

	@Override
	public ArrayList getProductTypeList() throws DPServiceException {
		java.sql.Connection connection = null;
		ArrayList typeList = null;
 
		try {

			connection = DBConnectionManager.getDBConnection();
			DPPurchaseOrderDao DDPODao = DAOFactory	.getDAOFactory(	Integer	.parseInt(ResourceReader
											.getCoreResourceBundleValue(Constants.DATABASE_TYPE))).getDPPurchaseOrderDao(connection);

			typeList = DDPODao.getProductTypeListDao();
			if (typeList.size() == 0) {
				log.error(" No Product type list present ! ");
			}
		
		} catch (Exception ex) {
			log	.error("**********->ERROR IN FETCHING PRODUCT TYPE LIST [getProductTypeList(String userId)] function of  DPPurchaseOrderServiceImpl ");
			throw new DPServiceException(ex.getMessage());
		} finally {
				/* Close the connection */
					DBConnectionManager.releaseResources(connection);
		}
		return typeList;

	}
}
	