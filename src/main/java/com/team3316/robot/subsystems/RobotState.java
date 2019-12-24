package com.team3316.robot.subsystems;

import com.team3316.robot.subsystems.CargoEjector.EjectorArmState;
import com.team3316.robot.subsystems.CargoIntake.IntakeArmState;
import com.team3316.robot.subsystems.Elevator.ElevatorState;

/*
* _startingCommand:
*   is the thread that should start the command group to change the state of the robot.
^ _runningCommands:
*   has a function get() which needs to return the needed command/commandGroups in order to change the robotState to the wanted one. 
*/
public enum RobotState {
  PRE_COLLECTCARGO, AFTER_COLLECTCARGO, CARGO_INSTALL_LVL3 ,Intermediet;

  private EjectorArmState _wantedEjectorState;
  private IntakeArmState _wantedIntakeState;
  private ElevatorState _wantedElevatorState;

  public void setStates(EjectorArmState wantedEjectorState, IntakeArmState wantedIntakeState, ElevatorState wantedElevaorState) {
    this._wantedEjectorState = wantedEjectorState;
    this._wantedIntakeState = wantedIntakeState;
    this._wantedElevatorState = wantedElevaorState;
  }

  public EjectorArmState getWantedEjectorState() {
    return this._wantedEjectorState;
  }

  public IntakeArmState getWantedIntakeState() {
    return this._wantedIntakeState;
  }

  public ElevatorState getWantedElevatorState() {
    return this._wantedElevatorState;
  }
}