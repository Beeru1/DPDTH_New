package com.ibm.dp.dao;

import java.io.InputStream;
import java.util.List;

import com.ibm.dp.dto.WHdistmappbulkDto;
import com.ibm.virtualization.recharge.exception.DAOException;

public interface WHdistmappbulkDao {

	List uploadExcel(InputStream inputstream) throws DAOException;
	String addDistWarehouseMap(List list) throws DAOException;
	List<WHdistmappbulkDto> getALLWhDistMapData() throws DAOException;
}
