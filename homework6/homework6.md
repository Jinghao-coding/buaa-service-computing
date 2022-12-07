**👋**

# 实验6：服务副本及静态分析技术

**实验内容** ：

1. **主流数据服务获取** :
2. 下载 HBase 和 Cassandra 项目的最新版本源代码，并编译打包
3. **副本技术相关类的静态分析** :
4. 使用 WALA 工具统计含有 Replica/Replication 关键字的类个数，并分析这些类之间的调用依赖；

## 源代码下载

Hbase 网址：[https://hbase.apache.org/](https://hbase.apache.org/)

cassandra 网址： [https://cassandra.apache.org/_/index.html](https://cassandra.apache.org/_/index.html)

## 编译

### Hbase编译：

将Hbase源码下载到作业目录下，本次采用的是HBase 2.4.15：

![](https://www.kdocs.cn/api/v3/office/copy/cWxsQzYrQ3dZeFVGTjFWamlUU21panEwcHQ2K1dZZVFGRW9iM2lybGcwbEVPT3YrdFB4MnNPVTNYTjh6YWR3WXB2WTQwNkJOanB5cGlDeWwwdHBDQk5CaXplTUNRWHd4Z3RHNnRMK24yS25CNlhWb1M1Z0xHb3dEd3RoUlVxVWZ6Mm5Va0xaYjRUdHhkZXFBNHlGRWJreWZ5bVI1c2ZyMDhNbUFGdkFVYkYwMHl6aWU4THFPY0Mva05aMVBhVjVVSllZdWVucXp5c1Y2c1MvQ2Nzci9zZDZ6NURtL2xDLzF6dXdsaDFNc3VxNzJwV3FsVHpIT2haOWkvVGlpVjlkUFVaeUkyT2tObDZRPQ==/attach/object/PL72AAYAZE)

maven版本：3.8.6

![](https://www.kdocs.cn/api/v3/office/copy/cWxsQzYrQ3dZeFVGTjFWamlUU21panEwcHQ2K1dZZVFGRW9iM2lybGcwbEVPT3YrdFB4MnNPVTNYTjh6YWR3WXB2WTQwNkJOanB5cGlDeWwwdHBDQk5CaXplTUNRWHd4Z3RHNnRMK24yS25CNlhWb1M1Z0xHb3dEd3RoUlVxVWZ6Mm5Va0xaYjRUdHhkZXFBNHlGRWJreWZ5bVI1c2ZyMDhNbUFGdkFVYkYwMHl6aWU4THFPY0Mva05aMVBhVjVVSllZdWVucXp5c1Y2c1MvQ2Nzci9zZDZ6NURtL2xDLzF6dXdsaDFNc3VxNzJwV3FsVHpIT2haOWkvVGlpVjlkUFVaeUkyT2tObDZRPQ==/attach/object/2372AAYA24)

进入 hbase 工程目录下，使用命令：`mvn package -DskipTests` 编译 jar 包

![](https://www.kdocs.cn/api/v3/office/copy/cWxsQzYrQ3dZeFVGTjFWamlUU21panEwcHQ2K1dZZVFGRW9iM2lybGcwbEVPT3YrdFB4MnNPVTNYTjh6YWR3WXB2WTQwNkJOanB5cGlDeWwwdHBDQk5CaXplTUNRWHd4Z3RHNnRMK24yS25CNlhWb1M1Z0xHb3dEd3RoUlVxVWZ6Mm5Va0xaYjRUdHhkZXFBNHlGRWJreWZ5bVI1c2ZyMDhNbUFGdkFVYkYwMHl6aWU4THFPY0Mva05aMVBhVjVVSllZdWVucXp5c1Y2c1MvQ2Nzci9zZDZ6NURtL2xDLzF6dXdsaDFNc3VxNzJwV3FsVHpIT2haOWkvVGlpVjlkUFVaeUkyT2tObDZRPQ==/attach/object/OYDKCAYAXQ)

编译完成：

![](https://www.kdocs.cn/api/v3/office/copy/cWxsQzYrQ3dZeFVGTjFWamlUU21panEwcHQ2K1dZZVFGRW9iM2lybGcwbEVPT3YrdFB4MnNPVTNYTjh6YWR3WXB2WTQwNkJOanB5cGlDeWwwdHBDQk5CaXplTUNRWHd4Z3RHNnRMK24yS25CNlhWb1M1Z0xHb3dEd3RoUlVxVWZ6Mm5Va0xaYjRUdHhkZXFBNHlGRWJreWZ5bVI1c2ZyMDhNbUFGdkFVYkYwMHl6aWU4THFPY0Mva05aMVBhVjVVSllZdWVucXp5c1Y2c1MvQ2Nzci9zZDZ6NURtL2xDLzF6dXdsaDFNc3VxNzJwV3FsVHpIT2haOWkvVGlpVjlkUFVaeUkyT2tObDZRPQ==/attach/object/SO3KCAYAUU)

### 2. cassandra编译

进入 cassandra 工程目录下，使用命令：`<span>ant build</span>` 编译工程，生成 jar 包：

![](https://www.kdocs.cn/api/v3/office/copy/cWxsQzYrQ3dZeFVGTjFWamlUU21panEwcHQ2K1dZZVFGRW9iM2lybGcwbEVPT3YrdFB4MnNPVTNYTjh6YWR3WXB2WTQwNkJOanB5cGlDeWwwdHBDQk5CaXplTUNRWHd4Z3RHNnRMK24yS25CNlhWb1M1Z0xHb3dEd3RoUlVxVWZ6Mm5Va0xaYjRUdHhkZXFBNHlGRWJreWZ5bVI1c2ZyMDhNbUFGdkFVYkYwMHl6aWU4THFPY0Mva05aMVBhVjVVSllZdWVucXp5c1Y2c1MvQ2Nzci9zZDZ6NURtL2xDLzF6dXdsaDFNc3VxNzJwV3FsVHpIT2haOWkvVGlpVjlkUFVaeUkyT2tObDZRPQ==/attach/object/Q4EKCAYBBQ)

编译成功：

![](https://www.kdocs.cn/api/v3/office/copy/cWxsQzYrQ3dZeFVGTjFWamlUU21panEwcHQ2K1dZZVFGRW9iM2lybGcwbEVPT3YrdFB4MnNPVTNYTjh6YWR3WXB2WTQwNkJOanB5cGlDeWwwdHBDQk5CaXplTUNRWHd4Z3RHNnRMK24yS25CNlhWb1M1Z0xHb3dEd3RoUlVxVWZ6Mm5Va0xaYjRUdHhkZXFBNHlGRWJreWZ5bVI1c2ZyMDhNbUFGdkFVYkYwMHl6aWU4THFPY0Mva05aMVBhVjVVSllZdWVucXp5c1Y2c1MvQ2Nzci9zZDZ6NURtL2xDLzF6dXdsaDFNc3VxNzJwV3FsVHpIT2haOWkvVGlpVjlkUFVaeUkyT2tObDZRPQ==/attach/object/PC3KCAYA2Q)

生成的 jar 包位于工程的 build 目录下;

## 使用 WALA 进行分析

### 3.1 统计含有 Replica/Replication 关键字的类个数

新建项目：

![](https://www.kdocs.cn/api/v3/office/copy/cWxsQzYrQ3dZeFVGTjFWamlUU21panEwcHQ2K1dZZVFGRW9iM2lybGcwbEVPT3YrdFB4MnNPVTNYTjh6YWR3WXB2WTQwNkJOanB5cGlDeWwwdHBDQk5CaXplTUNRWHd4Z3RHNnRMK24yS25CNlhWb1M1Z0xHb3dEd3RoUlVxVWZ6Mm5Va0xaYjRUdHhkZXFBNHlGRWJreWZ5bVI1c2ZyMDhNbUFGdkFVYkYwMHl6aWU4THFPY0Mva05aMVBhVjVVSllZdWVucXp5c1Y2c1MvQ2Nzci9zZDZ6NURtL2xDLzF6dXdsaDFNc3VxNzJwV3FsVHpIT2haOWkvVGlpVjlkUFVaeUkyT2tObDZRPQ==/attach/object/BHYKCAYBBQ)

通过pom.xml下载依赖：

```
<?xml version="1.0" encoding="UTF-8"?>
<project xmlns="http://maven.apache.org/POM/4.0.0"
         xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
         xsi:schemaLocation="http://maven.apache.org/POM/4.0.0 http://maven.apache.org/xsd/maven-4.0.0.xsd">
    <modelVersion>4.0.0</modelVersion>

    <groupId>org.example</groupId>
    <artifactId>wala-static-analysis</artifactId>
    <version>1.0-SNAPSHOT</version>

    <properties>
        <maven.compiler.source>8</maven.compiler.source>
        <maven.compiler.target>8</maven.compiler.target>
        <project.build.sourceEncoding>UTF-8</project.build.sourceEncoding>
    </properties>

    <dependencies>

        <!-- https://mvnrepository.com/artifact/com.ibm.wala/com.ibm.wala.util -->
        <dependency>
            <groupId>com.ibm.wala</groupId>
            <artifactId>com.ibm.wala.util</artifactId>
            <version>1.5.5</version>
        </dependency>
        <!-- https://mvnrepository.com/artifact/com.ibm.wala/com.ibm.wala.shrike -->
        <dependency>
            <groupId>com.ibm.wala</groupId>
            <artifactId>com.ibm.wala.shrike</artifactId>
            <version>1.5.5</version>
        </dependency>
        <!-- https://mvnrepository.com/artifact/com.ibm.wala/com.ibm.wala.core -->
        <dependency>
            <groupId>com.ibm.wala</groupId>
            <artifactId>com.ibm.wala.core</artifactId>
            <version>1.5.5</version>
        </dependency>
        <!-- https://mvnrepository.com/artifact/com.ibm.wala/com.ibm.wala.cast -->
        <dependency>
            <groupId>com.ibm.wala</groupId>
            <artifactId>com.ibm.wala.cast</artifactId>
            <version>1.5.5</version>
        </dependency>
        <dependency>
            <groupId>junit</groupId>
            <artifactId>junit</artifactId>
            <version>4.13</version>
            <scope>test</scope>
        </dependency>

    </dependencies>

</project>
```

编写代码进行分析：

```
package org.example;

import com.ibm.wala.classLoader.IClass;
import com.ibm.wala.ipa.callgraph.AnalysisScope;
import com.ibm.wala.ipa.cha.ClassHierarchy;
import com.ibm.wala.ipa.cha.ClassHierarchyException;
import com.ibm.wala.ipa.cha.ClassHierarchyFactory;
import com.ibm.wala.shrikeCT.InvalidClassFileException;
import com.ibm.wala.util.CancelException;
import com.ibm.wala.util.config.AnalysisScopeReader;
import com.ibm.wala.util.io.FileProvider;

import java.io.File;
import java.io.IOException;

public class Analyse {

    public static void main(String args[]) throws IOException, ClassHierarchyException, IllegalArgumentException, InvalidClassFileException, CancelException {
//        String hbaseClient = "F:\\mygithub\\course\\jinghao-service-compute-course\\homework6\\code\\wala-static-analysis\\data\\hbase-client-3.0.0-alpha-4-SNAPSHOT.jar";
//        String hbaseServer = "F:\\mygithub\\course\\jinghao-service-compute-course\\homework6\\code\\wala-static-analysis\\data\\hbase-server-3.0.0-alpha-4-SNAPSHOT.jar";
        String jarPath = "F:\\mygithub\\course\\jinghao-service-compute-course\\homework6\\code\\wala-static-analysis\\data\\apache-cassandra-4.2-SNAPSHOT.jar";

        System.out.print(jarPath);
        System.out.println();
        // 获得一个文件
        File exFile = new FileProvider().getFile("Java60RegressionExclusions.txt");

        // 将分析域存到文件中
        AnalysisScope scope = AnalysisScopeReader.makeJavaBinaryAnalysisScope(jarPath, exFile);

        // 构建ClassHierarchy，相当与类的一个层级结构
        ClassHierarchy cha = ClassHierarchyFactory.make(scope);

        // 循环遍历每一个类
        int num = 0;
        for(IClass klass : cha) {
            // 打印类名
            if (klass.getName().toString().contains("Replic")) {
                System.out.println(klass.getName().toString());
                num += 1;
            }
        }
        System.out.println("Class Name Contain Replica/Replication num: " + num);
    }
}
```

#### Cassandra

有 **75**个类含有 Replica/Replication 关键字

![](https://www.kdocs.cn/api/v3/office/copy/cWxsQzYrQ3dZeFVGTjFWamlUU21panEwcHQ2K1dZZVFGRW9iM2lybGcwbEVPT3YrdFB4MnNPVTNYTjh6YWR3WXB2WTQwNkJOanB5cGlDeWwwdHBDQk5CaXplTUNRWHd4Z3RHNnRMK24yS25CNlhWb1M1Z0xHb3dEd3RoUlVxVWZ6Mm5Va0xaYjRUdHhkZXFBNHlGRWJreWZ5bVI1c2ZyMDhNbUFGdkFVYkYwMHl6aWU4THFPY0Mva05aMVBhVjVVSllZdWVucXp5c1Y2c1MvQ2Nzci9zZDZ6NURtL2xDLzF6dXdsaDFNc3VxNzJwV3FsVHpIT2haOWkvVGlpVjlkUFVaeUkyT2tObDZRPQ==/attach/object/XHYKCAYCBQ)

#### HBase

* `<span>hbase client</span>`，有 **23** 个类含有 Replica/Replication 关键字

![](https://www.kdocs.cn/api/v3/office/copy/cWxsQzYrQ3dZeFVGTjFWamlUU21panEwcHQ2K1dZZVFGRW9iM2lybGcwbEVPT3YrdFB4MnNPVTNYTjh6YWR3WXB2WTQwNkJOanB5cGlDeWwwdHBDQk5CaXplTUNRWHd4Z3RHNnRMK24yS25CNlhWb1M1Z0xHb3dEd3RoUlVxVWZ6Mm5Va0xaYjRUdHhkZXFBNHlGRWJreWZ5bVI1c2ZyMDhNbUFGdkFVYkYwMHl6aWU4THFPY0Mva05aMVBhVjVVSllZdWVucXp5c1Y2c1MvQ2Nzci9zZDZ6NURtL2xDLzF6dXdsaDFNc3VxNzJwV3FsVHpIT2haOWkvVGlpVjlkUFVaeUkyT2tObDZRPQ==/attach/object/DLY2CAYALU)

* 对于 `<span>hbase server</span>`，有 **70** 个类含有 Replica/Replication 关键字

![](https://www.kdocs.cn/api/v3/office/copy/cWxsQzYrQ3dZeFVGTjFWamlUU21panEwcHQ2K1dZZVFGRW9iM2lybGcwbEVPT3YrdFB4MnNPVTNYTjh6YWR3WXB2WTQwNkJOanB5cGlDeWwwdHBDQk5CaXplTUNRWHd4Z3RHNnRMK24yS25CNlhWb1M1Z0xHb3dEd3RoUlVxVWZ6Mm5Va0xaYjRUdHhkZXFBNHlGRWJreWZ5bVI1c2ZyMDhNbUFGdkFVYkYwMHl6aWU4THFPY0Mva05aMVBhVjVVSllZdWVucXp5c1Y2c1MvQ2Nzci9zZDZ6NURtL2xDLzF6dXdsaDFNc3VxNzJwV3FsVHpIT2haOWkvVGlpVjlkUFVaeUkyT2tObDZRPQ==/attach/object/73YKCAYA6A)

### 3.2 分析类之间的调用依赖

wala 官方给出了一个构建类之间层次结构，并使用 graphviz 工具，将类之间关系可视化的样例代码

安装graphviz工具：

![](https://www.kdocs.cn/api/v3/office/copy/cWxsQzYrQ3dZeFVGTjFWamlUU21panEwcHQ2K1dZZVFGRW9iM2lybGcwbEVPT3YrdFB4MnNPVTNYTjh6YWR3WXB2WTQwNkJOanB5cGlDeWwwdHBDQk5CaXplTUNRWHd4Z3RHNnRMK24yS25CNlhWb1M1Z0xHb3dEd3RoUlVxVWZ6Mm5Va0xaYjRUdHhkZXFBNHlGRWJreWZ5bVI1c2ZyMDhNbUFGdkFVYkYwMHl6aWU4THFPY0Mva05aMVBhVjVVSllZdWVucXp5c1Y2c1MvQ2Nzci9zZDZ6NURtL2xDLzF6dXdsaDFNc3VxNzJwV3FsVHpIT2haOWkvVGlpVjlkUFVaeUkyT2tObDZRPQ==/attach/object/E34KCAYA3U)

代码如下：

```
package org.example;

import com.ibm.wala.classLoader.IClass;
import com.ibm.wala.core.tests.callGraph.CallGraphTestUtil;
import com.ibm.wala.examples.properties.WalaExamplesProperties;
import com.ibm.wala.ipa.callgraph.AnalysisScope;
import com.ibm.wala.ipa.cha.ClassHierarchy;
import com.ibm.wala.ipa.cha.ClassHierarchyFactory;
import com.ibm.wala.ipa.cha.IClassHierarchy;
import com.ibm.wala.properties.WalaProperties;
import com.ibm.wala.types.ClassLoaderReference;
import com.ibm.wala.util.WalaException;
import com.ibm.wala.util.collections.CollectionFilter;
import com.ibm.wala.util.config.AnalysisScopeReader;
import com.ibm.wala.util.debug.Assertions;
import com.ibm.wala.util.graph.Graph;
import com.ibm.wala.util.graph.GraphSlicer;
import com.ibm.wala.util.graph.impl.SlowSparseNumberedGraph;
import com.ibm.wala.util.io.FileProvider;
import com.ibm.wala.viz.DotUtil;
import com.ibm.wala.viz.PDFViewUtil;

import java.io.File;
import java.io.IOException;
import java.util.Collection;
import java.util.Properties;
import java.util.function.Predicate;

/**
 * This simple example WALA application builds a TypeHierarchy and fires off ghostview to viz a DOT
 * representation.
 *
 * @author sfink
 */
public class AnalysisHierarchy {
    private static final String EXCLUSIONS = "java\\/awt\\/.*\n" +
            "javax\\/swing\\/.*\n" +
            "sun\\/awt\\/.*\n" +
            "sun\\/swing\\/.*\n" +
            "com\\/sun\\/.*\n" +
            "sun\\/.*\n" +
            "org\\/netbeans\\/.*\n" +
            "org\\/openide\\/.*\n" +
            "com\\/ibm\\/crypto\\/.*\n" +
            "com\\/ibm\\/security\\/.*\n" +
            "org\\/apache\\/xerces\\/.*\n" +
            "java\\/security\\/.*\n" +
            "java\\/util\\/.*\n" +
            "java\\/io\\/.*\n" +
            "java\\/lang\\/.*\n" +
            "";

    // This example takes one command-line argument, so args[1] should be the "-classpath" parameter
    static final int CLASSPATH_INDEX = 1;

    public static final String DOT_FILE = "hbaseServer.dt";

    private static final String PDF_FILE = "hbaseServer.pdf";

    public static Properties p;

    static {
        try {
            p = WalaProperties.loadProperties();
            p.putAll(WalaExamplesProperties.loadProperties());
        } catch (WalaException e) {
            e.printStackTrace();
            Assertions.UNREACHABLE();
        }
    }

    public static void main(String[] args) throws IOException {
        String OUTPUT_DIR = "F:\\mygithub\\course\\jinghao-service-compute-course\\homework6\\code\\wala-static-analysis\\output";
        try {
            String hbaseClient = "F:\\mygithub\\course\\jinghao-service-compute-course\\homework6\\code\\wala-static-analysis\\data\\hbase-client-3.0.0-alpha-4-SNAPSHOT.jar";
            String hbaseServer = "F:\\mygithub\\course\\jinghao-service-compute-course\\homework6\\code\\wala-static-analysis\\data\\hbase-server-3.0.0-alpha-4-SNAPSHOT.jar";
            String casspath = "F:\\mygithub\\course\\jinghao-service-compute-course\\homework6\\code\\wala-static-analysis\\data\\apache-cassandra-4.2-SNAPSHOT.jar";
            AnalysisScope scope =
                    AnalysisScopeReader.makeJavaBinaryAnalysisScope(
                            hbaseServer, (new FileProvider()).getFile(CallGraphTestUtil.REGRESSION_EXCLUSIONS));

            // scope.setExclusions(new FileOfClasses(new ByteArrayInputStream(EXCLUSIONS.getBytes()))); // 排除部分类
            // invoke WALA to build a class hierarchy
            ClassHierarchy cha = ClassHierarchyFactory.make(scope);

            Graph<IClass> g = typeHierarchy2Graph(cha);

            g = pruneForAppLoader(g);
            String dotFile = OUTPUT_DIR + File.separatorChar + DOT_FILE;
            String pdfFile = OUTPUT_DIR + File.separatorChar + PDF_FILE;
            String dotExe = "D:\\ProgramFiles\\Graphviz2.38\\bin\\dot.exe";
            //String gvExe = p.getProperty(WalaExamplesProperties.PDFVIEW_EXE);
            DotUtil.dotify(g, null, dotFile, pdfFile, dotExe);

        } catch (WalaException e) {
            e.printStackTrace();
        }
    }



    public static <T> Graph<T> pruneGraph(Graph<T> g, Predicate<T> f) {
        Collection<T> slice = GraphSlicer.slice(g, f);
        return GraphSlicer.prune(g, new CollectionFilter<>(slice));
    }

    /** Restrict g to nodes from the Application loader */
    public static Graph<IClass> pruneForAppLoader(Graph<IClass> g) {
        Predicate<IClass> f =
                c -> (c.getClassLoader().getReference().equals(ClassLoaderReference.Application));
        return pruneGraph(g, f);
    }

    /**
     * Validate that the command-line arguments obey the expected usage.
     *
     * <p>Usage: args[0] : "-classpath" args[1] : String, a ";"-delimited class path
     *
     * @throws UnsupportedOperationException if command-line is malformed.
     */
    public static void validateCommandLine(String[] args) {
        if (args.length < 2) {
            throw new UnsupportedOperationException("must have at least 2 command-line arguments");
        }
        if (!args[0].equals("-classpath")) {
            throw new UnsupportedOperationException(
                    "invalid command-line, args[0] should be -classpath, but is " + args[0]);
        }
    }
    public static boolean checkName(String name) {
        return name.contains("Replica");
    }

    /**
     * Return a view of an {@link IClassHierarchy} as a {@link Graph}, with edges from classes to
     * immediate subtypes
     */
    public static Graph<IClass> typeHierarchy2Graph(IClassHierarchy cha) {
        Graph<IClass> result = SlowSparseNumberedGraph.make();
        for (IClass c : cha) {
            if (checkName(c.getName().toString()))
                result.addNode(c);
        }
        for (IClass c : cha) {
            if (checkName(c.getName().toString())){
                for (IClass x : cha.getImmediateSubclasses(c)) {
                    if (checkName(x.getName().toString()))
                        result.addEdge(c, x);
                }
                if (c.isInterface()) {
                    for (IClass x : cha.getImplementors(c.getReference())) {
                        if (checkName(x.getName().toString()))
                            result.addEdge(c, x);
                    }
                }
            }
        }
        return result;
    }
}
```

#### 调用依赖结果

![](https://www.kdocs.cn/api/v3/office/copy/cWxsQzYrQ3dZeFVGTjFWamlUU21panEwcHQ2K1dZZVFGRW9iM2lybGcwbEVPT3YrdFB4MnNPVTNYTjh6YWR3WXB2WTQwNkJOanB5cGlDeWwwdHBDQk5CaXplTUNRWHd4Z3RHNnRMK24yS25CNlhWb1M1Z0xHb3dEd3RoUlVxVWZ6Mm5Va0xaYjRUdHhkZXFBNHlGRWJreWZ5bVI1c2ZyMDhNbUFGdkFVYkYwMHl6aWU4THFPY0Mva05aMVBhVjVVSllZdWVucXp5c1Y2c1MvQ2Nzci9zZDZ6NURtL2xDLzF6dXdsaDFNc3VxNzJwV3FsVHpIT2haOWkvVGlpVjlkUFVaeUkyT2tObDZRPQ==/attach/object/CX52CAYAEM)

#### HBase

**client:**

![](https://www.kdocs.cn/api/v3/office/copy/cWxsQzYrQ3dZeFVGTjFWamlUU21panEwcHQ2K1dZZVFGRW9iM2lybGcwbEVPT3YrdFB4MnNPVTNYTjh6YWR3WXB2WTQwNkJOanB5cGlDeWwwdHBDQk5CaXplTUNRWHd4Z3RHNnRMK24yS25CNlhWb1M1Z0xHb3dEd3RoUlVxVWZ6Mm5Va0xaYjRUdHhkZXFBNHlGRWJreWZ5bVI1c2ZyMDhNbUFGdkFVYkYwMHl6aWU4THFPY0Mva05aMVBhVjVVSllZdWVucXp5c1Y2c1MvQ2Nzci9zZDZ6NURtL2xDLzF6dXdsaDFNc3VxNzJwV3FsVHpIT2haOWkvVGlpVjlkUFVaeUkyT2tObDZRPQ==/attach/object/B36KCAYBXQ)

**server:**

![](https://www.kdocs.cn/api/v3/office/copy/cWxsQzYrQ3dZeFVGTjFWamlUU21panEwcHQ2K1dZZVFGRW9iM2lybGcwbEVPT3YrdFB4MnNPVTNYTjh6YWR3WXB2WTQwNkJOanB5cGlDeWwwdHBDQk5CaXplTUNRWHd4Z3RHNnRMK24yS25CNlhWb1M1Z0xHb3dEd3RoUlVxVWZ6Mm5Va0xaYjRUdHhkZXFBNHlGRWJreWZ5bVI1c2ZyMDhNbUFGdkFVYkYwMHl6aWU4THFPY0Mva05aMVBhVjVVSllZdWVucXp5c1Y2c1MvQ2Nzci9zZDZ6NURtL2xDLzF6dXdsaDFNc3VxNzJwV3FsVHpIT2haOWkvVGlpVjlkUFVaeUkyT2tObDZRPQ==/attach/object/7P52CAYAYE)

#### Cassandra

![](https://www.kdocs.cn/api/v3/office/copy/cWxsQzYrQ3dZeFVGTjFWamlUU21panEwcHQ2K1dZZVFGRW9iM2lybGcwbEVPT3YrdFB4MnNPVTNYTjh6YWR3WXB2WTQwNkJOanB5cGlDeWwwdHBDQk5CaXplTUNRWHd4Z3RHNnRMK24yS25CNlhWb1M1Z0xHb3dEd3RoUlVxVWZ6Mm5Va0xaYjRUdHhkZXFBNHlGRWJreWZ5bVI1c2ZyMDhNbUFGdkFVYkYwMHl6aWU4THFPY0Mva05aMVBhVjVVSllZdWVucXp5c1Y2c1MvQ2Nzci9zZDZ6NURtL2xDLzF6dXdsaDFNc3VxNzJwV3FsVHpIT2haOWkvVGlpVjlkUFVaeUkyT2tObDZRPQ==/attach/object/YL52CAYB64)
