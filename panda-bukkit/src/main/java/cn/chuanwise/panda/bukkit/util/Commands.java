package cn.chuanwise.panda.bukkit.util;

import cn.chuanwise.common.util.Preconditions;
import cn.chuanwise.common.util.Reflections;
import cn.chuanwise.common.util.StaticUtilities;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandMap;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.PluginManager;

import java.util.NoSuchElementException;
import java.util.Objects;

public class Commands
    extends StaticUtilities {
    
    private static volatile CommandMap commandMap;

    public static CommandMap getCommandMap() {
        if (Objects.isNull(commandMap)) {
            final PluginManager pluginManager = Bukkit.getServer().getPluginManager();
            commandMap = (CommandMap) Reflections.getExistFieldValue(pluginManager, "commandMap");
            Preconditions.stateNonNull(commandMap);
        }

        return commandMap;
    }

    /**
     * 动态注册一个指令
     * @param command 指令
     * @param plugin 插件
     */
    public static void registerCommand(Command command, Plugin plugin) {
        Preconditions.namedNonNull(command, "command");
        Preconditions.stateNonNull(plugin, "plugin is null");

        getCommandMap().register(command.getName(), command);
    }
}
