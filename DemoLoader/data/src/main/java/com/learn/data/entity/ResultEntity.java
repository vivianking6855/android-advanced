package com.learn.data.entity;

/**
 * data return format
 *
 * @param <T>
 */
public class ResultEntity<T> {
    public int state;
    public String error;
    public T infos;
}
