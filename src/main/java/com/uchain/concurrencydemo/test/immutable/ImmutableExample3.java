package com.uchain.concurrencydemo.test.immutable;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.ImmutableSet;
import com.uchain.concurrencydemo.annonation.ThreadSafe;
import lombok.extern.slf4j.Slf4j;

import java.util.List;

@Slf4j
@ThreadSafe
public class ImmutableExample3 {

    private final static ImmutableList<Integer> list = ImmutableList.of(1,2,3);
    private final static ImmutableSet set = ImmutableSet.copyOf(list);
    private final static ImmutableMap<Integer,Integer> map = ImmutableMap.of(1,2,3,4);
    private final static ImmutableMap<Integer,Integer> map2 = ImmutableMap.<Integer,Integer>builder()
            .put(1,2).put(3,4).build();

    public static void main(String[] args) {
        log.info("{}",map2.get(3));
    }

}
