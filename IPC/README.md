## IPC 跨进程通信

[IPC进程间通信 blog](http://vivianking6855.github.io/Android-IPC/)

跨进程通信方式有很多：Intent传递Bundle数据，共享文件，Binder，Intent传递Bundle数据,ContentProvider,Socket.

- Intent 方式较为简单，没有包含在示例中
- file文件在并发读写时会有问题。SharedPreferences多进模式下读写不可靠，不包含在示例中

### 1. static variable did't work well for multi-process

- MainActivity set new sUserId = 2; But SecondActivity get sUserId = 1;
- When start SecondActivity, it run in another process, Application onCreate will enter again.

### 2. Messenger 轻量级的IPC方案

### 3. AIDL

- Book.java 图书信息的类，实现Parcelable
- IBook.aidl 是Book类在AIDL中的声明
- IBookManager Book管理接口：添加，获取图书，监听图书变化
- INewBookListener : 新书通知
- BookManagerActivity: Manager UI
- BookManagerService: Service deal with command from client (run on alone process)
