package com.ibm.dp.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import org.apache.log4j.Logger;

import com.ibm.dp.beans.StockSummaryReportBean;
import com.ibm.dp.common.Constants;
import com.ibm.dp.common.DBQueries;
import com.ibm.dp.dao.DPDistributorStockTransferDao;
import com.ibm.dp.dto.CircleDto;
import com.ibm.dp.dto.DPDistributorStockTransferDTO;
import com.ibm.utilities.Utility;
import com.ibm.virtualization.recharge.dao.rdbms.BaseDaoRdbms;
import com.ibm.virtualization.recharge.db.DBConnectionManager;
import com.ibm.virtualization.recharge.exception.DAOException;

public class DPDistributorStockTransferDaoImpl extends BaseDaoRdbms implements DPDistributorStockTransferDao 
{
	Logger logger = Logger.getLogger(DPDistributorStockTransferDao.class.getName());
	
	protected static final String SQL_DST_DISTRIBUTOR_LIST_KEY = "SQL_DST_DISTRIBUTOR_LIST_KEY";
	protected static final String SQL_DST_DISTRIBUTOR_LIST = DBQueries.SQL_DST_DISTRIBUTOR_LIST;
	
	protected static final String SQL_DST_PRODUCT_LIST_KEY = "SQL_DST_PRODUCT_LIST_KEY";
	protected static final String SQL_DST_PRODUCT_LIST = DBQueries.SQL_DST_PRODUCT_LIST;
	
	protected static final String SQL_DST_STOCK_AVAIL_KEY = "SQL_DST_STOCK_AVAIL_KEY";
	protected static final String SQL_DST_STOCK_AVAIL = DBQueries.SQL_DST_STOCK_AVAIL;
	
	protected static final String SQL_DST_STOCK_INSERT_KEY = "SQL_DST_STOCK_INSERT_KEY";
	protected static final String SQL_DST_STOCK_INSERT = DBQueries.SQL_DST_STOCK_INSERT;
	public static final String SQL_CIRCLE_MST 	= DBQueries.SQL_SELECT_CIRCLES;
	public static final String SQL_CIRCLE_MST_TSM 	= DBQueries.SQL_SELECT_CIRCLES_TSM;
	public DPDistributorStockTransferDaoImpl(Connection connection)
	{
		super(connection);
		
		queryMap.put(SQL_DST_DISTRIBUTOR_LIST_KEY, SQL_DST_DISTRIBUTOR_LIST);
		
		queryMap.put(SQL_DST_PRODUCT_LIST_KEY, SQL_DST_PRODUCT_LIST);
		
		queryMap.put(SQL_DST_STOCK_AVAIL_KEY, SQL_DST_STOCK_AVAIL);
		
		queryMap.put(SQL_DST_STOCK_INSERT_KEY, SQL_DST_STOCK_INSERT);
	}
	
	
	
	public List<CircleDto> getAllCircleList() throws DAOException 
	{
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
			pstmt = null;
			rset = null;
		}
		return circleListDTO;
	}
	
	public List<List> getInitDistStockTransferDao(int intCircleID,int intAccountZoneID) throws DAOException
	{
		PreparedStatement pstmt = null;
		ResultSet rset = null;
		List<List> listReturn = new ArrayList<List>();
		
		List<DPDistributorStockTransferDTO> listDistributor = null;
		List<DPDistributorStockTransferDTO> listProduct = null;
		
		try
		{
			pstmt = connection.prepareStatement(queryMap.get(SQL_DST_DISTRIBUTOR_LIST_KEY));
			
			pstmt.setLong(1, intCircleID);
			pstmt.setLong(2, intAccountZoneID);
			
			rset = pstmt.executeQuery();
			
			listDistributor = fetchDistributorList(rset);
			
			pstmt = null;
			rset = null;
			
			pstmt = connection.prepareStatement(queryMap.get(SQL_DST_PRODUCT_LIST_KEY));
			
			pstmt.setLong(1, intCircleID);
			
			rset = pstmt.executeQuery();
			
			listProduct = getProductList(rset);
			
			listReturn.add(listDistributor);
			listReturn.add(listProduct);
		}
		catch(SQLException sqlEx)
		{
			sqlEx.printStackTrace();
			logger.error("SQL Exception occured while fetching initial data for Distributor Stock Transfer  ::  "+sqlEx.getMessage());
			throw new DAOException(sqlEx.getMessage());
		}
		catch(Exception e)
		{
			e.printStackTrace();
			logger.error("Exception occured while fetching initial data for Distributor Stock Transfer  ::  "+e.getMessage());
			//throw new DAOException(e.getMessage());			
		}
		finally
		{
			pstmt = null;
			rset = null;
			listDistributor = null;
			listProduct = null;
		}
		return listReturn;
	}

	private List<DPDistributorStockTransferDTO> getProductList(ResultSet rset) throws Exception
	{
		List<DPDistributorStockTransferDTO> listReturn = new ArrayList<DPDistributorStockTransferDTO>();
		DPDistributorStockTransferDTO  dpDPDDto = null;
		while(rset.next())
		{
			dpDPDDto = new DPDistributorStockTransferDTO();
			
			dpDPDDto.setIntProductID(rset.getInt("PRODUCT_ID"));
			
			dpDPDDto.setStrProductName(rset.getString("PRODUCT_NAME"));
			
			listReturn.add(dpDPDDto);
		}
		
		return listReturn;
	}

	private List<DPDistributorStockTransferDTO> fetchDistributorList(ResultSet rset)  throws Exception
	{
		List<DPDistributorStockTransferDTO> listReturn = new ArrayList<DPDistributorStockTransferDTO>();
		DPDistributorStockTransferDTO dpDPDDto = null;
		while(rset.next())
		{
			dpDPDDto = new DPDistributorStockTransferDTO();
			
			dpDPDDto.setIntDistID(rset.getInt("ACCOUNT_ID"));
			
			dpDPDDto.setStrDistName(rset.getString("ACCOUNT_NAME"));
			
			listReturn.add(dpDPDDto);
		}
		
		return listReturn;
	}
	
	public int getAvailableStockDao(Integer intDistID, Integer intProdID) throws DAOException
	{
		PreparedStatement pstmt = null;
		ResultSet rset = null;
		int intReturn = 0;
		
		try
		{
			pstmt = connection.prepareStatement(queryMap.get(SQL_DST_STOCK_AVAIL_KEY));
			
			pstmt.setInt(1, intDistID);
			pstmt.setInt(2, intProdID);
			
			rset = pstmt.executeQuery();
			
			while(rset.next())			
				intReturn = rset.getInt("STOCK_AVAIL");
		}
		catch(Exception e)
		{
			e.printStackTrace();
			logger.error("Exception occured while fetching available Stock of a Product for a Distributor  ::  "+e.getMessage());
			throw new DAOException(e.getMessage());			
		}
		finally
		{
			pstmt = null;
			rset = null;
		}
		return intReturn;
	}
	
	public List<List> transferDistStockDao(Map<String, String[]> tranferedDistStock, long loginUserId, int intCircleID,
			int intAccountZoneID) throws DAOException
	 {
		List<List> listReturn = null;
		PreparedStatement pstmt = null;
		
		try
		{
			connection.setAutoCommit(false);
			
			String[] arrFromDistID = tranferedDistStock.get(Constants.DST_FROM_DIST_ARRAY);
			String[] arrToDistID = tranferedDistStock.get(Constants.DST_TO_DIST_ARRAY);
			String[] arrProductID = tranferedDistStock.get(Constants.DST_PRODUCT_ARRAY);
			String[] arrTransQty = tranferedDistStock.get(Constants.DST_TRANS_QTY_ARRAY);
			
			int intNoOfRecords = arrFromDistID.length;
			
			logger.info("NO OF RECORDS SUBMITTED  ::  "+intNoOfRecords);
			
			pstmt = connection.prepareStatement(queryMap.get(SQL_DST_STOCK_INSERT_KEY));
			int intUpd = 0;
			
			java.sql.Timestamp currentTime = Utility.getCurrentTimeStamp();
			
			for(int count=0; count<intNoOfRecords; count++)
			{
				pstmt.setInt(1, Integer.parseInt(arrFromDistID[count]));
				pstmt.setInt(2, Integer.parseInt(arrToDistID[count]));
				pstmt.setInt(3, Integer.parseInt(arrProductID[count]));
				pstmt.setInt(4, Integer.parseInt(arrTransQty[count]));
				pstmt.setString(5, Constants.DST_STATUS_INITIATED);
				pstmt.setLong(6, loginUserId);
				pstmt.setTimestamp(7, currentTime);
				
				intUpd = pstmt.executeUpdate();
			}
			listReturn = getInitDistStockTransferDao(intCircleID, intAccountZoneID);
			connection.commit();
		}
		catch (Exception e) 
		{
			try
			{
				connection.rollback();
			}
			catch(SQLException sqlEx)
			{
				sqlEx.printStackTrace();
			}
			
			e.printStackTrace();
			logger.error("Exception occured while saving transfered Distributor Stock   ::  "+e.getMessage());
			listReturn = getInitDistStockTransferDao(intCircleID, intAccountZoneID);
			throw new DAOException(e.getMessage());	
		}
		finally
		{
			pstmt = null;
		}
		return listReturn;
	 }
	public List<List<CircleDto>> getInitData(long loginUserId) throws DAOException 
	{
		logger.info("getInitData*******************123******");
		List<List<CircleDto>> retListDTO	= new ArrayList<List<CircleDto>>();
		List<CircleDto> circleListDTO = new ArrayList<CircleDto>();
		List<CircleDto> busCatListDTO = new ArrayList<CircleDto>();
		
		PreparedStatement pstmt = null;
		ResultSet rset			= null;
		CircleDto  circleDto = null;
				
		try
		{
			pstmt = connection.prepareStatement(SQL_CIRCLE_MST_TSM);
			pstmt.setLong(1, loginUserId);
			rset = pstmt.executeQuery();
			circleListDTO = new ArrayList<CircleDto>();
			
			while(rset.next())
			{
				circleDto = new CircleDto();
				circleDto.setCircleId(rset.getString("CIRCLE_ID"));
				circleDto.setCircleName(rset.getString("CIRCLE_NAME"));
								
				circleListDTO.add(circleDto);
			}
			
			retListDTO.add(circleListDTO);
			
			DBConnectionManager.releaseResources(pstmt, rset);
			
			/* pstmt = connection.prepareStatement(DBQueries.SQL_BUSINESS_CATEGORY);  */
			
			/*  Added by Parnika for getting business categories according to transaction type */
			
			pstmt = connection.prepareStatement(DBQueries.SQL_BUSINESS_CATEGORY_TSM_TYPE);	
			pstmt.setLong(1, loginUserId);
			
			/* End of changes by parnika */
			rset = pstmt.executeQuery();
			while(rset.next())
			{
				circleDto = new CircleDto();
				circleDto.setCircleId(rset.getString("CATEGORY_CODE"));
				circleDto.setCircleName(rset.getString("CATEGORY_NAME"));
								
				busCatListDTO.add(circleDto);
			}
			
			retListDTO.add(busCatListDTO);
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
		return retListDTO;
	}
	
	
	public int validateToDistDao(Integer intFromDistID, Integer intToDistID) throws DAOException
	{
		PreparedStatement pstmt = null;
		ResultSet rset = null;
		int intReturn = 0;
		
		try
		{
			pstmt = connection.prepareStatement("select case when a.CIRCLE_ID=b.CIRCLE_ID then 1 else 0 end as VALID_CIRCLE from VR_ACCOUNT_DETAILS a inner join VR_ACCOUNT_DETAILS b on a.ACCOUNT_LEVEL=b.ACCOUNT_LEVEL where a.ACCOUNT_ID=? and b.ACCOUNT_ID=?");
			
			pstmt.setInt(1, intFromDistID);
			pstmt.setInt(2, intToDistID);
			logger.info("********daoimpl***************12432*****");
			rset = pstmt.executeQuery();
			
			if(rset.next())			
			{
				intReturn = rset.getInt("VALID_CIRCLE");
			}
			logger.info("intReturn "+intReturn);
			
			
			//if(rset.getInt("VALID_CIRCLE") == 1)
		}
		catch(Exception e)
		{
			e.printStackTrace();
			logger.error("Exception occured while selecting fromdist and todist of same circle  ::  "+e.getMessage());
			throw new DAOException(e.getMessage());			
		}
		
		return intReturn;
	}



	public List<StockSummaryReportBean> getDistAccountDetailsDAO(int intTSMID, int circleId, int intBusCat) throws DAOException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		List<StockSummaryReportBean> revLogList = new ArrayList<StockSummaryReportBean>();
		try 
		{
			/*
			int intDistTransType = 1;
			
			if(intBusCat==1)
				intDistTransType = 2;
			*/
			
			ps = connection.prepareStatement(DBQueries.GET_TSM_DIST_TRANSACTION);
			ps.setInt(1, intTSMID);
			ps.setInt(2, circleId);
			//ps.setInt(3, intDistTransType);
			
			rs = ps.executeQuery();
			
			StockSummaryReportBean statusReportBean = null;
			while(rs.next())
			{
				statusReportBean = new StockSummaryReportBean();
				statusReportBean.setDistAccId(String.valueOf(rs.getInt("account_id")));
				statusReportBean.setDistAccName(rs.getString("account_name"));
				revLogList.add(statusReportBean);
			}
		}catch(SQLException sqle){
			sqle.printStackTrace();
			throw new DAOException("SQLException: " + sqle.getMessage());
		}catch(Exception e){
			e.printStackTrace();
			throw new DAOException("Exception: " + e.getMessage());
		}finally{
			try {
				if (rs != null)
					rs.close();
				if (ps != null)
					ps.close();
			} catch (Exception e) {
				e.printStackTrace();
				logger.error("Error Occured in ReportDAOImpl "+e.getMessage());
			}
		}
		
		return revLogList;
	}
	public boolean checkcombination(String fromDistID, Integer intProdID,String toDistid) throws DAOException
	{
		PreparedStatement ps = null;
		ResultSet rs = null;
		boolean flag = false;
		try 
		{
			ps = connection.prepareStatement(DBQueries.CHECK_NUMBER_OF_REQUESTS_PEDING);
			ps.setInt(1, Integer.parseInt(fromDistID));
			ps.setInt(2, Integer.parseInt(toDistid));
			ps.setInt(3, intProdID);
							
			rs = ps.executeQuery();
			
			while(rs.next())
			{
				if(rs.getInt(1)>0)
				{
					flag=true;
					logger.info(rs.getInt(1)+" request pending with distributor"+flag);
				}
			}
			
		}catch(SQLException sqle){
			sqle.printStackTrace();
			throw new DAOException("SQLException: " + sqle.getMessage());
		}catch(Exception e){
			e.printStackTrace();
			throw new DAOException("Exception: " + e.getMessage());
		}finally{
			try {
				if (rs != null)
					rs.close();
				if (ps != null)
					ps.close();
			} catch (Exception e) {
				e.printStackTrace();
				logger.error("Error Occured in ReportDAOImpl "+e.getMessage());
			}
		}
		
		return flag;
	}
}