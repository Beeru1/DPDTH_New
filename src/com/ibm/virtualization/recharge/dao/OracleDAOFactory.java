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

import com.ibm.dp.dao.AvStockUploadDao;
import com.ibm.dp.dao.C2SBulkUploadDao;
import com.ibm.dp.dao.CannonDPDataDAO;
import com.ibm.dp.dao.CollectionDetailReportDao;
import com.ibm.dp.dao.ConfigurationMasterDao;
import com.ibm.dp.dao.DPCreateAccountDao;
import com.ibm.dp.dao.DPCreateAccountITHelpDao;
import com.ibm.dp.dao.DPCreateBeatDao;
import com.ibm.dp.dao.DPDistPinCodeMapDao;
import com.ibm.dp.dao.DPDistSecurityDepositLoanDao;
import com.ibm.dp.dao.DPInterSSDStockTransferReportDao;
import com.ibm.dp.dao.DPInterSSDTransferBulkUploadDao;
import com.ibm.dp.dao.DPPendingListTransferBulkUploadDao;
import com.ibm.dp.dao.DPPoAcceptanceBulkDAO;
import com.ibm.dp.dao.DPProductSecurityListDao;
import com.ibm.dp.dao.DPStbInBulkDAO;
import com.ibm.dp.dao.DPStockLevelBulkUploadDao;
import com.ibm.dp.dao.DPWrongShipmentDao;
import com.ibm.dp.dao.DistAvStockUploadDao;
import com.ibm.dp.dao.DistRecoDao;
import com.ibm.dp.dao.DistributorSTBMappingDao;
import com.ibm.dp.dao.InterSsdTransferAdminDao;
import com.ibm.dp.dao.NSBulkUploadDao;
import com.ibm.dp.dao.NonSerializedConsumptionReportDao;
import com.ibm.dp.dao.PODetailReportDao;
import com.ibm.dp.dao.RetailerLapuDataDao;
import com.ibm.dp.dao.STBFlushOutDao;
import com.ibm.dp.dao.SignInvoiceDao;
import com.ibm.dp.dao.StockDeclarationDao;
import com.ibm.dp.dao.StockRecoSummReportDao;
import com.ibm.dp.dao.StockTransferDao;
import com.ibm.dp.dao.TransferHierarchyDao;
import com.ibm.dp.dao.WHdistmappbulkDao;
import com.ibm.dp.dao.WarehouseMstFrmBotreeDao;
import com.ibm.dp.dao.impl.AccountManagementActivityReportDaoImpl;
import com.ibm.dp.dao.impl.AvStockUploadDaoImpl;
import com.ibm.dp.dao.impl.ConfigurationMasterDaoRdms;
import com.ibm.dp.dao.impl.DPCreateAccountDaoImpl;
import com.ibm.dp.dao.impl.DPCreateAccountITHelpDaoImpl;
import com.ibm.dp.dao.impl.DPDistPinCodeMapDaoImpl;
import com.ibm.dp.dao.impl.DPStbInBulkDAOImpl;
import com.ibm.dp.dao.impl.DPWrongShipmentDaoImpl;
import com.ibm.dp.dao.impl.DistAvStockUploadDaoImpl;
import com.ibm.dp.dao.impl.RetailerLapuDataDaoImpl;
import com.ibm.hbo.dao.HBOProductDAO;
import com.ibm.hbo.dao.HBOStockDAO;
import com.ibm.virtualization.recharge.dao.rdbms.AccountDaoRdbms;
import com.ibm.virtualization.recharge.dao.rdbms.CircleDaoRdbms;
import com.ibm.virtualization.recharge.dao.rdbms.CircleTopupConfigDaoRdbms;
import com.ibm.virtualization.recharge.dao.rdbms.DistributorTransactionDaoRdbms;
import com.ibm.virtualization.recharge.dao.rdbms.GroupDaoRdbms;
import com.ibm.virtualization.recharge.dao.rdbms.INRouterDaoRdbms;
import com.ibm.virtualization.recharge.dao.rdbms.LogTransactionDaoRdbms;
import com.ibm.virtualization.recharge.dao.rdbms.LoginDaoRdbms;
import com.ibm.virtualization.recharge.dao.rdbms.RechargeDaoRdbms;
import com.ibm.virtualization.recharge.dao.rdbms.ReportDaoRdbms;
import com.ibm.virtualization.recharge.dao.rdbms.SequenceDaoRdbms;
import com.ibm.virtualization.recharge.dao.rdbms.SysConfigDaoRdbms;
import com.ibm.virtualization.recharge.dao.rdbms.UserDaoRdbms;
import com.ibm.dp.dao.DPDistSecurityDepositLoanDao;
import com.ibm.dp.dao.DPProductSecurityListDao;
import com.ibm.dp.dao.DPStockLevelBulkUploadDao;

public class OracleDAOFactory extends DAOFactory {

	@Override
	public AccountDao getAccountDao(Connection connection) {

		return new AccountDaoRdbms(connection);
	}
	public com.ibm.dp.dao.DayWiseActivationSummarylReportDao getDayWiseActivationSummarylReportDao(Connection connection) {
		// TODO Auto-generated method stub
		return null;
	}
	@Override
	public ReportDao getReportDao(Connection connection) {

		return new ReportDaoRdbms(connection);
	}

	@Override
	public SequenceDao getSequenceDao(Connection connection) {

		return new SequenceDaoRdbms(connection);
	}

	@Override
	public CircleDao getCircleDao(Connection connection) {

		return new CircleDaoRdbms(connection);
	}

	@Override
	public CircleTopupConfigDao getCircleTopupConfigDao(Connection connection) {

		return new CircleTopupConfigDaoRdbms(connection);
	}

	@Override
	public DistributorTransactionDao getDistributorTransactionDao(
			Connection connection) {

		return new DistributorTransactionDaoRdbms(connection);
	}

	@Override
	public GroupDao getGroupDao(Connection connection) {

		return new GroupDaoRdbms(connection);
	}

	@Override
	public LogTransactionDao getLogTransactionDao(Connection connection) {

		return new LogTransactionDaoRdbms(connection);
	}

	@Override
	public LoginDao getLoginDao(Connection connection) {

		return new LoginDaoRdbms(connection);
	}

	@Override
	public RechargeDao getRechargeDao(Connection connection) {

		return new RechargeDaoRdbms(connection);
	}

	@Override
	public SysConfigDao getSysConfigDao(Connection connection) {

		return new SysConfigDaoRdbms(connection);
	}

	@Override
	public UserDao getUserDao(Connection connection) {

		return new UserDaoRdbms(connection);
	}

	@Override
	public INRouterDao getINRouterDao(Connection connection) {
		return new INRouterDaoRdbms(connection);
	}

//Create Account by Anju	
	
	public DPCreateAccountDao getNewAccountDao(Connection connection) {
		return new DPCreateAccountDaoImpl(connection);
	}
// Create Beat
	
	@Override
	public DPCreateBeatDao createBeat(Connection connection) {
		// TODO Auto-generated method stub
		return null;
	}	

	//vivek
	@Override
	public DPMasterDAO getDPMasterDAO(Connection connection) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public DPMasterDAO getBusinessCategoryDao(Connection connection) {
		// TODO Auto-generated method stub
		return null;
	}
	@Override
	public DPMasterDAO select(Connection connection) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public DPMasterDAO getCircleListDao(Connection connection) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public DPMasterDAO update(Connection connection) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public DPMasterDAO getDataForEdit(Connection connection) {
		// TODO Auto-generated method stub
		return null;
	}
	@Override
	public MinReorderDao getAssignedorderDao(Connection connection) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public MinReorderDao check(Connection connection) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public MinReorderDao updateAssign(Connection connection) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public DPMasterDAO warranty(Connection connection) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public DPMasterDAO getProductListwarrantyDao(Connection connection) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public DPMasterDAO select1(Connection connection) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public DPMasterDAO getWarrantyDao(Connection connection) {
		// TODO Auto-generated method stub
		return null;
	}


	@Override
	public DPMasterDAO getNewProductDAO(Connection connection) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public DPMasterDAO insertWebservice(Connection connection) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public DPMasterDAO getUpdateProductDAO(Connection connection) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public DPMasterDAO selectProduct(Connection connection) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public MinReorderDao getDistributorDao(Connection connection) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public HBOProductDAO getProductDao(Connection connection) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public HBOStockDAO getStockDao(Connection connection) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public DPMasterDAO getWarranty(Connection connection) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public DPMasterDAO selectwarranty(Connection connection) {
		// TODO Auto-generated method stub
		return null;
	}
	
	public  DPMasterDAO getCardGroupDao(Connection connection){
		return null;
	}
	
	@Override
	public DPWrongShipmentDao getWrongShipmentDAO(Connection connection)
	{
		return new DPWrongShipmentDaoImpl(connection);
	}
	
	//Added by Mohammad Aslam
	@Override
	public StockDeclarationDao getStockDeclarationDao(Connection connection) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public StockTransferDao getStockTransferDao(Connection connection) {
		// TODO Auto-generated method stub
		return null;
	}
	@Override
	public C2SBulkUploadDao getuploadExcelDao(Connection connection) {
		// TODO Auto-generated method stub
		return null;
	}
	public DPPoAcceptanceBulkDAO getuploadExcel(Connection connection) {
		// TODO Auto-generated method stub
		return null;
	}
	
	@Override
	public NSBulkUploadDao getNsuploadExcelDao(Connection connection) {
		// TODO Auto-generated method stub
		return null;
	}
	
	@Override
	public WHdistmappbulkDao getDistWhuploadExcelDao(Connection connection) {
		// TODO Auto-generated method stub
		return null;
	}
	
	
	
	
	@Override
	public InterSsdTransferAdminDao getInterSSDTransDao(Connection connection) {
		// TODO Auto-generated method stub
		return null;
	}
	
	@Override
	public DistRecoDao getDistRecoDao(Connection connection) {
		// TODO Auto-generated method stub
		return null;
	}
	@Override
	public PODetailReportDao getPoDetailReportDao(Connection connection) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public CollectionDetailReportDao getCollectionDetailReportDao(Connection connection) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public NonSerializedConsumptionReportDao getNonSerializedConsumptionReportDao(Connection connection) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public DPInterSSDStockTransferReportDao getDPInterSSDStockTransferReportDao(Connection connection) {
		// TODO Auto-generated method stub
		return null;
	}
	@Override
	public StockRecoSummReportDao getRecoSummaryReportDao(Connection connection) {
		// TODO Auto-generated method stub
		return null;
	}
	@Override
	public DistributorSTBMappingDao getDistSTBMapDao(Connection connection) {
		// TODO Auto-generated method stub
		return null;
	}
	@Override
	public com.ibm.dp.dao.AccountDetailReportDao  getAccountDetailReportDao(Connection connection) {
		// TODO Auto-generated method stub
		return null;
	}
	@Override
	public com.ibm.dp.dao.CircleActivationSummarylReportDao  getCircleActivationSummarylReportDao(Connection connection) {
		// TODO Auto-generated method stub
		return null;
	}
	@Override
	public DPStockLevelBulkUploadDao getStockLevelExcelDao(Connection connection) {
		return null;
	}
	@Override
	public DPProductSecurityListDao  getProductSecurityListExcelDao(Connection connection) {
		return null;
	}
	@Override
	public DPDistSecurityDepositLoanDao  getSecurityLoanDepositExcelDao(Connection connection) {
		return null;
	}
	@Override
	public WarehouseMstFrmBotreeDao  getWarehouseMasterExcelDao(Connection connection) {
		 return null;
	}



	@Override
	public STBFlushOutDao getFlushDPRevSerialNumbersDao(Connection connection) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public STBFlushOutDao getFlushDPSerialNumbersDao(Connection connection) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public STBFlushOutDao getuploadRevExcelDao(Connection connection) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public STBFlushOutDao getuploadFreshExcelDao(Connection connection) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	
	public DPCreateAccountITHelpDao getNewAccountITHelpDao(Connection connection) {
		return new DPCreateAccountITHelpDaoImpl(connection);
	}

	@Override
	public TransferHierarchyDao getTransHierConnDao(Connection connection) {
		// TODO Auto-generated method stub
		return null;
	}

	public AccountManagementActivityReportDaoImpl getAccountManagementActivityReportDao(Connection connection) 
	{
		return new AccountManagementActivityReportDaoImpl(connection);
	}

	@Override
	public DPDistPinCodeMapDao getDistPincodeMapExcelDao(Connection connection) {
		// TODO Auto-generated method stub
		return new DPDistPinCodeMapDaoImpl(connection);
	}
	@Override
	public DPStbInBulkDAO getStbUploadExcelHistory(Connection connection) {
		// TODO Auto-generated method stub
		return new DPStbInBulkDAOImpl(connection);
	}
	@Override
	public AvStockUploadDao getuploadFile(Connection connection) {
		// TODO Auto-generated method stub
		return new AvStockUploadDaoImpl(connection);
	}
	@Override
	public DistAvStockUploadDao getuploadFileDist(Connection connection) {
		// TODO Auto-generated method stub
		return new DistAvStockUploadDaoImpl(connection);
	}

/*	@Override
	public NSBulkUploadDao getBulkUploadExcelDao(Connection connection) {
		// TODO Auto-generated method stub
		return null;
	}*/
	
	public RetailerLapuDataDao getRetailerLapuDataDao(Connection connection)
	{
		return null;
	}

	public  CannonDPDataDAO getCannonDPDataDAO(Connection connection){
		return null;
	}
	@Override
	public RejectedInvDAO getRejectedInvDAO(Connection connection) {
		// TODO Auto-generated method stub
		return null;
	}
	

	public DPPendingListTransferBulkUploadDao getPendingListTransferExcelDao(Connection connection)
	{
		return null;
	}
	
	public DPInterSSDTransferBulkUploadDao getInterSSDTransferExcelDao(Connection connection)
	{
		return null;
	}
	
	public  ConfigurationMasterDao getConfigrationMasterDao(Connection connection) {
		return null;
	}
	@Override
	public SignInvoiceDao getSignInvoiceDao(Connection connection) {
		// TODO Auto-generated method stub
		return null;
	}
}
