package com.team3316.robot.commands;

import com.team3316.kit.commands.DBugCommandGroup;
import com.team3316.robot.commands.cargoEjector.EjectorToCollect;
import com.team3316.robot.commands.cargoEjector.EjectorToEject;
import com.team3316.robot.commands.cargoIntake.CargoIntakeClose;
import com.team3316.robot.commands.cargoIntake.CargoIntakeOpen;

/**
 * GoToDefence
 */
public class GoToDefence extends DBugCommandGroup {

  public GoToDefence() {
    addSequential(new CargoIntakeOpen());
    addSequential(new EjectorToCollect());
    addSequential(new CargoIntakeClose());
  }
  
}