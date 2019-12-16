package com.team3316.robot.utils;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Files;

import com.team3316.kit.DBugLogger;
import com.team3316.robot.Robot;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.context.support.FileSystemXmlApplicationContext;

public class Utils {

  private static ApplicationContext _context;

  public static double scale(double x, Point a, Point b) {
    return ((x - a.x) * (a.y - b.y) / (a.x - b.x) + a.y);
  }

  public static Object getBean(String beanName) {
    if (_context == null) {
      if (Robot.isReal()) {
        _context = new FileSystemXmlApplicationContext("/home/lvuser/deploy/DeployApllicationContext.xml");
      } else _context = new ClassPathXmlApplicationContext("/TestApplicationContext.xml");
    }
    return _context.getBean(beanName);
  }

  
  public static boolean isInNeighborhood(double x, double L, double epsilon) {
    return Math.abs(x - L) < epsilon;
  }

  /*
   * returns a linear interpolation from a lookup table
   * assuming x=0 is for x values and x=1 is for y values
   * if the requiredIndex is lower than the min x value or higher than the max x value,
   * returns the minimum or maximum value accordingly
   */
  public static double valueInterpolation(double requiredIndex, double table[][]) {
    if (requiredIndex < table[0][0]) {
      return table[1][0];
    }
    if (requiredIndex > table[0][table[0].length - 1]) {
      return table[1][table[1].length - 1];
    }

    //binary search to find the appropriate indexes
    int bot = 0, top = table[0].length - 1;

    int mid = (bot + top) / 2;
    while (mid != bot) {
      if (table[0][mid] > requiredIndex) {
        top = mid;
        mid = (bot + top) / 2;
      } else {
        bot = mid;
        mid = (bot + top) / 2;
      }
    }

    

    //linear interpolation between the points in the lookup table
    double valueToReturn = scale(requiredIndex,
        new Point(table[0][bot], table[1][bot]),
        new Point(table[0][top], table[1][top]));
    return valueToReturn;
  }
}
