/**************************************************************************
 * File     : DistportalInterface.java
 * Author   : Banita
 * Created  : Oct 6, 2008
 * Modified : Oct 6, 2008
 * Version  : 0.1
 **************************************************************************
 *
 * Copyright @ 2002 This document has been prepared and written by 
 * IBM Global Services on behalf of Bharti, and is copyright of Bharti
 *
 **************************************************************************/
package com.ibm.virtualization.ussdactivationweb.daoInterfaces;

import com.ibm.virtualization.ussdactivationweb.utils.SQLConstants;

/*******************************************************************************
 * This class acts as interface for all distportal queries
 * 
 * @author Banita
 * @version 1.0
 ******************************************************************************/
public interface DistportalInterface {
	/***************************************db2 queries****************************************************/
	
	public static final String RETRIEVE_REQUESTER_ID = new StringBuffer(500)
	.append("SELECT rm.BUS_USER_ID FROM ")
	.append(SQLConstants.DISTPORTAL_SCHEMA)
	.append(".V_REGISTERED_NUMBER rm WHERE rm.MOBILE_NO=?")
	.append("WITH UR ").toString();
	
	public static final String RETRIEVE_TYPE = new StringBuffer(500)
	.append("SELECT bm.USER_TYPE,bm.BASE_LOC FROM ")
	.append(SQLConstants.DISTPORTAL_SCHEMA)
	.append(".V_BUSINESS_USER_MASTER bm WHERE bm.MASTER_ID=? ")
	.append("WITH UR ").toString();
	
	public static final String RETRIEVE_REQUESTER_DETAILS = new StringBuffer(500)
	.append("SELECT bu.DATA_ID,bu.USER_CODE,bu.USER_NAME,bu.MASTER_ID,bu.PARENT_ID, ")
	.append("bu.STATUS,bu.CONTACT_NO,bu.ADDRESS,bu.CIRCLE_CODE,bu.HUB_CODE, ")
	.append("bu.FOS_CHECK_REQ,bu.FOS_ACTV_CHK,bu.LOC_ID,bu.ALL_SERVICE_CLASS,bu.INCLUDE_SERVICE, ")
	.append("bu.EXCLUDE_SERVICE,bu.CREATED_BY,bu.CREATED_DT,bu.UPDATED_BY,bu.UPDATED_DT, ")
	.append("bm.USER_TYPE,bm.BASE_LOC,rn.MOBILE_NO,bu.IPHONE_ACT_RIGHTS FROM ")
	.append(SQLConstants.DISTPORTAL_SCHEMA)
	.append(".V_BUSINESS_USER_DATA bu , ")
	.append(SQLConstants.DISTPORTAL_SCHEMA)
	.append(".V_BUSINESS_USER_MASTER bm , ")
	.append(SQLConstants.DISTPORTAL_SCHEMA)
	.append(".V_REGISTERED_NUMBER rn  ")
	.append("WHERE bu.MASTER_ID=bm.MASTER_ID AND rn.BUS_USER_ID=bu.DATA_ID ")
	.append("AND rn.MOBILE_NO=? AND rn.STATUS=? AND bu.STATUS=? AND bm.USER_TYPE=?")
	.append("WITH UR ").toString();
	
	
	public static final String RETRIEVE_REQUESTER_DETAILS_SMS = new StringBuffer(500)
	.append("SELECT bu.DATA_ID,bu.USER_CODE,bu.USER_NAME,bu.MASTER_ID,bu.PARENT_ID, ")
	.append("bu.STATUS,bu.CONTACT_NO,bu.ADDRESS,bu.CIRCLE_CODE,bu.HUB_CODE, ")
	.append("bu.FOS_CHECK_REQ,bu.FOS_ACTV_CHK,bu.LOC_ID,bu.ALL_SERVICE_CLASS,bu.INCLUDE_SERVICE, ")
	.append("bu.EXCLUDE_SERVICE,bu.CREATED_BY,bu.CREATED_DT,bu.UPDATED_BY,bu.UPDATED_DT, ")
	.append("bm.USER_TYPE,bm.BASE_LOC,rn.MOBILE_NO FROM ")
	.append(SQLConstants.DISTPORTAL_SCHEMA)
	.append(".V_BUSINESS_USER_DATA bu , ")
	.append(SQLConstants.DISTPORTAL_SCHEMA)
	.append(".V_BUSINESS_USER_MASTER bm , ")
	.append(SQLConstants.DISTPORTAL_SCHEMA)
	.append(".V_REGISTERED_NUMBER rn  ")
	.append("WHERE bu.MASTER_ID=bm.MASTER_ID AND rn.BUS_USER_ID=bu.DATA_ID ")
	.append("AND rn.MOBILE_NO=? AND rn.STATUS=? AND bu.STATUS=?")
	.append("WITH UR ").toString();
	
	
		
	
	/**In this there are several conditions and on the basis only u get data:
		a)Register Number should be acitve
		b)Status of the user who has registered for should be active
		c)The location to which he belongs and all its parent heriarchies should be active
		d)Now you get heriarchical details only when its parent are in active state.
		e)The register number of parent should also be in active state else you will get details only till the user whose every field is active. .
		f)Parent's location should be in active state else you will get details only till the user whose every field is active. 
		g)Circle should also be in active state,if not no details will be fetch.

		Note:
		In rest all services you get data when they are active else not.*/
	public static final String RETRIEVE_COMPLETE_REQUESTER_DETAILS = new StringBuffer(500)
	.append("WITH temptab(DATA_ID , USER_CODE , USER_NAME , MASTER_ID , PARENT_ID , STATUS , CONTACT_NO ,ADDRESS ,")
	.append(" CIRCLE_CODE , HUB_CODE , FOS_CHECK_REQ,FOS_ACTV_CHK,LOC_ID,ALL_SERVICE_CLASS,INCLUDE_SERVICE,")
	.append("EXCLUDE_SERVICE,CREATED_BY,CREATED_DT,UPDATED_BY,UPDATED_DT,USER_TYPE,BASE_LOC,MOBILE_NO,IPHONE_ACT_RIGHTS) AS ")
	.append("(Select root.DATA_ID AS DATA_ID, root.USER_CODE AS USER_CODE, root.USER_NAME AS USER_NAME,") 
	.append("root.MASTER_ID AS MASTER_ID, root.PARENT_ID AS PARENT_ID, root.STATUS AS STATUS, root.CONTACT_NO AS CONTACT_NO,")
	.append("root.ADDRESS AS ADDRESS ,")
	.append("root.CIRCLE_CODE AS CIRCLE_CODE, root.HUB_CODE AS HUB_CODE, root.FOS_CHECK_REQ AS FOS_CHECK_REQ,")
	.append("root.FOS_ACTV_CHK AS FOS_ACTV_CHK,")
	.append("root.LOC_ID AS LOC_ID,root.ALL_SERVICE_CLASS AS ALL_SERVICE_CLASS,")
	.append("root.INCLUDE_SERVICE AS INCLUDE_SERVICE,")
	.append("root.EXCLUDE_SERVICE AS EXCLUDE_SERVICE,root.CREATED_BY AS CREATED_BY,")
	.append("root.CREATED_DT AS CREATED_DT,")
	.append("root.UPDATED_BY AS UPDATED_BY,root.UPDATED_DT AS UPDATED_DT ,usr_mstr.USER_TYPE as USER_TYPE,usr_mstr.BASE_LOC as BASE_LOC, reg_no.MOBILE_NO as MOBILE_NO ,root.IPHONE_ACT_RIGHTS as IPHONE_ACT_RIGHTS ")
	.append("FROM ")
	.append(SQLConstants.DISTPORTAL_SCHEMA)
	.append(".V_BUSINESS_USER_DATA root,")
	.append(SQLConstants.DISTPORTAL_SCHEMA)
	.append(".V_REGISTERED_NUMBER as reg_no,")
	.append(SQLConstants.DISTPORTAL_SCHEMA)
	 .append(".V_BUSINESS_USER_MASTER as usr_mstr ")
	.append("WHERE root.DATA_ID =reg_no.BUS_USER_ID AND  reg_no.MOBILE_NO=? AND usr_mstr.USER_TYPE=? AND root.STATUS=?  AND reg_no.STATUS=? AND root.MASTER_ID = usr_mstr.MASTER_ID ")
	.append("UNION ALL ")
	.append("Select sub.DATA_ID AS DATA_ID, sub.USER_CODE AS USER_CODE, sub.USER_NAME AS USER_NAME,") 
	.append("sub.MASTER_ID AS MASTER_ID, sub.PARENT_ID AS PARENT_ID, sub.STATUS AS STATUS, sub.CONTACT_NO AS CONTACT_NO,")
	.append("sub.ADDRESS AS ADDRESS ,")
	.append("sub.CIRCLE_CODE AS CIRCLE_CODE, sub.HUB_CODE AS HUB_CODE, sub.FOS_CHECK_REQ AS FOS_CHECK_REQ,")
	.append("sub.FOS_ACTV_CHK AS FOS_ACTV_CHK,")
	.append("sub.LOC_ID AS LOC_ID,sub.ALL_SERVICE_CLASS AS ALL_SERVICE_CLASS,")
	.append("sub.INCLUDE_SERVICE AS INCLUDE_SERVICE,")
	.append("sub.EXCLUDE_SERVICE AS EXCLUDE_SERVICE,sub.CREATED_BY AS CREATED_BY,")
	.append("sub.CREATED_DT AS CREATED_DT,")
	.append("sub.UPDATED_BY AS UPDATED_BY,sub.UPDATED_DT AS UPDATED_DT,usr_mstr.USER_TYPE as USER_TYPE,usr_mstr.BASE_LOC as BASE_LOC,reg_no.MOBILE_NO as MOBILE_NO,sub.IPHONE_ACT_RIGHTS as IPHONE_ACT_RIGHTS ")
	.append("FROM ")
	.append(SQLConstants.DISTPORTAL_SCHEMA) 
	.append(".V_BUSINESS_USER_DATA  sub, temptab super,")
	.append(SQLConstants.DISTPORTAL_SCHEMA)
	.append(".V_REGISTERED_NUMBER as reg_no,")
	.append(SQLConstants.DISTPORTAL_SCHEMA)
	.append(".V_BUSINESS_USER_MASTER as usr_mstr ")
	.append("WHERE super.PARENT_ID = sub.DATA_ID AND sub.STATUS=? AND reg_no.STATUS=? AND sub.MASTER_ID = usr_mstr.MASTER_ID  AND sub.DATA_ID = reg_no.BUS_USER_ID)")
	.append("SELECT * from temptab ")
	.append("WITH UR ").toString();

	
	
	/**In this there are several conditions and on the basis only u get data:
	a)Register Number should be acitve
	b)Status of the user who has registered for should be active
	c)The location to which he belongs and all its parent heriarchies should be active
	d)Now you get heriarchical details only when its parent are in active state.
	e)The register number of parent should also be in active state 
	f)Parent's location should be in active state else you will get details only till the user whose every field is active. 
	g)Circle should also be in active state,if not no details will be fetch.

	Note:
	In rest all services you get data when they are active else not.*/
	
//	public static final String RETRIEVE_COMPLETE_REQUESTER_DETAILS = new StringBuffer(500)
//	.append("WITH temptab(DATA_ID , USER_CODE , USER_NAME , MASTER_ID , PARENT_ID , STATUS , CONTACT_NO ,ADDRESS ,")
//	.append(" CIRCLE_CODE , HUB_CODE , FOS_CHECK_REQ,FOS_ACTV_CHK,LOC_ID,ALL_SERVICE_CLASS,INCLUDE_SERVICE,")
//	.append("EXCLUDE_SERVICE,CREATED_BY,CREATED_DT,UPDATED_BY,UPDATED_DT,USER_TYPE,BASE_LOC,MOBILE_NO,REGSTATUS) AS ")
//	.append("(Select root.DATA_ID AS DATA_ID, root.USER_CODE AS USER_CODE, root.USER_NAME AS USER_NAME,") 
//	.append("root.MASTER_ID AS MASTER_ID, root.PARENT_ID AS PARENT_ID, root.STATUS AS STATUS, root.CONTACT_NO AS CONTACT_NO,")
//	.append("root.ADDRESS AS ADDRESS ,")
//	.append("root.CIRCLE_CODE AS CIRCLE_CODE, root.HUB_CODE AS HUB_CODE, root.FOS_CHECK_REQ AS FOS_CHECK_REQ,")
//	.append("root.FOS_ACTV_CHK AS FOS_ACTV_CHK,")
//	.append("root.LOC_ID AS LOC_ID,root.ALL_SERVICE_CLASS AS ALL_SERVICE_CLASS,")
//	.append("root.INCLUDE_SERVICE AS INCLUDE_SERVICE,")
//	.append("root.EXCLUDE_SERVICE AS EXCLUDE_SERVICE,root.CREATED_BY AS CREATED_BY,")
//	.append("root.CREATED_DT AS CREATED_DT,")
//	.append("root.UPDATED_BY AS UPDATED_BY,root.UPDATED_DT AS UPDATED_DT ,usr_mstr.USER_TYPE as USER_TYPE,usr_mstr.BASE_LOC as BASE_LOC, reg_no.MOBILE_NO as MOBILE_NO,reg_no.STATUS as REGSTATUS  ")
//	.append("FROM ")
//	.append(SQLConstants.DISTPORTAL_SCHEMA)
//	.append(".V_BUSINESS_USER_DATA root,")
//	.append(SQLConstants.DISTPORTAL_SCHEMA)
//	.append(".V_REGISTERED_NUMBER as reg_no,")
//	.append(SQLConstants.DISTPORTAL_SCHEMA)
//	 .append(".V_BUSINESS_USER_MASTER as usr_mstr ")
//	.append("WHERE root.DATA_ID =(SELECT rm.BUS_USER_ID FROM ")
//	.append(SQLConstants.DISTPORTAL_SCHEMA)
//	.append(".V_REGISTERED_NUMBER rm WHERE rm.MOBILE_NO=? AND rm.STATUS=?) AND root.MASTER_ID = usr_mstr.MASTER_ID AND root.DATA_ID = reg_no.BUS_USER_ID ")
//	.append("UNION ALL ")
//	.append("Select sub.DATA_ID AS DATA_ID, sub.USER_CODE AS USER_CODE, sub.USER_NAME AS USER_NAME,") 
//	.append("sub.MASTER_ID AS MASTER_ID, sub.PARENT_ID AS PARENT_ID, sub.STATUS AS STATUS, sub.CONTACT_NO AS CONTACT_NO,")
//	.append("sub.ADDRESS AS ADDRESS ,")
//	.append("sub.CIRCLE_CODE AS CIRCLE_CODE, sub.HUB_CODE AS HUB_CODE, sub.FOS_CHECK_REQ AS FOS_CHECK_REQ,")
//	.append("sub.FOS_ACTV_CHK AS FOS_ACTV_CHK,")
//	.append("sub.LOC_ID AS LOC_ID,sub.ALL_SERVICE_CLASS AS ALL_SERVICE_CLASS,")
//	.append("sub.INCLUDE_SERVICE AS INCLUDE_SERVICE,")
//	.append("sub.EXCLUDE_SERVICE AS EXCLUDE_SERVICE,sub.CREATED_BY AS CREATED_BY,")
//	.append("sub.CREATED_DT AS CREATED_DT,")
//	.append("sub.UPDATED_BY AS UPDATED_BY,sub.UPDATED_DT AS UPDATED_DT,usr_mstr.USER_TYPE as USER_TYPE,usr_mstr.BASE_LOC as BASE_LOC,reg_no.MOBILE_NO as MOBILE_NO,reg_no.STATUS as REGSTATUS  ")
//	.append("FROM ")
//	.append(SQLConstants.DISTPORTAL_SCHEMA) 
//	.append(".V_BUSINESS_USER_DATA  sub, temptab super,")
//	.append(SQLConstants.DISTPORTAL_SCHEMA)
//	.append(".V_REGISTERED_NUMBER as reg_no,")
//	.append(SQLConstants.DISTPORTAL_SCHEMA)
//	.append(".V_BUSINESS_USER_MASTER as usr_mstr ")
//	.append("WHERE super.PARENT_ID = sub.DATA_ID AND sub.MASTER_ID = usr_mstr.MASTER_ID  AND sub.DATA_ID = reg_no.BUS_USER_ID )")
//	.append("SELECT * from temptab ")
//	.append("WITH UR ").toString();
	
	public static final String RETRIEVE_REQUESTER_INFO = new StringBuffer(500)
	.append("SELECT bu.DATA_ID,bu.USER_CODE,bu.USER_NAME,bu.MASTER_ID,bu.PARENT_ID,")
	.append("bu.STATUS,bu.CONTACT_NO,bu.ADDRESS,bu.CIRCLE_CODE,bu.HUB_CODE,")
	.append("bu.FOS_CHECK_REQ,bu.FOS_ACTV_CHK,bu.LOC_ID,bu.ALL_SERVICE_CLASS,bu.INCLUDE_SERVICE,")
	.append("bu.EXCLUDE_SERVICE,bu.CREATED_BY,bu.CREATED_DT,bu.UPDATED_BY,bu.UPDATED_DT,")
	.append("bm.USER_TYPE,bm.BASE_LOC ,rn.MOBILE_NO,bu.IPHONE_ACT_RIGHTS FROM ")
	.append(SQLConstants.DISTPORTAL_SCHEMA)
	.append(".V_BUSINESS_USER_DATA bu ,")
	.append(SQLConstants.DISTPORTAL_SCHEMA)
	.append(".V_BUSINESS_USER_MASTER bm ,")
	.append(SQLConstants.DISTPORTAL_SCHEMA)
	.append(".V_REGISTERED_NUMBER rn ")
	.append("WHERE bu.MASTER_ID=bm.MASTER_ID AND bu.DATA_ID=? AND rn.BUS_USER_ID=bu.DATA_ID AND bu.STATUS=?")
	.append("WITH UR").toString();
	
	public static final String GET_CURRENT_DATE = new StringBuffer(500)
	.append("select current date from sysibm.sysdummy1")
	.append(" WITH UR").toString();

	public static final String SQL_BIZ_USER_COUNT = new StringBuffer(500)
	.append("SELECT COUNT(*) FROM ")
	.append(SQLConstants.DISTPORTAL_SCHEMA)
	.append(".V_BUSINESS_USER_DATA  WHERE ((CREATED_DT)>=? AND (CREATED_DT)<=?)") 
	.append("OR ((UPDATED_DT)>=? AND (UPDATED_DT)<=?)")
	.append(" WITH UR").toString();
	
	public static final String RETRIEVE_BIZ_USER_DATA = new StringBuffer(500)
	.append("SELECT * from (select a.*,ROW_NUMber() over() rnum FROM (SELECT bd.DATA_ID,bd.USER_CODE,bd.USER_NAME,bd.MASTER_ID,bd.PARENT_ID,bd.STATUS,")
	.append("bd.LOC_ID,bd.CIRCLE_CODE,bm.BASE_LOC,rm.MOBILE_NO,bm.USER_TYPE,bd.CREATED_DT,bd.UPDATED_DT FROM ")
	.append(SQLConstants.DISTPORTAL_SCHEMA)
	.append(".V_BUSINESS_USER_DATA bd ,")
	.append(SQLConstants.DISTPORTAL_SCHEMA)
	.append(".V_BUSINESS_USER_MASTER bm ,")
	.append(SQLConstants.DISTPORTAL_SCHEMA)
	.append(".V_REGISTERED_NUMBER rm ")
	.append("WHERE bd.MASTER_ID=bm.MASTER_ID AND bd.DATA_ID=rm.BUS_USER_ID ")
	.append("AND (((bd.CREATED_DT)>=? AND (bd.CREATED_DT)<=?)")
	.append("OR ((bd.UPDATED_DT )>=? AND (bd.UPDATED_DT )<=?))) a )b ")
	.append("Where rnum<=? and rnum>=? ")
	.append(" WITH UR").toString();
	
	
	public static final String RETREIVE_BIZ_USER_ID = new StringBuffer(500)
	.append("SELECT rn.BUS_USER_ID,bd.MASTER_ID FROM ")
	.append(SQLConstants.DISTPORTAL_SCHEMA)
	.append(".V_REGISTERED_NUMBER rn, ")
	.append(SQLConstants.DISTPORTAL_SCHEMA)
	.append(".V_BUSINESS_USER_DATA bd ")
	.append("WHERE rn.MOBILE_NO = ? ")
	.append("AND rn.STATUS=? AND rn.BUS_USER_ID=bd.DATA_ID ")
	.append("WITH UR").toString();
	
}
