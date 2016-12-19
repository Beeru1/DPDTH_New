package com.ibm.hbo.dto;

import java.util.*;
import java.io.Serializable;


 /** 
	* @author Avadesh 
	* Created On Thu Oct 18 16:47:14 IST 2007 
	* Data Trasnfer Object class for table ARC_CATEGORY_MSTR 
 
 **/ 
public class HBOCategoryMstr implements Serializable {


    private String categoryId;

    private String categoryName;

    private String categoryDescription;

    /** Creates a dto for the ARC_CATEGORY_MSTR table */
    public HBOCategoryMstr() {
    }


 /** 
	* Get categoryId associated with this object.
	* @return categoryId
 **/ 

    public String getCategoryId() {
        return categoryId;
    }

 /** 
	* Set categoryId associated with this object.
	* @param categoryId The categoryId value to set
 **/ 

    public void setCategoryId(String categoryId) {
        this.categoryId = categoryId;
    }

 /** 
	* Get categoryName associated with this object.
	* @return categoryName
 **/ 

    public String getCategoryName() {
        return categoryName;
    }

 /** 
	* Set categoryName associated with this object.
	* @param categoryName The categoryName value to set
 **/ 

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }

 /** 
	* Get categoryDescription associated with this object.
	* @return categoryDescription
 **/ 

    public String getCategoryDescription() {
        return categoryDescription;
    }

 /** 
	* Set categoryDescription associated with this object.
	* @param categoryDescription The categoryDescription value to set
 **/ 

    public void setCategoryDescription(String categoryDescription) {
        this.categoryDescription = categoryDescription;
    }

}
