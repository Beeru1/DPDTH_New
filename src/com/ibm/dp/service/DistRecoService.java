package com.ibm.dp.service;

import java.io.InputStream;
import java.util.List;

import org.apache.struts.upload.FormFile;

import com.ibm.dp.dto.DistRecoDto;
import com.ibm.dp.dto.PrintRecoDto;
import com.ibm.dp.dto.RecoPeriodDTO;
import com.ibm.dp.exception.DPServiceException;

public interface DistRecoService {
	public List<RecoPeriodDTO> getRecoPeriodList(String login_id) throws DPServiceException;
	public List<RecoPeriodDTO> getRecoPeriodListAdmin() throws DPServiceException;
	public List<DistRecoDto> getRecoProductList(String stbType, String recoPeriod,String distId,boolean result,String stockType,String productId) throws DPServiceException;
	public String submitDetail(DistRecoDto distRecoDto) throws DPServiceException;
	public List<PrintRecoDto> getRecoPrintList(String certificateId) throws DPServiceException;
	public boolean compareRecoGoLiveDate(String recoPeriod) throws DPServiceException;
	public List<DistRecoDto> getDetailsList(String productId, String columnId,Long distId,String tabId, String recoPeriodId) throws DPServiceException;
	public String uploadClosingStockDetailsXls(FormFile file,int stockType,int productId,Long distId,String indexes,int recoId) throws DPServiceException;
	public String uploadClosingStockDetailsXlsagain(FormFile file,int stockType,int productId,Long distId,String indexes,int recoId) throws DPServiceException;
	public String findNotOkStbs(Long distId,int recoId,String productId) throws DPServiceException;
	public String Uploadvalidation(Long distId,int recoId,String productId,int coloum ,String okWithSystemStock) throws DPServiceException;
	public List<DistRecoDto> getProductList(String recoPeriod,String distId ,String stockType) throws DPServiceException;
}
