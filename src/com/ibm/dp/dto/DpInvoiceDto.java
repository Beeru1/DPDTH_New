package com.ibm.dp.dto;

import java.util.Date;

public class DpInvoiceDto {

	private String bilNo;
	private String tinNo;
	private String panNo;
	private String srvcTxCt;
	private String olmId;
	private String partnerNm;
	private String Status;
	private String cstNam;
	private String cStAdd;
	private String status;
	private String remarks="";
	private String monthFr="";
	private Date bilDt;
	
	private int cperecNo;
	private int srrwdNo;
	private int retNo;
	private int othNo;
	private int others;
	private int others1;
	private int others2;
	private int weakGeo;
	private int hillyArea;
	private String finRemarks;
	
	public String getFinRemarks() {
		return finRemarks;
	}
	public void setFinRemarks(String finRemarks) {
		this.finRemarks = finRemarks;
	}
	private String amountInWords="";
	
	public String getAmountInWords() {
		return amountInWords;
	}
	public void setAmountInWords(String amountInWords) {
		this.amountInWords = amountInWords;
	}
	public int getOthers() {
		return others;
	}
	public void setOthers(int others) {
		this.others = others;
	}
	public int getOthers1() {
		return others1;
	}
	public void setOthers1(int others1) {
		this.others1 = others1;
	}
	public int getOthers2() {
		return others2;
	}
	public void setOthers2(int others2) {
		this.others2 = others2;
	}
	public int getWeakGeo() {
		return weakGeo;
	}
	public void setWeakGeo(int weakGeo) {
		this.weakGeo = weakGeo;
	}
	public int getHillyArea() {
		return hillyArea;
	}
	public void setHillyArea(int hillyArea) {
		this.hillyArea = hillyArea;
	}
	private String invNo;
	
	private double cperecRt;
	private double cperecAmt=0.0;
	private double srrwdRt;
	private double srrwdAmt=0.0;
	private double retRt;
	private double retAmt=0.0;
	private double othRt;
	private double othAmt=0.0;
	private double total=0.0;
	private double srvTxRt;
	private double srvTxAmt;
	private double eduCesRt;
	private double eduCesAmt;
	private double heduCesRt;
	private double heduCesAmt;
	private double grndTotal=0.0;
	
	private double kkcrt;
	private double kkcamt;
	
	private String asm;
	private String circle;
	private int dvrT1;
	private int dvrT2;
	private int dvrT3;
	
	private int hdextT1;
	private int hdextT2;
	private int hdextT3;
	
	private int multiT1;
	private int multiT2;
	private int multiT3;
	
	private int multidvrT1;
	private int multidvrT2;
	private int multidvrT3;
	
	private int totalAct;
	private int totalAmt;
	private int hdplusExt;
	private int dvr;
	private int amt;
	private int oneyrCount;
	private int oneyrAmt;
	private int fieldreprelAmt;
	private int arpPayout;
	private int totbasAmt;
	private int servTx;
	
	private int kkc;
	
	private int eduCes;
	private int heduCes;
	private int totinvAmt;
	private int totTax;
	private int tier;
	
	
	
	public String getAsm() {
		return asm;
	}
	public void setAsm(String asm) {
		this.asm = asm;
	}
	public String getCircle() {
		return circle;
	}
	public void setCircle(String circle) {
		this.circle = circle;
	}
	public int getDvrT1() {
		return dvrT1;
	}
	public void setDvrT1(int dvrT1) {
		this.dvrT1 = dvrT1;
	}
	public int getDvrT2() {
		return dvrT2;
	}
	public void setDvrT2(int dvrT2) {
		this.dvrT2 = dvrT2;
	}
	public int getDvrT3() {
		return dvrT3;
	}
	public void setDvrT3(int dvrT3) {
		this.dvrT3 = dvrT3;
	}
	public int getHdextT1() {
		return hdextT1;
	}
	public void setHdextT1(int hdextT1) {
		this.hdextT1 = hdextT1;
	}
	public int getHdextT2() {
		return hdextT2;
	}
	public void setHdextT2(int hdextT2) {
		this.hdextT2 = hdextT2;
	}
	public int getHdextT3() {
		return hdextT3;
	}
	public void setHdextT3(int hdextT3) {
		this.hdextT3 = hdextT3;
	}
	public int getMultiT1() {
		return multiT1;
	}
	public void setMultiT1(int multiT1) {
		this.multiT1 = multiT1;
	}
	public int getMultiT2() {
		return multiT2;
	}
	public void setMultiT2(int multiT2) {
		this.multiT2 = multiT2;
	}
	public int getMultiT3() {
		return multiT3;
	}
	public void setMultiT3(int multiT3) {
		this.multiT3 = multiT3;
	}
	public int getMultidvrT1() {
		return multidvrT1;
	}
	public void setMultidvrT1(int multidvrT1) {
		this.multidvrT1 = multidvrT1;
	}
	public int getMultidvrT2() {
		return multidvrT2;
	}
	public void setMultidvrT2(int multidvrT2) {
		this.multidvrT2 = multidvrT2;
	}
	public int getMultidvrT3() {
		return multidvrT3;
	}
	public void setMultidvrT3(int multidvrT3) {
		this.multidvrT3 = multidvrT3;
	}
	public int getTotalAct() {
		return totalAct;
	}
	public void setTotalAct(int totalAct) {
		this.totalAct = totalAct;
	}
	public int getTotalAmt() {
		return totalAmt;
	}
	public void setTotalAmt(int totalAmt) {
		this.totalAmt = totalAmt;
	}
	public int getHdplusExt() {
		return hdplusExt;
	}
	public void setHdplusExt(int hdplusExt) {
		this.hdplusExt = hdplusExt;
	}
	public int getDvr() {
		return dvr;
	}
	public void setDvr(int dvr) {
		this.dvr = dvr;
	}
	public int getAmt() {
		return amt;
	}
	public void setAmt(int amt) {
		this.amt = amt;
	}
	public int getOneyrCount() {
		return oneyrCount;
	}
	public void setOneyrCount(int oneyrCount) {
		this.oneyrCount = oneyrCount;
	}
	public int getOneyrAmt() {
		return oneyrAmt;
	}
	public void setOneyrAmt(int oneyrAmt) {
		this.oneyrAmt = oneyrAmt;
	}
	public int getFieldreprelAmt() {
		return fieldreprelAmt;
	}
	public void setFieldreprelAmt(int fieldreprelAmt) {
		this.fieldreprelAmt = fieldreprelAmt;
	}
	public int getArpPayout() {
		return arpPayout;
	}
	public void setArpPayout(int arpPayout) {
		this.arpPayout = arpPayout;
	}
	public int getTotbasAmt() {
		return totbasAmt;
	}
	public void setTotbasAmt(int totbasAmt) {
		this.totbasAmt = totbasAmt;
	}
	public int getServTx() {
		return servTx;
	}
	public void setServTx(int servTx) {
		this.servTx = servTx;
	}
	public int getEduCes() {
		return eduCes;
	}
	public void setEduCes(int eduCes) {
		this.eduCes = eduCes;
	}
	public int getHeduCes() {
		return heduCes;
	}
	public void setHeduCes(int heduCes) {
		this.heduCes = heduCes;
	}
	public int getTotinvAmt() {
		return totinvAmt;
	}
	public void setTotinvAmt(int totinvAmt) {
		this.totinvAmt = totinvAmt;
	}
	public int getTotTax() {
		return totTax;
	}
	public void setTotTax(int totTax) {
		this.totTax = totTax;
	}
	public void setCperecAmt(double cperecAmt) {
		this.cperecAmt = cperecAmt;
	}
	public void setSrrwdAmt(double srrwdAmt) {
		this.srrwdAmt = srrwdAmt;
	}
	public void setRetAmt(double retAmt) {
		this.retAmt = retAmt;
	}
	public void setOthAmt(double othAmt) {
		this.othAmt = othAmt;
	}
	public void setTotal(double total) {
		this.total = total;
	}
	public void setGrndTotal(double grndTotal) {
		this.grndTotal = grndTotal;
	}
	public String getBilNo() {
		return bilNo;
	}
	public void setBilNo(String bilNo) {
		this.bilNo = bilNo;
	}
	public String getTinNo() {
		return tinNo;
	}
	public void setTinNo(String tinNo) {
		this.tinNo = tinNo;
	}
	public String getPanNo() {
		return panNo;
	}
	public void setPanNo(String panNo) {
		this.panNo = panNo;
	}
	public String getSrvcTxCt() {
		return srvcTxCt;
	}
	public void setSrvcTxCt(String srvcTxCt) {
		this.srvcTxCt = srvcTxCt;
	}
	public String getOlmId() {
		return olmId;
	}
	public void setOlmId(String olmId) {
		this.olmId = olmId;
	}
	public String getPartnerNm() {
		return partnerNm;
	}
	public void setPartnerNm(String partnerNm) {
		this.partnerNm = partnerNm;
	}
	public String getStatus() {
		return Status;
	}
	public void setStatus(String status) {
		Status = status;
	}
	public String getCstNam() {
		return cstNam;
	}
	public void setCstNam(String cstNam) {
		this.cstNam = cstNam;
	}
	public String getCStAdd() {
		return cStAdd;
	}
	public void setCStAdd(String stAdd) {
		this.cStAdd = stAdd;
	}
	public Date getBilDt() {
		return bilDt;
	}
	public void setBilDt(Date bilDt) {
		this.bilDt = bilDt;
	}
	public  int getCperecNo() {
		return cperecNo;
	}
	public void setCperecNo(int recNo) {
		this.cperecNo = recNo;
	}
	public int getSrrwdNo() {
		return srrwdNo;
	}
	public void setSrrwdNo(int no) {
		this.srrwdNo = no;
	}
	public int getRetNo() {
		return retNo;
	}
	public void setRetNo(int retNo) {
		this.retNo = retNo;
	}
	public int getOthNo() {
		return othNo;
	}
	public void setOthNo(int othNo) {
		this.othNo = othNo;
	}
	public String getInvNo() {
		return invNo;
	}
	public void setInvNo(String invNo) {
		this.invNo = invNo;
	}
	public double getCperecRt() {
		return cperecRt;
	}
	public void setCperecRt(double cpeRecRt) {
		this.cperecRt = cpeRecRt;
	}
	public double getCperecAmt() {
		return cperecAmt;
	}
	public void setCperecAmt() {
		//CPERecAmt = recAmt;
		cperecAmt=cperecRt*cperecNo;
	}
	public double getSrrwdRt() {
		return srrwdRt;
	}
	public void setSrrwdRt(double rwdRt) {
		this.srrwdRt = rwdRt;
	}
	public double getSrrwdAmt() {
		return srrwdAmt;
	}
	public void setSrrwdAmt() {
		//SRRwdAmt = rwdAmt;
		srrwdAmt=srrwdRt*srrwdNo;
	}
	public double getRetRt() {
		return retRt;
	}
	public void setRetRt(double retRt) {
		this.retRt = retRt;
	}
	public double getRetAmt() {
		return retAmt;
	}
	public void setRetAmt() {
		retAmt = retRt*retNo;
	}
	public double getOthRt() {
		return othRt;
	}
	public void setOthRt(double othRt) {
		this.othRt = othRt;
	}
	public double getOthAmt() {
		return othAmt;
	}
	public void setOthAmt() {
		//this.othAmt = othAmt;
		this.othAmt=this.othRt*this.othNo;
	}
	public double getTotal() {
		return total;
	}
	public void setTotal() {
		//Total = (CPERecNo*CPERecRt)
		total=cperecAmt+srrwdAmt+retAmt+othAmt+others+others1;
	}
	public double getSrvTxRt() {
		return srvTxRt;
	}
	public void setSrvTxRt(double srvTxRt) {
		this.srvTxRt = srvTxRt;
	}
	public double getSrvTxAmt() {
		return srvTxAmt;
	}
	public void setSrvTxAmt(double srvTxAmt ) {
		this.srvTxAmt = srvTxAmt;
		//SrvTxAmt=(SrvTxRt/100)*Total;
	}
	public double getEduCesRt() {
		return eduCesRt;
	}
	public void setEduCesRt(double eduCesRt) {
		this.eduCesRt = eduCesRt;
	}
	public double getEduCesAmt() {
		return eduCesAmt;
	}
	public void setEduCesAmt(double eduCesAmt) {
		//EduCesAmt =(EduCesRt/100)*Total ;
		this.eduCesAmt=eduCesAmt;
	}
	public double getHeduCesRt() {
		return heduCesRt;
	}
	public void setHeduCesRt(double eduCesRt) {
		this.heduCesRt = eduCesRt;
	}
	public double getHeduCesAmt() {
		return heduCesAmt;
	}
	public void setHeduCesAmt(double eduCesAmt) {
		this.heduCesAmt = eduCesAmt;
		//HEduCesAmt=(HEduCesRt/100)*Total;
	}
	public double getGrndTotal() {
		return grndTotal;
	}
	public void setGrndTotal() {
		//GrndTotal;
		grndTotal=total+srvTxAmt+eduCesAmt+kkcamt;
	}
	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}
	public String getRemarks() {
		return remarks;
	}
	public void setMonthFr(String monthFr) {
		this.monthFr = monthFr;
	}
	public String getMonthFr() {
		return monthFr;
	}
	public void setTier(int tier) {
		this.tier = tier;
	}
	public int getTier() {
		return tier;
	}
	public void setKkcrt(double kkcrt) {
		this.kkcrt = kkcrt;
	}
	public double getKkcrt() {
		return kkcrt;
	}
	public void setKkcamt(double kkcamt) {
		this.kkcamt = kkcamt;
	}
	public double getKkcamt() {
		return kkcamt;
	}
	public void setKkc(int kkc) {
		this.kkc = kkc;
	}
	public int getKkc() {
		return kkc;
	}
	
	
	
	

}
