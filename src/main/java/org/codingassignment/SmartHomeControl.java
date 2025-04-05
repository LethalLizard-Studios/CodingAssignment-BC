package org.codingassignment;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/**
 * Main class for the Smart Home Appliance Controller
 */
public class SmartHomeControl {

    static Light light;
    static Fan fan;
    static AirConditioner airConditioner;

    /**
     * Main method for the Smart Home Appliance Controller
     */
    public static void main(String[] args) {
        SmartHomeLogger.initialize();
        SmartHomeLogger.msg("Smart Home Control starting");

        List<Appliance> applianceList = new ArrayList<>();

        light = new Light();
        applianceList.add(light);

        fan = new Fan();
        applianceList.add(fan);

        airConditioner = new AirConditioner();
        applianceList.add(airConditioner);

        // If the version is below the current update and past or equal to the update date it will automatically update
        boolean wasUpdated = UpdateSystem.checkForUpdateAndInstall(applianceList);
        SmartHomeLogger.msg(wasUpdated ? "System was updated" : "No update required");

        System.out.println(MessageColor.SUCCESS + "Welcome to Smart Home Appliance Control\n" + MessageColor.RESET);

        // Instructions
        System.out.println("""
                Input Ex: a2 sets the AC to FAN mode\s
                Lights (l), 0 = OFF, 1 = ON\s
                Fans (f), 0 = Reduce Speed, 1 = Increase Speed\s
                AC (a), 0 = OFF, AUTO = 1, FAN = 2, DRY = 3, COOL = 4, SLEEP = 5""");

        Scanner scanner = new Scanner(System.in);

        char applianceType;
        int mode;

        while (scanner.hasNextLine()) {
            String line = scanner.nextLine();
            SmartHomeLogger.msg("User input: " + line);

            if (line.length() == 1 && line.charAt(0) == 'x') {
                break;
            }

            if (line.length() == 2 && Character.isDigit(line.charAt(1))) {
                applianceType = Character.toLowerCase(line.charAt(0));

                // Subtract by the char of 0 to get the true integer value
                mode = line.charAt(1) - '0';
                SmartHomeLogger.msg("Appliance type: " + applianceType + ", Mode:" + mode);

                switch (applianceType) {
                    case 'l' -> setLightMode(mode);
                    case 'f' -> setFanMode(mode);
                    case 'a' -> setAirConditionerMode(mode);
                    default -> warningMessage("Warning: Appliance type was not found");
                }
            }
            else {
                warningMessage("Warning: Input must be 2 characters (Ex. f1)");
            }
        }

        SmartHomeLogger.msg("Shutting down");
    }

    private static void setLightMode(int mode) {
        if (light == null)
            warningMessage("Warning: No light was found");

        if (mode < 0 || mode > 1)
            warningMessage("Warning: A light may only be On (1) or Off (0)");
        else
            light.toggleSwitch(mode == 1);
    }

    private static void setFanMode(int mode) {
        if (fan == null)
            warningMessage("Warning: No fan was found");

        switch (mode) {
            case 0 -> fan.reduceSpeed();
            case 1 -> fan.increaseSpeed();
            default -> warningMessage("Warning: A fan can increase speed (1) or reduce speed (0)");
        }
    }

    private static void setAirConditionerMode(int mode) {
        if (airConditioner == null)
            warningMessage("Warning: No fan was found");

        if (!airConditioner.setMode(mode))
            warningMessage("Warning: AC modes are OFF (0), AUTO (1), FAN (2), DRY (3), COOL (4), SLEEP (5)");
    }

    /**
     * Prints warning message in yellow with an underline
     */
    public static void warningMessage(String message) {
        SmartHomeLogger.warning(message);
        System.out.println(MessageColor.WARNING + message + MessageColor.RESET);
    }
}