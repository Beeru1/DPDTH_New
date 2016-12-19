/*****************************************************************************\
 **
 ** Virtualization - Recharge.
 **
 ** Copyright (c) 2007-2008 IBM.
 ** All Rights Reserved
 **
 **
 \****************************************************************************/

package com.ibm.virtualization.recharge.common;

import java.io.IOException;
import java.io.StringReader;
import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Random;

import javax.mail.internet.NewsAddress;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.apache.log4j.Logger;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

import com.ibm.virtualization.framework.bean.ConnectionBean;
import com.ibm.virtualization.framework.bean.TransactionStatus;
import com.ibm.virtualization.framework.utils.QueueAdaptor;
import com.ibm.virtualization.recharge.exception.VirtualizationServiceException;
import com.ibm.virtualization.recharge.message.CoreMessageBean;

/**
 * This is a Generic Utility Class which provides helper methods to get current
 * Server date time etc.
 * 
 * @author Paras
 * 
 */
public class Utility {

	static {
		/** * Setting the Resource Bundle Name for the PropertyReader File ** */
		// PropertyReader.setBundleName(Constants.APLLICATION_RESOURCE_BUNDLE);
	}

	/*
	 * Logger for this class.
	 * 
	 */
	private static Logger logger = Logger.getLogger(Utility.class.getName());

	private static DocumentBuilderFactory dbFactory = null;

	private static DocumentBuilder documentBuilder = null;

	/**
	 * This method returns Timestamp of Application Server
	 * 
	 * 
	 * @return sqlDateTime
	 */
	public static Timestamp getDateTime() {
		logger.trace(" Started getDateTime()");
		GregorianCalendar cal = (GregorianCalendar) GregorianCalendar
				.getInstance();
		// Calendar cal = Calendar.getInstance();
		Timestamp sqlDateTime = new Timestamp(cal.getTimeInMillis());
		logger.debug(" Time :" + sqlDateTime.toString());
		return sqlDateTime;
	}

	public static String getFormattedDate() {
		logger.trace(" Started getFormattedDate()");
		return getFormattedDate(new Date());
	}
	
	public static String getFormattedDate(Date d) {
		logger.trace(" Started getFormattedDate(). Date:"+d.toString());
		try {
			SimpleDateFormat sdf = new SimpleDateFormat(ResourceReader
					.getCoreResourceBundleValue(
							"DateTimePattern.dateMonthYearTime"));
			return sdf.format(d);
		} catch (VirtualizationServiceException virtualizationExp) {
			logger.warn("caught VirtualizationServiceException"
					+ virtualizationExp.getMessage());
			return null;
		}
	}

	/**
	 * This method searches and reads a resource bundle and provides the
	 * property value requested
	 * 
	 * @param propertyName
	 * @return property
	 * @throws VirtualizationServiceException
	 */
	/*
	 * public static String getPropertyFromApplicationResource(String
	 * propertyKey) throws VirtualizationServiceException { try { ResourceBundle
	 * resourceBundle = ResourceBundle .getBundle("ApplicationResources");
	 * return resourceBundle.getString(propertyKey); } catch
	 * (MissingResourceException missingResourceExp) { logger .error("Customer
	 * Transaction(): caught MissingResourceException : Unable to get " +
	 * "ApplicationResources.properties " + missingResourceExp.getMessage());
	 * throw new VirtualizationServiceException(
	 * "errors.utility.resources_notfound"); } }
	 */
	/**
	 * This method returns the value of property of the given resource bundle
	 * and property.
	 * 
	 * @param propertyKey
	 * @return String
	 * @throws VirtualizationServiceException
	 */
	/*
	 * public static String getPropertyFromBundle(String propertyKey) throws
	 * VirtualizationServiceException { try { if(resourceBundle==null) {
	 * resourceBundle = ResourceBundle
	 * .getBundle("com.ibm.virtualization.recharge.resources.VirtualizationResources"); }
	 * return resourceBundle.getString(propertyKey); } catch
	 * (MissingResourceException missingResourceExp) { logger
	 * .error("getPropertyFromBundle() method : caught MissingResourceException :
	 * Unable to get " +
	 * "com.ibm.virtualization.recharge.resources.VirtualizationResources.properties " +
	 * missingResourceExp.getMessage()); throw new
	 * VirtualizationServiceException( "errors.utility.resources_notfound"); } }
	 */
	/**
	 * To retirve the date passed in the parameter as java.sql.Date
	 * 
	 * @param strDate
	 * @return
	 */
	public static java.sql.Date getSqlDate(String strDate) {
		logger.debug(" Started getSqlDate(). Date:" + strDate);
		return new java.sql.Date(Utility.getDate(strDate).getTime());
	}

	/**
	 * This method returns the value of property of the given resource bundle
	 * and property.
	 * 
	 * @param propertyKey
	 * @param resourceBundleName
	 * @return String
	 * @throws VirtualizationServiceException
	 */
	/*
	 * public static String getValueFromBundle(String propertyKey, String
	 * resourceBundleName) { try { ResourceBundle resourceBundle =
	 * ResourceBundle .getBundle(resourceBundleName); return
	 * resourceBundle.getString(propertyKey); } catch (MissingResourceException
	 * missingResourceExp) { logger .error( "getPropertyFromBundle() method :
	 * caught MissingResourceException : Unable to get " + resourceBundleName + " : " +
	 * missingResourceExp.getMessage(), missingResourceExp);
	 *  } return null; }
	 */
	/**
	 * @param date
	 * @return
	 */
	public static Date getDate(String date) {
		logger.debug(" Started getDate(). Date:" + date);
		SimpleDateFormat sdf;
		try {
			sdf = new SimpleDateFormat(ResourceReader.getCoreResourceBundleValue("DatePattern.dateMonthYear"));

			return sdf.parse(date);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			logger.warn("Exception occured while parsign date", e);
			return null;
		} catch (VirtualizationServiceException virtualizationExp) {
			logger.warn("caught VirtualizationServiceException"
					+ virtualizationExp.getMessage());
			return null;
		}
	}

	/**
	 * This function converts the passed string date into java.sql.Timestamp
	 * Input should be in format : 20070331T20:08:07
	 * 
	 * @param date
	 * @return
	 */
	public static java.sql.Timestamp getDateAsTimestamp(String date) {
		logger.debug(" Started getDateAsTimestamp(). Date:" + date);
		try {
			String date1 = date;
			if (date.indexOf("T") == 8) {
				date1 = date.replace("T", " ");
			}

			SimpleDateFormat sdf = new SimpleDateFormat(ResourceReader
					.getCoreResourceBundleValue(
							"DateTimePattern.dateMonthYearTime"));

			Date tempDate = sdf.parse(date1);
			java.sql.Timestamp currentDt = new java.sql.Timestamp(tempDate
					.getTime());
			return currentDt;

		} catch (ParseException e) {
			// TODO Auto-generated catch block
			logger.warn("Exception occured while parsign date", e);
			return null;
		} catch (VirtualizationServiceException virtualizationExp) {
			logger.warn("caught VirtualizationServiceException"
					+ virtualizationExp.getMessage());
			return null;
		}
	}

	/**
	 * This function converts the passed string date into java.sql.Timestamp
	 * Input should be in format : 20070331T20:08:07
	 * 
	 * @param strDate
	 * @return
	 */
	public static java.util.Date getRequestTimeDate(String strDate) {
		logger.debug(" Started getRequestTimeDate(). Date:" + strDate);
		try {
			String date = strDate;
			SimpleDateFormat sdf = new SimpleDateFormat(ResourceReader
					.getCoreResourceBundleValue(
							"DateTimePattern.dateMonthYearTime"));
			// TODO ashish current date format is needed in case of
			// transactionMaster.getRequestTime() is null
			Date t = new Date();
			
			String currentDate = sdf.format(t);

			if (strDate != null) {

				if (strDate.indexOf("T") == 8) {
					date = strDate.replace("T", " ");
				}
				return sdf.parse(date);
			} else {
				logger
						.info("Date is null, parsing current date:"
								+ currentDate);
				return sdf.parse(currentDate);
			}
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			logger.warn("Exception occured while parsign date", e);
			return null;
		} catch (VirtualizationServiceException virtualizationExp) {
			logger.warn("caught VirtualizationServiceException"
					+ virtualizationExp.getMessage());
			return null;
		}
	}

	/**
	 * This method returns the current date in the format provided in the
	 * property flie
	 * 
	 * @param dateFormat
	 * @return sqlDateTime
	 */
	public static String getCurrentDateTime() {
		logger.trace(" Started getCurrentDateTime().");
		SimpleDateFormat sdf;
		try {

			// sdf = new
			// SimpleDateFormat(getCorePropertyFromBundle("DatePattern.dateMonthYear"));\
			sdf = new SimpleDateFormat(ResourceReader.getCoreResourceBundleValue("DatePattern.dateMonthYear"));

			return sdf.format(new java.util.Date());
		} catch (VirtualizationServiceException virtualizationExp) {
			logger.warn("caught VirtualizationServiceException"
					+ virtualizationExp.getMessage());
			return null;
		}
	}

	public static final double roundDouble(double d, int places) {
		logger.trace(" Started roundDouble().");
		return Math.round(d * Math.pow(10, (double) places))
				/ Math.pow(10, (double) places);
	}

	/**
	 * Converts amount to (######.00) format
	 * 
	 * @param amount
	 *            value which should be formatted
	 * @return String in ######.00 format
	 */
	public static String formatAmount(double amount) {
		logger.trace(" Started formatAmount(). Amount:" +amount);
		DecimalFormat df;

		try {
			df = new DecimalFormat(ResourceReader.getCoreResourceBundleValue("Amount.currencyFormat"),
					new DecimalFormatSymbols());
			df.setMinimumFractionDigits(2);
			df.setMaximumFractionDigits(2);
			df.setDecimalSeparatorAlwaysShown(true);
			return df.format(amount);
		} catch (VirtualizationServiceException virtualizationExp) {
			logger.warn("caught VirtualizationServiceException"
					+ virtualizationExp.getMessage());
			return null;
		}
	}

	/**
	 * Converts amount to (######.00) format
	 * 
	 * @param amount
	 *            value which should be formatted
	 * @return String in ######.00 format for Processing Fee
	 */
	public static String formatAmountPfee(double amount) {
		DecimalFormat df;

		try {
			df = new DecimalFormat(ResourceReader.getCoreResourceBundleValue("Amount.currencyFormat"),
					new DecimalFormatSymbols());
			df.setMinimumFractionDigits(2);
			df.setMaximumFractionDigits(4);
			df.setDecimalSeparatorAlwaysShown(true);
			return df.format(amount);
		} catch (VirtualizationServiceException virtualizationExp) {
			logger.error("caught VirtualizationServiceException"
					+ virtualizationExp.getMessage());
			return null;
		}
	}

	public static String formatNumber(double amount) {
		DecimalFormat df;

		// df = new
		// DecimalFormat(getCorePropertyFromBundle("Amount.currencyFormat"),
		// new DecimalFormatSymbols());

		df = new DecimalFormat("############", new DecimalFormatSymbols());
		// df.setMinimumFractionDigits(2);
		// df.setMaximumFractionDigits(2);
		// df.setDecimalSeparatorAlwaysShown(true);
		return df.format(amount);

	}

	/**
	 * This method returns the date passed to the function in the format
	 * provided in the property flie in string form
	 * 
	 * @param dateFormat
	 * @return sqlDateTime
	 */
	public static String convertDatetoStr(Date dateToCovert) {
		String dateFromat = "";
		try {
			dateFromat = ResourceReader.getCoreResourceBundleValue(
					"DatePattern.dateMonthYear");
			DateFormat formatter;
			formatter = new SimpleDateFormat(dateFromat);
			String s = formatter.format(dateToCovert);

			return s;
		} catch (VirtualizationServiceException virtualizationExp) {
			logger.error("caught VirtualizationServiceException"
					+ virtualizationExp.getMessage());
			return null;
		}

	}

	/**
	 * Converts amount to (######.00) format
	 * 
	 * @param amount
	 *            value which should be formatted
	 * @return String in ######.00 format
	 */
	public static String formatCurrency(double amount) {
		DecimalFormat df = new DecimalFormat("############.00",
				new DecimalFormatSymbols());
		df.setMinimumFractionDigits(2);
		df.setMaximumFractionDigits(2);
		df.setDecimalSeparatorAlwaysShown(true);
		return df.format(amount);
	}

	/**
	 * <p>
	 * Joins the elements of the provided array into a single String containing
	 * the provided list of elements.
	 * </p>
	 * 
	 * <p>
	 * No delimiter is added before or after the list. A <code>null</code>
	 * separator is the same as an empty String (""). Null objects or empty
	 * strings within the array are represented by empty strings.
	 * </p>
	 * 
	 * 
	 * @param array
	 *            the array of values to join together, may be null
	 * @param aSeparator
	 *            the separator character to use, null treated as ""
	 * @return the joined String, <code>null</code> if null array input
	 */
	public static String join(Object[] array, String aSeparator) {
		String separator = aSeparator;
		if (array == null) {
			return null;
		}
		if (separator == null) {
			separator = "";
		}
		int arraySize = array.length;

		int bufSize = arraySize == 0 ? 0
				: arraySize
						* ((array[0] == null ? 16 : array[0].toString()
								.length()) + separator.length());

		StringBuffer buf = new StringBuffer(bufSize);

		buf.append("'");
		for (int i = 0; i < arraySize; i++) {
			if (i > 0) {
				buf.append("'").append(separator).append("'");
			}
			if (array[i] != null) {
				buf.append(array[i]);
			}
		}
		buf.append("'");
		if (buf.length() == 2) {
			return "";
		} else {
			return buf.toString();
		}
	}

	/**
	 * Converts date to string in the format specified
	 * 
	 * @param thisdate
	 * @param dateFormat
	 *            like "yyyy-MM-dd"
	 * @return
	 */
	public static java.util.Date str2date(String thisdate) {

		try {
			return str2date(thisdate, ResourceReader.getCoreResourceBundleValue("DatePattern.dateMonthYear"));
		} catch (VirtualizationServiceException virtualizationExp) {
			logger.error("caught VirtualizationServiceException"
					+ virtualizationExp.getMessage());
			return null;
		}

	}

	/**
	 * Converts date to string in the format specified
	 * 
	 * @param thisdate
	 * @param dateFormat
	 *            like "yyyy-MM-dd"
	 * @return
	 */
	public static java.util.Date str2date(String thisdate, String dateFormat) {
		java.util.Date returndate = null;
		if (thisdate != null) {
			SimpleDateFormat dateFormatter = new SimpleDateFormat(dateFormat);
			try {
				returndate = dateFormatter.parse(thisdate);
			} catch (ParseException pe) {

			}
		}
		return returndate;
	}

	/**
	 * This method gets a MessageBean , a ConnectionBean object & a QueueAdaptor
	 * object Sets the status ID(STATUS_ID_SUCCESS) for passed request, uts the
	 * MessageBean pbject in the OUT Q
	 * 
	 */
	public static boolean putToQ(CoreMessageBean coreMessageBean,
			ConnectionBean connBean) {
		QueueAdaptor qAdapt = new QueueAdaptor();
		// Set StatusID = ResponseConstants.STATUS_ID_SUCCESS for passed
		// Requests ***//
//		coreMessageBean.setTransactionStatus(TransactionStatus.SUCCESS);
		// Put Message to Q
		
		logger.debug("Host.............."
				+ connBean.getHost() + ">>>>");
		logger.debug("QMgr.............."
				+ connBean.getQMgr() + ">>>>");
		logger.debug("Qu.............."
				+ connBean.getQu() + ">>>>");
		logger.debug("Port.............."
				+ connBean.getPort() + ">>>>");
		logger.debug("Channel.............."
				+ connBean.getChannel() + ">>>>");
		try {
			// Call QueueAdapter's method to put message to Q
			//qAdapt.putMsg(coreMessageBean, connBean);
			return true;
		}catch (Exception ioException) {
			logger.fatal("IOException caught while putting message in  : "
					+ connBean.getQu() + " . " + ioException);
			logger.fatal("Exception stack trace ", ioException);
			return false;
		}

	}

	/*
	 * This method retrieves the credited amount from the message
	 * 
	 * @param message @return
	 */
	public static double getValues(String message, String startString,
			String endString) {
		int startPos = message.indexOf(startString);
		// if String tokens not found in the message
		if (-1 == startPos) {
			return 0.0;
		}
		int endPos = message.indexOf(endString);
		String temp = message.substring(startPos, endPos);

		int startPosColon = temp.lastIndexOf(':') + 1;
		int endPosValue = temp.length();

		return Double.parseDouble(temp.substring(startPosColon, endPosValue));
	}

	/**
	 * calculate the no of records to be shown on each page
	 * 
	 * @param noofRow
	 * @return
	 */
	public static int getPaginationSize(int noofRow) {
		int recordsPerPage = initializePaginationSize();
		int noofpages;
		if (noofRow % recordsPerPage == 0)
			noofpages = noofRow / recordsPerPage;
		else
			noofpages = noofRow / recordsPerPage + 1;
		return noofpages;
	}

	/**
	 * set the number of records to be shown in each page. Fetch this count from
	 * properties file.
	 * 
	 * @return
	 */
	public static int initializePaginationSize() {
		int recordsPerPage = 0;
		try {
			recordsPerPage = Integer.parseInt(ResourceReader
					.getCoreResourceBundleValue(
							Constants.RECORDS_PER_PAGE));
		} catch (NumberFormatException e) {
			logger.error("The number is not a valid number format."
					+ "Exception Message: ", e);
			/** if any exception occurs then assign the default value */
			recordsPerPage = 10;
		} catch (VirtualizationServiceException virtualizationExp) {
			logger.error("caught VirtualizationServiceException"
					+ virtualizationExp.getMessage());
		}
		return recordsPerPage;
	}
	
	
	
	/**
	 * set the number of links to be shown in each page. Fetch this count from
	 * properties file.
	 * 
	 * @return
	 */
	public static int initializeLinkSize() {
		int linksPerPage = 0;
		try {
			linksPerPage = Integer.parseInt(ResourceReader
					.getCoreResourceBundleValue(
							Constants.LINKS_PER_PAGE));
		} catch (NumberFormatException e) {
			logger.error("The number is not a valid number format."
					+ "Exception Message: ", e);
			/** if any exception occurs then assign the default value */
			linksPerPage = 10;
		} catch (VirtualizationServiceException virtualizationExp) {
			logger.error("caught VirtualizationServiceException"
					+ virtualizationExp.getMessage());
		}
		return linksPerPage;
	}

	/**
	 * This method delemits the length of input string to 20 characters
	 * 
	 * @param inputString
	 * @return
	 */
	public static String delemitDesctiption(String inputString) {
		if (null == inputString || "".equals(inputString.trim())) {
			return "";
		} else if (10 >= inputString.length()) {
			return inputString;
		} else {
			StringBuilder outputString = new StringBuilder();
			String temp = inputString.substring(0, 10);
			return outputString.append(temp).append("...").toString();
		}
	}

	/**
	 * This method retrieves the value from the xml message
	 * 
	 * @param xmlString
	 * @param elementName
	 * @return
	 */
	public static double parseMessage(String xmlString, String elementName) {
		logger
				.info("Parsing message...Starting Time : "
						+ new Date().getTime());
		String strValue = parseMessageString(xmlString, elementName);
		if ((strValue == null) || ("".equalsIgnoreCase(strValue))
				|| ("null".equalsIgnoreCase(strValue))) {
			return 0;
		} else {
			double returnValue = Double.parseDouble(strValue);
			return returnValue;
		}

	}

	/**
	 * This method retrieves the String value from the xml message
	 * 
	 * @param xmlString
	 * @param elementName
	 * @return
	 */
	public static String parseMessageString(String xmlString, String elementName) {
		logger
				.info("Parsing message...Starting Time : "
						+ new Date().getTime());
		long startingTime = new Date().getTime();

		if (dbFactory == null) {
			/** get a new instance of DocumentBuilderFactory */
			dbFactory = DocumentBuilderFactory.newInstance();
			dbFactory.setValidating(false);
			dbFactory.setIgnoringElementContentWhitespace(true);
		}
		String value = null;
		try {
			if (documentBuilder == null) {
				/** get the Document Builder * */
				documentBuilder = dbFactory.newDocumentBuilder();
			}
			try {
				/** get the Document * */
				Document document = documentBuilder.parse(new InputSource(
						new StringReader(xmlString)));
				/** get the root element * */
				Element root = document.getDocumentElement();
				NodeList list = root.getElementsByTagName(elementName);
				Node node = list.item(0);
				if (node != null) {
					String temp = node.getFirstChild().getTextContent();
					// if (temp != null) {
					value = temp;
					// }
				}

			} catch (SAXException e) {
				logger.error("Exception occurred while parsing the string...");
			} catch (IOException e) {
				logger.error("Exception occurred while reading the string...");
			}
		} catch (ParserConfigurationException e) {
			logger
					.error("Exception occurred while creating new document builder...");
		} catch (NullPointerException ne) {
			logger
					.error("Exception occurred while creating new document builder...");
		}

		logger.info("Ending Time :" + (new Date().getTime() - startingTime));
		return value;
	}

	// method to generate random password
	public static String getRandomPassword() {
		Random ran = new Random();
		char[] pass = new char[10];

		for (int i = 0; i < 8; i++) {
			int n = ran.nextInt(25);
			pass[i] = (char) (65 + n);
		}
		pass[8] = (char) (ran.nextInt(10) + 48);
		int n = ran.nextInt(25);
		pass[9] = (char) (65 + n);
		return new String(pass);
	}

	// method to generate random 8 digit numeric password
	public static String getRandomNumericPassword() {
		Random ran = new Random();
		char[] pass = new char[8];
		for (int i = 0; i < 8; i++) {
			pass[i] = (char) (ran.nextInt(10) + 48);
		}
		return new String(pass);
	}

	/**
	 * Get the date time in the format 20070331T20:08:07 for Aynsc throough Web
	 * 
	 * @return
	 */
	public static String getRequestTimeDateFormat() {
		Calendar cal = Calendar.getInstance();
		String s = cal.get(Calendar.YEAR)  + lpad(cal.get(Calendar.MONTH) + 1)
				+ lpad(cal.get(Calendar.DAY_OF_MONTH)) + "T" + lpad(cal.get(Calendar.HOUR_OF_DAY)) + ":"
				+ lpad(cal.get(Calendar.MINUTE)) + ":" + lpad(cal.get(Calendar.SECOND));
		return s;
	}

	/**
	 * Lpads the year, month, date , hours, minutes and seconds with 00 to get
	 * doublie digit values
	 * 
	 * @param i
	 * @return
	 */
	private static String lpad(int i) {
		String s = String.valueOf(i);
		s = ("00" + s).substring(s.length());
		return s;
	}
	
	/**
	 * Function to generates the xml for the inStatus 
	 * @param inStatus
	 * @param validity
	 * @param srcAvailBalBeforeRecharge
	 * @return
	 */
	public static String getInStatusXML(String inStatus,String validity,String srcAvailBalBeforeRecharge){
		StringBuilder str = getXML("status",inStatus);
		str.append(getXML("validity",validity));
		str.append(getXML("balAftrRec",srcAvailBalBeforeRecharge));
		return getXML("error",getXML("in",str.toString()).toString()).toString();
	}
	
	/**
	 * Function to generates the xml for the selfcare 
	 * @param inStatus
	 * @param validity
	 * @param srcAvailBalBeforeRecharge
	 * @return
	 */
	public static String getSelfcareXML(String selfCareStatus,String srcAvailBalBeforeRecharge){
		StringBuilder str = getXML("status",selfCareStatus);
		str.append(getXML("balAftrRec",srcAvailBalBeforeRecharge));
		return getXML("error",getXML("selfcare",str.toString()).toString()).toString();
	}
	/**
	 * Function to add source aviable balance before recharge to inStatus 
	 * @param inStatus
	 * @param openingBalance
	 * @return
	 */
	public static String getInStatusXMLforAvaiBalBeforeRchg(String inStatus,
			String openingBalance) {
		if (inStatus != null) {
			return inStatus.replaceAll("null</balAftrRec>", openingBalance
					+ "</balAftrRec>");
		} else {
			return "";
		}

	}
	
	public static StringBuilder getXML(String xmlTag, String value){
		StringBuilder localXMLTag = new StringBuilder("<").append(xmlTag)
				.append(">").append(value).append("</").append(xmlTag).append(
						">");
		return localXMLTag;
	}
	
	
	// Add by harbans
	public static java.sql.Date getSqlDateFromString(String strDate,String strFormat) {

		SimpleDateFormat sdf;
		try {
			sdf = new SimpleDateFormat(strFormat);
			return new java.sql.Date(sdf.parse(strDate).getTime());
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			logger.info("Exception occured while parsign date", e);
			return null;
		} 
	}
	
	public static String convertDateFormat(String strDate,String strFromFormat,String strToFormat) {

		SimpleDateFormat sdfFrom;
		SimpleDateFormat sdfTo;
		try {
			sdfFrom = new SimpleDateFormat(strFromFormat);
			sdfTo = new SimpleDateFormat(strToFormat);
			java.util.Date date = sdfFrom.parse(strDate);
			return sdfTo.format(date);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			logger.info("Exception occured while parsign date", e);
			return null;
		} 
	}
	
	public static String replaceNullBySpace(String str)
	{
		if(str == null || "null".equalsIgnoreCase(str))
			return "";
		else
			return str;
	}

	public static String getDateAsString(Date date, String format) {
			
			format = (format == null) ? "dd-MM-yyyy-hh-mm-ss" : format;
			date = (date == null) ? new Date(): date;
			SimpleDateFormat sdf = new SimpleDateFormat(format);
	        return sdf.format(date);		
		}
	
}
