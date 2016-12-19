package com.ibm.utilities;

import com.ibm.dp.common.Constants;
import com.ibm.virtualization.recharge.exception.DAOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;

import org.apache.log4j.Logger;

public class DcCreation  {
	private static DcCreation dcCreation = null;
	private String strDcNoFull="";
	//private Connection con; 
	private DcCreation()
	{
		//con = connection;
	}
	
	public static DcCreation getDcCreationObject()
	{
		if(dcCreation==null)
			dcCreation = new DcCreation();
		
		return dcCreation;
	}
	 private Logger logger = Logger.getLogger(DcCreation.class.getName());
	 public static final String SQL_SELECT_DC_MASTER 	= "SELECT * FROM DP_CONFIGURATION_MASTER WHERE CONFIG_ID=? WITH UR";
	 public static final String SQL_SELECT_DC_CONFIG_DETAILS 	= "SELECT * FROM DP_CONFIGURATION_DETAILS WHERE CONFIG_ID=? AND ID=1 WITH UR";
	 public static final String SQL_UPDATE_DC_MASTER 	= "UPDATE DP_CONFIGURATION_MASTER SET CONFIG_VALUE=?  WHERE  CONFIG_ID=? WITH UR";
	 public static final String SQL_UPDATE_DC_CONFIG_DETAILS_YEARLY 	= "UPDATE DP_CONFIGURATION_DETAILS SET VALUE=?  WHERE  CONFIG_ID=? AND ID=1 WITH UR";
	 public static final String SQL_UPDATE_DC_CONFIG_MASTER_YEARLY 	= "UPDATE DP_CONFIGURATION_MASTER SET CONFIG_VALUE='0000000001', CONFIG_DESC=?  WHERE  CONFIG_ID=? WITH UR";
		
	public String  generateDcNo(Connection con, String strDCType) throws DAOException {
		//Connection con = null;
		PreparedStatement ps = null;
		PreparedStatement ps1 = null;
		PreparedStatement ps2 = null;
		PreparedStatement ps3 = null;
		ResultSet rset			= null;
		ResultSet rset2			= null;
		String strNewDc ="";
		String strDCPrefix = "";
		
		String strCurrentFY = Utility.getCurentFy(new Date());
		int intConfigID = 0;
		if(strDCType.equals(Constants.DC_TYPE_REVERSE))
		{
			intConfigID = Constants.DC_TYPE_REVERSE_CONFIG_ID;
			strDCPrefix = Constants.DC_TYPE_REVERSE_PREFIX;
		}
		else if(strDCType.equals(Constants.DC_TYPE_FRESH))
		{
			intConfigID = Constants.DC_TYPE_FRESH_CONFIG_ID;
			strDCPrefix = Constants.DC_TYPE_FRESH_PREFIX;
		}
		else if(strDCType.equals(Constants.DC_TYPE_CHURN))
		{
			intConfigID = Constants.DC_TYPE_CHURN_CONFIG_ID;
			strDCPrefix = Constants.DC_TYPE_CHURN_PREFIX;
		}
		else if(strDCType.equals(Constants.DC_TYPE_SWAP_DOA))
		{
			intConfigID = Constants.DC_TYPE_SWAP_DAO_CONFIG_ID;
			strDCPrefix = Constants.DC_TYPE_SWAP_DAO_PREFIX;
		}
		
		try {
			//con = DBConnectionManager.getDBConnection();
			ps2 = con.prepareStatement(SQL_SELECT_DC_CONFIG_DETAILS);
			ps2.setInt(1, intConfigID);
			rset2 = ps2.executeQuery();
			String strDetailFinancialYear = "";
			while(rset2.next())
			{
				strDetailFinancialYear = rset2.getString("VALUE");
			}
			
			ps = con.prepareStatement(SQL_SELECT_DC_MASTER);
			ps.setInt(1, intConfigID);
			
			rset = ps.executeQuery();
			String strCurrentDc = "";
			
			while(rset.next())
			{
				strCurrentDc = rset.getString("CONFIG_VALUE");
			}
			logger.info("strCurrentDc = "+strCurrentDc);
			Long lngCurrentDc = Long.parseLong(strCurrentDc.trim());
			Long lngNewDc = lngCurrentDc+1;
		    strNewDc = lngNewDc.toString();
			
			int newCharCount = strNewDc.length();
			String strZeroes  = "";
			if(newCharCount<10){
				int intRemainingZeroes = 10 - newCharCount;
				for(int count=0;count<intRemainingZeroes;count++){
					strZeroes  += "0";
				}
			}
			if(!strZeroes.equals("")){
				strNewDc = strZeroes + strNewDc;
			}
			logger.info("new D c== "+strNewDc);
			
			
			if(strCurrentFY.equals(strDetailFinancialYear)){
				logger.info("in if cond in Dc Creation strNewDc == "+strNewDc);
				ps1 = con.prepareStatement(SQL_UPDATE_DC_MASTER);
				ps1.setString(1, strNewDc);
				ps1.setInt(2, intConfigID);
				ps1.executeUpdate();
			}else{
				logger.info("in else cond in Dc Creation strCurrentFY == "+strCurrentFY);
				
				ps3 = con.prepareStatement(SQL_UPDATE_DC_CONFIG_DETAILS_YEARLY);
				
				ps3.setString(1, strCurrentFY);
				ps3.setInt(2, intConfigID);
				
				ps3.executeUpdate();
				
				ps1 = con.prepareStatement(SQL_UPDATE_DC_CONFIG_MASTER_YEARLY);
				ps1.setString(1, strCurrentDc);
				ps1.setInt(2, intConfigID);
				ps1.executeUpdate();
				strNewDc="0000000001";
			}		
			
			//con.commit();
			strDcNoFull = strDCPrefix+"/"+strCurrentFY+"/"+strNewDc;
			
		}catch(SQLException sqle){
			sqle.printStackTrace();
			throw new DAOException("SQLException: " + sqle.getMessage());
		}catch(Exception e){
			e.printStackTrace();
			throw new DAOException("Exception: " + e.getMessage());
		}finally{
			try {
				if (ps != null)
					ps.close();
				if (ps1 != null)
					ps1.close();
/*//				if (con != null) {
//					con.close();
//				}
*/			} catch (Exception e) {
				e.printStackTrace();
				logger.error("Error Occured in insertStockDecl "+e.getMessage());
			}
		}
		
		return strDcNoFull;
	}
	
}
