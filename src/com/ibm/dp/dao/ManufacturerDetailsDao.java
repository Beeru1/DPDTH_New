package com.ibm.dp.dao;
import java.sql.Connection;
import java.util.List;
import java.util.Map;

import com.ibm.dp.dto.ManufacturerDetailsDto;
import com.ibm.virtualization.recharge.exception.DAOException;

public interface ManufacturerDetailsDao {

	public List<ManufacturerDetailsDto> getManufacturerDetailsDao(Connection connection)throws DAOException;
	public void saveManufacturerDao(Connection connection,String manuList,String flag)throws DAOException;
	
}
