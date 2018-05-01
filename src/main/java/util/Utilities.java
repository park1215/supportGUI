package util;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Properties;
import java.util.Random;
import java.util.Set;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.apache.commons.io.FileUtils;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

public class Utilities {

	public static final String RESOURCES_FOLDER = ".\\resources\\data\\";
	public static final String SCREENSHOTS_FOLDER = ".\\TestOutput\\screenshots\\";

	public static Properties loadPropertyFile(String fileName) {
		Properties prop = new Properties();
		FileInputStream fis = null;

		try {
			fis = new FileInputStream(RESOURCES_FOLDER + fileName);
			System.out.println("Successfully load properties file: " + fileName.toString());
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		try {
			prop.load(fis);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return prop;
	}

	public static String captureScreenshot(WebDriver driver, String screenshotName) {
		try {
			TakesScreenshot ts = (TakesScreenshot) driver;

			File source = ts.getScreenshotAs(OutputType.FILE);

			String dest = "C:\\Temp\\centcomA\\Screenshots\\" + screenshotName + ".png";

			File destination = new File(dest);

			FileUtils.copyFile(source, destination);

			System.out.println("Screenshot taken");

			return dest;
		} catch (IOException e) {
			System.out.println("Exception while take a screenshot " + e.getMessage());
			return e.getMessage();
		}
	}

	public static Workbook loadExcelFile(String fileName) {

		String FILE_NAME = ".\\resources\\data\\" + fileName;

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

	public static Sheet getWorksheet(String fileName, String sheetName) {

		Workbook workbook = loadExcelFile(fileName);
		Sheet worksheet = workbook.getSheet(sheetName);

		return worksheet;
	}

	public static String getCellValue(Sheet worksheet, int rowNum, int cellNum) {

		Cell cell = worksheet.getRow(rowNum).getCell(cellNum);

		return null;
	}

	public static String getCellValueAsString(Cell cell) {

		Object cellValue = null;

		// Object returnObject; Just in case it is needed to return an object
		// type.

		if (cell.getCellTypeEnum() == CellType.STRING) {

			System.out.println(cell.getStringCellValue());

			cellValue = String.valueOf(cell.getStringCellValue());
		}

		else if (cell.getCellTypeEnum() == CellType.NUMERIC) {

			System.out.println(cell.getNumericCellValue());

			cellValue = String.valueOf(cell.getNumericCellValue());
		}

		else if (cell.getCellTypeEnum() == CellType.BOOLEAN) {
			System.out.println(cell.getBooleanCellValue());
			cellValue = String.valueOf(cell.getBooleanCellValue());
		} else if (cell.getCellTypeEnum() == CellType.BLANK) {
			System.out.println("N/A");
			cellValue = "N/A";
		}

		return (String) cellValue;
	}

	public static String getCellValue(String fileName, String sheetName, int rowNum, int cellNum) {

		Workbook workbook = loadExcelFile(fileName);

		Sheet worksheet = workbook.getSheet(sheetName);

		Cell cell = worksheet.getRow(rowNum).getCell(cellNum);

		Object cellValue = null;

		// Object returnObject; Just in case it is needed to return an object
		// type.

		if (cell.getCellTypeEnum() == CellType.STRING) {

			System.out.println(cell.getStringCellValue());

			cellValue = String.valueOf(cell.getStringCellValue());
		}

		else if (cell.getCellTypeEnum() == CellType.NUMERIC) {

			System.out.println(cell.getNumericCellValue());

			cellValue = String.valueOf(cell.getNumericCellValue());
		}

		else if (cell.getCellTypeEnum() == CellType.BOOLEAN) {
			System.out.println(cell.getBooleanCellValue());
			cellValue = String.valueOf(cell.getBooleanCellValue());
		} else if (cell.getCellTypeEnum() == CellType.BLANK) {
			System.out.println("N/A");
			cellValue = "N/A";
		}

		return (String) cellValue;
	}

	public static HashMap<String, String> getRowCellMap(Sheet sheet, int currentRow) {

		HashMap<String, String> dataSet = new HashMap<String, String>();

		Row headerRow = sheet.getRow(0);
		Row dataRow = sheet.getRow(currentRow);

		Iterator<Cell> headerRowIterator = headerRow.iterator();
		Iterator<Cell> dataRowIterator = dataRow.iterator();

		while (dataRowIterator.hasNext()) {

			Cell headerCell = headerRowIterator.next();
			Cell dataCell = dataRowIterator.next();
			System.out.print(headerCell.getStringCellValue() + " : ");
			System.out.println(dataCell.getStringCellValue());

			dataSet.put(headerCell.getStringCellValue(), dataCell.getStringCellValue());

			// getCellValueAsString(dataCell);
		}

		System.out.println(dataSet.toString());

		return dataSet;
	}

	public static void traverseMap(HashMap<String, String> hashMap) {

		// HashMap<String, String> hashMap = getRowCellMap(sheet, 1);

		Set<String> set = hashMap.keySet();

		for (String key : hashMap.keySet()) {
			System.out.println(key + " - " + hashMap.get(key));

		}
	}
	
	public static String getRandomMac() {

		Random rand = new Random();

		byte[] macAddr = new byte[6];

		rand.nextBytes(macAddr);

		// zeroing last 2 bytes to make it unicast and locally adminstrated
		macAddr[0] = (byte) (macAddr[0] & (byte) 254); 

		StringBuilder sb = new StringBuilder(18);

		for (byte b : macAddr) {

			if (sb.length() > 0)
				sb.append(":");

			sb.append(String.format("%02x", b));
		}

		sb = sb.replace(0, 8, "00:A0:BC");

		return sb.toString().toUpperCase();
	}
	
}