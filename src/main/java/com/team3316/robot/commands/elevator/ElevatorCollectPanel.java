package com.team3316.robot.commands.elevator;

import com.team3316.kit.commands.DBugCommand;
import com.team3316.robot.Robot;

/**
 * ElevatorCollectPanel
 */
public class ElevatorCollectPanel extends DBugCommand {

  private long _initTime;
  private float addedPos = 0;

  public ElevatorCollectPanel() {
    requires(Robot.elevator);
  }  

  public ElevatorCollectPanel(float addedPos) {
    requires(Robot.elevator);
    this.addedPos = addedPos;
  }  

  @Override
  protected void init() {
    if (addedPos == 0) Robot.elevator.collectPanel();
    else Robot.elevator.collectPanel(addedPos);
    this._initTime = System.currentTimeMillis();
  }

  @Override
  protected void execute() {

  }

  @Override
  protected boolean isFinished() {
    long currentTime = System.currentTimeMillis();
    return currentTime - this._initTime >= 150l;
  }

  @Override
  protected void fin() {

  }

  @Override
  protected void interr() {

  }

  
}