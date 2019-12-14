package com.team3316.robot.commands.cargoEjector;

import com.team3316.kit.commands.DBugCommand;
import com.team3316.robot.Robot;
import com.team3316.robot.subsystems.Elevator.ElevatorState;

public class SetEjectorArmVoltage extends DBugCommand {
  private double _voltage = 0;
  
  public SetEjectorArmVoltage(double v) {
    //requires(Robot.cargoEjector);
    this._voltage = v;
  }

	@Override
	protected void init() {
		
	}

	@Override
	protected void execute() {
		//Robot.cargoEjector.setArmVoltage(this._voltage);
	}

	@Override
	protected boolean isFinished() {
		return Robot.elevator.getPosition() < ElevatorState.LVL2_CARGO.getHeight() - 30;
	}

	@Override
	protected void fin() {
		
	}

	@Override
	protected void interr() {
		
	}

  
}