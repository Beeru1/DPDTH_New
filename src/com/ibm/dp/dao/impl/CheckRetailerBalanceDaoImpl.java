package com.ibm.dp.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.log4j.Logger;

import com.ibm.dp.common.DBQueries;
import com.ibm.dp.dao.CheckRetailerBalanceDao;
import com.ibm.dp.dto.CheckRetailerBalanceDto;
import com.ibm.dp.dto.DpInvoiceDto;
import com.ibm.virtualization.recharge.db.DBConnectionManager;
import com.ibm.virtualization.recharge.exception.DAOException;
import com.ibm.virtualization.recharge.exception.VirtualizationServiceException;

public class CheckRetailerBalanceDaoImpl implements CheckRetailerBalanceDao {

	private Logger logger = Logger.getLogger(CheckRetailerBalanceDaoImpl.class.getName());
	public ArrayList getDetails( long loginUserId ) throws VirtualizationServiceException, DAOException {
		
		logger.info("Started...");
        		
		Connection con= DBConnectionManager.getDBConnection();
		PreparedStatement ps = null;
		ResultSet rs = null;
		int paramCount = 1;
		
		ArrayList<CheckRetailerBalanceDto> results = new ArrayList<CheckRetailerBalanceDto>();
		try {
			logger.info(" Before Connection");
			StringBuilder query = new StringBuilder(DBQueries.SQL_SELECT_FOR_RETAILER_BALANCE);
							
			ps = con.prepareStatement(query.toString());
			logger.info("Query=" + query.toString());
						
			ps.setLong(1, loginUserId);
			
			
			
			rs = ps.executeQuery();
			//ArrayList<CheckRetailerBalanceDto> results = new ArrayList<CheckRetailerBalanceDto>();
			while (rs.next()) {
				CheckRetailerBalanceDto dto = new CheckRetailerBalanceDto();
				dto.setBalance(rs.getString("BALANCE"));
				dto.setRetailerid(rs.getInt("RETAILER_ID"));
				dto.setRetailerlapu(rs.getString("RETAILER_LAPU"));
				dto.setRetailername(rs.getString("RETAILER_NAME"));
				dto.setFseid(rs.getInt("FSE_ID"));
				dto.setFsename(rs.getString("FSE_NAME"));
				dto.setFsemobile(rs.getString("FSE_MOBILE_NO"));
				dto.setDepth(rs.getString("DEPTH"));
				results.add(dto);
			}
			logger.info(" Number of records found = " + results.size());
			
			logger.info("Executed .... ");
			
		} catch (SQLException e) {

			logger.error("SQL Exception occured while select."
					+ "Exception Message: ", e);
			
		} 
		
		finally {
			DBConnectionManager.releaseResources(con);
			DBConnectionManager.releaseResources(ps, rs);

		}
		logger.info(" Records to be shown=====" + results.size());
		return results;
	}

}
