package com.team3316.robot.subsystems;

import java.util.ArrayList;

import com.team3316.kit.DBugSubsystem;
import com.team3316.robot.Robot;
import com.team3316.robot.commands.CommandGroupV2;
import com.team3316.robot.commands.cargoEjector.CargoEjectorSetState;
import com.team3316.robot.commands.cargoIntake.CargoIntakeSetState;
import com.team3316.robot.commands.elevator.ElevatorSetState;
import com.team3316.robot.subsystems.CargoEjector.EjectorArmState;
import com.team3316.robot.subsystems.CargoIntake.IntakeArmState;
import com.team3316.robot.subsystems.Elevator.ElevatorState;

import edu.wpi.first.wpilibj.command.Command;

public class SuperSructure extends DBugSubsystem {

  private RobotState _wantedRobotState;
  private Handler _handler;

  public SuperSructure() {
    this._wantedRobotState = RobotState.Intermediet;
  }

  public synchronized void setRobotState(RobotState state) {
    this._wantedRobotState = state;
    this._handler = new Handler(state);
    this._handler.start();
  }

  public synchronized RobotState getWantedRobotState() {
    return this._wantedRobotState;
  }

  public synchronized ArrayList<Command> getNeededCommands() {
   return this._handler.getCommands();
  }

  @Override
  public void initDefaultCommand() { }

  @Override
  public void displayTestData() { }

  @Override
  public void displayMatchData() { }

  @Override
  public void displayCommands() { }
}

class Handler extends CommandGroupV2 {

  RobotState _state;

  Handler(RobotState state) {
    this._state = state;
    EjectorArmState _wantedEjectorState = state.getWantedEjectorState();
    IntakeArmState _wantedIntakeState = state.getWantedIntakeState();
    ElevatorState _wantedElevatorState = state.getWantedElevatorState();
    EjectorArmState _currentEjectorState = Robot.cargoEjector.getArmState();
    IntakeArmState _currentIntakeState = Robot.cargoIntake.getArmState();
    ElevatorState _currentElevatorState = Robot.elevator.getState();
    if (_currentElevatorState != _wantedElevatorState) {
      if (_currentEjectorState == EjectorArmState.EJECT) {
        if (_currentIntakeState != _wantedIntakeState) {
          add(() -> new ElevatorSetState(_wantedElevatorState), () -> new CargoIntakeSetState(_wantedIntakeState));
        } else {
          add(() -> new ElevatorSetState(_wantedElevatorState));
        }
      } 
      else {
        add(() -> new CargoEjectorSetState(EjectorArmState.EJECT));
        if (_currentIntakeState != _wantedIntakeState) {
          add(() -> new ElevatorSetState(_wantedElevatorState), () -> new CargoIntakeSetState(_wantedIntakeState));
        } else {
          add(() -> new ElevatorSetState(_wantedElevatorState));
        }
      }
    } else {
      if (_wantedIntakeState != _currentIntakeState) {
        if (_wantedIntakeState == IntakeArmState.OUT) add(() -> new CargoIntakeSetState(IntakeArmState.OUT));
        else {
          if (_currentEjectorState != EjectorArmState.EJECT) {
            add(() -> new CargoEjectorSetState(EjectorArmState.EJECT));
            add(() -> new CargoIntakeSetState(IntakeArmState.IN));
          } else {
            add(() -> new CargoIntakeSetState(IntakeArmState.IN));
          }
        }
      }
    }
  }

  public ArrayList<Command> getCommands() {
    ArrayList<Command> needed = new ArrayList<Command>();
    EjectorArmState _wantedEjectorState = this._state.getWantedEjectorState();
    IntakeArmState _wantedIntakeState = this._state.getWantedIntakeState();
    ElevatorState _wantedElevatorState = this._state.getWantedElevatorState();
    EjectorArmState _currentEjectorState = Robot.cargoEjector.getArmState();
    IntakeArmState _currentIntakeState = Robot.cargoIntake.getArmState();
    ElevatorState _currentElevatorState = Robot.elevator.getState();
    if (_currentElevatorState != _wantedElevatorState) {
      if (_currentEjectorState == EjectorArmState.EJECT) {
        if (_currentIntakeState != _wantedIntakeState) {
          needed.add(new ElevatorSetState(_wantedElevatorState));
          needed.add(new CargoIntakeSetState(_wantedIntakeState));
        } else {
          needed.add(new ElevatorSetState(_wantedElevatorState));
        }
      } 
      else {
        needed.add(new CargoEjectorSetState(EjectorArmState.EJECT));
        if (_currentIntakeState != _wantedIntakeState) {
          needed.add(new ElevatorSetState(_wantedElevatorState));
          needed.add(new CargoIntakeSetState(_wantedIntakeState));
        } else {
          needed.add(new ElevatorSetState(_wantedElevatorState));
        }
      }
    } else {
      if (_wantedIntakeState != _currentIntakeState) {
        if (_wantedIntakeState == IntakeArmState.OUT) {
          needed.add(new CargoIntakeSetState(IntakeArmState.OUT));
          if (_wantedEjectorState != _currentEjectorState) needed.add(new CargoEjectorSetState(_wantedEjectorState));
        }
        else {
          if (_currentEjectorState != EjectorArmState.EJECT) {
            needed.add(new CargoEjectorSetState(EjectorArmState.EJECT));
            needed.add(new CargoIntakeSetState(IntakeArmState.IN));
          } else {
            needed.add(new CargoIntakeSetState(IntakeArmState.IN));
          }
        }
      }
    }
    return needed;
  }

}
