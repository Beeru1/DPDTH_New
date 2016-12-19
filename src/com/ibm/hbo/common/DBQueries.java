package com.ibm.hbo.common;

import com.ibm.dp.common.Constants;

public final class DBQueries {

	// jha
	//updated shilpa khanna for FSE /Retailer 
	public static final String retailerUserList = "select * from vr_account_details AD, VR_LOGIN_MASTER LM where  LM.LOGIN_ID = AD.ACCOUNT_ID AND  (LM.STATUS='A' OR LM.STATUS is null) and AD.PARENT_ACCOUNT IN ";// jha
	public static final String retailerFromUserList = "select * from vr_account_details AD, VR_LOGIN_MASTER LM where  LM.LOGIN_ID = AD.ACCOUNT_ID and AD.PARENT_ACCOUNT IN ";// jha
	
	public static final String retailerFSE="select * from vr_account_details AD, VR_LOGIN_MASTER LM where LM.LOGIN_ID = AD.ACCOUNT_ID AND  (LM.STATUS='A' OR LM.STATUS is null) and  AD.ACCOUNT_ID=";//rajiv jha
	public static final String businessCategory = "select * from  dp_business_category_master bcm order by bcm.category_code";
	public static final String businessCategorySerially = "select * from  dp_business_category_master bcm where PURCHASE_INTERNALLY='Y' and SERIALLY='Y' order by bcm.category_code";
	public static final String businessCategoryExt = "select * from  dp_business_category_master bcm where PURCHASE_INTERNALLY='N' order by bcm.category_code ";
	public static final String allProductList = "SELECT DP_PRODUCT_MASTER.*,DP_BUSINESS_CATEGORY_MASTER.CATEGORY_NAME " +
												"FROM DP_PRODUCT_MASTER , DP_BUSINESS_CATEGORY_MASTER WHERE " +
												"DP_BUSINESS_CATEGORY_MASTER.PURCHASE_INTERNALLY='N' and " +
												"DP_BUSINESS_CATEGORY_MASTER.CATEGORY_CODE = DP_PRODUCT_MASTER.CATEGORY_CODE WITH UR" ;
	public static final String circle="select * from  VR_CIRCLE_MASTER where 1=1 "; 

	public static final String loggedInUsercircle="select * from  DP_CIRCLE_MASTER "+
												"FROM DP_PRODUCT_MASTER , DP_BUSINESS_CATEGORY_MASTER WHERE " +
												"DP_BUSINESS_CATEGORY_MASTER.PURCHASE_INTERNALLY='N' and " +
												"DP_BUSINESS_CATEGORY_MASTER.CATEGORY_CODE = DP_PRODUCT_MASTER.CATEGORY_CODE " ;

	public static final String getChangeCategory=" SELECT DP_PRODUCT_MASTER.*,DP_BUSINESS_CATEGORY_MASTER.CATEGORY_NAME FROM DP_PRODUCT_MASTER, DP_BUSINESS_CATEGORY_MASTER WHERE DP_BUSINESS_CATEGORY_MASTER.CATEGORY_CODE = DP_PRODUCT_MASTER.CATEGORY_CODE and DP_BUSINESS_CATEGORY_MASTER.CATEGORY_CODE =? and (DP_PRODUCT_MASTER.CIRCLE_ID=0 or DP_PRODUCT_MASTER.CIRCLE_ID=?) WITH UR";
	public static final String getRequisitionCategory=" SELECT DP_PRODUCT_MASTER.*,DP_BUSINESS_CATEGORY_MASTER.CATEGORY_NAME FROM DP_PRODUCT_MASTER, DP_BUSINESS_CATEGORY_MASTER WHERE DP_BUSINESS_CATEGORY_MASTER.CATEGORY_CODE = DP_PRODUCT_MASTER.CATEGORY_CODE and DP_BUSINESS_CATEGORY_MASTER.CATEGORY_CODE =? and DP_PRODUCT_MASTER.circle_id=? or DP_PRODUCT_MASTER.circle_id=0 WITH UR";
	public static final String account_ND = "SELECT A.* FROM VR_ACCOUNT_DETAILS A ,VR_LOGIN_MASTER B , " +
			" VR_GROUP_ROLE_MAPPING C,VR_ROLE_MASTER D WHERE A.ACCOUNT_ID = B.LOGIN_ID AND B.GROUP_ID = C.GROUP_ID " +
			" AND C.ROLE_ID=D.ROLE_ID and D.ROLE_NAME='ROLE_ND'";	
	public static final String insertRequisition = "INSERT INTO DP_REQUISITION_HEADER(REQUISITION_ID, CREATED_BY,ASSIGNED_WAREHOUSE_ID, " +
			"CREATED_DT, REQUISITION_QTY, " +
			"FOR_MONTH, REQUISITION_PRODUCT_CODE, UPLOADED_QUANTITY) " +
			"VALUES(NEXT VALUE for DP_RH_SEQ,?,?,current timestamp,?,?,?,?) ";
	public static final String simStockInhand ="SELECT SIM_SIH As avlstock  FROM DP_SIM_STOCK_SUMMARY WHERE WAREHOUSE_ID=?";
	
	public static final String listofNDandLD ="select account_name,Account_Id from VR_ACCOUNT_DETAILS where ACCOUNT_ID in(select distinct lm.LOGIN_ID from vr_login_master lm, " +
			" vr_group_role_mapping vgr,vr_role_master vrm,vr_circle_master cm where lm.group_id=vgr.group_id and vgr.role_id=vrm.role_id and vrm.role_name in ('ROLE_LD','ROLE_ND')) " +
			" and (circle_id=? or circle_id=0)";
   // Updation ends here
	public static final String viewRequisitionND = "SELECT A.REQUISITION_ID,TO_CHAR(A.CREATED_DT,'dd/mm/yyyy')CREATED_DT,A.REQUISITION_PRODUCT_CODE,A.ACCOUNT_NAME,A.REQUISITION_QTY,A.PRODUCT_NAME,A.FOR_MONTH,A.INVOICE_NO,TO_CHAR(A.INVOICE_DT,'dd/mm/yyyy')INVOICE_DT,COUNT(A.imei_no) as UPLOADED_QUANTITY, " +
			" COALESCE(A.uploaded_quantity, 0) as SUMUPLOADED_QTY FROM (	SELECT rh.REQUISITION_ID,rh.CREATED_DT,rh.REQUISITION_PRODUCT_CODE,pm.PRODUCT_NAME,ad.ACCOUNT_NAME,rh.REQUISITION_QTY,rh.FOR_MONTH,rp.INVOICE_NO,rp.INVOICE_DT,rp.imei_no,rh.uploaded_quantity " +
			" FROM  DP_REQUISITION_HEADER rh  LEFT OUTER JOIN  DP_REQUISITION_PRODUCTS rp ON  rh.REQUISITION_ID  = rp.REQUISITION_ID   INNER JOIN  VR_ACCOUNT_DETAILS ad  ON  rh.ASSIGNED_WAREHOUSE_ID  = ad.ACCOUNT_ID   INNER JOIN  DP_PRODUCT_MASTER pm " +
			" ON  rh.REQUISITION_PRODUCT_CODE  = pm.PRODUCT_ID WHERE	 rh.ASSIGNED_WAREHOUSE_ID  = ? ) as A	GROUP BY ACCOUNT_NAME, " +
			" FOR_MONTH,REQUISITION_ID,CREATED_DT,REQUISITION_PRODUCT_CODE,PRODUCT_NAME,REQUISITION_QTY,INVOICE_NO,INVOICE_DT,COALESCE(uploaded_quantity, 0) ORDER BY REQUISITION_ID DESC ";

	public static final String viewRequisitionMD = "SELECT REQUISITION_ID,PRODUCT_NAME,TO_CHAR(A.CREATED_DT,'dd/mm/yyyy')CREATED_DT,REQUISITION_PRODUCT_CODE,ACCOUNT_NAME,REQUISITION_QTY,FOR_MONTH,COUNT(imei_no) UPLOADED_QUANTITY,COALESCE(uploaded_quantity, 0) SUMUPLOADED_QTY " +
			" FROM ( SELECT rh.REQUISITION_ID,rh.CREATED_DT,rh.REQUISITION_PRODUCT_CODE,pm.PRODUCT_NAME,ad.ACCOUNT_NAME,rh.REQUISITION_QTY,rh.FOR_MONTH,rp.imei_no,rh.uploaded_quantity	FROM  DP_REQUISITION_HEADER rh  LEFT OUTER JOIN  DP_REQUISITION_PRODUCTS rp " +
			" ON  rh.REQUISITION_ID  = rp.REQUISITION_ID   INNER JOIN  VR_ACCOUNT_DETAILS ad  ON  rh.ASSIGNED_WAREHOUSE_ID  = ad.ACCOUNT_ID   INNER JOIN  DP_PRODUCT_MASTER pm  ON  rh.REQUISITION_PRODUCT_CODE  = pm.PRODUCT_ID " +
			" ) A GROUP BY ACCOUNT_NAME,FOR_MONTH,REQUISITION_ID,PRODUCT_NAME,CREATED_DT,REQUISITION_PRODUCT_CODE,REQUISITION_QTY,COALESCE(uploaded_quantity, 0) ORDER BY REQUISITION_ID DESC ";

	public static final String assignedStock = "SELECT bh.BATCH_NO,bh.WAREHOUSE_TO,bh.WAREHOUSE_FROM,bh.ASSIGNED_QTY,bh.CREATED_BY, " +
			" bh.CREATED_DT,bh.STATUS, " +
			" bh.UPDATED_DT,bh.UPDATED_BY,bh.PRODUCT_TYPE,bh.PRODUCT_CODE,bh.BUNDLED_FLAG, " +
			" ad.ACCOUNT_NAME FROM DP_BATCH_HEADER bh,VR_ACCOUNT_DETAILS ad ";

	public static final String availProdQtyNonPair="select UNBUNDLED_SIH from DP_WAREHOUSE_STOCK where WAREHOUSE_ID =? and PRODUCT_CODE=? and CIRCLE_ID is null"; 
	public static final String availableProdQtyPair="select BUNDLED_SIH from DP_WAREHOUSE_STOCK where WAREHOUSE_ID =? and PRODUCT_CODE=? and CIRCLE_ID is null";
	
	


	public static final String assignedStockAll = " WHERE WAREHOUSE_FROM =? AND bh.WAREHOUSE_TO = ad.ACCOUNT_ID Order By bh.CREATED_DT desc ";

	public static final String assignedStockTransit = " WHERE WAREHOUSE_TO = ? AND STATUS = 'I' AND bh.WAREHOUSE_TO = ad.ACCOUNT_ID and PRODUCT_TYPE='H'";
	public static final String assignedStockAR = " WHERE WAREHOUSE_TO = ? AND STATUS in('A','R') AND bh.WAREHOUSE_TO = ad.ACCOUNT_ID and PRODUCT_TYPE='H'";

	public static final String assignedSimBatch="SELECT BH.batch_no,WM.Account_Name,BH.assigned_qty,BH.created_dt created_dt,BH.status "+ 
												"FROM dp_batch_header BH, VR_Account_Details WM " +	
												"WHERE  (BH.Warehouse_To = WM.Account_ID) and  ((BH.Warehouse_From =?) AND (BH.PRODUCT_TYPE = 'S')) Order By Created_DT desc ";
	public static final String assignedSimBatchDetails="SELECT ss.msidn_no, bd.imei_sim_no FROM dp_sim_stock ss, dp_batch_details bd"+
												" WHERE ((ss.sim_no = bd.imei_sim_no) and bd.batch_no = ?)";



	public static final String unverifiedSimStockList =" SELECT batch_no,assigned_qty,created_dt ,updated_dt ,status "+
														" FROM DP_batch_header "+ 
														" WHERE ((warehouse_to = ?) AND (PRODUCT_TYPE = 'S')AND (dp_batch_header.status = 'I')) Order By Created_DT desc";

	public static final String verifiedSimStockList="SELECT batch_no,assigned_qty,created_dt ,updated_dt,status "+
													" FROM DP_batch_header "+
													" WHERE ((warehouse_to = ?) AND (PRODUCT_TYPE = 'S')AND (dp_batch_header.status in ('A','R'))) Order By Created_DT desc ";

	public static final String batchDetailsList = "SELECT rp.invoice_no,rp.invoice_dt,rp.RECEIVING_DT,rp.ISSUANCE_DT,ps.sim_no,rp.status,rp.last_warehouse_id,ps.msidn_no," +
			"rp.damage_flag,rp.bundled,bd.*,pm.COMPANY_DESC,pm.PRODUCT_NAME FROM  dp_batch_details bd  INNER JOIN  dp_requisition_products rp  ON  bd.imei_sim_no  = rp.imei_no " +
			"LEFT OUTER JOIN  dp_product_sim ps  ON  rp.imei_no  = ps.IMEI_NO    INNER JOIN  dp_requisition_header rh  ON  RP.REQUISITION_ID  = RH.REQUISITION_ID   " +
			"INNER JOIN  dp_product_master pm  ON  rh.REQUISITION_PRODUCT_CODE  = PM.PRODUCT_ID WHERE bd.batch_no  = ? ";
	
	public static final String acceptReject_getCircle = "select CIRCLE_ID from VR_ACCOUNT_DETAILS where ACCOUNT_ID = ?";
	public static final String acceptReject_update = "UPDATE DP_BATCH_HEADER SET STATUS = ?,UPDATED_DT = current timestamp, UPDATED_BY = ? WHERE BATCH_NO = ?";
	public static final String acceptReject_getbundleFlag = "SELECT BUNDLED_FLAG,WAREHOUSE_FROM,PRODUCT_CODE FROM DP_BATCH_HEADER WHERE BATCH_NO = ? and product_type = 'H' ";
	public static final String acceptReject_assignedQty = "SELECT ASSIGNED_QTY FROM DP_BATCH_HEADER bh WHERE bh.BATCH_NO = ?";
	public static final String acceptReject_recordCheck = "select count(*) cnt from DP_WAREHOUSE_STOCK ws WHERE ws.WAREHOUSE_ID = ? and PRODUCT_CODE = ? ";
	public static final String acceptReject_updateReqProd = "UPDATE DP_REQUISITION_PRODUCTS SET LAST_WAREHOUSE_ID = ?,UPDATED_BY = ?,UPDATED_DT = current timestamp,STATUS = 'S',RECEIVING_DT=current timestamp WHERE LAST_BATCH_NO =? ";
	public static final String acceptReject_updateWarehouseToStockBundled = "UPDATE DP_WAREHOUSE_STOCK ws SET BUNDLED_SIH = BUNDLED_SIH+? WHERE ws.WAREHOUSE_ID = ? and product_code = ?";
	public static final String acceptReject_insertBundled = "INSERT INTO DP_WAREHOUSE_STOCK(WAREHOUSE_ID, PRODUCT_CODE, BUNDLED_SIH, BUNDLED_SIT, UNBUNDLED_SIH, UNBUNDLED_SIT, MIN_REORDER_LEVEL_QTY, CIRCLE_ID) VALUES(?,?,?, 0, 0, 0, 0, null)";
	public static final String acceptReject_updateWarehouseFromStockBundled = "UPDATE DP_WAREHOUSE_STOCK ws SET BUNDLED_SIT = BUNDLED_SIT -? WHERE ws.WAREHOUSE_ID = ? and product_code= ? ";
	public static final String acceptReject_circleCondition = " and CIRCLE_ID = ?";
	public static final String acceptReject_updateWarehouseToStockUnbundled = "UPDATE DP_WAREHOUSE_STOCK ws SET UNBUNDLED_SIH = UNBUNDLED_SIH+? WHERE ws.WAREHOUSE_ID = ? and product_code= ?";
	public static final String acceptReject_insertUnbundled = "INSERT INTO DP_WAREHOUSE_STOCK(WAREHOUSE_ID, PRODUCT_CODE, BUNDLED_SIH, BUNDLED_SIT, UNBUNDLED_SIH, UNBUNDLED_SIT, MIN_REORDER_LEVEL_QTY, CIRCLE_ID) VALUES (?,?,0,0,?,0,0,null)";
	public static final String acceptReject_updateWarehouseStock = "UPDATE DP_WAREHOUSE_STOCK ws SET UNBUNDLED_SIT = UNBUNDLED_SIT-? WHERE ws.WAREHOUSE_ID = ? and product_code= ? ";
	public static final String acceptReject_updateReqProdReject = "UPDATE DP_REQUISITION_PRODUCTS SET UPDATED_BY = ?,UPDATED_DT = current timestamp,STATUS = 'S' WHERE LAST_BATCH_NO =?";
	public static final String acceptReject_updateWarehouseStockReject = "UPDATE DP_WAREHOUSE_STOCK ws SET BUNDLED_SIH = BUNDLED_SIH + ?,BUNDLED_SIT = BUNDLED_SIT -? WHERE ws.WAREHOUSE_ID = ? and product_code=?";
	public static final String acceptReject_updateWareStockRejectUnbundled = "UPDATE DP_WAREHOUSE_STOCK ws SET UNBUNDLED_SIH = UNBUNDLED_SIH + ?, UNBUNDLED_SIT = UNBUNDLED_SIT-? WHERE ws.WAREHOUSE_ID = ? and product_code=?";
	public static final String acceptReject_circleCondNull = "and CIRCLE_ID is null";
	public static final String circleOwn = "and circle_id = (select ad.circle_id from vr_account_details ad,vr_login_master lm " +
			"where  lm.login_id = ad.account_id and lm.login_name =?)";
	public static final String projectedQty = "select PROJECTION_QTY from DP_CIRCLE_PROJECTION where CIRCLE_ID=? and PRODUCT_CODE=? and MONTH=? and YEAR=?";
	public static final String createProjection = "insert into DP_CIRCLE_PROJECTION(CIRCLE_ID, PRODUCT_CODE,MONTH, YEAR, PROJECTION_QTY, CREATED_BY, CREATED_DT, UPDATED_BY, UPDATE_DT) " +
			" values (?,?,?,?,?,?,current timestamp,?,current timestamp)";
	public static final String updateProjection = " update DP_CIRCLE_PROJECTION set PROJECTION_QTY=?, UPDATED_BY=?,UPDATE_DT=current timestamp where CIRCLE_ID=?  and PRODUCT_CODE=? and MONTH=? and YEAR=? ";
	public static final String projectionBulkUpload = "INSERT INTO DP_BULK_PROJECTION_UPLOAD (FILE_ID,FILE_NAME,UPLOADED_DT,UPLOADED_BY,CIRCLE_ID,MONTH,YEAR,PROCESSED_STATUS, " +
			"PROCESSED_DT,FILE_PATH,STATUS) " +
			"VALUES(NEXT VALUE for BULK_UPLOAD_FILE_SEQ,?,current timestamp,?,?,?,?,'D',current timestamp,?,'D')";
	//public static final String markDamageDirect= "select a.imei_no,a.damage_flag,b.sim_no,a.BUNDLED,a.STATUS,a.LAST_WAREHOUSE_ID from " +
	//		" DP_REQUISITION_PRODUCTS  a, DP_PRODUCT_SIM b where a.IMEI_NO= ? and a.IMEI_NO=b.IMEI_NO " +
	//		" and (a.STATUS <> 'I' and  a.LAST_WAREHOUSE_ID = ?)";
	public static final String markDamageDirect= "select a.imei_no,a.damage_flag,b.sim_no,a.BUNDLED,a.STATUS,a.LAST_WAREHOUSE_ID from " +
			"DP_REQUISITION_PRODUCTS  a left outer join DP_PRODUCT_SIM b on a.IMEI_NO=b.IMEI_NO,DP_REQUISITION_HEADER rh where a.IMEI_NO= ? " +
			"and (a.STATUS <>'I' and  a.LAST_WAREHOUSE_ID = ?) and a.requisition_id=rh.requisition_id and rh.REQUISITION_PRODUCT_CODE=?";
	public static final String markDamageImei = "select a.imei_no,a.damage_flag,b.sim_no,a.BUNDLED,a.STATUS,a.LAST_WAREHOUSE_ID	" +
			" from DP_REQUISITION_PRODUCTS  a, DP_PRODUCT_SIM b where a.INVOICE_NO=? and a.IMEI_NO=b.IMEI_NO(+) ";
													
	public static final String getUploadedSimFileList="SELECT bulkupload.FILE_ID,bulkupload.FILE_NAME,usermaster.Account_Name as LOGINID,TO_CHAR(UPLOADED_DT,'dd/mm/yyyy') as UPLOADEDDATE "+ 
													",bulkupload.PROCESSED_STATUS,TO_CHAR(PROCESSED_DT,'dd/mm/yyyy') AS PROCESSEDDATE , FILE_PATH "+ 
													"FROM DP_BULK_UPLOAD_FILE bulkupload,VR_Account_Details usermaster "+  
													"WHERE bulkupload.CIRCLE_ID=? AND bulkupload.UPLOADED_BY = ? and bulkupload.UPLOADED_BY = usermaster.Account_ID and bulkupload.file_type = ? "+  
													" and date(bulkupload.UPLOADED_DT) = ";
	/*public static final String accountList="select account_details2.* from VR_ACCOUNT_DETAILS account_details1, VR_ACCOUNT_DETAILS account_details2" +
						" where account_details1.account_level = ? and account_details1.account_id = ? and " +
						" account_details2.parent_account = account_details1.account_id " +
						" union " +
						" select account_details1.* from VR_ACCOUNT_DETAILS account_details1, VR_ACCOUNT_DETAILS account_details2" +
						" where account_details2.account_level = ? and account_details2.account_id = ?" +
						" and account_details2.parent_account = account_details1.account_id";*/
	
	public static final String accountList ="select account_details2.*  "
											+" from VR_ACCOUNT_DETAILS account_details1, VR_ACCOUNT_DETAILS account_details2 ,VR_LOGIN_MASTER login "
											+" where account_details1.account_level = ? and account_details1.account_id = ? and   "
											+" account_details2.parent_account = account_details1.account_id  and account_details2.ACCOUNT_ID=login.LOGIN_ID "
											+" and login.STATUS='A' "
											+" union   "
											+" select account_details1.* " 
											+" from VR_ACCOUNT_DETAILS account_details1, VR_ACCOUNT_DETAILS account_details2 ,VR_LOGIN_MASTER login "
											+" where account_details2.account_level = ? and account_details2.account_id = ?  "
											+" and account_details2.parent_account = account_details1.account_id and account_details1.ACCOUNT_ID=login.LOGIN_ID "
											+" and login.STATUS='A'  with ur ";
											
	
	public static final String getUploadedHandsetFileList="SELECT bulkupload.FILE_ID,bulkupload.FILE_NAME,usermaster.Account_Name as LOGINID ,TO_CHAR(UPLOADED_DT,'dd/mm/yyyy') as UPLOADEDDATE,bulkupload.PROCESSED_STATUS "+
														",TO_CHAR(PROCESSED_DT,'dd/mm/yyyy') AS PROCESSEDDATE , FILE_PATH FROM DP_BULK_UPLOAD_FILE bulkupload,VR_Account_Details usermaster "+ 
														"WHERE bulkupload.UPLOADED_BY = ? and bulkupload.UPLOADED_BY = usermaster.Account_ID and "+ 
														"bulkupload.file_type = ? and date(bulkupload.UPLOADED_DT) =";
	public static final String BulkFileSeq="select VARCHAR(RTRIM(CHAR(NEXTVAL for DP_BULK_UPLOAD_FILE_SEQ))) as lfileId from  SYSIBM.SYSDUMMY1";
	
	public static final String InsertBulkUploadFile="INSERT INTO DP_BULK_UPLOAD_FILE (FILE_ID,FILE_NAME,UPLOADED_DT,UPLOADED_BY,CIRCLE_ID,PROCESSED_STATUS," +
											" PROCESSED_DT,FILE_TYPE,FILE_PATH)VALUES(?,?,current timestamp,?,?,?,current timestamp,?,?)";
	public static final String listOfBundledProd ="SELECT   DPM.*  FROM DP_PRODUCT_MASTER DPM WHERE DPM.STOCK_TYPE='P'";
	public static final String HLRBulkUploadFile="INSERT INTO DP_BULK_UPLOAD_FILE (FILE_ID,FILE_NAME,CHANGED_FILE_NAME,UPLOADED_DT,UPLOADED_BY,CIRCLE_ID,PROCESSED_STATUS," +
	" PROCESSED_DT,FILE_TYPE,FILE_PATH)VALUES(?,?,?,current timestamp,?,?,?,current timestamp,?,?)";
	

	public static final String avlSimStockInhandforACircle = simStockInhand + " and Circle_ID=? ";
	public static final String avlUnbundledStockInhand ="SELECT WAR.UNBUNDLED_SIH FROM DP_WAREHOUSE_STOCK WAR WHERE WAR.WAREHOUSE_ID =? AND WAR.PRODUCT_CODE =?  AND WAR.CIRCLE_ID IS NULL ";
	public static final String viewBundleStock="SELECT DBH.REQUEST_ID,DBH.BUNDLED_QTY,DPM.PRODUCT_NAME MODEL_CODE,DPM.PRODUCT_NAME MODEL_NAME,VCM.CIRCLE_NAME,DBH.BUNDLED_DT "+ 
	"FROM DP_Bundle_Header  DBH , DP_PRODUCT_MASTER DPM , "+
	"VR_CIRCLE_MASTER VCM WHERE DBH.PRODUCT_CODE = DPM.PRODUCT_ID "+ 
	"and DBH.CIRCLE_ID = VCM.CIRCLE_ID  and Bundled_By = ?";
	
	public static final String ProductList="select * from DP_PRODUCT_MASTER where 1=1 ";
	
	public static final String ProductListSwap="select * from DP_PRODUCT_MASTER where PRODUCT_TYPE='0'";
	//	Updated by shilpa khanna for FSE/Retalor inactive
	public static final String UserList="select * from VR_ACCOUNT_DETAILS AD,VR_LOGIN_MASTER LM where LM.LOGIN_ID = AD.ACCOUNT_ID AND  (LM.STATUS='A' OR LM.STATUS is null) ";
    //Ends here
	public static final String NDBundledSIH="select BUNDLED_SIH from DP_WAREHOUSE_STOCK where WAREHOUSE_ID =? and PRODUCT_CODE=? and CIRCLE_ID= ? ";
	
	
	public static final String simRcStockDist="update dp_stock_inventory ";

	
	public static final String bundledSIH="select BUNDLED_SIH from DP_WAREHOUSE_STOCK where WAREHOUSE_ID =? and PRODUCT_CODE=? and CIRCLE_ID is null ";
	public static final String UnbundledSIH="select UNBUNDLED_SIH from DP_WAREHOUSE_STOCK where WAREHOUSE_ID =? and PRODUCT_CODE=? and CIRCLE_ID is null ";
	public static final String simRcStock=" select count(*) from RC_SUK_MASTER where DISTRIBUTOR_ID=? and PRODUCT_ID=? ";
	public static final String checkSerialNo = "SELECT * FROM DP_STOCK_INVENTORY WHERE SERIAL_NO = ? AND DISTRIBUTOR_ID = ? and FSE_ID is null";
	public static final String markDamagedProduct = "update DP_STOCK_INVENTORY set MARK_DAMAGED = 'Y',DAMAGE_REMARKS = ?,DAMAGED_BY = ?,DAMAGE_COST = ? where serial_no = ? and DISTRIBUTOR_ID = ? and product_id=?";
	public static final String markDamagedHandsetInv = "update DP_STOCK_INVENTORY set MARK_DAMAGED = 'Y',DAMAGED_BY = ? where serial_no = ? and DISTRIBUTOR_ID = ? and product_id=?";
	
	/*
	 * Query to be used in production 
	 * */
	
	public static final String updateStockDamaged = "update DIST_STOCK_SUMMARY set CLOSINGBALANCE = CLOSINGBALANCE-1,DAMAGED_STOCK=DAMAGED_STOCK+1 where prod_id = (select product_id from DP_STOCK_INVENTORY where serial_no= ? and DISTRIBUTOR_ID=? and FSE_ID is null and product_id=? ) and dist_id=? and " +
			" CURR_DATE =(Select max(Curr_Date) from DIST_STOCK_Summary where DIST_ID =? and PROD_ID=?)";
	/*public static final String updateStockDamaged = "update DIST_STOCK_SUMMARY set CLOSINGBALANCE = CLOSINGBALANCE-1 where prod_id = (select product_id from DP_STOCK_INVENTORY where serial_no= ? and DISTRIBUTOR_ID=? and FSE_ID is null and product_id=? ) and dist_id=? and " +
	" CURR_DATE =(Select max(Curr_Date) from DIST_STOCK_Summary where DIST_ID =? and PROD_ID=?)";*/
	public static final String damageFlagReqProducts = "update DP_REQUISITION_PRODUCTS set damage_flag='Y' where IMEI_NO=? and LAST_WAREHOUSE_ID=?";
	public static final String damageSelectProdCode =  "select REQUISITION_PRODUCT_CODE,bundled  from "
	+ " DP_REQUISITION_HEADER,DP_REQUISITION_PRODUCTS where "
	+ " DP_REQUISITION_HEADER.REQUISITION_ID=DP_REQUISITION_PRODUCTS.REQUISITION_ID and IMEI_NO=?";
	public static final String damageUpdateStockUnbundled = "update DP_WAREHOUSE_STOCK set unbundled_SIH=unbundled_SIH-1 where warehouse_id=?  and product_code=? and circle_id is null ";
	public static final String damageUpdateStockBundled = "update dp_warehouse_stock set bundled_SIH=bundled_SIH-1 where warehouse_id=?  and product_code=? ";
	public static final String damageCircleNullCond = " and circle_id is null";
	public static final String damageGetCircle = "select circle_id from dp_sim_stock where sim_no=?";
	public static final String damageCircleCond = "and circle_id =? ";
	public static final String checkDamagedFlag = "select MARK_DAMAGED from DP_STOCK_INVENTORY where SERIAL_NO = ?";
	public static final String categoryList="select * from DP_BUSINESS_CATEGORY_MASTER where category_code=? ";
	public static final String selectCategoryList="select * from DP_BUSINESS_CATEGORY_MASTER where category_code=? ";
	
	public static final String selectSerialnos="select min(A.SERIAL_NO)ST_SERIAL_NO,max(A.SERIAL_NO)END_SERIAL_NO " +
			" from(select SERIAL_NO from DP_STOCK_INVENTORY where MARK_DAMAGED='N' ";
	
	public static final String selectAvailableSerialNos = " select SERIAL_NO from DP_STOCK_INVENTORY where MARK_DAMAGED='N' ";
	
	public static final String  viewBundleStockDetails ="select ps.IMEI_NO,ps.SIM_NO,ps.MSIDN_NO,rp.DAMAGE_FLAG,rp.STATUS,rp.LAST_WAREHOUSE_ID "+
														" from dp_product_sim ps,DP_REQUISITION_PRODUCTS rp where Request_Id = ? "+				
														" and rp.IMEI_NO=ps.IMEI_NO(+) ";
	public static final String assignStock="";
	
	public static final String getRoles ="select gd.group_name,alm.ACC_LEVEL, gd1.group_name next_level " +
										 "from vr_login_master lm,vr_group_details gd,VR_ACCOUNT_DETAILS ad," +
										 "	VR_ACCOUNT_LEVEL_MASTER alm, vr_group_details gd1   " +
										 " where lm.group_id=gd.GROUP_ID and lm.LOGIN_ID = ad.ACCOUNT_ID "+
										 " and ad.ACCOUNT_LEVEL = alm.LEVEL_ID  and lm.LOGIN_ID in(?,?)" +
										 "and gd1.group_id= gd.group_id+1 "+
										 "and alm.HIERARCHY_ID = 1 order by ACC_LEVEL with ur";
	
	//	Updated by shilpa khanna for FSE/Retalor inactive
	
	public static final String DistList="select * from VR_ACCOUNT_DETAILS AD,VR_LOGIN_MASTER LM where LM.LOGIN_ID = AD.ACCOUNT_ID AND  (LM.STATUS='A' OR LM.STATUS is null) ";
	
	//Ends here
	public static final String  GetStock="select CLOSINGBALANCE from DIST_STOCK_SUMMARY where DIST_ID=? and " +
										" PROD_ID=? and CURR_DATE=current date";
    public static final String  GetReturnStock="select count(*) STOCK from DP_STOCK_INVENTORY where DISTRIBUTOR_ID=?" +
												" and RETAILER_ID=? and PRODUCT_ID=? with ur";
	
	public static final String  GetLevel=" select (select (acc_level)a from VR_ACCOUNT_LEVEL_MASTER alm,VR_ACCOUNT_DETAILS ad " +
									" where alm.LEVEL_ID = ad.ACCOUNT_LEVEL and ad.ACCOUNT_ID in (?) ) " +
									" - (select (acc_level)b from VR_ACCOUNT_LEVEL_MASTER alm,VR_ACCOUNT_DETAILS ad " +
									" where alm.LEVEL_ID = ad.ACCOUNT_LEVEL and ad.ACCOUNT_ID=? )from sysibm.SYSDUMMY1 ";

	public static final String getImeiNo = "select IMEI_NO from DP_PRODUCT_SIM where SIM_NO=? ";
	public static final String callBundleProc = "{call PROC_DP_BUNDLE_PRODSTOCK_NEW(?,?,?,?,?,?,?)}";
	public static final String viewBundleBatchDetails = "select ps.IMEI_NO,ps.SIM_NO,ps.MSIDN_NO,rp.DAMAGE_FLAG,rp.STATUS,rp.LAST_WAREHOUSE_ID "+
														" from dp_product_sim ps left outer join DP_REQUISITION_PRODUCTS rp on "+ 
														" rp.IMEI_NO=ps.IMEI_NO where ps.request_id = ?";
	
	// Query for damaged stock
	public static final String viewDistStockSumm = "select VAD.ACCOUNT_NAME,DPM.PRODUCT_NAME, "+
											" case DSS.curr_date when current date then DSS.OPENINGBALANCE else DSS.CLOSINGBALANCE end OPENINGBALANCE , "+   
											" case DSS.curr_date when current date then DSS.RECEIPT else 0 end RECEIPT , "+    
											" case DSS.curr_date when current date then DSS.SALES else 0 end SALES , " +
											" case DSS.curr_date when current date then DAMAGED_STOCK else 0 end DAMAGED_STOCK ,"+
											" case DSS.curr_date when current date then RETURNN else 0 end RETURN , "+        
											" DSS.CLOSINGBALANCE from Dist_Stock_Summary DSS,VR_ACCOUNT_DETAILS VAD,DP_Product_Master DPM "+
											" where DSS.DIST_ID = VAD.Account_ID and DSS.PROD_ID = DPM.Product_ID and (VAD.ROOT_LEVEL_ID = ? OR DSS.DIST_ID=?) " +
											" and CURR_DATE  = (Select max(Curr_Date) from DIST_STOCK_SUMMARY  DSSINNER "+
											" Where DSSINNER.DIST_ID = DSS.DIST_ID and DSSINNER.PROD_ID = DSS.PROD_ID) and account_level <= 7 "+
											" order by Account_level ";
	
	/*public static final String viewDistStockSumm = "select VAD.ACCOUNT_NAME,DPM.PRODUCT_NAME, "+
	" case DSS.curr_date when current date then DSS.OPENINGBALANCE else DSS.CLOSINGBALANCE end OPENINGBALANCE , "+   
	" case DSS.curr_date when current date then DSS.RECEIPT else 0 end RECEIPT , "+    
	" case DSS.curr_date when current date then DSS.SALES else 0 end SALES , " +
	" case DSS.curr_date when current date then RETURNN else 0 end RETURN , "+        
	" DSS.CLOSINGBALANCE from Dist_Stock_Summary DSS,VR_ACCOUNT_DETAILS VAD,DP_Product_Master DPM "+
	" where DSS.DIST_ID = VAD.Account_ID and DSS.PROD_ID = DPM.Product_ID and (VAD.ROOT_LEVEL_ID = ? OR DSS.DIST_ID=?) " +
	" and CURR_DATE  = (Select max(Curr_Date) from DIST_STOCK_SUMMARY  DSSINNER "+
	" Where DSSINNER.DIST_ID = DSS.DIST_ID and DSSINNER.PROD_ID = DSS.PROD_ID) and account_level <= 7 "+
	" order by Account_level ";*/
		
		/*"select DSS.*,DPM.PRODUCT_NAME, VAD.CONTACT_NAME from Dist_Stock_Summary DSS,VR_ACCOUNT_DETAILS VAD,DP_Product_Master DPM " +
													" where DSS.DIST_ID = VAD.Account_ID and DSS.PROD_ID = DPM.Product_ID "+
													" and CURR_DATE = current date "+
													" and (DSS.DIST_ID=? or DSS.DIST_ID  in (select account_id from VR_ACCOUNT_DETAILS where ROOT_LEVEL_ID =?)) " +
													" order by PROD_ID,dist_id";*/

	public static final String GetUpdatedNos="select serial_no from dp_stock_inventory where serial_no>=? " +
			" and serial_no<= ? and product_id=? ";
	
	public static final String simRcRetStock="update DP_STOCK_INVENTORY set ";
	
	public static final String closingBalance="Select CLOSINGBALANCE  from DIST_STOCK_SUMMARY where DIST_ID =? " +
			" and PROD_ID=? and CURR_DATE =(Select max(Curr_Date) from DIST_STOCK_Summary where DIST_ID =?" +
			" and PROD_ID=?)";
	
	// Query for damagedstock
	//public static final String insertClosingBal="insert INTO DIST_STOCK_SUMMARY  Values(current date,?,?,0,0,0,?,?,0)";
     
	public static final String insertClosingBal="insert INTO DIST_STOCK_SUMMARY(CURR_DATE,DIST_ID,PROD_ID,RECEIPT,RETURNN,SALES,OPENINGBALANCE,CLOSINGBALANCE)  Values(current date,?,?,0,0,0,?,?)";
	
	public static final String distStockSummarySales="Update DIST_STOCK_SUMMARY Set SALES = SALES+ ? ,CLOSINGBALANCE = CASE WHEN (CLOSINGBALANCE >= ?) THEN CLOSINGBALANCE - ? ELSE 0 END  Where dist_id=? and Prod_ID =? and Curr_date = current date ";
	
	public static final String distStockSummary="Update DIST_STOCK_SUMMARY Set RETURNN = RETURNN+ ? ,CLOSINGBALANCE = CASE WHEN (CLOSINGBALANCE >= ?) THEN CLOSINGBALANCE - ? ELSE 0 END  Where dist_id=? and Prod_ID =? and Curr_date = current date ";
	
	//public static final String retdistStockTo="Update DIST_STOCK_SUMMARY  Set Receipt = Receipt+ ? ," +
	//" CLOSINGBALANCE = CLOSINGBALANCE +? Where dist_id=? and Prod_ID =? and Curr_date =current date";
	public static final String retdistStockTo="Update DIST_STOCK_SUMMARY  Set Receipt = Receipt+ ? ," +
	" CLOSINGBALANCE = CLOSINGBALANCE +? Where dist_id=? and Prod_ID =? and Curr_date =current date";
	
	
	//public static final String distStockSummary="Update DIST_STOCK_SUMMARY Set Sales = Sales+ ? ,CLOSINGBALANCE = CLOSINGBALANCE -? Where dist_id=? and Prod_ID =? and Curr_date = current date ";
	
	public static final String insertReceiptBal="insert INTO DIST_STOCK_SUMMARY(CURR_DATE,DIST_ID,PROD_ID,RECEIPT,RETURNN,SALES,OPENINGBALANCE,CLOSINGBALANCE)  Values(current date,?,?,?,0,0,0,?)";
	
	public static final String balance="select CLOSINGBALANCE from  DIST_STOCK_SUMMARY Where dist_id=? and Prod_ID =? and CURR_DATE = current date ";
	
	public static final String distStockTo= "Update DIST_STOCK_SUMMARY  Set Receipt = Receipt+ ? ," +
			" CLOSINGBALANCE = CLOSINGBALANCE +? Where dist_id=? and Prod_ID =? and Curr_date =current date";
	
	//public static final String retdistStockTo="Update DIST_STOCK_SUMMARY  Set RETURNN = RETURNN+ ? ," +
		//	" CLOSINGBALANCE = CLOSINGBALANCE +? Where dist_id=? and Prod_ID =? and Curr_date =current date";
	
	//public static final String insertReceiptBal="insert INTO DIST_STOCK_SUMMARY  Values(current date,?,?,?,0,0,0,?)";
	public static final String uploadStatus = "select Status from DP_Bulk_Upload_File where File_ID = ?";
	public static final String uploadStatusDetails = "select Status_Details from DP_Bulk_Upload_File where File_ID = ?";
	public static final String checkCurrentDateRecord = "select * from DIST_STOCK_SUMMARY where dist_id=? and prod_id=? and curr_date=current date";
	public static final String selectMaxDateClosingBalance = "select * from DIST_STOCK_SUMMARY where dist_id=? and prod_id=? and curr_date = (select max(curr_date) from DIST_STOCK_SUMMARY where dist_id=? and prod_id=?)";
	public static final String selectAccountDetails = "select alm.* from vr_account_details vad,VR_ACCOUNT_LEVEL_MASTER alm " +
			"where vad.ACCOUNT_LEVEL = alm.LEVEL_ID and vad.account_id = ? with ur";
	public static final String markDamageDirectAD = "select a.imei_no,a.damage_flag,b.sim_no,a.BUNDLED,a.STATUS,a.LAST_WAREHOUSE_ID from " +
			"DP_REQUISITION_PRODUCTS  a left outer join DP_PRODUCT_SIM b on a.IMEI_NO=b.IMEI_NO,DP_REQUISITION_HEADER rh,DP_STOCK_INVENTORY si " +
			"where a.IMEI_NO=? and (a.STATUS <>'I' and  a.LAST_WAREHOUSE_ID = ?) and a.requisition_id=rh.requisition_id " +
			"and rh.REQUISITION_PRODUCT_CODE=? and a.IMEI_NO=si.SERIAL_NO and si.FSE_ID is null ";
	public static final String listOfDamagedProd = "select stock.*,product.PRODUCT_NAME from DP_STOCK_INVENTORY stock,DP_PRODUCT_MASTER product where MARK_DAMAGED='Y' and DISTRIBUTOR_ID=? and DAMAGED_BY=? and stock.PRODUCT_ID=product.PRODUCT_ID with ur";
	
	public static final String viewProdStockSumm="select VAD.ACCOUNT_NAME,DPM.PRODUCT_NAME,DSS.PROD_ID, case DSS.curr_date when current date then DSS.OPENINGBALANCE else DSS.CLOSINGBALANCE end OPENINGBALANCE , " +
			" case DSS.curr_date when current date then DSS.RECEIPT else 0 end RECEIPT ,case DSS.curr_date when current date then DSS.SALES else 0 end SALES , " +
			" case DSS.curr_date when current date then RETURNN else 0 end RETURN , VAD.ACCOUNT_ID,VAD.PARENT_ACCOUNT, " +
			" VALM.LEVEL_NAME,DSS.CLOSINGBALANCE from Dist_Stock_Summary DSS,VR_ACCOUNT_DETAILS VAD,DP_Product_Master DPM, " +
			" VR_ACCOUNT_LEVEL_MASTER VALM  where DSS.DIST_ID = VAD.Account_ID and DSS.PROD_ID = DPM.Product_ID and (VAD.ROOT_LEVEL_ID = ? OR DSS.DIST_ID=?) " +
			" and CURR_DATE  = (Select max(Curr_Date) from DIST_STOCK_SUMMARY  DSSINNER Where DSSINNER.DIST_ID = DSS.DIST_ID and DSSINNER.PROD_ID = DSS.PROD_ID) " +
			" and account_level <= 7 and VAD.ACCOUNT_LEVEL=VALM.LEVEL_ID group by product_name,DSS.PROD_ID,ACCOUNT_NAME ,curr_date, " +
			" RECEIPT,CLOSINGBALANCE,SALES,RETURNN,OPENINGBALANCE,LEVEL_NAME,PARENT_ACCOUNT,ACCOUNT_ID with ur";
	
	public static final String viewAssignedInvoiceList = "";
	public static final String insertRetailerTrans = "insert into SALES_TRANSACTION (TRANSACTION_ID,CREATED_BY,CREATED_DATE,TRANSACTION_DATE,PRODUCT_CATEGORY,PRODUCT_ID,ASSIGN_FROM_ID,ASSIGN_TO_ID,QUANTITY,BILL_NO,VAT,RATE,TRANSACTION_STATUS) values(?,?,current date,current date,?,?,?,?,?,?,?,?,?)";

	
	public static final String transactionID="select nextval for SEQ_TRANSACTION_ID from sysibm.SYSDUMMY1";
	
	public static final String GET_DISTRIBUTOR_AVAILABLE_STOCK = "SELECT  COUNT(SERIAL_NO) TOTAL_AVAL_STOCK  FROM DP_STOCK_INVENTORY WHERE MARK_DAMAGED='N' AND FSE_ID IS NULL AND DISTRIBUTOR_ID = ? AND PRODUCT_ID = ? WITH UR";
	
	public static final String GET_FSE_AVAILABLE_STOCK = "SELECT  COUNT(SERIAL_NO) TOTAL_AVAL_STOCK  FROM DP_STOCK_INVENTORY WHERE MARK_DAMAGED='N' AND RETAILER_ID IS NULL AND FSE_ID = ? AND PRODUCT_ID = ? WITH UR ";
	
	public static final String GET_DIST_AVAILABLE_STOCK = "select count(SERIAL_NO)TOTAL_AVAL_STOCK from DP_STOCK_INVENTORY where PRODUCT_ID=? and DISTRIBUTOR_ID=? AND STATUS=5 AND FSE_ID is null AND MARK_DAMAGED='N' with ur";
	
	public static final String GET_DIST_FRESH_AVAILABLE_STOCK = "select SERIAL_NO, (select PRODUCT_NAME from DP_PRODUCT_MASTER where PRODUCT_ID=SI.PRODUCT_ID fetch first 1 row only) from DP_STOCK_INVENTORY SI where SI.PRODUCT_ID=? and SI.DISTRIBUTOR_ID=? AND SI.STATUS=5 AND SI.FSE_ID is null AND MARK_DAMAGED='N' with ur";
// Added by Rajiv jha for Fresh Stock return stock to warehouse
	
	public static final String INSERT_DP_STOCK_INVENTORY_ASSIGNED="INSERT INTO DP_STOCK_INVENTORY_ASSIGNED"
		+" (PRODUCT_ID, SERIAL_NO, MARK_DAMAGED, DISTRIBUTOR_ID, DISTRIBUTOR_PURCHASE_DATE, FSE_ID, FSE_PURCHASE_DATE, RETAILER_ID, RETAILER_PURCHASE_DATE, DAMAGE_REMARKS, DAMAGED_BY, DAMAGE_COST, INV_NO, REMARKS, MSISDN, TRANSACTION_ID, STATUS, ASSIGN_DATE, CUSTOMER_ID )" 
		+" SELECT PRODUCT_ID, SERIAL_NO, MARK_DAMAGED, DISTRIBUTOR_ID, DISTRIBUTOR_PURCHASE_DATE, FSE_ID, FSE_PURCHASE_DATE, RETAILER_ID, RETAILER_PURCHASE_DATE, DAMAGE_REMARKS, DAMAGED_BY, DAMAGE_COST, INV_NO, REMARKS, MSISDN, TRANSACTION_ID, "+ Constants.STB_STATUS_RETRUN_TO_WH +", CURRENT_TIMESTAMP, CUSTOMER_ID"
		+" from DP_STOCK_INVENTORY where DISTRIBUTOR_ID=? and PRODUCT_ID=? AND SERIAL_NO=?";
	
	public static final String INSERT_DP_STOCK_INVENTORY_ASSIGNED_SWAP="INSERT INTO DP_STOCK_INVENTORY_ASSIGNED"
		+" (PRODUCT_ID, SERIAL_NO, MARK_DAMAGED, DISTRIBUTOR_ID, DISTRIBUTOR_PURCHASE_DATE, FSE_ID, FSE_PURCHASE_DATE, RETAILER_ID, RETAILER_PURCHASE_DATE, DAMAGE_REMARKS, DAMAGED_BY, DAMAGE_COST, INV_NO, REMARKS, MSISDN, TRANSACTION_ID, STATUS, ASSIGN_DATE, CUSTOMER_ID )" 
		+" SELECT PRODUCT_ID, SERIAL_NO, MARK_DAMAGED, DISTRIBUTOR_ID, DISTRIBUTOR_PURCHASE_DATE, FSE_ID, FSE_PURCHASE_DATE, RETAILER_ID, RETAILER_PURCHASE_DATE, DAMAGE_REMARKS, DAMAGED_BY, DAMAGE_COST, INV_NO, REMARKS, MSISDN, TRANSACTION_ID, "+ Constants.STB_STATUS_RETRUN_TO_WH_SWAP +", CURRENT_TIMESTAMP, CUSTOMER_ID"
		+" from DP_STOCK_INVENTORY where DISTRIBUTOR_ID=? and PRODUCT_ID=? AND SERIAL_NO=?";

	
	//Updated by Shilpa Khanna 23-09-2011
	public static final String INSERT_DP_REV_DELIVERY_CHALAN_DETAIL="INSERT INTO DP_REV_DELIVERY_CHALAN_DETAIL(DC_NO, DIST_ID, SERIAL_NO, PRODUCT_ID, COLLECTION_ID, DEFECT_ID, COLLECTED_ON, CIRCLE_ID, STATUS)" 
    +" VALUES(?, ?, ?, ?, 0, 0, current_timestamp, ?, 'IDC')";
	
	public static final String INSERT_DP_REV_FRESH_STOCK="INSERT INTO DP_REV_FRESH_STOCK(DISTRIBUTOR_ID, PRODUCT_ID, SERIAL_NO, RETURN_DATE, STATUS, REMARKS, IS_SEC_SALE)" 
    +" VALUES(?, ?, ?, current_timestamp, 'COL', ?, ?)";
	
	//updated by Shilpa for critical changes BFR 14
	//public static final String INSERT_DP_REV_DELIVERY_CHALAN_HDR="INSERT INTO DP_REV_DELIVERY_CHALAN_HDR(DC_NO, CIRCLE_ID, CREATED_BY, CREATED_ON, STATUS, REMARKS, WS_TIME, WS_MESSAGE, BT_REMARKS, BT_COMM_EMP_CODE,DC_TYPE)"
	//+" VALUES(?, ?, ?, current_timestamp, 'CREATED', ?, current_timestamp, '', '', '', 'FRESH')";
	public static final String INSERT_DP_REV_DELIVERY_CHALAN_HDR="INSERT INTO DP_REV_DELIVERY_CHALAN_HDR(DC_NO, CIRCLE_ID, CREATED_BY, CREATED_ON, STATUS, REMARKS, WS_MESSAGE, BT_REMARKS, BT_COMM_EMP_CODE,DC_TYPE,COURIER_AGENCY,DOCKET_NUMBER, WH_CODE)"
		+" VALUES(?, ?, ?, current_timestamp, ?, ?, '', '', '', 'FRESH',?,?,(select WH_CODE from DP_WH_DIST_MAP where DISTID=?))";
		
	
	public static final String DELETE_FROM_STOCK_INVENTORY="DELETE FROM DP_STOCK_INVENTORY WHERE SERIAL_NO=? "
	+"AND PRODUCT_ID=? AND DISTRIBUTOR_ID=?";
	public static final String SR_NO_SEC_SALE_STATUS = "select IS_SEC_SALE from DP_STOCK_INVENTORY where SERIAL_NO=? and PRODUCT_ID=? and DISTRIBUTOR_ID=? with ur ";
}