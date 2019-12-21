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
  protected void init() {
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
  protected void execute() {

  }

  @Override
  protected boolean isFinished() {
    return true;
  }

  @Override
  protected void fin() {
  }

  @Override
  protected void interr() {

  }
}