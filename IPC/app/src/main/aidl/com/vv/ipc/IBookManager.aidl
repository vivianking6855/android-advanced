// IBookManager.aidl
package com.vv.ipc;

import com.vv.ipc.book.Book;

interface IBookManager {
    List<Book> getBookList();
    void addBook(in Book book);
    int getBookSize();
}
