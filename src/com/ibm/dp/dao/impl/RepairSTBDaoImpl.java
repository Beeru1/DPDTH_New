package com.ibm.dp.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
//import java.util.Date;
import java.util.List;
import java.util.ListIterator;

import org.apache.log4j.Logger;

import com.ibm.dp.common.DBQueries;
import com.ibm.dp.dao.RepairSTBDao;
import com.ibm.dp.dto.ProductMasterDto;
import com.ibm.dp.dto.RepairSTBDto;
import com.ibm.virtualization.recharge.dao.rdbms.BaseDaoRdbms;
import com.ibm.virtualization.recharge.db.DBConnectionManager;
import com.ibm.virtualization.recharge.exception.DAOException;

public class RepairSTBDaoImpl extends BaseDaoRdbms implements  RepairSTBDao{
	
	
public static Logger logger = Logger.getLogger(RepairSTBDaoImpl.class.getName());
	
	public RepairSTBDaoImpl(Connection connection) 
	{
		super(connection);
	}
	
	public static final String SQL_PRODUCT_MST 	= DBQueries.SQL_PRODUCT_lIST;
	public static final String SQL_STOCK_COLLECTION_ALL_LIST 	= DBQueries.GET_SELF_REPAIR_STOCK_INVENTORY_ALL_LIST;
//	Commented by nazim hussain as this functionality is implemented by SCM Scheduler
	//Uncommented by Shilpa khanna
	public static final String SQL_INSERT_REV_STOCK_COLLECTION_HIST	= DBQueries.INSERT_REV_STOCK_COLLECTION_HIST;
	public static final String SQL_DELETE_REV_STOCK_COLLECTION	= DBQueries.DELETE_REV_STOCK_COLLECTION; //DBQueries.UPDATE_REV_STOCK_COLLECTION;
	public static final String SQL_INSERT_STOCK_COLLECTION	= DBQueries.INSERT_STOCK_COLLECTION;
	public static final String SQL_SELECT_STOCK_COLLECTION_LIST	= DBQueries.SELECT_REVERSE_STOCK_INVENTORY_LIST;
	public static final String SQL_CHECK_IS_VALID_FOR_REPAIR	= DBQueries.CHECK_IS_VALID_FOR_REPAIR;
	
	public static final String SQL_INSERT_REV_INV_CHANGE_HIST	= DBQueries.INSERT_INSERT_REV_INV_CHANGE_HIST;
	public static final String SQL_DELETE_REV_INV_CHANGE	= DBQueries.DELETE_REV_INV_CHANGE;
	
	public List<ProductMasterDto> getProductTypeMaster(String circleId) throws DAOException 
	{
		List<ProductMasterDto> productTypeListDTO	= new ArrayList<ProductMasterDto>();
		PreparedStatement pstmt = null;
		ResultSet rset			= null;
		ProductMasterDto  productTypeDto = null;
				
		try
		{
			pstmt = connection.prepareStatement(SQL_PRODUCT_MST);
			pstmt.setString(1, circleId);
			rset = pstmt.executeQuery();
			productTypeListDTO = new ArrayList<ProductMasterDto>();
			
			while(rset.next())
			{
				productTypeDto = new ProductMasterDto();
				productTypeDto.setProductId(rset.getString("PRODUCT_ID"));
				productTypeDto.setProductName(rset.getString("PRODUCT_NAME"));
				
								
				productTypeListDTO.add(productTypeDto);
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
		return productTypeListDTO;
	}
	
	
	public List<RepairSTBDto> getStockCollectionList(Long lngCrBy)  throws DAOException 
	{
		List<RepairSTBDto> dcStockCollectionListDTO	= new ArrayList<RepairSTBDto>();
		PreparedStatement pstmt = null;
		ResultSet rset			= null;
		RepairSTBDto  dcStockCollectionDto = null;
				
		try
		{
			
				pstmt = connection.prepareStatement(SQL_STOCK_COLLECTION_ALL_LIST);
				pstmt.setString(1, lngCrBy.toString());
				rset = pstmt.executeQuery();
				dcStockCollectionListDTO = new ArrayList<RepairSTBDto>();
				int j =0;
				String strCollectedDate = null;
				while(rset.next())
				{
					j++;
					dcStockCollectionDto = new RepairSTBDto();
					dcStockCollectionDto.setProdId(rset.getString("PRODUCT_ID"));
					strCollectedDate = com.ibm.utilities.Utility.converDateToString(rset.getDate("COLLECTION_DATE"),"dd/MM/yyyy");
					logger.info(" hi  &&&&&&&&&&&&&&&& date === "+strCollectedDate);
					dcStockCollectionDto.setStrCollectionDate(strCollectedDate);
					dcStockCollectionDto.setStrCollectionType(rset.getString("COLLECTION_NAME"));
					dcStockCollectionDto.setStrDefectType(rset.getString("DEFECT_NAME"));
					dcStockCollectionDto.setStrProduct(rset.getString("PRODUCT_NAME"));
					dcStockCollectionDto.setStrRemarks(rset.getString("REMARKS"));
					dcStockCollectionDto.setStrSerialNo(rset.getString("SERIAL_NO_COLLECT"));
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
			pstmt = null;
			rset = null;
		}
		return dcStockCollectionListDTO;
	}
	 public String insertStockCollectionRepair(ListIterator<RepairSTBDto> dcCreationDtoListItr,Long lngDistId,String circleId)throws DAOException
	   {
			PreparedStatement ps1 = null;
			PreparedStatement ps2 = null;
			PreparedStatement psInvChangeHist = null;
			PreparedStatement psInvChange = null;
			Statement ps3 = null;
			String strSuccessMessage ="";
			try 
				{
					connection.setAutoCommit(false);
					RepairSTBDto dcCreationDto = null;
					String strSrNo = "";
					String strOldProductId = "";
					String strNewProductId = "";
					String strDistId =  lngDistId.toString();
					String strNewRemarks ="";
					ps1 = connection.prepareStatement(SQL_DELETE_REV_STOCK_COLLECTION);
					ps2 = connection.prepareStatement(SQL_INSERT_STOCK_COLLECTION);
					
					psInvChangeHist = connection.prepareStatement(SQL_INSERT_REV_INV_CHANGE_HIST);
					psInvChange = connection.prepareStatement(SQL_DELETE_REV_INV_CHANGE);
					
					//ps3 = connection.prepareStatement(SQL_INSERT_REV_STOCK_COLLECTION_HIST);
					ps3 = connection.createStatement();
					String query2 = "";		 
					while(dcCreationDtoListItr.hasNext()) 
					{	
						 dcCreationDto = dcCreationDtoListItr.next();
						 
						 strSrNo = dcCreationDto.getStrSerialNo();
						 strOldProductId = dcCreationDto.getStrOldProductId();
						 strNewProductId=dcCreationDto.getProdId();
						 strNewRemarks = dcCreationDto.getStrNewRemarks();
						
						 //To insert into DP_REV_STOCK_INVENTORY_HIST
					 	query2 = SQL_INSERT_REV_STOCK_COLLECTION_HIST;
						query2= query2.replace("?1", strNewProductId);
						query2= query2.replace("?2", strNewRemarks);
						query2= query2.replace("?3", strOldProductId);
						query2= query2.replace("?4", strSrNo);
						query2= query2.replace("?5", strDistId);
						ps3.executeUpdate(query2);
						/* 
						 ps3.setString(1, strNewProductId);
						 ps3.setString(2, strNewRemarks);
						 ps3.setString(3, strOldProductId);
						 ps3.setString(4, strSrNo);
						 ps3.setString(5, strDistId);
						 ps3.executeUpdate();
						 */
						 
						 //To delete row from REV_STOCK_INVENTORY
						 ps1.setString(1, strOldProductId);
						 ps1.setString(2, strSrNo);
						 ps1.setString(3, strDistId);
						 ps1.executeUpdate();
 
						 //To insert into DP_STOCK_INVENTORY
						 ps2.setString(1, strNewProductId);
						 ps2.setString(2, strSrNo);
						 ps2.setString(3, strDistId);
						 ps2.executeUpdate();
						 logger.info("in insertStockCollectionRepair while condition.......");
						 
//						To insert into DP_REV_INVENTORY_CHANGE_HIST @ CR58299
						 psInvChangeHist.setString(1, strSrNo);
						 psInvChangeHist.execute();
						
//						 To delete from DP_REV_INVENTORY_CHANGE  @ CR58299
						 psInvChange.setString(1, strSrNo);
						 psInvChange.execute();
					}
					
					
					connection.commit();
					strSuccessMessage = "SUCCESS";
				}
				catch(SQLException sqle){
					strSuccessMessage = "ERROR";
					sqle.printStackTrace();
					throw new DAOException("SQLException: " + sqle.getMessage());
				}catch(Exception e){
					strSuccessMessage = "ERROR";
					e.printStackTrace();
					throw new DAOException("Exception: " + e.getMessage());
				}finally
				{
					ps1=null;
					ps2=null;
					ps3=null;
					
				}
			return strSuccessMessage;
				
			 }
	 
		public String isValid(Long lngLoginId) throws DAOException 
		{
			String strIsValid ="";
			PreparedStatement pstmt = null;
			ResultSet rset			= null;
					
			try
			{
				pstmt = connection.prepareStatement(SQL_CHECK_IS_VALID_FOR_REPAIR);
				pstmt.setString(1, lngLoginId.toString());
				rset = pstmt.executeQuery();
				
				while(rset.next())
				{
					strIsValid = rset.getString("IS_REPAIR");
				}
				logger.info("in Is Valid Fundction valid Falg ==== "+strIsValid);
			}
			catch (Exception e) 
			{
				e.printStackTrace();
				throw new DAOException(e.getMessage());
			}
			finally
			{
				DBConnectionManager.releaseResources(pstmt, rset);
				pstmt = null;
				rset = null;
			}
			return strIsValid;
		}

}
