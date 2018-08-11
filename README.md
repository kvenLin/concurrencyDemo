# concurrencyDemo
并发学习demo

高并发处理的思路和手段：

	扩容，缓存，队列，应用拆分，限流，服务将级与服务熔断，数据库切库、分库、分表，高可用的一些手段：任务调度分布式elastic-job主备curator的实现、监控报警等机制。

线程安全的三特性: 原子性,可见性,有序性

### 6.1.1原子性
原子性: Atomic包,CAS算法,synchronized,Lock

互斥访问,同一时刻只能有一个线程进行操作
### 6.1.2可见性

    可见性:一个线程对内存的修改可以及时的被其他线程观察到
    导致共享变量在线程之间不可见的原因:
	1.线程交叉执行
	2.重排序结合线程交叉执行
	3.共享变量更新后的值没有在工作内存与主存之间即使更新
JVM关于synchronize的两条规定:

	1.线程解锁前,必须吧共享变量的最新值刷新到主内存
	2.线程加锁时,将清空内存中共享变量的值,从而使用共享变量时需要从内存中重新读取最新的值
	(注意:加锁与解锁是同一把锁)
可见性-volatile:

	通过加入内存屏障和禁止重排序优化来实现
	1.对volatile变量写操作时,会在写操作后加入一条store屏障指令,将本地内存中的共享变量值刷新到主内存中
	2.对volatile变量读操作时,会在读操作前加入一条load屏障指令,从主内存中读取共享变量
### 6.2安全发布对象

发布对象:使一个对象能够被当前范围之外的代码所使用

对象逸出:一种错误的发布. 当一个对象还没有构造完成时,就使它被其他线程所见

安全发布的方法:

	1.在静态初始化函数中初始化一个对象的引用
	2.将对象的引用保存到volatile类型或者AtomicReference对象中
	3.将对象的引用保存到某个正确构造对象的final类型域中
	4.将对象的引用保存到一个由锁保护的域中
### 6.3单例模式的学习

https://blog.csdn.net/Box_clf/article/details/81560384

### 6.4不可变对象

对象不可变需要满足的条件：

	1.对象创建以后其状态就不能再修改
	2.对象所有域都是final类型
	3.对象是正确创建的(在对象创建期间,this引用没有逸出)
常见的线程不安全的类:

	StringBuilder,SimpleDateFormat,ArrayList,HashSet,HashMap
对应的线程安全的类:

	StringBuilder--->StringBuffer
	SimpleDateFormat--->JodaTime 
	HashMap---> HashTable(key,value不能为null)
	Collections.synchronizedXXX(List,Set,Map)
	ArrayList---> CopyOnWriteArrayList
	HashSet---> CopyOnWriteArraySet,TreeSet---> ConcurrentSkipListSet
	HashMap---> Concurr	entHashMap,TreeMap---> ConcurrentSkipListMap
	
### 6.5J.U.C(Java.util.concurrency)

- CountDownLatch

        使用场景: 程序需要等待执行某个条件后才能执行后续的操作
        并行的计算,当某个计算量比较大时可以拆分成多个子任务,等待所有子任务完成之后,父任务再拿到所有子任务的运算结果进行汇总
    
- Semaphore

        使用场景:常用于仅能提供有限访问的资源,比如数据库的连接数只有20,而上层应用可能会远大于20,如果同时对数据库进行操作就可能会无法获取数据库连接数导致异常,这时就可以通过Semaphore来做并发访问控制
    
- CyclicBarrier

多个线程之间进行相互等待

使用场景和CountDownLatch类似

    1.创建对象指明等待的线程数
    2.当前线程完成对应的逻辑操作后调用CyclicBarrier的await()方法进行等待其他线程
    3.当等待其他线程的线程总数达到了指定的数量进行后续的逻辑操作

- ReentrantLock

ReentrantLock(可重入锁)和synchronized区别:

	1.可重入性
	2.锁的实现:synchronized是JVM实现的,ReentrantLock是JDK实现的
	3.性能区别:当synchronized引入轻量级所和自学锁后两者性能就差不多
	官方建议使用synchronized,因为使用更简单
	4.功能区别:
		便利性:synchronized使用方便简洁,并且是由编译器保证加锁和释放,而ReentrantLock是手动声明加锁和释放锁
		细粒度和灵活度:ReentrantLock优于synchronized
ReentrantLock独有的功能:

	1.可指定是公平锁还是非公平锁,而synchronized只能是非公平锁(所谓的公平锁就是先等待的线程先获得锁)
	2.提供了一个Condition类,可以分组唤醒需要唤醒的线程
	3.提供能够中断等待锁的线程的机制,lock.lockInterruptibly()
- StampedLock

控制锁有三种模式:写,读,乐观读

一个StampedLock是由版本和模式两个部分组成.

*乐观读*:当读的操作很多,写的操作很少的情况下,写入和读取同时发生的几率很小,
因此不悲观的使用完整的读取锁定,程序可以查看读取资料之后是否得到写入之后的变更再采取后续的措施,大幅度提高程序的吞吐量.

总结:

	1.只有少量的线程的时候,使用synchronized是很好的通用的锁实现
	2.线程不少,但是增长的趋势是可以预估的,这时ReentrantLock是一个很好的锁实现