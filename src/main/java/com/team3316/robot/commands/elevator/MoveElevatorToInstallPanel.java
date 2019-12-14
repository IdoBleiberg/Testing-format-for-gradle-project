package com.team3316.robot.commands.elevator;

import com.team3316.kit.commands.DBugCommand;
import com.team3316.robot.Robot;
import com.team3316.robot.subsystems.Elevator;

/**
 * MoveElevatorToInstallPanel
 */
public class MoveElevatorToInstallPanel extends DBugCommand {

  private double _startPos;

  public MoveElevatorToInstallPanel() {
    requires(Robot.elevator);
  }

  @Override
  protected void init() {
    this._startPos = Robot.elevator.getPosition();
    Robot.elevator.installPanel();
  }

  @Override
  protected void execute() {

  }

  @Override
  protected boolean isFinished() {
    return this._startPos - Robot.elevator.getPosition() >= 4.5;
  }

  @Override
  protected void fin() {

  }

  @Override
  protected void interr() {

  }
  
}