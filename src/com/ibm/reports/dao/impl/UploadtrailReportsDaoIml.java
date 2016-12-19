package com.ibm.reports.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

import com.ibm.dp.reports.common.ReportConstants;
import com.ibm.reports.dao.GenericReportsDao;
import com.ibm.reports.dao.UploadTrailReportsDao;
import com.ibm.reports.dto.GenericOptionDTO;
import com.ibm.reports.dto.GenericReportsOutputDto;
import com.ibm.reports.dto.UploadTrailReportDTO;
import com.ibm.reports.dto.UploadTrailReportsOutputDTO;
import com.ibm.reports.dto.UploadTrailReportsParameterDTO;
import com.ibm.virtualization.recharge.common.Utility;
import com.ibm.virtualization.recharge.dao.rdbms.BaseDaoRdbms;
import com.ibm.virtualization.recharge.db.DBConnectionManager;
import com.ibm.virtualization.recharge.exception.DAOException;

public class UploadtrailReportsDaoIml implements UploadTrailReportsDao{


	private Logger logger = Logger.getLogger(UploadtrailReportsDaoIml.class.getName());
	private static UploadTrailReportsDao ReportsDao = new UploadtrailReportsDaoIml();

	
	public static UploadTrailReportsDao getInstance() {
		// TODO Auto-generated method stub
		return ReportsDao;
	}

	@Override
	public List<UploadTrailReportDTO> getUploadTypeList() {
		// TODO Auto-generated method stub
		List<UploadTrailReportDTO> uploadTypeList = new ArrayList<UploadTrailReportDTO>();
			
		UploadTrailReportDTO uploadTrailReportDto = null;
						
		uploadTrailReportDto = new UploadTrailReportDTO();

				uploadTrailReportDto.setId("1");
				uploadTrailReportDto.setValue("Inter SSD Transfer");
				uploadTypeList.add(uploadTrailReportDto);

				
				uploadTrailReportDto = new UploadTrailReportDTO();

				uploadTrailReportDto.setId("2");
				uploadTrailReportDto.setValue("Pending List Transfer");
				uploadTypeList.add(uploadTrailReportDto);
				return uploadTypeList;
	}

	@Override
	public UploadTrailReportsOutputDTO exportToExcel(
			UploadTrailReportsParameterDTO trailReportParameterDTO) throws DAOException {
		// TODO Auto-generated method stub
		UploadTrailReportsOutputDTO trailReportDTO = new UploadTrailReportsOutputDTO();
		String uploadTypeValue = trailReportParameterDTO.getUploadType();
		Connection connection = null;
		ResultSet rs = null;
		PreparedStatement pstmt = null;
		String uploadBy= " \"UPLOADED BY\" ";
		String frmDistributerOlmId = "\" FROM DISTRIBUTOR OLM ID\"";
		String toDistributorOLMID = "\" TO DISTRIBUTOR OLM ID\"";
		String chngProductType = "\"PRODUCT NAME\"";
		String chngProductSN = "\"SERIAL NO\"";
		
		String defProductType = "\"DEFECTIVE PRODUCT NAME\"";
		String defProductSN = "\"DEFECTIVE SERIAL NO\"";

		
		String fromDate = "'"+trailReportParameterDTO.getFromDate()+"'";
		String toDate = "'"+trailReportParameterDTO.getToDate()+"'";
		
		String query = "";
		
		
		if(uploadTypeValue.equalsIgnoreCase("1"))
		{
			 query = "select D1.DISTRIBUTOR_OLM_ID AS "+frmDistributerOlmId+",D2.DISTRIBUTOR_OLM_ID AS "+toDistributorOLMID+""+
				",(SELECT PRODUCT_NAME FROM DP_PRODUCT_MASTER WHERE PRODUCT_ID = UT.CHANGE_PRODUCT_ID) AS "+chngProductType
				+",UT.CHANGE_SERIAL_NO AS "+chngProductSN+" ,UPLOADED_ON,VR.LOGIN_NAME AS "+uploadBy+" ,UT.UPLOADED_STATUS"+    
				" from DP_UPLOAD_TRAIL_STOCK_TRANSFER UT,DP_DIST_DETAILS D1,DP_DIST_DETAILS D2,VR_LOGIN_MASTER VR"+
				" where UT.FROM_DIST_ID=d1.DISTRIBUTOR_ID and UT.TO_DIST_ID=d2.DISTRIBUTOR_ID and UT.UPLOADED_BY = VR.LOGIN_ID"
				+" and UT.DEF_PRODUCT_ID IS NULL and CAST( UPLOADED_ON AS date) BETWEEN "+fromDate+" and "+toDate;
			
		}
		else if(uploadTypeValue.equalsIgnoreCase("2"))
		{
			query = "select D1.DISTRIBUTOR_OLM_ID AS "+frmDistributerOlmId+",D2.DISTRIBUTOR_OLM_ID AS "+toDistributorOLMID+""+
			",(SELECT PRODUCT_NAME FROM DP_PRODUCT_MASTER WHERE PRODUCT_ID = UT.DEF_PRODUCT_ID) AS "+defProductType+
			",UT.DEF_SERIAL_NO AS "+defProductSN+",(SELECT PRODUCT_NAME FROM DP_PRODUCT_MASTER WHERE PRODUCT_ID = UT.CHANGE_PRODUCT_ID) AS "+chngProductType
			+",UT.CHANGE_SERIAL_NO AS "+chngProductSN+" ,UPLOADED_ON,VR.LOGIN_NAME AS "+uploadBy+" ,UT.UPLOADED_STATUS"+    
			" from DP_UPLOAD_TRAIL_STOCK_TRANSFER UT,DP_DIST_DETAILS D1,DP_DIST_DETAILS D2,VR_LOGIN_MASTER VR"+
			" where UT.FROM_DIST_ID=d1.DISTRIBUTOR_ID and UT.TO_DIST_ID=d2.DISTRIBUTOR_ID and UT.UPLOADED_BY = VR.LOGIN_ID"+
			" and UT.DEF_PRODUCT_ID IS NOT NULL  and CAST( UPLOADED_ON AS date) BETWEEN "+fromDate+" and "+toDate;
			
		
		}
		
		
		try{
			connection = DBConnectionManager.getDBConnection();

			pstmt = connection.prepareStatement(query);
			rs = pstmt.executeQuery();			
			trailReportDTO = getRegetResultSet(trailReportDTO, rs);
		
		}	
		catch (Exception e)
		{
			logger.error("Exception occured::" + e.getMessage());
			logger.error(e.getMessage(), e);
			logger.error(e.getLocalizedMessage());
			throw new DAOException(e.getMessage());
		}
		finally
		{
			DBConnectionManager.releaseResources(null, rs);
			DBConnectionManager.releaseResources(connection);
		}	
		
		return trailReportDTO;
	}
	


	@Override
	public UploadTrailReportsOutputDTO getRegetResultSet(
			UploadTrailReportsOutputDTO trailReportDTO , ResultSet rs) throws DAOException {
		// TODO Auto-generated method stub
		try
		{
			if (rs == null)
			{
				throw new DAOException(
						"Exception Occured!! ResultSet is NULL!!! See below for SQLException message!");
			}

				ResultSetMetaData md = rs.getMetaData();
				int count = md.getColumnCount();
				List<String> outputHeaderList = new ArrayList<String>();
				outputHeaderList.add("S. NO.");
				List<String[]> outputList = new ArrayList<String[]>();
				for (int i = 1; i <= count; i++)
				{
					String columnHeader = md.getColumnLabel(i);
					outputHeaderList.add(columnHeader.replaceAll("_", " "));
				}
				
				trailReportDTO.setHeaders(outputHeaderList);
				while (rs.next())
				{
					String[] arr = new String[count];
					for (int i = 1; i <= count; i++)
					{
						arr[i - 1] = Utility.replaceNullBySpace(rs.getString(i));
					}
					outputList.add(arr);
				}
				trailReportDTO.setOutput(outputList);

		}
		catch (SQLException sqe)
		{
			logger.error("SQL exception occured::" + sqe.getMessage());
			logger.error(sqe.getMessage(), sqe);
			throw new DAOException(sqe.getMessage());
		}
		catch (Exception e)
		{
			logger.error("Exception occured::" + e.getMessage());
			logger.error(e.getMessage(), e);
			throw new DAOException(e.getMessage());
		}
		
		return trailReportDTO;
	}
	
	
	


}
