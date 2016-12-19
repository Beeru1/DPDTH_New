package com.ibm.virtualization.recharge.dto;

public class AccountBalance {

	/* The openingBalance of the an Account */
	private double openingBalance;

	/* The accountId of an Account */
	private long accountId;

	/* The threshold for the Account */
	private double threshold;

	/* The operatingBalance for the Account */
	private double operatingBalance;

	/* The availableBalance for the Account */
	private double availableBalance;

	/* The Rate for the Account */
	private float Rate;

	/**
	 * @return the rate
	 */
	public float getRate() {
		return Rate;
	}

	/**
	 * @param rate
	 *            the rate to set
	 */
	public void setRate(float rate) {
		Rate = rate;
	}

	/**
	 * 
	 * @return the accountId
	 */
	public long getAccountId() {
		return accountId;
	}

	/**
	 * @param accountId
	 *            The accountId to set
	 */
	public void setAccountId(long accountId) {
		this.accountId = accountId;
	}

	/**
	 * @return Returns the availableBalance.
	 */
	public double getAvailableBalance() {
		return availableBalance;
	}

	/**
	 * @param availableBalance
	 *            The availableBalance to set.
	 */
	public void setAvailableBalance(double availableBalance) {
		this.availableBalance = availableBalance;
	}

	/**
	 * @return Returns the openingBalance.
	 */
	public double getOpeningBalance() {
		return openingBalance;
	}

	/**
	 * @param openingBalance
	 *            The openingBalance to set.
	 */
	public void setOpeningBalance(double openingBalance) {
		this.openingBalance = openingBalance;
	}

	/**
	 * @return Returns the operatingBalance.
	 */
	public double getOperatingBalance() {
		return operatingBalance;
	}

	/**
	 * @param operatingBalance
	 *            The operatingBalance to set.
	 */
	public void setOperatingBalance(double operatingBalance) {
		this.operatingBalance = operatingBalance;
	}

	/**
	 * @return Returns the threshold.
	 */
	public double getThreshold() {
		return threshold;
	}

	/**
	 * @param threshold
	 *            The threshold to set.
	 */
	public void setThreshold(double threshold) {
		this.threshold = threshold;
	}

	/**
	 * Constructs a <code>String</code> with all attributes in name = value
	 * format.
	 * 
	 * @return a <code>String</code> representation of this object.
	 */
	public String toString() {
		final String TAB = "    ";

		String retValue = "";

		retValue = "AccountBalance ( " + super.toString() + TAB
				+ "openingBalance = " + this.openingBalance + TAB
				+ "accountId = " + this.accountId + TAB + "threshold = "
				+ this.threshold + TAB + "operatingBalance = "
				+ this.operatingBalance + TAB + "availableBalance = "
				+ this.availableBalance + TAB + "Rate = " + this.Rate + TAB
				+ " )";

		return retValue;
	}
}