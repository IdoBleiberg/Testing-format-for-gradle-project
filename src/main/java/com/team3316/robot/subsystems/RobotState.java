package com.team3316.robot.subsystems;

import java.util.ArrayList;

import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.command.InstantCommand;

/*
* _startingCommand:
*   is the thread that should start the command group to change the state of the robot.
^ _runningCommands:
*   has a function get() which needs to return the needed command/commandGroups in order to change the robotState to the wanted one. 
*/
public enum RobotState {
  COLLECTCARGO, COLLECTHP, Intermediet;

  private InstantCommand _startingCommand;
  private NeededCommands _runningCommands;

  public synchronized InstantCommand getStartingCommand() {
    return this._startingCommand;
  }

  public void setStartingCommand(InstantCommand cmd) {
    this._startingCommand = cmd;
  }

  public ArrayList<Command> getNeededComamnds() {
    return this._runningCommands.get();
  }

  public void setNeededCommands(NeededCommands cmd) {
    this._runningCommands = cmd;
  }

}