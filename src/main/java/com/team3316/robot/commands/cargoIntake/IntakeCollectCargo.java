
package com.team3316.robot.commands.cargoIntake;

import com.team3316.kit.DBugLogger;
import com.team3316.kit.commands.DBugCommand;
import com.team3316.robot.Robot;
import com.team3316.robot.subsystems.CargoIntake.IntakeRollersState;

/**
 * IntakeCollectCargo
 */
public class IntakeCollectCargo extends DBugCommand {
  private double _precentage;

  public IntakeCollectCargo() {
    requires(Robot.cargoIntake);
    this._precentage = 1;
  }

  @Override
  protected void init() {
    DBugLogger.getInstance().info("Intake started Collecting cargo");
  }

  @Override
  protected void execute() {
    Robot.cargoIntake.setRollersState(IntakeRollersState.IN);
    Robot.cargoIntake.setPrecntage(this._precentage);
  }

  @Override
  protected boolean isFinished() {
    return Robot.cargoEjector.hasCargo();

  }

  @Override
  protected void fin() {
    Robot.cargoIntake.setRollersState(IntakeRollersState.STOPPED);
    Robot.cargoIntake.setPrecntage(0.0);
  }

  @Override
  protected void interr() {
    this.fin();
  }
}
