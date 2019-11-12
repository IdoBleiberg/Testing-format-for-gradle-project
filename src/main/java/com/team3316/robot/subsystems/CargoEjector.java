package com.team3316.robot.subsystems;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.NeutralMode;
import com.team3316.kit.motors.DBugTalon;
import com.team3316.robot.Robot;
import com.team3316.robot.subsystems.CargoIntake.IntakeArmState;
import com.team3316.robot.utils.InvalidStateException;
import com.team3316.robot.utils.Utils;

import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.VictorSP;
import edu.wpi.first.wpilibj.command.Subsystem;
/**
 * CGEjector
 */
public class CargoEjector extends Subsystem {
  private VictorSP _ejectorMotor;
  //private DigitalInput _LSwitch, _RSwitch;
  private DigitalInput _switch;
  private DBugTalon _armTalon;
  private DigitalInput _collectHallEffect, _ejectHallEffect;
  private int _armPIDLoopIndex, _armPIDTimeout;
  private double _kArmTolerance;

  public CargoEjector() {
    this._armPIDTimeout = 3;
    this._armPIDLoopIndex = 0;
    this._ejectorMotor = (VictorSP) Utils.getBean("VictorMotor");

    this._kArmTolerance = 2;

    /*
     * Switches
     */
    this._switch = (DigitalInput) Utils.getBean("Switch");

    /*
     * Hall Effects
     */
    this._ejectHallEffect = (DigitalInput) Utils.getBean("Switch");
    this._collectHallEffect = (DigitalInput) Utils.getBean("Switch");

    /*
     * talon configuration
     */
    this._armTalon = (DBugTalon) Utils.getBean("ejectorArmMotor");

    this._armTalon.setDistancePerRevolution(1, 1);

    this._armTalon.setInverted(true);
    this._armTalon.setSensorPhase(true);

    EjectorArmState.COLLECT.setAngle(0);
    EjectorArmState.EJECT.setAngle(180);

    EjectorRollerState.IN.setVoltage(1);
    EjectorRollerState.OUT.setVoltage(-1);
    EjectorRollerState.STOP.setVoltage(0.0);
    // TODO: move to config
    this._armTalon.configVoltageCompSaturation(4);
    this._armTalon.enableVoltageCompensation(true);
  }


  @Override
  public void periodic() {
    if (this._collectHallEffect.get())
      this.resetEncoder(EjectorArmState.COLLECT);
    else if (this._ejectHallEffect.get())
      this.resetEncoder(EjectorArmState.EJECT);
  }

  public enum EjectorArmState {
    EJECT, COLLECT, INTERMEDIATE;

    private double _angle;

    public void setAngle(double angle) {
      this._angle = angle;
    }

    public double getAngle() {
      return this._angle;
    }
  }

  public enum EjectorRollerState {
    IN, OUT, STOP;

    private double _voltage;

    public void setVoltage(double voltage) {
      this._voltage = voltage;
    }

    public double getVoltage() {
      return this._voltage;
    }
  }

  public void setRollerState(EjectorRollerState state) {
    this._ejectorMotor.set(state.getVoltage());
  }

  public void setArmState(EjectorArmState state) throws InvalidStateException {
    if (state == EjectorArmState.INTERMEDIATE)
      throw new InvalidStateException("Cannot move ejector arm to intermediate");
    if (state == EjectorArmState.COLLECT && Robot.cargoIntake.getArmState() == IntakeArmState.IN)
      throw new InvalidStateException("Cannot move arm to COLLECT if cargointake is IN");

    EjectorArmState currentState = this.getArmState();
    //DBugLogger.getInstance().info("Changing Ejector arm state: " + currentState.toString() + " -> " + state.toString());
    this._armTalon.set(ControlMode.Position, state.getAngle());
  }

  @Override
  public void initDefaultCommand() {

  }

  public EjectorArmState getArmState() {
    if (this.getArmPos() >= EjectorArmState.EJECT.getAngle() - this._kArmTolerance)
      return EjectorArmState.EJECT;
    else if (this.getArmPos() <= EjectorArmState.COLLECT.getAngle() + this._kArmTolerance)
      return EjectorArmState.COLLECT;
    return EjectorArmState.INTERMEDIATE;
  }

  public double getArmPos() {
    return this._armTalon.getDistance();
  }

  public boolean hasCargo() {
    return this._switch.get(); //this._LSwitch.get() || this._RSwitch.get();
  }

  private void resetEncoder(EjectorArmState state) {
    this._armTalon.setDistance(state.getAngle());
  }

  public void setBrake(boolean status) {
    NeutralMode mode = status ? NeutralMode.Brake : NeutralMode.Coast;
    this._armTalon.setNeutralMode(mode);
  }

  public void initRoutine() {
    this.setBrake(false);
  }

  public void disabledRoutine() {
    this.setBrake(true);
    this._armTalon.set(ControlMode.PercentOutput, 0.0);
  }
}