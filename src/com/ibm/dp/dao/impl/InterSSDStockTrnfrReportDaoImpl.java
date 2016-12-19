package com.ibm.dp.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

import com.ibm.dp.common.DBQueries;
import com.ibm.dp.dao.InterSSDStockTrnfrReportDao;
import com.ibm.dp.dto.InterSSDStockTrnfrReportDto;
import com.ibm.virtualization.recharge.dao.rdbms.BaseDaoRdbms;
import com.ibm.virtualization.recharge.db.DBConnectionManager;
import com.ibm.virtualization.recharge.exception.DAOException;

public class InterSSDStockTrnfrReportDaoImpl  extends BaseDaoRdbms  implements InterSSDStockTrnfrReportDao {
	private Logger logger = Logger.getLogger(InterSSDStockTrnfrReportDaoImpl.class.getName());
	public InterSSDStockTrnfrReportDaoImpl(Connection con) {
		super(con);
	}
	
	public  List<InterSSDStockTrnfrReportDto> getReportExcel(String circleId , String fromDate,String toDate,int loginRole,String loginCircleId) throws DAOException
	{
		PreparedStatement ps1 = null;
		ResultSet rs1 = null;
		Connection con = null;
		
		
		List<InterSSDStockTrnfrReportDto> reportList = new ArrayList<InterSSDStockTrnfrReportDto>();
		InterSSDStockTrnfrReportDto reportDto = null ;
				
		try 
		{
			con = DBConnectionManager.getDBConnection(); // db2
			if(loginRole==3 || loginRole==4){
				logger.info("In if condition in getReportExcel consumption dao impl circle == "+circleId);
				
				ps1 = 	con.prepareStatement(DBQueries.GET_REPORT_LIST_INTER_SSD_STOCK_TRANSFER);
				ps1.setString(1, loginCircleId);
				ps1.setString(2, fromDate);
				ps1.setString(3, toDate);
				
				
			}else{
				logger.info("In if condition in getReportExcel iNTER SSD dao impl circle == "+circleId);
				if(circleId.equals("-1")){
					ps1 = 	con.prepareStatement(DBQueries.GET_REPORT_LIST_INTER_SSD_STOCK_TRANSFER_ALL);
					ps1.setString(1, fromDate);
					ps1.setString(2, toDate);
					
				}else{
					ps1 = 	con.prepareStatement(DBQueries.GET_REPORT_LIST_INTER_SSD_STOCK_TRANSFER);
					ps1.setString(1, circleId);
					ps1.setString(2, fromDate);
					ps1.setString(3, toDate);
				}
				
				
				
			}
			rs1=ps1.executeQuery();
			reportList = new ArrayList<InterSSDStockTrnfrReportDto>();
			while(rs1.next())
			{
				reportDto = new InterSSDStockTrnfrReportDto();
				reportDto.setCircleName(rs1.getString("CIRCLE_NAME"));
				reportDto.setFromDistName(rs1.getString("FROM_DIST"));
				String iniateDate =getDateFormat(rs1.getString("CREATED_DATE"));
				logger.info("Iniate date bef rs1.getString(CREATED_DATE) === "+rs1.getString("CREATED_DATE") + "   after == "+iniateDate  );
				reportDto.setInitiationDate(iniateDate);
				reportDto.setProductName(rs1.getString("PRODUCT_NAME"));
				String receiptDate =getDateFormat(rs1.getString("UPDATED_DATE"));
				logger.info("Iniate date bef rs1.getString(UPDATED_DATE) === "+rs1.getString("UPDATED_DATE") + "   after == "+receiptDate  );
				reportDto.setReceiptDate(receiptDate);
				reportDto.setRequestedQty(rs1.getString("REQ_TRANSFER_QTY"));
				reportDto.setToDistName(rs1.getString("TO_DIST"));
				String transferDate =getDateFormat(rs1.getString("DC_DATE"));
				logger.info("Iniate date bef rs1.getString(DC_DATE) === "+rs1.getString("DC_DATE") + "   after == "+transferDate  );
				reportDto.setTransferDate(transferDate);
				
				reportDto.setTransferedQty(rs1.getString("TRANSFERRED_QTY"));
				reportDto.setZbmZsmName(rs1.getString("CONTACT_NAME"));
				reportDto.setFromTSM(rs1.getString("FROM_TSM"));
				reportDto.setToTSM(rs1.getString("TO_TSM"));
				
				reportList.add(reportDto);
			}
			
			
			
			
	}catch(SQLException sqle){
			sqle.printStackTrace();
			throw new DAOException("SQLException: " + sqle.getMessage());
		}catch(Exception e){
			e.printStackTrace();
			throw new DAOException("Exception: " + e.getMessage());
		}finally{
			try {
				if (rs1 != null)
					rs1.close();
				if (ps1 != null)
					ps1.close();
				if (con != null) {
					con.close();
				}
			} catch (Exception e) {
				e.printStackTrace();
				logger.error("Error Occured in ReportDAOImpl "+e.getMessage());
			}
		}
		return reportList;
	}
	
	private String getDateFormat(String strDate){
		String fullDate="";
		
		String[] strArrayDat = strDate.split("-");
		String strDDate = strArrayDat[2].split(" ")[0];
		String strMonth = strArrayDat[1];
		String strYear = strArrayDat[0];
		fullDate  =strDDate+"/"+strMonth+"/"+strYear;
		
		return fullDate;
	}
}
