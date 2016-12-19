package com.ibm.reports.service.impl;

	import java.util.List;

	import org.apache.log4j.Logger;

	import com.ibm.dp.dao.DPPrintBulkAcceptanceDao;
import com.ibm.dp.dao.DPPrintReverseCollectionDao;
	import com.ibm.dp.dao.impl.DPPrintBulkAcceptanceDaoImpl;
import com.ibm.dp.dao.impl.DPPrintReverseCollectionDaoImpl;
	import com.ibm.dp.dto.DPPrintBulkAcceptanceDTO;
import com.ibm.dp.service.DPPrintBulkAcceptanceService;
import com.ibm.dp.service.DPPrintReverseCollectionService;

	public class DPPrintReverseCollectionServiceImpl implements DPPrintReverseCollectionService{
		private Logger logger = Logger.getLogger(DPPrintBulkAcceptanceServiceImpl.class.getName());


		public String getProductName(String productId) {
			// TODO Auto-generated method stub
			logger.info("********************** getInvoiceQty() **************************************");
			String productName="";
			DPPrintReverseCollectionDao printReverseCollectionDao = DPPrintReverseCollectionDaoImpl.getInstance();
			try {
				productName = printReverseCollectionDao.getProductName(productId);
			} catch (Exception e) {
				logger.error("Exception occured::" + e.getMessage());
				logger.error(e.getMessage(), e);
			
			}
			return productName;
		}

		
	}

