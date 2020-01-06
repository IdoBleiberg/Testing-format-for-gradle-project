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
  public void init() {
    if (addedPos == 0) Robot.elevator.collectPanel();
    else Robot.elevator.collectPanel(addedPos);
    this._initTime = System.currentTimeMillis();
  }

  @Override
  public void execute() {

  }

  @Override
  public boolean isFinished() {
    long currentTime = System.currentTimeMillis();
    return currentTime - this._initTime >= 150l;
  }

  @Override
  public void fin() {

  }

  @Override
  public void interr() {

  }

  
}