package com.ibm.dp.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import com.ibm.dp.dao.ManufacturerDetailsDao;
import com.ibm.dp.dto.ManufacturerDetailsDto;
import com.ibm.virtualization.recharge.exception.DAOException;

public class ManufacturerDetailsDaoImpl implements ManufacturerDetailsDao{

	@Override
	public  List<ManufacturerDetailsDto> getManufacturerDetailsDao(
			Connection connection) throws DAOException {
		// TODO Auto-generated method stub
		
		 //List<ManufacturerDetailsDto>> mapReturn = new HashMap<String, List<ManufacturerDetailsDto>>();
		PreparedStatement ps = null;
		ResultSet rs=null;
		List<ManufacturerDetailsDto> listMessage = new ArrayList<ManufacturerDetailsDto>();
			 
			 try{
			ps = connection.prepareStatement("SELECT MANUFACTURER_ID,MODEL_NUMBER,MANUFACTURER,STATUS,SELECTION_FLAG FROM DP_MANUFACTURER_DETAILS");
			rs = ps.executeQuery();
			 while(rs.next()){		 
				 ManufacturerDetailsDto tempDTO = new ManufacturerDetailsDto();
				 tempDTO.setManufacturerId(rs.getString("MANUFACTURER_ID"));
				 tempDTO.setModelNumber(rs.getString("MODEL_NUMBER"));
				 tempDTO.setManufacturer(rs.getString("MANUFACTURER"));
				 tempDTO.setStatus(rs.getString("STATUS"));
				 tempDTO.setSelectionFlag(rs.getString("SELECTION_FLAG").trim());
				 listMessage.add(tempDTO);
			 }
			 
			 }
			 catch(Exception ex){
				 ex.printStackTrace();
			 }
		return listMessage;
	}

	@Override
	public void saveManufacturerDao(Connection connection, String manuList,String flag)
			throws DAOException {
		// TODO Auto-generated method stub
		String[] manuId =manuList.split(",");
		String[] sFlag =flag.split(",");
		String stFlag=null;
		//String manuIdd=null;

		String manufacturerId=null;
		PreparedStatement ps = null;
		try{
		for (int z = 0; z < manuId.length; z++) {
			manufacturerId=manuId[z].trim();
			stFlag=sFlag[z].trim();
			 ps= connection.prepareStatement("Update DP_MANUFACTURER_DETAILS SET SELECTION_FLAG = ? where MANUFACTURER_ID = ? ");
			 ps.setString(1, stFlag);
			 ps.setString(2, manufacturerId);
			 ps.executeUpdate();
			 connection.commit();
		}
		}
		catch(Exception e){
			e.printStackTrace();
		}
		
	}

}
