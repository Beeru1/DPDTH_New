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
import com.ibm.dp.dao.AccountManagementActivityReportDao;
import com.ibm.dp.dto.AccountDetailReportDto;
import com.ibm.dp.dto.AccountManagementActivityReportDto;
import com.ibm.dpmisreports.common.SelectionCollection;
import com.ibm.utilities.Utility;
import com.ibm.virtualization.recharge.dao.rdbms.BaseDaoRdbms;
import com.ibm.virtualization.recharge.db.DBConnectionManager;
import com.ibm.virtualization.recharge.exception.DAOException;

public class AccountManagementActivityReportDaoImpl  extends BaseDaoRdbms implements AccountManagementActivityReportDao{
	private Logger logger = Logger.getLogger(AccountManagementActivityReportDaoImpl.class.getName());
	public static final String SQL_PO_DETAIL_REPORT 	= DBQueries.SQL_PO_DETAIL_REPORT;
	public static final String SQL_PO_STATUS_LIST 	= DBQueries.SQL_PO_STATUS_LIST;
	public static final String GET_PARENT_ID 	= DBQueries.GET_PARENT_ACCOUNT_ID;
	
	public AccountManagementActivityReportDaoImpl(Connection connection) {
		super(connection);
	}
	
	public List<AccountManagementActivityReportDto> getAccountMngtActivityExcel(AccountManagementActivityReportDto reportDto) throws DAOException{
		List<AccountManagementActivityReportDto> listReturn = new ArrayList<AccountManagementActivityReportDto>();
		logger.info("==== in getAccountMngtActivityExcel() of AccountManagementActivityReportDaoImpl========= method");
		PreparedStatement ps = null;
		ResultSet rsetReport = null;
		
		
		try
		{
			String sql = "";
			
			System.out.println("List of cIrcles....in getAccountMngtActivityExcel() " + reportDto.getHiddenCircleSelecIds());

			connection = DBConnectionManager.getDBConnection();
			String statusStr = reportDto.getStatus();
			String accountType = reportDto.getAccountType();
			String fromDate=reportDto.getFromDate();
			String toDate=reportDto.getToDate();
			String circleIds=reportDto.getHiddenCircleSelecIds();
			String accountTypes=reportDto.getAccountType();
			String actions=reportDto.getAction();
			String searchOption=reportDto.getSearchOption();
			String searchCriterias=reportDto.getSearchCriteria();
			String showCircle=reportDto.getShowCircle();
			long accountId=reportDto.getAccountId();
			logger.info("fromDate :"+fromDate);
			logger.info("toDate :"+toDate);
			logger.info("circleIds :"+circleIds);
			logger.info("accountType :"+accountTypes);
			logger.info("actions :"+actions);
			logger.info("searchOption :"+searchOption);
			logger.info("searchCriterias :"+searchCriterias);
			logger.info("showCircle :"+showCircle);
			
			String query = "";
			String query1 = "";
			String query2 = "";
			String query3 = "";
			String status ="'A','I','L'"; //Neetika added L
			String searchCriteria = "";
			String accountTypesArr[]=accountType.split(",");
			String actionArr[];
			
			
			/*if(statusStr.equalsIgnoreCase("0")){
				status = "'A','I'";
			}
			if(statusStr.equalsIgnoreCase("1")){
				status = "'A'";
			}
			if(statusStr.equalsIgnoreCase("2")){
				status = "'I'";
			}*/
			String circleFilter="";
			String sql2="";
			String flag="";
			String qry="";
			
			for(int actionCount=0;actionCount<accountTypesArr.length;actionCount++)
			{
			accountType=accountTypesArr[actionCount];
			logger.info(" For account type :"+accountType);
			if(accountType != null)
			//if(accountType != null && (accountType.equalsIgnoreCase("1")))
			
					{
							
				logger.info("asa::1");

			
			if(accountType != null &&  accountType.equalsIgnoreCase("1") || accountType.equalsIgnoreCase("2") || accountType.equalsIgnoreCase("3") || accountType.equalsIgnoreCase("4") || accountType.equalsIgnoreCase("5")) 
			{
				logger.info("asa::2");
				sql = "SELECT b.LOGIN_NAME as Login_ID, a.ACCOUNT_LEVEL, " +
					"			(SELECT LEVEL_NAME FROM VR_ACCOUNT_LEVEL_MASTER where LEVEL_ID = a.ACCOUNT_LEVEL) as Account_Type," +
					"			a.ACCOUNT_NAME as Contact_Name," +
					"			a.LAPU_MOBILE_NO as Lapu_No," +
					"			a.LAPU_MOBILE_NO_2 as LAPU_MOBILE_NO_2,a.FTA_MOBILE_NO as FTA_MOBILE_NO,a.FTA_MOBILE_NO_2 as FTA_MOBILE_NO_2," +
					"			(SELECT LOGIN_NAME FROM VR_LOGIN_MASTER where b.LOGIN_ID = a.PARENT_ACCOUNT fetch first 1 row only) as Parent_Login_ID," +
					"			(SELECT LEVEL_NAME FROM VR_ACCOUNT_LEVEL_MASTER where LEVEL_ID = a.ACCOUNT_LEVEL -1) as Parent_Account_Type," +
					"			(SELECT ACCOUNT_NAME FROM VR_ACCOUNT_DETAILS where ACCOUNT_ID = a.PARENT_ACCOUNT) as Parent_Account_Name," +			
					"			(SELECT CIRCLE_NAME FROM VR_CIRCLE_MASTER where CIRCLE_ID = map1.CIRCLE_ID fetch first 1 row only ) as Circle," +
					"			(SELECT CITY_NAME FROM VR_CITY_MASTER where CITY_ID = a.CITY_ID) as City," +
					"			( select WH_NAME from DP_WH_MASTER where WH_CODE in(select wh_code from DPDTH.DP_WH_DIST_MAP where DISTID = a.ACCOUNT_ID) )  as WH_NAME,"+
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
					"			case when b.LAST_LOGIN_TIME is null then coalesce(to_char((b.IPWD_CHANGED_DT+45 DAYS),'dd/MM/yyyy'),'') else coalesce(to_char((b.IPWD_CHANGED_DT),'dd/MM/yyyy'),'') end as IPWD_CHANGED_DT ," +
					"			a.ACCOUNT_ID as ACC_ID ,a.APPROVAL_1 as APPROVAL1,a.APPROVAL_2 as APPROVAL2 ,a.SR_NUMBER as SR_NUMBER,a.REMARKS as REMARKS 	"+	
					"			from VR_LOGIN_MASTER b left outer join VR_LOGIN_MASTER_HIST LMH on b.LOGIN_ID = LMH.LOGIN_ID, VR_ACCOUNT_DETAILS a left outer join  DP_ACCOUNT_CIRCLE_MAP map1 on map1.account_id= a.account_id  " +
					"			where a.ACCOUNT_ID=b.LOGIN_ID  and b.STATUS IN( "+status+ ") ";

					// by pratap start
				if(actions!="")
				{
					logger.info("asa::3");
					actionArr=actions.split(",");
					logger.info("actionArr.length :"+actionArr.length+"fromDate"+fromDate);
					logger.info("asa::::::::::::::::::::::::::::::::::::::::::");
					if(fromDate!=null && !fromDate.equalsIgnoreCase("") && toDate!=null && !toDate.equalsIgnoreCase("") )
					{
						
						logger.info("asa00000000000000");
						if(actionArr.length == 1)
						{
							if(actionArr[0].equals("CREATE"))
							{
								sql=sql+" and date(a.CREATED_DT) between date('"+fromDate+"') and date('"+toDate+"')";
							}
							if(actionArr[0].equals("EDIT"))
							{
								sql=sql+" and date(a.UPDATED_DT) between date('"+fromDate+"') and date('"+toDate+"')";
							}
							if(actionArr[0].equals("INACTIVE"))
							{
								sql=sql+" and date(b.SUSPENSION_DT) between date('"+fromDate+"') and date('"+toDate+"')";
							}
						}
						else if(actionArr.length > 1)
						{
							logger.info("=== in else if block =====");
							qry=" and (";
							boolean qry2=false;
							String qry3="";
							for(int i=0;i<actionArr.length;i++)
							{
								logger.info("actionArr[i] :"+actionArr[i]);
								if(actionArr[i].equals("CREATE"))
								{
									if(qry2)qry3=qry3+" or";
									qry3=qry3+" date(a.CREATED_DT) between date('"+fromDate+"') and date('"+toDate+"') ";
									qry2=true;
								}
								if(actionArr[i].equals("EDIT"))
								{
									if(qry2)qry3=qry3+" or";
									qry3=qry3+"  date(a.UPDATED_DT) between date('"+fromDate+"') and date('"+toDate+"') ";
									qry2=true;
								}
								if(actionArr[i].equals("INACTIVE"))
								{
									if(qry2)qry3=qry3+" or";
									qry3=qry3+" date(b.SUSPENSION_DT) between date('"+fromDate+"') and date('"+toDate+"') ";
								}
								
							}
							qry=qry+qry3+") ";
						}
						else
						{
							sql=sql+" and (date(a.CREATED_DT) between date('"+fromDate+"') and date('"+toDate+"')";
							sql=sql+" or date(a.UPDATED_DT) between date('"+fromDate+"') and date('"+toDate+"')";
							sql=sql+" or date(b.SUSPENSION_DT) between date('"+fromDate+"') and date('"+toDate+"'))";
						}
					}
					sql=sql+qry;
				}
					// by pratap end 
					
					circleFilter = "map1";
					flag = "true";
			}
			else if(accountType != null && accountType.equalsIgnoreCase("6") || accountType.equalsIgnoreCase("7") || accountType.equalsIgnoreCase("8"))
			{
				logger.info("asa::4");
				
				sql = "SELECT b.LOGIN_NAME as Login_ID, a.ACCOUNT_LEVEL, " +
				"			(SELECT LEVEL_NAME FROM VR_ACCOUNT_LEVEL_MASTER where LEVEL_ID = a.ACCOUNT_LEVEL) as Account_Type," +
				"			a.ACCOUNT_NAME as Contact_Name," +
				"			a.LAPU_MOBILE_NO as Lapu_No," +
				"			a.LAPU_MOBILE_NO_2 as LAPU_MOBILE_NO_2,a.FTA_MOBILE_NO as FTA_MOBILE_NO,a.FTA_MOBILE_NO_2 as FTA_MOBILE_NO_2," +
				"			(SELECT LOGIN_NAME FROM VR_LOGIN_MASTER where b.LOGIN_ID = a.PARENT_ACCOUNT) as Parent_Login_ID," +
				"			(SELECT LEVEL_NAME FROM VR_ACCOUNT_LEVEL_MASTER where LEVEL_ID = a.ACCOUNT_LEVEL -1) as Parent_Account_Type," +
				"			(SELECT ACCOUNT_NAME FROM VR_ACCOUNT_DETAILS where ACCOUNT_ID = a.PARENT_ACCOUNT) as Parent_Account_Name," +			
				"			(SELECT CIRCLE_NAME FROM VR_CIRCLE_MASTER where CIRCLE_ID = a.CIRCLE_ID) as Circle," +
				"			(SELECT CITY_NAME FROM VR_CITY_MASTER where CITY_ID = a.CITY_ID) as City," +
				"			( select WH_NAME from DP_WH_MASTER where WH_CODE in(select wh_code from DPDTH.DP_WH_DIST_MAP where DISTID = a.ACCOUNT_ID) )  as WH_NAME,"+
				"			a.EMAIL as Email," +
				"			case when a.Account_level=8 then a.RETAILER_LAPU else a.MOBILE_NUMBER end as Mobile," +
				"			a.ACCOUNT_ADDRESS as Address," +
				"			a.PIN_NUMBER as Pin," +
				"			(SELECT ZONE_NAME FROM DP_ZONE_MASTER where ZONE_ID = a.ZONE_ID) as Zone_Name," +
				"			(SELECT EXT_DIST_ID FROM DP_DISTRIBUTOR_MAPPING where DP_DIST_ID = a.ACCOUNT_ID FETCH FIRST 1 row only) as BoTree_Code," +
				"			a.DISTRIBUTOR_LOCATOR as Oracle_Locator," +
				"			b.STATUS as Status," +
				"			DATE(a.CREATED_DT) as Created_Date," +
				"			(SELECT ACCOUNT_NAME FROM VR_ACCOUNT_DETAILS where ACCOUNT_ID = a.CREATED_BY) as Created_By," +
				"			DATE(a.UPDATED_DT) as Last_Updated_Date," +
				"			(SELECT ACCOUNT_NAME FROM VR_ACCOUNT_DETAILS where ACCOUNT_ID = a.UPDATED_BY ) as Last_Updated_By, " +
				"			coalesce(to_char(b.LAST_LOGIN_TIME,'dd/MM/yyyy'),'') as Last_Login_date, " +
				"			case when b.LAST_LOGIN_TIME is null then coalesce(to_char((b.IPWD_CHANGED_DT+45 DAYS),'dd/MM/yyyy'),'') else coalesce(to_char((b.IPWD_CHANGED_DT),'dd/MM/yyyy'),'') end as IPWD_CHANGED_DT ," +
				"			a.ACCOUNT_ID as ACC_ID	,a.APPROVAL_1 as APPROVAL1,a.APPROVAL_2 as APPROVAL2 ,a.SR_NUMBER as SR_NUMBER,a.REMARKS as REMARKS "+	
				"			from VR_LOGIN_MASTER b left outer join VR_LOGIN_MASTER_HIST LMH on b.LOGIN_ID = LMH.LOGIN_ID," +
				"			VR_ACCOUNT_DETAILS a left outer join VR_ACCOUNT_DETAILS_HIST ADH	 on a.ACCOUNT_ID= ADH.ACCOUNT_ID " +
				"			where a.ACCOUNT_ID=b.LOGIN_ID  and b.STATUS IN( "+status+ ") ";  //and date(lmh.HIST_DATE)=date(adh.HIST_DATE) chck this
				// by pratap start
				if(actions!="")
				{
					if(fromDate!=null && !fromDate.equalsIgnoreCase("") && toDate!=null && !toDate.equalsIgnoreCase("") )
					{
						logger.info("asa11111111");
						actionArr=actions.split(",");
						if(actionArr.length == 1)
						{
							if(actionArr[0].equals("CREATE"))
							{
								sql=sql+" and date(a.CREATED_DT) between date('"+fromDate+"') and date('"+toDate+"')";
							}
							if(actionArr[0].equals("EDIT"))
							{
								sql=sql+" and date(a.UPDATED_DT) between date('"+fromDate+"') and date('"+toDate+"')";
							}
							if(actionArr[0].equals("INACTIVE"))
							{
								sql=sql+" and date(b.SUSPENSION_DT) between date('"+fromDate+"') and date('"+toDate+"')";
							}
						}
						else if(actionArr.length > 1)
						{
							qry=" and (";
							boolean qry2=false;
							String qry3="";
							for(int i=0;i<actionArr.length;i++)
							{
								if(actionArr[i].equals("CREATE"))
								{
									if(qry2)qry3=qry3+" or";
									qry3=qry3+" date(a.CREATED_DT) between date('"+fromDate+"') and date('"+toDate+"') ";
									qry2=true;
								}
								if(actionArr[i].equals("EDIT"))
								{
									if(qry2)qry3=qry3+" or";
									qry3=qry3+"  date(a.UPDATED_DT) between date('"+fromDate+"') and date('"+toDate+"') ";
									qry2=true;
								}
								if(actionArr[i].equals("INACTIVE"))
								{
									if(qry2)qry3=qry3+" or";
									qry3=qry3+" date(b.SUSPENSION_DT) between date('"+fromDate+"') and date('"+toDate+"') ";
								}
								
							}
							qry=qry+qry3+") ";
						}
						else
						{
							sql=sql+" and ( date(a.CREATED_DT) between date('"+fromDate+"') and date('"+toDate+"')";
							sql=sql+" or date(a.UPDATED_DT) between date('"+fromDate+"') and date('"+toDate+"')";
							sql=sql+" or date(b.SUSPENSION_DT) between date('"+fromDate+"') and date('"+toDate+"'))";
						
						}
					}
					sql=sql+qry;
				}
					// by pratap end 
				
				circleFilter = "a";
				flag = "true";
			}
			
			if(flag != null && flag.equalsIgnoreCase("true"))  {
			
			if(reportDto.getSearchOption().equalsIgnoreCase("-1")) {
				
			}
			else if(reportDto.getSearchOption().equalsIgnoreCase("USERNAME")) {
				logger.info("username :11111111");
				searchCriteria = " AND b.LOGIN_ID = (SELECT LOGIN_ID FROM VR_LOGIN_MASTER WHERE UPPER(LOGIN_NAME) ='" +reportDto.getSearchCriteria().toUpperCase() +"')";
			}
			else if(reportDto.getSearchOption().equalsIgnoreCase("SRNO")) {
				searchCriteria = " AND a.ACCOUNT_ID = (SELECT ACCOUNT_ID FROM VR_ACCOUNT_DETAILS WHERE UPPER(SR_NUMBER) ='" +reportDto.getSearchCriteria().toUpperCase() +"')";
			}
			else if(reportDto.getSearchOption().equalsIgnoreCase("APPROVER")) {
				searchCriteria = " AND a.ACCOUNT_ID = (SELECT ACCOUNT_ID FROM VR_ACCOUNT_DETAILS WHERE UPPER(APPROVAL_1) ='" +reportDto.getSearchCriteria().toUpperCase() +"' fetch first 1 row only)";
			}
			/*else if(reportDto.getSearchOption().equalsIgnoreCase("")) {
				searchCriteria = " AND MOBILE_NUMBER = '"+reportDto.getSearchCriteria()+"'";
			}
			else if(reportDto.getSearchOption().equalsIgnoreCase("EMAIL_ID")) {
				searchCriteria = " AND UPPER(EMAIL) = '" +reportDto.getSearchCriteria().toUpperCase()+"'";
			}
			else if(reportDto.getSearchOption().equalsIgnoreCase("PIN_CODE")) {
				searchCriteria = " AND PIN_NUMBER = "+ reportDto.getSearchCriteria();
			}
			else if(reportDto.getSearchOption().equalsIgnoreCase("CITY")) {
				searchCriteria = " AND CITY_ID = (SELECT CITY_ID FROM VR_CITY_MASTER WHERE UPPER(CITY_NAME) = '" +reportDto.getSearchCriteria().toUpperCase()+"')";
			}*/

			
			
			logger.info("reportDto.getAccountLevel():::::"+reportDto.getAccountLevel());
			logger.info("accountType:::::"+accountType);
			logger.info("reportDto.getHiddenCircleSelecIds():::::"+reportDto.getHiddenCircleSelecIds());
			
			if(reportDto.getAccountLevel() == 5) {
				if(accountType.equals("-1")) {
					query1 = sql + " AND a.PARENT_ACCOUNT IN ("+reportDto.getAccountId()+")";
					query1 += searchCriteria.toString();
		
					query2 = 	" UNION ALL " + sql + " AND a.PARENT_ACCOUNT IN (SELECT ACCOUNT_ID FROM VR_ACCOUNT_DETAILS where PARENT_ACCOUNT IN ("+reportDto.getAccountId()+"))";;
					query2 += searchCriteria.toString();
					
					query3 =	" UNION ALL " + sql + " AND a.PARENT_ACCOUNT IN (SELECT ACCOUNT_ID FROM VR_ACCOUNT_DETAILS where PARENT_ACCOUNT IN (SELECT ACCOUNT_ID FROM VR_ACCOUNT_DETAILS where PARENT_ACCOUNT IN ("+reportDto.getAccountId()+")))";;
					query3 += searchCriteria.toString();
					
					sql = query1 + query2 + query3;
					
				} else if(accountType.equals("6")) {
					sql +=" AND a.PARENT_ACCOUNT IN ("+reportDto.getAccountId()+")";
				} else if (accountType.equals("7")) {
					sql +=" AND a.PARENT_ACCOUNT IN (SELECT ACCOUNT_ID FROM VR_ACCOUNT_DETAILS where PARENT_ACCOUNT IN ("+reportDto.getAccountId()+"))";
				} else if (accountType.equals("8")) {
					sql +=" AND a.PARENT_ACCOUNT IN (SELECT ACCOUNT_ID FROM VR_ACCOUNT_DETAILS where PARENT_ACCOUNT IN (SELECT ACCOUNT_ID FROM VR_ACCOUNT_DETAILS where PARENT_ACCOUNT IN ("+reportDto.getAccountId()+")))";
				}
			} else if (reportDto.getAccountLevel() == 6) {

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
			System.out.println(reportDto.getAccountLevel() );
			if(accountType.equals("-1") ){// For all account Type
				
				logger.info("asa::5");
					sql = "SELECT b.LOGIN_NAME as Login_ID, a.ACCOUNT_LEVEL, " +
					"			(SELECT LEVEL_NAME FROM VR_ACCOUNT_LEVEL_MASTER where LEVEL_ID = a.ACCOUNT_LEVEL) as Account_Type," +
					"			a.ACCOUNT_NAME as Contact_Name," +
					"			a.LAPU_MOBILE_NO as Lapu_No," +
					"			a.LAPU_MOBILE_NO_2 as LAPU_MOBILE_NO_2,a.FTA_MOBILE_NO as FTA_MOBILE_NO,a.FTA_MOBILE_NO_2 as FTA_MOBILE_NO_2," +
					"			(SELECT LOGIN_NAME FROM VR_LOGIN_MASTER where b.LOGIN_ID = a.PARENT_ACCOUNT) as Parent_Login_ID," +
					"			(SELECT LEVEL_NAME FROM VR_ACCOUNT_LEVEL_MASTER where LEVEL_ID = a.ACCOUNT_LEVEL -1) as Parent_Account_Type," +
					"			(SELECT ACCOUNT_NAME FROM VR_ACCOUNT_DETAILS where ACCOUNT_ID = a.PARENT_ACCOUNT) as Parent_Account_Name," +			
					"			(SELECT CIRCLE_NAME FROM VR_CIRCLE_MASTER where CIRCLE_ID = map1.CIRCLE_ID fetch first 1 row only ) as Circle," +
					"			(SELECT CITY_NAME FROM VR_CITY_MASTER where CITY_ID = a.CITY_ID) as City," +
					"			( select WH_NAME from DP_WH_MASTER where WH_CODE in(select wh_code from DPDTH.DP_WH_DIST_MAP where DISTID = a.ACCOUNT_ID) )  as WH_NAME,"+
					"			a.EMAIL as Email," +
					"			case when a.Account_level=8 then a.RETAILER_LAPU else a.MOBILE_NUMBER end as Mobile," +
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
					"			case when b.LAST_LOGIN_TIME is null then coalesce(to_char((b.IPWD_CHANGED_DT+45 DAYS),'dd/MM/yyyy'),'') else coalesce(to_char((b.IPWD_CHANGED_DT),'dd/MM/yyyy'),'') end as IPWD_CHANGED_DT ," +
					"			a.ACCOUNT_ID as ACC_ID  ,a.APPROVAL_1 as APPROVAL1,a.APPROVAL_2 as APPROVAL2,a.SR_NUMBER as SR_NUMBER,a.REMARKS as REMARKS "+	
					"			from VR_LOGIN_MASTER b left outer join VR_LOGIN_MASTER_HIST LMH on b.LOGIN_ID = LMH.LOGIN_ID," +
					"			VR_ACCOUNT_DETAILS a left outer join  DP_ACCOUNT_CIRCLE_MAP map1 on map1.account_id= a.account_id  " +
					"			where a.ACCOUNT_ID=b.LOGIN_ID and b.STATUS IN( "+status+ ") " ;
					// by pratap start
					if(actions!="")
					{
						if(fromDate!=null && !fromDate.equalsIgnoreCase("") && toDate!=null && !toDate.equalsIgnoreCase("") )
						{
							
							actionArr=actions.split(",");
							if(actionArr.length == 1)
							{
								if(actionArr[0].equals("CREATE"))
								{
									sql=sql+" and date(a.CREATED_DT) between date('"+fromDate+"') and date('"+toDate+"')";
								}
								if(actionArr[0].equals("EDIT"))
								{
									sql=sql+" and date(a.UPDATED_DT) between date('"+fromDate+"') and date('"+toDate+"')";
								}
								if(actionArr[0].equals("INACTIVE"))
								{
									sql=sql+" and date(b.SUSPENSION_DT) between date('"+fromDate+"') and date('"+toDate+"')";
								}
							}
							else if(actionArr.length > 1)
							{
								qry=" and (";
								boolean qry2=false;
								String qry3="";
								for(int i=0;i<actionArr.length;i++)
								{
									if(actionArr[i].equals("CREATE"))
									{
										if(qry2)qry3=qry3+" or";
										qry3=qry3+" date(a.CREATED_DT) between date('"+fromDate+"') and date('"+toDate+"') ";
										qry2=true;
									}
									if(actionArr[i].equals("EDIT"))
									{
										if(qry2)qry3=qry3+" or";
										qry3=qry3+"  date(a.UPDATED_DT) between date('"+fromDate+"') and date('"+toDate+"') ";
										qry2=true;
									}
									if(actionArr[i].equals("INACTIVE"))
									{
										if(qry2)qry3=qry3+" or";
										qry3=qry3+" date(b.SUSPENSION_DT) between date('"+fromDate+"') and date('"+toDate+"') ";
									}
									
								}
								qry=qry+qry3+") ";
							}
							else
							{
								sql=sql+" and ( date(a.CREATED_DT) between date('"+fromDate+"') and date('"+toDate+"')";
								sql=sql+" or date(a.UPDATED_DT) between date('"+fromDate+"') and date('"+toDate+"')";
								sql=sql+" or date(b.SUSPENSION_DT) between date('"+fromDate+"') and date('"+toDate+"'))";
							
							}
						}
						sql=sql+qry;
					}
						// by pratap end 
					
					sql2 = " 				UNION " +
					" 		SELECT b.LOGIN_NAME as Login_ID, a.ACCOUNT_LEVEL, " +
					"			(SELECT LEVEL_NAME FROM VR_ACCOUNT_LEVEL_MASTER where LEVEL_ID = a.ACCOUNT_LEVEL) as Account_Type," +
					"			a.ACCOUNT_NAME as Contact_Name," +
					"			a.LAPU_MOBILE_NO as Lapu_No," +
					"			a.LAPU_MOBILE_NO_2 as LAPU_MOBILE_NO_2,a.FTA_MOBILE_NO as FTA_MOBILE_NO,a.FTA_MOBILE_NO_2 as FTA_MOBILE_NO_2," +
					"			(SELECT LOGIN_NAME FROM VR_LOGIN_MASTER where b.LOGIN_ID = a.PARENT_ACCOUNT) as Parent_Login_ID," +
					"			(SELECT LEVEL_NAME FROM VR_ACCOUNT_LEVEL_MASTER where LEVEL_ID = a.ACCOUNT_LEVEL -1) as Parent_Account_Type," +
					"			(SELECT ACCOUNT_NAME FROM VR_ACCOUNT_DETAILS where ACCOUNT_ID = a.PARENT_ACCOUNT) as Parent_Account_Name," +			
					"			(SELECT CIRCLE_NAME FROM VR_CIRCLE_MASTER where CIRCLE_ID = a.CIRCLE_ID) as Circle," +
					"			(SELECT CITY_NAME FROM VR_CITY_MASTER where CITY_ID = a.CITY_ID) as City," +
					"			( select WH_NAME from DP_WH_MASTER where WH_CODE in(select wh_code from DPDTH.DP_WH_DIST_MAP where DISTID = a.ACCOUNT_ID) )  as WH_NAME,"+
					"			a.EMAIL as Email," +
					"			case when a.Account_level=8 then a.RETAILER_LAPU else a.MOBILE_NUMBER end as Mobile," +
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
					"			case when b.LAST_LOGIN_TIME is null then coalesce(to_char((b.IPWD_CHANGED_DT+45 DAYS),'dd/MM/yyyy'),'') else coalesce(to_char((b.IPWD_CHANGED_DT),'dd/MM/yyyy'),'') end as IPWD_CHANGED_DT ," +
					"			a.ACCOUNT_ID as ACC_ID ,a.APPROVAL_1 as APPROVAL1,a.APPROVAL_2 as APPROVAL2 ,a.SR_NUMBER as SR_NUMBER,a.REMARKS as REMARKS"+	
					"			from VR_LOGIN_MASTER b left outer join VR_LOGIN_MASTER_HIST LMH on b.LOGIN_ID = LMH.LOGIN_ID," +
					"			VR_ACCOUNT_DETAILS a left outer join VR_ACCOUNT_DETAILS_HIST ADH	on a.ACCOUNT_ID= ADH.ACCOUNT_ID " +
					"			where a.ACCOUNT_ID=b.LOGIN_ID and b.STATUS IN( "+status+ ") ";
					// by pratap start
					if(actions!="")
					{
						if(fromDate!=null && !fromDate.equalsIgnoreCase("") && toDate!=null && !toDate.equalsIgnoreCase("") )
						{
							actionArr=actions.split(",");
							if(actionArr.length == 1)
							{
								if(actionArr[0].equals("CREATE"))
								{
									sql2=sql2+" and date(a.CREATED_DT) between date('"+fromDate+"') and date('"+toDate+"')";
								}
								if(actionArr[0].equals("EDIT"))
								{
									sql2=sql2+" and date(a.UPDATED_DT) between date('"+fromDate+"') and date('"+toDate+"')";
								}
								if(actionArr[0].equals("INACTIVE"))
								{
									sql2=sql2+" and date(b.SUSPENSION_DT) between date('"+fromDate+"') and date('"+toDate+"')";
								}
							}
							else if(actionArr.length > 1)
							{
								qry=" and (";
								boolean qry2=false;
								String qry3="";
								for(int i=0;i<actionArr.length;i++)
								{
									if(actionArr[i].equals("CREATE"))
									{
										if(qry2)qry3=qry3+" or";
										qry3=qry3+" date(a.CREATED_DT) between date('"+fromDate+"') and date('"+toDate+"') ";
										qry2=true;
									}
									if(actionArr[i].equals("EDIT"))
									{
										if(qry2)qry3=qry3+" or";
										qry3=qry3+"  date(a.UPDATED_DT) between date('"+fromDate+"') and date('"+toDate+"') ";
										qry2=true;
									}
									if(actionArr[i].equals("INACTIVE"))
									{
										if(qry2)qry3=qry3+" or";
										qry3=qry3+" date(b.SUSPENSION_DT) between date('"+fromDate+"') and date('"+toDate+"') ";
									}
									
								}
								qry=qry+qry3+") ";
							}
							else
							{
								sql=sql+" and ( date(a.CREATED_DT) between date('"+fromDate+"') and date('"+toDate+"')";
								sql=sql+" or date(a.UPDATED_DT) between date('"+fromDate+"') and date('"+toDate+"')";
								sql=sql+" or date(b.SUSPENSION_DT) between date('"+fromDate+"') and date('"+toDate+"'))";
							
							}
						}
						sql=sql+qry;
					}
						// by pratap end 
					
					if(reportDto.getSearchOption().equalsIgnoreCase("-1")) {
						
					}
					
					else if(reportDto.getSearchOption().equalsIgnoreCase("USERNAME")) {
						logger.info("username :22222222222");
						searchCriteria = " AND b.LOGIN_ID = (SELECT LOGIN_ID FROM VR_LOGIN_MASTER WHERE UPPER(LOGIN_NAME) ='" +reportDto.getSearchCriteria().toUpperCase() +"')";
					}
					else if(reportDto.getSearchOption().equalsIgnoreCase("SRNO")) {
						searchCriteria = " AND a.ACCOUNT_ID = (SELECT ACCOUNT_ID FROM VR_ACCOUNT_DETAILS WHERE UPPER(SR_NUMBER) ='" +reportDto.getSearchCriteria().toUpperCase() +"')";
					}
					else if(reportDto.getSearchOption().equalsIgnoreCase("APPROVER")) {
						searchCriteria = " AND a.ACCOUNT_ID = (SELECT ACCOUNT_ID FROM VR_ACCOUNT_DETAILS WHERE UPPER(APPROVAL_1) ='" +reportDto.getSearchCriteria().toUpperCase() +"' fetch first 1 row only) ";
					}
					
					
					
					
					
					
					/*else if(reportDto.getSearchOption().equalsIgnoreCase("LOGIN_ID")) {
						searchCriteria = " AND LOGIN_ID = (SELECT LOGIN_ID FROM VR_LOGIN_MASTER WHERE UPPER(LOGIN_NAME) ='" +reportDto.getSearchCriteria().toUpperCase() +"')";
					}
					else if(reportDto.getSearchOption().equalsIgnoreCase("ACCOUNT_NAME")) {
						searchCriteria = " AND ACCOUNT_ID = (SELECT ACCOUNT_ID FROM VR_ACCOUNT_DETAILS WHERE UPPER(ACCOUNT_NAME) ='" +reportDto.getSearchCriteria().toUpperCase() +"')";
					}
					else if(reportDto.getSearchOption().equalsIgnoreCase("MOBILE_NO")) {
						searchCriteria = " AND MOBILE_NUMBER = '"+reportDto.getSearchCriteria()+"'";
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

*/					
					
					logger.info("reportDto.getAccountLevel():::::"+reportDto.getAccountLevel());
					logger.info("accountType:::::"+accountType);
					logger.info("reportDto.getHiddenCircleSelecIds():::::"+reportDto.getHiddenCircleSelecIds());
					
				  if(accountType.equals("-1") && reportDto.getAccountLevel() == 0 ){
						if(!reportDto.getHiddenCircleSelecIds().equals("0")) {
							sql = sql + " and a.ACCOUNT_LEVEL > " + reportDto.getAccountLevel() +" and map1.CIRCLE_ID IN(0,"+ reportDto.getHiddenCircleSelecIds() +")";
							sql2 = sql2 + " and a.ACCOUNT_LEVEL > " + reportDto.getAccountLevel() +" and a.CIRCLE_ID IN(0,"+ reportDto.getHiddenCircleSelecIds() +")";
							
						} else {
							sql = sql + " and a.ACCOUNT_LEVEL > " + reportDto.getAccountLevel();
							sql2 = sql2 + " and a.ACCOUNT_LEVEL > " + reportDto.getAccountLevel();
						}
					} else if (accountType.equals("1")) {
						sql = sql + " and a.ACCOUNT_LEVEL = " + accountType;
						sql2 = sql2 + " and a.ACCOUNT_LEVEL = " + accountType;
					} else if (accountType.equals("-1")) {	
						if(!reportDto.getHiddenCircleSelecIds().equals("0")) {
							sql = sql + " and a.ACCOUNT_LEVEL > " + reportDto.getAccountLevel() +" and map1.CIRCLE_ID IN("+ reportDto.getHiddenCircleSelecIds() +")";
							sql2 = sql2 + " and a.ACCOUNT_LEVEL > " + reportDto.getAccountLevel() +" and a.CIRCLE_ID IN("+ reportDto.getHiddenCircleSelecIds() +")";
						} else {
							sql = sql + " and a.ACCOUNT_LEVEL > " + reportDto.getAccountLevel();
							sql2 = sql2 + " and a.ACCOUNT_LEVEL > " + reportDto.getAccountLevel();
						}
					} else {
						if(!reportDto.getHiddenCircleSelecIds().equals("0")) {
							sql = sql + " and a.ACCOUNT_LEVEL = " + accountType +" and map1.CIRCLE_ID IN("+ reportDto.getHiddenCircleSelecIds() +")";
							sql2 = sql2 + " and a.ACCOUNT_LEVEL = " + accountType +" and a.CIRCLE_ID IN("+ reportDto.getHiddenCircleSelecIds() +")";
						} else {
							sql = sql + " and a.ACCOUNT_LEVEL = " + accountType;
							sql2 = sql2 + " and a.ACCOUNT_LEVEL = " + accountType;
						}
					}
					
					
						String orderBy = " ORDER BY CIRCLE, ACCOUNT_LEVEL ASC" ;
					
						orderBy="";
					
						String sqlFinal = sql + searchCriteria + sql2 + searchCriteria +  orderBy;
						System.out.println("Sql Final :"+sqlFinal);
						sql=sqlFinal;
						logger.info("asa:::final::::::"+sqlFinal);
				
			}
			
			

			System.out.println(sql);
			logger.info("asa:::sql:::"+sql);
			ps = connection.prepareStatement(sql);			

			rsetReport = ps.executeQuery();
			AccountManagementActivityReportDto reportReturnDto =null;
	
			
			while(rsetReport.next()){
				reportReturnDto =new AccountManagementActivityReportDto();
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
				//reportReturnDto.setStatus(rsetReport.getString("STATUS"));
				reportReturnDto.setCreatedDate(Utility.getDateAsString(rsetReport.getDate("CREATED_DATE"), "dd-MMM-yyyy"));
				reportReturnDto.setCreatedBy(rsetReport.getString("CREATED_BY"));
				reportReturnDto.setLastUpdatedDate(Utility.getDateAsString(rsetReport.getDate("LAST_UPDATED_DATE"), "dd-MMM-yyyy"));
				reportReturnDto.setLastUpdatedBy(rsetReport.getString("LAST_UPDATED_BY"));
				reportReturnDto.setLastLoginDate((rsetReport.getString("LAST_LOGIN_DATE")));
				reportReturnDto.setWhName(rsetReport.getString("WH_NAME"));
				reportReturnDto.setPassChangeDT(rsetReport.getString("IPWD_CHANGED_DT"));
				// added by pratap
					reportReturnDto.setApproval1(rsetReport
							.getString("APPROVAL1"));
					reportReturnDto.setApproval2(rsetReport
							.getString("APPROVAL2"));
					reportReturnDto.setSrNo(rsetReport
							.getString("SR_NUMBER"));
					reportReturnDto.setRemarks(rsetReport
							.getString("REMARKS"));
				// end adding by pratap	
				listReturn.add(reportReturnDto);
			}
			logger.info("Account Detail Excel executed successfuly in AccountDetailReportDaoImpl");
		}
			}
		}
			
		catch (Exception e) 
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
		logger.info("==== in getLoginIdList() of AccountManagementActivityReportDaoImpl========= method");
		try
		{	logger.info("asa::::getLoginIdList method");
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
		logger.info("==== in getAccountNameList() of AccountManagementActivityReportDaoImpl========= method");
		try
		{
			logger.info("asa::::getAccountNameList method");
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
		logger.info("==== in getParentId() of AccountManagementActivityReportDaoImpl========= method");
		try
		{	logger.info("asa:::getParentId method");
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
