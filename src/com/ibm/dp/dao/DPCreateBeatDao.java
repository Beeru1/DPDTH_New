package com.ibm.dp.dao;

import java.util.ArrayList;

import com.ibm.dp.dto.DPCreateBeatDto;
import com.ibm.virtualization.recharge.exception.DAOException;
import com.ibm.virtualization.recharge.exception.VirtualizationServiceException;

public interface DPCreateBeatDao {
	
	public void insertBeat(DPCreateBeatDto createBeatDto) throws VirtualizationServiceException;
	
	public ArrayList getCities() throws VirtualizationServiceException;
	
	public int checkBeatNameAlreadyExist(String beatName)throws DAOException;
	
	public ArrayList getAllBeats(DPCreateBeatDto beat, int lb, int ub) throws VirtualizationServiceException, DAOException;
	
	// Method Added to fix Pagination Issue on View Beat Screen
	public int getAllBeatsCount(DPCreateBeatDto beat) throws VirtualizationServiceException, DAOException;

	public ArrayList getAllCircles() throws DAOException;
	
	public ArrayList getCites(int zoneId) throws DAOException;

	public ArrayList getZones(int stateId) throws DAOException;
	
	public DPCreateBeatDto getBeatRecord(int beatId)throws DAOException;
	
	public String updateBeat(DPCreateBeatDto beat) throws DAOException;
}
