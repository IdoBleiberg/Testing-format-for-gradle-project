package com.team3316.robot.commands.cargoEjector;

import com.team3316.kit.DBugLogger;
import com.team3316.kit.commands.DBugCommand;
import com.team3316.robot.Robot;
import com.team3316.robot.subsystems.CargoEjector.EjectorRollerState;

/**
 * EjectorCollectCargo
 */
public class EjectorCollectCargo extends DBugCommand {
  public EjectorCollectCargo() {
    requires(Robot.cargoEjector);
  }

  @Override
  public void init() {
    DBugLogger.getInstance().info("Ejector started collecting cargo");
  }

  @Override
  public void execute() {
    Robot.cargoEjector.setRollerState(EjectorRollerState.IN);
  }

  @Override
  public boolean isFinished() {
    return Robot.cargoEjector.hasCargo();
  }

  @Override
  public void fin() {
    Robot.cargoEjector.setRollerState(EjectorRollerState.STOP);
  }

  @Override
  public void interr() {
    this.fin();
  }
}
