package cn.chuanwise.panda.bukkit.plugin;

import cn.chuanwise.panda.bukkit.contact.Contact;
import cn.chuanwise.panda.plugin.PluginObject;
import cn.chuanwise.common.util.Preconditions;
import cn.chuanwise.common.util.Reflections;
import cn.chuanwise.common.util.Types;
import org.bukkit.Server;
import org.bukkit.command.ConsoleCommandSender;

import java.beans.Transient;
import java.lang.reflect.Method;
import java.util.Optional;

public class BukkitPluginObject<T extends BukkitPlugin>
        implements PluginObject<T> {

    protected transient final T plugin;

    public BukkitPluginObject(T plugin) {
        Preconditions.namedNonNull(plugin, "plugin");
        this.plugin = plugin;
    }

    @Override
    @Transient
    public T plugin() {
        return plugin;
    }

    public ConsoleCommandSender consoleSender() {
        return server().getConsoleSender();
    }

    public Server server() {
        return plugin.getServer();
    }

    public Contact contact() {
        return plugin.contact;
    }
}