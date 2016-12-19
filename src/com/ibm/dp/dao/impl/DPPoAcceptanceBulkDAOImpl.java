package com.ibm.dp.dao.impl;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.FileReader;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.StringTokenizer;

import org.apache.log4j.Logger;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.struts.upload.FormFile;

import com.ibm.dp.common.Constants;
import com.ibm.dp.common.DBQueries;
import com.ibm.dp.dao.DPPoAcceptanceBulkDAO;
import com.ibm.dp.dto.DPPoAcceptanceBulkDTO;
import com.ibm.utilities.Utility;
import com.ibm.utilities.ValidatorUtility;
import com.ibm.virtualization.recharge.common.ResourceReader;
import com.ibm.virtualization.recharge.dao.rdbms.BaseDaoRdbms;
import com.ibm.virtualization.recharge.db.DBConnectionManager;
import com.ibm.virtualization.recharge.exception.DAOException;

public class DPPoAcceptanceBulkDAOImpl extends BaseDaoRdbms implements DPPoAcceptanceBulkDAO{
	
	private static Logger logger = Logger.getLogger(DPPoAcceptanceBulkDAOImpl.class.getName());
	
	public DPPoAcceptanceBulkDAOImpl(Connection connection) 
	{
		super(connection);
	}
	
	public List uploadExcel(FormFile myfile ,String deliveryChallanNo) throws DAOException
	{
		String str  = "";
		List list = new ArrayList();
		List Error_list = new ArrayList();
		DPPoAcceptanceBulkDTO dpPoAcceptanceBulkDTO = new DPPoAcceptanceBulkDTO();
		boolean validateFlag=true;
		String data="";
		ArrayList checkDuplicate = new ArrayList();
		String stb_no="";
		InputStream stream = null;
		ByteArrayOutputStream baos = null;
		try
		{
			 /* HSSFWorkbook workbook = new HSSFWorkbook(myfile.getInputStream());
			  HSSFSheet sheet = workbook.getSheetAt(0);
			  Iterator rows = sheet.rowIterator();
			  int totalrows = sheet.getLastRowNum()+1;
			  
			  if(totalrows > Integer.parseInt(ResourceReader.getValueFromBundle(com.ibm.virtualization.recharge.common.Constants.BULK_UPLAOD_MAX_LIMIT , com.ibm.virtualization.recharge.common.Constants.WEBSERVICE_RESOURCE_BUNDLE)))
			  {
				  dpPoAcceptanceBulkDTO = new DPPoAcceptanceBulkDTO();
				  dpPoAcceptanceBulkDTO.setErr_msg("Limit exceeds: Maximum "+Integer.parseInt(ResourceReader.getValueFromBundle(com.ibm.virtualization.recharge.common.Constants.BULK_UPLAOD_MAX_LIMIT , com.ibm.virtualization.recharge.common.Constants.WEBSERVICE_RESOURCE_BUNDLE))+" Serial Numbers are allowed in a file.");
        		  //list.add(dpPoAcceptanceBulkDTO);
        		  Error_list.add(dpPoAcceptanceBulkDTO);
        		  return Error_list;
			  }
			  int rowNo = 0;
	          int rowNumber = 1;
	          
	          ArrayList checkDuplicate = new ArrayList();*/
	          boolean isEmpty=true;
	          stream = myfile.getInputStream();
	          baos = new ByteArrayOutputStream();
	          int size = (int) myfile.getFileSize();
	          byte buffer[] = new byte[size];
	          int bytesRead = 0;
//	        read the input
	          while( (bytesRead = stream.read(buffer, 0, size)) != -1 )
	          {
	        	  baos.write( buffer, 0, bytesRead );
	          }
	          data = new String( baos.toByteArray() );
	          int k = 0;
	          
	          //replace ,, with , ,
	          String newdata = data.replace(",", ",");
	          StringTokenizer st = new StringTokenizer(newdata, "");
	      //    String[] values = new String[dynamicArraySize];
	          
	          String categoryCode = Utility.getDcDetail(connection, deliveryChallanNo);
	          
	          
	          while (st.hasMoreTokens())
	          {
	        	  isEmpty = false;
	        	  dpPoAcceptanceBulkDTO = new DPPoAcceptanceBulkDTO();
	        	  stb_no = st.nextToken(",\n\r").toString();
	        	  if(stb_no != null)
	        	  {
	        		  stb_no = stb_no.trim();
	        	  }
	        	  boolean validate = validateCell(stb_no,categoryCode);
		          if(stb_no != null && "".equals(stb_no.trim()))
		          {
		        	  	dpPoAcceptanceBulkDTO.setErr_msg("Blank space not allowed in Serial Number");
		        	  	dpPoAcceptanceBulkDTO.setSerial_no(stb_no+" ");
		            	Error_list.add(dpPoAcceptanceBulkDTO);
						validateFlag = false;
		          }
		          else if(checkDuplicate.contains(stb_no))
	          		{
	          				dpPoAcceptanceBulkDTO.setErr_msg("Duplicate Serial Number");
	            			dpPoAcceptanceBulkDTO.setSerial_no(stb_no+" ");
	            			//list.add(dpPoAcceptanceBulkDTO);
	            			Error_list.add(dpPoAcceptanceBulkDTO);
	            			validateFlag = false;
	            			//return list;
	          		}
		          else if(!validate)
	          		{
				        	  if(categoryCode != null && categoryCode.equalsIgnoreCase(Constants.PRODUCT_CATEGORY_CPE))
					      		{
			        	  			dpPoAcceptanceBulkDTO.setErr_msg("Invalid Serial Number.It should be 11 digit number.");
					      		}
				        	  else
				        	  {
				        		  	dpPoAcceptanceBulkDTO.setErr_msg("Invalid Serial Number.It should be 16 digit number.");
				        	  }
		              			dpPoAcceptanceBulkDTO.setSerial_no(stb_no+" ");
		              			//list.add(dpPoAcceptanceBulkDTO);
		              			Error_list.add(dpPoAcceptanceBulkDTO);
		              			validateFlag = false;
	          		}
	          		else if(categoryCode != null && categoryCode.equalsIgnoreCase(Constants.PRODUCT_CATEGORY_CPE) && !validateCellForZero(stb_no))
	          		{
	          				dpPoAcceptanceBulkDTO.setSerial_no(stb_no+" ");
	          				dpPoAcceptanceBulkDTO.setErr_msg("Invalid Serial Number.It should be start with 0 ");
		              		//list.add(dpPoAcceptanceBulkDTO);
	          				Error_list.add(dpPoAcceptanceBulkDTO);
		              		validateFlag = false;
		              		//return list;
	          		}
	          		else
	      			{
	      				String presentInDp = Utility.stbPresentinDPForPO(connection, stb_no , deliveryChallanNo,categoryCode);
	      				if( presentInDp != null && !presentInDp.equals(""))
	      				{
	      					dpPoAcceptanceBulkDTO.setSerial_no(stb_no+" ");
	          				dpPoAcceptanceBulkDTO.setErr_msg(presentInDp);
		              		Error_list.add(dpPoAcceptanceBulkDTO);
		              		validateFlag = false;
		              	}
	      				else
	      				{
	      					dpPoAcceptanceBulkDTO.setErr_msg("SUCCESS");
		          			checkDuplicate.add(stb_no);
		          			
		          			dpPoAcceptanceBulkDTO.setSerial_no(stb_no+" ");
		          			Error_list.add(dpPoAcceptanceBulkDTO);
	      				}
	      			}
		          k++;
	          };
	          if(isEmpty)
	          {
	        	  	dpPoAcceptanceBulkDTO.setErr_msg("Empty File Uploaded");
      				dpPoAcceptanceBulkDTO.setSerial_no(" ");
      				Error_list.add(dpPoAcceptanceBulkDTO);
      				list = Error_list;
	          }
	          else {
		          if(validateFlag)
		          {
		        	  dpPoAcceptanceBulkDTO.setList_srno(checkDuplicate);
		        	  list.add(dpPoAcceptanceBulkDTO);
		          }
		          else
		          {
		        	  list = Error_list;
		          }
	          }
	          
	         /*
	        
	          while (rows.hasNext()) {
	        	  dpPoAcceptanceBulkDTO = new DPPoAcceptanceBulkDTO();
	        	  HSSFRow row = (HSSFRow) rows.next();
	        	  if(row.getLastCellNum() >1 || row.getPhysicalNumberOfCells() >1 )
	        	  {
	        		  dpPoAcceptanceBulkDTO.setErr_msg("File should contain only one data column.Row Number:"+(row.getRowNum()+1));
            		  Error_list.add(dpPoAcceptanceBulkDTO);
            		  validateFlag = false;
            		  return Error_list;
	        	  }
	        	  
	        	  
	        	  Iterator cells = row.cellIterator();
	        	  int columnno = 0;
	        	  
	        int columnIndex = 0;
	        int cellNo = 0;
	        
	            if(cells != null)
	            {
	             
	        	  while (cells.hasNext()) {
	        		  if(cellNo == 0)
	        		  {
	        			  cellNo++;
	        			  HSSFCell cell = (HSSFCell) cells.next();
	        			  	columnIndex = cell.getColumnIndex();
		              		if(columnIndex > 0)
		              		{
		              			dpPoAcceptanceBulkDTO.setErr_msg("File should contain only one data column.Row Number:"+(row.getRowNum()+1));
		              			//list.add(dpPoAcceptanceBulkDTO);
		              			Error_list.add(dpPoAcceptanceBulkDTO);
		              			validateFlag = false;
		              			//return list;
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
		            		if(cellValue != null)
		            		{
		            			cellValue = cellValue.trim();
		            		}
		            		
		            		boolean validate = validateCell(cellValue);
		            		if(cellValue ==  null || "".equalsIgnoreCase(cellValue))
		            		{
		              			dpPoAcceptanceBulkDTO.setErr_msg("File should contain only one data column.Row Number:"+(row.getRowNum()+1));
		              			//list.add(dpPoAcceptanceBulkDTO);
		              			Error_list.add(dpPoAcceptanceBulkDTO);
		              			validateFlag = false;
		              			//return list;
		            		}
		            		else if(checkDuplicate.contains(cellValue))
		            		{
		            			dpPoAcceptanceBulkDTO.setErr_msg("Duplicate Serial Number");
		              			dpPoAcceptanceBulkDTO.setSerial_no(cellValue+" ");
		              			//list.add(dpPoAcceptanceBulkDTO);
		              			Error_list.add(dpPoAcceptanceBulkDTO);
		              			validateFlag = false;
		              			//return list;
		            		}
		            		else if(!validate)
		            		{
		              				dpPoAcceptanceBulkDTO.setErr_msg("Invalid Serial Number.It should be 11 digit number.");
			              			dpPoAcceptanceBulkDTO.setSerial_no(cellValue+" ");
			              			//list.add(dpPoAcceptanceBulkDTO);
			              			Error_list.add(dpPoAcceptanceBulkDTO);
			              			validateFlag = false;
		            		}
		            		else if(!validateCellForZero(cellValue))
		            		{
		            			
			            			dpPoAcceptanceBulkDTO.setSerial_no(cellValue+" ");
		            				dpPoAcceptanceBulkDTO.setErr_msg("Invalid Serial Number.It should be start with 0 ");
			              			//list.add(dpPoAcceptanceBulkDTO);
		            				Error_list.add(dpPoAcceptanceBulkDTO);
			              			validateFlag = false;
			              			//return list;
		            		}	
	            			else
	            			{
		            			dpPoAcceptanceBulkDTO.setErr_msg("SUCCESS");
		            			checkDuplicate.add(cellValue);
		            			
		            			dpPoAcceptanceBulkDTO.setSerial_no(cellValue+" ");
		            			Error_list.add(dpPoAcceptanceBulkDTO);
	            			}
		            }
	        		  else
	        		  {
	              		  dpPoAcceptanceBulkDTO.setErr_msg("File should contain only one data column.Row Number:"+(row.getRowNum()+1));
	              		  //list.add(dpPoAcceptanceBulkDTO);
	              		  Error_list.add(dpPoAcceptanceBulkDTO);
	              		  validateFlag = false;
	              		  //return list;
	        		  }
	        		  rowNumber++;
	            	}
	            }
	            else
	            {
             		  dpPoAcceptanceBulkDTO.setErr_msg("File should contain only one data column.Row Number:"+(row.getRowNum()+1));
             		  //list.add(dpPoAcceptanceBulkDTO);
             		  Error_list.add(dpPoAcceptanceBulkDTO);
             		  validateFlag = false;
             		  //return list; 
	            }
	        	 k++;
	        }
          if(validateFlag)
          {
        	  dpPoAcceptanceBulkDTO.setList_srno(checkDuplicate);
        	  list.add(dpPoAcceptanceBulkDTO);
          }
          else
          {
        	  list = Error_list;
          }*/
        }
		catch (Exception e) {
        	
        	logger.info("Error while reading the file---");
    	  	dpPoAcceptanceBulkDTO.setErr_msg("Error while reading the file");
			dpPoAcceptanceBulkDTO.setSerial_no(" ");
			Error_list.add(dpPoAcceptanceBulkDTO);
			list = Error_list;
			return list;
        }finally{
        	
        	try {
				stream.close();
				baos.close();
			} catch (Exception e) {
				logger.info("Error while closing the files");
				e.printStackTrace();
			}
	         
        	
        }

		return list;
	}
	public boolean validateCell(String str , String categoryCode)
	{
		
		if(categoryCode != null && categoryCode.equalsIgnoreCase(Constants.PRODUCT_CATEGORY_CPE))
		{
			if(str.length() != Constants.C2S_BULK_UPLOAD_NO_LENGTH)
			{
				return false;
			}
		}
		else
		{
			if(str.length() != Constants.C2S_BULK_UPLOAD_NO_LENGTH_AV)
			{
				return false;
			}
		}
		if(!ValidatorUtility.isValidNumber(str))
		{
			return false;
		}
		return true;
	}
	public boolean validateCellForZero(String str)
	{
		if(str.startsWith("0"))
			return true;
		else
			return false;
	}
	public DPPoAcceptanceBulkDTO getWrongShortSrNo(String invoiceNo, String dcNo, int intCircleID ,List list) throws DAOException
	{

		PreparedStatement ps = null;
		ResultSet rs = null;
		List<DPPoAcceptanceBulkDTO> invoiceList = new ArrayList<DPPoAcceptanceBulkDTO>();
		List<DPPoAcceptanceBulkDTO> shortSrNoList= new ArrayList<DPPoAcceptanceBulkDTO>();
		List<DPPoAcceptanceBulkDTO> totalSrNoList= new ArrayList<DPPoAcceptanceBulkDTO>();
		
		List<DPPoAcceptanceBulkDTO> sr_no_list_file= new ArrayList<DPPoAcceptanceBulkDTO>();
		List<DPPoAcceptanceBulkDTO> ExtraSrNoList= new ArrayList<DPPoAcceptanceBulkDTO>();
		List<DPPoAcceptanceBulkDTO> availableSerialNo= new ArrayList<DPPoAcceptanceBulkDTO>();
		List<String> list_invoice= new ArrayList<String>();
		DPPoAcceptanceBulkDTO finalPoAcceptanceBulkDTO = null;
		int totalSerialNumbers=0;
		try 
		{
			ps = connection.prepareStatement(DBQueries.GET_ALL_SERIALS_NO_PRODUCT);
			
			ps.setInt(1, intCircleID);
			ps.setString(2, invoiceNo);
			ps.setString(3, dcNo);			
			rs = ps.executeQuery();
			DPPoAcceptanceBulkDTO dpPoAcceptanceBulkDTO = null;
			DPPoAcceptanceBulkDTO dpPoAcceptanceBulkDTO_File = null;
			DPPoAcceptanceBulkDTO dpPoAcceptanceBulkDTOShort = null;
			DPPoAcceptanceBulkDTO dpPoAcceptanceBulkDTOExtra = null;
			DPPoAcceptanceBulkDTO dpPoAcceptanceBulkDTOAvailable= null;
			DPPoAcceptanceBulkDTO dpPoAcceptanceBulkDTOTotal= null;
			String sr_no_invoice="";
			while(rs.next())
			{
				dpPoAcceptanceBulkDTO = new DPPoAcceptanceBulkDTO();
				sr_no_invoice = rs.getString("SERIAL_NO");
				dpPoAcceptanceBulkDTO.setSerial_no(sr_no_invoice);
				dpPoAcceptanceBulkDTO.setProduct_id(rs.getInt("PRODUCT_ID"));
				dpPoAcceptanceBulkDTO.setProduct_name(rs.getString("PRODUCT_NAME"));
				invoiceList.add(dpPoAcceptanceBulkDTO);
				list_invoice.add(sr_no_invoice);
			}
			Iterator ittShort = invoiceList.iterator();
			Iterator ittFile = null;
			
			while(ittShort.hasNext())
			{
				dpPoAcceptanceBulkDTO = (DPPoAcceptanceBulkDTO) ittShort.next();
				ittFile = list.iterator();
				while(ittFile.hasNext())
				{
					dpPoAcceptanceBulkDTO_File = (DPPoAcceptanceBulkDTO) ittFile.next();
					sr_no_list_file = dpPoAcceptanceBulkDTO_File.getList_srno();
					if(!sr_no_list_file.contains(dpPoAcceptanceBulkDTO.getSerial_no()))
					{
						dpPoAcceptanceBulkDTOShort = new DPPoAcceptanceBulkDTO();
						dpPoAcceptanceBulkDTOShort.setSerial_no(dpPoAcceptanceBulkDTO.getSerial_no());
						dpPoAcceptanceBulkDTOShort.setProduct_id(dpPoAcceptanceBulkDTO.getProduct_id());
						dpPoAcceptanceBulkDTOShort.setProduct_name(dpPoAcceptanceBulkDTO.getProduct_name());
						shortSrNoList.add(dpPoAcceptanceBulkDTOShort);
					}
					else
					{
						dpPoAcceptanceBulkDTOAvailable = new DPPoAcceptanceBulkDTO();
						dpPoAcceptanceBulkDTOAvailable.setSerial_no(dpPoAcceptanceBulkDTO.getSerial_no());
						availableSerialNo.add(dpPoAcceptanceBulkDTOAvailable);
					}
				}
			}
			Iterator ittExtra = list.iterator();
			Iterator ittExtraSrNo= null;
			String sr_no_file="";
			while(ittExtra.hasNext())
			{
				dpPoAcceptanceBulkDTO = (DPPoAcceptanceBulkDTO) ittExtra.next();
				sr_no_list_file = dpPoAcceptanceBulkDTO.getList_srno();
				ittExtraSrNo = sr_no_list_file.iterator();
				while(ittExtraSrNo.hasNext())
				{
					sr_no_file = (String) ittExtraSrNo.next();
					if(!list_invoice.contains(sr_no_file))
					{
						dpPoAcceptanceBulkDTOExtra = new DPPoAcceptanceBulkDTO();
						dpPoAcceptanceBulkDTOExtra.setSerial_no(sr_no_file);
						ExtraSrNoList.add(dpPoAcceptanceBulkDTOExtra);
					}
				}
				
			}
			
			Iterator ittTotalFile = null;
			Iterator ittTotalSrNo= null;
			ittTotalFile = list.iterator();
				while(ittTotalFile.hasNext())
				{
					dpPoAcceptanceBulkDTOTotal = (DPPoAcceptanceBulkDTO) ittTotalFile.next();
					sr_no_list_file = dpPoAcceptanceBulkDTO.getList_srno();
					ittTotalSrNo = sr_no_list_file.iterator();
					while(ittTotalSrNo.hasNext())
					{
						sr_no_file = (String) ittTotalSrNo.next();
						
						dpPoAcceptanceBulkDTOTotal = new DPPoAcceptanceBulkDTO();
						dpPoAcceptanceBulkDTOTotal.setSerial_no(sr_no_file);
						totalSrNoList.add(dpPoAcceptanceBulkDTOTotal);
						
					}
					
					
				}
			
			
			
			finalPoAcceptanceBulkDTO = new DPPoAcceptanceBulkDTO();
			finalPoAcceptanceBulkDTO.setShort_sr_no_list(shortSrNoList);
			finalPoAcceptanceBulkDTO.setExtra_sr_no_list(ExtraSrNoList);
			finalPoAcceptanceBulkDTO.setAvailable_sr_no_list(availableSerialNo);
			finalPoAcceptanceBulkDTO.setTotalSrNoList(totalSrNoList);
			//finalPoAcceptanceBulkDTO.setTotalSerialNumbers(String.valueOf(shortSrNoList.size()+ExtraSrNoList.size()+availableSerialNo.size()));
		}catch(SQLException sqle)
		{
			sqle.printStackTrace();
			throw new DAOException("SQLException: " + sqle.getMessage());
		}catch(Exception ex)
		{
			ex.printStackTrace();
			throw new DAOException("Exception: " + ex.getMessage());
		}
		finally
		{
			try {
				if (rs != null)
					rs.close();
				if (ps != null)
					ps.close();
			} catch (Exception e) {
				e.printStackTrace();
				logger.error("Error Occured in ReportDAOImpl "+e.getMessage());
			}
		}
		
			return finalPoAcceptanceBulkDTO;
	}
	public DPPoAcceptanceBulkDTO getShortSrNo(String dcNo) throws DAOException
	{

		PreparedStatement ps = null;
		ResultSet rs = null;
		List<DPPoAcceptanceBulkDTO> invoiceList = new ArrayList<DPPoAcceptanceBulkDTO>();
		/*List<DPPoAcceptanceBulkDTO> shortSrNoList= new ArrayList<DPPoAcceptanceBulkDTO>();
		List<DPPoAcceptanceBulkDTO> totalSrNoList= new ArrayList<DPPoAcceptanceBulkDTO>();*/
		
		/*List<DPPoAcceptanceBulkDTO> sr_no_list_file= new ArrayList<DPPoAcceptanceBulkDTO>();
		List<DPPoAcceptanceBulkDTO> ExtraSrNoList= new ArrayList<DPPoAcceptanceBulkDTO>();
		List<DPPoAcceptanceBulkDTO> availableSerialNo= new ArrayList<DPPoAcceptanceBulkDTO>();*/
		List<String> list_invoice= new ArrayList<String>();
		DPPoAcceptanceBulkDTO finalPoAcceptanceBulkDTO = null;
		/*int totalSerialNumbers=0;*/
		try 
		{
			ps = connection.prepareStatement(DBQueries.GET_ALL_SERIALS_NO_PRODUCT_MISSING);
			
			ps.setString(1, dcNo);	
			rs = ps.executeQuery();
			DPPoAcceptanceBulkDTO dpPoAcceptanceBulkDTO = new DPPoAcceptanceBulkDTO();
			/*DPPoAcceptanceBulkDTO dpPoAcceptanceBulkDTO_File = null;
			DPPoAcceptanceBulkDTO dpPoAcceptanceBulkDTOShort = null;
			DPPoAcceptanceBulkDTO dpPoAcceptanceBulkDTOExtra = null;
			DPPoAcceptanceBulkDTO dpPoAcceptanceBulkDTOAvailable= null;
			DPPoAcceptanceBulkDTO dpPoAcceptanceBulkDTOTotal= null;*/
			String sr_no_invoice="";
			String inv_no="";
			String dc_no="";
			
			while(rs.next())
			{
				sr_no_invoice = rs.getString("SERIAL_NO");
				inv_no = rs.getString("INV_NO");
				dc_no = rs.getString("DC_NO");
				
				list_invoice.add(sr_no_invoice);
			}
			String[] shortSrNo = new String[list_invoice.size()];
			for(int i=0; i < list_invoice.size() ; i++)
			{
				shortSrNo[i] = list_invoice.get(i);
			}
			//shortSrNo = (String[]) list_invoice.toArray();
			
			/*Iterator ittShort = invoiceList.iterator();
			Iterator ittFile = null;*/
			
			/*while(ittShort.hasNext())
			{
				dpPoAcceptanceBulkDTO = (DPPoAcceptanceBulkDTO) ittShort.next();
				ittFile = list.iterator();
				while(ittFile.hasNext())
				{
					dpPoAcceptanceBulkDTO_File = (DPPoAcceptanceBulkDTO) ittFile.next();
					sr_no_list_file = dpPoAcceptanceBulkDTO_File.getList_srno();
					if(!sr_no_list_file.contains(dpPoAcceptanceBulkDTO.getSerial_no()))
					{
						dpPoAcceptanceBulkDTOShort = new DPPoAcceptanceBulkDTO();
						dpPoAcceptanceBulkDTOShort.setSerial_no(dpPoAcceptanceBulkDTO.getSerial_no());
						dpPoAcceptanceBulkDTOShort.setProduct_id(dpPoAcceptanceBulkDTO.getProduct_id());
						dpPoAcceptanceBulkDTOShort.setProduct_name(dpPoAcceptanceBulkDTO.getProduct_name());
						shortSrNoList.add(dpPoAcceptanceBulkDTOShort);
					}
					else
					{
						dpPoAcceptanceBulkDTOAvailable = new DPPoAcceptanceBulkDTO();
						dpPoAcceptanceBulkDTOAvailable.setSerial_no(dpPoAcceptanceBulkDTO.getSerial_no());
						availableSerialNo.add(dpPoAcceptanceBulkDTOAvailable);
					}
				}
			}*/

			

			
			
			
			finalPoAcceptanceBulkDTO = new DPPoAcceptanceBulkDTO();
			finalPoAcceptanceBulkDTO.setShort_sr_no_list(invoiceList);
			finalPoAcceptanceBulkDTO.setShortSrNo(shortSrNo);
			finalPoAcceptanceBulkDTO.setDc_no(dc_no);
			finalPoAcceptanceBulkDTO.setInv_no(inv_no);
			
			/*finalPoAcceptanceBulkDTO.setExtra_sr_no_list(ExtraSrNoList);
			finalPoAcceptanceBulkDTO.setAvailable_sr_no_list(availableSerialNo);
			finalPoAcceptanceBulkDTO.setTotalSrNoList(totalSrNoList);*/
			//finalPoAcceptanceBulkDTO.setTotalSerialNumbers(String.valueOf(shortSrNoList.size()+ExtraSrNoList.size()+availableSerialNo.size()));
		}catch(SQLException sqle)
		{
			sqle.printStackTrace();
			throw new DAOException("SQLException: " + sqle.getMessage());
		}catch(Exception ex)
		{
			ex.printStackTrace();
			throw new DAOException("Exception: " + ex.getMessage());
		}
		finally
		{
			try {
				if (rs != null)
					rs.close();
				if (ps != null)
					ps.close();
			} catch (Exception e) {
				e.printStackTrace();
				logger.error("Error Occured in ReportDAOImpl "+e.getMessage());
			}
		}
		
			return finalPoAcceptanceBulkDTO;
	}
	

}
