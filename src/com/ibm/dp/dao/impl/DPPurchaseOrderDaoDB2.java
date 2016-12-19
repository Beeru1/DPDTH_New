package com.ibm.dp.dao.impl;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.ResourceBundle;
import java.util.Set;

import org.apache.log4j.Logger;
import org.apache.struts.util.LabelValueBean;

import com.ibm.dp.common.Constants;
import com.ibm.dp.common.DBQueries;
import com.ibm.dp.beans.DPPurchaseOrderFormBean;
import com.ibm.dp.dao.DPPurchaseOrderDao;
import com.ibm.dp.dto.DPDeliveryChallanAcceptDTO;
import com.ibm.dp.dto.DPInterSSDTransferBulkUploadDto;
import com.ibm.dp.dto.DPPurchaseOrderDTO;
import com.ibm.dp.dto.SMSDto;
import com.ibm.dp.exception.DPServiceException;
import com.ibm.dp.sms.SMSUtility;
import com.ibm.hbo.common.HBOConstants;
import com.ibm.virtualization.recharge.common.ExceptionCode;
import com.ibm.virtualization.recharge.common.Utility;
import com.ibm.virtualization.recharge.dao.rdbms.BaseDaoRdbms;
import com.ibm.virtualization.recharge.db.DBConnectionManager;
import com.ibm.virtualization.recharge.exception.DAOException;
/**
 * DPPurchaseOrderDaoDB2 class is core class for interacting with DB layer for Purchase Order Requisition
 * * @author Rohit Kunder
 */

public class DPPurchaseOrderDaoDB2 extends BaseDaoRdbms implements DPPurchaseOrderDao{ 	

	private static Logger log = Logger.getLogger(DPPurchaseOrderDaoDB2.class
			.getClass());

	protected static final String SQL_BUSINESSCATEGORY_KEY = "SQL_BUSINESSCATEGORY";

	protected static final String SQL_BUSINESSCATEGORY = DBQueries.SQL_BUSINESSCATEGORY;

	protected static final String SQL_PRODUCTNAMElIST_KEY = "SQL_PRODUCTNAMElIST_KEY";

	protected static final String SQL_PRODUCTNAMElIST = DBQueries.SQL_PRODUCTNAMElIST;

	protected static final String SQL_PO_VIEW = DBQueries.SQL_PO_VIEW;
	
	protected static final String SQL_GET_ACCOUNT_LEVEL = DBQueries.SQL_GET_ACCOUNT_LEVEL;
	
	protected static final String SQL_PO_VIEW_ZBM = DBQueries.SQL_GET_PO_LIST_ZBM;
	
	//protected static final String SQL_PO_VIEW_CIRCLE = DBQueries.SQL_PO_VIEW_CIRCLE;
	
	protected static final String SQL_SEQ_VALUE = DBQueries.SQL_SEQ_VALUE;
	
	protected static final String SQL_CANCELPORNUMBER_KEY = "SQL_CANCELPORNUMBER";

	protected static final String SQL_CANCEL_PORNO = DBQueries.SQL_CANCEL_PORNO ;
	
	protected static final String SQL_SELECT_PONO = DBQueries.SQL_SELECT_PONO ;
	
	protected static final String SQL_CANCEL_PONO = DBQueries.SQL_CANCEL_PONO ;
		
	protected static final String SQL_INSERT_PR_HEADER_KEY = "SQL_INSERT_PR_HEADER";

	protected static final String SQL_INSERT_PR_HEADER = DBQueries.SQL_INSERT_PR_HEADER;
	
	protected static final String SQL_INSERT_PR_DETAILS_KEY = "SQL_INSERT_PR_DETAILS";

	protected static final String SQL_INSERT_PR_DETAILS = DBQueries.SQL_INSERT_PR_DETAILS;
	
	protected static final String SQL_SELECT_EXT_DIST_ID_KEY = "SQL_SELECT_EXT_DIST_ID";

	protected static final String SQL_SELECT_EXT_DIST_ID = DBQueries.SQL_SELECT_EXT_DIST_ID;
	
	protected static final String SQL_SELECT_EXT_PRODUCT_ID_KEY = DBQueries.SQL_SELECT_EXT_PRODUCT_ID ;
	
	protected static final String SQL_SELECT_EXT_PRODUCT_ID = DBQueries.SQL_INSERT_PR_DETAILS;//
	
	protected static final String SQL_INSERT_PO_SEQ = DBQueries.SQL_INSERT_PO_SEQ;
	
	protected static final String SQL_COUNT = DBQueries.SQL_COUNT;
	
	protected static final String SQL_SELECT_PO_STATUS = DBQueries.SQL_SELECT_PO_STATUS;

	
	protected static final String SQL_PRODUCTNAMElIST_KEY_2 = "SQL_PRODUCTNAMElIST_KEY_2";
	protected static final String SQL_PRODUCTNAMElIST_2 = DBQueries.SQL_PRODUCTNAMElIST_2;
	public DPPurchaseOrderDaoDB2(Connection con) {
		super(con);

		queryMap.put(SQL_BUSINESSCATEGORY_KEY, SQL_BUSINESSCATEGORY);
		queryMap.put(SQL_PRODUCTNAMElIST_KEY, SQL_PRODUCTNAMElIST);
		queryMap.put(SQL_PRODUCTNAMElIST_KEY_2, SQL_PRODUCTNAMElIST_2);
		queryMap.put(SQL_INSERT_PR_HEADER_KEY, SQL_INSERT_PR_HEADER);
		queryMap.put(SQL_INSERT_PR_DETAILS_KEY, SQL_INSERT_PR_DETAILS);
		queryMap.put(SQL_SELECT_EXT_PRODUCT_ID_KEY, SQL_SELECT_EXT_PRODUCT_ID);
		queryMap.put(SQL_SELECT_EXT_DIST_ID_KEY, SQL_SELECT_EXT_DIST_ID);

	} 
	
	/**
	 * Get Business Category List from DP_BUSINESS_CATEGORY_MASTER
	 * */
	
	public ArrayList getBusinessCategoryDao() throws DAOException {
		
		Statement stmt = null;
		ResultSet rs = null;
		ArrayList bcList = null;

				try {

			stmt = connection.createStatement();
			rs = stmt.executeQuery(queryMap.get(SQL_BUSINESSCATEGORY_KEY));
			bcList = fetchSingleResult(rs);
		} catch (SQLException e) {
			log.error("SQL Exception occured while inserting. Function : getBusinessCategoryDao()"
					+ "Exception Message:  " + e.getMessage());
			throw new DAOException(e.getMessage());

		} catch (Exception e) {
			log.error("Exception occured while inserting."
					+ "Exception Message: Function : getBusinessCategoryDao()" + e.getMessage());
			throw new DAOException(e.getMessage());

		} finally {
			
			/* Close the statement,resultset */
			DBConnectionManager.releaseResources(stmt, rs);
		}
		return bcList;
	}

	/**
	 * Get Product List from DP_PRODUCT_MASTER
	 * */
	public ArrayList getProductNameDao(String circleID,int selectedValue,int productTypeValue)throws DAOException {

		PreparedStatement pstmt = null;
		PreparedStatement pstmtID = null;
		ResultSet rs = null;
		ArrayList productList = null;
		try {
			String sql="";
			ResourceBundle rb = ResourceBundle.getBundle("com.ibm.dp.resources.DPResources");
			String configId =rb.getString("android.configId");

		    String QueryForGetProductId = "select DD_VALUE from DP_CONFIGURATION_DETAILS where CONFIG_ID = ? with ur";
		    
			String QueryForAndProd = "SELECT PRODUCT_ID, PRODUCT_NAME FROM DP_PRODUCT_MASTER  WHERE circle_id=?"
				+" and CATEGORY_CODE = ?  and CM_STATUS ='"+Constants.ACTIVE_STATUS+"' and PRODUCT_TYPE = 1 and PRODUCT_CATEGORY in (select VALUE from "
				+" DP_CONFIGURATION_DETAILS where ID in ("+configId+")) with ur ";
		    

			if(productTypeValue == 1)
			{
				//productList = null;
				sql=QueryForAndProd;
			}
			else
			{
			//	sql=queryMap.get(SQL_PRODUCTNAMElIST_KEY_2);
				//productList = null;
				sql = "select PRODUCT_ID,PRODUCT_NAME from DP_PRODUCT_MASTER where circle_id=? and  CATEGORY_CODE = ?  and  CM_STATUS ='"+Constants.ACTIVE_STATUS+"'"+
					" and PRODUCT_ID not in (select PRODUCT_ID from DP_PRODUCT_MASTER where PRODUCT_NAME  in  (select VALUE from " 
					+" DP_CONFIGURATION_DETAILS where ID in ("+configId+")) and PRODUCT_TYPE = 1 with ur )";
			}
			
			//System.out.println(" Sqlllll  ==== "+sql);
			pstmt = connection.prepareStatement(sql);
			pstmt.setString(1,circleID);
			pstmt.setInt(2,selectedValue);
			rs = pstmt.executeQuery();
			productList = ProductListfetchResult(rs);
		
			
		} catch (SQLException e) {
				log.error("SQL Exception occured while inserting."
					+ "Exception Message: " + e.getMessage());
				throw new DAOException(e.getMessage());

		} catch (Exception e) {
				log.error("Exception occured while inserting."
					+ "Exception Message: " + e.getMessage());
				throw new DAOException(e.getMessage());

		} finally {
			
			/* Close the statement,resultset */
			DBConnectionManager.releaseResources(pstmt, rs);
		}
		return productList;
	}

	
	protected ArrayList fetchSingleResult(ResultSet rs) throws Exception {
		
		ArrayList arrMasterData = new ArrayList();
		LabelValueBean lvb = null;
		while (rs.next()) {
			lvb = new LabelValueBean(rs.getString("CATEGORY_NAME"), rs.getString("CATEGORY_CODE"));
			arrMasterData.add(lvb);
		} //end-of-while 
		
		return arrMasterData;
	}

	protected ArrayList ProductListfetchResult(ResultSet rs) throws Exception {

		DPPurchaseOrderDTO ProductNameDTO = null;
		ArrayList arrMasterData = new ArrayList();

		while (rs.next()) {
			ProductNameDTO = new DPPurchaseOrderDTO();
			ProductNameDTO.setProductName(rs.getString("PRODUCT_NAME"));
			ProductNameDTO.setProductCode(rs.getString("PRODUCT_ID"));
			arrMasterData.add(ProductNameDTO);
		} //end-of-while 
		
		return arrMasterData;
	}
	
	/**
	 * Function for Cancel POR No
	 * */
	
	public void caneclPORNODAO(int pronumber,int productno)throws DAOException {
	
		ResultSet rs=null;
		PreparedStatement pst = null;
		int numRows = 0;
		ArrayList poNos = new ArrayList(); 
		int i=0;
		try {
			pst = connection.prepareStatement(SQL_CANCEL_PORNO);
			pst.setInt(1, pronumber);
			numRows = pst.executeUpdate();
			pst = connection.prepareStatement(SQL_SELECT_PONO);
			pst.setInt(1, pronumber);
			rs=pst.executeQuery();
			while(rs.next()){
				poNos.add(rs.getString("PO_NO"));
			}
			for(int j=0;j<poNos.size();j++){
				pst = connection.prepareStatement(SQL_CANCEL_PONO);
				pst.setString(1, (String)poNos.get(j));
				numRows = pst.executeUpdate();
			}
			connection.commit();

		} catch (SQLException e) {
			try {
				connection.rollback();
			} catch (SQLException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			throw new DAOException(e.getMessage());
		} catch (Exception e) {
			try {
				connection.rollback();
			} catch (SQLException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			log.error("INside caneclPORNODAO Method Exception Error :" + e);
			throw new DAOException(e.getMessage());
		} 
		finally {
			/* Close the statement,resultset */
			DBConnectionManager.releaseResources(pst, rs);
		}
		
	}
		
/**
 *  Insert value into Purchase Order releated Tables
 * */	
	
	public String insertPurchaseOrder(ArrayList porList,int loginID,String comments) throws DAOException {
		
		PreparedStatement pst = null;
		ResultSet seq_RS = null;
		ResultSet extProductId_RS = null;
		ResultSet extDistId_RS = null;
		String message=Constants.SUCCESS_MESSAGE;
		PreparedStatement pstExtDistId = null;
		int transactionType =0;
		try {
			/**
			 * Fetch sequence value
			 * */
			pst = connection.prepareStatement(SQL_SEQ_VALUE);
			seq_RS = pst.executeQuery();
			int por_seqId = 0;
			if (seq_RS == null) {
				log.info("Result set Null while fetching Sequence");
			}
			while (seq_RS.next()) {
				por_seqId = seq_RS.getInt(1);
			
			}
			pst.clearParameters();
			//seq_RS.close();
			/** 
			 * Fetch EXT_PRODUCT_ID value
			 * */
			
			HashMap prdmap = null;
			String tempPrdID ="" ;
			String CATEGORY_CODE = "";
			
			ArrayList listExtPrdID = new ArrayList();
			try {
				System.out.println(" 111111111111 insertPurchaseOrder :");
				pst = connection.prepareStatement(SQL_SELECT_EXT_PRODUCT_ID_KEY);
				for (int i = 0; i < porList.size(); i++) 
				{
					prdmap = (HashMap)porList.get(i);
					pst.setInt(1,Integer.parseInt(prdmap.get("PRODUCT_CODE").toString()));
					extProductId_RS = pst.executeQuery();
							
						if (extProductId_RS == null) {
							log.info("Result set Null while fetching extDistId");
							message = Constants.ERROR_PRODUCT_NOT_PROCESSED; 
							return message;
						}
						while (extProductId_RS.next()) { 
							if(extProductId_RS.getString(1) ==null){
								log.info("in null conditon of external product id ");
								message = Constants.ERROR_PRODUCT_NOT_PROCESSED; 
								return message;
							}
							tempPrdID = extProductId_RS.getString(1);
							CATEGORY_CODE = extProductId_RS.getString(2);
							listExtPrdID.add(tempPrdID);
						}
				}//for

				
			} catch (SQLException e) {
				log.error("INside  insertPurchaseOrder(ArrayList porList,int loginID) Method SQL Error :" + e);
				throw new DAOException(e.getMessage());
			} catch (Exception e) {
				log.error("INside  insertPurchaseOrder(ArrayList porList,int loginID) Method Exception Error :" + e);
				throw new DAOException(e.getMessage());
			} 
			
			if(CATEGORY_CODE != null && CATEGORY_CODE.equalsIgnoreCase(Constants.PRODUCT_CATEGORY_CPE))
			{
				transactionType =2;
			}else{
				transactionType =1;
			}
			
			/**
			 * Fetch EXT_DIST_ID value
			 * */
			pstExtDistId = connection.prepareStatement(SQL_SELECT_EXT_DIST_ID);
			pstExtDistId.setInt(1, loginID);
			pstExtDistId.setInt(2, transactionType);
			extDistId_RS = pstExtDistId.executeQuery();
			
			String por_extDistId = "";
			if (extDistId_RS == null) {
				log.info("Result set Null while fetching extDistId");
			}
			while (extDistId_RS.next()) {
				por_extDistId = extDistId_RS.getString(1);
			}
			pstExtDistId.clearParameters();
			
			
			try {
				pst = connection.prepareStatement(queryMap.get(SQL_INSERT_PR_HEADER_KEY));
				System.out.println(por_seqId+"loginID"+loginID+"por_extDistId"+por_extDistId+"comments"+comments+"CATEGORY_CODE"+CATEGORY_CODE);
				pst.setInt(1, por_seqId);
				pst.setInt(2, loginID);
				pst.setString(3, por_extDistId);
				pst.setString(4, comments);
				pst.setString(5, CATEGORY_CODE);
				pst.executeUpdate();
				 
				pst.clearParameters();
			
			} catch (SQLException e) {
				e.printStackTrace();
				log.error("INside  insertPurchaseOrder(ArrayList porList,int loginID) Method SQL Error [queryMap.get(SQL_INSERT_PR_HEADER_KEY)] :" + e);
				throw new DAOException(e.getMessage());
			} catch (Exception e) {
				log.error("INside  insertPurchaseOrder(ArrayList porList,int loginID) Method Exception Error :" + e);
				throw new DAOException(e.getMessage());
			}
				pst = connection.prepareStatement(queryMap.get(SQL_INSERT_PR_DETAILS_KEY));
				HashMap map = null;
				int insertCheck = 0;
				
				for (int i = 0; i < porList.size(); i++) 
				{
					map = (HashMap) porList.get(i);
					//System.out.println((String)map.get("maxPOQTY")+"MAX PO ========="+Math.round(new Float((String)map.get("maxPOQTY"))));//comment it 
					pst.setInt(1, por_seqId);
					pst.setInt(2, Integer.parseInt(map.get("PRODUCT_CODE").toString()));
					pst.setString(3,listExtPrdID.get(i).toString()); 
					pst.setInt(4, Integer.parseInt(map.get("QUANTITY").toString()));
					pst.setInt(5, Integer.parseInt(map.get("CIRCLE_ID").toString()));
					pst.setString(6, (String) map.get("INHANDQUANTITY")); //Added by Mohammad Aslam
					pst.setString(7, (String) map.get("DPQUANTITY")); //Added by Mohammad Aslam
					pst.setString(8, (String) map.get("openStkQty")); // Added by Harbans
										
					pst.setDouble(9, Double.parseDouble((String)map.get("eligibleAmt"))); 
					pst.setInt(10, Math.round(new Float((String)map.get("eligibilityQty")))); 
					
					
					if(CATEGORY_CODE != null && CATEGORY_CODE.equalsIgnoreCase(Constants.PRODUCT_CATEGORY_CPE))
					{
						//log.info("MAX PO ===="+Math.round(new Float((String)map.get("maxPOQTY"))));//comment it 
						// For CPE type products
						pst.setInt(11, Math.round(new Float((String)map.get("maxPOQTY")))); 
						pst.setInt(12, Integer.parseInt((String)map.get("flagNegative")));
					}
					else
					{
						//for other than CPE type products
						pst.setInt(11, 0); 
						pst.setInt(12, 0);
					}
					
					pst.setInt(13, Integer.parseInt(map.get("QUANTITY").toString()));
					insertCheck = pst.executeUpdate();
				}
				/**
				 * ********************************ALERT MANAGEMENT********************************
				 */
				
				log.info("*********************Sending SMS alerts***********************");
				/*String mobileno=null;
				if(message.equalsIgnoreCase(Constants.SUCCESS_MESSAGE))
				{
					//System.out.println("2222222222 Loghin id  :"+loginID);
					SMSDto sMSDto = null;
					sMSDto = SMSUtility.getUserDetails(String.valueOf(loginID), connection);
					mobileno=SMSUtility.getMobileNoForSmsOfTSM(Long.valueOf(loginID), com.ibm.virtualization.recharge.common.Constants.CONFIRM_ID_CREATE_PO, connection);
					if(mobileno!=null)
					{
						//System.out.println("33333333333333333");
					String SMSmessage=SMSUtility.createMessageContent(com.ibm.virtualization.recharge.common.Constants.CONFIRM_ID_CREATE_PO, sMSDto, connection);
					
					if(SMSmessage != null && !SMSmessage.equalsIgnoreCase(""))
					{
						//System.out.println("inserting in DP_SEND_SMS===");
						SMSUtility.saveSMSInDB(connection, new String[] {mobileno}, SMSmessage,com.ibm.virtualization.recharge.common.Constants.CONFIRM_ID_CREATE_PO);
					}
				}
					
					
					log.info("*********************Sending SMS alerts ends***********************");
					
					/**
					 * ********************************ALERT MANAGEMENT ENDS********************************
					 */
				//}
				
			connection.commit();
			
			} catch (SQLException e) {
				log.error("SQL Exception occured while inserting.Into PURCHASE ORDER REQUEST Tables"
						+ "Exception Message: ", e);
				message = "error";
				throw new DAOException(ExceptionCode.ERROR_DB_INTERNAL+e.getMessage());
			
		}catch(Exception ex)
		{
			ex.printStackTrace();
			log.error("Exception occured while inserting into PURCHASE ORDER REQUEST Tables"
					+ "Exception Message: ", ex);
			message = "error";
			throw new DAOException(ExceptionCode.ERROR_DB_INTERNAL+ex.getMessage());
		}
			finally {
			/* Close the statement,resultset */
			DBConnectionManager.releaseResources(pst, seq_RS);
			DBConnectionManager.releaseResources(null, extProductId_RS);
			DBConnectionManager.releaseResources(pstExtDistId, extDistId_RS);
		}
			return message;
		
	}

	/**
	 * Function is for fetching the details and status of purchase order requisition 
	 */	
	public ArrayList viewPODetails(long accountID,int circleID,int lowerbound, int upperbound,String status)throws DAOException  {
	
		ResultSet rst = null;
		DPPurchaseOrderDTO dto;
		
		PreparedStatement pst = null, pstmtID = null;
		ArrayList<DPPurchaseOrderDTO> POList = new ArrayList<DPPurchaseOrderDTO>();
		ArrayList countList=new ArrayList();
		int oldPrNO=0;
		int newPrNO=0;
		String sql="";
		String groupName = "";
		try {
			//if(roleName.equalsIgnoreCase(HBOConstants.ROLE_DIST))
				sql = SQL_GET_ACCOUNT_LEVEL;
				sql += " WITH UR";
				pst = connection.prepareStatement(sql);
				pst.setLong(1, accountID);
				rst=pst.executeQuery();
				while(rst.next()){
					groupName = rst.getString("GROUP_NAME");
				}
				if(groupName.equalsIgnoreCase("ZonalBusinessManager")){
					sql = SQL_PO_VIEW_ZBM;
				}
				else{
					sql = SQL_PO_VIEW;
				}
					
					
			//else if(roleName.equalsIgnoreCase(HBOConstants.ROLE_CIRCLE))
				
			
			if(lowerbound != -1 && upperbound != -1) {
				sql = sql += " where  rx<=? and rx>=?";
			}
		
			
			sql += " )  records ";
			
			//System.out.println("status=............"+status);
			if(status!=null && !status.equals("0")){
				sql += "  where records.My_status in (select PO_STATUS_ID from DP_PO_STATUS_MAP where VIEW_PO_ID=? ) WITH UR";
			}else{
				sql += "  WITH UR";
			}
			pst = connection.prepareStatement(sql);
			//Added by harpreet
			
			pst.setLong(1, accountID);
			if(lowerbound != -1 && upperbound != -1) {
				pst.setString(2, String.valueOf(upperbound));
				pst.setString(3, String.valueOf(lowerbound ));
				if(status!=null && !status.equals("0")){
				pst.setInt(4, Integer.parseInt(status));
				}
			}else if(status!=null && !status.equals("0")){
			pst.setInt(2, Integer.parseInt(status));
			}
			rst = pst.executeQuery();

			while (rst.next()) {
				dto = new DPPurchaseOrderDTO();
				dto.setPor_no(rst.getInt("PR_NO"));
				dto.setDistName(rst.getString("DISNAME"));
				dto.setPrcancelStatus(rst.getString("PR_CANCEL_FLAG"));
				if (rst.getString("PR_DATE") != null) {
					Date dt=rst.getDate("PR_DATE");
					SimpleDateFormat formatter=new SimpleDateFormat("MM/dd/yyyy");
					dto.setPr_dt(formatter.format(dt));
//					dto.setPr_dt(rst.getString("PR_DATE").substring(0, 11));
					}else{
					dto.setPr_dt(rst.getString("PR_DATE"));
				}
				dto.setPo_no(rst.getString("PO_NO"));
				if (rst.getString("PO_DATE") != null) {
					Date dt=rst.getDate("PO_DATE");
					SimpleDateFormat formatter=new SimpleDateFormat("MM/dd/yyyy");
					dto.setPo_dt(formatter.format(dt));
//					 dto.setPo_dt(rst.getString("PO_DATE").substring(0, 11));
					}else{
					dto.setPo_dt(rst.getString("PO_DATE"));
				}
				dto.setStatus(rst.getString("POSTATUS"));
				dto.setInvoice_no(rst.getString("INV_NO"));
				
				if (rst.getString("INV_DATE") != null) {
					Date dt=rst.getDate("INV_DATE");
					SimpleDateFormat formatter=new SimpleDateFormat("MM/dd/yyyy");
					dto.setInvoice_dt(formatter.format(dt));
//					dto.setInvoice_dt(rst.getString("INV_DATE").substring(0, 11));
					}else{
					dto.setInvoice_dt(rst.getString("INV_DATE"));
				}
				dto.setDc_no(rst.getString("DC_NO"));
				if (rst.getString("DC_DT")!= null) {
					Date dt=rst.getDate("DC_DT");
					SimpleDateFormat formatter=new SimpleDateFormat("MM/dd/yyyy");
					dto.setDc_dt(formatter.format(dt));
//					dto.setDc_dt(rst.getString("DC_DT").substring(0, 11));
					}else{
					dto.setDc_dt(rst.getString("DC_DT"));
				}
				dto.setDd_cheque_no(rst.getString("DD_CHEQUE_NO"));
				if (rst.getString("DD_CHEQUE_DATE")!= null) {
					Date dt=rst.getDate("DD_CHEQUE_DATE");
					SimpleDateFormat formatter=new SimpleDateFormat("MM/dd/yyyy");
					dto.setDd_cheque_dt(formatter.format(dt));
//					dto.setDd_cheque_dt(rst.getString("DD_CHEQUE_DATE").substring(0, 11));
					}else{
					dto.setDd_cheque_dt(rst.getString("DD_CHEQUE_DATE"));
				}
					dto.setProductName(rst.getString("PRODUCT"));
					dto.setProductIDNew(rst.getString("PRODUCT_ID"));
					log.info("setProductName == "+rst.getString("PRODUCT")+  " and setProductID === "+rst.getString("PRODUCT_ID"));
					dto.setRaised_quantity(rst.getLong("RAISED_QTY"));
					dto.setInvprod(rst.getString("INV_PROD"));
					dto.setInvqty(rst.getLong("INV_QTY"));
					dto.setQuantity(rst.getString("PO_QTY"));
					dto.setAcceptStatus(rst.getString("ACCEPT_FLAG"));
					dto.setUnit(rst.getString("PRODUCT_UNIT"));
					dto.setRemarks(rst.getString("REMARKS"));
					dto.setCircleId(circleID);
					POList.add(dto);
			} 
			
			// while
//			sql=DBQueries.GET_PR_COUNT;
//			pst = connection.prepareStatement(sql);
//			pst.setLong(1, accountID);
//			rst=pst.executeQuery();
//			while(rst.next()){
//				dto = new DPPurchaseOrderDTO();
//				dto.setPrNo(rst.getString("PR_NO"));
//				dto.setCountPrNo(rst.getString("COUNT_PR"));
//				countList.add(dto);
//			}
//			POList.add(countList);
	
			
			ResultSet rsetID = null;
			
			ResourceBundle rb = ResourceBundle.getBundle("com.ibm.dp.resources.DPResources");
			String configId =rb.getString("android.configId");
			String productIds = null;

		  //  String QueryForGetProductId = "select DD_VALUE from DP_CONFIGURATION_DETAILS where CONFIG_ID = ? with ur";

		    String QueryForAndProdId = "SELECT PRODUCT_ID, PRODUCT_NAME FROM DP_PRODUCT_MASTER  WHERE circle_id=?"
				+" and CM_STATUS ='"+Constants.ACTIVE_STATUS+"' and PRODUCT_TYPE = 1 and PRODUCT_CATEGORY in (select VALUE from "
				+" DP_CONFIGURATION_DETAILS where ID in ("+configId+")) with ur ";
		    
		    
			ArrayList<DPDeliveryChallanAcceptDTO> arrReturn = new ArrayList<DPDeliveryChallanAcceptDTO>();
				pstmtID = connection.prepareStatement(QueryForAndProdId);
				pstmtID.setInt(1,circleID);
				
				rsetID = pstmtID.executeQuery();
				while (rsetID.next())
				{
					if(productIds == null)
					{
						productIds = rsetID.getString("PRODUCT_ID");
					}
					else
					{
						productIds = productIds+","+rsetID.getString("PRODUCT_ID");
					}
				}
				
			
			ArrayList<DPPurchaseOrderDTO> POList2 = new ArrayList<DPPurchaseOrderDTO>();
			Map POListMap = new LinkedHashMap<String, DPPurchaseOrderDTO>();
			Iterator itr = POList.iterator();
		    while(itr.hasNext())
            {
            	
            	dto = (DPPurchaseOrderDTO) itr.next();
            	
            	if(productIds != null)
            	{
            		if(productIds.contains(dto.getProductIDNew()))
            		{
           						dto.setProductName("Android Set");
            	      	  		dto.setInvprod("Android Set");
            	      	  		POListMap.put(dto.getPor_no()+"/"+dto.getPr_dt()+"/"+dto.getPo_no()+"/"+dto.getInvoice_no()+"/"+dto.getQuantity(), dto);
            			
            		}

                	else
                		{
                			POListMap.put(dto.getPor_no()+"/"+dto.getPr_dt()+"/"+dto.getPo_no()+"/"+dto.getInvoice_no()+"/"+dto.getProductName(), dto);
                		
                		}
            	}
            	else
            		{
            			POListMap.put(dto.getPor_no()+"/"+dto.getPr_dt()+"/"+dto.getPo_no()+"/"+dto.getInvoice_no()+"/"+dto.getProductName(), dto);
            		
            		}
            	
            }
            
            
            Set set = POListMap.entrySet();
            Iterator itrMap = set.iterator();
            while(itrMap.hasNext()) {
               Map.Entry me = (Map.Entry)itrMap.next();
                 dto = (DPPurchaseOrderDTO) me.getValue();
             	 POList2.add(dto);
            }
			
			
		
			return POList2;
			 			
		} catch (SQLException e) {
		    e.printStackTrace();
			log.error("SQL Exception occured while inserting.Into PURCHASE ORDER REQUEST Tables"
							+ "Exception Message: ", e);
			throw new DAOException(e.getMessage());
			
		} catch (Exception ex) {
			ex.printStackTrace();
			log
			.error(" **: ERROR INSIDE EXIT insertPurchaseOrder(DPPurchaseOrderFormBean dppfb) OF -> DPPurchaseOrderDaoImpl Class :** ");
			throw new DAOException(ex.getMessage());
		} finally {

			{
				/* Close the statement,resultset */
				DBConnectionManager.releaseResources(pst,rst);
			}
		}
	//	return POList;

	}
/**
 * Function to know the total count for Purchase Order Requisition
 */
	
	public int viewPORCount(int circleID) throws DAOException, SQLException {
	
	PreparedStatement  ps = null;
	Statement stmt = null;
	ResultSet rs = null;
	int count = 0;
	int noofpages = 0;
	try {
		//ps = connection.prepareStatement(SQL_COUNT);
		stmt = connection.createStatement();
	 	rs = stmt.executeQuery(SQL_COUNT);
		
		//	ps.setInt(1, CircleID);
			
			while (rs.next())
			{
				count = rs.getInt(1); 
			}
			noofpages = Utility.getPaginationSize(count);
			
		} catch (SQLException e) {
			log.error("SQL Exception occured while fetching viewPORCount()"
							+ "Exception Message: ", e);
			throw new DAOException(e.getMessage());
		} catch (Exception ex) {
			log
			.error(" **: ERROR INSIDE EXIT viewPORCount() OF -> DPPurchaseOrderDaoImpl Class :** ");
			throw new DAOException(ex.getMessage());
		} finally 
			{
				/* Close the statement,resultset */
				DBConnectionManager.releaseResources(ps,rs);
				DBConnectionManager.releaseResources(stmt,null);
			}
		return noofpages;
		}
	
	public void acceptStock(String[] selectedInvNos) throws DAOException{
		PreparedStatement  ps = null;
		ResultSet rs=null;
		int distributorId=0;
		ArrayList productList = new ArrayList();
		String query="";
		HashMap prodMap=null ;
		try{
			String invNo="";
			for(int i=0;i<selectedInvNos.length;i++){
				query = DBQueries.GET_DIST_PRODUCT;
				ps = connection.prepareStatement(query);
				ps.setString(1, selectedInvNos[i]);
				rs=ps.executeQuery();
				while(rs.next()){
					prodMap = new HashMap();
					distributorId=rs.getInt("DISTRIBUTOR_ID");
					prodMap.put("product", rs.getInt("PRODUCT_ID"));
					prodMap.put("count", rs.getInt("COUNT"));
					productList.add(prodMap);
				}
			}
			for(int i=0;i<selectedInvNos.length;i++){
				invNo = invNo+"'"+selectedInvNos[i]+"'";
				if(i!=selectedInvNos.length-1)
					invNo=invNo+",";
			}
			query = DBQueries.ACCEPT_DIST_STOCK+invNo+")";
			query = query+" with ur ";
			ps = connection.prepareStatement(query);
			ps.executeUpdate();
			for(int j=0;j<productList.size();j++){
				insertRowStockSumm(distributorId,(Integer)((HashMap)productList.get(j)).get("product"));
				query = DBQueries.updateDistStockSummary;
				ps = connection.prepareStatement(query);
				ps.setInt(1, (Integer)((HashMap)productList.get(j)).get("count"));
				ps.setInt(2, (Integer)((HashMap)productList.get(j)).get("count"));
				ps.setInt(3, distributorId);
				ps.setInt(4, (Integer)((HashMap)productList.get(j)).get("product"));
				ps.executeUpdate();
			}
			connection.commit();
		}catch (SQLException e) {
			try {
				connection.rollback();
			} catch (SQLException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			e.printStackTrace();
			log.error("SQL Exception occured while fetching viewPORCount()"
					+ "Exception Message: ", e);
			throw new DAOException(e.getMessage());
		} catch (Exception ex) {
			try {
				connection.rollback();
			} catch (SQLException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			ex.printStackTrace();
			log
			.error(" **: ERROR INSIDE EXIT viewPORCount() OF -> DPPurchaseOrderDaoImpl Class :** ");
			throw new DAOException(ex.getMessage());
		} finally 
			{
				/* Close the statement,resultset */
				DBConnectionManager.releaseResources(ps,null);
	}
	}
	
	public ArrayList getPrCount(long accountID) throws DAOException{
	ResultSet rst = null;
	DPPurchaseOrderDTO dto;
	PreparedStatement pst = null;
	ArrayList countList=new ArrayList();
	String sql=DBQueries.GET_PR_COUNT;
	try{
		pst = connection.prepareStatement(sql);
		pst.setLong(1, accountID);
		rst=pst.executeQuery();
		while(rst.next()){
			dto = new DPPurchaseOrderDTO();
			dto.setPrNo(rst.getString("PR_NO"));
			dto.setCountPrNo(rst.getString("COUNT_PR"));
			countList.add(dto);
		}
	}catch(SQLException e){
		e.printStackTrace();
	}catch(Exception ex){
		ex.printStackTrace();
	}
	finally{
		DBConnectionManager.releaseResources(pst,rst);
	}
	return countList;
}

	public String getProductUnit(String productId) throws DAOException, SQLException {
		ResultSet rst = null;
		DPPurchaseOrderDTO dto;
		PreparedStatement pst = null;
		String productUnit = "";
		String sql=DBQueries.GET_PRODUCT_UNIT;
		try{
			pst = connection.prepareStatement(sql);
			pst.setString(1, productId);
			rst=pst.executeQuery();
			while(rst.next()){
				dto = new DPPurchaseOrderDTO();
				dto.setUnit(rst.getString("UNIT_NAME"));
				productUnit = dto.getUnit();
			}
		}catch(SQLException e){
			e.printStackTrace();
		}catch(Exception ex){
			ex.printStackTrace();
		}
		finally{
			DBConnectionManager.releaseResources(pst,rst);
		}
		return productUnit;
}
	
	public int insertRowStockSumm(int accountfrom, int productId)
	throws DAOException {
	PreparedStatement ps = null;
	ResultSet rs = null;
	StringBuffer sql = new StringBuffer();
	ArrayList arrgetChangedValues = new ArrayList();
	int closingBalance = 0;
	try {
		// Get the Closing Balance for Current Date for Login ID
		sql = sql.append(DBQueries.GetStock);
		ps = connection.prepareStatement(sql.toString() + " with ur ");
		ps.setInt(1, accountfrom);
		ps.setInt(2, productId);
		rs = ps.executeQuery();
		int flag = 0;
		while (rs.next()) {
			// If found get DB Value
			closingBalance = rs.getInt("CLOSINGBALANCE");
			flag++;
		}
		if (flag == 0) { // No Records Found, Get Last Date Closing Balance
			sql = new StringBuffer();
			sql = sql.append(DBQueries.closingBalance);
			ps = connection.prepareStatement(sql.toString() + " with ur ");
			ps.setInt(1, accountfrom);
			ps.setInt(2, productId);
			ps.setInt(3, accountfrom);
			ps.setInt(4, productId);
			rs = ps.executeQuery();
			while (rs.next()) {
				closingBalance = rs.getInt("CLOSINGBALANCE");
			}
			// if Closing Balance Found, Then Insert Last Closing as todays
			// opening
			if (closingBalance != 0) {
				sql = new StringBuffer();
				sql.append(DBQueries.insertClosingBal);
				ps = connection.prepareStatement(sql.toString());
				ps.setInt(1, accountfrom);
				ps.setInt(2, productId);
				ps.setInt(3, closingBalance);
				ps.setInt(4, closingBalance);
				ps.executeUpdate();
			}else{
				sql = new StringBuffer();
				sql.append(DBQueries.insertClosingBal);
				ps = connection.prepareStatement(sql.toString());
				ps.setInt(1, accountfrom);
				ps.setInt(2, productId);
				ps.setInt(3, 0);
				ps.setInt(4, 0);
				ps.executeUpdate();
				
			}
			connection.commit();
		}
	
	} catch (SQLException e) {
		try {
			connection.rollback();
		} catch (Exception ex) {
		}
		e.printStackTrace();
	} catch (Exception e) {
		try {
			connection.rollback();
		} catch (Exception ex) {
		}
		e.printStackTrace();
		log.error("Exception occured while inserting."
				+ "Exception Message: " + e.getMessage());
		throw new DAOException("Exception: " + e.getMessage());
	} finally {
		DBConnectionManager.releaseResources(ps,rs);
	}
	return closingBalance;
	}
	
	//Added by Mohammad Aslam
	public String getDPQuantity(String prodID, Long loginId)throws DAOException, SQLException {
		ResultSet rst = null;
		PreparedStatement pst = null;
		String quantityDP = "";
		String sql = DBQueries.GET_DP_QUANTITY;
		try {
			pst = connection.prepareStatement(sql);
			pst.setString(1, prodID);
			pst.setLong(2, loginId);
			rst = pst.executeQuery();
			if (rst.next()) {
				quantityDP = rst.getString(1);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (Exception ex) {
			ex.printStackTrace();
		} finally {
			DBConnectionManager.releaseResources(pst, rst);
		}
		return quantityDP;
	}
	
	// Get total open stock 
	public String getDPTotalOpenStock(Long loginId,String productId) throws DAOException, SQLException 
	{
		ResultSet rst = null;
		PreparedStatement pst = null;
		String openStockQty = "0";
		String sql = DBQueries.GET_TOTAL_OPEN_STOCK_DIST;
		try 
		{
			pst = connection.prepareStatement(sql);
			pst.setLong(1, loginId);
			pst.setString(2, productId);
			
			rst = pst.executeQuery();
			if (rst.next()) 
			{
				openStockQty = rst.getString("TOTAL_STOCK");
				if(openStockQty == null)
				{
					openStockQty = "0";	
					log.info("No records available for RCV products.");	
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (Exception ex) {
			ex.printStackTrace();
		} finally {
			DBConnectionManager.releaseResources(pst, rst);
		}
		return openStockQty;
	}
	

	//Added By Harpreet for getting PO STATUS
public ArrayList getPOStatus() throws DAOException {
		
		
		PreparedStatement pst = null;
		ResultSet rs = null;
		ArrayList posList = null;
		DPPurchaseOrderDTO  dto ;

				try {
			posList=new ArrayList();

			pst = connection.prepareStatement(SQL_SELECT_PO_STATUS);
			rs = pst.executeQuery();
			//posList = fetchSingleResult(rs);
				
			while(rs.next()) 
			{
				dto = new DPPurchaseOrderDTO ();	
			
				dto.setStatusId(rs.getString(2));
			
				dto.setStatusValue(rs.getString(1));
				posList.add(dto);
			}
			
		
		} catch (SQLException e) {
		
			log.error("SQL Exception occured while selecting. Function : getPOStatus()"
					+ "Exception Message:  " + e.getMessage());
			throw new DAOException(e.getMessage());

		} catch (Exception e) {
			log.error("Exception occured while selecting."
					+ "Exception Message: Function : getPOStatus()" + e.getMessage());
			throw new DAOException(e.getMessage());

		} finally {
			
			/* Close the statement,resultset */
			DBConnectionManager.releaseResources(pst, rs);
		}
		return posList;
	}


	public int getSecurityLoan(String distId) throws DAOException 
	{
		ResultSet rst = null;
		PreparedStatement pst = null;
		int securityAndLoan = 0;
		int securityAmt = 0;
		int loanAmt = 0;
		String sql = DBQueries.GET_SECURITY_AND_LOAN_AMOUNT;
		Long distIdLong = Long.parseLong(distId);
		try 
		{
			pst = connection.prepareStatement(sql);
			pst.setLong(1, distIdLong);
				
			rst = pst.executeQuery();
			if (rst.next()) 
			{
				securityAmt = rst.getInt("SECURITY_AMOUNT");
				loanAmt = rst.getInt("LOAN_AMOUNT");
				securityAndLoan = securityAmt + loanAmt;
			}
		} catch (Exception ex) {
			ex.printStackTrace();
		} finally {
			DBConnectionManager.releaseResources(pst, rst);
		}
		return securityAndLoan;
	}
	

	public String getProductCost(String productId) throws DAOException 
	{
		ResultSet rst = null;
		
		PreparedStatement pst = null;
	
		String productCategory = "0";
		String productCost = "";
		
		//String sql = DBQueries.GET_PRODUCT_CATEGORY;
		String sql = DBQueries.GET_PRODUCT_COST;
		int productIdInt = Integer.parseInt(productId);
		try 
		{
			pst = connection.prepareStatement(sql);
			pst.setInt(1, productIdInt);
			pst.setInt(2, productIdInt);
			pst.setInt(3, productIdInt);
			pst.setInt(4, productIdInt);
			pst.setInt(5, productIdInt);
				
			rst = pst.executeQuery();
			while(rst.next()) 
			{
				productCost = rst.getString("cost");	
				
			}
		} catch (Exception ex) {
			ex.printStackTrace();
		} finally {
			DBConnectionManager.releaseResources(pst, rst);
		}
		return productCost;
	}
	
	public String getTotalStock(String distId,String productId) throws DAOException 
	{
		ResultSet rst = null;
		PreparedStatement pst = null;
		
		String totalCost = "0";
				
		String sql = DBQueries.GET_PRODUCT_COST;
		int productIdInt = Integer.parseInt(productId);
		int distIdInt = Integer.parseInt(distId);
		try 
		{
			pst = connection.prepareStatement(sql);
			pst.setInt(1, distIdInt);
			pst.setInt(2, distIdInt);
			pst.setInt(3, distIdInt);
			pst.setInt(4, distIdInt);
			pst.setInt(5, distIdInt);
				
			rst = pst.executeQuery();
			while(rst.next()) 
			{
				totalCost = rst.getString("STOCK_SECURITY");
			}
		} catch (Exception ex) {
			ex.printStackTrace();
		} finally {
			DBConnectionManager.releaseResources(pst, rst);
		}
		return totalCost;
	}
	
	public String checkTransactionType(Long distId) throws DAOException 
	{
		ResultSet rst = null;
		PreparedStatement pst = null;
		
		String transactionType = "";
				
		String sql = DBQueries.GET_TRANSACTION_TYPE;
		try 
		{
			pst = connection.prepareStatement(sql);
			pst.setLong(1, distId);
			pst.setLong(2, distId);
			rst = pst.executeQuery();
			while(rst.next()){
			    if(!transactionType.equals("")) 
			        transactionType += ",";
			    transactionType += rst.getString("TRANSACTION_TYPE_ID");
			}
			log.info("transactionType == "+transactionType);
			
			//transactionType = "2";
		} catch (Exception ex) {
			ex.printStackTrace();
		} finally {
			DBConnectionManager.releaseResources(pst, rst);
		}
		return transactionType;
	}
	public int checkFNFDone(Long distId) throws DAOException 
	{
		ResultSet rst = null;
		PreparedStatement pst = null;
		int trans=0;
			int count=0;	
		String sql = DBQueries.SQL_DIST_FNF_DONE;
		try 
		{
			pst = connection.prepareStatement(sql);
			pst.setLong(1, distId);
				
			rst = pst.executeQuery();
			
			rst=pst.executeQuery();
			while(rst.next())
			{
				trans=rst.getInt(1);
				count++;	
				
			}
			
			
			log.info("count == "+count);
			
			//transactionType = "2";
		} catch (Exception ex) {
			ex.printStackTrace();
		} finally {
			DBConnectionManager.releaseResources(pst, rst);
		}
		return count;
	}
	public int checkTransactionTypeReverse(Long distId) throws DAOException 
	{
		ResultSet rst = null;
		PreparedStatement pst = null;
		int count=0;
		
				
		String sql = DBQueries.GET_TRANSACTION_TYPE_REVERSE;
		try 
		{
			pst = connection.prepareStatement(sql);
			pst.setLong(1, distId);
		
			rst = pst.executeQuery();
			while(rst.next()){
			    
			    count = rst.getInt(1);
			}
			//log.info("GET_TRANSACTION_TYPE_REVERSE == "+count);
			
			//transactionType = "2";
		} catch (Exception ex) {
			ex.printStackTrace();
		} finally {
			DBConnectionManager.releaseResources(pst, rst);
		}
		return count;
	}
	//Added by Neetika for BFR 2 of Upload eligibility on 25-07-2014
	public String getEligibilityFlag() throws DAOException 
	{
		ResultSet rst = null;
		PreparedStatement pst = null;
		String ELIGIBILITY="N";
		String sql = DBQueries.GET_FLAG_ELIGIBILITY;
		
		try 
		{
			pst = connection.prepareStatement(sql);
			
			rst = pst.executeQuery();
			if (rst.next()) 
			{
				ELIGIBILITY=rst.getString("DD_VALUE");
			}
		} catch (Exception ex) {
			ex.printStackTrace();
		} finally {
			DBConnectionManager.releaseResources(pst, rst);
		}
		return ELIGIBILITY;
	}
	//Added by Neetika for BFR 2 of Upload eligibility on 25-07-2014
	public String getProdCategory(int prod) throws DAOException 
	{
		ResultSet rst = null;
		PreparedStatement pst = null;
		String cat="";
		String sql = "select CATEGORY_CODE from DP_PRODUCT_MASTER where PRODUCT_ID=? with ur"; //1 CPE
		
		try 
		{
			pst = connection.prepareStatement(sql);
			pst.setInt(1, prod);
			rst = pst.executeQuery();
			if (rst.next()) 
			{
				cat=rst.getString("CATEGORY_CODE");
			}
		} catch (Exception ex) {
			ex.printStackTrace();
		} finally {
			DBConnectionManager.releaseResources(pst, rst);
		}
		return cat;
	}

	@Override
	public ArrayList getProductTypeListDao() throws DAOException {
		// TODO Auto-generated method stub
		ArrayList typeList = new ArrayList();
		LabelValueBean lvb = null;
		LabelValueBean lvb1 = null;
		LabelValueBean lvb2 = null;

		  lvb = new LabelValueBean("Android","1");
		  lvb1 = new LabelValueBean("Non Android","2");
		    
		    typeList.add(lvb);
			typeList.add(lvb1);
			
			System.out.println("Type List= "+typeList);		
			
		return typeList;
	}
	
}