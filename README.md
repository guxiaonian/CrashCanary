<div align="center">

## CrashCanary

**`Android`Crash日志打印输出**

[![Download](https://api.bintray.com/packages/guxiaonian/crashcanary/crashcanary/images/download.svg) ](https://bintray.com/guxiaonian/crashcanary/crashcanary/_latestVersion)
[![GitHub issues](https://img.shields.io/github/issues/guxiaonian/CrashCanary.svg)](https://github.com/guxiaonian/CrashCanary/issues)
[![GitHub forks](https://img.shields.io/github/forks/guxiaonian/CrashCanary.svg)](https://github.com/guxiaonian/CrashCanary/network)
[![GitHub stars](https://img.shields.io/github/stars/guxiaonian/CrashCanary.svg)](https://github.com/guxiaonian/CrashCanary/stargazers)
[![GitHub license](https://img.shields.io/github/license/guxiaonian/CrashCanary.svg)](http://www.apache.org/licenses/LICENSE-2.0)

</div>
<br>

# 效果展示

![crash2_logo](./img/img1.png)![crash2_logo](./img/img2.png)


# 依赖

```gradle
debugImplementation  'fairy.easy.crashcanary:crashcanary:{latestVersion}'
releaseImplementation  'fairy.easy.crashcanary:crashcanary-no-op:{latestVersion}'
//androidX使用
//debugImplementation  'fairy.easy.crashcanary:crashcanary-androidx:{latestVersion}'

```
      
# 调用方式

```java
public class App extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        CrashCanary.install(this);
    }
}

```
