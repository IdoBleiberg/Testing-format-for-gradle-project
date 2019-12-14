package com.team3316.robot.commands.cargoEjector;

import com.team3316.kit.DBugLogger;
import com.team3316.kit.commands.DBugCommand;
import com.team3316.robot.Robot;
import com.team3316.robot.subsystems.CargoEjector.EjectorArmState;
import com.team3316.robot.utils.InvalidStateException;

/**
 * EjectorArmToEject
 */
public class EjectorArmToEject extends DBugCommand {

  private boolean _shouldRun = true;

  protected EjectorArmToEject() {
    requires(Robot.cargoEjector);
  }

  @Override
  protected void init() {
    try {
      this._shouldRun = true;
      Robot.cargoEjector.setArmState(EjectorArmState.EJECT);
    } catch (InvalidStateException e) {
      DBugLogger.getInstance().severe(e);
      this._shouldRun = false;
    } catch (Exception e) {
      DBugLogger.getInstance().severe(e);
      this._shouldRun = false;
    }
    DBugLogger.getInstance().info("Moving ejector to eject (Command)");
  }

  @Override
  protected void execute() {

  }

  @Override
  protected boolean isFinished() {
    DBugLogger.getInstance()
        .info(String.valueOf(Robot.cargoEjector.getArmState() == EjectorArmState.EJECT || !this._shouldRun) + " im here");
    return Robot.cargoEjector.getArmState() == EjectorArmState.EJECT || !this._shouldRun;
  }

  @Override
  protected void fin() {

  }

  @Override
  protected void interr() {

  }
}
