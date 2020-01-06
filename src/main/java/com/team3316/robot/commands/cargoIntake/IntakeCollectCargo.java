
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
  public void init() {
    DBugLogger.getInstance().info("Intake started Collecting cargo");
  }

  @Override
  public void execute() {
    Robot.cargoIntake.setRollersState(IntakeRollersState.IN);
    Robot.cargoIntake.setPrecntage(this._precentage);
  }

  @Override
  public boolean isFinished() {
    return Robot.cargoEjector.hasCargo();

  }

  @Override
  public void fin() {
    Robot.cargoIntake.setRollersState(IntakeRollersState.STOPPED);
    Robot.cargoIntake.setPrecntage(0.0);
  }

  @Override
  public void interr() {
    this.fin();
  }
}
