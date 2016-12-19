package com.ibm.dp.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

import com.ibm.dp.common.Constants;
import com.ibm.dp.common.DBQueries;
import com.ibm.dp.dao.DPOpenStockDepleteDao;
import com.ibm.dp.dto.DPOpenStockDepletionDTO;
import com.ibm.virtualization.recharge.dao.rdbms.BaseDaoRdbms;
import com.ibm.virtualization.recharge.exception.DAOException;

public class DPOpenStockDepleteDaoImpl extends BaseDaoRdbms implements DPOpenStockDepleteDao
{
	public static Logger logger = Logger.getLogger(DPOpenStockDepleteDaoImpl.class.getName());
	
	public DPOpenStockDepleteDaoImpl(Connection connection) 
	{
		super(connection);
	}
	
	public static final String SQL_OPEN_STOCK_INFO 			= DBQueries.SQL_OPEN_STOCK_INFO;
	public static final String SQL_OPEN_STOCK_ERROR_CODE 	= DBQueries.SQL_OPEN_STOCK_ERROR_CODE;
	public static final String SQL_OPEN_STOCK_UPDATE 		= DBQueries.SQL_OPEN_STOCK_UPDATE;
	public static final String SQL_OPEN_STOCK_TSM			= DBQueries.SQL_OPEN_STOCK_TSM;
	public static final String SQL_OPEN_STOCK_DIST			= DBQueries.SQL_OPEN_STOCK_DIST;
	public static final String SQL_OPEN_STOCK_PROD			= DBQueries.SQL_OPEN_STOCK_PROD;
	public static final String SQL_OPEN_STOCK_BAL			= DBQueries.SQL_OPEN_STOCK_BAL;
	public static final String SQL_OPEN_STOCK_LOG			= DBQueries.SQL_OPEN_STOCK_LOG;
	
	public String depleteOpenStockDao(String distributorCode, String productCode, int productQuantity)
	throws DAOException 
	{
		//Runs for CRM Scheduler
		
		int intClosingStock, intProdID, intDistID;
		
		String strServiceMsg 	= Constants.OPEN_STOCK_WS_MSG_SUCCESS;
		PreparedStatement pstmt = null;
		ResultSet rset 			= null;
		
		try
		{
			connection.setAutoCommit(false);
			
			pstmt = connection.prepareStatement(SQL_OPEN_STOCK_INFO);
			
			pstmt.setString(1, productCode);
			
			pstmt.setString(2, distributorCode);
			
			rset = pstmt.executeQuery();
			
			if(rset.next())
			{
				intDistID = rset.getInt("DIST_ID");
				intProdID = rset.getInt("PRODUCT_ID");
				intClosingStock = rset.getInt("CLOSING_STOCK");
				
				if(intProdID==0)
				{
					pstmt = null;
					rset  = null;
					
					pstmt = connection.prepareStatement(SQL_OPEN_STOCK_ERROR_CODE);
					pstmt.setInt(1, Constants.OPEN_STOCK_ERROR_PROD);
					rset = pstmt.executeQuery();
					rset.next();
					
					strServiceMsg = rset.getString("ERROR_CODE");
					logger.info("INVALID PRODUCT CODE");
				}
//				Commented as told by Kivisha to allow user to enter quantity more than his closing stock
//				else if(productQuantity<1 || intClosingStock<productQuantity)
				else if(productQuantity<1)
				{
					pstmt = null;
					rset  = null;
					
					pstmt = connection.prepareStatement(SQL_OPEN_STOCK_ERROR_CODE);
					pstmt.setInt(1, Constants.OPEN_STOCK_ERROR_QTY);
					rset = pstmt.executeQuery();
					rset.next();
					
					strServiceMsg = rset.getString("ERROR_CODE");
					logger.info("INVALID PROCUCT QUANTITY");
				}
				else
				{
					strServiceMsg = updateOpenStock(intDistID, intProdID, productQuantity);
					logger.info("SERVICE RESULT  ::  "+strServiceMsg);
				}				
			}
			else
			{
				pstmt = null;
				rset  = null;
				
				pstmt = connection.prepareStatement(SQL_OPEN_STOCK_ERROR_CODE);
				pstmt.setInt(1, Constants.OPEN_STOCK_ERROR_DIST);
				rset = pstmt.executeQuery();
				rset.next();
				
				strServiceMsg = rset.getString("ERROR_CODE");
				logger.info("INVALID DISTRIBUTOR CODE");
			}
			
			pstmt = null;
			pstmt = connection.prepareStatement(SQL_OPEN_STOCK_LOG);
			pstmt.setString(1, distributorCode);
			pstmt.setString(2, productCode);
			pstmt.setInt(3, productQuantity);
			pstmt.setString(4, strServiceMsg);
			pstmt.executeUpdate();
			
			connection.commit();
		}
		catch (Exception e) 
		{
			e.printStackTrace();
			
			logger.error("::::::::::::::::::: Error in depleteOpenStock WebService -------->",e);
			
			strServiceMsg = Constants.OPEN_STOCK_WS_MSG_OTHERS;
			
			try
			{				
				connection.rollback();
			}
			catch (SQLException sqle) 
			{
				sqle.printStackTrace();
				logger.error("::::::::::::::::::: Error in depleteOpenStock WebService -------->",sqle);
			}
		}
		finally
		{
			pstmt = null;
			rset = null;
		}
		return strServiceMsg;
	}

	private String updateOpenStock(int intDistID, int intProdID, int productQuantity) throws Exception
	{
		String strReturn 			= Constants.OPEN_STOCK_WS_MSG_SUCCESS;
		PreparedStatement pstmt 	= null;
		int intUpd 					= 0;
		
		try
		{
			pstmt = connection.prepareStatement(SQL_OPEN_STOCK_UPDATE);
			
			logger.info(":::::::::::::::::::OPEN STOCK UPDATING FOR:::::::::::::::::::\n"+
						"intDistID     ::   "+intDistID+"\n" +
						"intProdID         ::   "+intProdID+"\n" +
						"productQuantity     ::   "+productQuantity);
			
			pstmt.setInt(1, intDistID);
			pstmt.setInt(2, intProdID);
			pstmt.setInt(3, productQuantity);
			pstmt.setInt(4, intDistID);
			pstmt.setInt(5, intProdID);
			pstmt.setInt(6, productQuantity);
			pstmt.setInt(7, intDistID);
			pstmt.setInt(8, intProdID);
			
			intUpd = pstmt.executeUpdate();
			
			if(intUpd<1)
			{
				strReturn = Constants.OPEN_STOCK_WS_MSG_OTHERS;
				
				logger.info("OTHERS ERROR OCCURED");
			}
		}
		catch (Exception e) 
		{
			e.printStackTrace();
			
			strReturn = Constants.OPEN_STOCK_WS_MSG_OTHERS;
			
			logger.error("::::::::::::::::::: Error in depleteOpenStock WebService -------->",e);
		}
		finally
		{
			pstmt = null;
		}
		
		return strReturn;
	}

	public List<List> getOpenStockDepleteInitDataDao(int intCircleID) throws DAOException 
	{
		List<List> listReturn 					= new ArrayList<List>();
		PreparedStatement pstmt 				= null;
		ResultSet rset							= null;
		DPOpenStockDepletionDTO depletionDTO 	= null;
		List<DPOpenStockDepletionDTO> listTSM	= null;
		List<DPOpenStockDepletionDTO> listDist	= null;
		List<DPOpenStockDepletionDTO> listProd	= null;
		try
		{
			pstmt = connection.prepareStatement(SQL_OPEN_STOCK_TSM);
			pstmt.setInt(1, Integer.parseInt(Constants.ACC_LEVEL_TSM));
			pstmt.setInt(2, intCircleID);
			
			rset = pstmt.executeQuery();
			
			listTSM = new ArrayList<DPOpenStockDepletionDTO>();
			while(rset.next())
			{
				depletionDTO = new DPOpenStockDepletionDTO();
				depletionDTO.setIntTSMID(rset.getInt("ACCOUNT_ID"));
				depletionDTO.setStrTSMName(rset.getString("ACCOUNT_NAME"));
				
				listTSM.add(depletionDTO);
			}
			
			pstmt = null;
			rset = null;
			
			pstmt = connection.prepareStatement(SQL_OPEN_STOCK_TSM);
			pstmt.setInt(1, Constants.ACC_LEVEL_DIST);
			pstmt.setInt(2, intCircleID);
			
			rset = pstmt.executeQuery();
			
			listDist = new ArrayList<DPOpenStockDepletionDTO>();
			while(rset.next())
			{
				depletionDTO = new DPOpenStockDepletionDTO();
				depletionDTO.setIntDistID(rset.getInt("ACCOUNT_ID"));
				depletionDTO.setStrDistName(rset.getString("ACCOUNT_NAME"));
				
				listDist.add(depletionDTO);
			}
			
			pstmt = null;
			rset = null;
			
			pstmt = connection.prepareStatement(SQL_OPEN_STOCK_PROD);
			pstmt.setInt(1, intCircleID);
			
			rset = pstmt.executeQuery();
			
			listProd = new ArrayList<DPOpenStockDepletionDTO>();
			while(rset.next())
			{
				depletionDTO = new DPOpenStockDepletionDTO();
				depletionDTO.setIntProdID(rset.getInt("PRODUCT_ID"));
				depletionDTO.setStrProdName(rset.getString("PRODUCT_NAME"));
				
				listProd.add(depletionDTO);
			}
			
			listReturn.add(listTSM);
			listReturn.add(listDist);
			listReturn.add(listProd);
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
		return listReturn;
	}

	public List<DPOpenStockDepletionDTO> filterDitributorsDao(Integer intTSMID, Integer intCircleID) throws DAOException 
	{
		List<DPOpenStockDepletionDTO> listReturn	= new ArrayList<DPOpenStockDepletionDTO>();
		PreparedStatement pstmt 					= null;
		ResultSet rset								= null;
		DPOpenStockDepletionDTO depletionDTO 		= null;
		
		try
		{
			if(intTSMID==0)
			{
				pstmt = connection.prepareStatement(SQL_OPEN_STOCK_TSM);
				pstmt.setInt(1, Constants.ACC_LEVEL_DIST);
				pstmt.setInt(2, intCircleID);
			}
			else
			{
				pstmt = connection.prepareStatement(SQL_OPEN_STOCK_DIST);
				pstmt.setInt(1, intTSMID);
			}
			
			rset = pstmt.executeQuery();
			
			listReturn = new ArrayList<DPOpenStockDepletionDTO>();
			while(rset.next())
			{
				depletionDTO = new DPOpenStockDepletionDTO();
				depletionDTO.setIntDistID(rset.getInt("ACCOUNT_ID"));
				depletionDTO.setStrDistName(rset.getString("ACCOUNT_NAME"));
				
				listReturn.add(depletionDTO);
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
		return listReturn;
	
	}

	public Integer getOpenStockBalanceDao(Integer intDistID, Integer intProductID) throws DAOException 
	{
		Integer intReturn			= null;
		PreparedStatement pstmt 	= null;
		ResultSet rset				= null;
		
		try
		{
			pstmt = connection.prepareStatement(SQL_OPEN_STOCK_BAL);
			logger.info("DIST ID  ::  "+intDistID);
			logger.info("PROD ID  ::  "+intProductID);
			pstmt.setInt(1, intDistID);
			pstmt.setInt(2, intProductID);
			
			rset = pstmt.executeQuery();
			
			if(rset.next())			
				intReturn = rset.getInt("CLOSING_STOCK");
			else
				intReturn = 0;			
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
		return intReturn;	
	}

	public List<List> depleteOpenStockDao(long loginUserId, Integer intCircleID, Integer intDistID, Integer intProdID, Integer intDepleteStock) throws DAOException 
	{
		//Runs for Circle Admin
		List<List> listReturn = new ArrayList<List>();
		PreparedStatement pstmt = null;
		try
		{
			connection.setAutoCommit(false);
			
			pstmt = connection.prepareStatement(SQL_OPEN_STOCK_UPDATE);
			
			logger.info(":::::::::::::::::::OPEN STOCK UPDATING FOR:::::::::::::::::::\n"+
						"intDistID     ::   "+intDistID+"\n" +
						"intProdID         ::   "+intProdID+"\n" +
						"productQuantity     ::   "+intDepleteStock);
			
			pstmt.setInt(1, intDistID);
			pstmt.setInt(2, intProdID);
			pstmt.setInt(3, intDepleteStock);
			pstmt.setInt(4, intDistID);
			pstmt.setInt(5, intProdID);
			pstmt.setInt(6, intDepleteStock);
			pstmt.setInt(7, intDistID);
			pstmt.setInt(8, intProdID);
			
			int intUpd = pstmt.executeUpdate();
			if(intUpd>0)
				logger.info("Stock depleted successfully");
			else
				logger.info("Stock depletion fails");
			
			listReturn = getOpenStockDepleteInitDataDao(intCircleID);
			
			connection.commit();
		}
		catch (Exception e) 
		{
			try
			{				
				connection.rollback();
			}
			catch (SQLException sqle) 
			{
				sqle.printStackTrace();
				logger.error("::::::::::::::::::: Error in depleteOpenStock -------->",sqle);
				throw new DAOException(sqle.getMessage());
			}
			
			e.printStackTrace();
			throw new DAOException(e.getMessage());
		}
		finally
		{
			pstmt = null;
		}
	
		return listReturn;
	}

}
