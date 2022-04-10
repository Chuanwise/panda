### Minecraft 开发工具库
# **panda** - **熊猫库**

`panda` 是一款便捷的、高效的 `Minecraft` 开发工具库。由椽子发起并编写，旨在将开发者从 `Minecraft` 原生的一些较为不便的 `API` 中解放出来，将更多的精力聚焦在核心业务逻辑上。

> **`QQ` 群**：`小明练剑场`：[1028959718](https://jq.qq.com/?_wv=1027&k=sjBXo6xh) <br>
> 项目发起人：[`椽子`](https://github.com/Chuanwise)

## 特性
### 便捷的指令注册

如使用过 `Bukkit` 自带的 `API` 写过一些较为复杂的插件，相信你已经疲于解析用户指令了。而借助 `panda`，你只需要写一个类，每一个方法对应一个指令，通过特定的注解设置格式和提取参数就可以了。就像这样：

```java
// ...

/** 专门负责用于处理指令的类 */
public class ExampleCommands {
    // ...

    @Format("e player [player] has [required]")
    void testIfPlayerHasPermission(CommandSender commandSender,
                                   @Reference("player") Player player,
                                   @Reference("required") String required) {

        final String hasOrHasNot = player.hasPermission(required) ? "有" : "无";
        commandSender.sendMessage("测试结果：玩家 " + player.getName() + hasOrHasNot + " 权限：" + required);
    }

    // ...
}
```

随后，在插件启动时注册一下指令，就像这样：

```java
// ...

public class ExamplePlugin
    extends JavaPlugin {
    // ...

    @Override
    public void onEnable() {
        // ...
        BukkitCommander.of(this)
                .bootstrap()
                .name("example")
                .aliases("e")
                .description("ExamplePlugin 的所有指令")
                .object(new ExampleCommands())
                .register();
        // ...
    }

    // ...
}
```

剩下的 `Tab` 补全、权限检测之类的操作都交给 `panda` 吧！

## 文档

`panda` 才刚刚开始，你的每一次使用和完善都是对我们最大的帮助。欢迎你同我们一起助力它的发展。

* [`panda-bukkit`](./panda-bukkit/README.md)

> `QQ` 群 `小明练剑场`：[1028959718](https://jq.qq.com/?_wv=1027&k=sjBXo6xh)
