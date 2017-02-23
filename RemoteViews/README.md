## RemoteViews

[RemoteViews blog](http://vivianking6855.github.io/Android-Nano-Tips-12-RemoteViews/)

逻辑如下：

- 有A,B 2个Activity分别运行在不同的进程中，一个A(MainActivity),一个B(ClientActivity)
- A接受通知，模拟通知栏的效果
- B可以不停的地发送通知栏消息（模拟消息）。通知方案选用Broadcast（系统是用Binder）
