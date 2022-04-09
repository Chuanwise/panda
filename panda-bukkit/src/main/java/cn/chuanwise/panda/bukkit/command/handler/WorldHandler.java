package cn.chuanwise.panda.bukkit.command.handler;

import cn.chuanwise.command.context.CompleteContext;
import cn.chuanwise.command.context.ParseContext;
import cn.chuanwise.command.handler.AbstractClassHandler;
import cn.chuanwise.common.space.Container;
import cn.chuanwise.panda.bukkit.command.BukkitCommander;
import lombok.Data;
import org.bukkit.World;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;

import java.util.Collections;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

@Data
public class WorldHandler
    extends AbstractClassHandler<World> {

    private static final WorldHandler INSTANCE = new WorldHandler();

    public static WorldHandler getInstance() {
        return INSTANCE;
    }

    protected WorldHandler() {}

    @Override
    protected Set<String> complete0(CompleteContext context) throws Exception {
        final BukkitCommander commander = (BukkitCommander) context.getCommander();
        final Plugin plugin = commander.getPlugin();

        final Set<String> worldNames = plugin.getServer()
                .getWorlds()
                .stream()
                .map(World::getName)
                .collect(Collectors.toSet());

        final Object commandSender = context.getCommandSender();
        if (commandSender instanceof Player) {
            final String placeHolder = commander.getBukkitCommandLibConfiguration().getWorldCompletePlaceHolder();
            worldNames.add(placeHolder);
        }

        return Collections.unmodifiableSet(worldNames);
    }

    @Override
    protected Container<World> parse0(ParseContext context) throws Exception {
        final BukkitCommander commander = (BukkitCommander) context.getCommander();
        final Plugin plugin = commander.getPlugin();

        final String string = context.getParsingReferenceInfo().getString();
        final String placeHolder = commander.getBukkitCommandLibConfiguration().getWorldCompletePlaceHolder();
        final Object commandSender = context.getCommandSender();
        if (commandSender instanceof Player && Objects.equals(string, placeHolder)) {
            final Player player = (Player) commandSender;
            return Container.of(player.getWorld());
        }

        return Container.ofNotNull(plugin.getServer().getWorld(string));
    }
}
