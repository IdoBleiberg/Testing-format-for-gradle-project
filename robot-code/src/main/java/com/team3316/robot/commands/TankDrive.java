package com.team3316.robot.commands;

import com.team3316.kit.commands.DBugCommand;
import com.team3316.robot.Robot;

public class TankDrive extends DBugCommand {

  public TankDrive() {
    requires(Robot.drivetrain);
  }

  @Override
  protected void init() {

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
  protected void fin() {

  }

  @Override
  protected void interr() {

  }

}