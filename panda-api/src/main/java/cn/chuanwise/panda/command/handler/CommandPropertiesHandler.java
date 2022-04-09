package cn.chuanwise.panda.command.handler;

import cn.chuanwise.command.command.Command;
import cn.chuanwise.command.command.CommandExecutor;
import cn.chuanwise.command.command.MethodCommandExecutor;
import cn.chuanwise.command.event.AbstractEventHandler;
import cn.chuanwise.command.event.CommandRegisterEvent;
import cn.chuanwise.common.util.Strings;
import cn.chuanwise.panda.annotation.Description;
import cn.chuanwise.panda.annotation.Required;
import cn.chuanwise.panda.annotation.Usage;
import cn.chuanwise.panda.command.Properties;
import lombok.Data;

import java.lang.reflect.Method;
import java.util.Objects;

/**
 * 为指令设置基础属性的事件监听器
 *
 * @author Chuanwise
 */
@Data
@SuppressWarnings("all")
public class CommandPropertiesHandler
    extends AbstractEventHandler<CommandRegisterEvent> {
    
    private static final CommandPropertiesHandler INSTANCE = new CommandPropertiesHandler();
    
    public static CommandPropertiesHandler getInstance() {
        return INSTANCE;
    }
    
    @Override
    protected boolean handleEvent0(CommandRegisterEvent commandRegisterEvent) throws Exception {
        final Command command = commandRegisterEvent.getCommand();
        final CommandExecutor executor = command.getExecutor();
    
        if (executor instanceof MethodCommandExecutor) {
            final MethodCommandExecutor methodCommandExecutor = (MethodCommandExecutor) executor;
            final Method method = methodCommandExecutor.getMethod();
        
            final Description description = method.getAnnotation(Description.class);
            if (Objects.nonNull(description)) {
                command.setProperty(Properties.DESCRIPTION, description.value());
            }
        
            final Usage usage = method.getAnnotation(Usage.class);
            if (Objects.nonNull(usage)) {
                command.setProperty(Properties.USAGE, usage.value());
            }
        
            final Required required = method.getAnnotation(Required.class);
            if (Objects.nonNull(required)) {
                command.setProperty(Properties.PERMISSION, required.value());
    
                if (Strings.nonEmpty(required.message())) {
                    command.setProperty(Properties.PERMISSION_MESSAGE, required.message());
                }
            }
            
            return true;
        }
        
        return false;
    }
}