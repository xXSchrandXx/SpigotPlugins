package de.xxschrandxx.bca.bungee.api.task;

import java.util.UUID;
import java.util.concurrent.TimeUnit;

import de.xxschrandxx.bca.bungee.BungeeCordAuthenticatorBungee;
import net.md_5.bungee.api.scheduler.ScheduledTask;

public class SessionTask {

    private ScheduledTask task;

  public SessionTask(BungeeCordAuthenticatorBungee bcab, UUID uuid) {
    task = bcab.getProxy().getScheduler().schedule(bcab, new Runnable() {
      @Override
      public void run() {
        Integer sessionlength = bcab.getAPI().getConfigHandler().SessionLength;
        //TODO task
      }
    }, 10, 5, TimeUnit.SECONDS);
  }

  public void cancel() {
    if (task != null)
      task.cancel();
  }

}
