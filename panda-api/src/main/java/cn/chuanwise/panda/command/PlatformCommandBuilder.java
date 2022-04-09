package cn.chuanwise.panda.command;

import cn.chuanwise.command.object.CommanderObject;

import java.util.Collection;

/**
 * 平台指令构建器
 *
 * @author Chuanwise
 */
public interface PlatformCommandBuilder
    extends CommanderObject {
    
    /**
     * 设置指令名
     *
     * @param name 指令名
     * @return 平台指令构建器
     */
    PlatformCommandBuilder name(String name);
    
    /**
     * 设置指令别名
     *
     * @param alias 指令别名
     * @return 平台指令构建器
     */
    PlatformCommandBuilder aliases(String... alias);
    
    /**
     * 设置指令名
     *
     * @param alias 指令别名
     * @return 平台指令构建器
     */
    PlatformCommandBuilder aliases(Collection<String> alias);
}
