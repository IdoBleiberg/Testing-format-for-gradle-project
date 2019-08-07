package com.team3316.robot.subsystems;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;
import com.team3316.kit.DBugSubsystem;
import com.team3316.kit.config.Config;
import com.team3316.kit.config.ConfigException;
import com.team3316.robot.commands.TankDrive;

public class Drivetrain extends DBugSubsystem{
  TalonSRX talon1;
  TalonSRX talon2;

  public Drivetrain() throws ConfigException {
    this.talon1 = new TalonSRX((int) Config.getInstance().get("drivetrain.talon1"));
    this.talon2 = new TalonSRX((int) Config.getInstance().get("drivetrain.talon2"));
  }

  @Override
  public void initDefaultCommand() {
    setDefaultCommand(new TankDrive());
  }

  public void setMotors(double talon1, double talon2) {
    this.talon1.set(ControlMode.PercentOutput, talon1);
    this.talon2.set(ControlMode.PercentOutput, talon2);
  }

  public double getMotor1() {
    return this.talon1.getMotorOutputPercent();
  }

}