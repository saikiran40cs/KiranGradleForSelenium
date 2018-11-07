package functionLibrary;

import java.util.LinkedHashMap;

import org.openqa.selenium.Proxy.ProxyType;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriverLogLevel;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.firefox.FirefoxProfile;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.ie.InternetExplorerOptions;
import org.openqa.selenium.remote.CapabilityType;

public class DriverProfiles {

	private int proxyType = ProxyType.AUTODETECT.ordinal();

	/**
	 * @see Function to set the firefox profile
	 * @author saikirannataraja
	 * @returns Firefox Profile
	 */
	public FirefoxOptions createFirefoxProfile() {
		System.setProperty("webdriver.gecko.driver", PathConstants.firefoxDriverPath);
		System.setProperty("webdriver.log.logfile", "INFO");

		// For Firefox above 47.0.2
		System.setProperty("webdriver.gecko.driver", PathConstants.firefoxDriverPath);
		System.setProperty("webdriver.log.logfile", "INFO");
		// An implementation of the {#link WebDriver} interface that drives Firefox.
		FirefoxOptions firefoxOptions = new FirefoxOptions().setProfile(new FirefoxProfile());
		// Set the capability for marionnette
		firefoxOptions.setCapability("marionette", false);
		// Set Log Level to INFO
		firefoxOptions.setLogLevel(FirefoxDriverLogLevel.INFO);
		firefoxOptions.setAcceptInsecureCerts(true); // Set profile to accept untrusted certificates
		firefoxOptions.addPreference("browser.download.folderList", 2); // 0- Desktop, 1-Browser's Default Path , 2-
																		// Custom Download Path
		firefoxOptions.addPreference("plugin.scan.plid.all", false);
		firefoxOptions.addPreference("plugin.scan.Acrobat", "99");
		firefoxOptions.addPreference("browser.download.dir", PathConstants.downloadsPath);
		firefoxOptions.addPreference("browser.download.manager.alertOnEXEOpen", false);
		firefoxOptions.addPreference("browser.helperApps.neverAsk.saveToDisk",
				"application/octet-stream;application/pdf");
		firefoxOptions.addPreference("browser.download.manager.showWhenStarting", false);
		firefoxOptions.addPreference("browser.download.manager.focusWhenStarting", false);
		firefoxOptions.addPreference("browser.helperApps.alwaysAsk.force", false);
		firefoxOptions.addPreference("browser.download.manager.alertOnEXEOpen", false);
		firefoxOptions.addPreference("browser.download.manager.closeWhenDone", false);
		firefoxOptions.addPreference("browser.download.manager.showAlertOnComplete", false);
		firefoxOptions.addPreference("browser.download.manager.useWindow", false);
		firefoxOptions.addPreference("browser.download.manager.showWhenStarting", false);
		firefoxOptions.addPreference("network.proxy.type", proxyType);
		firefoxOptions.addPreference("services.sync.prefs.sync.browser.download.manager.showWhenStarting", false);
		// Do NOT COMMENT Below line as it is required to download the PDF into download
		// location
		firefoxOptions.addPreference("plugin.disable_full_page_plugin_for_types", "application/pdf");
		return firefoxOptions;
	}

	/**
	 * @see Function to create chrome capabilities
	 * @author saikirannataraja
	 * @returns chromeoptions
	 */
	public ChromeOptions createChromeCapabilites() {
		ChromeOptions chromeOptions = new ChromeOptions();
		LinkedHashMap<String, Object> chromePrefs = new LinkedHashMap<String, Object>();
		// Set ACCEPT_SSL_CERTS variable to true
		chromePrefs.put(CapabilityType.ACCEPT_SSL_CERTS, true);
		chromePrefs.put(CapabilityType.ForSeleniumServer.ENSURING_CLEAN_SESSION, true);
		chromePrefs.put("profile.default_content_settings.popups", 0);
		chromePrefs.put("download.extensions_to_open", "pdf");
		chromePrefs.put("--always-authorize-plugins", false);
		chromePrefs.put("download.prompt_for_download", true);
		chromePrefs.put("download.default_directory", PathConstants.downloadsPath);
		chromePrefs.put("credentials_enable_service", false);
		chromePrefs.put("profile.password_manager_enabled", false);
		chromeOptions.setExperimentalOption("prefs", chromePrefs);
		chromeOptions.setCapability("network.proxy.type", proxyType);
		chromeOptions.addArguments("--start-maximized");
		chromeOptions.addArguments("disable-infobars");
		// Optional, if not specified, WebDriver will search your path for chromedriver.
		System.setProperty("webdriver.chrome.driver", PathConstants.chromeDriverPath);
		System.setProperty("webdriver.chrome.args", "--disable-logging");
		System.setProperty("webdriver.chrome.silentOutput", "true");
		return chromeOptions;
	}

	/**
	 * @see Function to create Internet Explorer Options
	 * @author saikirannataraja
	 * @returns ieOptions to be used in the browser
	 */
	public InternetExplorerOptions createIECapabilities() {
		InternetExplorerOptions ieOptions = new InternetExplorerOptions();
		// Set ACCEPT_SSL_CERTS variable to true
		ieOptions.setCapability(CapabilityType.ACCEPT_SSL_CERTS, true);
		ieOptions.setCapability("network.proxy.type", ProxyType.AUTODETECT.ordinal());
		// Added to avoid the Protected Mode settings for all zones
		ieOptions.setCapability(InternetExplorerDriver.INTRODUCE_FLAKINESS_BY_IGNORING_SECURITY_DOMAINS, true);
		ieOptions.setCapability(InternetExplorerDriver.INITIAL_BROWSER_URL, "about:blank");
		ieOptions.setCapability(InternetExplorerDriver.SILENT, true);
		ieOptions.setCapability(InternetExplorerDriver.IE_ENSURE_CLEAN_SESSION, true);
		ieOptions.setCapability(InternetExplorerDriver.IGNORE_ZOOM_SETTING, true);
		System.setProperty("webdriver.ie.driver", PathConstants.IEDriverPath);
		return ieOptions;
	}

}
