package com.ibm.dp.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

import com.ibm.dp.common.DBQueries;
import com.ibm.dp.dao.NonSerializedConsumptionReportDao;
import com.ibm.dp.dto.NonSerializedConsumptionReportDTO;
import com.ibm.virtualization.recharge.dao.rdbms.BaseDaoRdbms;
import com.ibm.virtualization.recharge.db.DBConnectionManager;
import com.ibm.virtualization.recharge.exception.DAOException;

public class NonSerializedConsumptionReportDaoImpl   extends BaseDaoRdbms implements NonSerializedConsumptionReportDao {
	private Logger logger = Logger.getLogger(PODetailReportDaoImpl.class.getName());
	public static final String SQL_ACTIVATION_DETAIL_REPORT 	= DBQueries.SQL_ACTIVATION_DETAIL_REPORT;
	
	public NonSerializedConsumptionReportDaoImpl(Connection connection) {
		super(connection);
	}
	
	public List<NonSerializedConsumptionReportDTO> getNonSerializedConsumptionExcel(NonSerializedConsumptionReportDTO reportDto) throws DAOException{
		List<NonSerializedConsumptionReportDTO> listReturn = new ArrayList<NonSerializedConsumptionReportDTO>();
		PreparedStatement pstmt = null;
		ResultSet rsetReport = null;
		logger.info(" in  &&&&&&&&&&&&&&&&&&&&& getNonSerializedConsumptionExcel");
		try
		{
			NonSerializedConsumptionReportDTO reportReturnDto =null;
			pstmt =  connection.prepareStatement(SQL_ACTIVATION_DETAIL_REPORT);
			rsetReport= pstmt.executeQuery();
			while(rsetReport.next()){
				reportReturnDto =new NonSerializedConsumptionReportDTO();
				reportReturnDto.setDistId(rsetReport.getString(""));
				//reportReturnDto.setDistLoginId(rsetReport.getString(""));
				reportReturnDto.setDistName(rsetReport.getString(""));
				reportReturnDto.setTsmName(rsetReport.getString(""));
				reportReturnDto.setCircleName(rsetReport.getString(""));
				reportReturnDto.setOracleLocatorCode(rsetReport.getString(""));
				reportReturnDto.setDate(rsetReport.getString(""));
				reportReturnDto.setBatchId(rsetReport.getString(""));
				reportReturnDto.setStbType(rsetReport.getString(""));
				reportReturnDto.setItemcode(rsetReport.getString(""));
				reportReturnDto.setQuantity(rsetReport.getString(""));
				reportReturnDto.setInstallationDate(rsetReport.getString(""));
				reportReturnDto.setStatus(rsetReport.getString(""));
				reportReturnDto.setCreatedBy(rsetReport.getString(""));
				reportReturnDto.setCreationDate(rsetReport.getString(""));
				reportReturnDto.setLastUpdatedBy(rsetReport.getString(""));
				reportReturnDto.setLastUpdateDate(rsetReport.getString(""));
				listReturn.add(reportReturnDto);
			}
			logger.info("getNonSerializedConsumptionExcel executed successfuly in NonSerializedConsumptionReportDaoImpl");
			
		}catch (Exception e) 
		{
			e.printStackTrace();
			throw new DAOException(e.getMessage());
		}
		finally
		{
			DBConnectionManager.releaseResources(pstmt ,rsetReport );
		}
		
		return listReturn;
	}
}
