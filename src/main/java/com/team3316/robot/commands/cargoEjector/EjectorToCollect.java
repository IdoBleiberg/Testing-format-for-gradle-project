package com.team3316.robot.commands.cargoEjector;

import com.team3316.kit.DBugLogger;
import com.team3316.kit.commands.DBugCommandGroup;
import com.team3316.robot.Robot;
import com.team3316.robot.commands.cargoIntake.CargoIntakeOpen;
import com.team3316.robot.commands.elevator.ElevatorSetState;
import com.team3316.robot.subsystems.CargoEjector.EjectorArmState;
import com.team3316.robot.subsystems.Elevator.ElevatorState;

/**
 * EjectorToCollect
 */
public class EjectorToCollect extends DBugCommandGroup {
  public EjectorToCollect() {
    addSequential(new ElevatorSetState(ElevatorState.BOTTOM));
    addSequential(new CargoIntakeOpen());
    addSequential(new EjectorArmToCollect());
  }
  @Override
  protected void init() {
    EjectorArmState state = Robot.cargoEjector.getArmState();
    DBugLogger.getInstance().info("Moving ejector from " + state.toString() + " to COLLECT");
  }
}
