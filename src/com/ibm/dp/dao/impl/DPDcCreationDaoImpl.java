package com.ibm.dp.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.ListIterator;

import org.apache.log4j.Logger;

import com.ibm.dp.common.Constants;
import com.ibm.dp.common.DBQueries;
import com.ibm.dp.cpedp.DPCPEDBConnection;
import com.ibm.dp.dao.DPDcCreationDao;
import com.ibm.dp.dto.DpDcCreationDto;
import com.ibm.dp.dto.DpDcReverseStockInventory;
import com.ibm.dp.dto.SMSDto;
import com.ibm.dp.sms.SMSUtility;
import com.ibm.utilities.DcCreation;
import com.ibm.utilities.Utility;
import com.ibm.virtualization.recharge.common.ResourceReader;
import com.ibm.virtualization.recharge.dao.DAOFactory;
import com.ibm.virtualization.recharge.dao.rdbms.BaseDaoRdbms;
import com.ibm.virtualization.recharge.db.DBConnectionManager;
import com.ibm.virtualization.recharge.exception.DAOException;
import com.ibm.virtualization.recharge.exception.VirtualizationServiceException;

public class DPDcCreationDaoImpl extends BaseDaoRdbms implements DPDcCreationDao {
	public static Logger logger = Logger.getLogger(DPDcCreationDaoImpl.class.getName());
	
	public DPDcCreationDaoImpl(Connection connection) 
	{
		super(connection);
	}
	
	public static final String SQL_COLLECTION_MST 	= DBQueries.GET_COLLECTION_LIST;
	public static final String SQL_STOCK_COLLECTION_LIST 	= DBQueries.GET_REVERSE_STOCK_INVENTORY_LIST;
	public static final String SQL_STOCK_COLLECTION_ALL_LIST 	= DBQueries.GET_REVERSE_STOCK_INVENTORY_ALL_LIST;
	public static final String SQL_INSERT_STOCK_COLLECTION_DETAIL	= DBQueries.INSERT_DC_CREATION_DETAIL;
	public static final String SQL_INSERT_STOCK_COLLECTION_HDR	= DBQueries.INSERT_DC_CREATION_HDR;
	public static final String SQL_UPDATE_STOCK_COLLECTION_LIST	= DBQueries.UPDATE_REVERSE_STOCK_INVENTORY_LIST;
	public static final String SQL_SELECT_STOCK_COLLECTION_LIST	= DBQueries.SELECT_REVERSE_STOCK_INVENTORY_LIST;
	public static final String SQL_CHECK_ERROR_STATUS	= DBQueries.CHECK_ERROR_STATUS;
	public static final String SQL_DEFEC_STOCK_DETAIL="select CUSTOMER_ID,REQ_ID,INV_CHANGE_DT from DP_REV_STOCK_INVENTORY where SERIAL_NO_COLLECT=? with ur";
	public static final String SQL_UPDATE_STOCK_DETAIL="UPDATE DP_STOCK_INVENTORY SET FSE_ID=NULL, FSE_PURCHASE_DATE=NULL , RETAILER_ID=NULL , RETAILER_PURCHASE_DATE=NULL, STATUS='5' ,IS_SEC_SALE='N' "
		+"WHERE SERIAL_NO=? AND PRODUCT_ID=? AND DISTRIBUTOR_ID=? with ur";
	
	
	//System.out.println("sql_cpe::"+sql_cpe);
	
	public List<DpDcCreationDto> getCollectionTypeMaster() throws DAOException 
	{
		List<DpDcCreationDto> dcCollectionListDTO	= new ArrayList<DpDcCreationDto>();
		PreparedStatement pstmt = null;
		ResultSet rset			= null;
		DpDcCreationDto  dcCollectionDto = null;
				
		try
		{
			pstmt = connection.prepareStatement(SQL_COLLECTION_MST);
			rset = pstmt.executeQuery();
			dcCollectionListDTO = new ArrayList<DpDcCreationDto>();
			
			while(rset.next())
			{
				dcCollectionDto = new DpDcCreationDto();
				dcCollectionDto.setCollectionId(rset.getString("COLLECTION_ID"));
				dcCollectionDto.setCollectionName(rset.getString("COLLECTION_NAME"));
				
								
				dcCollectionListDTO.add(dcCollectionDto);
			}
		}
		catch (Exception e) 
		{
			e.printStackTrace();
			throw new DAOException(e.getMessage());
		}
		finally
		{
			pstmt = null;
			rset = null;
		}
		return dcCollectionListDTO;
	}
	
	
	public List<DpDcReverseStockInventory> getStockCollectionList(String collectionId,Long lngCrBy)  throws DAOException 
	{
		List<DpDcReverseStockInventory> dcStockCollectionListDTO	= new ArrayList<DpDcReverseStockInventory>();
		PreparedStatement pstmt = null;
		ResultSet rset			= null;
		DpDcReverseStockInventory  dcStockCollectionDto = null;
				
		try
		{
			if(collectionId.equals("0")){
				pstmt = connection.prepareStatement(SQL_STOCK_COLLECTION_ALL_LIST);
				pstmt.setString(1, lngCrBy.toString());
			}else{
				pstmt = connection.prepareStatement(SQL_STOCK_COLLECTION_LIST);
				//pstmt.setString(1, collectionId);
				pstmt.setString(2, lngCrBy.toString());
			}
			rset = pstmt.executeQuery();
			dcStockCollectionListDTO = new ArrayList<DpDcReverseStockInventory>();
			int j =0;
			String strCollectedDate = null;
			while(rset.next())
			{
				j++;
				dcStockCollectionDto = new DpDcReverseStockInventory();
				dcStockCollectionDto.setProdId(rset.getString("PRODUCT_ID"));
				
				strCollectedDate = com.ibm.utilities.Utility.converDateToString(rset.getDate("COLLECTION_DATE"),"dd/MM/yyyy");
				//logger.info(" hi  &&&&&&&&&&&&&&&& date === "+strCollectedDate);
				dcStockCollectionDto.setStrCollectionDate(strCollectedDate);
				dcStockCollectionDto.setStrCollectionType(rset.getString("COLLECTION_NAME"));
				dcStockCollectionDto.setStrDefectType(rset.getString("DEFECT_NAME"));
				dcStockCollectionDto.setStrProduct(rset.getString("PRODUCT_NAME"));
				dcStockCollectionDto.setStrRemarks(rset.getString("REMARKS"));
				dcStockCollectionDto.setStrSerialNo(rset.getString("SERIAL_NO_COLLECT"));
				dcStockCollectionDto.setStrCustomerId(rset.getString("CUSTOMER_ID"));
				dcStockCollectionDto.setStrReqId(rset.getString("REQ_ID"));
				dcStockCollectionDto.setStrInvChangeDate(rset.getString("INV_CHANGE_DT"));
				//logger.info("dcStockCollectionDto........"+dcStockCollectionDto.getStrInvChangeDate());
				dcStockCollectionDto.setRowSrNo(String.valueOf(j));
				dcStockCollectionListDTO.add(dcStockCollectionDto);
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
		}
		return dcStockCollectionListDTO;
	}
	
	 public String insertStockCollection(ListIterator<DpDcReverseStockInventory> dcCreationDtoListItr,Long lngDistId,String circleId,String strRemarks,String agencyName,String docketNumber)throws DAOException
	   {
			PreparedStatement ps = null;
			PreparedStatement ps1 = null;
			PreparedStatement ps2 = null;
			PreparedStatement ps3 = null;
			ResultSet rset			= null;
			String strDcNo ="";
			String status="";
			try 
			{
				connection.setAutoCommit(false);
				DpDcReverseStockInventory dcCreationDto = null;
				DcCreation dcCreation = DcCreation.getDcCreationObject();
				strDcNo = dcCreation.generateDcNo(connection, Constants.DC_TYPE_REVERSE);
				//Updated by Shilpa for Critical Changes BFR 14
				
				if(agencyName==null || agencyName.trim().equals("") 
						|| docketNumber==null || docketNumber.trim().equals(""))
				{
					status = com.ibm.dp.common.Constants.PUSH_DC_TO_BOTREE_DRAFT_STATUS;
				}
				else
				{
					status =com.ibm.dp.common.Constants.PUSH_DC_TO_BOTREE_CREATED_STATUS;
				}
				//logger.info("Status of DC == "+status);	
				String strSrNo = "";
				String strProductId = "";
				String strDistId =  lngDistId.toString();
				String strCollectionId ="";
				String strDefectId ="";
				String dtCollectedOn ="";
				String strCircleId ="";
				String strCustomerId="";
				String strReqId="";
				String strInvChange="";
				logger.info("1");
				ArrayList<String> releasedSerialNos =new ArrayList<String>();
				ps = connection.prepareStatement(SQL_INSERT_STOCK_COLLECTION_DETAIL);
				ps1 = connection.prepareStatement(SQL_INSERT_STOCK_COLLECTION_HDR);
				ps3 = connection.prepareStatement(SQL_SELECT_STOCK_COLLECTION_LIST);
				ps2 = connection.prepareStatement(SQL_UPDATE_STOCK_COLLECTION_LIST);
				 //To insert values in DP_DELIVERY_CHALAN_HDR
				 ps1.setString(1, strDcNo);
				 ps1.setString(2, circleId);
				 ps1.setString(3, strDistId);
				 ps1.setString(4, status);
				 ps1.setString(5, strRemarks);
				 ps1.setString(6, agencyName.trim());
				 ps1.setString(7, docketNumber.trim());
				 ps1.setString(8, strDistId);
				 ps1.executeUpdate();
				 logger.info("2");
				while(dcCreationDtoListItr.hasNext()) 
				{	
					 dcCreationDto = dcCreationDtoListItr.next();
					// strDcNo = dcCreationDto.getStrDCNo();
					 strSrNo = dcCreationDto.getStrSerialNo();
					 strProductId = dcCreationDto.getProdId();
					 //strRemarks = dcCreationDto.getStrNewRemarks();
					strCustomerId=dcCreationDto.getStrCustomerId();
					//logger.info("Customer id before == "+strCustomerId);
					
					if(strCustomerId!=null && strCustomerId.trim().equals("-1"))
						strCustomerId =null;
					
					//logger.info("Customer id after == "+strCustomerId);
					
					strReqId=dcCreationDto.getStrReqId();
					strInvChange = dcCreationDto.getStrInvChangeDate();
					  //To Select Stock Inverntory Detail
					 ps3.setString(1, strProductId);
					 ps3.setString(2, strSrNo);
					 ps3.setString(3, strDistId);
					 rset = ps3.executeQuery();
					 while(rset.next())
					 {
						 strCollectionId = rset.getString("COLLECTION_ID");
						 strDefectId = rset.getString("DEFECT_ID");
						 strCircleId = rset.getString("CIRCLE_ID");
						 dtCollectedOn = rset.getString("CREATED_ON");
					 }
					 
					 //logger.info(SQL_INSERT_STOCK_COLLECTION_DETAIL);
					 //To insert values in DP_DELIVERY_CHALAN_DETAIL
					 logger.info("strDcNo:::::::::::::::::"+strDcNo);
					 logger.info("strDistId:::::::::::::::::"+strDistId);
					 logger.info("strSrNo:::::::::::::::::"+strSrNo);
					 logger.info("strCollectionId:::::::::::::::::"+strCollectionId);
					 logger.info("strDefectId:::::::::::::::::"+strDefectId);
					 logger.info("dtCollectedOn:::::::::::::::::"+dtCollectedOn);
					 logger.info("strCircleId:::::::::::::::::"+strCircleId);
					 logger.info("strCustomerId:::::::::::::::::"+strCustomerId);
					 logger.info("strReqId:::::::::::::::::"+strReqId);
					 logger.info("strInvChange:::::::::::::::::"+strInvChange);
					 logger.info("3");
					 ps.setString(1, strDcNo);
					 ps.setString(2, strDistId);
					 ps.setString(3, strSrNo);
					 ps.setString(4, strProductId);
					 ps.setString(5, strCollectionId);
					 ps.setString(6, strDefectId);
					 ps.setString(7, dtCollectedOn);
					 ps.setString(8, strCircleId);
					 ps.setString(9, strCustomerId);
					 ps.setString(10, strReqId);
					 if(strInvChange != null && strInvChange.trim().equals(""))
						 strInvChange = null;
					 ps.setString(11, strInvChange);
					 
					 ps.executeUpdate();
						 
						 //To Update status= 'IDC' of DP_STOCK_INVENTORY_REVERSE 
					 ps2.setString(1, strDistId);
					 ps2.setString(2, strProductId);
					 ps2.setString(3, strSrNo);
					 ps2.setString(4, strDistId);
					 ps2.executeUpdate();
//						Added by SHilpa Khanna on 30-06-12 for Releasing POs
					releasedSerialNos.add(strSrNo);
				}
				
				//logger.info("All releasedSerialNos Size==   == "+releasedSerialNos.size());
				if(releasedSerialNos.size()>0)
				{
				//	logger.info("Calling releaseSTB function in STBFlushOutDaoImpl");
					STBFlushOutDaoImpl relesePoObj = new STBFlushOutDaoImpl(connection);
					relesePoObj.releaseSTB(releasedSerialNos);
				//	logger.info("Exit from releaseSTB function in STBFlushOutDaoImpl");
				}
				logger.info("4");
////////////////alert management//////////////////
/*				if(status ==com.ibm.dp.common.Constants.PUSH_DC_TO_BOTREE_CREATED_STATUS)
				{
					SMSDto sMSDto = null;
					sMSDto = SMSUtility.getUserDetails(strDistId, connection);
					
						sMSDto.setDcNo(strDcNo);
						sMSDto.setCountSrNo(releasedSerialNos.size());
						String SMSmessage=SMSUtility.createMessageContent(com.ibm.virtualization.recharge.common.Constants.CONFIRM_ID_DC_CREATION, sMSDto, connection);
						SMSUtility.saveSMSInDB(connection, new String[] {sMSDto.getParentMobileNumber()}, SMSmessage);
				}*/
//////////////////////////////////////////////////
				
				
				connection.commit();
			}
				catch(SQLException sqle){
				sqle.printStackTrace();
				throw new DAOException("SQLException: " + sqle.getMessage());
			}catch(Exception e){
				e.printStackTrace();
				throw new DAOException("Exception: " + e.getMessage());
			}finally
			{
				DBConnectionManager.releaseResources(ps, null);
				DBConnectionManager.releaseResources(ps1, null);
				DBConnectionManager.releaseResources(ps2, null);
				DBConnectionManager.releaseResources(ps3, rset);
			
			}
			return strDcNo;
				
		}
	 
		public String checkERR(String rowSrNo) throws  DAOException 
		{
			PreparedStatement ps = null;			
			ResultSet rset			= null;
			String strSCMStatus ="";
			String strStatus="";
			String isERR="";
			try 
				{
					connection.setAutoCommit(false);
					DpDcReverseStockInventory dcCreationDto = null;					
					ps = connection.prepareStatement(SQL_CHECK_ERROR_STATUS);
					 ps.setString(1, rowSrNo);
					
					 rset =  ps.executeQuery();
					 
					while(rset.next()) 
					{							
							 strStatus= rset.getString("STATUS");
							 strSCMStatus = rset.getString("ERROR_STATUS");												
					}
					if(strStatus.trim().equalsIgnoreCase("ERR") &&  strSCMStatus.trim().equalsIgnoreCase("ERR")){
						isERR="Y";
					}
					else{
						isERR="N";
					}
				
				}
				catch(SQLException sqle){
					sqle.printStackTrace();
					throw new DAOException("SQLException: " + sqle.getMessage());
				}catch(Exception e){
					e.printStackTrace();
					throw new DAOException("Exception: " + e.getMessage());
				}finally
				{
					DBConnectionManager.releaseResources(ps, rset);
				
				}
			return isERR;
				
			 }


		@Override
		public DpDcReverseStockInventory getStockDetail(String srNoCollect)
				throws DAOException {
			PreparedStatement ps = null;			
			ResultSet rset= null;
			logger.info(srNoCollect);
			DpDcReverseStockInventory dpdcRevDto=new DpDcReverseStockInventory();
			try 
				{
				ps=connection.prepareStatement(SQL_DEFEC_STOCK_DETAIL);
				ps.setString(1, srNoCollect);
				rset=ps.executeQuery();
				while(rset.next()){
					dpdcRevDto.setStrSerialNo(srNoCollect);
					dpdcRevDto.setStrCustomerId( rset.getString("CUSTOMER_ID"));
					dpdcRevDto.setStrReqId(((Long)rset.getLong("REQ_ID")).toString());
					dpdcRevDto.setStrInvChangeDate(rset.getTimestamp("INV_CHANGE_DT").toString());
					
				}
				
				}
				catch(SQLException sqle){
					sqle.printStackTrace();
					throw new DAOException("SQLException: " + sqle.getMessage());
				}catch(Exception e){
					e.printStackTrace();
					throw new DAOException("Exception: " + e.getMessage());
				}finally
				{
					DBConnectionManager.releaseResources(ps, rset);
				
				}
			return dpdcRevDto;
		}
	
		public int updateInactiveSecondaryStock(String prodId,long distId,String serialNum)
		throws Exception {
	
	PreparedStatement ps  = null;	
	PreparedStatement ps1 = null;
	PreparedStatement ps2 = null;
	PreparedStatement ps3 = null;
	PreparedStatement ps4 = null;

	int rset,j;
	logger.info("  serialNum "+serialNum);
	
	Connection conCPE=null;
	Connection con=null;
	con = DBConnectionManager.getDBConnection();
	conCPE=DPCPEDBConnection.getDBConnectionCPE();
	ResultSet rs=null;
	ResultSet rs1=null;
	ResultSet rs2=null;
	
	try 
		{
		
		String SQL_CPE_CHECK="select SERIAL_NO from DP_STOCK_INVENTORY where SERIAL_NO in ('"+serialNum+"') and retailer_id is not null";
		ps1=con.prepareStatement(SQL_CPE_CHECK);
		rs=ps1.executeQuery();
		
		ps=con.prepareStatement(SQL_UPDATE_STOCK_DETAIL);
		logger.info(" serialNum "+ serialNum);
		logger.info(" prodId "+ prodId);
		logger.info(" distId "+ distId);
		ps.setString(1, serialNum);
		ps.setString(2, prodId);
		ps.setLong(3, distId);
		rset=ps.executeUpdate();
		
		
		String SQL_DELETE="delete from DPCPE.CPE_TEMP_INSERT_SERIAL_NUMBER where SERIAL_NUMBER in('"+serialNum+"')  and PRODUCT_ID ="+prodId ;
		String SQL_UPDATE="update DPCPE.CPE_INVENTORY_DETAILS set CPE_INVENTORY_STATUS_KEY = 5 where ASSET_SERIAL_NO in ('"+serialNum+"') ";
		String 	SQL_CPE="INSERT INTO DPCPE.CPE_INSERT_SERIAL_ASSIGN_HIST(SEQ_ID, SERIAL_NUMBER, STATUS, TRANSACTION_ID, DIST_ID, PRODUCT_ID, UNASSIGN_DATE, ASSIGN_DATE, TRANSFER_DATE, FSE_ID, RETAILER_ID, LAST_HIST_DATE, CUSTOMER_ID, IS_SCM, PRODUCT_TYPE)  " 
			+" SELECT SEQ_ID, SERIAL_NUMBER, -1,     TRANSACTION_ID, DIST_ID, PRODUCT_ID, UNASSIGN_DATE, ASSIGN_DATE, sysdate,FSE_ID, RETAILER_ID,sysdate,CUSTOMER_ID,'Y', PRODUCT_TYPE  "
            +" FROM DPCPE.CPE_TEMP_INSERT_SERIAL_NUMBER where SERIAL_NUMBER in('"+serialNum+"') and PRODUCT_ID ="+prodId;
		
		if(rset>0){
	
	
			
		if(rs.next())
		{
		ps2=conCPE.prepareStatement(SQL_CPE);
		rs1=ps2.executeQuery();
		
		ps3=conCPE.prepareStatement(SQL_DELETE);
	    rs2=ps3.executeQuery();
		
	  /*  ps4=conCPE.prepareStatement(SQL_UPDATE);
	    j=ps4.executeUpdate();*/
		
		}
	    con.commit();	
	    conCPE.commit();
		
		}
		
		}
		catch(SQLException sqle){
			sqle.printStackTrace();
			throw new DAOException("SQLException: " + sqle.getMessage());
		}catch(Exception e){
			e.printStackTrace();
			throw new DAOException("Exception: " + e.getMessage());
		}finally
		{
			if (rs != null) {
				/* close resultset */
				rs.close();
				rs = null;
			}
			if (ps != null) {
				/* close statement */
				ps.close();
				ps = null;
			}
			
			if (ps1 != null) {
				/* close statement */
				ps1.close();
				ps1 = null;
			}
		
			if (con != null) {
				/* close statement */
				con.close();
				con = null;
			}
			if (conCPE != null) {
				/* close statement */
				conCPE.close();
				conCPE = null;
			}
		}
	return rset;
}
	
	
	
}
