## Android Message机制

[Android Message blog](http://vivianking6855.github.io/Android-Message)

### 1. Work thread send message to Main thread

- Main thread create Handler，implement handleMessage()
- Work thread send message through handler to update UI. message will add to MessageQueue
- Then Looper will pick out message to Main thread to deal with in handleMessage

### 2. Main thread send message to Work thread

- Create work thread and it's handler and Looper (two ways)
- Main thread send message with handler
