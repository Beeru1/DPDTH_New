package com.ibm.dp.dao;


import com.ibm.dp.dto.WarehouseMstFrmBotreeDTO;
import com.ibm.virtualization.recharge.exception.DAOException;

public interface WarehouseMstFrmBotreeDao {

	int fetchCircleId(String circleCode)throws DAOException;
	String[] updateWhtMasterData(WarehouseMstFrmBotreeDTO whDTO)throws DAOException;
}
