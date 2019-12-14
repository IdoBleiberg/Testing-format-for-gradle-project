
package com.team3316.robot.commands.cargoIntake;

import com.team3316.kit.DBugLogger;
import com.team3316.kit.commands.DBugCommand;
import com.team3316.robot.Robot;
import com.team3316.robot.subsystems.CargoIntake.IntakeArmState;
import com.team3316.robot.utils.InvalidStateException;

/**
 * CargoIntakeClose
 */
public class CargoIntakeClose extends DBugCommand {

  private boolean _shouldRun = true;

  public CargoIntakeClose() {
    requires(Robot.cargoIntake);
  }

  @Override
  protected void init() {
    try {
      this._shouldRun = true;
      Robot.cargoIntake.setBrake(false);
      Robot.cargoIntake.setArmState(IntakeArmState.IN);
    } catch (InvalidStateException e) {
      DBugLogger.getInstance().severe(e);
      this._shouldRun = false;
    }
    DBugLogger.getInstance().info("Closing cargo intake");
  }

  @Override
  protected void execute() {

  }

  @Override
  protected boolean isFinished() {
    return Robot.cargoIntake.getArmState() == IntakeArmState.IN || !this._shouldRun;
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
