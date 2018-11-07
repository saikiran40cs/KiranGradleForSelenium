package functionLibrary;

import java.awt.AWTException;
import java.awt.HeadlessException;
import java.awt.Rectangle;
import java.awt.Robot;
import java.awt.Toolkit;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Properties;
import java.util.concurrent.TimeUnit;

import javax.imageio.ImageIO;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.Capabilities;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.testng.ITestResult;
import org.testng.Reporter;
import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Optional;
import org.testng.annotations.Parameters;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.MediaEntityBuilder;
import com.aventstack.extentreports.Status;
import com.aventstack.extentreports.reporter.ExtentHtmlReporter;

import utilities.ExcelReportManager;
import utilities.ExtentManager;
import utilities.LoggerUtility;

public class DriverFactory extends ExtentManager {

	protected String executingTestCaseName;
	private LoggerUtility loggerInstance;
	protected ExtentReports extentInstance;
	protected ExtentTest testInstance;
	protected ExtentHtmlReporter htmlReporterInstance;
	public final String getCurrentlyLoggedInUser = System.getProperty("user.name");
	protected WebDriver webdriver = null;
	protected String runOnBrowser = null;
	protected Properties OR = null;
	protected String baseURL = "";

	@BeforeSuite
	/**
	 * Function to change the Error Screenshot folder name before the Suite starts
	 * 
	 * @author saikiran.nataraja
	 */
	public void CreateErrorRepFolder() throws Exception {
		PathConstants.setDateFormatSettings(); // set the date format before starting each test
		PathConstants.ErrorReportPath = PathConstants.ReportPath + PathConstants.DateFormat + "ErrorScreenshots";
	}

	/**
	 * This is to create an extent manager instance before every new class
	 * 
	 * @author saikiran.nataraja
	 * @throws IOException
	 * @throws InterruptedException
	 */
	@BeforeClass
	public void createExtentInstance() throws IOException, InterruptedException {
		System.setProperty("log4j.configurationFile", PathConstants.log4j2Path);
		loggerInstance = new LoggerUtility();
		ExtentManager xtentMgr = new ExtentManager();
		extentInstance = xtentMgr.createExtentRep();
		ExcelReportManager xlRepMgr = new ExcelReportManager();
		xlRepMgr.initializeExcelReport();
		Thread.sleep(PathConstants.minWaitTime);
	}

	@Parameters({ "BrowserType" })
	@BeforeTest
	public void browserSetup(@Optional("chrome") String browser) {
		runOnBrowser = browser;
		DriverProfiles DrProf = new DriverProfiles();
		switch (runOnBrowser) {
		case "firefox":
			webdriver = new FirefoxDriver(DrProf.createFirefoxProfile());
			break;
		case "chrome":
			webdriver = new ChromeDriver(DrProf.createChromeCapabilites());
			break;
		case "ie":
			// Add Pop-up allowed in IE manually
			webdriver = new InternetExplorerDriver(DrProf.createIECapabilities());
			break;
		default:
			Reporter.log("Driver Not Found");
			break;
		}
		webdriver.manage().window().maximize();
		webdriver.manage().deleteAllCookies();
		webdriver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
		webdriver.manage().timeouts().pageLoadTimeout(60, TimeUnit.SECONDS);
	}

	/**
	 * Function to return browser version
	 * 
	 * @author saikiran.nataraja
	 * @return browser name and version
	 */
	public String getBrowserVersion() {
		String browser_version;
		Capabilities cap = ((RemoteWebDriver) webdriver).getCapabilities();
		String browsername = cap.getBrowserName();
		String GetuAgent = (String) ((JavascriptExecutor) webdriver).executeScript("return navigator.userAgent;");
		// This block to find out IE Version number
		if ("internet explorer".equalsIgnoreCase(browsername)) {
			// uAgent return as "MSIE 8.0 Windows" for IE8
			if (GetuAgent.contains("MSIE") && GetuAgent.contains("Windows")) {
				browser_version = GetuAgent.substring(GetuAgent.indexOf("MSIE") + 5, GetuAgent.indexOf("Windows") - 2);
			} else if (GetuAgent.contains("Trident/7.0")) {
				browser_version = "11.0";
			} else {
				browser_version = "0.0";
			}
		} else if ("firefox".equalsIgnoreCase(browsername)) {
			browser_version = GetuAgent.substring(GetuAgent.indexOf("Firefox")).split(" ")[0].replace("/", "-");
			browser_version = browser_version.replace("Firefox-", "");
		} else { // Browser version for Chrome and Opera
			browser_version = cap.getVersion();
		}
		String browserversion = browser_version.substring(0, browser_version.indexOf('.'));
		return Captialize(browsername) + " browser (Version: " + browserversion + " )";
	}

	/**
	 * Function to Captialize the word
	 * 
	 * @param RequiredWord
	 * @return
	 */
	public String Captialize(String RequiredWord) {
		return RequiredWord.substring(0, 1).toUpperCase()
				+ RequiredWord.substring(1, RequiredWord.length()).toLowerCase();
	}

	/**
	 * Function to initialize Extent report and it must be called only @Test
	 * annotation
	 * 
	 * @author saikiran.nataraja
	 */
	public void initializeExtentReport() {
		// Instantiating the ExtentReports
		executingTestCaseName = super.getClass().getSimpleName();
		loggerInstance.startTestCase(executingTestCaseName);

		// Create testInstance
		testInstance = extentInstance.createTest(executingTestCaseName,
				"'" + executingTestCaseName + "' is used to check details in Application.");
		testInstance.assignAuthor(getCurrentlyLoggedInUser);
		testInstance.assignCategory("RegressionTestCases_Test");
	}

	@AfterTest
	public void closeSession() {
		if (webdriver != null) {
			webdriver.close();
		}
		Runtime rt = Runtime.getRuntime();
		try {
			rt.exec("taskkill /F /IM geckodriver_win32_v0.21.0.exe");
			rt.exec("taskkill /F /IM chromedriver_win32_v2.42.exe");
			rt.exec("taskkill /F /IM IEDriverServer_Win32_v3.14.0.exe");
		} catch (IOException e) {
			// clean up state...
			Thread.currentThread().interrupt();
		}
	}

	@AfterClass
	public void tearDownFunction() {
		loggerInstance.endTestCase(executingTestCaseName);
		// write all resources to report file
		extentInstance.flush();
	}

	/**
	 * Capture the operations on Test completion @param testResult @author
	 * saikiran.nataraja @throws AWTException @throws IOException @throws
	 */
	@AfterMethod
	public void operationsOnTestCompletion(ITestResult testResult) throws AWTException, IOException {
		if (testResult.getStatus() == ITestResult.FAILURE) {
			Reporter.log(" - FAILED.", true);
			// Create Error Screenshot Directory if doesnot exists
			File dir = new File(PathConstants.ErrorReportPath);
			dir.mkdirs();
			// Capture screenshot of Driver
			String relativeScErrImgPath = captureScreenshotOfDriver();
			// Capture screenshot of Screen - Enable if required
			// captureScreenshotOfScreen();
			// Extent Reports take screenshot
			testInstance.log(Status.FAIL, "Failure Stack Trace: " + testResult.getThrowable().getMessage());
			// adding screenshots to log
			testInstance.log(Status.INFO, "Refer below Snapshot: ",
					MediaEntityBuilder.createScreenCaptureFromPath(relativeScErrImgPath).build());
		} else if (testResult.getStatus() == ITestResult.SKIP) {
			Reporter.log(" - SKIPPED.", false);
			testInstance.log(Status.SKIP, "Test skipped: " + testResult.getThrowable().getMessage());
		} else {
			Reporter.log(" - PASSED.", false);
			testInstance.log(Status.PASS, "'" + executingTestCaseName + "' is passed based on the test criteria.");
		}

	}

	public String captureScreenshotOfDriver() {
		// Take screenshot and store as a file format
		File errSource = ((TakesScreenshot) webdriver).getScreenshotAs(OutputType.FILE);
		String errScName = PathConstants.ErrorReportPath + PathConstants.fs + PathConstants.DateFormat
				+ Captialize(runOnBrowser) + "_"
				+ executingTestCaseName.substring(0, Math.min(executingTestCaseName.length(), 20)) + ".jpeg";
		try {
			// Here the screenshot path is reduced to a maximum of 20 literals
			File imagePath = new File(errScName);
			// now copy the screenshot to desired location using copyFile //method
			FileUtils.copyFile(errSource, imagePath);
		} catch (IOException e) {
			Reporter.log(e.getMessage(), true);

		}
		return errScName;
	}

	public String captureScreenshotOfScreen() throws HeadlessException, AWTException, IOException {
		BufferedImage image = new Robot()
				.createScreenCapture(new Rectangle(Toolkit.getDefaultToolkit().getScreenSize()));
		// Here the screenshot path is reduced to a maximum of 20 literals
		File imagePath = new File(PathConstants.ErrorReportPath + PathConstants.fs + "Screen_"
				+ PathConstants.DateFormat + Captialize(runOnBrowser) + "_"
				+ executingTestCaseName.substring(0, Math.min(executingTestCaseName.length(), 20)) + ".jpeg");
		ImageIO.write(image, "JPG", imagePath);
		// Check below line if the screenshot is NOT displayed properly
		String relativeErrImgPath = imagePath.getAbsoluteFile().toString().replace(PathConstants.ReportPath,
				"." + PathConstants.fs);
		Reporter.log(relativeErrImgPath, true);
		return relativeErrImgPath;
	}

}
