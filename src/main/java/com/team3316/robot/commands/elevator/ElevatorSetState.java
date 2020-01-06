package com.team3316.robot.commands.elevator;

import com.team3316.kit.DBugLogger;
import com.team3316.kit.commands.DBugCommand;
import com.team3316.robot.Robot;
import com.team3316.robot.subsystems.Elevator.ElevatorState;
import com.team3316.robot.utils.InvalidStateException;

/**
 * ElevatorSetState
 */
public class ElevatorSetState extends DBugCommand {
  private ElevatorState _wantedState;
  private boolean _shouldRun = true;

  public ElevatorSetState(ElevatorState wanted) {
    requires(Robot.elevator);
    this._wantedState = wanted;
  }

  @Override
  public void init() {
    try {
      this._shouldRun = true;
      Robot.elevator.setState(this._wantedState);
    } catch (InvalidStateException e) {
      DBugLogger.getInstance().severe(e);
      this._shouldRun = false;
    }
    DBugLogger.getInstance().info("Moving elevator to " + this._wantedState.toString());
  }

  @Override
  public void execute() {
    // Nothin'
  }

  @Override
  public boolean isFinished() {
    DBugLogger.getInstance().info(String.valueOf(this._wantedState.getHeight()) + " next to " + String.valueOf(Robot.elevator.getState().getHeight()));
    return this._wantedState.getHeight() == Robot.elevator.getState().getHeight() || !this._shouldRun;
  }

  @Override
  public void fin() {
    // Nothin'
  }

  @Override
  public void interr() {
    this.fin();
  }
}
