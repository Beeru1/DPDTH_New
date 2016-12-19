//ask what needs to be done for List<InventoryStatusBean>??
//line no. 150 onwards has confusing methods starting with List..

package com.ibm.dp.service.impl;

import java.sql.Connection;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

import com.ibm.dp.beans.DPReverseLogisticReportFormBean;
import com.ibm.dp.beans.InventoryStatusBean;
import com.ibm.dp.beans.ReverseLogisticReportBean;
import com.ibm.dp.dao.DPDetailReportDao;
import com.ibm.dp.dao.DPReverseLogisticReportDao;
import com.ibm.dp.dao.impl.DPDetailReportDaoImpl;
import com.ibm.dp.dao.impl.DPReverseLogisticReportDaoImpl;
import com.ibm.dp.dto.DPDetailReportDTO;
import com.ibm.dp.dto.DPReverseLogisticReportDTO;
import com.ibm.dp.service.DPDetailReportService;
import com.ibm.dp.service.DPReverseLogisticReportService;
import com.ibm.virtualization.recharge.db.DBConnectionManager;
import com.ibm.virtualization.recharge.exception.DAOException;
import com.ibm.virtualization.recharge.exception.VirtualizationServiceException;


public class DPDetailReportServiceImpl implements DPDetailReportService {
	
	private Logger logger = Logger.getLogger(DPCreateBeatServiceImpl.class.getName());

	public List<DPDetailReportDTO> getdetailReportExcel(String fromDate, String toDate) throws VirtualizationServiceException
	{
		System.out.println("inside getdetailReportExcel of DPDetailReportServiceImpl");
		Connection connection = null;
		
		List<DPDetailReportDTO> detailReportList = new ArrayList<DPDetailReportDTO>();
		try
		{
			connection = DBConnectionManager.getDBConnection();
			DPDetailReportDao detailReportDao = new DPDetailReportDaoImpl(connection);
			detailReportList = detailReportDao.getDetailReportExcel(fromDate, toDate);
		}catch (DAOException de) 
		{
			logger.error(" Exception occured : Message : " + de.getMessage());
			throw new VirtualizationServiceException(de.getMessage());
		}
		finally
		{
			//Releasing the database connection
			DBConnectionManager.releaseResources(connection);
		}
		return detailReportList;
	}

}