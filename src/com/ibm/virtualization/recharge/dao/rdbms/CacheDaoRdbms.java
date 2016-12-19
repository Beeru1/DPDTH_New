/*****************************************************************************\
 **
 ** Virtualization - Recharge.
 **
 ** Copyright (c) 2007-2008 IBM.
 ** All Rights Reserved
 **
 **
 \****************************************************************************/
package com.ibm.virtualization.recharge.dao.rdbms;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;

import org.apache.log4j.Logger;

import com.ibm.virtualization.framework.bean.ChannelType;
import com.ibm.virtualization.framework.bean.ConnectionBean;
import com.ibm.virtualization.recharge.dto.AuthenticationKeyInCache;
import com.ibm.virtualization.recharge.dto.MobileNumberSeries;
import com.ibm.virtualization.recharge.dto.Role;
import com.ibm.virtualization.recharge.dto.InMapping;
import com.ibm.virtualization.recharge.dto.MessageDetail;
import com.ibm.virtualization.recharge.dto.SysConfig;
import com.ibm.virtualization.recharge.common.Constants;
import com.ibm.virtualization.recharge.common.ExceptionCode;
import com.ibm.virtualization.recharge.common.RecieverType;
import com.ibm.virtualization.recharge.common.Utility;
import com.ibm.virtualization.recharge.dao.CacheDao;
import com.ibm.virtualization.recharge.db.DBConnectionManager;
import com.ibm.virtualization.recharge.dto.Circle;
import com.ibm.virtualization.recharge.dto.CircleTopupConfig;
import com.ibm.virtualization.recharge.dto.ReportInputs;
import com.ibm.virtualization.recharge.exception.DAOException;

/**
 * This class provides the implementation for all the method declarations in
 * CircleDao interface
 * 
 * @author Paras
 * 
 * 
 */
public class CacheDaoRdbms implements CacheDao {
	/*
	 * Logger for this class.
	 */
	private static Logger logger = Logger.getLogger(CacheDaoRdbms.class
			.getName());

	/*
	 * SQL Used within DaoImpl
	 */

	protected static final String SQL_SELECT_GROUP_ROLE_MAPPING = "SELECT GRP.GROUP_ID ,ROLE.ROLE_ID,ROLE.ROLE_NAME ,ROLE.CHANNEL_TYPE FROM VR_ROLE_MASTER ROLE,VR_GROUP_ROLE_MAPPING GRPROLE,VR_GROUP_DETAILS GRP WHERE ROLE.ROLE_ID = GRPROLE.ROLE_ID AND GRP.GROUP_ID=GRPROLE.GROUP_ID ORDER BY GRP.GROUP_ID,ROLE.CHANNEL_TYPE";

	protected static final String SQL_SELECT_TOP_UP_CONFIG = "SELECT TOPUP_CONFIG_ID,CIRCLE_ID,UPPER(TRANSACTION_TYPE) as TRANSACTION_TYPE ,START_AMOUNT,TILL_AMOUNT,ESP_COMMISSION,PSP_COMMISSION,SERVICE_TAX,PROCESSING_FEE,IN_CARD_GROUP FROM VR_CIRCLE_TOPUP_CONFIG WHERE STATUS ='A' order by CIRCLE_ID,TRANSACTION_TYPE";

	protected static final String SQL_SELECT_SYSTEM_CONFIG = "SELECT CIRCLE_ID, UPPER(TRANSACTION_TYPE) as TRANSACTION_TYPE ,UPPER(CHANNEL) as CHANNEL ,MIN_AMOUNT,MAX_AMOUNT FROM VR_SYSTEM_CONFIG ORDER BY CIRCLE_ID,TRANSACTION_TYPE";

	protected static final String SQL_SELECT_IN_MAPPING = "SELECT CIRCLE.WIRELESS_CODE,CIRCLE.WIRELINE_CODE,CIRCLE.DTH_CODE,CIRCLE.CIRCLE_CODE,CONFIG.EXTERNAL_ID,CONFIG.EXTERNAL_INT_TYPE,SERIES.CIRCLE_ID,CONFIG.EXTERNAL_URL,SERIES.PRIMARY_ID,SERIES.SECONDARY_ID FROM VR_MOBILE_SERIES_CONFIG SERIES,VR_MOBILE_SERIES_IN_MAPPING MAPPING,VR_EXTERNAL_CONFIG_MSTR CONFIG,VR_CIRCLE_MASTER CIRCLE WHERE SERIES.MOBILE_SERIES_ID=MAPPING.MOBILE_SERIES_ID AND MAPPING.EXTERNAL_ID=CONFIG.EXTERNAL_ID AND CONFIG.EXTERNAL_INT_TYPE=2 AND SERIES.CIRCLE_ID=CIRCLE.CIRCLE_ID";

	protected static final String SQL_SELECT_MSG_DETAILS = "SELECT MSG_CODE,MESSAGE, RECEIVER_NAME FROM VR_MSG_MSTR ORDER BY MSG_CODE";

	protected static final String SQL_SELECT_NUM_SERIES = "SELECT SERIES_NO, SERIES_NAME, SERIES_FROM, SERIES_TO, REQ_HOST, REQ_PORT, REQ_CHANNEL, REQ_QMGR, REQ_QNAME, IN_HOST, IN_PORT, IN_CHANNEL, IN_QMGR, IN_QNAME, OUT_HOST, OUT_PORT, OUT_CHANNEL, OUT_QMGR, OUT_QNAME FROM VR_NUMBER_SERIES";

	protected static final String SQL_SELECT_MOBILE_SERIES_CONFIG = "SELECT MOBILE_SERIES_ID,PRIMARY_ID,SECONDARY_ID FROM VR_MOBILE_SERIES_CONFIG";

	protected static final String SQL_SELECT_GROUP_URL_MAPPING = "select tab1.GROUP_ID GROUPID,LM.LINK_URL URL from (select GROUP_ID, LRM.LINK_ID from VR_GROUP_ROLE_MAPPING GRM inner join  VR_LINK_ROLE_MAPPING LRM on GRM.ROLE_ID = LRM.ROLE_ID ) as tab1 inner join VR_LINK_MASTER LM on tab1.LINK_ID=LM.LINK_ID  order by 1 desc with ur";
	
	protected static final String SQL_SELECT_TOTAL_URLS="SELECT LINK_URL URL FROM VR_LINK_MASTER WHERE LINK_URL is not null and LINK_URL!='' with ur";
	
	protected Connection connection = null;

	/**
	 * Constructor to initialize connection
	 * 
	 * @param connection
	 */
	public CacheDaoRdbms(Connection connection) {
		this.connection = connection;
	}
	public CacheDaoRdbms() {
		
	}

	/**
	 * This method will retrieve all the roles of users, order by group id, and
	 * will return an collection of < groupID id, roleName > , each instance
	 * represented by Role inner class.
	 * 
	 * @return ArrayList
	 */
	public ArrayList getGroupAndRoleInfo() {
		logger.trace("Started...");
		PreparedStatement ps = null;
		ResultSet rs = null;
		ArrayList<Role> userCredentials = new ArrayList<Role>();
		try {
			ps = connection.prepareStatement(SQL_SELECT_GROUP_ROLE_MAPPING);
			rs = ps.executeQuery();
			while (rs.next()) {
				Role role = new Role();
				role.setRoleName(rs.getString("ROLE_NAME"));
				role.setGroupId(rs.getLong("GROUP_ID"));
				role.setChannelType(ChannelType.getChannelType(rs
						.getInt("CHANNEL_TYPE")));
				userCredentials.add(role);
			}
			logger.info(" Number of userCredentials found = "
					+ userCredentials.size());
			logger.trace("Executed .... ");

		} catch (SQLException se) {
			logger.error("getGroupAndRoleInfo : SQLException "
					+ se.getMessage()
					+ " occured while geting group and role information", se);

		} finally {
			/* Close the resultset, statement. */
			logger
			.trace("getGroupAndRoleInfo : Finally Closing resultset,connection and prepasred statement");
			DBConnectionManager.releaseResources(ps, rs);

		}
		return userCredentials;
	}

	/**
	 * This method will retrieve all the roles of users, order by group id, and
	 * will return an collection of < groupID id, roleName > , each instance
	 * represented by Role inner class.
	 * 
	 * @return ArrayList
	 */
	public ArrayList getTopupConfigInfo() {
		logger.info("Started...");
		PreparedStatement ps = null;
		ResultSet rs = null;
		ArrayList<CircleTopupConfig> topupConfigInfo = new ArrayList<CircleTopupConfig>();
		try {
			ps = connection.prepareStatement(SQL_SELECT_TOP_UP_CONFIG);
			rs = ps.executeQuery();
			while (rs.next()) {
				CircleTopupConfig topupDto = new CircleTopupConfig();
				topupDto.setCircleId(rs.getInt("CIRCLE_ID"));
				topupDto.setTransactionType(rs.getString("TRANSACTION_TYPE"));
				topupDto.setStartAmount(rs.getDouble("START_AMOUNT"));
				topupDto.setTillAmount(rs.getDouble("TILL_AMOUNT"));
				topupDto.setEspCommission(rs.getDouble("ESP_COMMISSION"));
				topupDto.setPspCommission(rs.getDouble("PSP_COMMISSION"));
				topupDto.setServiceTax(rs.getDouble("SERVICE_TAX"));
				topupDto.setProcessingFee(rs.getDouble("PROCESSING_FEE"));
				topupDto.setInCardGroup(rs.getString("IN_CARD_GROUP"));
				topupDto.setTopupConfigId(rs.getInt("TOPUP_CONFIG_ID"));
				topupConfigInfo.add(topupDto);
			}
		
			logger.info("Executed .... ");

		} catch (SQLException se) {
			logger.error("getTopupConfigInfo : SQLException "
					+ se.getMessage()
					+ " occured while geting group and role information", se);
			
		} finally {
			/* Close the resultset, statement. */
			logger
			.info("getTopupConfigInfo : Finally Closing resultset,connection and prepasred statement");
			DBConnectionManager.releaseResources(ps, rs);

		}
		return topupConfigInfo;
	}

	/**
	 * This method will retrieve all the System Config info, order by circleId
	 * transactionType channelType, and will return an collection of
	 * SystemConfig DTO
	 * 
	 * @return ArrayList
	 */
	public ArrayList getSysConfigInfo()

	{
		logger.info("Started...");
		PreparedStatement ps = null;
		ResultSet rs = null;
		ArrayList<SysConfig> sysConfigList = new ArrayList<SysConfig>();
		try {
			ps = connection.prepareStatement(SQL_SELECT_SYSTEM_CONFIG);
			rs = ps.executeQuery();
			while (rs.next()) {
				SysConfig sysDto = new SysConfig();
				sysDto.setCircleId(rs.getInt("CIRCLE_ID"));
				sysDto.setTransactionType(rs.getString("TRANSACTION_TYPE"));
				sysDto.setChannelName(rs.getString("CHANNEL"));
				sysDto.setMinAmount(rs.getDouble("MIN_AMOUNT"));
				sysDto.setMaxAmount(rs.getDouble("MAX_AMOUNT"));
				sysConfigList.add(sysDto);
			}
			logger.info("Executed .... ");

		} catch (SQLException se) {
			logger.error("getSysConfigInfo : SQLException " + se.getMessage()
					+ " occured while geting system config information", se);
		} finally {
			/* Close the resultset, statement. */
			logger
			.info("getSysConfigInfo : Finally Closing resultset,connection and prepasred statement");
			DBConnectionManager.releaseResources(ps, rs);

		}
		return sysConfigList;
	}

	/**
	 * This Method will retrieve all the INMApping information.
	 */
	public ArrayList getInMappingInfo() {
		logger.info("Started...");
		PreparedStatement ps = null;
		ResultSet rs = null;
		ArrayList<InMapping> inMappingList = new ArrayList<InMapping>();
		try {
			ps = connection.prepareStatement(SQL_SELECT_IN_MAPPING);
			rs = ps.executeQuery();
			while (rs.next()) {
				InMapping inMapping = new InMapping();
				inMapping.setCircleId(rs.getInt("CIRCLE_ID"));
				inMapping.setPrimaryId(rs.getString("PRIMARY_ID"));
				inMapping.setSecondryId(rs.getString("SECONDARY_ID"));
				inMapping.setExternalIntType(rs.getLong("EXTERNAL_INT_TYPE"));
				inMapping.setExternalId(rs.getInt("EXTERNAL_ID"));
				inMapping.setExternalUrl(rs.getString("EXTERNAL_URL"));
				inMapping.setSubscriberCircleCode(rs.getString("CIRCLE_CODE"));
				inMapping.setDthCode(rs.getString("DTH_CODE"));
				inMapping.setWireLessCode(rs.getString("WIRELESS_CODE"));
				inMapping.setWireLineCode(rs.getString("WIRELINE_CODE"));
				inMappingList.add(inMapping);
			}
			
			logger.info("Executed .... ");

		} catch (SQLException se) {
			logger.error("getInMappingInfo : SQLException " + se.getMessage()
					+ " occured while geting In Mapping information", se);

		} finally {
			/* Close the resultset, statement. */
			logger
			.info("getInMappingInfo : Finally Closing resultset,connection and prepasred statement");
			DBConnectionManager.releaseResources(ps, rs);

		}
		
		return inMappingList;
	}
	
	/**
	 * This method will retrieve all Message Details info,and store it in
	 * HashMap<String,HashMap<RecieverType,String(message to send)>
	 */
	public ArrayList getResponseMsgDetails(){
		
		logger.info("Started...");
		PreparedStatement ps = null;
		ResultSet rs = null;
		ArrayList<MessageDetail> messageDetailList = new ArrayList<MessageDetail>();
		try {
			ps = connection.prepareStatement(SQL_SELECT_MSG_DETAILS);
			rs = ps.executeQuery();
			while (rs.next()) {
				MessageDetail msgDetail = new MessageDetail();
				msgDetail.setMessageCode(rs.getString("MSG_CODE"));
				msgDetail.setDescription(rs.getString("MESSAGE"));
				msgDetail.setRecipent(RecieverType.valueOf(rs.getString("RECEIVER_NAME").toUpperCase()));
				messageDetailList.add(msgDetail);
			}
			logger.info("Executed .... ");

		} catch (SQLException se) {
			logger.error("getResponseMsgDetails : SQLException "
					+ se.getMessage()
					+ " occured while geting Message Details ", se);
		} finally {
			/* Close the resultset, statement. */
			logger
			.info("getResponseMsgDetails : Finally Closing resultset and prepasred statement");
			DBConnectionManager.releaseResources(ps, rs);

		}
		return messageDetailList;
	}
		
	
	
/**
 * This method will retrieve all NumberSeriesDetails info,and store it in
 * ArrayList<MobileNumberSeries>
 */
public ArrayList getNumberSeriesDetails(){
	
	logger.info("Started...");
	PreparedStatement ps = null;
	ResultSet rs = null;
	ArrayList<MobileNumberSeries> numberSeriesList = new ArrayList<MobileNumberSeries>();
	try {
		ps = connection.prepareStatement(SQL_SELECT_NUM_SERIES);
		rs = ps.executeQuery();
		MobileNumberSeries numberSeries;
		ConnectionBean cbReq = null;
		ConnectionBean cbIN = null;
		ConnectionBean cbOut = null;
		
		while (rs.next()) {
			cbReq = new ConnectionBean();
			cbIN = new ConnectionBean();
			cbOut = new ConnectionBean();
			numberSeries = new MobileNumberSeries();
			// Setting Series Number
			numberSeries.setSeriesNo(rs.getInt("SERIES_NO"));
			// Setting Series Name
			numberSeries.setSeriesName(rs.getString("SERIES_NAME"));
			// Setting Series From
			numberSeries.setSeriesFrom(rs.getLong("SERIES_FROM"));
			// Setting Series To
			numberSeries.setSeriesTo(rs.getLong("SERIES_TO"));
			// Setting Request Q Properties
			
			// Setting Host
			cbReq.setHost(rs.getString("REQ_HOST"));
			// Setting Port Number
			cbReq.setPort(new Integer(rs.getInt("REQ_PORT")));
			// Setting Channel
			cbReq.setChannel(rs.getString("REQ_CHANNEL"));
			// Setting Q Manager Name
			cbReq.setQMgr(rs.getString("REQ_QMGR"));
			// Setting Q Name
			cbReq.setQu(rs.getString("REQ_QNAME"));
			numberSeries.setReqConnBean(cbReq);
			// Setting IN Q Properties
			
			// Setting Host
			if (null != rs.getString("IN_HOST")) {
				cbIN.setHost(rs.getString("IN_HOST"));
			}
			// Setting Port Number
			if (null != rs.getString("IN_PORT")) {
				cbIN.setPort(new Integer(rs.getInt("IN_PORT")));
			}
			// Setting Channel
			if (null != rs.getString("IN_CHANNEL")) {
				cbIN.setChannel(rs.getString("IN_CHANNEL"));
			}
			// Setting Q Manager Name
			if (null != rs.getString("IN_QMGR")) {
				cbIN.setQMgr(rs.getString("IN_QMGR"));
			}
			// Setting Q Name
			if(null!=rs.getString("IN_QNAME"))
			{
				cbIN.setQu(rs.getString("IN_QNAME"));
			}

			numberSeries.setInConnBean(cbIN);

			// Setting OUT Q Properties
			cbOut = new ConnectionBean();

			// Setting Host
			if (null != rs.getString("OUT_HOST")) {
				cbOut.setHost(rs.getString("OUT_HOST"));
			}

			// Setting Port Number
			if (null != rs.getString("OUT_PORT")) {
				cbOut.setPort(new Integer(rs.getInt("OUT_PORT")));
			}
			// Setting Channel
			if (null != rs.getString("OUT_CHANNEL")) {
				cbOut.setChannel(rs.getString("OUT_CHANNEL"));
			}
			// Setting Q Manager Name
			if (null != rs.getString("OUT_QMGR")) {
				cbOut.setQMgr(rs.getString("OUT_QMGR"));
			}
			// Setting Q Name
			if (null != rs.getString("OUT_QNAME")) {
				cbOut.setQu(rs.getString("OUT_QNAME"));
			}

			numberSeries.setOutConnBean(cbOut);

			numberSeriesList.add(numberSeries);
		}
		
		
		logger.info("Executed .... ");

	} catch (SQLException se) {
		logger.error("getNumberSeriesDetails : SQLException "
				+ se.getMessage()
				+ " occured while geting Message Details ", se);
	} finally {
		/* Close the resultset, statement. */
		logger
		.info("getNumberSeriesDetails : Finally Closing resultset and prepasred statement");
		DBConnectionManager.releaseResources(ps, rs);

	}
	return numberSeriesList;
}


public ArrayList getMobileSeriesConfig() {
	logger.info("Started...");
	PreparedStatement ps = null;
	ResultSet rs = null;
	ArrayList<InMapping> mobileSeriesConfigList = new ArrayList<InMapping>();
	try {
		ps = connection.prepareStatement(SQL_SELECT_MOBILE_SERIES_CONFIG);
		rs = ps.executeQuery();
	
		while (rs.next()) {
			InMapping mobileSeriesConfig = new InMapping();
			mobileSeriesConfig.setMobileSeriesId(rs
					.getString("MOBILE_SERIES_ID"));
			mobileSeriesConfig.setPrimaryId(rs.getString("PRIMARY_ID"));
			mobileSeriesConfig.setSecondryId(rs.getString("SECONDARY_ID"));
			mobileSeriesConfigList.add(mobileSeriesConfig);
		}
	} catch (SQLException se) {
		logger.error("getMobileSeriesConfig : SQLException "
				+ se.getMessage()
				+ " occured while geting Mobile Series Config ", se);
	
	} finally {
		/* Close the resultset, statement. */
		logger
		.info(" Finally Closing resultset and prepared statement");
			// Finallly closing resultset,statement.
		DBConnectionManager.releaseResources(ps, rs);

	}
	return mobileSeriesConfigList;
}
public ArrayList getGroupAndUrlInfo() {
	logger.trace("getGroupAndUrlInfo   Started.......................................");
	PreparedStatement ps = null;
	ResultSet rs = null;
	ArrayList<AuthenticationKeyInCache> groupurlcredential = new ArrayList<AuthenticationKeyInCache>();
	try {
		connection = DBConnectionManager.getDBConnection();
		ps = connection.prepareStatement(SQL_SELECT_GROUP_URL_MAPPING);
		rs = ps.executeQuery();
		while (rs.next()) {
			AuthenticationKeyInCache groupUrl = new AuthenticationKeyInCache();
			//logger.info("rs.getInt('GROUPID'): "+rs.getInt("GROUPID")+" rs.getString('URL') :"+rs.getString("URL"));
			groupUrl.setGroupId(rs.getInt("GROUPID"));
			groupUrl.setUrl(rs.getString("URL"));
			groupurlcredential.add(groupUrl);
		}
		logger.info(" Number of groupurlcredential found = "
				+ groupurlcredential.size());
		logger.trace("Executed .... ");

	} catch (Exception se) {
		logger.error("getGroupAndUrlInfo : SQLException "
				+ se.getMessage()
				+ " occured while geting group and url information", se);

	} finally {
		/* Close the resultset, statement. */
		logger
		.trace("getGroupAndUrlInfo : Finally Closing resultset,connection and prepasred statement");
		DBConnectionManager.releaseResources(ps, rs);
		DBConnectionManager.releaseResources(connection);

	}
	return groupurlcredential;
}
public ArrayList<String> getTotalUrls()
{
	ArrayList<String> list = new ArrayList<String>();
	PreparedStatement ps = null;
	ResultSet rs = null;
	try
	{
	connection = DBConnectionManager.getDBConnection();
	ps = connection.prepareStatement(SQL_SELECT_TOTAL_URLS);
	rs = ps.executeQuery();
	while (rs.next()) {
	list.add(rs.getString("URL"));
	}
	}
	catch(Exception ex)
	{
		logger.error("getTotalUrls : Exception "
				+ ex.getMessage()
				+ " occured while geting total url information", ex);
	}
	return list;
}
}

