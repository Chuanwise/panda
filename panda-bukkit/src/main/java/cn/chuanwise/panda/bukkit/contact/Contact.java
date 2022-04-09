package cn.chuanwise.panda.bukkit.contact;

import cn.chuanwise.panda.bukkit.plugin.BukkitPlugin;
import cn.chuanwise.panda.bukkit.plugin.BukkitPluginObject;
import cn.chuanwise.panda.storage.Language;
import cn.chuanwise.common.util.Preconditions;
import lombok.Data;
import lombok.NonNull;
import org.bukkit.command.CommandSender;

@SuppressWarnings("all")
@Data
public class Contact extends BukkitPluginObject {
    @NonNull
    protected final Language language;

    protected boolean debug = false;

    public Contact(BukkitPlugin plugin, Language language) {
        super(plugin);
        this.language = language;
    }

    public void consoleInfo(String path, Object... arguments) {
        info(consoleSender(), path, arguments);
    }

    public void consoleDebug(String path, Object... arguments) {
        debug(consoleSender(), path, arguments);
    }

    public void consoleWarn(String path, Object... arguments) {
        warn(consoleSender(), path, arguments);
    }

    public void consoleError(String path, Object... arguments) {
        error(consoleSender(), path, arguments);
    }

    public void consoleSuccess(String path, Object... arguments) {
        success(consoleSender(), path, arguments);
    }

    public void consoleInfoString(String message, Object... arguments) {
        infoString(consoleSender(), language.format(message, arguments));
    }

    public void consoleWarnString(String message, Object... arguments) {
        warnString(consoleSender(), language.format(message, arguments));
    }

    public void consoleErrorString(String message, Object... arguments) {
        errorString(consoleSender(), language.format(message, arguments));
    }

    public void consoleDebugString(String message, Object... arguments) {
        debugString(consoleSender(), language.format(message, arguments));
    }

    public void consoleSuccessString(String message, Object... arguments) {
        successString(consoleSender(), language.format(message, arguments));
    }

    public void info(CommandSender sender, String node, Object... arguments) {
        Preconditions.nonNull(sender, "command sender");
        Preconditions.nonNull(sender, "node");
        Preconditions.nonNull(arguments, "arguments");

        sender.sendMessage(language.formatInfoNode(node, arguments));
    }

    public void warn(CommandSender sender, String node, Object... arguments) {
        Preconditions.nonNull(sender, "command sender");
        Preconditions.nonNull(sender, "node");
        Preconditions.nonNull(arguments, "arguments");

        sender.sendMessage(language.formatWarnNode(node, arguments));
    }

    public void debug(CommandSender sender, String node, Object... arguments) {
        Preconditions.nonNull(sender, "command sender");
        Preconditions.nonNull(sender, "node");
        Preconditions.nonNull(arguments, "arguments");

        sender.sendMessage(language.formatDebugNode(node, arguments));
    }

    public void error(CommandSender sender, String node, Object... arguments) {
        Preconditions.nonNull(sender, "command sender");
        Preconditions.nonNull(sender, "node");
        Preconditions.nonNull(arguments, "arguments");

        sender.sendMessage(language.formatErrorNode(node, arguments));
    }

    public void success(CommandSender sender, String node, Object... arguments) {
        Preconditions.nonNull(sender, "command sender");
        Preconditions.nonNull(sender, "node");
        Preconditions.nonNull(arguments, "arguments");

        sender.sendMessage(language.formatSuccessNode(node, arguments));
    }

    public void infoString(CommandSender sender, String node) {
        Preconditions.nonNull(sender, "command sender");
        Preconditions.nonNull(sender, "node");

        sender.sendMessage(language.formatInfo(node));
    }

    public void debugString(CommandSender sender, String node) {
        if (!debug) {
            return;
        }

        Preconditions.nonNull(sender, "command sender");
        Preconditions.nonNull(sender, "node");

        sender.sendMessage(language.formatDebug(node));
    }

    public void errorString(CommandSender sender, String node) {
        Preconditions.nonNull(sender, "command sender");
        Preconditions.nonNull(sender, "node");

        sender.sendMessage(language.formatError(node));
    }

    public void warnString(CommandSender sender, String node) {
        Preconditions.nonNull(sender, "command sender");
        Preconditions.nonNull(sender, "node");

        sender.sendMessage(language.formatWarn(node));
    }

    public void successString(CommandSender sender, String node) {
        Preconditions.nonNull(sender, "command sender");
        Preconditions.nonNull(sender, "node");

        sender.sendMessage(language.formatSuccess(node));
    }
}
