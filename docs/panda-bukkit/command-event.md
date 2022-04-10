**panda -> panda-bukkit -> command -> event**
# panda-bukkit 指令事件

## 事件类型

* `CommandDispatchEvent` - 指令调度事件
  * `CommandDispatchErrorEvent` - 指令调度异常事件
  * `CommandDispatchOptionEvent` - 指令调度时有关选项事件
    * `LackRequiredOptionEvent` - 缺少必要选项事件
    * `ReassignOptionEvent` - 对选项重复赋值事件
    * `UndefinedOptionValueEvent` - 非法选项值事件
  * `MismatchedFormatEvent` - 格式错误事件
  * `MultipleCommandsMatchedEvent` - 多指令匹配事件
* `CommandExecuteEvent` - 指令执行事件
  * `CommandPreExecuteEvent` - 指令即将执行事件
  * `CommandPostExecuteEvent` - 指令已执行事件
  * `CommandExecuteErrorEvent` - 指令执行时抛出异常事件
  * `WireEvent` - 装配事件
    * `WireFailedEvent` - 装配失败事件
    * `WireMismatchedEvent` - 装配错误事件（很少出现）
* `CommandRegisterEvent` - 指令注册事件
* `CommandSenderMismatchedEvent` - 指令发送人错误事件
* `MethodRegisterEvent` - 方法注册事件

## 事件 API

### `ErrorEvent` - 异常事件

实现了该接口的事件，在没有被捕捉时，其携带的异常信息 `ErrorEvent#getCause()` 将会被送入异常捕捉器。

### `Cancellable` - 可取消事件

实现了该接口的事件，在被取消后（`Cancellable#isCancelled()`）只有 `alwaysValid` 为 `true` 的监听器才能监听到。