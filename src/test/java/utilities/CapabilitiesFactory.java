package utilities;

import org.openqa.selenium.remote.DesiredCapabilities;

public class CapabilitiesFactory {
    public static DesiredCapabilities getCapabilities() {
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
}
