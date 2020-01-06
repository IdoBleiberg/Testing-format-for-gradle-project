package com.team3316.robot.commands.cargoIntake;

import com.team3316.kit.commands.DBugCommand;
import com.team3316.robot.Robot;

/**
 * CargoIntakeDefault
 */
public class CargoIntakeDefault extends DBugCommand {

  public CargoIntakeDefault() {
    requires(Robot.cargoIntake);
  }

  @Override
  public void init() {

  }

  @Override
  public void execute() {
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
  public boolean isFinished() {
    return false;
  }

  @Override
  public void fin() {

  }

  @Override
  public void interr() {

  }
  
}