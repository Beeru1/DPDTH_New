/* 
 * VDistributorDealerMappingDao.java
 * Created: July 16, 2007
 * 
 * 
 */ 

package com.ibm.virtualization.ussdactivationweb.daoInterfaces;

import com.ibm.virtualization.ussdactivationweb.dto.VDistributorSubscriberMappingDTO;
import com.ibm.virtualization.ussdactivationweb.utils.DAOException;



/** 
  * DAO for the <b>V_DISTRIBUTOR_DEALER_MAPPING</b> database table.
  * Provides methods to perform insert, update, delete and select queries.
  * <br><pre>
  * Table: V_DISTRIBUTOR_DEALER_MAPPING
  * ----------------------------------------------
  *     column: DISTRIBUTOR_ID        NUMBER null
  *     column: DEALER_ID        NUMBER null
  * 
  * Primary Key(s):  DEALER_ID DISTRIBUTOR_ID
  * </pre>
  * 
  */ 
public interface VDistributorSubscriberMappingDAOInterface {

 /** 

	* Inserts a row in the V_DISTRIBUTOR_DEALER_MAPPING table.
	* @param dto  DTO Object holding the data to be inserted.
	* @return  The no. of rows inserted. 
	* @throws VDistributorDealerMappingDaoException In case of an error 
 **/ 

    public  int insert(String distributorId,String dealerId,int userId) throws DAOException;

 /** 

	* Updates a row in the V_DISTRIBUTOR_DEALER_MAPPING table.
	* The Primary Key Object determines wich row gets updated.
	* @param dto  DTO Object holding the data to be updated. The primary key vales determine which row will be updated
	* @return  The no. of rows updated. 
	* @throws VDistributorDealerMappingDaoException In case of an error 
 **/ 

    public  int update(VDistributorSubscriberMappingDTO dto,int userId) throws DAOException;

 /** 

	* Deletes a row in the database based on the primary key supplied.
	* @param dealerId  The dealerId value for which the row needs to be deleted
	* @param distributorId  The distributorId value for which the row needs to be deleted
	* @return  The no. of rows deleted.
	* @throws VDistributorDealerMappingDaoException In case of an error 
 **/ 

    public  int delete(String dealerId, String distributorId) throws DAOException;

 /** 

	* Returns a single row of the database based on the primary key information supplied. If there is no match, <code>null</code> is returned.
	* @param dealerId  The dealerId value for which the row needs to be retrieved.
	* @param distributorId  The distributorId value for which the row needs to be retrieved.
	* @return DTO Object representing a single row of the table based on the primary key information supplied.If there is no match, <code>null</code> is returned.
 **/ 

    public  VDistributorSubscriberMappingDTO findByPrimaryKey(String dealerId, String distributorId) throws DAOException;

}
