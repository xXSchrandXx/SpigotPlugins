package de.xxschrandxx.bca.bungee.api.task;

import java.util.concurrent.TimeUnit;

import de.xxschrandxx.bca.bungee.BungeeCordAuthenticatorBungee;
import net.md_5.bungee.api.chat.TextComponent;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.scheduler.ScheduledTask;

public class UnauthedTask {

  private final ScheduledTask task;

  public UnauthedTask(BungeeCordAuthenticatorBungee bcab, ProxiedPlayer player) {
    task = bcab.getProxy().getScheduler().schedule(bcab, new Runnable() {
      @Override
      public void run() {
        player.disconnect(new TextComponent(bcab.getAPI().getConfigHandler().UnauthenticatedKickMessage));
      }
    }, bcab.getAPI().getConfigHandler().UnauthenticatedKickLength, TimeUnit.MINUTES);
  }

  public void cancel() {
    if (task != null)
      task.cancel();
  }

}
