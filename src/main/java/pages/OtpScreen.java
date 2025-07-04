package pages;

import io.appium.java_client.AppiumDriver;
import io.github.ashwith.flutter.FlutterElement;
import io.github.ashwith.flutter.FlutterFinder;

public class OtpScreen extends BasePage {
    public OtpScreen(AppiumDriver driver, FlutterFinder flutterFinder) {
        super(driver, flutterFinder);
    }

    // Element keys as constants
    private static final String OTP_No_BOX = "";
    private static final String SUBMIT_BUTTON = "تأكيد التسجيل";


    public FlutterElement getOtpNoField() {
        waitForElementByKey(OTP_No_BOX, 10);
        return getElementByKey(OTP_No_BOX);
    }

    public FlutterElement getSubmitButton() {
        waitForElementByKey(SUBMIT_BUTTON, 10);
        return getElementByKey(SUBMIT_BUTTON);
    }

    public void clickOnOtpNoBox() {

        getOtpNoField().click();
    }


    public void enterOtpNumber(String phoneNumber) {
        clickOnOtpNoBox();
        enterText(getOtpNoField(), phoneNumber);
    }

    public void clickOnsubmitButton() {
        clickOnElement(getSubmitButton());
    }

    public void enterByPhoneNumber(String Number) {
        enterOtpNumber(Number);
        clickOnsubmitButton();
    }
}
