package cn.chuanwise.panda.command.event;

import cn.chuanwise.command.Commander;
import cn.chuanwise.command.command.Command;
import cn.chuanwise.command.context.ReferenceInfo;
import cn.chuanwise.command.event.Cancellable;
import cn.chuanwise.command.object.AbstractCommanderObject;
import cn.chuanwise.common.util.Preconditions;
import lombok.Data;

import java.util.Map;

/**
 * 缺少权限事件
 *
 * @author Chuanwise
 */
@Data
@SuppressWarnings("all")
public abstract class AbstractLackPermissionEvent
    extends AbstractCommanderObject
    implements Cancellable {
    
    private final Command command;
    private final Map<String, ReferenceInfo> referenceInfo;
    private final String permission;
    private final String permissionMessage;
    
    private boolean cancelled;
    
    public AbstractLackPermissionEvent(Commander commander,
                                       Command command,
                                       Map<String, ReferenceInfo> referenceInfo,
                                       String permission,
                                       String permissionMessage) {
        super(commander);
    
        Preconditions.namedArgumentNonNull(command, "command");
        Preconditions.namedArgumentNonNull(referenceInfo, "reference info");
        Preconditions.namedArgumentNonNull(permission, "permission");
    
        this.command = command;
        this.referenceInfo = referenceInfo;
        this.permission = permission;
        this.permissionMessage = permissionMessage;
    }
    
    public abstract Object getCommandSender();
}
