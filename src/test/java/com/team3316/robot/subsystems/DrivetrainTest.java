package com.team3316.robot.subsystems;

import static org.junit.jupiter.api.Assertions.assertEquals;

import com.team3316.kit.config.ConfigException;
import com.team3316.robot.Robot;

import org.junit.jupiter.api.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.beans.factory.BeanFactory;

public class DrivetrainTest {
  Drivetrain drivetrain ;

  //private Animal dog;

  public DrivetrainTest() {
  }

  @Test
  public void testTalon1() {
    Robot.drivetrain.setMotors(0.3, 0.0);
    assertEquals(Robot.drivetrain.getTalon1(), 0.3);
  }

  @Test
  public void testTalon2() {
    Robot.drivetrain.setMotors(0.0, -0.2);
    assertEquals(Robot.drivetrain.getTalon2(), -0.2);
  }

  @Test
  public void testVictor1() {
    Robot.drivetrain.setMotors(-0.5, 0.0);
    assertEquals(Robot.drivetrain.getVictor1(), -0.5);
  }

  @Test
  public void testVictor2() {
    Robot.drivetrain.setMotors(0.0, 1.0);
    assertEquals(Robot.drivetrain.getVictor2(), 1.0);
  }

}
