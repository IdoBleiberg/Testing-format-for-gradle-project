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
  protected void init() {

  }

  @Override
  protected void execute() {
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