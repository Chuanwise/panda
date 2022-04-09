package cn.chuanwise.panda.bukkit.event;

import lombok.Data;
import org.bukkit.event.Cancellable;

@Data
public class BukkitCancellableEvent
    extends BukkitEvent
    implements Cancellable {
    
    boolean cancelled = false;
}
