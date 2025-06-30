package pages;


import io.appium.java_client.AppiumBy;
import io.appium.java_client.AppiumDriver;
import io.appium.java_client.android.AndroidDriver;
import io.github.ashwith.flutter.FlutterElement;
import io.github.ashwith.flutter.FlutterFinder;
import org.openqa.selenium.WebDriverException;

import java.io.IOException;
import java.time.Duration;
import java.util.Map;

public class BasePage {
    protected AndroidDriver androidDriver;
    protected final AppiumDriver driver;
    protected final FlutterFinder flutterFinder;

    public BasePage(AppiumDriver driver, FlutterFinder flutterFinder) {
        this.driver = driver;
        this.flutterFinder = flutterFinder;
    }

    // 🔹 Reusable wait method for Flutter Element by valueKey
    protected void waitForElementByKey(String key, int timeoutInSeconds) {
        long endTime = System.currentTimeMillis() + timeoutInSeconds * 1000L;
        while (System.currentTimeMillis() < endTime) {
            try {
                driver.executeScript("flutter:waitFor", Map.of(
                        "finderType", "key",
                        "keyValueString", key
                ));
                return; // ✅ Found, exit the method
            } catch (WebDriverException e) {
                // Element not found yet, wait and retry
                try {
                    Thread.sleep(500); // ⏱️ wait half a second
                } catch (InterruptedException ignored) {
                }
            }
        }
        System.err.println("❌ Timed out waiting for element with key: " + key);
    }

    // 🔹 Wait for an element by visible text
    protected void waitForElementByText(String text, int timeoutInSeconds) {
        long endTime = System.currentTimeMillis() + timeoutInSeconds * 1000L;
        while (System.currentTimeMillis() < endTime) {
            try {
                driver.executeScript("flutter:waitFor", Map.of(
                        "finderType", "text",
                        "keyValueString", text
                ));
                return; // ✅ Found, exit the method
            } catch (WebDriverException e) {
                // Element not found yet, wait and retry
                try {
                    Thread.sleep(500); // ⏱️ wait half a second
                } catch (InterruptedException ignored) {
                }
            }
        }
        System.err.println("❌ Timed out waiting for element with key: " + text);
    }

    // 🔹 Get element by valueKey
    protected FlutterElement getElementByKey(String key) {
        return flutterFinder.byValueKey(key);
    }

    // 🔹 Get element by text
    protected FlutterElement getElementByText(String text) {
        return flutterFinder.byText(text);
    }

    // 🔹 Enter text into a field
    protected void enterText(FlutterElement element, String value) {
        element.sendKeys(value);
    }

    // 🔹 Enter text into a field
    protected void clickOnElement(FlutterElement element) {
        element.click();
    }

    // 🔹Checks if an element is present and catch failure
    protected boolean isElementPresent(String key) {
        try {
            waitForElementByKey(key, 10);
            getElementByKey(key);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    // 🔹 Generic wait for duration (fallback)
    protected void hardWait(long seconds) {
        try {
            Thread.sleep(Duration.ofSeconds(seconds).toMillis());
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }

    // 🔹 Generic wait for App to be idle
    protected void waitForAppToBeIdle() {
        try {
            driver.executeScript("flutter:waitForFirstFrame");
        } catch (Exception e) {
            System.err.println("❌ Failed to wait for app to be idle");
        }
    }

    public void closeNotification() {
        if (driver instanceof AndroidDriver) {
            androidDriver = (AndroidDriver) driver;
            androidDriver.openNotifications();
            try {
                androidDriver.findElement(AppiumBy.androidUIAutomator("new UiSelector().text(\"Clear all\")")).click();
            } catch (Exception ignored) {
            }
        } else {
            System.out.println("❌ Not an Android device. Cannot open notifications.");
        }
    }
}
