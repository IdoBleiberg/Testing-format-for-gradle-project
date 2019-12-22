package com.team3316.robot.commands.examples;

import com.team3316.robot.Robot;
import com.team3316.robot.subsystems.RobotState;

import edu.wpi.first.wpilibj.command.Command;

/**
 * MoveToState
 */
public class MoveToState extends Command {

  private RobotState _state;

  public MoveToState(RobotState state) {
    requires(Robot.superSructure);
    this._state = state;
  }

  @Override
  protected void initialize() {
    Robot.superSructure.setRobotState(this._state);
  }

  @Override
  protected boolean isFinished() {
    return Robot.cargoEjector.getArmState() == this._state.getWantedEjectorState() && Robot.cargoIntake.getArmState() == this._state.getWantedIntakeState() 
      && Robot.elevator.getState() == this._state.getWantedElevatorState();
  }

  
  
}