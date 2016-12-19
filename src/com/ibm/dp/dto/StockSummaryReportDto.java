package com.ibm.dp.dto;

public class StockSummaryReportDto {
	  	private static final long serialVersionUID = 1L;
		private String distName;
		private String distID;
		private String parentAccount;
		private String productName;
		private int nonSerailizedAsonDate;
		private int serialized;
		private int totalReceived;
		private int totalSerializedActivation;
		private int damagedAvailableDist;
		private int damagedS2W;
		private int totalInhandStock;
		public static long getSerialVersionUID() {
			return serialVersionUID;
		}
		
		public int getDamagedAvailableDist() {
			return damagedAvailableDist;
		}
		public void setDamagedAvailableDist(int damagedAvailableDist) {
			this.damagedAvailableDist = damagedAvailableDist;
		}
		public int getDamagedS2W() {
			return damagedS2W;
		}
		public void setDamagedS2W(int damagedS2W) {
			this.damagedS2W = damagedS2W;
		}
		
		public int getNonSerailizedAsonDate() {
			return nonSerailizedAsonDate;
		}
		public void setNonSerailizedAsonDate(int nonSerailizedAsonDate) {
			this.nonSerailizedAsonDate = nonSerailizedAsonDate;
		}
		public String getProductName() {
			return productName;
		}
		public void setProductName(String productName) {
			this.productName = productName;
		}
		public int getSerialized() {
			return serialized;
		}
		public void setSerialized(int serialized) {
			this.serialized = serialized;
		}
		
		public int getTotalSerializedActivation() {
			return totalSerializedActivation;
		}
		public void setTotalSerializedActivation(int totalSerializedActivation) {
			this.totalSerializedActivation = totalSerializedActivation;
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

		public String getParentAccount() {
			return parentAccount;
		}

		public void setParentAccount(String parentAccount) {
			this.parentAccount = parentAccount;
		}

		public int getTotalInhandStock() {
			return totalInhandStock;
		}

		public void setTotalInhandStock(int totalInhandStock) {
			this.totalInhandStock = totalInhandStock;
		}

		public int getTotalReceived() {
			return totalReceived;
		}

		public void setTotalReceived(int totalReceived) {
			this.totalReceived = totalReceived;
		}
		
	

}
