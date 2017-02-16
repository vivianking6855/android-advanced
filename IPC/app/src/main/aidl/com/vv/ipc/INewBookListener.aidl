// INewBookListener.aidl
package com.vv.ipc;

import com.vv.ipc.book.Book;

interface INewBookListener {
    void onNewBookArrived(in Book newBook);
}
