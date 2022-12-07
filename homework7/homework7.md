# **实验7：符号执行分析及自动化服务测试**

**🔔**

**实验内容：**

1. 符号执行分析
2. 选取自己之前作业中开发的某一个服务，使用 LLVM 进行符号执行分析，并进行自动化的测试用例生成。
3. 自动化服务测试
4. 执行上述自动生成的测试用例，对照代码分析符号执行技术的原理。
5. 撰写实验报告

> 
> 实验代码及文档：实验7-GitHub

# 基础学习

LLVM：[LLVM基本概念入门 - 知乎 (zhihu.com)](https://zhuanlan.zhihu.com/p/140462815) ；[(42条消息) LLVM介绍_快要瘦了的小林的博客-CSDN博客_llvm](https://blog.csdn.net/weixin_47358139/article/details/126418557)

# KLEE 编译

## **klee是什么**

klee是通过对llvm bitcode进行解释以实现符号执行的工具。它通过插入函数调用（klee_make_symbolic）对内存进行符号化。并且会跟踪符号内存的使用，并收集使用这些符号内存的约束。如果有使用前面符号内存的其他内存，那么该内存也将会被符号化。当遇到一个使用符号化内存的分支时，KLEE会将执行状态一分为二，看看分支的哪一边可以找到一个可以满足符号约束的解。KLEE使用STP来求解这些符号约束。

## 安装步骤

KLEE 给出了在 LLVM11上的编译步骤：[LLVM 11 (recommended) · KLEE](http://klee.github.io/build-llvm11/)，依据官方文档编译安装 KLEE

本次项目采用llvm9

* **安装依赖项：**KREE需要LLVM的所有依赖项（见[这里](http://llvm.org/docs/GettingStarted.html#requirements)）

ubuntu下：

```
sudo apt-get install build-essential cmake curl file g++-multilib gcc-multilib git libcap-dev libgoogle-perftools-dev libncurses5-dev libsqlite3-dev libtcmalloc-minimal4 python3-pip unzip graphviz doxygen
```

* 安装 `<span>lit</span>`用于测试，`<span> tabulate ，klee-stats </span>`和 `<span>wllvm</span>`

```
sudo pip3 install lit wllvm
sudo apt-get install python3-tabulate
```

* **Install LLVM 9**

```
sudo apt-get install clang-9 llvm-9 llvm-9-dev llvm-9-tools
```

* **安装约束求解器：STP**

安装stp依赖：

```
sudo apt-get install cmake bison flex libboost-all-dev python perl zlib1g-dev minisat
```

安装stp

```
git clone https://github.com/stp/stp.git
cd stp
git checkout tags/2.3.3
mkdir build
cd build
cmake ..
make
sudo make install
```

![](https://www.kdocs.cn/api/v3/office/copy/OURYN1MvZXlkWGRZL21lekFSUUpQUmJMdWFvL0lkQzhjcjgvemdxMnRBTEV5U2lsVU5nUGc0eW5Ld3lZMytHUnNycDdvWDBtMEJZbTQra3JqWVlUYmJVZDQzOGttVGI0MVJManVPVVQrd05EeVJuNC9RR2dZTFl3cm9lY1ZWNDVkcnlOUkdmbSthMWVxbS9jMlFwbXFBZ3ZIVHNqK0dDVmIxbXpNa29MQzFVTTZrcm9jUHhoSVA5bmpxQStVSXV2ZmVycmZWT0xucWtLbERXeFVXSEZLZUxWUis1L3I3VXlsZHFibDZQaC83ZWR5QVNkcTkzbmk2WjBDbEcrRGNLVHZWckFKOHRCbHJnPQ==/attach/object/AEMKEAYAPI)

设置一下参数运行无限制

```
ulimit -s unlimited
```

* 下载klee内置库uclibc

构建 uClibc 和 POSIX 环境模型：默认  KLEE 不支持使用外部库，如果要使用 KLEE 分析实际程序，需要启用 KLEE POSIX Runtime

```
git clone https://github.com/klee/klee-uclibc.git  
cd klee-uclibc  
./configure --make-llvm-lib --with-llvm-config=/usr/bin/llvm-config-9 --with-cc=/usr/bin/clang-9
make -j2  
cd ..
```

![](https://www.kdocs.cn/api/v3/office/copy/OURYN1MvZXlkWGRZL21lekFSUUpQUmJMdWFvL0lkQzhjcjgvemdxMnRBTEV5U2lsVU5nUGc0eW5Ld3lZMytHUnNycDdvWDBtMEJZbTQra3JqWVlUYmJVZDQzOGttVGI0MVJManVPVVQrd05EeVJuNC9RR2dZTFl3cm9lY1ZWNDVkcnlOUkdmbSthMWVxbS9jMlFwbXFBZ3ZIVHNqK0dDVmIxbXpNa29MQzFVTTZrcm9jUHhoSVA5bmpxQStVSXV2ZmVycmZWT0xucWtLbERXeFVXSEZLZUxWUis1L3I3VXlsZHFibDZQaC83ZWR5QVNkcTkzbmk2WjBDbEcrRGNLVHZWckFKOHRCbHJnPQ==/attach/object/V4MKEAYAMA)

* 编译 KLEE

下载klee源码

```
git clone https://github.com/klee/klee.git
```

build libc++支持C++

<LIBCXX_DIR>是uclibc的绝对路径, 在本项目中：/home/ivic/tools/klee-uclibc

```
cd klee

LLVM_VERSION=9 SANITIZER_BUILD= BASE=<LIBCXX_DIR> REQUIRES_RTTI=1 DISABLE_ASSERTIONS=1 ENABLE_DEBUG=0 ENABLE_OPTIMIZED=1 ./scripts/build/build.sh libcxx

---
LLVM_VERSION=9 SANITIZER_BUILD= BASE=/home/ivic/tools/klee-uclibc REQUIRES_RTTI=1 DISABLE_ASSERTIONS=1 ENABLE_DEBUG=0 ENABLE_OPTIMIZED=1 ./scripts/build/build.sh libcxx
```

构建klee (涉及多个路径）

```
mkdir build
cd build
cmake \
  -DENABLE_SOLVER_STP=ON \
  -DENABLE_POSIX_RUNTIME=ON \
  -DENABLE_KLEE_UCLIBC=ON \
  -DKLEE_UCLIBC_PATH=/home/ivic/tools/klee-uclibc \
  -DENABLE_UNIT_TESTS=OFF \
  -DLLVM_CONFIG_BINARY=/usr/bin/llvm-config-9 \
  -DLLVMCC=/usr/bin/clang-9 \
  -DLLVMCXX=/usr/bin/clang++-9 \
  ..
```

![](https://www.kdocs.cn/api/v3/office/copy/OURYN1MvZXlkWGRZL21lekFSUUpQUmJMdWFvL0lkQzhjcjgvemdxMnRBTEV5U2lsVU5nUGc0eW5Ld3lZMytHUnNycDdvWDBtMEJZbTQra3JqWVlUYmJVZDQzOGttVGI0MVJManVPVVQrd05EeVJuNC9RR2dZTFl3cm9lY1ZWNDVkcnlOUkdmbSthMWVxbS9jMlFwbXFBZ3ZIVHNqK0dDVmIxbXpNa29MQzFVTTZrcm9jUHhoSVA5bmpxQStVSXV2ZmVycmZWT0xucWtLbERXeFVXSEZLZUxWUis1L3I3VXlsZHFibDZQaC83ZWR5QVNkcTkzbmk2WjBDbEcrRGNLVHZWckFKOHRCbHJnPQ==/attach/object/7YPKEAYBFQ)

开始安装

```
make
sudo make install
```

![](https://www.kdocs.cn/api/v3/office/copy/OURYN1MvZXlkWGRZL21lekFSUUpQUmJMdWFvL0lkQzhjcjgvemdxMnRBTEV5U2lsVU5nUGc0eW5Ld3lZMytHUnNycDdvWDBtMEJZbTQra3JqWVlUYmJVZDQzOGttVGI0MVJManVPVVQrd05EeVJuNC9RR2dZTFl3cm9lY1ZWNDVkcnlOUkdmbSthMWVxbS9jMlFwbXFBZ3ZIVHNqK0dDVmIxbXpNa29MQzFVTTZrcm9jUHhoSVA5bmpxQStVSXV2ZmVycmZWT0xucWtLbERXeFVXSEZLZUxWUis1L3I3VXlsZHFibDZQaC83ZWR5QVNkcTkzbmk2WjBDbEcrRGNLVHZWckFKOHRCbHJnPQ==/attach/object/4EP2EAYAOA)

安装完成后，检测版本：

![](https://www.kdocs.cn/api/v3/office/copy/OURYN1MvZXlkWGRZL21lekFSUUpQUmJMdWFvL0lkQzhjcjgvemdxMnRBTEV5U2lsVU5nUGc0eW5Ld3lZMytHUnNycDdvWDBtMEJZbTQra3JqWVlUYmJVZDQzOGttVGI0MVJManVPVVQrd05EeVJuNC9RR2dZTFl3cm9lY1ZWNDVkcnlOUkdmbSthMWVxbS9jMlFwbXFBZ3ZIVHNqK0dDVmIxbXpNa29MQzFVTTZrcm9jUHhoSVA5bmpxQStVSXV2ZmVycmZWT0xucWtLbERXeFVXSEZLZUxWUis1L3I3VXlsZHFibDZQaC83ZWR5QVNkcTkzbmk2WjBDbEcrRGNLVHZWckFKOHRCbHJnPQ==/attach/object/BEQKEAYACY)

# 使用 KLEE 进行符号测试

编写一段测试代码，该段代码对输入的符号 x，进行判断，比较其和 0 的大小：

```
#include "stdio.h"

int get_sign(int x) {
  if (x == 0)
     return 0;

  if (x < 0)
     return -1;
  else
     return 1;
}

int main() {
  int a;
  klee_make_symbolic(&a, sizeof(a), "a");
  return get_sign(a);
}
```

为了使用 KLEE 进行测试，使用klee_make_symbolic()函数，对需要测试的变量进行标记。

- 之后，使用 LLVM 将源代码编译成 LLVM bitcode

```
clang -I ../../include -emit-llvm -c -g -O0 -Xclang -disable-O0-optnone sign.c
```

- 然后使用 klee 进行测试：

```
klee get_sign.bc
```

![](https://www.kdocs.cn/api/v3/office/copy/OURYN1MvZXlkWGRZL21lekFSUUpQUmJMdWFvL0lkQzhjcjgvemdxMnRBTEV5U2lsVU5nUGc0eW5Ld3lZMytHUnNycDdvWDBtMEJZbTQra3JqWVlUYmJVZDQzOGttVGI0MVJManVPVVQrd05EeVJuNC9RR2dZTFl3cm9lY1ZWNDVkcnlOUkdmbSthMWVxbS9jMlFwbXFBZ3ZIVHNqK0dDVmIxbXpNa29MQzFVTTZrcm9jUHhoSVA5bmpxQStVSXV2ZmVycmZWT0xucWtLbERXeFVXSEZLZUxWUis1L3I3VXlsZHFibDZQaC83ZWR5QVNkcTkzbmk2WjBDbEcrRGNLVHZWckFKOHRCbHJnPQ==/attach/object/XUQ2EAYADE)

  可以看到，KLEE 判断出程序存在3条路径，生成了 3 个测试用例

  可以在 klee-last 目录下，看到生成的测试集：

![](https://www.kdocs.cn/api/v3/office/copy/OURYN1MvZXlkWGRZL21lekFSUUpQUmJMdWFvL0lkQzhjcjgvemdxMnRBTEV5U2lsVU5nUGc0eW5Ld3lZMytHUnNycDdvWDBtMEJZbTQra3JqWVlUYmJVZDQzOGttVGI0MVJManVPVVQrd05EeVJuNC9RR2dZTFl3cm9lY1ZWNDVkcnlOUkdmbSthMWVxbS9jMlFwbXFBZ3ZIVHNqK0dDVmIxbXpNa29MQzFVTTZrcm9jUHhoSVA5bmpxQStVSXV2ZmVycmZWT0xucWtLbERXeFVXSEZLZUxWUis1L3I3VXlsZHFibDZQaC83ZWR5QVNkcTkzbmk2WjBDbEcrRGNLVHZWckFKOHRCbHJnPQ==/attach/object/KERKEAYACY)
