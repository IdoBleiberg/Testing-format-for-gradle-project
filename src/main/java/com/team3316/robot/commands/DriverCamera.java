package com.team3316.robot.commands;
import com.team3316.kit.commands.DBugCommand;

import edu.wpi.cscore.UsbCamera;
import edu.wpi.first.cameraserver.CameraServer;


public class DriverCamera extends DBugCommand{
  static UsbCamera camera;

  public DriverCamera() {
    setRunWhenDisabled(true);
  }

  @Override
  protected void init() {
    CameraServer.getInstance().startAutomaticCapture("camera",0);
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