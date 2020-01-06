package com.team3316.robot.commands.examples;

import com.team3316.robot.commands.CommandGroupV2;
import com.team3316.robot.commands.cargoEjector.CargoEjectorSetState;
import com.team3316.robot.commands.cargoIntake.CargoIntakeSetState;
import com.team3316.robot.commands.elevator.ElevatorSetState;
import com.team3316.robot.subsystems.CargoEjector.EjectorArmState;
import com.team3316.robot.subsystems.CargoIntake.IntakeArmState;
import com.team3316.robot.subsystems.Elevator.ElevatorState;

/**
 * CollectCargo2
 */
public class CollectCargo2 extends CommandGroupV2 {
  public CollectCargo2() {
    add(() -> new ElevatorSetState(ElevatorState.BOTTOM), () -> new CargoIntakeSetState(IntakeArmState.OUT));
    add(() -> new CargoEjectorSetState(EjectorArmState.COLLECT));
  }
  
}