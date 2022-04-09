package cn.chuanwise.panda.bukkit.plugin;

import cn.chuanwise.common.api.ExceptionSupplier;
import cn.chuanwise.common.util.Exceptions;
import cn.chuanwise.panda.bukkit.scheduler.BukkitScheduler;
import cn.chuanwise.panda.bukkit.contact.Contact;
import cn.chuanwise.panda.scheduler.Scheduler;
import cn.chuanwise.panda.storage.Language;
import cn.chuanwise.panda.plugin.Plugin;
import cn.chuanwise.common.util.Preconditions;
import cn.chuanwise.panda.stored.StoredFile;
import lombok.Getter;
import org.bukkit.event.Listener;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.nio.charset.Charset;

public class BukkitPlugin
    extends JavaPlugin
    implements Plugin {

    protected Language language;
    protected Contact contact = new Contact(this, new Language());
    protected Scheduler scheduler;

    @Getter
    private boolean loadSucceed, enableSucceed, disableSucceed;

    @Override
    public Scheduler scheduler() {
        return scheduler;
    }

    public Contact contact() {
        return contact;
    }

    @Override
    public Language language() {
        return language;
    }

    @Override
    public File dataFolder() {
        return getDataFolder();
    }

    @Override
    public final void onLoad() {
        try {
            scheduler = new BukkitScheduler(this);
            onLoad0();
            loadSucceed = true;
        } catch (Throwable t) {
            contact.consoleWarnString("插件 " + getName() + " 加载失败，异常信息如下");
            contact.consoleErrorString(Exceptions.readStackTrace(t));
        }
    }

    protected void onLoad0() throws Exception {}

    @Override
    public final void onEnable() {
        enableSucceed = false;
        if (!loadSucceed) {
            return;
        }

        try {
            onEnable0();
            enableSucceed = true;
        } catch (Throwable t) {
            contact.consoleWarnString("插件 " + getName() + " 启动失败，异常信息如下");
            contact.consoleErrorString(Exceptions.readStackTrace(t));
        }
    }

    protected void onEnable0() throws Exception {}

    @Override
    public final void onDisable() {
        disableSucceed = false;
        if (!enableSucceed) {
            return;
        }

        try {
            onDisable0();
            disableSucceed = true;
        } catch (Throwable t) {
            contact.consoleWarnString("插件 " + getName() + " 关闭失败，异常信息如下");
            contact.consoleErrorString(Exceptions.readStackTrace(t));
        } finally {
            getServer().getScheduler().cancelTasks(this);
        }
    }

    protected void onDisable0() throws Exception {}
    
    @Override
    public File existedDataFolder() {
        final File dataFolder = getDataFolder();
        if (!dataFolder.isDirectory()) {
            Preconditions.state(dataFolder.mkdirs(), "无法创建插件数据文件夹");
        }
        return dataFolder;
    }
    
    /**
     * 安装配置文件
     * @param configurationClass 配置文件类
     * @param configurationFile 配置文件
     * @param constructor 获取默认配置文件的方法
     * @param <T> 配置文件类型
     * @return 安装的配置文件
     */
    protected <T extends StoredFile> T setupConfiguration(Class<T> configurationClass, File configurationFile, ExceptionSupplier<T> constructor) throws Exception {
        Preconditions.nonNull(configurationClass, "configuration class");
        Preconditions.nonNull(configurationFile, "configuration file");
        Preconditions.nonNull(constructor, "constructor");
        Preconditions.nonNull(contact, "language handler");

        if (configurationFile.isFile()) {
            return StoredFile.loadFile(configurationFile, configurationClass);
        } else {
            final T t = constructor.exceptGet();
            t.setFile(configurationFile);
            t.save();
            return t;
        }
    }

    @SuppressWarnings("all")
    protected Language setupLanguage(File languageFile, ExceptionSupplier<Language> constructor) throws Exception {
        Preconditions.nonNull(languageFile, "language class");
        Preconditions.nonNull(constructor, "constructor");

        final Language defaultLanguage = constructor.exceptGet();

        if (languageFile.isFile()) {
            language = StoredFile.loadFile(languageFile, Language.class);
            language.setCharset(Charset.defaultCharset());

            contact = new Contact(this, this.language);

            if (language.update(defaultLanguage)) {
                language.save();
                contact.consoleInfo("language.updated");
            } else {
                contact.consoleInfo("language.loaded");
            }
        } else {
            language = defaultLanguage;
            language.setCharset(Charset.defaultCharset());
            language.setFile(languageFile);
            language.save();

            contact = new Contact(this, this.language);

            contact.consoleInfo("language.default");
        }

        return language;
    }

    /** 注册监听器 */
    protected void registerListeners(Listener listener) {
        Preconditions.nonNull(listener, "listener");
        getServer().getPluginManager().registerEvents(listener, this);
    }
}