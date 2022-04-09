package cn.chuanwise.panda.bukkit.command;

import cn.chuanwise.command.object.AbstractCommanderObject;
import cn.chuanwise.common.util.Collections;
import cn.chuanwise.common.util.Preconditions;
import cn.chuanwise.panda.command.PlatformCommandBuilder;

import java.util.*;

/**
 * Bukkit 平台的指令构建器
 *
 * @author Chuanwise
 */
public class BukkitCommandBuilder
    extends AbstractCommanderObject
    implements PlatformCommandBuilder {

    protected String name;
    protected Set<String> aliases = new HashSet<>();

    protected String description;
    protected String permission;
    protected String usage;
    protected String permissionMessage;
    protected String label;

    public BukkitCommandBuilder(BukkitCommander commander) {
        super(commander);
    }

    @Override
    public BukkitCommandBuilder name(String name) {
        Preconditions.namedArgumentNonEmpty(name, "command name");

        this.name = name;
        return this;
    }

    @Override
    public BukkitCommandBuilder aliases(Collection<String> aliases) {
        Preconditions.namedArgumentNonEmpty(aliases, "command aliases");

        this.aliases.addAll(aliases);
        return this;
    }

    @Override
    public BukkitCommandBuilder aliases(String... aliases) {
        Preconditions.argument(Objects.nonNull(aliases) && aliases.length != 0, "command aliases is empty!");

        this.aliases.addAll(Arrays.asList(aliases));
        return this;
    }

    public BukkitCommandBuilder description(String description) {
        Preconditions.namedArgumentNonNull(description, "description");

        this.description = description;
        return this;
    }

    public BukkitCommandBuilder permission(String permission) {
        Preconditions.namedArgumentNonNull(description, "permission");

        this.permission = permission;
        return this;
    }

    public BukkitCommandBuilder usage(String usage) {
        Preconditions.namedArgumentNonNull(description, "usage");

        this.usage = usage;
        return this;
    }

    public BukkitCommandBuilder permissionMessage(String permissionMessage) {
        Preconditions.namedArgumentNonNull(description, "permission message");

        this.permissionMessage = permissionMessage;
        return this;
    }

    public BukkitCommandBuilder label(String label) {
        Preconditions.namedArgumentNonNull(description, "label");

        this.label = label;
        return this;
    }

    public BukkitCommand build() {
        Preconditions.namedArgumentNonEmpty(name, "command name");

        final BukkitCommand command = new BukkitCommand((BukkitCommander) commander, name, description, usage, Collections.asList(aliases));

        if (Objects.nonNull(permissionMessage)) {
            command.setPermissionMessage(permissionMessage);
        }

        if (Objects.nonNull(label)) {
            command.setLabel(label);
        }

        return command;
    }
}
