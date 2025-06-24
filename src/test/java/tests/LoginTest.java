package tests;

import org.testng.annotations.Test;
import pages.LoginPage;

public class LoginTest extends AndroidTestBase {
    LoginPage loginPage;

    @Test
    public void login() {
        loginPage = new LoginPage(driver, finder);
        loginPage.tapLoginButton();
    }
}