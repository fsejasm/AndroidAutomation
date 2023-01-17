import io.appium.java_client.AppiumDriver;
import io.appium.java_client.MobileElement;
import io.appium.java_client.TouchAction;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.remote.MobileCapabilityType;
import org.openqa.selenium.By;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.net.MalformedURLException;
import java.net.URL;

import static jdk.nashorn.internal.objects.Global.undefined;

public class ioSampleTest {

    public AndroidDriver<MobileElement> driver;
    public WebDriverWait wait;
    public static String mobileElement;

    //Elements
    String secondNewJob = "//android.widget.FrameLayout[2]/android.widget.LinearLayout/" +
            "android.widget.RelativeLayout/android.widget.ImageView";

    @BeforeMethod
    public void setup () throws MalformedURLException {
        DesiredCapabilities caps = new DesiredCapabilities();
        caps.setCapability("deviceName", "Galaxy Nexus API 29");
        caps.setCapability("udid", "emulator-5554"); //DeviceId from "adb devices" command
        caps.setCapability("platformName", "Android");
        caps.setCapability("platformVersion", "10.0");
        caps.setCapability("skipUnlock","true");
        caps.setCapability("appPackage", "com.swaglabsmobileapp");
        caps.setCapability("appActivity","com.swaglabsmobileapp.MainActivity");
        caps.setCapability("noReset","false");

        try {
             driver = new AndroidDriver<MobileElement>(new URL("http://127.0.0.1:4723/wd/hub"),caps);
            wait = new WebDriverWait(driver, 10);
        } catch (MalformedURLException e) {
            System.out.println(e.getMessage());
        }
    }


    @Test
    public void basicTest () throws InterruptedException {


        //Fill Login User Name
        wait.until(ExpectedConditions.visibilityOfElementLocated
                  (By.xpath("//android.widget.EditText[@content-desc=\"test-Username\"]"))).clear();
        wait.until(ExpectedConditions.visibilityOfElementLocated
                (By.xpath("//android.widget.EditText[@content-desc=\"test-Username\"]"))).sendKeys("holaUsuario");

        //Fill User Password
        wait.until(ExpectedConditions.visibilityOfElementLocated
                (By.xpath("//android.widget.EditText[@content-desc=\"test-Password\"]"))).clear();
        wait.until(ExpectedConditions.visibilityOfElementLocated
                (By.xpath("//android.widget.EditText[@content-desc=\"test-Password\"]"))).sendKeys("Password");

        // Click sobre login
        wait.until(ExpectedConditions.visibilityOfElementLocated
                (By.xpath("//android.view.ViewGroup[@content-desc=\"test-LOGIN\"]"))).click();
        //Validar EL login
        wait.until(ExpectedConditions.visibilityOfElementLocated
                (By.xpath("//android.view.ViewGroup[@content-desc=\"test-Error message\"]"))).isDisplayed();

        mobileElement = wait.until(ExpectedConditions.visibilityOfElementLocated
                (By.xpath("//android.view.ViewGroup[@content-desc=\"test-Error message\"]/android.widget.TextView"))).getText();

        Assert.assertTrue(mobileElement.contains("Username and password do not match any user in this service."));
    }

    @AfterMethod
    public void teardown(){
        driver.quit();
    }
}

