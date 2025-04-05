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
        if (checkIfUpdateRequired(currentVersionYear)) {
            turnOffDevices(applianceList);
            currentVersionYear = LocalDate.now().getYear();
            System.out.println("System has updated to Version " + UpdateSystem.currentVersionYear);
            return true;
        }
        return false;
    }

    /**
     * Checks if current date is equal to the updates date and time or after and not yet updated
     */
    private static boolean checkIfUpdateRequired(int currentVersionYear) {
        //If current version is up-to-date or newer don't update
        if (currentVersionYear >= currentDate.getYear())
            return false;

        // Update each year on January 1st 1:00am local time
        LocalDate updateDate = LocalDate.of(currentDate.getYear(), 1, 1);
        LocalTime updateTime = LocalTime.of(1, 0);

        // Checks if date is equal to or after the update date, then checks the time if it's the same day.
        if (!currentDate.isBefore(updateDate))
            return currentDate.isEqual(updateDate) ? !currentTime.isBefore(updateTime) : true;
        else
            return false;
    }

    /**
     * Forces all devices/appliances to turn off for the update
     */
    private static void turnOffDevices(List<Appliance> appliancesList) {
        // Force all appliances to turn off
        for (Appliance appliance : appliancesList) {
            appliance.forceOff();
        }
    }
}
