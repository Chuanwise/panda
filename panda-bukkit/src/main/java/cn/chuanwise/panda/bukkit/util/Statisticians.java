package cn.chuanwise.panda.bukkit.util;

import cn.chuanwise.common.util.StaticUtilities;
import cn.chuanwise.panda.bukkit.event.BukkitTpsEvent;
import cn.chuanwise.common.util.Preconditions;
import org.bukkit.Bukkit;
import org.bukkit.plugin.Plugin;

public class Statisticians
    extends StaticUtilities {
    
    private static volatile Plugin tpsHandlerPlugin;
    private static volatile int callEventDelay = 300;
    private static volatile int tps;

    public static boolean isTpsHandlerRegistered() {
        return tpsHandlerPlugin != null;
    }

    public static int getCallEventDelay() {
        return callEventDelay;
    }

    public static void setCallEventDelay(int callEventDelay) {
        Preconditions.argument(callEventDelay > 0, "call event delay must be greater than 0!");
        Statisticians.callEventDelay = callEventDelay;
    }

    public void registerTpsHandler(Plugin plugin) {
        Preconditions.argumentNonNull(plugin, "plugin is null");
        if (isTpsHandlerRegistered()) {
            return;
        }
        tpsHandlerPlugin = plugin;

        Bukkit.getServer().getScheduler().scheduleSyncRepeatingTask(plugin, new Runnable() {
            long sec;
            long currentSec;
            int ticks;
            int delay;

            @Override
            public void run() {
                sec = (System.currentTimeMillis() / 1000);

                if (currentSec == sec) {
                    // this code block triggers each tick

                    ticks++;
                } else {
                    // this code block triggers each second

                    currentSec = sec;
                    tps = (tps == 0 ? ticks : ((tps + ticks) / 2));
                    ticks = 0;

                    if ((++delay % 300) == 0) {
                        // this code block triggers each 5 minutes

                        delay = 0;

                        Bukkit.getServer().getPluginManager().callEvent(new BukkitTpsEvent(tps));
                    }
                }
            }
        }, 0, 1);
    }
}