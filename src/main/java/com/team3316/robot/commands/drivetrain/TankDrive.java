package com.team3316.robot.commands.drivetrain;

import com.team3316.robot.Robot;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

import com.team3316.kit.DBugLogger;
import com.team3316.kit.commands.DBugCommand;

/**
 * TankDrive Command - used to move the robot with joysticks
 */
public class TankDrive extends DBugCommand {
  public TankDrive() {
    requires(Robot.drivetrain);
    setName("TankDrive");
  }

  @Override
  protected void init() {
    DBugLogger.getInstance().info("Started driving with joysticks");
  }

  /**
   * Sets motors value to Joysticks' Y axis
   */
  @Override
  protected void execute() {
    double leftY = -Robot.joysticks.getRightY();
    double rightY = -Robot.joysticks.getLeftY();
    SmartDashboard.putNumber("left joystick", rightY);
    SmartDashboard.putNumber("right joystick", leftY);
    Robot.drivetrain.setMotors(leftY, rightY);
  }

  @Override
  protected boolean isFinished() {
    return false;
  }

  @Override
  protected void fin() {
    Robot.drivetrain.setMotors(0, 0);
  }

  @Override
  protected void interr() {
    this.fin();
  }
}
