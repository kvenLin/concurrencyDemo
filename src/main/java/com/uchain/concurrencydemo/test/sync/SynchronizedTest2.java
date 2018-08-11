package com.uchain.concurrencydemo.test.sync;

import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Slf4j
public class SynchronizedTest2 {

    //修饰一个类
    public static void test1(int j){
        synchronized (SynchronizedTest2.class){
            for (int i = 0; i < 10; i++) {
                log.info("test1 {} - {}",j,i);
            }
        }
    }

    //TODO,注:如果这个类是个父类,子类在继承后是带不上父类方法的synchronize关键字的,因为synchronize不属于方法声明的一部分
    //修饰一个静态方法
    public static synchronized void test2(int j){
        for (int i = 0; i < 10; i++) {
            log.info("test2 {} - {}",j,i);
        }
    }

    public static void main(String[] args) {
        SynchronizedTest2 synchronizedTest1 = new SynchronizedTest2();
        SynchronizedTest2 synchronizedTest2 = new SynchronizedTest2();
        ExecutorService executorService = Executors.newCachedThreadPool();
        //模拟两次同时调用一个方法
        executorService.execute(()->{
            synchronizedTest1.test1(1);
        });
        executorService.execute(()->{
            synchronizedTest2.test1(2);
        });
        executorService.shutdown();
    }
}
