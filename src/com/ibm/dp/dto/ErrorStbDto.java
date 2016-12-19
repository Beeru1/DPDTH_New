package com.ibm.dp.dto;

import java.sql.Timestamp;

public class ErrorStbDto {
	private int    errorID;
	private int    distId;
	private int    prNO;
	private String poNo="";
	private Timestamp poDt;
	private String dcNo="";
	private Timestamp dcDt;
	private String serialNo="";
	private int    prodId;
	private int    prodQty;
	private int    conflictStatus;
	private Timestamp createdOn;
	private int    oldProdId;
	private String oldPoNo="";
	private int    oldDistId;
    private Timestamp oldPoAcceptDt;
    private String oldStbStatus="";
    private String btProductCode="";
	private String invoiceNo=""; 
	private Timestamp oldInvChangeDt;
    private String oldCollectionId="";
	private int    oldDefectId;
    private Timestamp oldReverceCollectionDt;
	private String oldRevDcNo="";
	private Timestamp oldRevDcDt;
	
	public void reset()
	{
		createdOn = null;
		oldProdId=0;
		oldPoNo="";
		oldDistId=0;
	    oldPoAcceptDt=null;
	    oldStbStatus="";
		oldInvChangeDt=null;
	    oldCollectionId="";
		oldDefectId=0;
	    oldReverceCollectionDt=null;
	    oldRevDcNo="";
		oldRevDcDt=null;		
	}

	public String getBtProductCode() {
		return btProductCode;
	}

	public void setBtProductCode(String btProductCode) {
		this.btProductCode = btProductCode;
	}

	public int getConflictStatus() {
		return conflictStatus;
	}

	public void setConflictStatus(int conflictStatus) {
		this.conflictStatus = conflictStatus;
	}

	public Timestamp getCreatedOn() {
		return createdOn;
	}

	public void setCreatedOn(Timestamp createdOn) {
		this.createdOn = createdOn;
	}

	public Timestamp getDcDt() {
		return dcDt;
	}

	public void setDcDt(Timestamp dcDt) {
		this.dcDt = dcDt;
	}

	public String getDcNo() {
		return dcNo;
	}

	public void setDcNo(String dcNo) {
		this.dcNo = dcNo;
	}

	public int getDistId() {
		return distId;
	}

	public void setDistId(int distId) {
		this.distId = distId;
	}

	public int getErrorID() {
		return errorID;
	}

	public void setErrorID(int errorID) {
		this.errorID = errorID;
	}

	public String getInvoiceNo() {
		return invoiceNo;
	}

	public void setInvoiceNo(String invoiceNo) {
		this.invoiceNo = invoiceNo;
	}

	public String getOldCollectionId() {
		return oldCollectionId;
	}

	public void setOldCollectionId(String oldCollectionId) {
		this.oldCollectionId = oldCollectionId;
	}

	public int getOldDefectId() {
		return oldDefectId;
	}

	public void setOldDefectId(int oldDefectId) {
		this.oldDefectId = oldDefectId;
	}

	public int getOldDistId() {
		return oldDistId;
	}

	public void setOldDistId(int oldDistId) {
		this.oldDistId = oldDistId;
	}

	public Timestamp getOldInvChangeDt() {
		return oldInvChangeDt;
	}

	public void setOldInvChangeDt(Timestamp oldInvChangeDt) {
		this.oldInvChangeDt = oldInvChangeDt;
	}

	public Timestamp getOldPoAcceptDt() {
		return oldPoAcceptDt;
	}

	public void setOldPoAcceptDt(Timestamp oldPoAcceptDt) {
		this.oldPoAcceptDt = oldPoAcceptDt;
	}

	public String getOldPoNo() {
		return oldPoNo;
	}

	public void setOldPoNo(String oldPoNo) {
		this.oldPoNo = oldPoNo;
	}

	public int getOldProdId() {
		return oldProdId;
	}

	public void setOldProdId(int oldProdId) {
		this.oldProdId = oldProdId;
	}

	public Timestamp getOldRevDcDt() {
		return oldRevDcDt;
	}

	public void setOldRevDcDt(Timestamp oldRevDcDt) {
		this.oldRevDcDt = oldRevDcDt;
	}

	public String getOldRevDcNo() {
		return oldRevDcNo;
	}

	public void setOldRevDcNo(String oldRevDcNo) {
		this.oldRevDcNo = oldRevDcNo;
	}

	public Timestamp getOldReverceCollectionDt() {
		return oldReverceCollectionDt;
	}

	public void setOldReverceCollectionDt(Timestamp oldReverceCollectionDt) {
		this.oldReverceCollectionDt = oldReverceCollectionDt;
	}

	public String getOldStbStatus() {
		return oldStbStatus;
	}

	public void setOldStbStatus(String oldStbStatus) {
		this.oldStbStatus = oldStbStatus;
	}

	public Timestamp getPoDt() {
		return poDt;
	}

	public void setPoDt(Timestamp poDt) {
		this.poDt = poDt;
	}

	public String getPoNo() {
		return poNo;
	}

	public void setPoNo(String poNo) {
		this.poNo = poNo;
	}

	public int getPrNO() {
		return prNO;
	}

	public void setPrNO(int prNO) {
		this.prNO = prNO;
	}

	public int getProdId() {
		return prodId;
	}

	public void setProdId(int prodId) {
		this.prodId = prodId;
	}

	public int getProdQty() {
		return prodQty;
	}

	public void setProdQty(int prodQty) {
		this.prodQty = prodQty;
	}

	public String getSerialNo() {
		return serialNo;
	}

	public void setSerialNo(String serialNo) {
		this.serialNo = serialNo;
	}
	
}
