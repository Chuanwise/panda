package cn.chuanwise.panda.storage;

import cn.chuanwise.common.space.Container;
import cn.chuanwise.common.util.Indexes;
import cn.chuanwise.common.util.Numbers;
import cn.chuanwise.common.util.Preconditions;
import cn.chuanwise.panda.stored.StoredFile;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.*;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 语言文件分为两个部分：message 和 config
 * 其中，config 是对所有 message 都生效的，主要是 prefix、suffix 等
 *
 * @author Chuanwise
 */
@Data
public class Language
    extends StoredFile {
    
    @Data
    public static class Configuration {
        @Data
        public static class FormatConfiguration {
            @Data
            @AllArgsConstructor
            @NoArgsConstructor
            public static class EachLevelConfiguration {
                String prefix, suffix;
            }

            EachLevelConfiguration
                    debug = new EachLevelConfiguration("§8[§1调试§8] §9", "§r"),
                    info = new EachLevelConfiguration("§8[§3信息§8] §b", "§r"),
                    warn = new EachLevelConfiguration("§8[§6警告§8] §e", "§r"),
                    error = new EachLevelConfiguration("§8[§4错误§8] §c", "§r"),
                    success = new EachLevelConfiguration("§8[§2成功§8] §a", "§r");
        }
        FormatConfiguration format = new FormatConfiguration();

        String noSuchNode = "§8[§c%s§8]§r";

        String noSuchContext = "[ctx %s : [0, %d)]";

        int maxVariableCount = 10;
        String contextPrefix = ".";
    }

    protected Configuration configuration = new Configuration();
    protected Map<String, Object> messages = new HashMap<>();

    public boolean update(Language newerLanguage) {
        Preconditions.nonNull(newerLanguage, "language");
        return updateMap(messages, newerLanguage.messages);
    }

    @SuppressWarnings("unchecked")
    private boolean updateMap(Map<String, Object> theElder, Map<String, Object> theNewer) {
        final AtomicBoolean updated = new AtomicBoolean(false);
        theNewer.forEach((newerKey, newerValue) -> {
            final Object elderValue = theElder.get(newerKey);
            if (Objects.isNull(elderValue)) {
                theElder.put(newerKey, newerValue);
                updated.set(true);
                return;
            }

            final boolean elderValueIsMap = elderValue instanceof Map;
            final boolean newerValueIsMap = newerValue instanceof Map;

            if (elderValueIsMap != newerValueIsMap) {
                theElder.put(newerKey, newerValue);
                updated.set(true);
            } else if (elderValueIsMap) {
                final boolean thisTimeUpdated = updateMap((Map<String, Object>) elderValue, (Map<String, Object>) newerValue);
                updated.set(updated.get() || thisTimeUpdated);
            }
        });
        return updated.get();
    }

    @SuppressWarnings("all")
    public Container<Object> getNakedMessage(String path) {
        Preconditions.nonNull(path, "node");

        final String[] nodes = path.split(Pattern.quote("."));
        Map<String, Object> map = messages;

        final int prefixIndex = nodes.length - 1;
        for (int i = 0; i < prefixIndex; i++) {
            final String node = nodes[i];

            final Object nextNode = map.get(node);
            if (nextNode instanceof Map) {
                map = (Map) nextNode;
            } else {
                return Container.empty();
            }
        }

        return Container.ofNotNull(map.get(nodes[nodes.length - 1]));
    }
    
    private static final Pattern VARIABLE_PATTERN = Pattern.compile("\\{(?<var>.+?)\\}");

    public String format(String string, Object... arguments) {
        if (!string.contains("{")) {
            return string;
        }
        
        final StringBuilder stringBuilder = new StringBuilder(string);
        Matcher matcher = VARIABLE_PATTERN.matcher(stringBuilder);
    
        int position = 0;
        final String contextPrefix = configuration.contextPrefix;
        while (matcher.find(position)) {
            final int start = matcher.start();
            final int end = matcher.end();
    
            final String variable = stringBuilder.substring(start, end);
            if (variable.startsWith(contextPrefix)) {
                final String remainPart = variable.substring(contextPrefix.length());
    
                final Integer index = Numbers.parseInt(remainPart);
                if (Objects.isNull(index)) {
                    position = end;
                    continue;
                }
    
                final String replaceTo;
                if (Indexes.isLegal(index, arguments.length)) {
                    replaceTo = Objects.toString(arguments[index]);
                    stringBuilder.replace(start, end, replaceTo);
                    position = start + 1;
                } else {
                    replaceTo = String.format(configuration.noSuchContext, remainPart, arguments.length);
                    position = end;
                }
                stringBuilder.replace(start, end, replaceTo);
            }
        }
        
        return stringBuilder.toString();
    }

    public String formatNode(String node, Object... arguments) {
        final Container<Object> container = getNakedMessage(node);
        if (container.isEmpty()) {
            return String.format(configuration.noSuchNode, node);
        }

        final Object object = container.get();
        final String string = Objects.toString(object);

        return format(string, arguments);
    }

    public String formatInfoNode(String node, Object... arguments) {
        final String string = formatNode(node, arguments);
        return formatInfo(string);
    }

    public String formatErrorNode(String node, Object... arguments) {
        final String string = formatNode(node, arguments);
        return formatError(string);
    }

    public String formatWarnNode(String node, Object... arguments) {
        final String string = formatNode(node, arguments);
        return formatWarn(string);
    }

    public String formatSuccessNode(String node, Object... arguments) {
        final String string = formatNode(node, arguments);
        return formatSuccess(string);
    }

    public String formatDebugNode(String node, Object... arguments) {
        final String string = formatNode(node, arguments);
        return formatDebug(string);
    }

    public String formatInfo(String string) {
        final String prefix = configuration.format.info.prefix;
        final String suffix = configuration.format.info.suffix;

        return prefix + string + suffix;
    }

    public String formatWarn(Object string) {
        final String prefix = configuration.format.warn.prefix;
        final String suffix = configuration.format.warn.suffix;

        return prefix + string + suffix;
    }

    public String formatError(Object string) {
        final String prefix = configuration.format.error.prefix;
        final String suffix = configuration.format.error.suffix;

        return prefix + string + suffix;
    }

    public String formatDebug(Object string) {
        final String prefix = configuration.format.debug.prefix;
        final String suffix = configuration.format.debug.suffix;

        return prefix + string + suffix;
    }

    public String formatSuccess(Object string) {
        final String prefix = configuration.format.success.prefix;
        final String suffix = configuration.format.success.suffix;

        return prefix + string + suffix;
    }
}