package com.team3316.robot.subsystems;

import static org.junit.Assert.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.team3316.kit.mocks.SparkMaxTesting;
import com.team3316.kit.motors.DBugSparkMax;
import com.team3316.robot.Robot;
import com.team3316.robot.subsystems.CargoEjector.EjectorArmState;
import com.team3316.robot.subsystems.CargoIntake.IntakeArmState;
import com.team3316.robot.subsystems.Elevator.ElevatorState;
import com.team3316.robot.utils.InvalidStateException;

import org.junit.jupiter.api.Test;

public class GeneralTest {

  SparkMaxTesting _spark;

  public GeneralTest() {
    // Robot.drivetrain = new Drivetrain();
    Robot.cargoEjector = new CargoEjector();
    Robot.cargoIntake = new CargoIntake();
    Robot.elevator = new Elevator();

    this._spark = new SparkMaxTesting(1);
    this._spark.setDistancePerRevolution(1, 1);

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
  public void sparkMaxTest1() {
    this._spark.setDistance(50.0);
    assertEquals((int) 50,(int) this._spark.getDistance());
  }

  @Test
  public void sparkMaxTest2() {
    this._spark.set(ControlMode.Position, 50);
    assertEquals((int) 50,(int) this._spark.getDistance());
  }

  @Test
  public void sparkMaxTest3() {
    this._spark.set(ControlMode.PercentOutput, 0.5);
    assertEquals((int) 0.5,(int) this._spark.getVelocity());
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
    } catch (InvalidStateException e) {
      assertTrue(false);
    } finally {
      assertTrue(true);
    }
  }

  @Test
  public void EjectorElevator2() {
    try {
      Robot.elevator.setState(ElevatorState.BOTTOM);
      Robot.cargoEjector.setArmState(EjectorArmState.EJECT);
      Robot.cargoIntake.setArmState(IntakeArmState.IN);
      Robot.elevator.setState(ElevatorState.LVL3_CARGO);
      Robot.cargoEjector.setArmState(EjectorArmState.INSTALL_LVL3);
    } catch (InvalidStateException e) {
      assertTrue(false);
    } finally {
      assertTrue(true);
    }
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
