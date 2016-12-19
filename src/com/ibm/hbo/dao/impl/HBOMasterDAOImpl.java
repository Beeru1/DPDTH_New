package com.ibm.hbo.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import org.apache.log4j.Logger;

import com.ibm.virtualization.recharge.common.ResourceReader;
import com.ibm.virtualization.recharge.db.DBConnectionManager;
import com.ibm.virtualization.recharge.dto.Account;
import com.ibm.virtualization.recharge.dto.UserSessionContext;
import com.ibm.dp.common.Constants;
import com.ibm.hbo.common.DBQueries;
import com.ibm.hbo.actions.HBOProdAction;
import com.ibm.hbo.common.DBConnection;
import com.ibm.hbo.common.DBQueries;
import com.ibm.hbo.common.HBOConstants;
import com.ibm.hbo.common.HBOUser;
import com.ibm.hbo.dao.HBOMasterDAO;
import com.ibm.hbo.dto.DistStockDTO;
import com.ibm.hbo.dto.HBOBusinessCategoryMasterDTO;
import com.ibm.hbo.dto.HBOCircleDTO;
import com.ibm.hbo.dto.HBOCommonDTO;
import com.ibm.hbo.dto.HBOMinReorderDTO;
import com.ibm.hbo.dto.HBOProductDTO;
import com.ibm.hbo.dto.HBOStockDTO;
import com.ibm.hbo.dto.HBOUserBean;
import com.ibm.hbo.dto.HBOUserMstr;
import com.ibm.hbo.dto.HBOWarehouseMasterDTO;
import com.ibm.hbo.exception.DAOException;
import com.ibm.hbo.forms.HBOBundleStockFormBean;
import com.ibm.hbo.forms.HBOBusinessCategoryFormBean;
import com.ibm.hbo.forms.HBOProductFormBean;
import com.ibm.hbo.forms.HBOWarehouseFormBean;
import com.ibm.utilities.PropertyReader;
import com.ibm.utilities.Utility;

/**
 * @author Parul
 *
 * To change the template for this generated type comment go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
public class HBOMasterDAOImpl implements HBOMasterDAO {

	private static Logger logger = Logger.getLogger(HBOMasterDAOImpl.class.getName());
	
	final int businesscategory =1;
	final int product=2;
	final int bundleProduct=3;
	final int avlSimStock=4;
	final int circle=5;
	final int account=6;
	final int businesscategoryext =7;
	final int businessCategorySerially =15;
	final int businesscategoryext1 =10;
	final int businessCategoryStock=8;
	final int prodlist=16;
	final int userList=9;
	final int retailerUserList=17;//jha
	final int retailerFSE=18;//rajiv jha
	final int distList=50;
	final int damagedProdList=20;
	final int distBusinessCategory=101;
	final int prodlistSwap=100;
	final int retailerUserFromList=27;//Added by Shilpa
	
	/**
	 * Single method to get values of Master Data depending upon the master field and condition passed
	 * @param Logged_in_userid
	 * @param master
	 * @param condition if any
	 * @return Arraylist of resultset
	 * @throws SQLException
	 */
	
	
	/*********************Start Code Done by Parul rastogi***********************************/
	
	public ArrayList findByPrimaryKey(
			String userId,
			int master,
			String condition)
			throws DAOException {

			Connection connection = null;
			PreparedStatement ps = null;
			ResultSet rs = null;
			StringBuffer sql = new StringBuffer("");			
			logger.info("Get Master Data for " + master);			
            switch (master){            
            case businesscategory:
            	sql = sql.append(DBQueries.businessCategory);
            	break;
            case distBusinessCategory:
            	sql = sql.append(DBQueries.businessCategory);
            	break;
            case product:
            	sql = sql.append(DBQueries.allProductList);
            	break;
            case circle:
            	sql = sql.append(DBQueries.circle);
            	break;
            case account:
            	sql = sql.append(DBQueries.account_ND);
            	break;
            case businesscategoryext:
            	sql = sql.append(DBQueries.businessCategoryExt);	
            	break;
            case businesscategoryext1:
            	sql = sql.append(DBQueries.businessCategoryExt);	
            	break;	
            case businessCategoryStock:
            	sql = sql.append(DBQueries.businessCategory);	
            	break;
            case prodlist:
            	sql = sql.append(DBQueries.ProductList);	
            	break;
            case prodlistSwap:
            	sql = sql.append(DBQueries.ProductListSwap);	
            	break;
            case userList:
            	sql = sql.append(DBQueries.UserList);	
            	break;
            case retailerUserList:
            	sql = sql.append(DBQueries.retailerUserList);	//jha
            	break;
            case retailerUserFromList:
            	sql = sql.append(DBQueries.retailerFromUserList);	//Added by Shilpa for getting from list of Retailers
            	break;
            case retailerFSE:
            	sql = sql.append(DBQueries.retailerFSE);	//rajiv jha
            	break;
            	
            case distList:
            	sql=sql.append(DBQueries.DistList);
            	break;
            case bundleProduct:
            	sql =sql.append(DBQueries.listOfBundledProd);
            	break;
            case damagedProdList:
            	sql =sql.append(DBQueries.listOfDamagedProd);
            	break;
            case businessCategorySerially:
            	sql =sql.append(DBQueries.businessCategorySerially);
            	break;
            }
			// Check for Conditions if any
			if (condition != null && !condition.equals("")) 
			{
				if(condition.equalsIgnoreCase(HBOConstants.CIRCLE_OWN))
				{
					sql.append(DBQueries.circleOwn);
				}
				else if(master == retailerUserList) //jha
				{
			    	sql.append(condition);
				}
				else if(master == retailerUserFromList) //jha
				{
			    	sql.append(condition);
				}
				else if(master == retailerFSE) //rajiv jha
				{
			    	sql.append(condition);
				}
				else{
					sql.append(" AND ");
					sql.append(condition);
				}	
			}
			try {
				connection = DBConnectionManager.getDBConnection();
				//sql.append(" With ur");
				System.err.println("Return to FSE check**** with ur removed : " + sql.toString());
				
				
				ps = connection.prepareStatement(sql.toString());
				if (condition != null && !condition.equals("")) {
					if(condition.equalsIgnoreCase(HBOConstants.CIRCLE_OWN)){
						ps.setString(1,userId);
					}
				}
				if(master==damagedProdList){
					int id = Integer.parseInt(userId.substring(userId.indexOf("@")+1,userId.length()));
					ps.setInt(1, id);
					ps.setInt(2, id);
				}
				
				
				rs = ps.executeQuery();
				return fetchSingleResult(rs, master);
			} catch (SQLException e) {
				e.printStackTrace();
				logger.error(
					"SQL Exception occured while inserting."
						+ "Exception Message: "
						+ e.getMessage());
				throw new DAOException("SQLException: " + e.getMessage(), e);
			} catch (Exception e) {
				e.printStackTrace();
				logger.error(
					"Exception occured while inserting."
						+ "Exception Message: "
						+ e.getMessage());
				throw new DAOException("Exception: " + e.getMessage(), e);
			} finally {
				try {
					if (rs != null)
						rs.close();
					if (ps != null)
						ps.close();
					if (connection != null) {
						connection.close();
					}
				} catch (Exception e) {
				}
			}
		}
	
	
	
	protected ArrayList fetchSingleResult(ResultSet rs, int master)
	throws SQLException {
	HBOWarehouseMasterDTO wareHouseMasterDTO = null;
	HBOBusinessCategoryMasterDTO businesscategoryMasterDTO = null;
	HBOProductDTO productDTO = null;
	DistStockDTO distStock=null;

	ArrayList arrMasterData = new ArrayList();
	int i = 0;
	while (rs.next()) {		
		switch (master){
			
		case businesscategory:
			businesscategoryMasterDTO = new HBOBusinessCategoryMasterDTO();
			businesscategoryMasterDTO.setBcode(rs.getInt("CATEGORY_CODE"));
			businesscategoryMasterDTO.setBname(rs.getString("CATEGORY_NAME"));
			businesscategoryMasterDTO.setNewBcode(rs.getInt("CATEGORY_CODE")+"@"+rs.getString("PURCHASE_INTERNALLY"));
			arrMasterData.add(businesscategoryMasterDTO);
			break;
		case businesscategoryext1:
			businesscategoryMasterDTO = new HBOBusinessCategoryMasterDTO();
			businesscategoryMasterDTO.setBcode(rs.getInt("CATEGORY_CODE"));
			businesscategoryMasterDTO.setBname(
			rs.getString("CATEGORY_NAME"));
			businesscategoryMasterDTO.setNewBcode(rs.getInt("CATEGORY_CODE")+"@"+rs.getString("PURCHASE_INTERNALLY"));
			arrMasterData.add(businesscategoryMasterDTO);
			break;
		case businesscategoryext:
			businesscategoryMasterDTO = new HBOBusinessCategoryMasterDTO();
			businesscategoryMasterDTO.setBcode(rs.getInt("CATEGORY_CODE"));
			businesscategoryMasterDTO.setBname(
			rs.getString("CATEGORY_NAME"));
			businesscategoryMasterDTO.setNewBcode(rs.getInt("CATEGORY_CODE")+"@"+rs.getString("PURCHASE_INTERNALLY"));
			arrMasterData.add(businesscategoryMasterDTO);
			break;	
		case businessCategoryStock:
			businesscategoryMasterDTO = new HBOBusinessCategoryMasterDTO();
			businesscategoryMasterDTO.setBcodeFlag(rs.getString("CATEGORY_CODE")+"#"+rs.getString("SERIALLY"));
			businesscategoryMasterDTO.setBcode(rs.getInt("CATEGORY_CODE"));
			businesscategoryMasterDTO.setBname(rs.getString("CATEGORY_NAME"));
			arrMasterData.add(businesscategoryMasterDTO);
			break;
		case avlSimStock :
			wareHouseMasterDTO = new HBOWarehouseMasterDTO();
			wareHouseMasterDTO.setWarehouseId(rs.getInt("WAREHOUSE_ID"));
			wareHouseMasterDTO.setName(rs.getString("WAREHOUSE_NAME"));
			arrMasterData.add(wareHouseMasterDTO);
			break;
		case product :
	    	productDTO = new HBOProductDTO();
			productDTO.setBcode(rs.getInt("CATEGORY_CODE"));
			productDTO.setBname(rs.getString("CATEGORY_NAME"));

			productDTO.setProductcode(rs.getString("PRODUCT_NAME"));

			productDTO.setProductdesc(rs.getString("PRODUCT_DESC"));
			productDTO.setStType(rs.getString("STOCK_TYPE")); 

			String stocktype = rs.getString("STOCK_TYPE");
			if (stocktype.equalsIgnoreCase("P")) {
				// for paired & non-paired
				productDTO.setStocktype("Paired");

				productDTO.setProd_stck_type(
					(rs.getString("PRODUCT_NAME")) + "(Paired)");
				productDTO.setPseudo_prodcode(
					"#Pair#" + rs.getString("PRODUCT_ID"));

			} else {
				productDTO.setStocktype("Non-Paired");

				productDTO.setProd_stck_type(
					(rs.getString("PRODUCT_NAME")) + "(Non-Paired)");
				productDTO.setPseudo_prodcode(
					"#NonP#" + rs.getString("PRODUCT_ID"));

			}
			productDTO.setWarranty(rs.getInt("PRODUCT_WARRANTY"));

			productDTO.setModelname(rs.getString("PRODUCT_NAME"));

			productDTO.setCreateddate(rs.getString("CREATED_DATE"));
			productDTO.setCreatedby(rs.getString("CREATED_BY"));
			productDTO.setUpdatedby(rs.getString("UPDATED_BY"));
			productDTO.setUpdateddate(rs.getString("UPDATED_Date"));
			arrMasterData.add(productDTO);
			break;
		case circle:
			wareHouseMasterDTO = new HBOWarehouseMasterDTO();
			wareHouseMasterDTO.setCircle(rs.getString("CIRCLE_ID"));
			wareHouseMasterDTO.setCirclename(rs.getString("CIRCLE_NAME"));
			arrMasterData.add(wareHouseMasterDTO);
			break;

		case bundleProduct:
			productDTO = new HBOProductDTO();
			productDTO.setModelcode(rs.getString("PRODUCT_NAME"));
			//productDTO.setModelname(rs.getString("MODEL_NAME"));
			productDTO.setProduct_id(rs.getInt("PRODUCT_ID"));
			arrMasterData.add(productDTO);
			break;
			

		case account:
			wareHouseMasterDTO = new HBOWarehouseMasterDTO();
			wareHouseMasterDTO.setWarehouseId(rs.getInt("ACCOUNT_ID"));
			wareHouseMasterDTO.setName(rs.getString("ACCOUNT_NAME"));
			arrMasterData.add(wareHouseMasterDTO);
			break;
		case prodlist:
		case prodlistSwap:
			distStock = new DistStockDTO();
			if(rs.getString("STOCK_TYPE")==null || rs.getString("STOCK_TYPE").equalsIgnoreCase("") || rs.getString("STOCK_TYPE").equalsIgnoreCase(" ") || rs.getString("STOCK_TYPE").length()==0){
				distStock.setOptionText(rs.getString("PRODUCT_NAME"));
			}
			else if(rs.getString("STOCK_TYPE").equalsIgnoreCase("P")||rs.getString("STOCK_TYPE").equalsIgnoreCase("N")){
				String paired=rs.getString("STOCK_TYPE").equalsIgnoreCase("P")?"Paired":"Non-Paired";
				distStock.setOptionText(rs.getString("PRODUCT_NAME")+"$"+paired);
			}
			
			distStock.setOptionValue(rs.getString("PRODUCT_ID"));
			distStock.setMrp(rs.getFloat("SR_MRP"));
			arrMasterData.add(distStock);
			break;			
		case userList:
			distStock = new DistStockDTO();          
			distStock.setUserId(rs.getInt("ACCOUNT_ID"));
			distStock.setUserName(rs.getString("ACCOUNT_NAME"));
			distStock.setOptionText(rs.getString("ACCOUNT_NAME"));
			distStock.setOptionValue(rs.getString("ACCOUNT_ID"));
			arrMasterData.add(distStock);
			break;
		case retailerUserList:             // jha
			distStock = new DistStockDTO();          
			distStock.setUserId(rs.getInt("ACCOUNT_ID"));
			distStock.setUserName(rs.getString("ACCOUNT_NAME"));
			distStock.setOptionText(rs.getString("ACCOUNT_NAME"));
			distStock.setOptionValue(rs.getString("ACCOUNT_ID"));
			arrMasterData.add(distStock);
			break;
		case retailerUserFromList:             // jha
			distStock = new DistStockDTO();          
			distStock.setUserId(rs.getInt("ACCOUNT_ID"));
			distStock.setUserName(rs.getString("ACCOUNT_NAME"));
			distStock.setOptionText(rs.getString("ACCOUNT_NAME"));
			distStock.setOptionValue(rs.getString("ACCOUNT_ID"));
			arrMasterData.add(distStock);
			break;
		case retailerFSE:             // rajiv jha
			distStock = new DistStockDTO();          
			distStock.setUserId(rs.getInt("ACCOUNT_ID"));
			distStock.setUserName(rs.getString("ACCOUNT_NAME"));
			distStock.setOptionText(rs.getString("ACCOUNT_NAME"));
			distStock.setOptionValue(rs.getString("ACCOUNT_ID"));
			arrMasterData.add(distStock);
			break;
			
		case damagedProdList:
			distStock = new DistStockDTO();
			distStock.setSerialNo(rs.getString("SERIAL_NO"));
			distStock.setDamagedStatus(rs.getString("MARK_DAMAGED"));
			distStock.setProductName(rs.getString("PRODUCT_NAME"));
			distStock.setDamageRemarks(rs.getString("DAMAGE_REMARKS"));
			arrMasterData.add(distStock);
			break;
			
		case businessCategorySerially:
			businesscategoryMasterDTO = new HBOBusinessCategoryMasterDTO();
			businesscategoryMasterDTO.setBcode(rs.getInt("CATEGORY_CODE"));
			businesscategoryMasterDTO.setBname(rs.getString("CATEGORY_NAME"));
			businesscategoryMasterDTO.setNewBcode(rs.getInt("CATEGORY_CODE")+"@"+rs.getString("PURCHASE_INTERNALLY"));
			arrMasterData.add(businesscategoryMasterDTO);
			break;	
		}

	} //end-of-while 
	return arrMasterData;
}
	
	public ArrayList getChange(ArrayList arr) throws DAOException
	{
		Connection con = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		StringBuffer sql=new StringBuffer();
		ArrayList arrgetChangedValues = new ArrayList();
		int param = 1;
		try {
			con = DBConnectionManager.getDBConnection();
			String condition=(String)arr.get(1);
			/*if (condition.equalsIgnoreCase("category")) {
				sql =
					sql.append(
						" SELECT PRODUCT_MASTER.*,BUSINESS_CATEGORY_MASTER.CATEGORY_NAME FROM PRODUCT_MASTER , BUSINESS_CATEGORY_MASTER WHERE BUSINESS_CATEGORY_MASTER.CATEGORY_CODE = PRODUCT_MASTER.CATEGORY_CODE ");
				sql.append(" and BUSINESS_CATEGORY_MASTER.CATEGORY_CODE =?");
				ps = con.prepareStatement(sql.toString());
				ps.setInt(1, Integer.parseInt(Id));
			}  else if (condition.equalsIgnoreCase("userType")) {

				if (Id.equalsIgnoreCase("1")) {
					logger.info("roleID===" + Id);
					sql =
						new StringBuffer("SELECT WAREHOUSE_ID,WAREHOUSE_NAME FROM WAREHOUSE_MASTER WHERE  ACTIVE_FLAG='A' AND  ROLE_ID=? ");
					ps = con.prepareStatement(sql.toString());
					ps.setInt(1, Integer.parseInt(Id));
				} else if (
					Id.equalsIgnoreCase("6") || Id.equalsIgnoreCase("7")) {
					sql =
						new StringBuffer("SELECT warehouse_circle_id,WAREHOUSE_ID,WAREHOUSE_NAME FROM WAREHOUSE_MASTER WHERE  ACTIVE_FLAG='A' ");
					sql.append(
						" AND ROLE_ID=? and warehouse_circle_id = (select warehouse_circle_id from user_master um, warehouse_master wmi ");
					sql.append(" where um.WAREHOUSE_ID= wmi.warehouse_id ");
					sql.append(" and um.USER_LOGIN_ID = ?) ");
					ps = con.prepareStatement(sql.toString());
					ps.setInt(1, Integer.parseInt(Id));
					HBOUserBean userBean = (HBOUserBean) FormBean;
					logger.info(
						"userBean.getUserLoginId():"
							+ userBean.getUserLoginId());
					logger.info("Id:" + Id);
					ps.setString(2, userBean.getUserLoginId());
				} else {
					sql =
						new StringBuffer("SELECT WAREHOUSE_ID,WAREHOUSE_NAME FROM WAREHOUSE_MASTER WHERE  ACTIVE_FLAG='A' AND ROLE_ID=?  ");
					//-- AND PARENT_WH_ID=? ");					
					ps = con.prepareStatement(sql.toString());
					ps.setInt(1, Integer.parseInt(Id));
					//ps.setInt(2,Integer.parseInt(warehouseId));
				} //end of else
				//	==end of changes

			}*/ 
			// TO GET AVAILABLE PRODUCT QUANTITY 
			if (condition.startsWith("#NonP")|| condition.startsWith("#Pair")) {
				Connection db2conn = DBConnectionManager.getDBConnection();
				String Id= (String) arr.get(0);
				int warehouseID = (Integer)arr.get(2);
				int selectedCircleId=Integer.parseInt((String)arr.get(3));
				ArrayList roleList=(ArrayList)arr.get(4);
				
				String pseudo_Code = condition;
				String prodCode = "";
				prodCode = pseudo_Code.substring(5, pseudo_Code.length());
				if (roleList.contains("ROLE_ND")
					&& condition.startsWith("#Pair")
					&& Id.equalsIgnoreCase("Bundled")) {
					sql =sql.append(DBQueries.NDBundledSIH);
					ps = db2conn.prepareStatement(sql.toString());
					ps.setInt(1, warehouseID);
					ps.setString(2, prodCode);
					ps.setInt(3, selectedCircleId);
				}  if (	condition.startsWith("#NonP")
						|| (condition.startsWith("#Pair")
							&& Id.equalsIgnoreCase("UNBUNDLED"))) {
					sql =sql.append(DBQueries.UnbundledSIH);
					//	new StringBuffer("select UNBUNDLED_SIH from WAREHOUSE_STOCK where WAREHOUSE_ID =? and PRODUCT_CODE=? and CIRCLE_ID is null");
					ps = db2conn.prepareStatement(sql.toString());
					ps.setInt(1, warehouseID);
					ps.setString(2, prodCode);
				} if (!roleList.contains("ROLE_ND")&&
							(condition.startsWith("#Pair"))
								&& (Id.equalsIgnoreCase("Bundled"))) {
					
					sql =sql.append(DBQueries.bundledSIH);
					ps = db2conn.prepareStatement(sql.toString());
					ps.setInt(1, warehouseID);
					ps.setString(2, prodCode);
				}
				rs = ps.executeQuery();
				while(rs.next()){
				HBOStockDTO stockDTO = new HBOStockDTO();
				stockDTO.setAvlProducts(rs.getString(1));
				arrgetChangedValues.add(stockDTO);
			}
			}
			// TO GET THE CIRCLELIST ON THE BASIS OF PROD SELECTION
			else if (condition.equalsIgnoreCase("Bundling")) {
				// Values from ArrayList
				
				ArrayList roleList=(ArrayList)arr.get(3);
				int circleId=(Integer)arr.get(4);
				HBOWarehouseMasterDTO wareHouseMasterDTO = null;
				ArrayList circleList = new ArrayList();
				
				sql = new StringBuffer("select CIRCLE_ID,CIRCLE_NAME from VR_CIRCLE_MASTER ");
				if (roleList.contains("ROLE_ND")|| (roleList.contains("ROLE_LD"))&& ((String)(arr.get(0))).equalsIgnoreCase("Unbundled")) {
					ps = con.prepareStatement(sql.toString());
					
				}
				if (roleList.contains("ROLE_SS")
					|| (roleList.contains("ROLE_LD"))&& ((String)(arr.get(0))).equalsIgnoreCase("bundled")
					|| roleList.contains("ROLE_AD")) {
					sql.append(
						"where circle_id=? ");
					
					ps = con.prepareStatement(sql.toString());
					ps.setInt(1, circleId);
				}
				rs = ps.executeQuery();
				while (rs.next()) {
					wareHouseMasterDTO = new HBOWarehouseMasterDTO();
					wareHouseMasterDTO.setOptionValue(rs.getString(1));//CircleID
					wareHouseMasterDTO.setOptionText(rs.getString(2));//CircleName
					circleList.add(wareHouseMasterDTO);
				}
				return circleList;

			} 
			else if (((String)(arr.get(1))).equalsIgnoreCase(HBOConstants.HBO_CATEGORY)) {
				sql = new StringBuffer(DBQueries.getChangeCategory);
				ps = con.prepareStatement(sql.toString());
				ps.setInt(param++, Integer.parseInt((String)(arr.get(0))));
				ps.setInt(param++, Integer.parseInt((String)(arr.get(2))));
				rs = ps.executeQuery();
			while (rs.next()) {
				if (((String)(arr.get(1))).equalsIgnoreCase(HBOConstants.HBO_CATEGORY)) {
					HBOProductDTO productDTO = new HBOProductDTO();
					productDTO.setProductcode(rs.getString(HBOConstants.COL_PRODUCT_NAME));
					productDTO.setProductId(rs.getString(HBOConstants.COL_PRODUCT_ID));
					arrgetChangedValues.add(productDTO);
				}
			}
			}else if (((String)(arr.get(1))).equalsIgnoreCase(HBOConstants.HBO_RequisitionCATEGORY)) {
				sql = new StringBuffer(DBQueries.getRequisitionCategory);
				ps = con.prepareStatement(sql.toString());
				ps.setInt(param, Integer.parseInt((String)(arr.get(0))));
				ps.setInt(param+1, (Integer)arr.get(2));
				rs = ps.executeQuery();
			while (rs.next()) {
				if (((String)(arr.get(1))).equalsIgnoreCase(HBOConstants.HBO_RequisitionCATEGORY)) {
					HBOProductDTO productDTO = new HBOProductDTO();
					productDTO.setProductcode(rs.getString(HBOConstants.COL_PRODUCT_NAME));
					productDTO.setProductId(rs.getString(HBOConstants.COL_PRODUCT_ID));
					arrgetChangedValues.add(productDTO);
				}
			}
			}
			
			
			//To Get the available  Sim STock for a Circle
			 if (condition.equalsIgnoreCase("sim_avlbstk")) {
				Connection db2conn = DBConnectionManager.getDBConnection();
				ps = db2conn.prepareStatement(DBQueries.avlSimStockInhandforACircle);
				ps.setString(1,(String)arr.get(2));
				ps.setInt(2,Integer.parseInt((String)arr.get(0)));
				rs = ps.executeQuery();
				if (rs.next()){
				  HBOStockDTO stockDTO = new HBOStockDTO();
					stockDTO.setAvlStock(rs.getString("avlstock"));
					arrgetChangedValues.add(stockDTO);
				}
			}else if (condition.equalsIgnoreCase("pr_avlbstk")) {
				Connection db2conn = DBConnectionManager.getDBConnection();
				ps = db2conn.prepareStatement(DBQueries.avlUnbundledStockInhand);			

					ps.setInt(1, Integer.parseInt((String)arr.get(2)));
					ps.setInt(2, Integer.parseInt((String)arr.get(0)));
					rs = ps.executeQuery();
					if (rs.next()){
						  HBOStockDTO stockDTO = new HBOStockDTO();
						  stockDTO.setAvlStock(rs.getString(1));
						  arrgetChangedValues.add(stockDTO);
						}
					
				}else if (condition.equalsIgnoreCase("warehouse")) {
					String stockType=((String)arr.get(3)).startsWith("#Pair")?"P":"N";
					String bundleFlag=((String)arr.get(5)).equalsIgnoreCase("bundled")?"Y":"N";
					int circleId=Integer.parseInt((String)arr.get(0));
					int warehouseId=(Integer)arr.get(2);
					ArrayList warehouse=getWarehouse(circleId,(ArrayList)arr.get(4),warehouseId,bundleFlag,stockType);
					for(int i=0;i<warehouse.size();i++){
						arrgetChangedValues.add(warehouse.get(i));
					}
				}

		} catch (SQLException e) {
			e.printStackTrace();
			logger.error(
				"SQL Exception occured while inserting."
					+ "Exception Message: "
					+ e.getMessage());
			throw new DAOException("SQLException: " + e.getMessage(), e);
		} catch (Exception e) {
			e.printStackTrace();
			logger.error(
				"Exception occured while inserting."
					+ "Exception Message: "
					+ e.getMessage());
			throw new DAOException("Exception: " + e.getMessage(), e);
		} finally {
			try {
				if (rs != null)
					rs.close();
				if (ps != null)
					ps.close();
				if (con != null) {
					con.close();
				}
			} catch (Exception e) {
			}
		}
		return arrgetChangedValues;
	}
	
	public ArrayList getWarehouse(
			int selectedCircle,
			ArrayList roleList,int warehouseId,
			 String bundleFlag,String stockType)
			throws DAOException {
			int parentWarehouseId = 0;
			Connection con = null;
			PreparedStatement ps = null;
			ResultSet rs = null;
			StringBuffer sqllocal = new StringBuffer("");
			ArrayList<HBOWarehouseMasterDTO> getWarehouse = new ArrayList<HBOWarehouseMasterDTO>();
			
			try {
				con = DBConnectionManager.getDBConnection();
				String SQL_QUERY = "select eligible_role,cross_circle_flag from DP_ASSIGN_ROLE_MAPPING " +
						" where login_role = ? and stock_type = ? and bundle_flag = ?";
				String roleName = "";
				String crossCircleFlag = "A";
				String adFlag = "N";
				StringBuffer eligibleRole = new StringBuffer("");
				if (roleList.contains("ROLE_ND")) {
					roleName = "ROLE_ND";
				}else if(roleList.contains("ROLE_LD")){
					roleName = "ROLE_LD";
				}else if(roleList.contains("ROLE_AD")){
					roleName = "ROLE_AD";
				}else if(roleList.contains("ROLE_SS")){
					roleName = "ROLE_SS";
				}
				
				ps=con.prepareStatement(SQL_QUERY);
				ps.setString(1, roleName);
				ps.setString(2, stockType);
				ps.setString(3, bundleFlag);
				
				// To-do : Execute Query with DB Connection
				ResultSet rsInner = ps.executeQuery();
				
				while(rsInner.next()) {
					eligibleRole.append("'"+rsInner.getString("eligible_role")+"'");
					eligibleRole.append(",");
					String eligible_role=rsInner.getString("eligible_role");
					if(eligible_role.equals("ROLE_AD")) {
						adFlag = "Y";
					}
				}
				if(eligibleRole.toString().indexOf(",") != -1 ) {
					eligibleRole.append("'X'");
				}
				// IN case LocalDist logs in, select parent since Local-2-Local is possible in case of
				// Un-paired. For National, since parent is MA.. no check required
				if (roleName.equalsIgnoreCase("ROLE_LD")){
						sqllocal.append("select PARENT_ACCOUNT from VR_Account_Details where Account_Id = ? ");
						ps = con.prepareStatement(sqllocal.toString());
						ps.setInt(1, warehouseId);
						rs = ps.executeQuery();
						while (rs.next()) {
							parentWarehouseId= rs.getInt("PARENT_ACCOUNT");
						}
				}
				StringBuffer assignQuery = new StringBuffer("");
				if(roleName.equalsIgnoreCase("ROLE_SS")){
				}else{
					assignQuery.append("Select Account_Id,Account_Name from  VR_Account_Details,VR_LOGIN_MASTER VLM," +
						" VR_GROUP_ROLE_MAPPING VGRM,VR_ROLE_MASTER vrm where " +
						" VR_Account_Details.ACCOUNT_ID=VLM.LOGIN_ID  and VLM.GROUP_ID = VGRM.GROUP_ID " +
						" and VGRM.role_id = vrm.ROLE_ID " +
						" and vrm.ROLE_Name  in ("+eligibleRole.toString()+ ")") ;
						// Fetch Parent Details as described above for Local LD
						if(roleName.equalsIgnoreCase("ROLE_LD")) {
							assignQuery.append(" and VR_Account_Details.PARENT_ACCOUNT=");
							assignQuery.append(parentWarehouseId);
						}
					assignQuery.append(" and VR_Account_Details.CIRCLE_ID =? ");
				}
				// Special Check in case of AD whereby no parent check is required 
				// as Hierarchy gets seperate here from Vendor to Airtel
				if(eligibleRole.indexOf("ROLE_AD") != -1) {
					if(assignQuery.length() > 0) {
						assignQuery.append(" Union ");
					}
					assignQuery.append("Select Account_Id,Account_Name from  VR_Account_Details ,VR_LOGIN_MASTER VLM, " +
					" VR_GROUP_ROLE_MAPPING VGRM,VR_ROLE_MASTER vrm, VR_ACCOUNT_LEVEL_MASTER alm where " +
					" VR_Account_Details.ACCOUNT_ID=VLM.LOGIN_ID  and VLM.GROUP_ID = VGRM.GROUP_ID " +
					" and VGRM.role_id = vrm.ROLE_ID  and alm.level_id=VR_Account_Details.ACCOUNT_LEVEL " +
					" and vrm.ROLE_Name = 'ROLE_AD' AND alm.level_name='Airtel Distributor' " +
					" AND VR_Account_Details.CIRCLE_ID ="+selectedCircle);
				}
				assignQuery.append(" with ur");
				ps = con.prepareStatement(assignQuery.toString());
				ps.setInt(1, selectedCircle);
				rs = ps.executeQuery();
				String warehouseString = "";
				while (rs.next()) {
					warehouseString = ""+warehouseId;
					if(!warehouseString.equalsIgnoreCase(rs.getString("Account_Id"))){
						HBOWarehouseMasterDTO warehouseMasterDTO = new HBOWarehouseMasterDTO();
						warehouseMasterDTO.setOptionText(rs.getString("Account_Name"));//CircleID
						warehouseMasterDTO.setOptionValue(rs.getString("Account_Id"));//CircleID
						getWarehouse.add(warehouseMasterDTO);
					}
				}
			} catch (SQLException e) {
				e.printStackTrace();
				logger.error(
					"SQL Exception occured while inserting."
						+ "Exception Message: "
						+ e.getMessage());
				throw new DAOException("SQLException: " + e.getMessage(), e);
			} catch (Exception e) {
				e.printStackTrace();
				logger.error(
					"Exception occured while inserting."
						+ "Exception Message: "
						+ e.getMessage());
				throw new DAOException("Exception: " + e.getMessage(), e);
			} finally {
				try {
					if (rs != null)
						rs.close();
					if (ps != null)
						ps.close();
					if (con != null) {
						con.close();
					}
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
			return getWarehouse;
		}
	
	/**
	 * This method will return the list of all Central warehouse and the List of Local ware Warehouse from the same circle as Looged in Admin belong to
	 * @param hboUserBean,master,condition
	 * @author Parul
	 * @return ArrayList
	 * @throws DAOException
	 */

public ArrayList findByPrimaryKey(
	HBOUser obj)
	throws DAOException {
	Connection con = null;
	PreparedStatement ps = null;
	ResultSet rs = null;
	StringBuffer sql = new StringBuffer("");
	sql.append(DBQueries.listofNDandLD);
	try {
		con = DBConnectionManager.getDBConnection();
		sql.append(" with ur");
		ps = con.prepareStatement(sql.toString());
		ps.setString(1, obj.getCircleId());
		
		rs = ps.executeQuery();
		return fetchSingleResult(rs, "avlSimStock");
	} catch (SQLException e) {
		e.printStackTrace();
		logger.error(
			"SQL Exception occured while inserting."
				+ "Exception Message: "
				+ e.getMessage());
		try
		{
			String err = ResourceReader.getCoreResourceBundleValue(Constants.Excess_Stock_Full_Final_Critical);
			logger.info(err + "::" +Utility.getCurrentDate());
		}
		catch(Exception ex)
		{
			ex.printStackTrace();
		}
		throw new DAOException("SQLException: " + e.getMessage(), e);
	} catch (Exception e) {
		e.printStackTrace();
		logger.error(
			"Exception occured while inserting."
				+ "Exception Message: "
				+ e.getMessage());
		try
		{
			String err = ResourceReader.getCoreResourceBundleValue(Constants.Excess_Stock_Full_Final_Critical);
			logger.info(err + "::" +Utility.getCurrentDate());
		}
		catch(Exception ex)
		{
			ex.printStackTrace();
		}
		throw new DAOException("Exception: " + e.getMessage(), e);
	} finally {
		try {
			if (rs != null)
				rs.close();
			if (ps != null)
				ps.close();
			if (con != null) {
				con.close();
			}
		} catch (Exception e) {
		}
	}
}
/*********************End Code Done by Parul Rastogi***********************************/

	public ArrayList findByPrimaryKey(
		String userId,
		String master,
		String condition)
		throws DAOException {

		Connection con = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		StringBuffer sql = new StringBuffer("");
		StringBuffer sqlOrder = new StringBuffer("");
		int iRoleID = 0;
		String userLoginId = null; //changes

		logger.info("Get Master Data for " + master);
		if (master.equalsIgnoreCase("viewWarehouse")) {
			if (userId != null && !userId.equals("")) {
				iRoleID = Integer.parseInt(userId);
				userLoginId = condition; //changes
				condition = null; //chnages

			}

			//Changes done by sachin on 24 Jan ---
			sql =
				sql.append(
					"SELECT WAM.*, CIR.CIRCLE_NAME, STA.STATE_NAME, ROL.ROLE_NAME FROM WAREHOUSE_MASTER WAM, CIRCLE_MASTER CIR, ROLE_MASTER ROL, STATE_MASTER STA WHERE CIR.CIRCLE_ID = WAM.WAREHOUSE_CIRCLE_ID AND ROL.ROLE_ID = WAM.ROLE_ID AND STA.STATE_ID = WAM.WAREHOUSE_STATE_ID  ");
			switch (iRoleID) {
				case 1 :
					sql = sql.append(" AND WAM.ROLE_ID in (1,2,3)");
					break;
				case 2 :
					sql =
						sql.append(
							" AND WAM.ROLE_ID in (2,6,7) and warehouse_circle_id = (select warehouse_circle_id from user_master um, warehouse_master wmi where um.WAREHOUSE_ID= wmi.warehouse_id and um.USER_LOGIN_ID = '"
								+ userLoginId
								+ "')");
					break;
				case 3 :
					sql = sql.append(" AND WAM.ROLE_ID in (3,4,5)");
					break;
			} //for switch
		} // for warehouse

		if (master.equalsIgnoreCase("warehouse")) {
			sql =
				sql.append(
					"SELECT WAM.*, CIR.CIRCLE_NAME, STA.STATE_NAME, ROL.ROLE_NAME FROM WAREHOUSE_MASTER WAM, CIRCLE_MASTER CIR, ROLE_MASTER ROL, STATE_MASTER STA WHERE CIR.CIRCLE_ID = WAM.WAREHOUSE_CIRCLE_ID AND ROL.ROLE_ID = WAM.ROLE_ID AND STA.STATE_ID = WAM.WAREHOUSE_STATE_ID  AND WAM.ACTIVE_FLAG='A' ");
		} // for warehouse
		//	end of changes----

		else if (master.equalsIgnoreCase("state")) {
			sql = sql.append("select * from  STATE_MASTER ");
		} 
			

		 else if (master.equalsIgnoreCase("reorder-warehouse")) {
			logger.info("reorder>>>>>>>>>>");
			sql =
				sql.append(
					"select  wm2.* from warehouse_master  wm1 , warehouse_master  wm2 ");
			sql.append(
				"where wm1.warehouse_circle_id = wm2.warehouse_circle_id and wm2.role_id=6 ");
			
			logger.info("reorder>>>>>>>>>>" + sql.toString());
		} else if (
			(master.equalsIgnoreCase("CentralSIH"))
				|| (master.equalsIgnoreCase("ADSales"))
				|| (master.equalsIgnoreCase("ProductWiseStockSale"))) {
			sql =
				sql.append(
					" SELECT distinct CM.* FROM warehouse_master WM, USER_Master UM ,Circle_Master CM  WHERE ((UM.warehouse_id = WM.warehouse_id)) AND WM.Warehouse_Circle_ID = CM.Circle_ID  ");
		}

		// Check for Conditions if any
		if (condition != null && !condition.equals("")) {
			sql.append(" AND ");
			sql.append(condition);
		}
		logger.info("sql------------" + sql.toString());
		// Execute the final SQL
		try {
			con = DBConnection.getDBConnection();
			ps = con.prepareStatement(sql.toString());
			rs = ps.executeQuery();
			return fetchSingleResult(rs, master);
		} catch (SQLException e) {
			e.printStackTrace();
			logger.error(
				"SQL Exception occured while inserting."
					+ "Exception Message: "
					+ e.getMessage());
			throw new DAOException("SQLException: " + e.getMessage(), e);
		} catch (Exception e) {
			e.printStackTrace();
			logger.error(
				"Exception occured while inserting."
					+ "Exception Message: "
					+ e.getMessage());
			throw new DAOException("Exception: " + e.getMessage(), e);
		} finally {
			try {
				if (rs != null)
					rs.close();
				if (ps != null)
					ps.close();
				if (con != null) {
					con.close();
				}
			} catch (Exception e) {
			}
		}
	}


	/**
	 * Method to return the Arraylist for the resultset passed
	 * @param rs
	 * @param master
	 * @return
	 * @throws SQLException
	 */
	protected ArrayList fetchSingleResult(ResultSet rs, String master)
		throws SQLException {
		HBOWarehouseMasterDTO wareHouseMasterDTO = null;
		HBOBusinessCategoryMasterDTO businesscategoryMasterDTO = null;
		HBOProductDTO productDTO = null;
		Account accountDTO=null;
		ArrayList arrMasterData = new ArrayList();
		int i = 0;
		while (rs.next()) {
			if (master.equals("state")) {
				wareHouseMasterDTO = new HBOWarehouseMasterDTO();
				wareHouseMasterDTO.setState(rs.getString("STATE_ID"));
				wareHouseMasterDTO.setStatename(rs.getString("STATE_NAME"));
				arrMasterData.add(wareHouseMasterDTO);
			} else if (
				(master.equals("circle"))
					|| (master.equalsIgnoreCase("CentralSIH"))
					|| (master.equalsIgnoreCase("ADSales"))
					|| (master.equalsIgnoreCase("ProductWiseStockSale"))) {
				wareHouseMasterDTO = new HBOWarehouseMasterDTO();
				wareHouseMasterDTO.setCircle(rs.getString("CIRCLE_ID"));
				wareHouseMasterDTO.setCirclename(rs.getString("CIRCLE_NAME"));
				arrMasterData.add(wareHouseMasterDTO);
			} 
			else if (master.equals("viewcircle")) {
			  wareHouseMasterDTO = new HBOWarehouseMasterDTO();
			  wareHouseMasterDTO.setCircle(rs.getString("CIRCLE_ID"));
			  wareHouseMasterDTO.setCirclename(rs.getString("CIRCLE_NAME"));
			  arrMasterData.add(wareHouseMasterDTO);			
			  }
			else if (master.equals("role")) {
				wareHouseMasterDTO = new HBOWarehouseMasterDTO();
				wareHouseMasterDTO.setRole(rs.getInt("ROLE_ID"));
				wareHouseMasterDTO.setRolename(rs.getString("ROLE_NAME"));
				arrMasterData.add(wareHouseMasterDTO);
			} else if (master.equals("businesscategory")) {
				businesscategoryMasterDTO = new HBOBusinessCategoryMasterDTO();
				businesscategoryMasterDTO.setBcode(rs.getInt("CATEGORY_CODE"));
				businesscategoryMasterDTO.setBname(
					rs.getString("CATEGORY_NAME"));
				arrMasterData.add(businesscategoryMasterDTO);
			} else if (master.equalsIgnoreCase("avlSimStock")) {// Changed By Parul on 28 /08/08
			 	accountDTO = new Account();
				accountDTO.setAccountId(rs.getLong("Account_Id"));
				accountDTO.setAccountName(rs.getString("Account_Name"));
				arrMasterData.add(accountDTO);
			}
			
			else if (
				master.equals("product") || master.equals("bundleProduct")) {
				

				productDTO = new HBOProductDTO();
				productDTO.setBcode(rs.getInt("CATEGORY_CODE"));
				productDTO.setBname(rs.getString("CATEGORY_NAME"));
				productDTO.setProductcode(rs.getString("PRODUCT_CODE"));
				productDTO.setProductdesc(rs.getString("PRODUCT_DESC"));
				productDTO.setStType(rs.getString("STOCK_TYPE")); //by sachin

				String stocktype = rs.getString("STOCK_TYPE");
				if (stocktype.equalsIgnoreCase("P")) {
					
					productDTO.setStocktype("Paired");
					//productDTO.setProd_stck_type(
					//	(rs.getString("PRODUCT_CODE")) + "(Paired)");
					//productDTO.setPseudo_prodcode(
					//	"#Pair#" + rs.getString("PRODUCT_CODE"));
				} else {
					productDTO.setStocktype("Non-Paired");
					//productDTO.setProd_stck_type(
					//	(rs.getString("PRODUCT_CODE")) + "(Non-Paired)");
					//productDTO.setPseudo_prodcode(
					//	"#NonP#" + rs.getString("PRODUCT_CODE"));
				}
				productDTO.setWarranty(rs.getInt("PRODUCT_WARRANTY"));
				productDTO.setModelcode(rs.getString("PRODUCT_CODE"));
				productDTO.setModelname(rs.getString("PRODUCT_NAME"));
				productDTO.setCompanyname(rs.getString("COMPANY_NAME"));
				productDTO.setCreateddate(rs.getString("CREATED_DATE"));
				productDTO.setCreatedby(rs.getString("CREATED_BY"));
				productDTO.setUpdatedby(rs.getString("UPDATED_BY"));
				productDTO.setUpdateddate(rs.getString("UPDATED_DT"));
				arrMasterData.add(productDTO);
			} else if (master.equalsIgnoreCase("reorder-warehouse")) {
				wareHouseMasterDTO = new HBOWarehouseMasterDTO();
				wareHouseMasterDTO.setWarehouseId(rs.getInt("WAREHOUSE_ID"));
				wareHouseMasterDTO.setName(rs.getString("WAREHOUSE_NAME"));
				arrMasterData.add(wareHouseMasterDTO);
			}

		} 
		return arrMasterData;
	}

	/**
	 * Method to insert the master data into the database
	 * @param userId
	 * @param formBean containing data
	 * @param master
	 * @param condition if any
	 * @return null
	 * @throws SQLException
	 */
	public void insert(
		String userId,
		Object formBean,
		String master,
		String condition)
		throws DAOException {

		HBOBusinessCategoryFormBean businessCategoryFormBean = null;
		HBOProductFormBean productFormBean = null;
		HBOWarehouseFormBean warehouseFormBean = null;

		Connection con = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		StringBuffer sql = new StringBuffer("");
		ArrayList arrPassParameters = new ArrayList();
		String transaction = "";

		logger.info("Insert Master Data for " + master);
		if (master.equalsIgnoreCase("businesscategory")) {
			businessCategoryFormBean = (HBOBusinessCategoryFormBean) formBean;
			int bcode = businessCategoryFormBean.getBcode();
			logger.info("Value of bcode===" + bcode);
			if (bcode != 0) {
				sql.append(
					"UPDATE DP_BUSINESS_CATEGORY_MASTER SET CATEGORY_NAME = ? WHERE CATEGORY_CODE=");
				sql.append(bcode);
			} else {
				sql.append(
					"INSERT INTO DP_BUSINESS_CATEGORY_MASTER(CATEGORY_CODE,CATEGORY_NAME) VALUES (BCM_SEQ.NEXTVAL,?)");
			}
			arrPassParameters.add(businessCategoryFormBean.getBname());
		} else if (master.equalsIgnoreCase("product")) {
			productFormBean = (HBOProductFormBean) formBean;
			String pcode = productFormBean.getProductcode();
			if (pcode != null && pcode != "" && !pcode.equals("")) {
				sql.append(
					"UPDATE DP_PRODUCT_MASTER SET PRODUCT_DESC=?,PRODUCT_WARRANTY=?,UPDATED_DT=SYSDATE,UPDATED_BY=?");
				sql.append("WHERE PRODUCT_CODE='");
				sql.append(pcode);
				sql.append("'");
				transaction = "update";
			} else {
				sql.append("INSERT INTO DP_PRODUCT_MASTER(");
				sql.append("CATEGORY_CODE, PRODUCT_CODE, MODEL_CODE,");
				sql.append("MODEL_NAME, COMPANY_NAME, PRODUCT_DESC,");
				sql.append("STOCK_TYPE, PRODUCT_WARRANTY, CREATED_DATE,");
				sql.append("CREATED_BY, UPDATED_BY, UPDATED_DT)");
				sql.append(
					"VALUES( ?,?||PM_SEQ.NEXTVAL,? ,?,? ,?,?,? ,SYSDATE ,? ,? ,SYSDATE)");
				transaction = "insert";
			}
		} else if (master.equalsIgnoreCase("warehouse")) {
			warehouseFormBean = (HBOWarehouseFormBean) formBean;
			int wcode = warehouseFormBean.getWarehouseId();
			logger.info("Value of warehouse Id===" + wcode);
			if (wcode != 0) {
				sql.append(
					"UPDATE WAREHOUSE_MASTER SET WAREHOUSE_NAME=?, WAREHOUSE_ADDRESS=?,  WAREHOUSE_CITY=?, WAREHOUSE_STATE_ID=?, WAREHOUSE_CIRCLE_ID=?,");
				sql.append(
					"WAREHOUSE_CONTACT_NO=?, ACTIVE_FLAG=?, UPDATED_BY=?,  UPDATED_DT=SYSDATE WHERE WAREHOUSE_ID=");
				sql.append(wcode);
				transaction = "update";
			} else {
				sql.append(
					"INSERT INTO WAREHOUSE_MASTER ( WAREHOUSE_ID, WAREHOUSE_NAME, WAREHOUSE_ADDRESS,  WAREHOUSE_CITY, WAREHOUSE_STATE_ID, WAREHOUSE_CIRCLE_ID,");
				sql.append(
					"WAREHOUSE_CONTACT_NO, ACTIVE_FLAG, ROLE_ID, CREATED_BY, CREATED_DT, UPDATED_BY,  UPDATED_DT, PARENT_WH_ID) VALUES (WAR_SEQ.NEXTVAL ,? , ?, ? ,? ,? ,? ,? ,? , ?,SYSDATE , ?,SYSDATE,? )");
				transaction = "insert";
			}
		}
		logger.info("SQL:" + sql.toString());
		
		try {
			con = DBConnection.getDBConnection();
			ps = con.prepareStatement(sql.toString());
			if (master.equalsIgnoreCase("product")) {
				if (transaction.equals("insert")) {
					int param = 1;
					String modelcode = productFormBean.getModelcode();
					String companyname = productFormBean.getCompanyname();
					String modelname = productFormBean.getModelname();
					String prcode =
						modelcode + "_" + modelname + "_" + companyname + "_";

					ps.setInt(param++, productFormBean.getBcode());
					// Business Cat
					ps.setString(param++, prcode);
					//combined modelcode  comopanyname
					ps.setString(param++, productFormBean.getModelcode());
					//model_code
					ps.setString(param++, productFormBean.getModelname());
					// model Name
					ps.setString(param++, productFormBean.getCompanyname());
					// Company Name
					ps.setString(param++, productFormBean.getProductdesc());
					// Product Desc
					ps.setString(param++, productFormBean.getStocktype());
					// Stock Type
					ps.setInt(param++, productFormBean.getWarranty());
					// Standard Warranty
					ps.setString(param++, userId); // Login ID Created
					ps.setString(param++, userId); // Login ID Modified
				} else { // Update
					int param = 1;
					ps.setString(param++, productFormBean.getProductdesc());
					ps.setInt(param++, productFormBean.getWarranty());
					ps.setString(param++, userId);
				}
			} else if (master.equalsIgnoreCase("warehouse")) {
				if (transaction.equals("insert")) {
					int param = 1;
					ps.setString(param++, warehouseFormBean.getName()); // name
					ps.setString(param++, warehouseFormBean.getAddress());
					//Address
					ps.setString(param++, warehouseFormBean.getCity()); // city
					ps.setString(param++, warehouseFormBean.getState());
					// state
					ps.setString(param++, warehouseFormBean.getCircle());
					// circle
					ps.setString(param++, warehouseFormBean.getContact());
					// contact no
					ps.setString(param++, warehouseFormBean.getStatus());
					//status
					ps.setInt(param++, warehouseFormBean.getRole()); //role
					ps.setString(param++, userId); // Login ID Created
					ps.setString(param++, userId); // Login ID Modified

				
					ps.setInt(
						param++,
						warehouseFormBean.getParentWarehouseId());
					// Parent Warehouse Id
				} else { // Update
					int param = 1;
					ps.setString(param++, warehouseFormBean.getName()); //name
					ps.setString(param++, warehouseFormBean.getAddress());
					//Address
					ps.setString(param++, warehouseFormBean.getCity()); // city
					ps.setString(param++, warehouseFormBean.getState());
					// state
					ps.setString(param++, warehouseFormBean.getCircle());
					// circle
					ps.setString(param++, warehouseFormBean.getContact());
					// contact no
					ps.setString(param++, warehouseFormBean.getStatus());
					//status
					ps.setString(param++, userId); // updated by
				}
			} else {
				for (int i = 0; i < arrPassParameters.size(); i++) {
					ps.setString(i + 1, (String) arrPassParameters.get(i));
				}
			}
			ps.executeUpdate();
			con.commit();
		} catch (SQLException e) {
			e.printStackTrace();
			logger.error(
				"SQL Exception occured while inserting."
					+ "Exception Message: "
					+ e.getMessage());
			throw new DAOException("SQLException: " + e.getMessage(), e);
		} catch (Exception e) {
			e.printStackTrace();
			logger.error(
				"Exception occured while inserting."
					+ "Exception Message: "
					+ e.getMessage());
			throw new DAOException("Exception: " + e.getMessage(), e);
		} finally {
			try {
				if (ps != null)
					ps.close();
				if (con != null) {
					con.close();
				}
			} catch (Exception e) {
			}
		}
	}

	/**
	 * @author - Aditya
	 * @param userId
	 * @param formBean
	 * @param master
	 * @param condition
	 * @return
	 * @throws HBOException
	 */
	public void insertMRL(
		String userId,
		Object minReordDTO,
		String master,
		String condition)
		throws DAOException {
		Connection con = null;
		PreparedStatement ps = null;
		ResultSet rs = null;

		StringBuffer sql = new StringBuffer("");
		HBOMinReorderDTO minReorderDTO = (HBOMinReorderDTO) minReordDTO;
		

		try {
			con = DBConnection.getDBConnection();
			sql.append(PropertyReader.getValue("MinReorder_select_mrl"));
			ps = con.prepareStatement(sql.toString());
			ps.setInt(1, Integer.parseInt(minReorderDTO.getDistributor()));
			ps.setString(2, minReorderDTO.getProduct());
			
			rs = ps.executeQuery();
			if (rs.next()) {
				sql =
					new StringBuffer(PropertyReader.getValue("MinReorder_update_mrl"));
				ps = con.prepareStatement(sql.toString());
				ps.setInt(1, minReorderDTO.getMinreorder());
				ps.setString(2, userId);
				ps.setInt(3, Integer.parseInt(minReorderDTO.getDistributor()));
				ps.setString(4, minReorderDTO.getProduct());
				rs = ps.executeQuery();
			} else {
				sql =
					new StringBuffer(PropertyReader.getValue("MinReorder_insert_mrl"));
				ps = con.prepareStatement(sql.toString());
				ps.setInt(1, Integer.parseInt(minReorderDTO.getDistributor()));
				ps.setString(2, minReorderDTO.getProduct());
				ps.setInt(3, minReorderDTO.getMinreorder());
				ps.setString(4, userId);
				ps.setString(5, userId);
				ps.setString(6,minReorderDTO.getBusiness_catg());
				rs = ps.executeQuery();
			}
		} catch (SQLException e) {
			e.printStackTrace();
			logger.error(
				"SQL Exception occured while inserting."
					+ "Exception Message: "
					+ e.getMessage());
			throw new DAOException("SQLException: " + e.getMessage(), e);
		} catch (Exception e) {
			e.printStackTrace();
			logger.error(
				"Exception occured while inserting."
					+ "Exception Message: "
					+ e.getMessage());
			throw new DAOException("Exception: " + e.getMessage(), e);
		} finally {
			try {
				if (rs != null)
					rs.close();
				if (ps != null)
					ps.close();
				if (con != null) {
					con.close();
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}


		
	public String findByPrimaryKey(HBOUserBean hboUserBean)
		throws DAOException {
		Connection con = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		StringBuffer sql = new StringBuffer("");
		sql =
			sql.append(
				" SELECT CM.Circle_ID  FROM warehouse_master WM, USER_Master UM ,Circle_Master CM  WHERE ((UM.warehouse_id = WM.warehouse_id)) AND WM.Warehouse_Circle_ID = CM.Circle_ID  ");
		sql = sql.append("and WM.warehouse_id =?");
		String circleId = null;
		try {
			con = DBConnection.getDBConnection();
			ps = con.prepareStatement(sql.toString());
			ps.setString(1, hboUserBean.getWarehouseID());
			rs = ps.executeQuery();
			while (rs.next()) {
				circleId = rs.getString("Circle_ID");
			}
			return circleId;
		} catch (SQLException e) {
			e.printStackTrace();
			logger.error(
				"SQL Exception occured in  findByPrimaryKey(HBOUserBean hboUserBean) ."
					+ "Exception Message: "
					+ e.getMessage());
			throw new DAOException("SQLException: " + e.getMessage(), e);
		} catch (Exception e) {
			e.printStackTrace();
			logger.error(
				"Exception occured in  findByPrimaryKey(HBOUserBean hboUserBean) ."
					+ "Exception Message: "
					+ e.getMessage());
			throw new DAOException("Exception: " + e.getMessage(), e);
		} finally {
			try {
				if (rs != null)
					rs.close();
				if (ps != null)
					ps.close();
				if (con != null) {
					con.close();
				}
			} catch (Exception e) {
			}
		}
	}
	public ArrayList getWarehouse(String circle, HBOUserBean hboUserBean, String condition) throws DAOException {
		// TODO Auto-generated method stub
		return null;
	}

	public int getProjectionQty(ArrayList params) throws DAOException{
		PreparedStatement ps = null;
		Connection db2conn = null;
		ResultSet rs = null;
		int projectedQty = 0;
		try{
			db2conn = DBConnectionManager.getDBConnection();
			String mth = ((String)params.get(2)).substring(0,((String)params.get(2)).lastIndexOf("@"));
			String year = ((String)params.get(2)).substring(((String)params.get(2))
					.lastIndexOf("@")+1,((String)params.get(2)).length());
			StringBuffer sql = new StringBuffer(DBQueries.projectedQty);
			ps = db2conn.prepareStatement(sql.toString());
			ps.setInt(1, Integer.parseInt(((String)params.get(1))));
			ps.setString(2, (String)params.get(0));
			ps.setString(3, mth);
			ps.setString(4, year);
			rs = ps.executeQuery();
			while(rs.next()){
				projectedQty = rs.getInt("PROJECTION_QTY");
			}
			return projectedQty;
		}catch(SQLException e){
			e.printStackTrace();
			logger.error("SQL Exception occured while getting projected quantity. " +
					"Exception message: "+e.getMessage());
		}
		catch(Exception e){
			e.printStackTrace();
			logger.error("Exception occured while getting projected quantity. " +
					"Exception message: "+e.getMessage());
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
		return projectedQty;
	}

	public ArrayList getAccountList(String userIdFrom,String conditionA,String conditionB) throws DAOException {
		PreparedStatement ps = null;
		Connection db2conn = null;
		ResultSet rs = null;
		DistStockDTO distStock=null;
		ArrayList userList=new ArrayList();
		boolean flag=false;
		try{
			db2conn = DBConnectionManager.getDBConnection();
			StringBuffer sql = new StringBuffer(DBQueries.accountList);
			logger.info("query for fetching child::"+DBQueries.accountList);
			logger.info("conditionA::"+conditionA);
			logger.info("userIdFrom::"+userIdFrom);
			logger.info("conditionB::"+conditionB);
			
			
			ps = db2conn.prepareStatement(sql.toString());
			ps.setInt(1, Integer.parseInt(conditionA));
			ps.setInt(2,Integer.parseInt(userIdFrom));
			ps.setInt(3,Integer.parseInt(conditionB));
			ps.setInt(4,Integer.parseInt(userIdFrom));
			rs = ps.executeQuery();
			while(rs.next()){
				distStock=new DistStockDTO();
				if(conditionA.equalsIgnoreCase(HBOConstants.fseAccountLvl)){
					distStock.setOptionText(rs.getString("ACCOUNT_NAME")+"  "+(rs.getString("RETAILER_LAPU")));
					distStock.setOptionValue(rs.getString("ACCOUNT_ID"));
				}
				else{
				distStock.setOptionText(rs.getString("ACCOUNT_NAME")+"  "+(rs.getString("MOBILE_NUMBER")));
				distStock.setOptionValue(rs.getString("ACCOUNT_ID"));
				}
				userList.add(distStock);
				flag = true;
			}
			logger.info("flag::::::"+flag);
			logger.info("ArrayList size in HBO Daoimpl::"+userList.size());
			
		}catch(SQLException e){
			e.printStackTrace();
			logger.error("SQL Exception occured while getting projected quantity. " +
					"Exception message: "+e.getMessage());
		}
		catch(Exception e){
			e.printStackTrace();
			logger.error("Exception occured while getting projected quantity. " +
					"Exception message: "+e.getMessage());
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
		return userList;

	}
}