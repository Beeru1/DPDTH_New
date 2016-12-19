package com.ibm.dp.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import org.apache.log4j.Logger;

import com.ibm.dp.common.DBQueries;
import com.ibm.dp.dao.DPCreateBeatDao;
import com.ibm.dp.dto.DPCreateBeatDto;
import com.ibm.virtualization.recharge.common.Constants;
import com.ibm.virtualization.recharge.common.ExceptionCode;
import com.ibm.virtualization.recharge.common.Utility;
import com.ibm.virtualization.recharge.dao.rdbms.BaseDaoRdbms;
import com.ibm.virtualization.recharge.db.DBConnectionManager;
import com.ibm.virtualization.recharge.exception.DAOException;
import com.ibm.virtualization.recharge.exception.VirtualizationServiceException;

public class DPCreateBeatDaoImpl extends BaseDaoRdbms implements DPCreateBeatDao{

	private Logger logger = Logger.getLogger(DPCreateBeatDaoImpl.class.getName());
	
	protected static final String SQL_INSERT_BEAT_KEY = "SQL_INSERT_BEAT";
	protected static final String SQL_INSERT_BEAT = DBQueries.SQL_INSERT_BEAT;
	
	protected static final String SQL_SELECT_CITY_ALL_KEY = "SQL_SELECT_CITY_ALL";
	protected static final String SQL_SELECT_CITY_ALL = DBQueries.SQL_SELECT_CITY_ALL;
	
	protected static final String SQL_IS_BEAT_EXIST_KEY = "SQL_IS_BEAT_EXIST";
	protected static final String SQL_IS_BEAT_EXIST = DBQueries.SQL_IS_BEAT_EXIST;
	
	protected static final String SQL_SELECT_BEATS_VIEW_KEY = "SQL_SELECT_BEATS_VIEW";
	protected static final String SQL_SELECT_BEATS_VIEW = DBQueries.SQL_SELECT_BEATS_VIEW;
	
	protected static final String SQL_SELECT_ALL_CIRCLES_KEY = "SQL_SELECT_ALL_CIRCLES";
	protected static final String SQL_SELECT_ALL_CIRCLES = DBQueries.SQL_SELECT_ALL_CIRCLES;
	
	protected static final String SQL_SELECT_CITES_KEY = "SQL_SELECT_CITES";
	protected static final String SQL_SELECT_CITES = DBQueries.SQL_SELECT_CITES;
	
	protected static final String SQL_SELECT_ZONE_KEY = "SQL_SELECT_ZONE";
	protected static final String SQL_SELECT_ZONE = DBQueries.SQL_SELECT_ZONES;
	
	protected static final String SQL_SELECT_BEAT_RECORD_KEY = "SQL_SELECT_BEAT_RECORD";
	protected static final String SQL_SELECT_BEAT_RECORD = DBQueries.SQL_SELECT_BEAT_RECORD;
	
	protected static final String SQL_UPDATE_BEAT_DETAIL_KEY = "SQL_UPDATE_BEAT_DETAIL_KEY";
	protected static final String SQL_UPDATE_BEAT_DETAIL = DBQueries.SQL_UPDATE_BEAT_DETAIL;
	
	
	public DPCreateBeatDaoImpl(Connection connection) {
		super(connection);
		queryMap.put(SQL_INSERT_BEAT_KEY, SQL_INSERT_BEAT);
		queryMap.put(SQL_SELECT_CITY_ALL_KEY, SQL_SELECT_CITY_ALL);
		queryMap.put(SQL_IS_BEAT_EXIST_KEY, SQL_IS_BEAT_EXIST);
		queryMap.put(SQL_SELECT_ALL_CIRCLES_KEY, SQL_SELECT_ALL_CIRCLES);
		queryMap.put(SQL_SELECT_CITES_KEY, SQL_SELECT_CITES);
		queryMap.put(SQL_SELECT_ZONE_KEY, SQL_SELECT_ZONE);
		queryMap.put(SQL_SELECT_BEATS_VIEW_KEY, SQL_SELECT_BEATS_VIEW);
		queryMap.put(SQL_SELECT_BEAT_RECORD_KEY, SQL_SELECT_BEAT_RECORD);
		queryMap.put(SQL_UPDATE_BEAT_DETAIL_KEY, SQL_UPDATE_BEAT_DETAIL);
	}
	
	public void insertBeat(DPCreateBeatDto createBeatDto) throws VirtualizationServiceException{
		PreparedStatement ps = null;
		try{
			ps = connection.prepareStatement(queryMap.get(SQL_INSERT_BEAT_KEY));
			int paramCount = 1;
			ps.setString(paramCount++, createBeatDto.getBeatName());
			ps.setInt(paramCount++, createBeatDto.getCreatedBy());
			ps.setInt(paramCount++, createBeatDto.getAccountCityId());
			ps.setInt(paramCount++, createBeatDto.getUpdatedBy());
			ps.setString(paramCount, createBeatDto.getDescription());
			ps.executeUpdate();
			connection.commit();
		}catch(Exception sql){
			try {
				connection.rollback();
			} catch (SQLException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			sql.printStackTrace();
		}
		finally {
			/* Close the statement,resultset */
			DBConnectionManager.releaseResources(ps, null);
			DBConnectionManager.releaseResources(connection);
		}
	}
	
	public ArrayList getCities() throws VirtualizationServiceException{
		ArrayList<DPCreateBeatDto> cityList = new ArrayList<DPCreateBeatDto>();
		PreparedStatement ps = null;
		ResultSet rs = null;
		try{
			ps = connection.prepareStatement(queryMap.get(SQL_SELECT_CITY_ALL_KEY));
			rs = ps.executeQuery();
			while(rs.next()){
				DPCreateBeatDto createBeatDto = new DPCreateBeatDto();
				createBeatDto.setAccountCityId(rs.getInt("CITY_ID"));
				createBeatDto.setAccountCityName(rs.getString("CITY_NAME"));
				cityList.add(createBeatDto);
			}
		}catch(Exception e){
			e.printStackTrace();
		}finally {
			/* Close the statement,resultset */
			DBConnectionManager.releaseResources(ps, rs);
			DBConnectionManager.releaseResources(connection);
		}
		return cityList;
	}
	
	public int checkBeatNameAlreadyExist(String beatName)
	throws DAOException {
		logger.info(" Started....mobileNo : " + beatName);
		PreparedStatement ps = null;
		ResultSet rs = null;
		int beatCode = -1;
		try {
			ps = connection.prepareStatement(queryMap
			.get(SQL_IS_BEAT_EXIST_KEY));
			ps.setString(1, beatName);
			rs = ps.executeQuery();
		// if mobile no. exist
			if (rs.next()) {
				beatCode = rs.getInt("BEAT_CODE");
				return beatCode;
		}

	} catch (SQLException e) {
		logger.fatal(
			"SQL Exception occured while Checking Entered  Beat Name already Exist   "
					+ "Exception Message: ", e);
	throw new DAOException(ExceptionCode.ERROR_DB_INTERNAL);

		} finally {
		// Close the resultset, statement. 
		DBConnectionManager.releaseResources(ps, rs);
		//DBConnectionManager.releaseResources(connection);
		logger.info("Executed ...ACCOUNT_ID " + beatCode);
		}

	return beatCode;
	}

		public ArrayList getAllBeats(DPCreateBeatDto beat, int lb, int ub) throws VirtualizationServiceException, DAOException {

			logger.info("Started...");

			PreparedStatement ps = null;
			ResultSet rs = null;
			int paramCount = 1;
			try {
				logger.info(" Before Connection");
				StringBuilder query = new StringBuilder(
						"with temp as(select row_number() over() as rnum,");
				query.append(queryMap.get(SQL_SELECT_BEATS_VIEW_KEY));

				if (beat.getCircleId() != Constants.ADMIN_CIRCLE_ID) {
					query.append(" AND circle.CIRCLE_ID = ? ");
				}
				if(beat.getAccountLevel() != null && beat.getAccountLevel().equalsIgnoreCase("6")){
					query.append(" and beat.created_by= ? ");
				}
					
				query
						.append(" and DP_BEAT_MASTER.beat_code = beat.beat_code" +
								" ORDER BY beat_code )SELECT * from temp where rnum between ? and ?");
				ps = connection.prepareStatement(query.toString());
				logger.info("Query after changes=" + query.toString());
				System.out.println("Query after changes=" + query.toString());
				
				ps.setInt(paramCount++,beat.getAccountCityId());
				if (beat.getBeatStatus() != null
						&& !beat.getBeatStatus().equals("")) {
					ps.setString(paramCount++, beat.getBeatStatus());

				} else {
					throw new DAOException(
							ExceptionCode.Report.ERROR_STATAUS_REQUIRED);
				}
				if (beat.getCircleId() != Constants.ADMIN_CIRCLE_ID) {
					ps.setInt(paramCount++,beat.getCircleId());
				}
				if(beat.getAccountLevel() != null && beat.getAccountLevel().equalsIgnoreCase("6")){
					ps.setLong(paramCount++,beat.getLoginId());
				}
				ps.setInt(paramCount++, lb + 1);
				ps.setInt(paramCount++, ub);
				logger.info("check lb=" + lb);
				logger.info("check ub=" + ub);
				rs = ps.executeQuery();
				ArrayList<DPCreateBeatDto> results = new ArrayList<DPCreateBeatDto>();
				while (rs.next()) {
					DPCreateBeatDto beatDto = new DPCreateBeatDto();
					beatDto.setBeatId(rs.getInt("BEAT_CODE"));
					beatDto.setBeatName(rs.getString("BEAT_NAME"));
					beatDto.setCircleId(rs.getInt("CIRCLE_ID"));
					beatDto.setCircleName(rs.getString("CIRCLE_NAME"));
					beatDto.setBeatStatus(rs.getString("STATUS"));
					beatDto.setAccountCityId(rs.getInt("CITY_ID"));
					beatDto.setAccountCityName(rs.getString("CITY_NAME"));
					beatDto.setAccountZoneId(rs.getInt("ZONE_ID"));
					beatDto.setAccountZoneName(rs.getString("ZONE_NAME"));
					beatDto.setDescription(rs.getString("DESCRIPTION"));
					beatDto.setRowNum(rs.getInt("RNUM"));
					results.add(beatDto);
				}
				logger.info(" Number of Circles found = " + results.size());
				if (0 == results.size()) {
					logger.error(" No Circles found  .");
					throw new DAOException(
							ExceptionCode.Account.ERROR_NO_BEAT_EXISTS);
				}
				logger.info("Executed .... ");
				return results;
			} catch (SQLException e) {

				logger.error("SQL Exception occured while select."
						+ "Exception Message: ", e);
				throw new DAOException(ExceptionCode.Account.ERROR_NO_BEAT_EXISTS);
			} finally {
				/* Close the resultset, statement. */
				DBConnectionManager.releaseResources(ps, rs);

			}

		}

		public ArrayList getAllCircles() throws DAOException {
			logger.info("Started...");
			PreparedStatement ps = null;
			ResultSet rs = null;
			ArrayList<DPCreateBeatDto> stateList = new ArrayList<DPCreateBeatDto>();
			DPCreateBeatDto beatDto = null;
			try {

				ps = connection.prepareStatement(queryMap
						.get(SQL_SELECT_ALL_CIRCLES_KEY));
				rs = ps.executeQuery();

				if (rs != null) {
					while (rs.next()) {
						beatDto = new DPCreateBeatDto();
						beatDto.setCircleId(rs.getInt("CIRCLE_ID"));
						beatDto.setCircleName(rs.getString("CIRCLE_NAME"));
						stateList.add(beatDto);
					}
				}

				return stateList;
			} catch (SQLException e) {
				logger.info("SQL Exception occured while getstates."
						+ "Exception Message: ", e);
				throw new DAOException(ExceptionCode.ERROR_STATE_NO_RECORD_FAILED);
			} finally {
				/* Close the resultset, statement. */
				DBConnectionManager.releaseResources(ps, rs);
				logger.info("Executed ....");
			}
		} 
		
		public ArrayList getCites(int zoneId) throws DAOException {

			logger.info("Started...zoneId :" + zoneId);
			PreparedStatement ps = null;
			ResultSet rs = null;
			ArrayList<DPCreateBeatDto> accountCity = new ArrayList<DPCreateBeatDto>();
			DPCreateBeatDto beatDto = null;
			try {

				ps = connection
						.prepareStatement(queryMap.get(SQL_SELECT_CITES_KEY));
				ps.setInt(1, zoneId);
				rs = ps.executeQuery();

				if (rs != null) {
					while (rs.next()) {
						beatDto = new DPCreateBeatDto();
						beatDto.setAccountCityId(rs.getInt("CITY_ID"));
						beatDto.setAccountCityName(rs.getString("CITY_NAME"));
						accountCity.add(beatDto);
					}
				}

				return accountCity;
			} catch (SQLException e) {
				logger.info("SQL Exception occured while getCity."
						+ "Exception Message: ", e);
				throw new DAOException(ExceptionCode.ERROR_CITY_NO_RECORD_FAILED);
			} finally {
				/* Close the resultset, statement. */
				DBConnectionManager.releaseResources(ps, rs);
				logger.info("Executed ....");
			}
		}
		
		public ArrayList getZones(int stateId) throws DAOException {

			logger.info("Started...stateId :" + stateId);
			PreparedStatement ps = null;
			ResultSet rs = null;
			ArrayList<DPCreateBeatDto> accountZone = new ArrayList<DPCreateBeatDto>();
			DPCreateBeatDto beatDto = null;
			try {

				ps = connection.prepareStatement(queryMap.get(SQL_SELECT_ZONE_KEY));
				ps.setInt(1, stateId);
				rs = ps.executeQuery();

				if (rs != null) {
					while (rs.next()) {
						beatDto = new DPCreateBeatDto();
						beatDto.setAccountZoneId(rs.getInt("ZONE_ID"));
						beatDto.setAccountZoneName(rs.getString("ZONE_NAME"));
						accountZone.add(beatDto);
					}
				}

				return accountZone;
			} catch (SQLException e) {
				logger.info("SQL Exception occured while getCity."
						+ "Exception Message: ", e);
				throw new DAOException(ExceptionCode.ERROR_CITY_NO_RECORD_FAILED);
			} finally {
				/* Close the resultset, statement. */
				DBConnectionManager.releaseResources(ps, rs);
				logger.info("Executed ....");
			}
		}
		
		public DPCreateBeatDto getBeatRecord(int beatId)throws DAOException{
			PreparedStatement ps = null;
			ResultSet rs = null;
			DPCreateBeatDto beatDto = null;
			try {

				ps = connection.prepareStatement(queryMap.get(SQL_SELECT_BEAT_RECORD_KEY));
				ps.setInt(1, beatId);
				rs = ps.executeQuery();

				if (rs != null) {
					while (rs.next()) {
						beatDto = new DPCreateBeatDto();
						beatDto.setBeatId(rs.getInt("BEAT_CODE"));
						beatDto.setBeatName(rs.getString("BEAT_NAME"));
						beatDto.setBeatStatus(rs.getString("STATUS"));
						beatDto.setDescription(rs.getString("DESCRIPTION"));
						beatDto.setCreatedBy(rs.getInt("CREATED_BY"));
						beatDto.setCreatedDt(rs.getString("CRAETED_DATE"));
						beatDto.setUpdatedBy(rs.getInt("UPDATED_BY"));
						beatDto.setUpdatedDt(rs.getString("UPDATED_DATE"));
						beatDto.setAccountCityId(rs.getInt("CITY_ID"));
						beatDto.setAccountCityName(rs.getString("CITY_NAME"));
					}
				}
			return beatDto;
			} catch (SQLException e) {
				logger.info("SQL Exception occured while fetching beat record."
						+ "Exception Message: ", e);
				throw new DAOException(ExceptionCode.ERROR_CITY_NO_RECORD_FAILED);
			} finally {
				/* Close the resultset, statement. */
				DBConnectionManager.releaseResources(ps, rs);
				logger.info("Executed ....");
			}
		}
		
		public String updateBeat(DPCreateBeatDto beat) throws DAOException{
			PreparedStatement ps = null;
			String message = "updationSuccess";
			try{
			ps = connection.prepareStatement(queryMap.get(SQL_UPDATE_BEAT_DETAIL_KEY));
			int paramCount = 1;
			ps.setString(paramCount++, beat.getBeatName());
			ps.setString(paramCount++, beat.getDescription());
			ps.setTimestamp(paramCount++, Utility.getDateTime());
			ps.setLong(paramCount++, beat.getUpdatedBy());
			ps.setString(paramCount++, beat.getBeatStatus());
			ps.setInt(paramCount, beat.getBeatId());
			ps.executeUpdate();
			ps.clearParameters();
			connection.commit();
			}catch (SQLException e) {
				try {
					connection.rollback();
				} catch (SQLException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				e.printStackTrace();
				message = "updationFaluire";
				logger.error(
						"SQL Exception occured while updating beat"
								+ "Exception Message: ", e);
				throw new DAOException(
						ExceptionCode.Account.ERROR_BEAT_UPDATE);
			} finally {
				/* Close the statement,resultset */
				DBConnectionManager.releaseResources(ps, null);
			}
			return message;
		}
		
		
		public int getAllBeatsCount(DPCreateBeatDto beat) throws VirtualizationServiceException, DAOException 
		{
			PreparedStatement ps = null;
			ResultSet rs = null;
			int paramCount = 1;
			int Count = 0;
			try 
			{
				StringBuilder query = new StringBuilder("with temp as(select row_number() over() as rnum,");
				query.append(queryMap.get(SQL_SELECT_BEATS_VIEW_KEY));
				if (beat.getCircleId() != Constants.ADMIN_CIRCLE_ID) 
				{
					query.append(" AND circle.CIRCLE_ID = ? ");
				}
				if(beat.getAccountLevel() != null && beat.getAccountLevel().equalsIgnoreCase("6")){
					query.append(" and beat.created_by= ? ");
				}
				query.append(" and DP_BEAT_MASTER.beat_code = beat.beat_code ");
				query.append(" ORDER BY beat_code )SELECT count(*)records from temp ");
				ps = connection.prepareStatement(query.toString());
				logger.info("Query after changes=" + query.toString());
				System.out.println("Query Count =" + query.toString());
				
				ps.setInt(paramCount++,beat.getAccountCityId());
				if (beat.getBeatStatus() != null 	&& !beat.getBeatStatus().equals("")) 
				{
					ps.setString(paramCount++, beat.getBeatStatus());
				} else {
					throw new DAOException(ExceptionCode.Report.ERROR_STATAUS_REQUIRED);
				}
				if (beat.getCircleId() != Constants.ADMIN_CIRCLE_ID) {
					ps.setInt(paramCount++,beat.getCircleId());
				}
				if(beat.getAccountLevel() != null && beat.getAccountLevel().equalsIgnoreCase("6")){
					ps.setLong(paramCount++,beat.getLoginId());
				}
				
				rs = ps.executeQuery();
				
				while (rs.next()) 
				{
					Count = rs.getInt("RECORDS");
				}
				
				logger.info("Executed .... ");
				return Count;
			} catch (SQLException e) {

				logger.error("SQL Exception occured while select."
						+ "Exception Message: ", e);
				throw new DAOException(ExceptionCode.Account.ERROR_NO_BEAT_EXISTS);
			} finally {
				/* Close the resultset, statement. */
				DBConnectionManager.releaseResources(ps, rs);

			}

		}
		
}