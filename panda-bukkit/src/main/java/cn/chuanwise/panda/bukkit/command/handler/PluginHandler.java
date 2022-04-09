package cn.chuanwise.panda.bukkit.command.handler;

import cn.chuanwise.command.context.CompleteContext;
import cn.chuanwise.command.context.ParseContext;
import cn.chuanwise.command.handler.AbstractClassHandler;
import cn.chuanwise.panda.bukkit.command.BukkitCommander;
import cn.chuanwise.common.space.Container;
import lombok.Data;
import org.bukkit.plugin.Plugin;

import java.util.Arrays;
import java.util.Collections;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

@Data
public class PluginHandler
    extends AbstractClassHandler<Plugin> {

    private static final PluginHandler INSTANCE = new PluginHandler();

    public static PluginHandler getInstance() {
        return INSTANCE;
    }

    protected PluginHandler() {}

    @Override
    public Set<String> complete0(CompleteContext context) throws Exception {
        final BukkitCommander commander = (BukkitCommander) context.getCommander();
        final Plugin plugin = commander.getPlugin();

        final Set<String> pluginNames = Arrays.stream(plugin.getServer()
                        .getPluginManager()
                        .getPlugins())
                .map(Plugin::getName)
                .collect(Collectors.toSet());

        final String placeHolder = commander.getBukkitCommandLibConfiguration().getPluginCompletePlaceHolder();
        pluginNames.add(placeHolder);

        return Collections.unmodifiableSet(
                pluginNames
        );
    }

    @Override
    protected Container<Plugin> parse0(ParseContext context) throws Exception {
        final BukkitCommander commander = (BukkitCommander) context.getCommander();
        final Plugin plugin = commander.getPlugin();

        final String string = context.getParsingReferenceInfo().getString();

        final String placeHolder = commander.getBukkitCommandLibConfiguration().getPluginCompletePlaceHolder();
        if (Objects.equals(string, placeHolder)) {
            return Container.of(plugin);
        }

        return Container.ofNotNull(plugin.getServer()
                .getPluginManager()
                .getPlugin(string));
    }
}
