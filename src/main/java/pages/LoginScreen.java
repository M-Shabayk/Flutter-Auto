package pages;

import io.appium.java_client.AppiumDriver;
import io.github.ashwith.flutter.FlutterElement;
import io.github.ashwith.flutter.FlutterFinder;

public class LoginScreen extends BasePage {
    public LoginScreen(AppiumDriver driver, FlutterFinder flutterFinder) {
        super(driver, flutterFinder);
    }


    // Element keys as constants
    private static final String LOGIN_TEXTBOX = "my_account_tab";
    private static final String LOGIN_BUTTON = "تسجيل الدخول";



    public FlutterElement getLoginTextField() {
        waitForElementByKey(LOGIN_TEXTBOX, 10);
        return getElementByKey(LOGIN_TEXTBOX);
    }

    public FlutterElement getLoginButton() {
        waitForElementByKey(LOGIN_BUTTON, 10);
        return getElementByKey(LOGIN_BUTTON);
    }

     public void clickOnLoginTextBox() {
        getLoginTextField().click();
    }


    public void enterPhoneNumber(String phoneNumber) {
        clickOnLoginTextBox();
        enterText(getLoginTextField(), phoneNumber);
    }

    public void clickOnLoginButton() {
        clickOnElement(getLoginButton());
    }

    public void loginByPhoneNumber(String phoneNumber) { //method for login that contains login data
        enterPhoneNumber(phoneNumber);
        clickOnLoginButton();
    }


}
