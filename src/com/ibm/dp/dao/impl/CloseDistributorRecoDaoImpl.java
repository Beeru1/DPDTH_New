package com.ibm.dp.dao.impl;

import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;

import com.ibm.core.exception.DAOException;
import com.ibm.dp.beans.RecoPeriodConfFormBean;
import com.ibm.dp.common.Constants;
import com.ibm.dp.common.DBQueries;
import com.ibm.dp.dao.CloseDistributorRecoDao;
import com.ibm.dp.dao.RecoDetailReportDao;
import com.ibm.dp.dao.STBFlushOutDao;
import com.ibm.dp.dao.impl.STBFlushOutDaoImpl;
import com.ibm.dp.dto.DPDistPinCodeMapDto;
import com.ibm.dp.dto.DistRecoDto;
import com.ibm.dp.dto.DuplicateSTBDTO;
import com.ibm.dp.dto.PrintRecoDto;
import com.ibm.dp.dto.RecoDetailReportDTO;
import com.ibm.dp.dto.RecoPeriodDTO;
import com.ibm.dpmisreports.common.DropDownUtility;
import com.ibm.dpmisreports.common.SelectionCollection;
import com.ibm.utilities.Utility;
import com.ibm.utilities.ValidatorUtility;
import com.ibm.virtualization.recharge.common.ResourceReader;
import com.ibm.virtualization.recharge.db.DBConnectionManager;

 
public class CloseDistributorRecoDaoImpl implements  CloseDistributorRecoDao{
	
	private static Logger logger = Logger.getLogger(CloseDistributorRecoDaoImpl.class);	
	public static final String SQL_SELECT_DP_RECO_CERTIFICATE_DETAIL = DBQueries.SQL_SELECT_DP_RECO_CERTIFICATE_DETAIL_RECO;
	public static final String SQL_SELECT_DP_RECO_CERTIFICATE_DETAIL1 = DBQueries.SQL_SELECT_DP_RECO_CERTIFICATE_DETAIL_RECO1;
	public static final String SQL_SELECT_ALL_DIST_CIRCLE="select ACCOUNT_ID from VR_ACCOUNT_DETAILS where CIRCLE_ID=? and ACCOUNT_LEVEL=6 with ur";
	public static final String SQL_SELECT_DIST_CHK_EXISTS 	= DBQueries.SQL_SELECT_DIST_CHK_EXISTS;
	
	private static CloseDistributorRecoDao recoDetailReportDao = new CloseDistributorRecoDaoImpl();
	private CloseDistributorRecoDaoImpl(){}
	public static CloseDistributorRecoDao getInstance() {
		return recoDetailReportDao;
	}
	public List<RecoDetailReportDTO> getRecoReportDetails(int recoID, String circleId, String productId, Integer groupId, long accountID)  {
		PreparedStatement ps = null;
		Connection db2conn = null; 
		ResultSet rs = null; 
		logger.info("reco_ID  ::  "+recoID);
		List<RecoDetailReportDTO> recoList= new ArrayList<RecoDetailReportDTO>();
		//List<RecoDetailReportDTO> recoListNew = new ArrayList<RecoDetailReportDTO>();
		
		try 
		{
			db2conn = DBConnectionManager.getDBConnection();
			String productIdStr=DropDownUtility.getInstance().getProductIds(db2conn, productId,circleId);
								
		/*	String sql = "Select (SELECT LOGIN_NAME FROM VR_LOGIN_MASTER where LOGIN_ID=RD.DIST_ID)  AS DISTRIBUTOR_OLM_ID ," +
					"(SELECT CONTACT_NAME FROM VR_ACCOUNT_DETAILS where ACCOUNT_ID=RD.DIST_ID) AS DISTRIBUTOR_NAME," +
					"CASE CREATED_BY WHEN 0 then 'SYSTEM GENERATED' ELSE (SELECT ACCOUNT_NAME FROM VR_ACCOUNT_DETAILS where ACCOUNT_ID=RD.DIST_ID)" +
					" END AS TYPE, (SELECT PRODUCT_NAME from DP_PRODUCT_MASTER WHERE PRODUCT_ID=RD.PRODUCT_ID ) AS PRODUCT_NAME, " +
					"(RD.SER_OPEN_STOCK + RD.NSER_OPEN_STOCK) AS OPEN_STOCK, RECEIVED_WH, RECEIVED_INTRANSIT, RECEIVED_INTER_SSD, RECEIVED_UPGRADE, RECEIVED_CHURN," +
					"RETURNED_FRESH, RETURNED_INTRANSIT, RETURNED_INTER_SSD, RETURNED_DOA_BI, RETURNED_DOA_AI, RETURNED_SWAP, RETURNED_C2S," +
					"( SER_ACTIVATION + NSER_ACTIVATION) AS ACTIVATION, CLOSING_STOCK from DP_RECO_DIST_DETAILS RD where RECO_ID=?  " ;
		*/
			String sql = "Select (SELECT  DISTRIBUTOR_OLM_ID FROM DP_DIST_DETAILS where DISTRIBUTOR_ID=RD.DIST_ID fetch first 1 row only)  AS DISTRIBUTOR_OLM_ID ," +
			" (SELECT DISTRIBUTOR_NAME FROM DP_DIST_DETAILS where DISTRIBUTOR_ID=RD.DIST_ID fetch first 1 row only) AS DISTRIBUTOR_NAME," +
			" CASE CREATED_BY WHEN 0 then 'SYSTEM GENERATED' ELSE (SELECT DISTRIBUTOR_NAME FROM DP_DIST_DETAILS where DISTRIBUTOR_ID=RD.DIST_ID fetch first 1 row only)" +
			" END AS TYPE, (SELECT PRODUCT_NAME from DP_PRODUCT_MASTER WHERE PRODUCT_ID=RD.PRODUCT_ID ) AS PRODUCT_NAME, " +
			" RD.PENDING_PO_OPENING,RD.PENDING_DC_OPENING,RD.SER_OPEN_STOCK, RD.NSER_OPEN_STOCK, RD.DEFECTIVE_OPEN_STOCK,RD.UPGRADE_OPEN_STOCK,RD.CHURN_OPEN_STOCK, RD.RECEIVED_WH, " +
			" (RD.RECEIVED_INTER_SSD_OK +RD.RECEIVED_HIERARCHY_TRANS) as RecInterSSDOK ,RD.RECEIVED_INTER_SSD_DEF,RD.RECEIVED_UPGRADE, RD.RECEIVED_CHURN, " +
			" RD.RETURNED_FRESH, (RD.RETURNED_INTER_SSD_OK+RD.RETURNED_HIERARCHY_TRANS) as RetInterSSDOK, RD.RETURNED_INTER_SSD_DEF, RD.RETURNED_DOA_BI, RD.RETURNED_DOA_AI, RD.RETURNED_SWAP, RD.RETURNED_C2S,RD.RETURNED_CHURN,(RD.FLUSH_OUT_OK+RD.FLUSH_OUT_DEFECTIVE-RD.RECO_QTY) AS ADJUSTMENT , " +
			" RD.INVENTORY_CHANGE,( SER_ACTIVATION + NSER_ACTIVATION) AS ACTIVATION, RD.SER_CLOSING_STOCK,RD.NSER_CLOSING_STOCK," +
			" RD.DEF_CLOSING_STOCK,UPGRADE_CLOSING_STOCK,CHURN_CLOSING_STOCK,PENDING_PO_CLOSING,PENDING_DC_CLOSING,RECO_STATUS from DP_RECO_DIST_DETAILS RD " +
			" where RECO_ID=?  " ;

			if(circleId != null && !circleId.equals("")) 
			{		
				if(!circleId.contains(","))
				{
					sql+= " AND CIRCLE_ID = "+ circleId;
				}
				else
				{					
					sql+= " AND CIRCLE_ID IN("+ circleId +")";
				}
			}
			if(productIdStr != null && !productIdStr.equals("")) 
			{		
				if(!productIdStr.contains(","))
				{
					sql+= " AND PRODUCT_ID = "+ productIdStr;
				}
				else
				{					
					sql+= " AND PRODUCT_ID IN("+ productIdStr +")";
				}
			}
			//Added by nazim hussain to make report available against multiplt circle for TSZ/ZSM/ZBM in CR81050
			if(groupId==6)
			{
				sql+=" AND DIST_ID in (select distinct DP_DIST_ID from DP_DISTRIBUTOR_MAPPING where PARENT_ACCOUNT =?) ";
			}
			
			sql+= " ORDER BY DIST_ID,PRODUCT_ID, CREATED_BY";
			ps = db2conn.prepareStatement(sql);
			ps.setInt(1, recoID);
			
			if(groupId==6)
			{
				ps.setLong(2, accountID);
			}
			
			logger.info("Query to get Reco report  ::  "+sql);
			
			rs = ps.executeQuery();
			int sgPenPOOpen =0;
			int dgPenPOOpen =0;
			int sgPenDCOpen =0;
			int dgPenDCOpen =0;
			int sgSerOpen =0;
			int dgSerOpen =0;
			int sgNsrOpen =0;
			int dgNsrOpen =0;
			int sgDefOpen =0;
			int dgDefOpen =0;
			int sgUpgradeOpen =0;
			int dgUpgradeOpen =0;
			int sgChuurnOpen =0;
			int dgChuurnOpen =0;
			int sgRecWHStock = 0;
			int dgRecWHStock = 0;
			int sgRecInterSSDOK = 0;
			int dgRecInterSSDOK = 0;
			int sgRecInterSSDDef = 0;
			int dgRecInterSSDDef = 0;
			int sgRecUpgrade = 0;
			int dgRecUpgrade = 0;
			int sgRecChurn = 0;
			int dgRecChurn = 0;
			int sgRetFresh = 0;
			int dgRetFresh = 0;
			int sgRetInterSSDOK = 0;
			int dgRetInterSSDOK = 0;
			int sgRetInterSSDDef = 0;
			int dgRetInterSSDDef = 0;
			int sgRetDOABI = 0;
			int dgRetDOABI = 0;
			int sgRetDOAAI = 0;
			int dgRetDOAAI = 0;
			int sgRetSwap = 0;
			int dgRetSwap = 0;
			int sgRetC2S = 0;
			int dgRetC2S = 0;
			int sgRetChurn = 0;
			int dgRetChurn = 0;
			int sgActivation = 0;
			int dgActivation = 0;
			int sgInvChange = 0;
			int dgInvChange = 0;
			int sgAdjustment =0;
			int dgAdjustment =0;
			int sgSerClosing =0;
			int dgSerClosing =0;
			int sgNsrClosing =0;
			int dgNsrClosing =0;
			int sgDefClosing =0;
			int dgDefClosing =0;
			int sgUpgradeClosing =0;
			int dgUpgradeClosing =0;
			int sgChuurnClosing =0;
			int dgChuurnClosing =0;
			int sgPenPOClosing =0;
			int dgPenPOClosing =0;
			int sgPenDCClosing =0;
			int dgPenDCClosing =0;
			String status="";
			int i=0;
			
			while (rs.next()) {
				status="";
				i++;
				RecoDetailReportDTO recDTO = new RecoDetailReportDTO();
				if(rs.getString("RECO_STATUS").equals("INITIATE")){
					status = "Pending";
				}else if(rs.getString("RECO_STATUS").equals("SUCCESS")){
					status = "Submitted";
				}
				recDTO.setDistributorId(rs.getString("DISTRIBUTOR_OLM_ID"));
				recDTO.setDistributorName(rs.getString("DISTRIBUTOR_NAME"));
				recDTO.setType(rs.getString("TYPE"));
				recDTO.setProductType(rs.getString("PRODUCT_NAME"));
				
				recDTO.setPenPOOpen(rs.getInt("PENDING_PO_OPENING"));
				recDTO.setPenDcOpen(rs.getInt("PENDING_DC_OPENING"));
				recDTO.setSerOpening(rs.getInt("SER_OPEN_STOCK"));
				recDTO.setNsrOpening(rs.getInt("NSER_OPEN_STOCK"));
				recDTO.setDefOpen(rs.getInt("DEFECTIVE_OPEN_STOCK"));
				recDTO.setUpgardeOpen(rs.getInt("UPGRADE_OPEN_STOCK"));
				recDTO.setChurnOpen(rs.getInt("CHURN_OPEN_STOCK"));
				
				recDTO.setReceivedWH(rs.getInt("RECEIVED_WH"));
				recDTO.setReceivedInterSSDOk(rs.getInt("RecInterSSDOK"));
				recDTO.setReceivedInterSSDDef(rs.getInt("RECEIVED_INTER_SSD_DEF"));
				recDTO.setReceivedUpgrade(rs.getInt("RECEIVED_UPGRADE"));
				recDTO.setReceivedChurn(rs.getInt("RECEIVED_CHURN"));
				
				recDTO.setReturnedFresh(rs.getInt("RETURNED_FRESH"));
				recDTO.setReturnedInterSSDOk(rs.getInt("RetInterSSDOK"));
				recDTO.setReturnedInterSSDDef(rs.getInt("RETURNED_INTER_SSD_DEF"));
				recDTO.setReturnedDOABI(rs.getInt("RETURNED_DOA_BI"));
				recDTO.setReturnedDOAAI(rs.getInt("RETURNED_DOA_AI"));
				recDTO.setReturnedSwap(rs.getInt("RETURNED_SWAP"));
				recDTO.setReturnedC2C(rs.getInt("RETURNED_C2S"));
				recDTO.setReturnedChurn(rs.getInt("RETURNED_CHURN"));
				
				recDTO.setTotalActivation(rs.getInt("ACTIVATION"));
				recDTO.setSerClosing(rs.getInt("SER_CLOSING_STOCK"));
				recDTO.setNsrClosing(rs.getInt("NSER_CLOSING_STOCK"));
				recDTO.setUpgardeClosing(rs.getInt("UPGRADE_CLOSING_STOCK"));
				recDTO.setChurnClosing(rs.getInt("CHURN_CLOSING_STOCK"));
				recDTO.setDefClosing(rs.getInt("DEF_CLOSING_STOCK"));
				recDTO.setPenPOClosing(rs.getInt("PENDING_PO_CLOSING"));
				recDTO.setPenDcClosing(rs.getInt("PENDING_DC_CLOSING"));
				recDTO.setAdjustment(rs.getInt("ADJUSTMENT"));
				recDTO.setInventoryChange(rs.getInt("INVENTORY_CHANGE"));
				
				
				if(i%2==0){
					
					dgPenPOOpen = recDTO.getPenPOOpen();
					dgPenDCOpen = recDTO.getPenDcOpen();
					dgSerOpen = recDTO.getSerOpening();
					dgNsrOpen = recDTO.getNsrOpening();
					dgDefOpen = recDTO.getDefOpen();
					dgUpgradeOpen = recDTO.getUpgardeOpen();
					dgChuurnOpen = recDTO.getChurnOpen();
					dgRecWHStock = recDTO.getReceivedWH();
					dgRecInterSSDOK = recDTO.getReceivedInterSSDOk();
					dgRecInterSSDDef  = recDTO.getReceivedInterSSDDef();
					dgRecChurn = recDTO.getReceivedChurn();
					dgRecUpgrade= recDTO.getReceivedUpgrade();
					dgRetFresh = recDTO.getReturnedFresh();
					dgRetInterSSDOK= recDTO.getReturnedInterSSDOk();
					dgRetInterSSDDef = recDTO.getReturnedInterSSDDef();
					dgRetChurn = recDTO.getReturnedChurn();
					dgRetC2S= recDTO.getReturnedC2C();
					dgRetDOAAI = recDTO.getReturnedDOAAI();
					dgRetDOABI = recDTO.getReturnedDOABI();
					dgRetSwap= recDTO.getReturnedSwap();
					
					dgActivation = recDTO.getTotalActivation();
					dgAdjustment= recDTO.getAdjustment();
					dgInvChange = recDTO.getInventoryChange();
					dgSerClosing= recDTO.getSerClosing();
					dgNsrClosing = recDTO.getNsrClosing();
					dgDefClosing = recDTO.getDefClosing();
					dgChuurnClosing= recDTO.getChurnClosing();
					dgUpgradeClosing = recDTO.getUpgardeClosing();
					
					dgPenDCClosing = recDTO.getPenDcClosing();
					dgPenPOClosing= recDTO.getPenPOClosing();
					
					recDTO.setStatus(status);
					 
					recoList.add(recDTO);
					RecoDetailReportDTO recVarianceDTO = new RecoDetailReportDTO();
					recVarianceDTO.setDistributorId(rs.getString("DISTRIBUTOR_OLM_ID"));
					recVarianceDTO.setDistributorName(rs.getString("DISTRIBUTOR_NAME"));
					recVarianceDTO.setType("Variance");
					recVarianceDTO.setProductType(rs.getString("PRODUCT_NAME"));
					
				 
				

					/*
					 //****************************************************
					///Variance == System generated - Partner Generated
					//************************************************** 
					recVarianceDTO.setPenPOOpen(sgPenPOOpen-dgPenPOOpen);
					recVarianceDTO.setPenDcOpen(sgPenDCOpen-dgPenDCOpen);
					recVarianceDTO.setSerOpening(sgSerOpen-dgSerOpen);
					recVarianceDTO.setNsrOpening(sgNsrOpen-dgNsrOpen);
					recVarianceDTO.setDefOpen(sgDefOpen-dgDefOpen);
					recVarianceDTO.setUpgardeOpen(sgUpgradeOpen-dgUpgradeOpen);
					recVarianceDTO.setChurnOpen(sgChuurnOpen-dgChuurnOpen);
					
					recVarianceDTO.setReceivedWH(sgRecWHStock-dgRecWHStock);
					recVarianceDTO.setReceivedInterSSDOk(sgRecInterSSDOK-dgRecInterSSDOK);
					recVarianceDTO.setReceivedInterSSDDef(sgRecInterSSDDef-dgRecInterSSDDef);
					recVarianceDTO.setReceivedUpgrade(sgRecUpgrade-dgRecUpgrade);
					recVarianceDTO.setReceivedChurn(sgRecChurn-dgRecChurn);
					
					recVarianceDTO.setReturnedFresh(sgRetFresh-dgRetFresh);
					recVarianceDTO.setReturnedInterSSDOk(sgRetInterSSDOK-dgRetInterSSDOK);
					recVarianceDTO.setReturnedInterSSDDef(sgRetInterSSDDef-dgRetInterSSDDef);
					recVarianceDTO.setReturnedDOABI(sgRetDOABI-dgRetDOABI);
					recVarianceDTO.setReturnedDOAAI(sgRetDOAAI-dgRetDOAAI);
					recVarianceDTO.setReturnedSwap(sgRetSwap-dgRetSwap);
					recVarianceDTO.setReturnedC2C(sgRetC2S-dgRetC2S);
					recVarianceDTO.setReturnedChurn(sgRetChurn-dgRetChurn);
					
					recVarianceDTO.setTotalActivation(sgActivation-dgActivation);
					
					recVarianceDTO.setInventoryChange(sgInvChange-dgInvChange);
					recVarianceDTO.setSerClosing(sgSerClosing-dgSerClosing);
					recVarianceDTO.setNsrClosing(sgNsrClosing-dgNsrClosing);
					recVarianceDTO.setUpgardeClosing(sgUpgradeClosing-dgUpgradeClosing);
					recVarianceDTO.setChurnClosing(sgChuurnClosing-dgChuurnClosing);
					recVarianceDTO.setDefClosing(sgDefClosing-dgDefClosing);
					recVarianceDTO.setPenPOClosing(sgPenPOClosing-dgPenPOClosing);
					recVarianceDTO.setPenDcClosing(sgPenDCClosing-dgPenDCClosing);
					recVarianceDTO.setAdjustment(sgAdjustment-dgAdjustment);*/
					
					//****************************************************
					///Variance == Partner generated - System Generated
					//**************************************************
					
					recVarianceDTO.setPenPOOpen(dgPenPOOpen-sgPenPOOpen);
					recVarianceDTO.setPenDcOpen(dgPenDCOpen-sgPenDCOpen);
					recVarianceDTO.setSerOpening(dgSerOpen-sgSerOpen);
					recVarianceDTO.setNsrOpening(dgNsrOpen-sgNsrOpen);
					recVarianceDTO.setDefOpen(dgDefOpen-sgDefOpen);
					recVarianceDTO.setUpgardeOpen(dgUpgradeOpen-sgUpgradeOpen);
					recVarianceDTO.setChurnOpen(dgChuurnOpen-sgChuurnOpen);
					
					recVarianceDTO.setReceivedWH(dgRecWHStock-sgRecWHStock);
					recVarianceDTO.setReceivedInterSSDOk(dgRecInterSSDOK-sgRecInterSSDOK);
					recVarianceDTO.setReceivedInterSSDDef(dgRecInterSSDDef-sgRecInterSSDDef);
					recVarianceDTO.setReceivedUpgrade(dgRecUpgrade-sgRecUpgrade);
					recVarianceDTO.setReceivedChurn(dgRecChurn-sgRecChurn);
					
					recVarianceDTO.setReturnedFresh(dgRetFresh-sgRetFresh);
					recVarianceDTO.setReturnedInterSSDOk(dgRetInterSSDOK-sgRetInterSSDOK);
					recVarianceDTO.setReturnedInterSSDDef(dgRetInterSSDDef-sgRetInterSSDDef);
					recVarianceDTO.setReturnedDOABI(dgRetDOABI-sgRetDOABI);
					recVarianceDTO.setReturnedDOAAI(dgRetDOAAI-sgRetDOAAI);
					recVarianceDTO.setReturnedSwap(dgRetSwap-sgRetSwap);
					recVarianceDTO.setReturnedC2C(dgRetC2S-sgRetC2S);
					recVarianceDTO.setReturnedChurn(dgRetChurn-sgRetChurn);
					
					recVarianceDTO.setTotalActivation(dgActivation-sgActivation);
					
					recVarianceDTO.setInventoryChange(dgInvChange-sgInvChange);
					recVarianceDTO.setSerClosing(dgSerClosing-sgSerClosing);
					recVarianceDTO.setNsrClosing(dgNsrClosing-sgNsrClosing);
					recVarianceDTO.setUpgardeClosing(dgUpgradeClosing-sgUpgradeClosing);
					recVarianceDTO.setChurnClosing(dgChuurnClosing-sgChuurnClosing);
					recVarianceDTO.setDefClosing(dgDefClosing-sgDefClosing);
					recVarianceDTO.setPenPOClosing(dgPenPOClosing-sgPenPOClosing);
					recVarianceDTO.setPenDcClosing(dgPenDCClosing-sgPenDCClosing);
					recVarianceDTO.setAdjustment(dgAdjustment-sgAdjustment);
				 
				 recoList.add(recVarianceDTO);
				}
				else{
					sgPenPOOpen = recDTO.getPenPOOpen();
					sgPenDCOpen = recDTO.getPenDcOpen();
					sgSerOpen = recDTO.getSerOpening();
					sgNsrOpen = recDTO.getNsrOpening();
					sgDefOpen = recDTO.getDefOpen();
					sgUpgradeOpen = recDTO.getUpgardeOpen();
					sgChuurnOpen = recDTO.getChurnOpen();
					sgRecWHStock = recDTO.getReceivedWH();
					sgRecInterSSDOK = recDTO.getReceivedInterSSDOk();
					sgRecInterSSDDef  = recDTO.getReceivedInterSSDDef();
					sgRecChurn = recDTO.getReceivedChurn();
					sgRecUpgrade= recDTO.getReceivedUpgrade();
					sgRetFresh = recDTO.getReturnedFresh();
					sgRetInterSSDOK= recDTO.getReturnedInterSSDOk();
					sgRetInterSSDDef = recDTO.getReturnedInterSSDDef();
					sgRetChurn = recDTO.getReturnedChurn();
					sgRetC2S= recDTO.getReturnedC2C();
					sgRetDOAAI = recDTO.getReturnedDOAAI();
					sgRetDOABI = recDTO.getReturnedDOABI();
					sgRetSwap= recDTO.getReturnedSwap();
					
					sgActivation = recDTO.getTotalActivation();
					sgAdjustment= recDTO.getAdjustment();
					sgInvChange = recDTO.getInventoryChange();
					sgSerClosing= recDTO.getSerClosing();
					sgNsrClosing = recDTO.getNsrClosing();
					sgDefClosing = recDTO.getDefClosing();
					sgChuurnClosing= recDTO.getChurnClosing();
					sgUpgradeClosing = recDTO.getUpgardeClosing();
				
					sgPenDCClosing = recDTO.getPenDcClosing();
					sgPenPOClosing= recDTO.getPenPOClosing();
					
					
					recoList.add(recDTO);
				}			
			}						
			
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("Exception occured while getting Reco Details. Exception message: "
					+ e.getMessage());
		} finally {
			DBConnectionManager.releaseResources(ps ,rs );
		DBConnectionManager.releaseResources(db2conn);
		}
		return recoList;
		
	}
	
	public List<RecoPeriodDTO> getRecoHistory() throws DAOException{
		PreparedStatement ps = null;
		Connection db2conn = null; 
		ResultSet rs = null; 
		List<RecoPeriodDTO> recoList= new ArrayList<RecoPeriodDTO>();
		
		try {
			db2conn = DBConnectionManager.getDBConnection();
						
			String sql = "SELECT  * FROM DP_RECO_PERIOD where TO_DATE<current date  ORDER BY ID DESC with ur";
	
			ps = db2conn.prepareStatement(sql);
			logger.info(sql);
			
			rs = ps.executeQuery();
			
			
			while (rs.next()) {
				RecoPeriodDTO recDTO = new RecoPeriodDTO();
				String fromDate = Utility.converDateToString(rs.getDate("FROM_DATE"), Constants.DT_FMT_RECO);
				String toDate = Utility.converDateToString(rs.getDate("TO_DATE"), Constants.DT_FMT_RECO);
				Integer recoID = rs.getInt("ID");
				recDTO.setFromDate(fromDate);
				recDTO.setToDate(toDate);
				recDTO.setRecoPeriodId(recoID.toString());
				recoList.add(recDTO);
			}
			
		} catch (Exception e) {
				e.printStackTrace();
				throw new DAOException("Exception: " + e.getMessage());
				}
		finally {
			DBConnectionManager.releaseResources(ps ,rs);			
			DBConnectionManager.releaseResources(db2conn);
			}
		return recoList;
		
	}
	

	public List<DistRecoDto> getExportToExcel(String circleList,String tsmList, String distList, String prodList,String recoPeriod, String distType) throws DAOException{
		PreparedStatement ps = null;
		PreparedStatement ps1 = null;
		Connection db2conn = null; 
		ResultSet rs = null; 
		ResultSet rs1 = null; 
		List<DistRecoDto> recoList= new ArrayList<DistRecoDto>();
		System.out.println("Circle List in DAOimpl : "+circleList);
		System.out.println("TSM List in Action : "+tsmList);
		System.out.println("Distributor List in Action : "+distList);
		System.out.println("Product List in Action : "+prodList);
		System.out.println("Reco Period in Action : "+recoPeriod);
		System.out.println("Distributor Type in Action : "+distType);
	
		java.sql.Date toDate = null;
		String sql = null;
		String toDate2 = null;
		if(!recoPeriod.equals("-1"))
		{
		//toDate = Utility.getSqlDateFromString(recoPeriod, "yyyy-MM-dd");
		//System.out.println("toDate1 : "+toDate);
		//System.out.println("new java.sql.Date(toDate.getTime()) : "+new java.sql.Date(toDate.getTime()));
		
	//	toDate = Utility.getSqlDateFromString(recoPeriod, Constants.DT_FMT_SQL);
	//	System.out.println("toDate2 : "+toDate);
	//	System.out.println("new java.sql.Date(toDate.getTime()) : "+new java.sql.Date(toDate.getTime()));
			
		
		}
		try {
			db2conn = DBConnectionManager.getDBConnection();
			
			String sqlToDate = "select TO_DATE from DP_RECO_PERIOD where id = ?";
			ps1 = db2conn.prepareStatement(sqlToDate);
			ps1.setString(1, recoPeriod);
			rs1 = ps1.executeQuery();
			
			if(rs1.next()){
				toDate2 = rs1.getString("TO_DATE");
			}
			
			
			toDate = Utility.getSqlDateFromString(toDate2, Constants.DATE_FORMAT);
			System.out.println("toDate2 : "+toDate);
			//System.out.println("new java.sql.Date(toDate.getTime()) : "+new java.sql.Date(toDate.getTime()));
			
			if(distType.equals("ok"))
			{
			//String sql = "SELECT  	LM.login_name  AS Distributor_OLM,  	AD.CONTACT_NAME AS Account_Name,      (select CIRCLE_NAME from VR_CIRCLE_MASTER where CIRCLE_ID = AD.CIRCLE_ID) as Circle_Name,       (select PRODUCT_NAME from DP_PRODUCT_MASTER where PRODUCT_ID = RUS.DEFECTIVE_PRODUCT_ID) as PRODUCT_NAME,       'Upgrade' as Type_of_Stock,       RUS.DIST_FLAG as Distributor_Flag,       RUS.DIST_REMARKS as Distributor_Remarks,       RUS.FLUSHOUT_FLAG as Flushout_Flag,       RUS.DTH_REMARKS as DTH_Remarks  FROM  	RECO_UPGRADE_STOCK RUS,  	VR_LOGIN_MASTER LM,  	VR_ACCOUNT_DETAILS AD   WHERE  	LM.LOGIN_ID = RUS.FROM_DIST_ID AND  	LM.LOGIN_ID = AD.ACCOUNT_ID AND  	DATE(RUS.RUNNING_SCHEDULER_DATE) = ?  AND  	RUS.FROM_DIST_ID IN (	SELECT  								account_id   							FROM  								VR_ACCOUNT_DETAILS   							WHERE  								ACCOUNT_ID IN(169218)  	)    AND RUS.DIST_FLAG = 'Y'     AND RUS.FROM_DIST_ID not in (select distinct FROM_DIST_ID from RECO_UPGRADE_STOCK where DIST_FLAG != 'Y') with ur";
				if(!recoPeriod.equals("-1"))
				sql = "WITH TEMP  (DIST_ID,Distributor_OLM,Account_Name,Circle_Name,TSM_Name, PRODUCT_NAME,Type_of_Stock,Serial_Number,Distributor_Flag,Distributor_Remarks,Flushout_Flag,Debit_Flag,DTH_Remarks,Other_Remarks,MAIN_FLAG)   AS (    SELECT AD.ACCOUNT_ID DIST_ID,LM.login_name     AS Distributor_OLM, AD.ACCOUNT_NAME   AS Account_Name,  	(	SELECT CIRCLE_NAME  FROM VR_CIRCLE_MASTER WHERE CIRCLE_ID = AD.CIRCLE_ID )  	AS Circle_Name,      (select TSM_NAME from DP_DIST_DETAILS where DISTRIBUTOR_ID = AD.ACCOUNT_ID) as TSM_Name,  	(	SELECT 	PRODUCT_NAME  FROM DP_PRODUCT_MASTER WHERE PRODUCT_ID = RUS.DEFECTIVE_PRODUCT_ID ) AS PRODUCT_NAME,  	'Upgrade'         AS Type_of_Stock,      RUS.Defective_SR_No as Serial_Number,   	RUS.DIST_FLAG     AS Distributor_Flag,  	RUS.DIST_REMARKS  AS Distributor_Remarks,  	RUS.FLUSHOUT_FLAG AS Flushout_Flag,    RUS.DEBIT_Flag as Debit_Flag,	RUS.DTH_REMARKS   AS DTH_Remarks, Other_Remarks,       CASE WHEN RUS.DIST_FLAG='Y' THEN 1            WHEN RUS.DIST_FLAG='N' AND trim(DEBIT_FLAG) IN( 'Y', 'N') THEN 1           ELSE 0 END MAIN_FLAG  FROM  	RECO_UPGRADE_STOCK RUS INNER JOIN VR_LOGIN_MASTER LM ON LM.LOGIN_ID = RUS.FROM_DIST_ID INNER JOIN   	VR_ACCOUNT_DETAILS AD ON LM.LOGIN_ID = AD.ACCOUNT_ID  WHERE  	DATE(RUS.RUNNING_SCHEDULER_DATE) = ?  	AND AD.ACCOUNT_ID IN ( "+distList+ ")     AND RUS.Defective_Product_id in(SELECT PRODUCT_ID from DP_PRODUCT_MASTER where CATEGORY_CODE=1 and PRODUCT_CATEGORY in (select value from dp_configuration_details where config_id=8 and id in ("+ prodList + ") ) )  UNION ALL    SELECT AD.ACCOUNT_ID DIST_ID,LM.login_name  AS Distributor_OLM, AD.ACCOUNT_NAME AS Account_Name,         (select CIRCLE_NAME from VR_CIRCLE_MASTER where CIRCLE_ID = AD.CIRCLE_ID) as Circle_Name,         (select TSM_NAME from DP_DIST_DETAILS where DISTRIBUTOR_ID = AD.ACCOUNT_ID) as TSM_Name,         (select PRODUCT_NAME from DP_PRODUCT_MASTER where PRODUCT_ID = RUS.PRODUCT_ID) as PRODUCT_NAME,         'Serialized stock' as Type_of_Stock,          RUS.Serial_No as Serial_Number,         RUS.DIST_FLAG as Distributor_Flag,         RUS.DIST_REMARKS as Distributor_Remarks,         RUS.FLUSHOUT_FLAG as Flushout_Flag,     RUS.DEBIT_Flag as Debit_Flag,     RUS.DTH_REMARKS as DTH_Remarks, Other_Remarks,         CASE WHEN RUS.DIST_FLAG='Y' THEN 1            WHEN RUS.DIST_FLAG='N' AND  trim(RUS.DEBIT_FLAG) IN( 'Y', 'N') THEN 1           ELSE 0 END MAIN_FLAG  FROM  	RECO_SERIALIZED_STOCK RUS INNER JOIN VR_LOGIN_MASTER LM ON LM.LOGIN_ID = RUS.DISTRIBUTOR_ID  	INNER JOIN VR_ACCOUNT_DETAILS AD ON LM.LOGIN_ID = AD.ACCOUNT_ID  WHERE  	DATE(RUS.RUNNING_SCHEDULER_DATE) = ? 	AND AD.ACCOUNT_ID IN ("+distList+ ")      AND RUS.PRODUCT_ID in(SELECT PRODUCT_ID from DP_PRODUCT_MASTER where CATEGORY_CODE=1 and PRODUCT_CATEGORY in (select value from dp_configuration_details where config_id=8 and id in ("+ prodList + ") ) )   UNION ALL    SELECT AD.ACCOUNT_ID DIST_ID,LM.login_name  AS Distributor_OLM, AD.ACCOUNT_NAME AS Account_Name,         (select CIRCLE_NAME from VR_CIRCLE_MASTER where CIRCLE_ID = AD.CIRCLE_ID) as Circle_Name,         (select TSM_NAME from DP_DIST_DETAILS where DISTRIBUTOR_ID = AD.ACCOUNT_ID) as TSM_Name,         (select PRODUCT_NAME from DP_PRODUCT_MASTER where PRODUCT_ID = RUS.defective_product_id) as PRODUCT_NAME,         'Defective Stock' as Type_of_Stock,         RUS.Defective_SR_No as Serial_Number,         RUS.DIST_FLAG as Distributor_Flag,         RUS.DIST_REMARKS as Distributor_Remarks,         RUS.FLUSHOUT_FLAG as Flushout_Flag,   RUS.DEBIT_Flag as Debit_Flag,      RUS.DTH_REMARKS as DTH_Remarks, Other_Remarks,         CASE WHEN RUS.DIST_FLAG='Y' THEN 1            WHEN RUS.DIST_FLAG='N' AND trim(RUS.DEBIT_FLAG) IN( 'Y', 'N') THEN 1      ELSE 0 END MAIN_FLAG  FROM  	RECO_DEFECTIVE_STOCK RUS INNER JOIN VR_LOGIN_MASTER LM ON LM.LOGIN_ID = RUS.FROM_DIST_ID  	INNER JOIN VR_ACCOUNT_DETAILS AD ON LM.LOGIN_ID = AD.ACCOUNT_ID  WHERE  	DATE(RUS.RUNNING_SCHEDULER_DATE) = ?  	AND AD.ACCOUNT_ID IN ("+distList+ ")    AND RUS.Defective_Product_id in(SELECT PRODUCT_ID from DP_PRODUCT_MASTER where CATEGORY_CODE=1 and PRODUCT_CATEGORY in (select value from dp_configuration_details where config_id=8 and id in ("+ prodList + ")) )    UNION ALL    SELECT AD.ACCOUNT_ID DIST_ID,LM.login_name  AS Distributor_OLM, AD.ACCOUNT_NAME AS Account_Name,         (select CIRCLE_NAME from VR_CIRCLE_MASTER where CIRCLE_ID = AD.CIRCLE_ID) as Circle_Name,         (select TSM_NAME from DP_DIST_DETAILS where DISTRIBUTOR_ID = AD.ACCOUNT_ID) as TSM_Name,         (select PRODUCT_NAME from DP_PRODUCT_MASTER where PRODUCT_ID = RUS.defective_product_id) as PRODUCT_NAME,         'Churn' as Type_of_Stock,         RUS.Defective_SR_No as Serial_Number,         RUS.DIST_FLAG as Distributor_Flag,         RUS.DIST_REMARKS as Distributor_Remarks,         RUS.FLUSHOUT_FLAG as Flushout_Flag,     RUS.DEBIT_Flag as Debit_Flag,     RUS.DTH_REMARKS as DTH_Remarks, Other_Remarks,         CASE WHEN RUS.DIST_FLAG='Y' THEN 1            WHEN RUS.DIST_FLAG='N' AND trim(RUS.DEBIT_FLAG) IN( 'Y', 'N') THEN 1           ELSE 0 END MAIN_FLAG  FROM  	RECO_CHURN_STOCK RUS INNER JOIN VR_LOGIN_MASTER LM ON LM.LOGIN_ID = RUS.FROM_DIST_ID  	INNER JOIN VR_ACCOUNT_DETAILS AD ON LM.LOGIN_ID = AD.ACCOUNT_ID  WHERE  	DATE(RUS.RUNNING_SCHEDULER_DATE) = ?  	AND AD.ACCOUNT_ID IN ("+distList+ ")    AND RUS.Defective_Product_id in(SELECT PRODUCT_ID from DP_PRODUCT_MASTER where CATEGORY_CODE=1 and PRODUCT_CATEGORY in (select value from dp_configuration_details where config_id=8 and id in ("+ prodList + ") ) )  )     SELECT * FROM TEMP WHERE MAIN_FLAG=1   AND DIST_ID NOT IN (SELECT DIST_ID FROM TEMP WHERE MAIN_FLAG=0)   order by DISTRIBUTOR_OLM,TYPE_OF_STOCK,PRODUCT_NAME WITH UR  ";
				else
				sql = "WITH TEMP  (DIST_ID,Distributor_OLM,Account_Name,Circle_Name,TSM_Name, PRODUCT_NAME,Type_of_Stock,Serial_Number,Distributor_Flag,Distributor_Remarks,Flushout_Flag,Debit_Flag,DTH_Remarks,Other_Remarks,MAIN_FLAG)   AS (    SELECT AD.ACCOUNT_ID DIST_ID,LM.login_name     AS Distributor_OLM, AD.ACCOUNT_NAME   AS Account_Name,  	(	SELECT CIRCLE_NAME  FROM VR_CIRCLE_MASTER WHERE CIRCLE_ID = AD.CIRCLE_ID )  	AS Circle_Name,      (select TSM_NAME from DP_DIST_DETAILS where DISTRIBUTOR_ID = AD.ACCOUNT_ID) as TSM_Name,  	(	SELECT 	PRODUCT_NAME  FROM DP_PRODUCT_MASTER WHERE PRODUCT_ID = RUS.DEFECTIVE_PRODUCT_ID ) AS PRODUCT_NAME,  	'Upgrade'         AS Type_of_Stock,      RUS.Defective_SR_No as Serial_Number,   	RUS.DIST_FLAG     AS Distributor_Flag,  	RUS.DIST_REMARKS  AS Distributor_Remarks,  	RUS.FLUSHOUT_FLAG AS Flushout_Flag,    RUS.DEBIT_Flag as Debit_Flag,	RUS.DTH_REMARKS   AS DTH_Remarks, Other_Remarks,      CASE WHEN RUS.DIST_FLAG='Y' THEN 1            WHEN RUS.DIST_FLAG='N' AND trim(DEBIT_FLAG) IN( 'Y', 'N') THEN 1           ELSE 0 END MAIN_FLAG  FROM  	RECO_UPGRADE_STOCK RUS INNER JOIN VR_LOGIN_MASTER LM ON LM.LOGIN_ID = RUS.FROM_DIST_ID INNER JOIN   	VR_ACCOUNT_DETAILS AD ON LM.LOGIN_ID = AD.ACCOUNT_ID  WHERE  	 AD.ACCOUNT_ID IN ( "+distList+ ")     AND RUS.Defective_Product_id in(SELECT PRODUCT_ID from DP_PRODUCT_MASTER where CATEGORY_CODE=1 and PRODUCT_CATEGORY in (select value from dp_configuration_details where config_id=8 and id in ("+ prodList + ") ) )  UNION ALL    SELECT AD.ACCOUNT_ID DIST_ID,LM.login_name  AS Distributor_OLM, AD.ACCOUNT_NAME AS Account_Name,         (select CIRCLE_NAME from VR_CIRCLE_MASTER where CIRCLE_ID = AD.CIRCLE_ID) as Circle_Name,         (select TSM_NAME from DP_DIST_DETAILS where DISTRIBUTOR_ID = AD.ACCOUNT_ID) as TSM_Name,         (select PRODUCT_NAME from DP_PRODUCT_MASTER where PRODUCT_ID = RUS.PRODUCT_ID) as PRODUCT_NAME,         'Serialized stock' as Type_of_Stock,          RUS.Serial_No as Serial_Number,         RUS.DIST_FLAG as Distributor_Flag,         RUS.DIST_REMARKS as Distributor_Remarks,         RUS.FLUSHOUT_FLAG as Flushout_Flag,     RUS.DEBIT_Flag as Debit_Flag,     RUS.DTH_REMARKS as DTH_Remarks, Other_Remarks,         CASE WHEN RUS.DIST_FLAG='Y' THEN 1            WHEN RUS.DIST_FLAG='N' AND trim(RUS.DEBIT_FLAG) IN( 'Y', 'N') THEN 1           ELSE 0 END MAIN_FLAG  FROM  	RECO_SERIALIZED_STOCK RUS INNER JOIN VR_LOGIN_MASTER LM ON LM.LOGIN_ID = RUS.DISTRIBUTOR_ID  	INNER JOIN VR_ACCOUNT_DETAILS AD ON LM.LOGIN_ID = AD.ACCOUNT_ID  WHERE  	 AD.ACCOUNT_ID IN ("+distList+ ")      AND RUS.PRODUCT_ID in(SELECT PRODUCT_ID from DP_PRODUCT_MASTER where CATEGORY_CODE=1 and PRODUCT_CATEGORY in (select value from dp_configuration_details where config_id=8 and id in ("+ prodList + ") ) )    UNION ALL    SELECT AD.ACCOUNT_ID DIST_ID,LM.login_name  AS Distributor_OLM, AD.ACCOUNT_NAME AS Account_Name,         (select CIRCLE_NAME from VR_CIRCLE_MASTER where CIRCLE_ID = AD.CIRCLE_ID) as Circle_Name,         (select TSM_NAME from DP_DIST_DETAILS where DISTRIBUTOR_ID = AD.ACCOUNT_ID) as TSM_Name,         (select PRODUCT_NAME from DP_PRODUCT_MASTER where PRODUCT_ID = RUS.defective_product_id) as PRODUCT_NAME,         'Defective Stock' as Type_of_Stock,         RUS.Defective_SR_No as Serial_Number,         RUS.DIST_FLAG as Distributor_Flag,         RUS.DIST_REMARKS as Distributor_Remarks,         RUS.FLUSHOUT_FLAG as Flushout_Flag,    RUS.DEBIT_Flag as Debit_Flag,     RUS.DTH_REMARKS as DTH_Remarks, Other_Remarks,         CASE WHEN RUS.DIST_FLAG='Y' THEN 1            WHEN RUS.DIST_FLAG='N' AND trim(RUS.DEBIT_FLAG) IN( 'Y', 'N') THEN 1           ELSE 0 END MAIN_FLAG  FROM  	RECO_DEFECTIVE_STOCK RUS INNER JOIN VR_LOGIN_MASTER LM ON LM.LOGIN_ID = RUS.FROM_DIST_ID  	INNER JOIN VR_ACCOUNT_DETAILS AD ON LM.LOGIN_ID = AD.ACCOUNT_ID  WHERE  	AD.ACCOUNT_ID IN ("+distList+ ")    AND RUS.Defective_Product_id in(SELECT PRODUCT_ID from DP_PRODUCT_MASTER where CATEGORY_CODE=1 and PRODUCT_CATEGORY in (select value from dp_configuration_details where config_id=8 and id in ("+ prodList + ") ) )    UNION ALL    SELECT AD.ACCOUNT_ID DIST_ID,LM.login_name  AS Distributor_OLM, AD.ACCOUNT_NAME AS Account_Name,         (select CIRCLE_NAME from VR_CIRCLE_MASTER where CIRCLE_ID = AD.CIRCLE_ID) as Circle_Name,         (select TSM_NAME from DP_DIST_DETAILS where DISTRIBUTOR_ID = AD.ACCOUNT_ID) as TSM_Name,         (select PRODUCT_NAME from DP_PRODUCT_MASTER where PRODUCT_ID = RUS.defective_product_id) as PRODUCT_NAME,         'Churn' as Type_of_Stock,         RUS.Defective_SR_No as Serial_Number,         RUS.DIST_FLAG as Distributor_Flag,         RUS.DIST_REMARKS as Distributor_Remarks,         RUS.FLUSHOUT_FLAG as Flushout_Flag,      RUS.DEBIT_Flag as Debit_Flag,    RUS.DTH_REMARKS as DTH_Remarks, Other_Remarks,        CASE WHEN RUS.DIST_FLAG='Y' THEN 1            WHEN RUS.DIST_FLAG='N' AND trim(RUS.DEBIT_FLAG) IN( 'Y', 'N') THEN 1           ELSE 0 END MAIN_FLAG  FROM  	RECO_CHURN_STOCK RUS INNER JOIN VR_LOGIN_MASTER LM ON LM.LOGIN_ID = RUS.FROM_DIST_ID  	INNER JOIN VR_ACCOUNT_DETAILS AD ON LM.LOGIN_ID = AD.ACCOUNT_ID  WHERE AD.ACCOUNT_ID IN ("+distList+ ")    AND RUS.Defective_Product_id in(SELECT PRODUCT_ID from DP_PRODUCT_MASTER where CATEGORY_CODE=1 and PRODUCT_CATEGORY in (select value from dp_configuration_details where config_id=8 and id in ("+ prodList + ") ) )   )     SELECT * FROM TEMP WHERE MAIN_FLAG=1   AND DIST_ID NOT IN (SELECT DIST_ID FROM TEMP WHERE MAIN_FLAG=0)    order by DISTRIBUTOR_OLM,TYPE_OF_STOCK,PRODUCT_NAME WITH UR  ";	
			}
			else{
				if(!recoPeriod.equals("-1"))
				sql = " WITH TEMP  (DIST_ID,Distributor_OLM,Account_Name,Circle_Name,TSM_Name, PRODUCT_NAME,Type_of_Stock,Serial_Number,Distributor_Flag,Distributor_Remarks,Flushout_Flag,Debit_Flag,DTH_Remarks,Other_Remarks,MAIN_FLAG)   AS (    SELECT AD.ACCOUNT_ID DIST_ID,LM.login_name     AS Distributor_OLM, AD.ACCOUNT_NAME   AS Account_Name,  (	SELECT CIRCLE_NAME  FROM VR_CIRCLE_MASTER WHERE CIRCLE_ID = AD.CIRCLE_ID )  	AS Circle_Name,      (select TSM_NAME from DP_DIST_DETAILS where DISTRIBUTOR_ID = AD.ACCOUNT_ID) as TSM_Name,  	(	SELECT 	PRODUCT_NAME  FROM DP_PRODUCT_MASTER WHERE PRODUCT_ID = RUS.DEFECTIVE_PRODUCT_ID ) AS PRODUCT_NAME,  	'Upgrade'         AS Type_of_Stock,      RUS.Defective_SR_No as Serial_Number,   	RUS.DIST_FLAG     AS Distributor_Flag,  	RUS.DIST_REMARKS  AS Distributor_Remarks,  	RUS.FLUSHOUT_FLAG AS Flushout_Flag,    RUS.DEBIT_Flag as Debit_Flag,	RUS.DTH_REMARKS   AS DTH_Remarks, Other_Remarks,       CASE WHEN RUS.DIST_FLAG='N' AND trim(DEBIT_FLAG) not in ( 'Y', 'N') THEN 1   WHEN (RUS.DIST_FLAG IS NULL or RUS.DIST_FLAG='') THEN 1    WHEN trim(RUS.DIST_FLAG) ='N' AND DEBIT_FLAG is NULL THEN 1      ELSE 0 END MAIN_FLAG  FROM  	RECO_UPGRADE_STOCK RUS INNER JOIN VR_LOGIN_MASTER LM ON LM.LOGIN_ID = RUS.FROM_DIST_ID INNER JOIN   	VR_ACCOUNT_DETAILS AD ON LM.LOGIN_ID = AD.ACCOUNT_ID  WHERE  	DATE(RUS.RUNNING_SCHEDULER_DATE) = ?  	AND AD.ACCOUNT_ID IN ("+distList+ ")      AND RUS.Defective_Product_id in(SELECT PRODUCT_ID from DP_PRODUCT_MASTER where CATEGORY_CODE=1 and PRODUCT_CATEGORY in (select value from dp_configuration_details where config_id=8 and id in ("+ prodList + ") ))  UNION ALL    SELECT AD.ACCOUNT_ID DIST_ID,LM.login_name  AS Distributor_OLM, AD.ACCOUNT_NAME AS Account_Name,         (select CIRCLE_NAME from VR_CIRCLE_MASTER where CIRCLE_ID = AD.CIRCLE_ID) as Circle_Name,         (select TSM_NAME from DP_DIST_DETAILS where DISTRIBUTOR_ID = AD.ACCOUNT_ID) as TSM_Name,         (select PRODUCT_NAME from DP_PRODUCT_MASTER where PRODUCT_ID = RUS.PRODUCT_ID) as PRODUCT_NAME,         'Serialized stock' as Type_of_Stock,          RUS.Serial_No as Serial_Number,         RUS.DIST_FLAG as Distributor_Flag,         RUS.DIST_REMARKS as Distributor_Remarks,         RUS.FLUSHOUT_FLAG as Flushout_Flag,     RUS.DEBIT_Flag as Debit_Flag,      RUS.DTH_REMARKS as DTH_Remarks, Other_Remarks,        CASE WHEN RUS.DIST_FLAG='N' AND trim(DEBIT_FLAG)  not in ( 'Y', 'N') THEN 1  WHEN (RUS.DIST_FLAG IS NULL or RUS.DIST_FLAG='') THEN 1    WHEN trim(RUS.DIST_FLAG) ='N' AND DEBIT_FLAG is NULL THEN 1        ELSE 0 END MAIN_FLAG  FROM  	RECO_SERIALIZED_STOCK RUS INNER JOIN VR_LOGIN_MASTER LM ON LM.LOGIN_ID = RUS.DISTRIBUTOR_ID  	INNER JOIN VR_ACCOUNT_DETAILS AD ON LM.LOGIN_ID = AD.ACCOUNT_ID  WHERE  	DATE(RUS.RUNNING_SCHEDULER_DATE) = ?  	AND AD.ACCOUNT_ID IN ("+distList+ ")      AND RUS.PRODUCT_ID in(SELECT PRODUCT_ID from DP_PRODUCT_MASTER where CATEGORY_CODE=1 and PRODUCT_CATEGORY in (select value from dp_configuration_details where config_id=8 and id in ("+ prodList + ") ) )    UNION ALL    SELECT AD.ACCOUNT_ID DIST_ID,LM.login_name  AS Distributor_OLM, AD.ACCOUNT_NAME AS Account_Name,         (select CIRCLE_NAME from VR_CIRCLE_MASTER where CIRCLE_ID = AD.CIRCLE_ID) as Circle_Name,         (select TSM_NAME from DP_DIST_DETAILS where DISTRIBUTOR_ID = AD.ACCOUNT_ID) as TSM_Name,         (select PRODUCT_NAME from DP_PRODUCT_MASTER where PRODUCT_ID = RUS.defective_product_id) as PRODUCT_NAME,         'Defective Stock' as Type_of_Stock,         RUS.Defective_SR_No as Serial_Number,         RUS.DIST_FLAG as Distributor_Flag,         RUS.DIST_REMARKS as Distributor_Remarks,         RUS.FLUSHOUT_FLAG as Flushout_Flag,    RUS.DEBIT_Flag as Debit_Flag,      RUS.DTH_REMARKS as DTH_Remarks, Other_Remarks,       CASE WHEN RUS.DIST_FLAG='N' AND trim(DEBIT_FLAG) not in ( 'Y', 'N') THEN 1    WHEN (RUS.DIST_FLAG IS NULL or RUS.DIST_FLAG='') THEN 1  WHEN trim(RUS.DIST_FLAG) ='N' AND DEBIT_FLAG is NULL THEN 1      ELSE 0 END MAIN_FLAG  FROM  	RECO_DEFECTIVE_STOCK RUS INNER JOIN VR_LOGIN_MASTER LM ON LM.LOGIN_ID = RUS.FROM_DIST_ID  	INNER JOIN VR_ACCOUNT_DETAILS AD ON LM.LOGIN_ID = AD.ACCOUNT_ID  WHERE  	DATE(RUS.RUNNING_SCHEDULER_DATE) = ?  	AND AD.ACCOUNT_ID IN ("+distList+ ")    AND RUS.Defective_Product_id in(SELECT PRODUCT_ID from DP_PRODUCT_MASTER where CATEGORY_CODE=1 and PRODUCT_CATEGORY in (select value from dp_configuration_details where config_id=8 and id in ("+ prodList + ") ) )    UNION ALL    SELECT AD.ACCOUNT_ID DIST_ID,LM.login_name  AS Distributor_OLM, AD.ACCOUNT_NAME AS Account_Name,         (select CIRCLE_NAME from VR_CIRCLE_MASTER where CIRCLE_ID = AD.CIRCLE_ID) as Circle_Name,         (select TSM_NAME from DP_DIST_DETAILS where DISTRIBUTOR_ID = AD.ACCOUNT_ID) as TSM_Name,         (select PRODUCT_NAME from DP_PRODUCT_MASTER where PRODUCT_ID = RUS.defective_product_id) as PRODUCT_NAME,         'Churn' as Type_of_Stock,         RUS.Defective_SR_No as Serial_Number,         RUS.DIST_FLAG as Distributor_Flag,         RUS.DIST_REMARKS as Distributor_Remarks,         RUS.FLUSHOUT_FLAG as Flushout_Flag,    RUS.DEBIT_Flag as Debit_Flag,     RUS.DTH_REMARKS as DTH_Remarks, Other_Remarks,        CASE WHEN RUS.DIST_FLAG='N' AND trim(DEBIT_FLAG) not in ( 'Y', 'N') THEN 1     WHEN (RUS.DIST_FLAG IS NULL or RUS.DIST_FLAG='') THEN 1   WHEN trim(RUS.DIST_FLAG) ='N' AND DEBIT_FLAG is NULL THEN 1     ELSE 0 END MAIN_FLAG  FROM  	RECO_CHURN_STOCK RUS INNER JOIN VR_LOGIN_MASTER LM ON LM.LOGIN_ID = RUS.FROM_DIST_ID  	INNER JOIN VR_ACCOUNT_DETAILS AD ON LM.LOGIN_ID = AD.ACCOUNT_ID  WHERE  	DATE(RUS.RUNNING_SCHEDULER_DATE) = ? 	AND AD.ACCOUNT_ID IN ("+distList+ ")    AND RUS.Defective_Product_id in(SELECT PRODUCT_ID from DP_PRODUCT_MASTER where CATEGORY_CODE=1 and PRODUCT_CATEGORY in (select value from dp_configuration_details where config_id=8 and id in ("+ prodList + ") ) )   )     SELECT * FROM TEMP where DIST_ID IN (SELECT DIST_ID FROM TEMP WHERE MAIN_FLAG=1)  order by DISTRIBUTOR_OLM,TYPE_OF_STOCK,PRODUCT_NAME WITH UR   ";
				else
				sql = " WITH TEMP  (DIST_ID,Distributor_OLM,Account_Name,Circle_Name,TSM_Name, PRODUCT_NAME,Type_of_Stock,Serial_Number,Distributor_Flag,Distributor_Remarks,Flushout_Flag,Debit_Flag,DTH_Remarks,Other_Remarks,MAIN_FLAG)   AS (    SELECT AD.ACCOUNT_ID DIST_ID,LM.login_name     AS Distributor_OLM, AD.ACCOUNT_NAME   AS Account_Name,  (	SELECT CIRCLE_NAME  FROM VR_CIRCLE_MASTER WHERE CIRCLE_ID = AD.CIRCLE_ID )  	AS Circle_Name,      (select TSM_NAME from DP_DIST_DETAILS where DISTRIBUTOR_ID = AD.ACCOUNT_ID) as TSM_Name,  	(	SELECT 	PRODUCT_NAME  FROM DP_PRODUCT_MASTER WHERE PRODUCT_ID = RUS.DEFECTIVE_PRODUCT_ID ) AS PRODUCT_NAME,  	'Upgrade'         AS Type_of_Stock,      RUS.Defective_SR_No as Serial_Number,   	RUS.DIST_FLAG     AS Distributor_Flag,  	RUS.DIST_REMARKS  AS Distributor_Remarks,  	RUS.FLUSHOUT_FLAG AS Flushout_Flag,    RUS.DEBIT_Flag as Debit_Flag,	RUS.DTH_REMARKS   AS DTH_Remarks, Other_Remarks,      CASE WHEN RUS.DIST_FLAG='N' AND trim(DEBIT_FLAG) not in ( 'Y', 'N') THEN 1   WHEN (RUS.DIST_FLAG IS NULL or RUS.DIST_FLAG='') THEN 1    WHEN trim(RUS.DIST_FLAG) ='N' AND DEBIT_FLAG is NULL THEN 1      ELSE 0 END MAIN_FLAG  FROM  	RECO_UPGRADE_STOCK RUS INNER JOIN VR_LOGIN_MASTER LM ON LM.LOGIN_ID = RUS.FROM_DIST_ID INNER JOIN   	VR_ACCOUNT_DETAILS AD ON LM.LOGIN_ID = AD.ACCOUNT_ID  WHERE   AD.ACCOUNT_ID IN ("+distList+ ")      AND RUS.Defective_Product_id in(SELECT PRODUCT_ID from DP_PRODUCT_MASTER where CATEGORY_CODE=1 and PRODUCT_CATEGORY in (select value from dp_configuration_details where config_id=8 and id in ("+ prodList + ") ) )  UNION ALL    SELECT AD.ACCOUNT_ID DIST_ID,LM.login_name  AS Distributor_OLM, AD.ACCOUNT_NAME AS Account_Name,         (select CIRCLE_NAME from VR_CIRCLE_MASTER where CIRCLE_ID = AD.CIRCLE_ID) as Circle_Name,         (select TSM_NAME from DP_DIST_DETAILS where DISTRIBUTOR_ID = AD.ACCOUNT_ID) as TSM_Name,         (select PRODUCT_NAME from DP_PRODUCT_MASTER where PRODUCT_ID = RUS.PRODUCT_ID) as PRODUCT_NAME,         'Serialized stock' as Type_of_Stock,          RUS.Serial_No as Serial_Number,         RUS.DIST_FLAG as Distributor_Flag,         RUS.DIST_REMARKS as Distributor_Remarks,         RUS.FLUSHOUT_FLAG as Flushout_Flag,      RUS.DEBIT_Flag as Debit_Flag,     RUS.DTH_REMARKS as DTH_Remarks, Other_Remarks,        CASE WHEN RUS.DIST_FLAG='N' AND trim(DEBIT_FLAG)  not in ( 'Y', 'N') THEN 1   WHEN (RUS.DIST_FLAG IS NULL or RUS.DIST_FLAG='') THEN 1    WHEN trim(RUS.DIST_FLAG) ='N' AND DEBIT_FLAG is NULL THEN 1       ELSE 0 END MAIN_FLAG  FROM  	RECO_SERIALIZED_STOCK RUS INNER JOIN VR_LOGIN_MASTER LM ON LM.LOGIN_ID = RUS.DISTRIBUTOR_ID  	INNER JOIN VR_ACCOUNT_DETAILS AD ON LM.LOGIN_ID = AD.ACCOUNT_ID  WHERE  AD.ACCOUNT_ID IN ("+distList+ ")      AND RUS.PRODUCT_ID in(SELECT PRODUCT_ID from DP_PRODUCT_MASTER where CATEGORY_CODE=1 and PRODUCT_CATEGORY in (select value from dp_configuration_details where config_id=8 and id in ("+ prodList + ") ) )    UNION ALL    SELECT AD.ACCOUNT_ID DIST_ID,LM.login_name  AS Distributor_OLM, AD.ACCOUNT_NAME AS Account_Name,         (select CIRCLE_NAME from VR_CIRCLE_MASTER where CIRCLE_ID = AD.CIRCLE_ID) as Circle_Name,         (select TSM_NAME from DP_DIST_DETAILS where DISTRIBUTOR_ID = AD.ACCOUNT_ID) as TSM_Name,         (select PRODUCT_NAME from DP_PRODUCT_MASTER where PRODUCT_ID = RUS.defective_product_id) as PRODUCT_NAME,         'Defective Stock' as Type_of_Stock,         RUS.Defective_SR_No as Serial_Number,         RUS.DIST_FLAG as Distributor_Flag,         RUS.DIST_REMARKS as Distributor_Remarks,         RUS.FLUSHOUT_FLAG as Flushout_Flag,     RUS.DEBIT_Flag as Debit_Flag,     RUS.DTH_REMARKS as DTH_Remarks, Other_Remarks,       CASE WHEN RUS.DIST_FLAG='N' AND trim(DEBIT_FLAG) not in ( 'Y', 'N') THEN 1    WHEN (RUS.DIST_FLAG IS NULL or RUS.DIST_FLAG='') THEN 1    WHEN trim(RUS.DIST_FLAG) ='N' AND DEBIT_FLAG is NULL THEN 1      ELSE 0 END MAIN_FLAG  FROM  	RECO_DEFECTIVE_STOCK RUS INNER JOIN VR_LOGIN_MASTER LM ON LM.LOGIN_ID = RUS.FROM_DIST_ID  	INNER JOIN VR_ACCOUNT_DETAILS AD ON LM.LOGIN_ID = AD.ACCOUNT_ID  WHERE   AD.ACCOUNT_ID IN ("+distList+ ")    AND RUS.Defective_Product_id in(SELECT PRODUCT_ID from DP_PRODUCT_MASTER where CATEGORY_CODE=1 and PRODUCT_CATEGORY in (select value from dp_configuration_details where config_id=8 and id in ("+ prodList + ") ))    UNION ALL    SELECT AD.ACCOUNT_ID DIST_ID,LM.login_name  AS Distributor_OLM, AD.ACCOUNT_NAME AS Account_Name,         (select CIRCLE_NAME from VR_CIRCLE_MASTER where CIRCLE_ID = AD.CIRCLE_ID) as Circle_Name,         (select TSM_NAME from DP_DIST_DETAILS where DISTRIBUTOR_ID = AD.ACCOUNT_ID) as TSM_Name,         (select PRODUCT_NAME from DP_PRODUCT_MASTER where PRODUCT_ID = RUS.defective_product_id) as PRODUCT_NAME,         'Churn' as Type_of_Stock,         RUS.Defective_SR_No as Serial_Number,         RUS.DIST_FLAG as Distributor_Flag,         RUS.DIST_REMARKS as Distributor_Remarks,         RUS.FLUSHOUT_FLAG as Flushout_Flag,    RUS.DEBIT_Flag as Debit_Flag,     RUS.DTH_REMARKS as DTH_Remarks, Other_Remarks,        CASE WHEN RUS.DIST_FLAG='N' AND trim(DEBIT_FLAG) not in ( 'Y', 'N') THEN 1     WHEN (RUS.DIST_FLAG IS NULL or RUS.DIST_FLAG='') THEN 1  WHEN trim(RUS.DIST_FLAG) ='N' AND DEBIT_FLAG is NULL THEN 1     ELSE 0 END MAIN_FLAG  FROM  	RECO_CHURN_STOCK RUS INNER JOIN VR_LOGIN_MASTER LM ON LM.LOGIN_ID = RUS.FROM_DIST_ID  	INNER JOIN VR_ACCOUNT_DETAILS AD ON LM.LOGIN_ID = AD.ACCOUNT_ID  WHERE   AD.ACCOUNT_ID IN ("+distList+ ")    AND RUS.Defective_Product_id in(SELECT PRODUCT_ID from DP_PRODUCT_MASTER where CATEGORY_CODE=1 and PRODUCT_CATEGORY in (select value from dp_configuration_details where config_id=8 and id in ("+ prodList + ") ) )   )     SELECT * FROM TEMP where DIST_ID IN (SELECT DIST_ID FROM TEMP WHERE MAIN_FLAG=1)  order by DISTRIBUTOR_OLM,TYPE_OF_STOCK,PRODUCT_NAME WITH UR   ";	
					
	
			}
			ps = db2conn.prepareStatement(sql);
			logger.info(sql);
			if(recoPeriod != null && !recoPeriod.equals("")) {
				//toDate = Utility.getSqlDateFromString(recoPeriod, Constants.DT_FMT_SQL);
				//toDate = Utility.getSqlDateFromString(recoPeriod, "yyyy-MM-dd");
				if(!recoPeriod.equals("-1"))
				{
				ps.setDate(1, new java.sql.Date(toDate.getTime()));
				ps.setDate(2, new java.sql.Date(toDate.getTime()));
				ps.setDate(3, new java.sql.Date(toDate.getTime()));
				ps.setDate(4, new java.sql.Date(toDate.getTime()));
				}
				
			}
			rs = ps.executeQuery();
			
			
			while (rs.next()) {
				DistRecoDto recDTO = new DistRecoDto();
				//String fromDate = Utility.converDateToString(rs.getDate("FROM_DATE"), Constants.DT_FMT_RECO);
				//String toDate = Utility.converDateToString(rs.getDate("TO_DATE"), Constants.DT_FMT_RECO);
				String dist_OLM = rs.getString("DISTRIBUTOR_OLM");
				String account_Name = rs.getString("ACCOUNT_NAME");
				String circle_Name = rs.getString("CIRCLE_NAME");
				String tsmName = rs.getString("TSM_NAME");
				String product_Name = rs.getString("PRODUCT_NAME");
				String type_of_stock = rs.getString("TYPE_OF_STOCK");
				String serialNo = rs.getString("SERIAL_NUMBER");
				String dist_flag = rs.getString("DISTRIBUTOR_FLAG");
				String dist_Remarks = rs.getString("DISTRIBUTOR_REMARKS");
				String flushout_Flag = rs.getString("FLUSHOUT_FLAG");
				String debit_flag = rs.getString("DEBIT_FLAG");
				String dth_Remarks = rs.getString("DTH_REMARKS");
				String other_Remarks = rs.getString("OTHER_REMARKS");
				
				//Integer recoID = rs.getInt("ID");
				//recDTO.setFromDate(fromDate);
				//recDTO.setToDate(toDate);
				//recDTO.setRecoPeriodId(recoID.toString());
				recDTO.setDistOlmId(dist_OLM);
				recDTO.setAccountName(account_Name);
				recDTO.setCircleName(circle_Name);
				recDTO.setTsmName(tsmName);
				recDTO.setProductName(product_Name);
				recDTO.setTypeOfStock(type_of_stock);
				recDTO.setSerialNo(" "+serialNo);
				recDTO.setDistFlag(dist_flag);
				recDTO.setDistRemarks(dist_Remarks);
				recDTO.setFlushOutFlag(flushout_Flag);
				recDTO.setDebitFlag(debit_flag);
				recDTO.setDthRemarks(dth_Remarks);
				recDTO.setOtherRemarks(other_Remarks);
				recoList.add(recDTO);
			}
			
		} catch (Exception e) {
				e.printStackTrace();
				throw new DAOException("Exception: " + e.getMessage());
				}
		finally {
			DBConnectionManager.releaseResources(ps ,rs);			
			DBConnectionManager.releaseResources(db2conn);
			}
		return recoList;
		
	}
	
	public Map uploadExcel(InputStream inputstream, String recoId) throws DAOException {

		System.out.println("uploadExcel : in DAOImpl");
		List list = new ArrayList();
		DistRecoDto dto = null;
		String distOlmId ="";
		String pinCode="";
		String typeOfStock="";
		String serialNo="";
		String distFlag="";
		String distRemarks="";
		String flushOutFlag="";
		String dthRemarks="";
		ResultSet rst =null;
		boolean validateFlag =true;
		Connection connection = null; 
		ArrayList<DuplicateSTBDTO> flushListUploadDTO = new ArrayList<DuplicateSTBDTO>();
		ArrayList<DuplicateSTBDTO> flushListUploadDTOReadDTO = new ArrayList<DuplicateSTBDTO>();
		
		DuplicateSTBDTO flushUploadDto  = null;
		ArrayList<DuplicateSTBDTO> stbList = new ArrayList<DuplicateSTBDTO>();
		ArrayList<DuplicateSTBDTO> queryListUpload = new ArrayList<DuplicateSTBDTO>();
		HashMap<String,List> returnMap = new HashMap<String ,List>();
		HashMap<String,List> errorMap = new HashMap<String ,List>();
		String serialNumber ="";
		//Neetika
	
	
		PreparedStatement pstmtClosing = null;//Neetika
		PreparedStatement pstmtClosingDef = null;//Neetika
		PreparedStatement pstmtClosingUp = null;//Neetika
		PreparedStatement pstmtClosingchurn = null;//Neetika
		ResultSet rsClosing=null;//Neetika
		String flushoutFlagtemp=null;
		String flushoutFlagfinal=null;
	
		try
		{
			connection = DBConnectionManager.getDBConnection();
			
			String sqlSerializedStockQuery="select FLUSHOUT_FLAG from reco_serialized_stock where reco_id=? and SERIAL_NO=? with ur";
			pstmtClosing =connection.prepareStatement(sqlSerializedStockQuery);
			
			String sqlDefStockQuery="select FLUSHOUT_FLAG from reco_defective_stock where reco_id=? and DEFECTIVE_SR_NO=? with ur";
			pstmtClosingDef =connection.prepareStatement(sqlDefStockQuery);
			
			String sqlUpgradeStockQuery="select FLUSHOUT_FLAG from reco_upgrade_stock where reco_id=? and DEFECTIVE_SR_NO=? with ur";
			pstmtClosingUp =connection.prepareStatement(sqlUpgradeStockQuery);
			
			String sqlChurnStockQuery="select FLUSHOUT_FLAG from reco_churn_stock where reco_id=? and DEFECTIVE_SR_NO=? with ur";
			pstmtClosingchurn =connection.prepareStatement(sqlChurnStockQuery);
			
			
			STBFlushOutDaoImpl flushDao = new STBFlushOutDaoImpl(connection);
			//System.out.println("1");
			  HSSFWorkbook workbook = new HSSFWorkbook(inputstream);
			  //System.out.println("2");
			  HSSFSheet sheet = workbook.getSheetAt(0);
			 // System.out.println("3");
			  Iterator rows = sheet.rowIterator();
			  int totalrows = sheet.getLastRowNum()+1;
			  logger.info("Total Rows == "+sheet.getLastRowNum());
			 /* if(totalrows > Integer.parseInt(ResourceReader.getValueFromBundle(com.ibm.virtualization.recharge.common.Constants.BULK_UPLAOD_MAX_LIMIT , com.ibm.virtualization.recharge.common.Constants.WEBSERVICE_RESOURCE_BUNDLE)))
			  {
				   dto = new DistRecoDto();
				   dto.setErr_msg("Limit exceeds: Maximum "+Integer.parseInt(ResourceReader.getValueFromBundle(com.ibm.virtualization.recharge.common.Constants.BULK_UPLAOD_MAX_LIMIT, com.ibm.virtualization.recharge.common.Constants.WEBSERVICE_RESOURCE_BUNDLE))+" Records are allowed in a file.");
        		  list.add(dto);
             		errorMap.put("error", list);
         			return errorMap;
			  }*/
			  int tolRow=sheet.getLastRowNum();
			  if(tolRow==0)
			  {

		        	logger.info("************inside blank file condition******");
		        	dto = new DistRecoDto();
		        	dto.setErr_msg("Blank File can not be uploaded!!");
	        		  list.add(dto);  	
                 		errorMap.put("error", list);
             			return errorMap;
			  }
			  if(tolRow> 200)
			  {

		        	logger.info("************inside Number of rows exceed condition******");
		        	dto = new DistRecoDto();
		        	dto.setErr_msg("Number of rows in file cannot exceed 200 records, Please remove excess rows");
	        		  list.add(dto);  	
                 		errorMap.put("error", list);
             			return errorMap;
			  }
			  
			  int rowNumber = 1;
			  DistRecoDto mapDto = null;
	          ArrayList checkDuplicate = new ArrayList();
	          int k=1;
	          int count=0;
	          while (rows.hasNext()) {
	        	  boolean localvalidate=true;
	        	  mapDto = new DistRecoDto();
	        	  mapDto.setRecoPeriodId(recoId);
	        	  HSSFRow row = (HSSFRow) rows.next();
	        	  Iterator cells = row.cellIterator();
	        	 // logger.info("************rowNumber"+rowNumber+"cells*********"+cells);
	        	  if(rowNumber>1)
	        	  {
	        	  int columnIndex = 0;
	              int cellNo = 0;
	              if(cells != null)
	              {
	            	distOlmId ="";
	            	pinCode ="";
	            	typeOfStock="";
	        		serialNo="";
	        		distFlag="";
	        		distRemarks="";
	        		flushOutFlag="";
	        		dthRemarks="";
	        		int i =0;
	    			//while (cells.hasNext()) {
	        		flushUploadDto= new DuplicateSTBDTO();
	        		while (i<13) {
	        			i++;
	        			//flushUploadDto= new DuplicateSTBDTO();
	        		  if(cellNo < 13)
	        		  {
	        			  cellNo++;
	        			  HSSFCell cell = (HSSFCell) cells.next();
	        			  columnIndex = cell.getColumnIndex();
	        			  
		              		if(columnIndex > 12)
		              		{
		              			dto = new DistRecoDto();
		              			dto.setErr_msg("File should contain only 13 data column");
		              			logger.info("File should contain only 13 data column");
		              			list.add(dto);
		                   		errorMap.put("error", list);
		             			return errorMap;
		              		}
		              		String cellValue = null;
		        		  
		            		switch(cell.getCellType()) {
			            		case HSSFCell.CELL_TYPE_NUMERIC:
			            			cellValue = String.valueOf((long)cell.getNumericCellValue());
			            		break;
			            		case HSSFCell.CELL_TYPE_STRING:
			            			cellValue = cell.getStringCellValue();
				            	break;
		            		}
		            		//logger.info("Row Number == "+rowNumber +" and Cell No. === "+cellNo +" and value == "+cellValue );
		            		if(cellNo==2)
		            		{
		            			//logger.info("Checking OLM ID 1 ");
		            			boolean validate = validateOlmId(cellValue.trim());
		            			//logger.info("Checking OLM ID 2");
		            			if(!validate)
			            		{
		            				validateFlag =false;
		            				localvalidate=false;
		            				dto = new DistRecoDto(); 
		            				dto.setErr_msg("Invalid OLM Id at row No: "+(rowNumber)+". It should be 8 character.");
		            				logger.info("Invalid OLM Id at row No: "+(rowNumber)+". It should be 8 character.");
			              			list.add(dto);
		            				
		            			}
		            			else
		            			{
		            				if(validateOlmdistId(cellValue.trim())){
		            				distOlmId=cellValue;
		            				mapDto.setStrDistOLMIds(cellValue.trim());
		            				flushUploadDto.setStrDistOLMId(cellValue.trim());
		            				
		            				}
		            				else
		            				{
		            					validateFlag =false;
		            					localvalidate=false;
		            					dto = new DistRecoDto(); 
		            					dto.setErr_msg("Invalid Distributor OLM Id at row No:  "+(rowNumber));
		            					logger.info("Invalid Distributor OLM Id at row No:  "+(rowNumber));
				              			list.add(dto);
		            					
		            				}
			            			
			            		}
		            		}   //end  if cell No 2
		            		else if(cellNo==7)
		            		{
		            			//logger.info("Checking type of product 1 ");
		            			boolean validate = validatePproductType(cellValue.trim());
		            			//logger.info("Checking type of product 1 ");
		            			typeOfStock=cellValue;
		            			mapDto.setTypeOfStock(cellValue);
		            			System.out.println("mapDto.getTypeOfStock() :"+mapDto.getTypeOfStock());
		            			if(!validate)
			            		{
		            				validateFlag =false;
		            				localvalidate=false;
		            				dto = new DistRecoDto(); 
		            				dto.setErr_msg("Invalid Product Type at row No: "+(rowNumber)+".");
		            				logger.info("Invalid Product Type at row No: "+(rowNumber));
			              			list.add(dto);
		            				
		            			}
		            			else
		            			{
		            				if(validatePproductType(cellValue.trim())){
		            					typeOfStock=cellValue;
		            				mapDto.setTypeOfStock(cellValue);
		            				}
		            				else
		            				{
		            					validateFlag =false;
		            					localvalidate=false;
		            					dto = new DistRecoDto(); 
		            					dto.setErr_msg("Invalid Product Type at row No:  "+(rowNumber));
				              			list.add(dto);
		            					
		            				}
			            			
			            		}
		            			
		            			
	          		} 		//end else if cell No 7
		            		else if(cellNo==8)
		            		{
		            			//logger.info("Checking serial no 1 ");
		            			boolean validate = validateSerialNo(cellValue.trim());
		            			//logger.info("Checking serial no 2 ");
		            			serialNo=cellValue;
		            			mapDto.setSerialNo(cellValue);
		            			if(!validate)
			            		{
		            				validateFlag =false;
		            				localvalidate=false;
		            				dto = new DistRecoDto(); 
		            				dto.setErr_msg("Invalid Serial Number at row No: "+(rowNumber)+".");
			              			list.add(dto);
		            				
		            			}
		            			else
		            			{
		            				if(validateSerialNo(cellValue.trim())){
		            					serialNo=cellValue;
		            				mapDto.setSerialNo(cellValue);

		            				}
		            				else
		            				{
		            					validateFlag =false;
		            					localvalidate=false;
		            					dto = new DistRecoDto(); 
		            					dto.setErr_msg("Invalid Serial Number at row No:  "+(rowNumber));
				              			list.add(dto);
		            					
		            				}
			            			
			            		}
		            			
		            		} //end else if cell No 8
		            		
		            		else if(cellNo==9)
		            		{
		            			//logger.info("Checking dist flag 1 ");
		            			boolean validate = validateDistFlag(cellValue.trim());
		            			//logger.info("Checking dist flag 2 ");
		            			distFlag=cellValue;
		            			mapDto.setDistFlag(cellValue);
		            			if(!validate)
			            		{
		            				validateFlag =false;
		            				localvalidate=false;
		            				dto = new DistRecoDto(); 
		            				dto.setErr_msg("Invalid Distributor Flag at row No: "+(rowNumber)+".");
			              			list.add(dto);
		            				
		            			}
		            			else
		            			{
		            				if(validateDistFlag(cellValue.trim())){
		            					distFlag=cellValue;
		            				mapDto.setDistFlag(cellValue);
		            				}
		            				else
		            				{
		            					validateFlag =false;
		            					localvalidate=false;
		            					dto = new DistRecoDto(); 
		            					dto.setErr_msg("Invalid Distributor Flag at row No:  "+(rowNumber));
				              			list.add(dto);
		            					
		            				}
			            			
			            		}
		            			
		            		} //end else if cell No 9
		            		
		            		else if(cellNo==10)
		            		{
		            			//logger.info("Checking dist remarks 1 ");
		            			//boolean validate = validateDistRemarks(cellValue.trim());
		            			//logger.info("Checking dist remarks 2 ");
		            			distRemarks=cellValue;
		            			boolean validate = true; // Not Validating Distributor remarks.
		            			mapDto.setDistRemarks(cellValue);
		            			if(!validate)
			            		{
		            				validateFlag =false;
		            				localvalidate=false;
		            				dto = new DistRecoDto(); 
		            				dto.setErr_msg("Invalid Distributor Remarks at row No: "+(rowNumber)+".");
			              			list.add(dto);
		            				
		            			}
		            			else
		            			{
		            				if(validateDistRemarks(cellValue.trim())){
		            					distRemarks=cellValue;
		            				mapDto.setDistRemarks(cellValue);
		            				}
		            				else
		            				{
		            					validateFlag =false;
		            					localvalidate=false;
		            					dto = new DistRecoDto(); 
		            					dto.setErr_msg("Invalid Distributor Remarks at row No:  "+(rowNumber));
				              			list.add(dto);
		            					
		            				}
			            			
			            		}
		            			
		            		} //end else if cell No 10
		            		
		            		
		            		else if(cellNo==11)
		            		{
		            			//logger.info("Checking flush out flag 1 ");
		            			boolean validate = validateFlushOutFlag(cellValue.trim());
		            			//logger.info("Checking flush out flag 1 ");
		            			flushOutFlag=cellValue;
		            			mapDto.setFlushOutFlag(cellValue);
		            			if(!validate)
			            		{
		            				validateFlag =false;
		            				localvalidate=false;
		            				dto = new DistRecoDto(); 
		            				dto.setErr_msg("Invalid Flush Out Flag at row No: "+(rowNumber)+".");
			              			list.add(dto);
		            				
		            			}
		            			else
		            			{
		            				if(validateFlushOutFlag(cellValue.trim())){
		            					flushOutFlag=cellValue;
		            				mapDto.setFlushOutFlag(cellValue);
			            				/* Adding The serial Number to flush out only when Flush out flag is "Y" */
			            				if(mapDto.getFlushOutFlag().trim().equalsIgnoreCase("Y")){
			            				
			            					//add code to chck previous status in table neetika
			            					//Neetika put code here to check in 4 tables
			            					//Serialized Table
			            					flushoutFlagtemp=null;
			            					flushoutFlagfinal=null;
			            					pstmtClosing.setString(1, recoId);
			            					pstmtClosing.setString(2, mapDto.getSerialNo().trim());
			            					rsClosing=pstmtClosing.executeQuery();
			            					if(rsClosing.next())
			            					{
			            						flushoutFlagtemp=rsClosing.getString("FLUSHOUT_FLAG");
			            						if(flushoutFlagtemp!=null && !(flushoutFlagtemp.equalsIgnoreCase("")))
			            						{
			            							flushoutFlagfinal=flushoutFlagtemp;
			            							//logger.info("flushoutFlag SR NO  Ser::  "+flushoutFlagfinal);
			            						}
			            						
			            					}
			            					//Defective table
			            					pstmtClosingDef.setString(1, recoId);
			            					pstmtClosingDef.setString(2,mapDto.getSerialNo().trim());
			            					rsClosing=pstmtClosingDef.executeQuery();
			            					if(rsClosing.next())
			            					{
			            						flushoutFlagtemp=rsClosing.getString("FLUSHOUT_FLAG");
			            						if(flushoutFlagtemp!=null && !(flushoutFlagtemp.equalsIgnoreCase("")))
			            						{
			            							flushoutFlagfinal=flushoutFlagtemp;
			            							//logger.info("flushoutFlag SR NO  Def::  "+flushoutFlagfinal);
			            						}
			            					
			            					}
			            					//Upgrade table
			            					pstmtClosingUp.setString(1, recoId);
			            					pstmtClosingUp.setString(2, mapDto.getSerialNo().trim());
			            					rsClosing=pstmtClosingUp.executeQuery();
			            					if(rsClosing.next())
			            					{
			            						flushoutFlagtemp=rsClosing.getString("FLUSHOUT_FLAG");
			            						if(flushoutFlagtemp!=null && !(flushoutFlagtemp.equalsIgnoreCase("")))
			            						{
			            							flushoutFlagfinal=flushoutFlagtemp;
			            							//logger.info("flushoutFlag SR NO  upgrade::  "+flushoutFlagfinal);
			            						}
			            					}
			            					//Churn table
			            					pstmtClosingchurn.setString(1, recoId);
			            					pstmtClosingchurn.setString(2, mapDto.getSerialNo().trim());
			            					rsClosing=pstmtClosingchurn.executeQuery();
			            					if(rsClosing.next())
			            					{
			            						flushoutFlagtemp=rsClosing.getString("FLUSHOUT_FLAG");
			            						if(flushoutFlagtemp!=null && !(flushoutFlagtemp.equalsIgnoreCase("")))
			            						{
			            							flushoutFlagfinal=flushoutFlagtemp;
			            							//logger.info("flushoutFlag SR NO  churn::  "+flushoutFlagfinal);
			            						}
			            					}
			            					
			            					
			            					
			            					
			            					
			            				//	logger.info("flushoutFlag SR NO  ::  "+flushoutFlagfinal+ "serialNumber :  "+mapDto.getSerialNo().trim());
			            					if((flushoutFlagfinal!=null && !(flushoutFlagfinal.trim().equals("Y"))) || (flushoutFlagfinal==null))
			            					{
			            						
				            				serialNumber=serialNumber+"'"+mapDto.getSerialNo().trim()+"'"+",";
				            				
				            				int tempcount=flushDao.getStatusListclose("'"+mapDto.getSerialNo().trim()+"'"); //csv 
				            				if(tempcount>0)
				            				count=count+1;
				            				else
				            				{
				            					
				            				validateFlag =false;
				            				localvalidate=false;
				            				dto = new DistRecoDto(); 
				            				dto.setErr_msg(mapDto.getSerialNo().trim() +" STB is not available for Flushout: "+(rowNumber)+".");
					              			list.add(dto);
				            				}
			            					}
			            					
					            			flushUploadDto.setStrSerialNo(mapDto.getSerialNo().trim());
			            				}
			            			

		            				}
		            				else
		            				{
		            					validateFlag =false;
		            					localvalidate=false;
		            					dto = new DistRecoDto(); 
		            					dto.setErr_msg("Invalid Flush Out Flag at row No:  "+(rowNumber));
				              			list.add(dto);
		            					
		            				}
			            			
			            		}
		            			
		            		} //end else if cell No 11
		            		
		            		else if(cellNo==12)
		            		{
		            			//logger.info("Checking debit flag 1 ");
		            			boolean validate = validateFlushOutFlag(cellValue.trim());
		            			//logger.info("Checking debit flag 1  ");
		            			mapDto.setDebitFlag(cellValue);
		            			if(!validate)
			            		{
		            				validateFlag =false;
		            				localvalidate=false;
		            				dto = new DistRecoDto(); 
		            				dto.setErr_msg("Invalid Debit Flag at row No: "+(rowNumber)+".");
			              			list.add(dto);
		            				
		            			}
		            			else
		            			{
		            				if(validateFlushOutFlag(cellValue.trim())){
		            			
		            				mapDto.setDebitFlag(cellValue);
		            				}
		            				else
		            				{
		            					validateFlag =false;
		            					localvalidate=false;
		            					dto = new DistRecoDto(); 
		            					dto.setErr_msg("Invalid Debit Flag at row No:  "+(rowNumber));
				              			list.add(dto);
		            					
		            				}
			            			
			            		}
		            			
		            		} //end else if cell No 12

		            		
		            		else if(cellNo==13)
		            		{
		            			//logger.info("Checking DTH remarks 1 ");
		            			boolean validate = validateDTHRemarks(cellValue.trim());
		            			//logger.info("Checking DTH remarks 1 ");
		            			dthRemarks=cellValue;
		            			mapDto.setDthRemarks(cellValue);
		            			if(!validate)
			            		{
		            				validateFlag =false;
		            				localvalidate=false;
		            				dto = new DistRecoDto(); 
		            				dto.setErr_msg("Invalid DTH Remarks/ More Than 250 characters  at row No: "+(rowNumber)+".");
			              			list.add(dto);
		            				
		            			}
		            			else
		            			{
		            				if(validateDTHRemarks(cellValue.trim())){
		            					dthRemarks=cellValue;
		            				mapDto.setDthRemarks(cellValue);
		            				/* Adding The Remarks to flush out only when Flush out flag is "Y" */
		            				if(mapDto.getFlushOutFlag().trim().equalsIgnoreCase("Y")){
				            			flushUploadDto.setStrRemarks((mapDto.getDthRemarks().trim()));
				            			flushListUploadDTO.add(flushUploadDto);
		            				}
		            				}
		            				else
		            				{
		            					validateFlag =false;
		            					localvalidate=false;
		            					dto = new DistRecoDto(); 
		            					dto.setErr_msg("Invalid DTH Remarks/ More Than 250 characters  at row No:  "+(rowNumber));
				              			list.add(dto);
		            					
		            				}
			            			
			            		}
		            			// flushListUploadDTO.add(flushUploadDto);
		            			
		            		} //end else if cell No 13
		            		
		    				
	        		  }
	        		  
	        		  else
	        		  {
	        			  validateFlag =false;
	        			  localvalidate=false;
	        			  dto = new DistRecoDto(); 
	        			  dto.setErr_msg("File should contain only 13 data column at row No: "+(rowNumber)+".");
	        			  logger.info("File should contain only 13 data column at row No: "+(rowNumber)+".");
              			 list.add(dto);
                   		errorMap.put("error", list);
             			return errorMap;

	        		  }
	        		  
	    			}
	            	
	    			if(cellNo != 13){
		            		validateFlag =false;
		            		localvalidate=false;
		            		dto = new DistRecoDto(); 
		            		dto.setErr_msg("File should contain 13 data Headers");
		            		logger.info("File should contain 13 data Headers");
		            		List list2 = new ArrayList();
		            		list2.add(dto);
		              		errorMap.put("error", list2);
		         			return errorMap;

		            	}
            		
            		// Validating the Data Flags for Logical Correctness
            		
	    			if (validateFlag){
	    				String msg = validateFlags(mapDto);
	    				if(!msg.equals("")){
	    					dto = new DistRecoDto(); 
		            		dto.setErr_msg(msg+" at row No: "+ +(rowNumber)+".");
		            		validateFlag =false;
		            		localvalidate=false;
		            		logger.info(msg);
		            		list.add(dto);
		              		//errorMap.put("error", list);
		         			//return errorMap;
	    					
	    				}
	    				
	    			} 
	    			           	
	        	  rowNumber++;
	            }
	              
	            else
	            {
	            	dto = new DistRecoDto();
	            	dto.setErr_msg("File should contain only 13 data column");
	            	logger.info("File should contain 13 data Headers..");
         			list.add(dto);
              		errorMap.put("error", list);
         			return errorMap;
	            }
 	              logger.info("In Row num == "+ (rowNumber-1) +" Error Message ==  "+validateFlag);
	        	  if(localvalidate) //Neetika changed to localvalidate=false;
	        	  {
	        		  mapDto.setErr_msg("SUCCESS");
	        		  list.add	(mapDto);  		        		  
	        	  }
	             
	        	  }
	        	  
	              else
	        	  {
	        		  logger.info("*************Header checking*************");
	  	        	//For Header Checking  
	  	        	int columnIndex = 0;
	  	              int cellNo = 0;
	  	              if(cells != null)
	  	              {
	  	            	  while (cells.hasNext()) {
	  		        		  if(cellNo < 13)
	  		        		  {
	  		        			  cellNo++;
	  		        			  HSSFCell cell = (HSSFCell) cells.next();
	  		        			  
	  		        			  	columnIndex = cell.getColumnIndex();
	  			              		if(columnIndex > 14)
	  			              		{
	  			              			validateFlag =false;
	  			              		localvalidate=false;
	  			              		dto= new DistRecoDto();
	  			              	dto.setErr_msg("File should contain only 13 data column");
	  			              			List list2 = new ArrayList();
	  				            		list2.add(dto);
	  			                   		errorMap.put("error", list2);
	  			             			return errorMap;
	  			              		}
	  			              		String cellValue = null;
	  			        		  
	  			            		switch(cell.getCellType()) {
	  				            		case HSSFCell.CELL_TYPE_NUMERIC:
	  				            			cellValue = String.valueOf((long)cell.getNumericCellValue());
	  				            		break;
	  				            		case HSSFCell.CELL_TYPE_STRING:
	  				            			cellValue = cell.getStringCellValue();
	  					            	break;
	  			            		}
	  			            		if(cellValue ==  null || "".equalsIgnoreCase(cellValue.trim()))
	  			            		{
	  			            			validateFlag =false;
	  			            			localvalidate=false;
	  			            			dto = new DistRecoDto();
	  			            			dto.setErr_msg("File should contain All Required Values");
	  			              			List list2 = new ArrayList();
	  				            		list2.add(dto);
	  			                   		errorMap.put("error", list2);
	  			             			return errorMap;
	  			               		}
	  			            		
	  			            		// logger.info("Row Number == "+rowNumber +" and Cell No. === "+cellNo +" and value == "+cellValue.trim().toLowerCase() );
	  			            		if(cellNo==1){
	  			            			if(!"S.No.".equals(cellValue.trim()))
	  				            		{
	  			            				validateFlag =false;
	  			            				localvalidate=false;
	  			            				dto = new DistRecoDto();
	  			            				dto.setErr_msg("Invalid Header For S.No");
	  			            				logger.info("Invalid Header For S.No");
	  				              			list.add(dto);
	  			            				
	  			            			}
	  			            		}else if(cellNo==2){
	  			            			if(!"Distributor OLM".equals(cellValue.trim()))
	  				            		{
	  			            				validateFlag =false;
	  			            				localvalidate=false;
	  			            				dto = new DistRecoDto();
	  			            				dto.setErr_msg("Invalid Header For Distributor OLM");
	  			            				logger.info("Invalid Header For Distributor OLM");
	  				              			list.add(dto);
	  			            				
	  			            			}
	  			            		}
	  			            		else if(cellNo==3){
	  			            			if(!"Distributor Name".equals(cellValue.trim()))
	  				            		{
	  			            				validateFlag =false;
	  			            				localvalidate=false;
	  			            				dto = new DistRecoDto();
	  			            				dto.setErr_msg("Invalid Header For Distributor Name");
	  			            				logger.info("Invalid Header For Distributor Name");
	  				              			list.add(dto);
	  			            				
	  			            			}
	  			            		}
	  			            		else if(cellNo==4){
	  			            			if(!"Circle Name".equals(cellValue.trim()))
	  				            		{
	  			            				validateFlag =false;
	  			            				localvalidate=false;
	  			            				dto = new DistRecoDto();
	  			            				dto.setErr_msg("Invalid Header For Circle Name");
	  			            				logger.info("Invalid Header For Circle Name");
	  				              			list.add(dto);
	  			            				
	  			            			}
	  			            		}
	  			            		else if(cellNo==5){
	  			            			if(!"TSM Name".equals(cellValue.trim()))
	  				            		{
	  			            				validateFlag =false;
	  			            				localvalidate=false;
	  			            				dto = new DistRecoDto();
	  			            				dto.setErr_msg("Invalid Header For TSM Name");
	  			            				logger.info("Invalid Header For TSM Name");
	  				              			list.add(dto);
	  			            				
	  			            			}
	  			            		}
	  			            		else if(cellNo==6){
	  			            			if(!"Product Name".equals(cellValue.trim()))
	  				            		{
	  			            				validateFlag =false;
	  			            				localvalidate=false;
	  			            				dto = new DistRecoDto();
	  			            				dto.setErr_msg("Invalid Header For Product Name");
	  			            				logger.info("Invalid Header For Product Name");
	  				              			list.add(dto);
	  			            				
	  			            			}
	  			            		}
	  			            		else if(cellNo==7){
	  			            			if(!"Type of Stock".equals(cellValue.trim()))
	  				            		{
	  			            				validateFlag =false;
	  			            				localvalidate=false;
	  			            				dto = new DistRecoDto();
	  			            				dto.setErr_msg("Invalid Header For Type of Stock");
	  			            				logger.info("Invalid Header For Type of Stock");
	  				              			list.add(dto);
	  			            				
	  			            			}
	  			            		}
	  			            		else if(cellNo==8){
	  			            			if(!"Serial Number".equals(cellValue.trim()))
	  				            		{
	  			            				validateFlag =false;
	  			            				localvalidate=false;
	  			            				dto = new DistRecoDto();
	  			            				dto.setErr_msg("Invalid Header For Serial Number");
	  			            				logger.info("Invalid Header For Serial Number");
	  				              			list.add(dto);
	  			            				
	  			            			}
	  			            		}
	  			            		
	  			            		else if(cellNo==9){
	  			            			if(!"Distributor Flag".equals(cellValue.trim()))
	  				            		{
	  			            				validateFlag =false;
	  			            				localvalidate=false;
	  			            				dto = new DistRecoDto();
	  			            				dto.setErr_msg("Invalid Header For Distributor Flag");
	  			            				logger.info("Invalid Header For Distributor Flag");
	  				              			list.add(dto);
	  			            				
	  			            			}
	  			            		}
	  			            		else if(cellNo==10){
	  			            			if(!"Distributor Remarks".equals(cellValue.trim()))
	  				            		{
	  			            				validateFlag =false;
	  			            				localvalidate=false;
	  			            				dto = new DistRecoDto();
	  			            				dto.setErr_msg("Invalid Header For Distributor Remarks");
	  			            				logger.info("Invalid Header For Distributor Remarks");
	  				              			list.add(dto);
	  			            				
	  			            			}
	  			            		}
	  			            	
	  			            		else if(cellNo==11){
	  			            			if(!"FlushOut Flag".equals(cellValue.trim()))
	  				            		{
	  			            				validateFlag =false;
	  			            				localvalidate=false;
	  			            				dto = new DistRecoDto();
	  			            				dto.setErr_msg("Invalid Header For FlushOut Flag");
	  			            				logger.info("Invalid Header For FlushOut Flag");
	  				              			list.add(dto);
	  			            				
	  			            			}
	  			            		}
		  			            	
	  			            		else if(cellNo==12){
	  			            			if(!"Debit Flag".equals(cellValue.trim()))
	  				            		{
	  			            				validateFlag =false;
	  			            				localvalidate=false;
	  			            				dto = new DistRecoDto();
	  			            				dto.setErr_msg("Invalid Header For Debit Flag");
	  			            				logger.info("Invalid Header For Debit Flag");
	  				              			list.add(dto);
	  			            				
	  			            			}
	  			            		}
	  			            		else if(cellNo==13){
	  			            			if(!"DTH Remarks".equals(cellValue.trim()))
	  				            		{
	  			            				validateFlag =false;
	  			            				localvalidate=false;
	  			            				dto = new DistRecoDto();
	  			            				dto.setErr_msg("Invalid Header For DTH Remarks");
	  			            				logger.info("Invalid Header For DTH Remarks");
	  				              			list.add(dto);
	  			            				
	  			            			}
	  			            		}
	  			            		
	  		        		  }else{
	  		        			  
	  		        			  validateFlag =false;
	  		        			localvalidate=false;
	  		        			dto = new DistRecoDto();
	  		        			dto.setErr_msg("File should contain only 13 data Headers");
	  		        		//	logger.info("File should contain only two data Headers");
	  		        			  List list2 = new ArrayList();
	  			            	list2.add(dto);
	  	                   		errorMap.put("error", list2);
	  	             			return errorMap;
	  		        		  }
	  	            	  }
	  	            	  if(cellNo !=13){
	  		            		validateFlag =false;
	  		          		localvalidate=false;
	  		            		dto = new DistRecoDto(); 
	  		            		dto.setErr_msg("File should contain 13 data Headers");
	  		            		List list2 = new ArrayList();
	  		            		list2.add(dto);
	  	                   		errorMap.put("error", list2);
	  	             			return errorMap;
	  		            	}
	  	              } else
	  		            {
	  	            	dto = new DistRecoDto(); 
	  	            	dto.setErr_msg("File should contain 13 data column");
	  		            	  List list2 = new ArrayList();
	  			            	list2.add(dto);
	  	                   		errorMap.put("error", list2);
	  	             			return errorMap;
	  		            }
	  	        	rowNumber ++;
	  	         }  
	        	  k++;
	              
	        }
	          
  			
  			// Validation for flush Out 
  			try {
  				logger.info(stbList.size()+"serialNumber   "+serialNumber+"count"+count +"validateFlag "+validateFlag);
				if(validateFlag)
				{
					int itrLen=0;

					serialNumber=serialNumber+"'"+0+"'";
					
					stbList=flushDao.getStatusList(serialNumber,flushListUploadDTO); //csv 
					
					// Calling method again for getting details 
					
					flushListUploadDTOReadDTO = flushDao.getStatusList(serialNumber,flushListUploadDTO);
					
					/* 
					Iterator<DuplicateSTBDTO> itra = flushListUploadDTO.iterator();
					
					while(itra.hasNext()){

						// To Be Added 
						DuplicateSTBDTO sampleDTO = (DuplicateSTBDTO) itra.next();
						sampleDTO.setStrRemarks("Sample Remarks");
						
					} */
					logger.info(stbList.size()+"serialNumber   "+serialNumber+"count"+count);
					Iterator<DuplicateSTBDTO> itr2 = flushListUploadDTOReadDTO.iterator();
					
					//if(stbList.size()==0 && !(serialNumber.equalsIgnoreCase("'0'"))) //Change //compare count
					/*if(stbList.size()!=count) //Change //compare count
					{
						dto = new DistRecoDto(); 
						dto.setErr_msg("Serial Number can not be flushed as required STB are not present");//complete file reject
						validateFlag =false;
							list.add(dto);

					}*/
					boolean isMismatched = false;
					int dbSTBListCount=stbList.size();
					while (itr2.hasNext()) {
						DuplicateSTBDTO objUploadDTO = (DuplicateSTBDTO) itr2.next();
						Iterator<DuplicateSTBDTO> itr1 = stbList.iterator();
						itrLen=0;
						int ii=1;
						DuplicateSTBDTO objQueryDTO=null;
						while (itr1.hasNext()) {
							 objQueryDTO = (DuplicateSTBDTO) itr1.next();
							 //logger.info("StrSerialNo:::::"+objQueryDTO.getStrSerialNo());
							 //logger.info("StrSTBStatus:::::"+objQueryDTO.getStrSTBStatus());
							 //logger.info("StrRemarks:::::"+objQueryDTO.getStrRemarks());
							 ii = objQueryDTO.compareTo(objUploadDTO);
							if(ii == 0) {
								queryListUpload.add(objQueryDTO);
				
								break;
							}
							else
							{
								++itrLen;									
							}
						}
						if(itrLen==dbSTBListCount)
						{
							isMismatched=true;
							dto = new DistRecoDto(); 
							dto.setErr_msg("Serial Number "+objUploadDTO.getStrSerialNo()+" can not be flushed as STB details are not matching.");
							validateFlag =false;
							list.add(dto);
		                 	
						}
					}
	        		/*if(queryListUpload.size()==0 && !(serialNumber.equalsIgnoreCase("'0'"))) //change
	        		{
	        			dto = new DistRecoDto(); 
	        			dto.setErr_msg("Please select atleast one Y in Action");
	        			validateFlag =false;
	        			list.add(dto);
	        		}
	        		else*/
	        	//	{
	        			returnMap.put("query",stbList);
	        			returnMap.put("upload",queryListUpload);

	        		//}

				} 
  			}
  			catch (Exception e) {
					e.printStackTrace();
					logger.error("Exception occured while validating uploaded Excel & DP data. Exception Message: " + e.getMessage());
				}
				
				// End of validation of flush Out
	          
	    }
		catch (IOException ioe) {
        	
        	//logger.info(ioe);
			
        	throw new DAOException("Please Upload a valid Excel Format File");
        }
		catch (Exception e) {
        	
        	//logger.info(e);
        	throw new DAOException("Please Upload a valid Excel Format File");
        }finally
		{
        		DBConnectionManager.releaseResources(null, rst);
        		DBConnectionManager.releaseResources(pstmtClosing ,null);
				DBConnectionManager.releaseResources(pstmtClosingchurn ,null);
				DBConnectionManager.releaseResources(pstmtClosingDef ,null);
				DBConnectionManager.releaseResources(pstmtClosingUp ,rsClosing);
            	logger.info("List size is "+list.size());
				if(list.size()!=0)
	        	{
            		returnMap.put("error", list);

     			}

        					
		}
		return returnMap;
	
	}
	
	
	public String uploadToSystem(List<DistRecoDto>  list, List<DuplicateSTBDTO> uploadList,List<DuplicateSTBDTO> queryList, String recoPeriodId, String loginUserId) throws DAOException {		
		
		
		PreparedStatement pstmt = null;
		PreparedStatement pstmt1 = null;
		PreparedStatement pstmt2 = null;
		ResultSet rst=null;
		ResultSet rstForUniversalFlag=null;
		String returnstr="";
		logger.info("Size--"+list.size());
		String olmId ="";
		String accountName="" ;
		String circleName ="";
		String tsmName="" ;
		String productName ="";
		String typeOfStock="" ;
		String serialNo ="";
		String distFlag="" ;
		
		String distRemarks ="";
		String flustOutFlag="" ;
		String dthRemarks ="";
		DistRecoDto mapDto = null;
		Connection connection = null; 
		logger.info("In Dao Impl");
		logger.info("recoPeriodId : "+recoPeriodId);
		Map<String, String> flushoutSno = new HashMap<String, String>();
		java.sql.Date toDate = null;
	try
	{	
		logger.info("calling ********* uploadToSystem ************");
		logger.info("no of records to be updated : "+list.size());
		//toDate = Utility.getSqlDateFromString(recoPeriodToDate, Constants.DT_FMT_RECO);
		//logger.info("recoPeriodToDate : "+toDate);
		//logger.info("new java.sql.Date(toDate.getTime()) : "+new java.sql.Date(toDate.getTime()));
		
		connection = DBConnectionManager.getDBConnection();
		connection.setAutoCommit(false);
		Iterator itr = list.iterator();
		int updaterows = 0;
		String sql = "";
		String sqlForUniversalFlag = "";
		String universalFlag = "";
		String flushOutFlag = null;
		String debitFlag = null;
		String flushOutMsg = null;
		
		/* Flushing out the selected serial Number */

		sqlForUniversalFlag = "select DD_VALUE from dp_configuration_details where config_id=28  and id=1 with ur";
		pstmt = connection.prepareStatement(sqlForUniversalFlag);
		rstForUniversalFlag = pstmt.executeQuery();
		if(rstForUniversalFlag.next())
		{
			universalFlag = rstForUniversalFlag.getString(1);
		}
		/* Flush out Flag will be done depending on Universal Flag */
		if(universalFlag.equalsIgnoreCase("Y"))
		{
				STBFlushOutDao dao = new STBFlushOutDaoImpl(connection);
				logger.info("size of upload list   == "+uploadList.size());
				if(uploadList.size()!=0)
				{
					logger.info("size of upload list inside if   == "+uploadList.size());	
					
							flushoutSno = dao.flushDPSerialNumbersCloseReco(uploadList,queryList ,loginUserId,recoPeriodId); //flushOutMsg
							logger.info("size of upload list inside if after flush   == "+uploadList.size());	
					
				}
				
		}		
		/* End of flushing Out */
		String flag="";
		int count_notflushed=0;
		String tempS="";
		while(itr.hasNext())
		{
			flag="";
			
			mapDto = (DistRecoDto) itr.next();
			
			if(universalFlag.equalsIgnoreCase("Y") )
			{
				if(mapDto.getFlushOutFlag() != null){
					flushOutFlag = mapDto.getFlushOutFlag().trim();
				}		
				
				if(flushoutSno.containsKey(mapDto.getSerialNo().trim()))
				{
					flag=flushoutSno.get(mapDto.getSerialNo().trim());
					if(flag!=null && flag.equalsIgnoreCase("N")) //Could not be comp
					{
						flushOutFlag = "N";
						if(mapDto.getFlushOutFlag().trim().equalsIgnoreCase("Y"))
						{
							count_notflushed=count_notflushed+1;
							tempS=tempS+""+mapDto.getSerialNo().trim()+",";
						}
					}
				}
				if(mapDto.getDebitFlag() != null)
				debitFlag = mapDto.getDebitFlag().trim();
			}
			else
			{
				flushOutFlag = "N";
				if(mapDto.getDebitFlag() != null)
				debitFlag = mapDto.getDebitFlag();
				
			} 
			
			logger.info("**getTypeOfStock********"+mapDto.getTypeOfStock());
			
			
			if(mapDto.getTypeOfStock().equalsIgnoreCase("Upgrade"))
			{
				sql = "update RECO_UPGRADE_STOCK set DEBIT_FLAG = ? , FLUSHOUT_FLAG = ? , DTH_REMARKS = ?,UPDATED_ON=current timestamp where DEFECTIVE_SR_NO = ? AND RECO_ID = ? AND FROM_DIST_ID in (SELECT LOGIN_ID FROM VR_LOGIN_MASTER WHERE LOGIN_NAME = ?) ";
			
			}
			else if(mapDto.getTypeOfStock().equalsIgnoreCase("Serialized stock"))
			{
				sql = "update RECO_SERIALIZED_STOCK set DEBIT_FLAG = ? , FLUSHOUT_FLAG = ? , DTH_REMARKS = ?,UPDATED_ON=current timestamp where SERIAL_NO = ? AND RECO_ID = ? AND DISTRIBUTOR_ID in (SELECT LOGIN_ID FROM VR_LOGIN_MASTER WHERE LOGIN_NAME = ?) ";
			}
			else if(mapDto.getTypeOfStock().equalsIgnoreCase("Churn"))
			{
				sql = "update RECO_CHURN_STOCK set DEBIT_FLAG = ? , FLUSHOUT_FLAG = ? , DTH_REMARKS = ?,UPDATED_ON=current timestamp where DEFECTIVE_SR_NO = ? AND RECO_ID = ? AND FROM_DIST_ID in (SELECT LOGIN_ID FROM VR_LOGIN_MASTER WHERE LOGIN_NAME = ?) ";
			}
			else
			{
				sql = "update RECO_DEFECTIVE_STOCK set DEBIT_FLAG = ? , FLUSHOUT_FLAG = ? , DTH_REMARKS = ?,UPDATED_ON=current timestamp where DEFECTIVE_SR_NO = ? AND RECO_ID = ? AND FROM_DIST_ID in (SELECT LOGIN_ID FROM VR_LOGIN_MASTER WHERE LOGIN_NAME = ?) ";				
			}
			
			pstmt1 = connection.prepareStatement(sql);
			if(debitFlag != null){
				pstmt1.setString(1, debitFlag.trim());
			}
			else{
				pstmt1.setString(1, "");
			}
			if(flushOutFlag != null){
				pstmt1.setString(2, flushOutFlag.trim());
			}
			else{
				pstmt1.setString(2, "");
			}		
			pstmt1.setString(3, mapDto.getDthRemarks());
			pstmt1.setString(4, mapDto.getSerialNo().trim());
			pstmt1.setString(5, recoPeriodId);
			pstmt1.setString(6, mapDto.getStrDistOLMIds().trim());
			
			logger.info("sql :"+sql);
			logger.info("calling uploadToSystem **select**getTypeOfStock********"+mapDto.getTypeOfStock());
			logger.info("calling uploadToSystem **select**getFlushOutFlag********"+mapDto.getFlushOutFlag());
			logger.info("calling uploadToSystem **select**universalFlag********"+universalFlag);
			logger.info("calling uploadToSystem **select**getDthRemarks********"+mapDto.getDthRemarks());
			logger.info("calling uploadToSystem **select**getSerialNo********"+mapDto.getSerialNo());
			logger.info("calling uploadToSystem **select**Reco Id********"+recoPeriodId);
			logger.info("calling uploadToSystem **select**getDistId********"+mapDto.getStrDistOLMIds());
			int i = pstmt1.executeUpdate();

			updaterows++;
		}
		returnstr = "Total "+updaterows+" Records have been Uploaded Successfully";
		if(count_notflushed!=0)
		{
			returnstr = "Total "+updaterows+" Records have been Uploaded Successfully Please Note "+count_notflushed +" STB Could Not be Flushed Out : "+tempS;
		}
		connection.commit();
		
		
	}
	catch (Exception e) 
	{
		try
		{
			logger.info("Inside main Exception goin to rollback");
			e.printStackTrace();
			connection.rollback();
			
			return "Internal Error Occured.Please try later";
		}
		catch(Exception ex)
		{
			logger.error(ex);
		}
	}
	finally
	{
		try {
			if (rst != null)
				rst.close();
			if (pstmt != null)
				pstmt.close();
			if (pstmt1 != null)
				pstmt1.close();
			if (pstmt2 != null)
				pstmt2.close();
			//Releasing the database connection

			connection.setAutoCommit(true);
			DBConnectionManager.releaseResources(pstmt, rst);
			DBConnectionManager.releaseResources(pstmt1, rst);
			DBConnectionManager.releaseResources(pstmt2, rst);
			DBConnectionManager.releaseResources(connection);


		} catch(Exception ex)
		{
			ex.printStackTrace();
			logger.error("***********Exception occured while uploadExcel************"+ex.getMessage());
		}

		
	}
		
		return returnstr;
	
	}	
	
	
	
	
	public  boolean validatePproductType(String str)  
	{ 
		return true;
	  /*try  
	  {  
	    double d = Double.parseDouble(str);  
	  }  
	  catch(NumberFormatException nfe)  
	  {  
	    return false;  
	  } 
	  if(str.length()==6 && str.charAt(0) != 0)
	  { 
		  //logger.info("pincode size:::::"+str.length()+"first digit of pincode:::: "+str.charAt(0));
	  return true;  
	  }
	  else
	  return false; */
	}
	public boolean validateOlmId(String str)
	{
		//logger.info("check olmid is only of eight character");
		if(str != null && str.length() != 8)
		{
			return false;
		}
		return true;
	}
	public boolean validateSerialNo(String str)
	{

		if(!ValidatorUtility.isValidNumber(str))
		{
			return false;
		}
		return true;
	}
	
	
	
	public boolean validateDistFlag(String str)
	{
		if(!ValidatorUtility.isYesNo(str))
		{
			return false;
		}
		return true;
	}
	public boolean validateDistRemarks(String str)
	{
		/*if(!ValidatorUtility.isAlphaNumericSpace(str))
		{
			return false;
		}*/
		return true;
	}
	
	public boolean validateFlushOutFlag(String str)
	{
		
		if(!ValidatorUtility.isYesNo(str))
		{
			return false;
		}
		return true;
	}
	public boolean validateDTHRemarks(String str)
	{
		
		//logger.info("validateDTHRemarks"+str);
		if(!ValidatorUtility.isAlphaNumericSpace(str)) //changed
		{
			//logger.info("validateDTHRemarks"+str);
			return false;
		}
		if(str.length() >= 250){
			return false;
		}
		return true;
	}
	
	public boolean validateOlmdistId(String distOlmId)
	{
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		boolean flag = false;
		Connection connection = null; 
		
		try{
			logger.info("inside --- validateOlmId dist OLM id == "+distOlmId);
			connection = DBConnectionManager.getDBConnection();
			pstmt = connection.prepareStatement(SQL_SELECT_DIST_CHK_EXISTS);
			pstmt.setString(1,distOlmId);
			rs =pstmt.executeQuery();
			if(rs.next())
			{
				flag =  true;
			}
			else
			{
				flag =  false;
			}
			
		}catch (Exception e) 
		{
			e.printStackTrace();
			//logger.info("Inside main Exception of validateDistOlmId method--"+e.getMessage());
		}
		finally {
			DBConnectionManager.releaseResources(pstmt ,rs);			
			DBConnectionManager.releaseResources(connection);
			}
		return flag;
	}
	
	public List getRecoPrintList(String recoID,String circleid,String selectedProductId ,int intSelectedTsmId,String hiddenDistId)
	throws DAOException {
		List recoProductListDTO = new ArrayList();
		List<PrintRecoDto> recoDetailsDist = new ArrayList<PrintRecoDto>();
		PreparedStatement pstmt = null;
		PreparedStatement pstmt1 = null;
		ResultSet rset = null;
		ResultSet rset1 = null;
		PrintRecoDto recoProductDto = null;
		Connection connection=null;
		String account_id="";
		boolean flag=false;
		java.sql.Date date=null;
		logger.info("in getRecoPrintList function of  DistRecoDaoImpl"+intSelectedTsmId);
		String date1="";
		try {
			
			connection = DBConnectionManager.getDBConnection();
			recoProductListDTO = new ArrayList<PrintRecoDto>();
			if(intSelectedTsmId==0)
			{	
			pstmt = connection.prepareStatement(SQL_SELECT_DP_RECO_CERTIFICATE_DETAIL);
			pstmt1 = connection.prepareStatement(SQL_SELECT_ALL_DIST_CIRCLE);
			pstmt1.setString(1, circleid);
			rset1 = pstmt1.executeQuery();
			
			while(rset1.next())
			{
				
				//logger.info("account_id : "+account_id);
				account_id = rset1.getString("ACCOUNT_ID");
				pstmt.setString(1, recoID);
				pstmt.setString(2, account_id);
				rset = pstmt.executeQuery();
				recoDetailsDist = null;
				recoDetailsDist = new ArrayList<PrintRecoDto>();
				while (rset.next()) {
					flag=true;
					recoProductDto = new PrintRecoDto();
					
					recoProductDto.setProductId(rset.getString("PRODUCT_ID"));
					recoProductDto.setProductName(rset.getString("PRODUCT_NAME"));
					recoProductDto.setQuantity(rset.getString("QUANTITY"));
					recoProductDto.setRefNo(com.ibm.virtualization.recharge.common.Utility.replaceNullBySpace(rset.getString("REF_NO")));
					SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
					
					date = rset.getDate("TO_DATE");
					date1="";
					if(date != null && !"".equals(date) && !"null".equalsIgnoreCase(date+""))
					{
						date1 = sdf.format(date);
					}
					
					recoProductDto.setCertDate(date1);
					recoProductDto.setDistName(rset.getString("ACCOUNT_NAME"));
					recoProductDto.setLoginName(rset.getString("LOGIN_NAME"));
					
					//logger.info("DATE ::::  " + date);
					
					recoDetailsDist.add(recoProductDto);
					recoProductDto = null;
				}
				
				if(flag==true)//Added by Neetika for fix for some distributors getting missed in Reco certificate page
				{
				recoProductListDTO.add(recoDetailsDist);
				}
				flag=false;
				DBConnectionManager.releaseResources(null, rset);
				
			}
		}
			
			
			else if(intSelectedTsmId!=0)
			{
				
				pstmt = connection.prepareStatement(SQL_SELECT_DP_RECO_CERTIFICATE_DETAIL1);

				String[] distIds = hiddenDistId.split(",");
				//logger.info(hiddenDistId);
				for (int i = 0; i < distIds.length; i++)
				{	
					//logger.info("dist id"+distIds[i].trim());
					pstmt.setString(1, recoID);
					pstmt.setInt(2, Integer.parseInt(distIds[i].trim()));
					
					
					rset = pstmt.executeQuery();
					recoDetailsDist = null;
					recoDetailsDist = new ArrayList<PrintRecoDto>();
					while (rset.next()) {
						flag=true;
						recoProductDto = new PrintRecoDto();
						
						recoProductDto.setProductId(rset.getString("PRODUCT_ID"));
						recoProductDto.setProductName(rset.getString("PRODUCT_NAME"));
						recoProductDto.setQuantity(rset.getString("QUANTITY"));
						recoProductDto.setRefNo(com.ibm.virtualization.recharge.common.Utility.replaceNullBySpace(rset.getString("REF_NO")));
						SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
						
						date = rset.getDate("TO_DATE");
						date1="";
						if(date != null && !"".equals(date) && !"null".equalsIgnoreCase(date+""))
						{
							date1 = sdf.format(date);
						}
						
						recoProductDto.setCertDate(date1);
						recoProductDto.setDistName(rset.getString("ACCOUNT_NAME"));
						recoProductDto.setLoginName(rset.getString("LOGIN_NAME"));
						
						//logger.info("DATE ::::  " + date);
						
						recoDetailsDist.add(recoProductDto);
						
						recoProductDto = null;
					
				}//for ends
					//logger.info("DATE :::::::::  " + date);
					if(flag==true)//Added by Neetika for fix for some distributors getting missed in Reco certificate page
					{
					recoProductListDTO.add(recoDetailsDist);
					}
					flag=false;
				}
				
				DBConnectionManager.releaseResources(null, rset);
				//recoProductListDTO.add(recoDetailsDist);
			}
		} catch (Exception e) {
			logger.info(e);
			//e.printStackTrace();
			throw new DAOException(e.getMessage());
		} finally {
			DBConnectionManager.releaseResources(pstmt, rset);
			DBConnectionManager.releaseResources(pstmt1, rset1);
			DBConnectionManager.releaseResources(connection);
		}
		return recoProductListDTO;
		}
	public List<SelectionCollection> getCircleListDAO(long accountID) throws com.ibm.dp.exception.DAOException 
	{
		List<SelectionCollection> listReturn = new ArrayList<SelectionCollection>();
		PreparedStatement pstmt = null;
		ResultSet rset = null;
		Connection connection = null;
		try
		{
			connection = DBConnectionManager.getDBConnection();
			pstmt = connection.prepareStatement("select a.CIRCLE_ID, a.CIRCLE_NAME from VR_CIRCLE_MASTER a inner join DP_ACCOUNT_CIRCLE_MAP b on a.CIRCLE_ID=b.CIRCLE_ID and b.ACCOUNT_ID=? ORDER BY CIRCLE_NAME with ur");
			pstmt.setLong(1, accountID);
			rset = pstmt.executeQuery();
			SelectionCollection selCol = null;
			while(rset.next())
			{
				selCol = new SelectionCollection();
				selCol.setStrValue(rset.getString(1));
				selCol.setStrText(rset.getString(2));
				
				listReturn.add(selCol);
				selCol = null;
			}
		}
		catch (Exception e) 
		{
			e.printStackTrace();
			logger.info("Exception while getting circle list for reco report  ::  "+e);
			logger.info("Exception while getting circle list for reco report  ::  "+e.getMessage());
			throw new com.ibm.dp.exception.DAOException(e.getMessage());
		}
		finally
		{
			DBConnectionManager.releaseResources(connection);
			DBConnectionManager.releaseResources(pstmt, rset);
		}
		return listReturn;
	}
	
	//changes by aman
	
	
	public List<RecoPeriodConfFormBean> getTsmList(int circleId) throws com.ibm.dp.exception.DAOException 
	{
		List<RecoPeriodConfFormBean> listReturn = new ArrayList<RecoPeriodConfFormBean>();
		PreparedStatement pstmt = null;
		ResultSet rset = null;
		Connection connection = null;
		try
		{

			connection = DBConnectionManager.getDBConnection();
			pstmt = connection.prepareStatement("SELECT ad.ACCOUNT_NAME,lm.LOGIN_ID FROM VR_ACCOUNT_DETAILS ad, " +
					"VR_LOGIN_MASTER lm,DP_ACCOUNT_CIRCLE_MAP map WHERE ad.ACCOUNT_LEVEL=5  and ad.ACCOUNT_ID=lm.LOGIN_ID and map.account_id=lm.LOGIN_ID and map.CIRCLE_ID=?  " +
					"and TRANSACTION_TYPE =2 with ur"); //query coorected by neetika
			pstmt.setInt(1, circleId);
			rset = pstmt.executeQuery();
			RecoPeriodConfFormBean recoPeriodConfFormBean = null;
			while(rset.next())
			{
				recoPeriodConfFormBean = new RecoPeriodConfFormBean();
				recoPeriodConfFormBean.setTsmName(rset.getString("ACCOUNT_NAME"));
				recoPeriodConfFormBean.setTsmAccountId(rset.getString("LOGIN_ID"));

				listReturn.add(recoPeriodConfFormBean);
				recoPeriodConfFormBean = null;
			}
		}
		catch (Exception e) 
		{
			e.printStackTrace();
			logger.info("Exception while getting circle list for reco report  ::  "+e);
			logger.info("Exception while getting circle list for reco report  ::  "+e.getMessage());
			throw new com.ibm.dp.exception.DAOException(e.getMessage());
		}
		finally
		{
			DBConnectionManager.releaseResources(connection);
			DBConnectionManager.releaseResources(pstmt, rset);
		}
		return listReturn;
	}
	
	
	public List<RecoPeriodConfFormBean> getDistList(int tsmId) throws com.ibm.dp.exception.DAOException 
	{
		List<RecoPeriodConfFormBean> listReturn = new ArrayList<RecoPeriodConfFormBean>();
		PreparedStatement pstmt = null;
		ResultSet rset = null;
		Connection connection = null;
		try
		{
			connection = DBConnectionManager.getDBConnection();
			pstmt = connection.prepareStatement("SELECT DISTRIBUTOR_NAME,DISTRIBUTOR_ID FROM DP_DIST_DETAILS WHERE TSM_ID in (?) with ur");
			pstmt.setInt(1, tsmId);
			rset = pstmt.executeQuery();
			RecoPeriodConfFormBean recoPeriodConfFormBean = null;
			while(rset.next())
			{
				recoPeriodConfFormBean = new RecoPeriodConfFormBean();
				recoPeriodConfFormBean.setDistName(rset.getString("DISTRIBUTOR_NAME"));
				recoPeriodConfFormBean.setDistAccId(rset.getString("DISTRIBUTOR_ID"));
				
				listReturn.add(recoPeriodConfFormBean);
				recoPeriodConfFormBean = null;
			}
		}
		catch (Exception e) 
		{
			e.printStackTrace();
			logger.info("Exception while getting circle list for reco report  ::  "+e);
			logger.info("Exception while getting circle list for reco report  ::  "+e.getMessage());
			throw new com.ibm.dp.exception.DAOException(e.getMessage());
		}
		finally
		{
			DBConnectionManager.releaseResources(connection);
			DBConnectionManager.releaseResources(pstmt, rset);
		}
		return listReturn;
	}
	//end of changes by aman
	
	/* Added By Parnika For validation */
	
	public String validateFlags(DistRecoDto mapDto)
	{
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		boolean flag = false;
		Connection connection = null; 
		String distFlag = "";
		String flushOutFlag = "";
		String debitFlag = "";
		String message = "";
		
		StringBuffer sqlSelectDistFlag = new StringBuffer("SELECT DIST_FLAG, FLUSHOUT_FLAG, DEBIT_FLAG FROM ");
		
		try{
			logger.info("inside --- validateFlags == ");
			connection = DBConnectionManager.getDBConnection();
			// To check  Dist Flag , Debit Flag and Flush out Flag 
			
			if(mapDto.getTypeOfStock().equalsIgnoreCase("Upgrade"))
			{
				sqlSelectDistFlag.append(" RECO_UPGRADE_STOCK WHERE RECO_ID = ? AND DEFECTIVE_SR_NO = ? AND FROM_DIST_ID  in (SELECT LOGIN_ID FROM VR_LOGIN_MASTER WHERE LOGIN_NAME = ?) ");							
			}
			else if(mapDto.getTypeOfStock().equalsIgnoreCase("Serialized stock"))
			{
				sqlSelectDistFlag.append(" RECO_SERIALIZED_STOCK WHERE RECO_ID = ? AND SERIAL_NO = ? AND DISTRIBUTOR_ID in (SELECT LOGIN_ID FROM VR_LOGIN_MASTER WHERE LOGIN_NAME = ?)");
			}
			else if(mapDto.getTypeOfStock().equalsIgnoreCase("Churn"))
			{
				sqlSelectDistFlag.append(" RECO_CHURN_STOCK  WHERE RECO_ID = ? AND DEFECTIVE_SR_NO = ? AND FROM_DIST_ID in (SELECT LOGIN_ID FROM VR_LOGIN_MASTER WHERE LOGIN_NAME = ?)");
			}
			else
			{
				sqlSelectDistFlag.append(" RECO_DEFECTIVE_STOCK  WHERE RECO_ID = ? AND DEFECTIVE_SR_NO = ? AND FROM_DIST_ID in (SELECT LOGIN_ID FROM VR_LOGIN_MASTER WHERE LOGIN_NAME = ?)");
			}
			
			sqlSelectDistFlag.append(" WITH UR");
			
			
			pstmt = connection.prepareStatement(sqlSelectDistFlag.toString());
			pstmt.setString(1,mapDto.getRecoPeriodId().trim());
			pstmt.setString(2,mapDto.getSerialNo().trim());
			pstmt.setString(3,mapDto.getStrDistOLMIds().trim());
			rs =pstmt.executeQuery();
			System.out.println(sqlSelectDistFlag+"distFlag"+distFlag+"reco id "+mapDto.getRecoPeriodId().trim()+"mapDto.getSerialNo().trim()"+mapDto.getSerialNo().trim());
			if(rs.next())
			{
				
				distFlag = rs.getString("DIST_FLAG");	
				//System.out.println(sqlSelectDistFlag+"distFlag"+distFlag);
				flushOutFlag = rs.getString("FLUSHOUT_FLAG");
				debitFlag = rs.getString("DEBIT_FLAG");
				flag =  true;
			}
			else
			{
				flag =  false;
				message = "Not Updated As Serial Number Not Found for selected Reco";
				return message;
			}
			logger.info("distFlag===="+distFlag+" mapDto.getDistFlag().trim() "+mapDto.getDistFlag().trim()+"flushOutFlag"+flushOutFlag+" mapDto.getFlushOutFlag().trim()"+ mapDto.getFlushOutFlag().trim());
			// If Dist Flag is Null , Record is rejected		
			if (distFlag == null || distFlag.trim().equals("")){
				
				message = "Not Updated As distributor's action is pending";
			}
			
			// If Dist Flag is Y , Record is rejected if Flushout Flag is received Y
			else if (distFlag != null  && distFlag.trim().equals("Y") && mapDto.getFlushOutFlag().trim().equalsIgnoreCase("Y") && !(flushOutFlag.equals("Y"))){				
				message = "Not Updated As dist Flag is 'Y' so Flushout Flag should be 'N' ";
			}
			
			// If Dist Flag is N , Record is rejected if Flush out Flag is Already Y , and Another Flush Out Flag is received As N.
			/*else if (distFlag != null  && flushOutFlag != null && flushOutFlag.trim().equals("Y")){				
				message = "Not Updated As it is Already flushed Out ";
			}*/
			else if (distFlag != null  && debitFlag != null && (debitFlag.trim().equals("Y") || debitFlag.trim().equals("N"))){				
				message = "Not Updated As it DTHADMIN has already taken action of Debit  ";
			}
			// If Dist Flag received and Dist Flag in File are different.
			else if(distFlag != null  && !(distFlag.trim().equalsIgnoreCase(mapDto.getDistFlag().trim()))){
				
				message = "Not Updated As dist Flag is not correct ";
			}
			else if(distFlag != null && flushOutFlag!=null && flushOutFlag.trim().equals("Y") && mapDto.getFlushOutFlag().trim().equalsIgnoreCase("N")){
				
				message = "Not Updated As STB is already flushed out from system, please mark flushout as Y "; //Neetika
			}
			//System.out.println("2004"+message);
		}catch (Exception e) 
		{
			e.printStackTrace();
			//logger.info("Inside main Exception of validateDistOlmId method--"+e.getMessage());
		}
		finally {
			DBConnectionManager.releaseResources(pstmt ,rs);			
			DBConnectionManager.releaseResources(connection);
			}
		return message;
	}
	
	public List<RecoPeriodDTO> getRecoHistoryNotClosed() throws DAOException{
		PreparedStatement ps = null;
		Connection db2conn = null; 
		ResultSet rs = null; 
		List<RecoPeriodDTO> recoList= new ArrayList<RecoPeriodDTO>();
		
		try {
			db2conn = DBConnectionManager.getDBConnection();
						
			String sql = "SELECT  * FROM DP_RECO_PERIOD where TO_DATE<current date  and IS_RECO_CLOSED != 1 ORDER BY ID DESC with ur";
	
			ps = db2conn.prepareStatement(sql);
			logger.info(sql);
			
			rs = ps.executeQuery();
			
			
			while (rs.next()) {
				RecoPeriodDTO recDTO = new RecoPeriodDTO();
				String fromDate = Utility.converDateToString(rs.getDate("FROM_DATE"), Constants.DT_FMT_RECO);
				String toDate = Utility.converDateToString(rs.getDate("TO_DATE"), Constants.DT_FMT_RECO);
				Integer recoID = rs.getInt("ID");
				recDTO.setFromDate(fromDate);
				recDTO.setToDate(toDate);
				recDTO.setRecoPeriodId(recoID.toString());
				recoList.add(recDTO);
			}
			
		} catch (Exception e) {
				e.printStackTrace();
				throw new DAOException("Exception: " + e.getMessage());
				}
		finally {
			DBConnectionManager.releaseResources(ps ,rs);			
			DBConnectionManager.releaseResources(db2conn);
			
			}
		return recoList;
		
	}
	
	/* End of changes By Parnika */
	
	
}


