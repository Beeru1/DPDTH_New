package com.ibm.dp.dao.impl;

import java.io.IOException;
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
import com.ibm.dp.common.DBQueries;
import com.ibm.dp.dao.DPDistPinCodeMapDao;
import com.ibm.dp.dto.DPDistPinCodeMapDto;
import com.ibm.virtualization.recharge.common.ResourceReader;
import com.ibm.virtualization.recharge.dao.rdbms.BaseDaoRdbms;
import com.ibm.virtualization.recharge.db.DBConnectionManager;
import com.ibm.virtualization.recharge.exception.DAOException;

 
public class DPDistPinCodeMapDaoImpl extends BaseDaoRdbms implements  DPDistPinCodeMapDao{
public static Logger logger = Logger.getLogger(DPDistPinCodeMapDaoImpl.class.getName());

	public static final String SQL_SELECT_ALL_DIST_PINCODE_MAP = DBQueries.SQL_SELECT_ALL_DIST_PINCODE_MAP;
	public static final String SQL_SELECT_DIST_CHK_EXISTS 	= DBQueries.SQL_SELECT_DIST_CHK_EXISTS;
	public static final String SQL_SELECT_VALIDATE_UNIQUE_DISTPINMAP 	= DBQueries.SQL_SELECT_VALIDATE_UNIQUE_DISTPINMAP;
	public static final String SQL_UPDATE_DP_DIST_PINCODE = DBQueries.SQL_UPDATE_DP_DIST_PINCODE;
	public static final String SQL_CHK_PINCODE = DBQueries.SQL_CHK_PINCODE;
	private static final String SQL_INSERT_DP_DIST_MAP = DBQueries.SQL_INSERT_DP_DIST_MAP;
	
	public DPDistPinCodeMapDaoImpl(Connection connection) {
		super(connection);
	}


	public List<DPDistPinCodeMapDto> getALLDistPinList() throws DAOException 
	{
		PreparedStatement ps = null;
		Connection db2conn = null; 
		ResultSet rs = null; 
		List<DPDistPinCodeMapDto> distPinCodeList= new ArrayList<DPDistPinCodeMapDto>();
		try {
			db2conn = DBConnectionManager.getDBConnection();
			ps = db2conn.prepareStatement(SQL_SELECT_ALL_DIST_PINCODE_MAP);
			rs = ps.executeQuery();
			while (rs.next()) 
			{
				DPDistPinCodeMapDto distPinMapDTO = new DPDistPinCodeMapDto();
				distPinMapDTO.setStrDistOLMIds(rs.getString("DIST_OLM_ID"));
				distPinMapDTO.setPincode(rs.getString("PINCODE"));
				distPinCodeList.add(distPinMapDTO);
			}
		} 
		catch (Exception e) 
		{
			e.printStackTrace();
			logger.error("Exception occured while getting existing DistributorOLMIds and Pincode mapping   Exception message: "
					+ e.getMessage());
		} 
		finally {
			try {
				if (rs != null)
					rs.close();
				if (ps != null)
					ps.close();
				if (db2conn != null) {
					db2conn.close();
				}
			} catch (Exception e) {
			}
		}
		return distPinCodeList;
	}


	public List uploadExcel(InputStream inputstream) throws DAOException {

		List list = new ArrayList();
		DPDistPinCodeMapDto distPinCodeMapErrDto = null;
		String distOlmId ="";
		String pinCode="";
		ResultSet rst =null;
		boolean validateFlag =true;
		try
		{
			  HSSFWorkbook workbook = new HSSFWorkbook(inputstream);
			  HSSFSheet sheet = workbook.getSheetAt(0);
			  Iterator rows = sheet.rowIterator();
			  int totalrows = sheet.getLastRowNum()+1;
			  //logger.info("Total Rows == "+sheet.getLastRowNum());
			  if(totalrows > Integer.parseInt(ResourceReader.getValueFromBundle(com.ibm.virtualization.recharge.common.Constants.BULK_UPLAOD_MAX_LIMIT , com.ibm.virtualization.recharge.common.Constants.WEBSERVICE_RESOURCE_BUNDLE)))
			  {
				   distPinCodeMapErrDto = new DPDistPinCodeMapDto();
				   distPinCodeMapErrDto.setErr_msg("Limit exceeds: Maximum "+Integer.parseInt(ResourceReader.getValueFromBundle(com.ibm.virtualization.recharge.common.Constants.BULK_UPLAOD_MAX_LIMIT, com.ibm.virtualization.recharge.common.Constants.WEBSERVICE_RESOURCE_BUNDLE))+" Serial Numbers are allowed in a file.");
        		  list.add(distPinCodeMapErrDto);
        		  return list;
			  }
			  int tolRow=sheet.getLastRowNum();
			  if(tolRow==0)
			  {

		        	//logger.info("************inside blank file condition******");
		        	distPinCodeMapErrDto = new DPDistPinCodeMapDto();
		        	distPinCodeMapErrDto.setErr_msg("Blank File can not be uploaded!!");
	        		  list.add(distPinCodeMapErrDto);  	
	        		  return list;
			  }
			  int rowNumber = 1;
			  DPDistPinCodeMapDto mapDto = null;
	          ArrayList checkDuplicate = new ArrayList();
	          int k=1;
	          while (rows.hasNext()) {
	        	  mapDto = new DPDistPinCodeMapDto();
	        	  
	        	  HSSFRow row = (HSSFRow) rows.next();
	        	  Iterator cells = row.cellIterator();
	        	//  logger.info("************rowNumber"+rowNumber+"cells*********"+cells);
	        	  if(rowNumber>1)
	        	  {
	        	  int columnIndex = 0;
	              int cellNo = 0;
	              if(cells != null)
	              {
	            	distOlmId ="";
	            	pinCode ="";
	    			while (cells.hasNext()) {
	        		  if(cellNo < 2)
	        		  {
	        			  cellNo++;
	        			  HSSFCell cell = (HSSFCell) cells.next();
	        			  columnIndex = cell.getColumnIndex();
	        		//	  logger.info("columnIndex**************"+columnIndex+"Sugandha");
		              		if(columnIndex > 1)
		              		{
		              			distPinCodeMapErrDto = new DPDistPinCodeMapDto();
		              			distPinCodeMapErrDto.setErr_msg("File should contain only two data column");
		              			list.add(distPinCodeMapErrDto);
		              			return list;
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
		            		logger.info("Row Number == "+rowNumber +" and Cell No. === "+cellNo +" and value == "+cellValue );
		            		if(cellNo==1)
		            		{
		            			boolean validate = validateOlmId(cellValue.trim());
		            			if(!validate)
			            		{
		            				validateFlag =false;
		            				distPinCodeMapErrDto = new DPDistPinCodeMapDto(); 
		            				distPinCodeMapErrDto.setErr_msg("Invalid OLM Id at row No: "+(rowNumber)+". It should be 8 character.");
			              			list.add(distPinCodeMapErrDto);
		            				
		            			}
		            			else
		            			{
		            				if(validateOlmdistId(cellValue.trim())){
		            				distOlmId=cellValue;
		            				mapDto.setStrDistOLMIds(cellValue);
		            				}
		            				else
		            				{
		            					validateFlag =false;
		            					distPinCodeMapErrDto = new DPDistPinCodeMapDto(); 
		            					distPinCodeMapErrDto.setErr_msg("Invalid Distributor OLM Id at row No:  "+(rowNumber));
				              			list.add(distPinCodeMapErrDto);
		            					
		            				}
			            			
			            		}
		            		}else if(cellNo==2)
		            		{
		            			boolean validate = validatePinCodeIsNumeric(cellValue.trim());
		            			pinCode=cellValue;
		            			mapDto.setPincode(cellValue);
		            			if(!validate)
			            		{
		            				validateFlag =false;
		            				distPinCodeMapErrDto = new DPDistPinCodeMapDto(); 
		            				distPinCodeMapErrDto.setErr_msg("Invalid Pincode at row No: "+(rowNumber)+". It should be of only 6 digit numeric value.");
			              			list.add(distPinCodeMapErrDto);
		            				
		            			}
		            			
		            			else
		            			{
		            				pinCode=cellValue;
		        	            	logger.info("pinCode*********"+pinCode);
		        	            	if(pinCode !=null && !pinCode.equalsIgnoreCase("null") && !pinCode.equalsIgnoreCase("")){
		        	            		Iterator pin =  checkDuplicate.iterator() ;
		        	            		while(pin.hasNext()){
		        		            		String pin1 = pin.next().toString();
		        		            		if(pin1.equalsIgnoreCase(pinCode))
		        		            		{
		        		            		//	logger.info("File contains duplicate Entries For Product");
		        			        	  		validateFlag =false;
		        			        	  		distPinCodeMapErrDto = new DPDistPinCodeMapDto();
		        			        	  		distPinCodeMapErrDto.setErr_msg("File contains duplicate Entries For PINCODE : "+pinCode);
		        		            			list.add(distPinCodeMapErrDto);
		        		            			
		        		            		}
		        		            		else
		        		            		{
		        		            			//logger.info("This pincode is already in use");
		        		              			
		        		            		}
		        		            		
		        		            	}
		                    			checkDuplicate.add(pinCode);
		        	            	}
		        	            	//logger.info("validateUniqueDistPinCode");
		            				rst = validateUniqueDistPinCode(distOlmId,pinCode);
		            				logger.info("*****validateUniqueDistPinCode::"+distOlmId+"pinCode::::::"+pinCode);
		            			validateFlag =true;
		            				//****************commented by sugandha
			            			if(rst.next())			                		{
			            				if(rst.getInt(1)>0)
			            				{
			            					validateFlag =false;
				            				distPinCodeMapErrDto = new DPDistPinCodeMapDto();
				            				distPinCodeMapErrDto.setErr_msg("Distributor Pincode Mapping already exists at row No: "+(rowNumber)+".");
				                  			list.add(distPinCodeMapErrDto);
			            					//multiple record found
			            				}
			            				else
				            			{
			            				// no record found
			            					validateFlag =true;
				            			}	
			            			}
		            			}
	          		}
		            		
	        		  }
	        		  else
	        		  {
	        			  validateFlag =false;
	        			  distPinCodeMapErrDto = new DPDistPinCodeMapDto(); 
	        			  distPinCodeMapErrDto.setErr_msg("File should contain only two data column at row No: "+(rowNumber)+".");
              			 list.add(distPinCodeMapErrDto);
              			 return list;
	        		  }
	        		  
	    			}
	            	
	    			if(cellNo !=2){
		            		validateFlag =false;
		            		distPinCodeMapErrDto = new DPDistPinCodeMapDto(); 
		            		distPinCodeMapErrDto.setErr_msg("File should contain two data Headers");
		            		List list2 = new ArrayList();
		            		list2.add(distPinCodeMapErrDto);
	              			return list2;
		            	}
	    			

	            	
	        	  rowNumber++;
	            }
	              
	            else
	            {
	            	distPinCodeMapErrDto = new DPDistPinCodeMapDto();
	            	distPinCodeMapErrDto.setErr_msg("File should contain only two data column");
         			list.add(distPinCodeMapErrDto);
         			return list;
	            }
 	             // logger.info("In Row num == "+ (rowNumber-1) +" Error Message ==  ");
	        	  if(validateFlag)
	        	  {
	        		  mapDto.setErr_msg("SUCCESS");
	        		  list.add(mapDto);  		        		  
	        	  }
	             
	        	  }
	        	  
	              else
	        	  {
	        		  logger.info("*************Header checking*************");
	  	        	//For Header Checking  
	  	        	int columnIndex = 0;
	  	              int cellNo = 0;
	  	              if(cells != null)
	  	              {
	  	            	  while (cells.hasNext()) {
	  		        		  if(cellNo < 2)
	  		        		  {
	  		        			  cellNo++;
	  		        			  HSSFCell cell = (HSSFCell) cells.next();
	  		        			  
	  		        			  	columnIndex = cell.getColumnIndex();
	  			              		if(columnIndex > 3)
	  			              		{
	  			              			validateFlag =false;
	  			              		distPinCodeMapErrDto = new DPDistPinCodeMapDto();
	  			              	distPinCodeMapErrDto.setErr_msg("File should contain only two data column");
	  			              			List list2 = new ArrayList();
	  				            		list2.add(distPinCodeMapErrDto);
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
	  			            		if(cellValue ==  null || "".equalsIgnoreCase(cellValue.trim()))
	  			            		{
	  			            			validateFlag =false;
	  			            			distPinCodeMapErrDto = new DPDistPinCodeMapDto();
	  			            			distPinCodeMapErrDto.setErr_msg("File should contain All Required Values");
	  			              			List list2 = new ArrayList();
	  				            		list2.add(distPinCodeMapErrDto);
	  			              			return list2;
	  			               		}
	  			            		
	  			            	//	logger.info("Row Number == "+rowNumber +" and Cell No. === "+cellNo +" and value == "+cellValue.trim().toLowerCase() );
	  			            		if(cellNo==1){
	  			            			if(!"distributor olmid".equals(cellValue.trim().toLowerCase()))
	  				            		{
	  			            				validateFlag =false;
	  			            				distPinCodeMapErrDto = new DPDistPinCodeMapDto();
	  			            				distPinCodeMapErrDto.setErr_msg("Invalid Header For Distributor OLM ID");
	  			            			//	logger.info("Invalid Header For Distributor OLM ID");
	  				              			list.add(distPinCodeMapErrDto);
	  			            				
	  			            			}
	  			            		}else if(cellNo==2){
	  			            			if(!"pincode".equals(cellValue.trim().toLowerCase()))
	  				            		{
	  			            				validateFlag =false;
	  			            				distPinCodeMapErrDto = new DPDistPinCodeMapDto();
	  			            				distPinCodeMapErrDto.setErr_msg("Invalid Header For  PinCode");
	  			            			//	logger.info("Invalid Header For  PinCode");
	  				              			list.add(distPinCodeMapErrDto);
	  			            				
	  			            			}
	  			            		}
	  		        		  }else{
	  		        			  
	  		        			  validateFlag =false;
	  		        			distPinCodeMapErrDto = new DPDistPinCodeMapDto();
	  		        			distPinCodeMapErrDto.setErr_msg("File should contain only two data Headers");
	  		        		//	logger.info("File should contain only two data Headers");
	  		        			  List list2 = new ArrayList();
	  			            	list2.add(distPinCodeMapErrDto);
	  		              		return list2;
	  		        		  }
	  	            	  }
	  	            	  if(cellNo !=2){
	  		            		validateFlag =false;
	  		            		distPinCodeMapErrDto = new DPDistPinCodeMapDto(); 
	  		            		distPinCodeMapErrDto.setErr_msg("File should contain two data Headers");
	  		            		List list2 = new ArrayList();
	  		            		list2.add(distPinCodeMapErrDto);
	  	              			return list2;
	  		            	}
	  	              } else
	  		            {
	  	            	distPinCodeMapErrDto = new DPDistPinCodeMapDto(); 
	  	            	distPinCodeMapErrDto.setErr_msg("File should contain two data column");
	  		            	  List list2 = new ArrayList();
	  			            	list2.add(distPinCodeMapErrDto);
	  		              		return list2;
	  		            }
	  	        	rowNumber ++;
	  	         }  
	        	  k++;
	              
	        }
	          
	    }
		catch (IOException ioe) {
        	
        	//logger.info(ioe);
        	throw new DAOException("Please Upload a valid Excel Format File");
        }
		catch (Exception e) {
        	
        	//logger.info(e);
        	throw new DAOException("Please Upload a valid Excel Format File");
        }finally
		{
        		DBConnectionManager.releaseResources(null, rst);
        					
		}
		return list;
	
	}
	
	public boolean validateOlmId(String str)
	{
		//logger.info("check olmid is only of eight character");
		if(str.length() != 8)
		{
			return false;
		}
		return true;
	}
	
	public boolean validateOlmdistId(String distOlmId)
	{
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		boolean flag = false;
		try{
			//logger.info("inside --- validateOlmId dist OLM id == "+distOlmId);
			pstmt = connection.prepareStatement(SQL_SELECT_DIST_CHK_EXISTS);
			pstmt.setString(1,distOlmId);
			rs =pstmt.executeQuery();
			if(rs.next())
			{
				flag =  true;
			}
			else
			{
				flag =  false;
			}
			
		}catch (Exception e) 
		{
			e.printStackTrace();
			//logger.info("Inside main Exception of validateDistOlmId method--"+e.getMessage());
		}
		return flag;
	}
	
	public  boolean validatePinCodeIsNumeric(String str)  
	{  
	  try  
	  {  
	    double d = Double.parseDouble(str);  
	  }  
	  catch(NumberFormatException nfe)  
	  {  
	    return false;  
	  } 
	  if(str.length()==6 && str.charAt(0) != 0)
	  { 
		  //logger.info("pincode size:::::"+str.length()+"first digit of pincode:::: "+str.charAt(0));
	  return true;  
	  }
	  else
	  return false;
	}
	
	public ResultSet validateUniqueDistPinCode(String distOlmId,String pinCode)
	{
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		try
		{
			
			logger.info("inside --- validateUniqueDistPinCode   == "+distOlmId +" and pincode == "+pinCode);
			pstmt = connection.prepareStatement(SQL_SELECT_VALIDATE_UNIQUE_DISTPINMAP);
			pstmt.setString(1,distOlmId);
			pstmt.setString(2,pinCode.toUpperCase());
			rs =pstmt.executeQuery();
		  }
		catch (Exception e) 
		{
			e.printStackTrace();
			logger.info("Inside main Exception of validateUniqueDistPinCode method--"+e.getMessage());
		}
		return rs;
	}

	public String addDistPinCodeMap(List list) throws DAOException {

			PreparedStatement pstmt = null;
			PreparedStatement pstmt1 = null;
			PreparedStatement pstmt2 = null;
			ResultSet rst=null;
			String returnstr="";
			logger.info("Size--"+list.size());
			String olmId ="";
			String pinCode="" ;
			DPDistPinCodeMapDto mapDto = null;
		try
		{	
			System.out.println("calling ********* addDistPinCodeMap ************");
			pstmt = connection.prepareStatement(SQL_CHK_PINCODE);
			pstmt1 = connection.prepareStatement(SQL_UPDATE_DP_DIST_PINCODE);
			pstmt2 = connection.prepareStatement(SQL_INSERT_DP_DIST_MAP);
			Iterator itr = list.iterator();
			int updaterows = 0;
			
			while(itr.hasNext())
			{
				mapDto = (DPDistPinCodeMapDto) itr.next();
				
				pstmt.setString(1, mapDto.getPincode());
				logger.info("******SQL_CHK_PINCODE******"+pstmt);
				logger.info("calling addDistPinCodeMap **select**pinCode********"+mapDto.getPincode());
				rst = pstmt.executeQuery();
				if(rst.next())
				{
					if(rst.getInt(1)>=1){
						
						olmId = mapDto.getStrDistOLMIds();
						pinCode = mapDto.getPincode();
						logger.info("calling addDistPinCodeMap **update**olmId********"+olmId);
						logger.info("calling addDistPinCodeMap ****pinCode********"+pinCode);
						pstmt1.setString(1, olmId);
						pstmt1.setString(2, pinCode);
						logger.info("******SQL_UPDATE_DP_DIST_PINCODE******"+pstmt1);
						pstmt1.executeUpdate();
					
					}
						
					else{
						
						pinCode = mapDto.getPincode();
						olmId = mapDto.getStrDistOLMIds();
						logger.info("calling addDistPinCodeMap **insert**pinCode********"+pinCode);
						logger.info("calling addDistPinCodeMap ****olmId********"+olmId);
						pstmt2.setString(1, olmId);
						pstmt2.setString(2, pinCode);
						logger.info("******SQL_INSERT_DP_DIST_MAP******"+pstmt2);
						pstmt2.executeUpdate();			
						
					}
				}
				updaterows++;
			}
			returnstr = "Total "+updaterows+" Records have been Uploaded Successfully";
			
			
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
			try {
				if (rst != null)
					rst.close();
				if (pstmt != null)
					pstmt.close();
				if (pstmt1 != null)
					pstmt1.close();
				if (pstmt2 != null)
					pstmt2.close();
				
			} catch (Exception e) {
			}
			DBConnectionManager.releaseResources(pstmt, rst);
			DBConnectionManager.releaseResources(pstmt1, rst);
			DBConnectionManager.releaseResources(pstmt2, rst);
			
		}
			
			return returnstr;
		
		}	

	
	/*	public String addDistPinCodeMap(List list) throws DAOException
		{
			PreparedStatement pstmt = null;
			PreparedStatement pstmt1 = null;
			PreparedStatement pstmt2 = null;
			PreparedStatement pstmt3 = null;
			ResultSet rst=null;
			String olmId ="";
			String pinCode="";
			//logger.info("Size--"+list.size());
			String returnstr="";
			//DPDistPinCodeMapDto mapDto = null;
		try
		{	
			//logger.info("----->> deleting from DP_DIST_PINCODE_MAP_HIST ");
			 pstmt= connection.prepareStatement("DELETE FROM DP_DIST_PINCODE_MAP_HIST");
			 pstmt.executeUpdate();
			// logger.info("----->> moving data from DP_DIST_PINCODE_MAP to-------->DP_DIST_PINCODE_MAP_HIST ");
			 pstmt1= connection.prepareStatement("INSERT INTO  DPDTH.DP_DIST_PINCODE_MAP_HIST SELECT DIST_OLM_ID,PINCODE FROM DPDTH.DP_DIST_PINCODE_MAP");
			 pstmt1.executeUpdate();
			// logger.info("--------------->>deleting the data from DP_DIST_PINCODE_MAP");
			 pstmt2=connection.prepareStatement("DELETE FROM DP_DIST_PINCODE_MAP");
			 pstmt2.executeUpdate();
			Iterator itr = list.iterator();
			int updaterows = 0;
			while(itr.hasNext())
			{
				DPDistPinCodeMapDto mapDto = null;
				mapDto = (DPDistPinCodeMapDto) itr.next();
				// logger.info("----------------->>Inserting new dist pincode map into the table DP_DIST_PINCODE_MAP------------->");
				 olmId = mapDto.getStrDistOLMIds();
				 pinCode = mapDto.getPincode();
				// logger.info("pincodevalues::::"+pinCode);
				// logger.info("calling addDistPinCodeMap ****olmId********"+olmId);
				 pstmt3=connection.prepareStatement("INSERT INTO DP_DIST_PINCODE_MAP(DIST_OLM_ID, PINCODE) VALUES(?, ?)");
				 pstmt3.setString(1, olmId);
				 pstmt3.setString(2, pinCode);
				 pstmt3.executeUpdate();		
				 updaterows++;
			}
			returnstr = "Total "+updaterows+" Records have been Uploaded Successfully";
		}
		catch (Exception e) 
		{
			try
			{
				e.printStackTrace();
				connection.rollback();
				//logger.info("Inside main Exception");
				return "Internal Error Occured.Please try later";
			}
			catch(Exception ex)
			{
				logger.error(ex);
			}
		}
		finally
		{
			try {
				if (rst != null)
					rst.close();
				if (pstmt != null)
					pstmt.close();
				if (pstmt1 != null)
					pstmt1.close();
				
			} catch (Exception e) {
			}
			DBConnectionManager.releaseResources(pstmt, rst);
			DBConnectionManager.releaseResources(pstmt1, rst);
			DBConnectionManager.releaseResources(pstmt2, rst);
			DBConnectionManager.releaseResources(pstmt3, rst);
			
		}
		return 	returnstr;
		}
		*/
}

