package ios;

import io.appium.java_client.AppiumDriver;
import io.appium.java_client.ios.IOSDriver;
import io.appium.java_client.service.local.AppiumDriverLocalService;
import io.appium.java_client.service.local.AppiumServiceBuilder;
import io.github.ashwith.flutter.FlutterFinder;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.testng.ITestResult;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import utilities.Helper;

import java.io.File;
import java.time.Duration;

public class IosTestBase {

    protected AppiumDriver driver;
    protected FlutterFinder flutterFinder;
    protected AppiumDriverLocalService service;
    protected DesiredCapabilities capabilities;

    @BeforeTest
    public void setUp() {
        try {
            service = new AppiumServiceBuilder().usingPort(4723).build();
            service.start();
            if (service == null || !service.isRunning()) {
                throw new RuntimeException("❌ Appium server failed to start");
            }
            System.out.println("✅ Appium session started successfully.");
            capabilities = new DesiredCapabilities();
            capabilities.setCapability("platformName", "iOS");
            capabilities.setCapability("platformVersion", "17.0"); // ios version
            capabilities.setCapability("deviceName", "iPhone 16 Pro Max");
            capabilities.setCapability("automationName", "XCUITest");
            capabilities.setCapability("appium:app", System.getProperty("user.dir") + "/apps/app-stg-debug.apk");
            capabilities.setCapability("appium:newCommandTimeout", 120);
            driver = new IOSDriver(service.getUrl(), capabilities);
            System.out.println("✅ IOSFlutterDriver initialized");
            System.out.println(service.getUrl().toString());
            driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(30));
            flutterFinder = new FlutterFinder(driver);
        } catch (Exception e) {
            System.err.println("❌ Failed to start Appium session: " + e.getMessage());
        }
    }

//    @AfterTest(alwaysRun = true)
//    public void takeScreenshotOnFailure(ITestResult result) {
//        if (result.getStatus() == ITestResult.FAILURE) {
//            System.out.println("Test is failed");
//            System.out.println("Take a screenshot of " + result.getMethod().getMethodName());
//            Helper.CaptureScreenshot(driver, result.getName());
//        }
//    }


    @AfterTest(alwaysRun = true)
    public void tearDown() {
        if (driver != null) {
            driver.quit();
            System.out.println("Driver session ended");
        }
        if (service != null && service.isRunning()) {
            service.stop();
        }
    }
}
