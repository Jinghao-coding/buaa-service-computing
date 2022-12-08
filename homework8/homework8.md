# 实验8：动态分析及服务性能缺陷

**🔔**

**实验内容：**

1. 主流数据处理服务获取
2. 下载 Map Reduce 项目的最新版本源代码，编译运行自带的 word count 例子。
3. 服务动态分析
4. 基于Java agent 机制和 javassist 工具生成 word count 例子运行过程中 Map Reduce 产生的 trace，包括进入和退出每个方法的时间戳、线程号等。
5. 服务性能缺陷分析
6. 基于上述运行期trace, 分析Map Reduce可能存在的性能缺陷。
7. 撰写实验报告

>
> 实验代码及文档：[实验8-GitHub](https://github.com/Jinghao-coding/jinghao-service-compute-course/tree/main/homework8)

## Hadoop 安装

### 安装

- 下载 Hadoop 源代码：

![](https://www.kdocs.cn/api/v3/office/copy/dE94VTJ1djkzVmpWMmdPdVlDc21BaWF4VThEaTJWQkVYWU5KNlBuOUNlN1FKQkJsUnpPQjExa0lHQVdzdFc1TDhWZGZQME1zR2NkdE51WHBaMVdaUjJqc2RVUWxpVE1uZlJzZEcwclVaaDdjZDlYZ2o5VmdpVll3NUo4VVVEakV2QzIvSWpDMmxKTVcrNTVsbUl2cjJLTDB0alozT1phVHRKS2g2NC91Y2NwUUMySUE2QkovSld3SGVoQ1E1Vm5XRFh1RDRwYjM0YzFENlRMS2F3UmlESWNJVkc0NzJuV3ZhVG1iRzB6cXRoaEpHRjBXRjVHenBiaFNzdUxGL2cyREdvSlljNDFFSW9nPQ==/attach/object/FYT2EAYAKY)

  这里选择下载 Hadoop 3.3.4 版本源代码：

```
wget https://mirrors.tuna.tsinghua.edu.cn/apache/hadoop/common/hadoop-3.3.4/hadoop-3.3.4.tar.gz
```

![](https://www.kdocs.cn/api/v3/office/copy/dE94VTJ1djkzVmpWMmdPdVlDc21BaWF4VThEaTJWQkVYWU5KNlBuOUNlN1FKQkJsUnpPQjExa0lHQVdzdFc1TDhWZGZQME1zR2NkdE51WHBaMVdaUjJqc2RVUWxpVE1uZlJzZEcwclVaaDdjZDlYZ2o5VmdpVll3NUo4VVVEakV2QzIvSWpDMmxKTVcrNTVsbUl2cjJLTDB0alozT1phVHRKS2g2NC91Y2NwUUMySUE2QkovSld3SGVoQ1E1Vm5XRFh1RDRwYjM0YzFENlRMS2F3UmlESWNJVkc0NzJuV3ZhVG1iRzB6cXRoaEpHRjBXRjVHenBiaFNzdUxGL2cyREdvSlljNDFFSW9nPQ==/attach/object/WIU2EAYANE)

移动到 `<span>/usr/local</span>`下

![](https://www.kdocs.cn/api/v3/office/copy/dE94VTJ1djkzVmpWMmdPdVlDc21BaWF4VThEaTJWQkVYWU5KNlBuOUNlN1FKQkJsUnpPQjExa0lHQVdzdFc1TDhWZGZQME1zR2NkdE51WHBaMVdaUjJqc2RVUWxpVE1uZlJzZEcwclVaaDdjZDlYZ2o5VmdpVll3NUo4VVVEakV2QzIvSWpDMmxKTVcrNTVsbUl2cjJLTDB0alozT1phVHRKS2g2NC91Y2NwUUMySUE2QkovSld3SGVoQ1E1Vm5XRFh1RDRwYjM0YzFENlRMS2F3UmlESWNJVkc0NzJuV3ZhVG1iRzB6cXRoaEpHRjBXRjVHenBiaFNzdUxGL2cyREdvSlljNDFFSW9nPQ==/attach/object/L4V2EAYA74)

若出现如上输出，则说明 Hadoop 安装成功。

### **安装**[**SSH**](https://so.csdn.net/so/search?q=SSH&spm=1001.2101.3001.7020)**和配置SSH无密码登录**

Ubuntu 操作系统下默认已安装了 SSH 客户端，因此这里我们只需安装 SSH 服务端：

```
sudo apt-get install openssh-server
```

安装后，可使用以下命令登录本机：

```
ssh localhost
```

输入 yes 与用户密码，就可以登录到本机，详细输出如下：

![](https://www.kdocs.cn/api/v3/office/copy/dE94VTJ1djkzVmpWMmdPdVlDc21BaWF4VThEaTJWQkVYWU5KNlBuOUNlN1FKQkJsUnpPQjExa0lHQVdzdFc1TDhWZGZQME1zR2NkdE51WHBaMVdaUjJqc2RVUWxpVE1uZlJzZEcwclVaaDdjZDlYZ2o5VmdpVll3NUo4VVVEakV2QzIvSWpDMmxKTVcrNTVsbUl2cjJLTDB0alozT1phVHRKS2g2NC91Y2NwUUMySUE2QkovSld3SGVoQ1E1Vm5XRFh1RDRwYjM0YzFENlRMS2F3UmlESWNJVkc0NzJuV3ZhVG1iRzB6cXRoaEpHRjBXRjVHenBiaFNzdUxGL2cyREdvSlljNDFFSW9nPQ==/attach/object/HIY2EAYAQA)

接着我们退出 SSH 登录，

```
exit
```

配置无密码登录：

```
cd ~/.ssh/
ssh-keygen -t rsa
```

注意这里第二步要你输入文件名时不用输入，直接一路 Enter 选择默认值就好了！

```
cat ./id_rsa.pub >> ./authorized_keys
```

此时再用 `<span> ssh localhost</span>` 命令无需密码即可登录了。

### 运行word count示例

格式化hadoop文件系统

```
cd /usr/local/hadoop
```

启动 dfs

```
./sbin/start-dfs.sh
```

![](https://www.kdocs.cn/api/v3/office/copy/dE94VTJ1djkzVmpWMmdPdVlDc21BaWF4VThEaTJWQkVYWU5KNlBuOUNlN1FKQkJsUnpPQjExa0lHQVdzdFc1TDhWZGZQME1zR2NkdE51WHBaMVdaUjJqc2RVUWxpVE1uZlJzZEcwclVaaDdjZDlYZ2o5VmdpVll3NUo4VVVEakV2QzIvSWpDMmxKTVcrNTVsbUl2cjJLTDB0alozT1phVHRKS2g2NC91Y2NwUUMySUE2QkovSld3SGVoQ1E1Vm5XRFh1RDRwYjM0YzFENlRMS2F3UmlESWNJVkc0NzJuV3ZhVG1iRzB6cXRoaEpHRjBXRjVHenBiaFNzdUxGL2cyREdvSlljNDFFSW9nPQ==/attach/object/HI4KEAYAXM)

全部启动：

```
./sbin/start-all.sh
```

![](https://www.kdocs.cn/api/v3/office/copy/dE94VTJ1djkzVmpWMmdPdVlDc21BaWF4VThEaTJWQkVYWU5KNlBuOUNlN1FKQkJsUnpPQjExa0lHQVdzdFc1TDhWZGZQME1zR2NkdE51WHBaMVdaUjJqc2RVUWxpVE1uZlJzZEcwclVaaDdjZDlYZ2o5VmdpVll3NUo4VVVEakV2QzIvSWpDMmxKTVcrNTVsbUl2cjJLTDB0alozT1phVHRKS2g2NC91Y2NwUUMySUE2QkovSld3SGVoQ1E1Vm5XRFh1RDRwYjM0YzFENlRMS2F3UmlESWNJVkc0NzJuV3ZhVG1iRzB6cXRoaEpHRjBXRjVHenBiaFNzdUxGL2cyREdvSlljNDFFSW9nPQ==/attach/object/NU4KEAYALU)

* 创建 HDFS 数据目录：
  * 创建一个目录，用于保存MapReduce任务的输入文件：

```
hadoop fs -mkdir -p /data/wordcount
```

* 创建目录，保存 MapReduce 任务输出文件

```
hadoop fs -mkdir /output
```

* 上传单词文件到 HDFS

单词文件：word.txt, 内容如下：

```
I love you
you love me
I love you and you love me
```

上传

```
hadoop fs -put word.txt /data/wordcount
```

![](https://www.kdocs.cn/api/v3/office/copy/dE94VTJ1djkzVmpWMmdPdVlDc21BaWF4VThEaTJWQkVYWU5KNlBuOUNlN1FKQkJsUnpPQjExa0lHQVdzdFc1TDhWZGZQME1zR2NkdE51WHBaMVdaUjJqc2RVUWxpVE1uZlJzZEcwclVaaDdjZDlYZ2o5VmdpVll3NUo4VVVEakV2QzIvSWpDMmxKTVcrNTVsbUl2cjJLTDB0alozT1phVHRKS2g2NC91Y2NwUUMySUE2QkovSld3SGVoQ1E1Vm5XRFh1RDRwYjM0YzFENlRMS2F3UmlESWNJVkc0NzJuV3ZhVG1iRzB6cXRoaEpHRjBXRjVHenBiaFNzdUxGL2cyREdvSlljNDFFSW9nPQ==/attach/object/ZFJKEAYALU)

* 运行 mapreduce wordcount 实例程序：

```
hadoop jar ./hadoop-mapreduce-examples-3.3.4.jar wordcount /data/wordcount /output/wordcount
```

  可以看到，直接运行原始 jar 包，可以运行成功：

![](https://www.kdocs.cn/api/v3/office/copy/dE94VTJ1djkzVmpWMmdPdVlDc21BaWF4VThEaTJWQkVYWU5KNlBuOUNlN1FKQkJsUnpPQjExa0lHQVdzdFc1TDhWZGZQME1zR2NkdE51WHBaMVdaUjJqc2RVUWxpVE1uZlJzZEcwclVaaDdjZDlYZ2o5VmdpVll3NUo4VVVEakV2QzIvSWpDMmxKTVcrNTVsbUl2cjJLTDB0alozT1phVHRKS2g2NC91Y2NwUUMySUE2QkovSld3SGVoQ1E1Vm5XRFh1RDRwYjM0YzFENlRMS2F3UmlESWNJVkc0NzJuV3ZhVG1iRzB6cXRoaEpHRjBXRjVHenBiaFNzdUxGL2cyREdvSlljNDFFSW9nPQ==/attach/object/VZL2EAYADQ)

运行命令查看结果：

```
hadoop fs -cat /output/wordcount/part-r-00000
```

![](https://www.kdocs.cn/api/v3/office/copy/dE94VTJ1djkzVmpWMmdPdVlDc21BaWF4VThEaTJWQkVYWU5KNlBuOUNlN1FKQkJsUnpPQjExa0lHQVdzdFc1TDhWZGZQME1zR2NkdE51WHBaMVdaUjJqc2RVUWxpVE1uZlJzZEcwclVaaDdjZDlYZ2o5VmdpVll3NUo4VVVEakV2QzIvSWpDMmxKTVcrNTVsbUl2cjJLTDB0alozT1phVHRKS2g2NC91Y2NwUUMySUE2QkovSld3SGVoQ1E1Vm5XRFh1RDRwYjM0YzFENlRMS2F3UmlESWNJVkc0NzJuV3ZhVG1iRzB6cXRoaEpHRjBXRjVHenBiaFNzdUxGL2cyREdvSlljNDFFSW9nPQ==/attach/object/QZL2EAYANA)

## 利用javassist编写java agent

* 创建一个新的 MAVEN 项目
* 编写 Agent 类：

```
import javassist.ClassPool;
import javassist.CtClass;
import javassist.CtMethod;

import java.io.ByteArrayInputStream;
import java.lang.instrument.ClassFileTransformer;
import java.lang.instrument.IllegalClassFormatException;
import java.lang.instrument.Instrumentation;
import java.security.ProtectionDomain;

public class Agent {
    /**
     * jvm 参数形式启动，运行此方法
     *
     * @param agentArgs
     * @param inst
     */
    public static void premain(String agentArgs, Instrumentation inst) {
        System.out.println("premain");
        customLogic(inst);
    }

    /**
     * 动态 attach 方式启动，运行此方法
     *
     * @param agentArgs
     * @param inst
     */
    public static void agentmain(String agentArgs, Instrumentation inst) {
        System.out.println("agentmain");
        customLogic(inst);
    }
    /**
     * 统计方法耗时
     *
     * @param inst
     */
    private static void customLogic(Instrumentation inst) {
        inst.addTransformer(new ClassFileTransformer() {
                                @Override
                                public byte[] transform(ClassLoader loader, String className,
                                                        Class<?> classBeingRedefined,
                                                        ProtectionDomain protectionDomain,
                                                        byte[] classfileBuffer) throws IllegalClassFormatException {
                                    // 只针对目标包下进行耗时统计
                                    if (!className.startsWith("org/apache/hadoop")) {
                                        return classfileBuffer;
                                    }

                                    CtClass cl = null;
                                    try {
                                        ClassPool classPool = ClassPool.getDefault();
                                        cl = classPool.makeClass(new ByteArrayInputStream(classfileBuffer));

                                        for (CtMethod method : cl.getDeclaredMethods()) {
                                            // 所有方法，统计耗时；请注意，需要通过`addLocalVariable`来声明局部变量
                                            method.addLocalVariable("start", CtClass.longType);
                                            method.addLocalVariable("pid", CtClass.longType);
                                            method.insertBefore("start = System.currentTimeMillis(); " +
                                                    "pid = Thread.currentThread().getId();");
                                            String methodName = method.getLongName();
                                            method.insertAfter("System.err.println(\"" + methodName + " , \" + (System" +
                                                    ".currentTimeMillis() - start) + \" , \" + start + \" ,  \" + pid);");
                                        }

                                        byte[] transformed = cl.toBytecode();
                                        return transformed;
                                    } catch (Exception e) {
                                        e.printStackTrace();
                                    }
                                    return classfileBuffer;
                                }
                            },
                true);
    }

}
```

通过 maven，可以比较简单的输出一个合规的 java agent 包，修改 pom.xml 文件如下：

```
<?xml version="1.0" encoding="UTF-8"?>
<project xmlns="http://maven.apache.org/POM/4.0.0"
         xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
         xsi:schemaLocation="http://maven.apache.org/POM/4.0.0 http://maven.apache.org/xsd/maven-4.0.0.xsd">
    <modelVersion>4.0.0</modelVersion>

    <groupId>org.example</groupId>
    <artifactId>hw8</artifactId>
    <version>1.0-SNAPSHOT</version>

    <build>
        <plugins>
            <plugin>
                <groupId>org.apache.maven.plugins</groupId>
                <artifactId>maven-assembly-plugin</artifactId>
                <configuration>
                    <descriptorRefs>
                        <descriptorRef>jar-with-dependencies</descriptorRef>
                    </descriptorRefs>
                    <archive>
                        <manifestEntries>
                            <Premain-Class>Agent</Premain-Class>
                            <Agent-Class>Agent</Agent-Class>
                            <Can-Redefine-Classes>true</Can-Redefine-Classes>
                            <Can-Retransform-Classes>true</Can-Retransform-Classes>
                        </manifestEntries>
                    </archive>
                </configuration>

                <executions>
                    <execution>
                        <goals>
                            <goal>attached</goal>
                        </goals>
                        <phase>package</phase>
                    </execution>
                </executions>
            </plugin>
            <plugin>
                <groupId>org.apache.maven.plugins</groupId>
                <artifactId>maven-compiler-plugin</artifactId>
                <configuration>
                    <source>6</source>
                    <target>6</target>
                </configuration>
            </plugin>
        </plugins>
    </build>
    <dependencies>
        <dependency>
            <groupId>org.javassist</groupId>
            <artifactId>javassist</artifactId>
            <version>3.25.0-GA</version>
        </dependency>
    </dependencies>

</project>
```

然后通过

```
mvn assembly:assembly
```

打包，在 `<span>target</span>` 目录下，可以看到一个后缀为 `<span> jar-with-dependencies</span>`的 jar 包，就是我们的agent, 如下图所示：

![](https://www.kdocs.cn/api/v3/office/copy/dE94VTJ1djkzVmpWMmdPdVlDc21BaWF4VThEaTJWQkVYWU5KNlBuOUNlN1FKQkJsUnpPQjExa0lHQVdzdFc1TDhWZGZQME1zR2NkdE51WHBaMVdaUjJqc2RVUWxpVE1uZlJzZEcwclVaaDdjZDlYZ2o5VmdpVll3NUo4VVVEakV2QzIvSWpDMmxKTVcrNTVsbUl2cjJLTDB0alozT1phVHRKS2g2NC91Y2NwUUMySUE2QkovSld3SGVoQ1E1Vm5XRFh1RDRwYjM0YzFENlRMS2F3UmlESWNJVkc0NzJuV3ZhVG1iRzB6cXRoaEpHRjBXRjVHenBiaFNzdUxGL2cyREdvSlljNDFFSW9nPQ==/attach/object/E5P2EAYASY)

## 利用 Java agent 对 WordCount进行分析

* **设置环境变量**

```
export HADOOP_OPTS='-javaagent:/home/ivic/wangjinghao/hw8-1.0-SNAPSHOT-jar-with-dependencies.jar'
```

* 运行 mapreduce wordcount 实例程序：

```
hadoop jar ./hadoop-mapreduce-examples-3.3.4.jar wordcount /data/wordcount /output/wordcount/agent
```

* 在启用环境变量之后，向 hadoop 提交任务，

配置环境，使我们可以从本地电脑访问服务器的hdfs:[https://blog.csdn.net/m0_59092234/article/details/123740395](https://blog.csdn.net/m0_59092234/article/details/123740395)

访问服务器地址：`<span>192.168.1.204:9870</span>`从web端可以看出分词结果:

![](https://www.kdocs.cn/api/v3/office/copy/dE94VTJ1djkzVmpWMmdPdVlDc21BaWF4VThEaTJWQkVYWU5KNlBuOUNlN1FKQkJsUnpPQjExa0lHQVdzdFc1TDhWZGZQME1zR2NkdE51WHBaMVdaUjJqc2RVUWxpVE1uZlJzZEcwclVaaDdjZDlYZ2o5VmdpVll3NUo4VVVEakV2QzIvSWpDMmxKTVcrNTVsbUl2cjJLTDB0alozT1phVHRKS2g2NC91Y2NwUUMySUE2QkovSld3SGVoQ1E1Vm5XRFh1RDRwYjM0YzFENlRMS2F3UmlESWNJVkc0NzJuV3ZhVG1iRzB6cXRoaEpHRjBXRjVHenBiaFNzdUxGL2cyREdvSlljNDFFSW9nPQ==/attach/object/NUF2GAYA6Q)

* 可以看到输出

了 `<strong><span>方法名，执行时间，开始时间戳，线程号</span></strong>`：

![](https://www.kdocs.cn/api/v3/office/copy/dE94VTJ1djkzVmpWMmdPdVlDc21BaWF4VThEaTJWQkVYWU5KNlBuOUNlN1FKQkJsUnpPQjExa0lHQVdzdFc1TDhWZGZQME1zR2NkdE51WHBaMVdaUjJqc2RVUWxpVE1uZlJzZEcwclVaaDdjZDlYZ2o5VmdpVll3NUo4VVVEakV2QzIvSWpDMmxKTVcrNTVsbUl2cjJLTDB0alozT1phVHRKS2g2NC91Y2NwUUMySUE2QkovSld3SGVoQ1E1Vm5XRFh1RDRwYjM0YzFENlRMS2F3UmlESWNJVkc0NzJuV3ZhVG1iRzB6cXRoaEpHRjBXRjVHenBiaFNzdUxGL2cyREdvSlljNDFFSW9nPQ==/attach/object/6BRKEAYALY)

![](https://www.kdocs.cn/api/v3/office/copy/dE94VTJ1djkzVmpWMmdPdVlDc21BaWF4VThEaTJWQkVYWU5KNlBuOUNlN1FKQkJsUnpPQjExa0lHQVdzdFc1TDhWZGZQME1zR2NkdE51WHBaMVdaUjJqc2RVUWxpVE1uZlJzZEcwclVaaDdjZDlYZ2o5VmdpVll3NUo4VVVEakV2QzIvSWpDMmxKTVcrNTVsbUl2cjJLTDB0alozT1phVHRKS2g2NC91Y2NwUUMySUE2QkovSld3SGVoQ1E1Vm5XRFh1RDRwYjM0YzFENlRMS2F3UmlESWNJVkc0NzJuV3ZhVG1iRzB6cXRoaEpHRjBXRjVHenBiaFNzdUxGL2cyREdvSlljNDFFSW9nPQ==/attach/object/HRR2EAYAZE)

![](https://www.kdocs.cn/api/v3/office/copy/dE94VTJ1djkzVmpWMmdPdVlDc21BaWF4VThEaTJWQkVYWU5KNlBuOUNlN1FKQkJsUnpPQjExa0lHQVdzdFc1TDhWZGZQME1zR2NkdE51WHBaMVdaUjJqc2RVUWxpVE1uZlJzZEcwclVaaDdjZDlYZ2o5VmdpVll3NUo4VVVEakV2QzIvSWpDMmxKTVcrNTVsbUl2cjJLTDB0alozT1phVHRKS2g2NC91Y2NwUUMySUE2QkovSld3SGVoQ1E1Vm5XRFh1RDRwYjM0YzFENlRMS2F3UmlESWNJVkc0NzJuV3ZhVG1iRzB6cXRoaEpHRjBXRjVHenBiaFNzdUxGL2cyREdvSlljNDFFSW9nPQ==/attach/object/JJR2EAYALY)


生成trace文件：`<span>wordcount-log.txt</span>`

![](https://www.kdocs.cn/api/v3/office/copy/dE94VTJ1djkzVmpWMmdPdVlDc21BaWF4VThEaTJWQkVYWU5KNlBuOUNlN1FKQkJsUnpPQjExa0lHQVdzdFc1TDhWZGZQME1zR2NkdE51WHBaMVdaUjJqc2RVUWxpVE1uZlJzZEcwclVaaDdjZDlYZ2o5VmdpVll3NUo4VVVEakV2QzIvSWpDMmxKTVcrNTVsbUl2cjJLTDB0alozT1phVHRKS2g2NC91Y2NwUUMySUE2QkovSld3SGVoQ1E1Vm5XRFh1RDRwYjM0YzFENlRMS2F3UmlESWNJVkc0NzJuV3ZhVG1iRzB6cXRoaEpHRjBXRjVHenBiaFNzdUxGL2cyREdvSlljNDFFSW9nPQ==/attach/object/2ML2GAYAXQ)

分析之后可以看到，耗时较长的方法有 `<span>waitForCompletion(boolean)、Connection.waitForWork()、mapreduce.Job.connect() </span>`等等，其中 `<span>Connection.waitForWork</span>` 调用次数较多

**缺点：**

只有方法名，方法耗时，时间戳 信息，较难分析出性能瓶颈，如果能分析出各方法的调用关系，能够更好的进行分析
