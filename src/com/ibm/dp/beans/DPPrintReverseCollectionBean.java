
package com.ibm.dp.beans;

import java.util.List;

import org.apache.struts.action.ActionForm;

import com.ibm.dp.dto.DPPrintBulkAcceptanceDTO;
import com.ibm.dp.dto.DPReverseCollectionDto;

public class DPPrintReverseCollectionBean extends ActionForm {

	private  List<DPReverseCollectionDto> upgradeCollectionDtoList;

	public List<DPReverseCollectionDto> getUpgradeCollectionDtoList() {
		return upgradeCollectionDtoList;
	}

	public void setUpgradeCollectionDtoList(
			List<DPReverseCollectionDto> upgradeCollectionDtoList) {
		this.upgradeCollectionDtoList = upgradeCollectionDtoList;
	}
	
}
