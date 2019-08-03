/**
 * Class for joysticks and joystick buttons
 */
package com.team3316.robot.humanIO;

import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.buttons.Button;
import com.team3316.robot.Robot;

public class Joysticks {
  /*
   * Defines a button in a gamepad POV for an array of angles
   */
  private class POVButton extends Button {
    Joystick m_joystick;
    int m_deg;

    public POVButton (Joystick joystick, int deg) {
      m_joystick = joystick;
      m_deg = deg;
    }

    public boolean get () {
      if (m_joystick.getPOV() == m_deg) {
        return true;
      }
      return false;
    }
  }

  public Joysticks () {

  }

  public void initButtons () {

  }
}
