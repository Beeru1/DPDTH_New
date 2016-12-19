package com.ibm.dp.dao.impl;

import java.io.InputStream;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Savepoint;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;
import java.util.Map;

import org.apache.log4j.Logger;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;

import com.ibm.dp.common.DBQueries;
import com.ibm.dp.cpedp.DPCPEDBConnection;
import com.ibm.dp.dao.STBFlushOutDao;
import com.ibm.dp.dto.DuplicateSTBDTO;
import com.ibm.utilities.ValidatorUtility;
import com.ibm.virtualization.recharge.common.Constants;
import com.ibm.virtualization.recharge.common.ResourceReader;
import com.ibm.virtualization.recharge.dao.rdbms.BaseDaoRdbms;
import com.ibm.virtualization.recharge.db.DBConnectionManager;
import com.ibm.virtualization.recharge.exception.DAOException;
import com.ibm.virtualization.ussdactivationweb.utils.Utility;


public class STBFlushOutDaoImpl  extends BaseDaoRdbms implements STBFlushOutDao
{
	private static Logger logger = Logger.getLogger(STBFlushOutDaoImpl.class.getName());

	protected static final String SQL_STB_FRESH_STOCK_LIST_KEY = "SQL_STB_FRESH_STOCK_LIST_KEY";
	protected static final String SQL_STB_FRESH_STOCK_LIST = DBQueries.SQL_SEARCH_STOCK_STB_LIST;

	protected static final String SQL_SELECT_ACTIVE_STB_LIST_KEY = "SQL_SELECT_ACTIVE_STB_LIST_KEY";
	protected static final String SQL_SELECT_ACTIVE_STB_LIST= DBQueries.SQL_SELECT_ACTIVE_STB_LIST;

	protected static final String SQL_SELECT_ERROR_PO_LIST_KEY = "SQL_SELECT_ERROR_PO_LIST_KEY";
	protected static final String SQL_SELECT_ERROR_PO_LIST = DBQueries.SQL_SELECT_ERROR_PO_LIST;

	protected static final String SQL_INSERT_PO_STOCK_INVENTORY_KEY = "SQL_INSERT_PO_STOCK_INVENTORY_KEY";
	protected static final String SQL_INSERT_PO_STOCK_INVENTORY = DBQueries.SQL_INSERT_PO_STOCK_INVENTORY;

	protected static final String SQL_INSERT_ERROR_PO_DETAILS_HIST_KEY = "SQL_INSERT_ERROR_PO_DETAILS_HIST_KEY";
	protected static final String SQL_INSERT_ERROR_PO_DETAILS_HIST = DBQueries.SQL_INSERT_ERROR_PO_DETAILS_HIST;

	protected static final String SQL_INSERT_DP_ERROR_REV_DETAIL_HIST_KEY = "SQL_INSERT_DP_ERROR_REV_DETAIL_HIST_KEY";
	protected static final String SQL_INSERT_DP_ERROR_REV_DETAIL_HIST = DBQueries.SQL_INSERT_DP_ERROR_REV_DETAIL_HIST;

	protected static final String SQL_SELECT_DP_ERROR_REV_DETAIL_KEY = "SQL_SELECT_DP_ERROR_REV_DETAIL_KEY";
	protected static final String SQL_SELECT_DP_ERROR_REV_DETAIL = DBQueries.SQL_SELECT_DP_ERROR_REV_DETAIL;

	protected static final String SQL_DELETE_ERROR_PO_DETAILS_KEY = "SQL_DELETE_ERROR_PO_DETAILS_KEY";
	protected static final String SQL_DELETE_ERROR_PO_DETAILS = DBQueries.SQL_DELETE_ERROR_PO_DETAILS;

	protected static final String SQL_UPDATE_DP_REV_STOCK_INVENTORY_KEY = "SQL_UPDATE_DP_REV_STOCK_INVENTORY_KEY";
	protected static final String SQL_UPDATE_DP_REV_STOCK_INVENTORY = DBQueries.SQL_UPDATE_DP_REV_STOCK_INVENTORY;

	protected static final String SQL_UPDATE_DP_REV_INVENTORY_CHANGE_KEY = "SQL_UPDATE_DP_REV_INVENTORY_CHANGE_KEY";
	protected static final String SQL_UPDATE_DP_REV_INVENTORY_CHANGE = DBQueries.SQL_UPDATE_DP_REV_INVENTORY_CHANGE;


	protected static final String SQL_DELETE_ERROR_REV_DETAILS_KEY = "SQL_DELETE_ERROR_REV_DETAILS_KEY";
	protected static final String SQL_DELETE_ERROR_REV_DETAILS = DBQueries.SQL_DELETE_ERROR_REV_DETAILS;

	protected static final String SQL_STB_STATUS_LIST = DBQueries.SQL_STB_STATUS_LIST;

	protected static final String SQL_PO_DETAIL_LIST_KEY = "SQL_PO_DETAIL_LIST_KEY";
	protected static final String SQL_PO_DETAIL_LIST = DBQueries.SQL_PO_DETAIL_LIST;

	public static final String SQL_SELECT_OLMID 	= DBQueries.SQL_SELECT_OLMID;
	public static final String SQL_SELECT_SECURITY_OLMID 	= DBQueries.SQL_SELECT_SECURITY_OLMID;
	public static final String SQL_SELECT_PRODUCT_SECURITY = DBQueries.SQL_SELECT_PRODUCT_SECURITY;

	//aman--add remarks and flushout id in query
	/*private static final String MOVETO_DP_STOCK_INVENTORY_ASSIGNED = "Insert into DP_STOCK_INVENTORY_ASSIGNED "+
			" (PRODUCT_ID,SERIAL_NO,MARK_DAMAGED,DISTRIBUTOR_ID,DISTRIBUTOR_PURCHASE_DATE,FSE_ID ,FSE_PURCHASE_DATE,RETAILER_ID,RETAILER_PURCHASE_DATE,DAMAGE_REMARKS,DAMAGED_BY,DAMAGE_COST, "+
			" INV_NO,REMARKS,MSISDN,TRANSACTION_ID,STATUS,ASSIGN_DATE,CUSTOMER_ID,IS_SEC_SALE, UPDATED_ON, FLUSHOUT_FLAG) select "+
			" PRODUCT_ID,SERIAL_NO,MARK_DAMAGED,DISTRIBUTOR_ID,DISTRIBUTOR_PURCHASE_DATE,FSE_ID,FSE_PURCHASE_DATE,RETAILER_ID,RETAILER_PURCHASE_DATE,DAMAGE_REMARKS,DAMAGED_BY,DAMAGE_COST, "+
			" INV_NO,REMARKS,MSISDN,TRANSACTION_ID,STATUS,CURRENT_TIMESTAMP,CUSTOMER_ID,IS_SEC_SALE, current timestamp,-2  from DP_STOCK_INVENTORY  where SERIAL_NO=?  with ur ";
*/
	//changed by aman for Dp_flushout functionality on 14/03/14
	
	private static final String MOVETO_DP_STOCK_INVENTORY_ASSIGNED = "Insert into DP_STOCK_INVENTORY_ASSIGNED "+
	" (PRODUCT_ID,SERIAL_NO,MARK_DAMAGED,DISTRIBUTOR_ID,DISTRIBUTOR_PURCHASE_DATE,FSE_ID ,FSE_PURCHASE_DATE,RETAILER_ID,RETAILER_PURCHASE_DATE,DAMAGE_REMARKS,DAMAGED_BY,DAMAGE_COST, "+
	" INV_NO,REMARKS,MSISDN,TRANSACTION_ID,STATUS,ASSIGN_DATE,CUSTOMER_ID,IS_SEC_SALE, UPDATED_ON, FLUSHOUT_FLAG,FLUSHOUT_REMARKS,FLUSHOUT_ID,IS_SCM_FLUSH) select "+
	" PRODUCT_ID,SERIAL_NO,MARK_DAMAGED,DISTRIBUTOR_ID,DISTRIBUTOR_PURCHASE_DATE,FSE_ID,FSE_PURCHASE_DATE,RETAILER_ID,RETAILER_PURCHASE_DATE,DAMAGE_REMARKS,DAMAGED_BY,DAMAGE_COST, "+
	" INV_NO,REMARKS,MSISDN,TRANSACTION_ID,STATUS,CURRENT_TIMESTAMP,CUSTOMER_ID,IS_SEC_SALE, current timestamp,-2,?,?,? from DP_STOCK_INVENTORY  where SERIAL_NO=? with ur ";

	
	
	//aman--add remarks and flushout id in query
	/*private static final String  MOVETO_DP_REV_INVENTORY_CHANGE_HISTORY ="Insert into DP_REV_INVENTORY_CHANGE_HIST(REQ_ID,DEFECTIVE_SR_NO,DEFECTIVE_PRODUCT_ID,DEFECTIVE_DIST_ID,NEW_SR_NO,NEW_PRODUCT_ID,NEW_DIST_D "+

								",INV_CHANGE_DATE,COLLECTION_ID,STATUS,CREATED_ON,UPDATED_ON,ARCHIVED_DATE,IS_SCM,ORIGINAL_DIST_ID,FLUSHOUT_FLAG,ERROR_STATUS) select REQ_ID,DEFECTIVE_SR_NO,DEFECTIVE_PRODUCT_ID,DEFECTIVE_DIST_ID,NEW_SR_NO,NEW_PRODUCT_ID,NEW_DIST_D"+
								",INV_CHANGE_DATE,COLLECTION_ID,STATUS,CREATED_ON,UPDATED_ON,CURRENT_TIMESTAMP,IS_SCM,ORIGINAL_DIST_ID,-2,ERROR_STATUS from DP_REV_INVENTORY_CHANGE where DEFECTIVE_SR_NO=? and COLLECTION_ID not in ('2','4')";
*/
	
	//changed by aman for Dp_flushout functionality on 14/03/14
	private static final String  MOVETO_DP_REV_INVENTORY_CHANGE_HISTORY ="Insert into DP_REV_INVENTORY_CHANGE_HIST(REQ_ID,DEFECTIVE_SR_NO,DEFECTIVE_PRODUCT_ID,DEFECTIVE_DIST_ID,NEW_SR_NO,NEW_PRODUCT_ID,NEW_DIST_D "+

	",INV_CHANGE_DATE,COLLECTION_ID,STATUS,CREATED_ON,UPDATED_ON,ARCHIVED_DATE,IS_SCM,ORIGINAL_DIST_ID,FLUSHOUT_FLAG,ERROR_STATUS,FLUSHOUT_REMARKS,FLUSHOUT_ID,IS_SCM_FLUSH) select REQ_ID,DEFECTIVE_SR_NO,DEFECTIVE_PRODUCT_ID,DEFECTIVE_DIST_ID,NEW_SR_NO,NEW_PRODUCT_ID,NEW_DIST_D"+
	",INV_CHANGE_DATE,COLLECTION_ID,STATUS,CREATED_ON,UPDATED_ON,CURRENT_TIMESTAMP,IS_SCM,ORIGINAL_DIST_ID,-2,ERROR_STATUS,?,?,? from DP_REV_INVENTORY_CHANGE where DEFECTIVE_SR_NO=? and COLLECTION_ID not in ('2','4')";

	
	//aman--add remarks and flushout id in query
	/*private static final String  MOVETO_DP_REV_INVENTORY_CHANGE_HISTORY2 ="Insert into DP_REV_INVENTORY_CHANGE_HIST(REQ_ID,DEFECTIVE_SR_NO,DEFECTIVE_PRODUCT_ID,DEFECTIVE_DIST_ID,NEW_SR_NO,NEW_PRODUCT_ID,NEW_DIST_D "+
	",INV_CHANGE_DATE,COLLECTION_ID,STATUS,CREATED_ON,UPDATED_ON,ARCHIVED_DATE,IS_SCM,ORIGINAL_DIST_ID,FLUSHOUT_FLAG,ERROR_STATUS) select REQ_ID,DEFECTIVE_SR_NO,DEFECTIVE_PRODUCT_ID,DEFECTIVE_DIST_ID,NEW_SR_NO,NEW_PRODUCT_ID,NEW_DIST_D"+
	",INV_CHANGE_DATE,COLLECTION_ID,STATUS,CREATED_ON,UPDATED_ON,CURRENT_TIMESTAMP,IS_SCM,ORIGINAL_DIST_ID,-2 ,ERROR_STATUS from DP_REV_INVENTORY_CHANGE where DEFECTIVE_SR_NO=? and DATE(INV_CHANGE_DATE)=?";
*/
	
	private static final String  MOVETO_DP_REV_INVENTORY_CHANGE_HISTORY2 ="Insert into DP_REV_INVENTORY_CHANGE_HIST(REQ_ID,DEFECTIVE_SR_NO,DEFECTIVE_PRODUCT_ID,DEFECTIVE_DIST_ID,NEW_SR_NO,NEW_PRODUCT_ID,NEW_DIST_D "+
	",INV_CHANGE_DATE,COLLECTION_ID,STATUS,CREATED_ON,UPDATED_ON,ARCHIVED_DATE,IS_SCM,ORIGINAL_DIST_ID,FLUSHOUT_FLAG,ERROR_STATUS,FLUSHOUT_REMARKS,FLUSHOUT_ID,IS_SCM_FLUSH) select REQ_ID,DEFECTIVE_SR_NO,DEFECTIVE_PRODUCT_ID,DEFECTIVE_DIST_ID,NEW_SR_NO,NEW_PRODUCT_ID,NEW_DIST_D"+
	",INV_CHANGE_DATE,COLLECTION_ID,STATUS,CREATED_ON,UPDATED_ON,CURRENT_TIMESTAMP,IS_SCM,ORIGINAL_DIST_ID,-2 ,ERROR_STATUS,?,?,? from DP_REV_INVENTORY_CHANGE where DEFECTIVE_SR_NO=? and DATE(INV_CHANGE_DATE)=?";


	
	
	
	//aman--add remarks and flushout id in query
	/*private static final String  MOVETO_DP_REV_INVENTORY_CHANGE_HIST_ERROR_REV ="Insert into DP_REV_INVENTORY_CHANGE_HIST(REQ_ID,DEFECTIVE_SR_NO,DEFECTIVE_PRODUCT_ID,DEFECTIVE_DIST_ID,NEW_SR_NO,NEW_PRODUCT_ID,NEW_DIST_D "+
	",INV_CHANGE_DATE,COLLECTION_ID,STATUS,CREATED_ON,UPDATED_ON,ARCHIVED_DATE,IS_SCM,ORIGINAL_DIST_ID,FLUSHOUT_FLAG,ERROR_STATUS) select REQ_ID,DEFECTIVE_SR_NO,DEFECTIVE_PRODUCT_ID,DEFECTIVE_DIST_ID,NEW_SR_NO,NEW_PRODUCT_ID,NEW_DIST_D"+
	",INV_CHANGE_DATE,COLLECTION_ID,STATUS,CREATED_ON,UPDATED_ON,CURRENT_TIMESTAMP,IS_SCM,ORIGINAL_DIST_ID,-2 ,ERROR_STATUS from DP_REV_INVENTORY_CHANGE where DEFECTIVE_SR_NO=? and  ERROR_STATUS = 'ERR'";
*/
	private static final String  MOVETO_DP_REV_INVENTORY_CHANGE_HIST_ERROR_REV ="Insert into DP_REV_INVENTORY_CHANGE_HIST(REQ_ID,DEFECTIVE_SR_NO,DEFECTIVE_PRODUCT_ID,DEFECTIVE_DIST_ID,NEW_SR_NO,NEW_PRODUCT_ID,NEW_DIST_D "+
	",INV_CHANGE_DATE,COLLECTION_ID,STATUS,CREATED_ON,UPDATED_ON,ARCHIVED_DATE,IS_SCM,ORIGINAL_DIST_ID,FLUSHOUT_FLAG,ERROR_STATUS,FLUSHOUT_REMARKS,FLUSHOUT_ID,IS_SCM_FLUSH) select REQ_ID,DEFECTIVE_SR_NO,DEFECTIVE_PRODUCT_ID,DEFECTIVE_DIST_ID,NEW_SR_NO,NEW_PRODUCT_ID,NEW_DIST_D"+
	",INV_CHANGE_DATE,COLLECTION_ID,STATUS,CREATED_ON,UPDATED_ON,CURRENT_TIMESTAMP,IS_SCM,ORIGINAL_DIST_ID,-2 ,ERROR_STATUS,?,?,? from DP_REV_INVENTORY_CHANGE where DEFECTIVE_SR_NO=? and  ERROR_STATUS = 'ERR'";

	
	
	
	//aman--add remarks and flushout id in query
	/*private static final String MOVE_TO_REV_STOCK_INV_HIST = "INSERT INTO DP_REV_STOCK_INVENTORY_HIST" +
			" (COLLECTION_ID, DEFECT_ID, PRODUCT_ID, SERIAL_NO_COLLECT, SERIAL_NO_REPLACE, COLLECTION_DATE, REMARKS, CREATED_ON, CREATED_BY, CIRCLE_ID, STATUS, UPDATED_ON, UPDATED_BY, SCM_STATUS, ARCHIVED_DATE, REPAIR_PRODUCT_ID, REPAIR_REMARK,CUSTOMER_ID,FLUSHOUT_FLAG )" +
			" (SELECT COLLECTION_ID, DEFECT_ID, PRODUCT_ID, SERIAL_NO_COLLECT, SERIAL_NO_REPLACE, COLLECTION_DATE, REMARKS, CREATED_ON, CREATED_BY, CIRCLE_ID, STATUS, UPDATED_ON, UPDATED_BY, SCM_STATUS, CURRENT_TIMESTAMP, REPAIR_PRODUCT_ID, REPAIR_REMARK ,CUSTOMER_ID,-2 FROM DP_REV_STOCK_INVENTORY where SERIAL_NO_COLLECT=? )";
*/

	private static final String MOVE_TO_REV_STOCK_INV_HIST = "INSERT INTO DP_REV_STOCK_INVENTORY_HIST" +
	" (COLLECTION_ID, DEFECT_ID, PRODUCT_ID, SERIAL_NO_COLLECT, SERIAL_NO_REPLACE, COLLECTION_DATE, REMARKS, CREATED_ON, CREATED_BY, CIRCLE_ID, STATUS, UPDATED_ON, UPDATED_BY, SCM_STATUS, ARCHIVED_DATE, REPAIR_PRODUCT_ID, REPAIR_REMARK,CUSTOMER_ID,FLUSHOUT_FLAG,FLUSHOUT_REMARKS,FLUSHOUT_ID,IS_SCM_FLUSH )" +
	" (SELECT COLLECTION_ID, DEFECT_ID, PRODUCT_ID, SERIAL_NO_COLLECT, SERIAL_NO_REPLACE, COLLECTION_DATE, REMARKS, CREATED_ON, CREATED_BY, CIRCLE_ID, STATUS, UPDATED_ON, UPDATED_BY, SCM_STATUS, CURRENT_TIMESTAMP, REPAIR_PRODUCT_ID, REPAIR_REMARK ,CUSTOMER_ID,-2,?,?,? FROM DP_REV_STOCK_INVENTORY where SERIAL_NO_COLLECT=? )";


	
	private static final String DELETE_DP_STOCK_INVENTORY = "DELETE from DP_STOCK_INVENTORY  where SERIAL_NO=?  with ur";

	//aman--add remarks and flushout id in query
	private static final String MOVE_DP_ERROR_REV_DETAIL2="INSERT INTO DPDTH.DP_ERROR_REV_DETAILS_HIST(ERROR_ID, SERIAL_NO,DIST_ID, PRODUCT_ID, INV_CHANGE_DATE, COLLECTION_ID, DEFECT_ID, CONF_STATUS, CREATED_ON, OLD_PO_NO, OLD_PO_ACCEPT_DT, OLD_PRODUCT_ID, OLD_DIST_ID, OLD_STB_STATUS, OLD_INV_CHANGE_DT, OLD_COLLECTION_ID, OLD_DEFECT_ID, OLD_REV_COLL_DT, OLD_REV_DC_NO, OLD_REV_DC_DATE, HIST_DATE,FLUSHOUT_FLAG) "+
	"SELECT ERROR_ID, SERIAL_NO,DIST_ID, PRODUCT_ID, INV_CHANGE_DATE, COLLECTION_ID, DEFECT_ID, CONF_STATUS, CREATED_ON, OLD_PO_NO, OLD_PO_ACCEPT_DT, OLD_PRODUCT_ID, OLD_DIST_ID, OLD_STB_STATUS, OLD_INV_CHANGE_DT, OLD_COLLECTION_ID, OLD_DEFECT_ID, OLD_REV_COLL_DT, OLD_REV_DC_NO, OLD_REV_DC_DATE,  current timestamp  ,-2 "+
	"FROM DPDTH.DP_ERROR_REV_DETAILS where SERIAL_NO=?   and DATE(INV_CHANGE_DATE) =?" ;

	private static final String DELETE_DP_ERROR_REV_DETAIL3="DELETE FROM DPDTH.DP_ERROR_REV_DETAILS WHERE SERIAL_NO = ?  and DATE(INV_CHANGE_DATE) =?";

	private static final String DELETE_DP_REV_INVENTORY_CHANGE = "DELETE from DP_REV_INVENTORY_CHANGE where DEFECTIVE_SR_NO=? and COLLECTION_ID not in ('2','4')";

	private static final String DELETE_DP_REV_INVENTORY_CHANGE2 = "DELETE from DP_REV_INVENTORY_CHANGE where DEFECTIVE_SR_NO=?  and DATE(INV_CHANGE_DATE) =?";

	private static final String DELETE_DP_REV_INVENTORY_CHANGE_ERROR_REV = "DELETE from DP_REV_INVENTORY_CHANGE where DEFECTIVE_SR_NO=?  and  ERROR_STATUS = 'ERR'";

	private static final String DELETE_REV_STOCK_INV = "DELETE FROM DP_REV_STOCK_INVENTORY where SERIAL_NO_COLLECT=? ";

	//aman--add remarks and flushout id in query
	/*private static final String MOVE_DP_REV_CHURN_INVENTORY="INSERT INTO DPDTH.DP_REV_CHURN_INVENTORY_HIST(REQ_ID, SERIAL_NUMBER, PRODUCT_ID, VC_ID, CUSTOMER_ID, SI_ID, AGEING, REMARK, STATUS, CIRCLE_ID, CREATED_ON, CREATED_BY, ACTION_DATE, HIST_DATE, PURCHASE_DT, SERVICE_START_DT, FLUSHOUT_FLAG, IS_SCM, ERROR_STATUS) "+
	    													" SELECT REQ_ID, SERIAL_NUMBER, PRODUCT_ID, VC_ID, CUSTOMER_ID, SI_ID, AGEING, REMARK, STATUS, CIRCLE_ID, CREATED_ON, CREATED_BY, ACTION_DATE,  current timestamp,PURCHASE_DT, SERVICE_START_DT,-2 , IS_SCM, ERROR_STATUS "+
															" FROM DPDTH.DP_REV_CHURN_INVENTORY where serial_number=?  ";

	*/
	private static final String MOVE_DP_REV_CHURN_INVENTORY="INSERT INTO DPDTH.DP_REV_CHURN_INVENTORY_HIST(REQ_ID, SERIAL_NUMBER, PRODUCT_ID, VC_ID, CUSTOMER_ID, SI_ID, AGEING, REMARK, STATUS, CIRCLE_ID, CREATED_ON, CREATED_BY, ACTION_DATE, HIST_DATE, PURCHASE_DT, SERVICE_START_DT, FLUSHOUT_FLAG, IS_SCM, ERROR_STATUS,FLUSHOUT_REMARKS,FLUSHOUT_ID,IS_SCM_FLUSH) "+
	" SELECT REQ_ID, SERIAL_NUMBER, PRODUCT_ID, VC_ID, CUSTOMER_ID, SI_ID, AGEING, REMARK, STATUS, CIRCLE_ID, CREATED_ON, CREATED_BY, ACTION_DATE,  current timestamp,PURCHASE_DT, SERVICE_START_DT,-2 , IS_SCM, ERROR_STATUS,?,?,? "+
	" FROM DPDTH.DP_REV_CHURN_INVENTORY where serial_number=?  ";

	// Queries to Update CPE
	private static final String SR_NO_AVAILABLE_CHECK_DP_STOCK_INVENTORY = "Select STATUS from DP_STOCK_INVENTORY  where SERIAL_NO=? and MARK_DAMAGED='N' with ur";
	
	private static final String MOVE_CPE_SERIAL_NUMBER_TO_HIST = "INSERT INTO CPE_INSERT_SERIAL_ASSIGN_HIST" +
	" (SEQ_ID, SERIAL_NUMBER, STATUS, TRANSACTION_ID, DIST_ID, PRODUCT_ID, UNASSIGN_DATE, ASSIGN_DATE, TRANSFER_DATE, FSE_ID, RETAILER_ID, LAST_HIST_DATE, CUSTOMER_ID, IS_SCM, PRODUCT_TYPE)" +
	" (SELECT SEQ_ID, SERIAL_NUMBER, STATUS, TRANSACTION_ID, DIST_ID, PRODUCT_ID, UNASSIGN_DATE, ASSIGN_DATE, SYSDATE, FSE_ID, RETAILER_ID, SYSDATE, CUSTOMER_ID, '"+Constants.STATUS_SYSTEM_OVERRIDEN +"' , PRODUCT_TYPE  FROM CPE_TEMP_INSERT_SERIAL_NUMBER" +
	" where SERIAL_NUMBER=?)";
	private static final String DELETE_CPE_SERIAL_NUMBER_TO_HIST = "DELETE from CPE_TEMP_INSERT_SERIAL_NUMBER where SERIAL_NUMBER=? ";

	public STBFlushOutDaoImpl(Connection connection)
	{
		super(connection);
		queryMap.put(SQL_STB_FRESH_STOCK_LIST_KEY, SQL_STB_FRESH_STOCK_LIST);
		queryMap.put(SQL_SELECT_ACTIVE_STB_LIST_KEY, SQL_SELECT_ACTIVE_STB_LIST);
		queryMap.put(SQL_SELECT_ERROR_PO_LIST_KEY, SQL_SELECT_ERROR_PO_LIST);
		queryMap.put(SQL_INSERT_PO_STOCK_INVENTORY_KEY, SQL_INSERT_PO_STOCK_INVENTORY);
		queryMap.put(SQL_INSERT_ERROR_PO_DETAILS_HIST_KEY, SQL_INSERT_ERROR_PO_DETAILS_HIST);
		queryMap.put(SQL_DELETE_ERROR_PO_DETAILS_KEY, SQL_DELETE_ERROR_PO_DETAILS);
		queryMap.put(SQL_SELECT_DP_ERROR_REV_DETAIL_KEY, SQL_SELECT_DP_ERROR_REV_DETAIL);
		queryMap.put(SQL_UPDATE_DP_REV_STOCK_INVENTORY_KEY, SQL_UPDATE_DP_REV_STOCK_INVENTORY);
		queryMap.put(SQL_UPDATE_DP_REV_INVENTORY_CHANGE_KEY, SQL_UPDATE_DP_REV_INVENTORY_CHANGE);
		queryMap.put(SQL_INSERT_DP_ERROR_REV_DETAIL_HIST_KEY, SQL_INSERT_DP_ERROR_REV_DETAIL_HIST);
		queryMap.put(SQL_DELETE_ERROR_REV_DETAILS_KEY, SQL_DELETE_ERROR_REV_DETAILS);
	}

	public ArrayList<DuplicateSTBDTO> getSTBList(String  stbSLNos)throws DAOException
	{
		PreparedStatement pstmt = null;
		ResultSet rset = null;
		ArrayList<DuplicateSTBDTO> stbList = new ArrayList<DuplicateSTBDTO>();
		try
		{
				pstmt = connection.prepareStatement(queryMap.get(SQL_STB_FRESH_STOCK_LIST_KEY).replace("?", stbSLNos));
			rset = pstmt.executeQuery();
			stbList = fetchSTBListData(rset);
		}
		catch(SQLException sqlEx)
		{
			logger.error("SQL Exception occured while fetching data for flush Error PO details  ::  "+sqlEx.getMessage());
			throw new DAOException(sqlEx.getMessage());
		}
		catch(Exception e)
		{
			logger.error("Exception occured while fetching data for Flush Error PO details  ::  "+e.getMessage());
			throw new DAOException(e.getMessage());
		}
		return stbList;
	}

	/*
	 * Method to release STBs from Error PO.
	 */

	public boolean releaseSTB(ArrayList<String>  stbSerialNos)throws DAOException
	{
		PreparedStatement pstmt = null;
		ResultSet rset = null;
		boolean isRelased = false;
		String strStbNo="";
		try
		{
			Iterator listItr = stbSerialNos.iterator();

			// For each STB
			while(listItr.hasNext())
			{
				
				strStbNo = (String)listItr.next();
				logger.info("STB To Release : "+strStbNo);
				
//				 search STB to release from Error_Rev_dtl  
				/* Search in ERROR_REV_DETAILS and not in ( DP_STOCK_INVENTORY,DP_REV_STOCK_INVENTORY,DP_REV_INVENTORY_CHANGE WHERE IC.ERROR_STATUS NOT IN ('ERR'), DP_REV_CHURN_INVENTORY )
			    if there is any entry, then remove it from this table, copy it in Error history
			    and add in corresponding table as:
				o	If fresh inventory change (“REC” status), then update the IsSCM status of this STB in “Rev Inventory Change” table to DP
				o	If “Error DC” (status “ERR”), update the IsSCM status of this STB in Rev stock Inventory to “DP”
				o	If MSN then update the IsSCM status of this STB in Rev Inventory change to “DP”
			   */
				pstmt = connection.prepareStatement(queryMap.get(SQL_SELECT_DP_ERROR_REV_DETAIL_KEY).replace("?", "'"+strStbNo+"'"));
				rset = pstmt.executeQuery();

				// IF found in DP_ERROR_REV_DETAILS & not found in DP
				if(rset.next())
				{
					logger.info("STB found in Error_Rev_Details, updating ERR to DP ... ");
					//Get Date, Old STB Status from result set
					String oldStbStatus=rset.getString("OLD_STB_STATUS");
					String invChangeDate=rset.getString("INV_CHANGE_DATE");
					logger.info("oldStbStatus : "+oldStbStatus+" :: invChangeDate : " +invChangeDate);
	
					// Insert into DP_ERROR_REV_DETAIL_HIST
					pstmt = connection.prepareStatement(queryMap.get(SQL_INSERT_DP_ERROR_REV_DETAIL_HIST_KEY));
					pstmt.setString(1,strStbNo);
					pstmt.setString(2,invChangeDate);
	
					pstmt.executeUpdate();
	
						int updateCount=0;
						pstmt = connection.prepareStatement(queryMap.get(SQL_UPDATE_DP_REV_INVENTORY_CHANGE_KEY));
						pstmt.setString(1,strStbNo);
						pstmt.setString(2,invChangeDate);
						updateCount = pstmt.executeUpdate();
						if(updateCount>0)
						{
						logger.info("Updated "+updateCount+" STBs in REV_INVENTORY_CHANGE...");
						}
						else
						{
							updateCount=0;
							pstmt = connection.prepareStatement(queryMap.get(SQL_UPDATE_DP_REV_STOCK_INVENTORY_KEY));
							pstmt.setString(1,strStbNo);
							pstmt.setString(2,invChangeDate);
							pstmt.executeUpdate();
							if(updateCount>0)
							{
								logger.info("Updated "+updateCount+" STBs in REV_STOCK_INVENTORY...");
							}
						}
						
					// Deleting from DP_ERROR_REV_DETAIL 
					pstmt = connection.prepareStatement(queryMap.get(SQL_DELETE_ERROR_REV_DETAILS_KEY));
					pstmt.setString(1,strStbNo);
					pstmt.setString(2,invChangeDate);
					pstmt.executeUpdate();
					isRelased = true;
				}
				
				// Search in DP System ( DP_STOCK_INVENTORY,DP_REV_STOCK_INVENTORY, DP_REV_INVENTORY_CHANGE, DP_REV_CHURN_INVENTORY )
				pstmt = connection.prepareStatement(queryMap.get(SQL_SELECT_ACTIVE_STB_LIST_KEY).replace("?", "'"+strStbNo+"'"));
				rset = pstmt.executeQuery();

				// Validate if there is an active instance of this STB then check for next STB.
				if(rset.next())
				{
					logger.info("STB still present in DP, so exiting release for this STB.");
					continue;
				}
				else
				{
					// If a single PO is in error for this STB, remove this STB from Error_PO table and add in Stock Inventory Table
					// If there are multiple PO(s) in error for this STB, remove this STB from Error_PO for oldest PO (oldest DC date)
					// and add in Stock inventory table
					pstmt = connection.prepareStatement(queryMap.get(SQL_SELECT_ERROR_PO_LIST_KEY));
					pstmt.setString(1,strStbNo);

					rset = pstmt.executeQuery();

					// IF found in DP_ERROR_PO_DETAIL
					if(rset.next())
					{
						logger.info("STB release from DP_ERROR_PO_DETAIL : "+strStbNo);
						int prNo = rset.getInt("PR_NO");

						// move to DP_ERROR_PO_DETAIL_HIST from DP_ERROR_PO_DETAIL
						pstmt = connection.prepareStatement(queryMap.get(SQL_INSERT_ERROR_PO_DETAILS_HIST_KEY));
						pstmt.setString(1,strStbNo);
						pstmt.setInt(2,prNo);
						pstmt.executeUpdate();

						// Insert into DP_STOCK_INVENTORY
						pstmt = connection.prepareStatement(queryMap.get(SQL_INSERT_PO_STOCK_INVENTORY_KEY));
						pstmt.setString(1,strStbNo);
						pstmt.setInt(2,prNo);
						pstmt.executeUpdate();

						// Delete from DP_ERROR_PO_DETAIL
						pstmt = connection.prepareStatement(queryMap.get(SQL_DELETE_ERROR_PO_DETAILS_KEY));
						pstmt.setString(1,strStbNo);
						pstmt.setInt(2,prNo);
						pstmt.executeUpdate();
						isRelased = true;
					}
				}
			} // End of While 
		}
		catch(Exception e)
		{
			e.printStackTrace();
			isRelased = false;
			logger.error("Exception occured in releasing STB :  "+strStbNo);
			throw new DAOException(e.getMessage());
		}
		return isRelased;
	}

	
	public ArrayList<DuplicateSTBDTO> getStatusList(String  stbSLNos)throws DAOException
	{
		PreparedStatement pstmt = null;
		ResultSet rset = null;
		ArrayList<DuplicateSTBDTO> stbList = new ArrayList<DuplicateSTBDTO>();
		try
		{

			pstmt = connection.prepareStatement(queryMap.get(SQL_STB_FRESH_STOCK_LIST_KEY).replace("?", stbSLNos));

			rset = pstmt.executeQuery();
			stbList = fetchStatusListData(rset);
		}
		catch(SQLException sqlEx)
		{
			logger.error("SQL Exception occured while fetching data for fresh stock Error PO details  ::  "+sqlEx.getMessage());
			throw new DAOException(sqlEx.getMessage());
		}
		catch(Exception e)
		{
			logger.error("Exception occured while fetching data for fresh stock Error PO details  ::  "+e.getMessage());
			throw new DAOException(e.getMessage());
		}
		return stbList;
	}
	public ArrayList<DuplicateSTBDTO> getStatusList(String  stbSLNos,List<DuplicateSTBDTO> flushListUploadDTO)throws DAOException
	{
		PreparedStatement pstmt = null;
		ResultSet rset = null;
		ArrayList<DuplicateSTBDTO> stbList = new ArrayList<DuplicateSTBDTO>();
		try
		{

			pstmt = connection.prepareStatement(queryMap.get(SQL_STB_FRESH_STOCK_LIST_KEY).replace("?", stbSLNos));

			rset = pstmt.executeQuery();
			stbList = fetchStatusListData(rset,flushListUploadDTO);
		}
		catch(SQLException sqlEx)
		{
			logger.error("SQL Exception occured while fetching data for fresh stock Error PO details  ::  "+sqlEx.getMessage());
			throw new DAOException(sqlEx.getMessage());
		}
		catch(Exception e)
		{
			logger.error("Exception occured while fetching data for fresh stock Error PO details  ::  "+e.getMessage());
			throw new DAOException(e.getMessage());
		}
		return stbList;
	}
	private ArrayList<DuplicateSTBDTO> fetchSTBListData(ResultSet rset) throws Exception
	{
		ArrayList<DuplicateSTBDTO> stbList = new ArrayList<DuplicateSTBDTO>();
		DuplicateSTBDTO  stbListDto = null;
		while(rset.next())
		{
			stbListDto = new DuplicateSTBDTO();

				stbListDto.setStrSerialNo(rset.getString("SERIAL_NO"));
				stbListDto.setStrProductName(rset.getString("PRODUCT_NAME"));
				stbListDto.setStrSTBStatus(rset.getString("STATUS"));
				stbListDto.setStrPONumber(rset.getString("PO_NO"));
				stbListDto.setStrPOStatus(rset.getString("PO_STATUS"));
				stbListDto.setStrDCNo(rset.getString("DC_NO"));
				stbListDto.setStrDCDate(rset.getString("DC_DT"));
				stbListDto.setStrInventoryChangeDt(rset.getString("INV_CHANGE_DT"));
				stbListDto.setStrReceivedOn(rset.getString("RECEIVED_ON"));
				stbListDto.setStrStockReceivedDate(rset.getString("ACCEPT_DATE"));
				stbListDto.setStrDistributorId(rset.getString("DISTRIBUTOR_ID"));
				stbListDto.setStrDistOLMId(rset.getString("DISTRIBUTOR_OLM_ID"));
				stbListDto.setStrDistName(rset.getString("DISTRIBUTOR_NAME"));
				stbListDto.setStrTeableName(rset.getString("TABLENAME"));
				stbList.add(stbListDto);
		}
		return stbList;
}

	private ArrayList<DuplicateSTBDTO> fetchStatusListData(ResultSet rset,List<DuplicateSTBDTO> flushListUploadDTO) throws Exception
	{
		ArrayList<DuplicateSTBDTO> stbList = new ArrayList<DuplicateSTBDTO>();
		DuplicateSTBDTO  stbListDto = null;
		Iterator<DuplicateSTBDTO> itr=null;
		DuplicateSTBDTO stbListDtoOne = new DuplicateSTBDTO();
		logger.info("fetchStatusListData == ");
		while(rset.next())
		{
			stbListDto = new DuplicateSTBDTO();

			stbListDto.setStrSerialNo(rset.getString("SERIAL_NO"));
			stbListDto.setStrProductName(rset.getString("PRODUCT_NAME"));
			stbListDto.setStrSTBStatus(rset.getString("STATUS"));
			stbListDto.setStrPONumber(rset.getString("PO_NO"));
			stbListDto.setStrPOStatus(rset.getString("PO_STATUS"));
			stbListDto.setStrDCNo(rset.getString("DC_NO"));
			stbListDto.setStrDCDate(rset.getString("DC_DT"));
			stbListDto.setStrInventoryChangeDt(rset.getString("INV_CHANGE_DT"));
			stbListDto.setStrReceivedOn(rset.getString("RECEIVED_ON"));
			stbListDto.setStrStockReceivedDate(rset.getString("ACCEPT_DATE"));
			stbListDto.setStrDistributorId(rset.getString("DISTRIBUTOR_ID"));
			stbListDto.setStrDistOLMId(rset.getString("DISTRIBUTOR_OLM_ID"));
			stbListDto.setStrDistName(rset.getString("DISTRIBUTOR_NAME"));
			stbListDto.setStrTeableName(rset.getString("TABLENAME"));
			itr=flushListUploadDTO.iterator();
			//logger.info("fetchStatusListData  "+rset.getString("TABLENAME"));
			while(itr.hasNext())
			{
				stbListDtoOne = itr.next();
				if(rset.getString("SERIAL_NO").equalsIgnoreCase(stbListDtoOne.getStrSerialNo()))
				{
					stbListDto.setStrRemarks(stbListDtoOne.getStrRemarks());
				}
			}
			
			stbList.add(stbListDto);

		}
		return stbList;
	}

	private ArrayList<DuplicateSTBDTO> fetchStatusListData(ResultSet rset) throws Exception
	{
		ArrayList<DuplicateSTBDTO> stbList = new ArrayList<DuplicateSTBDTO>();
		DuplicateSTBDTO  stbListDto = null;
		while(rset.next())
		{
			stbListDto = new DuplicateSTBDTO();

			stbListDto.setStrSerialNo(rset.getString("SERIAL_NO"));
			stbListDto.setStrProductName(rset.getString("PRODUCT_NAME"));
			stbListDto.setStrSTBStatus(rset.getString("STATUS"));
			stbListDto.setStrPONumber(rset.getString("PO_NO"));
			stbListDto.setStrPOStatus(rset.getString("PO_STATUS"));
			stbListDto.setStrDCNo(rset.getString("DC_NO"));
			stbListDto.setStrDCDate(rset.getString("DC_DT"));
			stbListDto.setStrInventoryChangeDt(rset.getString("INV_CHANGE_DT"));
			stbListDto.setStrReceivedOn(rset.getString("RECEIVED_ON"));
			stbListDto.setStrStockReceivedDate(rset.getString("ACCEPT_DATE"));
			stbListDto.setStrDistributorId(rset.getString("DISTRIBUTOR_ID"));
			stbListDto.setStrDistOLMId(rset.getString("DISTRIBUTOR_OLM_ID"));
			stbListDto.setStrDistName(rset.getString("DISTRIBUTOR_NAME"));
			stbListDto.setStrTeableName(rset.getString("TABLENAME"));
			stbList.add(stbListDto);

		}
		return stbList;
	}
	@SuppressWarnings("finally")
	public Map uploadFreshExcel(InputStream inputstream)
	{
		List<DuplicateSTBDTO> list = new ArrayList<DuplicateSTBDTO>();
		List<DuplicateSTBDTO> list2 = new ArrayList<DuplicateSTBDTO>();
		List<DuplicateSTBDTO> list3 = new ArrayList<DuplicateSTBDTO>();
		HashMap<String,ArrayList<DuplicateSTBDTO>> returnMap = new HashMap<String ,ArrayList<DuplicateSTBDTO>>();

		HashMap<String,List> errorMap = new HashMap<String ,List>();

		DuplicateSTBDTO flushUploadDto  = null;
		DuplicateSTBDTO flushUploadErrDto  = null;
		String serialNumber ="";

		ArrayList<DuplicateSTBDTO> flushListUploadDTO = new ArrayList<DuplicateSTBDTO>();
		ArrayList<DuplicateSTBDTO> queryListUpload = new ArrayList<DuplicateSTBDTO>();
		ArrayList<DuplicateSTBDTO> stbList = new ArrayList<DuplicateSTBDTO>();

		ResultSet rst =null;
		boolean validateFlag =true;
		try
		{
			  HSSFWorkbook workbook = new HSSFWorkbook(inputstream);
			  HSSFSheet sheet = workbook.getSheetAt(0);
			  Iterator rows = sheet.rowIterator();
			  HSSFRow row;
			  Iterator cells;
			  int totalrows = sheet.getLastRowNum()+1;
			  logger.info("Total Rows == "+sheet.getLastRowNum());
			  if(totalrows > Integer.parseInt(ResourceReader.getValueFromBundle(com.ibm.virtualization.recharge.common.Constants.BULK_UPLAOD_MAX_LIMIT , com.ibm.virtualization.recharge.common.Constants.WEBSERVICE_RESOURCE_BUNDLE)))
			  {
				  flushUploadErrDto = new DuplicateSTBDTO();
				  flushUploadErrDto.setErr_msg("Limit exceeds: Maximum "+Integer.parseInt(ResourceReader.getValueFromBundle(com.ibm.virtualization.recharge.common.Constants.BULK_UPLAOD_MAX_LIMIT , com.ibm.virtualization.recharge.common.Constants.WEBSERVICE_RESOURCE_BUNDLE))+" Serial Numbers are allowed in a file.");
	      		  list.add(flushUploadErrDto);
	      		errorMap.put("error", list);
     			return errorMap;
			  }


			  int rowNumber = 1;

	       //   ArrayList checkDuplicate = new ArrayList();
	          int k=1;

	          while (rows.hasNext()) {
	        	  flushUploadDto = new DuplicateSTBDTO();
	        	  row = (HSSFRow) rows.next();
	        	  cells = row.cellIterator();
	        	 if(rowNumber>1){

	        	  int columnIndex = 0;
	              int cellNo = 0;
	              if(cells != null)
	              {

	          		HSSFCell cell;
	          		String cellValue="";

	            	while (cells.hasNext()) {
	        		  if(cellNo < 14)
	        		  {
	        			  cellNo++;
	        			   cell = (HSSFCell) cells.next();

	        			  	columnIndex = cell.getColumnIndex();
		              		//if(columnIndex > 13)
		              		if(columnIndex > 14)	 //aman-remarks to be added 
		              		{
		              			validateFlag =false;
		              			flushUploadErrDto = new DuplicateSTBDTO();
		              			//flushUploadErrDto.setErr_msg("File should contain only 13 data column");
		              			flushUploadErrDto.setErr_msg("File should contain only 14 data column");
		              			list.add(flushUploadErrDto);
		              			errorMap.put("error", list);
		                		return errorMap;
		              		}
		              		 cellValue = null;

		            		switch(cell.getCellType()) {
			            		case HSSFCell.CELL_TYPE_NUMERIC:
			            			cellValue = String.valueOf((double)cell.getNumericCellValue());
			            			logger.info("numeric cell");
			            		break;
			            		case HSSFCell.CELL_TYPE_STRING:
			            			cellValue = cell.getStringCellValue();
			            			logger.info("String cell");
			            		break;
			            		case HSSFCell.CELL_TYPE_BLANK:
			            			cellValue=":::";
			            			logger.info("blank cell");
			            			break;
		            		}

		            		logger.info("cellNo : "+cellNo+ " , cellValue : "+cellValue+" , columnIndex : "+columnIndex);

		            		logger.info("Row Number == "+rowNumber +" and Cell No. === "+cellNo +" and value == "+cellValue );
		            		if(cellNo==1){
		            			if(cellValue ==  null || "".equalsIgnoreCase(cellValue.trim()))
			            		{
			            			validateFlag =false;
			            			flushUploadErrDto = new DuplicateSTBDTO();
			            			flushUploadErrDto.setErr_msg("Serial Number is missing");
			              			list3.add(flushUploadErrDto);
			              			errorMap.put("error", list3);
			                		return errorMap;
			               		}
		            			serialNumber=serialNumber+"'"+cellValue.trim()+"'"+",";
		            			flushUploadDto.setStrSerialNo(cellValue.trim());
			        	      logger.info("serialNumber : "+serialNumber);

		            		}else if(cellNo==2){


		            				flushUploadDto.setStrProductName(cellValue.trim());


		            				  logger.info("PRD NAME : "+cellValue);
		            		}else if(cellNo==3){

		            				flushUploadDto.setStrSTBStatus(cellValue.trim());
		            				  logger.info("STB ST : "+cellValue);

		            		}else if(cellNo==4){

		            				flushUploadDto.setStrPONumber(cellValue.trim());
		            				  logger.info("PO NO : "+cellValue);

		            		}
		            		else if(cellNo==5){

		            				flushUploadDto.setStrPOStatus(cellValue.trim());
		            				  logger.info("PO ST : "+cellValue);

	            		}
		            		else if(cellNo==6){

	            				flushUploadDto.setStrDCNo(cellValue.trim());
	            				  logger.info("DC NO : "+cellValue);
	            		}
		            		else if(cellNo==7){

	            				flushUploadDto.setStrDCDate(cellValue.trim());
	            				  logger.info("DC DT : "+cellValue);
	            		}
		            		else if(cellNo==8){


		            				flushUploadDto.setStrInventoryChangeDt(cellValue.trim());
		            				  logger.info("INV CHNG : "+cellValue);

	            		}
		            		else if(cellNo==9){

	            				flushUploadDto.setStrReceivedOn(cellValue.trim());
	            				  logger.info("REC ON : "+cellValue);
	            		}
		            		else if(cellNo==10){

	            				flushUploadDto.setStrStockReceivedDate(cellValue.trim());
	            				  logger.info("STK RC : "+cellValue);
	            		}
		            		else if(cellNo==11){
			        	        	  		flushUploadDto.setStrDistOLMId(cellValue.trim());
			        	        logger.info("OLM ID : "+cellValue);
	            		}
		            		else if(cellNo==12){
		            			flushUploadDto.setStrDistName(cellValue.trim());

		            			  logger.info("Dist Name : "+cellValue);
		            		}
		            		
		            		//aman
		            		else if(cellNo==13){

		            			flushUploadDto.setStrRemarks(cellValue);

		            			logger.info("Remarks : "+cellValue);
		            		}
		            		// end by aman


		            		else if(cellNo==14){

		            			if(cellValue.equalsIgnoreCase("Y"))
		            			{
		            				flushListUploadDTO.add(flushUploadDto);
		            				  logger.info("Action  : "+cellValue);
		            			}


		            		}
		            	
	        		  }
	        		  else
	        		  {
	        			  validateFlag =false;
	        			  flushUploadErrDto = new DuplicateSTBDTO();
	        			  //flushUploadErrDto.setErr_msg("File should contain only 13 data column");
	        			  flushUploadErrDto.setErr_msg("File should contain only 14 data column");//aman
            			 list.add(flushUploadErrDto);
            			 errorMap.put("error", list);
                 			return errorMap;
	        		  }
          		}
	        	   rowNumber++;
	            }
	            else
	            {
	            	flushUploadErrDto = new DuplicateSTBDTO();
	            	flushUploadErrDto.setErr_msg("File should contain only fourteen data column");//have to confirm
       			list.add(flushUploadErrDto);
       			errorMap.put("error", list);
     			return errorMap;
	            }
	              logger.info("In Row num == "+ (rowNumber-1) +" Error Message ==  "+validateFlag);

	              k++;
	        }else if(rowNumber==1){

	        	//For Header Checking
	        	int columnIndex = 0;
	              int cellNo = 0;
	              if(cells != null)
	              {
	            	  while (cells.hasNext()) {
		        		  if(cellNo < 15)
		        		  {
		        			  cellNo++;
		        			  HSSFCell cell = (HSSFCell) cells.next();

		        			  	columnIndex = cell.getColumnIndex();
			              		if(columnIndex > 14)
			              		{
			              			validateFlag =false;
			              			flushUploadErrDto = new DuplicateSTBDTO();
			              			flushUploadErrDto.setErr_msg("File should contain only fourteen data column");
			              			list3.add(flushUploadErrDto);
			              			errorMap.put("error", list3);
		                 			return errorMap;
			              		}
			              		String cellValue = null;

			            		switch(cell.getCellType()) {
				            		case HSSFCell.CELL_TYPE_NUMERIC:
				            			cellValue = String.valueOf((long)cell.getNumericCellValue());
				            		break;
				            		case HSSFCell.CELL_TYPE_STRING:
				            			cellValue = cell.getStringCellValue();
					            	break;
			            		}
			            		if(cellValue ==  null || "".equalsIgnoreCase(cellValue.trim()))
			            		{
			            			validateFlag =false;
			            			flushUploadErrDto = new DuplicateSTBDTO();
			            			flushUploadErrDto.setErr_msg("File should contain All Required Values");
			              			list3.add(flushUploadErrDto);
			              			 errorMap.put("error", list3);
		                 			return errorMap;
			               		}

			            		logger.info("Row Number == "+rowNumber +" and Cell No. === "+cellNo +" and value == "+cellValue.trim().toLowerCase() );
			            		if(cellNo==1){
			            			if(!"Serial No.".equalsIgnoreCase(cellValue.trim()))
				            		{
			            				validateFlag =false;
			            				flushUploadErrDto = new DuplicateSTBDTO();
			            				flushUploadErrDto.setErr_msg("Invalid Header For Serial No.");
				              			list.add(flushUploadErrDto);

			            			}
			            		}else if(cellNo==2){
			            			if(!"Product Name".equalsIgnoreCase(cellValue.trim()))
				            		{
			            				validateFlag =false;
			            				flushUploadErrDto = new DuplicateSTBDTO();
			            				flushUploadErrDto.setErr_msg("Invalid Header For Product Name");
				              			list.add(flushUploadErrDto);

			            			}
			            		}else if(cellNo==3){
			            			if(!"Status".equalsIgnoreCase(cellValue.trim()))
				            		{
			            				validateFlag =false;
			            				flushUploadErrDto = new DuplicateSTBDTO();
			            				flushUploadErrDto.setErr_msg("Invalid Header For Status");
				              			list.add(flushUploadErrDto);

			            			}
			            		}else if(cellNo==4){
			            			if(!"PO Number".equalsIgnoreCase(cellValue.trim()))
				            		{
			            				validateFlag =false;
			            				flushUploadErrDto = new DuplicateSTBDTO();
			            				flushUploadErrDto.setErr_msg("Invalid Header For PO Number");
				              			list.add(flushUploadErrDto);

			            			}
			            		}
			            		else if(cellNo==5){
			            			if(!"PO Status".equalsIgnoreCase(cellValue.trim()))
				            		{
			            				validateFlag =false;
			            				flushUploadErrDto = new DuplicateSTBDTO();
			            				flushUploadErrDto.setErr_msg("Invalid Header For PO Status");
				              			list.add(flushUploadErrDto);

			            			}
			            		}
			            		else if(cellNo==6){
			            			if(!"Forward DC Number".equalsIgnoreCase(cellValue.trim()))
				            		{
			            				validateFlag =false;
			            				flushUploadErrDto = new DuplicateSTBDTO();
			            				flushUploadErrDto.setErr_msg("Invalid Header For Forward DC Number");
				              			list.add(flushUploadErrDto);

			            			}
			            		}
			            		else if(cellNo==7){
			            			if(!"Forward DC Date".equalsIgnoreCase(cellValue.trim()))
				            		{
			            				validateFlag =false;
			            				flushUploadErrDto = new DuplicateSTBDTO();
			            				flushUploadErrDto.setErr_msg("Invalid Header For Forward DC Date");
				              			list.add(flushUploadErrDto);

			            			}
			            		}
			            		else if(cellNo==8){
			            			if(!"Inventory Change Date".equalsIgnoreCase(cellValue.trim()))
				            		{
			            				validateFlag =false;
			            				flushUploadErrDto = new DuplicateSTBDTO();
			            				flushUploadErrDto.setErr_msg("Invalid Header For Inventory Change Date");
				              			list.add(flushUploadErrDto);

			            			}
			            		}
			            		else if(cellNo==9){
			            			if(!"Received On".equalsIgnoreCase(cellValue.trim()))
				            		{
			            				validateFlag =false;
			            				flushUploadErrDto = new DuplicateSTBDTO();
			            				flushUploadErrDto.setErr_msg("Invalid Header For Received On");
				              			list.add(flushUploadErrDto);

			            			}
			            		}
			            		else if(cellNo==10){
			            			if(!"Stock Received Date".equalsIgnoreCase(cellValue.trim()))
				            		{
			            				validateFlag =false;
			            				flushUploadErrDto = new DuplicateSTBDTO();
			            				flushUploadErrDto.setErr_msg("Invalid Header For Stock Received Date");
				              			list.add(flushUploadErrDto);

			            			}
			            		}
			            		else if(cellNo==11){
			            			if(!"Distributor OLM ID".equalsIgnoreCase(cellValue.trim()))
				            		{
			            				validateFlag =false;
			            				flushUploadErrDto = new DuplicateSTBDTO();
			            				flushUploadErrDto.setErr_msg("Invalid Header For Distributor OLM ID");
				              			list.add(flushUploadErrDto);

			            			}
			            		}
			            		else if(cellNo==12){
			            			if(!"Distributor Name".equalsIgnoreCase(cellValue.trim()))
				            		{
			            				validateFlag =false;
			            				flushUploadErrDto = new DuplicateSTBDTO();
			            				flushUploadErrDto.setErr_msg("Invalid Header For Distributor Name");
				              			list.add(flushUploadErrDto);

			            			}
			            		}
			            		
			            		//aman
			            		
			            		else if(cellNo==13){
			            			if(!"Remarks".equalsIgnoreCase(cellValue.trim()))
				            		{
			            				validateFlag =false;
			            				flushUploadErrDto = new DuplicateSTBDTO();
			            				flushUploadErrDto.setErr_msg("Invalid Header For Remarks");
				              			list.add(flushUploadErrDto);

			            			}
			            		}
			            		
			            		//end by aman

			            		else if(cellNo==14){
			            			if(!"Action (Y/N)".equalsIgnoreCase(cellValue.trim()))
				            		{
			            				validateFlag =false;
			            				flushUploadErrDto = new DuplicateSTBDTO();
			            				flushUploadErrDto.setErr_msg("Invalid Header For Action");
				              			list.add(flushUploadErrDto);

			            			}
			            		}
		        		  }else{
		        			  validateFlag =false;
		        			  flushUploadErrDto = new DuplicateSTBDTO();
		        			  //flushUploadErrDto.setErr_msg("File should contain only 13 data Headers");
		        			  flushUploadErrDto.setErr_msg("File should contain only 14 data Headers");//aman
		        			list3.add(flushUploadErrDto);
		        			errorMap.put("error", list3);
                 			return errorMap;
		        		  }
	            	  }
	            	 // if(cellNo !=13){
	            		if(cellNo !=14){//aman
		            		validateFlag =false;
		            		flushUploadErrDto = new DuplicateSTBDTO();
		            		//flushUploadErrDto.setErr_msg("File should contain 13 data Headers");
		            		flushUploadErrDto.setErr_msg("File should contain 14 data Headers");//aman
		            		list3.add(flushUploadErrDto);
		            		errorMap.put("error", list3);
                 			return errorMap;
		            	}
	              }
	        	rowNumber ++;
	         }
	     }
	          	logger.info("K == "+k);				
				try {

					if(validateFlag)
					{
						int itrLen=0;
						Iterator<DuplicateSTBDTO> itr2 = flushListUploadDTO.iterator();
						serialNumber=serialNumber+"'"+0+"'";
						
						
						//stbList=getStatusList(serialNumber); //Ram1
						stbList=getStatusList(serialNumber,flushListUploadDTO);// RAm
						if(stbList.size()==0)
						{
							flushUploadErrDto = new DuplicateSTBDTO();
			            	  flushUploadErrDto.setErr_msg("Serial Number can not be flushed as required STB are not present");
				            	 list3.add(flushUploadErrDto);
				            	 errorMap.put("error", list3);
		                 			return errorMap;

						}
						boolean isMismatched = false;
						int dbSTBListCount=stbList.size();
						while (itr2.hasNext()) {
							DuplicateSTBDTO objUploadDTO = (DuplicateSTBDTO) itr2.next();
							Iterator<DuplicateSTBDTO> itr1 = stbList.iterator();
							itrLen=0;
							int ii=1;
							DuplicateSTBDTO objQueryDTO=null;
							while (itr1.hasNext()) {
								 objQueryDTO = (DuplicateSTBDTO) itr1.next();
								 ii = objQueryDTO.compareTo(objUploadDTO);
								if(ii == 0) {
									queryListUpload.add(objQueryDTO);
									break;
								}
								else
								{
									++itrLen;									
								}
							}
							if(itrLen==dbSTBListCount)
							{
								isMismatched=true;
								flushUploadErrDto = new DuplicateSTBDTO();
				            	flushUploadErrDto.setErr_msg("Serial Number "+objUploadDTO.getStrSerialNo()+" can not be flushed as STB details are not matching.");
					            list3.add(flushUploadErrDto);
					            errorMap.put("error", list3);
			                 	
							}
						}
						if(isMismatched)
						{
							return errorMap;
						}
					}
				} catch (Exception e) {
					e.printStackTrace();
					logger.error("Exception occured while validating uploaded Excel & DP data. Exception Message: " + e.getMessage());
				}
			if(k==1){
	    	flushUploadErrDto = new DuplicateSTBDTO();
	    	flushUploadErrDto.setErr_msg("Uploaded file is Blank, it can not be processed.");
      		  list.add(flushUploadErrDto);
      		errorMap.put("error", list);
 			return errorMap;
	        }
	    }

		catch (Exception e) {

        	logger.info(e);
        	e.printStackTrace();
        }
		finally
		{
        	DBConnectionManager.releaseResources(null, rst);
        	
        	if(list3.size()!=0)
        		{errorMap.put("error", list3);
        		return errorMap;}
        	else if(list.size()!=0)
	        	{errorMap.put("error", list);
     			return errorMap;}
        	else if(list2.size()!=0)
	        	{errorMap.put("error", list2);
     			return errorMap;}
        	else
        	{
        		if(queryListUpload.size()==0)
        		{
        			flushUploadErrDto = new DuplicateSTBDTO();
	            	  flushUploadErrDto.setErr_msg("Please select atleast one Y in Action");
		            	 list3.add(flushUploadErrDto);
		            	 errorMap.put("error", list3);
              			return errorMap;
        		}
        		else
        		{
        			returnMap.put("query",stbList);
        			returnMap.put("upload",queryListUpload);

        			return returnMap;
        		}
        	}
		}
	}

	public boolean validateProductName(String productName)
	{
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		int count =0;
		boolean flag = false;
		try{

			logger.info("inside --- validateProductName  productName == "+productName);
			pstmt = connection.prepareStatement(SQL_SELECT_PRODUCT_SECURITY);
			pstmt.setString(1,productName.toUpperCase());
			rs =pstmt.executeQuery();
			if(rs.next()){

				count = rs.getInt(1);
			}
			if(count >=1)
				flag =  true;
			else
				flag =  false;
			}catch (Exception e)
		{
			e.printStackTrace();
			logger.info("Inside main Exception of validateProductName method--"+e.getMessage());
		}
			return flag;
	}

	public boolean validateAlphabet(String str)
	{
		if(!ValidatorUtility.isAlfabetSpace(str))
		{
			return false;
		}
		return true;
	}

	public boolean validateSerialNumInGrid(String str)
	{
		return true;
	}
	public boolean validateOlmId(String str)
	{
		if(str.length() != 8)
		{
			return false;
		}


		return true;
	}
	public ResultSet validateDistOlmId(String distOlmId)
	{
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try{

			logger.info("inside --- validateDistOlmId  and dist OLM id == "+distOlmId);
			pstmt = connection.prepareStatement(SQL_SELECT_SECURITY_OLMID);
			pstmt.setString(1,distOlmId);
			rs =pstmt.executeQuery();
			}catch (Exception e)
		{
			e.printStackTrace();
			logger.info("Inside main Exception of validateDistOlmId method--"+e.getMessage());
		}
		return rs;
	}

	@SuppressWarnings("finally")
	/*public String flushDPSerialNumbers(List<DuplicateSTBDTO> flushList,List<DuplicateSTBDTO> queryList) throws Exception
	{
		logger.info("asa::::::::::::::::;stbflushoutdaoimpl:::::::flushDPSerialNumbers");
		ListIterator<DuplicateSTBDTO> flushListItr = flushList.listIterator();
		String statusDP="Flush Out completed successfully";
		ArrayList<DuplicateSTBDTO> stbList = new ArrayList<DuplicateSTBDTO>();
		PreparedStatement pstmtDP = null;
		PreparedStatement pstmtDPErr = null;
		DuplicateSTBDTO flushUploadDto=null;
		String serialNumber="";
		ArrayList<String> releaseList = new ArrayList<String>();
		
		String activeStockSTBs="";
		Connection conCPE = null;
		
		String is_scm_flush=ResourceReader.getCoreResourceBundleValue(com.ibm.virtualization.recharge.common.Constants.IS_SCM_FLUSH);
		logger.info("asa::::is_scm_fllush:::::"+is_scm_flush);
		
		 try
		 {
			conCPE = DPCPEDBConnection.getDBConnectionCPENew();
						
			while(flushListItr.hasNext()) 
			{
				flushUploadDto=(DuplicateSTBDTO) flushListItr.next();
				serialNumber=serialNumber+"'"+flushUploadDto.getStrSerialNo()+"'"+",";
				if(flushUploadDto.getStrTeableName().equalsIgnoreCase("DP_STOCK_INVENTORY"))
				{
					logger.info("asa::::::::::::::::;stbflushoutdaoimpl:::::::flushDPSerialNumbers :::flushList:::DP_STOCK_INVENTORY");
					// Updating CPE Tables ------------------------------------------------------
					
					ResultSet rsetDP = null;	
				
					String strNo = null;
				
					PreparedStatement pstmtCPE = null;
					ResultSet rsetCPE = null;
					
					int intStatusDP = 0;
					int intStatusCPE = 0;
					
					strNo = flushUploadDto.getStrSerialNo(); //dpBoTreeFlushOutDetail.getSerialNumber();
					
					logger.info("FLUSHING SR NO  ::  "+strNo);
					
					pstmtDP = connection.prepareStatement(SR_NO_AVAILABLE_CHECK_DP_STOCK_INVENTORY);
					
					pstmtDP.setString(1, strNo);
					rsetDP = pstmtDP.executeQuery();
					if(rsetDP.next())
					{
						intStatusDP = rsetDP.getInt(1);				
					}
				
					DBConnectionManager.releaseResources(pstmtDP, rsetDP);
					
					logger.info("Sr num STATUS in DP_STOCK_INV == "+intStatusDP);
					
					// Check STB STATUS is RESTRICTED or UNASSIGNED_PENDING
					if (intStatusDP==Constants.STATUS_UNASSIGNED_COMPLETE )    // STATUS_UNASSIGNED_COMPLETE = 1
					{
						// STB STATUS is UNASSIGNED_COMPLETE
						String cpeUser= ResourceReader
						.getCoreResourceBundleValue(com.ibm.virtualization.recharge.common.Constants.DP_CPE_USER);
						String SR_NO_STATUS_CPE = "select CPE_INVENTORY_STATUS_KEY from "+cpeUser+".CPE_INVENTORY_DETAILS WHERE ASSET_SERIAL_NO=?";
						
						pstmtCPE = conCPE.prepareStatement(SR_NO_STATUS_CPE);
						pstmtCPE.setString(1, strNo);
						rsetCPE = pstmtCPE.executeQuery();
						
						// Check STB Present in CPE
						if(rsetCPE.next())
						{
							intStatusCPE = rsetCPE.getInt(1);
							
							DBConnectionManager.releaseResources(pstmtCPE, rsetCPE);
							logger.info("STB STATUS in CPE  ::  "+intStatusCPE);
							
							// CPE STB Status is UNASSIGNED COMPLETE
							if(intStatusCPE==Constants.STATUS_UNASSIGNED_COMPLETE) // STATUS_UNASSIGNED_COMPLETE = 1
							{
								String UPDATE_STB_CPE = "update "+cpeUser+".CPE_INVENTORY_DETAILS set CPE_INVENTORY_STATUS_KEY=? WHERE ASSET_SERIAL_NO=?";
								
								pstmtCPE = conCPE.prepareStatement(UPDATE_STB_CPE);
								pstmtCPE.setInt(1, Constants.STATUS_RESTRICTED);
								pstmtCPE.setString(2, strNo);
								pstmtCPE.executeUpdate();
								
								DBConnectionManager.releaseResources(pstmtCPE, null);
								
								pstmtCPE = conCPE.prepareStatement(MOVE_CPE_SERIAL_NUMBER_TO_HIST);
								pstmtCPE.setString(1, strNo);
								pstmtCPE.executeUpdate();
								
								DBConnectionManager.releaseResources(pstmtCPE, null);
								
								pstmtCPE = conCPE.prepareStatement(DELETE_CPE_SERIAL_NUMBER_TO_HIST);
								pstmtCPE.setString(1, strNo);
								pstmtCPE.executeUpdate();
							
								DBConnectionManager.releaseResources(pstmtCPE, null);
								
								//aman--add remarks and flushout id in query
								pstmtDP = connection.prepareStatement(MOVETO_DP_STOCK_INVENTORY_ASSIGNED);
								pstmtDP.setString(1, flushUploadDto.getStrSerialNo());
								pstmtDP.setString(2, flushUploadDto.getStrRemarks());//remarks
								pstmtDP.setString(1, flushUploadDto.getStrSerialNo());//dist id
								pstmtDP.setString(4, is_scm_flush);//scm flag
								
								pstmtDP.executeUpdate();
								DBConnectionManager.releaseResources(pstmtDP, null);
	
								pstmtDP = connection.prepareStatement(DELETE_DP_STOCK_INVENTORY);
								pstmtDP.setString(1, flushUploadDto.getStrSerialNo());
								pstmtDP.executeUpdate();
								DBConnectionManager.releaseResources(pstmtDP, null);
							}
							// CPE STB Status is Restricted
							else if(intStatusCPE==Constants.STATUS_ASSIGNED)   //STATUS_ASSIGNED 3
							{
								//stop flush out
								activeStockSTBs = activeStockSTBs+","+flushUploadDto.getStrSerialNo();
								logger.info("Sr num STATUS already activated by CPE == "+strNo);						
								DBConnectionManager.releaseResources(pstmtDP, rsetDP);
								DBConnectionManager.releaseResources(pstmtCPE, rsetCPE);
								continue;
							}
							else
							{   
								// CPE STB Status is Activated
								pstmtCPE = conCPE.prepareStatement(MOVE_CPE_SERIAL_NUMBER_TO_HIST);
								pstmtCPE.setString(1, strNo);
								pstmtCPE.executeUpdate();
								
								DBConnectionManager.releaseResources(pstmtCPE, null);
								
								pstmtCPE = conCPE.prepareStatement(DELETE_CPE_SERIAL_NUMBER_TO_HIST);
								pstmtCPE.setString(1, strNo);
								pstmtCPE.executeUpdate();
								
								DBConnectionManager.releaseResources(pstmtCPE, null);
								
								//aman--add remarks and flushout id in query
								pstmtDP = connection.prepareStatement(MOVETO_DP_STOCK_INVENTORY_ASSIGNED);
								pstmtDP.setString(1, flushUploadDto.getStrSerialNo());
								pstmtDP.executeUpdate();
								DBConnectionManager.releaseResources(pstmtDP, null);
	
								pstmtDP = connection.prepareStatement(DELETE_DP_STOCK_INVENTORY);
								pstmtDP.setString(1, flushUploadDto.getStrSerialNo());
								pstmtDP.executeUpdate();
								DBConnectionManager.releaseResources(pstmtDP, null);
							}							
						}
					}
					//STB STATUS IS NOT UNASSIGNED COMPLETE
					else
					{
						//aman--add remarks and flushout id in query
						pstmtDP = connection.prepareStatement(MOVETO_DP_STOCK_INVENTORY_ASSIGNED);
						pstmtDP.setString(1, flushUploadDto.getStrSerialNo());
						pstmtDP.executeUpdate();
						DBConnectionManager.releaseResources(pstmtDP, null);
	
						pstmtDP = connection.prepareStatement(DELETE_DP_STOCK_INVENTORY);
						pstmtDP.setString(1, flushUploadDto.getStrSerialNo());
						pstmtDP.executeUpdate();
						DBConnectionManager.releaseResources(pstmtDP, null);
					}
					DBConnectionManager.releaseResources(pstmtDPErr,null);
					flushUploadDto.setConfStatus(1);
					stbList.add(flushUploadDto);
					continue;
				}
				else if(flushUploadDto.getStrTeableName().equalsIgnoreCase("DP_ERROR_REV_DETAILS"))
				{
					logger.info("asa::::::::::::::::;stbflushoutdaoimpl:::::::flushDPSerialNumbers ::::::DP_ERROR_REV_DETAILS");
					// FOR UAT & SIT environment
					java.sql.Date invDate = Utility.getSqlDateFromString(flushUploadDto.getStrInventoryChangeDt(), "dd-MMM-yyyy");
					//FOR Development as DB2 is 9.5 not compitable with MMM date format
				//	java.sql.Date invDate = Utility.getSqlDateFromString(flushUploadDto.getStrInventoryChangeDt(), "dd-MM-yyyy");
				    logger.info(flushUploadDto.getStrSerialNo()+" :: "+ invDate);
					pstmtDPErr = connection.prepareStatement(MOVE_DP_ERROR_REV_DETAIL2);
					pstmtDPErr.setString(1, flushUploadDto.getStrSerialNo());
					pstmtDPErr.setDate(2, invDate);
					pstmtDPErr.executeUpdate();
					DBConnectionManager.releaseResources(pstmtDPErr, null);
		
					// Moving records from DP_REV_INVENTORY_CHANGE when Error STBs are flushed.
					
					//aman--add remarks and flushout id in query
					pstmtDP = connection.prepareStatement(MOVETO_DP_REV_INVENTORY_CHANGE_HIST_ERROR_REV);
					pstmtDP.setString(1, flushUploadDto.getStrSerialNo());
					pstmtDP.executeUpdate();
		
					DBConnectionManager.releaseResources(pstmtDP, null);
		
					pstmtDP = connection.prepareStatement(DELETE_DP_REV_INVENTORY_CHANGE_ERROR_REV);
					pstmtDP.setString(1, flushUploadDto.getStrSerialNo());
					pstmtDP.executeUpdate();
		
					pstmtDPErr = connection.prepareStatement(DELETE_DP_ERROR_REV_DETAIL3);
					pstmtDPErr.setString(1, flushUploadDto.getStrSerialNo());
					pstmtDPErr.setDate(2, invDate);
					pstmtDPErr.executeUpdate();
					DBConnectionManager.releaseResources(pstmtDPErr, null);

				}
				else if(flushUploadDto.getStrTeableName().equalsIgnoreCase("DP_REV_CHURN_INVENTORY"))
				{
					logger.info("asa::::::::::::::::;stbflushoutdaoimpl:::::::flushDPSerialNumbers ::::::DP_REV_CHURN_INVENTORY");
					ResultSet rset=null;
					String status="";
					pstmtDP = connection.prepareStatement("select status from DP_REV_CHURN_INVENTORY where serial_number =?");
		
					pstmtDP.setString(1, flushUploadDto.getStrSerialNo());
					//pstmtDP.setDate(2, new java.sql.Date(invDate.getTime()));
		
					rset = pstmtDP.executeQuery();
		
					if(rset.next())
					{
						status=rset.getString("status");
					}
		
					if(status.trim().equalsIgnoreCase("REC")||status.trim().equalsIgnoreCase("COL")||status.trim().equalsIgnoreCase("ERR"))
					{
						//aman--add remarks and flushout id in query
						pstmtDP = connection.prepareStatement(MOVE_DP_REV_CHURN_INVENTORY);
						pstmtDP.setString(1, flushUploadDto.getStrSerialNo());
						pstmtDP.executeUpdate();
						DBConnectionManager.releaseResources(pstmtDP, null);
		
						pstmtDP = connection.prepareStatement("delete from DP_REV_CHURN_INVENTORY where SERIAL_NUMBER=?");
						pstmtDP.setString(1, flushUploadDto.getStrSerialNo());
						pstmtDP.executeUpdate();
						DBConnectionManager.releaseResources(pstmtDP, null);
		
						flushUploadDto.setConfStatus(7);
						stbList.add(flushUploadDto);

						DBConnectionManager.releaseResources(pstmtDPErr,null);
		
						continue;
					}
				}
				else
				{
					logger.info("asa::::::::::::::::;stbflushoutdaoimpl:::::::flushDPSerialNumbers ::::::elseeeeeeeeeeeeeeee");
						ResultSet rset=null;
						String status="";
		
						if(flushUploadDto.getStrInventoryChangeDt()!=null && (!(flushUploadDto.getStrInventoryChangeDt().equals(""))))
						{
							pstmtDP = connection.prepareStatement("select status from DP_REV_INVENTORY_CHANGE where DEFECTIVE_SR_NO=?  and DATE(INV_CHANGE_DATE) =?");
							logger.error("----"+flushUploadDto.getStrInventoryChangeDt());
							// FOR UAT & SIT environment
							java.sql.Date invDate = Utility.getSqlDateFromString(flushUploadDto.getStrInventoryChangeDt(), "dd-MMM-yyyy");
							// FOR Development as DB2 is 9.5 not compitable with MMM date format
						//	java.sql.Date invDate = Utility.getSqlDateFromString(flushUploadDto.getStrInventoryChangeDt(), "dd-MM-yyyy");
							logger.error("invDate::::::::"+invDate);
		
							pstmtDP.setString(1, flushUploadDto.getStrSerialNo());
							pstmtDP.setDate(2, invDate);
							rset = pstmtDP.executeQuery();
		
							if(rset.next())
							{
								status=rset.getString("status");
							}
							if(status.trim().equalsIgnoreCase("REC"))
							{
								//aman--add remarks and flushout id in query
								pstmtDP = connection.prepareStatement(MOVETO_DP_REV_INVENTORY_CHANGE_HISTORY2);
								pstmtDP.setString(1, flushUploadDto.getStrSerialNo());
								pstmtDP.setDate(2, invDate);
								pstmtDP.executeUpdate();
		
								DBConnectionManager.releaseResources(pstmtDP, null);
		
								pstmtDP = connection.prepareStatement(DELETE_DP_REV_INVENTORY_CHANGE2);
								pstmtDP.setString(1, flushUploadDto.getStrSerialNo());
								pstmtDP.setDate(2, invDate);
								pstmtDP.executeUpdate();
								
								flushUploadDto.setConfStatus(2);
								stbList.add(flushUploadDto);
							
								DBConnectionManager.releaseResources(pstmtDPErr,null);
								continue;
							}
						}
						pstmtDP = connection.prepareStatement("select status from DP_REV_STOCK_INVENTORY where SERIAL_NO_COLLECT=? ");
						pstmtDP.setString(1, flushUploadDto.getStrSerialNo());
						rset = pstmtDP.executeQuery();
		
						if(rset.next())
						{
							status=rset.getString("status");
						}
						if(status.trim().equalsIgnoreCase("COL")||status.trim().equalsIgnoreCase("ERR"))
						{
							//aman--add remarks and flushout id in query
							pstmtDP = connection.prepareStatement(MOVE_TO_REV_STOCK_INV_HIST);
							pstmtDP.setString(1, flushUploadDto.getStrSerialNo());
							pstmtDP.executeUpdate();

							DBConnectionManager.releaseResources(pstmtDP, null);
		
							//aman--add remarks and flushout id in query
							pstmtDP = connection.prepareStatement(MOVETO_DP_REV_INVENTORY_CHANGE_HISTORY);
							pstmtDP.setString(1, flushUploadDto.getStrSerialNo());//inv
							pstmtDP.executeUpdate();
		
							DBConnectionManager.releaseResources(pstmtDP, null);
		
							pstmtDP = connection.prepareStatement(DELETE_REV_STOCK_INV);
							pstmtDP.setString(1, flushUploadDto.getStrSerialNo());
							pstmtDP.executeUpdate();
		
							DBConnectionManager.releaseResources(pstmtDP, null);
		
							pstmtDP = connection.prepareStatement(DELETE_DP_REV_INVENTORY_CHANGE);
							pstmtDP.setString(1, flushUploadDto.getStrSerialNo());
							pstmtDP.executeUpdate();
		
							DBConnectionManager.releaseResources(pstmtDP, null);
		
							flushUploadDto.setConfStatus(3);
							stbList.add(flushUploadDto);
							continue;
						}
					}
				}

				Map<String, Integer> uploadCount = new HashMap<String, Integer>();
				for (DuplicateSTBDTO duplicateSTBDTO : flushList) 
				{
					if(uploadCount.containsKey(duplicateSTBDTO.getStrSerialNo()))
					{
						uploadCount.put(duplicateSTBDTO.getStrSerialNo(), uploadCount.get(duplicateSTBDTO.getStrSerialNo()) + 1);
					}
					else
					{
						uploadCount.put(duplicateSTBDTO.getStrSerialNo(),1);
					}
				}

				Map<String, Integer> queryCount = new HashMap<String, Integer>();
		
				for (DuplicateSTBDTO duplicateSTBDTO : queryList) 
				{
					if(queryCount.containsKey(duplicateSTBDTO.getStrSerialNo()))
					{
						queryCount.put(duplicateSTBDTO.getStrSerialNo(), queryCount.get(duplicateSTBDTO.getStrSerialNo()) + 1);
					}
					else
					{
						queryCount.put(duplicateSTBDTO.getStrSerialNo(),1);
					}
				}

				for (Map.Entry<String, Integer> entry1 : uploadCount.entrySet())
				{
					for (Map.Entry<String, Integer> entry2 : queryCount.entrySet())
					{
						String str1=entry1.getKey();
						String str2=entry2.getKey();
	
						if(str2.equalsIgnoreCase(str1))
						{
							releaseList.add(entry2.getKey());
						}
					}
				}
			
				if(statusDP.equalsIgnoreCase("Flush Out completed successfully"))
				{
					serialNumber=serialNumber.substring(0,serialNumber.length()-1);
					String[] STBNos = serialNumber.split(",");
					int totalFlushCount = 0;
					for(int ii=0; ii< STBNos.length ; ii++)
					{
						totalFlushCount++;
					}
					statusDP= totalFlushCount+" STBs flushed successfully.";
					
					// Message if STB in active state in CPE
					if(!"".equals(activeStockSTBs))
					{
						activeStockSTBs = activeStockSTBs.replaceFirst(",","");
						logger.info("STBs("+ activeStockSTBs +") Cann't be Flushed as it is Activated in CPE.");
						statusDP = statusDP+" STBs("+ activeStockSTBs +") Cann't be Flushed as it is Activated in CPE.";
					}
					
					serialNumber = serialNumber + ",'"+0+"'";
					
					releaseSTB(releaseList);
				}
	 		}
	 		catch(Exception e)
			{		
				e.printStackTrace();
				statusDP="Flush could not be completed";
				logger.error(e);
				logger.error(e.getMessage());
				try
				{
					String err = ResourceReader.getCoreResourceBundleValue(com.ibm.dp.common.Constants.STB_Bulk_Flush_Out_Critical);
					logger.info(err + "::" +com.ibm.utilities.Utility.getCurrentDate());
				}
				catch(Exception ex)
				{
					ex.printStackTrace();
				}
				throw new DAOException(e.getMessage());
			}
			finally
			{	
				DBConnectionManager.releaseResources(conCPE);
				return statusDP;
			}
		}*/
	
	//added by aman
	
	public String flushDPSerialNumbers(List<DuplicateSTBDTO> flushList,List<DuplicateSTBDTO> queryList, String loginUserId) throws Exception
	{
		ListIterator<DuplicateSTBDTO> flushListItr = flushList.listIterator();
		String statusDP="Flush Out completed successfully";
		ArrayList<DuplicateSTBDTO> stbList = new ArrayList<DuplicateSTBDTO>();
		PreparedStatement pstmtDP = null;
		PreparedStatement pstmtDPErr = null;
		PreparedStatement scmFlag = null;//aman
		ResultSet rs=null;//aman
		DuplicateSTBDTO flushUploadDto=null;
		String serialNumber="";
		ArrayList<String> releaseList = new ArrayList<String>();
		
		String activeStockSTBs="";
		Connection conCPE = null;
		
		PreparedStatement pstmtClosing = null;//Neetika
		PreparedStatement pstmtClosingDef = null;//Neetika
		PreparedStatement pstmtClosingUp = null;//Neetika
		PreparedStatement pstmtClosingchurn = null;//Neetika
		ResultSet rsClosing=null;//Neetika
		int reco=0;
		String is_scm_flush="";//ResourceReader.getCoreResourceBundleValue(com.ibm.virtualization.recharge.common.Constants.IS_SCM_FLUSH);
		//logger.info("is_scm_fllush:::::"+is_scm_flush);
		
		 try
		 {
			conCPE = DPCPEDBConnection.getDBConnectionCPENew();
			
			String sql="select dd_value from DP_CONFIGURATION_DETAILS where CONFIG_ID=28 and id=2 with ur";
			scmFlag =connection.prepareStatement(sql);
			rs=scmFlag.executeQuery();
			
			
			String sqlSerializedStockQuery="update reco_serialized_stock set FLUSHOUT_FLAG='Y',UPDATED_ON=current timestamp where (FLUSHOUT_FLAG is null or FLUSHOUT_FLAG='') and RECO_ID in  (select ID from dp_reco_period where IS_RECO_CLOSED!=1 ) and  SERIAL_NO=?";
			pstmtClosing =connection.prepareStatement(sqlSerializedStockQuery);
			
			String sqlDefStockQuery="update reco_defective_stock set FLUSHOUT_FLAG='Y' ,UPDATED_ON=current timestamp where (FLUSHOUT_FLAG is null or FLUSHOUT_FLAG='') and RECO_ID in  (select ID from dp_reco_period where IS_RECO_CLOSED!=1 ) and  DEFECTIVE_SR_NO=?";
			pstmtClosingDef =connection.prepareStatement(sqlDefStockQuery);
			
			String sqlUpgradeStockQuery="update reco_upgrade_stock set FLUSHOUT_FLAG='Y',UPDATED_ON=current timestamp where (FLUSHOUT_FLAG is null or FLUSHOUT_FLAG='') and RECO_ID in  (select ID from dp_reco_period where IS_RECO_CLOSED!=1 ) and  DEFECTIVE_SR_NO=?";
			pstmtClosingUp =connection.prepareStatement(sqlUpgradeStockQuery);
			
			String sqlChurnStockQuery="update reco_churn_stock set FLUSHOUT_FLAG='Y',UPDATED_ON=current timestamp where (FLUSHOUT_FLAG is null or FLUSHOUT_FLAG='') and RECO_ID in  (select ID from dp_reco_period where IS_RECO_CLOSED!=1 ) and  DEFECTIVE_SR_NO=?";
			pstmtClosingchurn =connection.prepareStatement(sqlChurnStockQuery);
			
			
			while(rs.next())
			{
				is_scm_flush = rs.getString("DD_VALUE");
				logger.info("scm flag::::::::::::"+is_scm_flush);
			}
			
						
			while(flushListItr.hasNext()) 
			{
				flushUploadDto=(DuplicateSTBDTO) flushListItr.next();
				serialNumber=serialNumber+"'"+flushUploadDto.getStrSerialNo()+"'"+",";
				if(flushUploadDto.getStrTeableName().equalsIgnoreCase("DP_STOCK_INVENTORY"))
				{
					// Updating CPE Tables ------------------------------------------------------
					
					ResultSet rsetDP = null;	
				
					String strNo = null;
				
					PreparedStatement pstmtCPE = null;
					ResultSet rsetCPE = null;
					
					int intStatusDP = 0;
					int intStatusCPE = 0;
					
					strNo = flushUploadDto.getStrSerialNo(); //dpBoTreeFlushOutDetail.getSerialNumber();
					
					logger.info("FLUSHING SR NO  ::  "+strNo);
					
					pstmtDP = connection.prepareStatement(SR_NO_AVAILABLE_CHECK_DP_STOCK_INVENTORY);
					
					
					pstmtDP.setString(1, strNo);
					rsetDP = pstmtDP.executeQuery();
					if(rsetDP.next())
					{
						intStatusDP = rsetDP.getInt(1);				
					}
				
					
					
					DBConnectionManager.releaseResources(pstmtDP, rsetDP);
					
					logger.info("Sr num STATUS in DP_STOCK_INV == "+intStatusDP);
					
					// Check STB STATUS is RESTRICTED or UNASSIGNED_PENDING
					if (intStatusDP==Constants.STATUS_UNASSIGNED_COMPLETE )    // STATUS_UNASSIGNED_COMPLETE = 1
					{
						// STB STATUS is UNASSIGNED_COMPLETE
						String cpeUser= ResourceReader
						.getCoreResourceBundleValue(com.ibm.virtualization.recharge.common.Constants.DP_CPE_USER);
						String SR_NO_STATUS_CPE = "select CPE_INVENTORY_STATUS_KEY from "+cpeUser+".CPE_INVENTORY_DETAILS WHERE ASSET_SERIAL_NO=?";
						
						pstmtCPE = conCPE.prepareStatement(SR_NO_STATUS_CPE);
						pstmtCPE.setString(1, strNo);
						rsetCPE = pstmtCPE.executeQuery();
						//logger.info("CPE me query maara == "+SR_NO_STATUS_CPE);
						
						// Check STB Present in CPE
						if(rsetCPE.next())
						{
							
							intStatusCPE = rsetCPE.getInt(1);
							
							DBConnectionManager.releaseResources(pstmtCPE, rsetCPE);
							logger.info("STB STATUS in CPE  ::  "+intStatusCPE);
							
							// CPE STB Status is UNASSIGNED COMPLETE
							if(intStatusCPE==Constants.STATUS_UNASSIGNED_COMPLETE) // STATUS_UNASSIGNED_COMPLETE = 1
							{
								
								String MOVETO_DP_STOCK_INVENTORY_ASSIGNED99 = "Insert into DP_STOCK_INVENTORY_ASSIGNED "+
								" (PRODUCT_ID,SERIAL_NO,MARK_DAMAGED,DISTRIBUTOR_ID,DISTRIBUTOR_PURCHASE_DATE,FSE_ID ,FSE_PURCHASE_DATE,RETAILER_ID,RETAILER_PURCHASE_DATE,DAMAGE_REMARKS,DAMAGED_BY,DAMAGE_COST, "+
								" INV_NO,REMARKS,MSISDN,TRANSACTION_ID,STATUS,ASSIGN_DATE,CUSTOMER_ID,IS_SEC_SALE, UPDATED_ON, FLUSHOUT_FLAG,FLUSHOUT_REMARKS,FLUSHOUT_ID,IS_SCM_FLUSH) select "+
								" PRODUCT_ID,SERIAL_NO,MARK_DAMAGED,DISTRIBUTOR_ID,DISTRIBUTOR_PURCHASE_DATE,FSE_ID,FSE_PURCHASE_DATE,RETAILER_ID,RETAILER_PURCHASE_DATE,DAMAGE_REMARKS,DAMAGED_BY,DAMAGE_COST, "+
								" INV_NO,REMARKS,MSISDN,TRANSACTION_ID,STATUS,CURRENT_TIMESTAMP,CUSTOMER_ID,IS_SEC_SALE, current timestamp,-2,'"+flushUploadDto.getStrRemarks()+"',"+Integer.parseInt(loginUserId)+",'"+is_scm_flush+"' from DP_STOCK_INVENTORY  where SERIAL_NO=? with ur ";

								
								
								
								
								String UPDATE_STB_CPE = "update "+cpeUser+".CPE_INVENTORY_DETAILS set CPE_INVENTORY_STATUS_KEY=? WHERE ASSET_SERIAL_NO=?";
								
								pstmtCPE = conCPE.prepareStatement(UPDATE_STB_CPE);
								pstmtCPE.setInt(1, Constants.STATUS_RESTRICTED);
								pstmtCPE.setString(2, strNo);
								pstmtCPE.executeUpdate();
								
								DBConnectionManager.releaseResources(pstmtCPE, null);
								
								pstmtCPE = conCPE.prepareStatement(MOVE_CPE_SERIAL_NUMBER_TO_HIST);
								pstmtCPE.setString(1, strNo);
								pstmtCPE.executeUpdate();
								
								DBConnectionManager.releaseResources(pstmtCPE, null);
								
								pstmtCPE = conCPE.prepareStatement(DELETE_CPE_SERIAL_NUMBER_TO_HIST);
								pstmtCPE.setString(1, strNo);
								pstmtCPE.executeUpdate();
							
								DBConnectionManager.releaseResources(pstmtCPE, null);
								
								
								pstmtDP = connection.prepareStatement(MOVETO_DP_STOCK_INVENTORY_ASSIGNED99);
								/*pstmtDP.setString(1, flushUploadDto.getStrRemarks());//remarks
								pstmtDP.setString(2, loginUserId);//dist id
								pstmtDP.setString(3, is_scm_flush);//scm flag
*/								pstmtDP.setString(1, flushUploadDto.getStrSerialNo());
								
								pstmtDP.executeUpdate();
								DBConnectionManager.releaseResources(pstmtDP, null);
	
								
								pstmtDP = connection.prepareStatement(DELETE_DP_STOCK_INVENTORY);
								pstmtDP.setString(1, flushUploadDto.getStrSerialNo());
								pstmtDP.executeUpdate();
								
								//NEETIKA PUT CODE HERE
								pstmtClosing.setString(1,flushUploadDto.getStrSerialNo());
								pstmtClosing.executeUpdate();
								
								DBConnectionManager.releaseResources(pstmtDP, null);
							}
							// CPE STB Status is Restricted
							else if(intStatusCPE==Constants.STATUS_ASSIGNED)   //STATUS_ASSIGNED 3
							{
								//stop flush out
								activeStockSTBs = activeStockSTBs+","+flushUploadDto.getStrSerialNo();
								logger.info("Sr num STATUS already activated by CPE == "+strNo);						
								DBConnectionManager.releaseResources(pstmtDP, rsetDP);
								DBConnectionManager.releaseResources(pstmtCPE, rsetCPE);
								continue;
							}
							else
							{   
								String MOVETO_DP_STOCK_INVENTORY_ASSIGNED99 = "Insert into DP_STOCK_INVENTORY_ASSIGNED "+
								" (PRODUCT_ID,SERIAL_NO,MARK_DAMAGED,DISTRIBUTOR_ID,DISTRIBUTOR_PURCHASE_DATE,FSE_ID ,FSE_PURCHASE_DATE,RETAILER_ID,RETAILER_PURCHASE_DATE,DAMAGE_REMARKS,DAMAGED_BY,DAMAGE_COST, "+
								" INV_NO,REMARKS,MSISDN,TRANSACTION_ID,STATUS,ASSIGN_DATE,CUSTOMER_ID,IS_SEC_SALE, UPDATED_ON, FLUSHOUT_FLAG,FLUSHOUT_REMARKS,FLUSHOUT_ID,IS_SCM_FLUSH) select "+
								" PRODUCT_ID,SERIAL_NO,MARK_DAMAGED,DISTRIBUTOR_ID,DISTRIBUTOR_PURCHASE_DATE,FSE_ID,FSE_PURCHASE_DATE,RETAILER_ID,RETAILER_PURCHASE_DATE,DAMAGE_REMARKS,DAMAGED_BY,DAMAGE_COST, "+
								" INV_NO,REMARKS,MSISDN,TRANSACTION_ID,STATUS,CURRENT_TIMESTAMP,CUSTOMER_ID,IS_SEC_SALE, current timestamp,-2,'"+flushUploadDto.getStrRemarks()+"',"+Integer.parseInt(loginUserId)+",'"+is_scm_flush+"' from DP_STOCK_INVENTORY  where SERIAL_NO=? with ur ";

								
								
								// CPE STB Status is Activated
								pstmtCPE = conCPE.prepareStatement(MOVE_CPE_SERIAL_NUMBER_TO_HIST);
								pstmtCPE.setString(1, strNo);
								pstmtCPE.executeUpdate();
								
								DBConnectionManager.releaseResources(pstmtCPE, null);
								
								pstmtCPE = conCPE.prepareStatement(DELETE_CPE_SERIAL_NUMBER_TO_HIST);
								pstmtCPE.setString(1, strNo);
								pstmtCPE.executeUpdate();
								
								DBConnectionManager.releaseResources(pstmtCPE, null);
								
								pstmtDP = connection.prepareStatement(MOVETO_DP_STOCK_INVENTORY_ASSIGNED99);
								/*pstmtDP.setString(1, flushUploadDto.getStrRemarks());//remarks
								pstmtDP.setString(2, loginUserId);//dist id
								pstmtDP.setString(3, is_scm_flush);//scm flag
								*/pstmtDP.setString(1, flushUploadDto.getStrSerialNo());
								pstmtDP.executeUpdate();
								DBConnectionManager.releaseResources(pstmtDP, null);
	
								pstmtDP = connection.prepareStatement(DELETE_DP_STOCK_INVENTORY);
								pstmtDP.setString(1, flushUploadDto.getStrSerialNo());
								pstmtDP.executeUpdate();
							
								//NEETIKA PUT CODE HERE
								
								pstmtClosing.setString(1,flushUploadDto.getStrSerialNo());
								reco=pstmtClosing.executeUpdate();
								logger.info(flushUploadDto.getStrSerialNo()+" Serial Number in Serialized Stock table of Reco  == "+reco);
								DBConnectionManager.releaseResources(pstmtDP, null);
							}							
						}
					}
					//STB STATUS IS NOT UNASSIGNED COMPLETE
					else
					{
						
						//private static final
						String MOVETO_DP_STOCK_INVENTORY_ASSIGNED1 = "Insert into DP_STOCK_INVENTORY_ASSIGNED "+
						" (PRODUCT_ID,SERIAL_NO,MARK_DAMAGED,DISTRIBUTOR_ID,DISTRIBUTOR_PURCHASE_DATE,FSE_ID ,FSE_PURCHASE_DATE,RETAILER_ID,RETAILER_PURCHASE_DATE,DAMAGE_REMARKS,DAMAGED_BY,DAMAGE_COST, "+
						" INV_NO,REMARKS,MSISDN,TRANSACTION_ID,STATUS,ASSIGN_DATE,CUSTOMER_ID,IS_SEC_SALE, UPDATED_ON, FLUSHOUT_FLAG,FLUSHOUT_REMARKS,FLUSHOUT_ID,IS_SCM_FLUSH) select "+
						" PRODUCT_ID,SERIAL_NO,MARK_DAMAGED,DISTRIBUTOR_ID,DISTRIBUTOR_PURCHASE_DATE,FSE_ID,FSE_PURCHASE_DATE,RETAILER_ID,RETAILER_PURCHASE_DATE,DAMAGE_REMARKS,DAMAGED_BY,DAMAGE_COST, "+
						" INV_NO,REMARKS,MSISDN,TRANSACTION_ID,STATUS,CURRENT_TIMESTAMP,CUSTOMER_ID,IS_SEC_SALE, current timestamp,-2,'"+flushUploadDto.getStrRemarks()+"',"+Integer.parseInt(loginUserId)+",'"+is_scm_flush+"' from DP_STOCK_INVENTORY  where SERIAL_NO=? with ur ";

						
						pstmtDP = connection.prepareStatement(MOVETO_DP_STOCK_INVENTORY_ASSIGNED1);
						/*pstmtDP.setString(1, flushUploadDto.getStrRemarks());//remarks
						pstmtDP.setInt(2, (Integer.parseInt(loginUserId)));//dist id
						pstmtDP.setString(3, is_scm_flush);//scm flag
						*/
						pstmtDP.setString(1, flushUploadDto.getStrSerialNo());
						
						pstmtDP.executeUpdate();
						DBConnectionManager.releaseResources(pstmtDP, null);
	
						pstmtDP = connection.prepareStatement(DELETE_DP_STOCK_INVENTORY);
						pstmtDP.setString(1, flushUploadDto.getStrSerialNo());
						pstmtDP.executeUpdate();
						//NEETIKA PUT CODE HERE
						pstmtClosing.setString(1,flushUploadDto.getStrSerialNo());
						pstmtClosing.executeUpdate();
						
						DBConnectionManager.releaseResources(pstmtDP, null);
					}
					DBConnectionManager.releaseResources(pstmtDPErr,null);
					flushUploadDto.setConfStatus(1);
					stbList.add(flushUploadDto);
					continue;
				}
				else if(flushUploadDto.getStrTeableName().equalsIgnoreCase("DP_ERROR_REV_DETAILS"))
				{
					// FOR UAT & SIT environment
					java.sql.Date invDate = Utility.getSqlDateFromString(flushUploadDto.getStrInventoryChangeDt(), "dd-MMM-yyyy");
					//FOR Development as DB2 is 9.5 not compitable with MMM date format
				//	java.sql.Date invDate = Utility.getSqlDateFromString(flushUploadDto.getStrInventoryChangeDt(), "dd-MM-yyyy");
				    logger.info(flushUploadDto.getStrSerialNo()+" :: "+ invDate);
					pstmtDPErr = connection.prepareStatement(MOVE_DP_ERROR_REV_DETAIL2);
					pstmtDPErr.setString(1, flushUploadDto.getStrSerialNo());
					pstmtDPErr.setDate(2, invDate);
					pstmtDPErr.executeUpdate();
					DBConnectionManager.releaseResources(pstmtDPErr, null);
		
					// Moving records from DP_REV_INVENTORY_CHANGE when Error STBs are flushed.
					
					String  MOVETO_DP_REV_INVENTORY_CHANGE_HIST_ERROR_REV ="Insert into DP_REV_INVENTORY_CHANGE_HIST(REQ_ID,DEFECTIVE_SR_NO,DEFECTIVE_PRODUCT_ID,DEFECTIVE_DIST_ID,NEW_SR_NO,NEW_PRODUCT_ID,NEW_DIST_D "+
					",INV_CHANGE_DATE,COLLECTION_ID,STATUS,CREATED_ON,UPDATED_ON,ARCHIVED_DATE,IS_SCM,ORIGINAL_DIST_ID,FLUSHOUT_FLAG,ERROR_STATUS,FLUSHOUT_REMARKS,FLUSHOUT_ID,IS_SCM_FLUSH) select REQ_ID,DEFECTIVE_SR_NO,DEFECTIVE_PRODUCT_ID,DEFECTIVE_DIST_ID,NEW_SR_NO,NEW_PRODUCT_ID,NEW_DIST_D"+
					",INV_CHANGE_DATE,COLLECTION_ID,STATUS,CREATED_ON,UPDATED_ON,CURRENT_TIMESTAMP,IS_SCM,ORIGINAL_DIST_ID,-2 ,ERROR_STATUS,'"+flushUploadDto.getStrRemarks()+"',"+Integer.parseInt(loginUserId)+",'"+is_scm_flush+"' from DP_REV_INVENTORY_CHANGE where DEFECTIVE_SR_NO=? and  ERROR_STATUS = 'ERR'";

					
					
					//aman--add remarks and flushout id in query
					pstmtDP = connection.prepareStatement(MOVETO_DP_REV_INVENTORY_CHANGE_HIST_ERROR_REV);
					/*pstmtDP.setString(1, flushUploadDto.getStrRemarks());//remarks
					pstmtDP.setString(2, loginUserId);//dist id
					pstmtDP.setString(3, is_scm_flush);//scm flag
					*/pstmtDP.setString(1, flushUploadDto.getStrSerialNo());
					pstmtDP.executeUpdate();
		
					DBConnectionManager.releaseResources(pstmtDP, null);
		
					pstmtDP = connection.prepareStatement(DELETE_DP_REV_INVENTORY_CHANGE_ERROR_REV);
					pstmtDP.setString(1, flushUploadDto.getStrSerialNo());
					pstmtDP.executeUpdate();
		
					pstmtDPErr = connection.prepareStatement(DELETE_DP_ERROR_REV_DETAIL3);
					pstmtDPErr.setString(1, flushUploadDto.getStrSerialNo());
					pstmtDPErr.setDate(2, invDate);
					pstmtDPErr.executeUpdate();
					
					//NEETIKA PUT CODE HERE
					pstmtClosingDef.setString(1,flushUploadDto.getStrSerialNo());
					reco=pstmtClosingDef.executeUpdate();
					logger.info(flushUploadDto.getStrSerialNo()+" Serial Number in defective Stock table of Reco  == "+reco);
					
					pstmtClosingUp.setString(1,flushUploadDto.getStrSerialNo());
					reco=pstmtClosingUp.executeUpdate();
					logger.info(flushUploadDto.getStrSerialNo()+" Serial Number in upgrade Stock table of Reco  == "+reco);
					
					DBConnectionManager.releaseResources(pstmtDPErr, null);

				}
				else if(flushUploadDto.getStrTeableName().equalsIgnoreCase("DP_REV_CHURN_INVENTORY"))
				{
					
					
					String MOVE_DP_REV_CHURN_INVENTORY1="INSERT INTO DPDTH.DP_REV_CHURN_INVENTORY_HIST(REQ_ID, SERIAL_NUMBER, PRODUCT_ID, VC_ID, CUSTOMER_ID, SI_ID, AGEING, REMARK, STATUS, CIRCLE_ID, CREATED_ON, CREATED_BY, ACTION_DATE, HIST_DATE, PURCHASE_DT, SERVICE_START_DT, FLUSHOUT_FLAG, IS_SCM, ERROR_STATUS,FLUSHOUT_REMARKS,FLUSHOUT_ID,IS_SCM_FLUSH) "+
					" SELECT REQ_ID, SERIAL_NUMBER, PRODUCT_ID, VC_ID, CUSTOMER_ID, SI_ID, AGEING, REMARK, STATUS, CIRCLE_ID, CREATED_ON, CREATED_BY, ACTION_DATE,  current timestamp,PURCHASE_DT, SERVICE_START_DT,-2 , IS_SCM, ERROR_STATUS,'"+flushUploadDto.getStrRemarks()+"',"+Integer.parseInt(loginUserId)+",'"+is_scm_flush+"' "+
					" FROM DPDTH.DP_REV_CHURN_INVENTORY where serial_number=?  ";

					
					ResultSet rset=null;
					String status="";
					pstmtDP = connection.prepareStatement("select status from DP_REV_CHURN_INVENTORY where serial_number =?");
		
					pstmtDP.setString(1, flushUploadDto.getStrSerialNo());
					//pstmtDP.setDate(2, new java.sql.Date(invDate.getTime()));
		
					rset = pstmtDP.executeQuery();
		
					if(rset.next())
					{
						status=rset.getString("status");
					}
		
					if(status.trim().equalsIgnoreCase("REC")||status.trim().equalsIgnoreCase("COL")||status.trim().equalsIgnoreCase("ERR"))
					{
						//aman--add remarks and flushout id in query
						pstmtDP = connection.prepareStatement(MOVE_DP_REV_CHURN_INVENTORY1);
						/*pstmtDP.setString(1, flushUploadDto.getStrRemarks());//remarks
						pstmtDP.setString(2, loginUserId);//dist id
						pstmtDP.setString(3, is_scm_flush);//scm flag
						*/pstmtDP.setString(1, flushUploadDto.getStrSerialNo());
						pstmtDP.executeUpdate();
						DBConnectionManager.releaseResources(pstmtDP, null);
		
						pstmtDP = connection.prepareStatement("delete from DP_REV_CHURN_INVENTORY where SERIAL_NUMBER=?");
						pstmtDP.setString(1, flushUploadDto.getStrSerialNo());
						pstmtDP.executeUpdate();
						DBConnectionManager.releaseResources(pstmtDP, null);
		
						flushUploadDto.setConfStatus(7);
						stbList.add(flushUploadDto);
						//NEETIKA ADD CODE HERE
						pstmtClosingchurn.setString(1,flushUploadDto.getStrSerialNo());
						reco=pstmtClosingchurn.executeUpdate();
						logger.info(flushUploadDto.getStrSerialNo()+" Serial Number in Churn Stock table of Reco  == "+reco);
					
						DBConnectionManager.releaseResources(pstmtDPErr,null);
		
						continue;
					}
				}
				else
				{
					String MOVETO_DP_REV_INVENTORY_CHANGE_HISTORY3 ="Insert into DP_REV_INVENTORY_CHANGE_HIST(REQ_ID,DEFECTIVE_SR_NO,DEFECTIVE_PRODUCT_ID,DEFECTIVE_DIST_ID,NEW_SR_NO,NEW_PRODUCT_ID,NEW_DIST_D "+
					",INV_CHANGE_DATE,COLLECTION_ID,STATUS,CREATED_ON,UPDATED_ON,ARCHIVED_DATE,IS_SCM,ORIGINAL_DIST_ID,FLUSHOUT_FLAG,ERROR_STATUS,FLUSHOUT_REMARKS,FLUSHOUT_ID,IS_SCM_FLUSH) select REQ_ID,DEFECTIVE_SR_NO,DEFECTIVE_PRODUCT_ID,DEFECTIVE_DIST_ID,NEW_SR_NO,NEW_PRODUCT_ID,NEW_DIST_D"+
					",INV_CHANGE_DATE,COLLECTION_ID,STATUS,CREATED_ON,UPDATED_ON,CURRENT_TIMESTAMP,IS_SCM,ORIGINAL_DIST_ID,-2 ,ERROR_STATUS,'"+flushUploadDto.getStrRemarks()+"',"+Integer.parseInt(loginUserId)+",'"+is_scm_flush+"' from DP_REV_INVENTORY_CHANGE where DEFECTIVE_SR_NO=? and DATE(INV_CHANGE_DATE)=?";

					
					
						ResultSet rset=null;
						String status="";
		
						if(flushUploadDto.getStrInventoryChangeDt()!=null && (!(flushUploadDto.getStrInventoryChangeDt().equals(""))))
						{
							
							
							
							pstmtDP = connection.prepareStatement("select status from DP_REV_INVENTORY_CHANGE where DEFECTIVE_SR_NO=?  and DATE(INV_CHANGE_DATE) =?");
							logger.error("----"+flushUploadDto.getStrInventoryChangeDt());
							// FOR UAT & SIT environment
							java.sql.Date invDate = Utility.getSqlDateFromString(flushUploadDto.getStrInventoryChangeDt(), "dd-MMM-yyyy");
							// FOR Development as DB2 is 9.5 not compitable with MMM date format
						//	java.sql.Date invDate = Utility.getSqlDateFromString(flushUploadDto.getStrInventoryChangeDt(), "dd-MM-yyyy");
							logger.error("invDate::::::::"+invDate);
		
							pstmtDP.setString(1, flushUploadDto.getStrSerialNo());
							pstmtDP.setDate(2, invDate);
							rset = pstmtDP.executeQuery();
		
							if(rset.next())
							{
								status=rset.getString("status");
							}
							if(status.trim().equalsIgnoreCase("REC"))
							{
								//aman--add remarks and flushout id in query
								pstmtDP = connection.prepareStatement(MOVETO_DP_REV_INVENTORY_CHANGE_HISTORY3);
								/*pstmtDP.setString(1, flushUploadDto.getStrRemarks());//remarks
								pstmtDP.setString(2, loginUserId);//dist id
								pstmtDP.setString(3, is_scm_flush);//scm flag
								*/pstmtDP.setString(1, flushUploadDto.getStrSerialNo());
								pstmtDP.setDate(2, invDate);
								pstmtDP.executeUpdate();
								
							
								DBConnectionManager.releaseResources(pstmtDP, null);
		
								pstmtDP = connection.prepareStatement(DELETE_DP_REV_INVENTORY_CHANGE2);
								pstmtDP.setString(1, flushUploadDto.getStrSerialNo());
								pstmtDP.setDate(2, invDate);
								pstmtDP.executeUpdate();
								
								flushUploadDto.setConfStatus(2);
								stbList.add(flushUploadDto);
								
								//NEETIKA PUT CODE HERE
								pstmtClosingDef.setString(1,flushUploadDto.getStrSerialNo());
								reco=pstmtClosingDef.executeUpdate();
								logger.info(flushUploadDto.getStrSerialNo()+" Serial Number in defective Stock table of Reco  == "+reco);
								
								pstmtClosingUp.setString(1,flushUploadDto.getStrSerialNo());
								reco=pstmtClosingUp.executeUpdate();
								logger.info(flushUploadDto.getStrSerialNo()+" Serial Number in upgrade Stock table of Reco  == "+reco);
								
							
								DBConnectionManager.releaseResources(pstmtDPErr,null);
								continue;
							}
						}
						
						
						
						pstmtDP = connection.prepareStatement("select status from DP_REV_STOCK_INVENTORY where SERIAL_NO_COLLECT=? ");
						pstmtDP.setString(1, flushUploadDto.getStrSerialNo());
						rset = pstmtDP.executeQuery();
		
						if(rset.next())
						{
							status=rset.getString("status");
						}
						if(status.trim().equalsIgnoreCase("COL")||status.trim().equalsIgnoreCase("ERR"))
						{
							
							
							String MOVE_TO_REV_STOCK_INV_HIST1 = "INSERT INTO DP_REV_STOCK_INVENTORY_HIST" +
							" (COLLECTION_ID, DEFECT_ID, PRODUCT_ID, SERIAL_NO_COLLECT, SERIAL_NO_REPLACE, COLLECTION_DATE, REMARKS, CREATED_ON, CREATED_BY, CIRCLE_ID, STATUS, UPDATED_ON, UPDATED_BY, SCM_STATUS, ARCHIVED_DATE, REPAIR_PRODUCT_ID, REPAIR_REMARK,CUSTOMER_ID,FLUSHOUT_FLAG,FLUSHOUT_REMARKS,FLUSHOUT_ID,IS_SCM_FLUSH )" +
							" (SELECT COLLECTION_ID, DEFECT_ID, PRODUCT_ID, SERIAL_NO_COLLECT, SERIAL_NO_REPLACE, COLLECTION_DATE, REMARKS, CREATED_ON, CREATED_BY, CIRCLE_ID, STATUS, UPDATED_ON, UPDATED_BY, SCM_STATUS, CURRENT_TIMESTAMP, REPAIR_PRODUCT_ID, REPAIR_REMARK ,CUSTOMER_ID,-2,'"+flushUploadDto.getStrRemarks()+"',"+Integer.parseInt(loginUserId)+",'"+is_scm_flush+"' FROM DP_REV_STOCK_INVENTORY where SERIAL_NO_COLLECT=? )";

							
							
							
							//aman--add remarks and flushout id in query
							pstmtDP = connection.prepareStatement(MOVE_TO_REV_STOCK_INV_HIST1);
							/*pstmtDP.setString(1, flushUploadDto.getStrRemarks());//remarks
							pstmtDP.setString(2, loginUserId);//dist id
							pstmtDP.setString(3, is_scm_flush);//scm flag
							*/pstmtDP.setString(1, flushUploadDto.getStrSerialNo());
							pstmtDP.executeUpdate();

							DBConnectionManager.releaseResources(pstmtDP, null);
		
							
							String  MOVETO_DP_REV_INVENTORY_CHANGE_HISTORY9 ="Insert into DP_REV_INVENTORY_CHANGE_HIST(REQ_ID,DEFECTIVE_SR_NO,DEFECTIVE_PRODUCT_ID,DEFECTIVE_DIST_ID,NEW_SR_NO,NEW_PRODUCT_ID,NEW_DIST_D "+

							",INV_CHANGE_DATE,COLLECTION_ID,STATUS,CREATED_ON,UPDATED_ON,ARCHIVED_DATE,IS_SCM,ORIGINAL_DIST_ID,FLUSHOUT_FLAG,ERROR_STATUS,FLUSHOUT_REMARKS,FLUSHOUT_ID,IS_SCM_FLUSH) select REQ_ID,DEFECTIVE_SR_NO,DEFECTIVE_PRODUCT_ID,DEFECTIVE_DIST_ID,NEW_SR_NO,NEW_PRODUCT_ID,NEW_DIST_D"+
							",INV_CHANGE_DATE,COLLECTION_ID,STATUS,CREATED_ON,UPDATED_ON,CURRENT_TIMESTAMP,IS_SCM,ORIGINAL_DIST_ID,-2,ERROR_STATUS,'"+flushUploadDto.getStrRemarks()+"',"+Integer.parseInt(loginUserId)+",'"+is_scm_flush+"' from DP_REV_INVENTORY_CHANGE where DEFECTIVE_SR_NO=? and COLLECTION_ID not in ('2','4')";

							
							
							pstmtDP = connection.prepareStatement(MOVETO_DP_REV_INVENTORY_CHANGE_HISTORY9);
							/*pstmtDP.setString(1, flushUploadDto.getStrRemarks());//remarks
							pstmtDP.setString(2, loginUserId);//dist id
							pstmtDP.setString(3, is_scm_flush);//scm flag
							*/pstmtDP.setString(1, flushUploadDto.getStrSerialNo());//inv
							pstmtDP.executeUpdate();
		
							DBConnectionManager.releaseResources(pstmtDP, null);
		
							pstmtDP = connection.prepareStatement(DELETE_REV_STOCK_INV);
							pstmtDP.setString(1, flushUploadDto.getStrSerialNo());
							pstmtDP.executeUpdate();
		
							DBConnectionManager.releaseResources(pstmtDP, null);
		
							pstmtDP = connection.prepareStatement(DELETE_DP_REV_INVENTORY_CHANGE);
							pstmtDP.setString(1, flushUploadDto.getStrSerialNo());
							pstmtDP.executeUpdate();
							//NEETIKA PUT CODE HERE
							pstmtClosingDef.setString(1,flushUploadDto.getStrSerialNo());
							reco=pstmtClosingDef.executeUpdate();
							logger.info(flushUploadDto.getStrSerialNo()+" Serial Number in defective Stock table of Reco  == "+reco);
							
							pstmtClosingUp.setString(1,flushUploadDto.getStrSerialNo());
							reco=pstmtClosingUp.executeUpdate();
							logger.info(flushUploadDto.getStrSerialNo()+" Serial Number in upgrade Stock table of Reco  == "+reco);
							
							DBConnectionManager.releaseResources(pstmtDP, null);
		
							flushUploadDto.setConfStatus(3);
							stbList.add(flushUploadDto);
							continue;
						}
					}
				}

				Map<String, Integer> uploadCount = new HashMap<String, Integer>();
				for (DuplicateSTBDTO duplicateSTBDTO : flushList) 
				{
					if(uploadCount.containsKey(duplicateSTBDTO.getStrSerialNo()))
					{
						uploadCount.put(duplicateSTBDTO.getStrSerialNo(), uploadCount.get(duplicateSTBDTO.getStrSerialNo()) + 1);
					}
					else
					{
						uploadCount.put(duplicateSTBDTO.getStrSerialNo(),1);
					}
				}

				Map<String, Integer> queryCount = new HashMap<String, Integer>();
		
				for (DuplicateSTBDTO duplicateSTBDTO : queryList) 
				{
					if(queryCount.containsKey(duplicateSTBDTO.getStrSerialNo()))
					{
						queryCount.put(duplicateSTBDTO.getStrSerialNo(), queryCount.get(duplicateSTBDTO.getStrSerialNo()) + 1);
					}
					else
					{
						queryCount.put(duplicateSTBDTO.getStrSerialNo(),1);
					}
				}

				for (Map.Entry<String, Integer> entry1 : uploadCount.entrySet())
				{
					for (Map.Entry<String, Integer> entry2 : queryCount.entrySet())
					{
						String str1=entry1.getKey();
						String str2=entry2.getKey();
	
						if(str2.equalsIgnoreCase(str1))
						{
							releaseList.add(entry2.getKey());
						}
					}
				}
			
				if(statusDP.equalsIgnoreCase("Flush Out completed successfully"))
				{
					System.out.println("oh hello.."+serialNumber);
					serialNumber=serialNumber.substring(0,serialNumber.length()-1);
				
					String[] STBNos = serialNumber.split(",");
					int totalFlushCount = 0;
					for(int ii=0; ii< STBNos.length ; ii++)
					{
						totalFlushCount++;
					}
					statusDP= totalFlushCount+" STBs flushed successfully.";
					
					// Message if STB in active state in CPE
					if(!"".equals(activeStockSTBs))
					{
						activeStockSTBs = activeStockSTBs.replaceFirst(",","");
						logger.info("STBs("+ activeStockSTBs +") Cann't be Flushed as it is Activated in CPE.");
						statusDP = statusDP+" STBs("+ activeStockSTBs +") Cann't be Flushed as it is Activated in CPE.";
					}
					
					serialNumber = serialNumber + ",'"+0+"'";
					
					releaseSTB(releaseList);
				}
	 		}
	 		catch(Exception e)
			{		
				e.printStackTrace();
				statusDP="Flush could not be completed";
				logger.error(e);
				logger.error(e.getMessage());
				try
				{
					String err = ResourceReader.getCoreResourceBundleValue(com.ibm.dp.common.Constants.STB_Bulk_Flush_Out_Critical);
					logger.info(err + "::" +com.ibm.utilities.Utility.getCurrentDate());
				}
				catch(Exception ex)
				{
					ex.printStackTrace();
				}
				throw new DAOException(e.getMessage());
			}
			finally
			{	
				DBConnectionManager.releaseResources(scmFlag ,null);
				DBConnectionManager.releaseResources(pstmtClosing ,rsClosing);
				DBConnectionManager.releaseResources(pstmtClosingchurn ,null);
				DBConnectionManager.releaseResources(pstmtClosingDef ,null);
				DBConnectionManager.releaseResources(pstmtClosingUp ,null);
				DBConnectionManager.releaseResources(conCPE);//test
				return statusDP;
			}
		}
	
	
	//end of changes by aman
	
	public Map<String, String> flushDPSerialNumbersCloseReco(List<DuplicateSTBDTO> flushList,List<DuplicateSTBDTO> queryList, String loginUserId,String reco) throws Exception
	{
		ListIterator<DuplicateSTBDTO> flushListItr = flushList.listIterator();
		String statusDP="Flush Out completed successfully";
		ArrayList<DuplicateSTBDTO> stbList = new ArrayList<DuplicateSTBDTO>();
		PreparedStatement pstmtDP = null;
		PreparedStatement pstmtDPErr = null;
		PreparedStatement scmFlag = null;//aman
		ResultSet rs=null;//aman
		DuplicateSTBDTO flushUploadDto=null;
		String serialNumber="";
		ArrayList<String> releaseList = new ArrayList<String>();
		PreparedStatement pstmtClosing = null;//Neetika
		PreparedStatement pstmtClosingDef = null;//Neetika
		PreparedStatement pstmtClosingUp = null;//Neetika
		PreparedStatement pstmtClosingchurn = null;//Neetika
		ResultSet rsClosing=null;//Neetika
		String activeStockSTBs="";
		Connection conCPE = null; //1
		Map<String, String> flushoutSno = new HashMap<String, String>(); //Neetika
		String is_scm_flush="";//ResourceReader.getCoreResourceBundleValue(com.ibm.virtualization.recharge.common.Constants.IS_SCM_FLUSH);
		//logger.info("is_scm_fllush:::::"+is_scm_flush);
		String flushoutFlag=null;
		String flushoutFlagtemp=null;
		 try
		 {
			conCPE = DPCPEDBConnection.getDBConnectionCPENew();
			
			String sql="select dd_value from DP_CONFIGURATION_DETAILS where CONFIG_ID=28 and id=2 with ur";
			scmFlag =connection.prepareStatement(sql);
			rs=scmFlag.executeQuery();
			
			String sqlSerializedStockQuery="select FLUSHOUT_FLAG from reco_serialized_stock where reco_id=? and SERIAL_NO=? with ur";
			pstmtClosing =connection.prepareStatement(sqlSerializedStockQuery);
			
			String sqlDefStockQuery="select FLUSHOUT_FLAG from reco_defective_stock where reco_id=? and DEFECTIVE_SR_NO=? with ur";
			pstmtClosingDef =connection.prepareStatement(sqlDefStockQuery);
			
			String sqlUpgradeStockQuery="select FLUSHOUT_FLAG from reco_upgrade_stock where reco_id=? and DEFECTIVE_SR_NO=? with ur";
			pstmtClosingUp =connection.prepareStatement(sqlUpgradeStockQuery);
			
			String sqlChurnStockQuery="select FLUSHOUT_FLAG from reco_churn_stock where reco_id=? and DEFECTIVE_SR_NO=? with ur";
			pstmtClosingchurn =connection.prepareStatement(sqlChurnStockQuery);
			
			while(rs.next())
			{
				is_scm_flush = rs.getString("DD_VALUE");
				logger.info("scm flag::::::::::::"+is_scm_flush);
			}
			
						
			while(flushListItr.hasNext()) 
			{
				try
				{
				flushoutFlagtemp=null;
				flushoutFlag=null;
				flushUploadDto=(DuplicateSTBDTO) flushListItr.next();
				serialNumber=serialNumber+"'"+flushUploadDto.getStrSerialNo()+"'"+",";
				//logger.info(" SR NO  Ser::  "+flushUploadDto.getStrSerialNo()+"   reco "+reco);
				//Neetika put code here to check in 4 tables
				//Serialized Table
				pstmtClosing.setString(1, reco);
				pstmtClosing.setString(2, flushUploadDto.getStrSerialNo());
				rsClosing=pstmtClosing.executeQuery();
				if(rsClosing.next())
				{
					flushoutFlagtemp=rsClosing.getString("FLUSHOUT_FLAG");
					if(flushoutFlagtemp!=null && !(flushoutFlagtemp.trim().equalsIgnoreCase("")))
					{
						flushoutFlag=flushoutFlagtemp;
						//logger.info("flushoutFlag SR NO  Ser::  "+flushoutFlag);
					}
					
				}
				//Defective table
				pstmtClosingDef.setString(1, reco);
				pstmtClosingDef.setString(2, flushUploadDto.getStrSerialNo());
				rsClosing=pstmtClosingDef.executeQuery();
				if(rsClosing.next())
				{
					flushoutFlagtemp=rsClosing.getString("FLUSHOUT_FLAG");
					if(flushoutFlagtemp!=null && !(flushoutFlagtemp.trim().equalsIgnoreCase("")))
					{
						flushoutFlag=flushoutFlagtemp;
						//logger.info("flushoutFlag SR NO  Def::"+flushoutFlag);
					}
				
				}
				//Upgrade table
				pstmtClosingUp.setString(1, reco);
				pstmtClosingUp.setString(2, flushUploadDto.getStrSerialNo());
				rsClosing=pstmtClosingUp.executeQuery();
				if(rsClosing.next())
				{
					flushoutFlagtemp=rsClosing.getString("FLUSHOUT_FLAG");
					if(flushoutFlagtemp!=null && !(flushoutFlagtemp.trim().equalsIgnoreCase("")))
					{
						flushoutFlag=flushoutFlagtemp;
						//logger.info("flushoutFlag SR NO  upgrade::  "+flushoutFlag);
					}
				}
				//Churn table
				pstmtClosingchurn.setString(1, reco);
				pstmtClosingchurn.setString(2, flushUploadDto.getStrSerialNo());
				rsClosing=pstmtClosingchurn.executeQuery();
				if(rsClosing.next())
				{
					flushoutFlagtemp=rsClosing.getString("FLUSHOUT_FLAG");
					if(flushoutFlagtemp!=null && !(flushoutFlagtemp.trim().equalsIgnoreCase("")))
					{
						flushoutFlag=flushoutFlagtemp;
						//logger.info("flushoutFlag SR NO  churn::  "+flushoutFlag);
					}
				}
				if(flushoutFlag==null)
				{
				if(flushUploadDto.getStrTeableName().equalsIgnoreCase("DP_STOCK_INVENTORY"))
				{
					// Updating CPE Tables ------------------------------------------------------
					
					ResultSet rsetDP = null;	
				
					String strNo = null;
				
					PreparedStatement pstmtCPE = null;
					ResultSet rsetCPE = null;
					
					int intStatusDP = 0;
					int intStatusCPE = 0;
					
					strNo = flushUploadDto.getStrSerialNo(); //dpBoTreeFlushOutDetail.getSerialNumber();
					
					logger.info("FLUSHING SR NO  ::  "+strNo);
					
					pstmtDP = connection.prepareStatement(SR_NO_AVAILABLE_CHECK_DP_STOCK_INVENTORY);
					
					
					pstmtDP.setString(1, strNo);
					rsetDP = pstmtDP.executeQuery();
					if(rsetDP.next())
					{
						intStatusDP = rsetDP.getInt(1);				
					}
				
					
					
					DBConnectionManager.releaseResources(pstmtDP, rsetDP);
					
					//logger.info("Sr num STATUS in DP_STOCK_INV == "+intStatusDP);
					
					// Check STB STATUS is RESTRICTED or UNASSIGNED_PENDING
					if (intStatusDP==Constants.STATUS_UNASSIGNED_COMPLETE )    // STATUS_UNASSIGNED_COMPLETE = 1
					{
						// STB STATUS is UNASSIGNED_COMPLETE
						String cpeUser= ResourceReader
						.getCoreResourceBundleValue(com.ibm.virtualization.recharge.common.Constants.DP_CPE_USER);
						String SR_NO_STATUS_CPE = "select CPE_INVENTORY_STATUS_KEY from "+cpeUser+".CPE_INVENTORY_DETAILS WHERE ASSET_SERIAL_NO=?";
						
						pstmtCPE = conCPE.prepareStatement(SR_NO_STATUS_CPE);
						pstmtCPE.setString(1, strNo);
						rsetCPE = pstmtCPE.executeQuery();
						//logger.info("CPE me query maara == "+SR_NO_STATUS_CPE);
						
						// Check STB Present in CPE
						if(rsetCPE.next())
						{
							
							intStatusCPE = rsetCPE.getInt(1);
							
							DBConnectionManager.releaseResources(pstmtCPE, rsetCPE);
							//logger.info("STB STATUS in CPE  ::  "+intStatusCPE);
							
							// CPE STB Status is UNASSIGNED COMPLETE
							if(intStatusCPE==Constants.STATUS_UNASSIGNED_COMPLETE) // STATUS_UNASSIGNED_COMPLETE = 1
							{
								
								String MOVETO_DP_STOCK_INVENTORY_ASSIGNED99 = "Insert into DP_STOCK_INVENTORY_ASSIGNED "+
								" (PRODUCT_ID,SERIAL_NO,MARK_DAMAGED,DISTRIBUTOR_ID,DISTRIBUTOR_PURCHASE_DATE,FSE_ID ,FSE_PURCHASE_DATE,RETAILER_ID,RETAILER_PURCHASE_DATE,DAMAGE_REMARKS,DAMAGED_BY,DAMAGE_COST, "+
								" INV_NO,REMARKS,MSISDN,TRANSACTION_ID,STATUS,ASSIGN_DATE,CUSTOMER_ID,IS_SEC_SALE, UPDATED_ON, FLUSHOUT_FLAG,FLUSHOUT_REMARKS,FLUSHOUT_ID,IS_SCM_FLUSH) select "+
								" PRODUCT_ID,SERIAL_NO,MARK_DAMAGED,DISTRIBUTOR_ID,DISTRIBUTOR_PURCHASE_DATE,FSE_ID,FSE_PURCHASE_DATE,RETAILER_ID,RETAILER_PURCHASE_DATE,DAMAGE_REMARKS,DAMAGED_BY,DAMAGE_COST, "+
								" INV_NO,REMARKS,MSISDN,TRANSACTION_ID,STATUS,CURRENT_TIMESTAMP,CUSTOMER_ID,IS_SEC_SALE, current timestamp,-2,'"+flushUploadDto.getStrRemarks()+"',"+Integer.parseInt(loginUserId)+",'"+is_scm_flush+"' from DP_STOCK_INVENTORY  where SERIAL_NO=? with ur ";

								
								
								
								
								
								String UPDATE_STB_CPE = "update "+cpeUser+".CPE_INVENTORY_DETAILS set CPE_INVENTORY_STATUS_KEY=? WHERE ASSET_SERIAL_NO=?";
								
								pstmtCPE = conCPE.prepareStatement(UPDATE_STB_CPE);
								pstmtCPE.setInt(1, Constants.STATUS_RESTRICTED);
								pstmtCPE.setString(2, strNo);
								pstmtCPE.executeUpdate();
								
								DBConnectionManager.releaseResources(pstmtCPE, null);
								
								pstmtCPE = conCPE.prepareStatement(MOVE_CPE_SERIAL_NUMBER_TO_HIST);
								pstmtCPE.setString(1, strNo);
								pstmtCPE.executeUpdate();
								
								DBConnectionManager.releaseResources(pstmtCPE, null);
								
								pstmtCPE = conCPE.prepareStatement(DELETE_CPE_SERIAL_NUMBER_TO_HIST);
								pstmtCPE.setString(1, strNo);
								pstmtCPE.executeUpdate();
							
								DBConnectionManager.releaseResources(pstmtCPE, null);
								
								
								pstmtDP = connection.prepareStatement(MOVETO_DP_STOCK_INVENTORY_ASSIGNED99);
								/*pstmtDP.setString(1, flushUploadDto.getStrRemarks());//remarks
								pstmtDP.setString(2, loginUserId);//dist id
								pstmtDP.setString(3, is_scm_flush);//scm flag
*/								pstmtDP.setString(1, flushUploadDto.getStrSerialNo());
								
								pstmtDP.executeUpdate();
								DBConnectionManager.releaseResources(pstmtDP, null);
	
								
								pstmtDP = connection.prepareStatement(DELETE_DP_STOCK_INVENTORY);
								pstmtDP.setString(1, flushUploadDto.getStrSerialNo());
								pstmtDP.executeUpdate();
								
								//NEETIKA PUT CODE HERE
								
								
								
								DBConnectionManager.releaseResources(pstmtDP, null);
							}
							// CPE STB Status is Restricted
							else if(intStatusCPE==Constants.STATUS_ASSIGNED)   //STATUS_ASSIGNED 3
							{
								//stop flush out
								activeStockSTBs = activeStockSTBs+","+flushUploadDto.getStrSerialNo();
								//logger.info("Sr num STATUS already activated by CPE == "+strNo);
								flushoutSno.put(flushUploadDto.getStrSerialNo(),"N"); //Neetika on 17 June
								DBConnectionManager.releaseResources(pstmtDP, rsetDP);
								DBConnectionManager.releaseResources(pstmtCPE, rsetCPE);
								continue;
							}
							else
							{   
								String MOVETO_DP_STOCK_INVENTORY_ASSIGNED99 = "Insert into DP_STOCK_INVENTORY_ASSIGNED "+
								" (PRODUCT_ID,SERIAL_NO,MARK_DAMAGED,DISTRIBUTOR_ID,DISTRIBUTOR_PURCHASE_DATE,FSE_ID ,FSE_PURCHASE_DATE,RETAILER_ID,RETAILER_PURCHASE_DATE,DAMAGE_REMARKS,DAMAGED_BY,DAMAGE_COST, "+
								" INV_NO,REMARKS,MSISDN,TRANSACTION_ID,STATUS,ASSIGN_DATE,CUSTOMER_ID,IS_SEC_SALE, UPDATED_ON, FLUSHOUT_FLAG,FLUSHOUT_REMARKS,FLUSHOUT_ID,IS_SCM_FLUSH) select "+
								" PRODUCT_ID,SERIAL_NO,MARK_DAMAGED,DISTRIBUTOR_ID,DISTRIBUTOR_PURCHASE_DATE,FSE_ID,FSE_PURCHASE_DATE,RETAILER_ID,RETAILER_PURCHASE_DATE,DAMAGE_REMARKS,DAMAGED_BY,DAMAGE_COST, "+
								" INV_NO,REMARKS,MSISDN,TRANSACTION_ID,STATUS,CURRENT_TIMESTAMP,CUSTOMER_ID,IS_SEC_SALE, current timestamp,-2,'"+flushUploadDto.getStrRemarks()+"',"+Integer.parseInt(loginUserId)+",'"+is_scm_flush+"' from DP_STOCK_INVENTORY  where SERIAL_NO=? with ur ";

								
								
								// CPE STB Status is Activated
								pstmtCPE = conCPE.prepareStatement(MOVE_CPE_SERIAL_NUMBER_TO_HIST);
								pstmtCPE.setString(1, strNo);
								pstmtCPE.executeUpdate();
								
								DBConnectionManager.releaseResources(pstmtCPE, null);
								
								pstmtCPE = conCPE.prepareStatement(DELETE_CPE_SERIAL_NUMBER_TO_HIST);
								pstmtCPE.setString(1, strNo);
								pstmtCPE.executeUpdate();
								
								DBConnectionManager.releaseResources(pstmtCPE, null);
								
								pstmtDP = connection.prepareStatement(MOVETO_DP_STOCK_INVENTORY_ASSIGNED99);
								/*pstmtDP.setString(1, flushUploadDto.getStrRemarks());//remarks
								pstmtDP.setString(2, loginUserId);//dist id
								pstmtDP.setString(3, is_scm_flush);//scm flag
								*/pstmtDP.setString(1, flushUploadDto.getStrSerialNo());
								pstmtDP.executeUpdate();
								DBConnectionManager.releaseResources(pstmtDP, null);
	
								pstmtDP = connection.prepareStatement(DELETE_DP_STOCK_INVENTORY);
								pstmtDP.setString(1, flushUploadDto.getStrSerialNo());
								pstmtDP.executeUpdate();
								//NEETIKA PUT CODE HERE
								DBConnectionManager.releaseResources(pstmtDP, null);
							}							
						}
					}
					//STB STATUS IS NOT UNASSIGNED COMPLETE
					else
					{
						
						//private static final
						String MOVETO_DP_STOCK_INVENTORY_ASSIGNED1 = "Insert into DP_STOCK_INVENTORY_ASSIGNED "+
						" (PRODUCT_ID,SERIAL_NO,MARK_DAMAGED,DISTRIBUTOR_ID,DISTRIBUTOR_PURCHASE_DATE,FSE_ID ,FSE_PURCHASE_DATE,RETAILER_ID,RETAILER_PURCHASE_DATE,DAMAGE_REMARKS,DAMAGED_BY,DAMAGE_COST, "+
						" INV_NO,REMARKS,MSISDN,TRANSACTION_ID,STATUS,ASSIGN_DATE,CUSTOMER_ID,IS_SEC_SALE, UPDATED_ON, FLUSHOUT_FLAG,FLUSHOUT_REMARKS,FLUSHOUT_ID,IS_SCM_FLUSH) select "+
						" PRODUCT_ID,SERIAL_NO,MARK_DAMAGED,DISTRIBUTOR_ID,DISTRIBUTOR_PURCHASE_DATE,FSE_ID,FSE_PURCHASE_DATE,RETAILER_ID,RETAILER_PURCHASE_DATE,DAMAGE_REMARKS,DAMAGED_BY,DAMAGE_COST, "+
						" INV_NO,REMARKS,MSISDN,TRANSACTION_ID,STATUS,CURRENT_TIMESTAMP,CUSTOMER_ID,IS_SEC_SALE, current timestamp,-2,'"+flushUploadDto.getStrRemarks()+"',"+Integer.parseInt(loginUserId)+",'"+is_scm_flush+"' from DP_STOCK_INVENTORY  where SERIAL_NO=? with ur ";

						
						pstmtDP = connection.prepareStatement(MOVETO_DP_STOCK_INVENTORY_ASSIGNED1);
						/*pstmtDP.setString(1, flushUploadDto.getStrRemarks());//remarks
						pstmtDP.setInt(2, (Integer.parseInt(loginUserId)));//dist id
						pstmtDP.setString(3, is_scm_flush);//scm flag
						*/
						pstmtDP.setString(1, flushUploadDto.getStrSerialNo());
						
						pstmtDP.executeUpdate();
						DBConnectionManager.releaseResources(pstmtDP, null);
	
						pstmtDP = connection.prepareStatement(DELETE_DP_STOCK_INVENTORY);
						pstmtDP.setString(1, flushUploadDto.getStrSerialNo());
						pstmtDP.executeUpdate();
						//NEETIKA PUT CODE HERE
						DBConnectionManager.releaseResources(pstmtDP, null);
					}
					DBConnectionManager.releaseResources(pstmtDPErr,null);
					flushUploadDto.setConfStatus(1);
					stbList.add(flushUploadDto);
					continue;
				}
				else if(flushUploadDto.getStrTeableName().equalsIgnoreCase("DP_ERROR_REV_DETAILS"))
				{
					// FOR UAT & SIT environment
					java.sql.Date invDate = Utility.getSqlDateFromString(flushUploadDto.getStrInventoryChangeDt(), "dd-MMM-yyyy");
					//FOR Development as DB2 is 9.5 not compitable with MMM date format
				//	java.sql.Date invDate = Utility.getSqlDateFromString(flushUploadDto.getStrInventoryChangeDt(), "dd-MM-yyyy");
				    //logger.info(flushUploadDto.getStrSerialNo()+" :: "+ invDate);
					pstmtDPErr = connection.prepareStatement(MOVE_DP_ERROR_REV_DETAIL2);
					pstmtDPErr.setString(1, flushUploadDto.getStrSerialNo());
					pstmtDPErr.setDate(2, invDate);
					pstmtDPErr.executeUpdate();
					DBConnectionManager.releaseResources(pstmtDPErr, null);
		
					// Moving records from DP_REV_INVENTORY_CHANGE when Error STBs are flushed.
					
					String  MOVETO_DP_REV_INVENTORY_CHANGE_HIST_ERROR_REV ="Insert into DP_REV_INVENTORY_CHANGE_HIST(REQ_ID,DEFECTIVE_SR_NO,DEFECTIVE_PRODUCT_ID,DEFECTIVE_DIST_ID,NEW_SR_NO,NEW_PRODUCT_ID,NEW_DIST_D "+
					",INV_CHANGE_DATE,COLLECTION_ID,STATUS,CREATED_ON,UPDATED_ON,ARCHIVED_DATE,IS_SCM,ORIGINAL_DIST_ID,FLUSHOUT_FLAG,ERROR_STATUS,FLUSHOUT_REMARKS,FLUSHOUT_ID,IS_SCM_FLUSH) select REQ_ID,DEFECTIVE_SR_NO,DEFECTIVE_PRODUCT_ID,DEFECTIVE_DIST_ID,NEW_SR_NO,NEW_PRODUCT_ID,NEW_DIST_D"+
					",INV_CHANGE_DATE,COLLECTION_ID,STATUS,CREATED_ON,UPDATED_ON,CURRENT_TIMESTAMP,IS_SCM,ORIGINAL_DIST_ID,-2 ,ERROR_STATUS,'"+flushUploadDto.getStrRemarks()+"',"+Integer.parseInt(loginUserId)+",'"+is_scm_flush+"' from DP_REV_INVENTORY_CHANGE where DEFECTIVE_SR_NO=? and  ERROR_STATUS = 'ERR'";

					
					
					//aman--add remarks and flushout id in query
					pstmtDP = connection.prepareStatement(MOVETO_DP_REV_INVENTORY_CHANGE_HIST_ERROR_REV);
					/*pstmtDP.setString(1, flushUploadDto.getStrRemarks());//remarks
					pstmtDP.setString(2, loginUserId);//dist id
					pstmtDP.setString(3, is_scm_flush);//scm flag
					*/pstmtDP.setString(1, flushUploadDto.getStrSerialNo());
					pstmtDP.executeUpdate();
		
					DBConnectionManager.releaseResources(pstmtDP, null);
		
					pstmtDP = connection.prepareStatement(DELETE_DP_REV_INVENTORY_CHANGE_ERROR_REV);
					pstmtDP.setString(1, flushUploadDto.getStrSerialNo());
					pstmtDP.executeUpdate();
		
					pstmtDPErr = connection.prepareStatement(DELETE_DP_ERROR_REV_DETAIL3);
					pstmtDPErr.setString(1, flushUploadDto.getStrSerialNo());
					pstmtDPErr.setDate(2, invDate);
					pstmtDPErr.executeUpdate();
					
					//NEETIKA PUT CODE HERE
					
					
					DBConnectionManager.releaseResources(pstmtDPErr, null);

				}
				else if(flushUploadDto.getStrTeableName().equalsIgnoreCase("DP_REV_CHURN_INVENTORY"))
				{
					
					
					String MOVE_DP_REV_CHURN_INVENTORY1="INSERT INTO DPDTH.DP_REV_CHURN_INVENTORY_HIST(REQ_ID, SERIAL_NUMBER, PRODUCT_ID, VC_ID, CUSTOMER_ID, SI_ID, AGEING, REMARK, STATUS, CIRCLE_ID, CREATED_ON, CREATED_BY, ACTION_DATE, HIST_DATE, PURCHASE_DT, SERVICE_START_DT, FLUSHOUT_FLAG, IS_SCM, ERROR_STATUS,FLUSHOUT_REMARKS,FLUSHOUT_ID,IS_SCM_FLUSH) "+
					" SELECT REQ_ID, SERIAL_NUMBER, PRODUCT_ID, VC_ID, CUSTOMER_ID, SI_ID, AGEING, REMARK, STATUS, CIRCLE_ID, CREATED_ON, CREATED_BY, ACTION_DATE,  current timestamp,PURCHASE_DT, SERVICE_START_DT,-2 , IS_SCM, ERROR_STATUS,'"+flushUploadDto.getStrRemarks()+"',"+Integer.parseInt(loginUserId)+",'"+is_scm_flush+"' "+
					" FROM DPDTH.DP_REV_CHURN_INVENTORY where serial_number=?  ";

					
					ResultSet rset=null;
					String status="";
					pstmtDP = connection.prepareStatement("select status from DP_REV_CHURN_INVENTORY where serial_number =?");
		
					pstmtDP.setString(1, flushUploadDto.getStrSerialNo());
					//pstmtDP.setDate(2, new java.sql.Date(invDate.getTime()));
		
					rset = pstmtDP.executeQuery();
		
					if(rset.next())
					{
						status=rset.getString("status");
					}
		
					if(status.trim().equalsIgnoreCase("REC")||status.trim().equalsIgnoreCase("COL")||status.trim().equalsIgnoreCase("ERR"))
					{
						//aman--add remarks and flushout id in query
						pstmtDP = connection.prepareStatement(MOVE_DP_REV_CHURN_INVENTORY1);
						/*pstmtDP.setString(1, flushUploadDto.getStrRemarks());//remarks
						pstmtDP.setString(2, loginUserId);//dist id
						pstmtDP.setString(3, is_scm_flush);//scm flag
						*/pstmtDP.setString(1, flushUploadDto.getStrSerialNo());
						pstmtDP.executeUpdate();
						DBConnectionManager.releaseResources(pstmtDP, null);
		
						pstmtDP = connection.prepareStatement("delete from DP_REV_CHURN_INVENTORY where SERIAL_NUMBER=?");
						pstmtDP.setString(1, flushUploadDto.getStrSerialNo());
						pstmtDP.executeUpdate();
						DBConnectionManager.releaseResources(pstmtDP, null);
		
						flushUploadDto.setConfStatus(7);
						stbList.add(flushUploadDto);
						//NEETIKA ADD CODE HERE
						DBConnectionManager.releaseResources(pstmtDPErr,null);
		
						continue;
					}
				}
				else
				{
					String MOVETO_DP_REV_INVENTORY_CHANGE_HISTORY3 ="Insert into DP_REV_INVENTORY_CHANGE_HIST(REQ_ID,DEFECTIVE_SR_NO,DEFECTIVE_PRODUCT_ID,DEFECTIVE_DIST_ID,NEW_SR_NO,NEW_PRODUCT_ID,NEW_DIST_D "+
					",INV_CHANGE_DATE,COLLECTION_ID,STATUS,CREATED_ON,UPDATED_ON,ARCHIVED_DATE,IS_SCM,ORIGINAL_DIST_ID,FLUSHOUT_FLAG,ERROR_STATUS,FLUSHOUT_REMARKS,FLUSHOUT_ID,IS_SCM_FLUSH) select REQ_ID,DEFECTIVE_SR_NO,DEFECTIVE_PRODUCT_ID,DEFECTIVE_DIST_ID,NEW_SR_NO,NEW_PRODUCT_ID,NEW_DIST_D"+
					",INV_CHANGE_DATE,COLLECTION_ID,STATUS,CREATED_ON,UPDATED_ON,CURRENT_TIMESTAMP,IS_SCM,ORIGINAL_DIST_ID,-2 ,ERROR_STATUS,'"+flushUploadDto.getStrRemarks()+"',"+Integer.parseInt(loginUserId)+",'"+is_scm_flush+"' from DP_REV_INVENTORY_CHANGE where DEFECTIVE_SR_NO=? and DATE(INV_CHANGE_DATE)=?";

					
					
						ResultSet rset=null;
						String status="";
		
						if(flushUploadDto.getStrInventoryChangeDt()!=null && (!(flushUploadDto.getStrInventoryChangeDt().equals(""))))
						{
							
							
							
							pstmtDP = connection.prepareStatement("select status from DP_REV_INVENTORY_CHANGE where DEFECTIVE_SR_NO=?  and DATE(INV_CHANGE_DATE) =?");
							logger.error("----"+flushUploadDto.getStrInventoryChangeDt());
							// FOR UAT & SIT environment
							java.sql.Date invDate = Utility.getSqlDateFromString(flushUploadDto.getStrInventoryChangeDt(), "dd-MMM-yyyy");
							// FOR Development as DB2 is 9.5 not compitable with MMM date format
						//	java.sql.Date invDate = Utility.getSqlDateFromString(flushUploadDto.getStrInventoryChangeDt(), "dd-MM-yyyy");
							//logger.error("invDate::::::::"+invDate);
		
							pstmtDP.setString(1, flushUploadDto.getStrSerialNo());
							pstmtDP.setDate(2, invDate);
							rset = pstmtDP.executeQuery();
		
							if(rset.next())
							{
								status=rset.getString("status");
							}
							if(status.trim().equalsIgnoreCase("REC"))
							{
								//aman--add remarks and flushout id in query
								pstmtDP = connection.prepareStatement(MOVETO_DP_REV_INVENTORY_CHANGE_HISTORY3);
								/*pstmtDP.setString(1, flushUploadDto.getStrRemarks());//remarks
								pstmtDP.setString(2, loginUserId);//dist id
								pstmtDP.setString(3, is_scm_flush);//scm flag
								*/pstmtDP.setString(1, flushUploadDto.getStrSerialNo());
								pstmtDP.setDate(2, invDate);
								pstmtDP.executeUpdate();
								//NEETIKA PUT CODE HERE
								DBConnectionManager.releaseResources(pstmtDP, null);
		
								pstmtDP = connection.prepareStatement(DELETE_DP_REV_INVENTORY_CHANGE2);
								pstmtDP.setString(1, flushUploadDto.getStrSerialNo());
								pstmtDP.setDate(2, invDate);
								pstmtDP.executeUpdate();
								
								flushUploadDto.setConfStatus(2);
								stbList.add(flushUploadDto);
							
								DBConnectionManager.releaseResources(pstmtDPErr,null);
								continue;
							}
						}
						
						
						
						pstmtDP = connection.prepareStatement("select status from DP_REV_STOCK_INVENTORY where SERIAL_NO_COLLECT=? ");
						pstmtDP.setString(1, flushUploadDto.getStrSerialNo());
						rset = pstmtDP.executeQuery();
		
						if(rset.next())
						{
							status=rset.getString("status");
						}
						if(status.trim().equalsIgnoreCase("COL")||status.trim().equalsIgnoreCase("ERR"))
						{
							
							
							String MOVE_TO_REV_STOCK_INV_HIST1 = "INSERT INTO DP_REV_STOCK_INVENTORY_HIST" +
							" (COLLECTION_ID, DEFECT_ID, PRODUCT_ID, SERIAL_NO_COLLECT, SERIAL_NO_REPLACE, COLLECTION_DATE, REMARKS, CREATED_ON, CREATED_BY, CIRCLE_ID, STATUS, UPDATED_ON, UPDATED_BY, SCM_STATUS, ARCHIVED_DATE, REPAIR_PRODUCT_ID, REPAIR_REMARK,CUSTOMER_ID,FLUSHOUT_FLAG,FLUSHOUT_REMARKS,FLUSHOUT_ID,IS_SCM_FLUSH )" +
							" (SELECT COLLECTION_ID, DEFECT_ID, PRODUCT_ID, SERIAL_NO_COLLECT, SERIAL_NO_REPLACE, COLLECTION_DATE, REMARKS, CREATED_ON, CREATED_BY, CIRCLE_ID, STATUS, UPDATED_ON, UPDATED_BY, SCM_STATUS, CURRENT_TIMESTAMP, REPAIR_PRODUCT_ID, REPAIR_REMARK ,CUSTOMER_ID,-2,'"+flushUploadDto.getStrRemarks()+"',"+Integer.parseInt(loginUserId)+",'"+is_scm_flush+"' FROM DP_REV_STOCK_INVENTORY where SERIAL_NO_COLLECT=? )";

							
							
							
							//aman--add remarks and flushout id in query
							pstmtDP = connection.prepareStatement(MOVE_TO_REV_STOCK_INV_HIST1);
							/*pstmtDP.setString(1, flushUploadDto.getStrRemarks());//remarks
							pstmtDP.setString(2, loginUserId);//dist id
							pstmtDP.setString(3, is_scm_flush);//scm flag
							*/pstmtDP.setString(1, flushUploadDto.getStrSerialNo());
							pstmtDP.executeUpdate();

							DBConnectionManager.releaseResources(pstmtDP, null);
		
							
							String  MOVETO_DP_REV_INVENTORY_CHANGE_HISTORY9 ="Insert into DP_REV_INVENTORY_CHANGE_HIST(REQ_ID,DEFECTIVE_SR_NO,DEFECTIVE_PRODUCT_ID,DEFECTIVE_DIST_ID,NEW_SR_NO,NEW_PRODUCT_ID,NEW_DIST_D "+

							",INV_CHANGE_DATE,COLLECTION_ID,STATUS,CREATED_ON,UPDATED_ON,ARCHIVED_DATE,IS_SCM,ORIGINAL_DIST_ID,FLUSHOUT_FLAG,ERROR_STATUS,FLUSHOUT_REMARKS,FLUSHOUT_ID,IS_SCM_FLUSH) select REQ_ID,DEFECTIVE_SR_NO,DEFECTIVE_PRODUCT_ID,DEFECTIVE_DIST_ID,NEW_SR_NO,NEW_PRODUCT_ID,NEW_DIST_D"+
							",INV_CHANGE_DATE,COLLECTION_ID,STATUS,CREATED_ON,UPDATED_ON,CURRENT_TIMESTAMP,IS_SCM,ORIGINAL_DIST_ID,-2,ERROR_STATUS,'"+flushUploadDto.getStrRemarks()+"',"+Integer.parseInt(loginUserId)+",'"+is_scm_flush+"' from DP_REV_INVENTORY_CHANGE where DEFECTIVE_SR_NO=? and COLLECTION_ID not in ('2','4')";

							
							
							pstmtDP = connection.prepareStatement(MOVETO_DP_REV_INVENTORY_CHANGE_HISTORY9);
							/*pstmtDP.setString(1, flushUploadDto.getStrRemarks());//remarks
							pstmtDP.setString(2, loginUserId);//dist id
							pstmtDP.setString(3, is_scm_flush);//scm flag
							*/pstmtDP.setString(1, flushUploadDto.getStrSerialNo());//inv
							pstmtDP.executeUpdate();
		
							DBConnectionManager.releaseResources(pstmtDP, null);
		
							pstmtDP = connection.prepareStatement(DELETE_REV_STOCK_INV);
							pstmtDP.setString(1, flushUploadDto.getStrSerialNo());
							pstmtDP.executeUpdate();
		
							DBConnectionManager.releaseResources(pstmtDP, null);
		
							pstmtDP = connection.prepareStatement(DELETE_DP_REV_INVENTORY_CHANGE);
							pstmtDP.setString(1, flushUploadDto.getStrSerialNo());
							pstmtDP.executeUpdate();
							//NEETIKA PUT CODE HERE
							DBConnectionManager.releaseResources(pstmtDP, null);
		
							flushUploadDto.setConfStatus(3);
							stbList.add(flushUploadDto);
							continue;
						}
					}
				//logger.info("In the try block of flushout.."+flushUploadDto.getStrSerialNo());
				flushoutSno.put(flushUploadDto.getStrSerialNo(),"Y");
				}
				else
				{
					logger.info("In the try block of flushout flushout flag already Y.."+flushUploadDto.getStrSerialNo());
					flushoutSno.put(flushUploadDto.getStrSerialNo(),flushoutFlag);
				}
				
			}
				//Neetika catch code
				catch(Exception e)
				{		
					e.printStackTrace();
					System.out.println("In the  catch block of flushout.."+flushUploadDto.getStrSerialNo());
					flushoutSno.put(flushUploadDto.getStrSerialNo(),"N");
					statusDP="Flush could not be completed";
					logger.error(e);
					logger.error(e.getMessage());
					try
					{
						String err = ResourceReader.getCoreResourceBundleValue(com.ibm.dp.common.Constants.STB_Bulk_Flush_Out_Critical);
						logger.info(err + "::" +com.ibm.utilities.Utility.getCurrentDate());
					}
					catch(Exception ex)
					{
						ex.printStackTrace();
					}
					
				}
				}

				Map<String, Integer> uploadCount = new HashMap<String, Integer>();
				for (DuplicateSTBDTO duplicateSTBDTO : flushList) 
				{
					if(uploadCount.containsKey(duplicateSTBDTO.getStrSerialNo()))
					{
						uploadCount.put(duplicateSTBDTO.getStrSerialNo(), uploadCount.get(duplicateSTBDTO.getStrSerialNo()) + 1);
					}
					else
					{
						uploadCount.put(duplicateSTBDTO.getStrSerialNo(),1);
					}
				}

				Map<String, Integer> queryCount = new HashMap<String, Integer>();
		
				for (DuplicateSTBDTO duplicateSTBDTO : queryList) 
				{
					if(queryCount.containsKey(duplicateSTBDTO.getStrSerialNo()))
					{
						queryCount.put(duplicateSTBDTO.getStrSerialNo(), queryCount.get(duplicateSTBDTO.getStrSerialNo()) + 1);
					}
					else
					{
						queryCount.put(duplicateSTBDTO.getStrSerialNo(),1);
					}
				}

				for (Map.Entry<String, Integer> entry1 : uploadCount.entrySet())
				{
					for (Map.Entry<String, Integer> entry2 : queryCount.entrySet())
					{
						String str1=entry1.getKey();
						String str2=entry2.getKey();
	
						if(str2.equalsIgnoreCase(str1))
						{
							releaseList.add(entry2.getKey());
						}
					}
				}
			
				if(statusDP.equalsIgnoreCase("Flush Out completed successfully"))
				{
					System.out.println("oh hello.."+serialNumber);
					serialNumber=serialNumber.substring(0,serialNumber.length()-1);
				
					String[] STBNos = serialNumber.split(",");
					int totalFlushCount = 0;
					for(int ii=0; ii< STBNos.length ; ii++)
					{
						totalFlushCount++;
					}
					statusDP= totalFlushCount+" STBs flushed successfully.";
					
					// Message if STB in active state in CPE
					if(!"".equals(activeStockSTBs))
					{
						activeStockSTBs = activeStockSTBs.replaceFirst(",","");
						logger.info("STBs("+ activeStockSTBs +") Cann't be Flushed as it is Activated in CPE.");
						statusDP = statusDP+" STBs("+ activeStockSTBs +") Cann't be Flushed as it is Activated in CPE.";
					}
					
					serialNumber = serialNumber + ",'"+0+"'";
					
					releaseSTB(releaseList);
				}
				return flushoutSno;
	 		}
	 		catch(Exception e)
			{		
				e.printStackTrace();
				System.out.println("In the last catch block of flushout..");
				statusDP="Flush could not be completed";
				logger.error(e);
				logger.error(e.getMessage());
				try
				{
					String err = ResourceReader.getCoreResourceBundleValue(com.ibm.dp.common.Constants.STB_Bulk_Flush_Out_Critical);
					logger.info(err + "::" +com.ibm.utilities.Utility.getCurrentDate());
				}
				catch(Exception ex)
				{
					ex.printStackTrace();
				}
				logger.info("throw===================================================..");
				throw new DAOException(e.getMessage());
			}
			finally
			{	
				DBConnectionManager.releaseResources(scmFlag ,null);
				DBConnectionManager.releaseResources(pstmtClosing ,null);
				DBConnectionManager.releaseResources(pstmtClosingchurn ,null);
				DBConnectionManager.releaseResources(pstmtClosingDef ,null);
				DBConnectionManager.releaseResources(pstmtClosingUp ,rsClosing);
				DBConnectionManager.releaseResources(conCPE);
				
			}
		}
	
	
	public int getStatusListclose(String  stbSLNos)throws DAOException
	{
		PreparedStatement pstmt = null;
		ResultSet rset = null;
		int count=0;
		try
		{
			//logger.info("stbSLNos ====================================================================================="+stbSLNos);
			pstmt = connection.prepareStatement(queryMap.get(SQL_STB_FRESH_STOCK_LIST_KEY).replace("?", stbSLNos));

			rset = pstmt.executeQuery();
			while(rset.next())
			{
				count=count+1;
				//logger.info("9999999999999999999999999999="+count);
			}
		}
		catch(SQLException sqlEx)
		{
			logger.error("SQL Exception occured while fetching data for fresh stock Error PO details  ::  "+sqlEx.getMessage());
			throw new DAOException(sqlEx.getMessage());
		}
		catch(Exception e)
		{
			logger.error("Exception occured while fetching data for fresh stock Error PO details  ::  "+e.getMessage());
			throw new DAOException(e.getMessage());
		}
		return count;
	}
	
	}