package com.ibm.dp.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.log4j.Logger;

import com.ibm.dp.common.DBQueries;
import com.ibm.dp.dao.AccountDetailReportDao;
import com.ibm.dp.dto.AccountDetailReportDto;
import com.ibm.dpmisreports.common.SelectionCollection;
import com.ibm.utilities.Utility;
import com.ibm.virtualization.recharge.dao.rdbms.BaseDaoRdbms;
import com.ibm.virtualization.recharge.db.DBConnectionManager;
import com.ibm.virtualization.recharge.exception.DAOException;

public class AccountDetailReportDaoImpl  extends BaseDaoRdbms implements AccountDetailReportDao{
	private Logger logger = Logger.getLogger(AccountDetailReportDaoImpl.class.getName());
	public static final String SQL_PO_DETAIL_REPORT 	= DBQueries.SQL_PO_DETAIL_REPORT;
	public static final String SQL_PO_STATUS_LIST 	= DBQueries.SQL_PO_STATUS_LIST;
	public static final String GET_PARENT_ID 	= DBQueries.GET_PARENT_ACCOUNT_ID;
	
	public AccountDetailReportDaoImpl(Connection connection) {
		super(connection);
	}
	
	public List<AccountDetailReportDto> getAccountDetailExcel(AccountDetailReportDto reportDto) throws DAOException{
		List<AccountDetailReportDto> listReturn = new ArrayList<AccountDetailReportDto>();
		PreparedStatement ps = null;
		ResultSet rsetReport = null;
		String accountMultiSelectedIds;
		String accountMultiSelectedIdsArr[];
		logger.info("Account Detail Report Start......................!!!");
		try
		{
			String sql = "";
			accountMultiSelectedIds=reportDto.getAccountTypeMultiSelectIds();
			accountMultiSelectedIdsArr=accountMultiSelectedIds.split(",");
			logger.info("accountMultiSelectedIds  in daoimpl :"+accountMultiSelectedIds);
			logger.info("List of cIrcles.... " + reportDto.getHiddenCircleSelecIds());

			connection = DBConnectionManager.getDBConnection();
			String statusStr = reportDto.getStatus();
			String accountType = reportDto.getAccountType();
			String query = "";
			String query1 = "";
			String query2 = "";
			String query3 = "";
			String status ="";
			String searchCriteria = "";
			//String distId = reportDto.getHiddenDistSelecIds();
			
			if(statusStr.equalsIgnoreCase("0")){
				//status = "'A','I'";
				status = "'A','I','L','C'";
			}
			if(statusStr.equalsIgnoreCase("1")){
				status = "'A'";
			}
			if(statusStr.equalsIgnoreCase("2")){
				status = "'I','L'"; //added L by neetika
			}
			if(statusStr.equalsIgnoreCase("3")){
				status = "'C'"; 
			}

			
			String circleFilter="";
			String sql2="";
			String sql3="";
			String flag="";
			for(int i=0;i<accountMultiSelectedIdsArr.length;i++)
			{
				if(accountMultiSelectedIdsArr[i]!="-1")
				{
					if(accountMultiSelectedIdsArr[i].equalsIgnoreCase("3"))
					{
						sql = "SELECT LOGIN_NAME as Login_ID, a.ACCOUNT_LEVEL, " +
						"			(SELECT LEVEL_NAME FROM VR_ACCOUNT_LEVEL_MASTER where LEVEL_ID = a.ACCOUNT_LEVEL) as Account_Type," +
						"			ACCOUNT_NAME as Contact_Name," +
						"			LAPU_MOBILE_NO as Lapu_No,a.LAPU_MOBILE_NO_2,a.FTA_MOBILE_NO,a.FTA_MOBILE_NO_2, " + //changed by neetika to add 3 more fields version 3.2
						"			(SELECT LOGIN_NAME FROM VR_LOGIN_MASTER where LOGIN_ID = a.PARENT_ACCOUNT) as Parent_Login_ID," +
						"			(SELECT LEVEL_NAME FROM VR_ACCOUNT_LEVEL_MASTER where LEVEL_ID = a.ACCOUNT_LEVEL -1) as Parent_Account_Type," +
						"			(SELECT ACCOUNT_NAME FROM VR_ACCOUNT_DETAILS where ACCOUNT_ID = a.PARENT_ACCOUNT) as Parent_Account_Name," +			
						"			(SELECT CIRCLE_NAME FROM VR_CIRCLE_MASTER where CIRCLE_ID = map1.CIRCLE_ID fetch first 1 row only ) as Circle," +
						"			(SELECT CITY_NAME FROM VR_CITY_MASTER where CITY_ID = a.CITY_ID) as City," +
						//"			( select WH_NAME from DP_WH_MASTER where WH_CODE in(select wh_code from DPDTH.DP_WH_DIST_MAP where DISTID = A.ACCOUNT_ID) )  as WH_NAME,"+
						"			''  as WH_NAME,"+ //Changed by Neetika as WH name is not required for this account level it impacts the performance of report
						"			EMAIL as Email," +
						"			MOBILE_NUMBER as Mobile," +
						"			ACCOUNT_ADDRESS as Address," +
						"			PIN_NUMBER as Pin," +
						"			(SELECT ZONE_NAME FROM DP_ZONE_MASTER where ZONE_ID = a.ZONE_ID) as Zone_Name," +
						//"			(SELECT EXT_DIST_ID FROM DP_DISTRIBUTOR_MAPPING where DP_DIST_ID = a.ACCOUNT_ID FETCH FIRST 1 row only) as BoTree_Code," +
						"			'' as BoTree_Code," + //Changed by Neetika as it is not required for this account level it impacts the performance of report
						//"			DISTRIBUTOR_LOCATOR as Oracle_Locator," +
						"			'' as Oracle_Locator," + //Changed by Neetika as it is not required for this account level it impacts the performance of report
						"			STATUS as Status," +
						"			DATE(CREATED_DT) as Created_Date," +
						"			(SELECT ACCOUNT_NAME FROM VR_ACCOUNT_DETAILS where ACCOUNT_ID = a.CREATED_BY) as Created_By," +
						"			DATE(UPDATED_DT) as Last_Updated_Date," +
						"			(SELECT ACCOUNT_NAME FROM VR_ACCOUNT_DETAILS where ACCOUNT_ID = a.UPDATED_BY ) as Last_Updated_By," +
						"			coalesce(to_char(LAST_LOGIN_TIME,'dd/MM/yyyy'),'') as Last_Login_date, " +
					"			case when LAST_LOGIN_TIME is null then coalesce(to_char((IPWD_CHANGED_DT+45 DAYS),'dd/MM/yyyy'),'') else coalesce(to_char((IPWD_CHANGED_DT),'dd/MM/yyyy'),'') end as IPWD_CHANGED_DT,"+ 
					"			case when a.ACCOUNT_LEVEL!=5 then '' when a.TRANSACTION_TYPE=1 then 'Sales' else 'CPE' end as TRANSACTION_TYPE" +
						"			from VR_LOGIN_MASTER b , VR_ACCOUNT_DETAILS a left outer join  DP_ACCOUNT_CIRCLE_MAP map1 on map1.account_id= a.account_id  " +
						"			where a.ACCOUNT_ID=b.LOGIN_ID and b.STATUS IN( "+status+ ") ";
						
						circleFilter = "map1";
						flag = "true";
					}
					else if(accountMultiSelectedIdsArr[i].equalsIgnoreCase("4"))
					{
						sql = "SELECT LOGIN_NAME as Login_ID, a.ACCOUNT_LEVEL, " +
						"			(SELECT LEVEL_NAME FROM VR_ACCOUNT_LEVEL_MASTER where LEVEL_ID = a.ACCOUNT_LEVEL) as Account_Type," +
						"			ACCOUNT_NAME as Contact_Name," +
						"			LAPU_MOBILE_NO as Lapu_No,a.LAPU_MOBILE_NO_2,a.FTA_MOBILE_NO,a.FTA_MOBILE_NO_2, " + //changed by neetika to add 3 more fields version 3.2
						"			(SELECT LOGIN_NAME FROM VR_LOGIN_MASTER where LOGIN_ID = a.PARENT_ACCOUNT) as Parent_Login_ID," +
						"			(SELECT LEVEL_NAME FROM VR_ACCOUNT_LEVEL_MASTER where LEVEL_ID = a.ACCOUNT_LEVEL -1) as Parent_Account_Type," +
						"			(SELECT ACCOUNT_NAME FROM VR_ACCOUNT_DETAILS where ACCOUNT_ID = a.PARENT_ACCOUNT) as Parent_Account_Name," +			
						"			(SELECT CIRCLE_NAME FROM VR_CIRCLE_MASTER where CIRCLE_ID = map1.CIRCLE_ID fetch first 1 row only ) as Circle," +
						"			(SELECT CITY_NAME FROM VR_CITY_MASTER where CITY_ID = a.CITY_ID) as City," +
						//"			( select WH_NAME from DP_WH_MASTER where WH_CODE in(select wh_code from DPDTH.DP_WH_DIST_MAP where DISTID = A.ACCOUNT_ID) )  as WH_NAME,"+
						"			''  as WH_NAME,"+ //Changed by Neetika as WH name is not required for this account level it impacts the performance of report
						"			EMAIL as Email," +
						"			MOBILE_NUMBER as Mobile," +
						"			ACCOUNT_ADDRESS as Address," +
						"			PIN_NUMBER as Pin," +
						"			(SELECT ZONE_NAME FROM DP_ZONE_MASTER where ZONE_ID = a.ZONE_ID) as Zone_Name," +
						//"			(SELECT EXT_DIST_ID FROM DP_DISTRIBUTOR_MAPPING where DP_DIST_ID = a.ACCOUNT_ID FETCH FIRST 1 row only) as BoTree_Code," +
						"			'' as BoTree_Code," + //Changed by Neetika as it is not required for this account level it impacts the performance of report
						//"			DISTRIBUTOR_LOCATOR as Oracle_Locator," +
						"			'' as Oracle_Locator," + //Changed by Neetika as it is not required for this account level it impacts the performance of report
						"			STATUS as Status," +
						"			DATE(CREATED_DT) as Created_Date," +
						"			(SELECT ACCOUNT_NAME FROM VR_ACCOUNT_DETAILS where ACCOUNT_ID = a.CREATED_BY) as Created_By," +
						"			DATE(UPDATED_DT) as Last_Updated_Date," +
						"			(SELECT ACCOUNT_NAME FROM VR_ACCOUNT_DETAILS where ACCOUNT_ID = a.UPDATED_BY ) as Last_Updated_By," +
						"			coalesce(to_char(LAST_LOGIN_TIME,'dd/MM/yyyy'),'') as Last_Login_date, " +
					"			case when LAST_LOGIN_TIME is null then coalesce(to_char((IPWD_CHANGED_DT+45 DAYS),'dd/MM/yyyy'),'') else coalesce(to_char((IPWD_CHANGED_DT),'dd/MM/yyyy'),'') end as IPWD_CHANGED_DT, " +
					"			case when a.ACCOUNT_LEVEL!=5 then '' when a.TRANSACTION_TYPE=1 then 'Sales' else 'CPE' end as TRANSACTION_TYPE" +
						"			from VR_LOGIN_MASTER b , VR_ACCOUNT_DETAILS a left outer join  DP_ACCOUNT_CIRCLE_MAP map1 on map1.account_id= a.account_id  " +
						"			where a.ACCOUNT_ID=b.LOGIN_ID and b.STATUS IN( "+status+ ") ";
						
						circleFilter = "map1";
						flag = "true";
					}
					else if(accountMultiSelectedIdsArr[i].equalsIgnoreCase("5"))
					{
						sql = "SELECT LOGIN_NAME as Login_ID, a.ACCOUNT_LEVEL, " +
						"			(SELECT LEVEL_NAME FROM VR_ACCOUNT_LEVEL_MASTER where LEVEL_ID = a.ACCOUNT_LEVEL) as Account_Type," +
						"			ACCOUNT_NAME as Contact_Name," +
						"			LAPU_MOBILE_NO as Lapu_No,a.LAPU_MOBILE_NO_2,a.FTA_MOBILE_NO,a.FTA_MOBILE_NO_2, " + //changed by neetika to add 3 more fields version 3.2
						"			(SELECT LOGIN_NAME FROM VR_LOGIN_MASTER where LOGIN_ID = a.PARENT_ACCOUNT) as Parent_Login_ID," +
						"			(SELECT LEVEL_NAME FROM VR_ACCOUNT_LEVEL_MASTER where LEVEL_ID = a.ACCOUNT_LEVEL -1) as Parent_Account_Type," +
						"			(SELECT ACCOUNT_NAME FROM VR_ACCOUNT_DETAILS where ACCOUNT_ID = a.PARENT_ACCOUNT) as Parent_Account_Name," +			
						"			(SELECT CIRCLE_NAME FROM VR_CIRCLE_MASTER where CIRCLE_ID = map1.CIRCLE_ID fetch first 1 row only ) as Circle," +
						"			(SELECT CITY_NAME FROM VR_CITY_MASTER where CITY_ID = a.CITY_ID) as City," +
						//"			( select WH_NAME from DP_WH_MASTER where WH_CODE in(select wh_code from DPDTH.DP_WH_DIST_MAP where DISTID = A.ACCOUNT_ID) )  as WH_NAME,"+
						"			''  as WH_NAME,"+ //Changed by Neetika as WH name is not required for this account level it impacts the performance of report
						"			EMAIL as Email," +
						"			MOBILE_NUMBER as Mobile," +
						"			ACCOUNT_ADDRESS as Address," +
						"			PIN_NUMBER as Pin," +
						"			(SELECT ZONE_NAME FROM DP_ZONE_MASTER where ZONE_ID = a.ZONE_ID) as Zone_Name," +
						//"			(SELECT EXT_DIST_ID FROM DP_DISTRIBUTOR_MAPPING where DP_DIST_ID = a.ACCOUNT_ID FETCH FIRST 1 row only) as BoTree_Code," +
						"			'' as BoTree_Code," + //Changed by Neetika as it is not required for this account level it impacts the performance of report
						//"			DISTRIBUTOR_LOCATOR as Oracle_Locator," +
						"			'' as Oracle_Locator," + //Changed by Neetika
						"			STATUS as Status," +
						"			DATE(CREATED_DT) as Created_Date," +
						"			(SELECT ACCOUNT_NAME FROM VR_ACCOUNT_DETAILS where ACCOUNT_ID = a.CREATED_BY) as Created_By," +
						"			DATE(UPDATED_DT) as Last_Updated_Date," +
						"			(SELECT ACCOUNT_NAME FROM VR_ACCOUNT_DETAILS where ACCOUNT_ID = a.UPDATED_BY ) as Last_Updated_By," +
						"			coalesce(to_char(LAST_LOGIN_TIME,'dd/MM/yyyy'),'') as Last_Login_date, " +
						"			case when LAST_LOGIN_TIME is null then coalesce(to_char((IPWD_CHANGED_DT+45 DAYS),'dd/MM/yyyy'),'') else coalesce(to_char((IPWD_CHANGED_DT),'dd/MM/yyyy'),'') end as IPWD_CHANGED_DT, " +
						"			case when a.ACCOUNT_LEVEL!=5 then '' when a.TRANSACTION_TYPE=1 then 'Sales' else 'CPE' end as TRANSACTION_TYPE" + //Neetika code merge with prod
						"			from VR_LOGIN_MASTER b , VR_ACCOUNT_DETAILS a left outer join  DP_ACCOUNT_CIRCLE_MAP map1 on map1.account_id= a.account_id  " +
						"			where a.ACCOUNT_ID=b.LOGIN_ID and b.STATUS IN( "+status+ ") ";
						
						circleFilter = "map1";
						flag = "true";
					}
					else if(accountMultiSelectedIdsArr[i].equalsIgnoreCase("6"))
					{
								sql = "SELECT b.LOGIN_NAME as Login_ID, a.ACCOUNT_LEVEL,   "
									+" (SELECT LEVEL_NAME FROM VR_ACCOUNT_LEVEL_MASTER where LEVEL_ID = a.ACCOUNT_LEVEL) as Account_Type, " 
									+" a.ACCOUNT_NAME as Contact_Name,  "
									+" a.LAPU_MOBILE_NO as Lapu_No, a.LAPU_MOBILE_NO_2,a.FTA_MOBILE_NO,a.FTA_MOBILE_NO_2, "  //changed by neetika to add 3 more fields version 3.2
									+" LOGPARENT.LOGIN_NAME as Parent_Login_ID, " 
									+" (SELECT LEVEL_NAME FROM VR_ACCOUNT_LEVEL_MASTER where LEVEL_ID =5) as Parent_Account_Type, " 
									+" ACCPARENT.ACCOUNT_NAME as Parent_Account_Name, 			 "
									+" (SELECT CIRCLE_NAME FROM VR_CIRCLE_MASTER where CIRCLE_ID = a.CIRCLE_ID) as Circle, " 
									+" (SELECT CITY_NAME FROM VR_CITY_MASTER where CITY_ID = a.CITY_ID) as City,  "
									+" ( select WH_NAME from DP_WH_MASTER where WH_CODE in(select wh_code from DPDTH.DP_WH_DIST_MAP where DISTID = A.ACCOUNT_ID) )  as WH_NAME, "
									+" a.EMAIL as Email,  "
									+" a.MOBILE_NUMBER as Mobile, " 
									+" a.ACCOUNT_ADDRESS as Address, " 
									+" a.PIN_NUMBER as Pin,  "
									+" (SELECT ZONE_NAME FROM DP_ZONE_MASTER where ZONE_ID = a.ZONE_ID) as Zone_Name, " 
									+" c.EXT_DIST_ID as BoTree_Code, " 
									+" a.DISTRIBUTOR_LOCATOR as Oracle_Locator,  "
									+" b.STATUS as Status,  "
									+" DATE(a.CREATED_DT) as Created_Date, " 
									+" (SELECT ACCOUNT_NAME FROM VR_ACCOUNT_DETAILS where ACCOUNT_ID = a.CREATED_BY) as Created_By, " 
									+" DATE(a.UPDATED_DT) as Last_Updated_Date,  "
									+" (SELECT ACCOUNT_NAME FROM VR_ACCOUNT_DETAILS where ACCOUNT_ID = a.UPDATED_BY ) as Last_Updated_By, "  
									+" coalesce(to_char(b.LAST_LOGIN_TIME,'dd/MM/yyyy'),'') as Last_Login_date,   "
									+" case when b.LAST_LOGIN_TIME is null then coalesce(to_char((b.IPWD_CHANGED_DT+ 45 DAYS),'dd/MM/yyyy'),'') else coalesce(to_char((b.IPWD_CHANGED_DT),'dd/MM/yyyy'),'') " 
									+" end as IPWD_CHANGED_DT ,  "
									//+" case when a.ACCOUNT_LEVEL!=5 then '' when a.TRANSACTION_TYPE=1 then 'Sales' else 'CPE' end as TRANSACTION_TYPE" 
									+"	case c.TRANSACTION_TYPE_ID when 1 then 'Sales' else 'CPE' end as TRANSACTION_TYPE " 

									+" from VR_LOGIN_MASTER b , VR_ACCOUNT_DETAILS a  ,DP_DISTRIBUTOR_MAPPING c ,VR_LOGIN_MASTER LOGPARENT , VR_ACCOUNT_DETAILS ACCPARENT "
									+" where a.ACCOUNT_ID=b.LOGIN_ID and b.STATUS IN( "+status+ ") "
									+" and c.DP_DIST_ID = a.account_id "
									+" and c.PARENT_ACCOUNT = LOGPARENT.login_id "
									+" and ACCPARENT.account_id=c.PARENT_ACCOUNT ";
						circleFilter = "a";
						flag = "true";
					}
					
					else if(accountMultiSelectedIdsArr[i].equalsIgnoreCase("7"))
					{
						
								sql = "SELECT LOGIN_NAME as Login_ID, a.ACCOUNT_LEVEL, " +
								"			(SELECT LEVEL_NAME FROM VR_ACCOUNT_LEVEL_MASTER where LEVEL_ID = a.ACCOUNT_LEVEL) as Account_Type," +
								"			ACCOUNT_NAME as Contact_Name," +
								"			LAPU_MOBILE_NO as Lapu_No,a.LAPU_MOBILE_NO_2,a.FTA_MOBILE_NO,a.FTA_MOBILE_NO_2, " + //changed by neetika to add 3 more fields version 3.2
								"			(SELECT LOGIN_NAME FROM VR_LOGIN_MASTER where LOGIN_ID = a.PARENT_ACCOUNT) as Parent_Login_ID," +
								"			(SELECT LEVEL_NAME FROM VR_ACCOUNT_LEVEL_MASTER where LEVEL_ID = a.ACCOUNT_LEVEL -1) as Parent_Account_Type," +
								"			(SELECT ACCOUNT_NAME FROM VR_ACCOUNT_DETAILS where ACCOUNT_ID = a.PARENT_ACCOUNT) as Parent_Account_Name," +			
								"			(SELECT CIRCLE_NAME FROM VR_CIRCLE_MASTER where CIRCLE_ID = a.CIRCLE_ID) as Circle," +
								"			(SELECT CITY_NAME FROM VR_CITY_MASTER where CITY_ID = a.CITY_ID) as City," +
								//"			( select WH_NAME from DP_WH_MASTER where WH_CODE in(select wh_code from DPDTH.DP_WH_DIST_MAP where DISTID = A.ACCOUNT_ID) )  as WH_NAME,"+
								"			''  as WH_NAME,"+ //Changed by Neetika as WH name is not required for this account level it impacts the performance of report
								"			EMAIL as Email," +
								"			MOBILE_NUMBER as Mobile," +
								"			ACCOUNT_ADDRESS as Address," +
								"			PIN_NUMBER as Pin," +
								"			(SELECT ZONE_NAME FROM DP_ZONE_MASTER where ZONE_ID = a.ZONE_ID) as Zone_Name," +
								//"			(SELECT EXT_DIST_ID FROM DP_DISTRIBUTOR_MAPPING where DP_DIST_ID = a.ACCOUNT_ID FETCH FIRST 1 row only) as BoTree_Code," +
								"			'' as BoTree_Code," + //Changed by Neetika as it is not required for this account level it impacts the performance of report
								//"			DISTRIBUTOR_LOCATOR as Oracle_Locator," +
								"			'' as Oracle_Locator," + //Changed by Neetika as it is not required for this account level it impacts the performance of report
								"			STATUS as Status," +
								"			DATE(CREATED_DT) as Created_Date," +
								"			(SELECT ACCOUNT_NAME FROM VR_ACCOUNT_DETAILS where ACCOUNT_ID = a.CREATED_BY) as Created_By," +
								"			DATE(UPDATED_DT) as Last_Updated_Date," +
								"			(SELECT ACCOUNT_NAME FROM VR_ACCOUNT_DETAILS where ACCOUNT_ID = a.UPDATED_BY ) as Last_Updated_By, " +
								"			coalesce(to_char(LAST_LOGIN_TIME,'dd/MM/yyyy'),'') as Last_Login_date, " +
					"			case when LAST_LOGIN_TIME is null then coalesce(to_char((IPWD_CHANGED_DT+45 DAYS),'dd/MM/yyyy'),'') else coalesce(to_char((IPWD_CHANGED_DT),'dd/MM/yyyy'),'') end as IPWD_CHANGED_DT, " +
					"			case when a.ACCOUNT_LEVEL!=5 then '' when a.TRANSACTION_TYPE=1 then 'Sales' else 'CPE' end as TRANSACTION_TYPE" +
								"			from VR_LOGIN_MASTER b , VR_ACCOUNT_DETAILS a " +
								"			where a.ACCOUNT_ID=b.LOGIN_ID and b.STATUS IN( "+status+ ") ";
							
						circleFilter = "a";
						flag = "true";
					}
					
					else if(accountMultiSelectedIdsArr[i].equalsIgnoreCase("8"))
					{
								sql = "SELECT LOGIN_NAME as Login_ID, a.ACCOUNT_LEVEL, " +
								"			(SELECT LEVEL_NAME FROM VR_ACCOUNT_LEVEL_MASTER where LEVEL_ID = a.ACCOUNT_LEVEL) as Account_Type," +
								"			ACCOUNT_NAME as Contact_Name," +
								"			LAPU_MOBILE_NO as Lapu_No,a.LAPU_MOBILE_NO_2,a.FTA_MOBILE_NO,a.FTA_MOBILE_NO_2, " + //changed by neetika to add 3 more fields version 3.2
								"			(SELECT LOGIN_NAME FROM VR_LOGIN_MASTER where LOGIN_ID = a.PARENT_ACCOUNT) as Parent_Login_ID," +
								"			(SELECT LEVEL_NAME FROM VR_ACCOUNT_LEVEL_MASTER where LEVEL_ID = a.ACCOUNT_LEVEL -1) as Parent_Account_Type," +
								"			(SELECT ACCOUNT_NAME FROM VR_ACCOUNT_DETAILS where ACCOUNT_ID = a.PARENT_ACCOUNT) as Parent_Account_Name," +			
								"			(SELECT CIRCLE_NAME FROM VR_CIRCLE_MASTER where CIRCLE_ID = a.CIRCLE_ID) as Circle," +
								"			(SELECT CITY_NAME FROM VR_CITY_MASTER where CITY_ID = a.CITY_ID) as City," +
								//"			( select WH_NAME from DP_WH_MASTER where WH_CODE in(select wh_code from DPDTH.DP_WH_DIST_MAP where DISTID = A.ACCOUNT_ID) )  as WH_NAME,"+
								"			''  as WH_NAME,"+ //Changed by Neetika as WH name is not required for this account level it impacts the performance of report
								"			EMAIL as Email," +
								"			RETAILER_LAPU as Mobile," + //Neetika changed frm mobile_no to retailer_lapu
								"			ACCOUNT_ADDRESS as Address," +
								"			PIN_NUMBER as Pin," +
								"			(SELECT ZONE_NAME FROM DP_ZONE_MASTER where ZONE_ID = a.ZONE_ID) as Zone_Name," +
								//"			(SELECT EXT_DIST_ID FROM DP_DISTRIBUTOR_MAPPING where DP_DIST_ID = a.ACCOUNT_ID FETCH FIRST 1 row only) as BoTree_Code," +
								"			'' as BoTree_Code," + //Changed by Neetika as it is not required for this account level it impacts the performance of report
								//"			DISTRIBUTOR_LOCATOR as Oracle_Locator," +
								"			'' as Oracle_Locator," + //Changed by Neetika as it is not required for this account level it impacts the performance of report
								"			STATUS as Status," +
								"			DATE(CREATED_DT) as Created_Date," +
								"			(SELECT ACCOUNT_NAME FROM VR_ACCOUNT_DETAILS where ACCOUNT_ID = a.CREATED_BY) as Created_By," +
								"			DATE(UPDATED_DT) as Last_Updated_Date," +
								"			(SELECT ACCOUNT_NAME FROM VR_ACCOUNT_DETAILS where ACCOUNT_ID = a.UPDATED_BY ) as Last_Updated_By, " +
								"			coalesce(to_char(LAST_LOGIN_TIME,'dd/MM/yyyy'),'') as Last_Login_date, " +
								"			case when LAST_LOGIN_TIME is null then coalesce(to_char((IPWD_CHANGED_DT+45 DAYS),'dd/MM/yyyy'),'') else coalesce(to_char((IPWD_CHANGED_DT),'dd/MM/yyyy'),'') end as IPWD_CHANGED_DT, " +
								"			case when a.ACCOUNT_LEVEL!=5 then '' when a.TRANSACTION_TYPE=1 then 'Sales' else 'CPE' end as TRANSACTION_TYPE " + //Neetika code merge with prod
								"			from VR_LOGIN_MASTER b , VR_ACCOUNT_DETAILS a " +
								"			where a.ACCOUNT_ID=b.LOGIN_ID and b.STATUS IN( "+status+ ") ";
							
						circleFilter = "a";
						flag = "true";
					}
					
					else if(accountMultiSelectedIdsArr[i].equalsIgnoreCase("2"))
					{
								sql = "SELECT LOGIN_NAME as Login_ID, a.ACCOUNT_LEVEL, " +
								"			(SELECT LEVEL_NAME FROM VR_ACCOUNT_LEVEL_MASTER where LEVEL_ID = a.ACCOUNT_LEVEL) as Account_Type," +
								"			ACCOUNT_NAME as Contact_Name," +
								"			LAPU_MOBILE_NO as Lapu_No,a.LAPU_MOBILE_NO_2,a.FTA_MOBILE_NO,a.FTA_MOBILE_NO_2, " + //changed by neetika to add 3 more fields version 3.2
								"			(SELECT LOGIN_NAME FROM VR_LOGIN_MASTER where LOGIN_ID = a.PARENT_ACCOUNT) as Parent_Login_ID," +
								"			(SELECT LEVEL_NAME FROM VR_ACCOUNT_LEVEL_MASTER where LEVEL_ID = a.ACCOUNT_LEVEL -1) as Parent_Account_Type," +
								"			(SELECT ACCOUNT_NAME FROM VR_ACCOUNT_DETAILS where ACCOUNT_ID = a.PARENT_ACCOUNT) as Parent_Account_Name," +			
								"			(SELECT CIRCLE_NAME FROM VR_CIRCLE_MASTER where CIRCLE_ID = map1.CIRCLE_ID) as Circle," +
								"			(SELECT CITY_NAME FROM VR_CITY_MASTER where CITY_ID = a.CITY_ID) as City," +
								//"			( select WH_NAME from DP_WH_MASTER where WH_CODE in(select wh_code from DPDTH.DP_WH_DIST_MAP where DISTID = A.ACCOUNT_ID) )  as WH_NAME,"+
								"			''  as WH_NAME,"+ //Changed by Neetika as WH name is not required for this account level it impacts the performance of report
								"			EMAIL as Email," +
								"			MOBILE_NUMBER as Mobile," +
								"			ACCOUNT_ADDRESS as Address," +
								"			PIN_NUMBER as Pin," +
								"			(SELECT ZONE_NAME FROM DP_ZONE_MASTER where ZONE_ID = a.ZONE_ID) as Zone_Name," +
							//	"			(SELECT EXT_DIST_ID FROM DP_DISTRIBUTOR_MAPPING where DP_DIST_ID = a.ACCOUNT_ID FETCH FIRST 1 row only) as BoTree_Code," +
								"			'' as BoTree_Code," + //Changed by Neetika as it is not required for this account level it impacts the performance of report
								//"			DISTRIBUTOR_LOCATOR as Oracle_Locator," +
								"			'' as Oracle_Locator," + //Changed by Neetika as it is not required for this account level it impacts the performance of report
								"			STATUS as Status," +
								"			DATE(CREATED_DT) as Created_Date," +
								"			(SELECT ACCOUNT_NAME FROM VR_ACCOUNT_DETAILS where ACCOUNT_ID = a.CREATED_BY) as Created_By," +
								"			DATE(UPDATED_DT) as Last_Updated_Date," +
								"			(SELECT ACCOUNT_NAME FROM VR_ACCOUNT_DETAILS where ACCOUNT_ID = a.UPDATED_BY ) as Last_Updated_By, " +
								"			coalesce(to_char(LAST_LOGIN_TIME,'dd/MM/yyyy'),'') as Last_Login_date, " +
								"			case when LAST_LOGIN_TIME is null then coalesce(to_char((IPWD_CHANGED_DT+45 DAYS),'dd/MM/yyyy'),'') else coalesce(to_char((IPWD_CHANGED_DT),'dd/MM/yyyy'),'') end as IPWD_CHANGED_DT, " +
								"			case when a.ACCOUNT_LEVEL!=5 then '' when a.TRANSACTION_TYPE=1 then 'Sales' else 'CPE' end as TRANSACTION_TYPE " + //Neetika code merge with prod
								"			from VR_LOGIN_MASTER b , VR_ACCOUNT_DETAILS a left outer join  DP_ACCOUNT_CIRCLE_MAP map1 on map1.account_id= a.account_id   " +
								"			where a.ACCOUNT_ID=b.LOGIN_ID and b.STATUS IN( "+status+ ") ";
							
						circleFilter = "map1";
						flag = "true";
					}
					
					if(flag != null && flag.equalsIgnoreCase("true"))  {
					if(reportDto.getSearchOption().equalsIgnoreCase("-1")) {
						
					}
					else if(reportDto.getSearchOption().equalsIgnoreCase("LOGIN_ID")) {
						searchCriteria = " AND b.LOGIN_ID = (SELECT LOGIN_ID FROM VR_LOGIN_MASTER WHERE UPPER(LOGIN_NAME) ='" +reportDto.getSearchCriteria().toUpperCase() +"')";
					}
					else if(reportDto.getSearchOption().equalsIgnoreCase("ACCOUNT_NAME")) {
						searchCriteria = " AND a.ACCOUNT_ID = (SELECT ACCOUNT_ID FROM VR_ACCOUNT_DETAILS WHERE UPPER(ACCOUNT_NAME) ='" +reportDto.getSearchCriteria().toUpperCase() +"')";
					}
					else if(reportDto.getSearchOption().equalsIgnoreCase("MOBILE_NO")) {
						searchCriteria = " AND ((a.MOBILE_NUMBER = '"+reportDto.getSearchCriteria()+"' and a.MOBILE_NUMBER is not null) or (a.RETAILER_LAPU is not null and a.RETAILER_LAPU = '"+reportDto.getSearchCriteria()+"'))";  //Neetika REG change
					}
					else if(reportDto.getSearchOption().equalsIgnoreCase("EMAIL_ID")) {
						searchCriteria = " AND UPPER(a.EMAIL) = '" +reportDto.getSearchCriteria().toUpperCase()+"'";
					}
					else if(reportDto.getSearchOption().equalsIgnoreCase("PIN_CODE")) {
						searchCriteria = " AND a.PIN_NUMBER = "+ reportDto.getSearchCriteria();
					}
					else if(reportDto.getSearchOption().equalsIgnoreCase("CITY")) {
						searchCriteria = " AND a.CITY_ID = (SELECT CITY_ID FROM VR_CITY_MASTER WHERE UPPER(CITY_NAME) = '" +reportDto.getSearchCriteria().toUpperCase()+"')";
					}

					
					
					System.out.println("reportDto.getAccountLevel():::::"+reportDto.getAccountLevel());
					System.out.println("accountMultiSelectedIdsArr[i]:::::"+accountMultiSelectedIdsArr[i]);
					System.out.println("reportDto.getHiddenCircleSelecIds():::::"+reportDto.getHiddenCircleSelecIds());
					
					if(reportDto.getAccountLevel() == 5) {
						System.out.println("getAccountLevel == 5 block");
						if(accountMultiSelectedIdsArr[i].equals("2,3,4,5,6,7,8")) {
							System.out.println("accountMultiSelectedIdsArr[i].equals -1 block");
							query1 = sql + " AND c.PARENT_ACCOUNT IN ("+reportDto.getAccountId()+")";
							query1 += searchCriteria.toString();
				
							query2 = 	" UNION ALL " + sql + " AND a.PARENT_ACCOUNT IN (SELECT DP_DIST_ID FROM DP_DISTRIBUTOR_MAPPING where PARENT_ACCOUNT IN ("+reportDto.getAccountId()+"))";;
							query2 += searchCriteria.toString();
							
							query3 =	" UNION ALL " + sql + " AND a.PARENT_ACCOUNT IN (SELECT ACCOUNT_ID FROM VR_ACCOUNT_DETAILS where PARENT_ACCOUNT IN (SELECT DP_DIST_ID FROM DP_DISTRIBUTOR_MAPPING where PARENT_ACCOUNT IN ("+reportDto.getAccountId()+")))";;
							query3 += searchCriteria.toString();
							
							sql = query1 + query2 + query3;
							
						} else if(accountMultiSelectedIdsArr[i].equals("6")) {
							sql +=" AND c.PARENT_ACCOUNT IN ("+reportDto.getAccountId()+")";
						} else if (accountMultiSelectedIdsArr[i].equals("7")) {
							sql +=" AND a.PARENT_ACCOUNT IN (SELECT DP_DIST_ID FROM DP_DISTRIBUTOR_MAPPING where PARENT_ACCOUNT IN ("+reportDto.getAccountId()+"))";
						} else if (accountMultiSelectedIdsArr[i].equals("8")) {
							sql +=" AND a.PARENT_ACCOUNT IN (SELECT ACCOUNT_ID FROM VR_ACCOUNT_DETAILS where PARENT_ACCOUNT IN (SELECT DP_DIST_ID FROM DP_DISTRIBUTOR_MAPPING where PARENT_ACCOUNT IN ("+reportDto.getAccountId()+")))";
						}
					} else if (reportDto.getAccountLevel() == 6) {
						if(accountMultiSelectedIdsArr[i].equals("-1")) {
							query1 = sql + " AND a.PARENT_ACCOUNT IN ("+reportDto.getAccountId()+")";
							query1 += searchCriteria.toString();
				
							query2 = 	" UNION ALL " + sql + " AND a.PARENT_ACCOUNT IN (SELECT ACCOUNT_ID FROM VR_ACCOUNT_DETAILS where PARENT_ACCOUNT IN ("+reportDto.getAccountId()+"))";
							query2 += searchCriteria.toString();
											
							sql = query1 + query2;
								
						} else if (accountMultiSelectedIdsArr[i].equals("7")) {
							sql +=" AND a.PARENT_ACCOUNT IN ("+reportDto.getAccountId()+")";
						} else if (accountMultiSelectedIdsArr[i].equals("8")) {
							sql +=" AND a.PARENT_ACCOUNT IN (SELECT ACCOUNT_ID FROM VR_ACCOUNT_DETAILS where PARENT_ACCOUNT IN ("+reportDto.getAccountId()+"))";
						}

					} else if(accountMultiSelectedIdsArr[i].equals("2,3,4,5,6,7,8") && reportDto.getAccountLevel() == 0 ){
						if(!reportDto.getHiddenCircleSelecIds().equals("0")) {
							sql = sql + " and a.ACCOUNT_LEVEL > " + reportDto.getAccountLevel() +" and "+circleFilter+".CIRCLE_ID IN(0,"+ reportDto.getHiddenCircleSelecIds() +")";
						} else {
							sql = sql + " and a.ACCOUNT_LEVEL > " + reportDto.getAccountLevel();
						}
					} else if (accountMultiSelectedIdsArr[i].equals("1")) {
						sql = sql + " and a.ACCOUNT_LEVEL = " + accountMultiSelectedIdsArr[i];
					} else if (accountMultiSelectedIdsArr[i].equals("2,3,4,5,6,7,8")) {	
						if(!reportDto.getHiddenCircleSelecIds().equals("0")) {
							sql = sql + " and a.ACCOUNT_LEVEL > " + reportDto.getAccountLevel() +" and "+circleFilter+".CIRCLE_ID IN("+ reportDto.getHiddenCircleSelecIds() +")";
						} else {
							sql = sql + " and a.ACCOUNT_LEVEL > " + reportDto.getAccountLevel();
						}
					} else {
						if(!reportDto.getHiddenCircleSelecIds().equals("0")) {
							sql = sql + " and a.ACCOUNT_LEVEL = " + accountMultiSelectedIdsArr[i] +" and "+circleFilter+".CIRCLE_ID IN("+ reportDto.getHiddenCircleSelecIds() +")";
						} else {
							sql = sql + " and a.ACCOUNT_LEVEL = " + accountMultiSelectedIdsArr[i];
						}
					}
					
					
						String orderBy = " ORDER BY CIRCLE, ACCOUNT_LEVEL ASC" ;
					
					
					
						sql = sql + searchCriteria + orderBy;
					}
					System.out.println(accountMultiSelectedIdsArr[i]);
					System.out.println(reportDto.getAccountLevel() );
					
					System.out.println(sql);
					ps=null;
					ps = connection.prepareStatement(sql);			
					rsetReport=null;	
					rsetReport = ps.executeQuery();
					AccountDetailReportDto reportReturnDto =null;
			
					logger.info(" Executing query for :"+accountMultiSelectedIdsArr[i]);
					
					while(rsetReport.next()){
						reportReturnDto =new AccountDetailReportDto();
						reportReturnDto.setDistLoginId(rsetReport.getString("LOGIN_ID"));
						reportReturnDto.setAccountType(rsetReport.getString("ACCOUNT_TYPE"));
						reportReturnDto.setAccountName(rsetReport.getString("CONTACT_NAME"));
						reportReturnDto.setLapuNo(rsetReport.getString("Lapu_No"));
						//added by neetika
						reportReturnDto.setLapuNo2(rsetReport.getString("LAPU_MOBILE_NO_2"));
						reportReturnDto.setFtaNo(rsetReport.getString("FTA_MOBILE_NO"));
						reportReturnDto.setFtaNo2(rsetReport.getString("FTA_MOBILE_NO_2"));
						
						//end
						reportReturnDto.setParentLoginId(rsetReport.getString("PARENT_LOGIN_ID"));
						reportReturnDto.setParentAccountType(rsetReport.getString("PARENT_ACCOUNT_TYPE"));	
						reportReturnDto.setParentAccountName(rsetReport.getString("PARENT_ACCOUNT_NAME"));				
						reportReturnDto.setCircle(rsetReport.getString("CIRCLE"));
						reportReturnDto.setCity(rsetReport.getString("CITY")); 
						reportReturnDto.setEmailId(rsetReport.getString("EMAIL"));
						reportReturnDto.setMobileNo(rsetReport.getString("Mobile"));
						reportReturnDto.setAddress(rsetReport.getString("ADDRESS"));
						reportReturnDto.setPin(rsetReport.getString("PIN"));
						reportReturnDto.setZone(rsetReport.getString("ZONE_NAME"));
						reportReturnDto.setBotreeCode(rsetReport.getString("BOTREE_CODE"));
						reportReturnDto.setOracleLocatorCode(rsetReport.getString("ORACLE_LOCATOR"));
						if (rsetReport.getString("STATUS").equalsIgnoreCase("A")){	reportReturnDto.setStatus("Active");
						}
						else  if (rsetReport.getString("STATUS").equalsIgnoreCase("I"))
						{ 
							reportReturnDto.setStatus("Inactive"); 
						}
						else  if (rsetReport.getString("STATUS").equalsIgnoreCase("L"))
						{ 
							reportReturnDto.setStatus("Inactive Due to Lapu Tree"); 
						}
						else  if (rsetReport.getString("STATUS").equalsIgnoreCase("C"))
						{ 
							reportReturnDto.setStatus("Close"); 
						}
						//reportReturnDto.setStatus(rsetReport.getString("STATUS"));
						reportReturnDto.setCreatedDate(Utility.getDateAsString(rsetReport.getDate("CREATED_DATE"), "dd-MMM-yyyy"));
						reportReturnDto.setCreatedBy(rsetReport.getString("CREATED_BY"));
						reportReturnDto.setLastUpdatedDate(Utility.getDateAsString(rsetReport.getDate("LAST_UPDATED_DATE"), "dd-MMM-yyyy"));
						reportReturnDto.setLastUpdatedBy(rsetReport.getString("LAST_UPDATED_BY"));
						reportReturnDto.setLastLoginDate((rsetReport.getString("LAST_LOGIN_DATE")));
						reportReturnDto.setWhName(rsetReport.getString("WH_NAME"));
						reportReturnDto.setPassChangeDT(rsetReport.getString("IPWD_CHANGED_DT"));
						reportReturnDto.setTransType(rsetReport.getString("TRANSACTION_TYPE"));

						listReturn.add(reportReturnDto);
					}
					ps=null;
					rsetReport=null;
					sql=null;
					logger.info(" Executed query for :"+accountMultiSelectedIdsArr[i]);
				}// end of if for not -1
				/*else if(accountMultiSelectedIdsArr[i].equals("-1") ){ // it is for all 
					
					System.out.println(" ALL ALL ALL ALL ALL ALL ALL ALL ALL ALL ALL ALL");
						sql = "SELECT LOGIN_NAME as Login_ID, a.ACCOUNT_LEVEL, " +
						"			(SELECT LEVEL_NAME FROM VR_ACCOUNT_LEVEL_MASTER where LEVEL_ID = a.ACCOUNT_LEVEL) as Account_Type," +
						"			ACCOUNT_NAME as Contact_Name," +
						"			LAPU_MOBILE_NO as Lapu_No," +
						"			(SELECT LOGIN_NAME FROM VR_LOGIN_MASTER where LOGIN_ID = a.PARENT_ACCOUNT) as Parent_Login_ID," +
						"			(SELECT LEVEL_NAME FROM VR_ACCOUNT_LEVEL_MASTER where LEVEL_ID = a.ACCOUNT_LEVEL -1) as Parent_Account_Type," +
						"			(SELECT ACCOUNT_NAME FROM VR_ACCOUNT_DETAILS where ACCOUNT_ID = a.PARENT_ACCOUNT) as Parent_Account_Name," +			
						"			(SELECT CIRCLE_NAME FROM VR_CIRCLE_MASTER where CIRCLE_ID = map1.CIRCLE_ID fetch first 1 row only ) as Circle," +
						"			(SELECT CITY_NAME FROM VR_CITY_MASTER where CITY_ID = a.CITY_ID) as City," +
						"			( select WH_NAME from DP_WH_MASTER where WH_CODE in(select wh_code from DPDTH.DP_WH_DIST_MAP where DISTID = A.ACCOUNT_ID) )  as WH_NAME,"+
						"			EMAIL as Email," +
						"			MOBILE_NUMBER as Mobile," +
						"			ACCOUNT_ADDRESS as Address," +
						"			PIN_NUMBER as Pin," +
						"			(SELECT ZONE_NAME FROM DP_ZONE_MASTER where ZONE_ID = a.ZONE_ID) as Zone_Name," +
						"			(SELECT EXT_DIST_ID FROM DP_DISTRIBUTOR_MAPPING where DP_DIST_ID = a.ACCOUNT_ID FETCH FIRST 1 row only) as BoTree_Code," +
						"			DISTRIBUTOR_LOCATOR as Oracle_Locator," +
						"			STATUS as Status," +
						"			DATE(CREATED_DT) as Created_Date," +
						"			(SELECT ACCOUNT_NAME FROM VR_ACCOUNT_DETAILS where ACCOUNT_ID = a.CREATED_BY) as Created_By," +
						"			DATE(UPDATED_DT) as Last_Updated_Date," +
						"			(SELECT ACCOUNT_NAME FROM VR_ACCOUNT_DETAILS where ACCOUNT_ID = a.UPDATED_BY ) as Last_Updated_By," +
						"			coalesce(to_char(LAST_LOGIN_TIME,'dd/MM/yyyy'),'') as Last_Login_date, " +
						"			case when LAST_LOGIN_TIME is null then coalesce(to_char((IPWD_CHANGED_DT+45 DAYS),'dd/MM/yyyy'),'') else coalesce(to_char((IPWD_CHANGED_DT),'dd/MM/yyyy'),'') end as IPWD_CHANGED_DT " +
						"			from VR_LOGIN_MASTER b , VR_ACCOUNT_DETAILS a left outer join  DP_ACCOUNT_CIRCLE_MAP map1 on map1.account_id= a.account_id  " +
						"			where a.ACCOUNT_ID=b.LOGIN_ID and b.STATUS IN( "+status+ ") and a.ACCOUNT_LEVEL !=6 " ;
						
						sql2 = " 				UNION " +
						" 		SELECT LOGIN_NAME as Login_ID, a.ACCOUNT_LEVEL, " +
						"			(SELECT LEVEL_NAME FROM VR_ACCOUNT_LEVEL_MASTER where LEVEL_ID = a.ACCOUNT_LEVEL) as Account_Type," +
						"			ACCOUNT_NAME as Contact_Name," +
						"			LAPU_MOBILE_NO as Lapu_No," +
						"			(SELECT LOGIN_NAME FROM VR_LOGIN_MASTER where LOGIN_ID = a.PARENT_ACCOUNT) as Parent_Login_ID," +
						"			(SELECT LEVEL_NAME FROM VR_ACCOUNT_LEVEL_MASTER where LEVEL_ID = a.ACCOUNT_LEVEL -1) as Parent_Account_Type," +
						"			(SELECT ACCOUNT_NAME FROM VR_ACCOUNT_DETAILS where ACCOUNT_ID = a.PARENT_ACCOUNT) as Parent_Account_Name," +			
						"			(SELECT CIRCLE_NAME FROM VR_CIRCLE_MASTER where CIRCLE_ID = a.CIRCLE_ID) as Circle," +
						"			(SELECT CITY_NAME FROM VR_CITY_MASTER where CITY_ID = a.CITY_ID) as City," +
						"			( select WH_NAME from DP_WH_MASTER where WH_CODE in(select wh_code from DPDTH.DP_WH_DIST_MAP where DISTID = A.ACCOUNT_ID) )  as WH_NAME,"+
						"			EMAIL as Email," +
						"			MOBILE_NUMBER as Mobile," +
						"			ACCOUNT_ADDRESS as Address," +
						"			PIN_NUMBER as Pin," +
						"			(SELECT ZONE_NAME FROM DP_ZONE_MASTER where ZONE_ID = a.ZONE_ID) as Zone_Name," +
						"			(SELECT EXT_DIST_ID FROM DP_DISTRIBUTOR_MAPPING where DP_DIST_ID = a.ACCOUNT_ID FETCH FIRST 1 row only) as BoTree_Code," +
						"			DISTRIBUTOR_LOCATOR as Oracle_Locator," +
						"			STATUS as Status," +
						"			DATE(CREATED_DT) as Created_Date," +
						"			(SELECT ACCOUNT_NAME FROM VR_ACCOUNT_DETAILS where ACCOUNT_ID = a.CREATED_BY) as Created_By," +
						"			DATE(UPDATED_DT) as Last_Updated_Date," +
						"			(SELECT ACCOUNT_NAME FROM VR_ACCOUNT_DETAILS where ACCOUNT_ID = a.UPDATED_BY ) as Last_Updated_By," +
						"			coalesce(to_char(LAST_LOGIN_TIME,'dd/MM/yyyy'),'') as Last_Login_date, " +
						"			case when LAST_LOGIN_TIME is null then coalesce(to_char((IPWD_CHANGED_DT+45 DAYS),'dd/MM/yyyy'),'') else coalesce(to_char((IPWD_CHANGED_DT),'dd/MM/yyyy'),'') end as IPWD_CHANGED_DT " +
						"			from VR_LOGIN_MASTER b , VR_ACCOUNT_DETAILS a " +
						"			where a.ACCOUNT_ID=b.LOGIN_ID and b.STATUS IN( "+status+ ") and a.ACCOUNT_LEVEL !=6 ";
						
						sql3 = " 				UNION " +
						" 		SELECT b.LOGIN_NAME as Login_ID, a.ACCOUNT_LEVEL, " +
						"			(SELECT LEVEL_NAME FROM VR_ACCOUNT_LEVEL_MASTER where LEVEL_ID = a.ACCOUNT_LEVEL) as Account_Type," +
						"			a.ACCOUNT_NAME as Contact_Name," +
						"			a.LAPU_MOBILE_NO as Lapu_No," +
						"			LOGPARENT.LOGIN_NAME as Parent_Login_ID, " +
						"			(SELECT LEVEL_NAME FROM VR_ACCOUNT_LEVEL_MASTER where LEVEL_ID = a.ACCOUNT_LEVEL -1) as Parent_Account_Type," +
						"			ACCPARENT.ACCOUNT_NAME as Parent_Account_Name, 	" +			
						"			(SELECT CIRCLE_NAME FROM VR_CIRCLE_MASTER where CIRCLE_ID = a.CIRCLE_ID) as Circle," +
						"			(SELECT CITY_NAME FROM VR_CITY_MASTER where CITY_ID = a.CITY_ID) as City," +
						"			( select WH_NAME from DP_WH_MASTER where WH_CODE in(select wh_code from DPDTH.DP_WH_DIST_MAP where DISTID = A.ACCOUNT_ID) )  as WH_NAME,"+
						"			a.EMAIL as Email," +
						"			a.MOBILE_NUMBER as Mobile," +
						"			a.ACCOUNT_ADDRESS as Address," +
						"			a.PIN_NUMBER as Pin," +
						"			(SELECT ZONE_NAME FROM DP_ZONE_MASTER where ZONE_ID = a.ZONE_ID) as Zone_Name," +
						"			(SELECT EXT_DIST_ID FROM DP_DISTRIBUTOR_MAPPING where DP_DIST_ID = a.ACCOUNT_ID FETCH FIRST 1 row only) as BoTree_Code," +
						"			a.DISTRIBUTOR_LOCATOR as Oracle_Locator," +
						"			b.STATUS as Status," +
						"			DATE(a.CREATED_DT) as Created_Date," +
						"			(SELECT ACCOUNT_NAME FROM VR_ACCOUNT_DETAILS where ACCOUNT_ID = a.CREATED_BY) as Created_By," +
						"			DATE(a.UPDATED_DT) as Last_Updated_Date," +
						"			(SELECT ACCOUNT_NAME FROM VR_ACCOUNT_DETAILS where ACCOUNT_ID = a.UPDATED_BY ) as Last_Updated_By," +
						"			coalesce(to_char(b.LAST_LOGIN_TIME,'dd/MM/yyyy'),'') as Last_Login_date, " +
						"			case when b.LAST_LOGIN_TIME is null then coalesce(to_char((b.IPWD_CHANGED_DT+45 DAYS),'dd/MM/yyyy'),'') else coalesce(to_char((b.IPWD_CHANGED_DT),'dd/MM/yyyy'),'') end as IPWD_CHANGED_DT " +
						"			from VR_LOGIN_MASTER b , VR_ACCOUNT_DETAILS a ,DP_DISTRIBUTOR_MAPPING c ,VR_LOGIN_MASTER LOGPARENT , VR_ACCOUNT_DETAILS ACCPARENT " +
						"			where c.DP_DIST_ID = a.account_id  and c.PARENT_ACCOUNT = LOGPARENT.login_id and ACCPARENT.account_id=c.PARENT_ACCOUNT "+
						"			and a.ACCOUNT_ID=b.LOGIN_ID and b.STATUS IN( "+status+ ") and a.ACCOUNT_LEVEL =6 ";
						
						if(reportDto.getSearchOption().equalsIgnoreCase("-1")) {
							
						}
						else if(reportDto.getSearchOption().equalsIgnoreCase("LOGIN_ID")) {
							searchCriteria = " AND b.LOGIN_ID = (SELECT LOGIN_ID FROM VR_LOGIN_MASTER WHERE UPPER(LOGIN_NAME) ='" +reportDto.getSearchCriteria().toUpperCase() +"')";
						}
						else if(reportDto.getSearchOption().equalsIgnoreCase("ACCOUNT_NAME")) {
							searchCriteria = " AND a.ACCOUNT_ID = (SELECT ACCOUNT_ID FROM VR_ACCOUNT_DETAILS WHERE UPPER(ACCOUNT_NAME) ='" +reportDto.getSearchCriteria().toUpperCase() +"')";
						}
						else if(reportDto.getSearchOption().equalsIgnoreCase("MOBILE_NO")) {
							searchCriteria = " AND a.MOBILE_NUMBER = '"+reportDto.getSearchCriteria()+"'";
						}
						else if(reportDto.getSearchOption().equalsIgnoreCase("EMAIL_ID")) {
							searchCriteria = " AND UPPER(a.EMAIL) = '" +reportDto.getSearchCriteria().toUpperCase()+"'";
						}
						else if(reportDto.getSearchOption().equalsIgnoreCase("PIN_CODE")) {
							searchCriteria = " AND a.PIN_NUMBER = "+ reportDto.getSearchCriteria();
						}
						else if(reportDto.getSearchOption().equalsIgnoreCase("CITY")) {
							searchCriteria = " AND a.CITY_ID = (SELECT CITY_ID FROM VR_CITY_MASTER WHERE UPPER(CITY_NAME) = '" +reportDto.getSearchCriteria().toUpperCase()+"')";
						}

						
						
						System.out.println("reportDto.getAccountLevel():::::"+reportDto.getAccountLevel());
						System.out.println("accountType:::::"+accountType);
						System.out.println("reportDto.getHiddenCircleSelecIds():::::"+reportDto.getHiddenCircleSelecIds());
						
					  if(accountType.equals("-1") && reportDto.getAccountLevel() == 0 ){
							if(!reportDto.getHiddenCircleSelecIds().equals("0")) {
								sql = sql + " and a.ACCOUNT_LEVEL > " + reportDto.getAccountLevel() +" and map1.CIRCLE_ID IN(0,"+ reportDto.getHiddenCircleSelecIds() +")";
								sql2 = sql2 + " and a.ACCOUNT_LEVEL > " + reportDto.getAccountLevel() +" and a.CIRCLE_ID IN(0,"+ reportDto.getHiddenCircleSelecIds() +")";
								sql3 = sql3 + " and a.ACCOUNT_LEVEL > " + reportDto.getAccountLevel() +" and a.CIRCLE_ID IN(0,"+ reportDto.getHiddenCircleSelecIds() +")";
								
							} else {
								sql = sql + " and a.ACCOUNT_LEVEL > " + reportDto.getAccountLevel();
								sql2 = sql2 + " and a.ACCOUNT_LEVEL > " + reportDto.getAccountLevel();
								sql3 = sql3 + " and a.ACCOUNT_LEVEL > " + reportDto.getAccountLevel();
							}
						} 
					  else if (accountType.equals("1")) {
							sql = sql + " and a.ACCOUNT_LEVEL = " + accountType;
							sql2 = sql2 + " and a.ACCOUNT_LEVEL = " + accountType;
							sql3 = sql3 + " and a.ACCOUNT_LEVEL = " + accountType;
						} else if (accountType.equals("-1")) {	
							if(!reportDto.getHiddenCircleSelecIds().equals("0")) {
								sql = sql + " and a.ACCOUNT_LEVEL > " + reportDto.getAccountLevel() +" and map1.CIRCLE_ID IN("+ reportDto.getHiddenCircleSelecIds() +")";
								sql2 = sql2 + " and a.ACCOUNT_LEVEL > " + reportDto.getAccountLevel() +" and a.CIRCLE_ID IN("+ reportDto.getHiddenCircleSelecIds() +")";
								sql3 = sql3 + " and a.ACCOUNT_LEVEL > " + reportDto.getAccountLevel() +" and a.CIRCLE_ID IN("+ reportDto.getHiddenCircleSelecIds() +")";
							} else {
								sql = sql + " and a.ACCOUNT_LEVEL > " + reportDto.getAccountLevel();
								sql2 = sql2 + " and a.ACCOUNT_LEVEL > " + reportDto.getAccountLevel();
								sql3 = sql3 + " and a.ACCOUNT_LEVEL > " + reportDto.getAccountLevel();
							}
						} else {
							if(!reportDto.getHiddenCircleSelecIds().equals("0")) {
								sql = sql + " and a.ACCOUNT_LEVEL = " + accountType +" and map1.CIRCLE_ID IN("+ reportDto.getHiddenCircleSelecIds() +")";
								sql2 = sql2 + " and a.ACCOUNT_LEVEL = " + accountType +" and a.CIRCLE_ID IN("+ reportDto.getHiddenCircleSelecIds() +")";
								sql3 = sql3 + " and a.ACCOUNT_LEVEL = " + accountType +" and a.CIRCLE_ID IN("+ reportDto.getHiddenCircleSelecIds() +")";
							} else {
								sql = sql + " and a.ACCOUNT_LEVEL = " + accountType;
								sql2 = sql2 + " and a.ACCOUNT_LEVEL = " + accountType;
								sql3 = sql3 + " and a.ACCOUNT_LEVEL = " + accountType;
							}
						}
						
						
							String orderBy = " ORDER BY CIRCLE, ACCOUNT_LEVEL ASC" ;
						
						
						
							String sqlFinal = sql + searchCriteria + sql2 + searchCriteria +  sql3 + searchCriteria + orderBy;
							System.out.println(sqlFinal);
							sql=sqlFinal;
							

							System.out.println(sql);
							ps = connection.prepareStatement(sql);			

							rsetReport = ps.executeQuery();
							AccountDetailReportDto reportReturnDto =null;
					
							
							while(rsetReport.next()){
								reportReturnDto =new AccountDetailReportDto();
								reportReturnDto.setDistLoginId(rsetReport.getString("LOGIN_ID"));
								reportReturnDto.setAccountType(rsetReport.getString("ACCOUNT_TYPE"));
								reportReturnDto.setAccountName(rsetReport.getString("CONTACT_NAME"));
								reportReturnDto.setLapuNo(rsetReport.getString("Lapu_No"));
								reportReturnDto.setParentLoginId(rsetReport.getString("PARENT_LOGIN_ID"));
								reportReturnDto.setParentAccountType(rsetReport.getString("PARENT_ACCOUNT_TYPE"));	
								reportReturnDto.setParentAccountName(rsetReport.getString("PARENT_ACCOUNT_NAME"));				
								reportReturnDto.setCircle(rsetReport.getString("CIRCLE"));
								reportReturnDto.setCity(rsetReport.getString("CITY")); 
								reportReturnDto.setEmailId(rsetReport.getString("EMAIL"));
								reportReturnDto.setMobileNo(rsetReport.getString("Mobile"));
								reportReturnDto.setAddress(rsetReport.getString("ADDRESS"));
								reportReturnDto.setPin(rsetReport.getString("PIN"));
								reportReturnDto.setZone(rsetReport.getString("ZONE_NAME"));
								reportReturnDto.setBotreeCode(rsetReport.getString("BOTREE_CODE"));
								reportReturnDto.setOracleLocatorCode(rsetReport.getString("ORACLE_LOCATOR"));
								if (rsetReport.getString("STATUS").equalsIgnoreCase("A")){	reportReturnDto.setStatus("Active");
								} else { reportReturnDto.setStatus("Inactive"); }
								//reportReturnDto.setStatus(rsetReport.getString("STATUS"));
								reportReturnDto.setCreatedDate(Utility.getDateAsString(rsetReport.getDate("CREATED_DATE"), "dd-MMM-yyyy"));
								reportReturnDto.setCreatedBy(rsetReport.getString("CREATED_BY"));
								reportReturnDto.setLastUpdatedDate(Utility.getDateAsString(rsetReport.getDate("LAST_UPDATED_DATE"), "dd-MMM-yyyy"));
								reportReturnDto.setLastUpdatedBy(rsetReport.getString("LAST_UPDATED_BY"));
								reportReturnDto.setLastLoginDate((rsetReport.getString("LAST_LOGIN_DATE")));
								reportReturnDto.setWhName(rsetReport.getString("WH_NAME"));
								reportReturnDto.setPassChangeDT(rsetReport.getString("IPWD_CHANGED_DT"));
								listReturn.add(reportReturnDto);
							}
					
				}*/// end for all 
			}// end for for loop
			/*if(accountType != null && (accountType.equalsIgnoreCase("3") || accountType.equalsIgnoreCase("4") || accountType.equalsIgnoreCase("5") ) )
			{
				//System.out.println("1111111");
					sql = "SELECT LOGIN_NAME as Login_ID, a.ACCOUNT_LEVEL, " +
					"			(SELECT LEVEL_NAME FROM VR_ACCOUNT_LEVEL_MASTER where LEVEL_ID = a.ACCOUNT_LEVEL) as Account_Type," +
					"			ACCOUNT_NAME as Contact_Name," +
					"			LAPU_MOBILE_NO as Lapu_No," +
					"			(SELECT LOGIN_NAME FROM VR_LOGIN_MASTER where LOGIN_ID = a.PARENT_ACCOUNT) as Parent_Login_ID," +
					"			(SELECT LEVEL_NAME FROM VR_ACCOUNT_LEVEL_MASTER where LEVEL_ID = a.ACCOUNT_LEVEL -1) as Parent_Account_Type," +
					"			(SELECT ACCOUNT_NAME FROM VR_ACCOUNT_DETAILS where ACCOUNT_ID = a.PARENT_ACCOUNT) as Parent_Account_Name," +			
					"			(SELECT CIRCLE_NAME FROM VR_CIRCLE_MASTER where CIRCLE_ID = map1.CIRCLE_ID fetch first 1 row only ) as Circle," +
					"			(SELECT CITY_NAME FROM VR_CITY_MASTER where CITY_ID = a.CITY_ID) as City," +
					"			( select WH_NAME from DP_WH_MASTER where WH_CODE in(select wh_code from DPDTH.DP_WH_DIST_MAP where DISTID = A.ACCOUNT_ID) )  as WH_NAME,"+
					"			EMAIL as Email," +
					"			MOBILE_NUMBER as Mobile," +
					"			ACCOUNT_ADDRESS as Address," +
					"			PIN_NUMBER as Pin," +
					"			(SELECT ZONE_NAME FROM DP_ZONE_MASTER where ZONE_ID = a.ZONE_ID) as Zone_Name," +
					"			(SELECT EXT_DIST_ID FROM DP_DISTRIBUTOR_MAPPING where DP_DIST_ID = a.ACCOUNT_ID FETCH FIRST 1 row only) as BoTree_Code," +
					"			DISTRIBUTOR_LOCATOR as Oracle_Locator," +
					"			STATUS as Status," +
					"			DATE(CREATED_DT) as Created_Date," +
					"			(SELECT ACCOUNT_NAME FROM VR_ACCOUNT_DETAILS where ACCOUNT_ID = a.CREATED_BY) as Created_By," +
					"			DATE(UPDATED_DT) as Last_Updated_Date," +
					"			(SELECT ACCOUNT_NAME FROM VR_ACCOUNT_DETAILS where ACCOUNT_ID = a.UPDATED_BY ) as Last_Updated_By," +
					"			coalesce(to_char(LAST_LOGIN_TIME,'dd/MM/yyyy'),'') as Last_Login_date, " +
					"			case when LAST_LOGIN_TIME is null then coalesce(to_char((IPWD_CHANGED_DT+45 DAYS),'dd/MM/yyyy'),'') else coalesce(to_char((IPWD_CHANGED_DT),'dd/MM/yyyy'),'') end as IPWD_CHANGED_DT " +
					"			from VR_LOGIN_MASTER b , VR_ACCOUNT_DETAILS a left outer join  DP_ACCOUNT_CIRCLE_MAP map1 on map1.account_id= a.account_id  " +
					"			where a.ACCOUNT_ID=b.LOGIN_ID and b.STATUS IN( "+status+ ") ";
					
					circleFilter = "map1";
					flag = "true";
			}
			else if(accountType != null && (accountType.equalsIgnoreCase("6") || accountType.equalsIgnoreCase("7") || accountType.equalsIgnoreCase("8") ) || accountType.equalsIgnoreCase("2") )
			{
				//System.out.println("2222222");
				if(accountType != null && accountType.equalsIgnoreCase("6"))
				{
					//System.out.println("33333333");
						sql = "SELECT b.LOGIN_NAME as Login_ID, a.ACCOUNT_LEVEL,   "
							+" (SELECT LEVEL_NAME FROM VR_ACCOUNT_LEVEL_MASTER where LEVEL_ID = a.ACCOUNT_LEVEL) as Account_Type, " 
							+" a.ACCOUNT_NAME as Contact_Name,  "
							+" a.LAPU_MOBILE_NO as Lapu_No,  "
							+" LOGPARENT.LOGIN_NAME as Parent_Login_ID, " 
							+" (SELECT LEVEL_NAME FROM VR_ACCOUNT_LEVEL_MASTER where LEVEL_ID = a.ACCOUNT_LEVEL -1) as Parent_Account_Type, " 
							+" ACCPARENT.ACCOUNT_NAME as Parent_Account_Name, 			 "
							+" (SELECT CIRCLE_NAME FROM VR_CIRCLE_MASTER where CIRCLE_ID = a.CIRCLE_ID) as Circle, " 
							+" (SELECT CITY_NAME FROM VR_CITY_MASTER where CITY_ID = a.CITY_ID) as City,  "
							+" ( select WH_NAME from DP_WH_MASTER where WH_CODE in(select wh_code from DPDTH.DP_WH_DIST_MAP where DISTID = A.ACCOUNT_ID) )  as WH_NAME, "
							+" a.EMAIL as Email,  "
							+" a.MOBILE_NUMBER as Mobile, " 
							+" a.ACCOUNT_ADDRESS as Address, " 
							+" a.PIN_NUMBER as Pin,  "
							+" (SELECT ZONE_NAME FROM DP_ZONE_MASTER where ZONE_ID = a.ZONE_ID) as Zone_Name, " 
							+" (SELECT EXT_DIST_ID FROM DP_DISTRIBUTOR_MAPPING where DP_DIST_ID = a.ACCOUNT_ID FETCH FIRST 1 row only) as BoTree_Code, " 
							+" a.DISTRIBUTOR_LOCATOR as Oracle_Locator,  "
							+" b.STATUS as Status,  "
							+" DATE(a.CREATED_DT) as Created_Date, " 
							+" (SELECT ACCOUNT_NAME FROM VR_ACCOUNT_DETAILS where ACCOUNT_ID = a.CREATED_BY) as Created_By, " 
							+" DATE(a.UPDATED_DT) as Last_Updated_Date,  "
							+" (SELECT ACCOUNT_NAME FROM VR_ACCOUNT_DETAILS where ACCOUNT_ID = a.UPDATED_BY ) as Last_Updated_By, "  
							+" coalesce(to_char(b.LAST_LOGIN_TIME,'dd/MM/yyyy'),'') as Last_Login_date,   "
							+" case when b.LAST_LOGIN_TIME is null then coalesce(to_char((b.IPWD_CHANGED_DT+ 45 DAYS),'dd/MM/yyyy'),'') else coalesce(to_char((b.IPWD_CHANGED_DT),'dd/MM/yyyy'),'') " 
							+" end as IPWD_CHANGED_DT   "
							+" from VR_LOGIN_MASTER b , VR_ACCOUNT_DETAILS a  ,DP_DISTRIBUTOR_MAPPING c ,VR_LOGIN_MASTER LOGPARENT , VR_ACCOUNT_DETAILS ACCPARENT "
							+" where a.ACCOUNT_ID=b.LOGIN_ID and b.STATUS IN( "+status+ ") "
							+" and c.DP_DIST_ID = a.account_id "
							+" and c.PARENT_ACCOUNT = LOGPARENT.login_id "
							+" and ACCPARENT.account_id=c.PARENT_ACCOUNT ";
				}
				else {
					//System.out.println("4444444");
						sql = "SELECT LOGIN_NAME as Login_ID, a.ACCOUNT_LEVEL, " +
						"			(SELECT LEVEL_NAME FROM VR_ACCOUNT_LEVEL_MASTER where LEVEL_ID = a.ACCOUNT_LEVEL) as Account_Type," +
						"			ACCOUNT_NAME as Contact_Name," +
						"			LAPU_MOBILE_NO as Lapu_No," +
						"			(SELECT LOGIN_NAME FROM VR_LOGIN_MASTER where LOGIN_ID = a.PARENT_ACCOUNT) as Parent_Login_ID," +
						"			(SELECT LEVEL_NAME FROM VR_ACCOUNT_LEVEL_MASTER where LEVEL_ID = a.ACCOUNT_LEVEL -1) as Parent_Account_Type," +
						"			(SELECT ACCOUNT_NAME FROM VR_ACCOUNT_DETAILS where ACCOUNT_ID = a.PARENT_ACCOUNT) as Parent_Account_Name," +			
						"			(SELECT CIRCLE_NAME FROM VR_CIRCLE_MASTER where CIRCLE_ID = a.CIRCLE_ID) as Circle," +
						"			(SELECT CITY_NAME FROM VR_CITY_MASTER where CITY_ID = a.CITY_ID) as City," +
						"			( select WH_NAME from DP_WH_MASTER where WH_CODE in(select wh_code from DPDTH.DP_WH_DIST_MAP where DISTID = A.ACCOUNT_ID) )  as WH_NAME,"+
						"			EMAIL as Email," +
						"			MOBILE_NUMBER as Mobile," +
						"			ACCOUNT_ADDRESS as Address," +
						"			PIN_NUMBER as Pin," +
						"			(SELECT ZONE_NAME FROM DP_ZONE_MASTER where ZONE_ID = a.ZONE_ID) as Zone_Name," +
						"			(SELECT EXT_DIST_ID FROM DP_DISTRIBUTOR_MAPPING where DP_DIST_ID = a.ACCOUNT_ID FETCH FIRST 1 row only) as BoTree_Code," +
						"			DISTRIBUTOR_LOCATOR as Oracle_Locator," +
						"			STATUS as Status," +
						"			DATE(CREATED_DT) as Created_Date," +
						"			(SELECT ACCOUNT_NAME FROM VR_ACCOUNT_DETAILS where ACCOUNT_ID = a.CREATED_BY) as Created_By," +
						"			DATE(UPDATED_DT) as Last_Updated_Date," +
						"			(SELECT ACCOUNT_NAME FROM VR_ACCOUNT_DETAILS where ACCOUNT_ID = a.UPDATED_BY ) as Last_Updated_By, " +
						"			coalesce(to_char(LAST_LOGIN_TIME,'dd/MM/yyyy'),'') as Last_Login_date, " +
						"			case when LAST_LOGIN_TIME is null then coalesce(to_char((IPWD_CHANGED_DT+45 DAYS),'dd/MM/yyyy'),'') else coalesce(to_char((IPWD_CHANGED_DT),'dd/MM/yyyy'),'') end as IPWD_CHANGED_DT " +
						"			from VR_LOGIN_MASTER b , VR_ACCOUNT_DETAILS a " +
						"			where a.ACCOUNT_ID=b.LOGIN_ID and b.STATUS IN( "+status+ ") ";
				}	
				circleFilter = "a";
				flag = "true";
			}
			
			
			if(flag != null && flag.equalsIgnoreCase("true"))  {
				//System.out.println("55555555");
			if(reportDto.getSearchOption().equalsIgnoreCase("-1")) {
				
			}
			else if(reportDto.getSearchOption().equalsIgnoreCase("LOGIN_ID")) {
				searchCriteria = " AND b.LOGIN_ID = (SELECT LOGIN_ID FROM VR_LOGIN_MASTER WHERE UPPER(LOGIN_NAME) ='" +reportDto.getSearchCriteria().toUpperCase() +"')";
			}
			else if(reportDto.getSearchOption().equalsIgnoreCase("ACCOUNT_NAME")) {
				searchCriteria = " AND a.ACCOUNT_ID = (SELECT ACCOUNT_ID FROM VR_ACCOUNT_DETAILS WHERE UPPER(ACCOUNT_NAME) ='" +reportDto.getSearchCriteria().toUpperCase() +"')";
			}
			else if(reportDto.getSearchOption().equalsIgnoreCase("MOBILE_NO")) {
				searchCriteria = " AND a.MOBILE_NUMBER = '"+reportDto.getSearchCriteria()+"'";
			}
			else if(reportDto.getSearchOption().equalsIgnoreCase("EMAIL_ID")) {
				searchCriteria = " AND UPPER(a.EMAIL) = '" +reportDto.getSearchCriteria().toUpperCase()+"'";
			}
			else if(reportDto.getSearchOption().equalsIgnoreCase("PIN_CODE")) {
				searchCriteria = " AND a.PIN_NUMBER = "+ reportDto.getSearchCriteria();
			}
			else if(reportDto.getSearchOption().equalsIgnoreCase("CITY")) {
				searchCriteria = " AND a.CITY_ID = (SELECT CITY_ID FROM VR_CITY_MASTER WHERE UPPER(CITY_NAME) = '" +reportDto.getSearchCriteria().toUpperCase()+"')";
			}

			
			
			if(reportDto.getAccountLevel() == 5) {
				//System.out.println("getAccountLevel == 5 block");
				if(accountType.equals("-1")) {
					//System.out.println("accountType.equals -1 block");
					query1 = sql + " AND c.PARENT_ACCOUNT IN ("+reportDto.getAccountId()+")";
					query1 += searchCriteria.toString();
		
					query2 = 	" UNION ALL " + sql + " AND a.PARENT_ACCOUNT IN (SELECT DP_DIST_ID FROM DP_DISTRIBUTOR_MAPPING where PARENT_ACCOUNT IN ("+reportDto.getAccountId()+"))";;
					query2 += searchCriteria.toString();
					
					query3 =	" UNION ALL " + sql + " AND a.PARENT_ACCOUNT IN (SELECT ACCOUNT_ID FROM VR_ACCOUNT_DETAILS where PARENT_ACCOUNT IN (SELECT DP_DIST_ID FROM DP_DISTRIBUTOR_MAPPING where PARENT_ACCOUNT IN ("+reportDto.getAccountId()+")))";;
					query3 += searchCriteria.toString();
					
					sql = query1 + query2 + query3;
					
				} else if(accountType.equals("6")) {
					//System.out.println("accountType.equals 6  block");
					sql +=" AND c.PARENT_ACCOUNT IN ("+reportDto.getAccountId()+")";
				} else if (accountType.equals("7")) {
					sql +=" AND a.PARENT_ACCOUNT IN (SELECT DP_DIST_ID FROM DP_DISTRIBUTOR_MAPPING where PARENT_ACCOUNT IN ("+reportDto.getAccountId()+"))";
				} else if (accountType.equals("8")) {
					sql +=" AND a.PARENT_ACCOUNT IN (SELECT ACCOUNT_ID FROM VR_ACCOUNT_DETAILS where PARENT_ACCOUNT IN (SELECT DP_DIST_ID FROM DP_DISTRIBUTOR_MAPPING where PARENT_ACCOUNT IN ("+reportDto.getAccountId()+")))";
				}
			} else if (reportDto.getAccountLevel() == 6) {
				//System.out.println("6666666666");
				if(accountType.equals("-1")) {
					query1 = sql + " AND a.PARENT_ACCOUNT IN ("+reportDto.getAccountId()+")";
					query1 += searchCriteria.toString();
		
					query2 = 	" UNION ALL " + sql + " AND a.PARENT_ACCOUNT IN (SELECT ACCOUNT_ID FROM VR_ACCOUNT_DETAILS where PARENT_ACCOUNT IN ("+reportDto.getAccountId()+"))";
					query2 += searchCriteria.toString();
									
					sql = query1 + query2;
						
				} else if (accountType.equals("7")) {
					sql +=" AND a.PARENT_ACCOUNT IN ("+reportDto.getAccountId()+")";
				} else if (accountType.equals("8")) {
					sql +=" AND a.PARENT_ACCOUNT IN (SELECT ACCOUNT_ID FROM VR_ACCOUNT_DETAILS where PARENT_ACCOUNT IN ("+reportDto.getAccountId()+"))";
				}

			} else if(accountType.equals("-1") && reportDto.getAccountLevel() == 0 ){
				if(!reportDto.getHiddenCircleSelecIds().equals("0")) {
					sql = sql + " and a.ACCOUNT_LEVEL > " + reportDto.getAccountLevel() +" and "+circleFilter+".CIRCLE_ID IN(0,"+ reportDto.getHiddenCircleSelecIds() +")";
				} else {
					sql = sql + " and a.ACCOUNT_LEVEL > " + reportDto.getAccountLevel();
				}
			} else if (accountType.equals("1")) {
				sql = sql + " and a.ACCOUNT_LEVEL = " + accountType;
			} else if (accountType.equals("-1")) {	
				if(!reportDto.getHiddenCircleSelecIds().equals("0")) {
					sql = sql + " and a.ACCOUNT_LEVEL > " + reportDto.getAccountLevel() +" and "+circleFilter+".CIRCLE_ID IN("+ reportDto.getHiddenCircleSelecIds() +")";
				} else {
					sql = sql + " and a.ACCOUNT_LEVEL > " + reportDto.getAccountLevel();
				}
			} else {
				if(!reportDto.getHiddenCircleSelecIds().equals("0")) {
					sql = sql + " and a.ACCOUNT_LEVEL = " + accountType +" and "+circleFilter+".CIRCLE_ID IN("+ reportDto.getHiddenCircleSelecIds() +")";
				} else {
					sql = sql + " and a.ACCOUNT_LEVEL = " + accountType;
				}
			}
			
			
				String orderBy = " ORDER BY CIRCLE, ACCOUNT_LEVEL ASC" ;
			
			
			
				sql = sql + searchCriteria + orderBy;
			}
			System.out.println(accountType);
			System.out.println(reportDto.getAccountLevel() );*/
			/*if(accountType.equals("-1") ){
				
			//	System.out.println("777777777");
					sql = "SELECT LOGIN_NAME as Login_ID, a.ACCOUNT_LEVEL, " +
					"			(SELECT LEVEL_NAME FROM VR_ACCOUNT_LEVEL_MASTER where LEVEL_ID = a.ACCOUNT_LEVEL) as Account_Type," +
					"			ACCOUNT_NAME as Contact_Name," +
					"			LAPU_MOBILE_NO as Lapu_No," +
					"			(SELECT LOGIN_NAME FROM VR_LOGIN_MASTER where LOGIN_ID = a.PARENT_ACCOUNT) as Parent_Login_ID," +
					"			(SELECT LEVEL_NAME FROM VR_ACCOUNT_LEVEL_MASTER where LEVEL_ID = a.ACCOUNT_LEVEL -1) as Parent_Account_Type," +
					"			(SELECT ACCOUNT_NAME FROM VR_ACCOUNT_DETAILS where ACCOUNT_ID = a.PARENT_ACCOUNT) as Parent_Account_Name," +			
					"			(SELECT CIRCLE_NAME FROM VR_CIRCLE_MASTER where CIRCLE_ID = map1.CIRCLE_ID fetch first 1 row only ) as Circle," +
					"			(SELECT CITY_NAME FROM VR_CITY_MASTER where CITY_ID = a.CITY_ID) as City," +
					"			( select WH_NAME from DP_WH_MASTER where WH_CODE in(select wh_code from DPDTH.DP_WH_DIST_MAP where DISTID = A.ACCOUNT_ID) )  as WH_NAME,"+
					"			EMAIL as Email," +
					"			MOBILE_NUMBER as Mobile," +
					"			ACCOUNT_ADDRESS as Address," +
					"			PIN_NUMBER as Pin," +
					"			(SELECT ZONE_NAME FROM DP_ZONE_MASTER where ZONE_ID = a.ZONE_ID) as Zone_Name," +
					"			(SELECT EXT_DIST_ID FROM DP_DISTRIBUTOR_MAPPING where DP_DIST_ID = a.ACCOUNT_ID FETCH FIRST 1 row only) as BoTree_Code," +
					"			DISTRIBUTOR_LOCATOR as Oracle_Locator," +
					"			STATUS as Status," +
					"			DATE(CREATED_DT) as Created_Date," +
					"			(SELECT ACCOUNT_NAME FROM VR_ACCOUNT_DETAILS where ACCOUNT_ID = a.CREATED_BY) as Created_By," +
					"			DATE(UPDATED_DT) as Last_Updated_Date," +
					"			(SELECT ACCOUNT_NAME FROM VR_ACCOUNT_DETAILS where ACCOUNT_ID = a.UPDATED_BY ) as Last_Updated_By," +
					"			coalesce(to_char(LAST_LOGIN_TIME,'dd/MM/yyyy'),'') as Last_Login_date, " +
					"			case when LAST_LOGIN_TIME is null then coalesce(to_char((IPWD_CHANGED_DT+45 DAYS),'dd/MM/yyyy'),'') else coalesce(to_char((IPWD_CHANGED_DT),'dd/MM/yyyy'),'') end as IPWD_CHANGED_DT " +
					"			from VR_LOGIN_MASTER b , VR_ACCOUNT_DETAILS a left outer join  DP_ACCOUNT_CIRCLE_MAP map1 on map1.account_id= a.account_id  " +
					"			where a.ACCOUNT_ID=b.LOGIN_ID and b.STATUS IN( "+status+ ") and a.ACCOUNT_LEVEL !=6 " ;
					
					sql2 = " 				UNION " +
					" 		SELECT LOGIN_NAME as Login_ID, a.ACCOUNT_LEVEL, " +
					"			(SELECT LEVEL_NAME FROM VR_ACCOUNT_LEVEL_MASTER where LEVEL_ID = a.ACCOUNT_LEVEL) as Account_Type," +
					"			ACCOUNT_NAME as Contact_Name," +
					"			LAPU_MOBILE_NO as Lapu_No," +
					"			(SELECT LOGIN_NAME FROM VR_LOGIN_MASTER where LOGIN_ID = a.PARENT_ACCOUNT) as Parent_Login_ID," +
					"			(SELECT LEVEL_NAME FROM VR_ACCOUNT_LEVEL_MASTER where LEVEL_ID = a.ACCOUNT_LEVEL -1) as Parent_Account_Type," +
					"			(SELECT ACCOUNT_NAME FROM VR_ACCOUNT_DETAILS where ACCOUNT_ID = a.PARENT_ACCOUNT) as Parent_Account_Name," +			
					"			(SELECT CIRCLE_NAME FROM VR_CIRCLE_MASTER where CIRCLE_ID = a.CIRCLE_ID) as Circle," +
					"			(SELECT CITY_NAME FROM VR_CITY_MASTER where CITY_ID = a.CITY_ID) as City," +
					"			( select WH_NAME from DP_WH_MASTER where WH_CODE in(select wh_code from DPDTH.DP_WH_DIST_MAP where DISTID = A.ACCOUNT_ID) )  as WH_NAME,"+
					"			EMAIL as Email," +
					"			MOBILE_NUMBER as Mobile," +
					"			ACCOUNT_ADDRESS as Address," +
					"			PIN_NUMBER as Pin," +
					"			(SELECT ZONE_NAME FROM DP_ZONE_MASTER where ZONE_ID = a.ZONE_ID) as Zone_Name," +
					"			(SELECT EXT_DIST_ID FROM DP_DISTRIBUTOR_MAPPING where DP_DIST_ID = a.ACCOUNT_ID FETCH FIRST 1 row only) as BoTree_Code," +
					"			DISTRIBUTOR_LOCATOR as Oracle_Locator," +
					"			STATUS as Status," +
					"			DATE(CREATED_DT) as Created_Date," +
					"			(SELECT ACCOUNT_NAME FROM VR_ACCOUNT_DETAILS where ACCOUNT_ID = a.CREATED_BY) as Created_By," +
					"			DATE(UPDATED_DT) as Last_Updated_Date," +
					"			(SELECT ACCOUNT_NAME FROM VR_ACCOUNT_DETAILS where ACCOUNT_ID = a.UPDATED_BY ) as Last_Updated_By," +
					"			coalesce(to_char(LAST_LOGIN_TIME,'dd/MM/yyyy'),'') as Last_Login_date, " +
					"			case when LAST_LOGIN_TIME is null then coalesce(to_char((IPWD_CHANGED_DT+45 DAYS),'dd/MM/yyyy'),'') else coalesce(to_char((IPWD_CHANGED_DT),'dd/MM/yyyy'),'') end as IPWD_CHANGED_DT " +
					"			from VR_LOGIN_MASTER b , VR_ACCOUNT_DETAILS a " +
					"			where a.ACCOUNT_ID=b.LOGIN_ID and b.STATUS IN( "+status+ ") and a.ACCOUNT_LEVEL !=6 ";
					
					sql3 = " 				UNION " +
					" 		SELECT b.LOGIN_NAME as Login_ID, a.ACCOUNT_LEVEL, " +
					"			(SELECT LEVEL_NAME FROM VR_ACCOUNT_LEVEL_MASTER where LEVEL_ID = a.ACCOUNT_LEVEL) as Account_Type," +
					"			a.ACCOUNT_NAME as Contact_Name," +
					"			a.LAPU_MOBILE_NO as Lapu_No," +
					"			LOGPARENT.LOGIN_NAME as Parent_Login_ID, " +
					"			(SELECT LEVEL_NAME FROM VR_ACCOUNT_LEVEL_MASTER where LEVEL_ID = a.ACCOUNT_LEVEL -1) as Parent_Account_Type," +
					"			ACCPARENT.ACCOUNT_NAME as Parent_Account_Name, 	" +			
					"			(SELECT CIRCLE_NAME FROM VR_CIRCLE_MASTER where CIRCLE_ID = a.CIRCLE_ID) as Circle," +
					"			(SELECT CITY_NAME FROM VR_CITY_MASTER where CITY_ID = a.CITY_ID) as City," +
					"			( select WH_NAME from DP_WH_MASTER where WH_CODE in(select wh_code from DPDTH.DP_WH_DIST_MAP where DISTID = A.ACCOUNT_ID) )  as WH_NAME,"+
					"			a.EMAIL as Email," +
					"			a.MOBILE_NUMBER as Mobile," +
					"			a.ACCOUNT_ADDRESS as Address," +
					"			a.PIN_NUMBER as Pin," +
					"			(SELECT ZONE_NAME FROM DP_ZONE_MASTER where ZONE_ID = a.ZONE_ID) as Zone_Name," +
					"			(SELECT EXT_DIST_ID FROM DP_DISTRIBUTOR_MAPPING where DP_DIST_ID = a.ACCOUNT_ID FETCH FIRST 1 row only) as BoTree_Code," +
					"			a.DISTRIBUTOR_LOCATOR as Oracle_Locator," +
					"			b.STATUS as Status," +
					"			DATE(a.CREATED_DT) as Created_Date," +
					"			(SELECT ACCOUNT_NAME FROM VR_ACCOUNT_DETAILS where ACCOUNT_ID = a.CREATED_BY) as Created_By," +
					"			DATE(a.UPDATED_DT) as Last_Updated_Date," +
					"			(SELECT ACCOUNT_NAME FROM VR_ACCOUNT_DETAILS where ACCOUNT_ID = a.UPDATED_BY ) as Last_Updated_By," +
					"			coalesce(to_char(b.LAST_LOGIN_TIME,'dd/MM/yyyy'),'') as Last_Login_date, " +
					"			case when b.LAST_LOGIN_TIME is null then coalesce(to_char((b.IPWD_CHANGED_DT+45 DAYS),'dd/MM/yyyy'),'') else coalesce(to_char((b.IPWD_CHANGED_DT),'dd/MM/yyyy'),'') end as IPWD_CHANGED_DT " +
					"			from VR_LOGIN_MASTER b , VR_ACCOUNT_DETAILS a ,DP_DISTRIBUTOR_MAPPING c ,VR_LOGIN_MASTER LOGPARENT , VR_ACCOUNT_DETAILS ACCPARENT " +
					"			where c.DP_DIST_ID = a.account_id  and c.PARENT_ACCOUNT = LOGPARENT.login_id and ACCPARENT.account_id=c.PARENT_ACCOUNT "+
					"			and a.ACCOUNT_ID=b.LOGIN_ID and b.STATUS IN( "+status+ ") and a.ACCOUNT_LEVEL =6 ";
					
					if(reportDto.getSearchOption().equalsIgnoreCase("-1")) {
						
					}
					else if(reportDto.getSearchOption().equalsIgnoreCase("LOGIN_ID")) {
						searchCriteria = " AND b.LOGIN_ID = (SELECT LOGIN_ID FROM VR_LOGIN_MASTER WHERE UPPER(LOGIN_NAME) ='" +reportDto.getSearchCriteria().toUpperCase() +"')";
					}
					else if(reportDto.getSearchOption().equalsIgnoreCase("ACCOUNT_NAME")) {
						searchCriteria = " AND a.ACCOUNT_ID = (SELECT ACCOUNT_ID FROM VR_ACCOUNT_DETAILS WHERE UPPER(ACCOUNT_NAME) ='" +reportDto.getSearchCriteria().toUpperCase() +"')";
					}
					else if(reportDto.getSearchOption().equalsIgnoreCase("MOBILE_NO")) {
						searchCriteria = " AND a.MOBILE_NUMBER = '"+reportDto.getSearchCriteria()+"'";
					}
					else if(reportDto.getSearchOption().equalsIgnoreCase("EMAIL_ID")) {
						searchCriteria = " AND UPPER(a.EMAIL) = '" +reportDto.getSearchCriteria().toUpperCase()+"'";
					}
					else if(reportDto.getSearchOption().equalsIgnoreCase("PIN_CODE")) {
						searchCriteria = " AND a.PIN_NUMBER = "+ reportDto.getSearchCriteria();
					}
					else if(reportDto.getSearchOption().equalsIgnoreCase("CITY")) {
						searchCriteria = " AND a.CITY_ID = (SELECT CITY_ID FROM VR_CITY_MASTER WHERE UPPER(CITY_NAME) = '" +reportDto.getSearchCriteria().toUpperCase()+"')";
					}

					
					
				  if(accountType.equals("-1") && reportDto.getAccountLevel() == 0 ){
						if(!reportDto.getHiddenCircleSelecIds().equals("0")) {
							sql = sql + " and a.ACCOUNT_LEVEL > " + reportDto.getAccountLevel() +" and map1.CIRCLE_ID IN(0,"+ reportDto.getHiddenCircleSelecIds() +")";
							sql2 = sql2 + " and a.ACCOUNT_LEVEL > " + reportDto.getAccountLevel() +" and a.CIRCLE_ID IN(0,"+ reportDto.getHiddenCircleSelecIds() +")";
							sql3 = sql3 + " and a.ACCOUNT_LEVEL > " + reportDto.getAccountLevel() +" and a.CIRCLE_ID IN(0,"+ reportDto.getHiddenCircleSelecIds() +")";
							
						} else {
							sql = sql + " and a.ACCOUNT_LEVEL > " + reportDto.getAccountLevel();
							sql2 = sql2 + " and a.ACCOUNT_LEVEL > " + reportDto.getAccountLevel();
							sql3 = sql3 + " and a.ACCOUNT_LEVEL > " + reportDto.getAccountLevel();
						}
					} 
				  else if (accountType.equals("1")) {
						sql = sql + " and a.ACCOUNT_LEVEL = " + accountType;
						sql2 = sql2 + " and a.ACCOUNT_LEVEL = " + accountType;
						sql3 = sql3 + " and a.ACCOUNT_LEVEL = " + accountType;
					} else if (accountType.equals("-1")) {	
						if(!reportDto.getHiddenCircleSelecIds().equals("0")) {
							sql = sql + " and a.ACCOUNT_LEVEL > " + reportDto.getAccountLevel() +" and map1.CIRCLE_ID IN("+ reportDto.getHiddenCircleSelecIds() +")";
							sql2 = sql2 + " and a.ACCOUNT_LEVEL > " + reportDto.getAccountLevel() +" and a.CIRCLE_ID IN("+ reportDto.getHiddenCircleSelecIds() +")";
							sql3 = sql3 + " and a.ACCOUNT_LEVEL > " + reportDto.getAccountLevel() +" and a.CIRCLE_ID IN("+ reportDto.getHiddenCircleSelecIds() +")";
						} else {
							sql = sql + " and a.ACCOUNT_LEVEL > " + reportDto.getAccountLevel();
							sql2 = sql2 + " and a.ACCOUNT_LEVEL > " + reportDto.getAccountLevel();
							sql3 = sql3 + " and a.ACCOUNT_LEVEL > " + reportDto.getAccountLevel();
						}
					} else {
						if(!reportDto.getHiddenCircleSelecIds().equals("0")) {
							sql = sql + " and a.ACCOUNT_LEVEL = " + accountType +" and map1.CIRCLE_ID IN("+ reportDto.getHiddenCircleSelecIds() +")";
							sql2 = sql2 + " and a.ACCOUNT_LEVEL = " + accountType +" and a.CIRCLE_ID IN("+ reportDto.getHiddenCircleSelecIds() +")";
							sql3 = sql3 + " and a.ACCOUNT_LEVEL = " + accountType +" and a.CIRCLE_ID IN("+ reportDto.getHiddenCircleSelecIds() +")";
						} else {
							sql = sql + " and a.ACCOUNT_LEVEL = " + accountType;
							sql2 = sql2 + " and a.ACCOUNT_LEVEL = " + accountType;
							sql3 = sql3 + " and a.ACCOUNT_LEVEL = " + accountType;
						}
					}
					
					
						String orderBy = " ORDER BY CIRCLE, ACCOUNT_LEVEL ASC" ;
					
					
					
						String sqlFinal = sql + searchCriteria + sql2 + searchCriteria +  sql3 + searchCriteria + orderBy;
						System.out.println(sqlFinal);
						sql=sqlFinal;
				
			}
			
			

			//System.out.println(sql);
			ps = connection.prepareStatement(sql);			

			rsetReport = ps.executeQuery();
			AccountDetailReportDto reportReturnDto =null;
	
			
			while(rsetReport.next()){
				reportReturnDto =new AccountDetailReportDto();
				reportReturnDto.setDistLoginId(rsetReport.getString("LOGIN_ID"));
				reportReturnDto.setAccountType(rsetReport.getString("ACCOUNT_TYPE"));
				reportReturnDto.setAccountName(rsetReport.getString("CONTACT_NAME"));
				reportReturnDto.setLapuNo(rsetReport.getString("Lapu_No"));
				reportReturnDto.setParentLoginId(rsetReport.getString("PARENT_LOGIN_ID"));
				reportReturnDto.setParentAccountType(rsetReport.getString("PARENT_ACCOUNT_TYPE"));	
				reportReturnDto.setParentAccountName(rsetReport.getString("PARENT_ACCOUNT_NAME"));				
				reportReturnDto.setCircle(rsetReport.getString("CIRCLE"));
				reportReturnDto.setCity(rsetReport.getString("CITY")); 
				reportReturnDto.setEmailId(rsetReport.getString("EMAIL"));
				reportReturnDto.setMobileNo(rsetReport.getString("Mobile"));
				reportReturnDto.setAddress(rsetReport.getString("ADDRESS"));
				reportReturnDto.setPin(rsetReport.getString("PIN"));
				reportReturnDto.setZone(rsetReport.getString("ZONE_NAME"));
				reportReturnDto.setBotreeCode(rsetReport.getString("BOTREE_CODE"));
				reportReturnDto.setOracleLocatorCode(rsetReport.getString("ORACLE_LOCATOR"));
				if (rsetReport.getString("STATUS").equalsIgnoreCase("A")){	reportReturnDto.setStatus("Active");
				} else { reportReturnDto.setStatus("Inactive"); }
				//reportReturnDto.setStatus(rsetReport.getString("STATUS"));
				reportReturnDto.setCreatedDate(Utility.getDateAsString(rsetReport.getDate("CREATED_DATE"), "dd-MMM-yyyy"));
				reportReturnDto.setCreatedBy(rsetReport.getString("CREATED_BY"));
				reportReturnDto.setLastUpdatedDate(Utility.getDateAsString(rsetReport.getDate("LAST_UPDATED_DATE"), "dd-MMM-yyyy"));
				reportReturnDto.setLastUpdatedBy(rsetReport.getString("LAST_UPDATED_BY"));
				reportReturnDto.setLastLoginDate((rsetReport.getString("LAST_LOGIN_DATE")));
				reportReturnDto.setWhName(rsetReport.getString("WH_NAME"));
				reportReturnDto.setPassChangeDT(rsetReport.getString("IPWD_CHANGED_DT"));
				listReturn.add(reportReturnDto);
			}*/
			logger.info("Account Detail Excel executed successfuly in AccountDetailReportDaoImpl");
			
		}catch (Exception e) 
		{
			e.printStackTrace();
			throw new DAOException(e.getMessage());
		}
		finally
		{
			DBConnectionManager.releaseResources(ps ,rsetReport );
			DBConnectionManager.releaseResources(connection);
		}
		
		return listReturn;
	}	

	public List<AccountDetailReportDto> getAccountUpdateExcel(AccountDetailReportDto reportDto) throws DAOException{
		List<AccountDetailReportDto> listReturn = new ArrayList<AccountDetailReportDto>();
		PreparedStatement ps = null;
		ResultSet rsetReport = null;
		String accountMultiSelectedIds;
		String accountMultiSelectedIdsArr[];
		try
		{
			String sql = "";
			String sqlFinal = "";
			
			logger.info("List of cIrcles.... " + reportDto.getHiddenCircleSelecIds());

			connection = DBConnectionManager.getDBConnection();
			String statusStr = reportDto.getStatus();
			String accountType = reportDto.getAccountType();
			String query = "";
			String query1 = "";
			String query2 = "";
			String query3 = "";
			String status ="";
			String searchCriteria = "";
			accountMultiSelectedIds=reportDto.getAccountTypeMultiSelectIds();
			accountMultiSelectedIdsArr=accountMultiSelectedIds.split(",");
			logger.info("accountMultiSelectedIds  in daoimpl :"+accountMultiSelectedIds);
			//Neetika
			if(statusStr.equalsIgnoreCase("0")){
				//status = "'A','I'";
				status = "'A','I','L'";
			}
			if(statusStr.equalsIgnoreCase("1")){
				status = "'A'";
			}
			if(statusStr.equalsIgnoreCase("2")){
				status = "'I','L'"; //added L by neetika
			}
			
			String circleFilter="";
			String sql2="";
			String sql3="";
			String flag="";
			
			// start of non all coding here
			//if(accountMultiSelectedIds!="-1"){
			for(int i=0;i<accountMultiSelectedIdsArr.length;i++){
				logger.info("Generating report of account id :"+accountMultiSelectedIdsArr[i]);
				if(accountMultiSelectedIds!="-1"){
					if(accountMultiSelectedIdsArr[i].equalsIgnoreCase("3")){
					sql = "SELECT LOGIN_NAME as Login_ID, a.ACCOUNT_LEVEL, (SELECT LEVEL_NAME FROM VR_ACCOUNT_LEVEL_MASTER where LEVEL_ID = a.ACCOUNT_LEVEL) as Account_Type, "+
							"ACCOUNT_NAME as Contact_Name,LAPU_MOBILE_NO as Lapu_No," +
							"a.LAPU_MOBILE_NO_2 as LAPU_MOBILE_NO_2,a.FTA_MOBILE_NO as FTA_MOBILE_NO,a.FTA_MOBILE_NO_2 as FTA_MOBILE_NO_2," +
							"(SELECT LOGIN_NAME FROM VR_LOGIN_MASTER where LOGIN_ID = a.PARENT_ACCOUNT) as Parent_Login_ID, "+
							"(SELECT LEVEL_NAME FROM VR_ACCOUNT_LEVEL_MASTER where LEVEL_ID = a.ACCOUNT_LEVEL -1) as Parent_Account_Type, "+
							"(SELECT ACCOUNT_NAME FROM VR_ACCOUNT_DETAILS where ACCOUNT_ID = a.PARENT_ACCOUNT) as Parent_Account_Name, "+			
							"(SELECT CIRCLE_NAME FROM VR_CIRCLE_MASTER where CIRCLE_ID = map1.CIRCLE_ID fetch first 1 row only ) as Circle, "+
							"(SELECT CITY_NAME FROM VR_CITY_MASTER where CITY_ID = a.CITY_ID) as City,"+
						//	"( select WH_NAME from DP_WH_MASTER where WH_CODE in(select wh_code from DPDTH.DP_WH_DIST_MAP where DISTID = A.ACCOUNT_ID) )  as WH_NAME, "+
							" ''  as WH_NAME,"+ //Changed by Neetika as WH name is not required for this account level it impacts the performance of report
							"EMAIL as Email,MOBILE_NUMBER as Mobile,ACCOUNT_ADDRESS as Address,PIN_NUMBER as Pin, "+
							"(SELECT ZONE_NAME FROM DP_ZONE_MASTER where ZONE_ID = a.ZONE_ID) as Zone_Name, "+
							//"(SELECT EXT_DIST_ID FROM DP_DISTRIBUTOR_MAPPING where DP_DIST_ID = a.ACCOUNT_ID FETCH FIRST 1 row only) as BoTree_Code, "+
							"	'' as BoTree_Code," + //Changed by Neetika as it is not required for this account level it impacts the performance of report
							//"DISTRIBUTOR_LOCATOR as Oracle_Locator, " +
							"			'' as Oracle_Locator," + //Changed by Neetika as it is not required for this account level it impacts the performance of report
							"STATUS as Status, DATE(CREATED_DT) as Created_Date, "+
							"(SELECT ACCOUNT_NAME FROM VR_ACCOUNT_DETAILS where ACCOUNT_ID = a.CREATED_BY) as Created_By, "+
							"DATE(UPDATED_DT) as Last_Updated_Date, (SELECT ACCOUNT_NAME FROM VR_ACCOUNT_DETAILS where ACCOUNT_ID = a.UPDATED_BY ) as Last_Updated_By, "+
							"coalesce(to_char(LAST_LOGIN_TIME,'dd/MM/yyyy'),'') as Last_Login_date, "+ 
							"case when LAST_LOGIN_TIME is null then coalesce(to_char((IPWD_CHANGED_DT+45 DAYS),'dd/MM/yyyy'),'') else coalesce(to_char((IPWD_CHANGED_DT),'dd/MM/yyyy'),'') end as IPWD_CHANGED_DT , "+
					" case when a.ACCOUNT_LEVEL!=5 then '' when a.TRANSACTION_TYPE=1 then 'Sales' else 'CPE' end as TRANSACTION_TYPE, " +
							"a.ACCOUNT_ID as ACC_ID ,a.HIST_DATE as HIST_DATE,a.SR_NUMBER as SR_NUMBER,a.APPROVAL_1 as APPROVAL_1,a.APPROVAL_2 as APPROVAL_2,a.REMARKS as REMARKS "+
							"from VR_LOGIN_MASTER_HIST b , VR_ACCOUNT_DETAILS_HIST a left outer join  DP_ACCOUNT_CIRCLE_MAP_HIST map1 on map1.account_id= a.account_id and map1.HIST_DATE=a.HIST_DATE "+
							"where a.ACCOUNT_ID=b.LOGIN_ID and b.HIST_DATE=a.HIST_DATE and b.STATUS IN( "+status+ ")";
					
							circleFilter = "map1";
							flag = "true";
				
				}
				else if(accountMultiSelectedIdsArr[i].equalsIgnoreCase("4")){
					sql = "SELECT LOGIN_NAME as Login_ID, a.ACCOUNT_LEVEL, (SELECT LEVEL_NAME FROM VR_ACCOUNT_LEVEL_MASTER where LEVEL_ID = a.ACCOUNT_LEVEL) as Account_Type, "+
					"ACCOUNT_NAME as Contact_Name,LAPU_MOBILE_NO as Lapu_No," +
					"a.LAPU_MOBILE_NO_2 as LAPU_MOBILE_NO_2,a.FTA_MOBILE_NO as FTA_MOBILE_NO,a.FTA_MOBILE_NO_2 as FTA_MOBILE_NO_2," +
					"(SELECT LOGIN_NAME FROM VR_LOGIN_MASTER where LOGIN_ID = a.PARENT_ACCOUNT) as Parent_Login_ID, "+
					"(SELECT LEVEL_NAME FROM VR_ACCOUNT_LEVEL_MASTER where LEVEL_ID = a.ACCOUNT_LEVEL -1) as Parent_Account_Type, "+
					"(SELECT ACCOUNT_NAME FROM VR_ACCOUNT_DETAILS where ACCOUNT_ID = a.PARENT_ACCOUNT) as Parent_Account_Name, "+			
					"(SELECT CIRCLE_NAME FROM VR_CIRCLE_MASTER where CIRCLE_ID = map1.CIRCLE_ID fetch first 1 row only ) as Circle, "+
					"(SELECT CITY_NAME FROM VR_CITY_MASTER where CITY_ID = a.CITY_ID) as City,"+
					//"( select WH_NAME from DP_WH_MASTER where WH_CODE in(select wh_code from DPDTH.DP_WH_DIST_MAP where DISTID = A.ACCOUNT_ID) )  as WH_NAME, "+
					" ''  as WH_NAME,"+ //Changed by Neetika as WH name is not required for this account level it impacts the performance of report
					"EMAIL as Email,MOBILE_NUMBER as Mobile,ACCOUNT_ADDRESS as Address,PIN_NUMBER as Pin, "+
					"(SELECT ZONE_NAME FROM DP_ZONE_MASTER where ZONE_ID = a.ZONE_ID) as Zone_Name, "+
				//	"(SELECT EXT_DIST_ID FROM DP_DISTRIBUTOR_MAPPING where DP_DIST_ID = a.ACCOUNT_ID FETCH FIRST 1 row only) as BoTree_Code, "+
					"	'' as BoTree_Code," + //Changed by Neetika as it is not required for this account level it impacts the performance of report
					//"DISTRIBUTOR_LOCATOR as Oracle_Locator," +
					" '' as Oracle_Locator," + //Changed by Neetika as it is not required for this account level it impacts the performance of report
					" STATUS as Status, DATE(CREATED_DT) as Created_Date, "+
					"(SELECT ACCOUNT_NAME FROM VR_ACCOUNT_DETAILS where ACCOUNT_ID = a.CREATED_BY) as Created_By, "+
					"DATE(UPDATED_DT) as Last_Updated_Date, (SELECT ACCOUNT_NAME FROM VR_ACCOUNT_DETAILS where ACCOUNT_ID = a.UPDATED_BY ) as Last_Updated_By, "+
					"coalesce(to_char(LAST_LOGIN_TIME,'dd/MM/yyyy'),'') as Last_Login_date, "+ 
					"case when LAST_LOGIN_TIME is null then coalesce(to_char((IPWD_CHANGED_DT+45 DAYS),'dd/MM/yyyy'),'') else coalesce(to_char((IPWD_CHANGED_DT),'dd/MM/yyyy'),'') end as IPWD_CHANGED_DT , "+
					" case when a.ACCOUNT_LEVEL!=5 then '' when a.TRANSACTION_TYPE=1 then 'Sales' else 'CPE' end as TRANSACTION_TYPE, " +
					"a.ACCOUNT_ID as ACC_ID ,a.HIST_DATE as HIST_DATE,a.SR_NUMBER as SR_NUMBER,a.APPROVAL_1 as APPROVAL_1,a.APPROVAL_2 as APPROVAL_2,a.REMARKS as REMARKS "+
					"from VR_LOGIN_MASTER_HIST b , VR_ACCOUNT_DETAILS_HIST a left outer join  DP_ACCOUNT_CIRCLE_MAP_HIST map1 on map1.account_id= a.account_id and map1.HIST_DATE=a.HIST_DATE "+
					"where a.ACCOUNT_ID=b.LOGIN_ID and b.HIST_DATE=a.HIST_DATE and b.STATUS IN( "+status+ ")";
			
					circleFilter = "map1";
					flag = "true";
				}
				else if (accountMultiSelectedIdsArr[i].equalsIgnoreCase("5"))
				{
					sql = "SELECT LOGIN_NAME as Login_ID, a.ACCOUNT_LEVEL, (SELECT LEVEL_NAME FROM VR_ACCOUNT_LEVEL_MASTER where LEVEL_ID = a.ACCOUNT_LEVEL) as Account_Type, "+
					"ACCOUNT_NAME as Contact_Name,LAPU_MOBILE_NO as Lapu_No," +
					"a.LAPU_MOBILE_NO_2 as LAPU_MOBILE_NO_2,a.FTA_MOBILE_NO as FTA_MOBILE_NO,a.FTA_MOBILE_NO_2 as FTA_MOBILE_NO_2," +
					"(SELECT LOGIN_NAME FROM VR_LOGIN_MASTER where LOGIN_ID = a.PARENT_ACCOUNT) as Parent_Login_ID, "+
					"(SELECT LEVEL_NAME FROM VR_ACCOUNT_LEVEL_MASTER where LEVEL_ID = a.ACCOUNT_LEVEL -1) as Parent_Account_Type, "+
					"(SELECT ACCOUNT_NAME FROM VR_ACCOUNT_DETAILS where ACCOUNT_ID = a.PARENT_ACCOUNT) as Parent_Account_Name, "+			
					"(SELECT CIRCLE_NAME FROM VR_CIRCLE_MASTER where CIRCLE_ID = map1.CIRCLE_ID fetch first 1 row only ) as Circle, "+
					"(SELECT CITY_NAME FROM VR_CITY_MASTER where CITY_ID = a.CITY_ID) as City,"+
					//"( select WH_NAME from DP_WH_MASTER where WH_CODE in(select wh_code from DPDTH.DP_WH_DIST_MAP where DISTID = A.ACCOUNT_ID) )  as WH_NAME, "+
					" ''  as WH_NAME,"+ //Changed by Neetika as WH name is not required for this account level it impacts the performance of report
					"EMAIL as Email,MOBILE_NUMBER as Mobile,ACCOUNT_ADDRESS as Address,PIN_NUMBER as Pin, "+
					"(SELECT ZONE_NAME FROM DP_ZONE_MASTER where ZONE_ID = a.ZONE_ID) as Zone_Name, "+
					//"(SELECT EXT_DIST_ID FROM DP_DISTRIBUTOR_MAPPING where DP_DIST_ID = a.ACCOUNT_ID FETCH FIRST 1 row only) as BoTree_Code, "+
					"	'' as BoTree_Code," + //Changed by Neetika as it is not required for this account level it impacts the performance of report
					//"DISTRIBUTOR_LOCATOR as Oracle_Locator, " +
					" '' as Oracle_Locator," + //Changed by Neetika as it is not required for this account level it impacts the performance of report
					"STATUS as Status, DATE(CREATED_DT) as Created_Date, "+
					"(SELECT ACCOUNT_NAME FROM VR_ACCOUNT_DETAILS where ACCOUNT_ID = a.CREATED_BY) as Created_By, "+
					"DATE(UPDATED_DT) as Last_Updated_Date, (SELECT ACCOUNT_NAME FROM VR_ACCOUNT_DETAILS where ACCOUNT_ID = a.UPDATED_BY ) as Last_Updated_By, "+
					"coalesce(to_char(LAST_LOGIN_TIME,'dd/MM/yyyy'),'') as Last_Login_date, "+ 
					"case when LAST_LOGIN_TIME is null then coalesce(to_char((IPWD_CHANGED_DT+45 DAYS),'dd/MM/yyyy'),'') else coalesce(to_char((IPWD_CHANGED_DT),'dd/MM/yyyy'),'') end as IPWD_CHANGED_DT , "+
					" case when a.ACCOUNT_LEVEL!=5 then '' when a.TRANSACTION_TYPE=1 then 'Sales' else 'CPE' end as TRANSACTION_TYPE, " +
					"a.ACCOUNT_ID as ACC_ID ,a.HIST_DATE as HIST_DATE,a.SR_NUMBER as SR_NUMBER,a.APPROVAL_1 as APPROVAL_1,a.APPROVAL_2 as APPROVAL_2,a.REMARKS as REMARKS "+
					"from VR_LOGIN_MASTER_HIST b , VR_ACCOUNT_DETAILS_HIST a left outer join  DP_ACCOUNT_CIRCLE_MAP_HIST map1 on map1.account_id= a.account_id and map1.HIST_DATE=a.HIST_DATE "+
					"where a.ACCOUNT_ID=b.LOGIN_ID and b.HIST_DATE=a.HIST_DATE and b.STATUS IN( "+status+ ")";
			
					circleFilter = "map1";
					flag = "true";
				}
				else if (accountMultiSelectedIdsArr[i].equalsIgnoreCase("2"))
				{
					sql = "SELECT LOGIN_NAME as Login_ID, a.ACCOUNT_LEVEL, (SELECT LEVEL_NAME FROM VR_ACCOUNT_LEVEL_MASTER where LEVEL_ID = a.ACCOUNT_LEVEL) as Account_Type, "+
					"ACCOUNT_NAME as Contact_Name,LAPU_MOBILE_NO as Lapu_No," +
					"a.LAPU_MOBILE_NO_2 as LAPU_MOBILE_NO_2,a.FTA_MOBILE_NO as FTA_MOBILE_NO,a.FTA_MOBILE_NO_2 as FTA_MOBILE_NO_2," +
					"(SELECT LOGIN_NAME FROM VR_LOGIN_MASTER where LOGIN_ID = a.PARENT_ACCOUNT) as Parent_Login_ID, "+
					"(SELECT LEVEL_NAME FROM VR_ACCOUNT_LEVEL_MASTER where LEVEL_ID = a.ACCOUNT_LEVEL -1) as Parent_Account_Type, "+
					"(SELECT ACCOUNT_NAME FROM VR_ACCOUNT_DETAILS where ACCOUNT_ID = a.PARENT_ACCOUNT) as Parent_Account_Name, "+			
					"(SELECT CIRCLE_NAME FROM VR_CIRCLE_MASTER where CIRCLE_ID = map1.CIRCLE_ID fetch first 1 row only ) as Circle, "+
					"(SELECT CITY_NAME FROM VR_CITY_MASTER where CITY_ID = a.CITY_ID) as City,"+
					//"( select WH_NAME from DP_WH_MASTER where WH_CODE in(select wh_code from DPDTH.DP_WH_DIST_MAP where DISTID = A.ACCOUNT_ID) )  as WH_NAME, "+
					" ''  as WH_NAME,"+ //Changed by Neetika as WH name is not required for this account level it impacts the performance of report
					"EMAIL as Email,MOBILE_NUMBER as Mobile,ACCOUNT_ADDRESS as Address,PIN_NUMBER as Pin, "+
					"(SELECT ZONE_NAME FROM DP_ZONE_MASTER where ZONE_ID = a.ZONE_ID) as Zone_Name, "+
					//"(SELECT EXT_DIST_ID FROM DP_DISTRIBUTOR_MAPPING where DP_DIST_ID = a.ACCOUNT_ID FETCH FIRST 1 row only) as BoTree_Code, "+
					"	'' as BoTree_Code," + //Changed by Neetika as it is not required for this account level it impacts the performance of report
					//"DISTRIBUTOR_LOCATOR as Oracle_Locator, " +
					" '' as Oracle_Locator," + //Changed by Neetika as it is not required for this account level it impacts the performance of report
					"STATUS as Status, DATE(CREATED_DT) as Created_Date, "+
					"(SELECT ACCOUNT_NAME FROM VR_ACCOUNT_DETAILS where ACCOUNT_ID = a.CREATED_BY) as Created_By, "+
					"DATE(UPDATED_DT) as Last_Updated_Date, (SELECT ACCOUNT_NAME FROM VR_ACCOUNT_DETAILS where ACCOUNT_ID = a.UPDATED_BY ) as Last_Updated_By, "+
					"coalesce(to_char(LAST_LOGIN_TIME,'dd/MM/yyyy'),'') as Last_Login_date, "+ 
					"case when LAST_LOGIN_TIME is null then coalesce(to_char((IPWD_CHANGED_DT+45 DAYS),'dd/MM/yyyy'),'') else coalesce(to_char((IPWD_CHANGED_DT),'dd/MM/yyyy'),'') end as IPWD_CHANGED_DT , "+
					" case when a.ACCOUNT_LEVEL!=5 then '' when a.TRANSACTION_TYPE=1 then 'Sales' else 'CPE' end as TRANSACTION_TYPE, " +
					"a.ACCOUNT_ID as ACC_ID ,a.HIST_DATE as HIST_DATE,a.SR_NUMBER as SR_NUMBER,a.APPROVAL_1 as APPROVAL_1,a.APPROVAL_2 as APPROVAL_2,a.REMARKS as REMARKS "+
					"from VR_LOGIN_MASTER_HIST b , VR_ACCOUNT_DETAILS_HIST a left outer join  DP_ACCOUNT_CIRCLE_MAP_HIST map1 on map1.account_id= a.account_id and map1.HIST_DATE=a.HIST_DATE "+
					"where a.ACCOUNT_ID=b.LOGIN_ID and b.HIST_DATE=a.HIST_DATE and b.STATUS IN( "+status+ ")";
			
					circleFilter = "map1";
					flag = "true";
				}
			
				else if (accountMultiSelectedIdsArr[i].equalsIgnoreCase("1"))
				{
					sql = "SELECT LOGIN_NAME as Login_ID, a.ACCOUNT_LEVEL, (SELECT LEVEL_NAME FROM VR_ACCOUNT_LEVEL_MASTER where LEVEL_ID = a.ACCOUNT_LEVEL) as Account_Type, "+
					"ACCOUNT_NAME as Contact_Name,LAPU_MOBILE_NO as Lapu_No," +
					"a.LAPU_MOBILE_NO_2 as LAPU_MOBILE_NO_2,a.FTA_MOBILE_NO as FTA_MOBILE_NO,a.FTA_MOBILE_NO_2 as FTA_MOBILE_NO_2," +
					"(SELECT LOGIN_NAME FROM VR_LOGIN_MASTER where LOGIN_ID = a.PARENT_ACCOUNT) as Parent_Login_ID, "+
					"(SELECT LEVEL_NAME FROM VR_ACCOUNT_LEVEL_MASTER where LEVEL_ID = a.ACCOUNT_LEVEL -1) as Parent_Account_Type, "+
					"(SELECT ACCOUNT_NAME FROM VR_ACCOUNT_DETAILS where ACCOUNT_ID = a.PARENT_ACCOUNT) as Parent_Account_Name, "+			
					"(SELECT CIRCLE_NAME FROM VR_CIRCLE_MASTER where CIRCLE_ID = map1.CIRCLE_ID fetch first 1 row only ) as Circle, "+
					"(SELECT CITY_NAME FROM VR_CITY_MASTER where CITY_ID = a.CITY_ID) as City,"+
					//"( select WH_NAME from DP_WH_MASTER where WH_CODE in(select wh_code from DPDTH.DP_WH_DIST_MAP where DISTID = A.ACCOUNT_ID) )  as WH_NAME, "+
					" ''  as WH_NAME,"+ //Changed by Neetika as WH name is not required for this account level it impacts the performance of report
					"EMAIL as Email,MOBILE_NUMBER as Mobile,ACCOUNT_ADDRESS as Address,PIN_NUMBER as Pin, "+
					"(SELECT ZONE_NAME FROM DP_ZONE_MASTER where ZONE_ID = a.ZONE_ID) as Zone_Name, "+
					//"(SELECT EXT_DIST_ID FROM DP_DISTRIBUTOR_MAPPING where DP_DIST_ID = a.ACCOUNT_ID FETCH FIRST 1 row only) as BoTree_Code, "+
					"	'' as BoTree_Code," + //Changed by Neetika as it is not required for this account level it impacts the performance of report
					//"DISTRIBUTOR_LOCATOR as Oracle_Locator, " +
					" '' as Oracle_Locator," + //Changed by Neetika as it is not required for this account level it impacts the performance of report
					"STATUS as Status, DATE(CREATED_DT) as Created_Date, "+
					"(SELECT ACCOUNT_NAME FROM VR_ACCOUNT_DETAILS where ACCOUNT_ID = a.CREATED_BY) as Created_By, "+
					"DATE(UPDATED_DT) as Last_Updated_Date, (SELECT ACCOUNT_NAME FROM VR_ACCOUNT_DETAILS where ACCOUNT_ID = a.UPDATED_BY ) as Last_Updated_By, "+
					"coalesce(to_char(LAST_LOGIN_TIME,'dd/MM/yyyy'),'') as Last_Login_date, "+ 
					"case when LAST_LOGIN_TIME is null then coalesce(to_char((IPWD_CHANGED_DT+45 DAYS),'dd/MM/yyyy'),'') else coalesce(to_char((IPWD_CHANGED_DT),'dd/MM/yyyy'),'') end as IPWD_CHANGED_DT , "+
					" case when a.ACCOUNT_LEVEL!=5 then '' when a.TRANSACTION_TYPE=1 then 'Sales' else 'CPE' end as TRANSACTION_TYPE, " +
					"a.ACCOUNT_ID as ACC_ID ,a.HIST_DATE as HIST_DATE,a.SR_NUMBER as SR_NUMBER,a.APPROVAL_1 as APPROVAL_1,a.APPROVAL_2 as APPROVAL_2,a.REMARKS as REMARKS "+
					"from VR_LOGIN_MASTER_HIST b , VR_ACCOUNT_DETAILS_HIST a left outer join  DP_ACCOUNT_CIRCLE_MAP_HIST map1 on map1.account_id= a.account_id and map1.HIST_DATE=a.HIST_DATE "+
					"where a.ACCOUNT_ID=b.LOGIN_ID and b.HIST_DATE=a.HIST_DATE and b.STATUS IN( "+status+ ")";
			
					circleFilter = "map1";
					flag = "true";
				}
			/*if(accountType != null && (accountType.equalsIgnoreCase("3") || accountType.equalsIgnoreCase("4") 
					|| accountType.equalsIgnoreCase("5")) || accountType.equalsIgnoreCase("2"))
			{
					sql = "SELECT LOGIN_NAME as Login_ID, a.ACCOUNT_LEVEL, (SELECT LEVEL_NAME FROM VR_ACCOUNT_LEVEL_MASTER where LEVEL_ID = a.ACCOUNT_LEVEL) as Account_Type, "+
							"ACCOUNT_NAME as Contact_Name,LAPU_MOBILE_NO as Lapu_No," +
							"a.LAPU_MOBILE_NO_2 as LAPU_MOBILE_NO_2,a.FTA_MOBILE_NO as FTA_MOBILE_NO,a.FTA_MOBILE_NO_2 as FTA_MOBILE_NO_2," +
							"(SELECT LOGIN_NAME FROM VR_LOGIN_MASTER where LOGIN_ID = a.PARENT_ACCOUNT) as Parent_Login_ID, "+
							"(SELECT LEVEL_NAME FROM VR_ACCOUNT_LEVEL_MASTER where LEVEL_ID = a.ACCOUNT_LEVEL -1) as Parent_Account_Type, "+
							"(SELECT ACCOUNT_NAME FROM VR_ACCOUNT_DETAILS where ACCOUNT_ID = a.PARENT_ACCOUNT) as Parent_Account_Name, "+			
							"(SELECT CIRCLE_NAME FROM VR_CIRCLE_MASTER where CIRCLE_ID = map1.CIRCLE_ID fetch first 1 row only ) as Circle, "+
							"(SELECT CITY_NAME FROM VR_CITY_MASTER where CITY_ID = a.CITY_ID) as City,"+
							"( select WH_NAME from DP_WH_MASTER where WH_CODE in(select wh_code from DPDTH.DP_WH_DIST_MAP where DISTID = A.ACCOUNT_ID) )  as WH_NAME, "+
							"EMAIL as Email,MOBILE_NUMBER as Mobile,ACCOUNT_ADDRESS as Address,PIN_NUMBER as Pin, "+
							"(SELECT ZONE_NAME FROM DP_ZONE_MASTER where ZONE_ID = a.ZONE_ID) as Zone_Name, "+
							"(SELECT EXT_DIST_ID FROM DP_DISTRIBUTOR_MAPPING where DP_DIST_ID = a.ACCOUNT_ID FETCH FIRST 1 row only) as BoTree_Code, "+
							"DISTRIBUTOR_LOCATOR as Oracle_Locator, STATUS as Status, DATE(CREATED_DT) as Created_Date, "+
							"(SELECT ACCOUNT_NAME FROM VR_ACCOUNT_DETAILS where ACCOUNT_ID = a.CREATED_BY) as Created_By, "+
							"DATE(UPDATED_DT) as Last_Updated_Date, (SELECT ACCOUNT_NAME FROM VR_ACCOUNT_DETAILS where ACCOUNT_ID = a.UPDATED_BY ) as Last_Updated_By, "+
							"coalesce(to_char(LAST_LOGIN_TIME,'dd/MM/yyyy'),'') as Last_Login_date, "+ 
							"case when LAST_LOGIN_TIME is null then coalesce(to_char((IPWD_CHANGED_DT+45 DAYS),'dd/MM/yyyy'),'') else coalesce(to_char((IPWD_CHANGED_DT),'dd/MM/yyyy'),'') end as IPWD_CHANGED_DT , "+
							"a.ACCOUNT_ID as ACC_ID ,a.HIST_DATE as HIST_DATE,a.SR_NUMBER as SR_NUMBER,a.APPROVAL_1 as APPROVAL_1,a.APPROVAL_2 as APPROVAL_2,a.REMARKS as REMARKS "+
							"from VR_LOGIN_MASTER_HIST b , VR_ACCOUNT_DETAILS_HIST a left outer join  DP_ACCOUNT_CIRCLE_MAP_HIST map1 on map1.account_id= a.account_id and map1.HIST_DATE=a.HIST_DATE "+
							"where a.ACCOUNT_ID=b.LOGIN_ID and b.HIST_DATE=a.HIST_DATE and b.STATUS IN( "+status+ ")";
					
					circleFilter = "map1";
					flag = "true";
			}*/
				else if (accountMultiSelectedIdsArr[i].equalsIgnoreCase("6"))
				{
					sql = "SELECT b.LOGIN_NAME as Login_ID, a.ACCOUNT_LEVEL, " +
					"			(SELECT LEVEL_NAME FROM VR_ACCOUNT_LEVEL_MASTER where LEVEL_ID = a.ACCOUNT_LEVEL) as Account_Type," +
					"			a.ACCOUNT_NAME as Contact_Name," +
					"			a.LAPU_MOBILE_NO as Lapu_No," +
					"			a.LAPU_MOBILE_NO_2 as LAPU_MOBILE_NO_2,a.FTA_MOBILE_NO as FTA_MOBILE_NO,a.FTA_MOBILE_NO_2 as FTA_MOBILE_NO_2," +
					//"			(SELECT LOGIN_NAME FROM VR_LOGIN_MASTER where LOGIN_ID = a.PARENT_ACCOUNT) as Parent_Login_ID," +
					"			LOGPARENT.LOGIN_NAME as Parent_Login_ID," +
					"			(SELECT LEVEL_NAME FROM VR_ACCOUNT_LEVEL_MASTER where LEVEL_ID = a.ACCOUNT_LEVEL -1) as Parent_Account_Type," +
					//"			(SELECT ACCOUNT_NAME FROM VR_ACCOUNT_DETAILS where ACCOUNT_ID = a.PARENT_ACCOUNT) as Parent_Account_Name," +		
					"			ACCPARENT.ACCOUNT_NAME as Parent_Account_Name," +
					"			(SELECT CIRCLE_NAME FROM VR_CIRCLE_MASTER where CIRCLE_ID = a.CIRCLE_ID) as Circle," +
					"			(SELECT CITY_NAME FROM VR_CITY_MASTER where CITY_ID = a.CITY_ID) as City," +
					"			( select WH_NAME from DP_WH_MASTER where WH_CODE in(select wh_code from DPDTH.DP_WH_DIST_MAP where DISTID = A.ACCOUNT_ID) )  as WH_NAME,"+
					"			a.EMAIL as Email," +
					"			a.MOBILE_NUMBER as Mobile," +
					"			a.ACCOUNT_ADDRESS as Address," +
					"			a.PIN_NUMBER as Pin," +
					"			(SELECT ZONE_NAME FROM DP_ZONE_MASTER where ZONE_ID = a.ZONE_ID) as Zone_Name," +
					"			c.EXT_DIST_ID  as BoTree_Code," +
					"			a.DISTRIBUTOR_LOCATOR as Oracle_Locator," +
					"			b.STATUS as Status," +
					"			DATE(a.CREATED_DT) as Created_Date," +
					"			(SELECT ACCOUNT_NAME FROM VR_ACCOUNT_DETAILS where ACCOUNT_ID = a.CREATED_BY) as Created_By," +
					"			DATE(a.UPDATED_DT) as Last_Updated_Date," +
					"			(SELECT ACCOUNT_NAME FROM VR_ACCOUNT_DETAILS where ACCOUNT_ID = a.UPDATED_BY ) as Last_Updated_By, " +
					"			coalesce(to_char(b.LAST_LOGIN_TIME,'dd/MM/yyyy'),'') as Last_Login_date, " +
					"			case when b.LAST_LOGIN_TIME is null then coalesce(to_char((b.IPWD_CHANGED_DT+45 DAYS),'dd/MM/yyyy'),'') else coalesce(to_char((b.IPWD_CHANGED_DT),'dd/MM/yyyy'),'') end as IPWD_CHANGED_DT ," +
					" case c.TRANSACTION_TYPE_ID when 1 then 'Sales' else 'CPE' end as TRANSACTION_TYPE, " +
					"			a.ACCOUNT_ID as ACC_ID ,a.HIST_DATE as HIST_DATE,a.SR_NUMBER as SR_NUMBER,a.APPROVAL_1 as APPROVAL_1,a.APPROVAL_2 as APPROVAL_2,a.REMARKS as REMARKS "+	
				//	"			from VR_LOGIN_MASTER_HIST b , VR_ACCOUNT_DETAILS_HIST a " +
					"			from VR_LOGIN_MASTER_HIST b , VR_ACCOUNT_DETAILS_HIST a ,DP_DISTRIBUTOR_MAPPING c,VR_LOGIN_MASTER LOGPARENT , VR_ACCOUNT_DETAILS ACCPARENT "+
					"			where a.ACCOUNT_ID=b.LOGIN_ID and b.HIST_DATE=a.HIST_DATE" +
					" and c.DP_DIST_ID = a.account_id  and c.PARENT_ACCOUNT = LOGPARENT.login_id  and ACCPARENT.account_id=c.PARENT_ACCOUNT " + //these conditions added 
					" and b.STATUS IN( "+status+ ") ";
					
					circleFilter = "a";
					flag = "true";
				}
				
				else if (accountMultiSelectedIdsArr[i].equalsIgnoreCase("7"))
				{
					sql = "SELECT LOGIN_NAME as Login_ID, a.ACCOUNT_LEVEL, " +
					"			(SELECT LEVEL_NAME FROM VR_ACCOUNT_LEVEL_MASTER where LEVEL_ID = a.ACCOUNT_LEVEL) as Account_Type," +
					"			ACCOUNT_NAME as Contact_Name," +
					"			LAPU_MOBILE_NO as Lapu_No," +
					"			a.LAPU_MOBILE_NO_2 as LAPU_MOBILE_NO_2,a.FTA_MOBILE_NO as FTA_MOBILE_NO,a.FTA_MOBILE_NO_2 as FTA_MOBILE_NO_2," +
					"			(SELECT LOGIN_NAME FROM VR_LOGIN_MASTER where LOGIN_ID = a.PARENT_ACCOUNT) as Parent_Login_ID," +
					"			(SELECT LEVEL_NAME FROM VR_ACCOUNT_LEVEL_MASTER where LEVEL_ID = a.ACCOUNT_LEVEL -1) as Parent_Account_Type," +
					"			(SELECT ACCOUNT_NAME FROM VR_ACCOUNT_DETAILS where ACCOUNT_ID = a.PARENT_ACCOUNT) as Parent_Account_Name," +			
					"			(SELECT CIRCLE_NAME FROM VR_CIRCLE_MASTER where CIRCLE_ID = a.CIRCLE_ID) as Circle," +
					"			(SELECT CITY_NAME FROM VR_CITY_MASTER where CITY_ID = a.CITY_ID) as City," +
					//"			( select WH_NAME from DP_WH_MASTER where WH_CODE in(select wh_code from DPDTH.DP_WH_DIST_MAP where DISTID = A.ACCOUNT_ID) )  as WH_NAME,"+
					" ''  as WH_NAME,"+ //Changed by Neetika as WH name is not required for this account level it impacts the performance of report
					"			EMAIL as Email," +
					"			MOBILE_NUMBER as Mobile," +
					"			ACCOUNT_ADDRESS as Address," +
					"			PIN_NUMBER as Pin," +
					"			(SELECT ZONE_NAME FROM DP_ZONE_MASTER where ZONE_ID = a.ZONE_ID) as Zone_Name," +
					//"			(SELECT EXT_DIST_ID FROM DP_DISTRIBUTOR_MAPPING where DP_DIST_ID = a.ACCOUNT_ID FETCH FIRST 1 row only) as BoTree_Code," +
					"	'' as BoTree_Code," + //Changed by Neetika as it is not required for this account level it impacts the performance of report
					//"			DISTRIBUTOR_LOCATOR as Oracle_Locator," +
					" '' as Oracle_Locator," + //Changed by Neetika as it is not required for this account level it impacts the performance of report
					"			STATUS as Status," +
					"			DATE(CREATED_DT) as Created_Date," +
					"			(SELECT ACCOUNT_NAME FROM VR_ACCOUNT_DETAILS where ACCOUNT_ID = a.CREATED_BY) as Created_By," +
					"			DATE(UPDATED_DT) as Last_Updated_Date," +
					"			(SELECT ACCOUNT_NAME FROM VR_ACCOUNT_DETAILS where ACCOUNT_ID = a.UPDATED_BY ) as Last_Updated_By, " +
					"			coalesce(to_char(LAST_LOGIN_TIME,'dd/MM/yyyy'),'') as Last_Login_date, " +
					"			case when LAST_LOGIN_TIME is null then coalesce(to_char((IPWD_CHANGED_DT+45 DAYS),'dd/MM/yyyy'),'') else coalesce(to_char((IPWD_CHANGED_DT),'dd/MM/yyyy'),'') end as IPWD_CHANGED_DT ," +
					" case when a.ACCOUNT_LEVEL!=5 then '' when a.TRANSACTION_TYPE=1 then 'Sales' else 'CPE' end as TRANSACTION_TYPE, " +
					"			a.ACCOUNT_ID as ACC_ID ,a.HIST_DATE as HIST_DATE,a.SR_NUMBER as SR_NUMBER,a.APPROVAL_1 as APPROVAL_1,a.APPROVAL_2 as APPROVAL_2,a.REMARKS as REMARKS "+	
					"			from VR_LOGIN_MASTER_HIST b , VR_ACCOUNT_DETAILS_HIST a " +
					"			where a.ACCOUNT_ID=b.LOGIN_ID and b.HIST_DATE=a.HIST_DATE and b.STATUS IN( "+status+ ") ";
					
					circleFilter = "a";
					flag = "true";
				}
				else if (accountMultiSelectedIdsArr[i].equalsIgnoreCase("8"))
				{
					sql = "SELECT LOGIN_NAME as Login_ID, a.ACCOUNT_LEVEL, " +
					"			(SELECT LEVEL_NAME FROM VR_ACCOUNT_LEVEL_MASTER where LEVEL_ID = a.ACCOUNT_LEVEL) as Account_Type," +
					"			ACCOUNT_NAME as Contact_Name," +
					"			LAPU_MOBILE_NO as Lapu_No," +
					"			a.LAPU_MOBILE_NO_2 as LAPU_MOBILE_NO_2,a.FTA_MOBILE_NO as FTA_MOBILE_NO,a.FTA_MOBILE_NO_2 as FTA_MOBILE_NO_2," +
					"			(SELECT LOGIN_NAME FROM VR_LOGIN_MASTER where LOGIN_ID = a.PARENT_ACCOUNT) as Parent_Login_ID," +
					"			(SELECT LEVEL_NAME FROM VR_ACCOUNT_LEVEL_MASTER where LEVEL_ID = a.ACCOUNT_LEVEL -1) as Parent_Account_Type," +
					"			(SELECT ACCOUNT_NAME FROM VR_ACCOUNT_DETAILS where ACCOUNT_ID = a.PARENT_ACCOUNT) as Parent_Account_Name," +			
					"			(SELECT CIRCLE_NAME FROM VR_CIRCLE_MASTER where CIRCLE_ID = a.CIRCLE_ID) as Circle," +
					"			(SELECT CITY_NAME FROM VR_CITY_MASTER where CITY_ID = a.CITY_ID) as City," +
					//"			( select WH_NAME from DP_WH_MASTER where WH_CODE in(select wh_code from DPDTH.DP_WH_DIST_MAP where DISTID = A.ACCOUNT_ID) )  as WH_NAME,"+
					" ''  as WH_NAME,"+ //Changed by Neetika as WH name is not required for this account level it impacts the performance of report
					"			EMAIL as Email," +
					"			RETAILER_LAPU as Mobile," + //Neetika REG
					"			ACCOUNT_ADDRESS as Address," +
					"			PIN_NUMBER as Pin," +
					"			(SELECT ZONE_NAME FROM DP_ZONE_MASTER where ZONE_ID = a.ZONE_ID) as Zone_Name," +
					//"			(SELECT EXT_DIST_ID FROM DP_DISTRIBUTOR_MAPPING where DP_DIST_ID = a.ACCOUNT_ID FETCH FIRST 1 row only) as BoTree_Code," +
					"	'' as BoTree_Code," + //Changed by Neetika as it is not required for this account level it impacts the performance of report
					//"			DISTRIBUTOR_LOCATOR as Oracle_Locator," +
					" '' as Oracle_Locator," + //Changed by Neetika as it is not required for this account level it impacts the performance of report
					"			STATUS as Status," +
					"			DATE(CREATED_DT) as Created_Date," +
					"			(SELECT ACCOUNT_NAME FROM VR_ACCOUNT_DETAILS where ACCOUNT_ID = a.CREATED_BY) as Created_By," +
					"			DATE(UPDATED_DT) as Last_Updated_Date," +
					"			(SELECT ACCOUNT_NAME FROM VR_ACCOUNT_DETAILS where ACCOUNT_ID = a.UPDATED_BY ) as Last_Updated_By, " +
					"			coalesce(to_char(LAST_LOGIN_TIME,'dd/MM/yyyy'),'') as Last_Login_date, " +
					"			case when LAST_LOGIN_TIME is null then coalesce(to_char((IPWD_CHANGED_DT+45 DAYS),'dd/MM/yyyy'),'') else coalesce(to_char((IPWD_CHANGED_DT),'dd/MM/yyyy'),'') end as IPWD_CHANGED_DT ," +
					" case when a.ACCOUNT_LEVEL!=5 then '' when a.TRANSACTION_TYPE=1 then 'Sales' else 'CPE' end as TRANSACTION_TYPE, " +
					"			a.ACCOUNT_ID as ACC_ID ,a.HIST_DATE as HIST_DATE,a.SR_NUMBER as SR_NUMBER,a.APPROVAL_1 as APPROVAL_1,a.APPROVAL_2 as APPROVAL_2,a.REMARKS as REMARKS "+	
					"			from VR_LOGIN_MASTER b , VR_ACCOUNT_DETAILS_HIST a " +
					"			where a.ACCOUNT_ID=b.LOGIN_ID  and b.STATUS IN( "+status+ ") ";
					
					circleFilter = "a";
					flag = "true";
				}
			/*else if(accountType != null && (accountType.equalsIgnoreCase("6") || accountType.equalsIgnoreCase("7") 
					|| accountType.equalsIgnoreCase("8") ) )
			{
				sql = "SELECT LOGIN_NAME as Login_ID, a.ACCOUNT_LEVEL, " +
				"			(SELECT LEVEL_NAME FROM VR_ACCOUNT_LEVEL_MASTER where LEVEL_ID = a.ACCOUNT_LEVEL) as Account_Type," +
				"			ACCOUNT_NAME as Contact_Name," +
				"			LAPU_MOBILE_NO as Lapu_No," +
				"			a.LAPU_MOBILE_NO_2 as LAPU_MOBILE_NO_2,a.FTA_MOBILE_NO as FTA_MOBILE_NO,a.FTA_MOBILE_NO_2 as FTA_MOBILE_NO_2," +
				"			(SELECT LOGIN_NAME FROM VR_LOGIN_MASTER where LOGIN_ID = a.PARENT_ACCOUNT) as Parent_Login_ID," +
				"			(SELECT LEVEL_NAME FROM VR_ACCOUNT_LEVEL_MASTER where LEVEL_ID = a.ACCOUNT_LEVEL -1) as Parent_Account_Type," +
				"			(SELECT ACCOUNT_NAME FROM VR_ACCOUNT_DETAILS where ACCOUNT_ID = a.PARENT_ACCOUNT) as Parent_Account_Name," +			
				"			(SELECT CIRCLE_NAME FROM VR_CIRCLE_MASTER where CIRCLE_ID = a.CIRCLE_ID) as Circle," +
				"			(SELECT CITY_NAME FROM VR_CITY_MASTER where CITY_ID = a.CITY_ID) as City," +
				"			( select WH_NAME from DP_WH_MASTER where WH_CODE in(select wh_code from DPDTH.DP_WH_DIST_MAP where DISTID = A.ACCOUNT_ID) )  as WH_NAME,"+
				"			EMAIL as Email," +
				"			MOBILE_NUMBER as Mobile," +
				"			ACCOUNT_ADDRESS as Address," +
				"			PIN_NUMBER as Pin," +
				"			(SELECT ZONE_NAME FROM DP_ZONE_MASTER where ZONE_ID = a.ZONE_ID) as Zone_Name," +
				"			(SELECT EXT_DIST_ID FROM DP_DISTRIBUTOR_MAPPING where DP_DIST_ID = a.ACCOUNT_ID FETCH FIRST 1 row only) as BoTree_Code," +
				"			DISTRIBUTOR_LOCATOR as Oracle_Locator," +
				"			STATUS as Status," +
				"			DATE(CREATED_DT) as Created_Date," +
				"			(SELECT ACCOUNT_NAME FROM VR_ACCOUNT_DETAILS where ACCOUNT_ID = a.CREATED_BY) as Created_By," +
				"			DATE(UPDATED_DT) as Last_Updated_Date," +
				"			(SELECT ACCOUNT_NAME FROM VR_ACCOUNT_DETAILS where ACCOUNT_ID = a.UPDATED_BY ) as Last_Updated_By, " +
				"			coalesce(to_char(LAST_LOGIN_TIME,'dd/MM/yyyy'),'') as Last_Login_date, " +
				"			case when LAST_LOGIN_TIME is null then coalesce(to_char((IPWD_CHANGED_DT+45 DAYS),'dd/MM/yyyy'),'') else coalesce(to_char((IPWD_CHANGED_DT),'dd/MM/yyyy'),'') end as IPWD_CHANGED_DT ," +
				"			a.ACCOUNT_ID as ACC_ID ,a.HIST_DATE as HIST_DATE,a.SR_NUMBER as SR_NUMBER,a.APPROVAL_1 as APPROVAL_1,a.APPROVAL_2 as APPROVAL_2,a.REMARKS as REMARKS "+	
				"			from VR_LOGIN_MASTER_HIST b , VR_ACCOUNT_DETAILS_HIST a " +
				"			where a.ACCOUNT_ID=b.LOGIN_ID and b.HIST_DATE=a.HIST_DATE and b.STATUS IN( "+status+ ") ";
				
				circleFilter = "a";
				flag = "true";
			}*/
			
			
			if(flag != null && flag.equalsIgnoreCase("true"))  {
			
			if(reportDto.getSearchOption().equalsIgnoreCase("-1")) {
				
			}
			else if(reportDto.getSearchOption().equalsIgnoreCase("LOGIN_ID")) {
				searchCriteria = " AND b.LOGIN_ID = (SELECT LOGIN_ID FROM VR_LOGIN_MASTER WHERE UPPER(LOGIN_NAME) ='" +reportDto.getSearchCriteria().toUpperCase() +"')";
			}
			else if(reportDto.getSearchOption().equalsIgnoreCase("ACCOUNT_NAME")) {
				searchCriteria = " AND A.ACCOUNT_ID = (SELECT ACCOUNT_ID FROM VR_ACCOUNT_DETAILS WHERE UPPER(ACCOUNT_NAME) ='" +reportDto.getSearchCriteria().toUpperCase() +"')";
			}
			else if(reportDto.getSearchOption().equalsIgnoreCase("MOBILE_NO")) {
				searchCriteria = "  AND ((a.MOBILE_NUMBER = '"+reportDto.getSearchCriteria()+"' and a.MOBILE_NUMBER is not null) or (a.RETAILER_LAPU is not null and a.RETAILER_LAPU = '"+reportDto.getSearchCriteria()+"'))";  //Neetika REG change
			}
			else if(reportDto.getSearchOption().equalsIgnoreCase("EMAIL_ID")) {
				searchCriteria = " AND UPPER(a.EMAIL) = '" +reportDto.getSearchCriteria().toUpperCase()+"'";
			}
			else if(reportDto.getSearchOption().equalsIgnoreCase("PIN_CODE")) {
				searchCriteria = " AND a.PIN_NUMBER = "+ reportDto.getSearchCriteria();
			}
			else if(reportDto.getSearchOption().equalsIgnoreCase("CITY")) {
				searchCriteria = " AND a.CITY_ID = (SELECT CITY_ID FROM VR_CITY_MASTER WHERE UPPER(CITY_NAME) = '" +reportDto.getSearchCriteria().toUpperCase()+"')";
			}

			
			
			logger.info("reportDto.getAccountLevel():::::"+reportDto.getAccountLevel());
			logger.info("accountMultiSelectedIdsArr[i]:::::"+accountMultiSelectedIdsArr[i]);
			logger.info("reportDto.getHiddenCircleSelecIds():::::"+reportDto.getHiddenCircleSelecIds());
			
			if(reportDto.getAccountLevel() == 5) {
				//aman
				//if(accountMultiSelectedIdsArr[i].equals("2,3,4,5,6,7,8")) {
				if(accountMultiSelectedIdsArr[i].equals("1,2,3,4,5,6,7,8")) {
					query1 = sql + " AND a.PARENT_ACCOUNT IN ("+reportDto.getAccountId()+")";
					query1 += searchCriteria.toString();
		
					query2 = 	" UNION ALL " + sql + " AND a.PARENT_ACCOUNT IN (SELECT ACCOUNT_ID FROM VR_ACCOUNT_DETAILS where PARENT_ACCOUNT IN ("+reportDto.getAccountId()+"))";;
					query2 += searchCriteria.toString();
					
					query3 =	" UNION ALL " + sql + " AND a.PARENT_ACCOUNT IN (SELECT ACCOUNT_ID FROM VR_ACCOUNT_DETAILS where PARENT_ACCOUNT IN (SELECT ACCOUNT_ID FROM VR_ACCOUNT_DETAILS where PARENT_ACCOUNT IN ("+reportDto.getAccountId()+")))";;
					query3 += searchCriteria.toString();
					
					sql = query1 + query2 + query3;
					
				} else if(accountMultiSelectedIdsArr[i].equals("6")) {
					sql +=" AND a.PARENT_ACCOUNT IN ("+reportDto.getAccountId()+")";
				} else if (accountMultiSelectedIdsArr[i].equals("7")) {
					sql +=" AND a.PARENT_ACCOUNT IN (SELECT ACCOUNT_ID FROM VR_ACCOUNT_DETAILS where PARENT_ACCOUNT IN ("+reportDto.getAccountId()+"))";
				} else if (accountMultiSelectedIdsArr[i].equals("8")) {
					sql +=" AND a.PARENT_ACCOUNT IN (SELECT ACCOUNT_ID FROM VR_ACCOUNT_DETAILS where PARENT_ACCOUNT IN (SELECT ACCOUNT_ID FROM VR_ACCOUNT_DETAILS where PARENT_ACCOUNT IN ("+reportDto.getAccountId()+")))";
				}
			} else if (reportDto.getAccountLevel() == 6) {

				if(accountMultiSelectedIdsArr[i].equals("2,3,4,5,6,7,8")) {
					query1 = sql + " AND a.PARENT_ACCOUNT IN ("+reportDto.getAccountId()+")";
					query1 += searchCriteria.toString();
		
					query2 = 	" UNION ALL " + sql + " AND a.PARENT_ACCOUNT IN (SELECT ACCOUNT_ID FROM VR_ACCOUNT_DETAILS where PARENT_ACCOUNT IN ("+reportDto.getAccountId()+"))";
					query2 += searchCriteria.toString();
									
					sql = query1 + query2;
						
				} else if (accountMultiSelectedIdsArr[i].equals("7")) {
					sql +=" AND a.PARENT_ACCOUNT IN ("+reportDto.getAccountId()+")";
				} else if (accountMultiSelectedIdsArr[i].equals("8")) {
					logger.info("accountMultiSelectedIdsArr=8");
					sql +=" AND a.PARENT_ACCOUNT IN (SELECT ACCOUNT_ID FROM VR_ACCOUNT_DETAILS where PARENT_ACCOUNT IN ("+reportDto.getAccountId()+"))";
				}

			} else if(accountMultiSelectedIdsArr[i].equals("1,2,3,4,5,6,7,8") && reportDto.getAccountLevel() == 0 ){
				logger.info("accountMultiSelectedIdsArr:::::::::::::::::::::::::::::=1"+accountMultiSelectedIdsArr);
				if(!reportDto.getHiddenCircleSelecIds().equals("0")) {
					sql = sql + " and a.ACCOUNT_LEVEL > " + reportDto.getAccountLevel() +" and "+circleFilter+".CIRCLE_ID IN(0,"+ reportDto.getHiddenCircleSelecIds() +")";
				} else {
					logger.info("accountMultiSelectedIdsArr:::::::::::::::::::::::::::::=1"+accountMultiSelectedIdsArr);
					sql = sql + " and a.ACCOUNT_LEVEL > " + reportDto.getAccountLevel();
				}
			} else if (accountMultiSelectedIdsArr[i].equals("1")) {
				logger.info("accountMultiSelectedIdsArr:::::::::::::::::::::::::::::=1"+accountMultiSelectedIdsArr);
				sql = sql + " and a.ACCOUNT_LEVEL = " + accountMultiSelectedIdsArr[i];
			} else if (accountMultiSelectedIdsArr[i].equals("2,3,4,5,6,7,8")) {	
				logger.info("accountMultiSelectedIdsArr:::::::::::::::::::::::::::::=1"+accountMultiSelectedIdsArr);
				if(!reportDto.getHiddenCircleSelecIds().equals("0")) {
					logger.info("accountMultiSelectedIdsArr:::::::::::::::::::::::::::::=1"+accountMultiSelectedIdsArr);
					sql = sql + " and a.ACCOUNT_LEVEL > " + reportDto.getAccountLevel() +" and "+circleFilter+".CIRCLE_ID IN("+ reportDto.getHiddenCircleSelecIds() +")";
				} else {
					logger.info("accountMultiSelectedIdsArr:::::::::::::::::::::::::::::=1"+accountMultiSelectedIdsArr);
					sql = sql + " and a.ACCOUNT_LEVEL > " + reportDto.getAccountLevel();
				}
			} else {
				if(!reportDto.getHiddenCircleSelecIds().equals("0")) {
					logger.info("accountMultiSelectedIdsArr:::::::::::::::::::::::::::::=1"+accountMultiSelectedIdsArr);
					sql = sql + " and a.ACCOUNT_LEVEL = " + accountMultiSelectedIdsArr[i] +" and "+circleFilter+".CIRCLE_ID IN("+ reportDto.getHiddenCircleSelecIds() +")";
				} else {
					logger.info("accountMultiSelectedIdsArr:::::::::::::::::::::::::::::=1"+accountMultiSelectedIdsArr);
					sql = sql + " and a.ACCOUNT_LEVEL = " + accountMultiSelectedIdsArr[i];
				}
			}
			
			
				//String orderBy = " ORDER BY map1.CIRCLE_ID, a.ACCOUNT_ID,b.HIST_DATE ASC" ;
			String orderBy=null;
			if(accountMultiSelectedIdsArr[i].equalsIgnoreCase("6") || accountMultiSelectedIdsArr[i].equalsIgnoreCase("7") || accountMultiSelectedIdsArr[i].equalsIgnoreCase("8"))
				orderBy=" ORDER BY a.CIRCLE_ID, a.ACCOUNT_ID,a.HIST_DATE ASC" ;
			else
				orderBy=" ORDER BY map1.CIRCLE_ID, a.ACCOUNT_ID,a.HIST_DATE ASC" ;
				
			
				sql = sql + searchCriteria + orderBy;
			}
			System.out.println(accountMultiSelectedIdsArr[i]);
			System.out.println(reportDto.getAccountLevel() );
			
			
			logger.info("Sql Final ::::::"+sql);
			ps = connection.prepareStatement(sql);			

			rsetReport = ps.executeQuery();
			AccountDetailReportDto reportReturnDto =null;
	
			
			while(rsetReport.next()){
				//logger.info("login id:::::"+rsetReport.getString("LOGIN_ID"));
				reportReturnDto =new AccountDetailReportDto();
				reportReturnDto.setDistLoginId(rsetReport.getString("LOGIN_ID"));
				reportReturnDto.setAccountType(rsetReport.getString("ACCOUNT_TYPE"));
				reportReturnDto.setAccountName(rsetReport.getString("CONTACT_NAME"));
				reportReturnDto.setLapuNo(rsetReport.getString("Lapu_No"));
				reportReturnDto.setLapuNo2(rsetReport.getString("LAPU_MOBILE_NO_2"));
				reportReturnDto.setFtaNo(rsetReport.getString("FTA_MOBILE_NO"));
				reportReturnDto.setFtaNo2(rsetReport.getString("FTA_MOBILE_NO_2"));
				reportReturnDto.setParentLoginId(rsetReport.getString("PARENT_LOGIN_ID"));
				reportReturnDto.setParentAccountType(rsetReport.getString("PARENT_ACCOUNT_TYPE"));	
				reportReturnDto.setParentAccountName(rsetReport.getString("PARENT_ACCOUNT_NAME"));				
				reportReturnDto.setCircle(rsetReport.getString("CIRCLE"));
				reportReturnDto.setCity(rsetReport.getString("CITY")); 
				reportReturnDto.setEmailId(rsetReport.getString("EMAIL"));
				
				reportReturnDto.setMobileNo(rsetReport.getString("Mobile"));
				reportReturnDto.setAddress(rsetReport.getString("ADDRESS"));
				reportReturnDto.setPin(rsetReport.getString("PIN"));
				reportReturnDto.setZone(rsetReport.getString("ZONE_NAME"));
				reportReturnDto.setBotreeCode(rsetReport.getString("BOTREE_CODE"));
				reportReturnDto.setOracleLocatorCode(rsetReport.getString("ORACLE_LOCATOR"));
				if (rsetReport.getString("STATUS").equalsIgnoreCase("A")){	reportReturnDto.setStatus("Active");
				}
				else  if (rsetReport.getString("STATUS").equalsIgnoreCase("I"))
				{ 
					reportReturnDto.setStatus("Inactive"); 
				}
				else  if (rsetReport.getString("STATUS").equalsIgnoreCase("L"))
				{ 
					reportReturnDto.setStatus("Inactive Due to Lapu Tree"); 
				}
				else  if (rsetReport.getString("STATUS").equalsIgnoreCase("C"))
				{ 
					reportReturnDto.setStatus("Close"); 
				}
				//reportReturnDto.setStatus(rsetReport.getString("STATUS"));
				reportReturnDto.setCreatedDate(Utility.getDateAsString(rsetReport.getDate("CREATED_DATE"), "dd-MMM-yyyy"));
				reportReturnDto.setCreatedBy(rsetReport.getString("CREATED_BY"));
				reportReturnDto.setLastUpdatedDate(Utility.getDateAsString(rsetReport.getDate("LAST_UPDATED_DATE"), "dd-MMM-yyyy"));
				reportReturnDto.setLastUpdatedBy(rsetReport.getString("LAST_UPDATED_BY"));
				reportReturnDto.setLastLoginDate((rsetReport.getString("LAST_LOGIN_DATE")));
				reportReturnDto.setWhName(rsetReport.getString("WH_NAME"));
				reportReturnDto.setPassChangeDT(rsetReport.getString("IPWD_CHANGED_DT"));
				reportReturnDto.setSrNo(rsetReport.getString("SR_NUMBER"));
				Date d=rsetReport.getDate("HIST_DATE");
				reportReturnDto.setHisDate(d.toString());
				reportReturnDto.setTransType(rsetReport.getString("TRANSACTION_TYPE"));
				reportReturnDto.setApproval1(rsetReport.getString("APPROVAL_1"));
				reportReturnDto.setApproval2(rsetReport.getString("APPROVAL_2"));
				reportReturnDto.setRemarks(rsetReport.getString("REMARKS"));
				
				listReturn.add(reportReturnDto);
			}
			ps=null;
			rsetReport=null;
			sql=null;
			logger.info("report generated and added to DTO for account id :"+accountMultiSelectedIdsArr[i]);
			} // if for not -1 is closed here

				// =============== this block is for all account type =================
				//if(accountType.equals("-1") )
				//logger.info("accountMultiSelectedIds::::::::::::Neetika.."+accountMultiSelectedIds);
				if(accountMultiSelectedIds.equals("-1") )
				{
					logger.info("Generating report for All account type ::::::::::::::::::::::");
					sql = "SELECT LOGIN_NAME as Login_ID,a.CIRCLE_ID,a.ACCOUNT_ID, a.ACCOUNT_LEVEL, " +
						"			(SELECT LEVEL_NAME FROM VR_ACCOUNT_LEVEL_MASTER where LEVEL_ID = a.ACCOUNT_LEVEL) as Account_Type," +
						"			ACCOUNT_NAME as Contact_Name," +
						"			LAPU_MOBILE_NO as Lapu_No," +
						"			a.LAPU_MOBILE_NO_2 as LAPU_MOBILE_NO_2,a.FTA_MOBILE_NO as FTA_MOBILE_NO,a.FTA_MOBILE_NO_2 as FTA_MOBILE_NO_2," +
						"			(SELECT LOGIN_NAME FROM VR_LOGIN_MASTER where LOGIN_ID = a.PARENT_ACCOUNT) as Parent_Login_ID," +
						"			(SELECT LEVEL_NAME FROM VR_ACCOUNT_LEVEL_MASTER where LEVEL_ID = a.ACCOUNT_LEVEL -1) as Parent_Account_Type," +
						"			(SELECT ACCOUNT_NAME FROM VR_ACCOUNT_DETAILS where ACCOUNT_ID = a.PARENT_ACCOUNT) as Parent_Account_Name," +			
						"			(SELECT CIRCLE_NAME FROM VR_CIRCLE_MASTER where CIRCLE_ID = map1.CIRCLE_ID fetch first 1 row only ) as Circle," +
						"			(SELECT CITY_NAME FROM VR_CITY_MASTER where CITY_ID = a.CITY_ID) as City," +
						"			( select WH_NAME from DP_WH_MASTER where WH_CODE in(select wh_code from DPDTH.DP_WH_DIST_MAP where DISTID = A.ACCOUNT_ID) )  as WH_NAME,"+
						"			EMAIL as Email," +
						"			MOBILE_NUMBER as Mobile," +
						"			ACCOUNT_ADDRESS as Address," +
						"			PIN_NUMBER as Pin," +
						"			(SELECT ZONE_NAME FROM DP_ZONE_MASTER where ZONE_ID = a.ZONE_ID) as Zone_Name," +
						"			(SELECT EXT_DIST_ID FROM DP_DISTRIBUTOR_MAPPING where DP_DIST_ID = a.ACCOUNT_ID FETCH FIRST 1 row only) as BoTree_Code," +
						"			DISTRIBUTOR_LOCATOR as Oracle_Locator," +
						"			STATUS as Status," +
						"			DATE(CREATED_DT) as Created_Date," +
						"			(SELECT ACCOUNT_NAME FROM VR_ACCOUNT_DETAILS where ACCOUNT_ID = a.CREATED_BY) as Created_By," +
						"			DATE(UPDATED_DT) as Last_Updated_Date," +
						"			(SELECT ACCOUNT_NAME FROM VR_ACCOUNT_DETAILS where ACCOUNT_ID = a.UPDATED_BY ) as Last_Updated_By," +
						"			coalesce(to_char(LAST_LOGIN_TIME,'dd/MM/yyyy'),'') as Last_Login_date, " +
						"			case when LAST_LOGIN_TIME is null then coalesce(to_char((IPWD_CHANGED_DT+45 DAYS),'dd/MM/yyyy'),'') else coalesce(to_char((IPWD_CHANGED_DT),'dd/MM/yyyy'),'') end as IPWD_CHANGED_DT ," +
						" case when a.ACCOUNT_LEVEL!=5 then '' when a.TRANSACTION_TYPE=1 then 'Sales' else 'CPE' end as TRANSACTION_TYPE, " +
						"			a.ACCOUNT_ID as ACC_ID ,a.HIST_DATE as HIST_DATE,a.SR_NUMBER as SR_NUMBER,a.APPROVAL_1 as APPROVAL_1,a.APPROVAL_2 as APPROVAL_2,a.REMARKS as REMARKS "+	
						"			from VR_LOGIN_MASTER_HIST b , VR_ACCOUNT_DETAILS_HIST a left outer join  DP_ACCOUNT_CIRCLE_MAP_HIST map1 on map1.account_id= a.account_id  " +
						"			where a.ACCOUNT_ID=b.LOGIN_ID and b.HIST_DATE=a.HIST_DATE and b.STATUS IN( "+status+ ") and account_level!=8 " ;
						
						sql2 = " 				UNION " +
						" 		SELECT LOGIN_NAME as Login_ID,a.CIRCLE_ID,a.ACCOUNT_ID, a.ACCOUNT_LEVEL, " +
						"			(SELECT LEVEL_NAME FROM VR_ACCOUNT_LEVEL_MASTER where LEVEL_ID = a.ACCOUNT_LEVEL) as Account_Type," +
						"			ACCOUNT_NAME as Contact_Name," +
						"			LAPU_MOBILE_NO as Lapu_No," +
						"			a.LAPU_MOBILE_NO_2 as LAPU_MOBILE_NO_2,a.FTA_MOBILE_NO as FTA_MOBILE_NO,a.FTA_MOBILE_NO_2 as FTA_MOBILE_NO_2," +
						"			(SELECT LOGIN_NAME FROM VR_LOGIN_MASTER where LOGIN_ID = a.PARENT_ACCOUNT) as Parent_Login_ID," +
						"			(SELECT LEVEL_NAME FROM VR_ACCOUNT_LEVEL_MASTER where LEVEL_ID = a.ACCOUNT_LEVEL -1) as Parent_Account_Type," +
						"			(SELECT ACCOUNT_NAME FROM VR_ACCOUNT_DETAILS where ACCOUNT_ID = a.PARENT_ACCOUNT) as Parent_Account_Name," +			
						"			(SELECT CIRCLE_NAME FROM VR_CIRCLE_MASTER where CIRCLE_ID = a.CIRCLE_ID) as Circle," +
						"			(SELECT CITY_NAME FROM VR_CITY_MASTER where CITY_ID = a.CITY_ID) as City," +
						"			( select WH_NAME from DP_WH_MASTER where WH_CODE in(select wh_code from DPDTH.DP_WH_DIST_MAP where DISTID = A.ACCOUNT_ID) )  as WH_NAME,"+
						"			EMAIL as Email," +
						"			MOBILE_NUMBER as Mobile," +
						"			ACCOUNT_ADDRESS as Address," +
						"			PIN_NUMBER as Pin," +
						"			(SELECT ZONE_NAME FROM DP_ZONE_MASTER where ZONE_ID = a.ZONE_ID) as Zone_Name," +
						"			(SELECT EXT_DIST_ID FROM DP_DISTRIBUTOR_MAPPING where DP_DIST_ID = a.ACCOUNT_ID FETCH FIRST 1 row only) as BoTree_Code," +
						"			DISTRIBUTOR_LOCATOR as Oracle_Locator," +
						"			STATUS as Status," +
						"			DATE(CREATED_DT) as Created_Date," +
						"			(SELECT ACCOUNT_NAME FROM VR_ACCOUNT_DETAILS where ACCOUNT_ID = a.CREATED_BY) as Created_By," +
						"			DATE(UPDATED_DT) as Last_Updated_Date," +
						"			(SELECT ACCOUNT_NAME FROM VR_ACCOUNT_DETAILS where ACCOUNT_ID = a.UPDATED_BY ) as Last_Updated_By," +
						"			coalesce(to_char(LAST_LOGIN_TIME,'dd/MM/yyyy'),'') as Last_Login_date, " +
						"			case when LAST_LOGIN_TIME is null then coalesce(to_char((IPWD_CHANGED_DT+45 DAYS),'dd/MM/yyyy'),'') else coalesce(to_char((IPWD_CHANGED_DT),'dd/MM/yyyy'),'') end as IPWD_CHANGED_DT ," +
						" case when a.ACCOUNT_LEVEL!=5 then '' when a.TRANSACTION_TYPE=1 then 'Sales' else 'CPE' end as TRANSACTION_TYPE, " +
						"			a.ACCOUNT_ID as ACC_ID ,a.HIST_DATE as HIST_DATE,a.SR_NUMBER as SR_NUMBER,a.APPROVAL_1 as APPROVAL_1,a.APPROVAL_2 as APPROVAL_2,a.REMARKS as REMARKS "+	
						"			from VR_LOGIN_MASTER_HIST b , VR_ACCOUNT_DETAILS_HIST a " +
						"			where a.ACCOUNT_ID=b.LOGIN_ID  and b.HIST_DATE=a.HIST_DATE and b.STATUS IN( "+status+ ") and account_level!=8  ";
						
						sql3 = " 				UNION " +
						" 		SELECT LOGIN_NAME as Login_ID,a.CIRCLE_ID,a.ACCOUNT_ID, a.ACCOUNT_LEVEL, " +
						"			(SELECT LEVEL_NAME FROM VR_ACCOUNT_LEVEL_MASTER where LEVEL_ID = a.ACCOUNT_LEVEL) as Account_Type," +
						"			ACCOUNT_NAME as Contact_Name," +
						"			LAPU_MOBILE_NO as Lapu_No," +
						"			a.LAPU_MOBILE_NO_2 as LAPU_MOBILE_NO_2,a.FTA_MOBILE_NO as FTA_MOBILE_NO,a.FTA_MOBILE_NO_2 as FTA_MOBILE_NO_2," +
						"			(SELECT LOGIN_NAME FROM VR_LOGIN_MASTER where LOGIN_ID = a.PARENT_ACCOUNT) as Parent_Login_ID," +
						"			(SELECT LEVEL_NAME FROM VR_ACCOUNT_LEVEL_MASTER where LEVEL_ID = a.ACCOUNT_LEVEL -1) as Parent_Account_Type," +
						"			(SELECT ACCOUNT_NAME FROM VR_ACCOUNT_DETAILS where ACCOUNT_ID = a.PARENT_ACCOUNT) as Parent_Account_Name," +			
						"			(SELECT CIRCLE_NAME FROM VR_CIRCLE_MASTER where CIRCLE_ID = a.CIRCLE_ID) as Circle," +
						"			(SELECT CITY_NAME FROM VR_CITY_MASTER where CITY_ID = a.CITY_ID) as City," +
						"			( select WH_NAME from DP_WH_MASTER where WH_CODE in(select wh_code from DPDTH.DP_WH_DIST_MAP where DISTID = A.ACCOUNT_ID) )  as WH_NAME,"+
						"			EMAIL as Email," +
						"			RETAILER_LAPU as Mobile," +
						"			ACCOUNT_ADDRESS as Address," +
						"			PIN_NUMBER as Pin," +
						"			(SELECT ZONE_NAME FROM DP_ZONE_MASTER where ZONE_ID = a.ZONE_ID) as Zone_Name," +
						"			(SELECT EXT_DIST_ID FROM DP_DISTRIBUTOR_MAPPING where DP_DIST_ID = a.ACCOUNT_ID FETCH FIRST 1 row only) as BoTree_Code," +
						"			DISTRIBUTOR_LOCATOR as Oracle_Locator," +
						"			STATUS as Status," +
						"			DATE(CREATED_DT) as Created_Date," +
						"			(SELECT ACCOUNT_NAME FROM VR_ACCOUNT_DETAILS where ACCOUNT_ID = a.CREATED_BY) as Created_By," +
						"			DATE(UPDATED_DT) as Last_Updated_Date," +
						"			(SELECT ACCOUNT_NAME FROM VR_ACCOUNT_DETAILS where ACCOUNT_ID = a.UPDATED_BY ) as Last_Updated_By," +
						"			coalesce(to_char(LAST_LOGIN_TIME,'dd/MM/yyyy'),'') as Last_Login_date, " +
						"			case when LAST_LOGIN_TIME is null then coalesce(to_char((IPWD_CHANGED_DT+45 DAYS),'dd/MM/yyyy'),'') else coalesce(to_char((IPWD_CHANGED_DT),'dd/MM/yyyy'),'') end as IPWD_CHANGED_DT ," +
						" case when a.ACCOUNT_LEVEL!=5 then '' when a.TRANSACTION_TYPE=1 then 'Sales' else 'CPE' end as TRANSACTION_TYPE, " +
						"			a.ACCOUNT_ID as ACC_ID ,a.HIST_DATE as HIST_DATE,a.SR_NUMBER as SR_NUMBER,a.APPROVAL_1 as APPROVAL_1,a.APPROVAL_2 as APPROVAL_2,a.REMARKS as REMARKS "+	
						"			from VR_LOGIN_MASTER b , VR_ACCOUNT_DETAILS_HIST a " +
						"			where a.ACCOUNT_ID=b.LOGIN_ID  and b.STATUS IN( "+status+ ") and account_level=8  ";
						
						
						
						if(reportDto.getSearchOption().equalsIgnoreCase("-1")) {
							
						}
						else if(reportDto.getSearchOption().equalsIgnoreCase("LOGIN_ID")) {
							searchCriteria = " AND LOGIN_ID = (SELECT LOGIN_ID FROM VR_LOGIN_MASTER WHERE UPPER(LOGIN_NAME) ='" +reportDto.getSearchCriteria().toUpperCase() +"')";
						}
						else if(reportDto.getSearchOption().equalsIgnoreCase("ACCOUNT_NAME")) {
							searchCriteria = " AND ACCOUNT_ID = (SELECT ACCOUNT_ID FROM VR_ACCOUNT_DETAILS WHERE UPPER(ACCOUNT_NAME) ='" +reportDto.getSearchCriteria().toUpperCase() +"')";
						}
						else if(reportDto.getSearchOption().equalsIgnoreCase("MOBILE_NO")) {
							searchCriteria = " AND ((a.MOBILE_NUMBER = '"+reportDto.getSearchCriteria()+"' and a.MOBILE_NUMBER is not null) or (a.RETAILER_LAPU is not null and a.RETAILER_LAPU = '"+reportDto.getSearchCriteria()+"'))";  //Neetika REG change
						}
						else if(reportDto.getSearchOption().equalsIgnoreCase("EMAIL_ID")) {
							searchCriteria = " AND UPPER(EMAIL) = '" +reportDto.getSearchCriteria().toUpperCase()+"'";
						}
						else if(reportDto.getSearchOption().equalsIgnoreCase("PIN_CODE")) {
							searchCriteria = " AND PIN_NUMBER = "+ reportDto.getSearchCriteria();
						}
						else if(reportDto.getSearchOption().equalsIgnoreCase("CITY")) {
							searchCriteria = " AND CITY_ID = (SELECT CITY_ID FROM VR_CITY_MASTER WHERE UPPER(CITY_NAME) = '" +reportDto.getSearchCriteria().toUpperCase()+"')";
						}

						
						
						logger.info("reportDto.getAccountLevel():::::"+reportDto.getAccountLevel());
						logger.info("accountType:::::"+accountType);
						logger.info("reportDto.getHiddenCircleSelecIds():::::"+reportDto.getHiddenCircleSelecIds());
						
					  if(accountType.equals("-1") && reportDto.getAccountLevel() == 0 ){
							if(!reportDto.getHiddenCircleSelecIds().equals("0")) {
								sql = sql + " and a.ACCOUNT_LEVEL > " + reportDto.getAccountLevel() +" and map1.CIRCLE_ID IN(0,"+ reportDto.getHiddenCircleSelecIds() +")";
								sql2 = sql2 + " and a.ACCOUNT_LEVEL > " + reportDto.getAccountLevel() +" and a.CIRCLE_ID IN(0,"+ reportDto.getHiddenCircleSelecIds() +")";
								sql3= sql3 + " and a.ACCOUNT_LEVEL > " + reportDto.getAccountLevel() +" and a.CIRCLE_ID IN(0,"+ reportDto.getHiddenCircleSelecIds() +")";
							} else {
								sql = sql + " and a.ACCOUNT_LEVEL > " + reportDto.getAccountLevel();
								sql2 = sql2 + " and a.ACCOUNT_LEVEL > " + reportDto.getAccountLevel();
								sql3 = sql3 + " and a.ACCOUNT_LEVEL > " + reportDto.getAccountLevel();
							}
						} else if (accountType.equals("1")) {
							sql = sql + " and a.ACCOUNT_LEVEL = " + accountType;
							sql2 = sql2 + " and a.ACCOUNT_LEVEL = " + accountType;
							sql3 = sql3 + " and a.ACCOUNT_LEVEL = " + accountType;
						} else if (accountType.equals("-1")) {	
							if(!reportDto.getHiddenCircleSelecIds().equals("0")) {
								sql = sql + " and a.ACCOUNT_LEVEL > " + reportDto.getAccountLevel() +" and map1.CIRCLE_ID IN("+ reportDto.getHiddenCircleSelecIds() +")";
								sql2 = sql2 + " and a.ACCOUNT_LEVEL > " + reportDto.getAccountLevel() +" and a.CIRCLE_ID IN("+ reportDto.getHiddenCircleSelecIds() +")";
								sql3 = sql3 + " and a.ACCOUNT_LEVEL > " + reportDto.getAccountLevel() +" and a.CIRCLE_ID IN("+ reportDto.getHiddenCircleSelecIds() +")";
							} else {
								sql = sql + " and a.ACCOUNT_LEVEL > " + reportDto.getAccountLevel();
								sql2 = sql2 + " and a.ACCOUNT_LEVEL > " + reportDto.getAccountLevel();
								sql3 = sql3 + " and a.ACCOUNT_LEVEL > " + reportDto.getAccountLevel();
							}
						} else {
							if(!reportDto.getHiddenCircleSelecIds().equals("0")) {
								sql = sql + " and a.ACCOUNT_LEVEL = " + accountType +" and map1.CIRCLE_ID IN("+ reportDto.getHiddenCircleSelecIds() +")";
								sql2 = sql2 + " and a.ACCOUNT_LEVEL = " + accountType +" and a.CIRCLE_ID IN("+ reportDto.getHiddenCircleSelecIds() +")";
								sql3 = sql3 + " and a.ACCOUNT_LEVEL = " + accountType +" and a.CIRCLE_ID IN("+ reportDto.getHiddenCircleSelecIds() +")";
							} else {
								sql = sql + " and a.ACCOUNT_LEVEL = " + accountType;
								sql2 = sql2 + " and a.ACCOUNT_LEVEL = " + accountType;
								sql3 = sql3 + " and a.ACCOUNT_LEVEL = " + accountType;
							}
						}
						
						
							String orderBy = " ORDER BY CIRCLE_ID,ACCOUNT_ID,HIST_DATE ASC" ;
							//logger.info("Sql Final Neeti..:"+sqlFinal);
							//added by aman
							if(searchCriteria!="" || searchCriteria!=null)
							{
								 sqlFinal = sql + searchCriteria + sql2 + searchCriteria + sql3 + searchCriteria + orderBy;
							}
						
							else
							{
								 sqlFinal = sql + sql2 + sql3 + orderBy;
							}
							//logger.info("Sql Final Neeti..:"+sqlFinal);
							sql=sqlFinal;
							
							//end of changes by aman
					
							logger.info("Sql Final ::::::::::"+sql);
							ps = connection.prepareStatement(sql);			

							rsetReport = ps.executeQuery();
							AccountDetailReportDto reportReturnDto =null;
					
							
							while(rsetReport.next()){
								reportReturnDto =new AccountDetailReportDto();
								reportReturnDto.setDistLoginId(rsetReport.getString("LOGIN_ID"));
								reportReturnDto.setAccountType(rsetReport.getString("ACCOUNT_TYPE"));
								reportReturnDto.setAccountName(rsetReport.getString("CONTACT_NAME"));
								reportReturnDto.setLapuNo(rsetReport.getString("Lapu_No"));
								reportReturnDto.setLapuNo2(rsetReport.getString("LAPU_MOBILE_NO_2"));
								reportReturnDto.setFtaNo(rsetReport.getString("FTA_MOBILE_NO"));
								reportReturnDto.setFtaNo2(rsetReport.getString("FTA_MOBILE_NO_2"));
								reportReturnDto.setParentLoginId(rsetReport.getString("PARENT_LOGIN_ID"));
								reportReturnDto.setParentAccountType(rsetReport.getString("PARENT_ACCOUNT_TYPE"));	
								reportReturnDto.setParentAccountName(rsetReport.getString("PARENT_ACCOUNT_NAME"));				
								reportReturnDto.setCircle(rsetReport.getString("CIRCLE"));
								reportReturnDto.setCity(rsetReport.getString("CITY")); 
								reportReturnDto.setEmailId(rsetReport.getString("EMAIL"));
								
								reportReturnDto.setMobileNo(rsetReport.getString("Mobile"));
								
								reportReturnDto.setAddress(rsetReport.getString("ADDRESS"));
								reportReturnDto.setPin(rsetReport.getString("PIN"));
								reportReturnDto.setZone(rsetReport.getString("ZONE_NAME"));
								reportReturnDto.setBotreeCode(rsetReport.getString("BOTREE_CODE"));
								reportReturnDto.setOracleLocatorCode(rsetReport.getString("ORACLE_LOCATOR"));
								if (rsetReport.getString("STATUS").equalsIgnoreCase("A")){	reportReturnDto.setStatus("Active");
								} 
								else if (rsetReport.getString("STATUS").equalsIgnoreCase("I"))
								
								{ 
									reportReturnDto.setStatus("Inactive"); 
								}
								else  if (rsetReport.getString("STATUS").equalsIgnoreCase("L"))
								{ 
									reportReturnDto.setStatus("Inactive Due to Lapu Tree"); 
								}
								else  if (rsetReport.getString("STATUS").equalsIgnoreCase("c"))
								{ 
									reportReturnDto.setStatus("Close"); 
								}
								//reportReturnDto.setStatus(rsetReport.getString("STATUS"));
								reportReturnDto.setCreatedDate(Utility.getDateAsString(rsetReport.getDate("CREATED_DATE"), "dd-MMM-yyyy"));
								reportReturnDto.setCreatedBy(rsetReport.getString("CREATED_BY"));
								reportReturnDto.setLastUpdatedDate(Utility.getDateAsString(rsetReport.getDate("LAST_UPDATED_DATE"), "dd-MMM-yyyy"));
								reportReturnDto.setLastUpdatedBy(rsetReport.getString("LAST_UPDATED_BY"));
								reportReturnDto.setLastLoginDate((rsetReport.getString("LAST_LOGIN_DATE")));
								reportReturnDto.setWhName(rsetReport.getString("WH_NAME"));
								reportReturnDto.setPassChangeDT(rsetReport.getString("IPWD_CHANGED_DT"));
								reportReturnDto.setSrNo(rsetReport.getString("SR_NUMBER"));
								Date d=rsetReport.getDate("HIST_DATE");
								reportReturnDto.setHisDate(d.toString());
								reportReturnDto.setApproval1(rsetReport.getString("APPROVAL_1"));
								reportReturnDto.setApproval2(rsetReport.getString("APPROVAL_2"));
								reportReturnDto.setRemarks(rsetReport.getString("REMARKS"));
								reportReturnDto.setTransType(rsetReport.getString("TRANSACTION_TYPE"));
								
								listReturn.add(reportReturnDto);
							}
						}
				
				// =============== end of this block is for all account type =================
			}// for loop closed
			// end of non all coding here 
			
			
			

			
			logger.info("Account Detail Excel executed successfuly in AccountDetailReportDaoImpl");
			
		}catch (Exception e) 
		{
			e.printStackTrace();
			throw new DAOException(e.getMessage());
		}
		finally
		{
			DBConnectionManager.releaseResources(ps ,rsetReport );
			DBConnectionManager.releaseResources(connection);
		}
		
		return listReturn;
	}
	public List<SelectionCollection> getLoginIdList() throws DAOException{
		List<SelectionCollection> listReturn = new ArrayList<SelectionCollection>();
		PreparedStatement pstmt = null;
		ResultSet rsetReport = null;
		logger.info(" getLoginId dao Impl Starts.....!!");
		try
		{
			pstmt =  connection.prepareStatement(" SELECT LOGIN_ID, LOGIN_NAME FROM VR_LOGIN_MASTER WITH UR");
			SelectionCollection loginId =null;
			rsetReport= pstmt.executeQuery();
			while(rsetReport.next()){
				loginId =new SelectionCollection();
				loginId.setStrValue(rsetReport.getString("LOGIN_ID"));
				loginId.setStrText(rsetReport.getString("LOGIN_NAME"));
				listReturn.add(loginId);
			}
			logger.info("Login Id List executed successfuly in AccountDetailReportDaoImpl");
			
		}catch (Exception e) 
		{
			e.printStackTrace();
			throw new DAOException(e.getMessage());
		}
		finally
		{
			DBConnectionManager.releaseResources(pstmt ,rsetReport );
		}
		
		return listReturn;
	}
	public List<SelectionCollection> getAccountNameList() throws DAOException{
		List<SelectionCollection> listReturn = new ArrayList<SelectionCollection>();
		PreparedStatement pstmt = null;
		ResultSet rsetReport = null;
		logger.info(" Account Name Dao Impl Start......!");
		try
		{
			pstmt =  connection.prepareStatement("SELECT ACCOUNT_ID, ACCOUNT_NAME FROM VR_ACCOUNT_DETAILS WITH UR");
			SelectionCollection accountName =null;
			rsetReport= pstmt.executeQuery();
			while(rsetReport.next()){
				accountName =new SelectionCollection();
				accountName.setStrValue(rsetReport.getString("ACCOUNT_ID"));
				accountName.setStrText(rsetReport.getString("ACCOUNT_NAME"));
				listReturn.add(accountName);
			}
			logger.info("Account Name Dao Impl eNDS......!");
			
		}catch (Exception e) 
		{
			e.printStackTrace();
			throw new DAOException(e.getMessage());
		}
		finally
		{
			DBConnectionManager.releaseResources(pstmt ,rsetReport );
		}
		
		return listReturn;
	}
	
	public int getParentId(String accountId) throws DAOException{
	int parentId =0;
		PreparedStatement pstmt = null;
		ResultSet rsetReport = null;
		logger.info(" Account Name Dao Impl Start......!");
		try
		{
			pstmt =  connection.prepareStatement(GET_PARENT_ID);	
			pstmt.setString(1, accountId);
			rsetReport= pstmt.executeQuery();
			while(rsetReport.next()){
				parentId=rsetReport.getInt("PARENT_ACCOUNT");
			}
			logger.info("Account Name Dao Impl eNDS......!");
			
		}catch (Exception e) 
		{
			e.printStackTrace();
			throw new DAOException(e.getMessage());
		}
		finally
		{
			DBConnectionManager.releaseResources(pstmt ,rsetReport );
		}
		
		return parentId;
	}
}
