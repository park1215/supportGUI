package Sandbox;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.testng.annotations.Test;

public class SandboxCodeTest {

	public static Workbook loadExcelFile(String fileName){
		
		String FILE_NAME = ".\\resources\\data\\"+fileName;
		
		FileInputStream excelFile = null;
		
		XSSFWorkbook workbook = null;
		
		try {
			excelFile = new FileInputStream(new File(FILE_NAME));
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		try {
			workbook = new XSSFWorkbook(excelFile);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return workbook;
	}
	
	public static Sheet getWorksheet(String fileName, String sheetName){
	
		Workbook workbook = loadExcelFile(fileName);
		Sheet worksheet = workbook.getSheet(sheetName);
		
		return worksheet;
	}
	
	public static String getCellValue(Sheet worksheet, int rowNum, int cellNum){
		
		Cell cell = worksheet.getRow(rowNum).getCell(cellNum);
		
		return null;
	}
	
	public static String getCellValueAsString(Cell cell){
		
			Object cellValue = null;
		
			//Object returnObject;  Just in case it is needed to return an object type.
		
			if(cell.getCellTypeEnum() == CellType.STRING){
			
				System.out.println(cell.getStringCellValue());
			
				cellValue = String.valueOf(cell.getStringCellValue());
			}
			
			else if (cell.getCellTypeEnum() == CellType.NUMERIC){
			
				System.out.println(cell.getNumericCellValue());
				
				cellValue = String.valueOf(cell.getNumericCellValue());
			}
			
			else if(cell.getCellTypeEnum() == CellType.BOOLEAN){
				System.out.println(cell.getBooleanCellValue());
				cellValue = String.valueOf(cell.getBooleanCellValue());
			}
			else if(cell.getCellTypeEnum() == CellType.BLANK){
				System.out.println("N/A");
				cellValue = "N/A";
			}
		
		return (String) cellValue;
	}
	
	
	
	public static String getCellValue(String fileName, String sheetName, int rowNum, int cellNum){
		
		Workbook workbook = loadExcelFile(fileName);
		
		Sheet worksheet = workbook.getSheet(sheetName);
		
		Cell cell = worksheet.getRow(rowNum).getCell(cellNum);

		Object cellValue = null;
		
		//Object returnObject;  Just in case it is needed to return an object type.
		
			if(cell.getCellTypeEnum() == CellType.STRING){
			
				System.out.println(cell.getStringCellValue());
			
				cellValue = String.valueOf(cell.getStringCellValue());
			}
			
			else if (cell.getCellTypeEnum() == CellType.NUMERIC){
			
				System.out.println(cell.getNumericCellValue());
				
				cellValue = String.valueOf(cell.getNumericCellValue());
			}
			
			else if(cell.getCellTypeEnum() == CellType.BOOLEAN){
				System.out.println(cell.getBooleanCellValue());
				cellValue = String.valueOf(cell.getBooleanCellValue());
			}
			else if(cell.getCellTypeEnum() == CellType.BLANK){
				System.out.println("N/A");
				cellValue = "N/A";
			}
		
		return (String) cellValue;
	}
	
	public static HashMap<String, String> getRowCellMap(Sheet sheet, int currentRow){
		
		HashMap<String, String> dataSet = new HashMap<String, String>();
		
		Row headerRow = sheet.getRow(0);
		Row dataRow = sheet.getRow(currentRow);
		
		Iterator<Cell> headerRowIterator = headerRow.iterator();
		Iterator<Cell> dataRowIterator = dataRow.iterator(); 
		
		while(dataRowIterator.hasNext()){
			
			Cell headerCell = headerRowIterator.next();
			Cell dataCell = dataRowIterator.next();
//			System.out.print(headerCell.getStringCellValue() + " : ");
//			System.out.println(dataCell.getStringCellValue());
			
			
			dataSet.put(headerCell.getStringCellValue(), dataCell.getStringCellValue());
			
			//getCellValueAsString(dataCell);
		}
		
		System.out.println(dataSet.toString());
		
		return dataSet;
	}

	
	@SuppressWarnings("deprecation")
	public static void main(String[] args) throws IOException {
		
		Sheet sheet = getWorksheet("Addresses.xlsx", "wb_direct");
		
		HashMap<String, String> hashMap = getRowCellMap(sheet, 1);
		
		Set<String> set = hashMap.keySet();
		
		for(String key : hashMap.keySet()){
			System.out.println(key + " - " + hashMap.get(key));
			
		}
		
/*
		String testString = getCellValue("Addresses.xlsx", "Prosperist", 1, 3);
		
		System.out.println("Test String : " + testString);
		
		String FILE_NAME = ".\\resources\\data\\Addresses.xlsx";

		FileInputStream excelFile = new FileInputStream(new File(FILE_NAME));

		XSSFWorkbook workbook = new XSSFWorkbook(excelFile);

		System.out.println("workbook name : " + workbook.toString());

		Sheet wb_directSheet = workbook.getSheet("wb_direct");

		System.out.println("worksheet name : " + wb_directSheet.getSheetName());

		Iterator<Row> iterator = wb_directSheet.iterator();

		Row headerRow = wb_directSheet.getRow(0);
		Row dataRow = wb_directSheet.getRow(1);
		
		Iterator<Cell> headerRowIterator = headerRow.iterator();
		Iterator<Cell> dataRowIterator = dataRow.iterator(); 
		
		Object cellValue = null;
		
		while(dataRowIterator.hasNext()){
			
			Cell headerCell = headerRowIterator.next();
			Cell dataCell = dataRowIterator.next();
			System.out.print(headerCell.getStringCellValue() + " : ");
			
			getCellValueAsString(dataCell);
//			
//			if(dataCell.getCellTypeEnum() == CellType.STRING){
//				
//				System.out.println(dataCell.getStringCellValue());
//			
//				cellValue = String.valueOf(dataCell.getStringCellValue());
//			}
//			
//			else if (dataCell.getCellTypeEnum() == CellType.NUMERIC){
//			
//				System.out.println(dataCell.getNumericCellValue());
//				
//				cellValue = String.valueOf(dataCell.getNumericCellValue());
//			}
//			
//			else if(dataCell.getCellTypeEnum() == CellType.BOOLEAN){
//				System.out.println(dataCell.getBooleanCellValue());
//				cellValue = String.valueOf(dataCell.getBooleanCellValue());
//			}
//			else if(dataCell.getCellTypeEnum() == CellType.BLANK){
//				System.out.println("N/A");
//				cellValue = "N/A";
//			}
			
//			if(dataCell.getCellTypeEnum() == CellType.STRING){
//				System.out.println(dataCell.getStringCellValue());
//			}
//			
//			else if (dataCell.getCellTypeEnum() == CellType.NUMERIC){
//				System.out.println(dataCell.getNumericCellValue());
//			}
//			else if(dataCell.getCellTypeEnum() == CellType.BOOLEAN){
//				System.out.println(dataCell.getBooleanCellValue());
//			}
//			else if(dataCell.getCellTypeEnum() == CellType.BLANK){
//				System.out.println("N/A");
//			}
		} */
	}
	
}