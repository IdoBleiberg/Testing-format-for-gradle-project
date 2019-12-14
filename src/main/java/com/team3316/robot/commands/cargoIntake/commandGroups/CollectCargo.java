package com.team3316.robot.commands.cargoIntake.commandGroups;

import com.team3316.kit.DBugLogger;
import com.team3316.kit.commands.DBugCommandGroup;
import com.team3316.robot.commands.cargoEjector.EjectorCollectCargo;
import com.team3316.robot.commands.cargoEjector.EjectorToCollect;
import com.team3316.robot.commands.cargoEjector.EjectorToEject;
import com.team3316.robot.commands.cargoIntake.CargoIntakeClose;
import com.team3316.robot.commands.cargoIntake.IntakeCollectCargo;

/**
 * CollectCargo
 */
public class CollectCargo extends DBugCommandGroup {
  public CollectCargo() {
    //addParallel(new SetPanelState(PanelMechanismState.CLOSED));
    addSequential(new EjectorToCollect());
    addParallel(new IntakeCollectCargo());
    addSequential(new EjectorCollectCargo());
    addSequential(new EjectorToEject(true));
    addParallel(new CargoIntakeClose());
  }

  @Override
  protected void init() {
    DBugLogger.getInstance().info("Started collecting cargo");
  }

}
