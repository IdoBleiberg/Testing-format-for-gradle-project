package com.team3316.robot;

import com.team3316.robot.humanIO.Joysticks;
import com.team3316.robot.humanIO.SDB;
import edu.wpi.first.wpilibj.TimedRobot;
import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.command.Scheduler;
import edu.wpi.first.wpilibj.livewindow.LiveWindow;
import java.util.Timer;

/**
 * The VM is configured to automatically run this class, and to call the
 * functions corresponding to each mode, as described in the TimedRobot
 * documentation. If you change the name of this class or the package after
 * creating this project, you must also update the manifest file in the resource
 * directory.
 */
public class Robot extends TimedRobot {
  public static Timer timer;

  /*
   * Human IO
   */
  public static Joysticks joysticks;
  public static SDB sdb;

  /*
   * Subsystems
   */

  Command autonomousCommand;

  /**
   * This function is run when the robot is first started up and should be
   * used for any initialization code.
   */
  public void robotInit () {
    /*
     * Above all else
     */
    timer = new Timer();

    /*
     * Human IO (that does not require subsystems)
     */
    joysticks = new Joysticks();

    /*
     * Subsystems
     */

    /*
     * Human IO (that requires subsystems)
     */
    joysticks.initButtons();
    sdb = new SDB();
  }

  public void disabledPeriodic () {
    Scheduler.getInstance().run();
  }

  public void autonomousInit () {
    if (autonomousCommand != null) autonomousCommand.start();
  }

  public void autonomousPeriodic () {
    Scheduler.getInstance().run();
  }

  public void teleopInit () {
    if (autonomousCommand != null) autonomousCommand.cancel();
  }

  public void disabledInit () {

  }

  public void teleopPeriodic () {
    Scheduler.getInstance().run();
  }

  public void testPeriodic () {
    LiveWindow.run();
  }

  private void printTheTruth () {
    System.out.println("Vita is the Melech!!");
  }
}
