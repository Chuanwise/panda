package cn.chuanwise.panda.bukkit.command.handler;

import cn.chuanwise.command.context.CompleteContext;
import cn.chuanwise.command.context.ParseContext;
import cn.chuanwise.command.handler.AbstractClassHandler;
import cn.chuanwise.common.space.Container;
import cn.chuanwise.panda.bukkit.command.BukkitCommander;
import lombok.Data;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;

import java.util.Collections;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Data
public class OfflinePlayerHandler
    extends AbstractClassHandler<OfflinePlayer> {

    private static final OfflinePlayerHandler INSTANCE = new OfflinePlayerHandler();

    public static OfflinePlayerHandler getInstance() {
        return INSTANCE;
    }

    protected OfflinePlayerHandler() {
        super(OfflinePlayer.class);
    }

    @Override
    protected Set<String> complete0(CompleteContext context) throws Exception {
        final BukkitCommander commander = (BukkitCommander) context.getCommander();
        final Plugin plugin = commander.getPlugin();

        final Set<String> offlinePlayerNames = Stream.of(plugin.getServer()
                        .getOfflinePlayers())
                .map(OfflinePlayer::getName)
                .collect(Collectors.toSet());

        if (context.getCommandSender() instanceof Player) {
            final String placeHolder = commander.getBukkitCommandLibConfiguration().getOfflinePlayerCompletePlaceHolder();
            offlinePlayerNames.add(placeHolder);
        }

        return Collections.unmodifiableSet(offlinePlayerNames);
    }

    @Override
    protected Container<OfflinePlayer> parse0(ParseContext context) throws Exception {
        final BukkitCommander commander = (BukkitCommander) context.getCommander();
        final Plugin plugin = commander.getPlugin();

        final String string = context.getParsingReferenceInfo().getString();
        final Object commandSender = context.getCommandSender();
        final String placeHolder = commander.getBukkitCommandLibConfiguration().getOfflinePlayerCompletePlaceHolder();
        if (commandSender instanceof Player && Objects.equals(string, placeHolder)) {
            final Player player = (Player) commandSender;
            return Container.of(player);
        }

        return Container.ofNotNull(plugin.getServer().getOfflinePlayer(string));
    }
}
