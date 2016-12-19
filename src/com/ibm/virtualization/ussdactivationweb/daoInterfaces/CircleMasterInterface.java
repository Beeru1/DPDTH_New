package com.ibm.virtualization.ussdactivationweb.daoInterfaces;

import com.ibm.virtualization.ussdactivationweb.utils.SQLConstants;

public interface CircleMasterInterface {
	/***************************************db2 queries****************************************************/
	public static final String SQL_COUNT_CIRCLE = new StringBuffer(500)
	.append("SELECT COUNT(*) FROM ")
	.append(SQLConstants.PRODCAT_SCHEMA)
	.append(".PC_CIRCLE_MSTR ")
	.append(" WITH UR").toString();

	public static final String SQL_SELECT_CIRCLE = new StringBuffer(500)
			.append("SELECT cm.CIRCLE_CODE, cm.CIRCLE_NAME,CONCAT(CHAR(hm.HUB_CODE),CONCAT('--',CHAR(hm.HUB_NAME))) AS HUB_CODE,")
			.append(" cm.STATUS, cm.CREATED_DT, ")
			.append("cm.CREATED_BY, cm.MODIFIED_DT , cm.MODIFIED_BY , cm.MINSAT_CHECK_REQ, cm.AEPF_CHECK_REQ , cm.WELCOME_MSG ,cm.SIM_REQ_CHECK,cm.RELEASE_TIME_IN_HRS FROM ")
			.append(SQLConstants.PRODCAT_SCHEMA)
			.append(".PC_CIRCLE_MSTR cm,")
			.append(SQLConstants.PRODCAT_SCHEMA)
			.append(".PC_HUB_MSTR hm ").toString();
	
	public static final String HUBCODE_OF_CIRCLE = new StringBuffer(500)
	.append("SELECT CIRCLE_CODE, CIRCLE_NAME,HUB_CODE FROM ")
	.append(SQLConstants.PRODCAT_SCHEMA)
	.append(".PC_CIRCLE_MSTR WHERE CIRCLE_CODE = ? ")
	.toString();

	public static final String SQL_INSERT_CIRCLE = new StringBuffer(500)
			.append("INSERT INTO ")
			.append(SQLConstants.PRODCAT_SCHEMA)
			.append(".PC_CIRCLE_MSTR(CIRCLE_ID,CIRCLE_CODE, CIRCLE_NAME,HUB_CODE, STATUS, CREATED_DT, ")
			.append(" CREATED_BY, MODIFIED_DT , MODIFIED_BY , MINSAT_CHECK_REQ, AEPF_CHECK_REQ ,WELCOME_MSG,SIM_REQ_CHECK,RELEASE_TIME_IN_HRS) ")
			.append(" VALUES (NEXT VALUE FOR PRODCAT.PC_CIRCLE_ID_SEQ, ?, ?, ? ,?, current timestamp, ?, current timestamp, ?, ?, ?,?,?,?) ")
			.append(" WITH UR").toString();

	public static final String SQL_CIRCLE_UPDATE_WITH_ID = new StringBuffer(500)
			.append("UPDATE ")
			.append(SQLConstants.PRODCAT_SCHEMA)
			.append(".PC_CIRCLE_MSTR SET CIRCLE_NAME =?,MODIFIED_DT=current timestamp,")
			.append("MODIFIED_BY=?,STATUS=?, MINSAT_CHECK_REQ=?, AEPF_CHECK_REQ=?, WELCOME_MSG=?, SIM_REQ_CHECK=? , RELEASE_TIME_IN_HRS=? WHERE CIRCLE_CODE=? ")
			.append(" WITH UR").toString();

	public static final String SQL_CIRCLE_CHECK = new StringBuffer(500)
	.append(" SELECT CIRCLE_CODE, CIRCLE_NAME,HUB_CODE, STATUS, CREATED_DT, ")
	.append("CREATED_BY, MODIFIED_DT , MODIFIED_BY , MINSAT_CHECK_REQ, AEPF_CHECK_REQ FROM ")
	.append(SQLConstants.PRODCAT_SCHEMA)
	.append(".PC_CIRCLE_MSTR ")
	.append(" WHERE upper(CIRCLE_NAME) = ? and CIRCLE_CODE<>? ")
	.append(" WITH UR").toString();

	public static final String SQL_DELETE_CIRCLE = new StringBuffer(500)
			.append("DELETE FROM ")
			.append(SQLConstants.PRODCAT_SCHEMA)
			.append(".PC_CIRCLE_MSTR WHERE CIRCLE_CODE = ? ")
			.append("WITH UR").toString();

	public static final String CIRCLE_LIST = new StringBuffer(500)
			.append("SELECT * from (select a.*,ROW_NUMber() over() rnum FROM (SELECT cm.CIRCLE_CODE,CONCAT(CHAR(hm.HUB_CODE),CONCAT('--',CHAR(hm.HUB_NAME))) AS HUB,cm.CIRCLE_NAME, cm.STATUS,cm.CREATED_DT, ")
			.append("cm.CREATED_BY,cm.MODIFIED_DT,cm.MODIFIED_BY FROM ")
			.append(SQLConstants.PRODCAT_SCHEMA)
			.append(".PC_HUB_MSTR hm,")
			.append(SQLConstants.PRODCAT_SCHEMA)
			.append(".PC_CIRCLE_MSTR cm WHERE hm.HUB_CODE = cm.HUB_CODE ORDER BY cm.CIRCLE_NAME ) a )b ")
			.append("Where rnum<=? and rnum>=? ")
			.append(" WITH UR").toString();
	
	public static final String AEPF_CHECK = new StringBuffer(500)
	.append("SELECT AEPF_CHECK_REQ FROM ")
	.append(SQLConstants.PRODCAT_SCHEMA)
	.append(".PC_CIRCLE_MSTR WHERE CIRCLE_CODE=? ").
	append(" WITH UR").toString();

	public static final String GET_HUB_LIST = new StringBuffer(500)
	.append("SELECT HUB_CODE,CONCAT(CHAR(HUB_CODE),CONCAT('--',CHAR(HUB_NAME))) AS HUB_NAME FROM ")
	.append(SQLConstants.PRODCAT_SCHEMA)
	.append(".PC_HUB_MSTR ")
	.append("WITH UR").toString();

	public static final String GET_ALL_CIRCLES = new StringBuffer(500)
	.append("SELECT CIRCLE_CODE,CIRCLE_NAME FROM ")
	.append(SQLConstants.PRODCAT_SCHEMA)
	.append(".PC_CIRCLE_MSTR ")
	.toString();
	
	
	public static final String GET_ALL_CITY = new StringBuffer(500)
	.append("SELECT LOCATION_NAME FROM ")
	.append(SQLConstants.PRODCAT_SCHEMA)
	.append(".PC_LOCTION_DATA WHERE LOC_MSTR_ID=3 ")
	.toString();
	public static final String RETRIEVE_LOC_MSTR_DETAILS = new StringBuffer(500)
	.append("SELECT LOC_MSTR_ID,LOCATION FROM ")
	.append(SQLConstants.PRODCAT_SCHEMA)
	.append(".PC_LOCATION_MSTR WHERE STATUS = ? AND SPECIAL_FTA_CHECK = 'Y' ")
	.toString();
	
	public static final String CREATE_LOC_DATA = new StringBuffer(500)
	.append("INSERT INTO ") 
	.append(SQLConstants.PRODCAT_SCHEMA)
	.append(".PC_LOCATION_DATA(LOC_DATA_ID, LOCATION_NAME, STATUS, LOC_MSTR_ID, ")
	.append("PARENT_ID, CREATED_DT, CREATED_BY, MODIFIED_DT, MODIFIED_BY, LOC_DATA_CODE)")
	.append("VALUES(?,?,?,?,?,CURRENT TIMESTAMP,?,?,?,? ) ").toString();
	
	
	public static final String RETRIEVE_LOCATION_LIST_ACTIVE= new StringBuffer(500)
	.append("SELECT LOC_DATA_ID,LOCATION_NAME,STATUS,CREATED_BY,CREATED_DT,MODIFIED_BY,MODIFIED_DT FROM ")
	.append(SQLConstants.PRODCAT_SCHEMA)
	.append(".PC_LOCATION_DATA WHERE LOC_MSTR_ID = ? AND PARENT_ID = ? AND STATUS = ? ORDER BY UPPER (LOCATION_NAME) ")
	.toString();
	
	public static final String RETRIEVE_LOCATION_LIST_ACTIVE_ZONE= new StringBuffer(500)
	.append("SELECT ZONE_ID LOC_DATA_ID,ZONE_NAME LOCATION_NAME,STATUS,CREATED_BY,CREATED_DT,UPDATED_BY MODIFIED_BY,UPDATED_DT MODIFIED_DT FROM ")
	//.append(SQLConstants.PRODCAT_SCHEMA)
	.append("DP_ZONE_MASTER WHERE CIRCLE_ID = ? AND STATUS = ? ORDER BY UPPER (ZONE_NAME) with ur")
	.toString();
	
	public static final String RETRIEVE_LOCATION_LIST_ACTIVE_CITY= new StringBuffer(500)
	.append("SELECT CITY_ID LOC_DATA_ID,CITY_NAME LOCATION_NAME,STATUS,UPDATED_DT MODIFIED_DT,UPDATED_BY MODIFIED_BY,CREATED_BY,CREATED_DT FROM ")
	//.append(SQLConstants.PRODCAT_SCHEMA)
	.append("VR_CITY_MASTER WHERE ZONE_ID = ? AND STATUS = ? ORDER BY UPPER (CITY_NAME) ")
	.toString();	
	
	public static final String RETRIEVE_LOCATION_LIST= new StringBuffer(500)
	.append("SELECT LOC_DATA_ID,LOCATION_NAME,STATUS,CREATED_BY,CREATED_DT,MODIFIED_BY,MODIFIED_DT FROM ")
	.append(SQLConstants.PRODCAT_SCHEMA)
	.append(".PC_LOCATION_DATA WHERE LOC_MSTR_ID = ? AND PARENT_ID = ?")
	.toString();
	
	public static final String RETRIEVE_LOCATION_LIST_PAGE= new StringBuffer(500)
	.append("SELECT * from (select a.*,ROW_NUMber() over() rnum FROM (SELECT LOC_DATA_ID,LOCATION_NAME,STATUS,CREATED_BY,CREATED_DT,MODIFIED_BY,MODIFIED_DT FROM ")
	.append(SQLConstants.PRODCAT_SCHEMA)
	.append(".PC_LOCATION_DATA WHERE LOC_MSTR_ID = ? AND PARENT_ID = ? ORDER BY UPPER(LOCATION_NAME)) a )b ")
	.append("Where rnum<=? and rnum>=? ")
	.toString();
	
	public static final String RETRIEVE_LOCATION_LIST_PAGE_ZONE= new StringBuffer(500)
	.append("SELECT * from (select a.*,ROW_NUMber() over() rnum FROM (SELECT ZONE_ID LOC_DATA_ID,ZONE_NAME LOCATION_NAME,STATUS,CREATED_BY,CREATED_DT,UPDATED_BY MODIFIED_BY,UPDATED_DT MODIFIED_DT FROM ")
	//.append(SQLConstants.PRODCAT_SCHEMA)
	.append("DP_ZONE_MASTER WHERE CIRCLE_ID = ? ORDER BY UPPER(ZONE_NAME)) a )b ")
	.append("Where rnum<=? and rnum>=? ")
	.toString();
	
	public static final String RETRIEVE_LOCATION_LIST_PAGE_CITY= new StringBuffer(500)
	.append("SELECT * from (select a.*,ROW_NUMber() over() rnum FROM (SELECT CITY_ID LOC_DATA_ID,CITY_NAME LOCATION_NAME,STATUS,CREATED_BY,CREATED_DT,UPDATED_BY MODIFIED_BY,UPDATED_DT MODIFIED_DT FROM ")
	//.append(SQLConstants.PRODCAT_SCHEMA)
	.append("VR_CITY_MASTER WHERE ZONE_ID = ? ORDER BY UPPER(CITY_NAME)) a )b ")
	.append("Where rnum<=? and rnum>=? ")
	.toString();
	
	public static final String COUNT_LOCATION_LIST= new StringBuffer(500)
	.append("SELECT COUNT(*) FROM ")
	.append(SQLConstants.PRODCAT_SCHEMA)
	.append(".PC_LOCATION_DATA WHERE LOC_MSTR_ID = ? AND PARENT_ID = ?")
	.toString();
	
	public static final String RETRIEVE_LOCATION_DETAILS = new StringBuffer(500)
	.append("SELECT LOCATION_NAME,PARENT_ID,STATUS,CREATED_BY,CREATED_DT,MODIFIED_BY,MODIFIED_DT,LOC_MSTR_ID FROM ")
	.append(SQLConstants.PRODCAT_SCHEMA)
	.append(".PC_LOCATION_DATA WHERE LOC_DATA_ID = ?")
	.toString();
	
	public static final  String GET_NEXT_LOC_DATA_ID= new StringBuffer(500)
	.append("VALUES NEXTVAL FOR ").append(SQLConstants.PRODCAT_SCHEMA).append(".PC_LOCATION_DATA_ID_SEQ").toString();
	
	public static final  String UPDATE_LOCATION_DETAILS =new StringBuffer(500)
	  .append("UPDATE ")
	  .append(SQLConstants.PRODCAT_SCHEMA)
	  .append(".PC_LOCATION_DATA SET LOCATION_NAME= ?,STATUS= ?,MODIFIED_BY =?,MODIFIED_DT= CURRENT TIMESTAMP " )
	  .append("WHERE LOC_DATA_ID=?").toString();
	
	public static final  String VALIDATE_LOCATION_NAME=new StringBuffer(500)
	 .append("SELECT LOCATION_NAME , PARENT_ID FROM ")
	 .append(SQLConstants.PRODCAT_SCHEMA)
	 .append(".PC_LOCATION_DATA WHERE LOWER(trim(LOCATION_NAME))= ? AND PARENT_ID=(SELECT PARENT_ID FROM ")
	 .append(SQLConstants.PRODCAT_SCHEMA)
	 .append(".PC_LOCATION_DATA WHERE LOC_DATA_ID = ?) ")
	 .append("WITH UR").toString();
	
	public static final  String VALIDATE_LOCATION_NAME_PARENT_ID=new StringBuffer(500)
	 .append("SELECT LOCATION_NAME , PARENT_ID FROM ")
	 .append(SQLConstants.PRODCAT_SCHEMA)
	 .append(".PC_LOCATION_DATA WHERE LOWER(trim(LOCATION_NAME))= ? AND PARENT_ID= ? ")
	 
	 .append("WITH UR").toString();

	public static final String GET_CHILD_OF_CIRCLES = new StringBuffer(500)
	 .append("WITH temptab(LOC_DATA_ID,LOCATION_NAME,STATUS,LOC_MSTR_ID,PARENT_ID) AS ")
	.append("( ")
	 .append("Select root.LOC_DATA_ID AS LOC_DATA_ID, root.LOCATION_NAME AS LOCATION_NAME, root.STATUS AS STATUS, ")
	.append("root.LOC_MSTR_ID AS LOC_MSTR_ID, root.PARENT_ID AS PARENT_ID ")
	.append("from ")
	 .append(SQLConstants.PRODCAT_SCHEMA)
	 .append(".PC_LOCATION_DATA root ")
	.append("where root.PARENT_ID = ? ")
	.append("UNION ALL ")
	.append("Select sub.LOC_DATA_ID AS LOC_DATA_ID, sub.LOCATION_NAME AS LOCATION_NAME, sub.STATUS AS STATUS, ") 
	.append("sub.LOC_MSTR_ID AS LOC_MSTR_ID, sub.PARENT_ID AS PARENT_ID ")
	.append("from ")
	 .append(SQLConstants.PRODCAT_SCHEMA)
	.append(".PC_LOCATION_DATA  sub, temptab super ")
		.append("WHERE  sub.PARENT_ID  =  CHAR(super.LOC_DATA_ID) ")
	 .append(") ")
	.append("SELECT * from temptab ")
	 .append("WITH UR").toString();
	
	public static final String UPDATE_LOCATION_STATUS = new StringBuffer(500)
	.append("UPDATE ")
	.append(SQLConstants.PRODCAT_SCHEMA)
	.append(".PC_LOCATION_DATA ") 
	.append("SET STATUS = ? " )
	.append("where LOC_DATA_ID IN (? ) " ).toString();
	
	public static final String UPDATE_PARENT_ID = new StringBuffer(500)
	.append("UPDATE ")
	.append(SQLConstants.PRODCAT_SCHEMA)
	.append(".PC_LOCATION_DATA ") 
	.append("SET PARENT_ID = ? " )
	.append("where LOC_DATA_ID IN (? ) " ).toString();

	public static final String UPDATE_CHILD_OF_CIRCLES = new StringBuffer(500)
	  .append("UPDATE ")
	  .append(SQLConstants.PRODCAT_SCHEMA)
	  .append(".PC_LOCATION_DATA SET STATUS = ? ")
	  .append("WHERE LOC_DATA_ID IN (<CIRCLECHILD>)").toString();
	
	public static final String UPDATE_CHILD_OF_CIRCLE = new StringBuffer(500)
	  .append("UPDATE ")
	  .append(SQLConstants.PRODCAT_SCHEMA)
	  .append(".PC_LOCATION_DATA SET PARENT_ID = ? ")
	  .append("WHERE PARENT_ID = ?").toString();
	
	
	public static final String GET_LOCATION_HIERARCHIAL = new StringBuffer(500)
	.append("WITH temptab(LOC_DATA_ID,LOCATION_NAME) AS ") 
	.append("( ")
	.append("Select  root.LOC_DATA_ID AS LOC_DATA_ID , root.LOCATION_NAME AS LOCATION_NAME ") 
	.append(" from ")
	.append(SQLConstants.PRODCAT_SCHEMA)
	.append(".PC_LOCATION_DATA root ") 
	.append(" where root.LOC_DATA_ID = ? AND root.STATUS = ?")
	.append(" UNION ALL ") 
	.append(" Select sub.LOC_DATA_ID AS LOC_DATA_ID  , sub.LOCATION_NAME AS LOCATION_NAME")
	.append(" from ")
	.append(SQLConstants.PRODCAT_SCHEMA)
	.append(".PC_LOCATION_DATA  sub, temptab super ")
	.append(" WHERE sub.PARENT_ID = CHAR(super.LOC_DATA_ID) ") 
	.append(")")
	.append(" SELECT * from temptab ")
	.append(" WITH UR ").toString();
	
	public static final String RETRIEVE_LOC_DETAILS = new StringBuffer(500)
	  .append("SELECT LD.LOCATION_NAME FROM")
	  .append(SQLConstants.PRODCAT_SCHEMA)
	  .append(".PC_LOCATION_DATA LD,")
	   .append(SQLConstants.PRODCAT_SCHEMA)
	  .append(".PC_LOCATION_MSTR LM")
	  .append("WHERE LD.LOC_DATA_ID = ? AND LM.LOCATION =? AND LD.STATUS =?").toString();
	
	/****************************************oracle queries************************************************/
	
}
