package com.team3316.robot.commands;

import com.team3316.robot.Robot;

import edu.wpi.first.wpilibj.command.Command;

public class TankDrive extends Command {

  public TankDrive() {
    requires(Robot.drivetrain);
  }

  @Override
  protected void initialize() {

  }

  @Override
  protected void execute() {
    Robot.drivetrain.setMotors(Robot.joysticks.getLeftY(), Robot.joysticks.getRightY());
  }

  @Override
  protected boolean isFinished() {
    return false;
  }

  @Override
  protected void end() {

  }

  @Override
  protected void interrupted() {

  }

}