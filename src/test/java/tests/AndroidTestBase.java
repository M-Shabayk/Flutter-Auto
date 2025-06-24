package tests;

import java.io.IOException;
import java.time.Duration;
import java.util.List;
import java.util.Map;

import io.appium.java_client.AppiumDriver;
import io.appium.java_client.android.AndroidDriver;
import io.github.ashwith.flutter.FlutterFinder;
import io.appium.java_client.service.local.AppiumDriverLocalService;
import io.appium.java_client.service.local.AppiumServiceBuilder;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Parameters;

public class AndroidTestBase {

    protected AppiumDriver driver;
    protected FlutterFinder finder;
    protected AppiumDriverLocalService service;

    @Parameters({"platformName"})
    @BeforeTest
    public void setUp(String platformName) throws IOException {
        // Start Appium Service
        service = new AppiumServiceBuilder().usingPort(4723).build();
        service.start();
        if (service == null || !service.isRunning()) {
            throw new RuntimeException("❌ Appium server failed to start");
        }
        System.out.println("✅ Appium session started successfully.");

        // Initialize Appium Desired Capabilities
        DesiredCapabilities capabilities = getDesiredCapabilities();
        driver = new AndroidDriver(service.getUrl(), capabilities);
        System.out.println(service.getUrl().toString());
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(30));
        finder = new FlutterFinder(driver);
        System.out.println("✅ AndroidFlutterDriver initialized");

        // Handle multiple isolates dynamically
        setupFlutterDriverWithDynamicIsolates();
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

    private DesiredCapabilities getDesiredCapabilities() {
        DesiredCapabilities capabilities = new DesiredCapabilities();
        capabilities.setCapability("platformName", "Android");
        capabilities.setCapability("appium:deviceName", "Pixel_9_Pro_XL");
        capabilities.setCapability("appium:automationName", "Flutter");
        capabilities.setCapability("appium:appPackage", "com.getsak.sak.stg");
        capabilities.setCapability("appium:appActivity", "com.getsak.sak.MainActivity");
        capabilities.setCapability("appium:app", System.getProperty("user.dir") + "/apps/app-stg-debug.apk");
        capabilities.setCapability("appium:newCommandTimeout", 120);
        return capabilities;
    }

    public void setupFlutterDriverWithDynamicIsolates() {
        try {
            Object vmInfoResult = driver.executeScript("flutter:getVMInfo");
            // Parse VM info to extract isolates
            Map<String, Object> vmInfo = (Map<String, Object>) vmInfoResult;
            List<Map<String, Object>> isolates = (List<Map<String, Object>>) vmInfo.get("isolates");

            if (isolates == null || isolates.isEmpty()) {
                throw new RuntimeException("No isolates found in VM info!");
            }

            System.out.println("🎯 Found " + isolates.size() + " isolates");

            boolean success = false;

            // Try each isolate until one works
            Map<String, Object> isolate = isolates.get(1);
            String isolateId = (String) isolate.get("id");
            String isolateName = (String) isolate.get("name");

            try {
                // Set a shorter timeout for individual operations
                driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(5));

                // Set the target isolate with timeout handling
                driver.executeScript("flutter:setIsolateId", isolateId);
                System.out.println("✅ Set isolate to: " + isolateId);

                // Try waitForFirstFrame with this isolate
                driver.executeScript("flutter:waitForFirstFrame");
                System.out.println("✅ waitForFirstFrame completed with isolate: " + isolateId + " (" + isolateName + ")");
                success = true;

            } catch (Exception e) {
                System.out.println("❌ Failed with isolate " + isolateId + ": " + e.getMessage());
                // Force continue to next iteration
            } finally {
                // Reset timeout
                driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
            }

            if (!success) {
                System.out.println("❌ All isolates failed. Trying without isolate targeting...");

                // Last resort: try without setting any isolate
                try {
                    driver.executeScript("flutter:waitForFirstFrame");
                    System.out.println("✅ waitForFirstFrame worked without isolate targeting");
                    success = true;
                } catch (Exception e) {
                    System.out.println("❌ Even default isolate failed: " + e.getMessage());
                }
            }

            if (!success) {
                throw new RuntimeException("All " + isolates.size() + " isolates failed!");
            }

        } catch (Exception e) {
            System.out.println("❌ Error in setupFlutterDriverWithDynamicIsolates: " + e.getMessage());
            e.printStackTrace();
            throw new RuntimeException("Failed to setup Flutter driver", e);
        }
    }
}
