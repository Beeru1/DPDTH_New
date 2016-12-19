package com.ibm.dp.beans;

import java.util.ArrayList;
import java.util.List;

import org.apache.struts.action.ActionForm;

import com.ibm.dp.dto.InterSSDTransferDTO;

/**
 * Form Bean class for table VR_CIRCLE_MASTER
 * 
 * @author Paras
 *  
 */
public class InterSSDFormBean extends ActionForm {
	public InterSSDFormBean()
	{}
	

    /**
	 * 
	 */
	private static final long serialVersionUID = 9169076242533742539L;

	private List<InterSSDTransferDTO> arrList = new ArrayList<InterSSDTransferDTO>();
	private List<InterSSDTransferDTO> distList = new ArrayList<InterSSDTransferDTO>();
	
	private String methodName ="";
	private String[] trans_id =null;
	private String trans_type="";
	private String to_dist="";
	private String to_fse="";
	private String transaction_type = "";
	private String transaction_Id;
	
	private String current_dist_id;
	private String current_dist_name;
	private String str_msg;
	
	private String current_fse_id;
	private String current_fse_name;
	
	private String tsm_id;
	private String circle_id;
	private String to_fse_dd;
	
	public String getTo_fse_dd() {
		return to_fse_dd;
	}

	public void setTo_fse_dd(String to_fse_dd) {
		this.to_fse_dd = to_fse_dd;
	}

	public String getStr_msg() {
		return str_msg;
	}

	public void setStr_msg(String str_msg) {
		this.str_msg = str_msg;
	}

	public String getCurrent_dist_id() {
		return current_dist_id;
	}

	public void setCurrent_dist_id(String current_dist_id) {
		this.current_dist_id = current_dist_id;
	}

	public String getCurrent_dist_name() {
		return current_dist_name;
	}

	public void setCurrent_dist_name(String current_dist_name) {
		this.current_dist_name = current_dist_name;
	}

	public String getTransaction_Id() {
		return transaction_Id;
	}

	public void setTransaction_Id(String transaction_Id) {
		this.transaction_Id = transaction_Id;
	}

	public String getTransaction_type() {
		return transaction_type;
	}

	public void setTransaction_type(String transaction_type) {
		this.transaction_type = transaction_type;
	}

	public String getTo_fse() {
		return to_fse;
	}

	public void setTo_fse(String to_fse) {
		this.to_fse = to_fse;
	}

	public String getTrans_type() {
		return trans_type;
	}

	public void setTrans_type(String trans_type) {
		this.trans_type = trans_type;
	}

	public List<InterSSDTransferDTO> getArrList() {
		return arrList;
	}

	public void setArrList(List<InterSSDTransferDTO> arrList) {
		this.arrList = arrList;
	}

	public String getMethodName() {
		return methodName;
	}

	public void setMethodName(String methodName) {
		this.methodName = methodName;
	}

	public String[] getTrans_id() {
		return trans_id;
	}

	public void setTrans_id(String[] trans_id) {
		this.trans_id = trans_id;
	}

	public List<InterSSDTransferDTO> getDistList() {
		return distList;
	}

	public void setDistList(List<InterSSDTransferDTO> distList) {
		this.distList = distList;
	}

	public String getTo_dist() {
		return to_dist;
	}

	public void setTo_dist(String to_dist) {
		this.to_dist = to_dist;
	}

	public String getCurrent_fse_id() {
		return current_fse_id;
	}

	public void setCurrent_fse_id(String current_fse_id) {
		this.current_fse_id = current_fse_id;
	}

	public String getCurrent_fse_name() {
		return current_fse_name;
	}

	public void setCurrent_fse_name(String current_fse_name) {
		this.current_fse_name = current_fse_name;
	}

	public String getTsm_id() {
		return tsm_id;
	}

	public void setTsm_id(String tsm_id) {
		this.tsm_id = tsm_id;
	}

	public String getCircle_id() {
		return circle_id;
	}

	public void setCircle_id(String circle_id) {
		this.circle_id = circle_id;
	}


	
	
}