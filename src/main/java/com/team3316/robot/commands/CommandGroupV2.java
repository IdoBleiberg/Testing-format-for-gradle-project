package com.team3316.robot.commands;

import java.util.LinkedList;
import java.util.List;
import java.util.function.Supplier;

import com.team3316.kit.DBugLogger;
import com.team3316.kit.config.ConfigException;

import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.command.IllegalUseOfCommandException;

/**
 * CommandGroupV2
 */
public abstract class CommandGroupV2 extends Command {
    List<List<Supplier<Command>>> queue = new LinkedList<>();
    List<Command> parallelsList;

    boolean canAdd = true;
    boolean isFinished = false;

    public CommandGroupV2() {

    }

    /**
     * starts the sequence
     */
    @Override
    public synchronized void start() {
        canAdd = false;
        super.start();
    }


    @Override
    protected void execute() {
      if (parallelsList == null) {
        this._runNextSequential();
      } else if (parallelsList.stream().allMatch(cmd -> cmd.isCompleted())) {
        DBugLogger.getInstance().info(parallelsList.toString());
        if (!queue.isEmpty()) {
          this._runNextSequential();
        } else {
          isFinished = true;
        }
      }
    }

    private void _runNextSequential() {
      List<Supplier<Command>> list = queue.remove(0);
      parallelsList = new LinkedList<>();
      for (Supplier<Command> sup : list) {
        Command cmd = sup.get();
        cmd.start();
        parallelsList.add(cmd);
      }
    }
    /**
     * @param args - appends a sequence of commands to run. Given as suppliers, each returning a new command instance. These will run simultaneously.
     *
     */
    @SafeVarargs
    public final synchronized void add(Supplier<Command>... args) throws ConfigException {
        List<Supplier<Command>> l = new LinkedList<>();

        for (int i = 0; i < args.length; i++) {
            l.add(args[i]);
        }

        if (canAdd) {
            queue.add(l);
        } else {
            throw new IllegalUseOfCommandException("cannot add after the command has been started");
        }
    }

    @Override
    protected boolean isFinished() {
      return isFinished;
    }

}
