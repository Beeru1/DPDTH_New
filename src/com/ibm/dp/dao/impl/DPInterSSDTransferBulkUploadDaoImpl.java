package com.ibm.dp.dao.impl;

import java.io.InputStream;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;
import java.util.Map.Entry;

import org.apache.commons.collections.MapIterator;
import org.apache.commons.collections.keyvalue.MultiKey;
import org.apache.commons.collections.map.MultiKeyMap;
import org.apache.log4j.Logger;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;

import com.ibm.dp.common.DBQueries;
import com.ibm.dp.dao.DPInterSSDTransferBulkUploadDao;
import com.ibm.dp.dao.DPPendingListTransferBulkUploadDao;
import com.ibm.dp.dto.DPInterSSDTransferBulkUploadDto;
import com.ibm.dp.dto.DPPendingListTransferBulkUploadDto;
import com.ibm.dp.exception.DPServiceException;
import com.ibm.utilities.Utility;
import com.ibm.utilities.ValidatorUtility;
import com.ibm.virtualization.recharge.common.Constants;
import com.ibm.virtualization.recharge.common.ResourceReader;
import com.ibm.virtualization.recharge.dao.rdbms.BaseDaoRdbms;
import com.ibm.virtualization.recharge.db.DBConnectionManager;
import com.ibm.virtualization.recharge.exception.DAOException;

public class DPInterSSDTransferBulkUploadDaoImpl extends BaseDaoRdbms
		implements DPInterSSDTransferBulkUploadDao {

	public static Logger logger = Logger
			.getLogger(DPInterSSDTransferBulkUploadDaoImpl.class.getName());

	public static final String SQL_SELECT_ST_DISTID = DBQueries.SQL_SELECT_ST_DISTID;
	public static final String SQL_SELECT_OLMID = DBQueries.SQL_SELECT_OLMID;
	public static final String SQL_SELECT_SERIAL_NO = DBQueries.SQL_SELECT_SERIAL_NO;
	public static final String SQL_SELECT_OLMID_INTER_SSD_BULK = DBQueries.SQL_SELECT_OLMID_INTER_SSD_BULK;
	protected static final String SQL_UPDATE_INTER_SSD_STOCK_TRANSFER = DBQueries.SQL_UPDATE_INTER_SSD_STOCK_TRANSFER;
	protected static final String SQL_SELECT_UPLOAD_INTER_SSD_TRAIL_TRANSFER = DBQueries.SQL_SELECT_UPLOAD_INTER_SSD_TRAIL_TRANSFER;
	protected static final String SQL_INSERT_UPLOAD_INTER_SSD_TRAIL_TRANSFER = DBQueries.SQL_INSERT_UPLOAD_INTER_SSD_TRAIL_TRANSFER;
	protected static final String SQL_UPDATE_UPLOAD_INTER_SSD_TRAIL_TRANSFER = DBQueries.SQL_UPDATE_UPLOAD_INTER_SSD_TRAIL_TRANSFER;	
	protected static final String SQL_SELECT_DP_DDST_SERIAL_NO = DBQueries.SQL_SELECT_DP_DDST_SERIAL_NO;
	protected static final String SQL_UPDATE_DIST_STOCK_TRANSFER = DBQueries.SQL_UPDATE_DIST_STOCK_TRANSFER;
	
	protected static final String SQL_SELECT_DP_DIST = DBQueries.SQL_SELECT_DP_DIST;
	protected static final String SQL_UPDATE_DP_DIST_UPDATE = DBQueries.SQL_UPDATE_DP_DIST_UPDATE;
	protected static final String SQL_SELECT_DP_DDST = DBQueries.SQL_SELECT_DP_DDST;
	protected static final String SQL_UPDATE_DP_DDST = DBQueries.SQL_UPDATE_DP_DDST;
	
	public static final String SQL_SELECT_DCNO  = "SELECT 'TRNSDC'||CHAR(SEQ_STOCK_TRANSFER_DC.NEXTVAL) FROM SYSIBM.SYSDUMMY1";
	public static final String SQL_SELECT_REQID  = "SELECT CHAR(SEQ_DP_DIST_STOCK_TRANSFER.NEXTVAL) FROM SYSIBM.SYSDUMMY1";
	public static final String SQL_INSERT_DDST_BULK_SERIAL_NO	= "INSERT INTO DP_DDST_SERIAL_NO(REQ_ID, DC_NO, SERIAL_NO, ACTION, PRODUCT_ID)" +
																"   VALUES(?, ?, ?, ?,?)";
	public static final String SQL_INSERT_BULK_STOCK_TRANSFER 	= "INSERT into DP_DIST_STOCK_TRANSFER(REQ_ID, FROM_DIST_ID, TO_DIST_ID," +
															 " PRODUCT_ID, REQ_TRANSFER_QTY, STATUS,DC_NO, CREATED_BY, CREATED_DATE,DC_DATE,TRANSFERRED_QTY,UPDATED_BY,UPDATED_DATE)" +
															 " values(?,?,?,?,?,?,?,?,current timestamp,current date,?,?,current timestamp)";

	private static final String SQL_SELECT_RECORD_FROM_TABLE = "select COUNT(*) AS ROWCOUNT from DP_DDST_SERIAL_NO where REQ_ID in (select REQ_ID from DP_DIST_STOCK_TRANSFER " +
																"where FROM_DIST_ID=? and TO_DIST_ID=? and PRODUCT_ID =? ) and SERIAL_NO = ?";
	private static final String SQL_SELECT_CHECK_STAUTS_FROM_TABLE = "select COUNT(*) AS ROWCOUNT from  DP_DIST_STOCK_TRANSFER where FROM_DIST_ID=? and TO_DIST_ID=? and PRODUCT_ID =? and STATUS = 'INITIATED' ";

	private static final String SQL_SELECT_CHECK_AGEING = "select count(*) as ROWCOUNT from DP_STOCK_INVENTORY where DISTRIBUTOR_ID= ? and SERIAL_NO=? " +
														" and PRODUCT_ID=? and days(current date)- days(DISTRIBUTOR_PURCHASE_DATE) > 210 ";
	
	private static final String SQL_SELECT_CHECK_FROMDISTID = "select count(*) as ROWCOUNT from DP_STOCK_INVENTORY where DISTRIBUTOR_ID= ? and SERIAL_NO=? ";

	

	public DPInterSSDTransferBulkUploadDaoImpl(Connection connection) {
		super(connection);
	}

	public List uploadExcel(InputStream inputstream) throws DAOException {
		List list = new ArrayList();
		DPInterSSDTransferBulkUploadDto interSSDTransferBulkUploadErrDto = null;

		String fromDistOlmId = "";
		String fromDistId = "";
		String toDistOlmId= "";	
		String toDistId= "";			
		String productName= "";
		String productId= "";
		String serialNo= "";
		
		
		
		
		ResultSet rst = null;
		ResultSet rst1 = null;
		ResultSet rst2 = null;
		boolean validateFlag = true;
		List list3 = new ArrayList();

		DPInterSSDTransferBulkUploadDto interSSDTransferBulkUploadDto = null;

		try {

			HSSFWorkbook workbook = new HSSFWorkbook(inputstream);
			HSSFSheet sheet = workbook.getSheetAt(0);
			Iterator rows = sheet.rowIterator();
			int totalrows = sheet.getLastRowNum() + 1;
			logger.info("Total Rows == " + sheet.getLastRowNum());
			if (totalrows > Integer
					.parseInt(ResourceReader
							.getValueFromBundle(
									com.ibm.virtualization.recharge.common.Constants.BULK_UPLAOD_MAX_LIMIT,
									com.ibm.virtualization.recharge.common.Constants.WEBSERVICE_RESOURCE_BUNDLE))) {
				interSSDTransferBulkUploadErrDto = new DPInterSSDTransferBulkUploadDto();
				interSSDTransferBulkUploadErrDto
						.setErr_msg("Limit exceeds: Maximum "
								+ Integer
										.parseInt(ResourceReader
												.getValueFromBundle(
														com.ibm.virtualization.recharge.common.Constants.BULK_UPLAOD_MAX_LIMIT,
														com.ibm.virtualization.recharge.common.Constants.WEBSERVICE_RESOURCE_BUNDLE))
								+ " Serial Numbers are allowed in a file.");
				list.add(interSSDTransferBulkUploadErrDto);
				return list;
			}

			int rowNumber = 1;
			
			ArrayList checkDuplicate = new ArrayList();
			int k = 1;

			while (rows.hasNext()) {
				interSSDTransferBulkUploadDto = new DPInterSSDTransferBulkUploadDto();
				HSSFRow row = (HSSFRow) rows.next();
				Iterator cells = row.cellIterator();

				System.out.println("************rowNumber" + rowNumber);
				System.out.println("************cells" + cells.toString());

				if (rowNumber > 1) {

					int columnIndex = 0;
					int cellNo = 0;
					if (cells != null) {
						 fromDistOlmId = "";
						 toDistOlmId = "";
						 productName = "";
						 serialNo = "";
						 fromDistId = "";
						 toDistId  = "";		
						 productId = "";
						 serialNo = "" ;
						while (cells.hasNext()) {
							if (cellNo < 4) {
								cellNo++;
								HSSFCell cell = (HSSFCell) cells.next();

								columnIndex = cell.getColumnIndex();
								if (columnIndex > 3) {
									validateFlag = false;
									interSSDTransferBulkUploadErrDto = new DPInterSSDTransferBulkUploadDto();
									interSSDTransferBulkUploadErrDto
											.setErr_msg("File should contain only four data column");
									list
											.add(interSSDTransferBulkUploadErrDto);
									return list;
								}
								String cellValue = null;
								
								switch(cell.getCellType()) {
			            		case HSSFCell.CELL_TYPE_NUMERIC:
			            			cellValue = String.valueOf((double)cell.getNumericCellValue());			            			
			            		break;
			            		case HSSFCell.CELL_TYPE_STRING:
			            			cellValue = cell.getStringCellValue();
				            	break;
								}
								
								System.out.println(" Cell value------->"+cellValue.trim());

								
								logger.info("Row Number == " + rowNumber
										+ " and Cell No. === " + cellNo
										+ " and value == " + cellValue);
								if (cellNo == 1) {
									boolean validate = validateOlmId(cellValue
											.trim());
									System.out.println("validate" + validate);
									
									if (!validate) {
										validateFlag = false;
										interSSDTransferBulkUploadErrDto = new DPInterSSDTransferBulkUploadDto();
										interSSDTransferBulkUploadErrDto
												.setErr_msg("Invalid OLM Id at row No: "
														+ (rowNumber)
														+ ".It should be 8 character.");
										list
												.add(interSSDTransferBulkUploadErrDto);

									} else if (validateOlmDistId(cellValue.trim())) {
										
										fromDistOlmId = cellValue.trim();
										interSSDTransferBulkUploadDto
												.setFromDistOlmId(cellValue.trim());
										
									} else {
										interSSDTransferBulkUploadErrDto = new DPInterSSDTransferBulkUploadDto();
										interSSDTransferBulkUploadErrDto
												.setErr_msg("Invalid Distributed OLM Id at row No : "
														+ (rowNumber) + ".");
										list
												.add(interSSDTransferBulkUploadErrDto);

									}
								} else if (cellNo == 2) {
									boolean validate = validateOlmId(cellValue
											.trim());
									System.out.println("validate" + validate);
									if (!validate) {
										validateFlag = false;
										interSSDTransferBulkUploadErrDto = new DPInterSSDTransferBulkUploadDto();
										interSSDTransferBulkUploadErrDto
												.setErr_msg("Invalid OLM Id at row No: "
														+ (rowNumber)
														+ ".It should be 8 character.");
										list
												.add(interSSDTransferBulkUploadErrDto);

									} else if (validateOlmDistId(cellValue.trim())) {
										toDistOlmId = cellValue.trim();
										interSSDTransferBulkUploadDto
												.setToDistOlmId(cellValue.trim());	
										rst = validateToDistOlmId(toDistOlmId);
				            			if(rst.next()){
				            				System.out.println("Sanjay&&&&&&&&&&&&&&&&&&&&&&&");
				            				toDistId = rst.getString("LOGIN_ID");        				
			                  					                  				
			                  				System.out.println("&&&&&&&&&&&&&&&&&&&&&&&&& To distId &&&&&&&&&"+toDistId);			                  							                  				
			                  				interSSDTransferBulkUploadDto.setToDistId(toDistId);               				                					
				            				
				            			}
				            			else {
											interSSDTransferBulkUploadErrDto = new DPInterSSDTransferBulkUploadDto();
											interSSDTransferBulkUploadErrDto
													.setErr_msg("Inactive Distributed OLM Id at row No : "
															+ (rowNumber) + ".");
											list
													.add(interSSDTransferBulkUploadErrDto);

										}
																				
									} else {
										interSSDTransferBulkUploadErrDto = new DPInterSSDTransferBulkUploadDto();
										interSSDTransferBulkUploadErrDto
												.setErr_msg("Invalid or Inactive Distributed OLM Id at row No : "
														+ (rowNumber) + ".");
										list
												.add(interSSDTransferBulkUploadErrDto);

									}
								} else if (cellNo == 3) {

									//interSSDTransferBulkUploadDto = new DPInterSSDTransferBulkUploadDto();
									//interSSDTransferBulkUploadDto.setProductNameDef(cellValue.trim());
									if(validateProductId(cellValue.trim())){
				            			
				            			rst = validateDistOlmId(cellValue,fromDistOlmId);
				            				if(rst.next()){
				            					System.out.println("Sanjay&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&");
				            					fromDistId = rst.getString("ACCOUNT_ID");
				            					productId = rst.getString("PRODUCT_ID");
				            					System.out.println("&&&&&&&&&&&&&&&&&&&&&&&&&distId&&&&&&&&&"+fromDistId+"productId"+productId);
				            					productName=cellValue.trim();					            			
				            					interSSDTransferBulkUploadDto.setProductName(cellValue.trim());			                  				
				            					interSSDTransferBulkUploadDto.setFromDistId(fromDistId);
				            					interSSDTransferBulkUploadDto.setProductId(productId);	                  					
				            				
				            				}else{
				            					validateFlag =false;
				            					interSSDTransferBulkUploadErrDto = new DPInterSSDTransferBulkUploadDto();
				            					interSSDTransferBulkUploadErrDto.setErr_msg("Product Name does not belong to the Circle of Distributor at row No: "+(rowNumber)+".");;
			                        			list.add(interSSDTransferBulkUploadErrDto);
			                  				
				            				}
				            			}else{
				            				validateFlag =false;
				            				interSSDTransferBulkUploadErrDto = new DPInterSSDTransferBulkUploadDto();
				            				interSSDTransferBulkUploadErrDto.setErr_msg("Invalid Product Name at row no.: "+(rowNumber));
		                      				list.add(interSSDTransferBulkUploadErrDto);
				            				
				            			}
									
								} else if (cellNo == 4) {					
									
										if (validateSerialNumber(cellValue.trim()))
										{
										   interSSDTransferBulkUploadDto.setSerialNo(cellValue.trim());
										}
										else {
											
											validateFlag =false;
											interSSDTransferBulkUploadErrDto = new DPInterSSDTransferBulkUploadDto();
											interSSDTransferBulkUploadErrDto
											.setErr_msg("Invalid Serial Number at row No: "
													+ (rowNumber) + ".");
											list.add(interSSDTransferBulkUploadErrDto);
										}
															 
								}
								
								
								
							}
							
							
						}

						rowNumber++;
					} else {
						interSSDTransferBulkUploadErrDto = new DPInterSSDTransferBulkUploadDto();
						interSSDTransferBulkUploadErrDto
								.setErr_msg("File should contain only five data column");
						list.add(interSSDTransferBulkUploadErrDto);
						return list;
					}
					
					logger.info("In Row num == " + (rowNumber - 1)
							+ " Error Message ==  " + validateFlag);
					if (validateFlag) {
						interSSDTransferBulkUploadDto.setErr_msg("SUCCESS");
						list.add(interSSDTransferBulkUploadDto);
					}

				} else {

					// For Header Checking
					int columnIndex = 0;
					int cellNo = 0;
					if (cells != null) {
						while (cells.hasNext()) {
							if (cellNo < 4) {
								cellNo++;
								HSSFCell cell = (HSSFCell) cells.next();

								columnIndex = cell.getColumnIndex();
								if (columnIndex > 3) {
									validateFlag = false;
									interSSDTransferBulkUploadErrDto = new DPInterSSDTransferBulkUploadDto();
									interSSDTransferBulkUploadErrDto
											.setErr_msg("File should contain only four data column");
									List list2 = new ArrayList();
									list2
											.add(interSSDTransferBulkUploadErrDto);
									return list2;
								}
								String cellValue = null;

								switch(cell.getCellType()) {
			            		case HSSFCell.CELL_TYPE_NUMERIC:
			            			cellValue = String.valueOf((long)cell.getNumericCellValue());
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
									interSSDTransferBulkUploadErrDto = new DPInterSSDTransferBulkUploadDto();
									interSSDTransferBulkUploadErrDto
											.setErr_msg("File should contain All Required Values");
									List list2 = new ArrayList();
									list2
											.add(interSSDTransferBulkUploadErrDto);
									return list2;
								}

								logger.info("Row Number == " + rowNumber
										+ " and Cell No. === " + cellNo
										+ " and value == "
										+ cellValue.trim().toLowerCase());
								if (cellNo == 1) {
									if (!"from dist olm id".equals(cellValue
											.trim().toLowerCase())) {
										validateFlag = false;
										interSSDTransferBulkUploadErrDto = new DPInterSSDTransferBulkUploadDto();
										interSSDTransferBulkUploadErrDto
												.setErr_msg("Invalid Header For From Dist OLM ID");
										list
												.add(interSSDTransferBulkUploadErrDto);

									}
								} else if (cellNo == 2) {
									if (!"to dist olm id".equals(cellValue
											.trim().toLowerCase())) {
										validateFlag = false;
										interSSDTransferBulkUploadErrDto = new DPInterSSDTransferBulkUploadDto();
										interSSDTransferBulkUploadErrDto
												.setErr_msg("Invalid Header For To Dist OLM ID");
										list
												.add(interSSDTransferBulkUploadErrDto);

									}
								} else if (cellNo == 3) {
									if (!"product name"
											.equals(cellValue.trim()
													.toLowerCase())) {
										validateFlag = false;
										interSSDTransferBulkUploadErrDto = new DPInterSSDTransferBulkUploadDto();
										interSSDTransferBulkUploadErrDto
												.setErr_msg("Invalid Header For Product Name");
										list
												.add(interSSDTransferBulkUploadErrDto);

									}
								} else if (cellNo == 4) {
									if (!"serial number"
											.equals(cellValue.trim()
													.toLowerCase())) {
										validateFlag = false;
										interSSDTransferBulkUploadErrDto = new DPInterSSDTransferBulkUploadDto();
										interSSDTransferBulkUploadErrDto
												.setErr_msg("Invalid Header For Serial Number");
										list
												.add(interSSDTransferBulkUploadErrDto);

									}
								}  
							} else {
								validateFlag = false;
								interSSDTransferBulkUploadErrDto = new DPInterSSDTransferBulkUploadDto();
								interSSDTransferBulkUploadErrDto
										.setErr_msg("File should contain only four data Headers");
								List list2 = new ArrayList();
								list2.add(interSSDTransferBulkUploadErrDto);
								return list2;
							}
						}
						if (cellNo != 4) {
							validateFlag = false;
							interSSDTransferBulkUploadErrDto = new DPInterSSDTransferBulkUploadDto();
							interSSDTransferBulkUploadErrDto
									.setErr_msg("File should contain four data Headers");
							List list2 = new ArrayList();
							list2.add(interSSDTransferBulkUploadErrDto);
							return list2;
						}
					} else {
						interSSDTransferBulkUploadErrDto = new DPInterSSDTransferBulkUploadDto();
						interSSDTransferBulkUploadErrDto
								.setErr_msg("File should contain four data column");
						List list2 = new ArrayList();
						list2.add(interSSDTransferBulkUploadErrDto);
						return list2;
					}
					rowNumber++;
				}
				k++;
			}
			rowNumber = 1;
			Iterator itr = list.iterator();
			while(itr.hasNext())
			{
				
				interSSDTransferBulkUploadDto = (DPInterSSDTransferBulkUploadDto) itr.next();
				
				if(validateDistIdandSerialNo(interSSDTransferBulkUploadDto.getFromDistId(),interSSDTransferBulkUploadDto.getSerialNo(),interSSDTransferBulkUploadDto.getProductId()))
				{
					validateFlag =false;
					interSSDTransferBulkUploadErrDto = new DPInterSSDTransferBulkUploadDto();
					interSSDTransferBulkUploadErrDto
					.setErr_msg("Serial No does not belong to the Distributor at row No: "
							+ (rowNumber) + ".");
					list3.add(interSSDTransferBulkUploadErrDto);
	
				}
				else if(validateDuplicateRecord(interSSDTransferBulkUploadDto.getFromDistId(), interSSDTransferBulkUploadDto.getToDistId(),
							interSSDTransferBulkUploadDto.getProductId(), interSSDTransferBulkUploadDto.getSerialNo()))
					{	
						validateFlag =false;
						interSSDTransferBulkUploadErrDto = new DPInterSSDTransferBulkUploadDto();
						interSSDTransferBulkUploadErrDto
						.setErr_msg("Record is already transfered at row No:"
								+ (rowNumber) + ".");
						list3.add(interSSDTransferBulkUploadErrDto);
					
					}
					else if(validateInitiated(interSSDTransferBulkUploadDto.getFromDistId(), interSSDTransferBulkUploadDto.getToDistId(),
							interSSDTransferBulkUploadDto.getProductId()))
					{
						validateFlag =false;
						interSSDTransferBulkUploadErrDto = new DPInterSSDTransferBulkUploadDto();
						interSSDTransferBulkUploadErrDto
						.setErr_msg("Record is already initiated state at row No: "
								+ (rowNumber) + ".");
						list3.add(interSSDTransferBulkUploadErrDto);
					}
					rowNumber++;
				
			}
			
					
			
			logger.info("K == " + k);
			if (k == 1) {
				interSSDTransferBulkUploadErrDto = new DPInterSSDTransferBulkUploadDto();
				interSSDTransferBulkUploadErrDto
						.setErr_msg("Blank File can not be uploaded!!");
				list.add(interSSDTransferBulkUploadErrDto);
			}

		} catch (Exception e) {

			logger.info(e);
			throw new DAOException("Please Upload a valid Excel Format File");
		} finally {
			DBConnectionManager.releaseResources(null, rst);

		}
		if(list3.size() > 0)
		{
			return list3;
		}
		else{
		return list;
		}
	}
	
	
	public String updateInterSSDListTransfer(List list, String userId)
	{
		PreparedStatement pstmt = null;
		PreparedStatement pstmttrail = null;
		PreparedStatement pstmt1 = null;
		PreparedStatement pstmt2 = null;
		PreparedStatement pstmt3 = null;
		PreparedStatement pstmt4 = null;
		PreparedStatement pstmt5 = null;
		PreparedStatement pstmt6 = null;
		PreparedStatement pstmt7 = null;
		PreparedStatement pstmt8 = null;
		PreparedStatement pstmt9 = null;
		PreparedStatement pstmt10 = null;
		
		String returnstr="";
		String duplicateValueMsg="";
		logger.info("Size--"+list.size());
		
		ResultSet rst=null;
		ResultSet rst1=null;
		ResultSet rst2=null;
		List<String> listCnt = new ArrayList<String>();		
		Map<String, Integer> map = new HashMap<String, Integer>();
		Map<String, String> mapExcelData = new HashMap<String, String>();
		Map<String, String> mapExcelData1 = new HashMap<String, String>();
		DPInterSSDTransferBulkUploadDto interSSDTransferBulkUploadDto = null;
		List<String> serailNumberList = null;
		Map<String , List <String>> serialNumberMap = new HashMap<String, List<String>>();
		Set<String> setDcNumber = new HashSet<String>();
		Map<String, String> mapDcNumber = new HashMap<String, String>();
		
		
		try
		{	
			pstmt = connection.prepareStatement(SQL_UPDATE_INTER_SSD_STOCK_TRANSFER);
			pstmt1 = connection.prepareStatement(SQL_SELECT_UPLOAD_INTER_SSD_TRAIL_TRANSFER);			
			pstmttrail = connection.prepareStatement(SQL_INSERT_UPLOAD_INTER_SSD_TRAIL_TRANSFER);
			pstmt2 = connection.prepareStatement(SQL_UPDATE_UPLOAD_INTER_SSD_TRAIL_TRANSFER);
			pstmt3 = connection.prepareStatement(SQL_SELECT_DP_DDST_SERIAL_NO);
			pstmt4 = connection.prepareStatement(SQL_UPDATE_DIST_STOCK_TRANSFER);			
			pstmt5 = connection.prepareStatement(SQL_INSERT_DDST_BULK_SERIAL_NO);
			pstmt6 = connection.prepareStatement(SQL_INSERT_BULK_STOCK_TRANSFER);
			pstmt7 = connection.prepareStatement(SQL_SELECT_DP_DIST);
			pstmt8 = connection.prepareStatement(SQL_UPDATE_DP_DIST_UPDATE);			
			pstmt9 = connection.prepareStatement(SQL_SELECT_DP_DDST);
			pstmt10 = connection.prepareStatement(SQL_UPDATE_DP_DDST);
			
			Iterator itr = list.iterator();
			int updaterows = 0;
			String toDistIdOld ="";
			int req_id = 0;
			String strDcNo = "";
			String reqId="";
			int rowcount = 0;
			int checkStauts = 0;
			int countDuplicateValue = 0 , i = 0;
			String lstProduct = "";
			String lstExceldata ="";
			while(itr.hasNext())
			{
				logger.info("UPDATING PENDING LIST BULK");				
							
				interSSDTransferBulkUploadDto = (DPInterSSDTransferBulkUploadDto) itr.next();
				logger.info("checking duplicate value in DP_DDST_SERIAL_NO table");
				
				 lstProduct = interSSDTransferBulkUploadDto.getFromDistId()+"|"+interSSDTransferBulkUploadDto.getToDistId()+"|"+interSSDTransferBulkUploadDto.getProductId();
				 lstExceldata = interSSDTransferBulkUploadDto.getProductId()+"|"+interSSDTransferBulkUploadDto.getSerialNo();
				 setDcNumber.add(interSSDTransferBulkUploadDto.getFromDistId().concat(interSSDTransferBulkUploadDto.getToDistId()));
				//Collections.sort(serailNumberList);
				//logger.info("lstProduct ::  "+lstProduct);
				 if(serialNumberMap.containsKey(lstProduct))
				 {
					 serailNumberList.add(lstExceldata);
					serialNumberMap.put(lstProduct, serailNumberList);
				 }
				 else if(!serialNumberMap.containsKey(lstProduct))
				 {  
					 serailNumberList = new ArrayList<String>();
				 	 serailNumberList.add(lstExceldata);
				 	 serialNumberMap.put(lstProduct, serailNumberList);
				 }
				 
				 
				listCnt.add(lstProduct);
				
				//Collections.sort(listCnt);
				System.out.println("serailNumberList   "+serailNumberList.size());
				System.out.println("listCnt   "+listCnt.size());
				logger.info("New Serial No.  ::  "+interSSDTransferBulkUploadDto.getSerialNo());				
				logger.info("New Dist ID  ::  "+interSSDTransferBulkUploadDto.getToDistId());
				logger.info("Product ID  ::  "+interSSDTransferBulkUploadDto.getProductId());
				logger.info("From Dist ID  ::  "+interSSDTransferBulkUploadDto.getFromDistId());
				logger.info("reqId  ::  "+reqId);
				logger.info("strDcNo ::  "+strDcNo);
				
				
				
				pstmt.setString(1, interSSDTransferBulkUploadDto.getToDistId());				
				pstmt.setString(2, interSSDTransferBulkUploadDto.getSerialNo());
				pstmt.setString(3, interSSDTransferBulkUploadDto.getProductId());
				pstmt.executeUpdate();
				
				pstmt1.setInt(1, Integer.parseInt(interSSDTransferBulkUploadDto.getFromDistId()));				
				pstmt1.setString(2, interSSDTransferBulkUploadDto.getSerialNo());				
				rst = pstmt1.executeQuery();
				
				if(rst.next()){
					if(rst.getInt(1)>=1){
						
						pstmt2.setString(1, interSSDTransferBulkUploadDto.getToDistId());						
						pstmt2.setString(2, interSSDTransferBulkUploadDto.getSerialNo());													
						pstmt2.executeUpdate();
						
				}else{
					pstmttrail.setInt(1, Integer.parseInt(interSSDTransferBulkUploadDto.getFromDistId()));
					pstmttrail.setInt(2, Integer.parseInt(interSSDTransferBulkUploadDto.getToDistId()));
					pstmttrail.setInt(3, Integer.parseInt(interSSDTransferBulkUploadDto.getProductId()));
					pstmttrail.setString(4, interSSDTransferBulkUploadDto.getSerialNo());					
					pstmttrail.setInt(5, Integer.parseInt(userId));
					pstmttrail.setString(6, Constants.INTER_SSD_TRANSFER_BULK_UPLOAD_STATUS);					
					pstmttrail.executeUpdate();
					
					}
			
				}
				
				updaterows++;	
			}

			
			//strDcNo = getDCNumber();	
						
			Iterator setItr = setDcNumber.iterator(); 
		 	 
		 	 while (setItr.hasNext()){
		 		strDcNo = getDCNumber();
		 		String frmToDistIdKey = (String) setItr.next();
		 		mapDcNumber.put(frmToDistIdKey, strDcNo);
		 		 
		 	 }
			
			 for (String temp : listCnt) {				    
			 		Integer count = map.get(temp);
			 		map.put(temp, (count == null) ? 1 : count + 1);
			 		
			 	}					
					
					int cntKey =0;
					String strDcNum = null;
					
					
					for (Map.Entry<String, Integer> entry : map.entrySet()) {
						reqId = getReqId();
						String strExlData = entry.getKey();
						System.out.println("  strExlData "+strExlData);
						int cntProduct = entry.getValue();
						int start = strExlData.indexOf('|');
					    int end = strExlData.lastIndexOf('|');
					    String strFrmToDistId = strExlData.substring(0, start).concat(strExlData.substring(start + 1, end));
					    System.out.println("  strFrmToDistId-----> "+strFrmToDistId);
					    
					   for (Map.Entry<String, String> dcEntry : mapDcNumber.entrySet()) {
					    	
					    	strDcNum = dcEntry.getValue();
					    	System.out.println(" DcNumber   "+ strDcNum);
					    	if(dcEntry.getKey().contains(strFrmToDistId))
					    		break;
					    	
					    }
					   String strValue = reqId+"|"+strDcNum;
				    	int rcdCount = 0;

							List<String> listSerialNo = serialNumberMap.get(entry.getKey());
					    	for(int n=0; n < listSerialNo.size();n++)
					    	{
						    	rcdCount = cntKey++;
					    		mapExcelData.put(strValue+"|"+rcdCount,(String) listSerialNo.get(n));	
					    	}
					    	
					    //	mapExcelData.put(strValue+"|"+rcdCount,serialNo);
					    //	mapExcelData.put(strValue+"|"+rcdCount, serailNumberList.get(rcdCount));					    	
					    
					    System.out.println("Key : " + entry.getKey() + " Value : "+ entry.getValue()+ " FROM_DIST_ID : "+strExlData.substring(0, start)+ " TO_DIST_ID : "+strExlData.substring(start + 1, end)+ " Product_id : "+strExlData.substring(end + 1, strExlData.length()));
						
					    pstmt7.setString(1,reqId );									
						rst1 = pstmt7.executeQuery();						
						if(rst1.next()){
							if(rst1.getInt(1)>=1){								
								pstmt8.setString(1, strExlData.substring(0, start));						
								pstmt8.setString(2, strExlData.substring(start + 1, end));	
								pstmt8.setString(3, reqId);
								pstmt8.executeUpdate();
							}
						else{
						  pstmt6.setString(1, reqId);
						  pstmt6.setString(2, strExlData.substring(0, start));
						  pstmt6.setString(3, strExlData.substring(start + 1, end));
						  pstmt6.setString(4, strExlData.substring(end + 1, strExlData.length()));	
						  pstmt6.setInt(5, cntProduct);
						  pstmt6.setString(6, Constants.STOCK_TRANSFER_STATUS_RECEIVED);
						  pstmt6.setString(7, strDcNum);
						  pstmt6.setString(8, userId);
						  pstmt6.setInt(9, cntProduct);						  
						  pstmt6.setString(10, strExlData.substring(start + 1, end));						  
						  pstmt6.executeUpdate();	
						 }
					    
					   }
							
					}
					 					
					
					for (Map.Entry<String, String> val : mapExcelData.entrySet()) {
						String strDataKey = val.getKey();
						String strMapValue = val.getValue();						
						System.out.println(" Map Key  "+strDataKey+ "Map Value "+strMapValue);
						int startKey = strDataKey.indexOf('|');
					    int endKey = strDataKey.lastIndexOf('|');
						String strDataVal = val.getValue();
						int startVal = strDataVal.indexOf('|');
						//int middle = strDataVal.indexOf(',');
					    int endVal = strDataVal.lastIndexOf('|');					    
						
					    System.out.println("REQ_ID "+strDataKey.substring(0, startKey)+"DC_NO "+strDataKey.substring(startKey + 1, endKey)+"Product_id  "+strDataVal.substring(0, startVal)+"Serial No "+strDataVal.substring(endVal + 1, strDataVal.length()));
					   /* pstmt9.setString(1,strDataKey.substring(0, startKey));
					    rst2 = pstmt9.executeQuery();
					    if(rst2.next()){
							if(rst2.getInt(1)>=1){								
								pstmt10.setString(1, strDataKey.substring(startKey + 1, endKey));
							    pstmt10.setString(2, strDataVal.substring(endVal + 1, strDataVal.length()));
							    pstmt10.setString(3, strDataKey.substring(0, startKey));
							    pstmt10.executeUpdate();
							}
							else
							{*/
								pstmt5.setString(1, strDataKey.substring(0, startKey));
								pstmt5.setString(2, strDataKey.substring(startKey + 1, endKey));
								pstmt5.setString(3, strDataVal.substring(endVal + 1, strDataVal.length()));
								pstmt5.setString(4, Constants.STOCK_TRANSFER_STATUS_ACCEPTED);
								pstmt5.setString(5, strDataVal.substring(0, startVal));					    
								pstmt5.executeUpdate();					
					    //}
								
				}
			
					returnstr = "Total "+updaterows+" Records Have Been Uploaded Successfully"+duplicateValueMsg;
			
			
		}
		catch (Exception e) 
		{
			try
			{
				e.printStackTrace();
				connection.rollback();
				logger.info("Inside main Exception");
				return "Internal Error Occured.Please try later";
			}
			catch(Exception ex)
			{
				logger.error(ex);
			}
		}
		finally
		{
			DBConnectionManager.releaseResources(pstmt, null);
			DBConnectionManager.releaseResources(pstmt1, rst);
			DBConnectionManager.releaseResources(pstmt2, null);
			DBConnectionManager.releaseResources(pstmt3, rst1);
			DBConnectionManager.releaseResources(pstmt4, null);
			DBConnectionManager.releaseResources(pstmttrail, null);
			
		}
			
			return returnstr;
		}
				
	public boolean validateProductId(String productName)
	{
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		int count =0;
		boolean flag = false;
		try{
			
			logger.info("inside --- validateOlmId productId == "+productName);
			pstmt = connection.prepareStatement(DBQueries.SQL_SELECT_PRODUCTID);
			pstmt.setString(1,productName);
			rs =pstmt.executeQuery();
			if(rs.next()){
				
				count = rs.getInt(1);
			}
			if(count >=1)
				flag =  true;
			else
				flag =  false;
			
		}catch (Exception e) 
		{
			e.printStackTrace();
			logger.info("Inside main Exception of validateProductId method--"+e.getMessage());
		}
		return flag;
	}
	
	
	public ResultSet validateDistOlmId(String productName,String distOlmId)
	{
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		try{
			
			logger.info("inside --- validateDistOlmId Product name == "+productName +" and dist OLM id == "+distOlmId);
			pstmt = connection.prepareStatement(SQL_SELECT_ST_DISTID);
			pstmt.setString(1,productName.toUpperCase());
			pstmt.setString(2,distOlmId);
			rs =pstmt.executeQuery();
			
			
		}catch (Exception e) 
		{
			e.printStackTrace();
			logger.info("Inside main Exception of validateDistOlmId method--"+e.getMessage());
		}
	/*	finally
		{
			//DBConnectionManager.releaseResources(pstmt, null);
		}*/
		
		
		
		return rs;
	}

	public boolean validateOlmId(String str) {
		if (str.length() != 8) {
			return false;
		}

		return true;
	}

	public boolean validateOlmDistId(String distOlmId) {
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		int count = 0;
		boolean flag = false;
		try {

			logger.info("inside --- validateOlmId dist OLM id == " + distOlmId);
			pstmt = connection.prepareStatement(SQL_SELECT_OLMID);
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

	public boolean validateSerialNo(String str) {

		if (!ValidatorUtility.isValidNumber(str)) {
			return false;
		}
		return true;
	}

	public ResultSet validateToDistOlmId(String toDistOlmId)
	{
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		try{
			
			logger.info("inside --- validateToDistOlmId ----- to dist OLM id == "+toDistOlmId);
			pstmt = connection.prepareStatement(SQL_SELECT_OLMID_INTER_SSD_BULK);
			
			pstmt.setString(1,toDistOlmId);
			rs =pstmt.executeQuery();
			
			
		}catch (Exception e) 
		{
			e.printStackTrace();
			logger.info("Inside main Exception of validateToDistOlmId method--"+e.getMessage());
		}
	/*	finally
		{
			//DBConnectionManager.releaseResources(pstmt, null);
		}*/
		
		
		
		return rs;
	}
	
	public boolean validateSerialNumber(String srno)
	{
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		int count =0;
		boolean flag = false;
		
		try{
			
			logger.info("inside --- validateSerailNumber == "+srno);
			pstmt = connection.prepareStatement(SQL_SELECT_SERIAL_NO);
			
			pstmt.setString(1,srno.trim());
			rs =pstmt.executeQuery();
			
			if (rs.next()) {

				count = rs.getInt(1);
			}
			if (count >= 1)
				flag = true;
			else
				flag = false;
			
			
		}catch (Exception e) 
		{
			e.printStackTrace();
			logger.info("Inside main Exception of validateSerialNo. method--"+e.getMessage());
		}
	
		return flag;
	}
	
	

	
	public String getDCNumber(){
		ResultSet rst = null;
		PreparedStatement pst = null;
		String dcNumber = "";
		try {
			pst = connection.prepareStatement(SQL_SELECT_DCNO);
			rst = pst.executeQuery();
			if(rst.next()){
				dcNumber = rst.getString(1);
			}
		} catch (SQLException e) {
			logger.error("**-> getDCNumber function of  InterSsdTransferAdminDaoImpl " + e);
		} catch (Exception ex) {
			logger.error("**-> getDCNumber function of  InterSsdTransferAdminDaoImpl " + ex);
		} finally {
			DBConnectionManager.releaseResources(pst, rst);
		}
		return dcNumber;
	}
	public String getReqId(){
		ResultSet rst = null;
		PreparedStatement pst = null;
		String reqId = "";
		try {
			pst = connection.prepareStatement(SQL_SELECT_REQID);
			rst = pst.executeQuery();
			if(rst.next()){
				reqId = rst.getString(1);
			}
		} catch (SQLException e) {
			logger.error("**-> getDCNumber function of  InterSsdTransferAdminDaoImpl " + e);
		} catch (Exception ex) {
			logger.error("**-> getDCNumber function of  InterSsdTransferAdminDaoImpl " + ex);
		} finally {
			DBConnectionManager.releaseResources(pst, rst);
		}
		return reqId;
	}

	public boolean validateDuplicateRecord(String fromdist, String toDist, String productId,String serialNo) throws DPServiceException
	{
		ResultSet rst = null;
		PreparedStatement pst = null;
		int rowCount = 0;
		boolean flag = false;
					
		
		try {
			
			pst = connection.prepareStatement(SQL_SELECT_RECORD_FROM_TABLE);
			pst.setString(1, fromdist);
			pst.setString(2, toDist);
			pst.setString(3, productId);
			pst.setString(4, serialNo);
			
			rst = pst.executeQuery();
			if(rst.next()){
				rowCount = rst.getInt("ROWCOUNT");
			}
			
			if(rowCount != 0)
			{
				flag = true;
			}
			else
			{
				flag = false;
			}
			
			
		} catch (SQLException e) {
			logger.error("**-> get record from table InterSsdTransferAdminDaoImpl " + e);
		} catch (Exception ex) {
			logger.error("**-> get record from table InterSsdTransferAdminDaoImpl  " + ex);
		} finally {
			DBConnectionManager.releaseResources(pst, rst);
		}
		return flag;
	}

	public boolean validateInitiated(String fromdist, String toDist, String productId) throws DPServiceException 
	{
		ResultSet rst = null;
		PreparedStatement pst = null;
		int rowCount = 0;
		boolean flag = false ;
		try {

			pst = connection.prepareStatement(SQL_SELECT_CHECK_STAUTS_FROM_TABLE);
			pst.setString(1, fromdist);
			pst.setString(2,toDist);
			pst.setString(3, productId);
			
			rst = pst.executeQuery();
			if(rst.next()){
				rowCount = rst.getInt("ROWCOUNT");
			}
			if(rowCount != 0)
			{
				flag = true;
			}
			else
			{
				flag = false;
			}
		} catch (SQLException e) {
			logger.error("**-> check  status from table DP_DIST_STOCK_TRANSFER InterSsdTransferAdminDaoImpl " + e);
		} catch (Exception ex) {
			logger.error("**-> get status from table DP_DIST_STOCK_TRANSFER InterSsdTransferAdminDaoImpl  " + ex);
		} finally {
			DBConnectionManager.releaseResources(pst, rst);
		}
		return flag;
	
	}
	
	
	public  boolean validateDistIdandSerialNo(String fromdist,String serialNo,String productId)
	{
		ResultSet rst = null;
		PreparedStatement pst = null;
		int rowCount = 0;
		boolean flag = false ;
		try {

			pst = connection.prepareStatement(SQL_SELECT_CHECK_FROMDISTID);
			pst.setString(1, fromdist);
			pst.setString(2,serialNo);
			pst.setString(3, productId);
			
			rst = pst.executeQuery();
			if(rst.next()){
				rowCount = rst.getInt("ROWCOUNT");
			}
			if(rowCount == 0)
			{
				flag = true;
			}
			else
			{
				flag = false;
			}
		} catch (SQLException e) {
			logger.error("**-> check  status from table DP_DIST_STOCK_TRANSFER InterSsdTransferAdminDaoImpl " + e);
		} catch (Exception ex) {
			logger.error("**-> get status from table DP_DIST_STOCK_TRANSFER InterSsdTransferAdminDaoImpl  " + ex);
		} finally {
			DBConnectionManager.releaseResources(pst, rst);
		}
		return flag;

	}
	
	/*public boolean validateAgeing(String fromdist, String serialNo, String productId) throws DPServiceException 
	{
		ResultSet rst = null;
		PreparedStatement pst = null;
		int rowCount = 0;
		boolean flag = false ;
		try {

			pst = connection.prepareStatement(SQL_SELECT_CHECK_AGEING);
			pst.setString(1, fromdist);
			pst.setString(2,serialNo);
			pst.setString(3, productId);
			
			rst = pst.executeQuery();
			if(rst.next()){
				rowCount = rst.getInt("ROWCOUNT");
			}
			if(rowCount == 0)
			{
				flag = true;
			}
			else
			{
				flag = false;
			}
		} catch (SQLException e) {
			logger.error("**-> check  status from table DP_DIST_STOCK_TRANSFER InterSsdTransferAdminDaoImpl " + e);
		} catch (Exception ex) {
			logger.error("**-> get status from table DP_DIST_STOCK_TRANSFER InterSsdTransferAdminDaoImpl  " + ex);
		} finally {
			DBConnectionManager.releaseResources(pst, rst);
		}
		return flag;
	
	}
*/	
	
}
