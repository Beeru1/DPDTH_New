/*****************************************************************************\
 **
 ** Virtualization - Recharge.
 **
 ** Copyright (c) 2007-2008 IBM.
 ** All Rights Reserved
 **
 **
 \****************************************************************************/
package com.ibm.dp.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;

import org.apache.log4j.Logger;

import com.ibm.dp.common.DBQueries;
import com.ibm.dp.dao.GeographyDao;
import com.ibm.dp.dto.Geography;
import com.ibm.virtualization.recharge.common.Constants;
import com.ibm.virtualization.recharge.common.ExceptionCode;
import com.ibm.virtualization.recharge.common.Utility;
import com.ibm.virtualization.recharge.dao.RegionDao;
import com.ibm.virtualization.recharge.db.DBConnectionManager;
import com.ibm.virtualization.recharge.dto.Circle;
import com.ibm.virtualization.recharge.dto.ReportInputs;
import com.ibm.virtualization.recharge.exception.DAOException;

public class GeographyDaoRdbms implements GeographyDao {
	/* Logger for this class. */
	private static Logger logger = Logger.getLogger(GeographyDaoRdbms.class
			.getName());

	/* SQL Used within DaoImpl */
	
	//protected final static String SQL_REGION_SELECT_REGION_NAME = "SELECT REGION_NAME FROM VR_REGION_MASTER WHERE REGION_ID = ? ";

	private Connection connection = null;


	public GeographyDaoRdbms(Connection connection) {
		this.connection = connection;
	}

	/**
	 * This is a generic method to set the data from Resultset object to
	 * Respective DTO object
	 * 
	 * @param rs
	 * @return region
	 * @throws SQLException
	 */
	private Geography populateDto(ResultSet rs) throws SQLException {
		logger.info("Started...populateDto....");
		Geography geography = new Geography();
		geography.setGeographyId(rs.getInt(1));
		geography.setGeographyName(rs.getString(3));
		geography.setGeographyCode(rs.getString(2));	
		geography.setStatus(rs.getString(5));
		geography.setParentId(rs.getInt(4));
		logger.info("Executed ....");
		return geography;
	}

	public ArrayList getActiveGeographys(Integer level) throws DAOException { //  to find parent 
		logger.info("Started...");
		PreparedStatement ps = null;
		ResultSet rs = null;
		String table_name ="";
		String SQL = "";
		ArrayList regionList = new ArrayList();

		try {
			//System.out.println("level----------"+level);
			if (level>0){
			table_name = getTableName(level-1); 
			
			//System.out.println("table_name.........."+table_name);
			String fields=getFieldsName(level-1);
			if(fields!=null){			
			SQL = "Select "+fields +" from " + table_name +" WHERE STATUS ='A'";
			//System.out.println("SQL ---"+SQL);
			ps = connection.prepareStatement(SQL);
			rs = ps.executeQuery();			
	
			while (rs.next()) {
				regionList.add(populateDto(rs));
			}
			}
			}
			logger.info("Executed ::::");			
			return regionList;
		} catch (SQLException sqle) {
			logger.fatal(" SQL Exception occured while find."
					+ "Exception Message: " + sqle.getMessage(), sqle);
			throw new DAOException(ExceptionCode.ERROR_DB_INTERNAL);
		} finally {
			/* Close the resultset, statement. */
			DBConnectionManager.releaseResources(ps, rs);
		}
	}
    private String getTableName(Integer level) throws DAOException
    {
    	logger.info("Started...getTableName...");
		PreparedStatement ps = null;
		ResultSet rs = null;
		String tableName ="";
		try{
			ps = connection.prepareStatement(DBQueries.SQL_GET_TABLE_NAME);
			ps.setInt(1,level);
			rs = ps.executeQuery();
			if (rs.next())
					tableName = rs.getString("table_name");
			return tableName;
			
		} catch (SQLException sqle) {

			logger.fatal(" SQL Exception occured while find."
					+ "Exception Message: " + sqle.getMessage(), sqle);
			throw new DAOException(ExceptionCode.ERROR_DB_INTERNAL);
		} finally {
			/* Close the resultset, statement. */
			DBConnectionManager.releaseResources(ps, rs);
		}
		
		}
    private String getFieldsName(Integer level) throws DAOException
    {
    	logger.info("Started...getFieldsName...");
		PreparedStatement ps = null;
		ResultSet rs = null;
		String fieldsName ="";
		try{
			ps = connection.prepareStatement(DBQueries.SQL_GET_FIELDS_NAME);
			ps.setInt(1,level);
			rs = ps.executeQuery();
			int i=0;
			while (rs.next()){
				fieldsName= fieldsName+rs.getString("GEOGRAPHY_FIELD")+",";
			}
			//System.out.println("----"+fieldsName);
			fieldsName=fieldsName.substring(0, fieldsName.length()-1);
			//System.out.println("----"+fieldsName);	
			return fieldsName;
			
		} catch (SQLException sqle) {

			logger.fatal(" SQL Exception occured while find."
					+ "Exception Message: " + sqle.getMessage(), sqle);
			throw new DAOException(ExceptionCode.ERROR_DB_INTERNAL);
		} finally {
			/* Close the resultset, statement. */
			DBConnectionManager.releaseResources(ps, rs);
		}
		
		}
    private String getGeoField(String field,Integer level) throws DAOException
    {
    	logger.info("Started...getFieldsName...");
		PreparedStatement ps = null;
		ResultSet rs = null;
		String fieldsName ="";
		try{
			ps = connection.prepareStatement(DBQueries.SQL_GET_FIELD);
			ps.setInt(1,level);
			ps.setString(2,field.toUpperCase());
			rs = ps.executeQuery();
			int i=0;
			if (rs.next())
				fieldsName=rs.getString("GEOGRAPHY_FIELD");
			return fieldsName;
			
		} catch (SQLException sqle) {

			logger.fatal(" SQL Exception occured while find."
					+ "Exception Message: " + sqle.getMessage(), sqle);
			throw new DAOException(ExceptionCode.ERROR_DB_INTERNAL);
		} finally {
			/* Close the resultset, statement. */
			DBConnectionManager.releaseResources(ps, rs);
		}
		
		}
    private String getSeqName(Integer level) throws DAOException
    {
    	logger.info("Started...getTableName...");
		PreparedStatement ps = null;
		ResultSet rs = null;
		String seqName ="";
		try{
			ps = connection.prepareStatement(DBQueries.SQL_GET_SEQ_NAME);
			ps.setInt(1,level);
			rs = ps.executeQuery();
			if (rs.next())
					seqName = rs.getString("seq_name");
			return seqName;
			
		} catch (SQLException sqle) {

			logger.fatal(" SQL Exception occured while find."
					+ "Exception Message: " + sqle.getMessage(), sqle);
			throw new DAOException(ExceptionCode.ERROR_DB_INTERNAL);
		} finally {
			/* Close the resultset, statement. */
			DBConnectionManager.releaseResources(ps, rs);
		}
		
		}
// 1- ID, 2- Code , 3-Name , 4- Parent_ID, 5-Status 5- Created_by ,6-Created_Dt,7-Updated_By 8-Updated_DT
	public ArrayList<Geography> getGeographys(String geographyCode, String geographyName, Integer level) throws DAOException {
	
		//logger.info("Started...");
		PreparedStatement ps = null;
		ResultSet rs = null;
		String sql = "";
		ArrayList<Geography> results = new ArrayList<Geography>();
		
	try {
		String fields= getFieldsName(level);
		if(fields!=null){
			String NAME =getGeoField("NAME",level);
		sql ="SELECT "+ fields +" FROM  "+getTableName(level)+" WHERE "+ getGeoField("CODE",level) +" = ?  OR "+ NAME +" = ? ORDER BY " + NAME;
		ps = connection.prepareStatement(sql);
		ps.setString(1, geographyCode.toUpperCase());
		ps.setString(2, geographyName.toUpperCase());
		rs = ps.executeQuery();		
		while (rs.next()) {
			Geography geography = new Geography();
			geography.setGeographyCode(rs.getString(2));//CODE
			geography.setGeographyName(rs.getString(3));//NAME
			results.add(geography);
		}
		}
		//logger.info(" Number of Geographys found = " + results.size());
		//logger.info("Executed .... ");
		return results;
	} catch (SQLException sqle) {
		//logger.error(" SQL Exception occured while find."
			//	+ "Exception Message: " + sqle.getMessage(), sqle);
		throw new DAOException(ExceptionCode.Geography.ERROR_GEOGRAPHY_NOT_FOUND);
	} finally {
		/* Close the resultset, statement. */
		DBConnectionManager.releaseResources(ps, rs);

		}	
	}
	public void insertGeography(Geography geography) throws DAOException {
		//logger.info("Started...");
		PreparedStatement ps = null;
		ResultSet rs = null;
		String SQL="";

 		int rowsUpdated = 0;
		try {
			String fields= getFieldsName(geography.getLevel());
			if(fields!=null){
			SQL="INSERT INTO "+getTableName(geography.getLevel())+" ("+ fields +") VALUES ( nextval for "+getSeqName(geography.getLevel())+", ?, ?, ?, ?, ?, ?,?,?)";
			//System.out.println("SQL----------------"+SQL);
			ps = connection.prepareStatement(SQL);
			int paramCount = 1;
		
			/*
			 * Geography ID is getting generated with Sequence SEQ_VR_CIRCLE_MASTER
			 */
			ps.setString(paramCount++, geography.getGeographyCode());
			ps.setString(paramCount++, geography.getGeographyName());
			ps.setInt(paramCount++, geography.getParentId()) ;
			ps.setString(paramCount++, "A");
			ps.setInt(paramCount++, geography.getCreatedBy());		
			ps.setTimestamp(paramCount++, Utility.getDateTime());
			ps.setInt(paramCount++, geography.getCreatedBy());	
			ps.setTimestamp(paramCount++, Utility.getDateTime());
			
			//System.out.println( "geography.getParentId()----------"+geography.getParentId());
			rowsUpdated = ps.executeUpdate();
			//logger.info(" Row insertion successful on table: Inserted:"
				//	+ rowsUpdated + " rows");
			}
		} catch (SQLException e) {
			logger.fatal(
					"insertGeography : SQL Exception occured while inserting."
							+ "Exception Message: ", e);
			throw new DAOException(
					ExceptionCode.Geography.ERROR_GEOGRAPHY_ALREADY_DEFINED);
		} finally {
			/* Close the resultset, statement. */
			DBConnectionManager.releaseResources(ps, rs);

		}
		//logger.info("Executed .... ");

	}
	public ArrayList getAllGeographys(Integer level) throws DAOException {
		
		//logger.info("Started...");
		PreparedStatement ps = null;
		ResultSet rs = null;
		String SQL="";
		ArrayList<Geography> results = new ArrayList<Geography>();
		try {
			//logger.info(" Before Connection");
			String fields= getFieldsName(level);
			if(fields!=null){
			SQL ="SELECT "+fields+" from " + getTableName(level)+" ORDER BY "+ getGeoField("NAME",level);
			ps = connection.prepareStatement(SQL);
			rs = ps.executeQuery();
		
			while (rs.next()) {
				results.add(populateDto(rs));
			}
			//logger.info(" Number of Geographys found = " + results.size());
			}
			if (0 == results.size()) {
				logger.error(" No Geographys found  .");
				throw new DAOException(
						ExceptionCode.Geography.ERROR_GEOGRAPHY_NOT_EXIST);
			}
			//logger.info("Executed .... ");
			
			return results;
		} catch (SQLException e) {

			logger.error("SQL Exception occured while select."
					+ "Exception Message: ", e);
			throw new DAOException(ExceptionCode.Geography.ERROR_GEOGRAPHY_NOT_EXIST);
		} finally {
			/* Close the resultset, statement. */
			DBConnectionManager.releaseResources(ps, rs);

		}
	}
	public Geography getGeography(int geographyId,Integer level) throws DAOException {
		//logger.info("Started...");
		PreparedStatement ps = null;
		ResultSet rs = null;
		String sql ="";
		try {
			String fields= getFieldsName(level);
			if(fields!=null){
			sql="SELECT "+fields+" FROM "+getTableName(level) +" WHERE "+ getGeoField("ID",level)+" = ?";
			ps = connection.prepareStatement(sql);
			ps.setInt(1, geographyId);
			rs = ps.executeQuery();
			if (rs.next()) {
				return populateDto(rs);
			}
			}
			//logger.info("Executed .... ");
			return null;
		} catch (SQLException sqle) {
			logger.error(" SQL Exception occured while find."
					+ "Exception Message: " + sqle.getMessage(), sqle);
			throw new DAOException(ExceptionCode.Geography.ERROR_GEOGRAPHY_NOT_FOUND);
		} finally {
			/* Close the resultset, statement. */
			DBConnectionManager.releaseResources(ps, rs);

		}

	}

	public Geography getGeography(String geographyName,Integer level) throws DAOException {
		//logger.info("Started...");
		PreparedStatement ps = null;
		ResultSet rs = null;
		String sql ="";
		try {
			String fields= getFieldsName(level);
			if(fields!=null){
			sql="SELECT "+fields+" FROM "+getTableName(level) +" WHERE "+ getGeoField("NAME",level)+" = ?";
			ps = connection.prepareStatement(sql);
			ps.setString(1, geographyName);
			rs = ps.executeQuery();
			if (rs.next()) {
				return populateDto(rs);
			}
			//logger.info("Executed .... ");
			}
			return null;
		} catch (SQLException sqle) {
			logger.error(" SQL Exception occured while find."
					+ "Exception Message: " + sqle.getMessage(), sqle);
			throw new DAOException(ExceptionCode.Geography.ERROR_GEOGRAPHY_NOT_FOUND);
		} finally {
			/* Close the resultset, statement. */
			DBConnectionManager.releaseResources(ps, rs);

		}

	}
	public void updateGeography(Geography geography) throws DAOException {
		//logger.info("Started..updateGeography");
		PreparedStatement ps = null;
		int numRows = -1;
		Integer level =geography.getLevel();
		String SQL_GEOGRAPHY_UPDATE = "UPDATE "+getTableName(level) +" SET "+ getGeoField("ID",level) +"= ?,  "+ getGeoField("CODE",level) +"= ?, "+ getGeoField("NAME",level) +" = ?,  "+ getGeoField("STATUS",level) +" = ?,  "+ getGeoField("UPDATED_BY",level) +" = ?,  "+ getGeoField("PARENT_ID",level) +" = ?, "+ getGeoField("UPDATED_DT",level) +" = ?  WHERE  "+ getGeoField("ID",level) +" = ?";
		//System.out.println("SQL_GEOGRAPHY_UPDATE       "+SQL_GEOGRAPHY_UPDATE);
		try {

			ps = connection.prepareStatement(SQL_GEOGRAPHY_UPDATE);
			int paramCount = 1;
			ps.setInt(paramCount++, geography.getGeographyId());
			ps.setString(paramCount++, geography.getGeographyCode());
			ps.setString(paramCount++, geography.getGeographyName());
			ps.setString(paramCount++, geography.getStatus());		
			ps.setInt(paramCount++, geography.getUpdatedBy());		
			ps.setInt(paramCount++, geography.getParentId());
			ps.setTimestamp(paramCount++, Utility.getDateTime());
			ps.setInt(paramCount, geography.getGeographyId());

			numRows = ps.executeUpdate();

			logger
					.info("Update successful on DPP_GEOGRAPHY_MASTER table:  Updated:"
							+ numRows + " rows");

		} catch (SQLException e) {
			logger.error("SQL Exception occured while update."
					+ "Exception Message: ", e);
			throw new DAOException(ExceptionCode.Geography.ERROR_GEOGRAPHY_ID_IN_USE);
		} finally {
			/* Close the resultset, statement. */
			DBConnectionManager.releaseResources(ps, null);
		}
		//logger.info("Executed .... ");
	}

	public ArrayList getAllGeographys(ReportInputs inputDto, int lb, int ub,Integer level)
	throws DAOException {

	//logger.info("Started...");
	
	PreparedStatement ps = null;
	ResultSet rs = null;
	int paramCount = 1;
	ArrayList<Geography> results = new ArrayList<Geography>();
	try {
		level= inputDto.getLevel();
		//System.out.println("level--------------------"+level);
		String fields= getFieldsName(level);
		//logger.info(" Before Connection");
		if(fields!=null){
		StringBuilder query = new StringBuilder(
				"SELECT * from (select a.*,ROW_NUMBER() over() rnum FROM (");
		query.append("SELECT "+fields+" , COUNT("+ getGeoField("ID",level) +")over() RECORD_COUNT  from  " + getTableName(level) +" where "+ getGeoField("STATUS",level) + " =? ");
		
			
	/*	if (inputDto.getCircleId() != Constants.ADMIN_CIRCLE_ID) {
			query.append(" AND CLE.CIRCLE_ID = ? ");
		}*/
	
		if (inputDto.getStartDt() != null
				&& !inputDto.getStartDt().equals("")) {
			query.append(" AND DATE("+ getGeoField("CREATED_DT",level) +") >= ? ");
		}
	
		if (inputDto.getEndDt() != null && !inputDto.getEndDt().equals("")) {
			query.append(" AND DATE("+ getGeoField("CREATED_DT",level) +") <= ? ");
		}
		query
				.append(" ORDER BY "+ getGeoField("NAME",level) +")a )b where rnum <=? and rnum >=?");
		
		
		ps = connection.prepareStatement(query.toString());
		//logger.info("Query after changes=" + query.toString());
	//System.out.println("Query after changes=" + query.toString());
		if (inputDto.getStatus() != null
				&& !inputDto.getStatus().equals("")) {
			ps.setString(paramCount++, inputDto.getStatus());
	
		} else {
			throw new DAOException(
					ExceptionCode.Report.ERROR_STATAUS_REQUIRED);
		}
/*		
		if (inputDto.getCircleId() != Constants.ADMIN_CIRCLE_ID) {
			ps.setInt(paramCount++,inputDto.getCircleId());
		}
		*/
		
		if (inputDto.getStartDt() != null
				&& !inputDto.getStartDt().equals("")) {
		
			Date date = Utility.str2date(inputDto.getStartDt());
			ps.setDate(paramCount++, new java.sql.Date(date.getTime()));
		}
		if (inputDto.getEndDt() != null && !inputDto.getEndDt().equals("")) {
		
			Date date = Utility.str2date(inputDto.getEndDt());
			ps.setDate(paramCount++, new java.sql.Date(date.getTime()));
		}
		ps.setInt(paramCount++, ub);
		ps.setInt(paramCount++, lb + 1);
		//logger.info("check lb=" + lb);
		//logger.info("check ub=" + ub);
		rs = ps.executeQuery();
		
		while (rs.next()) {
			Geography geography = new Geography();
		
			geography.setGeographyId(rs.getInt(1));
			geography.setGeographyCode(rs.getString(2));
			geography.setGeographyName(rs.getString(3));
			geography.setRegionId(rs.getInt(4));
			geography.setStatus(rs.getString(5));
			
		    geography.setCreatedBy(rs.getInt(6));
			geography.setCreatedDt(rs.getTimestamp(7));
			geography.setUpdatedBy(rs.getInt(8));
			geography.setUpdatedDt(rs.getTimestamp(9));
			geography.setRowNum(rs.getString("RNUM"));
			geography.setTotalRecords(rs.getInt("RECORD_COUNT"));
			results.add(geography);
			// results.add(populateDto(rs));
		}
		}
		//logger.info(" Number of Geographys found = " + results.size());
		if (0 == results.size()) {
			logger.error(" No Geographys found  .");
			throw new DAOException(
					ExceptionCode.Geography.ERROR_GEOGRAPHY_NOT_EXIST);
		}
		//logger.info("Executed .... ");
		return results;
	} catch (SQLException e) {
	
		logger.error("SQL Exception occured while select."
				+ "Exception Message: ", e);
		throw new DAOException(ExceptionCode.Geography.ERROR_GEOGRAPHY_NOT_EXIST);
	} finally {
		/* Close the resultset, statement. */
		DBConnectionManager.releaseResources(ps, rs);
	
	}

}

}