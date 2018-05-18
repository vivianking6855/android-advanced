# CleanArchitecture说明

## 使用步骤

1. copy整个文件夹
2. 修改app name -> strings.xml
3. 修改package name

	简单的改法build.gradle中修改： applicationId "com.yours" 

4. 客制化功能

# 默认参考功能说明

项目中默认有三种数据加载：

1. 简单加载：HomeActivity的加载String[]，跳转不同的Fragment
2. AsyncTaskLoader加载：ApkListFragment 通过AsyncTaskLoader加载apk列表
3. 图片加载：PhototFragment 通过AsyncTaskLoader 和 CursorAdapter加载本地图片网络加载：
4. UserFragment 三种加载方式：线程池加载，异步+listener, Rx加载方式

用户可以参考来扩展自己的功能

# 项目结构说明

CleanArchitecture项目包含四层: 

- 展示层
- 业务公共层
- 数据层
- 业务无关层

还有调试debug模块

其中业务采用MVP框架

![](https://i.imgur.com/5X0VIG0.jpg)

详细说明请参看[博客CleanArchitecture](http://vivianking6855.github.io/2018/03/30/Template-Open/)

