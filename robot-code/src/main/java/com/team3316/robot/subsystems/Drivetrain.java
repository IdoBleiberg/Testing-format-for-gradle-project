package com.team3316.robot.subsystems;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.IMotorController;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;
import com.team3316.kit.DBugSubsystem;
import com.team3316.kit.config.Config;
import com.team3316.kit.config.ConfigException;
import com.team3316.robot.commands.TankDrive;
import com.team3316.robot.utils.TalonSRXTesting;
import com.team3316.robot.utils.Utils;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import edu.wpi.first.wpilibj.command.Subsystem;

public class Drivetrain extends Subsystem {
  IMotorController talon1;
  IMotorController talon2;

  public Drivetrain() throws ConfigException {
    this.talon1 = (IMotorController) Utils.getBean("talon1");
    this.talon2 = (IMotorController) Utils.getBean("talon2");
    this.talon1.neutralOutput();
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