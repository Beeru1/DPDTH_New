package com.ibm.dp.beans;

import java.io.Serializable;
import org.apache.struts.validator.ValidatorForm;
import java.util.ArrayList;
import com.ibm.dp.dto.StockDecOptionsDTO;
 
/**
 * StockDeclarationBean class holds the Properties for Stock Declaration
 *
 * @author Arvind Gupta
 */
public class StockAcceptTransferBean extends ValidatorForm implements Serializable {
	
	private static final long serialVersionUID = 1L;

	private String transFrom="";
	private String transDCNo="";
	private String transDCNoText="";
	private String transFromText="";
	private String transTo="";
	private String quantity="";
	private String message="";
	private String DCDate="";
	private boolean check=false;
	ArrayList<StockDecOptionsDTO> distList;
	ArrayList<StockDecOptionsDTO> dcList;
	private int account_id=0;
	/**
	 * @return the account_id
	 */
	public int getAccount_id() {
		return account_id;
	}
	/**
	 * @param account_id the account_id to set
	 */
	public void setAccount_id(int account_id) {
		this.account_id = account_id;
	}
	/**
	 * @return the dCDate
	 */
	public String getDCDate() {
		return DCDate;
	}
	/**
	 * @param date the dCDate to set
	 */
	public void setDCDate(String date) {
		DCDate = date;
	}
	/**
	 * @return the dcList
	 */
	public ArrayList<StockDecOptionsDTO> getDcList() {
		return dcList;
	}
	/**
	 * @param dcList the dcList to set
	 */
	public void setDcList(ArrayList<StockDecOptionsDTO> dcList) {
		this.dcList = dcList;
	}
	/**
	 * @return the distList
	 */
	public ArrayList<StockDecOptionsDTO> getDistList() {
		return distList;
	}
	/**
	 * @param distList the distList to set
	 */
	public void setDistList(ArrayList<StockDecOptionsDTO> distList) {
		this.distList = distList;
	}
	/**
	 * @return the message
	 */
	public String getMessage() {
		return message;
	}
	/**
	 * @param message the message to set
	 */
	public void setMessage(String message) {
		this.message = message;
	}
	/**
	 * @return the quantity
	 */
	public String getQuantity() {
		return quantity;
	}
	/**
	 * @param quantity the quantity to set
	 */
	public void setQuantity(String quantity) {
		this.quantity = quantity;
	}
	/**
	 * @return the transDCNo
	 */
	public String getTransDCNo() {
		return transDCNo;
	}
	/**
	 * @param transDCNo the transDCNo to set
	 */
	public void setTransDCNo(String transDCNo) {
		this.transDCNo = transDCNo;
	}
	/**
	 * @return the transDCNoText
	 */
	public String getTransDCNoText() {
		return transDCNoText;
	}
	/**
	 * @param transDCNoText the transDCNoText to set
	 */
	public void setTransDCNoText(String transDCNoText) {
		this.transDCNoText = transDCNoText;
	}
	/**
	 * @return the transFrom
	 */
	public String getTransFrom() {
		return transFrom;
	}
	/**
	 * @param transFrom the transFrom to set
	 */
	public void setTransFrom(String transFrom) {
		this.transFrom = transFrom;
	}
	/**
	 * @return the transFromText
	 */
	public String getTransFromText() {
		return transFromText;
	}
	/**
	 * @param transFromText the transFromText to set
	 */
	public void setTransFromText(String transFromText) {
		this.transFromText = transFromText;
	}
	/**
	 * @return the transTo
	 */
	public String getTransTo() {
		return transTo;
	}
	/**
	 * @param transTo the transTo to set
	 */
	public void setTransTo(String transTo) {
		this.transTo = transTo;
	}
	/**
	 * @return the check
	 */
	public boolean isCheck() {
		return check;
	}
	/**
	 * @param check the check to set
	 */
	public void setCheck(boolean check) {
		this.check = check;
	}
	
	
	
	

}
