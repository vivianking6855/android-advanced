package com.vv.ipc.book;

import android.annotation.SuppressLint;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.os.RemoteException;
import android.support.v7.app.AppCompatActivity;
import android.text.method.ScrollingMovementMethod;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.vv.ipc.Const;
import com.vv.ipc.IBookManager;
import com.vv.ipc.INewBookListener;
import com.vv.ipc.R;

import java.util.Date;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class BookManagerActivity extends AppCompatActivity {
    private static final String TAG = BookManagerActivity.class.getSimpleName();

    private IBookManager mRemoteBookManager;
    private ExecutorService bookThreadPool = Executors.newFixedThreadPool(4);

    private TextView mTVShow;

    @SuppressLint("HandlerLeak")
    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case Const.MSG_UPDATE_UI:
                    String show = msg.obj.toString();
                    mTVShow.append(show);
                    break;
                default:
                    super.handleMessage(msg);
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_book_manager);
        initView();

        // bind service : after 5.0 must follow
        // https://developer.android.com/google/play/billing/billing_integrate.html#billing-requests
        Intent intent = new Intent(this, BookManagerService.class);
        intent.setPackage(getPackageName());
        bindService(intent, mServiceConn, Context.BIND_AUTO_CREATE);
    }

    private void initView() {
        mTVShow = (TextView) findViewById(R.id.show);
        mTVShow.setMovementMethod(ScrollingMovementMethod.getInstance());
    }

    private IBinder.DeathRecipient mDeathRecipient = new IBinder.DeathRecipient() {
        @Override
        public void binderDied() {
            Log.d(TAG, "Binder died, tid = " + Thread.currentThread().getName());
            if (mRemoteBookManager == null) {
                return;
            }
            mRemoteBookManager.asBinder().unlinkToDeath(mDeathRecipient, 0);
            mRemoteBookManager = null;

            // you could re connect service here
        }
    };

    private ServiceConnection mServiceConn = new ServiceConnection() {
        public void onServiceConnected(ComponentName className, IBinder service) {
            mRemoteBookManager = IBookManager.Stub.asInterface(service);
            if (mRemoteBookManager != null)
                try {
                    mRemoteBookManager.asBinder().linkToDeath(mDeathRecipient, 0);
                    mRemoteBookManager.registerListener(mNewBookListener);
                } catch (RemoteException e) {
                    e.printStackTrace();
                }
            Log.d(TAG, "bind service");
        }

        public void onServiceDisconnected(ComponentName className) {
            mRemoteBookManager = null;
            Log.d(TAG, "onServiceDisconnected, tid = " + Thread.currentThread().getName());
        }
    };

    private INewBookListener mNewBookListener = new INewBookListener.Stub() {

        @Override
        public void onNewBookArrived(Book newBook) throws RemoteException {
            mHandler.obtainMessage(Const.MSG_UPDATE_UI, "rev new book notify : " + newBook.toString() + "\r\n")
                    .sendToTarget();
        }
    };

    public void getBookList(View v) {
        if (mRemoteBookManager == null) {
            Log.w(TAG, "mRemoteBookManager is null");
            return;
        }

        bookThreadPool.execute(new Runnable() {
            @Override
            public void run() {
                try {
                    List<Book> list = mRemoteBookManager.getBookList();
                    Message msg = mHandler.obtainMessage(Const.MSG_UPDATE_UI);
                    msg.obj = String.format("-- books %s : %s \r\n", Const.DATA_FORMAT.format(new Date()), list.toString());
                    msg.sendToTarget();
                } catch (RemoteException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    public void addBook(View v) {
        if (mRemoteBookManager == null) {
            Log.w(TAG, "mRemoteBookManager is null");
            return;
        }

        bookThreadPool.execute(new Runnable() {
            @Override
            public void run() {
                try {
                    int bookId = mRemoteBookManager.getBookSize() + 1;
                    Book newBook = new Book(bookId, "new-" + bookId);
                    mRemoteBookManager.addBook(newBook);
                    Log.d(TAG, "add book:" + newBook);
                } catch (RemoteException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    @Override
    public void onDestroy() {
        safeQuitService();
        super.onDestroy();
    }

    private void safeQuitService() {
        if (mRemoteBookManager != null) {
            try {
                if (mRemoteBookManager.asBinder().isBinderAlive()) {
                    Log.d(TAG, "unregister listener:" + mNewBookListener);
                    mRemoteBookManager.unregisterListener(mNewBookListener);
                }

                unbindService(mServiceConn);
                Log.d(TAG, "unBindService mRemoteBookManager");

                mRemoteBookManager = null;
            } catch (RemoteException e) {
                e.printStackTrace();
            }
        }
    }

}
