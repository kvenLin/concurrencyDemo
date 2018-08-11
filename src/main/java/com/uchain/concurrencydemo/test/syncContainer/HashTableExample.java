package com.uchain.concurrencydemo.test.syncContainer;

import com.uchain.concurrencydemo.annonation.ThreadSafe;
import lombok.extern.slf4j.Slf4j;

import java.util.HashMap;
import java.util.Hashtable;
import java.util.Map;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Semaphore;

@Slf4j
@ThreadSafe
public class HashTableExample {
    //不是一个线程安全的实例
    private static Map<Integer,Integer> map = new Hashtable<>();

    //请求的总数
    public static int clientTotal = 5000;

    //同时并发的线程数
    public static int threadTotal = 200;


    public static void main(String[] args) throws InterruptedException {
        ExecutorService executorService = Executors.newCachedThreadPool();//定义线程池
        final Semaphore semaphore = new Semaphore(threadTotal);//定义允许的并发量
        final CountDownLatch countDownLatch = new CountDownLatch(clientTotal);//定义允许的请求总数
        for (int i = 0; i < clientTotal; i++) {
            final int count = i;
            executorService.execute(()->{
                try {
                    semaphore.acquire();
                    update(count);
                    semaphore.release();
                } catch (Exception e) {
                    e.printStackTrace();
                    log.error("Exception:{}",e);
                }
                countDownLatch.countDown();//没执行一次就进行countDown
            });
        }
        countDownLatch.await();//保证对应的countDown执行到减为0才会通过打印出后面的日志信息
        executorService.shutdown();
        log.info("map:{}", map.size());
    }


    private static void update(int i){
        map.put(i,i);
    }
}
