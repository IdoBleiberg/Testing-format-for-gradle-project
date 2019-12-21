package com.team3316.robot.commands.hp;

import com.team3316.kit.DBugLogger;
import com.team3316.kit.commands.DBugCommandGroup;
import com.team3316.robot.commands.elevator.MoveElevatorToInstallPanel;
import com.team3316.robot.subsystems.Elevator.ElevatorState;
import com.team3316.robot.subsystems.PanelMechanism.PanelMechanismState;

/**
 * CollectCargo
 */
public class InstallPanel extends DBugCommandGroup {
  public InstallPanel(ElevatorState target) {
    addSequential(new MoveElevatorToInstallPanel());
    addSequential(new SetPanelState(PanelMechanismState.CLOSED));
  }

  @Override
  protected void init() {
    DBugLogger.getInstance().info("Installing Panel");
  }

}
