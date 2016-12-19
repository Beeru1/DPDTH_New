package com.ibm.dp.service.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

import com.ibm.dp.common.DBQueries;
import com.ibm.dp.dao.CollectionDetailReportDao;
import com.ibm.dp.dao.DPInterSSDStockTransferReportDao;
import com.ibm.dp.dao.StockRecoSummReportDao;
import com.ibm.dp.dao.impl.DPInterSSDStockTransferReportDaoImpl;
import com.ibm.dp.dto.CollectionReportDTO;
import com.ibm.dp.dto.DpProductCategoryDto;
import com.ibm.dp.service.CollectionReportService;
import com.ibm.dpmisreports.common.DropDownUtility;
import com.ibm.virtualization.recharge.common.Constants;
import com.ibm.virtualization.recharge.common.ResourceReader;
import com.ibm.virtualization.recharge.dao.DAOFactory;
import com.ibm.virtualization.recharge.db.DBConnectionManager;
import com.ibm.virtualization.recharge.exception.DAOException;
import com.ibm.virtualization.recharge.exception.VirtualizationServiceException;

public class CollectionReportServiceImpl implements CollectionReportService{

	private Logger logger = Logger.getLogger(CollectionReportServiceImpl.class.getName());
	public static final String SQL_COLLECTION_MST 	= DBQueries.SQL_COLLECTION_MST;
	
	public List<DpProductCategoryDto> getProductCategory() throws Exception
	{
		Connection con=null;
		DPInterSSDStockTransferReportDao interSSDReportDao = new DPInterSSDStockTransferReportDaoImpl(con);
		List<DpProductCategoryDto> productList = null;
		try
		{
			productList = DropDownUtility.getInstance().getProductCategoryLst();
		}
		catch (DAOException de) {
			logger.error(" Exception occured : Message : " + de.getMessage());
			throw new VirtualizationServiceException(de.getMessage());
		}	
		return productList;
	}
	
	public List<CollectionReportDTO> getCollectionTypeMaster() throws DAOException 
	{
		List<CollectionReportDTO> reverseCollectionListDTO	= new ArrayList<CollectionReportDTO>();
		PreparedStatement pstmt = null;
		ResultSet rset			= null;
		Connection connection=null;
		CollectionReportDTO  reverseCollectionDto = null;
				
		try
		{
			connection = DBConnectionManager.getDBConnection();
			pstmt = connection.prepareStatement(SQL_COLLECTION_MST);
			rset = pstmt.executeQuery();
			reverseCollectionListDTO = new ArrayList<CollectionReportDTO>();
			
			while(rset.next())
			{
				reverseCollectionDto = new CollectionReportDTO();
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
			pstmt = null;
			rset = null;
		}
		return reverseCollectionListDTO;
	}
	
	public List<DpProductCategoryDto> getProductList() throws  VirtualizationServiceException {
		Connection connection=null;
		List<DpProductCategoryDto> productList = null;
		try
		{
			connection = DBConnectionManager.getDBConnection();
			CollectionDetailReportDao collectionDao  = DAOFactory.getDAOFactory(Integer.parseInt(
					ResourceReader.getCoreResourceBundleValue(Constants.DATABASE_TYPE))).getCollectionDetailReportDao(connection);
			
			productList = collectionDao.getProductList();
		}
		catch (DAOException de) {
			logger.error(" Exception occured : Message : " + de.getMessage());
			throw new VirtualizationServiceException(de.getMessage());
		}	
		finally
		{
			//Releasing the database connection
			DBConnectionManager.releaseResources(connection);
		}
		return productList;
	}
	
	public List<CollectionReportDTO> getCollectionDetailExcel(CollectionReportDTO reportDto) throws VirtualizationServiceException {
		Connection connection=null;
		List<CollectionReportDTO> collectionDetailList = null;
		try
		{
			connection = DBConnectionManager.getDBConnection();
			CollectionDetailReportDao collectionDetailDao = DAOFactory.getDAOFactory(Integer.parseInt(
					ResourceReader.getCoreResourceBundleValue(Constants.DATABASE_TYPE))).getCollectionDetailReportDao(connection);
			collectionDetailList = collectionDetailDao.getCollectionDetailExcel(reportDto);
		}
		catch (DAOException de) {
			logger.error(" Exception occured : Message : " + de.getMessage());
			throw new VirtualizationServiceException(de.getMessage());
		}	
		return collectionDetailList;
	}
}	