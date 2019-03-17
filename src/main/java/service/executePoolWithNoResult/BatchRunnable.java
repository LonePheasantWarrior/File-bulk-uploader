package service.executePoolWithNoResult;

public interface BatchRunnable {
    /**
     * 上传线程默认的执行方法
     */
    void run();

    /**
     * 达到最大重试次数后的异常处理方法
     * @param e
     */
    void error(Exception e);
}
