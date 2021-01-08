package de.xxschrandxx.bca.bungee.api.task;

import java.sql.SQLException;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

import de.xxschrandxx.bca.bungee.BungeeCordAuthenticatorBungee;
import net.md_5.bungee.api.scheduler.ScheduledTask;

public class SessionTask {

  private final ScheduledTask task;
  private Integer sessionlength = 0;

  public SessionTask(BungeeCordAuthenticatorBungee bcab, UUID uuid) {
    Integer maxsessionlength = bcab.getAPI().getConfigHandler().SessionLength;
    task = bcab.getProxy().getScheduler().schedule(bcab, new Runnable() {
      @Override
      public void run() {
        if (sessionlength <= maxsessionlength) {
          try {
            bcab.getAPI().unsetOpenSession(uuid);
          }
          catch (SQLException e) {
            e.printStackTrace();
          }
        }
        else {
          sessionlength++;
        }
      }
    }, 0, 1, TimeUnit.MINUTES);
  }

  public void cancel() {
    if (task != null)
      task.cancel();
  }

}
