package com.team3316.robot.commands.elevator;

import com.team3316.kit.DBugLogger;
import com.team3316.kit.commands.DBugCommandGroup;
import com.team3316.robot.Robot;
import com.team3316.robot.commands.cargoEjector.EjectorToEject;
import com.team3316.robot.commands.cargoEjector.EjectorToIntallLVL3;
import com.team3316.robot.commands.cargoEjector.SetEjectorArmVoltage;
import com.team3316.robot.commands.cargoIntake.CargoIntakeClose;
import com.team3316.robot.commands.hp.SetPanelState;
import com.team3316.robot.subsystems.CargoEjector.EjectorArmState;
import com.team3316.robot.subsystems.Elevator.ElevatorState;
import com.team3316.robot.subsystems.PanelMechanism.PanelMechanismState;

import edu.wpi.first.wpilibj.command.InstantCommand;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/**
 * SmartElevatorSequence
 */
public class SmartElevatorSequence extends DBugCommandGroup {
  private ElevatorState _wantedState;
  public SmartElevatorSequence(int elevatorLevel) {
    this._wantedState = ElevatorState.BOTTOM;
    if (Robot.cargoEjector.hasCargo()) {
      this._wantedState = ElevatorState.getCargoLevel(elevatorLevel);
    } else {
      this._wantedState = ElevatorState.getPanelsLevel(elevatorLevel);
    }
    if (Robot.cargoEjector.getArmState() != EjectorArmState.EJECT) {
      addSequential(new EjectorToEject(true));
    }

    addParallel(new SetEjectorArmVoltage(0.2));
    addParallel(new CargoIntakeClose());
    addSequential(new ElevatorSetState(this._wantedState));
    if (this._wantedState != ElevatorState.BOTTOM) {
      if (this._wantedState.isHPInstallState()) {
        addParallel(new SetPanelState(PanelMechanismState.OPEN));
      } else {
        addParallel(new SetPanelState(PanelMechanismState.CLOSED));
      }
    }
    if (this._wantedState == ElevatorState.LVL3_CARGO) {
      addSequential(new EjectorToIntallLVL3());
    }
  }
  @Override
  protected void init() {
    DBugLogger.getInstance().info("Moving to " + this._wantedState.toString());
  }
}
