package com.team3316.robot.commands.hp.commandGroups;

import com.team3316.kit.DBugLogger;
import com.team3316.kit.commands.DBugCommandGroup;
import com.team3316.robot.commands.cargoEjector.EjectorStartingConfig;
import com.team3316.robot.commands.elevator.ElevatorCollectPanel;
import com.team3316.robot.commands.hp.SetPanelState;
import com.team3316.robot.subsystems.PanelMechanism.PanelMechanismState;

/**
 * PanelStartingConfig
 */
public class GameStartV2 extends DBugCommandGroup {

  public GameStartV2() {
    addSequential(new EjectorStartingConfig());
    addSequential(new ElevatorCollectPanel(30.0f)); 
    addSequential(new SetPanelState(PanelMechanismState.OPEN));
    addSequential(new ElevatorCollectPanel(30.0f));
  }
  
  @Override
  protected void init() {
    DBugLogger.getInstance().info("Started collecting panel from LS");
  }

}