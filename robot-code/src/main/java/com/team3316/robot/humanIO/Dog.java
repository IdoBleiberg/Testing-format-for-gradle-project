package com.team3316.robot.humanIO;

import com.team3316.robot.subsystems.Animal;

import org.springframework.stereotype.Component;

public class Dog extends Animal{
  private String _name = "danny";
  
  public Dog() {

  }

  public String getName() {
    return this._name;
  }

}