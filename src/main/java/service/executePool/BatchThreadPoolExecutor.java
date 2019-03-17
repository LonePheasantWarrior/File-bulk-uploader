package service.executePool;

import java.util.concurrent.Executors;
import java.util.concurrent.ThreadPoolExecutor;

public class BatchThreadPoolExecutor {
    private ThreadPoolExecutor threadPoolExecutor;

    private int size = 20;

    //默认线程数大小为本机的处理器核心数
    public BatchThreadPoolExecutor(){
        this(Runtime.getRuntime().availableProcessors());
    }

    private BatchThreadPoolExecutor(int threadCount){
        this.size = threadCount;
        threadPoolExecutor = (ThreadPoolExecutor) Executors.newFixedThreadPool(threadCount);
    }

    public ThreadPoolExecutor getThreadPoolExecutor(){
        return threadPoolExecutor;
    }

}
