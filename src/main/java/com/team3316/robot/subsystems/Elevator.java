package com.team3316.robot.subsystems;

import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.NeutralMode;
import com.ctre.phoenix.motorcontrol.can.VictorSPX;
import com.team3316.kit.DBugSubsystem;
import com.team3316.kit.motors.DBugTalon;
import com.team3316.robot.Robot;
import com.team3316.robot.subsystems.CargoEjector.EjectorArmState;
import com.team3316.robot.utils.InvalidStateException;
import com.team3316.robot.utils.Utils;

import edu.wpi.first.wpilibj.DigitalInput;

/**
 * Elevator subsystem
 */
public class Elevator extends DBugSubsystem {
  /*
   * Actuators
   */
  private DBugTalon _masterMotor;
  private VictorSPX _slaveMotor;
  private double _distPerRevolution;
  private int _upr;

  /*
   * Tolerances
   */
  private double _motorCLETolerance;

  /*
   * Talon profile slots
   */
  public static final int kUpProfileSlot = 0;
  public static final int kDownProfileSlot = 1;

  /**
   * An enumeration of the elevator's different states
   */
  public enum ElevatorState {
    BOTTOM(0), // Elevator is bottomed
    BOTTOM_BP(1), // Elevator is at the bottom breakpoint
    SC(2),
    AFTER_SC(2),
    LVL1_HP(3),// Level 1 HP install & acquire
    LS_COLLECT(4), 
    CARGOSHIP(5),
    LVL1_CARGO(6), // Level 1 Cargo
    STARTING_CONF(7), // Starting Configuration
    PRE_LVL2_HP(8), LVL2_HP(9), // Level 2 HP install
    LVL2_CARGO(10), // Level 2 Cargo install
    PRE_LVL3_HP(11), LVL3_HP(12), // Level 3 HP install
    LVL3_CARGO(13), // Level 3 Cargo install
    TOP_BP(14), // Elevator is at the top breakpoint
    TOP(15), // Elevator is topped
    INTERMEDIATE(16); // Intermediate state

    private int _index;
    private double _height = -3316.0;
    private DigitalInput _hallEffect = null;

    ElevatorState(int index) {
      this._index = index;
    }

    /**
     * Sets the states' physical height in the elevator.
     *
     * @param height The height of the state, in centimeters. The value for
     *               heightless states (i.e. INTERMEDIATE) should be -3316.0 which
     *               is default.
     */
    public void setHeight(double height) {
      this._height = height;
    }

    /**
     * @return The state's height
     */
    public double getHeight() {
      return this._height;
    }

    public double getDistanceFromState() {
      return Math.abs(Robot.elevator.getPosition() - this.getHeight());
    }

    /**
     * Adds a sensor to the state that activates when the state is active. This
     * usually will be a hall effect sensor (that's the reason for the name), but
     * can be any digital input. This method shouldn't be called on sensorless
     * states (i.e. INTERMEDIATE) since the default value of this property is null,
     * which is what the {@link ElevatorState#getSensorValue()} method requires for
     * reutrning an empty Optional.
     *
     * @param port The DIO port of the sensor.
     */
    public void addSensor(int port) {
      this._hallEffect = new DigitalInput(port);
    }

    /**
     * Returns an Optional instance with the sensor's value (if there is a sensor).
     *
     * @return If the sensor isn't null, then an Optional with its value. Else, an
     *         empty Optional.
     */
    public Optional<Boolean> getSensorValue() {
      return this._hallEffect != null ? Optional.of(!this._hallEffect.get()) : Optional.empty();
    }

    /**
     * @return A boolean indicating whether the state's position is above the
     *         ElevatorState.BOTTOM position using its index.
     */
    public boolean isAboveBottom() {
      return this._index > 0;
    }
    /*
    public ElevatorState getPreState() {
      switch (this) {
        case LVL1_HP:
          return ElevatorState.PRE_LVL1_HP;
        case LVL2_HP:
          return ElevatorState.PRE_LVL2_HP;
        case LVL3_HP:
          return ElevatorState.PRE_LVL3_HP;
        default:
          return ElevatorState.INTERMEDIATE;
      }
    }
    */
    public boolean isHPInstallState() {
      ElevatorState[] HPInstallStates = { ElevatorState.LVL1_HP, ElevatorState.LVL2_HP, ElevatorState.LVL3_HP, ElevatorState.LS_COLLECT};
      return (Arrays.stream(HPInstallStates).anyMatch(this::equals));
    }

    public boolean isCargoInstallState() {
      ElevatorState[] CargoInstallStates = { ElevatorState.CARGOSHIP, ElevatorState.LVL1_CARGO, ElevatorState.LVL2_CARGO,
          ElevatorState.LVL3_CARGO, ElevatorState.LVL2_HP, ElevatorState.LVL3_HP };
      return (Arrays.stream(CargoInstallStates).anyMatch(this::equals));
    }

    public static ElevatorState getCargoLevel(int level) {
      switch (level) {
        case 1:
          return ElevatorState.LVL1_CARGO;
        case 2:
          return ElevatorState.LVL2_CARGO;
        case 3:
          return ElevatorState.LVL3_CARGO;
        case 4:
          return ElevatorState.CARGOSHIP;
        default:
          return ElevatorState.BOTTOM;
      }
    }

    public static ElevatorState getPanelsLevel(int level) {
      switch (level) {
        case 1:
          return ElevatorState.LVL1_HP;
        case 2:
          return ElevatorState.LVL2_HP;
        case 3:
          return ElevatorState.LVL3_HP;
        case 4:
          return ElevatorState.LS_COLLECT;
        case 5: 
        return ElevatorState.CARGOSHIP;
        default:
          return ElevatorState.BOTTOM;
      }
    }
  }

  public Elevator() {
    // Actuators
    this._masterMotor = (DBugTalon) Utils.getBean("elevator.motor.1");
    this._slaveMotor = (VictorSPX) Utils.getBean("elevator.motor.2");
    this._slaveMotor.follow(this._masterMotor); // Motor 1 is the master, motor 2 .losedLoopErrorTolerance");
    this._distPerRevolution = 4;
    this._upr = 10;
    this._motorCLETolerance = 2;
    this._masterMotor.setDistancePerRevolution(this._distPerRevolution, this._upr);
    this._masterMotor.zeroEncoder();

    this._masterMotor.configAllowableClosedloopError(Elevator.kDownProfileSlot, (int) this._motorCLETolerance, 10);
    this._masterMotor.configAllowableClosedloopError(Elevator.kUpProfileSlot, (int) this._motorCLETolerance, 10);

    this._masterMotor.setInverted(true);
    this._slaveMotor.setInverted(false);
    this._masterMotor.setSensorPhase(true);

    this._masterMotor.enableCurrentLimit(true);
    this._masterMotor.configPeakCurrentLimit(80);
    this._masterMotor.configContinuousCurrentLimit(60);

    // Internal states initalization
    ElevatorState.BOTTOM.setHeight(0);
    ElevatorState.BOTTOM_BP.setHeight(7.9);
    ElevatorState.STARTING_CONF.setHeight(58.25);
    ElevatorState.TOP_BP.setHeight(142.5);
    ElevatorState.TOP.setHeight(160.4);

    // Levels initialization
    ElevatorState.SC.setHeight(12.6);
    ElevatorState.AFTER_SC.setHeight(26.0);
    ElevatorState.LVL1_CARGO.setHeight(13.0);
    ElevatorState.LVL1_HP.setHeight(43.9);
    ElevatorState.LS_COLLECT.setHeight(44.3);
    ElevatorState.CARGOSHIP.setHeight(60.0);
    ElevatorState.PRE_LVL2_HP.setHeight(126.6 - 8.0);
    ElevatorState.LVL2_HP.setHeight(126.6);
    ElevatorState.LVL2_CARGO.setHeight(90.0);
    ElevatorState.PRE_LVL3_HP.setHeight(161.5 - 8.0);
    ElevatorState.LVL3_HP.setHeight(161.5);
    ElevatorState.LVL3_CARGO.setHeight(161.5);

    // Sensors
    /*
    ElevatorState.BOTTOM.addSensor(0);
    ElevatorState.BOTTOM_BP.addSensor(1);
    ElevatorState.STARTING_CONF.addSensor((int) Config.getInstance().get("elevator.hallEffects.startingConfiguration"));
    ElevatorState.TOP_BP.addSensor((int) Config.getInstance().get("elevator.hallEffects.topBreakpoint"));
    ElevatorState.TOP.addSensor((int) Config.getInstance().get("elevator.hallEffects.top"));
    */ 
    //Problematic for testing TODO: Talk to the gang about not doing that
  }

  @Override
  public void initDefaultCommand() {

  }

  /**
   * Elevator periodic zeroing function
   */
  @Override
  public void periodic() {
    if (ElevatorState.BOTTOM.getSensorValue().orElse(false)) {
      this._masterMotor.setDistance(ElevatorState.BOTTOM.getHeight());
    }

    else if (ElevatorState.BOTTOM_BP.getSensorValue().orElse(false)) {
      this._masterMotor.setDistance(ElevatorState.BOTTOM_BP.getHeight());
    }

    else if (ElevatorState.STARTING_CONF.getSensorValue().orElse(false)) {
      this._masterMotor.setDistance(ElevatorState.STARTING_CONF.getHeight());
    }

    else if (ElevatorState.TOP_BP.getSensorValue().orElse(false)) {
      this._masterMotor.setDistance(ElevatorState.TOP_BP.getHeight());
    }

    else if (ElevatorState.TOP.getSensorValue().orElse(false)) {
      this._masterMotor.setDistance(ElevatorState.TOP.getHeight());
    }

  }

  /**
   * Sets the elevator motors' percentage output. The method will check whether
   * the motion is physically possible (meaning that when the elevator is down we
   * can't go more down and when it's up we can't go more up, so do nothing in
   * these cases).
   *
   * @param percentage The voltage percentage to set to the motors. Value ranges
   *                   from -1 to 1.
   */
  public void setPercentage(double percentage) {
    double direction = Math.signum(percentage);
    boolean willHurtElevator = (this.getState() == ElevatorState.BOTTOM && direction == -1) // Trying to go down in bottom
        || (this.getState() == ElevatorState.TOP && direction == 1); // Trying to go up in top
    if (willHurtElevator)
      return;

    this._masterMotor.set(ControlMode.PercentOutput, percentage);
  }

  /**
   * Sets the elevator's position using a PID loop. This method is unsafe since it
   * doesn't check for system safety which is why it's private. In order to set
   * the position safely, use {@link Elevator#setPosition(double)}
   *
   * @param position The wanted elevator position. This should be between 0 and
   *                 the top state's height.
   */
  public void setPositionUnsafe(double position) {
    if (this._masterMotor.getDistance() < position) {
      this._masterMotor.selectProfileSlot(Elevator.kUpProfileSlot, 0);
    } else {
      this._masterMotor.selectProfileSlot(Elevator.kDownProfileSlot, 0);
    }
    this._masterMotor.set(ControlMode.Position, position);
  }

  /**
   * Returns the holder's postion inside the elevator.
   */
  public double getPosition() {
    return this._masterMotor.getDistance();
  }

  public void collectPanel() {
    this._masterMotor.set(ControlMode.Position, this.getPosition() + 17.0);
  }

  public void collectPanel(float addedPos) {
    this._masterMotor.set(ControlMode.Position, this.getPosition() + addedPos);
  }

  /**
   * @return The closed-loop error of the master TalonSRX.
   */
  public double getError() {
    return this._masterMotor.getError();
  }

  public void installPanel() {
    this._masterMotor.set(ControlMode.Position, this.getPosition() - 5.5);
  }

  /**
   * Returns the elevator's current state - a value of the {@link ElevatorState}
   * enum.
   *
   * @return If any of the elevator's hall effect sensors is activated, then its
   *         ajdacent state. Otherwise, the intermediate state.
   */
  public ElevatorState getState() {
    for (ElevatorState possibleState : ElevatorState.values()) { // Iterate over all of the possible states
      if (possibleState == ElevatorState.INTERMEDIATE) {
        continue;
      }

      if (Utils.isInNeighborhood(this.getPosition(), possibleState.getHeight(), this._motorCLETolerance)) {
        return possibleState;
      }
    }
    return ElevatorState.INTERMEDIATE;
  }

  /**
   * Return the closest panel state
   * When we want to find the pre-state of a state we can't just use getState because
   * the elevator might slip out of range. So we use this more stable solution instead. 
   */
  public ElevatorState getClosestPanelState() {
    Map <Double, ElevatorState> heightDiff = new HashMap<Double, ElevatorState>();
    heightDiff.put(ElevatorState.LVL1_HP.getDistanceFromState(),ElevatorState.LVL1_HP);
    heightDiff.put(ElevatorState.LVL2_HP.getDistanceFromState(),ElevatorState.LVL2_HP);
    heightDiff.put(ElevatorState.LVL3_HP.getDistanceFromState(),ElevatorState.LVL3_HP);
    return heightDiff.get(Collections.min(heightDiff.keySet()));
  }

  /**
   * Sets the elevator state to a given wanted state
   *
   * @param wantedState The wanted state of the elevator, as a member of the
   *                    {@link ElevatorState} enum.
   * @throws InvalidStateException
   */
  public void setState(ElevatorState wantedState) throws InvalidStateException {
    if (this.getState() == wantedState)
      return;
    switch (wantedState) {
    case INTERMEDIATE:
    case STARTING_CONF:
    System.out.println("Cannot move elevator states without heights.");
      throw new InvalidStateException("Cannot move elevator states without heights.");
    default:
      if (wantedState != ElevatorState.BOTTOM && Robot.cargoEjector.getArmState() != EjectorArmState.EJECT) 
        if (Robot.cargoEjector.getArmState() != EjectorArmState.SC && wantedState.getHeight() > ElevatorState.LVL1_HP.getHeight())  {
          System.out.println("Cannot lift elevator if ejector is not in eject");
          throw new InvalidStateException("Cannot lift elevator if ejector is not in eject");
        }
    }
    this.setPositionUnsafe(wantedState.getHeight());
  }

  public void setBrake(boolean status) {
    NeutralMode mode = status ? NeutralMode.Brake : NeutralMode.Coast;
    this._masterMotor.setNeutralMode(mode);
    this._slaveMotor.setNeutralMode(mode);
  }

  public void initRoutine() {
    this.setBrake(false);
  }

  public void disabledRoutine() {
    this.setBrake(true);
    this.setPercentage(0.0);
  }

  public void displayTestData() { }

  public void displayMatchData() { }

  public void displayCommands() { }
}