package com.ibm.dp.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.log4j.Logger;

import com.ibm.dp.beans.CloseInactivePartnersBean;
import com.ibm.dp.dao.CloseInactivePartnersDao;
import com.ibm.dp.dto.CheckRetailerBalanceDto;
import com.ibm.dp.dto.CloseInactivePartnersDto;
import com.ibm.dp.dto.DpInvoiceDto;
import com.ibm.virtualization.recharge.db.DBConnectionManager;
import com.ibm.virtualization.recharge.exception.DAOException;

public class CloseInactivePartnersDaoImpl implements CloseInactivePartnersDao {
	
	public static Logger logger=Logger.getLogger(CloseInactivePartnersDaoImpl.class);

	public ArrayList uploadInvoiceSheet(Map<String, CloseInactivePartnersDto> cipMap) throws Exception {
		
		Connection con;
		PreparedStatement ps=null;
		ResultSet rs=null;
		String olmidNew="";
		try {
			con=DBConnectionManager.getDBConnection();
		} catch (DAOException e) {
			logger.info("Error in DB Connection");
			e.printStackTrace();
			throw new Exception();
		
		}
		Set<String> olmIdSet=cipMap.keySet();
		
		//String sqlUpdatequery=" update VR_LOGIN_MASTER set STATUS='C' where ( STATUS='A'and LOGIN_NAME=? ) or ( STATUS='I' and LOGIN_NAME=? )";
		ArrayList<String> updatedolmid=new ArrayList<String>();
//String sqlUpdatequery=" update VR_LOGIN_MASTER set STATUS='C' where ( STATUS='A'and LOGIN_NAME=? ) or ( STATUS='I' and LOGIN_NAME=? )";
		CloseInactivePartnersBean bean= new CloseInactivePartnersBean();
		String sqlUpdatequery="update VR_LOGIN_MASTER set STATUS='C' where ( (STATUS='A' and GROUP_ID=7) and LOGIN_NAME=?  ) or ( (STATUS='I' and GROUP_ID=7) and LOGIN_NAME=?)";
		ArrayList<CloseInactivePartnersDto> results= new ArrayList<CloseInactivePartnersDto>();
		logger.info("Updating DB");
		try{
	    for(String olmId:olmIdSet)
	    {		    
	    	 logger.info("olm id"+olmId);
	    	 ps=con.prepareStatement(sqlUpdatequery);
	    	 ps.setString(1, olmId);
	    	 ps.setString(2, olmId);
	    	 
	    	 ps.executeUpdate();
	    	
	    	 
	    	 con.commit();
	    	 
	    }
	        
	    
	    logger.info("Updated the database");
	    
	    String sqlSelectquery=" Select LOGIN_NAME from VR_LOGIN_MASTER where (LOGIN_NAME=? and (STATUS='C' and GROUP_ID=7)) ";
	    
	    try{
	    	//CloseInactivePartnersDto dto=new CloseInactivePartnersDto();
	    	//ArrayList<CloseInactivePartnersDto> results= new ArrayList<CloseInactivePartnersDto>();
		    for(String olmId:olmIdSet)
		    {		    
		    	 //logger.info("olm id"+olmId);
		    	 ps=con.prepareStatement(sqlSelectquery);
		    	 ps.setString(1, olmId);
		    	 rs = ps.executeQuery();
		    	 
		    	 //ps.executeUpdate();
		    	 
		    	 while (rs.next()) {
		    		 
		    		 CloseInactivePartnersDto dto=new CloseInactivePartnersDto();					
					 dto.setOlmid(rs.getString("LOGIN_NAME"));
					 logger.info("OLM---"+rs.getString("LOGIN_NAME"));
					 
					 results.add(dto);
						
						
					}
		    	 
		    	 con.commit();
		    } 
		    
		    for(CloseInactivePartnersDto dto: results)
	        {
		    	String olmid= dto.getOlmid();
		    	
		    	olmidNew =olmidNew+olmid+" ,";
	        }
		    
		    olmidNew=olmidNew.substring(0,olmidNew.length()-1);
		    results.clear();
		    CloseInactivePartnersDto dto=new CloseInactivePartnersDto();	
		    dto.setOlmid(olmidNew);
		    results.add(dto);
		    
		    
		    logger.info("Actual distributors found in file:"+results.size()); 
		    for(int i = 0; i<results.size();i++){
		        logger.info("Record number "+i+" DATA  --  "+results.get(i).getOlmid());
		        
		    } 

	    }
		    catch(Exception expc)
		    {
		    	
		    }
		    
		
		}catch(Exception expc){
	    	expc.printStackTrace();
	    	logger.info("Exception in DB....please try again");
	    	throw new Exception();
	    }finally {
	    	con.commit();
	    	DBConnectionManager.releaseResources(ps, rs);
	    }
		
	return(results);
		
	}
	
}
