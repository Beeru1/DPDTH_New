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
import com.ibm.dp.dao.HierarchyTransferDao;
import com.ibm.dp.dao.InterSsdTransferAdminDao;
import com.ibm.dp.dao.NSBulkUploadDao;
import com.ibm.dp.dao.NonSerializedConsumptionReportDao;
import com.ibm.dp.dao.PODetailReportDao;
import com.ibm.dp.dao.POStatusUpdateDao;
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
import com.ibm.dp.dao.impl.AccountDetailReportDaoImpl;
import com.ibm.dp.dao.impl.AccountManagementActivityReportDaoImpl;
import com.ibm.dp.dao.impl.AvStockUploadDaoImpl;
import com.ibm.dp.dao.impl.C2SBulkUploadDaoImpl;
import com.ibm.dp.dao.impl.CannonDPDataDAOImpl;
import com.ibm.dp.dao.impl.ChurnDCGenerationDAOImpl;
import com.ibm.dp.dao.impl.CircleActivationSummaryReportDaoImpl;
import com.ibm.dp.dao.impl.CollectionDetailReportDaoImpl;
import com.ibm.dp.dao.impl.ConfigurationMasterDaoRdms;
import com.ibm.dp.dao.impl.DPCreateAcountITHelpRdbmsDB2;
import com.ibm.dp.dao.impl.DPCreateAcountRdbmsDB2;
import com.ibm.dp.dao.impl.DPCreateBeatDaoImpl;
import com.ibm.dp.dao.impl.DPDcCreationDaoImpl;
import com.ibm.dp.dao.impl.DPDeliveryChallanAcceptDaoImpl;
import com.ibm.dp.dao.impl.DPDistPinCodeMapDaoImpl;
import com.ibm.dp.dao.impl.DPDistSecurityDepositLoanDaoImpl;
import com.ibm.dp.dao.impl.DPDistributorStockTransferDaoImpl;
import com.ibm.dp.dao.impl.DPHierarchyAcceptDAOImpl;
import com.ibm.dp.dao.impl.DPInterSSDStockTransferReportDaoImpl;
import com.ibm.dp.dao.impl.DPInterSSDTransferBulkUploadDaoImpl;
import com.ibm.dp.dao.impl.DPMissingStockApprovalDaoImpl;
import com.ibm.dp.dao.impl.DPOpenStockDepleteDaoImpl;
import com.ibm.dp.dao.impl.DPPendingListTransferBulkUploadDaoImpl;
import com.ibm.dp.dao.impl.DPPoAcceptanceBulkDAOImpl;
import com.ibm.dp.dao.impl.DPProductSecurityListDaoImpl;
import com.ibm.dp.dao.impl.DPPurchaseOrderDaoDB2;
import com.ibm.dp.dao.impl.DPReverseChurnCollectionDAOImpl;
import com.ibm.dp.dao.impl.DPReverseCollectionDaoImpl;
import com.ibm.dp.dao.impl.DPSecFileUploadDaoImpl;
import com.ibm.dp.dao.impl.DPStbInBulkDAOImpl;
import com.ibm.dp.dao.impl.DPStockLevelBulkUploadDaoImpl;
import com.ibm.dp.dao.impl.DPWrongShipmentDaoImpl;
import com.ibm.dp.dao.impl.DayWiseActivationSummaryReportDaoImpl;
import com.ibm.dp.dao.impl.DistAvStockUploadDaoImpl;
import com.ibm.dp.dao.impl.DistRecoDaoImpl;
import com.ibm.dp.dao.impl.DistributorSTBMappingDaoImpl;
import com.ibm.dp.dao.impl.DpDcChangeStatusDaoImpl;
import com.ibm.dp.dao.impl.DpDcChurnStatusDaoImpl;
import com.ibm.dp.dao.impl.DpDcDamageStatusDaoImpl;
import com.ibm.dp.dao.impl.DpDcFreshStatusDaoImpl;
import com.ibm.dp.dao.impl.ErrorPODaoImpl;
import com.ibm.dp.dao.impl.HierarchyTransferDaoImpl;
import com.ibm.dp.dao.impl.InterSsdTransferAdminDaoImpl;
import com.ibm.dp.dao.impl.NSBulkUploadDaoImpl;
import com.ibm.dp.dao.impl.NonSerializedConsumptionReportDaoImpl;
import com.ibm.dp.dao.impl.PODetailReportDaoImpl;
import com.ibm.dp.dao.impl.POStatusUpdateDaoImpl;
import com.ibm.dp.dao.impl.RepairSTBDaoImpl;
import com.ibm.dp.dao.impl.RetailerLapuDataDaoImpl;
import com.ibm.dp.dao.impl.STBFlushOutDaoImpl;
import com.ibm.dp.dao.impl.STBStatusDaoImpl;
import com.ibm.dp.dao.impl.SackDistributorDaoImpl;
import com.ibm.dp.dao.impl.SignInvoiceDaoImpl;
import com.ibm.dp.dao.impl.StockDeclarationDaoDB2;
import com.ibm.dp.dao.impl.StockRecoSummReportDaoImpl;
import com.ibm.dp.dao.impl.StockTransferDaoDB2;
import com.ibm.dp.dao.impl.TransferHierarchyDaoImpl;
import com.ibm.dp.dao.impl.UploadDistStockEligibilityDaoImpl;
import com.ibm.dp.dao.impl.WHdistmappbulkDaoImpl;
import com.ibm.dp.dao.impl.WarehouseMstFrmBotreeDaoImpl;
import com.ibm.dpmisreports.dao.DropDownUtilityAjaxDao;
import com.ibm.dpmisreports.dao.impl.DropDownUtilityAjaxDaoImpl;
import com.ibm.hbo.dao.HBOProductDAO;
import com.ibm.hbo.dao.HBOStockDAO;
import com.ibm.hbo.dao.impl.HBOProductDAOImpl;
import com.ibm.hbo.dao.impl.HBOStockDAOImpl;
import com.ibm.virtualization.recharge.dao.rdbms.AccountDaoRdbmsDB2;
import com.ibm.virtualization.recharge.dao.rdbms.CircleDaoRdbmsDB2;
import com.ibm.virtualization.recharge.dao.rdbms.CircleTopupConfigDaoRdbmsDB2;
import com.ibm.virtualization.recharge.dao.rdbms.DPMasterDAOImplDB2;
import com.ibm.virtualization.recharge.dao.rdbms.DistributorTransactionDaoRdbmsDB2;
import com.ibm.virtualization.recharge.dao.rdbms.GroupDaoRdbmsDB2;
import com.ibm.virtualization.recharge.dao.rdbms.INRouterDaoRdbmsDB2;
import com.ibm.virtualization.recharge.dao.rdbms.LogTransactionDaoRdbmsDB2;
import com.ibm.virtualization.recharge.dao.rdbms.LoginDaoRdbmsDB2;
import com.ibm.virtualization.recharge.dao.rdbms.MinReorderDaoImpl;
import com.ibm.virtualization.recharge.dao.rdbms.RechargeDaoRdbmsDB2;
import com.ibm.virtualization.recharge.dao.rdbms.RejectedInvDAOImpl;
import com.ibm.virtualization.recharge.dao.rdbms.ReportDaoRdbmsDB2;
import com.ibm.virtualization.recharge.dao.rdbms.SequenceDaoRdbmsDB2;
import com.ibm.virtualization.recharge.dao.rdbms.SysConfigDaoRdbmsDB2;
import com.ibm.virtualization.recharge.dao.rdbms.UserDaoRdbmsDB2;


public class DB2DAOFactory extends DAOFactory {

	@Override
	public AccountDao getAccountDao(Connection connection) {

		return new AccountDaoRdbmsDB2(connection);
	}

	@Override
	public com.ibm.virtualization.recharge.dao.ReportDao getReportDao(
			Connection connection) {

		return new ReportDaoRdbmsDB2(connection);
	}

	@Override
	public com.ibm.virtualization.recharge.dao.SequenceDao getSequenceDao(
			Connection connection) {

		return new SequenceDaoRdbmsDB2(connection);
	}
	public CircleActivationSummarylReportDao getCircleActivationSummarylReportDao(Connection connection) {
		return new CircleActivationSummaryReportDaoImpl(connection);
	}
	@Override
	
	public DayWiseActivationSummarylReportDao getDayWiseActivationSummarylReportDao(Connection connection) {
		return new DayWiseActivationSummaryReportDaoImpl(connection);
	}
	@Override
	public CircleDao getCircleDao(Connection connection) {

		return new CircleDaoRdbmsDB2(connection);
	}

	@Override
	public CircleTopupConfigDao getCircleTopupConfigDao(Connection connection) {

		return new CircleTopupConfigDaoRdbmsDB2(connection);
	}

	@Override
	public DistributorTransactionDao getDistributorTransactionDao(
			Connection connection) {

		return new DistributorTransactionDaoRdbmsDB2(connection);
	}

	@Override
	public GroupDao getGroupDao(Connection connection) {
		return new GroupDaoRdbmsDB2(connection);
	}

	@Override
	public LogTransactionDao getLogTransactionDao(Connection connection) {

		return new LogTransactionDaoRdbmsDB2(connection);
	}

	@Override
	public LoginDao getLoginDao(Connection connection) {

		return new LoginDaoRdbmsDB2(connection);
	}

	@Override
	public RechargeDao getRechargeDao(Connection connection) {

		return new RechargeDaoRdbmsDB2(connection);
	}

	@Override
	public SysConfigDao getSysConfigDao(Connection connection) {

		return new SysConfigDaoRdbmsDB2(connection);
	}

	@Override
	public UserDao getUserDao(Connection connection) {

		return new UserDaoRdbmsDB2(connection);
	}

	@Override
	public INRouterDao getINRouterDao(Connection connection) {
		return new INRouterDaoRdbmsDB2(connection);
	}
	
	@Override //Added by Anju
	public DPCreateAccountDao getNewAccountDao(Connection connection) {
		return new DPCreateAcountRdbmsDB2(connection);
	}
	
	@Override
	public DPCreateBeatDao createBeat(Connection connection) {
		// TODO Auto-generated method stub
		return new DPCreateBeatDaoImpl(connection);
	}

//Vivek
	
	@Override
	public DPMasterDAO getDPMasterDAO(Connection connection) {
		// TODO Auto-generated method stub
		return new DPMasterDAOImplDB2(connection);
	}

	
	
	@Override
	
	
	public DPPurchaseOrderDao getDPPurchaseOrderDao(Connection connection) {
	return new DPPurchaseOrderDaoDB2(connection);
}
	public DPSecFileUploadDao getDPSecFileUploadDao(Connection connection) {//Rohit kunder
		// TODO Auto-generated method stub
		return new DPSecFileUploadDaoImpl(connection);
	}


	@Override
	public DPMasterDAO getBusinessCategoryDao(Connection connection) {
		// TODO Auto-generated method stub
		return new DPMasterDAOImplDB2(connection);
	}
	

	@Override
	public DPMasterDAO select(Connection connection) {
		// TODO Auto-generated method stub
		return new DPMasterDAOImplDB2(connection);
	}

	@Override
	public DPMasterDAO getCircleListDao(Connection connection) {
		// TODO Auto-generated method stub
		return new DPMasterDAOImplDB2(connection);
	}

	@Override
	public DPMasterDAO update(Connection connection) {
		return new DPMasterDAOImplDB2(connection);
	}

	@Override
	public DPMasterDAO getDataForEdit(Connection connection) {
		return new DPMasterDAOImplDB2(connection);
	}

	@Override
	public MinReorderDao getAssignedorderDao(Connection connection) {
		return new MinReorderDaoImpl(connection);
	}
	
	public MinReorderDao check(Connection connection){
		return new MinReorderDaoImpl(connection);
	}

	@Override
	public MinReorderDao updateAssign(Connection connection) {
		return new MinReorderDaoImpl(connection);
	}

	@Override
	public DPMasterDAO getProductListwarrantyDao(Connection connection) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public DPMasterDAO select1(Connection connection) {
		return new DPMasterDAOImplDB2(connection);
	}


	@Override
	public DPMasterDAO warranty(Connection connection) {
		// TODO Auto-generated method stub
		return null;
	}
   
	@Override
	public DPMasterDAO getWarrantyDao(Connection connection) {
		return new DPMasterDAOImplDB2(connection);
	}

	@Override
	public DPMasterDAO getNewProductDAO(Connection connection) {
		return new DPMasterDAOImplDB2(connection);
	}

	@Override
	public DPMasterDAO insertWebservice(Connection connection) {
		return new DPMasterDAOImplDB2(connection);
	}

	@Override
	public DPMasterDAO getUpdateProductDAO(Connection connection) {
		return new DPMasterDAOImplDB2(connection);
	}

	@Override
	public DPMasterDAO selectProduct(Connection connection) {
		return new DPMasterDAOImplDB2(connection);
	}

	@Override
	public MinReorderDao getDistributorDao(Connection connection) {
		return new MinReorderDaoImpl(connection);
	}

	@Override
	public HBOProductDAO getProductDao(Connection connection) {
		return new HBOProductDAOImpl(connection);
	}
	
	@Override
	public HBOStockDAO getStockDao(Connection connection) {
		return new HBOStockDAOImpl(connection);
	}
	public DPMasterDAO getWarranty(Connection connection) {
		return new DPMasterDAOImplDB2(connection);
	}

	@Override
	public DPMasterDAO selectwarranty(Connection connection) {
		// TODO Auto-generated method stub
		return null;
	}
	public DPMasterDAO getCardGroupDao(Connection connection) {
		// TODO Auto-generated method stub
		return new DPMasterDAOImplDB2(connection);
	}
	
	public DPWrongShipmentDao getWrongShipmentDAO(Connection connection) {
		return new DPWrongShipmentDaoImpl(connection);
	}

	//----------------Added by Nazim Hussain-------------------------------
	@Override	
	public DPDeliveryChallanAcceptDao getInitDeliveryChallanDao(Connection connexction)
	{
		return new DPDeliveryChallanAcceptDaoImpl(connexction);
	}
	
	@Override	
	public DPDeliveryChallanAcceptDao reportDeliveryChallanDao(Connection connexction)
	{
		return new DPDeliveryChallanAcceptDaoImpl(connexction);
	}
	
	@Override
	public DPMissingStockApprovalDao getInitMissingStockDao(Connection connection)
	{
		return new DPMissingStockApprovalDaoImpl(connection);
	}
	
	@Override
	public DPMissingStockApprovalDao saveMissingStockDao(Connection connection) {
		return new DPMissingStockApprovalDaoImpl(connection);
	}
	
	@Override
	public DPDistributorStockTransferDao getInitDistStockTransferDao(Connection connection) {
		return new DPDistributorStockTransferDaoImpl(connection);
	}
	
	@Override
	public DPDistributorStockTransferDao getAvailableStockDao(Connection connection) {
		return new DPDistributorStockTransferDaoImpl(connection);
	}
	
	public DPDistributorStockTransferDao transferDistStockDao(Connection connection) {
		return new DPDistributorStockTransferDaoImpl(connection);
	}
	
	@Override
	public DPOpenStockDepleteDao depleteOpenStockDao(Connection connection) {
		return new DPOpenStockDepleteDaoImpl(connection);
	}
	
	@Override
	public DPOpenStockDepleteDao getOpenStockDepleteInitDataDao(Connection connection) {
		return new DPOpenStockDepleteDaoImpl(connection);
	}
	
	@Override
	public DPOpenStockDepleteDao filterDitributorsDao(Connection connection) {
		return new DPOpenStockDepleteDaoImpl(connection);
	}
	
	@Override
	public DPReverseCollectionDao getDPReverseCollectionDao(Connection connection) {
		return new DPReverseCollectionDaoImpl(connection);
	}
	
	//----------------Added by Nazim Hussain ends here----------------------


	//Added by Mohammad Aslam
	@Override
	public StockDeclarationDao getStockDeclarationDao(Connection connection) {
		return new StockDeclarationDaoDB2(connection);
	}
	public StockTransferDao getStockTransferDao(Connection connection) {
		return new StockTransferDaoDB2(connection);
	}
	
	//Added by Shilpa khanna
	
	@Override
	public DPDcCreationDao getDPDcCreationDao(Connection connection) {
		return new DPDcCreationDaoImpl(connection);
	}
	
//Added by Shilpa khanna for DC Change Status
	
	@Override
	public DpDcDamageStatusDao getDpDcDamageStatusDao(Connection connection) {
		return new DpDcDamageStatusDaoImpl(connection);
	}
	
	public DpDcFreshStatusDao getDpDcFreshStatusDao(Connection connection) {
		return new DpDcFreshStatusDaoImpl(connection);
	}
	
	
//Added by Shilpa khanna for Hierarchy Transfer
	
	@Override
	public DPHierarchyAcceptDAOImpl getHierarchyListDao(Connection connection) {
		return new DPHierarchyAcceptDAOImpl(connection);
	}
	
	@Override
	public DPHierarchyAcceptDAO getHierarchyTransferInit(Connection connection) {
		return new DPHierarchyAcceptDAOImpl(connection);
	}
	
	@Override
	public DPHierarchyAcceptDAO acceptHierarchy(Connection connection) {
		return new DPHierarchyAcceptDAOImpl(connection);
	}
	
	public HierarchyTransferDao getHierarchyTransferDao(Connection connection) {
		return new HierarchyTransferDaoImpl(connection);
	}
	
//Added by Shilpa khanna for Repair STB
	
	@Override
	public RepairSTBDao getRepairSTBDao(Connection connection) {
		return new RepairSTBDaoImpl(connection);
	}
	
//Added by Shilpa khanna for Sack Distributor
	
	@Override
	public SackDistributorDao getSackDistributorDao(Connection connection) {
		return new SackDistributorDaoImpl(connection);
	}
	public C2SBulkUploadDao getuploadExcelDao(Connection connection) {
		return new C2SBulkUploadDaoImpl(connection);
	}
	
	
//	 **********Added By Shilpa Khanna for STB Status Webservice********//
	@Override
	public STBStatusDao getSTBStatusDao(Connection connection) {
		return new STBStatusDaoImpl(connection);
	}

//	 **********Added By Shilpa Khanna Ends********//




	public DpDcChangeStatusDao getDpDcChangeStatusDao(Connection connection) {
		return new DpDcChangeStatusDaoImpl(connection);
	}


	@Override
	public DropDownUtilityAjaxDao getDropDownUtilityAjaxDaoConnection(Connection connection) {
		// TODO Auto-generated method stub
		return new DropDownUtilityAjaxDaoImpl(connection);
	}

public DPPoAcceptanceBulkDAO getuploadExcel(Connection connection) {
	return new DPPoAcceptanceBulkDAOImpl(connection);
}

//public DPCreateAccountDao getStates(Connection connection) {
//	return new DPCreateAccountDaoImpl(connection);
//}


public NSBulkUploadDao getNsuploadExcelDao(Connection connection) {
	return new NSBulkUploadDaoImpl(connection);
}

public WHdistmappbulkDao getDistWhuploadExcelDao(Connection connection) {
	return new WHdistmappbulkDaoImpl(connection);
}


@Override
public InterSsdTransferAdminDao getInterSSDTransDao(Connection connection) {
	return new InterSsdTransferAdminDaoImpl(connection);
}
@Override
public  DistRecoDao getDistRecoDao(Connection connection) {
	return new DistRecoDaoImpl(connection);
}
@Override
public  PODetailReportDao getPoDetailReportDao(Connection connection) {
	return new PODetailReportDaoImpl(connection);
}

@Override
public CollectionDetailReportDao getCollectionDetailReportDao(Connection connection) {
	// TODO Auto-generated method stub
	return new CollectionDetailReportDaoImpl(connection);
}

@Override
public NonSerializedConsumptionReportDao getNonSerializedConsumptionReportDao(Connection connection) {
	// TODO Auto-generated method stub
	return new NonSerializedConsumptionReportDaoImpl(connection);
}
@Override
public DPInterSSDStockTransferReportDao getDPInterSSDStockTransferReportDao(Connection connection) {
	// TODO Auto-generated method stub
	return new DPInterSSDStockTransferReportDaoImpl(connection);
}
@Override
public AccountDetailReportDao getAccountDetailReportDao(Connection connection) {
	return new AccountDetailReportDaoImpl(connection);
}

//**********Added By Priya for PO Status Update Webservice********//
@Override
public POStatusUpdateDao getPOStatusUpdateDao(Connection connection) {
	return new POStatusUpdateDaoImpl(connection);
}
@Override
public  StockRecoSummReportDao getRecoSummaryReportDao(Connection connection) {
	return new StockRecoSummReportDaoImpl(connection);
}


//**********Added By Shilpa Khanna for Dist STB Mapping Webservice********//
@Override
public DistributorSTBMappingDao getDistSTBMapDao(Connection connection) {
	return new DistributorSTBMappingDaoImpl(connection);
}


// **********Added By Shilpa Khanna Ends********//

//**********Added By Amit Mishra ********//
@Override 
public DPStbInBulkDAO getStbUploadExcel(Connection connection) {
	return new DPStbInBulkDAOImpl(connection);
}

public DPStbInBulkDAO getStbUploadExcelHistory(Connection connection) {
	return new DPStbInBulkDAOImpl(connection);
}


//**********Added By Arti Jain********//


public DPStockLevelBulkUploadDao getStockLevelExcelDao(Connection connection) {
	return new DPStockLevelBulkUploadDaoImpl(connection);
}


public DPDistSecurityDepositLoanDao  getSecurityLoanDepositExcelDao(Connection connection) {
	return new DPDistSecurityDepositLoanDaoImpl(connection);
}

public DPProductSecurityListDao  getProductSecurityListExcelDao(Connection connection) {
	return new DPProductSecurityListDaoImpl(connection);
}

public WarehouseMstFrmBotreeDao  getWarehouseMasterExcelDao(Connection connection) {
	return new WarehouseMstFrmBotreeDaoImpl(connection);
}

public ErrorPODao getErrorSTBListDao(Connection connection) {
	// TODO Auto-generated method stub
	return new ErrorPODaoImpl(connection);
}

public STBFlushOutDao getSTBListDao(Connection connection) {
	// TODO Auto-generated method stub
	return new STBFlushOutDaoImpl(connection);
}


//ended

    @Override	
    public DPReverseChurnCollectionDAO reportChurnDao(Connection connexction)
    {	
    	return new DPReverseChurnCollectionDAOImpl(connexction);
    }
   
    public ChurnDCGenerationDAO getviewDCDetails(Connection connection) {
		// TODO Auto-generated method stub
		return new ChurnDCGenerationDAOImpl(connection) ;
	}
    
    public DpDcChurnStatusDao getDpDcChurnStatusDao(Connection connection) {
		// TODO Auto-generated method stub
    	return new DpDcChurnStatusDaoImpl(connection) ;
	}
  //End

	
	@Override
	public STBFlushOutDao getFlushDPRevSerialNumbersDao(Connection connection) {
		// TODO Auto-generated method stub
		return new STBFlushOutDaoImpl(connection);
	}

	@Override
	public STBFlushOutDao getFlushDPSerialNumbersDao(Connection connection) {
		// TODO Auto-generated method stub
		return new STBFlushOutDaoImpl(connection);
	}

	@Override
	public STBFlushOutDao getuploadRevExcelDao(Connection connection) {
		// TODO Auto-generated method stub
		return new STBFlushOutDaoImpl(connection);
	}

	@Override
	public STBFlushOutDao getuploadFreshExcelDao(Connection connection) {
		// TODO Auto-generated method stub
		return new STBFlushOutDaoImpl(connection);
	}
	
	@Override
	public DPCreateAccountITHelpDao getNewAccountITHelpDao(Connection connection) {
		return new DPCreateAcountITHelpRdbmsDB2(connection);
	}

/*	@Override
	public NSBulkUploadDao getBulkUploadExcelDao(Connection connection) {
		// TODO Auto-generated method stub
		return new NSBulkUploadDaoImpl(connection);
	}*/
	public AvStockUploadDao getuploadFile(Connection connection) {
		return new AvStockUploadDaoImpl(connection);
	}
	
	public DistAvStockUploadDao getuploadFileDist(Connection connection) {
		return new DistAvStockUploadDaoImpl(connection);
	}

	@Override
	public DPDistPinCodeMapDao getDistPincodeMapExcelDao(Connection connection) {
		return new DPDistPinCodeMapDaoImpl(connection);
	}

	@Override
	public AccountManagementActivityReportDao getAccountManagementActivityReportDao(Connection connection) {
		// TODO Auto-generated method stub
		return new AccountManagementActivityReportDaoImpl(connection);
	}

	@Override
	public TransferHierarchyDao getTransHierConnDao(Connection connection) {
		// TODO Auto-generated method stub
		return new TransferHierarchyDaoImpl(connection);
	}
	
	public RetailerLapuDataDao getRetailerLapuDataDao(Connection connection)
	{
		return new RetailerLapuDataDaoImpl(connection);
	}

	public  CannonDPDataDAO getCannonDPDataDAO(Connection connection){
		return new CannonDPDataDAOImpl(connection);
	}
	
	@Override
	public  ConfigurationMasterDao getConfigrationMasterDao(Connection connection) {
		return new ConfigurationMasterDaoRdms(connection);
	}

	@Override
	public RejectedInvDAO getRejectedInvDAO(Connection connection) {
		// TODO Auto-generated method stub
		return new RejectedInvDAOImpl(connection);
	}

	@Override
	public DPInterSSDTransferBulkUploadDao getInterSSDTransferExcelDao(
			Connection connection) {
		// TODO Auto-generated method stub
		
		return new DPInterSSDTransferBulkUploadDaoImpl(connection);
	}

	@Override
	public DPPendingListTransferBulkUploadDao getPendingListTransferExcelDao(
			Connection connection) {
		// TODO Auto-generated method stub
		return new DPPendingListTransferBulkUploadDaoImpl(connection);
	}

	@Override
	public SignInvoiceDao getSignInvoiceDao(Connection connection) {
		// TODO Auto-generated method stub
		return new SignInvoiceDaoImpl(connection);
	}

}



