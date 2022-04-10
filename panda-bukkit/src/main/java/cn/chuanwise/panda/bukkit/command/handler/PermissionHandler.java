package cn.chuanwise.panda.bukkit.command.handler;

import cn.chuanwise.command.Commander;
import cn.chuanwise.command.command.Command;
import cn.chuanwise.command.command.CommandExecutor;
import cn.chuanwise.command.command.MethodCommandExecutor;
import cn.chuanwise.command.context.CompleteContext;
import cn.chuanwise.command.context.ParseContext;
import cn.chuanwise.command.event.CommandPreExecuteEvent;
import cn.chuanwise.command.event.CommandRegisterEvent;
import cn.chuanwise.command.event.EventHandler;
import cn.chuanwise.command.handler.AbstractClassHandler;
import cn.chuanwise.common.space.Container;
import cn.chuanwise.common.util.Strings;
import cn.chuanwise.panda.annotation.Required;
import cn.chuanwise.panda.bukkit.command.BukkitCommander;
import cn.chuanwise.panda.bukkit.command.event.BukkitLackPermissionEvent;
import cn.chuanwise.panda.command.Properties;
import cn.chuanwise.panda.command.event.AbstractLackPermissionEvent;
import lombok.Data;
import org.bukkit.command.CommandSender;
import org.bukkit.permissions.Permission;
import org.bukkit.plugin.Plugin;

import java.lang.reflect.Method;
import java.util.Collections;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

@Data
public class PermissionHandler
    extends AbstractClassHandler<Permission>
    implements EventHandler {

    private static final PermissionHandler INSTANCE = new PermissionHandler();

    public static PermissionHandler getInstance() {
        return INSTANCE;
    }

    protected PermissionHandler() {}

    @Override
    public boolean handleEvent(Object event) throws Exception {
        if (event instanceof CommandPreExecuteEvent) {
            final CommandPreExecuteEvent commandPreExecuteEvent = (CommandPreExecuteEvent) event;
            final CommandSender commandSender = (CommandSender) commandPreExecuteEvent.getCommandSender();

            // check permission
            final Command command = commandPreExecuteEvent.getCommand();
            final String permission = command.getProperty(Properties.PERMISSION);
            if (Objects.nonNull(permission)) {
                if (!commandSender.hasPermission(permission)) {
                    
                    // 发送权限消息
                    final String permissionMessage = command.getProperty(Properties.PERMISSION_MESSAGE);
                    final Commander commander = commandPreExecuteEvent.getCommander();
                    final AbstractLackPermissionEvent abstractLackPermissionEvent = new BukkitLackPermissionEvent(
                        commander,
                        commandPreExecuteEvent.getCommand(),
                        commandSender,
                        commandPreExecuteEvent.getReferenceInfo(),
                        permission,
                        permissionMessage
                    );
    
                    final boolean handled = commander.getEventService().broadcastEvent(abstractLackPermissionEvent);
                    if (!abstractLackPermissionEvent.isCancelled()) {
                        commandPreExecuteEvent.setCancelled(true);
                    }
    
                    if (!handled && Strings.nonEmpty(permissionMessage)) {
                        commandSender.sendMessage(permissionMessage);
                    }
                }
            }
            
            return true;
        }
        if (event instanceof CommandRegisterEvent) {
            final CommandRegisterEvent commandRegisterEvent = (CommandRegisterEvent) event;
            final Command command = commandRegisterEvent.getCommand();
            final CommandExecutor executor = command.getExecutor();

            if (executor instanceof MethodCommandExecutor) {
                final MethodCommandExecutor methodCommandExecutor = (MethodCommandExecutor) executor;
                final Method method = methodCommandExecutor.getMethod();

                final Required required = method.getAnnotation(Required.class);
                if (Objects.nonNull(required)) {
                    command.setProperty(Properties.PERMISSION, required.value());
                }
            }
            
            return true;
        }
        return false;
    }

    @Override
    protected Set<String> complete0(CompleteContext context) throws Exception {
        final BukkitCommander commander = (BukkitCommander) context.getCommander();
        final Plugin plugin = commander.getPlugin();

        return Collections.unmodifiableSet(
                plugin.getServer()
                        .getPluginManager()
                        .getPermissions()
                        .stream()
                        .map(Objects::toString)
                        .collect(Collectors.toSet())
        );
    }

    @Override
    protected Container<Permission> parse0(ParseContext context) throws Exception {
        final BukkitCommander commander = (BukkitCommander) context.getCommander();
        final Plugin plugin = commander.getPlugin();

        return Container.ofNotNull(plugin.getServer()
                .getPluginManager()
                .getPermission(context.getParsingReferenceInfo().getString()));
    }
}
