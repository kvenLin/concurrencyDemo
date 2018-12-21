package com.uchain.concurrencydemo.test.singleton;

import com.uchain.concurrencydemo.annonation.NotThreadSafe;

/**
 * 懒汉模式:
 * 单例的实例在第一次使用时进行创建
 */
@NotThreadSafe
public class SingletonExample1 {

    //私有的构造函数
    private SingletonExample1(){

    }
    //单例的对象
    public static SingletonExample1 instance = null;

    //静态的工厂方法
    public static SingletonExample1 getInstance(){
        if (instance == null){//多线程都可能走到这里,这时存在线程不安全
            //若多个线程走到这里会创建多个对象,即多次调用构造方法
            instance = new SingletonExample1();
        }
        return instance;
    }
}
