package service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import service.executePool.BatchThreadPoolExecutor;
import service.executePool.FileUploadTask;

import java.io.File;
import java.util.Set;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ThreadPoolExecutor;

import static java.lang.Thread.sleep;

public class FileUploadProcessor implements Processor<BatchContext,BatchContext> {
    private Logger logger = LoggerFactory.getLogger(FileUploadProcessor.class);
    private BatchThreadPoolExecutor batchThreadPoolExecutor;

    @Override
    public BatchContext process(BatchContext batchContext) throws ExecutionException, InterruptedException {
        Set<File> fileList = batchContext.getFileList();

        logger.info("Bulk uploading...");
        logger.info("Number of items: " + fileList.size());
//        long timeConsuming = System.currentTimeMillis();
        ThreadPoolExecutor threadPoolExecutor = batchThreadPoolExecutor.getThreadPoolExecutor();
//        for (File file : fileList){
//            try {
//                if (uploadFileWithMultithreading(threadPoolExecutor,file)){
//                    uploadedName.add(file.getName());
//                }else {
//                    uploadFailedName.add(file.getName());
//                }
//            } catch (InterruptedException e) {
//                e.printStackTrace();
//                logger.error(e.toString());
//            }
//        }

        for (File file : fileList){
            threadPoolExecutor.submit(new FileUploadTask(file,batchContext));
        }

        while (threadPoolExecutor.getActiveCount() != 0){
            sleep(5000);
        }

//        batchContext.setUploadedNameList(uploadedName);
//        batchContext.setUploadFailedList(uploadFailedName);

//        logger.info(uploadedName.size() + " items upload completed,took " + (System.currentTimeMillis() - timeConsuming) / 1000 + " seconds");
        Set<String> uploadFailedName = batchContext.getUploadFailedList();
        if (uploadFailedName.size() > 0){
            logger.warn("Upload failed items: " + uploadFailedName.toString());
        }
        return batchContext;
    }

    public BatchThreadPoolExecutor getBatchThreadPoolExecutor() {
        return batchThreadPoolExecutor;
    }

    public void setBatchThreadPoolExecutor(BatchThreadPoolExecutor batchThreadPoolExecutor) {
        this.batchThreadPoolExecutor = batchThreadPoolExecutor;
    }
}
