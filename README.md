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