package com.team3316.robot.commands.cargoIntake;

import com.team3316.kit.DBugLogger;
import com.team3316.kit.commands.DBugCommand;
import com.team3316.robot.Robot;
import com.team3316.robot.subsystems.CargoIntake.IntakeArmState;
import com.team3316.robot.utils.InvalidStateException;

/**
 * CargoIntakeSetState
 */
public class CargoIntakeSetState extends DBugCommand{

  private boolean _shouldRun = true;
  private IntakeArmState _wantedState;

  public CargoIntakeSetState(IntakeArmState state) {
    requires(Robot.cargoIntake);
    this._wantedState = state;
  }

  @Override
  protected void init() {
    try {
      this._shouldRun = true;
      Robot.cargoIntake.setBrake(false);
      Robot.cargoIntake.setArmState(this._wantedState);
    } catch (InvalidStateException e) {
      DBugLogger.getInstance().severe(e);
      this._shouldRun = false;
    }

  }

  @Override
  protected void execute() { }

  @Override
  protected boolean isFinished() {
    return Robot.cargoIntake.getArmState() == this._wantedState || !this._shouldRun;
  }

  @Override
  protected void fin() {
    Robot.cargoIntake.setBrake(true);

  }

  @Override
  protected void interr() {
    this.fin();
  }

  
}