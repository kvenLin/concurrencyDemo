package com.uchain.concurrencydemo.test.atomic;

import com.uchain.concurrencydemo.annonation.ThreadSafe;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.atomic.AtomicIntegerFieldUpdater;
import java.util.concurrent.atomic.AtomicReference;

@Slf4j
@ThreadSafe
public class AtomicTest5 {
    private static AtomicIntegerFieldUpdater<AtomicTest5> updater
            = AtomicIntegerFieldUpdater.newUpdater(AtomicTest5.class,"count");

    @Getter
    private volatile int count = 100;//必须使用volatile进行修饰,且非static描述的字段

    public static void main(String[] args) {
        AtomicTest5 atomicTest5  = new AtomicTest5();
        if (updater.compareAndSet(atomicTest5,100,120)){
            log.info("update success 1 ,{}",atomicTest5.getCount());
        }
        if (updater.compareAndSet(atomicTest5,100,120)){
            log.info("update success 2 ,{}",atomicTest5.getCount());
        }else {
            log.info("update failed ,{}",atomicTest5.getCount());

        }
    }
}
