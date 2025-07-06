package tests;

import org.testng.annotations.Test;
import pages.LoginScreen;

public class LoginScreenTest extends AndroidTestBase{

    LoginScreen loginSrn;

    private static final String Phone_No = "0570170170";

    @Test
    public void login() {
        loginSrn = new LoginScreen(driver, finder);
        loginSrn.loginByPhoneNumber(Phone_No);
    }
}
