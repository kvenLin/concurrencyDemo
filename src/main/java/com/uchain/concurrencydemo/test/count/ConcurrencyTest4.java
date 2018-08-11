package com.uchain.concurrencydemo.test.count;

import com.uchain.concurrencydemo.annonation.NotThreadSafe;
import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Semaphore;

@Slf4j
@NotThreadSafe
public class ConcurrencyTest4 {
    //请求的总数
    public static int clientTotal = 5000;

    //同时并发的线程数
    public static int threadTotal = 200;

    //volatile不具备原子性
    public static volatile int count = 0;

    public static void main(String[] args) throws InterruptedException {
        ExecutorService executorService = Executors.newCachedThreadPool();//定义线程池
        final Semaphore semaphore = new Semaphore(threadTotal);//定义允许的并发量
        final CountDownLatch countDownLatch = new CountDownLatch(clientTotal);//定义允许的请求总数
        for (int i = 0; i < clientTotal; i++) {
            executorService.execute(()->{
                try {
                    semaphore.acquire();
                    add();
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
        log.info("stringBuffer:{}",count);
    }


    //线程不安全的方法
    private static void add(){
        count++;
        //1.取出count
        //2.+1
        //3.写回主存
    }
}
