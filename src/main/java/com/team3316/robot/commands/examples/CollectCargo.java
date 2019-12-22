package com.team3316.robot.commands.examples;

import com.team3316.robot.commands.CommandGroupV2;
import com.team3316.robot.commands.cargoEjector.EjectorCollectCargo;
import com.team3316.robot.commands.cargoIntake.CargoIntakeSetRollers;
import com.team3316.robot.subsystems.RobotState;
import com.team3316.robot.subsystems.CargoIntake.IntakeRollersState;

/**
 * CollectCargo
 */
public class CollectCargo extends CommandGroupV2 {

  public CollectCargo() {
    add(() -> new MoveToState(RobotState.PRE_COLLECTCARGO));
    add(() -> new CargoIntakeSetRollers(IntakeRollersState.IN), () -> new EjectorCollectCargo());
    add(() -> new MoveToState(RobotState.AFTER_COLLECTCARGO));
  }
  
}