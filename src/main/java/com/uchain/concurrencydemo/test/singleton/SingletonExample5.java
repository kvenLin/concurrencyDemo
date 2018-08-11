package com.uchain.concurrencydemo.test.singleton;

import com.uchain.concurrencydemo.annonation.ThreadSafe;

/**
 * 懒汉模式==>双重同步锁单例模式
 * 单例的实例在第一次使用时进行创建
 */
@ThreadSafe
public class SingletonExample5 {

    //私有的构造函数
    private SingletonExample5(){

    }

    //单例的对象,volatile限制发生指令重排,解决了双重检测机制的指令重排引起的线程不安全的问题
    public static volatile SingletonExample5 instance = null;

    //1.memory = allocate() 分配对象内存空间
    //2.ctorInstance()初始化对象
    //3.instance = memory 设置instance指向刚分配的内存

    //静态的工厂方法
    public static SingletonExample5 getInstance(){
        if (instance==null){//双重检测机制        //B
            synchronized (SingletonExample5.class){//同步锁
                if (instance==null){
                    //指令重排后,当A执行到3,instance指向内存还没有进行初始化对象,
                    // 此时B检测到了instance不为空就会直接返回instance,
                    // 但是实际是没有进行初始化对象的操作,虽然几率很小但是是存在线程安全的
                    instance = new SingletonExample5();//A--3
                }
            }
        }
        return instance;
    }
}
