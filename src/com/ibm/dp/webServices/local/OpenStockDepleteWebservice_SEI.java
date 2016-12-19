package com.ibm.dp.webServices.local;


public interface OpenStockDepleteWebservice_SEI extends java.rmi.Remote
{
  public java.lang.String depleteOpenStock(java.lang.String distributorCode,java.lang.String productCode,int productQuantity,java.lang.String userid,java.lang.String password);
}