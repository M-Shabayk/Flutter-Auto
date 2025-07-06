package tests;
import java.io.IOException;
import io.appium.java_client.AppiumDriver;
import io.appium.java_client.android.AndroidDriver;
import io.github.ashwith.flutter.FlutterFinder;
import io.appium.java_client.service.local.AppiumDriverLocalService;
import io.appium.java_client.service.local.AppiumServiceBuilder;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Parameters;
import utilities.CapabilitiesFactory;
import utilities.EmulatorManager;
import utilities.FlutterIsolateHandler;
public class AndroidTestBase {
    protected AppiumDriver driver;
    protected FlutterFinder finder;
    protected AppiumDriverLocalService service;
    @Parameters({"platformName"})
    @BeforeTest
    public void setUp(String platformName) throws IOException, InterruptedException {
        // Start Appium Service
        service = new AppiumServiceBuilder().usingPort(4723).build();
        service.start();
        if (service == null || !service.isRunning()) {
            throw new RuntimeException(":x: Appium server failed to start");
        }
        System.out.println(":white_check_mark: Appium session started successfully.");
        // Start mobile android Emulator
        EmulatorManager.startEmulator();
        Thread.sleep(25000);
        // Initialize Appium Desired Capabilities
        DesiredCapabilities capabilities = CapabilitiesFactory.getCapabilities();
        driver = new AndroidDriver(service.getUrl(), capabilities);
        System.out.println(service.getUrl().toString());
        finder = new FlutterFinder(driver);
        System.out.println(":white_check_mark: AndroidFlutterDriver initialized");
    }
    @BeforeClass
    public void initFlutter() throws InterruptedException {
        // Handle multiple isolates dynamically
        FlutterIsolateHandler.setupFlutterDriverWithDynamicIsolates(driver);
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
        EmulatorManager.closeEmulator();
    }
}












