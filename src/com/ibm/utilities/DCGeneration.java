package com.ibm.utilities;

import com.ibm.dp.common.Constants;
import com.ibm.dp.common.DBQueries;
import com.ibm.virtualization.recharge.db.DBConnectionManager;
import com.ibm.virtualization.recharge.exception.DAOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;

import org.apache.log4j.Logger;

public class DCGeneration  {
	private static DCGeneration dcGeneration = null;
	private String strDcNoFull="";
	//private Connection con; 
	//private DCGeneration()	{	}
	
	public static DCGeneration getDcCreationObject()	{
		if(dcGeneration==null){			dcGeneration = new DCGeneration();		}
		return dcGeneration;
	}
	 private Logger logger = Logger.getLogger(DCGeneration.class.getName());
	 	
	public String  generateDcNo(Connection con, String strDCType) throws DAOException {
		//Connection con = null;
		PreparedStatement ps = null;		PreparedStatement ps1 = null;		
		ResultSet rset			= null;		ResultSet rset2			= null;
		String strDc ="";		String strDCPrefix = "CHR";String strNewDc ="";
		//long dcNo=0;
		String strCurrentFY = Utility.getCurentFy(new Date());
		
		try {
			//con = DBConnectionManager.getDBConnection();
			//ps = con.prepareStatement("select  max(substr(DC_NO,11,10)) AS MaxDc from DP_REV_CHURN_INVENTORY");
			ps = con.prepareStatement("select  max(substr(DC_NO,11,10)) AS MaxDc from DP_REV_CHURN_DC_HEADER");
			rset=ps.executeQuery();
			while(rset.next())	{
				if(rset.getString("MaxDc") !=null){	strDc = rset.getString("MaxDc");
				} else {strDc ="0000000000";}
			}
			Long dcNo = Long.parseLong(strDc)+ 1;
			strNewDc= dcNo.toString();
			
			
			int newCharCount = strNewDc.length();
			String strZeroes  = "";
			if(newCharCount<10){
				int intRemainingZeroes = 10 - newCharCount;
				for(int count=0;count<intRemainingZeroes;count++){	strZeroes  += "0";	}
			}
			if(!strZeroes.equals("")){				strNewDc = strZeroes + strNewDc;			}
			logger.info("new D c== "+strNewDc);
			
			strDcNoFull = strDCPrefix+"/"+strCurrentFY+"/"+strNewDc;
			
		}catch(SQLException sqle){	sqle.printStackTrace();
			throw new DAOException("SQLException: " + sqle.getMessage());
		}catch(Exception e){			e.printStackTrace();
			throw new DAOException("Exception: " + e.getMessage());
		}finally{
			try {	if (ps != null)					ps.close();
				if (ps1 != null)					ps1.close();
			} catch (Exception e) {
				e.printStackTrace();
				logger.error("Error Occured in insertStockDecl "+e.getMessage());
			}
		}
		
		return strDcNoFull;
	}
	
}
