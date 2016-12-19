
//Define calendar(s): addCalendar ("Unique Calendar Name", "Window title", "Form element's name", Form name")
//addCalendar("Calendar1", "Select Date", "UM_Date", "headerform");
addCalendar("Calendar1", "Select Date", "txtSefReceivedDate", "CreateSEFBean");
addCalendar("Calendar2", "Select Date", "txtDOB", "CreateSEFBean");
addCalendar("Calendar3", "Select Date", "txtCardExpiryDate", "CreateSEFBean");
addCalendar("Calendar4", "Select Date", "txtPassportExpiryDate", "CreateSEFBean");
addCalendar("Calendar5", "Select Date", "txtCustomerFilledDate", "CreateSEFBean");
addCalendar("Calendar11", "Select Date", "txtcrdate", "SearchBean");
addCalendar("Calendar12", "Select Date", "txtrcdate", "SearchBean");
addCalendar("Calendar6", "Select Date", "txtstartdate", "BillCycleRuleBean");
addCalendar("Calendar7", "Select Date", "txtenddate", "BillCycleRuleBean");
addCalendar("DOB", "Select Date", "txtDOB", "CreateSIFBean");
addCalendar("CalendarWddngAnnvrsry", "Select Date", "txtWddngAnnvrsry", "CreateSIFBean");
addCalendar("CalendarAgencyDate", "Select Date", "txtAgencyDate", "CreateSIFBean");
addCalendar("SIFProgressStartDate", "Select Date", "toDate1", "SIFProgressReportBean");
addCalendar("SIFProgressEndDate", "Select Date", "toDate2", "SIFProgressReportBean");
addCalendar("OpenRejectedCasesStartDate", "Select Date", "toDate1", "OpenRejectedCasesBean");
addCalendar("OpenRejectedCasesEndDate", "Select Date", "toDate2", "OpenRejectedCasesBean");
addCalendar("SEFStartDate", "Select Date", "toDate1", "SEFReportBean");
addCalendar("SEFEndDate", "Select Date", "toDate2", "SEFReportBean");
addCalendar("Calendar13", "Select Date", "txtcrdate1", "SearchBean");
addCalendar("Calendar14", "Select Date", "txtrcdate1", "SearchBean");
addCalendar("Calendar15", "Select Date", "txtDob", "SearchBean");
addCalendar("Calendar16", "Select Date", "start_dt", "ScrollBean");
addCalendar("Calendar17", "Select Date", "end_dt", "ScrollBean");
addCalendar("welcomeStartDate", "Select Date", "toDate1", "welcomeLetterBeans");
addCalendar("welcomeEndDate", "Select Date", "toDate2", "welcomeLetterBeans");

addCalendar("StartDate", "Select Date", "toDate1", "custCareBean");
addCalendar("EndDate", "Select Date", "toDate2", "custCareBean");

//Added by Avadesh on 19/06/2006
addCalendar("RepStartDate", "Select Date", "toDate1", "fileUploadForm");

addCalendar("RepEndDate", "Select Date", "toDate2", "fileUploadForm");
//Ended by Avadesh on 19/06/2006
//Added by Parul on 05/12/2007

addCalendar("HBOStartDate", "Select Date", "toDate1", "HBOFileUploadForm");
addCalendar("HBOEndDate", "Select Date", "toDate2", "HBOFileUploadForm");

//Ended by Parul on 05/12/2007
//Added by Avadesh on 28/07/2006
addCalendar("Calendar20", "Select Date", "txtFrom", "ActivationTATForm");
addCalendar("Calendar21", "Select Date", "txtTo", "ActivationTATForm");
//Ended by Avadesh on 28/07/2006

//Added by Amit Sethi on 31/07/2006
addCalendar("SEFStartDate2", "Select Date", "toDate1", "AVReportBean");
addCalendar("SEFEndDate2", "Select Date", "toDate2", "AVReportBean");
//Ended by Amit Sethi on 31/07/2006

//Added by Avadesh on 21/11/2006
addCalendar("swappingSEFDate", "Select Date", "toDate1", "SIFProgressReportBean");
addCalendar("auditorSEFDate", "Select Date", "toDate1", "AuditorSEFForm");
addCalendar("connectSEFDate", "Select Date", "toDate1", "AuditorSEFForm");


//Ended by Avadesh on 21/11/2006

// default settings for English
// Uncomment desired lines and modify its values 
// setFont("verdana", 9);
 setWidth(90, 1, 15, 1);
// setColor("#cccccc", "#cccccc", "#ffffff", "#ffffff", "#333333", "#cccccc", "#333333");
// setFontColor("#333333", "#333333", "#333333", "#ffffff", "#333333");
// setFormat("yyyy/mm/dd");
// setSize(200, 200, -200, 16);

// setWeekDay(0);
// setMonthNames("January", "February", "March", "April", "May", "June", "July", "August", "September", "October", "November", "December");
// setDayNames("Sunday", "Monday", "Tuesday", "Wednesday", "Thursday", "Friday", "Saturday");
// setLinkNames("[Close]", "[Clear]");
