/*****************************************************************************\
 **
 ** Virtualization - Recharge.
 **
 ** Copyright (c) 2007-2008 IBM.
 ** All Rights Reserved
 **
 **
 \****************************************************************************/

package com.ibm.virtualization.recharge.cache;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.MissingResourceException;
import java.util.Timer;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

import org.apache.log4j.Logger;

import com.ibm.virtualization.framework.bean.ChannelType;
import com.ibm.virtualization.recharge.common.Constants;
import com.ibm.virtualization.recharge.common.ExceptionCode;
import com.ibm.virtualization.recharge.common.RecieverType;
import com.ibm.virtualization.recharge.common.ResourceReader;
import com.ibm.virtualization.recharge.common.TransactionType;
import com.ibm.virtualization.recharge.dao.CacheDao;
import com.ibm.virtualization.recharge.dao.DAOFactory;
import com.ibm.virtualization.recharge.db.DBConnectionManager;
import com.ibm.virtualization.recharge.dto.AuthorizationKey;
import com.ibm.virtualization.recharge.dto.CircleTopupConfig;
import com.ibm.virtualization.recharge.dto.INRouterServiceDTO;
import com.ibm.virtualization.recharge.dto.InMapping;
import com.ibm.virtualization.recharge.dto.MessageDetail;
import com.ibm.virtualization.recharge.dto.MobileNumberSeries;
import com.ibm.virtualization.recharge.dto.Role;
import com.ibm.virtualization.recharge.dto.SysConfig;
import com.ibm.virtualization.recharge.dto.SysConfigKey;
import com.ibm.virtualization.recharge.dto.TopupConfigKey;
import com.ibm.virtualization.recharge.exception.DAOException;
import com.ibm.virtualization.recharge.exception.VirtualizationServiceException;
/**
 * Cache implementation class, providing all the cache related implementation
 *
 * @author Rohit Dhall
 * @date 07-Sep-2007
 *
 */
public class CacheImpl implements Cache {
	/* Logger for this class. */
	private static Logger logger = Logger.getLogger(CacheImpl.class.getName());

	/* Authorization Cache. */
	private LinkedHashMap<AuthorizationKey, ArrayList<String>> authorizationCache = new LinkedHashMap<AuthorizationKey, ArrayList<String>>();

	/* CircleTopupConfig Cache */
	private HashMap<TopupConfigKey, ArrayList> circleTopupConfigCache = new HashMap<TopupConfigKey, ArrayList>();

	/* SystemConfig Cache */
	private HashMap<SysConfigKey, SysConfig> systemConfigCache = new HashMap<SysConfigKey, SysConfig>();

	/* IN Mapping Cache */
	private ArrayList<InMapping> inMappingCache = new ArrayList<InMapping>();

	/* Number Series Mapping cache */
	private ArrayList<MobileNumberSeries> arLstSeriesCache = new ArrayList<MobileNumberSeries>();

	/* Mobile Series Config Cache */
	private ArrayList<InMapping> mobileSeriesConfigCache = new ArrayList<InMapping>();

	/* Response Message Detais Cache */
	private HashMap<String, HashMap<RecieverType,String>> msgDetailsCache = new HashMap<String, HashMap<RecieverType,String>>();
	
	private static int database_type;
	
	
	private Connection con;
	
	private ReadWriteLock readWriteLock = new ReentrantReadWriteLock(true);

	/**
	 * Default constructor, made protected so that cannot be accessed from
	 * outside except CacheFactory class in same package
	 *
	 */
	protected CacheImpl() {
		
		// Initialize all Cache
		//this.populateCache();
		logger.info("******* In Cache Implementation Class");
		Timer timer = new Timer();
		long timeInterval = 0;
		
		try {
		
			// Read time interval from resource bundle
			
			timeInterval = Long.parseLong(ResourceReader.getCoreResourceBundleValue("cache.refreshtime.minutes"));
			
			//read database type from resource bundle
			database_type = Integer.parseInt(ResourceReader.getCoreResourceBundleValue(Constants.DATABASE_TYPE));
			
		} catch (MissingResourceException missingResourceExp) {
			logger
					.fatal("caught MissingResourceException : Unable to get com.ibm.virtualization.recharge.resources.VirtualizationResources.properties "
							+ missingResourceExp.getMessage());
			
			//default time interval,database_type if not defined in resource bundle
			database_type=2;
			timeInterval = 30;
		}catch(VirtualizationServiceException virtualizationExp){
			logger.warn("caught VirtualizationServiceException"+virtualizationExp.getMessage());
			//default time interval,database_type if not defined in resource bundle
			database_type=2;
			timeInterval = 30;
		}
//		 Initialize all Cache
		this.populateCache();

		// refresh cache after specified time interval repeatedly
		timer.scheduleAtFixedRate(new CacheRefreshTask(),
				timeInterval * 60 * 1000, timeInterval * 60 * 1000);

	}
	
	/*
	 * (non-Javadoc)
	 *
	 * @see com.ibm.virtualization.recharge.cache.Cache#getUserCredentials(java.lang.String)
	 */
	public ArrayList getUserCredentials(long groupID, ChannelType channelType) {
		logger.trace("********Started");
		AuthorizationKey keyObject = new AuthorizationKey();
		keyObject.setChannelType(channelType);
		keyObject.setGroupId(groupID);
		ArrayList autheCache = null;
		try{
			readWriteLock.readLock().lock();
			autheCache = (ArrayList)authorizationCache.get(keyObject);
			/*for(int i=0;i<autheCache.size();i++){
				System.out.println("arrayList in get user credentials"+i+":"+autheCache.get(i));
			}*/
		}finally{
			readWriteLock.readLock().unlock();
		}
		logger.trace("Completed");
		return autheCache;		
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see com.ibm.virtualization.recharge.cache.Cache#isUserAuthorized(java.lang.String,java.util.List)
	 */
	public boolean isUserAuthorized(long groupID, ChannelType channelType,
			List roleNames) {
		logger.info("********Is User Authorized");
		// Get the HashMap corresponding to key(groupID)
		ArrayList map = getUserCredentials(groupID, channelType);

		if (map != null) {
			return map.containsAll(roleNames);
		}
		return false;
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see com.ibm.virtualization.recharge.cache.Cache#isUserAuthorized(java.lang.String,java.lang.String)
	 */
	public boolean isUserAuthorized(long groupID, ChannelType channelType,
			String roleName) {
		// Get the HashMap corresponding to key(groupID)
		ArrayList map = getUserCredentials(groupID, channelType);
		if (map != null) {
			return map.contains(roleName);
		}
		return false;
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see com.ibm.virtualization.recharge.cache.Cache#getTopupConfig(int,
	 *      com.ibm.virtualization.recharge.common.TransactionType, float)
	 */
	public CircleTopupConfig getTopupConfig(int circleId,
			TransactionType transactionType, double amount,long commissionId) {
		logger.trace("Started");
		CircleTopupConfig configDto = new CircleTopupConfig();
		ArrayList<CircleTopupConfig> list = new ArrayList<CircleTopupConfig>();
		TopupConfigKey keyObject = new TopupConfigKey();
		keyObject.setCircleId(circleId);
		keyObject.setTransactionType(transactionType);
		keyObject.setComissionGroupId(commissionId);
		// Get the ArrayList corresponding to key(TopupConfigKey)
		
		try{
			readWriteLock.readLock().lock();
			list = circleTopupConfigCache.get(keyObject);
		}finally{
			readWriteLock.readLock().unlock();
		}
		if (list != null) {
			for (Object obj : list) {
				configDto = (CircleTopupConfig) obj;
				if (amount >= configDto.getStartAmount()
						&& amount <= configDto.getTillAmount()) {
					return configDto;
				}
			}
		}
		// return null in case there is no record correspondint to parameter
		// passed as argument
		logger
				.warn("getTopupConfig :Does not found CircleTopupConfig DTO corresponding to parameters"
						+ circleId + "'");
		logger.trace("Completed");
		return null;
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see com.ibm.virtualization.recharge.cache.Cache#getSystemConfig(int,
	 *      com.ibm.virtualization.recharge.common.TransactionType,
	 *      com.ibm.virtualization.framework.bean.ChannelType)
	 */
	public SysConfig getSystemConfig(int circleId,
			TransactionType transactionType, ChannelType channelType) {
		//logger.trace("Started");
		SysConfigKey key = new SysConfigKey();
		key.setCircleId(circleId);
		key.setChannelType(channelType);
		key.setTransactionType(transactionType);
		// Get SysConfig Dto corresponding to key(SysConfigKey)
		SysConfig SysConfCache = null;
		
		try{
			readWriteLock.readLock().lock();
			SysConfCache = (SysConfig) systemConfigCache.get(key);
		}finally{
			readWriteLock.readLock().unlock();
		}
		//logger.trace("Completed");
		return SysConfCache;	
		
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see com.ibm.virtualization.recharge.cache.Cache#getDestinationIn(java.lang.String)
	 */
	public INRouterServiceDTO getDestinationIn(String subscriberNumber)
			throws VirtualizationServiceException {
		logger.trace("Started");
		INRouterServiceDTO inDto = new INRouterServiceDTO();
		long subsNumber = Long.parseLong(subscriberNumber);

		inDto.setSubsCircleId(-1);
		inDto.setExternalConfigId(-1);
		inDto.setAirUrl(null);
		
		try{
			readWriteLock.readLock().lock();
			for (InMapping mapping : inMappingCache) {
				if ((subsNumber >= Long.parseLong(mapping.getPrimaryId()))
						&& (subsNumber <= Long.parseLong(mapping.getSecondryId()))) {
					inDto.setSubsCircleId(mapping.getCircleId());
					inDto.setAirUrl(mapping.getExternalUrl());
					inDto.setExternalConfigId(mapping.getExternalId());
					inDto.setSubscriberCircleCode(mapping.getSubscriberCircleCode());
					//TODO:bhanu to be confirmed after payment code
					inDto.setDthCode(mapping.getDthCode());
					inDto.setWireLessCode(mapping.getWireLessCode());
					inDto.setWireLineCode(mapping.getWireLineCode());
					break;
				}
			}
		}finally{
			readWriteLock.readLock().unlock();
		}
		
		if (inDto.getExternalConfigId() == -1) {
			/* External Id Not Found. */
			logger
					.warn("External Config Id Could not be fetched/not found for subscriber no:"
							+ subscriberNumber);
			//throw new VirtualizationServiceException("External ID Not Present.");
			throw new VirtualizationServiceException(ExceptionCode.Transaction.ERROR_TRANSACTION_NO_EXTERNAL_ID);
		}
		if (inDto.getSubsCircleId() == -1) {
			logger.warn("Circle does not exist for Present subscriber no :"
					+ subscriberNumber);
			throw new VirtualizationServiceException(
					ExceptionCode.Transaction.ERROR_TRANSACTION_NO_SUBS_CIRCLE);
		}
		if ("".equalsIgnoreCase(inDto.getAirUrl()) || inDto.getAirUrl() == null) {
			logger.warn("Air Url Not Present for subscriber no :"
					+ subscriberNumber);
			throw new VirtualizationServiceException(
					"Air URL Could Not be Fetched or is null or empty.");
		}
		logger.trace("Completed");
		return inDto;
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see com.ibm.virtualization.recharge.cache.Cache#getMobileSeriesId(long)
	 */
	public int getMobileSeriesId(long mobileNumber) {
		logger.debug("Started moblie no:"+mobileNumber);
		try{
			readWriteLock.readLock().lock();
			for (InMapping mobileSeriesConfig : mobileSeriesConfigCache) {
				if (mobileNumber >= Long.parseLong(mobileSeriesConfig
						.getPrimaryId())
						&& mobileNumber <= Long.parseLong(mobileSeriesConfig
								.getSecondryId())) {
					logger
							.debug("getMobileSeriesId:found mobile number in mobile series config"+mobileSeriesConfig.getMobileSeriesId());
					return Integer.parseInt(mobileSeriesConfig.getMobileSeriesId());
				}
			}
		}finally{
			readWriteLock.readLock().unlock();
		}

		return -1;
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see com.ibm.virtualization.recharge.cache.Cache#getMessageDetails(java.lang.String)
	 */
	public HashMap<RecieverType, String> getMessageDetails(String msgCode) {
		HashMap<RecieverType,String> msgDCache = null;
		try{
			readWriteLock.readLock().lock();
			msgDCache = (HashMap<RecieverType,String>) msgDetailsCache.get(msgCode);
		}finally{
			readWriteLock.readLock().unlock();
		}
		return msgDCache;
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see com.ibm.virtualization.recharge.cache.Cache#getNumberSeries(long)
	 */
	public MobileNumberSeries getNumberSeries(long mobileNumber,
			String seriesName) {
		try{
			readWriteLock.readLock().lock();
			for (MobileNumberSeries noSeriesDto : arLstSeriesCache) {
				if (seriesName.equalsIgnoreCase(noSeriesDto.getSeriesName())
						&& (mobileNumber >= noSeriesDto.getSeriesFrom() && mobileNumber <= noSeriesDto
								.getSeriesTo())) {
					return noSeriesDto;
				}
			}
		}finally{
			readWriteLock.readLock().unlock();
		}
		return null;
	}

	/**
	 * This method will retrieve all the roles of users, order by group id, and
	 * will return an collection of < groupID id, roleName > , each instance
	 * represented by Role inner class.
	 *
	 * @return ArrayList
	 */
	protected ArrayList<Role> getGroupAndRoleInfo() {
		logger.trace("getGroupAndRoleInfo : Started");
		
		ArrayList<Role> userCredentials = new ArrayList<Role>();
		try {
			// TODO BAhuja Aug.17, 2007 To use dao factory here.
			CacheDao cacheDao = DAOFactory.getDAOFactory(database_type)
					.getCacheDao(con);
			userCredentials=cacheDao.getGroupAndRoleInfo();
		
		} catch (Exception e) {
		
			logger.warn("getGroupAndRoleInfo : Exception " + e.getMessage()
					+ " while geting group and role information");
			
		} 
		return userCredentials;
	}

	/**
	 * This method will retrieve all the Circle_Topup_Config info, order by
	 * circleId transactionType, and will return an collection of
	 * CircleTopupConfig DTO
	 *
	 * @return ArrayList
	 */
	protected ArrayList<CircleTopupConfig> getTopupConfigInfo() {
		logger.trace("getTopupConfigInfo : Started");
		
		ArrayList<CircleTopupConfig> topupConfigInfo = new ArrayList<CircleTopupConfig>();
		try {
			// TODO BAhuja Aug.17, 2007 To use dao factory here.
			CacheDao cacheDao = DAOFactory.getDAOFactory(
					database_type)
					.getCacheDao(con);
				topupConfigInfo=cacheDao.getTopupConfigInfo();
			

		} catch (Exception e) {
			logger.warn(" Exception " + e.getMessage()
					+ " while geting circle topup config information");
		}
		logger.trace(" returned topupConfigInfo");
		return topupConfigInfo;
	}

	
	
	
	
   /*
    * (non-Javadoc)
    * @see com.ibm.virtualization.recharge.cache.Cache#getConsolidateInfo()
    */ 	 
	public Map getConsolidateCacheInfo(){
		Map<String, Object> hm = new HashMap<String, Object>();
		try {
			hm.put("alInMappingInfo", inMappingCache);
			hm.put("alNumberSeriesDetails", arLstSeriesCache);
			hm.put("alMobileSeriesConfig", mobileSeriesConfigCache);
			hm.put("lhm_authorizationCache", authorizationCache);
			hm.put("hmcircleTopupConfigCache", circleTopupConfigCache);
			hm.put("hmsystemConfigCache", systemConfigCache);
			hm.put("hmmsgDetailsCache", msgDetailsCache);
		}catch(Exception e){
			logger.warn("Exception : while getting info from cache" , e);
		}
		logger.trace("returned consolidate Map");
		return hm;
	}
	
	/**
	 * This method will retrieve all the System Config info, order by circleId
	 * transactionType channelType, and will return an collection of
	 * SystemConfig DTO
	 *
	 * @return ArrayList
	 */
	protected ArrayList<SysConfig> getSysConfigInfo() {
		logger.trace("Started");
		ArrayList<SysConfig> sysConfigInfo = new ArrayList<SysConfig>();
		try {
			// TODO BAhuja Aug.17, 2007 To use dao factory here.
			CacheDao cacheDao = DAOFactory.getDAOFactory(
					database_type)
					.getCacheDao(con);
				sysConfigInfo =cacheDao.getSysConfigInfo();				
		
		} catch (Exception e) {
			logger.warn(" Exception " + e.getMessage()
					+ " while geting geting system config information");
		} 
		logger.trace("returned sysConfigInfo");
		return sysConfigInfo;
	}

	/**
	 * This Method will retrieve all the INMApping information.
	 */
	protected void getInMappingInfo() {
		logger.trace("Started");
		inMappingCache.clear();
	try {
			// TODO BAhuja Aug.17, 2007 To use dao factory here.
			CacheDao cacheDao = DAOFactory.getDAOFactory(
					database_type)
					.getCacheDao(con);
				inMappingCache=cacheDao.getInMappingInfo();
				logger.trace("InMappingLoggerInfo :" + inMappingCache.toString());
		} catch (Exception e) {
			logger.warn("Exception " + e.getMessage()
					+ " while geting geting In Mapping information");
		} 
		logger.trace("Completed");
		//return inMappingCache;
	}

	/**
	 * This method will retrieve all Message Details info,and store it in
	 * HashMap<String,HashMap<RecieverType,String(message to send)>
	 */
	protected ArrayList getResponseMsgDetails() {
		logger.trace("Started");
		ArrayList<MessageDetail> messageDetailList=new ArrayList<MessageDetail>();
		try {
//			 TODO BAhuja Aug.17, 2007 To use dao factory here.
			CacheDao cacheDao = DAOFactory.getDAOFactory(
					database_type)
					.getCacheDao(con);
			messageDetailList=cacheDao.getResponseMsgDetails();
		} catch (Exception e) {
			logger.warn("getResponseMsgDetails : Exception " + e.getMessage()
					+ " while geting geting message details");
		} 
		logger.trace("Completed");
		return messageDetailList;
	}

	/**
	 * This method will retrieve all NumberSeriesDetails info,and store it in
	 * ArrayList<MobileNumberSeries>
	 */
	protected void getNumberSeriesDetails() {
		logger.trace("Started");
		arLstSeriesCache.clear();
		try {
			CacheDao cacheDao = DAOFactory.getDAOFactory(
					database_type)
					.getCacheDao(con);

				arLstSeriesCache=cacheDao.getNumberSeriesDetails();
				logger.trace("getNumberSeriesDetailsLogger Info:" + arLstSeriesCache.toString());
				logger.trace("Add MobileNumberSeries object to ArrayList.");
			
		} catch (Exception e) {
			logger.warn("getNumberSeriesDetails : Exception " + e.getMessage()
					+ " while geting geting message details");
		} 
		logger.trace("Completed");
	}
	
	
	protected void getMobileSeriesConfig() {
		logger.trace("Started");
		mobileSeriesConfigCache.clear();
		try {
			CacheDao cacheDao = DAOFactory.getDAOFactory(
					database_type)
					.getCacheDao(con);
							mobileSeriesConfigCache=cacheDao.getMobileSeriesConfig();
							logger.trace("getMobileSeriesConfigLoggerInfo" + mobileSeriesConfigCache.toString());							
			
		} catch (Exception e) {
			logger.warn(" Exception " + e.getMessage()
					+ " while geting geting message details");
		} 
		
		logger.trace("Completed");
	}

	/*
	 * Method to initialize cache
	 */
	protected synchronized void populateCache() {
		try {
			logger.info(" Refreshing cache");
			readWriteLock.writeLock().lock();
			this.con = DBConnectionManager.getDBConnection();
			logger.info("****Populating Role Cache");
			this.pouplateRoleCache(this.getGroupAndRoleInfo());
			logger.info("****Populating Topup Config");
			//this.populateTopupConfig(this.getTopupConfigInfo());
			//this.populateSysConfig(this.getSysConfigInfo());
			//this.getInMappingInfo();
			//this.populateMsgDetailCache(this.getResponseMsgDetails());
			//this.getNumberSeriesDetails();
			//this.getMobileSeriesConfig();

		} catch (DAOException e) {
			logger.warn("DAOException ::" + e.getMessage()
					+ " occured while refreshing cacahe ");
		} finally {
			readWriteLock.writeLock().unlock();
			// Closing Connection
			try {
				if (con != null) {
					con.commit();
				}
			} catch (SQLException se) {
				logger.error("SQLException " + se.getMessage()
						+ " occured while performing commit on connection");
			}
			logger.trace("Completed");
			DBConnectionManager.releaseResources(con);
			logger.info("*******Completed release");
		}
		
	}

	/**
	 * This method will iterate through the passed msgDetailList collection and will
	 * create a map of individual message code as key and messages assosiated to that code in a
	 * Map , as a element of the Map. Map structure would be like <MSG_CODE, MAP
	 * of messages associated to Message Code>, the structure of the map of messages belonging
	 * to message code would be <RecieverType,String(MessageDescription)>
	 *
	 * @param msgDetailList
	 *            ArrayList
	 */
	protected void populateMsgDetailCache(ArrayList<MessageDetail> msgDetailList){
		Iterator<MessageDetail> msgCollection=msgDetailList.iterator();
		HashMap<RecieverType,String> msgAssociated=null;
		MessageDetail msgDetail=null;
		String msgCode=null;
		boolean msgPut=false;
		msgDetailsCache.clear();
		
		if(msgCollection.hasNext()){
			msgDetail=msgCollection.next();
			msgPut=false;
			msgCode=msgDetail.getMessageCode();
			msgAssociated=new HashMap<RecieverType,String>();
			if(!msgCollection.hasNext()){
				msgAssociated.put(msgDetail.getRecipent(),msgDetail.getDescription());
				msgPut=true;
				msgDetailsCache.put(msgDetail.getMessageCode(),msgAssociated);
				logger.trace("Only one Message found in Data base");
				logger.trace("Mesasage Associated to message code :"+msgDetail.getMessageCode()+"=>"+msgAssociated);
				return;
			}
		}
		while(msgCollection.hasNext()){
			msgAssociated.put(msgDetail.getRecipent(),msgDetail.getDescription());
			while(msgCollection.hasNext()){
				msgDetail=msgCollection.next();
				if(msgDetail.getMessageCode().equalsIgnoreCase(msgCode)){
					msgAssociated.put(msgDetail.getRecipent(),msgDetail.getDescription());
				}else{
					msgDetailsCache.put(msgCode,msgAssociated);
					msgPut=true;
					logger.trace("Messages associated with message code :"+msgCode+"=>"+msgAssociated);
					if(!msgCollection.hasNext()){
						msgAssociated=new HashMap<RecieverType,String>();
						msgAssociated.put(msgDetail.getRecipent(),msgDetail.getDescription());
						msgDetailsCache.put(msgDetail.getMessageCode(),msgAssociated);
						logger.trace("Messages associated with message code :"+msgDetail.getMessageCode()+"=>"+msgAssociated);
					}
					break;
				}
			}
			if(!msgPut){
				msgDetailsCache.put(msgCode,msgAssociated);
				logger.trace("Messages associated with message code :"+msgCode+"=>"+msgAssociated);
			}
				msgCode=msgDetail.getMessageCode();
				msgAssociated=new HashMap<RecieverType,String>();
				msgPut=false;
		}
		if(!msgPut){
			msgAssociated.put(msgDetail.getRecipent(),msgDetail.getDescription());
			msgDetailsCache.put(msgCode,msgAssociated);
			logger.trace("Messages associated with message code :"+msgCode+"=>"+msgAssociated);
		}
		logger.trace("populateMsgDetailCache:CompletedSuccessfully");
	}
	/**
	 * This method will iterate through the passed  Role collection and will
	 * create a map of individual groupId as key and roles of that group in a
	 * List , as a element of the Map. Map structure would be like <GROUP_ID, List
	 * of Roles of this Group_id>, the structure of the list of Roles belonging
	 * to group id would be ArrayList<ROLE_NAME>
	 *
	 * @param roleList
	 *            ArrayList
	 */
	protected void pouplateRoleCache(ArrayList<Role> roleList) {
		//logger.trace("pouplateRoleCache:Started");
		Iterator<Role> roleCollection = roleList.iterator();
		ArrayList<String> rolesAssigned = null;
		Role role = null;
		AuthorizationKey keyObjectP = null;
		AuthorizationKey keyObjectN = null;
		authorizationCache.clear();
		if (roleCollection.hasNext()) 
		{
			role = roleCollection.next();
			keyObjectP = new AuthorizationKey();
			keyObjectP.setGroupId(role.getGroupID());
			keyObjectP.setChannelType(role.getChannelType());
			if (!roleCollection.hasNext()) 
			{
				rolesAssigned = new ArrayList<String>();
				rolesAssigned.add(role.getRoleName());
			}
		} 
		else 
			return;
		int roleAss = 0;
		while (roleCollection.hasNext()) 
		{
			roleAss ++;
			rolesAssigned = new ArrayList<String>();
			rolesAssigned.add(role.getRoleName());
			role = roleCollection.next();
			keyObjectN = new AuthorizationKey();
			keyObjectN.setChannelType(role.getChannelType());
			keyObjectN.setGroupId(role.getGroupID());
			
//			logger.info("keyObjectP group ID ::  "+keyObjectP.getGroupId());
//			logger.info("keyObjectN group ID ::  "+keyObjectN.getGroupId());
			
			while (keyObjectN.equals(keyObjectP)) 
			{
				//logger.info("Assigning role ::  "+role.getRoleName());
				rolesAssigned.add(role.getRoleName());
				
				if (roleCollection.hasNext()) 
				{
					role = roleCollection.next();
					keyObjectN = new AuthorizationKey();
					keyObjectN.setChannelType(role.getChannelType());
					keyObjectN.setGroupId(role.getGroupID());
				} 
				else 
					break;
			}
			
			//logger.info("rolesAssigned ::  "+rolesAssigned.get(0));
			authorizationCache.put(keyObjectP, rolesAssigned);
			//logger.info("Roles Assigned to groupId ="+keyObjectP.getGroupId()+" ChannelType::"+keyObjectP.getChannelType()+"=>"+rolesAssigned);
			
			keyObjectP = new AuthorizationKey();
			keyObjectP.setGroupId(keyObjectN.getGroupId());
			keyObjectP.setChannelType(keyObjectN.getChannelType());
			

			if(!roleCollection.hasNext()){
			//	rolesAssigned=new ArrayList<String>();
			//	rolesAssigned.add(role.getRoleName());
				break;
			}
			
			roleAss = 0;
		}
		
		if(roleAss==0)
		{
			rolesAssigned = new ArrayList<String>();
			rolesAssigned.add(role.getRoleName());
		}
		
		authorizationCache.put(keyObjectP, rolesAssigned);
		//logger.info("Roles Assigned to groupId ="+keyObjectP.getGroupId()+"ChannelType :"+keyObjectP.getChannelType()+"=>"+rolesAssigned);
	}

	/**
	 * This method will iterate through the passed in SystemConfig collection
	 * and will create a map of individual circleId,transactionType and
	 * channelType as a key and SystemConfig DTO corresponding to that
	 * key(circleId,transactionType,channelType).Map structure would be like
	 * <(Circle_Id,Transaction Type,channelType),SystemConfig Dto>
	 *
	 * @param sysConfig
	 *            ArrayList
	 */
	protected void populateSysConfig(ArrayList<SysConfig> sysConfigInfo) {
		logger.trace("Started");
		Iterator<SysConfig> sysConfigIt = sysConfigInfo.iterator();
		SysConfig sysConfigDto = null;
		SysConfigKey pKey = null;
		systemConfigCache.clear();
		while (sysConfigIt.hasNext()) {
			sysConfigDto = new SysConfig();
			pKey = new SysConfigKey();
			sysConfigDto = sysConfigIt.next();
			pKey.setCircleId(sysConfigDto.getCircleId());
			pKey.setTransactionType(TransactionType.valueOf(sysConfigDto
					.getTransactionType()));
			pKey.setChannelType(ChannelType.valueOf(sysConfigDto
					.getChannelName()));
			systemConfigCache.put(pKey, sysConfigDto);
			}
		logger.trace("getsystemConfigCacheLoggerInfo" + systemConfigCache.toString());
		logger.trace("Completed");
	}

	/**
	 * This method will iterate through the passed in CircleTopup config
	 * collection and will create a map of individual circleId and
	 * transactionType as a key and ArrayList containing all CircleTopupConfig
	 * DTO corresponding to that key(circleId,transactionType).Map structure
	 * would be like <(Circle_Id,Transaction Type),ArrayList of topupConfig Dto>
	 *
	 * @param topupConfig
	 *            ArrayList
	 */
	protected void populateTopupConfig(ArrayList<CircleTopupConfig> configInfo) {
		logger.trace("started");
		Iterator<CircleTopupConfig> topupIt = configInfo.iterator();
		ArrayList<CircleTopupConfig> topupList;
		CircleTopupConfig topupDto = null;
		TopupConfigKey pKey = null;
		TopupConfigKey nKey = null;
		circleTopupConfigCache.clear();

		if (topupIt.hasNext()) {
			pKey = new TopupConfigKey();
			topupDto = new CircleTopupConfig();

			topupDto = topupIt.next();
			pKey.setCircleId(topupDto.getCircleId());
			pKey.setTransactionType(TransactionType.valueOf(topupDto
					.getTransactionType()));

			if (!topupIt.hasNext()) {// if list contain only one record.
				topupList = new ArrayList<CircleTopupConfig>();
				topupList.add(topupDto);
				circleTopupConfigCache.put(pKey, topupList);
			}
			logger.trace("topupConfig:" + circleTopupConfigCache.toString());
		} else {
			return;
		}
		while (topupIt.hasNext()) {
			topupList = new ArrayList<CircleTopupConfig>();
			topupList.add(topupDto);

			nKey = new TopupConfigKey();
			topupDto = new CircleTopupConfig();

			topupDto = topupIt.next();
			nKey.setCircleId(topupDto.getCircleId());
			nKey.setTransactionType(TransactionType.valueOf(topupDto
					.getTransactionType()));

			while (pKey.equals(nKey)) {
				topupList.add(topupDto);
				if (topupIt.hasNext()) {
					topupDto = new CircleTopupConfig();
					topupDto = topupIt.next();
					nKey.setCircleId(topupDto.getCircleId());
					nKey.setTransactionType(TransactionType.valueOf(topupDto
							.getTransactionType()));
				} else {
					break;
				}
			}// Inner While Loop
			circleTopupConfigCache.put(pKey, topupList);
			
			// set previous key value
			pKey = new TopupConfigKey();
			pKey.setCircleId(nKey.getCircleId());
			pKey.setTransactionType(nKey.getTransactionType());
			if(!topupIt.hasNext()){
				circleTopupConfigCache.put(nKey, topupList);
			}
		}// Outer While Loop
		logger.trace("CircleTopupConfigCache" + circleTopupConfigCache.toString());
		logger.trace(" Completed");
	}
	
	
	

	
}







