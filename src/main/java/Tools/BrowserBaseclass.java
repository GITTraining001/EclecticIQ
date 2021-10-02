package Tools;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.Set;
import java.util.concurrent.TimeUnit;
import java.io.*;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.By;
import org.openqa.selenium.Cookie;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
//import org.slf4j.LoggerFactory;


public class BrowserBaseclass {
    //	private static final org.slf4j.Logger LOG = LoggerFactory.getLogger(Tools.BrowserBaseclass.class);
    private static final int TIMEOUT_IN_SECONDS = 40;
    private WebDriver driver;
    WebDriverWait wait;
    public static long max = 160;
    public static long med = 30;
    public static long min = 10;
    // public static String Currentplatform = MobileBaseTest.DeviceOS;
    public String SelectedPage = null;

    private void initDriver(String browser, String appUrl) {
        if (browser.equalsIgnoreCase("Chrome")) {
            WebDriverManager.chromedriver().setup();
            ChromeOptions options = new ChromeOptions();
            DesiredCapabilities capabilities = DesiredCapabilities.chrome();
            Map prefs = new HashMap();
            //prefs.put("profile.default_content_settings.cookies", 2);
            options.addArguments("start-maximized");
            //options.addArguments("user-data-dir=C:\\Users\\Ashithraj.S\\AppData\\Local\\Google\\Chrome\\User Data");
            options.setExperimentalOption("prefs", prefs);
//			System.setProperty("webdriver.chrome.driver", chromeclient);
			driver = new ChromeDriver(options);
            driver.get(appUrl);
        } else if (browser.equalsIgnoreCase("internet explorer")) {
            WebDriverManager.edgedriver().setup();
//            System.setProperty("webdriver.ie.driver", internet);
            driver = new InternetExplorerDriver();
            driver.get(appUrl);
        } else if (browser.equalsIgnoreCase("firefox")) {
            WebDriverManager.firefoxdriver().setup();
//            System.setProperty("webdriver.gecko.driver", firefox);
            driver = new FirefoxDriver();
            driver.get(appUrl);

        } else {
            System.out.println("Please select proper value.");
        }
    }
     public WebDriver getDriver (){
        return this.driver;
     }

     public void setDriver (String browser, String appUrl){
        initDriver(browser, appUrl);
     }

//    public static void reuseExistingSession(ChromeOptions options) {
//		/*Set <Cookie> cookies = driver.manage().getCookies();
//		for (Cookie cookie:cookies){
//			driver.manage().addCookie(cookie);
//		}
//		refreshBrowser();*/
//        String existingChromeProfile = ReadConfig.getPropValues("pluginType.properties").get("chromeUserProfile").trim();
//
//    }
//
//    public static void refreshBrowser() {
//        driver.navigate().refresh();
//    }
//
    public boolean waitForPageLoad() {
        // LOG.info(new
        // Object(){}.getClass().getEnclosingMethod().getName());
        boolean waitValue = false;
        try {
            driver.manage().timeouts().pageLoadTimeout(max, TimeUnit.SECONDS);
            waitValue = true;
        } catch (TimeoutException e) {
            waitValue = true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return waitValue;
    }
//
//    public static boolean isElementPresent(By locator, WebDriver driver) {
//        boolean waitValue = false;
//        boolean waitnew = false;
//        System.out.println("0");
//        try {
//            driver = TestNGInitializer.driver;
//            LOG.info("Waiting for element to be visible -->" + locator);
//            waitnew = new WebDriverWait(driver, 30).until(ExpectedConditions.visibilityOfElementLocated(locator))
//                    .isDisplayed();
//            waitValue = true;
//            LOG.info(locator + "Is Present");
//            LOG.info(locator + "Is Present");
//            // waitValue = true;
//
//        } catch (Exception e) {
//            e.printStackTrace();
//            LOG.info(locator + "Is Not present on the page");
//        }
//        return waitValue;
//    }
//
    public boolean isElementPresent(By locator, long timeout) {
        boolean waitValue = false;
        boolean waitnew = false;
        try {
            waitnew = new WebDriverWait(driver, timeout).until(ExpectedConditions.visibilityOfElementLocated(locator))
                    .isDisplayed();
            waitValue = waitnew;
            // waitValue = true;

        } catch (Exception e) {
            e.printStackTrace();
        }
        return waitValue;
    }

    public  WebElement fetchElement(By locator, long timeout) {
        boolean waitValue = false;
        boolean waitnew = false;
        WebElement retElement = null;
        System.out.println("0");
        try {
            waitnew = new WebDriverWait(driver, timeout).until(ExpectedConditions.visibilityOfElementLocated(locator))
                    .isDisplayed();
            waitValue = true;
            // waitValue = true;

        } catch (Exception e) {
            e.printStackTrace();
        }
        if (waitValue) {
            retElement = driver.findElement(locator);
        }
        return retElement;
    }

    public List<WebElement> fetchAllElements(By locator, long timeout) {
        boolean waitValue = false;
        boolean waitnew = false;
        List<WebElement> retElements = null;
        System.out.println("0");
        try {
            waitnew = new WebDriverWait(driver, timeout).until(ExpectedConditions.visibilityOfElementLocated(locator))
                    .isDisplayed();
            waitValue = true;

        } catch (Exception e) {
            e.printStackTrace();
        }
        if (waitValue) {
            retElements = driver.findElements(locator);
        }
        return retElements;
    }

    public void clickOnElement(WebElement element) {

        try {
            element.click();
            // LOG.info("Clicked on element: " + element);
        } catch (Exception e) {
            ((JavascriptExecutor) driver).executeScript("arguments[0].click()", element);
            e.printStackTrace();
        }
    }
//
//    public static void clickOnElement(WebDriver driver, By element) {
//        LOG.info(new Object() {
//        }.getClass().getEnclosingMethod().getName() + " : " + element.toString());
//        WebElement element1 = null;
//        try {
//            element1 = driver.findElement(element);
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//        try {
//            //driver = TestNGInitializer.driver;
//            System.out.println(driver);
//            if (isElementPresent(element, driver)) {
//                clickOnElement(element1);
//                //element1.click();
//            }
//            // LOG.info("Clicked on element: " + element);
//        } catch (Exception e) {
//            ((JavascriptExecutor) driver).executeScript("arguments[0].click()", element1);
//            e.printStackTrace();
//        }
//    }
//
    public boolean clickOnElement(By element, int timeout) {
        boolean retval = false;
        WebElement element1 = null;
		/*try{
			element1 = driver.findElement(element);
		}
		catch (Exception e){
			e.printStackTrace();
		}*/
        try {
            System.out.println(driver);
            if (isElementPresent(element, timeout)) {
                element1 = driver.findElement(element);
                clickOnElement(element1);
                retval = true;
            }
            // LOG.info("Clicked on element: " + element);
        } catch (Exception e) {
            ((JavascriptExecutor) driver).executeScript("arguments[0].click()", element1);
            e.printStackTrace();
            retval = true;
        }
        return retval;
    }
//
//    public boolean javascriptClickOnElement(WebDriver driver, By element) {
//        LOG.info(new Object() {
//        }.getClass().getEnclosingMethod().getName() + " : " + element.toString());
//        boolean retval = false;
//        WebElement element1 = null;
//
//        if (this.isElementPresent(element, driver, 30)) {
//            element1 = driver.findElement(element);
//            ((JavascriptExecutor) driver).executeScript("arguments[0].click()", element1);
//            retval = true;
//        }
//        return retval;
//    }
//
    public void type(By element, String text) throws InterruptedException {
        WebElement element1 = driver.findElement(element);
        element1.clear();
        Thread.sleep(2000);
        element1.sendKeys(text);
    }

    public void clearTextBox(By element) {
        WebElement element1 = driver.findElement(element);
        element1.sendKeys(Keys.chord(Keys.CONTROL, "a"));
        element1.sendKeys(Keys.BACK_SPACE);
    }

//    public void clickOnElement(WebDriver driver, WebElement element) {
//        LOG.info(new Object() {
//        }.getClass().getEnclosingMethod().getName() + " : " + element.toString());
//        try {
//            // WebElement element1=driver.findElement((By) element);
//            element.click();
//            // LOG.info("Clicked on element: " + element);
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//    }
//
//    public String getWindowHandle(WebDriver driver) {
//        String openWindow = "";
//        openWindow = driver.getWindowHandle();
//        System.out.println(openWindow);
//        return openWindow;
//    }
//
//    public List<String> getWindowHandles(WebDriver driver) {
//        List<String> windowHandles = new ArrayList<String>(driver.getWindowHandles());
//        return windowHandles;
//    }
//
//    public void switchToWindow(String windowHandle) {
//        driver.switchTo().window(windowHandle);
//    }
//
//    public void switchToFrame(By locator, int timeout) {
//        WebElement element = this.fetchElement(locator, driver, timeout);
//        driver.switchTo().frame(element);
//    }
//
//    public void switchOutOfFrame() {
//        driver.switchTo().defaultContent();
//    }
//
//
//    protected List<String> getTextOfSimilarElements(WebDriver driver, String xpath) {
//        List<String> elementTextList = new ArrayList<String>();
//        try {
//            List<WebElement> elementList = driver.findElements(By.xpath(xpath));
//            LOG.info("There are " + elementList.size() + " Similar elements(Element with locator " + xpath + " )");
//
//            for (WebElement anElement : elementList) {
//                elementTextList.add(anElement.getText());
//            }
//        } catch (Exception e) {
//            e.getMessage();
//        }
//        return elementTextList;
//    }
//
//    protected int getSizeOfSimilarElements(WebDriver driver, String xpath) {
//        int retVal = 0;
//        try {
//            List<WebElement> elementList = driver.findElements(By.xpath(xpath));
//            retVal = elementList.size();
//            LOG.info("There are " + elementList.size() + " Similar elements(Element with locator " + xpath + " )");
//
//        } catch (Exception e) {
//            e.getMessage();
//        }
//        return retVal;
//    }
//
//    public boolean checkIfEnabled(WebDriver driver, WebElement element) {
//        if (element.isEnabled()) {
//            return true;
//        } else {
//            return false;
//        }
//    }
//
//    public boolean checkIfDisabled(WebDriver driver, WebElement element) {
//        if (element.isEnabled()) {
//            return false;
//        } else {
//            return true;
//        }
//
//    }
//
//    public boolean checkIfEnabled(WebDriver driver, By element) {
//        WebElement element1 = this.fetchElement(element, driver, 10);
//        if (element1 == null) {
//            System.out.println(element + " not found in page");
//            return false;
//        }
//        if (element1.isEnabled()) {
//            return true;
//        } else {
//            return false;
//        }
//
//    }
//
//    /**
//     * method to open a link in new tab
//     *
//     * @param driver
//     * @param element
//     */
//    public static void openLinkInNewTab(WebDriver driver, WebElement element) {
//        System.out.println("---open link in new tab method---");
//        Actions action = new Actions(driver);
//        gen_utils.wait(3000);
//        action.keyDown(Keys.CONTROL).build().perform();
//        clickOnElement(element);
//        System.out.println("---after clicking on sign in----");
//        action.keyUp(Keys.CONTROL).build().perform();
//
//    }
//
//    /**
//     * method to send keys
//     *
//     * @param driver
//     * @param key
//     */
//    public static void keyActions(WebDriver driver, String key) {
//        Actions action = new Actions(driver);
//        switch (key) {
//            case "ArrowDown":
//                action.sendKeys(Keys.ARROW_DOWN).build().perform();
//                break;
//            case "ArrowUp":
//                action.keyDown(Keys.ARROW_UP);
//                action.keyUp(Keys.ARROW_UP);
//                break;
//            case "ArrowLeft":
//                action.keyDown(Keys.ARROW_LEFT);
//                action.keyUp(Keys.ARROW_LEFT);
//                break;
//            case "ArrowRight":
//                action.keyDown(Keys.ARROW_RIGHT);
//                action.keyUp(Keys.ARROW_RIGHT);
//                break;
//
//        }
//    }
//
//    /**
//     * method to move mouse to a particular co-ordinate on an element
//     *
//     * @param actionType takes type of mouse action
//     * @param driver     takes WebDriver
//     */
//    public static void moveToElementCoordinate(WebDriver driver, WebElement element, int x, int y) {
//        Actions action = new Actions(driver);
//        action.clickAndHold(element).build().perform();
//        action.moveToElement(element, x, y).click().build().perform();
//        gen_utils.wait(5000);
//    }
//
//    /**
//     * method to move mouse to a particular co-ordinate on an element
//     *
//     * @param actionType takes type of mouse action
//     * @param driver     takes WebDriver
//     */
//    public void hoverToElement(WebDriver driver, By element) {
//        Actions action = new Actions(driver);
//        WebElement element1 = driver.findElement(element);
//        System.out.println("Hovering on " + element1);
//        action.clickAndHold(element1).build().perform();
//        action.moveToElement(element1).build().perform();
//        GenUtils.wait(5000);
//    }
//
//    /**
//     * method to retrieve text of a WebElement
//     *
//     * @param element accepts Web Element
//     * @return String - WebElement text value
//     */
//    public static String getTextOfElement(By element) {
//        WebElement element1 = driver.findElement(element);
//        String textValue = element1.getText();
//        return textValue;
//    }
//
//    /**
//     * method to get the text of all WebElements
//     *
//     * @param elementList takes ArrayList of WebElements
//     * @return arrayList of all text values
//     */
//    public static ArrayList<String> getTextOfElements(ArrayList<WebElement> elementList) {
//        ArrayList<String> allTextValues = new ArrayList<String>();
//        for (int i = 0; i < elementList.size(); i++) {
//            System.out.println("-----" + elementList.get(i));
//            String textValue = elementList.get(i).getText().trim();
//            System.out.println("----text value is----" + textValue);
//            allTextValues.add(textValue);
//        }
//        return allTextValues;
//    }
//
//    /**
//     * method to check if a WebElement is present
//     *
//     * @param element accepts Web Element
//     * @return boolean
//     */
//    public static boolean checkIfExists(WebElement element) {
//        if (element.isDisplayed()) {
//            return true;
//        } else {
//            return false;
//        }
//    }
//
//    /**
//     * method to get the color of a text
//     *
//     * @param color takes color as String
//     * @return color of the text
//     */
//    public static String getTextColor(String color) {
//        String actualColor = null;
//        String[] hexValue = color.replace("rgb(", "").replace("rgba(", "").replace(")", "").split(",");
//        System.out.println("------" + hexValue);
//        int hexValue1 = Integer.parseInt(hexValue[0]);
//        System.out.println("0------" + hexValue1);
//        hexValue[1] = hexValue[1].trim();
//        int hexValue2 = Integer.parseInt(hexValue[1]);
//        System.out.println("1------" + hexValue2);
//        hexValue[2] = hexValue[2].trim();
//        int hexValue3 = Integer.parseInt(hexValue[2]);
//        System.out.println("3------" + hexValue3);
//
//        actualColor = String.format("#%02x%02x%02x", hexValue1, hexValue2, hexValue3);
//
//        return actualColor;
//    }
//
//    /**
//     * method to perform mouse actions
//     *
//     * @param element    takes WebElement
//     * @param actionType takes type of mouse action
//     * @param driver     takes WebDriver
//     */
//    public static void mouseActions(WebDriver driver, WebElement element, String actionType) {
//        Actions action = new Actions(driver);
//        if (actionType.equalsIgnoreCase("hover")) {
//            action.moveToElement(element).build().perform();
//        } else if (actionType.equalsIgnoreCase("rightClick")) {
//            action.contextClick(element).build().perform();
//        } else if (actionType.equalsIgnoreCase("doubleClick")) {
//            action.doubleClick(element).build().perform();
//        }
//
//    }
//
//    /**
//     * method to perform keyboard actions
//     *
//     * @param actionType takes type of mouse action
//     * @param driver     takes WebDriver
//     */
//    public static void keyBoradActions(WebDriver driver, String actionType) {
//        Actions action = new Actions(driver);
//        if (actionType.equals("Esc")) {
//            action.sendKeys(Keys.ESCAPE).build().perform();
//        }
//        if (actionType.equals("Enter")) {
//            action.sendKeys(Keys.ENTER).build().perform();
//        }
//    }
//
//    /**
//     * method to scroll to a particular WebElement
//     *
//     * @param element takes WebElement
//     * @param driver  takes WebDriver
//     */
//    public static void scrollToElement(WebDriver driver, WebElement element) {
//
//        ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", element);
//
//    }
//
//    /**
//     * method to scroll to a particular WebElement
//     *
//     * @param element takes By element
//     * @param driver  takes WebDriver
//     */
//    public static void scrollToElement(WebDriver driver, By element) {
//
//        WebElement element1 = fetchElement(element, driver, 10);
//
//        ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", element1);
//
//    }
//
//    /**
//     * method to click using javaScript executors
//     *
//     * @param element accepts web element
//     * @param driver  accepts webDriver
//     * @return null
//     */
//    public static Object executeScript(WebDriver driver, WebElement element) {
//
//        return ((JavascriptExecutor) driver).executeScript("arguments[0].click()", element);
//
//    }
//
//    /**
//     * method to scroll vertically and horizontally using javascript executor
//     *
//     * @param y         accepts number of pixels to scroll
//     * @param direction takes direction to move
//     * @param driver    - WebDriver
//     */
//    public void scroll(WebDriver driver, int x, String direction) {
//        JavascriptExecutor js = (JavascriptExecutor) driver;
//        if (direction.equalsIgnoreCase("down")) {
//            js.executeScript("window.scrollBy(0," + x + ")");
//        } else if (direction.equalsIgnoreCase("up")) {
//            js.executeScript("window.scrollBy(0," + x + ")");
//        } else if (direction.equalsIgnoreCase("right")) {
//            js.executeScript("window.scrollBy(" + x + ", 0)");
//        } else if (direction.equalsIgnoreCase("left")) {
//            js.executeScript("window.scrollBy(-" + x + ", 0)");
//        }
//    }
//
//    /**
//     * method to scroll to bottom of the page using JavaScript
//     *
//     * @param driver - WebDriver
//     */
//
//    public static void scrollToBottom(WebDriver driver) {
//        JavascriptExecutor js = (JavascriptExecutor) driver;
//        // js.executeScript("window.scrollTo(0, document.body.scrollHeight)");
//    }
//
//    public static void scrollToView(WebDriver driver, WebElement element) {
//        JavascriptExecutor js = (JavascriptExecutor) driver;
//        // js.executeScript("window.scrollTo(0, document.body.scrollHeight)");
//        js.executeScript("arguments[0].scrollIntoView();", element);
//    }
//
//
//    /**
//     * method to explicitly wait for an element to be invisible
//     *
//     * @param element accepts Web Element
//     * @param driver  accepts WebDriver
//     * @return Boolean
//     */
//    public static void waitForElementToBeInVisible(WebDriver driver, WebElement element, int timeout) {
//        WebDriverWait wait = new WebDriverWait(driver, timeout);
//        wait.until(ExpectedConditions.invisibilityOf(element));
//    }
//
//    public static boolean selectItemFromComboBox(WebDriver driver, By element, String option, int timeout) {
//        boolean retval = false;
//        String comboTextAfterSelection = "";
//        WebElement comboBoxElement = fetchElement(element, driver, timeout);
//        Select comboBox = new Select(comboBoxElement);
//        comboBox.selectByVisibleText(option);
//        comboTextAfterSelection = fetchComboBoxSelectedString(driver, element, timeout);
//        if (comboTextAfterSelection.trim().equalsIgnoreCase(option)) {
//            retval = true;
//        }
//        return retval;
//    }
//
//    public static String fetchComboBoxSelectedString(WebDriver driver, By element, int timeout) {
//        String retval = "";
//        WebElement comboBoxElement = fetchElement(element, driver, timeout);
//        Select comboBox = new Select(comboBoxElement);
//        WebElement firstSelectedOption = comboBox.getFirstSelectedOption();
//        retval = firstSelectedOption.getText();
//        return retval;
//    }
//
//    public static ArrayList<String> openNewURL(WebDriver driver, String url) {
//        ((JavascriptExecutor) driver).executeScript("window.open()");
//        ArrayList<String> tabs = new ArrayList<String>(driver.getWindowHandles());
//        driver.switchTo().window(tabs.get(1));
//        driver.get(url);
//        return tabs;
//    }

    public List <WebElement> selectBoxItemList(By locator, long timeout){
        WebElement comboBox = this.fetchElement(locator, timeout);
        Select objSelect = new Select(comboBox);
        List <WebElement> comboElements = objSelect.getOptions();
        return comboElements;
    }

    public void selectComboBoxByIndex(By locator, long timeout, int index){
        WebElement comboBox = this.fetchElement(locator, timeout);
        Select objSelect = new Select(comboBox);
        objSelect.selectByIndex(index);
    }

    public String getElementText(WebElement element, long timeout){
        return element.getText();
    }

}