/**
 * 
 */
package com.ibm.virtualization.ussdactivationweb.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import org.apache.log4j.Logger;
import com.ibm.virtualization.ussdactivationweb.beans.RegistrationOfAllBean;

import com.ibm.virtualization.ussdactivationweb.daoInterfaces.BusinessUserInterface;
import com.ibm.virtualization.ussdactivationweb.utils.DAOException;
import com.ibm.virtualization.ussdactivationweb.utils.DBConnection;
import com.ibm.virtualization.ussdactivationweb.utils.DBConnectionUtil;
import com.ibm.virtualization.ussdactivationweb.utils.SQLConstants;

/**
 * 
 * @author abhipsa
 * 
 */
public class RegisteredNumbersDAO {

	private static final Logger logger = Logger
			.getLogger(RegisteredNumbersDAO.class);

	/**
	 * Method used to insert the registered number.
	 * 
	 * @param dRegBean :
	 *            RegistrationOfAllBean
	 * @param intUserId :
	 *            int
	 * @param typeOfUser :
	 *            int
	 * @return boolean value
	 * @throws DAOException
	 */
	public boolean insert(RegistrationOfAllBean bean, int intUserId)
			throws DAOException {
		logger.debug("Entering insert() method of RegisteredNumbersDAO.");
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet resSet = null;
		boolean status = false;
		try {
			con = DBConnection
					.getDBConnection(SQLConstants.DISTPORTAL_JNDI_NAME);
			logger.debug("From DAO Connection obtained.");
			pstmt = con
					.prepareStatement(BusinessUserInterface.SELECT_REG_FROMALL_DIST_DEAL_FOS);
			pstmt.setString(1, bean.getRegNumber());
			resSet = pstmt.executeQuery();
			if (resSet.next()) {
				if (resSet.getInt(1) == 0) {

					pstmt = con
							.prepareStatement(BusinessUserInterface.INSERT_STMT_DIST);
					pstmt.setString(1, bean.getRegNumber());
					pstmt
							.setInt(2, Integer.parseInt(bean
									.getBussinessUserId()));
					pstmt.setString(3, "A");
					pstmt.setInt(4, intUserId);
					pstmt.setInt(5, intUserId);
					pstmt.executeUpdate();
					status = true;
				}
			}

		} catch (SQLException e) {
			e.printStackTrace();
			throw new DAOException(
					"Exception occurs while inserting Regstered Number data in the database.");
		} finally {
			try {
				DBConnectionUtil.closeDBResources(con, pstmt, resSet);
			} catch (SQLException e) {
				throw new DAOException(
						"Exception in closing database resources in class RegistrationDAO.",
						e);
			}
		}
		logger.debug("Exiting insert() method of RegisteredNumbersDAO.");
		return status;
	}

	/**
	 * Method gets the list of registered number.
	 * 
	 * @param intDealerId :
	 *            int
	 * @return object of list.
	 * @throws DAOException
	 */
	public List getRegisteredNumbers(int intId, int typeOfUser)
			throws DAOException {
		logger
				.debug("Entering getRegisteredNumbers() method of RegisteredNumbersDAO.");
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet resSet = null;
		List numberList = new ArrayList();
		try {
			con = DBConnection
					.getDBConnection(SQLConstants.DISTPORTAL_JNDI_NAME);
			logger.debug("From DAO Connection obtained");
			pstmt = con
					.prepareStatement(BusinessUserInterface.VIEW_REG_NOS_All_Business_User);
			pstmt.setInt(1, intId);
			resSet = pstmt.executeQuery();
			while (resSet.next()) {
				RegistrationOfAllBean regbean = new RegistrationOfAllBean();
				regbean.setBussinessUserId(String.valueOf(resSet
						.getInt("BUS_USER_ID")));
				regbean.setRegNumber(resSet.getString("MOBILE_NO"));
				regbean.setRegNumberId(String.valueOf(resSet
						.getInt("MOBILE_ID")));
				numberList.add(regbean);
			}
		} catch (SQLException e) {
			throw new DAOException(
					"Error occurs while reading data in the database.");
		} finally {
			try {
				DBConnectionUtil.closeDBResources(con, pstmt, resSet);
			} catch (SQLException e) {
				throw new DAOException(
						"Exception in closing database resources in class DealerRegistrationDAO.",
						e);
			}
		}
		logger
				.debug("Exiting getRegisteredNumbers() method of RegisteredNumbersDAO.");
		return numberList;
	}

	/**
	 * Method used to get the registered numbers of a user by Mobileid.
	 * 
	 * @param intMobileId :
	 *            int
	 * @return object of dealer reg number bean.
	 * @throws DAOException
	 */
	public RegistrationOfAllBean getRegisteredSingleNumber(int intMobileId,
			int typeOfUser) throws DAOException {
		logger
				.debug("Entering getRegisteredSingleNumber() method of RegisteredNumbersDAO.");
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet resSet = null;
		RegistrationOfAllBean bean = new RegistrationOfAllBean();
		try {
			con = DBConnection
					.getDBConnection(SQLConstants.DISTPORTAL_JNDI_NAME);
			logger.debug("From DAO Connection obtained");
			pstmt = con
					.prepareStatement(BusinessUserInterface.VIEW_SINGLE_REG_NO_Business_User);
			pstmt.setInt(1, intMobileId);
			resSet = pstmt.executeQuery();
			if (resSet.next()) {
				bean.setRegNumber(resSet.getString("MOBILE_NO"));
				bean.setBussinessUserId(String.valueOf(resSet
						.getInt("BUS_USER_ID")));
				bean.setCircleCode(resSet.getString("CIRCLE_CODE"));
				bean.setRegNumberId(resSet.getString("MOBILE_ID"));
				bean.setStatus(resSet.getString("STATUS"));
			}

		} catch (SQLException e) {
			throw new DAOException(
					"Error occurs while reading data in the database.");
		} finally {
			try {
				DBConnectionUtil.closeDBResources(con, pstmt, resSet);
			} catch (SQLException e) {
				throw new DAOException(
						"Exception in closing database resources in class DealerRegistrationDAO.",
						e);
			}
		}
		logger
				.debug("Exiting getRegisteredSingleNumber() method of RegisteredNumbersDAO.");
		return bean;
	}

	/**
	 * Method updates the registered number.
	 * 
	 * @param bean :
	 *            RegistrationOfAllBean
	 * @param intUserId :
	 *            int
	 * @param bolFlag :
	 *            boolean
	 * @return boolean value
	 * @throws DAOException
	 */
	public boolean update(RegistrationOfAllBean bean, int intUserId,
			boolean bolFlag) throws DAOException {
		logger.debug("Entering update() method of RegisteredNumbersDAO.");
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet resSet = null;
		boolean status = false;
		try {
			conn = DBConnection
					.getDBConnection(SQLConstants.DISTPORTAL_JNDI_NAME);
			if (bolFlag) {
				pstmt = conn
						.prepareStatement(BusinessUserInterface.UPDATE_DIST);
				pstmt.setString(1, bean.getRegNumber());
				pstmt.setString(2, String.valueOf(intUserId));
				pstmt.setString(3, bean.getStatus());
				pstmt.setString(4, bean.getRegNumberId());
				pstmt.executeUpdate();
				status = true;
			} else {
				pstmt = conn
						.prepareStatement(BusinessUserInterface.SELECT_REG_CHECK);
				pstmt.setString(1, bean.getRegNumber());
				resSet = pstmt.executeQuery();
				if (resSet.next()) {
					status = false;
				} else {
					pstmt = conn
							.prepareStatement(BusinessUserInterface.UPDATE_DIST);
					pstmt.setString(1, bean.getRegNumber());
					pstmt.setString(2, String.valueOf(intUserId));
					pstmt.setString(3, bean.getStatus());
					pstmt.setString(4, bean.getRegNumberId());
					pstmt.executeUpdate();
					status = true;
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
			throw new DAOException(
					"Error occurs while updating Regstered Number data in the database.",
					e);
		} finally {
			try {
				DBConnectionUtil.closeDBResources(conn, pstmt, resSet);
			} catch (SQLException e) {
				throw new DAOException(
						"Exception in closing database resources in class RegistrationDAO.",
						e);
			}
		}
		logger.debug("Exiting update() method of RegisteredNumbersDAO.");
		return status;
	}
}
