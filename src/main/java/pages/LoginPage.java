package pages;

import io.appium.java_client.AppiumDriver;
import io.github.ashwith.flutter.FlutterElement;
import io.github.ashwith.flutter.FlutterFinder;

public class LoginPage extends BasePage {

    // Element keys as constants
    private static final String SKIP_BUTTON_KEY = "skip_button_onboarding";
    private static final String MY_ACCOUNT_TAB_KEY = "my_account_tab";

    public LoginPage(AppiumDriver appiumDriver, FlutterFinder flutterFinder) {
        super(appiumDriver, flutterFinder);
    }

    public FlutterElement getSkipButton() {
        waitForElementByKey(SKIP_BUTTON_KEY);
        return getElementByKey(SKIP_BUTTON_KEY);
    }

    public FlutterElement getMyAccountTabButton() {
        waitForElementByKey(MY_ACCOUNT_TAB_KEY);
        return getElementByKey(MY_ACCOUNT_TAB_KEY);
    }

    public void tapLoginButton() {
        //waitForAppToBeIdle();
        getSkipButton().click();
        getMyAccountTabButton().click();
    }

    public void performLogin(String email, String password) {

    }
}
