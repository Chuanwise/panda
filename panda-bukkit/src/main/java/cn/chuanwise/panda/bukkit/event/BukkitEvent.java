package cn.chuanwise.panda.bukkit.event;

import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

public class BukkitEvent
    extends Event {
    
    protected static final HandlerList HANDLER_LIST = new HandlerList();

    @Override
    public HandlerList getHandlers() {
        return HANDLER_LIST;
    }

    public static HandlerList getHandlerList() {
        return HANDLER_LIST;
    }
}
