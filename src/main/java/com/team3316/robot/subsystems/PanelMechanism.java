package com.team3316.robot.subsystems;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.NeutralMode;
import com.ctre.phoenix.motorcontrol.can.VictorSPX;
import com.team3316.kit.DBugSubsystem;
import com.team3316.kit.config.Config;
import com.team3316.kit.config.ConfigException;
import com.team3316.kit.motors.DBugTalon;
import com.team3316.robot.Robot;
import com.team3316.robot.subsystems.Elevator.ElevatorState;
import com.team3316.robot.utils.InvalidStateException;
import com.team3316.robot.utils.Utils;

import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.command.InstantCommand;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/**
 * A subsystem for the Hatch Panel collection mechanism
 */
public class PanelMechanism extends DBugSubsystem {
  private DigitalInput _closedSwitch, _openSwitch;
  private VictorSPX _armMotor;
  private boolean _isActive = true;

  private PanelMechanismState _wantedState;

  public PanelMechanism() {
    // Initialize actuators and sensors
    this._closedSwitch = (DigitalInput) Utils.getBean("Switch");
    this._openSwitch = (DigitalInput) Utils.getBean("Switch");
    this._armMotor = (VictorSPX) Utils.getBean("panelMotors");

    // Set voltage output percentage for OPEN and CLOSED
    PanelMechanismState.OPEN.setVoltage(1);
    PanelMechanismState.OPEN.setHoldVoltage(0.3);
    PanelMechanismState.CLOSED.setVoltage(-1);
    PanelMechanismState.CLOSED.setHoldVoltage(-0.3);

    // Set voltage scale removed for now becuase of tests.
    this._armMotor.enableVoltageCompensation(true);
    this._armMotor.configVoltageCompSaturation(5);
  }

  /**
   * Contains the possible mechanism states and their voltage if they have one
   */
  public enum PanelMechanismState {
    CLOSED, OPEN, INTERMEDIATE;

    private double _voltage = 0, _holdVoltage;

    public void setVoltage(double _voltage) {
      this._voltage = _voltage;
    }

    public double getVoltage() {
      return this._voltage;
    }

    public double getHoldVoltage() {
      return this._holdVoltage;
    }

    public void setHoldVoltage(double voltage) {
      this._holdVoltage = voltage;
    }

  }

  /**
   * When false the input from setArmState will be ignore
   */
  public void setIsActive(boolean active) {
    this._isActive = active;
  }

  /**
   * @return the state of the mechanism.
   *
   * +---------------+--------+------+--------------+
   * |               | CLOSED | OPEN | INTERMEDIATE |
   * +---------------+--------+------+--------------+
   * | _openSwitch   | 1      | 1    | 0            |
   * +---------------+--------+------+--------------+
   * | _closedSwitch | 1      | 0    | 0            |
   * +---------------+--------+------+--------------+
   */
  public PanelMechanismState getState() {
    if (this.getClosedSwitch()) return PanelMechanismState.CLOSED;
    if (this.getOpenSwitch()) return PanelMechanismState.OPEN;
    return PanelMechanismState.INTERMEDIATE;
  }

  /**
   * @return _closedSwitch's current value
   */
  private boolean getClosedSwitch() {
    return !this._closedSwitch.get();
  }

  public void stopMovment() {
    this._armMotor.set(ControlMode.PercentOutput, 0.0);
  }

  /**
   * @return _openSwitch's current value
   */
  private boolean getOpenSwitch() {
    return !this._openSwitch.get();
  }

  public void setArmState(PanelMechanismState wantedState) throws Exception, InvalidStateException {
    // Stop running if _isActive is set to false
    if (!this._isActive) throw new Exception("Cannot move panel mechanism when set to inactive");
    // If wantedState is INTERMEDIATE throw InvalidStateException
    if (wantedState == PanelMechanismState.INTERMEDIATE)
      throw new InvalidStateException("Cannot set panel mechanism to INTERMEDIATE");

    if (!Robot.elevator.getState().isAboveBottom()) {
      throw new InvalidStateException("Cannot move panel when elevator is below lvl 1 hp");
    }

    this._wantedState = wantedState;

    this._armMotor.set(ControlMode.PercentOutput, wantedState.getVoltage());
  }

  /**
   * Put data in SDB
   */
  public void displayTestData() {
  }

  public void setVoltageUnsafe(double voltage) {
    this._armMotor.set(ControlMode.PercentOutput, voltage);
  }

  /**
   * Put commands in SDB
   */
  public void displayCommands() {
  }

  public void setBrakes(boolean brake) {
    NeutralMode mode = brake ? NeutralMode.Brake : NeutralMode.Coast;
    this._armMotor.setNeutralMode(mode);
  }
  @Override
  public void periodic() {
    //this._armMotor.set(ControlMode.PercentOutput, this._holdVoltage);
  }

  public PanelMechanismState getWantedState() {
    return this._wantedState;
  }

  public void setHoldVoltage(PanelMechanismState state) {
    this._armMotor.set(ControlMode.PercentOutput, state.getHoldVoltage());
  }

  @Override
  public void initDefaultCommand() {
    //setDefaultCommand(new StayAtState());
  }

  @Override
  public void displayMatchData() {
    // TODO Auto-generated method stub

  }
}