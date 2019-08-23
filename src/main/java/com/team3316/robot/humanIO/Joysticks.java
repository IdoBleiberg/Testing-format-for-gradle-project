/**
 * Class for joysticks and joystick buttons
 */
package com.team3316.robot.humanIO;

import com.team3316.kit.config.ConfigException;

import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.buttons.Button;

public class Joysticks {
  /*
   * Defines a button in a gamepad POV for an array of angles
   */
  private class POVButton extends Button {
    Joystick m_joystick;
    int m_deg;

    public POVButton(Joystick joystick, int deg) {
      m_joystick = joystick;
      m_deg = deg;
    }

    public boolean get() {
      if (m_joystick.getPOV() == m_deg) {
        return true;
      }
      return false;
    }
  }

  private Joystick _leftJoystick, _rightJoystick, _operatorJoystick;

  public Joysticks() {
    //this._leftJoystick = new Joystick(0);
    this._rightJoystick = new Joystick(1);
    //this._operatorJoystick = new Joystick((int) Config.getInstance().get("joysticks.operator"));
  }

  public double getLeftY() {
    return -this._leftJoystick.getY();
  }

  public double getRightY() {
    return -this._rightJoystick.getY();
  }

  public static double fromJoystickToVoltage(double joy) {
    if (-0.5 < joy && joy < 0.5) {
      return joy / 2;
    } else {
      return joy;
    }
  }

  public void initButtons() {
  }
}