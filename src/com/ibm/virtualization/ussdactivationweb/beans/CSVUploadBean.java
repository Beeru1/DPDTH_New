 
package com.ibm.virtualization.ussdactivationweb.beans;
import java.util.ArrayList;
import org.apache.struts.action.ActionForm;
import org.apache.struts.upload.FormFile;

public class CSVUploadBean extends ActionForm
{
 
		private FormFile file=null;
	    private String filepath=null;
    	private int logID;
    	private ArrayList fileuploadedstatus;
		/**
	 * @return
	 */
	public FormFile getFile() {
		return file;
	}

	/**
	 * @param file
	 */
	public void setFile(FormFile file) {
		this.file = file;
	}

	/**
		 * @return
		 */
		public String getFilepath() {
			return filepath;
		}

		/**
		 * @param string
		 */
		public void setFilepath(String string) {
			filepath = string;
		}
		/**
		 * @return
		 */
		public int getLogID() {
			return logID;
		}

		/**
		 * @param string
		 */
		public void setLogID(int string) {
			logID = string;
		}

		 

		/**
		 * @return
		 */
		public ArrayList getFileuploadedstatus() {
			return fileuploadedstatus;
		}

		/**
		 * @param list
		 */
		public void setFileuploadedstatus(ArrayList list) {
			fileuploadedstatus = list;
		}

}
	