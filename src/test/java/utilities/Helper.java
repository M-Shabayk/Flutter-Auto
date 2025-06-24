package utilities;

import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class Helper {
    public static void CaptureScreenshot(WebDriver driver, String screenshotName) {
        // creating path for saving the screenshots
        Path destination = Paths.get("./screenshots", screenshotName + ".png");
        try {
            // creating file input and output to write into the screenshot
            Files.createDirectories(destination.getParent());
            FileOutputStream fileInOut = new FileOutputStream(destination.toString());
            // putting the screenshot into the file
            fileInOut.write(((TakesScreenshot) driver).getScreenshotAs(OutputType.BYTES));
            // close the file after adding the screenshot
            fileInOut.close();
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("Error while taking screenshot" + e.getMessage());
        }
    }
}