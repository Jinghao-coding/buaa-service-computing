# 实验4：服务QoS预测

**🔔**

**实验内容：**

1. 服务QoS预测算法代码理解
   1. 参考WS-DREAM文档（[https://github.com/icsme2020/WS-DREAM](https://github.com/icsme2020/WS-DREAM)），查看/home/WS-DREAM目录下至少5种服务QoS预测算法代码，分析其原理。
2. 服务QoS预测算法执行
   1. 执行至少5种服务QoS预测算法并分析结果。
3. 撰写实验报告

> 实验代码及文档：[实验4-github](https://github.com/Jinghao-coding/buaa-service-computing/tree/main/homework4)

## 程序运行环境搭建

WS-DREAM 项目依赖的运行环境为：

* Python 2.7 ([[https://www.python.org](https://www.python.org)]([https://www.python.org/))](https://www.python.org/)))
* Cython 0.20.1 ([[http://cython.org](http://cython.org)]([http://cython.org/))](http://cython.org/)))
* numpy 1.8.1 ([[http://www.scipy.org](http://www.scipy.org)]([http://www.scipy.org/))](http://www.scipy.org/)))
* scipy 0.13.3 ([[http://www.scipy.org](http://www.scipy.org)]([http://www.scipy.org/))](http://www.scipy.org/)))
* AMF ([https://github.com/wsdream/AMF)](https://github.com/wsdream/AMF))
* PPCF ([https://github.com/wsdream/PPCF)](https://github.com/wsdream/PPCF))

1. 该项目使用 Python 2.7 版本，为了避免操作系统现在的 Python 版本对其干扰，使用 Anaconda 创建 python 版本为 2.7 的虚拟环境：

![](https://www.kdocs.cn/api/v3/office/copy/K2FTQ0dKSGxiRURzTW91a3pZOVdzL3hVOXNqUEgybUVOeVhOa0UrNmF6elRZdFpvU09tT2lBSnZQalNIb3BtdmZnb2crSThBT2NZTEZlaWJHOFNESzJQQ0Q3bHB0WWNuQk8zZzlyNzROSEFyQmE2ZTVwYXBkWWtuVk9kVFMxcVl5QmwyeFB3TXZ6K0tTMHZnVnBVeDE0VXdpeWNVZmZJMDFLczBVWkRJSjMrM3BGSStUOTN1cHhCQlpnRlR5ZEZNSDMwYkZTVWgxZEVqN09PZys2aUd1RTZoQ0VZYW9FWmQrZXNaYTdWUzBPTFh2V0ZMQUtzdU5pMjRxc1d1eWkxaWNaUUlMTDBSQWZVPQ==/attach/object/SJ5Y6AYACY)

1. 环境创建好后，先安装Python的基础依赖

该项目依赖的 Python 包写在项目根目录下的 `<span>requirements.txt</span>`文件中，

使用命令 `<span> pip install -r requirements.txt</span>`完成安装

分别在 `<span>src/</span>`目录下

![](https://www.kdocs.cn/api/v3/office/copy/K2FTQ0dKSGxiRURzTW91a3pZOVdzL3hVOXNqUEgybUVOeVhOa0UrNmF6elRZdFpvU09tT2lBSnZQalNIb3BtdmZnb2crSThBT2NZTEZlaWJHOFNESzJQQ0Q3bHB0WWNuQk8zZzlyNzROSEFyQmE2ZTVwYXBkWWtuVk9kVFMxcVl5QmwyeFB3TXZ6K0tTMHZnVnBVeDE0VXdpeWNVZmZJMDFLczBVWkRJSjMrM3BGSStUOTN1cHhCQlpnRlR5ZEZNSDMwYkZTVWgxZEVqN09PZys2aUd1RTZoQ0VZYW9FWmQrZXNaYTdWUzBPTFh2V0ZMQUtzdU5pMjRxc1d1eWkxaWNaUUlMTDBSQWZVPQ==/attach/object/JVNJEAYAZM)

的两个依赖分别构建。

1. 安装 package:

 使用命令 `<span>python setup.py install --user</span>` 编译成功

![](https://www.kdocs.cn/api/v3/office/copy/K2FTQ0dKSGxiRURzTW91a3pZOVdzL3hVOXNqUEgybUVOeVhOa0UrNmF6elRZdFpvU09tT2lBSnZQalNIb3BtdmZnb2crSThBT2NZTEZlaWJHOFNESzJQQ0Q3bHB0WWNuQk8zZzlyNzROSEFyQmE2ZTVwYXBkWWtuVk9kVFMxcVl5QmwyeFB3TXZ6K0tTMHZnVnBVeDE0VXdpeWNVZmZJMDFLczBVWkRJSjMrM3BGSStUOTN1cHhCQlpnRlR5ZEZNSDMwYkZTVWgxZEVqN09PZys2aUd1RTZoQ0VZYW9FWmQrZXNaYTdWUzBPTFh2V0ZMQUtzdU5pMjRxc1d1eWkxaWNaUUlMTDBSQWZVPQ==/attach/object/CZNJEAYAC4)

至此，运行环境搭建完成

## QoS 测试

在此运行5种服务QoS预测算法代码

### NTF 方法(非负矩阵分解)：

**💡**

**Non-negative Matrix Factorization**

[算法原文](https://dl.acm.org/doi/10.1145/2566486.2568001)：[Temporal QoS-Aware Web Service Recommendation via
Non-negative Tensor Factorization](https://dl.acm.org/doi/pdf/10.1145/2566486.2568001)

NTF方法为Model-based 方法

**运行结果**

![](https://www.kdocs.cn/api/v3/office/copy/K2FTQ0dKSGxiRURzTW91a3pZOVdzL3hVOXNqUEgybUVOeVhOa0UrNmF6elRZdFpvU09tT2lBSnZQalNIb3BtdmZnb2crSThBT2NZTEZlaWJHOFNESzJQQ0Q3bHB0WWNuQk8zZzlyNzROSEFyQmE2ZTVwYXBkWWtuVk9kVFMxcVl5QmwyeFB3TXZ6K0tTMHZnVnBVeDE0VXdpeWNVZmZJMDFLczBVWkRJSjMrM3BGSStUOTN1cHhCQlpnRlR5ZEZNSDMwYkZTVWgxZEVqN09PZys2aUd1RTZoQ0VZYW9FWmQrZXNaYTdWUzBPTFh2V0ZMQUtzdU5pMjRxc1d1eWkxaWNaUUlMTDBSQWZVPQ==/attach/object/BOXJIAYBEU)

![](https://www.kdocs.cn/api/v3/office/copy/K2FTQ0dKSGxiRURzTW91a3pZOVdzL3hVOXNqUEgybUVOeVhOa0UrNmF6elRZdFpvU09tT2lBSnZQalNIb3BtdmZnb2crSThBT2NZTEZlaWJHOFNESzJQQ0Q3bHB0WWNuQk8zZzlyNzROSEFyQmE2ZTVwYXBkWWtuVk9kVFMxcVl5QmwyeFB3TXZ6K0tTMHZnVnBVeDE0VXdpeWNVZmZJMDFLczBVWkRJSjMrM3BGSStUOTN1cHhCQlpnRlR5ZEZNSDMwYkZTVWgxZEVqN09PZys2aUd1RTZoQ0VZYW9FWmQrZXNaYTdWUzBPTFh2V0ZMQUtzdU5pMjRxc1d1eWkxaWNaUUlMTDBSQWZVPQ==/attach/object/2SYJIAYADM)

**方法原理**

该文章将两个二维用户服务矩阵扩展为一个以三维张量表示的更复杂的用户服务时间三元关系，并提出了一种基于矩阵分解的广义张量因子分解（TF）。通过考虑不同时间QoS值的差异，使用 用户-服务-时间 关系替换用户-服务矩阵，来进行 QoS 预测。在真实世界中，QoS 值永远为非负数，因此，文章提出了一种非负张量因子分解（NTF）方法，以在考虑服务调用时间的情况下预测Web服务QoS值，并提出了一种基于时间QoS的Web服务推荐框架。

通过考虑第三种动态上下文信息，提出了一种时间 QoS 感知 Web 服务推荐框架，以预测在各种时间上下文下丢失的 QoS 值。此外，我们将这个问题形式化为广义张量分解模型，并提出了一种能够处理用户服务时间模型的三元关系的非负张量分解（NTF）算法。基于我们在 Planet-Lab 上收集的真实 Web 服务 QoS 数据集进行了大量实验，该数据集由 32 个时间段内 5,817 个 Web 服务上的 343 个用户的服务调用响应时间和吞吐量值组成。

下图展示了如何将二维的矩阵，增加时间维度，构建 用户-服务-时间 矩阵：

![](https://www.kdocs.cn/api/v3/office/copy/K2FTQ0dKSGxiRURzTW91a3pZOVdzL3hVOXNqUEgybUVOeVhOa0UrNmF6elRZdFpvU09tT2lBSnZQalNIb3BtdmZnb2crSThBT2NZTEZlaWJHOFNESzJQQ0Q3bHB0WWNuQk8zZzlyNzROSEFyQmE2ZTVwYXBkWWtuVk9kVFMxcVl5QmwyeFB3TXZ6K0tTMHZnVnBVeDE0VXdpeWNVZmZJMDFLczBVWkRJSjMrM3BGSStUOTN1cHhCQlpnRlR5ZEZNSDMwYkZTVWgxZEVqN09PZys2aUd1RTZoQ0VZYW9FWmQrZXNaYTdWUzBPTFh2V0ZMQUtzdU5pMjRxc1d1eWkxaWNaUUlMTDBSQWZVPQ==/attach/object/A6YJIAYAN4)

该三维矩阵的构造方法如下：

![](https://www.kdocs.cn/api/v3/office/copy/K2FTQ0dKSGxiRURzTW91a3pZOVdzL3hVOXNqUEgybUVOeVhOa0UrNmF6elRZdFpvU09tT2lBSnZQalNIb3BtdmZnb2crSThBT2NZTEZlaWJHOFNESzJQQ0Q3bHB0WWNuQk8zZzlyNzROSEFyQmE2ZTVwYXBkWWtuVk9kVFMxcVl5QmwyeFB3TXZ6K0tTMHZnVnBVeDE0VXdpeWNVZmZJMDFLczBVWkRJSjMrM3BGSStUOTN1cHhCQlpnRlR5ZEZNSDMwYkZTVWgxZEVqN09PZys2aUd1RTZoQ0VZYW9FWmQrZXNaYTdWUzBPTFh2V0ZMQUtzdU5pMjRxc1d1eWkxaWNaUUlMTDBSQWZVPQ==/attach/object/FWYJIAYA7E)

NTF 方法流程如下：

![](https://www.kdocs.cn/api/v3/office/copy/K2FTQ0dKSGxiRURzTW91a3pZOVdzL3hVOXNqUEgybUVOeVhOa0UrNmF6elRZdFpvU09tT2lBSnZQalNIb3BtdmZnb2crSThBT2NZTEZlaWJHOFNESzJQQ0Q3bHB0WWNuQk8zZzlyNzROSEFyQmE2ZTVwYXBkWWtuVk9kVFMxcVl5QmwyeFB3TXZ6K0tTMHZnVnBVeDE0VXdpeWNVZmZJMDFLczBVWkRJSjMrM3BGSStUOTN1cHhCQlpnRlR5ZEZNSDMwYkZTVWgxZEVqN09PZys2aUd1RTZoQ0VZYW9FWmQrZXNaYTdWUzBPTFh2V0ZMQUtzdU5pMjRxc1d1eWkxaWNaUUlMTDBSQWZVPQ==/attach/object/I2YJIAYASY)

其中：

![](https://www.kdocs.cn/api/v3/office/copy/K2FTQ0dKSGxiRURzTW91a3pZOVdzL3hVOXNqUEgybUVOeVhOa0UrNmF6elRZdFpvU09tT2lBSnZQalNIb3BtdmZnb2crSThBT2NZTEZlaWJHOFNESzJQQ0Q3bHB0WWNuQk8zZzlyNzROSEFyQmE2ZTVwYXBkWWtuVk9kVFMxcVl5QmwyeFB3TXZ6K0tTMHZnVnBVeDE0VXdpeWNVZmZJMDFLczBVWkRJSjMrM3BGSStUOTN1cHhCQlpnRlR5ZEZNSDMwYkZTVWgxZEVqN09PZys2aUd1RTZoQ0VZYW9FWmQrZXNaYTdWUzBPTFh2V0ZMQUtzdU5pMjRxc1d1eWkxaWNaUUlMTDBSQWZVPQ==/attach/object/PWYJIAYAP4)

![](https://www.kdocs.cn/api/v3/office/copy/K2FTQ0dKSGxiRURzTW91a3pZOVdzL3hVOXNqUEgybUVOeVhOa0UrNmF6elRZdFpvU09tT2lBSnZQalNIb3BtdmZnb2crSThBT2NZTEZlaWJHOFNESzJQQ0Q3bHB0WWNuQk8zZzlyNzROSEFyQmE2ZTVwYXBkWWtuVk9kVFMxcVl5QmwyeFB3TXZ6K0tTMHZnVnBVeDE0VXdpeWNVZmZJMDFLczBVWkRJSjMrM3BGSStUOTN1cHhCQlpnRlR5ZEZNSDMwYkZTVWgxZEVqN09PZys2aUd1RTZoQ0VZYW9FWmQrZXNaYTdWUzBPTFh2V0ZMQUtzdU5pMjRxc1d1eWkxaWNaUUlMTDBSQWZVPQ==/attach/object/R2YJIAYBWI)

### PMF 方法（概率矩阵分解）：

**👋**

PMF 为 Model-based 方法。

文章：[Personalized Reliability Prediction of Web Services](https://dl.acm.org/doi/pdf/10.1145/2430545.2430548)

该方法会根据可用的Web服务故障数据建立一个因子模型，并使用该因子模型进行进一步的可靠性预测。

**运行结果：**

![](https://www.kdocs.cn/api/v3/office/copy/K2FTQ0dKSGxiRURzTW91a3pZOVdzL3hVOXNqUEgybUVOeVhOa0UrNmF6elRZdFpvU09tT2lBSnZQalNIb3BtdmZnb2crSThBT2NZTEZlaWJHOFNESzJQQ0Q3bHB0WWNuQk8zZzlyNzROSEFyQmE2ZTVwYXBkWWtuVk9kVFMxcVl5QmwyeFB3TXZ6K0tTMHZnVnBVeDE0VXdpeWNVZmZJMDFLczBVWkRJSjMrM3BGSStUOTN1cHhCQlpnRlR5ZEZNSDMwYkZTVWgxZEVqN09PZys2aUd1RTZoQ0VZYW9FWmQrZXNaYTdWUzBPTFh2V0ZMQUtzdU5pMjRxc1d1eWkxaWNaUUlMTDBSQWZVPQ==/attach/object/P5SZEAYAME)

**方法原理**

1. 对 user-item 矩阵 P ，进行分解，分解为两个矩阵 W 和 H

![]()

W 中的每一行是用户的特定系数，H 中的每一列是 Web 服务的因子向量

例如对于矩阵 P：

![](https://www.kdocs.cn/api/v3/office/copy/K2FTQ0dKSGxiRURzTW91a3pZOVdzL3hVOXNqUEgybUVOeVhOa0UrNmF6elRZdFpvU09tT2lBSnZQalNIb3BtdmZnb2crSThBT2NZTEZlaWJHOFNESzJQQ0Q3bHB0WWNuQk8zZzlyNzROSEFyQmE2ZTVwYXBkWWtuVk9kVFMxcVl5QmwyeFB3TXZ6K0tTMHZnVnBVeDE0VXdpeWNVZmZJMDFLczBVWkRJSjMrM3BGSStUOTN1cHhCQlpnRlR5ZEZNSDMwYkZTVWgxZEVqN09PZys2aUd1RTZoQ0VZYW9FWmQrZXNaYTdWUzBPTFh2V0ZMQUtzdU5pMjRxc1d1eWkxaWNaUUlMTDBSQWZVPQ==/attach/object/GOVJIAYAYM)

 可以将其分解为：

![](https://www.kdocs.cn/api/v3/office/copy/K2FTQ0dKSGxiRURzTW91a3pZOVdzL3hVOXNqUEgybUVOeVhOa0UrNmF6elRZdFpvU09tT2lBSnZQalNIb3BtdmZnb2crSThBT2NZTEZlaWJHOFNESzJQQ0Q3bHB0WWNuQk8zZzlyNzROSEFyQmE2ZTVwYXBkWWtuVk9kVFMxcVl5QmwyeFB3TXZ6K0tTMHZnVnBVeDE0VXdpeWNVZmZJMDFLczBVWkRJSjMrM3BGSStUOTN1cHhCQlpnRlR5ZEZNSDMwYkZTVWgxZEVqN09PZys2aUd1RTZoQ0VZYW9FWmQrZXNaYTdWUzBPTFh2V0ZMQUtzdU5pMjRxc1d1eWkxaWNaUUlMTDBSQWZVPQ==/attach/object/SKVJIAYA6E)

通过矩阵 W 和 H 的乘积，计算 P 中空缺的部分，完成 Qos 预测。

![](https://www.kdocs.cn/api/v3/office/copy/K2FTQ0dKSGxiRURzTW91a3pZOVdzL3hVOXNqUEgybUVOeVhOa0UrNmF6elRZdFpvU09tT2lBSnZQalNIb3BtdmZnb2crSThBT2NZTEZlaWJHOFNESzJQQ0Q3bHB0WWNuQk8zZzlyNzROSEFyQmE2ZTVwYXBkWWtuVk9kVFMxcVl5QmwyeFB3TXZ6K0tTMHZnVnBVeDE0VXdpeWNVZmZJMDFLczBVWkRJSjMrM3BGSStUOTN1cHhCQlpnRlR5ZEZNSDMwYkZTVWgxZEVqN09PZys2aUd1RTZoQ0VZYW9FWmQrZXNaYTdWUzBPTFh2V0ZMQUtzdU5pMjRxc1d1eWkxaWNaUUlMTDBSQWZVPQ==/attach/object/UOVJIAYAJU)

矩阵 W 和 H 通常不唯一，通过使 WH 和 P 之间距离最小化来确定，文章中使用平方误差的方法，计算 P 和 WH 的差异：

![](https://www.kdocs.cn/api/v3/office/copy/K2FTQ0dKSGxiRURzTW91a3pZOVdzL3hVOXNqUEgybUVOeVhOa0UrNmF6elRZdFpvU09tT2lBSnZQalNIb3BtdmZnb2crSThBT2NZTEZlaWJHOFNESzJQQ0Q3bHB0WWNuQk8zZzlyNzROSEFyQmE2ZTVwYXBkWWtuVk9kVFMxcVl5QmwyeFB3TXZ6K0tTMHZnVnBVeDE0VXdpeWNVZmZJMDFLczBVWkRJSjMrM3BGSStUOTN1cHhCQlpnRlR5ZEZNSDMwYkZTVWgxZEVqN09PZys2aUd1RTZoQ0VZYW9FWmQrZXNaYTdWUzBPTFh2V0ZMQUtzdU5pMjRxc1d1eWkxaWNaUUlMTDBSQWZVPQ==/attach/object/ZGVJIAYAOU)

但为了避免过拟合，需要增加惩罚函数，最终可以将优化问题归结为如下目标函数

![](https://www.kdocs.cn/api/v3/office/copy/K2FTQ0dKSGxiRURzTW91a3pZOVdzL3hVOXNqUEgybUVOeVhOa0UrNmF6elRZdFpvU09tT2lBSnZQalNIb3BtdmZnb2crSThBT2NZTEZlaWJHOFNESzJQQ0Q3bHB0WWNuQk8zZzlyNzROSEFyQmE2ZTVwYXBkWWtuVk9kVFMxcVl5QmwyeFB3TXZ6K0tTMHZnVnBVeDE0VXdpeWNVZmZJMDFLczBVWkRJSjMrM3BGSStUOTN1cHhCQlpnRlR5ZEZNSDMwYkZTVWgxZEVqN09PZys2aUd1RTZoQ0VZYW9FWmQrZXNaYTdWUzBPTFh2V0ZMQUtzdU5pMjRxc1d1eWkxaWNaUUlMTDBSQWZVPQ==/attach/object/6GVJIAYCJU)

1. 使用梯度下降的方法，找到目标函数的局部最小值

算法流程为：

![](https://www.kdocs.cn/api/v3/office/copy/K2FTQ0dKSGxiRURzTW91a3pZOVdzL3hVOXNqUEgybUVOeVhOa0UrNmF6elRZdFpvU09tT2lBSnZQalNIb3BtdmZnb2crSThBT2NZTEZlaWJHOFNESzJQQ0Q3bHB0WWNuQk8zZzlyNzROSEFyQmE2ZTVwYXBkWWtuVk9kVFMxcVl5QmwyeFB3TXZ6K0tTMHZnVnBVeDE0VXdpeWNVZmZJMDFLczBVWkRJSjMrM3BGSStUOTN1cHhCQlpnRlR5ZEZNSDMwYkZTVWgxZEVqN09PZys2aUd1RTZoQ0VZYW9FWmQrZXNaYTdWUzBPTFh2V0ZMQUtzdU5pMjRxc1d1eWkxaWNaUUlMTDBSQWZVPQ==/attach/object/EGVZIAYFN4)

 其中，梯度可以通过下式得到：

![](https://www.kdocs.cn/api/v3/office/copy/K2FTQ0dKSGxiRURzTW91a3pZOVdzL3hVOXNqUEgybUVOeVhOa0UrNmF6elRZdFpvU09tT2lBSnZQalNIb3BtdmZnb2crSThBT2NZTEZlaWJHOFNESzJQQ0Q3bHB0WWNuQk8zZzlyNzROSEFyQmE2ZTVwYXBkWWtuVk9kVFMxcVl5QmwyeFB3TXZ6K0tTMHZnVnBVeDE0VXdpeWNVZmZJMDFLczBVWkRJSjMrM3BGSStUOTN1cHhCQlpnRlR5ZEZNSDMwYkZTVWgxZEVqN09PZys2aUd1RTZoQ0VZYW9FWmQrZXNaYTdWUzBPTFh2V0ZMQUtzdU5pMjRxc1d1eWkxaWNaUUlMTDBSQWZVPQ==/attach/object/I2VZIAYBWE)

首先。用小的随机数初始化矩阵 W 和 H；

之后，通过梯度下降的方法，迭代更新矩阵 W 和 H，参数![]()为学习率，控制迭代的速度。

迭代停止时，计算出目标函数取得极小值处的 W 和 H，进行 QoS 预测

### CloudPred 方法(基于邻域的方法)

**🔔**

**CloudPred 方法**

文章链接：[Exploring Latent Features for Memory-Based QoS Prediction in Cloud Computing](https://ieeexplore.ieee.org/document/6076756?arnumber=6076756)

**运行结果**

![](https://www.kdocs.cn/api/v3/office/copy/K2FTQ0dKSGxiRURzTW91a3pZOVdzL3hVOXNqUEgybUVOeVhOa0UrNmF6elRZdFpvU09tT2lBSnZQalNIb3BtdmZnb2crSThBT2NZTEZlaWJHOFNESzJQQ0Q3bHB0WWNuQk8zZzlyNzROSEFyQmE2ZTVwYXBkWWtuVk9kVFMxcVl5QmwyeFB3TXZ6K0tTMHZnVnBVeDE0VXdpeWNVZmZJMDFLczBVWkRJSjMrM3BGSStUOTN1cHhCQlpnRlR5ZEZNSDMwYkZTVWgxZEVqN09PZys2aUd1RTZoQ0VZYW9FWmQrZXNaYTdWUzBPTFh2V0ZMQUtzdU5pMjRxc1d1eWkxaWNaUUlMTDBSQWZVPQ==/attach/object/EOZZIAYAFA)

![](https://www.kdocs.cn/api/v3/office/copy/K2FTQ0dKSGxiRURzTW91a3pZOVdzL3hVOXNqUEgybUVOeVhOa0UrNmF6elRZdFpvU09tT2lBSnZQalNIb3BtdmZnb2crSThBT2NZTEZlaWJHOFNESzJQQ0Q3bHB0WWNuQk8zZzlyNzROSEFyQmE2ZTVwYXBkWWtuVk9kVFMxcVl5QmwyeFB3TXZ6K0tTMHZnVnBVeDE0VXdpeWNVZmZJMDFLczBVWkRJSjMrM3BGSStUOTN1cHhCQlpnRlR5ZEZNSDMwYkZTVWgxZEVqN09PZys2aUd1RTZoQ0VZYW9FWmQrZXNaYTdWUzBPTFh2V0ZMQUtzdU5pMjRxc1d1eWkxaWNaUUlMTDBSQWZVPQ==/attach/object/DO5ZIAYAQI)

**方法原理**

作者提出了一种基于邻域的方法，称为 CloudPred，用于云组件的协作和个性化质量预测。 CloudPred 通过对用户和组件进行特征建模得到增强。

CloudPred 是基于邻域的方法，CloudPred通过非负矩阵分解（NMF）了解用户的特征，并探索类似用户的QoS体验，以实现较高的QoS值预测准确性。

该方法将 QoS 矩阵分解为两个低秩矩阵 V 和 H，V 中的每一列代表用户的 l 维特征向量，H 中的每一列代表组件的特征向量。使用近似矩阵 ![]() 来拟合

矩阵 V 和 H 是未知的，通过矩阵 W 中获得的 QoS 值学习，通过近似矩阵和原矩阵的距离，定义成本函数

![](https://www.kdocs.cn/api/v3/office/copy/K2FTQ0dKSGxiRURzTW91a3pZOVdzL3hVOXNqUEgybUVOeVhOa0UrNmF6elRZdFpvU09tT2lBSnZQalNIb3BtdmZnb2crSThBT2NZTEZlaWJHOFNESzJQQ0Q3bHB0WWNuQk8zZzlyNzROSEFyQmE2ZTVwYXBkWWtuVk9kVFMxcVl5QmwyeFB3TXZ6K0tTMHZnVnBVeDE0VXdpeWNVZmZJMDFLczBVWkRJSjMrM3BGSStUOTN1cHhCQlpnRlR5ZEZNSDMwYkZTVWgxZEVqN09PZys2aUd1RTZoQ0VZYW9FWmQrZXNaYTdWUzBPTFh2V0ZMQUtzdU5pMjRxc1d1eWkxaWNaUUlMTDBSQWZVPQ==/attach/object/ZC2JIAYAJA)

目标函数为：

![](https://www.kdocs.cn/api/v3/office/copy/K2FTQ0dKSGxiRURzTW91a3pZOVdzL3hVOXNqUEgybUVOeVhOa0UrNmF6elRZdFpvU09tT2lBSnZQalNIb3BtdmZnb2crSThBT2NZTEZlaWJHOFNESzJQQ0Q3bHB0WWNuQk8zZzlyNzROSEFyQmE2ZTVwYXBkWWtuVk9kVFMxcVl5QmwyeFB3TXZ6K0tTMHZnVnBVeDE0VXdpeWNVZmZJMDFLczBVWkRJSjMrM3BGSStUOTN1cHhCQlpnRlR5ZEZNSDMwYkZTVWgxZEVqN09PZys2aUd1RTZoQ0VZYW9FWmQrZXNaYTdWUzBPTFh2V0ZMQUtzdU5pMjRxc1d1eWkxaWNaUUlMTDBSQWZVPQ==/attach/object/7C2JIAYAJU)

 为了使目标函数最小化，使用梯度下降的方法，计算局部最小值

![](https://www.kdocs.cn/api/v3/office/copy/K2FTQ0dKSGxiRURzTW91a3pZOVdzL3hVOXNqUEgybUVOeVhOa0UrNmF6elRZdFpvU09tT2lBSnZQalNIb3BtdmZnb2crSThBT2NZTEZlaWJHOFNESzJQQ0Q3bHB0WWNuQk8zZzlyNzROSEFyQmE2ZTVwYXBkWWtuVk9kVFMxcVl5QmwyeFB3TXZ6K0tTMHZnVnBVeDE0VXdpeWNVZmZJMDFLczBVWkRJSjMrM3BGSStUOTN1cHhCQlpnRlR5ZEZNSDMwYkZTVWgxZEVqN09PZys2aUd1RTZoQ0VZYW9FWmQrZXNaYTdWUzBPTFh2V0ZMQUtzdU5pMjRxc1d1eWkxaWNaUUlMTDBSQWZVPQ==/attach/object/CS2ZIAYCEU)

**相似度计算**

 得到用户和组件特征矩阵 V 和 H 后，使用Person 相关系数，计算不同用户和组件的邻域相似度，

![](https://www.kdocs.cn/api/v3/office/copy/K2FTQ0dKSGxiRURzTW91a3pZOVdzL3hVOXNqUEgybUVOeVhOa0UrNmF6elRZdFpvU09tT2lBSnZQalNIb3BtdmZnb2crSThBT2NZTEZlaWJHOFNESzJQQ0Q3bHB0WWNuQk8zZzlyNzROSEFyQmE2ZTVwYXBkWWtuVk9kVFMxcVl5QmwyeFB3TXZ6K0tTMHZnVnBVeDE0VXdpeWNVZmZJMDFLczBVWkRJSjMrM3BGSStUOTN1cHhCQlpnRlR5ZEZNSDMwYkZTVWgxZEVqN09PZys2aUd1RTZoQ0VZYW9FWmQrZXNaYTdWUzBPTFh2V0ZMQUtzdU5pMjRxc1d1eWkxaWNaUUlMTDBSQWZVPQ==/attach/object/IW2ZIAYADM)

![](https://www.kdocs.cn/api/v3/office/copy/K2FTQ0dKSGxiRURzTW91a3pZOVdzL3hVOXNqUEgybUVOeVhOa0UrNmF6elRZdFpvU09tT2lBSnZQalNIb3BtdmZnb2crSThBT2NZTEZlaWJHOFNESzJQQ0Q3bHB0WWNuQk8zZzlyNzROSEFyQmE2ZTVwYXBkWWtuVk9kVFMxcVl5QmwyeFB3TXZ6K0tTMHZnVnBVeDE0VXdpeWNVZmZJMDFLczBVWkRJSjMrM3BGSStUOTN1cHhCQlpnRlR5ZEZNSDMwYkZTVWgxZEVqN09PZys2aUd1RTZoQ0VZYW9FWmQrZXNaYTdWUzBPTFh2V0ZMQUtzdU5pMjRxc1d1eWkxaWNaUUlMTDBSQWZVPQ==/attach/object/KO2ZIAYA5A)

**QoS 预测**

通过对相似度进行排序来识别当前用户的相似邻居。较少相似或不相似用户的QoS可能会大大降低预测准确性。 作者从相似的邻居集中排除PCC值为负的那些用户，而仅使用Top-K最大PCC值的的QoS来预测当前用户的QoS值。

![](https://www.kdocs.cn/api/v3/office/copy/K2FTQ0dKSGxiRURzTW91a3pZOVdzL3hVOXNqUEgybUVOeVhOa0UrNmF6elRZdFpvU09tT2lBSnZQalNIb3BtdmZnb2crSThBT2NZTEZlaWJHOFNESzJQQ0Q3bHB0WWNuQk8zZzlyNzROSEFyQmE2ZTVwYXBkWWtuVk9kVFMxcVl5QmwyeFB3TXZ6K0tTMHZnVnBVeDE0VXdpeWNVZmZJMDFLczBVWkRJSjMrM3BGSStUOTN1cHhCQlpnRlR5ZEZNSDMwYkZTVWgxZEVqN09PZys2aUd1RTZoQ0VZYW9FWmQrZXNaYTdWUzBPTFh2V0ZMQUtzdU5pMjRxc1d1eWkxaWNaUUlMTDBSQWZVPQ==/attach/object/PG2ZIAYAJU)

![](https://www.kdocs.cn/api/v3/office/copy/K2FTQ0dKSGxiRURzTW91a3pZOVdzL3hVOXNqUEgybUVOeVhOa0UrNmF6elRZdFpvU09tT2lBSnZQalNIb3BtdmZnb2crSThBT2NZTEZlaWJHOFNESzJQQ0Q3bHB0WWNuQk8zZzlyNzROSEFyQmE2ZTVwYXBkWWtuVk9kVFMxcVl5QmwyeFB3TXZ6K0tTMHZnVnBVeDE0VXdpeWNVZmZJMDFLczBVWkRJSjMrM3BGSStUOTN1cHhCQlpnRlR5ZEZNSDMwYkZTVWgxZEVqN09PZys2aUd1RTZoQ0VZYW9FWmQrZXNaYTdWUzBPTFh2V0ZMQUtzdU5pMjRxc1d1eWkxaWNaUUlMTDBSQWZVPQ==/attach/object/QW2ZIAYAZI)

![](https://www.kdocs.cn/api/v3/office/copy/K2FTQ0dKSGxiRURzTW91a3pZOVdzL3hVOXNqUEgybUVOeVhOa0UrNmF6elRZdFpvU09tT2lBSnZQalNIb3BtdmZnb2crSThBT2NZTEZlaWJHOFNESzJQQ0Q3bHB0WWNuQk8zZzlyNzROSEFyQmE2ZTVwYXBkWWtuVk9kVFMxcVl5QmwyeFB3TXZ6K0tTMHZnVnBVeDE0VXdpeWNVZmZJMDFLczBVWkRJSjMrM3BGSStUOTN1cHhCQlpnRlR5ZEZNSDMwYkZTVWgxZEVqN09PZys2aUd1RTZoQ0VZYW9FWmQrZXNaYTdWUzBPTFh2V0ZMQUtzdU5pMjRxc1d1eWkxaWNaUUlMTDBSQWZVPQ==/attach/object/R22ZIAYAKM)

CloudPred 预测算法，混合了基于用户的预测方法和基于组件的方法，其算法如下：

![](https://www.kdocs.cn/api/v3/office/copy/K2FTQ0dKSGxiRURzTW91a3pZOVdzL3hVOXNqUEgybUVOeVhOa0UrNmF6elRZdFpvU09tT2lBSnZQalNIb3BtdmZnb2crSThBT2NZTEZlaWJHOFNESzJQQ0Q3bHB0WWNuQk8zZzlyNzROSEFyQmE2ZTVwYXBkWWtuVk9kVFMxcVl5QmwyeFB3TXZ6K0tTMHZnVnBVeDE0VXdpeWNVZmZJMDFLczBVWkRJSjMrM3BGSStUOTN1cHhCQlpnRlR5ZEZNSDMwYkZTVWgxZEVqN09PZys2aUd1RTZoQ0VZYW9FWmQrZXNaYTdWUzBPTFh2V0ZMQUtzdU5pMjRxc1d1eWkxaWNaUUlMTDBSQWZVPQ==/attach/object/2W2ZIAYAWI)

### **EMF (扩展矩阵分解)**

**📌**

**EMF： Extended Matrix Factorization**

文章：[An Extended Matrix Factorization Approach for QoS Prediction in Service Selection](https://ieeexplore.ieee.org/document/6274140?arnumber=6274140&tag=1)

**运行结果**

![](https://www.kdocs.cn/api/v3/office/copy/K2FTQ0dKSGxiRURzTW91a3pZOVdzL3hVOXNqUEgybUVOeVhOa0UrNmF6elRZdFpvU09tT2lBSnZQalNIb3BtdmZnb2crSThBT2NZTEZlaWJHOFNESzJQQ0Q3bHB0WWNuQk8zZzlyNzROSEFyQmE2ZTVwYXBkWWtuVk9kVFMxcVl5QmwyeFB3TXZ6K0tTMHZnVnBVeDE0VXdpeWNVZmZJMDFLczBVWkRJSjMrM3BGSStUOTN1cHhCQlpnRlR5ZEZNSDMwYkZTVWgxZEVqN09PZys2aUd1RTZoQ0VZYW9FWmQrZXNaYTdWUzBPTFh2V0ZMQUtzdU5pMjRxc1d1eWkxaWNaUUlMTDBSQWZVPQ==/attach/object/IK5ZIAYAN4)

![](https://www.kdocs.cn/api/v3/office/copy/K2FTQ0dKSGxiRURzTW91a3pZOVdzL3hVOXNqUEgybUVOeVhOa0UrNmF6elRZdFpvU09tT2lBSnZQalNIb3BtdmZnb2crSThBT2NZTEZlaWJHOFNESzJQQ0Q3bHB0WWNuQk8zZzlyNzROSEFyQmE2ZTVwYXBkWWtuVk9kVFMxcVl5QmwyeFB3TXZ6K0tTMHZnVnBVeDE0VXdpeWNVZmZJMDFLczBVWkRJSjMrM3BGSStUOTN1cHhCQlpnRlR5ZEZNSDMwYkZTVWgxZEVqN09PZys2aUd1RTZoQ0VZYW9FWmQrZXNaYTdWUzBPTFh2V0ZMQUtzdU5pMjRxc1d1eWkxaWNaUUlMTDBSQWZVPQ==/attach/object/6HHJIAYBDM)

**方法原理**

作者提出了一种具有关系正则化的扩展矩阵分解 (EMF) 框架来进行 QoS 值缺失预测。首先从一般角度阐述矩阵分解（MF）模型。为了准确收集人群的智慧，我们在用户端和服务端采用不同的相似性度量来识别邻域。然后作者在邻域内系统地设计了两个新的关系正则化项。最后，作者将这两个术语组合成一个统一的 MF 框架来预测丢失的 QoS 值。在传统的矩阵分解方法的基础上通过增加**相关用户**和**相关服务**这两个正则项来预测缺失的 QoS 值

文章中，定义 user-service 矩阵为 R，目标将矩阵 R 分解为 U 和 S 两个矩阵：

![]()

目标使得估计的矩阵 R 更逼近原始矩阵，归结为最小化问题：

![](https://www.kdocs.cn/api/v3/office/copy/K2FTQ0dKSGxiRURzTW91a3pZOVdzL3hVOXNqUEgybUVOeVhOa0UrNmF6elRZdFpvU09tT2lBSnZQalNIb3BtdmZnb2crSThBT2NZTEZlaWJHOFNESzJQQ0Q3bHB0WWNuQk8zZzlyNzROSEFyQmE2ZTVwYXBkWWtuVk9kVFMxcVl5QmwyeFB3TXZ6K0tTMHZnVnBVeDE0VXdpeWNVZmZJMDFLczBVWkRJSjMrM3BGSStUOTN1cHhCQlpnRlR5ZEZNSDMwYkZTVWgxZEVqN09PZys2aUd1RTZoQ0VZYW9FWmQrZXNaYTdWUzBPTFh2V0ZMQUtzdU5pMjRxc1d1eWkxaWNaUUlMTDBSQWZVPQ==/attach/object/TC3ZIAYBEU)

现实情况下，原始矩阵 R 是一个稀疏矩阵，使用 $I_{ij}$ 标记用户 $u_i$，与服务$s_j$ 是否进行了交互，原始问题修改为以下问题：

![](https://www.kdocs.cn/api/v3/office/copy/K2FTQ0dKSGxiRURzTW91a3pZOVdzL3hVOXNqUEgybUVOeVhOa0UrNmF6elRZdFpvU09tT2lBSnZQalNIb3BtdmZnb2crSThBT2NZTEZlaWJHOFNESzJQQ0Q3bHB0WWNuQk8zZzlyNzROSEFyQmE2ZTVwYXBkWWtuVk9kVFMxcVl5QmwyeFB3TXZ6K0tTMHZnVnBVeDE0VXdpeWNVZmZJMDFLczBVWkRJSjMrM3BGSStUOTN1cHhCQlpnRlR5ZEZNSDMwYkZTVWgxZEVqN09PZys2aUd1RTZoQ0VZYW9FWmQrZXNaYTdWUzBPTFh2V0ZMQUtzdU5pMjRxc1d1eWkxaWNaUUlMTDBSQWZVPQ==/attach/object/VW3ZIAYAXM)

为了避免过拟合，进行正则化：

![](https://www.kdocs.cn/api/v3/office/copy/K2FTQ0dKSGxiRURzTW91a3pZOVdzL3hVOXNqUEgybUVOeVhOa0UrNmF6elRZdFpvU09tT2lBSnZQalNIb3BtdmZnb2crSThBT2NZTEZlaWJHOFNESzJQQ0Q3bHB0WWNuQk8zZzlyNzROSEFyQmE2ZTVwYXBkWWtuVk9kVFMxcVl5QmwyeFB3TXZ6K0tTMHZnVnBVeDE0VXdpeWNVZmZJMDFLczBVWkRJSjMrM3BGSStUOTN1cHhCQlpnRlR5ZEZNSDMwYkZTVWgxZEVqN09PZys2aUd1RTZoQ0VZYW9FWmQrZXNaYTdWUzBPTFh2V0ZMQUtzdU5pMjRxc1d1eWkxaWNaUUlMTDBSQWZVPQ==/attach/object/Y23ZIAYAME)

EMF 融合了 user 和 service 进行 QoS 预测，

![](https://www.kdocs.cn/api/v3/office/copy/K2FTQ0dKSGxiRURzTW91a3pZOVdzL3hVOXNqUEgybUVOeVhOa0UrNmF6elRZdFpvU09tT2lBSnZQalNIb3BtdmZnb2crSThBT2NZTEZlaWJHOFNESzJQQ0Q3bHB0WWNuQk8zZzlyNzROSEFyQmE2ZTVwYXBkWWtuVk9kVFMxcVl5QmwyeFB3TXZ6K0tTMHZnVnBVeDE0VXdpeWNVZmZJMDFLczBVWkRJSjMrM3BGSStUOTN1cHhCQlpnRlR5ZEZNSDMwYkZTVWgxZEVqN09PZys2aUd1RTZoQ0VZYW9FWmQrZXNaYTdWUzBPTFh2V0ZMQUtzdU5pMjRxc1d1eWkxaWNaUUlMTDBSQWZVPQ==/attach/object/PC4JIAYBEU)

![]() 表示用户 i 的基于 Top-k 的相邻用户，矩阵 PU 表示用户之间的经过归一化后的相似度，

![]() 表示服务 j 的基于 Top-k 的相邻服务，矩阵 PS表示服务之间的经过归一化后的相似度

之后使用梯度下降的方法，寻找局部最优解。

### UIPCC

**🔔**

the user-based PCC approach and the item-based PCC approach

文章：[QoS-Aware Web Service Recommendation by Collaborative Filtering](https://ieeexplore.ieee.org/document/5674010?arnumber=5674010)

**运行结果**

![](https://www.kdocs.cn/api/v3/office/copy/K2FTQ0dKSGxiRURzTW91a3pZOVdzL3hVOXNqUEgybUVOeVhOa0UrNmF6elRZdFpvU09tT2lBSnZQalNIb3BtdmZnb2crSThBT2NZTEZlaWJHOFNESzJQQ0Q3bHB0WWNuQk8zZzlyNzROSEFyQmE2ZTVwYXBkWWtuVk9kVFMxcVl5QmwyeFB3TXZ6K0tTMHZnVnBVeDE0VXdpeWNVZmZJMDFLczBVWkRJSjMrM3BGSStUOTN1cHhCQlpnRlR5ZEZNSDMwYkZTVWgxZEVqN09PZys2aUd1RTZoQ0VZYW9FWmQrZXNaYTdWUzBPTFh2V0ZMQUtzdU5pMjRxc1d1eWkxaWNaUUlMTDBSQWZVPQ==/attach/object/IXHZIAYAZI)

![](https://www.kdocs.cn/api/v3/office/copy/K2FTQ0dKSGxiRURzTW91a3pZOVdzL3hVOXNqUEgybUVOeVhOa0UrNmF6elRZdFpvU09tT2lBSnZQalNIb3BtdmZnb2crSThBT2NZTEZlaWJHOFNESzJQQ0Q3bHB0WWNuQk8zZzlyNzROSEFyQmE2ZTVwYXBkWWtuVk9kVFMxcVl5QmwyeFB3TXZ6K0tTMHZnVnBVeDE0VXdpeWNVZmZJMDFLczBVWkRJSjMrM3BGSStUOTN1cHhCQlpnRlR5ZEZNSDMwYkZTVWgxZEVqN09PZys2aUd1RTZoQ0VZYW9FWmQrZXNaYTdWUzBPTFh2V0ZMQUtzdU5pMjRxc1d1eWkxaWNaUUlMTDBSQWZVPQ==/attach/object/MUIZKAYASY)

**方法原理**

UIPCC 使用**协作过滤**的方法，利用Web服务用户过去的使用经验来预测Web服务的QoS值并提出Web服务推荐。 作者首先为过去从不同服务用户收集Web服务QoS信息提出了一种用户协作机制。 然后，基于收集的QoS数据，设计了一种协作过滤方法来预测Web服务QoS值。 最后，使用以WSRec的原型由Java语言实现，并部署到Internet上以进行实际实验。

![](https://www.kdocs.cn/api/v3/office/copy/K2FTQ0dKSGxiRURzTW91a3pZOVdzL3hVOXNqUEgybUVOeVhOa0UrNmF6elRZdFpvU09tT2lBSnZQalNIb3BtdmZnb2crSThBT2NZTEZlaWJHOFNESzJQQ0Q3bHB0WWNuQk8zZzlyNzROSEFyQmE2ZTVwYXBkWWtuVk9kVFMxcVl5QmwyeFB3TXZ6K0tTMHZnVnBVeDE0VXdpeWNVZmZJMDFLczBVWkRJSjMrM3BGSStUOTN1cHhCQlpnRlR5ZEZNSDMwYkZTVWgxZEVqN09PZys2aUd1RTZoQ0VZYW9FWmQrZXNaYTdWUzBPTFh2V0ZMQUtzdU5pMjRxc1d1eWkxaWNaUUlMTDBSQWZVPQ==/attach/object/CO6ZIAYAPU)

**流程：**

1.服务用户将过去的Web服务QoS数据贡献给中央服务器WSRec 。

2. WSRec从培训用户中选择类似用户作为活动用户。训练用户代表其QoS值存储在WSRec服务器中并用于为活动用户进行值预测的服务用户。
3. WSRec预测活动用户的Web服务的QoS值。
4. WSRec根据不同Web服务的预测QoS值提出Web服务推荐。

5.服务用户接收预测的QoS值以及推荐结果，其可以被用于辅助决策（例如，服务选择，复合服务性能预测等。

1. 使用 Person 相关系数来计算两个服务用户 a、u 之间的相似度：

![](https://www.kdocs.cn/api/v3/office/copy/K2FTQ0dKSGxiRURzTW91a3pZOVdzL3hVOXNqUEgybUVOeVhOa0UrNmF6elRZdFpvU09tT2lBSnZQalNIb3BtdmZnb2crSThBT2NZTEZlaWJHOFNESzJQQ0Q3bHB0WWNuQk8zZzlyNzROSEFyQmE2ZTVwYXBkWWtuVk9kVFMxcVl5QmwyeFB3TXZ6K0tTMHZnVnBVeDE0VXdpeWNVZmZJMDFLczBVWkRJSjMrM3BGSStUOTN1cHhCQlpnRlR5ZEZNSDMwYkZTVWgxZEVqN09PZys2aUd1RTZoQ0VZYW9FWmQrZXNaYTdWUzBPTFh2V0ZMQUtzdU5pMjRxc1d1eWkxaWNaUUlMTDBSQWZVPQ==/attach/object/HW6ZIAYA44)

  计算两个 Web 服务之间的相似度：

![](https://www.kdocs.cn/api/v3/office/copy/K2FTQ0dKSGxiRURzTW91a3pZOVdzL3hVOXNqUEgybUVOeVhOa0UrNmF6elRZdFpvU09tT2lBSnZQalNIb3BtdmZnb2crSThBT2NZTEZlaWJHOFNESzJQQ0Q3bHB0WWNuQk8zZzlyNzROSEFyQmE2ZTVwYXBkWWtuVk9kVFMxcVl5QmwyeFB3TXZ6K0tTMHZnVnBVeDE0VXdpeWNVZmZJMDFLczBVWkRJSjMrM3BGSStUOTN1cHhCQlpnRlR5ZEZNSDMwYkZTVWgxZEVqN09PZys2aUd1RTZoQ0VZYW9FWmQrZXNaYTdWUzBPTFh2V0ZMQUtzdU5pMjRxc1d1eWkxaWNaUUlMTDBSQWZVPQ==/attach/object/M26ZIAYAV4)

   作者认为 PCC 会高估一些实际上并不相似，但恰巧有相似 QoS 的 Web 服务的相似度，因此，作者提出了改进的 PCC 计算定义：

![](https://www.kdocs.cn/api/v3/office/copy/K2FTQ0dKSGxiRURzTW91a3pZOVdzL3hVOXNqUEgybUVOeVhOa0UrNmF6elRZdFpvU09tT2lBSnZQalNIb3BtdmZnb2crSThBT2NZTEZlaWJHOFNESzJQQ0Q3bHB0WWNuQk8zZzlyNzROSEFyQmE2ZTVwYXBkWWtuVk9kVFMxcVl5QmwyeFB3TXZ6K0tTMHZnVnBVeDE0VXdpeWNVZmZJMDFLczBVWkRJSjMrM3BGSStUOTN1cHhCQlpnRlR5ZEZNSDMwYkZTVWgxZEVqN09PZys2aUd1RTZoQ0VZYW9FWmQrZXNaYTdWUzBPTFh2V0ZMQUtzdU5pMjRxc1d1eWkxaWNaUUlMTDBSQWZVPQ==/attach/object/QS6ZIAYA6U)

1. user-item 矩阵通常非常稀疏，这将极大的影响预测进度，文章提出了一种使得矩阵更密集的缺失值预测方法
   * 传统的 Top-K 算法根据 PCC 相似度对邻居进行排名，选择前 k 个相似的邻居进行缺失值预测，但实际上，用户项矩阵中，某些项可能邻居很少，甚至没有邻居，这将大大降低预测精度
   * 文章提出了增强的 Top K 算法，排除 PCC 相似度小于或等于 0 的邻居

![](https://www.kdocs.cn/api/v3/office/copy/K2FTQ0dKSGxiRURzTW91a3pZOVdzL3hVOXNqUEgybUVOeVhOa0UrNmF6elRZdFpvU09tT2lBSnZQalNIb3BtdmZnb2crSThBT2NZTEZlaWJHOFNESzJQQ0Q3bHB0WWNuQk8zZzlyNzROSEFyQmE2ZTVwYXBkWWtuVk9kVFMxcVl5QmwyeFB3TXZ6K0tTMHZnVnBVeDE0VXdpeWNVZmZJMDFLczBVWkRJSjMrM3BGSStUOTN1cHhCQlpnRlR5ZEZNSDMwYkZTVWgxZEVqN09PZys2aUd1RTZoQ0VZYW9FWmQrZXNaYTdWUzBPTFh2V0ZMQUtzdU5pMjRxc1d1eWkxaWNaUUlMTDBSQWZVPQ==/attach/object/AS7JIAYAPQ)

* QoS 值的预测公式：
  * 基于用户的方法：

![](https://www.kdocs.cn/api/v3/office/copy/K2FTQ0dKSGxiRURzTW91a3pZOVdzL3hVOXNqUEgybUVOeVhOa0UrNmF6elRZdFpvU09tT2lBSnZQalNIb3BtdmZnb2crSThBT2NZTEZlaWJHOFNESzJQQ0Q3bHB0WWNuQk8zZzlyNzROSEFyQmE2ZTVwYXBkWWtuVk9kVFMxcVl5QmwyeFB3TXZ6K0tTMHZnVnBVeDE0VXdpeWNVZmZJMDFLczBVWkRJSjMrM3BGSStUOTN1cHhCQlpnRlR5ZEZNSDMwYkZTVWgxZEVqN09PZys2aUd1RTZoQ0VZYW9FWmQrZXNaYTdWUzBPTFh2V0ZMQUtzdU5pMjRxc1d1eWkxaWNaUUlMTDBSQWZVPQ==/attach/object/GW7JIAYAFU)

* 基于项目的方法：

![](https://www.kdocs.cn/api/v3/office/copy/K2FTQ0dKSGxiRURzTW91a3pZOVdzL3hVOXNqUEgybUVOeVhOa0UrNmF6elRZdFpvU09tT2lBSnZQalNIb3BtdmZnb2crSThBT2NZTEZlaWJHOFNESzJQQ0Q3bHB0WWNuQk8zZzlyNzROSEFyQmE2ZTVwYXBkWWtuVk9kVFMxcVl5QmwyeFB3TXZ6K0tTMHZnVnBVeDE0VXdpeWNVZmZJMDFLczBVWkRJSjMrM3BGSStUOTN1cHhCQlpnRlR5ZEZNSDMwYkZTVWgxZEVqN09PZys2aUd1RTZoQ0VZYW9FWmQrZXNaYTdWUzBPTFh2V0ZMQUtzdU5pMjRxc1d1eWkxaWNaUUlMTDBSQWZVPQ==/attach/object/KS7JIAYADM)

* 两方法结合：

![](https://www.kdocs.cn/api/v3/office/copy/K2FTQ0dKSGxiRURzTW91a3pZOVdzL3hVOXNqUEgybUVOeVhOa0UrNmF6elRZdFpvU09tT2lBSnZQalNIb3BtdmZnb2crSThBT2NZTEZlaWJHOFNESzJQQ0Q3bHB0WWNuQk8zZzlyNzROSEFyQmE2ZTVwYXBkWWtuVk9kVFMxcVl5QmwyeFB3TXZ6K0tTMHZnVnBVeDE0VXdpeWNVZmZJMDFLczBVWkRJSjMrM3BGSStUOTN1cHhCQlpnRlR5ZEZNSDMwYkZTVWgxZEVqN09PZys2aUd1RTZoQ0VZYW9FWmQrZXNaYTdWUzBPTFh2V0ZMQUtzdU5pMjRxc1d1eWkxaWNaUUlMTDBSQWZVPQ==/attach/object/QO7JIAYAUE)

![](https://www.kdocs.cn/api/v3/office/copy/K2FTQ0dKSGxiRURzTW91a3pZOVdzL3hVOXNqUEgybUVOeVhOa0UrNmF6elRZdFpvU09tT2lBSnZQalNIb3BtdmZnb2crSThBT2NZTEZlaWJHOFNESzJQQ0Q3bHB0WWNuQk8zZzlyNzROSEFyQmE2ZTVwYXBkWWtuVk9kVFMxcVl5QmwyeFB3TXZ6K0tTMHZnVnBVeDE0VXdpeWNVZmZJMDFLczBVWkRJSjMrM3BGSStUOTN1cHhCQlpnRlR5ZEZNSDMwYkZTVWgxZEVqN09PZys2aUd1RTZoQ0VZYW9FWmQrZXNaYTdWUzBPTFh2V0ZMQUtzdU5pMjRxc1d1eWkxaWNaUUlMTDBSQWZVPQ==/attach/object/S27JIAYAPI)

![](https://www.kdocs.cn/api/v3/office/copy/K2FTQ0dKSGxiRURzTW91a3pZOVdzL3hVOXNqUEgybUVOeVhOa0UrNmF6elRZdFpvU09tT2lBSnZQalNIb3BtdmZnb2crSThBT2NZTEZlaWJHOFNESzJQQ0Q3bHB0WWNuQk8zZzlyNzROSEFyQmE2ZTVwYXBkWWtuVk9kVFMxcVl5QmwyeFB3TXZ6K0tTMHZnVnBVeDE0VXdpeWNVZmZJMDFLczBVWkRJSjMrM3BGSStUOTN1cHhCQlpnRlR5ZEZNSDMwYkZTVWgxZEVqN09PZys2aUd1RTZoQ0VZYW9FWmQrZXNaYTdWUzBPTFh2V0ZMQUtzdU5pMjRxc1d1eWkxaWNaUUlMTDBSQWZVPQ==/attach/object/UW7JIAYAYM)

![]() 可以自动平衡基于用户的预测和基于项目的预测，提高了方法适用于不同数据集的可行性。
