package com.team2502.basketball.subsystem;

import com.ctre.CANTalon;
import com.team2502.basketball.OI;
import com.team2502.basketball.RobotMap;
import com.team2502.basketball.command.teleop.DriveCommand;
import edu.wpi.first.wpilibj.RobotDrive;
import edu.wpi.first.wpilibj.command.Subsystem;


public class DriveTrainSubsystem extends Subsystem
{
    private static final Pair<Double, Double> SPEED_CONTAINER = new Pair<Double, Double>();

    public final CANTalon leftTalon0; //enc
    public final CANTalon leftTalon1;
    public final CANTalon rightTalon0;
    public final CANTalon rightTalon1; //enc

    private final RobotDrive drive;
    private double lastLeft;
    private double lastRight;
    private boolean isNegativePressed;
    private boolean negative;

    public DriveTrainSubsystem()
    {
        lastLeft = 0.0D;
        lastRight = 0.0D;

        leftTalon0 = new CANTalon(RobotMap.Motor.LEFT_DRIVE_TALON_0);
        leftTalon1 = new CANTalon(RobotMap.Motor.LEFT_DRIVE_TALON_1);
        rightTalon0 = new CANTalon(RobotMap.Motor.RIGHT_DRIVE_TALON_0);
        rightTalon1 = new CANTalon(RobotMap.Motor.RIGHT_DRIVE_TALON_1);

        drive = new RobotDrive(leftTalon0, leftTalon1, rightTalon0, rightTalon1);

        drive.setSafetyEnabled(true);
    }

    public double turningFactor() { return Math.abs(OI.JOYSTICK_DRIVE_LEFT.getY() - OI.JOYSTICK_DRIVE_RIGHT.getY());}

    @Override
    protected void initDefaultCommand() { setDefaultCommand(new DriveCommand()); }

    /**
     * Used to gradually increase the speed of the robot.
     *
     * @param out The object to store the data in
     * @return the speed of the robot
     */
    private Pair<Double, Double> getSpeed(Pair<Double, Double> out)
    {
        double joystickLevel;
        // Get the base speed of the robot
        if(negative) { joystickLevel = -OI.JOYSTICK_DRIVE_RIGHT.getY(); }
        else { joystickLevel = -OI.JOYSTICK_DRIVE_LEFT.getY(); }

        // Only increase the speed by a small amount
        double diff = joystickLevel - lastLeft;
        if(diff > 0.1D) { joystickLevel = lastLeft + 0.1D; }
        else if(diff < 0.1D) { joystickLevel = lastLeft - 0.1D; }

        lastLeft = joystickLevel;
        out.left = joystickLevel;

        if(negative) { joystickLevel = -OI.JOYSTICK_DRIVE_LEFT.getY(); }
        else { joystickLevel = -OI.JOYSTICK_DRIVE_RIGHT.getY(); }

        diff = joystickLevel - lastRight;
        if(diff > 0.1D) { joystickLevel = lastRight + 0.1D; }
        else if(diff < 0.1D) { joystickLevel = lastRight - 0.1D; }

        lastRight = joystickLevel;
        out.right = joystickLevel;

        // Sets the speed to 0 if the speed is less than 0.05 or larger than -0.05
        if(Math.abs(out.left) < 0.05D) { out.left = 0.0D; }
        if(Math.abs(out.right) < 0.05D) { out.right = 0.0D; }

        return out;
    }

    private Pair<Double, Double> getSpeed() { return getSpeed(SPEED_CONTAINER); }

    public void drive()
    {
        Pair<Double, Double> speed = getSpeed();


        //reverse drive
        if(OI.JOYSTICK_DRIVE_LEFT.getRawButton(RobotMap.Joystick.Button.REVERSE_DRIVE) && !isNegativePressed) { negative = !negative; }

        isNegativePressed = OI.JOYSTICK_DRIVE_LEFT.getRawButton(RobotMap.Joystick.Button.REVERSE_DRIVE);

        if(negative) { drive.tankDrive(-speed.left, -speed.right, true); }
        else { drive.tankDrive(speed.left, speed.right, true); }
    }

    /**
     * This is the main method for powering motors for auto
     * <br  />
     * Left and Right will turn on a dime
     * @param direction Type which direction you want to go Ex: Forwards, Backwards, Left, Right
     * @param speed How much power will be sent to the motors
     *              Max = 1 Min = -1
     */
    public void autoDrive(double speed, String direction)
    {
        if(direction.equalsIgnoreCase("Forwards"))
        {
            leftTalon0.set(speed);
            leftTalon1.set(speed);
            rightTalon0.set(-speed);
            leftTalon1.set(-speed);
        }
        else if(direction.equalsIgnoreCase("Backwards"))
        {
            leftTalon0.set(-speed);
            leftTalon1.set(-speed);
            rightTalon0.set(speed);
            leftTalon1.set(speed);
        }
        else if(direction.equalsIgnoreCase("Left"))
        {
            leftTalon0.set(-speed);
            leftTalon1.set(-speed);
            rightTalon0.set(-speed);
            rightTalon1.set(-speed);
        }
        else if(direction.equalsIgnoreCase("Right"))
        {
            leftTalon0.set(speed);
            leftTalon1.set(speed);
            rightTalon0.set(speed);
            rightTalon1.set(speed);
        }
    }

    /**
     * <br   />
     * ++ equals forwards -- equals backwards
     * +-equals right -+equals left
     * <br  />
     * This allows us to move left and right side at individual speeds
     * @param speed this sets speed for left side
     * @param speed2 this sets speed for right side
     *
     */
    public void autoDrive(double speed, double speed2)
    {
        leftTalon0.set(speed);
        leftTalon1.set(speed);
        rightTalon0.set(-speed2);
        rightTalon1.set(-speed2);
    }


    @SuppressWarnings("WeakerAccess")
    public static class Pair<L, R>
    {
        public L left;
        public R right;

        private String nameL;
        private String nameR;

        public Pair(L left, R right)
        {
            this.left = left;
            this.right = right;
            this.nameL = left.getClass().getSimpleName();
            this.nameR = right.getClass().getSimpleName();
        }

        public Pair() {}

        @Override
        public String toString()
        {
            return new StringBuilder(100 + nameL.length() + nameR.length()).append("Pair<").append(nameL).append(',')
                                                                           .append(nameR).append("> { \"left\": \"").append(left).append("\", \"right\": \"").append(right)
                                                                           .append("\" }").toString();
        }

        @Override
        public int hashCode() { return left.hashCode() * 13 + (right == null ? 0 : right.hashCode()); }

        @Override
        public boolean equals(Object o)
        {
            if(this == o) { return true; }
            if(o instanceof Pair)
            {
                Pair pair = (Pair) o;
                return (left != null ? left.equals(pair.left) : pair.left == null)
                       && (left != null ? left.equals(pair.left) : pair.left == null);
            }
            return false;
        }
    }
}
