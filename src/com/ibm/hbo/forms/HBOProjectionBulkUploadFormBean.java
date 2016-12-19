/*
 * Created on Aug 7, 2008
 *
 * To change the template for this generated file go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
package com.ibm.hbo.forms;
import org.apache.struts.upload.FormFile;
import java.util.ArrayList;
import org.apache.struts.action.ActionForm;

/**
 * @author vivek kumar
 *
 * To change the template for this generated type comment go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
public class HBOProjectionBulkUploadFormBean extends ActionForm {
	
		private FormFile thefile;
		private String fileName = null;
		private String filePath = null;
		//String file_ID = null;
		ArrayList arrCircle=null;	
		ArrayList arrYear=null;
		ArrayList arrMonth=null;
		String month="";
		String year="";		
		String circle="";		
		String business_catg=""; 
		String message ="";
		String uploadedBy;
		String uploadedDt;
		String circleId;
	

	/**
	 * @return
	 */
	public ArrayList getArrCircle() {
		return arrCircle;
	}

		/**
		 * @return
		 */
		public ArrayList getArrMonth() {
			return arrMonth;
		}

	/**
	 * @return
	 */
	public ArrayList getArrYear() {
		return arrYear;
	}

		/**
		 * @return
		 */
		public String getBusiness_catg() {
			return business_catg;
		}

		/**
		 * @return
		 */
		public String getCircle() {
			return circle;
		}

		/**
		 * @return
		 */
		public String getFileName() {
			return fileName;
		}

		/**
		 * @return
		 */
		public String getFilePath() {
			return filePath;
		}

		/**
		 * @return
		 */
		public String getMessage() {
			return message;
		}

	/**
	 * @return
	 */
	public String getMonth() {
		return month;
	}

	/**
	 * @return
	 */
	public FormFile getThefile() {
		return thefile;
	}

	/**
	 * @return
	 */
	public String getYear() {
		return year;
	}

	/**
	 * @param list
	 */
	public void setArrCircle(ArrayList list) {
		arrCircle = list;
	}

		/**
		 * @param list
		 */
		public void setArrMonth(ArrayList list) {
			arrMonth = list;
		}

	/**
	 * @param list
	 */
	public void setArrYear(ArrayList list) {
		arrYear = list;
	}

		/**
		 * @param string
		 */
		public void setBusiness_catg(String string) {
			business_catg = string;
		}

		/**
		 * @param string
		 */
		public void setCircle(String string) {
			circle = string;
		}

		/**
		 * @param string
		 */
		public void setFileName(String string) {
			fileName = string;
		}

		/**
		 * @param string
		 */
		public void setFilePath(String string) {
			filePath = string;
		}

		/**
		 * @param string
		 */
		public void setMessage(String string) {
			message = string;
		}

	/**
	 * @param string
	 */
	public void setMonth(String string) {
		month = string;
	}

	/**
	 * @param file
	 */
	public void setThefile(FormFile file) {
		thefile = file;
	}

	
	/**
	 * @param string
	 */
	public void setYear(String string) {
		year = string;
	}

		/**
		 * @return
		 */
		public String getUploadedBy() {
			return uploadedBy;
		}

		/**
		 * @param string
		 */
		public void setUploadedBy(String string) {
			uploadedBy = string;
		}

		/**
		 * @return
		 */
		public String getUploadedDt() {
			return uploadedDt;
		}

		/**
		 * @param string
		 */
		public void setUploadedDt(String string) {
			uploadedDt = string;
		}

		/**
		 * @return
		 */
		public String getCircleId() {
			return circleId;
		}

		/**
		 * @param string
		 */
		public void setCircleId(String string) {
			circleId = string;
		}

}

