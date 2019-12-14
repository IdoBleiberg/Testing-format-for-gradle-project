package com.team3316.robot.commands.cargoEjector;

import com.team3316.kit.DBugLogger;
import com.team3316.kit.commands.DBugCommand;
import com.team3316.robot.Robot;

/**
 * EjectorToEject
 */
public class EjectorStartingConfig extends DBugCommand {
  
  private long _counterStarted = 0l, _duration = 500l;

  @Override
  protected void init() {
    this._counterStarted = System.currentTimeMillis();
    DBugLogger.getInstance().info("Started Ejector Strating config");
  }

  @Override
  protected void execute() {
    //Robot.cargoEjector.setArmVoltage(0.5);
  }

  @Override
  protected boolean isFinished() {
    long timeDelta = System.currentTimeMillis() - this._counterStarted;
    return timeDelta > this._duration;
  }

  @Override
  protected void fin() {

  }

  @Override
  protected void interr() {

  }
}
