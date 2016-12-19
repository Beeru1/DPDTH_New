package com.ibm.dp.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

import com.ibm.dp.common.DBQueries;
import com.ibm.dp.dao.DayWiseActivationSummarylReportDao;
import com.ibm.dp.dto.CircleActivationSummaryReportDTO;
import com.ibm.dp.dto.DayWiseActivationSummaryReportDTO;
import com.ibm.reports.dto.GenericReportsOutputDto;
import com.ibm.utilities.Utility;
import com.ibm.virtualization.recharge.dao.rdbms.BaseDaoRdbms;
import com.ibm.virtualization.recharge.db.DBConnectionManager;
import com.ibm.virtualization.recharge.exception.DAOException;

public class DayWiseActivationSummaryReportDaoImpl  extends BaseDaoRdbms implements DayWiseActivationSummarylReportDao{
	private Logger logger = Logger.getLogger(DayWiseActivationSummaryReportDaoImpl.class.getName());
	public static final String SQL_PO_DETAIL_REPORT 	= DBQueries.SQL_PO_DETAIL_REPORT;
	public static final String SQL_PO_STATUS_LIST 	= DBQueries.SQL_PO_STATUS_LIST;
	public static final String GET_PARENT_ID 	= DBQueries.GET_PARENT_ACCOUNT_ID;
	
	public DayWiseActivationSummaryReportDaoImpl(Connection connection) {
		super(connection);
	}
	
	
	public GenericReportsOutputDto getDayWiseActivationSummarylReportDao(DayWiseActivationSummaryReportDTO reportDto) throws DAOException{
		
		
		
		List<CircleActivationSummaryReportDTO> listReturn = new ArrayList<CircleActivationSummaryReportDTO>();
		
		List<CircleActivationSummaryReportDTO> arrReturn = new ArrayList<CircleActivationSummaryReportDTO>();
		
		CircleActivationSummaryReportDTO circleReportDto=new CircleActivationSummaryReportDTO();
		PreparedStatement pstmt = null;
		ResultSet rset = null;
		logger.info("Circle Activation Detail Report Start......................!!!");
		GenericReportsOutputDto genericReportsOutputDto = null;
		
		try
		{
			
			
			pstmt = connection.prepareStatement("select VALUE as PROD_TYPE from DP_CONFIGURATION_DETAILS where  (CONFIG_ID=8 and ID<10) with ur");
			rset = pstmt.executeQuery();
			
			List<String> listProductTypes = new ArrayList<String>();
			while(rset.next())
			{
				listProductTypes.add(rset.getString("PROD_TYPE"));
			}
			
			DBConnectionManager.releaseResources(pstmt, rset);
			
			StringBuffer sbPrefix = new StringBuffer("select CIR.CIRCLE_NAME,CIR.DATE as ASSIGN_DATE, ");
			String strFrom = " from DP_CIRCLE_DATE_MAP CIR left outer join ";
			StringBuffer sbHeader = new StringBuffer("");
			StringBuffer total = new StringBuffer(",("); //Neetika adding column Total for BFR 36 Release 3. The column was not shown in report 
	/*	String strInnerQuery = new String("( select cm.CIRCLE_ID, cm.CIRCLE_NAME, coalesce (normal_cnt.cnt, 0) cnt ,'HC_PROD_CAT' as PRODUCT_CATEGORY,date(ASSIGN_DATE) as ASSIGN_DATE from VR_CIRCLE_MASTER cm left outer join (" +
										" select count(*) as cnt,c.PRODUCT_CATEGORY, d.CIRCLE_ID, d.CIRCLE_NAME ,DATE(ASSIGN_DATE) as ASSIGN_DATE from DP_STOCK_INVENTORY_ASSIGNED a,VR_ACCOUNT_DETAILS b,DP_PRODUCT_MASTER c,VR_CIRCLE_MASTER d" +
										" where a.DISTRIBUTOR_ID=b.ACCOUNT_ID AND a.PRODUCT_ID=c.PRODUCT_ID AND b.CIRCLE_ID=d.CIRCLE_ID AND c.PRODUCT_CATEGORY='HC_PROD_CAT' " +
										" AND date(a.ASSIGN_DATE) >= ? and  date(a.ASSIGN_DATE) <= ? group by PRODUCT_CATEGORY,d.CIRCLE_ID,d.CIRCLE_NAME ,DATE(ASSIGN_DATE) " +
										" ) normal_cnt on cm.CIRCLE_ID=normal_cnt.CIRCLE_ID ) my_alias on CIR.CIRCLE_ID=my_alias.CIRCLE_ID ");
	*/
			
			String strInnerQuery = new String("( select cm.CIRCLE_ID, cm.CIRCLE_NAME, coalesce (normal_cnt.cnt, 0) cnt ,'HC_PROD_CAT' as PRODUCT_CATEGORY,cm.date as ASSIGN_DATE " 
											+" from DP_CIRCLE_DATE_MAP cm  left outer join (select count(*) as cnt,c.PRODUCT_CATEGORY, d.CIRCLE_ID, d.CIRCLE_NAME ,DATE(ASSIGN_DATE) as ASSIGN_DATE " 
											+" from DP_STOCK_INVENTORY_INACTIVE a, DP_DIST_DETAILS b,DP_PRODUCT_MASTER c,DPMQT.VR_CIRCLE_MASTER_MQT d "
											+" where a.DISTRIBUTOR_ID=b.DISTRIBUTOR_ID AND a.PRODUCT_ID=c.PRODUCT_ID AND b.CIRCLE_ID=d.CIRCLE_ID AND c.PRODUCT_CATEGORY='HC_PROD_CAT' " 
											+" AND date(a.ASSIGN_DATE) >= ? and  date(a.ASSIGN_DATE) <= ? and IS_SCM='Y' group by PRODUCT_CATEGORY,d.CIRCLE_ID,d.CIRCLE_NAME , "
											+" DATE(ASSIGN_DATE)  "
											+" ) normal_cnt on cm.CIRCLE_ID=normal_cnt.CIRCLE_ID and date(cm.DATE)= date(normal_cnt.ASSIGN_DATE)  ) my_alias on CIR.CIRCLE_ID=my_alias.CIRCLE_ID  and DATE(CIR.DATE)=date(my_alias.ASSIGN_DATE) ");
	
			
			
			
			String strPostFix = " where CIR.CIRCLE_ID in ("+reportDto.getHiddenCircleSelecIds()+") and date(CIR.date) >=?  and date(CIR.date) <=?  with ur ";
			
			int intCnt = 0;
			
			int intRSCnt = listProductTypes.size();
			String strAlias = null;
			StringBuffer strInnerQueryAll = new StringBuffer("");
			String strInnerQuerTemp = null;
			for(String strProdType : listProductTypes)
			{
				strAlias = "a"+intCnt;
				intCnt++;
				strInnerQuerTemp = strInnerQuery;
				if(intCnt!=intRSCnt)
				{
					sbHeader.append(strAlias).append(".cnt").append(" as \"").append(strProdType).append("\", ");
					total.append(strAlias).append(".cnt +");
					strInnerQuerTemp = strInnerQuerTemp.replace("my_alias", strAlias);
					strInnerQuerTemp = strInnerQuerTemp.replace("HC_PROD_CAT", strProdType);
					
					strInnerQueryAll = strInnerQueryAll.append(strInnerQuerTemp + " inner join ");
				}
				else 
				{
					sbHeader.append(strAlias).append(".cnt").append(" as \"").append(strProdType).append("\"");
					total.append(strAlias).append(".cnt ");
					strInnerQuerTemp = strInnerQuerTemp.replace("my_alias", strAlias);
					strInnerQuerTemp = strInnerQuerTemp.replace("HC_PROD_CAT", strProdType);
					
					strInnerQueryAll = strInnerQueryAll.append(strInnerQuerTemp);
				}
			}
			total.append(" ) as TOTAL");
			String strFinalQuery = sbPrefix.append(sbHeader.toString()).append(total.toString()).append(strFrom).append(strInnerQueryAll.toString()).append(strPostFix).toString();
			
			//logger.info("************************************from date******************************"+reportDto.getFromDate());
			///logger.info("************************************to date*********************************"+reportDto.getToDate());
			//logger.info("************************************circle*********************************"+reportDto.getHiddenCircleSelecIds());
			pstmt = connection.prepareStatement(strFinalQuery);
			System.out.println("connection::"+connection.getMetaData().getURL()+"total"+total+"Query : "+strFinalQuery);
			//String format = "MM/dd/yyyy";
			//logger.info("******************final query****************"+strFinalQuery);
			//logger.info("******************intRSCnt****************"+intRSCnt);
			int j=1;
			for(int i=1; i<=(intRSCnt*2); i++)
			{
				pstmt.setString(i, (Utility.getSqlDate(reportDto.getFromDate())+""));
				pstmt.setString(++i, (Utility.getSqlDate(reportDto.getToDate())+""));
				System.out.println("**********************FROM DATE PSTMNT************************"+Utility.getSqlDate(reportDto.getFromDate()));
				System.out.println("**********************TO DATE PSTMNT************************"+Utility.getSqlDate(reportDto.getToDate()));
				//System.out.println("**********************FROM DATE PSTMNT************************"+pstmt.toString());
				//System.out.println("**********************FROM DATE PSTMNT************************ " +pstmt);
				j=i;
			}
			System.out.println("j::"+j);
			pstmt.setString(++j, (Utility.getSqlDate(reportDto.getFromDate())+""));
			pstmt.setString(++j, (Utility.getSqlDate(reportDto.getToDate())+""));
			
			logger.info("***************************************************222222222222222222222"+strFinalQuery);
			rset=pstmt.executeQuery();	
			genericReportsOutputDto = new GenericReportsOutputDto();
			genericReportsOutputDto = getResultSet(rset , genericReportsOutputDto);
			
			/*while(rset.next())
			{
				logger.info("in loop");
				circleReportDto.setCircleName(rset.getString("CIRCLE_NAME"));
				circleReportDto.setNormal(rset.getString("NORMAL"));
				circleReportDto.setHd(rset.getString("HD"));
				circleReportDto.setDvr(rset.getString("DVR"));
				circleReportDto.setDvr_HD(rset.getString("HD DVR"));
				logger.info("to add list");
				arrReturn.add(circleReportDto);
			}*/
			
		}catch (Exception e) 
		{
			e.printStackTrace();
			throw new DAOException(e.getMessage());
		}
		finally
		{   
			logger.info("list:" +arrReturn);
			DBConnectionManager.releaseResources(pstmt ,rset );
			DBConnectionManager.releaseResources(connection);
		}
		
		//return genericReportsOutputDto;
		return genericReportsOutputDto;
	}
	
	
protected GenericReportsOutputDto getResultSet(ResultSet rs,GenericReportsOutputDto genericOutputDto) throws DAOException {
		try {
			
			if(rs == null) {
				throw new DAOException("Exception Occured!! ResultSet is NULL!!! See below for SQLException message!");
			}
			
					
					ResultSetMetaData md = rs.getMetaData();
					int count = md.getColumnCount();
					List<String> outputHeaderList = new ArrayList<String>();
					outputHeaderList.add("S. NO.");
					List<String[]> outputList = new ArrayList<String[]>();
		
					for (int i = 1; i <= count; i++) {
						String columnHeader  = md.getColumnLabel(i);				
						outputHeaderList.add(columnHeader.replaceAll("_", " "));
					}
					genericOutputDto.setHeaders(outputHeaderList);
					while (rs.next()) {
						String[] arr = new String[count];
						for (int i = 1; i <= count; i++) {
							System.out.println("RS data::"+rs.getString(i));
							arr[i - 1] = com.ibm.virtualization.recharge.common.Utility.replaceNullBySpace(rs.getString(i));
						}
						outputList.add(arr);
					}
					genericOutputDto.setOutput(outputList);
		

		} catch (SQLException sqe) {
			logger.error("SQL exception occured::" + sqe.getMessage());
			logger.error(sqe.getMessage(), sqe);
			throw new DAOException(sqe.getMessage());
		} catch (Exception e) {
			logger.error("Exception occured::" + e.getMessage());
			logger.error(e.getMessage(), e);
			throw new DAOException(e.getMessage());
		}
		return genericOutputDto;
	}
	
	
}
				
				
				
			
				
				
				
				
				
				
				
				
				
				
				
				
				
				
				
				
				
				
				
				
				
				
				
				
				
				
				
				
				
				
				
				
				
				
				
				
				
				
				
				
				
				
				
				
				
				
				
				
				
				
				
				
				
				
				
				
				
				
				
				
				
				
				
				
				
				
				
				
				
				
				
				
				
				
				
				
				
				
				
				
				
				
				
				
				
				
				
				
				
				
				
				
				
				
				
				
				
				
			
			
		

		
		
		
		
		
		
		
		
		
		
		
		
		
	
