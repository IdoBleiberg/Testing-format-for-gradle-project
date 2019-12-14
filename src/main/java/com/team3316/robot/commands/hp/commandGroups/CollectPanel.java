package com.team3316.robot.commands.hp.commandGroups;

import com.team3316.kit.DBugLogger;
import com.team3316.kit.commands.DBugCommandGroup;
import com.team3316.robot.commands.elevator.ElevatorCollectPanel;
import com.team3316.robot.commands.hp.SetPanelState;
import com.team3316.robot.subsystems.PanelMechanism.PanelMechanismState;

/**
 * CollectPanel
 */
public class CollectPanel extends DBugCommandGroup {

  public CollectPanel() {
    addSequential(new ElevatorCollectPanel());
    //addSequential(new SetPanelState(PanelMechanismState.CLOSED));
  }
  
  @Override
  protected void init() {
    DBugLogger.getInstance().info("Started collecting panel from LS");
  }

}