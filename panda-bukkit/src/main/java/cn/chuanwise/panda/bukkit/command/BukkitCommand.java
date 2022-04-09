package cn.chuanwise.panda.bukkit.command;

import cn.chuanwise.command.context.DispatchContext;
import cn.chuanwise.command.util.Arguments;
import cn.chuanwise.common.util.Collections;
import cn.chuanwise.common.util.Preconditions;
import cn.chuanwise.common.util.Strings;
import lombok.Data;
import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.PluginIdentifiableCommand;
import org.bukkit.plugin.Plugin;

import java.util.List;

@Data
public class BukkitCommand
        extends Command
        implements PluginIdentifiableCommand {

    protected final BukkitCommander commander;

    protected BukkitCommand(BukkitCommander commander, String name, String description, String usage, List<String> aliases) {
        super(name);

        Preconditions.argumentNonEmpty(name, "command name");
        Preconditions.argumentNonNull(commander, "command lib");

        if (Strings.nonEmpty(description)) {
            setDescription(description);
        }

        if (Strings.nonEmpty(usage)) {
            setUsage(usage);
        }

        if (Collections.nonEmpty(aliases)) {
            setAliases(aliases);
        }

        this.commander = commander;
    }

    @Override
    public boolean execute(CommandSender commandSender, String alias, String[] arguments) {
        final List<String> finalArguments;
        if (arguments.length != 0) {
            finalArguments = Arguments.split(simpleAlias(alias) + " " + Arguments.merge(arguments));
        } else {
            finalArguments = Collections.asList(getName());
        }

        commander.execute(new DispatchContext(commander, commandSender, finalArguments));
        return true;
    }

    @Override
    public List<String> tabComplete(CommandSender commandSender, String alias, String[] arguments, Location location) throws IllegalArgumentException {
        // 寻找后面有无空白，有的话就是完整的
        final boolean uncompleted;

        final List<String> finalArguments;
        if (arguments.length != 0) {
            finalArguments = Arguments.split(simpleAlias(alias) + " " + Arguments.merge(arguments));
            uncompleted = Strings.nonEmpty(arguments[arguments.length - 1]);
        } else {
            finalArguments = Collections.asList(getName());
            uncompleted = false;
        }

        return commander.getCompleteService().sortedComplete(
            new DispatchContext(commander, commandSender, finalArguments),
            uncompleted
        );
    }

    @Override
    public Plugin getPlugin() {
        return commander.plugin;
    }

    private String simpleAlias(String alias) {
        final int pos = alias.indexOf(':');
        return pos == -1
                ? alias
                : alias.substring(pos + 1);
    }
}
