package cn.chuanwise.panda.bukkit.command;

import cn.chuanwise.command.wirer.CommandSenderWrier;
import cn.chuanwise.common.util.Preconditions;
import cn.chuanwise.panda.bukkit.command.configuration.BukkitCommandLibConfiguration;
import cn.chuanwise.panda.bukkit.command.handler.*;
import cn.chuanwise.panda.command.PandaCommander;
import lombok.Data;
import org.bukkit.command.CommandSender;
import org.bukkit.plugin.Plugin;

import java.util.Map;
import java.util.Objects;
import java.util.concurrent.ConcurrentHashMap;

@Data
public class BukkitCommander
    extends PandaCommander {

    protected final Plugin plugin;
    protected BukkitCommandLibConfiguration bukkitCommandLibConfiguration = new BukkitCommandLibConfiguration();

    protected static final Map<String, BukkitCommander> INSTANCES = new ConcurrentHashMap<>();
    
    public BukkitCommandBootstrap bootstrap() {
        return new BukkitCommandBootstrap(this);
    }

    @Override
    public BukkitCommandBuilder builder() {
        return new BukkitCommandBuilder(this);
    }

    protected BukkitCommander(Plugin plugin) {
        Preconditions.argumentNonNull(plugin, "plugin");

        this.plugin = plugin;

        wireService.registerWirer(new CommandSenderWrier<>(CommandSender.class));
        
        completeService.registerCompleter(OfflinePlayerHandler.getInstance());
        parseService.registerParser(OfflinePlayerHandler.getInstance());
        
        completeService.registerCompleter(PermissionHandler.getInstance());
        parseService.registerParser(PermissionHandler.getInstance());
        
        completeService.registerCompleter(PlayerHandler.getInstance());
        parseService.registerParser(PlayerHandler.getInstance());
        
        completeService.registerCompleter(PluginHandler.getInstance());
        parseService.registerParser(PluginHandler.getInstance());
        
        completeService.registerCompleter(WorldHandler.getInstance());
        parseService.registerParser(WorldHandler.getInstance());
        
        completeService.registerCompleter(ScoreBoardHandler.getInstance());
        parseService.registerParser(ScoreBoardHandler.getInstance());
    }

    public static BukkitCommander of(Plugin plugin) {
        Preconditions.argumentNonNull(plugin, "plugin");

        final String pluginName = plugin.getName();
        BukkitCommander commander = INSTANCES.get(pluginName);
        if (Objects.isNull(commander)) {
            commander = new BukkitCommander(plugin);
            INSTANCES.put(pluginName, commander);
        }

        return commander;
    }
}
