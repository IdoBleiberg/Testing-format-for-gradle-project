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
  public void init() {
    Robot.elevator.setPositionUnsafe(Robot.elevator.getPosition());
  }

  @Override
  public void execute() {

  }

  @Override
  public boolean isFinished() {
    return true;
  }

  @Override
  public void fin() {

  }

  @Override
  public void interr() {

  }

}