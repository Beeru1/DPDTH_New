/**************************************************************************
 * File     : BulkBizUserAssoThreadManager.java
 * Author   : Abhipsa
 * Created  : Dec 8, 2008 
 * Modified : Dec 8, 2008 
 * Version  : 0.1
 **************************************************************************
 *                               HISTORY
 **************************************************************************
 * V0.1		Dec 8, 2008 	Abhipsa	First Cut.
 * V0.2		Dec 8, 2008 	Abhipsa	First modification
 **************************************************************************
 *
 * Copyright @ 2002 This document has been prepared and written by 
 * IBM Global Services on behalf of Bharti, and is copyright of Bharti
 *
 **************************************************************************/
package com.ibm.virtualization.ussdactivationweb.bulkupload.ThreadManager;

import org.apache.log4j.Logger;

import com.ibm.virtualization.ussdactivationweb.bulkupload.services.impl.BulkBizUserAssoServiceImpl;
import com.ibm.virtualization.ussdactivationweb.bulkupload.services.interfaces.BulkBizUserAssoService;
import com.ibm.virtualization.ussdactivationweb.dto.BulkBizUserAssoDTO;
import com.ibm.virtualization.ussdactivationweb.utils.Constants;
import com.ibm.virtualization.ussdactivationweb.utils.Utility;

/**
 * @author a_gupta
 *
 */
public class BulkBizUserAssoThreadManager implements Runnable {

	private static Logger logger = Logger
			.getLogger(BulkBizUserAssoThreadManager.class.getName());
	
	private BulkBizUserAssoDTO bulkBizUserAssoDTO;
	
	private String threadName;

	private static int currentThreadCount = 0;

	private static boolean maxThreadCountReached = false;
	
	
	/**
	 * This Constructor is called during File-Upload.
	 * 
	 * @param methodIdentifier
	 * @param bulkBizUserAssoDTO
	 */
	public BulkBizUserAssoThreadManager(BulkBizUserAssoDTO bulkBizUserAssoDTO) {
		this.bulkBizUserAssoDTO = bulkBizUserAssoDTO;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Runnable#run()
	 */
	public void run() {

		threadName = new StringBuffer(50).append(bulkBizUserAssoDTO.getFileId())
				.append("-").append(Thread.currentThread().getName())
				.toString();
		Thread.currentThread().setName(threadName);

		threadStarted();
		try {
			
			processBizAssoFile();
			
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
				BulkBizUserAssoThreadManager.class.wait();

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
		BulkBizUserAssoThreadManager.class.notifyAll();
	}
	
	private void processBizAssoFile() {
		logger.debug("Entering method processFixedBaseFile().. ");
		
		try {
			
			BulkBizUserAssoService bulkBizUserAssoService = new BulkBizUserAssoServiceImpl();
			bulkBizUserAssoService.uploadFile(bulkBizUserAssoDTO);
			
		}catch(Exception e){
			
		}
	}
		
	

}
