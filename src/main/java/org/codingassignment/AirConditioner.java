package org.codingassignment;

/**
 * Air Conditioner implements Appliance and can have its mode selected
 * by the corresponding index
 */
public class AirConditioner implements Appliance {

    public enum Mode {
        OFF, AUTO, FAN, DRY, COOL, SLEEP
    }

    private Mode currentMode;

    public AirConditioner() {
        currentMode = Mode.OFF;
        SmartHomeLogger.msg("AC initialized: OFF state");
    }

    @Override
    public boolean isOff() {
        return currentMode == Mode.OFF;
    }

    @Override
    public void forceOff() {
        currentMode = Mode.OFF;
        SmartHomeLogger.msg("AC forced off");
    }

    /**
     * Sets the AC's mode using an index value corresponding to the Mode
     */
    public boolean setMode(int modeIndex) {
        if (modeIndex >= Mode.values().length || modeIndex < 0)
            return false;

        currentMode = Mode.values()[modeIndex];
        System.out.println(MessageColor.SUCCESS + "AC mode set to " + currentMode.toString() + MessageColor.RESET);
        return true;
    }
}
