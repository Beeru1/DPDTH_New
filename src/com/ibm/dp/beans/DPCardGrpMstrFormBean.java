//aman

package com.ibm.dp.beans;



import java.util.ArrayList;
import java.util.List;

import org.apache.struts.action.ActionForm;

import com.ibm.dp.dto.CardMstrDto;
import com.ibm.dp.dto.RecoSummaryDTO;

public class DPCardGrpMstrFormBean extends ActionForm
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	

private String cardgrpId;
private String cardgrpName;
private String message;
private String createdBy;
private String updatedBy;
private String status;
private boolean checkDuplicateFlag;
List cardGroupList=new ArrayList<CardMstrDto>();



public List getCardGroupList() {
	return cardGroupList;
}
public void setCardGroupList(List cardGroupList) {
	this.cardGroupList = cardGroupList;
}
public String getCardgrpId() {
	return cardgrpId;
}
public void setCardgrpId(String cardgrpId) {
	this.cardgrpId = cardgrpId;
}
public String getCardgrpName() {
	return cardgrpName;
}
public void setCardgrpName(String cardgrpName) {
	this.cardgrpName = cardgrpName;
}
public String getMessage() {
	return message;
}
public void setMessage(String message) {
	this.message = message;
}
public String getCreatedBy() {
	return createdBy;
}
public void setCreatedBy(String createdBy) {
	this.createdBy = createdBy;
}
public String getUpdatedBy() {
	return updatedBy;
}
public void setUpdatedBy(String updatedBy) {
	this.updatedBy = updatedBy;
}
public String getStatus() {
	return status;
}
public void setStatus(String status) {
	this.status = status;
}
public boolean isCheckDuplicateFlag() {
	return checkDuplicateFlag;
}
public void setCheckDuplicateFlag(boolean checkDuplicateFlag) {
	this.checkDuplicateFlag = checkDuplicateFlag;
}


}
