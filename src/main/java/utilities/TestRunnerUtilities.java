package utilities;

import org.testng.Reporter;

import functionLibrary.PathConstants;

public class TestRunnerUtilities {

	/**
	 * @author saikirannataraja
	 * @param xls
	 * @param testCaseName
	 * @returns true if run mode of the test case is set to Y
	 */

	public static boolean isTestCaseRunnable(ExcelReadWriter xls, String testCaseName, String TESTCASES_WORKSHEET,
			String TESTCASES_TESTCASEID) {
		boolean isExecutable = false;
		for (int i = 2; i <= xls.getRowCount(TESTCASES_WORKSHEET); i++) {
			if (xls.getCellData(TESTCASES_WORKSHEET, TESTCASES_TESTCASEID, i).equalsIgnoreCase(testCaseName)) {
				if (xls.getCellData(TESTCASES_WORKSHEET, PathConstants.RUNMODE, i).equalsIgnoreCase(PathConstants.YES)) {
					isExecutable = true;
				} else {
					isExecutable = false;
				}
			}
		}
		return isExecutable;
	}

	/**
	 * @author saikirannataraja
	 * @param xls
	 * @param testCaseName
	 * @param browserSelection
	 * @returns true if run on browser ( firefox, ie, chrome)set to yes
	 */
	public static boolean runTestcaseOnBrowser(ExcelReadWriter xls, String testCaseName, String browserSelection) {
		boolean selectedBrowser = false;
		for (int i = 2; i <= xls.getRowCount(PathConstants.TESTCASES_WORKSHEET); i++) {
			if (xls.getCellData(PathConstants.TESTCASES_WORKSHEET, PathConstants.TESTCASES_TESTCASEID, i)
					.equalsIgnoreCase(testCaseName)) {
				switch (browserSelection) {
				case "firefox":
					if (xls.getCellData(PathConstants.TESTCASES_WORKSHEET, PathConstants.RUN_ON_FIREFOX, i)
							.equalsIgnoreCase(PathConstants.YES)) {
						selectedBrowser = true;
					}
					break;
				case "chrome":
					if (xls.getCellData(PathConstants.TESTCASES_WORKSHEET, PathConstants.RUN_ON_CHROME, i)
							.equalsIgnoreCase(PathConstants.YES)) {
						selectedBrowser = true;
					}
					break;
				case "ie":
					if (xls.getCellData(PathConstants.TESTCASES_WORKSHEET, PathConstants.RUN_ON_IE, i).equalsIgnoreCase(PathConstants.YES)) {
						selectedBrowser = true;
					}
					break;
				default:
					Reporter.log(
							"The browser you have mentioned in xml file suite file is not updated in test data excel sheet");
					break;
				}
			}
		}
		return selectedBrowser;
	}

}
