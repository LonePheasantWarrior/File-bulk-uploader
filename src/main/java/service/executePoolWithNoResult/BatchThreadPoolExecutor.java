package service.executePoolWithNoResult;

import java.util.concurrent.Executors;
import java.util.concurrent.ThreadPoolExecutor;

public class BatchThreadPoolExecutor {
    private ThreadPoolExecutor threadPoolExecutor;
    //重试次数
    private int retry = 3;
    //线程池大小
    private int size = 20;

    /**
     * 默认线程数大小为电脑的核心数
     */
    public BatchThreadPoolExecutor(){
        this(Runtime.getRuntime().availableProcessors());
    }

    public BatchThreadPoolExecutor(int threadCount){
        this.size=threadCount;
        threadPoolExecutor= (ThreadPoolExecutor) Executors.newFixedThreadPool(threadCount);
    }

    public void execute(BatchRunnable runnable){
        threadPoolExecutor.execute(new Runnable() {
            @Override
            public void run() {
                execute(runnable,0);
            }
        });
    }

    private void execute(BatchRunnable runnable,int retryCount){
        try {
            runnable.run();
        }catch (Exception e){
            if (retryCount < retry){
                execute(runnable,++retryCount);
            }else {
                runnable.error(e);
            }
        }
    }

    public  ThreadPoolExecutor getThreadPoolExecutor(){
        return threadPoolExecutor;
    }

    public int getRetry(){
        return retry;
    }

    public void setRetry(int retry){
        this.retry = retry;
    }

    public int getSize(){
        return size;
    }

    public void setSize(int size){
        this.size = size;

        threadPoolExecutor.setCorePoolSize(size);
        threadPoolExecutor.setMaximumPoolSize(size);
    }


}
