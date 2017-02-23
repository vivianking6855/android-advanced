## 多线程和线程池之MultiThread

Android线程形态

[多线程和线程池 blog](http://vivianking6855.github.io/Multi-Thread/)

- AsyncTask 模拟下载文件。
    - AsyncTask的execute必须在UI线程调用
    - 不要直接调用onPreExecute, doInBackground等方法
    - 一个AsyncTask对象只能执行一次，即只能调用一次execute方法
- IntentService
    - 适合于高优先级的后台任务。
    - 所有task执行完毕后，IntentService会自动退出
    - 可用Messenger，Broadcast等方式更新UI
- 四个基本线程池的使用
    - newFixedThreadPool
    - newCachedThreadPool
    - newScheduledThreadPool
    - newSingleThreadExecutor