package com.team3316.robot.commands.cargoEjector;

import com.team3316.kit.DBugLogger;
import com.team3316.kit.commands.DBugCommand;
import com.team3316.robot.Robot;
import com.team3316.robot.subsystems.CargoEjector.EjectorArmState;
import com.team3316.robot.utils.InvalidStateException;

/**
 * CargoEjectorSetState
 */
public class CargoEjectorSetState extends DBugCommand{

  private boolean _shouldRun = true;
  private EjectorArmState _wantedState;

  public CargoEjectorSetState(EjectorArmState state) {
    requires(Robot.cargoEjector);
    this._wantedState = state;
  }

  @Override
  protected void init() {
    try {
      this._shouldRun = true;
      Robot.cargoIntake.setBrake(false);
      Robot.cargoEjector.setArmState(this._wantedState);
    } catch (InvalidStateException e) {
      DBugLogger.getInstance().severe(e);
      this._shouldRun = false;
    }

  }

  @Override
  protected void execute() { }

  @Override
  protected boolean isFinished() {
    return Robot.cargoEjector.getArmState() == this._wantedState || !this._shouldRun;
  }

  @Override
  protected void fin() { }

  @Override
  protected void interr() { }

  
}