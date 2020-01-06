package com.team3316.robot.commands;

import com.team3316.kit.commands.DBugCommand;

import edu.wpi.first.wpilibj.command.Command;



/**
 * DBugToggleCommand
 */
public class DBugToggleOneCommand extends DBugCommand{

  private Command _cmd1;
  private static boolean _toogle = false;

  public DBugToggleOneCommand(Command cmd1) {
    this._cmd1 = cmd1;
  }

  @Override
  public void init() {
    if (DBugToggleOneCommand._toogle) {
      this._cmd1.cancel();
      DBugToggleOneCommand._toogle = false;
    } else {
      this._cmd1.start();
      DBugToggleOneCommand._toogle = true;
    }
  }

  @Override
  public void execute() {}

  @Override
  public boolean isFinished() {
    return true;
  }

  @Override
  public void fin() {

  }

  @Override
  public void interr() {

  }

  
}