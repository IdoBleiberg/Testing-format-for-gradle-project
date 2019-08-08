package com.team3316.robot.subsystems;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.beans.factory.BeanFactory;

public class DrivetrainTest {
  //Drivetrain mockDrivetrain = mock(Drivetrain.class);

  private Animal dog;

  public DrivetrainTest() {
    ApplicationContext context = new ClassPathXmlApplicationContext("/ApplicationContext.xml");
    this.dog = (Animal) context.getBean("dog");
  }

  @Test
  public void dogTest() {
    assertEquals("danny", dog.getName());
  }
}
