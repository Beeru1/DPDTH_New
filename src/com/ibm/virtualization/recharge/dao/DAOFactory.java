/*****************************************************************************\
 **
 ** Virtualization - Recharge.
 **
 ** Copyright (c) 2007-2008 IBM.
 ** All Rights Reserved
 **
 **
 \****************************************************************************/

package com.ibm.virtualization.recharge.dao;

import java.sql.Connection;

import org.apache.log4j.Logger;

import com.ibm.dp.dao.AccountDetailReportDao;
import com.ibm.dp.dao.AccountManagementActivityReportDao;
import com.ibm.dp.dao.AvStockUploadDao;
import com.ibm.dp.dao.C2SBulkUploadDao;
import com.ibm.dp.dao.CannonDPDataDAO;
import com.ibm.dp.dao.ChurnDCGenerationDAO;
import com.ibm.dp.dao.CircleActivationSummarylReportDao;
import com.ibm.dp.dao.CollectionDetailReportDao;
import com.ibm.dp.dao.ConfigurationMasterDao;
import com.ibm.dp.dao.DPCreateAccountDao;
import com.ibm.dp.dao.DPCreateAccountITHelpDao;
import com.ibm.dp.dao.DPCreateBeatDao;
import com.ibm.dp.dao.DPDcCreationDao;
import com.ibm.dp.dao.DPDeliveryChallanAcceptDao;
import com.ibm.dp.dao.DPDistPinCodeMapDao;
import com.ibm.dp.dao.DPDistSecurityDepositLoanDao;
import com.ibm.dp.dao.DPDistributorStockTransferDao;
import com.ibm.dp.dao.DPHierarchyAcceptDAO;
import com.ibm.dp.dao.DPInterSSDStockTransferReportDao;
import com.ibm.dp.dao.DPInterSSDTransferBulkUploadDao;
import com.ibm.dp.dao.DPMissingStockApprovalDao;
import com.ibm.dp.dao.DPOpenStockDepleteDao;
import com.ibm.dp.dao.DPPendingListTransferBulkUploadDao;
import com.ibm.dp.dao.DPPoAcceptanceBulkDAO;
import com.ibm.dp.dao.DPProductSecurityListDao;
import com.ibm.dp.dao.DPPurchaseOrderDao;
import com.ibm.dp.dao.DPReverseChurnCollectionDAO;
import com.ibm.dp.dao.DPReverseCollectionDao;
import com.ibm.dp.dao.DPSecFileUploadDao;
import com.ibm.dp.dao.DPStbInBulkDAO;
import com.ibm.dp.dao.DPStockLevelBulkUploadDao;
import com.ibm.dp.dao.DPWrongShipmentDao;
import com.ibm.dp.dao.DayWiseActivationSummarylReportDao;
import com.ibm.dp.dao.DistAvStockUploadDao;
import com.ibm.dp.dao.DistRecoDao;
import com.ibm.dp.dao.DistributorSTBMappingDao;
import com.ibm.dp.dao.DpDcChangeStatusDao;
import com.ibm.dp.dao.DpDcChurnStatusDao;
import com.ibm.dp.dao.DpDcDamageStatusDao;
import com.ibm.dp.dao.DpDcFreshStatusDao;
import com.ibm.dp.dao.ErrorPODao;
import com.ibm.dp.dao.GeographyDao;
import com.ibm.dp.dao.HierarchyTransferDao;
import com.ibm.dp.dao.InterSsdTransferAdminDao;
import com.ibm.dp.dao.NSBulkUploadDao;
import com.ibm.dp.dao.NonSerializedConsumptionReportDao;
import com.ibm.dp.dao.PODetailReportDao;
import com.ibm.dp.dao.POStatusUpdateDao;
import com.ibm.dp.dao.RecoSummaryDao;
import com.ibm.dp.dao.RepairSTBDao;
import com.ibm.dp.dao.RetailerLapuDataDao;
import com.ibm.dp.dao.STBFlushOutDao;
import com.ibm.dp.dao.STBStatusDao;
import com.ibm.dp.dao.SackDistributorDao;
import com.ibm.dp.dao.SignInvoiceDao;
import com.ibm.dp.dao.StockDeclarationDao;
import com.ibm.dp.dao.StockRecoSummReportDao;
import com.ibm.dp.dao.StockTransferDao;
import com.ibm.dp.dao.TransferHierarchyDao;
import com.ibm.dp.dao.UploadDistStockEligibilityDao;
import com.ibm.dp.dao.WHdistmappbulkDao;
import com.ibm.dp.dao.WarehouseMstFrmBotreeDao;
import com.ibm.dp.dao.impl.ConfigurationMasterDaoRdms;
import com.ibm.dp.dao.impl.GeographyDaoRdbms;
import com.ibm.dpmisreports.dao.DropDownUtilityAjaxDao;
import com.ibm.hbo.dao.HBOProductDAO;
import com.ibm.hbo.dao.HBOStockDAO;
import com.ibm.virtualization.recharge.dao.rdbms.CacheDaoRdbms;
import com.ibm.virtualization.recharge.dao.rdbms.GroupRoleMappingDaoRdbms;
import com.ibm.virtualization.recharge.dao.rdbms.QueryDaoRdbms;
import com.ibm.virtualization.recharge.dao.rdbms.RegionDaoRdbms;
import com.ibm.virtualization.recharge.dao.rdbms.UserDaoRdbms;

public abstract class DAOFactory {
	/*
	 * Logger for this class.
	 */

	protected static Logger logger = Logger.getLogger(UserDaoRdbms.class
			.getName());

	// List of DAO types supported by the factory
	public static final int ORACLE = 1;

	public static final int DB2 = 2;

	public abstract AccountDao getAccountDao(Connection connection);
	
	public abstract CircleDao getCircleDao(Connection connection);
	public CircleActivationSummarylReportDao getCircleActivationSummarylReportDao(Connection connection){ return null; }
	public DayWiseActivationSummarylReportDao getDayWiseActivationSummarylReportDao(Connection connection){ return null; }
	public abstract CircleTopupConfigDao getCircleTopupConfigDao(
			Connection connection);

	public abstract DistributorTransactionDao getDistributorTransactionDao(
			Connection connection);

	public abstract GroupDao getGroupDao(Connection connection);

	public GroupRoleMappingDao getGroupRoleMappingDao(Connection connection) {

		return new GroupRoleMappingDaoRdbms(connection);
	}

	public abstract LoginDao getLoginDao(Connection connection);

	public abstract LogTransactionDao getLogTransactionDao(Connection connection);

	public QueryDao getQueryDao(Connection connection) {

		return new QueryDaoRdbms(connection);
	}

	public abstract RechargeDao getRechargeDao(Connection connection);

	public abstract ReportDao getReportDao(Connection connection);

	public abstract SequenceDao getSequenceDao(Connection connection);

	public abstract SysConfigDao getSysConfigDao(Connection connection);

	public abstract UserDao getUserDao(Connection connection);

	public RegionDao getRegionDao(Connection connection) {
		return new RegionDaoRdbms(connection);
	}

	public CacheDao getCacheDao(Connection connection) {
		return new CacheDaoRdbms(connection);
	}

	public DPPurchaseOrderDao getDPPurchaseOrderDao(Connection connection) {//Rohit kunder
		// TODO Auto-generated method stub
		return null;
	}
	public DPSecFileUploadDao getDPSecFileUploadDao(Connection connection) {//Rohit kunder
		// TODO Auto-generated method stub
		return null;
	}
	
	public GeographyDao getGeographyDao(Connection connection) {
		return new GeographyDaoRdbms(connection);
	}
//	public DPPurchaseOrderDao insertPurchaseOrderDao(Connection connection) {//Rohit kunder
//		// TODO Auto-generated method stub
//		return new DPPurchaseOrderDaoOra(connection);
//	}
	
	public abstract INRouterDao getINRouterDao(Connection connection);
	
	// Added by Anju
	
	public abstract DPCreateAccountDao getNewAccountDao(Connection connection);
	public abstract DPCreateBeatDao createBeat(Connection connection);
	
	// by vivek
	public abstract DPMasterDAO getDPMasterDAO(Connection connection);

	public abstract DPMasterDAO getNewProductDAO(Connection connection);
	public abstract DPMasterDAO getUpdateProductDAO(Connection connection);
	
	public abstract DPMasterDAO getBusinessCategoryDao(Connection connection);
	
	public abstract DPMasterDAO select(Connection connection);
	
	public abstract DPMasterDAO getCircleListDao(Connection connection); 
	public abstract DPMasterDAO update(Connection connection);
	public abstract DPMasterDAO getDataForEdit(Connection connection);
	
	public abstract MinReorderDao getAssignedorderDao(Connection connection);
	public abstract MinReorderDao check(Connection connection);
	public abstract MinReorderDao updateAssign(Connection connection);
	public abstract DPMasterDAO warranty(Connection connection);
	public abstract DPMasterDAO getProductListwarrantyDao(Connection connection);
	public abstract DPMasterDAO select1(Connection connection);
	public abstract DPMasterDAO getWarrantyDao(Connection connection);
	public abstract DPMasterDAO insertWebservice(Connection connection);
	public abstract DPMasterDAO selectProduct(Connection connection);
	public abstract MinReorderDao getDistributorDao(Connection connection);
	public abstract HBOProductDAO getProductDao(Connection connection);
	public abstract HBOStockDAO getStockDao(Connection connection);
	public  abstract DPMasterDAO selectwarranty(Connection connection);
	public  abstract DPMasterDAO getWarranty(Connection connection) ;

	public abstract SignInvoiceDao getSignInvoiceDao(Connection connection);
	
	public static DAOFactory getDAOFactory(int whichFactory) {

		switch (whichFactory) {
		case ORACLE:
			logger.trace("Using oracle dao factory");
			return new OracleDAOFactory();
		case DB2:
			logger.trace("Using db2 dao factory");
			return new DB2DAOFactory();

		default:
			return null;
		}
	}

	
	public abstract DPMasterDAO getCardGroupDao(Connection connection);	

    // Add by harbans on wrong shipment.
	public abstract DPWrongShipmentDao getWrongShipmentDAO(Connection connection);


	//----------------Added by Nazim Hussain-------------------------------

	public DPDeliveryChallanAcceptDao getInitDeliveryChallanDao(Connection connection) {
		// TODO Auto-generated method stub
		return null;
	}

	public DPDeliveryChallanAcceptDao reportDeliveryChallanDao(Connection connection) {
		// TODO Auto-generated method stub
		return null;
	}

	
	public STBFlushOutDao getSTBListDao(Connection connection) {
		// TODO Auto-generated method stub
		return null;
	}
	
	public ErrorPODao getErrorSTBListDao(Connection connection) {
		// TODO Auto-generated method stub
		return null;
	}
	
	public DPMissingStockApprovalDao getInitMissingStockDao(Connection connection) {
		// TODO Auto-generated method stub
		return null;
	}
	
	//added by saumya
		public DPHierarchyAcceptDAO getHierarchyListDao(Connection connection) {
		// TODO Auto-generated method stub
		return null;
	}
	//ended by saumya
	
	public DPMissingStockApprovalDao saveMissingStockDao(Connection connection) {
		// TODO Auto-generated method stub
		return null;
	}

	public DPDistributorStockTransferDao getInitDistStockTransferDao(Connection connection) {
		// TODO Auto-generated method stub
		return null;
	}

	public DPDistributorStockTransferDao getAvailableStockDao(Connection connection) {
		// TODO Auto-generated method stub
		return null;
	}

	public DPDistributorStockTransferDao transferDistStockDao(Connection connection) {
		// TODO Auto-generated method stub
		return null;
	}
	
	public DPOpenStockDepleteDao depleteOpenStockDao(Connection connection) {
		// TODO Auto-generated method stub
		return null;
	}
	
	public DPOpenStockDepleteDao getOpenStockDepleteInitDataDao(Connection connection) {
		// TODO Auto-generated method stub
		return null;
	}
	
	public DPOpenStockDepleteDao filterDitributorsDao(Connection connection) {
		// TODO Auto-generated method stub
		return null;
	}
	
	public DPOpenStockDepleteDao getOpenStockBalanceDao(Connection connection) {
		// TODO Auto-generated method stub
		return null;
	}
	
	public DPReverseCollectionDao getDPReverseCollectionDao(Connection connection) {
		return null;
	}
	
	
//	----------------Added by Nazim Hussain ends here----------------------


	//Added by Mohammad Aslam
	public abstract StockDeclarationDao getStockDeclarationDao(Connection connection);
	public abstract StockTransferDao getStockTransferDao(Connection connection);
    
	// **********Added By Shilpa Khanna ********//

	public DPDcCreationDao getDPDcCreationDao(Connection connection) {
		return null;
	}

//	 **********Added By Shilpa Khanna Ends********//
	
//	 **********Added By Shilpa Khanna for DC Status change********//

	public DpDcDamageStatusDao getDpDcDamageStatusDao(Connection connection) {
		return null;
	}

	public DpDcChangeStatusDao getDpDcChangeStatusDao(Connection connection) {
		return null;
	}
//	 **********Added By Shilpa Khanna Ends********//
	
//	 **********Added By DIGVIJAY SINGH THAKUR for DC Status FRESH********//

	public DpDcFreshStatusDao getDpDcFreshStatusDao(Connection connection) {
		return null;
	}
	
	public RecoSummaryDao getRecoSummaryDao(Connection connection) {
		return null;
	}

//	 **********Added By DIGVIJAY SINGH THAKUR Ends********//	

//	 **********Added By Shilpa Khanna for Hierarchy Transfer ********//

	public HierarchyTransferDao getHierarchyTransferDao(Connection connection) {
		return null;
	}

	public DPHierarchyAcceptDAO getHierarchyTransferInit(Connection connection) {
		// TODO Auto-generated method stub
		return null;
	}

	public DPHierarchyAcceptDAO acceptHierarchy(Connection connection) {
		// TODO Auto-generated method stub
		return null;
	}

//	 **********Added By Shilpa Khanna Ends********//
	
//	 **********Added By Shilpa Khanna for STB Repair********//

	public RepairSTBDao getRepairSTBDao(Connection connection) {
		return null;
	}

//	 **********Added By Shilpa Khanna Ends********//
//	 **********Added By Shilpa Khanna for Sack Distributor********//

	public SackDistributorDao getSackDistributorDao(Connection connection) {
		return null;
	}

//	 **********Added By Shilpa Khanna Ends********//
	public abstract C2SBulkUploadDao getuploadExcelDao(Connection connection);
	
	
	public abstract STBFlushOutDao getuploadFreshExcelDao(Connection connection);
	
	public abstract STBFlushOutDao getuploadRevExcelDao(Connection connection);
	
	public abstract STBFlushOutDao getFlushDPRevSerialNumbersDao(Connection connection);
	
	public abstract STBFlushOutDao getFlushDPSerialNumbersDao(Connection connection);
	
	
	
	/*public C2SBulkUploadDao uploadExcel(Connection connection) {
		return null;
	}*/
	
//	 **********Added By Shilpa Khanna for STB Status Webservice********//

	public STBStatusDao getSTBStatusDao(Connection connection) {
		return null;
	}
	
	public DPPoAcceptanceBulkDAO getuploadExcel(Connection connection) {
		return null;
	}
	
	

	public DropDownUtilityAjaxDao getDropDownUtilityAjaxDaoConnection(Connection connection) {
		// TODO Auto-generated method stub
		return null;
	}

//	 **********Added By Shilpa Khanna Ends********//
//	 **********Added By Priya for PO Status Update Webservice********//

	public POStatusUpdateDao getPOStatusUpdateDao(Connection connection) {
		return null;
	}
	public POStatusUpdateDao AccountDetailReportDao(Connection connection) {
		return null;
	}
	
	public DPStbInBulkDAO getStbUploadExcel(Connection connection) {
		return null;
	}
	
//	public DPCreateAccountDao getStates(Connection connection) {
//		return null;
//	}
//	
	public abstract NSBulkUploadDao getNsuploadExcelDao(Connection connection);
	
	public abstract WHdistmappbulkDao getDistWhuploadExcelDao(Connection connection);
	
	
	public abstract InterSsdTransferAdminDao getInterSSDTransDao(Connection connection);
	public abstract DistRecoDao getDistRecoDao(Connection connection);
	public abstract PODetailReportDao getPoDetailReportDao(Connection connection);
	public AccountDetailReportDao getAccountDetailReportDao(Connection connection){ return null; }
	public abstract AccountManagementActivityReportDao getAccountManagementActivityReportDao(Connection connection);
	public abstract CollectionDetailReportDao getCollectionDetailReportDao(Connection connection);
	public abstract NonSerializedConsumptionReportDao getNonSerializedConsumptionReportDao(Connection connection);
	
	public abstract DPInterSSDStockTransferReportDao getDPInterSSDStockTransferReportDao(Connection connection);
	public abstract StockRecoSummReportDao getRecoSummaryReportDao(Connection connection);
//	 **********Added By Shilpa Khanna for Dist STB Mapping Webservice********//

	public abstract DistributorSTBMappingDao getDistSTBMapDao(Connection connection);

	//Added by Shah
	public DPReverseChurnCollectionDAO reportChurnDao(Connection connection) 
	{		
		return null;	
	}
	
	public ChurnDCGenerationDAO getviewDCDetails(Connection connection) {
		// TODO Auto-generated method stub
		return null;
	}
	
	public DpDcChurnStatusDao getDpDcChurnStatusDao(Connection connection) {
		// TODO Auto-generated method stub
		return null;
	}
	
    //END

	
//Added by ARTI
	public abstract DPStockLevelBulkUploadDao getStockLevelExcelDao(Connection connection);
	public abstract DPDistSecurityDepositLoanDao getSecurityLoanDepositExcelDao(Connection connection);
	
	public abstract DPProductSecurityListDao getProductSecurityListExcelDao(Connection connection);
	public abstract WarehouseMstFrmBotreeDao getWarehouseMasterExcelDao(Connection connection);
	//End

	
	public abstract DPCreateAccountITHelpDao getNewAccountITHelpDao(Connection connection);

	public abstract TransferHierarchyDao getTransHierConnDao(Connection connection) ;	
	//Added by suagndha
	public abstract DPDistPinCodeMapDao getDistPincodeMapExcelDao(Connection connection);
	// ends here
	
	public abstract AvStockUploadDao getuploadFile(Connection connection);
	
	public abstract DistAvStockUploadDao getuploadFileDist(Connection connection);
	
	public abstract DPStbInBulkDAO getStbUploadExcelHistory(Connection connection) ;

	public abstract RetailerLapuDataDao getRetailerLapuDataDao(Connection connection);
	public abstract RejectedInvDAO getRejectedInvDAO(Connection connection);
	
	public  CannonDPDataDAO getCannonDPDataDAO(Connection connection){
		return null;
	}
	
	//added by jyotsna sharma
	public abstract  ConfigurationMasterDao getConfigrationMasterDao(Connection connection);
	
//added by Sanjay
	
	public abstract DPPendingListTransferBulkUploadDao getPendingListTransferExcelDao(Connection connection);
	
	public abstract DPInterSSDTransferBulkUploadDao getInterSSDTransferExcelDao(Connection connection);
	
	
	//

	
}



