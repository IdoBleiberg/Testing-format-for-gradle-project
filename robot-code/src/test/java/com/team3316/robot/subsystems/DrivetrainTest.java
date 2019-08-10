package com.team3316.robot.subsystems;

import static org.junit.jupiter.api.Assertions.assertEquals;

import com.team3316.kit.config.ConfigException;

import org.junit.jupiter.api.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.beans.factory.BeanFactory;

public class DrivetrainTest {
  Drivetrain drivetrain ;

  //private Animal dog;

  public DrivetrainTest() {
    ApplicationContext context = new ClassPathXmlApplicationContext("/ApplicationContext.xml");
    try {
      this.drivetrain = new Drivetrain();
    } catch (ConfigException e) {
      // TODO Auto-generated catch block
		e.printStackTrace();
	}
    //this.dog = (Animal) context.getBean("dog");
  }

  @Test
  public void dogTest() {
    //assertEquals("danny", dog.getName());
    this.drivetrain.setMotors(0.3, 0.2);
    assertEquals(0.3, this.drivetrain.getMotor1()); 
  }
}
