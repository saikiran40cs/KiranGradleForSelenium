package utilities;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.lang.reflect.Constructor;
import java.util.Calendar;
import java.util.Iterator;

import org.apache.commons.collections4.Get;
import org.apache.poi.ss.usermodel.BuiltinFormats;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.DateUtil;
import org.apache.poi.ss.usermodel.FillPatternType;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.testng.Reporter;

public class ExcelReadWriter {

	private String path;
	private FileInputStream fis = null;
	private XSSFWorkbook workbook;
	private XSSFSheet sheet = null;
	private XSSFRow row = null;
	private XSSFCell cell = null;

	/**
	 * @see Constructor to Instantiate ReadWriter Class
	 * @param path
	 * @throws IOException
	 */
	public ExcelReadWriter(String path) throws IOException {
		fis = null;
		sheet = null;
		row = null;
		cell = null;
		this.path = path;
		try {
			fis = new FileInputStream(path);
			workbook = new XSSFWorkbook(fis);
			sheet = workbook.getSheetAt(0);
		} catch (Exception e) {
			throw e;
		} finally {
			if (fis != null) {
				fis.close();
			}
		}

	}

	/**
	 * @author saikiran.nataraja
	 * @param sheetName
	 * @returns 0 - if index is -1, else returns the last row number
	 */
	public int getRowCount(String sheetName) {
		int index = workbook.getSheetIndex(sheetName);
		if (index == -1)
			return 0;
		else {
			sheet = workbook.getSheetAt(index);
			int number = sheet.getLastRowNum() + 1;
			return number;
		}
	}

	/**
	 * @author saikirannataraja
	 * @see Get Column Count for a particular row in a worksheet
	 * @param sheetName
	 * @param rowNumber
	 * @return
	 */
	public int getColCountForParticularRow(String sheetName, int rowNumber) {
		int index = workbook.getSheetIndex(sheetName);
		if (index == -1)
			return 0;
		else {
			sheet = workbook.getSheetAt(index);
			int noOfColumns = sheet.getRow(rowNumber).getPhysicalNumberOfCells(); // getLastCellNum();
			return noOfColumns;
		}

	}

	/**
	 * @author saikirannataraja
	 * @see find whether sheets exists or not in the workbook
	 * @param sheetName
	 * @returns true or false
	 */
	public boolean isSheetExist(String sheetName) {
		int index = workbook.getSheetIndex(sheetName);
		if (index == -1) {
			index = workbook.getSheetIndex(sheetName.toUpperCase());
			if (index == -1)
				return false;
			else
				return true;
		} else
			return true;
	}

	/**
	 * @author saikirannataraja
	 * @see getColumnCount is used to get the column count in the sheet passed
	 * @param sheetName
	 * @returns number of columns in a sheet
	 */
	public int getColumnCount(String sheetName) { // check if sheet exists
		if (!isSheetExist(sheetName))
			return -1;

		sheet = workbook.getSheet(sheetName);
		row = sheet.getRow(0);

		if (row == null)
			return -1;

		return row.getLastCellNum(); // return row.getPhysicalNumberOfCells();
	}

	/**
	 * @author saikiran.nataraja
	 * @param cellContent
	 * @see Function to find the row number of the specific string available in the
	 *      excel
	 * @returns Row number of the String present, Otherwise returns 0
	 */
	public int findRowNumber(String cellContent) {
		sheet = workbook.getSheetAt(workbook.getActiveSheetIndex());
		for (Row row : sheet) {
			for (Cell cell : row) {
				if (cell.getCellType() == CellType.STRING) {
					if (cell.getRichStringCellValue().getString().trim().equalsIgnoreCase(cellContent)) {
						return row.getRowNum();
					}
				}
			}
		}
		return 0;
	}

	/**
	 * @see Function to autosize columns in a workbook
	 * @author saikiran.nataraja
	 */
	public void autoSizeColumns() {
		sheet = workbook.getSheetAt(workbook.getActiveSheetIndex());
		if (sheet.getPhysicalNumberOfRows() > 0) {
			Row row = sheet.getRow(0);
			Iterator<Cell> cellIterator = row.cellIterator();
			while (cellIterator.hasNext()) {
				Cell cell = cellIterator.next();
				int columnIndex = cell.getColumnIndex();
				sheet.autoSizeColumn(columnIndex);
			}
		}
	}

	/**
	 * @see Function to get cell data based on the sheet and column name
	 * @param sheetName
	 * @param colName
	 * @param rowNum
	 * @returns the cell information
	 */
	public String getCellData(String sheetName, String colName, int rowNum) {
		try {
			if (rowNum <= 0)
				return "";

			int index = workbook.getSheetIndex(sheetName);
			int col_Num = -1;
			if (index == -1)
				return "";

			sheet = workbook.getSheetAt(index);
			row = sheet.getRow(0);
			for (int i = 0; i < row.getLastCellNum(); i++) {
				if (row.getCell(i).getStringCellValue().trim().equals(colName.trim()))
					col_Num = i;
			}
			if (col_Num == -1)
				return "@null";

			sheet = workbook.getSheetAt(index);
			row = sheet.getRow(rowNum - 1);
			if (row == null)
				return "";
			cell = row.getCell(col_Num);

			if (cell == null)
				return "";

			if (cell.getCellType() == CellType.STRING)
				return cell.getStringCellValue();
			else if (cell.getCellType() == CellType.NUMERIC || cell.getCellType() == CellType.FORMULA) {

				String cellText = String.valueOf(cell.getNumericCellValue());
				if (DateUtil.isCellDateFormatted(cell)) {
					// format in form of M/D/YY
					double d = cell.getNumericCellValue();

					Calendar cal = Calendar.getInstance();
					cal.setTime(DateUtil.getJavaDate(d));
					cellText = (String.valueOf(cal.get(Calendar.YEAR))).substring(2);
					cellText = cal.get(Calendar.DAY_OF_MONTH) + "/" + cal.get(Calendar.MONTH) + 1 + "/" + cellText;
				}
				return cellText;
			} else if (cell.getCellType() == CellType.BLANK)
				return "";
			else
				return String.valueOf(cell.getBooleanCellValue());

		} catch (Exception e) {
			Reporter.log("File IO Exception in XL_Reader.java row " + rowNum + " or column " + colName
					+ " does not exist in xls" + e.toString());
			throw e;
		}
	}

	/**
	 * @see Function to get cell data from Particular test case
	 * @author saikiran.nataraja
	 * @param ExcelFileName
	 * @param sheetName
	 * @param TCName
	 * @param colName
	 * @returns the cell data in String format @throws Exception
	 */
	public String getCellDataFromParticularTestCase(String ExcelFileName, String sheetName, String TCName,
			String colName) throws Exception {
		int rowNum = 0;
		try {
			workbook = new XSSFWorkbook(ExcelFileName);
			int index = workbook.getSheetIndex(sheetName);
			int col_Num = -1;
			if (index == -1)
				return "";

			sheet = workbook.getSheetAt(index);
			rowNum = findRowNumber(TCName) + 1;
			row = sheet.getRow(rowNum);
			for (int i = 0; i < row.getLastCellNum(); i++) {
				if (row.getCell(i).getStringCellValue().trim().equals(colName.trim())) {
					Reporter.log(row.getCell(i).getStringCellValue().trim());
					col_Num = i;
					break;
				}
			}
			if (col_Num == -1)
				return "@null";
			rowNum = rowNum + 1;
			row = sheet.getRow(rowNum);
			if (row == null)
				return "";
			cell = row.getCell(col_Num);

			if (cell == null)
				return "";
			if (cell.getCellType() == CellType.STRING)
				return cell.getStringCellValue();
			else if (cell.getCellType() == CellType.NUMERIC || cell.getCellType() == CellType.FORMULA) {

				String cellText = String.valueOf(cell.getNumericCellValue());
				if (DateUtil.isCellDateFormatted(cell)) {
					// format in form of M/D/YY
					double d = cell.getNumericCellValue();

					Calendar cal = Calendar.getInstance();
					cal.setTime(DateUtil.getJavaDate(d));
					cellText = (String.valueOf(cal.get(Calendar.YEAR))).substring(2);
					cellText = cal.get(Calendar.DAY_OF_MONTH) + "/" + cal.get(Calendar.MONTH) + 1 + "/" + cellText;
				}
				return cellText;
			} else if (cell.getCellType() == CellType.BLANK)
				return "";
			else
				return String.valueOf(cell.getBooleanCellValue());
		} catch (Exception e) {
			Reporter.log("File IO Exception in XL_Reader row " + rowNum + " or column " + colName
					+ " does not exist in xls" + e.toString());
			throw e;
		}
	}

	/**
	 * @author saikiran.nataraja
	 * @param sheetName
	 * @param colName
	 * @param rowNum
	 * @param data
	 * @see Function to set the data for a value from a test case
	 * @returns true or false if cell data is set @throws Exception
	 */
	public boolean setCellDataFromTestCase(String sheetName, String colName, String TestcaseName, String data)
			throws Exception {
		try {
			fis = new FileInputStream(path);
			workbook = new XSSFWorkbook(fis);
			int rowNum = 0;
			/**
			 * for(int i = 2; i <= PathConstants.getTCXLS().getRowCount(sheetName); i++) {
			 * if(PathConstants.getTCXLS().getCellData(sheetName,
			 * Address.TESTCASES_TESTCASEID, i).equalsIgnoreCase(TestcaseName)) { // Pass
			 * the Header Row Number for that test case so that it can get the required
			 * header rowNum = i + 1; break; } }
			 */

			if (rowNum <= 0)
				return false;

			int index = workbook.getSheetIndex(sheetName);
			int colNum = -1;
			if (index == -1)
				return false;
			sheet = workbook.getSheetAt(index);
			row = sheet.getRow(rowNum - 1); // Get the Header Row Name as per // test case //
			System.out.println("Column Count is:" + getColCountForParticularRow(sheetName, rowNum));
			for (int i = 0; i < getColCountForParticularRow(sheetName, rowNum); i++) { //
				System.out.println(row.getCell(i).getStringCellValue().trim());
				if (!(row.getCell(i).getStringCellValue().isEmpty())) {
					if (row.getCell(i).getStringCellValue().trim().equals(colName)) {
						colNum = i;
						break;
					}
				}
			}
			if (colNum == -1)
				return false;

			sheet.autoSizeColumn(colNum);
			row = sheet.getRow(rowNum);// Get the Value of Header Row passed as per test case
			if (row == null)
				row = sheet.createRow(rowNum - 1);

			cell = row.getCell(colNum);
			if (cell == null)
				cell = row.createCell(colNum);

			CellStyle cs = workbook.createCellStyle();
			cs.setWrapText(true);
			cs.setFillForegroundColor(IndexedColors.YELLOW.getIndex());
			cs.setFillPattern(FillPatternType.SOLID_FOREGROUND);
			// Set the default cell format as text
			cs.setDataFormat((short) BuiltinFormats.getBuiltinFormat("text"));
			// Added to make all formatting a text
			cell.setCellStyle(cs);
			cell.setCellValue(data);

			FileOutputStream fileOut = new FileOutputStream(path);
			workbook.write(fileOut);
			fileOut.close();
			workbook.close();
			fis.close();
		} catch (Exception e) {
			Reporter.log("File IO Exception in ExcelReadWriter.java" + e.toString());
			throw e;
		}
		return true;
	}

	/**
	 * @see Function to get row by row details from Excel file
	 * @param testDataPath
	 * @param testDataFileName
	 * @param testDataSheetName
	 * @returns Object array with the contents from excel @throws IOException
	 */
	public static Object[][] getRowByRowDataFromXL(String testDataPath, String testDataFileName,
			String testDataSheetName) throws IOException {
		Object[][] object;
		// Create a object of File class to open xlsx file
		File file = new File(testDataPath + File.separator + testDataFileName);
		// Create an object of FileInputStream class to read excel file
		FileInputStream inputStream = new FileInputStream(file);
		XSSFWorkbook kiranWorkbook = null; // Find the file extension by spliting file name in substing and getting only
		// extensionname
		String fileExtensionName = testDataFileName.substring(testDataFileName.indexOf('.'));
		// Check conditionif the file is xlsx file
		if (".xlsx".equalsIgnoreCase(fileExtensionName)) {
			kiranWorkbook = new XSSFWorkbook(inputStream);
		} // Check condition if the file is xls file
		else if (".xls".equalsIgnoreCase(fileExtensionName)) {
			// If it is xls file then create object of HSSFWorkbook class
			Reporter.log("Please pass an XLSX file");
		}
		// Read sheet inside the workbook by its name -keyword sheet
		Sheet kiranSheet = kiranWorkbook.getSheet(testDataSheetName); // Find number of rows in excel file
		int rowCount = kiranSheet.getLastRowNum() - kiranSheet.getFirstRowNum();
		object = new Object[rowCount][5]; // '5' is the maximum number of columns available in the excel
		for (int i = 0; i < rowCount; i++) { // Loop over all the rows
			Row row = kiranSheet.getRow(i + 1); // Create a loop to print cell values in a row
			for (int j = 0; j < row.getLastCellNum(); j++) {
				object[i][j] = row.getCell(j).toString();
			}
		}
		inputStream.close();
		kiranWorkbook.close();
		return object;
	}

}
