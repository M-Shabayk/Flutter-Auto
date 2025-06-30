package pages;

import io.appium.java_client.AppiumDriver;
import io.github.ashwith.flutter.FlutterElement;
import io.github.ashwith.flutter.FlutterFinder;

public class LoginPage extends BasePage {

    // Element keys as constants
    private static final String SKIP_BUTTON_KEY = "skip_button_onboarding";
    private static final String SKIP_BUTTON_TEXT = "تخطي";
    private static final String MY_ACCOUNT_TAB_KEY = "my_account_tab";

    public LoginPage(AppiumDriver appiumDriver, FlutterFinder flutterFinder) {
        super(appiumDriver, flutterFinder);
    }

    public FlutterElement getSkipButtonByKey() {
        waitForElementByKey(SKIP_BUTTON_KEY, 10);
        return getElementByKey(SKIP_BUTTON_KEY);
    }

    public void clickOnSkipButtonByKey() {
        clickOnElement(getSkipButtonByKey());
    }

    public FlutterElement getSkipButtonByText() {
        waitForElementByText(SKIP_BUTTON_TEXT, 10);
        return getElementByText(SKIP_BUTTON_TEXT);
    }

    public void clickOnSkipButtonByText() {
        clickOnElement(getSkipButtonByText());
    }

    public FlutterElement getMyAccountTabButtonByKey() {
        waitForElementByKey(MY_ACCOUNT_TAB_KEY, 10);
        return getElementByKey(MY_ACCOUNT_TAB_KEY);
    }

    public void clickOnMyAccountTabButtonByKey() {
        clickOnElement(getMyAccountTabButtonByKey());
    }

    public void clickOnLoginButton() {
        //clickOnSkipButtonByKey();
        clickOnMyAccountTabButtonByKey();
    }

    public void performLogin(String email, String password) {

    }
}
