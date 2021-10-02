package Utils;

import Tools.BrowserBaseclass;
import org.openqa.selenium.WebDriver;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeSuite;

import java.lang.reflect.Method;

public class Initializer extends BrowserBaseclass{
    public WebDriver driver;
    public String methodName;
    @BeforeSuite
    public void initSuite(){
        super.setDriver("Chrome", "https://mystifying-beaver-ee03b5.netlify.app/");
        super.waitForPageLoad();
        this.driver = super.getDriver();
    }
    @BeforeMethod
    public void beforeTestCase(Method m) {
        methodName = m.getName();
    }
    @AfterSuite
    public void teardown(){
        this.driver.quit();
    }
    public WebDriver getDriver(){
        return this.driver;
    }
}
