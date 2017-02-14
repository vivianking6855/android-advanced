# android-advanced Demo 说明

## 1. IPC 跨进程通信

[IPC进程间通信 blog](http://vivianking6855.github.io/Android-IPC/)

## 2. RemoteViews

[RemoteViews blog](http://vivianking6855.github.io/Android-Nano-Tips-12-RemoteViews/)

逻辑如下：

- 有A,B 2个Activity分别运行在不同的进程中，一个A(MainActivity),一个B(ClientActivity)
- A接受通知，模拟通知栏的效果
- B可以不停的地发送通知栏消息（模拟消息）。通知方案选用Broadcast（系统是用Binder）

## 3. Android Message机制

### Demo One : Work thread send message to Main thread

- 1.	Main thread create Handler，implement handleMessage()
- 2.	Work thread send message through handler to update UI. message will add to MessageQueue
- 3.	Then Looper will pick out message to Main thread to deal with in handleMessage

### Demo Two: Main thread send message to Work thread

- 1.	Create work thread and it's handler and Looper (two ways)
- 2.	Main thread send message with handler


## 4. 多线程和线程池

### MultiThread: Android线程形态

### ThreadSync：线程同步和concurrent包

## 5. ImageCache： Image加载和缓存