package com.team3316.robot.commands;

import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.function.Supplier;

import com.team3316.kit.DBugLogger;
import com.team3316.kit.config.ConfigException;

import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.command.IllegalUseOfCommandException;

/**
 * CommandGroupV2
 */
public abstract class CommandGroupV2 extends Command{
    List<List<Supplier<Command>>> queue = new LinkedList<>();

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
        ExecutorService es = null;
        for (List<Supplier<Command>> list : queue) {
            es =  Executors.newCachedThreadPool();
            DBugLogger.getInstance().info("SEQUENTIAL HAS STARTED");
            for (Supplier<Command> sup : list) {
                es.submit(() -> {
                    Command cmd = sup.get();
                    cmd.start();
                    boolean wasRunning = false;
                    while (!(!cmd.isRunning() || wasRunning)) {
                      DBugLogger.getInstance().info("INWHILE: " + cmd.isRunning() + ", " + wasRunning);
                      if (cmd.isRunning()) wasRunning = true;
                    }
                });
            }
            es.shutdown();
            try {
              es.awaitTermination(Long.MAX_VALUE, TimeUnit.MILLISECONDS);
              DBugLogger.getInstance().info("SEQUENTIAL HAS ENDED BEFORE TIMEOUT");
            } catch (InterruptedException e) {
              DBugLogger.getInstance().info("SEQUENTIAL HASN'T ENDED UNTIL TIMEOUT");
            }
        }
        isFinished = true;
    }

    /**
     * @param args - appends a sequence of commands to run. Given as suppliers, each returning a new command instance. These will run simultaneously.
     *
     */
    @SafeVarargs
    public final synchronized void add(Supplier<Command>... args) throws ConfigException{
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
