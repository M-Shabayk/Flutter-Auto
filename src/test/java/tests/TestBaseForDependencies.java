package tests;

import io.appium.java_client.AppiumDriver;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.ios.IOSDriver;
import io.appium.java_client.service.local.AppiumDriverLocalService;
import io.appium.java_client.service.local.AppiumServiceBuilder;
import io.github.ashwith.flutter.FlutterFinder;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.testng.ITestResult;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Parameters;
import utilities.Helper;

import java.io.File;
import java.io.IOException;
import java.time.Duration;

public class TestBaseForDependencies {

    protected AppiumDriver driver;
    protected FlutterFinder flutterFinder;
    protected AppiumDriverLocalService service;

    @Parameters({"platformName", "automationName", "deviceName"})
    @BeforeTest
    public void setUp(final String platformName, final String automationName, final String deviceName) {
        try {
            service = new AppiumServiceBuilder().usingPort(4723).build();
            service.start();
            if (service == null || !service.isRunning()) {
                throw new RuntimeException("❌ Appium server failed to start");
            }

            DesiredCapabilities capabilities = getCapabilities(platformName);
            switch (platformName.toLowerCase()) {
                case "android":
                    driver = new AndroidDriver(service.getUrl(), capabilities);
                    break;
                case "ios":
                    driver = new IOSDriver(service.getUrl(), capabilities);
                    break;
                default:
                    throw new IllegalArgumentException("Unsupported platform: " + platformName);
            }

            System.out.println(service.getUrl().toString());
            driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(30));
            flutterFinder = new FlutterFinder(driver);
            System.out.println("✅ Appium session started successfully.");

        } catch (Exception e) {
            driver = null;
            System.err.println("❌ Failed to start Appium session: " + e.getMessage());
        }
        System.out.println("✅ FlutterDriver initialized");
    }


    @AfterTest(alwaysRun = true)
    public void takeScreenshotOnFailure(ITestResult result) {
        if (result.getStatus() == ITestResult.FAILURE) {
            System.out.println("Test is failed");
            System.out.println("Take a screenshot of " + result.getMethod().getMethodName());
            Helper.CaptureScreenshot(driver, result.getName());
        }
    }


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

    private DesiredCapabilities getCapabilities(String platformName) throws IOException {
        DesiredCapabilities capabilities = new DesiredCapabilities();

        switch (platformName.toLowerCase()) {
            case "android":
                capabilities.setCapability("platformName", "Android");
                capabilities.setCapability("appium:deviceName", "Pixel_9_Pro_XL");
                capabilities.setCapability("appium:automationName", "Flutter");
                capabilities.setCapability("appium:appPackage", "com.getsak.sak.stg");
                capabilities.setCapability("appium:appActivity", "com.getsak.sak.MainActivity");
                File androidAppDir = new File(System.getProperty("user.dir"), "apps");
                File androidApp = new File(androidAppDir.getCanonicalPath(), "app-stg-debug.apk");
                capabilities.setCapability("appium:app", androidApp.getAbsolutePath());
                capabilities.setCapability("appium:newCommandTimeout", 120);
                break;

            case "ios":
                capabilities.setCapability("platformName", "iOS");
                capabilities.setCapability("platformVersion", "17.0");
                capabilities.setCapability("deviceName", "iPhone 16 Pro Max");
                capabilities.setCapability("automationName", "XCUITest");
                capabilities.setCapability("appium:app", "/Users/appleworld/Documents/GitHub/sak/apps/app-stg-debug.ipa");
                capabilities.setCapability("appium:newCommandTimeout", 120);
                break;

            default:
                throw new IllegalArgumentException("Unsupported platform: " + platformName);
        }

        return capabilities;
    }
}
