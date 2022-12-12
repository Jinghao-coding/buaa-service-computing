# 实验3： web服务开发与调用

**🔔**

实验内容：

**基于 Apache Axis2 提供 Web 服务，生成 WSDL 文件，调用服务**

1. **Web服务开发：** 下载Apache Axis2， 参照其文档将下面类的方法对外提供Web服务，生成 WSDL 文件；
2. **Web服务调用：** 通过网络调用上述Web服务的方法并获得返回结果，其中输入参数name设置为自己学号；
3. **撰写实验报告：** 将整个实验过程的关键步骤进行描述并截图，书写实验报告

## **1. 安装 Tomcat**

Axis 2 需要依赖 Tomcat 提供 Web 服务，因此需要先搭建 Tomcat 服务器。

从 Tomcat 官网下载 tomcat 9.0.69 版本安装包，解压后运行 startup.sh 脚本：[https://tomcat.apache.org/download-90.cgi](https://tomcat.apache.org/download-90.cgi)

![](https://www.kdocs.cn/api/v3/office/copy/NFpOWmFJWkZvUjZZR3dvalQvbHRqa29NKzdGS3ZDVjYzK2w4UUhBY1NUL0lRK1pLREo5bE1NTDc2Wkx5N3dHNjhObzZFYmNTcTlpVDBwZnBOYk0wVEdIR2hubWlLNWRyY2g5VFltS2xLcHpHYXNkanhRbFdnS3JhMmo0RFZhNzV0aDFYZXd6T1VLaGFtVEx5UTBubDVpZURNbW1idG9EQ2ordlVjbWhJMEhtc0hJZ0NCcGNocmJSeFBiY2lzdHFQVSt2dHgxNERkZ3p0aER0eUFWck0yVXE1SGZ0NWNkam5SR0dYdVBJbmp1ZGMyMjlLUnZPN2p5Sm1Xb1gvVCtKMkY2TS9EblZsVmZJPQ==/attach/object/73RIQAYAGA)

启动tomcat服务后，访问 [http://localhost:8080](http://localhost:8080/) ，

可以看到 Apache Tomcat 的初始界面，tomcat 搭建成功

![](https://www.kdocs.cn/api/v3/office/copy/NFpOWmFJWkZvUjZZR3dvalQvbHRqa29NKzdGS3ZDVjYzK2w4UUhBY1NUL0lRK1pLREo5bE1NTDc2Wkx5N3dHNjhObzZFYmNTcTlpVDBwZnBOYk0wVEdIR2hubWlLNWRyY2g5VFltS2xLcHpHYXNkanhRbFdnS3JhMmo0RFZhNzV0aDFYZXd6T1VLaGFtVEx5UTBubDVpZURNbW1idG9EQ2ordlVjbWhJMEhtc0hJZ0NCcGNocmJSeFBiY2lzdHFQVSt2dHgxNERkZ3p0aER0eUFWck0yVXE1SGZ0NWNkam5SR0dYdVBJbmp1ZGMyMjlLUnZPN2p5Sm1Xb1gvVCtKMkY2TS9EblZsVmZJPQ==/attach/object/ILSIQAYAIY)


## **2. 安装 axis2**

Apache Axis2的官方文档为：[http://axis.apache.org/axis2/java/core/](http://axis.apache.org/axis2/java/core/)

下载IDEA所需要的配置文件：[http://axis.apache.org/axis2/java/core/download.html](http://axis.apache.org/axis2/java/core/download.html)

![](https://www.kdocs.cn/api/v3/office/copy/NFpOWmFJWkZvUjZZR3dvalQvbHRqa29NKzdGS3ZDVjYzK2w4UUhBY1NUL0lRK1pLREo5bE1NTDc2Wkx5N3dHNjhObzZFYmNTcTlpVDBwZnBOYk0wVEdIR2hubWlLNWRyY2g5VFltS2xLcHpHYXNkanhRbFdnS3JhMmo0RFZhNzV0aDFYZXd6T1VLaGFtVEx5UTBubDVpZURNbW1idG9EQ2ordlVjbWhJMEhtc0hJZ0NCcGNocmJSeFBiY2lzdHFQVSt2dHgxNERkZ3p0aER0eUFWck0yVXE1SGZ0NWNkam5SR0dYdVBJbmp1ZGMyMjlLUnZPN2p5Sm1Xb1gvVCtKMkY2TS9EblZsVmZJPQ==/attach/object/ZTMIQAYANY)

目前，axis2 最新的版本为 1.8.2，下载 `<span>axis2-1.8.2-war.zip </span>`包，解包后可以获得一个 war 包

将该 war 包放置在 Tomcat 的 webapps 目录下，axis2 安装到 Tomcat 中。

![](https://www.kdocs.cn/api/v3/office/copy/NFpOWmFJWkZvUjZZR3dvalQvbHRqa29NKzdGS3ZDVjYzK2w4UUhBY1NUL0lRK1pLREo5bE1NTDc2Wkx5N3dHNjhObzZFYmNTcTlpVDBwZnBOYk0wVEdIR2hubWlLNWRyY2g5VFltS2xLcHpHYXNkanhRbFdnS3JhMmo0RFZhNzV0aDFYZXd6T1VLaGFtVEx5UTBubDVpZURNbW1idG9EQ2ordlVjbWhJMEhtc0hJZ0NCcGNocmJSeFBiY2lzdHFQVSt2dHgxNERkZ3p0aER0eUFWck0yVXE1SGZ0NWNkam5SR0dYdVBJbmp1ZGMyMjlLUnZPN2p5Sm1Xb1gvVCtKMkY2TS9EblZsVmZJPQ==/attach/object/ITTIQAYAI4)

重启 Tomcat 访问 [http://127.0.0.1:8080/axis2/，可以看到](http://127.0.0.1:8080/axis2/%EF%BC%8C%E5%8F%AF%E4%BB%A5%E7%9C%8B%E5%88%B0) axis2 的默认页面，axis2 环境搭建成功。

![](https://www.kdocs.cn/api/v3/office/copy/NFpOWmFJWkZvUjZZR3dvalQvbHRqa29NKzdGS3ZDVjYzK2w4UUhBY1NUL0lRK1pLREo5bE1NTDc2Wkx5N3dHNjhObzZFYmNTcTlpVDBwZnBOYk0wVEdIR2hubWlLNWRyY2g5VFltS2xLcHpHYXNkanhRbFdnS3JhMmo0RFZhNzV0aDFYZXd6T1VLaGFtVEx5UTBubDVpZURNbW1idG9EQ2ordlVjbWhJMEhtc0hJZ0NCcGNocmJSeFBiY2lzdHFQVSt2dHgxNERkZ3p0aER0eUFWck0yVXE1SGZ0NWNkam5SR0dYdVBJbmp1ZGMyMjlLUnZPN2p5Sm1Xb1gvVCtKMkY2TS9EblZsVmZJPQ==/attach/object/B3TIQAYADA)

## **3. POJO 类实现**

**👋**

在Axis2中不需要进行任何的配置，就可以直接将一个简单的POJO发布成WebService。其中POJO中所有的public方法将被发布成WebService方法。

在 `<span><Tomcat安装目录>\webapps\axis2\WEB-INF\pojo</span>` 目录中，建立 Person.java 文件，实现 Person 类：

```
public class Person {
    String name;
    int age;
    boolean gender;

    public void setName(String name) {
        this.name = name;
    }

    public Person(String name, int age, boolean gender) {
        this.name = name;
        this.age = age;
        this.gender = gender;
    }

    public Person() {
        this.name = "None";
        this.age = 0;
        this.gender = true;
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

    public boolean getGender(boolean gender) {
        return this.gender;
    }

    public String sayHello(){
        return "web services: hello world !" + this.name;
    }
}
```

启动命令行，在终端里边执行

```
javac Persion.java
```

生成Person.class文件

![](https://www.kdocs.cn/api/v3/office/copy/NFpOWmFJWkZvUjZZR3dvalQvbHRqa29NKzdGS3ZDVjYzK2w4UUhBY1NUL0lRK1pLREo5bE1NTDc2Wkx5N3dHNjhObzZFYmNTcTlpVDBwZnBOYk0wVEdIR2hubWlLNWRyY2g5VFltS2xLcHpHYXNkanhRbFdnS3JhMmo0RFZhNzV0aDFYZXd6T1VLaGFtVEx5UTBubDVpZURNbW1idG9EQ2ordlVjbWhJMEhtc0hJZ0NCcGNocmJSeFBiY2lzdHFQVSt2dHgxNERkZ3p0aER0eUFWck0yVXE1SGZ0NWNkam5SR0dYdVBJbmp1ZGMyMjlLUnZPN2p5Sm1Xb1gvVCtKMkY2TS9EblZsVmZJPQ==/attach/object/3BZIWAYAW4)

## **4. 测试服务调用**

编译Persion类后，将Person.class文件放到 `<span><Tomcat安装目录>\webapps\axis2\WEB-INF\pojo</span>`目录中（如果没有pojo目录，则建立该目录）。现在我们已经成功将Persion类发布成了WebService。在浏览器地址栏中输入如下的URL：

[http://localhost:8080/axis2/services/listServices](http://localhost:8080/axis2/services/listServices)

现在成功将 Person 类发布成了 WebService，在浏览器访问 [http://localhost:8080/axis2/services/listServices](http://localhost:8080/axis2/services/listServices)

![](https://www.kdocs.cn/api/v3/office/copy/NFpOWmFJWkZvUjZZR3dvalQvbHRqa29NKzdGS3ZDVjYzK2w4UUhBY1NUL0lRK1pLREo5bE1NTDc2Wkx5N3dHNjhObzZFYmNTcTlpVDBwZnBOYk0wVEdIR2hubWlLNWRyY2g5VFltS2xLcHpHYXNkanhRbFdnS3JhMmo0RFZhNzV0aDFYZXd6T1VLaGFtVEx5UTBubDVpZURNbW1idG9EQ2ordlVjbWhJMEhtc0hJZ0NCcGNocmJSeFBiY2lzdHFQVSt2dHgxNERkZ3p0aER0eUFWck0yVXE1SGZ0NWNkam5SR0dYdVBJbmp1ZGMyMjlLUnZPN2p5Sm1Xb1gvVCtKMkY2TS9EblZsVmZJPQ==/attach/object/MZZYWAYACY)

在浏览器地址栏中输入以下的URL来分别测试sayHello方法：

[http://localhost:8080/axis2/services/Person/sayHello](http://localhost:8080/axis2/services/Person/sayHello)

页面显示如下结果：

![](https://www.kdocs.cn/api/v3/office/copy/NFpOWmFJWkZvUjZZR3dvalQvbHRqa29NKzdGS3ZDVjYzK2w4UUhBY1NUL0lRK1pLREo5bE1NTDc2Wkx5N3dHNjhObzZFYmNTcTlpVDBwZnBOYk0wVEdIR2hubWlLNWRyY2g5VFltS2xLcHpHYXNkanhRbFdnS3JhMmo0RFZhNzV0aDFYZXd6T1VLaGFtVEx5UTBubDVpZURNbW1idG9EQ2ordlVjbWhJMEhtc0hJZ0NCcGNocmJSeFBiY2lzdHFQVSt2dHgxNERkZ3p0aER0eUFWck0yVXE1SGZ0NWNkam5SR0dYdVBJbmp1ZGMyMjlLUnZPN2p5Sm1Xb1gvVCtKMkY2TS9EblZsVmZJPQ==/attach/object/QR2IWAYCIY)

## **5. 使用jar包形式实现**WebService

**📌**

用Axis2实现Web Service，虽然可以将POJO类放在 `<span>axis2\WEB-INF\pojo</span>`目录中直接发布成Web Service， 这样做不需要进行任何配置，但这些POJO类不能在任何包中。这似乎有些不方便.

为此，Axis2也允许将带包的POJO类发布成Web Service。先实现一个POJO类，代码如下：

```
package com.buaa;

import org.apache.axis2.context.MessageContext;
import org.apache.axis2.context.ServiceGroupContext;

public class PersonService {
    String name;
    int age;
    boolean gender;

    public void setName(String name) {
        this.name = name;
        MessageContext mc = MessageContext.getCurrentMessageContext();
        ServiceGroupContext sgc = mc.getServiceGroupContext();
        sgc.setProperty("name", name);
    }

    public PersonService(String name, int age, boolean gender) {
        this.name = name;
        this.age = age;
        this.gender = gender;
    }

    public PersonService() {
        this.name = "wang jinghao";
        this.age = 0;
        this.gender = true;
    }

    public String getName() {
        MessageContext mc = MessageContext.getCurrentMessageContext();
        ServiceGroupContext sgc =  mc.getServiceGroupContext();
        String name = (String) sgc.getProperty("name");
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

    public boolean getGender(boolean gender) {
        return this.gender;
    }

    public String sayHello(){
        //  获得key-value对中的value
        MessageContext mc = MessageContext.getCurrentMessageContext();
        ServiceGroupContext sgc =  mc.getServiceGroupContext();
        String name = (String) sgc.getProperty("name");
        String rs = String.format("web services: hello world ! %s", name);
        return rs;
    }
}
```

要想将PersonService类发布成Web Service，需要一个services.xml文件，在项目下创建services文件夹然后创建 `<strong><span>META-INF-->services.xml</span></strong>`，services.xml这个文件必须要放在META-INF目录中，该文件的内容如下：

```
<?xml version="1.0" encoding="UTF-8"?>

<service name="PersonService" scope="application" targetNamespace="http://ws.apache.org/ax2">
    <description>有状态 Person</description>
    <!-- 服务全类名 -->
    <parameter name="ServiceClass">com.buaa.PersonService</parameter>

    <messageReceivers>
        <!-- 配置消息接收器，Axis2会自动选择 -->
        <messageReceiver mep="http://www.w3.org/ns/wsdl/in-out"
                         class="org.apache.axis2.rpc.receivers.RPCMessageReceiver" />
        <messageReceiver mep="http://www.w3.org/ns/wsdl/in-only"
                         class="org.apache.axis2.rpc.receivers.RPCInOnlyMessageReceiver" />
    </messageReceivers>

</service>
```

　其中 `<span class="color_font"><strong><span><service></span></strong></span>`元素用于发布Web Service，一个 `<span class="color_font"><strong><span><service></span></strong></span>`元素只能发布一个WebService类，name属性表示WebService名，如下面的URL可以获得这个WebService的WSDL内容：

　　[http://localhost:8080/axis2/services/PersonService?wsdl](http://localhost:8080/axis2/services/HelloServiceNew?wsdl)

其中name属性名就是上面URL中”?”和”/“之间的部分。`<span class="color_font"><strong><span><description></span></strong></span>`元素表示当前Web Service的描述，`<span class="color_font"><strong><span><parameter></span></strong></span>`元素用于设置WebService的参数，在这里用于设置WebService对应的类名。在这里最值得注意的是 `<span class="color_font"><strong><span><messageReceivers></span></strong></span>`元素，该元素用于设置处理WebService方法的处理器。

例如，sayHello方法有一个返回值，因此，需要使用可处理输入输出的RPCMessageReceiver类.

使用这种方式发布WebService，必须打包成.aar文件，.aar文件实际上就是改变了扩展名的.jar文件。

现在建立了两个文件：PersonService.java和services.xml。

将PersonService.java编译，生成PersonService.class。services.xml和HelloServiceNew.class文件的位置如下：

![](https://www.kdocs.cn/api/v3/office/copy/NFpOWmFJWkZvUjZZR3dvalQvbHRqa29NKzdGS3ZDVjYzK2w4UUhBY1NUL0lRK1pLREo5bE1NTDc2Wkx5N3dHNjhObzZFYmNTcTlpVDBwZnBOYk0wVEdIR2hubWlLNWRyY2g5VFltS2xLcHpHYXNkanhRbFdnS3JhMmo0RFZhNzV0aDFYZXd6T1VLaGFtVEx5UTBubDVpZURNbW1idG9EQ2ordlVjbWhJMEhtc0hJZ0NCcGNocmJSeFBiY2lzdHFQVSt2dHgxNERkZ3p0aER0eUFWck0yVXE1SGZ0NWNkam5SR0dYdVBJbmp1ZGMyMjlLUnZPN2p5Sm1Xb1gvVCtKMkY2TS9EblZsVmZJPQ==/attach/object/IK7IWAYATE)

在windows控制台中进 `<span class="color_font"><span>src/main/java</span></span>`目录，并输入如下的命令生成.aar文件.

　　`<span class="color_font"><strong><span>jar cvf ws.aar .</span></strong></span>`(`<span class="color_font"><span>注意</span></span>`：最后面是空格+小数点)

**📌**

最后将ws.aar文件复制到 `<strong><span><Tomcat安装目录>\webapps\axis2\WEB-INF\services</span></strong>`目录中，启动Tomcat后，就可以调用这个WebService了。

访问[http://localhost:8080/axis2/services/listServices](http://localhost:8080/axis2/services/listServices)

![](https://www.kdocs.cn/api/v3/office/copy/NFpOWmFJWkZvUjZZR3dvalQvbHRqa29NKzdGS3ZDVjYzK2w4UUhBY1NUL0lRK1pLREo5bE1NTDc2Wkx5N3dHNjhObzZFYmNTcTlpVDBwZnBOYk0wVEdIR2hubWlLNWRyY2g5VFltS2xLcHpHYXNkanhRbFdnS3JhMmo0RFZhNzV0aDFYZXd6T1VLaGFtVEx5UTBubDVpZURNbW1idG9EQ2ordlVjbWhJMEhtc0hJZ0NCcGNocmJSeFBiY2lzdHFQVSt2dHgxNERkZ3p0aER0eUFWck0yVXE1SGZ0NWNkam5SR0dYdVBJbmp1ZGMyMjlLUnZPN2p5Sm1Xb1gvVCtKMkY2TS9EblZsVmZJPQ==/attach/object/W67IWAYAW4)

访问：[http://localhost:8080/axis2/services/PersonService?wsdl](http://localhost:8080/axis2/services/PersonService?wsdl)

如图所示：

![](https://www.kdocs.cn/api/v3/office/copy/NFpOWmFJWkZvUjZZR3dvalQvbHRqa29NKzdGS3ZDVjYzK2w4UUhBY1NUL0lRK1pLREo5bE1NTDc2Wkx5N3dHNjhObzZFYmNTcTlpVDBwZnBOYk0wVEdIR2hubWlLNWRyY2g5VFltS2xLcHpHYXNkanhRbFdnS3JhMmo0RFZhNzV0aDFYZXd6T1VLaGFtVEx5UTBubDVpZURNbW1idG9EQ2ordlVjbWhJMEhtc0hJZ0NCcGNocmJSeFBiY2lzdHFQVSt2dHgxNERkZ3p0aER0eUFWck0yVXE1SGZ0NWNkam5SR0dYdVBJbmp1ZGMyMjlLUnZPN2p5Sm1Xb1gvVCtKMkY2TS9EblZsVmZJPQ==/attach/object/4O7IWAYADA)

首先：

[http://localhost:8080/axis2/services/PersonService/setName=by2221105](http://localhost:8080/axis2/services/PersonService/setName=by2221105)

然后访问：[http://localhost:8080/axis2/services/PersonService/sayHello](http://localhost:8080/axis2/services/Person/sayHello)

结果如图所示，**成功将名称改为了学号**

![](https://www.kdocs.cn/api/v3/office/copy/NFpOWmFJWkZvUjZZR3dvalQvbHRqa29NKzdGS3ZDVjYzK2w4UUhBY1NUL0lRK1pLREo5bE1NTDc2Wkx5N3dHNjhObzZFYmNTcTlpVDBwZnBOYk0wVEdIR2hubWlLNWRyY2g5VFltS2xLcHpHYXNkanhRbFdnS3JhMmo0RFZhNzV0aDFYZXd6T1VLaGFtVEx5UTBubDVpZURNbW1idG9EQ2ordlVjbWhJMEhtc0hJZ0NCcGNocmJSeFBiY2lzdHFQVSt2dHgxNERkZ3p0aER0eUFWck0yVXE1SGZ0NWNkam5SR0dYdVBJbmp1ZGMyMjlLUnZPN2p5Sm1Xb1gvVCtKMkY2TS9EblZsVmZJPQ==/attach/object/BO6YWAYANQ)

代码：[https://github.com/Jinghao-coding/buaa-service-computing/tree/main/homework3](https://github.com/Jinghao-coding/buaa-service-computing/tree/main/homework3)

参考：[https://locqi.github.io/2018/09/25/use-Axis2-build-webService/](https://locqi.github.io/2018/09/25/use-Axis2-build-webService/)
