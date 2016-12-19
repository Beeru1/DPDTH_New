package com.ibm.dp.beans;

import java.io.Serializable;
import org.apache.struts.validator.ValidatorForm;
import java.util.ArrayList;

import com.ibm.dp.dto.DistStockAccpDisplayDTO;
import com.ibm.dp.dto.DistStockDecOptionsDTO;
 
/**
 * StockDeclarationBean class holds the Properties for Stock Declaration
 *
 * @author Arvind Gupta
 */
public class DistStockAcceptTransferBean extends ValidatorForm implements Serializable {
	
	private static final long serialVersionUID = 1L;

	
	
	private String methodName;
	private ArrayList stockTransferAccepList;
	private String strCheckedSTA;
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
	 * @return the methodName
	 */
	public String getMethodName() {
		return methodName;
	}
	/**
	 * @param methodName the methodName to set
	 */
	public void setMethodName(String methodName) {
		this.methodName = methodName;
	}
	/**
	 * @return the stockTransferAccepList
	 */
	public ArrayList getStockTransferAccepList() {
		return stockTransferAccepList;
	}
	/**
	 * @param stockTransferAccepList the stockTransferAccepList to set
	 */
	public void setStockTransferAccepList(ArrayList stockTransferAccepList) {
		this.stockTransferAccepList = stockTransferAccepList;
	}
	/**
	 * @return the strCheckedSTA
	 */
	public String getStrCheckedSTA() {
		return strCheckedSTA;
	}
	/**
	 * @param strCheckedSTA the strCheckedSTA to set
	 */
	public void setStrCheckedSTA(String strCheckedSTA) {
		this.strCheckedSTA = strCheckedSTA;
	}
	
	
	
	
	

}
