package cn.chuanwise.panda.bukkit.ask;

import cn.chuanwise.common.util.Preconditions;
import lombok.Getter;
import org.bukkit.command.CommandSender;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

@Getter
public class AskerManager {
    protected final Map<CommandSender, Asker> askers = new ConcurrentHashMap<>();

    public Map<CommandSender, Asker> getAskers() {
        return Collections.unmodifiableMap(askers);
    }

    public Optional<Asker> ask(CommandSender commandSender) {
        Preconditions.nonNull(commandSender, "command sender");

        Asker asker = askers.get(commandSender);
        if (Objects.nonNull(asker)) {
            return Optional.empty();
        }

        asker = new Asker(this, commandSender);
        askers.put(commandSender, asker);
        return Optional.of(asker);
    }

    public Optional<Asker> getAsker(CommandSender commandSender) {
        return Optional.ofNullable(askers.get(commandSender));
    }
}
