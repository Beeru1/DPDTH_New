package com.ibm.dp.dao;

import java.util.List;

import com.ibm.dp.dto.InterSSDStockTrnfrReportDto;
import com.ibm.virtualization.recharge.exception.DAOException;

public interface InterSSDStockTrnfrReportDao {
	public List<InterSSDStockTrnfrReportDto> getReportExcel(String circleId , String fromDate,String toDate,int loginRole,String loginCircleId)throws DAOException;
}
