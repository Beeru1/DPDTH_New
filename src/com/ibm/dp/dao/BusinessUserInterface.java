/**************************************************************************
 * File     : BusinessUserInterface.java
 * Author   : Abhipsa
 * Created  : Sept 10, 2008
 * Modified : Sept 10, 2008
 * Version  : 0.1
 **************************************************************************
 *                               HISTORY
 **************************************************************************
 * V0.1		Sept 10, 2008 	Abhipsa	First Cut.
 * V0.2		Sept 10, 2008 	Abhipsa	First modification
 **************************************************************************
 *
 * Copyright @ 2002 This document has been prepared and written by 
 * IBM Global Services on behalf of Bharti, and is copyright of Bharti
 *
 **************************************************************************/

package com.ibm.dp.dao;

import com.ibm.dp.common.SQLConstants;

public interface BusinessUserInterface {

	public static final String GET_NEXT_BusinessUser_ID= new StringBuffer(500).append("VALUES NEXTVAL FOR ")
	.append(SQLConstants.DISTPORTAL_SCHEMA)
	.append(".SEQ_V_BUSINESS_USER_DATA_ID").append(" WITH UR").toString();

	
	public static String UPDATE_STMT_FOR_Business_User = new StringBuffer(500)
	.append("Update ")
	.append(SQLConstants.DISTPORTAL_SCHEMA)
	.append(".V_BUSINESS_USER_DATA SET USER_NAME = ?,")
	.append("CONTACT_NO = ? ,ADDRESS = ?,UPDATED_BY = ?,UPDATED_DT = current timestamp,CIRCLE_CODE = ?,")
	.append("STATUS = ?,FOS_CHECK_REQ = ? ,FOS_ACTV_CHK = ? ,ALL_SERVICE_CLASS = ? ,INCLUDE_SERVICE = ? ,EXCLUDE_SERVICE = ? ,LOC_ID = ? ,HUB_CODE = ?,IPHONE_ACT_RIGHTS=? WHERE DATA_ID = ?")
	.append(" WITH UR").toString();

		
	public static final String DELETE_Related_MAPPING = new StringBuffer(500)
	.append("UPDATE ")
	.append(SQLConstants.DISTPORTAL_SCHEMA)
	.append(".V_BUSINESS_USER_DATA SET PARENT_ID = ? WHERE PARENT_ID = ? ")
	.append(" WITH UR").toString();
	
	public static final String DELETE_own_MAPPING = new StringBuffer(500)
	.append("UPDATE ")
	.append(SQLConstants.DISTPORTAL_SCHEMA)
	.append(".V_BUSINESS_USER_DATA SET PARENT_ID = ? WHERE DATA_ID = ? ")
	.append(" WITH UR").toString();

		
	public static final String REG_NUMBER_Business_User = new StringBuffer(500)
	.append("UPDATE ")
	.append(SQLConstants.DISTPORTAL_SCHEMA)
	.append(".V_REGISTERED_NUMBER SET  STATUS='D' WHERE BUS_USER_ID=? ")
	.append(" WITH UR").toString();

	public static String SEARCH_STMT_ALL_Business_User = new StringBuffer(500)
	.append("SELECT * from (select a.*,ROW_NUMber() over() rnum FROM (select DATA_ID,CONCAT(CHAR(USER_NAME),CONCAT(' -- ',CHAR(USER_CODE))) AS USER_NAME,USER_CODE,")
	.append("CIRCLE_CODE , ud.STATUS , MOBILE_NO from ")
	.append(SQLConstants.DISTPORTAL_SCHEMA)
	.append(".V_BUSINESS_USER_DATA ud, ")
	.append(SQLConstants.DISTPORTAL_SCHEMA)
	.append(".V_REGISTERED_NUMBER WHERE BUS_USER_ID= DATA_ID AND CIRCLE_CODE = ? AND MASTER_ID = ? ORDER BY UPPER(USER_NAME)) a )b ")
	.append("Where rnum<=? and rnum>=? ")
	.append(" WITH UR").toString();
	
	public static String SEARCH_STMT_ALL_Business_User_Active = new StringBuffer(500)
	.append("SELECT * from (select a.*,ROW_NUMber() over() rnum FROM (select DATA_ID,CONCAT(CHAR(USER_NAME),CONCAT(' -- ',CHAR(USER_CODE))) AS USER_NAME,USER_CODE,")
	.append("CIRCLE_CODE , ud.STATUS , MOBILE_NO from ")
	.append(SQLConstants.DISTPORTAL_SCHEMA)
	.append(".V_BUSINESS_USER_DATA ud, ")
	.append(SQLConstants.DISTPORTAL_SCHEMA)
	.append(".V_REGISTERED_NUMBER WHERE BUS_USER_ID= DATA_ID AND CIRCLE_CODE = ? AND MASTER_ID = ? AND ud.STATUS = ? ORDER BY UPPER(USER_NAME)) a )b ")
	.append("Where rnum<=? and rnum>=? ")
	.append(" WITH UR").toString();
	
	public static String SEARCH_STMT_CEO_ED_Business_User = new StringBuffer(500)
	.append("SELECT * from (select a.*,ROW_NUMber() over() rnum FROM (select DATA_ID,CONCAT(CHAR(USER_NAME),CONCAT(' -- ',CHAR(USER_CODE))) AS USER_NAME,USER_CODE,")
	.append("CIRCLE_CODE , ud.STATUS , MOBILE_NO from ")
	.append(SQLConstants.DISTPORTAL_SCHEMA)
	.append(".V_BUSINESS_USER_DATA ud, ")
	.append(SQLConstants.DISTPORTAL_SCHEMA)
	.append(".V_REGISTERED_NUMBER WHERE BUS_USER_ID= DATA_ID AND MASTER_ID = ? ORDER BY UPPER(USER_NAME)) a )b ")
	.append("Where rnum<=? and rnum>=? ")
	.append(" WITH UR").toString();
	
	public static String SEARCH_STMT_CEO_ED_Business_User_Active = new StringBuffer(500)
	.append("SELECT * from (select a.*,ROW_NUMber() over() rnum FROM (select DATA_ID,CONCAT(CHAR(USER_NAME),CONCAT(' -- ',CHAR(USER_CODE))) AS USER_NAME,USER_CODE,")
	.append("CIRCLE_CODE , ud.STATUS , MOBILE_NO from ")
	.append(SQLConstants.DISTPORTAL_SCHEMA)
	.append(".V_BUSINESS_USER_DATA ud, ")
	.append(SQLConstants.DISTPORTAL_SCHEMA)
	.append(".V_REGISTERED_NUMBER WHERE BUS_USER_ID= DATA_ID AND MASTER_ID = ? AND ud.STATUS = ? ORDER BY UPPER(USER_NAME)) a )b ")
	.append("Where rnum<=? and rnum>=? ")
	.append(" WITH UR").toString();
	
	public static String SEARCH_USER_TYPE_ALL = new StringBuffer(500)
	.append("select MASTER_ID ,USER_TYPE , STATUS ,PARENT_ID ,BASE_LOC ,USER_MAPPING ,CODE_PREFIX FROM ")
	.append(SQLConstants.DISTPORTAL_SCHEMA)
	.append(".V_BUSINESS_USER_MASTER WHERE STATUS = ? ").toString();
	
	public static String GET_BIZ_LIST_WITH_LOC_LIST = new StringBuffer(500)
	.append("select DATA_ID,USER_NAME,USER_CODE,MASTER_ID , STATUS ,PARENT_ID , LOC_ID FROM ")
	.append(SQLConstants.DISTPORTAL_SCHEMA)
	.append(".V_BUSINESS_USER_DATA WHERE LOC_ID IN (<LOC_LIST>) ").toString();
	
	public static String GET_BIZ_LIST_WITH_CIRCLECODE = new StringBuffer(500)
	.append("select DATA_ID,USER_NAME,USER_CODE,MASTER_ID , STATUS ,PARENT_ID , LOC_ID FROM ")
	.append(SQLConstants.DISTPORTAL_SCHEMA)
	.append(".V_BUSINESS_USER_DATA WHERE CIRCLE_CODE = ? ").toString();
	
	public static String GET_BIZ_LIST_FOR_MOVE = new StringBuffer(500)
	.append("select DATA_ID,USER_NAME,USER_CODE,MASTER_ID , STATUS ,PARENT_ID , LOC_ID FROM ")
	.append(SQLConstants.DISTPORTAL_SCHEMA)
	.append(".V_BUSINESS_USER_DATA WHERE LOC_ID = ? AND MASTER_ID = ? AND STATUS=?").toString();
	
	public static String GET_BIZ_LIST_FOR_MOVE_CEO_ED = new StringBuffer(500)
	.append("select DATA_ID,USER_NAME,USER_CODE,MASTER_ID , STATUS ,PARENT_ID , LOC_ID FROM ")
	.append(SQLConstants.DISTPORTAL_SCHEMA)
	.append(".V_BUSINESS_USER_DATA WHERE HUB_CODE = ? AND MASTER_ID = ? AND STATUS=?").toString();
	
	public static String GET_BIZ_LIST_FOR_MOVE_SALESHEAD = new StringBuffer(500)
	.append("select DATA_ID,USER_NAME,USER_CODE,MASTER_ID , STATUS ,PARENT_ID , LOC_ID FROM ")
	.append(SQLConstants.DISTPORTAL_SCHEMA)
	.append(".V_BUSINESS_USER_DATA WHERE CIRCLE_CODE = ? AND MASTER_ID = ? AND STATUS=?").toString();
	
	public static String UPDATE_BIZ_LIST_WITH_LOC_LIST = new StringBuffer(500)
	.append("Update ")
	.append(SQLConstants.DISTPORTAL_SCHEMA)
	.append(".V_BUSINESS_USER_DATA SET STATUS = ? WHERE  DATA_ID IN (<DATA_LIST>)").toString();
	
	public static String UPDATE_BIZ_LIST_LOC_ID_ZONE = new StringBuffer(500)
	.append("Update ")
	.append(SQLConstants.DISTPORTAL_SCHEMA)
	.append(".VR_ACCOUNT_DETAILS SET ZONE_ID=? WHERE ZONE_ID=?").toString();
	
	public static String UPDATE_BIZ_LIST_LOC_ID_CITY = new StringBuffer(500)
	.append("Update ")
	.append(SQLConstants.DISTPORTAL_SCHEMA)
	.append(".VR_ACCOUNT_DETAILS SET CITY=? WHERE CITY=?").toString();
	
	public static String UPDATE_BIZ_LIST_LOC_ID = new StringBuffer(500)
	.append("Update ")
	.append(SQLConstants.DISTPORTAL_SCHEMA)
	.append(".V_BUSINESS_USER_DATA SET LOC_ID = ? WHERE  DATA_ID IN (<DATA_LIST>)").toString();
	
	public static String UPDATE_BIZ_LIST_PARENT_ID = new StringBuffer(500)
	.append("Update ")
	.append(SQLConstants.DISTPORTAL_SCHEMA)
	.append(".V_BUSINESS_USER_DATA SET PARENT_ID = ? WHERE PARENT_ID = ?").toString();
	
	public static String UPDATE_BIZ_LIST_CIRCLE = new StringBuffer(500)
	.append("Update ")
	.append(SQLConstants.DISTPORTAL_SCHEMA)
	.append(".V_BUSINESS_USER_DATA SET CIRCLE_CODE = ? WHERE DATA_ID IN (<DATA_ID>)").toString();
	
	public static String SEARCH_USER_TYPE_BASE_LOC = new StringBuffer(500)
	.append("select MASTER_ID , USER_TYPE , STATUS , PARENT_ID , BASE_LOC , USER_MAPPING , CODE_PREFIX FROM ")
	.append(SQLConstants.DISTPORTAL_SCHEMA)
	.append(".V_BUSINESS_USER_MASTER WHERE STATUS = ? AND MASTER_ID = ?")
	.append(" WITH UR").toString();
	
	public static String COUNT_STMT_ALL_Business_User = new StringBuffer(500)
	.append("SELECT COUNT(*) FROM ")
	.append(SQLConstants.DISTPORTAL_SCHEMA)
	.append(".V_BUSINESS_USER_DATA WHERE CIRCLE_CODE = ? AND MASTER_ID = ?")
	.append(" WITH UR").toString();
	
	public static String COUNT_STMT_ALL_Business_User_Active = new StringBuffer(500)
	.append("SELECT COUNT(*) FROM ")
	.append(SQLConstants.DISTPORTAL_SCHEMA)
	.append(".V_BUSINESS_USER_DATA bd,")
	.append(SQLConstants.DISTPORTAL_SCHEMA)
	.append(".V_REGISTERED_NUMBER WHERE BUS_USER_ID = DATA_ID AND bd.CIRCLE_CODE = ? AND bd.MASTER_ID = ? AND bd.STATUS=?")
	.append(" WITH UR").toString();
	
	
	public static String COUNT_STMT_ALL_Business_User_ED_CEO = new StringBuffer(500)
	.append("SELECT COUNT(*) FROM ")
	.append(SQLConstants.DISTPORTAL_SCHEMA)
	.append(".V_BUSINESS_USER_DATA WHERE MASTER_ID = ?")
	.append(" WITH UR").toString();
	
	public static String COUNT_STMT_ALL_Business_User_ED_CEOActive = new StringBuffer(500)
	.append("SELECT COUNT(*) FROM ")
	.append(SQLConstants.DISTPORTAL_SCHEMA)
	.append(".V_BUSINESS_USER_DATA bd,")
	.append(SQLConstants.DISTPORTAL_SCHEMA)
	.append(".V_REGISTERED_NUMBER WHERE BUS_USER_ID = DATA_ID AND bd.MASTER_ID = ? AND bd.STATUS=?")
	.append(" WITH UR").toString();
	
	public static String SEARCH_STMT_ALL_Busniess_User_CODE = new StringBuffer(500)
	.append("SELECT * from (select a.*,ROW_NUMber() over() rnum FROM (select DATA_ID,CONCAT(CHAR(USER_NAME),CONCAT(' -- ',CHAR(USER_CODE))) AS USER_NAME,USER_CODE ,")
	.append("CIRCLE_CODE , ud.STATUS , MOBILE_NO from ")
	.append(SQLConstants.DISTPORTAL_SCHEMA)
	.append(".V_BUSINESS_USER_DATA ud, ")
	.append(SQLConstants.DISTPORTAL_SCHEMA)
	.append(".V_REGISTERED_NUMBER ")
	.append("WHERE BUS_USER_ID = DATA_ID AND CIRCLE_CODE = ? AND MASTER_ID = ? AND USER_CODE = ? ORDER BY UPPER(USER_NAME)) a )b ")
	.append("Where rnum<=? and rnum>=? ")
	.append(" WITH UR").toString();
	
	public static String SEARCH_STMT_ALL_Busniess_User_CODE_Active = new StringBuffer(500)
	.append("SELECT * from (select a.*,ROW_NUMber() over() rnum FROM (select DATA_ID,CONCAT(CHAR(USER_NAME),CONCAT(' -- ',CHAR(USER_CODE))) AS USER_NAME,USER_CODE ,")
	.append("CIRCLE_CODE , ud.STATUS , MOBILE_NO from ")
	.append(SQLConstants.DISTPORTAL_SCHEMA)
	.append(".V_BUSINESS_USER_DATA ud, ")
	.append(SQLConstants.DISTPORTAL_SCHEMA)
	.append(".V_REGISTERED_NUMBER ")
	.append("WHERE BUS_USER_ID = DATA_ID AND CIRCLE_CODE = ? AND MASTER_ID = ? AND USER_CODE = ? AND ud.STATUS = ? ORDER BY UPPER(USER_NAME)) a )b ")
	.append("Where rnum<=? and rnum>=? ")
	.append(" WITH UR").toString();
	
	public static String SEARCH_STMT_CEO_ED_Busniess_User_CODE = new StringBuffer(500)
	.append("SELECT * from (select a.*,ROW_NUMber() over() rnum FROM (select DATA_ID,CONCAT(CHAR(USER_NAME),CONCAT(' -- ',CHAR(USER_CODE))) AS USER_NAME,USER_CODE ,")
	.append("CIRCLE_CODE , ud.STATUS , MOBILE_NO from ")
	.append(SQLConstants.DISTPORTAL_SCHEMA)
	.append(".V_BUSINESS_USER_DATA ud, ")
	.append(SQLConstants.DISTPORTAL_SCHEMA)
	.append(".V_REGISTERED_NUMBER ")
	.append("WHERE BUS_USER_ID = DATA_ID AND MASTER_ID = ? AND USER_CODE = ? ORDER BY UPPER(USER_NAME)) a )b ")
	.append("Where rnum<=? and rnum>=? ")
	.append(" WITH UR").toString();
	
	public static String SEARCH_STMT_CEO_ED_Busniess_User_CODE_Active = new StringBuffer(500)
	.append("SELECT * from (select a.*,ROW_NUMber() over() rnum FROM (select DATA_ID,CONCAT(CHAR(USER_NAME),CONCAT(' -- ',CHAR(USER_CODE))) AS USER_NAME,USER_CODE ,")
	.append("CIRCLE_CODE , ud.STATUS , MOBILE_NO from ")
	.append(SQLConstants.DISTPORTAL_SCHEMA)
	.append(".V_BUSINESS_USER_DATA ud, ")
	.append(SQLConstants.DISTPORTAL_SCHEMA)
	.append(".V_REGISTERED_NUMBER ")
	.append("WHERE BUS_USER_ID = DATA_ID AND MASTER_ID = ? AND USER_CODE = ? AND ud.STATUS = ? ORDER BY UPPER(USER_NAME)) a )b ")
	.append("Where rnum<=? and rnum>=? ")
	.append(" WITH UR").toString();
	
	
		
	public static String COUNT_STMT_ALL_Business_User_CODE = new StringBuffer(500)
	.append("SELECT COUNT(*) from ")
	.append(SQLConstants.DISTPORTAL_SCHEMA)
	.append(".V_BUSINESS_USER_DATA WHERE CIRCLE_CODE = ? AND MASTER_ID = ? AND USER_CODE = ? ")
	.append(" WITH UR").toString();
	
	public static String COUNT_STMT_ALL_Business_User_CODE_Active = new StringBuffer(500)
	.append("SELECT COUNT(*) from ")
	.append(SQLConstants.DISTPORTAL_SCHEMA)
	.append(".V_BUSINESS_USER_DATA bd,")
	.append(SQLConstants.DISTPORTAL_SCHEMA)
	.append(".V_REGISTERED_NUMBER WHERE BUS_USER_ID = DATA_ID AND bd.CIRCLE_CODE = ? AND bd.MASTER_ID = ? AND bd.USER_CODE = ? AND bd.STATUS=?")
	.append(" WITH UR").toString();
	
	public static String COUNT_STMT_ALL_Business_User_CODE_ED_CEO = new StringBuffer(500)
	.append("SELECT COUNT(*) from ")
	.append(SQLConstants.DISTPORTAL_SCHEMA)
	.append(".V_BUSINESS_USER_DATA WHERE MASTER_ID = ? AND USER_CODE = ? ")
	.append(" WITH UR").toString();
	
	public static String COUNT_STMT_ALL_Business_User_CODE_ED_CEO_Active = new StringBuffer(500)
	.append("SELECT COUNT(*) from ")
	.append(SQLConstants.DISTPORTAL_SCHEMA)
	.append(".V_BUSINESS_USER_DATA bd,")
	.append(SQLConstants.DISTPORTAL_SCHEMA)
	.append(".V_REGISTERED_NUMBER WHERE BUS_USER_ID = DATA_ID AND bd.MASTER_ID = ? AND bd.USER_CODE = ? AND bd.STATUS = ?")
	.append(" WITH UR").toString();


	public static String SEARCH_STMT_ALL_Business_user_NAME = new StringBuffer(500)
	.append("SELECT * from (select a.*,ROW_NUMber() over() rnum FROM (select DATA_ID,CONCAT(CHAR(USER_NAME),CONCAT(' -- ',CHAR(USER_CODE))) AS USER_NAME,USER_CODE,")
	.append("CIRCLE_CODE , ud.STATUS , MOBILE_NO from ")
	.append(SQLConstants.DISTPORTAL_SCHEMA)
	.append(".V_BUSINESS_USER_DATA ud,")
	.append(SQLConstants.DISTPORTAL_SCHEMA)
	.append(".V_REGISTERED_NUMBER ")
	.append("WHERE BUS_USER_ID = DATA_ID AND CIRCLE_CODE = ? AND MASTER_ID = ? AND UPPER(USER_NAME) LIKE ? ORDER BY UPPER(USER_NAME)) a )b ")
	.append("Where rnum<=? and rnum>=? ")
	.append(" WITH UR").toString();
	
	public static String SEARCH_STMT_ALL_Business_user_NAME_Active = new StringBuffer(500)
	.append("SELECT * from (select a.*,ROW_NUMber() over() rnum FROM (select DATA_ID,CONCAT(CHAR(USER_NAME),CONCAT(' -- ',CHAR(USER_CODE))) AS USER_NAME,USER_CODE,")
	.append("CIRCLE_CODE , ud.STATUS , MOBILE_NO from ")
	.append(SQLConstants.DISTPORTAL_SCHEMA)
	.append(".V_BUSINESS_USER_DATA ud,")
	.append(SQLConstants.DISTPORTAL_SCHEMA)
	.append(".V_REGISTERED_NUMBER ")
	.append("WHERE BUS_USER_ID = DATA_ID AND CIRCLE_CODE = ? AND MASTER_ID = ? AND UPPER(USER_NAME) LIKE ? AND ud.STATUS = ? ORDER BY UPPER(USER_NAME)) a )b ")
	.append("Where rnum<=? and rnum>=? ")
	.append(" WITH UR").toString();
	
	public static String SEARCH_STMT_CEO_ED_Business_user_NAME = new StringBuffer(500)
	.append("SELECT * from (select a.*,ROW_NUMber() over() rnum FROM (select DATA_ID,CONCAT(CHAR(USER_NAME),CONCAT(' -- ',CHAR(USER_CODE))) AS USER_NAME,USER_CODE,")
	.append("CIRCLE_CODE , ud.STATUS , MOBILE_NO from ")
	.append(SQLConstants.DISTPORTAL_SCHEMA)
	.append(".V_BUSINESS_USER_DATA ud,")
	.append(SQLConstants.DISTPORTAL_SCHEMA)
	.append(".V_REGISTERED_NUMBER ")
	.append("WHERE BUS_USER_ID = DATA_ID AND MASTER_ID = ? AND UPPER(USER_NAME) LIKE ? ORDER BY UPPER(USER_NAME)) a )b ")
	.append("Where rnum<=? and rnum>=? ")
	.append(" WITH UR").toString();
	
	public static String SEARCH_STMT_CEO_ED_Business_user_NAME_Active = new StringBuffer(500)
	.append("SELECT * from (select a.*,ROW_NUMber() over() rnum FROM (select DATA_ID,CONCAT(CHAR(USER_NAME),CONCAT(' -- ',CHAR(USER_CODE))) AS USER_NAME,USER_CODE,")
	.append("CIRCLE_CODE , ud.STATUS , MOBILE_NO from ")
	.append(SQLConstants.DISTPORTAL_SCHEMA)
	.append(".V_BUSINESS_USER_DATA ud,")
	.append(SQLConstants.DISTPORTAL_SCHEMA)
	.append(".V_REGISTERED_NUMBER ")
	.append("WHERE BUS_USER_ID = DATA_ID AND MASTER_ID = ? AND UPPER(USER_NAME) LIKE ? AND ud.STATUS = ? ORDER BY UPPER(USER_NAME)) a )b ")
	.append("Where rnum<=? and rnum>=? ")
	.append(" WITH UR").toString();
	

	public static String COUNT_STMT_ALL_Business_User_NAME = new StringBuffer(500)
	.append("SELECT COUNT(*) from ")
	.append(SQLConstants.DISTPORTAL_SCHEMA)
	.append(".V_BUSINESS_USER_DATA WHERE CIRCLE_CODE = ? AND MASTER_ID = ? AND UPPER(USER_NAME) LIKE ? ")
	.append(" WITH UR").toString();
	
	public static String COUNT_STMT_ALL_Business_User_NAME_Active = new StringBuffer(500)
	.append("SELECT COUNT(*) from ")
	.append(SQLConstants.DISTPORTAL_SCHEMA)
	.append(".V_BUSINESS_USER_DATA bd,")
	.append(SQLConstants.DISTPORTAL_SCHEMA)
	.append(".V_REGISTERED_NUMBER WHERE BUS_USER_ID = DATA_ID AND bd.CIRCLE_CODE = ? AND bd.MASTER_ID = ? AND UPPER(USER_NAME) LIKE ? AND bd.STATUS = ?")
	.append(" WITH UR").toString();
	
	
	public static String COUNT_STMT_ALL_Business_User_NAME_ED_CEO = new StringBuffer(500)
	.append("SELECT COUNT(*) from ")
	.append(SQLConstants.DISTPORTAL_SCHEMA)
	.append(".V_BUSINESS_USER_DATA WHERE MASTER_ID = ? AND UPPER(USER_NAME) LIKE ? ")
	.append(" WITH UR").toString();

	public static String COUNT_STMT_ALL_Business_User_NAME_ED_CEO_ACTIVE = new StringBuffer(500)
	.append("SELECT COUNT(*) from ")
	.append(SQLConstants.DISTPORTAL_SCHEMA)
	.append(".V_BUSINESS_USER_DATA bd,")
	.append(SQLConstants.DISTPORTAL_SCHEMA)
	.append(".V_REGISTERED_NUMBER WHERE BUS_USER_ID = DATA_ID AND bd.MASTER_ID = ? AND UPPER(USER_NAME) LIKE ? AND bd.STATUS = ?")
	.append(" WITH UR").toString();
	
	public static String COUNT_STMT_REGNUMBER_All_Busniess = new StringBuffer(500)
	.append("SELECT COUNT(*) from ")
	.append(SQLConstants.DISTPORTAL_SCHEMA)
	.append(".V_BUSINESS_USER_DATA dm , ")
	.append(SQLConstants.DISTPORTAL_SCHEMA)
	.append(".V_REGISTERED_NUMBER drn ") 
	.append("WHERE dm.CIRCLE_CODE =?  AND dm.MASTER_ID = ? AND drn.MOBILE_NO = ? AND dm.DATA_ID = drn.BUS_USER_ID ")
	.append("WITH UR").toString();
	
	public static String COUNT_STMT_REGNUMBER_All_Busniess_Active = new StringBuffer(500)
	.append("SELECT COUNT(*) from ")
	.append(SQLConstants.DISTPORTAL_SCHEMA)
	.append(".V_BUSINESS_USER_DATA dm , ")
	.append(SQLConstants.DISTPORTAL_SCHEMA)
	.append(".V_REGISTERED_NUMBER drn ") 
	.append("WHERE dm.CIRCLE_CODE =?  AND dm.MASTER_ID = ? AND drn.MOBILE_NO = ? AND dm.STATUS = ? AND dm.DATA_ID = drn.BUS_USER_ID ")
	.append("WITH UR").toString();
	
	
	public static String COUNT_STMT_REGNUMBER_All_Busniess_ED_CEO = new StringBuffer(500)
	.append("SELECT COUNT(*) from ")
	.append(SQLConstants.DISTPORTAL_SCHEMA)
	.append(".V_BUSINESS_USER_DATA dm , ")
	.append(SQLConstants.DISTPORTAL_SCHEMA)
	.append(".V_REGISTERED_NUMBER drn ") 
	.append("WHERE dm.MASTER_ID = ? AND drn.MOBILE_NO = ? AND dm.DATA_ID = drn.BUS_USER_ID ")
	.append("WITH UR").toString();
	
	public static String COUNT_STMT_REGNUMBER_All_Busniess_ED_CEO_Active = new StringBuffer(500)
	.append("SELECT COUNT(*) from ")
	.append(SQLConstants.DISTPORTAL_SCHEMA)
	.append(".V_BUSINESS_USER_DATA dm , ")
	.append(SQLConstants.DISTPORTAL_SCHEMA)
	.append(".V_REGISTERED_NUMBER drn ") 
	.append("WHERE dm.MASTER_ID = ? AND drn.MOBILE_NO = ? AND dm.STATUS = ? AND dm.DATA_ID = drn.BUS_USER_ID ")
	.append("WITH UR").toString();
	
	public static String COUNT_STMT_REGNUMBER_DIST_STATUS = new StringBuffer(500)
	.append("SELECT COUNT(*) from ")
	.append(SQLConstants.DISTPORTAL_SCHEMA)
	.append(".V_DISTRIBUTOR_MSTR dm , ")
	.append(SQLConstants.DISTPORTAL_SCHEMA)
	.append(".V_DISTRIBUTOR_REGISTERED_NUMBE drn ") 
	.append("WHERE dm.CIRCLE_CODE =?  AND drn.MOBILE_NO=?  AND drn.STATUS=? AND dm.DISTRIBUTOR_ID = drn.DISTRIBUTOR_ID ")
	.append("WITH UR").toString();
	
	public static String COUNT_STMT_REGNUMBER_Business_User_STATUS = new StringBuffer(500)
	.append("SELECT COUNT(*) from ")
	.append(SQLConstants.DISTPORTAL_SCHEMA)
	.append(".V_BUSINESS_USER_DATA dm , ")
	.append(SQLConstants.DISTPORTAL_SCHEMA)
	.append(".V_REGISTERED_NUMBER drn ") 
	.append("WHERE dm.CIRCLE_CODE =?  AND dm.MASTER_ID = ? AND drn.MOBILE_NO = ?  AND drn.STATUS = ? AND dm.DATA_ID = drn.BUS_USER_ID ")
	.append("WITH UR").toString();
	
	public static String COUNT_STMT_REGNUMBER_Business_User_STATUS_Active = new StringBuffer(500)
	.append("SELECT COUNT(*) from ")
	.append(SQLConstants.DISTPORTAL_SCHEMA)
	.append(".V_BUSINESS_USER_DATA dm , ")
	.append(SQLConstants.DISTPORTAL_SCHEMA)
	.append(".V_REGISTERED_NUMBER drn ") 
	.append("WHERE dm.CIRCLE_CODE =?  AND dm.MASTER_ID = ? AND drn.MOBILE_NO = ?  AND drn.STATUS = ? AND dm.STATUS = ? AND dm.DATA_ID = drn.BUS_USER_ID ")
	.append("WITH UR").toString();
	
	public static String COUNT_STMT_REGNUMBER_Business_User_STATUS_ED_CEO = new StringBuffer(500)
	.append("SELECT COUNT(*) from ")
	.append(SQLConstants.DISTPORTAL_SCHEMA)
	.append(".V_BUSINESS_USER_DATA dm , ")
	.append(SQLConstants.DISTPORTAL_SCHEMA)
	.append(".V_REGISTERED_NUMBER drn ") 
	.append("WHERE dm.MASTER_ID = ? AND drn.MOBILE_NO = ?  AND drn.STATUS = ? AND dm.DATA_ID = drn.BUS_USER_ID ")
	.append("WITH UR").toString();
	
	public static String COUNT_STMT_REGNUMBER_Business_User_STATUS_ED_CEO_Active = new StringBuffer(500)
	.append("SELECT COUNT(*) from ")
	.append(SQLConstants.DISTPORTAL_SCHEMA)
	.append(".V_BUSINESS_USER_DATA dm , ")
	.append(SQLConstants.DISTPORTAL_SCHEMA)
	.append(".V_REGISTERED_NUMBER drn ") 
	.append("WHERE dm.MASTER_ID = ? AND drn.MOBILE_NO = ?  AND drn.STATUS = ? AND dm.STATUS=? AND dm.DATA_ID = drn.BUS_USER_ID ")
	.append("WITH UR").toString();

		
	public static String SELECT_STMT_REGNUMBER_Business_User = new StringBuffer(500)
	.append("SELECT * from (select a.*,ROW_NUMber() over() rnum FROM (SELECT DATA_ID,CONCAT(CHAR(USER_NAME),CONCAT(' -- ',CHAR(USER_CODE))) AS USER_NAME,USER_CODE,CIRCLE_CODE , dm.STATUS , MOBILE_NO  from ")
	.append(SQLConstants.DISTPORTAL_SCHEMA)
	.append(".V_BUSINESS_USER_DATA dm , ")
	.append(SQLConstants.DISTPORTAL_SCHEMA)
	.append(".V_REGISTERED_NUMBER drn ") 
	.append("WHERE dm.CIRCLE_CODE =? AND dm.MASTER_ID = ? AND drn.MOBILE_NO=?  AND dm.DATA_ID = drn.BUS_USER_ID ) a )b ")
	.append("Where rnum<=? and rnum>=? ")
	.append(" WITH UR").toString();
	
	public static String SELECT_STMT_REGNUMBER_Business_User_Active = new StringBuffer(500)
	.append("SELECT * from (select a.*,ROW_NUMber() over() rnum FROM (SELECT DATA_ID,CONCAT(CHAR(USER_NAME),CONCAT(' -- ',CHAR(USER_CODE))) AS USER_NAME,USER_CODE,CIRCLE_CODE , dm.STATUS , MOBILE_NO  from ")
	.append(SQLConstants.DISTPORTAL_SCHEMA)
	.append(".V_BUSINESS_USER_DATA dm , ")
	.append(SQLConstants.DISTPORTAL_SCHEMA)
	.append(".V_REGISTERED_NUMBER drn ") 
	.append("WHERE dm.CIRCLE_CODE =? AND dm.MASTER_ID = ? AND drn.MOBILE_NO=? AND dm.STATUS = ? AND dm.DATA_ID = drn.BUS_USER_ID ) a )b ")
	.append("Where rnum<=? and rnum>=? ")
	.append(" WITH UR").toString();
	
	public static String SELECT_STMT_CEO_ED_REGNUMBER_Business_User = new StringBuffer(500)
	.append("SELECT * from (select a.*,ROW_NUMber() over() rnum FROM (SELECT DATA_ID,CONCAT(CHAR(USER_NAME),CONCAT(' -- ',CHAR(USER_CODE))) AS USER_NAME,USER_CODE,CIRCLE_CODE , dm.STATUS , MOBILE_NO  from ")
	.append(SQLConstants.DISTPORTAL_SCHEMA)
	.append(".V_BUSINESS_USER_DATA dm , ")
	.append(SQLConstants.DISTPORTAL_SCHEMA)
	.append(".V_REGISTERED_NUMBER drn ") 
	.append("WHERE dm.MASTER_ID = ? AND drn.MOBILE_NO=?  AND dm.DATA_ID = drn.BUS_USER_ID ) a )b ")
	.append("Where rnum<=? and rnum>=? ")
	.append(" WITH UR").toString();
	
	public static String SELECT_STMT_CEO_ED_REGNUMBER_Business_User_Active = new StringBuffer(500)
	.append("SELECT * from (select a.*,ROW_NUMber() over() rnum FROM (SELECT DATA_ID,CONCAT(CHAR(USER_NAME),CONCAT(' -- ',CHAR(USER_CODE))) AS USER_NAME,USER_CODE,CIRCLE_CODE , dm.STATUS , MOBILE_NO  from ")
	.append(SQLConstants.DISTPORTAL_SCHEMA)
	.append(".V_BUSINESS_USER_DATA dm , ")
	.append(SQLConstants.DISTPORTAL_SCHEMA)
	.append(".V_REGISTERED_NUMBER drn ") 
	.append("WHERE dm.MASTER_ID = ? AND drn.MOBILE_NO=?  AND dm.STATUS = ? AND dm.DATA_ID = drn.BUS_USER_ID ) a )b ")
	.append("Where rnum<=? and rnum>=? ")
	.append(" WITH UR").toString();

	
	public static String SELECT_STMT_REGNUMBER_Business_User_STATUS = new StringBuffer(500)
	.append("SELECT * from (select a.*,ROW_NUMber() over() rnum FROM (SELECT DATA_ID,CONCAT(CHAR(USER_NAME),CONCAT(' -- ',CHAR(USER_CODE))) AS USER_NAME,USER_CODE,CIRCLE_CODE , dm.STATUS , MOBILE_NO  from ")
	.append(SQLConstants.DISTPORTAL_SCHEMA)
	.append(".V_BUSINESS_USER_DATA dm , ")
	.append(SQLConstants.DISTPORTAL_SCHEMA)
	.append(".V_REGISTERED_NUMBER drn ") 
	.append("WHERE dm.CIRCLE_CODE =?  AND dm.MASTER_ID = ? AND drn.MOBILE_NO=? AND drn.STATUS=? AND dm.DATA_ID = drn.BUS_USER_ID ) a )b ")
	.append("Where rnum<=? and rnum>=? ")
	.append(" WITH UR").toString();
	
	public static String SELECT_STMT_REGNUMBER_Business_User_STATUS_Active = new StringBuffer(500)
	.append("SELECT * from (select a.*,ROW_NUMber() over() rnum FROM (SELECT DATA_ID,CONCAT(CHAR(USER_NAME),CONCAT(' -- ',CHAR(USER_CODE))) AS USER_NAME,USER_CODE,CIRCLE_CODE , dm.STATUS , MOBILE_NO  from ")
	.append(SQLConstants.DISTPORTAL_SCHEMA)
	.append(".V_BUSINESS_USER_DATA dm , ")
	.append(SQLConstants.DISTPORTAL_SCHEMA)
	.append(".V_REGISTERED_NUMBER drn ") 
	.append("WHERE dm.CIRCLE_CODE =?  AND dm.MASTER_ID = ? AND drn.MOBILE_NO=? AND drn.STATUS=? AND dm.STATUS = ? AND dm.DATA_ID = drn.BUS_USER_ID ) a )b ")
	.append("Where rnum<=? and rnum>=? ")
	.append(" WITH UR").toString();
	
	public static String SELECT_STMT_CEO_ED_REGNUMBER_Business_User_STATUS = new StringBuffer(500)
	.append("SELECT * from (select a.*,ROW_NUMber() over() rnum FROM (SELECT DATA_ID,CONCAT(CHAR(USER_NAME),CONCAT(' -- ',CHAR(USER_CODE))) AS USER_NAME,USER_CODE,CIRCLE_CODE , dm.STATUS , MOBILE_NO  from ")
	.append(SQLConstants.DISTPORTAL_SCHEMA)
	.append(".V_BUSINESS_USER_DATA dm , ")
	.append(SQLConstants.DISTPORTAL_SCHEMA)
	.append(".V_REGISTERED_NUMBER drn ") 
	.append("WHERE dm.MASTER_ID = ? AND drn.MOBILE_NO=? AND drn.STATUS=? AND dm.DATA_ID = drn.BUS_USER_ID ) a )b ")
	.append("Where rnum<=? and rnum>=? ")
	.append(" WITH UR").toString();
	
	public static String SELECT_STMT_CEO_ED_REGNUMBER_Business_User_STATUS_Active = new StringBuffer(500)
	.append("SELECT * from (select a.*,ROW_NUMber() over() rnum FROM (SELECT DATA_ID,CONCAT(CHAR(USER_NAME),CONCAT(' -- ',CHAR(USER_CODE))) AS USER_NAME,USER_CODE,CIRCLE_CODE , dm.STATUS , MOBILE_NO  from ")
	.append(SQLConstants.DISTPORTAL_SCHEMA)
	.append(".V_BUSINESS_USER_DATA dm , ")
	.append(SQLConstants.DISTPORTAL_SCHEMA)
	.append(".V_REGISTERED_NUMBER drn ") 
	.append("WHERE dm.MASTER_ID = ? AND drn.MOBILE_NO=? AND drn.STATUS=? AND dm.STATUS = ? AND dm.DATA_ID = drn.BUS_USER_ID ) a )b ")
	.append("Where rnum<=? and rnum>=? ")
	.append(" WITH UR").toString();
	
	
	public static String DIST_PROFILE_Business_User = new StringBuffer(500)
	.append("select DATA_ID ,USER_CODE ,USER_NAME ,d.MASTER_ID ,d.PARENT_ID ,d.STATUS ,CONTACT_NO ,ADDRESS ,CIRCLE_CODE ,HUB_CODE ,FOS_CHECK_REQ ,FOS_ACTV_CHK ,LOC_ID ,ALL_SERVICE_CLASS ,")
	.append(" mt.USER_TYPE , INCLUDE_SERVICE ,EXCLUDE_SERVICE ,CREATED_BY ,UPDATED_DT ,UPDATED_BY ,CREATED_DT,IPHONE_ACT_RIGHTS from  ")
	.append(SQLConstants.DISTPORTAL_SCHEMA)
	.append(".V_BUSINESS_USER_DATA d, ")
	.append(SQLConstants.DISTPORTAL_SCHEMA)
	.append(".V_BUSINESS_USER_MASTER mt where DATA_ID = ? AND d.MASTER_ID = mt.MASTER_ID ")
	.append(" WITH UR").toString();

		
	public static String CHECK_Avilable_User = new StringBuffer(500)
	.append("select DATA_ID ,USER_CODE ,USER_NAME ,MASTER_ID ,PARENT_ID ,STATUS ,CONTACT_NO ,ADDRESS ,CIRCLE_CODE ,HUB_CODE ,FOS_CHECK_REQ ,FOS_ACTV_CHK ,LOC_ID ,ALL_SERVICE_CLASS ,")
	.append(" INCLUDE_SERVICE ,EXCLUDE_SERVICE ,CREATED_BY ,UPDATED_DT ,UPDATED_BY ,CREATED_DT from ")
	.append(SQLConstants.DISTPORTAL_SCHEMA)
	.append(".V_BUSINESS_USER_DATA where USER_CODE = ? AND MASTER_ID =? ")
	.append(" WITH UR").toString();
	
	public static String GET_Avilable_User = new StringBuffer(500)
	.append("select DATA_ID ,USER_CODE ,USER_NAME ,d.MASTER_ID ,d.PARENT_ID ,d.STATUS ,CONTACT_NO ,ADDRESS ,CIRCLE_CODE ,HUB_CODE ,FOS_CHECK_REQ ,FOS_ACTV_CHK ,LOC_ID ,ALL_SERVICE_CLASS ,")
	.append("INCLUDE_SERVICE ,EXCLUDE_SERVICE ,CREATED_BY ,UPDATED_DT ,UPDATED_BY ,CREATED_DT,m.BASE_LOC from ")
	.append(SQLConstants.DISTPORTAL_SCHEMA)
	.append(".V_BUSINESS_USER_DATA d ,")
	.append(SQLConstants.DISTPORTAL_SCHEMA)
	.append(".V_BUSINESS_USER_MASTER m where  DATA_ID = ? AND d.MASTER_ID = m.MASTER_ID")
	.append(" WITH UR").toString();

	public static final String FAILED_DATA = new StringBuffer(500)
	.append("SELECT * from(select a.*,ROW_NUMBER() OVER() rnum FROM (SELECT * FROM ")
	.append(SQLConstants.FTA_SCHEMA)
	.append(".V_FAILED_TRANSACTION where FILE_ID=? ) a)b")
	.append(" Where rnum<=? and rnum>=?")
	.append(" WITH UR").toString();
	
	public static final String FAILED_DATA_Business_User = new StringBuffer(500)
	.append("SELECT * from(select a.*,ROW_NUMBER() OVER() rnum FROM (SELECT * FROM ")
	.append(SQLConstants.DISTPORTAL_SCHEMA)
	.append(".V_FAILED_TRANSACTION where FILE_ID=? ) a)b")
	.append(" Where rnum<=? and rnum>=?")
	.append(" WITH UR").toString();

	public final String COUNT_FAILED_DATA = new StringBuffer(500)
	.append("select count(*) FROM ")
	.append(SQLConstants.FTA_SCHEMA)
	.append(".V_FAILED_TRANSACTION where FILE_ID=? ")
	.append(" WITH UR").toString();
	
	public final String COUNT_FAILED_DATA_Business_User = new StringBuffer(500)
	.append("select count(*) FROM ")
	.append(SQLConstants.DISTPORTAL_SCHEMA)
	.append(".V_FAILED_TRANSACTION where FILE_ID=? ")
	.append(" WITH UR").toString();

	public static final String NEXT_VALUE = new StringBuffer(500)
	.append("SELECT NEXTVAL FOR SEQ_V_FILE_MSTR FROM  SYSIBM.SYSDUMMY1 ")
	.append("FETCH FIRST 1 ROWS ONLY ")
	.append(" WITH UR").toString();
	
	public static final String NEXT_VALUE_Business_User = new StringBuffer(500)
	.append("SELECT NEXTVAL FOR SEQ_V_FILE_MSTR FROM  SYSIBM.SYSDUMMY1 ")
	.append("FETCH FIRST 1 ROWS ONLY ")
	.append(" WITH UR").toString();

	public static String FILE_ENTRY = new StringBuffer(500)
	.append("INSERT INTO ")
	.append(SQLConstants.FTA_SCHEMA)
	.append(".V_FILE_MSTR(FILE_ID, FILE_NAME, ")
	.append("INTERNAL_FILE_NAME, CIRCLE_CODE, STATUS, CREATED_BY, UPDATED_DT, UPDATED_BY, ")
	.append("CREATED_DT)VALUES(?,?,?,?,'A',?,current timestamp,?,current timestamp) ")
	.append(" WITH UR").toString();
	
	public static String FILE_ENTRY_Business_User = new StringBuffer(500)
	.append("INSERT INTO ")
	.append(SQLConstants.DISTPORTAL_SCHEMA)
	.append(".V_FILE_MSTR(FILE_ID, FILE_NAME, ")
	.append("INTERNAL_FILE_NAME, CIRCLE_CODE, STATUS, CREATED_BY, UPDATED_DT, UPDATED_BY, ")
	.append("CREATED_DT)VALUES(?,?,?,?,'A',?,current timestamp,?,current timestamp) ")
	.append(" WITH UR").toString();

	
	public static final String INSERT_Business_User_Data = new StringBuffer(500)
	.append("INSERT INTO ")
	.append(SQLConstants.DISTPORTAL_SCHEMA)
	.append(".V_BUSINESS_USER_DATA(DATA_ID,USER_CODE,USER_NAME,MASTER_ID,")
	.append(" PARENT_ID,STATUS,CONTACT_NO,ADDRESS,CIRCLE_CODE,HUB_CODE,FOS_CHECK_REQ,FOS_ACTV_CHK,LOC_ID,CREATED_BY,CREATED_DT,UPDATED_BY,UPDATED_DT,ALL_SERVICE_CLASS,INCLUDE_SERVICE,EXCLUDE_SERVICE,IPHONE_ACT_RIGHTS) ")
	.append("values(")
	.append("?,?,?,?,?,?,?,?,?,?,?,?,?,?,current timestamp,?,current timestamp,?,?,?,?)")
	.append(" WITH UR").toString();

	public static final String SELECT_REG_CHECK = new StringBuffer(500)
	.append("SELECT MOBILE_ID ,MOBILE_NO ,BUS_USER_ID ,STATUS ,CREATED_BY ,UPDATED_DT ,UPDATED_BY ,CREATED_DT FROM ")
	.append(SQLConstants.DISTPORTAL_SCHEMA)
	.append(".V_REGISTERED_NUMBER WHERE ")
	.append("MOBILE_NO=? ")
	.append(" WITH UR").toString();

	
	public static final String SELECT_REG_FROMALL_DIST_DEAL_FOS = new StringBuffer(500)
	.append(" SELECT COUNT(*) FROM ")
	.append(SQLConstants.DISTPORTAL_SCHEMA)
	.append(".V_REGISTERED_NUMBER rm ,")
	.append(SQLConstants.DISTPORTAL_SCHEMA)
	.append(".V_BUSINESS_USER_DATA ud ")
	.append(" WHERE rm.MOBILE_NO=? AND rm.STATUS='A' AND ud.DATA_ID=rm.BUS_USER_ID AND ud.MASTER_ID = ? ")
	.append(" WITH UR").toString();
	
	public static final String SELECT_REG_FROMALL_DIST_DEAL_FOS_Two = new StringBuffer(500)
	.append(" SELECT COUNT(*) FROM ")
	.append(SQLConstants.DISTPORTAL_SCHEMA)
	.append(".V_REGISTERED_NUMBER rm ,")
	.append(SQLConstants.DISTPORTAL_SCHEMA)
	.append(".V_BUSINESS_USER_DATA ud ")
	.append(" WHERE MOBILE_NO=? AND ud.DATA_ID=rm.BUS_USER_ID AND ud.MASTER_ID = ?  ")
	.append(" WITH UR").toString();


	public static final String INSERT_STMT_DIST = new StringBuffer(500)
	.append("Insert into ")
	.append(SQLConstants.DISTPORTAL_SCHEMA)
	.append(".V_REGISTERED_NUMBER(MOBILE_ID, ")
	.append(" MOBILE_NO  ,BUS_USER_ID , STATUS, CREATED_BY, UPDATED_DT, UPDATED_BY, CREATED_DT) values(next value ")
	.append(" for ")
	.append(SQLConstants.DISTPORTAL_SCHEMA)
	.append(".SEQ_V_REGISTERED_NUMBER,?,?,?,?,current timestamp,?,current timestamp) ")
	.append(" with ur ").toString();

	
	public static final String VIEW_REG_NOS_All_Business_User = new StringBuffer(500)
	.append("SELECT MOBILE_ID , MOBILE_NO, BUS_USER_ID , STATUS, CREATED_BY , ")
	.append("UPDATED_DT , UPDATED_BY , CREATED_DT FROM ")
	.append(SQLConstants.DISTPORTAL_SCHEMA)
	.append(".V_REGISTERED_NUMBER ")
	.append("WHERE BUS_USER_ID = ? ")
	.append(" WITH UR").toString();

	public static final String UPDATE_DIST = new StringBuffer(500)
	.append("UPDATE ")
	.append(SQLConstants.DISTPORTAL_SCHEMA)
	.append(".V_REGISTERED_NUMBER SET MOBILE_NO =?,UPDATED_BY =?,STATUS=?,")
	.append("UPDATED_DT =current timestamp  WHERE MOBILE_ID=? ")
	.append(" WITH UR").toString();
	
	public static final String VIEW_SINGLE_REG_NO_Business_User = new StringBuffer(500)
	.append("SELECT a.MOBILE_ID , a.MOBILE_NO , a.BUS_USER_ID , a.STATUS, a.CREATED_BY, ")
	.append("a.UPDATED_DT, a.UPDATED_BY, a.CREATED_DT,b.CIRCLE_CODE FROM ")
	.append(SQLConstants.DISTPORTAL_SCHEMA)
	.append(".V_REGISTERED_NUMBER a, ")
	.append(SQLConstants.DISTPORTAL_SCHEMA)
	.append(".V_BUSINESS_USER_DATA b ")
	.append("WHERE a.MOBILE_ID=? AND b.DATA_ID=a.BUS_USER_ID")
	.append(" WITH UR").toString();

	
	public static String FILE_ENTRY_FOS = new StringBuffer(500)
	.append("INSERT INTO ")
	.append(SQLConstants.DISTPORTAL_SCHEMA)
	.append(".V_FILE_MSTR(FILE_ID, FILE_NAME, INTERNAL_FILE_NAME, CIRCLE_CODE, STATUS, CREATED_BY, UPDATED_DT, UPDATED_BY, CREATED_DT)VALUES(?,?,?,?,'A',?,CURRENT TIMESTAMP,?,CURRENT TIMESTAMP) ")
	.append(" WITH UR").toString();


	public static final String DELETE_DIST_SUBS_MAPPING_FOS = new StringBuffer(500)
	.append("DELETE FROM ")
	.append(SQLConstants.DISTPORTAL_SCHEMA)
	.append(".V_DIST_SUBSCRIBER_MAPPING WHERE FOS_ID = ?")
	.append(" WITH UR").toString();


	public static final String NEXT_VALUE_FOS = new StringBuffer(500)
	.append("SELECT NEXTVAL FOR SEQ_V_FILE_MSTR FROM  SYSIBM.SYSDUMMY1 FETCH FIRST 1 ROWS ONLY ")
	.append(" WITH UR").toString();


	public static final String FAILED_DATA_FOS = new StringBuffer(500)
	.append("SELECT FILE_ID , FAILED_TRANSACTION , FAILURE_DETAILS , TRANSACTION_DATE FROM ")
	.append(SQLConstants.FTA_SCHEMA)
	.append(".V_FAILED_TRANSACTION where FILE_ID=?")
	.append(" WITH UR").toString();

	public static final String INSERT_ERROR_DATA = new StringBuffer(500)
	.append("Insert into ")
	.append(SQLConstants.FTA_SCHEMA)
	.append(".V_FAILED_TRANSACTION(FILE_ID,FAILED_TRANSACTION,FAILURE_DETAILS,TRANSACTION_DATE) values(?,?,?,current timestamp)")
	.append(" WITH UR").toString();

	public static final String INSERT_ERROR_DATA_ZBM = new StringBuffer(500)
	.append("Insert into ")
	.append(SQLConstants.FTA_SCHEMA)
	.append(".V_FAILED_TRANSACTION(FILE_ID,FAILED_TRANSACTION,FAILURE_DETAILS,TRANSACTION_DATE) values(?,?,?,current timestamp)")
	.append(" WITH UR").toString();

	public static final String INSERT_ERROR_DATA_ZSM = new StringBuffer(500)
	.append("Insert into ")
	.append(SQLConstants.FTA_SCHEMA)
	.append(".V_FAILED_TRANSACTION(FILE_ID,FAILED_TRANSACTION,FAILURE_DETAILS,TRANSACTION_DATE) values(?,?,?,current timestamp)")
	.append(" WITH UR").toString();

public static String Loc_Upwards_Query = new StringBuffer(500)
	.append("WITH temptab(LOC_DATA_ID,LOCATION_NAME,STATUS,LOC_MSTR_ID,PARENT_ID) AS ") 
	.append("( ")
	.append("Select root.LOC_DATA_ID AS LOC_DATA_ID, root.LOCATION_NAME AS LOCATION_NAME, root.STATUS AS STATUS, ") 
	.append(" root.LOC_MSTR_ID AS LOC_MSTR_ID, root.PARENT_ID AS PARENT_ID ")
	.append(" from ")
	.append(SQLConstants.PRODCAT_SCHEMA)
	.append(".PC_LOCATION_DATA root ") 
	.append(" where root.LOC_DATA_ID =  ? ")
	.append(" UNION ALL ") 
	.append(" Select sub.LOC_DATA_ID AS LOC_DATA_ID, sub.LOCATION_NAME AS LOCATION_NAME, sub.STATUS AS STATUS, ") 
	.append(" sub.LOC_MSTR_ID AS LOC_MSTR_ID, sub.PARENT_ID AS PARENT_ID ")
	.append(" from ")
	.append(SQLConstants.PRODCAT_SCHEMA)
	.append(".PC_LOCATION_DATA  sub, temptab super ")
	.append(" WHERE CHAR(sub.LOC_DATA_ID) =  super.PARENT_ID ") 
	.append(")")
	.append(" SELECT * from temptab ")
	.append(" WITH UR ").toString();

public static final String Prefix_For_Code = new StringBuffer(500)
	.append("select MASTER_ID ,USER_TYPE ,STATUS ,PARENT_ID ,BASE_LOC ,USER_MAPPING ,CODE_PREFIX from ")
	.append(SQLConstants.DISTPORTAL_SCHEMA)
	.append(".V_BUSINESS_USER_MASTER where MASTER_ID =?")
	.append(" WITH UR").toString();

public static final String Code_for_Business_User_Data = new StringBuffer(500)
	.append("VALUES NEXTVAL FOR ")
	.append(SQLConstants.DISTPORTAL_SCHEMA)
	.append(".SEQ_V_BUSINESS_USER_CODE ")
	.append(" WITH UR").toString();
	
	public static final String BUSINESS_USER_DETAILS = new StringBuffer(500)
	.append(" SELECT rm.MOBILE_NO,bd.USER_NAME,bm.USER_TYPE FROM ")
	.append(SQLConstants.DISTPORTAL_SCHEMA)
	.append(".V_REGISTERED_NUMBER rm,")
	.append(SQLConstants.DISTPORTAL_SCHEMA)
	.append(".V_BUSINESS_USER_DATA bd,")
	.append(SQLConstants.DISTPORTAL_SCHEMA)
	.append(".V_BUSINESS_USER_MASTER bm ")
	.append("Where rm.MOBILE_NO = ? ")
	.append("AND rm.BUS_USER_ID=bd.DATA_ID ")
	.append("AND bd.MASTER_ID=bm.MASTER_ID AND UPPER(bm.USER_TYPE) = ? ")
	.append("WITH UR").toString();


}
