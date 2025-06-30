package utilities;

import io.appium.java_client.AppiumDriver;

import java.time.Duration;
import java.util.List;
import java.util.Map;

public class FlutterIsolateHandler {
    public static void setupFlutterDriverWithDynamicIsolates(AppiumDriver driver) throws InterruptedException {
        Thread.sleep(5000); // Ensure VM is ready

        Object vmInfoResult = driver.executeScript("flutter:getVMInfo");
        Map<String, Object> vmInfo = (Map<String, Object>) vmInfoResult;
        List<Map<String, Object>> isolates = (List<Map<String, Object>>) vmInfo.get("isolates");

        if (isolates == null || isolates.isEmpty()) {
            throw new RuntimeException("No isolates found in VM info!");
        }

        System.out.println("🎯 Found " + isolates.size() + " isolates");

        boolean success = false;

        for (Map<String, Object> isolate : isolates) {
            String isolateId = (String) isolate.get("id");
            String isolateName = (String) isolate.get("name");

            try {
                driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(5));
                driver.executeScript("flutter:setIsolateId", isolateId);
                //driver.executeScript("flutter:waitForFirstFrame");
                System.out.println("✅ Connected to isolate: " + isolateId + " (" + isolateName + ")");
                success = true;
                break;
            } catch (Exception e) {
                System.out.println("❌ Failed with isolate " + isolateId + ": " + e.getMessage());
            } finally {
                driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
            }
        }

        if (!success) {
            throw new RuntimeException("❌ All isolates failed!");
        }
    }

}
