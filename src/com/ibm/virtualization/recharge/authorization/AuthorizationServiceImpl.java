/*****************************************************************************\
 **
 ** Virtualization - Recharge.
 **
 ** Copyright (c) 2007-2008 IBM.
 ** All Rights Reserved
 **
 **
 \****************************************************************************/

package com.ibm.virtualization.recharge.authorization;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.TreeMap;

import org.apache.log4j.Logger;

import com.ibm.dp.common.DBQueries;
import com.ibm.dp.dto.RecoPeriodDTO;
import com.ibm.utilities.Utility;
import com.ibm.virtualization.framework.bean.ChannelType;
import com.ibm.virtualization.recharge.cache.CacheFactory;
import com.ibm.virtualization.recharge.common.Constants;
import com.ibm.virtualization.recharge.common.ResourceReader;
import com.ibm.virtualization.recharge.dao.DAOFactory;
import com.ibm.virtualization.recharge.dao.LoginDao;
import com.ibm.virtualization.recharge.db.DBConnectionManager;
import com.ibm.virtualization.recharge.exception.DAOException;
import com.ibm.virtualization.recharge.exception.VirtualizationServiceException;
import com.ibm.virtualization.recharge.service.AuthorizationInterface;
/**
 * Authorization implementation class, providing all the authorization related
 * implementation
 * 
 * @author Rohit Dhall
 * @date 07-Sep-2007
 * 
 */
public class AuthorizationServiceImpl implements AuthorizationInterface {

	/* Logger for this class. */
	private static Logger logger = Logger.getLogger(AuthorizationServiceImpl.class.getName());

	public static final String SQL_RECO_STATUS	= DBQueries.SQL_RECO_STATUS;
	/*
	 * (non-Javadoc)
	 * 
	 * @see com.ibm.virtualization.recharge.authorization.IAuthorizer#getUserCredentials(java.lang.String)
	 */
	public ArrayList getUserCredentials(long groupID, ChannelType channelType) {
		logger.info("*********In Authorization Service Impl class");
		return CacheFactory.getCacheImpl().getUserCredentials(groupID,channelType);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.ibm.virtualization.recharge.authorization.IAuthorizer#isUserAuthorized(java.lang.String,java.util.List)
	 */
	public boolean isUserAuthorized(long groupID, ChannelType channelType,
			List roles) {
		return CacheFactory.getCacheImpl().isUserAuthorized(groupID,
				channelType, roles);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.ibm.virtualization.recharge.authorization.IAuthorizer#loadRoleLinkMap()
	 */

	public LinkedHashMap loadRoleLinkMap()
			throws VirtualizationServiceException {
		LinkedHashMap<String, ArrayList> roleLinkMap = new LinkedHashMap<String, ArrayList>();
		Connection connection = null;

		try {
			/* get the database connection */
			connection = DBConnectionManager.getDBConnection();
			LoginDao loginDao = DAOFactory.getDAOFactory(
					Integer.parseInt(ResourceReader.getCoreResourceBundleValue(Constants.DATABASE_TYPE)))
					.getLoginDao(connection);
			roleLinkMap = loginDao.getRoleLinkMap();
		} catch (DAOException de) {
			logger.error("loadRoleLinkMap : DAOException " + de.getMessage()
					+ " occured while geting link and role information", de);
			throw new VirtualizationServiceException(de.getMessage());
		} catch(VirtualizationServiceException virtualizationExp){
			logger.error("VirtualizationServiceException occured"+virtualizationExp.getMessage());
			throw new VirtualizationServiceException(virtualizationExp.getMessage());
		}finally {
			DBConnectionManager.releaseResources(connection);
		}

		return roleLinkMap;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.ibm.virtualization.recharge.authorization.IAuthorizer#isUserAuthorized(java.lang.String,java.lang.String)
	 */
	public boolean isUserAuthorized(long groupID, ChannelType channelType,
			String roleName) {
		return CacheFactory.getCacheImpl().isUserAuthorized(groupID,
				channelType, roleName);
	}

	@SuppressWarnings("finally")
	public int forceToRecoPage(Long distID) {
		// TODO Auto-generated method stub
		Connection connection = null;
		PreparedStatement pstmt = null;
		ResultSet rset			= null;
		int forceToReco=0;
		final String query="select " 
							+" (case when (min(TO_DATE + b.GRACE_PERIOD days)) <  current date then 1 else 0 end) as count "
							+" from DP_RECO_PERIOD a,DP_RECO_DIST_DETAILS b,DP_DISTRIBUTOR_MAPPING dm "
							+" where a.ID=b.RECO_ID "
							+" and b.DIST_ID=? and dm.DP_DIST_ID=b.DIST_ID and dm.TRANSACTION_TYPE_ID=2  "
							+" and a.IS_RECO_CLOSED=0 and a.TO_DATE < current date and b.reco_status='INITIATE' with ur";
		final String query2="select " 
			+" (case when (min(TO_DATE + a.GRACE_PERIOD days)) <  current date then 1 else 0 end) as count "
			+" from DP_RECO_PERIOD a,DP_RECO_DIST_DETAILS b ,DP_DISTRIBUTOR_MAPPING dm "
			+" where a.ID=b.RECO_ID "
			+" and b.DIST_ID=? and dm.DP_DIST_ID=b.DIST_ID and dm.TRANSACTION_TYPE_ID=2  "
			+" and a.IS_RECO_CLOSED=0 and a.TO_DATE < current date and b.reco_status='INITIATE' with ur";
		try
		{
			connection=DBConnectionManager.getDBConnection();
			//pstmt = connection.prepareStatement(SQL_RECO_STATUS);
			pstmt = connection.prepareStatement(query); //changed by neetika
			pstmt.setString(1, distID.toString());
			
			rset = pstmt.executeQuery();
			if (rset.next()) {
				forceToReco = rset.getInt(1);
			}
			if(forceToReco==0)
			{
				pstmt.clearParameters();
			pstmt = connection.prepareStatement(query2); //changed by neetika
			pstmt.setString(1, distID.toString());
			
			rset = pstmt.executeQuery();
			if (rset.next()) {
				forceToReco = rset.getInt(1);
			}
			}
			System.out.println(SQL_RECO_STATUS+"forceToReco"+forceToReco);
		}
		catch (Exception e) 
		{
			e.printStackTrace();
			throw new DAOException(e.getMessage());
		}
		finally
		{
			DBConnectionManager.releaseResources(pstmt, rset);
			return forceToReco;
		}
		
		
	}
}
