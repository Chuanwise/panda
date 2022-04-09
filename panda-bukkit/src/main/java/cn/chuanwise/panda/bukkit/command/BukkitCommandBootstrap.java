package cn.chuanwise.panda.bukkit.command;

import cn.chuanwise.command.configuration.CommandInfoConfiguration;
import cn.chuanwise.command.handler.CommandInfoConfigurationEventHandler;
import cn.chuanwise.command.object.AbstractCommanderObject;
import cn.chuanwise.command.tree.CommandTreeNode;
import cn.chuanwise.command.tree.CommandTree;
import cn.chuanwise.command.tree.PlainTextsCommandTreeNode;
import cn.chuanwise.panda.bukkit.util.Commands;
import cn.chuanwise.common.util.Collections;
import cn.chuanwise.common.util.Preconditions;

import java.util.*;

public class BukkitCommandBootstrap
    extends AbstractCommanderObject {

    protected BukkitCommandBuilder builder;

    public BukkitCommandBootstrap(BukkitCommander commander) {
        super(commander);

        this.builder = new BukkitCommandBuilder(commander);
    }

    public BukkitCommandBootstrap name(String name) {
        builder.name(name);
        return this;
    }

    public BukkitCommandBootstrap aliases(String... aliases) {
        builder.aliases(aliases);
        return this;
    }

    public BukkitCommandBootstrap aliases(Collection<String> aliases) {
        builder.aliases(aliases);
        return this;
    }

    public BukkitCommandBootstrap description(String description) {
        builder.description(description);
        return this;
    }

    public BukkitCommandBootstrap usage(String usage) {
        builder.usage(usage);
        return this;
    }

    public BukkitCommandBootstrap permission(String permission) {
        builder.permission(permission);
        return this;
    }

    public BukkitCommandBootstrap label(String label) {
        builder.label(label);
        return this;
    }

    public BukkitCommandBootstrap permissionMessage(String permissionMessage) {
        builder.permissionMessage(permissionMessage);
        return this;
    }

    public BukkitCommandBootstrap object(Object object) {
        Preconditions.argumentNonNull(object, "commands");

        commander.register(object);

        return this;
    }

    public BukkitCommandBootstrap object(Object object, CommandInfoConfiguration configuration) {
        Preconditions.argumentNonNull(object, "commands");
        Preconditions.argumentNonNull(configuration, "configuration");

        commander.getEventService().registerEventHandler(new CommandInfoConfigurationEventHandler(configuration));
        commander.register(object);

        return this;
    }

    public void register() {
        final BukkitCommand command = builder.build();
        final BukkitCommander commander = (BukkitCommander) this.commander;

        final String name = command.getName();
        final List<String> aliases = new ArrayList<>(command.getAliases());
        final CommandTree commandTree = commander.getCommandTree();

        Commands.registerCommand(command, commander.plugin);

        PlainTextsCommandTreeNode node = null;
        final List<CommandTreeNode> sons = commandTree.getSons();
        for (int i = 0; i < sons.size(); i++) {
            final CommandTreeNode son = sons.get(i);

            if (son instanceof PlainTextsCommandTreeNode) {
                final PlainTextsCommandTreeNode plainTextsCommandTree = (PlainTextsCommandTreeNode) son;
                final List<String> texts = plainTextsCommandTree.getTexts();
                if (texts.contains(name) || Collections.isIntersected(texts, aliases)) {
                    // 合并指令分支
                    if (Objects.nonNull(node)) {
                        sons.remove(i);
                        i--;
                        node.merge(plainTextsCommandTree);
                    }
                    node = plainTextsCommandTree;
                }
            }
        }

        Preconditions.stateNonNull(node, "没有发现任何 @Format 中由 " + name + "（或任一别名 " + aliases + "）开头的指令方法");
        Collections.addDistinctly(node.getTexts(), name);
    }
}
