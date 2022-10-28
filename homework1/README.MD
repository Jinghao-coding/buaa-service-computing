# 0、运行环境

* OS：64bit windows10
* 编辑器 ：Idea 2022.1.3
* Java : 1.8.0_331

# 1、开发环境配置

## 安装 JDK 环境

下载安装 JDK8 环境，下载地址：[https://www.oracle.com/java/technologies/javase/javase-jdk8-downloads.html](https://www.oracle.com/java/technologies/javase/javase-jdk8-downloads.html)

在下载页面中，找到对应的平台进行下载，并完成安装

安装之后在终端检查 java 版本：

![](https://n6frw6l6f0.feishu.cn/space/api/box/stream/download/asynccode/?code=ZGVmMzMzODMxNzc4ODI0MWZhMzhmYTYyNmM5ZjIyNDJfUEV2eDJGNXhNekM3QWRrdTdVRXRrRjRwdzYxMnA4WDJfVG9rZW46Ym94Y254V2NER3J6TVF0aE5haFBxUHREU0N3XzE2NjY5NzM3MDk6MTY2Njk3NzMwOV9WNA)

## 安装 ZooKeeper

由于 dubbo 需要在 zookeeper 中完成注册，因此需要搭建 zookeeper

zookeeper 下载地址：[https://zookeeper.apache.org/](https://zookeeper.apache.org/) 下载 ZooKeeper

[Windows10安装Zookeeper教程_JMzz的博客-CSDN博客_win10安装zookeeper](https://blog.csdn.net/weixin_45740811/article/details/125489253)

## 创建 Dubbo 工程项目

前往 dubbo 官方 github 仓库：[https://github.com/apache/dubbo](https://github.com/apache/dubbo) ReadMe 中的 Getting started 说明，引入 dubbo 依赖：

在项目根目录下的 pom.xml 文件中加入以下依赖项：

```undefined
<properties>
    <dubbo.version>3.0.7</dubbo.version>
</properties>

<dependencies>
    <dependency>
        <groupId>org.apache.dubbo</groupId>
        <artifactId>dubbo</artifactId>
        <version>${dubbo.version}</version>
    </dependency>
    <dependency>
        <groupId>org.apache.dubbo</groupId>
        <artifactId>dubbo-dependencies-zookeeper</artifactId>
        <version>${dubbo.version}</version>
        <type>pom</type>
    </dependency>
</dependencies>
```

下载maven配置

![](https://n6frw6l6f0.feishu.cn/space/api/box/stream/download/asynccode/?code=ODcyZmE1NjI0N2ZhNzZlOTViZmFkYjkxNjg3ODlmYjBfMm1QcEVrT3VCdVo4WG43cDZjMFR4VVFtcmRYVnRaa0VfVG9rZW46Ym94Y24zZEpnNUZTUVNSVzRrb0pKZUhhcnQwXzE2NjY5NzM3MDk6MTY2Njk3NzMwOV9WNA)

# 2. 服务接口定义、实现

创建文件 Person.java 定义 Person 接口

```Java
package org.example.api;

public interface Person {

    public void setName(String name);

    public String getName();

    public void setAge(int age);

    public int getAge();

    public void setGender(boolean gender);

    public boolean getGender();

    public String sayHello();

}
```

![](https://n6frw6l6f0.feishu.cn/space/api/box/stream/download/asynccode/?code=ZjZhZjZlMmEyYzliN2M1MmVhNGEyYzEyOTVkOGRjOWNfRmlVNGxlRmVHYmgxdXBTNHJjMkhhSktiMnptbVI5dndfVG9rZW46Ym94Y251d3lkS0pQRVJGckJrN2RPUUtqRlRqXzE2NjY5NzM3MDk6MTY2Njk3NzMwOV9WNA)

在provider目录下 创建文件 PersonImpl.java，对定义的 Person 接口完成实现

```Java
package org.example.provider;

import org.apache.dubbo.config.annotation.DubboService;
import org.example.api.Person;

@DubboService
public class PersonImpl implements Person {
    String name;
    int age;
    boolean gender;

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public int getAge() {
        return age;
    }

    public void setGender(boolean gender) {
        this.gender = gender;
    }

    public boolean getGender() {
        return gender;
    }

    @Override
    public String sayHello() {
        return (" RPC Service: Hello world!" + this.name);
    }
}
```

![](https://n6frw6l6f0.feishu.cn/space/api/box/stream/download/asynccode/?code=OTJkNTg2MWVjNTY4NDU4YTQ1ODg3NDgyM2I1N2U5MDRfb09Xa2RzNVdUektEcHIxOW5RSUdwTUhzamZyMnpQRk5fVG9rZW46Ym94Y25FQVVLU2J3QXljT3ZxRXFJWGl3U2tlXzE2NjY5NzM3MDk6MTY2Njk3NzMwOV9WNA)

# 3、provider定义及实现

#### 编写 Dubbo Service 代码

定义 zookeeper Host 地址：

```Java
private static String zookeeperHost= System
        .getProperty("zookeeper.address", "127.0.0.1");
      
private static String zookeeperPort= System.getProperty("zookeeper.port",
        "2181");
```

编写 main 函数，先进行注册中心的配置：

```Java
public static void main(String[] args) throws Exception {
    ServiceConfig<Person> service = new ServiceConfig<>();
    service.setApplication(new ApplicationConfig("first-dubbo-provider"));
    service.setRegistry(new RegistryConfig(
            "zookeeper://" + zookeeperHost + ":" + zookeeperPort));
    service.setInterface(Person.class);
    service.setRef(new PersonImpl());
    service.export();

    System.out.println("dubbo service started");
    new CountDownLatch(1).await();
}
```

定义接口和具体实现：

```Java
service.setInterface(Person.class);
service.setRef(new PersonImpl());
```

启动 Dubbo Service：

```Java
service.export();
System.out.println("dubbo service started");
new CountDownLatch(1).await();
```

完成 zookeeper 配置后，启动服务端，可以看到提示 dubbo server 成功启动：

![](https://n6frw6l6f0.feishu.cn/space/api/box/stream/download/asynccode/?code=MjM4NzNiZjk3MTNkMzQwOTZhNWZjMGNiZjIwOThiODhfdkFKWEJFMzZrZDNVaHQ2TU95SmtLc0RFREtsTVN4RnpfVG9rZW46Ym94Y25wUDFVaW9JQU5zSkY1YXF6Tm9RNkVlXzE2NjY5NzM3MDk6MTY2Njk3NzMwOV9WNA)

# 4、 编写 Dubbo 调用端，发起 rpc 调用

```Java
package org.example.client;

import org.apache.dubbo.config.ApplicationConfig;
import org.apache.dubbo.config.ReferenceConfig;
import org.apache.dubbo.config.RegistryConfig;
import org.example.api.Person;

public class Application {
    private static String zookeeperHost= System
            .getProperty("zookeeper.address", "127.0.0.1");
    private static String zookeeperPort= System.getProperty("zookeeper.port",
            "2181");

    public static void main(String[] args) {
        ReferenceConfig<Person> reference = new ReferenceConfig<>();
        reference.setApplication(new ApplicationConfig("first-dubbo-consumer"));
        reference.setRegistry(new RegistryConfig(
                "zookeeper://" + zookeeperHost + ":" + zookeeperPort));
        reference.setInterface(Person.class);
        Person service = reference.get();
        service.setName("Wang Jinghao");
        String message = service.sayHello();
        System.out.println(message);
    }
}
```

* 调用端首先通过 reference 获得远端对象 `service`
* 调用远端对象的 `setName` 方法，设置对象姓名：wangjinghao
* 之后调用对象的 `sayHello` 方法，获得远端对象的返回值，并在终端打印：

CODE: 
