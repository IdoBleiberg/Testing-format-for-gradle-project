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
  public void init() {
    this._initTime = System.currentTimeMillis();
    Robot.panelMechanism.setBrakes(true);
  }

  @Override
  public void execute() {
    Robot.panelMechanism.setVoltageUnsafe(this._voltage);
  }

  @Override
  public boolean isFinished() {
    return System.currentTimeMillis() - this._initTime >= this._time;
  }

  @Override
  public void fin() {
    Robot.panelMechanism.stopMovment();
    Robot.panelMechanism.setBrakes(true);
  }

  @Override
  public void interr() {

  }

  
}