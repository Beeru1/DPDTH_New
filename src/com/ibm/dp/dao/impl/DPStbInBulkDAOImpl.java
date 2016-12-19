package com.ibm.dp.dao.impl;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.StringTokenizer;

import org.apache.log4j.Logger;
import org.apache.struts.upload.FormFile;

import com.ibm.dp.common.Constants;
import com.ibm.dp.dao.DPStbInBulkDAO;
import com.ibm.dp.dto.DPSTBHistDTO;
import com.ibm.dp.dto.DPStbInBulkDTO;
import com.ibm.utilities.Utility;
import com.ibm.utilities.ValidatorUtility;
import com.ibm.virtualization.recharge.common.ResourceReader;
import com.ibm.virtualization.recharge.dao.rdbms.BaseDaoRdbms;
import com.ibm.virtualization.recharge.db.DBConnectionManager;
import com.ibm.virtualization.recharge.exception.DAOException;

public class DPStbInBulkDAOImpl extends BaseDaoRdbms implements DPStbInBulkDAO {

	private static Logger logger = Logger.getLogger(DPStbInBulkDAOImpl.class
			.getName());

	public DPStbInBulkDAOImpl(Connection connection) {
		super(connection);
	}

	public List uploadExcel(FormFile myfile) throws DAOException {
		String str = "";
		List list = new ArrayList();
		List Error_list = new ArrayList();
		DPStbInBulkDTO dpStbInBulkDTO = new DPStbInBulkDTO();
		boolean validateFlag = true;
		String data = "";
		ArrayList checkDuplicate = new ArrayList();
		String stb_no = "";
		try {
			boolean isEmpty = true;
			InputStream stream = myfile.getInputStream();
			ByteArrayOutputStream baos = new ByteArrayOutputStream();
			int size = (int) myfile.getFileSize();
			//logger.info("****************File size**********************"
			//		+ size);
			byte buffer[] = new byte[size];
			int bytesRead = 0;

			// read the input
			while ((bytesRead = stream.read(buffer, 0, size)) != -1) {
				baos.write(buffer, 0, bytesRead);
			}
			data = new String(baos.toByteArray());
			//logger.info("****************File data**********************"
				//	+ data);

			// replace ,, with , ,
			String newdata = data.replace(",", ",");
			//logger.info("****************new  data**********************"
			//		+ newdata);

			StringTokenizer st = new StringTokenizer(newdata, ",");
			while (st.hasMoreTokens()) {
				isEmpty = false;
				dpStbInBulkDTO = new DPStbInBulkDTO();

				try {
					// stb_no = st.nextToken(",\n\r").toString().trim();
					stb_no = st.nextToken().toString().trim();
					
				} catch (Exception e) {
					logger
							.info("****************Empty File Uploaded**********************");
					dpStbInBulkDTO.setErr_msg("Empty File Uploaded");
					dpStbInBulkDTO.setSerial_no(" ");
					Error_list.add(dpStbInBulkDTO);
					list = Error_list;
					return list;
				}

				boolean validate = validateCell(stb_no);
				if (stb_no != null && "".equals(stb_no.trim())) {
					
					//logger
						//	.info("****************black spaces*********************");
					dpStbInBulkDTO.setErr_msg("blank spaces ");
					dpStbInBulkDTO.setSerial_no(" ");
					Error_list.add(dpStbInBulkDTO);
					validateFlag = false;
					//list = Error_list;

				} else if (checkDuplicate.contains(stb_no)) {
					//logger
						//	.info("****************Duplicate Serial Number**********************");
					dpStbInBulkDTO.setErr_msg("Duplicate Serial Number");
					dpStbInBulkDTO.setSerial_no(stb_no + " ");
					Error_list.add(dpStbInBulkDTO);
					validateFlag = false;
				} else if (checkIsAlfabet(stb_no)) {
					//logger
						//	.info("****************Invalid Serial Number.It should only be 11 digit numeric value **********************");
					dpStbInBulkDTO
							.setErr_msg("Invalid Serial Number.It should be only numeric value .");
					dpStbInBulkDTO.setSerial_no(stb_no + " ");
					Error_list.add(dpStbInBulkDTO);
					validateFlag = false;

				} else if (ValidatorUtility.isAlfabetSpace(stb_no)) {
					//logger
					//		.info("****************Invalid Serial Number.It should only be 11 digit numeric value **********************");
					dpStbInBulkDTO
							.setErr_msg("Invalid Serial Number.It should be only numeric value .");
					dpStbInBulkDTO.setSerial_no(stb_no + " ");
					Error_list.add(dpStbInBulkDTO);
					validateFlag = false;

				} else if (ValidatorUtility.isAlphaNumeric(stb_no)) {
					//logger
						//	.info("****************Invalid Serial Number.It should only be 11 digit numeric value **********************");
					dpStbInBulkDTO
							.setErr_msg("Invalid Serial Number.It should be only numeric value .");
					dpStbInBulkDTO.setSerial_no(stb_no + " ");
					Error_list.add(dpStbInBulkDTO);
					validateFlag = false;

				} else if (!ValidatorUtility.isLegal(stb_no)) {
					//logger
						//	.info("****************Invalid Serial Number.It should only be 11 digit numeric value **********************");
					dpStbInBulkDTO
							.setErr_msg("Invalid Serial Number.It should only be 11 digit numeric value.");
					dpStbInBulkDTO.setSerial_no(stb_no + " ");
					Error_list.add(dpStbInBulkDTO);
					validateFlag = false;

				}

				else if (checkjunk(stb_no)) {
					//logger
						//	.info("****************Invalid Serial Number.It should be only numeric value**********************");
					dpStbInBulkDTO
							.setErr_msg("Invalid Serial Number.It should be only numeric value .");
					dpStbInBulkDTO.setSerial_no(stb_no + " ");
					Error_list.add(dpStbInBulkDTO);
					validateFlag = false;

				} else if (!validate) {
					//logger
						//	.info("****************Invalid Serial Number.It should be 11 digit number**********************");
					dpStbInBulkDTO
							.setErr_msg("Invalid Serial Number.It should be 11 digit number.");
					dpStbInBulkDTO.setSerial_no(stb_no + " ");
					Error_list.add(dpStbInBulkDTO);
					validateFlag = false;
				} else if (!validateCellForZero(stb_no)) {
					//logger
						//	.info("****************Invalid Serial Number.It should start with 0 **********************");
					dpStbInBulkDTO.setSerial_no(stb_no + " ");
					dpStbInBulkDTO
							.setErr_msg("Invalid Serial Number.It should start with 0 ");
					Error_list.add(dpStbInBulkDTO);
					validateFlag = false;
				} else {
					//logger
						//	.info("****************SUCCESS**********************");
					dpStbInBulkDTO.setErr_msg("SUCCESS");
					checkDuplicate.add(stb_no);
					dpStbInBulkDTO.setSerial_no(stb_no + " ");
					Error_list.add(dpStbInBulkDTO);
				}
			}

			if (isEmpty) {
				//logger
					//	.info("****************Empty File Uploaded**********************");
				dpStbInBulkDTO.setErr_msg("Empty File Uploaded");
				dpStbInBulkDTO.setSerial_no(" ");
				Error_list.add(dpStbInBulkDTO);
				list = Error_list;
			} else {
				if (validateFlag) {
					dpStbInBulkDTO.setList_srno(checkDuplicate);
					list.add(dpStbInBulkDTO);
				} else {
					list = Error_list;
				}
			}
		} catch (Exception e) {
			logger.info(e);
		}
		return list;
	}
	
	public List uploadExcelHistory(FormFile myfile) throws DAOException {
		String str = "";
		List list = new ArrayList();
		List Error_list = new ArrayList();
		DPSTBHistDTO dpSTBHistDTO = new DPSTBHistDTO();
		boolean validateFlag = true;
		String data = "";
		ArrayList checkDuplicate = new ArrayList();
		String stb_no = "";
		try {
			boolean isEmpty = true;
			InputStream stream = myfile.getInputStream();
			ByteArrayOutputStream baos = new ByteArrayOutputStream();
			int size = (int) myfile.getFileSize();
			//logger.info("****************File size**********************"
			//		+ size);
			byte buffer[] = new byte[size];
			int bytesRead = 0;

			// read the input
			while ((bytesRead = stream.read(buffer, 0, size)) != -1) {
				baos.write(buffer, 0, bytesRead);
			}
			data = new String(baos.toByteArray());
			//logger.info("****************File data**********************"
				//	+ data);

			// replace ,, with , ,
			String newdata = data.replace(",", ",");
			//logger.info("****************new  data**********************"
			//		+ newdata);

			StringTokenizer st = new StringTokenizer(newdata, ",");
			while (st.hasMoreTokens()) {
				isEmpty = false;
				dpSTBHistDTO = new DPSTBHistDTO();

				try {
					// stb_no = st.nextToken(",\n\r").toString().trim();
					stb_no = st.nextToken().toString().trim();
					
				} catch (Exception e) {
					logger
							.info("****************Empty File Uploaded**********************");
					dpSTBHistDTO.setErr_msg("Empty File Uploaded");
					dpSTBHistDTO.setSerial_no(" ");
					Error_list.add(dpSTBHistDTO);
					list = Error_list;
					return list;
				}

				boolean validate = validateCell(stb_no);
				if (stb_no != null && "".equals(stb_no.trim())) {
					
					//logger
						//	.info("****************black spaces*********************");
					dpSTBHistDTO.setErr_msg("blank spaces ");
					dpSTBHistDTO.setSerial_no(" ");
					Error_list.add(dpSTBHistDTO);
					validateFlag = false;
					//list = Error_list;

				} else if (checkDuplicate.contains(stb_no)) {
					//logger
						//	.info("****************Duplicate Serial Number**********************");
					dpSTBHistDTO.setErr_msg("Duplicate Serial Number");
					dpSTBHistDTO.setSerial_no(stb_no + " ");
					Error_list.add(dpSTBHistDTO);
					validateFlag = false;
				} else if (checkIsAlfabet(stb_no)) {
					//logger
						//	.info("****************Invalid Serial Number.It should only be 11 digit numeric value **********************");
					dpSTBHistDTO
							.setErr_msg("Invalid Serial Number.It should be only numeric value .");
					dpSTBHistDTO.setSerial_no(stb_no + " ");
					Error_list.add(dpSTBHistDTO);
					validateFlag = false;

				} else if (ValidatorUtility.isAlfabetSpace(stb_no)) {
					//logger
					//		.info("****************Invalid Serial Number.It should only be 11 digit numeric value **********************");
					dpSTBHistDTO
							.setErr_msg("Invalid Serial Number.It should be only numeric value .");
					dpSTBHistDTO.setSerial_no(stb_no + " ");
					Error_list.add(dpSTBHistDTO);
					validateFlag = false;

				} else if (ValidatorUtility.isAlphaNumeric(stb_no)) {
					//logger
						//	.info("****************Invalid Serial Number.It should only be 11 digit numeric value **********************");
					dpSTBHistDTO
							.setErr_msg("Invalid Serial Number.It should be only numeric value .");
					dpSTBHistDTO.setSerial_no(stb_no + " ");
					Error_list.add(dpSTBHistDTO);
					validateFlag = false;

				} else if (!ValidatorUtility.isLegal(stb_no)) {
					//logger
						//	.info("****************Invalid Serial Number.It should only be 11 digit numeric value **********************");
					dpSTBHistDTO
							.setErr_msg("Invalid Serial Number.It should only be 11 digit numeric value.");
					dpSTBHistDTO.setSerial_no(stb_no + " ");
					Error_list.add(dpSTBHistDTO);
					validateFlag = false;

				}

				else if (checkjunk(stb_no)) {
					//logger
						//	.info("****************Invalid Serial Number.It should be only numeric value**********************");
					dpSTBHistDTO
							.setErr_msg("Invalid Serial Number.It should be only numeric value .");
					dpSTBHistDTO.setSerial_no(stb_no + " ");
					Error_list.add(dpSTBHistDTO);
					validateFlag = false;

				} else if (!validate) {
					//logger
						//	.info("****************Invalid Serial Number.It should be 11 digit number**********************");
					dpSTBHistDTO
							.setErr_msg("Invalid Serial Number.It should be 11 digit number.");
					dpSTBHistDTO.setSerial_no(stb_no + " ");
					Error_list.add(dpSTBHistDTO);
					validateFlag = false;
				} 
				//Added by sugandha to check whether STB exists in DP or not
				else if (!validateStb(stb_no))
				{

					dpSTBHistDTO.setErr_msg("This Serial Number does not exist in DP.");
					dpSTBHistDTO.setSerial_no(stb_no + " ");
					Error_list.add(dpSTBHistDTO);
					validateFlag = false;
				
				}
				else if (!validateCellForZero(stb_no)) {
					//logger
						//	.info("****************Invalid Serial Number.It should start with 0 **********************");
					dpSTBHistDTO.setSerial_no(stb_no + " ");
					dpSTBHistDTO
							.setErr_msg("Invalid Serial Number.It should start with 0 ");
					Error_list.add(dpSTBHistDTO);
					validateFlag = false;
				} else {
					//logger
						//	.info("****************SUCCESS**********************");
					dpSTBHistDTO.setErr_msg("SUCCESS");
					checkDuplicate.add(stb_no);
					dpSTBHistDTO.setSerial_no(stb_no + " ");
					Error_list.add(dpSTBHistDTO);
				}
			}

			if (isEmpty) {
				//logger
					//	.info("****************Empty File Uploaded**********************");
				dpSTBHistDTO.setErr_msg("Empty File Uploaded");
				dpSTBHistDTO.setSerial_no(" ");
				Error_list.add(dpSTBHistDTO);
				list = Error_list;
			} else {
				if (validateFlag) {
					dpSTBHistDTO.setList_srno(checkDuplicate);
					list.add(dpSTBHistDTO);
				} else {
					list = Error_list;
				}
			}
		} catch (Exception e) {
			logger.info(e);
		}
		return list;
	}

	public boolean validateCell(String str) {
		if (str.length() != Constants.C2S_BULK_UPLOAD_NO_LENGTH) {
			return false;
		} else if (!ValidatorUtility.isValidNumber(str)) {
			return false;
		}
		return true;
	}

	public boolean checkIsAlfabet(String str) {
		if (ValidatorUtility.isAlfabet(str)) {
			return true;
		}
		return false;
	}

	public boolean checkjunk(String str) {
		if (!ValidatorUtility.isJunkFree(str)) {
			return true;
		}
		return false;
	}

	public boolean validateCellForZero(String str) {
		if (str.startsWith("0"))
			return true;
		else
			return false;
	}
	//Added by sugandha to validate stb exist in dp whether or not on 20-th-August-2013 for BFR-52 DP-PHASE-3 bug fixing
	
	public boolean validateStb(String stb_no) 
	{PreparedStatement pstmt = null;
	ResultSet rs = null;
	boolean flag = false;
	try{
		logger.info("inside --- validateStb== "+stb_no);
		pstmt = connection.prepareStatement("SELECT count(*) FROM DP_STOCK_INVENTORY WHERE SERIAL_NO=?");
		pstmt.setString(1,stb_no);
		rs =pstmt.executeQuery();
		if(rs.next())
		{
			flag =  true;
		}
		else
		{
			flag =  false;
		}
		
	}catch (Exception e) 
	{
		e.printStackTrace();
		logger.info("Inside main Exception of validateStb method--"+e.getMessage());
	}
	return flag;
	}
	public ArrayList getSerialNoList(DPStbInBulkDTO dpStbInBulkDTO)
			throws DAOException {

		//logger.info("****************getSerialNoList**********************");
		DateFormat formatter = new SimpleDateFormat("dd-MMM-yyyy");
		DateFormat formatters = new SimpleDateFormat("yyyy-MM-dd");
		Connection con = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		ArrayList serialNosList = new ArrayList();
		ArrayList assignSerialNosList = new ArrayList();
		ArrayList reserveSerialNosList = new ArrayList();
		ArrayList reserveSerialNosHistList = new ArrayList();
		ArrayList churnSerialNoList = new ArrayList();
		ArrayList churnSerialNoListHist = new ArrayList();
     	Map<String, DPStbInBulkDTO> dpStockInv = new HashMap<String, DPStbInBulkDTO>();
		Map<String, DPStbInBulkDTO> assignInv = new HashMap<String, DPStbInBulkDTO>();
		Map<String, DPStbInBulkDTO> reserveInv = new HashMap<String, DPStbInBulkDTO>();
		Map<String, DPStbInBulkDTO> reserveInvHist = new HashMap<String, DPStbInBulkDTO>();
		Map<String, DPStbInBulkDTO> churnInv = new HashMap<String, DPStbInBulkDTO>();
		Map<String, DPStbInBulkDTO> churnInvHist = new HashMap<String, DPStbInBulkDTO>();
/*
	//	Map<String, DPStbInBulkDTO> forwardInv = new HashMap<String, DPStbInBulkDTO>();
	//	Map<String, DPStbInBulkDTO> reverseInv = new HashMap<String, DPStbInBulkDTO>();
		Map<String, DPStbInBulkDTO> dpStockInv = new HashMap<String, DPStbInBulkDTO>();
		Map<String, DPStbInBulkDTO> assignInv = new HashMap<String, DPStbInBulkDTO>();
		Map<String, DPStbInBulkDTO> reserveInv = new HashMap<String, DPStbInBulkDTO>();
		Map<String, DPStbInBulkDTO> churnInv = new HashMap<String, DPStbInBulkDTO>();
*/
		PreparedStatement prepareStatement = null;
		ResultSet resultSet = null;
		DPStbInBulkDTO dpStbInBulkStockInv = null;
		ArrayList FinalListOfStb = new ArrayList();
		// Connection connection = null;

		String stbList = dpStbInBulkDTO.getSerial_no();
		//logger.info("****************stbList********************** " + stbList);
/*		
		//Search STB from Forward. Prepare HashMap
		dpStockInv = getStockInventoryDetails(dpStbInBulkDTO);
		assignInv = getAssignSerialNoList(dpStbInBulkDTO);
		
		//Search STB from Reverse. Prepare HashMap 
		reserveInv = getReserveSerialNoList(dpStbInBulkDTO);
		churnInv = getChurnSerialNoList(dpStbInBulkDTO);
		
		/**Take the original list of STBs and run for loop for each STB
		
			For each STB, get forward and reverse DTO from Hashmaps.
			
			Case 1. No DTO returned - "Not in DP"
			Case 2. Only forward DTO returned - put forward details in final DTO
			Case 3. Only Reverse DTO returned - Put reverse details in final DTO, with non-serialized for forward
			Case 4. Both Forward and reverse are returned and forward detail is latest
					-	put forward details in final DTO
					
			Case 5. Both Forward and reverse are returned and reverse detail is latest-
				Put both forward and revers in final DTO
		
		* /
		
		DPStbInBulkDTO dpStockInvDto = new DPStbInBulkDTO();
		DPStbInBulkDTO assignInvDto = new DPStbInBulkDTO();
		DPStbInBulkDTO reserveInvDto =  new DPStbInBulkDTO();
		DPStbInBulkDTO churnInvDto =  new DPStbInBulkDTO();
		ArrayList<DPStbInBulkDTO> finalSearchResult = new ArrayList();
	
		String serialNo="";
		for(int i=0;i<dpStbInBulkDTO.getList_srno().size();i++)
		{
			serialNo=(String)dpStbInBulkDTO.getList_srno().get(i);
			if(dpStockInv.get(serialNo)!=null)
			{
				//STB found in stock inventory
				dpStockInvDto=(DPStbInBulkDTO)dpStockInv.get(serialNo);
				//Set Status of Column 3
				continue;
			}
			else
			{
				if(assignInv.get(serialNo)!=null)
					assignInvDto=(DPStbInBulkDTO)assignInv.get(serialNo);

				if(reserveInv.get(serialNo)!=null)
					reserveInvDto=(DPStbInBulkDTO)reserveInv.get(serialNo);

				if(assignInv.get(serialNo)!=null)
					churnInvDto=(DPStbInBulkDTO)churnInv.get(serialNo);
				
			}
		}
		
	*/	
		try 
		{
			//connection = DBConnectionManager.getDBConnection();

			//logger.info("****************getFreshStockList********************** ");
			dpStockInv = getStockInventoryList(dpStbInBulkDTO);
			//logger.info("****************getAssignSerialNoList********************** ");
			assignSerialNosList = getAssignSerialNoList(dpStbInBulkDTO);
			//logger.info("****************getReserveSerialNoList********************** ");
			reserveSerialNosList = getReserveSerialNoList(dpStbInBulkDTO);
			//logger.info("****************getReserveSerialNoListHist********************** ");
			reserveSerialNosHistList = getReserveSerialNoListHist(dpStbInBulkDTO);

			//logger.info("****************getChurnSerialNoList********************** ");
			churnSerialNoList = getChurnSerialNoList(dpStbInBulkDTO);
			//logger.info("****************getChurnSerialNoListHist********************** ");
			churnSerialNoListHist = getChurnSerialNoListHist(dpStbInBulkDTO);

			//logger.info("****************assignInv MAP********************** ");

			try {
				Iterator<DPStbInBulkDTO> itr1 = assignSerialNosList.iterator();

				while (itr1.hasNext()) {
					DPStbInBulkDTO objAssDTO = (DPStbInBulkDTO) itr1.next();
					assignInv.put(objAssDTO.getSerial_no(), objAssDTO);
					
				}
			} catch (Exception e) {
				e.printStackTrace();
				logger.error("Exception occured while inserting in assignInv MAP."
								+ "Exception Message: " + e.getMessage());
				try
				{
					String err = ResourceReader.getCoreResourceBundleValue(Constants.STB_Search_Bulk_Critical);
					logger.info(err + "::" +Utility.getCurrentDate());
				}
				catch(Exception ex)
				{
					ex.printStackTrace();
				}
			}

			//logger.info("****************reserveInv MAP********************** ");

			try {
				Iterator<DPStbInBulkDTO> itr2 = reserveSerialNosList.iterator();

				while (itr2.hasNext()) {
					DPStbInBulkDTO objRevDTO = (DPStbInBulkDTO) itr2.next();
					reserveInv.put(objRevDTO.getSerial_no(), objRevDTO);
				}
			} catch (Exception e) {
				e.printStackTrace();
				logger.error("Exception occured while inserting in reserveInv MAP."
								+ "Exception Message: " + e.getMessage());
				try
				{
					String err = ResourceReader.getCoreResourceBundleValue(Constants.STB_Search_Bulk_Critical);
					logger.info(err + "::" +Utility.getCurrentDate());
				}
				catch(Exception ex)
				{
					ex.printStackTrace();
				}

			}
			//logger.info("****************reserveInvHist MAP********************** ");

			try {
				Iterator<DPStbInBulkDTO> itr3 = reserveSerialNosHistList.iterator();

				while (itr3.hasNext()) {
					DPStbInBulkDTO objRevHistDTO = (DPStbInBulkDTO) itr3.next();
					reserveInvHist.put(objRevHistDTO.getSerial_no(),objRevHistDTO);
				}
			} catch (Exception e) {
				logger.error("Exception occured while inserting in reserveInvHist MAP. Exception Message: " + e.getMessage());
				try
				{
					String err = ResourceReader.getCoreResourceBundleValue(Constants.STB_Search_Bulk_Critical);
					logger.info(err + "::" +Utility.getCurrentDate());
				}
				catch(Exception ex)
				{
					ex.printStackTrace();
				}
			}

			//logger.info("****************churnInv MAP********************** ");

			try {
				Iterator<DPStbInBulkDTO> itr4 = churnSerialNoList.iterator();

				while (itr4.hasNext()) {
					DPStbInBulkDTO objChurnDTO = (DPStbInBulkDTO) itr4.next();
					churnInv.put(objChurnDTO.getSerial_no(), objChurnDTO);
				}
			} catch (Exception e) {
				logger.error("Exception occured while inserting in churnInv MAP."
								+ "Exception Message: " + e.getMessage());
				try
				{
					String err = ResourceReader.getCoreResourceBundleValue(Constants.STB_Search_Bulk_Critical);
					logger.info(err + "::" +Utility.getCurrentDate());
				}
				catch(Exception ex)
				{
					ex.printStackTrace();
				}
			}

			//logger.info("****************churnInvHist MAP********************** ");

			try {
				Iterator<DPStbInBulkDTO> itr5 = churnSerialNoListHist.iterator();

				while (itr5.hasNext()) {
					DPStbInBulkDTO objChurnHistDTO = (DPStbInBulkDTO) itr5.next();
					churnInvHist.put(objChurnHistDTO.getSerial_no(),objChurnHistDTO);
				}
			} catch (Exception e) {
				logger.error("Exception occured while inserting in churnInvHist MAP."
								+ "Exception Message: " + e.getMessage());
				try
				{
					String err = ResourceReader.getCoreResourceBundleValue(Constants.STB_Search_Bulk_Critical);
					logger.info(err + "::" +Utility.getCurrentDate());
				}
				catch(Exception ex)
				{
					ex.printStackTrace();
				}
			}

			//logger.info("****************Record creation**********************");

			String stbNoList[] = new String[200];
			stbList = stbList.replace("'", "");
			stbNoList = stbList.split(",");
//			Arrays.sort(stbNoList); //removed Sorting to get the output result in same order as was in the input file.

			/*
			 * Date inv_change_date = null; Date assign_date = null; Date
			 * inv_change_date_hist = null;
			 */

			for (int i = 0; i < stbNoList.length; i++) 
			{
				Date inv_change_date = null;
				Date inv_change_date_hist = null;
				Date assign_date = null;
				Date churn_inv_change_date = null;
				Date rfdDCDate=null;
				String stbNo = stbNoList[i];
				boolean stbExist = false;
				boolean reverseStockFound=false;
				boolean churnStockFound=false;
				boolean rfdStock=false;
				//logger.info("****************stbNo**********************"+ stbNo);
				
					//logger.info("****************record creation**********************");
					DPStbInBulkDTO objStockDTO = null;
					DPStbInBulkDTO objAssignDTO = null;
					DPStbInBulkDTO objRevDTO = null;
					DPStbInBulkDTO objRevHistDTO = null;
					DPStbInBulkDTO objChurnDTO = null;
					DPStbInBulkDTO objChurnHistDTO = null;

					//First Checking if stock is available in Stock Inventory. If so, take the data and add in final DTO
					if (!dpStockInv.isEmpty() && dpStockInv.containsKey(stbNo)) {
						//logger.info("****************stbNo exists in dpStockInv**********************");
						objStockDTO = dpStockInv.get(stbNo);
						FinalListOfStb.add(objStockDTO);
						stbExist = true;
						continue;
					}
					//logger.info("****************stbNo not exists in dpStockInv**********************");
					
					//If stock is not available in Stock Inventory, check if Stock available in Assigned table take it as forward stock 
					//and take the latest of all reverse Stocks if available.
					
					if (!stbExist) 
					{
						//logger.info("****************stbNo not exists in dpStockIn**********************");
						if (!assignInv.isEmpty() && assignInv.containsKey(stbNo)) 
						{
							//logger.info("****************stbNo exists in assignInv**********************");
							objAssignDTO = assignInv.get(stbNo);
							if (objAssignDTO != null) {
								String str_assign_date = objAssignDTO.getActivationDate();
								//logger.info("****************str_assign_date********************** "+str_assign_date);
								if (str_assign_date != null && !str_assign_date.equals("")) {
									assign_date = (Date) formatter.parse(str_assign_date);
									//logger.info("****************assign_date**********************"+ assign_date);
								//Shilpa
								//If the STB is not Activated (Has been flushed out or sent back to WH as RFD, then set Activation date as null.
								/*if( !objAssignDTO.getStatus().equals("Not In Stock -- Assigned"))
									objAssignDTO.setActivationDate(null);*/
									
								if(objAssignDTO.getStatus().equals("Not In Stock -- Flushed out by DP") || objAssignDTO.getStatus().equals("Not In Stock -- Flushed out by Botree") || objAssignDTO.getStatus().equals("Not In Stock -- Ok Stock Returned To WH"))
									objAssignDTO.setActivationDate(null);
								}
							}
						}
		
						//Check Reverse Stock
						if (!reserveInv.isEmpty() && reserveInv.containsKey(stbNo)) 
						{
								//logger.info("****************stbNo exists in reserveInv**********************"+stbNo);
								objRevDTO = reserveInv.get(stbNo);
								if (objRevDTO != null) {
									String str_inv_change_date = objRevDTO.getInv_change_date();
									String str_collection_date = objRevDTO.getCollection_date();
	
									if(objRevDTO.getReverseDCType().equals("FRESH"))
									{
						
										//logger.info("****************stbNo exists in RFD**********************"+ objRevDTO.getRev_dc_date());
																				rfdStock=true;
										String rfdDCDateStr = objRevDTO.getRev_dc_date();
										if (rfdDCDateStr != null && !rfdDCDateStr.equals("")) {
											SimpleDateFormat sdf = new SimpleDateFormat("dd-MMM-yyyy");
											rfdDCDate = (Date) sdf.parse(rfdDCDateStr);
											//rfdDCDate = (Date) formatters.parse(rfdDCDateStr);
											inv_change_date=rfdDCDate;
										}
									}
									else
									{
										if (str_inv_change_date != null && !str_inv_change_date.equals("")) {
											inv_change_date = (Date) formatters.parse(str_inv_change_date);
										} else if (str_collection_date != null && !str_collection_date.equals("")) {
											inv_change_date = (Date) formatters.parse(str_collection_date);
										} else {
											inv_change_date = null;
	
										}
									}
									reverseStockFound=true;
								}
						}
								//logger.info("****************stbNo not exists in dpStockIn**********************");
						//If Stock not present in Reverse Current Tables, then check in history		
						if ( (rfdStock || !reverseStockFound)  && !reserveInvHist.isEmpty() && reserveInvHist.containsKey(stbNo)) 
						{
							//logger.info("****************stbNo exists in reserveInvHist**********************");
							objRevHistDTO = reserveInvHist.get(stbNo);
							if (objRevHistDTO != null) 
							{
								String str_inv_change_date = objRevHistDTO
										.getInv_change_date();
								String str_collection_date = objRevHistDTO
										.getCollection_date();
								if (str_inv_change_date != null
										&& !str_inv_change_date.equals("")) {
									inv_change_date_hist = (Date) formatters
											.parse(str_inv_change_date);
								} else if (str_collection_date != null
										&& !str_collection_date.equals("")) {
									inv_change_date_hist = (Date) formatters
											.parse(str_collection_date);
								} else {
									inv_change_date_hist = null;

								}
								
								if(rfdStock && inv_change_date_hist!=null)
								{
									if(rfdDCDate.before(inv_change_date_hist))
									{
										objRevDTO=objRevHistDTO;
										inv_change_date=inv_change_date_hist;
									}
								}
								else if(!reverseStockFound  && inv_change_date_hist!=null)
								{
									objRevDTO=objRevHistDTO;
									inv_change_date=inv_change_date_hist;
								}
								reverseStockFound=true;
							} 
						}
								//logger.info("****************stbNo not exists in dpStockIn**********************");
						if (!churnInv.isEmpty() && churnInv.containsKey(stbNo)) 
						{
							//logger
								//	.info("****************stbNo exists in churnInv**********************");
							objChurnDTO = churnInv.get(stbNo);
							if (objChurnDTO != null) {
								String str_collection_date = objChurnDTO.getCollection_date();
								if (str_collection_date != null
										&& !str_collection_date.equals("")) {
									churn_inv_change_date = (Date) formatters
											.parse(str_collection_date);
								
									// IF Churn stock is latest	
									if(reverseStockFound)
									{
										if(inv_change_date!=null && inv_change_date.before(churn_inv_change_date))
										{
											inv_change_date=churn_inv_change_date;
											objRevDTO=objChurnDTO;
										}
									}
									else
									{
										inv_change_date=churn_inv_change_date;
										objRevDTO=objChurnDTO;
									}
								} else {
									churn_inv_change_date = null;
									
								}
								churnStockFound=true;
							}
						}
	
							
							//logger
								//	.info("****************stbNo not exists in dpStockIn**********************");
						if (!churnStockFound && !churnInvHist.isEmpty()	&& churnInvHist.containsKey(stbNo)) 
						{
							//logger
							//		.info("****************stbNo exists in churnInvHist**********************");
							objChurnHistDTO = churnInvHist.get(stbNo);
							if (objChurnHistDTO != null) {
								String str_collection_date = objChurnHistDTO.getCollection_date();
								if (str_collection_date != null && !str_collection_date.equals("")) {
									churn_inv_change_date = (Date) formatters.parse(str_collection_date);
									// IF Churn stock is latest	
									if(reverseStockFound)
									{
										if(inv_change_date!=null && inv_change_date.before(churn_inv_change_date))
										{	
											inv_change_date=churn_inv_change_date;
											objRevDTO=objChurnHistDTO;
										}
									}
									else
									{
										inv_change_date=churn_inv_change_date;
										objRevDTO=objChurnHistDTO;
									}
								} else {
									churn_inv_change_date = null;
	
								}
								churnStockFound=true;
							}
						}
	
					
				if (inv_change_date == null && assign_date==null)
				{
					//Not in DP
					DPStbInBulkDTO notInDPDTO = new DPStbInBulkDTO();
					notInDPDTO.setSerial_no(stbNo);
					notInDPDTO.setStatus("Not in DP");
					FinalListOfStb.add(notInDPDTO);
				}
				else
				{
					//logger.info("****************Inv Change Date "+inv_change_date+" Assign Date "+assign_date+"**********************");
					if(inv_change_date != null)
					{
						if (assign_date != null) {
							//logger
								//	.info("****************stbNo exists in both reserveInv,assignInv**********************");

							if (inv_change_date.after(assign_date)|| inv_change_date.equals(assign_date)) 
							{
						
							//Stock is available in both Forward and reverse. And Reverse Stock is older than fwd. 
								//Thus show both
								
								//logger.info("****************inv_change_date is greater, thus showing both reverse and forward stock information**********************");
								objRevDTO.setProduct_name(objRevDTO.getRev_product_name());
								objRevDTO.setStatus(objRevDTO.getRev_stock_in_status());
								objRevDTO.setDistId(objAssignDTO.getDistId());
								objRevDTO.setDistributor(objAssignDTO.getDistributor());
								objRevDTO.setCircle(objAssignDTO.getCircle());
								objRevDTO.setPono(objAssignDTO.getPono());
								objRevDTO.setPoDate(objAssignDTO.getPoDate());
								objRevDTO.setDcno(objAssignDTO.getDcno());
								objRevDTO.setDcDate(objAssignDTO.getDcDate());
								objRevDTO.setAccept_date(objAssignDTO.getAccept_date());
								objRevDTO.setFsc(objAssignDTO.getFsc());
								objRevDTO.setRetailer(objAssignDTO.getRetailer());
								objRevDTO.setInvoice_status(objAssignDTO.getInvoice_status());
								objRevDTO.setActivationDate(objAssignDTO.getActivationDate());
								objRevDTO.setCustomer(objAssignDTO.getCustomer());
								SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
								DateFormat format = new SimpleDateFormat("dd-MMM-yyyy");
								if(!rfdStock)
								{
									//System.out.println(" Not RFD . Inv Ch Date: "+objRevDTO.getInv_change_date());
									if (objRevDTO.getInv_change_date() != null 	&& !"".equals(objRevDTO.getInv_change_date().trim())) {
										//System.out.println(" Inv Ch Date not null. Value is: "+objRevDTO.getInv_change_date());
										Date convertedDate = dateFormat.parse(objRevDTO.getInv_change_date());
										//System.out.println("********************************convertedDate "+ convertedDate);
										String dte = "";
										dte = format.format(convertedDate);
										objRevDTO.setInv_change_date(dte);
									} 
									else if (objRevDTO.getCollection_date() != null && !"".equals(objRevDTO.getCollection_date())) 
									{
										//System.out.println(" Not RFD . Collection Date: "+objRevDTO.getCollection_date());
										Date convertedDate = dateFormat.parse(objRevDTO.getCollection_date());
										//System.out.println("********************************convertedDate "+ convertedDate);
										String dte = "";
										dte = format.format(convertedDate);
										objRevDTO.setCollection_date(dte);
									/* Commenting below code as inventory change date is already set in DB, wherever required 
									 * and should not be mandatorily set here, as in case of Churn there is no inventory change date
										if (objRevDTO.getInv_change_date() == null || "".equals(objRevDTO.getInv_change_date().trim())) {
											objRevDTO.setInv_change_date(objRevDTO.getCollection_date());
										}
									*/
									}
								}
								else
								{
									//System.out.println(" RFD Case . Rev DC Date: "+objRevDTO.getRev_dc_date() );
									//objRevDTO.setStatus(objAssignDTO.getStatus());
									if (objRevDTO.getRev_dc_date() != null 	&& !"".equals(objRevDTO.getRev_dc_date().trim())) {
										//System.out.println(" RFD Case . Inside if of REV DC: "+objRevDTO.getRev_dc_date() );

										//Date convertedDate = dateFormat.parse(objRevDTO.getRev_dc_date());
										Date convertedDate  = format.parse(objRevDTO.getRev_dc_date());
										//System.out.println("********************************convertedDate "+ convertedDate);
										String dte = "";
										dte = format.format(convertedDate);
										objRevDTO.setRev_dc_date(dte);
									} 
									
								}
								FinalListOfStb.add(objRevDTO);
							} 
							else 
							{
								//logger.info("****************inv_change_date is Older, then show only forward **********************");
								objRevDTO = objAssignDTO;
								FinalListOfStb.add(objRevDTO);
							}
						} 
						else 
						{
							//logger.info("****************not in assign stock**********************");
							objRevDTO.setProduct_name(objRevDTO
									.getRev_product_name());
							objRevDTO.setStatus(objRevDTO
									.getRev_stock_in_status());
							objRevDTO.setInvoice_status("Non Serialized stock");
							SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
							DateFormat format = new SimpleDateFormat("dd-MMM-yyyy");
							if (objRevDTO.getInv_change_date() != null	&& !"".equals(objRevDTO.getInv_change_date())) 
							{
								Date convertedDate = dateFormat.parse(objRevDTO.getInv_change_date());
								//System.out.println("********************************convertedDate "+ convertedDate);
								String dte = "";
								dte = format.format(convertedDate);
								objRevDTO.setInv_change_date(dte);
							} 
							else if (objRevDTO.getCollection_date() != null && !"".equals(objRevDTO.getCollection_date())) 
							{
								Date convertedDate = dateFormat.parse(objRevDTO.getCollection_date());
								//System.out.println("********************************convertedDate "+ convertedDate);
								String dte = "";
								dte = format.format(convertedDate);
								objRevDTO.setCollection_date(dte);
								/* Commenting below code as inventory change date is already set in DB, wherever required 
								 * and should not be mandatorily set here, as in case of Churn there is no inventory change date
								if (objRevDTO.getInv_change_date() == null || "".equals(objRevDTO.getInv_change_date())) {
									objRevDTO.setInv_change_date(objRevDTO.getCollection_date());
								}*/
								
							}
							FinalListOfStb.add(objRevDTO);
						}
					} 
					else 
					{
						//If Reverse Stock is not there and forward data is available
							//logger.info("****************Only Forward details are available, thus show only forward **********************");
							objRevDTO = objAssignDTO;
							FinalListOfStb.add(objRevDTO);
					}
					}
				}//if !stbExists
			} //END FOR LOOP
				
/*				} catch (Exception e) {
					logger
							.info("****************record creation  error**********************");
					e.printStackTrace();
					logger.error("Exception occured while record creation  ."
							+ "Exception Message: " + e.getMessage());

				}
			}

		} catch (SQLException e) {
			try {
				con.rollback();
			} catch (Exception ex) {
			}
			e.printStackTrace();
*/		} catch (Exception e) {
			try {
				con.rollback();
			} catch (Exception ex) {
			}
			e.printStackTrace();
			logger.error("Exception occured while getSerialNoList."
					+ "Exception Message: " + e.getMessage());
			try
			{
				String err = ResourceReader.getCoreResourceBundleValue(Constants.STB_Search_Bulk_Critical);
				logger.info(err + "::" +Utility.getCurrentDate());
			}
			catch(Exception ex)
			{
				ex.printStackTrace();
			}

		} finally {
			try {
				if (ps != null)
					ps.close();
				if (con != null) {
					con.close();
				}
			} catch (Exception e) {
				try
				{
					String err = ResourceReader.getCoreResourceBundleValue(Constants.STB_Search_Bulk_Critical);
					logger.info(err + "::" +Utility.getCurrentDate());
				}
				catch(Exception ex)
				{
					ex.printStackTrace();
				}
			}
		}
		return FinalListOfStb;

	}
    
	//added by aman
	
	public ArrayList getSerialNoListHistory(DPSTBHistDTO dpStbInBulkDTO)
	throws DAOException 
	{
		
		connection = DBConnectionManager.getDBConnection();
	
		DateFormat formatter = new SimpleDateFormat("dd-MMM-yyyy");
		DateFormat formatters = new SimpleDateFormat("yyyy-MM-dd");
		Connection con = null;
		PreparedStatement psFwd = null;
		PreparedStatement psRev = null;
		ResultSet rs = null;
		ResultSet rsrev = null;
		String stbList= dpStbInBulkDTO.getSerial_no();
		DPSTBHistDTO objDPStbInBulkDTO = null;
		DPSTBHistDTO objtDPStbInBulkDTO = null;
		ArrayList<DPSTBHistDTO> list = null;
		
		try
		{
			list = new ArrayList<DPSTBHistDTO>();
			logger.info("stbList  ::  "+stbList);
			
			String queryFwd=" select SI.SERIAL_NO, PM.PRODUCT_NAME, 'Not In Stock -- ' || (CASE SI.STATUS WHEN 3 then 'Assigned' WHEN 12 then 'Returned as SWAP' " + 
			" when 11 then 'Returned as DOA' when 13 then 'Added By Warehouse' when 14 then 'Returned To Warehouse' else 'Flushed out' end) as STB_STATUS, " +
			" LM.LOGIN_NAME, AD.ACCOUNT_NAME, CM.CIRCLE_NAME, coalesce(si.INV_NO, SI.INV_NO) AS INV_NO, coalesce(PO.PO_NO, 'REV-PO') AS PO_NO, " +
			" DPDTH.TO_CHAR(PO.PO_DATE, 'dd/MM/yyyy') AS PO_DATE, IH.DC_NO, DPDTH.TO_CHAR(IH.DC_DT, 'dd/MM/yyyy') AS DC_DT, " +
			" DPDTH.TO_CHAR(Si.DISTRIBUTOR_PURCHASE_DATE, 'dd/MM/yyyy') AS ACCEPT_DATE, (select ACCOUNT_NAME from DPDTH.VR_ACCOUNT_DETAILS " + 
			" where ACCOUNT_ID=SI.FSE_ID) as FSE_NAME, (select ACCOUNT_NAME from DPDTH.VR_ACCOUNT_DETAILS where ACCOUNT_ID=SI.RETAILER_ID) as RET_NAME, " +
			" DPDTH.TO_CHAR(SI.ASSIGN_DATE, 'dd/MM/yyyy') as ASSIGN_DATE, SI.CUSTOMER_ID, SI.ASSIGN_DATE as SORT_DATE " +
			" from DPDTH.DP_STOCK_INVENTORY_ASSIGNED SI inner join DPDTH.DP_PRODUCT_MASTER PM on SI.PRODUCT_ID=PM.PRODUCT_ID " + 
			" inner join DPDTH.VR_ACCOUNT_DETAILS AD on SI.DISTRIBUTOR_ID=AD.ACCOUNT_ID inner join DPDTH.VR_LOGIN_MASTER LM on AD.ACCOUNT_ID=LM.LOGIN_ID " + 
			" inner join DPDTH.VR_CIRCLE_MASTER CM on AD.CIRCLE_ID=CM.CIRCLE_ID left outer join DPDTH.INVOICE_HEADER ih on ih.INV_NO=SI.INV_NO left outer join " + 
			" DPDTH.PO_HEADER PO on PO.PO_NO=IH.PO_NO " +
			" where SERIAL_NO in ("+Constants.REPLACE_PLACEHOLDER+") " +
			" UNION ALL " +
			" select SI.SERIAL_NO, PM.PRODUCT_NAME, CASE WHEN SI.MARK_DAMAGED='I' AND IH.STATUS='INTRANSIT' then 'In Transit -- Pending In PO' " +
			" WHEN SI.MARK_DAMAGED='I' AND IH.STATUS='TSM_APPROVAL_PENDING' then 'In Transit -- Pending TSM action(Short/Wrong)' " +
			" WHEN SI.STATUS=5 then 'Serialized Stock -- Restricted' else 'Serialized Stock -- Unassigned' end as STB_STATUS, " +
			" LM.LOGIN_NAME, AD.ACCOUNT_NAME, CM.CIRCLE_NAME, coalesce(si.INV_NO, SI.INV_NO) AS INV_NO, coalesce(PO.PO_NO, 'REV-PO') AS PO_NO, " + 
			" DPDTH.TO_CHAR(PO.PO_DATE, 'dd/MM/yyyy') AS PO_DATE, IH.DC_NO, DPDTH.TO_CHAR(IH.DC_DT, 'dd/MM/yyyy') AS DC_DT, " +
			" DPDTH.TO_CHAR(Si.DISTRIBUTOR_PURCHASE_DATE, 'dd/MM/yyyy') AS ACCEPT_DATE, (select ACCOUNT_NAME from DPDTH.VR_ACCOUNT_DETAILS " + 
			" where ACCOUNT_ID=SI.FSE_ID) as FSE_NAME, (select ACCOUNT_NAME from DPDTH.VR_ACCOUNT_DETAILS where ACCOUNT_ID=SI.RETAILER_ID) as RET_NAME, " +
			" DPDTH.TO_CHAR(SI.ASSIGN_DATE, 'dd/MM/yyyy') as ASSIGN_DATE, SI.CUSTOMER_ID, SI.DISTRIBUTOR_PURCHASE_DATE as SORT_DATE  " +
			" from DPDTH.DP_STOCK_INVENTORY SI inner join DPDTH.DP_PRODUCT_MASTER PM on SI.PRODUCT_ID=PM.PRODUCT_ID inner join DPDTH.VR_ACCOUNT_DETAILS AD on " + 
			" SI.DISTRIBUTOR_ID=AD.ACCOUNT_ID inner join DPDTH.VR_LOGIN_MASTER LM on AD.ACCOUNT_ID=LM.LOGIN_ID inner join DPDTH.VR_CIRCLE_MASTER CM " +
			" on AD.CIRCLE_ID=CM.CIRCLE_ID left outer join DPDTH.INVOICE_HEADER ih on ih.INV_NO=SI.INV_NO left outer join DPDTH.PO_HEADER PO " +
			" on PO.PO_NO=IH.PO_NO "+
			" where SERIAL_NO in ("+Constants.REPLACE_PLACEHOLDER+") "+
			" order by SERIAL_NO, SORT_DATE desc with ur ";
			
			queryFwd = queryFwd.replaceAll(Constants.REPLACE_PLACEHOLDER, stbList);
			logger.info("queryFwd::"+queryFwd);
			psFwd = connection.prepareStatement(queryFwd);
			rs=psFwd.executeQuery();
			while(rs.next())
			{
				objDPStbInBulkDTO = new DPSTBHistDTO();
				
				objDPStbInBulkDTO.setSerial_no(rs.getString("SERIAL_NO"));
				objDPStbInBulkDTO.setProduct_name(rs.getString("PRODUCT_NAME"));
				objDPStbInBulkDTO.setStatus(rs.getString("STB_STATUS"));
				objDPStbInBulkDTO.setLoginName(rs.getString("LOGIN_NAME"));
				objDPStbInBulkDTO.setDistId(rs.getString("LOGIN_NAME"));
				objDPStbInBulkDTO.setAccountName(rs.getString("ACCOUNT_NAME"));
				objDPStbInBulkDTO.setDistributor(rs.getString("LOGIN_NAME"));
				objDPStbInBulkDTO.setCircle(rs.getString("CIRCLE_NAME"));
				objDPStbInBulkDTO.setInvNo(rs.getString("INV_NO"));
				objDPStbInBulkDTO.setPono(rs.getString("PO_NO"));
				objDPStbInBulkDTO.setPoDate(rs.getString("PO_DATE"));
				objDPStbInBulkDTO.setDcno(rs.getString("DC_NO"));
				objDPStbInBulkDTO.setDcDate(rs.getString("DC_DT"));
				objDPStbInBulkDTO.setAccept_date(rs.getString("ACCEPT_DATE"));
				objDPStbInBulkDTO.setFseName(rs.getString("FSE_NAME"));
				objDPStbInBulkDTO.setRetName(rs.getString("RET_NAME"));
				objDPStbInBulkDTO.setAssignDate(rs.getString("ASSIGN_DATE"));
				objDPStbInBulkDTO.setCustomer(rs.getString("CUSTOMER_ID"));
				
				objDPStbInBulkDTO.setStrSerialNo(rs.getString("SERIAL_NO"));
				objDPStbInBulkDTO.setTsSTBDate(rs.getTimestamp("SORT_DATE"));
				
				list.add(objDPStbInBulkDTO);
				objDPStbInBulkDTO = null;	
			}
			
			
	String queryRev=    " select SI.DEFECTIVE_SR_NO as DEFECTIVE_SR_NO, PM.PRODUCT_NAME, LM.LOGIN_NAME, AD.ACCOUNT_NAME, CM.CIRCLE_NAME, DPDTH.TO_CHAR(SI.INV_CHANGE_DATE, 'dd/MM/yyyy') AS INV_CHANGE_DT, " +
						" CM.COLLECTION_NAME as INV_CHANGE_TYPE, coalesce(DM.DEFECT_NAME, '') as DEFECT_TYPE, '' as DC_NO, '' as DC_DATE, '' as DC_STATUS, " +
						" 'Defective Stock -- Pending For Receiving' as STATUS, SI.INV_CHANGE_DATE AS SORT_DATE " +
						" from DP_REV_INVENTORY_CHANGE SI inner join DPDTH.DP_PRODUCT_MASTER PM on SI.DEFECTIVE_PRODUCT_ID=PM.PRODUCT_ID " + 
						" inner join DPDTH.VR_ACCOUNT_DETAILS AD on SI.NEW_DIST_D=AD.ACCOUNT_ID inner join DPDTH.VR_LOGIN_MASTER LM on AD.ACCOUNT_ID=LM.LOGIN_ID " + 
						" inner join DPDTH.VR_CIRCLE_MASTER CM on AD.CIRCLE_ID=CM.CIRCLE_ID inner join DP_REV_COLLECTION_MST CM on SI.COLLECTION_ID=CHAR(CM.COLLECTION_ID) " +
						" left outer join DP_REV_DEFECT_MST DM on SI.DEFECT_ID=DM.DEFECT_ID " +
						" where SI.STATUS='REC' and SI.DEFECTIVE_SR_NO in ("+Constants.REPLACE_PLACEHOLDER+") " +
						" UNION " +
						" select SI.SERIAL_NO_COLLECT as DEFECTIVE_SR_NO, PM.PRODUCT_NAME, LM.LOGIN_NAME, AD.ACCOUNT_NAME, CM.CIRCLE_NAME, DPDTH.TO_CHAR(SI.CREATED_ON, 'dd/MM/yyyy')" +
						" AS INV_CHANGE_DT,  CM.COLLECTION_NAME as INV_CHANGE_TYPE, coalesce(DM.DEFECT_NAME, '') as DEFECT_TYPE, '' as DC_NO, '' as DC_DATE, ''" +
						" as DC_STATUS, coalesce(case when SI.STATUS='COL' then 'Defective Stock -- Pending DC Generation' when SI.STATUS='IDC' and" +
						" DH.STATUS='DRAFT' then 'Defective Stock -- In draft DC'  when SI.STATUS='IDC' and DH.STATUS='CREATED' then 'In Transit --" +
						" Ready for Dispatch DC ' when SI.STATUS='S2W' then 'In Transit -- Sent To WH DC' else SI.STATUS end, '') as STATUS, DATE(SI.CREATED_ON) AS SORT_DATE" +
						" from DP_REV_STOCK_INVENTORY SI inner join DPDTH.DP_PRODUCT_MASTER PM on SI.PRODUCT_ID=PM.PRODUCT_ID inner join DPDTH.VR_ACCOUNT_DETAILS AD on SI.CREATED_BY=AD.ACCOUNT_ID" +
						" inner join DPDTH.VR_LOGIN_MASTER LM on AD.ACCOUNT_ID=LM.LOGIN_ID  inner join DPDTH.VR_CIRCLE_MASTER CM on AD.CIRCLE_ID=CM.CIRCLE_ID" +
						" inner join DP_REV_COLLECTION_MST CM on SI.COLLECTION_ID=CM.COLLECTION_ID  left outer join DP_REV_DEFECT_MST DM" +
						" on SI.DEFECT_ID=DM.DEFECT_ID  left outer join DP_REV_DELIVERY_CHALAN_DETAIL DD on SI.SERIAL_NO_COLLECT=DD.SERIAL_NO and SI.PRODUCT_ID=DD.PRODUCT_ID and" +
						" SI.CREATED_BY=DD.DIST_ID and DD.COLLECTION_ID=2 left outer join DP_REV_DELIVERY_CHALAN_HDR DH on DD.DC_NO=DH.DC_NO" +
						" where SI.COLLECTION_ID=2 and SI.SERIAL_NO_COLLECT in ("+Constants.REPLACE_PLACEHOLDER+") " +
						" UNION " +
						" select SI.DEFECTIVE_SR_NO, PM.PRODUCT_NAME, LM.LOGIN_NAME, AD.ACCOUNT_NAME, CM.CIRCLE_NAME, DPDTH.TO_CHAR(SI.INV_CHANGE_DATE, 'dd/MM/yyyy') AS INV_CHANGE_DT, " +
						" CM.COLLECTION_NAME as INV_CHANGE_TYPE, coalesce(DM.DEFECT_NAME, '') as DEFECT_TYPE, coalesce(DH.DC_NO, '') as DC_NO, " +
						" coalesce(DPDTH.TO_CHAR(DH.CREATED_ON, 'dd/MM/yyyy'),'') as DC_DATE, coalesce(CASE DH.STATUS WHEN 'DRAFT' then 'Draft DC' when 'CREATED' " + 
						" then 'Ready to dispatch' when 'SUCCESS' then 'Sent to warehouse' else DH.STATUS end, '') as DC_STATUS,  " +
						" coalesce(case when RS.STATUS='COL' then 'Defective Stock -- Pending DC Generation' when RS.STATUS='IDC' and DH.STATUS='DRAFT' then 'Defective Stock -- In draft DC' " +
						" when RS.STATUS='IDC' and DH.STATUS='CREATED' then 'In Transit -- Ready for Dispatch DC ' when RS.STATUS='S2W' then 'In Transit -- Sent To WH DC' else RS.STATUS end, '') as STATUS, SI.INV_CHANGE_DATE AS SORT_DATE" +
						" from DP_REV_INVENTORY_CHANGE SI inner join DP_REV_STOCK_INVENTORY RS on SI.DEFECTIVE_SR_NO=RS.SERIAL_NO_COLLECT and SI.DEFECTIVE_PRODUCT_ID=RS.PRODUCT_ID " +
						" and SI.NEW_DIST_D=RS.CREATED_BY and SI.INV_CHANGE_DATE <= date(RS.CREATED_ON) and SI.COLLECTION_ID=char(RS.COLLECTION_ID) " +
						" inner join DPDTH.DP_PRODUCT_MASTER PM on SI.DEFECTIVE_PRODUCT_ID=PM.PRODUCT_ID  " +
						" inner join DPDTH.VR_ACCOUNT_DETAILS AD on SI.NEW_DIST_D=AD.ACCOUNT_ID inner join DPDTH.VR_LOGIN_MASTER LM on AD.ACCOUNT_ID=LM.LOGIN_ID " + 
						" inner join DPDTH.VR_CIRCLE_MASTER CM on AD.CIRCLE_ID=CM.CIRCLE_ID inner join DP_REV_COLLECTION_MST CM on SI.COLLECTION_ID=CHAR(CM.COLLECTION_ID) " +
						" left outer join DP_REV_DEFECT_MST DM on SI.DEFECT_ID=DM.DEFECT_ID " +
						" left outer join DP_REV_DELIVERY_CHALAN_DETAIL DD on RS.SERIAL_NO_COLLECT=DD.SERIAL_NO and RS.PRODUCT_ID=DD.PRODUCT_ID and RS.CREATED_BY=DD.DIST_ID " +
						" and RS.CREATED_ON=DD.COLLECTED_ON left outer join DP_REV_DELIVERY_CHALAN_HDR DH on DD.DC_NO=DH.DC_NO " +
						" where SI.STATUS='COL' and SI.DEFECTIVE_SR_NO in ("+Constants.REPLACE_PLACEHOLDER+") " +
						" UNION "+
						" select SI.DEFECTIVE_SR_NO, PM.PRODUCT_NAME, LM.LOGIN_NAME, AD.ACCOUNT_NAME, CM.CIRCLE_NAME, DPDTH.TO_CHAR(SI.INV_CHANGE_DATE, 'dd/MM/yyyy') AS INV_CHANGE_DT, "+
						" CM.COLLECTION_NAME as INV_CHANGE_TYPE, coalesce(DM.DEFECT_NAME, '') as DEFECT_TYPE, coalesce(DH.DC_NO, '') as DC_NO, "+
						" coalesce(DPDTH.TO_CHAR(DH.CREATED_ON, 'dd/MM/yyyy'),'') as DC_DATE, coalesce(CASE DH.STATUS WHEN 'DRAFT' then 'Draft DC' when 'CREATED' "+ 
						" then 'Ready to dispatch' when 'SUCCESS' then 'Sent to warehouse' when 'AIW' then 'Accepted In WH' when 'AIWM' then 'Accept In Warehouse With Modification' "+
						" when 'REJECT' then 'Rejected by WH' else DH.STATUS end, '') as DC_STATUS,  "+
						" coalesce(case when RS.STATUS='COL' then 'Defective Stock -- Pending DC Generation' when RS.STATUS='IDC' and DH.STATUS='DRAFT' then 'Defective Stock -- In draft DC' "+
						" when RS.STATUS='IDC' and DH.STATUS='CREATED' then 'In Transit -- Ready for Dispatch DC ' when RS.STATUS='S2W' then 'In Transit -- Sent To WH DC' "+
						" when RS.STATUS='AIW' then 'Not in Stock -- Defective Stock Received at WH' when RS.STATUS='MSN' then 'Not in Stock -- Defective Stock Not Received at WH' "+ 
						" when RS.STATUS='ABW' then 'Not in Stock -- Defective Stock Added by WH' when RS.STATUS='REP' then 'Self repaired by Distributor' "+
						" when RS.STATUS='REU' then 'Added in stock by distributor' else RS.STATUS end, '') as STATUS, SI.INV_CHANGE_DATE AS SORT_DATE "+
						" from DP_REV_INVENTORY_CHANGE SI inner join DP_REV_STOCK_INVENTORY_HIST RS on SI.DEFECTIVE_SR_NO=RS.SERIAL_NO_COLLECT and SI.DEFECTIVE_PRODUCT_ID=RS.PRODUCT_ID "+
						" and SI.NEW_DIST_D=RS.CREATED_BY and SI.INV_CHANGE_DATE <= date(RS.CREATED_ON) and SI.COLLECTION_ID=char(RS.COLLECTION_ID) "+
						" inner join DPDTH.DP_PRODUCT_MASTER PM on SI.DEFECTIVE_PRODUCT_ID=PM.PRODUCT_ID  "+
						" inner join DPDTH.VR_ACCOUNT_DETAILS AD on SI.NEW_DIST_D=AD.ACCOUNT_ID inner join DPDTH.VR_LOGIN_MASTER LM on AD.ACCOUNT_ID=LM.LOGIN_ID "+ 
						" inner join DPDTH.VR_CIRCLE_MASTER CM on AD.CIRCLE_ID=CM.CIRCLE_ID inner join DP_REV_COLLECTION_MST CM on SI.COLLECTION_ID=CHAR(CM.COLLECTION_ID) "+
						" left outer join DP_REV_DEFECT_MST DM on SI.DEFECT_ID=DM.DEFECT_ID "+
						" left outer join DP_REV_DELIVERY_CHALAN_DETAIL DD on RS.SERIAL_NO_COLLECT=DD.SERIAL_NO and RS.PRODUCT_ID=DD.PRODUCT_ID and RS.CREATED_BY=DD.DIST_ID "+
						" and RS.CREATED_ON=DD.COLLECTED_ON left outer join DP_REV_DELIVERY_CHALAN_HDR DH on DD.DC_NO=DH.DC_NO "+
						" where SI.STATUS='COL' and SI.DEFECTIVE_SR_NO in ("+Constants.REPLACE_PLACEHOLDER+") "+
						" UNION "+
						" select SI.DEFECTIVE_SR_NO, PM.PRODUCT_NAME, LM.LOGIN_NAME, AD.ACCOUNT_NAME, CM.CIRCLE_NAME, DPDTH.TO_CHAR(SI.INV_CHANGE_DATE, 'dd/MM/yyyy') AS INV_CHANGE_DT, "+
						" CM.COLLECTION_NAME as INV_CHANGE_TYPE, coalesce(DM.DEFECT_NAME, '') as DEFECT_TYPE, coalesce(DH.DC_NO, '') as DC_NO, "+
						" coalesce(DPDTH.TO_CHAR(DH.CREATED_ON, 'dd/MM/yyyy'),'') as DC_DATE, coalesce(CASE DH.STATUS WHEN 'DRAFT' then 'Draft DC' when 'CREATED' "+ 
						" then 'Ready to dispatch' when 'SUCCESS' then 'Sent to warehouse' when 'AIW' then 'Accepted In Warehouse' when 'AIWM' then 'Accept In Warehouse With Modification' "+
						" when 'REJECT' then 'Rejected by Warehouse' when 'ERROR' then 'Error in sending to Warehouse' else DH.STATUS end, '') as DC_STATUS, "+
						" coalesce(case when DD.STATUS='ERR' then 'Error in sending to WH' when RS.STATUS='COL' then 'Defective Stock -- Pending DC Generation' when RS.STATUS='IDC' and DH.STATUS='DRAFT' then 'Defective Stock -- In draft DC' "+
						" when RS.STATUS='IDC' and DH.STATUS='CREATED' then 'In Transit -- Ready for Dispatch DC ' when RS.STATUS='S2W' then 'In Transit -- Sent To WH DC' "+
						" when RS.STATUS='AIW' then 'Not in Stock -- Defective Stock Received at WH' when RS.STATUS='MSN' then 'Not in Stock -- Defective Stock Not Received at WH' "+ 
						" when RS.STATUS='ABW' then 'Not in Stock -- Defective Stock Added by WH' when RS.STATUS='REP' then 'Self repaired by Distributor' "+
						" when RS.STATUS='REU' then 'Added in stock by distributor' else RS.STATUS end, '') as STATUS, SI.INV_CHANGE_DATE AS SORT_DATE "+
						" from DP_REV_INVENTORY_CHANGE_HIST SI inner join DP_REV_STOCK_INVENTORY_HIST RS on SI.DEFECTIVE_SR_NO=RS.SERIAL_NO_COLLECT and SI.DEFECTIVE_PRODUCT_ID=RS.PRODUCT_ID "+
						" and SI.NEW_DIST_D=RS.CREATED_BY and SI.INV_CHANGE_DATE <= date(RS.CREATED_ON) and SI.COLLECTION_ID=char(RS.COLLECTION_ID) "+
						" inner join DPDTH.DP_PRODUCT_MASTER PM on SI.DEFECTIVE_PRODUCT_ID=PM.PRODUCT_ID  "+
						" inner join DPDTH.VR_ACCOUNT_DETAILS AD on SI.NEW_DIST_D=AD.ACCOUNT_ID inner join DPDTH.VR_LOGIN_MASTER LM on AD.ACCOUNT_ID=LM.LOGIN_ID "+ 
						" inner join DPDTH.VR_CIRCLE_MASTER CM on AD.CIRCLE_ID=CM.CIRCLE_ID inner join DP_REV_COLLECTION_MST CM on SI.COLLECTION_ID=CHAR(CM.COLLECTION_ID) "+
						" left outer join DP_REV_DEFECT_MST DM on SI.DEFECT_ID=DM.DEFECT_ID "+
						" left outer join DP_REV_DELIVERY_CHALAN_DETAIL DD on RS.SERIAL_NO_COLLECT=DD.SERIAL_NO and RS.PRODUCT_ID=DD.PRODUCT_ID and RS.CREATED_BY=DD.DIST_ID "+
						" and RS.CREATED_ON=DD.COLLECTED_ON left outer join DP_REV_DELIVERY_CHALAN_HDR DH on DD.DC_NO=DH.DC_NO "+
						" where RS.STATUS!='ABW' and SI.DEFECTIVE_SR_NO in ("+Constants.REPLACE_PLACEHOLDER+") "+
						" UNION "+
						" select SI.SERIAL_NO_COLLECT as DEFECTIVE_SR_NO, PM.PRODUCT_NAME, LM.LOGIN_NAME, AD.ACCOUNT_NAME, CM.CIRCLE_NAME, DPDTH.TO_CHAR(SI.CREATED_ON, 'dd/MM/yyyy') AS INV_CHANGE_DT, "+
						" CM.COLLECTION_NAME as INV_CHANGE_TYPE, coalesce(DM.DEFECT_NAME, '') as DEFECT_TYPE, coalesce(DH.DC_NO, '') as DC_NO, "+
						" coalesce(DPDTH.TO_CHAR(DH.CREATED_ON, 'dd/MM/yyyy'),'') as DC_DATE, 'Accept In Warehouse With Modification' as DC_STATUS, "+ 
						" 'Not in Stock -- Defective Stock Added by WH' as STATUS, DATE(SI.CREATED_ON) AS SORT_DATE "+
						" from DP_REV_STOCK_INVENTORY_HIST SI inner join DPDTH.DP_PRODUCT_MASTER PM on SI.PRODUCT_ID=PM.PRODUCT_ID "+ 
						" inner join DPDTH.VR_ACCOUNT_DETAILS AD on SI.CREATED_BY=AD.ACCOUNT_ID inner join DPDTH.VR_LOGIN_MASTER LM on AD.ACCOUNT_ID=LM.LOGIN_ID "+ 
						" inner join DPDTH.VR_CIRCLE_MASTER CM on AD.CIRCLE_ID=CM.CIRCLE_ID inner join DP_REV_COLLECTION_MST CM on SI.COLLECTION_ID=CM.COLLECTION_ID "+
						" left outer join DP_REV_DEFECT_MST DM on SI.DEFECT_ID=DM.DEFECT_ID "+
						" left outer join DP_REV_DELIVERY_CHALAN_DETAIL DD on SI.SERIAL_NO_COLLECT=DD.SERIAL_NO and date(SI.ARCHIVED_DATE)=date(DD.COLLECTED_ON) "+
						" left outer join DP_REV_DELIVERY_CHALAN_HDR DH on DD.DC_NO=DH.DC_NO "+
						" where SI.STATUS='ABW' and SI.SERIAL_NO_COLLECT in ("+Constants.REPLACE_PLACEHOLDER+")"; 
									
			queryRev = queryRev.replaceAll(Constants.REPLACE_PLACEHOLDER, stbList);
			logger.info("queryRev::"+queryRev);
			psRev = connection.prepareStatement(queryRev,ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_UPDATABLE);
			
			rsrev=psRev.executeQuery();
			
			logger.info(" resut set size::"+rsrev.getRow());
			
			while(rsrev.next())
			{
				objtDPStbInBulkDTO = new DPSTBHistDTO();
				
				logger.info("here");
				objtDPStbInBulkDTO.setSerial_no(rsrev.getString("DEFECTIVE_SR_NO"));
				objtDPStbInBulkDTO.setDefectiveSrNo(rsrev.getString("DEFECTIVE_SR_NO"));
				objtDPStbInBulkDTO.setProduct_name(rsrev.getString("PRODUCT_NAME"));
				objtDPStbInBulkDTO.setLoginName(rsrev.getString("LOGIN_NAME"));
				objtDPStbInBulkDTO.setDistIdRev(rsrev.getString("LOGIN_NAME"));
				objtDPStbInBulkDTO.setAccountName(rsrev.getString("ACCOUNT_NAME"));
				objtDPStbInBulkDTO.setDistributorRev(rsrev.getString("ACCOUNT_NAME"));
				//objtDPStbInBulkDTO.setCircle(rsrev.getString("CIRCLE_NAME"));
				objtDPStbInBulkDTO.setCircleRev(rsrev.getString("CIRCLE_NAME"));
				objtDPStbInBulkDTO.setInv_change_date(rsrev.getString("INV_CHANGE_DT"));
				objtDPStbInBulkDTO.setInv_change_type(rsrev.getString("INV_CHANGE_TYPE"));
				objtDPStbInBulkDTO.setDefect_type(rsrev.getString("DEFECT_TYPE"));
				objtDPStbInBulkDTO.setDcno(rsrev.getString("DC_NO"));
				objtDPStbInBulkDTO.setDcDate(rsrev.getString("DC_DATE"));
				objtDPStbInBulkDTO.setDcStatus(rsrev.getString("DC_STATUS"));
				objtDPStbInBulkDTO.setStatus(rsrev.getString("STATUS"));
				
				objtDPStbInBulkDTO.setStrSerialNo(rsrev.getString("DEFECTIVE_SR_NO"));
				objtDPStbInBulkDTO.setTsSTBDate(rsrev.getTimestamp("SORT_DATE"));
				
				list.add(objtDPStbInBulkDTO);
				objtDPStbInBulkDTO = null;
			}
								
			Collections.sort(list, new DPSTBHistDTO());
		}
		catch (Exception e) 
		{
			e.printStackTrace();
			throw new DAOException(e.getMessage());
		}
		finally
		{
			DBConnectionManager.releaseResources(psFwd ,rs);
			DBConnectionManager.releaseResources(psRev ,rsrev);
			DBConnectionManager.releaseResources(connection);
		}
		
		return list;
	}
		
	
	
	
	
	//end of changes by aman
	
	
	public ArrayList getAssignSerialNoList(DPStbInBulkDTO dpStbInBulkDTO)
			throws DAOException {
		//logger
			//	.info("****************In getAssignSerialNoList method********************** ");
		ArrayList assignSerialList = new ArrayList();
		PreparedStatement prepareStatement = null;
		ResultSet resultSet = null;
		DPStbInBulkDTO dpStbInBulk = null;
		Connection connection = null;

		String stbList = dpStbInBulkDTO.getSerial_no();
		try {
			connection = DBConnectionManager.getDBConnection();
			String query = "SELECT SERIAL_NO, "
					+ "pm.PRODUCT_NAME AS stb_type, "
					+ "csm.STATUS_ID, "
					+ "si.MARK_DAMAGED, "
					+ "lm.login_name AS dist_id, "
					+ "ad.ACCOUNT_NAME  as DIST, "
					+ "cm.circle_name, "
					+ "ph.PO_NO, "
					+ "ph.PO_DATE AS podate, "
					+ "ih.DC_NO, "
					+ "ih.DC_DT AS dc_date, "
					+ "ih.dist_action_date AS Accpt_Date, "
					+ "ad1.ACCOUNT_NAME as FSE, "
					+ "ad2.ACCOUNT_NAME as RETAILER, "
					+ "DPDTH.TO_CHAR(si.ASSIGN_DATE, 'dd/MM/yyyy') as Activation ,"
					+ " ih.Status, "
					+ "postatus.id, "
					+ "postatus.value, "
					+ "si.CUSTOMER_ID, "
					+ " si.INV_NO, si.Flushout_Flag "
					+ "FROM  "
					+ "DP_PRODUCT_MASTER pm, "
					+ "DP_CPE_STATUS_MASTER csm, "
					+ "VR_ACCOUNT_DETAILS ad, "
					+ "vr_login_master lm, "
					+ "VR_CIRCLE_MASTER cm, "
					+ "DP_STOCK_INVENTORY_ASSIGNED si LEFT OUTER JOIN VR_ACCOUNT_DETAILS ad1 ON si.FSE_ID = ad1.ACCOUNT_ID "
					+ "LEFT OUTER JOIN VR_ACCOUNT_DETAILS ad2 ON si.RETAILER_ID = ad2.ACCOUNT_ID "
					+ "LEFT OUTER JOIN INVOICE_HEADER ih left outer join PO_HEADER ph "
					+ "LEFT OUTER JOIN (select id,value from DP_CONFIGURATION_DETAILS where CONFIG_ID=2) as postatus  on (postatus.ID = ph.PO_STATUS)"
					+ " on ih.PO_NO = ph.PO_NO ON si.INV_NO = ih.INV_NO "
					+ "WHERE  si.STATUS = csm.STATUS_ID "
					+ "AND si.DISTRIBUTOR_ID = ad.ACCOUNT_ID "
					+ "AND si.DISTRIBUTOR_ID = lm.LOGIN_ID "
					+ "AND si.PRODUCT_ID = pm.PRODUCT_ID "
					+ "AND cm.circle_id = ad.circle_id "
					+ "AND si.ASSIGN_DATE =(select max(ASSIGN_DATE) from DP_STOCK_INVENTORY_ASSIGNED a where a.SERIAL_NO=si.SERIAL_NO ) "
					+ "and si.SERIAL_NO in (" + stbList + " ) "
					+ " order by si.SERIAL_NO ";

			//logger.info("****************query**********************" + query);
			prepareStatement = connection.prepareStatement(query);
			resultSet = prepareStatement.executeQuery();
			while (resultSet.next()) 
			{
				dpStbInBulk = new DPStbInBulkDTO();

				if (resultSet.getString("SERIAL_NO") != null) {
					dpStbInBulk.setSerial_no(resultSet.getString("SERIAL_NO"));
				} else {
					dpStbInBulk.setSerial_no("");
				}
				if (resultSet.getString("stb_type") != null) {
					dpStbInBulk
							.setProduct_name(resultSet.getString("stb_type"));
				} else {
					dpStbInBulk.setProduct_name("");
				}

				if(resultSet.getString("Flushout_Flag") != null && !"0".equals(resultSet.getString("Flushout_Flag")))
				{
					if("-1".equals(resultSet.getString("Flushout_Flag")) )
						dpStbInBulk.setStatus("Not In Stock -- Flushed out by Botree");
					else if("-2".equals(resultSet.getString("Flushout_Flag")))
						dpStbInBulk.setStatus("Not In Stock -- Flushed out by DP");								
				}
				else
				{
					if (resultSet.getString("MARK_DAMAGED") != null) 
					{
						if ("I".equals(resultSet.getString("MARK_DAMAGED"))) 
						{
		
							if (resultSet.getString("id") != null) 
							{
								if ("1".equals(resultSet.getString("id"))
									|| "2".equals(resultSet.getString("id"))
									|| "3".equals(resultSet.getString("id"))
									|| "4".equals(resultSet.getString("id"))
									|| "7".equals(resultSet.getString("id"))
									|| "8".equals(resultSet.getString("id"))
									|| "9".equals(resultSet.getString("id"))
									|| "10".equals(resultSet.getString("id"))
									|| "11".equals(resultSet.getString("id"))
									|| "12".equals(resultSet.getString("id"))
									|| "13".equals(resultSet.getString("id"))
									|| "14".equals(resultSet.getString("id"))) {
									dpStbInBulk.setStatus("In Transit -- Pending In PO");
								} else if ("32".equals(resultSet.getString("id"))
										|| "34".equals(resultSet.getString("id"))) {
									dpStbInBulk.setStatus("In Transit -- Pending TSM action(Short/Wrong)");
								} else if ("31".equals(resultSet.getString("id"))
										|| "33".equals(resultSet.getString("id"))) {
									dpStbInBulk.setStatus("Serialized Stock -- Restricted");
								} else {
									dpStbInBulk.setStatus("");
								}
							} else {
								dpStbInBulk.setStatus("");
							}
						}
					 else {
						if (resultSet.getString("STATUS_ID") != null) {
							if ("10".equals(resultSet.getString("STATUS_ID"))) {
								dpStbInBulk
										.setStatus("Serialized Stock -- Unassigned Pending");
							} else if ("1".equals(resultSet
									.getString("STATUS_ID"))) {
								dpStbInBulk
										.setStatus("Serialized Stock -- Unassigned Complete");
							} else if ("5".equals(resultSet
									.getString("STATUS_ID"))) {
								dpStbInBulk
										.setStatus("Serialized Stock -- Restricted");
							} else if ("-1".equals(resultSet
									.getString("STATUS_ID"))) {
								dpStbInBulk.setStatus("Returned To Warehouse");
							} else if ("3".equals(resultSet
									.getString("STATUS_ID"))) {
								dpStbInBulk
										.setStatus("Not In Stock -- Assigned");
							} else if ("4".equals(resultSet
									.getString("STATUS_ID"))) {
								dpStbInBulk.setStatus("Repaired");
							} else if ("11".equals(resultSet
									.getString("STATUS_ID"))) {
								dpStbInBulk.setStatus("Returned as DOA");
							} else if ("12".equals(resultSet
									.getString("STATUS_ID"))) {
								dpStbInBulk.setStatus("Returned as SWAP");
							} else if ("13".equals(resultSet
									.getString("STATUS_ID"))) {
								dpStbInBulk.setStatus("Added By Warehouse");
							} else if ("14".equals(resultSet
									.getString("STATUS_ID"))) {
								dpStbInBulk
										.setStatus("Not In Stock -- Ok Stock Returned To WH");
							}else if ("-1".equals(resultSet.getString("STATUS_ID"))) {
								dpStbInBulk.setStatus("Not In Stock -- Flushed out by Botree");
							}else if ("-2".equals(resultSet.getString("STATUS_ID"))) {
								dpStbInBulk.setStatus("Not In Stock -- Flushed out by DP");
							} else {
								dpStbInBulk.setStatus(resultSet
										.getString("STATUS_ID"));
							}
						} else {
							dpStbInBulk.setStatus("");
						}
					}
				}else {
					dpStbInBulk.setStatus("");
				}
			} 

				if (resultSet.getString("dist_id") != null) {
					dpStbInBulk.setDistId(resultSet.getString("dist_id"));
				} else {
					dpStbInBulk.setDistId("");
				}
				if (resultSet.getString("dist") != null) {
					dpStbInBulk.setDistributor(resultSet.getString("dist"));
				} else {
					dpStbInBulk.setDistributor("");
				}
				if (resultSet.getString("circle_name") != null) {
					dpStbInBulk.setCircle(resultSet.getString("circle_name"));
				} else {
					dpStbInBulk.setCircle("");
				}

				if (resultSet.getString("PO_NO") != null) {
					dpStbInBulk.setPono(resultSet.getString("PO_NO"));
				} else {
					if (resultSet.getString("INV_NO") != null) {
						dpStbInBulk.setPono(resultSet.getString("INV_NO"));
					} else {
						dpStbInBulk.setPono("");
					}
				}
				if (resultSet.getDate("podate") != null) {
					Date dt = resultSet.getDate("podate");
					DateFormat format = new SimpleDateFormat("dd-MMM-yyyy");
					String dte = "";
					dte = format.format(dt);
					dpStbInBulk.setPoDate(dte);
				} else {
					dpStbInBulk.setPoDate("");
				}

				if (resultSet.getString("DC_NO") != null) {
					dpStbInBulk.setDcno(resultSet.getString("DC_NO"));
				} else {
					dpStbInBulk.setDcno("");
				}
				if (resultSet.getString("dc_date") != null) {
					Date dt = resultSet.getDate("dc_date");
					DateFormat format = new SimpleDateFormat("dd-MMM-yyyy");
					String dte = "";
					dte = format.format(dt);
					dpStbInBulk.setDcDate(dte);
				} else {
					dpStbInBulk.setDcDate("");
				}
				if (resultSet.getString("accpt_date") != null) {
					Date dt = resultSet.getDate("accpt_date");
					DateFormat format = new SimpleDateFormat("dd-MMM-yyyy");
					String dte = "";
					dte = format.format(dt);
					dpStbInBulk.setAccept_date(dte);
				} else {
					dpStbInBulk.setAccept_date("");
				}
				if (resultSet.getString("FSE") != null) {
					dpStbInBulk.setFsc(resultSet.getString("FSE"));
				} else {
					dpStbInBulk.setFsc("");
				}
				if (resultSet.getString("RETAILER") != null) {
					dpStbInBulk.setRetailer(resultSet.getString("RETAILER"));
				} else {
					dpStbInBulk.setRetailer("");
				}
				if (resultSet.getString("value") != null) {
					/*dpStbInBulk.setInvoice_status(resultSet.getString("value"));

				} else {*/

					if (resultSet.getString("MARK_DAMAGED") != null) {
						if ("I".equals(resultSet.getString("MARK_DAMAGED"))) {
							if (resultSet.getString("id") != null) {
								if ("1".equals(resultSet.getString("id"))
										|| "2"
												.equals(resultSet
														.getString("id"))
										|| "3"
												.equals(resultSet
														.getString("id"))
										|| "4"
												.equals(resultSet
														.getString("id"))
										|| "7"
												.equals(resultSet
														.getString("id"))
										|| "8"
												.equals(resultSet
														.getString("id"))
										|| "9"
												.equals(resultSet
														.getString("id"))
										|| "10".equals(resultSet
												.getString("id"))
										|| "11".equals(resultSet
												.getString("id"))
										|| "12".equals(resultSet
												.getString("id"))
										|| "13".equals(resultSet
												.getString("id"))
										|| "14".equals(resultSet
												.getString("id"))) {
									dpStbInBulk
											.setInvoice_status("In Transit -- Pending In PO");
								} else if ("32".equals(resultSet
										.getString("id"))
										|| "34".equals(resultSet
												.getString("id"))) {
									dpStbInBulk
											.setInvoice_status("In Transit -- Pending TSM action(Short/Wrong)");
								} else if ("31".equals(resultSet
										.getString("id"))
										|| "33".equals(resultSet
												.getString("id"))) {
									dpStbInBulk
											.setInvoice_status("Serialized Stock -- Restricted");
								} else {
									dpStbInBulk.setInvoice_status("");
								}
							} else {
								dpStbInBulk.setInvoice_status("");
							}
						} else {
							if (resultSet.getString("STATUS_ID") != null) {
								if ("10".equals(resultSet
										.getString("STATUS_ID"))) {
									dpStbInBulk
											.setInvoice_status("Serialized Stock -- Unassigned Pending");
								} else if ("1".equals(resultSet
										.getString("STATUS_ID"))) {
									dpStbInBulk
											.setInvoice_status("Serialized Stock -- Unassigned Complete");
								} else if ("5".equals(resultSet
										.getString("STATUS_ID"))) {
									dpStbInBulk
											.setInvoice_status("Serialized Stock -- Restricted");
								} else if ("-1".equals(resultSet
										.getString("STATUS_ID"))) {
									dpStbInBulk
											.setInvoice_status("Returned To Warehouse");
								} else if ("3".equals(resultSet
										.getString("STATUS_ID"))) {
									dpStbInBulk
											.setInvoice_status("Not In Stock -- Assigned");
								} else if ("4".equals(resultSet
										.getString("STATUS_ID"))) {
									dpStbInBulk.setInvoice_status("Repaired");
								} else if ("11".equals(resultSet
										.getString("STATUS_ID"))) {
									dpStbInBulk
											.setInvoice_status("Returned as DOA");
								} else if ("12".equals(resultSet
										.getString("STATUS_ID"))) {
									dpStbInBulk
											.setInvoice_status("Returned as SWAP");
								} else if ("13".equals(resultSet
										.getString("STATUS_ID"))) {
									dpStbInBulk
											.setInvoice_status("Added By Warehouse");
								} else if ("14".equals(resultSet
										.getString("STATUS_ID"))) {
									dpStbInBulk
											.setInvoice_status(" Not In Stock -- Ok Stock Returned To WH");
								} else {
									dpStbInBulk.setInvoice_status(resultSet
											.getString("STATUS_ID"));
								}
							} else {
								dpStbInBulk.setInvoice_status("");
							}

						}
					} else {
						dpStbInBulk.setInvoice_status("");
					}
				}
				//logger.info("********************************out of Activation ");
				
				if (resultSet.getString("Activation") != null) {
					//logger.info("********************************in side Activation =="+resultSet.getString("Activation"));
					SimpleDateFormat dateFormat = new SimpleDateFormat(
							"dd/MM/yyyy");
					Date convertedDate = dateFormat.parse(resultSet
							.getString("Activation"));
					//System.out.println("********************************convertedDate for Activation "+ convertedDate);
					DateFormat format = new SimpleDateFormat("dd-MMM-yyyy");
					String dte = "";
					dte = format.format(convertedDate);
					dpStbInBulk.setActivationDate(dte);
				} else {
					dpStbInBulk.setActivationDate("");
				}
				if (resultSet.getString("CUSTOMER_ID") != null) {
					dpStbInBulk.setCustomer(resultSet.getString("CUSTOMER_ID"));
				} else {
					dpStbInBulk.setCustomer("");
				}

				assignSerialList.add(dpStbInBulk);
			}
			//logger
			//		.info("****************End  getAssignSerialNoList method********************** ");
		} catch (SQLException e) {
			try {
				connection.rollback();
			} catch (Exception ex) {
				ex.printStackTrace();
			}
			e.printStackTrace();

		} catch (Exception e) {
			try {
				connection.rollback();
			} catch (Exception ex) {
			}
			e.printStackTrace();
			logger.error("Exception occured while getAssignSerialNoList"
					+ "Exception Message: " + e.getMessage());

		} finally {
			try {
				DBConnectionManager.releaseResources(prepareStatement, resultSet);
				DBConnectionManager.releaseResources(connection);
			} catch (Exception e) {
			}
		}
		//logger
			//	.info("****************return assignSerialList********************** "
				//		+ assignSerialList);
		return assignSerialList;

	}

	
	/**
	 * METHOD getReserveSerialNoList
	 * @param dpStbInBulkDTO
	 * @return
	 * @throws DAOException
	 */
	public ArrayList getReserveSerialNoList(DPStbInBulkDTO dpStbInBulkDTO)
			throws DAOException {
		//logger
			//	.info("****************In getReserveSerialNoList method********************** ");
		ArrayList reserveSerialNoList = new ArrayList();
		PreparedStatement prepareStatement = null;
		ResultSet resultSet = null;
		PreparedStatement prepareStatementForREC = null;
		ResultSet resultSetForREC = null;

		PreparedStatement prepareStatementForRFD = null;
		ResultSet resultSetForRFD = null;
		
		DPStbInBulkDTO dpStbInBulk = null;
		Connection connection = null;

		String stbList = dpStbInBulkDTO.getSerial_no();
		//logger
			//	.info("****************In getReserveSerialNoList stbList********************** "
				//		+ stbList);
		try {
			connection = DBConnectionManager.getDBConnection();
			String query = "select si.SERIAL_NO_COLLECT, pm.PRODUCT_NAME AS stb_type, lm.LOGIN_NAME, ad.ACCOUNT_NAME, "
					+ "cm.CIRCLE_NAME, ic.INV_CHANGE_DATE as change_date, si.COLLECTION_DATE as COLLECTION_DATE, "
					+ "rcm.COLLECTION_NAME, dm.DEFECT_NAME, dcd.DC_NO, dch.CREATED_ON as DC_DATE, "
					+ "case dch.STATUS when 'SUCCESS' then 'Sent To Warehouse' when 'ERROR' then 'Defective Stock -- Pending DC Generation' "
					+ "when 'MOD' then 'DC Accepted with Modifications' when 'AIW' then 'Received by Warehouse' "
					+ "when 'AIWM' THEN 'Accept In Warehouse With Modification' when 'REJECT' THEN 'DC Rejected' "
					+ "else dch.STATUS end as DC_STATUS, "
					+ "CASE "
					+ "WHEN SI.STATUS = 'REC' then 'Defective Stock -- Pending For Receiving' "
					+ "WHEN SI.STATUS='COL' THEN 'Defective Stock -- Pending DC Generation' "
					+ "WHEN SI.STATUS='S2W' THEN 'In Transit -- Sent To WH DC' "
					+ "WHEN SI.STATUS='AIW' THEN 'Not in Stock -- Defective Stock Received at WH'  "
					+ "WHEN SI.STATUS='MSN' THEN 'Defective Stock -- Not Received at WH' "
					+ "WHEN SI.STATUS='ABW' THEN 'Not in Stock -- Defective Stock Received at WH' "
					+ "WHEN SI.STATUS='ERR' THEN 'Defective Stock -- Pending DC Generation' "
					+ "when dch.STATUS='DRAFT' and SI.STATUS='IDC' then 'In Incomplete DC' "
					+ "when dch.STATUS='CREATED' and SI.STATUS='IDC' then 'In Transit -- Ready for Dispatch DC' "
					+ "when dch.STATUS='INTRANSIT' and SI.STATUS='IDC' then 'In Transit -- Ready for Dispatch DC' "
					+ "ELSE si.STATUS END AS STB_STATUS "
					+ "from DP_PRODUCT_MASTER pm, VR_CIRCLE_MASTER cm, VR_LOGIN_MASTER lm, "
					+ "VR_ACCOUNT_DETAILS ad, DP_REV_COLLECTION_MST rcm, "
					+ "DP_REV_STOCK_INVENTORY si left outer join DP_REV_INVENTORY_CHANGE ic "
					+ "on si.SERIAL_NO_COLLECT=ic.DEFECTIVE_SR_NO and ic.DEFECTIVE_PRODUCT_ID=si.PRODUCT_ID "
					+ "and DATE(ic.CREATED_ON)<DATE(si.COLLECTION_DATE) AND ic.STATUS='COL' "
					+ "left outer join DP_REV_DELIVERY_CHALAN_DETAIL dcd on si.SERIAL_NO_COLLECT=dcd.SERIAL_NO  "
					+ "and si.CREATED_BY=dcd.DIST_ID and si.PRODUCT_ID=dcd.PRODUCT_ID  "
					+ "left outer join  DP_REV_DELIVERY_CHALAN_HDR dch on dcd.DC_NO=dch.DC_NO and dch.DC_TYPE='REVERSE'" +
					" left outer join DP_REV_DEFECT_MST dm on si.DEFECT_ID=dm.DEFECT_ID "
					+ "where cm.CIRCLE_ID=ad.CIRCLE_ID "
					+ "and ad.ACCOUNT_ID=lm.LOGIN_ID "
					+ "and ad.ACCOUNT_ID=si.CREATED_BY "
					+ "AND si.PRODUCT_ID = pm.PRODUCT_ID "
					+ "and rcm.COLLECTION_ID=si.COLLECTION_ID "
					+ "and SERIAL_NO_COLLECT in (" + stbList + ")"
					+ " order by SERIAL_NO_COLLECT,dch.CREATED_ON";

			String queryForREC = "select ic.DEFECTIVE_SR_NO, "
					+ "pm.PRODUCT_NAME AS stb_type, "
					+ "lm.LOGIN_NAME, "
					+ "ad.ACCOUNT_NAME, "
					+ "cm.CIRCLE_NAME, "
					+ "ic.INV_CHANGE_DATE as change_date, "
					+ "rcm.COLLECTION_NAME, "
					+ "dm.DEFECT_NAME, "
					+ "CASE "
					+ "WHEN ic.STATUS= 'REC' then 'Defective Stock -- Pending For Receiving' "
					+ "WHEN ic.STATUS='COL' THEN 'Defective Stock -- Pending DC Generation' "
					+ "WHEN ic.STATUS='S2W' THEN 'In Transit -- Sent To WH DC' "
					+ "WHEN ic.STATUS='AIW' THEN 'Not in Stock -- Defective Stock Received at WH' "
					+ "WHEN ic.STATUS='MSN' THEN 'Defective Stock -- Not Received at WH' "
					+ "WHEN ic.STATUS='ABW' THEN 'Not in Stock -- Defective Stock Received at WH' "
					+ "WHEN ic.STATUS='IDC' then 'In Incomplete DC' "
					+ "WHEN ic.STATUS='ERR' THEN 'Defective Stock -- Pending DC Generation' "
					+ "ELSE ic.STATUS END AS STB_STATUS "
					+ "from DP_PRODUCT_MASTER pm, VR_CIRCLE_MASTER cm, "
					+ "VR_LOGIN_MASTER lm, VR_ACCOUNT_DETAILS ad, "
					+ "DP_REV_COLLECTION_MST rcm, DP_REV_INVENTORY_CHANGE ic left outer join DP_REV_DEFECT_MST dm on ic.DEFECT_ID=dm.DEFECT_ID " +
					" where ic.COLLECTION_ID=char(rcm.COLLECTION_ID) and cm.CIRCLE_ID=ad.CIRCLE_ID "
					+ "and ic.DEFECTIVE_PRODUCT_ID=pm.PRODUCT_ID "
					+ "and ic.STATUS='REC' AND ad.ACCOUNT_ID=lm.LOGIN_ID "
					+ "and ad.ACCOUNT_ID=ic.NEW_DIST_D "
					+ "and DEFECTIVE_SR_NO in  (" + stbList + ")";

			String queryForRFD="select dcd.SERIAL_NO, pm.PRODUCT_NAME AS stb_type, lm.LOGIN_NAME, ad.ACCOUNT_NAME, "
					+" cm.CIRCLE_NAME, dcd.DC_NO, DPDTH.to_char(dch.CREATED_ON,'dd-MON-yyyy') as DC_DATE, " +
					" case dch.STATUS when 'SUCCESS' then 'Sent To Warehouse' when 'ERROR' then 'OK Stock -- Pending DC Generation'"
					+" when 'MOD' then 'DC Accepted with Modifications' when 'AIW' then 'Received by Warehouse'"
					+" when 'AIWM' THEN 'Accept In Warehouse With Modification' when 'REJECT' THEN 'DC Rejected'"
					+" else dch.STATUS end as DC_STATUS, "
					+" CASE "
					+" WHEN dcd.STATUS = 'REC' then 'OK Stock -- Pending For Receiving' "
					+" WHEN dcd.STATUS='COL' THEN 'OK Stock -- Pending DC Generation'"
					+" WHEN dcd.STATUS='S2W' THEN 'In Transit -- Sent To WH DC' "
					+" WHEN dcd.STATUS='AIW' THEN 'Not in Stock -- OK Stock Received at WH'"
					+" WHEN dcd.STATUS='MSN' THEN 'OK Stock -- Not Received at WH'"
					+" WHEN dcd.STATUS='ABW' THEN 'Not in Stock -- OK Stock Received at WH'"
					+" WHEN dcd.STATUS='ERR' THEN 'OK Stock -- Pending DC Generation' "
					+" when dch.STATUS='DRAFT'  then 'In Incomplete DC'"
					+" when dch.STATUS='CREATED'  then 'In Transit -- Ready for Dispatch DC'"
					+" when dch.STATUS='INTRANSIT' then 'In Transit -- Ready for Dispatch DC' "
					+" ELSE dcd.STATUS END AS STB_STATUS "
					+" from DP_PRODUCT_MASTER pm, VR_CIRCLE_MASTER cm, VR_LOGIN_MASTER lm, "
					+" VR_ACCOUNT_DETAILS ad, DP_REV_DELIVERY_CHALAN_DETAIL dcd, DP_REV_DELIVERY_CHALAN_HDR dch"
					+"  where dcd.PRODUCT_ID=pm.PRODUCT_ID and cm.CIRCLE_ID=dcd.CIRCLE_ID "
					+" and ad.ACCOUNT_ID=lm.LOGIN_ID and ad.ACCOUNT_ID=dcd.DIST_ID"
					+" and dcd.SERIAL_NO in  (" + stbList + ") and dcd.DC_NO=dch.DC_NO and dch.DC_TYPE='FRESH'";
			//logger.info("****************query********************** " + query);
			prepareStatement = connection.prepareStatement(query);
			resultSet = prepareStatement.executeQuery();

			while (resultSet.next()) {
				dpStbInBulk = new DPStbInBulkDTO();

				if (resultSet.getString("SERIAL_NO_COLLECT") != null) {
					dpStbInBulk.setSerial_no(resultSet
							.getString("SERIAL_NO_COLLECT"));

				} else {
					dpStbInBulk.setSerial_no("");
				}
				if (resultSet.getString("stb_type") != null) {
					dpStbInBulk.setRev_product_name(resultSet
							.getString("stb_type"));
				} else {
					dpStbInBulk.setRev_product_name("");
				}

				if (resultSet.getString("LOGIN_NAME") != null) {
					dpStbInBulk.setDistIdRev(resultSet.getString("LOGIN_NAME"));
				} else {
					dpStbInBulk.setDistIdRev("");
				}
				if (resultSet.getString("ACCOUNT_NAME") != null) {
					dpStbInBulk.setDistributorRev(resultSet
							.getString("ACCOUNT_NAME"));
				} else {
					dpStbInBulk.setDistributorRev("");
				}
				if (resultSet.getString("CIRCLE_NAME") != null) {
					dpStbInBulk
							.setCircleRev(resultSet.getString("CIRCLE_NAME"));
				} else {
					dpStbInBulk.setCircleRev("");
				}
				if (resultSet.getString("change_date") != null) {
					dpStbInBulk.setInv_change_date(resultSet
							.getString("change_date"));
				} else {
					dpStbInBulk.setInv_change_date("");
				}
				if (resultSet.getString("COLLECTION_DATE") != null) {
					dpStbInBulk.setCollection_date(resultSet
							.getString("COLLECTION_DATE"));
				} else {
					dpStbInBulk.setCollection_date("");
				}
				if (resultSet.getString("COLLECTION_NAME") != null) {
					dpStbInBulk.setInv_change_type(resultSet
							.getString("COLLECTION_NAME"));
				} else {
					dpStbInBulk.setInv_change_type("");
				}
				if (resultSet.getString("DEFECT_NAME") != null) {
					dpStbInBulk.setDefect_type(resultSet
							.getString("DEFECT_NAME"));
				} else {
					dpStbInBulk.setDefect_type("");
				}
				if (resultSet.getString("DC_NO") != null) {
					dpStbInBulk.setRev_dcno(resultSet.getString("DC_NO"));
				} else {
					dpStbInBulk.setRev_dcno("");
				}
				if (resultSet.getString("DC_DATE") != null) {
					Date dt = resultSet.getDate("DC_DATE");
					DateFormat format = new SimpleDateFormat("dd-MMM-yyyy");
					String dte = "";
					dte = format.format(dt);
					dpStbInBulk.setRev_dc_date(dte);
				} else {
					dpStbInBulk.setRev_dc_date("");
				}
				if (resultSet.getString("DC_STATUS") != null) {
					dpStbInBulk.setRev_dc_status(resultSet
							.getString("DC_STATUS"));
				} else {
					dpStbInBulk.setRev_dc_status("");
				}
				if (resultSet.getString("STB_STATUS") != null) {
					dpStbInBulk.setRev_stock_in_status(resultSet
							.getString("STB_STATUS"));
				} else {
					dpStbInBulk.setRev_stock_in_status("");
				}
				dpStbInBulk.setReverseDCType("REVERSE");
				reserveSerialNoList.add(dpStbInBulk);
			}

			//logger.info("****************query********************** "
			//		+ queryForREC);
			prepareStatementForREC = connection.prepareStatement(queryForREC);
			resultSetForREC = prepareStatementForREC.executeQuery();

			while (resultSetForREC.next()) {
				dpStbInBulk = new DPStbInBulkDTO();

				if (resultSetForREC.getString("DEFECTIVE_SR_NO") != null) {
					dpStbInBulk.setSerial_no(resultSetForREC
							.getString("DEFECTIVE_SR_NO"));

				} else {
					dpStbInBulk.setSerial_no("");
				}
				if (resultSetForREC.getString("stb_type") != null) {
					dpStbInBulk.setRev_product_name(resultSetForREC
							.getString("stb_type"));
				} else {
					dpStbInBulk.setRev_product_name("");
				}

				if (resultSetForREC.getString("LOGIN_NAME") != null) {
					dpStbInBulk.setDistIdRev(resultSetForREC
							.getString("LOGIN_NAME"));
				} else {
					dpStbInBulk.setDistIdRev("");
				}
				if (resultSetForREC.getString("ACCOUNT_NAME") != null) {
					dpStbInBulk.setDistributorRev(resultSetForREC
							.getString("ACCOUNT_NAME"));
				} else {
					dpStbInBulk.setDistributorRev("");
				}
				if (resultSetForREC.getString("CIRCLE_NAME") != null) {
					dpStbInBulk.setCircleRev(resultSetForREC
							.getString("CIRCLE_NAME"));
				} else {
					dpStbInBulk.setCircleRev("");
				}
				if (resultSetForREC.getString("change_date") != null) {
					dpStbInBulk.setInv_change_date(resultSetForREC
							.getString("change_date"));
				} else {
					dpStbInBulk.setInv_change_date("");
				}
				dpStbInBulk.setCollection_date("");

				if (resultSetForREC.getString("COLLECTION_NAME") != null) {
					dpStbInBulk.setInv_change_type(resultSetForREC
							.getString("COLLECTION_NAME"));
				} else {
					dpStbInBulk.setInv_change_type("");
				}
				if (resultSetForREC.getString("DEFECT_NAME") != null) {
					dpStbInBulk.setDefect_type(resultSetForREC
							.getString("DEFECT_NAME"));
				} else {
					dpStbInBulk.setDefect_type("");
				}

				dpStbInBulk.setRev_dcno("");

				dpStbInBulk.setRev_dc_date("");

				dpStbInBulk.setRev_dc_status("");

				if (resultSetForREC.getString("STB_STATUS") != null) {
					dpStbInBulk.setRev_stock_in_status(resultSetForREC
							.getString("STB_STATUS"));
				} else {
					dpStbInBulk.setRev_stock_in_status("");
				}
				dpStbInBulk.setReverseDCType("REVERSE");
				reserveSerialNoList.add(dpStbInBulk);
			}

			
			//logger.info("****************query for RFD********************** "+ queryForRFD);
			prepareStatementForRFD = connection.prepareStatement(queryForRFD);
			resultSetForRFD = prepareStatementForRFD.executeQuery();

			/*
			 * dcd.SERIAL_NO, pm.PRODUCT_NAME AS stb_type, lm.LOGIN_NAME, ad.ACCOUNT_NAME, "
					+" cm.CIRCLE_NAME, dcd.DC_NO, dch.CREATED_ON as DC_DATE,
			 */
			while (resultSetForRFD.next()) {
				dpStbInBulk = new DPStbInBulkDTO();

				if (resultSetForRFD.getString("SERIAL_NO") != null) 
				{
					dpStbInBulk.setSerial_no(resultSetForRFD.getString("SERIAL_NO"));
				} else dpStbInBulk.setSerial_no("");
				//logger.info("****************Setting Data of RFD********************** "+ resultSetForRFD.getString("SERIAL_NO"));
				
				if (resultSetForRFD.getString("stb_type") != null) {
					dpStbInBulk.setRev_product_name(resultSetForRFD.getString("stb_type"));
				} else 
					dpStbInBulk.setRev_product_name("");
				
				if (resultSetForRFD.getString("LOGIN_NAME") != null) {
					dpStbInBulk.setDistIdRev(resultSetForRFD.getString("LOGIN_NAME"));
				} else 
					dpStbInBulk.setDistIdRev("");
				
				if (resultSetForRFD.getString("ACCOUNT_NAME") != null) {
					dpStbInBulk.setDistributorRev(resultSetForRFD.getString("ACCOUNT_NAME"));
				} else {
					dpStbInBulk.setDistributorRev("");
				}
				if (resultSetForRFD.getString("CIRCLE_NAME") != null) {
					dpStbInBulk.setCircleRev(resultSetForRFD
							.getString("CIRCLE_NAME"));
				} else {
					dpStbInBulk.setCircleRev("");
				}

				if (resultSetForRFD.getString("DC_NO") != null) {
					dpStbInBulk.setRev_dcno(resultSetForRFD.getString("DC_NO"));
				} else {
					dpStbInBulk.setRev_dcno("");
				}
				if (resultSetForRFD.getString("DC_DATE") != null) {
/*					Date dt = resultSetForRFD.getDate("DC_DATE");
					DateFormat format = new SimpleDateFormat("dd-MMM-yyyy");
					String dte = "";
					dte = format.format(dt);
*/					dpStbInBulk.setRev_dc_date(resultSetForRFD.getString("DC_DATE"));
				} else {
					dpStbInBulk.setRev_dc_date("");
				}
				if (resultSetForRFD.getString("DC_STATUS") != null) {
					dpStbInBulk.setRev_dc_status(resultSetForRFD.getString("DC_STATUS"));
				} else {
					dpStbInBulk.setRev_dc_status("");
				}
				if (resultSetForRFD.getString("STB_STATUS") != null) {
					dpStbInBulk.setRev_stock_in_status(resultSetForRFD
							.getString("STB_STATUS"));
				} else {
					dpStbInBulk.setRev_stock_in_status("");
				}
				dpStbInBulk.setReverseDCType("FRESH");
				reserveSerialNoList.add(dpStbInBulk);
			}

		} catch (SQLException e) {
			try {
				connection.rollback();
			} catch (Exception ex) {
			}
			e.printStackTrace();
		} catch (Exception e) {
			try {
				connection.rollback();
			} catch (Exception ex) {
			}
			e.printStackTrace();
			logger.error("Exception occured while getReserveSerialNoList."
					+ "Exception Message: " + e.getMessage());
		} finally {
			try {
				DBConnectionManager.releaseResources(prepareStatement, resultSet);
				DBConnectionManager.releaseResources(prepareStatementForREC, resultSetForREC);
				DBConnectionManager.releaseResources(prepareStatementForRFD, resultSetForRFD);
				DBConnectionManager.releaseResources(connection);
			
			} catch (Exception e) {
			}
		}
		return reserveSerialNoList;

	}

	
	/**
	 * 
	 * @param dpStbInBulkDTO
	 * @return
	 * @throws DAOException
	 */
	public ArrayList getReserveSerialNoListHist(DPStbInBulkDTO dpStbInBulkDTO)
			throws DAOException {
		//logger
			//	.info("****************In getReserveSerialNoListHist method********************** ");

		ArrayList reserveSerialNoList = new ArrayList();
		PreparedStatement prepareStatement = null;
		ResultSet resultSet = null;

		DPStbInBulkDTO dpStbInBulk = null;
		Connection connection = null;

		String stbList = dpStbInBulkDTO.getSerial_no();
		//logger
			//	.info("****************In getReserveSerialNoListHist stbList********************** "
				//		+ stbList);
		try {
			connection = DBConnectionManager.getDBConnection();
			String query = "select si.SERIAL_NO_COLLECT, pm.PRODUCT_NAME AS stb_type, lm.LOGIN_NAME, ad.ACCOUNT_NAME, cm.CIRCLE_NAME, ic.INV_CHANGE_DATE as change_date," +
					" si.COLLECTION_DATE as COLLECTION_DATE, rcm.COLLECTION_NAME, dm.DEFECT_NAME, dcd.DC_NO, dch.CREATED_ON as DC_DATE," +
					" case dch.STATUS when 'SUCCESS' then 'Sent To Warehouse' when 'ERROR' then 'Defective Stock -- Pending DC Generation'" +
					" when 'MOD' then 'DC Accepted with Modifications' when 'AIW' then 'Received by Warehouse'" +
					" when 'REJECT' THEN 'DC Rejected' when 'AIWM' THEN 'Accept In Warehouse With Modification'" +
					" else dch.STATUS end as DC_STATUS, CASE WHEN SI.STATUS = 'REC' then 'Defective Stock -- Pending For Receiving'" +
					" WHEN SI.STATUS='COL' THEN 'Defective Stock -- Pending DC Generation' WHEN SI.STATUS='S2W' THEN 'In Transit -- Sent To WH DC'" +
					" WHEN SI.STATUS='AIW' THEN 'Not in Stock -- Defective Stock Received at WH' WHEN SI.STATUS='MSN'" +
					" THEN 'Defective Stock -- Not Received at WH' WHEN SI.STATUS='ABW' THEN 'Not in Stock -- Defective Stock Received at WH'" +
					" WHEN SI.STATUS='ERR' THEN 'Defective Stock -- Pending DC Generation' when dch.STATUS='DRAFT' and SI.STATUS='IDC' then 'In Incomplete DC'" +
					" WHEN SI.STATUS='-1' THEN 'Not in Stock - Flushed out by Botree' WHEN SI.STATUS='-2' THEN 'Not in Stock - Flushed out by DP' "+
					" when dch.STATUS='CREATED' and SI.STATUS='IDC' then 'In Transit -- Ready for Dispatch DC' when dch.STATUS='INTRANSIT' and SI.STATUS='IDC' then 'In Transit -- Ready for Dispatch DC'" +
					" ELSE si.STATUS END AS STB_STATUS, si.FLUSHOUT_FLAG" +
					" from VR_CIRCLE_MASTER cm, DP_PRODUCT_MASTER pm, VR_LOGIN_MASTER lm, VR_ACCOUNT_DETAILS ad, DP_REV_COLLECTION_MST rcm," +
					" DP_REV_DEFECT_MST dm, DP_REV_STOCK_INVENTORY_HIST si left outer join DP_REV_INVENTORY_CHANGE_HIST ic" +
					" on si.SERIAL_NO_COLLECT=ic.DEFECTIVE_SR_NO and ic.DEFECTIVE_PRODUCT_ID=si.PRODUCT_ID and DATE(ic.CREATED_ON)<DATE(si.COLLECTION_DATE)" +
					" left outer join DP_REV_DELIVERY_CHALAN_DETAIL dcd on si.SERIAL_NO_COLLECT=dcd.SERIAL_NO" +
					" and si.CREATED_BY=dcd.DIST_ID and si.PRODUCT_ID=dcd.PRODUCT_ID  left outer join  DP_REV_DELIVERY_CHALAN_HDR dch on dcd.DC_NO=dch.DC_NO" +
					" where cm.CIRCLE_ID=ad.CIRCLE_ID AND si.PRODUCT_ID = pm.PRODUCT_ID and ad.ACCOUNT_ID=lm.LOGIN_ID AND si.PRODUCT_ID = pm.PRODUCT_ID" +
					" and ad.ACCOUNT_ID=si.CREATED_BY and rcm.COLLECTION_ID=si.COLLECTION_ID and si.DEFECT_ID=dm.DEFECT_ID" +
					" and si.ARCHIVED_DATE = (select  max(ARCHIVED_DATE) from DP_REV_STOCK_INVENTORY_HIST a" +
					" where a.SERIAL_NO_COLLECT=si.SERIAL_NO_COLLECT  ) and SERIAL_NO_COLLECT in (" + stbList + ")" +
					" union" +
					" select si.SERIAL_NO_COLLECT, pm.PRODUCT_NAME AS stb_type, lm.LOGIN_NAME, ad.ACCOUNT_NAME, cm.CIRCLE_NAME, ic.INV_CHANGE_DATE as change_date," +
					" si.COLLECTION_DATE as COLLECTION_DATE, rcm.COLLECTION_NAME, dm.DEFECT_NAME, dcd.DC_NO, dch.CREATED_ON as DC_DATE," +
					" case dch.STATUS when 'SUCCESS' then 'Sent To Warehouse' when 'ERROR' then 'Defective Stock -- Pending DC Generation'" +
					" when 'MOD' then 'DC Accepted with Modifications' when 'AIW' then 'Received by Warehouse'" +
					" when 'REJECT' THEN 'DC Rejected' when 'AIWM' THEN 'Accept In Warehouse With Modification'" +
					" else dch.STATUS end as DC_STATUS, CASE WHEN SI.STATUS = 'REC' then 'Defective Stock -- Pending For Receiving'" +
					" WHEN SI.STATUS='COL' THEN 'Defective Stock -- Pending DC Generation' WHEN SI.STATUS='S2W' THEN 'In Transit -- Sent To WH DC'" +
					" WHEN SI.STATUS='AIW' THEN 'Not in Stock -- Defective Stock Received at WH' WHEN SI.STATUS='MSN'" +
					" THEN 'Defective Stock -- Not Received at WH' WHEN SI.STATUS='ABW' THEN 'Not in Stock -- Defective Stock Received at WH'" +
					" WHEN SI.STATUS='ERR' THEN 'Defective Stock -- Pending DC Generation' when dch.STATUS='DRAFT' and SI.STATUS='IDC' then 'In Incomplete DC'" +
					" WHEN SI.STATUS='-1' THEN 'Not in Stock - Flushed out by Botree' WHEN SI.STATUS='-2' THEN 'Not in Stock - Flushed out by DP' "+
					" when dch.STATUS='CREATED' and SI.STATUS='IDC' then 'In Transit -- Ready for Dispatch DC' when dch.STATUS='INTRANSIT' and SI.STATUS='IDC' then 'In Transit -- Ready for Dispatch DC'" +
					" ELSE si.STATUS END AS STB_STATUS, si.FLUSHOUT_FLAG" +
					" from VR_CIRCLE_MASTER cm, DP_PRODUCT_MASTER pm, VR_LOGIN_MASTER lm, VR_ACCOUNT_DETAILS ad, DP_REV_COLLECTION_MST rcm," +
					" DP_REV_DEFECT_MST dm, DP_REV_STOCK_INVENTORY_HIST si left outer join DP_REV_INVENTORY_CHANGE ic" +
					" on si.SERIAL_NO_COLLECT=ic.DEFECTIVE_SR_NO and ic.DEFECTIVE_PRODUCT_ID=si.PRODUCT_ID and DATE(ic.CREATED_ON)<=DATE(si.COLLECTION_DATE)" +
					" AND ic.STATUS in ('COL','S2W') left outer join DP_REV_DELIVERY_CHALAN_DETAIL dcd on si.SERIAL_NO_COLLECT=dcd.SERIAL_NO" +
					" and si.CREATED_BY=dcd.DIST_ID and si.PRODUCT_ID=dcd.PRODUCT_ID  left outer join  DP_REV_DELIVERY_CHALAN_HDR dch on dcd.DC_NO=dch.DC_NO" +
					" where cm.CIRCLE_ID=ad.CIRCLE_ID AND si.PRODUCT_ID = pm.PRODUCT_ID and ad.ACCOUNT_ID=lm.LOGIN_ID AND si.PRODUCT_ID = pm.PRODUCT_ID" +
					" and ad.ACCOUNT_ID=si.CREATED_BY and rcm.COLLECTION_ID=si.COLLECTION_ID and si.DEFECT_ID=dm.DEFECT_ID" +
					" and si.ARCHIVED_DATE = (select  max(ARCHIVED_DATE) from DP_REV_STOCK_INVENTORY_HIST a" +
					" where a.SERIAL_NO_COLLECT=si.SERIAL_NO_COLLECT  ) and SERIAL_NO_COLLECT in (" + stbList + ")" +
					" order by SERIAL_NO_COLLECT, CHANGE_DATE ";

			//logger
				//	.info("****************In getReserveSerialNoListHist query********************** "
				//			+ query);
			prepareStatement = connection.prepareStatement(query);
			resultSet = prepareStatement.executeQuery();
			while (resultSet.next()) {
				dpStbInBulk = new DPStbInBulkDTO();

				if (resultSet.getString("SERIAL_NO_COLLECT") != null) {
					dpStbInBulk.setSerial_no(resultSet
							.getString("SERIAL_NO_COLLECT"));

				} else {
					dpStbInBulk.setSerial_no("");
				}
				if (resultSet.getString("stb_type") != null) {
					dpStbInBulk.setRev_product_name(resultSet
							.getString("stb_type"));
				} else {
					dpStbInBulk.setRev_product_name("");
				}

				if (resultSet.getString("LOGIN_NAME") != null) {
					dpStbInBulk.setDistIdRev(resultSet.getString("LOGIN_NAME"));
				} else {
					dpStbInBulk.setDistIdRev("");
				}
				if (resultSet.getString("ACCOUNT_NAME") != null) {
					dpStbInBulk.setDistributorRev(resultSet
							.getString("ACCOUNT_NAME"));
				} else {
					dpStbInBulk.setDistributorRev("");
				}
				if (resultSet.getString("CIRCLE_NAME") != null) {
					dpStbInBulk
							.setCircleRev(resultSet.getString("CIRCLE_NAME"));
				} else {
					dpStbInBulk.setCircleRev("");
				}
				if (resultSet.getString("change_date") != null) {
					dpStbInBulk.setInv_change_date(resultSet
							.getString("change_date"));
				} else {
					dpStbInBulk.setInv_change_date("");
				}
				if (resultSet.getString("COLLECTION_DATE") != null) {
					dpStbInBulk.setCollection_date(resultSet
							.getString("COLLECTION_DATE"));
				} else {
					dpStbInBulk.setCollection_date("");
				}
				if (resultSet.getString("COLLECTION_NAME") != null) {
					dpStbInBulk.setInv_change_type(resultSet
							.getString("COLLECTION_NAME"));
				} else {
					dpStbInBulk.setInv_change_type("");
				}
				if (resultSet.getString("DEFECT_NAME") != null) {
					dpStbInBulk.setDefect_type(resultSet
							.getString("DEFECT_NAME"));
				} else {
					dpStbInBulk.setDefect_type("");
				}
				if (resultSet.getString("DC_NO") != null) {
					dpStbInBulk.setRev_dcno(resultSet.getString("DC_NO"));
				} else {
					dpStbInBulk.setRev_dcno("");
				}
				if (resultSet.getString("DC_DATE") != null) {
					Date dt = resultSet.getDate("DC_DATE");
					DateFormat format = new SimpleDateFormat("dd-MMM-yyyy");
					String dte = "";
					dte = format.format(dt);
					dpStbInBulk.setRev_dc_date(dte);
				} else {
					dpStbInBulk.setRev_dc_date("");
				}
				if (resultSet.getString("DC_STATUS") != null) {
					dpStbInBulk.setRev_dc_status(resultSet
							.getString("DC_STATUS"));
				} else {
					dpStbInBulk.setRev_dc_status("");
				}
				if (resultSet.getString("STB_STATUS") != null) {
					dpStbInBulk.setRev_stock_in_status(resultSet
							.getString("STB_STATUS"));
				} else {
					dpStbInBulk.setRev_stock_in_status("");
				}

				if(resultSet.getString("FLUSHOUT_FLAG")!=null && !"0".equals(resultSet.getString("FLUSHOUT_FLAG")))
				{
					if("-1".equals(resultSet.getString("FLUSHOUT_FLAG")) )
						dpStbInBulk.setStatus("Not in Stock - Flushed out by Botree");
					else if("-2".equals(resultSet.getString("FLUSHOUT_FLAG")))
						dpStbInBulk.setStatus("Not in Stock - Flushed out by DP");								
					
				}
				reserveSerialNoList.add(dpStbInBulk);
			}
			//logger
			//		.info("****************End getReserveSerialNoListHist ********************** ");
		} catch (SQLException e) {
			try {
				connection.rollback();
			} catch (Exception ex) {
			}
			e.printStackTrace();
		} catch (Exception e) {
			try {
				connection.rollback();
			} catch (Exception ex) {
			}
			e.printStackTrace();
			logger.error("Exception occured while getReserveSerialNoListHist."
					+ "Exception Message: " + e.getMessage());
		} finally {
			try {
				DBConnectionManager.releaseResources(prepareStatement, resultSet);
				DBConnectionManager.releaseResources(connection);
			} catch (Exception e) {
			}
		}
		logger
				.info("****************retrun getReserveSerialNoListHist ********************** "
						+ reserveSerialNoList);
		return reserveSerialNoList;

	}

	/**
	 * 
	 * @param dpStbInBulkDTO
	 * @return
	 * @throws DAOException
	 */
	public ArrayList getChurnSerialNoList(DPStbInBulkDTO dpStbInBulkDTO)
			throws DAOException {
		//logger
			//	.info("****************In getChurnSerialNoList method********************** ");

		ArrayList reserveSerialNoList = new ArrayList();
		PreparedStatement prepareStatement = null;
		ResultSet resultSet = null;

		DPStbInBulkDTO dpStbInBulk = null;
		Connection connection = null;

		String stbList = dpStbInBulkDTO.getSerial_no();
		//logger
			//	.info("****************In getChurnSerialNoList stbList********************** "
				//		+ stbList);
		try {
			connection = DBConnectionManager.getDBConnection();
			String query = "select si.SERIAL_NUMBER, pm.PRODUCT_NAME AS stb_type,lm.LOGIN_NAME, ad.ACCOUNT_NAME, cm.CIRCLE_NAME, "
					+ "DPDTH.to_char(si.CREATED_ON,'yyyy-mm-dd') as COLLECTION_DATE,dcd.DC_NO, dch.DC_DATE as DC_DATE, "
					+ "case dch.DC_STATUS when 'SUCCESS' then 'Sent To Warehouse' "
					+ "when 'ERROR' then 'Stock -- Pending DC Generation' "
					+ "when 'MOD' then 'DC Accepted with Modifications' "
					+ "when 'AIW' then 'Received by Warehouse' "
					+ "when 'REJECT' THEN 'DC Rejected' "
					+ "when 'AIWM' THEN 'Accept In Warehouse With Modification' "
					+ "else dch.DC_STATUS end as DC_STATUS, "
					+ "CASE "
					+ "WHEN SI.STATUS = 'REC' then 'Stock -- Pending For Receiving' "
					+ "WHEN SI.STATUS='COL' THEN 'Stock -- Pending DC Generation' "
					+ "WHEN SI.STATUS='S2W' THEN 'In Transit -- Sent To WH DC' "
					+ "WHEN SI.STATUS='AIW' THEN 'Not in Stock -- Stock Received at WH' "
					+ "WHEN SI.STATUS='MSN' THEN 'Stock -- Not Received at WH' "
					+ "WHEN SI.STATUS='ABW' THEN 'Not in Stock -- Stock Received at WH' "
					+ "WHEN SI.STATUS='ERR' THEN 'Stock -- Pending DC Generation' "
					+ "when dch.DC_STATUS='DRAFT' and SI.STATUS='IDC' then 'In Incomplete DC' "
					+ "when dch.DC_STATUS='CREATED' and SI.STATUS='IDC' then 'In Transit -- Ready for Dispatch DC' "
					+ "when dch.DC_STATUS='INTRANSIT' and SI.STATUS='IDC' then 'In Transit -- Ready for Dispatch DC' "
					+ "ELSE si.STATUS END AS STB_STATUS "
					+ "from "
					+ "DP_PRODUCT_MASTER pm, "
					+ "VR_CIRCLE_MASTER cm, "
					+ "VR_LOGIN_MASTER lm, "
					+ "VR_ACCOUNT_DETAILS ad, "
					+ "DP_REV_CHURN_INVENTORY si left outer join DP_REV_CHURN_DC_DETAIL dcd "
					+ "on si.SERIAL_NUMBER=dcd.SERIAL_NUMBER "
					+ "and si.CREATED_BY=dcd.DIST_ID and si.PRODUCT_ID=dcd.PRODUCT_ID "
					+ "and si.REQ_ID =dcd.REQ_ID "
					+ "left outer join  DP_REV_CHURN_DC_HEADER dch on dcd.DC_NO=dch.DC_NO "
					+ "where cm.CIRCLE_ID=ad.CIRCLE_ID "
					+ "and ad.ACCOUNT_ID=lm.LOGIN_ID "
					+ "and ad.ACCOUNT_ID=si.CREATED_BY "
					+ "AND si.PRODUCT_ID = pm.PRODUCT_ID "
					+ "and si.SERIAL_NUMBER in ("
					+ stbList
					+ ")"
					+ " order by SERIAL_NUMBER,dch.DC_DATE";

			//logger
			//		.info("****************In getChurnSerialNoList query********************** "
				//			+ query);
			prepareStatement = connection.prepareStatement(query);
			resultSet = prepareStatement.executeQuery();
			while (resultSet.next()) {
				dpStbInBulk = new DPStbInBulkDTO();

				if (resultSet.getString("SERIAL_NUMBER") != null) {
					dpStbInBulk.setSerial_no(resultSet
							.getString("SERIAL_NUMBER"));

				} else {
					dpStbInBulk.setSerial_no("");
				}
				if (resultSet.getString("stb_type") != null) {
					dpStbInBulk.setRev_product_name(resultSet
							.getString("stb_type"));
				} else {
					dpStbInBulk.setRev_product_name("");
				}

				if (resultSet.getString("LOGIN_NAME") != null) {
					dpStbInBulk.setDistIdRev(resultSet.getString("LOGIN_NAME"));
				} else {
					dpStbInBulk.setDistIdRev("");
				}
				if (resultSet.getString("ACCOUNT_NAME") != null) {
					dpStbInBulk.setDistributorRev(resultSet
							.getString("ACCOUNT_NAME"));
				} else {
					dpStbInBulk.setDistributorRev("");
				}
				if (resultSet.getString("CIRCLE_NAME") != null) {
					dpStbInBulk
							.setCircleRev(resultSet.getString("CIRCLE_NAME"));
				} else {
					dpStbInBulk.setCircleRev("");
				}
				dpStbInBulk.setInv_change_date("");

				if (resultSet.getString("COLLECTION_DATE") != null) {
					dpStbInBulk.setCollection_date(resultSet
							.getString("COLLECTION_DATE"));
				} else {
					dpStbInBulk.setCollection_date("");
				}
				dpStbInBulk.setInv_change_type("");

				dpStbInBulk.setDefect_type("");

				if (resultSet.getString("DC_NO") != null) {
					dpStbInBulk.setRev_dcno(resultSet.getString("DC_NO"));
				} else {
					dpStbInBulk.setRev_dcno("");
				}
				if (resultSet.getString("DC_DATE") != null) {
					Date dt = resultSet.getDate("DC_DATE");
					DateFormat format = new SimpleDateFormat("dd-MMM-yyyy");
					String dte = "";
					dte = format.format(dt);
					dpStbInBulk.setRev_dc_date(dte);
				} else {
					dpStbInBulk.setRev_dc_date("");
				}
				if (resultSet.getString("DC_STATUS") != null) {
					dpStbInBulk.setRev_dc_status(resultSet
							.getString("DC_STATUS"));
				} else {
					dpStbInBulk.setRev_dc_status("");
				}
				if (resultSet.getString("STB_STATUS") != null) {
					dpStbInBulk.setRev_stock_in_status(resultSet
							.getString("STB_STATUS"));
				} else {
					dpStbInBulk.setRev_stock_in_status("");
				}

				reserveSerialNoList.add(dpStbInBulk);
			}
			//logger
			//		.info("****************End getChurnSerialNoList ********************** ");
		} catch (SQLException e) {
			try {
				connection.rollback();
			} catch (Exception ex) {
			}
			e.printStackTrace();
		} catch (Exception e) {
			try {
				connection.rollback();
			} catch (Exception ex) {
			}
			e.printStackTrace();
			logger.error("Exception occured while getChurnSerialNoList."
					+ "Exception Message: " + e.getMessage());
		} finally {
			try {
				DBConnectionManager.releaseResources(prepareStatement, resultSet);
				DBConnectionManager.releaseResources(connection);

			} catch (Exception e) {
			}
		}
		//logger
			//	.info("****************retrun getChurnSerialNoList ********************** "
				//		+ reserveSerialNoList);
		return reserveSerialNoList;

	}

	/**
	 * 
	 * @param dpStbInBulkDTO
	 * @return
	 * @throws DAOException
	 */
	public ArrayList getChurnSerialNoListHist(DPStbInBulkDTO dpStbInBulkDTO)
			throws DAOException {
		//logger
			//	.info("****************In getChurnSerialNoListHist method********************** ");

		ArrayList reserveSerialNoList = new ArrayList();
		PreparedStatement prepareStatement = null;
		ResultSet resultSet = null;

		DPStbInBulkDTO dpStbInBulk = null;
		Connection connection = null;

		String stbList = dpStbInBulkDTO.getSerial_no();
		//logger
			//	.info("****************In getChurnSerialNoListHist stbList********************** "
				//		+ stbList);
		try {
			connection = DBConnectionManager.getDBConnection();
			String query = "select si.SERIAL_NUMBER, pm.PRODUCT_NAME AS stb_type,lm.LOGIN_NAME, ad.ACCOUNT_NAME, cm.CIRCLE_NAME, "
					+ "DPDTH.to_char(si.CREATED_ON,'yyyy-mm-dd') as COLLECTION_DATE,dcd.DC_NO, dch.DC_DATE as DC_DATE, "
					+ "case dch.DC_STATUS when 'SUCCESS' then 'Sent To Warehouse' "
					+ "when 'ERROR' then 'Stock -- Pending DC Generation' "
					+ "when 'MOD' then 'DC Accepted with Modifications' "
					+ "when 'AIW' then 'Received by Warehouse' "
					+ "when 'REJECT' THEN 'DC Rejected' "
					+ "when 'AIWM' THEN 'Accept In Warehouse With Modification' "
					+ "else dch.DC_STATUS end as DC_STATUS, "
					+ "CASE "
					+ "WHEN SI.STATUS = 'REC' then 'Stock -- Pending For Receiving' "
					+ "WHEN SI.STATUS='COL' THEN 'Stock -- Pending DC Generation' "
					+ "WHEN SI.STATUS='S2W' THEN 'In Transit -- Sent To WH DC' "
					+ "WHEN SI.STATUS='AIW' THEN 'Not in Stock -- Stock Received at WH' "
					+ "WHEN SI.STATUS='MSN' THEN 'Stock -- Not Received at WH' "
					+ "WHEN SI.STATUS='ABW' THEN 'Not in Stock -- Stock Received at WH' "
					+ "WHEN SI.STATUS='ERR' THEN 'Stock -- Pending DC Generation' "
					+ "WHEN SI.STATUS='-1' THEN 'Not in Stock -- Flushed out by Botree' "
					+ "WHEN SI.STATUS='-2' THEN 'Not in DP -- Flushed out by DP' "
					+ "when dch.DC_STATUS='DRAFT' and SI.STATUS='IDC' then 'In Incomplete DC' "
					+ "when dch.DC_STATUS='CREATED' and SI.STATUS='IDC' then 'In Transit -- Ready for Dispatch DC' "
					+ "when dch.DC_STATUS='INTRANSIT' and SI.STATUS='IDC' then 'In Transit -- Ready for Dispatch DC' "
					+ "ELSE si.STATUS END AS STB_STATUS , si.FLUSHOUT_FLAG "
					+ "from "
					+ "DP_PRODUCT_MASTER pm, "
					+ "VR_CIRCLE_MASTER cm, "
					+ "VR_LOGIN_MASTER lm, "
					+ "VR_ACCOUNT_DETAILS ad, "
					+ "DP_REV_CHURN_INVENTORY_hist si left outer join DP_REV_CHURN_DC_DETAIL dcd "
					+ "on si.SERIAL_NUMBER=dcd.SERIAL_NUMBER "
					+ "and si.CREATED_BY=dcd.DIST_ID and si.PRODUCT_ID=dcd.PRODUCT_ID "
					+ "and si.REQ_ID =dcd.REQ_ID "
					+ "left outer join  DP_REV_CHURN_DC_HEADER dch on dcd.DC_NO=dch.DC_NO "
					+ "where cm.CIRCLE_ID=ad.CIRCLE_ID "
					+ "and ad.ACCOUNT_ID=lm.LOGIN_ID "
					+ "and ad.ACCOUNT_ID=si.CREATED_BY "
					+ "AND si.PRODUCT_ID = pm.PRODUCT_ID "
					+ "And si.HIST_DATE = (select  max(HIST_DATE) from DP_REV_CHURN_INVENTORY_hist a "
					+ "where a.SERIAL_NUMBER=si.SERIAL_NUMBER) "
					+ "and si.SERIAL_NUMBER in ("
					+ stbList
					+ ")"
					+ " order by SERIAL_NUMBER,dch.DC_DATE";

			//logger
				//	.info("****************In getChurnSerialNoListHist query********************** "
					//		+ query);
			prepareStatement = connection.prepareStatement(query);
			resultSet = prepareStatement.executeQuery();
			while (resultSet.next()) {
				dpStbInBulk = new DPStbInBulkDTO();

				if (resultSet.getString("SERIAL_NUMBER") != null) {
					dpStbInBulk.setSerial_no(resultSet
							.getString("SERIAL_NUMBER"));

				} else {
					dpStbInBulk.setSerial_no("");
				}
				if (resultSet.getString("stb_type") != null) {
					dpStbInBulk.setRev_product_name(resultSet
							.getString("stb_type"));
				} else {
					dpStbInBulk.setRev_product_name("");
				}

				if (resultSet.getString("LOGIN_NAME") != null) {
					dpStbInBulk.setDistIdRev(resultSet.getString("LOGIN_NAME"));
				} else {
					dpStbInBulk.setDistIdRev("");
				}
				if (resultSet.getString("ACCOUNT_NAME") != null) {
					dpStbInBulk.setDistributorRev(resultSet
							.getString("ACCOUNT_NAME"));
				} else {
					dpStbInBulk.setDistributorRev("");
				}
				if (resultSet.getString("CIRCLE_NAME") != null) {
					dpStbInBulk
							.setCircleRev(resultSet.getString("CIRCLE_NAME"));
				} else {
					dpStbInBulk.setCircleRev("");
				}
				dpStbInBulk.setInv_change_date("");

				if (resultSet.getString("COLLECTION_DATE") != null) {
					dpStbInBulk.setCollection_date(resultSet
							.getString("COLLECTION_DATE"));
				} else {
					dpStbInBulk.setCollection_date("");
				}
				dpStbInBulk.setInv_change_type("");

				dpStbInBulk.setDefect_type("");

				if (resultSet.getString("DC_NO") != null) {
					dpStbInBulk.setRev_dcno(resultSet.getString("DC_NO"));
				} else {
					dpStbInBulk.setRev_dcno("");
				}
				if (resultSet.getString("DC_DATE") != null) {
					Date dt = resultSet.getDate("DC_DATE");
					DateFormat format = new SimpleDateFormat("dd-MMM-yyyy");
					String dte = "";
					dte = format.format(dt);
					dpStbInBulk.setRev_dc_date(dte);
				} else {
					dpStbInBulk.setRev_dc_date("");
				}
				if (resultSet.getString("DC_STATUS") != null) {
					dpStbInBulk.setRev_dc_status(resultSet
							.getString("DC_STATUS"));
				} else {
					dpStbInBulk.setRev_dc_status("");
				}
				if (resultSet.getString("STB_STATUS") != null) {
					dpStbInBulk.setRev_stock_in_status(resultSet
							.getString("STB_STATUS"));
				} else {
					dpStbInBulk.setRev_stock_in_status("");
				}
				if(resultSet.getString("FLUSHOUT_FLAG")!=null && !"0".equals(resultSet.getString("FLUSHOUT_FLAG")))
				{
					if("-1".equals(resultSet.getString("FLUSHOUT_FLAG")) )
						dpStbInBulk.setStatus("Not in Stock - Flushed out by Botree");
					else if("-2".equals(resultSet.getString("FLUSHOUT_FLAG")))
						dpStbInBulk.setStatus("Not in Stock - Flushed out by DP");								
					
				}

				reserveSerialNoList.add(dpStbInBulk);
			}
			//logger
				//	.info("****************End getChurnSerialNoListHist ********************** ");
		} catch (SQLException e) {
			try {
				connection.rollback();
			} catch (Exception ex) {
			}
			e.printStackTrace();
		} catch (Exception e) {
			try {
				connection.rollback();
			} catch (Exception ex) {
			}
			e.printStackTrace();
			logger.error("Exception occured while getChurnSerialNoListHist."
					+ "Exception Message: " + e.getMessage());
		} finally {
			try {
				DBConnectionManager.releaseResources(prepareStatement, resultSet);
				DBConnectionManager.releaseResources(connection);
			} catch (Exception e) {
			}
		}
		//logger
			//	.info("****************retrun getChurnSerialNoListHist ********************** "
			//			+ reserveSerialNoList);
		return reserveSerialNoList;

	}
	
	/**
	 * 
	 * @param dpStbInBulkDTO
	 * @return
	 * @throws DAOException
	 */
	public Map getStockInventoryList(DPStbInBulkDTO dpStbInBulkDTO)
			throws DAOException {
		//logger
		//		.info("****************In getStockInventoryList method********************** ");

		ArrayList freshSerialNoList = new ArrayList();
		PreparedStatement prepareStatement = null;
		ResultSet resultSet = null;

		DPStbInBulkDTO dpStbInBulk = null;
		Connection connection = null;
		Map<String, DPStbInBulkDTO> freshInvMap = new HashMap<String, DPStbInBulkDTO>();

		String stbList = dpStbInBulkDTO.getSerial_no();
		//logger
			//	.info("****************In getStockInventoryList stbList********************** "
				//		+ stbList);
		try {
			connection = DBConnectionManager.getDBConnection();
	
		String query = "SELECT si.SERIAL_NO, pm.PRODUCT_NAME AS stb_type, csm.STATUS_ID, si.MARK_DAMAGED, "
			+ "lm.login_name AS dist_id, ad.ACCOUNT_NAME AS dist, cm.circle_name, ph.PO_NO, ph.PO_DATE as podate, ih.DC_NO, "
			+ "ih.DC_DT AS dc_date, ih.dist_action_date AS accpt_date, ad1.ACCOUNT_NAME AS fse, ad2.ACCOUNT_NAME AS retailer, "
			+ "ih.Status, postatus.id, postatus.value,si.CUSTOMER_ID, si.INV_NO " 
			+ "FROM DP_PRODUCT_MASTER pm,DP_CPE_STATUS_MASTER csm, VR_ACCOUNT_DETAILS ad, vr_login_master lm, VR_CIRCLE_MASTER cm, DP_STOCK_INVENTORY si "
			+ "LEFT OUTER JOIN VR_ACCOUNT_DETAILS ad1 ON si.FSE_ID = ad1.ACCOUNT_ID "
			+ "LEFT OUTER JOIN VR_ACCOUNT_DETAILS ad2 ON si.RETAILER_ID = ad2.ACCOUNT_ID "
			+ "LEFT OUTER JOIN INVOICE_HEADER ih LEFT OUTER JOIN PO_HEADER ph "
			+ "LEFT OUTER JOIN (select id,value from DP_CONFIGURATION_DETAILS where CONFIG_ID=2) as postatus  on (postatus.ID = ph.PO_STATUS)"
			+ " ON (ih.PO_NO = ph.PO_NO) ON (si.INV_NO = ih.INV_NO) "
			+ "WHERE  si.STATUS = csm.STATUS_ID AND si.DISTRIBUTOR_ID = ad.ACCOUNT_ID "
			+ "AND si.DISTRIBUTOR_ID = lm.LOGIN_ID AND si.PRODUCT_ID = pm.PRODUCT_ID "
			+ "AND cm.circle_id = ad.circle_id  and si.SERIAL_NO in (" + stbList + ") order by si.SERIAL_NO ";

	//logger.info("****************query**********************" + query);
	prepareStatement = connection.prepareStatement(query);
	resultSet = prepareStatement.executeQuery();
	DPStbInBulkDTO dpStbInBulkStockInv = null;
	
	while (resultSet.next()) {
		dpStbInBulkStockInv = new DPStbInBulkDTO();

		if (resultSet.getString("SERIAL_NO") != null) {
			dpStbInBulkStockInv.setSerial_no(resultSet
					.getString("SERIAL_NO"));
		} else {
			dpStbInBulkStockInv.setSerial_no("");
		}
		if (resultSet.getString("stb_type") != null) {
			dpStbInBulkStockInv.setProduct_name(resultSet
					.getString("stb_type"));
		} else {
			dpStbInBulkStockInv.setProduct_name("");
		}

		if (resultSet.getString("MARK_DAMAGED") != null) {
			if ("I".equals(resultSet.getString("MARK_DAMAGED"))) {
				if (resultSet.getString("id") != null) {
					if ("1".equals(resultSet.getString("id")) || "2".equals(resultSet.getString("id")) || "3".equals(resultSet.getString("id"))
							|| "4".equals(resultSet.getString("id")) || "7".equals(resultSet.getString("id")) || "8".equals(resultSet.getString("id"))
							|| "9".equals(resultSet.getString("id")) || "10".equals(resultSet.getString("id")) || "11".equals(resultSet.getString("id"))
							|| "12".equals(resultSet.getString("id")) || "13".equals(resultSet.getString("id")) || "14".equals(resultSet.getString("id"))) {
						dpStbInBulkStockInv.setStatus("In Transit -- Pending In PO");
					} else if ("32".equals(resultSet.getString("id"))
							|| "34".equals(resultSet.getString("id"))) {
						dpStbInBulkStockInv.setStatus("In Transit -- Pending TSM action(Short/Wrong)");
					} else if ("31".equals(resultSet.getString("id"))
							|| "33".equals(resultSet.getString("id"))) {
						dpStbInBulkStockInv.setStatus("Serialized Stock -- Restricted");
					} else if ("-1".equals(resultSet.getString("id"))) {
						dpStbInBulkStockInv.setStatus("Not In Stock- Flushed out by BoTree");
					} else if ("-2".equals(resultSet.getString("id"))) {
						dpStbInBulkStockInv.setStatus("Not In Stock- Flushed out by DP");
					}else {
						dpStbInBulkStockInv.setStatus("");
					}
				} else {
					dpStbInBulkStockInv.setStatus("");
				}
			} else {
				if (resultSet.getString("STATUS_ID") != null) {
					if ("10".equals(resultSet.getString("STATUS_ID"))) {
						dpStbInBulkStockInv.setStatus("Serialized Stock -- Unassigned Pending");
					} else if ("1".equals(resultSet.getString("STATUS_ID"))) {
						dpStbInBulkStockInv.setStatus("Serialized Stock -- Unassigned Complete");
					} else if ("5".equals(resultSet.getString("STATUS_ID"))) {
						dpStbInBulkStockInv.setStatus("Serialized Stock -- Restricted");
					} else if ("-1".equals(resultSet.getString("STATUS_ID"))) {
						dpStbInBulkStockInv.setStatus("Not In Stock- Flushed out by BoTree");
					} else if ("-2".equals(resultSet.getString("STATUS_ID"))) {
						dpStbInBulkStockInv.setStatus("Not In Stock- Flushed out by DP");
					} else if ("3".equals(resultSet.getString("STATUS_ID"))) {
						dpStbInBulkStockInv.setStatus("Assigned");
					} else if ("4".equals(resultSet.getString("STATUS_ID"))) {
						dpStbInBulkStockInv.setStatus("Repaire");
					} else if ("11".equals(resultSet.getString("STATUS_ID"))) {
						dpStbInBulkStockInv.setStatus("Returned as DOA");
					} else if ("12".equals(resultSet.getString("STATUS_ID"))) {
						dpStbInBulkStockInv.setStatus("Returned as SWAP");
					} else if ("13".equals(resultSet.getString("STATUS_ID"))) {
						dpStbInBulkStockInv.setStatus("Added By Warehouse");
					} else if ("14".equals(resultSet.getString("STATUS_ID"))) {
						dpStbInBulkStockInv.setStatus("Returned To Warehouse");
					} else {
						dpStbInBulkStockInv.setStatus(resultSet.getString("STATUS_ID"));
					}
				} else {
					dpStbInBulkStockInv.setStatus("");
				}

			}
		} else {
			dpStbInBulkStockInv.setStatus("");
		}

		if (resultSet.getString("dist_id") != null) {
			dpStbInBulkStockInv.setDistId(resultSet.getString("dist_id"));
		} else {
			dpStbInBulkStockInv.setDistId("");
		}
		if (resultSet.getString("dist") != null) {
			dpStbInBulkStockInv.setDistributor(resultSet.getString("dist"));
		} else {
			dpStbInBulkStockInv.setDistributor("");
		}
		if (resultSet.getString("circle_name") != null) {
			dpStbInBulkStockInv.setCircle(resultSet.getString("circle_name"));
		} else {
			dpStbInBulkStockInv.setCircle("");
		}
		if (resultSet.getString("PO_NO") != null) {
			dpStbInBulkStockInv.setPono(resultSet.getString("PO_NO"));
		} else {
			if (resultSet.getString("INV_NO") != null) {
				dpStbInBulkStockInv.setPono(resultSet.getString("INV_NO"));

			} else {
				dpStbInBulkStockInv.setPono("");
			}
		}
		if (resultSet.getDate("podate") != null) {
			Date dt = resultSet.getDate("podate");
			DateFormat format = new SimpleDateFormat("dd-MMM-yyyy");
			String dte = "";
			dte = format.format(dt);
			dpStbInBulkStockInv.setPoDate(dte);
		} else {
			dpStbInBulkStockInv.setPoDate("");
		}
		if (resultSet.getString("DC_NO") != null) {
			dpStbInBulkStockInv.setDcno(resultSet.getString("DC_NO"));
		} else {
			dpStbInBulkStockInv.setDcno("");
		}
		if (resultSet.getString("dc_date") != null) {
			Date dt = resultSet.getDate("dc_date");
			DateFormat format = new SimpleDateFormat("dd-MMM-yyyy");
			String dte = "";
			dte = format.format(dt);
			dpStbInBulkStockInv.setDcDate(dte);
		} else {
			dpStbInBulkStockInv.setDcDate("");
		}
		if (resultSet.getString("accpt_date") != null) {
			Date dt = resultSet.getDate("accpt_date");
			DateFormat format = new SimpleDateFormat("dd-MMM-yyyy");
			String dte = "";
			dte = format.format(dt);
			dpStbInBulkStockInv.setAccept_date(dte);
		} else {
			dpStbInBulkStockInv.setAccept_date("");
		}
		if (resultSet.getString("fse") != null) {
			dpStbInBulkStockInv.setFsc(resultSet.getString("fse"));
		} else {
			dpStbInBulkStockInv.setFsc("");
		}
		if (resultSet.getString("retailer") != null) {
			dpStbInBulkStockInv.setRetailer(resultSet
					.getString("retailer"));
		} else {
			dpStbInBulkStockInv.setRetailer("");
		}
		if (resultSet.getString("value") != null) {
			
		/*	dpStbInBulkStockInv.setInvoice_status(resultSet
					.getString("value"));

		} else {*/
			if (resultSet.getString("MARK_DAMAGED") != null) {
				if ("I".equals(resultSet.getString("MARK_DAMAGED"))) {
					if (resultSet.getString("id") != null) {
						if ("1".equals(resultSet.getString("id")) || "2".equals(resultSet.getString("id")) || "3".equals(resultSet.getString("id"))
								|| "4".equals(resultSet.getString("id")) || "7".equals(resultSet.getString("id")) || "8".equals(resultSet.getString("id"))
								|| "9".equals(resultSet.getString("id")) || "10".equals(resultSet.getString("id")) || "11".equals(resultSet.getString("id"))
								|| "12".equals(resultSet.getString("id")) || "13".equals(resultSet.getString("id")) || "14".equals(resultSet.getString("id"))) {
							dpStbInBulkStockInv.setInvoice_status("In Transit -- Pending In PO");
						} else if ("32".equals(resultSet.getString("id"))
								|| "34".equals(resultSet.getString("id"))) {
							dpStbInBulkStockInv.setInvoice_status("In Transit -- Pending TSM action(Short/Wrong)");
						} else if ("31".equals(resultSet.getString("id")) || "33".equals(resultSet.getString("id"))) {
							dpStbInBulkStockInv.setInvoice_status("Serialized Stock -- Restricted");
						} else if ("-1".equals(resultSet.getString("id"))) {
							dpStbInBulkStockInv.setStatus("Not In Stock- Flushed out by BoTree");
						} else if ("-2".equals(resultSet.getString("id"))) {
							dpStbInBulkStockInv.setStatus("Not In Stock- Flushed out by DP");
						}else {
							dpStbInBulkStockInv.setInvoice_status("");
						}
					} else {
						dpStbInBulkStockInv.setInvoice_status("");
					}
				} else {
					if (resultSet.getString("STATUS_ID") != null) {
						if ("10".equals(resultSet
								.getString("STATUS_ID"))) {
							dpStbInBulkStockInv.setInvoice_status("Serialized Stock -- Unassigned Pending");
						} else if ("1".equals(resultSet
								.getString("STATUS_ID"))) {
							dpStbInBulkStockInv.setInvoice_status("Serialized Stock -- Unassigned Complete");
						} else if ("5".equals(resultSet
								.getString("STATUS_ID"))) {
							dpStbInBulkStockInv.setInvoice_status("Serialized Stock -- Restricted");
						} else if ("-1".equals(resultSet
								.getString("STATUS_ID"))) {
							dpStbInBulkStockInv.setInvoice_status("Not In Stock- Flushed out by BoTree");
						} else if ("-2".equals(resultSet
								.getString("STATUS_ID"))) {
							dpStbInBulkStockInv.setInvoice_status("Not In Stock- Flushed out by DP");
						} else if ("3".equals(resultSet.getString("STATUS_ID"))) {
							dpStbInBulkStockInv.setInvoice_status("Assigned");
						} else if ("4".equals(resultSet.getString("STATUS_ID"))) {
							dpStbInBulkStockInv.setInvoice_status("Repaire");
						} else if ("11".equals(resultSet.getString("STATUS_ID"))) {
							dpStbInBulkStockInv.setInvoice_status("Returned as DOA");
						} else if ("12".equals(resultSet.getString("STATUS_ID"))) {
							dpStbInBulkStockInv.setInvoice_status("Returned as SWAP");
						} else if ("13".equals(resultSet.getString("STATUS_ID"))) {
							dpStbInBulkStockInv.setInvoice_status("Added By Warehouse");
						} else if ("14".equals(resultSet.getString("STATUS_ID"))) {
							dpStbInBulkStockInv.setInvoice_status("Returned To Warehouse");
						} else {
							dpStbInBulkStockInv.setInvoice_status(resultSet.getString("STATUS_ID"));
						}
					} else {
						dpStbInBulkStockInv.setInvoice_status("");
					}

				}
			} else {
				dpStbInBulkStockInv.setInvoice_status("");
			}

			// dpStbInBulkStockInv.setInvoice_status("");
		}

		dpStbInBulkStockInv.setActivationDate("");

		if (resultSet.getString("CUSTOMER_ID") != null) {
			dpStbInBulkStockInv.setCustomer(resultSet.getString("CUSTOMER_ID"));
		} else {
			dpStbInBulkStockInv.setCustomer("");
		}

		freshInvMap.put(dpStbInBulkStockInv.getSerial_no(), dpStbInBulkStockInv);
	} 
		}catch (SQLException e) {
		try {
			connection.rollback();
		} catch (Exception ex) {
		}
		e.printStackTrace();
	} catch (Exception e) {
		try {
			connection.rollback();
		} catch (Exception ex) {
		}
		e.printStackTrace();
		logger.error("Exception occured while getChurnSerialNoListHist."
				+ "Exception Message: " + e.getMessage());
	} finally {
		try {
			DBConnectionManager.releaseResources(prepareStatement, resultSet);
			DBConnectionManager.releaseResources(connection);
		} catch (Exception e) {
		}
	}
	logger
			.info("****************retrun getChurnSerialNoListHist ********************** "
					+ freshInvMap.size());
	return freshInvMap;
	}

}
