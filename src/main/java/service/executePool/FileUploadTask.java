package service.executePool;

import domain.util.FileUploader;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import service.BatchContext;

import java.io.File;
import java.util.Set;

import static java.lang.Thread.sleep;

public class FileUploadTask implements Runnable {
    private Logger log = LoggerFactory.getLogger(FileUploadTask.class);

    private BatchContext batchContext;
    private static final int RETRY = 3;

    private File target;

    public FileUploadTask(File target,BatchContext batchContext){
        this.batchContext = batchContext;
        this.target = target;
    }

    @Override
    public void run() {
        try {
            if (execute(0)){
                Set<String> upLoadedNameList = batchContext.getUploadedNameList();
                upLoadedNameList.add(target.getName());
            }else {
                Set<String> uploadFailedList = batchContext.getUploadFailedList();
                uploadFailedList.add(target.getName());
            }
        } catch (InterruptedException e) {
            log.error("upload error",e);
        }
    }

    private Boolean execute(int reTryCount) throws InterruptedException {
        boolean success = false;
        try {
            if (FileUploader.ftpUpload(target,target.getName())){
                success = true;
            }else {
                log.warn("The file of [" + target.getName() + "] Upload fail,will be re-uploaded in two seconds" );
                throw new Exception("Upload fail");
            }
        }catch (Exception e){
            if (reTryCount++ < RETRY){
                sleep(2000);
                log.debug("Start the "+reTryCount+"th upload");
                execute(reTryCount);
            }else {
                log.error("Upload failed, the number of retries is " + --reTryCount);
            }
        }
        return success;
    }
}
