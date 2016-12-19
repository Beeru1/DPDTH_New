package com.ibm.dp.dao.impl;

import java.io.IOException;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.log4j.Logger;

import com.ibm.dp.beans.InvoiceDetails;
import com.ibm.dp.beans.InvoiceForm;
import com.ibm.dp.beans.InvoiceListForm;
import com.ibm.dp.dao.DpInvoiceDao;
import com.ibm.dp.dto.DpInvoiceDto;
import com.ibm.dp.dto.ErrorDto;
import com.ibm.dp.dto.PartnerDetails;
import com.ibm.dp.dto.RateDTO;
import com.ibm.virtualization.recharge.db.DBConnectionManager;
import com.ibm.virtualization.recharge.exception.DAOException;

public class DpInvoiceDaoImpl implements DpInvoiceDao {
	
	
	public static List<Long> billList=new ArrayList<Long>();
	private String SQL_INS_INVOICE="INSERT INTO DPDTH.DP_INVOICE(BILL_DT, CPE_REC_NO, CPE_REC_RT," +
			" CPE_AMT, SR_REWARD_NO, SR_REWARD_RT, SR_REWARD_AMT, RETENTION_NO, RETENTION_RT," +
			" RETENTION_AMT, OTHR_NO, OTHR_RT, OTHR_AMT, TOTAL, SRVC_TAX_RT, SRVC_TX_AMT, EDU_CESS_RT," +
			" EDU_CESS_AMT, HIGH_EDU_CESS_RT, HIGH_EDU_AMT, GRAND_TOTAL, NAME_CUST, ADD_CUST, INVOICE_NO, OLM_ID, PARTNER_NM, STATUS,MONTH_FR,OTHERS,OTHERS_1, KKC_RT, KKC_AMT) "+
   " VALUES(?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
	
	private String SQL_INS_INVOICE_2="INSERT INTO DPDTH.DP_PARTNER_INVOICE_2(OLM_ID, PARTNER_NAME, ASM, CIRCLE, DVR_T1, "+
	"DVR_T2, DVR_T3, HD_T1, HD_T2, HD_T3, MULTI_T1, MULTI_T2, MULTI_T3, MULTI_DVR_T1, "+
	"MULTI_DVR_T2, MULTI_DVR_3, TOTAL_ACT, TOTAL_AMOUNT, HD_HD_EXT, DVR, AMOUNT, ON_YEAR_COUNT, "+
	"ON_YEAR_AMOUNT, FIELD_REPAIRS_AMOUNT, ARP_AMOUNT, TOTAL_BASE_AMOUNT, ST, ES, HES, TOTAL_INVOICE_AMT, INVOICE_NO,STATUS,MONTH_FR,TIER,BILL_DT,WEAK_GEO_PAY,HILLY_PAY,OTHERS,OTHERS_1,OTHERS_2,FIN_REMARKS, KKC) "+
	"VALUES(?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, current date, ?, ?, ?, ?, ?, ?, ?) ";

	public static Logger logger=Logger.getLogger(DpInvoiceDaoImpl.class);
	
	private String PARTNER_DETAILS_QUERY=" select vad.ACCOUNT_NAME,vad.ACCOUNT_ADDRESS, vad.ADDRESS_2, vad.SERVICE_TAX_NO, vad.PAN_NO, vad.TIN_NO "
										+" from VR_LOGIN_MASTER vlm, VR_ACCOUNT_DETAILS vad "
										+" where vlm.LOGIN_ID=vad.ACCOUNT_ID "
										+" and vlm.LOGIN_NAME=? with ur";
	
	private String LIST_INVOICE="select  OLM_ID,PARTNER_NM ,BILL_NO,INVOICE_NO,MONTH_FR from DP_INVOICE where STATUS='U' and OLM_ID=? order by PARTNER_NM asc ";
	private String LIST_REJECTED_INVOICE="select  OLM_ID,PARTNER_NM ,BILL_NO,INVOICE_NO from DP_INVOICE where STATUS='R' order by PARTNER_NM asc";
	private String LIST_ACC_INVOICE="select OLM_ID,PARTNER_NM,concat(BILL_NO,'/CPE_SLA')as BILL_NO,INVOICE_NO,MONTH_FR "+
									" from DP_INVOICE where STATUS=? "+
									" union all "+
									" select OLM_ID,PARTNER_NAME as PARTNER_NM,concat(BILL_NO,'/STB_INS')as BILL_NO,INVOICE_NO,MONTH_FR "+
									" from DP_PARTNER_INVOICE_2 "+
									" where STATUS=? ";
	
	private String LIST_ALL_INVOICE = "select OLM_ID,PARTNER_NM,concat(BILL_NO,'/CPE_SLA')as BILL_NO,INVOICE_NO,MONTH_FR, case when STATUS='A' then 'ACCEPTED' when STATUS='R' then 'REJECTED' when STATUS='U' then 'Unactioned' end as STATUS"
									+ " from DP_INVOICE where OLM_ID=? and STATUS<>'S'"
									+ " union all "
									+ " select OLM_ID,PARTNER_NAME as PARTNER_NM,concat(BILL_NO,'/STB_INS')as BILL_NO,INVOICE_NO,MONTH_FR, case when STATUS='A' then 'ACCEPTED' when STATUS='R' then 'REJECTED' when STATUS='U' then 'Unactioned' end as STATUS"
									+ " from DP_PARTNER_INVOICE_2 where OLM_ID=? and STATUS<>'S' ";
	
	

	private String LIST_INVOICE_STB="select  OLM_ID,PARTNER_NAME ,BILL_NO,INVOICE_NO,MONTH_FR from DP_PARTNER_INVOICE_2 where STATUS='U' and OLM_ID=? order by PARTNER_NAME asc ";
	private String LIST_INVOICES_DIST="select OLM_ID,PARTNER_NM,concat(BILL_NO,'/CPE_SLA')as BILL_NO,INVOICE_NO,MONTH_FR "+
								" from DP_INVOICE where STATUS=?  and OLM_ID=?"+
								" union all "+
								" select OLM_ID,PARTNER_NAME as PARTNER_NM,concat(BILL_NO,'/STB_INS')as BILL_NO,INVOICE_NO,MONTH_FR "+
								" from DP_PARTNER_INVOICE_2 "+
								" where STATUS=? and OLM_ID=?";
	private String LATES_RATES_QUERY= "select * from DP_PAYOUT_RATE_MASTER where RATE_REVISION = (select max(RATE_REVISION) as RATE_REVISION from DP_PAYOUT_RATE_MASTER) with ur";
	
	private String BILL_RATES_QUERY="select * from DP_PAYOUT_RATE_MASTER where RATE_REVISION= "+
		"(select case when max(RATE_REVISION) is not null then max(RATE_REVISION) else 1 end from DP_PAYOUT_RATE_MASTER where REVISION_DT< ? with ur)";
	
	
	private String FETCH_ADDRESS="select * from DP_CONFIGURATION_DETAILS where CONFIG_ID=35 with ur";

	public void uploadInvoiceDetails(Map<String,DpInvoiceDto> dpInvMap) throws Exception
	{
		
		Connection con;
		try {
			con=DBConnectionManager.getDBConnection();
		} catch (DAOException e) {
			logger.info("Error in DB Connection");
			e.printStackTrace();
			throw new Exception();
		
		}
		Set<String> olmIdSet=dpInvMap.keySet();
		Calendar cal=Calendar.getInstance();
		int month=cal.get(Calendar.MONTH);
		int year=cal.get(Calendar.YEAR);
		if(month==0)
		{
			month=12;
			year=year-1;
		}
		String monthFr=month+ "-"+year;
		if(month<10){
			monthFr="0"+monthFr;
		}
		String sqlUpdateOldInv="UPDATE DP_INVOICE SET STATUS='S' where STATUS='U' and OLM_ID=? and MONTH_FR=?";
		try{
	    for(String olmId:olmIdSet)
	    {
	    	 DpInvoiceDto dpInvdto=dpInvMap.get(olmId);
	    	 if(dpInvdto.getMonthFr()!=null && !dpInvdto.getMonthFr().trim().equals(""))
	    		 monthFr=dpInvdto.getMonthFr();
	    	 PreparedStatement pst=con.prepareStatement(sqlUpdateOldInv);
	    	 pst.setString(1, olmId);
	    	 pst.setString(2,monthFr);
	    	 pst.executeUpdate();
	    	 
	    	 con.commit();
	    	 
	    	 
	    	pst=con.prepareStatement(SQL_INS_INVOICE,new String[]{"BILL_NO"});
	    	logger.info(SQL_INS_INVOICE);
	    	logger.info("Inserting for OLM ID="+olmId);
	    	
	    	pst.setDate(1, new java.sql.Date(System.currentTimeMillis()));
	    	
	    	pst.setInt(2,dpInvdto.getCperecNo());
	    	pst.setDouble(3, dpInvdto.getCperecRt());
	    	pst.setDouble(4, dpInvdto.getCperecAmt());
	    	
	    	pst.setInt(5, dpInvdto.getSrrwdNo());
	    	pst.setDouble(6, dpInvdto.getSrrwdRt());
	    	pst.setDouble(7, dpInvdto.getSrrwdAmt());
	    	
	    	pst.setInt(8, dpInvdto.getRetNo());
	    	pst.setDouble(9, dpInvdto.getRetRt());
	    	pst.setDouble(10, dpInvdto.getRetAmt());
	    	
	    	pst.setInt(11, dpInvdto.getOthNo());
	    	pst.setDouble(12, dpInvdto.getOthRt());
	    	pst.setDouble(13, dpInvdto.getOthAmt());
	    	
	    	pst.setDouble(14, dpInvdto.getTotal());
	    	
	    	pst.setDouble(15, dpInvdto.getSrvTxRt());
	    	pst.setDouble(16, dpInvdto.getSrvTxAmt());
	    	
	    	pst.setDouble(17, dpInvdto.getEduCesRt());
	    	pst.setDouble(18, dpInvdto.getEduCesAmt());
	    	
	    	pst.setDouble(19, dpInvdto.getHeduCesRt());
	    	pst.setDouble(20, dpInvdto.getHeduCesAmt());
	    	
	    	pst.setDouble(21, dpInvdto.getGrndTotal());
	    	
	    	pst.setString(22, dpInvdto.getCstNam());
	    	pst.setString(23, dpInvdto.getCStAdd());
	    	
	    	pst.setString(24, dpInvdto.getInvNo());
	    	
	    	pst.setString(25, dpInvdto.getOlmId());
	    	pst.setString(26, dpInvdto.getPartnerNm());
	    	pst.setString(27, "U");
	    	pst.setString(28, monthFr);
	    	pst.setInt(29, dpInvdto.getOthers());
	    	pst.setInt(30, dpInvdto.getOthers1());
	    	pst.setDouble(31, dpInvdto.getKkcrt());
	    	pst.setDouble(32, dpInvdto.getKkcamt());
	    	
	    	
	    	try{
	    	pst.executeUpdate();
	    	ResultSet genKey=pst.getGeneratedKeys();
	    	con.commit();
	    	
	    	
	    	
	    	logger.info("Inserted for OLM ID:"+olmId);
	    	}catch(SQLException excep){
	    		excep.printStackTrace();
	    		logger.info("Exception in insert");
	    	}
	    	
	    	
	    	
	    	
	    }
		}catch(Exception exp){
			exp.printStackTrace();
			logger.info("Message :"+exp.getMessage());
			logger.info("Exception in insertion of Invoice data");
			
		}finally{
			con.commit();
			DBConnectionManager.releaseResources(con);
		}
	}
	

	@Override
	public List<InvoiceDetails> listInvoices(String loginNm) throws Exception {
		

		Connection con;
		PreparedStatement ps=null;
		ResultSet rs=null;
		
		try {
			con=DBConnectionManager.getDBConnection();
		} catch (DAOException e) {
			logger.info("Error in DB Connection");
			e.printStackTrace();
			throw new Exception();
		
		}
		
		List<InvoiceDetails> invList=new ArrayList<InvoiceDetails>();
		
		try{
		ps=con.prepareStatement(LIST_INVOICE);
		ps.setString(1, loginNm);
		logger.info("Login Name="+loginNm);
		rs=ps.executeQuery();
		
		while(rs.next()){
			
			InvoiceDetails invDetail=new InvoiceDetails();
			
			invDetail.setBillNo(((Integer)rs.getInt(3)).toString()+"/CPE_SLA");
			invDetail.setInvoiceNo(rs.getString(4));
			invDetail.setOlmId(rs.getString(1));
			invDetail.setPartnerNm(rs.getString(2));
			invDetail.setMonthFor(rs.getString(5));
			
			invList.add(invDetail);
			
		}
		}catch(Exception exp){
			
			logger.info("Exception in loading invoice data");
		}finally{
			
			DBConnectionManager.releaseResources(con);
			DBConnectionManager.releaseResources(ps, rs);
		}
		
		return invList;
	}

	@Override
	public DpInvoiceDto partnerInvoice(int billNo) throws Exception {
		
		
		DpInvoiceDto dpinvDto=new DpInvoiceDto();
		
		String partnerInvoiceQuery="SELECT * FROM DP_INVOICE WHERE BILL_NO=? with ur";
		Connection con=null;
		PreparedStatement ps=null;
		ResultSet rs=null;
		String noToWords[]={"Zero","One","Two","Three","Four","Five","Six","Seven","Eight","Nine"};
		
		try{
			con=DBConnectionManager.getDBConnection();
		}catch(DAOException daoEx){
			daoEx.printStackTrace();
			logger.info("Connection error");
			throw new Exception();
		}
	    ps=con.prepareStatement(partnerInvoiceQuery);
	    ps.setInt(1, billNo);
	    rs=ps.executeQuery();
	    Integer blNo=billNo;
		//ResultSetMetaData rsmd=rs.getMetaData();
	    try{
		while(rs.next()){
			dpinvDto.setBilNo(blNo.toString());
			dpinvDto.setBilDt(rs.getDate("BILL_DT"));
			dpinvDto.setTinNo(rs.getString("TIN_NO"));
			dpinvDto.setPanNo(rs.getString("PAN_NO"));
			dpinvDto.setSrvcTxCt("SRVC_TAX_CT");
			dpinvDto.setCperecNo(rs.getInt("CPE_REC_NO"));
			dpinvDto.setCperecRt(rs.getDouble("CPE_REC_RT"));
			dpinvDto.setCperecAmt();
			dpinvDto.setSrrwdNo(rs.getInt("SR_REWARD_NO"));
			dpinvDto.setSrrwdRt(rs.getDouble("SR_REWARD_RT"));
			dpinvDto.setSrrwdAmt();
			dpinvDto.setRetNo(rs.getInt("RETENTION_NO"));
			dpinvDto.setRetRt(rs.getDouble("RETENTION_RT"));
			dpinvDto.setRetAmt();
			dpinvDto.setOthNo(rs.getInt("OTHR_NO"));
			dpinvDto.setOthRt(rs.getDouble("OTHR_RT"));
			dpinvDto.setOthers(rs.getInt("OTHERS"));
			dpinvDto.setOthers1(rs.getInt("OTHERS_1"));
			dpinvDto.setOthAmt();
			dpinvDto.setTotal();
			dpinvDto.setSrvTxRt(rs.getDouble("SRVC_TAX_RT"));
			dpinvDto.setSrvTxAmt(rs.getDouble("SRVC_TX_AMT"));
			dpinvDto.setEduCesRt(rs.getDouble("EDU_CESS_RT"));
			dpinvDto.setEduCesAmt(rs.getDouble("EDU_CESS_AMT"));
			dpinvDto.setHeduCesRt(rs.getDouble("HIGH_EDU_CESS_RT"));
			dpinvDto.setHeduCesAmt(rs.getDouble("HIGH_EDU_AMT"));
			dpinvDto.setKkcrt(rs.getDouble("KKC_RT"));
			dpinvDto.setKkcamt(rs.getDouble("KKC_AMT"));
			dpinvDto.setGrndTotal();
			dpinvDto.setCstNam(rs.getString("NAME_CUST"));
			dpinvDto.setCStAdd(rs.getString("ADD_CUST"));
			dpinvDto.setInvNo(rs.getString("INVOICE_NO"));
			dpinvDto.setOlmId(rs.getString("OLM_ID"));
			dpinvDto.setPartnerNm(rs.getString("PARTNER_NM"));
			dpinvDto.setStatus(rs.getString("STATUS"));
			dpinvDto.setRemarks(rs.getString("REMARKS"));
			dpinvDto.setMonthFr(rs.getString("MONTH_FR"));
			
			
			
		}
		
		String amtinwords="";
		int grandtotal=(int)dpinvDto.getGrndTotal();
		amtinwords=NumToWord.mainOfNotoWords(grandtotal);
		
		
		dpinvDto.setAmountInWords(amtinwords);
		
	    }catch(Exception expc){
	    	expc.printStackTrace();
	    	logger.info("Exception in DB....please try again");
	    	throw new Exception();
	    }finally {
	    	DBConnectionManager.releaseResources(ps, rs);
	    	DBConnectionManager.releaseResources(con);
	    }
		return dpinvDto;
		
	}

	@Override
	public String genericInvoice(int billNo, String invoiceNo, String status,String rem)
			throws Exception {
		String updateInv="UPDATE DP_INVOICE SET INVOICE_NO=?,STATUS=?,REMARKS=?,ACTION_DATE=? WHERE BILL_NO=? with ur";
		Connection con=null;
		PreparedStatement ps=null;
		ResultSet rs=null;
		int noRec;
		
		try{
			con=DBConnectionManager.getDBConnection();
		}catch(DAOException daoEx){
			daoEx.printStackTrace();
			logger.info("Connection error");
			throw new Exception();
		}
		con.setAutoCommit(false);
		ps=con.prepareStatement(updateInv);
		ps.setString(1,invoiceNo);
		ps.setString(2, status);
		ps.setString(3, rem);
		ps.setTimestamp(4, new Timestamp(System.currentTimeMillis()));
		ps.setInt(5, billNo);
		
		try{
			noRec=ps.executeUpdate();
			
			if(noRec==1){
				con.commit();
				return "success";
			}
			else{
				con.rollback();
				return "errorInUpdate";
			}
		}catch(Exception exp){
			exp.printStackTrace();
			logger.info("Exception in update");
			throw new Exception();
		}finally{
			DBConnectionManager.releaseResources(ps, rs);
	    	DBConnectionManager.releaseResources(con);
		}

			
				
	}

	@Override
	public void uploadInvoiceSheet(Map<String, DpInvoiceDto> dpInvMap)
			throws Exception {
		
		
		Connection con;
		PreparedStatement ps=null;
		ResultSet rs=null;
		try {
			con=DBConnectionManager.getDBConnection();
		} catch (DAOException e) {
			logger.info("Error in DB Connection");
			e.printStackTrace();
			throw new Exception();
		
		}
		Set<String> olmIdSet=dpInvMap.keySet();
		Calendar cal=Calendar.getInstance();
		int month=cal.get(Calendar.MONTH);
		int year=cal.get(Calendar.YEAR);
		if(month==0)
		{
			month=12;
			year=year-1;
		}
		String monthFr=month+ "-"+year;
		if(month<10){
			monthFr="0"+monthFr;
		}
		String sqlUpdateOldInv="UPDATE DP_PARTNER_INVOICE_2 SET STATUS='S' where STATUS='U' and OLM_ID=? and MONTH_FR=? with ur";
		
		logger.info("Uploading sheet 1");
		try{
	    for(String olmId:olmIdSet)
	    {		    
	    	
	    	DpInvoiceDto dpinvDto=new DpInvoiceDto();
	    	 dpinvDto=dpInvMap.get(olmId);
	    	 if(dpinvDto.getMonthFr()!=null && !dpinvDto.getMonthFr().trim().equals(""))
	    		 monthFr=dpinvDto.getMonthFr();
	    	 ps=con.prepareStatement(sqlUpdateOldInv);
	    	 ps.setString(1, olmId);
	    	 ps.setString(2,monthFr);
	    	 ps.executeUpdate();
	    	 con.commit();
	    	 
	    	 ps=con.prepareStatement(SQL_INS_INVOICE_2);
	    	/* DpInvoiceDto dpinvDto=new DpInvoiceDto();
	    	 dpinvDto=dpInvMap.get(olmId);*/
	    	 ps.setString(1,olmId);
	    	 ps.setString(2,dpinvDto.getPartnerNm());
	    	 ps.setString(3,dpinvDto.getAsm());
	    	 ps.setString(4,dpinvDto.getCircle());
	    	 ps.setInt(5,dpinvDto.getDvrT1());
	    	 ps.setInt(6, dpinvDto.getDvrT2());
	    	 ps.setInt(7,dpinvDto.getDvrT3());
	    	 ps.setInt(8, dpinvDto.getHdextT1());
	    	 ps.setInt(9,dpinvDto.getHdextT2());
	    	 ps.setInt(10,dpinvDto.getHdextT3());
	    	 ps.setInt(11,dpinvDto.getMultiT1());
	    	 ps.setInt(12,dpinvDto.getMultiT2());
	    	 ps.setInt(13,dpinvDto.getMultiT3());
	    	 ps.setInt(14, dpinvDto.getMultidvrT1());
	    	 ps.setInt(15,dpinvDto.getMultidvrT2());
	    	 ps.setInt(16, dpinvDto.getMultidvrT3());
	    	 ps.setInt(17,dpinvDto.getTotalAct());
	    	 ps.setInt(18,dpinvDto.getTotalAmt());
	    	 ps.setInt(19, dpinvDto.getHdplusExt());
	    	 ps.setInt(20,dpinvDto.getDvr());
	    	 ps.setInt(21,dpinvDto.getAmt());
	    	 ps.setInt(22,dpinvDto.getOneyrCount());
	    	 ps.setInt(23,dpinvDto.getOneyrAmt());
	    	 ps.setInt(24,dpinvDto.getFieldreprelAmt());
	    	 ps.setInt(25,dpinvDto.getArpPayout());
	    	 ps.setInt(26,dpinvDto.getTotbasAmt());
	    	 ps.setInt(27,dpinvDto.getServTx());
	    	 ps.setInt(28,dpinvDto.getEduCes());
	    	 ps.setInt(29,dpinvDto.getHeduCes());
	    	 ps.setInt(30,dpinvDto.getTotinvAmt());
	    	 ps.setInt(31,0);
	    	 ps.setString(32,"U");
	    	 ps.setString(33, monthFr);
	    	 ps.setInt(34, dpinvDto.getTier());
	    	 ps.setInt(35,dpinvDto.getWeakGeo());
	    	 ps.setInt(36,dpinvDto.getHillyArea());
	    	 ps.setInt(37,dpinvDto.getOthers());
	    	 ps.setInt(38,dpinvDto.getOthers1());
	    	 ps.setInt(39, dpinvDto.getOthers2());
	    	 ps.setString(40,dpinvDto.getFinRemarks());
	    	 ps.setInt(41, dpinvDto.getKkc());
	    	 ps.executeUpdate();
	    	 
	    	 
	    	 
	    	 
	    	 
	    	
	    	 
	    }
	    logger.info("Uploaded Invoice sheet 1");
		}catch(Exception expc){
	    	expc.printStackTrace();
	    	logger.info("Exception in DB....please try again");
	    	throw new Exception();
	    }finally {
	    	con.commit();
	    	DBConnectionManager.releaseResources(ps, rs);
	    	DBConnectionManager.releaseResources(con);
	    }
		
		
		
	}

	@Override
	public List<InvoiceDetails> listRejectedInvoices()
			throws Exception {
		Connection con;
		PreparedStatement ps=null;
		ResultSet rs=null;
		
		try {
			con=DBConnectionManager.getDBConnection();
		} catch (DAOException e) {
			logger.info("Error in DB Connection");
			e.printStackTrace();
			throw new Exception();
		
		}
		
		List<InvoiceDetails> invList=new ArrayList<InvoiceDetails>();
		
		try{
		ps=con.prepareStatement(LIST_REJECTED_INVOICE);
		
		
		rs=ps.executeQuery();
		
		while(rs.next()){
			
			InvoiceDetails invDetail=new InvoiceDetails();
			
			//invDetail.setBillNo(rs.getInt(3));
			invDetail.setInvoiceNo(rs.getString(4));
			invDetail.setOlmId(rs.getString(1));
			invDetail.setPartnerNm(rs.getString(2));
			
			invList.add(invDetail);
			
		}
		}catch(Exception exp){
			
			logger.info("Exception in loading invoice data");
		}finally{
			
			DBConnectionManager.releaseResources(con);
			DBConnectionManager.releaseResources(ps, rs);
		}
		
		return invList;
	}


	@Override
	public List<InvoiceDetails> listInvoicesSTB(String loginNm)
			throws Exception {

		Connection con;
		PreparedStatement ps=null;
		ResultSet rs=null;
		
		try {
			con=DBConnectionManager.getDBConnection();
		} catch (DAOException e) {
			logger.info("Error in DB Connection");
			e.printStackTrace();
			throw new Exception();
		
		}
		
		List<InvoiceDetails> invList=new ArrayList<InvoiceDetails>();
		
		try{
		ps=con.prepareStatement(LIST_INVOICE_STB);
		ps.setString(1, loginNm);
		logger.info("Login Name="+loginNm);
		rs=ps.executeQuery();
		
		while(rs.next()){
			
			InvoiceDetails invDetail=new InvoiceDetails();
			
			invDetail.setBillNo(((Integer)rs.getInt(3)).toString()+"/STB_INS");
			invDetail.setInvoiceNo(rs.getString(4));
			invDetail.setPartnerNm(rs.getString(2));
			invDetail.setMonthFor(rs.getString(5));
			invList.add(invDetail);
			
			logger.info(rs.getString(2));
			
		}
		logger.info("Size =" + invList.size());
		}catch(Exception exp){
			 exp.printStackTrace();
			logger.info("Exception in loading invoice data");
		}finally{
			
			DBConnectionManager.releaseResources(con);
			DBConnectionManager.releaseResources(ps, rs);
		}
		
		return invList;
	}


	@Override
	public DpInvoiceDto partnerInvoiceSTB(int billNo) throws Exception {
		DpInvoiceDto dpinvDto = new DpInvoiceDto();

		String partnerInvoiceQuery = "SELECT * FROM DP_PARTNER_INVOICE_2 WHERE BILL_NO=? with ur";
		Connection con = null;
		PreparedStatement ps = null;
		ResultSet rs = null;

		try {
			con = DBConnectionManager.getDBConnection();
		} catch (DAOException daoEx) {
			daoEx.printStackTrace();
			logger.info("Connection error");
			throw new Exception();
		}
		ps = con.prepareStatement(partnerInvoiceQuery);
		ps.setInt(1, billNo);
		rs = ps.executeQuery();
		Integer blNo = billNo;
		
		
		try 
		{
		
		while (rs.next()) {
			
			
			dpinvDto.setOlmId(rs.getString("OLM_ID"));
	    	dpinvDto.setPartnerNm(rs.getString("PARTNER_NAME"));
	    	dpinvDto.setAsm(rs.getString("ASM"));
	    	dpinvDto.setCircle(rs.getString("CIRCLE"));
	    	
	    	dpinvDto.setDvrT1(rs.getInt("DVR_T1"));
	    	dpinvDto.setDvrT2(rs.getInt("DVR_T2"));
	    	dpinvDto.setDvrT3(rs.getInt("DVR_T3"));
	    	dpinvDto.setBilNo(blNo.toString());
	    	
	    	dpinvDto.setHdextT1(rs.getInt("HD_T1"));
	    	dpinvDto.setHdextT2(rs.getInt("HD_T2"));
	    	dpinvDto.setHdextT3(rs.getInt("HD_T3"));
	    	dpinvDto.setMultiT1(rs.getInt("MULTI_T1"));
	    	dpinvDto.setMultiT2(rs.getInt("MULTI_T2"));
	    	dpinvDto.setMultiT3(rs.getInt("MULTI_T3"));
	    	dpinvDto.setMultidvrT1(rs.getInt("MULTI_DVR_T1"));
	    	dpinvDto.setMultidvrT2(rs.getInt("MULTI_DVR_T2"));
	    	dpinvDto.setMultidvrT3(rs.getInt("MULTI_DVR_3"));
	    	dpinvDto.setTotalAct(rs.getInt("TOTAL_ACT"));
	    	dpinvDto.setTotalAmt(rs.getInt("TOTAL_AMOUNT"));
	    	dpinvDto.setHdplusExt(rs.getInt("HD_HD_EXT"));
	    	dpinvDto.setDvr(rs.getInt("DVR"));
	    	dpinvDto.setAmt(rs.getInt("AMOUNT"));
	    	dpinvDto.setOneyrCount(rs.getInt("ON_YEAR_COUNT"));
	    	dpinvDto.setOneyrAmt(rs.getInt("ON_YEAR_AMOUNT"));
	    	dpinvDto.setFieldreprelAmt(rs.getInt("FIELD_REPAIRS_AMOUNT"));
	    	dpinvDto.setArpPayout(rs.getInt("ARP_AMOUNT"));
	    	dpinvDto.setTotbasAmt(rs.getInt("TOTAL_BASE_AMOUNT"));
	    	dpinvDto.setServTx(rs.getInt("ST"));
	    	dpinvDto.setEduCes(rs.getInt("ES"));
	    	dpinvDto.setHeduCes(rs.getInt("HES"));
	    	dpinvDto.setTotinvAmt(rs.getInt("TOTAL_INVOICE_AMT"));
	    	dpinvDto.setStatus(rs.getString("STATUS"));
	    	dpinvDto.setRemarks(rs.getString("REMARKS"));
	    	dpinvDto.setInvNo(rs.getString("INVOICE_NO"));
	    	dpinvDto.setMonthFr(rs.getString("MONTH_FR"));
	    	dpinvDto.setTier(rs.getInt("TIER"));
	    	dpinvDto.setBilDt(rs.getDate("BILL_DT"));
	    	dpinvDto.setFinRemarks(rs.getString("FIN_REMARKS"));
	    	dpinvDto.setOthers(rs.getInt("OTHERS"));
	    	dpinvDto.setOthers1(rs.getInt("OTHERS_1"));
	    	dpinvDto.setOthers2(rs.getInt("OTHERS_2"));
	    	dpinvDto.setWeakGeo(rs.getInt("WEAK_GEO_PAY"));
	    	dpinvDto.setHillyArea(rs.getInt("HILLY_PAY"));
	    	dpinvDto.setKkc(rs.getInt("KKC"));
	    }
		String amtinwords="";
		int grandtotal=(int)dpinvDto.getTotinvAmt();
		amtinwords=NumToWord.mainOfNotoWords(grandtotal);
		dpinvDto.setAmountInWords(amtinwords);
		}catch(Exception expc){
	    	expc.printStackTrace();
	    	logger.info("Exception in DB....please try again");
	    	throw new Exception();
	    }finally {
	    	DBConnectionManager.releaseResources(ps, rs);
	    	DBConnectionManager.releaseResources(con);
	    }
	    
	    
	    
	    
		return dpinvDto;
	}


	@Override
	public String genericInvoiceSTB(int billNo, String invoiceNo, String status,String remarks)
			throws Exception {
		// TODO Auto-generated method stub
		String updateInv="UPDATE DP_PARTNER_INVOICE_2 SET INVOICE_NO=?,STATUS=?,REMARKS=?,ACTION_DATE=? WHERE BILL_NO=? with ur";
		Connection con=null;
		PreparedStatement ps=null;
		ResultSet rs=null;
		int noRec;
		
		try{
			con=DBConnectionManager.getDBConnection();
		}catch(DAOException daoEx){
			daoEx.printStackTrace();
			logger.info("Connection error");
			throw new Exception();
		}
		con.setAutoCommit(false);
		ps=con.prepareStatement(updateInv);
		ps.setString(1,invoiceNo);
		ps.setString(2, status);
		ps.setString(3, remarks);
		ps.setTimestamp(4, new Timestamp(System.currentTimeMillis()));
		ps.setInt(5, billNo);
		
		try{
			noRec=ps.executeUpdate();
			
			if(noRec==1){
				con.commit();
				return "success";
			}
			else{
				con.rollback();
				return "errorInUpdate";
			}
		}catch(Exception exp){
			exp.printStackTrace();
			logger.info("Exception in update");
			throw new Exception();
		}finally{
			DBConnectionManager.releaseResources(ps, rs);
	    	DBConnectionManager.releaseResources(con);
		}

			
				
	}


	@Override
	public ErrorDto validateOlmIdPartner(String olmId, String partnerNm,String monthFr)
			throws Exception {
			
			boolean status=false;
			String olmSql="select ACCOUNT_NAME, LOGIN_ID from VR_LOGIN_MASTER vlm, VR_ACCOUNT_DETAILS vad "+
							"WHERE STATUS='A' AND GROUP_ID=7 "+
							"AND vad.ACCOUNT_ID=vlm.LOGIN_ID "+
							"AND LOGIN_NAME=? with ur";
			
			String uploadCheck="SELECT BILL_NO FROM DP_INVOICE where OLM_ID=? and STATUS in ('A') and MONTH_FR=? " ;
			Connection conn;
			
			String partner="";
			Calendar cal=Calendar.getInstance();
			int month=cal.get(Calendar.MONTH);
			int year=cal.get(Calendar.YEAR);
			
			if(month==0){
				month=12;
				year=year-1;
			}
			if (monthFr == null || monthFr.trim().equals("")) {
				monthFr = month + "-" + year;
				if (month < 10) {
					monthFr = "0" + monthFr;
							}
				}
			logger.info("Month For the upload"+monthFr);
			PreparedStatement ps=null,ps1=null;
			ResultSet rs=null,rs1=null;
			ErrorDto errDto=new ErrorDto();
			errDto.setMessage("SUCCESS");
			errDto.setStatus(true);
			try{
				conn=DBConnectionManager.getDBConnection();
			}catch(DAOException daoexcep){
				logger.info("Unable to get DB connection");
				throw new Exception();
			}
			
			try{
			ps=conn.prepareStatement(olmSql);
			ps.setString(1, olmId);
			rs=ps.executeQuery();
			if(rs.next()){
				partner=rs.getString("ACCOUNT_NAME");
				//if(partner.equalsIgnoreCase(partnerNm))
					ps1=conn.prepareStatement(uploadCheck);
					ps1.setString(1,olmId);
					ps1.setString(2, monthFr);
					rs1=ps1.executeQuery();
					if(rs1.next()){
						logger.info("Hello");
						logger.info(rs1.getInt("BILL_NO"));
						//if(rs.getInt("BILL_NO")==0) {
						errDto.setStatus(false);
						errDto.setMessage("INVOICE ALREADY ACCEPTED  FOR THIS USER");
					//	}
						
					}
					else
						errDto.setStatus(true);
				/*else
				{	
					logger.info("Partner Name does not matches");
					errDto.setStatus(false);
					errDto.setMessage("Partner Name Mismatch");
				}*/
			}
			else{
				logger.info("OLM ID not found");
				errDto.setStatus(false);
				errDto.setMessage("OLM ID Mismatch");
			}
			}catch(SQLException sqlExp){
				sqlExp.printStackTrace();
				throw new Exception();
			} finally{
				
				DBConnectionManager.releaseResources(conn);
				DBConnectionManager.releaseResources(ps,rs);
			}
		
			return errDto;
	}
	@Override
	public ErrorDto validateOlmIdPartnerNew(String olmId, String partnerNm,String monthFr)
	throws Exception {
	
	boolean status=false;
	String olmSql="select ACCOUNT_NAME, LOGIN_ID from VR_LOGIN_MASTER vlm, VR_ACCOUNT_DETAILS vad "+
					"WHERE STATUS='A' AND GROUP_ID=7 "+
					"AND vad.ACCOUNT_ID=vlm.LOGIN_ID "+
					"AND LOGIN_NAME=? with ur";
	
	String uploadCheck="SELECT BILL_NO FROM DP_PARTNER_INVOICE_2 where OLM_ID=? and STATUS in ('A') and MONTH_FR=? ";
	Connection conn;
	
	String partner="";
	Calendar cal=Calendar.getInstance();
	int month=cal.get(Calendar.MONTH);
	int year=cal.get(Calendar.YEAR);
	
	if(month==0){
		month=12;
		year=year-1;
	}
	if (monthFr == null || monthFr.trim().equals("")) {
			monthFr = month + "-" + year;
			if (month < 10) {
				monthFr = "0" + monthFr;
			}
		}
	logger.info("Month For the upload"+monthFr);
	PreparedStatement ps=null,ps1=null;
	ResultSet rs=null,rs1=null;
	ErrorDto errDto=new ErrorDto();
	errDto.setMessage("SUCCESS");
	errDto.setStatus(true);
	try{
		conn=DBConnectionManager.getDBConnection();
	}catch(DAOException daoexcep){
		logger.info("Unable to get DB connection");
		throw new Exception();
	}
	
	try{
	ps=conn.prepareStatement(olmSql);
	ps.setString(1, olmId);
	rs=ps.executeQuery();
	if(rs.next()){
		partner=rs.getString("ACCOUNT_NAME");
		//if(partner.equalsIgnoreCase(partnerNm))
			ps1=conn.prepareStatement(uploadCheck);
			ps1.setString(1,olmId);
			ps1.setString(2, monthFr);
			rs1=ps1.executeQuery();
			if(rs1.next()){
				logger.info("Hello");
				logger.info(rs1.getInt("BILL_NO"));
				//if(rs.getInt("BILL_NO")==0) {
				errDto.setStatus(false);
				errDto.setMessage("INVOICE ALREADY ACCEPTED  FOR THIS USER");
			//	}
				
			}
			else
				errDto.setStatus(true);
		/*else
		{	
			logger.info("Partner Name does not matches");
			errDto.setStatus(false);
			errDto.setMessage("Partner Name Mismatch");
		}*/
	}
	else{
		logger.info("OLM ID not found");
		errDto.setStatus(false);
		errDto.setMessage("OLM ID Mismatch");
	}
	}catch(SQLException sqlExp){
		sqlExp.printStackTrace();
		throw new Exception();
	} finally{
		
		DBConnectionManager.releaseResources(conn);
		DBConnectionManager.releaseResources(ps,rs);
	}

	return errDto;
}

	@Override
	public ErrorDto validateDistCircle(String olmId, String circle)
			throws Exception {

		String circleQuery="select vlm.LOGIN_NAME,vcm.CIRCLE_NAME from VR_ACCOUNT_DETAILS vad,VR_LOGIN_MASTER vlm,VR_CIRCLE_MASTER vcm "
							+" where vlm.LOGIN_ID=vad.ACCOUNT_ID "
							+" and vad.CIRCLE_ID=vcm.CIRCLE_ID "
							+" and vlm.LOGIN_NAME=? with ur";
		Connection conn;
		String circleNm;
		PreparedStatement ps=null;
		ResultSet rs=null;
		
		ErrorDto errDto=new ErrorDto();
		errDto.setMessage("SUCCESS");
		errDto.setStatus(true);
		try{
			conn=DBConnectionManager.getDBConnection();
		}catch(DAOException daoexcep){
			logger.info("Unable to get DB connection");
			throw new Exception();
		}
		
		try{
		ps=conn.prepareStatement(circleQuery);
		ps.setString(1, olmId);
		rs=ps.executeQuery();
		if(rs.next()){
			circleNm=rs.getString("CIRCLE_NAME");
			if(circle.equalsIgnoreCase(circle))
				errDto.setStatus(true);
			else
			{	
				logger.info("Invalid Circle");
				errDto.setStatus(false);
				errDto.setMessage("Invalid Circle");
			}
		}
		else{
			logger.info("OLM ID not found");
			errDto.setStatus(false);
			errDto.setMessage("OLM ID Mismatch");
		}
		}catch(SQLException sqlExp){
			sqlExp.printStackTrace();
			throw new Exception();
		}finally{
			DBConnectionManager.releaseResources(conn);
			DBConnectionManager.releaseResources(ps,rs);
		}
		
		return errDto;
	}


	@Override
	public String invoiceUploaded(String absFile,long loginId) throws Exception {
		// TODO Auto-generated method stub
		
		String uploadQuery="INSERT INTO DP_MONTHLY_PAYOUT(UPLOADED_BY, UPLOADED_DT, FILE_NAME_PATH,  CYCLE, UPLOADED_FR_MNTH) "+
							" VALUES(?, current timestamp, ?, ?, ?) ";
		int cycle=1;
		String selectCycleQuery=" SELECT CASE when max(CYCLE)is null then 0 ELSE max(CYCLE) END as CYCLE From DP_MONTHLY_PAYOUT where UPLOADED_FR_MNTH=? with ur ";
		PreparedStatement ps=null;
		ResultSet rs=null;
		Connection conn;
		String message="";
		
		Calendar cal=Calendar.getInstance();
		int month=cal.get(Calendar.MONTH);
		int year=cal.get(Calendar.YEAR);
		if(month==0)
		{
			month=12;
			year=year-1;
		}
		
		String monthFr=month+ "-" +year;
		if(month<10)
			monthFr="0"+monthFr;
		
		try{
			conn=DBConnectionManager.getDBConnection();
		}catch(DAOException daoexcep){
			logger.info("Unable to get DB connection");
			throw new Exception();
		}
		
		try{
			ps=conn.prepareStatement(selectCycleQuery);
			ps.setString(1, monthFr);
			rs=ps.executeQuery();
			if(rs.next()){
				if(rs.getInt(1)!=0){
					cycle=rs.getInt("CYCLE");
					cycle++;
				}
				else
					cycle=1;
			}
			
		ps=conn.prepareStatement(uploadQuery);
		ps.setLong(1, loginId);
		ps.setString(2, absFile);
		ps.setInt(3, cycle);
		ps.setString(4, monthFr);
		int i=ps.executeUpdate();
		
		if(i==0){
			message="failure";
			logger.info("+++++++++++DB ENTRY WAS NOT MADE FOR THE FILE UPLOAD+++++++++++");
		}
		else
			message="SUCCESS";
			
		}catch(SQLException sqlExp){
			sqlExp.printStackTrace();
			throw new Exception();
			
		}finally{
			conn.commit();
			DBConnectionManager.releaseResources(conn);
			DBConnectionManager.releaseResources(ps,rs);
			
		}
		
		return message;
	}


	@Override
	public List<InvoiceDetails> listInvoicesAcc(String status) throws Exception {
		// TODO Auto-generated method stub
		Connection con;
		PreparedStatement ps=null;
		ResultSet rs=null;
		
		try {
			con=DBConnectionManager.getDBConnection();
		} catch (DAOException e) {
			logger.info("Error in DB Connection");
			e.printStackTrace();
			throw new Exception();
		
		}
		
		List<InvoiceDetails> invList=new ArrayList<InvoiceDetails>();
		
		try{
		ps=con.prepareStatement(LIST_ACC_INVOICE);
		ps.setString(1, status);
		ps.setString(2, status);
		
		rs=ps.executeQuery();
		
		while(rs.next()){
			
			InvoiceDetails invDetail=new InvoiceDetails();
			
			invDetail.setBillNo(rs.getString(3));
			invDetail.setInvoiceNo(rs.getString(4));
			invDetail.setOlmId(rs.getString(1));
			invDetail.setPartnerNm(rs.getString(2));
			invDetail.setMonthFor(rs.getString(5));
			
			invList.add(invDetail);
			
		}
		}catch(Exception exp){
			exp.printStackTrace();
			logger.info("Exception in loading invoice data");
		}finally{
			
			DBConnectionManager.releaseResources(con);
			DBConnectionManager.releaseResources(ps, rs);
		}
		
		return invList;
	}


	@Override
	public List<InvoiceDetails> listInvoicesDist(String loginNm, String statFlag)
			throws Exception {
		Connection con;
		PreparedStatement ps=null;
		ResultSet rs=null;
		
		try {
			con=DBConnectionManager.getDBConnection();
		} catch (DAOException e) {
			logger.info("Error in DB Connection");
			e.printStackTrace();
			throw new Exception();
		
		}
		
		List<InvoiceDetails> invList=new ArrayList<InvoiceDetails>();
		
		try{
		ps=con.prepareStatement(LIST_INVOICES_DIST);
		ps.setString(1, statFlag);
		ps.setString(2,loginNm);
		ps.setString(3,statFlag);
		ps.setString(4,loginNm);
		
		logger.info("Login Name="+loginNm);
		rs=ps.executeQuery();
		
		while(rs.next()){
			
			InvoiceDetails invDetail=new InvoiceDetails();
			
			invDetail.setBillNo(rs.getString(3));
			invDetail.setInvoiceNo(rs.getString(4));
			invDetail.setOlmId(rs.getString(1));
			invDetail.setPartnerNm(rs.getString(2));
			invDetail.setMonthFor(rs.getString(5));
			
			invList.add(invDetail);
			
		}
		}catch(Exception exp){
			
			logger.info("Exception in loading invoice data");
		}finally{
			
			DBConnectionManager.releaseResources(con);
			DBConnectionManager.releaseResources(ps, rs);
		}
		
		return invList;
	}
	
	@Override
	public List<InvoiceDetails> listAllInvoicesDist(String loginNm)
			throws Exception {
		Connection con;
		PreparedStatement ps=null;
		ResultSet rs=null;
		
		try {
			con=DBConnectionManager.getDBConnection();
		} catch (DAOException e) {
			logger.info("Error in DB Connection");
			e.printStackTrace();
			throw new Exception();
		
		}
		
		List<InvoiceDetails> invList=new ArrayList<InvoiceDetails>();
		
		try{
		ps=con.prepareStatement(LIST_ALL_INVOICE);
		ps.setString(1, loginNm);
		ps.setString(2,loginNm);
		
		
		logger.info("Login Name="+loginNm);
		rs=ps.executeQuery();
		
		while(rs.next()){
			
			InvoiceDetails invDetail=new InvoiceDetails();
			
			invDetail.setBillNo(rs.getString(3));
			invDetail.setInvoiceNo(rs.getString(4));
			invDetail.setOlmId(rs.getString(1));
			invDetail.setPartnerNm(rs.getString(2));
			invDetail.setMonthFor(rs.getString(5));
			invDetail.setStatus(rs.getString(6));
			
			invList.add(invDetail);
			
		}
		}catch(Exception exp){
			
			logger.info("Exception in loading invoice data");
		}finally{
			
			DBConnectionManager.releaseResources(con);
			DBConnectionManager.releaseResources(ps, rs);
		}
		
		return invList;
	}


	@Override
	public PartnerDetails partnerDetails(String olmId) throws Exception {
		// TODO Auto-generated method stub
	
			Connection con;
			PreparedStatement ps=null;
			ResultSet rs=null;
			
			try {
				con=DBConnectionManager.getDBConnection();
			} catch (DAOException e) {
				logger.info("Error in DB Connection");
				e.printStackTrace();
				throw new Exception();
			
			}
			
			PartnerDetails partnerDetail=new PartnerDetails();
			
			try{
			ps=con.prepareStatement(PARTNER_DETAILS_QUERY);
			ps.setString(1, olmId);
			
			
			
			logger.info("Login Name="+olmId);
			rs=ps.executeQuery();
			
			while(rs.next()){
				
				partnerDetail.setAccountName(rs.getString("ACCOUNT_NAME"));
				partnerDetail.setAccountAddress(rs.getString("ACCOUNT_ADDRESS"));
				partnerDetail.setAccAddress2(rs.getString("ADDRESS_2"));
				partnerDetail.setPanNo(rs.getString("PAN_NO"));
				partnerDetail.setServcTxNo(rs.getString("SERVICE_TAX_NO"));
				partnerDetail.setTinNo(rs.getString("TIN_NO"));
				
				
				
			}
			}catch(Exception exp){
				
				logger.info("Exception in loading invoice data");
			}finally{
				
				DBConnectionManager.releaseResources(con);
				DBConnectionManager.releaseResources(ps, rs);
			}
		return partnerDetail;
	}


	@Override
	public RateDTO fetchRates(java.util.Date billDt) throws Exception {
		// TODO Auto-generated method stub
		

		Connection con;
		PreparedStatement ps=null;
		ResultSet rs=null;
		
		try {
			con=DBConnectionManager.getDBConnection();
		} catch (DAOException e) {
			logger.info("Error in DB Connection");
			e.printStackTrace();
			throw new Exception();
		
		}
		RateDTO rates=new RateDTO();
		
		try{
			ps=con.prepareStatement(BILL_RATES_QUERY);
			//convert java.util.Date to javax.sql.Date
			Timestamp dateOfBill=new Timestamp(billDt.getTime());
			ps.setTimestamp(1,dateOfBill);
		//	ps.setTimestamp(2, dateOfBill);
			
			rs=ps.executeQuery();
			
			while(rs.next()){
				rates.setStbRateT1(rs.getInt("STD_STB_RATE_T1"));
				rates.setStbRateT2(rs.getInt("STD_STB_RATE_T2"));
				rates.setStbRateT3(rs.getInt("STD_STB_RATE_T3"));
				
				rates.setHdRateT1(rs.getInt("HD_STB_RATE_T1"));
				rates.setHdRateT2(rs.getInt("HD_STB_RATE_T2"));
				rates.setHdRateT3(rs.getInt("HD_STB_RATE_T3"));
				
				rates.setDvrRateT1(rs.getInt("DVR_STB_RATE_T1"));
				rates.setDvrRateT2(rs.getInt("DVR_STB_RATE_T2"));
				rates.setDvrRateT3(rs.getInt("DVR_STB_RATE_T3"));
				
				rates.setMultiSdT1(rs.getInt("MULTI_CONN_STD_T1"));
				rates.setMultiSdT2(rs.getInt("MULTI_CONN_STD_T2"));
				rates.setMultiSdT3(rs.getInt("MULTI_CONN_STD_T3"));
				
				rates.setMultiHdT1(rs.getInt("MULTI_CONN_STD_T1"));
				rates.setMultiHdT2(rs.getInt("MULTI_CONN_STD_T2"));
				rates.setMultiHdT3(rs.getInt("MULTI_CONN_STD_T3"));
				
				rates.setMultiDvrT1(rs.getInt("MULTI_DVR_T1"));
				rates.setMultiDvrT2(rs.getInt("MULTI_DVR_T2"));
				rates.setMultiDvrT3(rs.getInt("MULTI_DVR_T3"));
				
				rates.setEduCess(rs.getDouble("EDU_CESS_RT"));
				rates.setServiceTx(rs.getDouble("SERVICE_TX_RT"));
				rates.setHdeuCess(rs.getDouble("HEDU_CESS_RT"));
				
				rates.setUpgradeHd(rs.getInt("UGRADE_HD"));
				rates.setUpgradeDvr(rs.getInt("UPGRADE_DVR"));
				rates.setKkcess(rs.getDouble("KKC_RT"));
				
			}
		}catch(Exception exp){
			
			logger.info("Exception in loading invoice data");
		}finally{
			
			DBConnectionManager.releaseResources(con);
			DBConnectionManager.releaseResources(ps, rs);
		}
			
		
		return rates;
	}
	public String fetchAddress() throws Exception
	{
		
		Connection con;
		PreparedStatement ps=null;
		ResultSet rs=null;
		
		try {
			con=DBConnectionManager.getDBConnection();
			
			
		} catch (DAOException e) {
			logger.info("Error in DB Connection");
			e.printStackTrace();
			throw new Exception();
		
		}
		
		InvoiceForm frm= new InvoiceForm();
		try{
			ps=con.prepareStatement(FETCH_ADDRESS);
						
			rs=ps.executeQuery();
			
			while(rs.next()){
				frm.setAddress(rs.getString("VALUE"));
				
			}
		}catch(Exception exp){
			
			logger.info("Exception in fetching address of Airtel corporate office");
		}
        finally{
			
			DBConnectionManager.releaseResources(con);
			DBConnectionManager.releaseResources(ps, rs);
		}
		
		String address= frm.getAddress();
		logger.info("Address in db"+address);
		return address;
		
	}

	
	
}
