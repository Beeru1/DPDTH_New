package com.ibm.dp.common;

import com.ibm.virtualization.recharge.common.Constants;

public class RDBMSQueries {

/* Queries for Account Master by Anju */
	
	public static final String SQL_SELECT_FOR_PARENT_ACC = "select account_id,account_name from VR_ACCOUNT_DETAILS DETAILS,VR_LOGIN_MASTER VLM" +
	" where account_level = ( select level_id from VR_ACCOUNT_LEVEL_MASTER where acc_level = (" +
	" SELECT level2.ACC_LEVEL-1 FROM VR_ACCOUNT_LEVEL_MASTER level2 WHERE	LEVEL_ID=?)" +
	" AND HIERARCHY_ID = (SELECT 	HIERARCHY_ID FROM VR_ACCOUNT_LEVEL_MASTER	WHERE	LEVEL_ID=?))" +
	" AND VLM.STATUS = '"+Constants.ACTIVE_STATUS+"' and DETAILS.circle_id = ? " +
	" AND DETAILS.account_id = VLM.login_id";

	public static final String SQL_SELECT_FOR_PARENT_ACC_COUNT = "select count(*) from  VR_ACCOUNT_DETAILS DETAILS,VR_LOGIN_MASTER VLM where account_level in ( select level_id from VR_ACCOUNT_LEVEL_MASTER where acc_level in ( SELECT level2.ACC_LEVEL-1 FROM VR_ACCOUNT_LEVEL_MASTER level2 WHERE	LEVEL_ID=?) AND HIERARCHY_ID = (SELECT 	HIERARCHY_ID FROM VR_ACCOUNT_LEVEL_MASTER	WHERE	LEVEL_ID=?)) AND VLM.STATUS = 'A' and DETAILS.circle_id = ?  AND DETAILS.account_id = VLM.login_id ";

	public static final String SQL_SELECT_ALL_ACC_WHERE_CLAUSE = " FROM VR_ACCOUNT_DETAILS DETAILS LEFT JOIN VR_ACCOUNT_BALANCE BALANCE ON DETAILS.ACCOUNT_ID = BALANCE.ACCOUNT_ID,VR_LOGIN_MASTER LOGIN, VR_LOGIN_MASTER LOGIN2,VR_LOGIN_MASTER L1 ,VR_CIRCLE_MASTER CIRCLE,VR_GROUP_DETAILS GROUPD, VR_REGION_MASTER REGION WHERE LOGIN.LOGIN_ID = DETAILS.ACCOUNT_ID AND DETAILS.PARENT_ACCOUNT = L1.LOGIN_ID AND DETAILS.CIRCLE_ID = CIRCLE.CIRCLE_ID AND LOGIN.GROUP_ID=GROUPD.GROUP_ID AND CIRCLE.REGION_ID = REGION.REGION_ID AND LOGIN2.LOGIN_ID = DETAILS.CREATED_BY";
	
	public static final String SQL_SELECT_ALL_ACC_COUNT_CHILD = "WITH n( ACCOUNT_ID) AS (SELECT  ACCOUNT_ID FROM VR_ACCOUNT_DETAILS WHERE ACCOUNT_ID = ? UNION ALL SELECT nplus1.ACCOUNT_ID FROM VR_ACCOUNT_DETAILS as nplus1, n WHERE n.ACCOUNT_ID = nplus1.PARENT_ACCOUNT) SELECT count(*) FROM VR_ACCOUNT_DETAILS DETAILS ,VR_LOGIN_MASTER LOGIN, VR_LOGIN_MASTER LOGIN2,VR_LOGIN_MASTER L1 ,VR_CIRCLE_MASTER CIRCLE,VR_GROUP_DETAILS GROUPD, VR_REGION_MASTER REGION WHERE LOGIN.LOGIN_ID = DETAILS.ACCOUNT_ID AND DETAILS.PARENT_ACCOUNT = L1.LOGIN_ID AND DETAILS.CIRCLE_ID = CIRCLE.CIRCLE_ID AND LOGIN.GROUP_ID=GROUPD.GROUP_ID AND CIRCLE.REGION_ID = REGION.REGION_ID AND LOGIN2.LOGIN_ID = DETAILS.CREATED_BY   ";
//		"WITH n( ACCOUNT_ID) AS (SELECT  ACCOUNT_ID FROM VR_ACCOUNT_DETAILS WHERE ACCOUNT_ID = ? UNION ALL SELECT nplus1.ACCOUNT_ID FROM VR_ACCOUNT_DETAILS as nplus1, n WHERE n.ACCOUNT_ID = nplus1.PARENT_ACCOUNT)SELECT COUNT(DETAILS.ACCOUNT_ID) "
//		+ SQL_SELECT_ALL_ACC_WHERE_CLAUSE;
	
	//public static final String SQL_SELECT_ALL_ACC_CHILD_DISTRIBUTOR = "SELECT GROUPD.GROUP_NAME, LOGIN.LOGIN_NAME, L1.LOGIN_NAME as parent, DETAILS.PARENT_ACCOUNT PCODE,DETAILS.ACCOUNT_ID, (Select CIRCLE_NAME from VR_CIRCLE_MASTER where CIRCLE_ID IN(cm.CIRCLE_ID)),DETAILS.ACCOUNT_NAME,DETAILS.CIRCLE_ID,DETAILS.MOBILE_NUMBER,DETAILS.SIM_NUMBER, DETAILS.ACCOUNT_ADDRESS, DETAILS.EMAIL,DETAILS.CREATED_DT, COALESCE (DETAILS.CATEGORY,'X') as CATEGORY,LOGIN.STATUS, REGION.REGION_NAME , DETAILS.CREATED_BY , LOGIN2.LOGIN_NAME as CREATEDBYNAME FROM DP_ACCOUNT_CIRCLE_MAP cm, VR_ACCOUNT_DETAILS DETAILS ,VR_LOGIN_MASTER LOGIN, VR_LOGIN_MASTER LOGIN2,VR_LOGIN_MASTER L1 ,VR_CIRCLE_MASTER CIRCLE,VR_GROUP_DETAILS GROUPD, VR_REGION_MASTER REGION WHERE cm.ACCOUNT_ID=DETAILS.ACCOUNT_ID and LOGIN.LOGIN_ID = DETAILS.ACCOUNT_ID AND DETAILS.PARENT_ACCOUNT = L1.LOGIN_ID AND LOGIN.GROUP_ID=GROUPD.GROUP_ID AND CIRCLE.REGION_ID = REGION.REGION_ID AND LOGIN2.LOGIN_ID = DETAILS.CREATED_BY  ";
	
	//public static final String SQL_SELECT_ALL_ACC_CHILD_DISTRIBUTOR = "SELECT GROUPD.GROUP_NAME, LOGIN.LOGIN_NAME, L1.LOGIN_NAME as parent, DETAILS.PARENT_ACCOUNT PCODE,DETAILS.ACCOUNT_ID, CIRCLE.CIRCLE_NAME,DETAILS.ACCOUNT_NAME,DETAILS.CIRCLE_ID,DETAILS.MOBILE_NUMBER,DETAILS.SIM_NUMBER, DETAILS.ACCOUNT_ADDRESS, DETAILS.EMAIL,DETAILS.CREATED_DT, COALESCE (DETAILS.CATEGORY,'X') as CATEGORY,LOGIN.STATUS, REGION.REGION_NAME , DETAILS.CREATED_BY , LOGIN2.LOGIN_NAME as CREATEDBYNAME FROM VR_ACCOUNT_DETAILS DETAILS ,VR_LOGIN_MASTER LOGIN, VR_LOGIN_MASTER LOGIN2,VR_LOGIN_MASTER L1 ,VR_CIRCLE_MASTER CIRCLE,VR_GROUP_DETAILS GROUPD, VR_REGION_MASTER REGION WHERE LOGIN.LOGIN_ID = DETAILS.ACCOUNT_ID AND DETAILS.PARENT_ACCOUNT = L1.LOGIN_ID AND DETAILS.CIRCLE_ID = CIRCLE.CIRCLE_ID AND LOGIN.GROUP_ID=GROUPD.GROUP_ID AND CIRCLE.REGION_ID = REGION.REGION_ID AND LOGIN2.LOGIN_ID = DETAILS.ROOT_LEVEL_ID  ";
	//Neetika for REG
	public static final String SQL_SELECT_ALL_ACC_CHILD_DISTRIBUTOR = "SELECT GROUPD.GROUP_NAME, LOGIN.LOGIN_NAME, L1.LOGIN_NAME as parent, DETAILS.PARENT_ACCOUNT PCODE,DETAILS.ACCOUNT_ID, CIRCLE.CIRCLE_NAME,DETAILS.ACCOUNT_NAME,DETAILS.CIRCLE_ID,case account_level when 8 then DETAILS.RETAILER_LAPU else DETAILS.MOBILE_NUMBER  end as MOBILE_NUMBER,DETAILS.SIM_NUMBER, DETAILS.ACCOUNT_ADDRESS, DETAILS.EMAIL,DETAILS.CREATED_DT, COALESCE (DETAILS.CATEGORY,'X') as CATEGORY,LOGIN.STATUS, REGION.REGION_NAME , DETAILS.CREATED_BY , LOGIN2.LOGIN_NAME as CREATEDBYNAME FROM VR_ACCOUNT_DETAILS DETAILS ,VR_LOGIN_MASTER LOGIN, VR_LOGIN_MASTER LOGIN2,VR_LOGIN_MASTER L1 ,VR_CIRCLE_MASTER CIRCLE,VR_GROUP_DETAILS GROUPD, VR_REGION_MASTER REGION WHERE LOGIN.LOGIN_ID = DETAILS.ACCOUNT_ID AND DETAILS.PARENT_ACCOUNT = L1.LOGIN_ID AND DETAILS.CIRCLE_ID = CIRCLE.CIRCLE_ID AND LOGIN.GROUP_ID=GROUPD.GROUP_ID AND CIRCLE.REGION_ID = REGION.REGION_ID AND LOGIN2.LOGIN_ID = DETAILS.ROOT_LEVEL_ID  ";
	public static final String SQL_SELECT_ALL_ACC_CHILD = "SELECT * FROM(SELECT a.*,ROW_NUMBER() over() rnum FROM (select RECORDS.* from (" +
			" select GD.GROUP_NAME, LM.LOGIN_NAME, (SELECT LOGIN_NAME from DPDTH.VR_LOGIN_MASTER WHERE LOGIN_ID=AD.PARENT_ACCOUNT) AS parent, AD.PARENT_ACCOUNT , " +
			" (SELECT STATUS from DPDTH.VR_LOGIN_MASTER WHERE LOGIN_ID=AD.PARENT_ACCOUNT)  AS PARENT_STATUS  , "+
			" AD.PARENT_ACCOUNT as PCODE, AD.ACCOUNT_ID, CM.CIRCLE_NAME, AD.ACCOUNT_NAME, CM.CIRCLE_ID, AD.MOBILE_NUMBER, AD.SIM_NUMBER," +
			" AD.ACCOUNT_ADDRESS, AD.EMAIL, AD.CREATED_DT, COALESCE (AD.CATEGORY,'X') as CATEGORY, LM.STATUS, AD.CREATED_BY, " +
			" (SELECT LOGIN_NAME from DPDTH.VR_LOGIN_MASTER WHERE LOGIN_ID=AD.CREATED_BY) AS CREATEDBYNAME" +
			" from DPDTH.VR_ACCOUNT_DETAILS AD inner join DPDTH.VR_LOGIN_MASTER LM on AD.ACCOUNT_ID=LM.LOGIN_ID" +
			" inner join DPDTH.VR_GROUP_DETAILS GD on LM.GROUP_ID=GD.GROUP_ID inner join DPDTH.DP_ACCOUNT_CIRCLE_MAP ACM on ACM.ACCOUNT_ID=AD.ACCOUNT_ID " +
			" inner join DPDTH.VR_CIRCLE_MASTER CM on CM.CIRCLE_ID=ACM.CIRCLE_ID ";
	
	public static final String SQL_SELECT_ALL_ACC_CHILD_COUNT = "select COUNT(*) from (" +
	" select GD.GROUP_NAME, LM.LOGIN_NAME, (SELECT LOGIN_NAME from DPDTH.VR_LOGIN_MASTER WHERE LOGIN_ID=AD.PARENT_ACCOUNT) AS parent," +
	" AD.PARENT_ACCOUNT as PCODE, AD.ACCOUNT_ID, CM.CIRCLE_NAME, AD.ACCOUNT_NAME, CM.CIRCLE_ID, AD.MOBILE_NUMBER, AD.SIM_NUMBER," +
	" AD.ACCOUNT_ADDRESS, AD.EMAIL, AD.CREATED_DT, COALESCE (AD.CATEGORY,'X') as CATEGORY, LM.STATUS, AD.CREATED_BY, " +
	" (SELECT LOGIN_NAME from DPDTH.VR_LOGIN_MASTER WHERE LOGIN_ID=AD.CREATED_BY) AS CREATEDBYNAME" +
	" from DPDTH.VR_ACCOUNT_DETAILS AD inner join DPDTH.VR_LOGIN_MASTER LM on AD.ACCOUNT_ID=LM.LOGIN_ID" +
	" inner join DPDTH.VR_GROUP_DETAILS GD on LM.GROUP_ID=GD.GROUP_ID inner join DPDTH.DP_ACCOUNT_CIRCLE_MAP ACM on ACM.ACCOUNT_ID=AD.ACCOUNT_ID " +
	" inner join DPDTH.VR_CIRCLE_MASTER CM on CM.CIRCLE_ID=ACM.CIRCLE_ID ";
	
	public static final String SQL_SELECT_ALL_ACC_CHILD_DIST = "SELECT GROUPD.GROUP_NAME, LOGIN.LOGIN_NAME, L1.LOGIN_NAME as parent,(case L1.STATUS when 'A' then 'Active' when 'I' then 'Suspended' else L1.STATUS end) AS PARENT_STATUS , DETAILS.PARENT_ACCOUNT PCODE,DETAILS.ACCOUNT_ID, DETAILS.PARENT_ACCOUNT  , CIRCLE.CIRCLE_NAME,DETAILS.ACCOUNT_NAME,DETAILS.CIRCLE_ID,DETAILS.MOBILE_NUMBER,DETAILS.SIM_NUMBER, DETAILS.ACCOUNT_ADDRESS, DETAILS.EMAIL,DETAILS.CREATED_DT, COALESCE (DETAILS.CATEGORY,'X') as CATEGORY,LOGIN.STATUS, REGION.REGION_NAME , DETAILS.CREATED_BY , LOGIN2.LOGIN_NAME as CREATEDBYNAME FROM  VR_ACCOUNT_DETAILS DETAILS ,VR_LOGIN_MASTER LOGIN, VR_LOGIN_MASTER LOGIN2,VR_LOGIN_MASTER L1 ,VR_CIRCLE_MASTER CIRCLE,VR_GROUP_DETAILS GROUPD, VR_REGION_MASTER REGION WHERE LOGIN.LOGIN_ID = DETAILS.ACCOUNT_ID AND DETAILS.PARENT_ACCOUNT = L1.LOGIN_ID AND DETAILS.CIRCLE_ID = CIRCLE.CIRCLE_ID AND LOGIN.GROUP_ID=GROUPD.GROUP_ID AND CIRCLE.REGION_ID = REGION.REGION_ID AND LOGIN2.LOGIN_ID = DETAILS.CREATED_BY  ";

	public static final String SQL_SELECT_ALL_ACC_CHILD_DB2 = "SELECT GROUPD.GROUP_NAME, LOGIN.LOGIN_NAME, L1.LOGIN_NAME as parent, DETAILS.PARENT_ACCOUNT PCODE,BALANCE.OPERATING_BALANCE,DETAILS.ACCOUNT_ID, CIRCLE.CIRCLE_NAME,DETAILS.ACCOUNT_NAME,DETAILS.CIRCLE_ID,DETAILS.MOBILE_NUMBER,DETAILS.SIM_NUMBER, DETAILS.ACCOUNT_ADDRESS, DETAILS.EMAIL,DETAILS.CREATED_DT, COALESCE (DETAILS.CATEGORY,'X') as CATEGORY,LOGIN.STATUS, BALANCE.OPENING_BALANCE,BALANCE.AVAILABLE_BALANCE, BALANCE.RATE, BALANCE.THRESHOLD, REGION.REGION_NAME , DETAILS.CREATED_BY , LOGIN2.LOGIN_NAME as CREATEDBYNAME FROM VR_ACCOUNT_DETAILS DETAILS LEFT JOIN VR_ACCOUNT_BALANCE BALANCE ON DETAILS.ACCOUNT_ID = BALANCE.ACCOUNT_ID,VR_LOGIN_MASTER LOGIN, VR_LOGIN_MASTER LOGIN2,VR_LOGIN_MASTER L1 ,VR_CIRCLE_MASTER CIRCLE,VR_GROUP_DETAILS GROUPD, VR_REGION_MASTER REGION WHERE LOGIN.LOGIN_ID = DETAILS.ACCOUNT_ID AND DETAILS.PARENT_ACCOUNT = L1.LOGIN_ID AND DETAILS.CIRCLE_ID = CIRCLE.CIRCLE_ID AND LOGIN.GROUP_ID=GROUPD.GROUP_ID AND CIRCLE.REGION_ID = REGION.REGION_ID AND LOGIN2.LOGIN_ID = DETAILS.CREATED_BY" ;
	//		"select GROUP.GROUP_NAME,LOGIN.LOGIN_NAME, L1.LOGIN_NAME as parent,DETAILS.PARENT_ACCOUNT PCODE,temp.OPERATING_BALANCE,DETAILS.ACCOUNT_ID,CIRCLE.CIRCLE_NAME,DETAILS.ACCOUNT_NAME,	DETAILS.CIRCLE_ID,DETAILS.MOBILE_NUMBER,DETAILS.SIM_NUMBER,DETAILS.ACCOUNT_ADDRESS,DETAILS.EMAIL,DETAILS.CREATED_DT,DETAILS.CREATED_BY,LOGIN2.LOGIN_NAME as CREATEDBYNAME,REGION.REGION_NAME,temp.CATEGORY,LOGIN.STATUS,temp.RATE,temp.THRESHOLD,temp.OPENING_BALANCE,temp.AVAILABLE_BALANCE,DETAILS.ACCOUNT_LEVEL,temp.DIST_CHANNEL_ID,temp.CHANNEL_CATEGORY_ID from VR_ACCOUNT_DETAILS DETAILS left join (select account.account_id,account.CATEGORY,account.DIST_CHANNEL_ID,account.CHANNEL_CATEGORY_ID,balance.OPERATING_BALANCE,balance.RATE,balance.THRESHOLD,balance.OPENING_BALANCE,balance.AVAILABLE_BALANCE from VR_ACCOUNT_BALANCE balance,VR_ACCOUNT_DETAILS account where account.account_id = balance.account_id)temp	on temp.account_id = details.account_id,VR_LOGIN_MASTER LOGIN,VR_LOGIN_MASTER L1,VR_LOGIN_MASTER LOGIN2,VR_GROUP_DETAILS group,VR_CIRCLE_MASTER circle,VR_REGION_MASTER REGION where l1.login_id=LOGIN.login_id AND DETAILS.account_id = LOGIN.login_id and	LOGIN.group_id = group.group_id and details.circle_id = circle.circle_id AND LOGIN2.LOGIN_ID = DETAILS.CREATED_BY AND CIRCLE.REGION_ID = REGION.REGION_ID "; 

	public static final String SQL_SELECT_PARENT_TRADE = "select dist.CHANNEL_NAME, cat.CATEGORY_NAME, "
		+"details.DIST_CHANNEL_ID, details.CHANNEL_CATEGORY_ID from VR_ACCOUNT_DETAILS details," 
		+" DP_DIST_CHANNEL_MASTER dist, "
		+"DP_CHANNEL_CATEGORY_MASTER CAT where dist.CHANNEL_ID = details.DIST_CHANNEL_ID "
		+"and cat.CATEGORY_ID = details.CHANNEL_CATEGORY_ID "
		+" and details.account_id=?";
	
	public static final String SQL_SELECT_ALL_ACC_H2 ="select GROUP.GROUP_NAME,region.region_name,VLM.LOGIN_NAME, L1.LOGIN_NAME as parent, DETAILS.PARENT_ACCOUNT PCODE,DETAILS.ACCOUNT_ID,CIRCLE.CIRCLE_NAME,DETAILS.ACCOUNT_NAME,DETAILS.created_dt,dd.contact_name as CREATED_BY_NAME,details.created_by,COALESCE (DETAILS.CATEGORY,'X') as CATEGORY, DETAILS.CIRCLE_ID,DETAILS.MOBILE_NUMBER,DETAILS.SIM_NUMBER,DETAILS.ACCOUNT_ADDRESS,DETAILS.EMAIL,VLM.STATUS, VLM.LOGIN_ATTEMPTED from VR_ACCOUNT_DETAILS DETAILS,VR_ACCOUNT_DETAILS dd,VR_LOGIN_MASTER VLM,VR_LOGIN_MASTER L1,VR_REGION_MASTER region, VR_GROUP_DETAILS group,VR_CIRCLE_MASTER circle where details.account_level in (select level_id from VR_ACCOUNT_LEVEL_MASTER where acc_level > (SELECT level2.ACC_LEVEL FROM VR_ACCOUNT_LEVEL_MASTER level2 WHERE LEVEL_ID=?) AND HIERARCHY_ID =(SELECT HIERARCHY_ID FROM VR_ACCOUNT_LEVEL_MASTER WHERE LEVEL_ID=?)) and l1.login_id=details.parent_account AND DETAILS.account_id = VLM.login_id and dd.account_id = details.created_by and VLM.group_id = group.group_id and details.circle_id = circle.circle_id  AND region.region_id = circle.region_id";

// Queries for Account Movement.	
	
	public static final String SQL_SELECT_DIST_LIST = "select account_id,account_name from vr_account_details where account_level = "+Constants.DIST_LEVEL_ID;
}