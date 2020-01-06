package com.team3316.robot.commands.elevator;

import com.team3316.kit.commands.DBugCommand;
import com.team3316.robot.Robot;

/**
 * MoveElevatorToInstallPanel
 */
public class MoveElevatorToInstallPanel extends DBugCommand {

  private double _startPos;

  public MoveElevatorToInstallPanel() {
    requires(Robot.elevator);
  }

  @Override
  public void init() {
    this._startPos = Robot.elevator.getPosition();
    Robot.elevator.installPanel();
  }

  @Override
  public void execute() {

  }

  @Override
  public boolean isFinished() {
    return this._startPos - Robot.elevator.getPosition() >= 4.5;
  }

  @Override
  public void fin() {

  }

  @Override
  public void interr() {

  }
  
}