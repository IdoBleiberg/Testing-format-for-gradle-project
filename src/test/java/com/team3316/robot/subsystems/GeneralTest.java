package com.team3316.robot.subsystems;

import static org.junit.Assert.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import com.team3316.robot.Robot;
import com.team3316.robot.subsystems.CargoEjector.EjectorArmState;
import com.team3316.robot.subsystems.CargoIntake.IntakeArmState;
import com.team3316.robot.subsystems.Elevator.ElevatorState;
import com.team3316.robot.utils.InvalidStateException;

import org.junit.jupiter.api.Test;

public class GeneralTest {

  public GeneralTest() {
    //Robot.drivetrain = new Drivetrain();
    Robot.cargoEjector = new CargoEjector();
    Robot.cargoIntake = new CargoIntake();
    Robot.panelMechanism = new PanelMechanism();
    Robot.elevator = new Elevator();
  }

  @Test
  public void EjectorIntake() {
    assertThrows(InvalidStateException.class, () -> {
        Robot.cargoIntake.setArmState(IntakeArmState.OUT);
        Robot.cargoEjector.setArmState(EjectorArmState.COLLECT);
        Robot.cargoIntake.setArmState(IntakeArmState.IN);
    });
  }

  @Test
  public void EjectorElevatorGround() {
    assertThrows(InvalidStateException.class, () -> {
      Robot.elevator.setState(ElevatorState.BOTTOM);
      Robot.cargoIntake.setArmState(IntakeArmState.OUT);
      Robot.cargoEjector.setArmState(EjectorArmState.COLLECT);
      Robot.elevator.setState(ElevatorState.LVL2_CARGO);
  });
  }

}
