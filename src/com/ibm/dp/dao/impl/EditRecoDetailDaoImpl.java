package com.ibm.dp.dao.impl;

import java.io.InputStream;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.apache.log4j.Logger;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;

import com.ibm.virtualization.recharge.common.ResourceReader;
import com.ibm.virtualization.recharge.dao.rdbms.BaseDaoRdbms;
import com.ibm.virtualization.recharge.db.DBConnectionManager;
import com.ibm.virtualization.recharge.exception.DAOException;
import com.ibm.dp.common.DBQueries;
import com.ibm.dp.dao.EditRecoDetailDao;
import com.ibm.dp.dto.NSBulkUploadDto;
import com.ibm.dp.dto.RecoPeriodDTO;
import com.ibm.dp.dto.WHdistmappbulkDto;
import com.ibm.dp.exception.DPServiceException;
import com.ibm.dp.service.impl.NSBulkUploadServiceImpl;

public class EditRecoDetailDaoImpl extends BaseDaoRdbms implements
		EditRecoDetailDao {
		public static Logger logger = Logger.getLogger(AccountDetailReportDaoImpl.class.getName());

		private HSSFRow getRecoPeriodList;
		public EditRecoDetailDaoImpl(Connection connection) {
		super(connection);
		}
	public static final String SQL_INSERT_RECO_DIST_HDR_AUDIT_TRAIL = DBQueries.SQL_INSERT_RECO_DIST_HDR_AUDIT_TRAIL;
	
	public static final String SQL_UPDATE_RECO_DIST_HDR = DBQueries.SQL_UPDATE_RECO_DIST_HDR;
	
	public static final String SQL_INSERT_RECO_DIST_DETILS_AUDIT_TRAIL = DBQueries.SQL_INSERT_RECO_DIST_DETILS_AUDIT_TRAIL;
	
	public static final String SQL_UPDATE_RECO_DIST_DETILS = DBQueries.SQL_UPDATE_RECO_DIST_DETILS;
	
	public static final String SQL_INSERT_RECO_CERTIFICATE_DETAIL_AUDIT_TRAIL = DBQueries.SQL_UPDATE_RECO_DIST_DETILS;
	
	public static final String SQL_INSERT_RECO_CERTIFICATE_HDR_AUDIT_TRAIL=DBQueries.SQL_INSERT_RECO_CERTIFICATE_HDR_AUDIT_TRAIL;
	
	public static final String SQL_DELETE_RECO_CERTIFICATE_DETAIL_AUDIT_TRAIL=DBQueries.SQL_DELETE_RECO_CERTIFICATE_DETAIL_AUDIT_TRAIL;
	
	public static final String SQL_DELETE_RECO_CERTIFICATE_HDR_AUDIT=DBQueries.SQL_DELETE_RECO_CERTIFICATE_HDR_AUDIT;
	
	
	public List<RecoPeriodDTO> getRecoPeriodList() throws DAOException {
		List<RecoPeriodDTO> listReturn = new ArrayList<RecoPeriodDTO>();
		PreparedStatement pstmt = null;
		ResultSet rsetReport = null;
		logger.info("get Reco Period List Dao Impl Start......!");
		try {
			pstmt = connection
					.prepareStatement("select  ID, char(FROM_DATE) ||' - '|| char(TO_DATE) AS RECO_PERIOD from DP_RECO_PERIOD where IS_RECO_CLOSED = 0 with ur");
			RecoPeriodDTO recoPeriodDTO = null;
			rsetReport = pstmt.executeQuery();
			while (rsetReport.next()) 
			{
				recoPeriodDTO = new RecoPeriodDTO();
				recoPeriodDTO.setRecoPeriodId(rsetReport.getInt("ID") + "");
				recoPeriodDTO.setRecoPeriodName(rsetReport.getString("RECO_PERIOD"));
				listReturn.add(recoPeriodDTO);
				logger.info(recoPeriodDTO.getRecoPeriodId());
				logger.info(recoPeriodDTO.getRecoPeriod());

			}
			logger.info("get Reco Period Dao Impl ENDS......!"
					+ listReturn.size());

		} catch (Exception e) {
			e.printStackTrace();
			throw new DAOException(e.getMessage());
		} finally {
			DBConnectionManager.releaseResources(pstmt, rsetReport);
		}

		return listReturn;
	}

	public List validateExcel(InputStream inputstream)
			throws DPServiceException {
		List list = new ArrayList();
		RecoPeriodDTO recoperioderrdto = null;

		String distOlmId = "";
		String remarks = "";
		ResultSet rst = null;
		boolean validateFlag = true;
		try 
		{
			HSSFWorkbook workbook = new HSSFWorkbook(inputstream);
			HSSFSheet sheet = workbook.getSheetAt(0);
			Iterator rows = sheet.rowIterator();
			int totalrows = sheet.getLastRowNum() + 1;
			logger.info("Total Rows == " + sheet.getLastRowNum());
			if (totalrows > Integer.parseInt(ResourceReader.getValueFromBundle(
									com.ibm.virtualization.recharge.common.Constants.BULK_UPLAOD_MAX_LIMIT,
									com.ibm.virtualization.recharge.common.Constants.WEBSERVICE_RESOURCE_BUNDLE))) 
			{
				recoperioderrdto = new RecoPeriodDTO();
				recoperioderrdto.setErr_msg("Limit exceeds: Maximum "+ Integer.parseInt(ResourceReader
												.getValueFromBundle(com.ibm.virtualization.recharge.common.Constants.BULK_UPLAOD_MAX_LIMIT,
														com.ibm.virtualization.recharge.common.Constants.WEBSERVICE_RESOURCE_BUNDLE))
								+ " OLM Ids are allowed in a file.");
				list.add(recoperioderrdto);
				return list;
			}
			int rowNumber = 1;
			RecoPeriodDTO recoPeriodDTO = null;

			ArrayList checkDuplicate = new ArrayList();
			int kk = 1;
			while (rows.hasNext()) 
			{
				recoPeriodDTO = new RecoPeriodDTO();
				HSSFRow row = (HSSFRow) rows.next();
				Iterator cells = row.cellIterator();
				logger.info("rowNumber== " + rowNumber);
				if (rowNumber > 1) 
				{
					int columnIndex = 0;
					int cellNo = 0;
					if (cells != null) 
					{
						distOlmId = "";
						remarks = "";
						while (cells.hasNext()) 
						{
							if (cellNo < 2) 
							{
								cellNo++;
								HSSFCell cell = (HSSFCell) cells.next();
								columnIndex = cell.getColumnIndex();
								logger.info("columnIndex*****=== "+ columnIndex);
								if (columnIndex > 1) 
								{
									recoperioderrdto = new RecoPeriodDTO();
									recoperioderrdto.setErr_msg("File should contain only two data column");
									list.add(recoperioderrdto);
									return list;
								}
								String cellValue = null;
								switch (cell.getCellType()) 
								{
									case HSSFCell.CELL_TYPE_NUMERIC:
										cellValue = String.valueOf((long) cell.getNumericCellValue());
										// System.out.println(" value ::
										// "+cellValue);
										recoPeriodDTO.setDistOlmId(cellValue);
										break;
									case HSSFCell.CELL_TYPE_STRING:
										cellValue = cell.getStringCellValue();
										recoPeriodDTO.setRemarks(cellValue);
										// System.out.println(" value ::
										// "+cellValue);
										break;
								}

								logger.info("Row Number == " + rowNumber
										+ " and Cell No. === " + cellNo
										+ " and value == " + cellValue);
								if (cellNo == 1) 
								{
									logger.info("Row Number >> :: " + cellNo);
									boolean validate = validateOlmId(cellValue);
									if (!validate) 
									{
										validateFlag = false;
										recoperioderrdto = new RecoPeriodDTO();
										recoperioderrdto.setErr_msg("Invalid Distributor OLM Id at row No: "+ (rowNumber)
														+ ". It should be 8 character.");
										list.add(recoperioderrdto);

									} 
									else 
									{
										if (validateOlmdistId(cellValue)) 
										{
											distOlmId = cellValue;
											// distProdComb = cellValue;
											recoPeriodDTO
													.setDistOlmId(cellValue);
										} 
										else 
										{
											validateFlag = false;
											recoperioderrdto = new RecoPeriodDTO();
											recoperioderrdto.setErr_msg("Invalid Distributor OLM Id at row No:  "
															+ (rowNumber));
											list.add(recoperioderrdto);
										}
									}
								} 
								else if (cellNo == 2) 
								{

								}

							}
							else 
							{
								validateFlag = false;
								recoperioderrdto = new RecoPeriodDTO();
								recoperioderrdto.setErr_msg("File should contain only two data column at row No: "
												+ (rowNumber) + ".");
								list.add(recoperioderrdto);
								return list;
							}
						}
						if (cellNo != 2) 
						{
							validateFlag = false;
							recoperioderrdto = new RecoPeriodDTO();
							recoperioderrdto
									.setErr_msg("File should contain two data Headers");
							List list2 = new ArrayList();
							list2.add(recoperioderrdto);
							return list2;
						}

						logger.info("distOlmId****==== " + distOlmId);
						if (distOlmId != null && !distOlmId.equalsIgnoreCase("null")
								&& !distOlmId.equalsIgnoreCase("")) 
						{
							if (checkDuplicate.contains(distOlmId)) 
							{
								validateFlag = false;
								recoperioderrdto = new RecoPeriodDTO();
								recoperioderrdto.setErr_msg("File contains duplicate Entries For Distributor : "
												+ distOlmId.split("#")[0]);
								list.add(recoperioderrdto);

							} 
							else 
							{
								// validateFlag =true;
								checkDuplicate.add(distOlmId);
							}
						}
						logger.info("In Row num == " + (rowNumber - 1)+ " Error Message ==  ");
						if (validateFlag) 
						{
							recoPeriodDTO.setErr_msg("SUCCESS");
							list.add(recoPeriodDTO);
						}

					}
				} 
				else 
				{
					// For Header Checking
					int columnIndex = 0;
					int cellNo = 0;
					if (cells != null) {
						while (cells.hasNext()) {
							if (cellNo < 2) {
								cellNo++;
								HSSFCell cell = (HSSFCell) cells.next();

								columnIndex = cell.getColumnIndex();
								if (columnIndex > 1) {
									validateFlag = false;
									recoperioderrdto = new RecoPeriodDTO();
									recoperioderrdto
											.setErr_msg("File should contain only two data column");
									List list2 = new ArrayList();
									list2.add(recoperioderrdto);
									return list2;
								}
								String cellValue = null;

								switch (cell.getCellType()) {
								case HSSFCell.CELL_TYPE_NUMERIC:
									cellValue = String.valueOf((long) cell
											.getNumericCellValue());
									break;
								case HSSFCell.CELL_TYPE_STRING:
									cellValue = cell.getStringCellValue();
									break;
								}
								if (cellValue == null
										|| ""
												.equalsIgnoreCase(cellValue
														.trim())) {
									validateFlag = false;
									recoperioderrdto = new RecoPeriodDTO();
									recoperioderrdto
											.setErr_msg("File should contain All Required Values");
									List list2 = new ArrayList();
									list2.add(recoperioderrdto);
									return list2;
								}

								logger.info("Row Number == " + rowNumber
										+ " and Cell No. === " + cellNo
										+ " and value == "
										+ cellValue.trim().toLowerCase());
								if (cellNo == 1) {
									if (!"distributor olm id".equals(cellValue
											.trim().toLowerCase())) {
										validateFlag = false;
										recoperioderrdto = new RecoPeriodDTO();
										recoperioderrdto
												.setErr_msg("Invalid Header For Distributor OLM ID");
										list.add(recoperioderrdto);

									}
								} else if (cellNo == 2) {
									if (!"remarks".equals(cellValue.trim()
											.toLowerCase())) {
										validateFlag = false;
										recoperioderrdto = new RecoPeriodDTO();
										recoperioderrdto
												.setErr_msg("Invalid Header For Remarks");
										list.add(recoperioderrdto);

									}
								}
							} else {
								validateFlag = false;
								recoperioderrdto = new RecoPeriodDTO();
								recoperioderrdto
										.setErr_msg("File should contain only two data Headers");
								List list2 = new ArrayList();
								list2.add(recoperioderrdto);
								return list2;
							}
						}
						if (cellNo != 2) {
							validateFlag = false;
							recoperioderrdto = new RecoPeriodDTO();
							recoperioderrdto
									.setErr_msg("File should contain two data Headers");
							List list2 = new ArrayList();
							list2.add(recoperioderrdto);
							return list2;
						}
					} else {
						recoperioderrdto = new RecoPeriodDTO();
						recoperioderrdto
								.setErr_msg("File should contain two data column");
						List list2 = new ArrayList();
						list2.add(recoperioderrdto);
						return list2;
					}
					rowNumber++;
				}
				kk++;

			}
			/*
			 * logger.info("K == "+kk); if(kk==1){ recoPeriodDTO = new
			 * RecoPeriodDTO(); recoperioderrdto.setErr_msg("Blank File can not
			 * be uploaded!!"); list.add(recoPeriodDTO);
			 * 
			 */
			logger.info("Total number of rows kk ::  "+kk);
			logger.info("Total number of rows rowNumber ::  "+rowNumber);
			if(rowNumber<2)
			{
				recoperioderrdto = new RecoPeriodDTO();
				recoperioderrdto.setErr_msg("Blank file uploaded");
				List list2 = new ArrayList();
				list2.add(recoperioderrdto);
				return list2;
			}

		}

		catch (Exception e) {

			recoperioderrdto = new RecoPeriodDTO();
			recoperioderrdto.setErr_msg("Invalid File Uploaded.");
			List list2 = new ArrayList();
			list2.add(recoperioderrdto);
			logger.info(e);
			return list2;
		} finally {
			DBConnectionManager.releaseResources(null, rst);

		}
		return list;
	}

	public boolean validateOlmId(String str) {
		if (str.length() != 8) {
			return false;
		}

		return true;
	}

	public boolean validateRemarks(String str) {
		if (str.length() >= 1) {
			return true;
		}

		return true;
	}

	public boolean validateOlmdistId(String distOlmId) {
		PreparedStatement pstmt = null;
		PreparedStatement pstmt1 = null;
		PreparedStatement pstmt2 = null;
		ResultSet rs = null;
		int count = 0;
		boolean flag = false;
		try {

			logger.info("inside --- validateOlmId dist OLM id == " + distOlmId);
			pstmt = connection.prepareStatement("select count(*)  from VR_LOGIN_MASTER where LOGIN_NAME =  ? and GROUP_ID =7  with ur");
			pstmt.setString(1, distOlmId);
			rs = pstmt.executeQuery();
			if (rs.next()) {
				count = rs.getInt(1);
			}
			if (count >= 1)
				flag = true;
			else
				flag = false;
		} catch (Exception e) {
			e.printStackTrace();
			logger.info("Inside main Exception of validateDistOlmId method--"
					+ e.getMessage());
		}
		return flag;
	}
	public int updateRecoPeriod(List<RecoPeriodDTO> recoDtoList, int recoID, int gracePeriod) throws DAOException 
	{
		PreparedStatement pstmtDist = null;
		PreparedStatement pstmt1 = null;
		PreparedStatement pstmt2 = null;
		PreparedStatement pstmt3 = null;
		PreparedStatement pstmt4 = null;

		PreparedStatement pstmt5 = null;
		PreparedStatement pstmt6 = null;
		PreparedStatement pstmt7 = null;
		PreparedStatement pstmt8 = null;
		ResultSet rsetReport = null;
		ResultSet rset = null;
		int updateStatus = 0;
		String distOLMID="";
		int distID = 0;
		logger.info(" ............when submit button is clicked the update starts in DB......!");
		
		RecoPeriodDTO recoPeriodDTO = new RecoPeriodDTO();
		try
		{
			pstmtDist = connection.prepareStatement("SELECT LOGIN_ID FROM VR_LOGIN_MASTER WHERE LOGIN_name=? with ur");
			pstmt1 = connection.prepareStatement(SQL_INSERT_RECO_DIST_HDR_AUDIT_TRAIL);
			pstmt2 = connection.prepareStatement(SQL_UPDATE_RECO_DIST_HDR);
			pstmt3 = connection.prepareStatement(SQL_INSERT_RECO_DIST_DETILS_AUDIT_TRAIL);
			pstmt4 = connection.prepareStatement(SQL_UPDATE_RECO_DIST_DETILS);
			pstmt5 = connection.prepareStatement(SQL_INSERT_RECO_CERTIFICATE_DETAIL_AUDIT_TRAIL);
			pstmt6 = connection.prepareStatement(SQL_INSERT_RECO_CERTIFICATE_HDR_AUDIT_TRAIL);
			pstmt7 = connection.prepareStatement(SQL_DELETE_RECO_CERTIFICATE_DETAIL_AUDIT_TRAIL);
			pstmt8 = connection.prepareStatement(SQL_DELETE_RECO_CERTIFICATE_HDR_AUDIT);
			
			Iterator itr = recoDtoList.iterator();
			
			while (itr.hasNext()) 
			{
				recoPeriodDTO = (RecoPeriodDTO) itr.next();
				distOLMID = recoPeriodDTO.getDistOlmId();
				//String distId = recoPeriodDTO.getDistId();
				logger.info("Reco ID  ::  "+recoID);
				logger.info("OLM ID  ::  "+distOLMID);
				
				distID = 0;
				
				pstmtDist.setString(1, distOLMID);
				rsetReport = pstmtDist.executeQuery();
				if(rsetReport.next())
				{
					distID = rsetReport.getInt("LOGIN_ID");
				}
				logger.info("DIST ID  ::  "+distID);
				
				logger.info("INSERT_RECO_DIST_DETILS_AUDIT_TRAIL");
				pstmt3.setInt(1, recoID);
				pstmt3.setInt(2, distID);
				pstmt3.executeUpdate();
				
				logger.info("INSERT_RECO_DIST_HDR_AUDIT_TRAIL ");
				pstmt1.setInt(1, recoID);
				pstmt1.setInt(2, distID);
				pstmt1.executeUpdate();
				
				logger.info("UPDATE_RECO_DIST_DETILS");
				pstmt4.setInt(1, recoID);
				pstmt4.setInt(2, distID);
				pstmt4.executeUpdate();
				
				logger.info("UPDATE_RECO_DIST_HDR ");
				pstmt2.setInt(1, gracePeriod);
				pstmt2.setInt(2, recoID);
				pstmt2.setInt(3, distID);
				pstmt2.executeUpdate();
				
				logger.info("INSERT_RECO_CERTIFICATE_DETAIL_AUDIT_TRAIL ");
				pstmt5.setInt(1, recoID);
				pstmt5.setInt(2, distID);
				pstmt5.executeUpdate();
				
				logger.info("INSERT_RECO_CERTIFICATE_HDR_AUDIT_TRAIL");
				pstmt6.setInt(1, recoID);
				pstmt6.setInt(2, distID);
				pstmt6.executeUpdate();
				
				logger.info("DELETE_RECO_CERTIFICATE_DETAIL_AUDIT_TRAIL");
				pstmt7.setInt(1, recoID);
				pstmt7.setInt(2, distID);
				pstmt7.executeUpdate();
				
				logger.info("DELETE_RECO_CERTIFICATE_HDR_AUDIT ");
				pstmt8.setInt(1, recoID);
				pstmt8.setInt(2, distID);
				pstmt8.executeUpdate();
			}
		} 
		catch (Exception e) 
		{
			e.printStackTrace();
			logger.error("Exception while updating Distributor Reco info  ::  "+e.getMessage());
			logger.error("Exception while updating Distributor Reco info  ::  "+e);
			throw new DAOException(e.getMessage());
		} 
		finally 
		{
			DBConnectionManager.releaseResources(pstmtDist, rsetReport);
			DBConnectionManager.releaseResources(pstmt1, rset);
			DBConnectionManager.releaseResources(pstmt2, rset);
			DBConnectionManager.releaseResources(pstmt3, rset);
			DBConnectionManager.releaseResources(pstmt4, rset);
			DBConnectionManager.releaseResources(pstmt5, rset);
			DBConnectionManager.releaseResources(pstmt6, rset);
			DBConnectionManager.releaseResources(pstmt7, rset);
			DBConnectionManager.releaseResources(pstmt8, rset);
		}


		return 1;
	}


}
