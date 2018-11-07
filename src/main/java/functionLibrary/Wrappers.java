package functionLibrary;

import java.util.List;

import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.NoSuchElementException;

/**
 * @author saikirannataraja
 */
public class Wrappers {

    ErrorHandling errHandleInst;
	WebDriver driver;
	

	public Wrappers(WebDriver driver, ErrorHandling errHandle)
	{
		this.errHandleInst = errHandle;
		this.driver = driver;
	}

    public WebElement getElement(String objDesc)
    {
        //Delimiters
        String[] delimiters = new String[] {":="};
        String[] arrFindByValues = objDesc.split(delimiters[0]);

        //Get Findby and Value
        String FindBy = arrFindByValues[0];
        String val = arrFindByValues[1];

        //Handle all FindBy cases
        String strElement = FindBy.toLowerCase();

        try{
            if (strElement.equalsIgnoreCase("id"))
            {
                return driver.findElement(By.id(val));
            }
            else if (strElement.equalsIgnoreCase("name"))
            {
                return driver.findElement(By.name(val));
            }
            else if (strElement.equalsIgnoreCase("linktext"))
            {
                return driver.findElement(By.linkText(val));
            }
            else if (strElement.equalsIgnoreCase("classname"))
            {
                return driver.findElement(By.className(val));
            }
            else if (strElement.equalsIgnoreCase("cssselector"))
            {
                return driver.findElement(By.cssSelector(val));
            }
            else if (strElement.equalsIgnoreCase("xpath"))
            {
                return driver.findElement(By.xpath(val));
            }
            else if (strElement.equalsIgnoreCase("partiallinktext"))
            {
                return driver.findElement(By.partialLinkText(val));
            }
            else if (strElement.equalsIgnoreCase("tagname"))
            {
                return driver.findElement(By.tagName(val));
            }
            else
            {
                errHandleInst.assertEquals("Get element matching description " + objDesc, "Property " + FindBy + " specified for element is invalid", "Fail");
                throw(new InvalidSelectorException("Wrapper method getElement() : Property "  + FindBy + " specified for element is invalid"));
            }
        }
        catch(NoSuchElementException ex){
            errHandleInst.assertEquals("Get element matching description " + objDesc, "Element not found", "Fail");
            throw(ex);
        }

    }

    public List<WebElement> getElements(String objDesc)
    {
        //Delimiters
        String[] delimiters = new String[] {":="};
        String[] arrFindByValues = objDesc.split(delimiters[0]);

        //Get Findby and Value
        String FindBy = arrFindByValues[0];
        String val = arrFindByValues[1];

        List<WebElement> elements = null;

        //Handle all FindBy cases
        String strElement = FindBy.toLowerCase();
        if (strElement.equalsIgnoreCase("linktext")){
            elements = driver.findElements(By.linkText(val));
        }
        else if (strElement.equalsIgnoreCase("partiallinktext")){
            elements = driver.findElements(By.partialLinkText(val));
        }
        else if (strElement.equalsIgnoreCase("xpath")){
            elements = driver.findElements(By.xpath(val));
        }
        else if (strElement.equalsIgnoreCase("name")){
            elements = driver.findElements(By.name(val));
        }
        else if (strElement.equalsIgnoreCase("id")){
            elements = driver.findElements(By.id(val));
        }
        else if (strElement.equalsIgnoreCase("classname")){
            elements = driver.findElements(By.className(val));
        }
        else if (strElement.equalsIgnoreCase("cssselector")){
            elements = driver.findElements(By.cssSelector(val));
        }
        else if (strElement.equalsIgnoreCase("tagname")){
            elements = driver.findElements(By.tagName(val));
        }
        else{
            errHandleInst.assertEquals("Get elements matching description " + objDesc, "Property " + FindBy + " specified for elements is invalid", "Fail");
            throw(new InvalidSelectorException("Wrapper method getElements() : Property "  + FindBy + " specified for element is invalid"));
        }

        return elements;
    }

    public WebElement getChildElement(WebElement parentElem, String objDesc)
    {
        //Delimiters
        String[] delimiters = new String[] {":="};
        String[] arrFindByValues = objDesc.split(delimiters[0]);

        //Get Findby and Value
        String FindBy = arrFindByValues[0];
        String val = arrFindByValues[1];

        //Handle all FindBy cases
        String strElement = FindBy.toLowerCase();
        if (strElement.equalsIgnoreCase("id")) {
            return parentElem.findElement(By.id(val));
        }
        else if (strElement.equalsIgnoreCase("name")) {
            return parentElem.findElement(By.name(val));
        }
        else if (strElement.equalsIgnoreCase("linktext")){
            return parentElem.findElement(By.linkText(val));
        }
        else if (strElement.equalsIgnoreCase("classname")){
            return parentElem.findElement(By.className(val));
        }
        else if (strElement.equalsIgnoreCase("cssselector")){
            return parentElem.findElement(By.cssSelector(val));
        }
        else if (strElement.equalsIgnoreCase("xpath")){
            return parentElem.findElement(By.xpath(val));
        }
        else if (strElement.equalsIgnoreCase("partiallinktext")){
            return parentElem.findElement(By.partialLinkText(val));
        }
        else if (strElement.equalsIgnoreCase("tagname")){
            return parentElem.findElement(By.tagName(val));
        }
        else{
            errHandleInst.assertEquals("Get child object matching description " + objDesc, "Property " + FindBy + " specified for object is invalid", "Fail");
            throw(new InvalidSelectorException("Wrapper method getChildElement() : Property "  + FindBy + " specified for element is invalid"));
        }
    }

    public List<WebElement> getChildElements(WebElement parentElem, String objDesc)
    {
        //Delimiters
        String[] delimiters = new String[] {":="};
        String[] arrFindByValues = objDesc.split(delimiters[0]);

        //Get Findby and Value
        String FindBy = arrFindByValues[0];
        String val = arrFindByValues[1];

        //List
        List<WebElement> elements = null;

        //Handle all FindBy cases
        String strElement = FindBy.toLowerCase();
        if (strElement.equalsIgnoreCase("id")){
            elements = parentElem.findElements(By.id(val));
        }
        else if (strElement.equalsIgnoreCase("name")){
            elements = parentElem.findElements(By.name(val));
        }
        else if (strElement.equalsIgnoreCase("linktext")){
            elements = parentElem.findElements(By.linkText(val));
        }
        else if (strElement.equalsIgnoreCase("classname")){
            elements = parentElem.findElements(By.className(val));
        }
        else if (strElement.equalsIgnoreCase("cssselector")){
            elements = parentElem.findElements(By.cssSelector(val));
        }
        else if (strElement.equalsIgnoreCase("xpath")){
            elements = parentElem.findElements(By.xpath(val));
        }
        else if (strElement.equalsIgnoreCase("tagname")){
            elements = parentElem.findElements(By.tagName(val));
        }
        else{
            errHandleInst.assertEquals("Get child elements matching description " + objDesc,  "Property " + FindBy + " specified for element is invalid", "Fail");
            throw(new InvalidSelectorException("Wrapper method getChildElements() : Property "  + FindBy + " specified for element is invalid"));
        }

        return elements;
    }


   public boolean isWebElementPresent(String strDesc){
        List<WebElement> lst = getElements(strDesc);
        boolean isPresent = lst != null && lst.size() != 0;
        errHandleInst.assertEquals(true,isPresent, strDesc + " Element presence state is " + isPresent);
        return isPresent;
    }


   public boolean isChildWebElementPresent(WebElement objParent, String strDesc){
        List<WebElement> lst = getChildElements(objParent,strDesc);
        boolean isPresent = lst != null && lst.size() != 0;
        errHandleInst.assertEquals(true,isPresent, strDesc + " Child Element presence state is " + isPresent);
        return isPresent;
    }
	
	public boolean isWebElementDisplayed(String strDesc) {
        WebElement webElement = getElement(strDesc);
        return isWebElementDisplayed(webElement);
    }
	
	public boolean isWebElementDisplayed(WebElement webElement) {
        boolean bIsDisplayed = webElement.isDisplayed();
        String state = bIsDisplayed ? "displayed" : "not displayed";
        String strDesc = webElement.toString();
        errHandleInst.assertEquals(true, bIsDisplayed,strDesc + "Object is " + state);
        return  bIsDisplayed;
    }

	public boolean isWebElementEnabled(String strDesc) {
        //Get WebElement
        WebElement webElement = getElement(strDesc);
        return isWebElementEnabled(webElement);
    }	
    
    public boolean isWebElementEnabled(WebElement webElement) {
    	//Check if the WebElement is Enabled
        boolean bIsEnabled = webElement.isEnabled();
        String state = bIsEnabled ? "enabled" : "disabled";
        String strDesc = webElement.toString();
        errHandleInst.assertEquals(true, bIsEnabled,"Check enabled state of object with description  " + strDesc+ "Object state is " + state);
        return  bIsEnabled;
    }	
    

    public boolean isWebElementSelected(String strDesc) {
        //Get WebElement
        WebElement webElement = getElement(strDesc);
        return isWebElementSelected(webElement);
    }

    public boolean isWebElementSelected(WebElement webElement){
        boolean bIsSelected = webElement.isSelected();
        String state = bIsSelected ? "selected" : "unselected";
        String strDesc = webElement.toString();
        errHandleInst.assertEquals(true, bIsSelected,"Check selected state of object with description  " + strDesc +" Object state is " + state);
        return  bIsSelected;
    }

    public Wrappers click(String strDesc)
    {
        //Initialize
        WebElement webElement = getElement(strDesc);
        return click(webElement);
    }
    
    public Wrappers click(WebElement objClick)
    {
        String strDesc = objClick.toString();

        //Check if the object is enabled, if yes click the same
        if (objClick.isDisplayed() && objClick.isEnabled()){
            //Click on the object
            objClick.click();
        }
        else{
            errHandleInst.assertEquals(true,  "Element is either not displayed or is not enabled", "Fail");
            throw(new ElementNotVisibleException("Wrapper method click() : Element is either not visible or is not enabled"));
            //return false;
        }

        errHandleInst.assertEquals(true,true,"Click object matching description " + objClick.toString());
        return this;
    }	

   public Wrappers enterText(String strDesc, String strText)
    {
        WebElement webElement = getElement(strDesc);
        return enterText(webElement,strText);
    }	
    
    public Wrappers enterText(WebElement objEdit, String strText)
    {
    	String strDesc = objEdit.toString();

        //Check if the object is enabled, if yes click the same
        if (objEdit.isDisplayed() && objEdit.isEnabled()){
            //Enter the text in the edit box
            objEdit.clear();
            objEdit.sendKeys(strText);
        }
        else{
            errHandleInst.assertEquals("Set value in element with description " + strDesc, "Wrapper method enterText() : Element with description " + strDesc + " is either not visible or is not enabled","Fail");
            //return false;
        }

        errHandleInst.assertEquals(true,true,"Set value in element with description " + strDesc + "Value " + strText + " should be set in the edit box");
        return this;
    }	


    public Wrappers selectOptionFromList(String strDesc, String strText)
    {
        WebElement webElement = getElement(strDesc);
        return selectOptionFromList(webElement,strText);
    }
    
    public Wrappers selectOptionFromList(WebElement objSelect, String strText)
    {
    	String strDesc = objSelect.toString();

        //Check if the object is enabled, if yes click the same
        if (objSelect.isDisplayed() && objSelect.isEnabled()){
            //Set Select Element and select required value by text
            try{
                Select select = new Select(objSelect);
                select.selectByVisibleText(strText);
            }
            catch(WebDriverException ex){
                errHandleInst.assertEquals(true,"Select value in element with description failed due to exception " + ex.getMessage(), "Fail");
                throw(ex);
            }
        }
        else{
            errHandleInst.assertEquals(true,"Wrapper method selectOptionFromList() : Element with description " + strDesc + " is either not visible or is not enabled","Fail");
            //return false;
        }

        errHandleInst.assertEquals(true,true,"Select value from dropdown: Select value " + strText+ " Value " + strText + " selected");
        return this;
    }


    /**
     * Function used to check the checkbox selection
     * @param strDesc string description to be passed
     * @param checkOrUncheck if checked pass true else Unchecked pass false
     * @return the Wrappers class
     */
    public Wrappers checkBoxSelection(String strDesc,boolean checkOrUncheck)
    {
        //Initialize
        WebElement webElement = getElement(strDesc);
        return checkBoxSelection(webElement,checkOrUncheck);
    }
    
    public Wrappers checkBoxSelection(WebElement objChkBox, boolean checkOrUncheck)
    {
    	String strDesc = objChkBox.toString();

        //Check if the object is enabled, if yes click the same
        if (objChkBox.isDisplayed() && objChkBox.isEnabled()){
            //Check state of check box
            boolean isChecked = objChkBox.isSelected();

            //Check if Not Checked
            if(isChecked == checkOrUncheck) objChkBox.click();
        }
        else{
            errHandleInst.assertEquals("Check CheckBox element with description " + strDesc,"Wrapper method checkCheckBox() : Element with description " + strDesc + " is either not visible or is not enabled","Fail");
            //return false;
        }

        errHandleInst.assertEquals(true,true,"Check CheckBox element with description " + strDesc);
        return this;
    }

	public String getCurrentBrowser()
	{
		try
		{
			Capabilities DC = ((RemoteWebDriver)driver).getCapabilities();
			return DC.getBrowserName();
		}
		catch(WebDriverException e)
		{
			errHandleInst.assertEquals("Get browser name", "Fetching Browser Name Failed. Exception " + e, "Fail");
			throw(e);
		}
	}


	public Wrappers switchToWindowWithName() {
		try
		{
			//driver.switchTo().window(strWindowName);
			//Switch to new window opened
			for(String winHandle : driver.getWindowHandles()){
			    driver.switchTo().window(winHandle);
			}
		}
		catch(Exception e)
		{
			errHandleInst.assertEquals("Switch Window", "Exception occured : " + e, "Fail");
			throw(e);
		}
		
		return this;
	}

    public String getTitle(){
        return driver.getTitle();
    }


    public boolean validateValuePresentInTable(String wTable, String Value) {
        int rowCount = driver.findElements(By.xpath(wTable+"/tr")).size();
        int colCount = driver.findElements(By.xpath(wTable+"/tr[1]/td")).size();
        boolean validateValuePresentInTable = false;
        for(int i=2;i<rowCount;i++) {
            for(int j=1;j<colCount;j++) {

                if(driver.findElement(By.xpath(wTable+"/tr["+i+"]/td["+j+"]")).getText().trim().equalsIgnoreCase(Value)) {
                    validateValuePresentInTable  = true;
                    break;
                }
            }
            if(validateValuePresentInTable) {
                break;
            }
        }
        return validateValuePresentInTable;
    }


}
