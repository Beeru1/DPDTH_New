/**
 * 
 */
package com.ibm.dp.dao.impl;

import java.io.InputStream;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.apache.log4j.Logger;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.struts.action.ActionError;

import com.ibm.dp.common.DBQueries;
import com.ibm.dp.dao.DPCreateAccountITHelpDao;
import com.ibm.dp.dao.RetailerLapuDataDao;
import com.ibm.dp.dto.DPCreateAccountDto;
import com.ibm.dp.dto.RetailerLapuDataDto;
import com.ibm.virtualization.recharge.common.Constants;
import com.ibm.virtualization.recharge.common.ExceptionCode;
import com.ibm.virtualization.recharge.common.ResourceReader;
import com.ibm.virtualization.recharge.dao.DAOFactory;
import com.ibm.virtualization.recharge.dao.rdbms.BaseDaoRdbms;
import com.ibm.virtualization.recharge.db.DBConnectionManager;
import com.ibm.virtualization.recharge.exception.DAOException;
import com.ibm.virtualization.recharge.exception.VirtualizationServiceException;

/**
 * @author Nehil Parashar
 *
 */
public class RetailerLapuDataDaoImpl extends BaseDaoRdbms implements RetailerLapuDataDao 
{
	public static Logger logger = Logger.getLogger(RetailerLapuDataDaoImpl.class.getName());

	public RetailerLapuDataDaoImpl(Connection connection) {
		super(connection);
	}

	public static final String SQL_RETAILER_LAPU_DATA_LIST = DBQueries.SQL_RETAILER_LAPU_DATA_LIST;
	public static final String SQL_UPDATE_LAPU_NUMBERS = DBQueries.SQL_UPDATE_LAPU_NUMBERS;
	public static final String SQL_UPDATE_LAPU_NUMBERS_STATUS = DBQueries.SQL_UPDATE_LAPU_NUMBERS_STATUS;
	public static final String SQL_INSERT_ACC_DETAIL_HIST_IT_HELP_RET = DBQueries.SQL_INSERT_ACC_DETAIL_HIST_IT_HELP_RET;

	/**
	 * 
	 */
	public List<RetailerLapuDataDto> getAllRetailerLapuData() throws DAOException
	{
		List<RetailerLapuDataDto> retailerLapuData	= new ArrayList<RetailerLapuDataDto>();

		PreparedStatement pstmt = null;
		ResultSet rs = null;
		RetailerLapuDataDto lapuDataDto = null;

		try 
		{
			pstmt = connection.prepareStatement(SQL_RETAILER_LAPU_DATA_LIST);
			rs = pstmt.executeQuery();
			while (rs.next()) 
			{
				lapuDataDto = new RetailerLapuDataDto();
				
				lapuDataDto.setLoginId(rs.getString("LOGIN_NAME"));
				lapuDataDto.setAccountName(rs.getString("ACCOUNT_NAME"));
				lapuDataDto.setLapuNumber(rs.getString("RETAILER_LAPU"));
				lapuDataDto.setMobile1(rs.getString("Mobile1"));
				lapuDataDto.setMobile2(rs.getString("Mobile2"));
				lapuDataDto.setMobile3(rs.getString("Mobile3"));
				lapuDataDto.setMobile4(rs.getString("Mobile4"));
				
				retailerLapuData.add(lapuDataDto);
			}
		}
		catch (Exception e)
		{
			e.printStackTrace();
			throw new DAOException(e.getMessage());
		} 
		finally 
		{
			DBConnectionManager.releaseResources(pstmt, rs);
		}
		return retailerLapuData;
	}


	private boolean validateCellValue(String cellValue)
	{
		try{
			//System.out.println("1."+cellValue);
		if(cellValue != null && cellValue.trim().length()>0 && cellValue.trim().length() != 10)
			return false;
		if(cellValue != null && cellValue.trim().length()>0) {
		for(char ch: cellValue.toCharArray())
		{
			if(!Character.isDigit(ch))
			{
				System.out.println("not char");
				return false;
			}
			
		}
		
		if(!(isMobileExistForDepthThree(cellValue)))
		{
			System.out.println("not in lapu");
			return false;
		}
		}
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		
		return true;
	}
	private boolean validateCellValueOthers(String cellValue)
	{
		try{
			//System.out.println("1."+cellValue);
		if(cellValue != null && cellValue.trim().length()>0 && cellValue.trim().length() != 10)
			return false;
		if(cellValue != null && cellValue.trim().length()>0) {
		for(char ch: cellValue.toCharArray())
		{
			if(!Character.isDigit(ch))
			{
				System.out.println("not char");
				return false;
			}
			
		}
		
		}
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		
		return true;
	}
	private boolean validateCellValueOthersLapu(String cellValue)
	{
		try{
			//System.out.println("1."+cellValue);
	
		
		if(isMobileExistForDepthThree(cellValue))
		{
			//System.out.println(" in lapu..");
			return false;
		}
		}
		
		catch (Exception e)
		{
			e.printStackTrace();
		}
		
		return true;
	}
	private long validateCellValueLoginID(String cellValue)
	{
		try{
			
			//System.out.println("1."+cellValue);
		if(cellValue == null && cellValue.trim().length()==0)
			return -1;
		if(cellValue != null && cellValue.trim().length()>0) {
		
			PreparedStatement pstmt  = null;
			ResultSet rs = null;
			try 
			{
				String strQuery = "SELECT LOGIN_ID from vr_login_master where LOGIN_NAME=? and Group_id=9 with ur";
				pstmt = connection.prepareStatement(strQuery);
				pstmt.setString(1, cellValue);
				rs = pstmt.executeQuery();
				if(rs.next())
				return rs.getLong("LOGIN_ID");
				else 
					return -1;
			}
			catch (Exception e) 
			{
				e.printStackTrace();
				throw new DAOException(ExceptionCode.ERROR_DB_INTERNAL);
			}
			finally
			{
				DBConnectionManager.releaseResources(pstmt, rs);
			}
		
		}
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		
		return -1;
	}
	public boolean isMobileExistForDepthThree(String mobileNo) throws DAOException
	{
		PreparedStatement pstmt  = null;
		ResultSet rs = null;
		try 
		{
			String strQuery = "SELECT DEPTH, AGENT FROM DP_HIERARCHY_REPORT WHERE DEPTH>=3  and status='ACTIVE' and AGENT=? WITH UR"; //not in ('1','2')
			pstmt = connection.prepareStatement(strQuery);
			pstmt.setString(1, mobileNo);
			rs = pstmt.executeQuery();
			
			return rs.next();
		}
		catch (Exception e) 
		{
			e.printStackTrace();
			throw new DAOException(ExceptionCode.ERROR_DB_INTERNAL);
		}
		finally
		{
			DBConnectionManager.releaseResources(pstmt, rs);
		}
	}
	
	public List<RetailerLapuDataDto> validateExcel(InputStream xls) throws DAOException
	{
		List<RetailerLapuDataDto> list = new ArrayList<RetailerLapuDataDto>();
		RetailerLapuDataDto retailerLapuDataDto = null;
		
		ResultSet rst =null;
		boolean validateFlag =true;
		boolean validateFlagFinal =true;
		try
		{
			HSSFWorkbook workbook = new HSSFWorkbook(xls);
			HSSFSheet sheet = workbook.getSheetAt(0);
			Iterator rows = sheet.rowIterator();
			int totalrows = sheet.getLastRowNum()+1;
			logger.info("Total Rows == "+sheet.getLastRowNum());
			  
			String rowsInWorkbook = ResourceReader.getValueFromBundle(Constants.BULK_LAPU_MAX_LIMIT , Constants.WEBSERVICE_RESOURCE_BUNDLE);
			
			if(totalrows > Integer.parseInt(rowsInWorkbook))
			{
				retailerLapuDataDto = new RetailerLapuDataDto();
				retailerLapuDataDto.setErr_msg("Limit exceeds: Maximum "+rowsInWorkbook+" Retailers are allowed in a file.");
	      		list.add(retailerLapuDataDto);
	      		return list;
			}
			  
			int rowNumber = 1;
	        boolean isEmptyFile = true;
	        DPCreateAccountITHelpDao accountDao = DAOFactory.getDAOFactory(Integer.parseInt(ResourceReader
					.getCoreResourceBundleValue(Constants.DATABASE_TYPE))).getNewAccountITHelpDao(connection);
	        List<String>  listLapus  =  new ArrayList<String>();
	        while (rows.hasNext())
	        {
	        	HSSFRow row = (HSSFRow) rows.next();
	        	Iterator cells = row.cellIterator();
	        	
	        	retailerLapuDataDto  = new RetailerLapuDataDto();
	        	
	        	if(rowNumber>1)
	        	{
	        		int columnIndex = 0;
	        		int cellNo = 0;
	        		long ret =-1;  
	        		if(cells != null)
	        		{
	        			while (cells.hasNext()) 
	        			{
	        				if(cellNo < 7)
	        				{
	        					cellNo++;
	        					HSSFCell cell = (HSSFCell) cells.next();
	        			  
	        					columnIndex = cell.getColumnIndex();
	        					if(columnIndex > 6)
	        					{
	        						validateFlag =false;
	        						retailerLapuDataDto = new RetailerLapuDataDto();
	        						retailerLapuDataDto.setErr_msg("File should contain only seven data column");
	        						list.add(retailerLapuDataDto);
	        						return list;
	        					}
	        					String cellValue = null;

	        					switch(cell.getCellType())
	        					{
	        						case HSSFCell.CELL_TYPE_NUMERIC:
	        					  		cellValue = String.valueOf((double)cell.getNumericCellValue());
	        					  		break;
	        					  	case HSSFCell.CELL_TYPE_STRING:
	        					  		cellValue = cell.getStringCellValue();
	        					  		break;
	        					}

	        				
	        			      					
	        					switch(cellNo)
	        					{
	        						case 1: 
	        						

	        						ret = validateCellValueLoginID(cellValue);
	        						logger.info("Row Number == "+rowNumber +" and Cell No. === "+cellNo +" and value == "+cellValue +"ret  = "+ret);
	        						if(ret<0)
	        							validateFlag=false;
	        						else
	        							validateFlag=true;
    					  			if(!validateFlag)
    					  			{
    					  				validateFlagFinal=false;
    					  				retailerLapuDataDto = new RetailerLapuDataDto();
    					  			
    					  				retailerLapuDataDto.setErr_msg("Retailer ID not proper at row number" + rowNumber);
    					  				list.add(retailerLapuDataDto);
    					  			//	return list;	
    					  			}
    					  			else
    					  			{
    					  				retailerLapuDataDto.setLoginId(cellValue); 
    					  				retailerLapuDataDto.setAccountid(ret);
    					  			}
	        						
	        						break;
	        						case 2: retailerLapuDataDto.setAccountName(cellValue);
	        						
    					  			break;
	        						case 3: //retailerLapuDataDto.setMobile1(cellValue); 
	        						

	        						validateFlag = validateCellValueOthers(cellValue);
    					  			if(!validateFlag)
    					  			{
    					  				validateFlagFinal=false;
    					  				retailerLapuDataDto = new RetailerLapuDataDto();
    					  				//System.out.println("Mobile 1 not proper");
    					  				retailerLapuDataDto.setErr_msg("Invalid  Mobile number 1 at row number " + rowNumber);
    					  				list.add(retailerLapuDataDto);
    					  			//	return list;	
    					  			}
    					  			validateFlag = validateCellValueOthersLapu(cellValue);
    					  			if(!validateFlag)
    					  			{
    					  				
    					  				validateFlagFinal=false;
    					  				retailerLapuDataDto = new RetailerLapuDataDto();
    					  				//System.out.println("Mobile 1 not proper");
    					  				retailerLapuDataDto.setErr_msg("Please don't feed Lapu number in Mobile number 1 at row number " + rowNumber);
    					  				list.add(retailerLapuDataDto);
    					  			//	return list;	
    					  			}
    					  			else
    					  			{
    					  			retailerLapuDataDto.setMobile1(cellValue);
    					  			}
	        						
    					  			
    					  			break;
	        					  	case 4: //retailerLapuDataDto.setMobile2(cellValue);
	        					  	
	        						validateFlag = validateCellValueOthers(cellValue);
    					  			if(!validateFlag)
    					  			{
    					  				validateFlagFinal=false;
    					  				retailerLapuDataDto = new RetailerLapuDataDto();
    					  				retailerLapuDataDto.setErr_msg("Invalid  Mobile number 2 at row number " + rowNumber);
    					  				list.add(retailerLapuDataDto);
    					  			//	return list;	
    					  			}
    					  			validateFlag = validateCellValueOthersLapu(cellValue);
    					  			if(!validateFlag)
    					  			{
    					  				
    					  				validateFlagFinal=false;
    					  				retailerLapuDataDto = new RetailerLapuDataDto();
    					  				//System.out.println("Mobile 1 not proper");
    					  				retailerLapuDataDto.setErr_msg("Please don't feed Lapu number in Mobile number 2 at row number " + rowNumber);
    					  				list.add(retailerLapuDataDto);
    					  			//	return list;	
    					  			}
    					  			else
    					  			{
    					  			retailerLapuDataDto.setMobile2(cellValue);
    					  			}
	        					  	break;
	        					  	case 5: //retailerLapuDataDto.setMobile3(cellValue); 
	        					  	
	        					  	validateFlag = validateCellValueOthers(cellValue);
    					  			if(!validateFlag)
    					  			{
    					  				validateFlagFinal=false;
    					  				retailerLapuDataDto = new RetailerLapuDataDto();
    					  				retailerLapuDataDto.setErr_msg("Invalid  Mobile number 3 at row number " + rowNumber);
    					  				list.add(retailerLapuDataDto);
    					  			//	return list;	
    					  			}
    					  			validateFlag = validateCellValueOthersLapu(cellValue);
    					  			if(!validateFlag)
    					  			{
    					  				
    					  				validateFlagFinal=false;
    					  				retailerLapuDataDto = new RetailerLapuDataDto();
    					  				//System.out.println("Mobile 1 not proper");
    					  				retailerLapuDataDto.setErr_msg("Please don't feed Lapu number in Mobile number 3 at row number " + rowNumber);
    					  				list.add(retailerLapuDataDto);
    					  			//	return list;	
    					  			}
    					  			else
    					  			{
    					  			retailerLapuDataDto.setMobile3(cellValue);
    					  			}
	        					  	break;
	        					  	case 6: //retailerLapuDataDto.setMobile4(cellValue); 
		        					  	//System.out.println("Mobile 4 == "+cellValue);
		        					 	validateFlag = validateCellValueOthers(cellValue);
	    					  			if(!validateFlag)
	    					  			{
	    					  				validateFlagFinal=false;
	    					  				retailerLapuDataDto = new RetailerLapuDataDto();
	    					  				retailerLapuDataDto.setErr_msg("Invalid  Mobile number 4 at row number " + rowNumber);
	    					  				list.add(retailerLapuDataDto);
	    					  			//	return list;	
	    					  			}
	    					  			validateFlag = validateCellValueOthersLapu(cellValue);
	    					  			if(!validateFlag)
	    					  			{
	    					  				
	    					  				validateFlagFinal=false;
	    					  				retailerLapuDataDto = new RetailerLapuDataDto();
	    					  				//System.out.println("Mobile 1 not proper");
	    					  				retailerLapuDataDto.setErr_msg("Please don't feed Lapu number in Mobile number 4 at row number " + rowNumber);
	    					  				list.add(retailerLapuDataDto);
	    					  			//	return list;	
	    					  			}
	    					  			else
	    					  			{
	    					  			retailerLapuDataDto.setMobile4(cellValue);
	    					  			}
		        					  	break;
	        					  	case 7: 
	        					  		validateFlag = validateCellValue(cellValue);
		        					  		if(!validateFlag)
		        					  		{
		        					  				validateFlagFinal=false;
		        					  				retailerLapuDataDto = new RetailerLapuDataDto();
		        					  				retailerLapuDataDto.setErr_msg("Invalid Lapu Mobile number at row number " + rowNumber);
		        					  				list.add(retailerLapuDataDto);
		        					  			//	return list;	
		        					  		}
		        					  		if(validateFlag)
		        					  		{
		        					  		
		        					  			if(cellValue!=null && !cellValue.equalsIgnoreCase("") && listLapus.contains(cellValue))
		        					  			{
		        					  				validateFlag=false;
			        					  			validateFlagFinal=false;
		        					  				retailerLapuDataDto = new RetailerLapuDataDto();
		        					  				retailerLapuDataDto.setErr_msg("You have put same Lapu Number in multiple Accounts at row " +rowNumber);
		        					  				list.add(retailerLapuDataDto);
		        					  			}
		        					  			else if(cellValue!=null && !cellValue.equalsIgnoreCase(""))
			        					  			listLapus.add(cellValue);
		        					  			
		        					  		}
		        					  		if(validateFlag)
		        					  		
	        					  			{
		        					  		//System.out.println("cellValue="+cellValue);
	        					  			retailerLapuDataDto.setLapuNumber(cellValue);
	        					  			
	        					  			}
	        					  			break;
	        					  			
	        					  			
	        					  			
	        					  }
	        					
	        					  
	        			      
					  			
	        				}
	        				else
	        				{
	        					validateFlag =false;
	        					retailerLapuDataDto = new RetailerLapuDataDto();; 
	        					retailerLapuDataDto.setErr_msg("File should contain only seven data column");
	        					list.add(retailerLapuDataDto);
	        					return list;
	        				}
	        				  
	        					
					  			
	        			}
	        			String flag = accountDao.checkAllReatilerMobileNumberAlreadyExistEditMode(String.valueOf(ret), retailerLapuDataDto.getLapuNumber(), retailerLapuDataDto.getMobile1(), retailerLapuDataDto.getMobile2(), retailerLapuDataDto.getMobile3(), retailerLapuDataDto.getMobile4());
    					//System.out.println(ret+" is account id "+retailerLapuDataDto.getLapuNumber());
	        			if(flag.equals("MOBILE_FLAG"))
    					{
    						  validateFlag =false;
    						  validateFlagFinal=false;
    						  retailerLapuDataDto = new RetailerLapuDataDto(); 
    						  retailerLapuDataDto.setErr_msg("Retailer Lapu Already Exists in another account at row number "+rowNumber);
    						  list.add(retailerLapuDataDto);
    						 // return list;
    					}
    					else if(flag.equals("LAPU_FLAG"))
    					{
    						  validateFlag =false;
    						  validateFlagFinal=false;
    						  retailerLapuDataDto = new RetailerLapuDataDto(); 
    						  retailerLapuDataDto.setErr_msg(" Mobile 1 Already Exists in another account at row number "+rowNumber);
    						  list.add(retailerLapuDataDto);
    						  //return list;
    					}
    					else if(flag.equals("LAPU_FLAG2"))
    					{
    						  validateFlag =false;
    						  validateFlagFinal=false;
    						  retailerLapuDataDto = new RetailerLapuDataDto(); 
    						  retailerLapuDataDto.setErr_msg(" Mobile 2 Already Exists in another account at row number "+rowNumber);
    						  list.add(retailerLapuDataDto);
    						 // return list;
    					}
    					else if(flag.equals("FTA_FLAG"))
    					{
    						  validateFlag =false;
    						  validateFlagFinal=false;
    						  retailerLapuDataDto = new RetailerLapuDataDto(); 
    						  retailerLapuDataDto.setErr_msg("Mobile 3 Already Exists in another account at row number "+rowNumber);
    						  list.add(retailerLapuDataDto);
    						  //return list;
    					}
    					else if(flag.equals("FTA_FLAG2"))
    					{
    						  validateFlag =false;
    						  validateFlagFinal=false;
    						  retailerLapuDataDto = new RetailerLapuDataDto(); 
    						  retailerLapuDataDto.setErr_msg(" Mobile 4 Already Exists in another account at row number "+rowNumber);
    						  list.add(retailerLapuDataDto);
    						 // return list;			
    					}
	        			if(cellNo !=7)
	        			{
	        				validateFlag =false;
	        				retailerLapuDataDto = new RetailerLapuDataDto(); 
	        				retailerLapuDataDto.setErr_msg("File should contain seven data column");
	        				list.add(retailerLapuDataDto);
	        				return list;
	        			}
	        			//System.out.println("Moving to nexr row");
	        			rowNumber++;
	        		}
	        		else
	        		{
	        			retailerLapuDataDto = new RetailerLapuDataDto();
	        			retailerLapuDataDto.setErr_msg("File should contain only seven data column");
	        			list.add(retailerLapuDataDto);
	        			return list;
	        		}
	        		logger.info("In Row num == "+ (rowNumber-1) +" Error Message ==  "+validateFlag);
	        		if(validateFlagFinal)
	        		{
	        			//retailerLapuDataDto = new RetailerLapuDataDto();
	        			retailerLapuDataDto.setErr_msg("SUCCESS");
	        			list.add(retailerLapuDataDto);
	        		}
	        		//else
	        			// list.add(retailerLapuDataDto);
	        	}
	        	else
	        	{
	        		  //For Header Checking  
	        		  int columnIndex = 0;
	        		  int cellNo = 0;
	        		  if(cells != null)
	        		  {
	        			  while (cells.hasNext()) 
	        			  {
	        				  if(cellNo < 7)
	        				  {
	        					  cellNo++;
	        					  HSSFCell cell = (HSSFCell) cells.next();
		        			  
	        					  columnIndex = cell.getColumnIndex();
	        					  
	        					  if(columnIndex > 6)
	        					  {
	        						  validateFlag =false;
	        						  retailerLapuDataDto = new RetailerLapuDataDto();
	        						  retailerLapuDataDto.setErr_msg("File should contain only seven data column");
	        						  list.add(retailerLapuDataDto);
	        						  return list;
	        					  }
	        					  
	        					  String cellValue = null;
			        		  
	        					  switch(cell.getCellType()) 
	        					  {
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
	        						  retailerLapuDataDto = new RetailerLapuDataDto();
	        						  retailerLapuDataDto.setErr_msg("File should contain All Required Values");
	        						  list.add(retailerLapuDataDto);
	        						  return list;
	        					  }
			            		
	        					  logger.info("Row Number == "+rowNumber +" and Cell No. === "+cellNo +" and value == "+cellValue.trim().toLowerCase() );
	        					  
	        					  switch(cellNo)
	        					  {
	        					  	case 1:
	        					  		if(!cellValue.trim().equalsIgnoreCase("Login ID"))
	        					  		{
	        					  			validateFlag =false;
		        							retailerLapuDataDto = new RetailerLapuDataDto(); 
		        							retailerLapuDataDto.setErr_msg("Invalid Header For Login ID");
		        							list.add(retailerLapuDataDto);
		        							return list;
	        					  		}
	        					  		break;
	        					  	case 2:
	        					  		if(!cellValue.trim().equalsIgnoreCase("Account Name"))
	        					  		{
	        					  			validateFlag =false;
		        							retailerLapuDataDto = new RetailerLapuDataDto(); 
		        							retailerLapuDataDto.setErr_msg("Invalid Header For Account Name");
		        							list.add(retailerLapuDataDto);
		        							return list;
	        					  		}
	        					  		break;
	        					  	case 3:
	        					  		if(!cellValue.trim().equalsIgnoreCase("Mobile 1"))
	        					  		{
	        					  			validateFlag =false;
		        							retailerLapuDataDto = new RetailerLapuDataDto(); 
		        							retailerLapuDataDto.setErr_msg("Invalid Header For Mobile 1");
		        							list.add(retailerLapuDataDto);
		        							return list;
	        					  		}
	        					  		break;
	        					  	case 4:
	        					  		if(!cellValue.trim().equalsIgnoreCase("Mobile 2"))
	        					  		{
	        					  			validateFlag =false;
		        							retailerLapuDataDto = new RetailerLapuDataDto(); 
		        							retailerLapuDataDto.setErr_msg("Invalid Header For Mobile 2");
		        							list.add(retailerLapuDataDto);
		        							return list;
	        					  		}
	        					  		break;
	        					  	case 5:
	        					  		if(!cellValue.trim().equalsIgnoreCase("Mobile 3"))
	        					  		{
	        					  			validateFlag =false;
		        							retailerLapuDataDto = new RetailerLapuDataDto(); 
		        							retailerLapuDataDto.setErr_msg("Invalid Header For Mobile 3");
		        							list.add(retailerLapuDataDto);
		        							return list;
	        					  		}
	        					  		break;
	        					  	case 6: 
	        					  		if(!cellValue.trim().equalsIgnoreCase("Mobile 4"))
	        					  		{
	        					  			validateFlag =false;
		        							retailerLapuDataDto = new RetailerLapuDataDto(); 
		        							retailerLapuDataDto.setErr_msg("Invalid Header For Mobile 4");
		        							list.add(retailerLapuDataDto);
		        							return list;
	        					  		}
	        					  		break;
	        					  	case 7:	
	        					  		if(!cellValue.trim().equalsIgnoreCase("Lapu Mobile Number"))
	        					  		{
	        					  			validateFlag =false;
		        							retailerLapuDataDto = new RetailerLapuDataDto(); 
		        							retailerLapuDataDto.setErr_msg("Invalid Header For Lapu Mobile Number");
		        							list.add(retailerLapuDataDto);
		        							return list;
	        					  		}
	        					  		break;
	        					  }
	        				  }
	        				  else
	        				  {
	        					  validateFlag =false;
	        					  retailerLapuDataDto = new RetailerLapuDataDto(); 
	        					  retailerLapuDataDto.setErr_msg("File should contain only seven data Headers");
	        					  list.add(retailerLapuDataDto);
	        					  return list;
	        				  }
	        			  }
	        			  if(cellNo !=7)
	        			  {
	        				  validateFlag =false;
	        				  retailerLapuDataDto = new RetailerLapuDataDto(); 
	        				  retailerLapuDataDto.setErr_msg("File should contain seven data Headers");
	        				  list.add(retailerLapuDataDto);
	        				  return list;
	        			  }
	        		  } 
	        		  else
	        		  {
	        			  retailerLapuDataDto = new RetailerLapuDataDto();
	        			  retailerLapuDataDto.setErr_msg("File should contain seven data column");
		            	  list.add(retailerLapuDataDto);
		            	  return list;
	        		  }
	        		  rowNumber ++;
	        	  }
	        	
	        	  isEmptyFile = false;
	          }
	      
	        
	        	if(!validateFlagFinal)
	        	{
      
        			return list;
	        	}
	          if(isEmptyFile)
	          {
	        	  retailerLapuDataDto = new RetailerLapuDataDto();
	        	  retailerLapuDataDto.setErr_msg("Blank File can not be uploaded!!");
	        	  list.add(retailerLapuDataDto); 
	        	  return list;
	          }
	    }
		catch (Exception e) 
		{
			logger.info(e);
			e.printStackTrace();
        	throw new DAOException("Please Upload a valid Excel Format File");
        }
		finally
		{
			DBConnectionManager.releaseResources(null, rst);
		}
		return list;
	}

	
	/**
	 * 
	 * @param lapuData
	 * @return
	 * @throws DAOException
	 */
	public String updateLapuNumbers(List<RetailerLapuDataDto> lapuData) throws DAOException
	{
		PreparedStatement pstmt = null;
		PreparedStatement pstmtStatus = null;
		PreparedStatement ps = null;
		String returnstr="";
		logger.info("Size--"+lapuData.size());
		
		ResultSet rst=null;
		RetailerLapuDataDto retailerLapuDataDto = null;
		try
		{	
			Timestamp currentTime = new Timestamp(new java.util.Date().getTime());
			String sql=SQL_INSERT_ACC_DETAIL_HIST_IT_HELP_RET.replace("?2", "timestamp('"+currentTime+"')");
			pstmt = connection.prepareStatement(SQL_UPDATE_LAPU_NUMBERS);
			pstmtStatus= connection.prepareStatement(SQL_UPDATE_LAPU_NUMBERS_STATUS);
			ps = connection.prepareStatement(sql);
			Iterator itr = lapuData.iterator();
			int updaterows = 0;
			while(itr.hasNext())
			{
			
				
				retailerLapuDataDto = (RetailerLapuDataDto) itr.next();
				//logger.info("UPDATING LAPU NUMBERS"+retailerLapuDataDto.getAccountid());
				ps.setLong(1, retailerLapuDataDto.getAccountid());
				ps.executeUpdate();
				pstmt.setString(1, retailerLapuDataDto.getMobile1());
				pstmt.setString(2, retailerLapuDataDto.getMobile2());
				pstmt.setString(3, retailerLapuDataDto.getMobile3());
				pstmt.setString(4, retailerLapuDataDto.getMobile4());
				if(retailerLapuDataDto.getLapuNumber()==null)
					retailerLapuDataDto.setLapuNumber(""); //Lapu number is blank in all cases 
				pstmt.setString(5, retailerLapuDataDto.getLapuNumber());
				pstmt.setLong(6, retailerLapuDataDto.getAccountid());
				
				pstmt.executeUpdate();
				
				if(retailerLapuDataDto.getLapuNumber()==null || (retailerLapuDataDto.getLapuNumber()!=null && retailerLapuDataDto.getLapuNumber().trim().length()==0))
				{
					pstmtStatus.setString(1,"L");
					pstmtStatus.setLong(2,retailerLapuDataDto.getAccountid());
					pstmtStatus.executeUpdate();
				}
				else if(retailerLapuDataDto.getLapuNumber()!=null && retailerLapuDataDto.getLapuNumber().trim().length()>0)
				{
					pstmtStatus.setString(1,"A");
					pstmtStatus.setLong(2,retailerLapuDataDto.getAccountid());
					pstmtStatus.executeUpdate();
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
			DBConnectionManager.releaseResources(pstmt, rst);
			DBConnectionManager.releaseResources(ps, null);
			DBConnectionManager.releaseResources(pstmtStatus, null);
		}
		
		return returnstr;
	}
}
