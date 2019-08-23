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
  public void dogTest() {
    //assertEquals("danny", dog.getName());
    Robot.drivetrain.setMotors(0.3, 0.2);
    assertEquals(0.3, Robot.drivetrain.getMotor1()); 
  }
}
