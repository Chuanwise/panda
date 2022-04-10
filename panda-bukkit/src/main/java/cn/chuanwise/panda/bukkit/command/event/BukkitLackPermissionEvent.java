package cn.chuanwise.panda.bukkit.command.event;

import cn.chuanwise.command.Commander;
import cn.chuanwise.command.command.Command;
import cn.chuanwise.command.context.ReferenceInfo;
import cn.chuanwise.common.util.Preconditions;
import cn.chuanwise.panda.command.event.AbstractLackPermissionEvent;
import lombok.Data;
import org.bukkit.command.CommandSender;

import java.util.Map;

/**
 * 缺少权限时的事件
 *
 * @author Chuanwise
 */
@Data
@SuppressWarnings("all")
public class BukkitLackPermissionEvent
    extends AbstractLackPermissionEvent {
    
    private final CommandSender commandSender;
    
    public BukkitLackPermissionEvent(Commander commander,
                                     Command command,
                                     CommandSender commandSender,
                                     Map<String, ReferenceInfo> referenceInfo,
                                     String permission,
                                     String permissionMessage) {
        super(commander, command, referenceInfo, permission, permissionMessage);
        
        Preconditions.namedArgumentNonNull(commandSender, "command sender");
        
        this.commandSender = commandSender;
    }
}
