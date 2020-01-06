package com.team3316.robot.commands.hp;

import com.team3316.kit.DBugLogger;
import com.team3316.kit.commands.DBugCommand;
import com.team3316.robot.Robot;
import com.team3316.robot.subsystems.PanelMechanism.PanelMechanismState;
import com.team3316.robot.utils.InvalidStateException;

/**
 * SetPanelState
 */
public class SetPanelState extends DBugCommand {
  private PanelMechanismState _wantedState;
  private boolean _shouldRun = true;

  public SetPanelState(PanelMechanismState wantedState) {
    requires(Robot.panelMechanism);
    this._wantedState = wantedState;
  }

  @Override
  public void init() {
    DBugLogger.getInstance()
        .info("Moving PanelMechanism " + Robot.panelMechanism.getState().toString() + " -> " + _wantedState.toString());
    Robot.panelMechanism.stopMovment();
    Robot.panelMechanism.setBrakes(false);
  }

  @Override
  public void execute() {
    try {
      this._shouldRun = true;
      Robot.panelMechanism.setArmState(this._wantedState);
    } catch (InvalidStateException e) {
      DBugLogger.getInstance().severe(e);
      this._shouldRun = false;
	  } catch (Exception e) {
      DBugLogger.getInstance().severe(e);
      this._shouldRun = false;
    }
  }

  @Override
  public boolean isFinished() {
    return Robot.panelMechanism.getState() == this._wantedState || !this._shouldRun;
  }

  @Override
  public void fin() {
    Robot.panelMechanism.stopMovment();
    //new setVoltageForTime(500l, 0.2 * Math.signum(this._wantedState.getVoltage()) * 0.2).start();
  }

  @Override
  public void interr() {
    this.fin();
  }

}
