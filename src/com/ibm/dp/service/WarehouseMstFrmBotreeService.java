package com.ibm.dp.service;


import com.ibm.dp.dto.WarehouseMstFrmBotreeDTO;
import com.ibm.virtualization.recharge.exception.VirtualizationServiceException;

public interface WarehouseMstFrmBotreeService {
	
	int fetchCircleId(String circelCode)throws VirtualizationServiceException;
	
	String[] updateWhtMasterData(WarehouseMstFrmBotreeDTO whDTO)throws VirtualizationServiceException;
}
