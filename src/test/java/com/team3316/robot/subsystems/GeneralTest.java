package com.team3316.robot.subsystems;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import com.team3316.robot.Robot;
import com.team3316.robot.subsystems.CargoEjector.EjectorArmState;
import com.team3316.robot.subsystems.CargoIntake.IntakeArmState;
import com.team3316.robot.subsystems.Elevator.ElevatorState;
import com.team3316.robot.utils.InvalidStateException;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

public class GeneralTest {

  @BeforeAll
  public static void startMethod() {
    Robot.cargoEjector = new CargoEjector();
    Robot.cargoIntake = new CargoIntake();
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
  public void EjectorIntake2() {
    assertThrows(InvalidStateException.class, () -> {
      Robot.elevator.setState(ElevatorState.BOTTOM);
      Robot.cargoEjector.setArmState(EjectorArmState.EJECT);
      Robot.cargoIntake.setArmState(IntakeArmState.IN);
      Robot.cargoEjector.setArmState(EjectorArmState.COLLECT);
    });
  }

  @Test
  public void EjectorElevator() {
    try {
      Robot.elevator.setState(ElevatorState.BOTTOM);
      Robot.cargoEjector.setArmState(EjectorArmState.EJECT);
      Robot.cargoIntake.setArmState(IntakeArmState.IN);
      Robot.cargoEjector.setArmState(EjectorArmState.INSTALL_LVL3);
    } catch (final InvalidStateException e) {
      assertTrue(false);
    }
    assertTrue(true);
  }

  @Test
  public void EjectorElevator2() {
    try {
      Robot.elevator.setState(ElevatorState.BOTTOM);
      Robot.cargoEjector.setArmState(EjectorArmState.EJECT);
      Robot.cargoIntake.setArmState(IntakeArmState.IN);
      Robot.elevator.setState(ElevatorState.LVL3_CARGO);
      Robot.cargoEjector.setArmState(EjectorArmState.INSTALL_LVL3);
    } catch (final InvalidStateException e) {
      assertTrue(false);
    }
    assertTrue(true);
  }

  @Test
  public void EjectorElevatorGround() {
    assertThrows(InvalidStateException.class, () -> {
      Robot.elevator.setState(ElevatorState.BOTTOM);
      Robot.cargoIntake.setArmState(IntakeArmState.OUT);
      Robot.cargoEjector.setArmState(EjectorArmState.COLLECT);
      Robot.elevator.setState(ElevatorState.LVL2_HP);
    });
  }
}
