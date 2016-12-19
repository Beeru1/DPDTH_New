package com.ibm.dp.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedHashMap;
import java.util.Map;

import org.apache.log4j.Logger;

import com.ibm.dp.common.Constants;
import com.ibm.dp.common.DBQueries;
import com.ibm.dp.dao.DistributorSTBMappingDao;
import com.ibm.virtualization.recharge.dao.rdbms.BaseDaoRdbms;
import com.ibm.virtualization.recharge.db.DBConnectionManager;
import com.ibm.virtualization.recharge.exception.DAOException;

public class DistributorSTBMappingDaoImpl extends BaseDaoRdbms implements
		DistributorSTBMappingDao {

	Logger logger = Logger.getLogger(DistributorSTBMappingDaoImpl.class
			.getName());

	public DistributorSTBMappingDaoImpl(Connection connection) {
		super(connection);
	}

	public String checkOLMId(String distOLMId) throws DAOException 
	{
		String distId = null;
		PreparedStatement pstmt = null;
		ResultSet rset = null;
		try 
		{
			pstmt = connection.prepareStatement(DBQueries.SQL_SELECT_DIST_ID);
			pstmt.setString(1, distOLMId.toUpperCase());
			rset = pstmt.executeQuery();
			
			if (rset.next()) 
			{
				int intDistID = rset.getInt("ACCOUNT_ID");
				
				distId = intDistID+"";
			}
			
			logger.info("distId == " + distId);
			
			//connection.commit();
		} 
		catch (Exception e) 
		{
			e.printStackTrace();

			logger.error("::::::::::::::::::: Error in DistributorSTBMappingDaoImpl in fun checkOLMId WebService -------->",e);

			distId = "-1";
			
			throw new DAOException(e.getMessage());
		} 
		finally 
		{
			DBConnectionManager.releaseResources(pstmt, rset);
		}
		return distId;
	}

	public String[] checkMapping(String distId, String stbNo, String distOLMId,int requestType,String productName) //Neetika
			throws DAOException {
		//String servMsg = "SUCCESS";
		String[] servMsg = new String[2];
		servMsg[0]="SUCCESS";
		servMsg[1]="";
		PreparedStatement pstmt = null;
		ResultSet rset = null;
		ResultSet rset1 = null;
		ResultSet rset2 = null;
		Integer status = -1;
		String dpDistId = "-1";
		Integer stbExist = 0;
		PreparedStatement pstmt2 = null;
		String accountId = "";
		String circle_id = "";
		String nserActivationStatus = "";
		String serActivationStatus = "";
		String nserInvChangeStatus = "";
		String serInvChangeStatus = "";
		boolean flag = false;
		boolean hdbFlag=false;
		
		//Neetika
		String prodname="";
		PreparedStatement pstmt3 = null;
		//Shantanu
		boolean isProductHDB=false;
		try {
			
			// Get Distributor's detail(Distributor ID, Circle ID) for the OLM ID. 
			// If OLM Id invalid - revert with error message "OLM ID Invalid" 
			pstmt2 = connection.prepareStatement(DBQueries.SELECT_DISTRIBUTOR_DETAILS);
			
			pstmt2.setString(1, distOLMId);
			rset = pstmt2.executeQuery();
			
			while (rset != null &&  rset.next()) 
			{
				accountId = rset.getString("account_id");
				circle_id = rset.getString("CIRCLE_ID");
				nserActivationStatus = rset.getString("NSER_ACTIVATION_STATUS");
				serActivationStatus = rset.getString("SER_ACTIVATION_STATUS");
				nserInvChangeStatus = rset.getString("INV_CHANGE_NSER_STATUS");
				serInvChangeStatus = rset.getString("INV_CHANGE_SER_STATUS");
				flag = true;
			}
			
			if (!flag) 
			{
				servMsg[0] ="Failure";
				servMsg[1] =com.ibm.dp.common.Constants.DIST_ID_NOT_VALID;
				return servMsg;
			//	If distributor is valid (Existing), get details of STB (STB status, Distributor ID) from Stock inventory table
			} 
			else 
			{
				pstmt = connection.prepareStatement(DBQueries.SQL_SELECT_STB_STATUS_WODAMAGED);
				
				pstmt.setString(1, stbNo);
				rset1 = pstmt.executeQuery();
				while (rset1 != null && rset1.next()) 
				{
					stbExist = 1;
					dpDistId = rset1.getString("DISTRIBUTOR_ID");
					
					//In case no restriction on serialized stock, return success with appropriate message restriction status value: "0" means enabled, "1" means disabled
					if( (requestType==1 && serActivationStatus.equals("1")) || (requestType==2 && serInvChangeStatus.equals("1")) )
					{
						servMsg[0] ="Success";
						servMsg[1] =com.ibm.dp.common.Constants.STB_SER_NO_REST ;
						return servMsg;
					}
					
					
					//If STB is existing, validate if Distributor ID for this STB is same as Distributor ID of OLM ID received.
					if (accountId.equals(dpDistId)) 
					{
						logger.info("Dist Id Matches Successfully ");
						status = rset1.getInt("STATUS");
						// If Distributor ID is not same, return "Failure" and
						// message "STB is not belonging to this distributor"

						// Add product id check Neetika
						//
					

							pstmt3 = connection
									.prepareStatement(DBQueries.SQL_SELECT_STB_STATUS_PROD);
							pstmt3.setString(1, stbNo);
							rset2 = pstmt3.executeQuery();

							while (rset2 != null && rset2.next()) {
								prodname = rset2.getString("PRODUCT_CATEGORY");

							}
							// if(prodname.equalsIgnoreCase(Constants.HDB)||
							// productName.equalsIgnoreCase(Constants.HDB))
							// isProductHDB=true;

							isProductHDB = prodname
									.equalsIgnoreCase(Constants.HDB)
									^ productName
											.equalsIgnoreCase(Constants.HDB);
							if (productName.contains(Constants.DOA) &&  prodname
									.equalsIgnoreCase(Constants.HDB)) {
								
								hdbFlag=true;
								
							}
							
							else if(!productName.contains(Constants.DOA)){
								
							if (prodname.equalsIgnoreCase(productName)) {
								logger
										.info("Product Name Matches Successfully ");

							} else if (isProductHDB) {
								servMsg[0] = "Failure";
								servMsg[1] = Constants.STB_TYPE_HDB+prodname;
								return servMsg;
							}

							/*
							 * else { servMsg[0] ="Failure"; servMsg[1]
							 * =com.ibm.
							 * dp.common.Constants.STB_BELONG_TO_CORRECT_PROD ;
							 * return servMsg; }
							 */

						}
						// end
					} 
					else 
					{
						servMsg[0] ="Failure";
						servMsg[1] =com.ibm.dp.common.Constants.STB_BELONG_DIST ;
						return servMsg;
					}
				}
			}
			logger.info("Status in checkMapping == " + status);
			//If distributor is same, check if STB status is "Unassigned", 
			//if not, return "Failure" and message "STB is not Unassigned (Secondary sale not done)".
			
			if (stbExist == 1) 
			{
				if (status == 1) 
				{
					servMsg[0] ="SUCCESS";
					if(hdbFlag)
					   servMsg[1]=Constants.DOA_HDB;
					else
						servMsg[1] =com.ibm.dp.common.Constants.DIST_STB_MAP_WS_STATUS_1;
					return servMsg;
				} 
				else 
				{
					servMsg[0] ="Failure";
					servMsg[1] =com.ibm.dp.common.Constants.DIST_STB_MAP_WS_NOTUNASSIGNED;
					return servMsg;
				}
				//If STB is not existing,  Check if "Non-Serialized Restriction" is enabled for the Circle (Circle ID) - discuss with priya
				//If enabled return "Failure" and "STB cannot be activated due to non-serialized restriction"
				//If disabled return "Success" and message "Non-Serialized Stock"
			} 
			else 
			{
				if ( (requestType==1 && nserActivationStatus.equals("1")) || (requestType==2 && nserInvChangeStatus.equals("1")) ) 
				{
					servMsg[0] ="SUCCESS";
					servMsg[1] =com.ibm.dp.common.Constants.STB_NON_SER_STOCK;
					return servMsg;
				} 
				else 
				{
					servMsg[0] ="Failure";
					servMsg[1] =com.ibm.dp.common.Constants.STB_NON_SER_STOCK_REST;
					return servMsg;
				}
			}
			
		} 
		catch (Exception e) 
		{
			e.printStackTrace();

			logger.error("::::::::::::::::::: Error in DistributorSTBMappingDaoImpl in fun checkOLMId WebService -------->",e);

			servMsg[0] = "Failure";
			servMsg[1] = "Error in WebService";
			try 
			{
				connection.rollback();
			} 
			catch (SQLException sqle) 
			{
				sqle.printStackTrace();
				logger.error("::::::::::::::::::: Error in DistributorSTBMappingDaoImpl in fun checkOLMId WebService -------->",sqle);
			}
		} 
		finally 
		{
			DBConnectionManager.releaseResources(pstmt, rset);
			DBConnectionManager.releaseResources(pstmt2, rset1);
		}

		return servMsg;
	}

	public String[] avRestrictionCheck(String avSerialNumber, String distOLMId) throws DAOException 
	{
		//String servMsg = "SUCCESS";
		String[] servMsg = new String[2];
		servMsg[0]="SUCCESS";
		servMsg[1]="";
		PreparedStatement pstmt = null;
		ResultSet rset = null;
		Integer intAVStatus = 0;
		Integer intDPDistId = 0;
		try 
		{
			//checkLapuMobileNO(lapuMobileNo);
			String strAVRestrictStatus = "I";
			// Get Distributor's detail(Distributor ID, Circle ID) for the OLM ID. 
			// If OLM Id invalid - revert with error message "OLM ID Invalid" 
			pstmt = connection.prepareStatement(DBQueries.AV_RESTRICTION_STATUS);
			
			rset = pstmt.executeQuery();
			
			if(rset != null &&  rset.next()) 
			{
				strAVRestrictStatus = rset.getString("WEBSERVICE_STATUS");
			}
			
			DBConnectionManager.releaseResources(pstmt, rset);
			
			if (strAVRestrictStatus=="I") 
			{
				servMsg[0] ="SUCCESS";
				servMsg[1] ="No restriction in DP";
				return servMsg;
			//	If distributor is valid (Existing), get details of STB (STB status, Distributor ID) from Stock inventory table
			} 
			else 
			{
				pstmt = connection.prepareStatement(DBQueries.SQL_SELECT_AV_STATUS);
				
				pstmt.setString(1, avSerialNumber);
				
				rset = pstmt.executeQuery();
				if (rset != null && rset.next()) 
				{
					intAVStatus = rset.getInt("STATUS");;
					intDPDistId = rset.getInt("DISTRIBUTOR_ID");
					
					//In case no restriction on serialized stock, return success with appropriate message restriction status value: "0" means enabled, "1" means disabled
					if( intAVStatus==1)
					{
						servMsg[0] ="SUCCESS";
						servMsg[1] ="SUCCESS";
						return servMsg;
					}
					else
					{
						servMsg[0] ="FAIL";
						servMsg[1] =com.ibm.dp.common.Constants.AV_RESTICTION_MSG_NO_SEC_SALE;
						return servMsg;
					}
				}
				else
				{
					servMsg[0] ="UNAVAILABLE_AV";
					servMsg[1] =com.ibm.dp.common.Constants.AV_RESTICTION_MSG_NOT_IN_DP;
					return servMsg;
				}
			}
		} 
		catch (Exception e) 
		{
			e.printStackTrace();

			logger.error("::::::::::::::::::: Error in DistributorSTBMappingDaoImpl in fun checkOLMId WebService -------->",e);

			servMsg[0] = "FAIL";
			servMsg[1] = "Error in WebService";
			try 
			{
				connection.rollback();
			} 
			catch (SQLException sqle) 
			{
				sqle.printStackTrace();
				logger.error("::::::::::::::::::: Error in DistributorSTBMappingDaoImpl in fun checkOLMId WebService -------->",sqle);
			}
		} 
		finally 
		{
			DBConnectionManager.releaseResources(pstmt, rset);
		}

		return servMsg;
	}
	
	public String[] avRestrictionCheck2(String avSerialNumber, String distOLMId) throws DAOException 
	{
		//String servMsg = "SUCCESS";
		String[] servMsg = new String[2];
		servMsg[0]="SUCCESS";
		servMsg[1]="";
		PreparedStatement pstmt = null;
		ResultSet rset = null;
		Integer intAVStatus = 0;
		Integer intDPDistId = 0;
		try 
		{
			checkLapuMobileNO(distOLMId);
			String strAVRestrictStatus = "I";
			// Get Distributor's detail(Distributor ID, Circle ID) for the OLM ID. 
			// If OLM Id invalid - revert with error message "OLM ID Invalid" 
			pstmt = connection.prepareStatement(DBQueries.AV_RESTRICTION_STATUS);
			
			rset = pstmt.executeQuery();
			
			if(rset != null &&  rset.next()) 
			{
				strAVRestrictStatus = rset.getString("WEBSERVICE_STATUS");
			}
			
			DBConnectionManager.releaseResources(pstmt, rset);
			
			if (strAVRestrictStatus=="I") 
			{
				servMsg[0] ="SUCCESS";
				servMsg[1] ="No restriction in DP";
				return servMsg;
			//	If distributor is valid (Existing), get details of STB (STB status, Distributor ID) from Stock inventory table
			} 
			else 
			{
				pstmt = connection.prepareStatement(DBQueries.SQL_SELECT_AV_STATUS);
				
				pstmt.setString(1, avSerialNumber);
				
				rset = pstmt.executeQuery();
				if (rset != null && rset.next()) 
				{
					intAVStatus = rset.getInt("STATUS");;
					intDPDistId = rset.getInt("DISTRIBUTOR_ID");
					
					//In case no restriction on serialized stock, return success with appropriate message restriction status value: "0" means enabled, "1" means disabled
					if( intAVStatus==1)
					{
						servMsg[0] ="SUCCESS - Secondary  sales is done";
						servMsg[1] ="SUCCESS";
						return servMsg;
					}
					else
					{
						servMsg[0] ="FAIL";
						servMsg[1] =com.ibm.dp.common.Constants.AV_RESTICTION_MSG_NO_SEC_SALE;
						return servMsg;
					}
				}
				else
				{
					servMsg[0] ="UNAVAILABLE_AV";
					servMsg[1] =com.ibm.dp.common.Constants.AV_RESTICTION_MSG_NOT_IN_DP;
					return servMsg;
				}
			}
		} 
		catch (Exception e) 
		{
			e.printStackTrace();

			logger.error("::::::::::::::::::: Error in DistributorSTBMappingDaoImpl in fun checkOLMId WebService -------->",e);

			servMsg[0] = "FAIL";
			servMsg[1] = "Error in WebService";
			try 
			{
				connection.rollback();
			} 
			catch (SQLException sqle) 
			{
				sqle.printStackTrace();
				logger.error("::::::::::::::::::: Error in DistributorSTBMappingDaoImpl in fun checkOLMId WebService -------->",sqle);
			}
		} 
		finally 
		{
			DBConnectionManager.releaseResources(pstmt, rset);
		}

		return servMsg;
	}
	
	
	public String checkLapuMobileNO(String lapuMobileNo) throws DAOException 
	{
		String distId = null;
		//String status= null;
		PreparedStatement pstmt = null;
		ResultSet rset = null;
		try 
		{
					
			pstmt = connection.prepareStatement(DBQueries.SQL_SELECT_LAPU_MOBILE_NO);
			pstmt.setString(1, lapuMobileNo);
			rset = pstmt.executeQuery();
			
			if (rset.next()) 
			{
				int intDistID = rset.getInt("ACCOUNT_ID");
				
				distId = intDistID+"";
			}
			
			logger.info("distId == " + distId);
			
			//connection.commit();
		} 
		catch (Exception e) 
		{
			e.printStackTrace();

			logger.error("::::::::::::::::::: Error in DistributorSTBMappingDaoImpl in  checkLapuNo -------->",e);

			distId = "-1";
			
			throw new DAOException(e.getMessage());
		} 
		finally 
		{
			DBConnectionManager.releaseResources(pstmt, rset);
		}
		return distId ;
	}

	@Override
	public String checkSameOlmId(String distId, String stbNo,String andrdStbNo) {
		String distOlmId = "" ;
		PreparedStatement ps = null;
		ResultSet rst= null;
	
  		logger.info("Enter checkSameOlmId ---");
  		try {
  			
  			connection.setAutoCommit(false);
  			ps =connection.prepareStatement("select LOGIN_ID from VR_LOGIN_MASTER where LOGIN_ID = "+
  									"(select DISTRIBUTOR_ID from DP_STOCK_INVENTORY where SERIAL_NO =? and STATUS = 1  INTERSECT "+
  									"select DISTRIBUTOR_ID from DP_STOCK_INVENTORY where SERIAL_NO =? and STATUS = 1 )");
  			ps.setString(1, stbNo);
  			ps.setString(2, andrdStbNo);

  			rst = ps.executeQuery();
  			
  			if(rst.next())
  			{
  				distOlmId = rst.getString("LOGIN_ID");
  				System.out.println("login ID--IF :" + distOlmId);
  			}
  			else
  			{
  				distOlmId = "";
  				System.out.println("login ID :" + distOlmId);
  			}
  			 	
  		
  		}
  		catch (Exception e) {
			// TODO: handle exception
  			logger.error("Error is checking distributor...");
		}
		
		return distOlmId;

	}


	@Override
	public String[] checkMappingForAndroid(String distId, String stbNo,
			String andrdStbNo, String distOLMId, int requestType,
			String andrdProdName, String productName) 
		throws DAOException {
		
			String[] servMsg = new String[2];
			servMsg[0]="SUCCESS";
			servMsg[1]="";
			PreparedStatement pstmt = null;
			ResultSet rset = null;
			ResultSet rset1 = null;
			ResultSet rset2 = null;
			Integer status = -1;
			Integer AndrStatus = -1;
			
			String dpDistId = "-1";
			Integer stbExist = 0;
			PreparedStatement pstmt2 = null;
			String accountId = "";
			String circle_id = "";
			String nserActivationStatus = "";
			String serActivationStatus = "";
			String nserInvChangeStatus = "";
			String serInvChangeStatus = "";
			boolean flag = false;
			boolean hdbFlag=false;
			
			//Neetika
			String prodname="";
			PreparedStatement pstmt3 = null;
			//Shantanu
			boolean isProductHDB=false;
			try {
				
				// Get Distributor's detail(Distributor ID, Circle ID) for the OLM ID. 
				// If OLM Id invalid - revert with error message "OLM ID Invalid" 
				pstmt2 = connection.prepareStatement(DBQueries.SELECT_DISTRIBUTOR_DETAILS);
				
				pstmt2.setString(1, distOLMId);
				rset = pstmt2.executeQuery();
				
				while (rset != null &&  rset.next()) 
				{
					accountId = rset.getString("account_id");
					circle_id = rset.getString("CIRCLE_ID");
					nserActivationStatus = rset.getString("NSER_ACTIVATION_STATUS");
					serActivationStatus = rset.getString("SER_ACTIVATION_STATUS");
					nserInvChangeStatus = rset.getString("INV_CHANGE_NSER_STATUS");
					serInvChangeStatus = rset.getString("INV_CHANGE_SER_STATUS");
					flag = true;
				}
				
				if (!flag) 
				{
					servMsg[0] ="Failure";
					servMsg[1] =com.ibm.dp.common.Constants.DIST_ID_NOT_VALID;
					return servMsg;
				//	If distributor is valid (Existing), get details of STB (STB status, Distributor ID) from Stock inventory table
				} 
				else 
				{
					pstmt = connection.prepareStatement(DBQueries.SQL_SELECT_STB_STATUS_WODAMAGED);
					pstmt.setString(1, stbNo);
					rset1 = pstmt.executeQuery();
					
					// for stb
					
					while (rset1 != null && rset1.next()) 
					{
						stbExist = 1;
						dpDistId = rset1.getString("DISTRIBUTOR_ID");
						
						//In case no restriction on serialized stock, return success with appropriate message restriction status value: "0" means enabled, "1" means disabled
						if( (requestType==1 && serActivationStatus.equals("1"))|| (requestType==2 && serInvChangeStatus.equals("1")) )
						{
							servMsg[0] ="Success";
							servMsg[1] =com.ibm.dp.common.Constants.STB_SER_NO_REST ;
							return servMsg;
						}
						
						
						//If STB is existing, validate if Distributor ID for this STB is same as Distributor ID of OLM ID received.
						if (accountId.equals(dpDistId)) 
						{
							logger.info("Dist Id Matches Successfully ");
							status = rset1.getInt("STATUS");
							// If Distributor ID is not same, return "Failure" and
							// message "STB is not belonging to this distributor"

								pstmt3 = connection
										.prepareStatement(DBQueries.SQL_SELECT_STB_STATUS_PROD);
								pstmt3.setString(1, stbNo);
								rset2 = pstmt3.executeQuery();

								while (rset2 != null && rset2.next()) {
									prodname = rset2.getString("PRODUCT_CATEGORY");

								}
								
							
								if (prodname.equalsIgnoreCase(productName)) {
									logger
											.info("Product Name Matches Successfully ");

								}
								else{
									logger.info("Product Name is not Matches Successfully "+productName);
									servMsg[0] = "Failure";
									servMsg[1] = Constants.STB_PRODUCT_TYPE;
									return servMsg;
								}
						} 
						else 
						{
							servMsg[0] ="Failure";
							servMsg[1] =com.ibm.dp.common.Constants.STB_BELONG_DIST ;
							return servMsg;
						}
					}
				
					
					// For Android 
				
					pstmt.setString(1, andrdStbNo);
					rset1 = pstmt.executeQuery();
					
					while (rset1 != null && rset1.next()) 
					{
						stbExist = 1;
						dpDistId = rset1.getString("DISTRIBUTOR_ID");
						
						//In case no restriction on serialized stock, return success with appropriate message restriction status value: "0" means enabled, "1" means disabled
						if( (requestType==1 && serActivationStatus.equals("1"))|| (requestType==2 && serInvChangeStatus.equals("1")) )
						{
							System.out.println("3 condition " + andrdProdName);
							servMsg[0] ="Success";
							servMsg[1] =com.ibm.dp.common.Constants.STB_SER_NO_REST ;
							return servMsg;
						}
						
						//If STB is existing, validate if Distributor ID for this STB is same as Distributor ID of OLM ID received.
						if (accountId.equals(dpDistId)) 
						{
							logger.info("Dist Id Matches Successfully ");
							AndrStatus = rset1.getInt("STATUS");
							pstmt3 = connection
							.prepareStatement(DBQueries.SQL_SELECT_STB_STATUS_PROD);
							pstmt3.setString(1, andrdStbNo);
							rset2 = pstmt3.executeQuery();

							while (rset2 != null && rset2.next()) {
								prodname = rset2.getString("PRODUCT_CATEGORY");

							}
					
				
							/*if(andrdProdName.equalsIgnoreCase("CAM AND"))
							{
								logger.info("Product Name is CAM AND");
							}
							else{
								logger.info("Product Name should cam android");
								servMsg[0] = "Failure";
								servMsg[1] = Constants.ANDPROD_PRODUCT_TYPE;
								return servMsg;
							}*/
				
							
							
							if (prodname.equalsIgnoreCase(andrdProdName)) {
								logger
									.info("Product Name Matches Successfully ");

							}
							else{
								logger.info("Product Name is not Matches Successfully " + andrdProdName);
								servMsg[0] = "Failure";
								servMsg[1] = Constants.ANDPROD_PRODUCT_TYPE;
								return servMsg;
							}
				
						} 
						else 
						{
							servMsg[0] ="Failure";
							servMsg[1] =com.ibm.dp.common.Constants.STB_BELONG_DIST ;
							return servMsg;
						}
					
					
					}
					
				}
				logger.info("Status in checkMapping == " + status);
				//If distributor is same, check if STB status is "Unassigned", 
				//if not, return "Failure" and message "STB is not Unassigned (Secondary sale not done)".
				
				if (stbExist == 1) 
				{
					if (status == 1 && AndrStatus == 1) 
					{
						servMsg[0] ="SUCCESS";
						if(hdbFlag)
						   servMsg[1]=Constants.DOA_HDB;
						else
							servMsg[1] =com.ibm.dp.common.Constants.DIST_STB_MAP_WS_STATUS_1;
						return servMsg;
					} 
					else 
					{
						servMsg[0] ="Failure";
						servMsg[1] =com.ibm.dp.common.Constants.DIST_STB_MAP_WS_NOTUNASSIGNED;
						return servMsg;
					}
					//If STB is not existing,  Check if "Non-Serialized Restriction" is enabled for the Circle (Circle ID) - discuss with priya
					//If enabled return "Failure" and "STB cannot be activated due to non-serialized restriction"
					//If disabled return "Success" and message "Non-Serialized Stock"
				} 
				else 
				{
					if ( (requestType==1 && nserActivationStatus.equals("1"))|| (requestType==2 && serInvChangeStatus.equals("1")) ) 
					{
						servMsg[0] ="SUCCESS";
						servMsg[1] =com.ibm.dp.common.Constants.STB_NON_SER_STOCK;
						return servMsg;
					} 
					else 
					{
						servMsg[0] ="Failure";
						servMsg[1] =com.ibm.dp.common.Constants.STB_NON_SER_STOCK_REST;
						return servMsg;
					}
				}
				
			} 
			catch (Exception e) 
			{
				e.printStackTrace();

				logger.error("::::::::::::::::::: Error in DistributorSTBMappingDaoImpl in fun checkOLMId WebService -------->",e);

				servMsg[0] = "Failure";
				servMsg[1] = "Error in WebService";
				try 
				{
					connection.rollback();
				} 
				catch (SQLException sqle) 
				{
					sqle.printStackTrace();
					logger.error("::::::::::::::::::: Error in DistributorSTBMappingDaoImpl in fun checkOLMId WebService -------->",sqle);
				}
			} 
			finally 
			{
				DBConnectionManager.releaseResources(pstmt, rset);
				DBConnectionManager.releaseResources(pstmt2, rset1);
			}

			return servMsg;
	}

	
		
}
