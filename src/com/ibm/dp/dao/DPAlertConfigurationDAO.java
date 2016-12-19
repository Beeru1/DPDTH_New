package com.ibm.dp.dao;

import java.util.List;

import com.ibm.dp.dto.AlertConfigurationDTO;
import com.ibm.dp.exception.DPServiceException;
import com.ibm.virtualization.recharge.exception.DAOException;

public interface DPAlertConfigurationDAO {
	
	public List<AlertConfigurationDTO> getAlertList() throws DAOException;
	public int updateAlertStatus(int[] idStatuschanged,boolean[] changedStatus)throws DAOException;
	
	public List<AlertConfigurationDTO> getuserGroupId(int groupId,String loginId)throws DAOException;
	public int otherupdateStatus(int[] alertId, int[] status, List<AlertConfigurationDTO> userGroupId,String loginId)throws DAOException;
	public AlertConfigurationDTO getTsmGracePeriod()throws DAOException;
	public AlertConfigurationDTO getZsmGracePeriod()throws DAOException;
	public AlertConfigurationDTO updateTsmGracePeriod(String tsmGracePeriod)throws DAOException;
	public AlertConfigurationDTO updateZsmGracePeriod(String zsmGracePeriod)throws DAOException;
}
