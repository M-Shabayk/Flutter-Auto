package pages;
import io.appium.java_client.AppiumDriver;
import io.github.ashwith.flutter.FlutterElement;
import io.github.ashwith.flutter.FlutterFinder;
public class HomeVideosPage extends BasePage {
    public HomeVideosPage(AppiumDriver driver, FlutterFinder flutterFinder) {
        super(driver, flutterFinder);
    }
    // Element keys as constants
    private static final String NOTIFICATION_BUTTON = "skip_button_onboarding";

    public FlutterElement getNotificationIcnByKey() {
        waitForElementByKey(NOTIFICATION_BUTTON, 10);
        return getElementByKey(NOTIFICATION_BUTTON);
    }
    public void clickOnNotificationIcnByKey() {
        clickOnElement(getNotificationIcnByKey());
    }
    /*public void viewHomeVideoScr(String email, String password) {

    }*/
}
