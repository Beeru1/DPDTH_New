package com.ibm.dp.dto;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

public class HierarchyTransferDto {
	
	private String rdbTransfer;
	private String fromDistributorId;
	private String fromFSEIdsForFseTrans;
	private String fromFSEIdForRtlrTrans;
	private String toFSEIdForRtlrTrans;
	private String rdbWithAndWithoutRtlr;
	private String methodName;
	private String strSuccessMsg;
	private String strLoginId;
	private String strRetailerId;
	private String hiddenFSESelecIds;
	private String hiddenRetlrSelecIds;
	private String circleName;
	private String circleIdNew;
	private String fromTsmId;
	private String fromDistId;
	private String toDistId;
	private Integer productId;
	private String  serialNo;
	private Integer fromDist;
	private Integer todist;
	private Integer fromFse;
	private Integer toFse;
	private Integer fromRetailer;
	private Integer toRetailer;
	private Integer transfer_by;
	private Timestamp transferDate;
	private String transId;
	
	
	public String getFromDistributorId() {
		return fromDistributorId;
	}
	public void setFromDistributorId(String fromDistributorId) {
		this.fromDistributorId = fromDistributorId;
	}
	public String getFromFSEIdForRtlrTrans() {
		return fromFSEIdForRtlrTrans;
	}
	public void setFromFSEIdForRtlrTrans(String fromFSEIdForRtlrTrans) {
		this.fromFSEIdForRtlrTrans = fromFSEIdForRtlrTrans;
	}
	public String getFromFSEIdsForFseTrans() {
		return fromFSEIdsForFseTrans;
	}
	public void setFromFSEIdsForFseTrans(String fromFSEIdsForFseTrans) {
		this.fromFSEIdsForFseTrans = fromFSEIdsForFseTrans;
	}
	public String getHiddenFSESelecIds() {
		return hiddenFSESelecIds;
	}
	public void setHiddenFSESelecIds(String hiddenFSESelecIds) {
		this.hiddenFSESelecIds = hiddenFSESelecIds;
	}
	public String getHiddenRetlrSelecIds() {
		return hiddenRetlrSelecIds;
	}
	public void setHiddenRetlrSelecIds(String hiddenRetlrSelecIds) {
		this.hiddenRetlrSelecIds = hiddenRetlrSelecIds;
	}
	public String getMethodName() {
		return methodName;
	}
	public void setMethodName(String methodName) {
		this.methodName = methodName;
	}
	public String getRdbTransfer() {
		return rdbTransfer;
	}
	public void setRdbTransfer(String rdbTransfer) {
		this.rdbTransfer = rdbTransfer;
	}
	public String getRdbWithAndWithoutRtlr() {
		return rdbWithAndWithoutRtlr;
	}
	public void setRdbWithAndWithoutRtlr(String rdbWithAndWithoutRtlr) {
		this.rdbWithAndWithoutRtlr = rdbWithAndWithoutRtlr;
	}
	public String getStrLoginId() {
		return strLoginId;
	}
	public void setStrLoginId(String strLoginId) {
		this.strLoginId = strLoginId;
	}
	public String getStrRetailerId() {
		return strRetailerId;
	}
	public void setStrRetailerId(String strRetailerId) {
		this.strRetailerId = strRetailerId;
	}
	public String getStrSuccessMsg() {
		return strSuccessMsg;
	}
	public void setStrSuccessMsg(String strSuccessMsg) {
		this.strSuccessMsg = strSuccessMsg;
	}
	public String getCircleIdNew() {
		return circleIdNew;
	}
	public void setCircleIdNew(String circleIdNew) {
		this.circleIdNew = circleIdNew;
	}
	public String getCircleName() {
		return circleName;
	}
	public void setCircleName(String circleName) {
		this.circleName = circleName;
	}
	public String getFromDistId() {
		return fromDistId;
	}
	public void setFromDistId(String fromDistId) {
		this.fromDistId = fromDistId;
	}
	public String getFromTsmId() {
		return fromTsmId;
	}
	public void setFromTsmId(String fromTsmId) {
		this.fromTsmId = fromTsmId;
	}
	public String getToDistId() {
		return toDistId;
	}
	public void setToDistId(String toDistId) {
		this.toDistId = toDistId;
	}
	public String getToFSEIdForRtlrTrans() {
		return toFSEIdForRtlrTrans;
	}
	public void setToFSEIdForRtlrTrans(String toFSEIdForRtlrTrans) {
		this.toFSEIdForRtlrTrans = toFSEIdForRtlrTrans;
	}
	public Integer getFromDist() {
		return fromDist;
	}
	public void setFromDist(Integer fromDist) {
		this.fromDist = fromDist;
	}
	public Integer getFromFse() {
		return fromFse;
	}
	public void setFromFse(Integer fromFse) {
		this.fromFse = fromFse;
	}
	public Integer getFromRetailer() {
		return fromRetailer;
	}
	public void setFromRetailer(Integer fromRetailer) {
		this.fromRetailer = fromRetailer;
	}
	public Integer getProductId() {
		return productId;
	}
	public void setProductId(Integer productId) {
		this.productId = productId;
	}
	public String getSerialNo() {
		return serialNo;
	}
	public void setSerialNo(String serialNo) {
		this.serialNo = serialNo;
	}
	public Integer getTodist() {
		return todist;
	}
	public void setTodist(Integer todist) {
		this.todist = todist;
	}
	public Integer getToFse() {
		return toFse;
	}
	public void setToFse(Integer toFse) {
		this.toFse = toFse;
	}
	public Integer getToRetailer() {
		return toRetailer;
	}
	public void setToRetailer(Integer toRetailer) {
		this.toRetailer = toRetailer;
	}
	public Integer getTransfer_by() {
		return transfer_by;
	}
	public void setTransfer_by(Integer transfer_by) {
		this.transfer_by = transfer_by;
	}
	public Timestamp getTransferDate() {
		return transferDate;
	}
	public void setTransferDate(Timestamp transferDate) {
		this.transferDate = transferDate;
	}
	public String getTransId() {
		return transId;
	}
	public void setTransId(String transId) {
		this.transId = transId;
	}
	
}
