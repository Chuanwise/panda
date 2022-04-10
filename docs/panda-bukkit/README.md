### panda
# panda-bukkit 开发文档

`panda-bukkit` 是 `panda` 的一部分，提供编写 `Bukkit` 及其分支相关的软件的工具。

> 如有疑问，欢迎加入 `QQ` 群 `小明练剑场`：[1028959718](https://jq.qq.com/?_wv=1027&k=sjBXo6xh) 参与讨论和开发。


## 配置依赖

### gradle

首先请在 `build.gradle` 中引用中央仓库：

```groovy
repositories {
    maven {
        name 'codethink repo'
        url 'https://repo.codethink.top/public'
    }
}
```

随后添加 `panda-bukkit` 依赖：

```groovy
dependencies {
    implementation 'cn.chuanwise:panda-bukkit:5.0.1-SNAPSHOT'
}
```

这里面的版本号不一定是最新的，请在 [此处](https://nexus.codethink.top/#browse/browse:maven-public:cn%2Fchuanwise%2Fpanda-bukkit) 查看最新的版本号。

别忘了添加 `Bukkit API` 和打包插件。写好之后，你的 `build.gradle` 应该看起来像是这样：

```groovy
plugins {
    id 'java'

    // shadow
    id 'com.github.johnrengelman.shadow' version '7.1.2'
}

group 'cn.chuanwise'
version '5.0.1-SNAPSHOT'

repositories {
    mavenCentral()

    maven {
        name 'codethink repo'
        url 'https://repo.codethink.top/public'
    }

    maven {
        name 'bukkit repo'
        url 'https://hub.spigotmc.org/nexus/content/repositories/public/'
    }
}

dependencies {
    // panda
    implementation 'cn.chuanwise:panda-bukkit:5.0.1-SNAPSHOT'

    // bukkit api
    compileOnly 'org.bukkit:bukkit:1.12-R0.1-SNAPSHOT'

    // junit
    testImplementation 'org.junit.jupiter:junit-jupiter-api:5.8.2'
    testRuntimeOnly 'org.junit.jupiter:junit-jupiter-engine:5.8.2'
}

test {
    useJUnitPlatform()
}

shadowJar {
    exclude "META-INF/*.SF"
    exclude "META-INF/*.DSA"
    exclude "META-INF/*.RSA"
    exclude "META-INF/LICENSE"

    archiveName 'PandaDemo-' + version + '.' + extension
}
```

刷新 `gradle`，即可使用 `panda-bukkit`。

### maven

首先请在 `pom.xml` 中引用中央仓库：

```xml
<repositories>
    <!-- panda 所在的仓库 -->
    <repository>
        <id>codethink repo</id>
        <url>https://repo.codethink.top</url>
    </repository>
</repositories>
```

随后添加 `panda-bukkit` 依赖：

```xml
<dependencies>
    <!-- panda 的 panda-bukkit 部分-->
    <dependency>
        <groupId>cn.chuanwise</groupId>
        <artifactId>panda-bukkit</artifactId>
        <version>5.0.1-SNAPSHOT</version>
    </dependency>
</dependencies>
```

这里面的版本号不一定是最新的，请在 [此处](https://nexus.codethink.top/#browse/browse:maven-public:cn%2Fchuanwise%2Fpanda-bukkit) 查看最新的版本号。

别忘了添加 `Bukkit API` 和打包插件。写好之后，你的 `pom.xml` 应该看起来像是这样：

```xml
<?xml version="1.0" encoding="UTF-8"?>
<project xmlns="http://maven.apache.org/POM/4.0.0"
         xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
         xsi:schemaLocation="http://maven.apache.org/POM/4.0.0 http://maven.apache.org/xsd/maven-4.0.0.xsd">
    <modelVersion>4.0.0</modelVersion>

    <groupId>cn.chuanwise</groupId>
    <artifactId>panda-demo</artifactId>
    <version>1.0-SNAPSHOT</version>

    <repositories>
        <!-- panda repo -->
        <repository>
            <id>codethink repo</id>
            <url>https://repo.codethink.top</url>
        </repository>

        <!-- bukkit repo -->
        <repository>
            <id>bukkit repo</id>
            <url>https://hub.spigotmc.org/nexus/content/repositories/public/</url>
        </repository>
    </repositories>

    <dependencies>
        <!-- bukkit api -->
        <dependency>
            <groupId>org.bukkit</groupId>
            <artifactId>bukkit</artifactId>
            <version>1.12-R0.1-SNAPSHOT</version>
            <scope>provided</scope>
        </dependency>

        <!-- panda -->
        <dependency>
            <groupId>cn.chuanwise</groupId>
            <artifactId>panda-bukkit</artifactId>
            <version>5.0.1-SNAPSHOT</version>
        </dependency>
    </dependencies>

    <build>
        <plugins>
            <!-- 使用 maven-shade-plugin 将 panda-bukkit 打包进你的插件 -->
            <!-- 或者为插件添加前置插件 panda-bukkit，避免缺少相关类 -->
            <plugin>
                <groupId>org.apache.maven.plugins</groupId>
                <artifactId>maven-shade-plugin</artifactId>
                <version>3.1.1</version>
                <configuration>
                    <!-- 最小化 Jar 包，可以避免将没有使用的其他 panda-bukkit 组件打包进插件增大插件体积 -->
                    <minimizeJar>true</minimizeJar>

                    <!-- 避免完整性校验，还可以进一步缩小 Jar 体积 -->
                    <filters>
                        <filter>
                            <artifact>*:*</artifact>
                            <excludes>
                                <exclude>META-INF/*.SF</exclude>
                                <exclude>META-INF/*.DSA</exclude>
                                <exclude>META-INF/*.RSA</exclude>
                            </excludes>
                        </filter>
                    </filters>

                    <!-- 可以加一些额外的 maven-shade 配置信息 -->
                </configuration>
                <executions>
                    <execution>
                        <phase>package</phase>
                        <goals>
                            <goal>shade</goal>
                        </goals>
                    </execution>
                </executions>
            </plugin>
        </plugins>
    </build>
</project>
```

刷新 `maven`，即可使用 `panda-bukkit`。

## 核心组件

* [**指令组件**](./command.md)：提供便捷的指令注册、解析、异常处理等服务。