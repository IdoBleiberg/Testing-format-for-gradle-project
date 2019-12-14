package com.team3316.robot.subsystems.superSructure;

import com.team3316.kit.commands.DBugCommandGroup;

public enum RobotState {
  COLLECTCARGO, COLLECTHP, Intermediet;
    
  private Thread _thread;

  public Thread geThread() {
    return this._thread;
  }

  public void setThread(Thread cmd) {
     this._thread = cmd;
  }

}