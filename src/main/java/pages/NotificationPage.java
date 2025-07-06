package pages;

import io.appium.java_client.AppiumDriver;
import io.github.ashwith.flutter.FlutterElement;
import io.github.ashwith.flutter.FlutterFinder;
import java.util.List;

public class NotificationPage extends BasePage {

    // Define keys for elements on the notification screen
    private static final String NOTIFICATION_TITLE_KEY = "notification_title";
    private static final String NOTIFICATION_LIST_KEY = "notification_list";
    private static final String BACK_BUTTON_KEY = "back_button";

    public NotificationPage(AppiumDriver driver, FlutterFinder finder) {
        super(driver, finder);
    }

    // Get title element
    public FlutterElement getNotificationTitle() {
        waitForElementByKey(NOTIFICATION_TITLE_KEY, 10);
        return getElementByKey(NOTIFICATION_TITLE_KEY);
    }

    // Get notification list
    public List<FlutterElement> getNotificationList() {
        waitForElementByKey(NOTIFICATION_LIST_KEY, 10);
        return getElementsByKey(NOTIFICATION_LIST_KEY);
    }

    // Click back button
    public void tapBackButton() {
        waitForElementByKey(BACK_BUTTON_KEY, 10);
        clickOnElement(getElementByKey(BACK_BUTTON_KEY));
        System.out.println("🔙 Back button tapped");
    }

    // Assert notification screen is displayed
    public void assertNotificationScreenVisible() {
        if (getNotificationTitle() == null || isListEmpty(getNotificationList()))
        {
            throw new AssertionError("❌ Notification screen elements are not visible.");
        }
        System.out.println("✅ Notification screen is visible");
    }
}
