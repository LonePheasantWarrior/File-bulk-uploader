package service.executePool;

import domain.util.FileUploader;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.util.concurrent.Callable;

import static java.lang.Thread.sleep;

public class FileUploadTask implements Callable<Boolean> {
    private Logger log = LoggerFactory.getLogger(FileUploadTask.class);

    private static final int RETRY = 3;

    private File target;

    public FileUploadTask(File target){
        this.target = target;
    }

    @Override
    public Boolean call() throws Exception {
        return execute(0);
    }

    private boolean execute(int reTryCount) throws InterruptedException {
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
                log.error("Upload failed, the number of retries is " + reTryCount);
            }
        }
        return success;
    }
}
