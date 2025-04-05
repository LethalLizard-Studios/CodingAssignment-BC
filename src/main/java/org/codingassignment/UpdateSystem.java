package org.codingassignment;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

/**
 * Updates the system and turns off all devices if equal to or past the set
 * date and time (January 1st 1:00am local time). This ensures that it's
 * updated to the newest yearly version.
 */
public final class UpdateSystem {

    // The current version installed on the system
    public static int currentVersionYear = 2023;

    public static LocalDate currentDate = LocalDate.now();
    public static LocalTime currentTime = LocalTime.now();

    /**
     * Verifies if update required then updates and installs
     */
    public static boolean checkForUpdateAndInstall(List<Appliance> applianceList) {
        SmartHomeLogger.msg("Checking for system updates. Current version: " + currentVersionYear +
                ", Current date: " + currentDate + ", Current time: " + currentTime);

        if (checkIfUpdateRequired(currentVersionYear)) {
            SmartHomeLogger.msg("Update required, turning off all devices and installing update");
            turnOffDevices(applianceList);
            int oldVersion = currentVersionYear;
            currentVersionYear = LocalDate.now().getYear();
            SmartHomeLogger.msg("System updated from version " + oldVersion + " to version " + currentVersionYear);
            System.out.println("System has updated to Version " + UpdateSystem.currentVersionYear);
            return true;
        }

        SmartHomeLogger.msg("No update required at this time");
        return false;
    }

    /**
     * Checks if current date is equal to the updates date and time or after and not yet updated
     */
    private static boolean checkIfUpdateRequired(int currentVersionYear) {
        //If current version is up-to-date or newer don't update
        if (currentVersionYear >= currentDate.getYear()) {
            SmartHomeLogger.msg("Current version is up-to-date or newer");
            return false;
        }

        // Update each year on January 1st 1:00am local time
        LocalDate updateDate = LocalDate.of(currentDate.getYear(), 1, 1);
        LocalTime updateTime = LocalTime.of(1, 0);
        SmartHomeLogger.msg("Update date: " + updateDate + ", Update time: " + updateTime);

        // Checks if date is equal to or after the update date, then checks the time if it's the same day.
        if (!currentDate.isBefore(updateDate)) {
            boolean updateRequired = currentDate.isEqual(updateDate) ? !currentTime.isBefore(updateTime) : true;
            SmartHomeLogger.msg("Date is after or equal to update date");
            return updateRequired;
        }
        else {
            SmartHomeLogger.msg("Current date is before update date");
            return false;
        }
    }

    /**
     * Forces all devices/appliances to turn off for the update
     */
    private static void turnOffDevices(List<Appliance> appliancesList) {
        // Force all appliances to turn off
        for (Appliance appliance : appliancesList) {
            appliance.forceOff();
        }
        SmartHomeLogger.msg("All devices turned off");
    }
}
