package com.team3316.robot.commands.cargoIntake;

import com.team3316.kit.commands.DBugCommand;
import com.team3316.robot.Robot;
import com.team3316.robot.subsystems.CargoIntake.IntakeArmState;

/**
 * CargoIntakeDefault
 */
public class CargoIntakeDefault extends DBugCommand {

  public CargoIntakeDefault() {
    requires(Robot.cargoIntake);
  }

  @Override
  protected void init() {

  }

  @Override
  protected void execute() {
    /*
    if (Robot.cargoIntake.getWantedArmState() != IntakeArmState.INTERMEDIATE ) {
      if (Robot.cargoIntake.getWantedArmState() == IntakeArmState.IN) {
        if (!Robot.cargoIntake.getInSwitch()) {
          Robot.cargoIntake.setEncoderDistance(IntakeArmState.OUT.getPosition());
        }
      } else {
        if (!Robot.cargoIntake.getOutSwitch()) {
          Robot.cargoIntake.setEncoderDistance(IntakeArmState.IN.getPosition());
        }
      }
    }
    */
  }

  @Override
  protected boolean isFinished() {
    return false;
  }

  @Override
  protected void fin() {

  }

  @Override
  protected void interr() {

  }
  
}