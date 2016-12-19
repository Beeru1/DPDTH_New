package com.ibm.dp.beans;

import org.apache.struts.action.ActionForm;

/**
 * StockDeclarationFormBean class holds the Properties for Stock Declaration
 * @author Mohammad Aslam
 */
public class StockDeclarationFormBean extends ActionForm {

	private static final long serialVersionUID = 8282042652134835812L;

	private String distributor = null;
	private String exportToExcel = null;

	public String getExportToExcel() {
		return exportToExcel;
	}

	public void setExportToExcel(String exportToExcel) {
		this.exportToExcel = exportToExcel;
	}

	public String getDistributor() {
		return distributor;
	}

	public void setDistributor(String distributor) {
		this.distributor = distributor;
	}
}