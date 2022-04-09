package cn.chuanwise.panda.bukkit;

import cn.chuanwise.command.annotation.*;
import cn.chuanwise.command.command.Command;
import cn.chuanwise.command.context.CompleteContext;
import cn.chuanwise.command.context.ParseContext;
import cn.chuanwise.command.context.WireContext;
import cn.chuanwise.command.tree.CommandTree;
import cn.chuanwise.panda.bukkit.command.BukkitCommander;
import cn.chuanwise.panda.bukkit.plugin.BukkitPlugin;
import cn.chuanwise.common.util.Collections;
import cn.chuanwise.common.util.Preconditions;
import org.bukkit.OfflinePlayer;
import org.bukkit.World;
import org.bukkit.command.CommandMap;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;

import java.util.Set;

public class BukkitPandaPlugin
    extends BukkitPlugin {

    private static BukkitPandaPlugin instance;
    private BukkitCommander commander;

    public static BukkitPandaPlugin getInstance() {
        Preconditions.stateNonNull(instance, "插件尚未加载");
        return instance;
    }

    public class Commands {
        @Format("pl online [online-player]")
        void player(CommandSender commandSender,
                    @Reference("online-player") Player player) {
            contact.infoString(commandSender, "player: " + player.getName());
        }

        @Format("pl offline [offline-player]")
        void onlinePlayer(CommandSender commandSender,
                          @Reference("offline-player") OfflinePlayer offlinePlayer) {
            contact.infoString(commandSender, "offline player: " + offlinePlayer.getName());
        }

        @Format("pl world [world]")
        void world(CommandSender commandSender,
                   @Reference("world") World world) {
            contact.infoString(commandSender, "world: " + world.getName());
        }

        @Format("pl debug")
        void debug(CommandSender commandSender) {
            final boolean setTo = !contact.isDebug();
            contact.setDebug(setTo);

            if (setTo) {
                contact.infoString(commandSender, "成功启动调试模式");
            } else {
                contact.infoString(commandSender, "成功关闭调试模式");
            }
        }

        @Format("pl test remain [remain~]")
        void testRemain(CommandSender commandSender,
                        @Reference("remain") String remain) {
            contact.infoString(commandSender, "remain = " + remain);
        }

        @Format("pl test nullable-remain [nullable-remain?~]")
        void testNullableRemain(CommandSender commandSender,
                                @Reference("nullable-remain") String remain) {
            contact.infoString(commandSender, "nullable-remain = " + remain);
        }

        @Format("pl op [-time|t] [-date|d=true|false] [-e?qwq]")
        void testOp1(CommandSender commandSender,
                     @Reference("t") String t,
                     @Reference("date") String date,
                     @Reference("e") String e) {
            contact.infoString(commandSender, "t = " + t + ", date = " + date + ", e = " + e);
        }

        @Format("pl op [-date|d=true|false] [-admin=true|false] [-e?qwq]")
        void testOp2(CommandSender commandSender,
                     @Reference("date") String date,
                     @Reference("e") String e,
                     @Reference("admin") String admin) {
            contact.infoString(commandSender, "admin = " + admin + ", date = " + date + ", e = " + e);
        }

        @Wirer(BukkitPandaPlugin.class)
        BukkitPandaPlugin provideBukkitPlugin(WireContext context) {
            return BukkitPandaPlugin.getInstance();
        }

        @Format("pl [-world]")
        void dd(CommandSender commandSender,
                @Reference("world") World world) {
            contact.infoString(commandSender, "world name = " + world.getName());
        }

        @Format("pl [player] say [msg~]")
        void s(CommandSender commandSender,
               @Reference("player") Player player,
               @Reference("msg") String msg) {
            contact.infoString(player, msg);
            contact.infoString(commandSender, "msg sent to " + player.getName());
        }

        @Format("pl [-plugin]")
        void dd(CommandSender commandSender,
                @Reference("plugin") Plugin plugin) {
            contact.infoString(commandSender, "plugin name = " + plugin.getName());
        }

        @Format("pl strong test")
        void strong(CommandSender commandSender) {
            contact.infoString(commandSender, "strong");
        }

        @Format("pl strong [test?~]")
        void week(CommandSender commandSender,
                  @Reference("test") String test) {
            contact.infoString(commandSender, "week, test = " + test);
        }

        @Format("pl complete-space [plugin] ha")
        void week(CommandSender commandSender,
                  @Reference("plugin") BukkitPandaPlugin plugin) {
            contact.infoString(commandSender, "completed");
        }

        @Completer(BukkitPandaPlugin.class)
        Set<String> complete(CompleteContext context) {
            return Collections.asUnmodifiableSet(
                    "test space arguments",
                    "space argument",
                    "space",
                    "plugin"
            );
        }

        @Parser
        BukkitPandaPlugin parse(ParseContext context) {
            contact.consoleDebugString("parse, ctx = " + context);
            return BukkitPandaPlugin.getInstance();
        }

        @EventHandler
        void handleEvent(Object event) {
            contact.consoleDebugString("event: " + event);
        }

        @ExceptionHandler
        void handleException(Throwable cause) {
            cause.printStackTrace();
        }

        @Format("pl help|h")
        void help(CommandSender sender) {
            final Set<Command> commands = commander.getCommandTree().getCommands();
            contact.infoString(sender, Collections.toString(commands, x -> "> " + x.getFormat(), "\n"));
        }
    }

    @Override
    protected void onLoad0() throws Exception {
        instance = this;
    }

    @Override
    protected void onEnable0() throws Exception {
        contact.setDebug(true);

        commander = BukkitCommander.of(this);

        commander.bootstrap()
                .name("pandalib")
                .aliases("pl")
                .object(new Commands())
                .register();
    
        final CommandTree commandTree = commander.getCommandTree();
        contact.consoleDebugString("sons: " + commandTree.getSons());
        contact.consoleDebugString("commands: " + commandTree.getCommands());

        final CommandMap commandMap = cn.chuanwise.panda.bukkit.util.Commands.getCommandMap();
        final org.bukkit.command.Command command = commandMap.getCommand("pandalib");
        contact.consoleDebugString("command: registered: " + command.isRegistered() + ", aliases: " + command.getAliases());
    }
}
