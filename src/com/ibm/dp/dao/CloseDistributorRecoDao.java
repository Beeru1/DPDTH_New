package com.ibm.dp.dao;

import java.io.InputStream;
import java.util.List;
import java.util.Map;

import com.ibm.dp.beans.RecoPeriodConfFormBean;
import com.ibm.dp.dto.DistRecoDto;
import com.ibm.dp.dto.DuplicateSTBDTO;
import com.ibm.dp.dto.RecoDetailReportDTO;
import com.ibm.dp.dto.RecoPeriodDTO;
import com.ibm.dp.exception.DAOException;
import com.ibm.dpmisreports.common.SelectionCollection;

public interface CloseDistributorRecoDao {
	public List<RecoPeriodDTO> getRecoHistory() throws Exception;
	public List<RecoDetailReportDTO> getRecoReportDetails(int recoID, String circleId,String productId, Integer groupId, long accountID) throws Exception;
	//List getRecoPrintList(String recoID,String circleid,String selectedProductId) throws DAOException ;
	List getRecoPrintList(String recoID,String circleid,String selectedProductId, int intSelectedTsmId,String hiddenDistId) throws DAOException ;
	public List<SelectionCollection> getCircleListDAO(long accountID) throws DAOException ;
	public List<RecoPeriodConfFormBean> getTsmList(int circleId) throws DAOException ;//added by aman 
	public List<RecoPeriodConfFormBean> getDistList(int tsmId) throws DAOException ; //added by aman
	public List<DistRecoDto> getExportToExcel(String circleList,
			String tsmList, String distList, String prodList,
			String recoPeriod, String distType)  throws DAOException ; //added by Rohit
	public Map uploadExcel(InputStream inputstream, String recoId)throws DAOException;
	public String uploadToSystem(List<DistRecoDto> list ,List<DuplicateSTBDTO> uploadList, List<DuplicateSTBDTO> queryList, String recoPeriodToDate, String loginUserId)throws DAOException;
	public List<RecoPeriodDTO> getRecoHistoryNotClosed() throws Exception;
}
