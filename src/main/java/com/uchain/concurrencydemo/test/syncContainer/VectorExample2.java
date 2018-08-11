package com.uchain.concurrencydemo.test.syncContainer;

import com.uchain.concurrencydemo.annonation.NotThreadSafe;
import lombok.extern.slf4j.Slf4j;

import java.util.Vector;

@Slf4j
@NotThreadSafe
public class VectorExample2 {
    public static Vector<Integer> vector = new Vector<>();

    public static void main(String[] args) {
        while (true) {
            for (int i = 0; i < vector.size(); i++) {
                vector.add(i);
            }


            Thread thread1 = new Thread() {
                public void run() {
                    for (int i = 0; i < 10; i++) {
                        vector.remove(i);
                    }
                }
            };

            Thread thread2 = new Thread() {
                public void run() {
                    for (int i = 0; i < 10; i++) {
                        vector.get(i);
                    }
                }
            };
            thread1.start();
            thread2.start();
        }


    }
}
