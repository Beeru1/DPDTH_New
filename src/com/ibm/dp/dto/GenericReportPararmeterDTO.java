package com.ibm.dp.dto;

import java.io.Serializable;

public class GenericReportPararmeterDTO implements Serializable
{

	private static final long serialVersionUID = 1L;
	private int reportId;
	private int groupId;
	private String tsmIds;
	private String distIds;
	private String circleIds;
	private String productType;
	private String fromDate;
	private String todate;
	private String date;
	private String collectionIds;
	private String statuses;
	private String collectionType;
	private String stbType;
	private String status;
	private String stbStatus;
	private String accountType;
	private String ageingType;
	private String accountLevel;
	private String accountName;
	private String searchOption;
	private String dateOption;
	private String transferType;
	private String pendingAt;
	private String poStatus;
	private String dcStatus;
	private String dcNo;
	private String searchCriteria;
	private String recoStatus;
	private String distData;
	private String otherUserData;
	private long accountId;
	private String stockType;
	private String recoPeriod;
	private String businessCategory;
	private String activity;// added by aman

	// added by aman for Debit Note report on 5/3/14
	public String debitDistId = "";
	public String debitDistName = "";
	public String debitCircleId = "";
	public String debitCircleName = "";
	public String debitProductId = "";
	public String debitProductName = "";
	public String debitSerial = "";

	// for report 51 (stock eligibility)
	// Added by Nehil
	private String zoneIds;
	private String ptId;
	private String dtId;

	public void setPtId(String ptId)
	{
		this.ptId = ptId;
	}

	public String getPtId()
	{
		return ptId;
	}

	public void setDtId(String dtId)
	{
		this.dtId = dtId;
	}

	public String getDtId()
	{
		return dtId;
	}

	public String getZoneIds()
	{
		return zoneIds;
	}

	public void setZoneIds(String zoneIds)
	{
		this.zoneIds = zoneIds;
	}

	public String getDebitDistId()
	{
		return debitDistId;
	}

	public void setDebitDistId(String debitDistId)
	{
		this.debitDistId = debitDistId;
	}

	public String getDebitDistName()
	{
		return debitDistName;
	}

	public void setDebitDistName(String debitDistName)
	{
		this.debitDistName = debitDistName;
	}

	public String getDebitCircleId()
	{
		return debitCircleId;
	}

	public void setDebitCircleId(String debitCircleId)
	{
		this.debitCircleId = debitCircleId;
	}

	public String getDebitCircleName()
	{
		return debitCircleName;
	}

	public void setDebitCircleName(String debitCircleName)
	{
		this.debitCircleName = debitCircleName;
	}

	public String getDebitProductId()
	{
		return debitProductId;
	}

	public void setDebitProductId(String debitProductId)
	{
		this.debitProductId = debitProductId;
	}

	public String getDebitProductName()
	{
		return debitProductName;
	}

	public void setDebitProductName(String debitProductName)
	{
		this.debitProductName = debitProductName;
	}

	public String getDebitSerial()
	{
		return debitSerial;
	}

	public void setDebitSerial(String debitSerial)
	{
		this.debitSerial = debitSerial;
	}

	public String getActivity()
	{
		return activity;
	}

	public void setActivity(String activity)
	{
		this.activity = activity;
	}

	public String getRecoPeriod()
	{
		return recoPeriod;
	}

	public void setRecoPeriod(String recoPeriod)
	{
		this.recoPeriod = recoPeriod;
	}

	public String getRecoStatus()
	{
		return recoStatus;
	}

	public void setRecoStatus(String recoStatus)
	{
		this.recoStatus = recoStatus;
	}

	public String getCollectionType()
	{
		return collectionType;
	}

	public void setCollectionType(String collectionType)
	{
		this.collectionType = collectionType;
	}

	public String getStatus()
	{
		return status;
	}

	public void setStatus(String status)
	{
		this.status = status;
	}

	public String getStbType()
	{
		return stbType;
	}

	public void setStbType(String stbType)
	{
		this.stbType = stbType;
	}

	public String getCircleIds()
	{
		return circleIds;
	}

	public void setCircleIds(String circleIds)
	{
		this.circleIds = circleIds;
	}

	public String getDistIds()
	{
		return distIds;
	}

	public void setDistIds(String distIds)
	{
		this.distIds = distIds;
	}

	public String getFromDate()
	{
		return fromDate;
	}

	public void setFromDate(String fromDate)
	{
		this.fromDate = fromDate;
	}

	public String getProductType()
	{
		return productType;
	}

	public void setProductType(String productType)
	{
		this.productType = productType;
	}

	public String getTodate()
	{
		return todate;
	}

	public void setTodate(String todate)
	{
		this.todate = todate;
	}

	public String getTsmIds()
	{
		return tsmIds;
	}

	public void setTsmIds(String tsmIds)
	{
		this.tsmIds = tsmIds;
	}

	public String getCollectionIds()
	{
		return collectionIds;
	}

	public void setCollectionIds(String collectionIds)
	{
		this.collectionIds = collectionIds;
	}

	public String getStatuses()
	{
		return statuses;
	}

	public void setStatuses(String statuses)
	{
		this.statuses = statuses;
	}

	public int getReportId()
	{
		return reportId;
	}

	public void setReportId(int reportId)
	{
		this.reportId = reportId;
	}

	public String getAccountName()
	{
		return accountName;
	}

	public void setAccountName(String accountName)
	{
		this.accountName = accountName;
	}

	public String getAccountType()
	{
		return accountType;
	}

	public void setAccountType(String accountType)
	{
		this.accountType = accountType;
	}

	public String getDateOption()
	{
		return dateOption;
	}

	public void setDateOption(String dateOption)
	{
		this.dateOption = dateOption;
	}

	public String getDcNo()
	{
		return dcNo;
	}

	public void setDcNo(String dcNo)
	{
		this.dcNo = dcNo;
	}

	public String getDcStatus()
	{
		return dcStatus;
	}

	public void setDcStatus(String dcStatus)
	{
		this.dcStatus = dcStatus;
	}

	public String getPendingAt()
	{
		return pendingAt;
	}

	public void setPendingAt(String pendingAt)
	{
		this.pendingAt = pendingAt;
	}

	public String getPoStatus()
	{
		return poStatus;
	}

	public void setPoStatus(String poStatus)
	{
		this.poStatus = poStatus;
	}

	public String getSearchCriteria()
	{
		return searchCriteria;
	}

	public void setSearchCriteria(String searchCriteria)
	{
		this.searchCriteria = searchCriteria;
	}

	public String getSearchOption()
	{
		return searchOption;
	}

	public void setSearchOption(String searchOption)
	{
		this.searchOption = searchOption;
	}

	public String getTransferType()
	{
		return transferType;
	}

	public void setTransferType(String transferType)
	{
		this.transferType = transferType;
	}

	public String getStbStatus()
	{
		return stbStatus;
	}

	public void setStbStatus(String stbStatus)
	{
		this.stbStatus = stbStatus;
	}

	public int getGroupId()
	{
		return groupId;
	}

	public void setGroupId(int groupId)
	{
		this.groupId = groupId;
	}

	public String getDistData()
	{
		return distData;
	}

	public void setDistData(String distData)
	{
		this.distData = distData;
	}

	public String getAccountLevel()
	{
		return accountLevel;
	}

	public void setAccountLevel(String accountLevel)
	{
		this.accountLevel = accountLevel;
	}

	public String getOtherUserData()
	{
		return otherUserData;
	}

	public void setOtherUserData(String otherUserData)
	{
		this.otherUserData = otherUserData;
	}

	public String getDate()
	{
		return date;
	}

	public void setDate(String date)
	{
		this.date = date;
	}

	/**
	 * @return the accountId
	 */
	public long getAccountId()
	{
		return accountId;
	}

	/**
	 * @param accountId
	 *            the accountId to set
	 */
	public void setAccountId(long accountId)
	{
		this.accountId = accountId;
	}

	public String getStockType()
	{
		return stockType;
	}

	public void setStockType(String stockType)
	{
		this.stockType = stockType;
	}

	public String getBusinessCategory()
	{
		return businessCategory;
	}

	public void setBusinessCategory(String businessCategory)
	{
		this.businessCategory = businessCategory;
	}

	public void setAgeingType(String ageingType) {
		this.ageingType = ageingType;
	}

	public String getAgeingType() {
		return ageingType;
	}

}
