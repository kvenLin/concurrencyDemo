package com.uchain.concurrencydemo.test.immutable;

import com.google.common.collect.Maps;
import com.uchain.concurrencydemo.annonation.NotThreadSafe;
import lombok.extern.slf4j.Slf4j;

import java.util.Map;

@Slf4j
@NotThreadSafe
public class ImmutableExample1 {
    private static final Integer a = 1;
    private static final String  b = "2";
    private static final Map<Integer,Integer> map = Maps.newHashMap();

    static {
        map.put(1,2);
        map.put(3,4);
        map.put(5,6);
    }

    public static void main(String[] args) {
        //引用对象后是不能再进行引用另外的对象,但是对象里的值是可以修改的,所以会存在线程安全问题
        map.put(1,3);
        log.info("{}",map.get(1));
    }


    public void test(final int a){
        //若果一个方法不允许传入的参数发生变化,
        // 可以使用final修饰传入的基本类型的变量
    }
}
