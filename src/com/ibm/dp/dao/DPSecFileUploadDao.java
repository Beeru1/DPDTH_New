package com.ibm.dp.dao;

import com.ibm.hbo.exception.HBOException;

public interface DPSecFileUploadDao {
	public int insertUploadFileInfo(int circleId,String fileName, int userId,String UploadfilePath)throws HBOException;
}
