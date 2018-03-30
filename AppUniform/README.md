# AppUniform使用指南

1. copy整个AppUniform文件夹
2. 修改app name -> strings.xml
3. 修改package name
4. 完善自己的功能


# 功能列表

- 重构优化的目录结构

   ![](https://i.imgur.com/9f3OrIV.jpg)

- MVP架构
- 默认搭载常用库

        // webstie
        compile 'com.squareup.okhttp3:okhttp:3.6.0'
        compile 'com.squareup.retrofit2:retrofit:2.2.0'
        compile 'com.squareup.retrofit2:converter-gson:2.0.2'
        compile 'com.google.code.gson:gson:2.8.0'
        // rxjava
        compile 'io.reactivex.rxjava2:rxandroid:2.0.1'
        compile "io.reactivex.rxjava2:rxjava:2.0.8"
        // utils library
        compile 'com.open.library:utilslib:1.0.171019'
