package cn.chuanwise.panda.bukkit.util;

import cn.chuanwise.common.util.Preconditions;
import cn.chuanwise.common.util.StaticUtilities;
import org.bukkit.OfflinePlayer;
import org.bukkit.Server;
import org.bukkit.entity.Player;

import java.util.Objects;
import java.util.Optional;
import java.util.UUID;

public class Players
    extends StaticUtilities {
    
    public static Optional<Player> getPlayer(Server server, String name) {
        Preconditions.nonNull(server, "server");
        Preconditions.nonNull(name, "player name");

        final Player player = server.getPlayer(name);
        if (Objects.nonNull(player) && Objects.equals(player.getName(), name)) {
            return Optional.of(player);
        } else {
            return Optional.empty();
        }
    }

    public static Optional<Player> getPlayer(Server server, UUID uuid) {
        Preconditions.nonNull(server, "server");
        Preconditions.nonNull(uuid, "player uuid");

        final Player player = server.getPlayer(uuid);
        if (Objects.nonNull(player) && Objects.equals(player.getUniqueId(), uuid)) {
            return Optional.of(player);
        } else {
            return Optional.empty();
        }
    }

    public static Optional<OfflinePlayer> getOfflinePlayer(Server server, String name) {
        Preconditions.nonNull(server, "server");
        Preconditions.nonNull(name, "player name");

        final OfflinePlayer offlinePlayer = server.getOfflinePlayer(name);
        if (Objects.nonNull(offlinePlayer) && Objects.equals(offlinePlayer.getName(), name)) {
            return Optional.of(offlinePlayer);
        } else {
            return Optional.empty();
        }
    }

    public static Optional<OfflinePlayer> getOfflinePlayer(Server server, UUID uuid) {
        Preconditions.nonNull(server, "server");
        Preconditions.nonNull(uuid, "player uuid");

        final OfflinePlayer offlinePlayer = server.getOfflinePlayer(uuid);
        if (Objects.nonNull(offlinePlayer) && Objects.equals(offlinePlayer.getUniqueId(), uuid)) {
            return Optional.of(offlinePlayer);
        } else {
            return Optional.empty();
        }
    }
}
