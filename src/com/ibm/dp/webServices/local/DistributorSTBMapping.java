package com.ibm.dp.webServices.local;

import java.util.ResourceBundle;

import org.apache.log4j.Logger;

import com.ibm.appsecure.util.Encryption;
import com.ibm.appsecure.util.IEncryption;
import com.ibm.dp.service.DistributorSTBMappingService;
import com.ibm.dp.service.impl.DistributorSTBMappingServiceImpl;
import com.ibm.virtualization.recharge.common.Constants;
import com.tivoli.pd.jasn1.boolean32;

public class DistributorSTBMapping {
	public static Logger logger = Logger.getLogger(DistributorSTBMapping.class
			.getName());

	public String[] distStbMapping(String distOLMId, String stbNo, String retailerMobNo, int requestType,String productName, String userid, String password) 
	{
		logger.info("DistributorSTBMapping WebService Started for \n distributor OLM Id  :::  "+ distOLMId 
				+ "\n STB No      :::  " + stbNo+ "\n requestType      :::  " + requestType);

		String[] serviceMsg = new String[2]; // [0] = header ,[1] = message
		IEncryption crypt = new Encryption();
		String encPassword = "";
		ResourceBundle webserviceResourceBundle = null;
		try 
		{
			encPassword = crypt.encrypt(password);
			if (webserviceResourceBundle == null) 
			{
				webserviceResourceBundle = ResourceBundle.getBundle(Constants.WEBSERVICE_RESOURCE_BUNDLE);
			}

			if (!userid.equals(webserviceResourceBundle.getString("dpwebservice.distStbMap.userid"))) 
			{
				logger.info("**********INVALID USER NAME**********");
				serviceMsg[0] = "Failure";
				serviceMsg[1] = com.ibm.dp.common.Constants.STB_STB_MAP_WS_INVALID_USER_NAME;
				logger.info("********** DistributorSTBMapping WebService msg returned == "+ serviceMsg[0]+"  ::  "+serviceMsg[1]);
				return serviceMsg;
			} 
			else if (!encPassword.equals(webserviceResourceBundle.getString("dpwebservice.distStbMap.password"))) 
			{
				logger.info("**********INVALID PASSWORD**********");
				serviceMsg[0] = "Failure";
				serviceMsg[1] = com.ibm.dp.common.Constants.STB_STB_MAP_WS_INVALID_PASSWORD;
				logger.info("********** DistributorSTBMapping WebService msg returned == "+ serviceMsg[0]+"  ::  "+serviceMsg[1]);
				return serviceMsg;
			} 
			else 
			{
				DistributorSTBMappingService stbService = new DistributorSTBMappingServiceImpl();
				if(distOLMId == null || distOLMId.trim().equals(""))
				{
					logger.info("**********INVALID OLM ID  ::  "+distOLMId+"   **********");
					serviceMsg[0] = "Failure";
					serviceMsg[1] = com.ibm.dp.common.Constants.DIST_STB_MAP_WS_INVALID_OLM_ID;
					logger.info("********** DistributorSTBMapping WebService msg returned == "+ serviceMsg[0]+"  ::  "+serviceMsg[1]);
					return serviceMsg;
				}
				
				String distId = stbService.checkOLMId(distOLMId);
				
				logger.info("************Distributor ID == " + distId);
				
				if (distId != "-1") 
				{
					if (stbNo.length() != 11) 
					{
						logger.info("********** Not 11 digit length, INVALID SERIAL NUMBER      :::  "+ stbNo);
						serviceMsg[0] = "Failure";
						serviceMsg[1] = com.ibm.dp.common.Constants.DIST_STB_MAP_WS_INVALID_STB+ " " + stbNo;
						logger.info("********** DistributorSTBMapping WebService msg returned == "+ serviceMsg[0]+"  ::  "+serviceMsg[1]);
						return serviceMsg;
					} 
					else 
					{
						boolean isNumeric = stbNo.matches("[0-9]+$");
						if (!isNumeric) 
						{
							logger.info("********** Non Numeric value, INVALID SERIAL NUMBER      :::  "+ stbNo);
							serviceMsg[0] = "Failure";
							serviceMsg[1] = com.ibm.dp.common.Constants.DIST_STB_MAP_WS_INVALID_STB+ " " + stbNo;
							logger.info("********** DistributorSTBMapping WebService msg returned == "+ serviceMsg[0]+"  ::  "+serviceMsg[1]);
							return serviceMsg;
						} 
						else if (stbNo.indexOf("0") != 0) 
						{
							logger.info("**********Not started with 0, INVALID SERIAL NUMBER      :::  "+ stbNo);
							serviceMsg[0] = "Failure";
							serviceMsg[1] = com.ibm.dp.common.Constants.DIST_STB_MAP_WS_INVALID_STB+ " " + stbNo;
							logger.info("********** DistributorSTBMapping WebService msg returned == "+ serviceMsg[0]+"  ::  "+serviceMsg[1]);
							return serviceMsg;

						} 
						else 
						{
							// check availability of serial number in DPDTH with
							// OLM Id
										//serviceMsg = stbService.checkMapping(distId, stbNo,distOLMId,requestType,productName);
									serviceMsg = stbService.checkMapping(distId, stbNo,distOLMId,requestType,productName);
						
							logger.info("********** DistributorSTBMapping WebService msg returned == "+ serviceMsg[0]+"  ::  "+serviceMsg[1]);
						}
					}
				} 
				else 
				{
					logger.info("**********INVALID OLM ID     :::  "+ distOLMId);
					serviceMsg[0] = "Failure";
					serviceMsg[1] = com.ibm.dp.common.Constants.DIST_STB_MAP_WS_INVALID_OLM_ID+ " " + distOLMId;
					
					logger.info("********** DistributorSTBMapping WebService msg returned == "+ serviceMsg[0]+"  ::  "+serviceMsg[1]);
					return serviceMsg;
				}
			}
		} 
		catch (Exception e) 
		{
			logger.error("************ Exception in DistributorSTBMapping WebService -------->",e);
			e.printStackTrace();
			serviceMsg[0] = "Failure";
			serviceMsg[1] = "Error in WebService";
			logger.info("********** DistributorSTBMapping WebService msg returned == "+ serviceMsg[0]+"  ::  "+serviceMsg[1]);
		}
		return serviceMsg;
	}
	
	
	public String[] distStbMappingNew(String distOLMId, String stbNo,String andrdStbNo, String retailerMobNo, int requestType,String productName,String andrdProdName, String userid, String password, String Flag) 
	{
		logger.info("DistributorSTBMapping WebService Started for \n distributor OLM Id  :::  "+ distOLMId 
				+ "\n STB No      :::  " + stbNo+ "\n Android No      :::  " + andrdStbNo
				+ "\n requestType      :::  " + requestType);

		String[] serviceMsg = new String[2]; // [0] = header ,[1] = message
		IEncryption crypt = new Encryption();
		String encPassword = "";
		ResourceBundle webserviceResourceBundle = null;
		try 
		{
			logger.info("**********INVALID USER**********");
			encPassword = crypt.encrypt(password);
			if (webserviceResourceBundle == null) 
			{
				webserviceResourceBundle = ResourceBundle.getBundle(Constants.WEBSERVICE_RESOURCE_BUNDLE);
			}

			if (!userid.equals(webserviceResourceBundle.getString("dpwebservice.distStbMap.userid"))) 
			{
				logger.info("**********INVALID USER NAME**********");
				serviceMsg[0] = "Failure";
				serviceMsg[1] = com.ibm.dp.common.Constants.STB_STB_MAP_WS_INVALID_USER_NAME;
				logger.info("********** DistributorSTBMapping WebService msg returned == "+ serviceMsg[0]+"  ::  "+serviceMsg[1]);
				return serviceMsg;
			} 
			else if (!encPassword.equals(webserviceResourceBundle.getString("dpwebservice.distStbMap.password"))) 
			{
				logger.info("**********INVALID PASSWORD**********");
				serviceMsg[0] = "Failure";
				serviceMsg[1] = com.ibm.dp.common.Constants.STB_STB_MAP_WS_INVALID_PASSWORD;
				logger.info("********** DistributorSTBMapping WebService msg returned == "+ serviceMsg[0]+"  ::  "+serviceMsg[1]);
				return serviceMsg;
			} 
			else 
			{
				DistributorSTBMappingService stbService = new DistributorSTBMappingServiceImpl();
				if(distOLMId == null || distOLMId.trim().equals(""))
				{
					logger.info("**********INVALID OLM ID  ::  "+distOLMId+"   **********");
					serviceMsg[0] = "Failure";
					serviceMsg[1] = com.ibm.dp.common.Constants.DIST_STB_MAP_WS_INVALID_OLM_ID;
					logger.info("********** DistributorSTBMapping WebService msg returned == "+ serviceMsg[0]+"  ::  "+serviceMsg[1]);
					return serviceMsg;
				}
				
				String distId = stbService.checkOLMId(distOLMId);
				
				logger.info("************Distributor ID == " + distId);
				logger.info("************Android stb no. == " + andrdStbNo);

				
				if (distId != "-1") 
				{
					
					if (Flag == "Y" || Flag.equalsIgnoreCase("Y"))
					{
						boolean isNumericForAndroid = andrdStbNo.matches("[0-9]+$"); 
						
						System.out.println("productName "+productName);
						System.out.println("andrdProdName "+andrdProdName);
						
					/*	if(!andrdProdName.equalsIgnoreCase("ANDPROD"))
						{
							logger.info("Product Name should ANDPROD");
							serviceMsg[0] = "Failure";
							serviceMsg[1] = com.ibm.dp.common.Constants.ANDPROD_PRODUCT_TYPE;
							return serviceMsg;
						}
						
						else if(!productName.trim().equalsIgnoreCase("CAM AND"))
						{
							logger.info("Product Name should CAM AND");
							serviceMsg[0] = "Failure";
							serviceMsg[1] = com.ibm.dp.common.Constants.CAMAND_PRODUCT_TYPE;
							return serviceMsg;
						}
						else 
						{
							logger.info("ANDPROD and CAM AND product match");
							
						}*/
						
						if(requestType == 2)
						{
							//		serviceMsg = stbService.checkMapping(distId, stbNo,distOLMId,requestType,productName);
					// for android 		
							if(andrdStbNo == null || andrdStbNo.equalsIgnoreCase(""))
												{
										
													logger.info("********** null Android SERIAL NUMBER      :::  "+ andrdStbNo);
													serviceMsg[0] = "Failure";
													serviceMsg[1] = com.ibm.dp.common.Constants.DIST_STB_MAP_WS_INVALID_STB;
													logger.info("********** DistributorSTBMapping WebService msg returned == "+ serviceMsg[0]+"  ::  "+serviceMsg[1]);
													return serviceMsg;
												}
												else if(andrdStbNo.length() != 11)
												{
													logger.info("********** null Android SERIAL NUMBER      :::  "+ andrdStbNo);
													serviceMsg[0] = "Failure";
													serviceMsg[1] = com.ibm.dp.common.Constants.DIST_STB_MAP_WS_INVALID_STB;
													logger.info("********** DistributorSTBMapping WebService msg returned == "+ serviceMsg[0]+"  ::  "+serviceMsg[1]);
													return serviceMsg;
												}
												else if(andrdStbNo.indexOf("0") !=0 )
												{
													logger.info("********** null Android SERIAL NUMBER      :::  "+ andrdStbNo);
													serviceMsg[0] = "Failure";
													serviceMsg[1] = com.ibm.dp.common.Constants.DIST_STB_MAP_WS_INVALID_STB;
													logger.info("********** DistributorSTBMapping WebService msg returned == "+ serviceMsg[0]+"  ::  "+serviceMsg[1]);
													return serviceMsg;
												
												}
												else if(!isNumericForAndroid)
												{
													logger.info("********** null Android SERIAL NUMBER      :::  "+ andrdStbNo);
													serviceMsg[0] = "Failure";
													serviceMsg[1] = com.ibm.dp.common.Constants.DIST_STB_MAP_WS_INVALID_STB;
													logger.info("********** DistributorSTBMapping WebService msg returned == "+ serviceMsg[0]+"  ::  "+serviceMsg[1]);
													return serviceMsg;
													
												}

												// for stb
							
												if (stbNo.length() != 11 ) 
												{
													logger.info("********** Not 11 digit length, INVALID SERIAL NUMBER      :::  "+ stbNo);
													serviceMsg[0] = "Failure";
													serviceMsg[1] = com.ibm.dp.common.Constants.DIST_STB_MAP_WS_INVALID_STB;
													logger.info("********** DistributorSTBMapping WebService msg returned == "+ serviceMsg[0]+"  ::  "+serviceMsg[1]);
													return serviceMsg;
												}
												else 
												{
													boolean isNumeric = stbNo.matches("[0-9]+$");
													
													 if (!isNumeric ) 
													{
														logger.info("********** Non Numeric value, INVALID SERIAL NUMBER      :::  "+ stbNo);
														serviceMsg[0] = "Failure";
														serviceMsg[1] = com.ibm.dp.common.Constants.DIST_STB_MAP_WS_INVALID_STB;
														logger.info("********** DistributorSTBMapping WebService msg returned == "+ serviceMsg[0]+"  ::  "+serviceMsg[1]);
														return serviceMsg;
													} 
													else if (stbNo.indexOf("0") != 0) 
													{
														logger.info("**********Not started with 0, INVALID SERIAL NUMBER      :::  "+ stbNo);
														serviceMsg[0] = "Failure";
														serviceMsg[1] = com.ibm.dp.common.Constants.DIST_STB_MAP_WS_INVALID_STB;
														logger.info("********** DistributorSTBMapping WebService msg returned == "+ serviceMsg[0]+"  ::  "+serviceMsg[1]);
														return serviceMsg;
													} 
													
													else 
													{
														String ID = stbService.checkSameOlmId(distId,stbNo,andrdStbNo);
														System.out
																.println("ID: "+ID);
														
														if(ID == distId || ID.equalsIgnoreCase(distId))
														{
												
														serviceMsg = stbService.checkMappingForAndroid(distId,stbNo,andrdStbNo, distOLMId, requestType, andrdProdName,productName);
														logger.info("********** DistributorSTBMapping WebService Android NO and stb no.");
														}
														else
														{
															logger.info("********** SERIAL NUMBER IS NOT SAME FOR ALL PRODUCT   :::  "+ stbNo +" and "+ andrdStbNo);
															serviceMsg[0] = "Failure";
															serviceMsg[1] = com.ibm.dp.common.Constants.ANDR_AND_STB_NO_IS_NOT_SAME;
															logger.info("********** DistributorSTBMapping WebService msg returned == "+ serviceMsg[0]+"  ::  "+serviceMsg[1]);
														}
													}
												
									}
										
						}
						else
						{
							logger.info("********** Request Type should only 2  :::  "+ stbNo +" and "+ andrdStbNo);
							serviceMsg[0] = "Failure";
							serviceMsg[1] = com.ibm.dp.common.Constants.STB_REQUEST_TYPE;
							logger.info("********** DistributorSTBMapping WebService msg returned == "+ serviceMsg[0]+"  ::  "+serviceMsg[1]);
							
						}
						
					}
					else if (Flag == "A" || Flag.equalsIgnoreCase("A"))
					{
						/*if(!andrdProdName.equalsIgnoreCase("ANDPROD"))
						{
							logger.info("Product Name should cam android");
							serviceMsg[0] = "Failure";
							serviceMsg[1] = com.ibm.dp.common.Constants.ANDPROD_PRODUCT_TYPE;
							return serviceMsg;
						}*/
						
						
						boolean isNumericForAndroid = andrdStbNo.matches("[0-9]+$"); 
					
						
						if(requestType == 2)
						{
									//		serviceMsg = stbService.checkMapping(distId, stbNo,distOLMId,requestType,productName);
											if ( !stbNo.trim().equalsIgnoreCase(""))
											{
													logger.info("********** Enter only Android SERIAL NUMBER ");
													serviceMsg[0] = "Failure";
													serviceMsg[1] = com.ibm.dp.common.Constants.ANDROID_RECOVERY;
													logger.info("********** DistributorSTBMapping WebService msg returned == "+ serviceMsg[0]+"  ::  "+serviceMsg[1]);
													return serviceMsg;
											}
												else
											{
												if(andrdStbNo == null || andrdStbNo.equalsIgnoreCase(""))
												{
										
													logger.info("********** null Android SERIAL NUMBER      :::  "+ andrdStbNo);
													serviceMsg[0] = "Failure";
													serviceMsg[1] = com.ibm.dp.common.Constants.DIST_STB_MAP_WS_INVALID_STB;
													logger.info("********** DistributorSTBMapping WebService msg returned == "+ serviceMsg[0]+"  ::  "+serviceMsg[1]);
													return serviceMsg;
												}
												else if(andrdStbNo.length() != 11)
												{
													logger.info("********** null Android SERIAL NUMBER      :::  "+ andrdStbNo);
													serviceMsg[0] = "Failure";
													serviceMsg[1] = com.ibm.dp.common.Constants.DIST_STB_MAP_WS_INVALID_STB;
													logger.info("********** DistributorSTBMapping WebService msg returned == "+ serviceMsg[0]+"  ::  "+serviceMsg[1]);
													return serviceMsg;
												}
												else if(andrdStbNo.indexOf("0") !=0 )
												{
													logger.info("********** null Android SERIAL NUMBER      :::  "+ andrdStbNo);
													serviceMsg[0] = "Failure";
													serviceMsg[1] = com.ibm.dp.common.Constants.DIST_STB_MAP_WS_INVALID_STB;
													logger.info("********** DistributorSTBMapping WebService msg returned == "+ serviceMsg[0]+"  ::  "+serviceMsg[1]);
													return serviceMsg;
												
												}
												else if(!isNumericForAndroid)
												{
													logger.info("********** null Android SERIAL NUMBER      :::  "+ andrdStbNo);
													serviceMsg[0] = "Failure";
													serviceMsg[1] = com.ibm.dp.common.Constants.DIST_STB_MAP_WS_INVALID_STB;
													logger.info("********** DistributorSTBMapping WebService msg returned == "+ serviceMsg[0]+"  ::  "+serviceMsg[1]);
													return serviceMsg;
													
												}
												else{
												
													serviceMsg = stbService.checkMapping(distId, andrdStbNo,distOLMId,requestType,andrdProdName);
													logger.info("********** DistributorSTBMapping WebService msg returned == "+ serviceMsg[0]+"  ::  "+serviceMsg[1]);
													}
												}
												
											}
										
						
						else
						{
							logger.info("********** Request Type should only 2  :::  "+ stbNo +" and "+ andrdStbNo );
							serviceMsg[0] = "Failure";
							serviceMsg[1] = com.ibm.dp.common.Constants.STB_REQUEST_TYPE;
							logger.info("********** DistributorSTBMapping WebService msg returned == "+ serviceMsg[0]+"  ::  "+serviceMsg[1]);
							
						}
						
					}
					else if(Flag == "N" || Flag.equalsIgnoreCase("N"))
					{
						if (stbNo.length() != 11 ) 
						{
							logger.info("********** Not 11 digit length, INVALID SERIAL NUMBER      :::  "+ stbNo);
							serviceMsg[0] = "Failure";
							serviceMsg[1] = com.ibm.dp.common.Constants.DIST_STB_MAP_WS_INVALID_STB;
							logger.info("********** DistributorSTBMapping WebService msg returned == "+ serviceMsg[0]+"  ::  "+serviceMsg[1]);
							return serviceMsg;
						}
						else if (stbNo == null || stbNo.equalsIgnoreCase("") )
						{
						
							logger.info("********** null SERIAL NUMBER      :::  "+ stbNo);
							serviceMsg[0] = "Failure";
							serviceMsg[1] = com.ibm.dp.common.Constants.DIST_STB_MAP_WS_INVALID_STB+ " " + stbNo;
							logger.info("********** DistributorSTBMapping WebService msg returned == "+ serviceMsg[0]+"  ::  "+serviceMsg[1]);
							return serviceMsg;
							
						}
						else 
						{
							boolean isNumeric = stbNo.matches("[0-9]+$");
							
							 if (!isNumeric ) 
							{
								logger.info("********** Non Numeric value, INVALID SERIAL NUMBER      :::  "+ stbNo);
								serviceMsg[0] = "Failure";
								serviceMsg[1] = com.ibm.dp.common.Constants.DIST_STB_MAP_WS_INVALID_STB;
								logger.info("********** DistributorSTBMapping WebService msg returned == "+ serviceMsg[0]+"  ::  "+serviceMsg[1]);
								return serviceMsg;
							} 
							else if (stbNo.indexOf("0") != 0) 
							{
								logger.info("**********Not started with 0, INVALID SERIAL NUMBER      :::  "+ stbNo);
								serviceMsg[0] = "Failure";
								serviceMsg[1] = com.ibm.dp.common.Constants.DIST_STB_MAP_WS_INVALID_STB;
								logger.info("********** DistributorSTBMapping WebService msg returned == "+ serviceMsg[0]+"  ::  "+serviceMsg[1]);
								return serviceMsg;

							} 
							
							else 
							{
								serviceMsg = stbService.checkMapping(distId, stbNo,distOLMId,requestType,productName);
								logger.info("********** DistributorSTBMapping WebService msg returned == "+ serviceMsg[0]+"  ::  "+serviceMsg[1]);
							}
						}
						
					}
					
					else if(Flag == "" || Flag.equalsIgnoreCase("") || Flag == null )
					{
						logger.info("********** FLAG IS NULL  ********** ");
						serviceMsg[0] = "Failure";
						serviceMsg[1] = com.ibm.dp.common.Constants.ANDR_FLAG_IS_NULL;
						logger.info("********** DistributorSTBMapping WebService msg returned == "+ serviceMsg[0]+"  ::  "+serviceMsg[1]);
					
					}
					else
					{
						logger.info("********** FLAG IS NOT MATCHES  ********** ");
						
						serviceMsg[0] = "Failure";
						serviceMsg[1] = com.ibm.dp.common.Constants.ANDR_FLAG_IS_NULL;
						logger.info("********** DistributorSTBMapping WebService msg returned == "+ serviceMsg[0]+"  ::  "+serviceMsg[1]);
						
					}

				} 
				else 
				{
					logger.info("**********INVALID OLM ID     :::  "+ distOLMId);
					serviceMsg[0] = "Failure";
					serviceMsg[1] = com.ibm.dp.common.Constants.DIST_STB_MAP_WS_INVALID_OLM_ID+ " " + distOLMId;
					
					logger.info("********** DistributorSTBMapping WebService msg returned == "+ serviceMsg[0]+"  ::  "+serviceMsg[1]);
					return serviceMsg;
				}
			}
		} 
		catch (Exception e) 
		{
			logger.error("************ Exception in DistributorSTBMapping WebService -------->",e);
			e.printStackTrace();
			serviceMsg[0] = "Failure";
			serviceMsg[1] = "Error in WebService";
			logger.info("********** DistributorSTBMapping WebService msg returned == "+ serviceMsg[0]+"  ::  "+serviceMsg[1]);
		}
		return serviceMsg;
	}

}
