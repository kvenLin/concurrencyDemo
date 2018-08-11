package com.uchain.concurrencydemo.test.sync;

import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Slf4j
public class SynchronizedTest1 {

    //修饰一个代码块
    public void test1(int j){
        //同步语句块,作用范围是大括号括起来的范围,作用的对象是调用该代码的对象
        synchronized (this){
            for (int i = 0; i < 10; i++) {
                log.info("test1 {} - {}",j,i);
            }
        }
    }

    //TODO,注:如果这个类是个父类,子类在继承后是带不上父类方法的synchronize关键字的,因为synchronize不属于方法声明的一部分
    //修饰一个方法
    public synchronized void test2(int j){
        for (int i = 0; i < 10; i++) {
            log.info("test2 {} - {}",j,i);
        }
    }

    public static void main(String[] args) {
        SynchronizedTest1 synchronizedTest1 = new SynchronizedTest1();
        SynchronizedTest1 synchronizedTest2 = new SynchronizedTest1();
        ExecutorService executorService = Executors.newCachedThreadPool();
        //模拟两次同时调用一个方法
        executorService.execute(()->{
            synchronizedTest1.test2(1);
        });
        executorService.execute(()->{
            synchronizedTest2.test2(2);
        });

    }
}
