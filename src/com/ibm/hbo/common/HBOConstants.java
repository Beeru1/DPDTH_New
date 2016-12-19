
package com.ibm.hbo.common;

public final class HBOConstants {
	
	// init create requisition action
	public static final String INIT_CREATE_REQUISITION_ACTION = "/initCreateRequisition";
	public static final String INIT_MARKDAMAGED_ACTION = "/initViewMarkDamaged";
	public static final String INIT_PROJECTION_ACTION = "/initProjectionStock";
	public static final String SUCCESS = "success";
	public static final String DAMAGE_SUCCESS = "damage_success";
	public static final String CSV = ".csv";
	public static final String FAILURE = "failure";
	public static final String INSERT_FAILED = "insert_failed";
	public static final String GET_BULK_DATA = "/bulkDataAction";
	public static final String INSERT_BULK_DATA = "/insertBulkDataAction";
	public static final int BUSINESS_CATEGORY = 1;
	public static final int BUSINESS_CATEGORY_SERIALLY = 15;
	public static final int DAMAGED_PROD_LIST = 20;
	public static final int PRODUCT = 2;
	public static final String NO_RECORD = "NO_RECORD";
	public static final String DAOEXCEPTION_OCCURED = "DAOEXCEPTION_OCCURED";
	public static final String EXCEPTION_OCCURED = "EXCEPTION_OCCURED";
	public static final String CATEGORY_HANDSET = "H";
	public static final String CATEGORY_INT = "I";
	public static final String REQUEST_ATT_CONDITION = "cond";
	public static final String REQUEST_ATT_BATCH = "batch";
	public static final String REQUEST_ATT_REQ_ID = "RequestId";
	public static final String REQUEST_ATT_ID = "Id";
	public static final String REQUEST_ATT_BUNDLED_LIST = "bundledList";
	public static final String BUNDLE_STOCK = "BundleStock";
	public static final String REQ_PARAM_IMEI = "imeiNo";
	public static final String REQ_PARAM_SIM = "simNo";
	public static final String REQ_PARAM_BATCH = "batch";
	public static final String REQ_PARAM_BUNDLED_QTY = "BundledQTY";
	public static final int CIRCLE = 5;
	public static final int WAREHOUSE = 6;
	public static final String CIRCLE_OWN = "circle_own";
	public static final int BUNDLEDPRODUCT =3;
	public static final String PRODUCT_NOT_AVAILABLE = "ProductUnavailable";
	public static final String ALREADY_DAMAGED = "AlreadyDamaged";
	public static final String UPDATE_FAILED = "UpdateFailed";
	public static final String UPDATE_SUCCESS = "UpdateSuccess";
	public static final String SUCCESS_PATH = "successpath";
	public static final String REQUISITION = "requisition";
	public static final String SUCCESS_MESSAGE = "success";
	public static final String ERROR_MESSAGE = "error";
	public static final String ERROR_OCCURED_MESSAGE = "ERROR_OCCURED";
	public static final String po_insert = "PO.insert";
	public static final String INSERT_SUCCESS_MESSAGE = "INSERT_SUCCESS";
	public static final String INSERT_FAILED_MESSAGE = "INSERT_FAILED";
	public static final String uploadSuccessKey = "upload.success";
	public static final String insertSuccessKey = "insert.success";
	public static final String updateSuccessKey = "update.success";
	public static final String insertFailedKey = "insert_failed";
	public static final String errorOccuredKey = "error.occured";
	public static final String UPLOAD_SUCCESS = "UPLOAD_SUCCESS";
	public static final String HBO_CATEGORY = "category";
	public static final String HBO_RequisitionCATEGORY="requisitionCategory";
	public static final String COL_PRODUCT_NAME = "PRODUCT_NAME";
	public static final String COL_MODEL_CODE = "PRODUCT_CODE";
	public static final String COL_PRODUCT_ID = "PRODUCT_ID";
	public static final String COL_CIRCLE_ID = "CIRCLE_ID";
	public static final String COL_BUNDLED_FLAG = "BUNDLED_FLAG";
	public static final String COL_WAREHOUSE_FROM = "WAREHOUSE_FROM";
	public static final String COL_PRODUCT_CODE= "PRODUCT_CODE";
	public static final String COL_ASSIGNED_QTY = "ASSIGNED_QTY";
	public static final String COL_COUNT = "CNT";
	public static final int BUSINESS_CATEGORY_EXT = 7;
	public static final int BUSINESS_CATEGORY_EXT_NEW = 10;
	public static final int DATABASE_ORACLE = 1;
	public static final String STOCK_IN_TRANSIT = "I";
	public static final String ACCEPTED_STOCK = "A";
	public static final String ALL_ASSIGNED_STOCK = "All";
	public static final String ACCEPTED_REJECTED_STOCK = "A/R";
	public static final String UNVERIFIED_BATCH = "unverifiedBatch"; 
	public static final String VERIFIED_BATCH = "verifiedBatch";
	public static final String BATCH_DETAILS_LIST = "batchDetailsList";
	public static final String BUNDLED_DETAILS = "BundledDetails";
	public static final String STOCK_FLAG_ACCEPT = "A";
	public static final String STOCK_FLAG_REJECT = "R";
	public static final String BUNDLE_FLAG = "Y";
	public static final String UNBUNDLE_FLAG = "N";
	public static final String ROLE = "role";
	public static final String ROLE_LOCALDIST = "ROLE_LD";
	public static final String ROLE_MOBILITYADMIN = "ROLE_MA";
	public static final String ROLE_NATIONALDIST = "ROLE_ND";
	public static final String ROLE_SUPER = "ROLE_SS";
	public static final String ROLE_DIST = "ROLE_AD";
	public static final String ROLE_MOBILITY = "mobility";
	public static final String ROLE_CIRCLE = "circle";
	public static final String ROLE_CIRCLEADMIN = "ROLE_CA";
	public static final String ACCOUNT_LEVEL_ZSM = "ZonalSalesManager";
	public static final String ACCOUNT_LEVEL_ZBM = "ZonalBusinessManager";
	public static final String PROJECTION_QTY = "projectionQty";
	public static final String UPLOAD_PROJECTION_PATH = "UPLOAD_PROJECTION_PATH";
	public static final String DIRECT_DAMAGE = "DirectDamage";
	public static final String DAMAGED = "Y";
	public static final String MARK_DAMAGE_IMEI_LIST = "imeiList";
	public static final int BUSINESS_CATEGORY_STOCK = 8;
	public static final int PRODUCT_LIST = 16;
	public static final int PRODUCT_LIST_SWAP = 100;
	public static final int USER_LIST = 9;
	public static final int RETAILER_USER_LIST = 17;// jha
	public static final int RETAILER_FROM_USER_LIST = 27;// Added by SHilpa
	public static final int retailerFSE = 18;// rajiv jha	
	public static final String  fse= "FSE";
	public static final String  retailer= "Retailer";
	public static final String  distributor= "Distributor";
	public static final String WITH_UR = "with ur";
	public static final String IMEI_NO = "imeiNo";
	public static final String SIM_NO = "simNo";
	public static final String SIM_NO_LIST = "simnoList";
	public static final String distAccountLvl="6";
	public static final String fseAccountLvl="7";
	public static final String retAccountLvl ="8";
	
	

}