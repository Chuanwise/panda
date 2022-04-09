package cn.chuanwise.panda.bukkit.command.configuration;

import lombok.Data;

@Data
public class BukkitCommandLibConfiguration {

    protected String pluginCompletePlaceHolder = "~";
    protected String playerCompletePlaceHolder = "~";
    protected String offlinePlayerCompletePlaceHolder = "~";
    protected String worldCompletePlaceHolder = "~";
}
