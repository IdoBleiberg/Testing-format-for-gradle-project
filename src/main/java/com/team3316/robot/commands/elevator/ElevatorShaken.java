package com.team3316.robot.commands.elevator;

import com.team3316.kit.commands.DBugCommand;
import com.team3316.robot.Robot;

/**
 * ElevatorSetPosition
 */
public class ElevatorShaken extends DBugCommand{

  public ElevatorShaken() {
    requires(Robot.elevator);
  }

  @Override
  protected void init() {
    Robot.elevator.setPositionUnsafe(Robot.elevator.getPosition());
  }

  @Override
  protected void execute() {

  }

  @Override
  protected boolean isFinished() {
    return true;
  }

  @Override
  protected void fin() {

  }

  @Override
  protected void interr() {

  }

}