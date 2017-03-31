# AndroidLib

## 使用方法 (Android Studio)

1. load AndroidLib code
2. add following to settings.gradle (cacheLib, appLib 可选). 

        include ':AndroidLib'
        project(':AndroidLib').projectDir = new File('../AndroidLib/')
        include ':AndroidLib:cacheLib'
        include ':AndroidLib:appLib'

路径'../AndroidLib/' 需要据文件夹

3. add following to build.gradle (app)

        dependencies {
            compile project(':AndroidLib:cacheLib')
            compile project(':AndroidLib:appLib')
        }

## 现有公用库

- appLib: general app lib（BaseActiviy, BaseAdapter, etc.)
- cacheLib: web, image cache