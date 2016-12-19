package com.ibm.dp.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

import com.ibm.dp.common.Constants;
import com.ibm.dp.common.DBQueries;
import com.ibm.dp.dao.StockDeclarationDao;
import com.ibm.dp.dto.DistributorDTO;
import com.ibm.dp.dto.DistributorDetailsDTO;
import com.ibm.virtualization.recharge.dao.rdbms.BaseDaoRdbms;
import com.ibm.virtualization.recharge.db.DBConnectionManager;
/**
 * @author Mohammad Aslam
 */
public class StockDeclarationDaoDB2 extends BaseDaoRdbms implements StockDeclarationDao {

	private static Logger logger = Logger.getLogger(StockDeclarationDaoDB2.class.getClass());
	protected static final String SQL_SELECT_DISTRIBUTORS_2 = DBQueries.SQL_SELECT_DISTRIBUTORS_2;
	protected static final String SQL_SELECT_DISTRIBUTORS_5 = DBQueries.SQL_SELECT_DISTRIBUTORS_5;
	protected static final String SQL_SELECT_DISTRIBUTORS_DETAILS_2 = DBQueries.SQL_SELECT_DISTRIBUTORS_DETAILS_2;
	protected static final String SQL_SELECT_DISTRIBUTORS_DETAILS_5 = DBQueries.SQL_SELECT_DISTRIBUTORS_DETAILS_5;
	protected static final String SQL_SELECT_DISTRIBUTORS_DETAILS = DBQueries.SQL_SELECT_DISTRIBUTORS_DETAILS;

	public StockDeclarationDaoDB2(Connection con) {
		super(con);
	}

	public List<DistributorDTO> getDistributors(String accountLavel, long accountId, long circleId) {
		ResultSet rst = null;
		PreparedStatement pst = null;
		List<DistributorDTO> distributorsList = new ArrayList<DistributorDTO>();
		try {
			if (Constants.ACC_LEVEL_CA.equals(accountLavel)) {
				pst = connection.prepareStatement(SQL_SELECT_DISTRIBUTORS_2);
				pst.setLong(1, circleId);
				rst = pst.executeQuery();
				processResultSetToDistributorDTO(rst, distributorsList);
			} else if (Constants.ACC_LEVEL_TSM.equals(accountLavel)) {
				pst = connection.prepareStatement(SQL_SELECT_DISTRIBUTORS_5);
				pst.setLong(1, accountId);
				rst = pst.executeQuery();
				processResultSetToDistributorDTO(rst, distributorsList);
			}
		} catch (SQLException e) {
			logger.error("**-> getDistributors function of  StockDeclarationDaoDB2 " + e);
		} catch (Exception ex) {
			logger.error("**-> getDistributors function of  StockDeclarationDaoDB2 " + ex);
		} finally {
			DBConnectionManager.releaseResources(pst, rst);
		}
		return distributorsList;
	}

	public List<DistributorDetailsDTO> getDistributorDetails(String accountLavel, String distributorId, long circleId, long loginUserID) {
		ResultSet rst = null;
		PreparedStatement pst = null;
		List<DistributorDetailsDTO> distributorDetailsList = new ArrayList<DistributorDetailsDTO>();
		try {
			if ("999".equals(distributorId)) 
			{
				if (Constants.ACC_LEVEL_CA.equals(accountLavel)) 
				{
					pst = connection.prepareStatement(SQL_SELECT_DISTRIBUTORS_DETAILS_2);
					pst.setLong(1, circleId);
					rst = pst.executeQuery();
					processResultSetToDistributorDetailsDTO(rst, distributorDetailsList);
				} 
				else if (Constants.ACC_LEVEL_TSM.equals(accountLavel)) 
				{
					pst = connection.prepareStatement(SQL_SELECT_DISTRIBUTORS_DETAILS_5);
					pst.setLong(1, loginUserID);
					rst = pst.executeQuery();
					processResultSetToDistributorDetailsDTO(rst, distributorDetailsList);
				}
				// Means selected All Distributors, So RETURN after this.
			} 
			else 
			{
				pst = connection.prepareStatement(SQL_SELECT_DISTRIBUTORS_DETAILS);
				pst.setString(1, distributorId);
				pst.setLong(2, circleId);
				rst = pst.executeQuery();
				processResultSetToDistributorDetailsDTO(rst, distributorDetailsList);
				return distributorDetailsList;
			}
		} catch (SQLException e) {
			logger.error("**-> getDistributorDetails function of  StockDeclarationDaoDB2 " + e);
		} catch (Exception ex) {
			logger.error("**-> getDistributorDetails function of  StockDeclarationDaoDB2 " + ex);
		} finally {
			DBConnectionManager.releaseResources(pst, rst);
		}
		return distributorDetailsList;
	}
	
	public void processResultSetToDistributorDTO(ResultSet rst, List<DistributorDTO> distributorsList) throws SQLException {
		boolean addAllDist = true;
		DistributorDTO distributorDTO;
		while (rst.next()) {
			if (addAllDist) {
				addAllDist = false;
				distributorDTO = new DistributorDTO();
				distributorDTO.setAccountId("999");
				distributorDTO.setAccountName("All Distributors");
				distributorsList.add(distributorDTO);
			}
			distributorDTO = new DistributorDTO();
			distributorDTO.setAccountId(rst.getString("ACCOUNT_ID"));
			distributorDTO.setAccountName(rst.getString("ACCOUNT_NAME"));
			distributorsList.add(distributorDTO);
		}
	}

	public void processResultSetToDistributorDetailsDTO(ResultSet rst, List<DistributorDetailsDTO> distributorDetailsList) throws SQLException {
		DistributorDetailsDTO distributorDetailsDTO;
		while (rst.next()) {
			distributorDetailsDTO = new DistributorDetailsDTO();
			distributorDetailsDTO.setDistributorName(rst.getString("ACCOUNT_NAME"));
			distributorDetailsDTO.setProductName(rst.getString("PRODUCT_NAME"));
			distributorDetailsDTO.setClosingStock(rst.getString("CLOSING_STOCK"));
			distributorDetailsDTO.setComments(rst.getString("COMMENTS"));
			distributorDetailsDTO.setCreationDateasTimestamp(rst.getTimestamp("CREATED_DATE"));
			distributorDetailsDTO.setIntDPStock(rst.getInt("DP_STOCK"));
			distributorDetailsDTO.setIntOpenStock(rst.getInt("OPEN_STOCK"));
			
			distributorDetailsList.add(distributorDetailsDTO);
		}
	}	

}