package com.team3316.robot.commands;

import com.team3316.kit.commands.DBugCommandGroup;
import com.team3316.robot.commands.cargoEjector.EjectorToEject;
import com.team3316.robot.commands.elevator.ElevatorSetState;
import com.team3316.robot.commands.hp.SetPanelState;
import com.team3316.robot.commands.hp.commandGroups.CollectPanel;
import com.team3316.robot.subsystems.Elevator.ElevatorState;
import com.team3316.robot.subsystems.PanelMechanism.PanelMechanismState;

import edu.wpi.first.wpilibj.command.WaitCommand;

/**
 * GameStart
 */
public class GameStart extends DBugCommandGroup {

  public GameStart() {
    addSequential(new ElevatorSetState(ElevatorState.SC));
    addSequential(new WaitCommand(0.3));
    addSequential(new SetPanelState(PanelMechanismState.OPEN));
    addParallel(new EjectorToEject(false));
    //addParallel(new ElevatorSetState(ElevatorState.AFTER_SC));
  }
  
}