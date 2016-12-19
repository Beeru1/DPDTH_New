package com.ibm.dp.service;

import java.io.InputStream;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.ibm.dp.beans.EditRecoDetailBean;
import com.ibm.dp.dto.RecoPeriodDTO;
import com.ibm.dp.exception.DPServiceException;

public interface EditRecoDetailService {
	public List<RecoPeriodDTO> getrecoPeriodList() throws DPServiceException;
	public List validateExcel(InputStream inputstream) throws DPServiceException;
	public int updateRecoPeriod(List<RecoPeriodDTO> recoDtoList, int recoID, int gracePeriod) throws DPServiceException;

	
}
