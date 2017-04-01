# 简介

AndroidLib 总结了项目中常用的库，实现Code更便捷的重用，优化项目结构，加速项目开发。

- 丰富的功能库
- 大量的测试用例
- 完整说明文档
- 实时更新

# 使用方法 (Android Studio)

1. load AndroidLib code
2. add following to settings.gradle (cacheLib, appLib 可选). 

        include ':AndroidLib'
        project(':AndroidLib').projectDir = new File('../AndroidLib/')
        include ':AndroidLib:applib'

路径'../AndroidLib/' 需要据文件夹

3. add following to build.gradle (app)

        dependencies {
            compile project(':AndroidLib:cacheLib')
            compile project(':AndroidLib:appLib')
        }

# 现有库

- applib: general app lib（BaseActiviy, BaseAdapter, etc.)

# Java Doc 


# Contributor 