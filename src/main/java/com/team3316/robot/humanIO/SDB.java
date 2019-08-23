/**
 * Class for managing the SmartDashboard data
 */
package com.team3316.robot.humanIO;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import java.util.Hashtable;
import java.util.Map.Entry;
import java.util.Set;
import java.util.TimerTask;

import com.team3316.kit.DBugLogger;
import com.team3316.kit.config.Config;
import com.team3316.kit.config.ConfigException;
import com.team3316.robot.Robot;

public class SDB {
  /*
   * Runnable that periodically updates values from the robot into the SmartDashboard
   * This is the place where all of the robot data should be displayed from
   */
  private class UpdateSDBTask extends TimerTask {
    public UpdateSDBTask () {
      DBugLogger.getInstance().info("Created UpdateSDBTask");
    }

    public void run () {
      /*
       * Insert put methods here
       */
    }

    private void put (String name, double d) {
      SmartDashboard.putNumber(name, d);
    }

    private void put (String name, int i) {
      SmartDashboard.putNumber(name, i);
    }

    private void put (String name, boolean b) {
      SmartDashboard.putBoolean(name, b);
    }

    private void put (String name, String s) {
      SmartDashboard.putString(name, s);
    }
  }

  private UpdateSDBTask updateSDBTask;

  private Hashtable<String, Class<?>> variablesInSDB;

  public SDB () {
    variablesInSDB = new Hashtable<String, Class<?>>();

    initSDB();
    timerInit();
  }

  public void timerInit () {
    updateSDBTask = new UpdateSDBTask();
    Robot.timer.schedule(updateSDBTask, 0, 100);
  }

  /**
   * Adds a certain key in the config to the SmartDashboard
   *
   * @param key the key required
   * @return whether the value was put in the SmartDashboard
   */
  public boolean putConfigVariableInSDB (String key) {
    try {
      Object value = Config.getInstance().get(key);
      Class<?> type = value.getClass();

      boolean constant = Character.isUpperCase(key.codePointAt(0));

      if (type == Double.class) {
        SmartDashboard.putNumber(key, (double) value);
      } else if (type == Integer.class) {
        SmartDashboard.putNumber(key, (int) value);
      } else if (type == Boolean.class) {
        SmartDashboard.putBoolean(key, (boolean) value);
      }

      if (!constant) {
        variablesInSDB.put(key, type);
        DBugLogger.getInstance().info("Added to SDB " + key + " of type " + type +
            "and allows for its modification");
      } else {
        DBugLogger.getInstance().info("Added to SDB " + key + " of type " + type +
            "BUT DOES NOT ALLOW for its modification");
      }

      return true;
    } catch (ConfigException e) {
      DBugLogger.getInstance().severe(e);
    }
    return false;
  }

  public Set<Entry<String, Class<?>>> getVariablesInSDB () {
    return variablesInSDB.entrySet();
  }

  private void initSDB () {
    SmartDashboard.putData(new UpdateVariablesInConfig()); //NEVER REMOVE THIS COMMAND

    DBugLogger.getInstance().info("Finished initSDB()");
  }
}
