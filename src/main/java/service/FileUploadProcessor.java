package service;

import domain.util.FileUploader;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.item.ItemProcessor;
import service.executePool.BatchThreadPoolExecutor;
import service.executePool.FileUploadTask;

import java.io.File;
import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;
import java.util.concurrent.ThreadPoolExecutor;

public class FileUploadProcessor implements ItemProcessor<BatchContext,BatchContext> {
    private Logger logger = LoggerFactory.getLogger(FileUploadProcessor.class);
    private BatchThreadPoolExecutor batchThreadPoolExecutor;

    @Override
    public BatchContext process(BatchContext batchContext) throws ExecutionException {
        Set<File> fileList = batchContext.getFileList();
        Set<String> uploadedName = new HashSet<>();

        logger.info("Bulk uploading...");
        long timeConsuming = System.currentTimeMillis();
        ThreadPoolExecutor threadPoolExecutor = batchThreadPoolExecutor.getThreadPoolExecutor();
        for (File file : fileList){
            try {
                if (uploadFileWithMultithreading(threadPoolExecutor,file)){
                    uploadedName.add(file.getName());
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
                logger.error(e.toString());
            }
        }

        batchContext.setUploadedNameList(uploadedName);
        logger.info("Batch upload completed,it took " + (System.currentTimeMillis() - timeConsuming) / 1000 + " seconds");
        return batchContext;
    }

    private boolean uploadFileWithMultithreading(ThreadPoolExecutor threadPoolExecutor,File target) throws ExecutionException, InterruptedException {
        Future<Boolean> success = threadPoolExecutor.submit(new FileUploadTask(target));
        return success.get();
    }

    public BatchThreadPoolExecutor getBatchThreadPoolExecutor() {
        return batchThreadPoolExecutor;
    }

    public void setBatchThreadPoolExecutor(BatchThreadPoolExecutor batchThreadPoolExecutor) {
        this.batchThreadPoolExecutor = batchThreadPoolExecutor;
    }
}
