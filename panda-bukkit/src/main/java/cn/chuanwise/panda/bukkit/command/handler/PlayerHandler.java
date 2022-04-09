package cn.chuanwise.panda.bukkit.command.handler;

import cn.chuanwise.command.context.CompleteContext;
import cn.chuanwise.command.context.ParseContext;
import cn.chuanwise.command.handler.AbstractClassHandler;
import cn.chuanwise.common.space.Container;
import cn.chuanwise.panda.bukkit.command.BukkitCommander;
import lombok.Data;
import org.bukkit.entity.HumanEntity;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;

import java.util.Collections;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

@Data
public class PlayerHandler
    extends AbstractClassHandler<Player> {

    private static final PlayerHandler INSTANCE = new PlayerHandler();

    public static PlayerHandler getInstance() {
        return INSTANCE;
    }

    protected PlayerHandler() {}

    @Override
    protected Set<String> complete0(CompleteContext context) throws Exception {
        final BukkitCommander commander = (BukkitCommander) context.getCommander();
        final Plugin plugin = commander.getPlugin();

        final Set<String> playerNames = plugin.getServer()
                .getOnlinePlayers()
                .stream()
                .map(HumanEntity::getName)
                .collect(Collectors.toSet());

        if (context.getCommandSender() instanceof Player) {
            final String placeHolder = commander.getBukkitCommandLibConfiguration().getPlayerCompletePlaceHolder();
            playerNames.add(placeHolder);
        }

        return Collections.unmodifiableSet(playerNames);
    }

    @Override
    protected Container<Player> parse0(ParseContext context) throws Exception {
        final BukkitCommander commander = (BukkitCommander) context.getCommander();
        final Plugin plugin = commander.getPlugin();

        final String string = context.getParsingReferenceInfo().getString();
        final Object commandSender = context.getCommandSender();
        final String placeHolder = commander.getBukkitCommandLibConfiguration().getPlayerCompletePlaceHolder();
        if (commandSender instanceof Player && Objects.equals(string, placeHolder)) {
            final Player player = (Player) commandSender;
            return Container.of(player);
        }

        return Container.ofNotNull(plugin.getServer().getPlayer(string));
    }
}
