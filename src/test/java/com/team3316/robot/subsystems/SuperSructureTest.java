package com.team3316.robot.subsystems;

import static org.junit.Assert.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.ArrayList;

import com.team3316.robot.Robot;
import com.team3316.robot.commands.cargoEjector.CargoEjectorSetState;
import com.team3316.robot.commands.cargoIntake.CargoIntakeSetState;
import com.team3316.robot.commands.elevator.ElevatorSetState;
import com.team3316.robot.subsystems.CargoEjector.EjectorArmState;
import com.team3316.robot.subsystems.CargoIntake.IntakeArmState;
import com.team3316.robot.subsystems.Elevator.ElevatorState;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import edu.wpi.first.wpilibj.command.Command;

public class SuperSructureTest {

  @BeforeAll
  public static void startMethod() {
    Robot.cargoEjector = new CargoEjector();
    Robot.cargoIntake = new CargoIntake();
    Robot.elevator = new Elevator();
    Robot.superSructure = new SuperSructure();
    RobotState.PRE_COLLECTCARGO.setStates(EjectorArmState.COLLECT, IntakeArmState.OUT, ElevatorState.BOTTOM);
    RobotState.AFTER_COLLECTCARGO.setStates(EjectorArmState.EJECT, IntakeArmState.IN, ElevatorState.BOTTOM);
    RobotState.CARGO_INSTALL_LVL3.setStates(EjectorArmState.INSTALL_LVL3, IntakeArmState.IN, ElevatorState.LVL3_CARGO);
  }

  @Test
  public void Lvl3CargoInstall() {
    ArrayList<Command> expected = new ArrayList<Command>();
    ArrayList<Command> actual = new ArrayList<Command>();

    expected.add(new ElevatorSetState(ElevatorState.LVL3_CARGO));
    expected.add(new CargoEjectorSetState(EjectorArmState.INSTALL_LVL3));

    try {
      Robot.elevator.setState(ElevatorState.BOTTOM);
      Robot.cargoEjector.setArmState(EjectorArmState.EJECT);
      Robot.cargoIntake.setArmState(IntakeArmState.IN);
    } catch (Exception e) {
      assertTrue(false);
    }
    Robot.superSructure.setRobotState(RobotState.CARGO_INSTALL_LVL3);
    actual = Robot.superSructure.getNeededCommands();

    assertEquals(expected.toString(), actual.toString());
  }

  @Test
  public void Lvl3CargoInstall2() {
    ArrayList<Command> expected = new ArrayList<Command>();
    ArrayList<Command> actual = new ArrayList<Command>();


    expected.add(new CargoEjectorSetState(EjectorArmState.INSTALL_LVL3));

    try {
      Robot.elevator.setState(ElevatorState.BOTTOM);
      Robot.cargoEjector.setArmState(EjectorArmState.EJECT);
      Robot.cargoIntake.setArmState(IntakeArmState.IN);
      Robot.elevator.setState(ElevatorState.LVL3_CARGO);
    } catch (Exception e) {
      assertTrue(false);
    }
    Robot.superSructure.setRobotState(RobotState.CARGO_INSTALL_LVL3);
    actual = Robot.superSructure.getNeededCommands();

    assertEquals(expected.toString(), actual.toString());
  }

  @Test
  public void Lvl3CargoInstall3() {
    ArrayList<Command> expected = new ArrayList<Command>();
    ArrayList<Command> actual = new ArrayList<Command>();


    expected.add(new CargoEjectorSetState(EjectorArmState.EJECT));
    expected.add(new ElevatorSetState(ElevatorState.LVL3_CARGO));
    expected.add(new CargoIntakeSetState(IntakeArmState.IN));
    expected.add(new CargoEjectorSetState(EjectorArmState.INSTALL_LVL3));

    try {
      Robot.elevator.setState(ElevatorState.BOTTOM);
      Robot.cargoEjector.setArmState(EjectorArmState.EJECT);
      Robot.cargoIntake.setArmState(IntakeArmState.OUT);
      Robot.cargoEjector.setArmState(EjectorArmState.COLLECT);
    } catch (Exception e) {
      assertTrue(false);
    }
    Robot.superSructure.setRobotState(RobotState.CARGO_INSTALL_LVL3);
    actual = Robot.superSructure.getNeededCommands();

    assertEquals(expected.toString(), actual.toString());
  }

  @Test
  public void preCollectCargo() {
    ArrayList<Command> expected = new ArrayList<Command>();
    ArrayList<Command> actual = new ArrayList<Command>();

    expected.add(new CargoIntakeSetState(IntakeArmState.OUT));
    expected.add(new CargoEjectorSetState(EjectorArmState.COLLECT));

    try {
      Robot.elevator.setState(ElevatorState.BOTTOM);
      Robot.cargoEjector.setArmState(EjectorArmState.EJECT);
      Robot.cargoIntake.setArmState(IntakeArmState.IN);
    } catch (Exception e) {
      assertTrue(false);
    }
    Robot.superSructure.setRobotState(RobotState.PRE_COLLECTCARGO);
    actual = Robot.superSructure.getNeededCommands();

    assertEquals(expected.toString(), actual.toString());
  }

  @Test
  public void preCollectCargo2() {
    ArrayList<Command> expected = new ArrayList<Command>();
    ArrayList<Command> actual = new ArrayList<Command>();

    try {
      Robot.elevator.setState(ElevatorState.BOTTOM);
      Robot.cargoIntake.setArmState(IntakeArmState.OUT);
      Robot.cargoEjector.setArmState(EjectorArmState.COLLECT);
    } catch (Exception e) {
      assertTrue(false);
    }

    Robot.superSructure.setRobotState(RobotState.PRE_COLLECTCARGO);
    actual = Robot.superSructure.getNeededCommands();

    assertEquals(expected.toString(), actual.toString());
  }

  @Test
  public void preCollectCargo3() {
    ArrayList<Command> expected = new ArrayList<Command>();
    ArrayList<Command> actual = new ArrayList<Command>();

    expected.add(new CargoEjectorSetState(EjectorArmState.EJECT));
    expected.add(new ElevatorSetState(ElevatorState.BOTTOM));
    expected.add(new CargoEjectorSetState(EjectorArmState.COLLECT));

    try {
      Robot.elevator.setState(ElevatorState.BOTTOM);
      Robot.cargoIntake.setArmState(IntakeArmState.OUT);
      Robot.cargoEjector.setArmState(EjectorArmState.EJECT);
      Robot.elevator.setState(ElevatorState.LVL3_CARGO);
      Robot.cargoEjector.setArmState(EjectorArmState.INSTALL_LVL3);
    } catch (Exception e) {
      assertTrue(false);
    }

    Robot.superSructure.setRobotState(RobotState.PRE_COLLECTCARGO);
    actual = Robot.superSructure.getNeededCommands();

    assertEquals(expected.toString(), actual.toString());
  }

  @Test
  public void afterCollectCargo() {
    ArrayList<Command> expected = new ArrayList<Command>();
    ArrayList<Command> actual = new ArrayList<Command>();

    expected.add(new CargoEjectorSetState(EjectorArmState.EJECT));
    expected.add(new CargoIntakeSetState(IntakeArmState.IN));

    try {
      Robot.elevator.setState(ElevatorState.BOTTOM);
      Robot.cargoIntake.setArmState(IntakeArmState.OUT);
      Robot.cargoEjector.setArmState(EjectorArmState.COLLECT);
    } catch (Exception e) {
      assertTrue(false);
    }

    Robot.superSructure.setRobotState(RobotState.AFTER_COLLECTCARGO);
    actual = Robot.superSructure.getNeededCommands();

    assertEquals(expected.toString(), actual.toString());
  }

  @Test
  public void afterCollectCargo2() {
    ArrayList<Command> expected = new ArrayList<Command>();
    ArrayList<Command> actual = new ArrayList<Command>();

    expected.add(new CargoIntakeSetState(IntakeArmState.IN));

    try {
      Robot.elevator.setState(ElevatorState.BOTTOM);
      Robot.cargoIntake.setArmState(IntakeArmState.OUT);
      Robot.cargoEjector.setArmState(EjectorArmState.EJECT);
    } catch (Exception e) {
      assertTrue(false);
    }

    Robot.superSructure.setRobotState(RobotState.AFTER_COLLECTCARGO);
    actual = Robot.superSructure.getNeededCommands();

    assertEquals(expected.toString(), actual.toString());
  }

  @Test
  public void afterCollectCargo3() {
    ArrayList<Command> expected = new ArrayList<Command>();
    ArrayList<Command> actual = new ArrayList<Command>();

    expected.add(new ElevatorSetState(ElevatorState.BOTTOM));
    expected.add(new CargoIntakeSetState(IntakeArmState.IN));

    try {
      Robot.cargoIntake.setArmState(IntakeArmState.OUT);
      Robot.cargoEjector.setArmState(EjectorArmState.EJECT);
      Robot.elevator.setState(ElevatorState.LVL1_CARGO);
    } catch (Exception e) {
      assertTrue(false);
    }

    Robot.superSructure.setRobotState(RobotState.AFTER_COLLECTCARGO);
    actual = Robot.superSructure.getNeededCommands();

    assertEquals(expected.toString(), actual.toString());
  }

  @Test
  public void afterCollectCargo4() {
    ArrayList<Command> expected = new ArrayList<Command>();
    ArrayList<Command> actual = new ArrayList<Command>();

    expected.add(new ElevatorSetState(ElevatorState.BOTTOM));

    try {
      Robot.cargoEjector.setArmState(EjectorArmState.EJECT);
      Robot.cargoIntake.setArmState(IntakeArmState.IN);
      Robot.elevator.setState(ElevatorState.LVL1_CARGO);
    } catch (Exception e) {
      assertTrue(false);
    }

    Robot.superSructure.setRobotState(RobotState.AFTER_COLLECTCARGO);
    actual = Robot.superSructure.getNeededCommands();

    assertEquals(expected.toString(), actual.toString());
  }

  @Test
  public void afterCollectCargo5() {
    ArrayList<Command> expected = new ArrayList<Command>();
    ArrayList<Command> actual = new ArrayList<Command>();

    expected.add(new CargoEjectorSetState(EjectorArmState.EJECT));
    expected.add(new ElevatorSetState(ElevatorState.BOTTOM));

    try {
      Robot.elevator.setState(ElevatorState.BOTTOM);
      Robot.cargoEjector.setArmState(EjectorArmState.EJECT);
      Robot.cargoIntake.setArmState(IntakeArmState.IN);
      Robot.elevator.setState(ElevatorState.LVL3_CARGO);
      Robot.cargoEjector.setArmState(EjectorArmState.INSTALL_LVL3);
    } catch (Exception e) {
      assertTrue(false);
    }

    Robot.superSructure.setRobotState(RobotState.AFTER_COLLECTCARGO);
    actual = Robot.superSructure.getNeededCommands();

    assertEquals(expected.toString(), actual.toString());
  }

} 