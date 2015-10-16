Requirements
** Java >= 7
** Maven for building

BASE URL SETTING
This parameter can be changed from the application.properties file located /DatimUtility/src/main/resources/

WORKING DIRECTORY
This parameter can be changed from the application.properties file located /DatimUtility/src/main/resources/

USERNAME & PASSWORD
These parameters can be changed from the application.properties file located /DatimUtility/src/main/resources/

EXCEL SHEET TO SCAN
ExcelScan.java has the functionality that reads excel files with the site ids that need to be updated. For the moment the path to the Excel file to be scanned will have 
to be supplied manually. A simple form will eventually be added to make it easier to select the file to be scanned.

End points
localhost:8080/ or index or home
This is the home page of this application.

localhost:8080/updateOrgUnitForm
This end point will update site names associated with site ids scanned from an excel sheet.

localhost:8080/test
This end point will return a list of Organisation Unit Levels if configurations are fine else it will return a blank page. Use this to test your settings.
Once the changes have been made, build using mavan and run as a Spring boot application. 