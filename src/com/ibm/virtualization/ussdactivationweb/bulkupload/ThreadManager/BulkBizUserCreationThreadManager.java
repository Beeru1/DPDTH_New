/**
 * 
 */
package com.ibm.virtualization.ussdactivationweb.bulkupload.ThreadManager;

import org.apache.log4j.Logger;

import com.ibm.virtualization.ussdactivationweb.bulkupload.services.impl.BulkBizUserCreationServiceImpl;
import com.ibm.virtualization.ussdactivationweb.bulkupload.services.interfaces.BulkBizUserCreationService;
//import com.ibm.virtualization.ussdactivationweb.dto.BulkBizUserCreationDTO;
import com.ibm.virtualization.ussdactivationweb.utils.Constants;
import com.ibm.virtualization.ussdactivationweb.utils.Utility;

/**
 * @author Nitesh
 *
 */
//public class BulkBizUserCreationThreadManager implements Runnable {
public class BulkBizUserCreationThreadManager {

	private static Logger logger = Logger
			.getLogger(BulkBizUserCreationThreadManager.class.getName());
	
	//private BulkBizUserCreationDTO bulkBizUserrDTO;
	
	private String threadName;

	private static int currentThreadCount = 0;

	private static boolean maxThreadCountReached = false;
	
	
	/**
	 * This Constructor is called during File-Upload.
	 * 
	 * @param methodIdentifier
	 * @param bulkBizUserAssoDTO
	 */
	//public BulkBizUserCreationThreadManager(BulkBizUserCreationDTO bulkBizUserCreationDTO) {
		//this.bulkBizUserrDTO = bulkBizUserCreationDTO;
	//}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Runnable#run()
	 */
	/*
	public void run() {

		threadName = new StringBuffer(50).append(bulkBizUserrDTO.getFileId())
				.append("-").append(Thread.currentThread().getName())
				.toString();
		Thread.currentThread().setName(threadName);

		threadStarted();
		try {
			
			processBizUserFile();
			
		} catch (Exception ex) {
			logger.error("Exception occured in thread manager: "
					+ ex.getMessage(), ex);
			ex.printStackTrace();
		} finally {
			try {
				threadStopped();
			} catch (Exception ex) {
				logger.error(new StringBuffer(100).append(
						"Exception occured at ThreadStop : ")
						.append(threadName).append(" : ").append(
								ex.getMessage().toString()), ex);
				ex.printStackTrace();
			}
		}
		logger.debug("Completed Thread.." + threadName);
	}

	*/
	private static synchronized int getMaxThreadCount() {
		int maxProcessCount = Integer.parseInt(Utility.getValueFromBundle(
				Constants.MAX_THREAD_COUNT,
				Constants.BULK_UPLOAD_APPLICATION_RESOURCE_BUNDLE));
		return maxProcessCount;
	}
	
	public static synchronized int getCurrentThreadCount() {
		return currentThreadCount;
	}

	public static synchronized void threadStarted() {
		currentThreadCount++;
		logger.debug("--->currentThreadCount: " + currentThreadCount);
		while (currentThreadCount > getMaxThreadCount()) {
			try {
				logger.debug("Starting to wait...."
						+ Thread.currentThread().getName());
				BulkBizUserCreationThreadManager.class.wait();

				if (!maxThreadCountReached) {
					maxThreadCountReached = true;
					break;
				}

			} catch (InterruptedException e) {
				logger
						.debug(" Interrupted Exception : Wait Over. Resuming Processing..."
								+ Thread.currentThread().getName());
			}
		}

		logger.debug("Resuming/Continuing Processing..."
				+ Thread.currentThread().getName());
	}

	public static synchronized void threadStopped() {

		currentThreadCount--;
		logger.debug("--->currentThreadCount: " + currentThreadCount);
		maxThreadCountReached = false;
		// maxThreadCountReachedLock.notifyAll();
		
		BulkBizUserCreationThreadManager.class.notifyAll();
	}
	
	/** uploaded file processed **/
	private void processBizUserFile() {
		logger.debug("Entering method processFixedBaseFile().. ");
		
		try {
			
			BulkBizUserCreationService bulkBizUserCreationService = new BulkBizUserCreationServiceImpl();
			//bulkBizUserCreationService.uploadFile(bulkBizUserrDTO, 241);
		}catch(Exception e){
			
		}
	}
		
	

}
