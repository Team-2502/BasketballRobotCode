package com.team2502.basketball.command.teleop;


import com.team2502.basketball.Robot;
import com.team2502.basketball.subsystem.DriveTrainSubsystem;
import edu.wpi.first.wpilibj.command.Command;

/**
 * Takes care of all Drivetrain related operations during Teleop, including automatic shifting
 * Automatic shifting will:
 * <li>
 * <ul>Space out shifting by at least 1/2 second</ul>
 * <ul>Invert itself if the driver holds a special button</ul>
 * <ul>Only shift when going mostly straight</ul>
 * <ul>Shift up if accelerating, going fast, and the driver is pushing hard on the sticks</ul>
 * <ul>Shift down if the sticks are being pushed but there is no acceleration</ul>
 * <ul>Shift down if the sticks aren't being pushed hard and the robot is going slow</ul>
 * </li>
 */
public class DriveCommand extends Command
{
    private final DriveTrainSubsystem driveTrainSubsystem;

    public DriveCommand()
    {
        requires(Robot.DRIVE_TRAIN);
        driveTrainSubsystem = Robot.DRIVE_TRAIN;
    }

    @Override
    protected void initialize() {}

    @Override
    protected void execute()
    {
        driveTrainSubsystem.drive();
    }

    @Override
    protected boolean isFinished() { return false; }

    @Override
    protected void end() {}

    @Override
    protected void interrupted() { end(); }
}
