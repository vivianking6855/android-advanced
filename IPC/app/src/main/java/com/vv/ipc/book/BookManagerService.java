package com.vv.ipc.book;

import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;
import android.os.RemoteException;
import android.os.SystemClock;

import com.vv.ipc.IBookManager;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

public class BookManagerService extends Service {
    private static final String TAG = BookManagerService.class.getSimpleName();

    private CopyOnWriteArrayList<Book> mBookList = new CopyOnWriteArrayList<Book>();
    // private CopyOnWriteArrayList<IOnNewBookArrivedListener> mListenerList =
    // new CopyOnWriteArrayList<IOnNewBookArrivedListener>();

    private Binder mBinder = new IBookManager.Stub() {

        @Override
        public List<Book> getBookList() throws RemoteException {
            SystemClock.sleep(2000);
            return mBookList;
        }

        @Override
        public void addBook(Book book) throws RemoteException {
            mBookList.add(book);
        }

        @Override
        public int getBookSize()throws RemoteException {
            return mBookList.size();
        }

//        public boolean onTransact(int code, Parcel data, Parcel reply, int flags)
//                throws RemoteException {
//            int check = checkCallingOrSelfPermission("com.ryg.chapter_2.permission.ACCESS_BOOK_SERVICE");
//            Log.d(TAG, "check=" + check);
//            if (check == PackageManager.PERMISSION_DENIED) {
//                return false;
//            }
//
//            String packageName = null;
//            String[] packages = getPackageManager().getPackagesForUid(
//                    getCallingUid());
//            if (packages != null && packages.length > 0) {
//                packageName = packages[0];
//            }
//            Log.d(TAG, "onTransact: " + packageName);
//            if (!packageName.startsWith("com.ryg")) {
//                return false;
//            }
//
//            return super.onTransact(code, data, reply, flags);
//        }

//        @Override
//        public void registerListener(IOnNewBookArrivedListener listener)
//                throws RemoteException {
//            mListenerList.register(listener);
//
//            final int N = mListenerList.beginBroadcast();
//            mListenerList.finishBroadcast();
//            Log.d(TAG, "registerListener, current size:" + N);
//        }
//
//        @Override
//        public void unregisterListener(IOnNewBookArrivedListener listener)
//                throws RemoteException {
//            boolean success = mListenerList.unregister(listener);
//
//            if (success) {
//                Log.d(TAG, "unregister success.");
//            } else {
//                Log.d(TAG, "not found, can not unregister.");
//            }
//            final int N = mListenerList.beginBroadcast();
//            mListenerList.finishBroadcast();
//            Log.d(TAG, "unregisterListener, current size:" + N);
//        }
    };

    public BookManagerService() {
    }

    @Override
    public void onCreate() {
        super.onCreate();
        mBookList.add(new Book(1, "Android"));
        mBookList.add(new Book(2, "Java"));
    }

    @Override
    public IBinder onBind(Intent intent) {
        return mBinder;
    }

}
