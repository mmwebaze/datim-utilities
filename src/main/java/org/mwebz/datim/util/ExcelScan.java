package org.mwebz.datim.util;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.springframework.stereotype.Component;

@Component
public class ExcelScan {

	public List<String> readSingleColumn(String file, int beginAtRow, int column) {
		List<String> siteIdList = new ArrayList<String>();
		try (
				InputStream inp = new FileInputStream(file);
				){
			Workbook wb = WorkbookFactory.create(inp);
			Sheet sheet = wb.getSheetAt(0);

			Row row = null;
			for (int i = beginAtRow; i <= sheet.getLastRowNum(); i++) {
				row = sheet.getRow(i);

				if (row.getCell(column) != null)
					siteIdList.add(row.getCell(column).toString());
			}
		} catch (InvalidFormatException|IOException ex) {
			System.out.println(ex.getLocalizedMessage());
		} 
		return siteIdList;
	}
	/**
	 * For the moment this function only supports scanning of 2 columns only
	 * @param file this is file name and location in the format C:/development/datim/xsl/car.xlsx for windows
	 * @param beginAtRow row in Excel sheet from which point you want to start the reading
	 * @param columns the different columns you want to read
	 * @return a HashMap with Key as site id and value as site name 
	 */
	public Map<String, String> readMultipleColumns(String file, int beginAtRow, int...columns) {
		Map<String, String> idNameMap = new HashMap<String, String>();
		
		try (
				InputStream inp = new FileInputStream(file);
				){
			Workbook wb = WorkbookFactory.create(inp);
			Sheet sheet = wb.getSheetAt(0);

			Row row = null;
			for (int i = beginAtRow; i <= sheet.getLastRowNum(); i++) {
				row = sheet.getRow(i);
				
				if (row.getCell(columns[0]) != null && row.getCell(columns[1]) != null)
					idNameMap.put(row.getCell(columns[0]).toString(), row.getCell(columns[1]).toString());
			}
		} catch (InvalidFormatException|IOException ex) {
			System.out.println(ex.getLocalizedMessage());
		} 
		return idNameMap;
	}
}
