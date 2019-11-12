package com.team3316.robot.subsystems;

import static org.junit.Assert.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import com.team3316.robot.Robot;
import com.team3316.robot.subsystems.CargoEjector.EjectorArmState;
import com.team3316.robot.subsystems.CargoIntake.IntakeArmState;
import com.team3316.robot.utils.InvalidStateException;

import org.junit.jupiter.api.Test;

public class GeneralTest {

  public GeneralTest() {
    //Robot.drivetrain = new Drivetrain();
    Robot.cargoEjector = new CargoEjector();
    Robot.cargoIntake = new CargoIntake();
  }

  @Test
  public void testStates() {
    assertThrows(InvalidStateException.class, () -> {
        Robot.cargoIntake.setArmState(IntakeArmState.OUT);
        Robot.cargoEjector.setArmState(EjectorArmState.COLLECT);
        Robot.cargoIntake.setArmState(IntakeArmState.IN);
    });
  }
}
