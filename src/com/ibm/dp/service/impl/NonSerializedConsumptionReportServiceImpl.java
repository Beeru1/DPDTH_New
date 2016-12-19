package com.ibm.dp.service.impl;

import java.sql.Connection;
import java.util.List;

import org.apache.log4j.Logger;

import com.ibm.dp.common.DBQueries;
import com.ibm.dp.dao.NonSerializedConsumptionReportDao;
import com.ibm.dp.dao.CollectionDetailReportDao;
import com.ibm.dp.dao.DPInterSSDStockTransferReportDao;
import com.ibm.dp.dao.impl.DPInterSSDStockTransferReportDaoImpl;
import com.ibm.dp.dto.NonSerializedConsumptionReportDTO;
import com.ibm.dp.dto.CollectionReportDTO;
import com.ibm.dp.dto.DpProductCategoryDto;
import com.ibm.dp.service.NonSerializedConsumptionReportService;
import com.ibm.dpmisreports.common.DropDownUtility;
import com.ibm.virtualization.recharge.common.Constants;
import com.ibm.virtualization.recharge.common.ResourceReader;
import com.ibm.virtualization.recharge.dao.DAOFactory;
import com.ibm.virtualization.recharge.db.DBConnectionManager;
import com.ibm.virtualization.recharge.exception.DAOException;
import com.ibm.virtualization.recharge.exception.VirtualizationServiceException;

public class NonSerializedConsumptionReportServiceImpl implements NonSerializedConsumptionReportService {
	private Logger logger = Logger.getLogger(PODetailReportServiceImpl.class.getName());
	public static final String SQL_COLLECTION_MST 	= DBQueries.SQL_COLLECTION_MST;
	
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
	
	public List<NonSerializedConsumptionReportDTO> getNonSerializedConsumptionExcel(NonSerializedConsumptionReportDTO reportDto) throws VirtualizationServiceException {
		Connection connection=null;
		List<NonSerializedConsumptionReportDTO> nonSerializedConsumptionList = null;
		try
		{
			connection = DBConnectionManager.getDBConnection();
			NonSerializedConsumptionReportDao nonSerializedConsumptionDao = DAOFactory.getDAOFactory(Integer.parseInt(
					ResourceReader.getCoreResourceBundleValue(Constants.DATABASE_TYPE))).getNonSerializedConsumptionReportDao(connection);
			nonSerializedConsumptionList = nonSerializedConsumptionDao.getNonSerializedConsumptionExcel(reportDto);
		}
		catch (DAOException de) {
			logger.error(" Exception occured : Message : " + de.getMessage());
			throw new VirtualizationServiceException(de.getMessage());
		}	
		return nonSerializedConsumptionList;
	}
}
