package com.ibm.dp.beans;



import java.util.ArrayList;
import java.util.List;

import org.apache.struts.action.ActionForm;

import com.ibm.dp.dto.RecoSummaryDTO;

public class RecoSummaryFormBean extends ActionForm
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
private ArrayList<RecoSummaryDTO> recoSummaryList = new ArrayList<RecoSummaryDTO>();
	
private ArrayList<RecoSummaryDTO> recoSummaryDTOList = new ArrayList<RecoSummaryDTO>();
	
	private String recoStatus;
	
	private String recoPeriod;
	private String recoID = "";
	
	private List recoListTotal = new ArrayList();
	
	private List   recoStatusList;
	
	private Integer circleId;
	
	private String[] circleIdArr = null;
	
	private List circleList;
	
	private String showTSM;
	
	private String groupId;
	public String getGroupId() {
		return groupId;
	}

	public void setGroupId(String groupId) {
		this.groupId = groupId;
	}

	public String getShowTSM() {
		return showTSM;
	}

	public void setShowTSM(String showTSM) {
		this.showTSM = showTSM;
	}

	public Integer getCircleId() {
		return circleId;
	}

	public void setCircleId(Integer circleId) {
		this.circleId = circleId;
	}

	public String[] getCircleIdArr() {
		return circleIdArr;
	}

	public void setCircleIdArr(String[] circleIdArr) {
		this.circleIdArr = circleIdArr;
	}

	public List getCircleList() {
		return circleList;
	}

	public void setCircleList(List circleList) {
		this.circleList = circleList;
	}

	public String getRecoStatus() {
		return recoStatus;
	}

	public void setRecoStatus(String recoStatus) {
		this.recoStatus = recoStatus;
	}

	public List getRecoStatusList() {
		return recoStatusList;
	}

	public void setRecoStatusList(List recoStatusList) {
		this.recoStatusList = recoStatusList;
	}

	public ArrayList<RecoSummaryDTO> getRecoSummaryDTOList() {
		return recoSummaryDTOList;
	}

	public void setRecoSummaryDTOList(ArrayList<RecoSummaryDTO> recoSummaryDTOList) {
		this.recoSummaryDTOList = recoSummaryDTOList;
	}

	public ArrayList<RecoSummaryDTO> getRecoSummaryList() {
		return recoSummaryList;
	}

	public void setRecoSummaryList(ArrayList<RecoSummaryDTO> recoSummaryList) {
		this.recoSummaryList = recoSummaryList;
	}

	public String getRecoPeriod() {
		return recoPeriod;
	}

	public void setRecoPeriod(String recoPeriod) {
		this.recoPeriod = recoPeriod;
	}

	public List getRecoListTotal() {
		return recoListTotal;
	}

	public void setRecoListTotal(List recoListTotal) {
		this.recoListTotal = recoListTotal;
	}

	public String getRecoID() {
		return recoID;
	}

	public void setRecoID(String recoID) {
		this.recoID = recoID;
	}

}
