package cn.chuanwise.panda.command;

import cn.chuanwise.command.command.Property;
import cn.chuanwise.common.util.StaticUtilities;

/**
 * 指令属性
 *
 * @author Chuanwise
 */
public class Properties
        extends StaticUtilities {
    
    /**
     * 指令描述
     */
    public static final Property<String> DESCRIPTION = new Property<>("description");
    
    /**
     * 所需权限
     */
    public static final Property<String> PERMISSION = new Property<>("permission");
    
    /**
     * 权限信息
     */
    public static final Property<String> PERMISSION_MESSAGE = new Property<>("permission-message");
    
    /**
     * 指令用法
     */
    public static final Property<String> USAGE = new Property<>("usage");
}
