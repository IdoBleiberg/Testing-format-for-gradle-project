package com.team3316.robot.commands.cargoEjector;

import com.team3316.kit.DBugLogger;
import com.team3316.kit.commands.DBugCommand;
import com.team3316.robot.Robot;
import com.team3316.robot.subsystems.CargoEjector.EjectorRollerState;

/**
 * EjectorEjectCargo
 */
public class EjectorEjectCargo extends DBugCommand {
  private long _lastHadCargo, _delay;

  public EjectorEjectCargo() {
    requires(Robot.cargoEjector);
    this._delay = 20l;
  }

  @Override
  protected void init() {
    this._lastHadCargo = System.currentTimeMillis();
    DBugLogger.getInstance().info("Strated ejcting cargo");
  }

  @Override
  protected void execute() {
    Robot.cargoEjector.setRollerState(EjectorRollerState.OUT);
  }

  @Override
  protected boolean isFinished() {
    if (Robot.cargoEjector.hasCargo()) this._lastHadCargo = System.currentTimeMillis();
    long timeDelta = System.currentTimeMillis() - this._lastHadCargo;
    return timeDelta > this._delay;
  }

  @Override
  protected void fin() {
    Robot.cargoEjector.setRollerState(EjectorRollerState.STOP);
  }

  @Override
  protected void interr() {
    this.fin();
  }

}
