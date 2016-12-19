package com.ibm.hbo.dto;

import java.io.Serializable;

/** 
	* @author Avadesh 
	* Created On Tue Sep 25 17:26:13 IST 2007 
	* Data Trasnfer Object class for table ARC_USER_MSTR 

**/
public class HBOMonthDTO implements Serializable {

 private int monthId =0; 
 private String monthName ="";
 private int currYear=0;
 private int nextYear=0;
 

/**
 * @return
 */
public int getMonthId() {
	return monthId;
}

/**
 * @return
 */
public String getMonthName() {
	return monthName;
}

/**
 * @param i
 */
public void setMonthId(int i) {
	monthId = i;
}

/**
 * @param string
 */
public void setMonthName(String string) {
	monthName = string;
}

/**
 * @return
 */
public int getCurrYear() {
	return currYear;
}

/**
 * @return
 */
public int getNextYear() {
	return nextYear;
}

/**
 * @param i
 */
public void setCurrYear(int i) {
	currYear = i;
}

/**
 * @param i
 */
public void setNextYear(int i) {
	nextYear = i;
}

}
