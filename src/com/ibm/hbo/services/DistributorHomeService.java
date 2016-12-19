package com.ibm.hbo.services;

import java.util.ArrayList;

import com.ibm.hbo.exception.HBOException;

public interface DistributorHomeService {
  public ArrayList getDistributorStockSummary(long distId) throws HBOException;

public ArrayList getDistributorProdSummary(long id)throws HBOException;
}
