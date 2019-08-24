package com.team3316.robot.subsystems;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;
import com.ctre.phoenix.motorcontrol.can.VictorSPX;
import com.team3316.kit.motors.DBugTalon;
import com.team3316.robot.commands.TankDrive;
import com.team3316.robot.utils.Utils;

import edu.wpi.first.wpilibj.VictorSP;
import edu.wpi.first.wpilibj.command.Subsystem;

public class Drivetrain extends Subsystem {
  TalonSRX talon1;
  TalonSRX talon2;
  VictorSPX victor1;
  VictorSPX victor2;

  public Drivetrain() {
    this.talon1 = (TalonSRX)  Utils.getBean("talon1");
    this.talon2 = (TalonSRX) Utils.getBean("talon2");
    this.victor1 = (VictorSPX) Utils.getBean("victor1");
    this.victor2 = (VictorSPX) Utils.getBean("victor2");
    this.victor1.follow(this.talon1);
    this.victor2.follow(this.talon2);
  }

  @Override
  public void initDefaultCommand() {
    setDefaultCommand(new TankDrive());
  }

  public void setMotors(double talon1, double talon2) {
    this.talon1.set(ControlMode.PercentOutput, talon1);
    this.talon2.set(ControlMode.PercentOutput, talon2);
  }

  public double getTalon1() {
    return this.talon1.getMotorOutputPercent();
  }

  public double getTalon2() {
    return this.talon2.getMotorOutputPercent();
  }

  public double getVictor1() {
    return this.victor1.getMotorOutputPercent();
  }

  public double getVictor2() {
    return this.victor2.getMotorOutputPercent();
  }

}