package com.ibm.virtualization.recharge.dao.rdbms;


import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import org.apache.struts.util.LabelValueBean;

import com.ibm.dp.common.DBQueries;
import com.ibm.dp.dto.CardMstrDto;
import com.ibm.dp.dto.DPProductDto;
import com.ibm.dp.dto.DpProductCategoryDto;
import com.ibm.dp.dto.DpProductTypeMasterDto;
import com.ibm.dp.exception.DPServiceException;
import com.ibm.virtualization.recharge.beans.DPCreateProductFormBean;
import com.ibm.virtualization.recharge.beans.DPEditWarrantyFormBean;
import com.ibm.virtualization.recharge.beans.DPViewProductFormBean;
import com.ibm.virtualization.recharge.beans.DPViewWarrantyFormBean;
import com.ibm.virtualization.recharge.common.Constants;
import com.ibm.virtualization.recharge.common.ExceptionCode;
import com.ibm.virtualization.recharge.common.Utility;
import com.ibm.virtualization.recharge.dao.DAOFactory;
import com.ibm.virtualization.recharge.dao.DPMasterDAO;
import com.ibm.virtualization.recharge.db.DBConnectionManager;
import com.ibm.virtualization.recharge.dto.DPEditProductDTO;
import com.ibm.virtualization.recharge.dto.DPEditWarrantyDTO;
import com.ibm.virtualization.recharge.dto.DPViewProductDTO;
import com.ibm.virtualization.recharge.dto.DPViewWarrantyDTO;
import com.ibm.virtualization.recharge.exception.DAOException;


/**
 * @author vivek kumar
 *
 * To change the template for this generated type comment go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */


public class DPMasterDAOImplDB2 extends BaseDaoRdbms implements DPMasterDAO
       {
	
	protected static Logger logger = Logger.getLogger(DPMasterDAOImplDB2.class.getName());

	protected static final String SQL_BUSINESSCATEGORY_KEY = "SQL_BUSINESSCATEGORY";
	protected static final String SQL_BUSINESSCATEGORY = DBQueries.SQL_BUSINESS_CATEGORY;
	protected static final String SQL_UPDATE_WARRANTY_NEW_KEY = "SQL_WARRANTY_UPDATE";
	protected static final String SQL_WARRANTY_UPDATE = DBQueries.SQL_WARRANTY_UPDATE;
	protected static final String SQL_SELECT_WARRANTY_KEY = "SQL_WARRANTY_NEW";
	protected static final String SQL_WARRANTY_NEW = DBQueries.SQL_WARRANTY_NEW;
	
	protected static final String SQL_CIRCLELIST_KEY = "SQL_CIRCLELIST";
	protected static final String SQL_CIRCLELIST = DBQueries.SQL_CIRCLE_LIST;	
	
	protected static final String SQL_INSERT_PRODUCT_HANDSET_KEY = "SQL_INSERT_PRODUCT_HANDSET";
	protected static final String SQL_INSERT_PRODUCT_HANDSET  = DBQueries.SQL_INSERT_HNDST;
	
	protected static final String SQL_INSERT_SUK_KEY = "SQL_INSERT_SUK";
	protected static final String SQL_INSERT_SUK = DBQueries.SQL_INSERT_SUK;	
	
//	 Change by harbans on Reservation Obserbation 30th June
	protected static final String SQL_GET_SEQ_CREATE_PRODUCT_KEY = "SQL_GET_SEQ_CREATE_PRODUCT";
	protected static final String SQL_GET_SEQ_CREATE_PRODUCT = DBQueries.SQL_GET_SEQ_CREATE_PRODUCT;	
	
	
	protected static final String SQL_INSERT_RC_KEY = "SQL_INSERT_RC";
	protected static final String SQL_INSERT_RC = DBQueries.SQL_INSERT_RC;	 
	
	protected static final String SQL_SELECT_HANDSET_KEY = "SQL_SELECT_PRODUCT_HANDSET";
	protected static final String SQL_SELECT_PRODUCT_HANDSET = DBQueries.SQL_SELECT_HNDST;
		
	protected static final String SQL_SELECT_SUK_KEY = "SQL_SELECT_PRODUCT_SUK";
	protected static final String SQL_SELECT_PRODUCT_SUK = DBQueries.SQL_SELECT_SUK;	
		
	protected static final String SQL_SELECT_RC_KEY = "SQL_SELECT_PRODUCT_RC";
	protected static final String SQL_SELECT_PRODUCT_RC = DBQueries.SQL_SELECT_RC;
 	
	protected static final String SQL_UPDATE_PRODUCT_HANDSET_KEY = "SQL_UPDATE_PRODUCT_HANDSET";
	protected static final String SQL_UPDATE_PRODUCT_HANDSET = DBQueries.SQL_UPDATE_HNDST;	
	
	protected static final String SQL_UPDATE_PRODUCT_SUK_KEY = "SQL_UPDATE_PRODUCT_SUK";
	protected static final String SQL_UPDATE_PRODUCT_SUK = DBQueries.SQL_UPDATE_SUK;	
	
	protected static final String SQL_SELECT_EDITDATA_KEY = "SQL_SELECT_DATA_FOR_EDIT";
	protected static final String SQL_SELECT_DATA_FOR_EDIT= DBQueries.SQL_SELECT_DATAFOREDIT;
	
	protected static final String SQL_SELECT_EDITWARRANTY_KEY = "SQL_SELECT_WARRANTY_EDIT";
	protected static final String SQL_SELECT_WARRANTY_EDIT =  DBQueries.SQL_EDIT_WARRANTY;			
		
	protected static final String SQL_UPDATE_PRODUCT_RC_KEY = "SQL_UPDATE_PRODUCT_RC";
	protected static final String SQL_UPDATE_PRODUCT_RC = DBQueries.SQL_UPDATE_RC;	
	
	protected static final String SQL_WARRANTY_KEY = "SQL_WARRANTY";
	protected static final String SQL_WARRANTY = DBQueries.SQL_WARRANTY;	
		
    protected static final String SQL_SELECT_WARRANTY_HANDSET_KEY = "SQL_SELECT_WARRANTY_HND";
    protected static final String SQL_SELECT_WARRANTY_HND = DBQueries.SQL_SELECT_WARRANTY_HND;

    protected static final String SQL_SELECT_WARRANTY_SUK_KEY = "SQL_SELECT_WARRANTY_SUK";
    protected static final String SQL_SELECT_WARRANTY_SUK = DBQueries.SQL_SELECT_WARRANTY_SUK;

    protected static final String SQL_SELECT_WARRANTY_RC_KEY = "SQL_SELECT_WARRANTY_RC";
    protected static final String SQL_SELECT_WARRANTY_RC = DBQueries.SQL_SELECT_WARRANTY_RC;
    
    protected static final String SQL_SELECT_CIRCLEIDS = "SQL_SELECT_CIRCLEID";
    protected static final String SQL_SELECT_CIRCLEID = DBQueries.SQL_CIRCLEIDS;
    
    protected static final String SQL_CIRCLEIDCOUNT = "SQL_CIRCLEID";
	protected static final String SQL_CIRCLEID = DBQueries.SQL_CIRCLEIDCOUNT;
	
    // Select query to fetch old record of product in order to compare with editted values for creating a new version.
	
	protected static final String SQL_SELECT_OLD_RECORD_KEY = "SQL_SELECT_OLD_RECORD";
	protected static final String SQL_SELECT_OLD_RECORD = DBQueries.SQL_SELECT_OLD_RECORD;
    
    protected static final String SQL_SELECT_OLD_HANDSET_KEY  = "SQL_SELECT_OLD_HANDSET";
    protected static final String SQL_SELECT_OLD_HANDSET = DBQueries.SQL_SELECT_OLD_HANDSET;
    
    protected static final String SQL_SELECT_HANDSET_COUNT_KEY = "SQL_SELECT_HANDSET_COUNT";
    //added by vishwas
    protected static final String SQL_SELECT_HANDSET_COUNT_KEY_CARDGROUP="SQL_SELECT_HANDSET_COUNT_CARDGROUP";
    protected static final String SQL_SELECT_HANDSET_COUNT_CARDGROUP = DBQueries.SQL_SELECT_HANDSET_COUNT_CARDGROUP;
    //end by vishwas
    protected static final String SQL_SELECT_HANDSET_COUNT = DBQueries.SQL_SELECT_HANDSET_COUNT;
    
    protected static final String SQL_SELECT_SUK_COUNT_KEY = "SQL_SELECT_SUK_COUNT";
    protected static final String SQL_SELECT_SUK_COUNT = DBQueries.SQL_SELECT_SUK_COUNT;
    
    protected static final String SQL_SELECT_RC_COUNT_KEY = "SQL_SELECT_RC_COUNT";
    protected static final String SQL_SELECT_RC_COUNT = DBQueries.SQL_SELECT_RC_COUNT;
    
    protected static final String SQL_GET_UNIT_LIST_KEY = "SQL_GET_UNIT_LIST";
    protected static final String SQL_GET_UNIT_LIST = DBQueries.SQL_GET_UNIT_LIST;
    
    protected static final String SQL_SELECT_BTPACKTYPE_KEY = "SQL_SELECT_BTPACKTYPE";
    protected static final String SQL_SELECT_BTPACKTYPE = DBQueries.SQL_SELECT_BTPACKTYPE;
    
    protected static final String SQL_SELECT_CARD_GROUP_KEY = "SQL_SELECT_CARD_GROUP";
    protected static final String SQL_SELECT_CARD_GROUP = DBQueries.SQL_SELECT_CARD_GROUP;
    
    protected static final String SQL_SELECT_CARD_GROUP_CIRCLEWISE_KEY = "SQL_SELECT_CARD_GROUP_CIRCLEWISE";
    protected static final String SQL_SELECT_CARD_GROUP_CIRCLEWISE = DBQueries.SQL_SELECT_CARD_GROUP_CIRCLEWISE;
    
//  add by harbans on Reservation Obserbation 30th June
    protected static final String SQL_GET_ALL_COMMERCIAL_PRODUCT_KEY = "GET_ALL_COMMERCIAL_PRODUCT";
    protected static final String SQL_GET_ALL_COMMERCIAL_PRODUCT = DBQueries.SQL_GET_ALL_COMMERCIAL_PRODUCT;
    
    
    protected static final String SQL_SELECT_PRODUCT_LIST = DBQueries.GET_PRODUCT_TYPE_LIST;
    protected static final String SQL_SELECT_PRODUCT_CATEGORY_LIST = DBQueries.GET_PRODUCT_CATEGORY_LIST;
	private SQLException nextException;
	
   
	//added by Karan for Validating Unique Oracle Item Code on 6th June 2012
	protected static final String GET_ORACLE_ITEM = DBQueries.GET_ORACLE_ITEM;
	protected static final String EDIT_ORACLE_ITEM = DBQueries.EDIT_ORACLE_ITEM; 
    
    
	protected static final String SQL_GET_PROD_CAT_CIRCLE = DBQueries.SQL_GET_PROD_CAT_CIRCLE;
	
	public DPMasterDAOImplDB2(Connection con) {
		super(con);
		queryMap.put(SQL_SELECT_WARRANTY_HANDSET_KEY, SQL_SELECT_WARRANTY_HND);
		queryMap.put(SQL_SELECT_WARRANTY_SUK_KEY, SQL_SELECT_WARRANTY_SUK);
		queryMap.put(SQL_SELECT_WARRANTY_RC_KEY, SQL_SELECT_WARRANTY_RC);		
		queryMap.put(SQL_BUSINESSCATEGORY_KEY, SQL_BUSINESSCATEGORY);
		queryMap.put(SQL_INSERT_PRODUCT_HANDSET_KEY, SQL_INSERT_PRODUCT_HANDSET);
		queryMap.put(SQL_INSERT_SUK_KEY, SQL_INSERT_SUK);
		queryMap.put(SQL_INSERT_RC_KEY, SQL_INSERT_RC);
		queryMap.put(SQL_SELECT_HANDSET_KEY, SQL_SELECT_PRODUCT_HANDSET);
		queryMap.put(SQL_SELECT_SUK_KEY, SQL_SELECT_PRODUCT_SUK);
		queryMap.put(SQL_SELECT_RC_KEY, SQL_SELECT_PRODUCT_RC);
		queryMap.put(SQL_CIRCLELIST_KEY, SQL_CIRCLELIST);
		queryMap.put(SQL_UPDATE_PRODUCT_HANDSET_KEY, SQL_UPDATE_PRODUCT_HANDSET);
		queryMap.put(SQL_SELECT_EDITDATA_KEY, SQL_SELECT_DATA_FOR_EDIT);
		queryMap.put(SQL_UPDATE_PRODUCT_SUK_KEY, SQL_UPDATE_PRODUCT_SUK);
		queryMap.put(SQL_UPDATE_PRODUCT_RC_KEY, SQL_UPDATE_PRODUCT_RC);
		queryMap.put(SQL_WARRANTY_KEY, SQL_WARRANTY);
		queryMap.put(SQL_SELECT_EDITWARRANTY_KEY,SQL_SELECT_WARRANTY_EDIT);
		queryMap.put(SQL_UPDATE_WARRANTY_NEW_KEY, SQL_WARRANTY_UPDATE);
		queryMap.put(SQL_CIRCLEIDCOUNT,SQL_CIRCLEID);
		queryMap.put(SQL_SELECT_CIRCLEIDS,SQL_SELECT_CIRCLEID);
		queryMap.put(SQL_SELECT_OLD_RECORD_KEY, SQL_SELECT_OLD_RECORD);
		queryMap.put(SQL_SELECT_OLD_HANDSET_KEY, SQL_SELECT_OLD_HANDSET);
		queryMap.put(SQL_SELECT_HANDSET_COUNT_KEY, SQL_SELECT_HANDSET_COUNT);
		//added by vishwas
		queryMap.put(SQL_SELECT_HANDSET_COUNT_KEY_CARDGROUP, SQL_SELECT_HANDSET_COUNT_CARDGROUP);
		//end by vishwas
		queryMap.put(SQL_SELECT_SUK_COUNT_KEY, SQL_SELECT_SUK_COUNT);
		queryMap.put(SQL_SELECT_RC_COUNT_KEY, SQL_SELECT_RC_COUNT);
		queryMap.put(SQL_GET_UNIT_LIST_KEY, SQL_GET_UNIT_LIST);
		queryMap.put(SQL_SELECT_BTPACKTYPE_KEY, SQL_SELECT_BTPACKTYPE);
		queryMap.put(SQL_SELECT_CARD_GROUP_KEY, SQL_SELECT_CARD_GROUP);
		queryMap.put(SQL_SELECT_CARD_GROUP_CIRCLEWISE_KEY, SQL_SELECT_CARD_GROUP_CIRCLEWISE);
//		 add by harbans on Reservation Obserbation 30th June
		queryMap.put(SQL_GET_ALL_COMMERCIAL_PRODUCT_KEY, SQL_GET_ALL_COMMERCIAL_PRODUCT);
		queryMap.put(SQL_GET_SEQ_CREATE_PRODUCT_KEY,SQL_GET_SEQ_CREATE_PRODUCT);
	}


	public int insert(DPCreateProductFormBean productdpbean) throws DAOException {
 		int[] insertion = new int[20];
 		int inLength=0;
		PreparedStatement pst = null;
		PreparedStatement pst1 = null;
		PreparedStatement pst3 = null;
		//Added by Shilpa
		PreparedStatement pst4 = null;
		PreparedStatement pst5 = null;		
		
		ResultSet rs1 = null;
		ResultSet rs3 = null;
//		Added by Shilpa
		ResultSet rs4 = null;
		ResultSet rs5 = null;
		int createProductSeq = 0; 
		int expType = 0;
		
		try 
		{
			String busCat = productdpbean.getBusinessCategory().trim();
			// Code added by Karan for Validating Unique Oracle Item Code
			connection.setAutoCommit(false);
			int circleprodcat=-1;
			if(productdpbean!=null && "1".equals(busCat))
			{
				pst = connection.prepareStatement(GET_ORACLE_ITEM);
				pst.setInt(1, productdpbean.getCircleid()) ;
				pst.setString(2, productdpbean.getOracleitmecode());
			  	rs1 = pst.executeQuery();
			  	if(rs1.next())
			  	{
			  		expType =4;
			  		throw new Exception("Oracle Item Code already in use for the selected Circle");
			  	}
			  	pst.clearParameters();
			  	rs1=null;
			}
		   
		  	//Code added by Karan Ends Here
			
			logger.info("in insert , Product Category =="+productdpbean.getProductCategory());
			ResultSet rs = null;
			int i =0;
			int[] circleIds=new int[1000];
			if (productdpbean.getCircleid()==0)
			{				
				pst = connection.prepareStatement(queryMap.get(SQL_SELECT_CIRCLEIDS));
				
				rs = pst.executeQuery();
				int iterate = 0;
				while(rs.next()){
					circleIds[iterate]=rs.getInt("CIRCLE_ID");
					iterate++;
				}				
				Constants.PAN_PRODUCTS = iterate;
				
				pst.clearParameters();
			}
			else if(productdpbean.getCircleid()!=0)
			{
				Constants.PAN_PRODUCTS = 1;
			}
			int pscount = 0;
			
			pst = connection.prepareStatement(queryMap.get(SQL_INSERT_SUK_KEY));
			
			pst1 = connection.prepareStatement(DBQueries.SQL_PRODUCT_EXIST);
			
			//Added by SHilpa
			pst4 = connection.prepareStatement(DBQueries.SQL_PRODUCT_BUSINESS_PARENT_TYPE_CHECK);
			pst5 = connection.prepareStatement(DBQueries.SQL_PRODUCT_PARENT_CHECK);
			for(i=0;i<Constants.PAN_PRODUCTS;i++)
			{
				//Added by Shilpa Khanna
				int circleId = 0;
				if (productdpbean.getCircleid()==0){
					circleId = circleIds[i];
				}else if(productdpbean.getCircleid()!=0) 
				{
					circleId = productdpbean.getCircleid();
				}
				
				String parentProduct = productdpbean.getProductCategory();
				String productType = productdpbean.getProductTypeId();
				if(busCat.equals("4")){
					productType = "2";
				}
				logger.info(" hi Circle == "+circleId + "  and businessCategory == "+ busCat + "   and parentProduct === "+parentProduct+ "  and productType == "+productType);
				
				//				 Change by harbans on Reservation Obserbation 30th June
				// Get sequence value
				pst3 = connection.prepareStatement(queryMap.get(SQL_GET_SEQ_CREATE_PRODUCT_KEY));
				rs3 = pst3.executeQuery();
				if(rs3.next())
				{
					createProductSeq = rs3.getInt(1); 				
				}
				rs3 = null;
				pst3 = null;
				
			  	pscount = 0;
			  	pst1.setString(1, productdpbean.getProductname1()) ;
			  	pst1.setInt(2, productdpbean.getCircleid()) ;
			  	rs1 = pst1.executeQuery();
			  	if(rs1.next())
			  	{
			  		expType =1;
			  		throw new Exception("Product already exist for this Circle"+productdpbean.getProductname1());
			  		
			  	}
			  	DBConnectionManager.releaseResources(null,rs1);
			  	logger.info("After Product Already exist ");
			  	
				
			  	if(busCat.equals("1"))
			  	{
				  	pst4.setString(1, busCat) ;
				  	pst4.setInt(2, circleId) ;
				  	pst4.setString(3, parentProduct) ;
				  	pst4.setString(4, productType) ;
				  	
				  	rs4 = pst4.executeQuery();
				  	if(rs4.next())
				  	{
				  		expType =2;
				  		throw new Exception("Product combination already Exist for this product");
				  	}
			  	}
			  	logger.info("After Product Combination ");
			  	if(productdpbean.getParentProductId() != null && (productType.equals(Constants.REVERSE_SWAP_PRODUCT_VALUE))) // SWAP
				{
			  		pst5.setString(1,productdpbean.getParentProductId()) ;
				  	pst5.setString(2, productdpbean.getParentProductId()) ;
				  	
				  	rs5 = pst5.executeQuery();
				  	if(rs5.next())
				  	{
				  		expType =3;
				  		throw new Exception("One product can be parent of only one product");
				  	}
				}
			  	System.out.println("sequence ======================== value is "+createProductSeq);
			  	logger.info("Neetika .. "+busCat+"  circleId   "+circleId+"parentProduct = "+parentProduct+"  productType "+productType+"parent is "+Integer.parseInt(productdpbean.getParentProductId()));
			  	pst.setInt(++pscount, createProductSeq);
			 	pst.setInt(++pscount, Integer.parseInt(busCat));
				pst.setString(++pscount, productdpbean.getProductname1());
				pst.setString(++pscount, productdpbean.getCardgroup());
				pst.setString(++pscount, productdpbean.getPacktype());
				pst.setDouble(++pscount, productdpbean.getSimcardcost());
				pst.setDouble(++pscount, productdpbean.getTalktime());
				pst.setDouble(++pscount, productdpbean.getMrp());
				pst.setString(++pscount, productdpbean.getActivation()+"");						
				pst.setDouble(++pscount, productdpbean.getProcessingfee());
				pst.setDouble(++pscount, productdpbean.getSalestax());
				pst.setDouble(++pscount, productdpbean.getCesstax());
				pst.setDouble(++pscount, productdpbean.getGoldennumbercharge());
				java.util.Date nowDate = new java.util.Date(productdpbean.getEffectivedate());
				java.sql.Date productDate=new java.sql.Date(nowDate.getTime());
				pst.setDate(++pscount,productDate);
				pst.setString(++pscount, productdpbean.getVersion());
				pst.setDouble(++pscount, productdpbean.getServicetax());
				pst.setDouble(++pscount, productdpbean.getTurnovertax());
				pst.setDouble(++pscount, productdpbean.getSh_educess());
				pst.setDouble(++pscount, productdpbean.getDiscount());
				
				// SR_DISCOUNT_PRICE,SR_COST_PRICE,SR_OCTORI_PRICE,CREATED_DATE,CREATED_BY,FREE_SERVICE,CIRCLE_ID,EXT_PRODUCT_ID,VAT, RET_DISCOUNT, 
				// RET_PRICE, UPDATED_BY, UPDATED_DATE, PRODUCT_UNIT, 
				// SURCHARGE, FREIGHT, INSURANCE, TRADE_DISCOUNT, CASH_DISCOUNT,Additional_Tax,Oracle_Item_Code,PRODUCT_TYPE, PARENT_PRODUCT_ID
				pst.setDouble(++pscount, productdpbean.getDistprice());
				pst.setDouble(++pscount, productdpbean.getCostprice());
				pst.setDouble(++pscount, productdpbean.getOctoriprice());	
				pst.setInt(++pscount,Integer.parseInt(productdpbean.getCreatedby()));
				pst.setString(++pscount,productdpbean.getFreeservice());
				
//				 Add by harbans on Reservation Obserbation 30th June
				int circleSeq = 0;
				if (productdpbean.getCircleid()==0){
					pst.setInt(++pscount,circleIds[i]); // set circle here
					circleSeq=returnSequenceId(circleIds[i],connection);		// Changed on 14 jul 09
					pst.setInt(++pscount,circleSeq);//	set EXT_PRODUCT_ID				// Changed on 14 jul 09
				}
				else if(productdpbean.getCircleid()!=0) 
				{
					pst.setInt(++pscount,productdpbean.getCircleid()); // set circle here
					circleSeq=returnSequenceId(productdpbean.getCircleid(), connection);	// Changed on 14 jul 09
					pst.setInt(++pscount,circleSeq);	 //	set EXT_PRODUCT_ID							// Changed on 14 jul 09
				}
				pst.setDouble(++pscount, productdpbean.getVat());
				pst.setDouble(++pscount, productdpbean.getRetDiscount());
				pst.setDouble(++pscount, productdpbean.getRetPrice());
				pst.setInt(++pscount, Integer.parseInt(productdpbean.getCreatedby()));
				pst.setString(++pscount, productdpbean.getUnit1());
				pst.setDouble(++pscount, productdpbean.getSurcharge());
				pst.setDouble(++pscount, productdpbean.getFreight());
				pst.setDouble(++pscount, productdpbean.getInsurance());
				pst.setDouble(++pscount, productdpbean.getTradediscount());
				pst.setDouble(++pscount, productdpbean.getCashdiscount());
				//rajiv jha added start
				pst.setDouble(++pscount, productdpbean.getAdditionalvat());
				if(Integer.parseInt(busCat) == 4){
					pst.setString(++pscount, productdpbean.getItmeCodeAv());
				}else{
					pst.setString(++pscount, productdpbean.getOracleitmecode());
				}
				//rajiv jha added end
				 
//				Update by harbans on Reservation Obserbation 7th July
				pst.setString(++pscount, productType);
				
//				Add by harbans on Reservation Obserbation 30th June
				if(productdpbean.getParentProductId() != null && (productType.equals(Constants.REVERSE_SWAP_PRODUCT_VALUE))) // SWAP
				{
					// changed by neetika on 15oct
				// pst.setInt(++pscount, Integer.parseInt(productdpbean.getParentProductId()));
					//logger.info("productdpbean.getParentProductId())was selected by user"+productdpbean.getParentProductId()+" for circle "+circleIds[i]+" reverse product value of comm "+Constants.REVERSE_COM_PRODUCT_VALUE+" parent is == "+parentProduct);
					
					if (productdpbean.getCircleid()==0){
						
						circleprodcat=getProductCategoryCircleWise(circleIds[i],Constants.REVERSE_COM_PRODUCT_VALUE,parentProduct);
						pst.setInt(++pscount, circleprodcat); 
					}else if(productdpbean.getCircleid()!=0) 
					{
						pst.setInt(++pscount, Integer.parseInt(productdpbean.getParentProductId()));
						
					}
					
					
				}
				else
				{
					// Set external product Id on commercial type product
					pst.setInt(++pscount, createProductSeq);
				}
				
				//Added by Shilpa Khanna for product category insertion
				pst.setString(++pscount, productdpbean.getProductCategory()); 
				
				pst.setString(++pscount, productdpbean.getProductStatus());     //aman
				pst.addBatch();
				
		}//end-of-for
			
			pst.executeBatch();
//			 insertion = pst.executeBatch();
//			 inLength = insertion.length;
			 connection.commit();
		}catch (SQLException e) {
			try{
			connection.rollback();
			}
			catch(Exception ex)
			{
				logger.info("Message of exception .."+ex.getMessage());
			}
			e.printStackTrace();
			logger.info(" batch failure  ================"+e.getNextException());
			//SQLException nextException = e.getNextException();
			//nextException.printStackTrace();
			logger.error("Exception occured while reteriving.Create Product"
					+ "Exception Message: ", e);
			throw new DAOException(ExceptionCode.ERROR_DB_INTERNAL);
		}
		catch(Exception e)
		{
			//e.printStackTrace();
			try{
				connection.rollback();
				}
				catch(Exception ex)
				{
					logger.info("Message of exception .."+ex.getMessage());
				}
			if(expType ==1){
				logger.error("Product already Exists: ", e);
				throw new DAOException(ExceptionCode.ERROR_DB_PRODUCT_EXIST);
			}else if(expType ==2){
				logger.error("Product Combination Fails ", e);
				throw new DAOException(ExceptionCode.ERROR_DB_PRODUCT_COMBINATION_EXIST);
			}else if(expType ==3){
				logger.error("One product can be parent of only one product", e);
				throw new DAOException(ExceptionCode.ERROR_DB_PRODUCT_PARENT_EXIST);
			}else if(expType ==4){
				logger.error("Entered Oracle Item Code Already In Use", e);
				throw new DAOException(ExceptionCode.ERROR_DB_ORACLE_ITEM_EXIST);
			}
			
			
		}
		finally{	
			DBConnectionManager.releaseResources(connection);
			DBConnectionManager.releaseResources(pst,null);
			DBConnectionManager.releaseResources(pst1,rs1);
			DBConnectionManager.releaseResources(pst4,rs4);
			DBConnectionManager.releaseResources(pst5,rs5);
		}
		return inLength;
	}	
	
	public ArrayList getBusinessCategoryDao(String userId) {
		java.sql.Statement stmt = null;
		ResultSet rs = null;
		ArrayList list=null;

		
		
		try {
	
			stmt = connection.createStatement();
			rs = stmt.executeQuery(queryMap.get(SQL_BUSINESSCATEGORY_KEY));
			list =  fetchSingleResult(rs);
			connection.commit();
		     }
		  catch (SQLException e) {
			e.printStackTrace();
			logger.error(
				"SQL Exception occured while inserting."
				+ "Exception Message: "
				+ e.getMessage());
		
		} catch (Exception e) {
			e.printStackTrace();
			logger.error(
				"Exception occured while selecting Business List."
					+ "Exception Message: "
					+ e.getMessage());
			
		} finally {
			try {
				if (rs != null)
					rs.close();
				if (stmt != null)
					stmt= null;
				if (connection != null) {
					connection.close();
				}
			} catch (Exception e) {
				logger.error("**error in closing rs or stmt or connection for selecting Business List");
			}
		}
		return list;
	}
	
	public ArrayList getUnitList() {
		PreparedStatement stmt = null;
		ResultSet rs = null;
		ArrayList list=new ArrayList();
		DPViewProductDTO dto = null;
		try {
			stmt = connection.prepareStatement(queryMap.get(SQL_GET_UNIT_LIST_KEY));
			rs = stmt.executeQuery();
			while(rs.next()){
				dto = new DPViewProductDTO();
				dto.setUnit(rs.getString("UNIT_ID"));
				dto.setUnitName(rs.getString("UNIT_NAME"));
				list.add(dto);
			}
		     }
		  catch (SQLException e) {
			e.printStackTrace();
			logger.error(
				"SQL Exception occured while inserting."
				+ "Exception Message: "
				+ e.getMessage());
		} catch (Exception e) {
			e.printStackTrace();
			logger.error(
				"Exception occured while selecting Business List."
					+ "Exception Message: "
					+ e.getMessage());
		} finally {
			try {
				if (rs != null)
					rs.close();
				if (stmt != null)
					stmt= null;
				if (connection != null) {
					connection.close();
				}
			} catch (Exception e) {
				logger.error("**error in closing rs or stmt or connection for selecting Business List");
			}
		}
		return list;
	}
	
	public ArrayList getCircleListDao(String userId) {
		java.sql.Statement stmt = null;
		ResultSet rs = null;
		StringBuffer sql = new StringBuffer("");
		ArrayList list=null;

		try {
	
			DAOFactory factory= DAOFactory.getDAOFactory(DAOFactory.DB2) ;
			stmt = connection.createStatement();			
			rs = stmt.executeQuery(queryMap.get(SQL_CIRCLELIST_KEY));
			list =  fetchcircleResult(rs);
			connection.commit();
		} catch (SQLException e) {
			e.printStackTrace();
			logger.error(
				"SQL Exception occured while selecting Circle List."
					+ "Exception Message: "
					+ e.getMessage());
		
		} catch (Exception e) {
			e.printStackTrace();
			logger.error(
				"Exception occured while selecting Circle List."
					+ "Exception Message: "
					+ e.getMessage());
			
		} finally {
			try {
				if (rs != null)
					rs.close();
				if (stmt != null)
					stmt= null;
				if (connection != null) {
					connection.close();
				}
			} catch (Exception e) {
				logger.error("**error in closing rs or stmt or connection for Circle List");
			}
		}
		
		return list;
	}

	public ArrayList getTsmID(String userId) {
		java.sql.Statement stmt = null;
		ResultSet rs = null;
		StringBuffer sql = new StringBuffer("");
		ArrayList list=null;

		try {
	
			DAOFactory factory= DAOFactory.getDAOFactory(DAOFactory.DB2) ;
			stmt = connection.createStatement();			
			rs = stmt.executeQuery(DBQueries.GET_REV_LOG_DISABLE_TSM_DIST);
			list =  fetchcircleResult(rs);
			connection.commit();
		} catch (SQLException e) {
			e.printStackTrace();
			logger.error(
				"SQL Exception occured while selecting Circle List."
					+ "Exception Message: "
					+ e.getMessage());
		
		} catch (Exception e) {
			e.printStackTrace();
			logger.error(
				"Exception occured while selecting Circle List."
					+ "Exception Message: "
					+ e.getMessage());
			
		} finally {
			try {
				if (rs != null)
					rs.close();
				if (stmt != null)
					stmt= null;
				if (connection != null) {
					connection.close();
				}
			} catch (Exception e) {
				logger.error("**error in closing rs or stmt or connection for Circle List");
			}
		}
		
		return list;
	}
	protected ArrayList fetchSingleResult(ResultSet rs) throws Exception {

		
		ArrayList arrMasterData = new ArrayList();
		LabelValueBean lvb=null;
		while (rs.next()) {
            lvb=new LabelValueBean(rs.getString("CATEGORY_NAME"),rs.getString("CATEGORY_CODE"));
            arrMasterData.add(lvb);
	
		}  

		return arrMasterData;
	}
	protected ArrayList fetchcircleResult(ResultSet rs) throws Exception
	{
		ArrayList arrCircleData = new ArrayList();
		LabelValueBean cir=null;
		while (rs.next())
		{
			cir=new LabelValueBean(rs.getString("CIRCLE_NAME"),rs.getString("CIRCLE_ID"));
			arrCircleData.add(cir);
		}
		return arrCircleData;
	}

	public ArrayList select(DPViewProductDTO dpvpDTO, int lowerBound, int upperBound) throws DAOException {
		
		PreparedStatement pst = null;
		ResultSet rs = null;
		DPViewProductDTO dto = null;
		ArrayList<DPViewProductDTO> productList = new ArrayList<DPViewProductDTO>();
		Connection con = null;
		StringBuffer sql = null;
		int pscount=0;
		try {
			if ("41".equalsIgnoreCase(dpvpDTO.getBusinessCategory()))
			{
				sql = new StringBuffer();
				sql.append(queryMap.get(SQL_SELECT_HANDSET_KEY));
				if(dpvpDTO.getCircleId() != 0){
					sql.append(" and p.circle_id=? ");
				}
				sql.append(" ) as tr Where RNUM between ? and ? with ur");
				logger.info("if Business Category is ==41 then,The Generated Query  == "+sql  );
				con = DBConnectionManager.getDBConnection();
				pst = con.prepareStatement(sql.toString());
				if(dpvpDTO.getCircleId() != 0){
					pst.setInt(++pscount, dpvpDTO.getCircleId());
				}
				pst.setInt(++pscount, lowerBound);
				pst.setInt(++pscount, upperBound);
				rs = pst.executeQuery();				
				while (rs.next())
			 	 {
					dto = new DPViewProductDTO();
					dto.setProductid(rs.getInt("PRODUCT_ID"));
					dto.setProductname(rs.getString("PRODUCT_NAME"));
					dto.setStocktype(rs.getString("STOCK_TYPE"));
					dto.setWarranty(rs.getInt("PRODUCT_WARRANTY"));
					dto.setCompanyname(rs.getString("COMPANY_DESC"));
					dto.setCirclename(rs.getString("CIRCLE_NAME"));
					dto.setVersion(rs.getString("FREE_SERVICE"));
					dto.setCircleId(rs.getInt("CIRCLE_ID"));
					productList.add(dto);
		 		 }
				return productList;
             }
			else if ("1".equalsIgnoreCase(dpvpDTO.getBusinessCategory()) || "3".equalsIgnoreCase(dpvpDTO.getBusinessCategory()))
			{
				sql = new StringBuffer();
				sql.append(queryMap.get(SQL_SELECT_SUK_KEY));
				if(dpvpDTO.getCircleId() != 0){
					sql.append(" and p.circle_id=? ");
				}
				sql.append(" ) as tr Where RNUM between ? and ? with ur");
				logger.info("if Business Category is ==1 OR 3 then,The Generated Query  == "+sql  );
				con = DBConnectionManager.getDBConnection(2);				
				pst = con.prepareStatement(sql.toString());
				pst.setInt(++pscount, Integer.parseInt(dpvpDTO.getBusinessCategory()));
				if(dpvpDTO.getCircleId() != 0){
					pst.setInt(++pscount, dpvpDTO.getCircleId());
				}
				pst.setInt(++pscount, lowerBound);
				pst.setInt(++pscount, upperBound);
				 rs = pst.executeQuery();
		 		while (rs.next())
		 		 {
	 				dto = new DPViewProductDTO();
					dto.setProductid(rs.getInt("PRODUCT_ID"));
					dto.setProductname(rs.getString("PRODUCT_NAME"));
					dto.setCardgroup(rs.getString("SR_CARD_GROUP"));
					dto.setPacktype(rs.getString("SR_PACK_TYPE"));
					dto.setSimcardcost(rs.getDouble("SR_SIM_CARD_COST"));
					dto.setMrp(rs.getDouble("SR_MRP"));
					dto.setCirclename(rs.getString("CIRCLE_NAME"));
					dto.setVersion(rs.getString("FREE_SERVICE"));
					dto.setCircleId(rs.getInt("CIRCLE_ID"));
					productList.add(dto);
				} 
		 		return productList;
		    }
			else if ("2".equalsIgnoreCase(dpvpDTO.getBusinessCategory()) || "4".equalsIgnoreCase(dpvpDTO.getBusinessCategory()))	
			{
				sql = new StringBuffer();
				sql.append(queryMap.get(SQL_SELECT_RC_KEY));
				if(dpvpDTO.getCircleId() != 0){
					sql.append(" and p.circle_id=? ");
				}
				sql.append(" ) as tr Where RNUM between ? and ?  with ur");
				logger.info("if Business Category is ==2 OR 4 then,The Generated Query  == "+sql  );
				con = DBConnectionManager.getDBConnection(2);
				pst = con.prepareStatement(sql.toString());
				pst.setInt(++pscount, Integer.parseInt(dpvpDTO.getBusinessCategory()));
				if(dpvpDTO.getCircleId() != 0){
					pst.setInt(++pscount, dpvpDTO.getCircleId());
				}
				pst.setInt(++pscount, lowerBound);
				pst.setInt(++pscount, upperBound);
				rs = pst.executeQuery();
	 			while (rs.next())
	 			 {
	 				dto = new DPViewProductDTO();
					dto.setProductid(rs.getInt("PRODUCT_ID"));
					dto.setProductname(rs.getString("PRODUCT_NAME"));
					dto.setCardgroup(rs.getString("SR_CARD_GROUP"));
					dto.setPacktype(rs.getString("SR_PACK_TYPE"));
					dto.setSimcardcost(rs.getDouble("SR_SIM_CARD_COST"));
					dto.setMrp(rs.getDouble("SR_MRP"));
					dto.setCirclename(rs.getString("CIRCLE_NAME"));
					dto.setVersion(rs.getString("FREE_SERVICE"));
					dto.setCircleId(rs.getInt("CIRCLE_ID"));
					productList.add(dto);
				} 
				return productList;
			}
		}
		catch (SQLException e) {
			e.printStackTrace();
			logger.error("Exception occured while reteriving.View Product"
					+ "Exception Message: ", e);
			throw new DAOException(ExceptionCode.ERROR_DB_INTERNAL);
			}finally{	
				try {
					if (rs != null)
						rs.close();
					if (pst != null)
						pst.close();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		return productList;
		}
	
	public void update(DPViewProductFormBean prodDPBean) throws DAOException {
		PreparedStatement pst = null;
		ResultSet rs = null;
		PreparedStatement pst1 = null;
		ResultSet rs1 = null;
		
		PreparedStatement pst2 = null;
		ResultSet rs2 = null;
		
		PreparedStatement pst3 = null;
		ResultSet rs3 = null;
		
		int expType =0;
		try {	
					
			// Code added by Karan for Validating Unique Oracle Item Code
			
			pst = connection.prepareStatement(EDIT_ORACLE_ITEM);
			pst.setInt(1,prodDPBean.getCircleId()) ;
			pst.setString(2, prodDPBean.getOracleitmecode());
			pst.setInt(3,prodDPBean.getProdId());
			logger.info("\n\nprodDPBean.getProductname() "+prodDPBean.getProductname()+"\n\n");
		  	rs1 = pst.executeQuery();
		  	if(rs1.next())
		  	{
		  		expType =4;
		  		throw new Exception("Oracle Item Code already in use for the selected Circle");
		  	}
		  	pst.clearParameters();
		  	rs1=null;
		   
		  	//Code added by Karan Ends Here
				
						int circleId = prodDPBean.getCircleId();
						
						int businessCategory = prodDPBean.getCategorycode();
						String parentProduct = prodDPBean.getProductCategory();
						String productType = prodDPBean.getProductTypeId();
						logger.info(" hi Circle == "+circleId + "  and businessCategory == "+ businessCategory + "   and parentProduct === "+parentProduct+ "  and productType == "+productType+  "  Product Id == "+ prodDPBean.getProdId());
						logger.info("prodDPBean.getParentProductId() == "+prodDPBean.getParentProductId());
						pst2 = connection.prepareStatement(DBQueries.SQL_PRODUCT_BUSINESS_PARENT_TYPE_CHECK_EDIT);
					  	pst3 = connection.prepareStatement(DBQueries.SQL_PRODUCT_PARENT_CHECK_EDIT);
						if(prodDPBean.getParentProductId() != null && (prodDPBean.getProductTypeId().equals(Constants.REVERSE_SWAP_PRODUCT_VALUE))) // SWAP
						{
					  		pst3.setString(1,prodDPBean.getParentProductId()) ;
						  	pst3.setString(2, prodDPBean.getParentProductId()) ;
						  	pst3.setInt(3, prodDPBean.getProdId());
						  	rs3 = pst3.executeQuery();
						  	if(rs3.next())
						  	{
						  		expType =2;
						  		throw new Exception("One product can be parent of only one product");
						  		
						  	}
					  		
						}
						if(businessCategory ==1){
							pst2.setInt(1, businessCategory) ;
						  	pst2.setInt(2, circleId) ;
						  	pst2.setString(3, parentProduct) ;
						  	pst2.setString(4, productType) ;
						  	pst2.setInt(5, prodDPBean.getProdId());
						  	rs2 = pst2.executeQuery();
						  	if(rs2.next())
						  	{
						  		expType =1;
						  		throw new Exception("Product combination already Exist for this product");
						  		
						  	}
						}
					  	
					  	logger.info("After Product Combination ");
					int pscount = 0;
					pst = connection.prepareStatement(queryMap.get(SQL_SELECT_OLD_RECORD_KEY));
					pst.setString(++pscount, Constants.ACTIVE_STATUS);
					pst.setInt(++pscount, prodDPBean.getCategorycode());
					pst.setInt(++pscount, prodDPBean.getProdId());
					pst.setString(++pscount, prodDPBean.getProductname1());	
		            pst.setString(++pscount, prodDPBean.getCardgroup());
					pst.setString(++pscount, prodDPBean.getPacktype());
					pst.setDouble(++pscount, prodDPBean.getProcessingfee());
					pst.setDouble(++pscount, prodDPBean.getGoldennumbercharge());
					pst.setString(++pscount, prodDPBean.getVersion());
					pst.setDouble(++pscount, prodDPBean.getMrp());
					pst.setDouble(++pscount, prodDPBean.getSimcardcost());
					pst.setString(++pscount, prodDPBean.getActivation()+"");
					pst.setDouble(++pscount, prodDPBean.getSalestax());
					pst.setDouble(++pscount, prodDPBean.getServicetax());
					pst.setDouble(++pscount, prodDPBean.getCesstax());
					pst.setDouble(++pscount, prodDPBean.getTurnovertax());
					pst.setDouble(++pscount, prodDPBean.getSh_educess());
					pst.setDouble(++pscount, prodDPBean.getDiscount());
					pst.setDouble(++pscount, prodDPBean.getDistprice());
					pst.setDouble(++pscount, prodDPBean.getCostprice());
					pst.setDouble(++pscount, prodDPBean.getOctoriprice());
					pst.setDouble(++pscount, prodDPBean.getTalktime());
					pst.setString(++pscount, prodDPBean.getFreeservice());
					pst.setDouble(++pscount, prodDPBean.getVat());
					pst.setDouble(++pscount, prodDPBean.getRetDiscount());
					//System.out.println(prodDPBean.getRetPrice()+"======LLLLLLLLLLLL= prodDPBean.getRetPrice()");
					pst.setDouble(++pscount, prodDPBean.getRetPrice());
					pst.setString(++pscount, prodDPBean.getUnit1());
					pst.setDouble(++pscount, prodDPBean.getSurcharge());
					pst.setDouble(++pscount, prodDPBean.getFreight());
					pst.setDouble(++pscount, prodDPBean.getInsurance());
					pst.setDouble(++pscount, prodDPBean.getTradediscount());
					pst.setDouble(++pscount, prodDPBean.getCashdiscount());
					pst.setDouble(++pscount, prodDPBean.getAdditionalvat());
					//Added by Shilpa to check category code is AV then set item code value
					if(prodDPBean.getCategorycode()==4){
						pst.setString(++pscount, prodDPBean.getItmeCodeAv());
					}else{
						pst.setString(++pscount, prodDPBean.getOracleitmecode());
					}
					
					pst.setString(++pscount, prodDPBean.getProductTypeId());
					pst.setString(++pscount, prodDPBean.getParentProductId());
//					Added By Shilpa Khanna to update product category
					pst.setString(++pscount, prodDPBean.getProductCategory());
					pst.setString(++pscount,String.valueOf(prodDPBean.getProductStatus()));
					rs = pst.executeQuery();
					pst.clearParameters();
					if (!(rs.next()))
					{
						pscount = 0;
						
						pst = connection.prepareStatement(queryMap.get(SQL_UPDATE_PRODUCT_SUK_KEY));
						logger.info("query executed for SUK update"+queryMap.get(SQL_UPDATE_PRODUCT_SUK_KEY));
			           	pscount = 0;		           
						pst.setString(++pscount, prodDPBean.getProductname1());
						pst.setString(++pscount, prodDPBean.getCardgroup());
						pst.setString(++pscount, prodDPBean.getPacktype());
						pst.setDouble(++pscount, prodDPBean.getSimcardcost());
						pst.setDouble(++pscount, prodDPBean.getMrp());
						pst.setDouble(++pscount, prodDPBean.getTalktime()); 
						pst.setString(++pscount, prodDPBean.getActivation()+"");						
						pst.setDouble(++pscount, prodDPBean.getProcessingfee());
						pst.setDouble(++pscount, prodDPBean.getCesstax());
						pst.setDouble(++pscount, prodDPBean.getServicetax());
						pst.setDouble(++pscount, prodDPBean.getTurnovertax());
						pst.setDouble(++pscount, prodDPBean.getSh_educess());
						pst.setDouble(++pscount, prodDPBean.getGoldennumbercharge());
						pst.setDouble(++pscount, prodDPBean.getDiscount());
						pst.setDouble(++pscount, prodDPBean.getDistprice());
						pst.setDouble(++pscount, prodDPBean.getCostprice());
						pst.setDouble(++pscount, prodDPBean.getOctoriprice());
						double newversion = Double.parseDouble(prodDPBean.getVersion())+1.0;
						pst.setString(++pscount, newversion+"");
						pst.setDouble(++pscount, prodDPBean.getSalestax());
						//java.util.Date nowDate1 = new java.util.Date(prodDPBean.getEffectivedate());
						//java.sql.Date productDate1=new java.sql.Date(nowDate1.getTime());
						//pst.setDate(++pscount,productDate1 );
						pst.setInt(++pscount, Integer.parseInt(prodDPBean.getUpdatedby()));
						pst.setString(++pscount,prodDPBean.getFreeservice());
						pst.setDouble(++pscount, prodDPBean.getVat());
						pst.setDouble(++pscount, prodDPBean.getRetDiscount());
						//System.out.println(prodDPBean.getRetPrice()+"======= prodDPBean.getRetPrice()");
						pst.setDouble(++pscount, prodDPBean.getRetPrice());
						pst.setString(++pscount, prodDPBean.getUnit1());
						//pst.setInt(++pscount, prodDPBean.getProdId());
						pst.setDouble(++pscount, prodDPBean.getSurcharge());
						pst.setDouble(++pscount, prodDPBean.getFreight());
						pst.setDouble(++pscount, prodDPBean.getInsurance());
						pst.setDouble(++pscount, prodDPBean.getTradediscount());
						pst.setDouble(++pscount, prodDPBean.getCashdiscount());
						//rajiv jha added
						pst.setDouble(++pscount, prodDPBean.getAdditionalvat());
						
//						Added by Shilpa to check category code is AV then set item code value
						if(prodDPBean.getCategorycode()==4){
							pst.setString(++pscount, prodDPBean.getItmeCodeAv());
						}else{
							pst.setString(++pscount, prodDPBean.getOracleitmecode());
						}
						
						//Added By Shilpa Khanna
//						pst.setString(++pscount, prodDPBean.getProductTypeId());
//						Update by harbans on Reservation Obserbation 7th July
						if(prodDPBean.getBusinessCategory().equals(Constants.PRODUCT_CPE))
						{
						  pst.setString(++pscount, prodDPBean.getProductTypeId());
						}else if(businessCategory == 4){
							pst.setString(++pscount, Constants.REVERSE_AV_PRODUCT_VALUE);		
						}
						else
						{
						 pst.setString(++pscount, Constants.REVERSE_COM_PRODUCT_VALUE);					
						}
		
						
												
						//Add by harbans on UAT observation on 4th July
						if(prodDPBean.getProductTypeId().equals(Constants.REVERSE_COM_PRODUCT_VALUE)) // If commercial or AV type Product then set Product Id
						{
						    pst.setInt(++pscount, prodDPBean.getProdId());
						}else if(businessCategory == 4){
							pst.setInt(++pscount, prodDPBean.getProdId());		
						}
						else
						{
							 pst.setInt(++pscount, Integer.parseInt(prodDPBean.getParentProductId()));
						}
//						Added By Shilpa Khanna to update product category
						pst.setString(++pscount, prodDPBean.getProductCategory());
						pst.setString(++pscount,String.valueOf(prodDPBean.getProductStatus()));
						pst.setInt(++pscount, prodDPBean.getProdId());
						
						pst.executeUpdate();
						connection.commit();
					}
	}
	catch (SQLException e) {
		e.printStackTrace();
		try {
			connection.rollback();
		} catch (SQLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		e.printStackTrace();
		logger.error("Exception occured while reteriving.Create Product"
				+ "Exception Message: ", e);
		throw new DAOException(ExceptionCode.ERROR_DB_INTERNAL);
		}
	catch(Exception e)
	{
		System.out.println("***********Inside exception handling in DAO Impl class Exception**********");
		//e.printStackTrace();
		if(expType ==1){
			logger.error("Product Combination Fails ", e);
			throw new DAOException(ExceptionCode.ERROR_DB_PRODUCT_COMBINATION_EXIST);
		}else if(expType ==2){
			logger.error("One product can be parent of only one product", e);
			throw new DAOException(ExceptionCode.ERROR_DB_PRODUCT_PARENT_EXIST);
		}else if(expType ==4){
			logger.error("Entered Oracle Item Code Already In Use", e);
			throw new DAOException(ExceptionCode.ERROR_DB_ORACLE_ITEM_EXIST);
		}
	}
	finally{	
			DBConnectionManager.releaseResources(pst, rs);
			DBConnectionManager.releaseResources(pst1,rs1);
//			DBConnectionManager.releaseResources(connection);
		}
	}	
	
	
	
	public ArrayList select1(DPViewWarrantyDTO dpvpDTO) throws DAOException {

		PreparedStatement pst = null;
		ResultSet rs = null;
		DPViewWarrantyDTO dto = null;
		//ArrayList<HashMap<String, Comparable>> productList = new ArrayList();
		ArrayList<DPViewWarrantyDTO> productList = new ArrayList<DPViewWarrantyDTO>();
		Connection con = null;
		try {
			if ("3".equalsIgnoreCase(dpvpDTO.getBusinessCategory()))
			{
				con = DBConnectionManager.getDBConnection(2);
				pst = con.prepareStatement(queryMap.get(SQL_SELECT_WARRANTY_HANDSET_KEY));
				rs = pst.executeQuery();	
				while (rs.next())
	 			{
					dto = new DPViewWarrantyDTO();
		 			dto.setProductid(rs.getInt("PRODUCT_ID"));
		 			dto.setProductname(rs.getString("PRODUCT_NAME"));
		 			dto.setWarranty(rs.getInt("PRODUCT_WARRANTY"));
		 			dto.setExtwarranty(rs.getInt("EXTENDED_WARRANTY"));
		 			productList.add(dto);							
				} 				 			
			return productList;
			}
				else if ("1".equalsIgnoreCase(dpvpDTO.getBusinessCategory()))
			{
			con = DBConnectionManager.getDBConnection(2);
			pst = con.prepareStatement(queryMap.get(SQL_SELECT_WARRANTY_SUK_KEY));
			rs = pst.executeQuery();
			while (rs.next())
			 {
				dto = new DPViewWarrantyDTO();
				dto.setProductid(rs.getInt("PRODUCT_ID"));
				dto.setProductname(rs.getString("PRODUCT_NAME"));
				dto.setWarranty(rs.getInt("PRODUCT_WARRANTY"));
				dto.setExtwarranty(rs.getInt("EXTENDED_WARRANTY"));
				productList.add(dto);	
			} 
			return productList;
    	}
				else if  ("2".equalsIgnoreCase(dpvpDTO.getBusinessCategory()))
					{
					con = DBConnectionManager.getDBConnection(2);
					pst = con.prepareStatement(queryMap.get(SQL_SELECT_WARRANTY_RC_KEY));
					rs = pst.executeQuery();
			 			while (rs.next())
			 			 {dto = new DPViewWarrantyDTO();
			 				dto.setProductid(rs.getInt("PRODUCT_ID"));
			 				dto.setProductname(rs.getString("PRODUCT_NAME"));
			 				dto.setWarranty(rs.getInt("PRODUCT_WARRANTY"));
			 				dto.setExtwarranty(rs.getInt("EXTENDED_WARRANTY"));
							productList.add(dto);		
						} 
						return productList;
					}
		}
		catch (SQLException e) {
			logger.error("Exception occured while reteriving.View Product"
					+ "Exception Message: ", e);
			throw new DAOException(ExceptionCode.ERROR_DB_INTERNAL);
			}finally{	
				try {
					if (rs != null)
						rs.close();
					if (pst != null)
						pst.close();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		return productList;
		}
	public void updatewarranty(DPViewWarrantyFormBean prodDPBean) throws DAOException {
		PreparedStatement pst = null;
		try {
			pst = connection.prepareStatement(queryMap.get(SQL_WARRANTY_KEY));			
				int pscount = 0;				
				pst.setInt(++pscount,prodDPBean.getExtwarranty());
				pst.setString(++pscount,prodDPBean.getUpdatedby());
				pst.setInt(++pscount,prodDPBean.getProductid());
				pst.executeUpdate();			
				connection.commit();
	    	}	
				catch (SQLException e) {
					logger.error("Exception occured while reteriving update Product Warranty"
							+ "Exception Message: ", e);
					throw new DAOException(ExceptionCode.ERROR_DB_INTERNAL);
					}finally{	
					DBConnectionManager.releaseResources((Connection) pst);
					DBConnectionManager.releaseResources(connection);
					logger.info("updation of product warranty done successfully");
					}
			}
	public DPEditProductDTO getProduct(int productid) throws DAOException{
		logger.info("Started in DAO Impl getProduct Method");
		PreparedStatement ps = null;
		ResultSet rs = null;
		DPEditProductDTO product = null;
		try {
			ps = connection.prepareStatement(queryMap.get(SQL_SELECT_EDITDATA_KEY));
			ps.setInt(1, productid);
			rs = ps.executeQuery();

			if (rs.next()) {
				product = new DPEditProductDTO();
				product.setProductid(rs.getInt("PRODUCT_ID"));
				product.setProductname(rs.getString("PRODUCT_NAME"));
				product.setProductname1(rs.getString("PRODUCT_NAME"));
				product.setStocktype(rs.getString("STOCK_TYPE"));				
				product.setWarranty(rs.getInt("PRODUCT_WARRANTY"));
				product.setCompanyname(rs.getString("COMPANY_DESC"));
				product.setProductdesc(rs.getString("PRODUCT_DESC"));
				product.setCardgroup(rs.getString("SR_CARD_GROUP"));
				product.setPacktype(rs.getString("SR_PACK_TYPE"));
				product.setProcessingfee(rs.getDouble("SR_PROCESSING_FEE"));
				product.setGoldennumbercharge(rs.getDouble("SR_GOLDEN_NUMBER_CHARGE"));
				product.setVersion(rs.getString("SR_VERSION"));
				product.setMrp(rs.getDouble("SR_MRP"));
				product.setSimcardcost(rs.getDouble("SR_SIM_CARD_COST"));
				product.setActivation(rs.getString("SR_ACTIVATION"));
				product.setSalestax(rs.getDouble("SR_SALE_TAX"));
				product.setServicetax(rs.getDouble("SR_SERVICE_TAX"));
				product.setCesstax(rs.getDouble("SR_CESS_TAX"));
				product.setTurnovertax(rs.getDouble("SR_TURNOVER_TAX"));
				product.setSh_educess(rs.getDouble("SR_SH_EDUCESS"));
				product.setDiscount(rs.getDouble("SR_DISCOUNT"));
				product.setDistprice(rs.getDouble("SR_DISCOUNT_PRICE"));
				product.setCostprice(rs.getDouble("SR_COST_PRICE"));
				product.setOctoriprice(rs.getDouble("SR_OCTORI_PRICE"));
				product.setTalktime(rs.getDouble("SR_TALKTIME"));
				//product.setEffectivedate(rs.getString("SR_EFFECTIVE_DATE"));
				Date dt=rs.getDate("SR_EFFECTIVE_DATE");
				SimpleDateFormat formatter=new SimpleDateFormat("MM/dd/yyyy");
				product.setEffectivedate(formatter.format(dt));
				product.setCategorycode(rs.getString("CATEGORY_CODE"));
				product.setCircleId(rs.getInt("CIRCLE_ID"));
				product.setBusinessCategory(rs.getString("CATEGORY_NAME"));
				product.setCirclename(rs.getString("CIRCLE_NAME"));
				product.setRetDiscount(rs.getDouble("RET_DISCOUNT"));
				product.setRetPrice(rs.getDouble("RET_PRICE"));
				product.setFreeservice(rs.getString("FREE_SERVICE"));
				product.setVat(rs.getDouble("VAT"));
				product.setUnit1(rs.getString("PRODUCT_UNIT"));
				product.setUnit(rs.getString("PRODUCT_UNIT"));
				product.setFreight(rs.getDouble("FREIGHT"));
				product.setInsurance(rs.getDouble("INSURANCE"));
				
												
				// Update by harbans
				/*
				product.setTradediscount(rs.getDouble("SURCHARGE"));
				product.setCashdiscount(rs.getDouble("TRADE_DISCOUNT"));
				product.setSurcharge(rs.getDouble("CASH_DISCOUNT"));
				*/
				
				product.setTradediscount(rs.getDouble("TRADE_DISCOUNT"));
				product.setCashdiscount(rs.getDouble("CASH_DISCOUNT"));
				product.setSurcharge(rs.getDouble("SURCHARGE"));
				//rajiv jha
				product.setAdditionalvat(rs.getDouble("ADDITIONAL_TAX"));
				product.setOracleitmecode(rs.getString("ORACLE_ITEM_CODE"));
				
				//rajiv jha end
				product.setProductTypeId(rs.getString("PRODUCT_TYPE"));
				
				product.setProductTypeId(rs.getString("PRODUCT_TYPE"));
				
//				Add by harbans on Reservation Obserbation 30th June
				product.setParentProductId(rs.getString("PARENT_PRODUCT_ID"));
				product.setExtProductId(rs.getInt("EXT_PRODUCT_ID"));
				//Added by Shilpa for Product Category
				
				product.setProductCategory(rs.getString("PRODUCT_CATEGORY"));
				if(rs.getString("CATEGORY_CODE").trim().equals("4")){
					product.setItmeCodeAv(rs.getString("ORACLE_ITEM_CODE"));
				}
				product.setProductStatus(rs.getString("PSTATUS")); //Neetika
			}
		} catch (SQLException e) {
			logger.error("Exception occured while get Product."
					+ "Exception Message: ", e);
			throw new DAOException(ExceptionCode.ERROR_DB_INTERNAL);
		}catch(Exception ex){
			ex.printStackTrace();
		} 
		finally {
			/* Close the statement,resultset */
			DBConnectionManager.releaseResources(ps, null);
		}
		return product;
	}
	public DPEditWarrantyDTO getProductwarranty(int productid) throws DAOException {
		logger.info("Started in DAO Impl getProductwarranty Method"+productid);
		PreparedStatement ps = null;
		ResultSet rs = null;
		DPEditWarrantyDTO product = null;
	
		try {
			ps = connection.prepareStatement(queryMap.get(SQL_SELECT_EDITWARRANTY_KEY));
			ps.setInt(1, productid);
			rs = ps.executeQuery();
			if (rs.next()) {
				product = new DPEditWarrantyDTO();
				//product.setProductid(rs.getInt("PRODUCT_ID"));
				product.setProductid(rs.getInt("PRODUCT_ID"));
				product.setProductname(rs.getString("PRODUCT_NAME"));
				product.setWarranty(rs.getInt("PRODUCT_WARRANTY"));
				product.setCategory(rs.getInt("CATEGORY_CODE"));
				product.setBusinessCategory(rs.getString("CATEGORY_NAME"));
				product.setExtwarranty(rs.getInt("EXTENDED_WARRANTY"));
			}

		} catch (SQLException e) {
			logger.error("Exception occured while get Product warranty."
					+ "Exception Message: ", e);
			throw new DAOException(ExceptionCode.ERROR_DB_INTERNAL);
		} finally {
			/* Close the statement,resultset */
			DBConnectionManager.releaseResources(ps, null);
			logger.info("DB connection is closed now.");
		}
		return product;
	}
	public void updateWarranty(DPEditWarrantyFormBean warrantyBean) throws DAOException {
		PreparedStatement pst = null;
		try {			
			DAOFactory factory= DAOFactory.getDAOFactory(DAOFactory.DB2);
							
			pst = connection.prepareStatement(queryMap.get(SQL_UPDATE_WARRANTY_NEW_KEY));
			 int pscount = 0;
			 pst.setString(++pscount,warrantyBean.getUpdatedby());
			 pst.setInt(++pscount,warrantyBean.getExtwrnty());
			 pst.setString(++pscount,warrantyBean.getImeino());			 					      
			 pst.executeUpdate();							  
			connection.commit();
          }
	catch (SQLException e) {
		logger.error("Exception occured while reteriving.Create Product"
				+ "Exception Message: ", e);
		throw new DAOException(ExceptionCode.ERROR_DB_INTERNAL);
		}finally{	
			DBConnectionManager.releaseResources(pst,null);
		}
	}
	public DPViewWarrantyDTO getWarranty(String imeino,long userId) throws DAOException {			
		PreparedStatement ps = null;
		ResultSet rs = null;
		int intUserId=Long.valueOf(userId).intValue();
		Connection con =  DBConnectionManager.getDBConnection();
		DPViewWarrantyDTO warranty = null;
		try {
			ps = con.prepareStatement(DBQueries.SQL_WARRANTY_NEW);
			ps.setString(1, imeino);
			ps.setInt(2, intUserId);
			rs = ps.executeQuery();
			if (rs.next()) {
				warranty = new DPViewWarrantyDTO();
				warranty.setImeino(rs.getString("IMEI_NO"));
				warranty.setImeiNo(rs.getString("IMEI_NO"));				
				warranty.setDmgstatus(rs.getString("DAMAGE_FLAG"));
				warranty.setStdwrnty(rs.getInt("STANDARD_WARRANTY"));
				warranty.setExtwrnty(rs.getInt("EXTENDED_WARRANTY"));
				warranty.setProductname(rs.getString("PRODUCT_NAME"));
				warranty.setImeiwrhs(rs.getString("LAST_WAREHOUSE_ID"));
			}
		//	ps = con.prepareStatement(DBQueries.SQL_WARRANTY_MRL);
		//	ps.setInt(1, intUserId);
		//	ps.setString(2, imeino);
		//	rs = ps.executeQuery();
		//	if (rs.next()) {
				//warranty = new DPViewWarrantyDTO();
		//		warranty.setMnreorder(rs.getInt("MIN_REORDER_LEVEL_QTY"));
		//	}
		
		} catch (SQLException e) {
			logger.error("Exception occured while get Warranty Details for particular IMEI No.."
					+ "Exception Message: ", e);
			e.printStackTrace();
			throw new DAOException(ExceptionCode.ERROR_DB_INTERNAL);
		} finally {
			/* Close the statement,resultset */
			DBConnectionManager.releaseResources(ps, rs);
		}
		return warranty;
	}
	
	public int getProductListCount(DPViewProductDTO dpvpDTO) throws DAOException{
		int noofPages=0;
		int noofRows=0;
		PreparedStatement pst = null;
		ResultSet rs = null;
		Connection con = null;
		StringBuffer sql = new StringBuffer();
		try {
//			if ("4".equalsIgnoreCase(dpvpDTO.getBusinessCategory()))
//			{
				sql.append(queryMap.get(SQL_SELECT_HANDSET_COUNT_KEY));
				if(dpvpDTO.getCircleId() != 0){
					sql.append(" and p.CIRCLE_ID=? ");
				}
				sql.append(" with ur");
				con = DBConnectionManager.getDBConnection();
				pst = con.prepareStatement(sql.toString());
				pst.setInt(1, Integer.parseInt(dpvpDTO.getBusinessCategory()));
				if(dpvpDTO.getCircleId() != 0){
					pst.setInt(2, dpvpDTO.getCircleId());
				}
				rs = pst.executeQuery();				
				while (rs.next())
			 	 {
					noofRows = rs.getInt(1);
		 		 }
//             }
//			else if ("1".equalsIgnoreCase(dpvpDTO.getBusinessCategory()) || "3".equalsIgnoreCase(dpvpDTO.getBusinessCategory()))
//			{
//				con = DBConnectionManager.getDBConnection(2);				
//				pst = con.prepareStatement(queryMap.get(SQL_SELECT_SUK_COUNT_KEY));
//				pst.setInt(1, Integer.parseInt(dpvpDTO.getBusinessCategory()));
//				 rs = pst.executeQuery();
//		 		while (rs.next())
//		 		 {
//		 			noofRows = rs.getInt(1);
//				} 
//		    }
//			else if("2".equalsIgnoreCase(dpvpDTO.getBusinessCategory()))
//			{
//				con = DBConnectionManager.getDBConnection(2);
//				pst = con.prepareStatement(queryMap.get(SQL_SELECT_RC_COUNT_KEY));
//				rs = pst.executeQuery();
//	 			while (rs.next())
//	 			 {
//	 				noofRows = rs.getInt(1);
//				} 
//			}
			noofPages = Utility.getPaginationSize(noofRows);
		}
		catch (SQLException e) {
			logger.error("Exception occured while reteriving.View Product"
					+ "Exception Message: ", e);
			
			throw new DAOException(ExceptionCode.ERROR_DB_INTERNAL);
			}finally{	
				try {
					if (rs != null)
						rs.close();
					if (pst != null)
						pst.close();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		return noofPages;
	}
	//added by vishwas for pegging in card group
	public int getProductListCount(CardMstrDto dpvpDTO) throws DAOException{
		int noofPages=0;
		int noofRows=0;
		PreparedStatement pst = null;
		ResultSet rs = null;
		Connection con = null;
		StringBuffer sql = new StringBuffer();
		try {
//			if ("4".equalsIgnoreCase(dpvpDTO.getBusinessCategory()))
//			{
				sql.append(queryMap.get(SQL_SELECT_HANDSET_COUNT_KEY_CARDGROUP));
				
				//sql.append(" with ur");
				con = DBConnectionManager.getDBConnection();
				pst = con.prepareStatement(sql.toString());
				//pst.setInt(1, Integer.parseInt(dpvpDTO.getBusinessCategory()));
				
				rs = pst.executeQuery();				
				while (rs.next())
			 	 {
					noofRows = rs.getInt(1);
		 		 }
//             }
//			else if ("1".equalsIgnoreCase(dpvpDTO.getBusinessCategory()) || "3".equalsIgnoreCase(dpvpDTO.getBusinessCategory()))
//			{
//				con = DBConnectionManager.getDBConnection(2);				
//				pst = con.prepareStatement(queryMap.get(SQL_SELECT_SUK_COUNT_KEY));
//				pst.setInt(1, Integer.parseInt(dpvpDTO.getBusinessCategory()));
//				 rs = pst.executeQuery();
//		 		while (rs.next())
//		 		 {
//		 			noofRows = rs.getInt(1);
//				} 
//		    }
//			else if("2".equalsIgnoreCase(dpvpDTO.getBusinessCategory()))
//			{
//				con = DBConnectionManager.getDBConnection(2);
//				pst = con.prepareStatement(queryMap.get(SQL_SELECT_RC_COUNT_KEY));
//				rs = pst.executeQuery();
//	 			while (rs.next())
//	 			 {
//	 				noofRows = rs.getInt(1);
//				} 
//			}
			System.out.println("noofRowssssssssssssssssssssssss"+noofRows);
				noofPages = Utility.getPaginationSize(noofRows);
				System.out.println("noofRowssssssssssssssssssssssss"+noofPages);
		}
		catch (SQLException e) {
			logger.error("Exception occured while reteriving.View Product"
					+ "Exception Message: ", e);
			
			throw new DAOException(ExceptionCode.ERROR_DB_INTERNAL);
			}finally{	
				try {
					if (rs != null)
						rs.close();
					if (pst != null)
						pst.close();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		return noofPages;
	}

	
	// end by vishwas
	
	public String getBTPackType(String busCat)throws DAOException{
		String btPackType= "";
		PreparedStatement pst = null;
		ResultSet rs = null;
		Connection con = null;
		StringBuffer sql = new StringBuffer();
		try {
				sql.append(queryMap.get(SQL_SELECT_BTPACKTYPE_KEY));
				con = DBConnectionManager.getDBConnection();
				pst = con.prepareStatement(sql.toString());
				pst.setInt(1, Integer.parseInt(busCat));
				rs = pst.executeQuery();				
				while (rs.next())
			 	 {
					btPackType = rs.getInt("BT_CATEGORY_CODE")+"";
		 		 }
		}
		catch (SQLException e) {
			logger.error("Exception occured while reteriving.View Product"
					+ "Exception Message: ", e);

			throw new DAOException(ExceptionCode.ERROR_DB_INTERNAL);
			}finally{	
				try {
					if (rs != null)
						rs.close();
					if (pst != null)
						pst.close();
					if(con != null)
						con.close();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		return btPackType;
	}
	
	public int getExistingCardGroup(String card, int circleId)throws DAOException
	{
		int productId= -1;
		PreparedStatement pst = null;
		ResultSet rs = null;
		Connection con = null;
		StringBuffer sql = new StringBuffer();
		try {
				sql.append(queryMap.get(SQL_SELECT_CARD_GROUP_KEY));
				con = DBConnectionManager.getDBConnection();
				pst = con.prepareStatement(sql.toString());
				pst.setString(1, card.toUpperCase());
				pst.setInt(2, circleId);
				pst.setString(3, card.toUpperCase());
				pst.setInt(4, circleId);
				rs = pst.executeQuery();				
				while (rs.next())
			 	 {
					productId = rs.getInt("PRODUCT_ID");
					return productId;
		 		 }
		}
		catch (SQLException e) {
			logger.error("Exception occured while reteriving.View Product"
					+ "Exception Message: ", e);

			throw new DAOException(ExceptionCode.ERROR_DB_INTERNAL);
			}finally{	
				try {
					if (rs != null)
						rs.close();
					if (pst != null)
						pst.close();
					if(con != null)
						con.close();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		return productId;
	}
	
//	 Return of sequence
	public int returnSequenceId(int circleId, Connection con)throws DAOException {
		int sequenceId=0;
		//Connection con = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		StringBuilder sql = new StringBuilder();
		
		try 
		{
			//con = DBConnectionManager.getDBConnection();
			sql=new StringBuilder();
			sql.append(DBQueries.sequenceId);
			sql.append(" SEQ_DP_PRODUCT_"+circleId+ " from sysibm.SYSDUMMY1");
			ps = con.prepareStatement(sql.toString());
			rs=ps.executeQuery();
			while(rs.next()){
				sequenceId=rs.getInt(1);
			}
		}catch (SQLException e) {
			try 
			{
			con.rollback();
			} catch (Exception ex) {
			}
		    e.printStackTrace();
			throw new DAOException(ExceptionCode.ERROR_DB_INTERNAL);
		} catch (Exception e) {
		try {
			con.rollback();
		} catch (Exception ex) {
		}
		e.printStackTrace();
		logger.error("Exception occured while inserting."
				+ "Exception Message: " + e.getMessage());
		throw new DAOException(ExceptionCode.ERROR_DB_INTERNAL);
	}
		return sequenceId;
		
	}
	
	public ArrayList getCardGroups(String circleId){

		PreparedStatement pstmt = null;
		ResultSet rs = null;
		ArrayList arrMasterData = new ArrayList();
		try {
			logger.info(queryMap.get(SQL_SELECT_CARD_GROUP_CIRCLEWISE_KEY)+" circleId"+circleId);
			pstmt = connection.prepareStatement(queryMap.get(SQL_SELECT_CARD_GROUP_CIRCLEWISE_KEY));
			//pstmt.setInt(1,Integer.parseInt(circleId));
			rs = pstmt.executeQuery();
			LabelValueBean lvb=null;
			while (rs.next()) {
	            lvb=new LabelValueBean(rs.getString("CARDGROUPID"),rs.getString("ID"));
	            arrMasterData.add(lvb);
		
			}
			connection.commit();
		     }
		  catch (SQLException e) {
			e.printStackTrace();
			logger.error(
				"SQL Exception occured while inserting."
				+ "Exception Message: "
				+ e.getMessage());
		
		} catch (Exception e) {
			e.printStackTrace();
			logger.error(
				"Exception occured while selecting Business List."
					+ "Exception Message: "
					+ e.getMessage());
			
		} finally {
			try {
				if (rs != null)
					rs.close();
				if (pstmt != null)
					pstmt= null;
				if (connection != null) {
					connection.close();
				}
			} catch (Exception e) {
				logger.error("**error in closing rs or stmt or connection for selecting Business List");
			}
		}
		return arrMasterData;
	
		
	}
	
	
	// Add by harbans on Reservation Observation 30th June
	public ArrayList getAllCommercialProducts(int circleId) throws DAOException 
	{
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		ArrayList<DpProductTypeMasterDto> arrMasterData = new ArrayList<DpProductTypeMasterDto>();
		DpProductTypeMasterDto dto= null;
		try {
					
//			 By pratap on 27-07-2013 for Pan india for circle data was not populated
			if(circleId == 0)
			{
				pstmt = connection.prepareStatement("SELECT PRODUCT_ID, PRODUCT_NAME FROM DP_PRODUCT_MASTER WHERE INT(PRODUCT_TYPE)=(SELECT ID FROM DP_CONFIGURATION_DETAILS WHERE CONFIG_ID=4 AND VALUE='COMMERCIAL') WITH UR");
			}
			else
			{
				pstmt = connection.prepareStatement(queryMap.get(SQL_GET_ALL_COMMERCIAL_PRODUCT_KEY));
				pstmt.setInt(1,circleId);
			}
			// end by pratap
			
			/* commented by pratap
			pstmt = connection.prepareStatement(queryMap.get(SQL_GET_ALL_COMMERCIAL_PRODUCT_KEY));
			pstmt.setInt(1,circleId);*/
			rs = pstmt.executeQuery();
			
			while (rs.next()) 
			{
				dto = new DpProductTypeMasterDto();
				dto.setProductId(rs.getString("PRODUCT_ID"));
				dto.setProductName(rs.getString("PRODUCT_NAME"));
				arrMasterData.add(dto);
			}
			//connection.commit();
		  }
		  catch (SQLException e) {
			e.printStackTrace();
			logger.error(
				"SQL Exception occured while inserting."
				+ "Exception Message: "
				+ e.getMessage());
		
		} catch (Exception e) {
			e.printStackTrace();
			logger.error(
				"Exception occured while selecting Business List."
					+ "Exception Message: "
					+ e.getMessage());
			
		} finally {
			DBConnectionManager.releaseResources(pstmt, rs);
			DBConnectionManager.releaseResources(connection);
		}
		return arrMasterData;
	}
	
	
	//Added by Shilpa
	public List<DpProductTypeMasterDto> getProductTypeMaster() throws DAOException 
	{
		logger.info("in getProductTypeMaster() Dao IML  ......Started");
		List<DpProductTypeMasterDto> dcProductListDTO	= new ArrayList<DpProductTypeMasterDto>();
		PreparedStatement pstmt = null;
		ResultSet rset			= null;
		DpProductTypeMasterDto  dcProductDto = null;
				
		try
		{
			pstmt = connection.prepareStatement(SQL_SELECT_PRODUCT_LIST);
			rset = pstmt.executeQuery();
			dcProductListDTO = new ArrayList<DpProductTypeMasterDto>();
			
			while(rset.next())
			{
				dcProductDto = new DpProductTypeMasterDto();
				dcProductDto.setProductId(rset.getString("ID"));
				dcProductDto.setProductType(rset.getString("VALUE"));
				
								
				dcProductListDTO.add(dcProductDto);
			}
			logger.info("in getProductTypeMaster() Dao IML  ......Ended Success Fully");
			
		}
		catch (Exception e) 
		{
			e.printStackTrace();
			throw new DAOException(e.getMessage());
		}
		finally
		{
			pstmt = null;
			rset = null;
		}
		return dcProductListDTO;
	}
	
//	Added by Shilpa for product category list
	public List<DpProductCategoryDto> getProductCategoryLst(String businessCategory) throws DAOException 
	{
		logger.info("in getProductCategoryLst() Dao IML  ......Started");
		List<DpProductCategoryDto> dcProductCategListDTO	= new ArrayList<DpProductCategoryDto>();
		PreparedStatement pstmt = null;
		ResultSet rset			= null;
		DpProductCategoryDto  dcProducCatetDto = null;
		Integer configId = 0 ;		
		try
		{
			if(Integer.parseInt(businessCategory.trim())==1){
				configId = 8;
			}else if(Integer.parseInt(businessCategory.trim())==4){
				configId = 22;
			}
			if(configId !=0){
				pstmt = connection.prepareStatement(SQL_SELECT_PRODUCT_CATEGORY_LIST);
				pstmt.setInt(1,configId);
				rset = pstmt.executeQuery();
				dcProductCategListDTO = new ArrayList<DpProductCategoryDto>();
				
				while(rset.next())
				{
					dcProducCatetDto = new DpProductCategoryDto();
					if(configId == 8){ // for CPE only changed by neetika
					if(Integer.parseInt((rset.getString("ID")))%10!=0)
					{
					dcProducCatetDto.setProductCategory(rset.getString("PRODUCT_CATEGORY"));
					dcProductCategListDTO.add(dcProducCatetDto);
					}
					}
					else
					{
						dcProducCatetDto.setProductCategory(rset.getString("PRODUCT_CATEGORY"));
						dcProductCategListDTO.add(dcProducCatetDto);
					}
					
				}
			}
			
			logger.info("in getProductCategoryLst() Dao IML  ......Ended Success Fully");
			
		}
		catch (Exception e) 
		{
			e.printStackTrace();
			throw new DAOException(e.getMessage());
		}
		finally
		{
			DBConnectionManager.releaseResources(pstmt, rset);
		}
		return dcProductCategListDTO;
	}


	public String checkProductName(String productCategory,int circleId) throws DAOException {
		
		PreparedStatement pstmt = null;
		ResultSet rset			= null;
		String strReturn = "true";
		String strQuery = "";
		try
		{
			logger.info("**************getchecked function in daoimpl****************"+productCategory+"  circleId "+circleId);
			
			
			if(circleId!=0)
			{
				strQuery = "select PRODUCT_CATEGORY from DP_PRODUCT_MASTER where CIRCLE_ID="+circleId+" and PRODUCT_CATEGORY=? ";
			
			pstmt = connection.prepareStatement(strQuery);
			pstmt.setString(1,productCategory);
			rset = pstmt.executeQuery();
			if(rset.next()) 
			{
				logger.info("**************This product already exists111111111****************"+strReturn);
				strReturn = "false";	
			}
			}
			connection.commit();	
		}
		catch (Exception e) 
		{
			e.printStackTrace();
			logger.error("error in validating parent product  ::  "+e.getMessage());
			logger.error("error in validating parent product  ::  "+e);
			throw new DAOException(e.getMessage());
		}
		finally
		{
			DBConnectionManager.releaseResources(pstmt, rset);
		}
		logger.info("**************final***************"+strReturn);
		return strReturn;
	}
	//Method added by Neetika on 15 Oct
	public int getProductCategoryCircleWise(int circleId,String type,String parentProduct) throws Exception {
		java.sql.PreparedStatement pst = null;
		ResultSet rs = null;
		int circleprodcat=-1;
		
		try {
	
			pst = connection.prepareStatement(SQL_GET_PROD_CAT_CIRCLE);
			pst.setInt(1, circleId);
			pst.setString(2, type);
			pst.setString(3, parentProduct);
			rs = pst.executeQuery();
			
			//logger.info(SQL_GET_PROD_CAT_CIRCLE);
			if(rs.next())
			{
			circleprodcat =  rs.getInt("PRODUCT_ID");
			}
			else
			{
				logger.error(
						"Exception occured while fidning product category..."	);
				//throw new Exception("There is no Commercial Product for this Parent Product and Circle ID "+circleId);
		  	
				
			}
			logger.info("circleprodcat is found to be "+circleprodcat +"for circleId"+circleId+"  parentProduct  "+parentProduct);
			
		    }
		  catch (SQLException e) {
			e.printStackTrace();
			logger.error(
				"SQL Exception occured while selecting."
				+ "Exception Message: "
				+ e.getMessage());
			
			throw new SQLException("There is error in finding Commercial Product for this Parent Product and Circle ID "+circleId);
			
		
		} catch (Exception e) {
			e.printStackTrace();
			logger.error(
				"Exception occured while fidning product category..."
					+ "Exception Message: "
					+ e.getMessage());
			
				throw new Exception("There is error in finding Commercial Product for this Parent Product and Circle ID "+circleId);
				
		} finally {
			try {
				if (rs != null)
					rs.close();
				if (pst != null)
					pst= null;
				
			} catch (Exception e) 
			{
				logger.error("**error in finding product category..");
			}
			if(circleprodcat==-1)
			{
				logger.error(
				"Exception occured while fidning product category..."	);
				throw new Exception("There is no Commercial Product for this Parent Product and Circle ID "+circleId);
			}
		}
		return circleprodcat;
	}
	
	//aman
	public List<DPProductDto> getProductList(String dpProdType) throws DAOException 
	{
		
		logger.info("in getProductCategoryLst() Dao IML  ......Started");
		List<DPProductDto> dpProductDtoList	= new ArrayList<DPProductDto>();
		PreparedStatement pstmt = null;
		ResultSet rset			= null;
		DPProductDto  dpProductDto = null;
		String sqlQuery="SELECT CIRCLE_NAME,PRODUCT_ID,PRODUCT_NAME,CARDGROUPNAME,(CASE  WHEN PRODUCT_TYPE ='0' THEN 'SWAP'  WHEN PRODUCT_TYPE='1' THEN 'COMMERCIAL'  WHEN PRODUCT_TYPE='2' THEN 'AV'  ELSE NULL end) as PRODUCT_TYPE ,SR_MRP,SR_SIM_CARD_COST,SR_TALKTIME,SR_ACTIVATION,SR_SERVICE_TAX,SR_CESS_TAX,SR_SH_EDUCESS,SR_DISCOUNT,SR_DISCOUNT_PRICE,SR_EFFECTIVE_DATE,CREATED_DATE,(CASE  WHEN dpm.PSTATUS='A' THEN 'ACTIVE'  WHEN dpm.PSTATUS='I' THEN 'INACTIVE'  end) as PSTATUS  FROM DP_PRODUCT_MASTER dpm left outer join DP_CARDGROUP_MASTER dcm on dcm.ID=int(dpm.SR_CARD_GROUP), VR_CIRCLE_MASTER cm WHERE dpm.CIRCLE_ID=cm.CIRCLE_ID   and CATEGORY_CODE=? order by CIRCLE_NAME,PRODUCT_NAME with ur";
		//and dpm.SR_CARD_GROUP=dcm.CARDGROUPID changed to lod
		try
		{
			
				pstmt = connection.prepareStatement(sqlQuery);
				pstmt.setInt(1,Integer.parseInt(dpProdType));
				rset = pstmt.executeQuery();
				dpProductDtoList = new ArrayList<DPProductDto>();
				while(rset.next())
				{
					dpProductDto = new DPProductDto();
					dpProductDto.setDpCircleName(rset.getString("CIRCLE_NAME"));
					dpProductDto.setProdCode(rset.getString("PRODUCT_ID"));
					dpProductDto.setProdName(rset.getString("PRODUCT_NAME"));
					dpProductDto.setCardGrpName(rset.getString("CARDGROUPNAME"));
					dpProductDto.setProdType(rset.getString("PRODUCT_TYPE"));
					dpProductDto.setDpMrp(rset.getString("SR_MRP"));
					dpProductDto.setBasicValue(rset.getString("SR_SIM_CARD_COST"));
					dpProductDto.setRechargeValue(rset.getString("SR_TALKTIME"));
					dpProductDto.setDpActivation(rset.getString("SR_ACTIVATION"));
					dpProductDto.setServiceTax(rset.getString("SR_SERVICE_TAX"));
					dpProductDto.setCess(rset.getString("SR_CESS_TAX"));
					dpProductDto.setCessTax(rset.getString("SR_SH_EDUCESS"));
					dpProductDto.setDistMargin(rset.getString("SR_DISCOUNT")); //dist_marging changed to SR_DISCOUNT
					dpProductDto.setDistPrice(rset.getString("SR_DISCOUNT_PRICE"));
					dpProductDto.setEffectiveDate(Utility.convertDateFormat(rset.getString("SR_EFFECTIVE_DATE"),"yyyy-MM-dd","dd-MMM-yyyy"));
					//Utility.convertDateFormat(rset.getString("SR_EFFECTIVE_DATE"),"MM/dd/YYYY","dd-MMM-yyyy");
					dpProductDto.setCreationDate(Utility.getDateAsString(rset.getDate("CREATED_DATE"),"dd-MMM-yyyy"));
					
					dpProductDto.setDpStatus(rset.getString("PSTATUS"));
					
					dpProductDtoList.add(dpProductDto);
					
				}
			
			
			logger.info("in getProductCategoryLst() Dao IML  ......Ended Success Fully");
			
		}
		catch (Exception e) 
		{
			e.printStackTrace();
			throw new DAOException(e.getMessage());
		}
		finally
		{
			DBConnectionManager.releaseResources(pstmt, rset);
		}
		return dpProductDtoList;
	}
       }
