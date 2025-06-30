package utilities;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class EmulatorManager {

    public static String getFirstEmulatorName() {
        String emulatorName = null;
        try {
            Process process = Runtime.getRuntime().exec("emulator -list-avds");
            BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));
            emulatorName = reader.readLine(); // Get the first available emulator name
            reader.close();

            if (emulatorName == null) {
                throw new RuntimeException("❌ No emulators found. Please create one using Android Studio AVD Manager.");
            }

            System.out.println("✅ Found emulator: " + emulatorName);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return emulatorName;
    }

    public static void startEmulator() throws IOException {
        String emulatorName = getFirstEmulatorName();
        if (emulatorName != null) {
            System.out.println("🚀 Starting emulator: " + emulatorName);
            new ProcessBuilder("emulator", "-avd", emulatorName).start();
        } else {
            throw new RuntimeException("❌ No AVD found to start.");
        }
    }

    public static void closeEmulator() {
        try {
            ProcessBuilder builder = new ProcessBuilder("adb", "emu", "kill");
            builder.inheritIO().start();
            System.out.println("🛑 Emulator shutdown command sent");
        } catch (IOException e) {
            System.err.println("❌ Failed to close emulator");
        }
    }
}
