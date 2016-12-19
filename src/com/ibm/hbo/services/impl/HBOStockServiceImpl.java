/*
 * Created on Nov 13, 2007
 *
 * To change the template for this generated file go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
package com.ibm.hbo.services.impl;

import java.sql.Connection;
import java.util.ArrayList;

import org.apache.log4j.Logger;

import com.ibm.hbo.common.HBOConstants;
import com.ibm.hbo.common.HBOUser;
import com.ibm.hbo.dao.HBOProductDAO;
import com.ibm.hbo.dao.HBOStockDAO;
import com.ibm.hbo.dao.impl.HBOStockDAOImpl;
import com.ibm.hbo.dto.DistStockDTO;
import com.ibm.hbo.dto.HBOStockDTO;
import com.ibm.hbo.exception.DAOException;
import com.ibm.hbo.exception.HBOException;
import com.ibm.hbo.services.HBOStockService;
import com.ibm.virtualization.framework.bean.ChannelType;
import com.ibm.virtualization.recharge.authorization.AuthorizationFactory;
import com.ibm.virtualization.recharge.common.Constants;
import com.ibm.virtualization.recharge.common.ResourceReader;
import com.ibm.virtualization.recharge.dao.DAOFactory;
import com.ibm.virtualization.recharge.db.DBConnectionManager;
import com.ibm.virtualization.recharge.service.AuthorizationInterface;

/**
 * @author Parul
 *
 * To change the template for this generated type comment go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
/**
 * @author Parul
 * @version 2.0
 * 
 */
public class HBOStockServiceImpl implements HBOStockService {

	public static Logger logger = Logger.getRootLogger();

	/**
		 * This method returns the Available Sim Stock depending upon the circle of the logged_in_userID
		 * @param userID
		 * @return
		 * @throws HBOException
		 */
	public int getAvlStock(String userId,String warehouseId) throws HBOException {
		int AvlailableStock = 0;
		Connection con = null;
		HBOStockDAO stockDAO = new HBOStockDAOImpl(con);
		try {
			AvlailableStock = stockDAO.findValues(userId,warehouseId);
		} catch (Exception e) {
			if (e instanceof DAOException) {
				logger.error(
					"DAOException occured in getAvlSimStock():"
						+ e.getMessage());
				throw new HBOException(e.getMessage());
			} else {
				logger.error(
					"Exception occured in getAvlSimStock():" + e.getMessage());
			}
		}
		return AvlailableStock;
	}
	
	
	public String AssignSimStock(HBOUser obj,HBOStockDTO AssSimStkDto) throws HBOException {
		Connection con = null;
		HBOStockDAO stockDAO = new HBOStockDAOImpl(con);
		String message = "success";
		try {
			logger.info("Inside HBOStockServiceImpl : AssignSimStock()");
			stockDAO.InsertAssignedSimStock(obj,AssSimStkDto);
			message = "success";
		} catch (Exception e) {
			if (e instanceof DAOException) {
				logger.error(
					"DAOException occured in AssignSimStock():"
						+ e.getMessage());
				throw new HBOException(e.getMessage());
			} else {
				logger.error(
					"Exception occured in AssignSimStock():" + e.getMessage());
			}
			message = "failure";
		}
		return message;
	}
	/* (non-Javadoc)
	 * @see com.ibm.hbo.services.HBOStockService#getBatchList()
	 */
	 
	 //Created by Parul
	public ArrayList getBatchList(String UserID, String warehouseId,String Cond)
		throws HBOException {
		ArrayList arrBatchList = new ArrayList();
		Connection con = null;
		HBOStockDAO stockDAO = new HBOStockDAOImpl(con);
		try {
			logger.info("Inside3   getBatchList :Cond " + Cond);
			arrBatchList = stockDAO.findByPrimaryKey(UserID,warehouseId, Cond);

		} catch (Exception e) {
			if (e instanceof DAOException) {
				logger.error(
					"DAOException occured in getBatchList(String UserID,String Cond):"
						+ e.getMessage());
				throw new HBOException(e.getMessage());
			} else {
				logger.error(
					"Exception occured in getBatchList(String UserID,String Cond):"
						+ e.getMessage());
			}
		}
		return arrBatchList;
	}
//-End Method
	/* (non-Javadoc)
	 * @see com.ibm.hbo.services.HBOStockService#VerifySimStock(com.ibm.hbo.dto.HBOStockDTO)
	 */
	public void VerifySimStock(HBOUser obj,HBOStockDTO simStkDto) throws HBOException {
		Connection con = null;
		HBOStockDAO stockDAO = new HBOStockDAOImpl(con);
		try {
					logger.info("Inside HBOStockServiceImpl : VerifySimStock()");
					stockDAO.UpdateSimStock(obj,simStkDto);

				} catch (Exception e) {
					if (e instanceof DAOException) {
						logger.error(
							"DAOException occured in AssignSimStock():"
								+ e.getMessage());
						throw new HBOException(e.getMessage());
					} else {
						logger.error(
							"Exception occured in AssignSimStock():" + e.getMessage());
					}
				}
	}

	/* (non-Javadoc)
	 * @see com.ibm.hbo.services.HBOStockService#bundleStock(com.ibm.hbo.dto.HBOUserBean, java.lang.Object, java.lang.String, java.lang.String)
	 */
	public String bundleStock(HBOUser obj,Object FormBean,String master,String condition) throws HBOException {
			String message="success";
			//logger.info("In Insert2222");
			Connection con = null;
			HBOStockDAO stockDAO = new HBOStockDAOImpl(con);
			try{			
				stockDAO.bundleStock(obj,FormBean,master,condition);
			} catch (Exception e) {
				if (e instanceof DAOException) {
					logger.error("DAOException occured in getMasterist():" + e.getMessage());
					message="failure"; // changes on 8/5/2008 by sachin
					//throw new HBOException(e.getMessage()); //changes on 8/5/2008 by sachin
				} else {
					logger.error("Exception occured in getMasterList():" + e.getMessage());
				}
				message="failure";
			}
			return message;
		}




	/* Created by Parul
	 * (non-Javadoc)
	 * @see com.ibm.hbo.services.HBOStockService#getBatchDetails(java.lang.String)
	 * This function will call the StockDAo method to the Sim list in a batch
	 */
	public ArrayList getSimBatchDetails(String batch_no) throws HBOException {
			ArrayList arrBatchDetails = new ArrayList();
			Connection con = null;
			HBOStockDAO stockDAO = new HBOStockDAOImpl(con);
					try {
						logger.info("Inside3   getBatchDetails :batch_no " + batch_no);
						arrBatchDetails = stockDAO.findByPrimaryKey(batch_no,"BatchDetails");

					} catch (Exception e) {
						if (e instanceof DAOException) {
							logger.error(
								"DAOException occured in getBatchList(String UserID,String Cond):"
									+ e.getMessage());
							throw new HBOException(e.getMessage());
						} else {
							logger.error(
								"Exception occured in getBatchList(String UserID,String Cond):"
									+ e.getMessage());
						}
					}
					return arrBatchDetails;
		
		} 
	 
	public ArrayList getBatchDetails(String batch_no) throws HBOException {
		ArrayList arrBatchDetails = new ArrayList();
			Connection con=null;	
			HBOStockDAO stockDAO = new HBOStockDAOImpl(con);
				try {
					arrBatchDetails = stockDAO.getBatchDetailList(batch_no);
				} catch (Exception e) {
					if (e instanceof DAOException) {
						logger.error(
							"DAOException occured in getBatchList(String UserID,String Cond):"
								+ e.getMessage());
						throw new HBOException(e.getMessage());
					} else {
						logger.error(
							"Exception occured in getBatchList(String UserID,String Cond):"
								+ e.getMessage());
					}
				}
				return arrBatchDetails;
		
	}

	public ArrayList getBundledStockDetails(String requestId) throws HBOException {
			ArrayList arrBundledDetails = new ArrayList();
			Connection con=null;	
			HBOStockDAO stockDAO = new HBOStockDAOImpl(con);
					try {
						arrBundledDetails = stockDAO.findByPrimaryKey(requestId,HBOConstants.BUNDLED_DETAILS);
					} catch (Exception e) {
						if (e instanceof DAOException) {
							logger.error(
								"DAOException occured in getBatchList(String UserID,String Cond):"
									+ e.getMessage());
							throw new HBOException(e.getMessage());
						} else {
							logger.error(
								"Exception occured in getBatchList(String UserID,String Cond):"
									+ e.getMessage());
						}
					}
					return arrBundledDetails;		
		}


	/* (non-Javadoc)
	 * @see com.ibm.hbo.services.HBOStockService#getBundledStock(com.ibm.hbo.dto.HBOUserBean)
	 */
	public ArrayList getBundledStock(HBOUser hboUserBean,String cond) throws HBOException {
				ArrayList arrBundledStock = new ArrayList();
				Connection con=null;	
				HBOStockDAO stockDAO = new HBOStockDAOImpl(con);
				try {
					
					arrBundledStock = stockDAO.findByPrimaryKey(hboUserBean,cond);

				} catch (Exception e) {
					if (e instanceof DAOException) {
						logger.error(
							"DAOException occured in getBundledStock(HBOUserBean hboUserBean):"	+ e.getMessage());
						throw new HBOException(e.getMessage());
					} else {
						logger.error("Exception occured in getBundledStock(HBOUserBean hboUserBean):"+ e.getMessage());
					}
				}
				return arrBundledStock;
	}
	public String acceptRejectStock(HBOUser userBean, HBOStockDTO stockDTO) throws HBOException{
		String path = "success";
//		HBOStockDAO stockDAO = new HBOStockDAOImpl();
		Connection con = null;
		try {
			con = DBConnectionManager.getDBConnection();
			HBOStockDAO stockDAO = DAOFactory.getDAOFactory(
	  		Integer.parseInt(ResourceReader.getCoreResourceBundleValue(Constants.DATABASE_TYPE))).getStockDao(con);
			stockDAO.acceptRejectProdStock(userBean, stockDTO);		
			}
			 catch (Exception e) {
						if (e instanceof DAOException) {
							logger.error(
								"DAOException occured in AssignSimStock():"
									+ e.getMessage());
							path = "failure";	// Changes on 8/5/2008 by sachin
							//throw new HBOException(e.getMessage());
						} else {
							logger.error(
								"Exception occured in AssignSimStock():" + e.getMessage());
						}
						path = "failure";
					}

		return path;
	}

	public ArrayList getAssignedStock(int warehouseId, String condition) throws HBOException{
		Connection con=null;	
		HBOStockDAO stockDAO = new HBOStockDAOImpl(con);
		ArrayList stockList = new ArrayList();
		try {
								 
			stockList = stockDAO.getAssignStockDetails(warehouseId,condition);
			
						} catch (Exception e) {
							if (e instanceof DAOException) {
								logger.error(
									"DAOException occured in AssignSimStock():"
										+ e.getMessage());
								throw new HBOException(e.getMessage());
							} else {
								logger.error(
									"Exception occured in AssignSimStock():" + e.getMessage());
							}
						}
		
		return stockList;
	}

	public String AssignProdStock(HBOStockDTO AssProdStkDto,String warehouseId,HBOUser hboUserDetails) throws HBOException {
		String message="success";
		Connection con=null;	
		HBOStockDAO stockDAO = new HBOStockDAOImpl(con);
		try {
			logger.info("Inside HBOStockServiceImpl : AssignProdStock()");
			stockDAO.AssignProdStock(AssProdStkDto,warehouseId,hboUserDetails);

		} catch (Exception e) {
			if (e instanceof DAOException) {
				logger.error(
					"DAOException occured in AssignProdStock():"
						+ e.getMessage());
				message="failure";// chnages on 8/5/2008 by sachin
				//throw new HBOException(e.getMessage());
			} else {
				logger.error(
					"Exception occured in AssignProdStock():" + e.getMessage());
			}
			message="failure";
		}
		return message;
	}
	
	public String projectQty(Object object) throws HBOException {
		String message="success";
		Connection con=null;	
		HBOStockDAO stockDAO = new HBOStockDAOImpl(con);
		try{
			stockDAO.projectQty(object);
		} catch (Exception e) {
			if (e instanceof DAOException) {
				logger.error("DAOException occured in getWarehouseList():" + e.getMessage());
				message="failure"; // changes on 12/5/2008 by sachin
				//throw new HBOException(e.getMessage());
			} else {
				logger.error("Exception occured in getWarehouseList():" + e.getMessage());
			}
			message="failure";
		}
		return message;
	}
	
	public String projectBulkQty(Object object) throws HBOException{
		String message="success";
		Connection con=null;	
		HBOStockDAO stockDAO = new HBOStockDAOImpl(con);
		int result = 0; 
		try{
			result = stockDAO.projectBulkQty(object);
		} catch (Exception e) {
			if (e instanceof DAOException) {
				logger.error("DAOException occured in getWarehouseList():" + e.getMessage());
				message="failure"; // changes on 12/5/2008 by sachin
				//throw new HBOException(e.getMessage());
			} else {
				logger.error("Exception occured in getWarehouseList():" + e.getMessage());
			}
			message="failure";
		}
		if(result==0)
			message="insert_failed";
		return message;
	}

	/* (non-Javadoc)
	 * @see com.ibm.hbo.services.HBOStockService#getImeiLists(java.lang.String, java.lang.String)
	 */
	public ArrayList getImeiLists(String productId,String userId,String extraCond,String cond) throws HBOException {
		
		ArrayList arrImeiList = new ArrayList();
		Connection con=null;	
		HBOStockDAO stockDAO = new HBOStockDAOImpl(con);
		try {
			logger.info("Inside   getImeiLists :Cond " + cond);
			arrImeiList = stockDAO.arrImeiList(productId,userId,extraCond,cond);
		} catch (Exception e) {
			if (e instanceof DAOException) {
				logger.error(
					"DAOException occured in getBatchList(String UserID,String Cond):"
						+ e.getMessage());
				throw new HBOException(e.getMessage());
			} else {
				logger.error(
					"Exception occured in getBatchList(String UserID,String Cond):"
						+ e.getMessage());
			}
		}
		return arrImeiList;
	}

	/* (non-Javadoc)
	 * @see com.ibm.hbo.services.HBOStockService#getNewImeiNo(java.lang.String, java.lang.String, java.lang.String)
	 */
	public String getNewImeiNo(String imeiNo, String simNo, HBOUser hboUser) throws HBOException {
		
		String newImeiNo = "";
		Connection con=null;	
		HBOStockDAO stockDAO = new HBOStockDAOImpl(con);
		try {
				logger.info("Inside   getImeiLists :Cond " + imeiNo);
				newImeiNo = stockDAO.newImeiNo(imeiNo,simNo,hboUser);
 		} catch (Exception e) {
			if (e instanceof DAOException) {
				logger.error(
					"DAOException occured in getBatchList(String UserID,String Cond):"
						+ e.getMessage());
				throw new HBOException(e.getMessage());
			} else {
				logger.error(
					"Exception occured in getBatchList(String UserID,String Cond):"
						+ e.getMessage());
			}
		}
		return newImeiNo;
	}

	/* (non-Javadoc)
	 * @see com.ibm.hbo.services.HBOStockService#updtDmgFlag(java.lang.String, java.lang.String, java.lang.String)
	 */
	public void updtDmgFlag(String imeiNo, String simNo, HBOUser hboUser) throws HBOException {
		Connection con=null;	
		HBOStockDAO stockDAO = new HBOStockDAOImpl(con);
	try {
	 logger.info("Mark As damage : Service"+imeiNo);
		stockDAO.updtDmgFlag(imeiNo,simNo,hboUser);
	} catch (Exception e) {
		if (e instanceof DAOException) {
			logger.error(
				"DAOException occured in updtDmgFlag:"
					+ e.getMessage());
			throw new HBOException(e.getMessage());
		} else {
			logger.error(
				"Exception occured in updtDmgFlag:"
					+ e.getMessage());
		}
	}
	}


	public ArrayList getAvailableStock(int accountfrom, int productId, String flag, long longAccountID) throws HBOException {
		
		ArrayList stockQty=new ArrayList();
		Connection con=null;	
		HBOStockDAO stockDAO = new HBOStockDAOImpl(con);
		try {
		 
		 stockQty=stockDAO.getAvailableStock(accountfrom, productId,flag, longAccountID);
		 
		 
		} catch (Exception e) {
			if (e instanceof DAOException) {
				logger.error(
					"DAOException occured in updtDmgFlag:"
						+ e.getMessage());
				throw new HBOException(e.getMessage());
			} else {
				logger.error(
					"Exception occured in updtDmgFlag:"
						+ e.getMessage());
			}
		}
		return stockQty;
	}
	
	public ArrayList getStockQtyAllocation(int accountfrom, int productId, String flag) throws HBOException {
		
		ArrayList stockQty=new ArrayList();
		Connection con=null;	
		HBOStockDAO stockDAO = new HBOStockDAOImpl(con);
		try {
		 
		 stockQty=stockDAO.getStockQtyAllocation(accountfrom, productId,flag);
		 
		 
		} catch (Exception e) {
			if (e instanceof DAOException) {
				logger.error(
					"DAOException occured in updtDmgFlag:"
						+ e.getMessage());
				throw new HBOException(e.getMessage());
			} else {
				logger.error(
					"Exception occured in updtDmgFlag:"
						+ e.getMessage());
			}
		}
		return stockQty;
	}
	
	
	public ArrayList getAvailableStockSecSale(int accountfrom, int productId, String flag) throws HBOException {
		
		ArrayList stockQty=new ArrayList();
		Connection con=null;	
		HBOStockDAO stockDAO = new HBOStockDAOImpl(con);
		try {
		 
		 stockQty=stockDAO.getAvailableStockSecSale(accountfrom, productId,flag);
		 
		 
		} catch (Exception e) {
			if (e instanceof DAOException) {
				logger.error(
					"DAOException occured in updtDmgFlag:"
						+ e.getMessage());
				throw new HBOException(e.getMessage());
			} else {
				logger.error(
					"Exception occured in updtDmgFlag:"
						+ e.getMessage());
			}
		}
		return stockQty;
	}
	
public ArrayList getAvailableStockDist(long distId, int productId, String flag) throws HBOException {
		
		ArrayList stockQty=new ArrayList();
		Connection con=null;	
		HBOStockDAO stockDAO = new HBOStockDAOImpl(con);
		try {
		 
		 stockQty=stockDAO.getAvailableStockDist(distId, productId,flag);
		 
		 
		} catch (Exception e) {
			if (e instanceof DAOException) {
				logger.error(
					"DAOException occured in updtDmgFlag:"
						+ e.getMessage());
				throw new HBOException(e.getMessage());
			} else {
				logger.error(
					"Exception occured in updtDmgFlag:"
						+ e.getMessage());
			}
		}
		return stockQty;
	}
	
	
	public String markDamagedProduct(HBOStockDTO stockDto) throws HBOException{
		String updateDmgFlag = "";
		Connection con=null;	
		HBOStockDAO stockDao = new HBOStockDAOImpl(con);
		try{
			updateDmgFlag = stockDao.markDamagedProduct(stockDto);
		}catch(Exception e){
			e.printStackTrace();
			logger.error("Error occured while updating:Mark Damaged");
			updateDmgFlag = HBOConstants.FAILURE;
		}	
		return updateDmgFlag;
	} 

	public ArrayList recordsUpdated(DistStockDTO distDto) throws HBOException,Exception {
		ArrayList recordsUpdated=new ArrayList();
		Connection con=null;	
		HBOStockDAO stockDAO = new HBOStockDAOImpl(con);
		try {
		 
			recordsUpdated=stockDAO.recordsUpdated(distDto);
		 
		 
		} catch (Exception e) {
			if (e instanceof DAOException) {
				logger.error(
					"DAOException occured in records update for Assign Stock" +
					":"
						+ e.getMessage()); 
				throw new HBOException(e.getMessage());
			} else {
				logger.error(
					"Exception occured for Assign Stock:"
						+ e.getMessage());
			}
		}
		return recordsUpdated;
	}
	
	//rajiv jha added method for allocation & Secondary start
	public ArrayList recordsNewUpdated(DistStockDTO distDto) throws HBOException,Exception {
		ArrayList recordsUpdated=new ArrayList();
		Connection con=null;	
		HBOStockDAO stockDAO = new HBOStockDAOImpl(con);
		try {
		 
			recordsUpdated=stockDAO.recordsNewUpdated(distDto);
		 
		 
		} catch (Exception e) {
			if (e instanceof DAOException) {
				logger.error(
					"DAOException occured in records update for Assign Stock" +
					":"
						+ e.getMessage()); 
				throw new HBOException(e.getMessage());
			} else {
				logger.error(
					"Exception occured for Assign Stock:"
						+ e.getMessage());
			}
		}
		return recordsUpdated;
	}
//	rajiv jha add new method for allocation end
	
	public ArrayList getAvailableSerialNos(DistStockDTO distDto)throws HBOException{
		ArrayList serialNosList = new ArrayList();
		Connection con=null;	
		HBOStockDAO stockDAO = new HBOStockDAOImpl(con);
		try {
		 	serialNosList=stockDAO.getSerialNoList(distDto);
		} catch (Exception e) {
			if (e instanceof DAOException) {
				logger.error(
					"DAOException occured in records update for Assign Stock" +
					":"
						+ e.getMessage()); 
				throw new HBOException(e.getMessage());
			} else {
				logger.error(
					"Exception occured for Assign Stock:"
						+ e.getMessage());
			}
		}
		
		return serialNosList;
	}
	
	public ArrayList getInvoiceList(DistStockDTO distDto)throws HBOException{
		
		ArrayList invoiceList = null;
		Connection con=null;	
		HBOStockDAO stockDAO = new HBOStockDAOImpl(con);
		//invoiceList = stockDAO.getInvoiceList(distDto);
		return invoiceList;
	}
	
//	public String flagStatus()throws HBOException
//	{
//		String flag = "N";
//		Connection con=null;
//		try 
//		{
//			HBOStockDAO stockDAO = new HBOStockDAOImpl(con);
//		 	flag = stockDAO.flagStatus();
//		} 
//		catch (Exception e) 
//		{
//			if (e instanceof DAOException) {
//				logger.error(
//					"DAOException occured in records update for Assign Stock" +	":"	+ e.getMessage()); 
//				throw new HBOException(e.getMessage());
//			} else {
//				logger.error(
//					"Exception occured for Assign Stock:"+ e.getMessage());
//			}
//		}
//		
//		return flag;
//	}
	public int insertDataCPEDB(DistStockDTO distDto)throws HBOException
	{

		int rowInseted = 0;
		Connection con=null;
		try 
		{
			HBOStockDAO stockDAO = new HBOStockDAOImpl(con);
			rowInseted = stockDAO.insertDataCPEDB(distDto);
		} 
		catch (Exception e) 
		{
			if (e instanceof DAOException) {
				logger.error(
					"DAOException occured in records update for Assign Stock" +	":"	+ e.getMessage()); 
				throw new HBOException(e.getMessage());
			} else {
				logger.error(
					"Exception occured for Assign Stock:"+ e.getMessage());
			}
		}
		
		return rowInseted;
	
		
		
	}


	public ArrayList getAvailableFreshStockSerialNos(DistStockDTO distDto) throws HBOException {

		ArrayList serialNosList = new ArrayList();
		Connection con=null;	
		HBOStockDAO stockDAO = new HBOStockDAOImpl(con);
		try {
		 	serialNosList=stockDAO.getAvailableFreshStockSerialNos(distDto);
		} catch (Exception e) {
			if (e instanceof DAOException) {
				logger.error(
					"DAOException occured in records update for Assign Stock" +
					":"
						+ e.getMessage()); 
				throw new HBOException(e.getMessage());
			} else {
				logger.error(
					"Exception occured for Assign Stock:"
						+ e.getMessage());
			}
		}
		
		return serialNosList;
	
	}


	public ArrayList freshRecordsNewUpdated(DistStockDTO distDto) throws HBOException, Exception {
		ArrayList SDCNO=new ArrayList();
		Connection con=null;	
		HBOStockDAO stockDAO = new HBOStockDAOImpl(con);
		try {
		 
			SDCNO=stockDAO.freshRecordsNewUpdated(distDto);
		 
		 
		} catch (Exception e) {
			if (e instanceof DAOException) {
				logger.error(
					"DAOException occured in records update for Assign Stock" +
					":"
						+ e.getMessage()); 
				throw new HBOException(e.getMessage());
			} else {
				logger.error(
					"Exception occured for Assign Stock:"
						+ e.getMessage());
			}
		}
		return SDCNO;
	}
	
	public ArrayList freshRecordsNewUpdatedSwap(DistStockDTO distDto) throws HBOException, Exception {
		ArrayList SDCNO=new ArrayList();
		Connection con=null;	
		HBOStockDAO stockDAO = new HBOStockDAOImpl(con);
		try {
		 
			SDCNO=stockDAO.freshRecordsNewUpdatedSwap(distDto);
		 
		 
		} catch (Exception e) {
			if (e instanceof DAOException) {
				logger.error(
					"DAOException occured in records update for Assign Stock" +
					":"
						+ e.getMessage()); 
				throw new HBOException(e.getMessage());
			} else {
				logger.error(
					"Exception occured for Assign Stock:"
						+ e.getMessage());
			}
		}
		return SDCNO;
	}



}
