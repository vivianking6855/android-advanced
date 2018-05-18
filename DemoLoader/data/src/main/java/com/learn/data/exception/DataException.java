package com.learn.data.exception;

import retrofit2.Response;

/**
 * The type Data exception.
 */
public class DataException extends Exception {
    /**
     * data status code.
     */
    public final int code;

    /**
     * Instantiates a new Data exception.
     *
     * @param code the code
     */
    public DataException(int code) {
        super();
        this.code = code;
    }

}
