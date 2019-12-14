package com.team3316.robot.commands.cargoEjector;

//import java.lang.module.ModuleDescriptor.Requires;

import com.team3316.kit.DBugLogger;
import com.team3316.kit.commands.DBugCommand;
import com.team3316.robot.Robot;
import com.team3316.robot.subsystems.CargoEjector.EjectorArmState;
import com.team3316.robot.utils.InvalidStateException;

/**
 * EjectorToIntallLVL3
 */
public class EjectorToIntallLVL3 extends DBugCommand {

  private boolean _shouldRun = true;

  public EjectorToIntallLVL3() {
    requires(Robot.cargoEjector);
  }

  @Override
  protected void init() {
    try {
      this._shouldRun = true;
      Robot.cargoEjector.setArmState(EjectorArmState.INSTALL_LVL3);
    } catch (InvalidStateException e) {
      DBugLogger.getInstance().severe(e);
      this._shouldRun = false;
	}
  }

  @Override
  protected void execute() {

  }

  @Override
  protected boolean isFinished() {
    return Robot.cargoEjector.getArmState() == EjectorArmState.INSTALL_LVL3 || !this._shouldRun;
  }

  @Override
  protected void fin() {

  }

  @Override
  protected void interr() {

  }
  
}