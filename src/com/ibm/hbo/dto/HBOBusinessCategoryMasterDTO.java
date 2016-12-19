/*
 * Created on Nov 13, 2007
 *
 * To change the template for this generated file go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
package com.ibm.hbo.dto;

/**
 * @author ranjan
 *
 * To change the template for this generated type comment go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
public class HBOBusinessCategoryMasterDTO {
public int bcode=0;
public String bname="";
public String newBcode = "";
public String bcodeFlag="";

public String getNewBcode() {
	return newBcode;
}

public void setNewBcode(String newBcode) {
	this.newBcode = newBcode;
}

public String getBcodeFlag() {
	return bcodeFlag;
}

public void setBcodeFlag(String bcodeFlag) {
	this.bcodeFlag = bcodeFlag;
}

/**
 * @return
 */
public int getBcode() {
	return bcode;
}

/**
 * @return
 */
public String getBname() {
	return bname;
}

/**
 * @param i
 */
public void setBcode(int i) {
	bcode = i;
}

/**
 * @param string
 */
public void setBname(String string) {
	bname = string;
}

}
