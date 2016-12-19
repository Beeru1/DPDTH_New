package com.ibm.dp.dao.impl;

import java.io.InputStream;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import org.apache.log4j.Logger;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;

import com.ibm.dp.common.DBQueries;
import com.ibm.dp.dao.DPPendingListTransferBulkUploadDao;
import com.ibm.dp.dto.DPPendingListTransferBulkUploadDto;
import com.ibm.utilities.Utility;
import com.ibm.utilities.ValidatorUtility;
import com.ibm.virtualization.recharge.common.Constants;
import com.ibm.virtualization.recharge.common.ResourceReader;
import com.ibm.virtualization.recharge.dao.rdbms.BaseDaoRdbms;
import com.ibm.virtualization.recharge.db.DBConnectionManager;
import com.ibm.virtualization.recharge.exception.DAOException;

public class DPPendingListTransferBulkUploadDaoImpl extends BaseDaoRdbms
		implements DPPendingListTransferBulkUploadDao {

	public static Logger logger = Logger
			.getLogger(DPPendingListTransferBulkUploadDaoImpl.class.getName());

	public static final String SQL_SELECT_ST_DISTID = DBQueries.SQL_SELECT_ST_DISTID;
	public static final String SQL_SELECT_OLMID = DBQueries.SQL_SELECT_OLMID;	
	protected static final String SQL_UPDATE_PENDING_LIST_BULK_INV_CHANGE = DBQueries.SQL_UPDATE_PENDING_LIST_BULK_INV_CHANGE;
	protected static final String SQL_UPDATE_PENDING_LIST_BULK_INV = DBQueries.SQL_UPDATE_PENDING_LIST_BULK_INV;	
	protected static final String SQL_SELECT_UPLOAD_TRAIL_STOCK_TRANSFER = DBQueries.SQL_SELECT_UPLOAD_TRAIL_STOCK_TRANSFER;
	protected static final String SQL_INSERT_UPLOAD_TRAIL_STOCK_TRANSFER = DBQueries.SQL_INSERT_UPLOAD_TRAIL_STOCK_TRANSFER;
	protected static final String SQL_UPDATE_UPLOAD_TRAIL_STOCK_TRANSFER = DBQueries.SQL_UPDATE_UPLOAD_TRAIL_STOCK_TRANSFER;
	
	
	

	public DPPendingListTransferBulkUploadDaoImpl(Connection connection) {
		super(connection);
	}

	public List uploadExcel(InputStream inputstream) throws DAOException {
		List list = new ArrayList();
		DPPendingListTransferBulkUploadDto pendingListTransferBulkUploadErrDto = null;

		String fromDistOlmId = "";
		String toDistOlmId = "";
		String productNameDef = "";
		String serialNoDef = "";
		String productNameChange = "";
		String serialNoChange = "";
		String fromDistId = "";
		String toDistId  = "";
		String productIdDef = "";
		String productIdChange = "";
		
		ResultSet rst = null;
		boolean validateFlag = true;

		DPPendingListTransferBulkUploadDto pendingListTransferBulkUploadDto = null;

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
				pendingListTransferBulkUploadErrDto = new DPPendingListTransferBulkUploadDto();
				pendingListTransferBulkUploadErrDto
						.setErr_msg("Limit exceeds: Maximum "
								+ Integer
										.parseInt(ResourceReader
												.getValueFromBundle(
														com.ibm.virtualization.recharge.common.Constants.BULK_UPLAOD_MAX_LIMIT,
														com.ibm.virtualization.recharge.common.Constants.WEBSERVICE_RESOURCE_BUNDLE))
								+ " Serial Numbers are allowed in a file.");
				list.add(pendingListTransferBulkUploadErrDto);
				return list;
			}

			int rowNumber = 1;

			ArrayList checkDuplicate = new ArrayList();
			int k = 1;

			while (rows.hasNext()) {
				pendingListTransferBulkUploadDto = new DPPendingListTransferBulkUploadDto();
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
						productNameDef = "";
						serialNoDef = "";
						productNameChange = "";
						serialNoChange = "";
						while (cells.hasNext()) {
							if (cellNo < 6) {
								cellNo++;
								HSSFCell cell = (HSSFCell) cells.next();

								columnIndex = cell.getColumnIndex();
								if (columnIndex > 5) {
									validateFlag = false;
									pendingListTransferBulkUploadErrDto = new DPPendingListTransferBulkUploadDto();
									pendingListTransferBulkUploadErrDto
											.setErr_msg("File should contain only six data column");
									list
											.add(pendingListTransferBulkUploadErrDto);
									return list;
								}
								String cellValue = null;
								
								if(cell.getCellType() == Cell.CELL_TYPE_NUMERIC) {
							        cell.setCellType(Cell.CELL_TYPE_STRING);
							        cellValue = "0"+cell.getStringCellValue();
								}else{								
								cellValue = cell.getStringCellValue();
								}
								
								System.out.println(" Cell value------->"+cellValue);

								/*switch(cell.getCellType()) {
			            		case HSSFCell.CELL_TYPE_NUMERIC:
			            			cellValue = String.valueOf((double)cell.getNumericCellValue());
			            			if(cellValue.contains(".0")){			            				
			            				cellValue=cellValue.substring(0,cellValue.indexOf(".0"));
			            			}
			            			cell.setCellType(cell.CELL_TYPE_STRING);
			            		break;
			            		case HSSFCell.CELL_TYPE_STRING:
			            			cellValue = cell.getStringCellValue();
				            	break;
		            		}*/
								logger.info("Row Number == " + rowNumber
										+ " and Cell No. === " + cellNo
										+ " and value == " + cellValue);
								if (cellNo == 1) {
									boolean validate = validateOlmId(cellValue
											.trim());
									System.out.println("validate" + validate);
									if (!validate) {
										validateFlag = false;
										pendingListTransferBulkUploadErrDto = new DPPendingListTransferBulkUploadDto();
										pendingListTransferBulkUploadErrDto
												.setErr_msg("Invalid OLM Id at row No: "
														+ (rowNumber)
														+ ".It should be 8 character.");
										list
												.add(pendingListTransferBulkUploadErrDto);

									} else if (validateOlmDistId(cellValue)) {
										fromDistOlmId = cellValue;
										pendingListTransferBulkUploadDto
												.setFromDistOlmId(cellValue);
									} else {
										pendingListTransferBulkUploadDto = new DPPendingListTransferBulkUploadDto();
										pendingListTransferBulkUploadErrDto
												.setErr_msg("Invalid Distributed OLM Id at row No : "
														+ (rowNumber) + ".");
										list
												.add(pendingListTransferBulkUploadErrDto);

									}
								} else if (cellNo == 2) {
									boolean validate = validateOlmId(cellValue
											.trim());
									System.out.println("validate" + validate);
									if (!validate) {
										validateFlag = false;
										pendingListTransferBulkUploadErrDto = new DPPendingListTransferBulkUploadDto();
										pendingListTransferBulkUploadErrDto
												.setErr_msg("Invalid OLM Id at row No: "
														+ (rowNumber)
														+ ".It should be 8 character.");
										list
												.add(pendingListTransferBulkUploadErrDto);

									} else if (validateOlmDistId(cellValue)) {
										toDistOlmId = cellValue;
										pendingListTransferBulkUploadDto
												.setToDistOlmId(cellValue);
									} else {
										pendingListTransferBulkUploadDto = new DPPendingListTransferBulkUploadDto();
										pendingListTransferBulkUploadErrDto
												.setErr_msg("Invalid Distributed OLM Id at row No : "
														+ (rowNumber) + ".");
										list
												.add(pendingListTransferBulkUploadErrDto);

									}
								} else if (cellNo == 3) {

									//pendingListTransferBulkUploadDto = new DPPendingListTransferBulkUploadDto();
									//pendingListTransferBulkUploadDto.setProductNameDef(cellValue.trim());
									if(validateProductId(cellValue)){
				            			
				            			rst = validateDistOlmId(cellValue,fromDistOlmId);
				            			if(rst.next()){
				            				System.out.println("Sanjay&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&");
				            				fromDistId = rst.getString("ACCOUNT_ID");
			                  				productIdDef = rst.getString("PRODUCT_ID");
			                  				System.out.println("&&&&&&&&&&&&&&&&&&&&&&&&&distId&&&&&&&&&"+fromDistId+"productId"+productIdDef);
			                  				productNameDef=cellValue;					            			
			                  				pendingListTransferBulkUploadDto.setProductNameDef(cellValue);			                  				
			                  				pendingListTransferBulkUploadDto.setFromDistId(fromDistId);
			                  				pendingListTransferBulkUploadDto.setProductIdDef(productIdDef);	                  					
				            				
				            				
				            			}else{
			                  				validateFlag =false;
			                  				pendingListTransferBulkUploadDto = new DPPendingListTransferBulkUploadDto();
			                  				pendingListTransferBulkUploadErrDto.setErr_msg("Product Name does not belong to the Circle of Distributor at row No: "+(rowNumber)+".");;
			                        			list.add(pendingListTransferBulkUploadErrDto);
			                  				
			                  			}
				            			}else{
				            				validateFlag =false;
				            				pendingListTransferBulkUploadErrDto = new DPPendingListTransferBulkUploadDto();
				            				pendingListTransferBulkUploadErrDto.setErr_msg("Invalid Product Name at row no.: "+(rowNumber));
		                      				list.add(pendingListTransferBulkUploadErrDto);
				            				
				            			}
									
								} else if (cellNo == 4) {
									
									//boolean validate = validateSerialNo(cellValue.trim());								
									
									serialNoDef = cellValue;
									pendingListTransferBulkUploadDto
											.setSerialNoDef(cellValue);
							/*		if (!validate) {
										validateFlag = false;
										pendingListTransferBulkUploadDto = new DPPendingListTransferBulkUploadDto();
										pendingListTransferBulkUploadDto
												.setErr_msg("Invalid Defective Serial Number at row No: "
														+ (rowNumber) + ".");
										list
												.add(pendingListTransferBulkUploadDto);

									} else {
										if (validateSerialNo(cellValue.trim())) {
											serialNoDef = cellValue;
											pendingListTransferBulkUploadDto
													.setSerialNoDef(cellValue);

										} else {
											validateFlag = false;

											pendingListTransferBulkUploadDto = new DPPendingListTransferBulkUploadDto();
											pendingListTransferBulkUploadDto
													.setErr_msg("Invalid  Defective Serial Number at row No:  "
															+ (rowNumber));
											list
													.add(pendingListTransferBulkUploadDto);

										}

									}*/

								}

								else if (cellNo == 5) {
									
									
									if(validateProductId(cellValue)){
				            			
				            			rst = validateDistOlmId(cellValue,toDistOlmId);
				            			if(rst.next()){
				            				System.out.println("Sanjay&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&");
				            				toDistId = rst.getString("ACCOUNT_ID");
			                  				productIdChange = rst.getString("PRODUCT_ID");
			                  					                  				
			                  				System.out.println("&&&&&&&&&&&&&&&&&&&&&&&&&distId&&&&&&&&&"+toDistId+"productId"+productIdChange);
			                  				productNameChange=cellValue;					            			
			                  				pendingListTransferBulkUploadDto.setProductNameChange(cellValue);			                  				
			                  				pendingListTransferBulkUploadDto.setToDistId(toDistId);
			                  				pendingListTransferBulkUploadDto.setProductIdChange(productIdChange);
		                  					
				            				
				            				
				            			}else{
			                  				validateFlag =false;
			                  				pendingListTransferBulkUploadDto = new DPPendingListTransferBulkUploadDto();
			                  				pendingListTransferBulkUploadErrDto.setErr_msg("Product Name does not belong to the Circle of Distributor at row No: "+(rowNumber)+".");;
			                        			list.add(pendingListTransferBulkUploadErrDto);
			                  				
			                  			}
				            			}else{
				            				validateFlag =false;
				            				pendingListTransferBulkUploadErrDto = new DPPendingListTransferBulkUploadDto();
				            				pendingListTransferBulkUploadErrDto.setErr_msg("Invalid Product Name at row no.: "+(rowNumber));
		                      				list.add(pendingListTransferBulkUploadErrDto);
				            				
				            			}


								} else if (cellNo == 6) {

									//boolean validate = validateSerialNo(cellValue.trim());									
									serialNoChange = cellValue;
									pendingListTransferBulkUploadDto
											.setSerialNoChange(cellValue);
									/*if (!validate) {
										validateFlag = false;
										pendingListTransferBulkUploadDto = new DPPendingListTransferBulkUploadDto();
										pendingListTransferBulkUploadDto
												.setErr_msg("Invalid Change Serial Number at row No: "
														+ (rowNumber) + ".");
										list
												.add(pendingListTransferBulkUploadDto);

									} else {
										if (validateSerialNo(cellValue.trim())) {
											serialNoChange = cellValue;
											pendingListTransferBulkUploadDto
													.setSerialNoChange(cellValue);

										} else {
											validateFlag = false;

											pendingListTransferBulkUploadDto = new DPPendingListTransferBulkUploadDto();
											pendingListTransferBulkUploadDto
													.setErr_msg("Invalid  Change Serial Number at row No:  "
															+ (rowNumber));
											list
													.add(pendingListTransferBulkUploadDto);

										}

									}*/

								}

							}

						}

						rowNumber++;
					} else {
						pendingListTransferBulkUploadErrDto = new DPPendingListTransferBulkUploadDto();
						pendingListTransferBulkUploadErrDto
								.setErr_msg("File should contain only five data column");
						list.add(pendingListTransferBulkUploadErrDto);
						return list;
					}
					logger.info("In Row num == " + (rowNumber - 1)
							+ " Error Message ==  " + validateFlag);
					if (validateFlag) {
						pendingListTransferBulkUploadDto.setErr_msg("SUCCESS");
						list.add(pendingListTransferBulkUploadDto);
					}

				} else {

					// For Header Checking
					int columnIndex = 0;
					int cellNo = 0;
					if (cells != null) {
						while (cells.hasNext()) {
							if (cellNo < 6) {
								cellNo++;
								HSSFCell cell = (HSSFCell) cells.next();

								columnIndex = cell.getColumnIndex();
								if (columnIndex > 5) {
									validateFlag = false;
									pendingListTransferBulkUploadErrDto = new DPPendingListTransferBulkUploadDto();
									pendingListTransferBulkUploadErrDto
											.setErr_msg("File should contain only six data column");
									List list2 = new ArrayList();
									list2
											.add(pendingListTransferBulkUploadErrDto);
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
									pendingListTransferBulkUploadErrDto = new DPPendingListTransferBulkUploadDto();
									pendingListTransferBulkUploadErrDto
											.setErr_msg("File should contain All Required Values");
									List list2 = new ArrayList();
									list2
											.add(pendingListTransferBulkUploadErrDto);
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
										pendingListTransferBulkUploadErrDto = new DPPendingListTransferBulkUploadDto();
										pendingListTransferBulkUploadErrDto
												.setErr_msg("Invalid Header For From Dist OLM ID");
										list
												.add(pendingListTransferBulkUploadErrDto);

									}
								} else if (cellNo == 2) {
									if (!"to dist olm id".equals(cellValue
											.trim().toLowerCase())) {
										validateFlag = false;
										pendingListTransferBulkUploadErrDto = new DPPendingListTransferBulkUploadDto();
										pendingListTransferBulkUploadErrDto
												.setErr_msg("Invalid Header For To Dist OLM ID");
										list
												.add(pendingListTransferBulkUploadErrDto);

									}
								} else if (cellNo == 3) {
									if (!"product name of defective stb"
											.equals(cellValue.trim()
													.toLowerCase())) {
										validateFlag = false;
										pendingListTransferBulkUploadErrDto = new DPPendingListTransferBulkUploadDto();
										pendingListTransferBulkUploadErrDto
												.setErr_msg("Invalid Header For Product Name Of Defective STB");
										list
												.add(pendingListTransferBulkUploadErrDto);

									}
								} else if (cellNo == 4) {
									if (!"defective serial number"
											.equals(cellValue.trim()
													.toLowerCase())) {
										validateFlag = false;
										pendingListTransferBulkUploadErrDto = new DPPendingListTransferBulkUploadDto();
										pendingListTransferBulkUploadErrDto
												.setErr_msg("Invalid Header For Defective Serial Number");
										list
												.add(pendingListTransferBulkUploadErrDto);

									}
								} else if (cellNo == 5) {
									if (!"product name of changed stb"
											.equals(cellValue.trim()
													.toLowerCase())) {
										validateFlag = false;
										pendingListTransferBulkUploadErrDto = new DPPendingListTransferBulkUploadDto();
										pendingListTransferBulkUploadErrDto
												.setErr_msg("Invalid Header For Product Name of Changed STB");
										list
												.add(pendingListTransferBulkUploadErrDto);

									}
								} else if (cellNo == 6) {
									if (!"changed serial number"
											.equals(cellValue.trim()
													.toLowerCase())) {
										validateFlag = false;
										pendingListTransferBulkUploadErrDto = new DPPendingListTransferBulkUploadDto();
										pendingListTransferBulkUploadErrDto
												.setErr_msg("Invalid Header For Changed Serial Number");
										list
												.add(pendingListTransferBulkUploadErrDto);

									}
								}
							} else {
								validateFlag = false;
								pendingListTransferBulkUploadErrDto = new DPPendingListTransferBulkUploadDto();
								pendingListTransferBulkUploadErrDto
										.setErr_msg("File should contain only six data Headers");
								List list2 = new ArrayList();
								list2.add(pendingListTransferBulkUploadErrDto);
								return list2;
							}
						}
						if (cellNo != 6) {
							validateFlag = false;
							pendingListTransferBulkUploadErrDto = new DPPendingListTransferBulkUploadDto();
							pendingListTransferBulkUploadErrDto
									.setErr_msg("File should contain six data Headers");
							List list2 = new ArrayList();
							list2.add(pendingListTransferBulkUploadErrDto);
							return list2;
						}
					} else {
						pendingListTransferBulkUploadErrDto = new DPPendingListTransferBulkUploadDto();
						pendingListTransferBulkUploadErrDto
								.setErr_msg("File should contain six data column");
						List list2 = new ArrayList();
						list2.add(pendingListTransferBulkUploadErrDto);
						return list2;
					}
					rowNumber++;
				}
				k++;
			}
			logger.info("K == " + k);
			if (k == 1) {
				pendingListTransferBulkUploadErrDto = new DPPendingListTransferBulkUploadDto();
				pendingListTransferBulkUploadErrDto
						.setErr_msg("Blank File can not be uploaded!!");
				list.add(pendingListTransferBulkUploadErrDto);
			}

		} catch (Exception e) {

			logger.info(e);
			throw new DAOException("Please Upload a valid Excel Format File");
		} finally {
			DBConnectionManager.releaseResources(null, rst);

		}
		return list;
	}
	
	
	public String updatePendingListTransfer(List list, String userId)
	{
		PreparedStatement pstmt = null;
		PreparedStatement pstmttrail = null;
		PreparedStatement pstmt1 = null;
		PreparedStatement pstmt2 = null;
		PreparedStatement pstmt3 = null;
		String returnstr="";
		logger.info("Size--"+list.size());
		
		ResultSet rst=null;
		DPPendingListTransferBulkUploadDto pendingListTransferBulkUploadDto = null;
		
		try
		{	
			pstmt = connection.prepareStatement(SQL_UPDATE_PENDING_LIST_BULK_INV_CHANGE);
			pstmt1 = connection.prepareStatement(SQL_SELECT_UPLOAD_TRAIL_STOCK_TRANSFER);			
			pstmttrail = connection.prepareStatement(SQL_INSERT_UPLOAD_TRAIL_STOCK_TRANSFER);
			pstmt2 = connection.prepareStatement(SQL_UPDATE_UPLOAD_TRAIL_STOCK_TRANSFER);
			pstmt3 = connection.prepareStatement(SQL_UPDATE_PENDING_LIST_BULK_INV);
			
			Iterator itr = list.iterator();
			int updaterows = 0;
			while(itr.hasNext())
			{
				logger.info("UPDATING PENDING LIST BULK");
				
				pendingListTransferBulkUploadDto = (DPPendingListTransferBulkUploadDto) itr.next();				
				logger.info("New Serail No.  ::  "+pendingListTransferBulkUploadDto.getSerialNoChange());
				logger.info("New Product ID  ::  "+pendingListTransferBulkUploadDto.getProductIdChange());
				logger.info("New Dist ID  ::  "+pendingListTransferBulkUploadDto.getToDistId());
				logger.info("Defective Serial No.  ::  "+pendingListTransferBulkUploadDto.getSerialNoDef());
				logger.info("Defective product ID  ::  "+pendingListTransferBulkUploadDto.getProductIdDef());
				logger.info("Defective Dist Id  ::  "+pendingListTransferBulkUploadDto.getFromDistId());
				pstmt.setString(1, pendingListTransferBulkUploadDto.getSerialNoChange());
				pstmt.setString(2, pendingListTransferBulkUploadDto.getProductIdChange());
				pstmt.setString(3, pendingListTransferBulkUploadDto.getToDistId() );
				pstmt.setString(4, pendingListTransferBulkUploadDto.getSerialNoDef());				
				pstmt.executeUpdate();
				
				pstmt3.setString(1, pendingListTransferBulkUploadDto.getSerialNoChange());
				pstmt3.setString(2, pendingListTransferBulkUploadDto.getSerialNoDef());				
				pstmt3.executeUpdate();
				
				pstmt1.setInt(1, Integer.parseInt(pendingListTransferBulkUploadDto.getFromDistId()));
				pstmt1.setInt(2, Integer.parseInt(pendingListTransferBulkUploadDto.getProductIdDef()));
				pstmt1.setString(3, pendingListTransferBulkUploadDto.getSerialNoDef());				
				rst = pstmt1.executeQuery();
				
				if(rst.next()){
					if(rst.getInt(1)>=1){
						
						pstmt2.setString(1, pendingListTransferBulkUploadDto.getSerialNoChange());
						pstmt2.setInt(2, Integer.parseInt(pendingListTransferBulkUploadDto.getProductIdChange()));
						pstmt2.setInt(3, Integer.parseInt(pendingListTransferBulkUploadDto.getToDistId()));
						pstmt2.setInt(4, Integer.parseInt(pendingListTransferBulkUploadDto.getFromDistId()));
						pstmt2.setInt(5, Integer.parseInt(pendingListTransferBulkUploadDto.getProductIdDef()));
						pstmt2.setString(6, pendingListTransferBulkUploadDto.getSerialNoDef());						
						pstmt2.executeUpdate();
						
				}else{
					pstmttrail.setInt(1, Integer.parseInt(pendingListTransferBulkUploadDto.getFromDistId()));
					pstmttrail.setInt(2, Integer.parseInt(pendingListTransferBulkUploadDto.getToDistId()));
					pstmttrail.setInt(3, Integer.parseInt(pendingListTransferBulkUploadDto.getProductIdDef()));
					pstmttrail.setString(4, pendingListTransferBulkUploadDto.getSerialNoDef());
					pstmttrail.setInt(5, Integer.parseInt(pendingListTransferBulkUploadDto.getProductIdChange()));
					pstmttrail.setString(6, pendingListTransferBulkUploadDto.getSerialNoChange());
					pstmttrail.setInt(7, Integer.parseInt(userId));
					pstmttrail.setString(8, Constants.PENDING_LIST_TRANSFER_BULK_UPLOAD_STATUS);
					
					pstmttrail.executeUpdate();
					
					}
			
				}			
				
				
				updaterows++;	
			}
			returnstr = "Total "+updaterows+" Records Have Been Uploaded Successfully";
			
			
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
			DBConnectionManager.releaseResources(pstmttrail, null);
			DBConnectionManager.releaseResources(pstmt3, null);
			
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

	

	
}
