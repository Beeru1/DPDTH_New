
package com.ibm.hbo.dao;
import java.util.ArrayList;
import com.ibm.hbo.exception.HBOException;
import com.ibm.hbo.forms.HBOFileUploadFormBean;
 
public interface HBOBulkUploadDAO {
	public int insertBulkUploadData(int circleId,String fileName, int userId,String UploadfilePath,  String fileType)throws HBOException; 
	public ArrayList getBulkUploadData(String circleId,	HBOFileUploadFormBean fileUploadFormBean,int userId,String fileType)	throws HBOException;	
}
