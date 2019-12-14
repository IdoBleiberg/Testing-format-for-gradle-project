package com.team3316.robot.commands.cargoEjector.commandgroups;

import com.team3316.kit.DBugLogger;
import com.team3316.kit.commands.DBugCommandGroup;
import com.team3316.kit.config.ConfigException;
import com.team3316.robot.Robot;
import com.team3316.robot.commands.cargoEjector.EjectorEjectCargo;
import com.team3316.robot.commands.cargoEjector.EjectorToEject;
import com.team3316.robot.commands.cargoIntake.CargoIntakeOpen;
import com.team3316.robot.commands.elevator.ElevatorSetState;
import com.team3316.robot.subsystems.CargoEjector.EjectorArmState;
import com.team3316.robot.subsystems.Elevator.ElevatorState;
import com.team3316.robot.utils.InvalidStateException;

/**
 * EjectCargo
 */
public class EjectCargo extends DBugCommandGroup {
  public EjectCargo(ElevatorState target) throws ConfigException, InvalidStateException {

    if (Robot.cargoEjector.getArmState() != EjectorArmState.EJECT && Robot.cargoEjector.getArmState() != EjectorArmState.INSTALL_LVL3) {
      addParallel(new CargoIntakeOpen());
      addSequential(new ElevatorSetState(ElevatorState.BOTTOM));
      addSequential(new EjectorToEject(true));
    }
    
    addSequential(new ElevatorSetState(target));

    addSequential(new EjectorEjectCargo());
  }
  @Override
  protected void init() {
    DBugLogger.getInstance().info("Ejecting cargo");
  }
}
