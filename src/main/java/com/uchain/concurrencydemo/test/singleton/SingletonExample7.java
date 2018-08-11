package com.uchain.concurrencydemo.test.singleton;


import com.uchain.concurrencydemo.annonation.Recommend;
import com.uchain.concurrencydemo.annonation.ThreadSafe;

/**
 * 枚举式：最安全的
 */
@Recommend
@ThreadSafe
public class SingletonExample7 {

    //私有的构造函数
    private SingletonExample7(){
    }

    public static SingletonExample7 getInstance(){
        return Singleton.INSTANCE.getInstance();
    }

    private enum Singleton{
        INSTANCE;
        private SingletonExample7 singleton;
        //JVM保证这个方法绝对只被调用一次
        Singleton(){
            singleton = new SingletonExample7();
        }
        public SingletonExample7 getInstance(){
            return singleton;
        }
    }

}
