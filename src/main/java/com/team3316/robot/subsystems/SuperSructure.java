package com.team3316.robot.subsystems;

import java.util.ArrayList;

import com.team3316.kit.DBugSubsystem;

import edu.wpi.first.wpilibj.command.Command;

public class SuperSructure extends DBugSubsystem {

  private RobotState _wantedRobotState;

  public SuperSructure() { }

  public synchronized void setRobotState(RobotState state) {
    state.getNeededComamnds().getAndSet();
    this._wantedRobotState = state;
  }

  public synchronized RobotState getWantedRobotState() {
    return this._wantedRobotState;
  }

  public synchronized ArrayList<Command> getNeededCommands() {
    return this._wantedRobotState.getNeededComamnds().getAndSet();
  }

  @Override
  public void initDefaultCommand() {
    // TODO Auto-generated method stub

  }

  @Override
  public void displayTestData() {
    // TODO Auto-generated method stub

  }

  @Override
  public void displayMatchData() {
    // TODO Auto-generated method stub

  }

  @Override
  public void displayCommands() {
    // TODO Auto-generated method stub

  }
}