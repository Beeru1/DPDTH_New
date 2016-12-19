package com.ibm.dp.dto;

/*
 * This DTO holds data for error STBs to display for Flush out from DP system.
 */

public class DistAvStockUploadDTO 
{
	private String strCircleName;
	private String strDistOLMId;
	private String strDistName;
	private String strTSMName;
	private String strPONumber;
	private String strDCNo;
	private String strDCDate;
	private String strSerialNo;
	private String strProductName;
	private String strSTBStatus;
	private String strErrorMsg;
	private String err_msg="";
	private String StrCurrentDate;
	private String strInventoryChangeDt;
	private String strReceivedOn;
	private String strStockReceivedDate;
	private String strPOStatus;
	private String strDistributorId;
	private int confStatus;
	private String strTeableName;
	private String strHasToFlush;
	
	public int compareTo(DistAvStockUploadDTO stbDto) {
		int status = 1;
		
			
		
		if(this.strDistOLMId!=null && stbDto.getStrDistOLMId()!=null)
		{
			if(this.strDistOLMId.trim().equals(stbDto.getStrDistOLMId().trim()))
			{
				status= 0;
			}
			else
			{
				status=1; return status;
			}
		}
		else if (this.strDistOLMId==null && stbDto.getStrDistOLMId()==null)
		{
			status= 0;
		}
		else if (this.strDistOLMId==null && stbDto.getStrDistOLMId()!=null)
		{
			if(stbDto.getStrDistOLMId().equals(""))
			{
				status=0;
			}
			else
			{
			status=1; return status;
			}
		}
		else if (stbDto.getStrDistOLMId()==null && this.strDistOLMId!=null )
		{
			if(this.strDistOLMId.equals(""))
			{
				status=0;
			}
			else
			{
			status=1; return status;
			}
		}
				
		if(this.strDistName!=null && stbDto.getStrDistName()!=null)
		{
			if(this.strDistName.trim().equals(stbDto.getStrDistName().trim()))
			{
				status= 0;
			}
			else
			{
				status=1; return status;
			}
		}
		else if (this.strDistName==null && stbDto.getStrDistName()==null)
		{
			status= 0;
		}
		else if (this.strDistName==null && stbDto.getStrDistName()!=null)
		{
			if(stbDto.getStrDistName().equals(""))
			{
				status=0;
			}
			else
			{
			status=1; return status;
			}
		}
		else if (stbDto.getStrDistName()==null && this.strDistName!=null)
		{
			if(this.strDistName.equals(""))
			{
				status=0;
			}
			else
			{
			status=1; return status;
			}
		}
		
		if(this.strSerialNo!=null && stbDto.getStrSerialNo()!=null)
		{
			if(this.strSerialNo.trim().equals(stbDto.getStrSerialNo().trim()))
			{
				status= 0;
			}
			else
			{
				status=1; return status;
			}
		}
		else if (this.strSerialNo==null && stbDto.getStrSerialNo()==null)
		{
			status= 0;
		}
		else if (this.strSerialNo==null && stbDto.getStrSerialNo()!=null)
		{
			if(stbDto.getStrSerialNo().equals(""))
			{
				status=0;
			}
			else
			{
			status=1; return status;
			}
		}
		else if (stbDto.getStrSerialNo()==null && this.strSerialNo!=null)
		{
			if(this.strSerialNo.equals(""))
			{
				status=0;
			}
			else
			{
			status=1; return status;
			}
		}
		
		
		if(this.strProductName!=null && stbDto.getStrProductName()!=null)
		{
			if(this.strProductName.trim().equals(stbDto.getStrProductName().trim()))
			{
				status= 0;
			}
			else
			{
				status=1; return status;
			}
		}
		else if (this.strProductName==null && stbDto.getStrProductName()==null)
		{
			status= 0;
		}
		else if (this.strProductName==null && stbDto.getStrProductName()!=null)
		{
			if(stbDto.getStrProductName().equals(""))
			{
				status=0;
			}
			else
			{
			status=1; return status;
			}
		}
		else if (stbDto.getStrProductName()==null && this.strProductName!=null)
		{
			if(this.strProductName.equals(""))
			{
				status=0;
			}
			else
			{
			status=1; return status;
			}
		}
		
		
		
		
		if(this.strSTBStatus!=null && stbDto.getStrSTBStatus()!=null)
		{
			if(this.strSTBStatus.trim().equals(stbDto.getStrSTBStatus().trim()))
			{
				status= 0;
			}
			else
			{
				status=1; return status;
			}
		}
		else if (this.strSTBStatus==null && stbDto.getStrSTBStatus()==null)
		{
			status= 0;
		}
		else if (this.strSTBStatus==null && stbDto.getStrSTBStatus()!=null)
		{
			if(stbDto.getStrSTBStatus().equals(""))
			{
				status=0;
			}
			else
			{
			status=1; return status;
			}
		}
		else if (stbDto.getStrSTBStatus()==null && this.strSTBStatus!=null)
		{
			if(this.strSTBStatus.equals(""))
			{
				status=0;
			}
			else
			{
			status=1; return status;
			}
		}
		
		
		if(this.strPONumber!=null && stbDto.getStrPONumber()!=null)
		{
			if(this.strPONumber.trim().equals(stbDto.getStrPONumber().trim()))
			{
				status= 0;
			}
			else
			{
				status=1; return status;
			}
		}
		else if (this.strPONumber==null && stbDto.getStrPONumber()==null)
		{
			status= 0;
		}
		else if (this.strPONumber==null && stbDto.getStrPONumber()!=null)
		{
			if(stbDto.getStrPONumber().equals(""))
			{
				status=0;
			}
			else
			{
			status=1; return status;
			}
		}
		else if (stbDto.getStrPONumber()==null && this.strPONumber!=null)
		{
			if(this.strPONumber.equals(""))
			{
				status=0;
			}
			else
			{
			status=1; return status;
			}
		}
		
		
		if(this.strPOStatus!=null && stbDto.getStrPOStatus()!=null)
		{
			if(this.strPOStatus.trim().equals(stbDto.getStrPOStatus().trim()))
			{
				status= 0;
			}
			else
			{
				status=1; return status;
			}
		}
		else if (this.strPOStatus==null && stbDto.getStrPOStatus()==null)
		{
			status= 0;
		}
		else if (this.strPOStatus==null && stbDto.getStrPOStatus()!=null)
		{
			if(stbDto.getStrPOStatus().equals(""))
			{
				status=0;
			}
			else
			{
			status=1; return status;
			}
		}
		else if (stbDto.getStrPOStatus()==null && this.strPOStatus!=null)
		{
			if(this.strPOStatus.equals(""))
			{
				status=0;
			}
			else
			{
			status=1; return status;
			}
		}
		
		
		
		if(this.strDCNo!=null && stbDto.getStrDCNo()!=null)
		{
			if(this.strDCNo.trim().equals(stbDto.getStrDCNo().trim()))
			{
				status= 0;
			}
			else
			{
				status=1; return status;
			}
		}
		else if (this.strDCNo==null && stbDto.getStrDCNo()==null)
		{
			status= 0;
		}
		else if (this.strDCNo==null && stbDto.getStrDCNo()!=null)
		{
			if(stbDto.getStrDCNo().equals(""))
			{
				status=0;
			}
			else
			{
			status=1; return status;
			}
		}
		else if (stbDto.getStrDCNo()==null && this.strDCNo!=null)
		{
			if(this.strDCNo.equals(""))
			{
				status=0;
			}
			else
			{
			status=1; return status;
			}
		}
		
		
		
		if(this.strDCDate!=null && stbDto.getStrDCDate()!=null)
		{
			if(this.strDCDate.trim().equals(stbDto.getStrDCDate().trim()))
			{
				status= 0;
			}
			else
			{
				status=1; return status;
			}
		}
		else if (this.strDCDate==null && stbDto.getStrDCDate()==null)
		{
			status= 0;
		}
		else if (this.strDCDate==null && stbDto.getStrDCDate()!=null)
		{
			if(stbDto.getStrDCDate().equals(""))
			{
				status=0;
			}
			else
			{
			status=1; return status;
			}
		}
		else if (stbDto.getStrDCDate()==null && this.strDCDate!=null)
		{
			if(this.strDCDate.equals(""))
			{
				status=0;
			}
			else
			{
			status=1; return status;
			}
		}
		
		
		
		if(this.strInventoryChangeDt!=null && stbDto.getStrInventoryChangeDt()!=null)
		{
			if(this.strInventoryChangeDt.trim().equals(stbDto.getStrInventoryChangeDt().trim()))
			{
				status= 0;
			}
			else
			{
				status=1; return status;
			}
		}
		else if (this.strInventoryChangeDt==null && stbDto.getStrInventoryChangeDt()==null)
		{
			status= 0;
		}
		else if (this.strInventoryChangeDt==null && stbDto.getStrInventoryChangeDt()!=null)
		{
			if(stbDto.getStrInventoryChangeDt().equals(""))
			{
				status=0;
			}
			else
			{
			status=1; return status;
			}
		}
		else if (stbDto.getStrInventoryChangeDt()==null && this.strInventoryChangeDt!=null)
		{
			if(this.strInventoryChangeDt.equals(""))
			{
				status=0;
			}
			else
			{
			status=1; return status;
			}
		}
		
		
		
		if(this.strReceivedOn!=null && stbDto.getStrReceivedOn()!=null)
		{
			if(this.strReceivedOn.trim().equals(stbDto.getStrReceivedOn().trim()))
			{
				status= 0;
			}
			else
			{
				status=1; return status;
			}
		}
		else if (this.strReceivedOn==null && stbDto.getStrReceivedOn()==null)
		{
			status= 0;
		}
		else if (this.strReceivedOn==null && stbDto.getStrReceivedOn()!=null)
		{
			if(stbDto.getStrReceivedOn().equals(""))
			{
				status=0;
			}
			else
			{
			status=1; return status;
			}
		}
		else if (stbDto.getStrReceivedOn()==null && this.strReceivedOn!=null)
		{
			if(this.strReceivedOn.equals(""))
			{
				status=0;
			}
			else
			{
			status=1; return status;
			}
		}
		
		
		if(this.strStockReceivedDate!=null && stbDto.getStrStockReceivedDate()!=null)
		{
			if(this.strStockReceivedDate.trim().equals(stbDto.getStrStockReceivedDate().trim()))
			{
				status= 0;
			}
			else
			{
				status=1; return status;
			}
		}
		else if (this.strStockReceivedDate==null && stbDto.getStrStockReceivedDate()==null)
		{
			status= 0;
		}
		else if (this.strStockReceivedDate==null && stbDto.getStrStockReceivedDate()!=null)
		{
			if(stbDto.getStrStockReceivedDate().equals(""))
			{
				status=0;
			}
			else
			{
			status=1; return status;
			}
		}
		else if (stbDto.getStrStockReceivedDate()==null && this.strStockReceivedDate!=null)
		{
			if(this.strStockReceivedDate.equals(""))
			{
				status=0;
			}
			else
			{
			status=1; return status;
			}
		}
		
		
		return status;
			
	}
	
	public String getStrCircleName() {
		return strCircleName;
	}
	public void setStrCircleName(String strCircleName) {
		this.strCircleName = strCircleName;
	}
	public String getStrDCDate() {
		return strDCDate;
	}
	public void setStrDCDate(String strDCDate) {
		this.strDCDate = strDCDate;
	}
	public String getStrDCNo() {
		return strDCNo;
	}
	public void setStrDCNo(String strDCNo) {
		this.strDCNo = strDCNo;
	}
	public String getStrDistName() {
		return strDistName;
	}
	public void setStrDistName(String strDistName) {
		this.strDistName = strDistName;
	}
	public String getStrDistOLMId() {
		return strDistOLMId;
	}
	public void setStrDistOLMId(String strDistOLMId) {
		this.strDistOLMId = strDistOLMId;
	}
	
	public String getStrPONumber() {
		return strPONumber;
	}
	public void setStrPONumber(String strPONumber) {
		this.strPONumber = strPONumber;
	}
	public String getStrTSMName() {
		return strTSMName;
	}
	public void setStrTSMName(String strTSMName) {
		this.strTSMName = strTSMName;
	}
	public String getStrSerialNo() {
		return strSerialNo;
	}
	public void setStrSerialNo(String strSerialNo) {
		this.strSerialNo = strSerialNo;
	}
	public String getStrErrorMsg() {
		return strErrorMsg;
	}
	public void setStrErrorMsg(String strErrorMsg) {
		this.strErrorMsg = strErrorMsg;
	}
	public String getStrProductName() {
		return strProductName;
	}
	public void setStrProductName(String strProductName) {
		this.strProductName = strProductName;
	}
	public String getStrSTBStatus() {
		return strSTBStatus;
	}
	public void setStrSTBStatus(String strSTBStatus) {
		this.strSTBStatus = strSTBStatus;
	}
	public String getStrDistributorId() {
		return strDistributorId;
	}
	public void setStrDistributorId(String strDistributorId) {
		this.strDistributorId = strDistributorId;
	}
	public String getStrInventoryChangeDt() {
		return strInventoryChangeDt;
	}
	public void setStrInventoryChangeDt(String strInventoryChangeDt) {
		this.strInventoryChangeDt = strInventoryChangeDt;
	}
	public String getStrPOStatus() {
		return strPOStatus;
	}
	public void setStrPOStatus(String strPOStatus) {
		this.strPOStatus = strPOStatus;
	}
	public String getStrReceivedOn() {
		return strReceivedOn;
	}
	public void setStrReceivedOn(String strReceivedOn) {
		this.strReceivedOn = strReceivedOn;
	}
	public String getStrStockReceivedDate() {
		return strStockReceivedDate;
	}
	public void setStrStockReceivedDate(String strStockReceivedDate) {
		this.strStockReceivedDate = strStockReceivedDate;
	}


	public String getStrHasToFlush() {
		return strHasToFlush;
	}

	public void setStrHasToFlush(String strHasToFlush) {
		this.strHasToFlush = strHasToFlush;
	}
	public String getErr_msg() {
		return err_msg;
	}
	public void setErr_msg(String err_msg) {
		this.err_msg = err_msg;
	}

	public String getStrTeableName() {
		return strTeableName;
	}

	public void setStrTeableName(String strTeableName) {
		this.strTeableName = strTeableName;
	}

	public String getStrCurrentDate() {
		return StrCurrentDate;
	}

	public void setStrCurrentDate(String strCurrentDate) {
		StrCurrentDate = strCurrentDate;
	}

	public int getConfStatus() {
		return confStatus;
	}

	public void setConfStatus(int confStatus) {
		this.confStatus = confStatus;
	}

	

}
