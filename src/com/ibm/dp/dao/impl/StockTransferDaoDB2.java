package com.ibm.dp.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;

import com.ibm.dp.beans.StockTransferFormBean;
import com.ibm.dp.common.Constants;
import com.ibm.dp.dao.StockTransferDao;
import com.ibm.dp.dto.DistributorDTO;
import com.ibm.dp.dto.DistributorDetailsDTO;
import com.ibm.dp.dto.InterSSDTransferDTO;
import com.ibm.virtualization.recharge.dao.rdbms.BaseDaoRdbms;
import com.ibm.virtualization.recharge.db.DBConnectionManager;
/**
 * @author Mohammad Aslam
 */
public class StockTransferDaoDB2 extends BaseDaoRdbms implements StockTransferDao {

	private static Logger logger = Logger.getLogger(StockTransferDaoDB2.class.getName());
	
	protected static final String SQL_SELECT_FROM_DISTRIBUTORS = "SELECT DISTINCT VAD.ACCOUNT_ID, VAD.ACCOUNT_NAME "
															   + "FROM DP_DIST_STOCK_TRANSFER DDST, VR_ACCOUNT_DETAILS VAD " 
															   + "WHERE VAD.ACCOUNT_ID = DDST.CREATED_BY AND FROM_DIST_ID = ? AND DDST.STATUS='INITIATED' WITH UR";
	protected static final String SQL_SELECT_TO_DISTRIBUTORS   = "SELECT DISTINCT VAD.ACCOUNT_ID, VAD.ACCOUNT_NAME "
															   	+ "FROM DP_DIST_STOCK_TRANSFER DDST, VR_ACCOUNT_DETAILS VAD " 
															   	+ "WHERE VAD.ACCOUNT_ID = DDST.TO_DIST_ID AND FROM_DIST_ID = ? AND DDST.STATUS='INITIATED' WITH UR";
	protected static final String SQL_SELECT_TRANS_DETAILS     = "SELECT DPM.PRODUCT_ID, DPM.PRODUCT_NAME, DDST.REQ_TRANSFER_QTY, DDST.REQ_ID "
			 											       + "FROM DP_DIST_STOCK_TRANSFER DDST, DP_PRODUCT_MASTER DPM " 
			 											       + "WHERE DDST.FROM_DIST_ID = ? AND DDST.TO_DIST_ID = ? AND DDST.CREATED_BY = ?  "
			 											       + "AND DDST.PRODUCT_ID = DPM.PRODUCT_ID AND DDST.STATUS='INITIATED' WITH UR";
	protected static final String SQL_UPDATE_TRANS_DETAILS     = "UPDATE DP_DIST_STOCK_TRANSFER SET DC_NO = ?, STATUS = ?, DC_DATE = CURRENT_DATE, "
		                                                       + "UPDATED_BY = ?, UPDATED_DATE = CURRENT_TIMESTAMP, TRANSFERRED_QTY = ? WHERE REQ_ID = ? WITH UR";
	protected static final String SQL_SELECT_CONTACT_NAME      = "SELECT CONTACT_NAME FROM VR_ACCOUNT_DETAILS WHERE ACCOUNT_ID = ? WITH UR";
	protected static final String SQL_SELECT_DCNO              = "SELECT 'TRNSDC'||CHAR(SEQ_STOCK_TRANSFER_DC.NEXTVAL) FROM SYSIBM.SYSDUMMY1";
	protected static final String SQL_SELECT_SERIAL_NOS        = "SELECT SERIAL_NO FROM DP_STOCK_INVENTORY WHERE MARK_DAMAGED = 'N' "
		   												       + "AND DISTRIBUTOR_ID = ? AND PRODUCT_ID = ? AND FSE_ID IS NULL " 
		   												       + "AND RETAILER_ID IS NULL WITH UR";		
	protected static final String SQL_INSERT_SERIAL_NOS        = "INSERT INTO  DP_DDST_SERIAL_NO (REQ_ID, DC_NO, SERIAL_NO, PRODUCT_ID, ACTION) VALUES (?, ?, ?, ?, ?) WITH UR";
	private static final String SQL_UPDATE_STOCK_INVENTORY     = "UPDATE DP_STOCK_INVENTORY SET MARK_DAMAGED = 'I' WHERE PRODUCT_ID = ? AND SERIAL_NO = ? WITH UR";
	
	protected static final String SQL_SELECT_INTER_SSD_TRANSFER="select dhth.TR_NO,case dhth.TRANS_TYPE when '"+Constants.INTER_SSD_FSE_TRANSFER+"' then '"+Constants.INTER_SSD_FSE_TRANSFER_DISPLAY+"' when '"+Constants.INTER_SSD_RETAILE_TRANSFER+"' then '"+Constants.INTER_SSD_RETAILER_TRANSFER_DISPLAY+"' end as TRANS_TYPE, "
														+" case dhth.TRANS_SUB_TYPE when '"+Constants.INTER_SSD_WITH_RETAILER+"' then '"+Constants.INTER_SSD_WITH_RETAILER_DISPLAY+"' when '"+Constants.INTER_SSD_WITH_OUT_RETAILER+"' then '"+Constants.INTER_SSD_WITH_OUT_RETAILER_DISPLAY+"' else '' end as TRANS_SUB_TYPE, " 
														+" ( select ACCOUNT_NAME from VR_ACCOUNT_DETAILS where ACCOUNT_ID=dhth.FROM_DIST) as from_Dist_Name,( select CIRCLE_NAME from VR_CIRCLE_MASTER where CIRCLE_ID=dhth.CIRCLE_ID) as circle "
														+" ,dhth.INIT_UNIT,dhth.TRANS_UNIT, "
														+" ( select ACCOUNT_NAME from VR_ACCOUNT_DETAILS where ACCOUNT_ID=dhth.CREATED_BY) as created_by, "
														+" to_char(dhth.CREATED_ON,'dd/MM/yyyy') as CREATED_ON "
														+" from DP_HEIRARCHY_TRNS_HDR dhth where  dhth.circle_id =  ? order by dhth.CREATED_ON DESC with ur" ;
														

	protected static final String SQL_SELECT_INTER_SSD_TRANSFER_DETAILS="select b.TR_NO as TR_NO, a.TRNS_ACCOUNT_ID , STATUS," 
																	+" CASE STATUS WHEN 'INIT' THEN 'REQUESTED' WHEN 'FWD' THEN 'FORWARDED' WHEN 'ACPT' THEN 'ACCEPTED' END ACTION,"
																	+" (select Account_name from VR_ACCOUNT_DETAILS where ACCOUNT_ID=a.TRNS_ACCOUNT_ID) as trans_name, "
																	+" (select Account_name from VR_ACCOUNT_DETAILS where ACCOUNT_ID=a.TO_DIST) as TO_DIST, "
																	+" (select Account_name from VR_ACCOUNT_DETAILS where ACCOUNT_ID=TO_FSE) as TO_FSE, "
																	+" b.TRANS_TYPE ,"											
																	+" (select level_name from VR_ACCOUNT_DETAILS act,VR_ACCOUNT_LEVEL_MASTER levelmst  where act.ACCOUNT_ID=a.TRNS_ACCOUNT_ID and act.account_level=levelmst.level_id) as level_name, "
																	+" (select Mobile_number from VR_ACCOUNT_DETAILS where ACCOUNT_ID=a.TRNS_ACCOUNT_ID) as Mobile_number, "
																	+" (select zone_name from VR_ACCOUNT_DETAILS act,DP_ZONE_MASTER zonemst where ACCOUNT_ID=a.TRNS_ACCOUNT_ID and act.zone_id=zonemst.zone_id) as zone_name, "
																	+" (select city_name from VR_ACCOUNT_DETAILS act,VR_CITY_MASTER citymst where ACCOUNT_ID=a.TRNS_ACCOUNT_ID and act.city_id=citymst.city_id) as city_name, "
																	+" (select contact_name from VR_ACCOUNT_DETAILS act where ACCOUNT_ID=a.TRNS_ACCOUNT_ID ) as contact_name "
																	+" from  DP_HEIRARCHY_TRNS_DTL a,DP_HEIRARCHY_TRNS_HDR b "
																	+" where a.TR_NO=b.TR_NO "
																	+" and b.TR_NO=? with ur" ;
	

	protected static final String SQL_SELECT_HIRERCHY_TSM_LIST="select ACCOUNT_ID,ACCOUNT_NAME from VR_ACCOUNT_DETAILS where CIRCLE_ID  = ? and ACCOUNT_LEVEL=5 order by ACCOUNT_NAME";

	protected static final String SQL_SELECT_HIRERCHY_DIST_LIST="select ACCOUNT_ID,ACCOUNT_NAME from VR_ACCOUNT_DETAILS where CIRCLE_ID  = ? and ACCOUNT_LEVEL=6 order by ACCOUNT_NAME WITH UR";

	
	protected static final String SQL_SELECT_HIRERCHY_CURRENT_DIST="select b.ACCOUNT_NAME,b.ACCOUNT_ID from DP_HEIRARCHY_TRNS_HDR a ,VR_ACCOUNT_DETAILS b where a.FROM_DIST=b.ACCOUNT_ID and a.TR_NO= ? order by ACCOUNT_NAME WITH UR";
	
	protected static final String SQL_SELECT_HIRERCHY_CURRENT_FSE="select b.ACCOUNT_NAME,b.ACCOUNT_ID from DP_HEIRARCHY_TRNS_HDR a ,VR_ACCOUNT_DETAILS b where a.FROM_FSE=b.ACCOUNT_ID and a.TR_NO= ? order by ACCOUNT_NAME WITH UR";

	protected static final String SQL_UPDATE_HIRERCHY_TRANSFER_RET="update DP_HEIRARCHY_TRNS_DTL set TO_DIST=?,TO_FSE=? , STATUS=? ,TRANSFER_BY=? ,"
															+" TRANSFER_ON=current timestamp where TR_NO=? and TRNS_ACCOUNT_ID=? and status='INIT' WITH UR";
				
	protected static final String SQL_UPDATE_HIRERCHY_TRANSFER_FSE="update DP_HEIRARCHY_TRNS_DTL set TO_DIST=? , STATUS=? ,TRANSFER_BY=?, "
															+" TRANSFER_ON=current timestamp where TR_NO=? and TRNS_ACCOUNT_ID=? and status='INIT' WITH UR";
	protected static final String SQL_UPDATE_HIRERCHY_TRANSFER_HDR="update DP_HEIRARCHY_TRNS_HDR set TRANS_UNIT=TRANS_UNIT+?  where TR_NO=? WITH UR";
	
	
	public StockTransferDaoDB2(Connection con) {
		super(con);
	}
	
	public List<DistributorDTO> getFromDistributors(long accountId){
		ResultSet rst = null;
		PreparedStatement pst = null;
		List<DistributorDTO> distributorFromList = new ArrayList<DistributorDTO>();
		try {
			pst = connection.prepareStatement(SQL_SELECT_FROM_DISTRIBUTORS);
			//logger.info("TRANSFER REQ BY QUERY  ::  "+SQL_SELECT_FROM_DISTRIBUTORS);
			pst.setLong(1, accountId);
			//logger.info("PARAM 1  ::  "+accountId);
			rst = pst.executeQuery();
			processResultSetToDistributorDTO(rst, distributorFromList);
		} catch (SQLException e) {
			logger.error("**-> getFromDistributors function of  StockTransferDaoDB2 " + e);
		} catch (Exception ex) {
			logger.error("**-> getFromDistributors function of  StockTransferDaoDB2 " + ex);
		} finally {
			DBConnectionManager.releaseResources(pst, rst);
		}
		return distributorFromList;
	}
	
	public List<DistributorDTO> getToDistributors(long accountId){
		ResultSet rst = null;
		PreparedStatement pst = null;
		List<DistributorDTO> distributorToList = new ArrayList<DistributorDTO>();
		try {
			//logger.info("QUERY TO GET TO DIST  ::  "+SQL_SELECT_TO_DISTRIBUTORS);
			pst = connection.prepareStatement(SQL_SELECT_TO_DISTRIBUTORS);
			pst.setLong(1, accountId);
			//logger.info("PARAM 1  ::  "+accountId);
			rst = pst.executeQuery();
			processResultSetToDistributorDTO(rst, distributorToList);
		} catch (SQLException e) {
			logger.error("**-> getDistributors function of  StockTransferDaoDB2 " + e);
		} catch (Exception ex) {
			logger.error("**-> getDistributors function of  StockTransferDaoDB2 " + ex);
		} finally {
			DBConnectionManager.releaseResources(pst, rst);
		}
		return distributorToList;
	}

	public List<DistributorDetailsDTO> getTransferDetails(long accountId, long fromDistId, long toDistId) {
		ResultSet rst = null;
		PreparedStatement pst = null;
		List<DistributorDetailsDTO> distributorDetailsList = new ArrayList<DistributorDetailsDTO>();
		try {
			pst = connection.prepareStatement(SQL_SELECT_TRANS_DETAILS);
			
			pst.setLong(1, accountId);
			pst.setLong(2, toDistId);
			pst.setLong(3, fromDistId);
			rst = pst.executeQuery();
			processResultSetToDistributorDetailsDTO(rst, distributorDetailsList);
		} catch (SQLException e) {
			logger.error("**-> getTransferDetails function of  StockTransferDaoDB2 " + e);
		} catch (Exception ex) {
			logger.error("**-> getTransferDetails function of  StockTransferDaoDB2 " + ex);
		} finally {
			DBConnectionManager.releaseResources(pst, rst);
		}
		return distributorDetailsList;
	}
	
	public void processResultSetToDistributorDTO(ResultSet rst, List<DistributorDTO> distributorFromList) throws SQLException {
		DistributorDTO distributorDTO;
		while (rst.next()) {
			distributorDTO = new DistributorDTO();
			distributorDTO.setAccountId(rst.getString("ACCOUNT_ID"));
			distributorDTO.setAccountName(rst.getString("ACCOUNT_NAME"));
			distributorFromList.add(distributorDTO);
		}
	}

	public void processResultSetToDistributorDetailsDTO(ResultSet rst, List<DistributorDetailsDTO> distributorDetailsList) throws SQLException {
		DistributorDetailsDTO distributorDetailsDTO;
		while (rst.next()) {
			distributorDetailsDTO = new DistributorDetailsDTO();
			distributorDetailsDTO.setProductId(rst.getString("PRODUCT_ID"));
			distributorDetailsDTO.setProductName(rst.getString("PRODUCT_NAME"));
			distributorDetailsDTO.setRequestedQuantity(rst.getString("REQ_TRANSFER_QTY"));
			distributorDetailsDTO.setRequestedId(rst.getString("REQ_ID"));
			distributorDetailsList.add(distributorDetailsDTO);
		}
	}

	public String getDCNumber(){
		ResultSet rst = null;
		PreparedStatement pst = null;
		String dcNumber = "";
		try {
			pst = connection.prepareStatement(SQL_SELECT_DCNO);
			rst = pst.executeQuery();
			if(rst.next()){
				dcNumber = rst.getString(1);
			}
		} catch (SQLException e) {
			logger.error("**-> getContactNameForAccontId function of  StockTransferDaoDB2 " + e);
		} catch (Exception ex) {
			logger.error("**-> getContactNameForAccontId function of  StockTransferDaoDB2 " + ex);
		} finally {
			DBConnectionManager.releaseResources(pst, rst);
		}
		return dcNumber;
	}
	
	public String submitTransferDetails(StockTransferFormBean stfb, List<DistributorDetailsDTO> distributorDetailsList, long accountId){
		PreparedStatement pstUpd = null;
		PreparedStatement pstIns = null;
		PreparedStatement pstUpdSI = null;
		String submitResult = "";
		String dcNo = stfb.getDcNumber();
		String status[] = stfb.getAction();
		String transQuan[] = stfb.getTotalCount();
		String serialNos[] = stfb.getSerialNos();
		
		try {
			connection.setAutoCommit(false);
			pstUpd = connection.prepareStatement(SQL_UPDATE_TRANS_DETAILS);
			pstIns = connection.prepareStatement(SQL_INSERT_SERIAL_NOS);
			pstUpdSI = connection.prepareStatement(SQL_UPDATE_STOCK_INVENTORY);

			int records = status.length;
			int intUpd = 0;
			int serNosLen, prodID;
			String reqID;
			
			for (int i = 0; i < records; i++) 
			{
				reqID = distributorDetailsList.get(i).getRequestedId();
				prodID = Integer.parseInt(distributorDetailsList.get(i).getProductId());
				pstUpd.setString(1, dcNo);
				pstUpd.setString(2, status[i]);
				pstUpd.setLong(3, accountId);
				
				if(status[i]==null || status[i].equals("0"))
				{
					continue;
				}
				else if (status[i].equals(Constants.ST_ACTION_TRANSFER)) 
				{
					pstUpd.setInt(4, Integer.parseInt(transQuan[i]));
				} 
				else if (status[i].equals(Constants.ST_ACTION_CANCEL)) 
				{
					pstUpd.setInt(4, 0);
				}
				
				pstUpd.setString(5, reqID);
				intUpd = pstUpd.executeUpdate();

				if (status[i].equals(Constants.ST_ACTION_TRANSFER)) 
				{
					String selectedSerialNo = serialNos[i];
					String reqdSerialNo[] = selectedSerialNo.split(",");
					serNosLen = reqdSerialNo.length;

					for (int n = 0; n < serNosLen; n++) 
					{
						String serialNo = reqdSerialNo[n];
						if("UNDEFINED".equalsIgnoreCase(serialNo))
						{
							continue;
						}
						pstIns.setString(1, reqID);
						pstIns.setString(2, dcNo);
						pstIns.setString(3, serialNo);
						pstIns.setInt(4, prodID);
						pstIns.setString(5, Constants.ST_ACTION_INTRANSIT);
						intUpd = pstIns.executeUpdate();
						
						//Update DP_STOCK_INVENTORY Table
						pstUpdSI.setInt(1, prodID);
						pstUpdSI.setString(2, serialNo);
						intUpd = pstUpdSI.executeUpdate();						
					}
				}
			}
			connection.commit();
			submitResult = Constants.SUCCESS_MESSAGE;
		} catch (SQLException e) {
			submitResult = Constants.ERROR_MESSAGE;
			try {
				connection.rollback();
			} catch (SQLException e1) {
				logger.error("**-> submitTransferDetails function of  StockTransferDaoDB2 " + e1);
			}
			logger.error("**-> submitTransferDetails function of  StockTransferDaoDB2 " + e);
		} catch (Exception ex) {
			logger.error("**-> submitTransferDetails function of  StockTransferDaoDB2 " + ex);
		} finally {
			pstUpd = null;
			pstIns = null;
			pstUpdSI = null;
		}
		return submitResult;
	}

	public String getContactNameForAccontId(long accountId){
		ResultSet rst = null;
		PreparedStatement pst = null;
		String contactName = "";
		try {
			pst = connection.prepareStatement(SQL_SELECT_CONTACT_NAME);
			pst.setLong(1, accountId);
			rst = pst.executeQuery();
			if(rst.next()){
				contactName = rst.getString("CONTACT_NAME");
			}
		} catch (SQLException e) {
			logger.error("**-> getContactNameForAccontId function of  StockTransferDaoDB2 " + e);
		} catch (Exception ex) {
			logger.error("**-> getContactNameForAccontId function of  StockTransferDaoDB2 " + ex);
		} finally {
			DBConnectionManager.releaseResources(pst, rst);
		}
		return contactName;
	}

	public List getSerailNumberList(long accountId, long productId){
		ResultSet rst = null;
		PreparedStatement pst = null;
		List serailNumberList = new ArrayList();
		try {
			pst = connection.prepareStatement(SQL_SELECT_SERIAL_NOS);
			pst.setLong(1, accountId);
			pst.setLong(2, productId);
			rst = pst.executeQuery();
			while (rst.next()) {
				serailNumberList.add(rst.getString("SERIAL_NO"));
			}
		} catch (SQLException e) {
			logger.error("**-> getTransferDetails function of  StockTransferDaoDB2 " + e);
		} catch (Exception ex) {
			logger.error("**-> getTransferDetails function of  StockTransferDaoDB2 " + ex);
		} finally {
			DBConnectionManager.releaseResources(pst, rst);
		}
		return serailNumberList;
	}
	public List<InterSSDTransferDTO> interSSDTrans(Long accountId){
		ResultSet rst = null;
		PreparedStatement pst = null;
		List<InterSSDTransferDTO> interSSDList = new ArrayList<InterSSDTransferDTO>();
		InterSSDTransferDTO interSSDTransferDTO = null;
		try {
			pst = connection.prepareStatement(SQL_SELECT_INTER_SSD_TRANSFER);
			pst.setLong(1, accountId);
			rst = pst.executeQuery();
			while (rst.next()) {
				
				interSSDTransferDTO= new InterSSDTransferDTO();
				
				interSSDTransferDTO.setTr_no(rst.getString("TR_NO"));
				if(rst.getString("TRANS_SUB_TYPE") != null && !("".equals(rst.getString("TRANS_SUB_TYPE"))))
				{ 
					interSSDTransferDTO.setTr_type(rst.getString("TRANS_TYPE")+" ( "+rst.getString("TRANS_SUB_TYPE")+" )");
				}
				else
				{
					interSSDTransferDTO.setTr_type(rst.getString("TRANS_TYPE"));
				}
				interSSDTransferDTO.setTr_sub_type_int(rst.getString("TRANS_SUB_TYPE"));
				interSSDTransferDTO.setTr_type_int(rst.getString("TRANS_TYPE"));
				interSSDTransferDTO.setFrom_dist(rst.getString("FROM_DIST_NAME"));
				interSSDTransferDTO.setCircle_id(rst.getString("circle"));
				interSSDTransferDTO.setInit_unit(rst.getString("INIT_UNIT"));
				interSSDTransferDTO.setTrans_unit(rst.getString("TRANS_UNIT"));
				interSSDTransferDTO.setCreated_by(rst.getString("CREATED_BY"));
				interSSDTransferDTO.setCreated_on(rst.getString("CREATED_ON"));
				interSSDTransferDTO.setRemain_qty((rst.getInt("INIT_UNIT") - rst.getInt("TRANS_UNIT"))+"");
				
				interSSDList.add(interSSDTransferDTO);
				interSSDTransferDTO = null;
			}
		} catch (SQLException e) {
			logger.error("**-> interSSDTrans function of  StockTransferDaoDB2 " + e);
		} catch (Exception ex) {
			logger.error("**-> interSSDTrans function of  StockTransferDaoDB2 " + ex);
		} finally {
			DBConnectionManager.releaseResources(pst, rst);
		}
		return interSSDList;
	}
	
	
	public Map interSSDTransDetails(String trans_no,String transType,String transsubtype,int circle_id){
		ResultSet rst = null;
		ResultSet rst1 = null;
		ResultSet rst2 = null;
		ResultSet rst3 = null;
		PreparedStatement pst = null;
		PreparedStatement pst1 = null;
		PreparedStatement pst2 = null;
		PreparedStatement pst3 = null;
		List<InterSSDTransferDTO> interSSDList = new ArrayList<InterSSDTransferDTO>();
		List<InterSSDTransferDTO> interSSDDistList = new ArrayList<InterSSDTransferDTO>();
		List<InterSSDTransferDTO> interSSDCurrDistList = new ArrayList<InterSSDTransferDTO>();
		List<InterSSDTransferDTO> interSSDCurrFseList = new ArrayList<InterSSDTransferDTO>();
		Map hashMap = new HashMap();
		InterSSDTransferDTO interSSDTransferDTO = null;
		List listFinal = new ArrayList();
		try {
			pst = connection.prepareStatement(SQL_SELECT_INTER_SSD_TRANSFER_DETAILS);
			pst.setString(1, trans_no);
			rst = pst.executeQuery();
			while (rst.next()) {
				
				interSSDTransferDTO= new InterSSDTransferDTO();
				interSSDTransferDTO.setTr_no(rst.getString("TR_NO"));
				interSSDTransferDTO.setAccount_id(rst.getString("TRNS_ACCOUNT_ID"));
				interSSDTransferDTO.setTrans_name(rst.getString("TRANS_NAME"));
				interSSDTransferDTO.setLevel_name(rst.getString("LEVEL_NAME"));
				interSSDTransferDTO.setContact_number(rst.getString("MOBILE_NUMBER"));
				interSSDTransferDTO.setZone_name(rst.getString("ZONE_NAME"));
				interSSDTransferDTO.setCity_name(rst.getString("CITY_NAME"));
				interSSDTransferDTO.setContact_name(rst.getString("CONTACT_NAME"));
				interSSDTransferDTO.setStatus(rst.getString("STATUS"));
				
				interSSDTransferDTO.setTo_dist(rst.getString("TO_DIST"));
				interSSDTransferDTO.setTo_fse(rst.getString("TO_FSE"));
				interSSDTransferDTO.setTr_type(rst.getString("TRANS_TYPE"));
				interSSDTransferDTO.setStrAction(rst.getString("ACTION"));
				
				interSSDList.add(interSSDTransferDTO);
				interSSDTransferDTO = null;
			}
			hashMap.put("details",interSSDList);
			//listFinal.add(interSSDList);
			
			pst1 = connection.prepareStatement(SQL_SELECT_HIRERCHY_TSM_LIST);
			pst1.setInt(1, circle_id);
			rst1 = pst1.executeQuery();
			interSSDTransferDTO = null;
			while (rst1.next()) {
				
				interSSDTransferDTO= new InterSSDTransferDTO();
				interSSDTransferDTO.setAccount_name(rst1.getString("ACCOUNT_NAME"));
				interSSDTransferDTO.setAccount_id(rst1.getString("ACCOUNT_ID"));
				
				interSSDDistList.add(interSSDTransferDTO);
				interSSDTransferDTO = null;
			}
			hashMap.put("distList",interSSDDistList);
			//listFinal.add(interSSDDistList);
			
			
			pst2 = connection.prepareStatement(SQL_SELECT_HIRERCHY_CURRENT_DIST);
			pst2.setString(1, trans_no);
			rst2 = pst2.executeQuery();
			interSSDTransferDTO = null;
			while (rst2.next()) {
				
				interSSDTransferDTO= new InterSSDTransferDTO();
				interSSDTransferDTO.setAccount_name(rst2.getString("ACCOUNT_NAME"));
				interSSDTransferDTO.setAccount_id(rst2.getString("ACCOUNT_ID"));
				interSSDCurrDistList.add(interSSDTransferDTO);				
			}
			hashMap.put("currdistList",interSSDCurrDistList);
			//listFinal.add(interSSDCurrDistList);
			
			if(transType.equalsIgnoreCase(Constants.INTER_SSD_RETAILER_TRANSFER_DISPLAY))
			{
				interSSDTransferDTO = null;
				pst3 = connection.prepareStatement(SQL_SELECT_HIRERCHY_CURRENT_FSE);
				pst3.setString(1, trans_no);
				rst3 = pst3.executeQuery();
				while (rst3.next()) {
					
					interSSDTransferDTO= new InterSSDTransferDTO();
					interSSDTransferDTO.setAccount_name(rst3.getString("ACCOUNT_NAME"));
					interSSDTransferDTO.setAccount_id(rst3.getString("ACCOUNT_ID"));
					interSSDCurrFseList.add(interSSDTransferDTO);	
				}
				
				//listFinal.add(interSSDCurrFseList);
				hashMap.put("currfseList",interSSDCurrFseList);
			}
			
			
			
			
			
			
		} catch (SQLException e) {
			logger.error("**-> interSSDTransDetails function of  StockTransferDaoDB2 " + e);
		} catch (Exception ex) {
			logger.error("**-> interSSDTransDetails function of  StockTransferDaoDB2 " + ex);
		} finally {
			DBConnectionManager.releaseResources(pst, rst);
			DBConnectionManager.releaseResources(pst1, rst1);
			DBConnectionManager.releaseResources(pst2, rst2);
			DBConnectionManager.releaseResources(pst3, rst3);
			
		}
		return hashMap;
	}
	
	public List<InterSSDTransferDTO> distList(int circle_id){
		ResultSet rst = null;
		PreparedStatement pst = null;
		List<InterSSDTransferDTO> interSSDList = new ArrayList<InterSSDTransferDTO>();
		InterSSDTransferDTO interSSDTransferDTO = null;
		try {
			pst = connection.prepareStatement(SQL_SELECT_HIRERCHY_TSM_LIST);
			pst.setInt(1, circle_id);
			rst = pst.executeQuery();
			while (rst.next()) {
				
				interSSDTransferDTO= new InterSSDTransferDTO();
				interSSDTransferDTO.setAccount_name(rst.getString("ACCOUNT_NAME"));
				interSSDTransferDTO.setAccount_id(rst.getString("ACCOUNT_ID"));
				
				interSSDList.add(interSSDTransferDTO);
				interSSDTransferDTO = null;
			}
		} catch (SQLException e) {
			logger.error("**-> distList function of  StockTransferDaoDB2 " + e);
		} catch (Exception ex) {
			logger.error("**-> distList function of  StockTransferDaoDB2 " + ex);
		} finally {
			DBConnectionManager.releaseResources(pst, rst);
		}
		return interSSDList;
	}
	
	
	public String hirarchyTransfer(List<InterSSDTransferDTO> list){
		ResultSet rst = null;
		PreparedStatement pst = null;
		PreparedStatement pst1 = null;
		List<InterSSDTransferDTO> interSSDList = new ArrayList<InterSSDTransferDTO>();
		InterSSDTransferDTO interSSDTransferDTO = null;
		int i= 0 ;
		int rowCount=0;
		String tr_no="";
		int qty=0;
		int hdrCount = 0;
		try {
			qty = list.size();
			Iterator itt = list.iterator();
			connection.setAutoCommit(false);
			while(itt.hasNext())
			{
				interSSDTransferDTO = (InterSSDTransferDTO) itt.next();
				tr_no = interSSDTransferDTO.getTr_no();
				
				if(interSSDTransferDTO.getTransaction_type().equalsIgnoreCase(Constants.INTER_SSD_RETAILER_TRANSFER_DISPLAY))
				{
					pst = connection.prepareStatement(SQL_UPDATE_HIRERCHY_TRANSFER_RET);
					System.out.println(SQL_UPDATE_HIRERCHY_TRANSFER_RET);
					pst.setInt(1, Integer.parseInt(interSSDTransferDTO.getTo_dist()));
					pst.setInt(2, Integer.parseInt(interSSDTransferDTO.getTo_fse()));
					pst.setString(3, "FWD");
					pst.setInt(4,Integer.parseInt(interSSDTransferDTO.getTransfer_by()));
					pst.setString(5, interSSDTransferDTO.getTr_no());
					pst.setInt(6, Integer.parseInt(interSSDTransferDTO.getAccount_id()));
					
				}
				else
				{
					pst = connection.prepareStatement(SQL_UPDATE_HIRERCHY_TRANSFER_FSE);
					pst.setInt(1, Integer.parseInt(interSSDTransferDTO.getTo_dist()));
					pst.setString(2, "FWD");
					pst.setInt(3,Integer.parseInt(interSSDTransferDTO.getTransfer_by()));
					pst.setString(4, interSSDTransferDTO.getTr_no());
					pst.setInt(5, Integer.parseInt(interSSDTransferDTO.getAccount_id()));
				}
				rowCount = rowCount + pst.executeUpdate();
			}
			
			
			if(rowCount > 0)
			{
				pst1 = connection.prepareStatement(SQL_UPDATE_HIRERCHY_TRANSFER_HDR);
				System.out.println(SQL_UPDATE_HIRERCHY_TRANSFER_HDR);
				pst1.setInt(1, rowCount);
				pst1.setString(2, tr_no);
				System.out.println("qty-----"+qty);
				System.out.println("tr_no-----"+tr_no);
				hdrCount = pst1.executeUpdate();
			}
			if(hdrCount > 0)
			{
				connection.commit();
				connection.setAutoCommit(true);
			}
			else
			{
				connection.rollback();
			}
			
			
		} catch (SQLException e) {
			try
			{
				connection.rollback();
			}
			catch(Exception ex)
			{
				logger.error("**-> hirarchyTransfer function of  StockTransferDaoDB2 " + ex);
			}
			System.out.println(e);
			logger.error("**-> hirarchyTransfer function of  StockTransferDaoDB2 " + e);
		} catch (Exception ex) {
			try
			{
				connection.rollback();
			}
			catch(Exception ex1)
			{
				logger.error("**-> hirarchyTransfer function of  StockTransferDaoDB2 " + ex1);
			}
			System.out.println(ex);
			logger.error("**-> hirarchyTransfer function of  StockTransferDaoDB2 " + ex);
		} finally {
			try
			{
				connection.setAutoCommit(true);
			}
			catch(Exception ex2)
			{
				
			}
			DBConnectionManager.releaseResources(pst, rst);
			DBConnectionManager.releaseResources(pst1, rst);
		}
		return i+"";
	}
	
	
	public InterSSDTransferDTO getCurrentDist(String tr_no){
		ResultSet rst = null;
		PreparedStatement pst = null;
		List<InterSSDTransferDTO> interSSDList = new ArrayList<InterSSDTransferDTO>();
		InterSSDTransferDTO interSSDTransferDTO = null;
		try {
			pst = connection.prepareStatement(SQL_SELECT_HIRERCHY_CURRENT_DIST);
			pst.setString(1, tr_no);
			rst = pst.executeQuery();
			while (rst.next()) {
				
				interSSDTransferDTO= new InterSSDTransferDTO();
				interSSDTransferDTO.setAccount_name(rst.getString("ACCOUNT_NAME"));
				interSSDTransferDTO.setAccount_id(rst.getString("ACCOUNT_ID"));
				
			}
		} catch (SQLException e) {
			logger.error("**-> getCurrentDist function of  StockTransferDaoDB2 " + e);
		} catch (Exception ex) {
			logger.error("**-> getCurrentDist function of  StockTransferDaoDB2 " + ex);
		} finally {
			DBConnectionManager.releaseResources(pst, rst);
		}
		
	return interSSDTransferDTO;
	}
	
	
	
	
	
}