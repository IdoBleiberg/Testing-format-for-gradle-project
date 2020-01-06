package com.team3316.robot.commands.hp;

import com.team3316.kit.commands.DBugCommand;
import com.team3316.robot.Robot;
import com.team3316.robot.subsystems.PanelMechanism.PanelMechanismState;

/**
 * StayAtState
 */
public class StayAtState extends DBugCommand {

  private PanelMechanismState _wantedState;

  public StayAtState() {
    requires(Robot.panelMechanism);
  }

  @Override
  public void init() {

  }

  @Override
  public void execute() {
    this._wantedState = Robot.panelMechanism.getWantedState();
    if (this._wantedState == null) {
      return;
    } else {
      if (Robot.panelMechanism.getState() != this._wantedState) {
        Robot.panelMechanism.setHoldVoltage(this._wantedState);
      } else {
        Robot.panelMechanism.stopMovment();
      }
    }
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