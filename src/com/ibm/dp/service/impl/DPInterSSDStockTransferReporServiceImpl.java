package com.ibm.dp.service.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

import com.ibm.dp.beans.DPInterSSDStockTransferReportFormBean;
import com.ibm.dp.beans.DPReverseLogisticReportFormBean;
import com.ibm.dp.common.Constants;
import com.ibm.dp.common.DBQueries;
import com.ibm.dp.dao.CollectionDetailReportDao;
import com.ibm.dp.dao.DPInterSSDStockTransferReportDao;
import com.ibm.dp.dao.DPReverseLogisticReportDao;
import com.ibm.dp.dao.impl.DPInterSSDStockTransferReportDaoImpl;
import com.ibm.dp.dao.impl.DPReverseLogisticReportDaoImpl;
import com.ibm.dp.dto.CollectionReportDTO;
import com.ibm.dp.dto.DpProductCategoryDto;
import com.ibm.dp.dto.InterSSDReportDTO;
import com.ibm.dp.service.DPInterSSDStockTransferReportService;
import com.ibm.dp.service.DPReverseLogisticReportService;
import com.ibm.dpmisreports.common.DropDownUtility;
import com.ibm.dpmisreports.common.SelectionCollection;
import com.ibm.virtualization.recharge.common.ResourceReader;
import com.ibm.virtualization.recharge.dao.DAOFactory;
import com.ibm.virtualization.recharge.db.DBConnectionManager;
import com.ibm.virtualization.recharge.exception.DAOException;
import com.ibm.virtualization.recharge.exception.VirtualizationServiceException;

public class DPInterSSDStockTransferReporServiceImpl implements DPInterSSDStockTransferReportService{

	private Logger logger = Logger.getLogger(DPInterSSDStockTransferReporServiceImpl.class.getName());

	
	
	public List<DpProductCategoryDto> getProductCategory() throws Exception
	{
		Connection con=null;
		DPInterSSDStockTransferReportDao interSSDReportDao = new DPInterSSDStockTransferReportDaoImpl(con);
		List<DpProductCategoryDto> productList = null;
		try
		{
			con = DBConnectionManager.getDBConnection(); // db2
			
			productList = DropDownUtility.getInstance().getProductCategoryLst();
		}
		catch (DAOException de) {
			logger.error(" Exception occured : Message : " + de.getMessage());
			throw new VirtualizationServiceException(de.getMessage());
		}	
		return productList;
	}
	public List<InterSSDReportDTO> getInterSSDStockTransferExcel(InterSSDReportDTO reportDto) throws VirtualizationServiceException {
		Connection connection=null;
		List<InterSSDReportDTO> collectionDetailList = null;
		try
		{
			connection = DBConnectionManager.getDBConnection();
			DPInterSSDStockTransferReportDao collectionDetailDao = DAOFactory.getDAOFactory(Integer.parseInt(
					ResourceReader.getCoreResourceBundleValue(Constants.DATABASE_TYPE))).getDPInterSSDStockTransferReportDao(connection);
			collectionDetailList = collectionDetailDao.getInterSSDStockTransferExcel(reportDto);
		}
		catch (DAOException de) {
			logger.error(" Exception occured : Message : " + de.getMessage());
			throw new VirtualizationServiceException(de.getMessage());
		}	
		return collectionDetailList;
	}
}
