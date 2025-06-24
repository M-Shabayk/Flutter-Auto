package pages;


import io.appium.java_client.AppiumDriver;
import io.github.ashwith.flutter.FlutterElement;
import io.github.ashwith.flutter.FlutterFinder;
import org.openqa.selenium.WebDriverException;

import java.time.Duration;
import java.util.Map;

public class BasePage {
    protected final AppiumDriver driver;
    protected final FlutterFinder flutterFinder;

    public BasePage(AppiumDriver driver, FlutterFinder flutterFinder) {
        this.driver = driver;
        this.flutterFinder = flutterFinder;
    }

    // 🔹 Reusable wait method for Flutter Element by valueKey
    protected void waitForElementByKey(String key) {
        try {
            driver.executeScript("flutter:waitFor", Map.of(
                    "finderType", "key",
                    "keyValueString", key
            ));
        } catch (WebDriverException e) {
            System.err.println("❌ Failed to wait for element with key: " + key);
            e.printStackTrace();
        }
    }

    // 🔹 Wait for an element by visible text
    protected void waitForElementByText(String text) {
        driver.executeScript("flutter:waitFor", Map.of(
                "finderType", "text",
                "text", text
        ));
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
            waitForElementByKey(key);
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
            e.printStackTrace();
        }
    }
}
