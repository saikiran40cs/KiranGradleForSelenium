/*'*************************************************************************************************************************************************
' Class Name			: PathConstants
' Description			: Used to store the variables that are used across.
' How to Use			:
'-----------------------------------------------------------------
' Author                    Version          Creation Date         
'-----------------------------------------------------------------
' Sai Kiran Nataraja         v1.0             05-November-2018		
'*************************************************************************************************************************************************
 */
package functionLibrary;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

/** @author saikiran.nataraja */

public final class PathConstants {

	public static int minWaitTime = 3000;
	// for additional accuracy use the String format for date as
	// "ddmmyyyy_hhmmss_sss_"
	public static final String DateFormat = new SimpleDateFormat("ddMMyyyy_HHmmss_").format(new Date());
	public static final String currentDateFormat = new SimpleDateFormat("ddMMyyyy").format(new Date());
	public final static String WEEK_NUMBER = new SimpleDateFormat("w").format(new java.util.Date());
	// For Report start time and End time format
	private static SimpleDateFormat DateFormatSettings;
	public static SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss", Locale.ENGLISH);
	public static final String fs = File.separator;
	public static final String log4j2Path = System.getProperty("user.dir") + fs + "resources" + fs + "configuration"
			+ fs + "log4j2.properties";
	public static final String configPath = System.getProperty("user.dir") + fs + "resources" + fs + "configuration"
			+ fs;
	public static final String testDataPath = System.getProperty("user.dir") + fs + "resources" + fs + "testData" + fs;
	public static String ReportPath = System.getProperty("user.dir") + fs + "gradle" + fs + "ExtentReports" + fs
			+ "Week" + PathConstants.WEEK_NUMBER + fs;
	public static final String downloadsPath = System.getProperty("user.dir") + fs + "downloads" + fs;
	public static String ErrorReportPath = System.getProperty("user.dir") + fs + "gradle" + fs + "ExtentReports" + fs
			+ "Week" + PathConstants.WEEK_NUMBER + fs;
	public static final String REP_INPUT_FILE = System.getProperty("user.dir") + fs + "gradle" + fs + "ExtentReports"
			+ fs + "Week" + PathConstants.WEEK_NUMBER + fs + currentDateFormat + "_AutomationReport.xlsx";

	// Driver path location
	public static final String firefoxDriverPath = configPath + fs + "geckodriver_win32_v0.17.0.exe";
	public static final String chromeDriverPath = configPath + fs + "chromedriver_win32_v2.42.exe";
	public static final String IEDriverPath = configPath + fs + "IEDriverServer_Win32_v3.4.0.exe";

	/** Test Data Worksheet part for all applications */
	public static final String TESTCASES_WORKSHEET = "Testcases";
	public static final String TESTCASES_TESTCASEID = "TestCaseID";
	public static final String TESTDATA_WORKSHEET = "ApplicationLevelTestData";

	/** RUN MODES FOR BROWSERS */
	public static final String RUNMODE = "Runmode";
	public static final String RUN_ON_FIREFOX = "RunOnFirefox";
	public static final String RUN_ON_CHROME = "RunOnChrome";
	public static final String RUN_ON_IE = "RunOnIE";
	public static final String RUN_ON_SAFARI = "RunOnSafari";
	public static final String RUN_ON_OPERA = "RunOnOpera";
	public static final String RUN_ON_HEADLESS = "RunOnHeadless";
	public static final String YES = "Y";

	private PathConstants() {
		throw new IllegalAccessError("PathConstants class");
	}

	/**
	 * @return the dateFormatSettings
	 */
	public static SimpleDateFormat getDateFormatSettings() {
		return DateFormatSettings;
	}

	/**
	 * @param dateFormatSettings the dateFormatSettings to set
	 */
	public static void setDateFormatSettings() {
		DateFormatSettings = new SimpleDateFormat("dd-MM-yyyy hh:mm:ss", Locale.ENGLISH);
	}
}
