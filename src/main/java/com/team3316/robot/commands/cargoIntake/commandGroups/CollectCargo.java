package com.team3316.robot.commands.cargoIntake.commandGroups;

import com.team3316.kit.DBugLogger;
import com.team3316.kit.commands.DBugCommandGroup;
import com.team3316.robot.commands.cargoEjector.EjectorCollectCargo;
import com.team3316.robot.commands.cargoIntake.IntakeCollectCargo;

/**
 * CollectCargo
 */
public class CollectCargo extends DBugCommandGroup {
  public CollectCargo() {
    //addParallel(new SetPanelState(PanelMechanismState.CLOSED));
  }

  @Override
  public void init() {
    DBugLogger.getInstance().info("Started collecting cargo");
  }

}
