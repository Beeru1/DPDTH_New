package com.ibm.dp.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

import com.ibm.core.exception.DAOException;
import com.ibm.dp.beans.RecoPeriodConfFormBean;
import com.ibm.dp.common.Constants;
import com.ibm.dp.common.DBQueries;
import com.ibm.dp.dao.RecoDetailReportDao;
import com.ibm.dp.dto.PrintRecoDto;
import com.ibm.dp.dto.RecoDetailReportDTO;
import com.ibm.dp.dto.RecoPeriodDTO;
import com.ibm.dpmisreports.common.DropDownUtility;
import com.ibm.dpmisreports.common.SelectionCollection;
import com.ibm.utilities.Utility;
import com.ibm.virtualization.recharge.db.DBConnectionManager;

 
public class RecoDetailReportDaoImpl implements  RecoDetailReportDao{
	
	private static Logger logger = Logger.getLogger(RecoDetailReportDaoImpl.class);	
	public static final String SQL_SELECT_DP_RECO_CERTIFICATE_DETAIL = DBQueries.SQL_SELECT_DP_RECO_CERTIFICATE_DETAIL_RECO;
	public static final String SQL_SELECT_DP_RECO_CERTIFICATE_DETAIL1 = DBQueries.SQL_SELECT_DP_RECO_CERTIFICATE_DETAIL_RECO1;
	public static final String SQL_SELECT_ALL_DIST_CIRCLE="select ACCOUNT_ID from VR_ACCOUNT_DETAILS where CIRCLE_ID=? and ACCOUNT_LEVEL=6 order by ACCOUNT_NAME with ur";
	
	private static RecoDetailReportDao recoDetailReportDao = new RecoDetailReportDaoImpl();
	private RecoDetailReportDaoImpl(){}
	public static RecoDetailReportDao getInstance() {
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
			" RD.DEF_CLOSING_STOCK,UPGRADE_CLOSING_STOCK,CHURN_CLOSING_STOCK,PENDING_PO_CLOSING,PENDING_DC_CLOSING,RECO_STATUS,REMARKS,INVENTORY_CHNG_OUT from DP_RECO_DIST_DETAILS RD , VR_LOGIN_MASTER LM" +
			" where RECO_ID=?  and DIST_ID!=29035 and LM.LOGIN_ID = RD.DIST_ID and LM.STATUS != 'C' " ; //on request from business sandeep bhatnagar split merge ID removed from report

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
			
			sql+= " ORDER BY DIST_ID,PRODUCT_ID, CREATED_BY with ur";//with ur on 23 june 
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
			int dginventorychangeout = 0;
			int sginventorychangeout = 0;
			String remarks="";
			
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
				
				//for opening in dp enhancement by rishab
				int opening =0;
				opening =
					rs.getInt("PENDING_PO_OPENING")
					+rs.getInt("PENDING_DC_OPENING")
						+ rs.getInt("SER_OPEN_STOCK")
						//+ rs.getInt("SER_OPEN_STOCK")
						+ rs.getInt("NSER_OPEN_STOCK")
						+ rs.getInt("DEFECTIVE_OPEN_STOCK")
						+ rs.getInt("UPGRADE_OPEN_STOCK")
						+ rs.getInt("CHURN_OPEN_STOCK");
				recDTO.setOpening(opening);
				
				
				recDTO.setReceivedWH(rs.getInt("RECEIVED_WH"));
				recDTO.setReceivedInterSSDOk(rs.getInt("RecInterSSDOK"));
				recDTO.setReceivedInterSSDDef(rs.getInt("RECEIVED_INTER_SSD_DEF"));
				recDTO.setReceivedUpgrade(rs.getInt("RECEIVED_UPGRADE"));
				recDTO.setReceivedChurn(rs.getInt("RECEIVED_CHURN"));
			
				//for opening in dp enhancement by rishab
				int dispatch = rs.getInt("RECEIVED_WH")
						+ rs.getInt("PENDING_PO_CLOSING")
						- rs.getInt("PENDING_PO_OPENING");
				recDTO.setDispatch(dispatch);
				
				
				recDTO.setReturnedFresh(rs.getInt("RETURNED_FRESH"));
				recDTO.setReturnedInterSSDOk(rs.getInt("RetInterSSDOK"));
				recDTO.setReturnedInterSSDDef(rs.getInt("RETURNED_INTER_SSD_DEF"));
				recDTO.setReturnedDOABI(rs.getInt("RETURNED_DOA_BI"));
				recDTO.setReturnedDOAAI(rs.getInt("RETURNED_DOA_AI"));
				recDTO.setReturnedSwap(rs.getInt("RETURNED_SWAP"));
				recDTO.setReturnedC2C(rs.getInt("RETURNED_C2S"));
				recDTO.setReturnedChurn(rs.getInt("RETURNED_CHURN"));
			
				//for opening in dp enhancement by rishab
				int returnVal =0;
				returnVal =rs.getInt("RETURNED_FRESH")+
				rs.getInt("RetInterSSDOK")+
					rs.getInt("RETURNED_DOA_BI")
				+ rs.getInt("RETURNED_DOA_AI")
				+ rs.getInt("RETURNED_SWAP")
				+ rs.getInt("RETURNED_C2S")
				+ rs.getInt("RETURNED_CHURN");
				
				recDTO.setReturnVal(returnVal);
		
				//for opening in dp enhancement by rishab
				int ipd =0;
				ipd = rs.getInt("RecInterSSDOK")
				+ rs.getInt("RECEIVED_INTER_SSD_DEF");
				
				recDTO.setDistPdis(ipd);
				
				//for ipr in dp enhancement by rishab
				int distPrec =0;
				distPrec = rs.getInt("RetInterSSDOK")
				+ rs.getInt("RETURNED_INTER_SSD_DEF");
				
				recDTO.setDistPrec(distPrec);
				
				recDTO.setTotalActivation(rs.getInt("ACTIVATION"));
				recDTO.setSerClosing(rs.getInt("SER_CLOSING_STOCK"));
				recDTO.setNsrClosing(rs.getInt("NSER_CLOSING_STOCK"));
				recDTO.setUpgardeClosing(rs.getInt("UPGRADE_CLOSING_STOCK"));
				recDTO.setChurnClosing(rs.getInt("CHURN_CLOSING_STOCK"));
				recDTO.setDefClosing(rs.getInt("DEF_CLOSING_STOCK"));
				recDTO.setPenPOClosing(rs.getInt("PENDING_PO_CLOSING"));
				recDTO.setPenDcClosing(rs.getInt("PENDING_DC_CLOSING"));
				recDTO.setAdjustment(rs.getInt("ADJUSTMENT"));
				recDTO.setRemarks(rs.getString("REMARKS"));
				recDTO.setInventoryChange(rs.getInt("INVENTORY_CHANGE"));
		
				//for derived closing in dp enhancement by rishab
				int derivedClosing = 0;
				derivedClosing = recDTO.getOpening() + recDTO.getDispatch()
						- recDTO.getReturnVal() - recDTO.getDistPdis()+recDTO.getDistPrec()
						+ recDTO.getReceivedChurn() + recDTO.getReceivedUpgrade()
						- recDTO.getAdjustment()
						- recDTO.getTotalActivation();
				recDTO.setDerivedClosing(derivedClosing);
				
				//for system calculated dp enhancement by rishab		
				int systemCL = 0;
				systemCL = recDTO.getSerClosing()+recDTO.getNsrClosing()+recDTO.getUpgardeClosing()+recDTO.getChurnClosing()+recDTO.getDefClosing()
				+recDTO.getPenDcClosing();
				recDTO.setSystemCL(systemCL);
			
				//for system calculated variance dp enhancement by rishab
				int variance=0;
				variance=systemCL-derivedClosing;
		
				recDTO.setVariance(variance);
				
				recDTO.setInverntoryChangeout(rs.getInt("INVENTORY_CHNG_OUT"));
				
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
					
					dginventorychangeout=recDTO.getInverntoryChangeout();
					
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
					recVarianceDTO.setInverntoryChangeout(dginventorychangeout-sginventorychangeout);
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
					sginventorychangeout=recDTO.getInverntoryChangeout();
					
					
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
						
			String sql = "SELECT  * FROM DP_RECO_PERIOD where TO_DATE<current date  ORDER BY ID DESC";
	
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
		String SQL_SELECT_DP_RECO_CERTIFICATE_DETAIL99="Select distinct(ACCOUNT_NAME),LOGIN_NAME,RD.CERTIFICATE_ID,RH.REF_NO,BBB.PRODUCT_ID , RD.QUANTITY,PM.PRODUCT_NAME,RECO.TO_DATE,PM.PRODUCT_TYPE , (select circle_name from VR_CIRCLE_MASTER vc,VR_ACCOUNT_DETAILS vd where vc.CIRCLE_ID=vd.CIRCLE_ID and vd.ACCOUNT_ID=?)" +
				" from ( SELECT * FROM VR_ACCOUNT_DETAILS ACC inner join VR_LOGIN_MASTER log on ACC.ACCOUNT_ID=log.LOGIN_ID inner join    (select * from DP_RECO_DIST_DETAILS where dist_id=? and reco_id ="+recoID+" and CREATED_BY!=0 ) DD   on ACC.ACCOUNT_ID=DD.DIST_ID )  BBB left outer join DP_RECO_CERTIFICATE_HDR RH on RH.DIST_ID=BBB.DIST_ID left outer join DP_RECO_PERIOD RECO on RH.RECO_ID =RECO.ID AND BBB.RECO_ID=RECO.ID left outer join DP_PRODUCT_MASTER PM on PM.PRODUCT_ID=BBB.PRODUCT_ID left outer join DP_RECO_CERTIFICATE_DETAIL RD on RH.CERTIFICATE_ID =RD.CERTIFICATE_ID  AND BBB.PRODUCT_ID=RD.PRODUCT_ID where BBB.ACCOUNT_ID=?  AND RH.RECO_ID ="+recoID+" order by PM.PRODUCT_TYPE desc,PM.PRODUCT_NAME desc with ur";
	//	String SQL_SELECT_DP_RECO_CERTIFICATE_DETAIL98="Select distinct(ACCOUNT_NAME),LOGIN_NAME,RD.CERTIFICATE_ID,RH.REF_NO,BBB.PRODUCT_ID , RD.QUANTITY,PM.PRODUCT_NAME,RECO.TO_DATE,PM.PRODUCT_TYPE from ( SELECT * FROM VR_ACCOUNT_DETAILS ACC inner join VR_LOGIN_MASTER log on ACC.ACCOUNT_ID=log.LOGIN_ID inner join    (select * from DP_RECO_DIST_DETAILS where  reco_id ="+recoID+" and CREATED_BY!=0 ) DD   on ACC.ACCOUNT_ID=DD.DIST_ID )  BBB left outer join DP_RECO_CERTIFICATE_HDR RH on RH.DIST_ID=BBB.DIST_ID left outer join DP_RECO_PERIOD RECO on RH.RECO_ID =RECO.ID AND BBB.RECO_ID=RECO.ID left outer join DP_PRODUCT_MASTER PM on PM.PRODUCT_ID=BBB.PRODUCT_ID left outer join DP_RECO_CERTIFICATE_DETAIL RD on RH.CERTIFICATE_ID =RD.CERTIFICATE_ID  AND BBB.PRODUCT_ID=RD.PRODUCT_ID where  RH.RECO_ID ="+recoID+" order by PM.PRODUCT_TYPE,PM.PRODUCT_NAME with ur";
		try {
			
			connection = DBConnectionManager.getDBConnection();
			recoProductListDTO = new ArrayList<PrintRecoDto>();
			if(intSelectedTsmId==0)
			{	
			//pstmt = connection.prepareStatement(SQL_SELECT_DP_RECO_CERTIFICATE_DETAIL);//asa
			pstmt = connection.prepareStatement(SQL_SELECT_DP_RECO_CERTIFICATE_DETAIL99);
			pstmt1 = connection.prepareStatement(SQL_SELECT_ALL_DIST_CIRCLE);
			pstmt1.setString(1, circleid);
			rset1 = pstmt1.executeQuery();
			while(rset1.next())
			{
				
				//logger.info("account_id : "+account_id);
				account_id = rset1.getString("ACCOUNT_ID");
				//pstmt.setString(1, recoID);
				//pstmt.setString(2, account_id);
				pstmt.setInt(1, Integer.parseInt(account_id));
				pstmt.setInt(2, Integer.parseInt(account_id));
				pstmt.setInt(3, Integer.parseInt(account_id));
				rset = pstmt.executeQuery();
				recoDetailsDist = null;
				recoDetailsDist = new ArrayList<PrintRecoDto>();
				while (rset.next()) {
				
					recoProductDto = new PrintRecoDto();
					
					recoProductDto.setProductId(rset.getString("PRODUCT_ID"));
					recoProductDto.setProductName(rset.getString("PRODUCT_NAME"));
					if(rset.getString("QUANTITY")!=null && ! ("".equalsIgnoreCase(rset.getString("QUANTITY"))))
					{
						flag=true;
						recoProductDto.setQuantity(rset.getString("QUANTITY"));
					}
					else
					{
						recoProductDto.setQuantity("Not Submitted");
					}
					
					recoProductDto.setRefNo(com.ibm.virtualization.recharge.common.Utility.replaceNullBySpace(rset.getString("REF_NO")));
					recoProductDto.setCircleName(rset.getString("CIRCLE_NAME"));
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
				int distId=-1;
				
				
				//pstmt = connection.prepareStatement(SQL_SELECT_DP_RECO_CERTIFICATE_DETAIL1);//asa
				pstmt = connection.prepareStatement(SQL_SELECT_DP_RECO_CERTIFICATE_DETAIL99);
				

				String[] distIds = hiddenDistId.split(",");
				//logger.info(hiddenDistId);
				for (int i = 0; i < distIds.length; i++)
				{	
					distId=Integer.parseInt(distIds[i].trim());
					//logger.info("dist id"+distIds[i].trim());
					/*pstmt.setString(1, recoID);
					pstmt.setInt(2, Integer.parseInt(distIds[i].trim()));
					pstmt.setString(3, recoID);
					pstmt.setInt(4, Integer.parseInt(distIds[i].trim()));*/
					pstmt.setInt(1, Integer.parseInt(distIds[i].trim()));
					pstmt.setInt(2, Integer.parseInt(distIds[i].trim()));
					pstmt.setInt(3, Integer.parseInt(distIds[i].trim()));

					rset = pstmt.executeQuery();
					recoDetailsDist = null;
					recoDetailsDist = new ArrayList<PrintRecoDto>();
					while (rset.next()) {
						//flag=true;
						recoProductDto = new PrintRecoDto();
						
						recoProductDto.setProductId(rset.getString("PRODUCT_ID"));
						recoProductDto.setProductName(rset.getString("PRODUCT_NAME"));
						if(rset.getString("QUANTITY")!=null && ! ("".equalsIgnoreCase(rset.getString("QUANTITY"))))
						{
							flag=true;
							recoProductDto.setQuantity(rset.getString("QUANTITY"));
						}
						else
						{
							recoProductDto.setQuantity("Not Submitted");
						}
						recoProductDto.setRefNo(com.ibm.virtualization.recharge.common.Utility.replaceNullBySpace(rset.getString("REF_NO")));
						recoProductDto.setCircleName(rset.getString("CIRCLE_NAME"));
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
	
	
}


