/**
 * 
 */
package com.ibm.virtualization.ussdactivationweb.daoInterfaces;

import com.ibm.virtualization.ussdactivationweb.utils.SQLConstants;

/**
 * @author Banita
 *
 */
public interface ProdcatInterface {
	
	/***************************************db2 queries****************************************************/
	
	public static final String RETRIEVE_CIRCLE_DETAILS = new StringBuffer(500)
	.append("SELECT cm.CIRCLE_CODE,cm.CIRCLE_NAME,cm.HUB_CODE,cm.STATUS,")
	.append("cm.CREATED_DT,cm.CREATED_BY,cm.MODIFIED_DT,cm.MODIFIED_BY,")
	.append("cm.MINSAT_CHECK_REQ,cm.AEPF_CHECK_REQ,hm.HUB_NAME,cm.WELCOME_MSG,cm.SIM_REQ_CHECK FROM ")
	.append(SQLConstants.PRODCAT_SCHEMA)
	.append(".PC_CIRCLE_MSTR cm,")
	.append(SQLConstants.PRODCAT_SCHEMA)
	.append(".PC_HUB_MSTR hm ")
	.append("WHERE cm.HUB_CODE = hm.HUB_CODE AND cm.CIRCLE_CODE = ? AND cm.STATUS=?")
	.append(" WITH UR").toString();
	
	public static final String RETRIEVE_SERVICECLASS_DETAILS = new StringBuffer(500)
	.append("SELECT sm.SERVICECLASS_ID,sm.SERVICECLASS_NAME,sm.CIRCLE_CODE,sm.STATUS,")
	.append("sm.SPECIAL_SC_CHECK,sm.CATEGORY_ID,")
	.append("sm.CREATED_BY,sm.CREATED_DT,sm.MODIFIED_BY,sm.MODIFIED_DT,sm.TARIFF_MESSAGE,scm.CATEGORY_DESC,scm.MED_CMD_FTA,scm.MED_RCB_FTA FROM ")
	.append(SQLConstants.PRODCAT_SCHEMA)
	.append(".PC_SERVICECLASS_MSTR sm,")
	.append(SQLConstants.PRODCAT_SCHEMA)
	.append(".PC_SC_CATEGORY_MSTR scm ")
	.append("WHERE sm.CATEGORY_ID = scm.CATEGORY_ID AND sm.CIRCLE_CODE = ? AND sm.SERVICECLASS_ID = ? AND sm.STATUS=?")
	.append(" WITH UR").toString();
	
	public static final String RETRIEVE_LOCATION_DETAILS = new StringBuffer(500)
	.append("SELECT ld.LOC_DATA_ID,ld.LOCATION_NAME,ld.STATUS,ld.LOC_MSTR_ID,ld.PARENT_ID,")
	.append("ld.CREATED_DT,ld.CREATED_BY,ld.MODIFIED_DT,ld.MODIFIED_BY,ld.LOC_DATA_CODE,lm.LOCATION FROM ")
	.append(SQLConstants.PRODCAT_SCHEMA)
	.append(".PC_LOCATION_DATA ld ,")
	.append(SQLConstants.PRODCAT_SCHEMA)
	.append(".PC_LOCATION_MSTR lm ")
	.append("WHERE ld.LOC_MSTR_ID=lm.LOC_MSTR_ID AND ld.LOC_DATA_ID=? ")
	.append(" WITH UR").toString();
	
	
	public static final String RETRIEVE_LOCATION_STATUS = new StringBuffer(500)
	.append("WITH temptab(LOC_DATA_ID,STATUS,PARENT_ID) AS ") 
	.append("( ")
	.append("Select  root.LOC_DATA_ID AS LOC_DATA_ID,root.STATUS AS STATUS, root.PARENT_ID AS PARENT_ID ") 
	.append(" from ")
	.append(SQLConstants.PRODCAT_SCHEMA)
	.append(".PC_LOCATION_DATA root ") 
	.append(" where root.LOC_DATA_ID = ? ")
	.append(" UNION ALL ") 
	.append(" Select sub.LOC_DATA_ID AS LOC_DATA_ID, sub.STATUS AS STATUS,sub.PARENT_ID AS PARENT_ID ")
	.append(" from ")
	.append(SQLConstants.PRODCAT_SCHEMA)
	.append(".PC_LOCATION_DATA  sub, temptab super ")
	.append(" WHERE CHAR(sub.LOC_DATA_ID) =  super.PARENT_ID ") 
	.append(")")
	.append(" SELECT * from temptab ")
	.append(" WITH UR ").toString();
	

	public static final String RETRIEVE_SERVICECLASS = new StringBuffer(500)
	.append("SELECT SERVICECLASS_ID FROM ")
	.append(SQLConstants.PRODCAT_SCHEMA)
	.append(".PC_SERVICECLASS_MSTR WHERE CATEGORY_ID=? AND STATUS=?")
	.append("WITH UR ").toString();
	
	public static final String RETRIEVE_MSISDN = new StringBuffer(500)
	.append("SELECT sm.MSISDN FROM ")
	.append(SQLConstants.FTA_SCHEMA)
	.append(".V_SUBSCRIBER_MSTR sm WHERE sm.SERVICECLASS_ID=? AND sm.STATUS=?")
	.append("WITH UR ").toString();
	
	public static final String RETRIEVE_ACTIVATION_COUNT = new StringBuffer(500)
	.append("SELECT tm.SUBS_MSISDN ") 
	.append("FROM ")
	.append(SQLConstants.FTA_SCHEMA)
	.append(".V_TRANSACTION_MSTR tm ")
	.append("WHERE tm.SUBS_MSISDN=? AND tm.TRAN_TYPE_ID=? AND tm.STATUS=? ")	
	.append("WITH UR ").toString();
	
	
	public static final String SQL_LOC_COUNT = new StringBuffer(500)
	.append("SELECT COUNT(*) FROM ")
	.append(SQLConstants.PRODCAT_SCHEMA)
	.append(".PC_LOCATION_DATA  WHERE ((CREATED_DT)>=? AND (CREATED_DT)<=?)") 
	.append("OR ((MODIFIED_DT)>=? AND (MODIFIED_DT)<=?)")
	.append(" WITH UR").toString();
	
	

	
	public static final String RETRIEVE_LOCATION_DATA = new StringBuffer(500)
	.append("SELECT * from (select a.*,ROW_NUMber() over() rnum FROM (SELECT ld.LOC_DATA_ID,ld.LOCATION_NAME,ld.STATUS,ld.LOC_MSTR_ID,ld.PARENT_ID,lm.LOCATION,ld.CREATED_DT,ld.MODIFIED_DT FROM ")
	.append(SQLConstants.PRODCAT_SCHEMA)
	.append(".PC_LOCATION_DATA ld ,")
	.append(SQLConstants.PRODCAT_SCHEMA)
	.append(".PC_LOCATION_MSTR lm ")
	.append("WHERE ld.LOC_MSTR_ID=lm.LOC_MSTR_ID AND (((ld.CREATED_DT)>=? AND (ld.CREATED_DT)<=?)") 
	.append("OR ((ld.MODIFIED_DT)>=? AND (ld.MODIFIED_DT)<=?)) order by ld.LOC_MSTR_ID ) a )b ")
	.append("Where rnum<=? and rnum>=? ")
	.append(" WITH UR").toString();
	
	public static final String RETRIEVE_CIRCLE_DATA_COUNT = new StringBuffer(500)
	.append("SELECT count(cm.CIRCLE_CODE) AS CIRCLE_COUNT FROM ")
	.append(SQLConstants.PRODCAT_SCHEMA)
	.append(".PC_CIRCLE_MSTR cm, ")
	.append(SQLConstants.PRODCAT_SCHEMA)
	.append(".PC_LOCATION_MSTR lm ")
	.append("WHERE lm.LOC_MSTR_ID=? AND (((cm.CREATED_DT)>=? AND (cm.CREATED_DT)<=?)") 
	.append("OR ((cm.MODIFIED_DT)>=? AND (cm.MODIFIED_DT)<=?)) ")
	.append(" WITH UR").toString();
	
	
	public static final String RETRIEVE_CIRCLE_DATA = new StringBuffer(500)
	.append("SELECT cm.CIRCLE_CODE,cm.CIRCLE_NAME,cm.CREATED_DT,cm.MODIFIED_DT,lm.LOC_MSTR_ID,lm.LOCATION, cm.HUB_CODE AS PARENT_ID,cm.STATUS,lm.SPECIAL_FTA_CHECK FROM ")
	.append(SQLConstants.PRODCAT_SCHEMA)
	.append(".PC_CIRCLE_MSTR cm, ")
	.append(SQLConstants.PRODCAT_SCHEMA)
	.append(".PC_LOCATION_MSTR lm ")
	.append("WHERE lm.LOC_MSTR_ID=? AND (((cm.CREATED_DT)>=? AND (cm.CREATED_DT)<=?)") 
	.append("OR ((cm.MODIFIED_DT)>=? AND (cm.MODIFIED_DT)<=?)) ")
	.append(" ORDER BY lm.LOC_MSTR_ID ")
	.append(" WITH UR").toString();
	
	public static final String RETRIEVE_HUB_DATA = new StringBuffer(500)
	.append("SELECT cm.HUB_CODE ,cm.HUB_NAME,cm.CREATED_DT,cm.MODIFIED_DT,lm.LOC_MSTR_ID,lm.LOCATION,1 AS STATUS FROM ")
	.append(SQLConstants.PRODCAT_SCHEMA)
	.append(".PC_HUB_MSTR cm, ")
	.append(SQLConstants.PRODCAT_SCHEMA)
	.append(".PC_LOCATION_MSTR lm ")
	.append("WHERE lm.LOC_MSTR_ID=? AND (((cm.CREATED_DT)>=? AND (cm.CREATED_DT)<=?)") 
	.append("OR ((cm.MODIFIED_DT)>=? AND (cm.MODIFIED_DT)<=?))")
	.append(" WITH UR").toString();
	
	public static final String RETRIEVE_HUB_DATA_COUNT = new StringBuffer(500)
	.append("SELECT COUNT(cm.HUB_CODE) AS HUB_COUNT FROM ")
	.append(SQLConstants.PRODCAT_SCHEMA)
	.append(".PC_HUB_MSTR cm, ")
	.append(SQLConstants.PRODCAT_SCHEMA)
	.append(".PC_LOCATION_MSTR lm ")
	.append("WHERE lm.LOC_MSTR_ID=? AND (((cm.CREATED_DT)>=? AND (cm.CREATED_DT)<=?)") 
	.append("OR ((cm.MODIFIED_DT)>=? AND (cm.MODIFIED_DT)<=?))")
	.append(" WITH UR").toString();
	
	public static final String RETRIEVE_CIRCLE = new StringBuffer(500)
	.append("SELECT cm.CIRCLE_CODE FROM ")
	.append(SQLConstants.PRODCAT_SCHEMA)
	.append(".PC_CIRCLE_MSTR cm ")
	.append("WHERE cm.STATUS=? AND cm.CIRCLE_CODE=? ")
	.append("WITH UR ").toString();
	
	public static final String RETRIEVE_ACTIVE_CIRCLE_COUNT = new StringBuffer(500)
	.append("SELECT count(cm.CIRCLE_CODE) FROM ")
	.append(SQLConstants.PRODCAT_SCHEMA)
	.append(".PC_CIRCLE_MSTR cm ")
	.append("WHERE cm.STATUS=? ")
	.append("WITH UR ").toString();
	
	public static final String RETRIEVE_ACTIVE_CIRCLE = new StringBuffer(500)
	.append("SELECT cm.CIRCLE_CODE,cm.CIRCLE_NAME,cm.CIRCLE_ID,cm.RELEASE_TIME_IN_HRS FROM ")
	.append(SQLConstants.PRODCAT_SCHEMA)
	.append(".PC_CIRCLE_MSTR cm ")
	.append("WHERE cm.STATUS=? ")
	.append("WITH UR ").toString();
	
	
	public static final String RETRIEVE_LOCATION_NAME = new StringBuffer(500)
	.append("SELECT ld.LOCATION_NAME FROM ")
	.append(SQLConstants.PRODCAT_SCHEMA)
	.append(".PC_LOCATION_DATA ld,")
	.append(SQLConstants.PRODCAT_SCHEMA)
	.append(".PC_LOCATION_MSTR lm ")
	.append("WHERE ld.LOC_DATA_ID=? AND lm.LOCATION=?")
	.append(" WITH UR").toString();
	
	

	
	
}
