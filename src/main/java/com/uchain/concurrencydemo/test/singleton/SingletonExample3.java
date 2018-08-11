package com.uchain.concurrencydemo.test.singleton;

import com.uchain.concurrencydemo.annonation.NotRecommend;
import com.uchain.concurrencydemo.annonation.ThreadSafe;

/**
 * 懒汉模式:
 * 单例的实例在第一次使用时进行创建
 */
@ThreadSafe
@NotRecommend
public class SingletonExample3 {

    //私有的构造函数
    private SingletonExample3(){

    }
    //单例的对象
    public static SingletonExample3 instance = null;

    //静态的工厂方法
    public static synchronized SingletonExample3 getInstance(){
        if (instance==null){
            instance = new SingletonExample3();
        }
        return instance;
    }
}
