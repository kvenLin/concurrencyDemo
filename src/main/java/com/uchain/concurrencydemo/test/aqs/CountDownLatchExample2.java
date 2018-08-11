package com.uchain.concurrencydemo.test.aqs;

import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

@Slf4j
public class CountDownLatchExample2 {

    private static final int threadCount = 200;

    public static void main(String[] args) throws Exception {

        ExecutorService executorService = Executors.newCachedThreadPool();

        final CountDownLatch countDownLatch = new CountDownLatch(threadCount);
        for (int i = 0; i < threadCount; i++) {
            final int threadNum = i;
            executorService.execute(()->{
                try {
                    test(threadNum);
                } catch (Exception e) {
                    e.printStackTrace();
                }finally {
                    countDownLatch.countDown();
                }
            });
        }
        countDownLatch.await(50,TimeUnit.MILLISECONDS);//指定只等待10毫秒
        //因为test方法休眠时间大于countDownLatch等待的时间,所有会在等待10毫秒后就输出finish信息
        //但后续已经启动的线程还将继续执行直到countDownLatch减为0后线程关闭
        log.info("finish...");
        //调用shutdown方法后并不是立即关闭掉所有线程,而是等待已有的线程执行完后再将其关闭
        executorService.shutdown();
    }

    public static void test(int threadNum) throws Exception {
        Thread.sleep(100);//休眠100毫秒
        log.info("threadNum:{}",threadNum);
    }
}
