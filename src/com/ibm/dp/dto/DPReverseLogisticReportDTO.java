package com.ibm.dp.dto;

import java.util.Date;

public class DPReverseLogisticReportDTO {

	    String status;
	    String fromDate;
	    String toDate;
		String accountName;
		//String productName;
		String loginName;
	    String tsmID;
		String tsmName;
		String distID;
		//String distName;
		private String status1;
		private static final long serialVersionUID = 1L;
		private String circle = "";
		private int churnInventory;
		private int upgradeInventory;
		private int doaInventory;
		private int defectiveInventory;
		private int c2sInventory;

		private int s2wInventory;
		private int repInventory;
		
		String collection_type= "";
		String account_name= "";
		String ser_no_collect= "";
		String collection_name= "";
		String defect_name= "";
		String collection_date= "";
		String remarks= "";
		
		// OpenStockDepleteDTO
		String distributorCode;
		  String productCode;
		  String quantity1;
		  String createdDate;
		  String status2;
		  String fromDate1;
		  String toDate1;
		  String errorDesc;
		  
		  String login_id;
		  String distributor_locator_code;
		  String product_name;
		  String non_ser_qty;
		  String ser_qty;
		  String physical_stock;
		  String last_pr_number;
		  String last_pr_date; 
		  String in_hand_qty;
		  int total_dp_qty;
		  int difference;
		//added by saumya for po status report
			private String name="";
			private String productName="";
			private String prNo="";
			private String prDate="";
			private String poNo="";
			private String poDate="";
			private String poStatus="";
			private String invoiceNo="";
			private String invoiceDate="";
			private String dcNo="";
			private String dcDate="";
			private String ddNo="";
			private String ddDate="";
			private String raisedQty="";
			private String poQty="";
			private String invoiceQty="";
			private String poAccDate="";
			private String prStatus="";
			private String poReportList="";
			private String distId="";
			private String distName="";
			private String circleName="";
			private String unit="";
			
			private int prodName=0;
		  
		public String getAccountName() {
			return accountName;
		}
		public void setAccountName(String accountName) {
			this.accountName = accountName;
		}
		public int getC2sInventory() {
			return c2sInventory;
		}
		public void setC2sInventory(int inventory) {
			c2sInventory = inventory;
		}
		public int getChurnInventory() {
			return churnInventory;
		}
		public void setChurnInventory(int churnInventory) {
			this.churnInventory = churnInventory;
		}
		public String getCircle() {
			return circle;
		}
		public void setCircle(String circle) {
			this.circle = circle;
		}
		public String getCreatedDate() {
			return createdDate;
		}
		public void setCreatedDate(String createdDate) {
			this.createdDate = createdDate;
		}
		public int getDefectiveInventory() {
			return defectiveInventory;
		}
		public void setDefectiveInventory(int defectiveInventory) {
			this.defectiveInventory = defectiveInventory;
		}
		public String getDistID() {
			return distID;
		}
		public void setDistID(String distID) {
			this.distID = distID;
		}
		public String getDistName() {
			return distName;
		}
		public void setDistName(String distName) {
			this.distName = distName;
		}
		public String getDistributorCode() {
			return distributorCode;
		}
		public void setDistributorCode(String distributorCode) {
			this.distributorCode = distributorCode;
		}
		public int getDoaInventory() {
			return doaInventory;
		}
		public void setDoaInventory(int doaInventory) {
			this.doaInventory = doaInventory;
		}
		public String getErrorDesc() {
			return errorDesc;
		}
		public void setErrorDesc(String errorDesc) {
			this.errorDesc = errorDesc;
		}
		public String getFromDate() {
			return fromDate;
		}
		public void setFromDate(String fromDate) {
			this.fromDate = fromDate;
		}
		public String getFromDate1() {
			return fromDate1;
		}
		public void setFromDate1(String fromDate1) {
			this.fromDate1 = fromDate1;
		}
		public String getLoginName() {
			return loginName;
		}
		public void setLoginName(String loginName) {
			this.loginName = loginName;
		}
		public String getProductCode() {
			return productCode;
		}
		public void setProductCode(String productCode) {
			this.productCode = productCode;
		}
		public String getProductName() {
			return productName;
		}
		public void setProductName(String productName) {
			this.productName = productName;
		}
		public String getQuantity1() {
			return quantity1;
		}
		public void setQuantity1(String quantity1) {
			this.quantity1 = quantity1;
		}
		public int getRepInventory() {
			return repInventory;
		}
		public void setRepInventory(int repInventory) {
			this.repInventory = repInventory;
		}
		public int getS2wInventory() {
			return s2wInventory;
		}
		public void setS2wInventory(int inventory) {
			s2wInventory = inventory;
		}
		public String getStatus() {
			return status;
		}
		public void setStatus(String status) {
			this.status = status;
		}
		public String getStatus1() {
			return status1;
		}
		public void setStatus1(String status1) {
			this.status1 = status1;
		}
		public String getStatus2() {
			return status2;
		}
		public void setStatus2(String status2) {
			this.status2 = status2;
		}
		public String getToDate() {
			return toDate;
		}
		public void setToDate(String toDate) {
			this.toDate = toDate;
		}
		public String getToDate1() {
			return toDate1;
		}
		public void setToDate1(String toDate1) {
			this.toDate1 = toDate1;
		}
		public String getTsmID() {
			return tsmID;
		}
		public void setTsmID(String tsmID) {
			this.tsmID = tsmID;
		}
		public String getTsmName() {
			return tsmName;
		}
		public void setTsmName(String tsmName) {
			this.tsmName = tsmName;
		}
		public int getUpgradeInventory() {
			return upgradeInventory;
		}
		public void setUpgradeInventory(int upgradeInventory) {
			this.upgradeInventory = upgradeInventory;
		}
		public String getAccount_name() {
			return account_name;
		}
		public void setAccount_name(String account_name) {
			this.account_name = account_name;
		}
		public String getCollection_date() {
			return collection_date;
		}
		public void setCollection_date(String collection_date) {
			this.collection_date = collection_date;
		}
		public String getCollection_name() {
			return collection_name;
		}
		public void setCollection_name(String collection_name) {
			this.collection_name = collection_name;
		}
		public String getCollection_type() {
			return collection_type;
		}
		public void setCollection_type(String collection_type) {
			this.collection_type = collection_type;
		}
		public String getDefect_name() {
			return defect_name;
		}
		public void setDefect_name(String defect_name) {
			this.defect_name = defect_name;
		}
		public String getRemarks() {
			return remarks;
		}
		public void setRemarks(String remarks) {
			this.remarks = remarks;
		}
		public String getSer_no_collect() {
			return ser_no_collect;
		}
		public void setSer_no_collect(String ser_no_collect) {
			this.ser_no_collect = ser_no_collect;
		}
		public String getDcDate() {
			return dcDate;
		}
		public void setDcDate(String dcDate) {
			this.dcDate = dcDate;
		}
		public String getDcNo() {
			return dcNo;
		}
		public void setDcNo(String dcNo) {
			this.dcNo = dcNo;
		}
		public String getDdDate() {
			return ddDate;
		}
		public void setDdDate(String ddDate) {
			this.ddDate = ddDate;
		}
		public String getDdNo() {
			return ddNo;
		}
		public void setDdNo(String ddNo) {
			this.ddNo = ddNo;
		}
		public String getDistId() {
			return distId;
		}
		public void setDistId(String distId) {
			this.distId = distId;
		}
		public String getInvoiceDate() {
			return invoiceDate;
		}
		public void setInvoiceDate(String invoiceDate) {
			this.invoiceDate = invoiceDate;
		}
		public String getInvoiceNo() {
			return invoiceNo;
		}
		public void setInvoiceNo(String invoiceNo) {
			this.invoiceNo = invoiceNo;
		}
		public String getInvoiceQty() {
			return invoiceQty;
		}
		public void setInvoiceQty(String invoiceQty) {
			this.invoiceQty = invoiceQty;
		}
		public String getName() {
			return name;
		}
		public void setName(String name) {
			this.name = name;
		}
		public String getPoDate() {
			return poDate;
		}
		public void setPoDate(String poDate) {
			this.poDate = poDate;
		}
		public String getPoNo() {
			return poNo;
		}
		public void setPoNo(String poNo) {
			this.poNo = poNo;
		}
		public String getPoQty() {
			return poQty;
		}
		public void setPoQty(String poQty) {
			this.poQty = poQty;
		}
		public String getPoReportList() {
			return poReportList;
		}
		public void setPoReportList(String poReportList) {
			this.poReportList = poReportList;
		}
		public String getPoStatus() {
			return poStatus;
		}
		public void setPoStatus(String poStatus) {
			this.poStatus = poStatus;
		}
		
		public String getPoAccDate() {
			return poAccDate;
		}
		public void setPoAccDate(String poAccDate) {
			this.poAccDate = poAccDate;
		}
		public String getPrDate() {
			return prDate;
		}
		public void setPrDate(String prDate) {
			this.prDate = prDate;
		}
		public String getPrNo() {
			return prNo;
		}
		public void setPrNo(String prNo) {
			this.prNo = prNo;
		}
		public int getProdName() {
			return prodName;
		}
		public void setProdName(int prodName) {
			this.prodName = prodName;
		}
		public String getPrStatus() {
			return prStatus;
		}
		public void setPrStatus(String prStatus) {
			this.prStatus = prStatus;
		}
		public String getRaisedQty() {
			return raisedQty;
		}
		public void setRaisedQty(String raisedQty) {
			this.raisedQty = raisedQty;
		}
		public String getDistributor_locator_code() {
			return distributor_locator_code;
		}
		public void setDistributor_locator_code(String distributor_locator_code) {
			this.distributor_locator_code = distributor_locator_code;
		}
		public String getLast_pr_date() {
			return last_pr_date;
		}
		public void setLast_pr_date(String last_pr_date) {
			this.last_pr_date = last_pr_date;
		}
		public String getLast_pr_number() {
			return last_pr_number;
		}
		public void setLast_pr_number(String last_pr_number) {
			this.last_pr_number = last_pr_number;
		}
		public String getLogin_id() {
			return login_id;
		}
		public void setLogin_id(String login_id) {
			this.login_id = login_id;
		}
		public String getNon_ser_qty() {
			return non_ser_qty;
		}
		public void setNon_ser_qty(String non_ser_qty) {
			this.non_ser_qty = non_ser_qty;
		}
		public String getPhysical_stock() {
			return physical_stock;
		}
		public void setPhysical_stock(String physical_stock) {
			this.physical_stock = physical_stock;
		}
		public String getProduct_name() {
			return product_name;
		}
		public void setProduct_name(String product_name) {
			this.product_name = product_name;
		}
		public String getSer_qty() {
			return ser_qty;
		}
		public void setSer_qty(String ser_qty) {
			this.ser_qty = ser_qty;
		}
		public String getIn_hand_qty() {
			return in_hand_qty;
		}
		public void setIn_hand_qty(String in_hand_qty) {
			this.in_hand_qty = in_hand_qty;
		}
		public int getTotal_dp_qty() {
			return total_dp_qty;
		}
		public void setTotal_dp_qty(int total_dp_qty) {
			this.total_dp_qty = total_dp_qty;
		}
		public String getCircleName() {
			return circleName;
		}
		public void setCircleName(String circleName) {
			this.circleName = circleName;
		}
		public String getUnit() {
			return unit;
		}
		public void setUnit(String unit) {
			this.unit = unit;
		}
		public int getDifference() {
			return difference;
		}
		public void setDifference(int difference) {
			this.difference = difference;
		}
		

}
