package functionLibrary;

import io.appium.java_client.AppiumDriver;
import io.appium.java_client.MobileBy;
import io.appium.java_client.android.AndroidDriver;
import org.openqa.selenium.*;

import java.util.List;

/**
 * @author saikirannataraja
 */
public class MobileWrappers {

    private ErrorHandling errHandleInst;
    private WebDriver driver;


    /**
     * @param driver    Needs to be passed from the calling function
     * @param errHandle errhandling functionalities are written in this class
     * @see MobileWrappers is a Constructor class required for instantiation
     */
    public MobileWrappers(WebDriver driver, ErrorHandling errHandle) {
        this.errHandleInst = errHandle;
        this.driver = driver;
    }

    public WebElement getMobileElement(String objDesc) {
        //Delimiters
        String[] delimiters = new String[]{":="};
        String[] arrFindByValues = objDesc.split(delimiters[0]);

        //Get Findby and Value
        String FindBy = arrFindByValues[0];
        String val = arrFindByValues[1];

        //Handle all FindBy cases
        String strElement = FindBy.toLowerCase();

        try {
            if (strElement.equalsIgnoreCase("xpath")) {
                return driver.findElement(By.xpath(val));
            } else if (strElement.equalsIgnoreCase("accessibility_id")) {
                return driver.findElement(MobileBy.AccessibilityId(val));
            } else if (strElement.equalsIgnoreCase("appclassname")) {
                return driver.findElement(By.className(val));
            } else if (strElement.equalsIgnoreCase("uiautomator")) {
                return driver.findElement(MobileBy.AndroidUIAutomator(val));
            } else if (strElement.equalsIgnoreCase("tagname")) {
                return driver.findElement(By.tagName(val));
            } else {
                errHandleInst.assertEquals("Element should be found and returned", "Property " + FindBy + " specified for element is invalid", "Fail");
                throw (new InvalidSelectorException("Wrapper method getElement() : Property " + FindBy + " specified for element is invalid"));
            }
        } catch (NoSuchElementException ex) {
            errHandleInst.assertEquals("Get element matching description " + objDesc, "Element not found", "Fail");
            throw (ex);
        }

    }

    public List<WebElement> getMobileElements(String objDesc) {
        //Delimiters
        String[] delimiters = new String[]{":="};
        String[] arrFindByValues = objDesc.split(delimiters[0]);

        //Get Findby and Value
        String FindBy = arrFindByValues[0];
        String val = arrFindByValues[1];

        List<WebElement> elements = null;

        //Handle all FindBy cases
        String strElement = FindBy.toLowerCase();
        if (strElement.equalsIgnoreCase("xpath")) {
            elements = driver.findElements(By.xpath(val));
        } else if (strElement.equalsIgnoreCase("name")) {
            elements = driver.findElements(By.name(val));
        } else if (strElement.equalsIgnoreCase("id")) {
            elements = driver.findElements(By.id(val));
        } else if (strElement.equalsIgnoreCase("accessibility_id")) {
            elements = ((AppiumDriver) driver).findElements(MobileBy.AccessibilityId(val));
        } else if (strElement.equalsIgnoreCase("appclassname")) {
            elements = ((AppiumDriver) driver).findElements(By.className(val));
        } else {
            errHandleInst.assertEquals("Get elements matching description " + objDesc, "Property " + FindBy + " specified for elements is invalid", "Fail");
            throw (new InvalidSelectorException("Wrapper method getElements() : Property " + FindBy + " specified for element is invalid"));
        }

        return elements;
    }

    public WebElement getMobileChildElement(WebElement parentElem, String objDesc) {
        //Delimiters
        String[] delimiters = new String[]{":="};
        String[] arrFindByValues = objDesc.split(delimiters[0]);

        //Get Findby and Value
        String FindBy = arrFindByValues[0];
        String val = arrFindByValues[1];

        //Handle all FindBy cases
        String strElement = FindBy.toLowerCase();
        if (strElement.equalsIgnoreCase("id")) {
            return parentElem.findElement(By.id(val));
        } else if (strElement.equalsIgnoreCase("name")) {
            return parentElem.findElement(By.name(val));
        } else if (strElement.equalsIgnoreCase("xpath")) {
            return parentElem.findElement(By.xpath(val));
        } else if (strElement.equalsIgnoreCase("accessibility_id")) {
            return parentElem.findElement(MobileBy.AccessibilityId(val));
        } else if (strElement.equalsIgnoreCase("appclassname")) {
            return parentElem.findElement(By.className(val));
        } else if (strElement.equalsIgnoreCase("uiautomator")) {
            return parentElem.findElement(MobileBy.AndroidUIAutomator(val));
        } else {
            errHandleInst.assertEquals("Get child object matching description " + objDesc, "Property " + FindBy + " specified for object is invalid", "Fail");
            throw (new InvalidSelectorException("Wrapper method getChildElement() : Property " + FindBy + " specified for element is invalid"));
        }
    }

    public List<WebElement> getMobileChildElements(WebElement parentElem, String objDesc) {
        //Delimiters
        String[] delimiters = new String[]{":="};
        String[] arrFindByValues = objDesc.split(delimiters[0]);

        //Get Findby and Value
        String FindBy = arrFindByValues[0];
        String val = arrFindByValues[1];

        //List
        List<WebElement> elements = null;

        //Handle all FindBy cases
        String strElement = FindBy.toLowerCase();
        if (strElement.equalsIgnoreCase("id")) {
            elements = parentElem.findElements(By.id(val));
        } else if (strElement.equalsIgnoreCase("name")) {
            elements = parentElem.findElements(By.name(val));
        } else if (strElement.equalsIgnoreCase("xpath")) {
            elements = parentElem.findElements(By.xpath(val));
        } else if (strElement.equalsIgnoreCase("accessibility_id")) {
            elements = parentElem.findElements(MobileBy.AccessibilityId(val));
        } else if (strElement.equalsIgnoreCase("appclassname")) {
            elements = parentElem.findElements(By.className(val));
        } else if (strElement.equalsIgnoreCase("uiautomator")) {
            elements = parentElem.findElements(MobileBy.AndroidUIAutomator(val));
        } else if (strElement.equalsIgnoreCase("tagname")) {
            elements = parentElem.findElements(By.tagName(val));
        } else {
            errHandleInst.assertEquals("Get child elements matching description " + objDesc, "Property " + FindBy + " specified for element is invalid", "Fail");
            throw (new InvalidSelectorException("Wrapper method getChildElements() : Property " + FindBy + " specified for element is invalid"));
        }

        return elements;
    }


    public MobileWrappers rotateDeviceScreen(String Orientation) throws InterruptedException {

        String strOrientation = "";
        ScreenOrientation iOrientation;

        try {
            if (Orientation.equalsIgnoreCase("L")) {
                iOrientation = ScreenOrientation.LANDSCAPE;
                strOrientation = "Landscape";
            } else {
                iOrientation = ScreenOrientation.PORTRAIT;
                strOrientation = "Portrait";
            }
            ((AppiumDriver) driver).rotate(iOrientation);
            //wait till orientation change
            Thread.sleep(1000);
        } catch (WebDriverException ex) {
            errHandleInst.assertEquals("Rotate Screen", "Set screen orientation to " + strOrientation, "Orientation Setting Failed");
            throw (ex);
            //return false;
        }

        errHandleInst.assertEquals("Rotate Screen", "Rotate Screen", "Set screen orientation to " + strOrientation + " Successfully");
        return this;
    }

    public MobileWrappers setGeoLocation(String lat, String lon) {
        //js
        String Script = "window.navigator.geolocation.getCurrentPosition =  function(success){var position = {'coords' : {'latitude': '" + lat + "', 'longitude': '" + lon + "'}}; success(position);}";
        //Update geolocation
        JavascriptExecutor js = (JavascriptExecutor) driver;
        Object[] args = {null};
        js.executeScript(Script, args);

        return this;
    }


    public String getCurrentAndroidActivity() {
        return ((AndroidDriver) driver).currentActivity();
    }

    public MobileWrappers waitForAndroidActivity(String expectedActivity, int sec) {
        int i = 0;
        String actualActivity = "";

        //Loop for activity
        while (i < sec) {

            actualActivity = ((AndroidDriver) driver).currentActivity();
            if (actualActivity.equals(expectedActivity)) {
                errHandleInst.assertEquals("Wait for activity " + expectedActivity, "Wait for activity " + expectedActivity, "Activity " + expectedActivity + " opened");
                return this;
            }

            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            //increment
            i++;
        }

        errHandleInst.assertEquals("Wait for activity " + expectedActivity, "Activity " + expectedActivity + " didnt open. Current Activity is " + actualActivity, "Fail");
        throw (new TimeoutException("Timeout occured while waiting for Android Activity " + expectedActivity + " Current Activity is " + actualActivity));
    }

}
