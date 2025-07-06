package tests;

import org.testng.annotations.Test;
import pages.HomeVideosPage;
public class HomeVideosTest extends AndroidTestBase {
    HomeVideosPage videosScr;

    @Test
    public void checkNotificationIcnIsInteractable(){
        videosScr = new HomeVideosPage(driver, finder);
        videosScr.clickOnNotificationIcnByKey();
    }
}