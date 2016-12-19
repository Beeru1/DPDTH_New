package com.ibm.dp.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import org.apache.log4j.Logger;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.struts.upload.FormFile;

import com.ibm.dp.common.Constants;
import com.ibm.dp.common.DBQueries;
import com.ibm.dp.dao.DistRecoDao;
import com.ibm.dp.dto.DistRecoDto;
import com.ibm.dp.dto.PrintRecoDto;
import com.ibm.dp.dto.RecoPeriodDTO;
import com.ibm.dp.exception.DPServiceException;
import com.ibm.dp.service.impl.DistRecoServiceImpl;
import com.ibm.utilities.FileHelpers;
import com.ibm.utilities.Utility;
import com.ibm.virtualization.recharge.common.ResourceReader;
import com.ibm.virtualization.recharge.dao.rdbms.BaseDaoRdbms;
import com.ibm.virtualization.recharge.db.DBConnectionManager;
import com.ibm.virtualization.recharge.exception.DAOException;

public class DistRecoDaoImpl extends BaseDaoRdbms implements DistRecoDao {
	public static Logger logger = Logger.getLogger(DistRecoDaoImpl.class
			.getName());

	public DistRecoDaoImpl(Connection connection) {
		super(connection);
	}


	public static final String SQL_RECO_PERIOD_LIST = DBQueries.SQL_RECO_PERIOD_LIST;
	
	public static final String SQL_RECO_PERIOD_LIST_ADMIN = DBQueries.SQL_RECO_PERIOD_LIST_ADMIN;

	public static final String SQL_UPDATE_RECO_DETAIL = DBQueries.SQL_UPDATE_RECO_DETAIL;

	public static final String SQL_UPDATE_RECO_STATUS = DBQueries.SQL_UPDATE_RECO_STATUS;

	public static final String SQL_RECO_COMPLETE_STATUS = DBQueries.SQL_RECO_COMPLETE_STATUS;

	public static final String SQL_GET_CERTIFICATE_ID = DBQueries.SQL_GET_CERTIFICATE_ID;
	
	public static final String SQL_DELETE_FROM_TEMP = DBQueries.SQL_DELETE_FROM_TEMP;

	public static final String SQL_SELECT_FROM_TEMP = DBQueries.SQL_SELECT_FROM_TEMP;

	public static final String SQL_INSERT_DP_RECO_CERTIFICATE_DETAIL = DBQueries.SQL_INSERT_DP_RECO_CERTIFICATE_DETAIL;

	public static final String SQL_SELECT_DP_RECO_CERTIFICATE_DETAIL = DBQueries.SQL_SELECT_DP_RECO_CERTIFICATE_DETAIL;
	public static final String SQL_SELECT_DP_RECO_Go_LIVE_DATE = DBQueries.SQL_SELECT_DP_RECO_Go_LIVE_DATE;
	

	// Added by shilpa on 2-5-12 for UAT Observation
	public static final String SQL_RECOS_COMPLETE_STATUS = DBQueries.SQL_RECOS_COMPLETE_STATUS;

	
	public List<RecoPeriodDTO> getRecoPeriodList(String login_id) throws DAOException{
		List<RecoPeriodDTO> recoPeriodListDTO	= new ArrayList<RecoPeriodDTO>();

		PreparedStatement pstmt = null;
		ResultSet rset = null;
		RecoPeriodDTO recoPeriodDto = null;

		try {
			logger.info("login_id:::::::::::"+login_id);
			pstmt = connection.prepareStatement(SQL_RECO_PERIOD_LIST);
			pstmt.setString(1,login_id );
			rset = pstmt.executeQuery();
			recoPeriodListDTO = new ArrayList<RecoPeriodDTO>();
			String toDate = "";
			String fromDate = "";
			while (rset.next()) {
				recoPeriodDto = new RecoPeriodDTO();
				recoPeriodDto.setRecoPeriodId(rset.getString("ID"));
				fromDate = Utility.getDateAsString(rset.getDate("FROM_DATE"),
						"dd-MMM-yyyy");
				toDate = Utility.getDateAsString(rset.getDate("TO_DATE"),
						"dd-MMM-yyyy");
				recoPeriodDto.setRecoPeriodName(fromDate + " to " + toDate);

				recoPeriodListDTO.add(recoPeriodDto);
			}
		} catch (Exception e) {
			e.printStackTrace();
			throw new DAOException(e.getMessage());
		} finally {
			DBConnectionManager.releaseResources(pstmt, rset);
		}
		return recoPeriodListDTO;
	}
	public List<RecoPeriodDTO> getRecoPeriodListAdmin() throws DAOException{
		List<RecoPeriodDTO> recoPeriodListDTO	= new ArrayList<RecoPeriodDTO>();

		PreparedStatement pstmt = null;
		ResultSet rset = null;
		RecoPeriodDTO recoPeriodDto = null;

		try {
			pstmt = connection.prepareStatement(SQL_RECO_PERIOD_LIST_ADMIN);
			rset = pstmt.executeQuery();
			recoPeriodListDTO = new ArrayList<RecoPeriodDTO>();
			String toDate = "";
			String fromDate = "";
			while (rset.next()) {
				recoPeriodDto = new RecoPeriodDTO();
				recoPeriodDto.setRecoPeriodId(rset.getString("ID"));
				fromDate = Utility.getDateAsString(rset.getDate("FROM_DATE"),
						"dd-MMM-yyyy");
				toDate = Utility.getDateAsString(rset.getDate("TO_DATE"),
						"dd-MMM-yyyy");
				recoPeriodDto.setRecoPeriodName(fromDate + " to " + toDate);

				recoPeriodListDTO.add(recoPeriodDto);
			}
		} catch (Exception e) {
			e.printStackTrace();
			throw new DAOException(e.getMessage());
		} finally {
			DBConnectionManager.releaseResources(pstmt, rset);
		}
		return recoPeriodListDTO;
	}
	
	public List<DistRecoDto> getRecoProductList(String stbType,
			String recoPeriod, String distId,boolean result ,String stockType, String productId) throws DAOException {
		List<DistRecoDto> recoProductListDTO = new ArrayList<DistRecoDto>();
		PreparedStatement pstmt = null;
		PreparedStatement pstmt2 = null;
		PreparedStatement pstmt3 = null;
		ResultSet rset = null;
		ResultSet rst = null;
		DistRecoDto recoProductDto = null;
		Integer completeCount = -1;
		logger.info("in getRecoProductList function of  DistRecoDaoImpl:"+productId);
		try {
			// Added by shilpa on 2-5-12 for UAT Observation
			// Checking, all entries for previous reco have been done or not
			pstmt2 = connection.prepareStatement(SQL_RECOS_COMPLETE_STATUS);
			pstmt2.setString(1, distId);
			pstmt2.setString(2, recoPeriod);
			rst = pstmt2.executeQuery();
			recoProductListDTO = new ArrayList<DistRecoDto>();
			if (rst.next()) {
				completeCount = rst.getInt(1);
				logger.info("completeCount == " + completeCount);
			}
			// logger.info("distId:::"+distId);
			// logger.info("recoPeriod:::"+recoPeriod);

			/*
			 * if(completeCount != 0) { recoProductDto = new DistRecoDto();
			 * //IsValidToEnter =0, means can not enter details
			 * recoProductDto.setIsValidToEnter("0");
			 * recoProductListDTO.add(recoProductDto);
			 * //DBConnectionManager.releaseResources(pstmt, rset);
			 * //DBConnectionManager.releaseResources(pstmt2, rst); //return
			 * recoProductListDTO; }
			 */
			// End here
			String certId = "-1";
			int count = -1;
			pstmt3 = connection.prepareStatement(SQL_GET_CERTIFICATE_ID);
			pstmt3.setString(1, recoPeriod);
			pstmt3.setString(2, distId);
			rst = pstmt3.executeQuery();
			if (rst.next()) {
				certId = rst.getString("CERTIFICATE_ID").trim();
				logger.info("Certificate Id in Dao Impl == " + certId);
			}
			logger.info("Dist Id == " + distId);

			pstmt3.clearParameters();

			pstmt3 = connection.prepareStatement(SQL_RECO_COMPLETE_STATUS);
			pstmt3.setString(1, distId);
			pstmt3.setString(2, recoPeriod);
			rst = pstmt3.executeQuery();
			if (rst.next()) {
				count = rst.getInt(1);
				logger.info("completeCount == " + completeCount);
			}

			if (count != 0) {
				certId = "-1";
			}
			String SQL_RECO_PERIOD_LIST;
			if(result)
			 SQL_RECO_PERIOD_LIST = DBQueries.SQL_RECO_PRODUCT_LIST_;// adding by pratap for showing system figure only to distributor
			else
				SQL_RECO_PERIOD_LIST = DBQueries.SQL_RECO_PRODUCT_LIST;	
			if (!stbType.equals("-1")) {
				SQL_RECO_PERIOD_LIST += " and RD.PRODUCT_TYPE=? ";
			}
			SQL_RECO_PERIOD_LIST += "  order by RD.PRODUCT_ID ,RD.CREATED_BY asc with ur";
			 logger.info(result + "SQL_RECO_PERIOD_LIST:::"+SQL_RECO_PERIOD_LIST);
			pstmt = connection.prepareStatement(SQL_RECO_PERIOD_LIST);
			pstmt.setString(1, recoPeriod);
			pstmt.setString(2, distId);
			if (!stbType.equals("-1")) {
				pstmt.setString(3, stbType);
			}
			rset = pstmt.executeQuery();
			Integer totInterOK = 0;
			int i = 0;
			String prodName=""; 
			Integer openTotal=0;
			Integer rcvdTotal=0;
			Integer retTotal=0;
			Integer activationTotal=0;
			Integer adjstTotal=0;
			Integer closingTotal=0;
			Integer invChngTotal=0;
			Integer sysInvChange=0;
			Integer adjstWithoutReco=0;
			while (rset.next()) {
				i++;
				adjstWithoutReco=0;
				// logger.info("While...");
				recoProductDto = new DistRecoDto();
				totInterOK = 0;
				
				openTotal=0;
				rcvdTotal=0;
				retTotal=0;
				activationTotal=0;
				adjstTotal=0;
				closingTotal=0;
				invChngTotal=0;
				logger.info("Complete Count--->"+completeCount);
				if (completeCount != 0) {
						 //logger.info("completeCount if");
						recoProductDto.setIsValidToEnter("0");
					} else {
						// logger.info(" completeCount else");
						recoProductDto.setIsValidToEnter("1");
					}
					recoProductDto.setProductType(rset.getString("PRODUCT_TYPE"));
					recoProductDto.setDataTypaId(rset.getString("CREATED_BY"));
					recoProductDto.setIsPartnerEntered(rset.getString("RECO_STATUS"));
					recoProductDto.setProductId(rset.getString("PRODUCT_ID"));
					recoProductDto.setProductName(rset.getString("PRODUCT_NAME"));
					prodName = rset.getString("PRODUCT_NAME");
					recoProductDto.setSerialisedOpenStock(rset.getString("SER_OPEN_STOCK"));
					recoProductDto.setNonSerialisedOpenStock(rset.getString("NSER_OPEN_STOCK"));
					recoProductDto.setDefectiveOpenStock(rset.getString("DEFECTIVE_OPEN_STOCK"));
					recoProductDto.setUpgradeOpenStock(rset.getString("UPGRADE_OPEN_STOCK"));
					recoProductDto.setChurnOpenStock(rset.getString("CHURN_OPEN_STOCK"));
					recoProductDto.setPendingPOIntransit(rset.getString("PENDING_PO_CLOSING"));
					recoProductDto.setReceivedInterSSDOK(rset.getString("RECEIVED_INTER_SSD_OK"));
					recoProductDto.setReceivedInterSSDDef(rset.getString("RECEIVED_INTER_SSD_DEF"));
					recoProductDto.setReceivedHierarchyTrans(rset.getString("RECEIVED_HIERARCHY_TRANS"));
					if (rset.getString("INVENTORY_CHANGE") != null) {
						sysInvChange = Integer.parseInt(rset.getString("INVENTORY_CHANGE"));
					}
					if(i%2==0){
						recoProductDto.setInventoryChange(sysInvChange.toString());
					}else{
						recoProductDto.setInventoryChange(rset.getString("INVENTORY_CHANGE"));
					}
					
					
					recoProductDto.setReturnedFresh(rset.getString("RETURNED_FRESH"));
					recoProductDto.setReturnedInterSSDOk(rset.getString("RETURNED_INTER_SSD_OK"));
					recoProductDto.setReturnedInterSSDDEF(rset.getString("RETURNED_INTER_SSD_DEF"));

					recoProductDto.setPendingDCIntransit(rset.getString("PENDING_DC_CLOSING"));
					recoProductDto.setReturnedHierarchyTrans(rset.getString("RETURNED_HIERARCHY_TRANS"));
					recoProductDto.setReturnedChurn(rset.getString("RETURNED_CHURN"));
					recoProductDto.setFlushOutOk(rset.getString("FLUSH_OUT_OK"));
					recoProductDto.setFlushOutDefective(rset.getString("FLUSH_OUT_DEFECTIVE"));
					recoProductDto.setRecoQty(rset.getString("RECO_QTY"));
					recoProductDto.setSerialisedClosingStock(rset.getString("SER_CLOSING_STOCK"));
					recoProductDto.setNonSerialisedClosingStock(rset.getString("NSER_CLOSING_STOCK"));
					recoProductDto.setDefectiveClosingStock(rset.getString("DEF_CLOSING_STOCK"));
					recoProductDto.setUpgradeClosingStock(rset.getString("UPGRADE_CLOSING_STOCK"));
					recoProductDto.setChurnClosingStock(rset.getString("CHURN_CLOSING_STOCK"));
					
					

					recoProductDto.setNonSerialisedActivation(rset.getString("NSER_ACTIVATION"));
					recoProductDto.setNonSerialisedOpenStock(rset.getString("NSER_OPEN_STOCK"));

					recoProductDto.setReceivedChurn(rset.getString("RECEIVED_CHURN"));
					recoProductDto.setReceivedUpgrade(rset.getString("RECEIVED_UPGRADE"));
					recoProductDto.setReceivedWh(rset.getString("RECEIVED_WH"));
					recoProductDto.setReturnedC2S(rset.getString("RETURNED_C2S"));
					recoProductDto.setReturnedDefectiveSwap(rset.getString("RETURNED_SWAP"));
					recoProductDto.setReturnedDoaAi(rset.getString("RETURNED_DOA_AI"));
					recoProductDto.setReturnedDoaBI(rset.getString("RETURNED_DOA_BI"));
					recoProductDto.setSerialisedActivation(rset.getString("SER_ACTIVATION"));
					recoProductDto.setSerialisedOpenStock(rset.getString("SER_OPEN_STOCK"));

					// Added by Shilpa on 23-6-2012 for Pending PO
					// intransit,Pending DC Intransit Open and closing Stock
					recoProductDto.setPendingPOIntransitOpen(rset.getString("PENDING_PO_OPENING"));
					recoProductDto.setOpeningPendgDCIntrnsit(rset.getString("PENDING_DC_OPENING"));
				//	logger.info("CREATED BY =================================================="+rset.getString("CREATED_BY"));
					if (rset.getString("CREATED_BY").equals("0")) {
						recoProductDto.setIsTextField("N");
					} else if (rset.getString("RECO_STATUS").equals("SUCCESS")) {
						recoProductDto.setIsTextField("N");
					} else if (rset.getString("RECO_STATUS").equals("INITIATE")) {
						recoProductDto.setIsTextField("Y");
					}
					
					if(result)
					{
						recoProductDto.setIsPartnerEntered("SUCCESS");
						
					}
					recoProductDto.setIsSubmitted(rset.getString("RECO_STATUS"));
					//logger.info("setIsTextField");

					if (recoProductDto.getDataTypaId().equals("0")) {

						recoProductDto.setDataTypa("System Generated");

					} else {
						recoProductDto.setDataTypa("Partner Figure");
					}
					// Inter SSD OK = Interssd OK + Hierarchy Transfer
					if (rset.getString("RECEIVED_INTER_SSD_OK") != null) {
						totInterOK += Integer.parseInt(rset
								.getString("RECEIVED_INTER_SSD_OK"));
					}
					if (rset.getString("RECEIVED_HIERARCHY_TRANS") != null) {
						totInterOK += Integer.parseInt(rset
								.getString("RECEIVED_HIERARCHY_TRANS"));
					}
					if (totInterOK != 0) {
						recoProductDto.setReceivedInterSSDOK(totInterOK
								.toString());
					}
					totInterOK = 0;

					if (rset.getString("RETURNED_INTER_SSD_OK") != null) {
						totInterOK += Integer.parseInt(rset
								.getString("RETURNED_INTER_SSD_OK"));
					}
					if (rset.getString("RETURNED_HIERARCHY_TRANS") != null) {
						totInterOK += Integer.parseInt(rset
								.getString("RETURNED_HIERARCHY_TRANS"));
					}
					if (totInterOK != 0) {
						recoProductDto.setReturnedInterSSDOk(totInterOK
								.toString());
					}
					
					/******************
					Calculating Total Open Stock
					*******************/	
					//// Pending po and pending dc exluded in closing stock as it should not be considerd
					
					/*if (rset.getString("PENDING_PO_OPENING") != null) {
						openTotal += Integer.parseInt(rset.getString("PENDING_PO_OPENING"));
					}
					if (rset.getString("PENDING_DC_OPENING") != null) {
						openTotal += Integer.parseInt(rset.getString("PENDING_DC_OPENING"));
					}*/
					
					//// pending dc included in closing stock as it should be considerd
					
					if (rset.getString("PENDING_DC_OPENING") != null) {
						openTotal += Integer.parseInt(rset.getString("PENDING_DC_OPENING"));
					}
					
					if (rset.getString("SER_OPEN_STOCK") != null) {
						openTotal += Integer.parseInt(rset.getString("SER_OPEN_STOCK"));
					}
					if (rset.getString("NSER_OPEN_STOCK") != null) {
						openTotal += Integer.parseInt(rset.getString("NSER_OPEN_STOCK"));
					}
					if (rset.getString("DEFECTIVE_OPEN_STOCK") != null) {
						openTotal += Integer.parseInt(rset.getString("DEFECTIVE_OPEN_STOCK"));
					}
					if (rset.getString("UPGRADE_OPEN_STOCK") != null) {
						openTotal += Integer.parseInt(rset.getString("UPGRADE_OPEN_STOCK"));
					}
					if (rset.getString("CHURN_OPEN_STOCK") != null) {
						openTotal += Integer.parseInt(rset.getString("CHURN_OPEN_STOCK"));
					}
					recoProductDto.setOpenTotal(openTotal.toString());
					/******************
					Calculating Total Recvd Stock
					*******************/	
					if (rset.getString("RECEIVED_WH") != null) {
						rcvdTotal += Integer.parseInt(rset.getString("RECEIVED_WH"));
					}
					if (rset.getString("RECEIVED_INTER_SSD_OK") != null) {
						rcvdTotal += Integer.parseInt(rset.getString("RECEIVED_INTER_SSD_OK"));
					}
					if (rset.getString("RECEIVED_INTER_SSD_DEF") != null) {
						rcvdTotal += Integer.parseInt(rset.getString("RECEIVED_INTER_SSD_DEF"));
					}
					if (rset.getString("RECEIVED_HIERARCHY_TRANS") != null) {
						rcvdTotal += Integer.parseInt(rset.getString("RECEIVED_HIERARCHY_TRANS"));
					}
					if (rset.getString("RECEIVED_UPGRADE") != null) {
						rcvdTotal += Integer.parseInt(rset.getString("RECEIVED_UPGRADE"));
					}
					if (rset.getString("RECEIVED_CHURN") != null) {
						rcvdTotal += Integer.parseInt(rset.getString("RECEIVED_CHURN"));
					}
					recoProductDto.setReceivedTotal(rcvdTotal.toString());
			/******************
			Calculating Total Returned Stock - Start
			*******************/				
					if (rset.getString("RETURNED_FRESH") != null) {
						retTotal += Integer.parseInt(rset.getString("RETURNED_FRESH"));
					}
					if (rset.getString("RETURNED_INTER_SSD_OK") != null) {
						retTotal += Integer.parseInt(rset.getString("RETURNED_INTER_SSD_OK"));
					}
					if (rset.getString("RETURNED_INTER_SSD_DEF") != null) {
						retTotal += Integer.parseInt(rset.getString("RETURNED_INTER_SSD_DEF"));
					}
					if (rset.getString("RETURNED_HIERARCHY_TRANS") != null) {
						retTotal += Integer.parseInt(rset.getString("RETURNED_HIERARCHY_TRANS"));
					}
					if (rset.getString("RETURNED_DOA_BI") != null) {
						retTotal += Integer.parseInt(rset.getString("RETURNED_DOA_BI"));
					}
					if (rset.getString("RETURNED_DOA_AI") != null) {
						retTotal += Integer.parseInt(rset.getString("RETURNED_DOA_AI"));
					}
					if (rset.getString("RETURNED_SWAP") != null) {
						retTotal += Integer.parseInt(rset.getString("RETURNED_SWAP"));
					}
					if (rset.getString("RETURNED_C2S") != null) {
						retTotal += Integer.parseInt(rset.getString("RETURNED_C2S"));
					}
					if (rset.getString("RETURNED_CHURN") != null) {
						retTotal += Integer.parseInt(rset.getString("RETURNED_CHURN"));
					}
					recoProductDto.setReturnedTotal(retTotal.toString());
				/**********************
				Calculating Total Activation
				*******************/	
					if (rset.getString("SER_ACTIVATION") != null) {
						activationTotal += Integer.parseInt(rset.getString("SER_ACTIVATION"));
					}
					if (rset.getString("NSER_ACTIVATION") != null) {
						activationTotal += Integer.parseInt(rset.getString("NSER_ACTIVATION"));
					}
					
					recoProductDto.setActivationTotal(activationTotal.toString());
			/**********************
			Calculating Inventory Change
			*******************/	
					
						if(i%2==0){
							invChngTotal += sysInvChange;
							sysInvChange=0;
						}else{
							if (rset.getString("INVENTORY_CHANGE") != null) {	
								invChngTotal += Integer.parseInt(rset.getString("INVENTORY_CHANGE"));
							}
						}
						
						recoProductDto.setInvChngTotal(invChngTotal.toString());
					
					
					
				/***************************
				Calculating total Adjustment
				*******************/	
					if (rset.getString("FLUSH_OUT_OK") != null) {
						adjstWithoutReco+= Integer.parseInt(rset
								.getString("FLUSH_OUT_OK"));
						adjstTotal += Integer.parseInt(rset
								.getString("FLUSH_OUT_OK"));
					}
					if (rset.getString("FLUSH_OUT_DEFECTIVE") != null) {
						adjstWithoutReco+= Integer.parseInt(rset
								.getString("FLUSH_OUT_DEFECTIVE"));
						adjstTotal += Integer.parseInt(rset
								.getString("FLUSH_OUT_DEFECTIVE"));
					}
					if (rset.getString("RECO_QTY") != null) {
						adjstTotal = adjstTotal - Integer.parseInt(rset
								.getString("RECO_QTY"));
					}
					recoProductDto.setAdjustmentTotal(adjstTotal.toString());
					
					
					/***************************
					Calculating total Closing
					*******************/	
					
					if (rset.getString("SER_CLOSING_STOCK") != null) {
						closingTotal += Integer.parseInt(rset
								.getString("SER_CLOSING_STOCK"));
					}
					if (rset.getString("NSER_CLOSING_STOCK") != null) {
						closingTotal += Integer.parseInt(rset
								.getString("NSER_CLOSING_STOCK"));
					}
					if (rset.getString("DEF_CLOSING_STOCK") != null) {
						closingTotal += Integer.parseInt(rset
								.getString("DEF_CLOSING_STOCK"));
					}
					if (rset.getString("UPGRADE_CLOSING_STOCK") != null) {
						closingTotal += Integer.parseInt(rset
								.getString("UPGRADE_CLOSING_STOCK"));
					}
					if (rset.getString("CHURN_CLOSING_STOCK") != null) {
						closingTotal += Integer.parseInt(rset
								.getString("CHURN_CLOSING_STOCK"));
					}
					//// Pending po and pending dc exluded in closing stock as it should not be considerd
					/*if (rset.getString("PENDING_PO_CLOSING") != null) {
						closingTotal += Integer.parseInt(rset
								.getString("PENDING_PO_CLOSING"));
					}
					if (rset.getString("PENDING_DC_CLOSING") != null) {
						closingTotal += Integer.parseInt(rset
								.getString("PENDING_DC_CLOSING"));
					}*/
					//// Pending dc included in closing stock as it should be considerd
					if (rset.getString("PENDING_DC_CLOSING") != null) {
						closingTotal += Integer.parseInt(rset.getString("PENDING_DC_CLOSING"));
					}
					
					recoProductDto.setClosingTotal(closingTotal.toString());
					logger.info("closing is ..."+closingTotal.toString());
					// Ends here
					
					recoProductDto.setCertId(certId);
					recoProductListDTO.add(recoProductDto);
					if(result)// Adding by pratap to freez the text box
					{
						//checking whether distributor uploaded stock for closing or not
						PreparedStatement stmtClosing=null;
						ResultSet rsetClosing=null;
						stmtClosing=connection.prepareStatement("SELECT * FROM RECO_CLOSINGSTOCK_TEMP WHERE DIST_ID=? AND RECO_PERIOD_ID=? with ur");
						stmtClosing.setInt(1,Integer.parseInt(String.valueOf(distId)));
						stmtClosing.setInt(2, Integer.parseInt(recoPeriod));
						rsetClosing=stmtClosing.executeQuery();
						DistRecoDto	recoProductDtoPartner = new DistRecoDto();
						
						recoProductDtoPartner=recoProductDto.clone();
						recoProductDtoPartner.setSerialisedActivation(activationTotal.toString());
//						--added by vishwas for hidden button
						if(recoProductDtoPartner.getSerialisedClosingStock().equalsIgnoreCase("0"))
						{
							recoProductDtoPartner.setShowDetailsClosingser("0");
						}
						if(recoProductDtoPartner.getDefectiveClosingStock().equalsIgnoreCase("0"))
						{
							recoProductDtoPartner.setShowDetailsClosingdef("0");
						}
						if(recoProductDtoPartner.getChurnClosingStock().equalsIgnoreCase("0"))
						{
							recoProductDtoPartner.setShowDetailsClosingchu("0");
						}
						if(recoProductDtoPartner.getPendingPOIntransit().equalsIgnoreCase("0"))
						{
							recoProductDtoPartner.setShowDetailsClosingPPO("0");
						}
						if(recoProductDtoPartner.getPendingDCIntransit().equalsIgnoreCase("0"))
						{
							recoProductDtoPartner.setShowDetailsClosingPDC("0");
						}
						if(recoProductDtoPartner.getActivationTotal().equalsIgnoreCase("0"))
						{
							recoProductDtoPartner.setShowDetailsClosingACTTOL("0");
						}
						//System.out.println(productId+"adjstWithoutReco ++++++++++++++++++++++++++++++++++++++  "+adjstWithoutReco);
						if(adjstWithoutReco<=0)
						{
							recoProductDtoPartner.setShowDetailsClosingADJTOL("0");
						}
						if(recoProductDtoPartner.getInventoryChange().equalsIgnoreCase("0"))
						{
							recoProductDtoPartner.setShowDetailsClosingINVCHG("0");
						}
						if(recoProductDtoPartner.getUpgradeClosingStock().equalsIgnoreCase("0"))
						{
							recoProductDtoPartner.setShowDetailsClosingup("0");
						}
						//---------opening----------
						if(recoProductDtoPartner.getSerialisedOpenStock().equalsIgnoreCase("0"))
						{
							recoProductDtoPartner.setShowDetailsOpeningser("0");
						}
						if(recoProductDtoPartner.getDefectiveOpenStock().equalsIgnoreCase("0"))
						{
							recoProductDtoPartner.setShowDetailsOpeningdef("0");
						}
						if(recoProductDtoPartner.getUpgradeOpenStock().equalsIgnoreCase("0"))
						{
							recoProductDtoPartner.setShowDetailsOpeningup("0");
						}
						if(recoProductDtoPartner.getChurnOpenStock().equalsIgnoreCase("0"))
						{
							recoProductDtoPartner.setShowDetailsOpeningchu("0");
						}
						//----receive---
						if(recoProductDtoPartner.getReceivedWh().equalsIgnoreCase("0"))
						{
							recoProductDtoPartner.setShowDetailsreceivedWh("0");
						}
						if(recoProductDtoPartner.getReceivedInterSSDOK().equalsIgnoreCase("0"))
						{
							recoProductDtoPartner.setShowDetailsreceivedInterSSDOK("0");
						}
						if(recoProductDtoPartner.getReceivedInterSSDDef().equalsIgnoreCase("0"))
						{
							recoProductDtoPartner.setShowDetailsreceivedInterSSDDef("0");
						}
						if(recoProductDtoPartner.getReceivedUpgrade().equalsIgnoreCase("0"))
						{
							recoProductDtoPartner.setShowDetailsreceivedUpgrade("0");
						}
						if(recoProductDtoPartner.getReceivedChurn().equalsIgnoreCase("0"))
						{
							recoProductDtoPartner.setShowDetailsreceivedChurn("0");
						}
						//-------return------------
						if(recoProductDtoPartner.getReturnedFresh().equalsIgnoreCase("0"))
						{
							recoProductDtoPartner.setShowDetailsreturnedFresh("0");
						}
						if(recoProductDtoPartner.getReturnedInterSSDDEF().equalsIgnoreCase("0"))
						{
							recoProductDtoPartner.setShowDetailsreturnedInterSSDDEF("0");
						}
						if(recoProductDtoPartner.getReturnedInterSSDOk().equalsIgnoreCase("0"))
						{
							recoProductDtoPartner.setShowDetailsreturnedInterSSDOk("0");
						}
						if(recoProductDtoPartner.getReturnedDoaBI().equalsIgnoreCase("0"))
						{
							recoProductDtoPartner.setShowDetailsreturnedDoaBI("0");
						}
						if(recoProductDtoPartner.getReturnedDoaAi().equalsIgnoreCase("0"))
						{
							recoProductDtoPartner.setShowDetailsreturnedDoaAi("0");
						}
						if(recoProductDtoPartner.getReturnedDefectiveSwap().equalsIgnoreCase("0"))
						{
							recoProductDtoPartner.setShowDetailsreturnedDefectiveSwap("0");
						}
						if(recoProductDtoPartner.getReturnedC2S().equalsIgnoreCase("0"))
						{
							recoProductDtoPartner.setShowDetailsreturnedC2S("0");
						}
						//System.out.println("getReturnedChurn  ===    =====   " +recoProductDtoPartner.getReturnedChurn());
						if(recoProductDtoPartner.getReturnedChurn().equalsIgnoreCase("0"))
						{
							recoProductDtoPartner.setShowDetailsRChurn("0");
						}
						//--end by vishwas for hidden detail button
					
						
						if(rsetClosing.next())
						{
					try
					{
					logger.info("*************** in dao impl  in result is ok**********************:distId:"+distId+"  productId:"+productId+"  stockType:"+stockType);
					ResultSet rsetClosingStk=null;
					PreparedStatement pstmtClosingStk=null;
					if(productId == null) {
						productId = recoProductDto.getProductId();
					}
					int tempCount=0;//Neetika Issue related to showing proper counts if uploaded already
					boolean flag=false;
					if(recoProductDto.getProductId().equals(productId)) {
					logger.info("recoProductDto.getProductId() :"+recoProductDto.getProductId());
					pstmtClosingStk = connection.prepareStatement("SELECT SERIAL_NO,trim(DIST_FLAG) as DIST_FLAG FROM RECO_CLOSINGSTOCK_TEMP WHERE DIST_ID=? AND PRODUCT_ID=? AND STOCK_TYPE=1 AND RECO_PERIOD_ID=?  with ur"); //AND upper(trim(DIST_FLAG)) = 'Y' group by DIST_ID,PRODUCT_ID ,STOCK_TYPE removed by Neetika
					pstmtClosingStk.setString(1, distId);
					pstmtClosingStk.setString(2, productId);
					pstmtClosingStk.setInt(3, Integer.parseInt(recoPeriod));
					rsetClosingStk = pstmtClosingStk.executeQuery();
					while(rsetClosingStk.next())
					{
						//logger.info("******* serialized stock ******pop*** :"+rsetClosingStk.getString("SERIAL_NO"));
						
						if(rsetClosingStk.getString("SERIAL_NO").equalsIgnoreCase(Constants.CLOSING_DEFAULT_SERIAL_NO))
						{
							break;
						}
						else if(rsetClosingStk.getString("DIST_FLAG")!=null && rsetClosingStk.getString("DIST_FLAG").equalsIgnoreCase("Y"))
						{
							tempCount=tempCount+1;
							flag=true;
						}
						else
						{
							flag=true; //flag true indicates that user has uploaded once
						}
						
						
					}
					if(flag==true)
					recoProductDtoPartner.setSerialisedClosingStock(""+tempCount);
				/*	else
					{
						recoProductDtoPartner.setSerialisedClosingStock("0");
					}*/
					 tempCount=0;//Neetika Issue related to showing proper counts if uploaded already
					 flag=false;
					pstmtClosingStk.clearParameters();
					pstmtClosingStk = connection.prepareStatement("SELECT SERIAL_NO,trim(DIST_FLAG) DIST_FLAG FROM RECO_CLOSINGSTOCK_TEMP WHERE DIST_ID=? AND PRODUCT_ID=? AND STOCK_TYPE=2 AND RECO_PERIOD_ID=?  with ur");
					pstmtClosingStk.setString(1, distId);
					pstmtClosingStk.setString(2, productId);
					pstmtClosingStk.setInt(3, Integer.parseInt(recoPeriod));
					rsetClosingStk = pstmtClosingStk.executeQuery();
					while(rsetClosingStk.next())
					{
						if(rsetClosingStk.getString("SERIAL_NO").equalsIgnoreCase(Constants.CLOSING_DEFAULT_SERIAL_NO))
						{
							break;
						}
						else if(rsetClosingStk.getString("DIST_FLAG")!=null && rsetClosingStk.getString("DIST_FLAG").equalsIgnoreCase("Y"))
						{
							tempCount=tempCount+1;
							flag=true;
						}
						else
						{
							flag=true; //flag true indicates that user has uploaded once
						}
						
						
					}
					//recoProductDtoPartner.setDefectiveClosingStock(rsetClosingStk.getString(1));
					if(flag==true)
						recoProductDtoPartner.setDefectiveClosingStock(""+tempCount);
					
					
					pstmtClosingStk.clearParameters();
					
					tempCount=0;//Neetika Issue related to showing proper counts if uploaded already
					 flag=false;
					pstmtClosingStk = connection.prepareStatement("SELECT SERIAL_NO,trim(DIST_FLAG) DIST_FLAG FROM RECO_CLOSINGSTOCK_TEMP WHERE DIST_ID=? AND PRODUCT_ID=? AND STOCK_TYPE=3 AND RECO_PERIOD_ID=?  with ur");
					pstmtClosingStk.setString(1, distId);
					pstmtClosingStk.setString(2, productId);
					pstmtClosingStk.setInt(3, Integer.parseInt(recoPeriod));
					rsetClosingStk = pstmtClosingStk.executeQuery();
					while(rsetClosingStk.next())
					{
						//logger.info("******* upgrade stock ********* :"+rsetClosingStk.getString("SERIAL_NO"));
						if(rsetClosingStk.getString("SERIAL_NO").equalsIgnoreCase(Constants.CLOSING_DEFAULT_SERIAL_NO))
						{
							break;
						}
						else if(rsetClosingStk.getString("DIST_FLAG")!=null && rsetClosingStk.getString("DIST_FLAG").equalsIgnoreCase("Y"))
						{
							tempCount=tempCount+1;
							flag=true;
						}
						else
						{
							flag=true; //flag true indicates that user has uploaded once
						}
						
					}
					/*else
					{
						recoProductDtoPartner.setUpgradeClosingStock("0");
					}*/
					if(flag==true)
						recoProductDtoPartner.setUpgradeClosingStock(""+tempCount);	
					tempCount=0;//Neetika Issue related to showing proper counts if uploaded already
					 flag=false;
					pstmtClosingStk.clearParameters();
					pstmtClosingStk = connection.prepareStatement("SELECT SERIAL_NO,trim(DIST_FLAG) DIST_FLAG FROM RECO_CLOSINGSTOCK_TEMP WHERE DIST_ID=? AND PRODUCT_ID=? AND STOCK_TYPE=4 AND RECO_PERIOD_ID=?  with ur");
					pstmtClosingStk.setString(1, distId);
					pstmtClosingStk.setString(2, productId);
					pstmtClosingStk.setInt(3, Integer.parseInt(recoPeriod));
					rsetClosingStk = pstmtClosingStk.executeQuery();
					while(rsetClosingStk.next())
					{
						//logger.info("******* churn stock ********* :"+rsetClosingStk.getString("SERIAL_NO"));
						if(rsetClosingStk.getString("SERIAL_NO").equalsIgnoreCase(Constants.CLOSING_DEFAULT_SERIAL_NO))
						{
							break;
						}
						else if(rsetClosingStk.getString("DIST_FLAG")!=null && rsetClosingStk.getString("DIST_FLAG").equalsIgnoreCase("Y"))
						{
							tempCount=tempCount+1;
							flag=true;
						}
						else
						{
							flag=true; //flag true indicates that user has uploaded once
						}
						
					}
					/*else
					{
						recoProductDtoPartner.setUpgradeClosingStock("0");
					}*/
					if(flag==true)
						recoProductDtoPartner.setChurnClosingStock(""+tempCount);	
					tempCount=0;//Neetika Issue related to showing proper counts if uploaded already
					 flag=false;
					}
					}
					catch(Exception ex)
					{
						logger.info("Exception occured :"+ex.getMessage());
						ex.printStackTrace();
					}
					}
						//added by Beeru 19 Apr 2014
						int closingTotalPartner  = 0;
						if(rset.getString("RECO_STATUS").equalsIgnoreCase("INITIATE")) {
							logger.info("closingTotalPartner is ..."+closingTotalPartner);
							closingTotalPartner = Integer.parseInt(recoProductDtoPartner.getSerialisedClosingStock());
							closingTotalPartner += Integer.parseInt(recoProductDtoPartner.getDefectiveClosingStock());
							closingTotalPartner += Integer.parseInt(recoProductDtoPartner.getUpgradeClosingStock());
							closingTotalPartner += Integer.parseInt(recoProductDtoPartner.getChurnClosingStock());
							if (rset.getString("NSER_CLOSING_STOCK") != null) {
								closingTotalPartner += Integer.parseInt(rset.getString("NSER_CLOSING_STOCK"));
							}
							if (rset.getString("PENDING_DC_CLOSING") != null) {
								closingTotalPartner += Integer.parseInt(rset.getString("PENDING_DC_CLOSING"));
							}
							recoProductDtoPartner.setClosingTotal(String.valueOf(closingTotalPartner));
							closingTotalPartner  =0;
						}else if(rset.getString("RECO_STATUS").equalsIgnoreCase("SUCCESS")) {
						PreparedStatement pstmtSuccess = null ;
						ResultSet rsetSuccess = null;
						pstmtSuccess = connection.prepareStatement(DBQueries.SQL_RECO_SUCCESS_PRODUCT_LIST_);
						pstmtSuccess.setString(1, recoPeriod);
						pstmtSuccess.setString(2, distId);
						pstmtSuccess.setInt(3, Integer.parseInt(rset.getString("PRODUCT_ID")));
						rsetSuccess = pstmtSuccess.executeQuery();
						closingTotal =  0;
						if(rsetSuccess.next()) {
							closingTotal += rsetSuccess.getString("SER_CLOSING_STOCK") == null ? 0 :Integer.parseInt(rsetSuccess.getString("SER_CLOSING_STOCK"));
							closingTotal += rsetSuccess.getString("NSER_CLOSING_STOCK") == null? 0 :Integer.parseInt(rsetSuccess.getString("NSER_CLOSING_STOCK"));
							closingTotal +=rsetSuccess.getString("DEF_CLOSING_STOCK") == null ?0: Integer.parseInt(rsetSuccess.getString("DEF_CLOSING_STOCK"));
							closingTotal += rsetSuccess.getString("UPGRADE_CLOSING_STOCK") == null ?0:Integer.parseInt(rsetSuccess.getString("UPGRADE_CLOSING_STOCK"));
							closingTotal += rsetSuccess.getString("CHURN_CLOSING_STOCK") == null ?0:Integer.parseInt(rsetSuccess.getString("CHURN_CLOSING_STOCK"));
							closingTotal += rsetSuccess.getString("PENDING_DC_CLOSING") == null ?0:Integer.parseInt(rsetSuccess.getString("PENDING_DC_CLOSING"));

							recoProductDtoPartner.setSerialisedClosingStock(null);
							recoProductDtoPartner.setDefectiveClosingStock(null);
							recoProductDtoPartner.setUpgradeClosingStock(null);
							recoProductDtoPartner.setChurnClosingStock(null);
							recoProductDtoPartner.setClosingTotal(null);
							recoProductDtoPartner.setSerialisedClosingStock(rsetSuccess.getString("SER_CLOSING_STOCK"));
							recoProductDtoPartner.setDefectiveClosingStock(rsetSuccess.getString("DEF_CLOSING_STOCK"));
							recoProductDtoPartner.setUpgradeClosingStock(rsetSuccess.getString("UPGRADE_CLOSING_STOCK"));
							recoProductDtoPartner.setChurnClosingStock(rsetSuccess.getString("CHURN_CLOSING_STOCK"));
							recoProductDtoPartner.setClosingTotal(String.valueOf(closingTotal));
						}
						}
						//end by Beeru 19 Apr 2014
						recoProductDtoPartner.setDataTypa("Partner Figure");
						//recoProductDtoPartner.setIsValidToEnter("1");
						recoProductDtoPartner.setIsPartnerEntered("INITIATE");
						//logger.info("PF"+recoProductDtoPartner.getProductId()+"-"+recoProductDtoPartner.getIsPartnerEntered());
						//logger.info("SF"+recoProductDto.getIsPartnerEntered());
						recoProductListDTO.add(recoProductDtoPartner);
						
						DistRecoDto	recoProductDtoVarience = new DistRecoDto();
						recoProductDtoVarience.setProductName(prodName);
						recoProductDtoVarience.setDataTypa("Variance");
						//System.out.println("Integer.parseInt(recoProductDto.getDefectiveClosingStock()) :"+Integer.parseInt(recoProductDto.getDefectiveClosingStock()));
						//System.out.println("Integer.parseInt(recoProductDtoPartner.getDefectiveClosingStock()) :"+Integer.parseInt(recoProductDtoPartner.getDefectiveClosingStock()));
						System.out.println("Diff :"+String.valueOf((Integer.parseInt(recoProductDto.getDefectiveClosingStock())- Integer.parseInt(recoProductDtoPartner.getDefectiveClosingStock()))));
						recoProductDtoVarience.setSerialisedClosingStock(null);
						recoProductDtoVarience.setDefectiveClosingStock(null);
						recoProductDtoVarience.setUpgradeClosingStock(null);
						recoProductDtoVarience.setChurnClosingStock(null);
						
						recoProductDtoVarience.setSerialisedClosingStock(String.valueOf((Integer.parseInt(recoProductDtoPartner.getSerialisedClosingStock())- Integer.parseInt(recoProductDto.getSerialisedClosingStock()))));
						logger.info("  =========================================  "+recoProductDtoVarience.getSerialisedClosingStock());
						recoProductDtoVarience.setDefectiveClosingStock(String.valueOf((Integer.parseInt(recoProductDtoPartner.getDefectiveClosingStock())- Integer.parseInt(recoProductDto.getDefectiveClosingStock()))));
						recoProductDtoVarience.setUpgradeClosingStock(String.valueOf((Integer.parseInt(recoProductDtoPartner.getUpgradeClosingStock())- Integer.parseInt(recoProductDto.getUpgradeClosingStock()))));
						recoProductDtoVarience.setChurnClosingStock(String.valueOf((Integer.parseInt(recoProductDtoPartner.getChurnClosingStock())- Integer.parseInt(recoProductDto.getChurnClosingStock()))));
						recoProductListDTO.add(recoProductDtoVarience);
						productId =  null;
			}
					else// End adding by pratap
					{
					if (i % 2 == 0) {
						recoProductDto = new DistRecoDto();
						recoProductDto.setProductName(prodName);
						recoProductDto.setDataTypa("Variance");
						recoProductListDTO.add(recoProductDto);
					}  
					}
					
				}
			
		} catch (Exception e) {
			e.printStackTrace();
			throw new DAOException(e.getMessage());
		} finally {
			DBConnectionManager.releaseResources(pstmt, rset);
			DBConnectionManager.releaseResources(pstmt2, rst);
		}
		return recoProductListDTO;
	}

	public String submitDetail(DistRecoDto distRecoDto) throws DAOException {
		PreparedStatement pstmt = null;
		PreparedStatement pstmt1 = null;
		PreparedStatement pstmt2 = null;
		PreparedStatement pstmt3 = null;
		PreparedStatement pstmt5 = null;
		PreparedStatement pstmt6 = null;
		ResultSet rst = null;
		ResultSet rst2 = null;
		int completeCount = -1;
		Integer totalClosing = 0;
		String certificateId = "-1";
		logger.info("in submitDetail function of  DistRecoDaoImpl");
		try {
			// To update DP_RECO_DIST_DETAILS partner entered data
			logger.info("Remarks ---"+ distRecoDto.getRemarks());
			pstmt = connection.prepareStatement(SQL_UPDATE_RECO_DETAIL);

			pstmt.setString(1, distRecoDto.getSerialisedOpenStock());
			pstmt.setString(2, distRecoDto.getNonSerialisedOpenStock());
			pstmt.setString(3, distRecoDto.getDefectiveOpenStock());
			pstmt.setString(4, distRecoDto.getUpgradeOpenStock());
			pstmt.setString(5, distRecoDto.getChurnOpenStock());
			pstmt.setString(6, distRecoDto.getReceivedWh());
			pstmt.setString(7, distRecoDto.getPendingPOIntransit()); // Closing pending PO
			pstmt.setString(8, distRecoDto.getReceivedInterSSDOK());
			pstmt.setString(9, distRecoDto.getReceivedInterSSDDef());
			pstmt.setString(10, distRecoDto.getReceivedHierarchyTrans());
			pstmt.setString(11, distRecoDto.getReceivedUpgrade());
			pstmt.setString(12, distRecoDto.getReceivedChurn());
			pstmt.setString(13, distRecoDto.getReturnedFresh());
			pstmt.setString(14, distRecoDto.getInventoryChange());
			pstmt.setString(15, distRecoDto.getPendingDCIntransit()); // Closing Pending DC
			pstmt.setString(16, distRecoDto.getReturnedInterSSDDEF());
			pstmt.setString(17, distRecoDto.getReturnedInterSSDOk());
			pstmt.setString(18, distRecoDto.getReturnedHierarchyTrans());
			pstmt.setString(19, distRecoDto.getReturnedDoaBI());
			pstmt.setString(20, distRecoDto.getReturnedDoaAi());
			pstmt.setString(21, distRecoDto.getReturnedDefectiveSwap());
			pstmt.setString(22, distRecoDto.getReturnedChurn());
			pstmt.setString(23, distRecoDto.getReturnedC2S());
			pstmt.setString(24, distRecoDto.getSerialisedActivation());
			pstmt.setString(25, distRecoDto.getNonSerialisedActivation());
			pstmt.setString(26, distRecoDto.getSerialisedClosingStock());
			pstmt.setString(27, distRecoDto.getNonSerialisedClosingStock());
			pstmt.setString(28, distRecoDto.getDefectiveClosingStock());
			pstmt.setString(29, distRecoDto.getUpgradeClosingStock());
			pstmt.setString(30, distRecoDto.getChurnClosingStock());

			pstmt.setString(31, distRecoDto.getPendingPOIntransitOpen());
			pstmt.setString(32, distRecoDto.getOpeningPendgDCIntrnsit());
			pstmt.setString(33, distRecoDto.getFlushOutOk());
			pstmt.setString(34, distRecoDto.getFlushOutDefective());
			pstmt.setString(35, distRecoDto.getRecoQty());

			pstmt.setString(36, Constants.PARTNER_RECO_DETAIL_SUCCESS);
			pstmt.setString(37, distRecoDto.getOldOrNewReco());// Added By RAM
			pstmt.setString(38, distRecoDto.getRemarks());
			pstmt.setString(39, distRecoDto.getRecoPeriodId());
			pstmt.setString(40, distRecoDto.getDistId());
			pstmt.setString(41, distRecoDto.getProductId());
			pstmt.setString(42, distRecoDto.getDistId());
			


			int totalRows = pstmt.executeUpdate();
			logger.info("Rows updated == " + totalRows);

			
			/****** Added by RAM to delete data from temp table of closing stock************/
			int totalRows6=0;
			if( distRecoDto.getOldOrNewReco().trim().equalsIgnoreCase("N"))
				{
				//added by beeru on 16 Apr 2014
				
				PreparedStatement pstmtForSelectFromTemp = null;
				PreparedStatement pstmtUpdate = null;
				String UpdateQuery =  null;
				pstmtForSelectFromTemp = connection.prepareStatement(SQL_SELECT_FROM_TEMP);
				pstmtForSelectFromTemp.setString(1,distRecoDto.getDistId());
				pstmtForSelectFromTemp.setInt(2, Integer.parseInt(distRecoDto.getRecoPeriodId()));
				pstmtForSelectFromTemp.setInt(3, Integer.parseInt(distRecoDto.getProductId()));
				logger.info("Dist ID --  "+distRecoDto.getDistId()+"          Reco period   ------ "+distRecoDto.getRecoPeriodId()+"       Product id       -----   "+distRecoDto.getProductId());
				ResultSet resultSet   = pstmtForSelectFromTemp.executeQuery();
				while(resultSet.next()) {
					boolean updateflag = false;
					try {
					int stockType =  resultSet.getInt("STOCK_TYPE") ;
					switch (stockType) {
					case Constants.CLOSING_SERIALIZED_STOCK:
					{
						UpdateQuery = DBQueries.SQL_UPDATE_SERIALIZED_STOCK;
						break;
					}
					case Constants.CLOSING_DEFECTIVE_STOCK:
					{
						UpdateQuery = DBQueries.SQL_UPDATE_DEFECTIVE_STOCK ;
						break;
					}
					case Constants.CLOSING_UPGRADE_STOCK:
					{
						UpdateQuery =  DBQueries.SQL_UPDATE_UPGRADE_STOCK;
						break;
					}
					case Constants.CLOSING_CHURN_STOCK:
					{
						UpdateQuery = DBQueries.SQL_UPDATE_CHURN_STOCK;
						break;
					}
					}
					if(!updateflag && resultSet.getString("SERIAL_NO")!= null && (resultSet.getString("SERIAL_NO").equalsIgnoreCase(Constants.CLOSING_DEFAULT_SERIAL_NO) || resultSet.getString("SERIAL_NO")== Constants.CLOSING_DEFAULT_SERIAL_NO)){
						updateflag  = true;
					}else if(stockType == Constants.CLOSING_SERIALIZED_STOCK){
						UpdateQuery =  UpdateQuery +" AND SERIAL_NO =? WITH UR";
					}else {
						UpdateQuery =  UpdateQuery +" AND DEFECTIVE_SR_NO =? WITH UR";
					}
					//logger.info("UpdateQuery" +UpdateQuery);
					pstmtUpdate = connection.prepareStatement(UpdateQuery);
					pstmtUpdate.setInt(4, Integer.parseInt(distRecoDto.getRecoPeriodId()));
					pstmtUpdate.setString(5,distRecoDto.getDistId());
					pstmtUpdate.setInt(6, Integer.parseInt(distRecoDto.getProductId()));
					if(resultSet.getString("OTHER_REMARKS")!=null && !resultSet.getString("OTHER_REMARKS").equalsIgnoreCase(""))
					{
						pstmtUpdate.setString(3,resultSet.getString("OTHER_REMARKS"));	
					}
					else
					{
						pstmtUpdate.setString(3,null);
					}
					if(!updateflag && resultSet.getString("SERIAL_NO") != null){
						pstmtUpdate.setString(1,resultSet.getString("DIST_FLAG"));
						pstmtUpdate.setString(2,resultSet.getString("DIST_REMARKS"));
						pstmtUpdate.setString(7,resultSet.getString("SERIAL_NO").trim());
					}else {
						pstmtUpdate.setString(1, ResourceReader.getCoreResourceBundleValue("CLOSING.DISTFLAG"));
						pstmtUpdate.setString(2, ResourceReader.getCoreResourceBundleValue("CLOSING.DISTREMARKS"));
					}
					int rs  =  pstmtUpdate.executeUpdate();
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
				//end by beeru on 16 Apr 2014
				pstmt6=connection.prepareStatement(SQL_DELETE_FROM_TEMP);
				pstmt6.setString(1,distRecoDto.getDistId());
				pstmt6.setInt(2, Integer.parseInt(distRecoDto.getRecoPeriodId()));
				pstmt6.setInt(3, Integer.parseInt(distRecoDto.getProductId()));
				totalRows6=pstmt6.executeUpdate();
				}
			logger.info("Total rows deleted from temp :"+totalRows6);
			/****** END Added by RAM to delete data from temp table of closing stock************/
			// To update DP_RECO_DIST_DETAILS Reco status for system generated
			// Data
			pstmt1 = connection.prepareStatement(SQL_UPDATE_RECO_STATUS);

			pstmt1.setString(1, Constants.PARTNER_RECO_DETAIL_SUCCESS);
			pstmt1.setString(2, distRecoDto.getRecoPeriodId());
			pstmt1.setString(3, distRecoDto.getDistId());
			pstmt1.setString(4, distRecoDto.getProductId());
			pstmt1.setString(5, "0");

			int totalRows2 = pstmt1.executeUpdate();

			logger.info("Rows updated == " + totalRows2);

			// To get Certificate Id
			pstmt3 = connection.prepareStatement(SQL_GET_CERTIFICATE_ID);
			pstmt3.setString(1, distRecoDto.getRecoPeriodId());
			pstmt3.setString(2, distRecoDto.getDistId());
			rst2 = pstmt3.executeQuery();
			if (rst2.next()) {
				certificateId = rst2.getString("CERTIFICATE_ID").trim();
				logger.info("Certificate Id in Dao Impl == " + certificateId);
			}
			logger.info("Dist Id == " + distRecoDto.getDistId()+ " and distRecoDto.getCircleId() == "+ distRecoDto.getCircleId());

			// to insert value in DP_RECO_CERTIFICATE_DETAIL
			pstmt5 = connection.prepareStatement(SQL_INSERT_DP_RECO_CERTIFICATE_DETAIL);
			pstmt5.setLong(1, Long.parseLong(certificateId));
			pstmt5.setString(2, distRecoDto.getProductId());
			totalClosing = Integer.parseInt(distRecoDto.getSerialisedClosingStock().trim())+ Integer.parseInt(distRecoDto.getNonSerialisedClosingStock());
			totalClosing += Integer.parseInt(distRecoDto.getDefectiveClosingStock())+ Integer.parseInt(distRecoDto.getUpgradeClosingStock())
					+ Integer.parseInt(distRecoDto.getChurnClosingStock())+Integer.parseInt(distRecoDto.getPendingDCIntransit());//niki
			pstmt5.setString(3, totalClosing.toString());

			pstmt5.executeUpdate();

			// Checking, all entries have been done or not
			pstmt2 = connection.prepareStatement(SQL_RECO_COMPLETE_STATUS);
			pstmt2.setString(1, distRecoDto.getDistId());
			pstmt2.setString(2, distRecoDto.getRecoPeriodId());
			rst = pstmt2.executeQuery();
			if (rst.next()) {
				completeCount = rst.getInt(1);
				logger.info("completeCount == " + completeCount);
			}

			if (completeCount != 0) {
				certificateId = "-1";
			}
			logger.info("Certificate id at the end == " + certificateId);

			connection.commit();

		} catch (Exception e) {
			e.printStackTrace();
			certificateId = "-1";
			throw new DAOException(e.getMessage());
		} finally {
			DBConnectionManager.releaseResources(pstmt, null);
			DBConnectionManager.releaseResources(pstmt1, null);
			DBConnectionManager.releaseResources(pstmt2, rst);
			DBConnectionManager.releaseResources(pstmt3, rst2);
			DBConnectionManager.releaseResources(pstmt5, null);
			DBConnectionManager.releaseResources(pstmt6, null);
		}
		return certificateId;
	}

	public List<PrintRecoDto> getRecoPrintList(String certificateId)
			throws DAOException {
		List<PrintRecoDto> recoProductListDTO = new ArrayList<PrintRecoDto>();
		PreparedStatement pstmt = null;
		ResultSet rset = null;
		PrintRecoDto recoProductDto = null;
		logger.info("in getRecoPrintList function of  DistRecoDaoImpl");
		try {

			pstmt = connection.prepareStatement(SQL_SELECT_DP_RECO_CERTIFICATE_DETAIL);
			pstmt.setString(1, certificateId);
			rset = pstmt.executeQuery();
			recoProductListDTO = new ArrayList<PrintRecoDto>();
			while (rset.next()) {
				recoProductDto = new PrintRecoDto();
				recoProductDto.setProductId(rset.getString("PRODUCT_ID"));
				recoProductDto.setProductName(rset.getString("PRODUCT_NAME"));
				recoProductDto.setQuantity(rset.getString("QUANTITY"));
				recoProductDto.setRefNo(rset.getString("REF_NO"));
				SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
				String date = sdf.format(rset.getDate("TO_DATE"));
				recoProductDto.setCertDate(date);

				logger.info("DATE ::::  " + date);

				recoProductListDTO.add(recoProductDto);
			}
		} catch (Exception e) {
			e.printStackTrace();
			throw new DAOException(e.getMessage());
		} finally {
			DBConnectionManager.releaseResources(pstmt, rset);
		}
		return recoProductListDTO;
	}
	public boolean compareRecoGoLiveDate(String recoPeriodId) 
	{
		PreparedStatement pstmt = null;
		ResultSet rset = null;
		String recoPeriodDt=null;
		String date=null;
		String result=null;
		
		
		String SQL_SELECT_DP_RECO_Go_LIVE_DATE1= "SELECT case when (date(value) < (SELECT date(from_date)  FROM DP_RECO_PERIOD WHERE ID in ("+recoPeriodId+") AND IS_RECO_START=1))  then 'true' else 'false' end as RESPONSE FROM dp_configuration_details WHERE CONFIG_ID=27 with ur";
		
		
		
		//System.out.println("*********in compareRecoGoLiveDate function of  DistRecoDaoImpl**********");
		try {
			
			
			
			//pstmt = connection.prepareStatement(SQL_SELECT_DP_RECO_Go_LIVE_DATE);
			pstmt = connection.prepareStatement(SQL_SELECT_DP_RECO_Go_LIVE_DATE1);
			//pstmt.setInt(1, Integer.parseInt(recoPeriodId));
			//pstmt.setString(1,recoPeriodId);
			
			rset = pstmt.executeQuery();
			while (rset.next()) {
				result=rset.getString("RESPONSE");
				logger.info("comparision result of golive date  :::  " + result);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			DBConnectionManager.releaseResources(pstmt, rset);
		}
		logger.info("*********in compareRecoGoLiveDate function of  DistRecoDaoImpl**********"+result);
		return Boolean.parseBoolean(result) ;
	}
	// method written by ram 31 Jan 2014
	public List<DistRecoDto> getDetailsList(String productId, String columnId,Long distId,String tabId,String recoPeriodId) throws DPServiceException
	{
		/* tabId description 
		 * 	1 opening stock
			2  received stock
			3 returned stock
			4 activation stock
			5 closing stock
		 * */
		 
	
		PreparedStatement pstmt = null;
		ResultSet rset = null;
		String recoPeriodDt=null;
		String date=null;
		String result=null;
		DistRecoDto distRecoDto=null;
		 List<DistRecoDto> detailsList=null;
		
		logger.info("*********in compareRecoGoLiveDate function of  DistRecoDaoImpl****:::productId:"+productId+" columnId:"+columnId+" distId :"+distId+" tabId:"+tabId+"  recoPeriodId:"+recoPeriodId);
		String SQL_SELECT_DP_RECO_DETAILS_LIST =null;
		try {
			if(tabId.equals("1"))  // For Opening Stock calculation 
				{
				if(columnId.equals("1"))
				{
				SQL_SELECT_DP_RECO_DETAILS_LIST= DBQueries.SQL_SELECT_DP_RECO_OPENING_PENDING_PO_DETAILS_LIST;// Pending PO calculation
				}
				else if(columnId.equals("2"))
				{
					SQL_SELECT_DP_RECO_DETAILS_LIST= DBQueries.SQL_SELECT_DP_RECO_OPENING_PENDING_DC_DETAILS_LIST;// Pending DC calculation
				}
				else if(columnId.equals("3"))
				{
					SQL_SELECT_DP_RECO_DETAILS_LIST= DBQueries.SQL_SELECT_DP_RECO_OPENING_SERIALIZED_STOCK_DETAILS_LIST;//  Serialized Stock calculation
				}
				else if(columnId.equals("4")){
					SQL_SELECT_DP_RECO_DETAILS_LIST= DBQueries.SQL_SELECT_DP_RECO_OPENING_DEFECTIVE_STOCK_DETAILS_LIST;//  Defective Stock calculation
				}
				else if(columnId.equals("5")){
					SQL_SELECT_DP_RECO_DETAILS_LIST= DBQueries.SQL_SELECT_DP_RECO_OPENING_UPGRAGE_STOCK_DETAILS_LIST;//  Upgrade Stock calculation
				}
				else if(columnId.equals("6")){
					SQL_SELECT_DP_RECO_DETAILS_LIST= DBQueries.SQL_SELECT_DP_RECO_OPENING_CHURN_STOCK_DETAILS_LIST;//  Churn Stock calculation
				}
				}
			else if(tabId.equals("2")) // For received Stock calculation 
				{	
				if(columnId.equals("1")){
					SQL_SELECT_DP_RECO_DETAILS_LIST= DBQueries.SQL_SELECT_DP_RECO_RECEIVED_RCV_WH_STOCK_DETAILS_LIST;//  Rcv. WH  Stock calculation
				}
					else if(columnId.equals("2")){
						SQL_SELECT_DP_RECO_DETAILS_LIST= DBQueries.SQL_SELECT_DP_RECO_RECEIVED_INTERSSD_OK_STOCK_DETAILS_LIST;//  Rcv. Inter-SSD OK  Stock calculation
					}
					else if(columnId.equals("3")){
						SQL_SELECT_DP_RECO_DETAILS_LIST= DBQueries.SQL_SELECT_DP_RECO_RECEIVED_INTERSSD_DEFECTIVE_STOCK_DETAILS_LIST;//  Inter-SSD Defective calculation
					}
					else if(columnId.equals("4")){
						SQL_SELECT_DP_RECO_DETAILS_LIST= DBQueries.SQL_SELECT_DP_RECO_RECEIVED_UPGRADE_STOCK_DETAILS_LIST;//  Rec. Upgrade calculation
					}
					else if(columnId.equals("5")){
						SQL_SELECT_DP_RECO_DETAILS_LIST= DBQueries.SQL_SELECT_DP_RECO_RECEIVED_CHURN_STOCK_DETAILS_LIST;//  Rec. Churn calculation
					}
				}
			else if(tabId.equals("3"))  // For returned Stock calculation 
				{
				// Ret. DOA BI  calculation,// Ret. DOA AI  calculation,// Ret. Churn  calculation,// Ret. Def. Swap calculation
				if( columnId.equals("4") || columnId.equals("5") || columnId.equals("6") || columnId.equals("8")){
					SQL_SELECT_DP_RECO_DETAILS_LIST= DBQueries.SQL_SELECT_DP_RECO_RETURNED_COLLECTION_DETAILS_LIST;
				}
				else if(columnId.equals("1") ) // Ret. OK  calculation
				{
					//logger.info("popopop 1111111111111");
					SQL_SELECT_DP_RECO_DETAILS_LIST=DBQueries.SQL_SELECT_DP_RECO_RETURNED_OK_DETAILS_LIST;// Ret. OK  calculation
				}
					else if(columnId.equals("2")){
						SQL_SELECT_DP_RECO_DETAILS_LIST= DBQueries.SQL_SELECT_DP_RECO_RETURNED_INTERSSD_OK_DETAILS_LIST;// Ret. Inter-SSD OK  calculation
					}
				    else if(columnId.equals("3")){
						SQL_SELECT_DP_RECO_DETAILS_LIST= DBQueries.SQL_SELECT_DP_RECO_RETURNED_INTERSSD_DEFECTIVE_DETAILS_LIST;// Inter-SSD Defective  calculation
				    }
				    else if(columnId.equals("7")){
						SQL_SELECT_DP_RECO_DETAILS_LIST= DBQueries.SQL_SELECT_DP_RECO_RETURNED_CHURNED_DETAILS_LIST;//Ret. Churn  calculation
				    }
			
				}
			else if(tabId.equals("4")) // For activation Stock calculation 
				{
				if(columnId.equals("1")){
					SQL_SELECT_DP_RECO_DETAILS_LIST= DBQueries.SQL_SELECT_DP_RECO_ACTIVATION_DETAILS_LIST; // Activation  calculation
				}
					else if(columnId.equals("2")){
						SQL_SELECT_DP_RECO_DETAILS_LIST= DBQueries.SQL_SELECT_DP_RECO_ACTIVATED_INVENTORY_STOCK_DETAILS_LIST; // Inventory Change  calculation
					}
					else if(columnId.equals("3")){
						SQL_SELECT_DP_RECO_DETAILS_LIST= DBQueries.SQL_SELECT_DP_RECO_ADJUSTMENT_STOCK_DETAILS_LIST; // Adjustment  calculation
					}
				}
			else if(tabId.equals("5")) // For closing Stock calculation 
			{
				if(columnId.equals("1")){
					SQL_SELECT_DP_RECO_DETAILS_LIST= DBQueries.SQL_SELECT_DP_RECO_CLOSING_SERIALIZED_STOCK_DETAILS_LIST;// Serialized Stock calculation
				}
					else if(columnId.equals("2")){
						SQL_SELECT_DP_RECO_DETAILS_LIST= DBQueries.SQL_SELECT_DP_RECO_CLOSING_DEFECTIVE_STOCK_DETAILS_LIST;//Defective Stock  calculation
					}
					else if(columnId.equals("3")){
						SQL_SELECT_DP_RECO_DETAILS_LIST= DBQueries.SQL_SELECT_DP_RECO_CLOSING_UPGRADE_STOCK_DETAILS_LIST;// Upgrade Stock  calculation
					}
					else if(columnId.equals("4")){
						SQL_SELECT_DP_RECO_DETAILS_LIST= DBQueries.SQL_SELECT_DP_RECO_CLOSING_CHURN_STOCK_DETAILS_LIST;// Churn Stock  calculation
					}
					else if(columnId.equals("5")){
						SQL_SELECT_DP_RECO_DETAILS_LIST= DBQueries.SQL_SELECT_DP_RECO_CLOSING_PENDINGPO_STOCK_DETAILS_LIST;//Pending PO  calculation
					}
					else if(columnId.equals("6")){
						SQL_SELECT_DP_RECO_DETAILS_LIST= DBQueries.SQL_SELECT_DP_RECO_CLOSING_PENDINGDC_STOCK_DETAILS_LIST;// Pending DC  calculation
					}
			}
			//logger.info("Query  :"+SQL_SELECT_DP_RECO_DETAILS_LIST);
			pstmt = connection.prepareStatement(SQL_SELECT_DP_RECO_DETAILS_LIST);
			pstmt.setInt(1, Integer.parseInt(distId.toString()));
			pstmt.setInt(2,Integer.parseInt(productId));
			pstmt.setInt(3,Integer.parseInt(recoPeriodId));
		if(tabId.equals("3"))
		{
			if(columnId.equals("1"))
				pstmt.setInt(4,0);
			else if(columnId.equals("4"))
				{
				pstmt.setInt(4,2);
				}
			else if(columnId.equals("5") )
				{
				pstmt.setInt(4,6);
				}
			else if(columnId.equals("6"))
				{
				pstmt.setInt(4,3);
				}
			else if(columnId.equals("8")){
				pstmt.setInt(4,1);
			}
		}
		else if(tabId.equals("4")) /// Adjustment 
		{
			if(columnId.equals("3"))
			{
				pstmt.setInt(4, Integer.parseInt(distId.toString()));
				pstmt.setInt(5,Integer.parseInt(productId));
				pstmt.setInt(6,Integer.parseInt(recoPeriodId));
			}
		}
			rset = pstmt.executeQuery();
			detailsList = new ArrayList<DistRecoDto>();
			while (rset.next()) {
				distRecoDto=new DistRecoDto();
				if(tabId.equals("1") && columnId.equals("1")){
				distRecoDto.setDistOlmId((rset.getString("DISTRIBUTOR_OLM_ID")));
				distRecoDto.setDistName((rset.getString("DISTRIBUTOR_NAME")));
				distRecoDto.setTsmName((rset.getString("TSM_NAME")));
				distRecoDto.setCircleName((rset.getString("CIRCLE_NAME")));
				distRecoDto.setProductName((rset.getString("PRODUCT_NAME")));
				distRecoDto.setProductSerialNo(rset.getString("SERIAL_NO"));
				distRecoDto.setPoNo(rset.getString("PO_NO"));
				distRecoDto.setPoDate(rset.getDate("PO_DATE") != null ?Utility.converDateToString(rset.getDate("PO_DATE"),"dd/MM/yyyy"):"");
				distRecoDto.setDcNo(rset.getString("DC_No"));
				distRecoDto.setDcDate(rset.getDate("DC_DATE") != null ?Utility.converDateToString(rset.getDate("DC_DATE"),"dd/MM/yyyy"):"");
				distRecoDto.setDispatchedQtyPerDc(rset.getInt("DISPATCHED_QTY_PER_PO"));
				distRecoDto.setPoStatus(rset.getString("PO_STATUS"));
				}
				else if(tabId.equals("1") && columnId.equals("2")){
					distRecoDto.setDistOlmId((rset.getString("DISTRIBUTOR_OLM_ID")));
					distRecoDto.setDistName((rset.getString("DISTRIBUTOR_NAME")));
					distRecoDto.setTsmName((rset.getString("TSM_NAME")));
					distRecoDto.setCircleName((rset.getString("CIRCLE_NAME")));
					distRecoDto.setProductName((rset.getString("PRODUCT_NAME")));
					distRecoDto.setProductSerialNo(rset.getString("SERIAL_NO"));
					distRecoDto.setDcNo(rset.getString("DC_No"));
					distRecoDto.setDcDate(rset.getDate("DC_DATE") != null ?Utility.converDateToString(rset.getDate("DC_DATE"),"dd/MM/yyyy"):"");
					distRecoDto.setCollectionType(rset.getString("COLLECTION_NAME"));
					distRecoDto.setDispatchedQtyPerDc(rset.getInt("DISPATCHED_QTY_PER_DC"));
					distRecoDto.setDcStbStatus(rset.getString("DC_STB_STATUS"));
				}
				else if(tabId.equals("1") && columnId.equals("3")){
					distRecoDto.setDistOlmId((rset.getString("DISTRIBUTOR_OLM_ID")));
					distRecoDto.setDistName((rset.getString("DISTRIBUTOR_NAME")));
					distRecoDto.setTsmName((rset.getString("TSM_NAME")));
					distRecoDto.setCircleName((rset.getString("CIRCLE_NAME")));
					distRecoDto.setProductName((rset.getString("PRODUCT_NAME")));
					distRecoDto.setProductSerialNo(rset.getString("SERIAL_NO"));
					distRecoDto.setPrNo(rset.getString("PR_NO"));
					distRecoDto.setPoNo(rset.getString("PO_NO"));// Utility.converDateToString(rset.getDate("DISTRIBUTOR_PURCHASE_DATE"), "dd/MM/yyyy")
					distRecoDto.setDistPurchageDate(rset.getDate("DISTRIBUTOR_PURCHASE_DATE") != null ?Utility.converDateToString(rset.getDate("DISTRIBUTOR_PURCHASE_DATE"),"dd/MM/yyyy"):"");
					distRecoDto.setFscName(rset.getString("LOGIN_NAME"));
					distRecoDto.setFscPurchageDate(rset.getDate("FSE_PURCHASE_DATE") != null ?Utility.converDateToString(rset.getDate("FSE_PURCHASE_DATE"),"dd/MM/yyyy") :"");
					distRecoDto.setRetailerName(rset.getString("RETAILER_NAME"));
					distRecoDto.setRetailerPurchageDate(rset.getDate("RETAILER_PURCHASE_DATE") != null ?Utility.converDateToString(rset.getDate("RETAILER_PURCHASE_DATE"),"dd/MM/yyyy"):"");
					distRecoDto.setStatusDesc(rset.getString("STATUS_DESCRIPTION"));
				}
				else if(tabId.equals("1") && columnId.equals("4")){
					distRecoDto.setDistOlmId((rset.getString("DISTRIBUTOR_OLM_ID")));
					distRecoDto.setDistName((rset.getString("DISTRIBUTOR_NAME")));
					distRecoDto.setTsmName((rset.getString("TSM_NAME")));
					distRecoDto.setCircleName((rset.getString("CIRCLE_NAME")));
					distRecoDto.setProductName((rset.getString("PRODUCT_NAME")));
					distRecoDto.setCollectionType((rset.getString("COLLECTION_NAME")));
					distRecoDto.setCollectionDate(rset.getDate("COLLECTED_ON") != null ?Utility.converDateToString(rset.getDate("COLLECTED_ON"),"dd/MM/yyyy"):"");
					distRecoDto.setInventoryChangeDate(rset.getDate("INV_CHANGE_DATE") != null ?Utility.converDateToString(rset.getDate("INV_CHANGE_DATE"),"dd/MM/yyyy"):"");
					distRecoDto.setRecoveredStbNo(rset.getString("DEFECTIVE_SR_NO"));
					distRecoDto.setInstalledStbNo(rset.getString("NEW_SR_NO"));
					distRecoDto.setDefectType(rset.getString("DEFECT_NAME"));
					distRecoDto.setAging(rset.getString("AGEING"));
				}
				else if(tabId.equals("1") && columnId.equals("5")){
					distRecoDto.setDistOlmId((rset.getString("DISTRIBUTOR_OLM_ID")));
					distRecoDto.setDistName((rset.getString("DISTRIBUTOR_NAME")));
					distRecoDto.setTsmName((rset.getString("TSM_NAME")));
					distRecoDto.setCircleName((rset.getString("CIRCLE_NAME")));
					distRecoDto.setProductName((rset.getString("PRODUCT_NAME")));
					distRecoDto.setCollectionType((rset.getString("COLLECTION_NAME")));
					distRecoDto.setCollectionDate(rset.getDate("COLLECTED_ON") != null ?Utility.converDateToString(rset.getDate("COLLECTED_ON"),"dd/MM/yyyy"):"");
					distRecoDto.setInventoryChangeDate(rset.getDate("INV_CHANGE_DATE") != null ?Utility.converDateToString(rset.getDate("INV_CHANGE_DATE"),"dd/MM/yyyy"):"");
					distRecoDto.setRecoveredStbNo(rset.getString("DEFECTIVE_SR_NO"));
					distRecoDto.setInstalledStbNo(rset.getString("NEW_SR_NO"));
					distRecoDto.setDefectType(rset.getString("DEFECT_NAME"));
					distRecoDto.setAging(rset.getString("AGEING"));
				}
				else if(tabId.equals("1") && columnId.equals("6")){
					distRecoDto.setDistOlmId((rset.getString("DISTRIBUTOR_OLM_ID")));
					distRecoDto.setDistName((rset.getString("DISTRIBUTOR_NAME")));
					distRecoDto.setTsmName((rset.getString("TSM_NAME")));
					distRecoDto.setCircleName((rset.getString("CIRCLE_NAME")));
					distRecoDto.setProductName((rset.getString("PRODUCT_NAME")));
					distRecoDto.setCollectionType((rset.getString("COLLECTION_NAME")));
					distRecoDto.setCollectionDate(rset.getDate("COLLECTED_ON") != null ?Utility.converDateToString(rset.getDate("COLLECTED_ON"),"dd/MM/yyyy"):"");
					distRecoDto.setRecoveredStbNo(rset.getString("DEFECTIVE_SR_NO"));
					distRecoDto.setAging(rset.getString("AGEING"));
				}
				/* ***********  Received Stock  ************** */
				else if(tabId.equals("2") && columnId.equals("1")){
					distRecoDto.setDistOlmId((rset.getString("DISTRIBUTOR_OLM_ID")));
					distRecoDto.setDistName((rset.getString("DISTRIBUTOR_NAME")));
					distRecoDto.setTsmName((rset.getString("TSM_NAME")));
					distRecoDto.setCircleName((rset.getString("CIRCLE_NAME")));
					distRecoDto.setProductName((rset.getString("PRODUCT_NAME")));
					distRecoDto.setProductSerialNo(rset.getString("SERIAL_NO"));
					distRecoDto.setPoNo(rset.getString("PO_NO"));
					distRecoDto.setPoDate(rset.getDate("PO_DATE") != null ?Utility.converDateToString(rset.getDate("PO_DATE"),"dd/MM/yyyy"):"");
					distRecoDto.setDcNo(rset.getString("DC_No"));
					distRecoDto.setDcDate(rset.getDate("DC_DATE") != null ?Utility.converDateToString(rset.getDate("DC_DATE"),"dd/MM/yyyy"):"");
					distRecoDto.setPoStatus(rset.getString("PO_STATUS"));
					distRecoDto.setStbStatus(rset.getString("STATUS_DESCRIPTION"));
					distRecoDto.setLastPoAcceptanceDate(rset.getDate("LAST_PO_ACTION_DATE"));
				}
				else if(tabId.equals("2") && columnId.equals("2")){

					distRecoDto.setFromDistId(rset.getString("FROM_DIST_ID"));
					distRecoDto.setFromDistName((rset.getString("FROM_DIST_NAME")));
					distRecoDto.setToDistId(rset.getString("TO_DIST_ID"));
					distRecoDto.setToDistName((rset.getString("TO_DIST_NAME")));
					distRecoDto.setInitiatorName((rset.getString("INITIATOR_NAME")));
					distRecoDto.setInitiationDate((rset.getDate("INITIATION_DATE")));
					distRecoDto.setDcNo(rset.getString("DC_NO"));
					distRecoDto.setAcceptanceDate(rset.getDate("ACCEPTANCE_DATE"));
					distRecoDto.setTransferDate(rset.getDate("TRANSFER_DATE"));
					distRecoDto.setTransferType(rset.getString("TRANSFER_TYPE"));
					distRecoDto.setCircleName((rset.getString("CIRCLE_NAME")));
					distRecoDto.setProductName((rset.getString("PRODUCT_NAME")));
					distRecoDto.setProductSerialNo(rset.getString("SERIAL_NO"));
				}
				else if(tabId.equals("2") && columnId.equals("3")){
					distRecoDto.setFromDistId(rset.getString("FROM_DIST_ID"));
					distRecoDto.setFromDistName((rset.getString("FROM_DIST_NAME")));
					distRecoDto.setToDistId(rset.getString("TO_DIST_ID"));
					distRecoDto.setToDistName((rset.getString("TO_DIST_NAME")));
					distRecoDto.setInitiatorName((rset.getString("INITIATOR_NAME")));
					distRecoDto.setInitiationDate((rset.getDate("INITIATION_DATE")));
					distRecoDto.setTransferDate(rset.getDate("TRANSFER_DATE"));
					distRecoDto.setCollectionType(rset.getString("COLLECTION_NAME"));
					distRecoDto.setCollectionDate(rset.getDate("COLLECTION_DATE") != null ?Utility.converDateToString(rset.getDate("COLLECTION_DATE"),"dd/MM/yyyy"):"");
					distRecoDto.setInventoryChangeDate(rset.getDate("INV_CHANGE_DATE") != null ?Utility.converDateToString(rset.getDate("INV_CHANGE_DATE"),"dd/MM/yyyy"):"");
					distRecoDto.setDefectType(rset.getString("DEFECT_NAME"));
					distRecoDto.setCircleName((rset.getString("CIRCLE_NAME")));
					distRecoDto.setProductName((rset.getString("PRODUCT_NAME")));
					distRecoDto.setProductSerialNo(rset.getString("SERIAL_NO"));
				}
				else if(tabId.equals("2") && columnId.equals("4")){
					distRecoDto.setDistOlmId((rset.getString("DISTRIBUTOR_OLM_ID")));
					distRecoDto.setDistName((rset.getString("DISTRIBUTOR_NAME")));
					distRecoDto.setTsmName((rset.getString("TSM_NAME")));
					distRecoDto.setCircleName((rset.getString("CIRCLE_NAME")));
					distRecoDto.setProductName((rset.getString("PRODUCT_NAME")));
					distRecoDto.setCollectionType((rset.getString("COLLECTION_NAME")));
					distRecoDto.setInstalledStbNo(rset.getString("NEW_SR_NO"));
					distRecoDto.setRecoveredStbNo(rset.getString("OLD_SR_NO"));
					distRecoDto.setDefectType(rset.getString("DEFECT_NAME"));
					distRecoDto.setCollectionDate(rset.getDate("COLLECTION_DATE") != null ?Utility.converDateToString(rset.getDate("COLLECTION_DATE"),"dd/MM/yyyy"):"");
					distRecoDto.setInventoryChangeDate(rset.getDate("INV_CHANGE_DATE") != null ?Utility.converDateToString(rset.getDate("INV_CHANGE_DATE"),"dd/MM/yyyy"):"");
					
				}
				else if(tabId.equals("2") && columnId.equals("5")){
					distRecoDto.setDistOlmId((rset.getString("DISTRIBUTOR_OLM_ID")));
					distRecoDto.setDistName((rset.getString("DISTRIBUTOR_NAME")));
					distRecoDto.setTsmName((rset.getString("TSM_NAME")));
					distRecoDto.setCircleName((rset.getString("CIRCLE_NAME")));
					distRecoDto.setProductName((rset.getString("PRODUCT_NAME")));
					distRecoDto.setCollectionType((rset.getString("COLLECTION_NAME")));
					distRecoDto.setRecoveredStbNo(rset.getString("SERIAL_NUMBER"));
					distRecoDto.setCollectionDate(rset.getDate("COLLECTION_DATE") != null ?Utility.converDateToString(rset.getDate("COLLECTION_DATE"),"dd/MM/yyyy"):"");
					distRecoDto.setAging(rset.getString("AGEING"));
				}
				else if(tabId.equals("3")){  // Returned stock 
					// Ret. OK  calculation ,,// Ret. DOA BI  calculation,// Ret. DOA AI  calculation,// Ret. Churn  calculation,// Ret. Def. Swap calculation
					if( columnId.equals("4") || columnId.equals("5") || columnId.equals("6") || columnId.equals("8"))
					{
						distRecoDto.setDistOlmId((rset.getString("DISTRIBUTOR_OLM_ID")));
						distRecoDto.setDistName((rset.getString("DISTRIBUTOR_NAME")));
						distRecoDto.setTsmName((rset.getString("TSM_NAME")));
						distRecoDto.setCircleName((rset.getString("CIRCLE_NAME")));
						distRecoDto.setProductName((rset.getString("PRODUCT_NAME")));
						distRecoDto.setCollectionType((rset.getString("COLLECTION_NAME")));
						distRecoDto.setCollectionDate(rset.getDate("COLLECTED_ON") != null ?Utility.converDateToString(rset.getDate("COLLECTED_ON"),"dd/MM/yyyy"):"");
						distRecoDto.setRecoveredStbNo(rset.getString("SERIAL_NO"));
						distRecoDto.setDcNo(rset.getString("DC_NO"));
						distRecoDto.setDcDate(rset.getDate("DC_DATE") != null ?Utility.converDateToString(rset.getDate("DC_DATE"),"dd/MM/yyyy"):"");
						distRecoDto.setWhReceivedDate(rset.getDate("WH_RECEIVED_DATE"));
					}
					if(columnId.equals("1"))
					{
						distRecoDto.setDistOlmId((rset.getString("DISTRIBUTOR_OLM_ID")));
						distRecoDto.setDistName((rset.getString("DISTRIBUTOR_NAME")));
						distRecoDto.setTsmName((rset.getString("TSM_NAME")));
						distRecoDto.setCircleName((rset.getString("CIRCLE_NAME")));
						distRecoDto.setProductName((rset.getString("PRODUCT_NAME")));
						distRecoDto.setCollectionType((rset.getString("COLLECTION_NAME")));
					
						distRecoDto.setRecoveredStbNo(rset.getString("SERIAL_NO"));
						distRecoDto.setDcNo(rset.getString("DC_NO"));
						distRecoDto.setDcDate(rset.getDate("DC_DATE") != null ?Utility.converDateToString(rset.getDate("DC_DATE"),"dd/MM/yyyy"):"");
						distRecoDto.setWhReceivedDate(rset.getDate("WH_RECEIVED_DATE"));
						distRecoDto.setDcStbStatus(rset.getString("DC_STB_STATUS"));
						distRecoDto.setStatusDesc(rset.getString("DC_STATUS"));
					}
					else if(columnId.equals("2"))
					{
						distRecoDto.setFromDistId(rset.getString("FROM_DIST_ID"));
						distRecoDto.setFromDistName((rset.getString("FROM_DIST_NAME")));
						distRecoDto.setToDistId(rset.getString("TO_DIST_ID"));
						distRecoDto.setToDistName((rset.getString("TO_DIST_NAME")));
						distRecoDto.setInitiatorName((rset.getString("INITIATOR_NAME")));
						distRecoDto.setInitiationDate((rset.getDate("INITIATION_DATE")));
						distRecoDto.setTransferType(rset.getString("TRANSFER_TYPE"));
						distRecoDto.setTransferDate(rset.getDate("TRANSFER_DATE"));
						distRecoDto.setDcNo(rset.getString("DC_NO"));
						distRecoDto.setCircleName((rset.getString("CIRCLE_NAME")));
						distRecoDto.setProductName((rset.getString("PRODUCT_NAME")));
						distRecoDto.setProductSerialNo(rset.getString("SERIAL_NO"));
						distRecoDto.setAcceptanceDate(rset.getDate("ACCEPTANCE_DATE"));
						
					}
					else if(columnId.equals("3"))
					{
						distRecoDto.setFromDistId(rset.getString("FROM_DIST"));
						distRecoDto.setFromDistName((rset.getString("FROM_DIST_NAME")));
						distRecoDto.setToDistId(rset.getString("TO_DIST"));
						distRecoDto.setToDistName((rset.getString("TO_DIST_NAME")));
						distRecoDto.setInitiatorName((rset.getString("INITIATOR_NAME")));
						distRecoDto.setCollectionType(rset.getString("COLLECTION_NAME"));
						distRecoDto.setProductName((rset.getString("PRODUCT_NAME")));
						distRecoDto.setCollectionDate(rset.getDate("COLLECTED_DATE") != null ?Utility.converDateToString(rset.getDate("COLLECTED_DATE"),"dd/MM/yyyy"):"");
						distRecoDto.setInventoryChangeDate(rset.getDate("INVENTORY_CHANGE_DATE") != null ?Utility.converDateToString(rset.getDate("INVENTORY_CHANGE_DATE"),"dd/MM/yyyy"):"");
						distRecoDto.setRecoveredStbNo(rset.getString("SERIAL_NO"));
						distRecoDto.setDefectType(rset.getString("DEFECT_NAME"));
						distRecoDto.setInitiationDate((rset.getDate("INITIATION_DATE")));
						distRecoDto.setTransferDate(rset.getDate("TRANSFER_DATE"));
					}
					else if(columnId.equals("7"))
					{
						distRecoDto.setDistOlmId((rset.getString("DISTRIBUTOR_OLM_ID")));
						distRecoDto.setDistName((rset.getString("DISTRIBUTOR_NAME")));
						distRecoDto.setTsmName((rset.getString("TSM_NAME")));
						distRecoDto.setCircleName((rset.getString("CIRCLE_NAME")));
						distRecoDto.setProductName((rset.getString("PRODUCT_NAME")));
						distRecoDto.setCollectionType((rset.getString("COLLECTION_NAME")));
						distRecoDto.setDcDate(rset.getDate("DC_DATE") != null ?Utility.converDateToString(rset.getDate("DC_DATE"),"dd/MM/yyyy"):"");
						distRecoDto.setRecoveredStbNo(rset.getString("SERIAL_NUMBER"));
						distRecoDto.setDcNo(rset.getString("DC_NO"));
						//distRecoDto.setDcDate(rset.get("DC_DATE"));
						distRecoDto.setWhReceivedDate(rset.getDate("WH_RECEIVED_DATE"));
					}
						
					
				}
				else if(tabId.equals("4"))  // Activated stocked
				{
					if(columnId.equals("1"))
					{
						distRecoDto.setDistOlmId((rset.getString("DISTRIBUTOR_OLM_ID")));
						distRecoDto.setDistName((rset.getString("DISTRIBUTOR_NAME")));
						distRecoDto.setTsmName((rset.getString("TSM_NAME")));
						distRecoDto.setCircleName((rset.getString("CIRCLE_NAME")));
						distRecoDto.setProductName((rset.getString("PRODUCT_NAME")));
						distRecoDto.setFscName(rset.getString("FSE_NAME"));
						distRecoDto.setRetailerName(rset.getString("RETAILER_NAME"));
						distRecoDto.setActivatedSerialNo(rset.getString("SERIAL_NO"));
						distRecoDto.setPrNo(rset.getString("PR_NO"));
						distRecoDto.setPoNo(rset.getString("PO_NO"));
						distRecoDto.setAcceptanceDate(rset.getDate("ACCEPT_DATE"));
						distRecoDto.setActivationDate(rset.getDate("ACTIVATION_DATE"));
						distRecoDto.setStbStatus(rset.getString("STB_STATUS"));
					}
					else if(columnId.equals("2"))
					{
						distRecoDto.setDistOlmId((rset.getString("DISTRIBUTOR_OLM_ID")));
						distRecoDto.setDistName((rset.getString("DISTRIBUTOR_NAME")));
						distRecoDto.setTsmName((rset.getString("TSM_NAME")));
						distRecoDto.setCircleName((rset.getString("CIRCLE_NAME")));
						distRecoDto.setProductName((rset.getString("PRODUCT_NAME")));
						distRecoDto.setRecoveredStbNo(rset.getString("OLD_SR_NO"));
						distRecoDto.setInstalledStbNo(rset.getString("NEW_SR_NO"));
						distRecoDto.setCollectionType(rset.getString("COLLECTION_NAME"));
						distRecoDto.setCollectionDate(rset.getDate("COLLECTION_DATE") != null ?Utility.converDateToString(rset.getDate("COLLECTION_DATE"),"dd/MM/yyyy"):"");//Neetika
						distRecoDto.setInventoryChangeDate(rset.getDate("INV_CHANGE_DATE") != null ?Utility.converDateToString(rset.getDate("INV_CHANGE_DATE"),"dd/MM/yyyy"):"");
						distRecoDto.setDefectType(rset.getString("DEFECT_NAME"));
						distRecoDto.setAging(rset.getString("AGEING"));//Neetika
					}
					else if(columnId.equals("3"))
					{
						distRecoDto.setDistName(rset.getString("DIST_NAME"));
						distRecoDto.setCircleName(rset.getString("CIRCLE_NAME"));
						distRecoDto.setProductName(rset.getString("PRODCUT_NAME"));
						distRecoDto.setRecoveredStbNo(rset.getString("SR_NO"));
						distRecoDto.setPrNo(rset.getString("PR_NO"));
						distRecoDto.setPoNo(rset.getString("PO_NO"));
						distRecoDto.setFscName(rset.getString("FSE_NAME"));
						distRecoDto.setRetailerName(rset.getString("RETAILER_NAME"));
						distRecoDto.setDistPurchageDate(rset.getDate("DISTRIBUTOR_PURCHASE_DATE") != null ?Utility.converDateToString(rset.getDate("DISTRIBUTOR_PURCHASE_DATE"),"dd/MM/yyyy"):"");
						distRecoDto.setFscPurchageDate(rset.getDate("FSE_PURCHASE_DATE") != null ?Utility.converDateToString(rset.getDate("FSE_PURCHASE_DATE"),"dd/MM/yyyy") :"");
						distRecoDto.setRetailerPurchageDate(rset.getDate("RETAILER_PURCHASE_DATE") != null ?Utility.converDateToString(rset.getDate("RETAILER_PURCHASE_DATE"),"dd/MM/yyyy"):"");
						distRecoDto.setInstalledStbNo(rset.getString("NEW_SR_NO"));
						distRecoDto.setNewProductName(rset.getString("NEW_PRODUCT_NAME"));
						distRecoDto.setCollectionDate(rset.getDate("COLLECTED_ON") != null ?Utility.converDateToString(rset.getDate("COLLECTED_ON"),"dd/MM/yyyy"):"");
						distRecoDto.setInventoryChangeDate(rset.getDate("INV_CHANGE_DATE") != null ?Utility.converDateToString(rset.getDate("INV_CHANGE_DATE"),"dd/MM/yyyy"):"");
						distRecoDto.setDefectType(rset.getString("DEFECT_NAME"));
					}
					
				}
				else if(tabId.equals("5"))  // Closing stocked
				{
					if(columnId.equals("1"))// Serialized stock
					{
						distRecoDto.setDistOlmId((rset.getString("DISTRIBUTOR_OLM_ID")));
						distRecoDto.setDistName((rset.getString("DISTRIBUTOR_NAME")));
						distRecoDto.setTsmName((rset.getString("TSM_NAME")));
						distRecoDto.setCircleName((rset.getString("CIRCLE_NAME")));
						distRecoDto.setProductName((rset.getString("PRODUCT_NAME")));
						distRecoDto.setProductSerialNo(rset.getString("SERIAL_NO"));
						distRecoDto.setPrNo(rset.getString("PR_NO"));
						distRecoDto.setPoNo(rset.getString("PO_NO"));
						//Utility.converDateToString(rset.getDate("DISTRIBUTOR_PURCHASE_DATE"), "dd/MM/yyyy")
						distRecoDto.setDistPurchageDate(rset.getDate("DISTRIBUTOR_PURCHASE_DATE") != null ?Utility.converDateToString(rset.getDate("DISTRIBUTOR_PURCHASE_DATE"),"dd/MM/yyyy"):"");
						distRecoDto.setFscName(rset.getString("LOGIN_NAME"));
						distRecoDto.setFscPurchageDate(rset.getDate("FSE_PURCHASE_DATE") != null ?Utility.converDateToString(rset.getDate("FSE_PURCHASE_DATE"),"dd/MM/yyyy") :"");
						distRecoDto.setRetailerName(rset.getString("RETAILER_ID"));
						distRecoDto.setRetailerPurchageDate(rset.getDate("RETAILER_PURCHASE_DATE") != null ?Utility.converDateToString(rset.getDate("RETAILER_PURCHASE_DATE"),"dd/MM/yyyy"):"");
						distRecoDto.setStatusDesc(rset.getString("STATUS_DESCRIPTION"));
					}
					else if(columnId.equals("2"))// defective closing stock
					{
						distRecoDto.setDistOlmId((rset.getString("DISTRIBUTOR_OLM_ID")));
						distRecoDto.setDistName((rset.getString("DISTRIBUTOR_NAME")));
						distRecoDto.setTsmName((rset.getString("TSM_NAME")));
						distRecoDto.setCircleName((rset.getString("CIRCLE_NAME")));
						distRecoDto.setProductName((rset.getString("PRODUCT_NAME")));
						distRecoDto.setCollectionType((rset.getString("COLLECTION_NAME")));
						distRecoDto.setCollectionDate(rset.getDate("COLLECTED_ON") != null ?Utility.converDateToString(rset.getDate("COLLECTED_ON"),"dd/MM/yyyy"):"");
						distRecoDto.setInventoryChangeDate(rset.getDate("INV_CHANGE_DATE") != null ?Utility.converDateToString(rset.getDate("INV_CHANGE_DATE"),"dd/MM/yyyy"):"");
						distRecoDto.setRecoveredStbNo(rset.getString("DEFECTIVE_SR_NO"));
						distRecoDto.setInstalledStbNo(rset.getString("NEW_SR_NO"));
						distRecoDto.setDefectType(rset.getString("DEFECT_NAME"));
						distRecoDto.setAging(rset.getString("AGEING"));
					}
					else if(columnId.equals("3"))// upgrade closing stock
					{
						distRecoDto.setDistOlmId((rset.getString("DISTRIBUTOR_OLM_ID")));
						distRecoDto.setDistName((rset.getString("DISTRIBUTOR_NAME")));
						distRecoDto.setTsmName((rset.getString("TSM_NAME")));
						distRecoDto.setCircleName((rset.getString("CIRCLE_NAME")));
						distRecoDto.setProductName((rset.getString("PRODUCT_NAME")));
						distRecoDto.setCollectionType((rset.getString("COLLECTION_NAME")));
						distRecoDto.setCollectionDate(rset.getDate("COLLECTED_ON") != null ?Utility.converDateToString(rset.getDate("COLLECTED_ON"),"dd/MM/yyyy"):"");
						distRecoDto.setInventoryChangeDate(rset.getDate("INV_CHANGE_DATE") != null ?Utility.converDateToString(rset.getDate("INV_CHANGE_DATE"),"dd/MM/yyyy"):"");
						distRecoDto.setRecoveredStbNo(rset.getString("DEFECTIVE_SR_NO"));
						distRecoDto.setInstalledStbNo(rset.getString("NEW_SR_NO"));
						distRecoDto.setDefectType(rset.getString("DEFECT_NAME"));
						distRecoDto.setAging(rset.getString("AGEING"));
					}
					else if(columnId.equals("4"))// Churned closing stock
					{
						distRecoDto.setDistOlmId((rset.getString("DISTRIBUTOR_OLM_ID")));
						distRecoDto.setDistName((rset.getString("DISTRIBUTOR_NAME")));
						distRecoDto.setTsmName((rset.getString("TSM_NAME")));
						distRecoDto.setCircleName((rset.getString("CIRCLE_NAME")));
						distRecoDto.setProductName((rset.getString("PRODUCT_NAME")));
						distRecoDto.setCollectionType((rset.getString("COLLECTION_NAME")));
						distRecoDto.setCollectionDate(rset.getDate("COLLECTED_ON") != null ?Utility.converDateToString(rset.getDate("COLLECTED_ON"),"dd/MM/yyyy"):"");
						distRecoDto.setRecoveredStbNo(rset.getString("DEFECTIVE_SR_NO"));
						distRecoDto.setAging(rset.getString("AGEING"));
					}
					else if(columnId.equals("5"))// Pending PO closing stock
					{
						distRecoDto.setDistOlmId((rset.getString("DISTRIBUTOR_OLM_ID")));
						distRecoDto.setDistName((rset.getString("DISTRIBUTOR_NAME")));
						distRecoDto.setTsmName((rset.getString("TSM_NAME")));
						distRecoDto.setCircleName((rset.getString("CIRCLE_NAME")));
						distRecoDto.setProductName((rset.getString("PRODUCT_NAME")));
						distRecoDto.setProductSerialNo(rset.getString("SERIAL_NO"));
						distRecoDto.setPoNo(rset.getString("PO_NO"));
						distRecoDto.setPoDate(rset.getDate("PO_DATE") != null ?Utility.converDateToString(rset.getDate("PO_DATE"),"dd/MM/yyyy"):"");
						distRecoDto.setDcNo(rset.getString("DC_No"));
						distRecoDto.setDcDate(rset.getDate("DC_DATE") != null ?Utility.converDateToString(rset.getDate("DC_DATE"),"dd/MM/yyyy"):"");
						distRecoDto.setPoStatus(rset.getString("PO_STATUS"));
						distRecoDto.setDispatchedQtyPerDc(rset.getInt("DISPATCHED_QTY_PER_PO"));
					}
					else if(columnId.equals("6"))// Pending DC closing stock
					{
						distRecoDto.setDistOlmId((rset.getString("DISTRIBUTOR_OLM_ID")));
						distRecoDto.setDistName((rset.getString("DISTRIBUTOR_NAME")));
						distRecoDto.setTsmName((rset.getString("TSM_NAME")));
						distRecoDto.setCircleName((rset.getString("CIRCLE_NAME")));
						distRecoDto.setProductName((rset.getString("PRODUCT_NAME")));
						distRecoDto.setProductSerialNo(rset.getString("SERIAL_NO"));
						distRecoDto.setDcNo(rset.getString("DC_No"));
						distRecoDto.setDcDate(rset.getDate("DC_DATE") != null ?Utility.converDateToString(rset.getDate("DC_DATE"),"dd/MM/yyyy"):"");
						distRecoDto.setCollectionType(rset.getString("COLLECTION_NAME"));
						distRecoDto.setDispatchedQtyPerDc(rset.getInt("DISPATCHED_QTY_PER_DC"));
						distRecoDto.setDcStbStatus(rset.getString("DC_STB_STATUS"));
					}
				}
				detailsList.add(distRecoDto);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			DBConnectionManager.releaseResources(pstmt, rset);
			DBConnectionManager.releaseResources(connection);
		}
		return detailsList;
	}
	public String uploadClosingStockDetailsXls(FormFile file,int stockType,int productId,Long distId,String indexes,int recoId) throws DPServiceException
	{

		String excelMessage = null;
		Connection connection=null;
		PreparedStatement pstmt=null;
		PreparedStatement pstmtdel=null;
		int deleted=0;
		try
		{
			Iterator<Row> rowIterator = FileHelpers.readExcelFile(file);
			Iterator<Row> rowIterator2 = FileHelpers.readExcelFile(file); //Neetika on 21 May
			Iterator<Row> rowIteratorInsert = FileHelpers.readExcelFile(file);
			connection = DBConnectionManager.getDBConnection();
			 connection.setAutoCommit(false);
			   
			  // ********************commenting by ram *******************
			  /*if(totalrows > Integer.parseInt(ResourceReader.getValueFromBundle(com.ibm.virtualization.recharge.common.Constants.BULK_UPLAOD_MAX_LIMIT , com.ibm.virtualization.recharge.common.Constants.WEBSERVICE_RESOURCE_BUNDLE)))
			  {
				 
				  nSBulkUploadErrDTO = new NSBulkUploadDto();
				  nSBulkUploadErrDTO.setErr_msg("Limit exceeds: Maximum "+Integer.parseInt(ResourceReader.getValueFromBundle(com.ibm.virtualization.recharge.common.Constants.BULK_UPLAOD_MAX_LIMIT , com.ibm.virtualization.recharge.common.Constants.WEBSERVICE_RESOURCE_BUNDLE))+" Serial Numbers are allowed in a file.");
        		  list.add(nSBulkUploadErrDTO);
        		  return list;
			  }*/
			  // *******************commenting by ram  end*****************
			  
			 /* String checkuploadedFlag = validateAlreadyUploadedCount(totalrows);
			  
			  if((checkuploadedFlag.equalsIgnoreCase("false")))
			  {
				  return "Limit exceeds: Maximum "+Integer.parseInt(ResourceReader.getValueFromBundle(com.ibm.virtualization.recharge.common.Constants.BULK_UPLAOD_MAX_LIMIT , com.ibm.virtualization.recharge.common.Constants.WEBSERVICE_RESOURCE_BUNDLE))+" Serial Numbers are allowed to upload in a day.";
			  }*/
			  
			  int rowNumber = 1;
			  int columnIndexDistFlag = 0;
        	  int columnIndexDistRemarks = 0;
        	  int  columnIndexserialNo = 0;
        	  int columnIndexOtherRemarks = 0;
        	  String distFlag=null;
        	  String distRemarks=null;
        	  String otherRemarks=null;
        	  String serialNo=null;
        	  String indexArr[]=null;
        	  indexArr = indexes.split(",");
        	  columnIndexserialNo = Integer.parseInt(indexArr[0]);
    		  columnIndexDistFlag = Integer.parseInt(indexArr[1]);
    		  columnIndexDistRemarks = Integer.parseInt(indexArr[2]);
    		  columnIndexOtherRemarks = Integer.parseInt(indexArr[3]);
    		  //added by Beeru
    		  logger.info("columnIndexserialNo :"+columnIndexserialNo+" columnIndexDistFlag:"+columnIndexDistFlag+"  columnIndexDistRemarks:"+columnIndexDistRemarks+" rownumber :"+rowNumber);
    		String excelMsg  =  excelValidationOfClosingStock(productId, stockType, distId, recoId, rowIterator, columnIndexserialNo,columnIndexDistFlag);
    		if(excelMsg !=null && excelMsg != "") {
    			return excelMsg;
    			}
    			else //Neetika 21 May Remarks validation separated from other code so that if wrong product file uploaded then message should be some error occured check the file and not that flag is wrong
    			{
    				excelMsg  =  uploadClosingStockDetailsXlsRemarks(productId, stockType, distId, recoId, rowIterator2, columnIndexserialNo,columnIndexDistFlag);
    				if(excelMsg !=null && excelMsg != "") {
    	    			return excelMsg;
    			}
    			}
    		
    		
    		
   		 pstmtdel=connection.prepareStatement("DELETE FROM RECO_CLOSINGSTOCK_TEMP WHERE DIST_ID= ? and RECO_PERIOD_ID= ? and PRODUCT_ID= ? and stock_type  = ? WITH UR");
   		 pstmtdel.setLong(1, distId);
   		 pstmtdel.setInt(2, recoId);
   		 pstmtdel.setInt(3, productId);
   		 pstmtdel.setInt(4,stockType);
   		 deleted=pstmtdel.executeUpdate();
   		
    		
    		//added by Beeru
			  while (rowIteratorInsert.hasNext()) {
				 // distRecoDto = new DistRecoDto();
				  Row row 	  = rowIteratorInsert.next();
				  Cell tempcells   = row.getCell(columnIndexserialNo);
	              Cell cell   = null;
	        	  Iterator<Cell> cells = row.cellIterator();
	        	  /***********************/
        		  if(tempcells == null || tempcells.getCellType() == Cell.CELL_TYPE_NUMERIC && tempcells.getNumericCellValue() <= 0 ) {
        		  break;
        		  }else if( tempcells == null || tempcells.getCellType() == Cell.CELL_TYPE_STRING && (tempcells.getStringCellValue() ==null || tempcells.getStringCellValue()=="") ){
        			  break;
        		  }
        	 
	        	  /***********************/
	        	  if(cells != null ){
	        	  while (cells.hasNext()) {
	        		 
	        	  if(rowNumber == 1)
	        	  {
	        		  cell = cells.next();
	        		  
	        	  }
	        	  else 
	        		  if(rowNumber > 1){
	            	  cell =  cells.next();
	            	  //columnIndex = cell.getColumnIndex();
	            	 
	            	  if(cell.getColumnIndex() == columnIndexDistFlag)
	            	  {
	            		  
	            		  if(cell.getCellType() == Cell.CELL_TYPE_STRING)
	            		  {
	            			  distFlag=cell.getStringCellValue();
	            			  if(distFlag.equalsIgnoreCase("yes")){
	            				  distFlag ="Y";  
	            			  }
	            			  else if(distFlag.equalsIgnoreCase("no"))
	            			  {
	            				  distFlag ="N";  
	            			  }
	            		  }  
	            		  if(cell.getCellType() == Cell.CELL_TYPE_NUMERIC)
	            			  distFlag=String.valueOf(cell.getNumericCellValue());
	            	  }
	            	 if(cell.getColumnIndex() == columnIndexDistRemarks)
	            	  {
	            		 if(cell.getCellType() == Cell.CELL_TYPE_STRING)
	            			  	distRemarks=cell.getStringCellValue();
	            		  if(cell.getCellType() == Cell.CELL_TYPE_NUMERIC)
	            			  distRemarks=String.valueOf(cell.getNumericCellValue());
	            	  }
	            	   if(cell.getColumnIndex() == columnIndexserialNo)
	            	  {
	            		  if(cell.getCellType() == Cell.CELL_TYPE_STRING)
	            			  serialNo=cell.getStringCellValue();
	            		  if(cell.getCellType() == Cell.CELL_TYPE_NUMERIC)
	            			  serialNo=String.valueOf(cell.getNumericCellValue());
	            	  }
	            	   if(cell.getColumnIndex() == columnIndexOtherRemarks)
		            	  {
		            		  if(cell.getCellType() == Cell.CELL_TYPE_STRING)
		            			  otherRemarks=cell.getStringCellValue();
		            		  if(cell.getCellType() == Cell.CELL_TYPE_NUMERIC)
		            			  otherRemarks=String.valueOf(cell.getNumericCellValue());
		            	  }
	        	  }
			  }
	        	  //logger.info("data to be inserted :distFlag:"+distFlag+" distRemarks:"+distRemarks+" serialNo:"+serialNo);
	        	 // logger.info("******* going to insert into temp table **********************");
	        	  if(rowNumber > 1){
	        		  // Inserting in temp table logic is Commented by Ram 
	        		  //logger.info("******* inserting into temp table **********************");
	        		  logger.info(" Inserting in table :serialNo:"+serialNo+" distId:"+distId+" productId:"+productId+" stockType :"+stockType+" distFlag:"+distFlag+"distRemarks :"+distRemarks);
	        	  	 pstmt=connection.prepareStatement("INSERT INTO DPDTH.RECO_CLOSINGSTOCK_TEMP(SERIAL_NO, DIST_ID, PRODUCT_ID, STOCK_TYPE, CREATED_ON, DIST_FLAG, DIST_REMARKS,RECO_PERIOD_ID, OTHER_REMARKS) VALUES(?, ?, ?, ?, current timestamp, ?, ?,?,?)");
	            	 pstmt.setString(1, serialNo);
	            	 pstmt.setLong(2, Integer.parseInt(distId.toString()));
	            	 pstmt.setInt(3, productId);
	            	 pstmt.setInt(4, stockType);
	            	 pstmt.setString(5,distFlag);
	            	 pstmt.setString(6, distRemarks);
	            	 pstmt.setInt(7, recoId);
	            	 pstmt.setString(8, otherRemarks);
	            	 int  insertedRows = pstmt.executeUpdate();
	            	 connection.commit();
	            	 pstmt.clearParameters();
	            	 logger.info("*******successfully inserted into temp table **********************");
	        	  }
			  }
        	 
	        	 rowNumber++;
	        	 }
	      }
	    
		catch (Exception e) {
			/*distRecoDto = new DistRecoDto();
			distRecoDto.setErr_msg("Invalid File Uploaded.");
        	List list2 = new ArrayList();
            list2.add(nSBulkUploadErrDTO);
            logger.info(e);
          		return list2;*/
			logger.info("Exception in daoimpl :::::::::::::");
			e.printStackTrace();
			try
			{
			connection.rollback();
			}
			catch(Exception ex)
			{
				ex.printStackTrace();
			}
			logger.info("Exception occured :"+e.getMessage());
			e.printStackTrace();
			return null;
        	
        }finally
		{
        	try
        	{
        	 connection.setAutoCommit(true);
        	}
        	catch(Exception ex)
        	{
        		
        	}
        		DBConnectionManager.releaseResources(connection);
        		DBConnectionManager.releaseResources(pstmt,null);
        		DBConnectionManager.releaseResources(pstmtdel,null);
        					
		}
		return null;
	}
	//added by vishwas on 21/03/14
	public String findNotOkStbs(Long distId,int recoId,String productId) throws DPServiceException
	{
		logger.info("findNotOkStbs in daoimpl ::::::"+recoId+" productId "+productId);

		Connection connection=null;
		PreparedStatement pstmt=null; 
		PreparedStatement pstmt11=null;
		PreparedStatement pstmt4=null;
		String result2="norecord"; 
		String result=null;
		 ResultSet rset=null;
		 ResultSet rset1=null;
		 ResultSet rset2=null;
		 ResultSet rset3=null;
		 ResultSet rset4=null;
		 List al=new ArrayList<Integer>();
		 int  ser=0;int  def=0;int  upgrade=0;int  churn=0;
		
		 StringBuffer sb=new StringBuffer("");
		 try
		 {
			 
			
			 connection = DBConnectionManager.getDBConnection();
			 
			 pstmt11=connection.prepareStatement("select SER_CLOSING_STOCK, def_closing_stock,upgrade_closing_stock,churn_closing_stock from DP_RECO_DIST_DETAILS where PRODUCT_ID=? and DIST_ID=? and CREATED_BY=0 and RECO_ID=?  with ur");
			 pstmt11.setInt(1,(Integer.parseInt(String.valueOf(productId))));
			 pstmt11.setInt(2, (Integer.parseInt(String.valueOf(distId))));
			 pstmt11.setInt(3, recoId);
			 rset=pstmt11.executeQuery();
		
			 while(rset.next())
			 {
				 ser=rset.getInt(1);
				 def=rset.getInt(2);
				 upgrade=rset.getInt(3);
				 churn=rset.getInt(4);
					
			 }
			 al.add(ser);
			 al.add(def);
			 al.add(upgrade);
			 al.add(churn);
			 
			 for(int i=0;i<al.size();i++)
			 {
			 if(Integer.parseInt(al.get(i).toString())!=0)
			 {
				 String query="SELECT * FROM RECO_CLOSINGSTOCK_TEMP WHERE DIST_ID= ? and RECO_PERIOD_ID= ? and PRODUCT_ID= ? and stock_type  = "+(i+1)+" with ur";
				 //logger.info("checking temp table ::::query::::::::11:"+query);
				 pstmt=connection.prepareStatement(query);
				 pstmt.setInt(1,(Integer.parseInt(String.valueOf(distId))));
				 pstmt.setInt(2, recoId);
				 pstmt.setInt(3, (Integer.parseInt(String.valueOf(productId))));
				 rset1=pstmt.executeQuery();
			
				 if(!rset1.next())
				 {
					 //logger.info("checking temp table ::::result::::11:"+String.valueOf(i+1));
					 return result=String.valueOf(i+1);
					 					
				 }
 
			 }
			 }


			

			 pstmt4=connection.prepareStatement("SELECT SERIAL_NO FROM RECO_CLOSINGSTOCK_TEMP WHERE DIST_ID= ? and RECO_PERIOD_ID=? and UPPER(TRIM(DIST_FLAG))='N' and PRODUCT_ID= ? with ur");
			 pstmt4.setInt(1,(Integer.parseInt(String.valueOf(distId))));
			 pstmt4.setInt(2, recoId);
			 pstmt4.setInt(3, (Integer.parseInt(String.valueOf(productId))));
			 rset=pstmt4.executeQuery();
			 while(rset.next())
			 {
			
				 sb=sb.append(rset.getString("SERIAL_NO")+",");
			 }
		 }
		 catch(Exception  ex)
		 {
			 logger.info("Exceptionn :"+ex.getMessage());
			 ex.printStackTrace();
		 }
		 logger.info("returning form daoimpl  :"+sb.toString());
		if(sb.length()>0)
		{
		 return sb.toString();		
		}
		else
		{
			return result2;
		}
		}
	//end by vishwas
//added by vishwas
	public String Uploadvalidation(Long distId,int recoId,String productId,int coloumn ,String okWithSystemStock) throws DPServiceException
	{
		
		Connection connection=null;
		 PreparedStatement pstmt=null;
		 PreparedStatement pstmt1=null;
		 PreparedStatement pstmt2=null;
		 PreparedStatement pstmt3=null;
		 PreparedStatement pstmt4=null;
		 String result="empty";
		 ResultSet rset=null;
		 ResultSet rset1=null;
		 ResultSet rset2=null;
		 ResultSet rset3=null;
		 ResultSet rset4=null;
		 int count=0;
		
		 try
		 {
			 
			
			 connection = DBConnectionManager.getDBConnection();
			 logger.info("Distributor  ==  "+distId+"Uploadvalidation === coloumn "+coloumn);
			//added by beeru 16 Apr 2014
			 if(okWithSystemStock !=null && okWithSystemStock.equals("5")) { // for ok with System generated clasing stock
			 String distFlag = ResourceReader.getCoreResourceBundleValue("CLOSING.DISTFLAG");
			 String distRemarks = ResourceReader.getCoreResourceBundleValue("CLOSING.DISTREMARKS");
			 pstmt=connection.prepareStatement("DELETE FROM RECO_CLOSINGSTOCK_TEMP WHERE DIST_ID= ? and RECO_PERIOD_ID= ? and PRODUCT_ID= ? and stock_type  = ? WITH UR");
			 pstmt.setLong(1, distId);
			 pstmt.setInt(2, recoId);
			 pstmt.setInt(3, (Integer.parseInt(String.valueOf(productId))));
			 pstmt.setInt(4,coloumn);
			 int rsetdelete=pstmt.executeUpdate();
			
			 pstmt=connection.prepareStatement("INSERT INTO RECO_CLOSINGSTOCK_TEMP(SERIAL_NO, DIST_ID, PRODUCT_ID, RECO_PERIOD_ID ,STOCK_TYPE, DIST_FLAG, DIST_REMARKS,CREATED_ON) VALUES(?, ?, ?, ?, ?, ?,?,current timestamp)");
			 pstmt.setString(1, Constants.CLOSING_DEFAULT_SERIAL_NO);
        	 pstmt.setLong(2, Integer.parseInt(distId.toString()));
        	 pstmt.setInt(3, (Integer.parseInt(String.valueOf(productId))));
        	 pstmt.setInt(4, recoId);
        	 pstmt.setInt(5, coloumn);
			 pstmt.setString(6,distFlag);
        	 pstmt.setString(7, distRemarks);
        	 int  insertedRows = pstmt.executeUpdate();
        	 connection.commit();
        	 return "success";
			 }
			 //end by beeru 16 Apr 2014
			 
if(coloumn==1)
{
			
			 pstmt=connection.prepareStatement("SELECT count(*) FROM RECO_CLOSINGSTOCK_TEMP WHERE DIST_ID= ? and RECO_PERIOD_ID= ? and PRODUCT_ID= ? and stock_type  = ? with ur");

			
			 pstmt.setInt(1,(Integer.parseInt(String.valueOf(distId))));

			 pstmt.setInt(2, recoId);
			 pstmt.setInt(3, (Integer.parseInt(String.valueOf(productId))));
			 
			 pstmt.setInt(4, coloumn);

			 rset1=pstmt.executeQuery();
			 
			// logger.info("-----------------in uploadClosingStockDetailsUploadvalidation-------4----------"+rset1.next());
			 if(rset1.next())
			 {

				 
				   count=rset1.getInt(1);
					  if(count>0)
					  {
					  result="1";
					  }
		
			 }
			//this else is used for updation when data is submited 
}			  
			 
			 //this else is used before the data submited in tem table and before the updating main table
if(coloumn==2)
{
			 pstmt1=connection.prepareStatement("SELECT count(*) FROM RECO_CLOSINGSTOCK_TEMP WHERE DIST_ID= ? and RECO_PERIOD_ID= ? and PRODUCT_ID= ? and stock_type  = 2 with ur");
			 pstmt1.setInt(1,(Integer.parseInt(String.valueOf(distId))));
			 pstmt1.setInt(2, recoId);
			 pstmt1.setInt(3, (Integer.parseInt(String.valueOf(productId))));
			 rset2=pstmt1.executeQuery();
			 if(rset2.next())
			 {
				 
				   count=rset2.getInt(1);
				  if(count>0)
				  {
				  result="2";
				  }
		
			 }
			 
}			 
if(coloumn==3){
			 pstmt2=connection.prepareStatement("SELECT count(*) FROM RECO_CLOSINGSTOCK_TEMP WHERE DIST_ID= ? and RECO_PERIOD_ID= ? and PRODUCT_ID= ? and stock_type  = 3 with ur");
			 pstmt2.setInt(1,(Integer.parseInt(String.valueOf(distId))));
			 pstmt2.setInt(2, recoId);
			 pstmt2.setInt(3, (Integer.parseInt(String.valueOf(productId))));
			 rset3=pstmt2.executeQuery();
			 if(rset3.next())
			 {
				
				 count=rset3.getInt(1);
				  if(count>0)
				  {
				  result="3";
				  }
		
			 }
			  
}
if(coloumn==4)
{
			 pstmt3=connection.prepareStatement("SELECT count(*) FROM RECO_CLOSINGSTOCK_TEMP WHERE DIST_ID= ? and RECO_PERIOD_ID= ? and PRODUCT_ID= ? and stock_type  = 4 with ur");
			 pstmt3.setInt(1,(Integer.parseInt(String.valueOf(distId))));
			 pstmt3.setInt(2, recoId);
			 pstmt3.setInt(3, (Integer.parseInt(String.valueOf(productId))));
			 rset4=pstmt3.executeQuery();
			 if(rset4.next())
			 {
	
				 
				 count=rset4.getInt(1);
				  if(count>0)
				  {
				  result="4";
				  }
		
			 }
			 }
}
			
		 
		 catch(Exception  ex)
		 {
			 logger.info("Exceptionn :"+ex.getMessage());
			 ex.printStackTrace();
		 }
		 logger.info("returning form daoimpl  :"+result);
			return result;
	}

	//added by vishwas

	//end by vishwas
	
//	added by vishwas
	public String uploadClosingStockDetailsXlsagain(FormFile file,int stockType,int productId,Long distId,String indexes,int recoId) throws DPServiceException
	{

		String excelMessage = null;
		Connection connection=null;
		PreparedStatement pstmt=null;
		int rset=0;

	try{
		/***************changing by Beeru 15 Apr 2014********************/
		 /*connection = DBConnectionManager.getDBConnection();
		 logger.info("Delete Query stockType"+stockType+" productId "+productId);
		 pstmt=connection.prepareStatement("DELETE FROM RECO_CLOSINGSTOCK_TEMP WHERE DIST_ID= ? and RECO_PERIOD_ID= ? and PRODUCT_ID= ? and stock_type  = ? WITH UR");
		 pstmt.setLong(1, distId);
		 pstmt.setInt(2, recoId);
		 pstmt.setInt(3, productId);
		 pstmt.setInt(4,stockType);
		 rset=pstmt.executeUpdate();
		 connection.commit();*/
		 /***************changing end by Beeru 15 Apr 2014********************/
		 excelMessage=uploadClosingStockDetailsXls(file,stockType,productId,distId,indexes,recoId);
	 
		
	}
		 catch(Exception ex)
				 {
			 System.out.println("Exception in uploadClosingStockDetailsXlsagain"+ex);
			 ex.printStackTrace();
				 }
	return excelMessage;
	}
	//end by vishwas
	//end by vishwas
	//added by beeru on 21 march 2014 for geting reco product list
	
	public List<DistRecoDto> getProductList(String recoPeriod, String distId ,String stockType) throws DPServiceException {

		logger.info("**************************in  getProductList()**************************");
		List<DistRecoDto> recoProductListDTO = new ArrayList<DistRecoDto>();
		PreparedStatement pstmt = null;
		ResultSet rset = null;
		DistRecoDto recoProductDto = null;
		
		try {
			StringBuilder productListQuery =  new StringBuilder(com.ibm.dp.common.DBQueries.SQL_PRODUCT_LIST);
			if(stockType !="-1" && !stockType.equalsIgnoreCase("-1")) {
			if(stockType.equals("1")) {
				productListQuery.append(" and PM.PRODUCT_TYPE ='1'");
			}else if(stockType.equals("0")) {
				productListQuery.append(" and PM.PRODUCT_TYPE ='0'");
			}
			}
			productListQuery.append(" with ur");
			pstmt = connection.prepareStatement(productListQuery.toString());
			pstmt.setString(1, recoPeriod);
			pstmt.setString(2, distId);
			rset = pstmt.executeQuery();
			recoProductListDTO = new ArrayList<DistRecoDto>();
			while (rset.next()) {
				recoProductDto =   new DistRecoDto();
				recoProductDto.setProductId(rset.getString("PRODUCT_ID"));
				recoProductDto.setProductName(rset.getString("PRODUCT_NAME"));
				recoProductListDTO.add(recoProductDto);
			}
		} catch (Exception e) {
			e.printStackTrace();
			throw new DPServiceException(e.getMessage());
		} finally {
			DBConnectionManager.releaseResources(pstmt, rset);
		}
		logger.info("**************************out  getProductList()**************************");
		return recoProductListDTO;
	}
	//	end by beeru 
	
	//added by beeru on 04 April 2014 for excelsheet validation
	private String excelValidationOfClosingStock(int productId,int stockType,Long distId,int recoId , Iterator<Row> rowIterator ,int columnIndexserialNo,int distflag){
		
		
		int remarksflag=distflag+1;
		ArrayList remarksSerialized=new ArrayList();
		ArrayList remarksDef=new ArrayList();
		String remarksYes="";
		String flagDist=null;
		String remarks=null;
		try {
			List<String>  closingSerialNosFromExcel  =  new ArrayList<String>();
			List<String>  closingSerialNosFromExcelDistFlag  =  new ArrayList<String>();
			//List<String>  closingSerialNosFromExcelRemarks  =  new ArrayList<String>();
			/*remarksYes=ResourceReader.getCoreResourceBundleValue("CLOSING.DISTREMARKS");
			remarksSerialized.add(ResourceReader.getCoreResourceBundleValue("CLOSING.DISTREMARKSN1"));
			remarksSerialized.add(ResourceReader.getCoreResourceBundleValue("CLOSING.DISTREMARKSN2"));
			remarksSerialized.add(ResourceReader.getCoreResourceBundleValue("CLOSING.DISTREMARKSN3"));
			remarksSerialized.add(ResourceReader.getCoreResourceBundleValue("CLOSING.DISTREMARKSN4"));
			remarksDef.add(ResourceReader.getCoreResourceBundleValue("CLOSING.DISTREMARKSN1"));
			remarksDef.add(ResourceReader.getCoreResourceBundleValue("CLOSING.DISTREMARKSN2"));
			remarksDef.add(ResourceReader.getCoreResourceBundleValue("CLOSING.DISTREMARKSN3"));
			remarksDef.add(ResourceReader.getCoreResourceBundleValue("CLOSING.DISTREMARKSN4"));
			remarksDef.add(ResourceReader.getCoreResourceBundleValue("CLOSING.DISTREMARKSN5"));
			remarksDef.add(ResourceReader.getCoreResourceBundleValue("CLOSING.DISTREMARKSN6"));*/
			int rowCount =1;
			String serial=null;
			logger.info("excelValidationOfClosingStock calling ....."+productId+" ==    distId  "+ distId +"stockType "+stockType);
			while (rowIterator.hasNext()) {
				Row row   = rowIterator.next();
				if(rowCount >1 && row.getCell(columnIndexserialNo) != null) {
					if(row.getCell(columnIndexserialNo).getCellType()== Cell.CELL_TYPE_STRING){
						serial=row.getCell(columnIndexserialNo).getStringCellValue();
						closingSerialNosFromExcel.add(row.getCell(columnIndexserialNo).getStringCellValue());
						//closingSerialNosFromExcelDistFlag.add(row.getCell(distflag).getStringCellValue());
						//closingSerialNosFromExcelRemarks.add(row.getCell(remarksflag).getStringCellValue());
					}else if(row.getCell(columnIndexserialNo).getCellType()== Cell.CELL_TYPE_NUMERIC) {
						//logger.info("rowCount"+rowCount);
						serial=String.valueOf(row.getCell(columnIndexserialNo).getNumericCellValue());
						closingSerialNosFromExcel.add(String.valueOf(row.getCell(columnIndexserialNo).getNumericCellValue()));
						//.add(String.valueOf(row.getCell(distflag).getNumericCellValue()));
						//closingSerialNosFromExcelRemarks.add(String.valueOf(row.getCell(remarksflag).getNumericCellValue()));
					}
					if(serial!=null && serial.length()!=11)
					{
						return "Please check the Type of Stock File you are uploading";
					}
					
				}else if(rowCount >1) {
					break;
				}
				rowCount++;
			}
			if(closingSerialNosFromExcel.size() <= 0) {
				return " No record found";
			}
			List<String>  closingSerialNos  =  new ArrayList<String>();
			DistRecoServiceImpl distRecoServiceImpl   = new DistRecoServiceImpl();//5 for  closing stock
			List<DistRecoDto>   distRecoList  =  distRecoServiceImpl.getDetailsList(String.valueOf(productId), String.valueOf(stockType), distId, "5",String.valueOf(recoId));
			for(DistRecoDto distRecoDto : distRecoList) {
				if(stockType==1) { //  only for serialized closing stock 
					closingSerialNos.add(distRecoDto.getProductSerialNo());
				}else {
					closingSerialNos.add(distRecoDto.getRecoveredStbNo());
				}
				
				
			}
			//System.out.println(rowCount+"From DB"+closingSerialNos);
			/*flagDist=null;
			for(int j=1;j<(rowCount-1);j++)
			{
				flagDist=closingSerialNosFromExcelDistFlag.get(j-1);
				if(flagDist!=null)
				{ 
				if(flagDist.equalsIgnoreCase("yes")){
					  flagDist ="Y";  
    			  }
    			  else if(flagDist.equalsIgnoreCase("no"))
    			  {
    				  flagDist ="N";  
    			  }
				
					if(!( (flagDist.equalsIgnoreCase("N")) || (flagDist.equalsIgnoreCase("Y")) ))
							{
						return "You have to choose distributor flag as No or Yes ";
							}
				}
			}
			remarks=null;
			//System.out.println("rowCount  in remarks "+rowCount);
			for(int j=1;j<(rowCount-1);j++)
			{
				
				
				remarks=closingSerialNosFromExcelRemarks.get(j-1);
				
				flagDist=closingSerialNosFromExcelDistFlag.get(j-1);
				//System.out.println("remarks "+remarks+"flagDist" +flagDist);
				if(flagDist!=null)
				{ 
				if(flagDist.equalsIgnoreCase("yes")){
					  flagDist ="Y";  
    			  }
    			  else if(flagDist.equalsIgnoreCase("no"))
    			  {
    				  flagDist ="N";  
    			  }
				if(flagDist.equals("Y"))
				{
					if(!remarks.equalsIgnoreCase(remarksYes))
					{
						return "You have to choose Remarks as This is ok for Yes Flag";
					}
				}
				else if(flagDist.equals("N"))
				{
					if(stockType==1)
					{
						if(!remarksSerialized.contains(remarks))
						{
							return "You have to choose Remarks as per No Flag";
						}
					}
					else
					{
						if(!remarksDef.contains(remarks))
						{
							return "You have to choose Remarks as per No Flag";
						}
					}
					
				}
			
				}
			}
			logger.info("excelValidationOfClosingStock calling ....44."+productId+" ==    distId  "+ distId +"stockType "+stockType);*/
			//logger.info(columnIndexserialNo+"******** printing serial no for fro mDB******"+closingSerialNos);
			//System.out.println("From excel :"+closingSerialNosFromExcel);
			//logger.info("******** printing serial no for excel******"+closingSerialNosFromExcel);
			//System.out.println("*******  closingSerialNosFromExcel.size() :"+ closingSerialNosFromExcel.size()+" closingSerialNos.size() :"+closingSerialNos.size()+"  comparings :"+(closingSerialNosFromExcel.size() == closingSerialNos.size()));
			
			if(closingSerialNos !=null && closingSerialNosFromExcel !=null && closingSerialNosFromExcel.size() == closingSerialNos.size()) {//for duplication checking
				Set<String> serialnos  = new HashSet<String>();
				serialnos.addAll(closingSerialNos);
				serialnos.addAll(closingSerialNosFromExcel);
				//System.out.println(" serialnos.size() :"+serialnos.size());
				System.out.println("   serialnos.size() == closingSerialNosFromExcel.size()  :  "+serialnos.size() == closingSerialNosFromExcel.size()+"  closingSerialNosFromExcel.size():"+closingSerialNosFromExcel.size());
				if(serialnos.size() == closingSerialNosFromExcel.size()){
					return null;
				}else {
					return "Either you have deleted or added some records Or please check the Product and stock type selected.";
				}
			}else {
				return "Either you have deleted or added some records Or please check the Product and stock type selected.";
			}
			
			
			
			
			} catch (Exception e) {
			e.printStackTrace();
		}
		return "Some error occured , Please try again or check the Type of Stock File you are uploading";
	}
	//end by beeru
	public String uploadClosingStockDetailsXlsRemarks(int productId,int stockType,Long distId,int recoId , Iterator<Row> rowIterator ,int columnIndexserialNo,int distflag){
		
		
		ArrayList remarksSerialized=new ArrayList();
		ArrayList remarksDef=new ArrayList();
		String remarksYes="";
		String flagDist=null;
		String remarks=null;
		String remarksOther = null;
	
	   	int remarksflag=distflag+1;
	   	int remarksOtherFlag = remarksflag + 1;
	   	
		try {
			
			List<String>  closingSerialNosFromExcel  =  new ArrayList<String>();
			List<String>  closingSerialNosFromExcelDistFlag  =  new ArrayList<String>();
			List<String>  closingSerialNosFromExcelRemarks  =  new ArrayList<String>();
			List<String>  closingSerialNosFromExcelRemarksOther  =  new ArrayList<String>();
			remarksYes=ResourceReader.getCoreResourceBundleValue("CLOSING.DISTREMARKS");
			remarksSerialized.add(ResourceReader.getCoreResourceBundleValue("CLOSING.DISTREMARKSN1"));
			remarksSerialized.add(ResourceReader.getCoreResourceBundleValue("CLOSING.DISTREMARKSN2"));
			remarksSerialized.add(ResourceReader.getCoreResourceBundleValue("CLOSING.DISTREMARKSN3"));
			remarksSerialized.add(ResourceReader.getCoreResourceBundleValue("CLOSING.DISTREMARKSN4"));
			remarksDef.add(ResourceReader.getCoreResourceBundleValue("CLOSING.DISTREMARKSN1"));
			remarksDef.add(ResourceReader.getCoreResourceBundleValue("CLOSING.DISTREMARKSN2"));
			remarksDef.add(ResourceReader.getCoreResourceBundleValue("CLOSING.DISTREMARKSN3"));
			remarksDef.add(ResourceReader.getCoreResourceBundleValue("CLOSING.DISTREMARKSN4"));
			remarksDef.add(ResourceReader.getCoreResourceBundleValue("CLOSING.DISTREMARKSN5"));
			remarksDef.add(ResourceReader.getCoreResourceBundleValue("CLOSING.DISTREMARKSN6"));
			int rowCount =1;
			logger.info("excelValidationOfClosingStock calling ....."+productId+" ==    distId  "+ distId +"stockType "+stockType+"columnIndexserialNo"+columnIndexserialNo);
			while (rowIterator.hasNext()) {
				Row row   = rowIterator.next();
				if(rowCount >1 && row.getCell(columnIndexserialNo) != null) {
					if(row.getCell(columnIndexserialNo).getCellType()== Cell.CELL_TYPE_STRING){
						closingSerialNosFromExcel.add(row.getCell(columnIndexserialNo).getStringCellValue());
						//logger.info("rowCount1"+rowCount);
						closingSerialNosFromExcelDistFlag.add(row.getCell(distflag).getStringCellValue());
						closingSerialNosFromExcelRemarks.add(row.getCell(remarksflag).getStringCellValue());
						closingSerialNosFromExcelRemarksOther.add(row.getCell(remarksOtherFlag).getStringCellValue());
					}else if(row.getCell(columnIndexserialNo).getCellType()== Cell.CELL_TYPE_NUMERIC) {
						logger.info("rowCount1"+rowCount);
						closingSerialNosFromExcel.add(String.valueOf(row.getCell(columnIndexserialNo).getNumericCellValue()));
						closingSerialNosFromExcelDistFlag.add(String.valueOf(row.getCell(distflag).getNumericCellValue()));
						closingSerialNosFromExcelRemarks.add(String.valueOf(row.getCell(remarksflag).getNumericCellValue()));
						closingSerialNosFromExcelRemarksOther.add(String.valueOf(row.getCell(remarksOtherFlag).getNumericCellValue()));
					}
				}else if(rowCount >1) {
					break;
				}
				rowCount++;
			}
			if(closingSerialNosFromExcel.size() <= 0) {
				return " No record found";
			}
			
			
			
		
			flagDist=null;
			for(int j=1;j<(rowCount-1);j++)
			{
				
				flagDist=closingSerialNosFromExcelDistFlag.get(j-1);
				//System.out.println("rowCount  in flagDist "+flagDist);
				if(flagDist!=null)
				{ 
				logger.info("*************************		Distributor flag is		************************     -------       "+flagDist);
				if(flagDist.equalsIgnoreCase("yes")){
					  flagDist ="Y";  
    			  }
    			  else if(flagDist.equalsIgnoreCase("no"))
    			  {
    				  flagDist ="N";  
    			  }
				
					if(!( (flagDist.equalsIgnoreCase("N")) || (flagDist.equalsIgnoreCase("Y")) ))
							{
						return "You have to choose distributor flag as No or Yes ";
							}
				}
			}
			remarks=null;
			//System.out.println("rowCount  in remarks "+rowCount);
			for(int j=1;j<(rowCount-1);j++)
			{
				//logger.info("remarks for "+j);
				
				remarks=closingSerialNosFromExcelRemarks.get(j-1);
				remarksOther = closingSerialNosFromExcelRemarksOther.get(j-1);
				
				flagDist=closingSerialNosFromExcelDistFlag.get(j-1);
				//System.out.println("remarks "+remarks+"flagDist" +flagDist);
				if(flagDist!=null)
				{ 
					if(flagDist.equalsIgnoreCase("yes")){
						  flagDist ="Y";  
	    			  }
	    			  else if(flagDist.equalsIgnoreCase("no"))
	    			  {
	    				  flagDist ="N";  
	    			  }
					//logger.info("flagDist for "+flagDist);
					if(flagDist.equals("Y"))
					{
						if(!remarks.equalsIgnoreCase(remarksYes))
						{
							return "You have to choose Remarks as This is ok for Yes Flag";
						}
						
					}
					else if(flagDist.equals("N"))
					{
						if(stockType==1)
						{
							//logger.info("remarks for stock 1"+remarks);
							if(!remarksSerialized.contains(remarks))
							{
								
								return "You have to choose Remarks as per No Flag";
							}
							
						}
						else
						{
							if(!remarksDef.contains(remarks))
							{
								return "You have to choose Remarks as per No Flag";
							}
							
						}
						if(remarks.equalsIgnoreCase("other") || remarks.equalsIgnoreCase("others"))
						{
							if(remarksOther == null || remarksOther.equalsIgnoreCase(""))
							{
								return "Please provide some remarks for reason 'Others'";
							}
						}
						else 
						{
							if(remarksOther != null)
							{
									if(!remarksOther.equalsIgnoreCase(""))
									{
										return "You have filled the 'Other Remarks' column, which is valid only for 'Other' reason case. Please remove the note from 'Other Remark' or select reason as 'Other' !";
									}
							}
						}
					}
				
				}
			}
			
			} catch (Exception e) {
			e.printStackTrace();
		}
			return null;
	}
	//end by beeru
	
}
 