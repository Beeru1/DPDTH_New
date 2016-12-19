package com.ibm.dp.webServices.local;


public interface RetailerInfoWebservice_SEI extends java.rmi.Remote
{
  public java.lang.String[] getRetailerDistInfoCannonNew(java.lang.String mobilenumber,java.lang.String pinCode,java.lang.String userid,java.lang.String password);
  public java.lang.String[] getRetailerInfo(java.lang.String mobilenumber,java.lang.String userid,java.lang.String password);
  public java.lang.String[] getRetailerDistInfoCannon(java.lang.String mobilenumber,java.lang.String pinCode,java.lang.String userid,java.lang.String password);
  public java.lang.String[] getRetailerInfoCannon(java.lang.String mobilenumber,java.lang.String userid,java.lang.String password);
}