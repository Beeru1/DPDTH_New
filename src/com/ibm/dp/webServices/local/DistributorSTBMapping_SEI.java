package com.ibm.dp.webServices.local;


public interface DistributorSTBMapping_SEI extends java.rmi.Remote
{
  public java.lang.String[] distStbMapping(java.lang.String distOLMId,java.lang.String stbNo,java.lang.String retailerMobNo,int requestType,java.lang.String productName,java.lang.String userid,java.lang.String password);
  public java.lang.String[] distStbMappingNew(java.lang.String distOLMId,java.lang.String stbNo,java.lang.String andrdStbNo,java.lang.String retailerMobNo,int requestType,java.lang.String productName,java.lang.String andrdProdName,java.lang.String userid,java.lang.String password,java.lang.String Flag);
}