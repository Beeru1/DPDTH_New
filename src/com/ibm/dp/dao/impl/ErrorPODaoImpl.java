package com.ibm.dp.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import org.apache.log4j.Logger;

import com.ibm.dp.common.DBQueries;
import com.ibm.dp.dao.ErrorPODao;
import com.ibm.dp.dao.STBFlushOutDao;
import com.ibm.dp.dto.DuplicateSTBDTO;
import com.ibm.virtualization.recharge.dao.rdbms.BaseDaoRdbms;
import com.ibm.virtualization.recharge.db.DBConnectionManager;
import com.ibm.virtualization.recharge.exception.DAOException;

public class ErrorPODaoImpl  extends BaseDaoRdbms implements ErrorPODao 
{
	private static Logger logger = Logger.getLogger(STBFlushOutDaoImpl.class.getName());
	
	protected static final String SQL_STB_FLUSH_OUT_INIT_KEY = "SQL_STB_FLUSH_OUT_INIT_KEY";
	protected static final String SQL_STB_FLUSH_OUT_INIT = DBQueries.SQL_VIEW_ERROR_PO_INIT;
	
	protected static final String SQL_PO_DETAIL_LIST_KEY = "SQL_PO_DETAIL_LIST_KEY";
	protected static final String SQL_PO_DETAIL_LIST = DBQueries.SQL_PO_DETAIL_LIST;
	
	public ErrorPODaoImpl(Connection connection)
	{
		super(connection);
		
		queryMap.put(SQL_STB_FLUSH_OUT_INIT_KEY, SQL_STB_FLUSH_OUT_INIT);
		
		queryMap.put(SQL_PO_DETAIL_LIST_KEY, SQL_PO_DETAIL_LIST);
		
	}
	
	public ArrayList<DuplicateSTBDTO> getSTBListDao(String loginUserIdAndGroup)throws DAOException
	{		
		PreparedStatement pstmt = null;
		ResultSet rset = null;
		String queryErrorPODetails =null;
		ArrayList<DuplicateSTBDTO> stbList = new ArrayList<DuplicateSTBDTO>();
		String userIdAndGroup[]=null ;
		try
		{	
			if(loginUserIdAndGroup !=null)
			userIdAndGroup = loginUserIdAndGroup.split(",");
			else
				throw new DAOException("loginUserIdAndGroup is null::"); 
			logger.info("loginUserId :  "+userIdAndGroup[0] +" & Group : "+userIdAndGroup[1]);
			// chaned done by pratap on 30-07-2013 for group id 6(TSM) , made a if block and new query is invoked
			if(Integer.parseInt(userIdAndGroup[1]) == 6)
			{
				//queryErrorPODetails = queryErrorPODetails.replace("with ur", " and DD.TSM_ID = "+userIdAndGroup[0] +" with ur");
				queryErrorPODetails = DBQueries.SQL_VIEW_ERROR_PO_INIT_FOR_TSM;
				pstmt = connection.prepareStatement(queryErrorPODetails);
				pstmt.setInt(1, Integer.parseInt(userIdAndGroup[0]));
				pstmt.setInt(2, Integer.parseInt(userIdAndGroup[0]));
				pstmt.setInt(3, Integer.parseInt(userIdAndGroup[0]));
				pstmt.setInt(4, Integer.parseInt(userIdAndGroup[0]));
				
			}
			else
			{
			queryErrorPODetails = queryMap.get(SQL_STB_FLUSH_OUT_INIT_KEY);
			pstmt = connection.prepareStatement(queryErrorPODetails);
			}
			rset = pstmt.executeQuery();			
			stbList = fetchSTBListInitData(rset);			
		}
		catch(SQLException sqlEx)
		{
			logger.error("SQL Exception occured while fetching initial data for Delivery Challan Accptance  ::  "+sqlEx.getMessage());
			throw new DAOException(sqlEx.getMessage());
		}
		catch(Exception e)
		{
			logger.error("Exception occured while fetching initial data for Delivery Challan Accptance  ::  "+e.getMessage());
			throw new DAOException(e.getMessage());			
		}
		finally
		{
			DBConnectionManager.releaseResources(pstmt ,rset ); // added by pratap on 31-07-2013
		}
		return stbList;	
	}
	
	
	public ArrayList<DuplicateSTBDTO> getpoDetailList(String poNumber) throws DAOException
	{

		PreparedStatement pstmt = null;
		
		ResultSet rset = null;
		ArrayList<DuplicateSTBDTO> poDetailsList = new ArrayList<DuplicateSTBDTO>();
		
		try
		{
			connection.setAutoCommit(false);			
			pstmt = connection.prepareStatement(queryMap.get(SQL_PO_DETAIL_LIST_KEY));		
			pstmt.setString(1, poNumber);
			rset = pstmt.executeQuery();
			poDetailsList = fetchPODetailData(rset);	
		
		}
		catch(Exception e)
		{
			try
			{
				connection.rollback();
			}
			catch(Exception e2)
			{
				e2.printStackTrace();
			}
						
			e.printStackTrace();
			logger.error("Exception occured while Saving Delivery Challan  ::  "+e.getMessage());
			throw new DAOException(e.getMessage());			
		}
		finally
		{
			pstmt = null;
		}
		return poDetailsList;
	}
	

	private ArrayList<DuplicateSTBDTO> fetchSTBListInitData(ResultSet rset) throws Exception
	{
		ArrayList<DuplicateSTBDTO> stbList = new ArrayList<DuplicateSTBDTO>();
		DuplicateSTBDTO  stbListDto = null;
		while(rset.next())
		{
			stbListDto = new DuplicateSTBDTO();
			stbListDto.setStrCircleName(rset.getString("CIRCLE_NAME"));
			stbListDto.setStrDistOLMId(rset.getString("DISTRIBUTOR_OLM_ID"));
			stbListDto.setStrDistName(rset.getString("DISTRIBUTOR_NAME"));
			stbListDto.setStrTSMName(rset.getString("TSM_NAME"));
			stbListDto.setStrPONumber(rset.getString("PO_NO"));
			stbListDto.setStrDCNo(rset.getString("DC_NO"));
			stbListDto.setStrDCDate(rset.getString("DC_DATE"));			
			stbList.add(stbListDto);
		}
		
		return stbList;
	}
	
	private ArrayList<DuplicateSTBDTO> fetchPODetailData(ResultSet rset) throws Exception
	{
		ArrayList<DuplicateSTBDTO> poDetailList = new ArrayList<DuplicateSTBDTO>();
		DuplicateSTBDTO  stbListDto = null;
		while(rset.next())
		{
			stbListDto = new DuplicateSTBDTO();			
			stbListDto.setStrCircleName(rset.getString("CIRCLE_NAME"));
			stbListDto.setStrDistOLMId(rset.getString("DISTRIBUTOR_OLM_ID"));
			stbListDto.setStrDistName(rset.getString("DISTRIBUTOR_NAME"));
			stbListDto.setStrTSMName(rset.getString("TSM_NAME"));
			stbListDto.setStrPONumber(rset.getString("PO_NO"));
			stbListDto.setStrDCNo(rset.getString("DC_NO"));
			stbListDto.setStrDCDate(rset.getString("DC_DATE"));					
			stbListDto.setStrSerialNo(rset.getString("SERIAL_NO"));
			stbListDto.setStrProductName(rset.getString("PRODUCT_NAME"));
			stbListDto.setStrSTBStatus(rset.getString("STB_STATUS"));
			stbListDto.setStrErrorMsg(rset.getString("CONF_STATUS"));
			poDetailList.add(stbListDto);
		}
		
		return poDetailList;
	}
		
}
