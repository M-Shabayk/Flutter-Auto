package tests;

import org.testng.annotations.Test;
import pages.LoginScreen;
import pages.OtpScreen;

public class OtpScreenTest extends AndroidTestBase {
    OtpScreen  otpSrn;

    private static final String Otp_No = "1234";

    @Test
    public void enterOtp() {
        otpSrn = new OtpScreen(driver, finder);
        otpSrn.enterOtpNumber(Otp_No);
    }

}
