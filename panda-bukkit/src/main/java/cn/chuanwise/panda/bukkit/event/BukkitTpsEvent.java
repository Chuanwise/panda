package cn.chuanwise.panda.bukkit.event;

import lombok.Data;

@Data
public class BukkitTpsEvent
    extends BukkitEvent {

    protected final int tps;
}
