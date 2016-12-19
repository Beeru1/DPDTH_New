package com.ibm.dp.dao.impl;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.sql.Types;

import org.apache.log4j.Logger;

import com.ibm.dp.common.Constants;
import com.ibm.dp.common.DBQueries;
import com.ibm.dp.dao.TransferPendingSTBDao;
import com.ibm.dp.dto.CircleDto;
import com.ibm.dp.dto.DPReverseCollectionDto;
import com.ibm.dp.dto.TransferPendingSTBDto;
import com.ibm.utilities.Utility;
import com.ibm.virtualization.recharge.dao.rdbms.BaseDaoRdbms;
import com.ibm.virtualization.recharge.db.DBConnectionManager;
import com.ibm.virtualization.recharge.exception.DAOException;

public class TransferPendingSTBDaoImpl  extends BaseDaoRdbms implements TransferPendingSTBDao {
	public static Logger logger = Logger.getLogger(TransferPendingSTBDaoImpl.class.getName());
	private static TransferPendingSTBDao  transferPendingSTBDao  = new TransferPendingSTBDaoImpl();
	private TransferPendingSTBDaoImpl(){}
	public static TransferPendingSTBDao getInstance() {
		return transferPendingSTBDao;
	}
	
	public static final String SQL_COLLECTION_MST 	= DBQueries.SQL_COLLECTION_MST;
	public static final String SQL_CIRCLE_MST 	= DBQueries.SQL_SELECT_CIRCLES;
	//public static final String SQL_COLLECTION_DATA = "SELECT (Select VALUE from DP_CONFIGURATION_DETAILS WHERE CONFIG_ID=9 and ID = SI.COLLECTION_ID ) As COLLECTION_TYPE, DEFECTIVE_SR_NO AS SERIAL_N0, DEFECTIVE_PRODUCT_ID AS PRODUCT, INV_CHANGE_DATE AS INVENTORY_CHANGE_DATE,(CURRENT DATE - INV_CHANGE_DATE) AS AGEING, DEFECT_ID AS DEFECT_TYPE, SI.COLLECTION_DATE FROM DP_REV_INVENTORY_CHANGE IC, DP_REV_STOCK_INVENTORY SI where integer(IC.COLLECTION_ID) = SI.COLLECTION_ID and NEW_DIST_D = ? ";
	public static final String UPDATE_REV_INVENTORY_CHANGE = "update DP_REV_INVENTORY_CHANGE " +
			" set NEW_DIST_D=?, DEFECTIVE_PRODUCT_ID=? " +
			" where REQ_ID=? ";
	
	public static final String GET_NEW_PRODUCT_ID = "select PRODUCT_ID from DP_PRODUCT_MASTER " +
			" where CM_STATUS='A' and CIRCLE_ID=? " +
			" and PRODUCT_CATEGORY=(select PRODUCT_CATEGORY from DP_PRODUCT_MASTER where PRODUCT_ID=?) " +
			" and PRODUCT_TYPE=(select PRODUCT_TYPE from DP_PRODUCT_MASTER where PRODUCT_ID=?)";
	
	public static final String UPDATE_REV_STOCK_INVENTORY ="update DP_REV_STOCK_INVENTORY " +
			" set CREATED_BY=?, PRODUCT_ID=? , CIRCLE_ID =? " +
			" where SERIAL_NO_COLLECT=? and PRODUCT_ID=? and CREATED_BY=?";
	
	public static final String INSERT_REV_PENDING_TRANSFER = "INSERT INTO DP_REV_PENDING_TRANS_DET(FROM_CIRCLE, TO_CIRCLE, FROM_TSM, TO_TSM, FROM_DIST, TO_DIST, SERIAL_NO, PRODUCT_ID_OLD, PRODUCT_ID_NEW, INV_CHANGE_DATE, COLLECTION_ID, DEFECT_ID, COLLECTION_DATE, CREATED_ON, CREATED_BY, STATUS) " +
			" VALUES(?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, current timestamp, ?, 'NEW')";

	//Commented and updated by Shilpa to filter data according to collection Id
	
	/*public static final String SQL_COLLECTION_DATA = "Select COLLECTION_NAME, SERIAL_NO, PRODUCT_NAME, PRODUCT_TYPE, INV_CHANGE_DATE, AGEING," +
			" case WHEN DEFECT_ID is null then '' else DEFECT_NAME end DEFECT_NAME, COLLECTION_DATE, COLLECTION_ID,PRODUCT_ID," +
			" DEFECT_ID, FLAG, REQ_ID from (select rcm.COLLECTION_NAME, ic.DEFECTIVE_SR_NO as SERIAL_NO, pm.PRODUCT_NAME,pm.PRODUCT_TYPE,ic.INV_CHANGE_DATE," +
			" (DAYS(CURRENT_DATE)-DAYS(INV_CHANGE_DATE)) as AGEING, dm.DEFECT_NAME DEFECT_NAME," +
			" DPDTH.DATE_CHAR(si.COLLECTION_DATE) COLLECTION_DATE, rcm.COLLECTION_ID, pm.PRODUCT_ID, dm.DEFECT_ID as DEFECT_ID," +
			" 2 AS FLAG, ic.REQ_ID as REQ_ID from DP_REV_INVENTORY_CHANGE ic inner join DP_REV_STOCK_INVENTORY si" +
			" on ic.DEFECTIVE_SR_NO=si.SERIAL_NO_COLLECT and ic.INV_CHANGE_DATE<=si.COLLECTION_DATE and int(ic.COLLECTION_ID)=si.COLLECTION_ID" +
			" and ic.NEW_DIST_D =si.CREATED_BY and ic.DEFECTIVE_PRODUCT_ID=si.PRODUCT_ID" +
			" inner join DP_REV_COLLECTION_MST rcm on si.COLLECTION_ID=rcm.COLLECTION_ID inner join DP_PRODUCT_MASTER pm" +
			" on ic.DEFECTIVE_PRODUCT_ID=pm.PRODUCT_ID left outer join DP_REV_DEFECT_MST dm on si.DEFECT_ID=dm.DEFECT_ID" +
			" where ic.ERROR_STATUS!='ERR' and ic.STATUS='COL' and si.STATUS in ('COL', 'ERR') and si.CREATED_BY=?" +
			" union" +
			" select rcm.COLLECTION_NAME, ic.DEFECTIVE_SR_NO as SERIAL_NO, pm.PRODUCT_NAME,pm.PRODUCT_TYPE,ic.INV_CHANGE_DATE," +
			" (DAYS(CURRENT_DATE)-DAYS(INV_CHANGE_DATE)) as AGEING, dm.DEFECT_NAME DEFECT_NAME, '' COLLECTION_DATE, rcm.COLLECTION_ID, pm.PRODUCT_ID, dm.DEFECT_ID as DEFECT_ID, 1 AS FLAG, ic.REQ_ID as REQ_ID" +
			" from DP_REV_INVENTORY_CHANGE ic inner join DP_REV_COLLECTION_MST rcm on ic.COLLECTION_ID=char(rcm.COLLECTION_ID) inner join DP_PRODUCT_MASTER pm on ic.DEFECTIVE_PRODUCT_ID=pm.PRODUCT_ID" +
			" left outer join DP_REV_DEFECT_MST dm on ic.DEFECT_ID=dm.DEFECT_ID where ic.ERROR_STATUS!='ERR' and ic.STATUS='REC' and NEW_DIST_D=?" +
			" union" +
			" select rcm.COLLECTION_NAME, si.SERIAL_NO_COLLECT as SERIAL_NO, pm.PRODUCT_NAME,pm.PRODUCT_TYPE, si.COLLECTION_DATE INV_CHANGE_DATE," +
			" (DAYS(CURRENT_DATE)-DAYS(COLLECTION_DATE)) as AGEING, dm.DEFECT_NAME DEFECT_NAME," +
			" DPDTH.DATE_CHAR(si.COLLECTION_DATE) COLLECTION_DATE, rcm.COLLECTION_ID, pm.PRODUCT_ID, dm.DEFECT_ID as DEFECT_ID," +
			" 3 AS FLAG, 0 as REQ_ID" +
			" from DP_REV_STOCK_INVENTORY si inner join DP_REV_COLLECTION_MST rcm on si.COLLECTION_ID=rcm.COLLECTION_ID" +
			"  inner join DP_PRODUCT_MASTER pm on si.PRODUCT_ID=pm.PRODUCT_ID left outer join DP_REV_DEFECT_MST dm on si.DEFECT_ID=dm.DEFECT_ID" +
			" where si.ERROR_STATUS!='ERR' and si.STATUS in ('COL', 'ERR') and si.COLLECTION_ID=2 and si.CREATED_BY=?) Order by COLLECTION_ID ,PRODUCT_TYPE,AGEING";
*/
	public static final String SQL_COLLECTION_DATA = "Select COLLECTION_NAME, DEFECTIVE_SERIAL_NO, DEFECTIVE_PRODUCT_NAME, DEFECTIVE_PRODUCT_TYPE," +
			" CHANGED_SERIAL_NO, CHANGED_PRODUCT_NAME, CHANGED_PRODUCT_TYPE, INV_CHANGE_DATE, AGEING," +
	" case WHEN DEFECT_ID is null then '' else DEFECT_NAME end DEFECT_NAME, COLLECTION_DATE, COLLECTION_ID,PRODUCT_ID," +
	" DEFECT_ID, FLAG, REQ_ID, case WHEN CUSTOMER_ID is null then '' else CUSTOMER_ID end CUSTOMER_ID  from (select rcm.COLLECTION_NAME, ic.DEFECTIVE_SR_NO as DEFECTIVE_SERIAL_NO, pm.PRODUCT_NAME as DEFECTIVE_PRODUCT_NAME,pm.PRODUCT_TYPE as DEFECTIVE_PRODUCT_TYPE," +
	" ic.NEW_SR_NO as CHANGED_SERIAL_NO, pmN.PRODUCT_NAME as CHANGED_PRODUCT_NAME,pmN.PRODUCT_TYPE as CHANGED_PRODUCT_TYPE,ic.INV_CHANGE_DATE," +
	" (DAYS(CURRENT_DATE)-DAYS(INV_CHANGE_DATE)) as AGEING, dm.DEFECT_NAME DEFECT_NAME," +
	" DPDTH.DATE_CHAR(si.COLLECTION_DATE) COLLECTION_DATE, rcm.COLLECTION_ID, pm.PRODUCT_ID, dm.DEFECT_ID as DEFECT_ID," +
	" 2 AS FLAG, ic.REQ_ID as REQ_ID,ic.CUSTOMER_ID as CUSTOMER_ID from DP_REV_INVENTORY_CHANGE ic inner join DP_REV_STOCK_INVENTORY si" +
	" on ic.DEFECTIVE_SR_NO=si.SERIAL_NO_COLLECT and ic.INV_CHANGE_DATE<=si.COLLECTION_DATE and int(ic.COLLECTION_ID)=si.COLLECTION_ID" +
	" and ic.NEW_DIST_D =si.CREATED_BY and ic.DEFECTIVE_PRODUCT_ID=si.PRODUCT_ID" +
	" inner join DP_REV_COLLECTION_MST rcm on si.COLLECTION_ID=rcm.COLLECTION_ID inner join DP_PRODUCT_MASTER pm" +
	" on ic.DEFECTIVE_PRODUCT_ID=pm.PRODUCT_ID inner join DP_PRODUCT_MASTER pmN on ic.NEW_PRODUCT_ID=pmN.PRODUCT_ID left outer join DP_REV_DEFECT_MST dm on si.DEFECT_ID=dm.DEFECT_ID" +
	" where ic.ERROR_STATUS!='ERR' and ic.STATUS='COL' and si.STATUS in ('COL', 'ERR') and ic.COLLECTION_ID =? and si.CREATED_BY=?" +
	" union" +
	" select rcm.COLLECTION_NAME, ic.DEFECTIVE_SR_NO as DEFECTIVE_SERIAL_NO, pm.PRODUCT_NAME as DEFECTIVE_PRODUCT_NAME,pm.PRODUCT_TYPE as DEFECTIVE_PRODUCT_TYPE," +
	" ic.NEW_SR_NO as CHANGED_SERIAL_NO, pmN.PRODUCT_NAME as CHANGED_PRODUCT_NAME,pmN.PRODUCT_TYPE as CHANGED_PRODUCT_TYPE,ic.INV_CHANGE_DATE," +
	" (DAYS(CURRENT_DATE)-DAYS(INV_CHANGE_DATE)) as AGEING, dm.DEFECT_NAME DEFECT_NAME, '' COLLECTION_DATE, rcm.COLLECTION_ID, pm.PRODUCT_ID, dm.DEFECT_ID as DEFECT_ID, 1 AS FLAG, ic.REQ_ID as REQ_ID,ic.CUSTOMER_ID as CUSTOMER_ID" +
	" from DP_REV_INVENTORY_CHANGE ic inner join DP_REV_COLLECTION_MST rcm on ic.COLLECTION_ID=char(rcm.COLLECTION_ID) inner join DP_PRODUCT_MASTER pm on ic.DEFECTIVE_PRODUCT_ID=pm.PRODUCT_ID" +
	" inner join DP_PRODUCT_MASTER pmN on ic.NEW_PRODUCT_ID=pmN.PRODUCT_ID " +
	" left outer join DP_REV_DEFECT_MST dm on ic.DEFECT_ID=dm.DEFECT_ID where ic.ERROR_STATUS!='ERR' and ic.STATUS='REC' and ic.COLLECTION_ID =? and NEW_DIST_D=?" ;

	public static final String SQL_COLLECTION_DATA_ID_2 = " union" +
	" select rcm.COLLECTION_NAME, si.SERIAL_NO_COLLECT as DEFECTIVE_SERIAL_NO, pm.PRODUCT_NAME as DEFECTIVE_PRODUCT_NAME,pm.PRODUCT_TYPE as DEFECTIVE_PRODUCT_TYPE," +
	"  si.SERIAL_NO_REPLACE as CHANGED_SERIAL_NO,pmN.PRODUCT_NAME as CHANGED_PRODUCT_NAME,pmN.PRODUCT_TYPE as CHANGED_PRODUCT_TYPE, si.COLLECTION_DATE as INV_CHANGE_DATE," +
	" (DAYS(CURRENT_DATE)-DAYS(COLLECTION_DATE)) as AGEING, dm.DEFECT_NAME DEFECT_NAME," +
	" DPDTH.DATE_CHAR(si.COLLECTION_DATE) COLLECTION_DATE, rcm.COLLECTION_ID, pm.PRODUCT_ID, dm.DEFECT_ID as DEFECT_ID," +
	" 3 AS FLAG, 0 as REQ_ID,si.CUSTOMER_ID as CUSTOMER_ID" +
	" from DP_REV_STOCK_INVENTORY si inner join DP_REV_COLLECTION_MST rcm on si.COLLECTION_ID=rcm.COLLECTION_ID" +
	"  inner join DP_PRODUCT_MASTER pm on si.PRODUCT_ID=pm.PRODUCT_ID inner join DP_PRODUCT_MASTER pmN on si.PRODUCT_ID_REPLACE=pmN.PRODUCT_ID " +
	" left outer join DP_REV_DEFECT_MST dm on si.DEFECT_ID=dm.DEFECT_ID" +
	" where si.ERROR_STATUS!='ERR' and si.STATUS in ('COL', 'ERR') and si.COLLECTION_ID=2 and si.CREATED_BY=? ";

	public static final String SQL_COLLECTION_DATA_FINAL = "  ) Order by COLLECTION_ID ,DEFECTIVE_PRODUCT_TYPE,AGEING with ur";
	
	public static final String SQL_COLLECTION_DATA_CHURN = "Select COLLECTION_NAME, DEFECTIVE_SERIAL_NO, DEFECTIVE_PRODUCT_NAME, DEFECTIVE_PRODUCT_TYPE," +
	" CHANGED_SERIAL_NO, CHANGED_PRODUCT_NAME, CHANGED_PRODUCT_TYPE, INV_CHANGE_DATE, AGEING," +
" case WHEN DEFECT_ID is null then '' else DEFECT_NAME end DEFECT_NAME, COLLECTION_DATE, COLLECTION_ID,PRODUCT_ID," +
" DEFECT_ID, FLAG, REQ_ID, case WHEN CUSTOMER_ID is null then '' else CUSTOMER_ID end CUSTOMER_ID  from (" +
" select (select COLLECTION_NAME from DP_REV_COLLECTION_MST where COLLECTION_ID=4) as COLLECTION_NAME, ic.SERIAL_NUMBER as DEFECTIVE_SERIAL_NO, pm.PRODUCT_NAME as DEFECTIVE_PRODUCT_NAME , pm.PRODUCT_TYPE as DEFECTIVE_PRODUCT_TYPE," +
"  '' as CHANGED_SERIAL_NO, '' as CHANGED_PRODUCT_NAME,'' as CHANGED_PRODUCT_TYPE, date(ic.CREATED_ON) as INV_CHANGE_DATE, ic.AGEING, '' DEFECT_NAME, date(ic.CREATED_ON) as COLLECTION_DATE, 4 as COLLECTION_ID, pm.PRODUCT_ID, 0 as DEFECT_ID," +
" 4 AS FLAG, ic.REQ_ID as REQ_ID, ic.CUSTOMER_ID " +
" from DP_REV_CHURN_INVENTORY ic inner join DP_PRODUCT_MASTER pm on ic.PRODUCT_ID=pm.PRODUCT_ID where ic.STATUS in ('REC','COL') and ic.CREATED_BY=?" +
" ) Order by COLLECTION_ID ,DEFECTIVE_PRODUCT_TYPE,AGEING with ur";
	
	
	public static final String SQL_COLLECTION_DATA_ALL = "Select COLLECTION_NAME, DEFECTIVE_SERIAL_NO, DEFECTIVE_PRODUCT_NAME, DEFECTIVE_PRODUCT_TYPE,CHANGED_SERIAL_NO, CHANGED_PRODUCT_NAME, CHANGED_PRODUCT_TYPE, INV_CHANGE_DATE, AGEING," +
	" case WHEN DEFECT_ID is null then '' else DEFECT_NAME end DEFECT_NAME, COLLECTION_DATE, COLLECTION_ID,PRODUCT_ID, DEFECT_ID, FLAG, REQ_ID, case WHEN CUSTOMER_ID is null then '' else CUSTOMER_ID end CUSTOMER_ID  from (" +
	" select (select COLLECTION_NAME from DP_REV_COLLECTION_MST where COLLECTION_ID=4) as COLLECTION_NAME, ic.SERIAL_NUMBER as DEFECTIVE_SERIAL_NO, pm.PRODUCT_NAME as DEFECTIVE_PRODUCT_NAME , pm.PRODUCT_TYPE as DEFECTIVE_PRODUCT_TYPE," +
	"  '' as CHANGED_SERIAL_NO, '' as CHANGED_PRODUCT_NAME,'' as CHANGED_PRODUCT_TYPE, date(ic.CREATED_ON) as INV_CHANGE_DATE, ic.AGEING, '' DEFECT_NAME, date(ic.CREATED_ON) as COLLECTION_DATE, 4 as COLLECTION_ID, pm.PRODUCT_ID, 0 as DEFECT_ID," +
	" 4 AS FLAG, ic.REQ_ID as REQ_ID, ic.CUSTOMER_ID from DP_REV_CHURN_INVENTORY ic inner join DP_PRODUCT_MASTER pm on ic.PRODUCT_ID=pm.PRODUCT_ID where ic.STATUS in ('REC','COL') and ic.CREATED_BY=?) " +
" union "+
	"Select COLLECTION_NAME, DEFECTIVE_SERIAL_NO, DEFECTIVE_PRODUCT_NAME, DEFECTIVE_PRODUCT_TYPE," +
	" CHANGED_SERIAL_NO, CHANGED_PRODUCT_NAME, CHANGED_PRODUCT_TYPE, INV_CHANGE_DATE, AGEING," +
	" case WHEN DEFECT_ID is null then '' else DEFECT_NAME end DEFECT_NAME, COLLECTION_DATE, COLLECTION_ID,PRODUCT_ID," +
	" DEFECT_ID, FLAG, REQ_ID, case WHEN CUSTOMER_ID is null then '' else CUSTOMER_ID end CUSTOMER_ID  from (select rcm.COLLECTION_NAME, ic.DEFECTIVE_SR_NO as DEFECTIVE_SERIAL_NO, pm.PRODUCT_NAME as DEFECTIVE_PRODUCT_NAME,pm.PRODUCT_TYPE as DEFECTIVE_PRODUCT_TYPE," +
	" ic.NEW_SR_NO as CHANGED_SERIAL_NO, pmN.PRODUCT_NAME as CHANGED_PRODUCT_NAME,pmN.PRODUCT_TYPE as CHANGED_PRODUCT_TYPE,ic.INV_CHANGE_DATE," +
	" (DAYS(CURRENT_DATE)-DAYS(INV_CHANGE_DATE)) as AGEING, dm.DEFECT_NAME DEFECT_NAME," +
	" date(si.COLLECTION_DATE) COLLECTION_DATE, rcm.COLLECTION_ID, pm.PRODUCT_ID, dm.DEFECT_ID as DEFECT_ID," +
	" 2 AS FLAG, ic.REQ_ID as REQ_ID,ic.CUSTOMER_ID as CUSTOMER_ID from DP_REV_INVENTORY_CHANGE ic inner join DP_REV_STOCK_INVENTORY si" +
	" on ic.DEFECTIVE_SR_NO=si.SERIAL_NO_COLLECT and ic.INV_CHANGE_DATE<=si.COLLECTION_DATE and int(ic.COLLECTION_ID)=si.COLLECTION_ID" +
	" and ic.NEW_DIST_D =si.CREATED_BY and ic.DEFECTIVE_PRODUCT_ID=si.PRODUCT_ID" +
	" inner join DP_REV_COLLECTION_MST rcm on si.COLLECTION_ID=rcm.COLLECTION_ID inner join DP_PRODUCT_MASTER pm" +
	" on ic.DEFECTIVE_PRODUCT_ID=pm.PRODUCT_ID inner join DP_PRODUCT_MASTER pmN on ic.NEW_PRODUCT_ID=pmN.PRODUCT_ID left outer join DP_REV_DEFECT_MST dm on si.DEFECT_ID=dm.DEFECT_ID" +
	" where ic.ERROR_STATUS!='ERR' and ic.STATUS='COL' and si.STATUS in ('COL', 'ERR') and si.CREATED_BY=? )" +
" union" +
	" select rcm.COLLECTION_NAME, ic.DEFECTIVE_SR_NO as DEFECTIVE_SERIAL_NO, pm.PRODUCT_NAME as DEFECTIVE_PRODUCT_NAME,pm.PRODUCT_TYPE as DEFECTIVE_PRODUCT_TYPE," +
	" ic.NEW_SR_NO as CHANGED_SERIAL_NO, pmN.PRODUCT_NAME as CHANGED_PRODUCT_NAME,pmN.PRODUCT_TYPE as CHANGED_PRODUCT_TYPE,ic.INV_CHANGE_DATE," +
	" (DAYS(CURRENT_DATE)-DAYS(INV_CHANGE_DATE)) as AGEING, dm.DEFECT_NAME DEFECT_NAME, cast(null as date) COLLECTION_DATE, rcm.COLLECTION_ID, pm.PRODUCT_ID, dm.DEFECT_ID as DEFECT_ID, 1 AS FLAG, ic.REQ_ID as REQ_ID,ic.CUSTOMER_ID as CUSTOMER_ID" +
	" from DP_REV_INVENTORY_CHANGE ic inner join DP_REV_COLLECTION_MST rcm on ic.COLLECTION_ID=char(rcm.COLLECTION_ID) inner join DP_PRODUCT_MASTER pm on ic.DEFECTIVE_PRODUCT_ID=pm.PRODUCT_ID" +
	" inner join DP_PRODUCT_MASTER pmN on ic.NEW_PRODUCT_ID=pmN.PRODUCT_ID " +
	" left outer join DP_REV_DEFECT_MST dm on ic.DEFECT_ID=dm.DEFECT_ID where ic.ERROR_STATUS!='ERR' and ic.STATUS='REC' and NEW_DIST_D=?"+ 
" union" +
	" select rcm.COLLECTION_NAME, si.SERIAL_NO_COLLECT as DEFECTIVE_SERIAL_NO, pm.PRODUCT_NAME as DEFECTIVE_PRODUCT_NAME,pm.PRODUCT_TYPE as DEFECTIVE_PRODUCT_TYPE," +
	"  si.SERIAL_NO_REPLACE as CHANGED_SERIAL_NO,pmN.PRODUCT_NAME as CHANGED_PRODUCT_NAME,pmN.PRODUCT_TYPE as CHANGED_PRODUCT_TYPE, si.COLLECTION_DATE as INV_CHANGE_DATE," +
	" (DAYS(CURRENT_DATE)-DAYS(COLLECTION_DATE)) as AGEING, dm.DEFECT_NAME DEFECT_NAME," +
	" date(si.COLLECTION_DATE) COLLECTION_DATE, rcm.COLLECTION_ID, pm.PRODUCT_ID, dm.DEFECT_ID as DEFECT_ID," +
	" 3 AS FLAG, 0 as REQ_ID,si.CUSTOMER_ID as CUSTOMER_ID" +
	" from DP_REV_STOCK_INVENTORY si inner join DP_REV_COLLECTION_MST rcm on si.COLLECTION_ID=rcm.COLLECTION_ID" +
	"  inner join DP_PRODUCT_MASTER pm on si.PRODUCT_ID=pm.PRODUCT_ID inner join DP_PRODUCT_MASTER pmN on si.PRODUCT_ID_REPLACE=pmN.PRODUCT_ID " +
	" left outer join DP_REV_DEFECT_MST dm on si.DEFECT_ID=dm.DEFECT_ID where si.ERROR_STATUS!='ERR' and si.STATUS in ('COL', 'ERR') and si.COLLECTION_ID=2 and si.CREATED_BY=? Order by COLLECTION_ID ,DEFECTIVE_PRODUCT_TYPE,AGEING with ur";
	
	//Added by Shilpa to update DP_REV_CHURN_INVENTORY table when Churn stock is transfered.
	public static final String UPDATE_REV_CHURN_INVENTORY ="update DP_REV_CHURN_INVENTORY " +
	" set CREATED_BY=?, PRODUCT_ID=?,CIRCLE_ID =? " +
	" where SERIAL_NUMBER=? and PRODUCT_ID=? and CREATED_BY=?";
//	Added by Shilpa to update DP_REV_CHURN_INVENTORY table when Churn stock is transfered. Ends here
	
	
	public List<CircleDto> getAllCircleList() throws DAOException 
	{
		Connection connection = DBConnectionManager.getDBConnection();
		List<CircleDto> circleListDTO	= new ArrayList<CircleDto>();
		PreparedStatement pstmt = null;
		ResultSet rset			= null;
		CircleDto  circleDto = null;
				
		try
		{
			pstmt = connection.prepareStatement(SQL_CIRCLE_MST);
			rset = pstmt.executeQuery();
			circleListDTO = new ArrayList<CircleDto>();
			
			while(rset.next())
			{
				circleDto = new CircleDto();
				circleDto.setCircleId(rset.getString("CIRCLE_ID"));
				circleDto.setCircleName(rset.getString("CIRCLE_NAME"));
				
									
				circleListDTO.add(circleDto);
			}
		}
		catch (Exception e) 
		{
			e.printStackTrace();
			throw new DAOException(e.getMessage());
		}
		finally
		{
			DBConnectionManager.releaseResources(pstmt, rset);
			DBConnectionManager.releaseResources(connection);
		}
		return circleListDTO;
	}

	public List<TransferPendingSTBDto> getCollectionData(String distID,String collectionId) throws DAOException 
	{
		Connection connection = DBConnectionManager.getDBConnection();
		PreparedStatement pstmt = null;
		ResultSet rset			= null;
		TransferPendingSTBDto  transferDto = null;
		ArrayList<TransferPendingSTBDto> transferDtoList = new ArrayList<TransferPendingSTBDto>();
				
		try
		{
			String query = "";
			if(collectionId.trim().equals("A"))
			{
				query = SQL_COLLECTION_DATA_ALL;
				pstmt = connection.prepareStatement(query);
				logger.info("query::::"+query);
				pstmt.setString(1, distID);
				pstmt.setString(2, distID);
				pstmt.setString(3, distID);
				pstmt.setString(4, distID);
			}
			else if(collectionId.trim().equals("4")){
				query = SQL_COLLECTION_DATA_CHURN;
				logger.info("Generated Query == "+query);
				pstmt = connection.prepareStatement(query);
				pstmt.setString(1, distID);
				
			}else{
				query = SQL_COLLECTION_DATA;
				if(collectionId.trim().equals("2")){
					query += SQL_COLLECTION_DATA_ID_2;
				}
				query += SQL_COLLECTION_DATA_FINAL;
				logger.info("Generated Query == "+query);
				pstmt = connection.prepareStatement(query);
				pstmt.setString(1, collectionId);
				pstmt.setString(2, distID);
				pstmt.setString(3, collectionId);
				pstmt.setString(4, distID);
				if(collectionId.trim().equals("2"))
					pstmt.setString(5, distID);
			}
			
			
			rset = pstmt.executeQuery();
			SimpleDateFormat sdf = new SimpleDateFormat("dd-MMM-yyyy");	

			while(rset.next())
			{
				//System.out.println("Inside wh  =="+rset.getString("COLLECTION_NAME"));
				transferDto = new TransferPendingSTBDto();
				transferDto.setCollectionType(rset.getString("COLLECTION_NAME"));
				transferDto.setSerialNo(rset.getString("DEFECTIVE_SERIAL_NO"));
				transferDto.setProduct(rset.getString("DEFECTIVE_PRODUCT_NAME"));
				transferDto.setSerialNoNew(rset.getString("CHANGED_SERIAL_NO"));
				transferDto.setProductNew(rset.getString("CHANGED_PRODUCT_NAME"));
				System.out.println("INV_CHANGE_DATE date ==="+rset.getDate("INV_CHANGE_DATE"));
				if(rset.getDate("INV_CHANGE_DATE") != null) {
					transferDto.setInvChangeDate(sdf.format(rset.getDate("INV_CHANGE_DATE")));
				} else {
					transferDto.setInvChangeDate("");
				}
				
				
				transferDto.setAgeing(rset.getString("AGEING"));
				transferDto.setDefectType(rset.getString("DEFECT_NAME"));
				//System.out.println("collection date ===");
				if((collectionId.trim().equals("A")) || (collectionId.trim().equals("4")))
				{
				if(rset.getDate("COLLECTION_DATE") != null) {
					
					transferDto.setCollectionDate(sdf.format(rset.getDate("COLLECTION_DATE")));
				} else {
					transferDto.setCollectionDate("");
				}
				}
				else
				{
					transferDto.setCollectionDate(rset.getString("COLLECTION_DATE"));
				}
				//System.out.println(" after collection date ===");
				
				transferDto.setProductId(rset.getString("PRODUCT_ID"));
				transferDto.setCollectionId(rset.getString("COLLECTION_ID"));
				transferDto.setDefectId(rset.getString("DEFECT_ID"));	
				
				transferDto.setReqId(rset.getString("REQ_ID"));
				transferDto.setCustomerId(rset.getString("CUSTOMER_ID"));
				
				transferDto.setFlag(rset.getString("FLAG"));
				
				transferDtoList.add(transferDto);
			}
		}
		catch (Exception e) 
		{
			e.printStackTrace();
			logger.error("Exception occured!!" + e.getMessage());
			throw new DAOException(e.getMessage());
		}
		finally
		{
			DBConnectionManager.releaseResources(pstmt, rset);
			DBConnectionManager.releaseResources(connection);
		}
		return transferDtoList;
	}
	
	public void updateRevInventory(TransferPendingSTBDto transferDto, Connection connection) throws DAOException 
	{
		//Connection connection = DBConnectionManager.getDBConnection();
		PreparedStatement pstmt = null;
		ResultSet rset			= null;		
		PreparedStatement pstmt1 = null;
		ResultSet rset1			= null;		
		int productIdNew=0;
				
		try
		{
			pstmt1 = connection.prepareStatement(GET_NEW_PRODUCT_ID);
			pstmt1.setString(1, transferDto.getToCircleId());
			pstmt1.setString(2, transferDto.getProductId());
			pstmt1.setString(3, transferDto.getProductId());
			rset1 = pstmt1.executeQuery();
			
			while(rset1.next())
			{
				productIdNew = rset1.getInt("PRODUCT_ID");
			}
			pstmt = connection.prepareStatement(UPDATE_REV_INVENTORY_CHANGE);
			pstmt.setString(1, transferDto.getToDistId());		
			pstmt.setInt(2, productIdNew);			
			pstmt.setInt(3, Integer.parseInt(transferDto.getReqId()));
			int result = pstmt.executeUpdate();
			//insertRevPendingTransfer(transferDto,connection);
			//connection.commit();
		}
		catch (Exception e) 
		{
			e.printStackTrace();
			logger.error("Exception occured!!" + e.getMessage());
			throw new DAOException(e.getMessage());
		}
		finally
		{
			DBConnectionManager.releaseResources(pstmt1, rset1);
			DBConnectionManager.releaseResources(pstmt, rset);
			//DBConnectionManager.releaseResources(connection);
		}
	}
	
	public void updateRevStockInventory(TransferPendingSTBDto transferDto, Connection connection) throws DAOException 
	{
		//Connection connection = DBConnectionManager.getDBConnection();
		PreparedStatement pstmt = null;
		ResultSet rset			= null;		
		PreparedStatement pstmt1 = null;
		ResultSet rset1			= null;		
		int productIdNew=0;
		//ArrayList<TransferPendingSTBDto> transferDtoList = new ArrayList<TransferPendingSTBDto>();
				
		try
		{
			pstmt1 = connection.prepareStatement(GET_NEW_PRODUCT_ID);
			pstmt1.setString(1, transferDto.getToCircleId());
			pstmt1.setString(2, transferDto.getProductId());
			pstmt1.setString(3, transferDto.getProductId());
			rset1 = pstmt1.executeQuery();
			
			while(rset1.next())
			{
				productIdNew = rset1.getInt("PRODUCT_ID");
			}
			pstmt = connection.prepareStatement(UPDATE_REV_STOCK_INVENTORY);
			pstmt.setInt(1, Integer.parseInt(transferDto.getToDistId()));
			pstmt.setInt(2, productIdNew);
			pstmt.setInt(3, Integer.parseInt(transferDto.getToCircleId()));
			pstmt.setString(4, transferDto.getSerialNo());
			pstmt.setInt(5, Integer.parseInt(transferDto.getProductId()));
			pstmt.setInt(6, Integer.parseInt(transferDto.getFromDistId()));
			int result = pstmt.executeUpdate();
			
			//insertRevPendingTransfer(transferDto,connection);
			
			//connection.commit();
		}
		catch (Exception e) 
		{
			e.printStackTrace();
			logger.error("Exception occured!!" + e.getMessage());
			throw new DAOException(e.getMessage());
		}
		finally
		{
			DBConnectionManager.releaseResources(pstmt, rset);
			DBConnectionManager.releaseResources(pstmt1, rset1);
			//DBConnectionManager.releaseResources(connection);
		}
		//return transferDtoList;
	}
	
	public void insertRevPendingTransfer(TransferPendingSTBDto transferDto, Connection connection) throws DAOException 
	{
		//Connection connection = DBConnectionManager.getDBConnection();
		PreparedStatement pstmt = null;
		ResultSet rset			= null;		
		PreparedStatement pstmt1 = null;
		ResultSet rset1			= null;		
		int productIdNew=0;
					
		try
		{
			pstmt1 = connection.prepareStatement(GET_NEW_PRODUCT_ID);
			pstmt1.setString(1, transferDto.getToCircleId());
			pstmt1.setString(2, transferDto.getProductId());
			pstmt1.setString(3, transferDto.getProductId());
			rset1 = pstmt1.executeQuery();
			
			while(rset1.next())
			{
				productIdNew = rset1.getInt("PRODUCT_ID");
			}
			int fromCircleId=0;
			int toCircled =0;
			int fromTsmId=0;
			int toTsmId =0;
			int fromDistId=0;
			int toDistId =0;
			int colId =0;
			int createdBy=0;
			int defectId=-1;
			
			
			fromCircleId = Integer.parseInt(transferDto.getFromCircleId());
			toCircled = Integer.parseInt( transferDto.getToCircleId());
			fromTsmId = Integer.parseInt( transferDto.getFromTsmId());
			toTsmId = Integer.parseInt( transferDto.getToTsmId());
			fromDistId = Integer.parseInt( transferDto.getFromDistId());
			toDistId = Integer.parseInt( transferDto.getToDistId());
			colId =  Integer.parseInt( transferDto.getCollectionId());
			createdBy = Integer.parseInt( transferDto.getCreatedBy());
			System.out.println("transferDto.getDefectId():::"+transferDto.getDefectId());
			if(transferDto.getDefectId()!=null && !"null".equalsIgnoreCase(transferDto.getDefectId().trim()) && !"".equals(transferDto.getDefectId()))
			{
				System.out.println("1111111111111111111");
				defectId = Integer.parseInt(transferDto.getDefectId());
				System.out.println("22222222222");
			}
			System.out.println("333333333333333");
			String serialNo = transferDto.getSerialNo();
			String productIdOld = transferDto.getProductId();
			
			
			pstmt = connection.prepareStatement(INSERT_REV_PENDING_TRANSFER);
			pstmt.setInt(1,fromCircleId);
			pstmt.setInt(2,toCircled);
			pstmt.setInt(3, fromTsmId);
			pstmt.setInt(4, toTsmId );
			
			pstmt.setInt(5, fromDistId);
			pstmt.setInt(6, toDistId);
			
			pstmt.setString(7,serialNo );
			pstmt.setString(8, productIdOld);
			
			pstmt.setInt(9, productIdNew);
			String invdate = transferDto.getInvChangeDate();
			Date dtInvDate = null;
			if(invdate != null && !invdate.equals("")) {
				dtInvDate = Utility.getSqlDateFromString(invdate, Constants.DT_STB_TRANSFER);
				pstmt.setDate(10, dtInvDate);
			}
			pstmt.setInt(11,colId );
			if(defectId==-1)
				pstmt.setNull(12, Types.INTEGER);
			else
				pstmt.setInt(12, defectId);
				
			String coldate = transferDto.getCollectionDate();
			logger.info("coldate  ::  "+coldate);
			Date dtColdate = null;
			if(coldate != null && !coldate.equals("")) {
				dtColdate = Utility.getSqlDateFromString(coldate, Constants.DT_STB_TRANSFER);
				logger.info("dtColdate  ::  "+dtColdate);
				pstmt.setDate(13, new java.sql.Date(dtColdate.getTime()));
			}else
				{				
				pstmt.setDate(13, null);
				}
			pstmt.setInt(14,createdBy);
			
			int result = pstmt.executeUpdate();
			//connection.commit();
		}
		catch (Exception e) 
		{
			e.printStackTrace();
			logger.error("Exception occured!!" + e.getMessage());
			throw new DAOException(e.getMessage());
		}
		finally
		{
			DBConnectionManager.releaseResources(pstmt, rset);
			DBConnectionManager.releaseResources(pstmt1, rset1);
			//DBConnectionManager.releaseResources(connection);
		}
	}
	
	
	public List<DPReverseCollectionDto> getCollectionTypeMaster() throws DAOException 
	{
		Connection connection = DBConnectionManager.getDBConnection();
		List<DPReverseCollectionDto> reverseCollectionListDTO	= new ArrayList<DPReverseCollectionDto>();
		PreparedStatement pstmt = null;
		ResultSet rset			= null;
		DPReverseCollectionDto  reverseCollectionDto = null;
				
		try
		{
			pstmt = connection.prepareStatement(SQL_COLLECTION_MST);
			rset = pstmt.executeQuery();
			reverseCollectionListDTO = new ArrayList<DPReverseCollectionDto>();
			
			while(rset.next())
			{
				reverseCollectionDto = new DPReverseCollectionDto();
				reverseCollectionDto.setCollectionId(rset.getString("COLLECTION_ID"));
				reverseCollectionDto.setCollectionName(rset.getString("COLLECTION_NAME"));	
								
				reverseCollectionListDTO.add(reverseCollectionDto);
			}
			
		}
		catch (Exception e) 
		{
			e.printStackTrace();
			throw new DAOException(e.getMessage());
		}
		finally
		{
			DBConnectionManager.releaseResources(pstmt, rset);
			DBConnectionManager.releaseResources(connection);
		}
		return reverseCollectionListDTO;
	}
	
	
	
	public void updateRevChurnInventory(TransferPendingSTBDto transferDto, Connection connection) throws DAOException 
	{
		PreparedStatement pstmt = null;
		ResultSet rset			= null;		
		PreparedStatement pstmt1 = null;
		ResultSet rset1			= null;		
		int productIdNew=0;
		try
		{
			pstmt1 = connection.prepareStatement(GET_NEW_PRODUCT_ID);
			pstmt1.setString(1, transferDto.getToCircleId());
			pstmt1.setString(2, transferDto.getProductId());
			pstmt1.setString(3, transferDto.getProductId());
			rset1 = pstmt1.executeQuery();
			
			while(rset1.next())
			{
				productIdNew = rset1.getInt("PRODUCT_ID");
			}
			pstmt = connection.prepareStatement(UPDATE_REV_CHURN_INVENTORY);
			pstmt.setInt(1, Integer.parseInt(transferDto.getToDistId()));
			pstmt.setInt(2, productIdNew);
			pstmt.setInt(3, Integer.parseInt(transferDto.getToCircleId()));
			pstmt.setString(4, transferDto.getSerialNo());
			pstmt.setInt(5, Integer.parseInt(transferDto.getProductId()));
			pstmt.setInt(6, Integer.parseInt(transferDto.getFromDistId()));
			int result = pstmt.executeUpdate();
			
		}
		catch (Exception e) 
		{
			e.printStackTrace();
			logger.error("Exception occured!!" + e.getMessage());
			throw new DAOException(e.getMessage());
		}
		finally
		{
			DBConnectionManager.releaseResources(pstmt, rset);
			DBConnectionManager.releaseResources(pstmt1, rset1);
			//DBConnectionManager.releaseResources(connection);
		}
		//return transferDtoList;
	}
}
