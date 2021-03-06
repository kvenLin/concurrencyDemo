package com.uchain.concurrencydemo.test.aqs;

import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.*;

@Slf4j
public class SemaphoreExample4 {

    private static final int threadCount = 200;

    public static void main(String[] args) throws Exception {

        ExecutorService executorService = Executors.newCachedThreadPool();

        final Semaphore semaphore = new Semaphore(3);//对并发访问的控制数

        final CountDownLatch countDownLatch = new CountDownLatch(threadCount);
        for (int i = 0; i < threadCount; i++) {
            final int threadNum = i;
            executorService.execute(()->{
                try {
                    if (semaphore.tryAcquire(5000,TimeUnit.MILLISECONDS)){
                        test(threadNum);
                        semaphore.release();//释放一个许可
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }finally {
                    countDownLatch.countDown();
                }
            });
        }
        countDownLatch.await();
        log.info("finish...");
        executorService.shutdown();
    }

    public static void test(int threadNum) throws Exception {
        log.info("threadNum:{}",threadNum);
        Thread.sleep(1000);
    }
}
