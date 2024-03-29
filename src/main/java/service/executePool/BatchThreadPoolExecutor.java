package service.executePool;

import java.util.concurrent.Executors;
import java.util.concurrent.ThreadPoolExecutor;

public class BatchThreadPoolExecutor {
    private ThreadPoolExecutor threadPoolExecutor;

    private int size;

    //默认线程数大小为本机的处理器核心数
    public BatchThreadPoolExecutor(){
        this(Runtime.getRuntime().availableProcessors() > 1 ? Runtime.getRuntime().availableProcessors() : 8);
    }

    private BatchThreadPoolExecutor(int threadCount){
        this.size = threadCount;
        threadPoolExecutor = (ThreadPoolExecutor) Executors.newFixedThreadPool(size);
        System.out.println("The size of thread pool: " + size);
    }

    public ThreadPoolExecutor getThreadPoolExecutor(){
        return threadPoolExecutor;
    }

    public int getSize() {
        return size;
    }
}
