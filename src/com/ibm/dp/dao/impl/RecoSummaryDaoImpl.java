package com.ibm.dp.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.apache.log4j.Logger;

import com.ibm.dp.dao.RecoSummaryDao;
import com.ibm.dp.dto.RecoSummaryDTO;
import com.ibm.virtualization.recharge.dao.rdbms.BaseDaoRdbms;
import com.ibm.virtualization.recharge.db.DBConnectionManager;
import com.ibm.virtualization.recharge.exception.DAOException;
import com.ibm.virtualization.ussdactivationweb.services.dto.CircleDTO;

public class RecoSummaryDaoImpl extends BaseDaoRdbms implements RecoSummaryDao {

public static Logger logger = Logger.getLogger(RecoSummaryDaoImpl.class.getName());
	
private static final String GET_CIRCLES = "SELECT CIRCLE_ID, CIRCLE_NAME FROM VR_CIRCLE_MASTER WITH UR "; 

	public RecoSummaryDaoImpl(Connection connection) 
	{
		super(connection);
	}
	
	
public List<CircleDTO> getCircleList() throws DAOException  {
		
		List<CircleDTO> circleList = new ArrayList<CircleDTO>();

		PreparedStatement ps = null;
		Connection db2conn = null; 
		ResultSet rs = null; 
		
		try {
			db2conn = DBConnectionManager.getDBConnection();

			ps = db2conn.prepareStatement(GET_CIRCLES);
			
			rs = ps.executeQuery();

			CircleDTO circleDTO = null;
			while (rs.next()) {
				circleDTO = new CircleDTO();
				circleDTO.setCircleId(rs.getInt("CIRCLE_ID"));
				circleDTO.setCircleName(rs.getString("CIRCLE_NAME"));
				circleList.add(circleDTO);
			}

			System.out.println("Circle List---------: " + circleList);
		} catch (SQLException e) {
			e.printStackTrace();
			logger.error("SQL Exception occured while getting List of Circle. Exception message: "
					+ e.getMessage());
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("Exception occured while getting List of Circle. Exception message: "
					+ e.getMessage());
		} finally {
			try {
				if (rs != null)
					rs.close();
				if (ps != null)
					ps.close();
				if (db2conn != null) {
					db2conn.close();
				}
			} catch (Exception e) {
			}
		}
		return circleList;
	}
	
public ArrayList<RecoSummaryDTO> getReport( ArrayList<Integer> circleIdArrList, String status, int recoID) throws DAOException{
	
		ArrayList<RecoSummaryDTO> reportList = new ArrayList<RecoSummaryDTO>();

		PreparedStatement ps = null;
		Connection db2conn = null;
		ResultSet rs = null;
		StringBuffer sql = null;
		
		Integer recoStatus = Integer.parseInt(status);
		System.out.println("Reco Status is ---------------------------------: " + recoStatus);

		if(recoStatus == 1){
			status = "INITIATE";
		}else if(recoStatus == 2){
			status = "SUCCESS";
		}
		System.out.println("Reco Status is ---------------------------------: " + status);
		
		StringBuffer circle = new StringBuffer();
		Iterator it = circleIdArrList.iterator();
		while(it.hasNext())
		{
			Integer in = (Integer)it.next();
			circle = circle.append(in);
			circle.append(",");	
			
		}
		
		circle.deleteCharAt(circle.length() - 1);
			
		try {
			connection = DBConnectionManager.getDBConnection();

				String query = "SELECT (SELECT ACCOUNT_NAME FROM VR_ACCOUNT_DETAILS WHERE ACCOUNT_ID=RD.DIST_ID) AS DIST_NAME, (SELECT LOGIN_NAME FROM VR_LOGIN_MASTER WHERE LOGIN_ID=RD.DIST_ID) AS DICT_OLM_ID, " +
						" RP.FROM_DATE AS RECO_START_DATE, RP.TO_DATE AS RECO_END_DATE, CASE RECO_STATUS WHEN 'INITIATE' THEN 'PENDING' ELSE 'COMPLETED' END AS RECO_STATUS, " +
						" (SELECT PRODUCT_NAME FROM DP_PRODUCT_MASTER WHERE PRODUCT_ID=RD.PRODUCT_ID) AS PRODUCT_NAME FROM DP_RECO_DIST_DETAILS RD, " +
						" DP_RECO_PERIOD RP WHERE RD.RECO_ID=RP.ID AND RD.CREATED_BY=RD.DIST_ID AND CIRCLE_ID IN ( "+circle.toString()+" )"; 
				
				
				if(recoID!=-1) {		
										
					query+= " AND RD.RECO_ID IN(" + recoID +")";				
					
				}	
				sql = new StringBuffer(query);
				if(recoStatus != 0) {
					System.out.println("Hereeeeeeeeeee I Am..............!!!");
					sql.append(" AND RECO_STATUS = ? ");
				}

				int index = 1;
				ps = connection.prepareStatement(sql.toString());
				
				if(recoStatus != 0) {
					ps.setString(index++, status);
				}

				System.out.println(sql.toString());
			rs = ps.executeQuery();
			RecoSummaryDTO recoSummaryDTO = null;
			while (rs.next()) {
				recoSummaryDTO = new RecoSummaryDTO();
				
				recoSummaryDTO.setDistCode(rs.getString("DICT_OLM_ID"));
				recoSummaryDTO.setDistName(rs.getString("DIST_NAME"));
				recoSummaryDTO.setRecoStartDate(rs.getDate("RECO_START_DATE"));
				recoSummaryDTO.setRecoEndDate(rs.getDate("RECO_END_DATE"));
				recoSummaryDTO.setProductName(rs.getString("PRODUCT_NAME"));
				recoSummaryDTO.setRecoStatus(rs.getString("RECO_STATUS"));				

				reportList.add(recoSummaryDTO);
			}
		} catch (SQLException e) {
			e.printStackTrace();
			logger
					.error("SQL Exception occured while getting Report. Exception message: "
							+ e.getMessage());
		} catch (Exception e) {
			e.printStackTrace();
			logger
					.error("Exception occured while getting Report. Exception message: "
							+ e.getMessage());
		} finally {
			try {
				if (rs != null)
					rs.close();
				if (ps != null)
					ps.close();
				if (db2conn != null) {
					db2conn.close();
				}
			} catch (Exception e) {
			}
		}
		return reportList;
	}	
}
