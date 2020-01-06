package com.team3316.robot.commands.elevator;

import com.team3316.kit.DBugLogger;
import com.team3316.kit.commands.DBugCommand;

/**
 * ElevatorJoysticks
 */
public class ElevatorJoysticks extends DBugCommand{

  @Override
  public void init() {
    DBugLogger.getInstance().info("Started moving elevator with joysticks");
  }

  @Override
  public void execute() {
    //Robot.elevator.setPercentage(Robot.joysticks.getXboxRightY());
  }

  @Override
  public boolean isFinished() {
    return false;
  }

  @Override
  public void fin() {

  }

  @Override
  public void interr() {

  }

  
}