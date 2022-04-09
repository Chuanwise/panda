package cn.chuanwise.panda.bukkit.scheduler;

import cn.chuanwise.panda.bukkit.plugin.BukkitPlugin;
import cn.chuanwise.panda.bukkit.plugin.BukkitPluginObject;
import cn.chuanwise.panda.scheduler.Scheduler;
import lombok.Getter;

import java.util.concurrent.Callable;
import java.util.concurrent.Future;

@Getter
public class BukkitScheduler
    extends BukkitPluginObject<BukkitPlugin>
    implements Scheduler {
    
    protected final org.bukkit.scheduler.BukkitScheduler bukkitScheduler = server().getScheduler();

    public BukkitScheduler(BukkitPlugin plugin) {
        super(plugin);
    }

    @Override
    public void runTask(Runnable action) {
        bukkitScheduler.runTask(plugin, action);
    }

    @Override
    public void runAsyncTask(Runnable action) {
        bukkitScheduler.runTaskAsynchronously(plugin, action);
    }

    @Override
    public <T> Future<T> runAsyncTask(Callable<T> action) {
        return bukkitScheduler.callSyncMethod(plugin, action);
    }

    @Override
    public void shutdown() {}
}