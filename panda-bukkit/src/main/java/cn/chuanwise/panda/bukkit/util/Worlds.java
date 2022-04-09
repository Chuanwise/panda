package cn.chuanwise.panda.bukkit.util;

import cn.chuanwise.common.util.Preconditions;
import cn.chuanwise.common.util.StaticUtilities;
import org.bukkit.Server;
import org.bukkit.World;

import java.util.Objects;
import java.util.Optional;
import java.util.UUID;

public class Worlds
    extends StaticUtilities {
    
    public static Optional<World> getWorld(Server server, String name) {
        Preconditions.nonNull(server, "server");
        Preconditions.nonNull(name, "world name");

        final World world = server.getWorld(name);
        if (Objects.nonNull(world) && Objects.equals(world.getName(), name)) {
            return Optional.of(world);
        } else {
            return Optional.empty();
        }
    }

    public static Optional<World> getWorld(Server server, UUID uuid) {
        Preconditions.nonNull(server, "server");
        Preconditions.nonNull(uuid, "world uuid");

        final World world = server.getWorld(uuid);
        if (Objects.nonNull(world) && Objects.equals(world.getUID(), uuid)) {
            return Optional.of(world);
        } else {
            return Optional.empty();
        }
    }
}
