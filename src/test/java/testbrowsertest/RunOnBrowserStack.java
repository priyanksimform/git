package testbrowsertest;

import java.net.MalformedURLException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.HashMap;
import org.openqa.selenium.MutableCapabilities;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

public class RunOnBrowserStack {

    private WebDriver driver;

    static String USERNAME = System.getenv("BROWSERSTACK_USERNAME");
    static String AUTOMATE_KEY = System.getenv("BROWSERSTACK_ACCESS_KEY");
    private static final String URL = "https://" + USERNAME + ":" + AUTOMATE_KEY + "@hub-cloud.browserstack.com/wd/hub";

    @Parameters({ "browser", "browserVersion", "os", "osVersion" })
    @BeforeMethod
    public void setUp(String browser, String browserVersion, String os, String osVersion)
            throws MalformedURLException, URISyntaxException {
        MutableCapabilities capabilities = new MutableCapabilities();
        HashMap<String, Object> bstackOptions = new HashMap<>();
        capabilities.setCapability("browserName", browser);
        bstackOptions.put("browserVersion", browserVersion);
        bstackOptions.put("os", os);
        bstackOptions.put("osVersion", osVersion);
        bstackOptions.put("consoleLogs", "info");
        capabilities.setCapability("bstack:options", bstackOptions);
        URI uri = new URI(URL);
        driver = new RemoteWebDriver(uri.toURL(), capabilities);
    }

    @Test
    public void testSite1() {
    	SoftAssert softAssert = new SoftAssert();
        String expectedTitle = "Swag Labs";
        driver.get("https://www.saucedemo.com/");
        String actualTitle = driver.getTitle();
        softAssert.assertEquals(actualTitle, expectedTitle, "Title not matching");
        softAssert.assertAll();
        driver.quit();
    }

    @Test
    public void testSite2() {
    	SoftAssert softAssert = new SoftAssert();
        String expectedTitle = "OrangeHRM | Sign In";
        driver.get("https://support.orangehrm.com/portal/en/signin/");
        String actualTitle = driver.getTitle();
        softAssert.assertEquals(actualTitle, expectedTitle, "Title not matching");
        softAssert.assertAll();
        driver.quit();
    }
}