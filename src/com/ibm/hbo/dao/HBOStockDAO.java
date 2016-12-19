/* 
 * ArcUserMstrDao.java
 * Created: September 25, 2007
 * 
 * 
 */

package com.ibm.hbo.dao;
import java.util.ArrayList;

import com.ibm.hbo.common.HBOUser;
import com.ibm.hbo.dto.DistStockDTO;
import com.ibm.hbo.dto.HBOStockDTO;
import com.ibm.hbo.dto.HBOUserBean;
import com.ibm.hbo.exception.DAOException;
 
public interface HBOStockDAO {

	/** 
	
		* Inserts a row in the ARC_USER_MSTR table.
		* @param dto  DTO Object holding the data to be inserted.
		* @return  The no. of rows inserted. 
		* @throws ArcUserMstrDaoException In case of an error
	**/

	public int findValues(String userId, String warehouseId) throws DAOException;
		
	public void InsertAssignedSimStock(HBOUser obj,HBOStockDTO AssSimStkDto)throws DAOException;
	public void bundleStock(HBOUser userBean,Object FormBean,String master,String condition)throws DAOException;

	
	public ArrayList findByPrimaryKey(String UserID,String warehouseID, String Cond)throws DAOException;

	/**
	 * @param simStkDto
	 */
	public void UpdateSimStock(HBOUser obj,HBOStockDTO simStkDto) throws DAOException;

	/**
	 * @param batch_no
	 * @return
	 */
	public ArrayList findByPrimaryKey(String batch_no,String cond) throws DAOException;

	/**
	 * @param hboUserBean
	 * @return
	 */
	
	public ArrayList findByPrimaryKey(HBOUser hboUserBean,String cond)throws DAOException;

	/** 
	
		* Inserts a row in the ARC_USER_MSTR table.
		* @param dto  DTO Object holding the data to be inserted.
		* @return  The no. of rows inserted. 
		* @throws ArcUserMstrDaoException In case of an error
	**/
	//public void InsertAssignedSimStock(HBOStockDTO AssSimStkDto)throws DAOException;

	/**
	 * @param simStkDto
	 */
	public void UpdateSimStock(HBOStockDTO simStkDto);
	public ArrayList getAssignStockDetails(int warehouseId, String condition) throws DAOException;
	public ArrayList getBatchDetailList(String batch_no) throws DAOException;
	public void acceptRejectProdStock(HBOUser userBean , HBOStockDTO prodStockDTO) throws DAOException;
	public void AssignProdStock(HBOStockDTO AssProdStkDto,String warehouseId,HBOUser hboUserDetails)throws DAOException;


	public void projectQty(Object object)throws DAOException;

	public int projectBulkQty(Object object)throws DAOException;
	/**
	 * @param userId
	 * @param Cond
	 * @return
	 */
	public ArrayList arrImeiList(String productId,String userId,String extraCond,String Cond)throws DAOException;

	/**
	 * @param imeiNo
	 * @param simNo
	 * @param string
	 * @return
	 */
	public String newImeiNo(String imeiNo, String simNo, HBOUser hboUser)throws DAOException;

	/**
	 * @param imeiNo
	 * @param simNo
	 * @param warehouseId
	 */
	public void updtDmgFlag(String imeiNo, String simNo, HBOUser hboUser)throws DAOException;
	public String markDamagedProduct(HBOStockDTO stockDto) throws DAOException;
	public ArrayList getAvailableStock(int accountfrom, int productId, String flag, long longAccountID)throws DAOException;
	public ArrayList getStockQtyAllocation(int accountfrom, int productId, String flag)throws DAOException;
	public ArrayList getAvailableStockSecSale(int accountfrom, int productId, String flag)throws DAOException;
	
	public ArrayList recordsUpdated(DistStockDTO distDto)throws DAOException;
	//rajiv jha added method for allocation start
	public ArrayList recordsNewUpdated(DistStockDTO distDto)throws DAOException;
	public ArrayList freshRecordsNewUpdated(DistStockDTO distDto)throws DAOException;	
	public ArrayList freshRecordsNewUpdatedSwap(DistStockDTO distDto)throws DAOException;	
	//rajiv jha add new method for allocation end
	public ArrayList getAvailableStockDist(long distId, int productId, String flag)throws DAOException;
	public ArrayList getSerialNoList(DistStockDTO distDto)throws DAOException;
	public ArrayList getAvailableFreshStockSerialNos(DistStockDTO distDto)throws DAOException;	
	public ArrayList getInvoiceList(DistStockDTO distDto)throws DAOException;
	//public String flagStatus()throws DAOException;
	public int insertDataCPEDB(DistStockDTO distDto) throws DAOException;
	

	

}
