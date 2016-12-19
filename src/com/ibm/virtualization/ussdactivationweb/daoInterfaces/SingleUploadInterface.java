/**************************************************************************
 * File     : SingleUploadInterface.java
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

package com.ibm.virtualization.ussdactivationweb.daoInterfaces;

import com.ibm.virtualization.ussdactivationweb.utils.SQLConstants;

/**
 * @author abhipsa
 * 
 */
public interface SingleUploadInterface {

	public static final String COUNT_Business_user = new StringBuffer(500)
	.append("select count(*)  from ")
	.append(SQLConstants.DISTPORTAL_SCHEMA)
	.append(".V_BUSINESS_USER_DATA where CIRCLE_CODE=? AND MASTER_ID =? AND STATUS='A'")
	.append(" WITH UR").toString();
	
	public static final String COUNT_Business_user_CEO = new StringBuffer(500)
	.append("select count(*)  from ")
	.append(SQLConstants.DISTPORTAL_SCHEMA)
	.append(".V_BUSINESS_USER_DATA where MASTER_ID =? AND STATUS='A'")
	.append(" WITH UR").toString();

	
	public static final String COUNT_Business_User_CODE = new StringBuffer(500) 
	.append("SELECT COUNT(*) FROM  ")
	.append(SQLConstants.DISTPORTAL_SCHEMA)
	.append(".V_BUSINESS_USER_DATA WHERE CIRCLE_CODE  = ? AND USER_CODE  LIKE ? AND MASTER_ID =? AND STATUS  = 'A'")
	.append(" WITH UR").toString();
	
	public static final String COUNT_Business_User_CODE_CEO = new StringBuffer(500) 
	.append("SELECT COUNT(*) FROM  ")
	.append(SQLConstants.DISTPORTAL_SCHEMA)
	.append(".V_BUSINESS_USER_DATA WHERE USER_CODE  LIKE ? AND MASTER_ID =? AND STATUS  = 'A'")
	.append(" WITH UR").toString();

		
	public static final String COUNT_Business_User_NAME = new StringBuffer(500) 
	.append("SELECT COUNT(*)FROM  ")
	.append(SQLConstants.DISTPORTAL_SCHEMA)
	.append(".V_BUSINESS_USER_DATA WHERE CIRCLE_CODE  = ? AND USER_NAME LIKE ? AND MASTER_ID =? AND STATUS  = 'A'")
	.append(" WITH UR").toString();
	
	public static final String COUNT_Business_User_NAME_CEO = new StringBuffer(500) 
	.append("SELECT COUNT(*)FROM  ")
	.append(SQLConstants.DISTPORTAL_SCHEMA)
	.append(".V_BUSINESS_USER_DATA WHERE USER_NAME LIKE ? AND MASTER_ID =? AND STATUS  = 'A'")
	.append(" WITH UR").toString();
	
	public final String Business_User_DATA = new StringBuffer(500) 
	.append("SELECT * from(select a.*,ROW_NUMber() over() rnum FROM (SELECT  USER_NAME ,")
	.append(" DATA_ID,USER_CODE,CIRCLE_CODE from ")
	.append(SQLConstants.DISTPORTAL_SCHEMA)
	.append(".V_BUSINESS_USER_DATA ")
	.append(" WHERE CIRCLE_CODE=? AND MASTER_ID =? AND STATUS='A' ORDER BY UPPER(USER_CODE) ) a)b Where rnum<=? and  rnum>=?")
	.append(" WITH UR").toString();
	
	public final String Business_User_DATA_CEO = new StringBuffer(500) 
	.append("SELECT * from(select a.*,ROW_NUMber() over() rnum FROM (SELECT  USER_NAME ,")
	.append(" DATA_ID,USER_CODE,CIRCLE_CODE from ")
	.append(SQLConstants.DISTPORTAL_SCHEMA)
	.append(".V_BUSINESS_USER_DATA ")
	.append(" WHERE MASTER_ID =? AND STATUS='A' ORDER BY UPPER(USER_CODE) ) a)b Where rnum<=? and  rnum>=?")
	.append(" WITH UR").toString();

	
	public final String Business_User_DATA_CODE = new StringBuffer(500) 
	.append(" SELECT * from(select a.*,ROW_NUMBER() OVER() rnum FROM (SELECT  USER_NAME ,")
	.append(" DATA_ID ,USER_CODE ,CIRCLE_CODE from ")
	.append(SQLConstants.DISTPORTAL_SCHEMA)
	.append(".V_BUSINESS_USER_DATA")
	.append(" WHERE USER_CODE LIKE ? AND MASTER_ID =? AND CIRCLE_CODE=? AND STATUS='A' ORDER BY UPPER(USER_CODE) ) a)b")
	.append(" Where rnum<=? and rnum>=?")
	.append(" WITH UR").toString();
	
	public final String Business_User_DATA_CODE_CEO = new StringBuffer(500) 
	.append(" SELECT * from(select a.*,ROW_NUMBER() OVER() rnum FROM (SELECT  USER_NAME ,")
	.append(" DATA_ID ,USER_CODE ,CIRCLE_CODE from ")
	.append(SQLConstants.DISTPORTAL_SCHEMA)
	.append(".V_BUSINESS_USER_DATA")
	.append(" WHERE USER_CODE LIKE ? AND MASTER_ID =? AND STATUS='A' ORDER BY UPPER(USER_CODE) ) a)b")
	.append(" Where rnum<=? and rnum>=?")
	.append(" WITH UR").toString();
		
		
	public final String Business_User_DATA_NAME = new StringBuffer(500) 
	.append(" SELECT * from(select a.*,ROW_NUMBER() OVER() rnum FROM (SELECT  USER_NAME ,")
	.append(" DATA_ID,USER_CODE,CIRCLE_CODE from ")
	.append(SQLConstants.DISTPORTAL_SCHEMA)
	.append(".V_BUSINESS_USER_DATA")
	.append(" WHERE USER_NAME LIKE ? AND PARENT_ID =? AND CIRCLE_CODE=? AND STATUS='A' ORDER BY UPPER(USER_CODE) ) a)b")
	.append(" Where rnum<=? and rnum>=?")
	.append(" WITH UR").toString();
	
	public final String Business_User_DATA_NAME_CEO = new StringBuffer(500) 
	.append(" SELECT * from(select a.*,ROW_NUMBER() OVER() rnum FROM (SELECT  USER_NAME ,")
	.append(" DATA_ID,USER_CODE,CIRCLE_CODE from ")
	.append(SQLConstants.DISTPORTAL_SCHEMA)
	.append(".V_BUSINESS_USER_DATA")
	.append(" WHERE USER_NAME LIKE ? AND PARENT_ID =? AND STATUS='A' ORDER BY UPPER(USER_CODE) ) a)b")
	.append(" Where rnum<=? and rnum>=?")
	.append(" WITH UR").toString();

	public final String COUNT_MAPPED_Business_User = new StringBuffer(500) 
	.append("select count(*) from ")
	.append(SQLConstants.DISTPORTAL_SCHEMA)
	.append(".V_BUSINESS_USER_DATA where PARENT_ID=? AND STATUS=?")
	.append(" WITH UR").toString();

	
	public static final String LIST_MAPPED_Business_User = new StringBuffer(500) 
	.append("SELECT * from(select a.*,ROW_NUMber() over() rnum FROM (SELECT * ")
	.append(" from ")
	.append(SQLConstants.DISTPORTAL_SCHEMA)
	.append(".V_BUSINESS_USER_DATA where PARENT_ID = ? AND STATUS=? ORDER BY USER_NAME ) a )b")
	.append(" Where rnum<=? and rnum>=?")
	.append(" WITH UR").toString();

	public static final String GET_MAPPED_Business_User_INFO = new StringBuffer(500) 
	.append(" SELECT A.DATA_ID,A.USER_NAME,A.USER_CODE,A.CONTACT_NO,A.CIRCLE_CODE,B.USER_NAME,B.DATA_ID FROM ")
	.append(SQLConstants.DISTPORTAL_SCHEMA)
	.append(".V_BUSINESS_USER_DATA A,")
	.append(SQLConstants.DISTPORTAL_SCHEMA)
	.append(".V_BUSINESS_USER_DATA B ")
	.append(" WHERE A.PARENT_ID = B.DATA_ID AND A.DATA_ID=?")
	.append(" WITH UR").toString();

		
	public static final String UPDATE_MAPPING_Business_User = new StringBuffer(500) 
	.append(" UPDATE ")
	.append(SQLConstants.DISTPORTAL_SCHEMA)
	.append(".V_BUSINESS_USER_DATA SET PARENT_ID = ? WHERE DATA_ID = ?")
	.append(" WITH UR").toString();


	public static final String COUNT_UNMAPPED_Business_User = new StringBuffer(500) 
	.append("select count(*) FROM ")
	.append(SQLConstants.DISTPORTAL_SCHEMA)
	.append(".V_BUSINESS_USER_DATA WHERE PARENT_ID = ? AND MASTER_ID = ? ")
	.append(" AND CIRCLE_CODE=? AND STATUS='A'")
	.append(" WITH UR").toString();
	
	
	public static final String COUNT_UNMAPPED_CEO_ED = new StringBuffer(500) 
	.append("select count(*) FROM ")
	.append(SQLConstants.DISTPORTAL_SCHEMA)
	.append(".V_BUSINESS_USER_DATA WHERE PARENT_ID = ? AND MASTER_ID = ? ")
	.append(" AND STATUS=?")
	.append(" WITH UR").toString();


	
	public static final String SELECT_UNMAPPED_Business_User = new StringBuffer(500) 
	.append(" SELECT * from(select a.*,ROW_NUMber() over() rnum FROM ")
	.append(" (  SELECT  USER_NAME ,USER_CODE,DATA_ID,CIRCLE_CODE ")
	.append(" from ")
	.append(SQLConstants.DISTPORTAL_SCHEMA)
	.append(".V_BUSINESS_USER_DATA ")
	.append(" WHERE PARENT_ID = ? AND MASTER_ID = ? AND CIRCLE_CODE=? AND STATUS='A'")
	.append(" ORDER BY UPPER(USER_CODE) ) a)b ")
	.append(" Where rnum<=? and  rnum>=? ")
	.append(" WITH UR").toString();
	
	public static final String SELECT_UNMAPPED_CEO_ED = new StringBuffer(500) 
	.append(" SELECT * from(select a.*,ROW_NUMber() over() rnum FROM ")
	.append(" (  SELECT  USER_NAME ,USER_CODE,DATA_ID,CIRCLE_CODE ")
	.append(" from ")
	.append(SQLConstants.DISTPORTAL_SCHEMA)
	.append(".V_BUSINESS_USER_DATA ")
	.append(" WHERE PARENT_ID = ? AND MASTER_ID = ? AND STATUS='A'")
	.append(" ORDER BY UPPER(USER_CODE) ) a)b ")
	.append(" Where rnum<=? and  rnum>=? ")
	.append(" WITH UR").toString();

		
	public static final String INSERT_Business_User_MAPPING = new StringBuffer(500) 
	.append("UPDATE ")
	.append(SQLConstants.DISTPORTAL_SCHEMA)
	.append(".V_BUSINESS_USER_DATA set PARENT_ID =? where DATA_ID = ?")
	.append(" WITH UR").toString();
	
	
	
	// new quries
	public static final String Bussiness_data = new StringBuffer(500) 
	.append("SELECT * FROM ")
	.append(SQLConstants.DISTPORTAL_SCHEMA)
	.append(".V_BUSINESS_USER_DATA WHERE DATA_ID = ? ")
	.append(" WITH UR").toString();
	
	public static final String Number_unmapped_users = new StringBuffer(500) 
	.append("SELECT count(*) FROM ")
	.append(SQLConstants.DISTPORTAL_SCHEMA)
	.append(".V_BUSINESS_USER_DATA WHERE PARENT_ID = ? AND MASTER_ID = ? AND STATUS = ? ").toString();
	
	public static final String List_unmapped_users = new StringBuffer(500) 
	.append("SELECT * from (select a.*,ROW_NUMber() over() rnum FROM (SELECT * FROM ")
	.append(SQLConstants.DISTPORTAL_SCHEMA)
	.append(".V_BUSINESS_USER_DATA WHERE PARENT_ID = ? AND MASTER_ID = ? AND STATUS = ? ").toString();
	
	public static final String Number_of_parents = new StringBuffer(500) 
	.append("SELECT count(*) FROM ")
	.append(SQLConstants.DISTPORTAL_SCHEMA)
	.append(".V_BUSINESS_USER_DATA WHERE MASTER_ID = ? AND STATUS = ? ").toString();
	
	public static final String List_of_parents = new StringBuffer(500) 
	.append("SELECT * from (select a.*,ROW_NUMber() over() rnum FROM (SELECT * FROM ")
	.append(SQLConstants.DISTPORTAL_SCHEMA)
	.append(".V_BUSINESS_USER_DATA WHERE MASTER_ID = ? AND STATUS = ? ").toString();
	
	

}
