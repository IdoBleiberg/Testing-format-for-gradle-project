package com.team3316.robot.commands;

import com.team3316.kit.commands.DBugCommand;

import edu.wpi.first.wpilibj.command.Command;

/**
 * DBugToggleCommand
 */
public class DBugToggleCommand extends DBugCommand {
  private Command _command1, _command2;
  private static boolean _toogle = true;

  public DBugToggleCommand (Command command1, Command command2) {
    this._command1 = command1;
    this._command2 = command2;
  }

  @Override
  public void init() {
    if (DBugToggleCommand._toogle) {
      this._command1.cancel();
      this._command2.start();
      DBugToggleCommand._toogle = false;
    } else {
      this._command2.cancel();
      this._command1.start();
      DBugToggleCommand._toogle = true;
    }
  }

  @Override
  public void execute() {

  }

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