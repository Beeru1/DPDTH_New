package com.ibm.dp.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

import com.ibm.dp.common.Constants;
import com.ibm.dp.common.DBQueries;
import com.ibm.dp.dao.DpInitiateFnfDao;
import com.ibm.dp.dto.DpInitiateFnfDto;
import com.ibm.virtualization.recharge.common.Utility;
import com.ibm.virtualization.recharge.dao.rdbms.BaseDaoRdbms;
import com.ibm.virtualization.recharge.db.DBConnectionManager;
import com.ibm.virtualization.recharge.exception.DAOException;

public class DpInitiateFnfDaoImpl extends BaseDaoRdbms implements DpInitiateFnfDao {

public static Logger logger = Logger.getLogger(DpInitiateFnfDaoImpl.class.getName());
	


	public DpInitiateFnfDaoImpl(Connection connection) 
	{
		super(connection);
	}
	public static final String SQL_DISTRIBUTOR_LIST_INITIATE_LIST = DBQueries.SQL_DISTRIBUTOR_LIST_INITIATE_LIST;
	public static final String SQL_DISTRIBUTOR_SEARCH_LIST =DBQueries.SQL_DISTRIBUTOR_SEARCH_LIST;
	public static final String SQL_INSERT_INTO_DP_FNF_HEADER =DBQueries.SQL_INSERT_INTO_DP_FNF_HEADER;
	public static final String SQL_VIEW_AV_STOCK_FNF_DIST =DBQueries.SQL_VIEW_AV_STOCK_FNF_DIST;
	public static final String SQL_VIEW_CPE_STOCK_FNF_DIST =DBQueries.SQL_VIEW_CPE_STOCK_FNF_DIST;
	public static final String SQL_DISTRIBUTOR_LIST_APPROVE_FNF = DBQueries.SQL_DISTRIBUTOR_LIST_APPROVE_FNF;
	public static final String SQL_INSERT__DP_FNF_HEADER_APPROVAL = DBQueries.SQL_INSERT__DP_FNF_HEADER_APPROVAL;
	public static final String SQL_CONFIRMATION_DIST_LIST_FNF = DBQueries.SQL_CONFIRMATION_DIST_LIST_FNF;
	public static final String SQL_CONFIRMATION_DIST_LIST_FNFPAN = DBQueries.SQL_CONFIRMATION_DIST_LIST_FNF_PAN;
	public static final String SQL_UPDATE_CONFIMATION_DP_FNF_HEADER = DBQueries.SQL_UPDATE_CONFIMATION_DP_FNF_HEADER;
	public List<DpInitiateFnfDto> getDistList(long loginUserId) throws DAOException {
		List<DpInitiateFnfDto> listReturn = new ArrayList<DpInitiateFnfDto>();
		PreparedStatement pstmtDist = null;
		ResultSet rsetReport = null;
		boolean b=false;
		logger.info("get dist List Dao Impl   Start......!");
		try
		{
			logger.info("********************get dist list when TSM logs in  ********* ");
			pstmtDist = connection.prepareStatement(SQL_DISTRIBUTOR_LIST_INITIATE_LIST);
			pstmtDist.setInt(1,(int) loginUserId);
			DpInitiateFnfDto dpInitiateFnfDto =null;
			rsetReport= pstmtDist.executeQuery();
			while(rsetReport.next()){
				dpInitiateFnfDto =new DpInitiateFnfDto();
				dpInitiateFnfDto.setDistId(rsetReport.getInt("LOGIN_ID")); //Neetika
				dpInitiateFnfDto.setStrDistOlmId(rsetReport.getString("LOGIN_NAME"));
				dpInitiateFnfDto.setStrDistName(rsetReport.getString("ACCOUNT_NAME"));
				dpInitiateFnfDto.setDays(rsetReport.getInt("LAST_ACT_DAY"));
				dpInitiateFnfDto.setDistTranscId(rsetReport.getInt("TRANSACTION_TYPE"));
				
				listReturn.add(dpInitiateFnfDto);
//			logger.info("********DISTRIBUTOR_ID********"+dpInitiateFnfDto.getDistId());
//			logger.info("********LOGIN_NAME********"+dpInitiateFnfDto.getStrDistOlmId());
//			logger.info("********ACCOUNT_NAME********"+dpInitiateFnfDto.getStrDistName());
//			logger.info("********LAST_ACT_DAY********"+dpInitiateFnfDto.getDays());
//			logger.info("********TRANSACTION_TYPE********"+dpInitiateFnfDto.getDistTranscId());
			
			}
			logger.info("get dist List Dao Impl eNDS......!"+listReturn.size());
			
		}catch (Exception e) 
		{
			e.printStackTrace();
			throw new DAOException(e.getMessage());
		}
		finally
		{
			DBConnectionManager.releaseResources(pstmtDist ,rsetReport );
		}
		
		return listReturn;
	}
	public DpInitiateFnfDto getDistListSearch(String searchedDistOlmId,long loginUserId) throws DAOException 
	{
		List<DpInitiateFnfDto> distList = new ArrayList<DpInitiateFnfDto>();
		PreparedStatement pstmtDist = null;
		boolean blUserFnd = false;
		DpInitiateFnfDto objDpInitiateFnfDto= null;
		DpInitiateFnfDto obj = null;
		ResultSet rset = null;
		try
		{
			objDpInitiateFnfDto = new DpInitiateFnfDto();
			logger.info("**************************inside DISTRIBUTOR_SEARCH_LIST************ ");
			pstmtDist = connection.prepareStatement(SQL_DISTRIBUTOR_SEARCH_LIST);
			pstmtDist.setInt(1, (int) loginUserId);
			pstmtDist.setString(2, searchedDistOlmId );
			rset= pstmtDist.executeQuery();
			while(rset.next())
			{
				logger.info("***************sql query**************"+pstmtDist);
				objDpInitiateFnfDto = new DpInitiateFnfDto();
				//select si.DISTRIBUTOR_ID, lm.LOGIN_NAME, ad.ACCOUNT_NAME,min((DAYS(CURRENT_DATE) - DAYS(si.ASSIGN_DATE))) as LAST_ACT_DAY 
				objDpInitiateFnfDto.setDistId(rset.getInt("LOGIN_ID"));//Neetika
				objDpInitiateFnfDto.setStrDistOlmId(rset.getString("LOGIN_NAME"));
				objDpInitiateFnfDto.setStrDistName(rset.getString("ACCOUNT_NAME"));
				objDpInitiateFnfDto.setDays(rset.getInt("LAST_ACT_DAY"));
				objDpInitiateFnfDto.setDistTranscId(rset.getInt("TRANSACTION_TYPE"));
				distList.add(objDpInitiateFnfDto);
//				logger.info("********DISTRIBUTOR_ID********"+objDpInitiateFnfDto.getDistId());
//				logger.info("********LOGIN_NAME********"+objDpInitiateFnfDto.getStrDistOlmId());
//				logger.info("********ACCOUNT_NAME********"+objDpInitiateFnfDto.getStrDistName());
//				logger.info("********LAST_ACT_DAY********"+objDpInitiateFnfDto.getDays());
//				logger.info("********TRANSACTION_TYPE********"+objDpInitiateFnfDto.getDistTranscId());
				blUserFnd=true;
			}
			
			if(blUserFnd)
			{
				logger.info("*************blUserFnd******"+blUserFnd);
				obj = new DpInitiateFnfDto();
				obj.setStrMsg("true");
				obj.setDataList(distList);
			}	
			else
			{
				obj = new DpInitiateFnfDto();
				logger.info("*************blUserFnd******"+blUserFnd);
				obj.setStrMsg("No distributor found");
			}
			
			logger.info("get dist List Dao Impl eNDS......!"+distList.size());
			
		}catch (Exception e) 
		{
			e.printStackTrace();
			throw new DAOException(e.getMessage());
		}
		finally
		{
			DBConnectionManager.releaseResources(pstmtDist ,rset );
		}
		
		return obj;
		
	}
	public String requestForDistFnF(String accountId, String distRemark, String debitrequired, long loginUserId,String days, String distName) throws DAOException {

		String msg="";
		PreparedStatement pstmtDist = null;
		logger.info("get dist List Dao Impl   Start......!");
		try
		{
			//DIST_ID, DIST_TRANS_ID, IS_DEBIT, CIRCLE, INITIATED_BY, INITIATION_DT, INIT_REMARKS
			pstmtDist = connection.prepareStatement(SQL_INSERT_INTO_DP_FNF_HEADER);
			
			pstmtDist.setString(1, accountId);
			logger.info("accountId::"+accountId);
			pstmtDist.setString(2, accountId );
			pstmtDist.setString(3, loginUserId+"" );
			pstmtDist.setString(4, debitrequired);
			logger.info("debitrequired::"+debitrequired);
			pstmtDist.setString(5, accountId);
			pstmtDist.setInt(6, (int) loginUserId);
			pstmtDist.setString(7, distRemark);
			pstmtDist.setString(8, days);
			logger.info("days::"+days);
			pstmtDist.executeUpdate();
			msg = " FnF has been initiated for "+distName;
			connection.commit();
		}
		catch (Exception e) 
		{
			System.out.println(e);
			e.printStackTrace();
			throw new DAOException(e.getMessage());
		}
		finally
		{
			DBConnectionManager.releaseResources(pstmtDist ,null );
		}
		
		return msg;
	}
	public List<DpInitiateFnfDto> viewDistStockDetail(String accountId,long loginUserId) throws DAOException {
		logger.info("****viewDistStockDetail*******************inside daoimpl");

		List<DpInitiateFnfDto> viewStockListDto = new ArrayList<DpInitiateFnfDto>();
		PreparedStatement pstmt = null;
		PreparedStatement pstmt1 = null;
		ResultSet rset			= null;
		ResultSet reportset			= null;
		DpInitiateFnfDto stockDto = null;
		int transactionId= 0 ;
		try
		{
			viewStockListDto = new ArrayList<DpInitiateFnfDto>();
			pstmt = connection.prepareStatement("select TRANSACTION_TYPE_ID from DP_DISTRIBUTOR_MAPPING where DP_DIST_ID=? and PARENT_ACCOUNT=?");
			pstmt.setString(1, accountId);
			pstmt.setInt(2,(int)loginUserId);
			rset= pstmt.executeQuery();
			while(rset.next())
			{
				transactionId = rset.getInt("TRANSACTION_TYPE_ID");
				logger.info("transactionId:::"+transactionId);
				if(transactionId==1)
				{
					logger.info("***********************inside  view AV Type stock **************");
					pstmt1 = connection.prepareStatement(SQL_VIEW_AV_STOCK_FNF_DIST);
					pstmt1.setString(1, accountId);
					pstmt1.setString(2, accountId);
					pstmt1.setString(3, accountId);
				}
				else
				{
					logger.info("***********************inside  view CPE Type stock **************");
					pstmt1 = connection.prepareStatement(SQL_VIEW_CPE_STOCK_FNF_DIST);
					logger.info("****************************accountId**********"+accountId);
					pstmt1.setString(1, accountId);
					pstmt1.setString(2, accountId);
					pstmt1.setString(3, accountId);
					pstmt1.setString(4, accountId);
					pstmt1.setString(5, accountId);
					pstmt1.setString(6, accountId);
					pstmt1.setString(7, accountId);
					pstmt1.setString(8, accountId);
					pstmt1.setString(9, accountId);
				}
				reportset= pstmt1.executeQuery();
				while(reportset.next())
				{
				//	PRODUCT_NAME     SER_STK     NSER_STK     PENDING_STK     REV_PEND_STK     REV_DC_STK     CHURN_STK     UPGRADE_STK                              
					stockDto = new DpInitiateFnfDto();
					stockDto.setProduct(reportset.getString("PRODUCT_NAME"));
					stockDto.setSerializedStock(reportset.getInt("SER_STK"));
					stockDto.setNonSerializedStock(reportset.getInt("NSER_STK"));
					stockDto.setInTransitPOStock(reportset.getInt("PENDING_STK"));
					stockDto.setDamagedPendingStock(reportset.getInt("REV_PEND_STK"));
					stockDto.setInTransitDCStock(reportset.getInt("REV_DC_STK"));
					stockDto.setUpgradePending(reportset.getInt("CHURN_STK"));
					stockDto.setRecoveryPending(reportset.getInt("UPGRADE_STK"));
					stockDto.setSum(reportset.getInt("SER_STK")+reportset.getInt("NSER_STK")+reportset.getInt("PENDING_STK")+reportset.getInt("REV_PEND_STK")+reportset.getInt("REV_DC_STK")+reportset.getInt("CHURN_STK")+reportset.getInt("UPGRADE_STK"));
//					logger.info("*********product***"+stockDto.getProduct());
//					logger.info("*********SerializedStock***"+stockDto.getSerializedStock());
//					logger.info("*********NonSerializedStock***"+stockDto.getNonSerializedStock());
//					logger.info("*********InTransitPOStock***"+stockDto.getInTransitPOStock());
//					logger.info("*********DamagedPendingStock***"+stockDto.getDamagedPendingStock());
//					logger.info("*********InTransitDCStock***"+stockDto.getInTransitDCStock());
//					logger.info("*********UpgradePending***"+stockDto.getUpgradePending());
//					logger.info("*********RecoveryPending***"+stockDto.getRecoveryPending());
					viewStockListDto.add(stockDto);
					stockDto = null;
				}
			}
		}
		catch (Exception e) 
		{
			e.printStackTrace();
			throw new DAOException(e.getMessage());
		}
		finally
		{
			DBConnectionManager.releaseResources(pstmt ,rset );
			DBConnectionManager.releaseResources(pstmt1 ,reportset );
			
		}
		return viewStockListDto;
		
	
	}
	public List<DpInitiateFnfDto> getPendingDistApprovalList(long loginUserId) throws DAOException {

		List<DpInitiateFnfDto> listReturn = new ArrayList<DpInitiateFnfDto>();
		PreparedStatement pstmtDist = null;
		ResultSet rsetReport = null;
		logger.info("ZSM     get dist List Dao Impl   Start......!");
		try
		{
			logger.info("********************get dist list when ZSM logs in  ********* "+loginUserId);
			pstmtDist = connection.prepareStatement(SQL_DISTRIBUTOR_LIST_APPROVE_FNF);
			pstmtDist.setLong(1, loginUserId);
			DpInitiateFnfDto dpInitiateFnfDto =null;
			rsetReport= pstmtDist.executeQuery();
			while(rsetReport.next()){
				// DIST_ID TSM_ID  DIST_OLM_ID     DIST_NAME     DAYS_SINCE_LAST_ACT     TSM_OLM_ID     TSM_NAME     INIT_REMARKS     DEBIT_REQ  
				dpInitiateFnfDto =new DpInitiateFnfDto();
				dpInitiateFnfDto.setReqId(rsetReport.getInt("REQ_ID"));
				dpInitiateFnfDto.setTsmId(rsetReport.getString("TSM_ID"));
				dpInitiateFnfDto.setDistId(rsetReport.getInt("DIST_ID"));
				dpInitiateFnfDto.setStrDistOlmId(rsetReport.getString("DIST_OLM_ID"));
				dpInitiateFnfDto.setStrDistName(rsetReport.getString("DIST_NAME"));
				dpInitiateFnfDto.setDays(rsetReport.getInt("DAYS_SINCE_LAST_ACT"));
				dpInitiateFnfDto.setStrTSMOlmId(rsetReport.getString("TSM_OLM_ID"));
				dpInitiateFnfDto.setApproverName(rsetReport.getString("TSM_NAME"));
				dpInitiateFnfDto.setApproverRemark(rsetReport.getString("INIT_REMARKS"));
				dpInitiateFnfDto.setDebitReq(rsetReport.getString("DEBIT_REQ"));
				listReturn.add(dpInitiateFnfDto);
				
//			logger.info("********REQ_ID********"+dpInitiateFnfDto.getReqId());
//			logger.info("********TSM_ID********"+dpInitiateFnfDto.getTsmId());
//			logger.info("********DIST_ID********"+dpInitiateFnfDto.getDistId());
//			logger.info("********LOGIN_NAME********"+dpInitiateFnfDto.getStrDistOlmId());
//			logger.info("********ACCOUNT_NAME********"+dpInitiateFnfDto.getStrDistName());
//			logger.info("********LAST_ACT_DAY********"+dpInitiateFnfDto.getDays());
//			logger.info("********TSM_OLM_ID********"+dpInitiateFnfDto.getStrTSMOlmId());
//			logger.info("********TSM_NAME********"+dpInitiateFnfDto.getApproverName());
//			logger.info("********INIT_REMARKS********"+dpInitiateFnfDto.getApproverRemark());
//			logger.info("********DEBIT_REQ********"+dpInitiateFnfDto.getDebitReq());
			}
			logger.info("get dist List Dao Impl eNDS......!"+listReturn.size());
			
		}catch (Exception e) 
		{
			e.printStackTrace();
			throw new DAOException(e.getMessage());
		}
		finally
		{
			DBConnectionManager.releaseResources(pstmtDist ,rsetReport );
		}
		
		return listReturn;
	
		}
	public String approveDistFnF(String accountId, String approverRemark, long loginUserId,String distName,String reqId) throws DAOException 
	{
		String msg="";
		PreparedStatement pstmtDist = null;
		logger.info("get dist List Dao Impl   Start......!");
		try
		{
			//APPROVED_BY, APPROVED_DT, APPR_REMARKS
			pstmtDist = connection.prepareStatement(SQL_INSERT__DP_FNF_HEADER_APPROVAL);
			pstmtDist.setInt(1,(int) loginUserId);
			pstmtDist.setString(2,approverRemark);
			pstmtDist.setString(3,reqId);
			pstmtDist.executeUpdate();
			msg = " FnF has been approved for "+distName;
			connection.commit();
		}
		catch (Exception e) 
		{
			logger.info(e);
			e.printStackTrace();
			throw new DAOException(e.getMessage());
		}
		finally
		{
			DBConnectionManager.releaseResources(pstmtDist ,null );
		}
		
		return msg;
	}
	public List<DpInitiateFnfDto> getConfirmationPendingDistList(long loginUserId, int circleId) throws DAOException {


		List<DpInitiateFnfDto> listReturn = new ArrayList<DpInitiateFnfDto>();
		PreparedStatement pstmtDist = null;
		ResultSet rsetReport = null;
		logger.info("ZSM     get dist List Dao Impl   Start......!");
		try
		{
			logger.info("********************get dist list when FINANCE USER logs in  ********* "+circleId);
			
			if(circleId==0)
			{
				pstmtDist = connection.prepareStatement(SQL_CONFIRMATION_DIST_LIST_FNFPAN);
			}
			else
			{
				pstmtDist = connection.prepareStatement(SQL_CONFIRMATION_DIST_LIST_FNF);
				pstmtDist.setLong(1, loginUserId); // Modified by Neetika changed circle id to login id to handle multi circle issue on 12 Aug
			}
						
			
			DpInitiateFnfDto dpInitiateFnfDto =null;
			rsetReport= pstmtDist.executeQuery();
			while(rsetReport.next()){
//REQ_ID     TSM_ID     DIST_ID     DIST_OLM_ID     ZSM_NAME     ZSM_REMARK     DIST_NAME     DAYS_SINCE_LAST_ACT     TSM_OLM_ID     TSM_NAME     INIT_REMARKS     DEBIT_REQ    
				dpInitiateFnfDto =new DpInitiateFnfDto();
				dpInitiateFnfDto.setReqId(rsetReport.getInt("REQ_ID"));
				dpInitiateFnfDto.setTsmId(rsetReport.getString("TSM_ID"));
				dpInitiateFnfDto.setDistId(rsetReport.getInt("DIST_ID"));
				dpInitiateFnfDto.setStrDistOlmId(rsetReport.getString("DIST_OLM_ID"));
				dpInitiateFnfDto.setConfirmerName(rsetReport.getString("ZSM_NAME"));
				dpInitiateFnfDto.setConfirmerRemark(rsetReport.getString("ZSM_REMARK"));
				dpInitiateFnfDto.setStrDistName(rsetReport.getString("DIST_NAME"));
				dpInitiateFnfDto.setDays(rsetReport.getInt("DAYS_SINCE_LAST_ACT"));
				dpInitiateFnfDto.setStrTSMOlmId(rsetReport.getString("TSM_OLM_ID"));
				dpInitiateFnfDto.setApproverName(rsetReport.getString("TSM_NAME"));
				dpInitiateFnfDto.setApproverRemark(rsetReport.getString("INIT_REMARKS"));
				dpInitiateFnfDto.setDebitReq(rsetReport.getString("DEBIT_REQ"));
				listReturn.add(dpInitiateFnfDto);
				
//			logger.info("********REQ_ID********"+dpInitiateFnfDto.getReqId());
//			logger.info("********TSM_ID********"+dpInitiateFnfDto.getTsmId());
//			logger.info("********DIST_ID********"+dpInitiateFnfDto.getDistId());
//			logger.info("********LOGIN_NAME********"+dpInitiateFnfDto.getStrDistOlmId());
//			logger.info("********ZSM_NAME********"+dpInitiateFnfDto.getConfirmerName());
//			logger.info("********ZSM_REMARK********"+dpInitiateFnfDto.getConfirmerRemark());
//			logger.info("********ACCOUNT_NAME********"+dpInitiateFnfDto.getStrDistName());
//			logger.info("********LAST_ACT_DAY********"+dpInitiateFnfDto.getDays());
//			logger.info("********TSM_OLM_ID********"+dpInitiateFnfDto.getStrTSMOlmId());
//			logger.info("********TSM_NAME********"+dpInitiateFnfDto.getApproverName());
//			logger.info("********INIT_REMARKS********"+dpInitiateFnfDto.getApproverRemark());
//			logger.info("********DEBIT_REQ********"+dpInitiateFnfDto.getDebitReq());
			}
			logger.info("get dist List Dao Impl eNDS......!"+listReturn.size());
			
		}catch (Exception e) 
		{
			e.printStackTrace();
			throw new DAOException(e.getMessage());
		}
		finally
		{
			DBConnectionManager.releaseResources(pstmtDist ,rsetReport );
		}
		
		return listReturn;
	
		
	}
	public String confirmFnF(String reqId, String confRemark, String distName, long loginUserId) throws DAOException 
	{

		String msg="";
		PreparedStatement pstmtDist = null;
		PreparedStatement pstmtDist1 = null;
		PreparedStatement pstmtDist2 = null;
		PreparedStatement pstmtDist3 = null;
		PreparedStatement pstmtDist4 = null;
		ResultSet rsetReport = null;
		logger.info("get dist List Dao Impl   Start......!");
		try
		{
			//APPROVED_BY, APPROVED_DT, APPR_REMARKS
			pstmtDist = connection.prepareStatement(SQL_UPDATE_CONFIMATION_DP_FNF_HEADER);
			pstmtDist.setInt(1,(int) loginUserId);
			pstmtDist.setString(2,confRemark);
			logger.info("******confRemark*******"+confRemark);
			pstmtDist.setString(3,reqId);
			pstmtDist.executeUpdate();
			msg = " FnF has been Confirmed for "+distName;
			// Added for so that distributor whose FnF is in "CONFIRMED" cannot login into dp
			
			String sql1 = "UPDATE DP_DISTRIBUTOR_MAPPING SET STATUS='F' WHERE DP_DIST_ID IN(Select DIST_ID from DP_FNF_HEADER "+
				"where STATUS='CONFIRMED'and REQ_ID=? ) and TRANSACTION_TYPE_ID IN (Select DIST_TRANS_ID from DP_FNF_HEADER "+
				"where STATUS='CONFIRMED'and REQ_ID=? )";
			pstmtDist1 = connection.prepareStatement(sql1);
			pstmtDist1.setString(1,reqId);
			pstmtDist1.setString(2,reqId);
			pstmtDist1.executeUpdate();
			
			// Added to block the distributor from login into application
			String sql2="select * from DP_DISTRIBUTOR_MAPPING where DP_DIST_ID IN(Select DIST_ID from DP_FNF_HEADER where STATUS='CONFIRMED'and REQ_ID=?)and STATUS ='A' with ur";
			pstmtDist2 = connection.prepareStatement(sql2);
			pstmtDist2.setString(1,reqId);
			rsetReport= pstmtDist2.executeQuery();
			if(rsetReport.next())
			{
				String sql3="UPDATE VR_LOGIN_MASTER SET STATUS='A' where LOGIN_ID IN (Select DIST_ID from DP_FNF_HEADER where STATUS='CONFIRMED'and REQ_ID=?)";
				pstmtDist3 = connection.prepareStatement(sql3);
				pstmtDist3.setString(1,reqId);
				pstmtDist3.executeUpdate();
			}
			else
			{
				String sql4="UPDATE VR_LOGIN_MASTER SET STATUS='F' where LOGIN_ID IN (Select DIST_ID from DP_FNF_HEADER where STATUS='CONFIRMED'and REQ_ID=?)";
				pstmtDist4 = connection.prepareStatement(sql4);
				pstmtDist4.setString(1,reqId);
				pstmtDist4.executeUpdate();
			}
			connection.commit();
		}
		catch (Exception e) 
		{
			logger.info(e);
			e.printStackTrace();
			throw new DAOException(e.getMessage());
		}
		finally
		{
			DBConnectionManager.releaseResources(pstmtDist ,null );
		}
		
		return msg;
	
	
	}
	}
