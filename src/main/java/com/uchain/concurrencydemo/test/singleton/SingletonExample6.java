package com.uchain.concurrencydemo.test.singleton;


import com.uchain.concurrencydemo.annonation.ThreadSafe;

/**
 * 饿汉模式:
 * 单例的实例在类装载时进行创建
 * 使用饿汉模式时需要注意：
 *  1.在构造方法中没有过多的处理
 *  2.保证实例化后会被调用，避免造成资源的浪费
 */
@ThreadSafe
public class SingletonExample6 {

    //私有的构造函数
    private SingletonExample6(){
    }

    //单例的对象
    public static SingletonExample6 instance = null;

    //通过静态块的方式进行实例
    static {
        instance = new SingletonExample6();
    }

    //静态的工厂方法
    public static SingletonExample6 getInstance(){
        return instance;
    }

    public static void main(String[] args) {
        System.out.println(getInstance());
        System.out.println(getInstance());
    }
}
