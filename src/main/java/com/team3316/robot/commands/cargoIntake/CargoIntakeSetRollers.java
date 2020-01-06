package com.team3316.robot.commands.cargoIntake;

 import com.team3316.kit.DBugLogger;
import com.team3316.kit.commands.DBugCommand;
import com.team3316.robot.Robot;
import com.team3316.robot.subsystems.CargoIntake.IntakeRollersState;

 /**
 * cargoIntakeSetRoller
 */
public class CargoIntakeSetRollers extends DBugCommand{
  private IntakeRollersState _state;

  public CargoIntakeSetRollers(IntakeRollersState state) {
    requires(Robot.cargoIntake);
    this._state = state;
  }

   @Override
  public void init() {
    DBugLogger.getInstance().info("Setting intake rollers to " + this._state.toString());
  }

   @Override
  public void execute() {
    Robot.cargoIntake.setRollersState(this._state);
  }

   @Override
  public boolean isFinished() {
    switch (this._state) {
      case IN:
        return Robot.cargoEjector.hasCargo();
      default:
        return true;
    }
  }

   @Override
  public void fin() {
    Robot.cargoIntake.setRollersState(IntakeRollersState.STOPPED);
  }

   @Override
  public void interr() {
    this.fin();
  }

}
