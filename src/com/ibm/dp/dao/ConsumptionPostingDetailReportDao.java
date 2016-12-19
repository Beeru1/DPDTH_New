package com.ibm.dp.dao;

import java.util.List;

import com.ibm.dp.dto.ConsumptionPostingDetailReportDto;
import com.ibm.dp.dto.DistributorDTO;
import com.ibm.virtualization.recharge.exception.DAOException;

public interface ConsumptionPostingDetailReportDao {
	public List<DistributorDTO> getAllDistList(String strLoginId,String strAccLevel) throws DAOException ;
	public List<ConsumptionPostingDetailReportDto> getReportExcel(int distId , String fromDate,String toDate, String circleId,int selectedCircleId,String strTSMId) throws DAOException;
	public List getRevLogTsmAccountDetails( int levelId) throws DAOException; 
	public List getRevLogDistAccountDetails( int levelId , int circleId) throws DAOException;
	
}
