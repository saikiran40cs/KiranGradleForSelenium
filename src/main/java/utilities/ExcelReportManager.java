package utilities;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

import org.apache.poi.ss.usermodel.BorderStyle;
import org.apache.poi.ss.usermodel.FillPatternType;
import org.apache.poi.ss.usermodel.HorizontalAlignment;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFCellStyle;
import org.apache.poi.xssf.usermodel.XSSFFont;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.testng.Reporter;

import functionLibrary.PathConstants;

public class ExcelReportManager {

	// Create an Report in excel
	XSSFWorkbook workbook;
	XSSFSheet sheet;
	String sheetName = "TestSuite Report";

	/**
	 * Function to generate excel report based on the test scripts
	 * 
	 * @author saikiran.nataraja
	 * @return
	 * @throws Exception
	 */
	public void initializeExcelReport() throws IOException {
		File reportFile = new File(PathConstants.REP_INPUT_FILE);
		int rowNum = 0;
		if (reportFile.exists()) {
			Reporter.log("reportFile exists");
		} else {
			// Create an Report in excel
			workbook = new XSSFWorkbook();
			// Create an excel in the prescribed format
			FileOutputStream writeXLOutput = null;
			try {
				XSSFCellStyle topHeaderContents = workbook.createCellStyle();
				XSSFCellStyle tableHeader = workbook.createCellStyle();
				XSSFCellStyle tableContentsOnLeft = workbook.createCellStyle();
				XSSFCellStyle tableContentsOnRight = workbook.createCellStyle();
				XSSFRow row;
				// Header Column Names
				XSSFCell celAppName;
				XSSFCell celTestName;
				XSSFCell celTestStatus;
				XSSFCell celExecStartTime;
				XSSFCell celExecEndTime;
				XSSFCell celComments;
				// Set the Font details for the entire sheet
				XSSFFont defaultFont;
				XSSFFont headerFont;
				headerFont = workbook.createFont();
				headerFont.setFontHeightInPoints((short) 11);
				headerFont.setFontName("Calibri");
				headerFont.setColor(IndexedColors.WHITE.getIndex());
				headerFont.setBold(true);
				headerFont.setItalic(false);

				defaultFont = workbook.createFont();
				defaultFont.setFontHeightInPoints((short) 11);
				defaultFont.setFontName("Calibri");
				defaultFont.setColor(IndexedColors.BLACK.getIndex());
				defaultFont.setBold(true);
				defaultFont.setItalic(false);

				// create style for cells in header row
				topHeaderContents.setFont(headerFont);
				topHeaderContents.setFillPattern(FillPatternType.NO_FILL);
				topHeaderContents.setFillForegroundColor(IndexedColors.DARK_BLUE.getIndex());
				topHeaderContents.setFillPattern(FillPatternType.SOLID_FOREGROUND);
				// Set the border style for the workbook
				topHeaderContents.setAlignment(HorizontalAlignment.LEFT);

				// create style for cells in header row
				tableHeader.setFont(defaultFont);
				tableHeader.setFillPattern(FillPatternType.NO_FILL);
				tableHeader.setFillForegroundColor(IndexedColors.GREY_25_PERCENT.getIndex());
				tableHeader.setFillPattern(FillPatternType.SOLID_FOREGROUND);
				// Set the border style for the workbook
				tableHeader.setBorderBottom(BorderStyle.THIN);
				tableHeader.setBorderLeft(BorderStyle.THIN);
				tableHeader.setBorderRight(BorderStyle.THIN);
				tableHeader.setBorderTop(BorderStyle.THIN);
				tableHeader.setAlignment(HorizontalAlignment.LEFT);

				// create style for cells in table contents
				tableContentsOnRight.setFillBackgroundColor(IndexedColors.BLACK.getIndex());
				tableContentsOnRight.setFillPattern(FillPatternType.NO_FILL);
				tableContentsOnRight.setWrapText(false);
				// Set the border style for the workbook
				tableContentsOnRight.setBorderBottom(BorderStyle.THIN);
				tableContentsOnRight.setBorderLeft(BorderStyle.THIN);
				tableContentsOnRight.setBorderRight(BorderStyle.THIN);
				tableContentsOnRight.setBorderTop(BorderStyle.THIN);
				tableContentsOnRight.setAlignment(HorizontalAlignment.RIGHT);

				// create style for cells in table contents
				tableContentsOnLeft.setFillBackgroundColor(IndexedColors.BLACK.getIndex());
				tableContentsOnLeft.setFillPattern(FillPatternType.NO_FILL);
				// tableContentsOnLeft.setWrapText(false);
				// Set the border style for the workbook
				tableContentsOnLeft.setBorderBottom(BorderStyle.THIN);
				tableContentsOnLeft.setBorderLeft(BorderStyle.THIN);
				tableContentsOnLeft.setBorderRight(BorderStyle.THIN);
				tableContentsOnLeft.setBorderTop(BorderStyle.THIN);
				tableContentsOnLeft.setAlignment(HorizontalAlignment.LEFT);

				// Create worksheet for individual test case level details
				sheet = workbook.createSheet(sheetName);
				XSSFCellStyle hiddenstyle = workbook.createCellStyle();
				hiddenstyle.setHidden(true);
				row = sheet.createRow(rowNum++);
				// Header Column Names
				celAppName = row.createCell(0);
				// Merges the cells
				CellRangeAddress cellRangeAddress = new CellRangeAddress(0, 0, 0, 1);
				celAppName.setCellValue("Automated Test Execution Report");
				celAppName.setCellStyle(topHeaderContents);
				sheet.addMergedRegion(cellRangeAddress);

				row = sheet.createRow(rowNum++);
				celAppName = row.createCell(0);
				celAppName.setCellValue("");

				String formulaToGetCount = "C10:C100";
				row = sheet.createRow(rowNum++);
				celTestName = row.createCell(0);
				celTestStatus = row.createCell(1);
				celTestName.setCellValue("Number of Test cases passed");
				celTestStatus.setCellFormula("COUNTIFS('TestSuite Report'!" + formulaToGetCount + ",\"PASSED\")");
				celTestName.setCellStyle(tableContentsOnLeft);
				celTestStatus.setCellStyle(tableContentsOnRight);

				row = sheet.createRow(rowNum++);
				celTestName = row.createCell(0);
				celTestStatus = row.createCell(1);
				celTestName.setCellValue("Number of Test cases failed");
				celTestStatus.setCellFormula("COUNTIFS('TestSuite Report'!" + formulaToGetCount + ",\"FAILED\")");
				celTestName.setCellStyle(tableContentsOnLeft);
				celTestStatus.setCellStyle(tableContentsOnRight);

				row = sheet.createRow(rowNum++);
				celTestName = row.createCell(0);
				celTestStatus = row.createCell(1);
				celTestName.setCellValue("Number of Test cases Not Executed");
				celTestStatus.setCellFormula("COUNTIFS('TestSuite Report'!" + formulaToGetCount + ",\"NOT EXECUTED\")");
				celTestName.setCellStyle(tableContentsOnLeft);
				celTestStatus.setCellStyle(tableContentsOnRight);

				row = sheet.createRow(rowNum++);
				celTestName = row.createCell(0);
				celTestStatus = row.createCell(1);
				celTestName.setCellValue("TOTAL");
				celTestStatus.setCellFormula("SUM(B3:B5)");
				celTestName.setCellStyle(topHeaderContents);
				celTestStatus.setCellStyle(topHeaderContents);

				sheet.autoSizeColumn(0);
				sheet.autoSizeColumn(1);
				sheet.autoSizeColumn(2);
				sheet.autoSizeColumn(3);
				sheet.autoSizeColumn(4);
				sheet.autoSizeColumn(5);

				// Individual test case level details
				workbook.setActiveSheet(0);

				row = sheet.createRow(rowNum++);
				celAppName = row.createCell(0);
				celAppName.setCellValue("");

				row = sheet.createRow(rowNum++);
				// Header Column Names
				cellRangeAddress = new CellRangeAddress(1, 1, 0, 1);
				celAppName = row.createCell(0);
				celAppName.setCellValue("Test case level execution details");
				celAppName.setCellStyle(topHeaderContents);
				sheet.addMergedRegion(cellRangeAddress);

				row = sheet.createRow(rowNum++);
				// Header Column Names
				celTestName = row.createCell(0);
				celTestStatus = row.createCell(1);
				celAppName = row.createCell(2);
				celExecStartTime = row.createCell(3);
				celExecEndTime = row.createCell(4);
				celComments = row.createCell(5);

				// Set the Header names for the sheet
				celTestName.setCellValue("Name of the Executed Test Script");
				celTestStatus.setCellValue("Execution Status");
				celAppName.setCellValue("Application Name");
				celExecStartTime.setCellValue("Execution Start Time");
				celExecEndTime.setCellValue("Execution End Time");
				celComments.setCellValue("Comments");

				// set style for the table header
				celAppName.setCellStyle(tableHeader);
				celTestName.setCellStyle(tableHeader);
				celTestStatus.setCellStyle(tableHeader);
				celExecStartTime.setCellStyle(tableHeader);
				celExecEndTime.setCellStyle(tableHeader);
				celComments.setCellStyle(tableHeader);

				// If you require it to make the entire directory path including parents,use
				// directory.mkdirs(); here instead.
				File directory = new File(reportFile.getParent());
				if (!directory.exists()) {
					directory.mkdirs();
				}
				writeXLOutput = new FileOutputStream(PathConstants.REP_INPUT_FILE);
				workbook.write(writeXLOutput);
			} catch (Exception e) {
				// Conversion into unchecked exception is also allowed
				Reporter.log(" The issue in Excel file creation is " + e.toString());
				throw new IOException(e);
			} finally {
				// Close files when they are no longer needed
				workbook.close();
				if (writeXLOutput != null) {
					writeXLOutput.close();
				}
			}
//			PathConstants.setREPORTXLS(new XL_Reader(Address.REP_INPUT_FILE));
		}
	}

}
