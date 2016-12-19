package com.ibm.dp.service;

import java.util.ArrayList;

import com.ibm.dp.dto.DPCreateBeatDto;
import com.ibm.virtualization.recharge.exception.DAOException;
import com.ibm.virtualization.recharge.exception.VirtualizationServiceException;

public interface DPCreateBeatService {
	
	public void insertBeat(DPCreateBeatDto createBeatDto) throws VirtualizationServiceException, DAOException;
	
	public ArrayList getCities()throws VirtualizationServiceException, DAOException;
	
	public ArrayList getAllBeats(DPCreateBeatDto beat, int lb,int ub) throws VirtualizationServiceException;
	
	// Method Added to fix Pagination issue on Beat Screen
	public int getAllBeatsCount(DPCreateBeatDto beat) throws VirtualizationServiceException;
	
	public ArrayList getAllcircles() throws NumberFormatException, VirtualizationServiceException;
	
	public ArrayList getZones(int stateId)throws VirtualizationServiceException;
	
	public ArrayList getCites(int zoneId)throws VirtualizationServiceException;
	
	public DPCreateBeatDto getBeatRecord (int beatId)throws VirtualizationServiceException;
	
	public String updateBeat(DPCreateBeatDto beat) throws VirtualizationServiceException;
}
