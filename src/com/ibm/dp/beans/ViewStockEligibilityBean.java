package com.ibm.dp.beans;

import java.util.ArrayList;
import java.util.List;

import org.apache.struts.action.ActionForm;
import org.apache.struts.upload.FormFile;

import com.ibm.dp.dto.ViewStockEligibilityDTO;

public class ViewStockEligibilityBean extends ActionForm
{

	public List<ViewStockEligibilityDTO> viewStockEligibilityList = new ArrayList<ViewStockEligibilityDTO>();
	private String security;
	private String loan;
	private String balance;
	// added for extending eligibility report by aman 27/6/13
	private String olmId;
	private String intUserAcctLevel;
	private String methodName;
	private String strSuccessMsg;
	public List<ViewStockEligibilityDTO> stkEligExcelDetailList = new ArrayList<ViewStockEligibilityDTO>();
	private String strOLMID;
	private String strDistName;
	private String distOlmId;
	private String error_flag;
	private String strmsg;
	private FormFile uploadedFile;
	private List<ViewStockEligibilityDTO> regionList;
	private int regionId;
	private int selectedCircleId;
	private int productTypeId;
	private int distTypeId;
	private List error_file = new ArrayList();

	private double securityEligibilityBalanceSwap;
	private double securityEligibilityBalanceCommercial;
	private int securityDepositAvailableQty;
	private int swapSdEligibility;
	private int swapHdEligibility;
	private int swapPVREligibilityProductQty;
	private int swapHdDvrEligibility;
	private int swapHdPlusEligibility;
	private int camEligibilitySwap;
	private int securityDepositEligibility;
	private int sdBoxSdPlusEligibility;
	private int hdBoxEligibility;
	private int hdDvrBoxEligibility;
	private int camEligibilityCommerical;

	/**
	 * @return the securityEligibilityBalanceSwap
	 */
	public double getSecurityEligibilityBalanceSwap()
	{
		return securityEligibilityBalanceSwap;
	}

	/**
	 * @param securityEligibilityBalanceSwap
	 *            the securityEligibilityBalanceSwap to set
	 */
	public void setSecurityEligibilityBalanceSwap(double securityEligibilityBalanceSwap)
	{
		this.securityEligibilityBalanceSwap = securityEligibilityBalanceSwap;
	}

	/**
	 * @return the securityEligibilityBalanceCommercial
	 */
	public double getSecurityEligibilityBalanceCommercial()
	{
		return securityEligibilityBalanceCommercial;
	}

	/**
	 * @param securityEligibilityBalanceCommercial
	 *            the securityEligibilityBalanceCommercial to set
	 */
	public void setSecurityEligibilityBalanceCommercial(double securityEligibilityBalanceCommercial)
	{
		this.securityEligibilityBalanceCommercial = securityEligibilityBalanceCommercial;
	}

	/**
	 * @return the securityDepositAvailableQty
	 */
	public int getSecurityDepositAvailableQty()
	{
		return securityDepositAvailableQty;
	}

	/**
	 * @param securityDepositAvailableQty
	 *            the securityDepositAvailableQty to set
	 */
	public void setSecurityDepositAvailableQty(int securityDepositAvailableQty)
	{
		this.securityDepositAvailableQty = securityDepositAvailableQty;
	}

	/**
	 * @return the swapSdEligibility
	 */
	public int getSwapSdEligibility()
	{
		return swapSdEligibility;
	}

	/**
	 * @param swapSdEligibility
	 *            the swapSdEligibility to set
	 */
	public void setSwapSdEligibility(int swapSdEligibility)
	{
		this.swapSdEligibility = swapSdEligibility;
	}

	/**
	 * @return the swapHdEligibility
	 */
	public int getSwapHdEligibility()
	{
		return swapHdEligibility;
	}

	/**
	 * @param swapHdEligibility
	 *            the swapHdEligibility to set
	 */
	public void setSwapHdEligibility(int swapHdEligibility)
	{
		this.swapHdEligibility = swapHdEligibility;
	}

	/**
	 * @return the swapPVREligibilityProductQty
	 */
	public int getSwapPVREligibilityProductQty()
	{
		return swapPVREligibilityProductQty;
	}

	/**
	 * @param swapPVREligibilityProductQty
	 *            the swapPVREligibilityProductQty to set
	 */
	public void setSwapPVREligibilityProductQty(int swapPVREligibilityProductQty)
	{
		this.swapPVREligibilityProductQty = swapPVREligibilityProductQty;
	}

	/**
	 * @return the swapHdDvrEligibility
	 */
	public int getSwapHdDvrEligibility()
	{
		return swapHdDvrEligibility;
	}

	/**
	 * @param swapHdDvrEligibility
	 *            the swapHdDvrEligibility to set
	 */
	public void setSwapHdDvrEligibility(int swapHdDvrEligibility)
	{
		this.swapHdDvrEligibility = swapHdDvrEligibility;
	}

	/**
	 * @return the swapHdPlusEligibility
	 */
	public int getSwapHdPlusEligibility()
	{
		return swapHdPlusEligibility;
	}

	/**
	 * @param swapHdPlusEligibility
	 *            the swapHdPlusEligibility to set
	 */
	public void setSwapHdPlusEligibility(int swapHdPlusEligibility)
	{
		this.swapHdPlusEligibility = swapHdPlusEligibility;
	}

	/**
	 * @return the camEligibilitySwap
	 */
	public int getCamEligibilitySwap()
	{
		return camEligibilitySwap;
	}

	/**
	 * @param camEligibilitySwap
	 *            the camEligibilitySwap to set
	 */
	public void setCamEligibilitySwap(int camEligibilitySwap)
	{
		this.camEligibilitySwap = camEligibilitySwap;
	}

	/**
	 * @return the securityDepositEligibility
	 */
	public int getSecurityDepositEligibility()
	{
		return securityDepositEligibility;
	}

	/**
	 * @param securityDepositEligibility
	 *            the securityDepositEligibility to set
	 */
	public void setSecurityDepositEligibility(int securityDepositEligibility)
	{
		this.securityDepositEligibility = securityDepositEligibility;
	}

	/**
	 * @return the sdBoxSdPlusEligibility
	 */
	public int getSdBoxSdPlusEligibility()
	{
		return sdBoxSdPlusEligibility;
	}

	/**
	 * @param sdBoxSdPlusEligibility
	 *            the sdBoxSdPlusEligibility to set
	 */
	public void setSdBoxSdPlusEligibility(int sdBoxSdPlusEligibility)
	{
		this.sdBoxSdPlusEligibility = sdBoxSdPlusEligibility;
	}

	/**
	 * @return the hdBoxEligibility
	 */
	public int getHdBoxEligibility()
	{
		return hdBoxEligibility;
	}

	/**
	 * @param hdBoxEligibility
	 *            the hdBoxEligibility to set
	 */
	public void setHdBoxEligibility(int hdBoxEligibility)
	{
		this.hdBoxEligibility = hdBoxEligibility;
	}

	/**
	 * @return the hdDvrBoxEligibility
	 */
	public int getHdDvrBoxEligibility()
	{
		return hdDvrBoxEligibility;
	}

	/**
	 * @param hdDvrBoxEligibility
	 *            the hdDvrBoxEligibility to set
	 */
	public void setHdDvrBoxEligibility(int hdDvrBoxEligibility)
	{
		this.hdDvrBoxEligibility = hdDvrBoxEligibility;
	}

	/**
	 * @return the camEligibilityCommerical
	 */
	public int getCamEligibilityCommerical()
	{
		return camEligibilityCommerical;
	}

	/**
	 * @param camEligibilityCommerical
	 *            the camEligibilityCommerical to set
	 */
	public void setCamEligibilityCommerical(int camEligibilityCommerical)
	{
		this.camEligibilityCommerical = camEligibilityCommerical;
	}

	public List getError_file()
	{
		return error_file;
	}

	public void setError_file(List error_file)
	{
		this.error_file = error_file;
	}

	public int getProductTypeId()
	{
		return productTypeId;
	}

	public void setProductTypeId(int productTypeId)
	{
		this.productTypeId = productTypeId;
	}

	public int getDistTypeId()
	{
		return distTypeId;
	}

	public void setDistTypeId(int distTypeId)
	{
		this.distTypeId = distTypeId;
	}

	public String getDistOlmId()
	{
		return distOlmId;
	}

	public int getSelectedCircleId()
	{
		return selectedCircleId;
	}

	public void setSelectedCircleId(int selectedCircleId)
	{
		this.selectedCircleId = selectedCircleId;
	}

	public int getRegionId()
	{
		return regionId;
	}

	public void setRegionId(int regionId)
	{
		this.regionId = regionId;
	}

	public List<ViewStockEligibilityDTO> getRegionList()
	{
		return regionList;
	}

	public void setRegionList(List<ViewStockEligibilityDTO> regionList)
	{
		this.regionList = regionList;
	}

	public String getError_flag()
	{
		return error_flag;
	}

	public void setError_flag(String error_flag)
	{
		this.error_flag = error_flag;
	}

	public String getStrmsg()
	{
		return strmsg;
	}

	public void setStrmsg(String strmsg)
	{
		this.strmsg = strmsg;
	}

	public FormFile getUploadedFile()
	{
		return uploadedFile;
	}

	public void setUploadedFile(FormFile uploadedFile)
	{
		this.uploadedFile = uploadedFile;
	}

	public void setDistOlmId(String distOlmId)
	{
		this.distOlmId = distOlmId;
	}

	public List<ViewStockEligibilityDTO> getStkEligExcelDetailList()
	{
		return stkEligExcelDetailList;
	}

	public void setStkEligExcelDetailList(List<ViewStockEligibilityDTO> stkEligExcelDetailList)
	{
		this.stkEligExcelDetailList = stkEligExcelDetailList;
	}

	public String getStrOLMID()
	{
		return strOLMID;
	}

	public void setStrOLMID(String strOLMID)
	{
		this.strOLMID = strOLMID;
	}

	public String getStrDistName()
	{
		return strDistName;
	}

	public void setStrDistName(String strDistName)
	{
		this.strDistName = strDistName;
	}

	public String getStrSuccessMsg()
	{
		return strSuccessMsg;
	}

	public void setStrSuccessMsg(String strSuccessMsg)
	{
		this.strSuccessMsg = strSuccessMsg;
	}

	public String getMethodName()
	{
		return methodName;
	}

	public void setMethodName(String methodName)
	{
		this.methodName = methodName;
	}

	public String getIntUserAcctLevel()
	{
		return intUserAcctLevel;
	}

	public void setIntUserAcctLevel(String intUserAcctLevel)
	{
		this.intUserAcctLevel = intUserAcctLevel;
	}

	public String getOlmId()
	{
		return olmId;
	}

	public void setOlmId(String olmId)
	{
		this.olmId = olmId;
	}

	public List<ViewStockEligibilityDTO> getViewStockEligibilityList()
	{
		return viewStockEligibilityList;
	}

	public void setViewStockEligibilityList(List<ViewStockEligibilityDTO> viewStockEligibilityList)
	{
		this.viewStockEligibilityList = viewStockEligibilityList;
	}

	public String getBalance()
	{
		return balance;
	}

	public void setBalance(String balance)
	{
		this.balance = balance;
	}

	public String getLoan()
	{
		return loan;
	}

	public void setLoan(String loan)
	{
		this.loan = loan;
	}

	public String getSecurity()
	{
		return security;
	}

	public void setSecurity(String security)
	{
		this.security = security;
	}

}
