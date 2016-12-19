package com.ibm.hbo.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import org.apache.log4j.Logger;

import com.ibm.virtualization.recharge.db.DBConnectionManager;
import com.ibm.virtualization.recharge.exception.DAOException;
import com.ibm.dp.common.DBQueries;
import com.ibm.hbo.dao.HBOSendEmailDao;
import com.ibm.hbo.dto.HBOSendEmailDto;
import com.ibm.hbo.exception.HBOException;

//import com.ibm.virtualization.recharge.dao.rdbms.BaseDaoRdbms;

public class HBOSendEmailDaoImpl implements HBOSendEmailDao {

	//	private final static String SQL_SELECT_SUBJECTLIST_KEY = "SQL_SELECT_SUBJECTLIST";

	private final static String SQL_SELECT_SUBJECTLIST = DBQueries.SQL_SELECT_SUBJECTLIST;

	//	private final static String SQL_PARENT_EMAIL_ID_KEY = "SQL_PARENT_EMAIL_ID";

	private final static String SQL_PARENT_EMAIL_ID = DBQueries.SQL_PARENT_EMAIL_ID;

	//	public HBOSendEmailDaoImpl(Connection connection) {
	//		super(connection);
	//		queryMap.put(SQL_SELECT_SUBJECTLIST_KEY, SQL_SELECT_SUBJECTLIST);
	//		queryMap.put(SQL_PARENT_EMAIL_ID_KEY, SQL_PARENT_EMAIL_ID);
	//		// TODO Auto-generated constructor stub
	//	}

	public static Logger logger = Logger.getLogger(HBOSendEmailDaoImpl.class);

	public ArrayList getSubjectList() throws HBOException, DAOException {
		Connection connection = null;
		connection = DBConnectionManager.getDBConnection();
		PreparedStatement ps = null;
		ResultSet rs = null;
		HBOSendEmailDto sendMail = null;
		ArrayList<HBOSendEmailDto> subjectList = new ArrayList<HBOSendEmailDto>();
		try {
			//	ps = connection.prepareStatement(queryMap.get(SQL_SELECT_SUBJECTLIST_KEY));
			ps = connection.prepareStatement(SQL_SELECT_SUBJECTLIST);
			rs = ps.executeQuery();
			while (rs.next()) {
				sendMail = new HBOSendEmailDto();
				sendMail.setSubjectId(rs.getString("SUBJECT_ID"));
				sendMail.setSubject(rs.getString("SUBJECT_TITLE"));
				subjectList.add(sendMail);
			}

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				try {
					if (rs != null)
						rs.close();
				} catch (SQLException sqle) {
				}
				try {
					if (ps != null)
						ps.close();
				} catch (SQLException sqle) {
				}
				connection.close();
			} catch (Exception e) {
				logger.info("Exception in from Subject List>> " + e);
				e.printStackTrace();
			}
		}

		// TODO Auto-generated method stub
		return subjectList;
	}

	public String getTSMEmailId(long loginId) throws HBOException, DAOException {
		Connection connection = null;
		connection = DBConnectionManager.getDBConnection();
		PreparedStatement ps = null;
		ResultSet rs = null;
		HBOSendEmailDto sendMail = null;
		String emailId = null;
		try {
			//	ps = connection.prepareStatement(queryMap.get(SQL_SELECT_SUBJECTLIST_KEY));
			ps = connection.prepareStatement(SQL_PARENT_EMAIL_ID);
			ps.setLong(1, loginId);
			rs = ps.executeQuery();
			while (rs.next()) {
				sendMail = new HBOSendEmailDto();
				sendMail.setReceiverId(rs.getString("EMAIL"));
				emailId = rs.getString("CONTACT_NAME")+"@"+rs.getString("EMAIL");
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				try {
					if (rs != null)
						rs.close();
				} catch (SQLException sqle) {
				}
				try {
					if (ps != null)
						ps.close();
				} catch (SQLException sqle) {
				}
				connection.close();
			} catch (Exception e) {
				logger.info("Exception in from TSM EmailId>> " + e);
				e.printStackTrace();
			}
		}
		// TODO Auto-generated method stub
		return emailId;
	}
}