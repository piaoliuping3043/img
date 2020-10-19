package vip.crazyboy.img.task;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import vip.crazyboy.img.utils.StandardThreadExecutor;

import java.util.Map;

/**
 * @author LP
 * @date 2020/4/24 8:51 下午
 * @description 线程监控
 */

@Component
@Slf4j
public class ThreadMonitorTask {

    /**
     * @author LP
     * @date 2020/4/24 8:51 下午
     * @description 线程池监控
     */
    @Scheduled(fixedRate = 10 * 1000)
    public void monitor(){
        log.info("==========================================================================================================================");
        for (Map.Entry<String, StandardThreadExecutor> executorEntry : StandardThreadExecutor.threadMap.entrySet()) {
            log.info(executorEntry.getKey() +
                    String.format("[monitor] [%d/%d] Active: %d, Completed: %d, queueSize: %d, Task: %d, isShutdown: %s, isTerminated: %s",
                            executorEntry.getValue().getPoolSize(),
                            executorEntry.getValue().getCorePoolSize(),
                            executorEntry.getValue().getActiveCount(),
                            executorEntry.getValue().getCompletedTaskCount(),
                            executorEntry.getValue().getQueue().size(),
                            executorEntry.getValue().getTaskCount(),
                            executorEntry.getValue().isShutdown(),
                            executorEntry.getValue().isTerminated()));
        }
        log.info("==========================================================================================================================");
    }
}
