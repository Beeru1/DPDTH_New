package com.ibm.dp.beans;

import java.util.ArrayList;
import java.util.List;

import org.apache.struts.action.ActionForm;

import com.ibm.dp.dto.ConsumptionPostingDetailReportDto;
import com.ibm.dp.dto.DistributorDTO;

public class DPSCMConsumptionReportBean  extends ActionForm
{
	private String DistributorId;
	private String fromDate = "";
	private String toDate = "";
	private List<DistributorDTO> DistributorList= new ArrayList<DistributorDTO>();
	
	private String customerId;
	private List<ConsumptionPostingDetailReportDto> reportDataList;
		
}
