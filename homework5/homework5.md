**🔔**

**实验内容：**

1. 原子服务开发及部署
   1. 自己选择原子服务及流程的功能，开发 4 个及以上原子服务(RESTful/Web服务可选)。
2. 组合服务开发及运行
   1. 通过 BPEL/BPMN 流程进行上述服务的组合并进行执行。
3. 撰写实验报告

> 实验代码及文档：[实验5-GitHub](https://github.com/Jinghao-coding/buaa-service-computing/tree/main/homework5)

## 系统功能

实现**身份证信息解析**组合服务

1. 该组合服务由四个子服务构成：
   1. 接口权限验证服务；
   2. 合法性检验服务；
   3. 证件签发省份计算服务；
   4. 公民年龄和性别计算服务；
2. 系统首先对用户输入的接口调用秘钥进行验证，判断用户是否有权限使用该组合服务，若有权限，对传入的身份证号码使用身份证合法性检验服务进行校验，判断该身份证号码是否是一个合法的身份证号码，若合法，继续依次调用省份计算、年龄计算、性别计算三个服务。最后将各个服务的返回值进行整合，最终返回所有结果。

## 实验过程

### 配置 Apache ODE

[下载 Apache ODE](https://ode.apache.org/getting-ode.html)， 当前最新的版本是1.3.8

![](https://www.kdocs.cn/api/v3/office/copy/L2J6WDIyVzZHSUtHM1NIYVNlOXlaTXl6QjBjc1d3YlZqdWtVM3g0d1Rxa0RtWVVML2s1NXFTVXVEV21JQUdtVGlYNzNXMFA4TUoycWo2NjdsejlKMFZQV3RxVi9Ob0I1YUU5SGFDUFZWRFl6K0lVQ1dXMXdKQnNQQk9UQ2RFZDNDMkMvQloweWNyMllrODNHalhTMXpRaitWdWNyaE1DQzJtL3JlaENCNVNXcXgzTitTbi81b29tcU5xZmZnVi9HSW9xOTV5M3hsdWRLd3JBZjVJOUZ2QXdDaS94eW92Vk1nbFljS1RWOEZma3BTWXhJdzM3VG5yK3ZRVTFCWE9wQTZDR2ZHZjl5MkUwPQ==/attach/object/373JSAYBQY)

 下载 War 包，并部署在 Tomcat 中。将war包置于 `<span><tomcat安装目录>/webapps</span>`下

访问 [127.0.0.1:8080/ode](http://127.0.0.1:8080/ode) `<span></span>`可以看到 Apache ODE 运行界面

![](https://www.kdocs.cn/api/v3/office/copy/L2J6WDIyVzZHSUtHM1NIYVNlOXlaTXl6QjBjc1d3YlZqdWtVM3g0d1Rxa0RtWVVML2s1NXFTVXVEV21JQUdtVGlYNzNXMFA4TUoycWo2NjdsejlKMFZQV3RxVi9Ob0I1YUU5SGFDUFZWRFl6K0lVQ1dXMXdKQnNQQk9UQ2RFZDNDMkMvQloweWNyMllrODNHalhTMXpRaitWdWNyaE1DQzJtL3JlaENCNVNXcXgzTitTbi81b29tcU5xZmZnVi9HSW9xOTV5M3hsdWRLd3JBZjVJOUZ2QXdDaS94eW92Vk1nbFljS1RWOEZma3BTWXhJdzM3VG5yK3ZRVTFCWE9wQTZDR2ZHZjl5MkUwPQ==/attach/object/K36JSAYAOM)

### 下载eclipse

本次实验采用的版本：[Index of /eclipse/oomph/epp/2018-09/R/ | 清华大学开源软件镜像站 | Tsinghua Open Source Mirror](https://mirrors.tuna.tsinghua.edu.cn/eclipse/oomph/epp/2018-09/R/)

![](https://www.kdocs.cn/api/v3/office/copy/L2J6WDIyVzZHSUtHM1NIYVNlOXlaTXl6QjBjc1d3YlZqdWtVM3g0d1Rxa0RtWVVML2s1NXFTVXVEV21JQUdtVGlYNzNXMFA4TUoycWo2NjdsejlKMFZQV3RxVi9Ob0I1YUU5SGFDUFZWRFl6K0lVQ1dXMXdKQnNQQk9UQ2RFZDNDMkMvQloweWNyMllrODNHalhTMXpRaitWdWNyaE1DQzJtL3JlaENCNVNXcXgzTitTbi81b29tcU5xZmZnVi9HSW9xOTV5M3hsdWRLd3JBZjVJOUZ2QXdDaS94eW92Vk1nbFljS1RWOEZma3BTWXhJdzM3VG5yK3ZRVTFCWE9wQTZDR2ZHZjl5MkUwPQ==/attach/object/Q77JSAYA54)

### 安装 BPEL Designer 插件

Eclipise 进入 install wizard，选择安装 SOA Development 中的所有插件

![](https://www.kdocs.cn/api/v3/office/copy/L2J6WDIyVzZHSUtHM1NIYVNlOXlaTXl6QjBjc1d3YlZqdWtVM3g0d1Rxa0RtWVVML2s1NXFTVXVEV21JQUdtVGlYNzNXMFA4TUoycWo2NjdsejlKMFZQV3RxVi9Ob0I1YUU5SGFDUFZWRFl6K0lVQ1dXMXdKQnNQQk9UQ2RFZDNDMkMvQloweWNyMllrODNHalhTMXpRaitWdWNyaE1DQzJtL3JlaENCNVNXcXgzTitTbi81b29tcU5xZmZnVi9HSW9xOTV5M3hsdWRLd3JBZjVJOUZ2QXdDaS94eW92Vk1nbFljS1RWOEZma3BTWXhJdzM3VG5yK3ZRVTFCWE9wQTZDR2ZHZjl5MkUwPQ==/attach/object/REAZUAYAFU)

### 开发并测试RestFul服务

* 使用eclipse开发，新建Dynamic Web Project

项目名称：`<span>WebService</span>`

项目结构如下：

![](https://www.kdocs.cn/api/v3/office/copy/L2J6WDIyVzZHSUtHM1NIYVNlOXlaTXl6QjBjc1d3YlZqdWtVM3g0d1Rxa0RtWVVML2s1NXFTVXVEV21JQUdtVGlYNzNXMFA4TUoycWo2NjdsejlKMFZQV3RxVi9Ob0I1YUU5SGFDUFZWRFl6K0lVQ1dXMXdKQnNQQk9UQ2RFZDNDMkMvQloweWNyMllrODNHalhTMXpRaitWdWNyaE1DQzJtL3JlaENCNVNXcXgzTitTbi81b29tcU5xZmZnVi9HSW9xOTV5M3hsdWRLd3JBZjVJOUZ2QXdDaS94eW92Vk1nbFljS1RWOEZma3BTWXhJdzM3VG5yK3ZRVTFCWE9wQTZDR2ZHZjl5MkUwPQ==/attach/object/DWSZYAYABQ)

* 下载并导入项目所需要的jar包

由于本项目使用Jersey框架，所以需要下载Jersey所需要的jar包。

Jersey JAX-RS 2.3 RI bundle下载地址：[https://github.com/eclipse-ee4j/jersey/releases/tag/2.37](https://github.com/eclipse-ee4j/jersey/releases/tag/2.37) （点击里面蓝色的Jersey JAX-RS 2.1 RI bundle就可以进行下载）。

![](https://www.kdocs.cn/api/v3/office/copy/L2J6WDIyVzZHSUtHM1NIYVNlOXlaTXl6QjBjc1d3YlZqdWtVM3g0d1Rxa0RtWVVML2s1NXFTVXVEV21JQUdtVGlYNzNXMFA4TUoycWo2NjdsejlKMFZQV3RxVi9Ob0I1YUU5SGFDUFZWRFl6K0lVQ1dXMXdKQnNQQk9UQ2RFZDNDMkMvQloweWNyMllrODNHalhTMXpRaitWdWNyaE1DQzJtL3JlaENCNVNXcXgzTitTbi81b29tcU5xZmZnVi9HSW9xOTV5M3hsdWRLd3JBZjVJOUZ2QXdDaS94eW92Vk1nbFljS1RWOEZma3BTWXhJdzM3VG5yK3ZRVTFCWE9wQTZDR2ZHZjl5MkUwPQ==/attach/object/TCSZYAYAKY)

其中 `<span>api，ext，lib</span>`这三个文件夹中分别包含很多jar包。将这些jar包全部拷贝到工程中 `<span>WebContent/WEB-INF/lib</span>`目录中。

不要直接将文件夹拷入，而是将这三个文件夹中的jar包全部拷入。也就是说，最后 `<span>WebContent/WEB-INF/lib</span>`目录中不是包含“`<span>api, ext,lib</span>`”这三个文件夹，而是直接包含全部的jar包。否则会出错。`<span> WebContent/WEB-INF/lib</span>`中的内容如下：

![](https://www.kdocs.cn/api/v3/office/copy/L2J6WDIyVzZHSUtHM1NIYVNlOXlaTXl6QjBjc1d3YlZqdWtVM3g0d1Rxa0RtWVVML2s1NXFTVXVEV21JQUdtVGlYNzNXMFA4TUoycWo2NjdsejlKMFZQV3RxVi9Ob0I1YUU5SGFDUFZWRFl6K0lVQ1dXMXdKQnNQQk9UQ2RFZDNDMkMvQloweWNyMllrODNHalhTMXpRaitWdWNyaE1DQzJtL3JlaENCNVNXcXgzTitTbi81b29tcU5xZmZnVi9HSW9xOTV5M3hsdWRLd3JBZjVJOUZ2QXdDaS94eW92Vk1nbFljS1RWOEZma3BTWXhJdzM3VG5yK3ZRVTFCWE9wQTZDR2ZHZjl5MkUwPQ==/attach/object/3SSZYAYA6I)

回到Eclipse中，在项目名上右键->Refresh，然后 `<span>再次右键->Build Path->Configure Build Path->Libraries->Add JARs</span>`，将刚才 `<span>WebContent/WEB-INF/lib</span>`中的 `<span>jar</span>`包添加进去：

* 编写Web Service所要提供的功能的代码

在 `<span class="color_font"><span>src</span></span>`中创建包，创建获取年龄的包 `<span class="color_font"><span>age</span></span>`,并在包中创建一个名为“`<span>AgeService</span>`”的类，`<span>AgeService</span>``<span class="color_font"><span>.Java</span></span>`中的代码如下：

```
package age;

import org.jvnet.hk2.annotations.Service;
import cardinfo.IDCardUtils;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.QueryParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

@Service
@Path("/AgeService")
public class AgeService {
    @GET
    @Path("/get")
    @Produces(MediaType.TEXT_PLAIN)
    public int getAge(@QueryParam("card_id")String cardID) {
        System.out.print(cardID); 
        return IDCardUtils.getAgeByIdCard(cardID);
    }
}
```

* 修改web.xml文件，增加jersey信息

修改后的web.xml文件代码如下：

```
<?xml version="1.0" encoding="UTF-8"?>
<web-app xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance" xmlns="http://xmlns.jcp.org/xml/ns/javaee" xsi:schemaLocation="http://xmlns.jcp.org/xml/ns/javaee http://xmlns.jcp.org/xml/ns/javaee/web-app_4_0.xsd" id="WebApp_ID" version="4.0">
  <display-name>WebService</display-name>
  <welcome-file-list>
    <welcome-file>index.html</welcome-file>
    <welcome-file>index.htm</welcome-file>
    <welcome-file>index.jsp</welcome-file>
    <welcome-file>default.html</welcome-file>
    <welcome-file>default.htm</welcome-file>
    <welcome-file>default.jsp</welcome-file>
  </welcome-file-list>
  
  
  <servlet>
      <servlet-name>Jersey RESTful Application</servlet-name>
      <servlet-class>org.glassfish.jersey.servlet.ServletContainer</servlet-class>
         <init-param>
            <param-name>jersey.config.server.provider.packages</param-name>
            <param-value>age</param-value>
         </init-param>
   </servlet>
   <servlet-mapping>
      <servlet-name>Jersey RESTful Application</servlet-name>
      <url-pattern>/rest/*</url-pattern>
   </servlet-mapping>  

</web-app>
```

#### 接口测试

##### 年龄和性别计算服务

**📌**

本次测试采用的身份证号为：`<span>642221202001010092</span>`

在项目名上右键->Run As->Run on Server，启动Tomcat服务器，进行测试，

发送 **年龄计算http请求** ：

```
GET http://localhost:8080/WebService/rest/AgeService/get
    ?card_id=642221202001010092
```

得到如下结果：

![](https://www.kdocs.cn/api/v3/office/copy/L2J6WDIyVzZHSUtHM1NIYVNlOXlaTXl6QjBjc1d3YlZqdWtVM3g0d1Rxa0RtWVVML2s1NXFTVXVEV21JQUdtVGlYNzNXMFA4TUoycWo2NjdsejlKMFZQV3RxVi9Ob0I1YUU5SGFDUFZWRFl6K0lVQ1dXMXdKQnNQQk9UQ2RFZDNDMkMvQloweWNyMllrODNHalhTMXpRaitWdWNyaE1DQzJtL3JlaENCNVNXcXgzTitTbi81b29tcU5xZmZnVi9HSW9xOTV5M3hsdWRLd3JBZjVJOUZ2QXdDaS94eW92Vk1nbFljS1RWOEZma3BTWXhJdzM3VG5yK3ZRVTFCWE9wQTZDR2ZHZjl5MkUwPQ==/attach/object/EWWJYAYARU)

可以看出我们计算出来的结果是age=2

其他的服务均按照以上操作完成。

发送**性别计算http请求**

```
GET http://localhost:8080/WebService/rest/GenderService/get
    ?card_id=642221202001010092
```

结果如下，可以看出此身份证号人为 **男性** ：

![](https://www.kdocs.cn/api/v3/office/copy/L2J6WDIyVzZHSUtHM1NIYVNlOXlaTXl6QjBjc1d3YlZqdWtVM3g0d1Rxa0RtWVVML2s1NXFTVXVEV21JQUdtVGlYNzNXMFA4TUoycWo2NjdsejlKMFZQV3RxVi9Ob0I1YUU5SGFDUFZWRFl6K0lVQ1dXMXdKQnNQQk9UQ2RFZDNDMkMvQloweWNyMllrODNHalhTMXpRaitWdWNyaE1DQzJtL3JlaENCNVNXcXgzTitTbi81b29tcU5xZmZnVi9HSW9xOTV5M3hsdWRLd3JBZjVJOUZ2QXdDaS94eW92Vk1nbFljS1RWOEZma3BTWXhJdzM3VG5yK3ZRVTFCWE9wQTZDR2ZHZjl5MkUwPQ==/attach/object/EDIZYAYCBQ)

##### 身份证签发省份计算服务：

Service 地址：[http://localhost:8080/WebService/rest/ProvinceService/get](http://localhost:8080/WebService/rest/ProvinceService/get)

该服务包含一个 `getProvince` 接口，接受字符串类型的身份证号码输入，返回字符串，表示该身份证的省份。

发送省份计算请求：

```
GET http://localhost:8080/WebService/rest/ProvinceService/get
    ?card_id=642221202001010092
```

结果如下：该同学来自宁夏回族自治区

![](https://www.kdocs.cn/api/v3/office/copy/L2J6WDIyVzZHSUtHM1NIYVNlOXlaTXl6QjBjc1d3YlZqdWtVM3g0d1Rxa0RtWVVML2s1NXFTVXVEV21JQUdtVGlYNzNXMFA4TUoycWo2NjdsejlKMFZQV3RxVi9Ob0I1YUU5SGFDUFZWRFl6K0lVQ1dXMXdKQnNQQk9UQ2RFZDNDMkMvQloweWNyMllrODNHalhTMXpRaitWdWNyaE1DQzJtL3JlaENCNVNXcXgzTitTbi81b29tcU5xZmZnVi9HSW9xOTV5M3hsdWRLd3JBZjVJOUZ2QXdDaS94eW92Vk1nbFljS1RWOEZma3BTWXhJdzM3VG5yK3ZRVTFCWE9wQTZDR2ZHZjl5MkUwPQ==/attach/object/TPKZYAYBXQ)

##### 身份证合法性检验服务：

接口地址：[http://localhost:8080/WebService/rest/IsLegalService/get](http://localhost:8080/WebService/rest/IsLegalService/get)

该服务包含一个 isLegal 接口，传入字符串类型的身份证号码，返回 int 类型，若返回值为 1，代表该身份证号码合法，否则表示身份证号码非法

发送**合法性检验请求：**

```
GET http://localhost:8080/WebService/rest/IsLegalService/get
    ?card_id=642221202001010092
```

结果如下：可以知道该身份证号**非法——因为该身份证号是假的**

![](https://www.kdocs.cn/api/v3/office/copy/L2J6WDIyVzZHSUtHM1NIYVNlOXlaTXl6QjBjc1d3YlZqdWtVM3g0d1Rxa0RtWVVML2s1NXFTVXVEV21JQUdtVGlYNzNXMFA4TUoycWo2NjdsejlKMFZQV3RxVi9Ob0I1YUU5SGFDUFZWRFl6K0lVQ1dXMXdKQnNQQk9UQ2RFZDNDMkMvQloweWNyMllrODNHalhTMXpRaitWdWNyaE1DQzJtL3JlaENCNVNXcXgzTitTbi81b29tcU5xZmZnVi9HSW9xOTV5M3hsdWRLd3JBZjVJOUZ2QXdDaS94eW92Vk1nbFljS1RWOEZma3BTWXhJdzM3VG5yK3ZRVTFCWE9wQTZDR2ZHZjl5MkUwPQ==/attach/object/TDLZYAYASU)

验证本人身份证号：由于信息敏感，不做列出，结果如下，可以看出身份证号为正确的

![](https://www.kdocs.cn/api/v3/office/copy/L2J6WDIyVzZHSUtHM1NIYVNlOXlaTXl6QjBjc1d3YlZqdWtVM3g0d1Rxa0RtWVVML2s1NXFTVXVEV21JQUdtVGlYNzNXMFA4TUoycWo2NjdsejlKMFZQV3RxVi9Ob0I1YUU5SGFDUFZWRFl6K0lVQ1dXMXdKQnNQQk9UQ2RFZDNDMkMvQloweWNyMllrODNHalhTMXpRaitWdWNyaE1DQzJtL3JlaENCNVNXcXgzTitTbi81b29tcU5xZmZnVi9HSW9xOTV5M3hsdWRLd3JBZjVJOUZ2QXdDaS94eW92Vk1nbFljS1RWOEZma3BTWXhJdzM3VG5yK3ZRVTFCWE9wQTZDR2ZHZjl5MkUwPQ==/attach/object/APMJYAYAXA)

##### 接口权限验证服务：

接口地址：[http://localhost:8080/WebService/rest/PayService/pay](http://localhost:8080/WebService/rest/PayService/pay)

该服务包含一个 pay 接口，传入接口调用秘钥，验证对服务的调用权限，执行计费。返回一个 double 类型的账户余额数据，若返回值小于0，代表账户已欠费，无法完成服务调用请求

发送省份计算请求：

```
POST http://localhost:8080/WebService/rest/PayService/pay
    ?key=by2221105
```

结果如下，可以看出是有权限的，这里为了方便认为密钥是：**by2221105**

![](https://www.kdocs.cn/api/v3/office/copy/L2J6WDIyVzZHSUtHM1NIYVNlOXlaTXl6QjBjc1d3YlZqdWtVM3g0d1Rxa0RtWVVML2s1NXFTVXVEV21JQUdtVGlYNzNXMFA4TUoycWo2NjdsejlKMFZQV3RxVi9Ob0I1YUU5SGFDUFZWRFl6K0lVQ1dXMXdKQnNQQk9UQ2RFZDNDMkMvQloweWNyMllrODNHalhTMXpRaitWdWNyaE1DQzJtL3JlaENCNVNXcXgzTitTbi81b29tcU5xZmZnVi9HSW9xOTV5M3hsdWRLd3JBZjVJOUZ2QXdDaS94eW92Vk1nbFljS1RWOEZma3BTWXhJdzM3VG5yK3ZRVTFCWE9wQTZDR2ZHZjl5MkUwPQ==/attach/object/ADLZYAYA74)

### 创建组合服务工程：

创建 ode Server，

![](https://www.kdocs.cn/api/v3/office/copy/L2J6WDIyVzZHSUtHM1NIYVNlOXlaTXl6QjBjc1d3YlZqdWtVM3g0d1Rxa0RtWVVML2s1NXFTVXVEV21JQUdtVGlYNzNXMFA4TUoycWo2NjdsejlKMFZQV3RxVi9Ob0I1YUU5SGFDUFZWRFl6K0lVQ1dXMXdKQnNQQk9UQ2RFZDNDMkMvQloweWNyMllrODNHalhTMXpRaitWdWNyaE1DQzJtL3JlaENCNVNXcXgzTitTbi81b29tcU5xZmZnVi9HSW9xOTV5M3hsdWRLd3JBZjVJOUZ2QXdDaS94eW92Vk1nbFljS1RWOEZma3BTWXhJdzM3VG5yK3ZRVTFCWE9wQTZDR2ZHZjl5MkUwPQ==/attach/object/DXPJYAYACY)

创建bpel工程

![](https://www.kdocs.cn/api/v3/office/copy/L2J6WDIyVzZHSUtHM1NIYVNlOXlaTXl6QjBjc1d3YlZqdWtVM3g0d1Rxa0RtWVVML2s1NXFTVXVEV21JQUdtVGlYNzNXMFA4TUoycWo2NjdsejlKMFZQV3RxVi9Ob0I1YUU5SGFDUFZWRFl6K0lVQ1dXMXdKQnNQQk9UQ2RFZDNDMkMvQloweWNyMllrODNHalhTMXpRaitWdWNyaE1DQzJtL3JlaENCNVNXcXgzTitTbi81b29tcU5xZmZnVi9HSW9xOTV5M3hsdWRLd3JBZjVJOUZ2QXdDaS94eW92Vk1nbFljS1RWOEZma3BTWXhJdzM3VG5yK3ZRVTFCWE9wQTZDR2ZHZjl5MkUwPQ==/attach/object/P7PJYAYBCY)

![](https://www.kdocs.cn/api/v3/office/copy/L2J6WDIyVzZHSUtHM1NIYVNlOXlaTXl6QjBjc1d3YlZqdWtVM3g0d1Rxa0RtWVVML2s1NXFTVXVEV21JQUdtVGlYNzNXMFA4TUoycWo2NjdsejlKMFZQV3RxVi9Ob0I1YUU5SGFDUFZWRFl6K0lVQ1dXMXdKQnNQQk9UQ2RFZDNDMkMvQloweWNyMllrODNHalhTMXpRaitWdWNyaE1DQzJtL3JlaENCNVNXcXgzTitTbi81b29tcU5xZmZnVi9HSW9xOTV5M3hsdWRLd3JBZjVJOUZ2QXdDaS94eW92Vk1nbFljS1RWOEZma3BTWXhJdzM3VG5yK3ZRVTFCWE9wQTZDR2ZHZjl5MkUwPQ==/attach/object/PLPZYAYC74)

![](https://www.kdocs.cn/api/v3/office/copy/L2J6WDIyVzZHSUtHM1NIYVNlOXlaTXl6QjBjc1d3YlZqdWtVM3g0d1Rxa0RtWVVML2s1NXFTVXVEV21JQUdtVGlYNzNXMFA4TUoycWo2NjdsejlKMFZQV3RxVi9Ob0I1YUU5SGFDUFZWRFl6K0lVQ1dXMXdKQnNQQk9UQ2RFZDNDMkMvQloweWNyMllrODNHalhTMXpRaitWdWNyaE1DQzJtL3JlaENCNVNXcXgzTitTbi81b29tcU5xZmZnVi9HSW9xOTV5M3hsdWRLd3JBZjVJOUZ2QXdDaS94eW92Vk1nbFljS1RWOEZma3BTWXhJdzM3VG5yK3ZRVTFCWE9wQTZDR2ZHZjl5MkUwPQ==/attach/object/DLTZYAYA4Y)

 前往 ode 面板，可以看到编写的组合服务已经发布成功：

![](https://www.kdocs.cn/api/v3/office/copy/L2J6WDIyVzZHSUtHM1NIYVNlOXlaTXl6QjBjc1d3YlZqdWtVM3g0d1Rxa0RtWVVML2s1NXFTVXVEV21JQUdtVGlYNzNXMFA4TUoycWo2NjdsejlKMFZQV3RxVi9Ob0I1YUU5SGFDUFZWRFl6K0lVQ1dXMXdKQnNQQk9UQ2RFZDNDMkMvQloweWNyMllrODNHalhTMXpRaitWdWNyaE1DQzJtL3JlaENCNVNXcXgzTitTbi81b29tcU5xZmZnVi9HSW9xOTV5M3hsdWRLd3JBZjVJOUZ2QXdDaS94eW92Vk1nbFljS1RWOEZma3BTWXhJdzM3VG5yK3ZRVTFCWE9wQTZDR2ZHZjl5MkUwPQ==/attach/object/APWJYAYAQI)

进行调用，组合服务运行正常：

![](https://www.kdocs.cn/api/v3/office/copy/L2J6WDIyVzZHSUtHM1NIYVNlOXlaTXl6QjBjc1d3YlZqdWtVM3g0d1Rxa0RtWVVML2s1NXFTVXVEV21JQUdtVGlYNzNXMFA4TUoycWo2NjdsejlKMFZQV3RxVi9Ob0I1YUU5SGFDUFZWRFl6K0lVQ1dXMXdKQnNQQk9UQ2RFZDNDMkMvQloweWNyMllrODNHalhTMXpRaitWdWNyaE1DQzJtL3JlaENCNVNXcXgzTitTbi81b29tcU5xZmZnVi9HSW9xOTV5M3hsdWRLd3JBZjVJOUZ2QXdDaS94eW92Vk1nbFljS1RWOEZma3BTWXhJdzM3VG5yK3ZRVTFCWE9wQTZDR2ZHZjl5MkUwPQ==/attach/object/DLWJYAYAK4)
