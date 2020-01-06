package com.team3316.robot.commands.cargoEjector;

import com.team3316.kit.commands.DBugCommand;
import com.team3316.robot.Robot;
import com.team3316.robot.subsystems.Elevator.ElevatorState;

public class SetEjectorArmVoltage extends DBugCommand {
  
  public SetEjectorArmVoltage(double v) {
    
  }

	@Override
	public void init() {
		
	}

	@Override
	public void execute() {
		//Robot.cargoEjector.setArmVoltage(this._voltage);
	}

	@Override
	public boolean isFinished() {
		return Robot.elevator.getPosition() < ElevatorState.LVL2_CARGO.getHeight() - 30;
	}

	@Override
	public void fin() {
		
	}

	@Override
	public void interr() {
		
	}

  
}