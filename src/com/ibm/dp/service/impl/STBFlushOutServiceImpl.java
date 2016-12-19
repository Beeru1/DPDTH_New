package com.ibm.dp.service.impl;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.sql.Connection;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.StringTokenizer;

import org.apache.log4j.Logger;

import com.ibm.dp.beans.StbFlushOutFormBean;
import com.ibm.dp.common.Constants;
import com.ibm.dp.dao.STBFlushOutDao;
import com.ibm.dp.dto.DuplicateSTBDTO;
import com.ibm.dp.exception.DPServiceException;
import com.ibm.dp.service.STBFlushOutService;
import com.ibm.utilities.ValidatorUtility;
import com.ibm.virtualization.recharge.common.ResourceReader;
import com.ibm.virtualization.recharge.dao.DAOFactory;
import com.ibm.virtualization.recharge.db.DBConnectionManager;

public class STBFlushOutServiceImpl implements STBFlushOutService
{
	
	Logger logger = Logger.getLogger(STBFlushOutServiceImpl.class.getName());
	public StbFlushOutFormBean getSTBList(StbFlushOutFormBean stbFlushOutFormBean)throws DPServiceException
	{
			java.sql.Connection connection = null;
			ArrayList<DuplicateSTBDTO> errorSTBList=null;
			String stbSLNos="";
			try
			{
				connection = DBConnectionManager.getDBConnection();
				STBFlushOutDao stbFlushOutDao = DAOFactory.getDAOFactory(Integer.parseInt(ResourceReader
												.getCoreResourceBundleValue(Constants.DATABASE_TYPE))).getSTBListDao(connection);
				
				InputStream inputstream = stbFlushOutFormBean.getStbSearchFile().getInputStream();
			      ByteArrayOutputStream baos = new ByteArrayOutputStream();
		          int size = (int) stbFlushOutFormBean.getStbSearchFile().getFileSize();
		          byte buffer[] = new byte[size];
		          int bytesRead = 0;
		          String data="";
                  // read the input file
		          while( (bytesRead = inputstream.read(buffer, 0, size)) != -1 )
		          {
		        	  baos.write( buffer, 0, bytesRead );
		          }
		          data = new String( baos.toByteArray() );
		          String newdata = data.replace(",", ",");
		          StringTokenizer st = new StringTokenizer(newdata, "");
		          String serialNo="";
		          while (st.hasMoreTokens())
		          {
		        	  serialNo = st.nextToken(",\n\r").toString().trim();	
		        	  boolean validate = validateCell(serialNo);
	            		if(!validate)
	            		{
	            			logger.info("Invalid Serial Number : "+serialNo+". It should be 11 digit number.");
	            		}
	            		else
	            		{			            			
	            			stbSLNos = stbSLNos + ",'"+serialNo+"'";
	            		}
		          };
		        
				stbSLNos=stbSLNos.replaceFirst(",", "");
				System.out.println("stbSLNos : "+stbSLNos);
				if(!"".equals(stbSLNos))
				{
					errorSTBList = stbFlushOutDao.getSTBList(stbSLNos);
					stbFlushOutFormBean.setFreshSTBList(errorSTBList);
					stbFlushOutFormBean.setFileEmptyFlag(false);
				}
			}
			catch(Exception e)
			{
				e.printStackTrace();
				logger.error("***********Exception occured while fetching initial data for STBList : "+e.getMessage());
				throw new DPServiceException(e.getMessage());
			}
			finally
			{
				//Releasing the database connection
				DBConnectionManager.releaseResources(connection);
			}
			return stbFlushOutFormBean;
		}
	
	public boolean validateCell(String str)
	{
		if(str.length() != Constants.C2S_BULK_UPLOAD_NO_LENGTH)
		{
			return false;
		}
		else if(!ValidatorUtility.isValidNumber(str))
		{
			return false;
		}
		return true;
	}
	
		
	public Map uploadFreshExcel(InputStream inputstream) throws DPServiceException
	{
	    	Map returnMap = null;
			
			Connection connection = null;
			STBFlushOutDao bulkupload = null; 
			try
			{
				connection = DBConnectionManager.getDBConnection();
				connection.setAutoCommit(false);
				bulkupload = DAOFactory.getDAOFactory(Integer.parseInt(ResourceReader.getCoreResourceBundleValue(Constants.DATABASE_TYPE)))
															.getuploadFreshExcelDao(connection);
				returnMap = bulkupload.uploadFreshExcel(inputstream);
				connection.commit();
				connection.setAutoCommit(true);
			}
			catch (Exception e) 
			{
				try
				{
					connection.rollback();
					
				}
				catch(Exception ex)
				{
					logger.error("***********Exception occured while uploadExcel service impl************"+ex.getMessage());
				}
				e.printStackTrace();
				logger.error("***********Exception occured while fetching uploadExcel************"+e.getMessage());
				throw new DPServiceException(e.getMessage());
			}
			finally
			{						
				DBConnectionManager.releaseResources(connection);
			}
			return returnMap;
		}

	/*public String flushDPSerialNumbers(List<DuplicateSTBDTO> uploadList,List<DuplicateSTBDTO> queryList) throws Exception 
	{
			String status="";
			Connection connection = null;
			STBFlushOutDao bulkupload = null; 
			try
			{
				logger.info("asa::::::::;;stbflushoutserviceimpl:::::::::flushDPSerialNumbers");
				connection = DBConnectionManager.getDBConnection();
				connection.setAutoCommit(false);
				bulkupload = DAOFactory.getDAOFactory(Integer.parseInt(ResourceReader.getCoreResourceBundleValue(Constants.DATABASE_TYPE)))
															.getFlushDPSerialNumbersDao(connection);
				status=bulkupload.flushDPSerialNumbers(uploadList,queryList);
				connection.commit();
				//connection.setAutoCommit(true);
			}
			catch (Exception e) 
			{
				try
				{
					connection.rollback();
				}
				catch(Exception ex)
				{
					logger.error("***********Exception occured while uploadExcel service impl************"+ex.getMessage());
				}
				e.printStackTrace();
				logger.error("***********Exception occured while fetching uploadExcel************"+e.getMessage());
				throw new DPServiceException(e.getMessage());
			}
			finally
			{			
				DBConnectionManager.releaseResources(connection);
			}
			return status;
		}*/
	//added by aman
	public String flushDPSerialNumbers(List<DuplicateSTBDTO> uploadList,List<DuplicateSTBDTO> queryList, String loginUserId) throws Exception 
	{
			String status="";
			Connection connection = null;
			STBFlushOutDao bulkupload = null; 
			try
			{
				logger.info("asa::::::::;;stbflushoutserviceimpl:::::::::flushDPSerialNumbers");
				connection = DBConnectionManager.getDBConnection();
				connection.setAutoCommit(false);
				bulkupload = DAOFactory.getDAOFactory(Integer.parseInt(ResourceReader.getCoreResourceBundleValue(Constants.DATABASE_TYPE)))
															.getFlushDPSerialNumbersDao(connection);
				//status=bulkupload.flushDPSerialNumbers(uploadList,queryList);
				status=bulkupload.flushDPSerialNumbers(uploadList,queryList,loginUserId);
				connection.commit();
				//connection.setAutoCommit(true);
			}
			catch (Exception e) 
			{
				try
				{
					connection.rollback();
				}
				catch(Exception ex)
				{
					logger.error("***********Exception occured while uploadExcel service impl************"+ex.getMessage());
				}
				e.printStackTrace();
				logger.error("***********Exception occured while fetching uploadExcel************"+e.getMessage());
				throw new DPServiceException(e.getMessage());
			}
			finally
			{			
				DBConnectionManager.releaseResources(connection);
			}
			return status;
		}
}
