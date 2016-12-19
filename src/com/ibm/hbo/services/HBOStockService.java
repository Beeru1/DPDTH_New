/*
 * Created on Nov 12,2007
 *
 * To change the template for this generated file go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
package com.ibm.hbo.services;

import java.util.ArrayList;

import com.ibm.hbo.common.HBOUser;
import com.ibm.hbo.dto.DistStockDTO;
import com.ibm.hbo.dto.HBOStockDTO;
import com.ibm.hbo.dto.HBOUserBean;
import com.ibm.hbo.exception.HBOException;

/**
 * @author Parul
 * @version 2.0
 * 
 */
public interface HBOStockService {
	public int getAvlStock(String userId,String warehouseId) throws HBOException;
	
	//public String AssignSimStock(HBOUserBean sessionUserBean,HBOStockDTO AssSimStkDto)throws HBOException;
	
	public String AssignSimStock(HBOUser obj,HBOStockDTO AssSimStkDto)throws HBOException;
	public String bundleStock(HBOUser userBean,Object FormBean,String master,String condition)throws HBOException;

	/**
	 * @return
	 */
	public ArrayList getBatchList(String USerID ,String warehouseId, String Cond)throws HBOException;

	/**
	 * @param simStkDto
	 */
	public void VerifySimStock(HBOUser obj,HBOStockDTO simStkDto)  throws HBOException;

	/**
	 * @param string
	 */

	public ArrayList getBatchDetails(String batch_no) throws HBOException;
	/**
	 * @param string
	 * @return
	 */
	public ArrayList getSimBatchDetails(String batch_no) throws HBOException;
	/**
	 * @param hboUserBean
	 * @return
	 */
	public ArrayList getBundledStock(HBOUser hboUserBean,String cond)throws HBOException;

	public ArrayList getBundledStockDetails(String requestId) throws HBOException;
	
	public ArrayList getAssignedStock(int warehouseId, String condition) throws HBOException;
	
	public String acceptRejectStock(HBOUser userBean,HBOStockDTO prodStkDto)  throws HBOException;
	
	public String AssignProdStock(HBOStockDTO AssProdStkDto,String warehouseId,HBOUser hboUserDetails)throws HBOException;
	
	public String projectQty(Object object) throws HBOException;
	
	public String projectBulkQty(Object object) throws HBOException;
	
	public ArrayList getImeiLists(String productId,String userId,String extraCond,String cond)throws HBOException;
	
	public void updtDmgFlag(String imeiNo, String simNo, HBOUser hboUser)throws HBOException;
	
	public String markDamagedProduct(HBOStockDTO stockDto) throws HBOException; 

	/**
	 * @param imeiNo
	 * @param simNo
	 * @param hboUserBean
	 * @return
	 */
	public String getNewImeiNo(String imeiNo, String simNo, HBOUser hboUser)throws HBOException;

	public ArrayList getAvailableStock(int accountfrom, int productId, String flag, long longAccountID)throws HBOException;
	
	public ArrayList getStockQtyAllocation(int accountfrom, int productId, String flag)throws HBOException;
	
	public ArrayList getAvailableStockSecSale(int accountfrom, int productId, String flag)throws HBOException;
		
	public ArrayList recordsUpdated(DistStockDTO distDto)throws HBOException,Exception;
	//rajiv jha add new method for allocation start
	public ArrayList recordsNewUpdated(DistStockDTO distDto)throws HBOException,Exception;
	public ArrayList freshRecordsNewUpdated(DistStockDTO distDto)throws HBOException,Exception;
	public ArrayList freshRecordsNewUpdatedSwap(DistStockDTO distDto)throws HBOException,Exception;
	
//	rajiv jha add new method for allocation end
	public ArrayList getAvailableStockDist(long distId, int productId, String flag)throws HBOException,Exception;
	public ArrayList getAvailableSerialNos(DistStockDTO distDto)throws HBOException;
	public ArrayList getAvailableFreshStockSerialNos(DistStockDTO distDto)throws HBOException;
	
	//public String flagStatus()throws HBOException;
	public int insertDataCPEDB(DistStockDTO distDto)throws HBOException;
	
	
	public ArrayList getInvoiceList(DistStockDTO distDto)throws HBOException;

	
}
 