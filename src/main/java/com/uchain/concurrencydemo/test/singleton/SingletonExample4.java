package com.uchain.concurrencydemo.test.singleton;

import com.uchain.concurrencydemo.annonation.NotRecommend;
import com.uchain.concurrencydemo.annonation.NotThreadSafe;
import com.uchain.concurrencydemo.annonation.ThreadSafe;

/**
 * 懒汉模式==>双重同步锁单例模式
 * 单例的实例在第一次使用时进行创建
 */
@NotThreadSafe
public class SingletonExample4 {

    //私有的构造函数
    private SingletonExample4(){

    }

    //单例的对象
    public static SingletonExample4 instance = null;

    //1.memory = allocate() 分配对象内存空间
    //2.ctorInstance()初始化对象
    //3.instance = memory 设置instance指向刚分配的内存

    //JVM和cpu优化,发生了指令重排

    //1.memory = allocate() 分配对象内存空间
    //3.instance = memory 设置instance指向刚分配的内存
    //2.ctorInstance()初始化对象

    //静态的工厂方法
    public static SingletonExample4 getInstance(){
        if (instance==null){//双重检测机制        //B
            synchronized (SingletonExample4.class){//同步锁
                if (instance==null){
                    //指令重排后,当A执行到3,instance指向内存还没有进行初始化对象,
                    // 此时B检测到了instance不为空就会直接返回instance,
                    // 但是实际是没有进行初始化对象的操作,虽然几率很小但是是存在线程安全的
                    instance = new SingletonExample4();//A--3
                }
            }
        }
        return instance;
    }
}
