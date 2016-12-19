package com.ibm.dp.service;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import com.ibm.dp.dto.AlertConfigurationDTO;
import com.ibm.dp.exception.DPServiceException;


public interface DPAlertConfigurationService
{
	public List<AlertConfigurationDTO> getAlertList() throws DPServiceException;
	public List<AlertConfigurationDTO> getuserGroupId(int groupId , String login_id)throws DPServiceException;
	public int updateStatus(HttpServletRequest request,List<AlertConfigurationDTO> alertList) throws DPServiceException;
	public int otherupdateStatus(int[] alertId, int[] status, List<AlertConfigurationDTO> userGroupId,String loginId) throws DPServiceException;
	public AlertConfigurationDTO getTsmGracePeriod()throws DPServiceException;
	public AlertConfigurationDTO getZsmGracePeriod()throws DPServiceException;
	public AlertConfigurationDTO updateTsmGracePeriod(String tsmGracePeriod)throws DPServiceException;
	public AlertConfigurationDTO updateZsmGracePeriod(String zsmGracePeriod)throws DPServiceException;
}
