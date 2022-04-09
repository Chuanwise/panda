package cn.chuanwise.panda.plugin;

import cn.chuanwise.panda.scheduler.Scheduler;
import cn.chuanwise.panda.storage.Language;

import java.io.File;

/**
 * MC 插件
 *
 * @author Chuanwise
 */
public interface Plugin {
    
    /**
     * 获取插件数据文件夹
     *
     * @return 插件数据文件夹
     */
    File dataFolder();
    
    /**
     * 获取或创建插件数据文件夹
     *
     * @return 插件数据文件夹
     */
    File existedDataFolder();
    
    /**
     * 获取插件语言
     *
     * @return 插件语言
     */
    Language language();
    
    /**
     * 获取插件调度器
     *
     * @return 插件调度器
     */
    Scheduler scheduler();
}