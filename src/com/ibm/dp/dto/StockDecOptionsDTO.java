package com.ibm.dp.dto;

import java.io.Serializable;

public class StockDecOptionsDTO implements Serializable {
	
	String optionText;

	int optionValue;
	
	String optionValueAccp="";
	
	

	/**
	 * @return the optionValueAccp
	 */
	public String getOptionValueAccp() {
		return optionValueAccp;
	}

	/**
	 * @param optionValueAccp the optionValueAccp to set
	 */
	public void setOptionValueAccp(String optionValueAccp) {
		this.optionValueAccp = optionValueAccp;
	}

	/**
	 * @return the optionText
	 */
	public String getOptionText() {
		return optionText;
	}

	/**
	 * @param optionText the optionText to set
	 */
	public void setOptionText(String optionText) {
		this.optionText = optionText;
	}

	/**
	 * @return the optionValue
	 */
	public int getOptionValue() {
		return optionValue;
	}

	/**
	 * @param optionValue the optionValue to set
	 */
	public void setOptionValue(int optionValue) {
		this.optionValue = optionValue;
	}

	

}
