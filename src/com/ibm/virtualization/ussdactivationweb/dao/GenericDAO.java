package com.ibm.virtualization.ussdactivationweb.dao;

import java.util.ArrayList;
import java.util.List;

import com.ibm.virtualization.ussdactivationweb.dto.SubscriberDTO;

/**
 * This class describes the DAO methods for Generic persistent object
 * that are implemented by all concrete DAO implementations. For example for
 * Oracle this class is implemeted by OracleGenericDAO
 * @author  Ashad
 * @version 1.0, Jul 6, 2007
 */
public interface GenericDAO
{
/**
 * This method implementation should read queries and execute it.
 *
 * @param  java.util.Map contains input parameters like XML_FILE_NAME,
 * 					CRITERIA_ID and so forth.
 * @return java.util.Map contains result retrieved from database
 * @exception java.lang.Exception
 */
  public ArrayList retrieveResults(SubscriberDTO sDTO) throws java.lang.Exception;
  public boolean checkNumber(long mobile)throws java.lang.Exception;
  public List pLogin(SubscriberDTO sDTO) throws java.lang.Exception;
  public List viewSingleMapping(SubscriberDTO sDTO)throws java.lang.Exception;
  public void editSubscriber(SubscriberDTO sDTO)throws java.lang.Exception;
}
