package com.team3316.robot.subsystems;

import static org.junit.Assert.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.ArrayList;

import com.team3316.kit.config.ConfigException;
import com.team3316.robot.Robot;
import com.team3316.robot.commands.CommandGroupV2;
import com.team3316.robot.commands.cargoEjector.EjectorCollectCargo;
import com.team3316.robot.commands.cargoEjector.EjectorToCollect;
import com.team3316.robot.commands.cargoEjector.EjectorToEject;
import com.team3316.robot.commands.cargoIntake.CargoIntakeClose;
import com.team3316.robot.commands.cargoIntake.IntakeCollectCargo;
import com.team3316.robot.subsystems.CargoEjector.EjectorArmState;
import com.team3316.robot.subsystems.CargoIntake.IntakeArmState;
import com.team3316.robot.subsystems.Elevator.ElevatorState;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.core.annotation.Order;

import edu.wpi.first.wpilibj.command.Command;

public class SuperSructureTest {

  @BeforeAll
  public static void startMethod() {
    Robot.cargoEjector = new CargoEjector();
    Robot.cargoIntake = new CargoIntake();
    Robot.elevator = new Elevator();
    Robot.superSructure = new SuperSructure();

    RobotState.COLLECTCARGO.setNeededCommands(() -> {
      class Anonymus extends CommandGroupV2 {
        public ArrayList<Command> setAndGet() {
          ArrayList<Command> needed = new ArrayList<Command>();
          try {
            if (Robot.cargoEjector.getArmState() != EjectorArmState.COLLECT) {
              needed.add(new EjectorToCollect());
              this.add(() -> new EjectorToCollect());
            }
            if (!Robot.cargoEjector.hasCargo()) {
              needed.add(new IntakeCollectCargo());
              needed.add(new EjectorCollectCargo());
              this.add(() -> new IntakeCollectCargo(), () -> new EjectorCollectCargo());
            }
            needed.add(new EjectorToEject(true));
            this.add(() -> new EjectorToEject(true));
            needed.add(new CargoIntakeClose());
            this.add(() -> new CargoIntakeClose());
          } catch (Exception e) { System.out.println(e); }
        return needed;
        }
      }
      return new Anonymus().setAndGet();
    });
  }

  @Test
  @Order(1)
  public void collectCargo() {
    ArrayList<Command> expected = new ArrayList<Command>();
    ArrayList<Command> actual = new ArrayList<Command>();

    expected.add(new EjectorToCollect());
    expected.add(new IntakeCollectCargo());
    expected.add(new EjectorCollectCargo());
    expected.add(new EjectorToEject(true));
    expected.add(new CargoIntakeClose());

    try {
      Robot.elevator.setState(ElevatorState.BOTTOM);
      Robot.cargoEjector.setArmState(EjectorArmState.EJECT);
      Robot.cargoIntake.setArmState(IntakeArmState.IN);
    } catch (Exception e) {
      assertTrue(false);
    }

    Robot.superSructure.setRobotState(RobotState.COLLECTCARGO);
    actual = Robot.superSructure.getNeededCommands();

    assertEquals(expected.toString(), actual.toString());
  }

  @Test
  @Order(2)
  public void collectCargo2() {
    ArrayList<Command> expected = new ArrayList<Command>();
    ArrayList<Command> actual = new ArrayList<Command>();

    expected.add(new IntakeCollectCargo());
    expected.add(new EjectorCollectCargo());
    expected.add(new EjectorToEject(true));
    expected.add(new CargoIntakeClose());

    try {
      Robot.elevator.setState(ElevatorState.BOTTOM);
      Robot.cargoIntake.setArmState(IntakeArmState.OUT);
      Robot.cargoEjector.setArmState(EjectorArmState.COLLECT);
    } catch (Exception e) {
      assertTrue(false);
    }

    Robot.superSructure.setRobotState(RobotState.COLLECTCARGO);
    actual = Robot.superSructure.getNeededCommands();

    assertEquals(expected.toString(), actual.toString());
  }
} 