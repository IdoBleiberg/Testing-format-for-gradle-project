package com.team3316.robot.humanIO;

import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;
public class JoysticksTest {
  private Joysticks joy;
  private double[][] normalP;
  private double[][] edgeP;

  JoysticksTest() {
    this.normalP = new double[][] {
        { 0.3, 0.15 },
        {-0.4, -0.2} 
    };
    this.edgeP = new double[][] {
        {5, 1},
        {-7, -1}
    };
  }

  //@Test
  public void testNormalP() {
    for (int i = 0; i < this.normalP.length; i++) {
      if (Joysticks.fromJoystickToVoltage(this.normalP[i][0]) != this.normalP[i][1]) {
        System.out.println("task fail at index" + String.valueOf(i));
        assertTrue(false);
        return;
      }
    }
    assertTrue(true);
  }

  //@Test
  public void testEdgeP() {
    for (int i = 0; i < this.edgeP.length; i++) {
      if (Joysticks.fromJoystickToVoltage(this.edgeP[i][0]) != this.edgeP[i][1]) {
        System.out.println("task fail at index" + String.valueOf(i));
        assertTrue(true);
        return;
      }
    }
    assertTrue(true);
  }

}