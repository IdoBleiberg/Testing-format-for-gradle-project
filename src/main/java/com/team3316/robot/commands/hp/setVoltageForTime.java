package com.team3316.robot.commands.hp;

import com.team3316.kit.commands.DBugCommand;
import com.team3316.robot.Robot;

/**
 * setVoltageForTime
 */
public class setVoltageForTime extends DBugCommand {

  private long _initTime, _time;
  private double _voltage;

  public setVoltageForTime(long time, double voltage) {
    requires(Robot.panelMechanism);
    this._time = time;
    this._voltage = voltage;
  }

  @Override
  protected void init() {
    this._initTime = System.currentTimeMillis();
    Robot.panelMechanism.setBrakes(true);
  }

  @Override
  protected void execute() {
    Robot.panelMechanism.setVoltageUnsafe(this._voltage);
  }

  @Override
  protected boolean isFinished() {
    return System.currentTimeMillis() - this._initTime >= this._time;
  }

  @Override
  protected void fin() {
    Robot.panelMechanism.stopMovment();
    Robot.panelMechanism.setBrakes(true);
  }

  @Override
  protected void interr() {

  }

  
}