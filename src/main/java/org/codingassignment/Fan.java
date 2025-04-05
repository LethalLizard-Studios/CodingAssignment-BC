package org.codingassignment;

/**
 * Fan implements Appliance and can have its speed setting turned
 * up or down by one mode at a time
 */
public class Fan implements Appliance {

    public enum Speed {
        OFF, LOW, HIGH
    }

    private Speed currentSpeed;

    public Fan() {
        currentSpeed = Speed.OFF;
        SmartHomeLogger.msg("Fan initialized: OFF state");
    }

    @Override
    public boolean isOff() {
        return currentSpeed == Speed.OFF;
    }

    @Override
    public void forceOff() {
        currentSpeed = Speed.OFF;
        SmartHomeLogger.msg("Fan forced off");
    }

    /**
     * Lowers the fans speed setting by one mode
     */
    public void reduceSpeed() {
        setCurrentSpeed(currentSpeed.ordinal() - 1);
        SmartHomeLogger.msg("Fan speed reduced to " + currentSpeed);
    }

    /**
     * Increases the fans speed setting by one mode
     */
    public void increaseSpeed() {
        setCurrentSpeed(currentSpeed.ordinal() + 1);
        SmartHomeLogger.msg("Fan speed increased to " + currentSpeed);
    }

    /**
     * Sets the fans speed to an index clamped within its boundaries
     */
    private void setCurrentSpeed(int speed) {
        int index = Math.max(0, Math.min(speed, Speed.values().length - 1));
        currentSpeed = Speed.values()[index];
        System.out.println(MessageColor.SUCCESS + "Fan speed set to " + currentSpeed.toString() + MessageColor.RESET);
    }
}
