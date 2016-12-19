package com.ibm.dp.dto;

import java.io.Serializable;
import java.sql.Date;

public class NegetiveEligibilityDTO implements Serializable  {
	 /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	 public String distOlmID = "";
	 public String distName = "";
	 public String distId = "";
	 public String eligibleAmt;
	 public int eligibleDays;
	 
	 String firstThirty;
	 String secondThirty;
	 String thirdThirty;
	 String fourthThirty;
	 String fifthThirty;
	
	public String getFifthThirty() {
		return fifthThirty;
	}
	public void setFifthThirty(String fifthThirty) {
		this.fifthThirty = fifthThirty;
	}
	public String getFirstThirty() {
		return firstThirty;
	}
	public void setFirstThirty(String firstThirty) {
		this.firstThirty = firstThirty;
	}
	public String getFourthThirty() {
		return fourthThirty;
	}
	public void setFourthThirty(String fourthThirty) {
		this.fourthThirty = fourthThirty;
	}
	public String getSecondThirty() {
		return secondThirty;
	}
	public void setSecondThirty(String secondThirty) {
		this.secondThirty = secondThirty;
	}
	public String getThirdThirty() {
		return thirdThirty;
	}
	public void setThirdThirty(String thirdThirty) {
		this.thirdThirty = thirdThirty;
	}
	public String getDistId() {
		return distId;
	}
	public void setDistId(String distId) {
		this.distId = distId;
	}
	public String getDistName() {
		return distName;
	}
	public void setDistName(String distName) {
		this.distName = distName;
	}
	public String getDistOlmID() {
		return distOlmID;
	}
	public void setDistOlmID(String distOlmID) {
		this.distOlmID = distOlmID;
	}
	public String getEligibleAmt() {
		return eligibleAmt;
	}
	public void setEligibleAmt(String eligibleAmt) {
		this.eligibleAmt = eligibleAmt;
	}
	public int getEligibleDays() {
		return eligibleDays;
	}
	public void setEligibleDays(int eligibleDays) {
		this.eligibleDays = eligibleDays;
	}

	
	  
}
