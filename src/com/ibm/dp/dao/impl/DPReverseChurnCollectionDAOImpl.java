package com.ibm.dp.dao.impl;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

import org.apache.log4j.Logger;
import org.apache.struts.upload.FormFile;

import com.ibm.dp.common.Constants;
import com.ibm.dp.common.DBQueries;
import com.ibm.dp.cpedp.DPCPEDBConnection;
import com.ibm.dp.dao.DPReverseChurnCollectionDAO;
import com.ibm.dp.dto.DPReverseChurnCollectionDTO;
import com.ibm.utilities.Utility;
import com.ibm.utilities.ValidatorUtility;
import com.ibm.virtualization.recharge.common.ResourceReader;
import com.ibm.virtualization.recharge.dao.rdbms.BaseDaoRdbms;
import com.ibm.virtualization.recharge.db.DBConnectionManager;
import com.ibm.virtualization.recharge.exception.DAOException;

public class DPReverseChurnCollectionDAOImpl extends BaseDaoRdbms implements DPReverseChurnCollectionDAO {


	private static Logger logger = Logger.getLogger(DPReverseChurnCollectionDAOImpl.class.getName());

	String stbList = "";

	public DPReverseChurnCollectionDAOImpl(Connection connection) 
	{
		super(connection);
	}

	public ArrayList<DPReverseChurnCollectionDTO> viewChurnDetails(long accountID)throws DAOException  
	{
		ResultSet rst = null;
		DPReverseChurnCollectionDTO dto;
		PreparedStatement pst = null;
		ArrayList<DPReverseChurnCollectionDTO> retChurnList = new ArrayList<DPReverseChurnCollectionDTO>();
		
		try 
		{
			String sql = DBQueries.GET_CHURN_PENDING_LIST;
			//logger.info("QUERY TO GET CHURN COLLECTION RECORDS  ::  "+sql);
			//logger.info("USER TO GET CHURN COLLECTION RECORDS  ::  "+accountID);
			pst = connection.prepareStatement(sql);
			pst.setLong(1, accountID);
			
			rst = pst.executeQuery();

			while (rst.next()) 
			{
				dto = new DPReverseChurnCollectionDTO();
				dto.setSerial_Number(rst.getString("SERIAL_NUMBER"));
				dto.setProduct_Name(rst.getString("PRODUCT_NAME"));
				dto.setVc_Id(rst.getString("VC_ID"));
				dto.setCustomer_Id(rst.getString("CUSTOMER_ID"));
				dto.setSi_Id(rst.getString("SI_ID"));
				SimpleDateFormat sdf = new SimpleDateFormat("dd-MMM-yyyy");	
				if(rst.getDate("CREATED_ON") != null) {
					dto.setCollectionDate(sdf.format(rst.getDate("CREATED_ON")));
					
				} else {
					dto.setCollectionDate("");
				}
				dto.setIntReqID(rst.getInt("REQ_ID"));
				retChurnList.add(dto);
			}
			
			return retChurnList;
		} 
		catch (SQLException e) 
		{
		    e.printStackTrace();
			logger.error("SQL Exception occured while Getting Churn Pending List " + "  Exception Message: ", e);
			throw new DAOException(e.getMessage());
		} 
		catch (Exception ex) 
		{
			ex.printStackTrace();
			logger.error(" **: ERROR WHILE GETTING PENDING CHURN LIST ");
			throw new DAOException(ex.getMessage());
		} 
		finally 
		{
			/* Close the statement,resultset */
			DBConnectionManager.releaseResources(pst,rst);
		}
	}
	
	public List uploadExcel(FormFile myfile) throws DAOException 
	{
		Connection conCPE =null;
		PreparedStatement pstatcpe = null;
		ResultSet rscpe=null;

		String statusSrNo=null;
		List list = new ArrayList();
		List Error_list = new ArrayList();
		DPReverseChurnCollectionDTO churnDTO = new DPReverseChurnCollectionDTO();
		boolean validateFlag = true;
		String data = "";
		ArrayList checkDuplicate = new ArrayList();
		String stb_no = "";
		try
		{
			conCPE = DPCPEDBConnection.getDBConnectionCPE();
			String cpeUser= ResourceReader
			.getCoreResourceBundleValue(com.ibm.virtualization.recharge.common.Constants.DP_CPE_USER);
			pstatcpe=conCPE.prepareStatement("Select CPE_INVENTORY_STATUS_KEY, ACCOUNT_NUMBER from "+cpeUser+".cpe_inventory_details where ASSET_SERIAL_NO = ? and ASSET_TYPE='STB' ");

			boolean isEmpty = true;
			InputStream stream = myfile.getInputStream();

			ByteArrayOutputStream baos = new ByteArrayOutputStream();

			int size = (int) myfile.getFileSize();
			byte buffer[] = new byte[size];
			int bytesRead = 0;
			// read the input
			while ((bytesRead = stream.read(buffer, 0, size)) != -1)
			{
				baos.write(buffer, 0, bytesRead);
			}

			data = new String(baos.toByteArray());
			int k = 0;

			String newdata = data.replace(",", ",");
			StringTokenizer st = new StringTokenizer(newdata, "");

			while (st.hasMoreTokens())
			{
				isEmpty = false;
				churnDTO = new DPReverseChurnCollectionDTO();
				try
				{
					stb_no = st.nextToken(",\n\r").toString().trim();
				}
				catch (Exception e) 
				{
					churnDTO.setErr_msg("Invalid data in File");
	            	churnDTO.setSerial_Number(" ");
	            	Error_list.add(churnDTO);
					validateFlag = false;
				}
				
				boolean validate = validateCell(stb_no);

//				if(stb_no != null && "".equals(stb_no.trim()))
//	            {
//					churnDTO.setErr_msg("Blank space not allowed in Serial Number");
//	            	churnDTO.setSerial_Number(stb_no+" ");
//	            	Error_list.add(churnDTO);
//					validateFlag = false;
//	            }
//	            else 
	            if(checkDuplicate.contains(stb_no))
          		{
	            	churnDTO.setErr_msg("Duplicate Serial Number");
	            	churnDTO.setSerial_Number(stb_no+" ");
	            	Error_list.add(churnDTO);
					validateFlag = false;
          		}
				else if(!validate)
				{
					churnDTO.setErr_msg("Invalid Serial Number. It should be 11 digit number.");
					churnDTO.setSerial_Number(stb_no + " ");
					Error_list.add(churnDTO);
					validateFlag = false;
				}
				else if (!validateCellForZero(stb_no))
				{
					churnDTO.setSerial_Number(stb_no + " ");
					churnDTO.setErr_msg("Invalid Serial Number.It should be start with 0 ");
					// list.add(dpPoAcceptanceBulkDTO);
					Error_list.add(churnDTO);
					validateFlag = false;
				}
				else
				{
					statusSrNo=	Utility.churnStbPresentinDP( connection, stb_no);

					if (statusSrNo !=null && !statusSrNo.equalsIgnoreCase("") )
					{
					    churnDTO.setSerial_Number(stb_no + " ");
						churnDTO.setErr_msg(statusSrNo);
						Error_list.add(churnDTO);
						validateFlag = false;
					}
					else
					{
						pstatcpe.setString(1, stb_no);
						rscpe=pstatcpe.executeQuery();

						if(rscpe.next())
						{
							int cpestatus = rscpe.getInt("CPE_INVENTORY_STATUS_KEY");
							String SI_ID = rscpe.getString("ACCOUNT_NUMBER");
							//logger.info("SI ID from CPE  ::  "+SI_ID );
							//Integer intProductCPE = rscpe.getInt("STB_TYPE");
							
							if (cpestatus==Constants.CPE_STATUS_UNASSIGNED )
							{
								churnDTO.setSerial_Number(stb_no + " ");
								churnDTO.setErr_msg("STB Status is unassigned in CPE.");
								Error_list.add(churnDTO);
								validateFlag = false;
							}
							else if(cpestatus == Constants.CPE_STATUS_RESERVED)
							{
								churnDTO.setSerial_Number(stb_no + " ");
								churnDTO.setErr_msg("STB Status is reserved in CPE.");
								Error_list.add(churnDTO);
								validateFlag = false;
							}
							else if(cpestatus == Constants.CPE_STATUS_REPAIRED)
							{
								churnDTO.setSerial_Number(stb_no + " ");
								churnDTO.setErr_msg("STB Status is repaired in CPE.");
								Error_list.add(churnDTO);
								validateFlag = false;
							}
							else if(cpestatus != Constants.CPE_STATUS_ASSIGNED 
									&& cpestatus != Constants.CPE_STATUS_RESTRICTED)
							{
								churnDTO.setSerial_Number(stb_no + " ");
								churnDTO.setErr_msg("Invalid STB status in CPE.");
								Error_list.add(churnDTO);
								validateFlag = false;
							}
//							else if(SI_ID==null || SI_ID.trim().equals(""))
//							{
//								churnDTO.setSerial_Number(stb_no + " ");
//								churnDTO.setErr_msg("STB does not belong to any customer");
//								Error_list.add(churnDTO);
//								validateFlag = false;
//							}
							else
							{
								checkDuplicate.add(stb_no);
								churnDTO.setSerial_Number(stb_no + " ");
								churnDTO.setErr_msg(com.ibm.virtualization.recharge.common.Constants.CHURN_STOCK_OK_MSG);
								Error_list.add(churnDTO);
							}
					    }
						else
						{
							churnDTO.setSerial_Number(stb_no + " ");
							churnDTO.setErr_msg("The Serial Number doesn't exist in CPE. ");
							Error_list.add(churnDTO);
							validateFlag = false;
						}
					}
					k++;
				}
				
			}
			if (isEmpty)
			{
				churnDTO.setErr_msg("Empty File Uploaded");
				churnDTO.setSerial_Number(" ");
				Error_list.add(churnDTO);
				list = Error_list;
			}
			else
			{
				if (validateFlag)
				{
					churnDTO.setList_srno(checkDuplicate);
					list.add(churnDTO);
				}
				else
				{
					list = Error_list;
				}
			}
		}
		catch (Exception e)
		{
			e.printStackTrace();
			logger.info("Exception while Excel upload  ::  "+e.getMessage());
			throw new DAOException ("Exception while Excel upload  ::  "+e.getMessage());
		}
		finally
		{
			DPCPEDBConnection.releaseResources(pstatcpe,rscpe);
			DPCPEDBConnection.releaseResources(conCPE);
		}
		return list;
	}

	public boolean validateCell(String str)
	{
		if (str.length() != Constants.C2S_BULK_UPLOAD_NO_LENGTH)
		{
			return false;
		}
		else if (!ValidatorUtility.isValidNumber(str))
		{
			return false;
		}

		return true;
	}

	public boolean validateCellForZero(String str) {
		if (str.startsWith("0"))
			return true;
		else
			return false;
	}

	public ArrayList insertFreshChurnSTB(DPReverseChurnCollectionDTO churnDTO, Long lnUserID, int circleId)throws DAOException 
	{
		Connection conCPE=null;
		ArrayList serialNosList = new ArrayList();

		PreparedStatement psCPE = null;
		PreparedStatement psI = null;
		ResultSet rsCPE = null;

		PreparedStatement psItem = null; 
		ResultSet rsItem = null;

		DPReverseChurnCollectionDTO churnBulk = null;
		
		int productId=0;

		String stbList = churnDTO.getSerial_Number();
		try 
		{
			conCPE = DPCPEDBConnection.getDBConnectionCPE();
			String cpeUser= ResourceReader
			.getCoreResourceBundleValue(com.ibm.virtualization.recharge.common.Constants.DP_CPE_USER);
			
			String query="Select STB_TYPE AS ITEM_CODE, cid.CPE_INVENTORY_STATUS_KEY, cid.ASSET_SERIAL_NO,  " +
					"cid.ACCOUNT_NUMBER , (TRUNC(SYSDATE) - TRUNC(cid.DATE_OF_UPLOAD)) AS AGEING  , cid.DATE_OF_UPLOAD, " +
					"(select cpd.VC_ID from "+cpeUser+".cpe_pairing_details cpd where cpd.STB_ID = cid.ASSET_SERIAL_NO AND rownum=1) AS VC_ID, cid.SERVICE_ACTIVATION_DATE	" +
					"from "+cpeUser+".cpe_inventory_details cid	where cid.ASSET_SERIAL_NO in (" +stbList  +")";

			psCPE = conCPE.prepareStatement(query);
			rsCPE = psCPE.executeQuery();

			//Getting Product_ID
			String itemQuery="select PRODUCT_ID from DP_PRODUCT_MASTER where CM_STATUS='A' and PRODUCT_CATEGORY=" +
					"(select VALUE from DP_CONFIGURATION_DETAILS where CONFIG_ID=15 and ID=?) and PRODUCT_TYPE='0' and CIRCLE_ID=? with ur";
			psItem = connection.prepareStatement(itemQuery);

			//REQ_ID     SERIAL_NUMBER     PRODUCT_ID     VC_ID        CUSTOMER_ID     SI_ID           AGEING
			//REMARK   STATUS     CIRCLE_ID     CREATED_ON             CREATED_BY     ACTION_DATE
			//PURCHASE_DT    SERVICE_START_DT

			psI=connection.prepareStatement("INSERT INTO DP_REV_CHURN_INVENTORY " +
					"(SERIAL_NUMBER, PRODUCT_ID, VC_ID, CUSTOMER_ID, SI_ID, AGEING, STATUS, CIRCLE_ID, " +
					"CREATED_ON,  CREATED_BY, PURCHASE_DT, SERVICE_START_DT ) " +
					"VALUES (?,?,?,?,?,?,?,?,?,?,?,?)");

			while (rsCPE.next())
			{
				churnBulk = new DPReverseChurnCollectionDTO();
				String strCustomerrID = "";
				String strSI_ID = "";
				
				psItem.setString(1, rsCPE.getString("ITEM_CODE"));
				psItem.setInt(2, circleId);
				rsItem=psItem.executeQuery();

				while(rsItem.next())
				{
					productId=rsItem.getInt("PRODUCT_ID");
				}

				 //logger.info(" ASSET_SERIAL_NO :: "+rsCPE.getString("ASSET_SERIAL_NO"));
				 psI.setString(1, rsCPE.getString("ASSET_SERIAL_NO"));
				 //logger.info(" productId :: "+productId);
				 psI.setInt(2, productId);
				 //logger.info(" VC_ID :: "+rsCPE.getString("VC_ID"));
				 String strVC_ID = rsCPE.getString("VC_ID");
				 
				 if(strVC_ID == null || "".equals(strVC_ID))
					 strVC_ID = Constants.CPE_BLANK_DATA_MSG;
				 
	    		 psI.setString(3, strVC_ID) ;

	    		 strSI_ID = rsCPE.getString("ACCOUNT_NUMBER");
	    		 
	    		 String [] cust_SI_ID = null;

	    		 if(strSI_ID !=null && !"".equals(strSI_ID) && strSI_ID.indexOf("-")>0)
	    			 cust_SI_ID = strSI_ID.split("-");
	    		 else
	    			 strSI_ID = Constants.CPE_BLANK_DATA_MSG;

	    		 if(cust_SI_ID!=null)
	    			 strCustomerrID = cust_SI_ID[0];
	    		 else
	    			 strCustomerrID = strSI_ID;

	    		 //logger.info(" strCustomerrID :: "+strCustomerrID);
				  psI.setString(4, strCustomerrID) ;
				  //logger.info(" strSI_ID :: "+strSI_ID);
				  psI.setString(5, strSI_ID) ;
				  //logger.info(" AGEING :: "+rsCPE.getDouble("AGEING"));
				  psI.setLong(6, Math.round(rsCPE.getDouble("AGEING")));
				  psI.setString(7, "REC") ;
				  //logger.info(" circleId :: "+circleId);
				  psI.setInt(8, circleId);
				  //logger.info(" Created_on :: "+Utility.getCurrentTimeStamp());
	              psI.setTimestamp(9, Utility.getCurrentTimeStamp());
	              //logger.info(" createdBy :: "+lnUserID);
	              psI.setLong(10, lnUserID);
	              //logger.info(" DATE_OF_UPLOAD :: "+rsCPE.getString("DATE_OF_UPLOAD"));
	        	  psI.setTimestamp(11, rsCPE.getTimestamp("DATE_OF_UPLOAD"));
	        	  //logger.info(" SERVICE_ACTIVATION_DATE :: "+rsCPE.getString("SERVICE_ACTIVATION_DATE"));
	              psI.setTimestamp(12, rsCPE.getTimestamp("SERVICE_ACTIVATION_DATE"));

	              psI.executeUpdate();

	            churnBulk.setSerial_Number(rsCPE.getString("ASSET_SERIAL_NO"));
	      		serialNosList.add(churnBulk);
			}
		}
		catch (Exception e) 
		{
			e.printStackTrace();
			logger.error("Exception occured while inserting."
					+ "Exception Message: " + e.getMessage());
			throw new DAOException("Exception: " + e.getMessage());
		} 
		finally 
		{
			DPCPEDBConnection.releaseResources(conCPE);
			DPCPEDBConnection.releaseResources(psCPE, rsCPE);
			DBConnectionManager.releaseResources(psI, null);
			DBConnectionManager.releaseResources(psItem, null);
		}
		return serialNosList;
	}

	public ArrayList<DPReverseChurnCollectionDTO> reportChurnDao(String[] arrCheckedChurn, long loginUserId, String strRemarks)throws DAOException
	{		
		PreparedStatement pstmt = null;

		ArrayList<DPReverseChurnCollectionDTO> arrReturn = new ArrayList<DPReverseChurnCollectionDTO>();
		try
		{
			pstmt = connection.prepareStatement(DBQueries.SQL_REPORT_CHURN_HDR);

			int intAcceptChurn = arrCheckedChurn.length;

			for(int i=0; i<intAcceptChurn; i++) 
			{
				pstmt.setString(1, com.ibm.virtualization.recharge.common.Constants.COLLECTION_STATUS_TYPE_COL);
				pstmt.setString(2, strRemarks);	
				pstmt.setTimestamp(3, Utility.getCurrentTimeStamp());
				pstmt.setString(4, arrCheckedChurn[i]);
				pstmt.executeUpdate();
			}
			
			arrReturn = viewChurnDetails(loginUserId);
		}
		catch(Exception e)
		{
			e.printStackTrace();
			logger.error("Exception occured while Saving Delivery Challan  ::  "+e.getMessage());
			try
			{
				String err = ResourceReader.getCoreResourceBundleValue(Constants.CPE_Recovery_Critical);
				logger.info(err + "::" +Utility.getCurrentDate());
			}
			catch(Exception exp)
			{
				exp.printStackTrace();
			}
			throw new DAOException(e.getMessage());
		}
		finally
		{
			DBConnectionManager.releaseResources(pstmt, null);
		}
		return arrReturn;
	}

	public ArrayList<DPReverseChurnCollectionDTO> reportChurnAddToStockDao(String[] arrCheckedChurn, long loginUserId, String strRemarks)throws DAOException
	{
		//For fetching from DP_REV_CHURN_INVENTORY
		PreparedStatement pstStockInv = null; 

		//For Inserting into Dp_Rev_Churn_Inven_Hist
		PreparedStatement pstmtStock = null;

		PreparedStatement psUpdate = null;
		
		PreparedStatement pstChurn = null;
		ResultSet rst = null;

		ArrayList<DPReverseChurnCollectionDTO> arrReturn = new ArrayList<DPReverseChurnCollectionDTO>();
		try
		{
			pstChurn = connection.prepareStatement(DBQueries.GET_CHURN_STB_DETAIL);
			
			int intAcceptChurn = arrCheckedChurn.length;
			
			//"Update DP_REV_CHURN_INVENTORY set REMARK=?, ACTION_DATE=? where SERIAL_NUMBER= '" + arrCheckedChurn[i]+"'"
			psUpdate = connection.prepareStatement(DBQueries.SQL_REPORT_CHURN_HDR);
			
			pstStockInv = connection.prepareStatement(DBQueries.SQL_STOCK_REVERSE_INVENTORY_INSERT);
			
			//updating DP_Rev_Churn_Inventory
			for(int i=0; i<intAcceptChurn; i++)	
			{
				int intProdID = 0;
				String strSerialNo = "";
				psUpdate.setString(1, com.ibm.virtualization.recharge.common.Constants.COLLECTION_STATUS_TYPE_REU);
				psUpdate.setString(2, strRemarks);
				psUpdate.setTimestamp(3, Utility.getCurrentTimeStamp());
				psUpdate.setString(4,arrCheckedChurn[i]);
				psUpdate.executeUpdate();				//Updating DP_REV_CHURN_INVENTORY
				
				pstChurn.setLong(1, Long.parseLong(arrCheckedChurn[i]));
				rst = pstChurn.executeQuery();			//Getting STB info from DP_REV_CHURN_INVENTORY
				
				if(rst.next())
				{
					intProdID = rst.getInt("PRODUCT_ID");
					strSerialNo = rst.getString("SERIAL_NUMBER");
				}
				
				DBConnectionManager.releaseResources(null, rst);
				
				//PRODUCT_ID, SERIAL_NO, MARK_DAMAGED, DISTRIBUTOR_ID, DISTRIBUTOR_PURCHASE_DATE, INV_NO, STATUS
				pstStockInv.setInt(1, intProdID);
				pstStockInv.setString(2, strSerialNo);
				pstStockInv.setLong(3, loginUserId);
				pstStockInv.execute();				//inserting into DP_Stock_INVENTORY
				
			}
			
			DBConnectionManager.releaseResources(psUpdate, null);
			
			//Insering records into Churn History
			pstmtStock = connection.prepareStatement(DBQueries.INSERT_CHURN_HIST);
			
			int intInsert = pstmtStock.executeUpdate();	//Inserting into DP_REV_CHURN_INVENTORY_HIST
			
			//logger.info("Number of records moved to Churn History  ::  "+intInsert);
			
			DBConnectionManager.releaseResources(pstmtStock, null);
			
			//Deleting records from Churn History
			pstmtStock = connection.prepareStatement(DBQueries.DELETE_CHURN_INVENTORY);
			intInsert = pstmtStock.executeUpdate();		//Deleting from DP_REV_CHURN_INVENTORY
			
			//logger.info("Number of records deteted from Churn Inventory  ::  "+intInsert);
			
			arrReturn = viewChurnDetails(loginUserId);
		}
		catch(Exception e)
		{
			e.printStackTrace();
			logger.error("Exception occured while Saving Delivery Challan  ::  "+e.getMessage());
			try
			{
				String err = ResourceReader.getCoreResourceBundleValue(Constants.CPE_Recovery_Critical);
				logger.info(err + "::" +Utility.getCurrentDate());
			}
			catch(Exception exp)
			{
				exp.printStackTrace();
			}
			throw new DAOException(e.getMessage());
		}
		finally		
		{
			DBConnectionManager.releaseResources(psUpdate, null);
			DBConnectionManager.releaseResources(pstmtStock, null);
			DBConnectionManager.releaseResources(pstChurn, rst);
			DBConnectionManager.releaseResources(pstStockInv, null);
		}
		return arrReturn;
	}

		
	public String checkCPEInventory(String req_id)throws DAOException  
	{
		//logger.info("******checkCPEInventory DAOimpl**************");
		ResultSet rst = null;
		DPReverseChurnCollectionDTO dto;
		Connection conCPE=null;
		PreparedStatement pst=null;
		PreparedStatement pst1=null;
		StringTokenizer st = null;
		ResultSet rs=null;
		String invalid_sr_no="";
		String returnMsg="true";
		boolean flag=true;
		String stb_no="";
		String strstb_no = "";
		
		// to resolve the "not able to connect to CPE" issue
		String schemaName = ResourceReader.getValueFromBundle(com.ibm.virtualization.recharge.common.Constants.DB_DPDTH_CPE_SCHEMA, com.ibm.virtualization.recharge.common.Constants.WEBSERVICE_RESOURCE_BUNDLE);
		System.out.println("schemaName:::::::::::::::::::::::"+schemaName);
		String sql="select ASSET_SERIAL_NO from "+schemaName+"CPE_INVENTORY_DETAILS  where ASSET_SERIAL_NO=? and CPE_INVENTORY_STATUS_KEY = 3";
		System.out.println("sql:::::::::::::::::::::::"+sql);
		try 
		{
			conCPE = DPCPEDBConnection.getDBConnectionCPE();
			pst = conCPE.prepareStatement(sql);
			pst1 = connection.prepareStatement("select SERIAL_NUMBER from DP_REV_CHURN_INVENTORY where REQ_ID in( "+req_id+" ) with ur ");
			rs = pst1.executeQuery();
			while(rs.next())
			{
				//st = new StringTokenizer(stb_no,",");
				String sr_no="";
				//while(st.hasMoreElements())
				//{
				sr_no =  rs.getString("SERIAL_NUMBER");
				
				pst.setString(1,sr_no);
				rst = pst.executeQuery();
				//logger.info("sr_no:::"+sr_no);
				if(rst.next())
				{
					//logger.info("------------Found in CPE as Assigned------");
					invalid_sr_no = invalid_sr_no + rst.getString("ASSET_SERIAL_NO")+",";
					flag = false;
				}
				//logger.info("invalid_sr_no::::::::"+invalid_sr_no);
				//rst = null;
				
				DPCPEDBConnection.releaseResources(null, rst);
			}
			//}
			if(invalid_sr_no.indexOf(",") > 0 )
			{
				invalid_sr_no = invalid_sr_no.substring(0,invalid_sr_no.length()-1);
			}
			if(!flag)
			{
				returnMsg = invalid_sr_no +" STB is not disconnected and is in 'Assigned' status. Kindly get the STB disconnected before adding into Serialized Stock";;
			}
			else
			{
				returnMsg = "true";
			}

		} catch (SQLException e) {
			logger.error("SQL Exception oin checkCPEInventory "
							+ "Exception Message: ", e);
			throw new DAOException(e.getMessage());

		} catch (Exception ex) {
			ex.printStackTrace();
			logger.error(" **: checkCPEInventory OF -> :** "+ex);
			throw new DAOException(ex.getMessage());
		} finally {

				/* Close the statement,resultset */
				DBConnectionManager.releaseResources(pst1, rs);
				DPCPEDBConnection.releaseResources(pst, rst);
				DPCPEDBConnection.releaseResources(conCPE);
		}
		return returnMsg;
	}


}
