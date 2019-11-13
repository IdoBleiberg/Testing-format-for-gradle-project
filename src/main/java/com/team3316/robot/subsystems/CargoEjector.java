package com.team3316.robot.subsystems;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.NeutralMode;
import com.team3316.kit.DBugSubsystem;
import com.team3316.kit.motors.DBugTalon;
import com.team3316.robot.Robot;
import com.team3316.robot.subsystems.CargoIntake.IntakeArmState;
import com.team3316.robot.subsystems.Elevator.ElevatorState;
import com.team3316.robot.utils.InvalidStateException;
import com.team3316.robot.utils.Utils;

import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.VictorSP;
/**
 * CGEjector
 */
public class CargoEjector extends DBugSubsystem {
  private VictorSP _ejectorMotor;
  //private DigitalInput _LSwitch, _RSwitch;
  private DigitalInput _switch;
  public DBugTalon _armTalon;
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
    EjectorArmState.SC.setAngle(90);
    EjectorArmState.INSTALL_LVL3.setAngle(175);
    EjectorArmState.EJECT.setAngle(180);

    EjectorRollerState.IN.setVoltage(1);
    EjectorRollerState.OUT.setVoltage(-1);
    EjectorRollerState.STOP.setVoltage(0.0);
    // TODO: move to config
    this._armTalon.configVoltageCompSaturation(4);
    this._armTalon.enableVoltageCompensation(true);

    this._armTalon.zeroEncoder();

  }


  @Override
  public void periodic() {
    if (this._collectHallEffect.get())
      this.resetEncoder(EjectorArmState.COLLECT);
    else if (this._ejectHallEffect.get())
      this.resetEncoder(EjectorArmState.EJECT);
  }

  public enum EjectorArmState {
    EJECT, SC, COLLECT, INSTALL_LVL3 ,INTERMEDIATE;

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
    if (state == EjectorArmState.INTERMEDIATE) {
      System.out.println("Cannot move ejector arm to intermediate");
      throw new InvalidStateException("Cannot move ejector arm to intermediate");
    }
    if (Robot.elevator.getPosition() > ElevatorState.LVL1_HP.getHeight()) {
      if (state == EjectorArmState.EJECT) {
        if (this.getArmPos() <= 173) {
          System.out.println("Cannot move ejector to eject if elevator isn't down and is at the other side of the elevator");
          throw new InvalidStateException("Cannot move ejector to eject if elevator isn't down and is at the other side of the elevator");
        }
      }
    }
    if (state == EjectorArmState.COLLECT && Robot.cargoIntake.getArmState() == IntakeArmState.IN) {
      System.out.println("Cannot move arm to COLLECT if cargointake is IN");
      throw new InvalidStateException("Cannot move arm to COLLECT if cargointake is IN");
    }
    // Barak: This raised a False Positive in game
    // if (state == EjectorArmState.EJECT
    //     && Robot.panelMechanism.getState() == PanelMechanism.PanelMechanismState.INTERMEDIATE_CLOSED)
    //   throw new InvalidStateException("Cannot move arm to EJECT if panel mechainsm is between CLOSED and INSTALL");

    /*
    if (this.getArmState() == EjectorArmState.EJECT && state == EjectorArmState.INSTALL_LVL3) {
      this._armTalon.selectProfileSlot(this._armPIDLoopIndex + 1, 0);
    } else if (this.getArmState() == EjectorArmState.INSTALL_LVL3 && state == EjectorArmState.EJECT) {
      this._armTalon.selectProfileSlot(this._armPIDLoopIndex + 1, 0);
    } else {
      this._armTalon.selectProfileSlot(this._armPIDLoopIndex, 0);
    }
    */
    this._armTalon.set(ControlMode.Position, state.getAngle());
  }

  @Override
  public void initDefaultCommand() {

  }

  public EjectorArmState getArmState() {
    if (this.getArmPos() >= EjectorArmState.EJECT.getAngle() - this._kArmTolerance) {
      return EjectorArmState.EJECT;
    }
    else if (this.getArmPos() <= EjectorArmState.COLLECT.getAngle() + this._kArmTolerance) {
      return EjectorArmState.COLLECT;
    }
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

  @Override
  public void displayTestData() {
    // TODO Auto-generated method stub

  }

  @Override
  public void displayMatchData() {
    // TODO Auto-generated method stub

  }

  @Override
  public void displayCommands() {
    // TODO Auto-generated method stub

  }
}