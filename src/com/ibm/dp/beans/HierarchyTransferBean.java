package com.ibm.dp.beans;

import java.util.ArrayList;
import java.util.List;

import org.apache.struts.action.ActionForm;

import com.ibm.dp.dto.CircleDto;
import com.ibm.dp.dto.DistributorDTO;
import com.ibm.dp.dto.FseDTO;
import com.ibm.dp.dto.RetailorDTO;
import com.ibm.dp.dto.TSMDto;


public class HierarchyTransferBean extends ActionForm{
	private String rdbTransfer="1";
	private String fromDistributorId;
	private String fromFSEIdsForFseTrans;
	private String fromFSEIdForRtlrTrans;
	private String toFSEIdForRtlrTrans;
	private String rdbWithAndWithoutRtlr ="1";
	private List<DistributorDTO> fromDistributorList= new ArrayList<DistributorDTO>();
	private List<FseDTO> fromFSEList= new ArrayList<FseDTO>();
	private String methodName;
	private String strSuccessMsg;
	private String strLoginId;
	private String strRetailerId;
	private List<RetailorDTO> retailorList= new ArrayList<RetailorDTO>();
	private String hiddenFSESelecIds;
	private String hiddenRetlrSelecIds;
	private List<CircleDto>  circleList  = new ArrayList<CircleDto>();
	private List<TSMDto>  fromTSMList  = new ArrayList<TSMDto>();
	private List<TSMDto>  toTSMList  = new ArrayList<TSMDto>();
	private String circleName;
	private String circleIdNew;
	private String fromTsmId;
	private String toTsmId;
	private String fromDistId;
	private String toDistId;
	private List<DistributorDTO>  fromDistList  = new ArrayList<DistributorDTO>();
	private List<DistributorDTO>  toDistList  = new ArrayList<DistributorDTO>();
	
	
	public List<DistributorDTO> getToDistList() {
		return toDistList;
	}
	public void setToDistList(List<DistributorDTO> toDistList) {
		this.toDistList = toDistList;
	}
	public List<DistributorDTO> getFromDistList() {
		return fromDistList;
	}
	public void setFromDistList(List<DistributorDTO> fromDistList) {
		this.fromDistList = fromDistList;
	}
	public String getToDistId() {
		return toDistId;
	}
	public void setToDistId(String toDistId) {
		this.toDistId = toDistId;
	}
	
	
	public String getFromDistributorId() {
		return fromDistributorId;
	}
	public void setFromDistributorId(String fromDistributorId) {
		this.fromDistributorId = fromDistributorId;
	}
	public List<DistributorDTO> getFromDistributorList() {
		return fromDistributorList;
	}
	public void setFromDistributorList(List<DistributorDTO> fromDistributorList) {
		this.fromDistributorList = fromDistributorList;
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
	public List<FseDTO> getFromFSEList() {
		return fromFSEList;
	}
	public void setFromFSEList(List<FseDTO> fromFSEList) {
		this.fromFSEList = fromFSEList;
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
	public String getStrSuccessMsg() {
		return strSuccessMsg;
	}
	public void setStrSuccessMsg(String strSuccessMsg) {
		this.strSuccessMsg = strSuccessMsg;
	}
	public String getStrLoginId() {
		return strLoginId;
	}
	public void setStrLoginId(String strLoginId) {
		this.strLoginId = strLoginId;
	}
	public List<RetailorDTO> getRetailorList() {
		return retailorList;
	}
	public void setRetailorList(List<RetailorDTO> retailorList) {
		this.retailorList = retailorList;
	}
	public String getStrRetailerId() {
		return strRetailerId;
	}
	public void setStrRetailerId(String strRetailerId) {
		this.strRetailerId = strRetailerId;
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
	public List<CircleDto> getCircleList() {
		return circleList;
	}
	public void setCircleList(List<CircleDto> circleList) {
		this.circleList = circleList;
	}
	public List<TSMDto> getFromTSMList() {
		return fromTSMList;
	}
	public void setFromTSMList(List<TSMDto> fromTSMList) {
		this.fromTSMList = fromTSMList;
	}
	public List<TSMDto> getToTSMList() {
		return toTSMList;
	}
	public void setToTSMList(List<TSMDto> toTSMList) {
		this.toTSMList = toTSMList;
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
	public String getFromTsmId() {
		return fromTsmId;
	}
	public void setFromTsmId(String fromTsmId) {
		this.fromTsmId = fromTsmId;
	}
	public String getFromDistId() {
		return fromDistId;
	}
	public void setFromDistId(String fromDistId) {
		this.fromDistId = fromDistId;
	}
	public String getToFSEIdForRtlrTrans() {
		return toFSEIdForRtlrTrans;
	}
	public void setToFSEIdForRtlrTrans(String toFSEIdForRtlrTrans) {
		this.toFSEIdForRtlrTrans = toFSEIdForRtlrTrans;
	}
	public String getToTsmId() {
		return toTsmId;
	}
	public void setToTsmId(String toTsmId) {
		this.toTsmId = toTsmId;
	}

}
