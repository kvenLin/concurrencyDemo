package com.uchain.concurrencydemo.test.publish;

import com.uchain.concurrencydemo.annonation.NotRecommend;
import com.uchain.concurrencydemo.annonation.NotThreadSafe;
import lombok.extern.slf4j.Slf4j;

import java.util.Arrays;

@Slf4j
@NotThreadSafe
@NotRecommend
public class Escape {
    private int thisCanBeEscape = 0;

    public Escape() {
        new InnerClass();
    }
    private class InnerClass{
        public InnerClass(){
            log.info("{}",Escape.this.thisCanBeEscape);//在没有完成构造函数之前就进行了引用,存在不安全的因素
        }
    }

    public static void main(String[] args) {
        new Escape();
    }
}
