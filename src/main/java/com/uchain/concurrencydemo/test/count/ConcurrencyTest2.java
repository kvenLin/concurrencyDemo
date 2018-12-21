package com.uchain.concurrencydemo.test.count;

import com.uchain.concurrencydemo.annonation.NotThreadSafe;
import com.uchain.concurrencydemo.annonation.ThreadSafe;
import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Semaphore;
import java.util.concurrent.atomic.AtomicInteger;

@Slf4j
@ThreadSafe
public class ConcurrencyTest2 {
    //请求的总数
    public static int clientTotal = 5000;

    //同时并发的线程数
    public static int threadTotal = 200;

    public static AtomicInteger count = new AtomicInteger(0);

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
        log.info("stringBuffer:{}",count.get());
    }


    private static void add(){
        int re = count.incrementAndGet();//现增加后获取值,得到的结果是准确的
//        int re = stringBuffer.getAndIncrement();//先获取值再进行增加,获取的值会不准确
        log.info("re:{}",re);
        ArrayList arrayList = new ArrayList();
        arrayList.add(re);

    }
}
