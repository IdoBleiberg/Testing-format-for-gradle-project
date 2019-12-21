package com.team3316.robot.commands.elevator;

import com.team3316.kit.DBugLogger;
import com.team3316.kit.commands.DBugCommand;

/**
 * ElevatorJoysticks
 */
public class ElevatorJoysticks extends DBugCommand{

  @Override
  protected void init() {
    DBugLogger.getInstance().info("Started moving elevator with joysticks");
  }

  @Override
  protected void execute() {
    //Robot.elevator.setPercentage(Robot.joysticks.getXboxRightY());
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