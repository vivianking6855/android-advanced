// IBookManager.aidl
package com.vv.ipc;

import com.vv.ipc.book.Book;
import com.vv.ipc.INewBookListener;

interface IBookManager {
    // get all book list
    List<Book> getBookList();
    // add one book
    void addBook(in Book book);
    // get book list size
    int getBookSize();
    // register and unregister new book listener
    void registerListener(INewBookListener listener);
    void unregisterListener(INewBookListener listener);
}
