package com.ibm.dp.beans;

public class StockDeclarationDetailsBean {
	
	private Integer productId;
	
	private Integer closingStock;
	
	private String comments;
	
	private int distId;
	
	private int circleId;

	/**
	 * @return the circleId
	 */
	public int getCircleId() {
		return circleId;
	}

	/**
	 * @param circleId the circleId to set
	 */
	public void setCircleId(int circleId) {
		this.circleId = circleId;
	}

	/**
	 * @return the closingStock
	 */
	public Integer getClosingStock() {
		return closingStock;
	}

	/**
	 * @param closingStock the closingStock to set
	 */
	public void setClosingStock(Integer closingStock) {
		this.closingStock = closingStock;
	}

	/**
	 * @return the comments
	 */
	public String getComments() {
		return comments;
	}

	/**
	 * @param comments the comments to set
	 */
	public void setComments(String comments) {
		this.comments = comments;
	}

	
	public Integer getProductId() {
		return productId;
	}

	/**
	 * @param productId the productId to set
	 */
	public void setProductId(Integer productId) {
		this.productId = productId;
	}

	/**
	 * @return the distId
	 */
	public int getDistId() {
		return distId;
	}

	/**
	 * @param distId the distId to set
	 */
	public void setDistId(int distId) {
		this.distId = distId;
	}

	/**
	 * @param distId the distId to set
	 */
	
	
}
