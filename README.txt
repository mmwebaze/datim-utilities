BASE URL SETTING
This can be changed from the DatimUtilityConstants.java file. Eventually a simple form will be added to make it easier to supply this parameter.

EXCEL SHEET TO SCAN
ExcelScan.java has the functionality that reads excel files with the site ids that need to be updated. For the moment the path to the Excel file to be scanned will have 
to be supplied manually. A simple form will eventually be added to make it easier to select the file to be scanned.

Controllers
SiteUpdateController.java has the different end points one can access. For the moment "/updateOrgUnit" is available.

Once the changes have been made, build using mavan and run application. 