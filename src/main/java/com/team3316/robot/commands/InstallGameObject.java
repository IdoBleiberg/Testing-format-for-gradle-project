package com.team3316.robot.commands;

import com.team3316.kit.DBugLogger;
import com.team3316.kit.commands.DBugCommand;
import com.team3316.robot.Robot;
import com.team3316.robot.commands.cargoEjector.commandgroups.EjectCargo;
import com.team3316.robot.commands.hp.InstallPanel;
import com.team3316.robot.subsystems.Elevator.ElevatorState;

public class InstallGameObject extends DBugCommand {
  @Override
  protected void init() {
    try {
      ElevatorState currentState = Robot.elevator.getState();
      if (Robot.cargoEjector.hasCargo()) {
        (new EjectCargo(currentState)).start();
        DBugLogger.getInstance().info("Installing game object Cargo");
      } else {
        (new InstallPanel(currentState)).start();//Robot.elevator.getClosestPanelState())).start();
        DBugLogger.getInstance().info("Installing game object Panel");
      }
    } catch (Exception e) {
      DBugLogger.getInstance().severe(e);
      cancel();
    }
  }

  @Override
  protected void execute() {
    // Nothin'
  }

  @Override
  protected boolean isFinished() {
    return true;
  }

  @Override
  protected void fin() {
    // Nothin'
  }

  @Override
  protected void interr() {
    // Nothin'
  }
}
