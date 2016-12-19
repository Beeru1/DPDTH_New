package com.ibm.dp.dto;

import java.io.Serializable;

public class DpDcCreationDto implements Serializable {
	 public String collectionId = "";
	 public String collectionName = "";
	 
	
	public String getCollectionId() {
		return collectionId;
	}
	public void setCollectionId(String collectionId) {
		this.collectionId = collectionId;
	}
	public String getCollectionName() {
		return collectionName;
	}
	public void setCollectionName(String collectionName) {
		this.collectionName = collectionName;
	}

}
