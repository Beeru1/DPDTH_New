package com.ibm.utilities;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;

import org.apache.log4j.Logger;

import com.ibm.virtualization.recharge.exception.DAOException;

public class TRNumberCreate {
	private static TRNumberCreate trNumberCreate = null;

	private TRNumberCreate()
	{
	}
	
	public static TRNumberCreate getTRNumberCreateObject()
	{
		if(trNumberCreate==null)
			trNumberCreate = new TRNumberCreate();
		
		return trNumberCreate;
	}
	 private Logger logger = Logger.getLogger(TRNumberCreate.class.getName());
	 public static final String SQL_SELECT_TR_MASTER 	= "SELECT CONFIG_VALUE FROM DP_CONFIGURATION_MASTER WHERE CONFIG_ID=5 WITH UR";
	 public static final String SQL_SELECT_TR_CONFIG_DETAILS 	= "SELECT VALUE FROM DP_CONFIGURATION_DETAILS WHERE CONFIG_ID=5 AND ID=1 WITH UR";
	 public static final String SQL_UPDATE_TR_MASTER 	= "UPDATE DP_CONFIGURATION_MASTER SET CONFIG_VALUE=?  WHERE  CONFIG_ID=5 WITH UR";
	 public static final String SQL_UPDATE_TR_CONFIG_DETAILS_YEARLY 	= "UPDATE DP_CONFIGURATION_DETAILS SET VALUE=?  WHERE  CONFIG_ID=5 AND ID=1 WITH UR";
	 public static final String SQL_UPDATE_TR_CONFIG_MASTER_YEARLY 	= "UPDATE DP_CONFIGURATION_MASTER SET CONFIG_VALUE='0000000001', CONFIG_DESC=?  WHERE  CONFIG_ID=5 WITH UR";
		
	public synchronized String  generateTRNo(Connection con) throws DAOException {
		//Connection con = null;
		PreparedStatement ps = null;
		PreparedStatement ps1 = null;
		PreparedStatement ps2 = null;
		PreparedStatement ps3 = null;
		ResultSet rset			= null;
		ResultSet rset2			= null;
		String strNewTR ="";
		String strTRNoFull="";
		String strCurrentFY = Utility.getCurentFy(new Date());
		
		try 
		{
			ps2 = con.prepareStatement(SQL_SELECT_TR_CONFIG_DETAILS);
			rset2 = ps2.executeQuery();
			String strDetailFinancialYear = "";
			while(rset2.next())
			{
				strDetailFinancialYear = rset2.getString("VALUE");
			}
			
			ps = con.prepareStatement(SQL_SELECT_TR_MASTER);
			rset = ps.executeQuery();
			String strCurrentTR = "";
			
			while(rset.next())
			{
				strCurrentTR = rset.getString("CONFIG_VALUE");
			}
			logger.info("strCurrentTR = "+strCurrentTR);
			Long lngCurrentTR = Long.parseLong(strCurrentTR.trim());
			Long lngNewTR = lngCurrentTR+1;
		    strNewTR = lngNewTR.toString();
			
			int newCharCount = strNewTR.length();
			String strZeroes  = "";
			if(newCharCount<10)
			{
				int intRemainingZeroes = 10 - newCharCount;
				for(int count=0;count<intRemainingZeroes;count++)
					strZeroes  += "0";
			}
			if(!strZeroes.equals(""))
			{
				strNewTR = strZeroes + strNewTR;
			}
			
			logger.info("new TR == "+strNewTR);
			
			
			if(strCurrentFY.equals(strDetailFinancialYear))
			{
				ps1 = con.prepareStatement(SQL_UPDATE_TR_MASTER);
				ps1.setString(1, strNewTR);
				ps1.executeUpdate();
			}
			else
			{
				ps3 = con.prepareStatement(SQL_UPDATE_TR_CONFIG_DETAILS_YEARLY);
				ps3.setString(1, strCurrentFY);
				ps3.executeUpdate();
				
				ps1 = con.prepareStatement(SQL_UPDATE_TR_CONFIG_MASTER_YEARLY);
				ps1.setString(1, strCurrentTR);
				ps1.executeUpdate();
				strNewTR="0000000001";
			}
			
			strTRNoFull = "TR/"+strCurrentFY+"/"+strNewTR;
			
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
			}catch (Exception e) {
				e.printStackTrace();
				logger.error("Error Occured in insertStockDecl "+e.getMessage());
			}
		}
		
		return strTRNoFull;
		
	}
}
