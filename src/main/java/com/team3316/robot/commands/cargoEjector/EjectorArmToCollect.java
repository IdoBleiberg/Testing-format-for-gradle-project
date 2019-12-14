package com.team3316.robot.commands.cargoEjector;

import com.team3316.kit.DBugLogger;
import com.team3316.kit.commands.DBugCommand;
import com.team3316.robot.Robot;
import com.team3316.robot.subsystems.CargoEjector.EjectorArmState;
import com.team3316.robot.utils.InvalidStateException;

/**
 * EjectorArmToCollect
 */
public class EjectorArmToCollect extends DBugCommand {

  private boolean _shouldRun = true;

  protected EjectorArmToCollect() {
    requires(Robot.cargoEjector);
  }

  @Override
  protected void init() {
    try {
      this._shouldRun = true;
      Robot.cargoEjector.setArmState(EjectorArmState.COLLECT);
    } catch (InvalidStateException e) {
      DBugLogger.getInstance().severe(e);
      this._shouldRun = false;
    }
    DBugLogger.getInstance().info("Moving ejector to collect (Command)");
  }

  @Override
  protected void execute() {

  }

  @Override
  protected boolean isFinished() {
    return Robot.cargoEjector.getArmState() == EjectorArmState.COLLECT || !this._shouldRun;
  }

  @Override
  protected void fin() {

  }

  @Override
  protected void interr() {

  }
}
