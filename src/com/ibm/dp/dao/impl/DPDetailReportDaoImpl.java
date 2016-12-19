package com.ibm.dp.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;

import org.apache.log4j.Logger;

import com.ibm.dp.common.DBQueries;
import com.ibm.dp.dao.DPDetailReportDao;
import com.ibm.dp.dto.DPDetailReportDTO;
import com.ibm.utilities.Utility;
import com.ibm.virtualization.recharge.dao.rdbms.BaseDaoRdbms;
import com.ibm.virtualization.recharge.exception.DAOException;

public class DPDetailReportDaoImpl extends BaseDaoRdbms implements DPDetailReportDao{

	private Logger logger = Logger.getLogger(DPReportDaoImpl.class.getName());

	public DPDetailReportDaoImpl(Connection con) {
		super(con);
	}
	public List<DPDetailReportDTO> getDetailReportExcel(String fromDate, String toDate) throws DAOException
	{
		//Connection con = null;
		PreparedStatement ps1 = null;
		ResultSet rs1 = null;
		
		PreparedStatement ps2 = null;
		ResultSet rs2 = null;
		
		
		int account_id = 0;
		String accountName= "";
		String productName = "";
		int productID = 0;
		int churnInventory= 0;
		int upgradeInventory= 0;
		int doaInventory= 0;
		int defectiveInventory= 0;
		int c2sInventory= 0;
		
		int churnInventoryHist= 0;
		int upgradeInventoryHist= 0;
		int doaInventoryHist= 0;
		int defectiveInventoryHist= 0;
		int c2sInventoryHist= 0;
		
		List<DPDetailReportDTO> detailReportList = new ArrayList<DPDetailReportDTO>();
		DPDetailReportDTO dpDetailReportDTO = null ;
				
		try 
		{
			//connection = DBConnectionManager.getDBConnection(); // db2
			
				ps1 = connection.prepareStatement(DBQueries.SQL_REV_LOG_Excel);
				ps1.setDate(1, Utility.getSqlDateFromString(fromDate.trim(), "MM/dd/yyyy"));
				ps1.setDate(2, Utility.getSqlDateFromString(toDate.trim(), "MM/dd/yyyy"));
				ps2 = connection.prepareStatement(DBQueries.SQL_REV_LOG_HIST_Excel);			
				rs1 = ps1.executeQuery();
			
		while(rs1.next())
		{
			account_id = rs1.getInt("ACCOUNT_ID");
			accountName = rs1.getString("ACCOUNT_NAME");
			productName = rs1.getString("PRODUCT_NAME");
			productID = rs1.getInt("PRODUCT_ID");
			churnInventory = rs1.getInt("CHURN");
			upgradeInventory = rs1.getInt("UPGRADE");
			doaInventory = rs1.getInt("DOA");
			defectiveInventory = rs1.getInt("DEFECTIVE");
			c2sInventory = rs1.getInt("C2S");
			
			
			ps2.setInt(1 , account_id);
			ps2.setInt(2, account_id);
			ps2.setInt(3, productID);
			ps2.setDate(4, Utility.getSqlDateFromString(fromDate.trim(), "MM/dd/yyyy"));
			ps2.setDate(5, Utility.getSqlDateFromString(toDate.trim(), "MM/dd/yyyy"));
			
			
			// Get products of given dist 
			
			rs2 = ps2.executeQuery(); 				
			
			if(rs2.next())
			{
				churnInventoryHist = rs2.getInt("CHURN");
				upgradeInventoryHist = rs2.getInt("UPGRADE");
				doaInventoryHist = rs2.getInt("DOA");
				defectiveInventoryHist = rs2.getInt("DEFECTIVE");
				c2sInventoryHist = rs2.getInt("C2S");
				
				dpDetailReportDTO= new DPDetailReportDTO();
				
				dpDetailReportDTO.setAccountName(accountName);
				dpDetailReportDTO.setProductName(productName);
				dpDetailReportDTO.setChurnInventory(churnInventory + churnInventoryHist);
				dpDetailReportDTO.setUpgradeInventory(upgradeInventory+upgradeInventoryHist);
				dpDetailReportDTO.setDoaInventory(doaInventory + doaInventoryHist);
				dpDetailReportDTO.setDefectiveInventory(defectiveInventory + defectiveInventoryHist);
				dpDetailReportDTO.setC2sInventory(c2sInventory + c2sInventoryHist);
				
				
				detailReportList.add(dpDetailReportDTO);
				dpDetailReportDTO = null;
			}// END PRODUCT WHILE
			
	
			
			} ///////////////////////////////// END of MAIN WHILE
			
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
				
				if (rs2 != null)
					rs2.close();
				if (ps2 != null)
					ps2.close();
			} catch (Exception e) {
				e.printStackTrace();
				logger.error("Error Occured in ReportDAOImpl "+e.getMessage());
			}
		}
		return detailReportList;
	}
public static void main(String[] args) {
	 
	String str = "06-12-2010 5:41:40 PM";
	
	try
	{
	 
	 	
	// Calendar cal1 = Calendar.getInstance();
		 Calendar cal1 = new GregorianCalendar();
	//System.out.println("cal------------------"+cal);
	 SimpleDateFormat simpleDateFormat=new SimpleDateFormat("dd-MM-yyyy hh:mm:ss a");
	 //SimpleDateFormat simpleDateFormat=new SimpleDateFormat("H:mm:ss:SSS");
	 
	 
	 System.out.println("%%%%%%%"+simpleDateFormat.parse(str) );
	 
	 //String[] ids = TimeZone.getAvailableIDs(-8 * 60 * 60 * 1000);

	// SimpleTimeZone pdt = new SimpleTimeZone(-8 * 60 * 60 * 1000, ids[0]);
	 //pdt.setStartRule(Calendar.APRIL, 1, Calendar.SUNDAY, 2 * 60 * 60 * 1000);
	 //pdt.setEndRule(Calendar.OCTOBER, -1, Calendar.SUNDAY, 2 * 60 * 60 * 1000);
	 cal1.clear();
	 //System.out.println(simpleDateFormat.parse(simpleDateFormat.format(str)));
	 cal1.setTime(new java.util.Date( simpleDateFormat.parse(str).getTime()));	
	 
	 //System.out.println( "***********"+new java.util.Date( simpleDateFormat.parse(str).getTime()));
	 
	// GregorianCalendar cal1 = new GregorianCalendar(pdt);
	 //cal1.set(2010,12,30,01,13,17);
	 
	// cal1.setTime(2010, 12, 30);
	// cal1.set(year, month, date, hourOfDay, minute, second), value)
	 //cal1.setTime(new java.util.Date(str));
	 System.out.println(cal1);
	 System.out.println(cal1.toString());
	 
	 //System.out.println("---"+cal1.get(Calendar.AM_PM));
	 //System.out.println("---"+cal1.get(Calendar.AM));
	 //System.out.println("---"+cal1.get(Calendar.PM));
	 System.out.println(cal1.get(Calendar.DATE));
	 System.out.println(cal1.get(Calendar.DAY_OF_MONTH));
	 System.out.println(cal1.get(Calendar.MONTH));
	 System.out.println(cal1.get(Calendar.YEAR));
	 System.out.println(cal1.get(Calendar.HOUR));
	 System.out.println(cal1.get(Calendar.HOUR_OF_DAY));
	 System.out.println(cal1.get(Calendar.MINUTE));
	 System.out.println(cal1.get(Calendar.SECOND));
	
	
	//MXDateTimeType mlongtype = new MXDateTimeType();						

	//mlongtype.set_value(cal);

	
	
	
	//DateFormat format;
	//String string = format.format(new java.util.Date(str));
//	----------------------------------------------------------------------------------
	GregorianCalendar calendar = new GregorianCalendar(2010, 11, 30, 01, 17, 45);
	System.out.println(calendar.getTime());
	
	DateFormat format1 = DateFormat.getInstance();
	System.out.println("Default locale format gives: " + format1.format(calendar.getTime()));
//	----------------------------------------------------------------------------------
	//GregorianCalendar calendar1 = new GregorianCalendar(1973, 0, 18, 3, 45, 50);
	//SimpleDateFormat format2 = new SimpleDateFormat("E M d hh:mm:ss z yyyy");
	//System.out.println("Customized format gives: " + format2.format(calendar1.getTime()));
}
catch(Exception ex)
{
	System.out.println(ex);
}
}

}

