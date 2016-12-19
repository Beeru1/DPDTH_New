package com.ibm.reports.dto;

public class CriteriaDTO {

	private int criteriaId;
	private String criteriaName;
	private String criteriaLabel;
	private String type;
	private boolean isMandatory;
	private boolean initvalFlag;
	private boolean enabledFlag;
	
	/**
	 * @return the criteriaId
	 */
	public int getCriteriaId() {
		return criteriaId;
	}
	/**
	 * @param criteriaId the criteriaId to set
	 */
	public void setCriteriaId(int criteriaId) {
		this.criteriaId = criteriaId;
	}
	/**
	 * @return the criteriaLabel
	 */
	public String getCriteriaLabel() {
		return criteriaLabel;
	}
	/**
	 * @param criteriaLabel the criteriaLabel to set
	 */
	public void setCriteriaLabel(String criteriaLabel) {
		this.criteriaLabel = criteriaLabel;
	}
	/**
	 * @return the criteriaName
	 */
	public String getCriteriaName() {
		return criteriaName;
	}
	/**
	 * @param criteriaName the criteriaName to set
	 */
	public void setCriteriaName(String criteriaName) {
		this.criteriaName = criteriaName;
	}
	/**
	 * @return the isMandatory
	 */
	public boolean isMandatory() {
		return isMandatory;
	}
	/**
	 * @param isMandatory the isMandatory to set
	 */
	public void setMandatory(boolean isMandatory) {
		this.isMandatory = isMandatory;
	}
	/**
	 * @return the type
	 */
	public String getType() {
		return type;
	}
	/**
	 * @param type the type to set
	 */
	public void setType(String type) {
		this.type = type;
	}
	public boolean isEnabledFlag() {
		return enabledFlag;
	}
	public void setEnabledFlag(boolean enabledFlag) {
		this.enabledFlag = enabledFlag;
	}
	public boolean isInitvalFlag() {
		return initvalFlag;
	}
	public void setInitvalFlag(boolean initvalFlag) {
		this.initvalFlag = initvalFlag;
	}
	
	
	
	
	
}
