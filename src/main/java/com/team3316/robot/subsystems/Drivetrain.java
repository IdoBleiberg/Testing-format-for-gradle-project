package com.team3316.robot.subsystems;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.can.VictorSPX;
import com.kauailabs.navx.frc.AHRS;
import com.team3316.kit.motors.DBugTalon;
import com.team3316.robot.commands.TankDrive;
import com.team3316.robot.utils.Utils;

import edu.wpi.first.wpilibj.command.Subsystem;

public class Drivetrain extends Subsystem {

  private DBugTalon talon1;
  private DBugTalon talon2;
  private VictorSPX victor1;
  private VictorSPX victor2;

  private AHRS _NavX;

  public Drivetrain() {
    this._NavX = (AHRS) Utils.getBean("NavX");

    this.talon1 = (DBugTalon)  Utils.getBean("talon1");
    this.talon2 = (DBugTalon) Utils.getBean("talon2");
    this.victor1 = (VictorSPX) Utils.getBean("victor1");
    this.victor2 = (VictorSPX) Utils.getBean("victor2");
    this.victor1.follow(this.talon1);
    this.victor2.follow(this.talon2);

    this.talon1.setDistancePerRevolution(2 * Math.PI * 5.0 / 40, 10);
    this.talon2.setDistancePerRevolution(2 * Math.PI * 5.0 / 40, 10);
  }

  @Override
  public void initDefaultCommand() {
    setDefaultCommand(new TankDrive());
  }

  public void setMotors(double talon1, double talon2) {
    this.talon1.set(ControlMode.PercentOutput, talon1);
    this.talon2.set(ControlMode.PercentOutput, talon2);
  }

  public double getYaw() {
    return this._NavX.getYaw();
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

  public void setDistance(double demand1, double demand2) {
    this.talon1.setDistance(demand1);
    this.talon2.setDistance(demand2);
  }

  public double[] getDistance() {
    double[] dist = { this.talon1.getDistance(), this.talon2.getDistance() };
    return dist;
  }

}