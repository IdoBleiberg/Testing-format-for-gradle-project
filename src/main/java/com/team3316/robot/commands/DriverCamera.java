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
  public void init() {
    CameraServer.getInstance().startAutomaticCapture("camera",0);
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