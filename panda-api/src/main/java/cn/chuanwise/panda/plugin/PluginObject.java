package cn.chuanwise.panda.plugin;

import cn.chuanwise.panda.storage.Language;

import java.beans.Transient;

/**
 * 持有插件引用的某种对象
 *
 * @author Chuanwise
 */
public interface PluginObject<T extends Plugin> {
    
    /**
     * 获取对应的插件
     *
     * @return 对应的插件
     */
    @Transient
    T plugin();
    
    /**
     * 获取插件语言
     *
     * @return 插件语言
     */
    @Transient
    default Language language() {
        return plugin().language();
    }
}
