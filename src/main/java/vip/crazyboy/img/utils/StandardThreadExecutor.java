package vip.crazyboy.img.utils;

import com.google.common.collect.Maps;
import lombok.extern.slf4j.Slf4j;
import org.apache.tomcat.util.threads.ThreadPoolExecutor;

import java.util.Map;
import java.util.concurrent.*;

/**
 * @author LP
 * @date 2020/4/21 6:56 下午
 * @description 线程池
 */
@Slf4j
public class StandardThreadExecutor extends ThreadPoolExecutor {
    public static Map<String, StandardThreadExecutor> threadMap = Maps.newConcurrentMap();
    private String threadPoolName;

    public StandardThreadExecutor(int corePoolSize, int maximumPoolSize, long keepAliveTime, TimeUnit unit, BlockingQueue<Runnable> workQueue, RejectedExecutionHandler handler, String threadPoolName) {
        super(corePoolSize, maximumPoolSize, keepAliveTime, unit, workQueue, handler);
        this.threadPoolName = threadPoolName;
        threadMap.put(threadPoolName, this);
    }

    public StandardThreadExecutor(int corePoolSize, int maximumPoolSize, long keepAliveTime, TimeUnit unit, BlockingQueue<Runnable> workQueue, ThreadFactory threadFactory, RejectedExecutionHandler handler, String threadPoolName) {
        super(corePoolSize, maximumPoolSize, keepAliveTime, unit, workQueue, threadFactory, handler);
        this.threadPoolName = threadPoolName;
        threadMap.put(threadPoolName, this);
    }

    public StandardThreadExecutor(int corePoolSize, int maximumPoolSize, long keepAliveTime, TimeUnit unit, BlockingQueue<Runnable> workQueue, ThreadFactory threadFactory, String threadPoolName) {
        super(corePoolSize, maximumPoolSize, keepAliveTime, unit, workQueue, threadFactory);
        this.threadPoolName = threadPoolName;
        threadMap.put(threadPoolName, this);
    }

    public StandardThreadExecutor(int corePoolSize, int maximumPoolSize, long keepAliveTime, TimeUnit unit, BlockingQueue<Runnable> workQueue, String threadPoolName) {
        super(corePoolSize, maximumPoolSize, keepAliveTime, unit, workQueue);
        this.threadPoolName = threadPoolName;
        threadMap.put(threadPoolName, this);
    }


    /**
     * @author LiuPeng
     * @date 2020/4/21 6:55 下午
     * @description 修改线程名字
     */
    @Override
    protected void beforeExecute(Thread t, Runnable r) {
        if (t.getName().startsWith("pool-")){
            t.setName(t.getName().replaceAll("pool-\\d-thread", this.threadPoolName));
        }
    }
}
