/**
 * 
 */
package com.ibm.virtualization.ussdactivationweb.bulkupload.ThreadManager;

import org.apache.log4j.Logger;

import com.ibm.virtualization.ussdactivationweb.bulkupload.services.impl.BulkSubscriberServiceImpl;
import com.ibm.virtualization.ussdactivationweb.bulkupload.services.interfaces.BulkSubscriberService;
//import com.ibm.virtualization.ussdactivationweb.dto.BulkSubscriberDTO;
import com.ibm.virtualization.ussdactivationweb.utils.Constants;
import com.ibm.virtualization.ussdactivationweb.utils.Utility;

/**
 * @author a_gupta
 *
 */
//public class BulkSubscriberThreadManager implements Runnable {
public class BulkSubscriberThreadManager {

	private static Logger logger = Logger
			.getLogger(BulkSubscriberThreadManager.class.getName());
	
	//private BulkSubscriberDTO bulkSubscriberDTO;
	
	private String threadName;

	private static int currentThreadCount = 0;

	private static boolean maxThreadCountReached = false;
	
	
	/**
	 * This Constructor is called during File-Upload.
	 * 
	 * @param methodIdentifier
	 * @param bulkBizUserAssoDTO
	 */
	//public BulkSubscriberThreadManager(BulkSubscriberDTO bulkSubscriberDTO) {
	//	this.bulkSubscriberDTO = bulkSubscriberDTO;
	//}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Runnable#run()
	 */
	/*
	public void run() {

		threadName = new StringBuffer(50).append(bulkSubscriberDTO.getFileId())
				.append("-").append(Thread.currentThread().getName())
				.toString();
		Thread.currentThread().setName(threadName);

		threadStarted();
		try {
			
			processSubscriberFile();
			
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
				BulkSubscriberThreadManager.class.wait();

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
		BulkSubscriberThreadManager.class.notifyAll();
	}
	
	/** uploaded file processed **/
	private void processSubscriberFile() {
		logger.debug("Entering method processFixedBaseFile().. ");
		
		try {
			
			BulkSubscriberService bulkSubscriberService = new BulkSubscriberServiceImpl();
			//bulkSubscriberService.uploadFile(bulkSubscriberDTO);
		}catch(Exception e){
			
		}
	}
		
	

}
