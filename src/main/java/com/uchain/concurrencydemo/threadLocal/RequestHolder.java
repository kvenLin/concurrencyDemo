package com.uchain.concurrencydemo.threadLocal;

public class RequestHolder {
    public final static ThreadLocal<Long> requestHolder = new InheritableThreadLocal<>();

    public static void add(Long id){
        requestHolder.set(id);
    }

    public static Long getId(){
        return requestHolder.get();
    }
    public static void remove(){
        requestHolder.remove();
    }
}
