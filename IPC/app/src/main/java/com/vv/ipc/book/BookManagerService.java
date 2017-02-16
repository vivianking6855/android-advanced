package com.vv.ipc.book;

import android.app.Service;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Binder;
import android.os.IBinder;
import android.os.Parcel;
import android.os.RemoteCallbackList;
import android.os.RemoteException;
import android.os.SystemClock;
import android.util.Log;

import com.vv.ipc.Const;
import com.vv.ipc.IBookManager;
import com.vv.ipc.INewBookListener;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

public class BookManagerService extends Service {
    private static final String TAG = BookManagerService.class.getSimpleName();

    // book list
    private CopyOnWriteArrayList<Book> mBookList = new CopyOnWriteArrayList<Book>();
    // new book listener RemoteCallbackList used for multi-process listener
    private RemoteCallbackList<INewBookListener> mListenerList = new RemoteCallbackList<INewBookListener>();
    private Binder mBinder = new IBookManager.Stub() {

        @Override
        public List<Book> getBookList() throws RemoteException {
            SystemClock.sleep(2000);
            return mBookList;
        }

        @Override
        public void addBook(Book book) throws RemoteException {
            mBookList.add(book);
            onNewBookArrived(book);
        }

        @Override
        public int getBookSize() throws RemoteException {
            return mBookList.size();
        }

        /**
         * registerListener for CopyOnWriteArrayList<INewBookListener> mListenerList
         * @param listener
         * @throws RemoteException
         */
        @Override
        public void registerListener(INewBookListener listener)
                throws RemoteException {
            mListenerList.register(listener);

            final int N = mListenerList.beginBroadcast();
            mListenerList.finishBroadcast();
            Log.d(TAG, "registerListener, current size:" + N);
        }

        /**
         * unregisterListener for CopyOnWriteArrayList<INewBookListener> mListenerList
         * @param listener
         * @throws RemoteException
         */
        @Override
        public void unregisterListener(INewBookListener listener)
                throws RemoteException {
            boolean res = mListenerList.unregister(listener);
            Log.d(TAG, "unregister " + res);

            final int N = mListenerList.beginBroadcast();
            mListenerList.finishBroadcast();
            Log.d(TAG, "unregisterListener, current size:" + N);
        }

        @Override
        public boolean onTransact(int code, Parcel data, Parcel reply, int flags) throws RemoteException {
            if (!verifyPackageName()) {
                return false;
            }

            return super.onTransact(code, data, reply, flags);
        }

        private boolean verifyPackageName() {
            String packageName = null;
            String[] packages = getPackageManager().getPackagesForUid(getCallingUid());
            if (packages != null && packages.length > 0) {
                packageName = packages[0];
            }
            Log.d(TAG, "verifyPackageName: " + packageName);
            if (!packageName.startsWith("com.vv.ipc")) {
                return false;
            }

            return true;
        }
    };

    public BookManagerService() {
    }

    @Override
    public void onCreate() {
        super.onCreate();
        simulatorInit();
    }

    private void simulatorInit() {
        // simulator init book list
        mBookList.add(new Book(1, "Android"));
        mBookList.add(new Book(2, "Java"));
    }

    @Override
    public IBinder onBind(Intent intent) {
        if (!verifyPermission()) {
            return null;
        }
        return mBinder;
    }

    private boolean verifyPermission() {
        int check = checkCallingOrSelfPermission(Const.PERMISSION_ACCESS_BOOK_SERVICE);
        Log.d(TAG, "checkPermission " + check);
        if (check == PackageManager.PERMISSION_DENIED) {
            Log.d(TAG, "checkPermission: permission deny");
            return false;
        }

        return true;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.d(TAG, "onDestroy");
    }

    private synchronized void onNewBookArrived(Book book) throws RemoteException {
        final int N = mListenerList.beginBroadcast();
        for (int i = 0; i < N; i++) {
            INewBookListener listener = mListenerList.getBroadcastItem(i);
            if (listener != null) {
                try {
                    listener.onNewBookArrived(book);
                } catch (RemoteException e) {
                    e.printStackTrace();
                }
            }
        }
        mListenerList.finishBroadcast();
    }

}
